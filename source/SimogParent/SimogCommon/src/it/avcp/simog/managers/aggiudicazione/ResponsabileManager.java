package it.avcp.simog.managers.aggiudicazione;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.RESPONSABILE;
import it.avlp.simog.db.generated.RUOLI_RESPONSABILE;
import it.avlp.simog.db.generated.SOGGETTI_PARTECIPANTI;
import it.avlp.simog.db.generated.SOGGETTI_RESPONSABILI;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Classe che si occupa della gestione dei dati relativi ai responsabili
 *
 */
public class ResponsabileManager extends AccessiDB implements IAnnullamentoMulti {

	public static String CLAZZ = "ResponsabileManager";
	
	
	private static String ELIMINA_RESPONSABILI = "DELETE FROM " + RESPONSABILE.TABLE_NAME + " WHERE "
	 + RESPONSABILE.ID_AGGIUDICAZIONE + " = ? AND " + RESPONSABILE.DATA_INIZIO_AGGIUDICAZIONE + " = ? ";
//     + "AND " + RESPONSABILE.DATA_FINE + " IS NULL"; 
	 
	/**
	 * metodo per la cancellazionen dei responsabili che risultano in un stato 
	 * di definizione
	 * 
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws SQLException
	 */
	public void deleteResponsabili(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
		PreparedStatement stmt  = null;
		try{
			stmt = activeConnection.prepareStatement(ELIMINA_RESPONSABILI);
			int index = 1;
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataInizioAggiudicazione);
			stmt.execute();
		}
		finally{
			close(null, stmt);
		}
	}
	
	private static String QUERY_LISTA_RESPONSABILI = 
		"SELECT "+RESPONSABILE.TABLE_NAME+".*,"
		+ RUOLI_RESPONSABILE.T_DESCRIZIONE+" AS "+RUOLI_RESPONSABILE.TABLE_NAME+RUOLI_RESPONSABILE.DESCRIZIONE+","
		+ SOGGETTI_RESPONSABILI.COGNOME+","
		+ SOGGETTI_RESPONSABILI.NOME+","
		+ SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE+","
		+ SOGGETTI_RESPONSABILI.TELEFONO+","
		+ SOGGETTI_RESPONSABILI.EMAIL+","
		+ SOGGETTI_RESPONSABILI.FAX + ", "
		+ SOGGETTI_RESPONSABILI.CAP+ ", "
		+ SOGGETTI_RESPONSABILI.INDIRIZZO+ ", "
		+ SOGGETTI_RESPONSABILI.COMUNE_ISTAT+ ", "
		+ RESPONSABILE.T_ID_RUOLO + ", "
		+ RESPONSABILE.T_ID_RESPONSABILE + ", "
		+ RESPONSABILE.T_DATA_INIZIO_RES + ", "
		+ RESPONSABILE.CIG_PROG_ESTERNA + ", " 
		+ RESPONSABILE.DATA_AFF_PROG_ESTERNA + ", "
		+ RESPONSABILE.DATA_CONS_PROG_ESTERNA + ", "
		+ RESPONSABILE.T_ID_GRUPPO + ", "
		+ RESPONSABILE.MANDANTE 
		+" FROM " 
		+ RESPONSABILE.TABLE_NAME + "," 
		+ RUOLI_RESPONSABILE.TABLE_NAME + ","
		+ SOGGETTI_RESPONSABILI.TABLE_NAME
		+ " WHERE "
		+ RESPONSABILE.T_ID_AGGIUDICAZIONE + " = ? "
		+ " AND " + RESPONSABILE.T_DATA_INIZIO_AGGIUDICAZIONE + " = ? "
// PP inutile		+ " AND " + RESPONSABILE.T_ID_STATO + "!=" + StatiScheda.ELIMINATO
		+ " AND " + RESPONSABILE.T_ID_RUOLO + "=" + RUOLI_RESPONSABILE.T_ID_RUOLO
		+ " AND " + SOGGETTI_RESPONSABILI.T_ID_RESPONSABILE + "="+RESPONSABILE.T_ID_RESPONSABILE
	    + " AND " + SOGGETTI_RESPONSABILI.T_DATA_INIZIO_RES + " = " + RESPONSABILE.T_DATA_INIZIO_RES
	    + " AND " + RESPONSABILE.ID_SEZIONE + " = ?" ;
