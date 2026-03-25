package it.avlp.simog.rubricamanager;

import it.avlp.simog.beans.RubricaResponsabili;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.SOGGETTI_RESPONSABILI;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;


/**
 * Questa classe gestisce tutti gli eventuali accessi 
 * al db per le funzioni legate alla rubrica
 *
 */
public class RubricaResponsabiliManager extends AccessiDB{

	public RubricaResponsabiliManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	//SELECT CHE ESTRAE TUTTI I PARTECIPANTI ALLE GARE CHE IL RUP/CS PUO' VEDERE
	private final String BASE_SELECT_SOGGETTI_RESPONSABILI =
		
		"SELECT "
		+ SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE
		+ ", " + SOGGETTI_RESPONSABILI.COGNOME
		+ ", " + SOGGETTI_RESPONSABILI.DATA_FINE_RES
		+ ", " + SOGGETTI_RESPONSABILI.DATA_INIZIO_RES
		+ ", " + SOGGETTI_RESPONSABILI.EMAIL		
		+ ", " + SOGGETTI_RESPONSABILI.FAX
		+ ", " + SOGGETTI_RESPONSABILI.ID_RESPONSABILE
		+ ", " + SOGGETTI_RESPONSABILI.NOME
		+ ", " + SOGGETTI_RESPONSABILI.TELEFONO
		+ ", " + SOGGETTI_RESPONSABILI.INDIRIZZO
		+ ", " + SOGGETTI_RESPONSABILI.CAP
		+ ", " + SOGGETTI_RESPONSABILI.COMUNE_ISTAT
		
		+ " FROM " + SOGGETTI_RESPONSABILI.TABLE_NAME
		//+ " WHERE "+ SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG + "= ''" ;	
		+ " WHERE "+ SOGGETTI_RESPONSABILI.DATA_FINE_RES+ " is NULL";
	
	private final String BASE_ORDERBY = " ORDER BY " + SOGGETTI_RESPONSABILI.COGNOME;
	
	private final String BASE_SELECT_DETTAGLIO_SOGGETTI_PARTECIPANTI =
		"SELECT "
		+ SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE
		+ ", " + SOGGETTI_RESPONSABILI.COGNOME
		+ ", " + SOGGETTI_RESPONSABILI.DATA_FINE_RES
		+ ", " + SOGGETTI_RESPONSABILI.DATA_INIZIO_RES
		+ ", " + SOGGETTI_RESPONSABILI.EMAIL		
		+ ", " + SOGGETTI_RESPONSABILI.FAX
		+ ", " + SOGGETTI_RESPONSABILI.ID_RESPONSABILE
		+ ", " + SOGGETTI_RESPONSABILI.NOME
		+ ", " + SOGGETTI_RESPONSABILI.TELEFONO
		+ ", " + SOGGETTI_RESPONSABILI.INDIRIZZO
		+ ", " + SOGGETTI_RESPONSABILI.CAP
		+ ", " + SOGGETTI_RESPONSABILI.COMUNE_ISTAT
		
		+ " FROM " + SOGGETTI_RESPONSABILI.TABLE_NAME;
	
	// PPriattivata per modifiche in corso opera
	private final String AGGIORNAMENTO_SOGGETTO =
		"UPDATE "
		+ SOGGETTI_RESPONSABILI.TABLE_NAME
		+ " SET "
		+ SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE + "= ?" 
		+ ", " + SOGGETTI_RESPONSABILI.COGNOME + " =?" 
		+ ", " + SOGGETTI_RESPONSABILI.DATA_FINE_RES + " =?" 
		+ ", " + SOGGETTI_RESPONSABILI.EMAIL + "=?"		
		+ ", " + SOGGETTI_RESPONSABILI.FAX + "=?" 
		+ ", " + SOGGETTI_RESPONSABILI.NOME + "=?" 
		+ ", " + SOGGETTI_RESPONSABILI.TELEFONO + "=?" 
		+ ", " + SOGGETTI_RESPONSABILI.INDIRIZZO + "=?"
		+ ", " + SOGGETTI_RESPONSABILI.CAP + "=?"
		+ ", " + SOGGETTI_RESPONSABILI.COMUNE_ISTAT + "=?"
		+ " WHERE "
		+ SOGGETTI_RESPONSABILI.ID_RESPONSABILE + " = ?"
		+" AND "
		+ SOGGETTI_RESPONSABILI.DATA_INIZIO_RES + " = ?";
	
