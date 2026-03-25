package it.avlp.simog.common.action;

import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.PageHelper;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

public class AggiudicatarioAction extends BaseSharedAction {
	public static String CLAZZ = "AggiudicatarioAction";
	public AggiudicatarioAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
	}
	
	/*******************************************************************************************************
	 * Imposta i dati dell'aggiudicazione :
	 * <ul>
	 * <li>Id Aggiudicazione</li>
	 * <li>Data Inizio Aggiudicazione</li>
	 * </ul>
	 * in tutta la lista di AggiudicatarioBean
	 * @param bean  List&lt;AggiudicatarioBean&gt;
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @throws ActionException
	 */
	public void setDatiAggiudicazione(List<AggiudicatarioBean> bean, long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException{
		for (AggiudicatarioBean aggiudicatarioBean : bean) {
			aggiudicatarioBean.setIdAggiudicazione(idAggiudicazione);
			aggiudicatarioBean.setDataInizioAggiudicazione(dataInizioAggiudicazione);	
		}
	}
	
	
	/*************************************************************************************************************************
	 * metodo per il recupero degli aggiudicatari associati all'aggiudicazione di cui id
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @param ignoraStato TODO
	 * @return List&lt;AggiudicatarioBean&gt;
	 * @throws ActionException
	 */
	public List<AggiudicatarioBean> loadMany(long idAggiudicazione, Timestamp dataInizioAggiudicazione, boolean ignoraStato) throws ActionException {
		
		AggiudicatarioManager man = new AggiudicatarioManager(connection, logger);
		
		List<AggiudicatarioBean> ris = null;
		try {
			ris = man.loadMany(idAggiudicazione, dataInizioAggiudicazione, ignoraStato);
			//gm aggiunto per ditte ausiliarie
			DittaAusiliariaAction dittaAusiliariaAction = new DittaAusiliariaAction(connection,logger);
			for(AggiudicatarioBean agg : ris){
				try{
    				String ditteAusiliarieString = dittaAusiliariaAction.creaDitteAusiliarieString(agg.getIdAggiudicatario(),agg.getDataInizioAggiudicatario(), ignoraStato);
    				//gm codice per la retrocompatibilità della ditta ausiliaria con il cf_ausiliaria di aggiudicatario
    				ditteAusiliarieString = dittaAusiliariaAction.getDitteAusiliarieStringRetrocompatibile(ditteAusiliarieString, agg.getCfAusiliaria(), agg.getFlagAvvalimento());
    				agg.setDitteAusiliarieString(ditteAusiliarieString);
    				if(agg.getDitteAusiliarieString()!=null && !"".equals(agg.getDitteAusiliarieString())){  
    					List <DittaAusiliariaBean> ditteAusiliarie = new DittaAusiliariaAction(connection,logger).creaListaDitteAusiliarie(agg.getDitteAusiliarieString(),idAggiudicazione,dataInizioAggiudicazione);
    				    if(!ditteAusiliarie.isEmpty()){
    					    agg.setDitteAusiliarie(ditteAusiliarie);
    				    }
    				}
				}
				catch (ActionException e) {
					throw new ActionException(e);
				}
			}
		} 
		catch (SQLException e) {
			throw new ActionException(e);
		}
		
		return ris;
	}
	
	/*************************************************************************************************************************
	 * metodo per il recupero degli aggiudicatari associati all'aggiudicazione di cui id
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @param ignoraStato TODO
	 * @return List&lt;AggiudicatarioBean&gt;
	 * @throws ActionException
	 */
	public List<AggiudicatarioBean> loadManyById(long idAggiudicazione, Timestamp dataInizioAggiudicazione, boolean ignoraStato) throws ActionException {
		
		AggiudicatarioManager man = new AggiudicatarioManager(connection, logger);
		
		List<AggiudicatarioBean> ris = null;
		try {
			ris = man.loadMany(idAggiudicazione,dataInizioAggiudicazione,false);
			//gm aggiunto per ditte ausiliarie
			DittaAusiliariaAction dittaAusiliariaAction = new DittaAusiliariaAction(connection,logger);
			for(AggiudicatarioBean agg : ris){
				try{
    				String ditteAusiliarieString = dittaAusiliariaAction.creaDitteAusiliarieString(agg.getIdAggiudicatario(),agg.getDataInizioAggiudicatario(), ignoraStato);
    				//gm codice per la retrocompatibilità della ditta ausiliaria con il cf_ausiliaria di aggiudicatario
    				ditteAusiliarieString = dittaAusiliariaAction.getDitteAusiliarieStringRetrocompatibile(ditteAusiliarieString, agg.getCfAusiliaria(), agg.getFlagAvvalimento());
    				agg.setDitteAusiliarieString(ditteAusiliarieString);
    				if(agg.getDitteAusiliarieString()!=null && !"".equals(agg.getDitteAusiliarieString())){  
    					List <DittaAusiliariaBean> ditteAusiliarie = new DittaAusiliariaAction(connection,logger).creaListaDitteAusiliarie(agg.getDitteAusiliarieString(),idAggiudicazione,dataInizioAggiudicazione);
    				    if(!ditteAusiliarie.isEmpty()){
    					    agg.setDitteAusiliarie(ditteAusiliarie);
    				    }
    				}
				}
				catch (ActionException e) {
					throw new ActionException(e);
				}
			}
		} 
		catch (SQLException e) {
			throw new ActionException(e);
		}
		
		return ris;
	}
	
	/*************************************************************************************************************************
	 * metodo per il recupero degli aggiudicatari associati all'aggiudicazione di cui id
	 * @param timestamp 
	 * @param l 
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @param ignoraStato TODO
	 * @return List&lt;AggiudicatarioBean&gt;
	 * @throws ActionException
	 */
	public List<AggiudicatarioBean> loadManyByIdInfo(long idAggiudicazione, Timestamp dataInizioAggiudicazione, long idInfo, Timestamp dataInizioInfo,  boolean ignoraStato) throws ActionException {
		
		AggiudicatarioManager man = new AggiudicatarioManager(connection, logger);
		
		List<AggiudicatarioBean> ris = null;
		try {
			ris = man.loadManyIdInfo(idInfo,dataInizioInfo,false);
			//gm aggiunto per ditte ausiliarie
			DittaAusiliariaAction dittaAusiliariaAction = new DittaAusiliariaAction(connection,logger);
			for(AggiudicatarioBean agg : ris){
				try{
    				String ditteAusiliarieString = dittaAusiliariaAction.creaDitteAusiliarieString(agg.getIdAggiudicatario(),agg.getDataInizioAggiudicatario(), ignoraStato);
    				//gm codice per la retrocompatibilità della ditta ausiliaria con il cf_ausiliaria di aggiudicatario
    				ditteAusiliarieString = dittaAusiliariaAction.getDitteAusiliarieStringRetrocompatibile(ditteAusiliarieString, agg.getCfAusiliaria(), agg.getFlagAvvalimento());
    				agg.setDitteAusiliarieString(ditteAusiliarieString);
    				if(agg.getDitteAusiliarieString()!=null && !"".equals(agg.getDitteAusiliarieString())){  
    					List <DittaAusiliariaBean> ditteAusiliarie = new DittaAusiliariaAction(connection,logger).creaListaDitteAusiliarie(agg.getDitteAusiliarieString(),idAggiudicazione,dataInizioAggiudicazione);
    				    if(!ditteAusiliarie.isEmpty()){
    					    agg.setDitteAusiliarie(ditteAusiliarie);
    				    }
    				}
				}
				catch (ActionException e) {
					throw new ActionException(e);
				}
			}
		} 
		catch (SQLException e) {
			throw new ActionException(e);
		}
		
		return ris;
	}
	
	
	/***************************************************************************************************************
	 * Viene effettualta la memorizzazione degli aggiudicatari associati ad una aggiudicazione identificata tramite
	 * idAggiudicazione e dataInizioAggiudicazione. 
	 * 
	 * @param aggiudicatari Lista di AggiudicatariBean 
	 * @param idAggiudicazione long contenente l'Id dell'Aggiudicazione
	 * @param dataInizioAggiudicazione Timestamp per la data di inizio aggiudicazione. 
	 * @throws ActionException
	 */
	
	public void save(List<AggiudicatarioBean> aggiudicatari, long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException {
		String mtd = "add";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		AggiudicatarioManager man = new AggiudicatarioManager(connection, logger);
		try {
			/*gm aggiunto per ditte ausiliarie, prima di cancellare gli aggiudicatari
			**devo cancellare le sue ditte ausiliarie per evitare conflitti nelle FK*/
			DittaAusiliariaAction dittaAusiliariaAction = new DittaAusiliariaAction(connection, logger);
	        dittaAusiliariaAction.deleteListByAggiudicazione(idAggiudicazione,dataInizioAggiudicazione);
			
			man.deleteAggiudicatari(idAggiudicazione, dataInizioAggiudicazione);
			for(AggiudicatarioBean bean: aggiudicatari){
				bean.setIdAggiudicazione(idAggiudicazione);
				bean.setDataInizioAggiudicazione(dataInizioAggiudicazione);

				man.save(bean);	
				
				//gm aggiunto per ditte ausiliarie, dopo aver effettuato il save di ogni bean 
				//salvo anche le sue ditte ausiliarie
				if(bean.getDitteAusiliarie()!=null && !bean.getDitteAusiliarie().isEmpty()){
				    dittaAusiliariaAction.saveList(bean);
				}
			}
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
	}
	
	
	/************************************************************************************************************
	 * Il metodo si ocupa di settare a confermato lo stato degli aggiudicatari 
	 * presenti nella lista in ingresso associati alla specifica Aggiudicazione 
	 * identificata tramite idAggiudicazione e dataInizioAggiudicazione. 
	 * 
	 * @param aggiudicatari List&lt;AggiudicatarioBean&gt;, lista contenente gli aggiudicatari da confermare 
	 * @param idAggiudicazione long per l'id dell'aggiudicazione
	 * @param dataInizioAggiudicazione Timestamp per indicare la data di inizio dell'aggiudicazione
	 * @throws ActionException
	 */
	public void confirm(List<AggiudicatarioBean> aggiudicatari, long idAggiudicazione, Timestamp dataInizioAggiudicazione)  throws ActionException  {
		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		AggiudicatarioManager man = new AggiudicatarioManager(connection, logger);
		
		try {
			/*gm aggiunto per ditte ausiliarie, prima di cancellare gli aggiudicatari
			**devo cancellare le sue ditte ausiliarie per evitare conflitti nelle FK*/
			DittaAusiliariaAction dittaAusiliariaAction = new DittaAusiliariaAction(connection, logger);
	        dittaAusiliariaAction.deleteListByAggiudicazione(idAggiudicazione,dataInizioAggiudicazione);
					
			man.deleteAggiudicatari(idAggiudicazione, dataInizioAggiudicazione);
			for(AggiudicatarioBean bean: aggiudicatari){
				bean.setIdAggiudicazione(idAggiudicazione);
				bean.setDataInizioAggiudicazione(dataInizioAggiudicazione);
				man.confirm(bean);	
				
				//gm aggiunto per ditte ausiliarie, dopo aver effettuato il confirm di ogni bean 
				//salvo anche le sue ditte ausiliariee
				if(bean.getDitteAusiliarie()!=null && !bean.getDitteAusiliarie().isEmpty()){
				    dittaAusiliariaAction.confirmList(bean);
				}
			}
			
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(logPrefix, e);
			throw new ActionException(e);
		}
	}
	
	/*************************************************************************************************************
	 * Gestisce la richiesta di annullamento effettuando la storicizzazione del bean. 
	 * 
	 * @param bean
	 * @param datavecchia
	 * @return boolean
	 * @throws ActionException
	 */
	public boolean richiediAnnullamento(RichiestaAnnullamento bean,Timestamp datavecchia) throws ActionException {
		AggiudicatarioManager aggMan = new AggiudicatarioManager(connection, logger);
		
		try {
		    boolean risultato = aggMan.copyRecord(bean.getId_record(), bean.getData_inizio_record(), datavecchia);
		    //gm aggiunto per ditte ausiliarie, carico gli aggiudicatari modificati e per ciascuno di essi
		    //effettuo il copyRecord delle sue ditte ausiliarie
		    List<AggiudicatarioBean> aggModificati = loadMany(Long.parseLong(bean.getId_record()),bean.getData_inizio_record(), false);
		    if(!aggModificati.isEmpty()){
		    	for(AggiudicatarioBean agg: aggModificati){
		    		String idbean = bean.getId_record();
		    		String databean = PageHelper.formatTimeStamp(bean.getData_inizio_record());
		    		long idagg = agg.getIdAggiudicazione();
		    		String dataagg = PageHelper.formatTimeStamp(agg.getDataInizioAggiudicazione());
		    		long idagg1 = agg.getIdAggiudicatario();
		    		String dataagg1 = PageHelper.formatTimeStamp(agg.getDataInizioAggiudicatario());
				    DittaAusiliariaAction dAction = new DittaAusiliariaAction(connection, logger);
				    boolean risultatoAnnDitteAux = dAction.richiediAnnullamento(bean, agg, datavecchia);
		    	}
		    }
		    return risultato;
		} 
		catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
		
	}
	
	/*****************************************************************************************
	 * Effettua una ricerca sugli aggiudicatari per denominazione, codice fiscale e cognome
	 * @param request
	 * @return TableBean
	 * @throws ActionException
	 */
//	public TableBean cerca(HttpServletRequest request)  throws ActionException  {
//
//		RubricaManager rubMan = new RubricaManager(connection, logger);
//		Rubrica rub = new Rubrica();
//		rub = setFiltriRicerca(request);
//		
//		logger.debug("rub.getDenominazione():"+rub.getDenominazione()+"<--");
//		logger.debug("rub.getCodice_fiscale():"+rub.getCodice_fiscale()+"<---");
//		logger.debug("rub.getCognome():"+rub.getCognome()+"<---");
//		
//		
//		TableBean result = null;
//		try {
//			//result = rubMan.getSoggettiPartecipantiRubTab(rub.getDenominazione(),rub.getCodice_fiscale());
//			
//			result = rubMan.getSoggettiPartecipantiRubTab(rub.getDenominazione(), rub.getCodice_fiscale());
//			
//			sendMessage(request, Messaggi.SIMOG_AGGIUDICAZIONI_050);
//		} catch (SQLException e) {
////			log come fatal demandato al chiamante
//			logger.error(e.getMessage());
//		//	e.printStackTrace();
//			throw new ActionException(e);
//		} catch (Exception e) {
////			log come fatal demandato al chiamante
//			logger.error(e.getMessage());
//			//e.printStackTrace();
//			throw new ActionException(e);
//		} 
//		return result;
//		
//	}
	
	/****************************************************************************************************
	 * Imposta i filtri della ricerca, restituisce una <code>Rubrica</code> nella quale vengono impostate 
	 * cognome, nome e codice fiscale recuperati dalla request.   
	 * @param request
	 * @return Rubrica
	 */
//	public Rubrica setFiltriRicerca(HttpServletRequest request){
//		Rubrica agg = new Rubrica();
//												
//		String COD_FISC = (request.getParameter(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE)!=null) ? request.getParameter(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE) : "";
//		//String DENOM = (request.getParameter(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE_RIC)!=null) ? request.getParameter(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE_RIC) : "";
//		
//		String cognome = (request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME)!=null) ? request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME) : "";
//		logger.debug("cognome - "+cognome);
//		
//		String nome = (request.getParameter(ParametriServletRubrica.FIELD_NAME_NOME)!=null) ? request.getParameter(ParametriServletRubrica.FIELD_NAME_NOME) : "";
//		logger.debug("nome - "+nome);
//		
//		
//		logger.debug("COD_FISC:"+COD_FISC+"<---");
//		
//		logger.debug("cognome:"+cognome+"<---");
//		logger.debug("nome:"+nome+"<---");
//		
//		agg.setCodice_fiscale(COD_FISC);
//		agg.setCognome(cognome);
//		agg.setNome(nome);
//		
//		return agg;
//	}
	
	/****************************************************************************************************
	 * Imposta i dati di conferma nell'<code>AggiudicatarioBean</code> inserendo i valori relativi a 
	 * <code>idAggiudicazione</code> e <code>dataInizioAggiudicazione</code>.  
	 * @param request HttpServletRequest
	 * @return AggiudicatarioBean
	 */
//	public AggiudicatarioBean setDatiConferma(HttpServletRequest request){
//		AggiudicatarioBean agg = new AggiudicatarioBean();
//		agg.setIdAggiudicazione(Long.parseLong(request.getParameter(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE)));
//		agg.setDataInizioAggiudicazione(PageHelper.parseTime(request.getParameter(PSBD.DATA_INIZIO_AGGIUDICAZIONE)));
//		return agg;
//	}
}
