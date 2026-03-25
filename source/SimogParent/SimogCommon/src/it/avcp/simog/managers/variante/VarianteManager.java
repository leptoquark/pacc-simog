package it.avcp.simog.managers.variante;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamento;
import it.avcp.simog.managers.comportamento.caricamento.ILoadVariante;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.common.servlet.ParametriServletVariante;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.MOTIVI_VARIANTE;
import it.avlp.simog.db.generated.MOTIVO_REVISIONE_PREZZI;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.db.generated.SUBAPPALTI;
import it.avlp.simog.db.generated.VARIANTE_CATEGORIA;
import it.avlp.simog.db.generated.VARIANTI;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
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
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class VarianteManager extends AccessiDB implements IAnnullamento,ILoadVariante{

	public static String CLAZZ = "VarianteManager";
	
	/*********************************************
	 *            COSTRUTTORE            
	 *********************************************/
	/**
	 * @param currentActiveConnection
	 * @param logger
	 */
	public VarianteManager(Connection currentActiveConnection,Logger logger){
		super(currentActiveConnection, logger);
	}
	/**********************************************
	 *        STRINGA PER LA INSERT
	 **********************************************/
	public final String INSERT_VARIANTE = "INSERT INTO " + VARIANTI.TABLE_NAME + "("
	+VARIANTI.ALTRE_MOTIVAZIONI+","
	+VARIANTI.DATA_ATTO_AGGIUNTIVO+","
	+VARIANTI.DATA_FINE_VAR+","
	+VARIANTI.DATA_INIZIO_AGGIUDICAZIONE+","
	+VARIANTI.DATA_INIZIO_VAR+","
	+VARIANTI.DATA_VERB_APPR+","
	+VARIANTI.ID_AGGIUDICAZIONE+","
	+VARIANTI.ID_STATO+","
	+VARIANTI.IMP_DISPOSIZIONE+","
	+VARIANTI.IMP_PROGETTAZIONE+","
	+VARIANTI.IMP_RIDET_FORNIT+","
	+VARIANTI.IMP_RIDET_LAVORI+","
	+VARIANTI.IMP_RIDET_SERVIZI+","
	+VARIANTI.IMP_SICUREZZA+","
	+VARIANTI.ULTERIORI_SOMME+","
	+VARIANTI.NUM_GIORNI_PROROGA+","
	+VARIANTI.ID_SCHEDA_LOCALE+","
	+VARIANTI.LINK_VARIANTI+"," //MEV 34191 3.04.8
	+VARIANTI.CIG_PROCEDURA+"," //TICKET ALM - 3.04.3 PT
	+VARIANTI.ID_MOTIVO_REV_PREZZI //MEV 34469 3.04.8
	+") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	//
	/**
	 * Metodo per l'inserimento di un oggetto variante
	 *  
	 * @param bean VarianteBean
	 * @param cfUtente
	 * @throws SQLException
	 */
	public void insert(VarianteBean bean, String cfUtente) throws SQLException{
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int index = 1 ; // indice utilizzato nel prepared statement
		try{
			pstmt = activeConnection.prepareStatement(createInsertQuery(INSERT_VARIANTE, VARIANTI.ID_VARIANTE));
			pstmt.setString(index++, bean.getAltreMotivazioni());
			pstmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataAttoAggiuntivo()));
			pstmt.setNull(index++, Types.TIMESTAMP);
			//pstmt.setTimestamp(index++, bean.getDataFineVar());
			pstmt.setTimestamp(index++, bean.getDataInizioAggiudicazione());
			
				bean.setDataInizioVar(getNow());
			pstmt.setTimestamp(index++, bean.getDataInizioVar());
			
			pstmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataVerbaleApprovazione()));
			pstmt.setLong(index++, bean.getIdAggiudicazione());
			
				bean.setIdStato((long)StatiScheda.IN_DEFINIZIONE);
			pstmt.setLong(index++, bean.getIdStato());
			
			pstmt.setBigDecimal(index++, bean.getImpDisposizione());
			pstmt.setBigDecimal(index++, bean.getImpProgettazione());
			pstmt.setBigDecimal(index++, bean.getImpRidetFornit());
			pstmt.setBigDecimal(index++, bean.getImpRidetLavori());
			pstmt.setBigDecimal(index++, bean.getImpRidetServizi());
			pstmt.setBigDecimal(index++, bean.getImpSicurezza());
			pstmt.setBigDecimal(index++, bean.getUlterioriSomme());
			pstmt.setInt(index++, bean.getNumGiorniProroga());
			if(bean.getIdLocale() == null){
				pstmt.setNull(index++, Types.VARCHAR);
			}else{
				pstmt.setString(index++, bean.getIdLocale());
			}
			
			//MEV 34191 3.04.8
			if(bean.getLinkVarianti() == null || "".equals(bean.getLinkVarianti())) {
				pstmt.setNull(index++, Types.VARCHAR);
			} else {
				pstmt.setString(index++,bean.getLinkVarianti());
			}
			//FINE MEV 34191 3.04.8
			
			
			
			//TICKET ALM - 3.04.3 PT
			if(bean.getCigProcedura() == null || "".equals(bean.getCigProcedura())) {
				pstmt.setNull(index++, Types.VARCHAR);
			} else {
				pstmt.setString(index++,bean.getCigProcedura());
			}
            //FINE TICKET ALM - 3.04.3 PT
			
			//MEV 34469 3.04.8
			if(bean.getIdMotivoRevPrezzi() == null){
				pstmt.setNull(index++, Types.BIGINT);
			}else{
				pstmt.setString(index++, bean.getIdMotivoRevPrezzi());
			}
			
			if(pstmt.execute()) {
				// Dopo l'inserimento vado a vedere quale id e' stato assegnato alla Variante e lo inserisco nel Bean
				rs = pstmt.getResultSet();
				rs.next();
				bean.setIdVariante(rs.getLong(VARIANTI.ID_VARIANTE));
				
				// Setto gli attributi chiave della Variante
				List<Object> attributiChiave = new ArrayList<Object>();
				
				attributiChiave.add(bean.getIdVariante());
				attributiChiave.add(bean.getDataInizioVar());
				LogBloccoDatiManager.loggingINSERT(activeConnection, logger, cfUtente, ParametriServletVariante.TAB_SCHEDA_VARIANTE , attributiChiave);				
			}
			//close(rs,pstmt);
		}
		catch(SQLException e) { 
			//e.printStackTrace();
		    throw e;
		}
		finally{
			close(rs,pstmt);
		}
	}
	
	/********************************************
	 *    SELECT PER PIU' VARIANTI 
	 ********************************************/
	private final String QUERY_SELECT_VARIANTI = 
	"SELECT "+
	VARIANTI.ID_VARIANTE + " ,"+
	VARIANTI.DATA_INIZIO_VAR + " ,"+
	VARIANTI.DATA_FINE_VAR + " ,"+
	VARIANTI.DATA_VERB_APPR + "," +
	VARIANTI.ID_AGGIUDICAZIONE + " ,"+
	VARIANTI.DATA_INIZIO_AGGIUDICAZIONE + " ,"+
	VARIANTI.IMP_RIDET_LAVORI + " ,"+
	VARIANTI.IMP_RIDET_SERVIZI + " ,"+
	VARIANTI.IMP_RIDET_FORNIT + " ,"+
	VARIANTI.IMP_SICUREZZA + " ,"+
	VARIANTI.IMP_PROGETTAZIONE + " ,"+
	VARIANTI.ULTERIORI_SOMME + " ,"+
	VARIANTI.CIG_PROCEDURA + " ,"+ //TICKET ALM - 3.04.3 PT
	VARIANTI.LINK_VARIANTI + " ,"+ //MEV 34191 3.04.8
	VARIANTI.IMP_DISPOSIZIONE + " ,"+
	VARIANTI.DATA_ATTO_AGGIUNTIVO + " ,"+
	VARIANTI.NUM_GIORNI_PROROGA + " ,"+
	VARIANTI.ALTRE_MOTIVAZIONI + " ,"+
	VARIANTI.ID_MOTIVO_REV_PREZZI + " ,"+ // MEV 34469 3.04.8
	VARIANTI.T_ID_STATO + ", " + STATI_SCHEDA.DESCRIZIONE + " + " +
	   buildRichAnnQuery(ParametriServletVariante.TAB_SCHEDA_VARIANTE, VARIANTI.T_ID_VARIANTE,null) 
	+" AS "+STATI_SCHEDA.DESCRIZIONE+
	", " + VARIANTI.ID_SCHEDA_LOCALE +
	" FROM " + 	VARIANTI.TABLE_NAME  + 
	", " + STATI_SCHEDA.TABLE_NAME
	+" WHERE " + 
	VARIANTI.ID_AGGIUDICAZIONE + "= ? AND " +
	VARIANTI.DATA_INIZIO_AGGIUDICAZIONE + "= ?" +
	" AND "   + VARIANTI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO +
	" AND (" + VARIANTI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE +
	" OR " + VARIANTI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+") order by " + VARIANTI.DATA_VERB_APPR;
	
	
	
	/**
	 * Viene caricata la singola variante identifica da un ID della variante e dalla data di inizio della 
	 * variante stessa 
	 * 
	 * @param idVariante Un long che identifica ID della variante 
	 * @param dataInizioVariante Un Timestamp per la data di inizio della variante
	 * @return VarianteBean 
	 * @throws SQLException
	 */
	public VarianteBean loadOne(long idVariante, Timestamp dataInizioVariante)throws SQLException{
		List<VarianteBean> lista = load(idVariante, dataInizioVariante, false);
		if(lista!= null && lista.size() > 0)
			return lista.get(0);
		else return null;
	}
	
	
	
	
	/*******************************************************************
	 *        QUERY PER LA SELEZIONE DI UNA SINGOLA VARIANTE
	 *******************************************************************/
	
	private final String SELECT_ONE_VARIANTE = 
		"SELECT "+
		VARIANTI.ID_VARIANTE + " ,"+
		VARIANTI.DATA_INIZIO_VAR + " ,"+
		VARIANTI.DATA_FINE_VAR + " ,"+
		VARIANTI.T_ID_STATO + " ,"+
		VARIANTI.ID_AGGIUDICAZIONE + " ,"+
		VARIANTI.DATA_INIZIO_AGGIUDICAZIONE + " ,"+
		VARIANTI.IMP_RIDET_LAVORI + " ,"+
		VARIANTI.IMP_RIDET_SERVIZI + " ,"+
		VARIANTI.IMP_RIDET_FORNIT + " ,"+
		VARIANTI.IMP_SICUREZZA + " ,"+
		VARIANTI.IMP_PROGETTAZIONE + " ,"+
		VARIANTI.ULTERIORI_SOMME + " ,"+
		VARIANTI.CIG_PROCEDURA + " ,"+ //TICKET ALM - 3.04.3 PT
		VARIANTI.LINK_VARIANTI + " ,"+ //MEV 34191 3.04.8
		VARIANTI.IMP_DISPOSIZIONE + " ,"+
		VARIANTI.DATA_ATTO_AGGIUNTIVO + " ,"+
		VARIANTI.NUM_GIORNI_PROROGA + " ,"+
		VARIANTI.ALTRE_MOTIVAZIONI + " ,"+
		VARIANTI.ID_MOTIVO_REV_PREZZI + " ,"+ // MEV 34469 3.04.8
		VARIANTI.DATA_VERB_APPR + " ,"+
		VARIANTI.T_ID_STATO + ", " + STATI_SCHEDA.DESCRIZIONE + " + " +
		   buildRichAnnQuery(ParametriServletVariante.TAB_SCHEDA_VARIANTE, VARIANTI.T_ID_VARIANTE,null) 
		+" AS "+STATI_SCHEDA.DESCRIZIONE+
		" ,"+VARIANTI.ID_SCHEDA_LOCALE+ 
		" FROM " + 	VARIANTI.TABLE_NAME  + 
		", " + STATI_SCHEDA.TABLE_NAME ;
