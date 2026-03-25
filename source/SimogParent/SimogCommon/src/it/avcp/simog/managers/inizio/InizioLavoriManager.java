package it.avcp.simog.managers.inizio;

import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avcp.simog.managers.comportamento.annullamento.IAnnullamento;
import it.avcp.simog.managers.comportamento.caricamento.ILoadInizio;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.INIZIO_LAVORI;
import it.avlp.simog.db.generated.STATI_SCHEDA;
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
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Classe che si occupa della gestione dei dati relativi agli inizio lavori
 *
 */
public class InizioLavoriManager extends AccessiDB implements IAnnullamento,ILoadInizio {
	
	public static String CLAZZ = "InizioLavoriManager";
	
	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public InizioLavoriManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	private final String SELECT_INIZIO_LAVORI = 
		"SELECT " + INIZIO_LAVORI.TABLE_NAME + ".*," +
		STATI_SCHEDA.T_DESCRIZIONE+ " + " +  buildRichAnnQuery(IdentificativoSchede.TAB_INIZIO_LAVORI, INIZIO_LAVORI.T_ID_INIZIO,null) 
		+ " AS "+STATI_SCHEDA.DESCRIZIONE +
		" FROM " + 
		INIZIO_LAVORI.TABLE_NAME + ", " +
		STATI_SCHEDA.TABLE_NAME;
	
