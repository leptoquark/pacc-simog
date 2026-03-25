package it.avcp.simog.managers.avanzamento;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamento;
import it.avcp.simog.managers.comportamento.caricamento.ILoadAvanzamento;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.common.servlet.ParametriServletAvanzamento;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.STATI_AVANZ;
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
 * Classe per la gestione dei dati relativi agli avanzamenti
 *
 */
public class AvanzamentoManager  extends AccessiDB implements IAnnullamento,ILoadAvanzamento{
	
	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public AvanzamentoManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}

	private final String QUERY_SELECT_MAX_AVANZAMENTO = "SELECT max(" + STATI_AVANZ.NUMERO_AVANZAMENTO+ ") as " + STATI_AVANZ.NUMERO_AVANZAMENTO+
	" FROM " +
	STATI_AVANZ.TABLE_NAME + 
	" WHERE " 
	 + STATI_AVANZ.ID_AGGIUDICAZIONE + " = ? "+
		" AND " + STATI_AVANZ.DATA_INIZIO_AGGIUDICAZIONE + "= ? "
		+" AND (" + STATI_AVANZ.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + STATI_AVANZ.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";

	/**
	 * metodo per il recupero del numero associato al massimo avanzamento effettuato
	 * 
	 * @param idAgg  Long id aggiudicazione
	 * @param dataInizioAgg Timestamp
	 * @return int - il numero del prossimo avanzamento
	 * @throws SQLException
	 */
	
	// TODO: la gestionee del numero avanzamento dovrebbe essere a carico dell'utente
	public int getNextAvanzamento(Long idAgg,Timestamp dataInizioAgg)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int retVal = 1;
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_MAX_AVANZAMENTO);
			logger.debug(QUERY_SELECT_MAX_AVANZAMENTO);
			stmt.setLong(1, idAgg);
			stmt.setTimestamp(2, dataInizioAgg);
			rs = stmt.executeQuery();
			if(rs.next()){
				retVal = rs.getInt(STATI_AVANZ.NUMERO_AVANZAMENTO)+1;
			}
		}

		finally{
			close(rs,stmt);
		}
		return retVal;
	}
	
	private final String QUERY_SELECT_MANY_AVANZAMENTI =
		"SELECT " +
		STATI_AVANZ.TABLE_NAME + ".* ," +
		STATI_SCHEDA.T_DESCRIZIONE+ " + " +  buildRichAnnQuery(ParametriServletAvanzamento.TAB_AVANZAMENTO, STATI_AVANZ.T_ID_AVANZAMENTO,null) 
		+" AS "+STATI_SCHEDA.DESCRIZIONE 
		+ ", " + STATI_AVANZ.ID_SCHEDA_LOCALE + "" +
		" FROM " +
		STATI_AVANZ.TABLE_NAME + ", " +
		STATI_SCHEDA.TABLE_NAME +
		" WHERE " 
		 + STATI_AVANZ.ID_AGGIUDICAZIONE + " = ? "+
		" AND " + STATI_AVANZ.DATA_INIZIO_AGGIUDICAZIONE + "= ? "
		+ " AND " + STATI_AVANZ.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
		+" AND (" + STATI_AVANZ.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + STATI_AVANZ.T_ID_STATO + "=" + StatiScheda.CONFERMATO+") order by " + STATI_AVANZ.DATA_RAGGIUNGIMENTO;
	
	private final String QUERY_SELECT_ONE_AVANZAMENTO = "SELECT " +
	STATI_AVANZ.TABLE_NAME + ".* ," +
	STATI_SCHEDA.T_DESCRIZIONE+ " + " +  buildRichAnnQuery(ParametriServletAvanzamento.TAB_AVANZAMENTO , STATI_AVANZ.T_ID_AVANZAMENTO,null) 
	+" AS "+STATI_SCHEDA.DESCRIZIONE+		
	", " + STATI_AVANZ.ID_SCHEDA_LOCALE +
	" FROM " +
	STATI_AVANZ.TABLE_NAME + ", " +
	STATI_SCHEDA.TABLE_NAME ;
