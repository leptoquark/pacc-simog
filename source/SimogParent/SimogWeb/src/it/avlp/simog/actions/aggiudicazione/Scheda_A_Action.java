package it.avlp.simog.actions.aggiudicazione;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avlp.simog.actions.BaseAction;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.MotivoDerogaBean;
import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.CondizioneAggBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.beans.aggiudicazione.RequisitiBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.aggiudicazione.TipoFinanziamentoBean;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.common.action.CupLottoAggAction;
import it.avlp.simog.common.action.DittaAusiliariaAction;
import it.avlp.simog.common.action.Scheda_A_SharedAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriCup;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.Base64Coder;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

public class Scheda_A_Action extends BaseAction {
	
public static String CLAZZ = "Scheda_A_Action";
	
public Scheda_A_SharedAction sasa;

	public Scheda_A_Action(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		this.sasa = new Scheda_A_SharedAction(activeConnection, logger);
	}
	
	/*******************************************************************************************************
	 * Carica le informazioni della scheda A in base ai parametri dell'aggiudicazione inserita
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @param tipoEnte String
	 * @param ignoraStato TODO
	 * @return Scheda_A
	 * @throws ActionException
	 */
	public Scheda_A load(long idAggiudicazione, Timestamp dataInizioAggiudicazione, String tipoEnte, boolean ignoraStato, long idLotto) throws ActionException{
		
	   return this.sasa.load(idAggiudicazione, dataInizioAggiudicazione, tipoEnte, ignoraStato, idLotto);
	}

	 /**
    * METODO CHE SI OCCUPA DI RECUPERARE UNA AGGIUDICAZIONE DAL CUI
    * @param CUI
    * @return
    */
   public AggiudicazioneBean getAggiudicazioneByProgAndCui(String CUI, boolean confermata) throws ActionException {
      return this.sasa.getAggiudicazioneByProgAndCui(CUI, confermata);
   }
   
	
	public Timestamp gestisciVariazioniCO(Scheda_A saBean, RichiestaAnnullamento rab, String cfUtente, String tipoEnte)throws Exception{
		
	   return this.sasa.gestisciVariazioniCO(saBean, rab, cfUtente, tipoEnte);
	}
	
	/******************************************************************************************************
	 * Viene gestito il salvataggio dei dati della scheda A.
	 * @param saBean Scheda_A
	 * @param flags Boolean[]
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public void save(Scheda_A saBean, Boolean[] flags, String cfUtente) throws ActionException{
		
	   this.sasa.save(saBean, flags, cfUtente);
	}
	
	/*********************************************************************************************************
	 * Viene gestita la conferma delle informazioni inserite
	 * @param saBean Scheda_A
	 * @param flags Boolean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public void confirm(Scheda_A saBean,Boolean[] flags,String cfUtente) throws ActionException{
		
	   this.sasa.confirm(saBean, flags, cfUtente);
	}
	
	
	/********************************************************************************************************
	 * Gestisce la richiesta di annullamento 
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public Timestamp richiediAnnullamento(RichiestaAnnullamento bean) throws ActionException{

	   return this.sasa.richiediAnnullamento(bean);
	}
	
	/********************************************************************************************************
	 * Gestisce la richiesta di cancellazione 
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public void richiediCancellazione(RichiestaAnnullamento bean) throws ActionException{

	   this.sasa.richiediCancellazione(bean);
	}
	
	/*******************************************************************************************************
	 * Ottiene il bean con i dati della scheda A. 
	 * @param request HttpServletRequest
	 * @param sezioneResp TODO
	 * @return Scheda_A
	 * @throws ActionException
	 */
	public Scheda_A getBean(HttpServletRequest request, String sezioneResp) throws ActionException {
		logger.debug("SCHEDA_A_ACTION.getBean: START");
		Scheda_A beans = new Scheda_A();
		
		beans.setAggiudicazione(getBeanAggiudicazione(request));
		
		beans.getAggiudicazione().setDataValidatore(getTimestampReqParameter(request, null, PSBD.ACTION_RIAGGIUDICAZIONE + PSBD.DATA_INIZIO_AGGIUDICAZIONE));

		//!!SM MEV #4113 15-01-2019 Inizio 
		//beans.setAggiudicatari(getBeanAggiudicatario(request)); 
		beans.setAggiudicatari(getBeanAggiudicatario(request, sezioneResp)); 
		//!!SM MEV #4113 Fine
		
		beans.setResponsabili(getBeanResp(request,sezioneResp));
		beans.setPrestazioni(getBeanResp(request,PSBD.SEZIONE_PA));
		beans.setRequisiti(getBeanRequisiti(request));  
		beans.setCondizioni(getBeanCondizioni(request));
		beans.setTipoLavoro(getBeanL(request));
		beans.setTipoFS(getBeanSF(request));
		beans.setFinanziamenti(getBeanFinanz(request));
		beans.getAggiudicazione().setIdAggiudicazione(getDatiGara(request).getIdAggiudicazione());
		beans.getAggiudicazione().setDataInizioAggiudicazione(getDatiGara(request).getDataInizioAggiudicazione());
		//beans.setSelMotiviDeroga(getBeanMotivoDeroga(request));
		
		if( SimogFlags.is3031_RFWEBGL02Active() ){
		   beans.setElencoCup( getBeanCup(request) );
		   new CupLottoAggAction(connection, logger).settingDatiDIPE(beans.getElencoCup());
		   beans.setFlagCUP(getStringReqParameter(request, null, ParametriCup.FIELD_FLAG_CUP));
		}

		/*beans.setFlagPnrrPnc(getStringReqParameter(request, null, ParametriServlet.FLAG_PNRR_PNC));
		beans.setFlagPrevisioneQuota(getStringReqParameter(request, null, ParametriServlet.FLAG_PREVISIONE_QUOTA));
		beans.setSelMotiviDeroga(getStringReqParameter(request, null, ParametriServlet.MOTIVO_DEROGA_SELECTED_TABLEBEAN));
		if(getStringReqParameter(request, null, ParametriServlet.QUOTA_FEMMINILE) != null) {
		beans.setQuotaFemminile(Double.parseDouble(getStringReqParameter(request, null, ParametriServlet.QUOTA_FEMMINILE)));
		}
		if(getStringReqParameter(request, null, ParametriServlet.QUOTA_GIOVANILE) != null) {
			beans.setQuotaGiovanile(Double.parseDouble(getStringReqParameter(request, null, ParametriServlet.QUOTA_GIOVANILE)));
		}
		*/
		
		
//		//FIX ME Parità di genere getStringReqParameter --> null pointerException
//		if(!SimogProperties.getInstance().isDataCreatedAfter3047(null)) {
//			beans.setQuotaFemminile(Double.parseDouble(getStringReqParameter(request, null, ParametriServlet.QUOTA_FEMMINILE)));
//
//			beans.setQuotaGiovanile(Double.parseDouble(getStringReqParameter(request, null, ParametriServlet.QUOTA_GIOVANILE)));
//		}
		logger.debug("SCHEDA_A_ACTION.getBean: END");
		return beans;
	}
	
