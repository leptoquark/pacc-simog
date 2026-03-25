package it.avcp.simog.managers.aggiudicazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamento;
import it.avcp.simog.managers.comportamento.caricamento.ILoadInfoComuni;
import it.avlp.simog.beans.EsitoEnum;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.db.generated.ART_ESCLUSIONE;
import it.avlp.simog.db.generated.CATEGORIA_SA;
import it.avlp.simog.db.generated.CPVEU;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.MODI_REALIZZAZIONE;
import it.avlp.simog.db.generated.PUBBLICAZIONI;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.db.generated.TIPI_CATEGORIA;
import it.avlp.simog.db.generated.TIPOLOGIA_PROCEDURA;
import it.avlp.simog.db.generated.TIPOLOGIA_SA;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.util.ObjectIntrospector;

/**
 * Classe che si occupa della gestione dei dati relativi agli info comuni
 *
 */
public class InfoComuniManager extends AccessiDB implements IAnnullamento,ILoadInfoComuni{

	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public InfoComuniManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}	
	private final String QUERY_INSERT_DATI_COMUNI_INFO =
		"INSERT INTO "+INFO_AGGIUDICAZIONI.TABLE_NAME+" ("
		+INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO
		+", "+ INFO_AGGIUDICAZIONI.ID_LOTTO
		+", "+INFO_AGGIUDICAZIONI.CIG_CICLE
		+", "+INFO_AGGIUDICAZIONI.CIG
		+", "+INFO_AGGIUDICAZIONI.T_ID_STATO
		+", "+INFO_AGGIUDICAZIONI.DATA_FINE_INFO	
		
		+", "+INFO_AGGIUDICAZIONI.ID_PUBBLICAZIONE
		+", "+INFO_AGGIUDICAZIONI.DATA_INIZIO_PUBB		
		
		+", "+INFO_AGGIUDICAZIONI.CF_AMM
		+", "+INFO_AGGIUDICAZIONI.DEN_AMM
		+", "+INFO_AGGIUDICAZIONI.CF_SA
		+", "+INFO_AGGIUDICAZIONI.DEN_SA
		+", "+INFO_AGGIUDICAZIONI.ID_CATEG_SA
		+", "+INFO_AGGIUDICAZIONI.CF_AMM_AGENTE
		+", "+INFO_AGGIUDICAZIONI.DEN_AMM_AGENTE
		+", "+INFO_AGGIUDICAZIONI.FLAG_ENTE_SPECIALE
		+", "+INFO_AGGIUDICAZIONI.TIPO_CONTRATTO
		+", "+INFO_AGGIUDICAZIONI.CODICE_CC
		+", "+INFO_AGGIUDICAZIONI.DENOM_CC
		+", "+INFO_AGGIUDICAZIONI.FLAG_SA_AGENTE
		+", "+INFO_AGGIUDICAZIONI.ID_TIPOLOGIA_SA
		+", "+INFO_AGGIUDICAZIONI.CF_RUP
		//gm nuovo codice dati comuni
		+", "+INFO_AGGIUDICAZIONI.ID_TIPO_PROCEDURA
		+", "+INFO_AGGIUDICAZIONI.DURATA_CONVENZIONE
		+", "+INFO_AGGIUDICAZIONI.FLAG_PROCEDE_STIPULA
		
		+", "+INFO_AGGIUDICAZIONI.ESITO_PROCEDURA
		+", "+INFO_AGGIUDICAZIONI.ID_SCHEDA_LOCALE
		+ (SimogFlags.is3028_RFWEBSC00Active() ? ", " + INFO_AGGIUDICAZIONI.ORIGINE : "")
		+ ")"
		+" VALUES ( ?,?,?,?,?,?,?,?,?,?,"
				+ "   ?,?,?,?,?,?,?,?,?,?,"
				+ "   ?,?,?,?,?,?,?"
		        + (SimogFlags.is3028_RFWEBSC00Active() ? ",?" : "")
				+ " )";
		
	private final String QUERY_UPDATE_DATI_COMUNI_INFO =
		"UPDATE "+INFO_AGGIUDICAZIONI.TABLE_NAME+" SET "
		+INFO_AGGIUDICAZIONI.T_ID_STATO+ " = ? "
		+", "+INFO_AGGIUDICAZIONI.DATA_FINE_INFO+ " = ? "
		
		
		+", "+INFO_AGGIUDICAZIONI.CF_SA+ " = ? "
		+", "+INFO_AGGIUDICAZIONI.DEN_SA+ " = ? "
		
		+", "+INFO_AGGIUDICAZIONI.ID_CATEG_SA + " = ? "
		+", "+INFO_AGGIUDICAZIONI.CF_AMM_AGENTE + " = ? "
		+", "+INFO_AGGIUDICAZIONI.DEN_AMM_AGENTE + " = ? "
		//gm nuovo codice dati comuni
		+", "+INFO_AGGIUDICAZIONI.ID_TIPO_PROCEDURA + " = ? "
		+", "+INFO_AGGIUDICAZIONI.DURATA_CONVENZIONE + " = ? "
		+", "+INFO_AGGIUDICAZIONI.FLAG_PROCEDE_STIPULA + " = ? "
		
		+", "+INFO_AGGIUDICAZIONI.CODICE_CC+ " = ? "
		+", "+INFO_AGGIUDICAZIONI.DENOM_CC+ " = ? "
		+", "+INFO_AGGIUDICAZIONI.FLAG_SA_AGENTE+ " = ? "
		+", "+INFO_AGGIUDICAZIONI.TIPO_CONTRATTO+ " = ? "
		+", "+INFO_AGGIUDICAZIONI.FLAG_ENTE_SPECIALE+ " = ? "
		+", "+INFO_AGGIUDICAZIONI.ID_TIPOLOGIA_SA+ " = ? "
		+", "+INFO_AGGIUDICAZIONI.ESITO_PROCEDURA+ " = ? "
		+" WHERE "+INFO_AGGIUDICAZIONI.ID_INFO+" = ?"
		+" AND "+INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO+" = ?";
	
	// PP fix controllo stato scheda
	private final String WHERE_DEF = " AND "+ INFO_AGGIUDICAZIONI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING;
	private final String WHERE_CONF = " AND ( "+INFO_AGGIUDICAZIONI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING
	        + " OR " +INFO_AGGIUDICAZIONI.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING+ " ) ";
	
	private final String QUERY_SELECT_DATI_GARA = " SELECT "
		
		+  GARA.CF_AMMINISTRAZIONE
		+ ", " + GARA.DENOM_AMMINISTRAZIONE
		+ ", " + GARA.ID_STAZIONE_APPALTANTE
		+ ", " + GARA.DENOM_STAZIONE_APPALTANTE + ", " + 
		LOTTO.T_CIG + ", " + 
		LOTTO.T_CIG_CICLE + ", " + 
		LOTTO.T_CIG_KKK + ", " +
		LOTTO.T_ID_CPV + ", " +
		LOTTO.T_OGGETTO + ", " + 
		LOTTO.T_DATA_CREAZIONE_LOTTO
		+" FROM "
		+GARA.TABLE_NAME
		+", "+LOTTO.TABLE_NAME
		
		+" WHERE "
		+LOTTO.T_ID_LOTTO+" =?"
		+" AND "+GARA.T_ID_GARA+" = "+LOTTO.T_ID_GARA;
	
	private final String QUERY_SELECT_DATI_COMUNI = 
		" SELECT "
		+ PUBBLICAZIONI.T_LINK_AFFIDAMENTO_DIRETTO //MEV 37523 3.04.8.1
		+ ", " + GARA.T_ID_GARA 
		+ ", " + GARA.T_OGGETTO
		+ ", " + GARA.T_ID_MODO_REAL
		+ ", " + GARA.DATA_CREAZIONE
		+ ", " + LOTTO.FLAG_ESCLUSO
		+ ", " + LOTTO.ID_ESCLUSIONE
		+ ", " + INFO_AGGIUDICAZIONI.T_ID_LOTTO
		+ ", " + INFO_AGGIUDICAZIONI.T_CIG_CICLE
		+ ", " + INFO_AGGIUDICAZIONI.T_CIG
		+", "+INFO_AGGIUDICAZIONI.ID_INFO
		+", "+INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO
		+", "+INFO_AGGIUDICAZIONI.T_ID_STATO
		+", "+STATI_SCHEDA.T_DESCRIZIONE+ " + " +  buildRichAnnQuery(IdentificativoSchede.TAB_INFO_COMUNI, INFO_AGGIUDICAZIONI.T_ID_INFO,null)
		+" AS "+STATI_SCHEDA.DESCRIZIONE
		+ ", " + INFO_AGGIUDICAZIONI.CF_AMM
		+ ", " + INFO_AGGIUDICAZIONI.DEN_AMM
		+ ", " + INFO_AGGIUDICAZIONI.CF_SA
		+ ", " + INFO_AGGIUDICAZIONI.DEN_SA
		+", "+INFO_AGGIUDICAZIONI.ID_CATEG_SA 
		+", "+INFO_AGGIUDICAZIONI.CF_AMM_AGENTE
		+", "+INFO_AGGIUDICAZIONI.DEN_AMM_AGENTE
		+", "+INFO_AGGIUDICAZIONI.DEN_AMM_AGENTE
		+", "+INFO_AGGIUDICAZIONI.FLAG_ENTE_SPECIALE
		+", "+INFO_AGGIUDICAZIONI.TIPO_CONTRATTO
		+", "+INFO_AGGIUDICAZIONI.CODICE_CC
		+", "+INFO_AGGIUDICAZIONI.DENOM_CC
		+", "+INFO_AGGIUDICAZIONI.FLAG_SA_AGENTE
		+", "+INFO_AGGIUDICAZIONI.ID_TIPOLOGIA_SA
		//gm nuovo codice dati comuni
		+", "+INFO_AGGIUDICAZIONI.ID_TIPO_PROCEDURA
		+", "+INFO_AGGIUDICAZIONI.DURATA_CONVENZIONE
		+", "+INFO_AGGIUDICAZIONI.FLAG_PROCEDE_STIPULA
		//gm modificato ID_PUBBLICAZIONE in T_ID_PUBBLICAZIONE in pubblicazione bando 3.0
		//per eliminare l'ambiguità tra i campi omonimi nella tabella gara e info_aggiudicazioni
		+", "+INFO_AGGIUDICAZIONI.T_ID_PUBBLICAZIONE
		//gm modificato DATA_INIZIO_PUBB in T_DATA_INIZIO_PUBB in pubblicazione bando 3.0
		//per eliminare l'ambiguità tra i campi omonimi nella tabella gara e info_aggiudicazioni
		+", "+INFO_AGGIUDICAZIONI.T_DATA_INIZIO_PUBB
		
		+ (SimogFlags.is3028_RFWEBSC00Active() ? ", " + INFO_AGGIUDICAZIONI.T_ORIGINE : "")
		
		+", upper("+INFO_AGGIUDICAZIONI.CF_RUP + ") as " + INFO_AGGIUDICAZIONI.CF_RUP
		+", "+INFO_AGGIUDICAZIONI.ESITO_PROCEDURA
		+", "+INFO_AGGIUDICAZIONI.T_ID_SCHEDA_LOCALE
      + (SimogFlags.is3031_ESCL_AVCPASS() ? "," + GARA.ESCLUSO_AVCPASS  : "") 
       + (SimogFlags.isINT85_RFWEBGL01Active() ? "," + GARA.SCELTA_LEGGE89  : "") 
       + (SimogFlags.isINT85_RFWEBGL01Active() ? "," + GARA.TIPOSA_BDNCP  : "") 
       + (SimogFlags.isINT87_RFSIMOGWEB01Active() ? "," + GARA.URGENZA_DL133  : "") 
       + (SimogFlags.is30350_RFWEBGL01Active() ? "," + GARA.COD_MOTIVO_EAGG  : "") 
       
       +" FROM " +GARA.TABLE_NAME
       + " LEFT JOIN  " + LOTTO.TABLE_NAME
       	+ " ON " + GARA.T_ID_GARA + "=" + LOTTO.T_ID_GARA
       + " LEFT JOIN  " + PUBBLICAZIONI.TABLE_NAME //MEV 37523 3.04.8.1
       	+ " ON " + GARA.T_ID_PUBBLICAZIONE + "=" + PUBBLICAZIONI.T_ID_PUBBLICAZIONE //MEV 37523 3.04.8.1
       + " LEFT OUTER JOIN  " + CPVEU.TABLE_NAME
       	+ " ON " + LOTTO.T_ID_CPV + "=" + CPVEU.ID_DIV + " + " + CPVEU.ID_GRP + " + " + CPVEU.ID_CLS + " + " + CPVEU.ID_CTG + " + " + CPVEU.ID_VOX + " +'-'+ " + CPVEU.CHK       
       	+ " AND " + CPVEU.VERSIONE + " = " + buildVersCPV( LOTTO.T_ID_CPV ,  GARA.T_DATA_CREAZIONE)  
		+" LEFT JOIN "+INFO_AGGIUDICAZIONI.TABLE_NAME
			+ " ON " +LOTTO.T_ID_LOTTO+" = "+INFO_AGGIUDICAZIONI.T_ID_LOTTO
		+ " LEFT JOIN " + STATI_SCHEDA.TABLE_NAME 
			+ " ON " + INFO_AGGIUDICAZIONI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO 
		+" WHERE (" + INFO_AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + INFO_AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	private final String QUERY_EXIST_DATI_COMUNI = 
		" SELECT *" +
		" " 
		+" FROM "
		+" "+INFO_AGGIUDICAZIONI.TABLE_NAME
		+" WHERE "
		+INFO_AGGIUDICAZIONI.CIG + " = ?"
		+" AND " + INFO_AGGIUDICAZIONI.CIG_CICLE + " = (SELECT MAX(" + INFO_AGGIUDICAZIONI.CIG_CICLE + ")" 
													+ " FROM " + INFO_AGGIUDICAZIONI.TABLE_NAME
													+ " WHERE " + INFO_AGGIUDICAZIONI.CIG + " = ?)"
		+" AND (" + INFO_AGGIUDICAZIONI.DATA_FINE_INFO + " IS NULL"
		+"  OR (" + INFO_AGGIUDICAZIONI.DATA_FINE_INFO + " IS NOT NULL AND " 
				+ INFO_AGGIUDICAZIONI.ID_STATO +  " = " + StatiScheda.CONFERMATO_STRING + ")"
		+ ")";	
	
	//MEV 37523 3.04.8.1 commentata quella precedente e fatta questa