//	+
//	" WHERE " 
//	 + STATI_AVANZ.ID_AVANZAMENTO + " = ? "+
//	" AND " + STATI_AVANZ.DATA_INIZIO_AVANZAMENTO + "= ? "
//	+ " AND " + STATI_AVANZ.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
//	+" AND (" + STATI_AVANZ.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
//	+" OR " + STATI_AVANZ.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";


	/**
	 * param idRecord
	 * param dataInizioRecord
	 * param selectMany
	 * return
	 * throws SQLException
	 */
	private List<AvanzamentoBean> load(Long idRecord,Timestamp dataInizioRecord, boolean selectMany)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<AvanzamentoBean> ris = new ArrayList<AvanzamentoBean>();
		try{
			AvanzamentoBean ab = null;
			if(selectMany)
				stmt = activeConnection.prepareStatement(QUERY_SELECT_MANY_AVANZAMENTI);
			
			else stmt = activeConnection.prepareStatement(QUERY_SELECT_ONE_AVANZAMENTO + WHERE_STANDARD);
			
			stmt.setLong(1, idRecord);
			stmt.setTimestamp(2, dataInizioRecord);
			rs = stmt.executeQuery();
			while(rs.next()){
				ab = new AvanzamentoBean();
				fillBean(rs, ab);
				ris.add(ab);
			}
		}
