package it.avlp.simog.common.action;


import it.avcp.simog.manager.cup.CupLottoAggManager;
import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.SimogProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

public class TipoAppaltoAction extends BaseSharedAction{
	public static String CLAZZ = "TipoAppaltoAction";
	
	public TipoAppaltoAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		
	}
	
   private TipoAppaltoManager man = null;

   public TipoAppaltoManager getTipoAppaltoManager() {
      if( man == null ){
         man = new TipoAppaltoManager(connection, logger);
      }
      return man;
   }
	/***********************************************************************************************
	 * Imposta nel tipo di appalto le informazioni relative all'aggiudicazione
	 * @param bean List&lt;TipoAppaltoAggBean&gt;
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws ActionException
	 */
	public void setDatiAggiudicazione(List<TipoAppaltoAggBean> bean, long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException{
		for(int i = 0; i < bean.size(); i++){
			bean.get(i).setIdAggiudicazione(idAggiudicazione);
			bean.get(i).setDataInizioAggiudicazione(dataInizioAggiudicazione);
		}
	}
	
   /***********************************************************************************************
    * Imposta nel tipo di appalto le informazioni relative all'aggiudicazione
    * @param bean List&lt;TipoAppaltoAggBean&gt;
    * @param idAggiudicazione long
    * @param dataInizioAggiudicazione Timestamp
    * @throws ActionException
    */
   public void setIdLotto(List<TipoAppaltoAggBean> bean, long idLotto) throws ActionException{
      for(int i = 0; i < bean.size(); i++){
         bean.get(i).setIdLotto(idLotto);
      }
   }

   /***********************************************************************************************
	 * Gestisce il salvataggio delle informazioni per la tipologia dell'appalto
	 * @param listaAppalti List&lt;TipoAppaltoAggBean&gt;
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws ActionException
	 */
	public void save(List<TipoAppaltoAggBean> listaAppalti, long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException{
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + "START"); 
		TipoAppaltoManager appMan = new TipoAppaltoManager(connection, logger);
		try{
			appMan.deleteAppaltiAgg(idAggiudicazione, dataInizioAggiudicazione);
			for(TipoAppaltoAggBean bean: listaAppalti){
				bean.setIdAggiudicazione(idAggiudicazione);
				bean.setDataInizioAggiudicazione(dataInizioAggiudicazione);
				appMan.save(bean);	
			}
				
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
		logger.debug(logPrefix + "END");  		
	}
	
	/*************************************************************************************************
	 * Carica le tipologie associate ad appalti per Lavori
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @param tipoEnte String
	 * @return List&lt;TipoAppaltoAggBean&gt;
	 * @throws ActionException
	 */
	public List<TipoAppaltoAggBean> loadManyL(long idAggiudicazione, Timestamp dataInizioAggiudicazione, String tipoEnte, boolean ignoraStato) throws ActionException { 
		return loadMany(idAggiudicazione, dataInizioAggiudicazione, true, tipoEnte, ignoraStato);
	}
	/*************************************************************************************************
	 * Carica le tipologie associate ad appalti per Forniture e Servizi
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @param tipoEnte String
	 * @return List&lt;TipoAppaltoAggBean&gt;
	 * @throws ActionException
	 */
	public List<TipoAppaltoAggBean> loadManyFS(long idAggiudicazione, Timestamp dataInizioAggiudicazione, String tipoEnte, boolean ignoraStato) throws ActionException {
		return loadMany(idAggiudicazione, dataInizioAggiudicazione, false, tipoEnte, ignoraStato);
	}

	/******************************************************************************************************
	 * Effettua il caricamenti delle informazioni di TipoAppalto per appalti relativi a Lavori nel caso in cui 
	 * il parametro lavori sia True, per appalti relativi a Servizi e Forniture in caso il parametro risulti False. 
	 * Le informazioni sono associate all'aggiudicazione identificata da Id e data di inizio
	 * param idAggiudicazione long
	 * param dataInizioAggiudicazione Timestamp
	 * param lavori boolean
	 * param tipoEnte String
	 * return List&lt;TipoAppaltoAggBean&gt;
	 * throws ActionException
	 */
	private List<TipoAppaltoAggBean> loadMany(long idAggiudicazione, Timestamp dataInizioAggiudicazione, boolean lavori, String tipoEnte, boolean ignoraStato) throws ActionException {
		String mtd = "load";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		List< TipoAppaltoAggBean> ris = null;
		TipoAppaltoManager appMan = new TipoAppaltoManager(connection, logger);
		try {
			if(lavori)
			ris =  appMan.loadManyL(idAggiudicazione, dataInizioAggiudicazione,tipoEnte, ignoraStato);
			else ris =  appMan.loadManyFS(idAggiudicazione, dataInizioAggiudicazione,tipoEnte, ignoraStato);
		    logger.debug("caricamento condizioni");
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
		return ris;
	}
	
	/**************************************************************************************************
	 * Gestisce la conferma 
	 * @param listaAppalti List&lt;TipoAppaltoAggBean&gt;
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws ActionException
	 */
	public void confirm(List<TipoAppaltoAggBean> listaAppalti,long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException{
		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		
		TipoAppaltoManager appMan = new TipoAppaltoManager(connection, logger);
		
		try {
			appMan.deleteAppaltiAgg(idAggiudicazione, dataInizioAggiudicazione);
			for(TipoAppaltoAggBean bean: listaAppalti){
				bean.setIdAggiudicazione(idAggiudicazione);
				bean.setDataInizioAggiudicazione(dataInizioAggiudicazione);
				appMan.confirm(bean);	
			}
		
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		} 	
	}
	
	
	/**************************************************************************************************
	 * Gestisce la richeista di annullamento
	 * @param bean RichiestaAnnullamento
	 * @param datavecchia Timesatmp
	 * @return booelan
	 * @throws ActionException
	 */
	public boolean richiediAnnullamento(RichiestaAnnullamento bean,Timestamp datavecchia) throws ActionException {
		TipoAppaltoManager appMan = new TipoAppaltoManager(connection, logger);
		
      try {
         InfoGaraBean infoGaraBean = getInfoGaraBeanByLotto(Long.parseLong(bean.getId_lotto()));
         boolean okDataAttivazioneCup = SimogProperties.getInstance().isCUPLotto(infoGaraBean.getDataCreazioneGara());
         if( !okDataAttivazioneCup){
            // Competenza Aggiudicazione
            return appMan.copyRecord(bean.getId_record(), bean.getData_inizio_record(), datavecchia);
         } else {
            // Competenza Lotto
            completaDatiTipoAppalto(Long.parseLong(bean.getId_lotto()), Long.parseLong(bean.getId_record()), bean.getData_inizio_record());
            
            return true;
         }		
		} catch (SQLException e) {
			
			logger.fatal(e);
			return false;
		}catch(Exception ex){
//			log come fatal demandato al chiamante
			logger.error(ex);
			throw new ActionException(ex);
		}
		
	}

   public void completaDatiTipoAppalto(long idLotto, long idAggiudicazione,
         Timestamp dataInizioAggiudicazione) throws ActionException {
      TipoAppaltoManager appMan = new TipoAppaltoManager(connection, logger);
      
      try {
      
         appMan.completaDatiTipoAppalto(idLotto, idAggiudicazione, dataInizioAggiudicazione);
      
      } catch (SQLException e) {
         
         logger.fatal(e);
      }catch(Exception ex){
//       log come fatal demandato al chiamante
         logger.error(ex);
         throw new ActionException(ex);
      }
   }
	
	
	
	
}
