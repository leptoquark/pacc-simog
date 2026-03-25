package it.avlp.simog.rubricamanager;

import it.avlp.simog.beans.Rubrica;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.SOGGETTI_PARTECIPANTI;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/* Questa classe gestira' tutti gli eventuali accessi 
 * al db per le funzioni legate alla rubrica*/

public class RubricaManager extends AccessiDB{

	
	public RubricaManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	//SELECT CHE ESTRAE TUTTI I PARTECIPANTI ALLE GARE CHE IL RUP/CS PUO' VEDERE
	private final String BASE_SELECT_SOGGETTI_PARTECIPANTI =
		
		"SELECT "
		+ SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE
		+ ", " + SOGGETTI_PARTECIPANTI.CODICE_FISCALE
		+ ", " + SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG
		+ ", " + SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG
		+ ", " + SOGGETTI_PARTECIPANTI.DENOMINAZIONE		
		+ ", " + SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE
		+ ", " + SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO
		+ ", " + SOGGETTI_PARTECIPANTI.PARTITA_IVA
		+ ", " + SOGGETTI_PARTECIPANTI.INDIRIZZO
		+ ", " + SOGGETTI_PARTECIPANTI.CIVICO
		+ ", " + SOGGETTI_PARTECIPANTI.CAP
		+ ", " + SOGGETTI_PARTECIPANTI.CITTA
		+ ", " + SOGGETTI_PARTECIPANTI.PROVINCIA
		+ ", " + SOGGETTI_PARTECIPANTI.NOME
		+ ", " + SOGGETTI_PARTECIPANTI.COGNOME
    	+ ", " + SOGGETTI_PARTECIPANTI.ID_STATO									
		+ " FROM " + SOGGETTI_PARTECIPANTI.TABLE_NAME
		//+ " WHERE "+ SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG + "= ''" ;	
		+ " WHERE "+ SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG+ " is NULL";
	
//	private final String SELECT_SOGGETTI_RUBRICA = 
//		
//		"SELECT"
//		+ SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE
//		+ ", " + SOGGETTI_PARTECIPANTI.CODICE_FISCALE
//		+ ", " + SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG
//		+ ", " + SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG
//		+ ", " + SOGGETTI_PARTECIPANTI.DENOMINAZIONE		
//		+ ", " + SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE
//		+ ", " + SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO
//		+ ", " + SOGGETTI_PARTECIPANTI.PARTITA_IVA
//		+ ", " + SOGGETTI_PARTECIPANTI.INDIRIZZO
//		+ ", " + SOGGETTI_PARTECIPANTI.CIVICO
//		+ ", " + SOGGETTI_PARTECIPANTI.CAP
//		+ ", " + SOGGETTI_PARTECIPANTI.CITTA
//		+ ", " + SOGGETTI_PARTECIPANTI.PROVINCIA
//		+ ", " + SOGGETTI_PARTECIPANTI.NOME
//		+ ", " + SOGGETTI_PARTECIPANTI.COGNOME
//		+ ", " + SOGGETTI_PARTECIPANTI.ID_STATO							
//		+ " FROM " 
//		+ SOGGETTI_PARTECIPANTI.TABLE_NAME
//		+ " WHERE "
//		+ SOGGETTI_PARTECIPANTI.CODICE_FISCALE + " = ? AND "
//		+ SOGGETTI_PARTECIPANTI.DENOMINAZIONE + " LIKE '%?%' AND "
//		+ SOGGETTI_PARTECIPANTI.NOME + "LIKE '%?%' AND "
//		+ SOGGETTI_PARTECIPANTI.COGNOME + "LIKE '%?%' "
//		;
		
	private final String BASE_ORDERBY = " ORDER BY " + SOGGETTI_PARTECIPANTI.DENOMINAZIONE;
	
	private final String BASE_SELECT_DETTAGLIO_SOGGETTI_PARTECIPANTI =
		"SELECT "
		+ SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE
		+ ", " + SOGGETTI_PARTECIPANTI.CODICE_FISCALE
		+ ", " + SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG
		+ ", " + SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG
		+ ", " + SOGGETTI_PARTECIPANTI.DENOMINAZIONE		
		+ ", " + SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE
		+ ", " + SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO
		+ ", " + SOGGETTI_PARTECIPANTI.PARTITA_IVA
		+ ", " + SOGGETTI_PARTECIPANTI.INDIRIZZO
		+ ", " + SOGGETTI_PARTECIPANTI.CIVICO
		+ ", " + SOGGETTI_PARTECIPANTI.CAP
		+ ", " + SOGGETTI_PARTECIPANTI.CITTA
		+ ", " + SOGGETTI_PARTECIPANTI.PROVINCIA
		+ ", " + SOGGETTI_PARTECIPANTI.NOME
		+ ", " + SOGGETTI_PARTECIPANTI.COGNOME
		+ ", " + SOGGETTI_PARTECIPANTI.ID_STATO								
		+ " FROM " + SOGGETTI_PARTECIPANTI.TABLE_NAME;	
	
//	private final String aggiornamentoPartecipante =
//		"UPDATE "
//		+ SOGGETTI_PARTECIPANTI.TABLE_NAME
//		+ " SET "
//		+ SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE + "= ?" // 1
//		+ ", " + SOGGETTI_PARTECIPANTI.CODICE_FISCALE + " =?" //2
//		+ ", " + SOGGETTI_PARTECIPANTI.DENOMINAZIONE + " =?" //5
//		+ ", " + SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO + "=?" //6		
//		+ ", " + SOGGETTI_PARTECIPANTI.PARTITA_IVA + "=?" //7
//		+ ", " + SOGGETTI_PARTECIPANTI.INDIRIZZO + "=?" //8
//		+ ", " + SOGGETTI_PARTECIPANTI.CIVICO + "=?" //9
//		+ ", " + SOGGETTI_PARTECIPANTI.CAP + "=?" //10
//		+ ", " + SOGGETTI_PARTECIPANTI.CITTA + "=?" //11
//		+ ", " + SOGGETTI_PARTECIPANTI.PROVINCIA + "=?" //12
//		+ ", " + SOGGETTI_PARTECIPANTI.NOME + "=?" //13
//		+ ", " + SOGGETTI_PARTECIPANTI.COGNOME + "=?" //14	
//		+ ", " + SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG + " =?" //4
//		+ ", " + SOGGETTI_PARTECIPANTI.ID_STATO + " =?"	//15
//		+ " WHERE "
//		+ SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE + " = ?"
//		+" AND "
//		+ SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG + " = ?"; //9
	