//	private final String QUERY_DATI_COMUNI_BY_ID_LOTTO = 
//			" SELECT * " + 
//			" FROM "+ INFO_AGGIUDICAZIONI.TABLE_NAME
//			+" WHERE " + INFO_AGGIUDICAZIONI.ID_LOTTO + " = ?"
//			+ " ORDER BY " + INFO_AGGIUDICAZIONI.ID_INFO + " , " + INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO + " DESC";
	private final String QUERY_DATI_COMUNI_BY_ID_LOTTO = 
			" SELECT " + INFO_AGGIUDICAZIONI.TABLE_NAME + ".*, "
			+ PUBBLICAZIONI.T_LINK_AFFIDAMENTO_DIRETTO
			+ " FROM "+ INFO_AGGIUDICAZIONI.TABLE_NAME
			+ " LEFT JOIN  " + PUBBLICAZIONI.TABLE_NAME //MEV 37523 3.04.8.1
        	+ " ON " + INFO_AGGIUDICAZIONI.T_ID_PUBBLICAZIONE + "=" + PUBBLICAZIONI.T_ID_PUBBLICAZIONE //MEV 37523 3.04.8.1
			+" WHERE " + INFO_AGGIUDICAZIONI.ID_LOTTO + " = ?"
			+ " ORDER BY " + INFO_AGGIUDICAZIONI.ID_INFO + " , " + INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO + " DESC";
	
	
	
	/**
	 * metodo per l'inserimento dei dati comuni, nel passaggio vengono settati dei valori
	 * del bean che sono l'id, e la data inizio
	 * 
	 * @param infoBean InfoComuniBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public void insert(InfoComuniBean infoBean, String cfUtente)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		logger.debug(ObjectIntrospector.propertiesInfo(InfoComuniBean.class, infoBean));
		try{ 
			stmt = activeConnection.prepareStatement(createInsertQuery(QUERY_INSERT_DATI_COMUNI_INFO,INFO_AGGIUDICAZIONI.ID_INFO));
			int index = 1;
			infoBean.setDataInizioInfo(getNow()); //data inizio
 			stmt.setTimestamp(index++, infoBean.getDataInizioInfo());
 			stmt.setLong(index++, infoBean.getIdLotto());
 	    	stmt.setInt(index++, infoBean.getCigCycle());
 		    stmt.setString(index++, infoBean.getCig());
			stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
			stmt.setNull(index++, Types.TIMESTAMP);
			
			stmt.setLong(index++, infoBean.getPubblicazione().getIdPubblicazione());
			stmt.setTimestamp(index++, infoBean.getPubblicazione().getDataInizioPubblicazione());
			
			
			stmt.setString(index++, infoBean.getCfAmministrazione());
			stmt.setString(index++, infoBean.getDenAmministrazione());
			stmt.setString(index++, infoBean.getCfStazioneAppaltante());
			stmt.setString(index++, infoBean.getDenStazioneAppaltante());
			
			if(infoBean.getIdCategSa() != null && !"".equals(infoBean.getIdCategSa()))
				stmt.setString(index++, infoBean.getIdCategSa());
			else
				stmt.setNull(index++, Types.VARCHAR);
			
			stmt.setString(index++, infoBean.getCfAmmAgente());
			stmt.setString(index++, infoBean.getDenAmmAgente());
			stmt.setString(index++, infoBean.getFlagEnteSpeciale());
			stmt.setString(index++, infoBean.getTipoContratto());
			stmt.setString(index++, infoBean.getCodiceCC());
			stmt.setString(index++, infoBean.getDenomCC());
			stmt.setString(index++, infoBean.getFlagSAAgente());
			if(infoBean.getTipologiaSA() < 1 ){
				stmt.setNull(index++, Types.BIGINT);
			}
			else{
				stmt.setLong(index++, infoBean.getTipologiaSA());
			}
			stmt.setString(index++, infoBean.getCfRup() == null ? cfUtente : infoBean.getCfRup());

			//gm nuovo codice dati comuni
			if(infoBean.getTipologiaProcedura()<1)
				stmt.setNull(index++, Types.BIGINT);
			else
			    stmt.setLong(index++, infoBean.getTipologiaProcedura());
			stmt.setObject(index++, infoBean.getDurataConvenzione());
			stmt.setString(index++, infoBean.getFlagProcedeStipula());
			
			stmt.setString(index++, infoBean.getEsitoProcedura());
			if(infoBean.getIdLocale() == null){
				stmt.setNull(index++, Types.VARCHAR);
			}else{
				stmt.setString(index++, infoBean.getIdLocale());
			}
			
			   stmt.setInt(index++, infoBean.getOrigine());
			

			if(stmt.execute()){
				rs = stmt.getResultSet();
				rs.next();
				infoBean.setIdInfo(rs.getLong(INFO_AGGIUDICAZIONI.ID_INFO));
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(infoBean.getIdInfo());
				attributiChiave.add(infoBean.getDataInizioInfo());
				LogBloccoDatiManager.loggingINSERT(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_INFO_COMUNI, attributiChiave);
			}

		 }
			finally {
				close(rs,stmt);
			}
	}
	/**
	 * @param infoBean
	 * @param cfUtente
	 * @param conferma
	 * @throws SQLException
	 * @throws SimogException 
	 */
	private int update(InfoComuniBean infoBean, String cfUtente, boolean conferma) throws SQLException{
		PreparedStatement stmt = null;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_DATI_COMUNI_INFO + (SimogFlags.isFlagNoDate() ? WHERE_CONF : WHERE_DEF));
			int index = 1;
			if (conferma){
				stmt.setLong(index++, StatiScheda.CONFERMATO);
				stmt.setTimestamp(index++, getNow());
				infoBean.setIdStato(StatiScheda.CONFERMATO);
			}else{
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
				stmt.setNull(index++, Types.TIMESTAMP);
				infoBean.setIdStato(StatiScheda.IN_DEFINIZIONE);
			}
			
			stmt.setString(index++, infoBean.getCfStazioneAppaltante());
			stmt.setString(index++, infoBean.getDenStazioneAppaltante());
			if(infoBean.getIdCategSa() != null && !"".equals(infoBean.getIdCategSa()))
				stmt.setString(index++, infoBean.getIdCategSa());
			else
				stmt.setNull(index++, Types.VARCHAR);
			
			
			stmt.setString(index++, infoBean.getCfAmmAgente());
			stmt.setString(index++, infoBean.getDenAmmAgente());
			//gm nuovo codice dati comuni
			if(infoBean.getTipologiaProcedura()<1)
				stmt.setNull(index++, Types.BIGINT);
			else
			    stmt.setLong(index++, infoBean.getTipologiaProcedura());
			stmt.setObject(index++, infoBean.getDurataConvenzione());
			stmt.setString(index++, infoBean.getFlagProcedeStipula());
			
			stmt.setString(index++, infoBean.getCodiceCC());
			stmt.setString(index++, infoBean.getDenomCC());
			stmt.setString(index++, infoBean.getFlagSAAgente());
			stmt.setString(index++, infoBean.getTipoContratto());
			stmt.setString(index++, infoBean.getFlagEnteSpeciale());
			if(infoBean.getTipologiaSA() < 1 ){
				stmt.setNull(index++, Types.BIGINT);
			}
			else{
				stmt.setLong(index++, infoBean.getTipologiaSA());
			}
			stmt.setString(index++, infoBean.getEsitoProcedura());	
			stmt.setLong(index++, infoBean.getIdInfo());
			stmt.setTimestamp(index++, infoBean.getDataInizioInfo());
			int num = stmt.executeUpdate();
			
			List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(infoBean.getIdInfo());
			attributiChiave.add(infoBean.getDataInizioInfo());
			if(conferma)
				LogBloccoDatiManager.loggingCONFIRM(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_INFO_COMUNI, attributiChiave);
			else 
				LogBloccoDatiManager.loggingUPDATE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_INFO_COMUNI, attributiChiave);
			return num;
		 }
		finally {
			close(null,stmt);
		}

	}
	/**
	 * metodo per il aggiornamento dei dati comuni
	 * 
	 * @param bean InfoComuniBean
	 * @param cfUtente String
	 * @throws SQLException
	 * @throws SimogException 
	 */
	public int save(InfoComuniBean bean, String cfUtente) throws SQLException{
		return update(bean, cfUtente, false);
	}
	/**
	 * metodo per il aggiornamento dei dati comuni, lo stato viene settato a "confermato"
	 * 
	 * @param bean InfoComuniBean
	 * @param cfUtente String
	 * @throws SQLException
	 * @throws SimogException 
	 */
	public int confirm(InfoComuniBean bean, String cfUtente)throws SQLException{
		return update(bean, cfUtente, true);
	}
	/**
	 * metodo per il recupero dei dati relativi a gara/lotto
	 * 
	 * @param idLotto String
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getDatiGara(String idLotto) throws SQLException{
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				stmt = activeConnection.prepareStatement(QUERY_SELECT_DATI_GARA);
				
				stmt.setObject(1, Integer.parseInt(idLotto));
				rs = stmt.executeQuery();
				TableBean tb = new TableBean(rs);
				logger.debug("++++++++++++++++++++++++++++++++++++++++++" +ObjectIntrospector.propertiesInfo(tb.getRow(0).getClass(), tb.getRow(0)));
				return tb;
				
			}
			finally{
				close(rs,stmt);
			}
		
	}
	private final String QUERY_SELECT_ENTE_CONTRATTO =
		" SELECT " +
		INFO_AGGIUDICAZIONI.TIPO_CONTRATTO+"," +
		INFO_AGGIUDICAZIONI.FLAG_ENTE_SPECIALE + ", " +
		INFO_AGGIUDICAZIONI.ID_INFO + "," +
		INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO + "," +
		"upper(" + INFO_AGGIUDICAZIONI.CF_RUP + ") as " + INFO_AGGIUDICAZIONI.CF_RUP + "," +
		GARA.CF_AMMINISTRAZIONE
		+ ", " + GARA.DENOM_AMMINISTRAZIONE
		+ ", " + GARA.ID_STAZIONE_APPALTANTE
		+ ", " + GARA.DENOM_STAZIONE_APPALTANTE 
		+ ", " + GARA.T_ID_PUBBLICAZIONE 
		+ ", " + GARA.T_DATA_INIZIO_PUBB 
		+ ", " + GARA.ID_OSSERVATORIO
		//gm aggiunto per adesione accordo quadro
		+ ", " + GARA.CIG_ACC_QUADRO
		+ ", " + 
		LOTTO.T_CIG + ", " + 
		LOTTO.T_ID_SCELTA_CONTRAENTE + ", " +
		LOTTO.T_CIG_CICLE + ", " + 
		LOTTO.T_CIG_KKK + ", " +
		LOTTO.T_ID_CPV + ", " +
		CPVEU.T_DESCRIZIONE + ", " +
		LOTTO.T_DATA_CANCELLAZIONE_LOTTO + ", " +
		LOTTO.T_DATA_INIB_PAGAMENTO + ", " +
		LOTTO.T_OGGETTO + ", " +
		LOTTO.T_ID_LOTTO + ", " +
		LOTTO.T_SOMMA_URGENZA + 
		",  " + LOTTO.T_IMPORTO_LOTTO +
		",  " + LOTTO.T_DATA_SCADENZA_PAGAMENTI +
		",  " + LOTTO.T_DATA_LETTERA_INVITO + //MEV 34183 3.04.8
		",  " + LOTTO.T_DATA_SCADENZA_RICHIESTA_INVITO + //MEV 34183 3.04.8
		",  " + LOTTO.T_DATA_CREAZIONE_LOTTO +
		",  " + GARA.T_ID_MODO_REAL +
		",  " + GARA.T_TIPO_SCHEDA_GARA +
      ",  " + GARA.T_DATA_CREAZIONE +
		",  " + LOTTO.T_FLAG_ESCLUSO +
		",  " + LOTTO.T_ID_ESCLUSIONE +
		",  " + LOTTO.T_TIPO_CONTRATTO_LOTTO +
		",  " + LOTTO.LUOGO_ISTAT +
		",  " + LOTTO.LUOGO_NUTS +
		",  " + LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA +
		",  " + LOTTO.T_ID_GARA +
		",  " + LOTTO.T_DATA_PUBBLICAZIONE +
		
		//
		" FROM " + LOTTO.TABLE_NAME + " JOIN " + GARA.TABLE_NAME + 
		" ON " + LOTTO.T_ID_GARA + " = " + GARA.T_ID_GARA + " LEFT OUTER JOIN " + 
		CPVEU.TABLE_NAME + " ON " + LOTTO.T_ID_CPV + " = " + CPVEU.ID_DIV + " + " + CPVEU.ID_GRP + " + " + CPVEU.ID_CLS + " + " + CPVEU.ID_CTG + " + " + CPVEU.ID_VOX + " +'-'+ " + CPVEU.CHK +
			" and " + CPVEU.T_VERSIONE + " = " + buildVersCPV( LOTTO.T_ID_CPV ,  GARA.T_DATA_CREAZIONE) +
		" LEFT OUTER JOIN " + INFO_AGGIUDICAZIONI.TABLE_NAME + " ON " +
		LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO +
		" AND (" + INFO_AGGIUDICAZIONI.ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE + " OR " 
		+ INFO_AGGIUDICAZIONI.ID_STATO + "=" + StatiScheda.CONFERMATO + " OR " + INFO_AGGIUDICAZIONI.ID_STATO  + " is null " + ")" +
		" WHERE " 
		+LOTTO.T_ID_LOTTO+" = ?" ;

	/**
	 * metodo per il recupero delle info gara
	 * 
	 * @param idLotto long
	 * @return InfoGaraBean
	 * @throws SQLException
	 */
	public InfoGaraBean loadInfoGara(long idLotto)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		InfoGaraBean igb = new InfoGaraBean();
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_ENTE_CONTRATTO);
			logger.debug("Select dati comuni, query ["+QUERY_SELECT_ENTE_CONTRATTO+"]");
			
			stmt.setLong(1,idLotto);
			
			rs = stmt.executeQuery();
			if(rs.next()){
				igb.setIdInfo(rs.getLong(INFO_AGGIUDICAZIONI.ID_INFO));
				igb.setDataInizioInfo(rs.getTimestamp(INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO));
				igb.setCig(rs.getString(LOTTO.CIG));
				igb.setCigCicle(rs.getInt(LOTTO.CIG_CICLE));
				igb.setCigKKK(rs.getString(LOTTO.CIG_KKK));
				igb.setIdSceltaContraente(rs.getLong(LOTTO.ID_SCELTA_CONTRAENTE));
				igb.setDataCancelazioneLotto(rs.getString(LOTTO.DATA_CANCELLAZIONE_LOTTO));
				igb.setDataInibPagamento(rs.getString(LOTTO.DATA_INIB_PAGAMENTO));
				igb.setIdCPV(rs.getString(LOTTO.ID_CPV));
				igb.setDescrizioneCPV(rs.getString(CPVEU.DESCRIZIONE));
				igb.setIdLotto(rs.getLong(LOTTO.ID_LOTTO));
				igb.setOggettoLotto(rs.getString(LOTTO.OGGETTO));
				igb.setTipoContratto(rs.getString(INFO_AGGIUDICAZIONI.TIPO_CONTRATTO));
				// PP se null da infocomuni prendo quello della gara
				if(igb.getTipoContratto()== null)
					igb.setTipoContratto(rs.getString(LOTTO.TIPO_CONTRATTO_LOTTO));
				
				igb.setTipoEnte(rs.getString(INFO_AGGIUDICAZIONI.FLAG_ENTE_SPECIALE));
				igb.setCfAmministrazione(rs.getString(GARA.CF_AMMINISTRAZIONE));
				igb.setDenomAmministrazione(rs.getString(GARA.DENOM_AMMINISTRAZIONE));
				igb.setCfSA(rs.getString(GARA.ID_STAZIONE_APPALTANTE));
				igb.setDenomSA(rs.getString(GARA.DENOM_STAZIONE_APPALTANTE));
				igb.setCfRup(rs.getString(INFO_AGGIUDICAZIONI.CF_RUP));
				igb.setImportoLotto(rs.getBigDecimal(LOTTO.IMPORTO_LOTTO));
				igb.setSommaUrgenza(rs.getString(LOTTO.SOMMA_URGENZA));
				igb.setDataScadenzaPagamenti(rs.getString(LOTTO.DATA_SCADENZA_PAGAMENTI));
				igb.setDataInvito(rs.getString(LOTTO.DATA_LETTERA_INVITO));//MEV 34183 3.04.8
				igb.setDataScadenzaInvito(rs.getString(LOTTO.DATA_SCADENZA_RICHIESTA_INVITO));//MEV 34183 3.04.8
				igb.setDataCreazione(rs.getString(LOTTO.DATA_CREAZIONE_LOTTO));
				igb.setDataCreazioneGara(rs.getString(GARA.DATA_CREAZIONE));
				igb.setTIPO_SCHEDA_GARA(rs.getString(GARA.TIPO_SCHEDA_GARA));
				igb.setID_MODO_REAL(rs.getInt(GARA.ID_MODO_REAL));			
				igb.setFLAG_ESCLUSO(rs.getString(LOTTO.FLAG_ESCLUSO));
				igb.setID_ESCLUSIONE(rs.getInt(LOTTO.ID_ESCLUSIONE));
				
				igb.setIdPubblicazione(rs.getInt(GARA.ID_PUBBLICAZIONE));
				igb.setDataInizioPubblicazione(rs.getTimestamp(GARA.DATA_INIZIO_PUBB));
				igb.setLUOGO_ISTAT(rs.getString(LOTTO.LUOGO_ISTAT));
				igb.setLUOGO_NUTS(rs.getString(LOTTO.LUOGO_NUTS));
				igb.setIMPORTO_ATTUAZIONE_SICUREZZA(rs.getBigDecimal(LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA));
				igb.setIdOsservatorio(rs.getString(GARA.ID_OSSERVATORIO));
				//gm aggiunto per adesione accordo quadro
				igb.setCIG_ACC_QUADRO(rs.getString(GARA.CIG_ACC_QUADRO));
				igb.setIdGara(rs.getLong(LOTTO.ID_GARA));
				igb.setDataPubblicazione(rs.getString(LOTTO.DATA_PUBBLICAZIONE));
			}
			return igb;
		}
		finally{
			close(rs,stmt);
		}
	}


