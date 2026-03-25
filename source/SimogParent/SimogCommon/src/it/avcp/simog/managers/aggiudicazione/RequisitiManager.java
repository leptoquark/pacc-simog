package it.avcp.simog.managers.aggiudicazione;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.RequisitiBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.CATEGORIA;
import it.avlp.simog.db.generated.CLASSI_IMPORTO;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.REL_LOTTO_CATEGORIA_SCORPORABILE;
import it.avlp.simog.db.generated.REQUISITI;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Classe che si occupa della gestione dei dati relativi ai requisiti
 *
 */
public class RequisitiManager extends AccessiDB implements IAnnullamentoMulti{
	public static String CLAZZ = "RequisitiManager";
	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public RequisitiManager(Connection currentActiveConnection, Logger logger) {		
		super(currentActiveConnection, logger);		
	}
	private static String QUERY_CLASSI_IMPORTO = 
		"SELECT "+CLASSI_IMPORTO.T_ID_CODICE+", "
		      + (SimogFlags.is3028_NRFDBDT04Active() ? CLASSI_IMPORTO.TITOLO : CLASSI_IMPORTO.ID_CODICE)
				  +"+' ('+convert(varchar,"+CLASSI_IMPORTO.IMPORTO_DA+")+' - '+convert(varchar,"+CLASSI_IMPORTO.IMPORTO_A+")+')' " +
				  		" AS " +CLASSI_IMPORTO.IMPORTO_DA+
		" FROM "+CLASSI_IMPORTO.TABLE_NAME
		+ " WHERE " + buildISNULL(CLASSI_IMPORTO.DATA_FINE_VALIDITA,"99999999") + " >= ? "
		+ " ORDER BY "  + buildISNULL(CLASSI_IMPORTO.DATA_FINE_VALIDITA,"99999999");
	/* SI SUPPONE CHE CATEGORIA CONTENGA VALORI */
	
	
	
	/*
	private static String QUERY_CATEGORIE = 
		"SELECT DISTINCT  "+CATEGORIA.T_ID_CATEGORIA+","  +CATEGORIA.T_DESCRIZIONE
		+ " FROM "
		+ CATEGORIA.TABLE_NAME+" CROSS JOIN "
		+ LOTTO.TABLE_NAME+" LEFT OUTER JOIN "
		+ REL_LOTTO_CATEGORIA_SCORPORABILE.TABLE_NAME 
		+ " ON "+CATEGORIA.T_ID_CATEGORIA+" = "+REL_LOTTO_CATEGORIA_SCORPORABILE.T_ID_CATEGORIA
		+ " WHERE " + buildISNULL(CATEGORIA.DATA_FINE_VALIDITA,"99999999") + " >= ? "
		+ " AND "+LOTTO.T_ID_LOTTO+" = ? "
		+ " AND("+CATEGORIA.T_ID_CATEGORIA+" = "+LOTTO.T_ID_CATEGORIA_PREVALENTE
		+ " OR ("+LOTTO.T_ID_LOTTO+" = "+REL_LOTTO_CATEGORIA_SCORPORABILE.T_ID_LOTTO+"))"
			;
	*/
	
// PP non usata
//	private static String QUERY_CATEGORIE = 
//		//Seleziono le categorie scorporabili
//	"Select " + CATEGORIA.T_ID_CATEGORIA+","  + CATEGORIA.T_DESCRIZIONE
//	+ " FROM "
//	+ CATEGORIA.TABLE_NAME + ", " + REL_LOTTO_CATEGORIA_SCORPORABILE.TABLE_NAME 
//	+ " WHERE " 
//	+ buildISNULL(CATEGORIA.DATA_FINE_VALIDITA,"99999999") + " >= ? AND "
//	+ REL_LOTTO_CATEGORIA_SCORPORABILE.T_ID_LOTTO + " = ? AND "
//	+ REL_LOTTO_CATEGORIA_SCORPORABILE.T_ID_CATEGORIA + " = " + CATEGORIA.T_ID_CATEGORIA
//	+ " UNION "
//		//Seleziono la categoria prevalente
//	+ " Select " + CATEGORIA.T_ID_CATEGORIA + "," + CATEGORIA.T_DESCRIZIONE
//	+ " FROM "
//	+ CATEGORIA.TABLE_NAME + ", " + LOTTO.TABLE_NAME 
//	+ " WHERE " 
//	+ buildISNULL(CATEGORIA.DATA_FINE_VALIDITA,"99999999") + " >= ? AND "
//	+ LOTTO.T_ID_LOTTO + " = ? AND "
//	+ CATEGORIA.T_ID_CATEGORIA + " = " + LOTTO.T_ID_CATEGORIA_PREVALENTE;
	
