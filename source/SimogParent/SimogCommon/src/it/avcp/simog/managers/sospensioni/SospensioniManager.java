package it.avcp.simog.managers.sospensioni;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamento;
import it.avcp.simog.managers.comportamento.caricamento.ILoadSospensioni;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.common.servlet.ParametriServletSospensioni;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.MOTIVI_SOSPENSIONE;
import it.avlp.simog.db.generated.R129;
import it.avlp.simog.db.generated.SOSPENSIONI;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
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
 * Classe per la gestione dei dati relativi alle sospensioni
 *
 */
public class SospensioniManager extends AccessiDB implements IAnnullamento,ILoadSospensioni{
	
	public static String CLAZZ = "SospensioniManager";

	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public SospensioniManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	public final String INSERT_SOSPENSIONI = "INSERT INTO " + SOSPENSIONI.TABLE_NAME + "("
			//+SOSPENSIONI.ID_SOSPENSIONE+","
			+SOSPENSIONI.DATA_INIZIO_SOSP+","
			+SOSPENSIONI.DATA_FINE_SOSP+","
			+SOSPENSIONI.ID_STATO+","
			+SOSPENSIONI.ID_AGGIUDICAZIONE+","
			+SOSPENSIONI.DATA_INIZIO_AGGIUDICAZIONE+","
			+SOSPENSIONI.DATA_VERB_SOSP+","
			+SOSPENSIONI.DATA_VERB_RIPR+","
			+SOSPENSIONI.ID_MOTIVO_SOSP+","
			+SOSPENSIONI.FLAG_SUPERO_TEMP+","
			+SOSPENSIONI.FLAG_RISERVE+","
			+SOSPENSIONI.FLAG_VERBALE+","
			+SOSPENSIONI.ID_SCHEDA_LOCALE
			+") VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * metodo per l'inserimento di una sospensione
	 * 
	 * @param bean SospensioniBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public void insert(SospensioniBean bean, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try {
			stmt = activeConnection.prepareStatement(createInsertQuery(INSERT_SOSPENSIONI, SOSPENSIONI.ID_SOSPENSIONE));
			
			bean.setDataInizioSosp(getNow());
			stmt.setTimestamp(index++, bean.getDataInizioSosp());
			
			stmt.setNull(index++,Types.TIMESTAMP);
			//stmt.setString(index++, bean.getDataInizioSosp().toString());
			//stmt.setNull(index++, bean.getDataFineSosp());
			
			bean.setIdStato(StatiScheda.IN_DEFINIZIONE);
			stmt.setLong(index++, bean.getIdStato());
			
			stmt.setLong(index++, bean.getIdAggiudicazione());
			stmt.setTimestamp(index++, bean.getDataInizioAggiudicazione());
			stmt.setString(index++, PageHelper.formatDateOrNull( bean.getDataVerbSosp()));
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataVerbRipr()));
			stmt.setLong(index++, bean.getIdMotivoSosp());
			stmt.setString(index++, bean.getFlagSuperoTemp());
			stmt.setString(index++, bean.getFlagRiserve());
			stmt.setString(index++, bean.getFlagVerbale());
			
			if(bean.getIdLocale() == null){
				stmt.setNull(index++, Types.VARCHAR);
			}else{
				stmt.setString(index++, bean.getIdLocale());
			}
			
			

