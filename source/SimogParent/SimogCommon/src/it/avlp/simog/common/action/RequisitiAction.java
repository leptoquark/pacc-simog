package it.avlp.simog.common.action;

import it.avcp.simog.managers.aggiudicazione.RequisitiManager;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.aggiudicazione.RequisitiBean;
import it.avlp.simog.exception.ActionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;



public class RequisitiAction extends BaseSharedAction {

	public static String CLAZZ = "RequisitiAction";
	
	public RequisitiAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		
	}
	
	/*****************************************************************************************
	 * Imposta i dati di aggiudicazione nei requisiti. 
	 * <p>
	 * Tali dati sono :
	 * <ul>
	 * <li>Id Aggiudicazione
	 * <li>data Inizio Aggiudicazione
	 * </ul>
	 * 
	 * @param requisiti List&lt;RequisitiBean&gt;
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws ActionException
	 */
	public void setDatiAggiudicazione(List<RequisitiBean> requisiti, long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException{
		for (RequisitiBean requisitiBean : requisiti) {
			requisitiBean.setIdAggiudicazione(idAggiudicazione);
			requisitiBean.setDataInizioAggiudicazione(dataInizioAggiudicazione);
		}
			
		
	}
	
	/*****************************************************************************************
	 * Restituisce una lista dei Requisiti in base ai dati dell'aggiudicazione in ingresso
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @param ignoraStato TODO
	 * @return List&lt;RequisitiBean&gt;
	 */
	public List<RequisitiBean> loadMany(long idAggiudicazione, Timestamp dataInizioAggiudicazione, boolean ignoraStato){
		String mtd = "loadRequisiti";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		RequisitiManager reqMan = new RequisitiManager(connection, logger);
		List<RequisitiBean> reqList = new ArrayList<RequisitiBean>();
		try {
			reqList = reqMan.loadMany(idAggiudicazione, dataInizioAggiudicazione, ignoraStato);
//			logger.debug("--------------------------------");
//			for(RequisitiBean rb : reqList){
//				logger.debug("id_categoria: "+rb.getIdCategoria()+", importo: "+ rb.getClasseImporto());
//			}
//			logger.debug("--------------------------------");
		} catch (SQLException e) {

			logger.fatal(e.getMessage());
			//e.printStackTrace();
		}
		logger.debug(logPrefix + "exit");
		return reqList;
	}

	
	/**************************************************************************************************
	 * Gestisce il salvataggio della lista dei requisiti. 
	 * <p> Ad ogni elemento della lista dei <code>RequisitiBean</code> viene impostato 
	 * il valore <code>idAggiudicazione</code> e <code>dataInizioAggiudicazione</code> e quindi 
	 * memorizzati
	 * @param requisiti List&lt;RequisitiBean&gt;
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp  
	 * @throws ActionException
	 */
	public void save(List<RequisitiBean> requisiti, long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException {
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix+"START");
		RequisitiManager reqMan = new RequisitiManager(connection, logger);
		
		try{
			reqMan.deleteRequisiti(idAggiudicazione, dataInizioAggiudicazione);
			for (RequisitiBean requisitiBean : requisiti) {
				requisitiBean.setIdAggiudicazione(idAggiudicazione);
				requisitiBean.setDataInizioAggiudicazione(dataInizioAggiudicazione);
				reqMan.save(requisitiBean);
			}
			
		
		}catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
		logger.debug(logPrefix+"END");
		
	}

	/****************************************************************************************************
	 * Gestisce la conferma dei dati
	 * @param requisiti List&lt;RequisitiBean&gt;
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws ActionException
	 */
	public void confirm(List<RequisitiBean> requisiti, long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws ActionException{
		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix+"START");
		RequisitiManager reqMan = new RequisitiManager(connection, logger);
		
		try{
			reqMan.deleteRequisiti(idAggiudicazione, dataInizioAggiudicazione);

			for (RequisitiBean requisitiBean : requisiti) {
				reqMan.confirm(requisitiBean);
			}
		
		}catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
		
		logger.debug(logPrefix+"END");
	
	
	}
	
	/**********************************************************************************************
	 * Gestisce la richiesta di annullamento
	 * @param bean RichiestaAnnullamento
	 * @param datavecchia Timestamp
	 * @return boolean
	 * @throws ActionException
	 */
	public boolean richiediAnnullamento(RichiestaAnnullamento bean,Timestamp datavecchia) throws ActionException {
		RequisitiManager reqMan = new RequisitiManager(connection, logger);
		
		try {
		
			return reqMan.copyRecord(bean.getId_record(), bean.getData_inizio_record(), datavecchia);
		
		} catch (SQLException e) {
			
			logger.fatal(e);
			return false;
		}catch(Exception ex){
			logger.fatal(ex);
			throw new ActionException(ex);
		}
		
	}

}
