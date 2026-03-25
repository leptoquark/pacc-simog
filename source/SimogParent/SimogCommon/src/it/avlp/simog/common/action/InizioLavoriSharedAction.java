package it.avlp.simog.common.action;

import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.RubricaResponsabili;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.comparators.SoggettiResponsabiliComparator;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.inizio.SchedaInizioLavori;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.factory.AnnullamentoFactory;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.rubricamanager.RubricaResponsabiliManager;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class InizioLavoriSharedAction extends BaseSharedAction {

	public InizioLavoriSharedAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
	
	}
	
	/********************************************************************************************
	 * Gestisce il salvataggio 
	 * @param bean InizioLavoriBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int save(InizioLavoriBean bean,String cfUtente)throws ActionException{
		logger.debug("InizioLavoriAction.save, bean da salvare: " + ObjectIntrospector.propertiesInfo(InizioLavoriBean.class, bean));
		
		InizioLavoriManager ilMan = new InizioLavoriManager(connection,logger);
		PubblicazioneManager pubMan = new PubblicazioneManager(connection,logger);
		try {
		
			if(bean.getIdInizioLavori()<1){ 
				pubMan.insertPubblicazione(bean.getPubblicazione());
				ilMan.insert(bean, cfUtente);
				return 1;
			}
			else {
				int num = ilMan.save(bean, cfUtente);
				if(num > 0) pubMan.save(bean.getPubblicazione());
				return num;
			}

		}
		catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	
	}			
	
	
	/******************************************************************************************************
	 * Gestisce la conferma
	 * @param bean InizioLavoriBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int confirm(InizioLavoriBean bean,String cfUtente)throws ActionException{
		
		InizioLavoriManager aggMan = new InizioLavoriManager(connection,logger);
		PubblicazioneManager pubMan = new PubblicazioneManager(connection,logger);
		try {
			int num = aggMan.confirm(bean, cfUtente);
			if(num > 0) pubMan.confirm(bean.getPubblicazione());
			return num;
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
		
	}	
	
	/*******************************************************************************************************
	 * Carica il bean di inizio lavori in base all'aggiudicazione indicata
	 * @param idAggiudicazione long
	 * @param dataInizioAgg Timestamp
	 * @param ignoraStato TODO
	 * @return InizioLavoriBean
	 * @throws ActionException
	 */
	public InizioLavoriBean load(long idAggiudicazione,Timestamp dataInizioAgg) throws ActionException {

		InizioLavoriManager man = new InizioLavoriManager(connection, logger);
        
        //PubblicazioneManager pubMan = new PubblicazioneManager(connection,logger);
		InizioLavoriBean ilb = null;
		try {
			ilb = man.load(idAggiudicazione, dataInizioAgg);
			//ilb.setPubblicazione(pubMan.getPubblicazione(ilb.getPubblicazione().getIdPubblicazione(), ilb.getPubblicazione().getDataInizioPubblicazione()));

			return  ilb;
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
		
	}
	
	/*******************************************************************************************************
	 * Carica il bean di inizio lavori in base all'id
	 * @param idAggiudicazione long
	 * @param dataInizioAgg Timestamp
	 * @param ignoraStato TODO
	 * @return InizioLavoriBean
	 * @throws ActionException
	 */
	public InizioLavoriBean loadById(long id,Timestamp dataInizio) throws ActionException {

		InizioLavoriManager man = new InizioLavoriManager(connection, logger);
		InizioLavoriBean ilb = null;
		try {
			ilb = man.loadById(id, dataInizio);
			return  ilb;
		} catch (SQLException e) {
			logger.error(e);
			throw new ActionException(e);
		}
		
	}
	/*******************************************************************************************************
	 * Effettua una copia del record di inizio lavori
	 * param idInizio long
	 * param dataInizio Timestamp
	 * param old_data_inizio_pub Timestamp
	 * param new_data_inizio_pub Timestamp
	 * return Timestamp
	 * throws ActionException
	 */
	private Timestamp copyRecord(long idInizio,Timestamp dataInizio,Timestamp old_data_inizio_pub, Timestamp new_data_inizio_pub)throws ActionException {
		
		InizioLavoriManager man = new InizioLavoriManager(connection, logger);
		try { 
			return man.copyRecord(idInizio,dataInizio,old_data_inizio_pub,new_data_inizio_pub);
		} catch (SQLException e) {
			throw new ActionException(e);
			
		}
	}
	
	/***************************************************************************************************
	 * Gestisce la richiesta di annullamento e la storicizzazione dei dati
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	private Timestamp richiediAnnullamento(RichiestaAnnullamento bean) throws ActionException {

		String idScheda = bean.getId_record();
		Timestamp dataInizioScheda = bean.getData_inizio_record();
		String idpub = bean.getId_pub();
		Timestamp old_data_inizio_pub = bean.getData_inizio_pub();
		String idLotto = bean.getId_lotto();
		logger.debug("idinfo: "+idScheda+", datainizioinfo: "+dataInizioScheda+", idpub: "+idpub+", datainiziopub: "+old_data_inizio_pub);
		try{
			PubblicazioneManager pubblicazioneManager = new PubblicazioneManager(connection,logger);
			Timestamp new_data_inizio_pub = pubblicazioneManager.copyRecord(Long.parseLong(idpub), old_data_inizio_pub);
			Timestamp nuovadata = copyRecord(Long.parseLong(idScheda),dataInizioScheda,old_data_inizio_pub,new_data_inizio_pub);
			
			if(nuovadata != null) {
				if (this.scriviAnnullamento(bean)){
					List<Object> attributiChiave = new ArrayList<Object>();
					attributiChiave.add(idScheda);
					attributiChiave.add(dataInizioScheda);
					LogBloccoDatiManager.loggingCANCELREQ(connection, logger, bean.getRichiedente(), bean.getBlocco(), attributiChiave);
					return nuovadata;
				}
			}
				
			return nuovadata;
		}
		catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/***************************************************************************************************
	 * Gestisce la richiesta di cancellazione e la storicizzazione dei dati
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public void richiediCancellazione(RichiestaAnnullamento bean) throws ActionException {

		//logger.debug
		String idScheda = bean.getId_record();
		Timestamp dataInizioScheda = bean.getData_inizio_record();
		String idpub = bean.getId_pub();
		Timestamp old_data_inizio_pub = bean.getData_inizio_pub();
		logger.debug("idinfo: "+idScheda+", datainizioinfo: "+dataInizioScheda+", idpub: "+idpub+", datainiziopub: "+old_data_inizio_pub);
		// fine logger.debug
		
		try{
			
			if (this.scriviAnnullamento(bean)){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idScheda);
				attributiChiave.add(dataInizioScheda);
				LogBloccoDatiManager.loggingDELETEREQ(connection, logger, bean.getRichiedente(), bean.getBlocco(), attributiChiave);
			}
				
		}
		catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	public AggiudicazioneBean getAggiudicazione(long idAggiudicazione,Timestamp dataInizioAgg){
		return super.getAggiudicazione(idAggiudicazione, dataInizioAgg);
	}
	
	public InfoComuniBean getInfoComuni(long idInfoComuni,Timestamp dataInizioInfo){
		return super.getInfoComuni(idInfoComuni, dataInizioInfo);
	}
	/*******************************************************************************************************
	 * Restituisce il bean della pubblicazione  
	 * @param idPubblicazione long
	 * @param dataInizioPubblicazione Timestamp
	 * @return PubblicazioneBan
	 * @throws ActionException
	 */
	public PubblicazioneBean getPubblicazione(long idPubblicazione,Timestamp dataInizioPubblicazione) throws ActionException{
		PubblicazioneManager pm = new PubblicazioneManager(connection,logger);
		PubblicazioneBean pb = null;
		try{
			pb = pm.getPubblicazione(idPubblicazione, dataInizioPubblicazione);
		}catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}return pb;
	}

	/*****************************************************************************************************
	 * Carica le info pubblicazioni dal'avviso di aggiudicazione pubblicato, se esiste 
	 * @param idPubb long
	 * @param dataInizioPubb Timestamp
	 * @return void
	 * @throws ActionException
	 */
	public void loadPubbFromAvviso(long idPubb, Timestamp dataInizioPubb, InizioLavoriBean icb) throws ActionException{
		PubblicazioneManager pubManager = new PubblicazioneManager(connection, logger);
		try{			
			icb.setPubblicazione(pubManager.getPubblicazione(idPubb, dataInizioPubb));
			return;
		}catch (SQLException e) {
			
			logger.error(e);
			throw new ActionException(e);
		}
	}

	public Timestamp annulla(Connection conn, RichiestaAnnullamento bean) throws ActionException {
		ResponsabileInizioSharedAction riAction = new ResponsabileInizioSharedAction(conn, logger);
		PosizioneAggiudicataroSharedAction paAction = new PosizioneAggiudicataroSharedAction(conn, logger);
		// sta in iaction PubblicazioneAction pAction = new PubblicazioneAction(conn, logger);
		
		Timestamp datavecchia = bean.getData_inizio_record();
		Timestamp nuovadata = richiediAnnullamento(bean);
		
		bean.setData_inizio_record(nuovadata);
		
		boolean successo = nuovadata != null && riAction.richiediAnnullamento(bean, datavecchia) && paAction.richiediAnnullamento(bean, datavecchia);										
		
		bean.setData_inizio_record(datavecchia);
		return  successo ? nuovadata : null;
	}

	public Timestamp gestisciVariazioniCO(SchedaInizioLavori saBean, RichiestaAnnullamento rab, String cfUtente)throws Exception{
		
		Timestamp vecchiaData = rab.getData_inizio_record();
		Timestamp ts = annulla(connection, rab );
		boolean ret = false;
		List<ResponsabileBean> responsabili = saBean.getResponsabiliInizio(); 
		
		//List<PosizioneAggiudicatarioBean> posizioni = saBean.getPosizioneAggiudicatari(); 
		
		BaseRichiestaAnnullamento aaa = AnnullamentoFactory.getAction(IdentificativoSchede.TAB_INIZIO_LAVORI, connection, logger);		
		
		ResponsabileInizioSharedAction riAction = new ResponsabileInizioSharedAction(connection, logger);
		PosizioneAggiudicataroSharedAction paAction = new PosizioneAggiudicataroSharedAction(connection, logger);

		if(ts != null){
			rab.setDecisore(cfUtente);
			rab.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
		
			rab.setData_inizio_record(vecchiaData);
			
			// PP B302.2.0
			if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive()){
				Map<String, String> lista = this.loadMotiviVCO(saBean.getDatiInizio().getDataInizioLavori());			
				rab.setMotivo_esito( (String) lista.get(saBean.getDatiInizio().getIdMotivoVarCO()));
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
				
					for(ResponsabileBean resp: responsabili)
						resp.setDataInizioScheda(ts);		
				}
				
				InizioLavoriBean datiLavori = load(saBean.getDatiInizio().getIdAggiudicazione(), saBean.getDatiInizio().getDataInizioAggiudicazione());
				saBean.setDatiInizio(datiLavori);

				saBean.setResponsabiliInizio(responsabili); 
								
				if( confirm(datiLavori, cfUtente) > 0){
					riAction.confirm(saBean.getResponsabiliInizio(), datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori());
					paAction.confirm(saBean.getPosizioneAggiudicatari(), datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori());
				}			
			}
		}
		return ts;
	}
}
