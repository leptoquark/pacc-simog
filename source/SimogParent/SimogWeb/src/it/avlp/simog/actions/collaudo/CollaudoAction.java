package it.avlp.simog.actions.collaudo;

import it.avlp.simog.actions.BaseAction;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.common.action.CollaudoSharedAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletCollaudo;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class CollaudoAction extends BaseAction {

	public CollaudoAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		this.csa = new CollaudoSharedAction(activeConnection, logger);
	}
	
	public CollaudoSharedAction csa;
	
	public static String CLAZZ = "CollaudoAction";
	
	/***************************************************************************************************
	 * Il metodo ottiene il Bean relativo al Collaudo riempiendo il bean con i dati presi dalla request
	 * @param request HttpServletRequest
	 * @param idAggiud long
	 * @param dataInizioAggiud Timestamp
	 * @return ColaudoBean
	 * @throws ActionException
	 */
	public CollaudoBean getBean(HttpServletRequest request , long idAggiud , Timestamp dataInizioAggiud)throws ActionException{
		String mtd = "getBean";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		
		BigDecimal bigZero = new BigDecimal(0);
		CollaudoBean bean = new CollaudoBean();
		bean.setAmmImportoDef(getBigDecimalReqParameter(request, bigZero, ParametriServletCollaudo.FIELD_NAME_AMM_IMPORTO_DEF));
		bean.setAmmImportoDefStr(PageHelper.formattaImporto(bean.getAmmImportoDef()));
		
		bean.setAmmImportoRich(getBigDecimalReqParameter(request, bigZero, ParametriServletCollaudo.FIELD_NAME_AMM_IMPORTO_RICH));
		bean.setAmmImportoRichStr(PageHelper.formattaImporto(bean.getAmmImportoRich()));
		
		bean.setAmmNumDaDef(getIntReqParameter(request, 0, ParametriServletCollaudo.FIELD_NAME_AMM_NUM_DADEF));
		
		bean.setAmmNumDefinite(getIntReqParameter(request, 0, ParametriServletCollaudo.FIELD_NAME_AMM_NUM_DEFINITE));
		
		bean.setArbImportoDef(getBigDecimalReqParameter(request, bigZero, ParametriServletCollaudo.FIELD_NAME_ARB_IMPORTO_DEF));
		bean.setArbImportoDefStr(PageHelper.formattaImporto(bean.getArbImportoDef()));
		
		bean.setArbImportoRich(getBigDecimalReqParameter(request, bigZero, ParametriServletCollaudo.FIELD_NAME_ARB_IMPORTO_RICH));
		bean.setArbImportoRichStr(PageHelper.formattaImporto(bean.getArbImportoRich()));
		
		bean.setArbNumDaDef(getIntReqParameter(request, 0, ParametriServletCollaudo.FIELD_NAME_ARB_NUM_DADEF));
		
		bean.setArbNumDefinite(getIntReqParameter(request, 0, ParametriServletCollaudo.FIELD_NAME_ARB_NUM_DEFINITE));
		
		bean.setDataCertCollaudo(getStringReqParameter(request, null, ParametriServletCollaudo.FIELD_NAME_DATA_CERT_COLLAUDO));
		
		bean.setDataCollaudoStat(getStringReqParameter(request, null, ParametriServletCollaudo.FIELD_NAME_DATA_COLLAUDO_STAT));
		
		bean.setDataDelibera(getStringReqParameter(request, null, ParametriServletCollaudo.FIELD_NAME_DATA_DELIBERA));

		
		bean.setDataFinColl(getTimestampReqParameter(request, null, ParametriServletCollaudo.FIELD_NAME_DATA_FINE_COLL));
		
		bean.setDataIniAggiudicazione(dataInizioAggiud);
		
		bean.setDataIniColl(getTimestampReqParameter(request, null, ParametriServletCollaudo.FIELD_NAME_DATA_INIZIO_COLL));
		
		if(bean.getDataIniColl()==null){
			bean.setDataIniColl(new Timestamp(new Date().getTime()));
		}
		
		bean.setDataIniOper(getStringReqParameter(request, null, ParametriServletCollaudo.FIELD_NAME_DATA_INIZIO_OPER));
		
		bean.setDataNominaColl(getStringReqParameter(request, null, ParametriServletCollaudo.FIELD_NAME_DATA_NOMINA_COLL));
		
		bean.setDataRegolareEsec(getStringReqParameter(request, null, ParametriServletCollaudo.FIELD_NAME_DATA_REGOLARE_ESEC));

		bean.setEsitoCollaudo(getStringReqParameter(request, null, ParametriServletCollaudo.FIELD_NAME_ESITO_COLLAUDO));
		
		bean.setGiuImportoDef(getBigDecimalReqParameter(request, bigZero, ParametriServletCollaudo.FIELD_NAME_GIU_IMPORTO_DEF));
		bean.setGiuImportoDefStr(PageHelper.formattaImporto(bean.getGiuImportoDef()));
		
		bean.setGiuImportORich(getBigDecimalReqParameter(request, bigZero, ParametriServletCollaudo.FIELD_NAME_GIU_IMPORTO_RICH));
		bean.setGiuImportORichStr(PageHelper.formattaImporto(bean.getGiuImportORich()));
		
		bean.setGiuNumDaDef(getIntReqParameter(request, 0, ParametriServletCollaudo.FIELD_NAME_GIU_NUM_DADEF));
		
		bean.setGiuNumDefinite(getIntReqParameter(request, 0, ParametriServletCollaudo.FIELD_NAME_GIU_NUM_DEFINITE));

		bean.setIdAggiudicazione(idAggiud);
		
		bean.setIdCollaudo(getLongReqParameter(request, -1, ParametriServletCollaudo.FIELD_NAME_ID_COLLAUDO));
		
		bean.setImpDisposizione(getBigDecimalReqParameter(request, bigZero, ParametriServletCollaudo.FIELD_NAME_IMP_DISPOSIZIONE));
		bean.setImpDisposizioneStr(PageHelper.formattaImporto(bean.getImpDisposizione()));
		
		
		bean.setImpFinaleFornit(getBigDecimalReqParameter(request, bigZero, ParametriServletCollaudo.FIELD_NAME_IMP_FINALE_FORNIT));
		bean.setImpFinaleFornitStr(PageHelper.formattaImporto(bean.getImpFinaleFornit()));
		
		bean.setImpFinaleServizi(getBigDecimalReqParameter(request, bigZero, ParametriServletCollaudo.FIELD_NAME_IMP_FINALE_SERVIZI));
		bean.setImpFinaleServiziStr(PageHelper.formattaImporto(bean.getImpFinaleServizi()));
		
		bean.setImpFinaleLavori(getBigDecimalReqParameter(request, bigZero, ParametriServletCollaudo.FIELD_NAME_IMP_FINALE_LAVORI));
		bean.setImpFinaleLavoriStr(PageHelper.formattaImporto(bean.getImpFinaleLavori()));
		
		bean.setImpFinaleSicur(getBigDecimalReqParameter(request, bigZero, ParametriServletCollaudo.FIELD_NAME_IMP_FINALE_SICUR));
		bean.setImpFinaleSicurStr(PageHelper.formattaImporto(bean.getImpFinaleSicur()));
		
		bean.setImpProgettazione(getBigDecimalReqParameter(request, bigZero, ParametriServletCollaudo.FIELD_NAME_IMP_PROGETTAZIONE));
		bean.setImpProgettazioneStr(PageHelper.formattaImporto(bean.getImpProgettazione()));
		
		//X-XX UN: Calcolo subtotali
		bean.setSub(bean.getImpFinaleLavori().add(bean.getImpFinaleServizi()).add(bean.getImpFinaleFornit()));
		bean.setSubStr(PageHelper.formattaImporto(bean.getSub()));
		
		bean.setSub2(bean.getSub().add(bean.getImpFinaleSicur()).add(bean.getImpProgettazione()));
		bean.setSub2Str(PageHelper.formattaImporto(bean.getSub2()));
		
		bean.setFinaleStr(PageHelper.formattaImporto(bean.getSub2().add(bean.getImpDisposizione())));
		
		/* X-XX UN: Perche'?
		bean.setSub(getBigDecimalReqParameter(request, null, ParametriServletCollaudo.SUBTOTALE));
		bean.setSubStr(PageHelper.formattaImporto(bean.getSub()));
		
		bean.setSub2(getBigDecimalReqParameter(request, null, ParametriServletCollaudo.SUBTOTALE2));
		bean.setSub2Str(PageHelper.formattaImporto(bean.getSub2()));
		
		bean.setFinaleStr(getStringReqParameter(request, null, ParametriServletCollaudo.FIELD_NAME_IMP_FINALE_TOTALE));
		*/
		
		String[] checkbox = request.getParameterValues(ParametriServletCollaudo.FIELD_NAME_MODO_COLLAUDO);
		if(checkbox == null)bean.setModoCollaudo(null);
		else if(checkbox.length == 2)bean.setModoCollaudo("3");
		else bean.setModoCollaudo(checkbox[0]);
		
		bean.setTraImportoDef(getBigDecimalReqParameter(request, bigZero, ParametriServletCollaudo.FIELD_NAME_TRA_IMPORTO_DEF));
		bean.setTraImportoDefStr(PageHelper.formattaImporto(bean.getTraImportoDef()));
		
		bean.setTraImportoRich(getBigDecimalReqParameter(request, bigZero, ParametriServletCollaudo.FIELD_NAME_TRA_IMPORTO_RICH));
		bean.setTraImportoRichStr(PageHelper.formattaImporto(bean.getTraImportoRich()));
		
		bean.setTraNumDaDef(getIntReqParameter(request, 0, ParametriServletCollaudo.FIELD_NAME_TRA_NUM_DADEF));
		
		bean.setTraNumDefinite(getIntReqParameter(request, 0, ParametriServletCollaudo.FIELD_NAME_TRA_NUM_DEFINITE));
		
		bean.setFlagLavoriEstesi(getStringReqParameter(request, null, ParametriServletCollaudo.FIELD_NAME_LAVORI_ANNUALI_ESTESI));
		
		// PP B302.2.0
		if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive())
			bean.setIdMotivoVarCO(getStringReqParameter(request, null, PSBD.FIELD_NAME_MOTIVO_CO));

		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(CollaudoBean.class, bean));
		return bean;
	}
	
	/*****************************************************************************************************
	 * Gestisce la richiesta di cancellazione 
	 * @param bean RichiestaAnnullamento
	 * @return void 
	 * @throws ActionException
	 */
	public void richiestaCancellazione(RichiestaAnnullamento bean)throws ActionException{	
		this.csa.richiestaCancellazione(bean);
	}

	/****************************************************************************************************
	 * carica il bean di Collaudo in base all'id di aggiudicazione ed alla data di inizioS
	 * @param idAggiud long
	 * @param dataInizioAggiud Timestamp
	 * @return CollaudiBean
	 * @throws ActionException
	 */
	public CollaudoBean load(long idAggiud , Timestamp dataInizioAggiud)throws ActionException{
		return this.csa.load(idAggiud, dataInizioAggiud);
	}
	
	/****************************************************************************************************
	 * carica il bean di Collaudo in base all'id di aggiudicazione ed alla data di inizioS
	 * @param idAggiud long
	 * @param dataInizioAggiud Timestamp
	 * @return CollaudiBean
	 * @throws ActionException
	 */
	public CollaudoBean loadById(long id , Timestamp dataInizio)throws ActionException{
		return this.csa.loadById(id, dataInizio);
	}

	/******************************************************************************************************
	 * Carica i ruoli relativi alla sezione 
	 * @param sezione String 
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadRuoliSezione(String sezione,Object o)throws ActionException{
		return this.csa.loadRuoliSezione(sezione, o);
	}
	
	/*******************************************************************************************************
	 * Restituisce il Bean dellaconclusione relativa all'aggiudicazione
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @return ConclusioniBean
	 * @throws ActionException
	 */
	public ConclusioneBean getConclusione(long idAggiudicazione,Timestamp dataInizioAggiudicazione) throws ActionException{
		return this.csa.getConclusione(idAggiudicazione, dataInizioAggiudicazione);
	}
	
	/******************************************************************************************************
	 * Ottiene la lista di Accordi associati all'aggiudicazione
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @return List AccordoBean
	 * @throws ActionException
	 */
	public List<AccordoBean> getAccordoBonario(long idAggiudicazione,Timestamp dataInizioAggiudicazione)throws ActionException{
		return this.csa.getAccordoBonario(idAggiudicazione, dataInizioAggiudicazione);
	}
	
	public AggiudicazioneBean getAggiudicazione(long idAggiudicazione,Timestamp dataInizioAgg){
		return this.bsa.getAggiudicazione(idAggiudicazione, dataInizioAgg);
	}
	//2.10 aggiunto il metodo getInizioLavori
	public InizioLavoriBean getInizioLavori(long idAggiudicazione,Timestamp dataInizioAgg) throws ActionException{
		return this.bsa.getInizioLavori(idAggiudicazione, dataInizioAgg);
	}
	//2.10 fine
	
	public InfoComuniBean getInfoComuni(long idInfoComuni,Timestamp dataInizioInfo){
		return this.bsa.getInfoComuni(idInfoComuni, dataInizioInfo);
	}

	public Timestamp annullaCollaudo(Connection conn,  
			RichiestaAnnullamento bean) throws ActionException {
		return this.csa.annullaCollaudo(conn, bean);
	}

	public Timestamp gestisciVariazioniCO(CollaudoBean saBean, RichiestaAnnullamento rab, String cfUtente, String tipoEnte)throws Exception{
		return this.csa.gestisciVariazioniCO(saBean, rab, cfUtente, tipoEnte);
	}
	
	public int confirm(CollaudoBean bean , String cfUtente)throws ActionException{
		return this.csa.confirm(bean, cfUtente);
	}

	public int save(CollaudoBean bean , String cfUtente)throws ActionException{
		return this.csa.save(bean, cfUtente);
	}
}