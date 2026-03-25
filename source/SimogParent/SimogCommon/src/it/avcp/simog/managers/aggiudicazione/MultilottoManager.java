package it.avcp.simog.managers.aggiudicazione;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.STATI_SCHEDA;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Classe che si occupa della lettura/scrittura per l'entit&agrave; aggiudicazione
 *
 */
public class MultilottoManager extends AccessiDB {
	
	public static String CLAZZ = "MultilottoManager";
	
	public MultilottoManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	private final String CAMPI_AGGIUDICAZIONE = "" + 
    	AGGIUDICAZIONI.ID_AGGIUDICAZIONE + ", " +
    	AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE + ", " +
	    AGGIUDICAZIONI.ID_INFO + ", " + 
	    AGGIUDICAZIONI.DATA_INIZIO_INFO + ", " + 
    	AGGIUDICAZIONI.DATA_VERBALE_AGGIUDICAZIONE + ", " +
    	AGGIUDICAZIONI.SOTTOTIPO + ", " +
    	AGGIUDICAZIONI.PROG_CUI_RIAGGIUDICATO +","+
    	AGGIUDICAZIONI.MODALITA_RIAGGIUDICAZIONE +","+
    	AGGIUDICAZIONI.DATA_FINE_AGGIUDICAZIONE + ", " + 	
    	AGGIUDICAZIONI.CUI + ", " + 
    	AGGIUDICAZIONI.PROG_CUI + ", " + 
    	AGGIUDICAZIONI.T_ID_STATO + ", " + 	 
    	AGGIUDICAZIONI.LUOGO_ISTAT + ", " + 
    	AGGIUDICAZIONI.LUOGO_NUTS + ", " + 
    	//gm nuovo per appalti multilotto
    	AGGIUDICAZIONI.CODICE_CONTRATTO + ", " +
    	AGGIUDICAZIONI.FLAG_AGGIUD_PRINCIPALE +", " + 
    	AGGIUDICAZIONI.IMPORTO_LAVORI +", " + 
        AGGIUDICAZIONI.IMPORTO_SERVIZI +", " + 
        AGGIUDICAZIONI.IMPORTO_FORNITURE +", " + 
        AGGIUDICAZIONI.IMPORTO_AGGIUDICAZIONE +", " + 
        AGGIUDICAZIONI.IMPORTO_COMPLESSIVO +", " + 
        AGGIUDICAZIONI.IMPORTO_ATTUAZIONE_SICUREZZA +", " + 
        AGGIUDICAZIONI.IMPORTO_PROGETTAZIONE +", " + 
        AGGIUDICAZIONI.IMPORTO_DISPOSIZIONE +", " + 
        AGGIUDICAZIONI.IMP_NON_ASSOG ;
	
	private final String QUERY_SELECT_MULTILOTTO_BY_CODICE_E_IDLOTTO = 
		"SELECT " + CAMPI_AGGIUDICAZIONE + ", " +
		STATI_SCHEDA.T_DESCRIZIONE +
		" FROM " + AGGIUDICAZIONI.TABLE_NAME + ", " + STATI_SCHEDA.TABLE_NAME +
		" WHERE " +
		AGGIUDICAZIONI.CODICE_CONTRATTO + " = ? " +
		" AND " +
		"(" + AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE +
		" OR " + AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.CONFERMATO + ")" +
		" AND " +
		AGGIUDICAZIONI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO +
		" AND " +
		AGGIUDICAZIONI.T_ID_INFO + " IN " +
		"(SELECT DISTINCT " +
		INFO_AGGIUDICAZIONI.T_ID_INFO + " FROM " + INFO_AGGIUDICAZIONI.TABLE_NAME +
		" WHERE " +
		INFO_AGGIUDICAZIONI.T_ID_LOTTO + " IN " +
		"(SELECT DISTINCT " +
		LOTTO.T_ID_LOTTO + " FROM " + LOTTO.TABLE_NAME +
		" WHERE " +
		LOTTO.T_ID_GARA + " = " +
		"(SELECT " +
		LOTTO.T_ID_GARA + " FROM " + LOTTO.TABLE_NAME +
		" WHERE " +
		LOTTO.T_ID_LOTTO + " = ? " +
	    ")))";
	
