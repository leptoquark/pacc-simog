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
import it.avlp.simog.beans.MotivoDerogaBean;
import it.avlp.simog.beans.MotivoDerogaLottoBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.LOTTO_MOTIVO_DEROGA;
import it.avlp.simog.db.generated.MOTIVO_DEROGA;
import it.avlp.simog.db.generated.MOTIVO_DEROGA_LOTTO;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;

/**
 * Classe che si occupa della lettura/scrittura dei dati relativi alle
 * condizioni di aggiudicazione
 *
 */

public class MotivoDerogaManager extends AccessiDB implements IAnnullamentoMulti {
	public static String CLAZZ = "MotivoDerogaManager";

	private static String QUERY_SELECT_MOTIVO_DEROGA_LOTTO = "SELECT * FROM " + MOTIVO_DEROGA_LOTTO.TABLE_NAME
			+ " WHERE " + MOTIVO_DEROGA_LOTTO.ID_LOTTO + " = ?";

	private static String QUERY_UPDATE_MOTIVO_DEROGA_LOTTO = "INSERT INTO " + MOTIVO_DEROGA_LOTTO.TABLE_NAME + "( "
			+ MOTIVO_DEROGA_LOTTO.DATA_INIZIO_VALIDITA + ", " + MOTIVO_DEROGA_LOTTO.ID_MOTIVO_DEROGA + ", "
			+ MOTIVO_DEROGA_LOTTO.ID_LOTTO + ", " + MOTIVO_DEROGA_LOTTO.DATA_FINE_VALIDITA + ", "
			+ MOTIVO_DEROGA_LOTTO.DATA_ULTIMA_MODIFICA + " ) VALUES (" + "?, ?, ?, ?, ?)";

	private static String QUERY_DELETE_MOTIVO_DEROGA_LOTTO = "DELETE FROM " + MOTIVO_DEROGA_LOTTO.TABLE_NAME + " WHERE "
			+ MOTIVO_DEROGA_LOTTO.ID_LOTTO + " = ?";
	
	//MEV 37010 3.04.8.1
	private static String QUERY_UPDATE_DATA_FINE_VALIDITA = "UPDATE " + MOTIVO_DEROGA_LOTTO.TABLE_NAME + " SET " + MOTIVO_DEROGA_LOTTO.DATA_FINE_VALIDITA + " = convert(varchar, getdate(), 112) "
	+ " WHERE " + MOTIVO_DEROGA_LOTTO.ID_LOTTO + " = ?";
	//MEV 37010 3.04.8.1
	

