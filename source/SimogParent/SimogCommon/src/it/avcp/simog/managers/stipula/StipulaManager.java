package it.avcp.simog.managers.stipula;

import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avcp.simog.managers.comportamento.annullamento.IAnnullamento;
import it.avcp.simog.managers.comportamento.caricamento.ILoadStipula;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.stipula.StipulaBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.db.generated.STIPULA;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.util.ObjectIntrospector;
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
 * Classe che si occupa della gestione dei dati relativi a stipula
 *
 */
public class StipulaManager extends AccessiDB implements IAnnullamento,ILoadStipula {
	
	public static String CLAZZ = "StipulaManager";
	
	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public StipulaManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	/**
	 * metodo per il recupero del bean stipula associato all'aggiudicazione di cui id
	 * 
	 * @param idAggiudicazione Long
	 * @param dataIniAggiudicazione Timestamp
	 * @return StipulaBean
	 * @throws SQLException
	 */
	public StipulaBean load(Long idAggiudicazione, Timestamp dataIniAggiudicazione) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StipulaBean sb = new StipulaBean();
		try{
			stmt = activeConnection.prepareStatement(SELECT_STIPULA + WHERE_STANDARD);
			int index = 1;
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataIniAggiudicazione);
			rs = stmt.executeQuery();
			while(rs.next()){
				fillBean(rs, sb);
				
			}
		}finally{
			close(rs,stmt);
		}
		return sb;
	}
	
	/**
	 * metodo per l'inserimento di una stipula
	 * 
	 * @param stipulaBean StipulaBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public void insert(StipulaBean stipulaBean, String cfUtente)throws SQLException{
		logger.debug("[stipula]problema con le pubblicazioni: "+ObjectIntrospector.propertiesInfo(PubblicazioneBean.class,stipulaBean.getPubblicazione()));
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try{		
			stmt = activeConnection.prepareStatement(createInsertQuery(QUERY_INSERT_STIPULA,STIPULA.ID_STIPULA));

			stipulaBean.setDataInizioStipula(getNow());
			stmt.setTimestamp(index++,stipulaBean.getDataInizioStipula());
			stmt.setLong(index++, stipulaBean.getPubblicazione().getIdPubblicazione());
			stmt.setTimestamp(index++, stipulaBean.getPubblicazione().getDataInizioPubblicazione());
			stmt.setLong(index++, stipulaBean.getIdAggiudicazione());
			stmt.setTimestamp(index++, stipulaBean.getDataInizioAggiudicazione());
			
			stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE);
			
			stmt.setString(index++, PageHelper.formatDateOrNull(stipulaBean.getDataStipulaContratto()));
			stmt.setString(index++, PageHelper.formatDateOrNull(stipulaBean.getDataDecorrenza()));
			stmt.setString(index++, PageHelper.formatDateOrNull(stipulaBean.getDataScadenza()));
			
			if(stipulaBean.getIdLocale() == null){
				stmt.setNull(index++, Types.VARCHAR);
			}else{
				stmt.setString(index++, stipulaBean.getIdLocale());
			}		
			if(stmt.execute()){
				rs = stmt.getResultSet();
				rs.next();
				stipulaBean.setIdStipula(rs.getLong(STIPULA.ID_STIPULA));
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(stipulaBean.getIdStipula());
				attributiChiave.add(stipulaBean.getDataInizioStipula());
				LogBloccoDatiManager.loggingINSERT(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_STIPULA, attributiChiave);
			}			
		}finally{
			close(rs, stmt);			
		}
	}
	
	private final String SELECT_STIPULA = 
		"SELECT " + STIPULA.TABLE_NAME + ".*," +
		STATI_SCHEDA.T_DESCRIZIONE+ " + " +  buildRichAnnQuery(IdentificativoSchede.TAB_STIPULA, STIPULA.T_ID_STIPULA,null) 
		+ " AS "+STATI_SCHEDA.DESCRIZIONE +
		" FROM " + 
		STIPULA.TABLE_NAME + ", " +
		STATI_SCHEDA.TABLE_NAME;
	
	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadStipula#fillBean(java.sql.ResultSet, it.avlp.simog.beans.stipula.StipulaBean)
	 */
	
	public void fillBean(ResultSet rs, StipulaBean stipulaBean) throws SQLException {
		stipulaBean.setIdStipula(rs.getLong(STIPULA.ID_STIPULA));
		stipulaBean.setDataInizioStipula(rs.getTimestamp(STIPULA.DATA_INIZIO_STIPULA));
		stipulaBean.setDataStipulaContratto(PageHelper.getViewDate(rs.getString(STIPULA.DATA_STIPULA_CONTRATTO)));
		stipulaBean.setDataDecorrenza(PageHelper.getViewDate(rs.getString(STIPULA.DATA_DECORRENZA)));
		stipulaBean.setDataScadenza(PageHelper.getViewDate(rs.getString(STIPULA.DATA_SCADENZA)));
		stipulaBean.setIdAggiudicazione(rs.getLong(STIPULA.ID_AGGIUDICAZIONE));
		stipulaBean.setDataInizioAggiudicazione(rs.getTimestamp(STIPULA.DATA_INIZIO_AGGIUDICAZIONE));
		stipulaBean.setIdStato(rs.getInt(STIPULA.ID_STATO));
		stipulaBean.setIdLocale(rs.getString(STIPULA.ID_SCHEDA_LOCALE));
		stipulaBean.setDescrizioneStato(rs.getString(STATI_SCHEDA.DESCRIZIONE));

		//setting inner bean pubblicazione
		PubblicazioneBean pubblicazioneBean = null; // PP pubblicazioneBean = new PubblicazioneBean();
		// PP pubblicazioneBean.setIdPubblicazione(rs.getLong(STIPULA.ID_PUBBLICAZIONE));
		// PP pubblicazioneBean.setDataInizioPubblicazione(rs.getTimestamp(STIPULA.DATA_INIZIO_PUBB));
		
		// gm vecchio caricamento, PP ripristinato
		pubblicazioneBean =	new PubblicazioneManager(activeConnection,logger).getPubblicazione(rs.getLong(STIPULA.ID_PUBBLICAZIONE),rs.getTimestamp(STIPULA.DATA_INIZIO_PUBB));
//		logger.debug(""+rs.getLong(INIZIO_LAVORI.ID_PUBBLICAZIONE)+","+rs.getTimestamp(INIZIO_LAVORI.DATA_INIZIO_PUBB));
//		logger.debug(ObjectIntrospector.propertiesInfo(PubblicazioneBean.class, pubblicazioneBean));
		stipulaBean.setPubblicazione(pubblicazioneBean);
	}
	
	private final String DELETE_RECORD_STIPULA = 
		"DELETE FROM " + STIPULA.TABLE_NAME 
		+ " WHERE " + STIPULA.ID_STIPULA + " = ? "
		+ " AND " + STIPULA.DATA_INIZIO_STIPULA + " = ? ";
	


	private String QUERY_INSERT_STIPULA = 
	"INSERT INTO "+STIPULA.TABLE_NAME+" ("
		+STIPULA.DATA_INIZIO_STIPULA
		+ ", " + STIPULA.ID_PUBBLICAZIONE 
		+ ", " + STIPULA.DATA_INIZIO_PUBB
		+ ", " + STIPULA.ID_AGGIUDICAZIONE
		+ ", " + STIPULA.DATA_INIZIO_AGGIUDICAZIONE
		+ ", " + STIPULA.ID_STATO
		+ ", " + STIPULA.DATA_STIPULA_CONTRATTO
		+ ", " + STIPULA.DATA_DECORRENZA
		+ ", " + STIPULA.DATA_SCADENZA
		+ ", " + STIPULA.ID_SCHEDA_LOCALE
		+")"
		+" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	

	private String QUERY_UPDATE_STIPULA = 
		"UPDATE "+STIPULA.TABLE_NAME+ " SET "	
		+STIPULA.ID_STATO+ " = ? "
		+ ", " + STIPULA.DATA_FINE_STIPULA + " = ? "
		+ ", " + STIPULA.DATA_STIPULA_CONTRATTO + " = ? "
		+ ", " + STIPULA.DATA_DECORRENZA + " = ? "
		+ ", " + STIPULA.DATA_SCADENZA + " = ? "
		+ " WHERE "
		+ STIPULA.ID_STIPULA + "= ?"
		+ " AND " + STIPULA.DATA_INIZIO_STIPULA + "= ?";
	
	// PP fix controllo stato scheda
    private final String WHERE_CONF = " AND (" + STIPULA.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING
         + " OR " + STIPULA.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING  + " ) ";
    private final String WHERE_DEF = " AND " + STIPULA.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING
		+ " AND (" + STIPULA.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING
		+ " OR " + STIPULA.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING + " ) ";
	
	/**
	 * metodo per la cancellazione di un record
	 * 
	 * @param idStipula long
	 * @param dataInizioStipula Timestamp
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int deleteRecord(long idStipula, Timestamp dataInizioStipula)throws SQLException{
		PreparedStatement stmt = null;
		int index = 1;
		try{
			
			
			stmt = activeConnection.prepareStatement(DELETE_RECORD_STIPULA);
			stmt.setLong(index++, idStipula);
			stmt.setTimestamp(index++, dataInizioStipula);
			return stmt.executeUpdate();
		}finally{
			close(null,stmt);
		}
	}

	/**
	 * metodo per la conferma della fase di stipula
	 * 
	 * @param stipulaBean StipulaBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public int confirm(StipulaBean stipulaBean, String cfUtente)throws SQLException{
		logger.debug("CONFIRM: " + ObjectIntrospector.propertiesInfo(StipulaBean.class, stipulaBean));
		return update(stipulaBean,cfUtente, true);
	}
	
	/**
	 * metodo per il salvataggio della fase di stipula
	 * 
	 * @param stipulaBean StipulaBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public int save(StipulaBean stipulaBean,String cfUtente) throws SQLException{
		logger.debug("SAVE: " + ObjectIntrospector.propertiesInfo(StipulaBean.class, stipulaBean));
		return update(stipulaBean,cfUtente, false);
	}
	
	private int update(StipulaBean stipulaBean,String cfUtente, boolean conferma)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try{
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_STIPULA + ((SimogFlags.isFlagNoDate()) ? WHERE_CONF : WHERE_DEF));
			//confermo o aggiorno...
			if(conferma){
				stmt.setInt(index++, StatiScheda.CONFERMATO);
				stmt.setTimestamp(index++, getNow());
			}else{
				stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE);
				stmt.setNull(index++, Types.TIMESTAMP);
			}
			
			stmt.setString(index++, PageHelper.formatDateOrNull(stipulaBean.getDataStipulaContratto()));
			stmt.setString(index++, PageHelper.formatDateOrNull(stipulaBean.getDataDecorrenza()));
			stmt.setString(index++, PageHelper.formatDateOrNull(stipulaBean.getDataScadenza()));
			
			stmt.setLong(index++, stipulaBean.getIdStipula());
			stmt.setTimestamp(index++, stipulaBean.getDataInizioStipula());
			int num = stmt.executeUpdate();
			
			List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(stipulaBean.getIdStipula());
			attributiChiave.add(stipulaBean.getDataInizioStipula());
			if(conferma)
				LogBloccoDatiManager.loggingCONFIRM(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_STIPULA, attributiChiave);
			else
				LogBloccoDatiManager.loggingUPDATE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_STIPULA, attributiChiave);
			return num;
		}finally{
			close(rs, stmt);
			
		}
	}
	
//	
//	/***********************************************************************************************************************************
//	 ************************************RICHIESTA ANNULLAMENTO*************************************************************************
//	 */
//	
	/**
	 * metodo per la storicizzazione di un record
	 * 
	 * @param idStipula long
	 * @param dataInizioStipula Timestamp
	 * @param oldDataInizioPubblicazione Timestamp
	 * @param newDataInizioPubblicazione Timestamp
	 * @return Timestamp - nuova data stipula
	 * @throws SQLException
	 */
	public Timestamp copyRecord(long idStipula, Timestamp dataInizioStipula, Timestamp oldDataInizioPubblicazione, Timestamp newDataInizioPubblicazione)throws SQLException{

		String QUERY_SELECT_DATA_FINE = "SELECT " + STIPULA.DATA_FINE_STIPULA
		+ " FROM " + STIPULA.TABLE_NAME
		+ " WHERE " + STIPULA.ID_STIPULA + " = ? "
		+ " AND " + STIPULA.DATA_INIZIO_STIPULA + " = ?"
		+ " AND " + STIPULA.ID_STATO + " = " + StatiScheda.CONFERMATO;
		
		
		String QUERY_COPY_RECORD_STIPULA = 
		
			"INSERT INTO "+STIPULA.TABLE_NAME+" ("
			+ STIPULA.ID_STIPULA
			+ ", " + STIPULA.ID_PUBBLICAZIONE 
			+ ", " + STIPULA.ID_AGGIUDICAZIONE
			+ ", " + STIPULA.DATA_INIZIO_AGGIUDICAZIONE
			+ ", " + STIPULA.DATA_STIPULA_CONTRATTO
			+ ", " + STIPULA.DATA_DECORRENZA
			+ ", " + STIPULA.DATA_SCADENZA
			
			+ ", " + STIPULA.ID_SCHEDA_LOCALE
			
			+ ", " + STIPULA.DATA_INIZIO_STIPULA
			+ ", " + STIPULA.DATA_FINE_STIPULA
			+ ", " + STIPULA.DATA_INIZIO_PUBB
			+ ", " + STIPULA.ID_STATO +" ) "
			+" SELECT "
			+ STIPULA.ID_STIPULA
			+ ", " + STIPULA.ID_PUBBLICAZIONE 
			+ ", " + STIPULA.ID_AGGIUDICAZIONE
			+ ", " + STIPULA.DATA_INIZIO_AGGIUDICAZIONE
			+ ", " + STIPULA.DATA_STIPULA_CONTRATTO
			+ ", " + STIPULA.DATA_DECORRENZA
			+ ", " + STIPULA.DATA_SCADENZA
			
			+ ", " + STIPULA.ID_SCHEDA_LOCALE
			+", ?"
			+", ?"
			+", ?"
			+", ?"
	        + " FROM " + STIPULA.TABLE_NAME
	        + " WHERE "
	        + STIPULA.ID_STIPULA + " = ?"
	        + " AND " + STIPULA.DATA_INIZIO_STIPULA + " = ?";
	        
		
		String UPDATE_STATO_OLD_RECORD_STIPULA = 
			"UPDATE " + STIPULA.TABLE_NAME + " SET "
			+ STIPULA.ID_STATO + " = " +StatiScheda.IN_DEFINIZIONE + " "
			+ ", " + STIPULA.DATA_INIZIO_STIPULA + " = ?"
			+ ", " + STIPULA.DATA_INIZIO_PUBB + " = ?"
			+ ", " + STIPULA.DATA_FINE_STIPULA + " = ?"			
			+ " WHERE "
			+ STIPULA.ID_STIPULA + " = ? AND  "
			+ STIPULA.DATA_INIZIO_STIPULA + " = ? AND "
			+ STIPULA.ID_STATO + " = " + StatiScheda.CONFERMATO;
		
		PreparedStatement getDataFine = null;
		PreparedStatement crStiStmt = null;
		PreparedStatement upStiStmt = null;
		Timestamp dataFine = null;
	
		Timestamp nuovaDataStipula = null;
		int index = 1;
		ResultSet rs = null;
		try{
			getDataFine = activeConnection.prepareStatement(QUERY_SELECT_DATA_FINE);
			getDataFine.setLong(index++, idStipula);
			getDataFine.setTimestamp(index++, dataInizioStipula);
			rs = getDataFine.executeQuery();
			if(rs.next()){
				dataFine = rs.getTimestamp(STIPULA.DATA_FINE_STIPULA);
				logger.debug("data fine: "+dataFine);
				
			try{
				//upLavStmt = activeConnection.prepareStatement(UPDATE_STATO_OLD_RECORD_STIPULA);
				index = 1;
				//update stato old record stipula
				nuovaDataStipula = getNow();
				upStiStmt = activeConnection.prepareStatement(UPDATE_STATO_OLD_RECORD_STIPULA);
				//upLavStmt.setInt(index++,StatiScheda.IN_DEFINIZIONE);
				upStiStmt.setTimestamp(index++, nuovaDataStipula);
				upStiStmt.setTimestamp(index++, newDataInizioPubblicazione);
				upStiStmt.setNull(index++, Types.TIMESTAMP);				
				upStiStmt.setLong(index++, idStipula);
				upStiStmt.setTimestamp(index++, dataInizioStipula);
				upStiStmt.execute();
			}catch(Throwable t){t.printStackTrace();throw (SQLException)t;}
				
				
				crStiStmt = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD_STIPULA,STIPULA.TABLE_NAME));
				index = 1;
				// copy record inizio lavori
				
				crStiStmt.setTimestamp(index++, dataInizioStipula);
				
				crStiStmt.setTimestamp(index++, dataFine);
				
				
				crStiStmt.setTimestamp(index++, oldDataInizioPubblicazione);
				crStiStmt.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA);
				crStiStmt.setLong(index++, idStipula);
				crStiStmt.setTimestamp(index++, nuovaDataStipula);
				crStiStmt.execute();
				return nuovaDataStipula;
			
		
			}
			return null;
		}/*catch(Exception e){	
			e.printStackTrace();
			throw new SQLException(e.getMessage());
			
		}*/finally{
			close(rs, getDataFine);
			close(null, crStiStmt);
			close(null, upStiStmt);
		}	
	}
	

	/**
	 * metodo per la verifica di esistenza di una fase stipula
	 * 
	 * @param idStipula long
	 * @param dataInStipula Timestamp
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean existStipula(long idStipula, Timestamp dataInStipula) throws SQLException{

		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM " + STIPULA.TABLE_NAME + " WHERE " + 
		                STIPULA.ID_STIPULA + " = ? AND " + 
		                STIPULA.DATA_INIZIO_STIPULA + " = ?";
		
		try{
			stmt = activeConnection.prepareStatement(query);
			stmt.setLong(1, idStipula);
			stmt.setTimestamp(2, dataInStipula);
			rs = stmt.executeQuery();
			return rs.next();
		}finally{
			close(rs, stmt);
		}
	}
	
	public static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO = 
		"UPDATE "+STIPULA.TABLE_NAME+
		" SET " + STIPULA.ID_STATO + " = ?,"
		+ STIPULA.DATA_FINE_STIPULA + " = " + buildGetDate()+	
		" WHERE "+STIPULA.ID_STIPULA + " = ?"+
		" AND "+STIPULA.DATA_INIZIO_STIPULA + " = ?";
	
	
	/**
	 * metodo per l'aggionramento di un record
	 * 
	 * @param idStipula long
	 * @param dataInizioStipula Timestamp
	 * @param stato_scheda String
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int updateRecord(long idStipula, Timestamp dataInizioStipula, String stato_scheda ) throws SQLException{
		int numRow=-1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);

	
			stmt.setString(index++, stato_scheda);
			stmt.setLong(index++, idStipula);
			stmt.setTimestamp(index++,dataInizioStipula);
			numRow = stmt.executeUpdate();
		
		} finally {
			close(rs, stmt);
		}
		return numRow;
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
		StipulaBean stipulaBean = loadByIdSimog(idSimog);
		
		if(stipulaBean.getIdStipula() > 0){
			return _annulla(stipulaBean.getIdStipula(), stipulaBean.getDataInizioStipula(),cfUtente);
		}
		return false;	
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean annulla(String idLocale, String rifSimog, String cfUtente) throws SQLException {
		StipulaBean stipulaBean = loadByIdLocale(idLocale, rifSimog);
		
		if(stipulaBean.getIdStipula() > 0){
			return _annulla(stipulaBean.getIdStipula(), stipulaBean.getDataInizioStipula(),cfUtente);
		}
		return false;
		
	}

	/**
	 * @param idStipula
	 * @param dataInizioInizioStipula
	 * @throws SQLException
	 */
	private boolean _annulla(long idStipula, Timestamp dataInizioInizioStipula, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_STIPULA);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idStipula);
			stmt.setTimestamp(index++, dataInizioInizioStipula);
			someRowAffected = stmt.executeUpdate() > 0;
			if(someRowAffected){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idStipula);
				attributiChiave.add(dataInizioInizioStipula);
					LogBloccoDatiManager.loggingDELETE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_STIPULA, attributiChiave);
			}
			return someRowAffected;
		}finally {
			close(null,stmt);
		}		
	}

	
	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadStipula#loadByIdLocale(java.lang.String, java.lang.String)
	 */
	public StipulaBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StipulaBean sb = new StipulaBean();
		try{
			stmt = activeConnection.prepareStatement(SELECT_STIPULA + WHERE_IDLOCALE);
			int index = 1;
			stmt.setString(index++, idLocale);
			long idAggiudicazioneL = Long.parseLong(idAggiudicazione);
			stmt.setLong(index++, idAggiudicazioneL);			
			rs = stmt.executeQuery();
			if(rs.next()){
				fillBean(rs, sb);				
			}
		}finally{
			close(rs,stmt);
		}
		return sb;
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadStipula#loadByIdSimog(long)
	 */
	public StipulaBean loadByIdSimog(long idSimog) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StipulaBean sb = new StipulaBean();
		try{
			stmt = activeConnection.prepareStatement(SELECT_STIPULA + WHERE_IDSIMOG);
			int index = 1;
			stmt.setLong(index++, idSimog);
			rs = stmt.executeQuery();
			if(rs.next()){
				fillBean(rs, sb);				
			}
		}finally{
			close(rs,stmt);
		}
		return sb;
	}

}