//		catch(SQLException ex){
//		}
		finally{
			close(rs,stmt);
		}
		ris.trimToSize();
		return ris;
	}

	/**
	 * metodo per il recupero dell'avanzamento
	 * 
	 * @param idAvanzamento long
	 * @param dataInizioAvanzamento Timestamp
	 * @return AvanzamentoBean
	 * @throws SQLException
	 */
	public AvanzamentoBean loadOne(long idAvanzamento, Timestamp dataInizioAvanzamento)throws SQLException{
		List<AvanzamentoBean> lst = load(idAvanzamento, dataInizioAvanzamento, false);
		if(lst.size()>0)
			return lst.get(0);
		else return null;
	}
	
	/**
	 * metodo per il recupero degli avanzamenti associati ad una aggiudicazione
	 * 
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @return List&lt;AvanzamentoBean&gt;
	 * @throws SQLException
	 */
	public List<AvanzamentoBean> loadMany(long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		return load(idAggiudicazione, dataInizioAggiudicazione, true);
	}
	
	
	private String INSERT_AVANZAMENTO = 
		"INSERT INTO " + STATI_AVANZ.TABLE_NAME + " ("
		+ STATI_AVANZ.DATA_INIZIO_AVANZAMENTO
		+ ", " + STATI_AVANZ.ID_STATO
		+ ", " + STATI_AVANZ.ID_AGGIUDICAZIONE
		+ ", " + STATI_AVANZ.DATA_INIZIO_AGGIUDICAZIONE
		+ ", " + STATI_AVANZ.NUMERO_AVANZAMENTO
		+ ", " + STATI_AVANZ.FLAG_PAGAMENTO
		+ ", " + STATI_AVANZ.DATA_ANTICIPAZIONE
		+ ", " + STATI_AVANZ.IMPORTO_ANTICIPAZIONE
		+ ", " + STATI_AVANZ.DATA_RAGGIUNGIMENTO
		+ ", " + STATI_AVANZ.IMPORTO_SAL
		+ ", " + STATI_AVANZ.DATA_CERTIFICATO
		+ ", " + STATI_AVANZ.IMPORTO_CERTIFICATO
		+ ", " + STATI_AVANZ.FLAG_RITARDO
		+ ", " + STATI_AVANZ.NUM_GIORNI_SCOST
		+ ", " + STATI_AVANZ.NUM_GIORNI_PROROGA
		+ ", " + STATI_AVANZ.DATA_FINE_AVANZAMENTO
		+ ", " + STATI_AVANZ.DENOM_AVANZ
		+ ", " + STATI_AVANZ.ID_SCHEDA_LOCALE
		+ ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	/**
	 * metodo per l'inserimento di un'avanzamento
	 * 
	 * @param bean AvanzamentoBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public void insert(AvanzamentoBean bean, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try {
			stmt = activeConnection.prepareStatement(createInsertQuery(INSERT_AVANZAMENTO, STATI_AVANZ.ID_AVANZAMENTO));
			
			bean.setDataInizioAvanzamento(getNow()); //data inizio record
			
			stmt.setTimestamp(index++, bean.getDataInizioAvanzamento());
			bean.setIdStato(StatiScheda.IN_DEFINIZIONE);
			stmt.setLong(index++, bean.getIdStato());
			stmt.setLong(index++, bean.getIdAggiudicazione());
			stmt.setTimestamp(index++, bean.getDataInizioAggiudicazione());
			stmt.setInt(index++, bean.getNumeroAvanzamento());
			stmt.setString(index++, bean.getFlagPagamento());
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataAnticipazione()));
			stmt.setBigDecimal(index++, bean.getImportoAnticipazione());
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataRaggiungimento()));
			stmt.setBigDecimal(index++, bean.getImportoSal());
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataCertificato()));
			stmt.setBigDecimal(index++, bean.getImportoCertificato());
			stmt.setString(index++, bean.getFlagRitardo());
			stmt.setInt(index++, bean.getNumeroGiorniScost());
			stmt.setInt(index++, bean.getNumeroGiorniProroga());
			stmt.setNull(index++,Types.TIMESTAMP);
			stmt.setString(index++, bean.getDenomStatoAvanz());
			if(bean.getIdLocale() == null){
				stmt.setNull(index++, Types.VARCHAR);
			}else{
				stmt.setString(index++, bean.getIdLocale());
			}
			
			if(stmt.execute()){
				rs = stmt.getResultSet();
				rs.next();
				bean.setIdAvanzamento(rs.getLong(STATI_AVANZ.ID_AVANZAMENTO));
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(bean.getIdAvanzamento());
				attributiChiave.add(bean.getDataInizioAvanzamento());
				LogBloccoDatiManager.loggingINSERT(activeConnection, logger, cfUtente, ParametriServletAvanzamento.TAB_AVANZAMENTO, attributiChiave);
			}
			
		}finally {
			close(rs, stmt);
		}
		
	}
	
	
	private String UPDATE_AVANZAMENTO = 
		"UPDATE " + STATI_AVANZ.TABLE_NAME + " SET "
		 + STATI_AVANZ.DATA_FINE_AVANZAMENTO + " =? "
		+ ", " + STATI_AVANZ.ID_STATO + " =? "
		+ ", " + STATI_AVANZ.NUMERO_AVANZAMENTO + " =? "
		+ ", " + STATI_AVANZ.FLAG_PAGAMENTO + " =? "
		+ ", " + STATI_AVANZ.DATA_ANTICIPAZIONE + " =? "
		+ ", " + STATI_AVANZ.IMPORTO_ANTICIPAZIONE + " =? "
		+ ", " + STATI_AVANZ.DATA_RAGGIUNGIMENTO + " =? "
		+ ", " + STATI_AVANZ.IMPORTO_SAL + " =? "
		+ ", " + STATI_AVANZ.DATA_CERTIFICATO + " =? "
		+ ", " + STATI_AVANZ.IMPORTO_CERTIFICATO + " =? "
		+ ", " + STATI_AVANZ.FLAG_RITARDO + " =? "
		+ ", " + STATI_AVANZ.NUM_GIORNI_SCOST + " =? "
		+ ", " + STATI_AVANZ.NUM_GIORNI_PROROGA + " =? "
		+ ", " + STATI_AVANZ.DENOM_AVANZ + " =? "
		+ " WHERE " + STATI_AVANZ.ID_AVANZAMENTO + " = ? "
		+ " AND " + STATI_AVANZ.DATA_INIZIO_AVANZAMENTO + " = ? ";
		//+ " AND (" + STATI_AVANZ.ID_STATO + " = " +  StatiScheda.IN_DEFINIZIONE_STRING
		// PP fix controllo stato scheda + 	" OR " + STATI_AVANZ.ID_STATO + " = " +  StatiScheda.CONFERMATO_STRING 
		//+" )";
	
	private final static String WHERE_DEF = " AND " + STATI_AVANZ.ID_STATO + " = " +  StatiScheda.IN_DEFINIZIONE_STRING;
	
	private final static String WHERE_CONF = " AND (" + STATI_AVANZ.ID_STATO + " = " 
	      +  StatiScheda.IN_DEFINIZIONE_STRING + " OR " + STATI_AVANZ.ID_STATO + " = " +  StatiScheda.CONFERMATO_STRING + ")";
	
	
	/**
	 * param bean
	 * param cfUtente
	 * param confirm
	 * throws SQLException
	 */
	private int update(AvanzamentoBean bean, String cfUtente, boolean confirm) throws SQLException{
		PreparedStatement stmt = null;
		
		int index = 1;
		try {
			stmt = activeConnection.prepareStatement(UPDATE_AVANZAMENTO + (SimogFlags.isFlagNoDate() ? WHERE_CONF : WHERE_DEF)); 
			
			if(confirm){
				stmt.setTimestamp(index++, getNow());
				stmt.setInt(index++, StatiScheda.CONFERMATO);
			}
			else{
				stmt.setNull(index++, Types.TIMESTAMP);
				stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE);
			}
			
			stmt.setInt(index++, bean.getNumeroAvanzamento());
			stmt.setString(index++, bean.getFlagPagamento());
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataAnticipazione()));
			stmt.setBigDecimal(index++, bean.getImportoAnticipazione());
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataRaggiungimento()));
			stmt.setBigDecimal(index++, bean.getImportoSal());
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataCertificato()));
			stmt.setBigDecimal(index++, bean.getImportoCertificato());
			stmt.setString(index++, bean.getFlagRitardo());
			stmt.setInt(index++, bean.getNumeroGiorniScost());
			stmt.setInt(index++, bean.getNumeroGiorniProroga());
			stmt.setString(index++, bean.getDenomStatoAvanz());
			stmt.setLong(index++, bean.getIdAvanzamento());
			stmt.setTimestamp(index++, bean.getDataInizioAvanzamento());
			int num = stmt.executeUpdate();
			
			List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(bean.getIdAvanzamento());
			attributiChiave.add(bean.getDataInizioAvanzamento());
			if(confirm)
				LogBloccoDatiManager.loggingCONFIRM(activeConnection, logger, cfUtente, ParametriServletAvanzamento.TAB_AVANZAMENTO, attributiChiave);
			else 
				LogBloccoDatiManager.loggingUPDATE(activeConnection, logger, cfUtente, ParametriServletAvanzamento.TAB_AVANZAMENTO, attributiChiave);
			return num;
		}finally{
			close(null,stmt);
		}
	}
	
	/**
	 * metodo per il salvataggio di un'avanzamento
	 * 
	 * @param bean AvanzamentoBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public int save(AvanzamentoBean bean, String cfUtente)throws SQLException{
		return update(bean, cfUtente, false);
	}
	
	/**
	 * metodo per la conferma di un'avanzamento
	 * 
	 * @param bean AvanzamentoBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public int confirm(AvanzamentoBean bean, String cfUtente)throws SQLException{
		return update(bean, cfUtente, true);
	}
	
	/**
	 * metodo per la storicizzazione  di un record
	 * 
	 * @param idRecord long
	 * @param dataInizioRecord Timestamp
	 * @return Timestamp - nuova data record
	 * @throws SQLException
	 */
	public Timestamp copyRecord(long idRecord, Timestamp dataInizioRecord) throws SQLException{
		
		String QUERY_SELECT_DATA_FINE = "SELECT " + STATI_AVANZ.DATA_FINE_AVANZAMENTO
		+ " FROM " + STATI_AVANZ.TABLE_NAME
		+ " WHERE " + STATI_AVANZ.ID_AVANZAMENTO + " = ? "
		+ " AND " + STATI_AVANZ.DATA_INIZIO_AVANZAMENTO + " = ?"
		+ " AND " + STATI_AVANZ.ID_STATO + " = " + StatiScheda.CONFERMATO;
		
		String UPDATE_STATO_OLD_RECORD = 
			"UPDATE " + STATI_AVANZ.TABLE_NAME + " SET "
			+ STATI_AVANZ.ID_STATO + " = ? "
			+ ", " + STATI_AVANZ.DATA_INIZIO_AVANZAMENTO + " = ?"
			+ ", " + STATI_AVANZ.DATA_FINE_AVANZAMENTO + " = ?"
			+ " WHERE " + STATI_AVANZ.ID_AVANZAMENTO + " = ? "
			+ " AND " + STATI_AVANZ.DATA_INIZIO_AVANZAMENTO + " = ?"
			+ " AND " + STATI_AVANZ.ID_STATO + " = " + StatiScheda.CONFERMATO;
		
		
		String COPY_RECORD = "INSERT INTO " + STATI_AVANZ.TABLE_NAME + " ("
			+ STATI_AVANZ.ID_AVANZAMENTO
			+ ", " + STATI_AVANZ.ID_AGGIUDICAZIONE
			+ ", " + STATI_AVANZ.DATA_INIZIO_AGGIUDICAZIONE
			+ ", " + STATI_AVANZ.NUMERO_AVANZAMENTO
			+ ", " + STATI_AVANZ.FLAG_PAGAMENTO
			+ ", " + STATI_AVANZ.DATA_ANTICIPAZIONE
			+ ", " + STATI_AVANZ.IMPORTO_ANTICIPAZIONE
			+ ", " + STATI_AVANZ.DATA_RAGGIUNGIMENTO
			+ ", " + STATI_AVANZ.IMPORTO_SAL
			+ ", " + STATI_AVANZ.DATA_CERTIFICATO
			+ ", " + STATI_AVANZ.IMPORTO_CERTIFICATO
			+ ", " + STATI_AVANZ.FLAG_RITARDO
			+ ", " + STATI_AVANZ.NUM_GIORNI_SCOST
			+ ", " + STATI_AVANZ.NUM_GIORNI_PROROGA
			+ ", " + STATI_AVANZ.ID_SCHEDA_LOCALE
			+ ", " + STATI_AVANZ.DATA_INIZIO_AVANZAMENTO
			+ ", " + STATI_AVANZ.DATA_FINE_AVANZAMENTO
			+ ", " + STATI_AVANZ.ID_STATO
			+ ", " + STATI_AVANZ.DENOM_AVANZ
			 + ") SELECT "
		     + STATI_AVANZ.ID_AVANZAMENTO
			+ ", " + STATI_AVANZ.ID_AGGIUDICAZIONE
			+ ", " + STATI_AVANZ.DATA_INIZIO_AGGIUDICAZIONE
			+ ", " + STATI_AVANZ.NUMERO_AVANZAMENTO
			+ ", " + STATI_AVANZ.FLAG_PAGAMENTO
			+ ", " + STATI_AVANZ.DATA_ANTICIPAZIONE
			+ ", " + STATI_AVANZ.IMPORTO_ANTICIPAZIONE
			+ ", " + STATI_AVANZ.DATA_RAGGIUNGIMENTO
			+ ", " + STATI_AVANZ.IMPORTO_SAL
			+ ", " + STATI_AVANZ.DATA_CERTIFICATO
			+ ", " + STATI_AVANZ.IMPORTO_CERTIFICATO
			+ ", " + STATI_AVANZ.FLAG_RITARDO
			+ ", " + STATI_AVANZ.NUM_GIORNI_SCOST
			+ ", " + STATI_AVANZ.NUM_GIORNI_PROROGA
			+ ", " + STATI_AVANZ.ID_SCHEDA_LOCALE
			+ ", ?"
			+ ", ?"
			+ ", ?"
			+ ", " + STATI_AVANZ.DENOM_AVANZ
			+ " FROM " + STATI_AVANZ.TABLE_NAME
			+ " WHERE " + STATI_AVANZ.ID_AVANZAMENTO + " = ? "
			+ " AND " + STATI_AVANZ.DATA_INIZIO_AVANZAMENTO + " = ?";
		
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
				dataFine = rs.getTimestamp(STATI_AVANZ.DATA_FINE_AVANZAMENTO);
				
				//il record corrente diventa il nuovo record
				index = 1;
				nuovaDataRecord = getNow();
				updateRecord = activeConnection.prepareStatement(UPDATE_STATO_OLD_RECORD);
				updateRecord.setInt(index++, StatiScheda.IN_DEFINIZIONE);
				updateRecord.setTimestamp(index++, nuovaDataRecord);
				updateRecord.setNull(index++, Types.TIMESTAMP);
				updateRecord.setLong(index++, idRecord);
				updateRecord.setTimestamp(index++, dataInizioRecord);
				updateRecord.execute();
				
				//copy record
				index = 1;
				copyRecord = activeConnection.prepareStatement(createCopyRecord(COPY_RECORD,STATI_AVANZ.TABLE_NAME));
				copyRecord.setTimestamp(index++, dataInizioRecord);
				copyRecord.setTimestamp(index++, dataFine);
				copyRecord.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA);
				copyRecord.setLong(index++, idRecord);
				copyRecord.setTimestamp(index++, nuovaDataRecord);
				copyRecord.execute();
				
				return nuovaDataRecord;
			}
			return null;
		}finally{
			close(rs, getDataFine);
			close(null, updateRecord);
			close(null, copyRecord);
		}
	
	}
	
	/**
	 * metodo per il controllo dell'esistenza di un'avanzamento
	 * 
	 * @param idAvanzamento long
	 * @param dataInizioRecord Timestamp
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean existAvanzamento(long idAvanzamento, Timestamp dataInizioRecord) throws SQLException{

		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM " + STATI_AVANZ.TABLE_NAME + " WHERE " + 
		STATI_AVANZ.ID_AVANZAMENTO + " = ? AND " + 
		STATI_AVANZ.DATA_INIZIO_AVANZAMENTO + " = ?";
		
		try{
			stmt = activeConnection.prepareStatement(query);
			stmt.setLong(1, idAvanzamento);
			stmt.setTimestamp(2, dataInizioRecord);
			rs = stmt.executeQuery();
			return (rs.next());
		}finally{
			close(rs, stmt);
		}
	}
	
	
	
	

	
	
	private static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO = 
		"UPDATE "+STATI_AVANZ.TABLE_NAME+
		" SET " + STATI_AVANZ.ID_STATO + " = ?," 
		+ STATI_AVANZ.DATA_FINE_AVANZAMENTO + " = " + buildGetDate()
		+ " WHERE " + STATI_AVANZ.ID_AVANZAMENTO + " = ?"
		+ " AND " + STATI_AVANZ.DATA_INIZIO_AVANZAMENTO + " = ?";
	
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
	
	
	private String DELETE_RECORD_AVANZ = 
		"DELETE FROM " + STATI_AVANZ.TABLE_NAME
		+ " WHERE " + STATI_AVANZ.ID_AVANZAMENTO + " = ?"
		+ " AND " + STATI_AVANZ.DATA_INIZIO_AVANZAMENTO + " = ?";
	
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
			
			stmt = activeConnection.prepareStatement(DELETE_RECORD_AVANZ);
			stmt.setLong(index++, idRecord);
			stmt.setTimestamp(index++, dataInizioRecord);
			return stmt.executeUpdate();
		}finally{
			close(null,stmt);
		}
	}
	/*********************************************************************************************************/
	/**************************		NUOVE FUNZIONALITA' 	**************************************************/
	/*********************************************************************************************************/

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(long, java.sql.Timestamp, java.lang.String)
	 */
	public boolean annulla(long idAvanzamento, Timestamp dataInizioAvanzamento, String cfUtente)throws SQLException {
		return _annulla(idAvanzamento, dataInizioAvanzamento, cfUtente);
		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(long, java.lang.String)
	 */
	public boolean annulla(long idSimog, String cfUtente) throws SQLException {
		AvanzamentoBean avanBean = loadByIdSimog(idSimog);
		
		if (avanBean.getIdAvanzamento() > 0){
			return _annulla(avanBean.getIdAvanzamento(), avanBean.getDataInizioAvanzamento(), cfUtente);
		}
		return false;
		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean annulla(String idLocale, String rifSimog, String cfUtente)throws SQLException {
		AvanzamentoBean avanBean = loadByIdLocale(idLocale, rifSimog);
		
		if (avanBean.getIdAvanzamento() > 0){
			return _annulla(avanBean.getIdAvanzamento(), avanBean.getDataInizioAvanzamento(), cfUtente);
		}
		return false;
		
	}
	/**
	 * @param idAvanzamento
	 * @param dataInizioAvanzamento
	 * @param cfUtente
	 * @throws SQLException
	 */
	private boolean _annulla(long idAvanzamento, Timestamp dataInizioAvanzamento, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_AVANZAMENTO);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idAvanzamento);
			stmt.setTimestamp(index++, dataInizioAvanzamento);
			someRowAffected = stmt.executeUpdate() > 0;
			if(someRowAffected){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idAvanzamento);
				attributiChiave.add(dataInizioAvanzamento);
					LogBloccoDatiManager.loggingDELETE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_AVANZAMENTO, attributiChiave);
			}
			return someRowAffected;
		}
		finally {
			close(null,stmt);
		}

	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadAvanzamento#fillBean(java.sql.ResultSet, it.avlp.simog.beans.avanzamento.AvanzamentoBean)
	 */
	public void fillBean(ResultSet rs, AvanzamentoBean avanBean)throws SQLException {
		avanBean.setDataAnticipazione(PageHelper.getViewDate(rs.getString(STATI_AVANZ.DATA_ANTICIPAZIONE)));
		avanBean.setDataCertificato(PageHelper.getViewDate(rs.getString(STATI_AVANZ.DATA_CERTIFICATO)));
		avanBean.setDataFineAvanzamento(rs.getTimestamp(STATI_AVANZ.DATA_FINE_AVANZAMENTO));
		avanBean.setDataInizioAggiudicazione(rs.getTimestamp(STATI_AVANZ.DATA_INIZIO_AGGIUDICAZIONE));
		avanBean.setDataInizioAvanzamento(rs.getTimestamp(STATI_AVANZ.DATA_INIZIO_AVANZAMENTO));
		avanBean.setDataRaggiungimento(PageHelper.getViewDate(rs.getString(STATI_AVANZ.DATA_RAGGIUNGIMENTO)));
		avanBean.setFlagPagamento(rs.getString(STATI_AVANZ.FLAG_PAGAMENTO));
		avanBean.setFlagRitardo(rs.getString(STATI_AVANZ.FLAG_RITARDO));
		avanBean.setIdAggiudicazione(rs.getLong(STATI_AVANZ.ID_AGGIUDICAZIONE));
		avanBean.setIdAvanzamento(rs.getLong(STATI_AVANZ.ID_AVANZAMENTO));
		avanBean.setIdStato(rs.getLong(STATI_AVANZ.ID_STATO));
		avanBean.setImportoAnticipazione(rs.getBigDecimal(STATI_AVANZ.IMPORTO_ANTICIPAZIONE));
		avanBean.setImportoCertificato(rs.getBigDecimal(STATI_AVANZ.IMPORTO_CERTIFICATO));
		avanBean.setImportoSal(rs.getBigDecimal(STATI_AVANZ.IMPORTO_SAL));
		avanBean.setNumeroAvanzamento(rs.getInt(STATI_AVANZ.NUMERO_AVANZAMENTO));
		avanBean.setNumeroGiorniProroga(rs.getInt(STATI_AVANZ.NUM_GIORNI_PROROGA));
		avanBean.setNumeroGiorniScost(rs.getInt(STATI_AVANZ.NUM_GIORNI_SCOST));
		avanBean.setDenomStatoAvanz(rs.getString(STATI_AVANZ.DENOM_AVANZ));		
		avanBean.setDescrizioneStato(rs.getString(STATI_SCHEDA.DESCRIZIONE));
		avanBean.setIdLocale(rs.getString(STATI_AVANZ.ID_SCHEDA_LOCALE));
		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadAvanzamento#loadByIdLocale(java.lang.String, java.lang.String)
	 */
	public AvanzamentoBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		AvanzamentoBean ab = null;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_SELECT_ONE_AVANZAMENTO + WHERE_IDLOCALE);
			stmt.setLong(1, Long.parseLong(idAggiudicazione));
			stmt.setString(2, idLocale);
			rs = stmt.executeQuery();
			ab = new AvanzamentoBean();
			if(rs.next()){
				fillBean(rs, ab);
			}
			return ab;
		}finally {
			close(null,stmt);
		}

	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadAvanzamento#loadByIdSimog(long)
	 */
	public AvanzamentoBean loadByIdSimog(long idSimog) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		AvanzamentoBean ab = null;
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_ONE_AVANZAMENTO + WHERE_IDSIMOG);
			stmt.setLong(1, idSimog);
			rs = stmt.executeQuery();
			ab = new AvanzamentoBean();
			if(rs.next()){
				fillBean(rs, ab);
			}
			return ab;
		}finally {
			close(null,stmt);
		}
	}

//	public boolean annullaByAggiudicazione(long idAggiudicazione,Timestamp dataInizioAggiudicazione, String cfUtente)throws SQLException {
//		List<AvanzamentoBean> listOfAvanzamenti = this.loadMany(idAggiudicazione, dataInizioAggiudicazione);
//		boolean esitOperazione = listOfAvanzamenti.size() > 0 ? true : false;
//		for(AvanzamentoBean avanzamentoCorrente : listOfAvanzamenti){
//			esitOperazione = esitOperazione && _annulla(avanzamentoCorrente.getIdAvanzamento(), avanzamentoCorrente.getDataInizioAvanzamento(), cfUtente);
//		}
//		return esitOperazione;
//	}
	
	
}
