package it.avlp.simog.common.action;

import it.avcp.simog.managers.aggiudicazione.CondizioniManager;
import it.avlp.simog.beans.CondizioneLottoBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.aggiudicazione.CondizioneAggBean;
import it.avlp.simog.exception.ActionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

public class CondizioniAction extends BaseSharedAction {
	public static String CLAZZ = "CondizioniAction";
	
	public CondizioniAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		
	}
	
	
	/******************************************************************************************************
	 * Imposta i dati di aggiudicazione: id aggiudicazione e data inizio aggiudicazione
	 * @param bean List&lt;CondizioneAggBean&gt;
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws ActionException
	 */
	public void setDatiAggiudicazione(List<CondizioneAggBean> bean, long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException{
		for(int i = 0; i < bean.size(); i++){
			bean.get(i).setIdAggiudicazione(idAggiudicazione);
			bean.get(i).setDataInizioAggiudicazione(dataInizioAggiudicazione);
		}
	}
	
	/*******************************************************************************************************
	 * Effettua il salvataggio delle condizioni associate ad una aggiudicazione 
	 * reperita attraverso <code>idAggiudicazione</code> e <code>dataInizioAggiudicazione</code>
	 * @param condizioni List&lt;CondizioneAggBean&gt;
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws ActionException
	 */
	public void save(List<CondizioneAggBean> condizioni, long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException{
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + "START"); 
		CondizioniManager condMan = new CondizioniManager(connection, logger);
		try{
			condMan.deleteCondizioniAgg(idAggiudicazione, dataInizioAggiudicazione);
			for(CondizioneAggBean bean: condizioni){
				bean.setIdAggiudicazione(idAggiudicazione);
				bean.setDataInizioAggiudicazione(dataInizioAggiudicazione);
				condMan.save(bean);	
			}
				
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
		logger.debug(logPrefix + "END");  		
	}

	/**
	 * Effettua il caricamento delle condizioni in base ad id aggiudicazione e data di inizio dell'aggiudicazione 
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @param ignoraStato TODO
	 * @return List&lt;CondizioneAggBean&gt;
	 * @throws ActionException
	 */
	public List<CondizioneAggBean> loadMany(long idAggiudicazione, Timestamp dataInizioAggiudicazione, boolean ignoraStato) throws ActionException {
		String mtd = "load";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		List< CondizioneAggBean> ris = null;
		CondizioniManager condMan = new CondizioniManager(connection, logger);
		try {
			ris =  condMan.loadMany(idAggiudicazione, dataInizioAggiudicazione, ignoraStato);
		    logger.debug("caricamento condizioni");
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
		return ris;
	}
	
	/******************************************************************************************************
	 * Effettua la conferma delle condizioni contenute nella lista in ingresso <code>condizioni</code>
	 * @param condizioni List&lt;CondizioneAggBean&gt; lista di condizioni da confermare
	 * @param idAggiudicazione long per l'id dell'aggiudicazione
	 * @param dataInizioAggiudicazione Timestamp per la data di inizio
	 * @throws ActionException
	 */
	public void confirm(List<CondizioneAggBean> condizioni, long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException{
		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		
		CondizioniManager condMan = new CondizioniManager(connection, logger);
		try {
			condMan.deleteCondizioniAgg(idAggiudicazione, dataInizioAggiudicazione);
			for(CondizioneAggBean bean: condizioni){
				bean.setIdAggiudicazione(idAggiudicazione);
				bean.setDataInizioAggiudicazione(dataInizioAggiudicazione);
				condMan.confirm(bean);	
			}
		
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		} 	
	}
	
	
	/****************************************************************************************************
	 * Effettua la richiesta di annulamento
	 * @param bean RichiestaAnnullamento
	 * @param datavecchia Timestamp
	 * @return boolean
	 * @throws ActionException
	 */
	public boolean richiediAnnullamento(RichiestaAnnullamento bean,Timestamp datavecchia) throws ActionException {
		CondizioniManager condMan = new CondizioniManager(connection, logger);
		
		try {
		
			return condMan.copyRecord(bean.getId_record(), bean.getData_inizio_record(), datavecchia);
		
		}catch(Exception ex){
			logger.fatal(ex);
			throw new ActionException(ex);
		}
		
	}
	
	
	//TICKET ALM #3835
	/**
	 * Effettua il caricamento delle condizioni in base ad id lotto
	 * @param idLotto long
	 * @param ignoraStato TODO
	 * @return List&lt;CondizioneAggBean&gt;
	 * @throws ActionException
	 */
	public List<CondizioneLottoBean> loadManyCondizioniLotto(long idLotto, boolean ignoraStato) throws ActionException {
		String mtd = "load";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		List< CondizioneLottoBean> ris = null;
		CondizioniManager condMan = new CondizioniManager(connection, logger);
		try {
			ris =  condMan.loadManyCondizioniLotto(idLotto, ignoraStato);
		    logger.debug("caricamento condizioni lotto");
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
		return ris;
	}
	
	
	/*******************************************************************************************************
	 * Effettua il salvataggio delle condizioni associate ad un lotto 
	 * reperita attraverso <code>idLotto</code>
	 * @param condizioni List&lt;CondizioneLottoBean&gt;
	 * @param idLotto long
	 * @throws ActionException
	 */
	public void saveCondizioniLotto(List<CondizioneLottoBean> condizioni, long idLotto) throws ActionException{
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + "START"); 
		CondizioniManager condMan = new CondizioniManager(connection, logger);
		try{
			condMan.deleteCondizioniLotto(idLotto);
			for(CondizioneLottoBean bean: condizioni){
				bean.setIdLotto(idLotto);
				condMan.saveCondBean(bean);	
			}
				
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
		logger.debug(logPrefix + "END");  		
	}
	//FINE TICKET ALM #3835

}
