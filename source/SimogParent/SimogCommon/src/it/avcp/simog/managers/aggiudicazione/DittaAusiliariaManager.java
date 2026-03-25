package it.avcp.simog.managers.aggiudicazione;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.AGGIUDICATARIO;
import it.avlp.simog.db.generated.DITTE_AUSILIARIE;
import it.avlp.simog.db.generated.SOGGETTI_PARTECIPANTI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Classe che si occupa della lettura/scrittura di dati relativi ad un aggiudicatario
 *
 */
public class DittaAusiliariaManager extends AccessiDB implements IAnnullamentoMulti{

	public static String CLAZZ = "DittaAusiliariaManager";
	
	/**
	 * Costruttore che passa connessione e logger alla sua super classe
	 * 
	 * @param currentActiveConnection
	 * @param logger
	 */
	public DittaAusiliariaManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	private final String QUERY_INSERT_DITTA_AUSILIARIA =
		"INSERT INTO " + DITTE_AUSILIARIE.TABLE_NAME + " ( " +
		DITTE_AUSILIARIE.ID_AGGIUDICAZIONE + ", " +
		DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICAZIONE + ", " + 
		DITTE_AUSILIARIE.ID_AGGIUDICATARIO + ", " +
		DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICATARIO + ", " + 	
		DITTE_AUSILIARIE.ID_SOGGETTO_PARTECIPANTE + ", " + 
		DITTE_AUSILIARIE.DATA_INIZIO_SOGG + ", " + 
		DITTE_AUSILIARIE.ID_STATO_SCHEDA + ", " + 	
		DITTE_AUSILIARIE.DATA_FINE_RECORD + ", " + 
		DITTE_AUSILIARIE.DATA_INIZIO_RECORD + ", " + 
		DITTE_AUSILIARIE.FLAG_AVVALIMENTO + 
		" ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private final String ELIMINA_DITTE_AUSILIARIE_BY_AGGIUDICATARIO = "DELETE FROM " + DITTE_AUSILIARIE.TABLE_NAME + " WHERE "
	+ DITTE_AUSILIARIE.ID_AGGIUDICATARIO + " = ? AND " + DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICATARIO +" = ? ";
	
	private final String ELIMINA_DITTE_AUSILIARIE_BY_AGGIUDICAZIONE = "DELETE FROM " + DITTE_AUSILIARIE.TABLE_NAME + " WHERE "
	+ DITTE_AUSILIARIE.ID_AGGIUDICAZIONE + " = ? AND " + DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICAZIONE +" = ? ";
	
	//Metodo utilizzato dall'amministratore per cancellare il record attivo qualora venisse rifiutata
	//la richiesta di annullamento ad esso relativa
	private final String QUERY_DELETE_DITTE_AUSILIARIE = 
		"DELETE FROM "+DITTE_AUSILIARIE.TABLE_NAME+
		" WHERE " + DITTE_AUSILIARIE.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	
	private final String QUERY_SELECT_LISTA_DITTE_AUSILIARIE = "SELECT " +
	    //DITTE_AUSILIARIE.T_ID_AGGIUDICAZIONE + ", " +
	    //DITTE_AUSILIARIE.T_DATA_INIZIO_AGGIUDICAZIONE + ", " + 
    	//DITTE_AUSILIARIE.T_ID_AGGIUDICATARIO + ", " +
    	//DITTE_AUSILIARIE.T_DATA_INIZIO_AGGIUDICATARIO + ", " + 	
    	DITTE_AUSILIARIE.T_ID_SOGGETTO_PARTECIPANTE + ", " + 
     	DITTE_AUSILIARIE.T_DATA_INIZIO_SOGG + ", " + 
    	//DITTE_AUSILIARIE.T_ID_STATO_SCHEDA + ", " + 	
    	//DITTE_AUSILIARIE.T_DATA_FINE_RECORD + ", " + 
    	//DITTE_AUSILIARIE.T_DATA_INIZIO_RECORD + ", " + 
    	DITTE_AUSILIARIE.T_FLAG_AVVALIMENTO + ", " +
    	SOGGETTI_PARTECIPANTI.T_DENOMINAZIONE + ", " +
    	SOGGETTI_PARTECIPANTI.T_CODICE_FISCALE + ", " +
    	SOGGETTI_PARTECIPANTI.T_ID_STATO + 
    	
