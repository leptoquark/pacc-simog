package test.manager;

import it.avlp.simog.massload.MassLoaderProperties;
import it.avlp.simog.massload.manager.DbManager;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public abstract class SuperTest {

//	private String pathBase = "c:/TEST/AZIENDA";
//	private String nomeFile = "Scheda.xml";
	private String nomePathConf = "C:/TEST/MassLoaderEnv/conf";
	private String nomeFileConf = "massloader.properties";
	
	protected static MassLoaderProperties configuration = null;
	protected static Logger logger = null; 
	protected static DbManager dbm;
	
	public void init(String nomePathConf, String nomeFileConf) throws Exception{
		
		try {
			PropertyConfigurator.configure(nomePathConf + "/massloader.log4j.properties" );
			logger = Logger.getLogger("MASSLOADER_LOGGER");
			logger.debug("LOGGER applicativo inizializzato correttamente");

			configuration = new MassLoaderProperties(nomePathConf+"/"+nomeFileConf, logger);
		} catch (Exception e) {
			throw new Exception(e);
		}										
	}
	
	public void initAll(){
		try {
			init(nomePathConf, nomeFileConf);
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
			System.exit(0);	
		}
		
		
		// init connessione db
		try {
			dbm = new DbManager(logger,configuration);
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
			System.exit(0);	
		}
	}
	
	public long getLong(int value){
		return new Long(value).longValue();
	}
	
	public Timestamp getDate(String data){
		
		String DEFAULT_DATE_TIME_FORMAT = "dd/MM/yyyy HH.mm.ss";
	 	try {    
	 		DateFormat formatter ; 
	        Date date ; 
	        formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
	        date = (Date)formatter.parse(data);  
	        return new Timestamp(date.getTime());
	        
	    } catch (ParseException e){
	    	System.out.println("Exception :"+e);
	    	System.exit(0);
	    	return null;
	    } 
	}


}