	private final String UPDATE_PARTECIPANTE_RUBRICA_RESPONSABILI =
		"INSERT INTO "
		+ SOGGETTI_RESPONSABILI.TABLE_NAME
		+ " (" 
		+ SOGGETTI_RESPONSABILI.ID_RESPONSABILE
		+ ", " + SOGGETTI_RESPONSABILI.DATA_INIZIO_RES
		+ ", " + SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE
		+ ", " + SOGGETTI_RESPONSABILI.COGNOME
		+ ", " + SOGGETTI_RESPONSABILI.EMAIL
		+ ", " + SOGGETTI_RESPONSABILI.FAX		
		+ ", " + SOGGETTI_RESPONSABILI.NOME
		+ ", " + SOGGETTI_RESPONSABILI.TELEFONO
		+ ", " + SOGGETTI_RESPONSABILI.INDIRIZZO
		+ ", " + SOGGETTI_RESPONSABILI.CAP
		+ ", " + SOGGETTI_RESPONSABILI.COMUNE_ISTAT
		
		+ " ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private final String INSERT_PARTECIPANTE_RUBRICA =
		"INSERT INTO "
		+ SOGGETTI_RESPONSABILI.TABLE_NAME
		+ " (" 
		//+ SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE + ", " 
		+ SOGGETTI_RESPONSABILI.DATA_INIZIO_RES
		+ ", " + SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE
		+ ", " + SOGGETTI_RESPONSABILI.COGNOME
		+ ", " + SOGGETTI_RESPONSABILI.EMAIL
		+ ", " + SOGGETTI_RESPONSABILI.FAX		
		+ ", " + SOGGETTI_RESPONSABILI.NOME
		+ ", " + SOGGETTI_RESPONSABILI.TELEFONO
		+ ", " + SOGGETTI_RESPONSABILI.INDIRIZZO
		+ ", " + SOGGETTI_RESPONSABILI.CAP
		+ ", " + SOGGETTI_RESPONSABILI.COMUNE_ISTAT
		
		+ " ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	
	private final String cancellazionePartecipante =
		"UPDATE "
		+ SOGGETTI_RESPONSABILI.TABLE_NAME
		+ " SET "
		+ SOGGETTI_RESPONSABILI.DATA_FINE_RES + "= ?" // 1
		+ " WHERE "
		+ SOGGETTI_RESPONSABILI.ID_RESPONSABILE + " = ?"
		+" AND "
		+ SOGGETTI_RESPONSABILI.DATA_INIZIO_RES + " = ?"; //9
	
	//Per ora lascio il controllo sull'hashtable perche' l'utente cs/rup potra' probabilmente visualizzare i partecipanti di una precisa stazione appaltante.
	// Al momento faccio si' che possa vederli tutti
	
	/********************************************************************************************************
	 * Restituisce la lista di tutti i soggetti Partecipanti effettuando una ricerca in base a denominazione 
	 * e codice fiscale. L'inserimento nella TableBean  inizia dal valore contenuto in startRow ed � 
	 * possibile effettuare un numero massimo di inserimenti pari a count.
	 * 
	 * @param denominazione String contenente la demoninazione da ricercare
	 * @param codiceFiscale String contenente il codice fiscale da ricercare
	 * @param startRow int per l'indice da cui iniziare per l'inserimento
	 * @param count int che indica il massimo numero di inserimenti possibili. 
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getPartecipantiList(
			String denominazione,
			String codiceFiscale,
			//Hashtable listaSAAbilitato,
			int startRow,
			int count
			) throws SQLException {

		String selectRicerca = BASE_SELECT_SOGGETTI_RESPONSABILI;
		String condizioni=null;
		StringTokenizer tokenDenominazione = null;
		String helpString = null;
		denominazione = cancPercentuale(denominazione);
		if ("".equalsIgnoreCase(denominazione) && "".equalsIgnoreCase(codiceFiscale) ) return new TableBean();
		
		if ( denominazione.trim().length() > 0 ) {
			
			tokenDenominazione = new StringTokenizer( denominazione );
			helpString = getCleanToken( tokenDenominazione.nextToken() );
			
			condizioni = SOGGETTI_RESPONSABILI.T_COGNOME + " LIKE '%" + helpString + "%'";
			condizioni += " OR "+SOGGETTI_RESPONSABILI.T_NOME + " LIKE '%" + helpString + "%'";
			//if (tokenDenominazione.hasMoreElements())
			//	helpString = getCleanToken( tokenDenominazione.nextToken() );
			//	condizioni += " OR "+SOGGETTI_RESPONSABILI.T_NOME + " LIKE '%" + getCleanToken( tokenDenominazione.nextToken() ) + "%'";
			while ( tokenDenominazione.hasMoreElements() ) {
				helpString = getCleanToken( tokenDenominazione.nextToken() );
				condizioni += " OR " + SOGGETTI_RESPONSABILI.T_COGNOME + " LIKE '%" + helpString + "%'";
				//if(tokenDenominazione.hasMoreElements())
				condizioni += " OR " + SOGGETTI_RESPONSABILI.T_NOME + " LIKE '%" + helpString + "%'";
			}
			/*
			tokenDenominazione = new StringTokenizer(denominazione);
			while(tokenDenominazione.hasMoreElements()) {
				condizioni = SOGGETTI_RESPONSABILI.T_COGNOME + " LIKE '%" + getCleanToken( tokenDenominazione.nextToken() ) + "%'";
				condizioni += " OR "+SOGGETTI_RESPONSABILI.T_NOME + " LIKE '%" + getCleanToken( tokenDenominazione.nextToken() ) + "%'";
			}
			*/
			selectRicerca +=
				" AND ( "
				+ condizioni
				+ ") AND "+ SOGGETTI_RESPONSABILI.DATA_FINE_RES+ " is NULL";
			
