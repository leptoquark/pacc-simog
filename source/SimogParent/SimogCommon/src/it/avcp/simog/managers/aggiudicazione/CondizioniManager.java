package it.avcp.simog.managers.aggiudicazione;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti;
import it.avlp.simog.beans.CondizioneLottoBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.CondizioneAggBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.CONDIZIONI_AGG;
import it.avlp.simog.db.generated.CONDIZIONI_LOTTO;
import it.avlp.simog.util.ObjectIntrospector;

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
 * Classe che si occupa della lettura/scrittura dei dati relativi
 * alle condizioni di aggiudicazione
 *
 */
public class CondizioniManager extends AccessiDB implements IAnnullamentoMulti{
	public static String CLAZZ = "CondizioniManager";
	
	/**
	 * Costruttore della classe 
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public CondizioniManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	private static String QUERY_DELETE_CONDIZIONI_AGG = 
		"DELETE FROM "+CONDIZIONI_AGG.TABLE_NAME+
		" WHERE " + CONDIZIONI_AGG.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	/**
	 * Metodo per la cancellazione di un record
	 * 
	 * @param idRecord String
	 * @param dataInizioRecord Timestamp
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int deleteRecord(String idRecord, Timestamp dataInizioRecord) throws SQLException{
		
		int numRow=-1;
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_DELETE_CONDIZIONI_AGG);
			logger.debug("query per la delete record attivo condizioni: "+QUERY_DELETE_CONDIZIONI_AGG);
			int index = 1;
			stmt.setInt(index++, Integer.parseInt(idRecord));
			
			stmt.setObject(index++,dataInizioRecord);
			
			numRow = stmt.executeUpdate();
			 
		} finally {
			close(null, stmt);
		}
		return numRow;
	}
	
	private static String QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_CONDIZIONI_AGG = 
		"UPDATE "+CONDIZIONI_AGG.TABLE_NAME+
		" SET " + CONDIZIONI_AGG.ID_STATO + " = ?,"+
		CONDIZIONI_AGG.DATA_FINE_COND + " = " + buildGetDate() +
		" WHERE "+CONDIZIONI_AGG.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	/**
	 * metodo per la cancellazione delle condizioni associate ad un'aggiudicazione
	 * 
	 * @param idAggiudicazione long 
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws SQLException
	 */
	public void deleteCondizioniAgg(long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{		
		PreparedStatement stmt = activeConnection.prepareStatement(QUERY_DELETE_CONDIZIONI_AGG);
		try{
			stmt.setLong(1, idAggiudicazione);
			stmt.setTimestamp(2, dataInizioAggiudicazione);
			stmt.execute();
			logger.debug(CLAZZ + ": Eliminato record: idAggiudicazione=" + 
					idAggiudicazione + " dataInizioAgg=" + dataInizioAggiudicazione );
		}finally{
			close(null,stmt);
		}
	}
	
	/**
	 * metodo per l'aggionrnamento del record allo stato di cui Stringa in ingresso
	 * 
	 * @param idRecord String
	 * @param dataInizioRecord Timestamp
	 * @param stato_scheda String
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int updateRecord(String idRecord, Timestamp dataInizioRecord, String stato_scheda ) throws SQLException{
		
		int numRow = -1; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_CONDIZIONI_AGG);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_CONDIZIONI_AGG);

			stmt.setObject(1, stato_scheda);
			logger.debug(1 + ": "+stato_scheda);
			
			stmt.setInt(2, Integer.parseInt(idRecord));
			logger.debug(2 + ": "+idRecord);
			
			stmt.setObject(3,dataInizioRecord);
			logger.debug(3 + ": "+dataInizioRecord);
			numRow = stmt.executeUpdate();
			//PPactiveConnection.commit(currentActiveConnection);
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}
	
	private static String QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_CONDIZIONI_AGG_NEWRECORD = 
		"UPDATE "+CONDIZIONI_AGG.TABLE_NAME+
		" SET " + CONDIZIONI_AGG.ID_STATO + " = ?,"+ //+STATI_SCHEDA.CONFERMATO+
		CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?"+
		" WHERE "+CONDIZIONI_AGG.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	public int updateRecordToPointToNew(String idAggiudicazione, Timestamp dataInizioAggOld,Timestamp dataInizioAggNew, String stato_scheda ) throws SQLException{
		int numRow = -1; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_CONDIZIONI_AGG_NEWRECORD);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_CONDIZIONI_AGG_NEWRECORD);

			stmt.setObject(1, stato_scheda);
			logger.debug(1 + ": "+stato_scheda);
			
			stmt.setObject(2,dataInizioAggNew);
			logger.debug(2 + ": "+dataInizioAggNew);
			
			stmt.setInt(3, Integer.parseInt(idAggiudicazione));
			logger.debug(3 + ": "+idAggiudicazione);
			
			stmt.setObject(4,dataInizioAggOld);
			logger.debug(4 + ": "+dataInizioAggOld);
			numRow = stmt.executeUpdate();
			//PPactiveConnection.commit(currentActiveConnection);
		} finally {
			close(rs, stmt);
		}
		return numRow;	
	}
	
	private static String QUERY_SELECT_CONDIZIONI = "SELECT * FROM " + CONDIZIONI_AGG.TABLE_NAME
	+ " WHERE " + CONDIZIONI_AGG.ID_AGGIUDICAZIONE + " = ? AND " 
	+ CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	//TICKET ALM #3835
	private static String QUERY_SELECT_CONDIZIONI_LOTTO = "SELECT * FROM " + CONDIZIONI_LOTTO.TABLE_NAME
	+ " WHERE " + CONDIZIONI_LOTTO.ID_LOTTO + " = ?";
	private final String WHERE_STATO_CONDIZIONI = " AND (" + CONDIZIONI_LOTTO.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
			+" OR " + CONDIZIONI_LOTTO.T_ID_STATO + " = " + StatiScheda.CONFERMATO+")";
	private static String QUERY_DELETE_CONDIZIONI_LOTTO = 
			"DELETE FROM "+CONDIZIONI_LOTTO.TABLE_NAME+
			" WHERE " + CONDIZIONI_LOTTO.ID_LOTTO + " = ?";
	//FINE TICKET ALM #3835
	

	private final String WHERE_STATO = " AND (" + CONDIZIONI_AGG.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
	+" OR " + CONDIZIONI_AGG.T_ID_STATO + " = " + StatiScheda.CONFERMATO+")";
	
	private static String QUERY_UPDATE_CONDIZIONE = "INSERT INTO " + CONDIZIONI_AGG.TABLE_NAME + "( "
	+ CONDIZIONI_AGG.DATA_INIZIO_COND + ", "
	+ CONDIZIONI_AGG.ID_CONDIZIONE + ", "
	+ CONDIZIONI_AGG.ID_STATO + ", "
	+ CONDIZIONI_AGG.DATA_FINE_COND + ", "
	+ CONDIZIONI_AGG.ID_AGGIUDICAZIONE + ", "
	+ CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE 
	+ " ) VALUES ("
	+ "?, ?, ?, ?, ?, ? )";
	
	//TICKET ALM #3835
	private static String QUERY_UPDATE_CONDIZIONE_LOTTO = "INSERT INTO " + CONDIZIONI_LOTTO.TABLE_NAME + "( "
	+ CONDIZIONI_LOTTO.DATA_INIZIO_COND + ", "
	+ CONDIZIONI_LOTTO.ID_CONDIZIONE + ", "
	+ CONDIZIONI_LOTTO.ID_STATO + ", "
	+ CONDIZIONI_LOTTO.DATA_FINE_COND + ", "
	+ CONDIZIONI_LOTTO.ID_LOTTO 
	+ " ) VALUES ("
	+ "?, ?, ?, ?, ?)";
	//FINE TICKET ALM #3835
	
	/**
	 * Carica tutti le condizioni associate ad una aggiudicazione
	 * 
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @param ignoraStato TODO
	 * @return List&lt;CondizioneAggBean&gt; - la lista delle condizioni associate alla aggiudicazione
	 * @throws SQLException
	 */
	public List<CondizioneAggBean> loadMany(long idAggiudicazione, Timestamp dataInizioAggiudicazione, boolean ignoraStato) throws SQLException{
		
		String qry = QUERY_SELECT_CONDIZIONI;
		if(!ignoraStato)
			qry += WHERE_STATO;
		
		PreparedStatement stmt = activeConnection.prepareStatement(qry);
		ResultSet rs = null;
		int index = 1;
		ArrayList<CondizioneAggBean> ris = new ArrayList<CondizioneAggBean>();
		CondizioneAggBean nuovaCondizione = null;
		try{
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++,dataInizioAggiudicazione);
			rs = stmt.executeQuery();
			while(rs.next()){
				nuovaCondizione = new CondizioneAggBean();
				nuovaCondizione.setDataFineCond(rs.getTimestamp(CONDIZIONI_AGG.DATA_FINE_COND));
				nuovaCondizione.setDataInizioAggiudicazione(rs.getTimestamp(CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE));
				nuovaCondizione.setDataInizioCond(rs.getTimestamp(CONDIZIONI_AGG.DATA_INIZIO_COND));
				nuovaCondizione.setIdAggiudicazione(rs.getLong(CONDIZIONI_AGG.ID_AGGIUDICAZIONE));
				nuovaCondizione.setIdCondizione(rs.getLong(CONDIZIONI_AGG.ID_CONDIZIONE));
				nuovaCondizione.setIdCondizioneAgg(rs.getLong(CONDIZIONI_AGG.ID_CONDIZIONE_AGG));
				nuovaCondizione.setIdStato(rs.getInt(CONDIZIONI_AGG.ID_STATO));
				ris.add(nuovaCondizione);
				
			}

		}finally{
			close(rs, stmt);
		}
		
		return ris;
	
	}
	
	
	/**
	 * metodo per la conferma della condizione , nel passaggio viene settato anche 
	 * il campo del bean per lo stato
	 * 
	 * @param condizioneBean CondizioneAggBean
	 * @throws SQLException
	 */
	public void confirm(CondizioneAggBean condizioneBean) throws SQLException {
		update(condizioneBean, true);
	}

