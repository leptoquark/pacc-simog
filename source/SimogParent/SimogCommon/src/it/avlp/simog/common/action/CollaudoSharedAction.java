package it.avlp.simog.common.action;

import it.avcp.simog.managers.accordo.AccordoManager;
import it.avcp.simog.managers.collaudo.CollaudoManager;
import it.avcp.simog.managers.collaudo.ResponsabileCollManager;
import it.avcp.simog.managers.conclusione.ConclusioniManager;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.RubricaResponsabili;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.comparators.SoggettiResponsabiliComparator;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.factory.AnnullamentoFactory;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.rubricamanager.RubricaManager;
import it.avlp.simog.rubricamanager.RubricaResponsabiliManager;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class CollaudoSharedAction extends BaseSharedAction {

	public CollaudoSharedAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		
	}
	
	public static String CLAZZ = "CollaudoSharedAction";
	
	
	/******************************************************************************************************
	 * Gestisce il salvataggio delle informazioni di collaudo
	 * @param bean CollaudoiBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int save(CollaudoBean bean , String cfUtente)throws ActionException{
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(CollaudoBean.class, bean));
		CollaudoManager cManager = new CollaudoManager(connection,logger);
		try{
			if(bean.getIdCollaudo() < 1){
				cManager.insert(bean, cfUtente);
				return 1;
			}else{
				if(!cManager.existCollaudo(bean.getIdCollaudo(), bean.getDataIniColl()))throw new ActionException("Scheda inesistente");
				return cManager.save(bean, cfUtente);
			}
		}catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/*******************************************************************************************
	 * Gestisce la conferma 
	 * @param bean CollaudoBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int confirm(CollaudoBean bean , String cfUtente)throws ActionException{
		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(CollaudoBean.class, bean));
		CollaudoManager cManager = new CollaudoManager(connection,logger);
		try{
			if(!cManager.existCollaudo(bean.getIdCollaudo(), bean.getDataIniColl())) throw new ActionException("Scheda inesistente");
			return cManager.confirm(bean, cfUtente);
		}catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/*****************************************************************************************************
	 * Gestisce la richiesta di annullamento restituendo la nuova data di inizio
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp 
	 * @throws ActionException
	 */
	private Timestamp richiestaAnnullamento(RichiestaAnnullamento bean)throws ActionException{
		String id = bean.getId_record();
		Timestamp data = bean.getData_inizio_record();
		String idLotto = bean.getId_lotto();
		String blocco = bean.getBlocco();
		String cfUtente = bean.getRichiedente();
		try{
			Timestamp nuovaDataInizio = (new CollaudoManager(connection,logger).copyRecord(Long.parseLong(id), data));
			if(nuovaDataInizio != null){
				logger.debug("Data nuova:" + nuovaDataInizio);
				if (super.scriviAnnullamento(bean)){
					List<Object> attributiChiave = new ArrayList<Object>();
					attributiChiave.add(id);
					attributiChiave.add(data);
					LogBloccoDatiManager.loggingCANCELREQ(connection, logger, cfUtente, blocco, attributiChiave);
					return nuovaDataInizio;
				}
			}
			return null;
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
	}
	
	/*****************************************************************************************************
	 * Gestisce la richiesta di cancellazione 
	 * @param bean RichiestaAnnullamento
	 * @return void 
	 * @throws ActionException
	 */
	public void richiestaCancellazione(RichiestaAnnullamento bean)throws ActionException{
	
		String id = bean.getId_record();
		Timestamp data = bean.getData_inizio_record();

		try{
			
			if (super.scriviAnnullamento(bean)){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(id);
				attributiChiave.add(data);
				LogBloccoDatiManager.loggingDELETEREQ(connection, logger, bean.getRichiedente(), bean.getBlocco(), attributiChiave);
			}
			
		}
		
		catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
	}

	/****************************************************************************************************
	 * carica il bean di Collaudo in base all'id di aggiudicazione ed alla data di inizioS
	 * @param idAggiud long
	 * @param dataInizioAggiud Timestamp
	 * @return CollaudiBean
	 * @throws ActionException
	 */
	public CollaudoBean load(long idAggiud , Timestamp dataInizioAggiud)throws ActionException{
		CollaudoManager cManager = new CollaudoManager(connection,logger);
		try{
			return cManager.load(idAggiud, dataInizioAggiud);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
		//	e.printStackTrace();
			throw new ActionException(e);
		}
	}
	
	/****************************************************************************************************
	 * carica il bean di Collaudo in base all'id di aggiudicazione ed alla data di inizioS
	 * @param idAggiud long
	 * @param dataInizioAggiud Timestamp
	 * @return CollaudiBean
	 * @throws ActionException
	 */
	public CollaudoBean loadById(long id , Timestamp dataInizio)throws ActionException{
		CollaudoManager cManager = new CollaudoManager(connection,logger);
		try{
			return cManager.loadById(id, dataInizio);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
		//	e.printStackTrace();
			throw new ActionException(e);
		}
	}

	/******************************************************************************************************
	 * Carica i ruoli relativi alla sezione 
	 * @param sezione String 
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadRuoliSezione(String sezione,Object o)throws ActionException{
		ResponsabileCollManager rcManager = new ResponsabileCollManager(connection,logger);
		try{
			return rcManager.loadRuoliSezione(sezione,o);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/*******************************************************************************************************
	 * Restituisce il Bean dellaconclusione relativa all'aggiudicazione
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @return ConclusioniBean
	 * @throws ActionException
	 */
	public ConclusioneBean getConclusione(long idAggiudicazione,Timestamp dataInizioAggiudicazione) throws ActionException{
		ConclusioniManager cm = new ConclusioniManager(connection,logger);
		ConclusioneBean cb = null;
		try{
			cb = cm.load(idAggiudicazione, dataInizioAggiudicazione);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
		return cb;
	}
	/******************************************************************************************************
	 * Ottiene la lista di Accordi associati all'aggiudicazione
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @return List AccordoBean
	 * @throws ActionException
	 */
	public List<AccordoBean> getAccordoBonario(long idAggiudicazione,Timestamp dataInizioAggiudicazione)throws ActionException{
		AccordoManager am = new AccordoManager(connection,logger);
		List<AccordoBean> accordi = null;
		try{
			accordi = am.loadMany(idAggiudicazione, dataInizioAggiudicazione);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
		return accordi;
	}
	
	public AggiudicazioneBean getAggiudicazione(long idAggiudicazione,Timestamp dataInizioAgg){
		return super.getAggiudicazione(idAggiudicazione, dataInizioAgg);
	}
	//2.10 aggiunto il metodo getInizioLavori
	public InizioLavoriBean getInizioLavori(long idAggiudicazione,Timestamp dataInizioAgg) throws ActionException{
		return super.getInizioLavori(idAggiudicazione, dataInizioAgg);
	}
	//2.10 fine
	
	public InfoComuniBean getInfoComuni(long idInfoComuni,Timestamp dataInizioInfo){
		return super.getInfoComuni(idInfoComuni, dataInizioInfo);
	}

	public Timestamp gestisciVariazioniCO(CollaudoBean saBean, RichiestaAnnullamento rab, String cfUtente, String tipoEnte)throws Exception{
		
		Timestamp vecchiaData = rab.getData_inizio_record();
		Timestamp ts = annullaCollaudo(connection, rab );
		boolean ret = false;
		List<ResponsabileBean> responsabili = saBean.getRespBean(); 
		
		BaseRichiestaAnnullamento aaa = AnnullamentoFactory.getAction(IdentificativoSchede.TAB_COLLAUDO, connection, logger);		
		
		if(ts != null){
			rab.setDecisore(cfUtente);
			rab.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
		
			rab.setData_inizio_record(vecchiaData);
			
			// PP B302.2.0
			if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive()){
				Map<String, String> lista = loadMotiviVCO(saBean.getDataIniColl());			
				rab.setMotivo_esito( (String) lista.get(saBean.getIdMotivoVarCO()));
			}
			
			ret = aaa.gestisciRichiesta(rab, cfUtente);
			if(ret){
				
				// aggiornamento anagrafiche responsabili modificate
				RubricaResponsabiliManager rrm = new RubricaResponsabiliManager(connection, logger);
				//RubricaManager rpm = new RubricaManager(connection, logger);
				
				if(responsabili != null){
					for(ResponsabileBean resp: responsabili){
						// da modificare
						if( resp.getSoggettoResponsabile().isModifica()){
	
							SoggettoResponsabileBean anagrafica_db = rrm.getAllSoggettoResponsabileByCF(resp.getSoggettoResponsabile().getCodiceFiscaleResponsabile());
							SoggettoResponsabileBean anagrafica_web = resp.getSoggettoResponsabile();
							anagrafica_web.setIdResponsabile(anagrafica_db.getIdResponsabile());
							anagrafica_web.setDataInizioRes(anagrafica_db.getDataInizioRes());
							boolean esitoConfronto = new SoggettiResponsabiliComparator().equals(resp.getSoggettoResponsabile(), anagrafica_db);
							//se le anagrafiche non sono uguali
							if(!esitoConfronto){
	
								rrm.cancellaPartecipante(RubricaResponsabili.converti(anagrafica_web));
								Object[] retVal = rrm.insertPartecipante(RubricaResponsabili.converti(resp.getSoggettoResponsabile()), true);
								
								// aggiorno la data validita
								resp.getSoggettoResponsabile().setDataInizioRes((Timestamp)retVal[1]);
							}
						}
					}
				}

                if(responsabili != null){
      				for(ResponsabileBean resp: responsabili)
      					resp.setDataInizioScheda(ts);		
                }
                
				saBean = load(saBean.getIdAggiudicazione(), saBean.getDataIniAggiudicazione());
				saBean.setRespBean(responsabili);
				
				IncaricatiCollaudoSharedAction iAction = new IncaricatiCollaudoSharedAction(connection,logger);
				
				if( confirm(saBean, cfUtente) > 0){
					iAction.confirm(responsabili, saBean.getIdCollaudo(), saBean.getDataIniColl());
				}
			}
		}
		return ts;
	}

	public Timestamp annullaCollaudo(Connection conn,  
		RichiestaAnnullamento bean) throws ActionException {
		IncaricatiCollaudoSharedAction iAction = new IncaricatiCollaudoSharedAction(conn,logger);
		
		Timestamp datavecchia = bean.getData_inizio_record();
		Timestamp nuovadata = richiestaAnnullamento(bean);
		
		bean.setData_inizio_record(nuovadata);
		
		boolean successo = nuovadata != null && iAction.richiediAnnullamento(bean, datavecchia);										
		
		bean.setData_inizio_record(datavecchia);
		return  successo ? nuovadata : null;
	}

}
