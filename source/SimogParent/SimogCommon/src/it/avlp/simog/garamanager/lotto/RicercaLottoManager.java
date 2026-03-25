package it.avlp.simog.garamanager.lotto;

import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.DOCUMENTO;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.LOTTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

import org.apache.log4j.Logger;


public class RicercaLottoManager extends AccessiDB {
	
	
		/**************************************************
		 * Stringa relativa alla condizione sulla data di pubblicazione, affinche sia compresa in un range, e scadenza pagamenti
		 * affinche sia compreso all'interno di un altro range di date.  
		 * 
		 */
		private final String DATA_CONDIZIONI = 
			" (" + LOTTO.DATA_PUBBLICAZIONE + " BETWEEN ? AND ? ) "
			+ " AND "
			+ "(" + LOTTO.DATA_SCADENZA_PAGAMENTI + " BETWEEN ? AND ?)";

		/**************************************************
		 * Stringa per la query di Select:
		 * <ul>
		 * <li>Denominazione Amministrazione
		 * <li>Oggetto Gara
		 * <li>Data Creazione
		 * </ul>
		 * From Condition
		 * <ul>
		 * <li>Tabella Gara
		 * <li>Tabella Lotto
		 * </ul>
		 * Where condition
		 * <ul>
		 * <li>Id Gara in Gara = Id Gara in Lotto
		 * <li>Data Condizioni
		 * </ul>
		 */
		private final String DETTAGLIO_GARA = "SELECT " +
			GARA.T_DENOM_AMMINISTRAZIONE + 
			", " + GARA.T_DENOM_STAZIONE_APPALTANTE +
			", "+ GARA.T_OGGETTO+
			", "+ GARA.T_DATA_CREAZIONE+
			" FROM " + GARA.TABLE_NAME + ", "
			+ LOTTO.TABLE_NAME
			+ " WHERE " + GARA.T_ID_GARA + "=" + LOTTO.T_ID_GARA
			+ " AND " + DATA_CONDIZIONI;
		
		/*******************************************************
		 * Stringa per la Select dei parametri
		 * <ul>
		 * <li>CIG
		 * <li>CIG KKK
		 * <li>Oggetto del Lotto
		 * <li>Importo del Lotto
		 * <li>Data Pubblicazione
		 * </ul>
		 * From condition
		 * <ul>
		 * <li>Tabella Lotto
		 * </ul>
		 * Where condition : <br>
		 * Date di sospensione e scadenza pagamenti cadano in due determinati range di date. 
		 */
		private final String DETTAGLIO_LOTTO =
			"SELECT "
			+ LOTTO.CIG + "+" + LOTTO.CIG_KKK + " AS " + LOTTO.CIG
			+ ", " + LOTTO.OGGETTO
			+ ", " + LOTTO.IMPORTO_LOTTO
			+ ", " + LOTTO.DATA_PUBBLICAZIONE
			+ " FROM "
			+ LOTTO.TABLE_NAME
			+ " WHERE "
			+ DATA_CONDIZIONI;
		
		private final String LISTA_DOCUMENTI = 
			"SELECT "
			+ DOCUMENTO.T_ID_DOCUMENTO 
			+ ", " + DOCUMENTO.T_NOMEDOCUMENTO
			+" FROM "
			+ DOCUMENTO.TABLE_NAME
			+ ", " + LOTTO.TABLE_NAME 
			+ " WHERE "
			+ DOCUMENTO.T_ID_LOTTO + "=" + LOTTO.T_ID_LOTTO + " AND " + DATA_CONDIZIONI;

	/****************************************************************************************************
	 * Costruttore 
	 * 
	 * @param currentActiveConnection
	 * @param logger
	 */
	public RicercaLottoManager(Connection currentActiveConnection, Logger logger) {		
		super(currentActiveConnection, logger);
	}
	