			if(stmt.execute()){
				rs = stmt.getResultSet();
				rs.next();
				bean.setIdSospensione(rs.getLong(SOSPENSIONI.ID_SOSPENSIONE));
				List<Object> attributiChiave = new ArrayList<Object>();
				//attributiChiave.add(bean.getIdSospensione());
				//attributiChiave.add(bean.getDataFineSosp());
				attributiChiave.add(bean.getIdSospensione());
				attributiChiave.add(bean.getDataInizioSosp());
				LogBloccoDatiManager.loggingINSERT(activeConnection, logger, cfUtente, ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI, attributiChiave);
			}
			
		} finally {
			close(rs, stmt);
		}
	}
	
	public static String UPDATE_SOSPENSIONI = "UPDATE " + SOSPENSIONI.TABLE_NAME + " SET "
		
		+SOSPENSIONI.DATA_FINE_SOSP+"=? ,"
		+SOSPENSIONI.ID_STATO+"=? ,"
		//+SOSPENSIONI.DATA_INIZIO_SOSP+"=? ,"
		//+SOSPENSIONI.ID_AGGIUDICAZIONE+"=? ,"
		//+SOSPENSIONI.DATA_INIZIO_AGGIUDICAZIONE+"=? ,"
		+SOSPENSIONI.DATA_VERB_SOSP+"=? ,"
		+SOSPENSIONI.DATA_VERB_RIPR+"=? ,"
		+SOSPENSIONI.ID_MOTIVO_SOSP+"=? ,"
		+SOSPENSIONI.FLAG_SUPERO_TEMP+"=? ,"
		+SOSPENSIONI.FLAG_RISERVE+"=? ,"
		+SOSPENSIONI.FLAG_VERBALE
		+"=? "
	    +"WHERE "+ SOSPENSIONI.ID_SOSPENSIONE + " = ? AND "
	    +SOSPENSIONI.DATA_INIZIO_SOSP + " = ? " ;
//	    " AND (" + SOSPENSIONI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING
	 // PP fix controllo stato scheda +" OR " + SOSPENSIONI.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING 
