package it.avcp.simog.managers.subappalti;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamento;
import it.avcp.simog.managers.comportamento.caricamento.ILoadSubAppalti;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.subappalti.SubappaltatoreBean;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.common.servlet.ParametriServletSubappalti;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.CATEGORIA;
import it.avlp.simog.db.generated.DITTE_SUBAPPALTATRICI;
import it.avlp.simog.db.generated.SOGGETTI_PARTECIPANTI;
import it.avlp.simog.db.generated.SOSPENSIONI;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.db.generated.SUBAPPALTI;
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
 * Classe per la gestione dei dati relativi ai subappalti
 *
 */
public class SubappaltiManager extends AccessiDB implements IAnnullamento,ILoadSubAppalti {
	
	public static String CLAZZ = "SubappaltiManager";
	
	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public SubappaltiManager(Connection currentActiveConnection , Logger logger){
		super(currentActiveConnection, logger);
	}
	
	private final String INSERT_SUBAPPALTI = "INSERT INTO " + SUBAPPALTI.TABLE_NAME + "("
	+ SUBAPPALTI.DATA_AUTORIZZAZIONE
	+ ", " + SUBAPPALTI.DATA_FINE_RECORD 
	+ ", " + SUBAPPALTI.CF_DITTA
	+ ", " + SUBAPPALTI.FLAG_DITTA_SUB_ESTERA //MEV 36771 3.04.8.1
	+ ", " + SUBAPPALTI.DATA_INIZIO_AGGIUDICAZIONE
	+ ", " + SUBAPPALTI.DATA_INIZIO_RECORD
	+ ", " + SUBAPPALTI.ID_AGGIUDICAZIONE
	+ ", " + SUBAPPALTI.ID_STATO
	+ ", " + SUBAPPALTI.IMPORTO_EFFETTIVO
	+ ", " + SUBAPPALTI.IMPORTO_PRESUNTO
	+ ", " + SUBAPPALTI.OGGETTO_SUBAPPALTO
	+ ", " + SUBAPPALTI.ID_CATEGORIA
	+ ", " + SUBAPPALTI.ID_CPV
	+ ", " + SUBAPPALTI.ID_SCHEDA_LOCALE
	//gm nuovo codice 3.0
	+ ", " + SUBAPPALTI.CF_AGGIUDICATARIO
	//gm fine nuovo codice 3.0
	+ ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //MEV 36771 3.04.8.1
	