	/****************************************************************************************************
	 * Recupera il dettaglio della gara inserendo nella TreeMap le informazioni relative a :
	 * <ul>
	 * <li>Denominazione Amministrazione
	 * <li>Denominazione Stazione Appaltante
	 * <li>Oggetto della Gara
	 * <li>Data Creazione
	 * </ul>
	 * dove la data di pubblicazione del lotto sia compresa tra data1 e 
	 * data2 e la data di scadenza pagamenti sia compresa tra data3 e data4.
	 * @param cig Stringa contenente il CIG
	 * @param data1 String per inizio data pubblicazione 
	 * @param data2 String per fine data pubblicazione
	 * @param data3 String per l'inizio della data di scadenza
	 * @param data4 String per la fine della data di scadenza
	 * @return TreeMap
	 * @throws SQLException
	 */
	public TreeMap getDettagliGara(String cig, String data1, String data2, String data3, String data4) throws SQLException{
		
		TreeMap map = new TreeMap();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String query = DETTAGLIO_GARA + " AND " + getQueryConditionByCIGSommaUrgenza(cig);	
				
			int index = 1;
			logger.debug("Esecuzione della query [" + query + "]");

			ps = activeConnection.prepareStatement(query);
			
			ps.setString(index++, data1);
			ps.setString(index++, data2);
			
			ps.setString(index++, data3);
			ps.setString(index++, data4);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				map.put(GARA.DENOM_AMMINISTRAZIONE, rs.getString(GARA.DENOM_AMMINISTRAZIONE));
				map.put(GARA.DENOM_STAZIONE_APPALTANTE, rs.getString(GARA.DENOM_STAZIONE_APPALTANTE));
				map.put(GARA.OGGETTO, rs.getString(GARA.OGGETTO));
				map.put(GARA.DATA_CREAZIONE, rs.getString(GARA.DATA_CREAZIONE));
			}
		} catch(java.sql.SQLException e){
			String message = "Impossibile leggere dal db";
			logger.fatal(message, e );
			throw e;
		} finally {
			close(rs,ps);
//			try {
//				ps.close();
//			} catch (Exception e ) {}
//			ps = null;
//			try {
//				rs.close();
//			} catch ( Exception e ) {}
//			rs = null;
		}
		
		return map;
	}
	
	/******************************************************************************************************
	 * Recupera i dettagli del lotto 
	 * dove la data di pubblicazione del lotto sia compresa tra data1 e 
	 * data2 e la data di scadenza pagamenti sia compresa tra data3 e data4.
	 * 
	 * @param cig String
	 * @param data1 String per inizio data pubblicazione 
	 * @param data2 String per fine data pubblicazione
	 * @param data3 String per l'inizio della data di scadenza
	 * @param data4 String per la fine della data di scadenza
	 * @return TableBean con le informazioni del lotto
	 * @throws SQLException
	 */
	
	public TableBean getDettagliLotto(String cig, String data1, String data2, String data3, String data4) throws SQLException{
		
		TableBean map = null;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			
			String query = DETTAGLIO_LOTTO + " AND " + getQueryConditionByCIGSommaUrgenza(cig);
			
			int index = 1;
			logger.debug("Esecuzione della query [" + query + "]");
			
			ps = activeConnection.prepareStatement(query);
			
			ps.setString(index++, data1);
			ps.setString(index++, data2);
			
			ps.setString(index++, data3);
			ps.setString(index++, data4);
			
			rs = ps.executeQuery();
			map = new TableBean(rs);

			return map;
			
		}
		catch(java.sql.SQLException e){
			String message = "Impossibile leggere dal db";
			logger.fatal(message, e );
			throw e;
		} finally {
			close(rs,ps);
//			try {
//				ps.close();
//			} catch (Exception e ) {}
//			ps = null;
//			try {
//				rs.close();
//			} catch ( Exception e ) {}
//			rs = null;
		}
	}

	
	/*****************************************************************************************************
	 * Recupera i documenti in base ad un determinato CIG e 
	 * dove la data di pubblicazione del lotto sia compresa tra data1 e 
	 * data2 e la data di scadenza pagamenti sia compresa tra data3 e data4.
	 * 
	 * @param cig String
	 * @param data1 String per inizio data pubblicazione 
	 * @param data2 String per fine data pubblicazione
	 * @param data3 String per l'inizio della data di scadenza
	 * @param data4 String per la fine della data di scadenza
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getDocumenti(String cig, String data1, String data2, String data3, String data4) throws SQLException{
		
		TableBean map = null;
		java.sql.PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
		
		try{
			
			String query = LISTA_DOCUMENTI + " AND " + getQueryConditionByCIGSommaUrgenza(cig);
			logger.debug("Esecuzione della query [" + query + "]");
			
			ps = activeConnection.prepareStatement( query );
			
			int index = 1;
			
			ps.setString(index++, data1);
			ps.setString(index++, data2);
			
			ps.setString(index++, data3);
			ps.setString(index++, data4);
			
			rs = ps.executeQuery();
			map = new TableBean(rs);
			
		} catch( java.sql.SQLException e){
			String message = "Impossibile leggere dal db";
			logger.fatal( message, e );
			throw e;
		} finally {
			close(rs,ps);
//			try {
//				ps.close();
//			} catch (Exception e ) {}
//			ps = null;
//			try {
//				rs.close();
//			} catch ( Exception e ) {}
//			rs = null;
		}
		return map;
	}
}
