package it.avcp.simog.managers.aggiudicazione;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamento;
import it.avcp.simog.managers.comportamento.caricamento.ILoadPubblicazione;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.PUBBLICAZIONI;
import it.avlp.simog.db.generated.REL_PUBB_AGG;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Classe che si occupa della gestione dei dati relativi alla pubblicazione
 *
 */
public class PubblicazioneManager extends AccessiDB implements IAnnullamento,ILoadPubblicazione {

	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection 
	 * @param logger Logger
	 */
	public PubblicazioneManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}

	private final String QUERY_INSERT_PUBBLICAZIONE = 
	"INSERT INTO "+PUBBLICAZIONI.TABLE_NAME+" ("
	 +PUBBLICAZIONI.DATA_INIZIO_PUBB
		+", "+PUBBLICAZIONI.DATA_ALBO
		+", "+PUBBLICAZIONI.DATA_GUCE
		+", "+PUBBLICAZIONI.DATA_GURI
		+", "+PUBBLICAZIONI.QUOTIDIANI_NAZ
		+", "+PUBBLICAZIONI.QUOTIDIANI_REG
		+", "+PUBBLICAZIONI.PROFILO_COMMITTENTE
		+", "+PUBBLICAZIONI.SITO_MINISTERO_INF_TRASP
		+", "+PUBBLICAZIONI.SITO_OSSERVATORIO_CP
		+", "+PUBBLICAZIONI.ID_STATO
		+", "+PUBBLICAZIONI.DATA_BORE
		+", "+PUBBLICAZIONI.PERIODICI
		//gm nuovo codice pubblicazione bando 3.0
		+", "+PUBBLICAZIONI.NUMERO_GURI
		+", "+PUBBLICAZIONI.NUMERO_GUCE
		+", "+PUBBLICAZIONI.NUMERO_BORE
		+", "+PUBBLICAZIONI.LINK_SITO_COMMITTENTE
		//gm nuovo codice pubblicazione bando 3.0
		+", "+PUBBLICAZIONI.TIPO_OPERAZIONE
		//gm nuovo codice estensione pubblicazione bandi
		+", "+PUBBLICAZIONI.FLAG_BENICULT
		+", "+PUBBLICAZIONI.FLAG_SOSPESO
		+", "+PUBBLICAZIONI.LINK_AFFIDAMENTO_DIRETTO // MEV 34470
		+")"
		+" VALUES (?,?,?,?,?,?,?,?,?,?," +
				  "?,?,?,?,?,?,?,?,?,?)";
	
	private final String DELETE_RECORD_PUBBLICAZIONE = 
		"DELETE FROM " + PUBBLICAZIONI.TABLE_NAME 
		+ " WHERE " + PUBBLICAZIONI.ID_PUBBLICAZIONE + " = ? "
		+ " AND " + PUBBLICAZIONI.DATA_INIZIO_PUBB + " = ? ";
	
	private final String QUERY_UPDATE_PUBBLICAZIONE = 
		"UPDATE "+PUBBLICAZIONI.TABLE_NAME+ " SET "
		
		+PUBBLICAZIONI.DATA_GUCE+ " = ? "
		+", "+PUBBLICAZIONI.DATA_GURI+ " = ? "
		+", "+PUBBLICAZIONI.DATA_ALBO+ " = ? "
		
		+", "+PUBBLICAZIONI.QUOTIDIANI_NAZ+ " = ? "
		+", "+PUBBLICAZIONI.QUOTIDIANI_REG+ " = ? "
		
		+", "+PUBBLICAZIONI.PROFILO_COMMITTENTE+ " = ? "
		+", "+PUBBLICAZIONI.SITO_MINISTERO_INF_TRASP + " = ? "
		+", "+PUBBLICAZIONI.SITO_OSSERVATORIO_CP+ " = ? "
		+", "+PUBBLICAZIONI.ID_STATO+ " = ? "
		+", "+PUBBLICAZIONI.DATA_FINE_PUBB+ " = ? "
		+", "+PUBBLICAZIONI.DATA_BORE+ " = ? "
		+", "+PUBBLICAZIONI.PERIODICI+ " = ? "
		//gm nuovo codice estensione pubblicazione bandi
		+", "+PUBBLICAZIONI.FLAG_BENICULT+ " = ? "
		+", "+PUBBLICAZIONI.FLAG_SOSPESO+ " = ? "
		+", "+PUBBLICAZIONI.LINK_AFFIDAMENTO_DIRETTO+ " = ? " //MEV 34470
		+" WHERE "
		+PUBBLICAZIONI.T_ID_PUBBLICAZIONE+"= ?"
		+" AND "+PUBBLICAZIONI.T_DATA_INIZIO_PUBB+"= ?";
	
	
	
	private final String QUERY_SELECT_PUBBLICAZIONE =
		" SELECT "
		+PUBBLICAZIONI.ID_PUBBLICAZIONE
		+", "+PUBBLICAZIONI.DATA_INIZIO_PUBB
		+", "+PUBBLICAZIONI.T_ID_STATO
		+", "+PUBBLICAZIONI.DATA_ALBO
		+", "+PUBBLICAZIONI.DATA_GUCE
		+", "+PUBBLICAZIONI.DATA_GURI
		+", "+PUBBLICAZIONI.QUOTIDIANI_NAZ
		+", "+PUBBLICAZIONI.QUOTIDIANI_REG
		+", "+PUBBLICAZIONI.PROFILO_COMMITTENTE
		+", "+PUBBLICAZIONI.SITO_MINISTERO_INF_TRASP
		+", "+PUBBLICAZIONI.SITO_OSSERVATORIO_CP
		+", "+PUBBLICAZIONI.DATA_BORE
		+", "+PUBBLICAZIONI.PERIODICI
		+", "+PUBBLICAZIONI.ID_SCHEDA_LOCALE
		//PP BANDI GARA
		+", "+PUBBLICAZIONI.NUMERO_GURI
		+", "+PUBBLICAZIONI.NUMERO_GUCE
		+", "+PUBBLICAZIONI.NUMERO_BORE
		+", "+PUBBLICAZIONI.LINK_SITO_COMMITTENTE
		+", "+PUBBLICAZIONI.LINK_AFFIDAMENTO_DIRETTO // MEV 34470
		+", "+PUBBLICAZIONI.TIPO_OPERAZIONE	
		//gm nuovo codice estensione pubblicazione bandi
		+", "+PUBBLICAZIONI.FLAG_BENICULT		
		+", "+PUBBLICAZIONI.FLAG_SOSPESO		
		+" FROM "
		+PUBBLICAZIONI.TABLE_NAME+ ", "
		+STATI_SCHEDA.TABLE_NAME + "";
			
	/**
	 * metodo per la cancellazione della pubblicazione di cui id
	 * 
	 * @param idPubblicazione long
	 * @param dataInizioPubblicazione Timestamp
	 * @throws SQLException
	 */
	public void deletePubblicazione(long idPubblicazione, Timestamp dataInizioPubblicazione)throws SQLException {
		PreparedStatement stmt = null;
		int index = 1;
		try{			
			stmt = activeConnection.prepareStatement(DELETE_RECORD_PUBBLICAZIONE);
			stmt.setLong(index++, idPubblicazione);
			stmt.setTimestamp(index++, dataInizioPubblicazione);
			stmt.execute();
		}finally{
			close(null,stmt);
		}		
	}
	
	/**
	 * metodo per il recupero di una pubblicazione
	 * 
	 * @param idPubblicazione long
	 * @param dataInizioPubblicazione Timestamp
	 * @return PubblicazioneBean
	 * @throws SQLException
	 */
	public PubblicazioneBean getPubblicazione(long idPubblicazione, Timestamp dataInizioPubblicazione) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PubblicazioneBean pubBean = new PubblicazioneBean();
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_PUBBLICAZIONE +WHERE_STANDARD);
			logger.debug("Select pubblicazione , query ["+QUERY_SELECT_PUBBLICAZIONE+WHERE_STANDARD+"]");
			stmt.setLong(1, idPubblicazione);
			stmt.setTimestamp(2, dataInizioPubblicazione);
			rs = stmt.executeQuery();
			if(rs.next()){
				this.fillBean(rs, pubBean);
			}
			
		}
		finally{
			close(rs, stmt);
		}
		
		return pubBean;
	}

	/**
	 * metodo per l'inserimento di una pubblicazione, nel passaggio venono settati
	 * nel bean l'id, e la data inizio
	 * 
	 * @param pubBean PubblicazioneBean
	 * @throws SQLException
	 */
	public void insertPubblicazione(PubblicazioneBean pubBean)throws SQLException{
		logger.debug("inserting pubblicazione: "+ObjectIntrospector.propertiesInfo(PubblicazioneBean.class, pubBean));
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try{
			//inserimento dei dati pubblicazione			
			stmt = activeConnection.prepareStatement(createInsertQuery(QUERY_INSERT_PUBBLICAZIONE,PUBBLICAZIONI.ID_PUBBLICAZIONE));			
			pubBean.setDataInizioPubblicazione(getNow());
			stmt.setTimestamp(index++,pubBean.getDataInizioPubblicazione());
			stmt.setString(index++, PageHelper.formatDateOrNull(pubBean.getDataAlbo()));
			stmt.setString(index++, PageHelper.formatDateOrNull(pubBean.getDataGuce()));
			stmt.setString(index++, PageHelper.formatDateOrNull(pubBean.getDataGuri()));
			//gm controllo se il campo è null per il WS 
			stmt.setInt(index++, pubBean.getQuotidianiNaz() == null ? 0 :pubBean.getQuotidianiNaz().intValue());
			//gm controllo se il campo è null per il WS 
			stmt.setInt(index++, pubBean.getQuotidianiReg() == null ? 0 :pubBean.getQuotidianiReg().intValue());
			//gm controllo se il campo è null per il WS 
			stmt.setString(index++, pubBean.getProfiloCommitente() == null ? "" :pubBean.getProfiloCommitente());
			//gm controllo se il campo è null per il WS 
			stmt.setString(index++, pubBean.getSitoMinisteroInfTrasp() == null ? "" :pubBean.getSitoMinisteroInfTrasp());
			//gm controllo se il campo è null per il WS 
			stmt.setString(index++, pubBean.getSitoOsservatorioCP() == null ? "" :pubBean.getSitoOsservatorioCP());
			stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE);
			stmt.setString(index++, PageHelper.formatDateOrNull(pubBean.getDataBore()));
			//campo int non gli piace il null ?
			stmt.setInt(index++, pubBean.getPeriodici() == null ? 0 :pubBean.getPeriodici().intValue());
			//gm nuovo codice pubblicazione bando 3.0
			//gm controllo se il campo è null per il WS 
			stmt.setString(index++, pubBean.getNumeroGuri() == null ? "" :pubBean.getNumeroGuri());
			//gm controllo se il campo è null per il WS 
			stmt.setString(index++, pubBean.getNumeroGuce() == null ? "" :pubBean.getNumeroGuce());
			//gm controllo se il campo è null per il WS 
			stmt.setString(index++, pubBean.getNumeroBore() == null ? "" :pubBean.getNumeroBore());
			//gm controllo se il campo è null per il WS 
			stmt.setString(index++, pubBean.getLinkSitoCommittente() == null ? "" :pubBean.getLinkSitoCommittente());
			//gm fine nuovo codice pubblicazione bando 3.0
			stmt.setString(index++, pubBean.getTipoOperazione());
				
			
			//gm nuovo codice estensione pubblicazione bandi
			if(pubBean.getFlag_benicult()==null)
				stmt.setNull(index++, Types.VARCHAR);
			else
				stmt.setString(index++, pubBean.getFlag_benicult());
			if(pubBean.getFlag_sospeso()==null)
				stmt.setNull(index++, Types.VARCHAR);
			else
				stmt.setString(index++, pubBean.getFlag_sospeso());
			
			//MEV 34470
			if(pubBean.getLinkAffidamentoDiretto()==null)
				stmt.setNull(index++, Types.VARCHAR);
			else
			    stmt.setString(index++, pubBean.getLinkAffidamentoDiretto());
			//FINE MEV 34470
			
			if(stmt.execute()){
				rs = stmt.getResultSet();
				rs.next();
				pubBean.setIdPubblicazione(rs.getLong(PUBBLICAZIONI.ID_PUBBLICAZIONE));
			}
		}finally{
			close(rs, stmt);
			
		}
	}

	/**
	 * metodo per il  aggiornamento/salvataggio di una pubblicazione
	 * 
	 * @param pubBean PubblicazioneBean
	 * @throws SQLException
	 */
	public void save(PubblicazioneBean pubBean) throws SQLException{
		 updatePubblicazione(pubBean, false);
	}
	/**
	 * metodo per la aggiornamento/conferma di una pubblicazione
	 * 
	 * @param pubBean PubblicazioneBean
	 * @throws SQLException
	 */
	public void confirm(PubblicazioneBean pubBean) throws SQLException{
		 updatePubblicazione(pubBean, true);
	}
	/**
	 * param pubBean
	 * param conferma
	 * throws SQLException
	 */
	private void updatePubblicazione(PubblicazioneBean pubBean, boolean conferma)throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try{
			//aggoirnamento/conferma dei dati pubblicazione
			
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_PUBBLICAZIONE);
			
			stmt.setString(index++, PageHelper.formatDateOrNull(pubBean.getDataGuce()));
			stmt.setString(index++, PageHelper.formatDateOrNull(pubBean.getDataGuri()));
			stmt.setString(index++, PageHelper.formatDateOrNull(pubBean.getDataAlbo()));
			stmt.setInt(index++, pubBean.getQuotidianiNaz());
			stmt.setInt(index++, pubBean.getQuotidianiReg());
			stmt.setString(index++, pubBean.getProfiloCommitente());
			stmt.setString(index++, pubBean.getSitoMinisteroInfTrasp() == null ? "" :pubBean.getSitoMinisteroInfTrasp());
			stmt.setString(index++, pubBean.getSitoOsservatorioCP());
			//se conferma....
			if(conferma){
				stmt.setLong(index++, StatiScheda.CONFERMATO);
				//dataFinePubblicazione
				stmt.setTimestamp(index++, getNow());
			}else{
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
				//dataFinePubblicazione
				stmt.setNull(index++, Types.TIMESTAMP);
			}
			stmt.setString(index++, PageHelper.formatDateOrNull(pubBean.getDataBore()));
			stmt.setInt(index++, pubBean.getPeriodici() == null ? 0 : pubBean.getPeriodici().intValue());
	      
			//gm nuovo codice estensione pubblicazione bandi
			if(pubBean.getFlag_benicult()==null)
	        	stmt.setNull(index++, Types.VARCHAR);
	        else
	        	stmt.setString(index++, pubBean.getFlag_benicult());
	        if(pubBean.getFlag_sospeso()==null)
	        	stmt.setNull(index++, Types.VARCHAR);
	        else
	        	stmt.setString(index++, pubBean.getFlag_sospeso());
	        
	        //MEV 34470
	        if(pubBean.getLinkAffidamentoDiretto()==null)
				stmt.setNull(index++, Types.VARCHAR);
			else
			    stmt.setString(index++, pubBean.getLinkAffidamentoDiretto());
	        //FINE MEV
	        
			stmt.setLong(index++, pubBean.getIdPubblicazione());
			stmt.setTimestamp(index++, pubBean.getDataInizioPubblicazione());
			stmt.execute();
			
			
		}finally{
			close(rs, stmt);
			
		}
	}
	
	private final String QUERY_UPDATE_RETTIFICA =
		"UPDATE " + PUBBLICAZIONI.TABLE_NAME + " SET "
		+PUBBLICAZIONI.FLAG_SOSPESO+ " = ?"
		+" WHERE "
		+PUBBLICAZIONI.T_ID_PUBBLICAZIONE+" = ?"
		+" AND "+PUBBLICAZIONI.T_DATA_INIZIO_PUBB+" = ?";
	
	
	/**
	 * param pubBean
	 * param confermaRettifica
	 * throws SQLException
	 */
	public void updateRettificaPubblicazione(PubblicazioneBean pubBean)throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try{
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RETTIFICA);
			stmt.setString(index++, pubBean.getFlag_sospeso());
			stmt.setLong(index++, pubBean.getIdPubblicazione());
			stmt.setTimestamp(index++, pubBean.getDataInizioPubblicazione());
			stmt.execute();		
		}finally{
			close(rs, stmt);	
		}
	}
	
	/**
	 * metodo per la presa incarico
	 * 
	 * @param idPubblicazione long
	 * @param dataInizioPubblicazione Timestamp
	 * @return nuova data inizio  - Timestamp
	 * @throws SQLException
	 */
	public Timestamp presaInCarico(long idPubblicazione, Timestamp dataInizioPubblicazione)throws SQLException{
		return copyRecord(idPubblicazione, dataInizioPubblicazione, true);
	}
	
	/**
	 * metodo per la storicizzazione del record
	 * 
	 * @param idPubblicazione long
	 * @param dataInizioPubblicazione Timestamp
	 * @return nuova data inizio  - Timestamp
	 * @throws SQLException
	 */
	public Timestamp copyRecord(long idPubblicazione, Timestamp dataInizioPubblicazione)throws SQLException{
		return copyRecord(idPubblicazione, dataInizioPubblicazione, false);
	}
	
	/**
	 * 
	 * @param idPubblicazione
	 * @param dataInizioPubblicazione
	 * @param presaInCarico
	 * @return
	 * @throws SQLException
	 */
	// MOD: SIMOG-32 UN 15/04/09 Aggiunta gestione per la presa in carico di una pubblicazione
	private Timestamp copyRecord(long idPubblicazione, Timestamp dataInizioPubblicazione, boolean presaInCarico)throws SQLException{
		String orPresaIncaricoDef = " OR " + INFO_AGGIUDICAZIONI.ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE + ")";
		
		String QUERY_SELECT_DATA_FINE_INFO =
			"SELECT " + PUBBLICAZIONI.DATA_FINE_PUBB + ", " + PUBBLICAZIONI.ID_STATO
			+" FROM " + PUBBLICAZIONI.TABLE_NAME
			+" WHERE " + PUBBLICAZIONI.T_ID_PUBBLICAZIONE+"= ?"
			+" AND " + PUBBLICAZIONI.T_DATA_INIZIO_PUBB+" = ?"
			+" AND ("+ PUBBLICAZIONI.ID_STATO+" = "+StatiScheda.CONFERMATO + (presaInCarico ? orPresaIncaricoDef : ")");
		
		String QUERY_COPY_RECORD_PUB =
			"INSERT INTO "+PUBBLICAZIONI.TABLE_NAME+" ("
			+ PUBBLICAZIONI.ID_PUBBLICAZIONE
			+", "+PUBBLICAZIONI.DATA_GUCE
			+", "+PUBBLICAZIONI.DATA_GURI
			+", "+PUBBLICAZIONI.DATA_ALBO

			+", "+PUBBLICAZIONI.QUOTIDIANI_NAZ
			+", "+PUBBLICAZIONI.QUOTIDIANI_REG
			+", "+PUBBLICAZIONI.PROFILO_COMMITTENTE
			+", "+PUBBLICAZIONI.SITO_MINISTERO_INF_TRASP
			+", "+PUBBLICAZIONI.SITO_OSSERVATORIO_CP
			+", "+PUBBLICAZIONI.DATA_INIZIO_PUBB
			+", "+PUBBLICAZIONI.DATA_FINE_PUBB
			+", "+PUBBLICAZIONI.ID_STATO 
			+", "+PUBBLICAZIONI.DATA_BORE
			+", "+PUBBLICAZIONI.PERIODICI
			//gm nuovo codice estensione pubblicazione bandi
			+", "+PUBBLICAZIONI.FLAG_BENICULT
			+", "+PUBBLICAZIONI.FLAG_SOSPESO
			+", "+PUBBLICAZIONI.LINK_AFFIDAMENTO_DIRETTO //MEV 34470
			+" ) "
			
			+" SELECT "
			+ PUBBLICAZIONI.ID_PUBBLICAZIONE
			+","+PUBBLICAZIONI.DATA_GUCE
			+","+PUBBLICAZIONI.DATA_GURI
			+","+PUBBLICAZIONI.DATA_ALBO
			+","+PUBBLICAZIONI.QUOTIDIANI_NAZ
			+","+PUBBLICAZIONI.QUOTIDIANI_REG
			+","+PUBBLICAZIONI.PROFILO_COMMITTENTE
			+","+PUBBLICAZIONI.SITO_MINISTERO_INF_TRASP
			+","+PUBBLICAZIONI.SITO_OSSERVATORIO_CP
			+", ?"
			+", ?"
			+", ?"
			+","+PUBBLICAZIONI.DATA_BORE
			+","+PUBBLICAZIONI.PERIODICI
			//gm nuovo codice estensione pubblicazione bandi
			+", "+PUBBLICAZIONI.FLAG_BENICULT
			+", "+PUBBLICAZIONI.FLAG_SOSPESO
			+", "+PUBBLICAZIONI.LINK_AFFIDAMENTO_DIRETTO //MEV 34470
			+" FROM "+PUBBLICAZIONI.TABLE_NAME
			+" WHERE "
			+PUBBLICAZIONI.ID_PUBBLICAZIONE+" = ?"
			+" AND "+PUBBLICAZIONI.DATA_INIZIO_PUBB+"= ?"
			+" AND ("+PUBBLICAZIONI.ID_STATO+" = "+StatiScheda.CONFERMATO +(presaInCarico ? orPresaIncaricoDef : ")");
		
		String UPDATE_STATO_OLD_RECORD_PUB = 
			"UPDATE " + PUBBLICAZIONI.TABLE_NAME + " SET "
			+ PUBBLICAZIONI.ID_STATO + " = ? "
			+ " WHERE "
			+ PUBBLICAZIONI.ID_PUBBLICAZIONE + " = ? AND "
			+ PUBBLICAZIONI.DATA_INIZIO_PUBB + " = ? AND ("
			+ PUBBLICAZIONI.ID_STATO + " = " + StatiScheda.CONFERMATO +(presaInCarico ? orPresaIncaricoDef : ")");
		
		PreparedStatement slPubStmt = null;
		PreparedStatement crPubStmt = null;
		PreparedStatement upPubStmt = null;
		ResultSet rs = null;
		
		Timestamp nuovaDataPub = null;
		int index = 1;
	
		try{
			//Select DataFineInfo e id_stato
			slPubStmt = activeConnection.prepareStatement(QUERY_SELECT_DATA_FINE_INFO);
			slPubStmt.setLong(index++, idPubblicazione);
			slPubStmt.setTimestamp(index++, dataInizioPubblicazione);
			rs = slPubStmt.executeQuery();
			
			Timestamp dataFine = null;
			int stato = StatiScheda.IN_DEFINIZIONE;
			
			if(rs.next()){	
				
				dataFine = rs.getTimestamp(PUBBLICAZIONI.DATA_FINE_PUBB);				
				stato = rs.getInt(PUBBLICAZIONI.ID_STATO);
				
				crPubStmt = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD_PUB,PUBBLICAZIONI.TABLE_NAME));
				index = 1;
				//copy record pubblicazioni
				nuovaDataPub = getNow();
				crPubStmt.setTimestamp(index++, nuovaDataPub);
				crPubStmt.setTimestamp(index++, (presaInCarico ?  dataFine : null));
				crPubStmt.setInt(index++, (presaInCarico ? stato : StatiScheda.IN_DEFINIZIONE));
				crPubStmt.setLong(index++, idPubblicazione);
				crPubStmt.setTimestamp(index++, dataInizioPubblicazione);
				crPubStmt.execute();
				
				upPubStmt = activeConnection.prepareStatement(UPDATE_STATO_OLD_RECORD_PUB);
				index = 1;
				//update stato old record pubblicazioni
				upPubStmt = activeConnection.prepareStatement(UPDATE_STATO_OLD_RECORD_PUB);
				upPubStmt.setInt(index++, (presaInCarico ? StatiScheda.PRESA_IN_CARICO : StatiScheda.ANNULLAMENTO_RICHIESTA));
				upPubStmt.setLong(index++, idPubblicazione);
				upPubStmt.setTimestamp(index++, dataInizioPubblicazione);
				upPubStmt.execute();
			}
			
		}/*catch(Exception e){	
			e.printStackTrace();
			throw new SQLException(e.getMessage());
			
		}*/finally{
			close(rs, slPubStmt);
			close(null, crPubStmt);
			close(null, upPubStmt);
		}
			
		
		return nuovaDataPub;
	}
	
	
	public static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_ACCETTATA_PUBB = 
		"UPDATE "+PUBBLICAZIONI.TABLE_NAME+
		" SET " + PUBBLICAZIONI.ID_STATO + " = ?,"
		+ PUBBLICAZIONI.DATA_FINE_PUBB + " = " + buildGetDate()+
		" WHERE "+PUBBLICAZIONI.ID_PUBBLICAZIONE + " = ?"+
		" AND "+PUBBLICAZIONI.DATA_INIZIO_PUBB + " = ?";
	
	
	/**
	 * metodo per l'aggiornamento del record
	 * 
	 * @param idPubblicazione long
	 * @param dataInizioPubblicazione Timestamp
	 * @param stato_scheda String
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int updateRecordPubblicazione(long idPubblicazione, Timestamp dataInizioPubblicazione, String stato_scheda ) throws SQLException{
		int numRow=-1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_ACCETTATA_PUBB);
			

	
			stmt.setString(1, stato_scheda);
			logger.debug(1 + ": "+stato_scheda);
			
			stmt.setLong(2, idPubblicazione);
			logger.debug(2 + ": "+idPubblicazione);
			
			stmt.setTimestamp(3,dataInizioPubblicazione);
			logger.debug(3 + ": "+dataInizioPubblicazione);
			numRow = stmt.executeUpdate();
		
		} finally {
			close(rs, stmt);
		}
		return numRow;

	}
	/*********************************************************************************************************/
	/**************************		NUOVE FUNZIONALITA' 	**************************************************/
	/*********************************************************************************************************/	
	/**
	 * @param idLocale
	 * @param cig
	 * @return
	 * @throws SQLException
	 */
	public PubblicazioneBean loadByIdLocale(String idLocale, String cig) throws SQLException{ 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PubblicazioneBean pubBean = new PubblicazioneBean();
		String cigNumerico = cig.substring(0, 7);
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_PUBBLICAZIONE_JOIN_INFOAGGIUDICAZIONI + WHERE_IDLOCALE);
			logger.debug("Select pubblicazione , query ["+QUERY_SELECT_PUBBLICAZIONE_JOIN_INFOAGGIUDICAZIONI+WHERE_IDLOCALE+"]");
			stmt.setString(1, cigNumerico);
			stmt.setString(2, idLocale);
			rs = stmt.executeQuery();
			if(rs.next()){
				this.fillBean(rs, pubBean);
			}
			
		}
		finally{
			close(rs, stmt);
		}
		
		return pubBean;
	}
	/**
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 */
	public PubblicazioneBean loadByIdSimog(long idSimog) throws SQLException{ 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PubblicazioneBean pubBean = new PubblicazioneBean();
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_PUBBLICAZIONE_JOIN_INFOAGGIUDICAZIONI+WHERE_IDSIMOG);
			logger.debug("Select pubblicazione , query ["+QUERY_SELECT_PUBBLICAZIONE_JOIN_INFOAGGIUDICAZIONI+WHERE_IDSIMOG+"]");
			stmt.setLong(1, idSimog);
			rs = stmt.executeQuery();
			if(rs.next()){
				this.fillBean(rs, pubBean);
			}
			
		}
		finally{
			close(rs, stmt);
		}
		
		return pubBean;
	}
	
	//gm le pubblicazioni relative ad una gara non hanno idAggiudicazione valorizzato
	public static String QUERY_SELECT_STORICO_PUBBLICAZIONI_GARA = 
	"SELECT * " +
	" FROM " + 
	PUBBLICAZIONI.TABLE_NAME +
	" WHERE " + 
	PUBBLICAZIONI.ID_PUBBLICAZIONE + " IN " +
	" (SELECT " + REL_PUBB_AGG.ID_PUBBLICAZIONE +
	" FROM " +
	REL_PUBB_AGG.TABLE_NAME +
	" WHERE " + 
	REL_PUBB_AGG.ID_GARA + " = ? " +
	" AND " +
	REL_PUBB_AGG.ID_AGGIUDICAZIONE + " IS NULL )" +
	" AND " + 
	PUBBLICAZIONI.ID_STATO + " != " + StatiScheda.ELIMINATO
	;	
	
	/**
	 * @param idGara
	 * @return List<PubblicazioneBean>
	 * @throws SQLException
	 */
	//gm il metodo serve per trovare l'elenco delle pubblicazioni relative ad una gara
	public List<PubblicazioneBean> getStoricoPubblicazioniGara(long idGara) throws SQLException{ 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List <PubblicazioneBean> storico = new ArrayList<PubblicazioneBean>();
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_STORICO_PUBBLICAZIONI_GARA);
			logger.debug("Select pubblicazione , query ["+QUERY_SELECT_STORICO_PUBBLICAZIONI_GARA+"]");
			stmt.setLong(1, idGara);
			rs = stmt.executeQuery();
			while(rs.next()){
				PubblicazioneBean pubBean = new PubblicazioneBean();
				this.fillBean(rs, pubBean);
				storico.add(pubBean);
			}		
		}
		finally{
			close(rs, stmt);
		}
		
		return storico;
	}
	
	//gm le pubblicazioni relative ad una gara hanno idAggiudicazione valorizzato
	public static String QUERY_SELECT_STORICO_PUBBLICAZIONI_AGGIUDICAZIONE = 
		"SELECT * " +
		" FROM " + 
		PUBBLICAZIONI.TABLE_NAME +
		" WHERE " + 
		PUBBLICAZIONI.ID_PUBBLICAZIONE + " IN " +
		" (SELECT " + REL_PUBB_AGG.ID_PUBBLICAZIONE +
		" FROM " +
		REL_PUBB_AGG.TABLE_NAME +
		" WHERE " + 
		REL_PUBB_AGG.ID_AGGIUDICAZIONE + " = ? " +
		" AND " + 
		REL_PUBB_AGG.DATA_INIZIO_AGG + " = ? )" +
		" AND " + 
		PUBBLICAZIONI.ID_STATO + " != " + StatiScheda.ELIMINATO
		;	
	
	//Utilizzato insieme ad Updata_data_lotti ed update_data_Gara per sbloccare una gara
	private final String UPDATE_DATA_PUBBLICAZIONE = "UPDATE "
		  +PUBBLICAZIONI.TABLE_NAME + " SET "
		  +PUBBLICAZIONI.T_ID_STATO + " = ?, "
		  +PUBBLICAZIONI.T_DATA_FINE_PUBB + " = ? "
		  +" WHERE "
		  + PUBBLICAZIONI.ID_PUBBLICAZIONE +" IN ("
		  +"SELECT "+ GARA.ID_PUBBLICAZIONE +" FROM "+ GARA.TABLE_NAME +" WHERE "
		  +GARA.ID_GARA +" = ? )";
		
	
		
		/**
		 * @param idAggiudicazione
		 * @param dataInizioAgg
		 * @return List<PubblicazioneBean>
		 * @throws SQLException
		 */
		//gm il metodo serve per trovare l'elenco delle pubblicazioni relative ad una aggiudicazione
		public List<PubblicazioneBean> getStoricoPubblicazioniAggiudicazione(long idAggiudicazione, Timestamp dataInizioAgg) throws SQLException{ 
			PreparedStatement stmt = null;
			ResultSet rs = null;
			List <PubblicazioneBean> storico = new ArrayList<PubblicazioneBean>();
			try{
				stmt = activeConnection.prepareStatement(QUERY_SELECT_STORICO_PUBBLICAZIONI_AGGIUDICAZIONE);
				logger.debug("Select pubblicazione , query ["+QUERY_SELECT_STORICO_PUBBLICAZIONI_AGGIUDICAZIONE+"]");
				int posCounter = 1;	
				stmt.setLong(posCounter++, idAggiudicazione);
				stmt.setTimestamp(posCounter++, dataInizioAgg);
				
				rs = stmt.executeQuery();
				while(rs.next()){
					PubblicazioneBean pubBean = new PubblicazioneBean();
					this.fillBean(rs, pubBean);
					storico.add(pubBean);
				}		
			}
			finally{
				close(rs, stmt);
			}		
			return storico;
		}
	
	/**
	 * @param rs
	 * @param pubBean
	 * @param withTableName
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, PubblicazioneBean pubBean) throws SQLException{
		pubBean.setIdPubblicazione(rs.getLong(PUBBLICAZIONI.ID_PUBBLICAZIONE));
		pubBean.setDataInizioPubblicazione(rs.getTimestamp(PUBBLICAZIONI.DATA_INIZIO_PUBB));
		pubBean.setDataAlbo(PageHelper.getViewDate(rs.getString(PUBBLICAZIONI.DATA_ALBO)));
		pubBean.setDataGuce(PageHelper.getViewDate(rs.getString(PUBBLICAZIONI.DATA_GUCE)));
		pubBean.setDataGuri(PageHelper.getViewDate(rs.getString(PUBBLICAZIONI.DATA_GURI)));
		pubBean.setQuotidianiNaz(rs.getInt(PUBBLICAZIONI.QUOTIDIANI_NAZ));
		pubBean.setQuotidianiReg(rs.getInt(PUBBLICAZIONI.QUOTIDIANI_REG));
		pubBean.setProfiloCommitente(rs.getString(PUBBLICAZIONI.PROFILO_COMMITTENTE));
		pubBean.setSitoMinisteroInfTrasp(rs.getString(PUBBLICAZIONI.SITO_MINISTERO_INF_TRASP));
		pubBean.setSitoOsservatorioCP(rs.getString(PUBBLICAZIONI.SITO_OSSERVATORIO_CP));
		pubBean.setDataBore(PageHelper.getViewDate(rs.getString(PUBBLICAZIONI.DATA_BORE)));
		pubBean.setPeriodici(rs.getInt(PUBBLICAZIONI.PERIODICI));
		pubBean.setIdLocale(rs.getString(PUBBLICAZIONI.ID_SCHEDA_LOCALE));
		//PP BANDI GARA
		pubBean.setNumeroGuce(rs.getString(PUBBLICAZIONI.NUMERO_GUCE));
		pubBean.setNumeroGuri(rs.getString(PUBBLICAZIONI.NUMERO_GURI));
		pubBean.setNumeroBore(rs.getString(PUBBLICAZIONI.NUMERO_BORE));
		pubBean.setLinkSitoCommittente(rs.getString(PUBBLICAZIONI.LINK_SITO_COMMITTENTE));
		pubBean.setTipoOperazione(rs.getString(PUBBLICAZIONI.TIPO_OPERAZIONE));
		//gm nuovo codice estensione pubblicazione bandi
		pubBean.setFlag_benicult(rs.getString(PUBBLICAZIONI.FLAG_BENICULT));
		pubBean.setFlag_sospeso(rs.getString(PUBBLICAZIONI.FLAG_SOSPESO));
		pubBean.setLinkAffidamentoDiretto(rs.getString(PUBBLICAZIONI.LINK_AFFIDAMENTO_DIRETTO)); //MEV 34470
	}
	/**
	 * Eliminazione scheda tramite identificativo del sistema remoto e CIG
	 * 
	 * @param idLocale
	 * @param cig
	 * @param cfUtente
	 * @throws SQLException
	 */
	public boolean annulla(String idLocale, String cig, String cfUtente) throws SQLException{
		PubblicazioneBean pubblicazioneBean = loadByIdLocale(idLocale, cig);
		
		if (pubblicazioneBean.getIdPubblicazione() > 0){
			return _annulla(pubblicazioneBean.getIdPubblicazione(), pubblicazioneBean.getDataInizioPubblicazione(), cfUtente);
		}
		return false;
	}
	
	/** Eliminazione scheda tramite identificativo simog
	 * @param idLocale
	 * @param cfUtente
	 * @throws SQLException
	 */
	public boolean annulla(long idSimog, String cfUtente) throws SQLException{
		PubblicazioneBean pubblicazioneBean = loadByIdSimog(idSimog);
		
		if (pubblicazioneBean.getIdPubblicazione() > 0){
			return _annulla(pubblicazioneBean.getIdPubblicazione(), pubblicazioneBean.getDataInizioPubblicazione(), cfUtente);
		}
		return false;
	}
	/** Eliminazione scheda tramite id e dataInizio della scheda.
	 * @param idPubblicazione
	 * @param dataInizioPubblicazione
	 * @param cfUtente
	 * @throws SQLException
	 */
	public boolean annulla(long idPubblicazione, Timestamp dataInizioPubblicazione, String cfUtente) throws SQLException{
		return _annulla(idPubblicazione, dataInizioPubblicazione, cfUtente);
	}

	/**
	 * @param idPubblicazione
	 * @param dataInizioPubblicazione
	 * @param cfUtente
	 * @throws SQLException
	 */
	private boolean _annulla(long idPubblicazione, Timestamp dataInizioPubblicazione, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_PUBBLICAZIONE);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idPubblicazione);
			stmt.setTimestamp(index++, dataInizioPubblicazione);
			someRowAffected = stmt.executeUpdate() > 0;
			return someRowAffected;
		}finally {
			close(null,stmt);
		}

	}
	
	public void sbloccaPubblicazione(long sessionIdGara) throws SQLException{
		
		int idx=0;
		int eliminato=StatiScheda.ELIMINATO;
		//Timestamp time=getNow();
		long l=sessionIdGara;
		
		PreparedStatement updatePerfezionamentoFunction = null;
		
		try {
			
			
			updatePerfezionamentoFunction = activeConnection.prepareStatement(UPDATE_DATA_PUBBLICAZIONE);
			updatePerfezionamentoFunction.setInt(1, StatiScheda.ELIMINATO);
			updatePerfezionamentoFunction.setTimestamp(2, getNow());
			updatePerfezionamentoFunction.setLong(3, sessionIdGara);
			
			updatePerfezionamentoFunction.executeUpdate();
			logger.debug ( "Eseguita query [" + UPDATE_DATA_PUBBLICAZIONE + "] per idGara [" + sessionIdGara + "]" );
			
			
		} finally {
			try {
				updatePerfezionamentoFunction.close();
			} catch ( Exception e ) {}
			updatePerfezionamentoFunction = null;			
		}
		
	}
}