	private final String UPDATE_PARTECIPANTE_RUBRICA =
		"INSERT INTO "
		+ SOGGETTI_PARTECIPANTI.TABLE_NAME
		+ " (" 
		+ SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE
		+ ", " + SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG
		+ ", " + SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE
		+ ", " + SOGGETTI_PARTECIPANTI.CODICE_FISCALE
		+ ", " + SOGGETTI_PARTECIPANTI.DENOMINAZIONE
		+ ", " + SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO		
		+ ", " + SOGGETTI_PARTECIPANTI.PARTITA_IVA
		+ ", " + SOGGETTI_PARTECIPANTI.INDIRIZZO
		+ ", " + SOGGETTI_PARTECIPANTI.CIVICO
		+ ", " + SOGGETTI_PARTECIPANTI.CAP
		+ ", " + SOGGETTI_PARTECIPANTI.CITTA
		+ ", " + SOGGETTI_PARTECIPANTI.PROVINCIA
		+ ", " + SOGGETTI_PARTECIPANTI.NOME
		+ ", " + SOGGETTI_PARTECIPANTI.COGNOME	
		+ ", " + SOGGETTI_PARTECIPANTI.ID_STATO
		+ " ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private final String INSERT_PARTECIPANTE_RUBRICA =
		"INSERT INTO "
		+ SOGGETTI_PARTECIPANTI.TABLE_NAME
		+ " (" 
		//+ SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE + ", " 
		+ SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG
		+ ", " + SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE
		+ ", " + SOGGETTI_PARTECIPANTI.CODICE_FISCALE
		+ ", " + SOGGETTI_PARTECIPANTI.DENOMINAZIONE
		+ ", " + SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO		
		+ ", " + SOGGETTI_PARTECIPANTI.PARTITA_IVA
		+ ", " + SOGGETTI_PARTECIPANTI.INDIRIZZO
		+ ", " + SOGGETTI_PARTECIPANTI.CIVICO
		+ ", " + SOGGETTI_PARTECIPANTI.CAP
		+ ", " + SOGGETTI_PARTECIPANTI.CITTA
		+ ", " + SOGGETTI_PARTECIPANTI.PROVINCIA
		+ ", " + SOGGETTI_PARTECIPANTI.NOME
		+ ", " + SOGGETTI_PARTECIPANTI.COGNOME	
		+ ", " + SOGGETTI_PARTECIPANTI.ID_STATO
		+ " ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	
	private final String cancellazionePartecipante =
		"UPDATE "
		+ SOGGETTI_PARTECIPANTI.TABLE_NAME
		+ " SET "
		+ SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG + "= ?" // 1
		+ " WHERE "
		+ SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE + " = ?"
		+" AND "
		+ SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG + " = ?"; //9
	
	//Per ora lascio il controllo sull'hashtable perche' l'utente cs/rup potra' probabilmente visualizzare i partecipanti di una precisa stazione appaltante.
	// Al momento faccio si' che possa vederli tutti
	
