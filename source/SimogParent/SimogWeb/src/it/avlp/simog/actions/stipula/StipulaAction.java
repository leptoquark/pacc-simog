package it.avlp.simog.actions.stipula;

import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avcp.simog.managers.stipula.StipulaManager;
import it.avlp.simog.actions.BaseAction;
import it.avlp.simog.actions.PubblicazioneAction;
import it.avlp.simog.actions.aggiudicazione.InfoComuniAction;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.stipula.SchedaStipula;
import it.avlp.simog.beans.stipula.StipulaBean;
import it.avlp.simog.common.action.AggiudicatarioAction;
import it.avlp.simog.common.action.AggiudicazioneAction;
import it.avlp.simog.common.servlet.ParametriServletInizioLavori;
import it.avlp.simog.common.servlet.ParametriServletStipula;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.util.ObjectIntrospector;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class StipulaAction extends BaseAction{
	public StipulaAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
	}
	
	/********************************************************************************************
	 * Gestisce il salvataggio 
	 * @param bean StipulaBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int save(StipulaBean bean,String cfUtente)throws ActionException{
		logger.debug("StipulaAction.save, bean da salvare: " + ObjectIntrospector.propertiesInfo(StipulaBean.class, bean));
		
		StipulaManager sMan = new StipulaManager(connection,logger);
		PubblicazioneManager pubMan = new PubblicazioneManager(connection,logger);
		try {
		
			if(bean.getIdStipula()<1){ 
				pubMan.insertPubblicazione(bean.getPubblicazione());
				sMan.insert(bean, cfUtente);
				return 1;
			}
			else {
				int num = sMan.save(bean, cfUtente);
				if(num > 0) pubMan.save(bean.getPubblicazione());
				return num;
			}

		}
		catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	
	}	
	
	/******************************************************************************************************
	 * Gestisce la conferma
	 * @param bean StipulaBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int confirm(StipulaBean bean,String cfUtente)throws ActionException{
		
		StipulaManager sMan = new StipulaManager(connection,logger);
		PubblicazioneManager pubMan = new PubblicazioneManager(connection,logger);
		try {
			int num = sMan.confirm(bean, cfUtente);
			if(num > 0) pubMan.confirm(bean.getPubblicazione());
			return num;
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
		
	}	
	
	/*******************************************************************************************************
	 * Carica il bean di stipula in base all'aggiudicazione indicata
	 * @param idAggiudicazione long
	 * @param dataInizioAgg Timestamp
	 * @return StipulaBean
	 * @throws ActionException
	 */
	public StipulaBean load(long idAggiudicazione,Timestamp dataInizioAgg) throws ActionException {

		StipulaManager man = new StipulaManager(connection, logger);
		PubblicazioneManager pubMan = new PubblicazioneManager(connection,logger);
		StipulaBean sb = null;
		try {
			sb = man.load(idAggiudicazione, dataInizioAgg);

			if(sb.getPubblicazione()!=null)
			    sb.setPubblicazione(pubMan.getPubblicazione(sb.getPubblicazione().getIdPubblicazione(), sb.getPubblicazione().getDataInizioPubblicazione()));
			return  sb;
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
		
	}
	
	/*******************************************************************************************************
	 * Effettua una copia del record di stipula
	 * param idStipula long
	 * param dataInizioStipula Timestamp
	 * param old_data_inizio_pub Timestamp
	 * param new_data_inizio_pub Timestamp
	 * return Timestamp
	 * throws ActionException
	 */
	private Timestamp copyRecord(long idStipula,Timestamp dataInizioStipula,Timestamp old_data_inizio_pub, Timestamp new_data_inizio_pub)throws ActionException {
		
		StipulaManager man = new StipulaManager(connection, logger);
		try { 
			return man.copyRecord(idStipula,dataInizioStipula,old_data_inizio_pub,new_data_inizio_pub);
		} catch (SQLException e) {
			throw new ActionException(e);
			
		}
	}
	
	/***************************************************************************************************
	 * Gestisce la richiesta di annullamento e la storicizzazione dei dati
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public Timestamp richiediAnnullamento(RichiestaAnnullamento bean) throws ActionException {

		String idScheda = bean.getId_record();
		Timestamp dataInizioScheda = bean.getData_inizio_record();
		String idpub = bean.getId_pub();
		Timestamp old_data_inizio_pub = bean.getData_inizio_pub();
		String idLotto = bean.getId_lotto();
		logger.debug("idinfo: "+idScheda+", datainizioinfo: "+dataInizioScheda+", idpub: "+idpub+", datainiziopub: "+old_data_inizio_pub);
		try{
			PubblicazioneManager pubblicazioneManager = new PubblicazioneManager(connection,logger);
			Timestamp new_data_inizio_pub = pubblicazioneManager.copyRecord(Long.parseLong(idpub), old_data_inizio_pub);
			Timestamp nuovadata = copyRecord(Long.parseLong(idScheda),dataInizioScheda,old_data_inizio_pub,new_data_inizio_pub);
			
			if(nuovadata != null) {
				if (super.bsa.scriviAnnullamento(bean)){
					List<Object> attributiChiave = new ArrayList<Object>();
					attributiChiave.add(idScheda);
					attributiChiave.add(dataInizioScheda);
					LogBloccoDatiManager.loggingCANCELREQ(connection, logger, bean.getRichiedente(), bean.getBlocco(), attributiChiave);
					return nuovadata;
				}
			}
				
			return nuovadata;
		}
		catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/***************************************************************************************************
	 * Gestisce la richiesta di cancellazione e la storicizzazione dei dati
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public void richiediCancellazione(RichiestaAnnullamento bean) throws ActionException {

		//logger.debug
		String idScheda = bean.getId_record();
		Timestamp dataInizioScheda = bean.getData_inizio_record();
		String idpub = bean.getId_pub();
		Timestamp old_data_inizio_pub = bean.getData_inizio_pub();
		logger.debug("idinfo: "+idScheda+", datainizioinfo: "+dataInizioScheda+", idpub: "+idpub+", datainiziopub: "+old_data_inizio_pub);
		// fine logger.debug
		
		try{
			
			if (super.bsa.scriviAnnullamento(bean)){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idScheda);
				attributiChiave.add(dataInizioScheda);
				LogBloccoDatiManager.loggingDELETEREQ(connection, logger, bean.getRichiedente(), bean.getBlocco(), attributiChiave);
			}
				
		}
		catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/*******************************************************************************************************
	 * Ottiene il bean con i dati del bean stipula. 
	 * @param request HttpServletRequest
	 * @return StipulaBean
	 * @throws ActionException
	 */
	public StipulaBean getBean(HttpServletRequest request) throws ActionException {
		StipulaBean sb = new StipulaBean();
		//dati aggiudicazione
		sb.setIdAggiudicazione(getDatiGara(request).getIdAggiudicazione());
		sb.setDataInizioAggiudicazione(getDatiGara(request).getDataInizioAggiudicazione());
		//dati pubblicazione
		PubblicazioneBean pubblicazione = new PubblicazioneAction(connection,logger).getBean(request);
		sb.setPubblicazione(pubblicazione);

		//altri...
		sb.setIdStipula(getLongReqParameter(request, -1, ParametriServletStipula.ID_STIPULA));
		sb.setDataInizioStipula(getTimestampReqParameter(request, null, ParametriServletStipula.DATA_INIZIO_STIPULA));
		sb.setDataStipulaContratto(getStringReqParameter(request, null , ParametriServletStipula.FIELD_NAME_DATA_STIPULA_CONTRATTO));
		sb.setDataDecorrenza(getStringReqParameter(request, null , ParametriServletStipula.FIELD_NAME_DATA_DECORRENZA_STIPULA));
		sb.setDataScadenza(getStringReqParameter(request, null , ParametriServletStipula.FIELD_NAME_DATA_SCADENZA_STIPULA));
		
		return sb;
	}
	
	public AggiudicazioneBean getAggiudicazione(long idAggiudicazione,Timestamp dataInizioAgg){
		return super.bsa.getAggiudicazione(idAggiudicazione, dataInizioAgg);
	}
	public InfoComuniBean getInfoComuni(long idInfoComuni,Timestamp dataInizioInfo){
		return super.bsa.getInfoComuni(idInfoComuni, dataInizioInfo);
	}
	
	/*******************************************************************************************************
	 * Restituisce il bean della pubblicazione  
	 * @param idPubblicazione long
	 * @param dataInizioPubblicazione Timestamp
	 * @return PubblicazioneBan
	 * @throws ActionException
	 */
	public PubblicazioneBean getPubblicazione(long idPubblicazione,Timestamp dataInizioPubblicazione) throws ActionException{
		PubblicazioneManager pm = new PubblicazioneManager(connection,logger);
		PubblicazioneBean pb = null;
		try{
			pb = pm.getPubblicazione(idPubblicazione, dataInizioPubblicazione);
		}catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}return pb;
	}

}