	/**
	 * metodo per il recupero di tutte le aggiudicazioni inerenti ai lotti di una 
	 * stessa gara che hanno codice contratto e aggiudicatario in comune
	 * 
	 * @param codiceContratto
	 * @param idLotto
	 * @return List&lt;AggiudicazioneBean&gt; - lista di aggiudicazioni
	 */
	public List<AggiudicazioneBean> getAggiudicazioniListMultilotto(String codiceContratto, long idLotto) {
		ArrayList<AggiudicazioneBean> listaAgg = new ArrayList<AggiudicazioneBean>();
		AggiudicazioneBean agg = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		AggiudicazioniManager man = new AggiudicazioniManager(activeConnection, logger);
		
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_MULTILOTTO_BY_CODICE_E_IDLOTTO);
			stmt.setString(1, codiceContratto);
			stmt.setLong(2, idLotto);
			rs = stmt.executeQuery();
			while(rs.next()){
			   // PP 18.12.2015 controllo che non sia stata riaggiudicata
			   boolean notRiaggiud = !man.isRevocataWithNewAgg(rs.getLong(AGGIUDICAZIONI.ID_INFO), rs.getTimestamp(AGGIUDICAZIONI.DATA_INIZIO_INFO), rs.getInt(AGGIUDICAZIONI.PROG_CUI));
				if (notRiaggiud) {
   			   agg = new AggiudicazioneBean();
   				this.fillBeanForMultilotto(rs, agg);
   				listaAgg.add(agg);
				}
			}
	    }
		catch(Exception e){
		    logger.debug("eccezione: "+e);
		}
		finally {
			close(rs,stmt);
		}
		listaAgg.trimToSize();
		return listaAgg;	
	}
	
	private final String QUERY_SELECT_MULTILOTTO_BY_IDLOTTO = 
		"SELECT " + CAMPI_AGGIUDICAZIONE + ", " +
		STATI_SCHEDA.T_DESCRIZIONE+ " + " +  buildRichAnnQueryMult(new String[]{IdentificativoSchede.TAB_AGGIUDICAZIONE, IdentificativoSchede.TAB_SOTTOSOGLIA, IdentificativoSchede.TAB_ESCLUSI, IdentificativoSchede.TAB_ADESIONE}, AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE,null) 
		+" AS "+STATI_SCHEDA.DESCRIZIONE+
	
		" FROM " + AGGIUDICAZIONI.TABLE_NAME 
		+ " join " + STATI_SCHEDA.TABLE_NAME 
		   + " on " + AGGIUDICAZIONI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO +
  		" WHERE " + AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.CONFERMATO +
        " AND " + AGGIUDICAZIONI.T_ID_INFO + " IN " +
        "(SELECT DISTINCT " +
        INFO_AGGIUDICAZIONI.T_ID_INFO + " FROM " + INFO_AGGIUDICAZIONI.TABLE_NAME +
        " WHERE " +
        INFO_AGGIUDICAZIONI.T_ID_LOTTO + " IN " +
        "(SELECT DISTINCT " +
        LOTTO.T_ID_LOTTO + " FROM " + LOTTO.TABLE_NAME +
        " WHERE " +
        LOTTO.T_ID_GARA + " = " +
        "(SELECT " +
        LOTTO.T_ID_GARA + " FROM " + LOTTO.TABLE_NAME +
        " WHERE " +
        LOTTO.T_ID_LOTTO + " = ? " +
        ")))";
	
	/**
	 * metodo per il recupero di tutte le aggiudicazioni inerenti ai lotti di una 
	 * stessa gara 
	 * @param idLotto
	 * @return List&lt;AggiudicazioneBean&gt; - lista di aggiudicazioni
	 */
	public List<AggiudicazioneBean> getAggiudicazioniListMultilotto(long idLotto) {
		ArrayList<AggiudicazioneBean> listaAgg = new ArrayList<AggiudicazioneBean>();
		AggiudicazioneBean agg = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
      AggiudicazioniManager man = new AggiudicazioniManager(activeConnection, logger);

		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_MULTILOTTO_BY_IDLOTTO);
			stmt.setLong(1, idLotto);
			rs = stmt.executeQuery();
			while(rs.next()){
            // PP 18.12.2015 controllo che non sia stata riaggiudicata
            boolean notRiaggiud = !man.isRevocataWithNewAgg(rs.getLong(AGGIUDICAZIONI.ID_INFO), rs.getTimestamp(AGGIUDICAZIONI.DATA_INIZIO_INFO), rs.getInt(AGGIUDICAZIONI.PROG_CUI));
            if (notRiaggiud) {
               agg = new AggiudicazioneBean();
               this.fillBeanForMultilotto(rs, agg);
               listaAgg.add(agg);
            }
			}
	    }
		catch(Exception e){
		    logger.debug("eccezione: "+e);
		}
		finally {
			close(rs,stmt);
		}
		listaAgg.trimToSize();
		return listaAgg;	
	}

	private final String QUERY_SELECT_MULTILOTTO_BY_IDAGG_E_DATAINIZIOAGG = 
		"SELECT " + CAMPI_AGGIUDICAZIONE + ", " +
		STATI_SCHEDA.T_DESCRIZIONE +
		" FROM " + AGGIUDICAZIONI.TABLE_NAME + ", " + STATI_SCHEDA.TABLE_NAME +
		" WHERE " +
		AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.CONFERMATO +
		" AND " +
		AGGIUDICAZIONI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO +
		" AND " +
		AGGIUDICAZIONI.CODICE_CONTRATTO + " IN " +
		"(SELECT " + AGGIUDICAZIONI.CODICE_CONTRATTO + 
		" FROM " + AGGIUDICAZIONI.TABLE_NAME +
		" WHERE " +
		AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = ? " +
		" AND " + 
		AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE + " = ? )";
	
	/**
	 * metodo per il recupero di tutte le aggiudicazioni inerenti ai lotti di una 
	 * stessa gara 
	 * @param idAggiudicazione, dataInizioAggiudicazione
	 * @return List&lt;AggiudicazioneBean&gt; - lista di aggiudicazioni
	 */