//	PP non usato public TableBean checkDatiComuni(String cig) throws SQLException{
//		TableBean ritorno = new TableBean();
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		try{
//			stmt = activeConnection.prepareStatement(QUERY_EXIST_DATI_COMUNI);
//			logger.debug("Select dati comuni, query ["+QUERY_EXIST_DATI_COMUNI+"]");
//			
//			stmt.setLong(1, Long.parseLong(cig));
//			stmt.setLong(2,  Long.parseLong(cig));
//			rs = stmt.executeQuery();
//						
//			if(rs.next()){
//				ritorno = new TableBean(rs);
//			} 
//			return ritorno;
//		}
//		finally{
//			close(rs,stmt);
//		}
//	}

	/**
	 * metodo per la verifica dell'esistenza degli info comuni associati al cig
	 * NOTA: il per cig si intende la stringa CIG senza il KKK 
	 * 
	 * @param infoComuni InfoComuniBean
	 * @param cig String
	 * @return boolean 
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean checkDatiComuni(InfoComuniBean infoComuni,String cig) throws SQLException, Exception{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			stmt = activeConnection.prepareStatement(QUERY_EXIST_DATI_COMUNI);
			logger.debug("Select dati comuni, query ["+QUERY_EXIST_DATI_COMUNI+"]");
			
			stmt.setString(1, cig);
			stmt.setString(2,  cig);
			rs = stmt.executeQuery();
						
			if(rs.next()){
			   fillBean(rs, infoComuni);
			   
/*** PP usare fillbean!
 *				infoComuni.setIdInfo(rs.getLong(INFO_AGGIUDICAZIONI.ID_INFO));
				infoComuni.setDataInizioInfo(rs.getTimestamp(INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO));
				infoComuni.setIdStato(rs.getLong(INFO_AGGIUDICAZIONI.ID_STATO));
				//X-XX UN - verifica se la gara e' Aggiudicata (necessaria per aggiungibilita'aggiudicazioni)
				infoComuni.setAggiudicata(rs.getString(INFO_AGGIUDICAZIONI.ESITO_PROCEDURA).equals(EsitoEnum.AGGIUDICATA.codice()));
				infoComuni.setEsitoProcedura(rs.getString(INFO_AGGIUDICAZIONI.ESITO_PROCEDURA));
				infoComuni.setTipoContratto(rs.getString(INFO_AGGIUDICAZIONI.TIPO_CONTRATTO));
				//XX-X:adds 5/3/2009
				infoComuni.setCfAmmAgente(rs.getString(INFO_AGGIUDICAZIONI.CF_AMM_AGENTE));
				infoComuni.setCfAmministrazione(rs.getString(INFO_AGGIUDICAZIONI.CF_AMM));
				infoComuni.setCfRup(rs.getString(INFO_AGGIUDICAZIONI.CF_RUP));
				infoComuni.setCfStazioneAppaltante(rs.getString(INFO_AGGIUDICAZIONI.CF_SA));
				infoComuni.setCigCycle(rs.getInt(INFO_AGGIUDICAZIONI.CIG_CICLE));
				infoComuni.setDenAmmAgente(rs.getString(INFO_AGGIUDICAZIONI.DEN_AMM_AGENTE));
				infoComuni.setDenAmministrazione(rs.getString(INFO_AGGIUDICAZIONI.DEN_AMM));
				infoComuni.setDenomCC(rs.getString(INFO_AGGIUDICAZIONI.DENOM_CC));
				infoComuni.setDenStazioneAppaltante(rs.getString(INFO_AGGIUDICAZIONI.DEN_SA));
				//infoComuni.setDescrizioneStato(rs.getString(INFO_AGGIUDICAZIONI.); // non esite il campo sul db derivato ?
				infoComuni.setFlagEnteSpeciale(rs.getString(INFO_AGGIUDICAZIONI.FLAG_ENTE_SPECIALE));
				infoComuni.setFlagSAAgente(rs.getString(INFO_AGGIUDICAZIONI.FLAG_SA_AGENTE));
				infoComuni.setIdLotto(rs.getLong(INFO_AGGIUDICAZIONI.ID_LOTTO));
				infoComuni.setProvvPresaCarico(rs.getString(INFO_AGGIUDICAZIONI.PROVV_PRESA_CARICO));
				infoComuni.setTipologiaSA(rs.getLong(INFO_AGGIUDICAZIONI.ID_TIPOLOGIA_SA));
				//gm nuovo codice dati comuni
				infoComuni.setTipologiaProcedura(rs.getLong(INFO_AGGIUDICAZIONI.ID_TIPO_PROCEDURA));
				infoComuni.setDurataConvenzione(rs.getInt(INFO_AGGIUDICAZIONI.DURATA_CONVENZIONE));
				infoComuni.setFlagProcedeStipula(rs.getString(INFO_AGGIUDICAZIONI.FLAG_PROCEDE_STIPULA));
			
				//end
				infoComuni.setIdLocale(rs.getString(INFO_AGGIUDICAZIONI.ID_SCHEDA_LOCALE));
***/				
				return true;
			}return false;
			
		}catch(Exception e){
			logger.debug("Errore durante il controllo del cig");
			//e.printStackTrace();
			if(e instanceof SQLException)
				throw (SQLException)e;
			throw e;
		}finally{
			close(rs,stmt);
		}
	}
	
	/**
	 * Metodo introdotto per permettere il recupero dei dati comuni per il massloader
	 * 
	 * @param cig
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public InfoComuniBean getInfoComuniByCig(String cig) throws SQLException, Exception{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		InfoComuniBean infoComuni = new InfoComuniBean();
		infoComuni.setPubblicazione(new PubblicazioneBean());
		
		if(cig != null && cig.length() == 10){
			String numeric_cig = cig.substring(0,7);
			try{
				stmt = activeConnection.prepareStatement(QUERY_EXIST_DATI_COMUNI);
				logger.debug("Select dati comuni, query ["+QUERY_EXIST_DATI_COMUNI+"]");
				
				stmt.setString(1, numeric_cig);
				stmt.setString(2,  numeric_cig);
				rs = stmt.executeQuery();
							
				if(rs.next()){
				   fillBean(rs, infoComuni);
				}			
				
			}catch(SQLException e){
				logger.fatal("Errore durante il recupero dei dati relativi ai dati comuni");
				if(e instanceof SQLException)
					throw (SQLException)e;
				throw e;
			}finally{
				close(rs,stmt);
			}
		}return infoComuni;
	}
	/**
	 * MAC 33380 3.04.8.1 - Metodo introdotto per controllare che non esistano altre Dati Comuni
	 * 
	 * @param cig
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<InfoComuniBean> getListInfoComuniByCig(String cig) throws SQLException, Exception{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<InfoComuniBean> infoComuniList = new ArrayList<InfoComuniBean>();
		
		
		if(cig != null && cig.length() == 10){
			String numeric_cig = cig.substring(0,7);
			try{
				stmt = activeConnection.prepareStatement(QUERY_EXIST_DATI_COMUNI);
				logger.debug("Select dati comuni, query ["+QUERY_EXIST_DATI_COMUNI+"]");
				
				stmt.setString(1, numeric_cig);
				stmt.setString(2,  numeric_cig);
				rs = stmt.executeQuery();
							
				while(rs.next()){
					InfoComuniBean infoComuni = new InfoComuniBean();
					infoComuni.setPubblicazione(new PubblicazioneBean());
					fillBean(rs, infoComuni);
					infoComuniList.add(infoComuni);
				   
				}			
				
			}catch(SQLException e){
				logger.fatal("Errore durante il recupero dei dati relativi ai dati comuni");
				if(e instanceof SQLException)
					throw (SQLException)e;
				throw e;
			}finally{
				close(rs,stmt);
			}
		}return infoComuniList;
	}
	
	public List<InfoComuniBean> getInfoComuniByIdLotto(long idLotto) throws SQLException, Exception{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<InfoComuniBean> listInfoComuni = new ArrayList<InfoComuniBean>();
		
		try{
			stmt = activeConnection.prepareStatement(QUERY_DATI_COMUNI_BY_ID_LOTTO);
			logger.debug("Select dati comuni, query ["+QUERY_DATI_COMUNI_BY_ID_LOTTO+"]");
			
			stmt.setLong(1, idLotto);
			rs = stmt.executeQuery();
			
			while(rs.next()) {			
				InfoComuniBean infoComuni = new InfoComuniBean();
				fillBean(rs, infoComuni);
				listInfoComuni.add(infoComuni);
			}
			
		}catch(SQLException e){
			logger.fatal("Errore durante il recupero dei dati relativi ai dati comuni");
			if(e instanceof SQLException)
				throw (SQLException)e;
			throw e;
		}finally{
			close(rs,stmt);
		}
		
		return listInfoComuni;
	}
	
	
	/*
	 * creazione dati per la richiesta di annullamento
	 */
	/**
	 * metodo per la presa incarico
	 * 
	 * @param idInfo long
	 * @param dataInizioInfo Timestamp 
	 * @param oldDataInizioPub Timestamp
	 * @param newDataInizioPub Timestamp
	 * @return Timestamp - nuova data inizio
	 * @throws SQLException
	 */
	public Timestamp presaInCarico(long idInfo, Timestamp dataInizioInfo, Timestamp oldDataInizioPub, Timestamp newDataInizioPub) throws SQLException{
		return copyRecord(String.valueOf(idInfo), dataInizioInfo, oldDataInizioPub, newDataInizioPub, true);
	}
	/**
	 * metodo per la storicizzazione del record
	 * 
	 * @param id_info String
	 * @param data_inizio_info Timestamp 
	 * @param old_data_inizio_pub Timestamp
	 * @param nuova_data_inizio_pub Timestamp
	 * @return nuova data inizio Timestamp
	 * @throws SQLException
	 */
	public Timestamp copyRecord(String id_info,Timestamp data_inizio_info, Timestamp old_data_inizio_pub, Timestamp nuova_data_inizio_pub) throws SQLException{
		return copyRecord(id_info, data_inizio_info, old_data_inizio_pub, nuova_data_inizio_pub, false);	
	}
	
	/**
	 * @param id_info
	 * @param data_inizio_info
	 * @param old_data_inizio_pub
	 * @param nuova_data_inizio_pub
	 * @param presaInCarico
	 * @return
	 * @throws SQLException
	 */
	private Timestamp copyRecord(String id_info,Timestamp data_inizio_info, Timestamp old_data_inizio_pub, Timestamp nuova_data_inizio_pub, boolean presaInCarico) throws SQLException{
		String orPresaIncaricoDef = " OR " + INFO_AGGIUDICAZIONI.ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE + ")";
		String orPresaIncaricoConf = " OR " + INFO_AGGIUDICAZIONI.ID_STATO + "=" + StatiScheda.CONFERMATO + ")";
		String QUERY_SELECT_DATA_FINE_INFO =
			"SELECT "
			+INFO_AGGIUDICAZIONI.DATA_FINE_INFO +", " + INFO_AGGIUDICAZIONI.ID_STATO
			+" FROM "+INFO_AGGIUDICAZIONI.TABLE_NAME
			+" WHERE "
			+INFO_AGGIUDICAZIONI.T_ID_INFO+"= ?"
			+" AND "+INFO_AGGIUDICAZIONI.T_DATA_INIZIO_INFO+" = ?"
			+" AND ("+INFO_AGGIUDICAZIONI.ID_STATO+" = "+StatiScheda.CONFERMATO + (presaInCarico ? orPresaIncaricoDef : ")") ;
		
		String QUERY_SET_STATO_RICHIESTA_ANNULLAMENTO_INFO =
			"UPDATE "+INFO_AGGIUDICAZIONI.TABLE_NAME+ " SET "
			+INFO_AGGIUDICAZIONI.ID_STATO+ " = ? , "
			+INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO+" = ? , "
			+INFO_AGGIUDICAZIONI.DATA_INIZIO_PUBB+" = ? , "
			+INFO_AGGIUDICAZIONI.DATA_FINE_INFO+" = ?"
			+" WHERE "
			+INFO_AGGIUDICAZIONI.ID_INFO+" = ?"
			+" AND "+INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO+" = ?"
			+" AND ("+INFO_AGGIUDICAZIONI.ID_STATO+" = "+StatiScheda.CONFERMATO + (presaInCarico ? orPresaIncaricoDef : ")") ;
		
		String QUERY_COPY_RECORD_INFO =
			
			"INSERT INTO "+INFO_AGGIUDICAZIONI.TABLE_NAME+" ("
			+INFO_AGGIUDICAZIONI.CIG
			+","+INFO_AGGIUDICAZIONI.CIG_CICLE
			+","+INFO_AGGIUDICAZIONI.ID_LOTTO
			
			+","+INFO_AGGIUDICAZIONI.ID_INFO
			+","+INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO
			+","+INFO_AGGIUDICAZIONI.ID_PUBBLICAZIONE
			+","+INFO_AGGIUDICAZIONI.DATA_INIZIO_PUBB
			+","+INFO_AGGIUDICAZIONI.DATA_FINE_INFO
			+","+INFO_AGGIUDICAZIONI.ID_STATO
			+", "+INFO_AGGIUDICAZIONI.CF_AMM
			+", "+INFO_AGGIUDICAZIONI.DEN_AMM
			+", "+INFO_AGGIUDICAZIONI.CF_SA
			+", "+INFO_AGGIUDICAZIONI.DEN_SA
			+", "+INFO_AGGIUDICAZIONI.ID_CATEG_SA 
			+", "+INFO_AGGIUDICAZIONI.CF_AMM_AGENTE
			+", "+INFO_AGGIUDICAZIONI.DEN_AMM_AGENTE
			+", "+INFO_AGGIUDICAZIONI.FLAG_ENTE_SPECIALE
			+", "+INFO_AGGIUDICAZIONI.TIPO_CONTRATTO
			+", "+INFO_AGGIUDICAZIONI.CODICE_CC
			+", "+INFO_AGGIUDICAZIONI.DENOM_CC
			+", "+INFO_AGGIUDICAZIONI.FLAG_SA_AGENTE
			+", "+INFO_AGGIUDICAZIONI.ID_TIPOLOGIA_SA
		    //gm nuovo codice dati comuni
			+", "+INFO_AGGIUDICAZIONI.ID_TIPO_PROCEDURA
			+", "+INFO_AGGIUDICAZIONI.DURATA_CONVENZIONE
			+", "+INFO_AGGIUDICAZIONI.FLAG_PROCEDE_STIPULA
			
			+", "+INFO_AGGIUDICAZIONI.CF_RUP
			+", "+INFO_AGGIUDICAZIONI.PROVV_PRESA_CARICO
			+", "+INFO_AGGIUDICAZIONI.ESITO_PROCEDURA
			+", "+INFO_AGGIUDICAZIONI.ID_SCHEDA_LOCALE
			+ (SimogFlags.is3028_RFWEBSC00Active() ? ", "+INFO_AGGIUDICAZIONI.ORIGINE : "")
			+ " ) "
			+"SELECT "
			+INFO_AGGIUDICAZIONI.CIG
			+","+INFO_AGGIUDICAZIONI.CIG_CICLE
			+","+INFO_AGGIUDICAZIONI.ID_LOTTO

			+", ?"
			+", ?"
			+", " + INFO_AGGIUDICAZIONI.ID_PUBBLICAZIONE
			+", ?"
			+", ?"
			+", ?"
			+", "+INFO_AGGIUDICAZIONI.CF_AMM
			+", "+INFO_AGGIUDICAZIONI.DEN_AMM
			+", "+INFO_AGGIUDICAZIONI.CF_SA
			+", "+INFO_AGGIUDICAZIONI.DEN_SA
			+", "+INFO_AGGIUDICAZIONI.ID_CATEG_SA 
			+", "+INFO_AGGIUDICAZIONI.CF_AMM_AGENTE
			+", "+INFO_AGGIUDICAZIONI.DEN_AMM_AGENTE
			+", "+INFO_AGGIUDICAZIONI.FLAG_ENTE_SPECIALE
			+", "+INFO_AGGIUDICAZIONI.TIPO_CONTRATTO
			+", "+INFO_AGGIUDICAZIONI.CODICE_CC
			+", "+INFO_AGGIUDICAZIONI.DENOM_CC
			+", "+INFO_AGGIUDICAZIONI.FLAG_SA_AGENTE
			+", "+INFO_AGGIUDICAZIONI.ID_TIPOLOGIA_SA
			//gm nuovo codice dati comuni
			+", "+INFO_AGGIUDICAZIONI.ID_TIPO_PROCEDURA
			+", "+INFO_AGGIUDICAZIONI.DURATA_CONVENZIONE
			+", "+INFO_AGGIUDICAZIONI.FLAG_PROCEDE_STIPULA
			
			+", "+INFO_AGGIUDICAZIONI.CF_RUP
			+", "+INFO_AGGIUDICAZIONI.PROVV_PRESA_CARICO
			+", "+INFO_AGGIUDICAZIONI.ESITO_PROCEDURA
			+", "+INFO_AGGIUDICAZIONI.ID_SCHEDA_LOCALE
			+ (SimogFlags.is3028_RFWEBSC00Active() ? ", "+INFO_AGGIUDICAZIONI.ORIGINE : "")
			+" FROM "+INFO_AGGIUDICAZIONI.TABLE_NAME
			+" WHERE "
			+INFO_AGGIUDICAZIONI.ID_INFO+" = ?"
			+" AND "+INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO+"= ?"
			+" AND ("+INFO_AGGIUDICAZIONI.ID_STATO+" = "+StatiScheda.IN_DEFINIZIONE+ (presaInCarico ? orPresaIncaricoConf : ")") ;
		
	
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		ResultSet rs = null;
		int index = 1;
		
		try{
			Timestamp nuova_data_inizio = getNow();
			//Select DataFineInfo
			stmt = activeConnection.prepareStatement(QUERY_SELECT_DATA_FINE_INFO);
			stmt.setLong(index++, Long.parseLong(id_info));
			stmt.setTimestamp(index++, data_inizio_info);
			logger.debug("SELECT DATA_FINE_INFO [ "+QUERY_SELECT_DATA_FINE_INFO+" ]");
			rs = stmt.executeQuery();
			Timestamp dataFine = null;
			int stato = StatiScheda.IN_DEFINIZIONE;
			if(rs.next()){
				index = 1;
				dataFine = rs.getTimestamp(INFO_AGGIUDICAZIONI.DATA_FINE_INFO);
				
				// se presa in carico su scheda in definizione la data fine è null imposto quella odierna
				if (presaInCarico && dataFine == null)
					dataFine = getNow();
					
				stato = rs.getInt(INFO_AGGIUDICAZIONI.ID_STATO);
				//Modifica vecchio record		
				stmt3 = activeConnection.prepareStatement(QUERY_SET_STATO_RICHIESTA_ANNULLAMENTO_INFO);
				stmt3.setLong(index++, (presaInCarico ? stato : StatiScheda.IN_DEFINIZIONE));
				stmt3.setTimestamp(index++, nuova_data_inizio);
				stmt3.setTimestamp(index++, nuova_data_inizio_pub);
				// MOD: SIMOG-32 UN 15/04/09 Impostare DataFine in caso di presa in carico 
				stmt3.setTimestamp(index++, (presaInCarico ? nuova_data_inizio : null)); // PP datafine
				stmt3.setLong(index++, Long.parseLong(id_info));
				stmt3.setTimestamp(index++, data_inizio_info);
				logger.debug("ESECUZIONE SET STATO INFO [ "+QUERY_SET_STATO_RICHIESTA_ANNULLAMENTO_INFO+" ]");
				stmt3.execute();
				
				//Inserimento nuovo record
				index = 1;
				stmt2 = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD_INFO,INFO_AGGIUDICAZIONI.TABLE_NAME));
				stmt2.setLong(index++, Long.parseLong(id_info));
				stmt2.setTimestamp(index++, data_inizio_info);
				stmt2.setTimestamp(index++, old_data_inizio_pub);
				stmt2.setTimestamp(index++, dataFine);
				stmt2.setLong(index++,(presaInCarico ? StatiScheda.PRESA_IN_CARICO : StatiScheda.ANNULLAMENTO_RICHIESTA));
				stmt2.setLong(index++,Long.parseLong(id_info));
				stmt2.setTimestamp(index++, nuova_data_inizio);
				logger.debug("ESECUZIONE COPIA INFO [ "+QUERY_COPY_RECORD_INFO+" ]");
				stmt2.execute();				
			}
			else return null;
			return nuova_data_inizio;
		}
		finally{
			close(rs,stmt);
			close(null,stmt2);
			close(null,stmt3);
		}
		

	}
	private final  String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_INFO_COMUNI = 
		"UPDATE "+INFO_AGGIUDICAZIONI.TABLE_NAME+
		" SET " + INFO_AGGIUDICAZIONI.ID_STATO + " = ?,"+
		INFO_AGGIUDICAZIONI.DATA_FINE_INFO + " = " + buildGetDate()	+
		" WHERE "+INFO_AGGIUDICAZIONI.ID_INFO + " = ?"+
		" AND "+INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO + " = ?";
	//Metodo utilizzato dall'amministratore per modificare il record con lo stato "Richiesta Di Annullamento" impostando lo stato
	//a "Confermato"
	/**
	 * Metodo utilizzato dall'amministratore per modificare il record con lo stato "Richiesta Di Annullamento" 
	 * impostando lo stato a "Confermato"
	 * 
	 * @param idRecord String
	 * @param dataInizioRecord Timestamp
	 * @param stato_scheda String
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int updateRecord(String idRecord, Timestamp dataInizioRecord, String stato_scheda ) throws SQLException{
		
		int numRow=-1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_INFO_COMUNI);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_INFO_COMUNI);
			

			stmt.setObject(1, stato_scheda);
			logger.debug(1 + ": "+stato_scheda);
			
			stmt.setLong(2, Long.parseLong(idRecord));
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
	//	Metodo utilizzato dall'amministratore per modificare il record con lo stato "Richiesta Di Annullamento" impostando lo stato
	//a "Confermato"
	private final  String QUERY_UPDATE_AGGIUDICAZIONE = 
		"UPDATE "+AGGIUDICAZIONI.TABLE_NAME+
		" SET " + AGGIUDICAZIONI.DATA_INIZIO_INFO + " = ? "+ 
		" WHERE " + AGGIUDICAZIONI.ID_INFO + " = ?"+
		" AND "+AGGIUDICAZIONI.DATA_INIZIO_INFO + " = ?";
	/**
	 * [private]
	 * metodo per l'aggiornamento del record di aggiudicazione
	 * 
	 * @param idInfoComuni String
	 * @param data_inizio_info Timestamp
	 * @param dataRecDaAnnullare Timestamp
	 * @return int - affected row count
	 * @throws SQLException
	 */
	private int updateRecordAggiudicazione(String idInfoComuni, Timestamp data_inizio_info, Timestamp dataRecDaAnnullare) throws SQLException{
		
		int numRow = -1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_AGGIUDICAZIONE);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_AGGIUDICAZIONE);
			
			
			stmt.setTimestamp(1,dataRecDaAnnullare);
			logger.debug(1 + "::: "+dataRecDaAnnullare);
			
			stmt.setLong(2, Long.parseLong(idInfoComuni));
			logger.debug(2 + ":::::: "+idInfoComuni);
			
			stmt.setTimestamp(3, data_inizio_info);
			logger.debug(3 + ":::::: "+data_inizio_info);

			numRow = stmt.executeUpdate();
			
			
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}

	private final  String QUERY_DELETE_INFO_COMUNI = 
		"DELETE FROM "+INFO_AGGIUDICAZIONI.TABLE_NAME+
		" WHERE " + INFO_AGGIUDICAZIONI.ID_INFO + " = ?"+
		" AND "+INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO + " = ?";
	//	Metodo utilizzato dall'amministratore per cancellare il record attivo qualora venisse rifiutata
	//  la richiesta di annullamento ad esso relativa
	/**
	 * [private]
	 * Metodo utilizzato dall'amministratore per cancellare il record attivo qualora venisse rifiutata 
	 * la richiesta di annullamento ad esso relativa
	 * 
	 * param idRecord String
	 * param dataInizioRecord Timestamp
	 * return int - affected row count
	 * throws SQLException
	 */
	private int deleteRecordInfoComuni(String idRecord, Timestamp dataInizioRecord) throws SQLException{
		
		int numRow = -1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_DELETE_INFO_COMUNI);
			logger.debug("query per la delete record attivo: "+QUERY_DELETE_INFO_COMUNI);
			

			logger.debug(1 + " idRecord :::::: "+idRecord);
			stmt.setLong(1, Long.parseLong(idRecord));
			
			stmt.setTimestamp(2,dataInizioRecord);
			logger.debug(2 + "dataInizioRecord ::: "+dataInizioRecord);
			
			numRow = stmt.executeUpdate();
			 
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}
	/*
	 * rifiuto richiesta annullamento (dati comuni)
	 */
	/**
	 * metodo per il rifiuto richiesta annullamento (dati comuni)
	 * 
	 * @param idInfoComuni String
	 * @param dataInizioInfoComuni Timestamp
	 * @param dataRecordDaAnnullare Timestamp
	 * @return int - affected row count
	 * @throws SQLException
	 */
	public int deleteRecordInfoAggiudicazioni(String idInfoComuni, Timestamp dataInizioInfoComuni, Timestamp dataRecordDaAnnullare) throws SQLException{
		int numRow=-1;
		//modifico tutti i record di aggiudicazioni che fanno riferimento al record di info_aggiudicazioni preso in considerazione
		numRow = updateRecordAggiudicazione(idInfoComuni, dataInizioInfoComuni, dataRecordDaAnnullare);
		
		
		numRow=deleteRecordInfoComuni(idInfoComuni, dataInizioInfoComuni);		
		
		return numRow;
	}