	/**
	 * Costruttore della classe
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger                  Logger
	 */
	public MotivoDerogaManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}

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
	public List<MotivoDerogaLottoBean> loadManyMotivoDerogaLotto(long idLotto) throws SQLException {

		String qry = QUERY_SELECT_MOTIVO_DEROGA_LOTTO;

		PreparedStatement stmt = activeConnection.prepareStatement(qry);
		ResultSet rs = null;
		int index = 1;
		ArrayList<MotivoDerogaLottoBean> ris = new ArrayList<MotivoDerogaLottoBean>();
		MotivoDerogaLottoBean nuovaMotivoDeroga = null;
		try {
			stmt.setLong(index++, idLotto);
			rs = stmt.executeQuery();
			while (rs.next()) {
				nuovaMotivoDeroga = new MotivoDerogaLottoBean();
				nuovaMotivoDeroga.setIdLotto(rs.getLong(MOTIVO_DEROGA_LOTTO.ID_LOTTO));
				nuovaMotivoDeroga.setIdMotivoDeroga(rs.getLong(MOTIVO_DEROGA_LOTTO.ID_MOTIVO_DEROGA));
				nuovaMotivoDeroga.setDataFineValidita(rs.getString(MOTIVO_DEROGA_LOTTO.DATA_FINE_VALIDITA));
				nuovaMotivoDeroga.setDataInizioValidita(rs.getString(MOTIVO_DEROGA_LOTTO.DATA_INIZIO_VALIDITA));
				ris.add(nuovaMotivoDeroga);
			}

		} finally {
			close(rs, stmt);
		}
		return ris;
	}

	public void createMotivoDerogaLottoRelation(long idLotto, List<MotivoDerogaBean> elencoMotivoDeroga)
			throws SQLException {
		
		if(elencoMotivoDeroga != null) {
			for (MotivoDerogaBean motivoDerogaBean : elencoMotivoDeroga) {
				this.saveMotivoDerogaLotto(motivoDerogaBean, idLotto);
			}
		}
	}

	/**
	 * metodo per la conferma della condizione , nel passaggio viene settato anche
	 * il campo del bean per lo stato
	 * 
	 * @param motivoDerogaLottoBean MotivoDerogaLottoBean
	 * @throws SQLException
	 */
	public void confirmMotivoDerogaLotto(MotivoDerogaBean motivoDerogaBean, long idLotto) throws SQLException {
		updateMotivoDerogaLotto(motivoDerogaBean, idLotto);
	}

	/**
	 * metodo per l'inserimento/salvataggio di una condizione nello stato di "in
	 * definizione", nel passaggio viene settato nel bean in ingresso, se non
	 * &egrave presente la data di inizio condizione
	 * 
	 * @param motivoDerogaLottoBean MotivoDerogaAggBean
	 * @throws SQLException
	 */
	public void saveMotivoDerogaLotto(MotivoDerogaBean motivoDerogaBean, long idLotto) throws SQLException {
		updateMotivoDerogaLotto(motivoDerogaBean, idLotto);
	}

	/**
	 * Aggiunge una nuova condizione\
	 * 
	 * param condizioneBean MotivoDerogaAggBean throws SQLException
	 */
	private void updateMotivoDerogaLotto(MotivoDerogaBean motivoDerogaBean, long idLotto) throws SQLException {
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";

		PreparedStatement stmt = activeConnection.prepareStatement(
				createInsertQuery(QUERY_UPDATE_MOTIVO_DEROGA_LOTTO, MOTIVO_DEROGA_LOTTO.ID_LOTTO_MOTIVO_DEROGA));
		ResultSet rs = null;
		int index = 1;
		try {
			MotivoDerogaLottoBean motivoDerogaLottoBean = new MotivoDerogaLottoBean();
			if (motivoDerogaLottoBean.getDataInizioValidita() == null || motivoDerogaLottoBean.getDataInizioValidita() == "") {
				SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
				motivoDerogaLottoBean.setDataInizioValidita(yyyyMMdd.format(getNow()));
			}
			stmt.setObject(index++, motivoDerogaLottoBean.getDataInizioValidita());
			stmt.setObject(index++, motivoDerogaBean.getIdMotivoDeroga());
			stmt.setLong(index++, idLotto);
			stmt.setObject(index++, null);
			stmt.setObject(index++, motivoDerogaLottoBean.getDataInizioValidita());

			stmt.execute();
			logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(MotivoDerogaLottoBean.class, motivoDerogaBean));
		} finally {
			close(rs, stmt);
		}
	}

	/**
	 * metodo per la cancellazione delle condizioni associate ad un lotto
	 * 
	 * @param idLotto long
	 * @throws SQLException
	 */
	public void deleteMotivoDerogaLotto(long idLotto) throws SQLException {
		PreparedStatement stmt = activeConnection.prepareStatement(QUERY_DELETE_MOTIVO_DEROGA_LOTTO);
		try {
			stmt.setLong(1, idLotto);
			stmt.execute();
			logger.debug(CLAZZ + ": Eliminato record: idLotto=" + idLotto);
		} finally {
			close(null, stmt);
		}
	}
	
	public void updateDataFineValiditaMotivoDerogaLotto(long idLotto) throws SQLException {
		PreparedStatement stmt = activeConnection.prepareStatement(QUERY_UPDATE_DATA_FINE_VALIDITA);
		try {
			stmt.setLong(1, idLotto);
			stmt.execute();
			logger.debug(CLAZZ + ": modificata data fine validita motivo deroga: idLotto=" + idLotto);
		} finally {
			close(null, stmt);
		}
	}
	
	/*
	 *  SELECT m.Id_Motivo_Deroga m.Descrizione
  		FROM [Simog].[dbo].[LOTTO_MOTIVO_DEROGA] as l, simog.dbo.MOTIVO_DEROGA as m
  		where l.Id_Motivo_Deroga = m.Id_Motivo_Deroga
	 * */
	
	private static final String QUERY_JOIN_MOTIVO_DEROGA_LOTTO = String.format("SELECT %s,%s FROM %s,%s WHERE %s >= ? AND %s >= ? AND %s = %s",
			//SELECT
			LOTTO_MOTIVO_DEROGA.T_ID_MOTIVO_DEROGA,
			MOTIVO_DEROGA.DESCRIZIONE,
			//FROM
			LOTTO_MOTIVO_DEROGA.TABLE_NAME,
			MOTIVO_DEROGA.TABLE_NAME,
			//WHERE
			buildISNULL(MOTIVO_DEROGA.T_DATA_FINE_VALIDITA, "99999999"),
			buildISNULL(LOTTO_MOTIVO_DEROGA.T_DATA_FINE_VALIDITA, "99999999"),
			LOTTO_MOTIVO_DEROGA.T_ID_MOTIVO_DEROGA,
			MOTIVO_DEROGA.T_ID_MOTIVO
			);
	
	public Map<String, String> caricaLottoComboMotivoDeroga(Object o) throws SQLException {
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      HashMap<String, String> listaMotivi = new HashMap<String, String>();
	      try {
	               
	         stmt = activeConnection.prepareStatement(QUERY_JOIN_MOTIVO_DEROGA_LOTTO);
	         String dataFine = PageHelper.getFormattedNowOrInputFormattedDate(o, getNow());
	         
	         stmt.setObject(1, dataFine);
	         stmt.setObject(2, dataFine);

	         rs = stmt.executeQuery();

	         while (rs.next()) {
	        	 listaMotivi.put(
	                  rs.getString(LOTTO_MOTIVO_DEROGA.ID_MOTIVO_DEROGA),
	                  rs.getString(MOTIVO_DEROGA.DESCRIZIONE));
	         }
	      } catch (Exception e) {
	         logger.error("Impossibile caricare i motivi deroga", e);
	      } finally {
	         close(rs, stmt);
	      }
	      return listaMotivi;
	   }
	
	
	private static final String QUERY_JOIN_MOTIVO_DEROGA_LOTTO_BY_ID_LOTTO = String.format("SELECT %s,%s FROM %s,%s WHERE %s = %s AND %s = ?",
			//SELECT
			LOTTO_MOTIVO_DEROGA.T_ID_LOTTO,
			LOTTO_MOTIVO_DEROGA.T_ID_MOTIVO_DEROGA,
			//FROM
			LOTTO_MOTIVO_DEROGA.TABLE_NAME,
			MOTIVO_DEROGA.TABLE_NAME,
			//WHERE
			LOTTO_MOTIVO_DEROGA.T_ID_MOTIVO_DEROGA,
			MOTIVO_DEROGA.T_ID_MOTIVO,
			LOTTO_MOTIVO_DEROGA.T_ID_LOTTO
			);
	
	private static final String QUERY_JOIN_MOTIVO_DEROGA_LOTTO_BY_ID_LOTTO_NO_DATA_FINE = String.format("SELECT %s,%s FROM %s,%s WHERE %s = %s AND %s = ? AND %s IS NULL",
			//SELECT
			LOTTO_MOTIVO_DEROGA.T_ID_LOTTO,
			LOTTO_MOTIVO_DEROGA.T_ID_MOTIVO_DEROGA,
			//FROM
			LOTTO_MOTIVO_DEROGA.TABLE_NAME,
			MOTIVO_DEROGA.TABLE_NAME,
			//WHERE
			LOTTO_MOTIVO_DEROGA.T_ID_MOTIVO_DEROGA,
			MOTIVO_DEROGA.T_ID_MOTIVO,
			LOTTO_MOTIVO_DEROGA.T_ID_LOTTO,
			LOTTO_MOTIVO_DEROGA.T_DATA_FINE_VALIDITA
			);
	
	public List<MotivoDerogaLottoBean> loadMany(long idLotto) throws SQLException {
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      
	      List<MotivoDerogaLottoBean> listaMotivi = new ArrayList<MotivoDerogaLottoBean>();
	      
	      try {
	               
	         stmt = activeConnection.prepareStatement(QUERY_JOIN_MOTIVO_DEROGA_LOTTO_BY_ID_LOTTO);
	         
	         stmt.setObject(1, idLotto);

	         rs = stmt.executeQuery();

	         while (rs.next()) {

	        	 MotivoDerogaLottoBean motivoDerogaLottoBean = new MotivoDerogaLottoBean();
	        	 String string = rs.getString(LOTTO_MOTIVO_DEROGA.ID_LOTTO);
 	        	 motivoDerogaLottoBean.setIdLotto(Long.parseLong(string));
	        	 motivoDerogaLottoBean.setIdMotivoDeroga(rs.getLong(LOTTO_MOTIVO_DEROGA.ID_MOTIVO_DEROGA));
	        	 
	        	 listaMotivi.add(motivoDerogaLottoBean);	                  
	         }
	      } catch (Exception e) {
	         logger.error("Impossibile caricare i motivi deroga", e);
	      } finally {
	         close(rs, stmt);
	      }
	      return listaMotivi;
	   }
	
	//MEV 37010 3.04.8.1
	public List<MotivoDerogaLottoBean> loadManyNoFineValidita(long idLotto) throws SQLException {
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      
	      List<MotivoDerogaLottoBean> listaMotivi = new ArrayList<MotivoDerogaLottoBean>();
	      
	      try {
	               
	         stmt = activeConnection.prepareStatement(QUERY_JOIN_MOTIVO_DEROGA_LOTTO_BY_ID_LOTTO_NO_DATA_FINE);
	         
	         stmt.setObject(1, idLotto);

	         rs = stmt.executeQuery();

	         while (rs.next()) {

	        	 MotivoDerogaLottoBean motivoDerogaLottoBean = new MotivoDerogaLottoBean();
	        	 String string = rs.getString(LOTTO_MOTIVO_DEROGA.ID_LOTTO);
	        	 motivoDerogaLottoBean.setIdLotto(Long.parseLong(string));
	        	 motivoDerogaLottoBean.setIdMotivoDeroga(rs.getLong(LOTTO_MOTIVO_DEROGA.ID_MOTIVO_DEROGA));
	        	 
	        	 listaMotivi.add(motivoDerogaLottoBean);	                  
	         }
	      } catch (Exception e) {
	         logger.error("Impossibile caricare i motivi deroga", e);
	      } finally {
	         close(rs, stmt);
	      }
	      return listaMotivi;
	   }
	
	//MEV 37010 3.04.8.1
		public List<MotivoDerogaBean> loadManyNoFineValiditaMotivoDerogaBean(long idLotto) throws SQLException {
		      PreparedStatement stmt = null;
		      ResultSet rs = null;
		      
		      List<MotivoDerogaBean> listaMotivi = new ArrayList<MotivoDerogaBean>();
		      
		      try {
		               
		         stmt = activeConnection.prepareStatement(QUERY_JOIN_MOTIVO_DEROGA_LOTTO_BY_ID_LOTTO_NO_DATA_FINE);
		         
		         stmt.setObject(1, idLotto);

		         rs = stmt.executeQuery();

		         while (rs.next()) {

		        	 MotivoDerogaBean motivoDerogaLottoBean = new MotivoDerogaBean();
		        	 String string = rs.getString(LOTTO_MOTIVO_DEROGA.ID_LOTTO);
		        	 motivoDerogaLottoBean.setIdMotivoDeroga(rs.getLong(LOTTO_MOTIVO_DEROGA.ID_MOTIVO_DEROGA));
		        	 
		        	 listaMotivi.add(motivoDerogaLottoBean);	                  
		         }
		      } catch (Exception e) {
		         logger.error("Impossibile caricare i motivi deroga", e);
		      } finally {
		         close(rs, stmt);
		      }
		      return listaMotivi;
		   }
	
	private static final String QUERY_JOIN_MOTIVO_DEROGA = String.format("SELECT %s,%s FROM %s WHERE %s >= ? ORDER BY %s",
			//SELECT
			MOTIVO_DEROGA.ID_MOTIVO,
			MOTIVO_DEROGA.DESCRIZIONE,
			//FROM
			MOTIVO_DEROGA.TABLE_NAME,
			//WHERE
			buildISNULL(MOTIVO_DEROGA.T_DATA_FINE_VALIDITA, "99999999"),
			MOTIVO_DEROGA.ID_MOTIVO);
	
	public Map<Integer, String> caricaMotivoDeroga(Object o) throws SQLException {
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      HashMap<Integer, String> listaMotiviDeroga = new HashMap<Integer, String>();
	      try {
	               
	         stmt = activeConnection.prepareStatement(QUERY_JOIN_MOTIVO_DEROGA);
	         String dataFine = PageHelper.getFormattedNowOrInputFormattedDate(o, getNow());
	         
	         stmt.setObject(1, dataFine);

	         rs = stmt.executeQuery();

	         while (rs.next()) {
	        	 listaMotiviDeroga.put(
	                  rs.getInt(MOTIVO_DEROGA.ID_MOTIVO),
	                  rs.getString(MOTIVO_DEROGA.DESCRIZIONE));
	         }
	      } catch (Exception e) {
	         logger.error("Impossibile caricare i motivi deroga", e);
	      } finally {
	         close(rs, stmt);
	      }
	      return listaMotiviDeroga;
	   }
	
	public void aggiornaMotivoDerogaLotto(Lotto lotto) throws SQLException {
		
		deleteMotivoDerogaLotto(lotto.getId_Lotto());
		createMotivoDerogaLottoRelation(lotto.getId_Lotto(), lotto.getElencoMotivoDeroga());
	    }

	@Override
	public boolean annulla(long idSchedaPadre, Timestamp dataInizioSchedaPadre) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	// FINET TICKET ALM #3835
