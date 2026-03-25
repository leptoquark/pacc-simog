package it.avlp.simog.common.sql;

import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.util.SimogProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TreeMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class ConnectionWSManager{
	
	private static InitialContext initialContext = null;
	private static DataSource dataSource = null;
	protected static int countConnection = 0;
	private Connection currentActiveConnection = null;
	protected Logger logger = null;
	protected SimogProperties configuration = null;
	private TreeMap<String,Integer> isolations = null;
	
	public ConnectionWSManager(Logger logger,SimogProperties sp){
		this.configuration = sp;
		this.logger = logger;
		this.isolations = new TreeMap<String,Integer>();
		this.isolations.put("t_serialize", Integer.parseInt(""+Connection.TRANSACTION_SERIALIZABLE));
		this.isolations.put("t_read_committed", Integer.parseInt(""+Connection.TRANSACTION_READ_COMMITTED));
		this.isolations.put("t_read_uncommitted", Integer.parseInt(""+Connection.TRANSACTION_READ_UNCOMMITTED));
		this.isolations.put("t_repeatable_read", Integer.parseInt(""+Connection.TRANSACTION_REPEATABLE_READ));
		this.isolations.put("t_none", Integer.parseInt(""+Connection.TRANSACTION_NONE));
	}
	/**
	 * Ottiene la connessione
	 * @return Connection
	 * @throws SimogWSException
	 */
	public synchronized Connection getConnection()throws SimogWSException{
		if(currentActiveConnection != null){
			logger.debug("*** CONN_GET: " + currentActiveConnection);
			return this.currentActiveConnection;
		}else{
			//nullpointer
			logger.fatal("connessione nulla");
			throw new SimogWSException(ErrorManager.SIMOGWS_CONNECTIONWSMANAGER_NULL_01);
		}
	}
	/**
	 * crea una nuova connessione se non esistente
	 * @param callingClass String 
	 * @throws SimogWSException
	 */
	public void createConnection(String callingClass) throws SimogWSException {
		logger.debug("eseguendo: createConnection(" + callingClass + ")");
		logger.debug(">>>>Richiesta connessione da " + callingClass + " -> contatore: " + String.valueOf(countConnection));		
		countConnection ++;			
		if(currentActiveConnection == null){
			currentActiveConnection = getNewConnection();
			logger.debug("*** CONNOPEN: " + currentActiveConnection);
		}
// PP pericolosissimo in ambiente WS
//		else {
//			this.closeConnection();
//			currentActiveConnection = getNewConnection();
//			logger.debug("*** CONNROPN: " + currentActiveConnection);
//		}
	}
	
	/**************************************************************************************
	 * Imposta il livello di isolamento della connesione 
	 * @param isolation String
	 * @throws SimogWSException
	 */
	public void setIsolation(String isolation)throws SimogWSException{
		logger.debug("eseguendo: setIsolation("+isolation+")");
		try{
			if(currentActiveConnection!=null){
				currentActiveConnection.setTransactionIsolation(this.getIsolation(isolation));
			}
		}catch(SQLException sqle){
			logger.fatal("errore settando l'isolamento della connessione: "+sqle.getMessage());
			//sqle.printStackTrace();
			throw new SimogWSException(ErrorManager.SIMOGWS_CONNECTIONWSMANAGER_SQL_02);
		}
	}

	//--------------	metodi interni	--------------------/
	
	/****************************************************************
	 * Ottiene un anuova connesione
	 * return Connection
	 * throws SimogWSException
	 */
	private Connection getNewConnection() throws SimogWSException {
		try{
			if ( configuration.getJDBCDataSourceName() != null && getDataSource() != null ) {
				logger.debug ( "Tentativo connessione DataSource in corso" );
				currentActiveConnection = getDataSource().getConnection();
				this.setIsolation("t_read_committed");
			} else {
				logger.debug ( "[Tentativo di Connessione locale] su " + configuration.getJDBCString());
				this.setDriver();
				try{
					currentActiveConnection = DriverManager.getConnection(configuration.getJDBCString());
				}catch(SQLException sqle){
					logger.fatal("eccezione tentando di instaurare una connessione");
					throw new SimogWSException(ErrorManager.SIMOGWS_CONNECTIONWSMANAGER_SQL_07);
				}
			}
			return currentActiveConnection;
		}catch(Exception e){
			logger.fatal("fallito il recupero della connessione: "+e.getMessage());
		//	e.printStackTrace();
			if(e instanceof SimogWSException){
				throw (SimogWSException)e;
			}
			throw new SimogWSException(ErrorManager.SIMOGWS_CONNECTIONWSMANAGER_SQL_03);
		}
	}
	
	/*******************************************************************************************
	 * Retituisce il contenuto della variabile dataSource 
	 * @return DataSource
	 * @throws SimogWSException
	 */
	protected DataSource getDataSource() throws SimogWSException {
		try{
			if ( dataSource == null ) {
				logger.debug ( "Necessaria inizializzazione del DataSource [" + configuration.getJDBCDataSourceName() + "]" );
				dataSource = (DataSource) getInitialContext().lookup(configuration.getJDBCDataSourceName());
			}
			return dataSource;
		}catch(Exception e){
			logger.fatal("fallito get data source: "+e.getMessage());
			//e.printStackTrace();
			//naming ex
			throw new SimogWSException(ErrorManager.SIMOGWS_CONNECTIONWSMANAGER_SQL_04);
		}
	}
	
	/**************************************************************************************
	 * Imposta i JDBCDriver
	 * throws SimogWSException
	 */
	private void setDriver()throws SimogWSException{
		try{
			Class.forName(configuration.getJDBCDriver());
		}catch(ClassNotFoundException cnfe){
			logger.debug("[error] -  driver non trovato");
			logger.fatal("[eccezione] -  "+cnfe.getMessage());
			//cnfe.printStackTrace();
			//class not found
			throw new SimogWSException(ErrorManager.SIMOGWS_CONNECTIONWSMANAGER_CLASSNOTFOUND_05);
		}
	}
	/* metodo per tradurre da stringa a int di isolamento per la connessione */
	
	/*****************************************************************************************
	 * Ottiene il livello di isolamento 
	 * param isolation String
	 * return int 
	 * throws SimogWSException
	 */
	private int getIsolation(String isolation)throws SimogWSException{
		if(isolation != null && ! isolation.equals("")){
			Integer i = (Integer)this.isolations.get(isolation);
			return i.intValue();
		}else{
			logger.fatal("la string per settare il livello della isolamento � nulla");
			throw new SimogWSException(ErrorManager.SIMOGWS_CONNECTIONWSMANAGER_SQL_06);
		}
	}
	/*	utility	*/
	/**
	 * Imposta l'autocommit
	 * @param b boolean
	 */
	public void setAutocommit(boolean b){
		try {
			if(currentActiveConnection != null){
			currentActiveConnection.setAutoCommit(b);
			}
		} catch (SQLException e) {
			logger.fatal("il settaggio di autocommit non riuscito: "+e.getMessage());
			//e.printStackTrace();
		}
	}
	/**
	 * metodo che effettua il roolback delle operazioni effetuate sul db
	 */
	public void rollback(){
		try {
			if(currentActiveConnection != null){
				currentActiveConnection.rollback();
			}
		} catch (SQLException e) {
			logger.fatal("rollback non riuscito: "+e.getMessage());
			//e.printStackTrace();			
		}
	}
	/**
	 * metodo che effettua la conferma delle operazioni effettuate sul db
	 */
	public void commit(){
		try{
			if(currentActiveConnection != null){
				currentActiveConnection.commit();
			}
		}catch(SQLException sqle){
			logger.fatal("commit fallito: "+sqle.getMessage());
		//	sqle.printStackTrace();
		}
	}
	/**
	 * Chiude la connessione
	 * 
	 */
	public void closeConnection(){
		try {
			if(currentActiveConnection != null){
				countConnection--;
				logger.debug("*** CONNCLOS: " + currentActiveConnection + " contatore: " + String.valueOf(countConnection));
				currentActiveConnection.close();
			}
		} 
		catch (SQLException e) {
			logger.fatal("errore nella chiusura della connessione");
		//	e.printStackTrace();
		}
	}
	/*********************************************************************
	 * Ottiene l'initialContext, se non esiste ne crea uno
	 * return InitialContext
	 * throws NamingException
	 */
	private InitialContext getInitialContext() throws NamingException {
		if ( initialContext == null ) {
			logger.debug ( "Ricerca Contesto locale" );
			initialContext = new InitialContext();
			logger.debug ( "Ottenuto contesto locale" );
		}
		return initialContext;
	}
}


