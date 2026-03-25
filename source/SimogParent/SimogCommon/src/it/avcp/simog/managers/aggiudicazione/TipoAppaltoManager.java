package it.avcp.simog.managers.aggiudicazione;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.APPALTI_PER_CATEGORIA;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.TIPO_APPALTO_AGG;
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
 * Classe per la gestione dei dati relatici ai tipi appalto
 *
 */
public class TipoAppaltoManager extends AccessiDB  implements IAnnullamentoMulti{
	public static String CLAZZ = "TipoAppaltoManager";
	
	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public TipoAppaltoManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
		
	}
	
	private static String QUERY_DELETE_APPALTI_AGG = 
		"DELETE FROM "+TIPO_APPALTO_AGG.TABLE_NAME+
		" WHERE " + TIPO_APPALTO_AGG.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+TIPO_APPALTO_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";	
	
   private static String QUERY_DELETE_APPALTI_LOTTO = 
         "DELETE FROM "+TIPO_APPALTO_AGG.TABLE_NAME+
         " WHERE " + TIPO_APPALTO_AGG.ID_LOTTO + " = ?";

   private static String QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_APPALTI_AGG = 
		"UPDATE "+TIPO_APPALTO_AGG.TABLE_NAME+
		" SET " + TIPO_APPALTO_AGG.ID_STATO + " = ?," + 
		TIPO_APPALTO_AGG.DATA_FINE_TIPOAPP + " = " + buildGetDate() +
		" WHERE "+TIPO_APPALTO_AGG.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+TIPO_APPALTO_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	private static String QUERY_SELECT_APPALTI_AGG = "SELECT * FROM " 
	+ TIPO_APPALTO_AGG.TABLE_NAME + " JOIN " + APPALTI_PER_CATEGORIA.TABLE_NAME 
	+ " ON " + TIPO_APPALTO_AGG.T_ID_APPALTO + " = " + APPALTI_PER_CATEGORIA.T_ID_APPALTO
	+ "    AND " + APPALTI_PER_CATEGORIA.ID_CATEGORIA + "= ?"
	+ "    AND " + APPALTI_PER_CATEGORIA.ID_TIPO_CATEGORIA + "= ?"
	+ " WHERE " + TIPO_APPALTO_AGG.ID_AGGIUDICAZIONE + " = ?"
	+ "    AND " + TIPO_APPALTO_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
   private static String QUERY_SELECT_APPALTI_LOTTO = "SELECT * FROM " 
   + TIPO_APPALTO_AGG.TABLE_NAME + " JOIN " + APPALTI_PER_CATEGORIA.TABLE_NAME 
   + " ON " + TIPO_APPALTO_AGG.T_ID_APPALTO + " = " + APPALTI_PER_CATEGORIA.T_ID_APPALTO
   + "    AND " + APPALTI_PER_CATEGORIA.ID_CATEGORIA + "= ?"
   + "    AND " + APPALTI_PER_CATEGORIA.ID_TIPO_CATEGORIA + "= ?"
   + " WHERE " + TIPO_APPALTO_AGG.ID_LOTTO + " = ?";

   private final String WHERE_STATO =  "    AND (" + TIPO_APPALTO_AGG.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		   +" OR " + TIPO_APPALTO_AGG.T_ID_STATO + " = " + StatiScheda.CONFERMATO+")";
	
	
	private static String QUERY_INSERT_APPALTI_AGG = "INSERT INTO " + TIPO_APPALTO_AGG.TABLE_NAME + "( "
	+ TIPO_APPALTO_AGG.DATA_INIZIO_TIPOAPP + ", "
	+ TIPO_APPALTO_AGG.ID_APPALTO + ", "
	+ TIPO_APPALTO_AGG.ID_STATO + ", "
	+ TIPO_APPALTO_AGG.DATA_FINE_TIPOAPP + ", "
	+ TIPO_APPALTO_AGG.ID_AGGIUDICAZIONE + ", "
	+ TIPO_APPALTO_AGG.DATA_INIZIO_AGGIUDICAZIONE 
	+ (SimogFlags.is3031_RFWEBGL00Active() ? ", " + TIPO_APPALTO_AGG.ID_LOTTO : "")
	+ " ) VALUES ("
	+ "?, ?, ?, ?, ?, ?"
	+ (SimogFlags.is3031_RFWEBGL00Active() ? ",?" : "")
	+ ")";
	
	/**
	 * metodo per l'aggiornamento di un record
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
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_APPALTI_AGG);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_APPALTI_AGG);

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
	private static String QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_APPALTI_AGG_NEWRECORD = 
		"UPDATE "+TIPO_APPALTO_AGG.TABLE_NAME+
		" SET " + TIPO_APPALTO_AGG.ID_STATO + " = ?,"+ //+STATI_SCHEDA.CONFERMATO+
		TIPO_APPALTO_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?"+
		" WHERE "+TIPO_APPALTO_AGG.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+TIPO_APPALTO_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	public int updateRecordToPointToNew(String idAggiudicazione, Timestamp dataInizioAggOld,Timestamp dataInizioAggNew, String stato_scheda ) throws SQLException{
		int numRow = -1; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_APPALTI_AGG_NEWRECORD);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_APPALTI_AGG_NEWRECORD);

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
	 * metodo per la cancellazione di tipo appalto associato ad una aggiudicazione
	 *  
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws SQLException
	 */
	public void deleteAppaltiAgg(long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		
		PreparedStatement stmt = activeConnection.prepareStatement(QUERY_DELETE_APPALTI_AGG);
		try{
			stmt.setLong(1, idAggiudicazione);
			stmt.setTimestamp(2, dataInizioAggiudicazione);
			stmt.execute();
		}finally{
			close(null,stmt);
		}
	}
	
	/**
	 * metodo per il caricamento di tutti i tipi appalto (componenti lavori) associati all'aggiudicazione
	 * 
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @param tipoEnte String
	 * @return List&lt;TipoAppaltoAggBean&gt;
	 * @throws SQLException
	 */
	public List<TipoAppaltoAggBean> loadManyL(long idAggiudicazione, Timestamp dataInizioAggiudicazione, String tipoEnte, boolean ignoraStato) throws SQLException{
		return loadMany(idAggiudicazione, dataInizioAggiudicazione, Costanti.TIPO_SCHEDA_LAVORI,tipoEnte, ignoraStato);
	}
	/**
	 * metodo per il caricamento di tutti i tipi appalto (componenti forniture e servizi) associati all'aggiudicazione
	 * 
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @param tipoEnte String
	 * @return List&lt;TipoAppaltoAggBean&gt;
	 * @throws SQLException
	 */
	public List<TipoAppaltoAggBean> loadManyFS(long idAggiudicazione, Timestamp dataInizioAggiudicazione, String tipoEnte, boolean ignoraStato) throws SQLException{
		return loadMany(idAggiudicazione, dataInizioAggiudicazione, Costanti.TIPO_SCHEDA_FORNITURE, tipoEnte, ignoraStato);
	}
	
	
	/**
	 * param idAggiudicazione
	 * param dataInizioAggiudicazione
	 * param tipoScheda
	 * param tipoEnte
	 * return
	 * throws SQLException
	 * @param ignoraStato TODO
	 */
	private List<TipoAppaltoAggBean> loadMany(long idAggiudicazione, Timestamp dataInizioAggiudicazione, String tipoScheda, String tipoEnte, boolean ignoraStato) throws SQLException{
		
		String qry = QUERY_SELECT_APPALTI_AGG;
		if(!ignoraStato)
			qry += WHERE_STATO;
		
		PreparedStatement stmt = activeConnection.prepareStatement(qry);
		ResultSet rs = null;
		int index = 1;
		ArrayList<TipoAppaltoAggBean> ris = new ArrayList<TipoAppaltoAggBean>();
		TipoAppaltoAggBean nuovoTipo = null;
		try{
			stmt.setString(index++, tipoScheda);
			stmt.setString(index++, tipoEnte);
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++,dataInizioAggiudicazione);
			rs = stmt.executeQuery();
			while(rs.next()){
				nuovoTipo = new TipoAppaltoAggBean();
				nuovoTipo.setDataFineTipApp(rs.getTimestamp(TIPO_APPALTO_AGG.DATA_FINE_TIPOAPP));
				nuovoTipo.setDataInizioAggiudicazione(rs.getTimestamp(TIPO_APPALTO_AGG.DATA_INIZIO_AGGIUDICAZIONE));
				nuovoTipo.setDataInizioTipApp(rs.getTimestamp(TIPO_APPALTO_AGG.DATA_INIZIO_TIPOAPP));
				nuovoTipo.setIdAggiudicazione(rs.getLong(TIPO_APPALTO_AGG.ID_AGGIUDICAZIONE));
				nuovoTipo.setIdAppalto(rs.getLong(TIPO_APPALTO_AGG.ID_APPALTO));
				nuovoTipo.setIdTipoAppAgg(rs.getLong(TIPO_APPALTO_AGG.ID_TIPOAPP_AGG));
				nuovoTipo.setIdStato(rs.getInt(TIPO_APPALTO_AGG.ID_STATO));
				
				if(SimogFlags.is3031_RFWEBGL00Active())
				   nuovoTipo.setIdLotto(rs.getLong(TIPO_APPALTO_AGG.ID_LOTTO));
				
				ris.add(nuovoTipo);
				
			}
			
		}finally{
			close(rs, stmt);
		}
		
		return ris;
	
	}
	
   /**
    * param idAggiudicazione
    * param dataInizioAggiudicazione
    * param tipoScheda
    * param tipoEnte
    * return
    * throws SQLException
    * @param ignoraStato TODO
    */
   public List<TipoAppaltoAggBean> loadMany(long idLotto, String tipoScheda, String tipoEnte, boolean ignoraStato) throws SQLException{
      
      String qry = QUERY_SELECT_APPALTI_LOTTO;
      if(!ignoraStato)
         qry += WHERE_STATO;
      
      PreparedStatement stmt = activeConnection.prepareStatement(qry);
      ResultSet rs = null;
      int index = 1;
      ArrayList<TipoAppaltoAggBean> ris = new ArrayList<TipoAppaltoAggBean>();
      TipoAppaltoAggBean nuovoTipo = null;
      try{
         stmt.setString(index++, tipoScheda);
         stmt.setString(index++, tipoEnte);
         stmt.setLong(index++, idLotto);
         rs = stmt.executeQuery();
         while(rs.next()){
            nuovoTipo = new TipoAppaltoAggBean();
            nuovoTipo.setDataFineTipApp(rs.getTimestamp(TIPO_APPALTO_AGG.DATA_FINE_TIPOAPP));
            nuovoTipo.setDataInizioAggiudicazione(rs.getTimestamp(TIPO_APPALTO_AGG.DATA_INIZIO_AGGIUDICAZIONE));
            nuovoTipo.setDataInizioTipApp(rs.getTimestamp(TIPO_APPALTO_AGG.DATA_INIZIO_TIPOAPP));
            nuovoTipo.setIdAggiudicazione(rs.getLong(TIPO_APPALTO_AGG.ID_AGGIUDICAZIONE));
            nuovoTipo.setIdAppalto(rs.getLong(TIPO_APPALTO_AGG.ID_APPALTO));
            nuovoTipo.setIdTipoAppAgg(rs.getLong(TIPO_APPALTO_AGG.ID_TIPOAPP_AGG));
            nuovoTipo.setIdStato(rs.getInt(TIPO_APPALTO_AGG.ID_STATO));
            
            if(SimogFlags.is3031_RFWEBGL00Active())
               nuovoTipo.setIdLotto(rs.getLong(TIPO_APPALTO_AGG.ID_LOTTO));
            
            ris.add(nuovoTipo);
            
         }
         
      }finally{
         close(rs, stmt);
      }
      
      return ris;
   
   }
	
	/**
	 * metodo per la conferma di un tipo appalto
	 * 
	 * @param appalto TipoAppaltoAggBean
	 * @throws SQLException
	 */
	public void confirm(TipoAppaltoAggBean appalto) throws SQLException {
		update(appalto, true);
	}

	/**
	 * metodo per il salvataggio di un tipo appalto
	 * 
	 * @param appalto TipoAppaltoAggBean
	 * @throws SQLException
	 */
	public void save(TipoAppaltoAggBean appalto) throws SQLException {
		update(appalto, false);
	}

	/**
	 * Aggiunge una nuova condizione
	 * 
	 * @param appaltoBean TipoAppaltoAggBean condizione da aggiungere
	 * @throws SQLException
	 */
	private void update(TipoAppaltoAggBean appaltoBean, boolean conferma) throws SQLException{
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
			
		PreparedStatement stmt = activeConnection.prepareStatement(createInsertQuery(QUERY_INSERT_APPALTI_AGG,TIPO_APPALTO_AGG.ID_TIPOAPP_AGG));
		ResultSet rs = null;
		int index = 1;		
		try{		
			
			
			appaltoBean.setDataInizioTipApp(getNow());
			stmt.setObject(index++, appaltoBean.getDataInizioTipApp());
			stmt.setObject(index++, appaltoBean.getIdAppalto());
			
			if (conferma){
				stmt.setLong(index++, StatiScheda.CONFERMATO);
				stmt.setTimestamp(index++, getNow());
				appaltoBean.setIdStato(StatiScheda.CONFERMATO);
			}else{
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
				stmt.setNull(index++, Types.TIMESTAMP);
				appaltoBean.setIdStato(StatiScheda.IN_DEFINIZIONE);
			}

			if (appaltoBean.getIdAggiudicazione() == 0)
            stmt.setNull(index++, Types.INTEGER);
         else
            stmt.setLong(index++, appaltoBean.getIdAggiudicazione());
			
			
			if( appaltoBean.getDataInizioAggiudicazione() != null )
			   stmt.setObject(index++, appaltoBean.getDataInizioAggiudicazione());
			else
			   stmt.setNull(index++, Types.TIMESTAMP);
			
			if(SimogFlags.is3031_RFWEBGL00Active()){
			   if (appaltoBean.getIdLotto() == 0)
			      stmt.setNull(index++, Types.INTEGER);
			   else
			      stmt.setLong(index++, appaltoBean.getIdLotto());
			}
			stmt.execute();
			logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(TipoAppaltoAggBean.class, appaltoBean));
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
			"UPDATE "+TIPO_APPALTO_AGG.TABLE_NAME+ " SET "
			+ TIPO_APPALTO_AGG.ID_STATO+ " = ?,"
			+ TIPO_APPALTO_AGG.DATA_INIZIO_AGGIUDICAZIONE+ " = ? "
			
			+" WHERE "
			+TIPO_APPALTO_AGG.T_ID_AGGIUDICAZIONE+" = ?"
			+" AND "+TIPO_APPALTO_AGG.T_DATA_INIZIO_AGGIUDICAZIONE+" = ?"
			+" AND "+TIPO_APPALTO_AGG.T_ID_STATO + "=" + StatiScheda.CONFERMATO;
		
		String QUERY_COPY_RECORD =
		"INSERT INTO "+TIPO_APPALTO_AGG.TABLE_NAME+" ("
			+TIPO_APPALTO_AGG.ID_TIPOAPP_AGG	
			+","+TIPO_APPALTO_AGG.ID_AGGIUDICAZIONE
			+","+TIPO_APPALTO_AGG.DATA_INIZIO_AGGIUDICAZIONE
			+","+TIPO_APPALTO_AGG.ID_APPALTO
			+","+TIPO_APPALTO_AGG.DATA_INIZIO_TIPOAPP
			+","+TIPO_APPALTO_AGG.DATA_FINE_TIPOAPP
			+","+TIPO_APPALTO_AGG.ID_STATO+" ) "
			+"SELECT "
			+TIPO_APPALTO_AGG.ID_TIPOAPP_AGG
			+","+TIPO_APPALTO_AGG.ID_AGGIUDICAZIONE
			+","+TIPO_APPALTO_AGG.DATA_INIZIO_AGGIUDICAZIONE
			+","+TIPO_APPALTO_AGG.ID_APPALTO
			+", ?"
			+", ?"
			+", ?"
			+" FROM "+TIPO_APPALTO_AGG.TABLE_NAME
			+" WHERE "
			+TIPO_APPALTO_AGG.ID_AGGIUDICAZIONE+" = ? AND "
			+TIPO_APPALTO_AGG.DATA_INIZIO_AGGIUDICAZIONE+" = ?"
			+ " AND " + TIPO_APPALTO_AGG.ID_STATO + " = " + StatiScheda.CONFERMATO;
		   
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try{
			int index = 1;
			stmt = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD,TIPO_APPALTO_AGG.TABLE_NAME));
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
				logger.debug("TIPOAPPALTO_MANAGER.copyRecord: Nessun record da copiare");
				return true;
			}

				
		
		}
		finally{
			close(null, stmt2);
			close(null, stmt);
		}
	}	
	
	/**
	 * metodo per la rimozione di un record
	 * 
	 * @param idAggiudicazione String
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws SQLException
	 */
	public void deleteRecord(String idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
		deleteAppaltiAgg(Long.parseLong(idAggiudicazione), dataInizioAggiudicazione);
	}
	public boolean annulla(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException {
		return _annulla(idAggiudicazione, dataInizioAggiudicazione);
		
	}
	/** cancellazione logica
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @throws SQLException
	 */
	private boolean _annulla(long idLotto) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_TIPOAPPALTO_LOTTO);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ANNULLATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idLotto);
			someRowAffected = stmt.executeUpdate() > 0;
			return someRowAffected;
		}finally {
			close(null,stmt);
		}		
	}
   public void deleteAppaltiAgg(Long idLotto) throws SQLException {
      PreparedStatement stmt = activeConnection.prepareStatement(QUERY_DELETE_APPALTI_LOTTO + WHERE_STATO);
      try{
         stmt.setLong(1, idLotto);
         stmt.execute();
      }finally{
         close(null,stmt);
      }   
   }	

   private boolean _annulla(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
      PreparedStatement stmt = null;
      boolean someRowAffected = false;
      try{ 
         stmt = activeConnection.prepareStatement(QUERY_ANNULLA_TIPOAPPALTO);
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

   /**
    * Settaggio di ID_LOTTO nell'elenco dei Tipi Appalto di un lotto
    * 
    * @param lotto
    */
   public void settingIdLotto(Lotto lotto){
      if (lotto.getElencoTipoAppaltoLottoF() != null)
         for(TipoAppaltoAggBean item: lotto.getElencoTipoAppaltoLottoF()){
            item.setIdLotto(lotto.getId_Lotto());
         }

      if (lotto.getElencoTipoAppaltoLottoL() != null)
      for(TipoAppaltoAggBean item: lotto.getElencoTipoAppaltoLottoL()){
         item.setIdLotto(lotto.getId_Lotto());
      }
   }

   /**
    * Inserisci le tipologie di appalto ad un lotto
    * 
    * @param elencoTipoAppaltoLotto
    * @return
    * @throws SQLException
    */
   public int addTipoAppaltoLotto(List<TipoAppaltoAggBean> elencoTipoAppaltoLotto, boolean conferma) throws SQLException {
      
//      TipoAppaltoManager tal = new TipoAppaltoManager(activeConnection, logger);

      for(TipoAppaltoAggBean currentTipoAppalto: elencoTipoAppaltoLotto)
      {
         if( conferma )
            confirm(currentTipoAppalto);
         else
            save(currentTipoAppalto);
      }
      return elencoTipoAppaltoLotto.size();
   }

   /**
    * Aggiorna le tipologie di appalto ad un lotto
    * (DELETE e CREATE)
    * 
    * @param elencoTipoAppaltoLotto
    * @return
    * @throws SQLException
    */
   public int aggiornaTipoAppaltoLotto(Lotto lotto, boolean conferma) throws SQLException {
      //PP meglio fisica _annulla(lotto.getId_Lotto());
      deleteAppaltiAgg(lotto.getId_Lotto());
      return addTipoAppaltoLotto(lotto.getElencoTipoAppaltoLotto(), conferma);
   }
   
   
   
   
   
   private static String UPDATE_TIPO_APPALTO_AGG = 
      "UPDATE " 
      + TIPO_APPALTO_AGG.TABLE_NAME
      + " SET " + TIPO_APPALTO_AGG.ID_AGGIUDICAZIONE + " = ?"
      + "," + TIPO_APPALTO_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?"
      + " WHERE " 
      + TIPO_APPALTO_AGG.ID_LOTTO + " = ?"
      ;
   
   private static String SGANCIA_TIPO_APPALTO_AGG = 
         "UPDATE " 
         + TIPO_APPALTO_AGG.TABLE_NAME
         + " SET " + TIPO_APPALTO_AGG.ID_AGGIUDICAZIONE + " = null"
         + "," + TIPO_APPALTO_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = null"
         + " WHERE " 
         + TIPO_APPALTO_AGG.ID_AGGIUDICAZIONE + " = ?"
         + " AND " 
         + TIPO_APPALTO_AGG.DATA_INIZIO_AGGIUDICAZIONE + " = ?"
         ;
      
   
   /**
    * Aggiunge al record relativo alla tipologia appalto le informazioni sull'aggiudicazione
    * - idAggiudicazione
    * - dataInizioAggiudicazione
    * 
    * @param idLotto
    * @param idAggiudicazione
    * @param dataInizioAgg
    * @return
    * @throws SQLException
    */
   public int completaDatiTipoAppalto(Long idLotto, Long idAggiudicazione, Timestamp dataInizioAgg) throws SQLException {
      
      //logger.debug("Completa dati CUP | Query Eseguita[" + UPDATE_TIPO_APPALTO_AGG + "]"); 
      
      PreparedStatement pstmt = null;
      int updated = 0;
      
      try 
      {
         pstmt = activeConnection.prepareStatement(UPDATE_TIPO_APPALTO_AGG);
         
         int idx = 0;
         
         if( idAggiudicazione != null )
            pstmt.setLong(++idx, idAggiudicazione);
         else
            pstmt.setNull(++idx, Types.BIGINT);
         
         if( dataInizioAgg != null )
            pstmt.setTimestamp(++idx, dataInizioAgg);
         else
            pstmt.setNull(++idx, Types.TIMESTAMP);

         pstmt.setLong(++idx, idLotto);
         
         updated = pstmt.executeUpdate();
         
         return updated;
      }
      finally{
         close(null, pstmt);
      }
   }
   public void cancellaDatiAgg(Long idAggiudicazione, Timestamp dataInizioAgg) throws SQLException {
      
      PreparedStatement pstmt = null;
      int updated = 0;
      
      try 
      {
         pstmt = activeConnection.prepareStatement(SGANCIA_TIPO_APPALTO_AGG + WHERE_STATO);
         
         int idx = 0;
         
         if( idAggiudicazione != null )
            pstmt.setLong(++idx, idAggiudicazione);
         else
            pstmt.setNull(++idx, Types.BIGINT);
         
         if( dataInizioAgg != null )
            pstmt.setTimestamp(++idx, dataInizioAgg);
         else
            pstmt.setNull(++idx, Types.TIMESTAMP);
         
         updated = pstmt.executeUpdate();
         
         return;
      }
      finally{
         close(null, pstmt);
      }
   }
   
   private static String QUERY_LOGIC_DELETE_APPALTI_LOTTO = 
      "UPDATE "
      + TIPO_APPALTO_AGG.TABLE_NAME
      + " SET "
      + TIPO_APPALTO_AGG.DATA_FINE_TIPOAPP + " = " + buildGetDate()
      + "," 
      + TIPO_APPALTO_AGG.ID_STATO + " = " + StatiScheda.ANNULLATO
      + " WHERE " 
      + TIPO_APPALTO_AGG.ID_LOTTO + " = ?";   
   
   /**
    * Cancellazione logica delle tipologie di appalto per idLotto
    * 
    * @param idLotto
    * @throws SQLException
    */
   public void deleteAppaltiLotto(Long idLotto) throws SQLException {
      PreparedStatement stmt = activeConnection.prepareStatement(QUERY_LOGIC_DELETE_APPALTI_LOTTO);
      try{
         stmt.setLong(1, idLotto);
         stmt.execute();
      }finally{
         close(null,stmt);
      }   
   }  
   
   private static String QUERY_LOGIC_RIPR_APPALTI_LOTTO = 
         "UPDATE "
         + TIPO_APPALTO_AGG.TABLE_NAME
         + " SET "
         + TIPO_APPALTO_AGG.DATA_FINE_TIPOAPP + " = null" 
         + "," 
         + TIPO_APPALTO_AGG.ID_STATO + " = ?" 
         + " WHERE " 
         + TIPO_APPALTO_AGG.ID_LOTTO + " = ?";   
      
   private static String QUERY_LOGIC_RIPR_APPALTI_LOTTO_CONF = 
         "UPDATE "
         + TIPO_APPALTO_AGG.TABLE_NAME
         + " SET "
         + TIPO_APPALTO_AGG.ID_STATO + " = ?" 
         + " WHERE " 
         + TIPO_APPALTO_AGG.ID_LOTTO + " = ?";   

   /**
       * aggiornamento stato delle tipologie di appalto per idLotto
       * 
       * @param idLotto
       * @throws SQLException
       */
      public void ripristinaAppaltiLotto(Long idLotto, int stato) throws SQLException {
         PreparedStatement stmt = activeConnection.prepareStatement(
               stato == StatiScheda.CONFERMATO ? QUERY_LOGIC_RIPR_APPALTI_LOTTO_CONF : QUERY_LOGIC_RIPR_APPALTI_LOTTO);
         try{
            stmt.setInt(1, stato);
            stmt.setLong(2, idLotto);
            
            stmt.execute();
         }finally{
            close(null,stmt);
         }   
      }

   private static String QUERY_SBLOCCA = 
         "UPDATE "
         + TIPO_APPALTO_AGG.TABLE_NAME
         + " SET "
         + TIPO_APPALTO_AGG.ID_STATO + " = " +  StatiScheda.IN_DEFINIZIONE 
         + "," + TIPO_APPALTO_AGG.DATA_FINE_TIPOAPP + " = null"
         + " WHERE " 
         + TIPO_APPALTO_AGG.ID_LOTTO + " IN ("
         + "SELECT " + LOTTO.ID_LOTTO + " FROM " + LOTTO.TABLE_NAME
            + " WHERE " + LOTTO.ID_GARA + " = ? AND " + LOTTO.DATA_CANCELLAZIONE_LOTTO + " IS NULL )";   

   public void sbloccaTipiAppalto(long id_Gara) throws SQLException {
      PreparedStatement stmt = activeConnection.prepareStatement(QUERY_SBLOCCA);
      logger.debug("querysbloccaTA: " + QUERY_SBLOCCA);
      try{
         stmt.setLong(1, id_Gara);
         
         stmt.execute();
      }finally{
         close(null,stmt);
      }   
   }  
}

		
	
	


