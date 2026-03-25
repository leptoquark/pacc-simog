package it.avlp.simog.common.action;

import it.avcp.simog.managers.inizio.ResponsabileInizioManager;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.exception.ActionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class ResponsabileInizioSharedAction extends BaseSharedAction{
	public static String CLAZZ = "ResponsabileInizioSharedAction";
	public ResponsabileInizioSharedAction(Connection activeConnection,
			Logger logger) {
		super(activeConnection, logger);
		
	}
	
	
	/**
	 * Legge dal DB i dati relativi ai responsabili
	 * @param idInizioLavori long
	 * @param dataInizioLavori Timestamp
	 * @param ignoraStato TODO
	 * @return List&lt;ResponsabileBean&gt;
	 * @throws ActionException
	 */
	public List<ResponsabileBean> loadMany(long idInizioLavori,Timestamp dataInizioLavori, boolean ignoraStato) throws ActionException {

		String mtd = "load";
		String logPrefix = CLAZZ + "." + mtd + ": ";

		ResponsabileInizioManager rm = new ResponsabileInizioManager(connection, logger);
				
		List<ResponsabileBean> result = null;
		try {
			result =  rm.loadMany(idInizioLavori, dataInizioLavori, ignoraStato);
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
		return result;
	}
	 
	

	/**
	 * @param responsabili List&lt;ResponsabileBean&gt;
	 * @param idInizioLavori long
	 * @param dataInizioLavori Timestamp
	 * @throws ActionException
	 */
	public void save(List<ResponsabileBean> responsabili,long idInizioLavori,Timestamp dataInizioLavori) throws ActionException {

		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		ResponsabileInizioManager rm = new ResponsabileInizioManager(connection, logger);
	
		try {
			rm.deleteRecord(idInizioLavori, dataInizioLavori);
			for(ResponsabileBean bean: responsabili ){
				bean.setIdScheda(idInizioLavori);
				bean.setDataInizioScheda(dataInizioLavori);
				rm.save(bean);
			}
		
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		} 
	}
	
	/************************************************************************************************
	 * Salva nel DB i dati inseriti dall'utente e conferma
	 * @param responsabili : List&lt;ResponsabileBean&gt;
	 * @param idInizioLavori long
	 * @param dataInizioLavori Timestamp
	 * @throws ActionException
	 */
	public void confirm(List<ResponsabileBean> responsabili,long idInizioLavori,Timestamp dataInizioLavori) throws ActionException {

		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		ResponsabileInizioManager rm = new ResponsabileInizioManager(connection, logger);
	
		try {
			rm.deleteRecord(idInizioLavori, dataInizioLavori);
			for(ResponsabileBean bean: responsabili ){
				bean.setIdScheda(idInizioLavori);
				bean.setDataInizioScheda(dataInizioLavori);
				rm.confirm(bean);
			}
		
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		} 
	}
	
	
	/*********************************************************************************************************
	 * Gestisce la richiesta di annullamento e storicizzazione dei dati
	 * @param bean RichiestaAnnullamento
	 * @param datavecchia Timestamp
	 * @return boolean
	 * @throws ActionException
	 */
	public boolean richiediAnnullamento(RichiestaAnnullamento bean,Timestamp datavecchia) throws ActionException {
		ResponsabileInizioManager rm = new ResponsabileInizioManager(connection, logger);
		
		try {
		
			return rm.copyRecord(Long.parseLong(bean.getId_record()), bean.getData_inizio_record(), datavecchia);
		
		} catch(Exception ex){
//			log come fatal demandato al chiamante
			logger.error(ex);
			throw new ActionException(ex);
		}
		
	}
	
	
	/************************************************************************************************
	 * Carica i ruoli relativi alla sezione 
	 * @param sezione String
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadRuoliSezione(String sezione,Object o)throws ActionException{
		ResponsabileInizioManager rcManager = new ResponsabileInizioManager(connection,logger);
		try{
			return rcManager.loadRuoliSezione(sezione,o);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	

}