//	private static String QUERY_DELETE_MOTIVO_DEROGA_AGG = 
//	"DELETE FROM "+MOTIVO_DEROGA_AGG.TABLE_NAME+
//	" WHERE " + MOTIVO_DEROGA_AGG.ID_AGGIUDICAZIONE + " = ?"+
//	" AND "+MOTIVO_DEROGA_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
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
//		stmt = activeConnection.prepareStatement(QUERY_DELETE_MOTIVO_DEROGA_AGG);
//		logger.debug("query per la delete record attivo condizioni: "+QUERY_DELETE_MOTIVO_DEROGA_AGG);
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
//private static String QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_MOTIVO_DEROGA_AGG = 
//	"UPDATE "+MOTIVO_DEROGA_AGG.TABLE_NAME+
//	" SET " + MOTIVO_DEROGA_AGG.ID_STATO + " = ?,"+
//	MOTIVO_DEROGA_AGG.DATA_FINE_COND + " = " + buildGetDate() +
//	" WHERE "+MOTIVO_DEROGA_AGG.ID_AGGIUDICAZIONE + " = ?"+
//	" AND "+MOTIVO_DEROGA_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
//
///**
// * metodo per la cancellazione delle condizioni associate ad un'aggiudicazione
// * 
// * @param idAggiudicazione long 
// * @param dataInizioAggiudicazione Timestamp
// * @throws SQLException
// */
//public void deleteMotivoDerogaAgg(long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{		
//	PreparedStatement stmt = activeConnection.prepareStatement(QUERY_DELETE_MOTIVO_DEROGA_AGG);
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
//		stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_MOTIVO_DEROGA_AGG);
//		logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_MOTIVO_DEROGA_AGG);
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
//private static String QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_MOTIVO_DEROGA_AGG_NEWRECORD = 
//	"UPDATE "+MOTIVO_DEROGA_AGG.TABLE_NAME+
//	" SET " + MOTIVO_DEROGA_AGG.ID_STATO + " = ?,"+ //+STATI_SCHEDA.CONFERMATO+
//	MOTIVO_DEROGA_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?"+
//	" WHERE "+MOTIVO_DEROGA_AGG.ID_AGGIUDICAZIONE + " = ?"+
//	" AND "+MOTIVO_DEROGA_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
//
//public int updateRecordToPointToNew(String idAggiudicazione, Timestamp dataInizioAggOld,Timestamp dataInizioAggNew, String stato_scheda ) throws SQLException{
//	int numRow = -1; 
//	PreparedStatement stmt = null;
//	ResultSet rs = null;
//	try {
//		stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_MOTIVO_DEROGA_AGG_NEWRECORD);
//		logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_MOTIVO_DEROGA_AGG_NEWRECORD);
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
//private static String QUERY_SELECT_MOTIVO_DEROGA = "SELECT * FROM " + MOTIVO_DEROGA_AGG.TABLE_NAME
//+ " WHERE " + MOTIVO_DEROGA_AGG.ID_AGGIUDICAZIONE + " = ? AND " 
//+ MOTIVO_DEROGA_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
//
	/*
	 * //TICKET ALM #3835 private static String QUERY_SELECT_MOTIVO_DEROGA_LOTTO =
	 * "SELECT * FROM " + MOTIVO_DEROGA_LOTTO.TABLE_NAME + " WHERE " +
	 * MOTIVO_DEROGA_LOTTO.ID_LOTTO + " = ?"; private final String
	 * WHERE_STATO_MOTIVO_DEROGA = " AND (" + MOTIVO_DEROGA_LOTTO.T_ID_STATO + "=" +
	 * StatiScheda.IN_DEFINIZIONE +" OR " + MOTIVO_DEROGA_LOTTO.T_ID_STATO + " = " +
	 * StatiScheda.CONFERMATO+")"; private static String
	 * QUERY_DELETE_MOTIVO_DEROGA_LOTTO =
	 * "DELETE FROM "+MOTIVO_DEROGA_LOTTO.TABLE_NAME+ " WHERE " +
	 * MOTIVO_DEROGA_LOTTO.ID_LOTTO + " = ?"; //FINE TICKET ALM #3835
	 * 
	 * 
	 * // private final String WHERE_STATO = " AND (" + MOTIVO_DEROGA_AGG.T_ID_STATO
	 * + "=" + StatiScheda.IN_DEFINIZIONE // +" OR " + MOTIVO_DEROGA_AGG.T_ID_STATO
	 * + " = " + StatiScheda.CONFERMATO+")"; // // private static String
	 * QUERY_UPDATE_CONDIZIONE = "INSERT INTO " + MOTIVO_DEROGA_AGG.TABLE_NAME +
	 * "( " // + MOTIVO_DEROGA_AGG.DATA_INIZIO_COND + ", " // +
	 * MOTIVO_DEROGA_AGG.ID_CONDIZIONE + ", " // + MOTIVO_DEROGA_AGG.ID_STATO + ", "
	 * // + MOTIVO_DEROGA_AGG.DATA_FINE_COND + ", " // +
	 * MOTIVO_DEROGA_AGG.ID_AGGIUDICAZIONE + ", " // +
	 * MOTIVO_DEROGA_AGG.DATA_INIZIO_AGGIUDICAZIONE // + " ) VALUES (" // +
	 * "?, ?, ?, ?, ?, ? )";
	 * 
	 * //TICKET ALM #3835 private static String QUERY_UPDATE_MOTIVO_DEROGA_LOTTO =
	 * "INSERT INTO " + MOTIVO_DEROGA_LOTTO.TABLE_NAME + "( " +
	 * MOTIVO_DEROGA_LOTTO.DATA_INIZIO_COND + ", " +
	 * MOTIVO_DEROGA_LOTTO.ID_MOTIVO_DEROGA + ", " + MOTIVO_DEROGA_LOTTO.ID_STATO +
	 * ", " + MOTIVO_DEROGA_LOTTO.DATA_FINE_COND + ", " +
	 * MOTIVO_DEROGA_LOTTO.ID_LOTTO + " ) VALUES (" + "?, ?, ?, ?, ?)"; //FINE
	 * TICKET ALM #3835
	 * 
	 * // /** // * Carica tutti le condizioni associate ad una aggiudicazione // *
	 * // * @param idAggiudicazione long // * @param dataInizioAggiudicazione
	 * Timestamp // * @param ignoraStato TODO // * @return
	 * List&lt;CondizioneAggBean&gt; - la lista delle condizioni associate alla
	 * aggiudicazione // * @throws SQLException //
	 */