//	private final String QUERY_UPDATE_PUBBLICAZIONI = 
//		"UPDATE "+PUBBLICAZIONI.TABLE_NAME+
//		" SET " + PUBBLICAZIONI.ID_STATO + " = ?"+ //+StatiScheda.CONFERMATO+
//		" WHERE "+PUBBLICAZIONI.ID_PUBBLICAZIONE + " = ?"+
//		" AND "+PUBBLICAZIONI.DATA_INIZIO_PUBB + " = ?";
	
	/*
	 * aggiornamento pubblicazioni
	 */
	/**
	 * metodo per l'aggiornamento pubblicazioni
	 * 
	 * @param idPubblicazioni String
	 * @param data_inizio_pubb Timestamp
	 * @param stato_scheda String
	 * @return int - affected row count
	 * @throws SQLException
	 */
//	public int updateRecordPubblicazioni(String idPubblicazioni, Timestamp data_inizio_pubb, String stato_scheda) throws SQLException{
//		
//		int numRow = -1;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		try {
//			stmt = activeConnection.prepareStatement(QUERY_UPDATE_PUBBLICAZIONI);
//			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_PUBBLICAZIONI);
//			
//			
//			stmt.setObject(1,Integer.parseInt(stato_scheda));
//			logger.debug(1 + "::: "+Integer.parseInt(stato_scheda));
//			
//			stmt.setLong(2, Long.parseLong(idPubblicazioni));
//			logger.debug(2 + ":::::: "+idPubblicazioni);
//			
//			stmt.setTimestamp(3, data_inizio_pubb);
//			logger.debug(3 + ":::::: "+data_inizio_pubb);
//
//			numRow = stmt.executeUpdate();
//			
//			
//		} finally {
//			close(rs, stmt);
//		}
//		return numRow;
//	}
		
			
			
			
	private final String QUERY_CONFIRM_INFOAGG = "UPDATE " + INFO_AGGIUDICAZIONI.TABLE_NAME 
		+ " SET " 
		+ INFO_AGGIUDICAZIONI.ID_STATO + "=?, "  + INFO_AGGIUDICAZIONI.DATA_FINE_INFO + "= ? "
		+ " WHERE " + INFO_AGGIUDICAZIONI.ID_INFO  + "=? AND " 
		+ INFO_AGGIUDICAZIONI.DATA_FINE_INFO + " is null";
	private final String QUERY_CONFIRM_PUBB = "UPDATE " + PUBBLICAZIONI.TABLE_NAME 
		+ " SET " 
		+ PUBBLICAZIONI.ID_STATO + "=?, "  + PUBBLICAZIONI.DATA_FINE_PUBB + "= ? "
		+ " WHERE " + PUBBLICAZIONI.ID_PUBBLICAZIONE  + "=? AND " 
		+ PUBBLICAZIONI.DATA_FINE_PUBB + " is null";
	/*
	 * solo conferma dei dati comuni completi, usato da massloader
	 */
	/**
	 * metodo per la conferma dei dati comuni completi, usato da massloader
	 * 
	 * @param idInfoComuni long
	 * @param dataInizioInfo Timestamp
	 * @param idPubblicazione long
	 * @param user String
	 * @throws SQLException
	 */
	public void confirm(long idInfoComuni, Timestamp dataInizioInfo,long idPubblicazione, String user) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_CONFIRM_INFOAGG);			
			
			stmt.setLong(1, StatiScheda.CONFERMATO);
			stmt.setTimestamp(2, getNow());
			stmt.setLong(3, idInfoComuni);
		
			stmt.executeUpdate();
			
			stmt = activeConnection.prepareStatement(QUERY_CONFIRM_PUBB);			
			
			stmt.setLong(1, StatiScheda.CONFERMATO);
			stmt.setTimestamp(2, getNow());
			stmt.setLong(3, idPubblicazione);
		
			stmt.executeUpdate();
			
			// scrittura sul LOG_OPERAZIONI
			List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(idInfoComuni);
			attributiChiave.add(dataInizioInfo);			
			LogBloccoDatiManager.loggingCONFIRM(activeConnection, logger, user, IdentificativoSchede.TAB_INFO_COMUNI, attributiChiave);
			
		} finally {
			close(rs, stmt);
		}
	}
	
	
	/**
	 * metodo per il caricamento dei tipi di ente disponibili
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validita' di una tipologia a posteriori
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException
	 */
	public Map<String,String> loadTipiEnte(Object o) throws SQLException{
		 return getTipologica(TIPI_CATEGORIA.TABLE_NAME, TIPI_CATEGORIA.ID_TIPO_CATEGORIA, TIPI_CATEGORIA.DESCRIZIONE, TIPI_CATEGORIA.DATA_FINE_VALIDITA,o);
		
	}
	
	
	/**
	 * metodo per il caricamento dei modi di realizzazione
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validita' di una tipologia a posteriori
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException
	 */
	public Map<String,String> loadModiReal(Object o) throws SQLException{
		 return getTipologicaWithData(MODI_REALIZZAZIONE.TABLE_NAME, MODI_REALIZZAZIONE.ID_MODO_REAL, MODI_REALIZZAZIONE.DESCRIZIONE, MODI_REALIZZAZIONE.DATA_INIZIO_VALIDITA, MODI_REALIZZAZIONE.DATA_FINE_VALIDITA,o);
		
	}
	
	/**
	 * metodo per il caricamento degli articoli esclusione
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validita' di una tipologia a posteriori
	 * @param b 
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException
	 */
	public Map<String,String> loadArtEsclusione(Object o, boolean b) throws SQLException{
		//TICKET ALM - 3.04.2 2005
		if(SimogFlags.is3042Active())
			 return getTipologicaDescrWithDP(ART_ESCLUSIONE.TABLE_NAME, ART_ESCLUSIONE.ID_ESCLUSIONE, ART_ESCLUSIONE.DESCRIZIONE, ART_ESCLUSIONE.DATA_FINE_VALIDITA,ART_ESCLUSIONE.DATA_INIZIO_VALIDITA, o, b, ART_ESCLUSIONE.REGIME_ESCLUSIONE,"E");
		else
		 return getTipologicaDescr(ART_ESCLUSIONE.TABLE_NAME, ART_ESCLUSIONE.ID_ESCLUSIONE, ART_ESCLUSIONE.DESCRIZIONE, ART_ESCLUSIONE.DATA_FINE_VALIDITA,o, b);
		//FINE TICKET ALM 3.04.2 2005
	}
	
	/**
	 * metodo il caricamento delle categorie stazioni appaltanti
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validita' di una tipologia a posteriori
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException
	 */
	public Map<String,String> loadCategoriaSA(Object o) throws SQLException{
		return getTipologica(CATEGORIA_SA.TABLE_NAME, CATEGORIA_SA.ID_CATEG_SA, CATEGORIA_SA.DESCRIZIONE, CATEGORIA_SA.DATA_FINE_VALIDITA,o);
		
	}
	
	/**
	 * metodo per il recupero delle tipologie stazioni appaltanti
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validita' di una tipologia a posteriori
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException
	 */
	public Map<String,String> loadTipologieSA(Object o) throws SQLException{
		return getTipologica(TIPOLOGIA_SA.TABLE_NAME, TIPOLOGIA_SA.ID_TIPOLOGIA_SA, TIPOLOGIA_SA.DESCRIZIONE, TIPOLOGIA_SA.DATA_FINE_VALIDITA,o);
		
	}
	
	/**
	 * metodo per il recupero delle tipologie procedura
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validita' di una tipologia a posteriori
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException
	 */
	public Map<String,String> loadTipologieProcedura(Object o) throws SQLException{
		return getTipologica(TIPOLOGIA_PROCEDURA.TABLE_NAME, TIPOLOGIA_PROCEDURA.ID_TIPOLOGIA_PROCEDURA, TIPOLOGIA_PROCEDURA.DESCRIZIONE, TIPOLOGIA_PROCEDURA.DATA_FINE_VALIDITA,o);
		
	}
	
	/*	METOD PER LA MODIFICA DELL'UTENTE RUP ASSOCIATO A UNA GARA..*/
	private final String QUERY_UPDATE_INFO_AGGIUDICAZIONI_RUP =
		"UPDATE "+INFO_AGGIUDICAZIONI.TABLE_NAME+
		" SET " + INFO_AGGIUDICAZIONI.PROVV_PRESA_CARICO + " = ?,"+ 
		INFO_AGGIUDICAZIONI.CF_RUP + " = ?"+ 
		" WHERE " + INFO_AGGIUDICAZIONI.ID_INFO + " = ?"+
		" AND "+AGGIUDICAZIONI.DATA_INIZIO_INFO + " = ?";
	
	/**
	 * metodo per l'aggiornamento del rup relativo ai dati comuni
	 * 
	 * @param icb InfoComuniBean
	 * @param cfUtente String
	 * @throws SQLException
	 */
	public void updateRUP(InfoComuniBean icb,String cfUtente)throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_INFO_AGGIUDICAZIONI_RUP);
			logger.debug("query per l'UPDATE del rup associato alla gara :::"+QUERY_UPDATE_INFO_AGGIUDICAZIONI_RUP);		
			stmt.setString(1,icb.getProvvPresaCarico());
			logger.debug(1 + "ProvvPresaCarico :::::: "+icb.getProvvPresaCarico());
			stmt.setString(2,cfUtente.toUpperCase());
			logger.debug(2 + "CfRup :::::: "+cfUtente.toUpperCase());
			stmt.setLong(3, icb.getIdInfo());
			logger.debug(3 + "IdInfo :::::: "+icb.getIdInfo());
			stmt.setTimestamp(4, icb.getDataInizioInfo());
			logger.debug(4 + "DataInizioInfo() :::::: "+icb.getDataInizioInfo());			
			stmt.executeUpdate();
			
		} finally {
			close(rs, stmt);
		}
		
	}

	public void updateStato(InfoComuniBean icb,long idStato)throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		final String QUERY_UPD_STATO = "UPDATE "+INFO_AGGIUDICAZIONI.TABLE_NAME+" SET "+INFO_AGGIUDICAZIONI.ID_STATO+" = ? "
				+ "WHERE "+INFO_AGGIUDICAZIONI.ID_INFO+" = ? and "+INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO+" = ?";
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPD_STATO);
			stmt.setLong(1,idStato);
			stmt.setLong(2,icb.getIdInfo());
			stmt.setTimestamp(3, icb.getDataInizioInfo());
			stmt.executeUpdate();
			
		} finally {
			close(rs, stmt);
		}
		
	}


	/*********************************************************************************************************/
	/**************************		NUOVE FUNZIONALITA' 	**************************************************/
	/*********************************************************************************************************/

