package it.avlp.simog.common.action;

import it.avcp.simog.manager.cup.CupLottoAggManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class AggiudicazioneAction extends BaseSharedAction{
	
	public static String CLAZZ = "AggiudicazioneAction";
	
	public AggiudicazioneAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
	}
	
		
	
	
	/****************************************************************************************************************
	 * Memorizza i dati contenuti nel bean <code>AggiudicazioneBean</code> associati al codice fiscale dell'Utente. 
	 * se il bean contiene l'id viene eseguito un aggiornamento dei dati altrimenti si affettua un inserimento. 
	 * @param bean AggiudicazioneBean il bean da memorizzare 
	 * @param cfUtente String il codice fiscale dell'utente
	 * @throws ActionException
	 */
	public int save(AggiudicazioneBean bean,String cfUtente)throws ActionException{
		logger.debug("AggiudicazioneAction.save, bean da salvare: " + ObjectIntrospector.propertiesInfo(AggiudicazioneBean.class, bean));
		
		AggiudicazioniManager aggMan = new AggiudicazioniManager(connection,logger);
		try {
		
			if(bean.getIdAggiudicazione()<1){ 
				aggMan.insert(bean, cfUtente);
				return 1;
			}else 
				return aggMan.save(bean, cfUtente);

		}
		catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	
	}			
	
	
	/*******************************************************************************************
	 * Conferma i dati contenuti nel bean AggidicazioniBean modificandone lo stato a "Confermato". 
	 * @param bean AggiudicazioneBean in ingresso che vogliamo confermare
	 * @param cfUtente String codice fiscale dell'utente 
	 * @throws ActionException
	 */
	public int confirm(AggiudicazioneBean bean,String cfUtente)throws ActionException{
		
		AggiudicazioniManager aggMan = new AggiudicazioniManager(connection,logger);
		try {
			return aggMan.confirm(bean, cfUtente);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
		
	}			
	
	/*********************************************************************************************************
	 * Preleva le informazioni di un'aggiudicazione restituendo un'<code>AggiudicazioneBean</code> 
	 * identificata tramite id Agiudicazione e la data di inizio dell'aggiudicazione
	 * @param idAggiudicazione long
	 * @param dataInizioAgg Timestamp
	 * @param ignoraStato TODO
	 * @return AggiudicazioneBean
	 * @throws ActionException
	 */
	public AggiudicazioneBean loadOne(long idAggiudicazione,Timestamp dataInizioAgg, boolean ignoraStato) throws ActionException {

		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);
		logger.debug(CLAZZ + ".load: " + idAggiudicazione + " -- " + dataInizioAgg );	
		
		try {
			return  man.getAggiudicazioni(idAggiudicazione, dataInizioAgg, ignoraStato);
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
		
	}

	/*********************************************************************************************************
    * Preleva le informazioni di un'aggiudicazione restituendo un'<code>AggiudicazioneBean</code> 
    * identificata tramite CUI e progcui
    * @param CUI String
    * @param solo se confemrata
    * @return AggiudicazioneBean
    * @throws ActionException
    */
   public AggiudicazioneBean getAggiudicazioneByProgAndCui(String CUI, boolean confermata) throws ActionException {

      AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);
      
      try {
         return  man.getAggiudicazioneByProgAndCui(CUI, confermata);
      } catch (SQLException e) {
//       log come fatal demandato al chiamante
         logger.error(e);
         throw new ActionException(e);
      }
      
   }
   
	/**
	 * Metodo per la storicizzazioe di un record di aggiudicazione
	 * 
	 * @param idpub String
	 * @param datainiziopub Timestamp
	 * @param blocco String
	 * @return Timestamp
	 * @throws ActionException se si verificano errori nell'operazione
	 */
	public Timestamp copyRecord(String idpub,Timestamp datainiziopub,String blocco)throws ActionException {
		
			AggiudicazioniManager man = new AggiudicazioniManager(connection,logger);
			try {
				return man.copyRecord(idpub, datainiziopub,blocco);
			} catch (SQLException e) {
				throw new ActionException(e);
				
			}
	}
	
	/******************************************************************************************************
	 * Gestisce la richiesta di annullamento storicizzando i dati dell'aggiudicazione 
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public Timestamp richiediAnnullamento(RichiestaAnnullamento bean) throws ActionException {
		String idrecord = bean.getId_record();
		Timestamp datainiziorecord = bean.getData_inizio_record();
		String idLotto = bean.getId_lotto();
		String blocco = bean.getBlocco();
		String cfUtente = bean.getRichiedente();
		try{
			Timestamp nuovadatainizio = copyRecord(idrecord,datainiziorecord,blocco);
			if(nuovadatainizio!=null){
				logger.debug("Data nuova: "+nuovadatainizio);
				if(super.scriviAnnullamento(bean)){
					
					List<Object> attributiChiave = new ArrayList<Object>();
					attributiChiave.add(idrecord);
					attributiChiave.add(datainiziorecord);
					LogBloccoDatiManager.loggingCANCELREQ(connection, logger, cfUtente, blocco, attributiChiave);
				}
			}
			return nuovadatainizio;
		}
		catch(Exception e){
			throw new ActionException(e);
		}
	}
	
	/******************************************************************************************************
	 * Gestisce la richiesta di cancellazione storicizzando i dati dell'aggiudicazione 
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public void richiediCancellazione(RichiestaAnnullamento bean) throws ActionException {
		
		String idrecord = bean.getId_record();
		Timestamp datainiziorecord = bean.getData_inizio_record();

		try{
			
			if(super.scriviAnnullamento(bean)){
				
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idrecord);
				attributiChiave.add(datainiziorecord);
				LogBloccoDatiManager.loggingDELETEREQ(connection, logger, bean.getRichiedente(), bean.getBlocco(), attributiChiave);
			}

	}
		catch(Exception e){
			throw new ActionException(e);
		}
	}
	
	/*****************************************************************************************************************
	 * Determina se una determinata aggiudicazione identificata da 
	 * idAggiudicazione e dataInizioAggiudicazione sia presente 
	 * (<code>True</code>) o meno (<code>False</code>) nel db. 
	 * 
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @return boolean
	 * @throws ActionException
	 */
	public boolean existAggiudicazione(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException{
		AggiudicazioniManager man = new AggiudicazioniManager(connection,logger);
		try{
			return man.existAggiudicazione(idAggiudicazione, dataInizioAggiudicazione);
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}

	/**
    * Gestione aggancio CUP di una aggiudicazione
    * 
    * @param row
    * @param idAggiudicazione
    * @param dataInizioAggiudicazione
    * @throws SQLException
    */
	@Deprecated
   public void updateRecordCup(boolean aggancia, Scheda_A bean )  {
       CupLottoAggManager claMan = new CupLottoAggManager(connection, logger);
       boolean okDataAttivazioneCupLotto = SimogProperties.getInstance().isCUPLotto(bean.getInfoGara().getDataCreazioneGara());
       InfoComuniBean icBean = getInfoComuni(bean.getInfoComuni().getIdInfo(), bean.getInfoComuni().getDataInizioInfo());
       try {
         if( okDataAttivazioneCupLotto ){
             // competenza lotto
             
             if(aggancia)
                claMan.completaDatiAggCup(icBean.getIdLotto(), bean.getAggiudicazione().getIdAggiudicazione(),  bean.getAggiudicazione().getDataInizioAggiudicazione());
             else
                claMan.completaDatiAggCup(icBean.getIdLotto(), null, null);
          }   
          else{
             // competenza aggiudicazione 
             if(!aggancia)
                claMan.deleteCup(bean.getAggiudicazione().getIdAggiudicazione(),  bean.getAggiudicazione().getDataInizioAggiudicazione(), false);
             else
                claMan.completaDatiAggCup(icBean.getIdLotto(), bean.getAggiudicazione().getIdAggiudicazione(),  bean.getAggiudicazione().getDataInizioAggiudicazione());

          }
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }


}
