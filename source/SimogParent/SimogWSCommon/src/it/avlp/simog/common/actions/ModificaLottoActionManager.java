package it.avlp.simog.common.actions;

import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Collaborazioni;
import it.avlp.simog.beans.CollaborazioniRssa;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.beans.ResponseModificaLotto;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.error.SimogWsXmlException;
import it.avlp.simog.massload.xmlbeans.LottoWSDocument;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.GaraLottoManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;

import java.sql.Connection;

import org.apache.log4j.Logger;

public class ModificaLottoActionManager {
	
	/***********************************************************************************
	 * Esegue la modifica del Lotto
	 * @param ticket : String
	 * @param indexCollaborazione : String
	 * @param datiGara : String
	 * @param cig :String
	 * @return
	 */	
	public synchronized static ResponseModificaLotto execute(String ticket, String indexCollaborazione,String datiGara,String cig){
		//-------	object declarations		-------//
		ResponseModificaLotto rgc = null;
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
			logger.info("eseguendo: ResponseModificaLotto execute(String ticket, String indexCollaborazione,GaraLotto datiGara)");
			//logger.debug("DATIGARA: " + datiGara);
			rgc=new ResponseModificaLotto();
			WsSessions wss = new WsSessions();
			cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
			WSSessionManager wsm = new WSSessionManager(logger,cwsm);
			con = cwsm.getConnection();
			TicketManager tm = new TicketManager();
			SqlTools sqlt = new SqlTools();
			//-------	 begin operations		--------//	
			cwsm.setAutocommit(false);
			wss.setTicket(ticket);
			wss.setComando("ModificaLotto");
			wss.setCollaborazione(Integer.parseInt(indexCollaborazione));		
			wss = wsm.selectFindValidSession(wss);
			cwsm.commit();
			if(wss != null){
				logger.info(">>>>esiste una sessione associata al ticket");
				try{
					tm.validateRequestedActionByProfile(wss, TicketManager.MODIFICA_LOTTO);
					if(tm.isValido()){
						logger.info(">>>>utente abilitato al comando richiesto");
						rgc.setSuccess(true);
						rgc.setError("");					
						//cwsm.setIsolation("t_serialize");
						logger.info(">>>> (connnessione settata a transaction serialized)");
						//la validazione viene fatta al caricamento, se non passa la validazione passa direttamente al catch
						GaraLottoManager garaLottoManager = new GaraLottoManager(con,logger);
						LottoWSDocument lotto = null;
						try{
							Collaborazione coll = null;
							if(!tm.isOperaComeOsservatorio()){
								coll = tm.getCollaborazione();
							}
							lotto = (LottoWSDocument)garaLottoManager.converti(datiGara, wss.getUserId(),coll,garaLottoManager.TIPO_LOTTO);
						}catch(SimogWsXmlException swe){
							//validazione stringa xml fallita
							wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							rgc.setSuccess(false);
							String messaggioErrore = swe.getMessage();
//							if(!garaLottoManager.thereIsAnError()){
							rgc.setError(messaggioErrore);
//							}else{
//								rgc.setError(messaggioErrore + " [" + garaLottoManager.getError()+"]");
//							}
							logger.error("SimogWSException catched: "+messaggioErrore);
							wss.setLastError(messaggioErrore);				
							wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							if(wsm.updateSessionAfterOp(wss)){
								logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
								rgc.setSuccess(false);
								rgc.setError(messaggioErrore);
								cwsm.commit();
							}else{
								//nel messaggio d'errore si prega di rieffettuare il login
								logger.error("aggiornamento sessione fallito");
								rgc.setError(messaggioErrore+" e' possibile che la sessione non sia piu valida si prega di rieffettuare il login e ripetere l'operazione, controllando i dati");
							}return rgc;
						}
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
							
	                  boolean esito = garaLottoManager.modificaLotto(lotto, wss.getUserId(),collsRssa,cig, tm.isOperaComeOsservatorio());

	                  if(SimogFlags.is3031_RFWEBGL02Active()
	                        && SimogProperties.getInstance().isCUPAttivo()
	                        ){
                        // imposto i dati dipe per la response
                        if(garaLottoManager.getCuplotto()!=null)
                           rgc.setCUPLOTTO(garaLottoManager.getCuplotto());
                     }

	                  if(esito){
								rgc.setSuccess(true);
								rgc.setMessaggio("operazione effettuata correttamente");
							}else{
								throw new SimogWSException(garaLottoManager.getError());
							}
							logger.info("modifica riuscito");
						}catch(SimogWSException swe){
							logger.error("modifica fallita "+swe.getMyMessage());
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
		catch(Exception swe){
		   swe.printStackTrace();
         if(cwsm != null){
            cwsm.rollback();
         }
         rgc.setSuccess(false);
         rgc.setError(swe.getMessage());
         logger.error("Exception catched: "+swe.getMessage());
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