	/**
	 * metodo per l'iserimento di un subappalto
	 * 
	 * @param bean SubappaltiBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public long insert(SubappaltiBean bean , String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		long idRecord = 0;
		try{
			stmt = activeConnection.prepareStatement(createInsertQuery(INSERT_SUBAPPALTI, SUBAPPALTI.ID_RECORD));  
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataAutorizzazione())); 
			stmt.setNull(index++, Types.TIMESTAMP);
			stmt.setString(index++, bean.getCfDitta());
			stmt.setString(index++, bean.getFlagDittaSubEstera()); //MEV 36771 3.04.8.1
			stmt.setTimestamp(index++, bean.getDataInizioAggiudicazione()); 
			bean.setDataInizioRecord(getNow());
			stmt.setTimestamp(index++, bean.getDataInizioRecord());
			stmt.setLong(index++, bean.getIdAggiudicazione());
			bean.setIdStato(StatiScheda.IN_DEFINIZIONE);
			stmt.setLong(index++, bean.getIdStato());
			stmt.setBigDecimal(index++, bean.getImportoEffettivo());
			stmt.setBigDecimal(index++, bean.getImportoPresunto());
			stmt.setString(index++, bean.getOggettoSubappalto());
			stmt.setString(index++, bean.getIdCategoria());
			stmt.setString(index++, bean.getIdCpv());
			 
			
			if(bean.getIdLocale() == null){
				stmt.setNull(index++, Types.VARCHAR);
			}else{
				stmt.setString(index++, bean.getIdLocale());
			}
			//gm nuovo codice 3.0
			stmt.setString(index++, bean.getCfAggiudicatario());
			//gm fine nuovo codice 3.0
			
			if(stmt.execute()){
				rs = stmt.getResultSet();
				rs.next();
				bean.setIdRecord(rs.getLong(SUBAPPALTI.ID_RECORD));
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(bean.getIdRecord());
				attributiChiave.add(bean.getDataInizioRecord());
				LogBloccoDatiManager.loggingINSERT(activeConnection, logger, cfUtente, ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI , attributiChiave);
			}
		}finally{
			close(rs,stmt);
		}
		return bean.getIdRecord();
	}
	
	public static String UPDATE_SUBAPPALTI = "UPDATE " + SUBAPPALTI.TABLE_NAME + " SET  "
	+ SUBAPPALTI.DATA_FINE_RECORD + " =?"
	+ ", " + SUBAPPALTI.ID_STATO + " =?"
	+ ", " + SUBAPPALTI.CF_DITTA + " =?"
	+ ", " + SUBAPPALTI.FLAG_DITTA_SUB_ESTERA + " =?" //MEV 36771 3.04.8.1
 	+ ", " + SUBAPPALTI.DATA_AUTORIZZAZIONE + " =?" 
	+ ", " + SUBAPPALTI.IMPORTO_EFFETTIVO + " =?"
	+ ", " + SUBAPPALTI.IMPORTO_PRESUNTO + " =?"
	+ ", " + SUBAPPALTI.OGGETTO_SUBAPPALTO + " =?" 
	+ ", " + SUBAPPALTI.ID_CATEGORIA + " =?"
	+ ", " + SUBAPPALTI.ID_CPV + " =?"
	//gm nuovo codice 3.0
	+ ", " + SUBAPPALTI.CF_AGGIUDICATARIO +" =?"
	//gm fine nuovo codice 3.0
	+ " WHERE "
	+ SUBAPPALTI.ID_RECORD + " =? AND "
	+ SUBAPPALTI.DATA_INIZIO_RECORD + " =? ";
//	+ SUBAPPALTI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING
	// PP fix controllo stato scheda +" OR " + SUBAPPALTI.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING 
//	+ " ) ";
	
    private final String WHERE_CONF = " AND (" + SUBAPPALTI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING
          + " OR " + SUBAPPALTI.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING  + " ) ";
     private final String WHERE_DEF = " AND " + SUBAPPALTI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING;
	/**
	 * metodo per l'aggiornamento di un subappalto
	 * 
	 * @param bean SubappaltiBean
	 * @param cfUtente String
	 * @param confirm  boolean (se true conferma altrimenti aggiorna solamente)
	 * @throws SQLException
	 */
	public int update(SubappaltiBean bean , String cfUtente , boolean confirm)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try{
			stmt = activeConnection.prepareStatement(UPDATE_SUBAPPALTI + (SimogFlags.isFlagNoDate() ? WHERE_CONF : WHERE_DEF));
			if(confirm){
				stmt.setTimestamp(index++, getNow());
				stmt.setLong(index++, StatiScheda.CONFERMATO);
			}else{
				stmt.setNull(index++, Types.TIMESTAMP);
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
			}	
			stmt.setString(index++, bean.getCfDitta());	 	
			stmt.setString(index++, bean.getFlagDittaSubEstera()); //MEV 36771 3.04.8.1
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataAutorizzazione()));
			stmt.setBigDecimal(index++, bean.getImportoEffettivo());
			stmt.setBigDecimal(index++, bean.getImportoPresunto());
			stmt.setString(index++, bean.getOggettoSubappalto());
			stmt.setString(index++, bean.getIdCategoria());
			stmt.setString(index++, bean.getIdCpv());
			
			//gm nuovo codice 3.0
			stmt.setString(index++, bean.getCfAggiudicatario());
			//gm fine nuovo codice 3.0
			
			stmt.setLong(index++, bean.getIdRecord());
			stmt.setTimestamp(index++, bean.getDataInizioRecord());
			int num = stmt.executeUpdate();
			
			List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(bean.getIdRecord());
			attributiChiave.add(bean.getDataInizioRecord());
			if(confirm){
				LogBloccoDatiManager.loggingCONFIRM(activeConnection, logger, cfUtente, ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI, attributiChiave);
			}else{
				LogBloccoDatiManager.loggingUPDATE(activeConnection, logger, cfUtente, ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI, attributiChiave);
			}
			return num;
		}finally{
			close(rs , stmt);
		}
	}
	
	/**
	 * metodo per il salvataggio di un subappalto
	 * 
	 * @param bean SubappaltiBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public int save(SubappaltiBean bean , String cfUtente)throws SQLException{
		return update(bean, cfUtente, false);
	}
	
	/**
	 * metodo per la conferma di un subappalto
	 * 
	 * @param bean SubappaltiBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public int confirm(SubappaltiBean bean , String cfUtente)throws SQLException{
		return update(bean, cfUtente, true);
	}
	
	public final String SELECT_ONE_SUBAPPALTI = "SELECT "
		+ SUBAPPALTI.ID_RECORD
		+ ", " + SUBAPPALTI.CF_DITTA
		+ ", " + SUBAPPALTI.FLAG_DITTA_SUB_ESTERA //MEV 36771 3.04.8.1
		+ ", " + SUBAPPALTI.DATA_INIZIO_RECORD
		+ ", " + SUBAPPALTI.DATA_FINE_RECORD
 		+ ", " + SUBAPPALTI.DATA_AUTORIZZAZIONE
		+ ", " + SUBAPPALTI.DATA_INIZIO_AGGIUDICAZIONE
		+ ", " + SUBAPPALTI.ID_AGGIUDICAZIONE
		+ ", " + SUBAPPALTI.IMPORTO_EFFETTIVO
		+ ", " + SUBAPPALTI.IMPORTO_PRESUNTO
		+ ", " + SUBAPPALTI.OGGETTO_SUBAPPALTO
		+ ", " + SUBAPPALTI.ID_CATEGORIA
		+ ", " + SUBAPPALTI.ID_CPV
		//gm nuovo codice 3.0
		+ ", " + SUBAPPALTI.CF_AGGIUDICATARIO
		//gm fine uovo codice 3.0
		+ ", " + SUBAPPALTI.T_ID_STATO + ", " + STATI_SCHEDA.DESCRIZIONE + " + " +
			buildRichAnnQuery(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI, SUBAPPALTI.T_ID_RECORD, null)
			+" AS "+STATI_SCHEDA.DESCRIZIONE
		+ ", " + SUBAPPALTI.ID_SCHEDA_LOCALE	
		+ " FROM " + SUBAPPALTI.TABLE_NAME + ", " + STATI_SCHEDA.TABLE_NAME;
//		+ " WHERE " 
//		+ SUBAPPALTI.T_ID_RECORD + " =? AND "
//		+ SUBAPPALTI.T_DATA_INIZIO_RECORD + " =? AND "
//		+ SUBAPPALTI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
//		+" AND (" + SUBAPPALTI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
//		+" OR " + SUBAPPALTI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")"; 
	
	/**
	 * metodo per il recupero di un subappalto dal suo id
	 * 
	 * @param idRecord long
	 * @param dataInizioRecord Timestamp
	 * @return SubappaltiBean
	 * @throws SQLException
	 */
	public SubappaltiBean loadOne(long idRecord , Timestamp dataInizioRecord)throws SQLException{
		List<SubappaltiBean> lista = load(idRecord , dataInizioRecord , false);
		if(lista != null && lista.size() > 0)
			return lista.get(0);
		else return null;
	}
	
	public final String SELECT_MANY_SUBAPPALTI = "SELECT "
		+ SUBAPPALTI.ID_RECORD
		+ ", " + SUBAPPALTI.DATA_INIZIO_RECORD
		+ ", " + SUBAPPALTI.DATA_FINE_RECORD
		+ ", " + SUBAPPALTI.CF_DITTA
		+ ", " + SUBAPPALTI.FLAG_DITTA_SUB_ESTERA ////MEV 36771 3.04.8.1
 		+ ", " + SUBAPPALTI.DATA_AUTORIZZAZIONE
		+ ", " + SUBAPPALTI.DATA_INIZIO_AGGIUDICAZIONE 
		+ ", " + SUBAPPALTI.ID_AGGIUDICAZIONE
		+ ", " + SUBAPPALTI.IMPORTO_EFFETTIVO
		+ ", " + SUBAPPALTI.IMPORTO_PRESUNTO
		+ ", " + SUBAPPALTI.OGGETTO_SUBAPPALTO
		+ ", " + SUBAPPALTI.ID_CATEGORIA
		+ ", " + SUBAPPALTI.ID_CPV
		//gm nuovo codice 3.0
		+ ", " + SUBAPPALTI.CF_AGGIUDICATARIO
		//gm fine uovo codice 3.0
		+ ", " + SUBAPPALTI.T_ID_STATO + ", " + STATI_SCHEDA.DESCRIZIONE + " + " +
		buildRichAnnQuery(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI, SUBAPPALTI.T_ID_RECORD,null)
		+" AS "+STATI_SCHEDA.DESCRIZIONE
		+ ", " + SUBAPPALTI.ID_SCHEDA_LOCALE + " "
	    + " FROM " + SUBAPPALTI.TABLE_NAME + ", " + STATI_SCHEDA.TABLE_NAME
	    + " WHERE " + SUBAPPALTI.T_ID_AGGIUDICAZIONE + " = ? AND "
	    + SUBAPPALTI.T_DATA_INIZIO_AGGIUDICAZIONE + " = ?  AND "
	    + SUBAPPALTI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
	    +" AND (" + SUBAPPALTI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + SUBAPPALTI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+") order by " + SUBAPPALTI.DATA_AUTORIZZAZIONE;
		
		/**
		 * metodo per il caricamento di una lista di subappalti
		 * 
		 * @param idAggiudicazione long
		 * @param dataInizioAggiudicazione Timestamp
		 * @return List&lt;SubappaltiBean&gt;
		 * @throws SQLException
		 */
		public List<SubappaltiBean> loadMany(long idAggiudicazione , Timestamp dataInizioAggiudicazione) throws  SQLException{
			return load(idAggiudicazione , dataInizioAggiudicazione , true);
		}
		
		private List<SubappaltiBean> load(long id , Timestamp date , boolean byAggiudicazione)throws SQLException{
			PreparedStatement stmt = null;
			ResultSet rs = null;
			int index = 1;
			ArrayList<SubappaltiBean> listaSchede = new ArrayList<SubappaltiBean>();
			SubappaltiBean bean = null;
			try{
				if(byAggiudicazione)
					stmt = activeConnection.prepareStatement(SELECT_MANY_SUBAPPALTI);
				else
					stmt = activeConnection.prepareStatement(SELECT_ONE_SUBAPPALTI + WHERE_STANDARD);
				stmt.setLong(index++, id);
				stmt.setTimestamp(index++, date);
				rs = stmt.executeQuery();
				while(rs.next()){
					bean = new SubappaltiBean();
					fillBean(rs, bean);
					bean.setSubappaltatori(loadDitteSubappaltatrici(bean));//TICKET ALM - 3.04.3 #4198
					listaSchede.add(bean);
				}
				listaSchede.trimToSize();
				return listaSchede;
				}finally{
					close(rs , stmt);
				}
		}
		
		/**
		 * Metodo per la storicizzazione di un record relativo ad un subbappalto
		 * 
		 * @param idRecord
		 * @param dataInizioRecord
		 * @return Timestamp
		 * @throws SQLException
		 */
		public Timestamp copyRecord(long idRecord , Timestamp dataInizioRecord)throws SQLException{
			String QUERY_SELECT_DATA_FINE = "SELECT " + SUBAPPALTI.DATA_FINE_RECORD
			+ " FROM " + SUBAPPALTI.TABLE_NAME
			+ " WHERE " + SUBAPPALTI.ID_RECORD + " = ? "
			+ " AND " + SUBAPPALTI.DATA_INIZIO_RECORD + " = ?"
			+ " AND " + SUBAPPALTI.ID_STATO + " = " + StatiScheda.CONFERMATO;
			
			String UPDATE_STATO_OLD_SUBAPPALTI = 
				"UPDATE " + SUBAPPALTI.TABLE_NAME + " SET "
				+ SUBAPPALTI.ID_STATO + " = ? "
				+ ", " + SUBAPPALTI.DATA_INIZIO_RECORD + " = ?"
				+ ", " + SUBAPPALTI.DATA_FINE_RECORD + " = ?"
				+ " WHERE " + SUBAPPALTI.ID_RECORD + " = ? "
				+ " AND " + SUBAPPALTI.DATA_INIZIO_RECORD + " = ?"
				+ " AND " + SUBAPPALTI.ID_STATO + " = " + StatiScheda.CONFERMATO;
			
			
			String COPY_RECORD = "INSERT INTO " + SUBAPPALTI.TABLE_NAME +"("
			+ SUBAPPALTI.ID_RECORD
 			//gm nuovo codice 3.0
			+ ", " + SUBAPPALTI.CF_AGGIUDICATARIO
			//gm fine nuovo codice 3.0
			+ ", " + SUBAPPALTI.DATA_AUTORIZZAZIONE
			+ ", " + SUBAPPALTI.DATA_INIZIO_AGGIUDICAZIONE
			+ ", " + SUBAPPALTI.CF_DITTA
			+ ", " + SUBAPPALTI.FLAG_DITTA_SUB_ESTERA //MEV 36771 3.04.8.1
			+ ", " + SUBAPPALTI.ID_AGGIUDICAZIONE
			+ ", " + SUBAPPALTI.IMPORTO_EFFETTIVO
			+ ", " + SUBAPPALTI.IMPORTO_PRESUNTO
			+ ", " + SUBAPPALTI.OGGETTO_SUBAPPALTO
			+ ", " + SUBAPPALTI.ID_CATEGORIA
			+ ", " + SUBAPPALTI.ID_CPV
			+ ", " + SUBAPPALTI.ID_SCHEDA_LOCALE
			
			+ ", " + SUBAPPALTI.DATA_INIZIO_RECORD
			+ ", " + SUBAPPALTI.DATA_FINE_RECORD
			+ ", " + SUBAPPALTI.ID_STATO
			  + ") SELECT "
			+ SUBAPPALTI.ID_RECORD
 			//gm nuovo codice 3.0
			+ ", " + SUBAPPALTI.CF_AGGIUDICATARIO
			//gm fine nuovo codice 3.0
			+ ", " + SUBAPPALTI.DATA_AUTORIZZAZIONE
			+ ", " + SUBAPPALTI.DATA_INIZIO_AGGIUDICAZIONE
			+ ", " + SUBAPPALTI.CF_DITTA
			+ ", " + SUBAPPALTI.FLAG_DITTA_SUB_ESTERA //MEV 36771 3.04.8.1
			+ ", " + SUBAPPALTI.ID_AGGIUDICAZIONE
			+ ", " + SUBAPPALTI.IMPORTO_EFFETTIVO
			+ ", " + SUBAPPALTI.IMPORTO_PRESUNTO
			+ ", " + SUBAPPALTI.OGGETTO_SUBAPPALTO
			+ ", " + SUBAPPALTI.ID_CATEGORIA
			+ ", " + SUBAPPALTI.ID_CPV
			+ ", " + SUBAPPALTI.ID_SCHEDA_LOCALE
			+ ",? "
			+ ",? "
			+ ",? "
			+ " FROM " + SUBAPPALTI.TABLE_NAME
			+ " WHERE " + SUBAPPALTI.ID_RECORD + " = ? "
			+ " AND " + SUBAPPALTI.DATA_INIZIO_RECORD + " = ?";
			
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
					dataFine = rs.getTimestamp(SUBAPPALTI.DATA_FINE_RECORD);
					
					//il record corrente diventa il nuovo record
					index = 1;
					nuovaDataRecord = getNow();
					updateRecord = activeConnection.prepareStatement(UPDATE_STATO_OLD_SUBAPPALTI);
					updateRecord.setInt(index++, StatiScheda.IN_DEFINIZIONE);
					updateRecord.setTimestamp(index++, nuovaDataRecord);
					updateRecord.setNull(index++, Types.TIMESTAMP);
					updateRecord.setLong(index++, idRecord);
					updateRecord.setTimestamp(index++, dataInizioRecord);
					updateRecord.execute();
					
					//copy record
					index = 1;
					copyRecord = activeConnection.prepareStatement(createCopyRecord(COPY_RECORD,SUBAPPALTI.TABLE_NAME));
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
		 * Metodo per la verifica di esistenza di un Subbappalto di cui s"id" e "data inizio"
		 * 
		 * @param idRecord
		 * @param dataInizioRecord
		 * @return boolean
		 * @throws SQLException
		 */
		public boolean existSubappalti(long idRecord, Timestamp dataInizioRecord) throws SQLException{

			PreparedStatement stmt = null;
			ResultSet rs = null;
			String query = "SELECT * FROM " + SUBAPPALTI.TABLE_NAME + " WHERE " + 
			SUBAPPALTI.ID_RECORD + " = ? AND " + 
			SUBAPPALTI.DATA_INIZIO_RECORD + " = ?";
			
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
		
	
		
		private String DELETE_RECORD_SUBAPPALTI = 
			"DELETE FROM " + SUBAPPALTI.TABLE_NAME
			+ " WHERE " + SUBAPPALTI.ID_RECORD + " = ?"
			+ " AND " + SUBAPPALTI.DATA_INIZIO_RECORD + " = ?";
		
		/**
		 * Metodo per la cancellazione di un record relativo a un subbappalto
		 * 
		 * @param idRecord
		 * @param dataInizioRecord
		 * @return int - affected rows
		 * @throws SQLException
		 */
		public int deleteRecord(long idRecord, Timestamp dataInizioRecord)throws SQLException{
			PreparedStatement stmt = null;
			int index = 1;
			try{
				
				stmt = activeConnection.prepareStatement(DELETE_RECORD_SUBAPPALTI);
				stmt.setLong(index++, idRecord);
				stmt.setTimestamp(index++, dataInizioRecord);
				return stmt.executeUpdate();
			}finally{
				close(null,stmt);
			}
		}
		
		private static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO = 
			"UPDATE "+SUBAPPALTI.TABLE_NAME+
			" SET " + SUBAPPALTI.ID_STATO + " = ?,"
			+ SUBAPPALTI.DATA_FINE_RECORD + " = " + buildGetDate()			
			+ " WHERE " + SUBAPPALTI.ID_RECORD + " = ?"
			+ " AND " + SUBAPPALTI.DATA_INIZIO_RECORD + " = ?";
		
		/**
		 * Metodo per l'aggiornamento dello stato di un record relativo a un subbappalto di cui parametri in ingresso
		 * 
		 * @param idRecord
		 * @param dataInizioRecord
		 * @param statoScheda
		 * @return int - affected rows
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
	 * Metodo per il recuperdo della mappa rappresentante le categorie esistenti
	 * 
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validita' di una tipologia a posteriori
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException
	 */
	public Map<String , String> laodCategorie(Object o)throws SQLException{
		return getTipologica(CATEGORIA.TABLE_NAME, CATEGORIA.ID_CATEGORIA, CATEGORIA.DESCRIZIONE, CATEGORIA.DATA_FINE_VALIDITA,o);
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
		SubappaltiBean subAppaltoBean = loadByIdSimog(idSimog);
		
		if(subAppaltoBean.getIdRecord() > 0){
			return _annulla(subAppaltoBean.getIdRecord(), subAppaltoBean.getDataInizioRecord(),cfUtente);
		}
		return false;
		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean annulla(String idLocale, String rifSimog, String cfUtente) throws SQLException {
		SubappaltiBean subAppaltoBean = loadByIdLocale(idLocale, rifSimog);
		
		if(subAppaltoBean.getIdRecord() > 0){
			return _annulla(subAppaltoBean.getIdRecord(), subAppaltoBean.getDataInizioRecord(),cfUtente);
		}
		return false;
		
	}
	/**
	 * @param idSubAppalto
	 * @param dataInizioSubAppalto
	 * @throws SQLException
	 */
	private boolean _annulla(long idSubAppalto, Timestamp dataInizioSubAppalto, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_SUBAPPALTI);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idSubAppalto);
			stmt.setTimestamp(index++, dataInizioSubAppalto);
			someRowAffected = stmt.executeUpdate() > 0;
			if(someRowAffected){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idSubAppalto);
				attributiChiave.add(dataInizioSubAppalto);
					LogBloccoDatiManager.loggingDELETE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_SUBAPPALTO, attributiChiave);
			}
			return someRowAffected;
		}finally {
			close(null,stmt);
		}		
	}
	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadSubAppalti#fillBean(java.sql.ResultSet, it.avlp.simog.beans.subappalti.SubappaltiBean)
	 */
	public void fillBean(ResultSet rs, SubappaltiBean bean) throws SQLException {
 		//gm nuovo codice 3.0
		//bean.setCfAggiudicatario(rs.getString(SUBAPPALTI.CF_AGGIUDICATARIO));
		//gm fine nuovo codice 3.0
		bean.setDataAutorizzazione(PageHelper.getViewDate(rs.getString(SUBAPPALTI.DATA_AUTORIZZAZIONE)));
		bean.setDataFineRecord(rs.getTimestamp(SUBAPPALTI.DATA_FINE_RECORD));
		bean.setDataInizioAggiudicazione(rs.getTimestamp(SUBAPPALTI.DATA_INIZIO_AGGIUDICAZIONE));
		bean.setCfDitta(rs.getString(SUBAPPALTI.CF_DITTA));
		bean.setFlagDittaSubEstera(rs.getString(SUBAPPALTI.FLAG_DITTA_SUB_ESTERA)); //MEV 36771 3.04.8.1
		bean.setDataInizioRecord(rs.getTimestamp(SUBAPPALTI.DATA_INIZIO_RECORD));
		bean.setIdAggiudicazione(rs.getLong(SUBAPPALTI.ID_AGGIUDICAZIONE));
		bean.setIdRecord(rs.getLong(SUBAPPALTI.ID_RECORD));
		bean.setIdStato(rs.getLong(SUBAPPALTI.ID_STATO));
		bean.setImportoEffettivo(rs.getBigDecimal(SUBAPPALTI.IMPORTO_EFFETTIVO));
		bean.setImportoPresunto(rs.getBigDecimal(SUBAPPALTI.IMPORTO_PRESUNTO));
		bean.setOggettoSubappalto(rs.getString(SUBAPPALTI.OGGETTO_SUBAPPALTO));
		bean.setDescrizioneStato(rs.getString(STATI_SCHEDA.DESCRIZIONE));
		bean.setIdCategoria(rs.getString(SUBAPPALTI.ID_CATEGORIA));
		bean.setIdCpv(rs.getString(SUBAPPALTI.ID_CPV));
		bean.setIdLocale(rs.getString(SUBAPPALTI.ID_SCHEDA_LOCALE));
		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadSubAppalti#loadByIdLocale(java.lang.String, java.lang.String)
	 */
	public SubappaltiBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		SubappaltiBean bean = null;
		try{
			stmt = activeConnection.prepareStatement(SELECT_ONE_SUBAPPALTI + WHERE_IDLOCALE);
			stmt.setString(index++, idLocale);
			long idAggiudicazioneL = Long.parseLong(idAggiudicazione);
			stmt.setLong(index++, idAggiudicazioneL);			
			rs = stmt.executeQuery();
			bean = new SubappaltiBean();
			while(rs.next()){				
				fillBean(rs, bean);
			}
			return bean;
			}finally{
				close(rs , stmt);
			}
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadSubAppalti#loadByIdSimog(long)
	 */
	public SubappaltiBean loadByIdSimog(long idSimog) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		SubappaltiBean bean = null;
		try{
			stmt = activeConnection.prepareStatement(SELECT_ONE_SUBAPPALTI + WHERE_IDSIMOG);
			stmt.setLong(index++, idSimog);			
			rs = stmt.executeQuery();
			bean = new SubappaltiBean();
			while(rs.next()){
				fillBean(rs, bean);
			}
			return bean;
			}finally{
				close(rs , stmt);
			}
	}
	
	//TICKET ALM - 3.04.3
	private final String INSERT_DITTA_SUBAPPALTATRICE = "INSERT INTO "+DITTE_SUBAPPALTATRICI.TABLE_NAME+" ("
			+DITTE_SUBAPPALTATRICI.DATA_INIZIO+","
			+DITTE_SUBAPPALTATRICI.ID_SUBAPPALTO+","
			+DITTE_SUBAPPALTATRICI.DATA_INIZIO_SUBAPPALTO+","
			+DITTE_SUBAPPALTATRICI.ID_SOGGETTO_PARTECIPANTE+","
			+DITTE_SUBAPPALTATRICI.DATA_INIZIO_SOGG+") VALUES(?,?,?,?,?)";
	
	public void insertDitteSubappaltatrici(SubappaltatoreBean bean ) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try{
			stmt = activeConnection.prepareStatement(INSERT_DITTA_SUBAPPALTATRICE);
			stmt.setTimestamp(index++,getNow());
			stmt.setLong(index++, bean.getIdSubappalto());
			stmt.setTimestamp(index++, bean.getDataInizioSubappalto());
			stmt.setLong(index++, bean.getSoggettoPartecipante().getIdSoggettoPartecipante());
			stmt.setTimestamp(index++, bean.getSoggettoPartecipante().getDataInizioSogg());
			stmt.execute();
			
		}finally{
			close(rs,stmt);
		}
	}
	
	private final String EXPIRE_SUBAPPALTATORI = "UPDATE "+DITTE_SUBAPPALTATRICI.TABLE_NAME+
			" SET "+DITTE_SUBAPPALTATRICI.DATA_FINE +" = ? "+
			" WHERE "+DITTE_SUBAPPALTATRICI.ID_SUBAPPALTO+" = ? AND "+
			DITTE_SUBAPPALTATRICI.DATA_INIZIO_SUBAPPALTO+ " = ?";
	
	public void expireDitteSubappaltatrici(SubappaltiBean sb) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try{
			stmt = activeConnection.prepareStatement(EXPIRE_SUBAPPALTATORI);
			stmt.setTimestamp(index++,getNow());
			stmt.setLong(index++, sb.getIdRecord());
			stmt.setTimestamp(index++, sb.getDataInizioRecord());	
			stmt.execute();
			
		}finally{
			close(rs,stmt);
		}
	}
	

	
	public List<SubappaltatoreBean> loadDitteSubappaltatrici(SubappaltiBean sb) throws SQLException{
		ArrayList<SubappaltatoreBean> lista = new ArrayList<SubappaltatoreBean>();
		SubappaltatoreBean bean = null;
		
		 final String SELECT_DITTE_SUBAPPALTATRICI = "SELECT A."+DITTE_SUBAPPALTATRICI.ID_DITTE_SUBAPPALTATRICI+
				", A."+DITTE_SUBAPPALTATRICI.DATA_INIZIO+
				", B."+SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE+
				", B."+SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG+
				", B."+SOGGETTI_PARTECIPANTI.CODICE_FISCALE+
				", B."+SOGGETTI_PARTECIPANTI.DENOMINAZIONE+
				", B."+SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO+
				", B."+SOGGETTI_PARTECIPANTI.PARTITA_IVA+
				", B."+SOGGETTI_PARTECIPANTI.CIVICO+
				", B."+SOGGETTI_PARTECIPANTI.CAP+
				", B."+SOGGETTI_PARTECIPANTI.PROVINCIA+
				", B."+SOGGETTI_PARTECIPANTI.ID_STATO
		        +" FROM "+DITTE_SUBAPPALTATRICI.TABLE_NAME+ " A "
		        +" INNER JOIN "+SOGGETTI_PARTECIPANTI.TABLE_NAME+" B ON B."+SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE+" = A."+DITTE_SUBAPPALTATRICI.ID_SOGGETTO_PARTECIPANTE+
		                        " AND B."+SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG+" = A."+DITTE_SUBAPPALTATRICI.DATA_INIZIO_SOGG
				+" WHERE A."+DITTE_SUBAPPALTATRICI.ID_SUBAPPALTO + "= ? "
				+ "AND A."+DITTE_SUBAPPALTATRICI.DATA_INIZIO_SUBAPPALTO +" = ? AND A."+DITTE_SUBAPPALTATRICI.DATA_FINE+" IS NULL";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index=1;
		int i=1;
		try {	
			stmt = activeConnection.prepareStatement(SELECT_DITTE_SUBAPPALTATRICI);
			stmt.setLong(index++, sb.getIdRecord());
			stmt.setTimestamp(index++, sb.getDataInizioRecord());
			rs = stmt.executeQuery();
			while(rs.next()){
				bean = new SubappaltatoreBean();
				bean.setIdDitteSubappaltatrici(rs.getLong(i++));
				bean.setDataInizio(rs.getTimestamp(i++));

				SoggettoPartecipanteBean ditta = new SoggettoPartecipanteBean();
				ditta.setIdSoggettoPartecipante(rs.getLong(i++));
				ditta.setDataInizioSogg(rs.getTimestamp(i++));
				ditta.setCodiceFiscale(rs.getString(i++));
				ditta.setDenominazione(rs.getString(i++));
				ditta.setCameraCommercio(rs.getString(i++));
				ditta.setPartitaIva(rs.getString(i++));
				ditta.setCivico(rs.getString(i++));
				ditta.setCap(rs.getString(i++));
				ditta.setProvincia(rs.getString(i++));
				ditta.setId_stato(rs.getString(i++));
				
				bean.setSoggettoPartecipante(ditta);
				
				lista.add(bean);
				i=1;
			}
			lista.trimToSize();	
			
			return lista;
		}finally{
			close(rs,stmt);
		}
	}
	//TICKET ALM - 3.04.3
	

//	public boolean annullaByAggiudicazione(long idAggiudicazione,Timestamp dataInizioAggiudicazione, String cfUtente)throws SQLException {
//		List<SubappaltiBean> listOfSubAppalti = this.loadMany(idAggiudicazione, dataInizioAggiudicazione);
//		boolean esitoOperazione = listOfSubAppalti.size() > 0 ? true : false;
//		for(SubappaltiBean subCorrente : listOfSubAppalti){
//			esitoOperazione = esitoOperazione && _annulla(subCorrente.getIdRecord(), subCorrente.getDataInizioRecord(), cfUtente);
//		}return esitoOperazione;
//	}
}