/**************************		LOAD 	**************************************************/	
	/**
	 * see it.avcp.simog.managers.comportamento.caricamento.ILoadInfoComuni#load(long, java.sql.Timestamp)
	 */
	public InfoComuniBean load(long idInfoComuni, Timestamp dataInizioInfo) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		InfoComuniBean infoComuni = new InfoComuniBean();
		infoComuni.setPubblicazione(new PubblicazioneBean());
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_DATI_COMUNI + WHERE_STANDARD);
			logger.debug("query: "+QUERY_SELECT_DATI_COMUNI + WHERE_STANDARD+"]");
			stmt.setLong(1, idInfoComuni);
			stmt.setTimestamp(2, dataInizioInfo);
			rs = stmt.executeQuery();
			if(rs.next()){
				fillBean(rs, infoComuni);
	}
			
		}finally{
			close(rs, stmt);
		}
		return infoComuni;
	}
	
	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadInfoComuni#loadByIdLocale(java.lang.String, java.lang.String)
	 */
	public InfoComuniBean loadByIdLocale(String idLocale, String cig) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		InfoComuniBean infoComuni = new InfoComuniBean();
		infoComuni.setPubblicazione(new PubblicazioneBean());
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_DATI_COMUNI + WHERE_IDLOCALE);
			stmt.setString(1, idLocale);
			stmt.setString(2, cig);
			rs = stmt.executeQuery();
			if(rs.next()){
				fillBean(rs, infoComuni);
			}
			
		}finally{
			close(rs, stmt);
		}
		return infoComuni;
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadInfoComuni#loadByIdSimog(long)
	 */
	public InfoComuniBean loadByIdSimog(long idSimog) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		InfoComuniBean infoComuni = new InfoComuniBean();
		infoComuni.setPubblicazione(new PubblicazioneBean());
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_DATI_COMUNI + WHERE_IDSIMOG);
			stmt.setLong(1, idSimog);
			rs = stmt.executeQuery();
			if(rs.next()){
				fillBean(rs, infoComuni);
			}
			
		}finally{
			close(rs, stmt);
		}
		return infoComuni;
	}

