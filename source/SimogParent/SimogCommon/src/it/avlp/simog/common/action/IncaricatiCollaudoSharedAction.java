package it.avlp.simog.common.action;

import it.avcp.simog.managers.collaudo.ResponsabileCollManager;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.exception.ActionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

public class IncaricatiCollaudoSharedAction extends BaseSharedAction{
	
	public static String CLAZZ = "IncaricatiCollaudoAction";

	public IncaricatiCollaudoSharedAction(Connection activeConnection,
			Logger logger) {
		super(activeConnection, logger);
		
	}
	
	/******************************************************************************************************
	 * Carica la lista dei responsabili associati al collaudo 
	 * @param idCollaudo long
	 * @param dataInizioCollaudo Timestamp
	 * @param ignoraStato TODO
	 * @return List&lt;ResponsabileBean&gt;
	 * @throws ActionException
	 */
	public List<ResponsabileBean> loadMany(long idCollaudo , Timestamp dataInizioCollaudo, boolean ignoraStato) throws ActionException {

		String mtd = "load";
		String logPrefix = CLAZZ + "." + mtd + ": ";

		ResponsabileCollManager rm = new ResponsabileCollManager(connection, logger);
				
		List<ResponsabileBean> result = null;
		try {
			result =  rm.load(idCollaudo, dataInizioCollaudo, ignoraStato);
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
		return result;
	}
	
	/*****************************************************************************************************
	 * Gestisce il salvataggio della lista di <code>ResponsabileBean</code> inserendo per ogni responsabile 
	 * <code>idCollaudo</code> e <code>dataInizioCollaudo</code>
	 * @param responsabili List&lt;ResponsabileBean&gt;
	 * @param idCollaudo long
	 * @param dataInizioCollaudo Timestamp
	 * @throws ActionException
	 */
	public void save(List<ResponsabileBean> responsabili,long idCollaudo,Timestamp dataInizioCollaudo) throws ActionException {

		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		ResponsabileCollManager rm = new ResponsabileCollManager(connection, logger);
	
		try {
			rm.deleteRecord(idCollaudo, dataInizioCollaudo);
			for(ResponsabileBean bean: responsabili ){
				bean.setIdScheda(idCollaudo);
				bean.setDataInizioScheda(dataInizioCollaudo);
				rm.save(bean);
			}
		
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		} 
	}
	
	/*******************************************************************************************************
	 * Gestisce la conferma dei dati impostatondo lo stato dei record a Confermato
	 * @param responsabili List&lt;ReponsabileBean&gt;
	 * @param idCollaudo long
	 * @param dataInizioCollaudo Timestamp
	 * @throws ActionException
	 */
	public void confirm(List<ResponsabileBean> responsabili,long idCollaudo,Timestamp dataInizioCollaudo) throws ActionException {

		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		ResponsabileCollManager rm = new ResponsabileCollManager(connection, logger);
	
		try {
			rm.deleteRecord(idCollaudo, dataInizioCollaudo);
			for(ResponsabileBean bean: responsabili ){
					bean.setIdScheda(idCollaudo);
					bean.setDataInizioScheda(dataInizioCollaudo);
					rm.confirm(bean);
			}
		
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		} 
	}
	
	/*****************************************************************************************************
	 * Gestisce la richiesta di annullamento aggiornando lo storico
	 * @param bean RichiestaAnnullamento
	 * @param datavecchia Timestamp
	 * @return booelan
	 * @throws ActionException
	 */
	public boolean richiediAnnullamento(RichiestaAnnullamento bean,Timestamp datavecchia) throws ActionException {
		ResponsabileCollManager rm = new ResponsabileCollManager(connection, logger);
		
		try {
		
			return rm.copyRecord(Long.parseLong(bean.getId_record()), bean.getData_inizio_record(), datavecchia);
		
		} catch(Exception ex){
//			log come fatal demandato al chiamante
			logger.error(ex);
			throw new ActionException(ex);
		}
		
	}
	
}
