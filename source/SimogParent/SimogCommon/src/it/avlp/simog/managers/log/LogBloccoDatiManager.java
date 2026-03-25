package it.avlp.simog.managers.log;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.ACCORDI;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.db.generated.COLLAUDO;
import it.avlp.simog.db.generated.FINE_LAVORI;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.INIZIO_LAVORI;
import it.avlp.simog.db.generated.LOG_OPERAZIONI;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.R129;
import it.avlp.simog.db.generated.SOSPENSIONI;
import it.avlp.simog.db.generated.STATI_AVANZ;
import it.avlp.simog.db.generated.STIPULA;
import it.avlp.simog.db.generated.SUBAPPALTI;
import it.avlp.simog.db.generated.VARIANTI;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.GenericUtilValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

public class LogBloccoDatiManager extends AccessiDB{
	//protected static Logger logger = Logger.getLogger(LogBloccoDatiManager.class);
	
//	Timestamp dataOperazione;
//	String operazione;
//	String cfUtente;
//	String idRecord;
//	String bloccoDati;
	
   public enum tipiOp{
      CREAZIONE("CREAZIONE")
      ,MODIFICA("MODIFICA")
      ,ELIMINAZIONE("ELIMINAZIONE")
      ,CONFERMA("CONFERMA")
      ,RICH_ANN("RICH_ANN")
      ,CONF_ANN("CONF_ANN")
      ,REV_ANN("REV_ANN")
      ,RICH_DEL("RICH_DEL")
      ,CONF_DEL("CONF_DEL")
      ,REV_DEL("REV_DEL")
      ,PUBBRETTAVVISO("PUBB.RETT.AVVISO")
      ,PUBBAVVISO("PUBB.AVVISO")
      ,PRESACAR("PRESA_CARICO")
      ;
      
      private String descrizione;
            
      tipiOp(String descrizione){
        this.setDescrizione(descrizione);
      }

      public String getDescrizione() {
         return descrizione;
      }

      public void setDescrizione(String descrizione) {
         this.descrizione = descrizione;
      }
   }
   
	private final String insertLog =
			"INSERT INTO "
			+ LOG_OPERAZIONI.TABLE_NAME
			+"("
			+ LOG_OPERAZIONI.CF_UTENTE
			+ ", " + LOG_OPERAZIONI.OPERAZIONE
			+ ", " + LOG_OPERAZIONI.DATA_OPERAZIONE
			+ ", " + LOG_OPERAZIONI.BLOCCO_DATI
			+ ", " + LOG_OPERAZIONI.ID_RECORD
			
			+ ") " +
			"VALUES (?, ?, ?, ?, ?)";
	
	/***********************************************
	 * Costruttore
	 * 
	 * @param connection
	 * @param logger
	 */
	public LogBloccoDatiManager(Connection connection, Logger logger){
		super(connection, logger);
	}
	
	/******************************************************************************************************
	 * Inserisce il log
	 * 
	 * @param cfUtente String
	 * @param bloccoDati String 
	 * @param dataOperazione Timestamp
	 * @param idRecord String
	 * @param operazione String
	 * @return int - indica il numero di elementi inseriti
	 * @throws SQLException
	 */
	private int log(String cfUtente, String bloccoDati, Timestamp dataOperazione, String idRecord, String operazione ) throws SQLException{
		
		PreparedStatement ps = null;
		int executionResult = 0;
		try{
		ps = activeConnection.prepareStatement(insertLog);
		
		logger.debug( "Inserimento sulla tabella LOG_operazioni ");
//		logger.debug( "Tentativo di inserimento su log blocco dati cfutente [" + cfUtente + "]");
//		logger.debug( "Tentativo di inserimento su log blocco dati bloccoDati [" + bloccoDati + "]");
//		logger.debug( "Tentativo di inserimento su log blocco dati dataOperazione [" + dataOperazione + "]");
//		logger.debug( "Tentativo di inserimento su log blocco dati idRecord [" + idRecord + "]");
//		logger.debug( "Tentativo di inserimento su log blocco dati operazione [" + operazione + "]");
		
		ps.setObject(1, cfUtente);
		ps.setObject(2, operazione);
		ps.setObject(3, dataOperazione);
		ps.setObject(4, bloccoDati);		
		ps.setObject(5, idRecord);
		
		executionResult = ps.executeUpdate();
		}
		finally {
			try {
				ps.close();
			} catch ( Exception e ) {}
			ps = null;
		}
		return executionResult;
	}
	
	
	
	/**************************************************************************************************
	 * Inserisce un log di Insert
	 * 
	 * @param conn Connection
	 * @param logger Logger
	 * @param cfUtente String
	 * @param bloccoDati String 
	 * @param attributiChiave List&lt;Object&gt;
	 */
	public static void loggingINSERT(Connection conn, Logger logger, String cfUtente, String bloccoDati, List<Object> attributiChiave){
		new LogBloccoDatiManager(conn, logger).logInsert(cfUtente, bloccoDati, attributiChiave);
	}

	/*********************************************************************************************
	 * Inserisce un log di modifica
	 * 
	 * @param conn Connection
	 * @param logger Logger
	 * @param cfUtente String 
	 * @param bloccoDati String 
	 * @param attributiChiave List&lt;Object&gt;
	 */
	public static void loggingUPDATE(Connection conn, Logger logger, String cfUtente, String bloccoDati, List<Object> attributiChiave){
		new LogBloccoDatiManager(conn, logger).logUpdate(cfUtente, bloccoDati, attributiChiave);
	}
	
	/**********************************************************************************************
	 * Inserisce un log di cancellazione
	 * 
	 * @param conn Connection
	 * @param logger Logger
	 * @param cfUtente String 
	 * @param bloccoDati tring 
	 * @param attributiChiave List&lt;Object&gt;
	 */
	public static void loggingDELETE(Connection conn, Logger logger, String cfUtente, String bloccoDati, List<Object> attributiChiave){
		new LogBloccoDatiManager(conn, logger).logDelete(cfUtente, bloccoDati, attributiChiave);
	}
	
	/***************************************************************************************************
	 * Log operazione di conferma scheda
	 * @param conn
	 * @param logger
	 * @param cfUtente
	 * @param bloccoDati
	 * @param attributiChiave
	 */
	public static void loggingCONFIRM(Connection conn, Logger logger, String cfUtente, String bloccoDati, List<Object> attributiChiave){
		new LogBloccoDatiManager(conn, logger).logConfirm(cfUtente, bloccoDati, attributiChiave);
	}
	
	
	
	/****************************************************************************************************
	 * inserisce un log per l'inserimento di una richiesta di cancellazione 
	 * 
	 * @param conn Connection 
	 * @param logger Logger
	 * @param cfUtente String 
	 * @param bloccoDati String
	 * @param attributiChiave List&lt;Object&gt;
	 */
	public static void loggingDELETEREQ(Connection conn, Logger logger, String cfUtente, String bloccoDati, List<Object> attributiChiave){
		new LogBloccoDatiManager(conn, logger).logDeleteReq(cfUtente, bloccoDati,attributiChiave);
	}
	
	/****************************************************************************************************
	 * Inserisce un log per la conferma di una richiesta di cancellazione
	 * 
	 * @param conn Connection
	 * @param logger Logger
	 * @param cfUtente String
	 * @param bloccoDati String 
	 * @param attributiChiave List&lt;Object&gt;
	 */
	public static void loggingCONFIRMCANC(Connection conn, Logger logger, String cfUtente, String bloccoDati, List<Object> attributiChiave){
		new LogBloccoDatiManager(conn, logger).logConfirmCanc(cfUtente, bloccoDati, attributiChiave);
	}