	private static String QUERY_CATEGORIE_LAVORI = 
		"Select " + CATEGORIA.T_ID_CATEGORIA+","  + CATEGORIA.T_DESCRIZIONE
		+ " FROM "
		+ CATEGORIA.TABLE_NAME 
		+ " WHERE " 
		+ buildISNULL(CATEGORIA.DATA_FINE_VALIDITA,"99999999") + " >= ? ";
// 		MOD: SIMOG-27 UN 09-04-09 disattivato filtro su ID_CATEGORIA per FB e FS
//		+ " AND " + CATEGORIA.T_ID_CATEGORIA + " <> '"+FORNITURE_BENI+"' AND " + CATEGORIA.T_ID_CATEGORIA + " <> '"+FORNITURE_SERVIZI+"'";
	
	
	private static String QUERY_SELECT_REQUISITI = 
		"SELECT " +REQUISITI.TABLE_NAME+".*, "
			+ CATEGORIA.DESCRIZIONE+", "
			+ (SimogFlags.is3028_NRFDBDT04Active() ? CLASSI_IMPORTO.TITOLO : CLASSI_IMPORTO.ID_CODICE)
			+"+' ('+convert(varchar,"+CLASSI_IMPORTO.IMPORTO_DA+")+' - '+convert(varchar,"+CLASSI_IMPORTO.IMPORTO_A+")+')' " +
			  		" AS "+CLASSI_IMPORTO.TABLE_NAME+CLASSI_IMPORTO.IMPORTO_DA
		+" FROM " + REQUISITI.TABLE_NAME+", "+CATEGORIA.TABLE_NAME+ ", "+CLASSI_IMPORTO.TABLE_NAME
	+ " WHERE " + REQUISITI.ID_AGGIUDICAZIONE + " = ? AND " 
	+ REQUISITI.DATA_INIZIO_AGGIUDICAZIONE + " = ? AND "
	+ REQUISITI.T_ID_CATEGORIA+" = "+CATEGORIA.T_ID_CATEGORIA+" AND "
	// PP 31.08.2016 non restituisce tutte le informazioni presenti + buildISNULL(CATEGORIA.T_DATA_FINE_VALIDITA,"99999999") + " >= ? AND "
	+ REQUISITI.T_CLASSE_IMPORTO+" = "+CLASSI_IMPORTO.T_ID_CODICE;
	
	private final String WHERE_STATO = " AND (" + REQUISITI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + REQUISITI.T_ID_STATO + " = " + StatiScheda.CONFERMATO+")";
	
	
	
	
	
	private static String ELIMINA_REQUISITI = "DELETE FROM " + REQUISITI.TABLE_NAME 
	+ " WHERE " + REQUISITI.ID_AGGIUDICAZIONE + " = ? AND " + REQUISITI.DATA_INIZIO_AGGIUDICAZIONE +  " = ? ";
//	+ "AND " + REQUISITI.DATA_FINE_REQ + " IS NULL ";
	
