package it.avlp.simog.ws.commons;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.util.SimogProperties;
/*	SingleTon	*/
/**
 * Class designed to load application properties
 **/
public class ConfigurationManager {
	
	private static String LocalConfigName = "C:\\home\\SIMOG\\simog.ini"; // PP"/../simog.ini"; NON COMMITTARE
	private static String SimogConfigName = "/opt/SIMOG/simog.ini"; 
//	private static String LocalConfigName = "C:\\ANAC\\software\\eclipse-workspace_ANAC\\3.04.8.1\\Configurazioni\\simog.ini"  ; // PP"/../simog.ini"; ""
//	private static String SimogConfigName = "/opt/SIMOG/simog.ini"; 
	


	
	
	private static Logger logger = null;
	private static ConfigurationManager confM = null;
	private static SimogProperties sp;
	
	/*	private contructor	*/
	  
	/****************************************************
	 * Costruttore privato
	 * @throws SimogWSException
	 */
	private ConfigurationManager(){}
	
	/* contructor caller if needed	*/
	
	/**************************************************************************************
	 * Metodo statico synchronized per la generazione del ConfigurationManager come Singleton
	 * @return ConfigurationManager
	 * @throws SimogWSException
	 */
	public synchronized static ConfigurationManager getInstance()throws SimogWSException{
		if(confM==null){
			confM = new ConfigurationManager();			

		     if (logger == null)
	            logger = LoggerManager.getInstance().getLogger();
	         
	      logger.debug("eseguendo: ConfigurationManager()_VERS1");
	      
	      //Se il percorso non esiste significa che si stanno facendo i test in ambiente di sviluppo locale
	      if(!new File(SimogConfigName).exists())
	    	  sp = SimogProperties.createInstance(LocalConfigName, logger);
          else
	          sp = SimogProperties.createInstance(SimogConfigName, logger);
	      
//	      }catch(SimogException se){
//	         logger.fatal("errore durante l'instanziazione di SimogProperties: "+se.getMessage());
//	         se.printStackTrace();
//	         throw new SimogWSException(ErrorManager.SIMOGWS_CONFIGURATIONMANAGER_APP_01); 
//	      }catch(Exception swse){
//	         swse.printStackTrace();
//	         throw new SimogWSException(ErrorManager.SIMOGWS_CONFIGURATIONMANAGER_APP_02);
//	      }
		}		

	   return confM;
	}
	/*	return loaded Object SimogProperties	*/
	
	
	public SimogProperties getSimogProperties(){
		return sp;
	}
	
//  static void setProperties() throws SimogWSException{
//   try{
////  URL smgp = getClass().getResource( SimogConfigName  );
////  BufferedInputStream in = new BufferedInputStream ( smgp.openStream() );
////  sp = new SimogProperties(new BufferedInputStream( in ) , logger);
//
////  HttpServlet servlet =(HttpServlet) MessageContext.getCurrentContext().getProperty(HTTPConstants.MC_HTTP_SERVLET); 
//
//// sp = new SimogProperties(servlet.getServletContext().getRealPath(SimogConfigName), logger);
//      sp = new SimogProperties(SimogConfigName, logger);
//   
//   }catch(SimogException se){
//      logger.fatal("errore durante l'instanziazione di SimogProperties: "+se.getMessage());
//      se.printStackTrace();
//      throw new SimogWSException(ErrorManager.SIMOGWS_CONFIGURATIONMANAGER_APP_01); 
//   }catch(Exception swse){
//      swse.printStackTrace();
//      throw new SimogWSException(ErrorManager.SIMOGWS_CONFIGURATIONMANAGER_APP_02);
//   }
//
//}
	

   public String buildVersion(String modulo, String filePath){
      String retVal = "";
      
      Properties version = new Properties();
      try {
        
        version.load(this.getClass().getResourceAsStream(filePath));
     } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return retVal;
     }
      
      retVal = "$0 - Versione $1.$2.$5 ($3) ($4)"
                  .replace("$0", modulo)
                  .replace("$1", version.getProperty("version.number"))
                  .replace("$2", version.getProperty("major.number"))
                  .replace("$5", version.getProperty("minor.number"))
                  .replace("$3", version.getProperty("build.number"))
                  .replace("$4", version.getProperty("build.date"));
      
      return retVal;
   }

   public synchronized static ConfigurationManager getInstance(Logger logger2)throws SimogWSException {
      if(confM==null){
         confM = new ConfigurationManager();       
         
         logger = logger2;
      
//         try{
            logger.debug("eseguendo: ConfigurationManager()_VERS2");
            if(new File(SimogConfigName).exists())
              sp = SimogProperties.createInstance(SimogConfigName, logger);
            else
              sp = SimogProperties.createInstance(LocalConfigName, logger);
//         }
//         catch(SimogException se){
//            logger.fatal("errore durante l'instanziazione di SimogProperties: "+se.getMessage());
//            se.printStackTrace();
//            throw new SimogWSException(ErrorManager.SIMOGWS_CONFIGURATIONMANAGER_APP_01); 
//         }catch(Exception swse){
//            swse.printStackTrace();
//            throw new SimogWSException(ErrorManager.SIMOGWS_CONFIGURATIONMANAGER_APP_02);
//         }
      }   

      return confM;
   }   
}
