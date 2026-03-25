package it.avlp.simog.common.actions;

import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.beans.ResponsePerfezionaLotto;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.GaraLottoManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;

import java.sql.Connection;

import org.apache.log4j.Logger;

public class PerfezionaLottoActionManager {

	/******************************************************************************
	 * effettua il perfezionamento del lotto
	 * @param ticket : String
	 * @param indexCollaborazione : String
 	 * @param dataPubblicazione : String 
	 * @param dataScadenzaPagamenti : String 
	 * @param cig : String
	 * @return ResponsePerfezionaLotto
	 */
	public synchronized static ResponsePerfezionaLotto execute(String ticket, String indexCollaborazione,
	      String dataPubblicazione,String dataScadenzaPagamenti,String cig, String oraScadenza){
		//-------	object declarations		-------//
		ResponsePerfezionaLotto rgc = null;
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
			logger.info("eseguendo: ResponsePerfezionaLotto execute(String ticket, String indexCollaborazione,String datiGara,String cig)");
			rgc = new ResponsePerfezionaLotto();
			WsSessions wss = new WsSessions();
			cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
			WSSessionManager wsm = new WSSessionManager(logger,cwsm);
			con = cwsm.getConnection();
			TicketManager tm = new TicketManager();
			SqlTools sqlt = new SqlTools();
			//-------	 begin operations		--------//	
			cwsm.setAutocommit(false);
			wss.setTicket(ticket);
			wss.setComando("PerfezionaLotto");
			wss.setCollaborazione(Integer.parseInt(indexCollaborazione));		
			wss = wsm.selectFindValidSession(wss);
			cwsm.commit();
			if(wss != null){
				logger.info(">>>>esiste una sessione associata al ticket");
				try{
                   // funzione disabilitata dopo il 1/1/2013
                   if(SimogFlags.is3024_NOPERFActive() && Costanti.DATA_NOPERF.compareTo(PageHelper.getCurrentDate())<=0)
                      tm.validateRequestedActionByProfile(wss,999);
                   else
                      tm.validateRequestedActionByProfile(wss,TicketManager.PERFEZIONA_LOTTO);
                      
					if(tm.isValido()){
						logger.info(">>>>utente abilitato al comando richiesto");
						rgc.setSuccess(true);
						rgc.setError("");					
						//cwsm.setIsolation("t_serialize");
						logger.info(">>>> (connnessione settata a transaction serialized)");
						//la validazione viene fatta al caricamento, se non passa la validazione passa direttamente al catch
						GaraLottoManager garaLottoManager = new GaraLottoManager(con,logger);
						//LottoWSDocument lotto = (LottoWSDocument)garaLottoManager.converti(datiLotto, wss.getUserId(),tm.getCollaborazione(),"LottoType");
						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
						try{
							Collaborazione coll = null;
							if(!tm.isOperaComeOsservatorio()){	
								coll = tm.getCollaborazione();
							}
							if(garaLottoManager.perfezionaLotto(dataPubblicazione, dataScadenzaPagamenti, 
							      wss.getUserId(),coll,cig, tm.isOperaComeOsservatorio(), oraScadenza)){
								rgc.setSuccess(true);
								rgc.setMessaggio("operazione effettuata correttamente");
							}else{
								throw new SimogWSException(garaLottoManager.getError());
							}
							logger.info("perfezionamento riuscito");
						}catch(SimogWSException swe){
							logger.error("perfezionamento fallito "+swe.getMyMessage());
							if(cwsm != null){
								cwsm.rollback();
							}
							rgc.setSuccess(false);
							rgc.setError(swe.getMyMessage());
							logger.error("SimogWSException catched: "+swe.getMyMessage());
							String messaggioErrore = swe.getMyMessage();
							wss.setLastError(messaggioErrore);				
							wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							if(wsm.updateSessionAfterOp(wss)){
								logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
								rgc.setSuccess(false);
								rgc.setError(messaggioErrore);
								cwsm.commit();
							}else{
								logger.error("aggiornamento sessione fallito");
								rgc.setError(messaggioErrore+" e' possibile che la sessione non sia piu valida si prega di rieffettuare il login e ripetere l'operazione, controllando i dati");
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
		}catch(Throwable t){t.printStackTrace();}
		finally{
			if(cwsm != null){
				cwsm.closeConnection();
			}
		}
		logger.info("----------		END		----------");
		return rgc;

	}
}
