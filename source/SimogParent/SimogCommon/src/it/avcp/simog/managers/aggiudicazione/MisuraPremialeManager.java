package it.avcp.simog.managers.aggiudicazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.MisuraPremialeBean;
import it.avlp.simog.beans.MisuraPremialeLottoBean;
import it.avlp.simog.beans.MotivoDerogaLottoBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.LOTTO_MOTIVO_DEROGA;
import it.avlp.simog.db.generated.MISURA_PREMIALE;
import it.avlp.simog.db.generated.MISURA_PREMIALE_LOTTO;
import it.avlp.simog.db.generated.MOTIVO_DEROGA;
import it.avlp.simog.db.generated.MOTIVO_DEROGA_LOTTO;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;

/**
 * Classe che si occupa della lettura/scrittura dei dati relativi alle
 * condizioni di aggiudicazione
 *
 */
public class MisuraPremialeManager extends AccessiDB implements IAnnullamentoMulti {
	public static String CLAZZ = "MisuraPremialeManager";

	/**
	 * Costruttore della classe
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger                  Logger
	 */
	public MisuraPremialeManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}

	private static String QUERY_SELECT_MISURA_PREMIALE_LOTTO = "SELECT * FROM " + MISURA_PREMIALE_LOTTO.TABLE_NAME
			+ " WHERE " + MISURA_PREMIALE_LOTTO.ID_LOTTO + " = ?";
	private static String QUERY_DELETE_MISURA_PREMIALE_LOTTO = "DELETE FROM " + MISURA_PREMIALE_LOTTO.TABLE_NAME
			+ " WHERE " + MISURA_PREMIALE_LOTTO.ID_LOTTO + " = ?";

	private static String QUERY_UPDATE_MISURA_PREMIALE_LOTTO = "INSERT INTO " + MISURA_PREMIALE_LOTTO.TABLE_NAME + "( "
			+ MISURA_PREMIALE_LOTTO.DATA_INIZIO_VALIDITA + ", " + MISURA_PREMIALE_LOTTO.ID_MISURA_PREMIALE + ", "
			+ MISURA_PREMIALE_LOTTO.ID_LOTTO + ", " + MISURA_PREMIALE_LOTTO.DATA_FINE_VALIDITA + ", "
			+ MISURA_PREMIALE_LOTTO.DATA_ULTIMA_MODIFICA + " ) VALUES (" + "?, ?, ?, ?, ?)";

	private static final String QUERY_JOIN_MISURA_PREMIALE_LOTTO_BY_ID_LOTTO_NO_DATA_FINE = String.format("SELECT %s,%s FROM %s,%s WHERE %s = %s AND %s = ? AND %s IS NULL",
			//SELECT
			MISURA_PREMIALE_LOTTO.T_ID_LOTTO,
			MISURA_PREMIALE_LOTTO.T_ID_MISURA_PREMIALE,
			//FROM
			MISURA_PREMIALE_LOTTO.TABLE_NAME,
			MISURA_PREMIALE.TABLE_NAME,
			//WHERE
			MISURA_PREMIALE_LOTTO.T_ID_MISURA_PREMIALE,
			MISURA_PREMIALE.T_ID_MISURA,
			MISURA_PREMIALE_LOTTO.T_ID_LOTTO,
			MISURA_PREMIALE_LOTTO.T_DATA_FINE_VALIDITA
			);
	
	//MEV 37010 3.04.8.1
	private static String QUERY_UPDATE_DATA_FINE_VALIDITA = "UPDATE " + MISURA_PREMIALE_LOTTO.TABLE_NAME + " SET " + MISURA_PREMIALE_LOTTO.DATA_FINE_VALIDITA + " = convert(varchar, getdate(), 112) "
	+ " WHERE " + MISURA_PREMIALE_LOTTO.ID_LOTTO + " = ?";
	//MEV 37010 3.04.8.1
	// TICKET ALM #3835
	/**
	 * Carica tutti le condizioni associate ad un lotto
	 * 
	 * @param idLotto     long
	 * @param ignoraStato TODO
	 * @return List&lt;CondizioneAggBean&gt; - la lista delle condizioni associate a
	 *         un lotto
	 * @throws SQLException
	 */
	public List<MisuraPremialeLottoBean> loadManyMisuraPremialeLotto(long idLotto) throws SQLException {

		String qry = QUERY_SELECT_MISURA_PREMIALE_LOTTO;

		PreparedStatement stmt = activeConnection.prepareStatement(qry);
		ResultSet rs = null;
		int index = 1;
		ArrayList<MisuraPremialeLottoBean> ris = new ArrayList<MisuraPremialeLottoBean>();
		MisuraPremialeLottoBean nuovaMisuraPremiale = null;
		try {
			stmt.setLong(index++, idLotto);
			rs = stmt.executeQuery();
			while (rs.next()) {
				nuovaMisuraPremiale = new MisuraPremialeLottoBean();
				nuovaMisuraPremiale
						.setIdMisuraPremialeLotto(rs.getLong(MISURA_PREMIALE_LOTTO.ID_MISURA_PREMIALE_LOTTO));
				nuovaMisuraPremiale.setIdLotto(rs.getLong(MISURA_PREMIALE_LOTTO.ID_LOTTO));
				nuovaMisuraPremiale.setIdMisuraPremiale(rs.getLong(MISURA_PREMIALE_LOTTO.ID_MISURA_PREMIALE));
				nuovaMisuraPremiale.setDataFineValidita(rs.getString(MISURA_PREMIALE_LOTTO.DATA_FINE_VALIDITA));
				nuovaMisuraPremiale.setDataInizioValidita(rs.getString(MISURA_PREMIALE_LOTTO.DATA_INIZIO_VALIDITA));
				nuovaMisuraPremiale.setDataUltimaModifica(rs.getString(MISURA_PREMIALE_LOTTO.DATA_ULTIMA_MODIFICA));
				ris.add(nuovaMisuraPremiale);
			}

		} finally {
			close(rs, stmt);
		}

		return ris;

	}
	
	//MEV 37010 3.04.8.1
		public List<MisuraPremialeLottoBean> loadManyNoFineValidita(long idLotto) throws SQLException {
		      PreparedStatement stmt = null;
		      ResultSet rs = null;
		      
		      List<MisuraPremialeLottoBean> listaMisure = new ArrayList<MisuraPremialeLottoBean>();
		      
		      try {
		               
		         stmt = activeConnection.prepareStatement(QUERY_JOIN_MISURA_PREMIALE_LOTTO_BY_ID_LOTTO_NO_DATA_FINE);
		         
		         stmt.setObject(1, idLotto);

		         rs = stmt.executeQuery();

		         while (rs.next()) {

		        	 MisuraPremialeLottoBean misuraPremialeLottoBean = new MisuraPremialeLottoBean();
		        	 String string = rs.getString(MISURA_PREMIALE_LOTTO.ID_LOTTO);
		        	 misuraPremialeLottoBean.setIdLotto(Long.parseLong(string));
		        	 misuraPremialeLottoBean.setIdMisuraPremiale(rs.getLong(MISURA_PREMIALE_LOTTO.ID_MISURA_PREMIALE));
		        	 
		        	 listaMisure.add(misuraPremialeLottoBean);	                  
		         }
		      } catch (Exception e) {
		         logger.error("Impossibile caricare i motivi deroga", e);
		      } finally {
		         close(rs, stmt);
		      }
		      return listaMisure;
		   }
		
		//MEV 37010 3.04.8.1
				public List<MisuraPremialeBean> loadManyNoFineValiditaMisuraPremialeBean(long idLotto) throws SQLException {
				      PreparedStatement stmt = null;
				      ResultSet rs = null;
				      
				      List<MisuraPremialeBean> listaMisure = new ArrayList<MisuraPremialeBean>();
				      
				      try {
				               
				         stmt = activeConnection.prepareStatement(QUERY_JOIN_MISURA_PREMIALE_LOTTO_BY_ID_LOTTO_NO_DATA_FINE);
				         
				         stmt.setObject(1, idLotto);

				         rs = stmt.executeQuery();

				         while (rs.next()) {

				        	 MisuraPremialeBean misuraPremialeLottoBean = new MisuraPremialeBean();
				        	 String string = rs.getString(MISURA_PREMIALE_LOTTO.ID_LOTTO);
				        	 misuraPremialeLottoBean.setIdMisuraPremiale(rs.getLong(MISURA_PREMIALE_LOTTO.ID_MISURA_PREMIALE));
				        	 
				        	 listaMisure.add(misuraPremialeLottoBean);	                  
				         }
				      } catch (Exception e) {
				         logger.error("Impossibile caricare i motivi deroga", e);
				      } finally {
				         close(rs, stmt);
				      }
				      return listaMisure;
				   }
	
	public void updateDataFineValiditaMisuraPremialeLotto(long idLotto) throws SQLException {
		PreparedStatement stmt = activeConnection.prepareStatement(QUERY_UPDATE_DATA_FINE_VALIDITA);
		try {
			stmt.setLong(1, idLotto);
			stmt.execute();
			logger.debug(CLAZZ + ": modificata data fine validita misura premiale: idLotto=" + idLotto);
		} finally {
			close(null, stmt);
		}
	}

	public void createMisuraPremialeLottoRelation(long idLotto, List<MisuraPremialeBean> elencoMisurePremiali)
			throws SQLException {

		if(elencoMisurePremiali != null) {
			for (MisuraPremialeBean misuraPremialeBean : elencoMisurePremiali) {
				this.updateMisuraPremialeLotto(misuraPremialeBean, idLotto);
			}
		}
	}

	/**
	 * metodo per la conferma della condizione , nel passaggio viene settato anche
	 * il campo del bean per lo stato
	 * 
	 * @param misuraPremialeBean MisuraPremialeLottoBean
	 * @throws SQLException
	 */
	public void confirmMisuraPremialeLottoBean(MisuraPremialeBean misuraPremialeBean, long idLotto)
			throws SQLException {
		updateMisuraPremialeLotto(misuraPremialeBean, idLotto);
	}

	/**
	 * metodo per l'inserimento/salvataggio di una condizione nello stato di "in
	 * definizione", nel passaggio viene settato nel bean in ingresso, se non
	 * &egrave presente la data di inizio condizione
	 * 
	 * @param misuraPremialeBean MisuraPremialeAggBean
	 * @throws SQLException
	 */
	public void saveMisuraPremialeBean(MisuraPremialeBean misuraPremialeBean, long idLotto) throws SQLException {
		updateMisuraPremialeLotto(misuraPremialeBean, idLotto);
	}

	/**
	 * Aggiunge una nuova condizione
	 * 
	 * param condizioneBean MisuraPremialeAggBean throws SQLException
	 */
	private void updateMisuraPremialeLotto(MisuraPremialeBean misuraPremialeBean, long idLotto) throws SQLException {
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";

		PreparedStatement stmt = activeConnection.prepareStatement(
				createInsertQuery(QUERY_UPDATE_MISURA_PREMIALE_LOTTO, MISURA_PREMIALE_LOTTO.ID_MISURA_PREMIALE_LOTTO));

		ResultSet rs = null;
		int index = 1;
		MisuraPremialeLottoBean misuraPremialeLottoBean = new MisuraPremialeLottoBean();
		try {
			if (misuraPremialeLottoBean.getDataInizioValidita() == null
					|| misuraPremialeLottoBean.getDataInizioValidita() == "") {
				SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
				misuraPremialeLottoBean.setDataInizioValidita(yyyyMMdd.format(getNow()));
			}
			stmt.setObject(index++, misuraPremialeLottoBean.getDataInizioValidita());
			stmt.setObject(index++, misuraPremialeBean.getIdMisuraPremiale());
			stmt.setLong(index++, idLotto);
			stmt.setObject(index++, null);
			stmt.setObject(index++, misuraPremialeLottoBean.getDataInizioValidita());

			stmt.execute();
			logger.debug(
					logPrefix + ObjectIntrospector.propertiesInfo(MisuraPremialeLottoBean.class, misuraPremialeBean));
		} finally {
			close(rs, stmt);
		}
	}

	private static final String QUERY_JOIN_MISURE_PREMIALI_LOTTO_BY_ID_LOTTO = String.format(
			"SELECT %s,%s FROM %s,%s WHERE %s = %s AND %s = ?",
			// SELECT
			MISURA_PREMIALE_LOTTO.T_ID_LOTTO, MISURA_PREMIALE_LOTTO.T_ID_MISURA_PREMIALE,
			// FROM
			MISURA_PREMIALE_LOTTO.TABLE_NAME, MISURA_PREMIALE.TABLE_NAME,
			// WHERE
			MISURA_PREMIALE_LOTTO.T_ID_MISURA_PREMIALE, MISURA_PREMIALE.T_ID_MISURA, MISURA_PREMIALE_LOTTO.T_ID_LOTTO);

	public List<MisuraPremialeLottoBean> loadMany(long idLotto) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<MisuraPremialeLottoBean> listaMotivi = new ArrayList<MisuraPremialeLottoBean>();

		try {

			stmt = activeConnection.prepareStatement(QUERY_JOIN_MISURE_PREMIALI_LOTTO_BY_ID_LOTTO);

			stmt.setObject(1, idLotto);

			rs = stmt.executeQuery();

			while (rs.next()) {

				MisuraPremialeLottoBean misuraPremialeLottoBean = new MisuraPremialeLottoBean();
				String string = rs.getString(LOTTO_MOTIVO_DEROGA.ID_LOTTO);
				misuraPremialeLottoBean.setIdLotto(Long.parseLong(string));
				misuraPremialeLottoBean.setIdMisuraPremiale(rs.getLong(MISURA_PREMIALE_LOTTO.ID_MISURA_PREMIALE));

				listaMotivi.add(misuraPremialeLottoBean);
			}
		} catch (Exception e) {
			logger.error("Impossibile caricare i motivi deroga", e);
		} finally {
			close(rs, stmt);
		}
		return listaMotivi;
	}

	/**
	 * metodo per la cancellazione delle condizioni associate ad un lotto
	 * 
	 * @param idLotto long
	 * @throws SQLException
	 */
	public void deleteMisuraPremialeLotto(long idLotto) throws SQLException {
		PreparedStatement stmt = activeConnection.prepareStatement(QUERY_DELETE_MISURA_PREMIALE_LOTTO);
		try {
			stmt.setLong(1, idLotto);
			stmt.execute();
			logger.debug(CLAZZ + ": Eliminato record: idLotto=" + idLotto);
		} finally {
			close(null, stmt);
		}
	}

	/*
	 * SELECT m.Id_Misura_Premiale, m.Descrizione FROM
	 * [Simog].[dbo].[LOTTO_MISURA_PREMIALE] as l, simog.dbo.MISURA_PREMIALE as m
	 * where m.Id_Misura_Premiale = l.Id_Misura_Premiale
	 */

	private static final String QUERY_JOIN_MISURE_PREMIALI_LOTTO = String.format(
			"SELECT %s,%s FROM %s,%s WHERE %s >= ? AND %s >= ? AND %s = %s",
			// SELECT
			MISURA_PREMIALE_LOTTO.T_ID_MISURA_PREMIALE, MISURA_PREMIALE.DESCRIZIONE,
			// FROM
			MISURA_PREMIALE_LOTTO.TABLE_NAME, MISURA_PREMIALE.TABLE_NAME,
			// WHERE
			buildISNULL(MISURA_PREMIALE.T_DATA_FINE_VALIDITA, "99999999"),
			buildISNULL(MISURA_PREMIALE_LOTTO.T_DATA_FINE_VALIDITA, "99999999"),
			MISURA_PREMIALE_LOTTO.T_ID_MISURA_PREMIALE, MISURA_PREMIALE.T_ID_MISURA);

	public Map<String, String> caricaLottoComboMisurePremiali(Object o) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		HashMap<String, String> listaMisure = new HashMap<String, String>();
		try {

			stmt = activeConnection.prepareStatement(QUERY_JOIN_MISURE_PREMIALI_LOTTO);
			String dataFine = PageHelper.getFormattedNowOrInputFormattedDate(o, getNow());

			stmt.setObject(1, dataFine);
			stmt.setObject(2, dataFine);

			rs = stmt.executeQuery();

			while (rs.next()) {
				listaMisure.put(rs.getString(MISURA_PREMIALE_LOTTO.ID_MISURA_PREMIALE),
						rs.getString(MISURA_PREMIALE.DESCRIZIONE));
			}
		} catch (Exception e) {
			logger.error("Impossibile caricare le misure premiali", e);
		} finally {
			close(rs, stmt);
		}
		return listaMisure;
	}

	private static final String QUERY_JOIN_MISURE_PREMIALI = String.format("SELECT %s,%s FROM %s WHERE %s >= ? ORDER BY %s",
			// SELECT
			MISURA_PREMIALE.ID_MISURA, MISURA_PREMIALE.DESCRIZIONE,
			// FROM
			MISURA_PREMIALE.TABLE_NAME,
			// WHERE
			buildISNULL(MISURA_PREMIALE.T_DATA_FINE_VALIDITA, "99999999"),
			MISURA_PREMIALE.ID_MISURA);

	public Map<Integer, String> caricaMisurePremiali(Object o) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		HashMap<Integer, String> listaMisurePremiali = new HashMap<Integer, String>();
		try {

			stmt = activeConnection.prepareStatement(QUERY_JOIN_MISURE_PREMIALI);
			String dataFine = PageHelper.getFormattedNowOrInputFormattedDate(o, getNow());

			stmt.setObject(1, dataFine);
			rs = stmt.executeQuery();

			while (rs.next()) {
				listaMisurePremiali.put(rs.getInt(MISURA_PREMIALE.ID_MISURA),
						rs.getString(MISURA_PREMIALE.DESCRIZIONE));
			}
		} catch (Exception e) {
			logger.error("Impossibile caricare le misure premiali", e);
		} finally {
			close(rs, stmt);
		}
		return listaMisurePremiali;
	}

	public void aggiornaMisuraPremialeLotto(Lotto lotto) throws SQLException {
		deleteMisuraPremialeLotto(lotto.getId_Lotto());
		createMisuraPremialeLottoRelation(lotto.getId_Lotto(), lotto.getElencoMisurePremiali());
	}

	@Override
	public boolean annulla(long idSchedaPadre, Timestamp dataInizioSchedaPadre) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

