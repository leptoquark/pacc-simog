package it.avlp.simog.common.actions;

import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Collaborazioni;
import it.avlp.simog.beans.CollaborazioniRssa;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.beans.ResponsePerfezionaGara;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.GaraLottoManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;

import java.sql.Connection;

import org.apache.log4j.Logger;

public class PerfezionaGaraActionManager {

	public static synchronized ResponsePerfezionaGara execute(String ticket, String indexCollaborazione,String id_gara) {
		//-------	object declarations		-------//
		ResponsePerfezionaGara rig = null;
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
			logger.info("eseguendo: Response execute(String ticket, String indexCollaborazione,String oggettoGara,String importoGara,String id_gara)");
			rig = new ResponsePerfezionaGara();
			WsSessions wss = new WsSessions();
			cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
			WSSessionManager wsm = new WSSessionManager(logger,cwsm);
			con = cwsm.getConnection();
			TicketManager tm = new TicketManager();
			SqlTools sqlt = new SqlTools();
			//-------	 begin operations		--------//	
			cwsm.setAutocommit(false);
			wss.setTicket(ticket);
			wss.setComando("PerfezionaGara");
			wss.setCollaborazione(Integer.parseInt(indexCollaborazione));		
			wss = wsm.selectFindValidSession(wss);
			cwsm.commit();
			if(wss != null){
				logger.info(">>>>esiste una sessione associata al ticket");
				try{
					boolean esito;
                    // funzione disabilitata dopo il 1/1/2013
                    if(SimogFlags.is3024_NOPERFActive() && Costanti.DATA_NOPERF.compareTo(PageHelper.getCurrentDate())<=0)
                       tm.validateRequestedActionByProfile(wss,999);
                    else
                       tm.validateRequestedActionByProfile(wss,TicketManager.PERFEZIONA_GARA);
					
					if(tm.isValido()){
						logger.info(">>>>utente abilitato al comando richiesto");
						rig.setSuccess(true);
						rig.setError("");					
						//cwsm.setIsolation("t_serialize");
						logger.info(">>>>generazione cig e valorizzazione del bean nella response (connnessione settata a transaction serialized)");
						//la validazione viene fatta al caricamento, se non passa la validazione passa direttamente al catch
												
						GaraLottoManager garaLottoManager = new GaraLottoManager(con,logger);
						try{
//							Collaborazione col = null;
//							if(!indexCollaborazione.equals("")){	col = tm.getCollaborazione();	}
							Collaborazione col = null;
							CollaborazioniRssa collsRssa = null;
							Collaborazioni colls = null;
							if(!tm.isOperaComeOsservatorio()){		
								col = tm.getCollaborazione();
								colls = tm.getCollaborazioni();
								collsRssa = new CollaborazioniRssa(colls,col);
							}
							esito = garaLottoManager.perfezionaGara(wss.getUserId(), collsRssa, id_gara, tm.isOperaComeOsservatorio());
							
							if(!esito){ throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_08 + " - "+garaLottoManager.getError());}
							else{
								rig.setSuccess(esito);
								rig.setMessaggio("Operazione completata correttamente");
							}
						}catch(SimogWSException swe){
							//validazione stringa xml fallita
							wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							String messaggioErrore = swe.getMyMessage();
							//setto l'errore nell'oggetto di risposta
							PerfezionaGaraActionManager.setResponseToError(rig, messaggioErrore);
							logger.error("SimogWSException catched: "+messaggioErrore);
							//setto l'errore da scrivere nel db
							wss.setLastError(messaggioErrore);				
							wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							if(wsm.updateSessionAfterOp(wss)){
								logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
								//setto l'errore nell'oggetto di risposta
								PerfezionaGaraActionManager.setResponseToError(rig, messaggioErrore); 
								cwsm.commit();
							}else{
								logger.debug("aggiornamento sessione fallito");
								rig.setError(messaggioErrore+" e' possibile che la sessione non sia piu valida si prega di rieffettuare il login e ripetere l'operazione, controllando i dati");
							}return rig;

						}
						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
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
							// setto l'errore nell'oggetto di risposta
							PerfezionaGaraActionManager.setResponseToError(rig, messaggioErrore);
						}			
					}
				//caso in cui l'indice passato non sia valido
				}catch(SimogWSException swe){
					logger.error("indice collaborazione non valido");
					String messaggioErrore = swe.getMyMessage();
					wss.setLastError("collaborazione ["+wss.getCollaborazione()+"] non esiste");				
					wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
					if(wsm.updateSessionAfterOp(wss)){
						logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
						// setto l'errore nell'oggetto di risposta
						PerfezionaGaraActionManager.setResponseToError(rig, messaggioErrore);
						cwsm.commit();
					}
					return rig;
				}
			}		
		}catch(SimogWSException swe){
			if(cwsm != null){
				cwsm.rollback();
			}
			// setto l'errore nell'oggetto di risposta
			String messaggioErrore = swe.getMyMessage();
			PerfezionaGaraActionManager.setResponseToError(rig, messaggioErrore);
			logger.error("SimogWSException catched: "+messaggioErrore);
		}catch(Throwable t){
			t.printStackTrace();
		}
		finally{
			if(cwsm != null){
				cwsm.closeConnection();
			}
		}
		logger.info("----------		END		----------");
		return rig;		
	}
	private static void setResponseToError(ResponsePerfezionaGara response,String errorMsg){
		response.setSuccess(false);
		response.setError(errorMsg);
	}
}
