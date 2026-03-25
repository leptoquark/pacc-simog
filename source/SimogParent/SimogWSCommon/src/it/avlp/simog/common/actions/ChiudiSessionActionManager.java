package it.avlp.simog.common.actions;

import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.beans.ResponseChiudiSession;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;

import org.apache.log4j.Logger;


	/**
	 * 
	 * Classe che si occupa della gestione della chiusura della sessione
	 * vers 0.1
	 * 
	 * **/
public class ChiudiSessionActionManager {
	
	/**
	 * Metodo per l'esecuzione della chiusura della sessione
	 * ritorna una:
	 *  ResponseChiudiSession
	 *  - success (boolean)
	 *  - error (not empty if success = false)
	 *  - messaggio (not empty if success 0 true)
	 *  
	 * **/
	public synchronized static ResponseChiudiSession execute(String ticket){
		//-------	object declarations		-------//
		ResponseChiudiSession rgc = null;
		Logger logger = null;
		ConnectionWSManager cwsm = null;
		try{
			logger = LoggerManager.getInstance().getLogger();
			logger.info("-----------	begin 	---------------");
			logger.info("eseguendo: ResponseChiudiSession execute(String ticket)");
			rgc=new ResponseChiudiSession();
			WsSessions wss = new WsSessions();
			cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
			WSSessionManager wsm = new WSSessionManager(logger,cwsm);
			//-------	 begin operations		--------//
			cwsm.setAutocommit(false);
			wss.setTicket(ticket);
			wss.setComando("ChiudiSessione");
			wss = wsm.selectFindValidSession(wss);
			if(wss != null){
				logger.info(">>>esiste una sessione associata al ticket");
				if(wsm.updateSessionUnvalidateFromTicket(wss)){
					cwsm.commit();
					logger.info(">>>aggiornamento db effettuato correttamente");
					rgc.setSuccess(true);
					rgc.setMessaggio("operazione completata correttamente");
					rgc.setError("");
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