//	private static String QUERY_DELETE_CONDIZIONI_AGG = 
//	"DELETE FROM "+CONDIZIONI_AGG.TABLE_NAME+
//	" WHERE " + CONDIZIONI_AGG.ID_AGGIUDICAZIONE + " = ?"+
//	" AND "+CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
//
///**
// * Metodo per la cancellazione di un record
// * 
// * @param idRecord String
// * @param dataInizioRecord Timestamp
// * @return int - affected row count
// * @throws SQLException
// */
//public int deleteRecord(String idRecord, Timestamp dataInizioRecord) throws SQLException{
//	
//	int numRow=-1;
//	PreparedStatement stmt = null;
//	try {
//		stmt = activeConnection.prepareStatement(QUERY_DELETE_CONDIZIONI_AGG);
//		logger.debug("query per la delete record attivo condizioni: "+QUERY_DELETE_CONDIZIONI_AGG);
//		int index = 1;
//		stmt.setInt(index++, Integer.parseInt(idRecord));
//		
//		stmt.setObject(index++,dataInizioRecord);
//		
//		numRow = stmt.executeUpdate();
//		 
//	} finally {
//		close(null, stmt);
//	}
//	return numRow;
//}
//
//private static String QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_CONDIZIONI_AGG = 
//	"UPDATE "+CONDIZIONI_AGG.TABLE_NAME+
//	" SET " + CONDIZIONI_AGG.ID_STATO + " = ?,"+
//	CONDIZIONI_AGG.DATA_FINE_COND + " = " + buildGetDate() +
//	" WHERE "+CONDIZIONI_AGG.ID_AGGIUDICAZIONE + " = ?"+
//	" AND "+CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
//
///**
// * metodo per la cancellazione delle condizioni associate ad un'aggiudicazione
// * 
// * @param idAggiudicazione long 
// * @param dataInizioAggiudicazione Timestamp
// * @throws SQLException
// */
//public void deleteMisuraPremialeAgg(long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{		
//	PreparedStatement stmt = activeConnection.prepareStatement(QUERY_DELETE_CONDIZIONI_AGG);
//	try{
//		stmt.setLong(1, idAggiudicazione);
//		stmt.setTimestamp(2, dataInizioAggiudicazione);
//		stmt.execute();
//		logger.debug(CLAZZ + ": Eliminato record: idAggiudicazione=" + 
//				idAggiudicazione + " dataInizioAgg=" + dataInizioAggiudicazione );
//	}finally{
//		close(null,stmt);
//	}
//}
//
///**
// * metodo per l'aggionrnamento del record allo stato di cui Stringa in ingresso
// * 
// * @param idRecord String
// * @param dataInizioRecord Timestamp
// * @param stato_scheda String
// * @return int - affected row count
// * @throws SQLException
// */
//public int updateRecord(String idRecord, Timestamp dataInizioRecord, String stato_scheda ) throws SQLException{
//	
//	int numRow = -1; 
//	PreparedStatement stmt = null;
//	ResultSet rs = null;
//	try {
//		stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_CONDIZIONI_AGG);
//		logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_CONDIZIONI_AGG);
//
//		stmt.setObject(1, stato_scheda);
//		logger.debug(1 + ": "+stato_scheda);
//		
//		stmt.setInt(2, Integer.parseInt(idRecord));
//		logger.debug(2 + ": "+idRecord);
//		
//		stmt.setObject(3,dataInizioRecord);
//		logger.debug(3 + ": "+dataInizioRecord);
//		numRow = stmt.executeUpdate();
//		//PPactiveConnection.commit(currentActiveConnection);
//	} finally {
//		close(rs, stmt);
//	}
//	return numRow;
//}
//
//private static String QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_CONDIZIONI_AGG_NEWRECORD = 
//	"UPDATE "+CONDIZIONI_AGG.TABLE_NAME+
//	" SET " + CONDIZIONI_AGG.ID_STATO + " = ?,"+ //+STATI_SCHEDA.CONFERMATO+
//	CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?"+
//	" WHERE "+CONDIZIONI_AGG.ID_AGGIUDICAZIONE + " = ?"+
//	" AND "+CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
//
//public int updateRecordToPointToNew(String idAggiudicazione, Timestamp dataInizioAggOld,Timestamp dataInizioAggNew, String stato_scheda ) throws SQLException{
//	int numRow = -1; 
//	PreparedStatement stmt = null;
//	ResultSet rs = null;
//	try {
//		stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_CONDIZIONI_AGG_NEWRECORD);
//		logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_CONDIZIONI_AGG_NEWRECORD);
//
//		stmt.setObject(1, stato_scheda);
//		logger.debug(1 + ": "+stato_scheda);
//		
//		stmt.setObject(2,dataInizioAggNew);
//		logger.debug(2 + ": "+dataInizioAggNew);
//		
//		stmt.setInt(3, Integer.parseInt(idAggiudicazione));
//		logger.debug(3 + ": "+idAggiudicazione);
//		
//		stmt.setObject(4,dataInizioAggOld);
//		logger.debug(4 + ": "+dataInizioAggOld);
//		numRow = stmt.executeUpdate();
//		//PPactiveConnection.commit(currentActiveConnection);
//	} finally {
//		close(rs, stmt);
//	}
//	return numRow;	
//}
//
//private static String QUERY_SELECT_CONDIZIONI = "SELECT * FROM " + CONDIZIONI_AGG.TABLE_NAME
//+ " WHERE " + CONDIZIONI_AGG.ID_AGGIUDICAZIONE + " = ? AND " 
//+ CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";

