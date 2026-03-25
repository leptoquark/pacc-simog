package it.anticorruzione.ted.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import it.anticorruzione.ted.beans.ResponseMessageTED;
import it.anticorruzione.ted.db.entity.TEDNotice;
import it.anticorruzione.ted.db.entity.TEDStatus;
import it.anticorruzione.ted.enums.ReasonCodeEnum;
import it.anticorruzione.ted.enums.StatusNoticeEnum;
import it.anticorruzione.ted.enums.TypeNoticeEnum;
import it.anticorruzione.ted.json.TEDNoticeInformation;
import it.anticorruzione.ted.util.UtilityClass;
import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;

public class VerificaTEDActionManager extends CommonActionManager {
	

	
	public synchronized ResponseMessageTED execute(String ticket, String indexCollaborazione, String no_doc_ext) {
		ResponseMessageTED ris = null;
		Logger logger = null;
		ConnectionWSManager cwsm = null;
		Connection con = null;
		if(indexCollaborazione == null || "".equals(indexCollaborazione.trim())){
			indexCollaborazione = "-1";
		}else{
			indexCollaborazione = indexCollaborazione.trim();
		}
		
		try{
			logger = LoggerManager.getInstance().getLogger();
			ris = new ResponseMessageTED();
			WsSessions wss = new WsSessions();
			cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
			WSSessionManager wsm = new WSSessionManager(logger,cwsm);
			con = cwsm.getConnection();
			TicketManager tm = new TicketManager();
			SqlTools sqlt = new SqlTools();
			//-------	 begin operations		--------//	
			cwsm.setAutocommit(false);
			wss.setTicket(ticket);
			wss.setComando("verificaTED");
			wss.setCollaborazione(Integer.parseInt(indexCollaborazione));		
			wss = wsm.selectFindValidSession(wss);
			cwsm.commit();
			if(wss != null){
				logger.info(">>>>esiste una sessione associata al ticket");
				try{
					tm.validateRequestedActionByProfile(wss,TicketManager.VERIFICA_TED);
					if(tm.isValido()){
						logger.info(">>>>utente abilitato al comando richiesto");
						cwsm.setIsolation("t_serialize");
						logger.info(">>>> (connnessione settata a transaction serialized)");
						Collaborazione coll = null;
						if(!tm.isOperaComeOsservatorio()){
							coll = tm.getCollaborazione();
						}
						
						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
						
						ris=startVerificaTED(ris,no_doc_ext);
						if(ris.isSuccess() && isSbloccaGara()) {
							long idGara = tedDb.findNoticeByNoDocExt(no_doc_ext).getIdGara();
							sbloccaGaraLotto(idGara, con, logger);
						}
						
						if(ris.isSuccess() && isPubblicaGara()) {
							long idGara = tedDb.findNoticeByNoDocExt(no_doc_ext).getIdGara();
							updateDataPubbGara(idGara, con, logger);
						}
						
						//--- fine operazione
				    	wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
				    	if(wsm.updateSessionAfterOp(wss)){
				    		cwsm.commit();				
				    		logger.info(">>>>aggiornamento dello stato della sessione riuscito");
				    	}
						
					} else{
						logger.info("fallimento della validazione del ticket associazione comando - profilo non autorizzata");
						String messaggioErrore = "collaborazione ["+wss.getCollaborazione()+"] non abilitata al comando ["+wss.getComando()+"] richiesto";
						wss.setLastError(messaggioErrore);				
						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
						if(wsm.updateSessionAfterOp(wss)){
							cwsm.commit();
							logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
							ris.setSuccess(false);
							ris.setError(messaggioErrore);
						}			
					}
				} catch(SimogWSException ste){
						logger.error("indice collaborazione non valido");
						String messaggioErrore = ste.getMyMessage();
						wss.setLastError("collaborazione ["+wss.getCollaborazione()+"] non esiste");				
						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
						if(wsm.updateSessionAfterOp(wss)){
							logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
							ris.setSuccess(false);
							ris.setError(messaggioErrore);
							cwsm.commit();
						}
						return ris;
					}
			}
			
		}catch(SimogWSException ste){
			if(cwsm != null){
				cwsm.rollback();
			}
			ris.setSuccess(false);
			ris.setError(ste.getMyMessage());
			logger.error("SimogTEDException catched: "+ste.getMyMessage());
		}finally{
			if(cwsm != null){
				cwsm.closeConnection();
			}
		}

		return ris;		
	}
	
