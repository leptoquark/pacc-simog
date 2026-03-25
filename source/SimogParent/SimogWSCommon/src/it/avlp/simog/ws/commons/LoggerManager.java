package it.avlp.simog.ws.commons;


import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
/*	SingleTon	*/
/**
 * Class designed to load application logger
 **/
public class LoggerManager {

	private static LoggerManager lm = null;
//	private static String SimogLog4jConfigName = "C:\\ANAC\\software\\eclipse-workspace_ANAC\\3.04.8.1\\Configurazioni\\simog_log4j.properties";
//	private static String SimogLog4jConfigName = "C:\\Users\\Fe.Lattanzi\\Documents\\SVN\\Configurazioni\\simog_log4j.properties";
	private static String SimogLog4jConfigName = "/opt/SIMOG/simogWS_log4j.properties"; 
	
	private static Logger logger = null;
	/*	private contructor	*/
	
	/***************************************************
	 * Costruttore privato della classe 
	 */
	private LoggerManager(){
		try{
//			HttpServlet servlet =(HttpServlet) MessageContext.getCurrentContext().getProperty(HTTPConstants.MC_HTTP_SERVLET);	

			//BufferedInputStream in = new BufferedInputStream ( servlet.getServletContext().getResource( SimogLog4jConfigName ).openStream() );

			FileInputStream in = new FileInputStream ( SimogLog4jConfigName) ;
			
			Properties log4jProp = new Properties ();
			log4jProp.load( in );
			PropertyConfigurator.configure( log4jProp );
			in.close();
			logger = Logger.getLogger("SIMOG_LOGGER");
			//logger.debug("URL: "+l4jp.toString());
		/*	END	*/
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/* contructor caller if needed	*/
	/****************************************************
	 * Metodi statico synchronized per istanziare LoggerManager in maniera Singleton
	 * @return LoggerManager
	 */
	public synchronized static LoggerManager getInstance(){
		if(lm==null){
			lm = new LoggerManager();
		}
		return lm;
	}
	/*	return configured logger	*/
	public Logger getLogger(){
		return logger;
	}
}