//		" WHERE " + 
//		VARIANTI.ID_VARIANTE + "= ? AND " +
//		VARIANTI.DATA_INIZIO_VAR + "= ?" +
//		" AND "   + VARIANTI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO +
//		" AND (" + VARIANTI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE +
//		" OR " + VARIANTI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	
	/*************************************************************************
	 * Il metodo si occupa di restituire delle varianti, singolarmente o una lista a seconda del parametro buleano passatogli
	 * Se il parametro byAggiudicazione e' impostato a True vengono trovate tutte le varianti per l'aggiudicazione identificata tramite ID e data inizio 
	 * Se il parametro byAggiudicazione e' impostato a False viene recuperata una singola variante recuperata tramite ID della variante e data inizio  
	 * 
	 * param idAggiudicazione : e' un long che assume significati diversi a seconda del parametro by aggiudicazione.   
	 * param date : Time stamp per la data di inizio
	 * param byAggiudicazione : un booleano, se true indica che il parametro idAggiudicazione indica appunto l'ID 
	 * dell'aggiudicazione e data la data di inizio dell'aggiudicazione. Se a False indica che idAggiudicazione rappresenta l'ID
	 * di una Variante e data la data inizio della variante 
	 * 
	 * return Restituisce una lista di Varianti se byAggiudicazione risulta True, una singola variante se byAggiudicazione risulta False
	 * throws SQLException
	 *************************************************************************/
	private List<VarianteBean> load(long idAggiudicazione, Timestamp date, boolean byAggiudicazione) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		//TableBean result = null;
		ArrayList<VarianteBean> listaSchede = new ArrayList<VarianteBean>();
		VarianteBean bean = null;
		try{
			if(byAggiudicazione) 
				stmt = activeConnection.prepareStatement(QUERY_SELECT_VARIANTI);
			else 
				stmt = activeConnection.prepareStatement(SELECT_ONE_VARIANTE + WHERE_STANDARD);
			stmt.setLong(index++, idAggiudicazione );
			stmt.setTimestamp(index++, date);
			rs = stmt.executeQuery();
			while(rs.next()){
				bean = new VarianteBean();
				fillBean(rs, bean);				
				listaSchede.add(bean);
			}
			listaSchede.trimToSize();
			return listaSchede;
		}finally{
			close(rs,stmt);
		}
	}
	
	
	/************************************************************************************************
	 * ritorna true o false a seconda che esista o meno la variante identificata da id e datainizio
	 * 
	 * @param idVariante  un long che identifica l'ID della variante
	 * @param dataInizioVariante  Timestamp per la data di inizio della variante
	 * @return boolean
	 * @throws SQLException
	 ************************************************************************************************/
	public boolean existVariante(long idVariante, Timestamp dataInizioVariante) throws SQLException{

		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM " + VARIANTI.TABLE_NAME + " WHERE " + 
		VARIANTI.ID_VARIANTE + " = ? AND " + 
		VARIANTI.DATA_INIZIO_VAR + " = ?";
		
		try{
			stmt = activeConnection.prepareStatement(query);
			stmt.setLong(1, idVariante);
			stmt.setTimestamp(2, dataInizioVariante);
			rs = stmt.executeQuery();
			return (rs.next());
		}finally{
			close(rs, stmt);
		}
	}
	
	/**********************************************************************************************
	 * Restituisce una lista delle varianti associate ad una determinata aggiudicazione 
	 *
	 * @param idAggiudicazioni long che identifica l'ID dell'aggiudicazione
	 * @param dataInizioAggiudicazione Timestamp per la data di inizio dell'aggiudicazione
	 * @return List&lt;VarianteBean&gt;
	 * @throws SQLException
	 **********************************************************************************************/
	public List<VarianteBean> loadMany(long idAggiudicazioni, Timestamp dataInizioAggiudicazione) throws SQLException{
		return load(idAggiudicazioni, dataInizioAggiudicazione, true);
	}
	
	
	
	/****************************************************
	 *               DELETE
	 ****************************************************/
	
	private String DELETE_RECORD_VARIANTE = 
		"DELETE FROM " + VARIANTI.TABLE_NAME
		+ " WHERE " + VARIANTI.ID_VARIANTE + " = ?"
		+ " AND " + VARIANTI.DATA_INIZIO_VAR + " = ?";
	
	
	
	/**********************************************************************************************
	 * Cancella la variante 
	 * @param idVariante long per ID varainte
	 * @param dataInizioVariante Timestamp per la data di inizio della variante
	 * @return int - restituisce il numero di righe cancellate 
	 * @throws SQLException
	 **********************************************************************************************/
	public int deleteRecord(long idVariante, Timestamp dataInizioVariante)throws SQLException{
		PreparedStatement stmt = null;
		int index = 1;
		try{
			
			stmt = activeConnection.prepareStatement(DELETE_RECORD_VARIANTE);
			stmt.setLong(index++, idVariante);
			stmt.setTimestamp(index++, dataInizioVariante);
			return stmt.executeUpdate();
		}finally{
			close(null,stmt);
		}
	}
	
	/**************************************************************
	 *    UPDATE RICHIESTA ANNULLAMENTO ACCETTATA
	 **************************************************************/
	
	private static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO = 
		"UPDATE "+VARIANTI.TABLE_NAME+
		" SET " + VARIANTI.ID_STATO + " = ?," 
		+ VARIANTI.DATA_FINE_VAR + " = " + buildGetDate()
		+ " WHERE " + VARIANTI.ID_VARIANTE + " = ?"
		+ " AND " + VARIANTI.DATA_INIZIO_VAR + " = ?";
	
	
	/**********************************************************************************************
	 * Viene aggiornata una variante identificata da id e data inizio
	 * @param idVariante long per l'id della variante
	 * @param dataInizioVariante Timestamp per la data di inizoi della variante
	 * @param statoScheda Stringa che descrive lo stato della scheda
	 * @return int - restituisce il numero di righe  aggiornate. 
	 * @throws SQLException
	 **********************************************************************************************/
	public int updateRecord(long idVariante, Timestamp dataInizioVariante, String statoScheda ) throws SQLException{
		int numRow=-1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);
	
			stmt.setString(1, statoScheda);
			stmt.setLong(2, idVariante);
			stmt.setTimestamp(3,dataInizioVariante);
			numRow = stmt.executeUpdate();
		
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}
	
	/************************************************************************************************
	 * Viene confermata una variante 
	 * @param varianteBean un VarinateBean che contiene le informazioni della variante
	 * @param cfUtente Stringa che racchiude il codicefiscale dell'utente
	 * @throws SQLException
	 ************************************************************************************************/
	public int confirm(VarianteBean varianteBean, String cfUtente) throws SQLException {
		return update(varianteBean, cfUtente, true);
	}

	/************************************************************************************************
	 * Viene eseguito il salvataggio di una variante
	 * @param varianteBean un VarinateBean che contiene le informazioni della variante
	 * @param cfUtente Stringa che racchiude il codicefiscale dell'utente
	 * @throws SQLException
	 ************************************************************************************************/
	public int save(VarianteBean varianteBean, String cfUtente) throws SQLException {
		return update(varianteBean, cfUtente, false);
	}

	
	/** *********************************************************************
	 * Crazione della stringa per la query di update VARIANTI
	 ************************************************************************/
	public static String UPDATE_VARIANTI = "UPDATE " + VARIANTI.TABLE_NAME + " SET "
	
	+ VARIANTI.DATA_FINE_VAR + " = ? ,"
	+ VARIANTI.ID_STATO + " = ? ,"
	+ VARIANTI.ALTRE_MOTIVAZIONI + " = ? ,"
	+ VARIANTI.IMP_DISPOSIZIONE+ " = ? ,"
	+ VARIANTI.IMP_PROGETTAZIONE + " = ? ,"
	+ VARIANTI.IMP_SICUREZZA+" = ? ,"
	+ VARIANTI.ULTERIORI_SOMME+" = ? ,"
	+ VARIANTI.CIG_PROCEDURA+" = ? ," //TICKET ALM - 3.04.3 PT
	+ VARIANTI.LINK_VARIANTI + " = ? ," //MEV 34191 3.04.8
	+ VARIANTI.IMP_RIDET_FORNIT + " = ? ,"
	+ VARIANTI.IMP_RIDET_LAVORI + " = ? ,"
	+ VARIANTI.IMP_RIDET_SERVIZI + " = ? ,"
	+ VARIANTI.DATA_ATTO_AGGIUNTIVO + " = ? ,"
	+ VARIANTI.NUM_GIORNI_PROROGA + " = ? ,"
	+ VARIANTI.DATA_VERB_APPR + " = ? ,"
	+ VARIANTI.ID_MOTIVO_REV_PREZZI + " = ? " //MEV 34469 3.04.8
    + "WHERE " + VARIANTI.ID_VARIANTE + " = ? AND "
    + VARIANTI.DATA_INIZIO_VAR + " = ? ";
