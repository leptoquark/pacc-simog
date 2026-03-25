package it.avcp.simog.managers.conclusione;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamento;
import it.avcp.simog.managers.comportamento.caricamento.ILoadConclusione;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.common.servlet.ParametriServletConclusioni;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.FINE_LAVORI;
import it.avlp.simog.db.generated.MOTIVI_INTERRUZIONE;
import it.avlp.simog.db.generated.MOTIVI_RISOLUZIONE;
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
 * Classe per la gestione dei dati dele conclusioni
 *
 */
public class ConclusioniManager extends AccessiDB implements IAnnullamento,ILoadConclusione{
	
	public static String CLAZZ = "ConclusioniManager";
	
	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public ConclusioniManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	private final String QUERY_SELECT_CONCLUSIONI =
		"SELECT " +
		FINE_LAVORI.TABLE_NAME + ".* ," +
		STATI_SCHEDA.T_DESCRIZIONE+ " + " +  buildRichAnnQuery(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI, FINE_LAVORI.T_ID_ULTIM,null) 
		+" AS "+STATI_SCHEDA.DESCRIZIONE+	
		" FROM " + 
		FINE_LAVORI.TABLE_NAME + ", " +
		STATI_SCHEDA.TABLE_NAME;




	
	/**
	 * metodo per il caricamento di una conclusione associata all'aggiudicazione di cui id
	 * 
	 * @param idAggiudicazione Long
	 * @param dataIniAggiudicazione Timestamp
	 * @return ConclusioneBean
	 * @throws SQLException
	 */
	public ConclusioneBean load(Long idAggiudicazione,Timestamp dataIniAggiudicazione)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ConclusioneBean cb = null;
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_CONCLUSIONI + WHERE_STANDARD);
			int index = 1;
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataIniAggiudicazione);
			rs = stmt.executeQuery();
			while(rs.next()){
				cb = new ConclusioneBean();
				fillBean(rs, cb);
			}
			
			return cb;
			
		}finally{
			close(rs,stmt);
		}
		
	}
	
	private final String INSERT_CONCLUSIONI = "INSERT INTO " + FINE_LAVORI.TABLE_NAME + " ("
	+ FINE_LAVORI.ID_MOTIVO_RISOL
	+ ", " + FINE_LAVORI.ID_MOTIVO_INTERR
	+ ", " + FINE_LAVORI.ID_AGGIUDICAZIONE
	+ ", " + FINE_LAVORI.DATA_INIZIO_AGGIUDICAZIONE
	+ ", " + FINE_LAVORI.ID_STATO
	+ ", " + FINE_LAVORI.DATA_INIZIO_ULTIM
	+ ", " + FINE_LAVORI.DATA_FINE_ULTIM
	+ ", " + FINE_LAVORI.DATA_RISOLUZIONE
	+ ", " + FINE_LAVORI.FLAG_ONERI
	+ ", " + FINE_LAVORI.ONERI_RISOLUZIONE
	+ ", " + FINE_LAVORI.FLAG_POLIZZA
	+ ", " + FINE_LAVORI.DATA_ULTIMAZIONE
	+ ", " + FINE_LAVORI.NUM_INFORTUNI
	+ ", " + FINE_LAVORI.NUM_INF_PERM
	+ ", " + FINE_LAVORI.NUM_INF_MORT
	+ ", " + FINE_LAVORI.ID_SCHEDA_LOCALE
	//gm nuovo codice 3.0
	+ ", " + FINE_LAVORI.DATA_CONSEGNA
	+ ", " + FINE_LAVORI.GIORNI_PROROGA
	+ ", " + FINE_LAVORI.TERMINE_ULTIMAZIONE
	//gm fine nuovo codice 3.0
	+ ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * metodo per l'inserimento di una conclusione
	 * 
	 * @param bean ConclusioneBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public void insert(ConclusioneBean bean , String cfUtente)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try{
			stmt = activeConnection.prepareStatement(createInsertQuery(INSERT_CONCLUSIONI, FINE_LAVORI.ID_ULTIM));
			stmt.setLong(index++, bean.getMotiviRisoluzione());
			stmt.setLong(index++, bean.getMotiviInterruzione());
			stmt.setLong(index++, bean.getIdAggiudicazione());
			stmt.setTimestamp(index++, bean.getDataInizioAggiudicazione());
			bean.setIdStato(StatiScheda.IN_DEFINIZIONE);
			stmt.setLong(index++, bean.getIdStato());
			bean.setDataIniUltim(getNow());
			stmt.setTimestamp(index++, bean.getDataIniUltim());
			stmt.setNull(index++, Types.TIMESTAMP);
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataRisoluzione()));
			stmt.setString(index++, bean.getFlagOneri());
			stmt.setBigDecimal(index++, bean.getOneriRisoluzione());
			stmt.setString(index++, bean.getFlagPolizza());
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataUltimazione()));
			stmt.setLong(index++, bean.getNumInfortuni());
			stmt.setLong(index++, bean.getNumInfPerm());
			stmt.setLong(index++, bean.getNumInfMort());
			if(bean.getIdLocale() == null){
				stmt.setNull(index++, Types.VARCHAR);
			}else{
				stmt.setString(index++, bean.getIdLocale());
			}
			//gm nuovo codice 3.0
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataConsegna()));
			stmt.setLong(index++, bean.getGiorniProroga());
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getTermineUltimazione()));
			//gm fine nuovo codice 3.0
			
			if(stmt.execute()){
				rs = stmt.getResultSet();
				rs.next();
				bean.setIdUltim(rs.getLong(FINE_LAVORI.ID_ULTIM));
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(bean.getIdUltim());
				attributiChiave.add(bean.getDataIniUltim());
				LogBloccoDatiManager.loggingINSERT(activeConnection, logger, cfUtente, ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI , attributiChiave);
		}
	}finally{
		close(rs,stmt);
		}
	
	}
	
	public final String UPDATE_CONCLUSIONI = "UPDATE " + FINE_LAVORI.TABLE_NAME + " SET "
	+ FINE_LAVORI.DATA_FINE_ULTIM + " =? "
	+ ", " + FINE_LAVORI.ID_STATO + " =? "
	+ ", " + FINE_LAVORI.DATA_ULTIMAZIONE + " =? "
	+ ", " + FINE_LAVORI.FLAG_ONERI + " =? "
	+ ", " + FINE_LAVORI.FLAG_POLIZZA + " =? "
	+ ", " + FINE_LAVORI.ID_MOTIVO_INTERR + " =? "
	+ ", " + FINE_LAVORI.ID_MOTIVO_RISOL + " =? "
	+ ", " + FINE_LAVORI.DATA_RISOLUZIONE + " =? "
	+ ", " + FINE_LAVORI.NUM_INF_MORT + " =? "
	+ ", " + FINE_LAVORI.NUM_INF_PERM + " =? "
	+ ", " + FINE_LAVORI.NUM_INFORTUNI + " =? "
	
	//gm nuovo codice 3.0
	+ ", " + FINE_LAVORI.DATA_CONSEGNA + " =? "
	+ ", " + FINE_LAVORI.GIORNI_PROROGA + " =? "
	+ ", " + FINE_LAVORI.TERMINE_ULTIMAZIONE + " =? "
	//gm fine nuovo codice 3.0
	
	+ ", " + FINE_LAVORI.ONERI_RISOLUZIONE + " =?"
	+ " WHERE "
	+ FINE_LAVORI.ID_ULTIM + " =? AND "
	+ FINE_LAVORI.DATA_INIZIO_ULTIM + " =?";
	
	// PP fix controllo stato scheda
	private final String WHERE_CONF = " AND ("+ FINE_LAVORI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING
	 + " OR " + FINE_LAVORI.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING + " )";
    private final String WHERE_DEF = " AND "+ FINE_LAVORI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING;
	
	/**
	 * metodo per l'aggiornamento di una conclusione
	 * 
	 * @param bean ConclusioneBean
	 * @param cfUtente String
	 * @param confirm boolean (se true viene confermato, altrimenti solamete aggiornato)
	 * @throws SQLException
	 */
	public int update(ConclusioneBean bean , String cfUtente , boolean confirm)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try{
			stmt = activeConnection.prepareStatement(UPDATE_CONCLUSIONI + ((SimogFlags.isFlagNoDate()) ? WHERE_CONF : WHERE_DEF));
			if(confirm){
				stmt.setTimestamp(index++, getNow());
				stmt.setLong(index++, StatiScheda.CONFERMATO);
			}else{
				stmt.setNull(index++, Types.TIMESTAMP);
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
			}
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataUltimazione()));
			stmt.setString(index++, bean.getFlagOneri());
			stmt.setString(index++, bean.getFlagPolizza());
			stmt.setLong(index++, bean.getMotiviInterruzione());
			stmt.setLong(index++, bean.getMotiviRisoluzione());
			stmt.setString(index++ , PageHelper.formatDateOrNull(bean.getDataRisoluzione()));
			stmt.setLong(index++, bean.getNumInfMort());
			stmt.setLong(index++, bean.getNumInfPerm());
			stmt.setLong(index++, bean.getNumInfortuni());
			
			//gm nuovo codice 3.0
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataConsegna()));
			stmt.setLong(index++, bean.getGiorniProroga());
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getTermineUltimazione()));
			//gm fine nuovo codice 3.0
			
			stmt.setBigDecimal(index++, bean.getOneriRisoluzione());
			stmt.setLong(index++, bean.getIdUltim());
			stmt.setTimestamp(index++, bean.getDataIniUltim());
			
			int num = stmt.executeUpdate();
			List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(bean.getIdUltim());
			attributiChiave.add(bean.getDataIniUltim());
			if(confirm){
				LogBloccoDatiManager.loggingCONFIRM(activeConnection, logger, cfUtente, ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI, attributiChiave);
			}else{
				LogBloccoDatiManager.loggingUPDATE(activeConnection, logger, cfUtente, ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI, attributiChiave);
			}
			return num;
		}finally{
			close(rs , stmt);
		}
	}
	
	/**
	 * metodo per il salvataggio di una conclusione
	 * 
	 * @param bean ConclusioneBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public int save(ConclusioneBean bean , String cfUtente)throws SQLException{
		return update(bean , cfUtente , false);
	}
	
	/**
	 * metodo per la conferma di una conclusione
	 * 
	 * @param bean ConclusioneBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public int confirm(ConclusioneBean bean , String cfUtente)throws SQLException{
		return update(bean , cfUtente , true);
	}
	
	/**
	 * metodo per la storicizzazione di un record
	 * 
	 * @param idUltim long
	 * @param dataInizioUltim Timestamp
	 * @return Timestamp
	 * @throws SQLException
	 */
	public Timestamp copyRecord(long idUltim , Timestamp dataInizioUltim)throws SQLException{
		String SELECT_DATA_FINE = "SELECT " + FINE_LAVORI.DATA_FINE_ULTIM
			+ " FROM " + FINE_LAVORI.TABLE_NAME
			+ " WHERE " + FINE_LAVORI.ID_ULTIM + " =?" 
			+ " AND " + FINE_LAVORI.DATA_INIZIO_ULTIM + " =?"
			+ " AND " + FINE_LAVORI.ID_STATO + " = " + StatiScheda.CONFERMATO;
		
		String UPDATE_STATO_OLD_CONCLUSIONI = "UPDATE " + FINE_LAVORI.TABLE_NAME
			+ " SET " + FINE_LAVORI.ID_STATO + " =?"
			+ ", " + FINE_LAVORI.DATA_INIZIO_ULTIM + " =?"
			+ ", " + FINE_LAVORI.DATA_FINE_ULTIM + " =?"
			+ " WHERE " + FINE_LAVORI.ID_ULTIM + " =?"
			+ " AND " + FINE_LAVORI.DATA_INIZIO_ULTIM + " =?"
			+ " AND " + FINE_LAVORI.ID_STATO + " = " + StatiScheda.CONFERMATO;
		
		String COPY_RECORD = "INSERT INTO " + FINE_LAVORI.TABLE_NAME + "("
		    + FINE_LAVORI.ID_ULTIM	
		    + ", " +FINE_LAVORI.DATA_INIZIO_AGGIUDICAZIONE
			+ ", " + FINE_LAVORI.DATA_RISOLUZIONE
			+ ", " + FINE_LAVORI.DATA_ULTIMAZIONE
			+ ", " + FINE_LAVORI.FLAG_ONERI
			+ ", " + FINE_LAVORI.FLAG_POLIZZA
			+ ", " + FINE_LAVORI.ID_AGGIUDICAZIONE
			+ ", " + FINE_LAVORI.ID_MOTIVO_INTERR
			+ ", " + FINE_LAVORI.ID_MOTIVO_RISOL
			
			+ " ," + FINE_LAVORI.NUM_INF_MORT
			+ ", " + FINE_LAVORI.NUM_INF_PERM
			+ ", " + FINE_LAVORI.NUM_INFORTUNI
			+ ", " + FINE_LAVORI.ONERI_RISOLUZIONE
			+ ", " + FINE_LAVORI.ID_SCHEDA_LOCALE
			
			//gm nuovo codice 3.0
			+ ", " + FINE_LAVORI.DATA_CONSEGNA
			+ ", " + FINE_LAVORI.GIORNI_PROROGA
			+ ", " + FINE_LAVORI.TERMINE_ULTIMAZIONE
			//gm nuovo codice 3.0
			
			+ ", " + FINE_LAVORI.DATA_INIZIO_ULTIM
			+ ", " + FINE_LAVORI.DATA_FINE_ULTIM
			+ ", " + FINE_LAVORI.ID_STATO
			
			+ ") SELECT "
			+ FINE_LAVORI.ID_ULTIM
			+", " + FINE_LAVORI.DATA_INIZIO_AGGIUDICAZIONE
			+ ", " + FINE_LAVORI.DATA_RISOLUZIONE
			+ ", " + FINE_LAVORI.DATA_ULTIMAZIONE
			+ ", " + FINE_LAVORI.FLAG_ONERI
			+ ", " + FINE_LAVORI.FLAG_POLIZZA
			+ ", " + FINE_LAVORI.ID_AGGIUDICAZIONE
			+ ", " + FINE_LAVORI.ID_MOTIVO_INTERR
			+ ", " + FINE_LAVORI.ID_MOTIVO_RISOL
			
			+ " ," + FINE_LAVORI.NUM_INF_MORT
			+ ", " + FINE_LAVORI.NUM_INF_PERM
			+ ", " + FINE_LAVORI.NUM_INFORTUNI
			+ ", " + FINE_LAVORI.ONERI_RISOLUZIONE
			+ ", " + FINE_LAVORI.ID_SCHEDA_LOCALE

			//gm nuovo codice 3.0
			+ ", " + FINE_LAVORI.DATA_CONSEGNA
			+ ", " + FINE_LAVORI.GIORNI_PROROGA
			+ ", " + FINE_LAVORI.TERMINE_ULTIMAZIONE
			//gm nuovo codice 3.0
			
			+ ", ?"
			+ ", ?"
			+ ", ?"
			+ " FROM " + FINE_LAVORI.TABLE_NAME
			+ " WHERE " + FINE_LAVORI.ID_ULTIM + " =? AND "
			+ FINE_LAVORI.DATA_INIZIO_ULTIM + " =?";
		
		
		
		
		
		
		Timestamp dataFine = null;
		Timestamp nuovaDataRecord = null;
		int index = 1;
		ResultSet rs = null;
		PreparedStatement getDataFine = null;
		PreparedStatement updateRecord = null;
		PreparedStatement copyRecord = null;
		try{
			getDataFine = activeConnection.prepareStatement(SELECT_DATA_FINE);
			getDataFine.setLong(index++, idUltim);
			getDataFine.setTimestamp(index++, dataInizioUltim);
			rs = getDataFine.executeQuery();
			if(rs.next()){
				dataFine = rs.getTimestamp(FINE_LAVORI.DATA_FINE_ULTIM);
				
				index = 1;
				nuovaDataRecord = getNow();
				updateRecord = activeConnection.prepareStatement(UPDATE_STATO_OLD_CONCLUSIONI);
				updateRecord.setInt(index++, StatiScheda.IN_DEFINIZIONE);
				updateRecord.setTimestamp(index++, nuovaDataRecord);
				updateRecord.setNull(index++, Types.TIMESTAMP);
				updateRecord.setLong(index++, idUltim);
				updateRecord.setTimestamp(index++, dataInizioUltim);
				updateRecord.execute();
				
				index = 1;
				copyRecord = activeConnection.prepareStatement(createCopyRecord(COPY_RECORD,FINE_LAVORI.TABLE_NAME));
				copyRecord.setTimestamp(index++, dataInizioUltim);
				copyRecord.setTimestamp(index++, dataFine);
				copyRecord.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA);
				copyRecord.setLong(index++, idUltim);
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
	 * metodo per verificare l'esitenza di una conclusione
	 * 
	 * @param idUltim long
	 * @param dataInizioUltim Timestamp
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean existConclusioni(long idUltim , Timestamp dataInizioUltim)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		String QUERY = "SELECT * FROM " + FINE_LAVORI.TABLE_NAME
			+ " WHERE " + FINE_LAVORI.ID_ULTIM + " =? AND "
			+ FINE_LAVORI.DATA_INIZIO_ULTIM + " =?";
		try{
			stmt = activeConnection.prepareStatement(QUERY);
			stmt.setLong(index++, idUltim);
			stmt.setTimestamp(index++, dataInizioUltim);
			rs = stmt.executeQuery();
			return (rs.next());
		}finally{
			close(rs,stmt);
		}
	}
	

	
	private static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO = 
		"UPDATE " + FINE_LAVORI.TABLE_NAME
		+ " SET " + FINE_LAVORI.ID_STATO + " =?,"
		+ FINE_LAVORI.DATA_FINE_ULTIM + " = " + buildGetDate()		
		+ " WHERE " + FINE_LAVORI.ID_ULTIM + " =?"
		+ " AND " + FINE_LAVORI.DATA_INIZIO_ULTIM + " =?";
	
	/**
	 * metodo per l'aggionrnamento di un record
	 * 
	 * @param idUltim long
	 * @param dataInizioUltim Timestamp
	 * @param statoScheda String
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int updateRecord(long idUltim , Timestamp dataInizioUltim , String statoScheda)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		int numRow = -1;
		try{
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);
			stmt.setString(index++, statoScheda);
			stmt.setLong(index++, idUltim);
			stmt.setTimestamp(index++, dataInizioUltim);
			numRow = stmt.executeUpdate();
		}finally{
			close(rs, stmt);
		}
		return numRow;
	}
	
	private static String DELETE_CONCLUSIONI = "DELETE FROM "
		+ FINE_LAVORI.TABLE_NAME
		+ " WHERE " + FINE_LAVORI.ID_ULTIM + " =?"
		+ " AND " + FINE_LAVORI.DATA_INIZIO_ULTIM + " =?";
	
	/**
	 * metodo per la cancellazione di un record
	 * 
	 * @param idUltim long
	 * @param dataInizioUltim Timestamp
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int deleteRecord(long idUltim , Timestamp dataInizioUltim)throws SQLException{
		PreparedStatement stmt = null;
		int index = 1;
		try{
			stmt = activeConnection.prepareStatement(DELETE_CONCLUSIONI);
			stmt.setLong(index++, idUltim);
			stmt.setTimestamp(index++, dataInizioUltim);
			return stmt.executeUpdate();
		}finally{
			close(null, stmt);
		}
	}
	
	/**
	 * metodo per il recupero dei motivi interruzione
	 * @param o data inizio della scheda invocante (estensione della validit� a posteriori)
	 * @return Map&lt;String, String&gt; - id,descrizione
	 * @throws SQLException
	 */
	public Map<String, String> loadMotiviInterruzione(Object o)throws SQLException{
		//TICKET ALM #2847 - Motivi interruzione
		//return getTipologica(MOTIVI_INTERRUZIONE.TABLE_NAME, MOTIVI_INTERRUZIONE.ID_MOTIVO_INTERR, MOTIVI_INTERRUZIONE.DESCRIZIONE, MOTIVI_INTERRUZIONE.DATA_FINE_VALIDITA,o);
		return getTipologicaWithData(MOTIVI_INTERRUZIONE.TABLE_NAME, MOTIVI_INTERRUZIONE.ID_MOTIVO_INTERR, MOTIVI_INTERRUZIONE.DESCRIZIONE, MOTIVI_INTERRUZIONE.DATA_INIZIO_VALIDITA,MOTIVI_INTERRUZIONE.DATA_FINE_VALIDITA,o);
		
	}
	
	/**
	 * metodo per il recupero dei motivi risoluzione
	 * @param o data inizio della scheda invocante (estensione della validit� a posteriori) 
	 * @return Map&lt;String, String&gt; - id,descrizione
	 * @throws SQLException
	 */
	public Map<String, String> loadMotiviRisoluzione(Object o)throws SQLException{
		//TICKET ALM #2847 - Motivi risoluzione
		//return getTipologica(MOTIVI_RISOLUZIONE.TABLE_NAME, MOTIVI_RISOLUZIONE.ID_MOTIVO_RISOL, MOTIVI_RISOLUZIONE.DESCRIZIONE, MOTIVI_RISOLUZIONE.DATA_FINE_VALIDITA,o);
		return getTipologicaWithData(MOTIVI_RISOLUZIONE.TABLE_NAME, MOTIVI_RISOLUZIONE.ID_MOTIVO_RISOL, MOTIVI_RISOLUZIONE.DESCRIZIONE, MOTIVI_INTERRUZIONE.DATA_INIZIO_VALIDITA, MOTIVI_RISOLUZIONE.DATA_FINE_VALIDITA,o);
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
		ConclusioneBean conclusioneBean = loadByIdSimog(idSimog);
		
		if(conclusioneBean.getIdUltim() > 0){
			return _annulla(conclusioneBean.getIdUltim(), conclusioneBean.getDataIniUltim(),cfUtente);
		}
		return  false;
		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean annulla(String idLocale, String rifSimog, String cfUtente) throws SQLException {
		ConclusioneBean conclusioneBean = loadByIdLocale(idLocale, rifSimog);
		
		if(conclusioneBean.getIdUltim() > 0){
			return _annulla(conclusioneBean.getIdUltim(), conclusioneBean.getDataIniUltim(),cfUtente);
		}
		return false;
		
	}
	/**
	 * @param idConclusione
	 * @param dataInizioConclusione
	 * @throws SQLException
	 */
	private boolean _annulla(long idConclusione, Timestamp dataInizioConclusione, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_CONCLUSIONE);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idConclusione);
			stmt.setTimestamp(index++, dataInizioConclusione);
			someRowAffected = stmt.executeUpdate() > 0;
			if(someRowAffected){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idConclusione);
				attributiChiave.add(dataInizioConclusione);
					LogBloccoDatiManager.loggingDELETE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_FINELAVORI, attributiChiave);
			}
			return someRowAffected;
		}finally {
			close(null,stmt);
		}		
	}
	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadConclusione#fillBean(java.sql.ResultSet, it.avlp.simog.beans.conclusione.ConclusioneBean)
	 */
	public void fillBean(ResultSet rs, ConclusioneBean bean) throws SQLException {
		bean.setDataFinUltim(rs.getTimestamp(FINE_LAVORI.DATA_FINE_ULTIM));
		bean.setDataIniUltim(rs.getTimestamp(FINE_LAVORI.DATA_INIZIO_ULTIM));
		bean.setDataInizioAggiudicazione(rs.getTimestamp(FINE_LAVORI.DATA_INIZIO_AGGIUDICAZIONE));
		bean.setDataRisoluzione(PageHelper.getViewDate(rs.getString(FINE_LAVORI.DATA_RISOLUZIONE)));
		bean.setDataUltimazione(PageHelper.getViewDate(rs.getString(FINE_LAVORI.DATA_ULTIMAZIONE)));
		bean.setFlagOneri(rs.getString(FINE_LAVORI.FLAG_ONERI));
		bean.setFlagPolizza(rs.getString(FINE_LAVORI.FLAG_POLIZZA));
		bean.setIdAggiudicazione(rs.getLong(FINE_LAVORI.ID_AGGIUDICAZIONE));
		bean.setIdStato(rs.getLong(FINE_LAVORI.ID_STATO));
		bean.setIdUltim(rs.getLong(FINE_LAVORI.ID_ULTIM));
		bean.setNumInfMort(rs.getLong(FINE_LAVORI.NUM_INF_MORT));
		bean.setNumInfortuni(rs.getLong(FINE_LAVORI.NUM_INFORTUNI));
		bean.setNumInfPerm(rs.getLong(FINE_LAVORI.NUM_INF_PERM));
		bean.setOneriRisoluzione(rs.getBigDecimal(FINE_LAVORI.ONERI_RISOLUZIONE));
		bean.setMotiviInterruzione(rs.getLong(FINE_LAVORI.ID_MOTIVO_INTERR));
		bean.setMotiviRisoluzione(rs.getLong(FINE_LAVORI.ID_MOTIVO_RISOL));
		bean.setDescrizioneStato(rs.getString(STATI_SCHEDA.DESCRIZIONE));
		bean.setIdLocale(rs.getString(FINE_LAVORI.ID_SCHEDA_LOCALE));
		
		//gm nuovo codice 3.0
		bean.setDataConsegna(PageHelper.getViewDate(rs.getString(FINE_LAVORI.DATA_CONSEGNA)));
		bean.setGiorniProroga(rs.getLong(FINE_LAVORI.GIORNI_PROROGA));
		bean.setTermineUltimazione(PageHelper.getViewDate(rs.getString(FINE_LAVORI.TERMINE_ULTIMAZIONE)));
		//gm fine nuovo codice 3.0
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadConclusione#loadByIdLocale(java.lang.String, java.lang.String)
	 */
	public ConclusioneBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ConclusioneBean bean = null;
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_CONCLUSIONI + WHERE_IDLOCALE);
			int index = 1;		
			long idAggiudicazioneL = Long.parseLong(idAggiudicazione);
			stmt.setLong(index++, idAggiudicazioneL );
			stmt.setString(index++, idLocale );
			rs = stmt.executeQuery();
			bean = new ConclusioneBean();
			if(rs.next()){
				this.fillBean(rs, bean);
			}
			return  bean; 
		}finally{
			close(rs, stmt);
		}		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadConclusione#loadByIdSimog(long)
	 */
	public ConclusioneBean loadByIdSimog(long idSimog) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ConclusioneBean bean = null;
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_CONCLUSIONI + WHERE_IDSIMOG);
			int index = 1;
			stmt.setLong(index++, idSimog );
			rs = stmt.executeQuery();
			bean = new ConclusioneBean();
			if(rs.next()){
				this.fillBean(rs, bean);
			}
			return  bean; 
		}finally{
			close(rs, stmt);
		}		
	}

//	public boolean annullaByAggiudicazione(long idAggiudicazione,Timestamp dataInizioAggiudicazione, String cfUtente)throws SQLException {
//		ConclusioneBean conclusioneBean = this.load(idAggiudicazione, dataInizioAggiudicazione);
//		if(conclusioneBean.getIdUltim() > 0){
//			return _annulla(conclusioneBean.getIdUltim(), conclusioneBean.getDataIniUltim(), cfUtente);
//		}return false;
//	}
		

}