//		+ " AND " + RESPONSABILE.T_DATA_FINE + " is null";

	private final String WHERE_STATO = " AND ( " + RESPONSABILE.T_ID_STATO + "=" + StatiScheda.CONFERMATO
	+ " OR " + RESPONSABILE.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE+" ) "; 
	
	private static String QUERY_LISTA_RESPONSABILI_SOGG_PART = 
		"SELECT "+RESPONSABILE.TABLE_NAME+".*,"
		+ RUOLI_RESPONSABILE.T_DESCRIZIONE+" AS "+RUOLI_RESPONSABILE.TABLE_NAME+RUOLI_RESPONSABILE.DESCRIZIONE+","
		+ SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE+","
		+ SOGGETTI_PARTECIPANTI.T_DATA_INIZIO_SOGG+","
		+ SOGGETTI_PARTECIPANTI.T_COGNOME+","
		+ SOGGETTI_PARTECIPANTI.T_NOME+","
		+ SOGGETTI_PARTECIPANTI.T_CODICE_FISCALE+","
		+ SOGGETTI_PARTECIPANTI.T_CF_RAPPRESENTANTE+ ", "	
		+ SOGGETTI_PARTECIPANTI.T_CAMERA_COMMERCIO+","
		+ SOGGETTI_PARTECIPANTI.T_PARTITA_IVA+ ", "
		+ SOGGETTI_PARTECIPANTI.T_ID_STATO+" AS "+SOGGETTI_PARTECIPANTI.TABLE_NAME+SOGGETTI_PARTECIPANTI.ID_STATO+","
		+ SOGGETTI_PARTECIPANTI.T_DENOMINAZIONE + ", "
		+ SOGGETTI_PARTECIPANTI.T_INDIRIZZO+ ", "
		+ SOGGETTI_PARTECIPANTI.T_CAP+ ", "
		+ SOGGETTI_PARTECIPANTI.T_PROVINCIA+ ", "
		+ SOGGETTI_PARTECIPANTI.T_CITTA+ ", "
		+ SOGGETTI_PARTECIPANTI.T_CIVICO+ ", "
		
		+ RESPONSABILE.T_ID_RUOLO + ", "
		+ RESPONSABILE.T_ID_RESPONSABILE + ", "
		+ RESPONSABILE.T_DATA_INIZIO_RES + ", "
		+ RESPONSABILE.T_CIG_PROG_ESTERNA + ", " 
		+ RESPONSABILE.T_DATA_AFF_PROG_ESTERNA + ", "
		+ RESPONSABILE.T_DATA_CONS_PROG_ESTERNA+ ", "
		+ RESPONSABILE.T_ID_GRUPPO + ", "
		+ RESPONSABILE.MANDANTE 
		+" FROM " 
		+ RESPONSABILE.TABLE_NAME + "," 
		+ RUOLI_RESPONSABILE.TABLE_NAME + ","
		+ SOGGETTI_PARTECIPANTI.TABLE_NAME
		+ " WHERE "
		+ RESPONSABILE.T_ID_AGGIUDICAZIONE + " = ? "
		+ " AND " + RESPONSABILE.T_DATA_INIZIO_AGGIUDICAZIONE + " = ? "
