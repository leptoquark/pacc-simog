package it.avlp.simog.common.action;


import it.avcp.simog.managers.aggiudicazione.FinanziamentoManager;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.aggiudicazione.TipoFinanziamentoBean;
import it.avlp.simog.exception.ActionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

public class FinanziamentoAction extends BaseSharedAction{
	public static String CLAZZ = "FinanziamentoAction";
	
	public FinanziamentoAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		
	}
	
	/***************************************************************************************************
	 * Imposta i dati di aggiudicazione
	 * @param bean List&lt;TipoFinanziamentoBean&gt;
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws ActionException
	 */
	public void setDatiAggiudicazione(List<TipoFinanziamentoBean> bean, long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException{
		for(int i = 0; i < bean.size(); i++){
			bean.get(i).setIdAggiudicazione(idAggiudicazione);
			bean.get(i).setDataInizioAggiudicazione(dataInizioAggiudicazione);
		}
	}
	
	/*******************************************************************************************************
	 * Effettua il salvataggio dei dati di Finanziamento
	 * @param listaFinanziamenti List&lt;TipoFinanziamentoBean&gt;
	 * @param idAggiudicazione long	
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws ActionException
	 */
	public void save(List<TipoFinanziamentoBean> listaFinanziamenti, long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException{
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + "START"); 
		FinanziamentoManager fMan = new FinanziamentoManager(connection, logger);
		try{
			fMan.deleteFinanziamenti(idAggiudicazione, dataInizioAggiudicazione);
			for(TipoFinanziamentoBean bean: listaFinanziamenti){
				bean.setIdAggiudicazione(idAggiudicazione);
				bean.setDataInizioAggiudicazione(dataInizioAggiudicazione);
				fMan.save(bean);	
			}
				
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
		logger.debug(logPrefix + "END");  		
	}

	/********************************************************************************************************
	 * carica in una lista i dati di finaziamenti di una aggiudicazione
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @param ignoraStato TODO
	 * @return List&lt;TipoFinanziamentoBean&gt;
	 * @throws ActionException
	 */
	public List<TipoFinanziamentoBean> loadMany(long idAggiudicazione, Timestamp dataInizioAggiudicazione, boolean ignoraStato) throws ActionException {
		String mtd = "load";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		List< TipoFinanziamentoBean> ris = null;
		FinanziamentoManager fMan = new FinanziamentoManager(connection, logger);
		try {
			ris =  fMan.loadMany(idAggiudicazione, dataInizioAggiudicazione, ignoraStato);
		    logger.debug("caricamento condizioni");
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
		return ris;
	}
	
	/********************************************************************************************************
	 * Effettua la conferma dei dati di Finanziamento
	 * @param listaFinanziamenti List&lt;TipoFinanziamentoBean&gt;
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws ActionException
	 */
	public void confirm(List<TipoFinanziamentoBean> listaFinanziamenti,long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException{
		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		
		FinanziamentoManager fMan = new FinanziamentoManager(connection, logger);
		
		try {
			fMan.deleteFinanziamenti(idAggiudicazione, dataInizioAggiudicazione);
			for(TipoFinanziamentoBean bean: listaFinanziamenti){
				bean.setIdAggiudicazione(idAggiudicazione);
				bean.setDataInizioAggiudicazione(dataInizioAggiudicazione);
				fMan.confirm(bean);	
			}
		
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		} 	
	}
	
	
	/****************************************************************************************************
	 * effettua la richiesta di finanziamento 
 	 * @param bean RichiestaAnnullamento
	 * @param datavecchia Timestamp
	 * @return boolean
	 * @throws ActionException
	 */
	public boolean richiediAnnullamento(RichiestaAnnullamento bean,Timestamp datavecchia) throws ActionException {
		FinanziamentoManager fMan = new FinanziamentoManager(connection, logger);
		
		try {
		
			return fMan.copyRecord(bean.getId_record(), bean.getData_inizio_record(), datavecchia);
		
		} catch (SQLException e) {
			logger.fatal(e);
			return false;
		}catch(Exception ex){
//			log come fatal demandato al chiamante
			logger.error(ex);
			throw new ActionException(ex);
		}
		
	}
	
	
	
	
}