	/**Elimina tutti i requisiti con dataFine= null e stato = in definizione
	 * 
	 */
	/** Elimina tutti i requisiti con stato "in definizione"
	 * 
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws SQLException
	 */
	public void deleteRequisiti(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException {
		PreparedStatement stmt = null;
		try{
			stmt = activeConnection.prepareStatement(ELIMINA_REQUISITI);
			stmt.setLong(1, idAggiudicazione);
			stmt.setTimestamp(2, dataInizioAggiudicazione);
			stmt.execute();
		}finally{
			close(null, stmt);
		}
	}
	
	
	/**
	 * Carica tutti i requisiti associati a una aggiudicazione
	 * 
	 * @param idAggiudicazione  long id dell aggiudicazione
	 * @param dataInizioAggiudicazione  Timestamp data inizio aggiudicazione
	 * @param ignoraStato TODO
	 * @return TableBean - la lista dei requisiti associati alla aggiudicazione
	 * @throws SQLException
	 */
	public List<RequisitiBean> loadMany(long idAggiudicazione, Timestamp dataInizioAggiudicazione, boolean ignoraStato) throws SQLException{
		
		PreparedStatement stmt = null;
		ArrayList<RequisitiBean> reqList = new ArrayList<RequisitiBean>();
		RequisitiBean requisito = null;
		ResultSet rs = null;
		int index = 1;
		try{
			//logger.debug("[1����1] - ("+dataInizioAggiudicazione+") - " + QUERY_SELECT_REQUISITI);
			
			String qry = QUERY_SELECT_REQUISITI;
			if(!ignoraStato)
				qry += WHERE_STATO;
			
			stmt = activeConnection.prepareStatement(qry);
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++,dataInizioAggiudicazione);
			// PP 31.08.2016 stmt.setString(index++,PageHelper.getDBDateFromTS(dataInizioAggiudicazione));
		
			rs = stmt.executeQuery();
			
			while(rs.next()){
				requisito = new RequisitiBean();
				
				requisito.setIdRequisito(rs.getLong(REQUISITI.ID_REQUISITO));
				requisito.setDataInizioRequisito(rs.getTimestamp(REQUISITI.DATA_INIZIO_REQ));
				requisito.setIdCategoria(rs.getString(CATEGORIA.ID_CATEGORIA));
				requisito.setClasseImporto(rs.getString(REQUISITI.CLASSE_IMPORTO));
				
				if(rs.getString(REQUISITI.PREVALENTE) != null)
					requisito.setPrevalente(rs.getString(REQUISITI.PREVALENTE));
				else requisito.setPrevalente("");
				if(rs.getString(REQUISITI.SCORPORABILE) != null)
					requisito.setScorporabile(rs.getString(REQUISITI.SCORPORABILE));
				else requisito.setScorporabile("");
				if(rs.getString(REQUISITI.SUBAPPALTABILE) != null)
					requisito.setSubAppaltabile(rs.getString(REQUISITI.SUBAPPALTABILE));
				else requisito.setSubAppaltabile("");
				
				requisito.setIdStato(rs.getInt(REQUISITI.ID_STATO));
				requisito.setIdAggiudicazione(rs.getLong(REQUISITI.ID_AGGIUDICAZIONE));
				requisito.setDataInizioAggiudicazione(rs.getTimestamp(REQUISITI.DATA_INIZIO_AGGIUDICAZIONE));
				requisito.setImportoDa(rs.getString(CLASSI_IMPORTO.TABLE_NAME + CLASSI_IMPORTO.IMPORTO_DA));
				requisito.setDescCategoria(rs.getString(CATEGORIA.DESCRIZIONE));
				
				reqList.add(requisito);
			}
			return reqList;
		}finally{
			close(rs, stmt);
			
		}
	}
	
	/**
	 * metodo per la conferma dei requisiti
	 * 
	 * @param requisiti RequisitiBean
	 * @throws SQLException
	 */
	public void confirm(RequisitiBean requisiti) throws SQLException {
//		update(requisiti, true); MEV 34181 3.04.8.1
	}
	
	/**
	 * metodo per l'aggiornamento dei requisiti
	 * 
	 * @param requisiti RequisitiBean
	 * @throws SQLException
	 */
	public void save(RequisitiBean requisiti) throws SQLException {
//		update(requisiti, false); MEV 34181 3.04.8.1
	}	
	
	private static String QUERY_UPDATE_REQUISITO ="INSERT INTO " + REQUISITI.TABLE_NAME + "( "
	+ REQUISITI.DATA_INIZIO_REQ + ", "
	+ REQUISITI.ID_CATEGORIA + ", "
	+ REQUISITI.CLASSE_IMPORTO + ", " 
	+ REQUISITI.PREVALENTE + ", "
	+ REQUISITI.SCORPORABILE + ", "
	+ REQUISITI.SUBAPPALTABILE + ", "
	+ REQUISITI.ID_STATO + ", "
	+ REQUISITI.DATA_FINE_REQ + ", "
	+ REQUISITI.ID_AGGIUDICAZIONE + ", "
	+ REQUISITI.DATA_INIZIO_AGGIUDICAZIONE 
	