//TICKET ALM #3835	

	// FINE TICKET ALM #3835

//	private final String WHERE_STATO = " AND (" + CONDIZIONI_AGG.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
//	+" OR " + CONDIZIONI_AGG.T_ID_STATO + " = " + StatiScheda.CONFERMATO+")";
//	
//	private static String QUERY_UPDATE_CONDIZIONE = "INSERT INTO " + CONDIZIONI_AGG.TABLE_NAME + "( "
//	+ CONDIZIONI_AGG.DATA_INIZIO_COND + ", "
//	+ CONDIZIONI_AGG.ID_CONDIZIONE + ", "
//	+ CONDIZIONI_AGG.ID_STATO + ", "
//	+ CONDIZIONI_AGG.DATA_FINE_COND + ", "
//	+ CONDIZIONI_AGG.ID_AGGIUDICAZIONE + ", "
//	+ CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE 
//	+ " ) VALUES ("
//	+ "?, ?, ?, ?, ?, ? )";

	// TICKET ALM #3835
	// FINE TICKET ALM #3835

//	/**
//	 * Carica tutti le condizioni associate ad una aggiudicazione
//	 * 
//	 * @param idAggiudicazione long
//	 * @param dataInizioAggiudicazione Timestamp
//	 * @param ignoraStato TODO
//	 * @return List&lt;MisuraPremialeAggBean&gt; - la lista delle condizioni associate alla aggiudicazione
//	 * @throws SQLException
//	 */
//	public List<CondizioneAggBean> loadMany(long idAggiudicazione, Timestamp dataInizioAggiudicazione, boolean ignoraStato) throws SQLException{
//		
//		String qry = QUERY_SELECT_CONDIZIONI;
//		if(!ignoraStato)
//			qry += WHERE_STATO;
//		
//		PreparedStatement stmt = activeConnection.prepareStatement(qry);
//		ResultSet rs = null;
//		int index = 1;
//		ArrayList<CondizioneAggBean> ris = new ArrayList<CondizioneAggBean>();
//		CondizioneAggBean nuovaCondizione = null;
//		try{
//			stmt.setLong(index++, idAggiudicazione);
//			stmt.setTimestamp(index++,dataInizioAggiudicazione);
//			rs = stmt.executeQuery();
//			while(rs.next()){
//				nuovaCondizione = new CondizioneAggBean();
//				nuovaCondizione.setDataFineCond(rs.getTimestamp(CONDIZIONI_AGG.DATA_FINE_COND));
//				nuovaCondizione.setDataInizioAggiudicazione(rs.getTimestamp(CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE));
//				nuovaCondizione.setDataInizioCond(rs.getTimestamp(CONDIZIONI_AGG.DATA_INIZIO_COND));
//				nuovaCondizione.setIdAggiudicazione(rs.getLong(CONDIZIONI_AGG.ID_AGGIUDICAZIONE));
//				nuovaCondizione.setIdCondizione(rs.getLong(CONDIZIONI_AGG.ID_CONDIZIONE));
//				nuovaCondizione.setIdCondizioneAgg(rs.getLong(CONDIZIONI_AGG.ID_CONDIZIONE_AGG));
//				nuovaCondizione.setIdStato(rs.getInt(CONDIZIONI_AGG.ID_STATO));
//				ris.add(nuovaCondizione);
//				
//			}
//
//		}finally{
//			close(rs, stmt);
//		}
//		
//		return ris;
//	
//	}
//	
//	
//	/**
//	 * metodo per la conferma della condizione , nel passaggio viene settato anche 
//	 * il campo del bean per lo stato
//	 * 
//	 * @param condizioneBean CondizioneAggBean
//	 * @throws SQLException
//	 */
//	public void confirm(CondizioneAggBean condizioneBean) throws SQLException {
//		update(condizioneBean, true);
//	}
//
//	/**
//	 * metodo per l'inserimento/salvataggio di una condizione nello stato di "in definizione",
//	 * nel passaggio viene settato nel bean in ingresso, se non &egrave presente la data di inizio condizione
//	 * 
//	 * @param condizioneBean CondizioneAggBean
//	 * @throws SQLException
//	 */
//	public void save(CondizioneAggBean condizioneBean) throws SQLException {
//		update(condizioneBean, false);
//	}
//
//	/**
//	 * Aggiunge una nuova condizione
//	 * 
//	 * param condizioneBean CondizioneAggBean
//	 * throws SQLException
//	 */
//	private void update(CondizioneAggBean condizioneBean, boolean conferma) throws SQLException{
//		String mtd = "save";
//		String logPrefix = CLAZZ + "." + mtd + ": ";
//			
//		PreparedStatement stmt = activeConnection.prepareStatement(createInsertQuery(QUERY_UPDATE_MISURA_PREMIALE,CONDIZIONI_AGG.ID_MISURA_PREMIALE_AGG));
//		ResultSet rs = null;
//		int index = 1;		
//		try{			
//			
//			if(condizioneBean.getDataInizioCond() == null)
//				condizioneBean.setDataInizioCond(getNow());
//			stmt.setObject(index++, condizioneBean.getDataInizioCond());
//			stmt.setObject(index++, condizioneBean.getIdCondizione());
//			
//			if (conferma){
//				stmt.setLong(index++, StatiScheda.CONFERMATO);
//				stmt.setTimestamp(index++, getNow());
//				condizioneBean.setIdStato(StatiScheda.CONFERMATO);
//			}else{
//				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
//				stmt.setNull(index++, Types.TIMESTAMP);
//				condizioneBean.setIdStato(StatiScheda.IN_DEFINIZIONE);
//			}
//			stmt.setLong(index++, condizioneBean.getIdAggiudicazione());
//			stmt.setObject(index++, condizioneBean.getDataInizioAggiudicazione());
//			
//			stmt.execute();
//			logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(CondizioneAggBean.class, condizioneBean));
//		}finally{
//			close(rs, stmt);
//		}
//	
//	}
//	
//	//modificare la query di copy per la gestione di tutti i contenziosi
//	/**
//	 * metodo per la storicizzazione del record
//	 * 
//	 * @param id_record String
//	 * @param data_inizio_record Timestamp
//	 * @param vecchiaData Timestamp
//	 * @return boolean
//	 * @throws SQLException
//	 */
//	public boolean copyRecord(String id_record,Timestamp data_inizio_record, Timestamp vecchiaData) throws SQLException{
//		String QUERY_UPDATE_OLD_RECORD =
//			"UPDATE "+CONDIZIONI_AGG.TABLE_NAME+ " SET "
//			+ CONDIZIONI_AGG.ID_STATO+ " = ?, "
//			+ CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE+ " = ? "
//			
//			+" WHERE "
//			+CONDIZIONI_AGG.T_ID_AGGIUDICAZIONE+" = ?"
//			+" AND "+CONDIZIONI_AGG.T_DATA_INIZIO_AGGIUDICAZIONE+" = ?"
//			+" AND "+CONDIZIONI_AGG.T_ID_STATO + "=" + StatiScheda.CONFERMATO;
//		
//		String QUERY_COPY_RECORD =
//			"INSERT INTO "+CONDIZIONI_AGG.TABLE_NAME+" ("
//			+CONDIZIONI_AGG.ID_CONDIZIONE_AGG	
//			+","+CONDIZIONI_AGG.ID_AGGIUDICAZIONE
//			+","+CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE
//			+","+CONDIZIONI_AGG.ID_CONDIZIONE
//			+","+CONDIZIONI_AGG.DATA_INIZIO_COND
//			+","+CONDIZIONI_AGG.DATA_FINE_COND
//			+","+CONDIZIONI_AGG.ID_STATO+" ) "
//			+"SELECT "
//			+CONDIZIONI_AGG.ID_CONDIZIONE_AGG	
//			+","+CONDIZIONI_AGG.ID_AGGIUDICAZIONE
//			+","+CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE
//			+","+CONDIZIONI_AGG.ID_CONDIZIONE
//			+", ?"
//			+", ?"
//			+", ?"
//			+" FROM "+CONDIZIONI_AGG.TABLE_NAME
//			+" WHERE "
//			+CONDIZIONI_AGG.ID_AGGIUDICAZIONE+" = ? AND "
//			+CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE+" = ?"
//			+" AND "+CONDIZIONI_AGG.ID_STATO+" = "+StatiScheda.CONFERMATO;
//		PreparedStatement stmt = null;
//		PreparedStatement stmt2 = null;
//		try{
//			int index = 1;
//			stmt = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD,CONDIZIONI_AGG.TABLE_NAME));
//			stmt.setTimestamp(index++, getNow()); //data_inizio_incaricato
//			stmt.setNull(index++, Types.TIMESTAMP); // data_fine_incaricato
//			stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE); //stato scheda
//			stmt.setLong(index++, Long.parseLong(id_record));
//			stmt.setTimestamp(index++, data_inizio_record);
//			int rowsCopied = stmt.executeUpdate();
//			if(rowsCopied > 0){
//				index = 1;
//				stmt2 = activeConnection.prepareStatement(QUERY_UPDATE_OLD_RECORD);
//				stmt2.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA); //stato scheda
//				stmt2.setTimestamp(index++, vecchiaData); //data_inizio_aggiudicazione
//				stmt2.setLong(index++, Long.parseLong(id_record));
//				stmt2.setTimestamp(index++, data_inizio_record);
//				rowsCopied = stmt2.executeUpdate();
//				return (rowsCopied>0);
//			}
//			else {
//				logger.debug("RESPONSABILE_MANAGER.copyRecord: Nessun record da copiare");
//				return true;
//			}
//
//				
//		
//		}
//		finally{
//			close(null, stmt2);
//			close(null, stmt);
//		}
//	}
//
//	/**
//	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti#annulla(long, java.sql.Timestamp)
//	 */
//	public boolean annulla(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException {
//		return _annulla(idAggiudicazione, dataInizioAggiudicazione);
//		
//	}
//	/**
//	 * @param idAggiudicazione
//	 * @param dataInizioAggiudicazione
//	 * @throws SQLException
//	 */
//	private boolean _annulla(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
//		PreparedStatement stmt = null;
//		boolean someRowAffected = false;
//		try{ 
//			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_CONDIZIONI);
//			int index = 1;
//			stmt.setLong(index++, StatiScheda.ELIMINATO);
//			stmt.setTimestamp(index++, getNow());
//			stmt.setLong(index++, idAggiudicazione);
//			stmt.setTimestamp(index++, dataInizioAggiudicazione);
//			someRowAffected = stmt.executeUpdate() > 0;
//			return someRowAffected;
//		}finally {
//			close(null,stmt);
//		}		
//	}
}
