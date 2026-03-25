package it.avlp.simog.actions.aggiudicazione;

import java.sql.Connection;

import org.apache.log4j.Logger;

import it.avlp.simog.actions.BaseAction;

@Deprecated
public class PresaInCaricoAction extends BaseAction {

	public PresaInCaricoAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		
	}

	
	/***********************************************************************************************
	 * Verifica la presenza di richieste di annullamento per la presa incarico identificata 
	 * dai dati in ingresso
	 *  
	 * @param idInfo long
	 * @param dataInizioInfo Timestamp
	 * @return boolean
	 * @throws ActionException
	 */
//	public boolean hasRichiesteAnnullamento(long idInfo, Timestamp dataInizioInfo)throws ActionException{
//		boolean result = false;
//		PresaInCaricoManager pim = new PresaInCaricoManager(connection, logger);
//		try{
//			result = result || pim.hasRichAnnInfoComuni(idInfo);
//			if(!result){
//				List<Long> aggL = pim.getDatiAggiudicazione(idInfo, dataInizioInfo);
//				for(Long idAggiudicazione: aggL){
//					result = result || pim.hasRichAnnAggiudicazioni(idAggiudicazione);
//				}
//			}
//			
//			return result;
//		}catch (Exception e) {
////			log come fatal demandato al chiamante
//			logger.error(e);
//			throw new ActionException(e);
//		}
//	}
//	
//	public void updateState(Set<String> schedeDaAggiornare, InfoGaraBean igb){
//		
//	}
//
//
//	public Timestamp richiediAnnullamento(
//			RichiestaAnnullamento richiestaAnnullamentoBean) {
//		
//		return null;
//	}

}