	+ " ) VALUES ("
	+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	/**
	 * param requisitiBean
	 * param conferma
	 * throws SQLException
	 */
	private void update(RequisitiBean requisitiBean, boolean conferma) throws SQLException{
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";		
		PreparedStatement stmt = activeConnection.prepareStatement(createInsertQuery(QUERY_UPDATE_REQUISITO,REQUISITI.ID_REQUISITO));		
		int index = 1;	
		//data inizio requisito
		try{
			if(requisitiBean.getDataInizioRequisito() == null){ requisitiBean.setDataInizioRequisito(getNow()); }		
			stmt.setTimestamp(index++, requisitiBean.getDataInizioRequisito());
			//MEV 34181 - 3.04.8.1 fase 2 sono stati oscurati i seguenti campi - DA TESTARE
//			stmt.setString(index++, requisitiBean.getIdCategoria());	
//			stmt.setString(index++, requisitiBean.getClasseImporto());	
//			stmt.setString(index++, requisitiBean.getPrevalente());	
//			stmt.setString(index++, requisitiBean.getScorporabile());	
//			stmt.setString(index++, requisitiBean.getSubAppaltabile());	
			// STATO E DATA_FINE
			if (conferma){
				stmt.setLong(index++, StatiScheda.CONFERMATO);
				stmt.setTimestamp(index++, getNow());
				requisitiBean.setIdStato(StatiScheda.CONFERMATO);
			}else{
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
				stmt.setNull(index++, Types.TIMESTAMP);
				requisitiBean.setIdStato(StatiScheda.IN_DEFINIZIONE);
			}	
			stmt.setLong(index++, requisitiBean.getIdAggiudicazione());				
			stmt.setTimestamp(index++, requisitiBean.getDataInizioAggiudicazione());						
			stmt.execute();								
		}finally{
			close(null, stmt);			
		}
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(RequisitiBean.class, requisitiBean));		
	}
	
	private final String QUERY_DELETE_REQUISITI = 
		"DELETE FROM "+REQUISITI.TABLE_NAME+
		" WHERE " + REQUISITI.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+REQUISITI.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	/**
	 * metodo per la cancellazione di un record
	 * 
	 * @param idRecord String
	 * @param dataInizioRecord Timestamp
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int deleteRecord(String idRecord, Timestamp dataInizioRecord) throws SQLException{
		
		int numRow=-1;
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_DELETE_REQUISITI);
			logger.debug("query per la delete record attivo INCARICATI: "+QUERY_DELETE_REQUISITI);
			int index = 1;
			stmt.setInt(index++, Integer.parseInt(idRecord));
			
			stmt.setObject(index++,dataInizioRecord);
			
			numRow = stmt.executeUpdate();
			 
		} finally {
			close(null, stmt);
		}
		return numRow;
	}
	
	private final String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_REQUISITI = 
		"UPDATE "+REQUISITI.TABLE_NAME+
		" SET " + REQUISITI.ID_STATO + " = ?,"+ 
		REQUISITI.DATA_FINE_REQ + " = " + buildGetDate() +		
		" WHERE "+REQUISITI.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+REQUISITI.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
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
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_REQUISITI);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_REQUISITI);
			int index = 1;
			stmt.setObject(index++, stato_scheda);
			
			stmt.setInt(index++, Integer.parseInt(idRecord));
			
			stmt.setObject(index++,dataInizioRecord);

			numRow = stmt.executeUpdate();
			//PPactiveConnection.commit(currentActiveConnection);
		} finally {
			close(null, stmt);
		}
		return numRow;
	}
	private final String qUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_REQUISITI_NEWRECORD = 
		"UPDATE "+REQUISITI.TABLE_NAME+
		" SET " + REQUISITI.ID_STATO + " = ?,"+ //+STATI_SCHEDA.CONFERMATO+
		REQUISITI.DATA_INIZIO_AGGIUDICAZIONE + " = ?"+
		" WHERE "+REQUISITI.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+REQUISITI.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	public int updateRecordToPointToNew(String idAggiudicazione, Timestamp dataInizioAggOld,Timestamp dataInizioAggNew, String stato_scheda ) throws SQLException{
		int numRow = -1; 
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(qUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_REQUISITI_NEWRECORD);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+qUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_REQUISITI_NEWRECORD);
			int index = 1;
			stmt.setObject(index++, stato_scheda);
			
			stmt.setObject(index++,dataInizioAggNew);
			
			stmt.setInt(index++, Integer.parseInt(idAggiudicazione));
			
			stmt.setObject(index++,dataInizioAggOld);

			numRow = stmt.executeUpdate();
			//PPactiveConnection.commit(currentActiveConnection);
		} finally {
			close(null, stmt);
		}
		return numRow;
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
			"UPDATE "+REQUISITI.TABLE_NAME+ " SET "
			+ REQUISITI.ID_STATO+ " = ?, "
			+ REQUISITI.DATA_INIZIO_AGGIUDICAZIONE+ " = ? "
			
			+" WHERE "
			+REQUISITI.T_ID_AGGIUDICAZIONE+" = ?"
			+" AND "+REQUISITI.T_DATA_INIZIO_AGGIUDICAZIONE+" = ?"
			+" AND "+REQUISITI.T_ID_STATO + "=" + StatiScheda.CONFERMATO;
		
		String QUERY_COPY_RECORD =
			"INSERT INTO "+REQUISITI.TABLE_NAME+" ("
			+REQUISITI.ID_REQUISITO	
			
			+","+REQUISITI.ID_CATEGORIA
			+","+REQUISITI.CLASSE_IMPORTO
			+","+REQUISITI.PREVALENTE
			+","+REQUISITI.SCORPORABILE
			+","+ REQUISITI.SUBAPPALTABILE 
			+","+REQUISITI.ID_AGGIUDICAZIONE
			+","+REQUISITI.DATA_INIZIO_AGGIUDICAZIONE
			
			+","+REQUISITI.DATA_INIZIO_REQ
			+","+REQUISITI.DATA_FINE_REQ
			+","+REQUISITI.ID_STATO+" ) "
			+"SELECT "
			+REQUISITI.ID_REQUISITO	
			+","+REQUISITI.ID_CATEGORIA
			+","+REQUISITI.CLASSE_IMPORTO
			+","+REQUISITI.PREVALENTE
			+","+REQUISITI.SCORPORABILE
			+","+ REQUISITI.SUBAPPALTABILE
			+","+REQUISITI.ID_AGGIUDICAZIONE
			+","+REQUISITI.DATA_INIZIO_AGGIUDICAZIONE
			+", ?"
			+", ?"
			+", ?"
			+" FROM "+REQUISITI.TABLE_NAME
			+" WHERE "
			+REQUISITI.ID_AGGIUDICAZIONE+" = ? AND "
			+REQUISITI.DATA_INIZIO_AGGIUDICAZIONE+" = ?"
			+" AND "+REQUISITI.ID_STATO+" = "+StatiScheda.CONFERMATO;
		
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try{
			int index = 1;
			stmt = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD,REQUISITI.TABLE_NAME));
			stmt.setTimestamp(index++, getNow()); //data_inizio_incaricato
			stmt.setNull(index++, Types.TIMESTAMP); // data_fine_incaricato
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
				logger.debug("RESPONSABILE_MANAGER.copyRecord: Nessun record da copiare");
				return true;
			}

				
		
		}
		finally{
			close(null, stmt2);
			close(null, stmt);
		}
	}
	
	/**
	 * metodo per il recupero delle categorie relative al lotto di cui id
	 * 
	 * @param idlotto String
	 * @param o Object deve essere un Timestamp o una String yyyymmdd
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException
	 */
