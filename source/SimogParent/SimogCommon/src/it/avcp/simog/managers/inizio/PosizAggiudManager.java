package it.avcp.simog.managers.inizio;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.inizio.PosizioneAggiudicatarioBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.POSIZ_AGGIUD;
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
 * Classe per la gestione delle posizione aggiudicatario
 *
 */
public class PosizAggiudManager extends AccessiDB implements IAnnullamentoMulti {
	public static String CLAZZ = "PosizAggiudManager";
	//other fields
	//query con join per ricavare i dati non esiste un manager dedicato
	
	
	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public PosizAggiudManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
		
	}
	private final String SELECT_POSIZ_AGGIUD = 
		" SELECT " + 
		POSIZ_AGGIUD.TABLE_NAME + ".*, " +
		SOGGETTI_PARTECIPANTI.T_ID_STATO + " AS IDPAESI, " +
		SOGGETTI_PARTECIPANTI.TABLE_NAME + ".* " + 
		" FROM " + POSIZ_AGGIUD.TABLE_NAME + ", " + SOGGETTI_PARTECIPANTI.TABLE_NAME + 
		" WHERE " + POSIZ_AGGIUD.T_ID_INIZIO + " = ? " +
		" AND " + POSIZ_AGGIUD.T_DATA_INIZIO_INIZIO + " = ? " + 
		" AND " + POSIZ_AGGIUD.T_ID_SOGGETTO_PARTECIPANTE + " = " + SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE + 
		" AND " + POSIZ_AGGIUD.T_DATA_INIZIO_SOGG + " = " + SOGGETTI_PARTECIPANTI.T_DATA_INIZIO_SOGG;
	
	private final String WHERE_STATO = " AND (" + POSIZ_AGGIUD.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + POSIZ_AGGIUD.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	/**
	 * metodo per recuperare la lista delle posizione aggiudicatario per una fase inizio
	 * lavori di cui id
	 * 
	 * @param idIniLavori Long
	 * @param dataIniLavori Timestamp
	 * @param ignoraStato TODO
	 * @return List&lt;PosizioneAggiudicatarioBean&gt;
	 * @throws SQLException
	 */
	public List<PosizioneAggiudicatarioBean> loadMany(Long idIniLavori,Timestamp dataIniLavori, boolean ignoraStato)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<PosizioneAggiudicatarioBean> pal = new ArrayList<PosizioneAggiudicatarioBean>();
		try{
			String qry = SELECT_POSIZ_AGGIUD;
			if(!ignoraStato)
				qry += WHERE_STATO;
			
			stmt = activeConnection.prepareStatement(qry);
			int index = 1;
			stmt.setLong(index++, idIniLavori);
			stmt.setTimestamp(index++, dataIniLavori);
			rs = stmt.executeQuery();
			while(rs.next()){
				PosizioneAggiudicatarioBean pa = new PosizioneAggiudicatarioBean();
				pa.setCodiceCassa(rs.getString(POSIZ_AGGIUD.CODICE_CASSA));
				pa.setCodiceINAIL(rs.getString(POSIZ_AGGIUD.CODICE_INAIL));
				pa.setCodiceINPS(rs.getString(POSIZ_AGGIUD.CODICE_INPS));
				pa.setDataInizioLavori(rs.getTimestamp(POSIZ_AGGIUD.DATA_INIZIO_INIZIO));
				pa.setIdInizioLavori(rs.getLong(POSIZ_AGGIUD.ID_INIZIO));
				pa.setIdStato(rs.getLong(POSIZ_AGGIUD.ID_STATO));
				/* nested bean */
					SoggettoPartecipanteBean spb = new SoggettoPartecipanteBean();
					/* setting */
					spb.setCameraCommercio(rs.getString(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO));
					spb.setCap(rs.getString(SOGGETTI_PARTECIPANTI.CAP));
					spb.setCfRappresentante(rs.getString(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE));
					spb.setCitta(rs.getString(SOGGETTI_PARTECIPANTI.CITTA));
					spb.setCivico(rs.getString(SOGGETTI_PARTECIPANTI.CIVICO));
					spb.setCodiceFiscale(rs.getString(SOGGETTI_PARTECIPANTI.CODICE_FISCALE));
					spb.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
					spb.setDataInizioSogg(rs.getTimestamp(SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG));
					spb.setDenominazione(rs.getString(SOGGETTI_PARTECIPANTI.DENOMINAZIONE));
					spb.setIdSoggettoPartecipante(rs.getLong(SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE));
					spb.setIndirizzo(rs.getString(SOGGETTI_PARTECIPANTI.INDIRIZZO));
					spb.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
					spb.setPartitaIva(rs.getString(SOGGETTI_PARTECIPANTI.PARTITA_IVA));
					spb.setProvincia(rs.getString(SOGGETTI_PARTECIPANTI.PROVINCIA));
					spb.setId_stato(rs.getString("IDPAESI"));		/*UN-X-XX*/
					spb.setId_stato(rs.getString("IDPAESI"));		/*UN*/
				pa.setSoggettoPartecipante(spb);
				pal.add(pa);
			}
		}finally{
			close(rs,stmt);
		}
		pal.trimToSize();
		return pal;
	}


	public static String ELIMINA_POSIZ_AGGIUD = 
		"DELETE FROM " + POSIZ_AGGIUD.TABLE_NAME
		+ " WHERE "
		+ POSIZ_AGGIUD.ID_INIZIO + " = ? "
		+ " AND " + POSIZ_AGGIUD.DATA_INIZIO_INIZIO + " = ? " ;

	public static String INSERT_POSIZ_AGGIUD = 
		"INSERT INTO " +  POSIZ_AGGIUD.TABLE_NAME + " (" 
		+ POSIZ_AGGIUD.DATA_INIZIO_RECORD
		+ ", " + POSIZ_AGGIUD.ID_INIZIO
		+ ", " + POSIZ_AGGIUD.DATA_INIZIO_INIZIO
		+ ", " + POSIZ_AGGIUD.ID_SOGGETTO_PARTECIPANTE
		+ ", " + POSIZ_AGGIUD.DATA_INIZIO_SOGG
		+ ", " + POSIZ_AGGIUD.ID_STATO
		+ ", " + POSIZ_AGGIUD.DATA_FINE_RECORD
		+ ", " + POSIZ_AGGIUD.CODICE_INAIL
		+ ", " + POSIZ_AGGIUD.CODICE_INPS
		+ ", " + POSIZ_AGGIUD.CODICE_CASSA
		+ ") VALUES ("
		+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	
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
			stmt = activeConnection.prepareStatement(ELIMINA_POSIZ_AGGIUD);
			stmt.setLong(index++, idInizioLavori);
			stmt.setTimestamp(index++, dataInizioLavori);
			stmt.execute();
		}finally{
			close(null,stmt);
		}
	}
	

	private void update(PosizioneAggiudicatarioBean posizBean, boolean confirm)throws SQLException{
		PreparedStatement stmt = null;
		
		ResultSet rs = null;
		int index = 1;
		try{
			//e inserisce una nuova ag
			stmt = activeConnection.prepareStatement(INSERT_POSIZ_AGGIUD);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, posizBean.getIdInizioLavori());
			stmt.setTimestamp(index++, posizBean.getDataInizioLavori());
			stmt.setLong(index++, posizBean.getSoggettoPartecipante().getIdSoggettoPartecipante());
			stmt.setTimestamp(index++, posizBean.getSoggettoPartecipante().getDataInizioSogg());
			if(confirm){
				stmt.setInt(index++, StatiScheda.CONFERMATO);
				stmt.setTimestamp(index++, getNow());
			}
			else{
				stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE);
				stmt.setNull(index++, Types.TIMESTAMP);
			}
			stmt.setString(index++, posizBean.getCodiceINAIL());
			stmt.setString(index++, posizBean.getCodiceINPS());
			stmt.setString(index++, posizBean.getCodiceCassa());
			stmt.execute();
			
		}finally{
			close(rs, stmt);
			
		}
	}
	
	 
	/**
	 * metodo per la conferma di una posizione aggiudicatario
	 * 
	 * @param bean PosizioneAggiudicatarioBean
	 * @throws SQLException
	 */
	public void confirm(PosizioneAggiudicatarioBean bean) throws SQLException{
		update(bean, true);
		
	}
	/**
	 * metodo per il salvataggio di una posizione aggiudicatario
	 * 
	 * @param bean PosizioneAggiudicatarioBean
	 * @throws SQLException
	 */	
	public void save(PosizioneAggiudicatarioBean bean) throws SQLException{
		update(bean, false);
		
		
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
			"UPDATE "+POSIZ_AGGIUD.TABLE_NAME+ " SET "
			+ POSIZ_AGGIUD.ID_STATO+ " = ?, "
			+ POSIZ_AGGIUD.DATA_INIZIO_INIZIO+ " = ? "
			
			+" WHERE "
			+POSIZ_AGGIUD.T_ID_INIZIO+" = ?"
			+" AND "+POSIZ_AGGIUD.T_DATA_INIZIO_INIZIO+" = ?"
			+" AND "+POSIZ_AGGIUD.T_ID_STATO + "=" + StatiScheda.CONFERMATO;
		
		String QUERY_COPY_RECORD =
			"INSERT INTO "+POSIZ_AGGIUD.TABLE_NAME+" ("
			+ POSIZ_AGGIUD.ID_RECORD
			+ ", " +  POSIZ_AGGIUD.ID_INIZIO
			+ ", " + POSIZ_AGGIUD.DATA_INIZIO_INIZIO
			+ ", " + POSIZ_AGGIUD.ID_SOGGETTO_PARTECIPANTE
			+ ", " + POSIZ_AGGIUD.DATA_INIZIO_SOGG
			+ ", " + POSIZ_AGGIUD.CODICE_INAIL
			+ ", " + POSIZ_AGGIUD.CODICE_INPS
			+ ", " + POSIZ_AGGIUD.CODICE_CASSA
			+ ", " + POSIZ_AGGIUD.DATA_INIZIO_RECORD
			+ ", " + POSIZ_AGGIUD.DATA_FINE_RECORD
			+ ", " + POSIZ_AGGIUD.ID_STATO + " ) "
			+"SELECT "
			+ POSIZ_AGGIUD.ID_RECORD
			+ ", " +  POSIZ_AGGIUD.ID_INIZIO
			+ ", " + POSIZ_AGGIUD.DATA_INIZIO_INIZIO
			+ ", " + POSIZ_AGGIUD.ID_SOGGETTO_PARTECIPANTE
			+ ", " + POSIZ_AGGIUD.DATA_INIZIO_SOGG
			+ ", " + POSIZ_AGGIUD.CODICE_INAIL
			+ ", " + POSIZ_AGGIUD.CODICE_INPS
			+ ", " + POSIZ_AGGIUD.CODICE_CASSA
			+ ", ?"
			+ ", ?"
			+ ", ?"
			+" FROM "+POSIZ_AGGIUD.TABLE_NAME
			+" WHERE "
			+POSIZ_AGGIUD.ID_INIZIO+" = ?"
			+" AND "+POSIZ_AGGIUD.DATA_INIZIO_INIZIO+" = ? "
			+" AND "+POSIZ_AGGIUD.ID_STATO+" = "+StatiScheda.CONFERMATO;
			int index = 1;
			PreparedStatement stmt = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD,POSIZ_AGGIUD.TABLE_NAME));
			PreparedStatement stmt2 = activeConnection.prepareStatement(QUERY_UPDATE_OLD_RECORD);
			try{
				
				stmt.setTimestamp(index++, getNow());
				stmt.setNull(index++, Types.TIMESTAMP);
				stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE);
				stmt.setLong(index++, idInizioLavori);
				stmt.setTimestamp(index++, dataInizioLavori);
				int rowsCopied = stmt.executeUpdate();
				
				if(rowsCopied > 0){
					index = 1;
					stmt2 = activeConnection.prepareStatement(QUERY_UPDATE_OLD_RECORD);
					stmt2.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA); //stato scheda
					stmt2.setTimestamp(index++, vecchiaData); //data_inizio_aggiudicazione
					stmt2.setLong(index++, idInizioLavori);
					stmt2.setTimestamp(index++, dataInizioLavori);
					rowsCopied = stmt2.executeUpdate();
					return (rowsCopied>0);
				}
				
				
				logger.debug("POSIZ_AGGIUD_MANAGER.copyRecord: Nessun record da copiare");
				return true;
			}finally{
				close(null, stmt);
                close(null, stmt2);
			}
	}
	
	public static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO = 
		"UPDATE "+POSIZ_AGGIUD.TABLE_NAME+
		" SET " + POSIZ_AGGIUD.ID_STATO + " = ?,"
		+ POSIZ_AGGIUD.DATA_FINE_RECORD + " = " + buildGetDate()+		
		" WHERE "+POSIZ_AGGIUD.ID_INIZIO + " = ?"+
		" AND "+POSIZ_AGGIUD.DATA_INIZIO_INIZIO + " = ?";
	
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
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_POSIZIONE_AGGIUDICATARI);
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