/**************************		VALORIZZAZIONE BEAN 	**************************************************/	

	/**
	 * @see it.avcp.simog.managers.comportamento.caricamento.ILoadInfoComuni#fillBean(java.sql.ResultSet, it.avlp.simog.beans.InfoComuniBean)
	 */
		
	public void fillBean(ResultSet rs, InfoComuniBean infoComuni) throws SQLException{
		infoComuni.setIdInfo(rs.getLong(INFO_AGGIUDICAZIONI.ID_INFO));
		infoComuni.setDataInizioInfo(rs.getTimestamp(INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO));
		infoComuni.setIdLotto(rs.getLong(INFO_AGGIUDICAZIONI.ID_LOTTO));
		infoComuni.setCfAmmAgente(rs.getString(INFO_AGGIUDICAZIONI.CF_AMM_AGENTE));
		infoComuni.setCfAmministrazione(rs.getString(INFO_AGGIUDICAZIONI.CF_AMM));
		infoComuni.setCfStazioneAppaltante(rs.getString(INFO_AGGIUDICAZIONI.CF_SA));
		infoComuni.setCig(rs.getString(INFO_AGGIUDICAZIONI.CIG));
		infoComuni.setCigCycle(rs.getInt(INFO_AGGIUDICAZIONI.CIG_CICLE));
		infoComuni.setDenAmmAgente(rs.getString(INFO_AGGIUDICAZIONI.DEN_AMM_AGENTE));
		infoComuni.setDenAmministrazione(rs.getString(INFO_AGGIUDICAZIONI.DEN_AMM));
		infoComuni.setDenStazioneAppaltante(rs.getString(INFO_AGGIUDICAZIONI.DEN_SA));
		infoComuni.setFlagEnteSpeciale(rs.getString(INFO_AGGIUDICAZIONI.FLAG_ENTE_SPECIALE));				
		infoComuni.setIdCategSa(rs.getString(INFO_AGGIUDICAZIONI.ID_CATEG_SA));
		/* warning i due settaggi qua sotto permettono di recuperare in un secondo tempo i dati della pubblicazioni sono le FK*/
		if(existCol(rs, INFO_AGGIUDICAZIONI.ID_PUBBLICAZIONE) && rs.getObject(INFO_AGGIUDICAZIONI.ID_PUBBLICAZIONE)!=null){
		   if (infoComuni.getPubblicazione() == null)
		      infoComuni.setPubblicazione(new PubblicazioneBean());
		   
		   infoComuni.getPubblicazione().setIdPubblicazione(rs.getLong(INFO_AGGIUDICAZIONI.ID_PUBBLICAZIONE));
		   infoComuni.getPubblicazione().setDataInizioPubblicazione(rs.getTimestamp(PUBBLICAZIONI.DATA_INIZIO_PUBB));
		   if(existCol(rs, PUBBLICAZIONI.LINK_AFFIDAMENTO_DIRETTO)) {
			   infoComuni.getPubblicazione().setLinkAffidamentoDiretto(rs.getString(PUBBLICAZIONI.LINK_AFFIDAMENTO_DIRETTO)); //MEV 37523 3.04.8.1
				
		   }
		}
		/*------------end----------------*/
		infoComuni.setIdStato(rs.getLong(INFO_AGGIUDICAZIONI.ID_STATO));
		
		if(existCol(rs, STATI_SCHEDA.DESCRIZIONE))
		   infoComuni.setDescrizioneStato(rs.getString(STATI_SCHEDA.DESCRIZIONE));	
		
		infoComuni.setTipoContratto(rs.getString(INFO_AGGIUDICAZIONI.TIPO_CONTRATTO));
		infoComuni.setCodiceCC(rs.getString(INFO_AGGIUDICAZIONI.CODICE_CC));
		infoComuni.setDenomCC(rs.getString(INFO_AGGIUDICAZIONI.DENOM_CC));
		infoComuni.setFlagSAAgente(rs.getString(INFO_AGGIUDICAZIONI.FLAG_SA_AGENTE));
		infoComuni.setTipologiaSA(rs.getLong(INFO_AGGIUDICAZIONI.ID_TIPOLOGIA_SA));
		//gm nuovo codice dati comuni
		infoComuni.setTipologiaProcedura(rs.getLong(INFO_AGGIUDICAZIONI.ID_TIPO_PROCEDURA));
		infoComuni.setDurataConvenzione(rs.getInt(INFO_AGGIUDICAZIONI.DURATA_CONVENZIONE));
		infoComuni.setFlagProcedeStipula(rs.getString(INFO_AGGIUDICAZIONI.FLAG_PROCEDE_STIPULA));
		
		infoComuni.setCfRup(rs.getString(INFO_AGGIUDICAZIONI.CF_RUP));
		infoComuni.setEsitoProcedura(rs.getString(INFO_AGGIUDICAZIONI.ESITO_PROCEDURA));
		infoComuni.setIdLocale(rs.getString(INFO_AGGIUDICAZIONI.ID_SCHEDA_LOCALE));
		
        if(existCol(rs, GARA.ID_MODO_REAL))
           infoComuni.setID_MODO_REAL(rs.getInt(GARA.ID_MODO_REAL));
		
        if(existCol(rs, LOTTO.FLAG_ESCLUSO)){
           infoComuni.setFLAG_ESCLUSO(rs.getString(LOTTO.FLAG_ESCLUSO));
           infoComuni.setID_ESCLUSIONE(rs.getInt(LOTTO.ID_ESCLUSIONE));
        }
        
		// verifica se esistono aggiudicazioni
		AggiudicazioniManager am = new AggiudicazioniManager(activeConnection,logger);
		List<AggiudicazioneBean> lab = am.getAggiudicazioniList(infoComuni.getIdInfo(), infoComuni.getDataInizioInfo());
		infoComuni.setHasAwards(lab.size()>0);
	
		infoComuni.setAggiudicata(EsitoEnum.AGGIUDICATA.codice().equals(rs.getString(INFO_AGGIUDICAZIONI.ESITO_PROCEDURA)));
		
		   infoComuni.setOrigine( rs.getInt(INFO_AGGIUDICAZIONI.ORIGINE) );
		
	}