	/**
	 * metodo per l'inserimento/salvataggio di una condizione nello stato di "in definizione",
	 * nel passaggio viene settato nel bean in ingresso, se non &egrave presente la data di inizio condizione
	 * 
	 * @param condizioneBean CondizioneAggBean
	 * @throws SQLException
	 */
	public void save(CondizioneAggBean condizioneBean) throws SQLException {
		update(condizioneBean, false);
	}

	/**
	 * Aggiunge una nuova condizione
	 * 
	 * param condizioneBean CondizioneAggBean
	 * throws SQLException
	 */
	private void update(CondizioneAggBean condizioneBean, boolean conferma) throws SQLException{
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
			
		PreparedStatement stmt = activeConnection.prepareStatement(createInsertQuery(QUERY_UPDATE_CONDIZIONE,CONDIZIONI_AGG.ID_CONDIZIONE_AGG));
		ResultSet rs = null;
		int index = 1;		
		try{			
			
			if(condizioneBean.getDataInizioCond() == null)
				condizioneBean.setDataInizioCond(getNow());
			stmt.setObject(index++, condizioneBean.getDataInizioCond());
			stmt.setObject(index++, condizioneBean.getIdCondizione());
			
			if (conferma){
				stmt.setLong(index++, StatiScheda.CONFERMATO);
				stmt.setTimestamp(index++, getNow());
				condizioneBean.setIdStato(StatiScheda.CONFERMATO);
			}else{
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
				stmt.setNull(index++, Types.TIMESTAMP);
				condizioneBean.setIdStato(StatiScheda.IN_DEFINIZIONE);
			}
			stmt.setLong(index++, condizioneBean.getIdAggiudicazione());
			stmt.setObject(index++, condizioneBean.getDataInizioAggiudicazione());
			
			stmt.execute();
			logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(CondizioneAggBean.class, condizioneBean));
		}finally{
			close(rs, stmt);
		}
	
	}
	
	//modificare la query di copy per la gestione di tutti i contenziosi
	/**
	 * metodo per la storicizzazione del record
	 * 
	 * @param id_record String
	 * @param data_inizio_record Timestamp
	 * @param vecchiaData Timestamp
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean copyRecord(String id_record,Timestamp data_inizio_record, Timestamp vecchiaData) throws SQLException{
		String QUERY_UPDATE_OLD_RECORD =
			"UPDATE "+CONDIZIONI_AGG.TABLE_NAME+ " SET "
			+ CONDIZIONI_AGG.ID_STATO+ " = ?, "
			+ CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE+ " = ? "
			
			+" WHERE "
			+CONDIZIONI_AGG.T_ID_AGGIUDICAZIONE+" = ?"
			+" AND "+CONDIZIONI_AGG.T_DATA_INIZIO_AGGIUDICAZIONE+" = ?"
			+" AND "+CONDIZIONI_AGG.T_ID_STATO + "=" + StatiScheda.CONFERMATO;
		
		String QUERY_COPY_RECORD =
			"INSERT INTO "+CONDIZIONI_AGG.TABLE_NAME+" ("
			+CONDIZIONI_AGG.ID_CONDIZIONE_AGG	
			+","+CONDIZIONI_AGG.ID_AGGIUDICAZIONE
			+","+CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE
			+","+CONDIZIONI_AGG.ID_CONDIZIONE
			+","+CONDIZIONI_AGG.DATA_INIZIO_COND
			+","+CONDIZIONI_AGG.DATA_FINE_COND
			+","+CONDIZIONI_AGG.ID_STATO+" ) "
			+"SELECT "
			+CONDIZIONI_AGG.ID_CONDIZIONE_AGG	
			+","+CONDIZIONI_AGG.ID_AGGIUDICAZIONE
			+","+CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE
			+","+CONDIZIONI_AGG.ID_CONDIZIONE
			+", ?"
			+", ?"
			+", ?"
			+" FROM "+CONDIZIONI_AGG.TABLE_NAME
			+" WHERE "
			+CONDIZIONI_AGG.ID_AGGIUDICAZIONE+" = ? AND "
			+CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE+" = ?"
			+" AND "+CONDIZIONI_AGG.ID_STATO+" = "+StatiScheda.CONFERMATO;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try{
			int index = 1;
			stmt = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD,CONDIZIONI_AGG.TABLE_NAME));
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
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_CONDIZIONI);
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

	
	//TICKET ALM #3835
	/**
	 * Carica tutti le condizioni associate ad un lotto
	 * 
	 * @param idLotto long
	 * @param ignoraStato TODO
	 * @return List&lt;CondizioneAggBean&gt; - la lista delle condizioni associate a un lotto
	 * @throws SQLException
	 */
	public List<CondizioneLottoBean> loadManyCondizioniLotto(long idLotto, boolean ignoraStato) throws SQLException{
		
		String qry = QUERY_SELECT_CONDIZIONI_LOTTO;
		if(!ignoraStato)
			qry += WHERE_STATO_CONDIZIONI;
		
		PreparedStatement stmt = activeConnection.prepareStatement(qry);
		ResultSet rs = null;
		int index = 1;
		ArrayList<CondizioneLottoBean> ris = new ArrayList<CondizioneLottoBean>();
		CondizioneLottoBean nuovaCondizione = null;
		try{
			stmt.setLong(index++, idLotto);
			rs = stmt.executeQuery();
			while(rs.next()){
				nuovaCondizione = new CondizioneLottoBean();
				nuovaCondizione.setDataFineCond(rs.getTimestamp(CONDIZIONI_LOTTO.DATA_FINE_COND));
				nuovaCondizione.setDataInizioCond(rs.getTimestamp(CONDIZIONI_LOTTO.DATA_INIZIO_COND));
				nuovaCondizione.setIdLotto(rs.getLong(CONDIZIONI_LOTTO.ID_LOTTO));
				nuovaCondizione.setIdCondizione(rs.getLong(CONDIZIONI_LOTTO.ID_CONDIZIONE));
				nuovaCondizione.setIdCondizioneLotto(rs.getLong(CONDIZIONI_LOTTO.ID_CONDIZIONE_LOTTO));
				nuovaCondizione.setIdStato(rs.getInt(CONDIZIONI_LOTTO.ID_STATO));
				ris.add(nuovaCondizione);
				
			}

		}finally{
			close(rs, stmt);
		}
		
		return ris;
	
	}
	
	
	/**
	 * metodo per la conferma della condizione , nel passaggio viene settato anche 
	 * il campo del bean per lo stato
	 * 
	 * @param condizioneBean CondizioneLottoBean
	 * @throws SQLException
	 */
	public void confirmCondBean(CondizioneLottoBean condizioneBean) throws SQLException {
		updateCondLotto(condizioneBean, true);
	}

	/**
	 * metodo per l'inserimento/salvataggio di una condizione nello stato di "in definizione",
	 * nel passaggio viene settato nel bean in ingresso, se non &egrave presente la data di inizio condizione
	 * 
	 * @param condizioneBean CondizioneAggBean
	 * @throws SQLException
	 */
	public void saveCondBean(CondizioneLottoBean condizioneBean) throws SQLException {
		updateCondLotto(condizioneBean, false);
	}

	/**
	 * Aggiunge una nuova condizione
	 * 
	 * param condizioneBean CondizioneAggBean
	 * throws SQLException
	 */
	private void updateCondLotto(CondizioneLottoBean condizioneBean, boolean conferma) throws SQLException{
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
			
		PreparedStatement stmt = activeConnection.prepareStatement(createInsertQuery(QUERY_UPDATE_CONDIZIONE_LOTTO,CONDIZIONI_LOTTO.ID_CONDIZIONE_LOTTO));
		ResultSet rs = null;
		int index = 1;		
		try{			
			if(condizioneBean.getDataInizioCond() == null)
				condizioneBean.setDataInizioCond(getNow());
			stmt.setObject(index++, condizioneBean.getDataInizioCond());
			stmt.setObject(index++, condizioneBean.getIdCondizione());
			
			if (conferma){
				stmt.setLong(index++, StatiScheda.CONFERMATO);
				stmt.setTimestamp(index++, getNow());
				condizioneBean.setIdStato(StatiScheda.CONFERMATO);
			}else{
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
				stmt.setNull(index++, Types.TIMESTAMP);
				condizioneBean.setIdStato(StatiScheda.IN_DEFINIZIONE);
			}
			stmt.setLong(index++, condizioneBean.getIdLotto());
			
			stmt.execute();
			logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(CondizioneLottoBean.class, condizioneBean));
		}finally{
			close(rs, stmt);
		}
	
	}
	
	/**
	 * metodo per la cancellazione delle condizioni associate ad un lotto
	 * 
	 * @param idLotto long 
	 * @throws SQLException
	 */
	public void deleteCondizioniLotto(long idLotto)throws SQLException{		
		PreparedStatement stmt = activeConnection.prepareStatement(QUERY_DELETE_CONDIZIONI_LOTTO);
		try{
			stmt.setLong(1, idLotto);
			stmt.execute();
			logger.debug(CLAZZ + ": Eliminato record: idLotto=" + 	idLotto  );
		}finally{
			close(null,stmt);
		}
	}
	
	//FINET TICKET ALM #3835
}