	/*********************************************************************************************
	 * Inserisce un log di rifiuto richiesta cancellazione
	 * 
	 * @param conn Connection
	 * @param logger Logger
	 * @param cfUtente String
	 * @param bloccoDati String
	 * @param attributiChiave List&lt;Object&gt;
	 */
	
	public static void loggingREVDELETE	(Connection conn, Logger logger, String cfUtente, String bloccoDati, List<Object> attributiChiave){
		new LogBloccoDatiManager(conn, logger).logRevDelete(cfUtente, bloccoDati, attributiChiave);
	}
	

	/****************************************************************************************************
	 * inserisce un log per l'inserimento di una richiesta di annullamento 
	 * 
	 * @param conn Connection 
	 * @param logger Logger
	 * @param cfUtente String 
	 * @param bloccoDati String
	 * @param attributiChiave List&lt;Object&gt;
	 */
	public static void loggingCANCELREQ(Connection conn, Logger logger, String cfUtente, String bloccoDati, List<Object> attributiChiave){
		new LogBloccoDatiManager(conn, logger).logCancelReq(cfUtente, bloccoDati,attributiChiave);
	}
	
	/****************************************************************************************************
	 * Inserisce un log per la conferma di una richiesta di annullamento
	 * 
	 * @param conn Connection
	 * @param logger Logger
	 * @param cfUtente String
	 * @param bloccoDati String 
	 * @param attributiChiave List&lt;Object&gt;
	 */
	public static void loggingCONFIRMANN(Connection conn, Logger logger, String cfUtente, String bloccoDati, List<Object> attributiChiave){
		new LogBloccoDatiManager(conn, logger).logConfirmAnn(cfUtente, bloccoDati, attributiChiave);
	}