//	public List<AggiudicazioneBean> getAggiudicazioniListMultilotto(long idAggiudicazione, Timestamp dataInizioAggiudicazione) {
//		ArrayList<AggiudicazioneBean> listaAgg = new ArrayList<AggiudicazioneBean>();
//		AggiudicazioneBean agg = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		try{
//			stmt = activeConnection.prepareStatement(QUERY_SELECT_MULTILOTTO_BY_IDAGG_E_DATAINIZIOAGG);
//			stmt.setLong(1, idAggiudicazione);
//			stmt.setTimestamp(2, dataInizioAggiudicazione);
//			rs = stmt.executeQuery();
//			while(rs.next()){
//				agg = new AggiudicazioneBean();
//				this.fillBeanForMultilotto(rs, agg);
//				listaAgg.add(agg);
//			}
//	    }
//		catch(Exception e){
//		    logger.debug("eccezione: "+e);
//		}
//		finally {
//			close(rs,stmt);
//		}
//		listaAgg.trimToSize();
//		return listaAgg;	
//	}
	
	private final String QUERY_SELECT_GET_IMPORTO_LOTTO_BY_ID_AGGIUDICAZIONE =  
		"SELECT " +
		    LOTTO.IMPORTO_LOTTO +
		" FROM " + 
		    LOTTO.TABLE_NAME +
		" WHERE " +
		    LOTTO.ID_LOTTO + " = " +
        " ( SELECT " +
            INFO_AGGIUDICAZIONI.ID_LOTTO + 
        " FROM " + 
            INFO_AGGIUDICAZIONI.TABLE_NAME + 
        " WHERE " +
            INFO_AGGIUDICAZIONI.ID_INFO + " = " +
        " ( SELECT " +
            AGGIUDICAZIONI.ID_INFO +
        " FROM " +
            AGGIUDICAZIONI.TABLE_NAME + 
        " WHERE " +
		    AGGIUDICAZIONI.ID_STATO + " = " + StatiScheda.CONFERMATO +
		" AND " + 
		    AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = ? ))";
	
	/**
	 * metodo per il recupero dell'importo del lotto dato l'idAggiudicazione
	 * 
	 * @param idAggiudicazione
	 * @return AggiudicazioneBean - bean dell'aggiudicazione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public BigDecimal getImportoLottoByIdAggiudicazione (long idAggiudicazione) throws SQLException {
		
		String mtd = "getAggiudicazioni";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		BigDecimal importoLotto = null;
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_GET_IMPORTO_LOTTO_BY_ID_AGGIUDICAZIONE);
			logger.debug(logPrefix+" query ["+QUERY_SELECT_GET_IMPORTO_LOTTO_BY_ID_AGGIUDICAZIONE+"]");
			stmt.setLong(1, idAggiudicazione);
			rs = stmt.executeQuery();			
			if(rs.next()){
				importoLotto = rs.getBigDecimal(LOTTO.IMPORTO_LOTTO);
			}
		}
		finally{
			close(rs,stmt);
		}
		return importoLotto;
	}


	
	private final String UPDATE_GRUPPO = 
		"UPDATE " + AGGIUDICAZIONI.TABLE_NAME +
		" SET " + 
		AGGIUDICAZIONI.CODICE_CONTRATTO + " = ?, " +
		AGGIUDICAZIONI.FLAG_AGGIUD_PRINCIPALE + " = ? " +
		" WHERE " + AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = ?";
	
	/**
	 * metodo per aggiungere un'aggiudicazione ad un gruppo multilotto
	 * 
	 * @param codiceGruppo String
	 * @param idAggiudicazione long
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public void aggiungiAlGruppo(String codiceContratto, String flagAggPrincipale, long idAggiudicazione) throws SQLException{
		
		PreparedStatement stmt = null;
		
		try {
			int index = 1;
			stmt = activeConnection.prepareStatement(UPDATE_GRUPPO);
			
			stmt.setString(index++,codiceContratto);
			stmt.setString(index++, flagAggPrincipale);
			stmt.setLong(index++, idAggiudicazione);
			
			stmt.executeUpdate();
			 
		} 
		finally {
			try{
    			close(null,stmt);
			}
			catch(Exception e){
				stmt = null;
			}
		}
	}
	
	/**
	 * metodo per eliminare un'aggiudicazione da un gruppo multilotto
	 * 
	 * @param idAggiudicazione long
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public void eliminaDalGruppo(long idAggiudicazione) throws SQLException{
		
		PreparedStatement stmt = null;
		
		try {
			int index = 1;
			stmt = activeConnection.prepareStatement(UPDATE_GRUPPO);
			
			stmt.setNull(index++, Types.VARCHAR);
			stmt.setNull(index++, Types.VARCHAR);
			stmt.setLong(index++, idAggiudicazione);
			stmt.executeUpdate();	 
		} 
		finally {
			try{
    			close(null,stmt);
			}
			catch(Exception e){
				stmt = null;
			}
		}
	}
	
//	private final String DELETE_GRUPPO_OLD = 
//		"UPDATE " + AGGIUDICAZIONI.TABLE_NAME +
//		" SET " + 
//		AGGIUDICAZIONI.CODICE_CONTRATTO + " = ?, " +
//		AGGIUDICAZIONI.FLAG_AGGIUD_PRINCIPALE + " = ? " +
//		" WHERE " + AGGIUDICAZIONI.CODICE_CONTRATTO + " = ?";

	private final String DELETE_GRUPPO = 
		"UPDATE " + AGGIUDICAZIONI.TABLE_NAME +
		" SET " + 
		AGGIUDICAZIONI.CODICE_CONTRATTO + " = ?, " +
		AGGIUDICAZIONI.FLAG_AGGIUD_PRINCIPALE + " = ? " +
		" WHERE " + AGGIUDICAZIONI.CODICE_CONTRATTO + " = ? " +
	    " AND ( " +
	        AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE +
	        " OR " +
	        AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.CONFERMATO +
	        " OR " +
	        AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.ANNULLAMENTO_RICHIESTA +
	    " ) AND " +    
    	AGGIUDICAZIONI.T_ID_INFO + " IN " +
    	"(SELECT DISTINCT " +
    	INFO_AGGIUDICAZIONI.T_ID_INFO + " FROM " + INFO_AGGIUDICAZIONI.TABLE_NAME +
    	" WHERE " +
    	INFO_AGGIUDICAZIONI.T_ID_LOTTO + " IN " +
    	"(SELECT DISTINCT " +
    	LOTTO.T_ID_LOTTO + " FROM " + LOTTO.TABLE_NAME +
    	" WHERE " +
    	LOTTO.T_ID_GARA + " = ? ))";	

	/**
	 * metodo per eliminare un gruppo multilotto discendente dalla stessa gara
	 * 
	 * @param codiceContratto String
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public void eliminaGruppo(long idGara, String codiceContratto) throws SQLException{
		
		PreparedStatement stmt = null;
		
		try {
			int index = 1;
			stmt = activeConnection.prepareStatement(DELETE_GRUPPO);
			
			stmt.setNull(index++, Types.VARCHAR);
			stmt.setNull(index++, Types.VARCHAR);
			stmt.setString(index++, codiceContratto);
			stmt.setLong(index++, idGara);
			stmt.executeUpdate();	 
		} 
		finally {
			try{
    			close(null,stmt);
			}
			catch(Exception e){
				stmt = null;
			}
		}
	}
	


	/**************************		VALORIZZAZIONE BEAN 	**************************************************/	
	/**
	 * Valorizzazione centralizza del bean di aggiudicazione 
	 * @param rs
	 * @param bean
	 * @throws SQLException
	 */
	public void fillBeanForMultilotto(ResultSet rs, AggiudicazioneBean bean) throws SQLException{
		
		bean.setIdAggiudicazione(rs.getLong(AGGIUDICAZIONI.ID_AGGIUDICAZIONE));
		bean.setDataInizioAggiudicazione(rs.getTimestamp(AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
		bean.setIdInfo(rs.getLong(AGGIUDICAZIONI.ID_INFO));
		bean.setDataInizioInfo(rs.getTimestamp(AGGIUDICAZIONI.DATA_INIZIO_INFO));
		//bean.setNumImpreseInvitate(rs.getInt(AGGIUDICAZIONI.NUM_IMPRESE_INVITATE));
		//bean.setNumImpreseRichiedenti(rs.getInt(AGGIUDICAZIONI.NUM_IMPRESE_RICHIEDENTI));
		//bean.setNumImpreseOfferenti(rs.getInt(AGGIUDICAZIONI.NUM_IMPRESE_OFFERENTI));
		//bean.setNumOfferteAmmesse(rs.getInt(AGGIUDICAZIONI.NUM_OFFERTE_AMMESSE));
		bean.setDataVerbaleAggiudicazione(rs.getString(AGGIUDICAZIONI.DATA_VERBALE_AGGIUDICAZIONE));
		//bean.setDataStipula(PageHelper.getViewDate(rs.getString(AGGIUDICAZIONI.DATA_STIPULA)));
		bean.setSottotipo(TipoAggiudicazione.fromString(rs.getString(AGGIUDICAZIONI.SOTTOTIPO)));
		bean.setProgCuiRiaggiudicato(rs.getInt(AGGIUDICAZIONI.PROG_CUI_RIAGGIUDICATO));
		bean.setModalitaRiaggiudicazione(rs.getInt(AGGIUDICAZIONI.MODALITA_RIAGGIUDICAZIONE));	
		//bean.setTermineContrattuale(PageHelper.getViewDate(rs.getString(AGGIUDICAZIONI.TERMINE_CONTRATTUALE)));
		//bean.setDurataContrattuale(PageHelper.getInteger(rs.getObject(AGGIUDICAZIONI.DURATA_CONTRATTUALE)));
		//bean.setDataScadenzaRichiestaInvito(PageHelper.getViewDate(rs.getString(AGGIUDICAZIONI.DATA_SCADENZA_RICHIESTA_INVITO)));
		//bean.setDataScadenzaPresOfferta(PageHelper.getViewDate(rs.getString(AGGIUDICAZIONI.DATA_SCADENZA_PRES_OFFERTA)));
		//bean.setIdModalitaGara(rs.getLong(AGGIUDICAZIONI.ID_MODALITA_GARA));
		bean.setDataFineAggiudicazione(rs.getTimestamp(AGGIUDICAZIONI.DATA_FINE_AGGIUDICAZIONE));
		bean.setCui(rs.getString(AGGIUDICAZIONI.CUI));
		bean.setProgCUI(rs.getInt(AGGIUDICAZIONI.PROG_CUI));
		bean.setImportoAggiudicazione(rs.getBigDecimal(AGGIUDICAZIONI.IMPORTO_AGGIUDICAZIONE));
		bean.setImportoComplessivo(rs.getBigDecimal(AGGIUDICAZIONI.IMPORTO_COMPLESSIVO));
		bean.setImportoLavori(rs.getBigDecimal(AGGIUDICAZIONI.IMPORTO_LAVORI));
		bean.setImportoServizi(rs.getBigDecimal(AGGIUDICAZIONI.IMPORTO_SERVIZI));
		bean.setImportoForniture(rs.getBigDecimal(AGGIUDICAZIONI.IMPORTO_FORNITURE));
		bean.setImportoAttuazioneSicurezza(rs.getBigDecimal(AGGIUDICAZIONI.IMPORTO_ATTUAZIONE_SICUREZZA));
		bean.setImportoDisposizione(rs.getBigDecimal(AGGIUDICAZIONI.IMPORTO_DISPOSIZIONE));
		bean.setImportoProgettazione(rs.getBigDecimal(AGGIUDICAZIONI.IMPORTO_PROGETTAZIONE));
		//bean.setSistemaQualificazione(rs.getString(AGGIUDICAZIONI.SISTEMA_QUALIFICAZIONE));
		//bean.setCriteriSelezioneStabilitiSA(rs.getString(AGGIUDICAZIONI.CRITERI_SELEZIONE_STABILITI_SA));
		bean.setIdStato(rs.getLong(AGGIUDICAZIONI.ID_STATO));
		//bean.setIdTipoPrestazione(rs.getLong(AGGIUDICAZIONI.ID_TIPO_PRESTAZIONE));
		//bean.setCup(rs.getString(AGGIUDICAZIONI.CUP));
		//bean.setFlagAccordoQuadro(rs.getString(AGGIUDICAZIONI.FLAG_ACCORDO_QUADRO));
		bean.setLuogoIstat(rs.getString(AGGIUDICAZIONI.LUOGO_ISTAT));
		bean.setLuogoNuts(rs.getString(AGGIUDICAZIONI.LUOGO_NUTS));
		//bean.setAstaElettronica(rs.getString(AGGIUDICAZIONI.ASTA_ELETTRONICA));
		//bean.setPercOffAumento(rs.getBigDecimal(AGGIUDICAZIONI.PERC_OFF_AUMENTO));
		//bean.setPercRibassoAgg(rs.getBigDecimal(AGGIUDICAZIONI.PERC_RIBASSO_AGG));
		//bean.setDataInvito(PageHelper.getViewDate(rs.getString(AGGIUDICAZIONI.DATA_INVITO)));
		//bean.setNumManifInteresse(rs.getInt(AGGIUDICAZIONI.NUM_MANIF_INTERESSE));
		//bean.setDataManifInteresse(PageHelper.getViewDate(rs.getString(AGGIUDICAZIONI.DATA_MANIF_INTERESSE)));
		//bean.setFlagRichSubappalto(rs.getString(AGGIUDICAZIONI.FLAG_RICH_SUBAPPALTO));
		//bean.setNumOfferteEscluse(rs.getInt(AGGIUDICAZIONI.NUM_OFFERTE_ESCLUSE));
		//bean.setOffertaMassimo(rs.getBigDecimal(AGGIUDICAZIONI.OFFERTA_MASSIMO));
		//bean.setOffertaMinima(rs.getBigDecimal(AGGIUDICAZIONI.OFFERTA_MINIMA));
		//bean.setValSogliaAnomalia(rs.getBigDecimal(AGGIUDICAZIONI.VAL_SOGLIA_ANOMALIA));
		//bean.setNumOfferteFuoriSoglia(rs.getInt(AGGIUDICAZIONI.NUM_OFFERTE_FUORI_SOGLIA));
		//bean.setNumImpEscluseInsufGiust(rs.getInt(AGGIUDICAZIONI.NUM_IMP_ESCL_INSUF_GIUST));
		//bean.setProceduraAcc(rs.getString(AGGIUDICAZIONI.PROCEDURA_ACC));
		//bean.setPreinformazione(rs.getString(AGGIUDICAZIONI.PREINFORMAZIONE));
		//bean.setTermineRidotto(rs.getString(AGGIUDICAZIONI.TERMINE_RIDOTTO));
		//bean.setIdSceltaContraente(rs.getBigDecimal(AGGIUDICAZIONI.ID_SCELTA_CONTRAENTE).longValue());
		bean.setDescrizioneStato(rs.getString(STATI_SCHEDA.DESCRIZIONE));
		//bean.setIdModoIndizione(rs.getInt(AGGIUDICAZIONI.ID_MODO_GARA));
		//bean.setCodStrumento(rs.getString(AGGIUDICAZIONI.COD_STRUMENTO));		
		bean.setImportoNonAssog(rs.getBigDecimal(AGGIUDICAZIONI.IMP_NON_ASSOG));
		bean.setCodiceContratto(rs.getString(AGGIUDICAZIONI.CODICE_CONTRATTO));
		bean.setFlagAggiudPrincipale(rs.getString(AGGIUDICAZIONI.FLAG_AGGIUD_PRINCIPALE));
		//bean.setIdPubblicazioneAgg(rs.getLong(AGGIUDICAZIONI.ID_PUBBLICAZIONE_AGG));
		//bean.setDataPubblicazioneAgg(rs.getTimestamp(AGGIUDICAZIONI.DATA_INIZIO_PUBB_AGG));	
		//bean.setOpereUrbanizzazione(rs.getString(AGGIUDICAZIONI.OPERE_URBANIZZAZIONE));
		//bean.setIdLocale(rs.getString(AGGIUDICAZIONI.ID_SCHEDA_LOCALE));
		
		//TICKET ALM #14639 - 3.04.5
//		bean.setRelazioneUnica(rs.getString(AGGIUDICAZIONI.RELAZIONE_UNICA));
		
	}
}
	