// PP inutile		+ " AND " + RESPONSABILE.T_ID_STATO + "!=" + StatiScheda.ELIMINATO
		+ " AND " + RESPONSABILE.T_ID_RUOLO + "=" + RUOLI_RESPONSABILE.T_ID_RUOLO
		+ " AND " + SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE + " = " + RESPONSABILE.T_ID_PARTECIPANTE
	    + " AND " + SOGGETTI_PARTECIPANTI.T_DATA_INIZIO_SOGG + " = " + RESPONSABILE.T_DATA_INIZIO_PART
	    + " AND " + RESPONSABILE.T_ID_SEZIONE + " = ?" ;

	private final String WHERE_STATO_SOGG_PART = " AND ( " + RESPONSABILE.T_ID_STATO + "=" + StatiScheda.CONFERMATO
	+ " OR " + RESPONSABILE.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE+" ) "; 

	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public ResponsabileManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}

	/**
	 * metodo per il recupero dei responsabili (di tipo soggetto responsabile)
	 * associati ad una aggiudicazione della sezionie di cui stringa in ingresso
	 * 
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @param sezione String
	 * @param ignoraStato TODO
	 * @return List&lt;ResponsabileBean&gt;
	 * @throws SQLException
	 */
	public List<ResponsabileBean> loadMany(long idAggiudicazione, Timestamp dataInizioAggiudicazione, String sezione, boolean ignoraStato) throws SQLException{
		
		String mtd = "loadMany";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<ResponsabileBean> ris = new ArrayList<ResponsabileBean>();
		ResponsabileBean nuovoResponsabile = null;
		SoggettoResponsabileBean nuovoSoggettoResponsabile = null;
		try {
			String qry = QUERY_LISTA_RESPONSABILI;
			if(!ignoraStato)
				qry += WHERE_STATO;
			
			stmt = activeConnection.prepareStatement(qry);
			logger.debug(logPrefix +" query ["+qry+"]");
			int index = 1;
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataInizioAggiudicazione);
			stmt.setString(index++, sezione);
			rs = stmt.executeQuery();
			while(rs.next()){
				nuovoResponsabile = new ResponsabileBean();
					nuovoSoggettoResponsabile = new SoggettoResponsabileBean();				
					nuovoResponsabile.setDescrizioneRuolo(rs.getString(RUOLI_RESPONSABILE.TABLE_NAME + RUOLI_RESPONSABILE.DESCRIZIONE));
					nuovoSoggettoResponsabile.setCognome(rs.getString(SOGGETTI_RESPONSABILI.COGNOME));
					nuovoSoggettoResponsabile.setNome(rs.getString(SOGGETTI_RESPONSABILI.NOME));
					nuovoSoggettoResponsabile.setCodiceFiscaleResponsabile(rs.getString(SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE));
					nuovoSoggettoResponsabile.setTelefono(rs.getString(SOGGETTI_RESPONSABILI.TELEFONO));
					nuovoSoggettoResponsabile.setEmail(rs.getString(SOGGETTI_RESPONSABILI.EMAIL));
					nuovoSoggettoResponsabile.setFax(rs.getString(SOGGETTI_RESPONSABILI.FAX));
					nuovoSoggettoResponsabile.setIdResponsabile(rs.getInt(RESPONSABILE.ID_RESPONSABILE));
					nuovoSoggettoResponsabile.setDataInizioRes(rs.getTimestamp(RESPONSABILE.DATA_INIZIO_RES));
					
				/* adds*/
					nuovoSoggettoResponsabile.setIndirizzo(rs.getString(SOGGETTI_RESPONSABILI.INDIRIZZO));
					nuovoSoggettoResponsabile.setCap(rs.getString(SOGGETTI_RESPONSABILI.CAP));
					nuovoSoggettoResponsabile.setComuneIstat(rs.getString(SOGGETTI_RESPONSABILI.COMUNE_ISTAT));
				/*end*/
				
				nuovoResponsabile.setIdRuolo(rs.getInt(RESPONSABILE.ID_RUOLO));
				nuovoResponsabile.setSezione(rs.getString(RESPONSABILE.ID_SEZIONE));
				nuovoResponsabile.setCigProgEsterna(rs.getString(RESPONSABILE.CIG_PROG_ESTERNA));
				nuovoResponsabile.setDataAffProgEsterna(PageHelper.getViewDate(rs.getString(RESPONSABILE.DATA_AFF_PROG_ESTERNA)));
				nuovoResponsabile.setDataConsProgEsterna(PageHelper.getViewDate(rs.getString(RESPONSABILE.DATA_CONS_PROG_ESTERNA)));
				nuovoResponsabile.setSoggettoResponsabile(nuovoSoggettoResponsabile);
				
				nuovoResponsabile.setDataInizioScheda(dataInizioAggiudicazione);
				nuovoResponsabile.setIdScheda(idAggiudicazione);
				
				nuovoResponsabile.setIdGruppo(rs.getLong(RESPONSABILE.ID_GRUPPO));
				nuovoResponsabile.setMandante(rs.getBoolean(RESPONSABILE.MANDANTE));
				
				ris.add(nuovoResponsabile);
			}			
						
		} finally {
			close(rs, stmt);
		}
		return ris;
	}
	
	/**
	 * metodo per il recupero dei responsabili (di tipo soggetto partecipante) 
	 * associati ad una aggiudicazione della sezione di cui stringa in ingresso
	 * 
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @param sezione String
	 * @param ignoraStato TODO
	 * @return List&lt;ResponsabileBean&gt;
	 * @throws SQLException
	 */
	public List<ResponsabileBean> loadManySoggPart(long idAggiudicazione, Timestamp dataInizioAggiudicazione, String sezione, boolean ignoraStato) throws SQLException{
		
		String mtd = "loadManySoggPart";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<ResponsabileBean> ris = new ArrayList<ResponsabileBean>();
		ResponsabileBean nuovoResponsabile = null;
		SoggettoPartecipanteBean nuovoSoggettoPartecipante = null;
		try {
			String qry = QUERY_LISTA_RESPONSABILI_SOGG_PART;
			if(!ignoraStato)
					qry += WHERE_STATO_SOGG_PART;
			
			stmt = activeConnection.prepareStatement(qry);
			logger.debug(logPrefix +" query ["+qry+"]");
			int index = 1;
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataInizioAggiudicazione);
			stmt.setString(index++, sezione);
			rs = stmt.executeQuery();
			while(rs.next()){
				nuovoResponsabile = new ResponsabileBean();
		    		nuovoSoggettoPartecipante = new SoggettoPartecipanteBean();				
					nuovoResponsabile.setDescrizioneRuolo(rs.getString(RUOLI_RESPONSABILE.TABLE_NAME + RUOLI_RESPONSABILE.DESCRIZIONE));
					nuovoSoggettoPartecipante.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
					nuovoSoggettoPartecipante.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
					nuovoSoggettoPartecipante.setCodiceFiscale(rs.getString(SOGGETTI_PARTECIPANTI.CODICE_FISCALE));
					nuovoSoggettoPartecipante.setCfRappresentante(rs.getString(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE));
					nuovoSoggettoPartecipante.setCameraCommercio(rs.getString(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO));
					nuovoSoggettoPartecipante.setPartitaIva(rs.getString(SOGGETTI_PARTECIPANTI.PARTITA_IVA));
					nuovoSoggettoPartecipante.setId_stato(rs.getString(SOGGETTI_PARTECIPANTI.TABLE_NAME+SOGGETTI_PARTECIPANTI.ID_STATO));
					nuovoSoggettoPartecipante.setDenominazione(rs.getString(SOGGETTI_PARTECIPANTI.DENOMINAZIONE));
					nuovoSoggettoPartecipante.setIndirizzo(rs.getString(SOGGETTI_PARTECIPANTI.INDIRIZZO));
					nuovoSoggettoPartecipante.setCap(rs.getString(SOGGETTI_PARTECIPANTI.CAP));
					nuovoSoggettoPartecipante.setProvincia(rs.getString(SOGGETTI_PARTECIPANTI.PROVINCIA));
					nuovoSoggettoPartecipante.setCitta(rs.getString(SOGGETTI_PARTECIPANTI.CITTA));
					nuovoSoggettoPartecipante.setCivico(rs.getString(SOGGETTI_PARTECIPANTI.CIVICO));
					nuovoSoggettoPartecipante.setIdSoggettoPartecipante(rs.getLong(SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE));
					nuovoSoggettoPartecipante.setDataInizioSogg(rs.getTimestamp(SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG));  
					
				nuovoResponsabile.setIdRuolo(rs.getInt(RESPONSABILE.ID_RUOLO));
				nuovoResponsabile.setSezione(rs.getString(RESPONSABILE.ID_SEZIONE));
				nuovoResponsabile.setCigProgEsterna(rs.getString(RESPONSABILE.CIG_PROG_ESTERNA));
				nuovoResponsabile.setDataAffProgEsterna(PageHelper.getViewDate(rs.getString(RESPONSABILE.DATA_AFF_PROG_ESTERNA)));
				nuovoResponsabile.setDataConsProgEsterna(PageHelper.getViewDate(rs.getString(RESPONSABILE.DATA_CONS_PROG_ESTERNA)));
				nuovoResponsabile.setSoggettoPartecipante(nuovoSoggettoPartecipante);
				
				nuovoResponsabile.setDataInizioScheda(dataInizioAggiudicazione);
				nuovoResponsabile.setIdScheda(idAggiudicazione);
				
				nuovoResponsabile.setIdGruppo(rs.getLong(RESPONSABILE.ID_GRUPPO));
				nuovoResponsabile.setMandante(rs.getBoolean(RESPONSABILE.MANDANTE));
				
				ris.add(nuovoResponsabile);
			}			
						
		} finally {
			close(rs, stmt);
		}
		return ris;
	}

	/**
	 * metodo per la conferma di un responsabile
	 * 
	 * @param responsabile ResponsabileBean
	 * @throws SQLException
	 */
	public void confirm(ResponsabileBean responsabile) throws SQLException {
		update(responsabile, true);
	}
	
	/**
	 * metodo per il salvataggio di un responsabile
	 * 
	 * @param responsabile ResponsabileBean
	 * @throws SQLException
	 */
	public void save(ResponsabileBean responsabile) throws SQLException {
		update(responsabile, false);
	}	
	
	private String QUERY_UPDATE_RESPONSABILE = 
		"INSERT INTO "+RESPONSABILE.TABLE_NAME+
		" (" +
		RESPONSABILE.ID_AGGIUDICAZIONE+", "+
		RESPONSABILE.DATA_INIZIO_AGGIUDICAZIONE+", "+
		RESPONSABILE.ID_RESPONSABILE+", "+
		RESPONSABILE.DATA_INIZIO_RES+", "+
		//gm aggiunti per responsabili soggetti partecipanti
		RESPONSABILE.ID_PARTECIPANTE+", "+
		RESPONSABILE.DATA_INIZIO_PART+", "+
		
		RESPONSABILE.DATA_INIZIO+", "+
		RESPONSABILE.ID_RUOLO+", "+
		RESPONSABILE.ID_STATO+", "+
		RESPONSABILE.DATA_FINE +", "+
		RESPONSABILE.ID_SEZIONE +", "+
		RESPONSABILE.CIG_PROG_ESTERNA + ", "+
		RESPONSABILE.DATA_AFF_PROG_ESTERNA + ", "+
		RESPONSABILE.DATA_CONS_PROG_ESTERNA +", "+
		RESPONSABILE.ID_GRUPPO+", "+
		RESPONSABILE.MANDANTE+
		" ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
	
	/**
	 * @param responsabile
	 * @param conferma
	 * @throws SQLException
	 */
	private void update(ResponsabileBean responsabile, boolean conferma) throws SQLException {
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(createInsertQuery(QUERY_UPDATE_RESPONSABILE,RESPONSABILE.ID_RESPONSABILE));
			
//			System.out.println("TB : RESPONSABILI ===== INIZIO ");
//			System.out.println("TB : RESPONSABILI "+QUERY_UPDATE_RESPONSABILE);
//			System.out.println("TB : responsabile.getIdScheda() "+responsabile.getIdScheda());
//			System.out.println("TB : responsabile.getDataInizioScheda() "+responsabile.getDataInizioScheda());
//			System.out.println("TB : responsabile.getIdResponsabile() "+responsabile.getSoggettoResponsabile() !=null ? responsabile.getSoggettoResponsabile().getIdResponsabile() : null);
//			System.out.println("TB : responsabile.getDataInizioRes() "+responsabile.getSoggettoResponsabile()!=null ? responsabile.getSoggettoResponsabile().getDataInizioRes() : null);
//			System.out.println("TB : responsabile.getIdSoggettoPartecipante() "+responsabile.getSoggettoPartecipante()!=null ? responsabile.getSoggettoPartecipante().getIdSoggettoPartecipante() : null);
//			System.out.println("TB : responsabile.getDataInizioSogg() "+responsabile.getSoggettoPartecipante()!=null ? responsabile.getSoggettoPartecipante().getDataInizioSogg() : null);
//			System.out.println("TB : RESPONSABILI ===== FINE ");
//			
			int index = 1;
			// ID_SOGGETTO_PARTECIPANTE
			
			stmt.setLong(index++, responsabile.getIdScheda());
			
			// DATA_INIZIO_RES
			
			stmt.setTimestamp(index++, responsabile.getDataInizioScheda());
			
			// ID_RESPONSABILE
			//gm modificato perchè è diventato nullable
			if(responsabile.getSoggettoResponsabile()==null || responsabile.getSoggettoResponsabile().getIdResponsabile()==0)
			    stmt.setNull(index++, Types.BIGINT);
			else
				stmt.setLong(index++, responsabile.getSoggettoResponsabile().getIdResponsabile());
			
			// DATA_INIZIO_RESP
			//gm modificato perchè è diventato nullable
			if(responsabile.getSoggettoResponsabile()==null || responsabile.getSoggettoResponsabile().getDataInizioRes()==null)
			    stmt.setNull(index++, Types.TIMESTAMP);
			else
				stmt.setTimestamp(index++, responsabile.getSoggettoResponsabile().getDataInizioRes());
			
			// ID_PARTECIPANTE
			if(responsabile.getSoggettoPartecipante()==null || responsabile.getSoggettoPartecipante().getIdSoggettoPartecipante()==0)
			    stmt.setNull(index++, Types.BIGINT);
			else
				stmt.setLong(index++, responsabile.getSoggettoPartecipante().getIdSoggettoPartecipante());
			
			// DATA_INIZIO_PART
			if(responsabile.getSoggettoPartecipante()==null || responsabile.getSoggettoPartecipante().getDataInizioSogg()==null)
			    stmt.setNull(index++, Types.TIMESTAMP);
			else
				stmt.setTimestamp(index++, responsabile.getSoggettoPartecipante().getDataInizioSogg());
			
			
			// DATA_INIZIO
			
			stmt.setObject(index++, getNow());
			
			// ID_RUOLO
			
			stmt.setInt(index++, responsabile.getIdRuolo());
			
			// STATO E DATA_FINEtop
			if (conferma){
				stmt.setLong(index++, StatiScheda.CONFERMATO);
				stmt.setTimestamp(index++, getNow());
				responsabile.setIdStato(StatiScheda.CONFERMATO);
			}else{
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
				stmt.setNull(index++, Types.TIMESTAMP);
				responsabile.setIdStato(StatiScheda.IN_DEFINIZIONE);
			}
			
			// ID_SEZIONE
			stmt.setString(index++,responsabile.getSezione());
			
			// CIG_PROG_ESTERNA
			stmt.setString(index++, responsabile.getCigProgEsterna());
			
			// DATA_AFF_PROG_ESTERNA
			stmt.setString(index++,PageHelper.formatDateOrNull( responsabile.getDataAffProgEsterna()));
			
			// DATA_AFF_CONS_ESTERNA
			stmt.setString(index++, PageHelper.formatDateOrNull(responsabile.getDataConsProgEsterna()));
			
			if(responsabile.getIdGruppo()!=0)
				stmt.setLong(index++, responsabile.getIdGruppo());
			else
				stmt.setNull(index++, Types.BIGINT);
			
			stmt.setBoolean(index++, responsabile.isMandante());
			
			stmt.execute();
			
		} finally {
			
			close(null, stmt);
			
		}
	}			
	
	/**
	 * metodo per la storicizzazione di un record
	 * 
	 * @param id_record String
	 * @param data_inizio_record Timestamp
	 * @param vecchiaData Timestamp
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean copyRecord(String id_record,Timestamp data_inizio_record, Timestamp vecchiaData) throws SQLException{
		String QUERY_UPDATE_OLD_RECORD =
			"UPDATE "+RESPONSABILE.TABLE_NAME+ " SET "
			+ RESPONSABILE.ID_STATO+ " = ?, "
			+ RESPONSABILE.DATA_INIZIO_AGGIUDICAZIONE+ " = ? "
			
			+" WHERE "
			+RESPONSABILE.T_ID_AGGIUDICAZIONE+" = ?"
			+" AND "+RESPONSABILE.T_DATA_INIZIO_AGGIUDICAZIONE+" = ?"
			+" AND "+RESPONSABILE.T_ID_STATO + "=" + StatiScheda.CONFERMATO;
		
		String QUERY_COPY_RECORD =
			"INSERT INTO "+RESPONSABILE.TABLE_NAME+" ("
			+ RESPONSABILE.ID_RUOLO
			+ ", " + RESPONSABILE.ID_RESPONSABILE 
			+ ", " + RESPONSABILE.DATA_INIZIO_RES
			//gm aggiunto per responsabili soggetti partecipanti
			+ ", " + RESPONSABILE.ID_PARTECIPANTE
			+ ", " + RESPONSABILE.DATA_INIZIO_PART
			
			+ ", " + RESPONSABILE.ID_AGGIUDICAZIONE 
			+ ", " + RESPONSABILE.DATA_INIZIO_AGGIUDICAZIONE  
			+ ", " + RESPONSABILE.ID_INCARICATO
			+ ", " + RESPONSABILE.ID_SEZIONE
			+ ", " + RESPONSABILE.CIG_PROG_ESTERNA
			+ ", " + RESPONSABILE.DATA_AFF_PROG_ESTERNA
			+ ", " + RESPONSABILE.DATA_CONS_PROG_ESTERNA
			+ ", " + RESPONSABILE.DATA_INIZIO
			+ ", " + RESPONSABILE.DATA_FINE
			+ ", " + RESPONSABILE.ID_STATO 
			+ ", " + RESPONSABILE.ID_GRUPPO 
			+ ", " + RESPONSABILE.MANDANTE + " ) "
			+"SELECT "
			+ RESPONSABILE.ID_RUOLO 
			+ ", " + RESPONSABILE.ID_RESPONSABILE 
			+ ", " + RESPONSABILE.DATA_INIZIO_RES
			//gm aggiunto per responsabili soggetti partecipanti
			+ ", " + RESPONSABILE.ID_PARTECIPANTE
			+ ", " + RESPONSABILE.DATA_INIZIO_PART
			
			+ ", " + RESPONSABILE.ID_AGGIUDICAZIONE 
			+ ", " + RESPONSABILE.DATA_INIZIO_AGGIUDICAZIONE  
			+ ", " + RESPONSABILE.ID_INCARICATO
			+ ", " + RESPONSABILE.ID_SEZIONE
			+ ", " + RESPONSABILE.CIG_PROG_ESTERNA
			+ ", " + RESPONSABILE.DATA_AFF_PROG_ESTERNA
			+ ", " + RESPONSABILE.DATA_CONS_PROG_ESTERNA
			+", ?"
			+", ?"
			+", ?"
			+ ", " + RESPONSABILE.ID_GRUPPO
			+ ", " + RESPONSABILE.MANDANTE
			+" FROM "+RESPONSABILE.TABLE_NAME
			+" WHERE "
			+RESPONSABILE.ID_AGGIUDICAZIONE+" = ?"
			+" AND "+RESPONSABILE.DATA_INIZIO_AGGIUDICAZIONE+" = ? "
			+" AND "+RESPONSABILE.ID_STATO+" = "+StatiScheda.CONFERMATO;
		
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try{
			int index = 1;
			stmt = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD,RESPONSABILE.TABLE_NAME));
			stmt.setTimestamp(index++, getNow()); //data_inizio_incaricato
			stmt.setNull(index++, Types.TIMESTAMP); // data_fine_incaricato
			stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE); //stato scheda
			stmt.setLong(index++, Long.parseLong(id_record));
			stmt.setTimestamp(index++, data_inizio_record);
			int rowsCopied = stmt.executeUpdate();
			if(rowsCopied > 0){
				index = 1;
				stmt2 = activeConnection.prepareStatement(QUERY_UPDATE_OLD_RECORD);
				stmt2.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA); //stato scheda
				stmt2.setTimestamp(index++, vecchiaData); //data_inizio_aggiudicazione
				stmt2.setLong(index++, Long.parseLong(id_record));
				stmt2.setTimestamp(index++, data_inizio_record);
				rowsCopied = stmt2.executeUpdate();
				return (rowsCopied>0);
			}
			else {
				logger.debug("RESPONSABILE_MANAGER.copyRecord: Nessun record da copiare");
				return true;
			}

				
		
		}
		finally{
			close(null, stmt2);
			close(null, stmt);
		}
	}
	
	
	//Metodo utilizzato dall'amministratore per cancellare il record attivo qualora fosse stata rifiutata la richiesta 
	//di annullamento
	
	private static String QUERY_DELETE_SOGG_RESPONSABILE = 
		"DELETE FROM "+RESPONSABILE.TABLE_NAME+
		" WHERE " + RESPONSABILE.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+RESPONSABILE.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	/**
	 * Metodo utilizzato dall'amministratore per cancellare il record attivo qualora fosse 
	 * stata rifiutata la richiesta di annullamento
	 * 
	 * @param idRecord String
	 * @param dataInizioRecord Timestamp
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int deleteRecord(String idRecord, Timestamp dataInizioRecord) throws SQLException{
		
		int numRow=-1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			int index = 1;
			stmt = activeConnection.prepareStatement(QUERY_DELETE_SOGG_RESPONSABILE);
			logger.debug("query per la delete record attivo INCARICATI: "+QUERY_DELETE_SOGG_RESPONSABILE);

			stmt.setInt(index++, Integer.parseInt(idRecord));
			stmt.setObject(index++,dataInizioRecord);
			
			numRow = stmt.executeUpdate();
			 
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}
	
	
	//Metodo utilizzato dall'amministratore per modificare il record con lo stato "Richiesta Di Annullamento" qualora sia stato rifiutata tale richiesta.
	//Viene quindi impostato lo stato del record da annullare a CONFERMATOe la data del record 
	
	private static String QUERY_UPDATE_RECORD_RICHIESTA_SOGG_RES = 
		"UPDATE "+RESPONSABILE.TABLE_NAME+
		" SET " + RESPONSABILE.ID_STATO + " = ?,"+
		RESPONSABILE.DATA_FINE + " = " + buildGetDate() +
		" WHERE "+RESPONSABILE.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+RESPONSABILE.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	/**
	 * Metodo utilizzato dall'amministratore per modificare il record con lo stato "Richiesta Di Annullamento" qualora 
	 * sia stato rifiutata tale richiesta. Viene quindi impostato lo stato del record da annullare a CONFERMATOe la data 
	 * del record 
	 * 
	 * @param idRecord String
	 * @param dataInizioRecord Timestamp
	 * @param stato_scheda String
	 * @return int - affected row cout
	 * @throws SQLException
	 */
	public int updateRecord(String idRecord, Timestamp dataInizioRecord, String stato_scheda ) throws SQLException{
		
		int numRow = -1; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_SOGG_RES);
			logger.debug("query per l'UPDATE del record con richiesta annullamento RESPONSABILE:::"+QUERY_UPDATE_RECORD_RICHIESTA_SOGG_RES);
			int index = 1;

			stmt.setObject(index++, stato_scheda);

			stmt.setInt(index++, Integer.parseInt(idRecord));
			
			stmt.setObject(index++,dataInizioRecord);

			numRow = stmt.executeUpdate();
			//PPactiveConnection.commit(currentActiveConnection);
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}
	private static String QUERY_UPDATE_RECORD_RICHIESTA_SOGG_RES_NEWRECORD = 
		"UPDATE "+RESPONSABILE.TABLE_NAME+
		" SET " + RESPONSABILE.ID_STATO + " = ?,"+ //+STATI_SCHEDA.CONFERMATO+
		RESPONSABILE.DATA_INIZIO_AGGIUDICAZIONE + " = ?"+
		" WHERE "+RESPONSABILE.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+RESPONSABILE.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	public int updateRecordToPointToNew(String idAggiudicazione, Timestamp dataInizioAggOld,Timestamp dataInizioAggNew, String stato_scheda ) throws SQLException{
		
		int numRow = -1; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_SOGG_RES_NEWRECORD);
			logger.debug("query per l'UPDATE del record con richiesta annullamento RESPONSABILE:::"+QUERY_UPDATE_RECORD_RICHIESTA_SOGG_RES_NEWRECORD);
			int index = 1;

			stmt.setObject(index++, stato_scheda);
			stmt.setTimestamp(index++, dataInizioAggNew);
			stmt.setInt(index++, Integer.parseInt(idAggiudicazione));
			
			stmt.setObject(index++,dataInizioAggOld);

			numRow = stmt.executeUpdate();
			//PPactiveConnection.commit(currentActiveConnection);
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}
	/**
	 * @see it.avlp.simog.db.AccessiDB#loadRuoliSezione(java.lang.String, java.lang.Object)
	 */
	public Map<String,String> loadRuoliSezione(String sezione,Object o) throws SQLException{		
		try {
			return super.loadRuoliSezione(sezione,o);
		}catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}
	
	private static String QUERY_LISTA_RESPONSABILI_BY_RESP = 
		"SELECT "+RESPONSABILE.TABLE_NAME+".*,"
		+ RUOLI_RESPONSABILE.T_DESCRIZIONE+" AS "+RUOLI_RESPONSABILE.TABLE_NAME+RUOLI_RESPONSABILE.DESCRIZIONE+","
		+ SOGGETTI_RESPONSABILI.COGNOME+","
		+ SOGGETTI_RESPONSABILI.NOME+","
		+ SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE+","
		+ SOGGETTI_RESPONSABILI.TELEFONO+","
		+ SOGGETTI_RESPONSABILI.EMAIL+","
		+ SOGGETTI_RESPONSABILI.FAX + ", "
		+ SOGGETTI_RESPONSABILI.CAP+ ", "
		+ SOGGETTI_RESPONSABILI.INDIRIZZO+ ", "
		+ SOGGETTI_RESPONSABILI.COMUNE_ISTAT+ ", "
		+ RESPONSABILE.T_ID_RUOLO + ", "
		+ RESPONSABILE.T_ID_RESPONSABILE + ", "
		+ RESPONSABILE.T_DATA_INIZIO_RES + ", "
		+ RESPONSABILE.CIG_PROG_ESTERNA + ", " 
		+ RESPONSABILE.DATA_AFF_PROG_ESTERNA + ", "
		+ RESPONSABILE.DATA_CONS_PROG_ESTERNA + ", "
		+ RESPONSABILE.T_DATA_INIZIO_AGGIUDICAZIONE + ", "
		+ RESPONSABILE.T_ID_AGGIUDICAZIONE
		+" FROM " 
		+ RESPONSABILE.TABLE_NAME + "," 
		+ RUOLI_RESPONSABILE.TABLE_NAME + ","
		+ SOGGETTI_RESPONSABILI.TABLE_NAME
		+ " WHERE "
		+ RESPONSABILE.T_ID_RUOLO + "=" + RUOLI_RESPONSABILE.T_ID_RUOLO
		+ " AND " + SOGGETTI_RESPONSABILI.T_ID_RESPONSABILE + " = " +RESPONSABILE.T_ID_RESPONSABILE
	    //+ " AND " + SOGGETTI_RESPONSABILI.T_DATA_INIZIO_RES + " = " + RESPONSABILE.T_DATA_INIZIO_RES
	    + " AND " + RESPONSABILE.T_ID_RESPONSABILE + " = ?" ;
	    //+ " AND " + RESPONSABILE.T_DATA_INIZIO_RES + " = ?" ;
	
	/**
	 * metodo per il recupero dei responsabili congruenti ad un soggetto
	 * della sezionie di cui stringa in inngresso
	 * 
	 * @param idResp long
	 * @param dataInizioRes Timestamp
	 * @return List&lt;ResponsabileBean&gt;
	 * @throws SQLException
	 */
	public List<ResponsabileBean> loadByResp(long idResp) throws SQLException{
		
		String mtd = "loadByResp";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<ResponsabileBean> ris = new ArrayList<ResponsabileBean>();
		ResponsabileBean nuovoResponsabile = null;
		SoggettoResponsabileBean nuovoSoggettoResponsabile = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_LISTA_RESPONSABILI_BY_RESP);
			logger.debug(logPrefix +" query ["+QUERY_LISTA_RESPONSABILI_BY_RESP+"]");
			int index = 1;
			stmt.setLong(index++, idResp);
			rs = stmt.executeQuery();
			while(rs.next()){
				nuovoResponsabile = new ResponsabileBean();
				
				nuovoSoggettoResponsabile = new SoggettoResponsabileBean();				
				nuovoResponsabile.setDescrizioneRuolo(rs.getString(RUOLI_RESPONSABILE.TABLE_NAME + RUOLI_RESPONSABILE.DESCRIZIONE));
				nuovoSoggettoResponsabile.setCognome(rs.getString(SOGGETTI_RESPONSABILI.COGNOME));
				nuovoSoggettoResponsabile.setNome(rs.getString(SOGGETTI_RESPONSABILI.NOME));
				nuovoSoggettoResponsabile.setCodiceFiscaleResponsabile(rs.getString(SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE));
				nuovoSoggettoResponsabile.setTelefono(rs.getString(SOGGETTI_RESPONSABILI.TELEFONO));
				nuovoSoggettoResponsabile.setEmail(rs.getString(SOGGETTI_RESPONSABILI.EMAIL));
				nuovoSoggettoResponsabile.setFax(rs.getString(SOGGETTI_RESPONSABILI.FAX));
				nuovoSoggettoResponsabile.setIdResponsabile(rs.getInt(RESPONSABILE.ID_RESPONSABILE));
				nuovoSoggettoResponsabile.setDataInizioRes(rs.getTimestamp(RESPONSABILE.DATA_INIZIO_RES));
				nuovoSoggettoResponsabile.setIndirizzo(rs.getString(SOGGETTI_RESPONSABILI.INDIRIZZO));
				nuovoSoggettoResponsabile.setCap(rs.getString(SOGGETTI_RESPONSABILI.CAP));
				nuovoSoggettoResponsabile.setComuneIstat(rs.getString(SOGGETTI_RESPONSABILI.COMUNE_ISTAT));
				
				nuovoResponsabile.setIdRuolo(rs.getInt(RESPONSABILE.ID_RUOLO));
				nuovoResponsabile.setSezione(rs.getString(RESPONSABILE.ID_SEZIONE));
				nuovoResponsabile.setCigProgEsterna(rs.getString(RESPONSABILE.CIG_PROG_ESTERNA));
				nuovoResponsabile.setDataAffProgEsterna(PageHelper.getViewDate(rs.getString(RESPONSABILE.DATA_AFF_PROG_ESTERNA)));
				nuovoResponsabile.setDataConsProgEsterna(PageHelper.getViewDate(rs.getString(RESPONSABILE.DATA_CONS_PROG_ESTERNA)));
				nuovoResponsabile.setSoggettoResponsabile(nuovoSoggettoResponsabile);
				
				nuovoResponsabile.setDataInizioScheda(PageHelper.parseTime(rs.getString(RESPONSABILE.DATA_INIZIO_AGGIUDICAZIONE)));
				nuovoResponsabile.setIdScheda(rs.getLong(RESPONSABILE.ID_AGGIUDICAZIONE));
				
				ris.add(nuovoResponsabile);
			}			
						
		} finally {
			close(rs, stmt);
		}
		return ris;
	}

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
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_RESPONSABILI_AGG);
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

	/**@deprecated: DA RIMUOVERE ? INSERITO PER TEST POTREBBE DARE ADITO AD ERRORI**/
	private static String QUERY_LISTA_RESPONSABILI_WITHOUT_SEZIONE = 
		"SELECT "+RESPONSABILE.TABLE_NAME+".*,"
		+ RUOLI_RESPONSABILE.T_DESCRIZIONE+" AS "+RUOLI_RESPONSABILE.TABLE_NAME+RUOLI_RESPONSABILE.DESCRIZIONE+","
		+ SOGGETTI_RESPONSABILI.COGNOME+","
		+ SOGGETTI_RESPONSABILI.NOME+","
		+ SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE+","
		+ SOGGETTI_RESPONSABILI.TELEFONO+","
		+ SOGGETTI_RESPONSABILI.EMAIL+","
		+ SOGGETTI_RESPONSABILI.FAX + ", "
		+ SOGGETTI_RESPONSABILI.CAP+ ", "
		+ SOGGETTI_RESPONSABILI.INDIRIZZO+ ", "
		+ SOGGETTI_RESPONSABILI.COMUNE_ISTAT+ ", "
		+ RESPONSABILE.T_ID_RUOLO + ", "
		+ RESPONSABILE.T_ID_RESPONSABILE + ", "
		+ RESPONSABILE.T_DATA_INIZIO_RES + ", "
		+ RESPONSABILE.CIG_PROG_ESTERNA + ", " 
		+ RESPONSABILE.DATA_AFF_PROG_ESTERNA + ", "
		+ RESPONSABILE.DATA_CONS_PROG_ESTERNA
		+" FROM " 
		+ RESPONSABILE.TABLE_NAME + "," 
		+ RUOLI_RESPONSABILE.TABLE_NAME + ","
		+ SOGGETTI_RESPONSABILI.TABLE_NAME
		+ " WHERE "
		+ RESPONSABILE.T_ID_AGGIUDICAZIONE + " = ? "
		+ " AND " + RESPONSABILE.T_DATA_INIZIO_AGGIUDICAZIONE + " = ? "
		+ " AND " + RESPONSABILE.T_ID_STATO + "!=" + StatiScheda.ELIMINATO
		+ " AND ( " + RESPONSABILE.T_ID_STATO + "=" + StatiScheda.CONFERMATO
		+ " OR " + RESPONSABILE.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE+" ) " 
		+ " AND " + RESPONSABILE.T_ID_RUOLO + "=" + RUOLI_RESPONSABILE.T_ID_RUOLO
		+ " AND " + SOGGETTI_RESPONSABILI.T_ID_RESPONSABILE + "="+RESPONSABILE.T_ID_RESPONSABILE
	    + " AND " + SOGGETTI_RESPONSABILI.T_DATA_INIZIO_RES + " = " + RESPONSABILE.T_DATA_INIZIO_RES;
	
	/**
	 * 
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @return
	 * @throws SQLException
	 * @deprecated: DA RIMUOVERE - ADITO AD ERRORI INTRODOTTO SOLO PER TEST
	 */
	public List<ResponsabileBean> loadMany(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
		
		String mtd = "loadMany";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<ResponsabileBean> ris = new ArrayList<ResponsabileBean>();
		ResponsabileBean nuovoResponsabile = null;
		SoggettoResponsabileBean nuovoSoggettoResponsabile = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_LISTA_RESPONSABILI_WITHOUT_SEZIONE);
			logger.debug(logPrefix +" query ["+QUERY_LISTA_RESPONSABILI_WITHOUT_SEZIONE+"]");
			int index = 1;
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataInizioAggiudicazione);
			rs = stmt.executeQuery();
			while(rs.next()){
				nuovoResponsabile = new ResponsabileBean();
					nuovoSoggettoResponsabile = new SoggettoResponsabileBean();				
					nuovoResponsabile.setDescrizioneRuolo(rs.getString(RUOLI_RESPONSABILE.TABLE_NAME + RUOLI_RESPONSABILE.DESCRIZIONE));
					nuovoSoggettoResponsabile.setCognome(rs.getString(SOGGETTI_RESPONSABILI.COGNOME));
					nuovoSoggettoResponsabile.setNome(rs.getString(SOGGETTI_RESPONSABILI.NOME));
					nuovoSoggettoResponsabile.setCodiceFiscaleResponsabile(rs.getString(SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE));
					nuovoSoggettoResponsabile.setTelefono(rs.getString(SOGGETTI_RESPONSABILI.TELEFONO));
					nuovoSoggettoResponsabile.setEmail(rs.getString(SOGGETTI_RESPONSABILI.EMAIL));
					nuovoSoggettoResponsabile.setFax(rs.getString(SOGGETTI_RESPONSABILI.FAX));
					nuovoSoggettoResponsabile.setIdResponsabile(rs.getInt(RESPONSABILE.ID_RESPONSABILE));
					nuovoSoggettoResponsabile.setDataInizioRes(rs.getTimestamp(RESPONSABILE.DATA_INIZIO_RES));
					
				/* adds*/
					nuovoSoggettoResponsabile.setIndirizzo(rs.getString(SOGGETTI_RESPONSABILI.INDIRIZZO));
					nuovoSoggettoResponsabile.setCap(rs.getString(SOGGETTI_RESPONSABILI.CAP));
					nuovoSoggettoResponsabile.setComuneIstat(rs.getString(SOGGETTI_RESPONSABILI.COMUNE_ISTAT));
				/*end*/
				
				nuovoResponsabile.setIdRuolo(rs.getInt(RESPONSABILE.ID_RUOLO));
				nuovoResponsabile.setSezione(rs.getString(RESPONSABILE.ID_SEZIONE));
				nuovoResponsabile.setCigProgEsterna(rs.getString(RESPONSABILE.CIG_PROG_ESTERNA));
				nuovoResponsabile.setDataAffProgEsterna(PageHelper.getViewDate(rs.getString(RESPONSABILE.DATA_AFF_PROG_ESTERNA)));
				nuovoResponsabile.setDataConsProgEsterna(PageHelper.getViewDate(rs.getString(RESPONSABILE.DATA_CONS_PROG_ESTERNA)));
				nuovoResponsabile.setSoggettoResponsabile(nuovoSoggettoResponsabile);
				
				nuovoResponsabile.setDataInizioScheda(dataInizioAggiudicazione);
				nuovoResponsabile.setIdScheda(idAggiudicazione);
				
				ris.add(nuovoResponsabile);
			}			
						
		} finally {
			close(rs, stmt);
		}
		return ris;
	}
}