	/**************************************************************************************************
	 * Restituisce la lista dei partecipanti effettuando una ricerca per denominazione e/o codice fiscale. 
	 * I risultati vengono inseriti nella TableBean a partire dall'indice startRow, 
	 * gli elementi massimi nella Tablebean sono count.
	 * 
	 * @param denominazione String che contiene la denominazione o parte di essa da cercare 
	 * @param codiceFiscale String che contiene l'esatto codicefiscale da cercare
	 * @param startRow int da cui iniziare l'inserimento nella TableBean
	 * @param count Int che indica il numero massimo di elementi inseribili
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getPartecipantiList(
			String denominazione,
			String codiceFiscale,
			String id_stato, 
			//Hashtable listaSAAbilitato,
			int startRow,
			int count
			) throws SQLException {

		String selectRicerca = BASE_SELECT_SOGGETTI_PARTECIPANTI;
		String condizioni=null;
		StringTokenizer tokenDenominazione = null;
		if ("".equalsIgnoreCase(denominazione) && "".equalsIgnoreCase(codiceFiscale) && "".equalsIgnoreCase(id_stato)) return new TableBean();
		
		denominazione = cancPercentuale(denominazione);
		if ( denominazione.trim().length() > 0 ) {
			tokenDenominazione = new StringTokenizer( denominazione );
			condizioni = SOGGETTI_PARTECIPANTI.T_DENOMINAZIONE+ " LIKE '%" + getCleanToken( tokenDenominazione.nextToken() ) + "%'";
			while ( tokenDenominazione.hasMoreElements() ) {
				condizioni += " OR " + SOGGETTI_PARTECIPANTI.T_DENOMINAZIONE + " LIKE '%" + getCleanToken( tokenDenominazione.nextToken() ) + "%'";
			}
			selectRicerca +=
				" AND ( "
				+ condizioni
				+ ") AND "+ SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG+ " is NULL";			
		}
		
		if ( codiceFiscale.trim().length() > 0 ) {
			
			tokenDenominazione = new StringTokenizer( codiceFiscale );
			condizioni = SOGGETTI_PARTECIPANTI.T_CODICE_FISCALE + " = '" + getCleanToken( tokenDenominazione.nextToken() ) + "'";
			while ( tokenDenominazione.hasMoreElements() ) {
				condizioni += " OR " + SOGGETTI_PARTECIPANTI.T_CODICE_FISCALE+ " = '" + getCleanToken( tokenDenominazione.nextToken() ) + "'";
			}
			selectRicerca +=
				" AND ( "
				+ condizioni
				+ ") AND "+ SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG+ " is NULL";
			
		}

		// X-XX: Nuove condizioni per la query - Filtro id_stato
		if ( id_stato.trim().length() > 0 ) {
			condizioni = SOGGETTI_PARTECIPANTI.ID_STATO + " = '" + id_stato + "'";   					

			selectRicerca +=
				" AND ( "
				+ condizioni
				+ ") AND "+ SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG+ " is NULL";
		}

		
		//PP aggiunta order by
		selectRicerca += BASE_ORDERBY;
		
		logger.debug("Visualizzazione Elenco Partecipanti [" + selectRicerca + "]");
		
		PreparedStatement pstmt = activeConnection.prepareStatement(selectRicerca);
		
		TableBean result = new TableBean ( pstmt.executeQuery(), startRow, count );
		
		close(null, pstmt);
		
		logger.debug( "Risultato per la query [" + selectRicerca + "] Tuple [" + result.getTableSize() + "]");
		return result;
		
	}
	
	/**
	 * Il metodo restituisce la stringa passatagli in ingresso ma priva
	 * di tutte le ricorrenze del carattere %
	 * 
	 * @param strConPercentuale &egrave; la stringa in ingresso che si parsa per eliminare i %
	 * @return String 
	 */
	public String cancPercentuale(String strConPercentuale) {
		String tempString = "";
		String log = "";
		boolean soloBlank = true;
		
		// cancello i % 
		for (int i=0; i < strConPercentuale.length();i++ ){
			if ( !(strConPercentuale.charAt(i) == '%') )
				tempString = tempString + strConPercentuale.charAt(i);
		}
		tempString = tempString.trim();
		/*
			log = log+"\n\t string2:>"+tempString+"<";
			log = log+"\n\t length="+tempString.length();
			// verifico se la stringa contiene solo spazi bianchi
			for (int i=1;i <= tempString.length();i++) {
				if ( strConPercentuale.charAt(i) == ' ' ) {
					soloBlank = soloBlank && true;
					log = log+"\n\ttrovato uno spazio bianco in posizione:"+i;
				}
				else soloBlank = soloBlank && false;
			}
			log = log+"\n\t string3:>"+tempString+"<";
			
			if (soloBlank) {
				tempString = "";
				log = log+"\n\t stringa completamente bianca";
			}
			log = log+"\n\t string4:>"+tempString+"<";
			logger.debug(log);
		*/
		return tempString;
	} 

	
	/*********************************************************************************************************
	 * Restituisce i soggetti partecipanti effettuando una ricerca per cognome e codice fiscale. La ricerca sul codice 
	 * fiscale avviene in maniera puntuale mentre la ricerca sul cognome ricerca tutti i cognomi che contengano la stringa 
	 * indicata
	 * 
	 * @param cognome String passata per la ricerca del cognome
	 * @param codiceFiscale String per la ricerca del codice fiscale 
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getSoggettiPartecipantiRubTab(
			String cognome,
			String codiceFiscale
			) throws SQLException {

		// String selectRicerca = BASE_SELECT_SOGGETTI_PARTECIPANTI;
		String condizioni=null;
		StringTokenizer tokenDenominazione = null;
		String selectRicerca = BASE_SELECT_SOGGETTI_PARTECIPANTI;
		
		cognome = cancPercentuale(cognome);
		codiceFiscale = cancPercentuale(codiceFiscale);
		
		if (
				(cognome.equalsIgnoreCase("") || cognome == null) &&
				(codiceFiscale.equalsIgnoreCase("") || codiceFiscale == null) 
			) 
			
			return new TableBean();
			//cognome="$&"; // per avere una ricerca con risultato vuoto
		
		
		if ( cognome.trim().length() > 0 ) {
			
			tokenDenominazione = new StringTokenizer( cognome );			
			condizioni = SOGGETTI_PARTECIPANTI.T_DENOMINAZIONE + " LIKE '%" + getCleanToken( tokenDenominazione.nextToken() ) + "%'";
			while ( tokenDenominazione.hasMoreElements() ) {
				condizioni += " OR " + SOGGETTI_PARTECIPANTI.T_DENOMINAZIONE + " LIKE '%" + getCleanToken( tokenDenominazione.nextToken() ) + "%'";
			}
			selectRicerca +=
				" AND ( "
				+ condizioni
				+ ")";
			
			logger.debug("selectRicerca"+selectRicerca);
		}

		//if (codiceFiscale.equalsIgnoreCase("") || codiceFiscale == null) codiceFiscale="%%"; //per avere una ricerca con risultato vuoto
		if ( codiceFiscale.trim().length() > 0 ) {
			
			tokenDenominazione = new StringTokenizer( codiceFiscale );
			condizioni = SOGGETTI_PARTECIPANTI.T_CODICE_FISCALE + " = '" + getCleanToken( tokenDenominazione.nextToken() ) + "'";
			while ( tokenDenominazione.hasMoreElements() ) {
				condizioni += " OR " + SOGGETTI_PARTECIPANTI.T_CODICE_FISCALE + " = '" + getCleanToken( tokenDenominazione.nextToken() ) + "'";
			}
			selectRicerca +=
				" AND ( "
				+ condizioni
				+ ")";
			logger.debug("selectRicerca"+selectRicerca);
		}
		
		//PP aggiunta order by
		selectRicerca += BASE_ORDERBY;
		
		//logger.debug("Visualizzazione Elenco Partecipanti [" + selectRicerca + "]");
		//logger.debug("activeConnection" + activeConnection + "]");
		//logger.debug("connection---20070920" + connection + "]");
		
		PreparedStatement pstmt = activeConnection.prepareStatement(selectRicerca);
		TableBean result = new TableBean ( pstmt.executeQuery());
		
		close(null, pstmt);
		
		return result;
		
	}
	
	/******************************************************************************************************
	 * Restituisce i dettagli del partecipante identificato attraverso il suo ID. 
	 * 
	 * @param id_partecipante int per l'id del partecipante
	 * @param startRow 
	 * @param count
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getDettaglioPartecipante(
			int id_partecipante,
			int startRow,
			int count
			) throws SQLException {

		String selectRicerca = BASE_SELECT_DETTAGLIO_SOGGETTI_PARTECIPANTI;
		String condizioniId=null;
		StringTokenizer tokenId = null;
		
		if ( id_partecipante != 0 ) {
			
			tokenId = new StringTokenizer( String.valueOf(id_partecipante) );
			condizioniId = " WHERE "+SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE + " = " + Integer.parseInt(getCleanToken( tokenId.nextToken() ));
			while ( tokenId.hasMoreElements() ) {
				condizioniId += " OR " + SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE + " = " + Integer.parseInt(getCleanToken( tokenId.nextToken() ));
			}
			selectRicerca +=condizioniId+" AND "+SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG+ " is NULL";
			
		}
		logger.debug("Visualizzazione Dettaglio Partecipante [" + selectRicerca + "]");
		
		PreparedStatement pstmt = activeConnection.prepareStatement(selectRicerca);

//		int posCounter = 1;
		
		//pstmt = fillPstmt(pstmt, posCounter, listaSAAbilitato);
		TableBean result = new TableBean ( pstmt.executeQuery(), startRow, count );
		
		close(null, pstmt);
		
		logger.debug( "Risultato per la query dettaglio partecipante[" + selectRicerca + "] Tuple [" + result.getTableSize() + "]");
		return result;
		
	}

//----------------------
	
	/*******************************************************************************************************
	 * Restituisce lo storico associato ad un detrminato Partecipante
	 * 
	 * @param id_partecipante int per l'id del partecipante
	 * @param startRow int per la riga di partenza
	 * @param count int 
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getStoricoPartecipante(
			int id_partecipante,
			int startRow,
			int count
			) throws SQLException {

		String selectStorico = BASE_SELECT_DETTAGLIO_SOGGETTI_PARTECIPANTI;
		String condizioniId=null;
		StringTokenizer tokenId = null;
		
		if ( id_partecipante != 0 ) {
			
			tokenId = new StringTokenizer( String.valueOf(id_partecipante) );
			condizioniId = " WHERE "+SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE + " = " + Integer.parseInt(getCleanToken( tokenId.nextToken() ));
			while ( tokenId.hasMoreElements() ) {
				condizioniId += " OR " + SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE + " = " + Integer.parseInt(getCleanToken( tokenId.nextToken() ));
			}
			selectStorico +=condizioniId+" AND DATA_FINE_SOGG is not null " ;
			selectStorico += "ORDER BY "+SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG;
			
		}
		logger.debug("Visualizzazione Storico Partecipante [" + selectStorico + "]");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TableBean result = null;
		try{
		pstmt = activeConnection.prepareStatement(selectStorico);
		rs = pstmt.executeQuery();
		
		//pstmt = fillPstmt(pstmt, posCounter, listaSAAbilitato);
		result = new TableBean ( rs, startRow, count );
		
		logger.debug( "Risultato per la query dettaglio partecipante[" + selectStorico + "] Tuple [" + result.getTableSize() + "]");
		}
		finally{
			close(rs,pstmt);
		}
		return result;
		//return null;
		
	}
	
	
//----------------------
	
	
	
	/*******************************************************************************************************
	 * Restituisce il token formattato in maniera appropriata per rendere ammissibile il carattere dell'apostrofo 
	 * 
	 * param currentToken String
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
	
	/*******************************************************************************************************
	 * modifica un partecipante della rubrica o ne inserisce uno nuovo a seconda della valorizzazione del parametro
	 * updateFlag
	 * 
	 * @param partecipanteDaInserire Rubrica
	 * @param updateFlag boolean, se true effettua la modifica, se false effettua un inserimento
	 * @return Object[]
	 * @throws SQLException
	 */
	public Object[] insertPartecipante ( Rubrica partecipanteDaInserire, boolean updateFlag ) throws SQLException {

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
				insertRubrica = activeConnection.prepareStatement(createInsertQuery(INSERT_PARTECIPANTE_RUBRICA,SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE));	
			}else{
				insertRubrica = activeConnection.prepareStatement(createCopyRecord(UPDATE_PARTECIPANTE_RUBRICA,SOGGETTI_PARTECIPANTI.TABLE_NAME));
				idSoggettoPartecipante = partecipanteDaInserire.getId_soggetto_partecipante();
				insertRubrica.setObject(i++, idSoggettoPartecipante);
				logger.debug("Modifica idSoggettoPartecipante 1[" + idSoggettoPartecipante + "]");
			}
			