/**************************		ANNULLAMENTO 	**************************************************/	
	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean annulla(String idLocale, String cig, String cfUtente) throws SQLException{
		InfoComuniBean icb = loadByIdLocale(idLocale, cig);
		
		if (icb.getIdInfo() > 0){
			return _annulla(icb.getIdInfo(), icb.getDataInizioInfo(), cfUtente);
			}
		return false;
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(long, java.lang.String)
	 */
	public boolean annulla(long idSimog, String cfUtente) throws SQLException{
		InfoComuniBean icb = loadByIdSimog(idSimog);
		
		if (icb.getIdInfo() > 0){
			return _annulla(icb.getIdInfo(), icb.getDataInizioInfo(), cfUtente);
	}
		return false;
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annulla(long, java.sql.Timestamp, java.lang.String)
	 */
	public boolean annulla(long idInfo, Timestamp dataInizioInfo, String cfUtente) throws SQLException{
		return _annulla(idInfo, dataInizioInfo, cfUtente);
}
	/**
	 * @param idInfo
	 * @param dataInizioInfo
	 * @param cfUtente
	 * @throws SQLException
	 */
	private boolean _annulla(long idInfo, Timestamp dataInizioInfo, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_INFO_COMUNI);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idInfo);
			stmt.setTimestamp(index++, dataInizioInfo);
			someRowAffected = stmt.executeUpdate() > 0;
			if(someRowAffected){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idInfo);
				attributiChiave.add(dataInizioInfo);
					LogBloccoDatiManager.loggingDELETE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_INFO_COMUNI, attributiChiave);
			}
			return someRowAffected;
		}
		finally {
			close(null,stmt);
		}

	}

}