			logger.debug("selectRicerca"+selectRicerca);
		}
 		if ( codiceFiscale.trim().length() > 0 ) {
			
			tokenDenominazione = new StringTokenizer( codiceFiscale );
			condizioni = SOGGETTI_RESPONSABILI.T_CODICE_FISCALE_RESPONSABILE + " = '" + getCleanToken( tokenDenominazione.nextToken() ) + "'";
			while ( tokenDenominazione.hasMoreElements() ) {
				condizioni += " OR " + SOGGETTI_RESPONSABILI.T_CODICE_FISCALE_RESPONSABILE + " = '" + getCleanToken( tokenDenominazione.nextToken() ) + "'";
			}
			
			selectRicerca +=
				" AND ( "
				+ condizioni
				+ ") AND "+ SOGGETTI_RESPONSABILI.DATA_FINE_RES+ " is NULL";;
			logger.debug("selectRicerca"+selectRicerca);
		}
		
		//PP aggiunta order by
		selectRicerca += BASE_ORDERBY;
		
		logger.debug("Visualizzazione Elenco Partecipanti [" + selectRicerca + "]");
		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TableBean result = null;
		try{
		pstmt = activeConnection.prepareStatement(selectRicerca);
		rs = pstmt.executeQuery();
//		int posCounter = 1;
		
		//pstmt = fillPstmt(pstmt, posCounter, listaSAAbilitato);
		
		result = new TableBean (rs , startRow, count );
		
		logger.debug( "Risultato per la query [" + selectRicerca + "] Tuple [" + result.getTableSize() + "]");
		}
		finally{
			close(rs,pstmt);
		}
		return result;
		
	}
	
	/****************************************************************************************************
	 * Il metodo restituisce la stringa passatagli in ingresso ma priva
	 * di tutte le ricorrenze del carattere "%"
	 * 
	 * @param strConPercentuale String in ingresso che si parsa per eliminare i "%"
	 * @return String. 
	 */
	public String cancPercentuale(String strConPercentuale) {
		String tempString = "";
		String log = "";
		boolean soloBlank = true;
		
		// cancello i % ed effettuo il trim per gli spazi bianchi
		for (int i=0; i < strConPercentuale.length();i++ ){
			if ( !(strConPercentuale.charAt(i) == '%') )
				tempString = tempString + strConPercentuale.charAt(i);
		}
		tempString = tempString.trim();
		return tempString;
	} 
	
	
	
	
	/******************************************************************************************************
	 * Effettua la ricerca dei Soggetti partecipanti in base a nome, cognome e codice fiscale
	 *
	 * @param cognome String
	 * @param nome String
	 * @param codiceFiscale String
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getSoggettiPartecipantiRubTab(
			String cognome,
			String nome,
			String codiceFiscale
			) throws SQLException {

		String selectRicerca = BASE_SELECT_SOGGETTI_RESPONSABILI;
		String condizioni=null;
		StringTokenizer tokenDenominazione = null;
		
		cognome = cancPercentuale(cognome);
		nome = cancPercentuale(nome);
		codiceFiscale = cancPercentuale(codiceFiscale);
		
		if  (
				(cognome.equalsIgnoreCase("") || cognome == null) &&
				(nome.equalsIgnoreCase("") || nome == null) &&
				(codiceFiscale.equalsIgnoreCase("") || codiceFiscale == null) 
			) 
			return new TableBean();
			//cognome="$&"; // per avere una ricerca con risultato vuoto
		
		if ( cognome.trim().length() > 0 ) {
			
			tokenDenominazione = new StringTokenizer( cognome );
			condizioni = SOGGETTI_RESPONSABILI.T_COGNOME + " LIKE '%" + getCleanToken( tokenDenominazione.nextToken() ) + "%'";
			//condizioni += " OR "+SOGGETTI_RESPONSABILI.T_NOME + " LIKE '%" + getCleanToken( tokenDenominazione.nextToken() ) + "%'";
			while ( tokenDenominazione.hasMoreElements() ) {
				condizioni += " OR " + SOGGETTI_RESPONSABILI.T_COGNOME + " LIKE '%" + getCleanToken( tokenDenominazione.nextToken() ) + "%'";
				//condizioni += " OR " + SOGGETTI_RESPONSABILI.T_NOME + " LIKE '%" + getCleanToken( tokenDenominazione.nextToken() ) + "%'";
			}
			
			selectRicerca +=
				" AND ( "
				+ condizioni
				+ ")";
			
			logger.debug("selectRicerca"+selectRicerca);
		}
		if ( nome.trim().length() > 0 ) {
			
			tokenDenominazione = new StringTokenizer( nome );
			condizioni = SOGGETTI_RESPONSABILI.T_NOME+ " LIKE '%" + getCleanToken( tokenDenominazione.nextToken() ) + "%'";
			while ( tokenDenominazione.hasMoreElements() ) {
				condizioni += " OR " + SOGGETTI_RESPONSABILI.T_NOME + " LIKE '%" + getCleanToken( tokenDenominazione.nextToken() ) + "%'";
			}
			selectRicerca +=
				" AND ( "
				+ condizioni
				+ ")";
			logger.debug("selectRicerca"+selectRicerca);
			
		}
		
		
		
		logger.debug("codice fiscale:::"+codiceFiscale);
		//if ( codiceFiscale.equalsIgnoreCase("") || codiceFiscale == null) codiceFiscale="%%"; // per effettuare la ricerca su tutti i codici fiscali
		if ( codiceFiscale.trim().length() > 0 ) {
			
			tokenDenominazione = new StringTokenizer( codiceFiscale );
			condizioni = SOGGETTI_RESPONSABILI.T_CODICE_FISCALE_RESPONSABILE + " = '" + getCleanToken( tokenDenominazione.nextToken() ) + "'";
			while ( tokenDenominazione.hasMoreElements() ) {
				condizioni += " OR " + SOGGETTI_RESPONSABILI.T_CODICE_FISCALE_RESPONSABILE + " = '" + getCleanToken( tokenDenominazione.nextToken() ) + "'";
			}
			selectRicerca +=
				" AND ( "
				+ condizioni
				+ ")";
			logger.debug("selectRicerca"+selectRicerca);
			
		}
		
		logger.debug("selectRicerca"+selectRicerca);
		selectRicerca += BASE_ORDERBY;
		
		logger.debug("Visualizzazione Elenco Partecipanti [" + selectRicerca + "]");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TableBean result = null;
		try{
		pstmt = activeConnection.prepareStatement(selectRicerca);
		rs = pstmt.executeQuery();
		result = new TableBean ( rs );
		}
		finally{
			close(rs,pstmt);
		}
		return result;
		
	}
	
	
	/***********************************************************************************************
	 * Restituisce i dettagli di un partecipante identificato attraverso Id. 
	 * 
	 * @param id_partecipante int 
	 * @param startRow int indicante l'indice da cui iniziare per l'inserimento nella TableBean
	 * @param count int indicante il massimo numero di elementi inseribili nella TableBean
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getDettaglioPartecipante(
			int id_partecipante,
			//Hashtable listaSAAbilitato,
			int startRow,
			int count
			) throws SQLException {

		String selectRicerca = BASE_SELECT_DETTAGLIO_SOGGETTI_PARTECIPANTI;
		String condizioniId=null;
		StringTokenizer tokenId = null;
		
		if ( id_partecipante != 0 ) {
			
			tokenId = new StringTokenizer( String.valueOf(id_partecipante) );
			condizioniId = " WHERE "+SOGGETTI_RESPONSABILI.T_ID_RESPONSABILE + " = " + Integer.parseInt(getCleanToken( tokenId.nextToken() ));
			while ( tokenId.hasMoreElements() ) {
				condizioniId += " OR " + SOGGETTI_RESPONSABILI.T_ID_RESPONSABILE + " = " + Integer.parseInt(getCleanToken( tokenId.nextToken() ));
			}
			selectRicerca +=condizioniId+" AND "+SOGGETTI_RESPONSABILI.DATA_FINE_RES+ " is NULL";
			
			
			logger.debug("selectRicerca"+selectRicerca);
		}
		logger.debug("Visualizzazione Dettaglio Partecipante [" + selectRicerca + "]");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TableBean result = null;
		try{
		pstmt = activeConnection.prepareStatement(selectRicerca);
		rs = pstmt.executeQuery();
//		int posCounter = 1;
		
		//pstmt = fillPstmt(pstmt, posCounter, listaSAAbilitato);
		result = new TableBean ( rs, startRow, count );
		
		logger.debug( "Risultato per la query dettaglio partecipante[" + selectRicerca + "] Tuple [" + result.getTableSize() + "]");
		}
		finally{
			close(rs,pstmt);
		}
		return result;
		
	}
	
	/**************************************************************************************
	 * il metodo restituisce uno storico dei responsabili. 
	 * 
	 * @param id_responsabile int 
	 * @param startRow int 
	 * @param count int 
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getStoricoResponsabili(
											int id_responsabile,
											int startRow,
											int count
											) throws SQLException {
		String selectStorico = BASE_SELECT_DETTAGLIO_SOGGETTI_PARTECIPANTI;
		String condizioniId=null;
		StringTokenizer tokenId = null;
		
		if ( id_responsabile != 0 ) {
			
			tokenId = new StringTokenizer( String.valueOf(id_responsabile) );
			condizioniId = " WHERE "+SOGGETTI_RESPONSABILI.T_ID_RESPONSABILE + " = " + Integer.parseInt(getCleanToken( tokenId.nextToken() ));
			while ( tokenId.hasMoreElements() ) {
				condizioniId += " OR " + SOGGETTI_RESPONSABILI.T_ID_RESPONSABILE + " = " + Integer.parseInt(getCleanToken( tokenId.nextToken() ));
			}
			selectStorico +=condizioniId+" AND DATA_FINE_RES is not null " ;
			selectStorico += "ORDER BY "+SOGGETTI_RESPONSABILI.DATA_FINE_RES;
			
		}
		logger.debug("Visualizzazione Storico Responsabili [" + selectStorico + "]");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TableBean result = null;
		try{
		    pstmt = activeConnection.prepareStatement(selectStorico);
		    rs = pstmt.executeQuery();
	    	//pstmt = fillPstmt(pstmt, posCounter, listaSAAbilitato);
	    	result = new TableBean (rs , startRow, count );
		
	    	logger.debug( "Risultato per la query dettaglio Responsabile[" + selectStorico + "] Tuple [" + result.getTableSize() + "]");
		}
		finally{
			close(rs,pstmt);
		}
	    return result;	
	}
	//====================================================================================================
	
	/*****************************************************************************************************
	 * Restituisce il token formattato per poter accettare il carattere di apostrofo
	 * 
	 * param currentToken
	 * return String
	 */
	private String getCleanToken ( String currentToken ) {
		if ( currentToken.contains("\'") ) {
			
			currentToken = currentToken.replaceAll("'", "''");
			//int apostrophePosition = currentToken.indexOf("'");
			//String firstPart = currentToken.substring(0, apostrophePosition);
			//String second = currentToken.substring(apostrophePosition + 1);
			//currentToken = firstPart + "''" + second;
		}
		return currentToken;
	}
	
	/** riattivata per modifiche in corso d'opera
	 * @param partecipanteDaAggiornare
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int modificaPartecipante ( RubricaResponsabili partecipanteDaAggiornare, Timestamp data ) throws SQLException, ClassNotFoundException {

		logger.debug("Modifica partecipante [" + partecipanteDaAggiornare + "]");
		PreparedStatement updateRubricaFunction = null;
		int ind = 0;
		try {
			//data odierna
			updateRubricaFunction = activeConnection.prepareStatement(AGGIORNAMENTO_SOGGETTO);
			updateRubricaFunction.setObject(++ind, partecipanteDaAggiornare.getCodice_fiscale_responsabile().toUpperCase());
			updateRubricaFunction.setObject(++ind, partecipanteDaAggiornare.getCognome());
			updateRubricaFunction.setObject(++ind, partecipanteDaAggiornare.getData_fine_res());
			updateRubricaFunction.setObject(++ind, partecipanteDaAggiornare.getEmail());
			updateRubricaFunction.setObject(++ind, partecipanteDaAggiornare.getFax());
			updateRubricaFunction.setObject(++ind, partecipanteDaAggiornare.getNome());
			updateRubricaFunction.setObject(++ind, partecipanteDaAggiornare.getTelefono());
			updateRubricaFunction.setObject(++ind, partecipanteDaAggiornare.getIndirizzo());
			updateRubricaFunction.setObject(++ind, partecipanteDaAggiornare.getCap());
			updateRubricaFunction.setObject(++ind, partecipanteDaAggiornare.getComuneIstat());

			updateRubricaFunction.setLong(++ind, partecipanteDaAggiornare.getId_soggetto_responsabile());
			updateRubricaFunction.setObject(++ind, data);

			int result = updateRubricaFunction.executeUpdate();
			
			logger.debug ( "Eseguita query [" + AGGIORNAMENTO_SOGGETTO + "] per idPartecipante [" + partecipanteDaAggiornare.getId_soggetto_responsabile() + "]" );
			
			return result;
		} finally {
			try {
				updateRubricaFunction.close();
			} catch ( Exception e ) {}
			updateRubricaFunction = null;			
		}
	}
	
	
	/*********************************************************************************************************
	 * Inserisce o modifica un Responsabile a seconda del valore del parametro updateFlag. Se updateFlag e' False 
	 * si effettua un inserimento, se updateFlag e'True si effettua una modifica
	 *
	 * @param partecipanteDaInserire RubricaResponsabili
	 * @param updateFlag boolean
	 * @return Object[]
	 * @throws SQLException
	 */
	public Object[] insertPartecipante ( RubricaResponsabili partecipanteDaInserire, boolean updateFlag ) throws SQLException {

		logger.debug("Modifica partecipante [" + partecipanteDaInserire + "]");
		PreparedStatement insertRubrica = null;
		ResultSet rs = null;
		
		Timestamp dataInizioSoggetto = getNow();
		Long idSoggettoPartecipante;
		
		Object[] retVal = new Object[2];
		retVal[1]=dataInizioSoggetto;
		
		int i = 1;
		try {
		
			if(!updateFlag){
				//idSoggettoPartecipante= (getMaxIndex(SOGGETTI_PARTECIPANTI.TABLE_NAME, SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE))+1;
				insertRubrica = activeConnection.prepareStatement(createInsertQuery(INSERT_PARTECIPANTE_RUBRICA,SOGGETTI_RESPONSABILI.ID_RESPONSABILE));
				
				logger.debug("INSERT_PARTECIPANTE_RUBRICA:::"+INSERT_PARTECIPANTE_RUBRICA);
				
			}else{
				insertRubrica = activeConnection.prepareStatement(createCopyRecord(UPDATE_PARTECIPANTE_RUBRICA_RESPONSABILI,SOGGETTI_RESPONSABILI.TABLE_NAME)); //non e copy record ma serve...
				idSoggettoPartecipante = partecipanteDaInserire.getId_soggetto_responsabile();
			
				insertRubrica.setObject(i++, idSoggettoPartecipante);
				logger.debug("Modifica idSoggettoPartecipante 1[" + idSoggettoPartecipante + "]");
			}
			
			insertRubrica.setObject(i++, dataInizioSoggetto);		
			insertRubrica.setObject(i++, partecipanteDaInserire.getCodice_fiscale_responsabile().toUpperCase());
			insertRubrica.setString(i++, partecipanteDaInserire.getCognome());
			insertRubrica.setString(i++, partecipanteDaInserire.getEmail());
			insertRubrica.setString(i++, partecipanteDaInserire.getFax());
			insertRubrica.setString(i++, partecipanteDaInserire.getNome());
			insertRubrica.setString(i++, partecipanteDaInserire.getTelefono());
			insertRubrica.setString(i++, partecipanteDaInserire.getIndirizzo());
			insertRubrica.setString(i++, partecipanteDaInserire.getCap());
			insertRubrica.setString(i++, partecipanteDaInserire.getComuneIstat());
			
			if(insertRubrica.execute()){
				rs = insertRubrica.getResultSet();
				rs.next();
				retVal[0] = rs.getLong(SOGGETTI_RESPONSABILI.ID_RESPONSABILE);
			}			
			return retVal;
		} finally {
			close(rs,insertRubrica);
//			try {
//				close(rs,insertRubrica);
//			} catch ( Exception e ) {}
//			insertRubrica = null;
//			rs = null;
		}
	}

	/*********************************************************************************************************
	 * ONLY MASSLOADER: !attenzione duplicazione codice!
	 * modifica un partecipante della rubrica, supporta modifiche (inserimenti veloci)
	 * aggiunge un secondo alla vecchia data inizio se resulta che ci si trovi nello stesso
	 * secondo.
	 *
	 * @param responsabileDaInserire
	 * @param vecchiaDataInizio
	 * @return Object[]
	 * @throws SQLException
	 */
	public Object[] inserisciResponsabileAvendoId ( RubricaResponsabili responsabileDaInserire, Timestamp vecchiaDataInizio ) throws SQLException {

		
		PreparedStatement insertRubrica = null;
		ResultSet rs = null;
		
		Timestamp dataInizioSoggetto = super.getTimestampPlusOneSecondIfInTheSameSecond(getNow(), vecchiaDataInizio);
		logger.debug("Modifica partecipante [" + responsabileDaInserire + "], Vecchia dataInizio ["+vecchiaDataInizio+"], Nuova dataInizio ["+dataInizioSoggetto+"]");
		Long idSoggettoPartecipante;
		
		Object[] retVal = new Object[2];
		retVal[1]=dataInizioSoggetto;
		
		int i = 1;
		try {

			insertRubrica = activeConnection.prepareStatement(createCopyRecord(UPDATE_PARTECIPANTE_RUBRICA_RESPONSABILI,SOGGETTI_RESPONSABILI.TABLE_NAME)); //non e copy record ma serve...
			idSoggettoPartecipante = responsabileDaInserire.getId_soggetto_responsabile();
		
			insertRubrica.setObject(i++, idSoggettoPartecipante);
			logger.debug("Modifica idSoggettoPartecipante 1[" + idSoggettoPartecipante + "]");

			
			insertRubrica.setObject(i++, dataInizioSoggetto);		
			insertRubrica.setObject(i++, responsabileDaInserire.getCodice_fiscale_responsabile() != null ? responsabileDaInserire.getCodice_fiscale_responsabile().toUpperCase() : null);
			insertRubrica.setString(i++, responsabileDaInserire.getCognome());
			insertRubrica.setString(i++, responsabileDaInserire.getEmail());
			insertRubrica.setString(i++, responsabileDaInserire.getFax());
			insertRubrica.setString(i++, responsabileDaInserire.getNome());
			insertRubrica.setString(i++, responsabileDaInserire.getTelefono());
			insertRubrica.setString(i++, responsabileDaInserire.getIndirizzo());
			insertRubrica.setString(i++, responsabileDaInserire.getCap());
			insertRubrica.setString(i++, responsabileDaInserire.getComuneIstat());
			
			insertRubrica.execute();
//			if(insertRubrica.execute()){
//				rs = insertRubrica.getResultSet();
//				rs.next();
//				retVal[0] = rs.getLong(SOGGETTI_RESPONSABILI.ID_RESPONSABILE);
//			}			
				retVal[0] = idSoggettoPartecipante;
			return retVal;
		}finally {
			close(rs,insertRubrica);
		}
	}
	/**
	 * Metodo che si occupa dell'aggiornamento di una anagrafica,
	 * esegue:
	 * - cancellazione logica
	 * - aggiornamento record attuale.
	 * - il bean passato ora contiene id e nuova data inizio
	 *
	 * @param sr
	 * @param anagrafica_responsabile
	 * @param vecchiaDataInizio
	 * @return
	 * @throws SQLException
	 */
	public boolean aggiornamentoConCancellazioneLogica(SoggettoResponsabileBean sr,RubricaResponsabili anagrafica_responsabile, Timestamp vecchiaDataInizio)throws SQLException{
		try{
			this.cancellaPartecipante(anagrafica_responsabile);
			Object[] chiavi = this.inserisciResponsabileAvendoId(anagrafica_responsabile, vecchiaDataInizio);
			if(chiavi != null){
				sr.setIdResponsabile(((Long)chiavi[0]).longValue());
				sr.setDataInizioRes((Timestamp)chiavi[1]);
//				anagrafica_responsabile.setId_soggetto_responsabile((Long)chiavi[0]);
//				anagrafica_responsabile.setData_inizio_res(PageHelper.getDBDateFromTS((Timestamp)chiavi[1]));
				return true;
			}return false;
		}catch(SQLException sqle){
			logger.error(sqle.getMessage());
			throw sqle;
		}catch(ClassNotFoundException e){
			logger.error("Eccezione: " + e.getMessage());
			return false;
		}
	}
	
	
	
	/************************************************************************************************
	 * cancella un responsabile 
	 * 
	 * @param partecipanteDaCancellare Rubricaresponsabili
	 * @return int elementi cancellati
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int cancellaPartecipante ( RubricaResponsabili partecipanteDaCancellare ) throws SQLException, ClassNotFoundException {

		logger.debug("Cancella resposabile [" + cancellazionePartecipante + "]");
		PreparedStatement updateRubricaFunction = null;
		
		try {
			//data odierna
			//Timestamp dataFineSoggetto = new Timestamp((System.currentTimeMillis()/1000)*1000);
			Timestamp dataFineSoggetto = getNow();
			updateRubricaFunction = activeConnection.prepareStatement(cancellazionePartecipante);
			
			updateRubricaFunction.setObject(1, dataFineSoggetto);
			logger.debug("Cancella data fine responsabile 1xxxxxxxxx[" + dataFineSoggetto + "]");

			updateRubricaFunction.setObject(2, partecipanteDaCancellare.getId_soggetto_responsabile());
			logger.debug("Cancella id soggetto responsabile 2xxxxxxxxx[" + partecipanteDaCancellare.getId_soggetto_responsabile() + "]");
			
			updateRubricaFunction.setObject(3, PageHelper.getFormattedDBDateTime(partecipanteDaCancellare.getData_inizio_res()));
			logger.debug("Cancella data inizio soggetto responsabile 3xxxxxxxxx[" + partecipanteDaCancellare.getData_inizio_res() + "]");
			
			int result = updateRubricaFunction.executeUpdate();
			
			logger.debug("Numero righe modificate per il soggetto Responsabile ["+partecipanteDaCancellare.getId_soggetto_responsabile()+"]["+PageHelper.getFormattedDBDateTime(partecipanteDaCancellare.getData_inizio_res())+"]: " + result);
			
			logger.debug ( "Eseguita query[" + cancellazionePartecipante + "] per idPartecipante [" + partecipanteDaCancellare.getId_soggetto_responsabile() + "]" );
			logger.debug("Completato aggiornamento del partecipante [" + partecipanteDaCancellare.getId_soggetto_responsabile() + "] CIG [" + "] Esito [" + result + "]");
			
			return result;
		} finally {
			try {
				
				updateRubricaFunction.close();
			} catch ( Exception e ) {}
			updateRubricaFunction = null;			
		}
	}
	
	
	private final String SELECT_COD_FISC_SOGGETTI_PARTECIPANTI =
		"SELECT "	
		+ SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE
		+ " FROM " + SOGGETTI_RESPONSABILI.TABLE_NAME
		+ " WHERE " + SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE + " = ?" 
		+ " AND " + SOGGETTI_RESPONSABILI.DATA_FINE_RES + " IS NULL";	


	/*******************************************************************************************************
	 * metodo che restituisce true se il cod fisc non e' presente nel db, false in 
	 * caso contrario
	 *
	 * @param codFisc String
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean checkCF(String codFisc) throws SQLException {
		boolean isCodFisc=true;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectRicerca = SELECT_COD_FISC_SOGGETTI_PARTECIPANTI;
		
		try{
		pstmt = activeConnection.prepareStatement(selectRicerca);
		pstmt.setString(1, codFisc.toUpperCase());
		
		rs = pstmt.executeQuery();
		isCodFisc=!rs.next();
		}
		finally{
		close(rs,pstmt);
		}
		return isCodFisc;
	}	
	
	private final String SELECT_BY_COD_FISC =
		"SELECT " + SOGGETTI_RESPONSABILI.ID_RESPONSABILE 
		+ "," + SOGGETTI_RESPONSABILI.DATA_INIZIO_RES
		+ " FROM " + SOGGETTI_RESPONSABILI.TABLE_NAME
		+ " WHERE " + SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE + " = ?" 
		+ " AND " + SOGGETTI_RESPONSABILI.DATA_FINE_RES + " IS NULL";	
	

	/***********************************************************************************************
	 * metodo che restituisce Id Responsabile e Data Inizio Responsabile del soggetto 
	 * in base al codice fiscale
	 * 
	 * @param codFisc
	 * @return Object[]
	 * @throws SQLException
	 */
	public Object[] getSoggettoByCF(String codFisc) throws SQLException {
		Object[] retVal = new Object[2];
		
		String selectRicerca = SELECT_BY_COD_FISC;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			pstmt = activeConnection.prepareStatement(selectRicerca);
	    	pstmt.setString(1, codFisc.toUpperCase());
		
     		rs = pstmt.executeQuery();
    		if(rs.next()) {
	    		retVal[0] = rs.getLong(SOGGETTI_RESPONSABILI.ID_RESPONSABILE );
		    	retVal[1] = rs.getTimestamp(SOGGETTI_RESPONSABILI.DATA_INIZIO_RES);
	    	}
		}
		finally{
    		close(rs,pstmt);
		}
		return retVal;
	}
	private final String SELECT_ALL_BY_COD_FISC =
		"SELECT " + SOGGETTI_RESPONSABILI.TABLE_NAME + ".* "
		+ " FROM " + SOGGETTI_RESPONSABILI.TABLE_NAME
		+ " WHERE " + SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE + " = ?" 
		+ " AND " + SOGGETTI_RESPONSABILI.DATA_FINE_RES + " IS NULL"
		// XXX: patch nel caso in cui ci siano 2 record con data fine a null deve prendere il piu recente
		+ " ORDER BY " + SOGGETTI_RESPONSABILI.DATA_INIZIO_RES + " DESC";	;
	
	public SoggettoResponsabileBean getAllSoggettoResponsabileByCF(String codFisc) throws SQLException{
		
		SoggettoResponsabileBean srb = new SoggettoResponsabileBean();
		
		String selectRicerca = SELECT_ALL_BY_COD_FISC;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
		pstmt = activeConnection.prepareStatement(selectRicerca);
		pstmt.setString(1, codFisc.toUpperCase());
		
		rs = pstmt.executeQuery();
		// NOTA CHE POTREBBE ESSERCI PIU DI UN RECORD MA PRENDO SOLO IL PRIMO
		if(rs.next()) {
			srb.setIdResponsabile( rs.getLong(SOGGETTI_RESPONSABILI.ID_RESPONSABILE ) );
			srb.setDataInizioRes( rs.getTimestamp(SOGGETTI_RESPONSABILI.DATA_INIZIO_RES) );
			srb.setCap(rs.getString(SOGGETTI_RESPONSABILI.CAP));
			srb.setCodiceFiscaleResponsabile(rs.getString(SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE));
			srb.setCognome(rs.getString(SOGGETTI_RESPONSABILI.COGNOME));			
			srb.setComuneIstat(rs.getString(SOGGETTI_RESPONSABILI.COMUNE_ISTAT));
			srb.setDataInizioRes(rs.getTimestamp(SOGGETTI_RESPONSABILI.DATA_INIZIO_RES));
			//srb.setDataFineRes(dataFineRes);
			srb.setEmail(rs.getString(SOGGETTI_RESPONSABILI.EMAIL));
			srb.setFax(rs.getString(SOGGETTI_RESPONSABILI.FAX));
			srb.setIndirizzo(rs.getString(SOGGETTI_RESPONSABILI.INDIRIZZO));
			srb.setNome(rs.getString(SOGGETTI_RESPONSABILI.NOME));
			srb.setTelefono(rs.getString(SOGGETTI_RESPONSABILI.TELEFONO));
		}
		}
		finally{
	    	close(rs,pstmt);
		}
		return srb;		
	}
	
}
