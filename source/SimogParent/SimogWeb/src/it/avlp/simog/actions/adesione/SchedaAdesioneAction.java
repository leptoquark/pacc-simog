package it.avlp.simog.actions.adesione;

import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avlp.simog.actions.BaseAction;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.common.action.AggiudicatarioAction;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.exception.ActionException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class SchedaAdesioneAction extends BaseAction {
	
public static String CLAZZ = "SchedaAdesioneAction";
	
	public SchedaAdesioneAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
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
    				// Ticket#2015050810000322 PP prendo l'ultima che trovo   break;
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
	public List<AggiudicatarioBean> getBeanAggiudicatariAccQuadroByAcc(AggiudicazioneBean aggiudicazioneAccQuadro) throws ActionException {
		logger.debug("SCHEDA_ADESIONE_ACTION.getBeanAggiudicazioneAccQuadro: START");
		
		List<AggiudicatarioBean> aggiudicatariAccQuadro = new ArrayList<AggiudicatarioBean>();
		// PP va usata la action! AggiudicatarioManager am = new AggiudicatarioManager(connection,logger);
		AggiudicatarioAction aa = new AggiudicatarioAction(connection, logger);
		
		try{
		   //PP va usata la action!  aggiudicatariAccQuadro = am.loadMany(aggiudicazioneAccQuadro.getIdAggiudicazione(), aggiudicazioneAccQuadro.getDataInizioAggiudicazione(), false);
//		   aggiudicatariAccQuadro = aa.loadManyById(aggiudicazioneAccQuadro.getIdAggiudicazione(), aggiudicazioneAccQuadro.getDataInizioAggiudicazione(), false);
		   aggiudicatariAccQuadro = aa.loadManyByIdInfo(aggiudicazioneAccQuadro.getIdAggiudicazione(), aggiudicazioneAccQuadro.getDataInizioAggiudicazione(),aggiudicazioneAccQuadro.getIdInfo(), aggiudicazioneAccQuadro.getDataInizioInfo(), false);
		}
		catch (Exception e){
			logger.error("Impossibile ottenere gli aggiudicatari dell'accordo quadro", e);
		}		
		return aggiudicatariAccQuadro;
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
		// PP va usata la action! AggiudicatarioManager am = new AggiudicatarioManager(connection,logger);
		AggiudicatarioAction aa = new AggiudicatarioAction(connection, logger);
		
		try{
		   //PP va usata la action!  aggiudicatariAccQuadro = am.loadMany(aggiudicazioneAccQuadro.getIdAggiudicazione(), aggiudicazioneAccQuadro.getDataInizioAggiudicazione(), false);
		   aggiudicatariAccQuadro = aa.loadMany(aggiudicazioneAccQuadro.getIdAggiudicazione(), aggiudicazioneAccQuadro.getDataInizioAggiudicazione(), false);
		}
		catch (Exception e){
			logger.error("Impossibile ottenere gli aggiudicatari dell'accordo quadro", e);
		}		
		return aggiudicatariAccQuadro;
	}
	/*******************************************************************************************************
	 * Carica solo i dati necessari dell'aggiudicazione dell'accordo quadro cui l'adesione si riferisce. 
	 * @param AggiudicazioneBean
	 * @return AggiudicazioneBean
	 * @throws ActionException
	 */
	public AggiudicazioneBean getAggAccQuadroBase (AggiudicazioneBean agg){
		AggiudicazioneBean aggiudicazioneBase = new AggiudicazioneBean();
		aggiudicazioneBase.setPercRibassoAgg(agg.getPercRibassoAgg());
		aggiudicazioneBase.setPercOffAumento(agg.getPercOffAumento());
		aggiudicazioneBase.setImportoAggiudicazione(agg.getImportoAggiudicazione());
		aggiudicazioneBase.setDataVerbaleAggiudicazione(agg.getDataVerbaleAggiudicazione());
		
		//PP patch per gli esclusi e sottosoglia il campo non esiste
		if(agg.getFlagRichSubappalto() == null)
			aggiudicazioneBase.setFlagRichSubappalto(Costanti.FLAG_VALORE_NO);
		else
			aggiudicazioneBase.setFlagRichSubappalto(agg.getFlagRichSubappalto()); 
		
		return aggiudicazioneBase;
	}
}