//	public List<CondizioneAggBean> loadMany(long idAggiudicazione, Timestamp dataInizioAggiudicazione, boolean ignoraStato) throws SQLException{
//		
//		String qry = QUERY_SELECT_MOTIVO_DEROGA;
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
//				nuovaCondizione.setDataFineCond(rs.getTimestamp(MOTIVO_DEROGA_AGG.DATA_FINE_COND));
//				nuovaCondizione.setDataInizioAggiudicazione(rs.getTimestamp(MOTIVO_DEROGA_AGG.DATA_INIZIO_AGGIUDICAZIONE));
//				nuovaCondizione.setDataInizioCond(rs.getTimestamp(MOTIVO_DEROGA_AGG.DATA_INIZIO_COND));
//				nuovaCondizione.setIdAggiudicazione(rs.getLong(MOTIVO_DEROGA_AGG.ID_AGGIUDICAZIONE));
//				nuovaCondizione.setIdCondizione(rs.getLong(MOTIVO_DEROGA_AGG.ID_CONDIZIONE));
//				nuovaCondizione.setIdCondizioneAgg(rs.getLong(MOTIVO_DEROGA_AGG.ID_CONDIZIONE_AGG));
//				nuovaCondizione.setIdStato(rs.getInt(MOTIVO_DEROGA_AGG.ID_STATO));
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
//		PreparedStatement stmt = activeConnection.prepareStatement(createInsertQuery(QUERY_UPDATE_CONDIZIONE,MOTIVO_DEROGA_AGG.ID_CONDIZIONE_AGG));
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
//			"UPDATE "+MOTIVO_DEROGA_AGG.TABLE_NAME+ " SET "
//			+ MOTIVO_DEROGA_AGG.ID_STATO+ " = ?, "
//			+ MOTIVO_DEROGA_AGG.DATA_INIZIO_AGGIUDICAZIONE+ " = ? "
//			
//			+" WHERE "
//			+MOTIVO_DEROGA_AGG.T_ID_AGGIUDICAZIONE+" = ?"
//			+" AND "+MOTIVO_DEROGA_AGG.T_DATA_INIZIO_AGGIUDICAZIONE+" = ?"
//			+" AND "+MOTIVO_DEROGA_AGG.T_ID_STATO + "=" + StatiScheda.CONFERMATO;
//		
//		String QUERY_COPY_RECORD =
//			"INSERT INTO "+MOTIVO_DEROGA_AGG.TABLE_NAME+" ("
//			+MOTIVO_DEROGA_AGG.ID_CONDIZIONE_AGG	
//			+","+MOTIVO_DEROGA_AGG.ID_AGGIUDICAZIONE
//			+","+MOTIVO_DEROGA_AGG.DATA_INIZIO_AGGIUDICAZIONE
//			+","+MOTIVO_DEROGA_AGG.ID_CONDIZIONE
//			+","+MOTIVO_DEROGA_AGG.DATA_INIZIO_COND
//			+","+MOTIVO_DEROGA_AGG.DATA_FINE_COND
//			+","+MOTIVO_DEROGA_AGG.ID_STATO+" ) "
//			+"SELECT "
//			+MOTIVO_DEROGA_AGG.ID_CONDIZIONE_AGG	
//			+","+MOTIVO_DEROGA_AGG.ID_AGGIUDICAZIONE
//			+","+MOTIVO_DEROGA_AGG.DATA_INIZIO_AGGIUDICAZIONE
//			+","+MOTIVO_DEROGA_AGG.ID_CONDIZIONE
//			+", ?"
//			+", ?"
//			+", ?"
//			+" FROM "+MOTIVO_DEROGA_AGG.TABLE_NAME
//			+" WHERE "
//			+MOTIVO_DEROGA_AGG.ID_AGGIUDICAZIONE+" = ? AND "
//			+MOTIVO_DEROGA_AGG.DATA_INIZIO_AGGIUDICAZIONE+" = ?"
//			+" AND "+MOTIVO_DEROGA_AGG.ID_STATO+" = "+StatiScheda.CONFERMATO;
//		PreparedStatement stmt = null;
//		PreparedStatement stmt2 = null;
//		try{
//			int index = 1;
//			stmt = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD,MOTIVO_DEROGA_AGG.TABLE_NAME));
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
//			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_MOTIVO_DEROGA);
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
//
//	
}
