package it.avlp.simog.common.actions;

import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Collaborazioni;
import it.avlp.simog.beans.CollaborazioniRssa;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.error.SimogWsXmlException;
import it.avlp.simog.massload.xmlbeans.GaraWSDocument;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.common.beans.ResponseInserisciGara;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.GaraLottoManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;

import java.sql.Connection;

import org.apache.log4j.Logger;

public class InserisciGaraActionManager {

	/**
	 * Metodo che permette l'inserimento di una nuova gara
	 * 
	 * @param ticket String alfa-numerica
	 * @param indexCollaborazione String numerica
	 * @param datiGara String xml di tipo GaraType (dovrebbe)
	 * @return ResponseInserisciGara
	 */
	public synchronized static ResponseInserisciGara execute(String ticket, String indexCollaborazione,String datiGara){
		//-------	object declarations		-------//
		ResponseInserisciGara rig = null;
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
			logger.info("eseguendo: ResponseInserisciGara execute(String ticket, String indexCollaborazione,GaraLotto datiGara)");
			rig = new ResponseInserisciGara();
			WsSessions wss = new WsSessions();
			cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
			WSSessionManager wsm = new WSSessionManager(logger,cwsm);
			con = cwsm.getConnection();
			TicketManager tm = new TicketManager();
			SqlTools sqlt = new SqlTools();
			//-------	 begin operations		--------//	
			cwsm.setAutocommit(false);
			wss.setTicket(ticket);
			wss.setComando("InserisciGara");
			wss.setCollaborazione(Integer.parseInt(indexCollaborazione));		
			wss = wsm.selectFindValidSession(wss);
			cwsm.commit(); 
			if(wss != null){
				logger.info(">>>>esiste una sessione associata al ticket");
				try{
					tm.validateRequestedActionByProfile(wss,TicketManager.INSERISCI_GARA);
					if(tm.isValido()){
						logger.info(">>>>utente abilitato al comando richiesto");
						rig.setSuccess(true);
						rig.setError("");					
						//cwsm.setIsolation("t_serialize");
						logger.info(">>>>inserimento della gara e valorizzazione del bean nella response (connnessione settata a transaction serialized)");
						//la validazione viene fatta al caricamento, se non passa la validazione passa direttamente al catch
						GaraLottoManager garaLottoManager = new GaraLottoManager(con,logger);
						GaraWSDocument gara = null;
						Collaborazione col = null;
						CollaborazioniRssa collsRssa = null;
						Collaborazioni colls = null;
						if(!tm.isOperaComeOsservatorio()){		
							col = tm.getCollaborazione();
							colls = tm.getCollaborazioni();
							collsRssa = new CollaborazioniRssa(colls,col);
						}
						
						try{
							
							
							gara = (GaraWSDocument)garaLottoManager.converti(datiGara, wss.getUserId(),col,garaLottoManager.TIPO_GARA);
							
						}catch(SimogWsXmlException swe){
							//validazione stringa xml fallita
							wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							String messaggioErrore = swe.getMessage();
							//setto l'errore nell'oggetto di risposta
//							if(!garaLottoManager.thereIsAnError()){
							InserisciGaraActionManager.setResponseToError(rig, messaggioErrore);
							//In caso il flag sia a true recupero anche il dettagli dell'errore
//							}else{
//								InserisciGaraActionManager.setResponseToError(rig, messaggioErrore + " [" + garaLottoManager.getError()+"]");
//							}
							logger.error("SimogWSException catched: "+messaggioErrore);
							//setto l'errore da scrivere nel db
							wss.setLastError(messaggioErrore);				
							wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							if(wsm.updateSessionAfterOp(wss)){
								logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
								//setto l'errore nell'oggetto di risposta
								InserisciGaraActionManager.setResponseToError(rig, messaggioErrore);
								cwsm.commit();
							}else{
								logger.debug("aggiornamento sessione fallito");
								rig.setError(messaggioErrore+" e' possibile che la sessione non sia piu valida si prega di rieffettuare il login e ripetere l'operazione, controllando i dati");
							}return rig;

						}
						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
						try{
							long id_gara = garaLottoManager.inserisciGara(gara.getGaraWS().getDatiGara(), wss.getUserId(),collsRssa, tm.isOperaComeOsservatorio(), tm.getCodiceRegione());
							//se c'e' stato un'errore lancia l'eccezione passando la stringa contente gli errori del validatore
							if(garaLottoManager.thereIsAnError()){rig.setSuccess(false);throw new SimogWSException(garaLottoManager.getError());}
							//altrimenti
							rig.setId_gara(String.valueOf(id_gara));
							logger.debug("inserimento riuscito");
						}catch(SimogWSException swe){
							swe.printStackTrace();
							logger.debug("inserimento fallito "+swe.getMyMessage());
							if(cwsm != null){
								cwsm.rollback();
							}
							// setto l'errore nell'oggetto di risposta
							String messaggioErrore = swe.getMyMessage();
							InserisciGaraActionManager.setResponseToError(rig, messaggioErrore);
							logger.error("SimogWSException catched: "+messaggioErrore);
							//setto l'errore nell'oggetto che viene scritto sul db
							wss.setLastError(messaggioErrore);				
							wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							if(wsm.updateSessionAfterOp(wss)){
								logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
								// setto l'errore nell'oggetto di risposta
								InserisciGaraActionManager.setResponseToError(rig, messaggioErrore);
								cwsm.commit();
							}else{
								logger.debug("aggiornamento sessione fallito");
							}
							return rig;
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
							// setto l'errore nell'oggetto di risposta
							InserisciGaraActionManager.setResponseToError(rig, messaggioErrore);
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
						InserisciGaraActionManager.setResponseToError(rig, messaggioErrore);
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
			InserisciGaraActionManager.setResponseToError(rig, messaggioErrore);
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
		//logger.info(ObjectIntrospector.propertiesInfo(ResponseInserisciGara.class, rig));
		return rig;
	}
	
	private static void setResponseToError(ResponseInserisciGara response,String errorMsg){
		response.setSuccess(false);
		response.setError(errorMsg);
	}

}
