package it.avlp.simog.actions.conclusione;

import it.avcp.simog.managers.conclusione.ConclusioniManager;
import it.avlp.simog.actions.BaseAction;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.common.servlet.ParametriServletConclusioni;
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

public class ConclusioniAction extends BaseAction {
	
	public ConclusioniAction (Connection activeConnection , Logger logger){
		super(activeConnection , logger);
	}
	
	public static String CLAZZ = "ConclusioniAction";
	
	/********************************************************************************************************
	 * Restituisce il bean contenente le informazioni sulla conclusione associata all'aggiudicazione
	 * @param request HttpServletRequest
	 * @param idAggiud long
	 * @param dataInizioAggiud Timestamp
	 * @return CoclusioniBean
	 * @throws ActionException
	 */
	public ConclusioneBean getBean(HttpServletRequest request , long idAggiud , Timestamp dataInizioAggiud)throws ActionException{
		String mtd = "getBean";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		ConclusioneBean bean = new ConclusioneBean();
		//getTipoReqPar
		bean.setDataFinUltim(getTimestampReqParameter(request, null, ParametriServletConclusioni.FIELD_NAME_DATA_FINE_ULTIM));
		bean.setDataIniUltim(getTimestampReqParameter(request, null, ParametriServletConclusioni.FIELD_NAME_DATA_INIZIO_ULTIM));
		bean.setDataInizioAggiudicazione(dataInizioAggiud);
		bean.setDataRisoluzione(getStringReqParameter(request, null, ParametriServletConclusioni.FIELD_NAME_DATA_RISOLUZIONE));
		bean.setDataUltimazione(getStringReqParameter(request, null, ParametriServletConclusioni.FIELD_NAME_DATA_ULTIMAZIONE));
		bean.setFlagOneri(getStringReqParameter(request, null, ParametriServletConclusioni.FIELD_NAME_FLAG_ONERI));
		bean.setFlagPolizza(getStringReqParameter(request, null, ParametriServletConclusioni.FIELD_NAME_FLAG_POLIZZA));
		bean.setIdAggiudicazione(idAggiud);
		bean.setIdUltim(getLongReqParameter(request, -1, ParametriServletConclusioni.FIELD_NAME_ID_ULTIM));
		bean.setMotiviInterruzione(getLongReqParameter(request, 0, ParametriServletConclusioni.FIELD_NAME_MOTIVO_INTERR));
		bean.setMotiviRisoluzione(getLongReqParameter(request, 0, ParametriServletConclusioni.FIELD_NAME_MOTIVO_RISOL));
		bean.setNumInfMort(getLongReqParameter(request, 0, ParametriServletConclusioni.FIELD_NAME_NUM_INF_MORT));
		bean.setNumInfortuni(getLongReqParameter(request, 0, ParametriServletConclusioni.FIELD_NAME_NUMERO_INFORTUNI));
		bean.setNumInfPerm(getLongReqParameter(request, 0, ParametriServletConclusioni.FIELD_NAME_NUM_INF_PERM));
		bean.setOneriRisoluzione(getBigDecimalReqParameter(request, null, ParametriServletConclusioni.FIELD_NAME_ONERI_RISOLUZIONE));
		
		//gm nuovo codice 3.0
		bean.setDataConsegna(getStringReqParameter(request, null, ParametriServletConclusioni.FIELD_NAME_DATA_CONSEGNA));
		bean.setGiorniProroga(getLongReqParameter(request, 0, ParametriServletConclusioni.FIELD_NAME_GIORNI_PROROGA));
		bean.setTermineUltimazione(getStringReqParameter(request, null, ParametriServletConclusioni.FIELD_NAME_TERMINE_ULTIMAZIONE));
		//gm fine nuovo codice 3.0
		
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(ConclusioneBean.class, bean));
		return bean;
	}
	
	/*******************************************************************************************
	 * Gestisce il salvataggio 
	 * @param bean ConclusioneBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int save(ConclusioneBean bean , String cfUtente)throws ActionException{
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(ConclusioneBean.class, bean));
		ConclusioniManager cManager = new ConclusioniManager(connection,logger);
		try{
			if(bean.getIdUltim() < 1){ 
				cManager.insert(bean, cfUtente);
				return 1;
			}
			else{
				if(!cManager.existConclusioni(bean.getIdUltim(), bean.getDataIniUltim())) throw new ActionException("Scheda inesistente");
				return cManager.save(bean, cfUtente);
			}
		}catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/***************************************************************************************************
	 * Gestisce la conferma
	 * @param bean ConclusioneBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int confirm(ConclusioneBean bean , String cfUtente)throws ActionException{
		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(ConclusioneBean.class, bean));
		ConclusioniManager cManager = new ConclusioniManager(connection,logger);
		try{
			if(!cManager.existConclusioni(bean.getIdUltim(), bean.getDataIniUltim()))throw new ActionException("Scheda inesistente");
			return cManager.confirm(bean, cfUtente);
		}catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/*****************************************************************************************************
	 * Gestisce la richiesta di aggiornamento
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
			Timestamp nuovaDataInizio = (new ConclusioniManager(connection,logger).copyRecord(Long.parseLong(id), data));
			if(nuovaDataInizio != null){
				logger.debug("Data nuova: " + nuovaDataInizio);
				if (this.bsa.scriviAnnullamento(bean)){
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
	
	/*****************************************************************************************************
	 * Gestisce la richiesta di aggiornamento
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public void richiestaCancellazione(RichiestaAnnullamento bean)throws ActionException{
		
		String id = bean.getId_record();
		Timestamp data = bean.getData_inizio_record();

		try{
			
			if (this.bsa.scriviAnnullamento(bean)){
				
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(id);
				attributiChiave.add(data);
				LogBloccoDatiManager.loggingDELETEREQ(connection, logger,  bean.getRichiedente(), bean.getBlocco(), attributiChiave);
			}
			
		}
		
		catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
		
	}
	
	/**************************************************************************************************
	 * Carica le informazioni relative alle conclusioni associate all'aggiudicazione
	 * @param idAggiud long
	 * @param dataInizioAggiud Timestamp
	 * @return ConclusioneBean
	 * @throws ActionException
	 */
	public ConclusioneBean load(long idAggiud , Timestamp dataInizioAggiud)throws ActionException{
		ConclusioniManager cManager = new ConclusioniManager(connection , logger);
		try{
			return cManager.load(idAggiud, dataInizioAggiud);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
	}
	
	/***************************************************************************************************
	 * Craica i motivi di interruzione
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadMotiviInterruzione(Object o)throws ActionException{
		ConclusioniManager cManager = new ConclusioniManager(connection,logger);
		try{
			return cManager.loadMotiviInterruzione(o);
		}catch(SQLException e){
			throw new ActionException(e);
		}
	}
	
	/*******************************************************************************************************
	 * Carica i motivi di risoluzione
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadMotiviRisoluzione(Object o)throws ActionException{
		ConclusioniManager cManager = new ConclusioniManager(connection,logger);
		try{
			return cManager.loadMotiviRisoluzione(o);
		}catch(SQLException e){
			throw new ActionException(e);
		}
	}
	
	public AggiudicazioneBean getAggiudicazione(long idAggiudicazione,Timestamp dataInizioAgg) {
		return this.bsa.getAggiudicazione(idAggiudicazione, dataInizioAgg);
	}
	public InfoComuniBean getInfoComuni(long idInfoComuni,Timestamp dataInizioInfo) {
		return this.bsa.getInfoComuni(idInfoComuni, dataInizioInfo);
	}

	public InizioLavoriBean getInizioLavori(long idAggiudicazione,Timestamp dataInizioAggiudicazione)throws ActionException {
		return this.bsa.getInizioLavori(idAggiudicazione, dataInizioAggiudicazione);
	}
	public List<AvanzamentoBean> getAvanzamenti(long idAggiudicazione,Timestamp dataInizioAggiudicazione) throws ActionException{
		return this.bsa.getAvanzamenti(idAggiudicazione, dataInizioAggiudicazione);
	}
		
}
