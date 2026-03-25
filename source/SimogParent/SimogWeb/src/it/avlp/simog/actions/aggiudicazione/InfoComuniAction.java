package it.avlp.simog.actions.aggiudicazione;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avlp.simog.actions.BaseAction;
import it.avlp.simog.actions.PubblicazioneAction;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.common.action.InfoComuniSharedAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.beans.PubblicazioneBean;

public class InfoComuniAction extends BaseAction{
	
	public static String CLAZZ = "InfoComuniAction";
	
public InfoComuniSharedAction icsa;
   
	public InfoComuniAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		this.icsa = new InfoComuniSharedAction(activeConnection, logger);
	}
	


	/************************************************************************************************
	 * Ottiele le informazioni delle infocomuni 
	 * @param request HttpServletRequest
	 * @return InfoComniBean
	 * @throws ActionException
	 */
	public InfoComuniBean getBean(HttpServletRequest request) throws ActionException{
		InfoComuniBean info = new InfoComuniBean();
//		String data_albo_pretorio = null;
	
		
		//String idLotto = request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO);
		
		//info.setIdLotto(idLotto);
		
		
		
		long idinfo = getLongReqParameter(request,-1, ParametriServlet.FIELD_NAME_ID_INFO);
		Timestamp data_inizio_info = getTimestampReqParameter(request, null, ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO);
		
		//nuovi campi
		String idCategSa = request.getParameter(ParametriServlet.FIELD_NAME_ID_CATEG_SA);
		String cfAmmAgente = request.getParameter(ParametriServlet.FIELD_NAME_CF_AMM_AGENTE);
		String denAmmAgente = request.getParameter(ParametriServlet.FIELD_NAME_DEN_AMM_AGENTE);
		String flagEnteSpeciale = request.getParameter(ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE);
		String cfSA = request.getParameter(ParametriServlet.FIELD_NAME_CF_STAZIONE_APPALTANTE);
		String denSA = request.getParameter(ParametriServlet.FIELD_NAME_DENOM_STAZIONE_APPALTANTE);
		String tipoContratto = request.getParameter(ParametriServlet.FIELD_NAME_TIPO_CONTRATTO);
		String codiceCC = getStringReqParameter(request, "", ParametriServlet.FIELD_NAME_CODICE_CC);
		String denomCC = getStringReqParameter(request, "", ParametriServlet.FIELD_NAME_DENOM_CC);
		String flagSAAgente = getStringReqParameter(request, "", ParametriServlet.FIELD_NAME_FLAG_SA_AGENTE);
		long tipoSAAgente = getLongReqParameter(request, 0, ParametriServlet.FIELD_NAME_ID_TIPO_SA);
		String esitoProcedura = getStringReqParameter(request, "", ParametriServlet.FIELD_NAME_ESITO_PROCEDURA);
		//gm nuovi campi dati comuni
		long tipologiaProcedura = getLongReqParameter(request,0,ParametriServlet.FIELD_NAME_ID_TIPO_PROCEDURA);
		int durataConvenzione = getIntReqParameter(request,0,ParametriServlet.FIELD_NAME_DURATA_CONVENZIONE);
		String flagProcedeStipula = getStringReqParameter(request, "", ParametriServlet.FIELD_NAME_FLAG_PROCEDE_STIPULA);
				
		info.setCfStazioneAppaltante(cfSA);
		info.setDenStazioneAppaltante(denSA);
		info.setIdCategSa(idCategSa);
		info.setCfAmmAgente(cfAmmAgente);
		info.setDenAmmAgente(denAmmAgente);
		info.setFlagEnteSpeciale(flagEnteSpeciale);
		info.setTipoContratto(tipoContratto);
		info.setCodiceCC(codiceCC);
		info.setDenomCC(denomCC);
		info.setFlagSAAgente(flagSAAgente);
		info.setTipologiaSA(tipoSAAgente);
		info.setPubblicazione((new PubblicazioneAction(connection,logger)).getBean(request));
		info.setEsitoProcedura(esitoProcedura);
		info.setID_MODO_REAL(getIntReqParameter(request, 0, ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE));
		info.setFLAG_ESCLUSO(getStringReqParameter(request, "N", ParametriServlet.FIELD_NAME_ESCLUSO));
		info.setID_ESCLUSIONE(getIntReqParameter(request, 0, ParametriServlet.FIELD_NAME_ID_ESCLUSIONE));
		//gm nuovi campi dati comuni
		info.setTipologiaProcedura(tipologiaProcedura);
		info.setDurataConvenzione(durataConvenzione);
		info.setFlagProcedeStipula(flagProcedeStipula);
		
		info.setIdInfo(idinfo);
		info.setDataInizioInfo(data_inizio_info);
		
		//dati non presenti come parametri ma come attributi....
		info.setDescrizioneStato(getStringReqParameter(request, null, "descrizioneStato"));
		info.setIdStato(getIntReqParameter(request, StatiScheda.IN_DEFINIZIONE, "idStato"));
		info.setFlagEnteSpeciale(getStringReqParameter(request, null, ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE));
		info.setTipoContratto(getStringReqParameter(request, null, ParametriServlet.FIELD_NAME_TIPO_CONTRATTO));
		if(SimogFlags.is3028_RFWEBSC00Active()){
		   info.setOrigine(getIntReqParameter(request, OrigineSchedaEnum.ND.code(), PSBD.FIELD_NAME_ORIGINE_SCHEDA));
		}
		setMissingFields(info, request);
		return info;
	}
	
	
	/***************************************************************************************************
	 * Copia un record di infoComuni per la storicizzazione
	 * param idinfo
	 * param datainizioinfo
	 * param old_data_inizio_pub
	 * param new_data_inizio_pub
	 * return Timestamp
	 * throws ActionException
	 */
	private Timestamp copyRecord(String idinfo,Timestamp datainizioinfo,Timestamp old_data_inizio_pub, Timestamp new_data_inizio_pub)throws ActionException {
		
		InfoComuniManager man = new InfoComuniManager(connection,logger);
		try { 
			return man.copyRecord(idinfo,datainizioinfo,old_data_inizio_pub,new_data_inizio_pub);
		} catch (SQLException e) {
			throw new ActionException(e);
			
		}
	}

	/*
	 * gestione richiesta di annullamento
	 */
	/*********************************************************************************************
	 * effettua la gestione della richiesta di annullamento 
	 * @param richiestaAnnullamentoBean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public Timestamp richiediAnnullamento(RichiestaAnnullamento richiestaAnnullamentoBean) throws ActionException {
		String mtd = "richiediAnnullamento";
		String logPrefix = CLAZZ + "." + mtd + ": ";
				
		String idinfo = richiestaAnnullamentoBean.getId_info();
		Timestamp datainizioinfo = richiestaAnnullamentoBean.getData_inizio_info();
		String idpub = richiestaAnnullamentoBean.getId_pub();
		Timestamp old_data_inizio_pub = richiestaAnnullamentoBean.getData_inizio_pub();
		String idLotto = richiestaAnnullamentoBean.getId_lotto();
		logger.debug("idinfo: "+idinfo+", datainizioinfo: "+datainizioinfo+", idpub: "+idpub+", datainiziopub: "+old_data_inizio_pub);
		try{
			PubblicazioneManager pubblicazioneManager = new PubblicazioneManager(connection,logger);
			Timestamp new_data_inizio_pub = pubblicazioneManager.copyRecord(Long.parseLong(idpub), old_data_inizio_pub);
			Timestamp nuovadata = copyRecord(idinfo,datainizioinfo,old_data_inizio_pub,new_data_inizio_pub);
			
			if(nuovadata != null) {
				this.bsa.scriviAnnullamento(richiestaAnnullamentoBean);
				
				String blocco = richiestaAnnullamentoBean.getBlocco();
				String cfUtente = richiestaAnnullamentoBean.getRichiedente();
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idinfo);
				attributiChiave.add(datainizioinfo);
				LogBloccoDatiManager.loggingCANCELREQ(connection, logger, cfUtente, blocco, attributiChiave);
			}
				
			return nuovadata;
		}
		catch(Exception e){

			logger.fatal(logPrefix, e);
			throw new ActionException(e);
		}
	}

	
	public void  richiediCancellazione(RichiestaAnnullamento richiestaAnnullamentoBean) throws ActionException {
		String mtd = "richiediCancellazione";
		String logPrefix = CLAZZ + "." + mtd + ": ";

		try{			
			this.bsa.scriviAnnullamento(richiestaAnnullamentoBean);
			
			String blocco = richiestaAnnullamentoBean.getBlocco();
			String cfUtente = richiestaAnnullamentoBean.getRichiedente();
			List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(richiestaAnnullamentoBean.getId_info());
			attributiChiave.add(richiestaAnnullamentoBean.getData_inizio_info());
			LogBloccoDatiManager.loggingDELETEREQ(connection, logger, cfUtente, blocco, attributiChiave);
		
		}
		catch(Exception e){

			logger.fatal(logPrefix, e);
			throw new ActionException(e);
		}
	}

	
	/***********************************************************************************************
	 * Carica le informazioni della gara in base all'id del lotto in ingresso
	 * @param idLotto long
	 * @return InfoGaraBean
	 * @throws ActionException
	 */
	public InfoGaraBean loadInfoGara(long idLotto)throws ActionException{
		try {
			InfoComuniManager iManager = new InfoComuniManager(connection, logger);
			InfoGaraBean igb = iManager.loadInfoGara(idLotto);
			return igb;
			
		}catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(e.getCause());
			throw new ActionException(e);
		}
	}
	/***************************************************************
	 * Restituisce la tipologia del lotto in base al'id del lotto in ingresso
	 * @param idLotto long
	 * @return String
	 * @throws ActionException
	 */
	public String loadTipoLotto(long idLotto)throws ActionException{
		try {
			LottoManager lm = new LottoManager(connection, logger);
			String tipo = "";
			try{
				tipo = lm.getCategoriaPrevalenteId(idLotto);
			}catch(Throwable t){t.printStackTrace();throw new SQLException();}
			return tipo;
		
		}catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(e.getCause());
			throw new ActionException(e);
		}		
	}
	
	/*
	 * lettura info comuni
	 */
	/*****************************************************************************************************
	 * Carica le infoComuni e le pubblicazioni restituendo un bean <code>InfoComuniBean</code> 
	 * @param idInfo long
	 * @param dataInizioInfo Timestamp
	 * @return InfoComuniBean
	 * @throws ActionException
	 */
	public InfoComuniBean load(long idInfo, Timestamp dataInizioInfo) throws ActionException{
		InfoComuniManager infoMan = new InfoComuniManager(connection, logger);
		PubblicazioneManager pubManager = new PubblicazioneManager(connection, logger);
		try{
			InfoComuniBean icb = infoMan.load(idInfo, dataInizioInfo);
			
			icb.setPubblicazione(pubManager.getPubblicazione(icb.getPubblicazione().getIdPubblicazione(), icb.getPubblicazione().getDataInizioPubblicazione()));
			
			if(!Costanti.FLAG_VALORE_SI.equals(icb.getPubblicazione().getSitoMinisteroInfTrasp())
				&& !Costanti.FLAG_VALORE_NO.equals(icb.getPubblicazione().getSitoMinisteroInfTrasp()))
				icb.getPubblicazione().setSitoMinisteroInfTrasp(Costanti.FLAG_VALORE_NO);

			return icb;
		}catch (SQLException e) {
			
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/*****************************************************************************************************
	 * Carica le info pubblicazioni dal bando gara pubblicato, se esiste 
	 * @param idPubb long
	 * @param dataInizioPubb Timestamp
	 * @return void
	 * @throws ActionException
	 */
	public void loadPubbFromBando(long idPubb, Timestamp dataInizioPubb, InfoComuniBean icb) throws ActionException{
		PubblicazioneManager pubManager = new PubblicazioneManager(connection, logger);
		try{			
			icb.setPubblicazione(pubManager.getPubblicazione(idPubb, dataInizioPubb));
			
			// PP patch sitom mininf
			if(!Costanti.FLAG_VALORE_SI.equals(icb.getPubblicazione().getSitoMinisteroInfTrasp())
					&& !Costanti.FLAG_VALORE_NO.equals(icb.getPubblicazione().getSitoMinisteroInfTrasp()))
					icb.getPubblicazione().setSitoMinisteroInfTrasp(Costanti.FLAG_VALORE_NO);
				
			return;
		}catch (SQLException e) {
			
			logger.error(e);
			throw new ActionException(e);
		}
	}
	/****************************************************************************************
	 * Gestisce la memorizzazione delle infocomuni su db
	 * @param bean InfoComuniBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int save(InfoComuniBean bean, String cfUtente)throws ActionException{
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(InfoComuniBean.class, bean));
		InfoComuniManager iManager = new InfoComuniManager(connection, logger);
		PubblicazioneManager pubManager = new PubblicazioneManager(connection, logger);
		PubblicazioneBean pubbBean = new PubblicazioneBean(); //MEV 37523 3.04.8.1
		try{
			if(bean.getIdInfo() < 1){
				//MEV 37523 3.04.8.1
				pubbBean = pubManager.getPubblicazione(bean.getPubblicazione().getIdPubblicazione(), bean.getPubblicazione().getDataInizioPubblicazione());
				bean.getPubblicazione().setLinkAffidamentoDiretto(pubbBean.getLinkAffidamentoDiretto());
				pubManager.insertPubblicazione(bean.getPubblicazione());
				iManager.insert(bean, cfUtente);
				return 1;
			}
			else{
				int num = iManager.save(bean, cfUtente);
				if(num>0) pubManager.save(bean.getPubblicazione());
				return num;
			}
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/*******************************************************************************************************
	 * Effettua la gestione della conferma su info comuni. 
	 * @param bean InfoComuniBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int confirm(InfoComuniBean bean, String cfUtente) throws ActionException{
		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(InfoComuniBean.class, bean));
		InfoComuniManager iManager = new InfoComuniManager(connection, logger);
		PubblicazioneManager pubManager = new PubblicazioneManager(connection, logger);
		PubblicazioneBean pubbBean = new PubblicazioneBean(); //MEV 37523 3.04.8.1
		try{
			int num = iManager.confirm(bean, cfUtente);
			//MEV 37523 3.04.8.1
			pubbBean = pubManager.getPubblicazione(bean.getPubblicazione().getIdPubblicazione(), bean.getPubblicazione().getDataInizioPubblicazione());
			bean.getPubblicazione().setLinkAffidamentoDiretto(pubbBean.getLinkAffidamentoDiretto());
			if(num>0) pubManager.confirm(bean.getPubblicazione());
			return num;
		}
		catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/*******************************************************************************************************
	 * Ottiene l'Aggiudicazione dell'accordo quadro cui l'adesione si riferisce. 
	 * @param String cig_acc_quadro
	 * @return Aggiudicazione
	 * @throws ActionException
	 */
	public AggiudicazioneBean getBeanAggiudicazioneAccQuadro(String cig_acc_quadro) throws ActionException {
		return this.icsa.getBeanAggiudicazioneAccQuadro(cig_acc_quadro);
	}
	
	/*******************************************************************************************************
	 * Ottiene la lista con i dati degli Aggiudicatari dell'accordo quadro cui l'adesione si riferisce. 
	 * @param AggiudicazioneBean
	 * @return List<Aggiudicatario>
	 * @throws ActionException
	 */
	public List<AggiudicatarioBean> getBeanAggiudicatariAccQuadro(AggiudicazioneBean aggiudicazioneAccQuadro) throws ActionException {

		return this.icsa.getBeanAggiudicatariAccQuadro(aggiudicazioneAccQuadro);
	}
	
	/*******************************************************************************************************
	 * Carica solo i dati necessari dell'aggiudicazione dell'accordo quadro cui l'adesione si riferisce. 
	 * @param AggiudicazioneBean
	 * @return AggiudicazioneBean
	 * @throws ActionException
	 */
	public AggiudicazioneBean getAggAccQuadroBase (AggiudicazioneBean agg){
		AggiudicazioneBean aggiudicazioneBase = new AggiudicazioneBean();
		aggiudicazioneBase.setPercRibassoAgg(agg.getPercRibassoAgg());
		aggiudicazioneBase.setPercOffAumento(agg.getPercOffAumento());
		aggiudicazioneBase.setImportoAggiudicazione(agg.getImportoAggiudicazione());
		aggiudicazioneBase.setDataVerbaleAggiudicazione(agg.getDataVerbaleAggiudicazione());
		aggiudicazioneBase.setFlagRichSubappalto(agg.getFlagRichSubappalto());
		return aggiudicazioneBase;
	}
	
	/******************************************************************************************
	 * Ottiene una lista contenente le tipologie degli enti
	 * @return Map&lt;String, String&gt;
	 * @param o deve essere o un Timestamp oppure una stringa formattata yyyymmdd 
	 * @throws ActionException
	 */
	public Map<String, String> getListaTipiEnte(Object o) throws ActionException{
		InfoComuniManager iManager = new InfoComuniManager(connection, logger);
		try {
			return iManager.loadTipiEnte(o);
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	/***********************************************************************************
	 * Ottiene una lista con le Categorie SA
	 * @param o deve essere o un Timestamp oppure una stringa formattata yyyymmdd 
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> getCategorieSA(Object o) throws ActionException{
		InfoComuniManager iManager = new InfoComuniManager(connection, logger);
		try {
			return iManager.loadCategoriaSA(o);
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	/***********************************************************************************
	 * Ottiene una lista con i modi di realizzazione
	 * @param o deve essere o un Timestamp oppure una stringa formattata yyyymmdd 
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> getListaModiReal(Object o) throws ActionException{
		InfoComuniManager iManager = new InfoComuniManager(connection, logger);
		try {
			return iManager.loadModiReal(o);
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/***********************************************************************************
	 * Ottiene una lista con gli articoli esclusione
	 * @param o deve essere o un Timestamp oppure una stringa formattata yyyymmdd 
	 * @param b 
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> getListaArtEsclusione(Object o, boolean b) throws ActionException{
		InfoComuniManager iManager = new InfoComuniManager(connection, logger);
		try {
			return iManager.loadArtEsclusione(o, b);
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/**
	 * Ottiene una lista con le Tipologie SA
	 * @param o deve essere o un Timestamp oppure una stringa formattata yyyymmdd 
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> getTipologieSA(Object o) throws ActionException{
		InfoComuniManager iManager = new InfoComuniManager(connection, logger);
		try {
			return iManager.loadTipologieSA(o);
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/**
	 * Ottiene una lista con le Tipologie Procedura
	 * @param o deve essere o un Timestamp oppure una stringa formattata yyyymmdd 
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> getTipologieProcedura(Object o) throws ActionException{
		InfoComuniManager iManager = new InfoComuniManager(connection, logger);
		try {
			return iManager.loadTipologieProcedura(o);
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/*****************************************************************************************
	 * Imposta i parametri mancanti quali :
	 * <ul>
	 * <li>codice fiscale amministratore
	 * <li>Denominazione amministratore
	 * <li>Cig
	 * <li>Cig Cycle 
	 * </ul>
	 * param bean InfoComuniBean
	 * param request HttpServletRequest
	 */
	private void setMissingFields(InfoComuniBean bean, HttpServletRequest request){
		InfoGaraBean igb = getDatiGara(request);
		bean.setCfAmministrazione(igb.getCfAmministrazione());
		bean.setDenAmministrazione(igb.getDenomAmministrazione());
		bean.setCig(igb.getCig());
		bean.setCigCycle(igb.getCigCicle());
		
	}
//	public void updateRUP(InfoComuniBean icb, String cfUtente) throws ActionException{
//		InfoComuniManager iManager = new InfoComuniManager(connection, logger);
//		try{
//			iManager.updateRUP(icb, cfUtente);
//		}catch(Exception e){
//			logger.error(e);
//			throw new ActionException(e);
//		}
//	}
	

	/*****************************************************************************************************
	 * Controlla se esiste la scheda per il cig indicato
	 * 
	 */
	public boolean checkScheda(String cig){
		InfoComuniManager infoMan = new InfoComuniManager(connection, logger);
		InfoComuniBean icb = new InfoComuniBean();
		try{
			boolean ret = infoMan.checkDatiComuni(icb, cig);
			
			return ret;
		}catch (Exception e) {
			// se ritorna eccezione potrebbe essere per la scheda esistente ma non confermata
			if(icb.getIdInfo() > 0) {
				logger.debug(e);
				return true;
			}
		}
		return false;
	}

    /***********************************************************************************************
     * Carica le informazioni della gara in base all'id gara
     * @param idGara
     * @return Gara
     * @throws ActionException
     */
    public Gara getGara(long idGara)throws ActionException{
        try {
            GaraManager gm = new GaraManager(connection, logger);
            Gara igb = gm.getGara(idGara);
            return igb;
            
        }catch (Exception e) {
            throw new ActionException(e);
        }
    }


    /******************************************************************************************
     * Effettua la presa in carico, crea una richiesta di annullamento con le 
     * informazioni relative a :
     * <ul>
     * <li>Blocco
     * <li>Id Info
     * <li>Data inizio info comuni
     * <li>Esito richiesta annullamento = richiesta Accettata
     * </ul>
     * 
     * @param icb InfoComuniBean
     * @param cfUtente String
     * @throws ActionException
     */
   public void presaInCarico(InfoComuniBean icb, String login) throws ActionException{
      this.icsa.presaInCarico(icb, login);
   }
   
   //TICKET ALM - 3.04.3 #659
	/***********************************************************************************
	 * Ottiene una lista delle funzioni delegate
	 * @param o deve essere o un Timestamp oppure una stringa formattata yyyymmdd 
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
/*	public Map<String, String> getListaFunzioniDelegate(Object o) throws ActionException{
		InfoComuniManager iManager = new InfoComuniManager(connection, logger);
		try {
			return iManager.loadFunzioniDelegate(o);
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}*/
   
}
