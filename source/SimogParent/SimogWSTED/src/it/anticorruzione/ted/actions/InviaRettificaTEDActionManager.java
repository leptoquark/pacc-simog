package it.anticorruzione.ted.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import eu.europa.publications.resource.schema.ted.r209.reception.TedEsenders;
import it.anticorruzione.ted.beans.LottoTED;
import it.anticorruzione.ted.beans.ResponseMessageTED;
import it.anticorruzione.ted.db.entity.TEDNotice;
import it.anticorruzione.ted.db.entity.TEDStatus;
import it.anticorruzione.ted.db.entity.TEDSubmit;
import it.anticorruzione.ted.enums.StatusNoticeEnum;
import it.anticorruzione.ted.enums.TypeNoticeEnum;
import it.anticorruzione.ted.notice.F14Generator;
import it.anticorruzione.ted.util.UtilityClass;
import it.anticorruzione.ted.validator.F14Validator;
import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.ws.beans.DataNotice;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;
import it.avlp.simog.ws.massload.xmlbeans.DeltaGaraTED;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoRettifica;

public class InviaRettificaTEDActionManager extends CommonActionManager {

	
	public ResponseMessageTED execute(String ticket,
											String indexCollaborazione, 
											String id_gara, 
											String no_doc_ojs, 
											FormularioAvvisoRettifica formularioRett) {
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
			wss.setComando("inviaRettificaTED");
			wss.setCollaborazione(Integer.parseInt(indexCollaborazione));		
			wss = wsm.selectFindValidSession(wss);
			cwsm.commit();
			if(wss != null){
				logger.info(">>>>esiste una sessione associata al ticket");
				try{
					tm.validateRequestedActionByProfile(wss,TicketManager.RETTIFICA_TED);
					if(tm.isValido()){
						logger.info(">>>>utente abilitato al comando richiesto");
						cwsm.setIsolation("t_serialize");
						logger.info(">>>> (connnessione settata a transaction serialized)");
						Collaborazione coll = null;
						if(!tm.isOperaComeOsservatorio()){
							coll = tm.getCollaborazione();
						}
						
						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
		
						ris = startInviaRettificaTED(id_gara, no_doc_ojs,formularioRett,con,logger);
						
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
					} catch (NumberFormatException e) {
						if(cwsm != null){
							cwsm.rollback();
						}
						ris.setSuccess(false);
						ris.setError(e.getMessage());
						logger.error("SimogWSException catched: "+e.getMessage());
					} catch (SQLException e) {
						if(cwsm != null){
							cwsm.rollback();
						}
						ris.setSuccess(false);
						ris.setError(e.getMessage());
						logger.error("SimogWSException catched: "+e.getMessage());
					} catch (Exception e) {
						e.printStackTrace();
						if(cwsm != null){
							cwsm.rollback();
						}
						ris.setSuccess(false);
						ris.setError(e.getMessage());
						logger.error("SimogWSException catched: "+e.getMessage());
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
	
	
	public synchronized ResponseMessageTED startInviaRettificaTED(String id_gara, 
																		String no_doc_ojs, 
																		FormularioAvvisoRettifica formularioRett, 
																		 Connection con, Logger logger)
																				throws NumberFormatException, SQLException, Exception{
		
		ResponseMessageTED ris = new ResponseMessageTED();
		//default a false
		ris.setSuccess(false);
		ris.setStatus(statusSE);
		
		String esitoValidation = noDocOjsValidation(no_doc_ojs,null,0,0);
		if(esitoValidation!=null && !"".equals(esitoValidation)) {
			ris.setStatus_msg(esitoValidation);
			return ris;
		}
		
			esitoValidation = garaValidation(id_gara);
		if(esitoValidation!=null) {
				ris.setStatus_msg(esitoValidation);
			return ris;
		}
			
				GaraManager gm = new GaraManager(con,logger);
				Gara g = gm.getGara(Long.parseLong(id_gara)); 
		if(g==null) {
					ris.setStatus_msg(statusSE+"_P07 – Numero gara inesistente o non di competenza");
			return ris;
		}
					
					LottoManager lm = new LottoManager(con, logger);
					String currentDate = PageHelper.getCurrentDate();
					List<Lotto> listaLotti = lm.getListaLotti(g.getId_Gara());
					
					String dataInvito = listaLotti.get(0).getDataScadenzaRichiestaInvito();
					String dataPag = listaLotti.get(0).getDATA_SCADENZA_PAGAMENTI();
					if((dataInvito==null && currentDate.compareTo(dataPag) > 0) || 
							(dataInvito!=null &&	currentDate.compareTo(dataInvito) > 0)) {
						esitoValidation = statusSE+"_108 - Non e' possibile procedere con la richiesta di rettifica: risultano decorsi i termini della data di scadenza della presentazione dell'offerta/data di scadenza richiesta di invito\n";
						ris.setStatus_msg(esitoValidation);
			return ris;
		}
		
							
							boolean verificaAgg = false;
							TEDNotice noticeAgg = tedDb.findByTypeAndIdGara(TypeNoticeEnum.F03, g.getId_Gara());
						
							if(noticeAgg!=null) {
								TEDStatus aggStatus = tedDb.getLastTEDStatus(noticeAgg.getIdTedNotice());
								if(aggStatus.getIdTedStatus().intValue()==StatusNoticeEnum.PUBLISHED.getIdStato())
									verificaAgg=true;
							}
		if(verificaAgg) {
			esitoValidation = statusSE+"_109 - Non è possibile procedere con la richiesta di rettifica: risulta essere pubblicato un formulario di avviso aggiudicazione\n";
			ris.setStatus_msg(esitoValidation);
			return ris;
		}
								
									//Esegui comunque la validazione
									esitoValidation = F14Validator.valida(formularioRett,tedDb);
		if(esitoValidation!=null && !"".equals(esitoValidation)) {
										ris.setStatus_msg(esitoValidation);
			return ris;
		} 
										
										TEDNotice notice = tedDb.findByTypeAndIdGara(TypeNoticeEnum.F14, g.getId_Gara());
									    //Se è stato precedentemente inviato un formulario, se questo è IN_PROGRESS cancellalo
										checkPreviousPublication(notice);
										
										DataNotice dataNotice = new DataNotice();									
										
										dataNotice.setGara(g);
										DeltaGaraTED deltaGaraTED = tedDb.getDeltaGara(g.getId_Gara());
										dataNotice.setDeltaGaraTED(deltaGaraTED);
										
										List<LottoTED> listaLottoTED = new ArrayList<LottoTED>();
										for(Lotto lotto : listaLotti) {
											LottoTED lottoTed = new LottoTED();
											lottoTed.setLotto(lotto);
											lottoTed.setDeltaLottoTED(tedDb.getDeltaLotto(lotto.getFullCIG()));
											listaLottoTED.add(lottoTed);
										}
										
										dataNotice.setListaLotti(listaLottoTED);
										
		TEDNotice originalNotice = tedDb.findNoticeByNoDocOjsByLotto(no_doc_ojs,null,0);
										TEDSubmit originalSubmit = tedDb.findSubmitByIdNotice(originalNotice.getIdTedNotice());
										
										dataNotice.setNoticeNumberOjs(no_doc_ojs);
										dataNotice.setOriginalDataDispatch(UtilityClass.dateToStringTED(originalSubmit.getDataRequest(), true));
										dataNotice.setOriginalNoDocExt(originalNotice.getNoDocExt());
										dataNotice.setFormularioRettifica(formularioRett);
										ris = sendNotice(dataNotice);
									
						
		return ris;
		
	}
	
	private ResponseMessageTED sendNotice(DataNotice dataNotice) {
		
		String newNoDocExt = createNoDocExt();
		dataNotice.setNoDocExt(newNoDocExt);
		dataNotice.setEsenderlogin(SimogProperties.getInstance().getUsernameTed());
		F14Generator f14gen = new F14Generator(dataNotice, SimogProperties.getInstance().getXsdTed());
		TedEsenders tedEsender = f14gen.createNotice();
		try {
			return saveAndSendToTED(dataNotice, tedEsender.toString(),TypeNoticeEnum.F14,null);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}



	
}
