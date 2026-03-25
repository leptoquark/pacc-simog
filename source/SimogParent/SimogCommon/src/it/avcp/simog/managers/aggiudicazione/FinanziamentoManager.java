package it.avcp.simog.managers.aggiudicazione;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.TipoFinanziamentoBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.FINANZIAMENTI_AGG;
import it.avlp.simog.db.generated.TIPO_FINANZIAMENTO;
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
 * Classe che si occupa della gestione dei dati relativi ai finanziamenti
 *
 */
public class FinanziamentoManager extends AccessiDB implements IAnnullamentoMulti{
	
public static String CLAZZ = "FinanziamentoManager";
	
	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection: 
	 * @param logger Logger
	 */
	public FinanziamentoManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
		
	}
	
	private static String QUERY_DELETE_FINANZIAMENTI_AGG = 
		"DELETE FROM "+FINANZIAMENTI_AGG.TABLE_NAME+
		" WHERE " + FINANZIAMENTI_AGG.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+FINANZIAMENTI_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	private static String QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_FINANZIAMENTI_AGG = 
		"UPDATE "+FINANZIAMENTI_AGG.TABLE_NAME+
		" SET " + FINANZIAMENTI_AGG.ID_STATO + " = ?,"+
		FINANZIAMENTI_AGG.DATA_FINE_FINAGG + " = " + buildGetDate() +
		" WHERE "+FINANZIAMENTI_AGG.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+FINANZIAMENTI_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
		
	private static String QUERY_SELECT_FINANZIAMENTI_AGG = "SELECT "
			+ FINANZIAMENTI_AGG.DATA_FINE_FINAGG + ", "
			+ FINANZIAMENTI_AGG.DATA_INIZIO_AGGIUDICAZIONE + ", "
			+ FINANZIAMENTI_AGG.DATA_INIZIO_FINAGG + ", "
			+ FINANZIAMENTI_AGG.ID_AGGIUDICAZIONE + ", "
			+ TIPO_FINANZIAMENTO.T_ID_FINANZIAMENTO + ", "			
			+ FINANZIAMENTI_AGG.ID_STATO + ", "
			+ FINANZIAMENTI_AGG.IMPORTO_FINANZIAMENTO + ", "
			+ TIPO_FINANZIAMENTO.DESCRIZIONE + " "
			+ " FROM " + FINANZIAMENTI_AGG.TABLE_NAME 
			+ " JOIN " + TIPO_FINANZIAMENTO.TABLE_NAME 
			+ " ON " + FINANZIAMENTI_AGG.T_ID_FINANZIAMENTO + " = " + TIPO_FINANZIAMENTO.T_ID_FINANZIAMENTO
			+ " WHERE " + FINANZIAMENTI_AGG.ID_AGGIUDICAZIONE + " = ? AND " 
			+ FINANZIAMENTI_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ? ";
	
	private final String WHERE_STATO = "AND (" + FINANZIAMENTI_AGG.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + FINANZIAMENTI_AGG.T_ID_STATO + " = " + StatiScheda.CONFERMATO+")" ;
	
	private static String QUERY_UPDATE_FINANZIAMENTI_AGG = "INSERT INTO " + FINANZIAMENTI_AGG.TABLE_NAME + "( "
	+ FINANZIAMENTI_AGG.DATA_INIZIO_FINAGG + ", "
	+ FINANZIAMENTI_AGG.ID_FINANZIAMENTO + ", "
	+ FINANZIAMENTI_AGG.IMPORTO_FINANZIAMENTO + ", "
	+ FINANZIAMENTI_AGG.ID_STATO + ", "
	+ FINANZIAMENTI_AGG.DATA_FINE_FINAGG + ", "
	+ FINANZIAMENTI_AGG.ID_AGGIUDICAZIONE + ", "
	+ FINANZIAMENTI_AGG.DATA_INIZIO_AGGIUDICAZIONE 
	+ " ) VALUES ("
	+ "?, ?, ?, ?, ?, ?, ?)";
	
	
	/**
	 * metodo per l'aggiornamento di un record allo stato di cui stringa in ingresso "stato scheda"
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
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_FINANZIAMENTI_AGG);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_FINANZIAMENTI_AGG);

			stmt.setString(1, stato_scheda);
			logger.debug(1 + ": "+stato_scheda);
			
			stmt.setInt(2, Integer.parseInt(idRecord));
			logger.debug(2 + ": "+idRecord);
			
			stmt.setTimestamp(3,dataInizioRecord);
			logger.debug(3 + ": "+dataInizioRecord);
			numRow = stmt.executeUpdate();
			//PPactiveConnection.commit(currentActiveConnection);
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}
	private static String QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_FINANZIAMENTI_AGG_NEWRECORD = 
		"UPDATE "+FINANZIAMENTI_AGG.TABLE_NAME+
		" SET " + FINANZIAMENTI_AGG.ID_STATO + " = ?,"+ //+STATI_SCHEDA.CONFERMATO+
		FINANZIAMENTI_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?" +
		" WHERE "+FINANZIAMENTI_AGG.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+FINANZIAMENTI_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	public int updateRecordToPointToNew(String idAggiudicazione, Timestamp dataInizioAggOld,Timestamp dataInizioAggNew, String stato_scheda ) throws SQLException{
		int numRow = -1; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_FINANZIAMENTI_AGG_NEWRECORD);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_FINANZIAMENTI_AGG_NEWRECORD);

			stmt.setString(1, stato_scheda);
			logger.debug(1 + ": "+stato_scheda);
			
			stmt.setTimestamp(2,dataInizioAggNew);
			logger.debug(2 + ": "+dataInizioAggNew);
			
			stmt.setInt(3, Integer.parseInt(idAggiudicazione));
			logger.debug(3 + ": "+idAggiudicazione);
			
			stmt.setTimestamp(4,dataInizioAggOld);
			logger.debug(4 + ": "+dataInizioAggOld);
			numRow = stmt.executeUpdate();
			//PPactiveConnection.commit(currentActiveConnection);
		} finally {
			close(rs, stmt);
		}
		return numRow;	
	}
	/**
	 * metodo per la cancellazione dei finanziamenti relativi ad un'aggiudicazione
	 * 
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws SQLException
	 */
	public void deleteFinanziamenti(long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		
		PreparedStatement stmt = activeConnection.prepareStatement(QUERY_DELETE_FINANZIAMENTI_AGG);
		try{
			stmt.setLong(1, idAggiudicazione);
			stmt.setTimestamp(2, dataInizioAggiudicazione);
			stmt.execute();
		}finally{
			close(null,stmt);
		}
	}
	
	
	/**
	 * metodo per il recupero della lista dei tipi di finanziamenti per un'aggiudicazione
	 * 
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @param ignoraStato TODO
	 * @return List&lt;TipoFinanziamentoBean&gt;
	 * @throws SQLException
	 */
	public List<TipoFinanziamentoBean> loadMany(long idAggiudicazione, Timestamp dataInizioAggiudicazione, boolean ignoraStato) throws SQLException{
		
		String qry = QUERY_SELECT_FINANZIAMENTI_AGG;
		if(!ignoraStato )	
			qry += WHERE_STATO;
		
		PreparedStatement stmt = activeConnection.prepareStatement(qry);
		ResultSet rs = null;
		int index = 1;
		ArrayList<TipoFinanziamentoBean> ris = new ArrayList<TipoFinanziamentoBean>();
		ArrayList<TipoFinanziamentoBean> arrayCompatto = new ArrayList<TipoFinanziamentoBean>();
		TipoFinanziamentoBean nuovoTipo = null;
		try{
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++,dataInizioAggiudicazione);
			rs = stmt.executeQuery();
			while(rs.next()){
				nuovoTipo = new TipoFinanziamentoBean();
				nuovoTipo.setDataFineFin(rs.getTimestamp(FINANZIAMENTI_AGG.DATA_FINE_FINAGG));
				nuovoTipo.setDataInizioAggiudicazione(rs.getTimestamp(FINANZIAMENTI_AGG.DATA_INIZIO_AGGIUDICAZIONE));
				nuovoTipo.setDataInizioFin(rs.getTimestamp(FINANZIAMENTI_AGG.DATA_INIZIO_FINAGG));
				nuovoTipo.setIdAggiudicazione(rs.getLong(FINANZIAMENTI_AGG.ID_AGGIUDICAZIONE));
				nuovoTipo.setIdFinanziamento(rs.getString(TIPO_FINANZIAMENTO.ID_FINANZIAMENTO));
				nuovoTipo.setIdStato(rs.getInt(FINANZIAMENTI_AGG.ID_STATO));
				nuovoTipo.setImporto(rs.getBigDecimal(FINANZIAMENTI_AGG.IMPORTO_FINANZIAMENTO));
				nuovoTipo.setDescrizione(rs.getString(TIPO_FINANZIAMENTO.DESCRIZIONE));
				ris.add(nuovoTipo);
				
			}
			
			
			
			
			for(int j = 0;ris.size() > j;j++) {
				
				if(arrayCompatto.size() == 0) {
					arrayCompatto.add(ris.get(j));
					continue;
				}
				
				for (int k = 0;arrayCompatto.size() > k;k++) {

					if(ris.get(j).getIdFinanziamento().equals(arrayCompatto.get(k).getIdFinanziamento())) {
						arrayCompatto.get(k).setImporto(arrayCompatto.get(k).getImporto().add(ris.get(j).getImporto()));
						break;
					}else if(k == arrayCompatto.size()-1){
						arrayCompatto.add(ris.get(j));
						break;
					}
				}
			}
			
		}finally{
			close(rs, stmt);
		}
		
		return arrayCompatto;
	
	}
	
	
	/**
	 * metodo per la conferma del finanziamento, nel passaggio viene settato lo stato nel bean
	 * 
	 * @param finanziamento TipoFinanziamentoBean
	 * @throws SQLException
	 */
	public void confirm(TipoFinanziamentoBean finanziamento) throws SQLException {
		update(finanziamento, true);
	}

	/**
	 * metodo per il salvataggio di un finanziamento, nel passagio viene settato lo stato nel bean
	 * 
	 * @param finanziamento
	 * @throws SQLException
	 */
	public void save(TipoFinanziamentoBean finanziamento) throws SQLException {
		update(finanziamento, false);
	}

	/**
	 * Aggiunge un nuovo finanziamento
	 * 
	 * param finanziamentoBean finanziamento da aggiungere
	 * throws SQLException
	 */
	private void update(TipoFinanziamentoBean finanziamentoBean, boolean conferma) throws SQLException{
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
			
		PreparedStatement stmt = activeConnection.prepareStatement(createInsertQuery(QUERY_UPDATE_FINANZIAMENTI_AGG,FINANZIAMENTI_AGG.ID_FINANZ_AGG));
		ResultSet rs = null;
		int index = 1;		
		try{			
			finanziamentoBean.setDataInizioFin(getNow());
			stmt.setTimestamp(index++, finanziamentoBean.getDataInizioFin());
			stmt.setString(index++, finanziamentoBean.getIdFinanziamento());
			logger.debug("Finanziamenti: " + finanziamentoBean.getIdFinanziamento() + " - " + finanziamentoBean.getImporto());
			stmt.setBigDecimal(index++, finanziamentoBean.getImporto());
			
			if (conferma){
				stmt.setLong(index++, StatiScheda.CONFERMATO);
				stmt.setTimestamp(index++, getNow());
				finanziamentoBean.setIdStato(StatiScheda.CONFERMATO);
			}else{
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
				stmt.setNull(index++, Types.TIMESTAMP);
				finanziamentoBean.setIdStato(StatiScheda.IN_DEFINIZIONE);
			}
			stmt.setLong(index++, finanziamentoBean.getIdAggiudicazione());
			stmt.setTimestamp(index++, finanziamentoBean.getDataInizioAggiudicazione());
			
			stmt.execute();
			logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(TipoFinanziamentoBean.class, finanziamentoBean));
		}finally{
			close(rs, stmt);
		}
	
	}
	
	//modificare la query di copy per la gestione di tutti i contenziosi
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
			"UPDATE "+FINANZIAMENTI_AGG.TABLE_NAME+ " SET "
			+ FINANZIAMENTI_AGG.ID_STATO+ " = ?,"
			+ FINANZIAMENTI_AGG.DATA_INIZIO_AGGIUDICAZIONE+ " = ? "
			
			+" WHERE "
			+FINANZIAMENTI_AGG.T_ID_AGGIUDICAZIONE+" = ?"
			+" AND "+FINANZIAMENTI_AGG.T_DATA_INIZIO_AGGIUDICAZIONE+" = ?"
			+" AND "+FINANZIAMENTI_AGG.T_ID_STATO + "=" + StatiScheda.CONFERMATO;
		
		String QUERY_COPY_RECORD =
		"INSERT INTO "+FINANZIAMENTI_AGG.TABLE_NAME+" ("
			+FINANZIAMENTI_AGG.ID_FINANZ_AGG	
			+","+FINANZIAMENTI_AGG.ID_AGGIUDICAZIONE
			+","+FINANZIAMENTI_AGG.DATA_INIZIO_AGGIUDICAZIONE
			+","+FINANZIAMENTI_AGG.ID_FINANZIAMENTO
			+","+FINANZIAMENTI_AGG.IMPORTO_FINANZIAMENTO
			+","+FINANZIAMENTI_AGG.DATA_INIZIO_FINAGG
			+","+FINANZIAMENTI_AGG.DATA_FINE_FINAGG
			+","+FINANZIAMENTI_AGG.ID_STATO+" ) "
			+"SELECT "
			+FINANZIAMENTI_AGG.ID_FINANZ_AGG
			+","+FINANZIAMENTI_AGG.ID_AGGIUDICAZIONE
			+","+FINANZIAMENTI_AGG.DATA_INIZIO_AGGIUDICAZIONE
			+","+FINANZIAMENTI_AGG.ID_FINANZIAMENTO
			+","+FINANZIAMENTI_AGG.IMPORTO_FINANZIAMENTO
			+", ?"
			+", ?"
			+", ?"
			+" FROM "+FINANZIAMENTI_AGG.TABLE_NAME
			+" WHERE "
			+FINANZIAMENTI_AGG.ID_AGGIUDICAZIONE+" = ? AND "
			+FINANZIAMENTI_AGG.DATA_INIZIO_AGGIUDICAZIONE+" = ?"
			+ " AND " + FINANZIAMENTI_AGG.ID_STATO + " = " + StatiScheda.CONFERMATO;
		   
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try{
			int index = 1;
			stmt = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD,FINANZIAMENTI_AGG.TABLE_NAME));
			stmt.setTimestamp(index++, getNow()); //data_inizio_appalto
			stmt.setNull(index++, Types.TIMESTAMP); // data_fine_appalto
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
				logger.debug("FINANZIAMENTO_MANAGER.copyRecord: Nessun record da copiare");
				return true;
			}
		}
		finally{
			close(null, stmt2);
			close(null, stmt);
		}
	}		
	
	/**
	 * metodo per la cancellazione di un record
	 * 
	 * @param idAggiudicazione String
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws SQLException
	 */
	public void deleteRecord(String idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
		deleteFinanziamenti(Long.parseLong(idAggiudicazione), dataInizioAggiudicazione);
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
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_FINANZIAMENTI);
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
	
}