	/*********************************************************************************************
	 * Inserisce un log di rifiuto richiesta annulamento
	 * 
	 * @param conn Connection
	 * @param logger Logger
	 * @param cfUtente String
	 * @param bloccoDati String
	 * @param attributiChiave List&lt;Object&gt;
	 */
	public static void loggingREVCANCEL(Connection conn, Logger logger, String cfUtente, String bloccoDati, List<Object> attributiChiave){
		new LogBloccoDatiManager(conn, logger).logRevCancel(cfUtente, bloccoDati, attributiChiave);
	}
	
	
	/**********************************************************************************************
	 * Inserisce un log di Creazione
	 * 
	 * @param cfUtente
	 * @param bloccoDati
	 * @param attributiChiave
	 */
	private void logInsert(String cfUtente, String bloccoDati,List<Object> attributiChiave){
		try {
			log(cfUtente,bloccoDati,getNow(),getIdRecord(attributiChiave), tipiOp.CREAZIONE.getDescrizione());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());			
			//e.printStackTrace();
		}
	}
	
	/**********************************************************************************************
	 * Inserisce un log di Modifica
	 * 
	 * @param cfUtente String 
	 * @param bloccoDati String 
	 * @param attributiChiave List&lt;Object&gt;
	 */
	private void logUpdate(String cfUtente, String bloccoDati, List<Object> attributiChiave){
		try {
			
			log(cfUtente,bloccoDati,getNow(),getIdRecord(attributiChiave),tipiOp.MODIFICA.getDescrizione());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			//e.printStackTrace();
		}
	}
	
	
	/**********************************************************************************************
	 * inserisce un log per l'eliminazione
	 * 
	 * @param cfUtente String 
	 * @param bloccoDati String
	 * @param attributiChiave List&lt;Object&gt;
	 */
	private void logDelete(String cfUtente, String bloccoDati,List<Object> attributiChiave){
		try {
			log(cfUtente,bloccoDati,getNow(),getIdRecord(attributiChiave),tipiOp.ELIMINAZIONE.getDescrizione());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			//e.printStackTrace();
		}
	}
	
	/**********************************************************************************************
	 * Inserisce un log di conferma
	 * 
	 * @param cfUtente String 
	 * @param bloccoDati String
	 * @param attributiChiave List&lt;Object&gt;
	 */
	private void logConfirm(String cfUtente, String bloccoDati,  List<Object> attributiChiave){
		try {
			log(cfUtente,bloccoDati,getNow(),getIdRecord(attributiChiave),tipiOp.CONFERMA.getDescrizione());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			//e.printStackTrace();
		}
	}
	

	/**********************************************************************************************
	 * Si inserisce un log di richiesta annullamento
	 * 
	 * @param cfUtente String
	 * @param bloccoDati String
	 * @param attributiChiave List&lt;Object&gt;
	 */
	private void logCancelReq(String cfUtente, String bloccoDati,  List<Object> attributiChiave){
		try {
			log(cfUtente,bloccoDati,getNow(),getIdRecord(attributiChiave),tipiOp.RICH_ANN.getDescrizione());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			//e.printStackTrace();
		}
	}
	
	/**********************************************************************************************
	 * Inserisce un log di conferma richiesta di annullamento
	 * 
	 * @param cfUtente String
	 * @param bloccoDati String
	 * @param attributiChiave List&lt;Object&gt;
	 */
	private void logConfirmAnn(String cfUtente, String bloccoDati,List<Object> attributiChiave){
		try {
			log(cfUtente,bloccoDati,getNow(),getIdRecord(attributiChiave),tipiOp.CONF_ANN.getDescrizione());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			//e.printStackTrace();
		}
	}
	
	/**************************************************************************************************
	 * Inserisce un log di rifiuto richiesta annullamento
	 * 
	 * @param cfUtente String
	 * @param bloccoDati String
	 * @param attributiChiave List&lt;Object&gt;
	 */
	private void logRevCancel(String cfUtente, String bloccoDati,  List<Object> attributiChiave){
		try {
			log(cfUtente,bloccoDati,getNow(),getIdRecord(attributiChiave),tipiOp.REV_ANN.getDescrizione());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			//e.printStackTrace();
		}
	}
	
	
	/**********************************************************************************************
	 * Si inserisce un log di richiesta CANCELLAZIONE
	 * 
	 * @param cfUtente String
	 * @param bloccoDati String
	 * @param attributiChiave List&lt;Object&gt;
	 */
	
	private void logDeleteReq(String cfUtente, String bloccoDati,  List<Object> attributiChiave){
		try {
			log(cfUtente,bloccoDati,getNow(),getIdRecord(attributiChiave),tipiOp.RICH_DEL.getDescrizione());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			//e.printStackTrace();
		}
	}
	
	/**********************************************************************************************
	 * Inserisce un log di conferma richiesta di cancellazione
	 * 
	 * @param cfUtente String
	 * @param bloccoDati String
	 * @param attributiChiave List&lt;Object&gt;
	 */
	private void logConfirmCanc(String cfUtente, String bloccoDati,List<Object> attributiChiave){
		try {
			log(cfUtente,bloccoDati,getNow(),getIdRecord(attributiChiave),tipiOp.CONF_DEL.getDescrizione());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			//e.printStackTrace();
		}
	}

	/**************************************************************************************************
	 * Inserisce un log rifiuto cancellazione
	 * 
	 * @param cfUtente String
	 * @param bloccoDati String
	 * @param attributiChiave List&lt;Object&gt;
	 */
	private void logRevDelete(String cfUtente, String bloccoDati,  List<Object> attributiChiave){
		try {
			log(cfUtente,bloccoDati,getNow(),getIdRecord(attributiChiave),tipiOp.REV_DEL.getDescrizione());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			//e.printStackTrace();
		}
	}
	
	
	/**************************************************************************************************
	 * Genera una stringa con gli elementi della lista passata in ingreso, Formattando la data in 
	 * maniera appropriata ed eliminando gli spazi vuoti inserendo un |.  
	 * 
	 * param attrPrimari List&lt;Object&gt;
	 * return String
	 */
	private String getIdRecord(List<Object> attrPrimari){
		StringBuffer idRecord = new StringBuffer();
		
		for (int i=0; i < attrPrimari.size(); i++){
			
			String string = attrPrimari.get(i).toString();
		
			if(string.contains("-") || string.contains("/") ){
			  
			  idRecord.append(PageHelper.getFormattedLogDateTime(string)+ "|");
			}
			
			else idRecord.append(string.replace(" ", "") + "|");
			
			
		}
		
		String record = idRecord.substring(0, idRecord.length() -1).replace(":", "");
		
		
		return record;
	}
	
	/****************************************************************************************************
	 * Recupera un log in base ai parametri di ingresso
	 * @param cfUtente String
	 * @param bloccoDati String 
	 * @param fromData String 
	 * @param toData String 
	 * @param orderField String 
	 * @param ascDesc boolean
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getLog(
			
			String cfUtente,
			String bloccoDati,
			String fromData,
			String toData,
			String orderField,
			boolean ascDesc,
			int startRow,
			int maxRowsAllowed)
			throws SQLException {
		
		boolean dateNotNull = false;
		boolean cfUtenteNotNull = false;
		boolean bloccoNotNull = false;
		 
		String query = "SELECT " + LOG_OPERAZIONI.TABLE_NAME + ".* " + getQueryCigNew(bloccoDati) + " FROM "+ LOG_OPERAZIONI.TABLE_NAME+ " WHERE 1 = 1 ";
					
		if(fromData != null && toData!= null){
			query += " AND " + LOG_OPERAZIONI.DATA_OPERAZIONE + " >= ? AND " + LOG_OPERAZIONI.DATA_OPERAZIONE + " <= ? + 1";
			dateNotNull = true;
		}
		if( cfUtente != null && ! "".equalsIgnoreCase(cfUtente) ) {
			cfUtenteNotNull = true;
			query += " AND " + LOG_OPERAZIONI.CF_UTENTE + " = ? "; 
		}
		if ( bloccoDati != null && ! "".equalsIgnoreCase(bloccoDati) ) {
			bloccoNotNull = true;
			query += " AND " + LOG_OPERAZIONI.BLOCCO_DATI + " = ? ";		
		}
		if ( orderField != null ) {
			String ascDescStr = ascDesc ? " ASC " : " DESC ";
			orderField = orderField + ascDescStr;
		} else {
			orderField = LOG_OPERAZIONI.DATA_OPERAZIONE + " DESC";
		}
		query += " ORDER BY " + orderField;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		TableBean tb = null;
		try{
		ps = activeConnection.prepareStatement(query); 
		int index = 0;
		if(dateNotNull == true) { // data non nulla
			index++;
			ps.setDate(index, PageHelper.getSqlDateFromFormattedDate(fromData));
			index++;
			ps.setDate(index, PageHelper.getSqlDateFromFormattedDate(toData));
		}		
		if(cfUtenteNotNull == true) {
			index++;
			ps.setObject(index, cfUtente);
		}		
		if(bloccoNotNull == true) {
			index++;
			ps.setObject(index, bloccoDati);
		}
		logger.debug("Esecuzione query [" + query + "]");
	    logger.debug("log operazioni, query: "+ query);
		rs = ps.executeQuery();
		tb = new TableBean(rs,startRow,maxRowsAllowed);
		}
		finally{
		close(rs,ps);
		}
		return tb;
	}
	/****************************************************************************************************
	 * Recupera un log in base ai parametri di ingresso [dangerous]
	 * 
	 * @param cfUtente String
	 * @param bloccoDati String 
	 * @param fromData String 
	 * @param toData String 
	 * @param orderField String 
	 * @param ascDesc boolean
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getLog(
			
			String cfUtente,
			String bloccoDati,
			String fromData,
			String toData,
			String orderField,
			boolean ascDesc,
			int startRow,
			int maxRowsAllowed,
			String fullCIG)
			throws SQLException {
		
		boolean dateNotNull = false;
		boolean cfUtenteNotNull = false;
		boolean bloccoNotNull = false;
		String query = "" ;
		String idInfo = getIdInfo(fullCIG);
		
		if ("0".equals(idInfo) || "".equals(idInfo)){
			return new TableBean();
		}
		else if(!"".equals(idInfo)){
			query = this.getBigNew(idInfo, bloccoDati);
		}				
		if(fromData != null && toData!= null){
			query = query.replace("$1", LOG_OPERAZIONI.DATA_OPERAZIONE + " >= ? AND " + LOG_OPERAZIONI.DATA_OPERAZIONE + " <= ? + 1 AND " ) ;
			dateNotNull = true;
		}else{query = query.replace("$1","");}
		if( cfUtente != null && ! "".equalsIgnoreCase(cfUtente) ) {
			cfUtenteNotNull = true;
			query = query.replace("$2",LOG_OPERAZIONI.CF_UTENTE + " = ? and "); 
		}else{query = query.replace("$2","");}
		if ( bloccoDati != null && ! "".equalsIgnoreCase(bloccoDati) ) {
			bloccoNotNull = true;
			query = query.replace("$3",LOG_OPERAZIONI.BLOCCO_DATI + " = ? and ");		
		}else{query = query.replace("$3","");}
		if ( orderField != null ) {
			String ascDescStr = ascDesc ? " ASC " : " DESC ";
			orderField = orderField + ascDescStr;
		} else {
			orderField = LOG_OPERAZIONI.DATA_OPERAZIONE + " DESC";
		}
		query += " ORDER BY " + orderField;
		logger.debug("[] "+query);
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		TableBean tb = null;
		try{
		ps = activeConnection.prepareStatement(query); 
		int index = 0;
		if(dateNotNull == true) { // data non nulla
			index++;
			ps.setDate(index, PageHelper.getSqlDateFromFormattedDate(fromData));
			index++;
			ps.setDate(index, PageHelper.getSqlDateFromFormattedDate(toData));
		}		
		if(cfUtenteNotNull == true) {
			index++;
			ps.setObject(index, cfUtente);
		}		
		if(bloccoNotNull == true) {
			index++;
			ps.setObject(index, bloccoDati);
		}

		logger.debug("Esecuzione query [" + query + "]");
	    logger.debug("log operazioni, query: "+ query);
		rs = ps.executeQuery();
		tb = new TableBean(rs,startRow,maxRowsAllowed);
		}
		finally{
		close(rs,ps);
		}
		return tb;
	}
//	private String getBig(String temp){
//	
//		String bloccoDati = "blocco_dati";
//		String tableId = "tableid";
//		String tableId_id = "tableid.id";
//		
//		String query =
//		"SELECT " + LOG_OPERAZIONI.TABLE_NAME + ".* " + getQueryCig() + " FROM " + LOG_OPERAZIONI.TABLE_NAME + " right join " +
//        "(SELECT distinct(SUBSTRING(" + LOG_OPERAZIONI.ID_RECORD + ", 0, LEN(" + LOG_OPERAZIONI.ID_RECORD + ") - 14)) " + " AS id FROM " + LOG_OPERAZIONI.TABLE_NAME + " ) as " + tableId +
//		" on SUBSTRING (" + LOG_OPERAZIONI.ID_RECORD + ", 0, LEN(" + LOG_OPERAZIONI.ID_RECORD + ") - 14) = " + tableId_id +
//		" WHERE $1$2$3 SUBSTRING(" + LOG_OPERAZIONI.ID_RECORD + ", 0, LEN(" + LOG_OPERAZIONI.ID_RECORD + ") - 14) in (case " +
//		
//		// per info_comuni
//		" WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_INFO_COMUNI + "' THEN $4 " + 
//		
//		//per aggiudicazione
//		" WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_AGGIUDICAZIONE + 
//		"' or " + bloccoDati + " = '" + IdentificativoSchede.TAB_SOTTOSOGLIA +
//		"' or " + bloccoDati + " = '" + IdentificativoSchede.TAB_ADESIONE +
//		"' or " + bloccoDati + " = '" + IdentificativoSchede.TAB_ESCLUSI 	+ "' THEN " + 
//		" (SELECT distinct(" + AGGIUDICAZIONI.ID_AGGIUDICAZIONE + ")" +
//		" FROM " + AGGIUDICAZIONI.TABLE_NAME + 
//		" WHERE " + AGGIUDICAZIONI.ID_INFO + " = $4  AND " + 
//		AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = " + tableId_id + ") " + 
//		
//		//per aggiudicazione
//		" WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_SOTTOSOGLIA + "' THEN " + 
//		" (SELECT distinct(" + AGGIUDICAZIONI.ID_AGGIUDICAZIONE + ")" +
//		" FROM " + AGGIUDICAZIONI.TABLE_NAME + 
//		" WHERE " + AGGIUDICAZIONI.ID_INFO + " = $4  AND " + 
//		AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = " + tableId_id + ") " + 
//		
//		//per aggiudicazione
//		" WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_ESCLUSI + "' THEN " + 
//		" (SELECT distinct(" + AGGIUDICAZIONI.ID_AGGIUDICAZIONE + ")" +
//		" FROM " + AGGIUDICAZIONI.TABLE_NAME + 
//		" WHERE " + AGGIUDICAZIONI.ID_INFO + " = $4  AND " + 
//		AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = " + tableId_id + ") " + 
//		
//		//per inizio_lavori
//		" WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_INIZIO_LAVORI + "' THEN " + 
//		" (SELECT distinct(" + INIZIO_LAVORI.T_ID_INIZIO + ")" +
//		" FROM " + AGGIUDICAZIONI.TABLE_NAME + ", " + INIZIO_LAVORI.TABLE_NAME + 
//		" WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4  AND " +
//		AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + INIZIO_LAVORI.T_ID_AGGIUDICAZIONE + " AND " +
//		INIZIO_LAVORI.ID_INIZIO + " = " + tableId_id + ") " + 
//		
//		//per stipula
//		" WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_STIPULA + "' THEN " + 
//        " (SELECT distinct(" + STIPULA.T_ID_STIPULA + ")" + 
//        " FROM " + AGGIUDICAZIONI.TABLE_NAME + ", " + STIPULA.TABLE_NAME +
//        " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
//        AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + STIPULA.T_ID_AGGIUDICAZIONE + " AND " +
//        STIPULA.ID_STIPULA + " = " + tableId_id + ") " + 
//       
//        //per avanzamenti
//		" WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_AVANZAMENTO + "' THEN " + 
//		" (SELECT distinct(" + STATI_AVANZ.T_ID_AVANZAMENTO + ")" + 
//	    " FROM " + AGGIUDICAZIONI.TABLE_NAME + ", " + STATI_AVANZ.TABLE_NAME +
//	    " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
//        AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + STATI_AVANZ.T_ID_AGGIUDICAZIONE + " AND " +
//        STATI_AVANZ.ID_AVANZAMENTO + " = " + tableId_id + ") " + 
//      
//        //per accordo
//        " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_ACCORDO + "' THEN " + 
//		" (SELECT distinct(" + ACCORDI.T_ID_ACCORDO + ")" + 
//	    " FROM " + AGGIUDICAZIONI.TABLE_NAME + ", " + ACCORDI.TABLE_NAME +
//	    " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
//        AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + ACCORDI.T_ID_AGGIUDICAZIONE + " AND " +
//        ACCORDI.ID_ACCORDO + " = " + tableId_id + ") " + 
//      
//        //per collaudo
//        " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_COLLAUDO + "' THEN " + 
//		" (SELECT distinct(" + COLLAUDO.T_ID_COLLAUDO + ")" + 
//	    " FROM " + AGGIUDICAZIONI.TABLE_NAME + ", " + COLLAUDO.TABLE_NAME +
//	    " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
//        AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + COLLAUDO.T_ID_AGGIUDICAZIONE + " AND " +
//        COLLAUDO.ID_COLLAUDO + " = " + tableId_id + ") " + 
//     
//        //per fine lavori
//        " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_FINELAVORI + "' THEN " + 
//		" (SELECT distinct(" + FINE_LAVORI.T_ID_ULTIM + ")" + 
//	    " FROM " + AGGIUDICAZIONI.TABLE_NAME + ", " + FINE_LAVORI.TABLE_NAME +
//	    " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
//        AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + FINE_LAVORI.T_ID_AGGIUDICAZIONE + " AND " +
//        FINE_LAVORI.ID_ULTIM + " = " + tableId_id + ") " + 
//    
//        //per ritardo
//        " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_RITARDO + "' THEN " + 
//		" (SELECT distinct(" + R129.T_ID_RECORD + ")" + 
//	    " FROM " + AGGIUDICAZIONI.TABLE_NAME + ", " + R129.TABLE_NAME +
//	    " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
//        AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + R129.T_ID_AGGIUDICAZIONE + " AND " +
//        R129.ID_RECORD + " = " + tableId_id + ") " + 
//     
//        //per sospensioni
//        " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_SOSPENSIONE + "' THEN " + 
//		" (SELECT distinct(" + SOSPENSIONI.T_ID_SOSPENSIONE + ")" + 
//	    " FROM " + AGGIUDICAZIONI.TABLE_NAME + ", " + SOSPENSIONI.TABLE_NAME +
//	    " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
//        AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + SOSPENSIONI.T_ID_AGGIUDICAZIONE + " AND " +
//        SOSPENSIONI.ID_SOSPENSIONE + " = " + tableId_id + ") " + 
//     
//        //per subappalti
//        " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_SUBAPPALTO + "' THEN " + 
//		" (SELECT distinct(" + SUBAPPALTI.T_ID_RECORD + ")" + 
//	    " FROM " + AGGIUDICAZIONI.TABLE_NAME + ", " + SUBAPPALTI.TABLE_NAME +
//	    " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
//        AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + SUBAPPALTI.T_ID_AGGIUDICAZIONE + " AND " +
//        SUBAPPALTI.ID_RECORD + " = " + tableId_id + ") " + 
//    
//        //per varianti
//        " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_VARIANTE + "' THEN " + 
//		" (SELECT distinct(" + VARIANTI.T_ID_VARIANTE + ")" + 
//	    " FROM " + AGGIUDICAZIONI.TABLE_NAME + ", " + VARIANTI.TABLE_NAME +
//	    " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
//        AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + VARIANTI.T_ID_AGGIUDICAZIONE + " AND " +
//        VARIANTI.ID_VARIANTE + " = " + tableId_id + ") " + 
//     
//		"ELSE '0' END) ";		
//		logger.debug(temp);
//		query = query.replace("$4", "'"+temp+"'");
//		logger.debug(query);
//		return query;
//	}
	
//	private String getQueryCig(){
//		String bloccoDati = "blocco_dati";
//		
//		String query =
//			" ,(case "
//			
//			//per infocomuni
//			+ " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_INFO_COMUNI + "' then "
//			  + " (select distinct(case " + LOTTO.T_SOMMA_URGENZA + " when 'S' then '9' else '0' end + substring(" + LOTTO.T_CIG + ",2,10) + " + LOTTO.T_CIG_KKK + ")"
//			    + " from " + INFO_AGGIUDICAZIONI.TABLE_NAME + ", " + LOTTO.TABLE_NAME
//			    + " where " + INFO_AGGIUDICAZIONI.T_ID_INFO + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)"
//			    + " and " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + ")" 
//			
//			//per aggiudicazioni					
//		    + " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_AGGIUDICAZIONE
//			+ "' or " + bloccoDati + " = '" + IdentificativoSchede.TAB_SOTTOSOGLIA
//			+ "' or " + bloccoDati + " = '" + IdentificativoSchede.TAB_ADESIONE
//			+ "' or " + bloccoDati + " = '" + IdentificativoSchede.TAB_ESCLUSI 	+ "' then "
//			
//			+ " (select distinct( ( select distinct(case " + LOTTO.T_SOMMA_URGENZA + " when 'S' then '9' else '0' end)"
//			    + " from " + INFO_AGGIUDICAZIONI.TABLE_NAME + ", " + LOTTO.TABLE_NAME
//			    + " where " + INFO_AGGIUDICAZIONI.T_ID_INFO + " = " + AGGIUDICAZIONI.T_ID_INFO 
//			    + " and " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + ") + substring(" + AGGIUDICAZIONI.T_CUI + ",4,20) + '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
//                  + " from " + AGGIUDICAZIONI.TABLE_NAME 
//                  + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ",0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14))"
//            
//            //per inizio lavori
//			+ " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_INIZIO_LAVORI + "' then "
//			  + " (select distinct( ( select distinct (case " + LOTTO.T_SOMMA_URGENZA + " when 'S' then '9' else '0' end)"
//			  + " from " + INFO_AGGIUDICAZIONI.TABLE_NAME + "," + LOTTO.TABLE_NAME 
//		      + " where " + INFO_AGGIUDICAZIONI.T_ID_INFO + " = " + AGGIUDICAZIONI.T_ID_INFO
//		      + " and " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + ") + substring( + " + AGGIUDICAZIONI.T_CUI + ",4,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
//		         + " from " + AGGIUDICAZIONI.TABLE_NAME
//		         + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
//		           + " = (select distinct (" + INIZIO_LAVORI.T_ID_AGGIUDICAZIONE + ")"
//		           + " from " + INIZIO_LAVORI.TABLE_NAME
//		           + " where " + INIZIO_LAVORI.T_ID_INIZIO + " =  SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"
//		   
//		    //per stipula
//			+ " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_STIPULA + "' then "
//			  + " (select distinct( ( select distinct (case " + LOTTO.T_SOMMA_URGENZA + " when 'S' then '9' else '0' end)"
//			  + " from " + INFO_AGGIUDICAZIONI.TABLE_NAME + "," + LOTTO.TABLE_NAME 
//		      + " where " + INFO_AGGIUDICAZIONI.T_ID_INFO + " = " + AGGIUDICAZIONI.T_ID_INFO
//		      + " and " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + ") + substring( + " + AGGIUDICAZIONI.T_CUI + ",4,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
//		         + " from " + AGGIUDICAZIONI.TABLE_NAME
//		         + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
//		           + " = (select distinct (" + STIPULA.T_ID_AGGIUDICAZIONE + ")"
//		           + " from " + STIPULA.TABLE_NAME
//		           + " where " + STIPULA.T_ID_STIPULA + " =  SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"
//				                       
//		    //per avanzamento     
//		    + " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_AVANZAMENTO + "' then"
//			  + " (select distinct((select distinct(case " + LOTTO.T_SOMMA_URGENZA + " when 'S' then '9' else '0' end)"
//		      + " from " + INFO_AGGIUDICAZIONI.TABLE_NAME + "," + LOTTO.TABLE_NAME
//			  + " where " + INFO_AGGIUDICAZIONI.T_ID_INFO + " = " + AGGIUDICAZIONI.T_ID_INFO
//		      + " and " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + ") + substring(" + AGGIUDICAZIONI.T_CUI + ",4,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
//		        + " from " + AGGIUDICAZIONI.TABLE_NAME
//		        + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
//		        + " = (select distinct (" + STATI_AVANZ.ID_AGGIUDICAZIONE + ")"
//		          + " from " + STATI_AVANZ.TABLE_NAME
//	              + " where " + STATI_AVANZ.T_ID_AVANZAMENTO + " =  SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"
//	        
//	        //per accordo
//	  		  + " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_ACCORDO + "' then"
//	    	  + " (select distinct((select distinct(case " + LOTTO.T_SOMMA_URGENZA + " when 'S' then '9' else '0' end)"
//		        + " from " + INFO_AGGIUDICAZIONI.TABLE_NAME + "," + LOTTO.TABLE_NAME
//		        + " where " + INFO_AGGIUDICAZIONI.T_ID_INFO + " = " + AGGIUDICAZIONI.T_ID_INFO
//		        + " and " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + ") + substring(" + AGGIUDICAZIONI.T_CUI + ",4,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
//		          + " from " + AGGIUDICAZIONI.TABLE_NAME
//	              + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
//	              + " = (select distinct (" + ACCORDI.T_ID_AGGIUDICAZIONE + ")"
//	                + " from " + ACCORDI.TABLE_NAME
//	                + " where " + ACCORDI.T_ID_ACCORDO + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"
//	       
//	        //per collaudo
//	        + " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_COLLAUDO + "' then"
//		    + " (select distinct((select distinct(case " + LOTTO.T_SOMMA_URGENZA +  " when 'S' then '9' else '0' end)"
//		      + " from " + INFO_AGGIUDICAZIONI.TABLE_NAME + "," + LOTTO.TABLE_NAME
//	     	  + " where " + INFO_AGGIUDICAZIONI.T_ID_INFO + " = " + AGGIUDICAZIONI.T_ID_INFO
//		      + " and " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + ") + substring(" + AGGIUDICAZIONI.T_CUI + ",4,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
//	            + " from " + AGGIUDICAZIONI.TABLE_NAME
//		        + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
//	            + " = (select distinct (" + COLLAUDO.T_ID_AGGIUDICAZIONE + ")"
//                  + " from " + COLLAUDO.TABLE_NAME
//	              + " where " + COLLAUDO.T_ID_COLLAUDO + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"				
//	        
//	         //per fine lavori
//		     + " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_FINELAVORI + "' then"
//			   + " (select distinct((select distinct(case " + LOTTO.T_SOMMA_URGENZA + " when 'S' then '9' else '0' end)"
//			     + " from " + INFO_AGGIUDICAZIONI.TABLE_NAME + "," + LOTTO.TABLE_NAME
//			     + " where " + INFO_AGGIUDICAZIONI.T_ID_INFO + " = " + AGGIUDICAZIONI.T_ID_INFO
//			     + " and " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + ") + substring(" + AGGIUDICAZIONI.T_CUI + ",4,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
//			       + " from " + AGGIUDICAZIONI.TABLE_NAME
//			       + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
//	               + " = (select distinct (" + FINE_LAVORI.T_ID_AGGIUDICAZIONE + ")"
//			         + " from " + FINE_LAVORI.TABLE_NAME
//			         + " where " + FINE_LAVORI.T_ID_ULTIM + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"
//			
//			//per ritardi		
//			+ " when " + bloccoDati +  " = '" + IdentificativoSchede.TAB_RITARDO + "' then"
//			  + " (select distinct((select distinct(case " + LOTTO.T_SOMMA_URGENZA + " when 'S' then '9' else '0' end)"
//			    + " from " + INFO_AGGIUDICAZIONI.TABLE_NAME + "," + LOTTO.TABLE_NAME
//			    + " where " + INFO_AGGIUDICAZIONI.T_ID_INFO + " = " + AGGIUDICAZIONI.T_ID_INFO
//			    + " and " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + ") + substring(" + AGGIUDICAZIONI.T_CUI + ",4,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
//		          + " from " + AGGIUDICAZIONI.TABLE_NAME
//	              + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
//	              + " = (select distinct (" + R129.T_ID_AGGIUDICAZIONE + ")"
//	                + " from " + R129.TABLE_NAME
//		            + " where " + R129.T_ID_RECORD + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"
//		    
//		    //per sospensioni        
//		    + " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_SOSPENSIONE + "' then"
//			  + " (select distinct((select distinct(case " + LOTTO.T_SOMMA_URGENZA + " when 'S' then '9' else '0' end)"
//		      + " from " + INFO_AGGIUDICAZIONI.TABLE_NAME + "," + LOTTO.TABLE_NAME
//			    + " where " + INFO_AGGIUDICAZIONI.T_ID_INFO + " = " + AGGIUDICAZIONI.T_ID_INFO
//			    + " and " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + ") + substring(" + AGGIUDICAZIONI.T_CUI + ",4,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
//			      + " from " + AGGIUDICAZIONI.TABLE_NAME
//			      + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
//	              + " = (select distinct (" + SOSPENSIONI.T_ID_AGGIUDICAZIONE + ")"
//		            + " from " + SOSPENSIONI.TABLE_NAME
//	                + " where " + SOSPENSIONI.T_ID_SOSPENSIONE + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"
//	      
//	       //per subappalti           
//	       + " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_SUBAPPALTO + "' then"
//			    + " (select distinct((select distinct(case " + LOTTO.T_SOMMA_URGENZA + " when 'S' then '9' else '0' end)"
//			     + " from " + INFO_AGGIUDICAZIONI.TABLE_NAME + "," + LOTTO.TABLE_NAME
//			     + " where " + INFO_AGGIUDICAZIONI.T_ID_INFO + " = " + AGGIUDICAZIONI.T_ID_INFO
//			     + " and " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + ") + substring(" + AGGIUDICAZIONI.T_CUI + ",4,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
//			       + " from " + AGGIUDICAZIONI.TABLE_NAME
//			       + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
//	               + " = (select distinct (" + SUBAPPALTI.T_ID_AGGIUDICAZIONE + ")"
//                     + " from " + SUBAPPALTI.TABLE_NAME
//	                 + " where " + SUBAPPALTI.T_ID_RECORD + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"	
//	      
//	       //per varianti          
//	       + " when " + bloccoDati +  "= '" + IdentificativoSchede.TAB_VARIANTE + "' then"
//	         + " (select distinct((select distinct(case " + LOTTO.T_SOMMA_URGENZA + " when 'S' then '9' else '0' end)"
//	    	   + " from " + INFO_AGGIUDICAZIONI.TABLE_NAME + "," + LOTTO.TABLE_NAME
//			   + " where " + INFO_AGGIUDICAZIONI.T_ID_INFO + " = " + AGGIUDICAZIONI.T_ID_INFO
//		       + " and " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + ") + substring(" + AGGIUDICAZIONI.T_CUI + ",4,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
//	             + " from " + AGGIUDICAZIONI.TABLE_NAME
//		         + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
//                 + " = (select distinct (" + VARIANTI.T_ID_AGGIUDICAZIONE + ")"
//                   + " from " + VARIANTI.TABLE_NAME
//	               + " where " + VARIANTI.T_ID_VARIANTE + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"	
//		       
//	       + " else '*** N/D ***'"
//		   + " end) as CIGCUI ";  
//		  
//		return query;
//	}

	private String getBigNew(String idInfo, String scheda){
		
		GenericUtilValidator val = new GenericUtilValidator(this.activeConnection, logger);
		
		String bloccoDati = "blocco_dati";
		String tableId = "tableid";
		String tableId_id = "tableid.id";
		
		String query =
		"SELECT " + LOG_OPERAZIONI.TABLE_NAME + ".* " + getQueryCigNew(scheda) + " FROM " + LOG_OPERAZIONI.TABLE_NAME + " right join " +
        "(SELECT distinct(SUBSTRING(" + LOG_OPERAZIONI.ID_RECORD + ", 0, LEN(" + LOG_OPERAZIONI.ID_RECORD + ") - 14)) " + " AS id FROM " + LOG_OPERAZIONI.TABLE_NAME + " ) as " + tableId +
		" on SUBSTRING (" + LOG_OPERAZIONI.ID_RECORD + ", 0, LEN(" + LOG_OPERAZIONI.ID_RECORD + ") - 14) = " + tableId_id +
		" WHERE $1$2$3 SUBSTRING(" + LOG_OPERAZIONI.ID_RECORD + ", 0, LEN(" + LOG_OPERAZIONI.ID_RECORD + ") - 14) in (case " +
		
		// per info_comuni
		(val.isEmpty(scheda) || IdentificativoSchede.TAB_INFO_COMUNI.equals(scheda)
			? " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_INFO_COMUNI + "' THEN $4 " 
			: "") + 
		
		//per aggiudicazione
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_AGGIUDICAZIONE.equals(scheda)
								 || IdentificativoSchede.TAB_SOTTOSOGLIA.equals(scheda)
								 || IdentificativoSchede.TAB_ADESIONE.equals(scheda)
								 || IdentificativoSchede.TAB_ESCLUSI.equals(scheda)
				? " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_AGGIUDICAZIONE + 
					"' or " + bloccoDati + " = '" + IdentificativoSchede.TAB_SOTTOSOGLIA +
					"' or " + bloccoDati + " = '" + IdentificativoSchede.TAB_ADESIONE +
					"' or " + bloccoDati + " = '" + IdentificativoSchede.TAB_ESCLUSI 	+ "' THEN " + 
					" (SELECT distinct(" + AGGIUDICAZIONI.ID_AGGIUDICAZIONE + ")" +
					" FROM " + AGGIUDICAZIONI.TABLE_NAME + 
					" WHERE " + AGGIUDICAZIONI.ID_INFO + " = $4  AND " + 
					AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = " + tableId_id + ") " 
				: "") + 
		
		//per aggiudicazione
//		" WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_SOTTOSOGLIA + "' THEN " + 
//		" (SELECT distinct(" + AGGIUDICAZIONI.ID_AGGIUDICAZIONE + ")" +
//		" FROM " + AGGIUDICAZIONI.TABLE_NAME + 
//		" WHERE " + AGGIUDICAZIONI.ID_INFO + " = $4  AND " + 
//		AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = " + tableId_id + ") " + 
		
		//per aggiudicazione
//		" WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_ESCLUSI + "' THEN " + 
//		" (SELECT distinct(" + AGGIUDICAZIONI.ID_AGGIUDICAZIONE + ")" +
//		" FROM " + AGGIUDICAZIONI.TABLE_NAME + 
//		" WHERE " + AGGIUDICAZIONI.ID_INFO + " = $4  AND " + 
//		AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = " + tableId_id + ") " + 
		
		//per inizio_lavori
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_INIZIO_LAVORI.equals(scheda)
				? " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_INIZIO_LAVORI + "' THEN " + 
					" (SELECT distinct(" + INIZIO_LAVORI.T_ID_INIZIO + ")" +
					" FROM " + AGGIUDICAZIONI.TABLE_NAME + " join " + INIZIO_LAVORI.TABLE_NAME + 
					" on " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + INIZIO_LAVORI.T_ID_AGGIUDICAZIONE + 
					" WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4  AND " +
					INIZIO_LAVORI.ID_INIZIO + " = " + tableId_id + ") " 				
				: "") + 
		
		//per stipula
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_STIPULA.equals(scheda)
				? " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_STIPULA + "' THEN " + 
			        " (SELECT distinct(" + STIPULA.T_ID_STIPULA + ")" + 
			        " FROM " + AGGIUDICAZIONI.TABLE_NAME + " join  " + STIPULA.TABLE_NAME +
			        " on " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + STIPULA.T_ID_AGGIUDICAZIONE + 
			        " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +    
			        STIPULA.ID_STIPULA + " = " + tableId_id + ") " 
				: "") + 
       
        //per avanzamenti
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_AVANZAMENTO.equals(scheda)
				? " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_AVANZAMENTO + "' THEN " + 
					" (SELECT distinct(" + STATI_AVANZ.T_ID_AVANZAMENTO + ")" + 
				    " FROM " + AGGIUDICAZIONI.TABLE_NAME + " join  " + STATI_AVANZ.TABLE_NAME +
				    " on " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + STATI_AVANZ.T_ID_AGGIUDICAZIONE + 
				    " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
			        STATI_AVANZ.ID_AVANZAMENTO + " = " + tableId_id + ") "  
				: "") + 
      
        //per accordo
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_ACCORDO.equals(scheda)
		       ? " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_ACCORDO + "' THEN " + 
					" (SELECT distinct(" + ACCORDI.T_ID_ACCORDO + ")" + 
				    " FROM " + AGGIUDICAZIONI.TABLE_NAME + " join " + ACCORDI.TABLE_NAME +
			        " on " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + ACCORDI.T_ID_AGGIUDICAZIONE + 
				    " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
			        ACCORDI.ID_ACCORDO + " = " + tableId_id + ") " 
				: "") + 
      
        //per collaudo
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_COLLAUDO.equals(scheda)
		       ? " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_COLLAUDO + "' THEN " + 
					" (SELECT distinct(" + COLLAUDO.T_ID_COLLAUDO + ")" + 
				    " FROM " + AGGIUDICAZIONI.TABLE_NAME + " join " + COLLAUDO.TABLE_NAME +
			        " on " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + COLLAUDO.T_ID_AGGIUDICAZIONE + 
				    " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
			        COLLAUDO.ID_COLLAUDO + " = " + tableId_id + ") " 
				: "") + 
     
        //per fine lavori
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_FINELAVORI.equals(scheda)				
		        ? " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_FINELAVORI + "' THEN " + 
					" (SELECT distinct(" + FINE_LAVORI.T_ID_ULTIM + ")" + 
				    " FROM " + AGGIUDICAZIONI.TABLE_NAME + " join " + FINE_LAVORI.TABLE_NAME +
			        " on " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + FINE_LAVORI.T_ID_AGGIUDICAZIONE + 
				    " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
			        FINE_LAVORI.ID_ULTIM + " = " + tableId_id + ") "  
				: "") + 
    
        //per ritardo
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_RITARDO.equals(scheda)				
				?  " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_RITARDO + "' THEN " + 
					" (SELECT distinct(" + R129.T_ID_RECORD + ")" + 
				    " FROM " + AGGIUDICAZIONI.TABLE_NAME + " join " + R129.TABLE_NAME +
			        " on " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + R129.T_ID_AGGIUDICAZIONE + 
				    " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
			        R129.ID_RECORD + " = " + tableId_id + ") " 
				: "") + 
     
        //per sospensioni
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_SOSPENSIONE.equals(scheda)				
		        ? " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_SOSPENSIONE + "' THEN " + 
					" (SELECT distinct(" + SOSPENSIONI.T_ID_SOSPENSIONE + ")" + 
				    " FROM " + AGGIUDICAZIONI.TABLE_NAME + " join " + SOSPENSIONI.TABLE_NAME +
			        " on " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + SOSPENSIONI.T_ID_AGGIUDICAZIONE +
				    " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
			        SOSPENSIONI.ID_SOSPENSIONE + " = " + tableId_id + ") "  
				: "") +				
     
        //per subappalti
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_SUBAPPALTO.equals(scheda)				
		       ? " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_SUBAPPALTO + "' THEN " + 
					" (SELECT distinct(" + SUBAPPALTI.T_ID_RECORD + ")" + 
				    " FROM " + AGGIUDICAZIONI.TABLE_NAME + " join " + SUBAPPALTI.TABLE_NAME +
			        " on " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + SUBAPPALTI.T_ID_AGGIUDICAZIONE + 
				    " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
			        SUBAPPALTI.ID_RECORD + " = " + tableId_id + ") " 
			   : "") +				
    
        //per varianti
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_VARIANTE.equals(scheda)				
		       ? " WHEN " + bloccoDati + " = '" + IdentificativoSchede.TAB_VARIANTE + "' THEN " + 
					" (SELECT distinct(" + VARIANTI.T_ID_VARIANTE + ")" + 
				    " FROM " + AGGIUDICAZIONI.TABLE_NAME + " join " + VARIANTI.TABLE_NAME +
			        " on " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + VARIANTI.T_ID_AGGIUDICAZIONE + 
				    " WHERE " + AGGIUDICAZIONI.T_ID_INFO + " = $4 AND  " +
			        VARIANTI.ID_VARIANTE + " = " + tableId_id + ") "  
					   : "") +				
     
		"ELSE '0' END) ";		
		logger.debug(idInfo);
		query = query.replace("$4", "'"+idInfo+"'");
		logger.debug(query);
		return query;
	}

	private String getQueryCigNew(String scheda){
		
		GenericUtilValidator val = new GenericUtilValidator(this.activeConnection, logger);
		String bloccoDati = "blocco_dati";
		
		String query =
			" ,(case " +
			
			//per infocomuni
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_INFO_COMUNI.equals(scheda)				
				?	" when " + bloccoDati + " = '" + IdentificativoSchede.TAB_INFO_COMUNI + "' then "
						+ " (select distinct(" + LOTTO.T_CIG + " + " +  LOTTO.T_CIG_KKK + ")"
					    + " from " + INFO_AGGIUDICAZIONI.TABLE_NAME + " join " + LOTTO.TABLE_NAME
					    + " on " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO
					    + " where " + INFO_AGGIUDICAZIONI.T_ID_INFO + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)"
				     + ")" 
			    : "") +				
			
			//per aggiudicazioni					
				(val.isEmpty(scheda) ||
						IdentificativoSchede.TAB_AGGIUDICAZIONE.equals(scheda)
						 || IdentificativoSchede.TAB_SOTTOSOGLIA.equals(scheda)
						 || IdentificativoSchede.TAB_ADESIONE.equals(scheda)
						 || IdentificativoSchede.TAB_ESCLUSI.equals(scheda)
					? " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_AGGIUDICAZIONE
						+ "' or " + bloccoDati + " = '" + IdentificativoSchede.TAB_SOTTOSOGLIA
						+ "' or " + bloccoDati + " = '" + IdentificativoSchede.TAB_ADESIONE
						+ "' or " + bloccoDati + " = '" + IdentificativoSchede.TAB_ESCLUSI 	+ "' then "
						+ " (select distinct( substring(" + AGGIUDICAZIONI.T_CUI + ",3,20) + '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
			                  + " from " + AGGIUDICAZIONI.TABLE_NAME 
			                  + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ",0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14))"
	  			    : "") +				
            
            //per inizio lavori
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_INIZIO_LAVORI.equals(scheda)				
					? " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_INIZIO_LAVORI + "' then "
					  + " (select distinct( substring( + " + AGGIUDICAZIONI.T_CUI + ",3,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
				         + " from " + AGGIUDICAZIONI.TABLE_NAME
				         + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
				           + " = (select distinct (" + INIZIO_LAVORI.T_ID_AGGIUDICAZIONE + ")"
				           + " from " + INIZIO_LAVORI.TABLE_NAME
				           + " where " + INIZIO_LAVORI.T_ID_INIZIO + " =  SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"
	  			    : "") +				
		   
		    //per stipula
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_STIPULA.equals(scheda)				
				? " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_STIPULA + "' then "
				  + " (select distinct( substring( + " + AGGIUDICAZIONI.T_CUI + ",3,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
			         + " from " + AGGIUDICAZIONI.TABLE_NAME
			         + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
			           + " = (select distinct (" + STIPULA.T_ID_AGGIUDICAZIONE + ")"
			           + " from " + STIPULA.TABLE_NAME
			           + " where " + STIPULA.T_ID_STIPULA + " =  SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"
  			    : "") +				
				                       
		    //per avanzamento     
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_AVANZAMENTO.equals(scheda)				
			    ? " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_AVANZAMENTO + "' then"
				  + " (select distinct( substring(" + AGGIUDICAZIONI.T_CUI + ",3,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
			        + " from " + AGGIUDICAZIONI.TABLE_NAME
			        + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
			        + " = (select distinct (" + STATI_AVANZ.ID_AGGIUDICAZIONE + ")"
			          + " from " + STATI_AVANZ.TABLE_NAME
		              + " where " + STATI_AVANZ.T_ID_AVANZAMENTO + " =  SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"
  			    : "") +				
	        
	        //per accordo
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_ACCORDO.equals(scheda)				
		  		  ? " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_ACCORDO + "' then"
		    	  + " (select distinct( substring(" + AGGIUDICAZIONI.T_CUI + ",3,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
			          + " from " + AGGIUDICAZIONI.TABLE_NAME
		              + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
		              + " = (select distinct (" + ACCORDI.T_ID_AGGIUDICAZIONE + ")"
		                + " from " + ACCORDI.TABLE_NAME
		                + " where " + ACCORDI.T_ID_ACCORDO + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"
  			    : "") +				
	       
	        //per collaudo
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_COLLAUDO.equals(scheda)				
		        ? " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_COLLAUDO + "' then"
			    + " (select distinct( substring(" + AGGIUDICAZIONI.T_CUI + ",3,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
		            + " from " + AGGIUDICAZIONI.TABLE_NAME
			        + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
		            + " = (select distinct (" + COLLAUDO.T_ID_AGGIUDICAZIONE + ")"
	                  + " from " + COLLAUDO.TABLE_NAME
		              + " where " + COLLAUDO.T_ID_COLLAUDO + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"				
			    : "") +				
	        
	         //per fine lavori
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_FINELAVORI.equals(scheda)				
			     ? " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_FINELAVORI + "' then"
				   + " (select distinct( substring(" + AGGIUDICAZIONI.T_CUI + ",3,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
				       + " from " + AGGIUDICAZIONI.TABLE_NAME
				       + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
		               + " = (select distinct (" + FINE_LAVORI.T_ID_AGGIUDICAZIONE + ")"
				         + " from " + FINE_LAVORI.TABLE_NAME
				         + " where " + FINE_LAVORI.T_ID_ULTIM + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"
			    : "") +				
			
			//per ritardi		
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_RITARDO.equals(scheda)				
				? " when " + bloccoDati +  " = '" + IdentificativoSchede.TAB_RITARDO + "' then"
				  + " (select distinct( substring(" + AGGIUDICAZIONI.T_CUI + ",3,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
			          + " from " + AGGIUDICAZIONI.TABLE_NAME
		              + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
		              + " = (select distinct (" + R129.T_ID_AGGIUDICAZIONE + ")"
		                + " from " + R129.TABLE_NAME
			            + " where " + R129.T_ID_RECORD + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"
			    : "") +				
		    
		    //per sospensioni        
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_SOSPENSIONE.equals(scheda)				
			    ? " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_SOSPENSIONE + "' then"
				  + " (select distinct( substring(" + AGGIUDICAZIONI.T_CUI + ",3,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
				      + " from " + AGGIUDICAZIONI.TABLE_NAME
				      + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
		              + " = (select distinct (" + SOSPENSIONI.T_ID_AGGIUDICAZIONE + ")"
			            + " from " + SOSPENSIONI.TABLE_NAME
		                + " where " + SOSPENSIONI.T_ID_SOSPENSIONE + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"
			    : "") +				
	      
	       //per subappalti           
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_SUBAPPALTO.equals(scheda)				
		       ? " when " + bloccoDati + " = '" + IdentificativoSchede.TAB_SUBAPPALTO + "' then"
				    + " (select distinct( substring(" + AGGIUDICAZIONI.T_CUI + ",3,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
				       + " from " + AGGIUDICAZIONI.TABLE_NAME
				       + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
		               + " = (select distinct (" + SUBAPPALTI.T_ID_AGGIUDICAZIONE + ")"
	                     + " from " + SUBAPPALTI.TABLE_NAME
		                 + " where " + SUBAPPALTI.T_ID_RECORD + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"	
 			    : "") +				
	      
	       //per varianti          
			(val.isEmpty(scheda) || IdentificativoSchede.TAB_VARIANTE.equals(scheda)				
		       ? " when " + bloccoDati +  "= '" + IdentificativoSchede.TAB_VARIANTE + "' then"
		         + " (select distinct( substring(" + AGGIUDICAZIONI.T_CUI + ",3,20) +  '-' + ltrim(str(" + AGGIUDICAZIONI.T_PROG_CUI + ")))"
		             + " from " + AGGIUDICAZIONI.TABLE_NAME
			         + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE
	                 + " = (select distinct (" + VARIANTI.T_ID_AGGIUDICAZIONE + ")"
	                   + " from " + VARIANTI.TABLE_NAME
		               + " where " + VARIANTI.T_ID_VARIANTE + " = SUBSTRING(" + LOG_OPERAZIONI.T_ID_RECORD + ", 0,LEN(" + LOG_OPERAZIONI.T_ID_RECORD + ") - 14)))"	
 			    : "") +				
		       
	       " else '*** N/D ***'"
		   + " end) as CIGCUI ";  
		  
		return query;
	}

	
	/**********************************************************************************************
	 * Inserisce un log di pubblicazione avviso
	 * 
	 * @param cfUtente String 
	 * @param bloccoDati String 
	 * @param attributiChiave List&lt;Object&gt;
	 * @param isRettifica
	 * 
	 */
	private void logPubbAvviso(String cfUtente, String bloccoDati, List<Object> attributiChiave, boolean isRettifica){
		try {
			
			log(cfUtente,bloccoDati,getNow(),getIdRecord(attributiChiave),isRettifica ? tipiOp.PUBBRETTAVVISO.getDescrizione() : tipiOp.PUBBAVVISO.getDescrizione());
		} catch (SQLException e) {
			logger.fatal(e.getMessage());
			//e.printStackTrace();
		}
	}
	
	/*********************************************************************************************
	 * Inserisce un log di avviso o rettifica avviso
	 * 
	 * @param conn Connection
	 * @param logger Logger
	 * @param cfUtente String 
	 * @param bloccoDati String 
	 * @param attributiChiave List&lt;Object&gt;
	 * @param isRettifica
	 */
	public static void loggingAVVISO(Connection conn, Logger logger, String cfUtente, 
			String bloccoDati, List<Object> attributiChiave, boolean isRettifica){
		new LogBloccoDatiManager(conn, logger).logPubbAvviso(cfUtente, bloccoDati, attributiChiave, isRettifica);
	}

   /**********************************************************************************************
    * Inserisce un log di presa in carico
    * 
    * @param cfUtente String 
    * @param bloccoDati String
    * @param attributiChiave List&lt;Object&gt;
    */
   private void logPresaCarico(String cfUtente, String bloccoDati,  List<Object> attributiChiave){
      try {
         log(cfUtente,bloccoDati,getNow(),getIdRecord(attributiChiave),tipiOp.PRESACAR.getDescrizione());
      } catch (SQLException e) {
         logger.fatal(e.getMessage());
         //e.printStackTrace();
      }
   }
	

	/*********************************************************************************************
    * Inserisce un log di presa in carico
    * 
    * @param conn Connection
    * @param logger Logger
    * @param cfUtente String 
    * @param bloccoDati String 
    * @param attributiChiave List&lt;Object&gt;

    */
   public static void loggingPRESACAR(Connection conn, Logger logger, String cfUtente, 
         String bloccoDati, List<Object> attributiChiave){
      new LogBloccoDatiManager(conn, logger).logPresaCarico(cfUtente, bloccoDati, attributiChiave);
   }
}