	/**
	 * metodo per il recupero del bean inizio lavori associato all'aggiudicazione di cui id
	 * 
	 * @param idAggiudicazione Long
	 * @param dataIniAggiudicazione Timestamp
	 * @param ignoraStato TODO
	 * @return InizioLavoriBean
	 * @throws SQLException
	 */
	public InizioLavoriBean load(Long idAggiudicazione, Timestamp dataIniAggiudicazione) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		InizioLavoriBean ilb = new InizioLavoriBean();
		try{
			stmt = activeConnection.prepareStatement(SELECT_INIZIO_LAVORI + WHERE_STANDARD);
			int index = 1;
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataIniAggiudicazione);
			rs = stmt.executeQuery();
			while(rs.next()){
				fillBean(rs, ilb);
				
			}
		}finally{
			close(rs,stmt);
		}
		return ilb;
	}
	
	/**
	 * metodo per il recupero del bean inizio lavori per id senza controllo stato
	 * 
	 * @param idAggiudicazione Long
	 * @param dataIniAggiudicazione Timestamp
	 * @param ignoraStato TODO
	 * @return InizioLavoriBean
	 * @throws SQLException
	 */
	public InizioLavoriBean loadById(Long idInizio, Timestamp dataInizio) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		InizioLavoriBean ilb = new InizioLavoriBean();
		try{
			stmt = activeConnection.prepareStatement(SELECT_INIZIO_LAVORI + WHERE_ID);
			int index = 1;
			stmt.setLong(index++, idInizio);
			stmt.setTimestamp(index++, dataInizio);
			rs = stmt.executeQuery();
			if(rs.next()){
				fillBean(rs, ilb);
			}
		}finally{
			close(rs,stmt);
		}
		return ilb;
	}
	private final String DELETE_RECORD_INIZIO_LAVORI = 
		"DELETE FROM " + INIZIO_LAVORI.TABLE_NAME 
		+ " WHERE " + INIZIO_LAVORI.ID_INIZIO + " = ? "
		+ " AND " + INIZIO_LAVORI.DATA_INIZIO_INIZIO + " = ? ";
	


	private String QUERY_INSERT_INIZIO_LAVORI = 
	"INSERT INTO "+INIZIO_LAVORI.TABLE_NAME+" ("
		+INIZIO_LAVORI.DATA_INIZIO_INIZIO 
		+ ", " + INIZIO_LAVORI.ID_PUBBLICAZIONE 
		+ ", " + INIZIO_LAVORI.DATA_INIZIO_PUBB
		+ ", " + INIZIO_LAVORI.ID_AGGIUDICAZIONE
		+ ", " + INIZIO_LAVORI.DATA_INIZIO_AGGIUDICAZIONE
		+ ", " + INIZIO_LAVORI.ID_STATO
		+ ", " + INIZIO_LAVORI.DATA_STIPULA
		+ ", " + INIZIO_LAVORI.DATA_ESECUTIVITA
		+ ", " + INIZIO_LAVORI.IMPORTO_CAUZ
		+ ", " + INIZIO_LAVORI.DATA_INI_PROG_ESEC
		+ ", " + INIZIO_LAVORI.DATA_APP_PROG_ESEC
		+ ", " + INIZIO_LAVORI.FLAG_FRAZIONATA
		+ ", " + INIZIO_LAVORI.DATA_VERBALE_CONS
		+ ", " + INIZIO_LAVORI.DATA_VERBALE_DEF
		+ ", " + INIZIO_LAVORI.FLAG_RISERVA
		+ ", " + INIZIO_LAVORI.DATA_VERB_INIZIO
		+ ", " + INIZIO_LAVORI.DATA_TERMINE
		+ ", " + INIZIO_LAVORI.ID_SCHEDA_LOCALE
		+")"
		+" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	

	private String QUERY_UPDATE_INIZIO_LAVORI = 
		"UPDATE "+INIZIO_LAVORI.TABLE_NAME+ " SET "
		
		+INIZIO_LAVORI.ID_STATO+ " = ? "
		+ ", " + INIZIO_LAVORI.DATA_FINE_INIZIO + " = ? "
		+ ", " + INIZIO_LAVORI.DATA_STIPULA + " = ? "
		+ ", " + INIZIO_LAVORI.DATA_ESECUTIVITA + " = ? "
		+ ", " + INIZIO_LAVORI.IMPORTO_CAUZ + " = ? "
		+ ", " + INIZIO_LAVORI.DATA_INI_PROG_ESEC + " = ? "
		+ ", " + INIZIO_LAVORI.DATA_APP_PROG_ESEC + " = ? "
		+ ", " + INIZIO_LAVORI.FLAG_FRAZIONATA + " = ? "
		+ ", " + INIZIO_LAVORI.DATA_VERBALE_CONS + " = ? "
		+ ", " + INIZIO_LAVORI.DATA_VERBALE_DEF + " = ? "
		+ ", " + INIZIO_LAVORI.FLAG_RISERVA + " = ? "
		+ ", " + INIZIO_LAVORI.DATA_VERB_INIZIO + " = ? "
		+ ", " + INIZIO_LAVORI.DATA_TERMINE + " = ? "
		
		+ " WHERE "
		+ INIZIO_LAVORI.ID_INIZIO + "= ?"
		+ " AND " + INIZIO_LAVORI.DATA_INIZIO_INIZIO + "= ?";
	
	// PP fix controllo stato scheda
	private final String WHERE_CONF = " AND (" + INIZIO_LAVORI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING
		 + " OR " + INIZIO_LAVORI.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING	+ " ) ";
	private final String WHERE_DEF = " AND " + INIZIO_LAVORI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING;
	    

	
	
	/**
	 * metodo per la cancellazione di un record
	 * 
	 * @param idInizioLavori long
	 * @param dataInizioLavori Timestamp
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int deleteRecord(long idInizioLavori, Timestamp dataInizioLavori)throws SQLException{
		PreparedStatement stmt = null;
		int index = 1;
		try{
			
			
			stmt = activeConnection.prepareStatement(DELETE_RECORD_INIZIO_LAVORI);
			stmt.setLong(index++, idInizioLavori);
			stmt.setTimestamp(index++, dataInizioLavori);
			return stmt.executeUpdate();
		}finally{
			close(null,stmt);
		}
	}
	



	/**
	 * metodo per l'inserimento di un inizio lavori
	 * 
	 * @param lavoriBean InizioLavoriBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public void insert(InizioLavoriBean lavoriBean, String cfUtente)throws SQLException{
		logger.debug("[dati inizio]problema con le pubblicazioni: "+ObjectIntrospector.propertiesInfo(PubblicazioneBean.class,lavoriBean.getPubblicazione()));
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try{
			
			
			stmt = activeConnection.prepareStatement(createInsertQuery(QUERY_INSERT_INIZIO_LAVORI,INIZIO_LAVORI.ID_INIZIO));
			
			lavoriBean.setDataInizioLavori(getNow());
			stmt.setTimestamp(index++, lavoriBean.getDataInizioLavori());
			stmt.setLong(index++, lavoriBean.getPubblicazione().getIdPubblicazione());
			stmt.setTimestamp(index++, lavoriBean.getPubblicazione().getDataInizioPubblicazione());
			stmt.setLong(index++, lavoriBean.getIdAggiudicazione());
			stmt.setTimestamp(index++, lavoriBean.getDataInizioAggiudicazione());
			stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE);
			stmt.setString(index++, PageHelper.formatDateOrNull(lavoriBean.getDataStipula()));
			stmt.setString(index++, PageHelper.formatDateOrNull(lavoriBean.getDataEsecutivita()));
			stmt.setBigDecimal(index++, lavoriBean.getImportoCauzione());
			stmt.setString(index++, PageHelper.formatDateOrNull(lavoriBean.getDataIniProgEsec()));
			stmt.setString(index++, PageHelper.formatDateOrNull(lavoriBean.getDataAppProgEsec()));
			stmt.setString(index++, lavoriBean.getFlagFrazionata());
			stmt.setString(index++, PageHelper.formatDateOrNull(lavoriBean.getDataVerbaleCons()));
			stmt.setString(index++, PageHelper.formatDateOrNull(lavoriBean.getDataVerbaleDef()));
			stmt.setString(index++, lavoriBean.getFlagRiserva());
			stmt.setString(index++, PageHelper.formatDateOrNull(lavoriBean.getDataVerbaleInizio()));
			stmt.setString(index++, PageHelper.formatDateOrNull(lavoriBean.getDataTermine()));
			
			if(lavoriBean.getIdLocale() == null){
				stmt.setNull(index++, Types.VARCHAR);
			}else{
				stmt.setString(index++, lavoriBean.getIdLocale());
			}
			
			
			
			if(stmt.execute()){
				rs = stmt.getResultSet();
				rs.next();
				lavoriBean.setIdInizioLavori(rs.getLong(INIZIO_LAVORI.ID_INIZIO));
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(lavoriBean.getIdInizioLavori());
				attributiChiave.add(lavoriBean.getDataInizioLavori());
				LogBloccoDatiManager.loggingINSERT(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_INIZIO_LAVORI, attributiChiave);
			}
			
		}finally{
			close(rs, stmt);
			
		}
	}


	/**
	 * metodo per la conferma della fase di inizio lavori
	 * 
	 * @param lavoriBean InizioLavoriBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public int confirm(InizioLavoriBean lavoriBean, String cfUtente)throws SQLException{
		logger.debug("CONFIRM: " + ObjectIntrospector.propertiesInfo(InizioLavoriBean.class, lavoriBean));
		return update(lavoriBean,cfUtente, true);
	}
	
	/**
	 * metodo per il salvataggio della fase di aggiudicazione
	 * 
	 * @param lavoriBean InizioLavoriBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public int save(InizioLavoriBean lavoriBean,String cfUtente) throws SQLException{
		logger.debug("SAVE: " + ObjectIntrospector.propertiesInfo(InizioLavoriBean.class, lavoriBean));
		return update(lavoriBean,cfUtente, false);
	}
	
	private int update(InizioLavoriBean lavoriBean,String cfUtente, boolean conferma)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try{
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_INIZIO_LAVORI + ((SimogFlags.isFlagNoDate()) ? WHERE_CONF : WHERE_DEF));
			//confermo o aggiorno...
			if(conferma){
				stmt.setInt(index++, StatiScheda.CONFERMATO);
				stmt.setTimestamp(index++, getNow());
			}else{
				stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE);
				stmt.setNull(index++, Types.TIMESTAMP);
			}
			
			stmt.setString(index++, PageHelper.formatDateOrNull(lavoriBean.getDataStipula()));
			stmt.setString(index++, PageHelper.formatDateOrNull(lavoriBean.getDataEsecutivita()));
			stmt.setBigDecimal(index++, lavoriBean.getImportoCauzione());
			stmt.setString(index++, PageHelper.formatDateOrNull(lavoriBean.getDataIniProgEsec()));
			stmt.setString(index++, PageHelper.formatDateOrNull(lavoriBean.getDataAppProgEsec()));
			stmt.setString(index++, lavoriBean.getFlagFrazionata());
			stmt.setString(index++, PageHelper.formatDateOrNull(lavoriBean.getDataVerbaleCons()));
			stmt.setString(index++, PageHelper.formatDateOrNull(lavoriBean.getDataVerbaleDef()));
			stmt.setString(index++, lavoriBean.getFlagRiserva());
			stmt.setString(index++, PageHelper.formatDateOrNull(lavoriBean.getDataVerbaleInizio()));
			stmt.setString(index++, PageHelper.formatDateOrNull(lavoriBean.getDataTermine()));
			
			stmt.setLong(index++, lavoriBean.getIdInizioLavori());
			stmt.setTimestamp(index++, lavoriBean.getDataInizioLavori());
			int num = stmt.executeUpdate();
			
			List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(lavoriBean.getIdInizioLavori());
			attributiChiave.add(lavoriBean.getDataInizioLavori());
			if(conferma)
				LogBloccoDatiManager.loggingCONFIRM(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_INIZIO_LAVORI, attributiChiave);
			else
				LogBloccoDatiManager.loggingUPDATE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_INIZIO_LAVORI, attributiChiave);
			return num;
		}finally{
			close(rs, stmt);
			
		}
	}
	

//	
//	/***********************************************************************************************************************************
//	 ************************************RICHIESTA ANNULLAMENTO*************************************************************************
//	 */
//	
	/**
	 * metodo per la storicizzazione di un record
	 * 
	 * @param idInizioLavori long
	 * @param dataInizioLavori Timestamp
	 * @param oldDataInizioPubblicazione Timestamp
	 * @param newDataInizioPubblicazione Timestamp
	 * @return Timestamp - nuova data lavori
	 * @throws SQLException
	 */
	public Timestamp copyRecord(long idInizioLavori, Timestamp dataInizioLavori, Timestamp oldDataInizioPubblicazione, Timestamp newDataInizioPubblicazione)throws SQLException{

		String QUERY_SELECT_DATA_FINE = "SELECT " + INIZIO_LAVORI.DATA_FINE_INIZIO
		+ " FROM " + INIZIO_LAVORI.TABLE_NAME
		+ " WHERE " + INIZIO_LAVORI.ID_INIZIO + " = ? "
		+ " AND " + INIZIO_LAVORI.DATA_INIZIO_INIZIO + " = ?"
		+ " AND " + INIZIO_LAVORI.ID_STATO + " = " + StatiScheda.CONFERMATO;
		
		
		String QUERY_COPY_RECORD_LAVORI = 
		
			"INSERT INTO "+INIZIO_LAVORI.TABLE_NAME+" ("
			+ INIZIO_LAVORI.ID_INIZIO
			+ ", " + INIZIO_LAVORI.ID_PUBBLICAZIONE 
			+ ", " + INIZIO_LAVORI.ID_AGGIUDICAZIONE
			+ ", " + INIZIO_LAVORI.DATA_INIZIO_AGGIUDICAZIONE
			+ ", " + INIZIO_LAVORI.DATA_STIPULA
			+ ", " + INIZIO_LAVORI.DATA_ESECUTIVITA
			+ ", " + INIZIO_LAVORI.IMPORTO_CAUZ
			+ ", " + INIZIO_LAVORI.DATA_INI_PROG_ESEC
			+ ", " + INIZIO_LAVORI.DATA_APP_PROG_ESEC
			+ ", " + INIZIO_LAVORI.FLAG_FRAZIONATA
			+ ", " + INIZIO_LAVORI.DATA_VERBALE_CONS
			+ ", " + INIZIO_LAVORI.DATA_VERBALE_DEF
			+ ", " + INIZIO_LAVORI.FLAG_RISERVA
			+ ", " + INIZIO_LAVORI.DATA_VERB_INIZIO
			+ ", " + INIZIO_LAVORI.DATA_TERMINE
			+ ", " + INIZIO_LAVORI.ID_SCHEDA_LOCALE
			
			+ ", " + INIZIO_LAVORI.DATA_INIZIO_INIZIO
			+ ", " + INIZIO_LAVORI.DATA_FINE_INIZIO
			+ ", " + INIZIO_LAVORI.DATA_INIZIO_PUBB
			+ ", " + INIZIO_LAVORI.ID_STATO +" ) "
			+" SELECT "
			+ INIZIO_LAVORI.ID_INIZIO
			+ ", " + INIZIO_LAVORI.ID_PUBBLICAZIONE 
			+ ", " + INIZIO_LAVORI.ID_AGGIUDICAZIONE
			+ ", " + INIZIO_LAVORI.DATA_INIZIO_AGGIUDICAZIONE
			+ ", " + INIZIO_LAVORI.DATA_STIPULA
			+ ", " + INIZIO_LAVORI.DATA_ESECUTIVITA
			+ ", " + INIZIO_LAVORI.IMPORTO_CAUZ
			+ ", " + INIZIO_LAVORI.DATA_INI_PROG_ESEC
			+ ", " + INIZIO_LAVORI.DATA_APP_PROG_ESEC
			+ ", " + INIZIO_LAVORI.FLAG_FRAZIONATA
			+ ", " + INIZIO_LAVORI.DATA_VERBALE_CONS
			+ ", " + INIZIO_LAVORI.DATA_VERBALE_DEF
			+ ", " + INIZIO_LAVORI.FLAG_RISERVA
			+ ", " + INIZIO_LAVORI.DATA_VERB_INIZIO
			+ ", " + INIZIO_LAVORI.DATA_TERMINE
			+ ", " + INIZIO_LAVORI.ID_SCHEDA_LOCALE
			+", ?"
			+", ?"
			+", ?"
			+", ?"
	        + " FROM " + INIZIO_LAVORI.TABLE_NAME
	        + " WHERE "
	        + INIZIO_LAVORI.ID_INIZIO + " = ?"
	        + " AND " + INIZIO_LAVORI.DATA_INIZIO_INIZIO + " = ?";
	        
	
		
		
		String UPDATE_STATO_OLD_RECORD_LAVORI = 
			"UPDATE " + INIZIO_LAVORI.TABLE_NAME + " SET "
			+ INIZIO_LAVORI.ID_STATO + " = " +StatiScheda.IN_DEFINIZIONE + " "
			+ ", " + INIZIO_LAVORI.DATA_INIZIO_INIZIO + " = ?"
			+ ", " + INIZIO_LAVORI.DATA_INIZIO_PUBB + " = ?"
			+ ", " + INIZIO_LAVORI.DATA_FINE_INIZIO + " = ?"			
			+ " WHERE "
			+ INIZIO_LAVORI.ID_INIZIO + " = ? AND  "
			+ INIZIO_LAVORI.DATA_INIZIO_INIZIO + " = ? AND "
			+ INIZIO_LAVORI.ID_STATO + " = " + StatiScheda.CONFERMATO;
		
		PreparedStatement getDataFine = null;
		PreparedStatement crLavStmt = null;
		PreparedStatement upLavStmt = null;
		Timestamp dataFine = null;
	
		Timestamp nuovaDataLav = null;
		int index = 1;
		ResultSet rs = null;
		try{
			getDataFine = activeConnection.prepareStatement(QUERY_SELECT_DATA_FINE);
			getDataFine.setLong(index++, idInizioLavori);
			getDataFine.setTimestamp(index++, dataInizioLavori);
			rs = getDataFine.executeQuery();
			if(rs.next()){
				dataFine = rs.getTimestamp(INIZIO_LAVORI.DATA_FINE_INIZIO);
				logger.debug("data fine: "+dataFine);
				
			try{
				//upLavStmt = activeConnection.prepareStatement(UPDATE_STATO_OLD_RECORD_LAVORI);
				index = 1;
				//update stato old record lavori
				nuovaDataLav = getNow();
				upLavStmt = activeConnection.prepareStatement(UPDATE_STATO_OLD_RECORD_LAVORI);
				//upLavStmt.setInt(index++,StatiScheda.IN_DEFINIZIONE);
				upLavStmt.setTimestamp(index++, nuovaDataLav);
				upLavStmt.setTimestamp(index++, newDataInizioPubblicazione);
				upLavStmt.setNull(index++, Types.TIMESTAMP);				
				upLavStmt.setLong(index++, idInizioLavori);
				upLavStmt.setTimestamp(index++, dataInizioLavori);
				upLavStmt.execute();
			}catch(Throwable t){t.printStackTrace();throw (SQLException)t;}
				
				
				crLavStmt = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD_LAVORI,INIZIO_LAVORI.TABLE_NAME));
				index = 1;
				// copy record inizio lavori
				
				crLavStmt.setTimestamp(index++, dataInizioLavori);
				
				crLavStmt.setTimestamp(index++, dataFine);
				
				
				crLavStmt.setTimestamp(index++, oldDataInizioPubblicazione);
				crLavStmt.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA);
				crLavStmt.setLong(index++, idInizioLavori);
				crLavStmt.setTimestamp(index++, nuovaDataLav);
				crLavStmt.execute();
				return nuovaDataLav;
			
		
			}
			return null;
		}/*catch(Exception e){	
			e.printStackTrace();
			throw new SQLException(e.getMessage());
			
		}*/finally{
			close(rs, getDataFine);
			close(null, crLavStmt);
			close(null, upLavStmt);
		}
			
		
		
	}
	

	/**
	 * metodo per la verifica di esistenza di una fase inizio lavori
	 * 
	 * @param idInizioLavori long
	 * @param dataInInizioLavori Timestamp
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean existInizioLavori(long idInizioLavori, Timestamp dataInInizioLavori) throws SQLException{

		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM " + INIZIO_LAVORI.TABLE_NAME + " WHERE " + 
						INIZIO_LAVORI.ID_INIZIO + " = ? AND " + 
						INIZIO_LAVORI.DATA_INIZIO_INIZIO + " = ?";
		
		try{
			stmt = activeConnection.prepareStatement(query);
			stmt.setLong(1, idInizioLavori);
			stmt.setTimestamp(2, dataInInizioLavori);
			rs = stmt.executeQuery();
			return rs.next();
		}finally{
			close(rs, stmt);
		}
	}
	
	
	/** TICKET ALM #3437
	 * metodo per la verifica di esistenza di una fase inizio lavori a partire dall'aggiudicazione
	 * 
	 * @param idAgg long
	 * @param dataInInizioAgg Timestamp
	 * @param confirmed
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean existInizioLavoriByAgg(long idAgg, Timestamp dataInInizioAgg, boolean confirmed) throws SQLException{

		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM " + INIZIO_LAVORI.TABLE_NAME + " WHERE " + 
						INIZIO_LAVORI.ID_AGGIUDICAZIONE + " = ? AND " + 
						INIZIO_LAVORI.DATA_INIZIO_AGGIUDICAZIONE + " = ? AND " +
						INIZIO_LAVORI.ID_STATO + " = "+(confirmed ? StatiScheda.CONFERMATO_STRING : StatiScheda.IN_DEFINIZIONE_STRING);

		try{
			stmt = activeConnection.prepareStatement(query);
			stmt.setLong(1, idAgg);
			stmt.setTimestamp(2, dataInInizioAgg);
		
			rs = stmt.executeQuery();
			return rs.next();
		}finally{
			close(rs, stmt);
		}
	}
	
	
	public static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO = 
		"UPDATE "+INIZIO_LAVORI.TABLE_NAME+
		" SET " + INIZIO_LAVORI.ID_STATO + " = ?,"
		+ INIZIO_LAVORI.DATA_FINE_INIZIO + " = " + buildGetDate()+	
		" WHERE "+INIZIO_LAVORI.ID_INIZIO + " = ?"+
		" AND "+INIZIO_LAVORI.DATA_INIZIO_INIZIO + " = ?";
	
	
	/**
	 * metodo per l'aggionramento di un record
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
		int index = 1;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);

	
			stmt.setString(index++, stato_scheda);
			stmt.setLong(index++, idInizioLavori);
			stmt.setTimestamp(index++,dataInizioLavori);
			numRow = stmt.executeUpdate();
		
		} finally {
			close(rs, stmt);
		}
		return numRow;
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
		InizioLavoriBean inizioLavoriBean = loadByIdSimog(idSimog);
		
		if(inizioLavoriBean.getIdInizioLavori() > 0){
			return _annulla(inizioLavoriBean.getIdInizioLavori(), inizioLavoriBean.getDataInizioLavori(),cfUtente);
		}
		return false;
		
	}




	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean annulla(String idLocale, String rifSimog, String cfUtente) throws SQLException {
		InizioLavoriBean inizioLavoriBean = loadByIdLocale(idLocale, rifSimog);
		
		if(inizioLavoriBean.getIdInizioLavori() > 0){
			return _annulla(inizioLavoriBean.getIdInizioLavori(), inizioLavoriBean.getDataInizioLavori(),cfUtente);
		}
		return false;
		
	}


	/**
	 * @param idInizioLavori
	 * @param dataInizioInizioLavori
	 * @throws SQLException
	 */
	private boolean _annulla(long idInizioLavori, Timestamp dataInizioInizioLavori, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_INIZIO_LAVORI);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idInizioLavori);
			stmt.setTimestamp(index++, dataInizioInizioLavori);
			someRowAffected = stmt.executeUpdate() > 0;
			if(someRowAffected){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idInizioLavori);
				attributiChiave.add(dataInizioInizioLavori);
					LogBloccoDatiManager.loggingDELETE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_INIZIO_LAVORI, attributiChiave);
			}
			return someRowAffected;
		}finally {
			close(null,stmt);
		}		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadInizio#fillBean(java.sql.ResultSet, it.avlp.simog.beans.inizio.InizioLavoriBean)
	 */
	public void fillBean(ResultSet rs, InizioLavoriBean inizioLavoriBean) throws SQLException {
		inizioLavoriBean.setDataAppProgEsec(PageHelper.getViewDate(rs.getString(INIZIO_LAVORI.DATA_APP_PROG_ESEC)));
		inizioLavoriBean.setDataEsecutivita(PageHelper.getViewDate(rs.getString(INIZIO_LAVORI.DATA_ESECUTIVITA)));
		inizioLavoriBean.setDataFineLavori(rs.getTimestamp(INIZIO_LAVORI.DATA_FINE_INIZIO));
		inizioLavoriBean.setDataIniProgEsec(PageHelper.getViewDate(rs.getString(INIZIO_LAVORI.DATA_INI_PROG_ESEC)));
		inizioLavoriBean.setDataInizioAggiudicazione(rs.getTimestamp(INIZIO_LAVORI.DATA_INIZIO_AGGIUDICAZIONE));
		inizioLavoriBean.setDataInizioLavori(rs.getTimestamp(INIZIO_LAVORI.DATA_INIZIO_INIZIO));
		inizioLavoriBean.setDataStipula(PageHelper.getViewDate(rs.getString(INIZIO_LAVORI.DATA_STIPULA)));
		inizioLavoriBean.setDataTermine(PageHelper.getViewDate(rs.getString(INIZIO_LAVORI.DATA_TERMINE)));
		inizioLavoriBean.setDataVerbaleCons(PageHelper.getViewDate(rs.getString(INIZIO_LAVORI.DATA_VERBALE_CONS)));
		inizioLavoriBean.setDataVerbaleDef(PageHelper.getViewDate(rs.getString(INIZIO_LAVORI.DATA_VERBALE_DEF)));
		inizioLavoriBean.setDataVerbaleInizio(PageHelper.getViewDate(rs.getString(INIZIO_LAVORI.DATA_VERB_INIZIO)));
		inizioLavoriBean.setFlagFrazionata(rs.getString(INIZIO_LAVORI.FLAG_FRAZIONATA));
		inizioLavoriBean.setFlagRiserva(rs.getString(INIZIO_LAVORI.FLAG_RISERVA));
		inizioLavoriBean.setIdAggiudicazione(rs.getLong(INIZIO_LAVORI.ID_AGGIUDICAZIONE));
		inizioLavoriBean.setIdInizioLavori(rs.getLong(INIZIO_LAVORI.ID_INIZIO));
		inizioLavoriBean.setIdStato(rs.getInt(INIZIO_LAVORI.ID_STATO));
		inizioLavoriBean.setImportoCauzione(rs.getBigDecimal(INIZIO_LAVORI.IMPORTO_CAUZ));	
		inizioLavoriBean.setDescrizioneStato(rs.getString(STATI_SCHEDA.DESCRIZIONE));
		inizioLavoriBean.setIdLocale(rs.getString(INIZIO_LAVORI.ID_SCHEDA_LOCALE));
		//setting inner bean pubblicazione
		PubblicazioneBean pubblicazioneBean = new PubblicazioneManager(activeConnection,logger).getPubblicazione(rs.getLong(INIZIO_LAVORI.ID_PUBBLICAZIONE),rs.getTimestamp(INIZIO_LAVORI.DATA_INIZIO_PUBB));
//		logger.debug(""+rs.getLong(INIZIO_LAVORI.ID_PUBBLICAZIONE)+","+rs.getTimestamp(INIZIO_LAVORI.DATA_INIZIO_PUBB));
//		logger.debug(ObjectIntrospector.propertiesInfo(PubblicazioneBean.class, pubblicazioneBean));
		inizioLavoriBean.setPubblicazione(pubblicazioneBean);
		
	}




	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadInizio#loadByIdLocale(java.lang.String, java.lang.String)
	 */
	public InizioLavoriBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		InizioLavoriBean ilb = new InizioLavoriBean();
		try{
			stmt = activeConnection.prepareStatement(SELECT_INIZIO_LAVORI + WHERE_IDLOCALE);
			int index = 1;
			stmt.setString(index++, idLocale);
			long idAggiudicazioneL = Long.parseLong(idAggiudicazione);
			stmt.setLong(index++, idAggiudicazioneL);			
			rs = stmt.executeQuery();
			if(rs.next()){
				fillBean(rs, ilb);				
			}
		}finally{
			close(rs,stmt);
		}
		return ilb;
	}




	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadInizio#loadByIdSimog(long)
	 */
	public InizioLavoriBean loadByIdSimog(long idSimog) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		InizioLavoriBean ilb = new InizioLavoriBean();
		try{
			stmt = activeConnection.prepareStatement(SELECT_INIZIO_LAVORI + WHERE_IDSIMOG);
			int index = 1;
			stmt.setLong(index++, idSimog);
			rs = stmt.executeQuery();
			if(rs.next()){
				fillBean(rs, ilb);				
			}
		}finally{
			close(rs,stmt);
		}
		return ilb;
	}




//	public boolean annullaByAggiudicazione(long idAggiudicazione,Timestamp dataInizioAggiudicazione, String cfUtente)throws SQLException {
//		InizioLavoriBean iniziobean = this.load(idAggiudicazione, dataInizioAggiudicazione);
//		if(iniziobean.getIdInizioLavori() > 0){
//			PosizAggiudManager posAggManager = new PosizAggiudManager(activeConnection,logger);
//			posAggManager.annulla(iniziobean.getIdInizioLavori(), iniziobean.getDataInizioLavori());
//			ResponsabileInizioManager respIniManager = new ResponsabileInizioManager(activeConnection, logger);
//			respIniManager.annulla(iniziobean.getIdInizioLavori(), iniziobean.getDataInizioLavori());
//			return _annulla(iniziobean.getIdInizioLavori(), iniziobean.getDataInizioLavori(), cfUtente);
//		}return false;
//	}
	

	
	
		
}