	public synchronized ResponseMessageTED startVerificaTED(ResponseMessageTED ris, String no_doc_ext) {
		//default a false
		ris.setSuccess(false);
		ris.setStatus(statusSE);
		
		
		String esitoValidataion = noDocExtValidation(no_doc_ext);
		
		//B1 - verifica obbligatorietà no_doc_ext
		if(esitoValidataion!=null) {
			ris.setStatus_msg(esitoValidataion);
		} else { 
				//B2 - verifica se il no_doc_ext è associato a un formulario precedentemente inviato
				TEDNotice notice = tedDb.findNoticeByNoDocExt(no_doc_ext);
				
				if(notice==null) {
					ris.setStatus_msg(statusSE+" 000 - no_doc_ext: Nessun formulario presente");
				} else {
					TEDStatus lastStatus = tedDb.getLastTEDStatus(notice.getIdTedNotice());

					ris.setStatus(lastStatus.getStatusNoticeEnum().getStrStatus());
					ris.setSuccess(true);
					
					switch(lastStatus.getStatusNoticeEnum()) {
						//Casi in cui si richiede aggiornamento stato dal TED
						case IN_PROGRESS:
						case TED_ERROR:
						case RECEIVED:
						case VALIDATION_ACCEPTED:
							//B6
							TEDNoticeInformation newStatusFromTED = conn.getNotice(notice.getSubmissionId());
							ris = manageNewStatus(lastStatus,newStatusFromTED);
							break;
							
						//Casi dove non è richiesto richiamare il TED e restituiamo i dati già salvati
						case RECEPTION_ERROR:
						case QUALIFICATION_ERROR:
						case NOT_PUBLISHED:
							if(lastStatus.isCancelByUser()==null || lastStatus.isCancelByUser()==false) {
								TEDNoticeInformation currentNoticeinfo = new TEDNoticeInformation(lastStatus.getJsonResponse());
								ris.setStatus_msg(currentNoticeinfo.getErrors());
							} else {
								ris.setStatus_msg(lastStatus.getJsonResponse());								
							}
							break;
						case QUALITY_ACCEPTED:
						case QUALITY_SKIPPED:
						case PUBLISHED:	
							ris.setNo_doc_ojs(notice.getNoDocOjs());
							ris.setPublication_date(UtilityClass.dateToStringTED(notice.getPublishedAt(), false));
							ris.setTed_link(notice.getTedLink());
							ris.setStatus_msg("Il formulario e' pubblicato su TED");
							break;

					}
				}
		}
		
		return ris;
	}
	
	public synchronized ResponseMessageTED manageNewStatus(TEDStatus lastStatus, 
															TEDNoticeInformation newStatus) {
		ResponseMessageTED ris = new ResponseMessageTED();
		
		//Se il nuovo stato è diverso dal precedente, salva un nuovo record nel db altrimenti aggiorna il precedente
		if(!lastStatus.getIdTedTypeStatus().equals(StatusNoticeEnum.findStatusByStr(newStatus.getStatus()).getIdStato()))
			tedDb.insertTEDStatus(newStatus, lastStatus.getIdTedNotice());
		else
			tedDb.updateTEDStatus(lastStatus);
		
		
			
		ris.setStatus(newStatus.getStatus());
		ris.setSuccess(true);
		StatusNoticeEnum enumNewStatus = StatusNoticeEnum.valueOf(newStatus.getStatus());
		switch(enumNewStatus) {
			case RECEIVED:
			case IN_PROGRESS:
			case VALIDATION_ACCEPTED:
			case QUALITY_ACCEPTED:
			case QUALITY_SKIPPED:
				ris.setStatus_msg("Il formulario e' stato preso in carico dal TED ed e' in attesa di essere elaborato");
				break;
			case PUBLISHED:
				//B7
				ris.setStatus_msg("Il TED conferma l'avvenuta pubblicazione del formulario");
				
				//Aggiorna le info del notice con i dati di pubblicazione
				TEDNotice notice = tedDb.findNoticeById(lastStatus.getIdTedNotice());
				Date dataPubbTed = UtilityClass.stringTEDtoDate(newStatus.getPublicationDate());
				notice.setPublishedAt(dataPubbTed);
				notice.setNoDocOjs(newStatus.getNoDocOjs());
				notice.setTedLink(newStatus.getTedLink());
				tedDb.updateTEDNotice(notice);
				
				if(TypeNoticeEnum.F02.getTipo().equals(newStatus.getForm())) {
					setPubblicaGara(true);
					tedDb.updatePubbTed(notice.getIdGara(), 
										PageHelper.formatDate(dataPubbTed), 
										UtilityClass.convertNoDocOjs(newStatus.getNoDocOjs()));
					ris.setStatus_msg(ris.getStatus_msg()+". Il processo di pubblicazione dell'appalto su Simog e' completato");
				}
				
				ris.setNo_doc_ojs(newStatus.getNoDocOjs());
				ris.setPublication_date(newStatus.getPublicationDate());
				ris.setTed_link(newStatus.getTedLink());
				break;
			case TED_ERROR:
				//TODO - occorre gestire meglio i TED ERROR....
				ris.setStatus_msg(tedErr+"_401 - Si riscontrano problemi di comunicazione con il TED. Si prega di riprovare più tardi.");
				break;
			case RECEPTION_ERROR:
			case QUALIFICATION_ERROR:
	             ris.setStatus_msg(newStatus.getErrors());

				if(TypeNoticeEnum.F02.getTipo().equals(newStatus.getForm())) {
					setSbloccaGara(true);
				}
				
				break;
			case NOT_PUBLISHED:
				String messaggeToPrint = getErrorTypeFromRC(newStatus.getReason_code());
	
				if(!newStatus.getReason_code().equals("CP"))
					ris.setStatus_msg(messaggeToPrint+" "+newStatus.getErrors());
				   
				
				if(TypeNoticeEnum.F02.getTipo().equals(newStatus.getForm())) {
					setSbloccaGara(true);
				}
				
				break;
		}
		
		return ris;
	}
	
	
	private static String getErrorTypeFromRC(String reasonCode) {
		ReasonCodeEnum resCodeEnum = ReasonCodeEnum.getReasonCodeMessage(reasonCode);
		if(resCodeEnum!=null)
			return resCodeEnum.getDescrizione();
		
		return null;
	}
	
	public void updateDataPubbGara(long idGara, Connection connection, Logger logger) {
		LottoManager lottoManager = new LottoManager(connection, logger);
		try {
			lottoManager.updatePubblicazioneToCurrentDate(idGara);
		} catch (SQLException e) {
			e.printStackTrace();
      		logger.error(e.getMessage());
		}
		
	}
	
}
