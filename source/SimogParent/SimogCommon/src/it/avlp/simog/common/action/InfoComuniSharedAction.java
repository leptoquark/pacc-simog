package it.avlp.simog.common.action;

import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.factory.AnnullamentoFactory;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class InfoComuniSharedAction extends BaseSharedAction {

	public InfoComuniSharedAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
	
	}
	
   /******************************************************************************************
    * Effettua la presa in carico, crea una richiesta di annullamento con le 
    * informazioni relative a :
    * <ul>
    * <li>Blocco
    * <li>Id Info
    * <li>Data inizio info comuni
    * <li>Esito richiesta annullamento = richiesta Accettata
    * </ul>
    * 
    * @param icb InfoComuniBean
    * @param cfUtente String
    * @throws ActionException
    */
   public void presaInCarico(InfoComuniBean icb, String cfUtente) throws ActionException{
      InfoComuniManager icm = new InfoComuniManager(connection,logger);
      PubblicazioneManager pm = new PubblicazioneManager(connection,logger);
      
      BaseRichiestaAnnullamento iaa = AnnullamentoFactory.getAction(IdentificativoSchede.TAB_INFO_COMUNI, connection, logger);
      try{
         // MOD: SIMOG-32 UN 15/04/09 Copia della pubblicazione da legare alla nuova scheda DatiComuni "presa in carico"  
         Timestamp newDataInizioPub = pm.presaInCarico(icb.getPubblicazione().getIdPubblicazione(), icb.getPubblicazione().getDataInizioPubblicazione());
         Timestamp nuovaData = icm.presaInCarico(icb.getIdInfo(), icb.getDataInizioInfo(), icb.getPubblicazione().getDataInizioPubblicazione(), newDataInizioPub);
         //*********rimuovo il record di richiesta annullamento
         RichiestaAnnullamento rAnn = new RichiestaAnnullamento();
         rAnn.setBlocco(IdentificativoSchede.TAB_INFO_COMUNI);
         rAnn.setId_record(String.valueOf(icb.getIdInfo()));
         rAnn.setData_inizio_record(icb.getDataInizioInfo());
         rAnn.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
         rAnn.setDecisore(cfUtente);
         iaa.gestisciPresaInCarico(rAnn, cfUtente);
         /**presa in carica**/
         icb.setDataInizioInfo(nuovaData);
         icm.updateRUP(icb, cfUtente);
         
      }catch (Exception e) {
//       log come fatal demandato al chiamante
         logger.error(e);
         throw new ActionException(e);
      }
   }

   /*******************************************************************************************************
    * Ottiene l'Aggiudicazione dell'accordo quadro cui l'adesione si riferisce. 
    * @param String cig_acc_quadro
    * @return Aggiudicazione
    * @throws ActionException
    */
   public AggiudicazioneBean getBeanAggiudicazioneAccQuadro(String cig_acc_quadro) throws ActionException {
      List <AggiudicazioneBean> aggiudicazioni = new ArrayList<AggiudicazioneBean>();
      AggiudicazioneBean aggiudicazioneAccQuadro = new AggiudicazioneBean();
      AggiudicazioniManager am = new AggiudicazioniManager(connection,logger);
      InfoComuniBean icb = new InfoComuniBean();
      InfoComuniManager icm = new InfoComuniManager(connection,logger);
      try{
         icb = icm.getInfoComuniByCig(cig_acc_quadro);
      }
      catch (Exception e){
         logger.error("Impossibile ottenere l'InfoComuniBean dell'accordo quadro", e);
      }
      
      try{
         aggiudicazioni = am.getAggiudicazioniList(icb.getIdInfo(), icb.getDataInizioInfo());
      }
      catch (Exception e){
         logger.error("Impossibile ottenere la lista di aggiudicazioni dell'accordo quadro", e);
      }
      for(AggiudicazioneBean agg : aggiudicazioni){
         if(agg.getIdStato()==StatiScheda.CONFERMATO){
            try{
               aggiudicazioneAccQuadro = am.getAggiudicazioni(agg.getIdAggiudicazione(), agg.getDataInizioAggiudicazione(), false);
//               break;
                 //aggiudicazioneAccQuadro = aggiudicazioni.get(0);
            }
            catch (Exception e){
               logger.error("Impossibile ottenere l'aggiudicazione dell'accordo quadro", e);
            }
         }
      }
      return aggiudicazioneAccQuadro;
   }

   /*******************************************************************************************************
    * Ottiene la lista con i dati degli Aggiudicatari dell'accordo quadro cui l'adesione si riferisce. 
    * @param AggiudicazioneBean
    * @return List<Aggiudicatario>
    * @throws ActionException
    */
   public List<AggiudicatarioBean> getBeanAggiudicatariAccQuadro(AggiudicazioneBean aggiudicazioneAccQuadro) throws ActionException {
      logger.debug("SCHEDA_ADESIONE_ACTION.getBeanAggiudicazioneAccQuadro: START");
      
      List<AggiudicatarioBean> aggiudicatariAccQuadro = new ArrayList<AggiudicatarioBean>();
      AggiudicatarioManager am = new AggiudicatarioManager(connection,logger);
      
      try{
         aggiudicatariAccQuadro = am.loadMany(aggiudicazioneAccQuadro.getIdAggiudicazione(), aggiudicazioneAccQuadro.getDataInizioAggiudicazione(), false);
      }
      catch (Exception e){
         logger.error("Impossibile ottenere gli aggiudicatari dell'accordo quadro", e);
      }     
      return aggiudicatariAccQuadro;
   }
}
