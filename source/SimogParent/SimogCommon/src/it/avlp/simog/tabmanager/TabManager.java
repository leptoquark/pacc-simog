package it.avlp.simog.tabmanager;


import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.DETTAGLIO_REQUISITO;
import it.avlp.simog.db.generated.DOCUMENTO_REQUISITO;
import it.avlp.simog.db.generated.TIPOLOGIA;
import it.avlp.simog.db.generated.TIPO_DOCUMENTO_REQ;
import it.avlp.simog.db.generated.TIPO_FONTE_DOCUMENTO;
import it.avlp.simog.db.generated.TIPO_USO;
import it.avlp.simog.util.UnicodeHelper;
import it.avlp.simog.util.UnicodeColumnHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import org.apache.log4j.Logger;

public class TabManager extends AccessiDB {

	public final static String KEY_PREFIX = "KEY_";
	
	private String lastErr = "";
	
	/******************************************************************************************************
	 * Il metodo restituisce True se il campo inizia con "KEY_", False altrimenti 
	 * 
	 * param campo Stringa relatica al campo da controllare
	 * return boolean
	 */
	private boolean isKey (String campo){
		return campo.toLowerCase().startsWith(KEY_PREFIX.toLowerCase());
	}
	
	
	/**
	 * Constructor
	 * 
	 * @param currentActiveConnection
	 * @param logger
	 */
	public TabManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}

	/**
	 * Metodo per il recupero delle variazioni
	 * 
	 * @param tabellaCorrente
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getVariazioniByTabella(String tabellaCorrente, boolean requisiti) throws SQLException {
		
		logger.debug ( "Ricerca variazioni per tabella [" + tabellaCorrente + "]" );
		
		// il nome della tabella e' fittizio, viene usta TIPOLOGIA solo per indicare il nome delle colonne
		String ricercaVariazioniByTabellaCorrente = 
			"SELECT "
			+ (requisiti ? "convert(varchar, " + TIPOLOGIA.DATA_ULTIMA_MODIFICA + ", 112)" 
			             : TIPOLOGIA.DATA_ULTIMA_MODIFICA) + " as " +  TIPOLOGIA.DATA_ULTIMA_MODIFICA
			+ " FROM "
			+ tabellaCorrente
			+ " WHERE "
			+ TIPOLOGIA.DATA_ULTIMA_MODIFICA + " IS NOT NULL "
			+ " GROUP BY "
			+ TIPOLOGIA.DATA_ULTIMA_MODIFICA
//			+ " UNION SELECT "
//			+ (requisiti ? "convert(varchar, " + TIPOLOGIA.DATA_FINE_VALIDITA + ", 112)" 
//                  : TIPOLOGIA.DATA_FINE_VALIDITA) + " as " +  TIPOLOGIA.DATA_ULTIMA_MODIFICA
//			+ " FROM "
//			+ tabellaCorrente
//			+ " WHERE "
//			+ TIPOLOGIA.DATA_FINE_VALIDITA + " IS NOT NULL "
//			+ " GROUP BY "
//			+ TIPOLOGIA.DATA_FINE_VALIDITA
			+ " ORDER BY 1";
		
		Statement stmt = activeConnection.createStatement();
		
		logger.debug ( "Esecuzione in corso query [" + ricercaVariazioniByTabellaCorrente + "]" );
		
		TableBean tb = new TableBean ( stmt.executeQuery(ricercaVariazioniByTabellaCorrente) );
		
		close(null, stmt);
		
		return tb;
	
	}
	
	/******************************************************************************************************
	 * Ricerca le variazioni effettuate sulla tabella indicata in tabellaCorrente alla data 
	 * specificata in dataUltimaModifica.
	 * 
	 * @param tabellaCorrente String identificante la tabella su cui si sta operando
	 * @param dataUltimaModifica String contenenete la data della modifica in base a cui effettuare la ricerca
	 * @param sortingField String contenente il campo su cui effettuare l'ordinamento
	 * @param sortStyle boolean, se true affettua un ordinamento ascendente, decrescente altrimenti.  
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getStatoVariazioniByTabella(String tabellaCorrente, String dataUltimaModifica, String sortingField, boolean sortStyle ) throws SQLException {
		
		String sortOrder = sortStyle ? " ASC " : " DESC ";
		
		logger.debug ( "Ricerca variazioni per tabella [" + tabellaCorrente + "] alla data [" + dataUltimaModifica + "]" );
		
		String ricercaVariazioniDataByTabellaCorrente = 
			"SELECT * FROM "
			+ tabellaCorrente;
		
		String dataCondition = " WHERE " + TIPOLOGIA.DATA_ULTIMA_MODIFICA + "=?";
		dataCondition += " OR " + getDataValiditaFieldName(tabellaCorrente) + "=?";
		
		String orderCondition = "";
		
		if ( sortingField != null ) {
			orderCondition += " ORDER BY " + sortingField + sortOrder;
		}
		
		String query = ricercaVariazioniDataByTabellaCorrente;
		
		if ( dataUltimaModifica != null ) {
			query += dataCondition;
		}

		query += orderCondition;
		
		logger.debug ( "Ricerca dettagli tabella servizio [" + tabellaCorrente + "] alla data [" + dataUltimaModifica + "] query [" + query + "]" );
			
		PreparedStatement stmt = activeConnection.prepareStatement(query);
		
		int i = 1;
		if ( dataUltimaModifica != null ) {
			stmt.setString(i++, dataUltimaModifica);
			stmt.setString(i++, dataUltimaModifica);
		}
		TableBean result = new TableBean ( stmt.executeQuery() );
		
		try {
			stmt.close();
		} catch ( Exception e ) {}
		stmt = null;
	
		return result;
	}

	
	/***************************************************************************************************
	 * genera la parte di query relativa alle where condition sulle chiavi contenute nella tabella tab. 
	 *
	 * @param tab TableBean contenente la HashMap su cui andare a ricercare le chiavi
	 * @return String
	 */
	public String getWhereKeys (TableBean tab) {
		String currentKeyName = "";
		
		for ( Enumeration e = tab.keys(); e.hasMoreElements(); ) {
			String currentFieldName = (String) e.nextElement();

			if ( isKey(currentFieldName)) {				
				currentKeyName = currentKeyName + (currentKeyName.equals("") ? "" : " AND ") 
							   + currentFieldName.toLowerCase().replace(KEY_PREFIX.toLowerCase(), "") + "=?";
			} 
		}
		return currentKeyName;
	}

	
	
	/*****************************************************************************************************
	 * genera la parte di query con i campi (update)
	 * 
	 * @param tab TableBean
	 * @return String
	 */
	public String getUpdateFields (TableBean tab) {
		String retVal = "";
		
		for ( Enumeration e = tab.keys(); e.hasMoreElements(); ) {
			String currentFieldName = (String) e.nextElement();

			if ( ! "OPERAZIONE".equalsIgnoreCase(currentFieldName)&& ! isKey(currentFieldName) ) {
				retVal += ", " + currentFieldName + "=?";
			}
		}
		return retVal;
	}


	/******************************************************************************************************
 	 * elaborazione delle richieste di aggiornamento record
 	 * 
	 * @param tableUPDATE TableBean
	 * @param tableName String
	 * @param todayDate String
	 * @throws SQLException
	 */
	public void aggiorna(TableBean tableUPDATE, String tableName, String todayDate) throws SQLException {
		logger.debug("Contenuto lista [" + tableUPDATE + "]");
		logger.info("Inizio [M]odifica di[" + tableUPDATE.getRowsCount() + "] tuple tabella [" + tableName + "]");

		String updateQuery = "UPDATE " + tableName + " SET " + TIPOLOGIA.DATA_ULTIMA_MODIFICA + "='" + todayDate + "' ";
		String updateFields = getUpdateFields(tableUPDATE);
		String currentKeyName = getWhereKeys(tableUPDATE);
		
		updateQuery += updateFields + " WHERE " + currentKeyName; // PP tolta + " AND " + TIPOLOGIA.DATA_FINE_VALIDITA + " IS NULL" ;
		
		logger.debug ("Query di aggiornamento corrente [" + updateQuery + "]" );

		PreparedStatement pstmt = null;
		
		try {
			pstmt = activeConnection.prepareStatement(updateQuery);

			for ( int i = 0; i < tableUPDATE.getRowsCount(); i++ ) {
				String currentKeyValue[] = new String[10]; // max 10 chiavi
				int posCount = 1;
				int keyCount = 0;

				for ( Enumeration e = tableUPDATE.keys(); e.hasMoreElements(); ) {
					String currentFieldName = (String) e.nextElement();
					String currentFieldValue = tableUPDATE.getField(currentFieldName, i);
					logger.debug ( "Campo corrente [" + currentFieldName + "] Valore corrente [" + currentFieldValue + "]" );

					if ( isKey(currentFieldName)) {
						logger.debug ( "Assegnato valore [" + currentFieldValue + "] al campo chiave (" + currentFieldName + ")" );
						currentKeyValue[keyCount++] = currentFieldValue;
					} else if ( ! "OPERAZIONE".equalsIgnoreCase(currentFieldName) ) {
						logger.debug ( "Impostazione campo [" + posCount + "] al valore [" + currentFieldValue + "]");
						// Usa UnicodeHelper per stringhe per garantire supporto Unicode (arabo)
						if (currentFieldValue != null && currentFieldValue instanceof String) {
							String columnName = currentFieldName.toLowerCase().replace(KEY_PREFIX.toLowerCase(), "");
							UnicodeHelper.setStringSmart(pstmt, posCount++, currentFieldValue, activeConnection, tableName, columnName);
						} else {
							pstmt.setObject(posCount++, currentFieldValue);
						}
					}
				}	
				// impostazione valori delle chiavi
				for(int z = 0; z < keyCount; z++) {
					logger.debug ( "Impostazione campo chiave[" + z+1 + "] al valore [" + currentKeyValue[z] + "]");
					// Usa UnicodeHelper per stringhe chiave per garantire supporto Unicode (arabo)
					if (currentKeyValue[z] != null && currentKeyValue[z] instanceof String) {
						// Per le chiavi, usa il nome del campo chiave originale
						String keyFieldName = null;
						for (Enumeration e = tableUPDATE.keys(); e.hasMoreElements(); ) {
							String fieldName = (String) e.nextElement();
							if (isKey(fieldName)) {
								keyFieldName = fieldName.toLowerCase().replace(KEY_PREFIX.toLowerCase(), "");
								break;
							}
						}
						UnicodeHelper.setStringSmart(pstmt, posCount++, currentKeyValue[z], activeConnection, tableName, keyFieldName);
					} else {
						pstmt.setObject(posCount++, currentKeyValue[z]);
					}
				}
				
				pstmt.executeUpdate();
			}
		} catch ( SQLException e ) {
			//Non viene loggata come fatal, se ne occupa il chiamante
			logger.error(e);
	        lastErr = e.getMessage();

			throw e;
		} finally {
			try {
				pstmt.close();
			} catch ( Exception e ) {}
			pstmt = null;
		}
	}
	

	/*****************************************************************************************************
	 * Esecuzione degli inserimenti richiesti
	 * Deve controllare che alle tabelle che NON richiedono
	 * la chiave primaria, in quanto IDENTITY-SQLSERVER,
	 * non venga passata la chiave primaria
	 * 
	 * @param tableINSERT TableBean
	 * @param tableName String
	 * @param todayDate String
	 * @throws SQLException
	 */
	public void inserisci(TableBean tableINSERT, String tableName, String todayDate) throws SQLException {

		logger.debug("Contenuto lista [" + tableINSERT + "]");
		logger.info("Inizio [I]nserimento di[" + tableINSERT.getRowsCount() + "]tuple tabella [" + tableName + "]");
		
//		int righeInserite = 0;
//		int righeCancellate = 0;
//		int errors = 0;
		
		/* ************************************* */
		/* **** Impostazione della query ******* */
		/* ************************************* */
		StringBuffer insertQueryFields = new StringBuffer ( "INSERT INTO " + tableName + "( " + TIPOLOGIA.DATA_ULTIMA_MODIFICA + ", ");
		StringBuffer insertQueryValues = new StringBuffer ( " VALUES ( '" + todayDate + "', ");		

		/* Viene "montata" la query di inserimento dinamicamente
		 * in base alla dimensione del TableBean 
		 */
		int poscounter = 0;
		for ( Enumeration columns = tableINSERT.keys(); columns.hasMoreElements(); ) {
			String currentColumn = (String) columns.nextElement();

			if ( ! currentColumn.equalsIgnoreCase("OPERAZIONE"))  { //&& ! isKey(currentColumn) )
					if ( poscounter++ > 0 ) {
						insertQueryFields.append ( ", " );
						insertQueryValues.append ( ", " );
					}
				
				insertQueryFields.append ( currentColumn.toLowerCase().replace(KEY_PREFIX.toLowerCase(), "") );
				insertQueryValues.append ( "?" );
			}
		}
		insertQueryFields.append ( " )" );
		insertQueryValues.append ( " )" );
		
		logger.debug ( "Query FIELDS [" + insertQueryFields + "]" );
		logger.debug ( "Query VALUES [" + insertQueryValues + "]" );		

		String insertQuery = insertQueryFields.append(insertQueryValues).toString();
		
		logger.debug ("Query finale per inserimento [" + insertQuery + "] ");

		
//		/* Impostazione query di cancellazione - per evitare tuple doppie */		
//		String deleteQuery = "DELETE FROM " + tableName + " WHERE " + currentKeyName;
//		/* *********** */
//		
//		logger.debug ( "Query di cancellazione [" + deleteQuery + "]" );
//		
//		/* ****************
//		 * Provvede a cancellare le tuple di cui e' richiesto
//		 * l'inserimento: evita la duplicazione di righe gia' inserite
//		 */
//		
//		logger.debug("Avvio cancellazione prima dell'inserimento: tuple[" + tableINSERT + "]");
//		
//		PreparedStatement deletePstmt = null;
//		
//		try {
//			deletePstmt = activeConnection.prepareStatement(deleteQuery);
//		
//			for ( int i = 0; i < tableINSERT.getRowsCount(); i++ ) {
//				String currentKeyValue[] = new String[10]; // max 10 chiavi
//				int posCount = 1;
//				int keyCount = 0;
//				
//				
//				
//				
//				
//				String currentKeyValue = tableINSERT.getField(tableINSERT.getKeyName(), i);
//				logger.debug("[" + i + "] riga - nomeChiave [" + tableINSERT.getKeyName() + "]  Chiave corrente [" + currentKeyValue + "]");
//				try {
//					logger.debug ( "Statement di cancellazione [" + deletePstmt + "]" );
//					deletePstmt.setObject(1, currentKeyValue);
//					righeCancellate += deletePstmt.executeUpdate();
//				} catch ( Exception e ) {
//					logger.error("[" + ++errors + "]Impossibile eliminare correttamente la tupla [" + currentKeyValue + "] per la tabella amministrativa [" + tableName +"]", e);
//				} 
//			}
//	
//		deletePstmt.close();

		/* ***********************
		 * Provvede ad inserire le tuple richieste
		 */
		PreparedStatement insertPstmt = null;
		try {
			logger.debug("Avvio Inserimento");
			
			// Verifica colonne VARCHAR e avvisa se necessario
			UnicodeColumnHandler.ColumnReport report = UnicodeColumnHandler.checkTableColumns(activeConnection, tableName);
			if (report.hasVarcharColumns()) {
				logger.warn("⚠️ Tabella " + tableName + " contiene colonne VARCHAR che potrebbero causare corruzione dati Unicode:");
				for (UnicodeColumnHandler.ColumnReport.ColumnInfo col : report.getVarcharColumns()) {
					logger.warn("  - " + col.getName() + " (" + col.getDataType() + ")");
				}
				logger.warn("Raccomandazione: convertire le colonne a NVARCHAR. Script disponibile tramite UnicodeColumnHandler.generateConversionScript()");
			}
			
			insertPstmt = activeConnection.prepareStatement(insertQuery);
			for ( int i = 0; i < tableINSERT.getRowsCount(); i++ ) {
				logger.debug("2 - tableINSERT.keys():::"+tableINSERT.keys());
//				try {
					int posCounter = 1;

					for ( Enumeration columns = tableINSERT.keys(); columns.hasMoreElements(); ) {
						String currentColumn = (String) columns.nextElement();

						if ( ! currentColumn.equalsIgnoreCase("OPERAZIONE") ){ //&& ! isKey(currentColumn) ) {
							logger.debug ("assegnazione a posizione [" + posCounter + "] valore[" + tableINSERT.getField(currentColumn, i) + "]" );
							String fieldValue = tableINSERT.getField(currentColumn, i);
							// Usa UnicodeHelper per stringhe per garantire supporto Unicode (arabo)
							if (fieldValue != null && fieldValue instanceof String) {
								String columnName = currentColumn.toLowerCase().replace(KEY_PREFIX.toLowerCase(), "");
								UnicodeHelper.setStringSmart(insertPstmt, posCounter++, fieldValue, activeConnection, tableName, columnName);
							} else {
								insertPstmt.setObject(posCounter++, fieldValue);
							}
						}
					}
//					righeInserite += 
			      insertPstmt.executeUpdate();
//				} catch ( Exception e ) {
//					
//					logger.fatal("Impossibile inserire correttamente la tupla nella tabella amministrativa [" + tableName +"]", e);
//					errors++;
//				}
			}
		} catch ( SQLException sqle ) {
//			Non viene loggata come fatal, il log viene effettuato dal chiamante
		   logger.debug("eccezione insert: " +  sqle.getMessage());
           lastErr = sqle.getMessage();

			throw sqle;
		} finally {
//			try {
//				deletePstmt.close();
//			} catch ( Exception sqle ) {}
//			deletePstmt = null;
			try {
				insertPstmt.close();
			} catch ( Exception e ) {}
			insertPstmt = null;

		}
	}
	
	/******************************************************************************************************************
	 * Imposta la data di fine validita' a quella attuale per la tabella indicata 
	 * da tableDELETE  e dove Data fine validita' sia NULL
	 * 
	 * @param tableDELETE TableBean della tabella da cancellare
	 * @param tableName String per il nome della tabella 
	 * @param todayDate String della data 
	 * @throws SQLException 
	 */
	public void cancella(TableBean tableDELETE, String tableName, String todayDate) throws SQLException {
		logger.debug("Contenuto lista [" + tableDELETE + "]");
		logger.info("Inizio [C]ancellazione di[" + tableDELETE.getRowsCount() + "]tuple tabella [" + tableName + "]");	
		
		String currentKeyName = getWhereKeys(tableDELETE);
		
		String deleteQuery =
			"UPDATE " + tableName + " SET "
			+ getDataValiditaFieldName(tableName) + "='" + todayDate + "'";

//		int count = 0;

		deleteQuery += " WHERE " + currentKeyName + " AND " + getDataValiditaFieldName(tableName) + " IS NULL" ;
		
		logger.debug ("Query di cancellazione corrente [" + deleteQuery + "]" );

		PreparedStatement pstmt = null;

		try {
			pstmt = activeConnection.prepareStatement(deleteQuery);

			for ( int i = 0; i < tableDELETE.getRowsCount(); i++ ) {
				// scorre tutte le righe della tabella
				String currentKeyValue[] = new String[10]; // max 10 chiavi
				int posCount = 1;
				int keyCount = 0;

				for ( Enumeration e = tableDELETE.keys(); e.hasMoreElements(); ) {
					// scorre le chiavi della tabella per ogni riga
					String currentFieldName = (String) e.nextElement();
					String currentFieldValue = tableDELETE.getField(currentFieldName, i);
					logger.debug ( "Campo corrente [" + currentFieldName + "] Valore corrente [" + currentFieldValue + "]" );

					if ( isKey(currentFieldName) ) {
						logger.debug ( "Assegnato valore [" + currentFieldValue + "] al campo chiave (" + currentFieldName + ")" );
					// Inserisco in currentKeyValue il valore del campo chiave ed incremento keyCount
						currentKeyValue[keyCount++] = currentFieldValue; 
					}
				}	
				// A questo punto ho memorizzato tutti i valori chiave in currentKeyValue o per lo meno i primi 10
				//logger.debug ( "Impostazione campo chiave[" + posCount + "] al valore [" + currentKeyValue + "]");
				// impostazione valori delle chiavi
				for(int z = 0; z < keyCount; z++) {
					logger.debug ( "Impostazione campo chiave[" + z+1 + "] al valore [" + currentKeyValue[z] + "]");
					
					try {
						long ltemp = Long.parseLong(currentKeyValue[z]);
						pstmt.setLong(posCount++, ltemp);
					} catch (NumberFormatException e){
						// Usa UnicodeHelper per stringhe chiave per garantire supporto Unicode (arabo)
						// Per le chiavi, usa il nome del campo chiave originale
						String keyFieldName = null;
						for (Enumeration e2 = tableDELETE.keys(); e2.hasMoreElements(); ) {
							String fieldName = (String) e2.nextElement();
							if (isKey(fieldName)) {
								keyFieldName = fieldName.toLowerCase().replace(KEY_PREFIX.toLowerCase(), "");
								break;
							}
						}
						UnicodeHelper.setStringSmart(pstmt, posCount++, currentKeyValue[z], activeConnection, tableName, keyFieldName);
					}
				}
				//ho inserito nella prepared statement tutti i valori dei campi chiave 
				pstmt.executeUpdate();
			}
		} catch ( SQLException e ) {
//			Non viene loggata come fatal, se ne occupa il chiamante
			logger.error (e);
			lastErr = e.getMessage();
			throw e;
		} finally {
			try {
				pstmt.close();
			} catch ( Exception e ) {}
			pstmt = null;
		}
	}

	/**
	 * Metodo per il recupero delle informazioni sui campi tabella
	 * 
	 * @param tabellaCorrente
	 * @return StringArray
	 * @throws SQLException
	 */
	public String getTabellaInfo(String tabellaCorrente) throws SQLException {
		
		logger.debug ( "Ricerca info per tabella [" + tabellaCorrente + "]" );
		
		String ricercaInfo = 
			"SELECT *"
			+ " FROM "
			+ tabellaCorrente
			+ " WHERE 1=2";
		
		Statement stmt = activeConnection.createStatement();
		ResultSet rs = stmt.executeQuery(ricercaInfo);
		ResultSetMetaData meta = rs.getMetaData();
		String retVal = "";
		
		for (int i = 0;i<meta.getColumnCount(); i++){
			retVal += "&nbsp;&nbsp;&nbsp;" + meta.getColumnName(i+1).toUpperCase() + " (" + meta.getColumnTypeName(i+1) 
					+ " "; 
			if (meta.isSigned(i+1))
				retVal += String.valueOf(meta.getPrecision(i+1)) + "," + String.valueOf(meta.getScale(i+1));
			else
				retVal += String.valueOf(meta.getColumnDisplaySize(i+1));

			retVal +=   ") " + (meta.isNullable(i+1)== ResultSetMetaData.columnNullable  ? "" : "OBBLIGATORIO") + "<br>";
		}
		
		try {
			stmt.close();
		} catch ( Exception e ) {}
		stmt = null;
		return retVal;
	
	}

	// patch per la diversità di nomenclatura sulle tabelle requisiti
   private String getDataValiditaFieldName(String tabella){
      
      String retVal = TIPOLOGIA.DATA_FINE_VALIDITA;
      
      if ( tabella.equals(DETTAGLIO_REQUISITO.TABLE_NAME)
        || tabella.equals(DOCUMENTO_REQUISITO.TABLE_NAME)
        || tabella.equals(TIPO_DOCUMENTO_REQ.TABLE_NAME)
        || tabella.equals(TIPO_FONTE_DOCUMENTO.TABLE_NAME)
        || tabella.equals(TIPO_USO.TABLE_NAME)
      )
         retVal = TIPO_USO.DATA_FINE;
      
      return retVal;
   }
	
   public String getLastErr() {
      return lastErr;
   }


   public void setLastErr(String lastErr) {
      this.lastErr = lastErr;
   }

}
