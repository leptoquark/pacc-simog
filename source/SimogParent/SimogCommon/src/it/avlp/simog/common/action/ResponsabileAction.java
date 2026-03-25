package it.avlp.simog.common.action;

import it.avcp.simog.managers.aggiudicazione.ResponsabileManager;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author PP
 *
 */
public class ResponsabileAction extends BaseSharedAction {

	public static String CLAZZ = "ResponsabileAction";
	
	public ResponsabileAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
	}
	
	/******************************************************************************************************
	 * Imposta i dati dell'aggiudicazione 
	 * @param bean List&lt;ResponsabileBean&gt;
	 * @param idAggiudicazione long 
	 * @param dataInizioAggiudicazione Timestamp 
	 * @throws ActionException
	 */
	public void setDatiAggiudicazione(List<ResponsabileBean> bean, long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException{
		for (ResponsabileBean responsabileBean : bean) {
			responsabileBean.setIdScheda(idAggiudicazione);
			responsabileBean.setDataInizioScheda(dataInizioAggiudicazione);	
		}
	}
	
	/**
	 * metodo per il caricamento di tutti i responsabili associati all'id/datainizio
	 * (sia quelli relativi a soggetti responsabili che soggetti partecipanti)
	 * @param idAggiudicazione long
	 * @param dataInizioAgg Timestamp
	 * @param sezione String
	 * @param ignoraStato TODO
	 * @return List&lt;ResponsabileBean&gt;
	 * @throws ActionException
	 */
	public List<ResponsabileBean> loadManyAll(long idAggiudicazione,Timestamp dataInizioAgg, String sezione, boolean ignoraStato) throws ActionException {
		
		List<ResponsabileBean> result = null;
		result = loadMany(idAggiudicazione,dataInizioAgg,sezione, ignoraStato);
		result.addAll(loadManySoggPart(idAggiudicazione, dataInizioAgg, sezione, ignoraStato));
		return result;
	}

	
	/**
	 * metodo per il caricamento dei resposabili associati all'id/datainizio
	 * 
	 * @param idAggiudicazione long
	 * @param dataInizioAgg Timestamp
	 * @param sezione String
	 * @param ignoraStato TODO
	 * @return List&lt;ResponsabileBean&gt;
	 * @throws ActionException
	 */
	public List<ResponsabileBean> loadMany(long idAggiudicazione,Timestamp dataInizioAgg, String sezione, boolean ignoraStato) throws ActionException {

		String mtd = "load";
		String logPrefix = CLAZZ + "." + mtd + ": ";

		ResponsabileManager rm = new ResponsabileManager(connection, logger);
		
		List<ResponsabileBean> result = null;
		try {
			result =  rm.loadMany(idAggiudicazione, dataInizioAgg, sezione, ignoraStato);
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
		return result;
	}
	
	/**
	 * metodo per il caricamento dei resposabili associati all'id/datainizio
	 * (relativi a soggetti partecipanti)
	 * @param idAggiudicazione long
	 * @param dataInizioAgg Timestamp
	 * @param sezione String
	 * @param ignoraStato TODO
	 * @return List&lt;ResponsabileBean&gt;
	 * @throws ActionException
	 */
	public List<ResponsabileBean> loadManySoggPart(long idAggiudicazione,Timestamp dataInizioAgg, String sezione, boolean ignoraStato) throws ActionException {

		String mtd = "load";
		String logPrefix = CLAZZ + "." + mtd + ": ";

		ResponsabileManager rm = new ResponsabileManager(connection, logger);
		
		List<ResponsabileBean> result = null;
		try {
			result =  rm.loadManySoggPart(idAggiudicazione, dataInizioAgg, sezione, ignoraStato);
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
		return result;
	}
	 
	
	
	/**
	 * Gestisce il salvataggio inserendo nel DB i dati impostati dall'utente
	 * @param responsabili List&lt;ResponsabiliBean&gt;
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws ActionException
	 */
	public void save(List<ResponsabileBean> responsabili,long idAggiudicazione,Timestamp dataInizioAggiudicazione) throws ActionException {

		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		ResponsabileManager man = new ResponsabileManager(connection, logger);	
	
		try {
			man.deleteResponsabili(idAggiudicazione, dataInizioAggiudicazione);
			for(ResponsabileBean bean: responsabili){
				bean.setIdScheda(idAggiudicazione);
				bean.setDataInizioScheda(dataInizioAggiudicazione);
				man.save(bean);	
			}
			
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		} 
	}
	
	
	/*******************************************************************************************************
	 *  Effettua la conferma della lista dei responsabili associandovi idAggiudicazione e dataInizioAggiudicazione. 
	 *  Tali dati vengono memorizzati su db dove stato viene impostato a confermato. 
	 * @param responsabili List&lt;ResponsabileBean&gt;
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws ActionException
	 */
	public void confirm(List<ResponsabileBean> responsabili,long idAggiudicazione,Timestamp dataInizioAggiudicazione)  throws ActionException  {

		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		ResponsabileManager man = new ResponsabileManager(connection, logger);
		
		try{
			man.deleteResponsabili(idAggiudicazione, dataInizioAggiudicazione);
			for(ResponsabileBean bean: responsabili){
				bean.setIdScheda(idAggiudicazione);
				bean.setDataInizioScheda(dataInizioAggiudicazione);
				man.confirm(bean);	
			}

		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		} 
	}
	
	
	/*******************************************************************************************************
	 * <b>richiediAnnullamento</b><br>
	 * @param bean RichiestaAnnullamento
	 * @param datavecchia Timestamp
	 * @return boolean
	 * @throws ActionException
	 */
	public boolean richiediAnnullamento(RichiestaAnnullamento bean,Timestamp datavecchia) throws ActionException {
		ResponsabileManager respMan = new ResponsabileManager(connection, logger);
		
		try {
		
			return respMan.copyRecord(bean.getId_record(), bean.getData_inizio_record(), datavecchia);
		
		} catch(Exception ex){
//			log come fatal demandato al chiamante
			logger.error(ex);
			throw new ActionException(ex);
		}
		
	}
	
	public List<ResponsabileBean> implodiAggiudicatari(List<ResponsabileBean> responsabili)throws ActionException  {
		List<ResponsabileBean> result = new ArrayList<ResponsabileBean>();
		Map <Long,List<ResponsabileBean>> mappaResponsabiliPerGruppo = new HashMap<Long,List<ResponsabileBean>>();
		List<ResponsabileBean> listaResponsabiliPerGruppo;
		List <Long> listIdGruppo = new ArrayList<Long>();
		for(ResponsabileBean agg : responsabili){
			if(!listIdGruppo.contains(Long.valueOf(agg.getIdGruppo())))
				listIdGruppo.add(Long.valueOf(agg.getIdGruppo()));
			//creo la mappa degli aggiudicatari organizzati per idGruppo
			listaResponsabiliPerGruppo = mappaResponsabiliPerGruppo.get(Long.valueOf(agg.getIdGruppo()));
			if(listaResponsabiliPerGruppo==null)
				listaResponsabiliPerGruppo = new ArrayList<ResponsabileBean>();
			listaResponsabiliPerGruppo.add(agg);	
		   	mappaResponsabiliPerGruppo.put(Long.valueOf(agg.getIdGruppo()), listaResponsabiliPerGruppo);		
		}
		
		for(Long idGruppo : listIdGruppo){
			List<ResponsabileBean> listaAggiudicatariDaRaggruppare = mappaResponsabiliPerGruppo.get(idGruppo);
			//se non ho idGruppo gli Aggiudicatari sono singoli e devono esseree aggiunti tutti al risultato
			if(idGruppo == 0)
				result.addAll(listaAggiudicatariDaRaggruppare);
			else{
				//Collections.sort(listaAggiudicatariDaRaggruppare,new ComparaAggiudicatarioPerIdAggiudicatario());
				//per l'ordinamento scelto, l'ultimo della lista è il capogruppo
				ResponsabileBean aggCapoGruppo = new ResponsabileBean();
				for(ResponsabileBean elem : listaAggiudicatariDaRaggruppare) {
					if(!elem.isMandante()) {
						aggCapoGruppo = elem;
						break;
					}
				}
				
				listaAggiudicatariDaRaggruppare.remove(aggCapoGruppo);
				String ditteRaggruppamentoString = "";
				ditteRaggruppamentoString = creaDitteRaggruppamentoString(listaAggiudicatariDaRaggruppare);
				aggCapoGruppo.setDitteRaggruppamentoString(ditteRaggruppamentoString);
				result.add(aggCapoGruppo);
			}
		}
		return result;
	}
	
	public String creaDitteRaggruppamentoString(List<ResponsabileBean> responsabili)throws ActionException  {
		String ditteRaggruppamentoString = "";
        
		if(responsabili!=null && !responsabili.isEmpty()){
			String record = "";
			for(ResponsabileBean dittaBean : responsabili){
				if(dittaBean.getSoggettoPartecipante()!=null) {
					record = record + 	
					dittaBean.getSoggettoPartecipante().getDenominazione() + "|" +
					dittaBean.getSoggettoPartecipante().getCodiceFiscale() + "|" +
					dittaBean.getSoggettoPartecipante().getId_stato() + "|" +
					String.valueOf(dittaBean.getSoggettoPartecipante().getIdSoggettoPartecipante()) + "|" +
					PageHelper.formatTimeStamp(dittaBean.getSoggettoPartecipante().getDataInizioSogg()) + "|" + 
					dittaBean.getSoggettoPartecipante().getDatiModifica() + "|";
					ditteRaggruppamentoString = ditteRaggruppamentoString + record + "~";
					record = "";
			   } else if(dittaBean.getSoggettoResponsabile()!=null) {
				   record = record + 	
					dittaBean.getSoggettoResponsabile().getCognome() + "|" +
					dittaBean.getSoggettoResponsabile().getCodiceFiscaleResponsabile() + "|" +
					"" + "|" +
					String.valueOf(dittaBean.getSoggettoResponsabile().getIdResponsabile()) + "|" +
					PageHelper.formatTimeStamp(dittaBean.getSoggettoResponsabile().getDataInizioRes()) + "|" + 
					dittaBean.getSoggettoResponsabile().getDatiModifica() + "|";
					ditteRaggruppamentoString = ditteRaggruppamentoString + record + "~";
					record = "";
			      }
			   }
		}	
        return ditteRaggruppamentoString;
	}
	
}