			insertRubrica.setObject(i++, dataInizioSoggetto);
			logger.debug("Modifica dataInizioSoggetto 2[" + dataInizioSoggetto + "]");		
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getCf_rappresentante());
			logger.debug("Modifica cf_rappresentante 3[" + partecipanteDaInserire.getCf_rappresentante() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getCodice_fiscale());
			logger.debug("Modifica codice fiscale 4[" + partecipanteDaInserire.getCodice_fiscale() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getDenominazione());
			logger.debug("Modifica denominazione 5[" + partecipanteDaInserire.getDenominazione() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getCamera_commercio());
			logger.debug("Modifica camera commercio 6[" + partecipanteDaInserire.getCamera_commercio() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getPartitaIva());
			logger.debug("Modifica partita iva 7[" + partecipanteDaInserire.getPartitaIva() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getIndirizzo());
			logger.debug("Modifica indirizzo 8[" + partecipanteDaInserire.getIndirizzo() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getCivico());
			logger.debug("Modifica civico 9[" + partecipanteDaInserire.getCivico() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getCap());
			logger.debug("Modifica cap 10[" + partecipanteDaInserire.getCap() + "]");
					
			insertRubrica.setObject(i++, partecipanteDaInserire.getCitta());
			logger.debug("Modifica citta 11[" + partecipanteDaInserire.getCitta() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getProvincia());
			logger.debug("Modifica provincia 12[" + partecipanteDaInserire.getProvincia() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getNome());
			logger.debug("Modifica nome 13[" + partecipanteDaInserire.getNome() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getCognome());
			logger.debug("Modifica provincia 14[" + partecipanteDaInserire.getCognome() + "]");
			
			logger.debug("Check  flafEsteri[" + partecipanteDaInserire.getFlagEsteri() + "]");
			//X-XX: Inserimento nuovo campo id_stato
			if("".equals(partecipanteDaInserire.getId_stato()) || partecipanteDaInserire.getId_stato() == null
					|| "N".equalsIgnoreCase(partecipanteDaInserire.getFlagEsteri()) ) {
				insertRubrica.setNull(i++,java.sql.Types.VARCHAR);
			}else{
				insertRubrica.setString(i++, partecipanteDaInserire.getId_stato());
			}
			logger.debug("Modifica id_stato 15[" + partecipanteDaInserire.getId_stato() + "]");
			
			if(insertRubrica.execute()){
				rs = insertRubrica.getResultSet();
				rs.next();
				retVal[0] = rs.getLong(SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE);
			}
			if(!updateFlag){
				logger.debug ( "Eseguita query [" + INSERT_PARTECIPANTE_RUBRICA + "] per idPartecipante [" + partecipanteDaInserire.getId_soggetto_partecipante() + "]" );
				
			}else{
				logger.debug ( "Eseguita query [" + UPDATE_PARTECIPANTE_RUBRICA + "] per idPartecipante [" + partecipanteDaInserire.getId_soggetto_partecipante() + "]" );
				
			}
			return retVal;
		} finally {
			close(rs,insertRubrica);
//			try {
//				close(rs,insertRubrica);
//				insertRubrica.close();
//			} catch ( Exception e ) {}
//			rs = null;
//			insertRubrica = null;			
		}
	}

	/*******************************************************************************************************
	 * ONLY MASSLOADER: !attenzione duplicazione codice!
	 * modifica un partecipante della rubrica, supporta modifiche (inserimenti veloci)
	 * aggiunge un secondo alla vecchia data inizio se resulta che ci si trovi nello stesso
	 * secondo.
	 * 
	 * @param partecipanteDaInserire
	 * @param vecchiaDataInizio
	 * @return Object[]
	 * @throws SQLException
	 */
	public Object[] inserisciPartecipanteAvendoId ( Rubrica partecipanteDaInserire, Timestamp vecchiaDataInizio) throws SQLException {

		logger.debug("Inserisci partecipante [" + partecipanteDaInserire + "], Vecchia dataInizio ["+vecchiaDataInizio+"]");
		PreparedStatement insertRubrica = null;
		ResultSet rs = null;
		//introduzione delle gestione degli inserimenti sotto il secondo
		Timestamp dataInizioSoggetto = super.getTimestampPlusOneSecondIfInTheSameSecond(getNow(), vecchiaDataInizio);
		Long idSoggettoPartecipante;
		Object[] retVal = new Object[2];
		retVal[1]=dataInizioSoggetto;
		int i = 1;
		try {
			
			insertRubrica = activeConnection.prepareStatement(createCopyRecord(UPDATE_PARTECIPANTE_RUBRICA,SOGGETTI_PARTECIPANTI.TABLE_NAME));
			idSoggettoPartecipante = partecipanteDaInserire.getId_soggetto_partecipante();
			insertRubrica.setObject(i++, idSoggettoPartecipante);
			logger.debug("Inserisci idSoggettoPartecipante 1[" + idSoggettoPartecipante + "]");

			
			insertRubrica.setObject(i++, dataInizioSoggetto);
			logger.debug("Inserisci dataInizioSoggetto 2[" + dataInizioSoggetto + "]");		
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getCf_rappresentante());
			logger.debug("Inserisci cf_rappresentante 3[" + partecipanteDaInserire.getCf_rappresentante() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getCodice_fiscale());
			logger.debug("Inserisci codice fiscale 4[" + partecipanteDaInserire.getCodice_fiscale() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getDenominazione());
			logger.debug("Inserisci denominazione 5[" + partecipanteDaInserire.getDenominazione() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getCamera_commercio());
			logger.debug("Inserisci camera commercio 6[" + partecipanteDaInserire.getCamera_commercio() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getPartitaIva());
			logger.debug("Inserisci partita iva 7[" + partecipanteDaInserire.getPartitaIva() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getIndirizzo());
			logger.debug("Inserisci indirizzo 8[" + partecipanteDaInserire.getIndirizzo() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getCivico());
			logger.debug("Inserisci civico 9[" + partecipanteDaInserire.getCivico() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getCap());
			logger.debug("Inserisci cap 10[" + partecipanteDaInserire.getCap() + "]");
					
			insertRubrica.setObject(i++, partecipanteDaInserire.getCitta());
			logger.debug("Inserisci citta 11[" + partecipanteDaInserire.getCitta() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getProvincia());
			logger.debug("Inserisci provincia 12[" + partecipanteDaInserire.getProvincia() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getNome());
			logger.debug("Inserisci nome 13[" + partecipanteDaInserire.getNome() + "]");
			
			insertRubrica.setObject(i++, partecipanteDaInserire.getCognome());
			logger.debug("Modifica provincia 14[" + partecipanteDaInserire.getCognome() + "]");
			
			logger.debug("Check  flafEsteri[" + partecipanteDaInserire.getFlagEsteri() + "]");
			//X-XX: Inserimento nuovo campo id_stato
			if("".equals(partecipanteDaInserire.getId_stato()) || partecipanteDaInserire.getId_stato() == null
					|| "N".equalsIgnoreCase(partecipanteDaInserire.getFlagEsteri()) ) {
				insertRubrica.setNull(i++,java.sql.Types.VARCHAR);
			}else{
				insertRubrica.setString(i++, partecipanteDaInserire.getId_stato());
			}
			logger.debug("Inserisci id_stato 15[" + partecipanteDaInserire.getId_stato() + "]");
			
			insertRubrica.execute();
//			if(insertRubrica.execute()){
//				rs = insertRubrica.getResultSet();
//				rs.next();
//				retVal[0] = rs.getLong(SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE);
//			}
			retVal[0] = idSoggettoPartecipante;
			logger.debug ( "Eseguita query [" + UPDATE_PARTECIPANTE_RUBRICA + "] per idPartecipante [" + partecipanteDaInserire.getId_soggetto_partecipante() + "]" );

			return retVal;
		} finally {
			close(rs,insertRubrica);		
		}
	}
	
	
	/******************************************************************************************************
	 * Il metodo si occupa della cancellazione di un partecipante 
	 * 
	 * @param partecipanteDaCancellare Rubrica
	 * @return int - elementi cancellati
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int cancellaPartecipante ( Rubrica partecipanteDaCancellare ) throws SQLException, ClassNotFoundException {

		logger.debug("Cancella partecipante [" + cancellazionePartecipante + "]");
		PreparedStatement updateRubricaFunction = null;
		
	
		try {
			//data odierna
			//Timestamp dataFineSoggetto = new Timestamp((System.currentTimeMillis()/1000)*1000);
			Timestamp dataFineSoggetto = getNow();
			updateRubricaFunction = activeConnection.prepareStatement(cancellazionePartecipante);
			
			updateRubricaFunction.setObject(1, dataFineSoggetto);
			logger.debug("Cancella id_soggetto_partecipante 1[" + dataFineSoggetto + "]");

			updateRubricaFunction.setLong(2, partecipanteDaCancellare.getId_soggetto_partecipante());
			logger.debug("Cancella id_soggetto_partecipante 2[" + partecipanteDaCancellare.getId_soggetto_partecipante() + "]");
			
			updateRubricaFunction.setObject(3, PageHelper.getFormattedDBDateTime(partecipanteDaCancellare.getData_inizio_sogg()));
			logger.debug("Cancella data inizio soggetto 3[" + partecipanteDaCancellare.getData_inizio_sogg() + "]");
			
			int result = updateRubricaFunction.executeUpdate();
			
			logger.debug ( "Eseguita query[" + cancellazionePartecipante + "] per idPartecipante [" + partecipanteDaCancellare.getId_soggetto_partecipante() + "]" );
			logger.debug("Completato aggiornamento del partecipante [" + partecipanteDaCancellare.getId_soggetto_partecipante() + "] CIG [" + "] Esito [" + result + "]");
			
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
		+ SOGGETTI_PARTECIPANTI.CODICE_FISCALE
		+ " FROM " + SOGGETTI_PARTECIPANTI.TABLE_NAME
		+ " WHERE " + SOGGETTI_PARTECIPANTI.CODICE_FISCALE + " = ?" 
		+ " $1 "
		+ " AND " + SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG + " IS NULL";	
		
	
	
	/******************************************************************************************************
	 * metodo che restituisce true se il cod fisc non e' presente nel db, false in caso contrario
	 *
	 * @param codFisc String
	 * @param codice_paese String
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean checkCF(String codFisc,String codice_paese) throws SQLException {
		boolean isCodFisc=true;
		/** adds 29092008 */
		boolean isCodicePaese = false;
		String selectRicerca = SELECT_COD_FISC_SOGGETTI_PARTECIPANTI;
		//siccome per l'italia il valore del campo � null devo rimuovere la condizione 
		//della chiave di ricerca e invece imporre == null 
		String vincolo_paese = "";
		if(codice_paese == null || Costanti.CODICE_STATO_ITALIANO.equalsIgnoreCase(codice_paese) || "".equals(codice_paese)){
			vincolo_paese = " AND " + SOGGETTI_PARTECIPANTI.ID_STATO +" IS NULL ";
			selectRicerca = selectRicerca.replace("$1", vincolo_paese);
		}else{
			isCodicePaese = true;
			vincolo_paese = " AND " + SOGGETTI_PARTECIPANTI.ID_STATO + " = ? ";
			logger.debug("Soggetto partecipante IT");
			selectRicerca = selectRicerca.replace("$1", vincolo_paese);
		}
		/** end */
