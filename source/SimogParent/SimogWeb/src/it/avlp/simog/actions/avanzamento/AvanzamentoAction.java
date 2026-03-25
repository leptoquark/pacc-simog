package it.avlp.simog.actions.avanzamento;

import it.avcp.simog.managers.avanzamento.AvanzamentoManager;
//2.10 aggiunto
import it.avcp.simog.managers.variante.VarianteManager;
import it.avlp.simog.beans.variante.VarianteBean;
//2.10 fine
import it.avlp.simog.actions.BaseAction;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.db.generated.STATI_AVANZ;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class AvanzamentoAction extends BaseAction{
	
	public AvanzamentoAction(Connection activeConnection , Logger logger) {
		super(activeConnection , logger);
	}
	
	public static String CLAZZ = "AvanzamentoAction";
	
	/******************************************************************************************
	 * Ottiene il bean di Avanzamento
	 * @param request HttpServletRequest
	 * @param idAggiud long
	 * @param dataInizioAggiud Timestamp
	 * @return AvanzamentoBeam
	 * @throws ActionException
	 */
	public AvanzamentoBean getBean(HttpServletRequest request , long idAggiud , Timestamp dataInizioAggiud)throws ActionException{
		String mtd = "getBean";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		AvanzamentoBean bean = new AvanzamentoBean();
		bean.setDataAnticipazione(getStringReqParameter(request, null, STATI_AVANZ.DATA_ANTICIPAZIONE));
		bean.setDataCertificato(getStringReqParameter(request, null, STATI_AVANZ.DATA_CERTIFICATO));
		bean.setDataFineAvanzamento(getTimestampReqParameter(request, null, STATI_AVANZ.DATA_FINE_AVANZAMENTO));
		bean.setDataInizioAggiudicazione(dataInizioAggiud);
		bean.setDataInizioAvanzamento(getTimestampReqParameter(request, null, STATI_AVANZ.DATA_INIZIO_AVANZAMENTO));
		bean.setDataRaggiungimento(getStringReqParameter(request, null, STATI_AVANZ.DATA_RAGGIUNGIMENTO));
		String[] checkbox = request.getParameterValues("modoPagamento");
		if(checkbox == null)bean.setFlagPagamento(null);
		else if(checkbox.length == 2)bean.setFlagPagamento("3");
		else bean.setFlagPagamento(checkbox[0]);
		bean.setFlagRitardo(getStringReqParameter(request, null, STATI_AVANZ.FLAG_RITARDO));
		bean.setIdAggiudicazione(idAggiud);
		bean.setIdAvanzamento(getLongReqParameter(request, -1, STATI_AVANZ.ID_AVANZAMENTO));
		bean.setImportoAnticipazione(getBigDecimalReqParameter(request, null, STATI_AVANZ.IMPORTO_ANTICIPAZIONE));
		bean.setImportoCertificato(getBigDecimalReqParameter(request, null, STATI_AVANZ.IMPORTO_CERTIFICATO));
		bean.setImportoSal(getBigDecimalReqParameter(request, null, STATI_AVANZ.IMPORTO_SAL));
		bean.setNumeroAvanzamento(getIntReqParameter(request, 0, STATI_AVANZ.NUMERO_AVANZAMENTO));
		bean.setNumeroGiorniProroga(getIntReqParameter(request, 0, STATI_AVANZ.NUM_GIORNI_PROROGA));
		bean.setNumeroGiorniScost(getIntReqParameter(request, 0, STATI_AVANZ.NUM_GIORNI_SCOST));
		bean.setDenomStatoAvanz(getStringReqParameter(request, null, STATI_AVANZ.DENOM_AVANZ));
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(AvanzamentoBean.class, bean));
		return bean;
	}
	
	/*************************************************************************************************
	 * Recupera il valore massimo associato agli avanzamenti relativi all'aggiudicazione
	 * @param idAgg Long 
	 * @param dataInizioAgg Timestamp
	 * @return int
	 * @throws ActionException
	 */
	public int getNextAvanzamento(Long idAgg,Timestamp dataInizioAgg)throws ActionException{
		String mtd = "getNextAvanzamento";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		AvanzamentoManager avanzamentoManager = new AvanzamentoManager(connection,logger);
		try{
				return avanzamentoManager.getNextAvanzamento(idAgg, dataInizioAgg);
	
		}catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/***************************************************************************************************
	 * gestisce il salvataggio delle imformazioni di avanzamento
	 * @param bean AvanzamentoBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int save(AvanzamentoBean bean , String cfUtente)throws ActionException{
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(AvanzamentoBean.class, bean));
		AvanzamentoManager avanzamentoManager = new AvanzamentoManager(connection,logger);
		try{
			if(bean.getIdAvanzamento() < 1){
				bean.setNumeroAvanzamento(avanzamentoManager.getNextAvanzamento(bean.getIdAggiudicazione(), bean.getDataInizioAggiudicazione()));
				avanzamentoManager.insert(bean, cfUtente);
				return 1;
			}
			else {
				if(!avanzamentoManager.existAvanzamento(bean.getIdAvanzamento(), bean.getDataInizioAvanzamento()))throw new ActionException("Scheda inesistente");
				return avanzamentoManager.save(bean, cfUtente);
			}
		}catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/***************************************************************************************************
	 * Gestisce la conferma delle informazioni relative all'avanzamento
	 * @param bean AvanzamentoBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int confirm(AvanzamentoBean bean , String cfUtente)throws ActionException{
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(AvanzamentoBean.class, bean));
		AvanzamentoManager avanzamentoManager = new AvanzamentoManager(connection,logger);
		try{
			if(!avanzamentoManager.existAvanzamento(bean.getIdAvanzamento(), bean.getDataInizioAvanzamento()))throw new ActionException("Scheda inesistente");
			else return avanzamentoManager.confirm(bean, cfUtente);
		}catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/***************************************************************************************************
	 * Gestisce la richiesta di annullamento
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public Timestamp richiestaAnnullamento(RichiestaAnnullamento bean)throws ActionException{
		String id = bean.getId_record();
		Timestamp data = bean.getData_inizio_record();
		String idLotto = bean.getId_lotto();
		String blocco = bean.getBlocco();
		String cfUtente = bean.getRichiedente();
		try{
			Timestamp nuovaDataInizio = (new AvanzamentoManager(connection,logger).copyRecord(Long.parseLong(id), data));
			if(nuovaDataInizio != null){
				logger.debug("Data nuova:" + nuovaDataInizio);
				if (super.bsa.scriviAnnullamento(bean)){
					List<Object> attributiChiave = new ArrayList<Object>();
					attributiChiave.add(id);
					attributiChiave.add(data);
					LogBloccoDatiManager.loggingCANCELREQ(connection, logger, cfUtente, blocco, attributiChiave);
					return nuovaDataInizio;
				}
			}
			return null;
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
	}
	
	/***************************************************************************************************
	 * Gestisce la richiesta di cancellazione
	 * @param bean RichiestaAnnullamento
	 * @return void
	 * @throws ActionException
	 */
	public void richiestaCancellazione(RichiestaAnnullamento bean)throws ActionException{
		
		String id = bean.getId_record();
		Timestamp data = bean.getData_inizio_record();

		try{
			
			if (super.bsa.scriviAnnullamento(bean)){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(id);
				attributiChiave.add(data);
				LogBloccoDatiManager.loggingDELETEREQ(connection, logger, bean.getRichiedente(), bean.getBlocco(), attributiChiave);
			}
			
		}
		
		catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
	}
	
	/******************************************************************************************************
	 * Carica un avanzamento identificandolo attraverso l'id e la data di inizio
	 * @param idAvanzamento long
	 * @param dataInizioAvanzamento Timestamp
	 * @return AvanzamentoBean
	 * @throws ActionException
	 */
	public AvanzamentoBean loadOne(long idAvanzamento , Timestamp dataInizioAvanzamento)throws ActionException{
		return loadOne(idAvanzamento,dataInizioAvanzamento,null);
	}
	
	/*******************************************************************************************************
	 *                                           <b>loadOne</b><br>
	 * Il metodo si occupa di reperire l'avanzamento in base all'id dell'avanzamento e la data di inizio o la nuova data di 
	 * inizio 
	 * @param idAvanzamento long
	 * @param dataInizioAvanzamento Timestamp
	 * @param newDataInizioAvanzamento Timesatmp
	 * @return AvanzamentoBean
	 * @throws ActionException
	 */
	public AvanzamentoBean loadOne(long idAvanzamento , Timestamp dataInizioAvanzamento , Timestamp newDataInizioAvanzamento)throws ActionException{
		if(newDataInizioAvanzamento == null) newDataInizioAvanzamento = dataInizioAvanzamento;
		AvanzamentoManager avanzamentoManager = new AvanzamentoManager(connection,logger);
		try{
			return avanzamentoManager.loadOne(idAvanzamento, newDataInizioAvanzamento);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
	}
	
	/********************************************************************************************************
	 * Carica la lista di avanzamenti associata all'aggiudicazione 
	 * @param idAggiud long
	 * @param dataInizioAggiud Timestamp
	 * @return List&lt;AvanzamentoBean&gt;
	 * @throws ActionException
	 */
	public List<AvanzamentoBean> loadMany(long idAggiud , Timestamp dataInizioAggiud)throws ActionException{
		AvanzamentoManager avanzamentoManager = new AvanzamentoManager(connection,logger);
		try{
			return avanzamentoManager.loadMany(idAggiud, dataInizioAggiud);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
	}
	
	public AggiudicazioneBean getAggiudicazione(long idAggiudicazione,Timestamp dataInizioAgg){
		return super.bsa.getAggiudicazione(idAggiudicazione, dataInizioAgg);
	}
	
	public InfoComuniBean getInfoComuni(long idInfoComuni,Timestamp dataInizioInfo){
		return super.bsa.getInfoComuni(idInfoComuni, dataInizioInfo);
	}
	
	//2.10 aggiunto il metodo getVarianti
	public List<VarianteBean> getVarianti(long idAggiud , Timestamp dataInizioAggiud)throws ActionException{
		VarianteManager varianteManager = new VarianteManager(connection,logger);
		try{
			return varianteManager.loadMany(idAggiud, dataInizioAggiud);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
	}
    //2.10 fine
}
