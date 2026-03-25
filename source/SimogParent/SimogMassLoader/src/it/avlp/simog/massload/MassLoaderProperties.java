package it.avlp.simog.massload;

import it.avlp.simog.exception.SimogException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class MassLoaderProperties extends Properties {
	
	private static final long serialVersionUID = 1L;
	
	private final static String JDBC_DRIVER = "JDBC_DRIVER";		
	private final static String JDBC_STRING = "JDBC_STRING";
	private final static String LOG_CONFIG_FILENAME = "LOG_CONFIG_FILENAME";
	public final static String USERNAME = "USERNAME";
	public final static String DEF_USERNAME = "MASSLOADER";
	
	private Logger logger = null;

	private String fileConfigName = null;

	
	static final float VERSIONE_PRG = (float)1.0;

	
	/**
	 * Inserire qui la descrizione del metodo.
	 * Data di creazione: (23/01/2004 12.19.34)
	 */
	
	public MassLoaderProperties( String fileConfigName, Logger logger ) throws SimogException {
		this.logger = logger;
		this.fileConfigName  = fileConfigName;
		reload(fileConfigName);
	}
	
	public Object put (Object chiave, Object valore) {
		logger.debug("Chiave [" + chiave + "] valore [" + valore + "]");
		return super.put(chiave, valore);
	}
	
	public void reload(String path) throws SimogException {
		
		FileInputStream fis = null;
		String buffer = "";

		try {
			fis = new FileInputStream(fileConfigName);
			load(fis);
		} catch (IOException ioe) {
			String message = "Errore nella lettura dei parametri iniziali"; 
			logger.fatal ( message, ioe );
			throw new SimogException ( message ); 
		} finally {
			try {
				if(fis != null)	fis.close();
			} catch (IOException io) {}
		}	
	}
	
	public String getProperty(String param) {
		String paramValue = super.getProperty(param);
		if ( paramValue == null ) {
			logger.info("Parametro [" + param + "] non inizializzato");
		}
		//logger.debug("getting Param [" + param + "] value [" + paramValue + "]");
		return paramValue;
	}
	
	/**
	 * @return Returns the dB_Driver.
	 */
	public String getJDBCDriver() {
		return getProperty(JDBC_DRIVER);
	}
	
	/**
	 * @return Returns username
	 */
	public String getUsername() {
		return getProperty(USERNAME);
	}

	/**
	 * @return Returns the JDBC String Connection.
	 */
	public String getJDBCString() {
		return getProperty(JDBC_STRING);
	}
	
	/**
	 * @param logger The logger to set.
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