// PP non usata	
//	public Map<String,String> caricaCategorie(long idlotto,Object o) throws SQLException{
//		PreparedStatement  stmt = null;
//		ResultSet rs = null;
//		HashMap<String, String> categorie = new HashMap<String, String>();
//		try{
//			stmt = activeConnection.prepareStatement(QUERY_CATEGORIE);
//			//logger.debug("[idLotto:"+idlotto+" ]: getNow: "+PageHelper.getDBDateFromTS(getNow()));
//			stmt.setObject(1, PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()));
//			stmt.setLong(2, idlotto);
//			stmt.setObject(3, PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()));
//			stmt.setLong(4, idlotto);
//			rs = stmt.executeQuery();
//			while(rs.next()){
//				categorie.put(rs.getString(CATEGORIA.ID_CATEGORIA), rs.getString(CATEGORIA.DESCRIZIONE));	
//			}
//			
//		}catch(Exception e){
//			logger.error(e.getMessage());
//			return null;
//		}finally{
//			close(rs, stmt);
//		}
//		
//		return categorie;
//	}
	
	/**
	 * Metodo che restituisce tutte le categorie dei lavori
	 * nato per poter visualizzare tutte le categorie lavori
	 * nella form di aggiudicazioni[requisiti->categorie]
	 * invece che solamente le categorie preselezionate in 
	 * fase di richiesta cig
	 * 
	 * @param o Timestamp o String yyyymmdd
	 * @return Map&lt;String,String&gt;
	 * @throws SQLException
	 */
	public Map<String,String> caricaCategorie(Object o) throws SQLException{
		//PreparedStatement  stmt = null;
		//ResultSet rs = null;
		//HashMap<String, String> categorie = new HashMap<String, String>();
//		try{
//			stmt = activeConnection.prepareStatement(QUERY_CATEGORIE_LAVORI);
//			logger.debug("[ query: "+QUERY_CATEGORIE_LAVORI+" ]: getNow: "+PageHelper.getDBDateFromTS(getNow()));
//			stmt.setObject(1, PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()));
//			rs = stmt.executeQuery();
//			while(rs.next()){
//				categorie.put(rs.getString(CATEGORIA.ID_CATEGORIA), rs.getString(CATEGORIA.DESCRIZIONE));	
//			}
//		}catch(Exception e){
//			logger.error(e.getMessage());
//			return null;
//		}finally{
//			close(rs, stmt);
//		}
		// TICKET ALM #7649
	   Map<String, String> categorie = getTipologicaNoFlag(CATEGORIA.TABLE_NAME, CATEGORIA.ID_CATEGORIA, CATEGORIA.DESCRIZIONE, CATEGORIA.DATA_FINE_VALIDITA,o);

	   return categorie;		
	}
	
	/**
	 * metodo per il caricameto delle classi importo (display use)
	 * @param o Object deve essere un Timestamp o una String yyyymmdd
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException
	 */
	public Map<String,String> caricaClassiImporto(Object o) throws SQLException{
		
		// TICKET ALM #7649
		/*PreparedStatement stmt = null;
		ResultSet rs = null;
		HashMap<String, String> classi = new HashMap<String, String>();
		try{
			stmt = activeConnection.prepareStatement(QUERY_CLASSI_IMPORTO);
			
			if(!SimogFlags.isFlagNoDate())
			   stmt.setObject(1, PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()));
			else 
			   stmt.setObject(1, "00000000");
			   
			rs = stmt.executeQuery();
			while(rs.next()){
				classi.put(rs.getString(CLASSI_IMPORTO.ID_CODICE), rs.getString(CLASSI_IMPORTO.IMPORTO_DA));	
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}finally{
			close(rs, stmt);
		}
		
		
		return classi;*/

		return getTipologicaNoFlag(CLASSI_IMPORTO.TABLE_NAME, CLASSI_IMPORTO.ID_CODICE, CLASSI_IMPORTO.TITOLO, CLASSI_IMPORTO.DATA_FINE_VALIDITA, o);
	}


	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti#annulla(long, java.sql.Timestamp)
	 */
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
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_REQUISITI);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataInizioAggiudicazione);
			someRowAffected = stmt.executeUpdate() > 0 ;
			return someRowAffected;
		}finally {
			close(null,stmt);
		}		
	}
}
