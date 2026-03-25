package it.avlp.simog.util;


import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.exception.SimogException;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
/**  
 *  Classe che si occupa delle operazioni sql di base
 *  come gestione della connessione ... in realta non fa niente. 
 *  Prende una connessione gia attiva e la usa. Serve come wrapper,
 *  cosi sia il massloader che l'applicazione web possono generare list types.
 **/
public class SimpleDbManager extends AccessiDB{

	
	/**
	 * Costruttore, carica i dati necessari alla connessione sql
	 * e il logger
	 * 
	 * @param logger
	 * @param configuration
	 * @throws SimogException
	 */
	

	/**
	 * Metodo che si occupa del tentativo di commit, effettua il roolback in caso
	 * di fallimento
	 * 
	 * @throws SimogException
	 */
	
	public SimpleDbManager(Connection con, Logger logger){
		this.activeConnection = con;
		this.logger = logger;
	}
	protected void rollbackOrCommit() throws SimogException {
		try {
			activeConnection.commit();
			logger.debug ( "Completata commit" );
		} catch(SQLException sqlCommit) {
			
			try {
				activeConnection.rollback();
			} catch(SQLException sqlRollback) {
				throw new SimogException("Errore durante l'operazione di rollback", sqlRollback);
			}
			throw new SimogException("Errore durante l'operazione di commit", sqlCommit);
		}
	}
	
	/**
	 * metodo per il roolback delle operazioni
	 * 
	 * @throws SimogException
	 */
	protected void rollback() throws SimogException {
			try {
				activeConnection.rollback();
			} catch(SQLException sqlRollback) {
				throw new SimogException("Errore durante l'operazione di rollback", sqlRollback);
			}
	}
	
	/**
	 * metodo per il commit delle operazioni
	 * 
	 * @throws SimogException
	 */
	protected void commit() throws SimogException {
		try {
			activeConnection.commit();
		} catch(SQLException sqlRollback) {
			throw new SimogException("Errore durante l'operazione di rollback", sqlRollback);
		}
	}

	/**
	 * metodo per la chiusura della connessione
	 */
	public void closeConnection () {
		try {
			activeConnection.close();
			//decreaseCounter();
			logger.debug("Chiusa connessione");
		} catch ( Exception e ) {}
		activeConnection = null;
	}
	
	/**
	 * Metodo per il recupero della connessione locale attiva
	 * 
	 * @return Connection
	 */
	public Connection getCurrentActiveConnection() {
		return activeConnection;
	}

	
}
