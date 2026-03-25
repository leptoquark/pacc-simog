package it.avlp.simog.actions.sospensioni;

import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avcp.simog.managers.sospensioni.SospensioniManager;
import it.avlp.simog.actions.BaseAction;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.common.servlet.ParametriServletSospensioni;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;



public class SospensioniAction extends BaseAction {

	public static String CLAZZ = "SospensioniAction";

	public SospensioniAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);

	}
	
	/*****************************************************************************************************
	 * Carica nel Bean di <code>SospensioniBean</code> i dati della sospensione associati all'aggiudicazione 
	 * recuperandoli dalla request. 
	 * 
	 * @param request HttpServletRequest
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @return SospensioniBean
	 * @throws ActionException
	 */
	
	public SospensioniBean getBean(HttpServletRequest request,long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException {
		String mtd = "getBean";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		
		// A parte ID_SOSPENSIONE gli altri campi sono in ordine alfabetico ... 
		// o almeno lo stesso ordine con cui compaiono con CTRL+SPAZIO -luca-
		
		SospensioniBean bean = new SospensioniBean();
		bean.setIdSospensione((getLongReqParameter(request, -1,
				ParametriServletSospensioni.FIELD_NAME_ID_SOSPENSIONE)));
		bean.setDataInizioSosp(getTimestampReqParameter(request, null,
				ParametriServletSospensioni.FIELD_NAME_DATA_INIZIO_SOSP));
		bean.setDataFineSosp(getTimestampReqParameter(request,null, 
				ParametriServletSospensioni.FIELD_NAME_DATA_FINE_SOSP));
		//bean.setDataInizioAggiudicazione(getTimestampReqParameter(request, null, 
		//		ParametriServletSospensioni.FIELD_NAME_DATA_INIZIO_AGGIUDICAZIONE));
		bean.setDataInizioAggiudicazione(dataInizioAggiudicazione);
		bean.setDataVerbRipr(getStringReqParameter(request, null, 
				ParametriServletSospensioni.FIELD_NAME_DATA_VERB_RIPR));
		bean.setDataVerbSosp(getStringReqParameter(request, null, 
				ParametriServletSospensioni.FIELD_NAME_DATA_VERB_SOSP));
		bean.setFlagRiserve(getStringReqParameter(request, null,
				ParametriServletSospensioni.FIELD_NAME_FLAG_RISERVE));
		bean.setFlagSuperoTemp(getStringReqParameter(request, null, 
				ParametriServletSospensioni.FIELD_NAME_FLAG_SUPERO_TEMP));
		bean.setFlagVerbale(getStringReqParameter(request, null, 
				ParametriServletSospensioni.FIELD_NAME_FLAG_VERBALE));
		bean.setIdAggiudicazione(idAggiudicazione);
		bean.setIdMotivoSosp(getLongReqParameter(request, -1, 
				ParametriServletSospensioni.FIELD_NAME_ID_MOTIVO_SOSP));
		bean.setIdStato(getLongReqParameter(request, -1, 
				ParametriServletSospensioni.FIELD_NAME_ID_STATO));
		
		logger.debug(logPrefix
				+ ObjectIntrospector.propertiesInfo(SospensioniBean.class, bean));
		return bean;
	}
	
	
	/**
	 * Inserisce la scheda nel DB, in questo stato la scheda 
	 * 		pu� essere ancora richiamata e modificata
	 * @param bean SospensioniBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int save(SospensioniBean bean, String cfUtente) throws ActionException {
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(SospensioniBean.class, bean));
		SospensioniManager rManager = new SospensioniManager(connection, logger);
		
 		try {
			if (bean.getIdSospensione() < 1){
				rManager.insert(bean, cfUtente);
				return 1;
			}else{
				if (!rManager.existSospensioni(bean.getIdSospensione(), 
											   bean.getDataInizioSosp()) )
					throw new ActionException("Scheda inesistente");
				return rManager.save(bean, cfUtente);
				
				
			}
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}

	
	
	
	/* ------------------------------------------------------------------------------------------------
	 * CONFIRM 
	 * 		la scheda viane confermata, in questo stato la scheda non puo' essere modificata a meno che 
	 * 		non venga prima annullata    -luca-
	 --------------------------------------------------------------------------------------------------*/
	/***************************************************************************************************
	 * Gestrisce la conferma 
	 * @param bean SospensioniBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int confirm(SospensioniBean bean, String cfUtente) throws ActionException {
		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(SospensioniBean.class, bean));
		SospensioniManager rManager = new SospensioniManager(connection, logger);
		try {
			if (!rManager.existSospensioni(bean.getIdSospensione(), 
										  bean.getDataInizioSosp()))
				throw new ActionException("Scheda inesistente");

			return rManager.confirm(bean, cfUtente);

		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/********************************************************************************************
	 * Gestisce la richiesta di annullamento
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public Timestamp richiediAnnullamento(RichiestaAnnullamento bean)
			throws ActionException {
		String idrecord = bean.getId_record(); // qui e' contenuto idSospensioni
		Timestamp datainiziorecord = bean.getData_inizio_record(); // qui e' contenuta la data di inizio della Sospensione 
		String idLotto = bean.getId_lotto();
		String blocco = bean.getBlocco();
		String cfUtente = bean.getRichiedente();
		try {
			Timestamp nuovaDataInizio = (new SospensioniManager(connection, logger).copyRecord(Long.parseLong(idrecord), datainiziorecord));
		
			if (nuovaDataInizio != null) {
		
				logger.debug("Data nuova: " + nuovaDataInizio);
				if (super.bsa.scriviAnnullamento(bean)){
					List<Object> attributiChiave = new ArrayList<Object>();
					attributiChiave.add(idrecord);
					attributiChiave.add(datainiziorecord);
					LogBloccoDatiManager.loggingCANCELREQ(connection, logger, cfUtente, blocco, attributiChiave);
					return nuovaDataInizio;
				}
			}
		
			return null;
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
	}
	
	/********************************************************************************************
	 * Gestisce la richiesta di cancellazione
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public void richiediCancellazione(RichiestaAnnullamento bean) throws ActionException {
		String idrecord = bean.getId_record(); // qui e' contenuto idSospensioni
		Timestamp datainiziorecord = bean.getData_inizio_record(); // qui e' contenuta la data di inizio della Sospensione 

		try {
		
			if (super.bsa.scriviAnnullamento(bean)){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idrecord);
				attributiChiave.add(datainiziorecord);
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
	
	/**********************************************************************************************
	 * Genera una lista delle sospensioni associate ad una aggiudicazione 
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @return List&lt;SospensioniBean&gt;
	 * @throws ActionException
	 */
	public List<SospensioniBean> loadAllByAgg(long idAggiudicazione, Timestamp dataInizioAggiudicazione)
			throws ActionException {
		
		SospensioniManager rManager = new SospensioniManager(connection, logger);

		try {

			return rManager.loadMany(idAggiudicazione, dataInizioAggiudicazione);

		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}

	}
	
	
	/**************************************************************************************************
	 * Carica la sospensione in base ai parametri inseriti id sospensione e data inizio
	 * @param idSospensione long
	 * @param dataInizioSospensione Timestamp
	 * @return SospensioniBean
	 * @throws ActionException
	 */
	public SospensioniBean loadOne(long idSospensione, Timestamp dataInizioSospensione) throws ActionException {
		return loadOne(idSospensione, dataInizioSospensione, null);

	}
	
	/***************************************************************************************************
	 * Carica la sospensione in base ai parametri inseriti id sospensione e data inizio
	 * @param idSospensione long
	 * @param dataInizioSospensione Timestamp
	 * @param newDataInizioSospensione Tiemstamp
	 * @return SospensioniBean
	 * @throws ActionException
	 */
	public SospensioniBean loadOne(long idSospensione, Timestamp dataInizioSospensione,
			Timestamp newDataInizioSospensione) throws ActionException {
		
		if (newDataInizioSospensione == null)
			newDataInizioSospensione = dataInizioSospensione;

		SospensioniManager rManager = new SospensioniManager(connection, logger);

		try {

			return rManager.loadOne(idSospensione, newDataInizioSospensione);

		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
	}
	
	
	
	/******************************************************************************************************
	 * Genera una mappa dei motivi di sospensione
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadMotiviSospensione(Object o) throws ActionException {
		SospensioniManager sm = new SospensioniManager(connection,logger);
		try {
			return sm.loadMotiviSospensione(o);
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
		
		
	}
	/**
	 * @see it.avlp.simog.actions.BaseAction#getAggiudicazione(long, java.sql.Timestamp)
	 */
	public AggiudicazioneBean getAggiudicazione(long idAggiudicazione,Timestamp dataInizioAgg) {
		return super.bsa.getAggiudicazione(idAggiudicazione, dataInizioAgg);
	}
	/**
	 * @see it.avlp.simog.actions.BaseAction#getInfoComuni(long, java.sql.Timestamp)
	 */
	public InfoComuniBean getInfoComuni(long idInfoComuni,Timestamp dataInizioInfo) {
		return super.bsa.getInfoComuni(idInfoComuni, dataInizioInfo);
	}
	/*****************************************************************************************************
	 * Ottiene le informazioni di InizioLavori associete all'aggiudicazione  
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @return InizioLavoriBean
	 * @throws ActionException
	 */
	public InizioLavoriBean getInizioLavori(long idAggiudicazione,Timestamp dataInizioAggiudicazione) throws ActionException{
		InizioLavoriBean inizioLavori = new InizioLavoriBean();
		InizioLavoriManager im = new InizioLavoriManager(connection,logger);
		try{
			inizioLavori = im.load(idAggiudicazione, dataInizioAggiudicazione);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
		return inizioLavori;
	}	
	
	
	
	
	
	
}
