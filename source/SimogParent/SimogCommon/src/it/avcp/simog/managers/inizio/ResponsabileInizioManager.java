package it.avcp.simog.managers.inizio;


import it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.RESP_INIZIO;
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

import org.apache.log4j.Logger;

/**
 * Classe per la gestione dei dati per i responsabili inizio
 *
 */
public class ResponsabileInizioManager extends AccessiDB implements IAnnullamentoMulti {

	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public ResponsabileInizioManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
		
	}
	
	public static String ELIMINA_RESP_INIZIO = 
		"DELETE FROM " + RESP_INIZIO.TABLE_NAME
		+ " WHERE "
		+ RESP_INIZIO.ID_INIZIO + " = ? "
		+ " AND " + RESP_INIZIO.DATA_INIZIO_INIZIO + " = ? ";
	
	
	public static String INSERT_RESP_INIZIO = 
		"INSERT INTO " +  RESP_INIZIO.TABLE_NAME + "(" 
		+ RESP_INIZIO.DATA_INIZIO_RECORD
		+ ", " + RESP_INIZIO.ID_INIZIO
		+ ", " + RESP_INIZIO.DATA_INIZIO_INIZIO
		+ ", " + RESP_INIZIO.ID_RESPONSABILE
		+ ", " + RESP_INIZIO.DATA_INIZIO_RES
		+ ", " + RESP_INIZIO.ID_STATO
		+ ", " + RESP_INIZIO.DATA_FINE_RECORD
		+ ", " + RESP_INIZIO.ID_RUOLO
		+ ") VALUES ("
		+ "?, ?, ?, ?, ?, ?, ?, ?)";
	
	
