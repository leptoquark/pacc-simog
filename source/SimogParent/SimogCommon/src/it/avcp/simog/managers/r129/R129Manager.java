package it.avcp.simog.managers.r129;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamento;
import it.avcp.simog.managers.comportamento.caricamento.ILoadRitardo;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.common.servlet.ParametriServletR129;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.INIZIO_LAVORI;
import it.avlp.simog.db.generated.R129;
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

import org.apache.log4j.Logger;

/**
 * Classe per la gestione dei dati relativi ai ritardi
 *
 */
public class R129Manager extends AccessiDB implements IAnnullamento,ILoadRitardo{
	public static String CLAZZ = "R129Manager";

	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public R129Manager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	
	}
	
	private final String INSERT_R129 = "INSERT INTO " + R129.TABLE_NAME + "("
    + R129.DATA_INIZIO
    + ", " + R129.DATA_FINE
    + ", " + R129.ID_STATO
    + ", " + R129.ID_AGGIUDICAZIONE
    + ", " + R129.DATA_INIZIO_AGGIUDICAZIONE
    + ", " + R129.DATA_COMUNIC
    + ", " + R129.DATA_TERMINE
    + ", " + R129.TIPO_COMUN
    + ", " + R129.DURATA_SOSP
    + ", " + R129.MOTIVO_SOSP
    + ", " + R129.DATA_IST_RECESSO
    + ", " + R129.FLAG_ACCOLTA
    + ", " + R129.FLAG_TARDIVA
    + ", " + R129.FLAG_RIPRESA
    + ", " + R129.FLAG_RISERVA
    + ", " + R129.IMPORTO_SPESE
    + ", " + R129.IMPORTO_ONERI
    + ", " + R129.DATA_CONSEGNA_LAVORI
    + ", " + R129.ID_SCHEDA_LOCALE
    + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	
	/**
	 * metodo per l'inserimento di un ritardo
	 * 
	 * @param bean R129Bean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public void insert(R129Bean bean, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try {
			logger.debug("invocato insert R129");
			stmt = activeConnection.prepareStatement(createInsertQuery(INSERT_R129, R129.ID_RECORD));
			bean.setDataInizioRecord(getNow()); //data inizio record, data db
			stmt.setTimestamp(index++, bean.getDataInizioRecord());
			stmt.setNull(index++, Types.TIMESTAMP);//data fine record
			bean.setIdStato(StatiScheda.IN_DEFINIZIONE);
			stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
			stmt.setLong(index++, bean.getIdAggiudicazione());
			stmt.setTimestamp(index++, bean.getDataInizioAggiudicazione());
			
			bean.setDataComunicazione(PageHelper.getDBDateFromTS(getNow()));
			stmt.setString(index++, bean.getDataComunicazione());
			
			stmt.setString(index++,PageHelper.formatDateOrNull( bean.getDataTermine()));
			stmt.setString(index++, bean.getTipoComunicazione());
			stmt.setInt(index++, bean.getDurataSospensione());
			stmt.setString(index++, bean.getMotivoSospensione());
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataIstRecesso()));
			stmt.setString(index++, bean.getFlagAccolta());
			stmt.setString(index++, bean.getFlagTardiva());
			stmt.setString(index++, bean.getFlagRipresa());
			stmt.setString(index++, bean.getFlagRiserva());
			stmt.setBigDecimal(index++, bean.getImportoSpese());
			stmt.setBigDecimal(index++, bean.getImportoOneri());
			stmt.setString(index++,PageHelper.formatDateOrNull( bean.getDataConsegna()));
			
			if(bean.getIdLocale() == null){
				stmt.setNull(index++, Types.VARCHAR);
			}else{
				stmt.setString(index++,bean.getIdLocale());
			}
			
			if(stmt.execute()){
				rs = stmt.getResultSet();
				rs.next();
				bean.setIdRecord(rs.getLong(R129.ID_RECORD));
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(bean.getIdRecord());
				attributiChiave.add(bean.getDataInizioRecord());
				LogBloccoDatiManager.loggingINSERT(activeConnection, logger, cfUtente, ParametriServletR129.TAB_SCHEDA_R129, attributiChiave);
			}
			
		} finally {
			close(rs, stmt);
		}
	}
	
	public static String UPDATE_R129 = "UPDATE " + R129.TABLE_NAME + " SET "
	+ R129.DATA_FINE + " =?"
    + ", " + R129.ID_STATO + " =?"
   
    
    + ", " + R129.DATA_TERMINE + " =?"
    + ", " + R129.TIPO_COMUN + " =?"
    + ", " + R129.DURATA_SOSP + " =?"
    + ", " + R129.MOTIVO_SOSP + " =?" 
    + ", " + R129.DATA_IST_RECESSO + " =?"
    + ", " + R129.FLAG_ACCOLTA + " =?"
    + ", " + R129.FLAG_TARDIVA + " =?"
    + ", " + R129.FLAG_RIPRESA + " =?"
    + ", " + R129.FLAG_RISERVA + " =?"
    + ", " + R129.IMPORTO_SPESE + " =?"
    + ", " + R129.IMPORTO_ONERI + " =? " 
    + ", " + R129.DATA_CONSEGNA_LAVORI + " =? " 
    + " WHERE " + R129.ID_RECORD + " = ? AND "
    + R129.DATA_INIZIO + " = ?  ";
//    + R129.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING
 // PP fix controllo stato scheda + " OR " + R129.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING
//    + " )";
	
    private final String WHERE_CONF = " AND (" + R129.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING
          + " OR " + R129.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING  + " ) ";
     private final String WHERE_DEF = " AND " + R129.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING;
	
	private int update(R129Bean bean, String cfUtente, boolean confirm)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try {
			logger.debug("invocato update R129");

			stmt = activeConnection.prepareStatement(UPDATE_R129 + (SimogFlags.isFlagNoDate() ? WHERE_CONF : WHERE_DEF));
			
			if(confirm){
				stmt.setTimestamp(index++, getNow()); // data fine 
				stmt.setLong(index++, StatiScheda.CONFERMATO);
			}else{
				//un aggiornamento normale
				stmt.setNull(index++, Types.TIMESTAMP);//data fine record
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
			}
			
		
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataTermine()));
			stmt.setString(index++, bean.getTipoComunicazione());
			stmt.setInt(index++, bean.getDurataSospensione());
			stmt.setString(index++, bean.getMotivoSospensione());
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataIstRecesso()));
			stmt.setString(index++, bean.getFlagAccolta());
			stmt.setString(index++, bean.getFlagTardiva());
			stmt.setString(index++, bean.getFlagRipresa());
			stmt.setString(index++, bean.getFlagRiserva());
			stmt.setBigDecimal(index++, bean.getImportoSpese());
			stmt.setBigDecimal(index++, bean.getImportoOneri());
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataConsegna()));			
			stmt.setLong(index++, bean.getIdRecord());
			stmt.setTimestamp(index++, bean.getDataInizioRecord());
			int num = stmt.executeUpdate();
			
			List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(bean.getIdRecord());
			attributiChiave.add(bean.getDataInizioRecord());
			if(confirm)
				LogBloccoDatiManager.loggingCONFIRM(activeConnection, logger, cfUtente, ParametriServletR129.TAB_SCHEDA_R129, attributiChiave);
			else 
				LogBloccoDatiManager.loggingUPDATE(activeConnection, logger, cfUtente, ParametriServletR129.TAB_SCHEDA_R129, attributiChiave);
			return num;
		} finally {
			close(rs, stmt);
		}
	}
	
	/**
	 * metodo per il salvataggio di un ritardo
	 * 
	 * @param bean R129Bean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public int save(R129Bean bean, String cfUtente)throws SQLException{
		return update(bean,cfUtente, false);
	}
	
	/**
	 * metodo per la conferma di un ritardo
	 * 
	 * @param bean R129Bean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public int confirm(R129Bean bean, String cfUtente)throws SQLException{
		return update(bean,cfUtente, true);
	}
	
	
	public final String SELECT_ONE_R129 = "SELECT "
		+ R129.ID_RECORD
		+ ", " + R129.DATA_INIZIO
	    + ", " + R129.DATA_FINE
	   
	    + ", " + R129.ID_AGGIUDICAZIONE
	    + ", " + R129.DATA_INIZIO_AGGIUDICAZIONE
	    + ", " + R129.DATA_COMUNIC
	    + ", " + R129.DATA_TERMINE
	    + ", " + R129.TIPO_COMUN
	    + ", " + R129.DURATA_SOSP
	    + ", " + R129.MOTIVO_SOSP
	    + ", " + R129.DATA_IST_RECESSO
	    + ", " + R129.FLAG_ACCOLTA
	    + ", " + R129.FLAG_TARDIVA
	    + ", " + R129.FLAG_RIPRESA
	    + ", " + R129.FLAG_RISERVA
	    + ", " + R129.IMPORTO_SPESE
	    + ", " + R129.IMPORTO_ONERI
	    + ", " + R129.DATA_CONSEGNA_LAVORI
	     + "," +R129.T_ID_STATO + ", " + STATI_SCHEDA.DESCRIZIONE + " + " +
		   buildRichAnnQuery(ParametriServletR129.TAB_SCHEDA_R129, R129.T_ID_RECORD,null) 
		+" AS "+STATI_SCHEDA.DESCRIZIONE
		+", " + R129.ID_SCHEDA_LOCALE
		 + " FROM " + R129.TABLE_NAME + ", " + STATI_SCHEDA.TABLE_NAME;
//		    + " WHERE " 
//		    + R129.T_ID_RECORD + " = ? AND "
//		    + R129.T_DATA_INIZIO + " = ?  AND "
//		    + R129.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
//		    +" AND (" + R129.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
//			+" OR " + R129.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")"; 
	
	
	/**
	 * metodo per il caricamento di un ritardo
	 * 
	 * @param idRecord long
	 * @param dataInizioRecord  Timestamp
	 * @return R129Bean
	 * @throws SQLException
	 */
	public R129Bean loadOne(long idRecord, Timestamp dataInizioRecord)throws SQLException{
		List<R129Bean> lista = load(idRecord, dataInizioRecord, false);
		if(lista!= null && lista.size() > 0)
			return lista.get(0);
		else return null;
		
	}
	
	public  final String SELECT_MANY_R129 = "SELECT "
		+ R129.ID_RECORD
		+ ", " + R129.DATA_INIZIO
	    + ", " + R129.DATA_FINE
	   
	    + ", " + R129.ID_AGGIUDICAZIONE
	    + ", " + R129.DATA_INIZIO_AGGIUDICAZIONE
	    + ", " + R129.DATA_COMUNIC
	    + ", " + R129.DATA_TERMINE
	    + ", " + R129.TIPO_COMUN
	    + ", " + R129.DURATA_SOSP
	    + ", " + R129.MOTIVO_SOSP
	    + ", " + R129.DATA_IST_RECESSO
	    + ", " + R129.FLAG_ACCOLTA
	    + ", " + R129.FLAG_TARDIVA
	    + ", " + R129.FLAG_RIPRESA
	    + ", " + R129.FLAG_RISERVA
	    + ", " + R129.IMPORTO_SPESE
	    + ", " + R129.IMPORTO_ONERI
	    + ", " + R129.DATA_CONSEGNA_LAVORI	    
	    + "," +R129.T_ID_STATO + ", " + STATI_SCHEDA.DESCRIZIONE + " + " +
		   buildRichAnnQuery(ParametriServletR129.TAB_SCHEDA_R129, R129.T_ID_RECORD,null) 
		+" AS "+STATI_SCHEDA.DESCRIZIONE
		+ ", " + R129.ID_SCHEDA_LOCALE + " "
	    + " FROM " + R129.TABLE_NAME + ", " + STATI_SCHEDA.TABLE_NAME
	    + " WHERE " + R129.T_ID_AGGIUDICAZIONE + " = ? AND "
	    + R129.T_DATA_INIZIO_AGGIUDICAZIONE + " = ?  AND "
	    + R129.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
	    +" AND (" + R129.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + R129.T_ID_STATO + "=" + StatiScheda.CONFERMATO+") order by " + R129.DATA_COMUNIC; 
	
	/**
	 * metodo per il recupero di una lista di ritardi associati ad una aggiudicazione
	 * 
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @return List&lt;R129Bean&gt;
	 * @throws SQLException
	 */
	public List<R129Bean> loadMany(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
		return load(idAggiudicazione, dataInizioAggiudicazione, true);
	}
	
	
	private List<R129Bean> load(long id, Timestamp date, boolean byAggiudicazione) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		//TableBean result = null;
		ArrayList<R129Bean> listaSchede = new ArrayList<R129Bean>();
		R129Bean bean = null;
		try{
			if(byAggiudicazione)
				stmt = activeConnection.prepareStatement(SELECT_MANY_R129);
			else
				stmt = activeConnection.prepareStatement(SELECT_ONE_R129 + WHERE_STANDARD);
			
			stmt.setLong(index++, id);
			stmt.setTimestamp(index++, date);
			rs = stmt.executeQuery();
			while(rs.next()){
				bean = new R129Bean();
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
	 * @param idRecord long
	 * @param dataInizioRecord Timestamp
	 * @return nuova data inizio Timestamp
	 * @throws SQLException
	 */
	public Timestamp copyRecord(long idRecord, Timestamp dataInizioRecord) throws SQLException{
		String QUERY_SELECT_DATA_FINE = "SELECT " + R129.DATA_FINE
		+ " FROM " + R129.TABLE_NAME
		+ " WHERE " + R129.ID_RECORD + " = ? "
		+ " AND " + R129.DATA_INIZIO + " = ?"
		+ " AND " + R129.ID_STATO + " = " + StatiScheda.CONFERMATO;
		
		String UPDATE_STATO_OLD_R129 = 
			"UPDATE " + R129.TABLE_NAME + " SET "
			+ R129.ID_STATO + " = ? "
			+ ", " + R129.DATA_INIZIO + " = ?"
			+ ", " + R129.DATA_FINE + " = ?"
			+ " WHERE " + R129.ID_RECORD + " = ? "
			+ " AND " + R129.DATA_INIZIO + " = ?"
			+ " AND " + R129.ID_STATO + " = " + StatiScheda.CONFERMATO;
		
		String COPY_RECORD = " INSERT INTO " + R129.TABLE_NAME + "("
			+ R129.ID_RECORD	
		    + ", " + R129.ID_AGGIUDICAZIONE
		    + ", " + R129.DATA_INIZIO_AGGIUDICAZIONE
		    + ", " + R129.DATA_COMUNIC
		    + ", " + R129.DATA_TERMINE
		    + ", " + R129.DATA_CONSEGNA_LAVORI
		    + ", " + R129.TIPO_COMUN
		    + ", " + R129.DURATA_SOSP
		    + ", " + R129.MOTIVO_SOSP
		    + ", " + R129.DATA_IST_RECESSO
		    + ", " + R129.FLAG_ACCOLTA
		    + ", " + R129.FLAG_TARDIVA
		    + ", " + R129.FLAG_RIPRESA
		    + ", " + R129.FLAG_RISERVA
		    + ", " + R129.IMPORTO_SPESE
		    + ", " + R129.IMPORTO_ONERI
		    + ", " + R129.ID_SCHEDA_LOCALE
		    
		    + " ," + R129.DATA_INIZIO
		    + ", " + R129.DATA_FINE
		    + ", " + R129.ID_STATO 
		    + ") SELECT "
		    + R129.ID_RECORD	
		    + ", " + R129.ID_AGGIUDICAZIONE
		    + ", " + R129.DATA_INIZIO_AGGIUDICAZIONE
		    + ", " + R129.DATA_COMUNIC
		    + ", " + R129.DATA_TERMINE
		    + ", " + R129.DATA_CONSEGNA_LAVORI
		    + ", " + R129.TIPO_COMUN
		    + ", " + R129.DURATA_SOSP
		    + ", " + R129.MOTIVO_SOSP
		    + ", " + R129.DATA_IST_RECESSO
		    + ", " + R129.FLAG_ACCOLTA
		    + ", " + R129.FLAG_TARDIVA
		    + ", " + R129.FLAG_RIPRESA
		    + ", " + R129.FLAG_RISERVA
		    + ", " + R129.IMPORTO_SPESE
		    + ", " + R129.IMPORTO_ONERI
		    + ", " + R129.ID_SCHEDA_LOCALE
		    + ", ?"
			+ ", ?"
			+ ", ?"
			+ " FROM " + R129.TABLE_NAME
			+ " WHERE " + R129.ID_RECORD + " = ? "
			+ " AND " + R129.DATA_INIZIO + " = ?";


		Timestamp dataFine = null;
		Timestamp nuovaDataRecord = null;
		int index = 1;
		ResultSet rs = null;
		PreparedStatement getDataFine = null;
		PreparedStatement updateRecord = null;
		PreparedStatement copyRecord = null;
		try{
			//prendo la data fine del record
			getDataFine = activeConnection.prepareStatement(QUERY_SELECT_DATA_FINE);
			getDataFine.setLong(index++, idRecord);
			getDataFine.setTimestamp(index++, dataInizioRecord);
			rs = getDataFine.executeQuery();
			if(rs.next()){
				dataFine = rs.getTimestamp(R129.DATA_FINE);
				
				//il record corrente diventa il nuovo record
				index = 1;
				nuovaDataRecord = getNow();
				updateRecord = activeConnection.prepareStatement(UPDATE_STATO_OLD_R129);
				updateRecord.setInt(index++, StatiScheda.IN_DEFINIZIONE);
				updateRecord.setTimestamp(index++, nuovaDataRecord);
				updateRecord.setNull(index++, Types.TIMESTAMP);
				updateRecord.setLong(index++, idRecord);
				updateRecord.setTimestamp(index++, dataInizioRecord);
				updateRecord.execute();
				
				//copy record
				index = 1;
				copyRecord = activeConnection.prepareStatement(createCopyRecord(COPY_RECORD,R129.TABLE_NAME));
				copyRecord.setTimestamp(index++, dataInizioRecord);
				copyRecord.setTimestamp(index++, dataFine);
				copyRecord.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA);
				copyRecord.setLong(index++, idRecord);
				copyRecord.setTimestamp(index++, nuovaDataRecord);
				copyRecord.execute();
				
				return nuovaDataRecord;
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
	 * metodo per il controllo dell'esistenza di un ritardo
	 * 
	 * @param idRecord long
	 * @param dataInizioRecord Timestamp
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean existR129(long idRecord, Timestamp dataInizioRecord) throws SQLException{

		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT 1 FROM " + R129.TABLE_NAME + " WHERE " + 
		R129.ID_RECORD + " = ? AND " + 
		R129.DATA_INIZIO + " = ?";
		
		try{
			stmt = activeConnection.prepareStatement(query);
			stmt.setLong(1, idRecord);
			stmt.setTimestamp(2, dataInizioRecord);
			rs = stmt.executeQuery();
			return (rs.next());
		}finally{
			close(rs, stmt);
		}
	}
	

	
	
	private String DELETE_RECORD_R129 = 
		"DELETE FROM " + R129.TABLE_NAME
		+ " WHERE " + R129.ID_RECORD + " = ?"
		+ " AND " + R129.DATA_INIZIO + " = ?";
	
	/**
	 * metodo per la cancellazione di un record
	 * 
	 * @param idRecord long
	 * @param dataInizioRecord Timestamp
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int deleteRecord(long idRecord, Timestamp dataInizioRecord)throws SQLException{
		PreparedStatement stmt = null;
		int index = 1;
		try{
			
			stmt = activeConnection.prepareStatement(DELETE_RECORD_R129);
			stmt.setLong(index++, idRecord);
			stmt.setTimestamp(index++, dataInizioRecord);
			return stmt.executeUpdate();
		}finally{
			close(null,stmt);
		}
	}
	
	
	private static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO = 
		"UPDATE "+R129.TABLE_NAME+
		" SET " + R129.ID_STATO + " = ?,"
		+ R129.DATA_FINE + " = " + buildGetDate()		
		+ " WHERE " + R129.ID_RECORD + " = ?"
		+ " AND " + R129.DATA_INIZIO + " = ?";
	
	/**
	 * metodo per l'aggiornamento di un record
	 * 
	 * @param idRecord long
	 * @param dataInizioRecord Timestamp
	 * @param statoScheda String
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int updateRecord(long idRecord, Timestamp dataInizioRecord, String statoScheda ) throws SQLException{
		int numRow=-1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);
			stmt.setString(1, statoScheda);
			stmt.setLong(2, idRecord);
			stmt.setTimestamp(3,dataInizioRecord);
			numRow = stmt.executeUpdate();
		
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(long, java.sql.Timestamp, java.lang.String)
	 */
	public boolean annulla(long idR129, Timestamp dataInizioR129, String cfUtente) throws SQLException {
		return _annulla(idR129, dataInizioR129,cfUtente);
		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(long, java.lang.String)
	 */
	public boolean annulla(long idSimog, String cfUtente) throws SQLException {
		R129Bean r129Bean = loadByIdSimog(idSimog);
		
		if(r129Bean.getIdRecord() > 0){
			return _annulla(r129Bean.getIdRecord(), r129Bean.getDataInizioRecord(),cfUtente);
		}
		return false;
		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean annulla(String idLocale, String rifSimog, String cfUtente) throws SQLException {
		R129Bean r129Bean = loadByIdLocale(idLocale, rifSimog);
		
		if(r129Bean.getIdRecord() > 0){
			return _annulla(r129Bean.getIdRecord(), r129Bean.getDataInizioRecord(),cfUtente);
		}
		return false;
		
	}
	
	/**
	 * @param idR129
	 * @param dataInizioR129
	 * @throws SQLException
	 */
	private boolean _annulla(long idR129, Timestamp dataInizioR129, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_R129);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idR129);
			stmt.setTimestamp(index++, dataInizioR129);
			someRowAffected = stmt.executeUpdate() > 0;
			if(someRowAffected){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idR129);
				attributiChiave.add(dataInizioR129);
					LogBloccoDatiManager.loggingDELETE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_RITARDO, attributiChiave);
			}
			return someRowAffected;
		}finally {
			close(null,stmt);
		}		
	}
	
	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadRitardo#fillBean(java.sql.ResultSet, it.avlp.simog.beans.r129.R129Bean)
	 */
	public void fillBean(ResultSet rs, R129Bean bean) throws SQLException {
		bean.setDataComunicazione(PageHelper.getViewDate(rs.getString(R129.DATA_COMUNIC)));
		bean.setDataInizioAggiudicazione(rs.getTimestamp(R129.DATA_INIZIO_AGGIUDICAZIONE));
		bean.setDataInizioRecord(rs.getTimestamp(R129.DATA_INIZIO));
		bean.setDataIstRecesso(PageHelper.getViewDate(rs.getString(R129.DATA_IST_RECESSO)));
		bean.setDataTermine(PageHelper.getViewDate(rs.getString(R129.DATA_TERMINE)));
		bean.setDurataSospensione(rs.getInt(R129.DURATA_SOSP));
		bean.setFlagAccolta(rs.getString(R129.FLAG_ACCOLTA));
		bean.setFlagRipresa(rs.getString(R129.FLAG_RIPRESA));
		bean.setFlagRiserva(rs.getString(R129.FLAG_RISERVA));
		bean.setFlagTardiva(rs.getString(R129.FLAG_TARDIVA));
		bean.setIdAggiudicazione(rs.getLong(R129.ID_AGGIUDICAZIONE));
		bean.setIdRecord(rs.getLong(R129.ID_RECORD));
		bean.setIdStato(rs.getLong(R129.ID_STATO));
		bean.setImportoOneri(rs.getBigDecimal(R129.IMPORTO_ONERI));
		bean.setImportoSpese(rs.getBigDecimal(R129.IMPORTO_SPESE));
		bean.setMotivoSospensione(rs.getString(R129.MOTIVO_SOSP));
		bean.setTipoComunicazione(rs.getString(R129.TIPO_COMUN));
		bean.setDataConsegna(PageHelper.getViewDate(rs.getString(R129.DATA_CONSEGNA_LAVORI)));				
		bean.setDescrizioneStato(rs.getString(STATI_SCHEDA.DESCRIZIONE));
		bean.setIdLocale(rs.getString(R129.ID_SCHEDA_LOCALE));
		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadRitardo#loadByIdLocale(java.lang.String, java.lang.String)
	 */
	public R129Bean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		R129Bean bean = null;
		try{
			stmt = activeConnection.prepareStatement(SELECT_ONE_R129 + WHERE_IDLOCALE);			
			stmt.setString(index++, idLocale);
			long idAggiudicazioneL = Long.parseLong(idAggiudicazione);
			stmt.setLong(index++, idAggiudicazioneL);
			rs = stmt.executeQuery();
			bean = new R129Bean();
			while(rs.next()){			
				fillBean(rs, bean);
			}
			return bean;
		}finally{
			close(rs,stmt);
		}
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadRitardo#loadByIdSimog(long)
	 */
	public R129Bean loadByIdSimog(long idSimog) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		R129Bean bean = null;
		try{
			stmt = activeConnection.prepareStatement(SELECT_ONE_R129 + WHERE_IDSIMOG);			
			stmt.setLong(index++, idSimog);
			rs = stmt.executeQuery();
			bean = new R129Bean();
			while(rs.next()){			
				fillBean(rs, bean);
			}
			return bean;
		}finally{
			close(rs,stmt);
		}
	}

//	public boolean annullaByAggiudicazione(long idAggiudicazione,Timestamp dataInizioAggiudicazione, String cfUtente)throws SQLException {
//		List<R129Bean> listOfR129 = this.loadMany(idAggiudicazione, dataInizioAggiudicazione);
//		boolean esitOperazione = listOfR129.size() > 0 ? true : false;
//		for(R129Bean recessoCorrente : listOfR129){
//			esitOperazione = esitOperazione && _annulla(recessoCorrente.getIdRecord(), recessoCorrente.getDataInizioRecord(), cfUtente);
//		}return esitOperazione;
//	}

}