	/*************************************************************************************************
	 * restituisce il bean di aggiudicazione impostando i parametri relativi all'Aggiudicazione, i Dati 
	 * della Gara e quelli relativi al CIG. 
	 *  
	 * @param request 
	 * @return AggiudicazioneBean
	 * @throws ActionException
	 */
	public AggiudicazioneBean getBeanAggiudicazione(HttpServletRequest request) throws ActionException{
		
		AggiudicazioneBean agg = new AggiudicazioneBean();
		AggiudicazioniManager aggMan = new AggiudicazioniManager(connection,logger);
				
		agg.setIdAggiudicazione(getLongReqParameter(request, -1, PSBD.FIELD_NAME_ID_AGGIUDICAZIONE));
		agg.setDataInizioAggiudicazione(getTimestampReqParameter(request, null, PSBD.DATA_INIZIO_AGGIUDICAZIONE));
		agg.setNumImpreseInvitate(getIntReqParameter(request,0,PSBD.FIELD_NAME_NUM_IMPRESE_INVITATE));
		agg.setNumImpreseRichiedenti(getIntReqParameter(request,0,PSBD.FIELD_NAME_NUM_IMPRESE_RICHIEDENTI));	
		agg.setNumImpreseOfferenti(getIntReqParameter(request,0,PSBD.FIELD_NAME_NUM_IMPRESE_OFFERENTI));
		agg.setNumOfferteAmmesse(getIntReqParameter(request,0,PSBD.FIELD_NAME_NUM_OFFERTE_AMMESSE));
		agg.setDataVerbaleAggiudicazione(getStringReqParameter(request, null, PSBD.FIELD_NAME_DATA_AGGIUDICAZIONE_DEFINITIVA));
		agg.setDataScadenzaRichiestaInvito(getStringReqParameter(request, null, PSBD.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO));
		agg.setDataScadenzaPresOfferta(getStringReqParameter(request, null, PSBD.FIELD_NAME_DATA_SCADENZA_PRES_OFFERTA));
		agg.setIdModalitaGara(getLongReqParameter(request, -1, PSBD.FIELD_NAME_ID_MODALITA_GARA));
		agg.setImportoComplessivo(getBigDecimalReqParameter(request, null, PSBD.FIELD_NAME_IMPORTO_COMPLESSIVO));
		agg.setIdSceltaContraente(getLongReqParameter(request, -1, ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE));
		agg.setDataStipula(getStringReqParameter(request, null, PSBD.FIELD_NAME_DATA_STIPULA));
		agg.setTermineContrattuale(getStringReqParameter(request, null, PSBD.FIELD_NAME_TERMINE_CONTRATTUALE));
		agg.setDurataContrattuale(getIntegerReqParameter(request, null, PSBD.FIELD_NAME_DURATA_CONTRATTUALE));
		
		String importoAggiudicazione =PageHelper.formattaImporto(getStringReqParameter(request, null,PSBD.FIELD_NAME_IMPORTO_AGGIUDICAZIONE));
		try{
			agg.setImportoAggiudicazione(new BigDecimal(importoAggiudicazione));
		}catch(Exception e){
			agg.setImportoAggiudicazione(null);
		}	
		
		String importoLavori =PageHelper.formattaImporto(getStringReqParameter(request, null,PSBD.FIELD_NAME_IMPORTO_LAVORI));
		try{
			agg.setImportoLavori(new BigDecimal(importoLavori));
		}catch(Exception e){
			agg.setImportoLavori(null);
		}
		String importoServizi =PageHelper.formattaImporto(getStringReqParameter(request, null,PSBD.FIELD_NAME_IMPORTO_SERVIZI));
		try{
			agg.setImportoServizi(new BigDecimal(importoServizi));
		}catch(Exception e){
			agg.setImportoServizi(null);
		}
		String importoForniture =PageHelper.formattaImporto(getStringReqParameter(request, null,PSBD.FIELD_NAME_IMPORTO_FORNITURE));
		try{
			agg.setImportoForniture(new BigDecimal(importoForniture));
		}catch(Exception e){
			agg.setImportoForniture(null);
		}
		String importoAttuzioneSicurezza =PageHelper.formattaImporto(getStringReqParameter(request, null,PSBD.FIELD_NAME_IMPORTO_ATTUAZIONE_SICUREZZA));
		try{
			agg.setImportoAttuazioneSicurezza(new BigDecimal(importoAttuzioneSicurezza));
		}catch(Exception e){
			agg.setImportoAttuazioneSicurezza(null);
		}
		String importDisposizione =PageHelper.formattaImporto(getStringReqParameter(request, null,PSBD.FIELD_NAME_IMPORTO_DISPOSIZIONE));
		try{
			agg.setImportoDisposizione(new BigDecimal(importDisposizione));
		}catch(Exception e){
			agg.setImportoDisposizione(null);
		}
		String importoProgettazione =PageHelper.formattaImporto(getStringReqParameter(request, null,PSBD.FIELD_NAME_IMPORTO_PROGETTAZIONE));
		try{
			agg.setImportoProgettazione(new BigDecimal(importoProgettazione));
		}catch(Exception e){
			agg.setImportoProgettazione(null);
		}
		
		String importoNonAssog = PageHelper.formattaImporto(getStringReqParameter(request, null,PSBD.FIELD_NAME_IMPORTO_NON_ASSOG));
		try{
			agg.setImportoNonAssog(new BigDecimal(importoNonAssog));
		}catch(Exception e){
			agg.setImportoNonAssog(null);
		}
		
		agg.setSistemaQualificazione(getStringReqParameter(request, null, PSBD.FIELD_NAME_SISTEMA_QUALIFICAZIONE));
		agg.setCriteriSelezioneStabilitiSA(getStringReqParameter(request, null, PSBD.FIELD_NAME_CRITERI_SELEZIONE_STABILITI_SA));
		
		agg.setIdTipoPrestazione(getLongReqParameter(request,0,PSBD.FIELD_NAME_ID_TIPO_PRESTAZIONE));
		
		agg.setCup(getStringReqParameter(request, null, PSBD.FIELD_NAME_CUP));
		agg.setFlagAccordoQuadro(getStringReqParameter(request, null, PSBD.FIELD_NAME_FLAG_ACCORDO_QUADRO));
		
		agg.setLuogoIstat(getStringReqParameter(request, null, PSBD.FIELD_NAME_LUOGO_ISTAT));
		agg.setLuogoNuts(getStringReqParameter(request, null, PSBD.FIELD_NAME_LUOGO_NUTS));

		agg.setAstaElettronica(getStringReqParameter(request, null, PSBD.FIELD_NAME_ASTA_ELETTRONICA));
		agg.setPreinformazione(getStringReqParameter(request, null, PSBD.FIELD_NAME_PREINFORMAZIONE));
		agg.setProceduraAcc(getStringReqParameter(request, null, PSBD.FIELD_NAME_PROCEDURA_ACC));
		agg.setTermineRidotto(getStringReqParameter(request, null, PSBD.FIELD_NAME_TERMINE_RIDOTTO));
		agg.setProgCuiRiaggiudicato(getIntReqParameter(request, 0, PSBD.FIELD_NAME_PRG_CUI_RIAGG));
		agg.setModalitaRiaggiudicazione(getIntReqParameter(request, 0, PSBD.FIELD_NAME_MOD_RIAGG));
		//gm nuovo per avvisi aggiudicazione
		agg.setIdPubblicazioneAgg(getLongReqParameter(request,0,PSBD.FIELD_NAME_ID_PUBBLICAZIONE_AGG));
		agg.setDataPubblicazioneAgg(getTimestampReqParameter(request, null, PSBD.FIELD_NAME_DATA_INIZIO_PUBB_AGG));

		//TICKET ALM #14639 - 3.04.5
		agg.setRelazioneUnica(getStringReqParameter(request, null, PSBD.FIELD_NAME_RELAZIONE_UNICA));
		
		InfoGaraBean igb =getDatiGara(request);
		String CIG_CYCLE = Integer.toString(igb.getCigCicle());
		String CIG = igb.getCig();
		String CIG_KKK = igb.getCigKKK();
//
		String CUI = CIG_CYCLE+"-"+CIG+CIG_KKK; 
		agg.setCui(CUI);
//		
		int maxProCUI;
		try {
			maxProCUI = aggMan.getMaxProgCUI(String.valueOf(igb.getIdInfo()),CUI);
			agg.setProgCUI(maxProCUI+1);
		} catch (SQLException e) {

			logger.fatal(e);
		}
//
//		
//		
//		
//		long idInfo = Long.parseLong(request.getParameter(ParametriServlet.FIELD_NAME_ID_INFO));
		agg.setIdInfo(igb.getIdInfo());
//
//
//		
//		
    	agg.setDataInizioInfo(igb.getDataInizioInfo());
//		//agg.setIdStato(request.getParameter(Para));
//		
		
		
    	agg.setPercRibassoAgg(getBigDecimalReqParameter(request, null, PSBD.FIELD_NAME_PERC_RIBASSO_AGG));
		
    	agg.setPercOffAumento(getBigDecimalReqParameter(request, null, PSBD.FIELD_NAME_PERC_OFF_AUMENTO));
		
		agg.setDataInvito( getStringReqParameter(request, "",PSBD.FIELD_NAME_DATA_INVITO));
		agg.setNumManifInteresse(getIntReqParameter(request, 0, PSBD.FIELD_NAME_NUM_MANIF_INTERESSE));
		agg.setDataManifInteresse( getStringReqParameter(request, "", PSBD.FIELD_NAME_DATA_MANIF_INTERESSE));
		agg.setFlagRichSubappalto(getStringReqParameter(request, null, PSBD.FIELD_NAME_FLAG_RICH_SUBAPPALTO));
		agg.setNumOfferteEscluse(getIntReqParameter(request, 0, PSBD.FIELD_NAME_NUM_OFFERTE_ESCLUSE));
		agg.setOffertaMassimo(getBigDecimalReqParameter(request, null,PSBD.FIELD_NAME_OFFERTA_MASSIMO_RIBASSO));
		agg.setOffertaMinima(getBigDecimalReqParameter(request, null,PSBD.FIELD_NAME_OFFERTA_MINIMO_RIBASSO));
		agg.setValSogliaAnomalia(getBigDecimalReqParameter(request, null,PSBD.FIELD_NAME_VALORE_SOGLIA_ANOMALIA));
		agg.setNumOfferteFuoriSoglia(getIntReqParameter(request, 0, PSBD.FIELD_NAME_NUM_OFFERTE_MAG_SOGLIA));
		agg.setNumImpEscluseInsufGiust(getIntReqParameter(request, 0, PSBD.FIELD_NAME_NUM_IMP_ESCL_INSUF_GIUST));	
		agg.setIdModoIndizione(getIntReqParameter(request, 0, PSBD.FIELD_NAME_ID_MODO_INDIZIONE));
		
		agg.setCodStrumento(getStringReqParameter(request, null, PSBD.FIELD_NAME_COD_STRUMENTO));
		agg.setDescrizioneStato(getStringReqParameter(request, null, "descrizioneStato"));
		agg.setIdStato(getIntReqParameter(request, StatiScheda.IN_DEFINIZIONE, "idStato"));
		
		//agg.setDurataConvenzione(getLongReqParameter(request, 0,PSBD.FIELD_NAME_DURATA_CONVENZIONE));	
		//gm nuovo codice 3.0
		agg.setOpereUrbanizzazione(getStringReqParameter(request, null, PSBD.FIELD_NAME_OPERE_URBANIZZAZIONE));
		//gm fine nuovo codice 3.0
		
		//gm nuovo per appalti multilotto
		agg.setCodiceContratto( getStringReqParameter(request, null, PSBD.FIELD_NAME_CODICE_CONTRATTO));
		agg.setFlagAggiudPrincipale(getStringReqParameter(request, null, PSBD.FIELD_NAME_FLAG_AGGIUD_PRINCIPALE));
		
		// PP B302.2.0
		if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive())
			agg.setIdMotivoVarCO(getStringReqParameter(request, null, PSBD.FIELD_NAME_MOTIVO_CO));
		
