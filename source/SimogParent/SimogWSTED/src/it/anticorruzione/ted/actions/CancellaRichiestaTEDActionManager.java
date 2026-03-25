package it.anticorruzione.ted.actions;

import java.sql.Connection;

import org.apache.log4j.Logger;

import it.anticorruzione.ted.beans.ResponseMessageTED;
import it.anticorruzione.ted.db.entity.TEDNotice;
import it.anticorruzione.ted.db.entity.TEDStatus;
import it.anticorruzione.ted.enums.StatusNoticeEnum;
import it.anticorruzione.ted.enums.TypeNoticeEnum;
import it.anticorruzione.ted.exception.TEDErrorException;
import it.anticorruzione.ted.exception.TEDErrorException.TEDErrors;
import it.anticorruzione.ted.json.TEDNoticeInformation;
import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;

public class CancellaRichiestaTEDActionManager extends CommonActionManager {

	public ResponseMessageTED execute(String ticket, String indexCollaborazione, String no_doc_ext) {
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
					tm.validateRequestedActionByProfile(wss,TicketManager.CANCELLA_TED);
					if(tm.isValido()){
						logger.info(">>>>utente abilitato al comando richiesto");
						ris.setSuccess(true);
						cwsm.setIsolation("t_serialize");
						logger.info(">>>> (connnessione settata a transaction serialized)");
						Collaborazione coll = null;
						if(!tm.isOperaComeOsservatorio()){
							coll = tm.getCollaborazione();
						}
						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
						
						ris = startCancellaRichiestaTED(no_doc_ext);
						if(ris.isSuccess() && isSbloccaGara()) {
							long idGara = tedDb.findNoticeByNoDocExt(no_doc_ext).getIdGara();
							sbloccaGaraLotto(idGara, con, logger);
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
		}

		return ris;		
	}
	
	public synchronized ResponseMessageTED startCancellaRichiestaTED(String no_doc_ext) {
		ResponseMessageTED ris = new ResponseMessageTED();
		//default a false
		ris.setSuccess(false);
		ris.setStatus(statusSE);
		
		
      String esitoValidation = noDocExtValidation(no_doc_ext);
		
		//B1 - verifica obbligatorietà no_doc_ext
		if(esitoValidation!=null) {
			ris.setStatus_msg(esitoValidation);
		} else { 
				//B2 - verifica se il no_doc_ext è associato a un formulario precedentemente inviato
				TEDNotice notice = tedDb.findNoticeByNoDocExt(no_doc_ext);
				if(notice==null) {
					ris.setStatus_msg(statusSE+" 000 - no_doc_ext: Nessun formulario presente");
				} else {
					TEDStatus noticeStatus = tedDb.getLastTEDStatus(notice.getIdTedNotice());
					
					ris.setSuccess(true);

					switch(noticeStatus.getStatusNoticeEnum()) {
						//Casi in cui si richiede aggiornamento stato dal TED
						case IN_PROGRESS:
						case VALIDATION_ACCEPTED:
						case TED_ERROR:
						case RECEIVED:
							TEDNoticeInformation currentNoticeinfo = new TEDNoticeInformation(noticeStatus.getJsonResponse());
							TEDNoticeInformation newStatus = conn.getNotice(currentNoticeinfo.getSubmission_id());
							ris = manageCancel(newStatus, ris);
							
							if(TypeNoticeEnum.F02.getIdTipo()==notice.getIdTedTypeNotice()) {
								setSbloccaGara(true);
							}
							
							break;
							
						//Casi dove non è richiesto richiamare il TED
						case RECEPTION_ERROR:
						case QUALIFICATION_ERROR:
						case NOT_PUBLISHED:
							ris.setStatus(StatusNoticeEnum.findStatusById(noticeStatus.getIdTedTypeStatus().intValue()).getStrStatus());
							ris.setStatus_msg("Non e' possibile cancellare la richiesta di pubblicazione di un formulario gia' precedentemente rifiutata");
							
							if(TypeNoticeEnum.F02.getIdTipo()==notice.getIdTedTypeNotice()) {
								setSbloccaGara(true);
							}
							
							break;
						case QUALITY_SKIPPED:
						case QUALITY_ACCEPTED:
						case PUBLISHED:
							ris.setStatus(StatusNoticeEnum.findStatusById(noticeStatus.getIdTedTypeStatus().intValue()).getStrStatus());
							ris.setStatus_msg("Non e' possibile cancellare la richiesta di pubblicazione di un formulario pubblicato");
							break;

					}
				}
		}
		
		return ris;
	}

	private ResponseMessageTED manageCancel(TEDNoticeInformation newStatus, ResponseMessageTED ris) {
		StatusNoticeEnum enumNewStatus = StatusNoticeEnum.valueOf(newStatus.getStatus());
		switch(enumNewStatus) {
			case IN_PROGRESS:
			case VALIDATION_ACCEPTED:
			case RECEIVED:
					try {
						 conn.stopPublication(newStatus.getSubmission_id());
					} catch (TEDErrorException e) {
		
						TEDErrors error = e.getTEDError();
						//Se il formulario sul TED era già cancellato o non presente, procedi ugualmente con la cancellazione locale,
						//altrimenti è un problema bloccante
						if(!TEDErrors.TE_403.equals(error) && !TEDErrors.TE_404.equals(error)) {
							//Altrimenti è un problema bloccante su TED
							ris.setSuccess(false);
							ris.setStatus(StatusNoticeEnum.TED_ERROR.getStrStatus());
							ris.setStatus_msg(e.getStatusMessage());
							return ris;
						}
						
					} catch(Exception e) {
						e.printStackTrace();
					}
						tedDb.cancelPublication(newStatus.getNo_doc_ext(),"Cancellato su richiesta dell'utente");
						ris.setStatus(StatusNoticeEnum.NOT_PUBLISHED.getStrStatus());
						ris.setStatus_msg("Conferma di avvenuta cancellazione della richiesta");
				break;
			case TED_ERROR:
				ris.setStatus_msg(tedErr+"_401 - Si riscontrano problemi di comunicazione con il TED. Si prega di riprovare più tardi.");
				break;
			case RECEPTION_ERROR:
			case NOT_PUBLISHED:
			case QUALIFICATION_ERROR:
				ris.setStatus_msg("Non e' possibile cancellare la richiesta di pubblicazione di un formulario gia' precedentemente rifiutata");
				break;
			case QUALITY_ACCEPTED:
			case QUALITY_SKIPPED:
			case PUBLISHED:
				ris.setStatus_msg("Non e' possibile cancellare la richiesta di pubblicazione di un formulario pubblicato");
				break;
		}
		return ris;
	}

}
