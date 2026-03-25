package it.avcp.simog.managers.collaudo;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamento;
import it.avcp.simog.managers.comportamento.caricamento.ILoadCollaudo;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.common.servlet.ParametriServletCollaudo;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.COLLAUDO;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
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
 * Classe per la gestione dei dati relativi ai collaudi
 *
 */
public class CollaudoManager extends AccessiDB implements IAnnullamento,ILoadCollaudo {
	
	/**
	 * Cotruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public CollaudoManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	private final String QUERY_SELECT_COLLAUDO =
		"SELECT " +
		COLLAUDO.TABLE_NAME + ".* ," +
		STATI_SCHEDA.T_DESCRIZIONE+ " + " +  buildRichAnnQuery(ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO, COLLAUDO.T_ID_COLLAUDO,null) 
		+" AS "+STATI_SCHEDA.DESCRIZIONE+
		" FROM " + 
		COLLAUDO.TABLE_NAME + ", " +
		STATI_SCHEDA.TABLE_NAME;
	


		
	/**
	 * metodo per l caricamento del collaudo associato all'aggiudicazione di cui
	 * id in ingresso
	 * 
	 * @param idAggiudicazione Long
	 * @param dataIniAggiudicazione Timestamp
	 * @return CollaudoBean
	 * @throws SQLException
	 */
	public CollaudoBean load(Long idAggiudicazione,Timestamp dataIniAggiudicazione)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CollaudoBean cb = new CollaudoBean();
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_COLLAUDO + WHERE_STANDARD);
			int index = 1;
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataIniAggiudicazione);
			rs = stmt.executeQuery();
			if(rs.next()){
				fillBean(rs, cb, false);
			}
		}finally{
			close(rs,stmt);
		}
		return cb;
	}
	
	/**
	 * metodo per l caricamento del collaudo associato all'id
	 *  in ingresso
	 * 
	 * @param idAggiudicazione Long
	 * @param dataIniAggiudicazione Timestamp
	 * @return CollaudoBean
	 * @throws SQLException
	 */
	public CollaudoBean loadById(Long id,Timestamp dataIni)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CollaudoBean cb = new CollaudoBean();
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_COLLAUDO + WHERE_ID);
			int index = 1;
			stmt.setLong(index++, id);
			stmt.setTimestamp(index++, dataIni);
			rs = stmt.executeQuery();
			while(rs.next()){
				fillBean(rs, cb, true);
			}
		}finally{
			close(rs,stmt);
		}
		return cb;
	}
	private final String INSERT_COLLAUDO = "INSERT INTO " + COLLAUDO.TABLE_NAME + " ("
		+ COLLAUDO.DATA_COLLAUDO_STAT
		+ ", " + COLLAUDO.DATA_REGOLARE_ESEC
		+ ", " + COLLAUDO.MODO_COLLAUDO
		+ ", " + COLLAUDO.DATA_NOMINA_COLL
		+ ", " + COLLAUDO.DATA_INIZIO_OPER
		+ ", " + COLLAUDO.DATA_CERT_COLLAUDO
		+ ", " + COLLAUDO.DATA_DELIBERA
		+ ", " + COLLAUDO.ESITO_COLLAUDO
		+ ", " + COLLAUDO.IMP_FINALE_LAVORI
		+ ", " + COLLAUDO.IMP_FINALE_SERVIZI
		+ ", " + COLLAUDO.IMP_FINALE_FORNIT
		+ ", " + COLLAUDO.IMP_FINALE_SICUR 
		+ ", " + COLLAUDO.IMP_PROGETTAZIONE
		+ ", " + COLLAUDO.IMP_DISPOSIZIONE
		+ ", " + COLLAUDO.AMM_NUM_DEFINITE
		+ ", " + COLLAUDO.AMM_NUM_DADEF
		+ ", " + COLLAUDO.AMM_IMPORTO_RICH
		+ ", " + COLLAUDO.AMM_IMPORTO_DEF
		+ ", " + COLLAUDO.ARB_NUM_DEFINITE							
		+ ", " + COLLAUDO.ARB_NUM_DADEF
		+ ", " + COLLAUDO.ARB_IMPORTO_RICH
		+ ", " + COLLAUDO.ARB_IMPORTO_DEF
		+ ", " + COLLAUDO.GIU_NUM_DEFINITE							
		+ ", " + COLLAUDO.GIU_NUM_DADEF
		+ ", " + COLLAUDO.GIU_IMPORTO_RICH
		+ ", " + COLLAUDO.GIU_IMPORTO_DEF
		+ ", " + COLLAUDO.TRA_NUM_DEFINITE							
		+ ", " + COLLAUDO.TRA_NUM_DADEF
		+ ", " + COLLAUDO.TRA_IMPORTO_RICH
		+ ", " + COLLAUDO.TRA_IMPORTO_DEF
		+ ", " + COLLAUDO.ID_AGGIUDICAZIONE
		+ ", " + COLLAUDO.DATA_INIZIO_AGGIUDICAZIONE
		+ ", " + COLLAUDO.ID_STATO//
		+ ", " + COLLAUDO.DATA_FINE_COLL
		+ ", " + COLLAUDO.DATA_INIZIO_COLL
		+ ", " + COLLAUDO.LAVORI_ESTESI
		+ ", " + COLLAUDO.ID_SCHEDA_LOCALE
		+ ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	/**
	 * metodo per l'inserimeto di un collaudo
	 * 
	 * @param bean CollaudoBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public void insert(CollaudoBean bean , String cfUtente)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try{
			stmt = activeConnection.prepareStatement(createInsertQuery(INSERT_COLLAUDO , COLLAUDO.ID_COLLAUDO));
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataCollaudoStat()));
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataRegolareEsec()));
			stmt.setString(index++, bean.getModoCollaudo());
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataNominaColl()));
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataIniOper()));
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataCertCollaudo()));
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataDelibera()));
			stmt.setString(index++, bean.getEsitoCollaudo());
			stmt.setBigDecimal(index++, bean.getImpFinaleLavori());
			stmt.setBigDecimal(index++, bean.getImpFinaleServizi());
			stmt.setBigDecimal(index++, bean.getImpFinaleFornit());
			stmt.setBigDecimal(index++, bean.getImpFinaleSicur());
			stmt.setBigDecimal(index++ , bean.getImpProgettazione());
			stmt.setBigDecimal(index++ , bean.getImpDisposizione());
			stmt.setInt(index++, bean.getAmmNumDefinite());
			stmt.setInt(index++, bean.getAmmNumDaDef());
			stmt.setBigDecimal(index++, bean.getAmmImportoRich());
			stmt.setBigDecimal(index++, bean.getAmmImportoDef());
			stmt.setInt(index++, bean.getArbNumDefinite());
			stmt.setInt(index++, bean.getArbNumDaDef());
			stmt.setBigDecimal(index++, bean.getArbImportoRich());
			stmt.setBigDecimal(index++, bean.getArbImportoDef());
			stmt.setInt(index++, bean.getGiuNumDefinite());
			stmt.setInt(index++, bean.getGiuNumDaDef());
			stmt.setBigDecimal(index++, bean.getGiuImportORich());
			stmt.setBigDecimal(index++, bean.getGiuImportoDef());
			stmt.setInt(index++, bean.getTraNumDefinite());
			stmt.setInt(index++, bean.getTraNumDaDef());
			stmt.setBigDecimal(index++, bean.getTraImportoRich());
			stmt.setBigDecimal(index++, bean.getTraImportoDef());
			stmt.setLong(index++, bean.getIdAggiudicazione());
			stmt.setTimestamp(index++, bean.getDataIniAggiudicazione());
			bean.setIdStato(StatiScheda.IN_DEFINIZIONE);
			stmt.setLong(index++, bean.getIdStato());
			stmt.setNull(index++, Types.TIMESTAMP);
			bean.setDataIniColl(getNow());
			stmt.setTimestamp(index++, bean.getDataIniColl());
			stmt.setString(index++, bean.getFlagLavoriEstesi());
			if(bean.getIdLocale() == null){
				stmt.setNull(index++, Types.VARCHAR);
			}else{
				stmt.setString(index++, bean.getIdLocale());
			}
			
			if(stmt.execute()){
				rs = stmt.getResultSet();
				rs.next();
				bean.setIdCollaudo(rs.getLong(COLLAUDO.ID_COLLAUDO));
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(bean.getIdCollaudo());
				attributiChiave.add(bean.getDataIniColl());
				LogBloccoDatiManager.loggingINSERT(activeConnection, logger, cfUtente, ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO , attributiChiave);
			}
			
		}finally{
			close(rs, stmt);
		}
	}
	
	private final String UPDATE_COLLAUDO = "UPDATE " + COLLAUDO.TABLE_NAME + " SET "
	+ COLLAUDO.DATA_FINE_COLL + " =? "
	+ ", " + COLLAUDO.ID_STATO + " =? "
	
	+ ", " + COLLAUDO.DATA_COLLAUDO_STAT + " =? "
	+ ", " + COLLAUDO.DATA_REGOLARE_ESEC + " =? "
	+ ", " + COLLAUDO.MODO_COLLAUDO + " =? "
	+ ", " + COLLAUDO.DATA_NOMINA_COLL + " =? "
	+ ", " + COLLAUDO.DATA_INIZIO_OPER + " =? "
	+ ", " + COLLAUDO.DATA_CERT_COLLAUDO + " =? "
	+ ", " + COLLAUDO.DATA_DELIBERA + " =? "
	+ ", " + COLLAUDO.ESITO_COLLAUDO + " =? "
	+ ", " + COLLAUDO.IMP_FINALE_LAVORI + " =? "
	+ ", " + COLLAUDO.IMP_FINALE_SERVIZI + " =? "
	+ ", " + COLLAUDO.IMP_FINALE_FORNIT + " =? "
	+ ", " + COLLAUDO.IMP_FINALE_SICUR + " =? "
	+ ", " + COLLAUDO.IMP_PROGETTAZIONE + " =? "
	+ ", " + COLLAUDO.IMP_DISPOSIZIONE + " =? "
	+ ", " + COLLAUDO.AMM_NUM_DEFINITE + " =? "
	+ ", " + COLLAUDO.AMM_NUM_DADEF + " =? "
	+ ", " + COLLAUDO.AMM_IMPORTO_RICH + " =? "
	+ ", " + COLLAUDO.AMM_IMPORTO_DEF + " =? "
	+ ", " + COLLAUDO.ARB_NUM_DEFINITE + " =? "				
	+ ", " + COLLAUDO.ARB_NUM_DADEF + " =? "
	+ ", " + COLLAUDO.ARB_IMPORTO_RICH + " =? "
	+ ", " + COLLAUDO.ARB_IMPORTO_DEF + " =? "
	+ ", " + COLLAUDO.GIU_NUM_DEFINITE + " =? "				
	+ ", " + COLLAUDO.GIU_NUM_DADEF + " =? "
	+ ", " + COLLAUDO.GIU_IMPORTO_RICH + " =? "
	+ ", " + COLLAUDO.GIU_IMPORTO_DEF + " =? "
	+ ", " + COLLAUDO.TRA_NUM_DEFINITE + " =? "				
	+ ", " + COLLAUDO.TRA_NUM_DADEF + " =? "
	+ ", " + COLLAUDO.TRA_IMPORTO_RICH + " =? "
	+ ", " + COLLAUDO.TRA_IMPORTO_DEF + " =? "
	+ ", " + COLLAUDO.LAVORI_ESTESI + " =? "
	+ " WHERE " + COLLAUDO.ID_COLLAUDO + " =? AND "
	+ COLLAUDO.DATA_INIZIO_COLL + " =?";
	
	// PP fix controllo stato scheda
	private final String WHERE_CONF = " AND ("+ COLLAUDO.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING
	 + " OR "+ COLLAUDO.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING +" )";
    private final String WHERE_DEF = " AND "+ COLLAUDO.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING;
	
	/**
	 * metodo per l'aggiornamento di un collaudo
	 * 
	 * @param bean CollaudoBean
	 * @param cfUtente String
	 * @param confirm  boolean (true � per confermare il collaudo, altrimenti semplice aggiornamento)
	 * @throws SQLException
	 */
	public int update(CollaudoBean bean , String cfUtente , boolean confirm)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try{
			stmt = activeConnection.prepareStatement(UPDATE_COLLAUDO + ((SimogFlags.isFlagNoDate()) ? WHERE_CONF : WHERE_DEF));
			if(confirm){
				stmt.setTimestamp(index++, getNow());
				stmt.setLong(index++, StatiScheda.CONFERMATO);
			}
			else{
				stmt.setNull(index++, Types.TIMESTAMP);
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
			}
			
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataCollaudoStat()));
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataRegolareEsec()));
			stmt.setString(index++, bean.getModoCollaudo());
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataNominaColl()));
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataIniOper()));
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataCertCollaudo()));
			stmt.setString(index++, PageHelper.formatDateOrNull(bean.getDataDelibera()));
			stmt.setString(index++, bean.getEsitoCollaudo());
			stmt.setBigDecimal(index++, bean.getImpFinaleLavori());
			stmt.setBigDecimal(index++, bean.getImpFinaleServizi());
			stmt.setBigDecimal(index++, bean.getImpFinaleFornit());
			stmt.setBigDecimal(index++, bean.getImpFinaleSicur());
			stmt.setBigDecimal(index++ , bean.getImpProgettazione());
			stmt.setBigDecimal(index++ , bean.getImpDisposizione());
			stmt.setInt(index++, bean.getAmmNumDefinite());
			stmt.setInt(index++, bean.getAmmNumDaDef());
			stmt.setBigDecimal(index++, bean.getAmmImportoRich());
			stmt.setBigDecimal(index++, bean.getAmmImportoDef());
			stmt.setInt(index++, bean.getArbNumDefinite());
			stmt.setInt(index++, bean.getArbNumDaDef());
			stmt.setBigDecimal(index++, bean.getArbImportoRich());
			stmt.setBigDecimal(index++, bean.getArbImportoDef());
			stmt.setInt(index++, bean.getGiuNumDefinite());
			stmt.setInt(index++, bean.getGiuNumDaDef());
			stmt.setBigDecimal(index++, bean.getGiuImportORich());
			stmt.setBigDecimal(index++, bean.getGiuImportoDef());
			stmt.setInt(index++, bean.getTraNumDefinite());
			stmt.setInt(index++, bean.getTraNumDaDef());
			stmt.setBigDecimal(index++, bean.getTraImportoRich());
			stmt.setBigDecimal(index++, bean.getTraImportoDef());
			stmt.setString(index++, bean.getFlagLavoriEstesi());
			stmt.setLong(index++, bean.getIdCollaudo());
			stmt.setTimestamp(index++, bean.getDataIniColl());
			
			int num = stmt.executeUpdate();
			List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(bean.getIdCollaudo());
			attributiChiave.add(bean.getDataIniColl());
			if(confirm){
				LogBloccoDatiManager.loggingCONFIRM(activeConnection, logger, cfUtente, ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO, attributiChiave);
			}
			else{
				LogBloccoDatiManager.loggingUPDATE(activeConnection, logger, cfUtente, ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO, attributiChiave);
			}
			return num;
		}finally{
			close(rs, stmt);
		}
	}
	
	/**
	 * metodo per il salvataggio di un collaudo
	 * 
	 * @param bean CollaudoBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public int save(CollaudoBean bean , String cfUtente)throws SQLException{
		return update(bean, cfUtente, false);
	}
	
	/**
	 * metodo per la conferma di un collaudo
	 * 
	 * @param bean CollaudoBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public int confirm(CollaudoBean bean , String cfUtente)throws SQLException{
		return update(bean, cfUtente, true);
	}
	
	/**
	 * metodo per la verifica dell'esistenza di un collaudo
	 * 
	 * @param idCollaudo long
	 * @param datainizio Timenstamp
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean existCollaudo(long idCollaudo , Timestamp datainizio)throws SQLException{
		PreparedStatement stmt = null;
		int index = 1;
		String EXIST = "SELECT * FROM " + COLLAUDO.TABLE_NAME
		+ " WHERE " + COLLAUDO.ID_COLLAUDO + " =? AND "
		+ COLLAUDO.DATA_INIZIO_COLL + " =? ";
		try{
			stmt = activeConnection.prepareStatement(EXIST);
			stmt.setLong(index++, idCollaudo);
			stmt.setTimestamp(index++, datainizio);
			return stmt.executeQuery().next();
		}finally{
			close(null, stmt);
		}
	}
	
	private final String DELETE_COLLAUDO = "DELETE FROM " + COLLAUDO.TABLE_NAME
	+ " WHERE " + COLLAUDO.ID_COLLAUDO 
	+ " =? AND " + COLLAUDO.DATA_INIZIO_COLL + " =? ";
	
	/**
	 * metodo per il cancellamento di un record
	 * 
	 * @param idCollaudo long
	 * @param dataInizio Timenstamp
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int deleteRecord(long idCollaudo , Timestamp dataInizio)throws SQLException{
		PreparedStatement stmt = null;
		int index = 1;
		try{
			stmt = activeConnection.prepareStatement(DELETE_COLLAUDO);
			stmt.setLong(index++, idCollaudo);
			stmt.setTimestamp(index++, dataInizio);
			return stmt.executeUpdate();
		}finally{
			close(null, stmt);
		}
	}
	
	/**
	 * metodo per la storicizzazione di un record
	 * 
	 * @param idCollaudo long
	 * @param dataInizio Timestamp
	 * @return nuova data inizio record  Timestamp
	 * @throws SQLException
	 */
	public Timestamp copyRecord(long idCollaudo , Timestamp dataInizio)throws SQLException{
		String SELECT_DATA_FINE = "SELECT " + COLLAUDO.DATA_FINE_COLL
		+ " FROM " + COLLAUDO.TABLE_NAME
		+ " WHERE " + COLLAUDO.ID_COLLAUDO + " =?" 
		+ " AND " + COLLAUDO.DATA_INIZIO_COLL + " =? "
		+ " AND " + COLLAUDO.ID_STATO + " = " + StatiScheda.CONFERMATO;
		
		String UPDATE_STATO_OLD_COLLAUDO = "UPDATE " + COLLAUDO.TABLE_NAME
		+ " SET " + COLLAUDO.ID_STATO + " =? "
		+ ", " + COLLAUDO.DATA_INIZIO_COLL + " =? "
		+ ", " + COLLAUDO.DATA_FINE_COLL + " =? "
		+ " WHERE " + COLLAUDO.ID_COLLAUDO + " =? "
		+ " AND " + COLLAUDO.DATA_INIZIO_COLL+ " =? "
		+ " AND " + COLLAUDO.ID_STATO + "=" + StatiScheda.CONFERMATO;
		
		String COPY_RECORD = "INSERT INTO " + COLLAUDO.TABLE_NAME + " ("
		+ COLLAUDO.DATA_COLLAUDO_STAT
		+ ", " + COLLAUDO.DATA_REGOLARE_ESEC
		+ ", " + COLLAUDO.MODO_COLLAUDO
		+ ", " + COLLAUDO.DATA_NOMINA_COLL
		+ ", " + COLLAUDO.DATA_INIZIO_OPER
		+ ", " + COLLAUDO.DATA_CERT_COLLAUDO
		+ ", " + COLLAUDO.DATA_DELIBERA
		+ ", " + COLLAUDO.ESITO_COLLAUDO
		+ ", " + COLLAUDO.IMP_FINALE_LAVORI
		+ ", " + COLLAUDO.IMP_FINALE_SERVIZI
		+ ", " + COLLAUDO.IMP_FINALE_FORNIT
		+ ", " + COLLAUDO.IMP_FINALE_SICUR 
		+ ", " + COLLAUDO.IMP_PROGETTAZIONE
		+ ", " + COLLAUDO.IMP_DISPOSIZIONE
		+ ", " + COLLAUDO.AMM_NUM_DEFINITE
		+ ", " + COLLAUDO.AMM_NUM_DADEF
		+ ", " + COLLAUDO.AMM_IMPORTO_RICH
		+ ", " + COLLAUDO.AMM_IMPORTO_DEF
		+ ", " + COLLAUDO.ARB_NUM_DEFINITE							
		+ ", " + COLLAUDO.ARB_NUM_DADEF
		+ ", " + COLLAUDO.ARB_IMPORTO_RICH
		+ ", " + COLLAUDO.ARB_IMPORTO_DEF
		+ ", " + COLLAUDO.GIU_NUM_DEFINITE							
		+ ", " + COLLAUDO.GIU_NUM_DADEF
		+ ", " + COLLAUDO.GIU_IMPORTO_RICH
		+ ", " + COLLAUDO.GIU_IMPORTO_DEF
		+ ", " + COLLAUDO.TRA_NUM_DEFINITE							
		+ ", " + COLLAUDO.TRA_NUM_DADEF
		+ ", " + COLLAUDO.TRA_IMPORTO_RICH
		+ ", " + COLLAUDO.TRA_IMPORTO_DEF
		+ ", " + COLLAUDO.ID_AGGIUDICAZIONE
		+ ", " + COLLAUDO.DATA_INIZIO_AGGIUDICAZIONE
		+ ", " + COLLAUDO.ID_COLLAUDO
		+ ", " + COLLAUDO.ID_SCHEDA_LOCALE
		
		+ ", " + COLLAUDO.DATA_INIZIO_COLL
		+ ", " + COLLAUDO.DATA_FINE_COLL
		+ ", " + COLLAUDO.ID_STATO
		
		+ ", " + COLLAUDO.LAVORI_ESTESI
		+ ") SELECT "
		+ COLLAUDO.DATA_COLLAUDO_STAT
		+ ", " + COLLAUDO.DATA_REGOLARE_ESEC
		+ ", " + COLLAUDO.MODO_COLLAUDO
		+ ", " + COLLAUDO.DATA_NOMINA_COLL
		+ ", " + COLLAUDO.DATA_INIZIO_OPER
		+ ", " + COLLAUDO.DATA_CERT_COLLAUDO
		+ ", " + COLLAUDO.DATA_DELIBERA
		+ ", " + COLLAUDO.ESITO_COLLAUDO
		+ ", " + COLLAUDO.IMP_FINALE_LAVORI
		+ ", " + COLLAUDO.IMP_FINALE_SERVIZI
		+ ", " + COLLAUDO.IMP_FINALE_FORNIT
		+ ", " + COLLAUDO.IMP_FINALE_SICUR 
		+ ", " + COLLAUDO.IMP_PROGETTAZIONE
		+ ", " + COLLAUDO.IMP_DISPOSIZIONE
		+ ", " + COLLAUDO.AMM_NUM_DEFINITE
		+ ", " + COLLAUDO.AMM_NUM_DADEF
		+ ", " + COLLAUDO.AMM_IMPORTO_RICH
		+ ", " + COLLAUDO.AMM_IMPORTO_DEF
		+ ", " + COLLAUDO.ARB_NUM_DEFINITE							
		+ ", " + COLLAUDO.ARB_NUM_DADEF
		+ ", " + COLLAUDO.ARB_IMPORTO_RICH
		+ ", " + COLLAUDO.ARB_IMPORTO_DEF
		+ ", " + COLLAUDO.GIU_NUM_DEFINITE							
		+ ", " + COLLAUDO.GIU_NUM_DADEF
		+ ", " + COLLAUDO.GIU_IMPORTO_RICH
		+ ", " + COLLAUDO.GIU_IMPORTO_DEF
		+ ", " + COLLAUDO.TRA_NUM_DEFINITE							
		+ ", " + COLLAUDO.TRA_NUM_DADEF
		+ ", " + COLLAUDO.TRA_IMPORTO_RICH
		+ ", " + COLLAUDO.TRA_IMPORTO_DEF
		+ ", " + COLLAUDO.ID_AGGIUDICAZIONE
		+ ", " + COLLAUDO.DATA_INIZIO_AGGIUDICAZIONE
		+ ", " + COLLAUDO.ID_COLLAUDO
		+ ", " + COLLAUDO.ID_SCHEDA_LOCALE
		
		+ ", ?"
		+ ", ?"
		+ ", ?"
		
		+ ", " + COLLAUDO.LAVORI_ESTESI
		+ " FROM " + COLLAUDO.TABLE_NAME
		+ " WHERE " + COLLAUDO.ID_COLLAUDO + " =? AND "
		+ COLLAUDO.DATA_INIZIO_COLL + " =? ";
		
		Timestamp dataFine = null;
		Timestamp nuovaDataRecord = null;
		int index = 1;
		ResultSet rs = null;
		PreparedStatement getDataFine = null;
		PreparedStatement updateRecord = null;
		PreparedStatement copyRecord = null;
		try{
			getDataFine = activeConnection.prepareStatement(SELECT_DATA_FINE);
			getDataFine.setLong(index++, idCollaudo);
			getDataFine.setTimestamp(index++, dataInizio);
			rs = getDataFine.executeQuery();
			if(rs.next()){
				dataFine = rs.getTimestamp(COLLAUDO.DATA_FINE_COLL);
				
				index = 1;
				nuovaDataRecord = getNow();
				updateRecord = activeConnection.prepareStatement(UPDATE_STATO_OLD_COLLAUDO);
				updateRecord.setLong(index++, StatiScheda.IN_DEFINIZIONE);
				updateRecord.setTimestamp(index++, nuovaDataRecord);
				updateRecord.setNull(index++, Types.TIMESTAMP);
				updateRecord.setLong(index++, idCollaudo);
				updateRecord.setTimestamp(index++, dataInizio);
				updateRecord.execute();
				
				index = 1;
				copyRecord = activeConnection.prepareStatement(createCopyRecord(COPY_RECORD, COLLAUDO.TABLE_NAME));
				copyRecord.setTimestamp(index++, dataInizio);
				copyRecord.setTimestamp(index++, dataFine);
				copyRecord.setLong(index++, StatiScheda.ANNULLAMENTO_RICHIESTA);
				copyRecord.setLong(index++, idCollaudo);
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
	

	
	private static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO = 
		"UPDATE " + COLLAUDO.TABLE_NAME
		+ " SET " + COLLAUDO.ID_STATO + " =?,"
		+ COLLAUDO.DATA_FINE_COLL + " = " + buildGetDate()
		+ " WHERE " + COLLAUDO.ID_COLLAUDO + " =?"
		+ " AND " + COLLAUDO.DATA_INIZIO_COLL + " =?";
	
	/**
	 * metodo per l'aggiornamento di un record
	 * 
	 * @param idCollaudo long
	 * @param dataInizioCollaudo Timestamp
	 * @param statoScheda String
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int updateRecord(long idCollaudo , Timestamp dataInizioCollaudo , String statoScheda)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		int numRow = -1;
		try{
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);
			stmt.setString(index++, statoScheda);
			stmt.setLong(index++, idCollaudo);
			stmt.setTimestamp(index++, dataInizioCollaudo);
			numRow = stmt.executeUpdate();
		}finally{
			close(rs, stmt);
		}
		return numRow;
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(long, java.sql.Timestamp, java.lang.String)
	 */
	public boolean annulla(long idCollaudo, Timestamp dataInizioCollaudo, String cfUtente) throws SQLException {
		return _annulla(idCollaudo, dataInizioCollaudo,cfUtente);
		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(long, java.lang.String)
	 */
	public boolean annulla(long idSimog, String cfUtente) throws SQLException {
		CollaudoBean collaudoBean = loadByIdSimog(idSimog);
		
		if(collaudoBean.getIdCollaudo() > 0){
			return _annulla(collaudoBean.getIdCollaudo(), collaudoBean.getDataIniColl(),cfUtente);
		}
		return false;
		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean annulla(String idLocale, String rifSimog, String cfUtente) throws SQLException {
		CollaudoBean collaudoBean = loadByIdLocale(idLocale, rifSimog);
		
		if(collaudoBean.getIdCollaudo() > 0){
			return _annulla(collaudoBean.getIdCollaudo(), collaudoBean.getDataIniColl(),cfUtente);
		}
		return false;
		
	}
	/**
	 * @param idCollaudo
	 * @param dataInizioCollaudo
	 * @throws SQLException
	 */
	private boolean _annulla(long idCollaudo, Timestamp dataInizioCollaudo, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_COLLAUDO);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idCollaudo);
			stmt.setTimestamp(index++, dataInizioCollaudo);
			someRowAffected = stmt.executeUpdate() > 0;
			if(someRowAffected){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idCollaudo);
				attributiChiave.add(dataInizioCollaudo);
					LogBloccoDatiManager.loggingDELETE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_COLLAUDO, attributiChiave);
			}
			return someRowAffected;
		}finally {
			close(null,stmt);
		}		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadCollaudo#fillBean(java.sql.ResultSet, it.avlp.simog.beans.collaudo.CollaudoBean)
	 */
	public void fillBean(ResultSet rs, CollaudoBean bean, boolean ignoraStato) throws SQLException {
		bean.setIdCollaudo(rs.getLong(COLLAUDO.ID_COLLAUDO));
		bean.setDataIniColl(rs.getTimestamp(COLLAUDO.DATA_INIZIO_COLL));
		bean.setDataFinColl(rs.getTimestamp(COLLAUDO.DATA_FINE_COLL));
		bean.setIdStato(rs.getLong(COLLAUDO.ID_STATO));
		bean.setIdAggiudicazione(rs.getLong(COLLAUDO.ID_AGGIUDICAZIONE));
		bean.setDataIniAggiudicazione(rs.getTimestamp(COLLAUDO.DATA_INIZIO_AGGIUDICAZIONE));
		bean.setDataRegolareEsec(PageHelper.getViewDate(rs.getString(COLLAUDO.DATA_REGOLARE_ESEC)));
		bean.setDataCollaudoStat(PageHelper.getViewDate(rs.getString(COLLAUDO.DATA_COLLAUDO_STAT)));

		// PP opzionale
		if(rs.getString(COLLAUDO.MODO_COLLAUDO) != null && !"".equals(rs.getString(COLLAUDO.MODO_COLLAUDO)))
			bean.setModoCollaudo(rs.getString(COLLAUDO.MODO_COLLAUDO));
		
		bean.setDataNominaColl(PageHelper.getViewDate(rs.getString(COLLAUDO.DATA_NOMINA_COLL)));

		// FIXMATO: ********* (togliere nella 3.0) PP il campo è required per la versione 2.9, ma i controlli consentono il null (?) forzo una data fittizia
//		if(SimogFlags.isFlagNoDate()){  // solo per il massloader, uso il flag delle date visto che lo imposta solo ML
//		if(rs.getString(COLLAUDO.DATA_INIZIO_OPER)!= null && !"".equals(rs.getString(COLLAUDO.DATA_INIZIO_OPER)))
//			bean.setDataIniOper(PageHelper.getViewDate(rs.getString(COLLAUDO.DATA_INIZIO_OPER)));
//		else
//			bean.setDataIniOper(PageHelper.getViewDate(Costanti.DEFAULT_DATE));
//		}
//		else

		bean.setDataIniOper(PageHelper.getViewDate(rs.getString(COLLAUDO.DATA_INIZIO_OPER)));
			
		bean.setDataCertCollaudo(PageHelper.getViewDate(rs.getString(COLLAUDO.DATA_CERT_COLLAUDO)));
		bean.setDataDelibera(PageHelper.getViewDate(rs.getString(COLLAUDO.DATA_DELIBERA)));
		bean.setEsitoCollaudo(rs.getString(COLLAUDO.ESITO_COLLAUDO));
		bean.setImpFinaleLavoriStr(PageHelper.formattaImporto(rs.getBigDecimal(COLLAUDO.IMP_FINALE_LAVORI)));
		bean.setImpFinaleLavori(rs.getBigDecimal(COLLAUDO.IMP_FINALE_LAVORI));
		bean.setImpFinaleServiziStr(PageHelper.formattaImporto(rs.getBigDecimal(COLLAUDO.IMP_FINALE_SERVIZI)));
		bean.setImpFinaleServizi(rs.getBigDecimal(COLLAUDO.IMP_FINALE_SERVIZI));
		bean.setImpFinaleFornitStr(PageHelper.formattaImporto(rs.getBigDecimal(COLLAUDO.IMP_FINALE_FORNIT)));
		bean.setImpFinaleFornit(rs.getBigDecimal(COLLAUDO.IMP_FINALE_FORNIT));
		bean.setImpFinaleSicurStr(PageHelper.formattaImporto(rs.getBigDecimal(COLLAUDO.IMP_FINALE_SICUR)));
		bean.setImpFinaleSicur(rs.getBigDecimal(COLLAUDO.IMP_FINALE_SICUR));
		bean.setImpProgettazioneStr(PageHelper.formattaImporto(rs.getBigDecimal(COLLAUDO.IMP_PROGETTAZIONE)));
		bean.setImpProgettazione(rs.getBigDecimal(COLLAUDO.IMP_PROGETTAZIONE));
		bean.setImpDisposizioneStr(PageHelper.formattaImporto(rs.getBigDecimal(COLLAUDO.IMP_DISPOSIZIONE)));
		bean.setImpDisposizione(rs.getBigDecimal(COLLAUDO.IMP_DISPOSIZIONE));
		bean.setAmmNumDefinite(rs.getInt(COLLAUDO.AMM_NUM_DEFINITE));
		bean.setAmmNumDaDef(rs.getInt(COLLAUDO.AMM_NUM_DADEF));
		bean.setAmmImportoRichStr(PageHelper.formattaImporto(rs.getBigDecimal(COLLAUDO.AMM_IMPORTO_RICH)));
		bean.setAmmImportoRich(rs.getBigDecimal(COLLAUDO.AMM_IMPORTO_RICH));
		bean.setAmmImportoDefStr(PageHelper.formattaImporto(rs.getBigDecimal(COLLAUDO.AMM_IMPORTO_DEF)));
		bean.setAmmImportoDef(rs.getBigDecimal(COLLAUDO.AMM_IMPORTO_DEF));
		bean.setArbNumDefinite(rs.getInt(COLLAUDO.ARB_NUM_DEFINITE));
		bean.setArbNumDaDef(rs.getInt(COLLAUDO.ARB_NUM_DADEF));
		bean.setArbImportoRichStr(PageHelper.formattaImporto(rs.getBigDecimal(COLLAUDO.ARB_IMPORTO_RICH)));
		bean.setArbImportoRich(rs.getBigDecimal(COLLAUDO.ARB_IMPORTO_RICH));
		bean.setArbImportoDefStr(PageHelper.formattaImporto(rs.getBigDecimal(COLLAUDO.ARB_IMPORTO_DEF)));
		bean.setArbImportoDef(rs.getBigDecimal(COLLAUDO.ARB_IMPORTO_DEF));
		bean.setGiuNumDefinite(rs.getInt(COLLAUDO.GIU_NUM_DEFINITE));
		bean.setGiuNumDaDef(rs.getInt(COLLAUDO.GIU_NUM_DADEF));
		bean.setGiuImportORichStr(PageHelper.formattaImporto(rs.getBigDecimal(COLLAUDO.GIU_IMPORTO_RICH)));
		bean.setGiuImportORich(rs.getBigDecimal(COLLAUDO.GIU_IMPORTO_RICH));
		bean.setGiuImportoDefStr(PageHelper.formattaImporto(rs.getBigDecimal(COLLAUDO.GIU_IMPORTO_DEF)));
		bean.setGiuImportoDef(rs.getBigDecimal(COLLAUDO.GIU_IMPORTO_DEF));
		bean.setTraNumDefinite(rs.getInt(COLLAUDO.TRA_NUM_DEFINITE));
		bean.setTraNumDaDef(rs.getInt(COLLAUDO.TRA_NUM_DADEF));
		bean.setTraImportoRichStr(PageHelper.formattaImporto(rs.getBigDecimal(COLLAUDO.TRA_IMPORTO_RICH)));
		bean.setTraImportoRich(rs.getBigDecimal(COLLAUDO.TRA_IMPORTO_RICH));
		bean.setTraImportoDefStr(PageHelper.formattaImporto(rs.getBigDecimal(COLLAUDO.TRA_IMPORTO_DEF)));
		bean.setTraImportoDef(rs.getBigDecimal(COLLAUDO.TRA_IMPORTO_DEF));
		bean.setDescrizioneStato(rs.getString(STATI_SCHEDA.DESCRIZIONE));
		bean.setFlagLavoriEstesi(rs.getString(COLLAUDO.LAVORI_ESTESI));
		BigDecimal sub = new BigDecimal(0); 
		if(rs.getBigDecimal(COLLAUDO.IMP_FINALE_LAVORI) != null)
			sub = sub.add(rs.getBigDecimal(COLLAUDO.IMP_FINALE_LAVORI));
		if(rs.getBigDecimal(COLLAUDO.IMP_FINALE_SERVIZI) != null)
				sub = sub.add(rs.getBigDecimal(COLLAUDO.IMP_FINALE_SERVIZI));
		if(rs.getBigDecimal(COLLAUDO.IMP_FINALE_FORNIT) != null)
			sub = sub.add(rs.getBigDecimal(COLLAUDO.IMP_FINALE_FORNIT));
		bean.setSubStr(PageHelper.formattaImporto(sub));
		BigDecimal sub2 = new BigDecimal(0);
		sub2 = sub2.add(sub);
		if(rs.getBigDecimal(COLLAUDO.IMP_FINALE_SICUR) != null)
			sub2 = sub2.add(rs.getBigDecimal(COLLAUDO.IMP_FINALE_SICUR));
		if(rs.getBigDecimal(COLLAUDO.IMP_PROGETTAZIONE) != null)
			sub2 = sub2.add(rs.getBigDecimal(COLLAUDO.IMP_PROGETTAZIONE));
		bean.setSub2Str(PageHelper.formattaImporto(sub2));
		BigDecimal finale = new BigDecimal(0);
		finale = finale.add(sub2);
		if(rs.getBigDecimal(COLLAUDO.IMP_DISPOSIZIONE) != null)
			finale = finale.add(rs.getBigDecimal(COLLAUDO.IMP_DISPOSIZIONE));
		bean.setFinaleStr(PageHelper.formattaImporto(finale));
		bean.setIdLocale(rs.getString(COLLAUDO.ID_SCHEDA_LOCALE));
		/* setting nested bean */
		ResponsabileCollManager rcm = new ResponsabileCollManager(activeConnection,logger);
		bean.setRespBean(rcm.load(bean.getIdCollaudo(), bean.getDataIniColl(), ignoraStato));
		/* end */
//		logger.debug("Collaudo manager: "+ObjectIntrospector.propertiesInfo(CollaudoBean.class, bean));
		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadCollaudo#loadByIdLocale(java.lang.String, java.lang.String)
	 */
	public CollaudoBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CollaudoBean cb = new CollaudoBean();
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_COLLAUDO + WHERE_IDLOCALE);
			int index = 1;
			long idAggiudicazioneL = Long.parseLong(idAggiudicazione);
			stmt.setLong(index++, idAggiudicazioneL);
			stmt.setString(index++, idLocale);
			rs = stmt.executeQuery();
			if(rs.next()){
				fillBean(rs, cb, false);
			}
		}finally{
			close(rs,stmt);
		}
		
		return cb;
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadCollaudo#loadByIdSimog(long)
	 */
	public CollaudoBean loadByIdSimog(long idSimog) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CollaudoBean cb = new CollaudoBean();
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_COLLAUDO + WHERE_IDSIMOG);
			int index = 1;
			stmt.setLong(index++, idSimog);
			rs = stmt.executeQuery();
			if(rs.next()){
				fillBean(rs, cb, false);
			}
		}finally{
			close(rs,stmt);
		}
		return cb;
	}

//	public boolean annullaByAggiudicazione(long idAggiudicazione,Timestamp dataInizioAggiudicazione, String cfUtente)throws SQLException {
//
//		CollaudoBean collaudo = this.load(idAggiudicazione, dataInizioAggiudicazione);
//		if(collaudo.getIdCollaudo() > 0){
//			ResponsabileCollManager respCollManager = new ResponsabileCollManager(activeConnection,logger);
//			respCollManager.annulla(collaudo.getIdCollaudo(), collaudo.getDataIniColl());
//			return _annulla(collaudo.getIdCollaudo(), collaudo.getDataIniColl(), cfUtente);
//		}return false;
//	}	
}