//		logger.debug("[check_cf] - "+selectRicerca);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
		pstmt = activeConnection.prepareStatement(selectRicerca);
		pstmt.setString(1, codFisc.toUpperCase());
		if(isCodicePaese){
			pstmt.setString(2, codice_paese.toUpperCase());
		}
		rs = pstmt.executeQuery();
		isCodFisc = !rs.next();
		//logger.debug("and the winner for [(String "+ codFisc+",String "+ codice_paese+")] is: " + isCodFisc);	
		}
		finally{
		close(rs,pstmt);
		}
		return isCodFisc;
	}	

	public boolean checkCFNoCountry(String codFisc) throws SQLException {
		boolean isCodFisc=true;
		/** adds 29092008 */
		String selectRicerca = SELECT_COD_FISC_SOGGETTI_PARTECIPANTI;
		selectRicerca = selectRicerca.replace("$1", "");
		/** end */
//		logger.debug("[check_cf] - "+selectRicerca);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
		pstmt = activeConnection.prepareStatement(selectRicerca);
		pstmt.setString(1, codFisc.toUpperCase());

		rs = pstmt.executeQuery();
		isCodFisc = !rs.next();
		//logger.debug("and the winner for [(String "+ codFisc+",String "+ codice_paese+")] is: " + isCodFisc);	
		}
		finally{
		close(rs,pstmt);
		}
		return isCodFisc;
	}	
	
	private final String SELECT_BY_COD_FISC =
		"SELECT " + SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE 
		+ "," + SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG
		+ " FROM " + SOGGETTI_PARTECIPANTI.TABLE_NAME
		+ " WHERE " + SOGGETTI_PARTECIPANTI.CODICE_FISCALE + " = ?" 
		+ " $1 "
		+ " AND " + SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG + " IS NULL";	

	//metodo che restituisce id e data del soggetto in base al codice fiscale
	
	/********************************************************************************************************
	 * metodo che restituisce id e data del soggetto in base al codice fiscale
	 * 
	 * @param codFisc String per il codice fiscale
	 * @return Object[]
	 * @throws SQLException
	 */
	@Deprecated
	public Object[] getSoggettoByCF(String codFisc,String codice_paese) throws SQLException {
		Object[] retVal = new Object[2];
		/** adds 29092008 */
		boolean isCodicePaese = false;
		String selectRicerca = SELECT_BY_COD_FISC;
		//siccome per l'italia il valore del campo � null devo rimuovere la condizione 
		//della chiave di ricerca e invece imporre == null 
		String vincolo_paese = "";
		if( codice_paese == null || Costanti.CODICE_STATO_ITALIANO.equalsIgnoreCase(codice_paese) || "".equals(codice_paese)){
			vincolo_paese = " AND " + SOGGETTI_PARTECIPANTI.ID_STATO +" IS NULL ";
			selectRicerca = selectRicerca.replace("$1", vincolo_paese);
		}else{
			isCodicePaese = true;
			vincolo_paese = " AND " + SOGGETTI_PARTECIPANTI.ID_STATO + " = ? ";
			logger.debug("Soggetto partecipante IT");
			selectRicerca = selectRicerca.replace("$1", vincolo_paese);
		}
		/** end */
//		logger.debug("[check_cf] - "+selectRicerca);
		PreparedStatement pstmt = activeConnection.prepareStatement(selectRicerca);
		pstmt.setString(1, codFisc.toUpperCase());
		if(isCodicePaese){
			pstmt.setString(2, codice_paese.toUpperCase());
		}
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			retVal[0] = rs.getLong(SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE );
			retVal[1] = rs.getTimestamp(SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG);
			//logger.debug("id_soggetto: "+retVal[0]+" , data inizio: "+retVal[1]);
		}
			
		close(rs,pstmt);
		return retVal;
	}
	private final String SELECT_ALL_BY_COD_FISC =
		"SELECT " + SOGGETTI_PARTECIPANTI.TABLE_NAME + ".*" 
		+ " FROM " + SOGGETTI_PARTECIPANTI.TABLE_NAME
		+ " WHERE " + SOGGETTI_PARTECIPANTI.CODICE_FISCALE + " = ?" 
		+ " $1 "
		+ " AND " + SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG + " IS NULL"
		// XXX: patch nel caso in cui ci siano 2 record con data fine a null deve prendere il piu recente
		+ " ORDER BY " + SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG + " DESC";	
	
	public SoggettoPartecipanteBean getAllSoggettoPartecipanteByCF(String codFisc,String codice_paese) throws SQLException {
		SoggettoPartecipanteBean spr = new SoggettoPartecipanteBean();
		/** adds 29092008 */
		boolean isCodicePaese = false;
		String selectRicerca = SELECT_ALL_BY_COD_FISC;
		//siccome per l'italia il valore del campo e' null devo rimuovere la condizione 
		//della chiave di ricerca e invece imporre == null 
		String vincolo_paese = "";
		if( codice_paese == null 
		         || Costanti.CODICE_STATO_ITALIANO.equalsIgnoreCase(codice_paese) 
		         || "".equals(codice_paese)
		         || "null".equals(codice_paese)){
			vincolo_paese = " AND " + SOGGETTI_PARTECIPANTI.ID_STATO +" IS NULL ";
			selectRicerca = selectRicerca.replace("$1", vincolo_paese);
		}else{
			isCodicePaese = true;
			vincolo_paese = " AND " + SOGGETTI_PARTECIPANTI.ID_STATO + " = ? ";
			logger.debug("Soggetto partecipante IT");
			selectRicerca = selectRicerca.replace("$1", vincolo_paese);
		}
		/** end */
//		logger.debug("[check_cf] - "+selectRicerca);
		PreparedStatement pstmt = activeConnection.prepareStatement(selectRicerca);
		pstmt.setString(1, codFisc.toUpperCase());
		if(isCodicePaese){
			pstmt.setString(2, codice_paese.toUpperCase());
		}
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			spr.setIdSoggettoPartecipante(rs.getLong(SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE ) );
			spr.setDataInizioSogg( rs.getTimestamp(SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG) );
			spr.setCameraCommercio(rs.getString(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO));
			spr.setCap(rs.getString(SOGGETTI_PARTECIPANTI.CAP));
			spr.setCfRappresentante(rs.getString(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE));
			spr.setCitta(rs.getString(SOGGETTI_PARTECIPANTI.CITTA));
			spr.setCivico(rs.getString(SOGGETTI_PARTECIPANTI.CIVICO));
			spr.setCodiceFiscale(rs.getString(SOGGETTI_PARTECIPANTI.CODICE_FISCALE));
			spr.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
			spr.setDenominazione(rs.getString(SOGGETTI_PARTECIPANTI.DENOMINAZIONE));
			//spr.setFlagEsteri(rs.getString(SOGGETTI_PARTECIPANTI.));
			spr.setId_stato(rs.getString(SOGGETTI_PARTECIPANTI.ID_STATO));
			spr.setIndirizzo(rs.getString(SOGGETTI_PARTECIPANTI.INDIRIZZO));
			spr.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
			spr.setPartitaIva(rs.getString(SOGGETTI_PARTECIPANTI.PARTITA_IVA));
			spr.setProvincia(rs.getString(SOGGETTI_PARTECIPANTI.PROVINCIA));
			//devo aggiungere il resto dei settings
			//logger.debug("id_soggetto: "+retVal[0]+" , data inizio: "+retVal[1]);
		}
			
		close(rs,pstmt);
		return spr;
	}
	
	
	public SoggettoPartecipanteBean getAllSoggettoPartecipanteByCFSubappaltatori(String codFisc) throws SQLException {
		SoggettoPartecipanteBean spr = new SoggettoPartecipanteBean();
		/** adds 29092008 */

		String selectRicerca = SELECT_ALL_BY_COD_FISC;
		selectRicerca = selectRicerca.replace("$1", "");
		
		/** end */
//		logger.debug("[check_cf] - "+selectRicerca);
		PreparedStatement pstmt = activeConnection.prepareStatement(selectRicerca);
		pstmt.setString(1, codFisc.toUpperCase());

		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			spr.setIdSoggettoPartecipante(rs.getLong(SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE ) );
			spr.setDataInizioSogg( rs.getTimestamp(SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG) );
			spr.setCameraCommercio(rs.getString(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO));
			spr.setCap(rs.getString(SOGGETTI_PARTECIPANTI.CAP));
			spr.setCfRappresentante(rs.getString(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE));
			spr.setCitta(rs.getString(SOGGETTI_PARTECIPANTI.CITTA));
			spr.setCivico(rs.getString(SOGGETTI_PARTECIPANTI.CIVICO));
			spr.setCodiceFiscale(rs.getString(SOGGETTI_PARTECIPANTI.CODICE_FISCALE));
			spr.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
			spr.setDenominazione(rs.getString(SOGGETTI_PARTECIPANTI.DENOMINAZIONE));
			//spr.setFlagEsteri(rs.getString(SOGGETTI_PARTECIPANTI.));
			spr.setId_stato(rs.getString(SOGGETTI_PARTECIPANTI.ID_STATO));
			spr.setIndirizzo(rs.getString(SOGGETTI_PARTECIPANTI.INDIRIZZO));
			spr.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
			spr.setPartitaIva(rs.getString(SOGGETTI_PARTECIPANTI.PARTITA_IVA));
			spr.setProvincia(rs.getString(SOGGETTI_PARTECIPANTI.PROVINCIA));
			//devo aggiungere il resto dei settings
			//logger.debug("id_soggetto: "+retVal[0]+" , data inizio: "+retVal[1]);
		}
			
		close(rs,pstmt);
		return spr;
	}
	
	/**
	 * Metodo che si occupa dell'aggiornamento di una anagrafica,
	 * esegue:
	 * - cancellazione logica
	 * - aggiornamento record attuale.
	 * - il bean passato ora contiene id e nuova data inizio
	 *
	 * @param sp
	 * @param anagrafica_partecipante
	 * @param vecchiaDataInizio
	 * @return
	 * @throws SQLException
	 */
	public boolean aggiornamentoConCancellazioneLogica(SoggettoPartecipanteBean sp, Rubrica anagrafica_partecipante,Timestamp vecchiaDataInizio)throws SQLException{
		try{ 
			this.cancellaPartecipante(anagrafica_partecipante);
			Object[] chiavi = this.inserisciPartecipanteAvendoId(anagrafica_partecipante, vecchiaDataInizio);
			if(chiavi != null){
				sp.setIdSoggettoPartecipante(((Long)chiavi[0]).longValue());
				sp.setDataInizioSogg((Timestamp)chiavi[1]);
//				anagrafica_partecipante.setId_soggetto_partecipante((Long)chiavi[0]);
//				anagrafica_partecipante.setData_inizio_sogg(PageHelper.getDBDateFromTS((Timestamp)chiavi[1]));
				return true;
			}return false;
		}catch(SQLException sqle){
			logger.fatal(sqle.getMessage());
			throw sqle;
		}catch(ClassNotFoundException e){
			logger.debug("Eccezione: " + e.getMessage());
			return false;
		}
	}

}
