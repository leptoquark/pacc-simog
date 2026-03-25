package it.avlp.simog.actions;

import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.ErrorBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.MessageBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.common.action.BaseSharedAction;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletRichAnnullamento;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.PageHelper;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public abstract class BaseAction {
	
	protected Connection connection;
	protected Logger logger;
	
	public BaseSharedAction bsa;
	
	/******************************************************************************
	 * Action di base
	 * 
	 * @param activeConnection Connection
	 * @param logger Logger
	 */
	protected BaseAction(Connection activeConnection, Logger logger){
		this.connection = activeConnection;
		this.logger = logger;
		this.bsa = new BaseSharedAction(connection, logger) {};
	}
		
	/************************************************************************
	 * Recupera i dati della Gara 
	 * @param request HttpServletRequest
	 * @return InfoGaraBean
	 */
	protected InfoGaraBean getDatiGara(HttpServletRequest request){
		InfoGaraBean infoGara = null;
		try{
			infoGara= (InfoGaraBean) request.getSession(false).getAttribute("dati_gara");
		}catch (Exception e) {
			//nothing here
		}
		return infoGara;
	}

	/***************************************************************************
	 * Invia un messaggio di errore 
	 * @param request HttpServletRequest
	 * @param message String 
	 * @throws IOException
	 * @throws ServletException
	 */
	protected final void sendError ( HttpServletRequest request, String message ) 
		throws IOException, ServletException {
		
		ErrorBean errorBean = new ErrorBean(message);
		request.setAttribute(ParametriServlet.ERRORBEAN, errorBean );
	}
	
	/****************************************************************************
	 * Invia un messaggio
	 * @param request HttpServletRequest
	 * @param message String
	 * @throws IOException
	 * @throws ServletException
	 */
	protected final void sendMessage ( HttpServletRequest request, String message ) 
		throws IOException, ServletException {
	
		MessageBean errorBean = new MessageBean(message);
		request.setAttribute(ParametriServlet.ERRORBEAN, errorBean );
	}
		
	/****************************************************************************
	 * Invia una validazione
	 * @param request HttpServletRequest
	 * @param bean
	 */
	public final void sendValidations ( HttpServletRequest request, AllValidationBeans bean) {
		request.setAttribute(ParametriServlet.ERRORBEAN, bean );
	}	
	
	
	/*******************************************************************************************
	 * Ricava un parametro dalla Request assegandogli un parametro di default se vuoto
	 * @param request HttpServletRequest
	 * @param defaultValue int
	 * @param parameterName String
	 * @return int
	 */
	protected int getIntReqParameter(HttpServletRequest request, int defaultValue, String parameterName){
		int result;
		try{
			String value = request.getParameter(parameterName);
			if(value==null || "".equals(value))
				result=defaultValue;
			else
				result= Integer.parseInt(value);
			return result;
		}catch (Exception e) {
			return defaultValue;
		}
	}
	
	/******************************************************************************************************
	 * Restituisce il valore di un parametro Integer, se vuoto inserisce un valore Integer di default
	 * @param request HttpServletRequest 
	 * @param defaultValue Integer
	 * @param parameterName String
	 * @return Integer
	 */
	protected Integer getIntegerReqParameter(HttpServletRequest request, Integer defaultValue, String parameterName){
		Integer result = null;
		String value = request.getParameter(parameterName);
		if(value==null || "".equals(value))
			result=defaultValue;
		else
			result= new Integer(value);
		return result;
	}
	
	
	
	/**************************************************************************************************
	 * Restituisce il valore di un parametro long contenuto nella request e reperito 
	 * attraverso il nome contenuto in parameterName , se vuoto inserisce un valore 
	 * long di default.
	 * @param request HttpServletRequest 
	 * @param defaultValue long da restituire di default
	 * @param parameterName String contenente il nome del parametro nella request
	 * @return long
	 */
	protected long getLongReqParameter(HttpServletRequest request, long defaultValue, String parameterName){
		long result;
		try{
			String value = request.getParameter(parameterName);
			if(value==null || "".equals(value))
				result=defaultValue;
			else
				result= Long.parseLong(value);
			return result;
		}catch (Exception e) {
			return defaultValue;
		}
	}
	
	protected Long getLongReqParameter(HttpServletRequest request, Long defaultValue, String parameterName){
	   long result = getLongReqParameter(request, -1L, parameterName);
	   return (result == -1L) ? defaultValue : new Long(result);
	}
	

	
	/**************************************************************************************************
	 * Restituisce il valore di un parametro Float contenuto nella request e reperito 
	 * attraverso il nome contenuto in parameterName, se vuoto inserisce un valore Float di default
	 * @param request HttpServletRequest 
	 * @param defaultValue Float
	 * @param parameterName String
	 * @return Float
	 */
	protected float getFloatReqParameter(HttpServletRequest request, float defaultValue, String parameterName){
		float result;
		try{
			String value = request.getParameter(parameterName);
			if(value==null || "".equals(value))
				result=defaultValue;
			else
				result= Float.parseFloat(value);
			return result;
		}catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**************************************************************************************************
	 * Restituisce il valore di un parametro Timestamp contenuto nella request e reperito 
	 * attraverso il nome contenuto in parameterName, se vuoto inserisce un valore Timestamp di default
	 * @param request HttpServletRequest 
	 * @param defaultValue Timestamp
	 * @param parameterName String
	 * @return Timestamp
	 */
	protected Timestamp getTimestampReqParameter(HttpServletRequest request, Timestamp defaultValue, String parameterName){
		Timestamp result;
		try{
			String value = request.getParameter(parameterName);
			if(value==null || "".equals(value))
				result=defaultValue;
			else
				result= PageHelper.parseTime(value);
			return result;
		}catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**************************************************************************************************
	 * Restituisce il valore di un parametro String contenuto nella request e reperito 
	 * attraverso il nome contenuto in parameterName, se vuoto inserisce un valore String di default
	 * @param request HttpServletRequest 
	 * @param defaultValue String
	 * @param parameterName String
	 * @return String
	 */
	protected String getStringReqParameter(HttpServletRequest request, String defaultValue, String parameterName){
		
		String result="";
		String value = request.getParameter(parameterName);

		if(value == null || "".equals(value))
			result = defaultValue;
		else
			result = value; 
		return result;
	}
	/**************************************************************************************************
	 * Restituisce il valore di un parametro BigDecimal contenuto nella request e reperito 
	 * attraverso il nome contenuto in parameterName, se vuoto inserisce un valore BigDecimal di default
	 * @param request HttpServletRequest 
	 * @param defaultValue BigDecimal
	 * @param parameterName String
	 * @return BigDecimal
	 */
	protected BigDecimal getBigDecimalReqParameter(HttpServletRequest request, BigDecimal defaultValue, String parameterName){
		try{
			BigDecimal result=null;
			String value = request.getParameter(parameterName);
			if(value == null || "".equals(value))
				result = defaultValue;
			else
				result = new BigDecimal(PageHelper.formattaImporto(value)); 
			return result;
		}catch (Exception e) {
			return defaultValue;
		}		
	}
}
