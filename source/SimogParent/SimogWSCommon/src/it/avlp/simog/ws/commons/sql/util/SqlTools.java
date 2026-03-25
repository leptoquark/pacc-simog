package it.avlp.simog.ws.commons.sql.util;

import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.log4j.Logger;

public class SqlTools {
	//[INCREMENTO TIMELIVE DELLA SESSIONE AD OGNI OPERAZIONE]
	private static final int increaseTime = 5;
	
	/******************************************************************
	 * ottiene il Timestamp relativo alla data attuale
	 * @param con : Connection
	 * @param logger : Logger
	 * @return Timestamp
	 * @throws SimogWSException
	 */
	public Timestamp getDBDate(Connection con,Logger logger)throws SimogWSException{
		logger.debug("eseguendo: getDBDate()");
		try{
			return new AccessiDB(con, logger).getNow();
		}catch(Exception e){
			logger.error("errore recuperando la data da AccessiDB");
			e.printStackTrace();
			throw new SimogWSException(ErrorManager.SIMOGWS_SQLTOOLS_ERR_01);
		}
	}
	
	/********************************************************************
	 * Incrementa la data del valore increaseTime per 
	 * prolungare la durata della sessione 
	 * @param logger : Logger
	 * @param date : Timestamp
	 * @return Timestamp
	 * @throws SimogWSException
	 */
	public Timestamp increseSessionEnd(Logger logger,Timestamp date)throws SimogWSException{
		logger.debug("eseguendo: increseSessionEnd("+date+")");
		try{
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MINUTE, increaseTime);
			return new Timestamp(c.getTimeInMillis());
		}catch(Exception e){
			logger.error("errore durante l'incremento della data: "+e.getMessage());
			e.printStackTrace();
			throw new SimogWSException(ErrorManager.SIMOGWS_SQLTOOLS_ERR_02);
		}
	}
	
	/********************************************************************
	 * Costruisce la query in modo che venga restituito l'id 
	 * in caso di inserimento. 
	 * @param logger : Logger
	 * @param query : String
	 * @param as : String
	 * @return String
	 */
	public String returnIdOnInsert(Logger logger,String query,String as){
		logger.debug("eseguendo: returnIdOnInsert("+query+","+as+")");
		String q = "SET NOCOUNT ON;" + query + ";SELECT SCOPE_IDENTITY() AS " + as;
		return q;
	}
}
