package it.avlp.simog.common.actions;

import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.beans.ResponseGeneraCig;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.ws.commons.CigManager;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;

import java.sql.Connection;

import org.apache.log4j.Logger;

/*
 * Metodo per l'esecuzione della chiusura della sessione
 * ritorna una:
 *  ResponseGeneraCig
 *  - success (boolean)
 *  - error (not empty if success = false)
 *  - CIGBean (not null if success 0 true)
 *  	- string cig
 *  	- int cigCicle
 *  	- String cigKKK	
 *  
 */

/**
 * @author vletizia
 * @deprecated
 *
 */
public class GeneraCigActionManager {
	

	/****************************************************************************
	 * Genera il ResponseGeneraCig inserendovi il valore di CIG apportuno
	 * @param ticket : String
	 * @param indexCollaborazione : String
	 * @return ResponseGeneraCig
	 */
	public synchronized static ResponseGeneraCig execute(String ticket, String indexCollaborazione){
		//-------	object declarations		-------//
		ResponseGeneraCig rgc = null;
		Logger logger = null;
		ConnectionWSManager cwsm = null;
		Connection con = null;
		try{
			logger = LoggerManager.getInstance().getLogger();
			logger.info("-----------	begin  	---------------");
			logger.info("eseguendo: ResponseGeneraCig execute(String ticket, String indexCollaborazione)");
			rgc=new ResponseGeneraCig();
			WsSessions wss = new WsSessions();
			cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
			WSSessionManager wsm = new WSSessionManager(logger,cwsm);
			con = cwsm.getConnection();
			TicketManager tm = new TicketManager();
			SqlTools sqlt = new SqlTools();
			CigManager cm = new CigManager();
			//-------	 begin operations		--------//	
			cwsm.setAutocommit(false);
			wss.setTicket(ticket);
			wss.setComando("GeneraCIG");
			wss.setCollaborazione(Integer.parseInt(indexCollaborazione));		
			wss = wsm.selectFindValidSession(wss);
			cwsm.commit();
			if(wss != null){
				logger.info(">>>>esiste una sessione associata al ticket");
				try{
					tm.validateRequestedActionByProfile(wss,TicketManager.GENERA_CIG);
					if(tm.isValido()){
						logger.info(">>>>utente abilitato al comando richiesto");
						rgc.setSuccess(true);
						rgc.setError("");					
						cwsm.setIsolation("t_serialize");
						logger.info(">>>>generazione cig e set del bean nella response (connnessione settata a transaction serialized)");
						rgc.setCig(cm.generate(con,wss.getUserId(),tm.getCollaborazione(), tm.getAdminOr()));
						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
						cwsm.setIsolation("t_read_committed");
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
						logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
						rgc.setSuccess(false);
						rgc.setError(messaggioErrore);
						cwsm.commit();
					}
					throw new SimogWSException(swe.getMessage());
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
