package it.avcp.simog.managers.collaudo;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.RESP_COLL;
import it.avlp.simog.db.generated.RUOLI_RESPONSABILE;
import it.avlp.simog.db.generated.SOGGETTI_RESPONSABILI;
import it.avlp.simog.util.ObjectIntrospector;

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
 * questo manager si occupa di caricare un bean responsabile con i dati di un responsabile collaudo
 * 
 */
public class ResponsabileCollManager extends AccessiDB implements IAnnullamentoMulti {
	
	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public ResponsabileCollManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	private final String QUERY_SELECT_RESP_COLL =
		"SELECT "
		+ RESP_COLL.TABLE_NAME+".*,"
		+ RUOLI_RESPONSABILE.T_DESCRIZIONE+" AS "+RUOLI_RESPONSABILE.TABLE_NAME+RUOLI_RESPONSABILE.DESCRIZIONE+","
		+ SOGGETTI_RESPONSABILI.T_COGNOME+","
		+ SOGGETTI_RESPONSABILI.T_NOME+","
		+ SOGGETTI_RESPONSABILI.T_CODICE_FISCALE_RESPONSABILE+","
		+ SOGGETTI_RESPONSABILI.T_TELEFONO+","
		+ SOGGETTI_RESPONSABILI.T_EMAIL+","
		+ SOGGETTI_RESPONSABILI.T_FAX + ", "
		+ SOGGETTI_RESPONSABILI.T_CAP+ ", "
		+ SOGGETTI_RESPONSABILI.T_INDIRIZZO+ ", "
		+ SOGGETTI_RESPONSABILI.T_COMUNE_ISTAT
		+" FROM " 
		+ RESP_COLL.TABLE_NAME + "," 
		+ RUOLI_RESPONSABILE.TABLE_NAME + ","
		+ SOGGETTI_RESPONSABILI.TABLE_NAME
		+ " WHERE "
		+ RESP_COLL.T_ID_COLLAUDO + " = ? "
		+ " AND " + RESP_COLL.T_DATA_INIZIO_COLL + " = ? "
		// PP inutile + " AND " + RESP_COLL.T_ID_STATO + "!=" + StatiScheda.ELIMINATO
		+ " AND " + RESP_COLL.T_ID_RUOLO + "=" + RUOLI_RESPONSABILE.T_ID_RUOLO
		+ " AND " + SOGGETTI_RESPONSABILI.T_ID_RESPONSABILE + "="+RESP_COLL.T_ID_RESPONSABILE
	    + " AND " + SOGGETTI_RESPONSABILI.T_DATA_INIZIO_RES + " = " + RESP_COLL.T_DATA_INIZIO_RES;

		private final String WHERE_STATO = " AND ( " + RESP_COLL.T_ID_STATO + "=" + StatiScheda.CONFERMATO
		+ " OR " + RESP_COLL.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE+" ) "; 

	/**
	 * metodo per caricare una lista di responsabili associati ad un collaudo
	 * 
	 * @param idCollaudo Long
	 * @param dataIniCollaudo Timestamp
	 * @param ignoraStato TODO
	 * @return List&lt;ResponsabileBean&gt;
	 * @throws SQLException
	 */
	public List<ResponsabileBean> load(Long idCollaudo,Timestamp dataIniCollaudo, boolean ignoraStato)throws SQLException{
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ResponsabileBean rb = null;
		List<ResponsabileBean> lista = new ArrayList<ResponsabileBean>();
		try{
			String qry = QUERY_SELECT_RESP_COLL;
			if(!ignoraStato)
				qry += WHERE_STATO;
			
			stmt = activeConnection.prepareStatement(qry);
			int index = 1;
			stmt.setLong(index++, idCollaudo);
			stmt.setTimestamp(index++, dataIniCollaudo);
			rs = stmt.executeQuery();
			while(rs.next()){
				rb = new ResponsabileBean();
				rb.setIdScheda(rs.getLong(RESP_COLL.ID_COLLAUDO));
				rb.setDataInizioScheda(rs.getTimestamp(RESP_COLL.DATA_INIZIO_COLL));
				rb.setIdStato(rs.getInt(RESP_COLL.ID_STATO));
				rb.setIdRuolo(rs.getInt(RESP_COLL.ID_RUOLO));
				rb.setDescrizioneRuolo(rs.getString(RUOLI_RESPONSABILE.TABLE_NAME+RUOLI_RESPONSABILE.DESCRIZIONE));
				/* setting inner(nested) bean */
					SoggettoResponsabileBean sb = new SoggettoResponsabileBean();
					sb.setIdResponsabile(rs.getLong(RESP_COLL.ID_RESPONSABILE));
					sb.setDataInizioRes(rs.getTimestamp(RESP_COLL.DATA_INIZIO_RES));
					sb.setCognome(rs.getString(SOGGETTI_RESPONSABILI.COGNOME));
					sb.setNome(rs.getString(SOGGETTI_RESPONSABILI.NOME));
					sb.setCodiceFiscaleResponsabile(rs.getString(SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE));
					sb.setTelefono(rs.getString(SOGGETTI_RESPONSABILI.TELEFONO));
					sb.setEmail(rs.getString(SOGGETTI_RESPONSABILI.EMAIL));
					sb.setFax(rs.getString(SOGGETTI_RESPONSABILI.FAX));
					sb.setCap(rs.getString(SOGGETTI_RESPONSABILI.CAP));
					sb.setIndirizzo(rs.getString(SOGGETTI_RESPONSABILI.INDIRIZZO));
					sb.setComuneIstat(rs.getString(SOGGETTI_RESPONSABILI.COMUNE_ISTAT));				
					rb.setSoggettoResponsabile(sb);
				lista.add(rb);
				/*END*/
			}
		}finally{
			close(rs,stmt);
		}		
		return lista;
	}
	