//	+ VARIANTI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING
	// PP fix controllo stato scheda + " OR " + VARIANTI.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING 
//	+ " ) ";
	
    private final String WHERE_CONF = " AND (" + VARIANTI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING
          + " OR " + VARIANTI.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING  + " ) ";
     private final String WHERE_DEF = " AND " + VARIANTI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING;

    //******************************************************************************
	
	
	
	/***********************************************************************************************
	 * Update viene richiamato in caso di salvataggio o conferma. <br>
	 *  Nel primo caso memorizza i campi mettendo a null la data di fine variante e
	 *  impostando lo stato a "in definizione" in caso di conferma imposta la data di fine variante 
	 *  alla data attuale e lo stato della scheda a "confermato" 
	 * param varianteBean : VarianteBean contenente le informazioni della variante
	 * param cfUtente : Stringa per il codice fiscale dell'utente 
	 * param conferma : booleano che determina l'esecuzione di un "salva" (False) o di un "conferma" (True)
	 * throws SQLException
	 ************************************************************************************************/
	private int update(VarianteBean varianteBean, String cfUtente, boolean conferma) throws SQLException{
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
			
		PreparedStatement stmt = activeConnection.prepareStatement(UPDATE_VARIANTI + (SimogFlags.isFlagNoDate() ? WHERE_CONF : WHERE_DEF));
		ResultSet rs = null;
		int index = 1;		
		try{		
			
			if (conferma){
				stmt.setTimestamp(index++, getNow());
				stmt.setLong(index++, StatiScheda.CONFERMATO);
				
			}else{
				stmt.setNull(index++, Types.TIMESTAMP);
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
				
			}
		
			stmt.setString(index++, varianteBean.getAltreMotivazioni());
			stmt.setBigDecimal(index++, varianteBean.getImpDisposizione());
			stmt.setBigDecimal(index++, varianteBean.getImpProgettazione());
			stmt.setBigDecimal(index++, varianteBean.getImpSicurezza());
			stmt.setBigDecimal(index++, varianteBean.getUlterioriSomme());
			
			
			
			//TICKET ALM - 3.04.3 PT
			if(varianteBean.getCigProcedura()==null || "".equals(varianteBean.getCigProcedura()))
				stmt.setNull(index++, Types.VARCHAR);
			else
				stmt.setString(index++, varianteBean.getCigProcedura());
			//FINE TICKET ALM - 3.04.3 PT
			
			//MEV 34191 3.04.8
			if(varianteBean.getLinkVarianti()==null || "".equals(varianteBean.getLinkVarianti()))
				stmt.setNull(index++, Types.VARCHAR);
			else
				stmt.setString(index++, varianteBean.getLinkVarianti());
			//FINE MEV 34191 3.04.8
			
			stmt.setBigDecimal(index++, varianteBean.getImpRidetFornit());
			stmt.setBigDecimal(index++, varianteBean.getImpRidetLavori());
			stmt.setBigDecimal(index++, varianteBean.getImpRidetServizi());
			stmt.setString(index++, PageHelper.formatDateOrNull(varianteBean.getDataAttoAggiuntivo()));// data atto aggiuntivo
			stmt.setInt(index++, varianteBean.getNumGiorniProroga() );//Giorni proroga
			stmt.setString(index++, PageHelper.formatDateOrNull(varianteBean.getDataVerbaleApprovazione()));
			
			//MEV 34469 3.04.8
			if(varianteBean.getIdMotivoRevPrezzi() == null) {
				stmt.setNull(index++, Types.BIGINT);
			}
			else {
			    stmt.setString(index++, varianteBean.getIdMotivoRevPrezzi());
			}
			//FINE MEV 34469 3.04.8
			
			stmt.setLong(index++, varianteBean.getIdVariante() );//Id variante
			stmt.setTimestamp(index++, varianteBean.getDataInizioVar());//data inizio var
			
			List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(varianteBean.getIdVariante());
			attributiChiave.add(varianteBean.getDataInizioVar());
			
			logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(VarianteBean.class, varianteBean));
			
			int num = stmt.executeUpdate();

			if(conferma)
				LogBloccoDatiManager.loggingCONFIRM(activeConnection, logger, cfUtente, ParametriServletVariante.TAB_SCHEDA_VARIANTE, attributiChiave);
			else 
				LogBloccoDatiManager.loggingUPDATE(activeConnection, logger, cfUtente, ParametriServletVariante.TAB_SCHEDA_VARIANTE, attributiChiave);			
			return num;
		}finally{
			close(rs, stmt);
		}
	}
		/**************************************************************************************************
		 * esegue una copia della variante impostando la data di fine alla data attuale, aggiorna lo stato della variante 
		 *  a "confermato", copia la variante mettendo come data inizio variante la data attuale 
		 * @param idVariante long per l'ID della variante
		 * @param dataInizioVariante Timestamp per la data di inizio della variante
		 * @return Timestamp relativa alla data attuale e quindi alla data inizio della variante.
		 * @throws SQLException
		 **************************************************************************************************/
		public Timestamp copyRecord( long idVariante, Timestamp dataInizioVariante) throws SQLException{
			
			// query che restituisce la data di fine variante di una data variante
			String QUERY_SELECT_DATA_FINE = "SELECT " + VARIANTI.DATA_FINE_VAR
			+ " FROM " + VARIANTI.TABLE_NAME
			+ " WHERE " + VARIANTI.ID_VARIANTE + " = ? "
			+ " AND " + VARIANTI.DATA_INIZIO_VAR + " = ?"
			+ " AND " + VARIANTI.ID_STATO + " = " + StatiScheda.CONFERMATO;
			
			// Aggiorna lo stato della variante a confermato
			String UPDATE_STATO_OLD_VARIANTI = 
				"UPDATE " + VARIANTI.TABLE_NAME + " SET "
				+ VARIANTI.ID_STATO + " = ? "
				+ ", " + VARIANTI.DATA_INIZIO_VAR + " = ?"
				+ ", " + VARIANTI.DATA_FINE_VAR + " = ?"
				+ " WHERE " + VARIANTI.ID_VARIANTE + " = ? "
				+ " AND " + VARIANTI.DATA_INIZIO_VAR + " = ?"
				+ " AND " + VARIANTI.ID_STATO + " = " + StatiScheda.CONFERMATO;
			
			// Insersce una variante
			String COPY_RECORD = " INSERT INTO " + VARIANTI.TABLE_NAME + "("
				+ VARIANTI.ID_VARIANTE	
			    + ", " + VARIANTI.ALTRE_MOTIVAZIONI
			    + ", " + VARIANTI.DATA_ATTO_AGGIUNTIVO
			    + ", " + VARIANTI.DATA_FINE_VAR
			    + ", " + VARIANTI.DATA_INIZIO_AGGIUDICAZIONE
			    + ", " + VARIANTI.DATA_INIZIO_VAR
			    + ", " + VARIANTI.DATA_VERB_APPR
			    + ", " + VARIANTI.ID_AGGIUDICAZIONE
			    + ", " + VARIANTI.ID_STATO
			    + ", " + VARIANTI.IMP_DISPOSIZIONE
			    + ", " + VARIANTI.IMP_RIDET_FORNIT
			    + ", " + VARIANTI.IMP_RIDET_LAVORI
			    + ", " + VARIANTI.IMP_RIDET_SERVIZI
			    + ", " + VARIANTI.ULTERIORI_SOMME
				+ ", " + VARIANTI.CIG_PROCEDURA //TICKET ALM - 3.04.3 PT
				+ ", " + VARIANTI.LINK_VARIANTI //MEV 34191 3.04.8
			    + ", " + VARIANTI.IMP_SICUREZZA
			    + ", " + VARIANTI.NUM_GIORNI_PROROGA
			    + ", " + VARIANTI.ID_MOTIVO_REV_PREZZI //MEV 34469 3.04.8
			    
			    + ") SELECT "
			    + VARIANTI.ID_VARIANTE	
			    + ", " + VARIANTI.ALTRE_MOTIVAZIONI
			    + ", " + VARIANTI.DATA_ATTO_AGGIUNTIVO
			    + ", ? " //", " + VARIANTI.DATA_FINE_VAR
			    + ", " + VARIANTI.DATA_INIZIO_AGGIUDICAZIONE
			    + ", ? " //", " + VARIANTI.DATA_INIZIO_VAR
			    + ", " + VARIANTI.DATA_VERB_APPR
			    + ", " + VARIANTI.ID_AGGIUDICAZIONE
			    + ", " + " ? "  // VARIANTI.ID_STATO
			    + ", " + VARIANTI.IMP_DISPOSIZIONE
			    + ", " + VARIANTI.IMP_RIDET_FORNIT
			    + ", " + VARIANTI.IMP_RIDET_LAVORI
			    + ", " + VARIANTI.IMP_RIDET_SERVIZI
			    + ", " + VARIANTI.ULTERIORI_SOMME
				+ ", " + VARIANTI.CIG_PROCEDURA //TICKET ALM - 3.04.3 PT
				+ ", " + VARIANTI.LINK_VARIANTI //MEV 34191 3.04.8
			    + ", " + VARIANTI.IMP_SICUREZZA
			    + ", " + VARIANTI.NUM_GIORNI_PROROGA
			    + ", " + VARIANTI.ID_MOTIVO_REV_PREZZI //MEV 34469 3.04.8
				+ " FROM " + VARIANTI.TABLE_NAME
				+ " WHERE " + VARIANTI.ID_VARIANTE + " = ? "
				+ " AND " + VARIANTI.DATA_INIZIO_VAR + " = ?";

			Timestamp dataFine = null;
			Timestamp nuovaDataAccordo = null;
			int index = 1;
			ResultSet rs = null;
			PreparedStatement getDataFine = null;
			PreparedStatement updateRecord = null;
			PreparedStatement copyRecord = null;
			try{
				//prendo la data fine del record
				getDataFine = activeConnection.prepareStatement(QUERY_SELECT_DATA_FINE);
				getDataFine.setLong(index++, idVariante);
				getDataFine.setTimestamp(index++, dataInizioVariante);
				rs = getDataFine.executeQuery();
				if(rs.next()){
					dataFine = rs.getTimestamp(VARIANTI.DATA_FINE_VAR);
					
					//il record corrente diventa il nuovo record
					index = 1;
					Timestamp nuovaDataVariante = getNow();
					updateRecord = activeConnection.prepareStatement(UPDATE_STATO_OLD_VARIANTI);
					
					updateRecord.setInt(index++, StatiScheda.IN_DEFINIZIONE);
					updateRecord.setTimestamp(index++, nuovaDataVariante);
					updateRecord.setNull(index++, Types.TIMESTAMP);
					updateRecord.setLong(index++, idVariante);
					updateRecord.setTimestamp(index++, dataInizioVariante);
					updateRecord.execute();
					
					//copy record
					index = 1;
					copyRecord = activeConnection.prepareStatement(createCopyRecord(COPY_RECORD,VARIANTI.TABLE_NAME));
					
					copyRecord.setTimestamp(index++, dataFine);
					copyRecord.setTimestamp(index++, dataInizioVariante);
					copyRecord.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA);
					copyRecord.setLong(index++, idVariante);
					copyRecord.setTimestamp(index++, nuovaDataVariante);
					copyRecord.execute();
					
					return nuovaDataVariante;
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
		
		/******************************************************************************************************
		 * <b>existAccordo</b>(long,Timestamp)<br>
		 * Verifica l'esistenza di uno o piu Varianti
		 * 
		 * @param idVariante rappresenta un long che indica l'ID dell'accordo
		 * @param dataInizioVariante specifica la data in cui e' stato effettuato l'accordo
		 * @return boolean true se esiste la variante, False se non esiste
		 * @throws SQLException
		 ******************************************************************************************************/
		public boolean existAccordo(long idVariante, Timestamp dataInizioVariante) throws SQLException{

			PreparedStatement stmt = null;
			ResultSet rs = null;
			String query = "SELECT * FROM " + VARIANTI.TABLE_NAME + " WHERE " + 
			VARIANTI.ID_VARIANTE + " = ? AND " + 
			VARIANTI.DATA_INIZIO_VAR + " = ?";
			
			try{
				stmt = activeConnection.prepareStatement(query);
				stmt.setLong(1, idVariante);
				stmt.setTimestamp(2, dataInizioVariante);
				rs = stmt.executeQuery();
				return (rs.next());
			}finally{
				close(rs, stmt);
			}
		}
	
		private static String QUERY_LOAD_MOTIVI =
			"SELECT DISTINCT " + MOTIVI_VARIANTE.T_ID_MOTIVO_VAR + ", " + MOTIVI_VARIANTE.DESCRIZIONE +
			" FROM " + MOTIVI_VARIANTE.TABLE_NAME + " " +
			"JOIN " + VARIANTE_CATEGORIA.TABLE_NAME + " " +
			"ON " + MOTIVI_VARIANTE.T_ID_MOTIVO_VAR + " = " + VARIANTE_CATEGORIA.T_ID_MOTIVO_VAR +
			" WHERE " + VARIANTE_CATEGORIA.ID_CONTRATTO + " = ?" +
			" AND " + buildISNULL(VARIANTE_CATEGORIA.T_DATA_FINE_VALIDITA,"99999999") + " >= ?" +
			" AND " + buildISNULL(MOTIVI_VARIANTE.T_DATA_FINE_VALIDITA,"99999999") + " >= ?";
	
		//TICKET ALM #2847 - Varianti
		private static String QUERY_LOAD_MOTIVI_PER_DATA_INIZIO =
				"SELECT DISTINCT " + MOTIVI_VARIANTE.T_ID_MOTIVO_VAR + ", " + MOTIVI_VARIANTE.DESCRIZIONE +
				" FROM " + MOTIVI_VARIANTE.TABLE_NAME + " " +
				"JOIN " + VARIANTE_CATEGORIA.TABLE_NAME + " " +
				"ON " + MOTIVI_VARIANTE.T_ID_MOTIVO_VAR + " = " + VARIANTE_CATEGORIA.T_ID_MOTIVO_VAR +
				" WHERE " + VARIANTE_CATEGORIA.ID_CONTRATTO + " = ?" +
				" AND " + buildISNULL(VARIANTE_CATEGORIA.T_DATA_FINE_VALIDITA,"99999999") + " >= ?" +
				" AND " + VARIANTE_CATEGORIA.T_DATA_INIZIO_VALIDITA + " <= ? " +
				" AND " + buildISNULL(MOTIVI_VARIANTE.T_DATA_FINE_VALIDITA,"99999999") + " >= ? "+
				" AND " + MOTIVI_VARIANTE.T_DATA_INIZIO_VALIDITA + " <= ? ORDER BY "+ MOTIVI_VARIANTE.T_ID_MOTIVO_VAR;
		
		
		/**************************************************************************************************
		 * <b>loadMotiviVariante</b>(String sezione)<br>
		 * restituisce una mappa don id motivo della variante e la sua descrizione
		 * @param sezione Stringa per l'ID del contratto
		 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validita' di una tipologia a posteriori
		 * @return Map 
		 * @throws SQLException
		 **************************************************************************************************/
		public Map<String,String> loadMotiviVariante(String sezione,Object o) throws SQLException{
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			HashMap<String,String> ris = new HashMap<String,String>();
			
			try {
				stmt = activeConnection.prepareStatement(QUERY_LOAD_MOTIVI);
				int index = 1;
				stmt.setString(index++, sezione);
				stmt.setObject(index++, PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()));
				stmt.setObject(index++, PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()));
				logger.debug(QUERY_LOAD_MOTIVI);
				rs = stmt.executeQuery();
				while(rs.next()){
					ris.put(rs.getString(MOTIVI_VARIANTE.ID_MOTIVO_VAR), rs.getString(MOTIVI_VARIANTE.DESCRIZIONE));	
				}	
				return ris;
			}catch(Exception e){
				logger.error(e.getMessage());
				return ris;
			}
			finally{
				close(rs, stmt);
			}
		}


		//TICKET ALM #2847 - Varianti
		/**************************************************************************************************
		 * <b>loadMotiviVariantePerData</b>(String sezione)<br>
		 * restituisce una mappa don id motivo della variante e la sua descrizione in base alla data di inizio e fine validit
		 * @param sezione Stringa per l'ID del contratto
		 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validita' di una tipologia a posteriori
		 * @return Map 
		 * @throws SQLException
		 **************************************************************************************************/
		public Map<String,String> loadMotiviVariantePerData(String sezione,Object o) throws SQLException{
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			HashMap<String,String> ris = new HashMap<String,String>();
			
			try {
				stmt = activeConnection.prepareStatement(QUERY_LOAD_MOTIVI_PER_DATA_INIZIO);
				int index = 1;
				stmt.setString(index++, sezione);
				stmt.setObject(index++, PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()));
				stmt.setObject(index++, PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()));
				stmt.setObject(index++, PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()));
				stmt.setObject(index++, PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()));
				logger.debug(QUERY_LOAD_MOTIVI_PER_DATA_INIZIO);
				rs = stmt.executeQuery();
				while(rs.next()){
					ris.put(rs.getString(MOTIVI_VARIANTE.ID_MOTIVO_VAR), rs.getString(MOTIVI_VARIANTE.DESCRIZIONE));	
				}	
				return ris;
			}catch(Exception e){
				logger.error(e.getMessage());
				return ris;
			}
			finally{
				close(rs, stmt);
			}
		}
		
		public Map<String,String> getAllMotiviVariante() throws SQLException{
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			HashMap<String,String> ris = new HashMap<String,String>();
			
			try {
				stmt = activeConnection.prepareStatement("select "+MOTIVI_VARIANTE.ID_MOTIVO_VAR+", "
						                                    + MOTIVI_VARIANTE.DESCRIZIONE
						                                    + " from "+MOTIVI_VARIANTE.TABLE_NAME
						                                    + " WHERE "+MOTIVI_VARIANTE.ID_MOTIVO_VAR+" <> ? ORDER BY "+MOTIVI_VARIANTE.ID_MOTIVO_VAR);
				stmt.setInt(1, Costanti.PROROGA_TECNICA);
				rs = stmt.executeQuery();
				while(rs.next()){
					ris.put(rs.getString(MOTIVI_VARIANTE.ID_MOTIVO_VAR), rs.getString(MOTIVI_VARIANTE.DESCRIZIONE));	
				}	
				return ris;
			}catch(Exception e){
				logger.error(e.getMessage());
				return ris;
			}
			finally{
				close(rs, stmt);
			}
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
		VarianteBean varianteBean = loadByIdSimog(idSimog);
		
		if(varianteBean.getIdVariante() > 0){
			return _annulla(varianteBean.getIdVariante(), varianteBean.getDataInizioVar(),cfUtente);
		}
		return false;
		
	}


	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean annulla(String idLocale, String rifSimog, String cfUtente)throws SQLException {
		VarianteBean varianteBean = loadByIdLocale(idLocale, rifSimog);
		
		if(varianteBean.getIdVariante() > 0){
			return _annulla(varianteBean.getIdVariante(), varianteBean.getDataInizioVar(),cfUtente);
		}
		return false;
		
	}
	
	/**
	 * @param idVariante
	 * @param dataInizioVariante
	 * @throws SQLException
	 */
	private boolean _annulla(long idVariante, Timestamp dataInizioVariante, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_VARIANTI);
			logger.debug(QUERY_ANNULLA_VARIANTI);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idVariante);
			stmt.setTimestamp(index++, dataInizioVariante);
			someRowAffected = stmt.executeUpdate() > 0;
			if(someRowAffected){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idVariante);
				attributiChiave.add(dataInizioVariante);
					LogBloccoDatiManager.loggingDELETE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_VARIANTE, attributiChiave);
			}
			return someRowAffected;
		}finally {
			close(null,stmt);
		}		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadVariante#fillBean(java.sql.ResultSet, it.avlp.simog.beans.variante.VarianteBean)
	 */
	public void fillBean(ResultSet rs, VarianteBean bean) throws SQLException {
		bean.setAltreMotivazioni(rs.getString(VARIANTI.ALTRE_MOTIVAZIONI));
		bean.setDataAttoAggiuntivo(PageHelper.getViewDate(rs.getString(VARIANTI.DATA_ATTO_AGGIUNTIVO)));
		bean.setDataFineVar(rs.getTimestamp(VARIANTI.DATA_FINE_VAR));
		bean.setDataInizioAggiudicazione(rs.getTimestamp(VARIANTI.DATA_INIZIO_AGGIUDICAZIONE));
		bean.setDataInizioVar(rs.getTimestamp(VARIANTI.DATA_INIZIO_VAR));
		bean.setDataVerbaleApprovazione(PageHelper.getViewDate(rs.getString(VARIANTI.DATA_VERB_APPR)));
		bean.setDescrizioneStato(rs.getString(STATI_SCHEDA.DESCRIZIONE));
		bean.setIdAggiudicazione(rs.getLong(VARIANTI.ID_AGGIUDICAZIONE));
		bean.setIdStato(rs.getLong(VARIANTI.ID_STATO));
		bean.setIdVariante(rs.getLong(VARIANTI.ID_VARIANTE));
		
		bean.setImpDisposizione(rs.getBigDecimal(VARIANTI.IMP_DISPOSIZIONE));
		bean.setImpProgettazione(rs.getBigDecimal(VARIANTI.IMP_PROGETTAZIONE));
		bean.setUlterioriSomme(rs.getBigDecimal(VARIANTI.ULTERIORI_SOMME));
		bean.setCigProcedura(rs.getString(VARIANTI.CIG_PROCEDURA)); //TICKET ALM - 3.04.3 PT
		bean.setLinkVarianti(rs.getString(VARIANTI.LINK_VARIANTI)); //MEV 34191 3.04.8
		bean.setImpRidetFornit(rs.getBigDecimal(VARIANTI.IMP_RIDET_FORNIT)); 
		bean.setImpRidetLavori(rs.getBigDecimal(VARIANTI.IMP_RIDET_LAVORI));
		bean.setImpRidetServizi(rs.getBigDecimal(VARIANTI.IMP_RIDET_SERVIZI));
		bean.setImpSicurezza(rs.getBigDecimal(VARIANTI.IMP_SICUREZZA));
		
		//MEV 34469 3.04.8
		bean.setIdMotivoRevPrezzi(rs.getString(VARIANTI.ID_MOTIVO_REV_PREZZI));
		//FINE
		
		bean.setNumGiorniProroga(rs.getInt(VARIANTI.NUM_GIORNI_PROROGA));
		bean.setIdLocale(rs.getString(VARIANTI.ID_SCHEDA_LOCALE));
		EventiMotiviVariantiManager emvb = new EventiMotiviVariantiManager(activeConnection,logger);
		bean.setEmvb(emvb.loadMany(bean.getIdVariante(), bean.getDataInizioVar()));
		
	}


	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadVariante#loadByIdLocale(java.lang.String, java.lang.String)
	 */
	public VarianteBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		VarianteBean bean = null;
		try{
			stmt = activeConnection.prepareStatement(SELECT_ONE_VARIANTE + WHERE_IDLOCALE);
			stmt.setString(index++, idLocale);
			long idAggiudicazioneL = Long.parseLong(idAggiudicazione);
			stmt.setLong(index++, idAggiudicazioneL );				
			rs = stmt.executeQuery();
			bean = new VarianteBean();
			while(rs.next()){				
				fillBean(rs, bean);		
			}
			return bean;
		}finally{
			close(rs,stmt);
		}
	}


	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadVariante#loadByIdSimog(long)
	 */
	public VarianteBean loadByIdSimog(long idSimog) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		VarianteBean bean = null;
		try{
			stmt = activeConnection.prepareStatement(SELECT_ONE_VARIANTE + WHERE_IDSIMOG);
			stmt.setLong(index++, idSimog );
			rs = stmt.executeQuery();
			bean = new VarianteBean();
			while(rs.next()){					
				fillBean(rs, bean);		
			}
			return bean;
		}finally{
			close(rs,stmt);
		}
	}