        " FROM "+
        DITTE_AUSILIARIE.TABLE_NAME +" , "+
		SOGGETTI_PARTECIPANTI.TABLE_NAME +
		" WHERE " +
		DITTE_AUSILIARIE.T_ID_AGGIUDICATARIO +" = ?"+
		" AND "+
		DITTE_AUSILIARIE.T_DATA_INIZIO_AGGIUDICATARIO +" = ?"+
		" AND "+ SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE + " = " + DITTE_AUSILIARIE.T_ID_SOGGETTO_PARTECIPANTE
		+" AND "+
		SOGGETTI_PARTECIPANTI.T_DATA_INIZIO_SOGG + " = " + DITTE_AUSILIARIE.T_DATA_INIZIO_SOGG;
	
		private final String WHERE_STATO = " AND (" + DITTE_AUSILIARIE.T_ID_STATO_SCHEDA + "=" + StatiScheda.IN_DEFINIZIONE 
		+" OR " + DITTE_AUSILIARIE.T_ID_STATO_SCHEDA + " = " + StatiScheda.CONFERMATO+")";
		
	private final String QUERY_SELECT_LISTA_DITTE_AUSILIARIE_WS = "SELECT " +
	    DITTE_AUSILIARIE.T_ID_AGGIUDICATARIO + ", " +
	    DITTE_AUSILIARIE.T_FLAG_AVVALIMENTO + ", " +
	    SOGGETTI_PARTECIPANTI.TABLE_NAME + ".*"
	    +" FROM "+
	    DITTE_AUSILIARIE.TABLE_NAME +" , "+
	    SOGGETTI_PARTECIPANTI.TABLE_NAME+
	    " WHERE " +
	    DITTE_AUSILIARIE.T_ID_AGGIUDICAZIONE +" = ?"+
		" AND "+
		DITTE_AUSILIARIE.T_DATA_INIZIO_AGGIUDICAZIONE +" = ?"+
		" AND "+ SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE + " = " + DITTE_AUSILIARIE.T_ID_SOGGETTO_PARTECIPANTE
		+" AND "+
		SOGGETTI_PARTECIPANTI.T_DATA_INIZIO_SOGG + " = " + DITTE_AUSILIARIE.T_DATA_INIZIO_SOGG
		+" AND (" + DITTE_AUSILIARIE.T_ID_STATO_SCHEDA + "=" + StatiScheda.IN_DEFINIZIONE 
		+" OR " + DITTE_AUSILIARIE.T_ID_STATO_SCHEDA + " = " + StatiScheda.CONFERMATO+")";
	
//	MAC 34266 3.04.8
	private final String QUERY_SELECT_LISTA_CF_DITTE_AUSILIARIE = "SELECT DISTINCT DITTE_AUSILIARIE.*, " +
			SOGGETTI_PARTECIPANTI.CODICE_FISCALE + ", " +
			SOGGETTI_PARTECIPANTI.ID_STATO + 
		    " FROM "+
		    DITTE_AUSILIARIE.TABLE_NAME +" INNER JOIN "+
		    SOGGETTI_PARTECIPANTI.TABLE_NAME +" ON "+
		    DITTE_AUSILIARIE.T_ID_SOGGETTO_PARTECIPANTE + " = " + SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE + 
		    " WHERE " +
		    DITTE_AUSILIARIE.T_ID_AGGIUDICAZIONE +" = ?";
		
	
	