	private final String DELETE_RESP_COLL = "DELETE FROM " + RESP_COLL.TABLE_NAME
		+ " WHERE " + RESP_COLL.ID_COLLAUDO + " =? "
		+ " AND " + RESP_COLL.DATA_INIZIO_COLL + " =? ";
	
	
	/**
	 * metodo per la cancellazione di un record
	 * 
	 * @param idCollaudo
	 * @param dataInizioCollaudo Timestamp
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int deleteRecord(long idCollaudo , Timestamp dataInizioCollaudo)throws SQLException{
		PreparedStatement stmt = null;
		try{
			stmt = activeConnection.prepareStatement(DELETE_RESP_COLL);
			stmt.setLong(1, idCollaudo);
			stmt.setTimestamp(2, dataInizioCollaudo);
			return stmt.executeUpdate();
		}finally{
			close(null, stmt);
		}
	}
	
	private final String INSERT_RESP_COLL = "INSERT INTO " + RESP_COLL.TABLE_NAME + "("
		+ RESP_COLL.DATA_INIZIO_RECORD
		+ ", " + RESP_COLL.ID_COLLAUDO
		+ ", " + RESP_COLL.DATA_INIZIO_COLL
		+ ", " + RESP_COLL.ID_RESPONSABILE
		+ ", " + RESP_COLL.DATA_INIZIO_RES
		+ ", " + RESP_COLL.ID_STATO
		+ ", " + RESP_COLL.DATA_FINE_RECORD
		+ ", " + RESP_COLL.ID_RUOLO
		+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
	/**
	 * metodo per l'aggiornamento di un responsabile collaudo
	 * 
	 * @param bean ResponsabileBean
	 * @param confirm  boolean � un flag nel caso sia true si esegue anche la conferma del responsabile, altrimenti semplice aggiornamento
	 * @throws SQLException
	 */
	public void update(ResponsabileBean bean , boolean confirm)throws SQLException{
		PreparedStatement stmt = null;
		logger.debug("saving bean: " + ObjectIntrospector.propertiesInfo(ResponsabileBean.class, bean));
		ResultSet rs = null;
		int index = 1;
		try{
			stmt = activeConnection.prepareStatement(INSERT_RESP_COLL);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, bean.getIdScheda());//dati del collaudo
			stmt.setTimestamp(index++, bean.getDataInizioScheda());//dati del collaudo
			stmt.setLong(index++, bean.getSoggettoResponsabile().getIdResponsabile());
			stmt.setTimestamp(index++, bean.getSoggettoResponsabile().getDataInizioRes());
			if(confirm){
				stmt.setInt(index++, StatiScheda.CONFERMATO);
				stmt.setTimestamp(index++, getNow());
			}
			else{
				stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE);
				stmt.setNull(index++, Types.TIMESTAMP);
			}
			stmt.setInt(index++, bean.getIdRuolo());
			stmt.execute();
		}finally{
			close(rs, stmt);
			
		}
	}
	
	/**
	 * metodo per il salvataggio di un responsabile di collaudo
	 * 
	 * @param bean ResponsabileBean
	 * @throws SQLException
	 */
	public void save(ResponsabileBean bean)throws SQLException{
		update(bean, false);
	}
	
	/**
	 * metodo per la conferma di un responsabile
	 * 
	 * @param bean ResponsabileBean
	 * @throws SQLException
	 */
	public void confirm(ResponsabileBean bean)throws SQLException{
		update(bean, true);
	}
	
	/**
	 * metodo per la storicizzazione di un record
	 * 
	 * @param idCollaudo long
	 * @param dataInizioCollaudo Timestamp
	 * @param vecchiaData Timestamp
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean copyRecord(long idCollaudo , Timestamp dataInizioCollaudo , Timestamp vecchiaData)throws SQLException{
		String QUERY_UPDATE_OLD_RECORD = "UPDATE " + RESP_COLL.TABLE_NAME + " SET "
		+ RESP_COLL.ID_STATO + " =?, "
		+ RESP_COLL.DATA_INIZIO_COLL + " =? "
		+ " WHERE " + RESP_COLL.ID_COLLAUDO + " =? AND "
		+ RESP_COLL.DATA_INIZIO_COLL + " =? AND "
		+ RESP_COLL.ID_STATO + "=" + StatiScheda.CONFERMATO;
		
		String QUERY_COPY_RECORD = "INSERT INTO " + RESP_COLL.TABLE_NAME + "("
		+ RESP_COLL.ID_RECORD
		+ ", " + RESP_COLL.ID_COLLAUDO
		+ ", " + RESP_COLL.DATA_INIZIO_COLL
		+ ", " + RESP_COLL.ID_RESPONSABILE
		+ ", " + RESP_COLL.DATA_INIZIO_RES
		+ ", " + RESP_COLL.ID_RUOLO
		+ ", " + RESP_COLL.DATA_INIZIO_RECORD
		+ ", " + RESP_COLL.DATA_FINE_RECORD
		+ ", " + RESP_COLL.ID_STATO + ")"
		+ " SELECT "
		+ RESP_COLL.ID_RECORD
		+ ", " + RESP_COLL.ID_COLLAUDO
		+ ", " + RESP_COLL.DATA_INIZIO_COLL
		+ ", " + RESP_COLL.ID_RESPONSABILE
		+ ", " + RESP_COLL.DATA_INIZIO_RES
		+ ", " + RESP_COLL.ID_RUOLO
		+ ", ?"
		+ ", ?"
		+ ", ?"
		+ " FROM " + RESP_COLL.TABLE_NAME
		+ " WHERE " + RESP_COLL.ID_COLLAUDO + " =? AND "
		+ RESP_COLL.DATA_INIZIO_COLL + " =? AND "
		+ RESP_COLL.ID_STATO + "=" + StatiScheda.CONFERMATO;
		
		PreparedStatement oldRecord = null;
		PreparedStatement copyRecord = null;
		int index = 1;
		try{
			oldRecord = activeConnection.prepareStatement(QUERY_UPDATE_OLD_RECORD);
			copyRecord = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD, RESP_COLL.TABLE_NAME));
			copyRecord.setTimestamp(index++, getNow());
			copyRecord.setNull(index++, Types.TIMESTAMP);
			copyRecord.setInt(index++, StatiScheda.IN_DEFINIZIONE);
			copyRecord.setLong(index++, idCollaudo);
			copyRecord.setTimestamp(index++, dataInizioCollaudo);
			int rowsCopied = copyRecord.executeUpdate();
			
			if(rowsCopied > 0){
				index = 1;
				oldRecord.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA);
				oldRecord.setTimestamp(index++, vecchiaData);
				oldRecord.setLong(index++, idCollaudo);
				oldRecord.setTimestamp(index++, dataInizioCollaudo);
				rowsCopied = oldRecord.executeUpdate();
				return (rowsCopied > 0);
			}
			return true;
		}finally{
			close(null, oldRecord);
			close(null, copyRecord);
		}
	}
	
	private final String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO = "UPDATE "+RESP_COLL.TABLE_NAME+
	" SET " + RESP_COLL.ID_STATO + " = ?,"
	+ RESP_COLL.DATA_FINE_RECORD + " = " + buildGetDate() +	
	" WHERE "+RESP_COLL.ID_COLLAUDO + " = ?"+
	" AND "+RESP_COLL.DATA_INIZIO_COLL+ " = ?";
	
	/**
	 * metodo per l'aggionramento di un record
	 * 
	 * @param idCollaudo long
	 * @param dataInizioCollaudo Timestamp
	 * @param statoScheda String
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int updateRecord(long idCollaudo , Timestamp dataInizioCollaudo , String statoScheda)throws SQLException{
		int numRow = -1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);

			stmt.setString(1, statoScheda);
			stmt.setLong(2, idCollaudo);
			stmt.setTimestamp(3,dataInizioCollaudo);
			numRow = stmt.executeUpdate();
		}finally {
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

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti#annulla(long, java.sql.Timestamp)
	 */
	public boolean annulla(long idCollaudo, Timestamp dataInizioCollaudo) throws SQLException {
		return _annulla(idCollaudo, dataInizioCollaudo);
		
	}
	/**
	 * @param idCollaudo
	 * @param dataInizioCollaudo
	 * @return
	 * @throws SQLException
	 */
	private boolean _annulla(long idCollaudo, Timestamp dataInizioCollaudo) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_RESPONSABILI_COLL);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idCollaudo);
			stmt.setTimestamp(index++, dataInizioCollaudo);
			someRowAffected = stmt.executeUpdate() > 0;
			return someRowAffected;
		}finally {
			close(null,stmt);
		}		
	}	
}
