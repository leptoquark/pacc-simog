package it.avlp.simog.actions.r129;

import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avcp.simog.managers.r129.R129Manager;
import it.avlp.simog.actions.BaseAction;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.common.servlet.ParametriServletR129;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class R129Action extends BaseAction {
	public static String CLAZZ = "R129Action";

	public R129Action(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);

	}

	/**********************************************************************************************
	 * restituisce il Bean di R129 associato all'aggiudicazione
	 * @param request HttpServletRequest
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @return R129Bean
	 * @throws ActionException
	 */
	public R129Bean getBean(HttpServletRequest request,long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException {
		String mtd = "getBean";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		R129Bean bean = new R129Bean();
		bean.setIdRecord(getLongReqParameter(request, -1,
				ParametriServletR129.FIELD_NAME_ID_RECORD));
		bean.setDataInizioRecord(getTimestampReqParameter(request, null,
				ParametriServletR129.FIELD_NAME_DATA_INIZIO_RECORD));
		bean.setTipoComunicazione(getStringReqParameter(request, null,
				ParametriServletR129.FIELD_NAME_TIPO_COMUNICAZIONE));
		bean.setDurataSospensione(getIntReqParameter(request, 0,
				ParametriServletR129.FIELD_NAME_DURATA_SOSPENSIONE));
		bean.setMotivoSospensione(getStringReqParameter(request, null,
				ParametriServletR129.FIELD_NAME_MOTIVAZIONE_SOSPENSIONE));
		bean.setDataIstRecesso(getStringReqParameter(request, null,
				ParametriServletR129.FIELD_NAME_DATA_IST_RECESSO));
		bean.setFlagAccolta(getStringReqParameter(request, null,
				ParametriServletR129.FIELD_NAME_FLAG_ISTANZA_RECESSO));
		bean.setFlagTardiva(getStringReqParameter(request, null,
				ParametriServletR129.FIELD_NAME_FLAG_TARDIVA));
		bean.setFlagRipresa(getStringReqParameter(request, null,
				ParametriServletR129.FIELD_NAME_FLAG_RIPRESA));
		bean.setFlagRiserva(getStringReqParameter(request, null,
				ParametriServletR129.FIELD_NAME_FLAG_RISERVE));
		bean.setDataTermine(getStringReqParameter(request, null,
				ParametriServletR129.FIELD_NAME_DATA_TERMINE));
		bean.setDataConsegna(getStringReqParameter(request, null,
				ParametriServletR129.FIELD_NAME_DATA_CONSEGNA_LAVORI));
		bean.setDataComunicazione(getStringReqParameter(request, null,
				ParametriServletR129.FIELD_NAME_DATA_COMUNICAZIONE));
		bean.setIdAggiudicazione(idAggiudicazione);
		bean.setDataInizioAggiudicazione(dataInizioAggiudicazione);

		try {

			bean.setImportoSpese(new BigDecimal(PageHelper
					.formattaImporto(getStringReqParameter(request, "0",
							ParametriServletR129.FIELD_NAME_RIMBORSO_SPESE))));

		} catch (Exception e) {

			bean.setImportoSpese(new BigDecimal(0));
		}

		try {

			bean.setImportoOneri(new BigDecimal(PageHelper
					.formattaImporto(getStringReqParameter(request, "0",
							ParametriServletR129.FIELD_NAME_ONERI))));

		} catch (Exception e) {

			bean.setImportoSpese(new BigDecimal(0));
		}
		logger.debug(logPrefix
				+ ObjectIntrospector.propertiesInfo(R129Bean.class, bean));
		return bean;
	}

	
	/**************************************************************************************************
	 * Effettua il salvataggio 
	 * @param bean R129Bean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int save(R129Bean bean, String cfUtente) throws ActionException {
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(R129Bean.class, bean));
		R129Manager rManager = new R129Manager(connection, logger);
		try {
			if (bean.getIdRecord() < 1){
				rManager.insert(bean, cfUtente);
				return 1;
			}else{
				if (!rManager.existR129(bean.getIdRecord(), bean
						.getDataInizioRecord()))
					throw new ActionException("Scheda inesistente");
				return rManager.save(bean, cfUtente);
			}
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}

	// la scheda viane confermata, in questo stato la scheda non puo essere modificata a meno che 
	// non venga annullata

	/***********************************************************************************************
	 * Gestisce la conferma della scheda
	 * @param bean R129Bean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int confirm(R129Bean bean, String cfUtente) throws ActionException {
		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(R129Bean.class, bean));
		R129Manager rManager = new R129Manager(connection, logger);
		try {
			if (!rManager.existR129(bean.getIdRecord(), bean
					.getDataInizioRecord()))
				throw new ActionException("Scheda inesistente");

			return rManager.confirm(bean, cfUtente);

		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}

	/*****************************************************************************************************
	 * Gestisce la richiesta di annullamento 
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp 
	 * @throws ActionException
	 */
	public Timestamp richiediAnnullamento(RichiestaAnnullamento bean)
			throws ActionException {
		String idrecord = bean.getId_record();
		Timestamp datainiziorecord = bean.getData_inizio_record();
		String idLotto = bean.getId_lotto();
		String blocco = bean.getBlocco();
		String cfUtente = bean.getRichiedente();
		try {
			Timestamp nuovaDataInizio = (new R129Manager(connection, logger)
					.copyRecord(Long.parseLong(idrecord), datainiziorecord));

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
	
	/*****************************************************************************************************
	 * Gestisce la richiesta di cancellazione 
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp 
	 * @throws ActionException
	 */
	public void richiediCancellazione(RichiestaAnnullamento bean)	throws ActionException {
		
		String idrecord = bean.getId_record();
		Timestamp datainiziorecord = bean.getData_inizio_record();

		try {
		

			if (super.bsa.scriviAnnullamento(bean)){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idrecord);
				attributiChiave.add(datainiziorecord);
				LogBloccoDatiManager.loggingDELETEREQ(connection, logger, bean.getRichiedente(), bean.getBlocco(), attributiChiave);
			}

		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
	}
	
  // CArica tutte le schede relative ad una aggiudicazione
	/**********************************************************************************************
	 * Restituisce una lista di R129Bean in base all'aggiudicazione
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @return List&lt;R129Bean&gt;
	 * @throws ActionException
	 */
	public List<R129Bean> loadAllByAgg(long idAggiudicazione, Timestamp dataInizioAggiudicazione)
			throws ActionException {
		
		R129Manager rManager = new R129Manager(connection, logger);

		try {

			return rManager.loadMany(idAggiudicazione, dataInizioAggiudicazione);

		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}

	}

	/***********************************************************************************************
	 * Carica le informazioni della R129 in base all'id del record e la data di inizio 
	 * @param idRecord long
	 * @param dataInizioRecord Timestamp
	 * @return R129Bean
	 * @throws ActionException
	 */
	public R129Bean loadOne(long idRecord, Timestamp dataInizioRecord) throws ActionException {
		return loadOne(idRecord, dataInizioRecord, null);

	}

	/**********************************************************************************************
	 * Carica le informazioni della R129 in base all'id del record e la data di inizio 
	 * @param idRecord long
	 * @param dataInizioRecord Timestamp
	 * @param newDataInizioRecord Timestamp
	 * @return R129Bean
	 * @throws ActionException
	 */
	public R129Bean loadOne(long idRecord, Timestamp dataInizioRecord,
			Timestamp newDataInizioRecord) throws ActionException {
		
		if (newDataInizioRecord == null)
			newDataInizioRecord = dataInizioRecord;

		R129Manager rManager = new R129Manager(connection, logger);

		try {

			return rManager.loadOne(idRecord, newDataInizioRecord);

		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see it.avlp.simog.actions.BaseAction#getAggiudicazione(long, java.sql.Timestamp)
	 */
	public AggiudicazioneBean getAggiudicazione(long idAggiudicazione,Timestamp dataInizioAgg) {
		return super.bsa.getAggiudicazione(idAggiudicazione, dataInizioAgg);
	}
	/* (non-Javadoc)
	 * @see it.avlp.simog.actions.BaseAction#getInfoComuni(long, java.sql.Timestamp)
	 */
	public InfoComuniBean getInfoComuni(long idInfoComuni,Timestamp dataInizioInfo) {
		return super.bsa.getInfoComuni(idInfoComuni, dataInizioInfo);
	}
	/**
	 * Metodo che recupera una bean InizioLavori dati l'idAggiudcazione e data inizio Aggiudicazione
	 * 
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
