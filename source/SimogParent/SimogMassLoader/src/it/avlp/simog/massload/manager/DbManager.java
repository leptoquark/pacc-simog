package it.avlp.simog.massload.manager;


import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.massload.MassLoaderProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
/**  
 *  Classe che si occupa delle operazioni sql di base
 *  come gestione della connessione
 **/
public class DbManager extends AccessiDB{
	
	private Connection currentActiveConnection = null;
	private Logger logger = null;
	private MassLoaderProperties config = null;
	
	/**
	 * Costruttore, carica i dati necessari alla connessione sql
	 * e il logger
	 * 
	 * @param logger
	 * @param configuration
	 * @throws SimogException
	 */
	public DbManager(Logger logger, MassLoaderProperties configuration) throws SimogException {
		super();
		
		super.logger = logger;

		this.logger = logger;
		this.config = configuration;
		
//		if(!Main.enableValidateOnly){
			logger.debug ( "Caricamento in corso del driver [" + configuration.getJDBCDriver() + "]" );
		
			boolean loadedODBC=false;
			try {
				Class.forName(configuration.getJDBCDriver());			
				// PP non serve ? Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				
				loadedODBC=true;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		
			try {
				currentActiveConnection = DriverManager.getConnection(configuration.getJDBCString());
				loadedODBC=true;
			} catch (SQLException e) {
				e.printStackTrace();
			}		
	
			if (!loadedODBC)
				throw new SimogException("Errore durante caricamento drivers database");
	
			super.activeConnection = currentActiveConnection;
//		}else{
//			logger.debug("La connessione non e' stata inizializzata, perche' non e' richiesta in questa modalita'");
//		}
	}

	/**
	 * Metodo che si occupa del tentativo di commit, effettua il roolback in caso
	 * di fallimento
	 * 
	 * @throws SimogException
	 */
	
//	protected void rollbackOrCommit() throws SimogException {
//		try {
//			currentActiveConnection.commit();
//			logger.debug ( "Completata commit" );
//		} catch(SQLException sqlCommit) {
//			
//			try {
//				currentActiveConnection.rollback();
//			} catch(SQLException sqlRollback) {
//				throw new SimogException("Errore durante l'operazione di rollback", sqlRollback);
//			}
//			throw new SimogException("Errore durante l'operazione di commit", sqlCommit);
//		}
//	}
	
	/**
	 * metodo per il roolback delle operazioni
	 * 
	 * @throws SimogException
	 */
	protected void rollback() throws SimogException {
			try {
				currentActiveConnection.rollback();
			} catch(SQLException sqlRollback) {
				throw new SimogException("Errore durante l'operazione di rollback", sqlRollback);
			}
	}
	


	
	/**
	 * metodo per il commit delle operazioni
	 * 
	 * @throws SimogException
	 */
//	protected void commit() throws SimogException {
//		try {
//			currentActiveConnection.commit();
//		} catch(SQLException sqlRollback) {
//			throw new SimogException("Errore durante l'operazione di rollback", sqlRollback);
//		}
//	}

	/**
	 * metodo per la chiusura della connessione
	 */
	public void closeConnection () {
		try {
			currentActiveConnection.close();
			//decreaseCounter();
			logger.debug("Chiusa connessione");
		} catch ( Exception e ) {}
		currentActiveConnection = null;
	}
	/**
	 * Metodo che chiude e riapre la connessione
	 */
	public void reNewConnection()throws SimogException{
		boolean loadedODBC=false;
		try {
			logger.info("Sto chiudendo la Connesione: " + currentActiveConnection.toString());
			this.closeConnection();
			currentActiveConnection = DriverManager.getConnection(this.config.getJDBCString());
			logger.info("Ottenuta Connesione: " + currentActiveConnection.toString());
			loadedODBC=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}		

		if (!loadedODBC)
			throw new SimogException("Errore durante caricamento drivers database");

		super.activeConnection = currentActiveConnection;		
	}
	/**
	 * Metodo per il recupero della connessione locale attiva
	 * 
	 * @return Connection
	 */
	public Connection getCurrentActiveConnection() {
		return currentActiveConnection;
	}

	public MassLoaderProperties getConfig() {
		return config;
	}
	
	public static void staticRollback(Connection currentActiveConnection, Logger logger, String invoker, String connectionId){
		logger.info(constructInvoker(invoker) + constructConnectionId(connectionId) + "Mi accingo ad effettuare il rollback delle operazioni effettuate sulla connessione");
		try {
			if(currentActiveConnection != null){
				currentActiveConnection.rollback();
				logger.info(constructInvoker(invoker) + constructConnectionId(connectionId) + "Rollback effettuato con successo");
			}
		} catch(SQLException sqle) {
			logger.fatal(constructInvoker(invoker) + constructConnectionId(connectionId) + "Tentativo di Rollback fallito",sqle);
		}
	}

	public static void staticCommit(Connection currentActiveConnection, Logger logger, String invoker, String connectionId){
		logger.info(constructInvoker(invoker) + constructConnectionId(connectionId) + "Mi accingo ad effettuare il commit delle operazioni effettuate sulla connessione");
		try {
			if(currentActiveConnection != null){

			
			   // solo per debug currentActiveConnection.rollback();
			   currentActiveConnection.commit();
				logger.info(constructInvoker(invoker) + constructConnectionId(connectionId) + "Commit effettuato con successo");
			}
		} catch(SQLException sqle) {
			logger.fatal(constructInvoker(invoker) + constructConnectionId(connectionId) + "Tentativo di commit fallito",sqle);
		}
	}
	
	public static void staticSetAutoCommitFalse(Connection  currentActiveConnection, Logger logger, String invoker, String connectionId){
		logger.info(constructInvoker(invoker) + constructConnectionId(connectionId) + "Mi accingo a settare l'autoCommit a False sulla connessione");
		try {
			if(currentActiveConnection != null){
				currentActiveConnection.setAutoCommit(false);
				logger.info(constructInvoker(invoker) + constructConnectionId(connectionId) + "Operazione di settaggio ad autocommit a False riuscita");
			}
		} catch(SQLException sqle) {
			logger.info(constructInvoker(invoker) + constructConnectionId(connectionId) + "Operazione di settaggio ad autocommit a False fallita",sqle);
		}		
	}
	public static void staticSetAutoCommitTrue(Connection  currentActiveConnection, Logger logger, String invoker, String connectionId){
		logger.info(constructInvoker(invoker) + constructConnectionId(connectionId) + "Mi accingo a settare l'autoCommit a True sulla connessione");
		try {
			if(currentActiveConnection != null){
				currentActiveConnection.setAutoCommit(true);
				logger.info(constructInvoker(invoker) + constructConnectionId(connectionId) + "Operazione di settaggio ad autocommit a True riuscita");
			}
		} catch(SQLException sqle) {
			logger.info(constructInvoker(invoker) + constructConnectionId(connectionId) + "Operazione di settaggio ad autocommit a True fallita",sqle);
		}		
	}
	
	private static String constructInvoker(String invoker){ return "[invoker: "+invoker+" ] "; }
	private static String constructConnectionId(String connectionId){ return "[Connection Id: "+connectionId+" ] "; }
	// simple method that call relative classes..
	//EXAMPLE: load(bean,xmlbean)
}
