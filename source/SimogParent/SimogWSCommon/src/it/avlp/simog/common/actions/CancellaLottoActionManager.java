package it.avlp.simog.common.actions;

import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Collaborazioni;
import it.avlp.simog.beans.CollaborazioniRssa;
import it.avlp.simog.beans.MotivazioniBean;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.beans.ResponseCancellaLotto;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.GaraLottoManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;

import java.sql.Connection;

import org.apache.log4j.Logger;

public class CancellaLottoActionManager {
	
	/***********************************************************************************
	 * Esegue la modifica del Lotto
	 * @param ticket : String
	 * @param indexCollaborazione : String
	 * @param datiGara : String
	 * @param cig :String
	 * @return
	 */
	public synchronized static ResponseCancellaLotto execute(String ticket, String indexCollaborazione, 
			String id_motivazione, String note_canc, String cig ){
		//-------	object declarations		-------//
		ResponseCancellaLotto rgc = null;
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
			logger.info("-----------	begin  	---------------");
			logger.info("eseguendo: ResponseCancellaLotto execute(String ticket, String indexCollaborazione, String id_gara,String id_motivazione, String note_canc");
			rgc=new ResponseCancellaLotto();
			WsSessions wss = new WsSessions();
			cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
			WSSessionManager wsm = new WSSessionManager(logger,cwsm);
			con = cwsm.getConnection();
			TicketManager tm = new TicketManager();
			SqlTools sqlt = new SqlTools();
			//-------	 begin operations		--------//	
			cwsm.setAutocommit(false);
			wss.setTicket(ticket);
			wss.setComando("CancellaLotto");

			wss.setCollaborazione(Integer.parseInt(indexCollaborazione));		
			wss = wsm.selectFindValidSession(wss);
			cwsm.commit();
			if(wss != null){
				logger.info(">>>>esiste una sessione associata al ticket");
				try{
					tm.validateRequestedActionByProfile(wss, TicketManager.CANCELLA_LOTTO);
					if(tm.isValido()){
						logger.info(">>>>utente abilitato al comando richiesto");
						rgc.setSuccess(true);
						rgc.setError("");					
						//cwsm.setIsolation("t_serialize");
						logger.info(">>>> (connnessione settata a transaction serialized)");

						GaraLottoManager garaLottoManager = new GaraLottoManager(con,logger);						
						
						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
						try{
							Collaborazione col = null;
							CollaborazioniRssa collsRssa = null;
							Collaborazioni colls = null;
							if(!tm.isOperaComeOsservatorio()){	
								col = tm.getCollaborazione();
								colls = tm.getCollaborazioni();
								collsRssa = new CollaborazioniRssa(colls,col);
							}
							
							// PP verifica obbligatorietà motivazione
							MotivazioniBean motiviCanc = new MotivazioniBean();
							motiviCanc.loadAll(con, logger, false);
							if (motiviCanc.loadMotivazioni().get(id_motivazione) != null)
							{
								if("".equals(note_canc) && motiviCanc.isNotaObbligatoria(id_motivazione))
									throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_34);
							}
							else
								throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_33);

							boolean esito = garaLottoManager.cancellaLotto(wss.getUserId(),col,collsRssa,cig,id_motivazione,note_canc, tm.isOperaComeOsservatorio()) ;
							
							if(esito){
								rgc.setSuccess(true);
								rgc.setMessaggio("operazione effettuata correttamente");
							}
							else{
								throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_23);
							}
							logger.info("cancellazione riuscita");
						}catch(SimogWSException swe){
							logger.error("cancellazione fallita "+swe.getMyMessage());
							if(cwsm != null){
								cwsm.rollback();
							}
							rgc.setSuccess(false);
							String messaggioErrore = swe.getMyMessage();
							rgc.setError(messaggioErrore);
							logger.error("SimogWSException catched: "+messaggioErrore);						
							wss.setLastError(messaggioErrore);				
							wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							if(wsm.updateSessionAfterOp(wss)){
								logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
								rgc.setSuccess(false);
								rgc.setError(messaggioErrore);
								cwsm.commit();
							}else{
								logger.error("aggiornamento sessione fallito");
							}
							return rgc;
						}
						//cwsm.setIsolation("t_read_committed");
						if(wsm.updateSessionAfterOp(wss)){
							cwsm.commit();				
							logger.info(">>>>aggiornamento dello stato della sessione riuscito");
						}
					}else{
						logger.info("fallimento della validazione del ticket associazione comando - profilo non autorizzata");
						String messaggioErrore = "collaborazione ["+wss.getCollaborazione()+"] non abilitata al comando ["+wss.getComando()+"] richiesto";
						wss.setLastError(messaggioErrore);				
						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
						if(wsm.updateSessionAfterOp(wss)){
							cwsm.commit();
							logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
							rgc.setSuccess(false);
							rgc.setError(messaggioErrore);
						}			
					}
				//caso in cui l'indice passato non sia valido
				}catch(SimogWSException swe){
					logger.error("indice collaborazione non valido");
					String messaggioErrore = swe.getMyMessage();
					wss.setLastError("collaborazione ["+wss.getCollaborazione()+"] non esiste");				
					wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
					if(wsm.updateSessionAfterOp(wss)){
						cwsm.commit();
						logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
						rgc.setSuccess(false);
						rgc.setError(messaggioErrore);
						cwsm.commit();
					}
					return rgc;
				}
			}		
		}catch(SimogWSException swe){
			if(cwsm != null){
				cwsm.rollback();
			}
			rgc.setSuccess(false);
			rgc.setError(swe.getMyMessage());
			logger.error("SimogWSException catched: "+swe.getMyMessage());
		}
		finally{
			if(cwsm != null){
				cwsm.closeConnection();
			}
		}
		logger.info("----------		END		----------");
		return rgc;

	}
}