//	public boolean annullaByAggiudicazione(long idAggiudicazione,Timestamp dataInizioAggiudicazione, String cfUtente)throws SQLException {
//		List<VarianteBean> listOfVarianti = this.loadMany(idAggiudicazione, dataInizioAggiudicazione);
//		EventiMotiviVariantiManager eventiManager = new EventiMotiviVariantiManager(activeConnection, logger);
//		
//		boolean esitoOperazione = listOfVarianti.size() > 0 ? true : false;
//		int counter = 0; 
//		for(VarianteBean varianteCorrente : listOfVarianti){
//			if(counter == 0){
//				eventiManager.annulla(varianteCorrente.getIdVariante(), varianteCorrente.getDataInizioVar());
//			}
//			esitoOperazione = esitoOperazione && _annulla(varianteCorrente.getIdVariante(), varianteCorrente.getDataInizioVar(), cfUtente);
//		}return esitoOperazione;
//	}
	
	//MEV 34469 3.04.8
		/**************************************************************************************************
		 * <b>getAllMotivoRevPrezzi</b><br>
		 * restituisce una mappa con id motivo revisione prezzi e la loro descrizione	
		 * @return SortedMap 
		 * @throws SQLException
		 **************************************************************************************************/
		public SortedMap<String, String> getAllMotivoRevPrezzi() throws SQLException{
			PreparedStatement stmt = null;
			ResultSet rs = null;
			
			SortedMap<String,String> ris = new TreeMap<String,String>();
			
			try {
				stmt = activeConnection.prepareStatement("SELECT "+MOTIVO_REVISIONE_PREZZI.ID_MOTIVO_REV_PREZZI+", "
						                                    + MOTIVO_REVISIONE_PREZZI.DESCRIZIONE
						                                    + " FROM "+MOTIVO_REVISIONE_PREZZI.TABLE_NAME);
				
				rs = stmt.executeQuery();
				while(rs.next()){
					ris.put(rs.getString(MOTIVO_REVISIONE_PREZZI.ID_MOTIVO_REV_PREZZI), rs.getString(MOTIVO_REVISIONE_PREZZI.DESCRIZIONE));				
				}	
				return ris;
			}catch(Exception e){
				logger.error(e.getMessage());
				return ris;
			}
			finally{
				close(rs, stmt);
			}
		}


	
	
}	