//	public static String SELECT_RESP_INIZIO = 
//		" SELECT "
//	    + SOGGETTI_RESPONSABILI.T_ID_RESPONSABILE
//	    + ", " + RUOLI_RESPONSABILE.T_DESCRIZIONE+" AS "+RUOLI_RESPONSABILE.TABLE_NAME+RUOLI_RESPONSABILE.DESCRIZIONE
//		+ ", " + SOGGETTI_RESPONSABILI.T_DATA_INIZIO_RES
//		+ ", " + RESP_INIZIO.T_ID_RUOLO
//		+ ", " + SOGGETTI_RESPONSABILI.COGNOME
//		+ ", " + SOGGETTI_RESPONSABILI.NOME
//		+ ", " + SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE
//		+ " FROM " + RESP_INIZIO.TABLE_NAME
//		+ ", " + SOGGETTI_RESPONSABILI.TABLE_NAME
//		+ ", " + RUOLI_RESPONSABILE.TABLE_NAME 
//		+ " WHERE " + RESP_INIZIO.ID_INIZIO + " = ? "
//		+ " AND " + RESP_INIZIO.DATA_INIZIO_INIZIO + " = ? "
//		+ " AND " + RESP_INIZIO.T_ID_RESPONSABILE + " = " + SOGGETTI_RESPONSABILI.T_ID_RESPONSABILE
//		+ " AND " + RESP_INIZIO.T_DATA_INIZIO_RES + " = " + SOGGETTI_RESPONSABILI.T_DATA_INIZIO_RES
//		+ " AND " + RESP_INIZIO.T_ID_RUOLO + "=" + RUOLI_RESPONSABILE.T_ID_RUOLO
//		+ " AND " + RESP_INIZIO.ID_STATO + " <> " + StatiScheda.ANNULLAMENTO_RICHIESTA;
	
	
	/**
	 * metodo per la cancellazione di un record
	 * 
	 * @param idInizioLavori long
	 * @param dataInizioLavori Timestamp
	 * @throws SQLException
	 */
	public void deleteRecord(long idInizioLavori, Timestamp dataInizioLavori)throws SQLException{
		PreparedStatement stmt = null;
		int index = 1;
		try{
			stmt = activeConnection.prepareStatement(ELIMINA_RESP_INIZIO);
			stmt.setLong(index++, idInizioLavori);
			stmt.setTimestamp(index++, dataInizioLavori);
			stmt.execute();
		}finally{
			close(null,stmt);
		}
	}
	
	

	private final String SELECT_RESP_INIZIO_NEW = 
		" SELECT "
	    + SOGGETTI_RESPONSABILI.TABLE_NAME + ".* "
	    + ", " + RUOLI_RESPONSABILE.T_DESCRIZIONE+" AS "+RUOLI_RESPONSABILE.TABLE_NAME+RUOLI_RESPONSABILE.DESCRIZIONE
		+ ", " + RESP_INIZIO.T_ID_RUOLO
		+ ", " + RESP_INIZIO.T_ID_STATO
		+ " FROM " 
		+ RESP_INIZIO.TABLE_NAME + ", " 
		+ RUOLI_RESPONSABILE.TABLE_NAME + ", " 
		+ SOGGETTI_RESPONSABILI.TABLE_NAME
		+ " WHERE " + RESP_INIZIO.ID_INIZIO + " = ? "
		+ " AND " + RESP_INIZIO.DATA_INIZIO_INIZIO + " = ? "
		+ " AND " + RESP_INIZIO.T_ID_RESPONSABILE + " = " + SOGGETTI_RESPONSABILI.T_ID_RESPONSABILE
		+ " AND " + RESP_INIZIO.T_DATA_INIZIO_RES + " = " + SOGGETTI_RESPONSABILI.T_DATA_INIZIO_RES
		+ " AND " + RESP_INIZIO.T_ID_RUOLO + "=" + RUOLI_RESPONSABILE.T_ID_RUOLO;
	
	private final String WHERE_STATO = " AND (" + RESP_INIZIO.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + RESP_INIZIO.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	
	/**
	 * metodo per il recupero dei responsabili associati ad una fase di inizio lavori
	 * 
	 * @param idIniLavori Long
	 * @param dataIniLavori Timestamp
	 * @param ignoraStato TODO
	 * @return List&lt;ResponsabileBean&gt;
	 * @throws SQLException
	 */
	public List<ResponsabileBean> loadMany(Long idIniLavori,Timestamp dataIniLavori, boolean ignoraStato)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<ResponsabileBean> ril = new ArrayList<ResponsabileBean>();
		try{
			String qry = SELECT_RESP_INIZIO_NEW;
			if(!ignoraStato)
				qry += WHERE_STATO;
			
			stmt = activeConnection.prepareStatement(qry);
			int index = 1;
			stmt.setLong(index++, idIniLavori);
			stmt.setTimestamp(index++, dataIniLavori);
			rs = stmt.executeQuery();
			while(rs.next()){
				ResponsabileBean ri = new ResponsabileBean();
				
//				ri.setDataInizioScheda(rs.getTimestamp(RESP_INIZIO.T_DATA_INIZIO_INIZIO));
//				ri.setIdScheda(rs.getLong(RESP_INIZIO.T_ID_INIZIO));
				ri.setIdRuolo(rs.getInt(RESP_INIZIO.ID_RUOLO));
				ri.setIdStato(rs.getInt(RESP_INIZIO.ID_STATO));
				ri.setDescrizioneRuolo(rs.getString(RUOLI_RESPONSABILE.TABLE_NAME+RUOLI_RESPONSABILE.DESCRIZIONE));
				/* nested bean */
					SoggettoResponsabileBean srb = new SoggettoResponsabileBean();
					srb.setCap(rs.getString(SOGGETTI_RESPONSABILI.CAP));
					srb.setCodiceFiscaleResponsabile(rs.getString(SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE));
					srb.setCognome(rs.getString(SOGGETTI_RESPONSABILI.COGNOME));
					srb.setComuneIstat(rs.getString(SOGGETTI_RESPONSABILI.COMUNE_ISTAT));
					srb.setDataInizioRes(rs.getTimestamp(SOGGETTI_RESPONSABILI.DATA_INIZIO_RES));
					srb.setDataFineRes(rs.getTimestamp(SOGGETTI_RESPONSABILI.DATA_FINE_RES));
					srb.setEmail(rs.getString(SOGGETTI_RESPONSABILI.EMAIL));
					srb.setFax(rs.getString(SOGGETTI_RESPONSABILI.FAX));
					srb.setIdResponsabile(rs.getLong(SOGGETTI_RESPONSABILI.ID_RESPONSABILE));
					srb.setIndirizzo(rs.getString(SOGGETTI_RESPONSABILI.INDIRIZZO));
					srb.setNome(rs.getString(SOGGETTI_RESPONSABILI.NOME));
					srb.setTelefono(rs.getString(SOGGETTI_RESPONSABILI.TELEFONO));
					
				/**/
				ri.setSoggettoResponsabile(srb);
				ril.add(ri);
			}
		}finally{
			close(rs,stmt);
		}
		ril.trimToSize();
		return ril;
	}
	
	private void update( ResponsabileBean respBean, boolean confirm)throws SQLException{
		PreparedStatement stmt = null;
		logger.debug("saving bean: " + ObjectIntrospector.propertiesInfo(ResponsabileBean.class, respBean));
		ResultSet rs = null;
		int index = 1;
		try{
			//e inserisce una nuova ag
			stmt = activeConnection.prepareStatement(INSERT_RESP_INIZIO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, respBean.getIdScheda());
			stmt.setTimestamp(index++, respBean.getDataInizioScheda());
			stmt.setLong(index++, respBean.getSoggettoResponsabile().getIdResponsabile());
			stmt.setTimestamp(index++, respBean.getSoggettoResponsabile().getDataInizioRes());
			if(confirm){
				stmt.setInt(index++, StatiScheda.CONFERMATO);
				stmt.setTimestamp(index++, getNow());
			}
			else{
				stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE);
				stmt.setNull(index++, Types.TIMESTAMP);
			}
			stmt.setInt(index++, respBean.getIdRuolo());
		
			stmt.execute();
			
		}finally{
			close(rs, stmt);
			
		}
	}
	
	 
	/**
	 * metodo per la conferma di un responsabile
	 * 
	 * @param respInizio ResponsabileBean
	 * @throws SQLException
	 */
	public void confirm(ResponsabileBean respInizio) throws SQLException{
		update(respInizio, true);
		
	}
	
	/**
	 * metodo per il salvataggio di un responsabile
	 * 
	 * @param respInizio ResponsabileBean
	 * @throws SQLException
	 */
	public void save(ResponsabileBean respInizio) throws SQLException{
		update(respInizio, false);
		
	}
	
	
	
	/**
	 * metodo per la storicizzazione di un record
	 * 
	 * @param idInizioLavori long
	 * @param dataInizioLavori Timestamp
	 * @param vecchiaData Timestamp
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean copyRecord(long idInizioLavori,Timestamp dataInizioLavori, Timestamp vecchiaData) throws SQLException{
		String QUERY_UPDATE_OLD_RECORD =
			"UPDATE "+RESP_INIZIO.TABLE_NAME+ " SET "
			+ RESP_INIZIO.ID_STATO+ " = ?, "
			+ RESP_INIZIO.DATA_INIZIO_INIZIO+ " = ? "
			
			+" WHERE "
			+RESP_INIZIO.T_ID_INIZIO+" = ?"
			+" AND "+RESP_INIZIO.T_DATA_INIZIO_INIZIO+" = ?"
			+" AND "+RESP_INIZIO.T_ID_STATO + "=" + StatiScheda.CONFERMATO;
		
		String QUERY_COPY_RECORD =
		"INSERT INTO "+RESP_INIZIO.TABLE_NAME+" ("
			+ RESP_INIZIO.ID_RECORD
			+ ", " +  RESP_INIZIO.ID_INIZIO
			+ ", " + RESP_INIZIO.DATA_INIZIO_INIZIO
			+ ", " + RESP_INIZIO.ID_RESPONSABILE
			+ ", " + RESP_INIZIO.DATA_INIZIO_RES
			+ ", " + RESP_INIZIO.ID_RUOLO
			+ ", " + RESP_INIZIO.DATA_INIZIO_RECORD
			+ ", " + RESP_INIZIO.DATA_FINE_RECORD
			+ ", " + RESP_INIZIO.ID_STATO + " ) "
			+"SELECT "
			+ RESP_INIZIO.ID_RECORD
			+ ", " +  RESP_INIZIO.ID_INIZIO
			+ ", " + RESP_INIZIO.DATA_INIZIO_INIZIO
			+ ", " + RESP_INIZIO.ID_RESPONSABILE
			+ ", " + RESP_INIZIO.DATA_INIZIO_RES
			+ ", " + RESP_INIZIO.ID_RUOLO
			+ ", ?"
			+ ", ?"
			+ ", ?"
			+" FROM "+RESP_INIZIO.TABLE_NAME
			+" WHERE "
			+RESP_INIZIO.ID_INIZIO+" = ?"
			+" AND "+RESP_INIZIO.DATA_INIZIO_INIZIO+" = ? "
			+" AND "+RESP_INIZIO.ID_STATO+" = "+StatiScheda.CONFERMATO;
			int index = 1;
			PreparedStatement stmt = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD,RESP_INIZIO.TABLE_NAME));
			PreparedStatement stmt2 = activeConnection.prepareStatement(QUERY_UPDATE_OLD_RECORD);
			try{
				logger.debug("richiesta annullamento:" + idInizioLavori + "---" + dataInizioLavori + "---" + vecchiaData);
				stmt.setTimestamp(index++, getNow());
				stmt.setNull(index++, Types.TIMESTAMP);
				stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE);
				stmt.setLong(index++, idInizioLavori);
				stmt.setTimestamp(index++, dataInizioLavori);
				int rowsCopied = stmt.executeUpdate();
				
				if(rowsCopied > 0){
					index = 1;
					stmt2.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA); //stato scheda
					stmt2.setTimestamp(index++, vecchiaData); //data_inizio_aggiudicazione
					stmt2.setLong(index++, idInizioLavori);
					stmt2.setTimestamp(index++, dataInizioLavori);
					rowsCopied = stmt2.executeUpdate();
					return (rowsCopied>0);
				}
				
				
				logger.debug("RESP_INIZIO_MANAGER.copyRecord: Nessun record da copiare");
				return true;
			}finally{
				close(null, stmt);
				close(null, stmt2);
			}
	}
	
	
	public static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO = 
		"UPDATE "+RESP_INIZIO.TABLE_NAME+
		" SET " + RESP_INIZIO.ID_STATO + " = ?,"
		+ RESP_INIZIO.DATA_FINE_RECORD + " = " + buildGetDate()+
		" WHERE "+RESP_INIZIO.ID_INIZIO + " = ?"+
		" AND "+RESP_INIZIO.DATA_INIZIO_INIZIO + " = ?";
	
	/**
	 * metodo per l'aggiornamento di un record
	 * 
	 * @param idInizioLavori long
	 * @param dataInizioLavori Timestamp
	 * @param stato_scheda String
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int updateRecord(long idInizioLavori, Timestamp dataInizioLavori, String stato_scheda ) throws SQLException{
		int numRow=-1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);

	
			stmt.setString(1, stato_scheda);
			stmt.setLong(2, idInizioLavori);
			stmt.setTimestamp(3,dataInizioLavori);
			numRow = stmt.executeUpdate();
		
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti#annulla(long, java.sql.Timestamp)
	 */
	public boolean annulla(long idInizioLavori, Timestamp dataInizioLavori) throws SQLException {
		return _annulla(idInizioLavori, dataInizioLavori);
	}
	
	/**
	 * @param idInizioLavori
	 * @param dataInizioLavori
	 * @throws SQLException
	 */
	private boolean _annulla(long idInizioLavori, Timestamp dataInizioLavori) throws SQLException{
		PreparedStatement stmt = null;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_RESPONSABILI_INIZIO);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idInizioLavori);
			stmt.setTimestamp(index++, dataInizioLavori);
			return stmt.executeUpdate() > 0;
		}finally {
			close(null,stmt);
		}		
	}	
	
		

}