//	    + " ) ";
	
    private final String WHERE_CONF = " AND (" + SOSPENSIONI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING
          + " OR " + SOSPENSIONI.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING  + " ) ";
     private final String WHERE_DEF = " AND " + SOSPENSIONI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING;
	
	private int update(SospensioniBean bean, String cfUtente, boolean confirm)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try {
			//OLD(SBAGLIATA PER ALTRO) Impedire di salvare una scheda confermata ma consentire la conferma di una scheda confermata per effettuare l'azione MODIFICA
//			String Update_Sospesioni = UPDATE_SOSPENSIONI + (!confirm ? " AND " + SOSPENSIONI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING :""); 
			
			//NEW  permette di modificare una scheda in definizione o confermata
			String Update_Sospesioni = UPDATE_SOSPENSIONI + (SimogFlags.isFlagNoDate() ? WHERE_CONF : WHERE_DEF);
			
			stmt = activeConnection.prepareStatement(Update_Sospesioni);
			
			if(confirm){
				stmt.setTimestamp(index++, getNow()); // data fine 
				stmt.setLong(index++, StatiScheda.CONFERMATO);
			}else{
				//un aggiornamento normale
				stmt.setNull(index++, Types.TIMESTAMP);//data fine record
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
			}
			stmt.setString(index++, PageHelper.formatDateOrNull( bean.getDataVerbSosp()));
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataVerbRipr()));
			stmt.setLong(index++, bean.getIdMotivoSosp());
			stmt.setString(index++, bean.getFlagSuperoTemp());
			stmt.setString(index++, bean.getFlagRiserve());
			stmt.setString(index++, bean.getFlagVerbale());
			
			stmt.setLong(index++, bean.getIdSospensione());
			stmt.setTimestamp(index++, bean.getDataInizioSosp());
			int num = stmt.executeUpdate();
			
			List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(bean.getIdSospensione());
			attributiChiave.add(bean.getDataInizioSosp());
			// Una conferma di una scheda già confermata viene loggata come Modifica
			if(confirm && !bean.isConfirmed())
				LogBloccoDatiManager.loggingCONFIRM(activeConnection, logger, cfUtente, ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI, attributiChiave);
			else 
				LogBloccoDatiManager.loggingUPDATE(activeConnection, logger, cfUtente, ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI, attributiChiave);
			return num;
		} finally {
			close(rs, stmt);
		}
	}
	
	/**
	 * metodo per il salvataggio di di una sospensione
	 * 
	 * @param bean SospensioniBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public int save(SospensioniBean bean, String cfUtente)throws SQLException{
		return update(bean,cfUtente, false);
	}
	
	/**
	 *  metodo per la conferma di una sospensione
	 *  
	 * @param bean SospensioniBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public int confirm(SospensioniBean bean, String cfUtente)throws SQLException{
		return update(bean,cfUtente, true);
	}
	
	public final String SELECT_ONE_SOSPENSIONI = "SELECT "
		+ SOSPENSIONI.ID_SOSPENSIONE
		+ ", " + SOSPENSIONI.DATA_INIZIO_SOSP
	    + ", " + SOSPENSIONI.DATA_FINE_SOSP
	   
	    + ", " + SOSPENSIONI.ID_AGGIUDICAZIONE
	    + ", " + SOSPENSIONI.DATA_INIZIO_AGGIUDICAZIONE
	    + ", " + SOSPENSIONI.DATA_VERB_RIPR
	    + ", " + SOSPENSIONI.DATA_VERB_SOSP
	    + ", " + SOSPENSIONI.FLAG_RISERVE
	    + ", " + SOSPENSIONI.FLAG_SUPERO_TEMP
	    + ", " + SOSPENSIONI.FLAG_VERBALE
	    + ", " + SOSPENSIONI.ID_MOTIVO_SOSP
	    +",  " + MOTIVI_SOSPENSIONE.DESCRIZIONE +" AS "+ MOTIVI_SOSPENSIONE.TABLE_NAME + MOTIVI_SOSPENSIONE.DESCRIZIONE
	    
	     + "," +SOSPENSIONI.T_ID_STATO + ", " + STATI_SCHEDA.DESCRIZIONE + " + " +
		   buildRichAnnQuery(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI, SOSPENSIONI.T_ID_SOSPENSIONE,null) 
		+" AS "+STATI_SCHEDA.DESCRIZIONE
		+", " + SOSPENSIONI.ID_SCHEDA_LOCALE
		+ " FROM " + SOSPENSIONI.TABLE_NAME + ", " + STATI_SCHEDA.TABLE_NAME
		+ " WHERE " 
//      Aggiunta la condizione per ottenere la descrizione della motivazione
	    + SOSPENSIONI.ID_MOTIVO_SOSP + " = " + MOTIVI_SOSPENSIONE.ID_MOTIVO_SOSP;
//	    
//			+ SOSPENSIONI.T_ID_SOSPENSIONE + " = ? AND "		   		    	    
//		    + SOSPENSIONI.T_DATA_INIZIO_SOSP + " = ?  AND "
//		    + SOSPENSIONI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
//		    +" AND (" + SOSPENSIONI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
//			+" OR " + SOSPENSIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+") " ; 

	
	/**
	 * Metodo per il caricamento di una sospensione, dati il suo id e la sua data inizio
	 * 
	 * @param idSospensioni long
	 * @param dataInizioSospensioni Timestamp
	 * @return SospensioniBean or null
	 * @throws SQLException
	 */
	public SospensioniBean loadOne(long idSospensioni, Timestamp dataInizioSospensioni)throws SQLException{
		List<SospensioniBean> lista = load(idSospensioni, dataInizioSospensioni, false);
		if(lista!= null && lista.size() > 0)
			return lista.get(0);
		else return null;
	}
	
	public  final String SELECT_MANY_SOSPENSIONI = "SELECT "
		+ SOSPENSIONI.ID_SOSPENSIONE
		+ ", " + SOSPENSIONI.DATA_INIZIO_SOSP
	    + ", " + SOSPENSIONI.DATA_FINE_SOSP   
	    + ", " + SOSPENSIONI.ID_AGGIUDICAZIONE
	    + ", " + SOSPENSIONI.DATA_INIZIO_AGGIUDICAZIONE
	    + ", " + SOSPENSIONI.DATA_VERB_RIPR
	    + ", " + SOSPENSIONI.DATA_VERB_SOSP
	    + ", " + SOSPENSIONI.FLAG_RISERVE
	    + ", " + SOSPENSIONI.FLAG_SUPERO_TEMP
	    + ", " + SOSPENSIONI.FLAG_VERBALE
	    + ", " + SOSPENSIONI.T_ID_MOTIVO_SOSP
	    + ", " + MOTIVI_SOSPENSIONE.T_DESCRIZIONE + " AS " + MOTIVI_SOSPENSIONE.TABLE_NAME + MOTIVI_SOSPENSIONE.DESCRIZIONE
	    + "," +SOSPENSIONI.T_ID_STATO + ", " + STATI_SCHEDA.T_DESCRIZIONE + " + " +
		   buildRichAnnQuery(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI, SOSPENSIONI.T_ID_SOSPENSIONE,null) 
		+" AS "+STATI_SCHEDA.DESCRIZIONE
		+ ", " + SOSPENSIONI.ID_SCHEDA_LOCALE + " "
	    + " FROM " + SOSPENSIONI.TABLE_NAME + ", " + STATI_SCHEDA.TABLE_NAME +", "+ MOTIVI_SOSPENSIONE.TABLE_NAME  
	    + " WHERE " + SOSPENSIONI.T_ID_AGGIUDICAZIONE + " = ? AND "
	    + SOSPENSIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " = ?  AND "
	    + SOSPENSIONI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
	    + " AND " + MOTIVI_SOSPENSIONE.T_ID_MOTIVO_SOSP + "=" + SOSPENSIONI.T_ID_MOTIVO_SOSP
	    +" AND (" + SOSPENSIONI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + SOSPENSIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+") order by " + SOSPENSIONI.DATA_VERB_SOSP;
	
	/**
	 * metodo per il recupero delle sospensioni associate alla aggiudicazione di cui id
	 * 
	 * @param idAggiudicazioni long
	 * @param dataInizioAggiudicazione Timestamp
	 * @return List&lt;SospensioniBean&gt;
	 * @throws SQLException
	 */
	public List<SospensioniBean> loadMany(long idAggiudicazioni, Timestamp dataInizioAggiudicazione) throws SQLException{
		return load(idAggiudicazioni, dataInizioAggiudicazione, true);
	}
	
	private List<SospensioniBean> load(long idSospensione, Timestamp date, boolean byAggiudicazione) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		//TableBean result = null;
		ArrayList<SospensioniBean> listaSchede = new ArrayList<SospensioniBean>();
		SospensioniBean bean = null;
		try{
			if(byAggiudicazione)
				stmt = activeConnection.prepareStatement(SELECT_MANY_SOSPENSIONI);
			else
				stmt = activeConnection.prepareStatement(SELECT_ONE_SOSPENSIONI + WHERE_STANDARD);
			
			stmt.setLong(index++, idSospensione);
			stmt.setTimestamp(index++, date);
			rs = stmt.executeQuery();
			while(rs.next()){
				bean = new SospensioniBean();
				fillBean(rs, bean);
				listaSchede.add(bean);
			}
			listaSchede.trimToSize();
			return listaSchede;
		}finally{
			close(rs,stmt);
		}
	}
	
	
	/**
	 * metodo per la storicizzazione di un record
	 * 
	 * @param idSospensioni long
	 * @param dataInizioSospensione Timestamp
	 * @return Timestamp - nuova data sospensioni
	 * @throws SQLException
	 */
	public Timestamp copyRecord(long idSospensioni, Timestamp dataInizioSospensione) throws SQLException{
		String QUERY_SELECT_DATA_FINE = "SELECT " + SOSPENSIONI.DATA_FINE_SOSP
		+ " FROM " + SOSPENSIONI.TABLE_NAME
		+ " WHERE " + SOSPENSIONI.ID_SOSPENSIONE + " = ? "
		+ " AND " + SOSPENSIONI.DATA_INIZIO_SOSP + " = ?"
		+ " AND " + SOSPENSIONI.ID_STATO + " = " + StatiScheda.CONFERMATO;
		
		String UPDATE_STATO_OLD_SOSPENSIONI = 
			"UPDATE " + SOSPENSIONI.TABLE_NAME + " SET "
			+ SOSPENSIONI.ID_STATO + " = ? "
			+ ", " + SOSPENSIONI.DATA_INIZIO_SOSP + " = ?"
			+ ", " + SOSPENSIONI.DATA_FINE_SOSP + " = ?"
			+ " WHERE " + SOSPENSIONI.ID_SOSPENSIONE + " = ? "
			+ " AND " + SOSPENSIONI.DATA_INIZIO_SOSP + " = ?"
			+ " AND " + SOSPENSIONI.ID_STATO + " = " + StatiScheda.CONFERMATO;
		
		String COPY_RECORD = " INSERT INTO " + SOSPENSIONI.TABLE_NAME + "("
			+ SOSPENSIONI.ID_SOSPENSIONE	
		    + ", " + SOSPENSIONI.DATA_INIZIO_AGGIUDICAZIONE
		    + ", " + SOSPENSIONI.ID_AGGIUDICAZIONE
		    + ", " + SOSPENSIONI.DATA_VERB_RIPR
		    + ", " + SOSPENSIONI.DATA_VERB_SOSP
		    + ", " + SOSPENSIONI.FLAG_RISERVE
		    + ", " + SOSPENSIONI.FLAG_SUPERO_TEMP
		    + ", " + SOSPENSIONI.FLAG_VERBALE		    
		    + ", " + SOSPENSIONI.ID_MOTIVO_SOSP
		    + ", " + SOSPENSIONI.ID_SCHEDA_LOCALE
		    
		    + ", " + SOSPENSIONI.DATA_INIZIO_SOSP
		    + ", " + SOSPENSIONI.DATA_FINE_SOSP
		    + ", " + SOSPENSIONI.ID_STATO
		    + ") SELECT "
		    + SOSPENSIONI.ID_SOSPENSIONE
		    + ", " + SOSPENSIONI.DATA_INIZIO_AGGIUDICAZIONE
		    + ", " + SOSPENSIONI.ID_AGGIUDICAZIONE
		    + ", " + SOSPENSIONI.DATA_VERB_RIPR
		    + ", " + SOSPENSIONI.DATA_VERB_SOSP
		    + ", " + SOSPENSIONI.FLAG_RISERVE
		    + ", " + SOSPENSIONI.FLAG_SUPERO_TEMP
		    + ", " + SOSPENSIONI.FLAG_VERBALE
		    + ", " + SOSPENSIONI.ID_MOTIVO_SOSP
		    + ", " + SOSPENSIONI.ID_SCHEDA_LOCALE
		    + ", ?"
			+ ", ?"
			+ ", ?"
			+ " FROM " + SOSPENSIONI.TABLE_NAME
			+ " WHERE " + SOSPENSIONI.ID_SOSPENSIONE + " = ? "
			+ " AND " + SOSPENSIONI.DATA_INIZIO_SOSP + " = ?";


		Timestamp dataFine = null;
		Timestamp nuovaDataSospensioni = null;
		int index = 1;
		ResultSet rs = null;
		PreparedStatement getDataFine = null;
		PreparedStatement updateRecord = null;
		PreparedStatement copyRecord = null;
		try{
			//prendo la data fine del record
			getDataFine = activeConnection.prepareStatement(QUERY_SELECT_DATA_FINE);
			getDataFine.setLong(index++, idSospensioni);
			getDataFine.setTimestamp(index++, dataInizioSospensione);
			rs = getDataFine.executeQuery();
			if(rs.next()){
				dataFine = rs.getTimestamp(SOSPENSIONI.DATA_FINE_SOSP);
				
				//il record corrente diventa il nuovo record
				index = 1;
				nuovaDataSospensioni = getNow();
				updateRecord = activeConnection.prepareStatement(UPDATE_STATO_OLD_SOSPENSIONI);
				updateRecord.setInt(index++, StatiScheda.IN_DEFINIZIONE);
				updateRecord.setTimestamp(index++, nuovaDataSospensioni);
				updateRecord.setNull(index++, Types.TIMESTAMP);
				updateRecord.setLong(index++, idSospensioni);
				updateRecord.setTimestamp(index++, dataInizioSospensione);
				updateRecord.execute();
				
				//copy record
				index = 1;
				copyRecord = activeConnection.prepareStatement(createCopyRecord(COPY_RECORD,SOSPENSIONI.TABLE_NAME));
				copyRecord.setTimestamp(index++, dataInizioSospensione);
				copyRecord.setTimestamp(index++, dataFine);
				copyRecord.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA);
				copyRecord.setLong(index++, idSospensioni);
				copyRecord.setTimestamp(index++, nuovaDataSospensioni);
				copyRecord.execute();
				
				return nuovaDataSospensioni;
			}
			return null;
		}/*catch(Exception e){	
			
			e.printStackTrace();
			throw new SQLException(e.getMessage());
			
		}*/finally{
			close(rs, getDataFine);
			close(null, updateRecord);
			close(null, copyRecord);
		}
	}
	
	/**
	 * metodo per il controllo dell'esistenza di una sospensione di cui id
	 * 
	 * @param idSospensioni long
	 * @param dataInizioSospensioni Timestamp
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean existSospensioni(long idSospensioni, Timestamp dataInizioSospensioni) throws SQLException{

		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM " + SOSPENSIONI.TABLE_NAME + " WHERE " + 
		SOSPENSIONI.ID_SOSPENSIONE + " = ? AND " + 
		SOSPENSIONI.DATA_INIZIO_SOSP + " = ?";
		
		try{
			stmt = activeConnection.prepareStatement(query);
			stmt.setLong(1, idSospensioni);
			stmt.setTimestamp(2, dataInizioSospensioni);
			rs = stmt.executeQuery();
			return (rs.next());
		}finally{
			close(rs, stmt);
		}
	}
	
	public static String QUERY_UPDATE_SOSPENSIONI_RICHIESTA_ANNULLAMENTO_RESPINTA =
		"UPDATE " + SOSPENSIONI.TABLE_NAME 
		+ " SET " + SOSPENSIONI.DATA_INIZIO_AGGIUDICAZIONE + " = ? "
		+ " WHERE " + SOSPENSIONI.ID_AGGIUDICAZIONE + " = ? "
		+ " AND " + SOSPENSIONI.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	private String DELETE_RECORD_SOSPENSIONI = 
		"DELETE FROM " + SOSPENSIONI.TABLE_NAME
		+ " WHERE " + SOSPENSIONI.ID_SOSPENSIONE + " = ?"
		+ " AND " + SOSPENSIONI.DATA_INIZIO_SOSP + " = ?";
	
	/**
	 * metodo per la cancellazione di un record
	 * 
	 * @param idSospensione long
	 * @param dataInizioSospensione Timestamp
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int deleteRecord(long idSospensione, Timestamp dataInizioSospensione)throws SQLException{
		PreparedStatement stmt = null;
		int index = 1;
		try{
			
			stmt = activeConnection.prepareStatement(DELETE_RECORD_SOSPENSIONI);
			stmt.setLong(index++, idSospensione);
			stmt.setTimestamp(index++, dataInizioSospensione);
			return stmt.executeUpdate();
		}finally{
			close(null,stmt);
		}
	}
	
	private static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO = 
		"UPDATE "+SOSPENSIONI.TABLE_NAME+
		" SET " + SOSPENSIONI.ID_STATO + " = ?,"
		+ SOSPENSIONI.DATA_FINE_SOSP + " = " + buildGetDate()		
		+ " WHERE " + SOSPENSIONI.ID_SOSPENSIONE + " = ?"
		+ " AND " + SOSPENSIONI.DATA_INIZIO_SOSP + " = ?";
	
	/**
	 * metodo per l'aggiornameto di un record
	 * 
	 * @param idSospensioni long
	 * @param dataInizioSospensioni Timestamp
	 * @param statoScheda String
	 * @return int - affected row cout
	 * @throws SQLException
	 */
	public int updateRecord(long idSospensioni, Timestamp dataInizioSospensioni, String statoScheda ) throws SQLException{
		int numRow=-1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);
			stmt.setString(1, statoScheda);
			stmt.setLong(2, idSospensioni);
			stmt.setTimestamp(3,dataInizioSospensioni);
			logger.debug(3 + ": "+dataInizioSospensioni);
			numRow = stmt.executeUpdate();
		
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}
	/**
	 * Il metodo restituisce in una Map le possibili Motivazioni di Sospensione presenti nel DB
	 * nella tabella MOTIVI_SOSPENSIONE. Nella MAP viene inserito l'ID della motivazione e la sua descrizione.
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validita' di una tipologia a posteriori
	 * @return Map&lt;String,String&gt;
	 * @throws SQLException
	 */
	public Map<String,String> loadMotiviSospensione(Object o) throws SQLException{
		return getTipologica(MOTIVI_SOSPENSIONE.TABLE_NAME, MOTIVI_SOSPENSIONE.ID_MOTIVO_SOSP, MOTIVI_SOSPENSIONE.DESCRIZIONE, MOTIVI_SOSPENSIONE.DATA_FINE_VALIDITA,o);		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(long, java.sql.Timestamp, java.lang.String)
	 */
	public boolean annulla(long idScheda, Timestamp dataInizioScheda, String cfUtente) throws SQLException {
		return _annulla(idScheda, dataInizioScheda,cfUtente);
		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(long, java.lang.String)
	 */
	public boolean annulla(long idSimog, String cfUtente) throws SQLException {
		SospensioniBean sospensioneBean = loadByIdSimog(idSimog);
		
		if(sospensioneBean.getIdSospensione() > 0){
			return _annulla(sospensioneBean.getIdSospensione(), sospensioneBean.getDataInizioSosp(),cfUtente);
		}
		return false;
		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean annulla(String idLocale, String rifSimog, String cfUtente) throws SQLException {
		SospensioniBean sospensioneBean = loadByIdLocale(idLocale, rifSimog);
		
		if(sospensioneBean.getIdSospensione() > 0){
			return _annulla(sospensioneBean.getIdSospensione(), sospensioneBean.getDataInizioSosp(),cfUtente);
		}
		return false;
		
	}
	/**
	 * @param idSospensioni
	 * @param dataInizioSospensioni
	 * @throws SQLException
	 */
	private boolean _annulla(long idSospensioni, Timestamp dataInizioSospensioni, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_SOSPENSIONI);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idSospensioni);
			stmt.setTimestamp(index++, dataInizioSospensioni);
			someRowAffected = stmt.executeUpdate() > 0;
			if(someRowAffected){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idSospensioni);
				attributiChiave.add(dataInizioSospensioni);
					LogBloccoDatiManager.loggingDELETE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_SOSPENSIONE, attributiChiave);
			}
			return someRowAffected;
		}finally {
			close(null,stmt);
		}		
	}
	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadSospensioni#fillBean(java.sql.ResultSet, it.avlp.simog.beans.sospensioni.SospensioniBean)
	 */
	public void fillBean(ResultSet rs, SospensioniBean bean) throws SQLException {
		bean.setDataInizioSosp(rs.getTimestamp(SOSPENSIONI.DATA_INIZIO_SOSP));
		bean.setDataFineSosp(rs.getTimestamp(SOSPENSIONI.DATA_FINE_SOSP));
		bean.setDataInizioAggiudicazione(rs.getTimestamp(SOSPENSIONI.DATA_INIZIO_AGGIUDICAZIONE));
		bean.setDataVerbRipr(PageHelper.getViewDate( rs.getString(SOSPENSIONI.DATA_VERB_RIPR)));
		bean.setDataVerbSosp(PageHelper.getViewDate(rs.getString(SOSPENSIONI.DATA_VERB_SOSP)));
		bean.setFlagRiserve(rs.getString(SOSPENSIONI.FLAG_RISERVE));
		bean.setFlagSuperoTemp(rs.getString(SOSPENSIONI.FLAG_SUPERO_TEMP));
		bean.setFlagVerbale(rs.getString(SOSPENSIONI.FLAG_VERBALE));
		bean.setIdAggiudicazione(rs.getLong(SOSPENSIONI.ID_AGGIUDICAZIONE));
		bean.setIdMotivoSosp(rs.getLong(SOSPENSIONI.ID_MOTIVO_SOSP));
		bean.setIdSospensione(rs.getLong(SOSPENSIONI.ID_SOSPENSIONE));
		bean.setIdStato(rs.getLong(SOSPENSIONI.ID_STATO));
		bean.setDescrizioneStato(rs.getString(STATI_SCHEDA.DESCRIZIONE));
		bean.setDescrizioneMotivo(rs.getString(MOTIVI_SOSPENSIONE.TABLE_NAME + MOTIVI_SOSPENSIONE.DESCRIZIONE));
		bean.setIdLocale(rs.getString(SOSPENSIONI.ID_SCHEDA_LOCALE));
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadSospensioni#loadByIdLocale(java.lang.String, java.lang.String)
	 */
	public SospensioniBean loadByIdLocale(String idLocale,String idAggiudicazione) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		SospensioniBean bean = null;
		try{
			stmt = activeConnection.prepareStatement(SELECT_ONE_SOSPENSIONI + WHERE_IDLOCALE);	
			stmt.setString(index++, idLocale);
			long idAggiudicazioneL = Long.parseLong(idAggiudicazione);
			stmt.setLong(index++, idAggiudicazioneL);			
			rs = stmt.executeQuery();
			bean = new SospensioniBean();
			while(rs.next()){				
				fillBean(rs, bean);
			}
			return bean;
		}finally{
			close(rs,stmt);
		}
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadSospensioni#loadByIdSimog(long)
	 */
	public SospensioniBean loadByIdSimog(long idSimog) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		SospensioniBean bean = null;
		try{
			stmt = activeConnection.prepareStatement(SELECT_ONE_SOSPENSIONI + WHERE_IDSIMOG);			
			stmt.setLong(index++, idSimog);
			rs = stmt.executeQuery();
			bean = new SospensioniBean();
			while(rs.next()){				
				fillBean(rs, bean);
			}
			return bean;
		}finally{
			close(rs,stmt);
		}
	}

//	public boolean annullaByAggiudicazione(long idAggiudicazione,Timestamp dataInizioAggiudicazione, String cfUtente)throws SQLException {
//		List<SospensioniBean> listOfSospensioni = this.loadMany(idAggiudicazione, dataInizioAggiudicazione);
//		boolean esitoOperazione = listOfSospensioni.size() > 0 ? true : false;
//		for(SospensioniBean sospensioneCorrente: listOfSospensioni){
//			esitoOperazione = esitoOperazione && _annulla(sospensioneCorrente.getIdSospensione(), sospensioneCorrente.getDataInizioSosp(), cfUtente);
//		}return esitoOperazione;
//	}
	
	
	
	
	
	
}