	/**
	 * metodo per l'inserimento della ditta ausilairia in stato "in definizione",
	 * nel passaggio nel bean viene settato lo stato anche nel bean
	 * 
	 * @param ditta ausiliaria
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */	
	public void update (DittaAusiliariaBean dittaAusiliaria, boolean conferma)throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {		
			stmt = activeConnection.prepareStatement(QUERY_INSERT_DITTA_AUSILIARIA);				
			int index = 1;
			
			// DITTE_AUSILIARIE.ID_AGGIUDICAZIONE 
			stmt.setLong(index++, dittaAusiliaria.getIdAggiudicazione());               	
			// DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICAZIONE + ", " +
			stmt.setTimestamp(index++, dittaAusiliaria.getDataInizioAggiudicazione());               	
			// DITTE_AUSILIARIE.ID_AGGIUDICATARIO
			stmt.setLong(index++, dittaAusiliaria.getIdAggiudicatario());               
			// DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICATARIO
			stmt.setTimestamp(index++, dittaAusiliaria.getDataInizioAggiudicatario());               
			//	DITTE_AUSILIARIE.ID_SOGGETTO_PARTECIPANTE
			stmt.setLong(index++, dittaAusiliaria.getSoggettoPartecipante().getIdSoggettoPartecipante());               
			// DITTE_AUSILIARIE.DATA_INIZIO_SOGG			
			stmt.setTimestamp(index++, dittaAusiliaria.getSoggettoPartecipante().getDataInizioSogg());               
			// DITTE_AUSILIARIE.ID_STATO
			// DITTE_AUSILIARIE.DATA_FINE_RECORD
			if (conferma){
				stmt.setLong(index++, StatiScheda.CONFERMATO);
				stmt.setTimestamp(index++, getNow());
				dittaAusiliaria.setIdStato(StatiScheda.CONFERMATO);
			}else{
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
				stmt.setNull(index++, Types.TIMESTAMP);
				dittaAusiliaria.setIdStato(StatiScheda.IN_DEFINIZIONE);
			}   
			//DITTE_AUSILIARIE.DATA_INIZIO_RECORD
			//aggiudicatario.setDataInizio(getNow());
			stmt.setTimestamp(index++, getNow());             		
			// DITTE_AUSILIARIE.FLAG_AVVALIMENTO
			stmt.setString(index++, dittaAusiliaria.getFlagAvvalimento());                       
			
			stmt.executeUpdate();
		} 
		finally {
			close(rs, stmt); 
		}
	}
	
	/**
	 * metodo per l'eliminazione di un record
	 * 
	 * @param idAggiudicazione String
	 * @param dataInizioAgg Timestamp
	 * @return int - affected row count
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public int deleteRecord(String idAggiudicazione, Timestamp dataInizioAgg) throws SQLException{
		
		int numRow = -1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			int index = 1;
			stmt = activeConnection.prepareStatement(QUERY_DELETE_DITTE_AUSILIARIE);
			
			stmt.setLong(index++, Long.parseLong(idAggiudicazione));
			
			stmt.setTimestamp(index++,dataInizioAgg);
			
			numRow = stmt.executeUpdate();
			 
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}
	
	/**
	 * metodo per la cancellazione di tutte le ditte ausiliarie, in stato di definizione, 
	 * relative ad un'aggiudicatario 
	 *  
	 * @param idAggiudicatario
	 * @param dataInizioAggiudicatario
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public void deleteDitteAusiliarieByAggiudicatario(long idAggiudicatario, Timestamp dataInizioAggiudicatario) throws SQLException{
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(ELIMINA_DITTE_AUSILIARIE_BY_AGGIUDICATARIO);
			int index = 1;
			stmt.setLong(index++, idAggiudicatario);
			stmt.setTimestamp(index++, dataInizioAggiudicatario);
			stmt.execute();
		}
		finally{
			close(null,stmt);
		}
	}
	
	/**
	 * metodo per la cancellazione di tutte le ditte ausiliarie, in stato di definizione, 
	 * relative ad un'aggiudicazione 
	 *  
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public void deleteDitteAusiliarieByAggiudicazione(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(ELIMINA_DITTE_AUSILIARIE_BY_AGGIUDICAZIONE);
			int index = 1;
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataInizioAggiudicazione);
			stmt.execute();
		}
		finally{
			close(null,stmt);
		}
	}
	
	/**
	 * metodo che serve a recuperare tutte le ditte ausiliarie relative 
	 * ad un aggiudicatario
	 * 
	 * @param idAggiudicatario
	 * @param dataInizioAggiudicatario
	 * @param ignoraStato TODO
	 * @return List&lt;DittaAusiliariaBean&gt; - una lista di ditte ausiliarie
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public List<DittaAusiliariaBean> loadMany(long idAggiudicatario, Timestamp dataInizioAggiudicatario, boolean ignoraStato) throws SQLException{
		
		String mtd = "loadMany";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<DittaAusiliariaBean> ris = new ArrayList<DittaAusiliariaBean>();
		try{
			DittaAusiliariaBean nuovaDitta = null;
			
			String qry = QUERY_SELECT_LISTA_DITTE_AUSILIARIE;
			if(!ignoraStato)
				qry += WHERE_STATO;
			
			stmt = activeConnection.prepareStatement(qry);
			logger.debug(logPrefix+" query ["+qry+"]");
			int index = 1;
			stmt.setLong(index++, idAggiudicatario);
			stmt.setTimestamp(index++, dataInizioAggiudicatario);
			rs = stmt.executeQuery();
			while(rs.next()){
				nuovaDitta = new DittaAusiliariaBean();
				fillBean(rs,nuovaDitta);
				ris.add(nuovaDitta);
			}
		}
		finally{
			close(rs,stmt);
		}
		ris.trimToSize();
		return ris;
	}
	

	/**
	 * metodo che serve a recuperare tutte le ditte ausiliarie relative 
	 * ad una aggiudicazione
	 * 
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @return List&lt;DittaAusiliariaBean&gt; - una lista di ditte ausiliarie
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public List<DittaAusiliariaBean> loadManyByAggiudicazione(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
		
		String mtd = "loadManyByAggiudicazione";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<DittaAusiliariaBean> ris = new ArrayList<DittaAusiliariaBean>();
		try{
			DittaAusiliariaBean nuovaDitta = null;
			stmt = activeConnection.prepareStatement(QUERY_SELECT_LISTA_DITTE_AUSILIARIE_WS);
			logger.debug(logPrefix+" query ["+QUERY_SELECT_LISTA_DITTE_AUSILIARIE_WS+"]");
			int index = 1;
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataInizioAggiudicazione);
			rs = stmt.executeQuery();
			while(rs.next()){
				nuovaDitta = new DittaAusiliariaBean();
				fillBeanWS(rs,nuovaDitta);
				ris.add(nuovaDitta);
			}
		}
		finally{
			close(rs,stmt);
		}
		ris.trimToSize();
		return ris;
	}
	
	//MAC 34266 3.04.8
	/**
	 * metodo che serve a recuperare tutti i codici fiscali delle ditte ausiliarie relative 
	 * ad una aggiudicazione
	 * 
	 * @param idAggiudicazione
	 * 
	 * @return List&lt;String&gt; - una lista di codici fiscali di ditte ausiliarie
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public List<DittaAusiliariaBean> loadManyCFByAggiudicazione(long idAggiudicazione) throws SQLException{
		
		String mtd = "loadManyCFByAggiudicazione";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<DittaAusiliariaBean> ris = new ArrayList<DittaAusiliariaBean>();
		try{
			DittaAusiliariaBean nuovaDitta = null;
			stmt = activeConnection.prepareStatement(QUERY_SELECT_LISTA_CF_DITTE_AUSILIARIE);
			logger.debug(logPrefix+" query ["+QUERY_SELECT_LISTA_CF_DITTE_AUSILIARIE+"]");
			int index = 1;
			stmt.setLong(index++, idAggiudicazione);
			rs = stmt.executeQuery();
			while(rs.next()){
				nuovaDitta = new DittaAusiliariaBean();
				fillBeanNoDenominazione(rs,nuovaDitta);
				ris.add(nuovaDitta);
			}
		}
		finally{
			close(rs,stmt);
		}
		ris.trimToSize();
		return ris;
	}
	
	/**
	 * @param rs
	 * @param DittaAusiliariaBean
	 * @throws SQLException
	 */
	private void fillBeanWS(ResultSet rs, DittaAusiliariaBean dittaAusiliariaBean)throws SQLException {
		SoggettoPartecipanteBean sogg = new SoggettoPartecipanteBean();
		sogg.setCodiceFiscale(rs.getString(SOGGETTI_PARTECIPANTI.CODICE_FISCALE));
		sogg.setDenominazione(rs.getString(SOGGETTI_PARTECIPANTI.DENOMINAZIONE));
		sogg.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
		sogg.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
		sogg.setIdSoggettoPartecipante(rs.getLong(AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE));
		sogg.setDataInizioSogg(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_SOGG));
		sogg.setCameraCommercio(rs.getString(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO));
		sogg.setCfRappresentante(rs.getString(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE));
		sogg.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
		sogg.setCap(rs.getString(SOGGETTI_PARTECIPANTI.CAP));
		sogg.setCitta(rs.getString(SOGGETTI_PARTECIPANTI.CITTA));
		sogg.setCivico(rs.getString(SOGGETTI_PARTECIPANTI.CIVICO));
		sogg.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
		sogg.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
		sogg.setPartitaIva(rs.getString(SOGGETTI_PARTECIPANTI.PARTITA_IVA));
		sogg.setProvincia(rs.getString(SOGGETTI_PARTECIPANTI.PROVINCIA));
		sogg.setId_stato(rs.getString(SOGGETTI_PARTECIPANTI.ID_STATO)); 
		sogg.setIdSoggettoPartecipante(rs.getLong(AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE));
		sogg.setDataInizioSogg(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_SOGG));
		dittaAusiliariaBean.setSoggettoPartecipante(sogg);	
		dittaAusiliariaBean.setIdAggiudicatario(rs.getLong(DITTE_AUSILIARIE.ID_AGGIUDICATARIO));
		dittaAusiliariaBean.setFlagAvvalimento(rs.getString(DITTE_AUSILIARIE.FLAG_AVVALIMENTO));
	}
	
	/**
	 * @param rs
	 * @param DittaAusiliariaBean
	 * @throws SQLException
	 */
	private void fillBean(ResultSet rs, DittaAusiliariaBean dittaAusiliariaBean)throws SQLException {
		//dittaAusiliariaBean.setIdDittaAusiliaria(rs.getLong(DITTE_AUSILIARIE.ID_RECORD));
		//dittaAusiliariaBean.setIdAggiudicazione(rs.getLong(DITTE_AUSILIARIE.ID_AGGIUDICAZIONE));
		//dittaAusiliariaBean.setDataInizioAggiudicazione(rs.getTimestamp(DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICAZIONE));
		//dittaAusiliariaBean.setIdAggiudicatario(rs.getLong(DITTE_AUSILIARIE.ID_AGGIUDICATARIO));
		//dittaAusiliariaBean.setDataInizioAggiudicatario(rs.getTimestamp(DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICATARIO));
		SoggettoPartecipanteBean sogg = new SoggettoPartecipanteBean();
		sogg.setIdSoggettoPartecipante(rs.getLong(DITTE_AUSILIARIE.ID_SOGGETTO_PARTECIPANTE));
		sogg.setDataInizioSogg(rs.getTimestamp(DITTE_AUSILIARIE.DATA_INIZIO_SOGG));
		sogg.setCodiceFiscale(rs.getString(SOGGETTI_PARTECIPANTI.CODICE_FISCALE));
		sogg.setDenominazione(rs.getString(SOGGETTI_PARTECIPANTI.DENOMINAZIONE));
		sogg.setId_stato(rs.getString(SOGGETTI_PARTECIPANTI.ID_STATO));
		dittaAusiliariaBean.setSoggettoPartecipante(sogg);
		
		//dittaAusiliariaBean.setIdStato(rs.getLong(DITTE_AUSILIARIE.ID_STATO_SCHEDA));
		dittaAusiliariaBean.setFlagAvvalimento(rs.getString(DITTE_AUSILIARIE.FLAG_AVVALIMENTO));
	}
	
	//MAC 34266 3.04.8
	private void fillBeanNoDenominazione(ResultSet rs, DittaAusiliariaBean dittaAusiliariaBean)throws SQLException {
		//dittaAusiliariaBean.setIdDittaAusiliaria(rs.getLong(DITTE_AUSILIARIE.ID_RECORD));
		//dittaAusiliariaBean.setIdAggiudicazione(rs.getLong(DITTE_AUSILIARIE.ID_AGGIUDICAZIONE));
		//dittaAusiliariaBean.setDataInizioAggiudicazione(rs.getTimestamp(DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICAZIONE));
		//dittaAusiliariaBean.setIdAggiudicatario(rs.getLong(DITTE_AUSILIARIE.ID_AGGIUDICATARIO));
		//dittaAusiliariaBean.setDataInizioAggiudicatario(rs.getTimestamp(DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICATARIO));
		SoggettoPartecipanteBean sogg = new SoggettoPartecipanteBean();
		sogg.setIdSoggettoPartecipante(rs.getLong(DITTE_AUSILIARIE.ID_SOGGETTO_PARTECIPANTE));
		sogg.setDataInizioSogg(rs.getTimestamp(DITTE_AUSILIARIE.DATA_INIZIO_SOGG));
		sogg.setCodiceFiscale(rs.getString(SOGGETTI_PARTECIPANTI.CODICE_FISCALE));
		sogg.setId_stato(rs.getString(SOGGETTI_PARTECIPANTI.ID_STATO));
		dittaAusiliariaBean.setSoggettoPartecipante(sogg);
		dittaAusiliariaBean.setIdAggiudicatario(rs.getLong(DITTE_AUSILIARIE.ID_AGGIUDICATARIO));
		//dittaAusiliariaBean.setIdStato(rs.getLong(DITTE_AUSILIARIE.ID_STATO_SCHEDA));
		dittaAusiliariaBean.setFlagAvvalimento(rs.getString(DITTE_AUSILIARIE.FLAG_AVVALIMENTO));
	}
	//FINE MAC 34266 3.04.8
	
	/**
	 * metodo per la storicizzazione di un record relativo ad un aggiudicatario
	 * 
	 * @param idAggiudicazione String
	 * @param dataInizioRecord Timestamp
	 * @param vecchiaData Timestamp
	 * @return boolean
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public boolean copyRecord(String idAggiudicazione,Timestamp dataInizioRecord, AggiudicatarioBean agg, Timestamp vecchiaData) throws SQLException{
	//  Con questa query viene aggiornato lo stato e la data di inizio aggiudicazione del record 
		//  identificato attraverso id aggiudicazione, data inizio aggiudicazione, con stato = confermato
		//  inoltre con id_aggiudicatario che 
		
		String QUERY_UPDATE_OLD_RECORD =
			"UPDATE "+DITTE_AUSILIARIE.TABLE_NAME+ " SET "
			+ DITTE_AUSILIARIE.ID_STATO_SCHEDA+ " = ?, "
			+ DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICAZIONE+ " = ? "
			
			+" WHERE "
			+DITTE_AUSILIARIE.T_ID_AGGIUDICAZIONE+" = ?"
			+" AND "+DITTE_AUSILIARIE.T_DATA_INIZIO_AGGIUDICAZIONE+" = ?"
			+" AND "+DITTE_AUSILIARIE.T_ID_STATO_SCHEDA + "=" + StatiScheda.CONFERMATO
			+" AND "+DITTE_AUSILIARIE.T_ID_AGGIUDICATARIO+" = ?";
		
	//  La query esegue un inserimento nella tabella ditte ausiliarie degli 
		//  elementi della tabella ditte ausiliarie identificati tramite l'id 
		//  dell'aggiudicazione, la data di inizio dell'aggiudicazione 
		//  ed aventi lo stato a confermato di tali record viene reimpostata 
		//  Data Inizio, Data Fine e Id Stato. 
		String QUERY_COPY_RECORD = 
			"INSERT INTO " + DITTE_AUSILIARIE.TABLE_NAME + " ( " +
			DITTE_AUSILIARIE.ID_RECORD + ", " +
			DITTE_AUSILIARIE.ID_AGGIUDICAZIONE + ", " +
			DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICAZIONE + ", " + 
			DITTE_AUSILIARIE.ID_SOGGETTO_PARTECIPANTE + ", " + 
			DITTE_AUSILIARIE.DATA_INIZIO_SOGG + ", " + 
			DITTE_AUSILIARIE.ID_AGGIUDICATARIO + ", " +
			DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICATARIO + ", " + 	
			DITTE_AUSILIARIE.ID_STATO_SCHEDA + ", " + 
			DITTE_AUSILIARIE.DATA_FINE_RECORD + ", " + 
			DITTE_AUSILIARIE.DATA_INIZIO_RECORD + ", " + 
			DITTE_AUSILIARIE.FLAG_AVVALIMENTO + " ) " +
			
			" SELECT " +
			DITTE_AUSILIARIE.ID_RECORD + ", " +
			DITTE_AUSILIARIE.ID_AGGIUDICAZIONE + ", " +
			DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICAZIONE + ", " + 
			DITTE_AUSILIARIE.ID_SOGGETTO_PARTECIPANTE + ", " + 
			DITTE_AUSILIARIE.DATA_INIZIO_SOGG + ", " + 
			DITTE_AUSILIARIE.ID_AGGIUDICATARIO + ", " +
			//DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICATARIO + ", " + 	
			//DITTE_AUSILIARIE.ID_STATO_SCHEDA + ", " + 	
			//DITTE_AUSILIARIE.DATA_FINE_RECORD + ", " + 
			//DITTE_AUSILIARIE.DATA_INIZIO_RECORD + ", " + 
			" ?, "+
			" ?, "+
			" ?, "+
			" ?, "+
			DITTE_AUSILIARIE.FLAG_AVVALIMENTO + 
			
			" FROM "+DITTE_AUSILIARIE.TABLE_NAME
			+" WHERE "
			+DITTE_AUSILIARIE.ID_AGGIUDICAZIONE+" = ?"
			+" AND "+DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICAZIONE+" = ?"
			+" AND "+DITTE_AUSILIARIE.T_ID_AGGIUDICATARIO+" = ?"
		    +" AND "+DITTE_AUSILIARIE.ID_STATO_SCHEDA+" = "+StatiScheda.CONFERMATO;
			PreparedStatement stmt = null;
			PreparedStatement stmt2 = null;
			try{
				int index = 1;
				stmt = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD,DITTE_AUSILIARIE.TABLE_NAME));
				stmt.setTimestamp(index++, agg.getDataInizioAggiudicatario()); //data_inizio_aggiudicatario
				stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE); //stato scheda
				stmt.setNull(index++, Types.TIMESTAMP); // data_fine_record
				stmt.setTimestamp(index++, getNow()); //data_inizio_record
				stmt.setLong(index++, Long.parseLong(idAggiudicazione));
				stmt.setTimestamp(index++, dataInizioRecord);
				stmt.setLong(index++, agg.getIdAggiudicatario());
				int rowsCopied = stmt.executeUpdate();
				if(rowsCopied > 0){
					index = 1;
					stmt2 = activeConnection.prepareStatement(QUERY_UPDATE_OLD_RECORD);
					stmt2.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA); //stato scheda
					stmt2.setTimestamp(index++, vecchiaData); //data_inizio_aggiudicazione
					stmt2.setLong(index++, Long.parseLong(idAggiudicazione));
					stmt2.setTimestamp(index++, dataInizioRecord);
					stmt2.setLong(index++, agg.getIdAggiudicatario());
					rowsCopied = stmt2.executeUpdate();
					return (rowsCopied>0);
				}
				else {
					logger.debug("DITTA_AUSILIARIA_MANAGER.copyRecord: Nessun record da copiare");
					return true;
				}
			}
			finally{
				close(null, stmt2);
				close(null, stmt);
			}
	}
	private final String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_DITTE_AUSILIARIE = 
		"UPDATE "+DITTE_AUSILIARIE.TABLE_NAME+
		" SET " + DITTE_AUSILIARIE.ID_STATO_SCHEDA + " = ?,"+
		DITTE_AUSILIARIE.DATA_FINE_RECORD + " = " + buildGetDate()+
		" WHERE " + DITTE_AUSILIARIE.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	/**
	 * metodo per l'aggiornamento di un record
	 * 
	 * @param idAggiudicazione String
	 * @param dataInizioAgg Timestamp
	 * @param stato_scheda ->(String) predefiniti @see StatiScheda
	 * @return int - affected row count
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public int updateRecord(String idAggiudicazione, Timestamp dataInizioAgg, String stato_scheda ) throws SQLException{
		
		int numRow = -1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			int index = 1;
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_DITTE_AUSILIARIE);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_DITTE_AUSILIARIE);
			
			stmt.setInt(index++, Integer.parseInt(stato_scheda));
			
			stmt.setLong(index++, Long.parseLong(idAggiudicazione));
			
			stmt.setTimestamp(index++,dataInizioAgg);
						
			numRow = stmt.executeUpdate();
			//PPactiveConnection.commit(currentActiveConnection);
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}	
	
	/**
	 * metodo che serve a recuperare il SoggettoPartecipante  
	 * relativo ad un codice fiscale
	 * 
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @return List&lt;DittaAusiliariaBean&gt; - una lista di ditte ausiliarie
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public SoggettoPartecipanteBean loadSoggettoPartecipanteByCF (String codice_fiscale) throws SQLException{
		
		String mtd = "loadSoggettoPartecipanteByCF";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		SoggettoPartecipanteBean sogg = new SoggettoPartecipanteBean();
		try{
			String query = 
				" SELECT * " +
				" FROM " + SOGGETTI_PARTECIPANTI.TABLE_NAME +
				" WHERE " + SOGGETTI_PARTECIPANTI.CODICE_FISCALE + " = ? " +
				" AND " + SOGGETTI_PARTECIPANTI.DATA_FINE_SOGG + " IS NULL ";
			stmt = activeConnection.prepareStatement(query);
			logger.debug(logPrefix+" query ["+query+"]");
			int index = 1;
			stmt.setString(index++, codice_fiscale);
			rs = stmt.executeQuery();
			//prendo il primo che trovo
			if(rs.next()){
				sogg.setCodiceFiscale(rs.getString(SOGGETTI_PARTECIPANTI.CODICE_FISCALE));
				sogg.setDenominazione(rs.getString(SOGGETTI_PARTECIPANTI.DENOMINAZIONE));
				sogg.setIdSoggettoPartecipante(rs.getLong(SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE));
				sogg.setDataInizioSogg(rs.getTimestamp(SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG));
				sogg.setId_stato(rs.getString(SOGGETTI_PARTECIPANTI.ID_STATO)); 
			}
		}
		finally{
			close(rs,stmt);
		}
		return sogg;
	}
	
	
	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti#annulla(long, java.sql.Timestamp)
	 */
	public boolean annulla(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException {
			return _annulla(idAggiudicazione, dataInizioAggiudicazione);	
	}
	/**
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @throws SQLException
	 */
	private boolean _annulla(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_DITTE_AUSILIARIE);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataInizioAggiudicazione);
			someRowAffected = stmt.executeUpdate() > 0;
			return someRowAffected;
		}finally {
			close(null,stmt);
		}
	}
	
	private final String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_ANEWRECORD = 
		"UPDATE "+DITTE_AUSILIARIE.TABLE_NAME+
		" SET " + DITTE_AUSILIARIE.ID_STATO_SCHEDA + " = ?,"+ //+STATI_SCHEDA.CONFERMATO+
		DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICAZIONE + " = ?"+
		" WHERE " + DITTE_AUSILIARIE.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICAZIONE + " = ?";	
	
	public int updateRecordToPointToNew(String idAggiudicazione, Timestamp dataInizioAggOld,Timestamp dataInizioAggNew, String stato_scheda ) throws SQLException{
		
		int numRow = -1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			int index = 1;
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_ANEWRECORD);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_ANEWRECORD);
			
			stmt.setInt(index++, Integer.parseInt(stato_scheda));
			stmt.setTimestamp(index++,dataInizioAggNew);
			stmt.setLong(index++, Long.parseLong(idAggiudicazione));
			
			stmt.setTimestamp(index++,dataInizioAggOld);
						
			numRow = stmt.executeUpdate();
			//PPactiveConnection.commit(currentActiveConnection);
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}	
}
				