		if(SimogFlags.is3028_RFWEBSC00Active()){
		   agg.setOrigine(getIntReqParameter(request, OrigineSchedaEnum.ND.code(), PSBD.FIELD_NAME_ORIGINE_SCHEDA));
		}
		
		/* Calcolo Importo Aggiudicazione Disabilitato secondo specifiche OSIT 2.9
		try{
			//X-XX: attenzione questo caso era stato \"dimenticato\"
			if(importoAggiudicazione == null || "".equals(importoAggiudicazione )){
				logger.debug("[aaaa - importo aggiudicazione risulta nullo o vuoto]");
				double temp = 0;
				//Controllo che se il valore di importo lavori � settato
				if(agg.getImportoLavori() != null){
					temp+=agg.getImportoLavori().doubleValue();
				}
				
				//Controllo che se il valore di importo servizi � settato
				if(agg.getImportoServizi() != null){
					temp+=agg.getImportoServizi().doubleValue();
				}

				//Controllo che se il valore di importo forniture � settato
				if(agg.getImportoForniture() != null){
					temp+=agg.getImportoForniture().doubleValue();
				}
				if(agg.getImportoProgettazione() != null){
					temp+=agg.getImportoProgettazione().doubleValue();
				}			
				
				if(agg.getPercRibassoAgg() != null){
					if(agg.getPercRibassoAgg().compareTo(new BigDecimal(0)) > 0){
						temp *= (1-agg.getPercRibassoAgg().doubleValue()/100);
					}
				}else{
					if(agg.getPercOffAumento().compareTo(new BigDecimal(0)) > 0){
						temp *= (1+agg.getPercOffAumento().doubleValue()/100);
					}				
				}
				if(agg.getImportoAttuazioneSicurezza()!= null){
					temp += agg.getImportoAttuazioneSicurezza().doubleValue();
				}
				importoAggiudicazione=String.valueOf(temp);
			}
		}catch(Exception e){
			logger.warn("fallito il calcolo dell'importo di aggiudicazione");
			agg.setImportoAggiudicazione(null);
		}
		*/
		return agg;
	}
	
	/***************************************************************************************
	 * Ottine la lista degli aggiudicatari contenuti all'interno della request. 
	 * 
	 * @param request HttpServletRequest
	 * @return List&lt;AggiudicatarioBean&gt;
	 * @throws ActionException
	 */
	//!!SM MEV #4113 15-01-2019 Inizio 
	public List<AggiudicatarioBean> getBeanAggiudicatario(HttpServletRequest request, String sezione) throws ActionException{
	//public List<AggiudicatarioBean> getBeanAggiudicatario(HttpServletRequest request) throws ActionException{
	//!!SM MEV #4113 Fine
		
		int nrAggiudicatari = getIntReqParameter(request, 0,PSBD.NR_RIGHE_AFFIDATARI);
		ArrayList<AggiudicatarioBean> aggiudicatari = new ArrayList<AggiudicatarioBean>(nrAggiudicatari);
		String prefix = "row" + PSBD.AGGIUDICATARIO;
		long idAggiudicazione = getLongReqParameter(request, 0, PSBD.FIELD_NAME_ID_AGGIUDICAZIONE);
		Timestamp dataInizioAggiudicazione =getTimestampReqParameter(request, null, PSBD.DATA_INIZIO_AGGIUDICAZIONE);
		AggiudicatarioBean nuovoAggiudicatario = null;
		int daleggere = nrAggiudicatari;
		int i = 0;
				
		while(daleggere>0){
			String name = prefix + i + PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE;
			String idAggiudString = request.getParameter(name);
			boolean found = false;//TICKET ALM #1490
			
			if(idAggiudString != null){
				found = true;//TICKET ALM #1490
				
				
		    	
		    	//!!SM MEV #4113 15-01-2019 Inizio 
				if(sezione.equalsIgnoreCase(PSBD.SEZIONE_RQ)) {
					String aggiudicatarioSelezionato = getStringReqParameter(request, null, "affidatarioSelez_" + i);
					if (aggiudicatarioSelezionato == null) {
						daleggere--;
						i++;
						continue;
					}
				}
				//!!SM MEV #4113 Fine
		    	
				nuovoAggiudicatario = new AggiudicatarioBean();
				
				long idAggiudParamValue = "".equalsIgnoreCase(idAggiudString)
				? 0 : Long.parseLong(idAggiudString);
				
				SoggettoPartecipanteBean nuovoSoggettoPartecipante = new SoggettoPartecipanteBean();
				nuovoSoggettoPartecipante.setIdSoggettoPartecipante(idAggiudParamValue);
				nuovoSoggettoPartecipante.setDataInizioSogg(getTimestampReqParameter(request, null, prefix + i + PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG));
				nuovoSoggettoPartecipante.setDenominazione(getStringReqParameter(request,null,prefix + i + PSBD.FIELD_NAME_AGG_DENOMINAZIONE));
				nuovoSoggettoPartecipante.setCodiceFiscale(getStringReqParameter(request,null,prefix + i + PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO));
				//VL - adds 24092008
				nuovoSoggettoPartecipante.setFlagEsteri(getStringReqParameter(request, Costanti.FLAG_VALORE_NO, ParametriServlet.FLAG_ESTERO));
				nuovoSoggettoPartecipante.setId_stato(getStringReqParameter(request, "", prefix + i + PSBD.FIELD_NAME_AGG_ID_PAESE));
				logger.debug(" "+prefix + i + PSBD.FIELD_NAME_AGG_ID_PAESE+" = "+nuovoSoggettoPartecipante.getId_stato());
				if(nuovoSoggettoPartecipante.getId_stato() == Costanti.CODICE_STATO_ITALIANO)	//X-XX: Verificare perche' arriva "IT" dal frontend
					nuovoSoggettoPartecipante.setId_stato("");
				//end
				
	    		// PP se esistono variazioni anagrafiche le memorizzo
	    		String datiAnag = getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_ANAGOE);
	    		if (datiAnag != null && !"".equals(datiAnag) && !"*".equals(datiAnag)){
	    			
	    			String [] val = Base64Coder.decodeString(datiAnag).split(PSBD.SEP_VARANAG_S,-1);
	    				    			
	    			nuovoSoggettoPartecipante.setIdSoggettoPartecipante(Long.parseLong(val[0]));
	    			nuovoSoggettoPartecipante.setCodiceFiscale(val[1]);
	    			nuovoSoggettoPartecipante.setDenominazione(val[2]);
	    			nuovoSoggettoPartecipante.setCameraCommercio(val[3]);
	    			nuovoSoggettoPartecipante.setPartitaIva(val[4]);
	    			nuovoSoggettoPartecipante.setIndirizzo(val[5]);
	    			nuovoSoggettoPartecipante.setCivico(val[6]);
	    			nuovoSoggettoPartecipante.setCitta(val[7]);
	    			nuovoSoggettoPartecipante.setProvincia(val[8]);
	    			nuovoSoggettoPartecipante.setCap(val[9]);
	    			nuovoSoggettoPartecipante.setCfRappresentante(val[10]);
	    			nuovoSoggettoPartecipante.setCognome(val[11]);
	    			nuovoSoggettoPartecipante.setNome(val[12]);
	    			nuovoSoggettoPartecipante.setId_stato(val[13]);
	    			
	    			// setto il flag per indicare la successiva modifica
	    			nuovoSoggettoPartecipante.setModifica(true);
	    		}

				nuovoAggiudicatario.setSoggettoPartecipante(nuovoSoggettoPartecipante);
				nuovoAggiudicatario.setIdAggiudicazione(idAggiudicazione);
				nuovoAggiudicatario.setDataInizioAggiudicazione(dataInizioAggiudicazione);
				
				//nuovoAggiudicatario.setDataInizioSogg(getTimestampReqParameter(request, null, prefix + i + PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG));
		
				nuovoAggiudicatario.setRuolo(getStringReqParameter(request,null,prefix + i + PSBD.FIELD_NAME_AGG_ID_RUOLO));
				// Rinaldo ticket 654 ///////////////////
//				System.out.println("Step 1 IMPORTO_AGGIUDICATARIO:"+getBigDecimalReqParameter(request, null, prefix + i + PSBD.FIELD_NAME_IMP_AGGIUDICATARIO)
//						+" PERC_RIBASSO_AGGIUDICATARIO:"+getBigDecimalReqParameter(request, null, prefix + i + PSBD.FIELD_NAME_PERC_RIBASSO_AGGIUDICATARIO)
//						+" PERC_AUMENTO_AGGIUDICATARIO:"+getBigDecimalReqParameter(request, null, prefix + i + PSBD.FIELD_NAME_PERC_AUMENTO_AGGIUDICATARIO)
//						+" TIPOLOGIA_AGGIUDICATARIO:"+getStringReqParameter(request, null, PSBD.FIELD_NAME_FLAG_TIPOLOGIA_AFFIDATARIO));
				nuovoAggiudicatario.setImpAggiudicatario(getBigDecimalReqParameter(request, null, prefix + i + PSBD.FIELD_NAME_IMP_AGGIUDICATARIO));
				nuovoAggiudicatario.setPercRibassoAggiudicatario(getBigDecimalReqParameter(request, null, prefix + i + PSBD.FIELD_NAME_PERC_RIBASSO_AGGIUDICATARIO));
				nuovoAggiudicatario.setPercAumentoAggiudicatario(getBigDecimalReqParameter(request, null, prefix + i + PSBD.FIELD_NAME_PERC_AUMENTO_AGGIUDICATARIO));	
				/////////////////////////////////////////
				//String percentuale = PageHelper.replaceCommasWithDots(request.getParameter(prefix + i + PSBD.FIELD_NAME_AGG_PERCENTUALE));
				
				
//				nuovoAggiudicatario.setPercentuale((percentuale)!=null && (!percentuale.equals(""))
//						? new BigDecimal(percentuale) 
//						: null);
				 
				nuovoAggiudicatario.setFlagAvvalimento(request.getParameter(prefix + i + PSBD.FIELD_NAME_AGG_FLAG_AVVALIMENTO));
				nuovoAggiudicatario.setCfAusiliaria(request.getParameter(prefix + i + PSBD.FIELD_NAME_AGG_CF_AUSILIARIA));
				nuovoAggiudicatario.setIdTipoAgg(getLongReqParameter(request, 0, prefix + i + PSBD.FIELD_NAME_AGG_ID_TIPO_AGG));	
				nuovoAggiudicatario.setIdStato(StatiScheda.IN_DEFINIZIONE); 
			
				//gm aggiunto per raggruppamenti di impresa, non lo carico se ditta singola, per evitare incongruenze
				if (nuovoAggiudicatario.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_ATI || nuovoAggiudicatario.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_CONSORZIO)
		    		nuovoAggiudicatario.setIdGruppo(getLongReqParameter(request,0, prefix + i + PSBD.FIELD_NAME_AGG_ID_GRUPPO));
				
				if(nuovoAggiudicatario.getIdTipoAgg()==Costanti.TIPODITTA_SINGOLA)
					nuovoAggiudicatario.setRuolo(null);
				//gm aggiunto per ditte ausiliarie
				nuovoAggiudicatario.setDitteAusiliarieString(getStringReqParameter(request,null,prefix + i + PSBD.FIELD_NAME_AGG_LISTA_AUSILIARIE));
				if(nuovoAggiudicatario.getDitteAusiliarieString()!=null && !"".equals(nuovoAggiudicatario.getDitteAusiliarieString())){  
					List <DittaAusiliariaBean> ditteAusiliarie = new DittaAusiliariaAction(connection,logger).creaListaDitteAusiliarie(nuovoAggiudicatario.getDitteAusiliarieString(),idAggiudicazione,dataInizioAggiudicazione);
				    if(!ditteAusiliarie.isEmpty()){
					    nuovoAggiudicatario.setDitteAusiliarie(ditteAusiliarie);
				    }
				}			
				//gm aggiunto per nuovi raggruppamenti d'impresa simog 3.05
		    	nuovoAggiudicatario.setDitteRaggruppamentoString(getStringReqParameter(request,null,prefix + i + PSBD.FIELD_NAME_AGG_LISTA_GRUPPI));
		    	
//				nuovoAggiudicatario.setDataInizio((request.getParameter(prefix + i + PSBD.FIELD_NAME_AGG_DATA_INIZIO)!= null && !"".equals(request.getParameter(prefix + i + PSBD.FIELD_NAME_AGG_DATA_INIZIO))) ? PageHelper.parseTime(request.getParameter("rowAggiudicatario" + i + PSBD.FIELD_NAME_AGG_DATA_INIZIO)) : null);
		    	logger.debug("getBean: " + ObjectIntrospector.propertiesInfo(AggiudicatarioBean.class, nuovoAggiudicatario));
				aggiudicatari.add(nuovoAggiudicatario);
			}
			i++;
			if(found)//TICKET ALM #1490
				daleggere--;
		}
		
		
		
		return aggiudicatari;
	}


	/***********************************************************************************************
	 * Restituisce la lista relativa alle informazioni dei responsabili
	 * <p>
	 * Vengono distinti due casi 
	 * <ul>
	 * <li>sezione di tipo RA
	 * <li>sezione non RA
	 * </ul>
	 * Nel primo caso si gestiscono responsabili, nel secondo prestazioni. Per prelevare 
	 * le informazioni relative dai parametri della request si accede ai parametri della requet
	 * attraverso nome generati da :
	 * <ul>
	 * <li>"row" 
	 * <li>"Incaricato" o "Prestazione" a seconda della sezione
	 * <li> i : un indice progressivo che differenzia i vari parametri
	 * <li>Parametro di PSBD
	 * </ul>
	 *  
	 * @param request HttpServletRequest
	 * @param sezione String
	 * @return List&lt;ResponsabileBean&gt;
	 */
	public List<ResponsabileBean> getBeanResp(HttpServletRequest request, String sezione) {

		int nrRighe = 0;
		
		if(sezione.equalsIgnoreCase(PSBD.SEZIONE_PA))
			nrRighe = getIntReqParameter(request, 0, PSBD.NR_RIGHE_PRESTAZIONI);	
		else 
			nrRighe = getIntReqParameter(request, 0, PSBD.NR_RIGHE_RESPONSABILI);
		
		ArrayList<ResponsabileBean> responsabili = new ArrayList<ResponsabileBean>(nrRighe);
		
		String prefix = null;
		if(sezione.equals(PSBD.SEZIONE_PA))
			prefix = "row" + PSBD.PRESTAZIONE;
		else 
			prefix = "row" + PSBD.RESPONSABILE;
		
		long idAggiudicazione = getLongReqParameter(request, 0, PSBD.FIELD_NAME_ID_AGGIUDICAZIONE);
		Timestamp dataInizioAggiudicazione =getTimestampReqParameter(request, null, PSBD.DATA_INIZIO_AGGIUDICAZIONE);
		int daleggere = nrRighe;
		int i = 0;
		ResponsabileBean nuovoResponsabile = null;
		while(daleggere>0){
			Long idResp = null;
			int idruolo = 0;
			String desRuolo = null;
			Timestamp dataInizioRes = null;
			String nomeRes= null,cognomeRes= null,codiceFisRes = null; 
			if(sezione.equals(PSBD.SEZIONE_PA)){
				idResp = getLongReqParameter(request, -1,prefix + i + PSBD.FIELD_NAME_PRESTAZIONE_ID_RESPONSABILE);
				idruolo = getIntReqParameter(request,0,prefix + i + PSBD.FIELD_NAME_PRESTAZIONE_DESCRIZIONE_RUOLO);
				dataInizioRes = getTimestampReqParameter(request, null, prefix + i + PSBD.FIELD_NAME_PRESTAZIONE_DATA_INIZIO_RES);
				nomeRes = getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_PRESTAZIONE_NOME);
				cognomeRes = getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_PRESTAZIONE_COGNOME);
				codiceFisRes = getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_PRESTAZIONE_CODICEFISCALE);
				desRuolo = getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_PRESTAZIONE_ID_RUOLO);
			}
			else {
				idResp = getLongReqParameter(request, -1, prefix + i + PSBD.FIELD_NAME_ID_RESPONSABILE);
				idruolo = getIntReqParameter(request,0,prefix + i + PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE);
				dataInizioRes = getTimestampReqParameter(request, null, prefix + i + PSBD.FIELD_NAME_DATA_INIZIO_RES);
				nomeRes = getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_NOME_RESPONSABILE);
				cognomeRes = getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_COGNOME_RESPONSABILE);
				codiceFisRes = getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE);
				desRuolo = getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE);
			}
			
			
			if(idResp > 0){
				nuovoResponsabile = new ResponsabileBean();
				nuovoResponsabile.setSezione(sezione);
				nuovoResponsabile.setIdScheda(idAggiudicazione);
				nuovoResponsabile. setDataInizioScheda(dataInizioAggiudicazione);
				nuovoResponsabile.setIdRuolo(idruolo);
				nuovoResponsabile.setDescrizioneRuolo(desRuolo);
				 			
				//gm nuovo controllo per soggetti partecipanti
				if("-".equals(codiceFisRes.substring(2,3))){
					SoggettoPartecipanteBean spb = new SoggettoPartecipanteBean();
					spb.setDenominazione(cognomeRes);
					spb.setCodiceFiscale(codiceFisRes.substring(3));
					spb.setIdSoggettoPartecipante(idResp);
					spb.setDataInizioSogg(dataInizioRes);

		    		// PP se esistono variazioni anagrafiche le memorizzo
		    		String datiAnag = getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_PRESTAZIONE_ANAG);
		    		if (datiAnag != null && !"".equals(datiAnag) && !"*".equals(datiAnag)){
		    			
		    			String [] val = Base64Coder.decodeString(datiAnag).split(PSBD.SEP_VARANAG_S,-1);
		    			
		    			spb.setIdSoggettoPartecipante(Long.parseLong(val[0]));
		    			spb.setCodiceFiscale(val[1]);
		    			spb.setDenominazione(val[2]);
		    			spb.setCameraCommercio(val[3]);
		    			spb.setPartitaIva(val[4]);
		    			spb.setIndirizzo(val[5]);
		    			spb.setCivico(val[6]);
		    			spb.setCitta(val[7]);
		    			spb.setProvincia(val[8]);
		    			spb.setCap(val[9]);
		    			spb.setCfRappresentante(val[10]);
		    			spb.setCognome(val[11]);
		    			spb.setNome(val[12]);
		    			spb.setId_stato(val[13]);
		    			
		    			// setto il flag per indicare la successiva modifica
		    			spb.setModifica(true);
		    		}

		    		nuovoResponsabile.setSoggettoPartecipante(spb);
				}
				else{
		    		SoggettoResponsabileBean srb = new SoggettoResponsabileBean();
		    		srb.setNome(nomeRes);
		    		srb.setCognome(cognomeRes);
		    		srb.setCodiceFiscaleResponsabile(codiceFisRes);
		     		srb.setIdResponsabile(idResp);
		    		srb.setDataInizioRes(dataInizioRes);

		    		// PP se esistono variazioni anagrafiche le memorizzo
		    		String datiAnag = getStringReqParameter(request, null,prefix + i 
		    				+ (sezione.equals(PSBD.SEZIONE_PA) ? PSBD.FIELD_NAME_PRESTAZIONE_ANAG : PSBD.FIELD_NAME_ANAG));
		    		if (datiAnag != null && !"".equals(datiAnag) && !"*".equals(datiAnag)){
		    			
		    			String [] val = Base64Coder.decodeString(datiAnag).split(PSBD.SEP_VARANAG_S,-1);
		    			
		    			srb.setIdResponsabile(Long.parseLong(val[0]));
		    			srb.setCodiceFiscaleResponsabile(val[1]);
		    			srb.setCognome(val[2]);
		    			srb.setNome(val[3]);
		    			srb.setTelefono(val[4]);
		    			srb.setFax(val[5]);
		    			srb.setEmail(val[6]);
		    			srb.setIndirizzo(val[7]);
		    			srb.setCap(val[8]);
		    			srb.setComuneIstat(val[9]);
		    			
		    			// setto il flag per indicare la successiva modifica
		    			srb.setModifica(true);
		    		}

		    		nuovoResponsabile.setSoggettoResponsabile(srb);
				}	
				if(sezione.equals(PSBD.SEZIONE_PA)){
					nuovoResponsabile.setCigProgEsterna(getStringReqParameter(request, null, prefix + i + PSBD.FIELD_NAME_PRESTAZIONE_CIG_PROG_ESTERNA));
					nuovoResponsabile.setDataAffProgEsterna(getStringReqParameter(request, null, prefix + i + PSBD.FIELD_NAME_PRESTAZIONE_DATA_AFF_PROG_ESTERNA));
					nuovoResponsabile.setDataConsProgEsterna(getStringReqParameter(request, null, prefix + i + PSBD.FIELD_NAME_PRESTAZIONE_DATA_CONS_PROG_ESTERNA));
					nuovoResponsabile.setDitteRaggruppamentoString(getStringReqParameter(request,null,prefix + i + PSBD.FIELD_NAME_AGG_LISTA_GRUPPI));

				}
				
				//logger.debug("getBean: " + ObjectIntrospector.propertiesInfo(ResponsabileBean.class, nuovoResponsabile));
				responsabili.add(nuovoResponsabile);
				
			}
			daleggere--;
			i++;
		}
		return responsabili;	
	}
	
	/******************************************************************************************************
	 * Ottiene la lista dei Requisiti contenuta nella Request
	 * @param request HttpServletRequest
	 * @return List&lt;RequisitiBean&gt;
	 * @throws ActionException
	 */
	public List<RequisitiBean> getBeanRequisiti (HttpServletRequest request) throws ActionException{
		
		int nrRequisiti = getIntReqParameter(request, 0, PSBD.NR_RIGHE_REQUISITI);
		List<RequisitiBean> requisitiList = null;
		if(nrRequisiti > 0){
			requisitiList = getReqFunction(request, nrRequisiti,PSBD.REQUISITO);
		}
		else{
			requisitiList = new ArrayList<RequisitiBean>();
		}
		return requisitiList;
	}
	
	/*************************************************************************************************
	 * Ottine la lista dei requisiti.
	 * <p>
	 * Ottiene id aggiudicazione e data inizio aggiudicazione dai parameti inseriti nella request. 
	 * Ad ogni requisito sono associati dei campi i cui valori sono memorizzati come 
	 * Parametri della request con un nome generato dinamicamente :
	 * <p> prefix = "row"+reqPrefix;
	 * <p> prefix + i + nome del campo in ParametriServletBloccodati
	 * <p>esempi: 
	 * <p>
	 * <code> prefix + i +PSBD.FIELD_NAME_ID_CATEGORIA</code>  
	 * 	<br>per prelevare informazioni relative a id categoria<br>
	 * <code>prefix + i +PSBD.FIELD_NAME_SUBAPPALTABILE</code>
	 * 	<br>per prelevare informazioni relative al campo Subappaltabile.<br> 
	 * param request
	 * param nrRequisiti : indica il numero di requisiri che verranno letti
	 * param reqPrefix : stringa che indica il prefisso utilizzato per determinare il nome del parametro su cui sono memorizzati i dati 
 	 * return
	 */
	private List<RequisitiBean> getReqFunction(HttpServletRequest request, int nrRequisiti,String reqPrefix){
		ArrayList<RequisitiBean> listaReq = new ArrayList<RequisitiBean>();
		RequisitiBean requisitiBean = null;
		//dati relativi all'aggiudicazione
		String prefix = "row" + reqPrefix;
		long idAggiudicazione = getLongReqParameter(request, -1, PSBD.FIELD_NAME_ID_AGGIUDICAZIONE);
		Timestamp dataInizioAggiudicazione = getTimestampReqParameter(request, null, PSBD.DATA_INIZIO_AGGIUDICAZIONE);
		int daleggere = nrRequisiti;
		int i = 0;
		while(daleggere>0){
			String name = prefix + i +PSBD.FIELD_NAME_ID_CATEGORIA;
			String sezioneReqString = request.getParameter(name);
			if(sezioneReqString != null){
				
				requisitiBean = new RequisitiBean();
		//		requisitiBean.setSezione(sezioneReqString);
				requisitiBean.setDataInizioRequisito(getTimestampReqParameter(request, null,prefix + i +PSBD.FIELD_NAME_DATA_INIZIO_REQ));
				
				requisitiBean.setIdCategoria(request.getParameter(prefix + i +PSBD.FIELD_NAME_ID_CATEGORIA));
				requisitiBean.setDescCategoria(request.getParameter(prefix + i +PSBD.FIELD_NAME_DESCRIZIONE_CATEGORIA));
				requisitiBean.setImportoDa(request.getParameter(prefix + i +PSBD.FIELD_NAME_CLASSE_IMPORTO));
				requisitiBean.setClasseImporto(request.getParameter(prefix + i +PSBD.FIELD_NAME_ID_CLASSE_IMPORTO));
				
				requisitiBean.setPrevalente(getStringReqParameter(request,null,prefix + i +PSBD.FIELD_NAME_PREVALENTE));
				requisitiBean.setScorporabile(getStringReqParameter(request,null,prefix + i +PSBD.FIELD_NAME_SCORPORABILE));
				requisitiBean.setSubAppaltabile(getStringReqParameter(request,null,prefix + i +PSBD.FIELD_NAME_SUBAPPALTABILE));

				requisitiBean.setIdStato(getIntReqParameter(request, -1,prefix + i + PSBD.FIELD_NAME_ID_STATO_REQUISITI));
				requisitiBean.setIdAggiudicazione(idAggiudicazione);
				requisitiBean.setDataInizioAggiudicazione(dataInizioAggiudicazione);
				
				listaReq.add(requisitiBean);
				
			}
			daleggere--;
			i++;
		}
		return listaReq;
	}

	/******************************************************************************************
	 * Ottiene la lista delle condizioni impostando per ogni condizione prelevata dalla request 
	 * <ul>
	 * <li>Id Aggiudicazione
	 * <li>Data Inizio Aggiudicazione
	 * <li>Id Condizione
	 * </ul> 
	 * @param request HttpServletRequest
	 * @return List&lt;CondizioneAggBean&gt;
	 * @throws ActionException
	 */
	@SuppressWarnings("unchecked")
	public List<CondizioneAggBean> getBeanCondizioni(HttpServletRequest request) throws ActionException{
		String mtd = "getBean";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + "START"); 
		ArrayList<CondizioneAggBean> condizioni = new ArrayList<CondizioneAggBean>();
		CondizioneAggBean cond = null;
		InfoGaraBean infoGara = getDatiGara(request);
		long idAggiudicazione = infoGara.getIdAggiudicazione();
		Timestamp dataInizioAggiudicazione = infoGara.getDataInizioAggiudicazione();
		String[] checked = request.getParameterValues(PSBD.FIELD_NAME_CONDIZIONI_AGG);
		for(int i=0; checked != null && i < checked.length; i++){
			cond = new CondizioneAggBean();
			cond.setIdAggiudicazione(idAggiudicazione);
			cond.setDataInizioAggiudicazione(dataInizioAggiudicazione);
			cond.setIdCondizione(Long.parseLong(checked[i]));
			condizioni.add(cond);
			logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(cond.getClass(), cond));
		}
		logger.debug(logPrefix + "END"); 
		condizioni.trimToSize();
		return condizioni;
	
	}
	
	/***********************************************************************************
	 * Genera una lista di motivi deroga
	 * @param request HttpServletRequest
	 * @return List MotivoDerogaBean
	 * @throws ActionException
	 */
	public List<MotivoDerogaBean> getBeanMotivoDeroga(HttpServletRequest request)throws ActionException{
		List<MotivoDerogaBean> elencoMotivoDeroga = new ArrayList<MotivoDerogaBean>();
	      String[] arraySelectedELements = request.getParameterValues(ParametriServlet.MOTIVO_DEROGA_TABLEBEAN);
	      List<String> elenco  = arraySelectedELements != null ? Arrays.asList(arraySelectedELements) : new ArrayList<String>();
	      
	      Map<String, String> motivoDerogaSelezionabili = (Map<String, String>) request.getAttribute(ParametriServlet.MOTIVO_DEROGA_TABLEBEAN);
	      
	      /**for(String _idMotivo: elenco)
	    	  
	      {
	         if( _idMotivo == null ) continue;
	         MotivoDerogaBean motivoDeroga = new MotivoDerogaBean(Long.parseLong(_idMotivo), descrizione, dataUltimaModifica, dataFineValidita, dataInizioValidita);

	         elencoMotivoDeroga.add(motivoDeroga);
	      }*/
	      return elencoMotivoDeroga;
	}

	/***********************************************************************************
	 * Genera una lista di tipologie per appalti inerenti Lavori
	 * @param request HttpServletRequest
	 * @return List TipoAppaltoAggBean
	 * @throws ActionException
	 */
	public List<TipoAppaltoAggBean> getBeanL(HttpServletRequest request)throws ActionException{
		return getBeanTipoApp(request, PSBD.FIELD_NAME_TIPO_APPALTO_AGG_L);
	}
	/***********************************************************************************
	 * Genera una lista di Tipologie associate ad un appalto per Servizi o Forniture
	 * @param request HttpServletRequest
	 * @return List&lt;TipoAppaltoAggBean&gt;
	 * @throws ActionException
	 */
	public List<TipoAppaltoAggBean> getBeanSF(HttpServletRequest request)throws ActionException{
		return getBeanTipoApp(request, PSBD.FIELD_NAME_TIPO_APPALTO_AGG_SF);
	}
	
	@SuppressWarnings("unchecked")
	private List<TipoAppaltoAggBean> getBeanTipoApp(HttpServletRequest request, String tipo) throws ActionException{
		String mtd = "getBean";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + "START"); 
		ArrayList<TipoAppaltoAggBean> listaAppalti = new ArrayList<TipoAppaltoAggBean>();
		TipoAppaltoAggBean appalto = null;
		InfoGaraBean infoGara = getDatiGara(request);
		long idAggiudicazione = infoGara.getIdAggiudicazione();
		Timestamp dataInizioAggiudicazione = infoGara.getDataInizioAggiudicazione();
	
		String[] checked = request.getParameterValues(tipo);
		for(int i=0; checked != null && i < checked.length; i++){
			appalto = new TipoAppaltoAggBean();
			appalto.setIdAggiudicazione(idAggiudicazione);
			appalto.setDataInizioAggiudicazione(dataInizioAggiudicazione);
			appalto.setIdAppalto(Long.parseLong(checked[i]));
			listaAppalti.add(appalto);
			logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(appalto.getClass(), appalto));
		}
		logger.debug(logPrefix + "END"); 
		listaAppalti.trimToSize();
		return listaAppalti;
	
	}
	
   public List<CupLottoAggExt> getBeanCup(HttpServletRequest request)
   {
      int nrElencoCup = getIntReqParameter(request, 0, ParametriCup.NR_RIGHE_CUP);
      List<CupLottoAggExt> elencoCup = new LinkedList<CupLottoAggExt>();
      String prefix = "row" + ParametriCup.ELENCO_CUP;
      int idx = 0;
      
      while( idx <= nrElencoCup ){
         String name = prefix + idx + ParametriCup.FIELD_NAME_CUP;
         String idCupString = request.getParameter(name);
         if( idCupString != null ){
            CupLottoAggExt item = new CupLottoAggExt();
            item.setCup( request.getParameter(prefix + idx + ParametriCup.FIELD_NAME_CUP) );
            item.setIdLotto( getLongReqParameter(request, -1L, prefix + idx + ParametriCup.FIELD_NAME_ID_LOTTO) );
            item.setIdAggiudicazione( getLongReqParameter(request, null, prefix + idx + ParametriCup.FIELD_NAME_ID_AGG) );
            item.setDataInizioAgg( getTimestampReqParameter(request, null, prefix + idx + ParametriCup.FIELD_NAME_DATA_INIZIO_AGG) );
            item.setOkUtente( getStringReqParameter(request, null, prefix + idx + ParametriCup.FIELD_NAME_OK_UTENTE) );
            elencoCup.add(item);
         }
         idx++;
      }
      return elencoCup;
   }	
	
	/**********************************************************************************************************
	 * Restituisce  la lista dei finanziamenti. 
	 * Ad ogni finanziamento � associato un id contenuto in un parametro il cui nome &egrave ottenuto da 
	 * <ul>
	 * <li>prefix + 
	 * <li>i + 
	 * <li>PSBD.FIELD_NAME_DES_FINANZIAMENTO
	 * </ul></code> dove prefix =
	 * <code>"rowFinanziamento"</code>, i &egrave l'indice del finanziamento nella lista.
	 * in modo analogo si recuperano i dati relativi alla descrizione del finanziamento.    
	 * @param request HttpServletRequest
	 * @return List&lt;TipoFinanziamentoBean&gt;
	 * @throws ActionException
	 */
	@SuppressWarnings("unchecked")
	public List<TipoFinanziamentoBean> getBeanFinanz(HttpServletRequest request) throws ActionException{
		
		int nrFinanziamenti = getIntReqParameter(request, 0,PSBD.NR_RIGHE_FINANZIAMENTI);
		ArrayList<TipoFinanziamentoBean> finanziamenti = new ArrayList<TipoFinanziamentoBean>(nrFinanziamenti);
		String prefix = "row" + PSBD.FINANZIAMENTO;
		
		long idAggiudicazione = getLongReqParameter(request, 0, PSBD.FIELD_NAME_ID_AGGIUDICAZIONE);
		Timestamp dataInizioAggiudicazione = getTimestampReqParameter(request, null, PSBD.DATA_INIZIO_AGGIUDICAZIONE);
		TipoFinanziamentoBean nuovoFinanziamento = null;
		int daleggere = nrFinanziamenti;
		int i = 0;
		while(daleggere>0){
			String name = prefix + i + PSBD.FIELD_NAME_TIPO_FINANZIAMENTO;
			String idFinanzString= request.getParameter(name);
			if(idFinanzString != null){
				
				nuovoFinanziamento = new TipoFinanziamentoBean();
				nuovoFinanziamento.setIdAggiudicazione(idAggiudicazione);
				nuovoFinanziamento.setDataInizioAggiudicazione(dataInizioAggiudicazione);
				nuovoFinanziamento.setIdFinanziamento(request.getParameter(prefix + i + PSBD.FIELD_NAME_DES_FINANZIAMENTO));//WOW
				nuovoFinanziamento.setIdStato(StatiScheda.IN_DEFINIZIONE);
				//logger.debug("/**************************************************/");
				//logger.debug("Action finanziamento(reqStr): "+getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_IMPORTO_FINANZIAMENTO));
				String importoFinanziamento = PageHelper.formattaImporto(getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_IMPORTO_FINANZIAMENTO));
				//logger.debug("Action finanziamento: PageHelper"+importoFinanziamento);
				//logger.debug("/**************************************************/");
				try{
					nuovoFinanziamento.setImporto(new BigDecimal(importoFinanziamento));
				}catch(Exception e){
					nuovoFinanziamento.setImporto(new BigDecimal("0.000"));
				}
				nuovoFinanziamento.setDescrizione(request.getParameter(prefix + i + PSBD.FIELD_NAME_TIPO_FINANZIAMENTO));
				logger.debug("getBean: " + ObjectIntrospector.propertiesInfo(AggiudicatarioBean.class, nuovoFinanziamento));
				
				finanziamenti.add(nuovoFinanziamento);	
			}
			daleggere--;
			i++;
		}
		
		//ticket #31064
		//simil group by dell'array finanziamenti
		/*
		 * input:
		 * mutuo 10
		 * pagamento 50
		 * mutuo 30
		 * altro 5
		 * altro 5
		 * 
		 * output:
		 * mutuo 40
		 * pagamento 50
		 * altro 10
		 */
		
		ArrayList<TipoFinanziamentoBean> arrayCompatto = new ArrayList<TipoFinanziamentoBean>();
		
		for(int j = 0;finanziamenti.size() > j;j++) {
			
			if(arrayCompatto.size() == 0) {
				arrayCompatto.add(finanziamenti.get(j));
				continue;
			}
			
			for (int k = 0;arrayCompatto.size() > k;k++) {

				if(finanziamenti.get(j).getIdFinanziamento().equals(arrayCompatto.get(k).getIdFinanziamento())) {
					arrayCompatto.get(k).setImporto(arrayCompatto.get(k).getImporto().add(finanziamenti.get(j).getImporto()));
					break;
				}else if(k == arrayCompatto.size()-1){
					arrayCompatto.add(finanziamenti.get(j));
					break;
				}
			}
		}
		
		return arrayCompatto;
	}
	
	/**********************************************************************************************
	 * Si occupa della creazione della Scheda_A
	 * @param saBean Scheda_A
	 * @param flags Boolean[]
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public void create(Scheda_A saBean,Boolean[] flags,String cfUtente) throws ActionException{
		
	   this.sasa.create(saBean, flags, cfUtente);
	}	

	/********************************************************************************************************
	 * Carica le tipologie di appalto del contratto 
	 * @param request HttpServletRequest
	 * @param tipoContratto String
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadTipiAppaltoContratto(HttpServletRequest request, String tipoContratto,Object o ) throws ActionException{
		
		InfoGaraBean igb = getDatiGara(request);

		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);	
		try {
			
			return  man.caricaComboAppalto(igb.getTipoEnte(), tipoContratto,o);
				
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
			
		}	
	}
	
	/********************************************************************************************************
	 * Carica i dati della combo relativa ai dati di Tipo prestazione
	 * @param request HttpServletRequest
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadComboTipoPrestazione(HttpServletRequest request,Object o) throws ActionException{
		
		InfoGaraBean igb = getDatiGara(request);
		
		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);	
		try {
			
			return  man.caricaComboPrestazione(igb.getIdLotto(),igb.getTipoEnte(), igb.getTipoContratto(),o);
				
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
			
		}	
	}
	
	/*********************************************************************************
	 * Carica la scelta del contraente
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @param idOss 
	 * @return Map&lt;String, String&gt; - id, descrizione
	 * @throws ActionException
	 */
	public Map<String, String> loadSceltaContraente(Object o, String cfAmm, String idOss) throws ActionException{
		
	   return this.sasa.loadSceltaContraente(o, cfAmm, idOss);
	}
	
	/***********************************************************************************
	 * Carica le condizioni aggiuntive della scheda
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadCondizioniAggiuntive(Object o) throws ActionException{

	   return this.sasa.loadCondizioniAggiuntive(o);
	}
	
	/*************************************************************************************
	 * Carica i criteri di aggiudicazione
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadCriteriAggiudicazione(Object o) throws ActionException{

	   return this.sasa.loadCriteriAggiudicazione(o);
	}
	
	/***********************************************************************************
	 * Carica le modalit� di indizione
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadModoIndizione(Object o) throws ActionException{

	   return this.sasa.loadModoIndizione(o);
	}
	
	/*******************************************************************************************
	 * Carica tutte le categorie "lavori"

	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadCategoria(Object o, String cfAmm) throws ActionException{
		
	   return this.sasa.loadCategoria(o, cfAmm);
	}	

	/**************************************************************************************
	 * Carica le classi di importo
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadClasseImporto(Object o) throws ActionException{
	
	   return this.sasa.loadClasseImporto(o);
	}	
	
	/****************************************************************************************
	 * Si occupa del caricamento delle tipologie di aggiudicatario
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadTipoAggiudicatario(Object o) throws ActionException{
	
	   return this.sasa.loadTipoAggiudicatario(o);
	}
	/****************************************************************************************
	 * Carica i Ruoli della sezione
	 * @param sezione
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadRuoliSezione(String sezione,Object o) throws ActionException{

	   return this.sasa.loadRuoliSezione(sezione, o);
	}
	
	/******************************************************************************************
	 * Carica i dati relativi ai finanziamenti
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadFinanziamenti(Object o) throws ActionException{

	   return this.sasa.loadFinanziamenti(o);
	}
	
	/********************************************************************************************
	 * Carica le informazioni relative agli strumenti della scheda
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadStrumenti(Object o) throws ActionException{

	   return this.sasa.loadStrumenti(o);
	}
	
	/********************************************************************************************
	 * Carica le informazioni relative ai modi riaggiudicazione della scheda
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadModiRiaggiud(Object o) throws ActionException{
		
	   return this.sasa.loadModiRiaggiud(o);
	}
	/****************************************************************************************************
	 * Crea la lista degli <code>AggiudicatarioBean</code> inserendo gli Aggiudicatari più
	 * gli Aggiudicatari dei raggruppamenti nella Stringa ditteRaggruppamentoString
	 * @param Scheda_A saBean
	 * @return Scheda_A
	 */
	public Scheda_A esplodiAggiudicatari(Scheda_A saBean)throws ActionException  {
	   return this.sasa.esplodiAggiudicatari(saBean);
    }
	
	/****************************************************************************************************
	 * Riorganizza la lista degli<code>AggiudicatarioBean</code> raggruppando gli Aggiudicatari
	 * dello stesso tipo e idGruppo in una stringa e restituendo la nuova lista alla Scheda_A
	 * @param Scheda_A saBean
	 * @return Scheda_A
	 */
	public Scheda_A implodiAggiudicatari(Scheda_A saBean)throws ActionException  {
	   return this.sasa.implodiAggiudicatari(saBean);
	}
	
	/****************************************************************************************************
	 * Trova il Capo Gruppo della lista di <code>AggiudicatarioBean</code> inserendo i valori relativi
	 * alla lista List<AggiudicatarioBean> passata in input
	 * @param List<AggiudicatarioBean>
	 * @return String ditteRaggruppamentoString
	 */
	public AggiudicatarioBean findCapoGruppoATI(List<AggiudicatarioBean> aggiudicatari)throws ActionException  {
	   return this.sasa.findCapoGruppoATI(aggiudicatari);
	}
}
