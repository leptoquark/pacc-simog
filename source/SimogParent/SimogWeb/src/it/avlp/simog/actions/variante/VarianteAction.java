package it.avlp.simog.actions.variante;

import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avcp.simog.managers.variante.EventiMotiviVariantiManager;
import it.avcp.simog.managers.variante.VarianteManager;
import it.avlp.simog.actions.BaseAction;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.variante.EventiMotiviVariantiBean;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.common.servlet.ParametriServletVariante;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.SimogProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class VarianteAction extends BaseAction{

	public static String CLAZZ = "VarianteAction";

	public VarianteAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
	}
	
	
	/********************************************************************************************
	 * Restituisce il Bean Variante corrispondente all'aggiudicazione 
	 * @param request HttpServletRequest
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @return VarianteBean
	 * @throws ActionException
	 */
	public VarianteBean getBean(HttpServletRequest request,long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException {
		String mtd = "getBean";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		
		VarianteBean bean = new VarianteBean();
		bean.setIdVariante((getLongReqParameter(request, -1,
				ParametriServletVariante.FIELD_NAME_ID_VARIANTE)));
		bean.setDataAttoAggiuntivo(getStringReqParameter(request, null,
				ParametriServletVariante.FIELD_NAME_DATA_ATTO_AGGIUNTIVO));
		
		bean.setDataInizioAggiudicazione(dataInizioAggiudicazione);
		//bean.setDescrizioneStato(ParametriServletAccordo.); 
		bean.setDataInizioVar(getTimestampReqParameter(request, null, ParametriServletVariante.FIELD_NAME_DATA_INIZIO_VAR));
		bean.setDataVerbaleApprovazione(getStringReqParameter(request, null, ParametriServletVariante.FIELD_NAME_DATA_VERB_APPR));
		bean.setIdAggiudicazione(idAggiudicazione);
		bean.setIdStato(getLongReqParameter(request, -1,
				ParametriServletVariante.FIELD_NAME_ID_STATO));
		bean.setImpDisposizione(getBigDecimalReqParameter(request, null,
				ParametriServletVariante.FIELD_NAME_IMP_DISPOSIZIONE));
		bean.setImpProgettazione(getBigDecimalReqParameter(request, null,
				ParametriServletVariante.FIELD_NAME_IMP_PROGETTAZIONE));
		bean.setUlterioriSomme(getBigDecimalReqParameter(request, null,
				ParametriServletVariante.FIELD_NAME_ULTERIORI_SOMME));
		bean.setImpRidetFornit(getBigDecimalReqParameter(request, null,
				ParametriServletVariante.FIELD_NAME_IMP_DIRET_FORNIT));
		bean.setImpRidetLavori(getBigDecimalReqParameter(request, null,
				ParametriServletVariante.FIELD_NAME_IMP_RIDET_LAVORI));
		bean.setImpRidetServizi(getBigDecimalReqParameter(request, null,
				ParametriServletVariante.FIELD_NAME_IMP_RIDET_SERVIZI));
		bean.setImpSicurezza(getBigDecimalReqParameter(request, null,
				ParametriServletVariante.FIELD_NAME_IMP_SICUREZZA));
		bean.setNumGiorniProroga(getIntReqParameter(request, 0,
				ParametriServletVariante.FIELD_NAME_NUM_GIORNI_PROROGA));
		bean.setAltreMotivazioni(getStringReqParameter(request, null,
				ParametriServletVariante.FIELD_NAME_ALTRE_MOTIVAZIONI));
		bean.setCigProcedura(getStringReqParameter(request, null, ParametriServletVariante.FIELD_NAME_CIG_PROCEDURA));//TICKET ALM - 3.04.3 PT
		
//		MEV 34191 3.04.8
		bean.setLinkVarianti(getStringReqParameter(request, null,
				ParametriServletVariante.FIELD_NAME_LINK_VARIANTI));
		
		// LF:       Lista motivazioni varianti
		// prendo dalla JSP la lista delle id_motivazioni e ne creo una lista di EventiMotiviVariantiBean
		// inserendone i valori per id_variante e data inizio variante
	
		String[] lista_id_motivi = request.getParameterValues(ParametriServletVariante.FIELD_NAME_ID_MOTIVAZIONE);
		
		//MEV 34469 3.04.8
		bean.setIdMotivoRevPrezzi((getStringReqParameter(request, null,
				ParametriServletVariante.FIELD_NAME_ID_MOTIVO_REV_PREZZI)));
		
		if ( lista_id_motivi != null && lista_id_motivi.length != 0 ) { 
			
			ArrayList<EventiMotiviVariantiBean> emvLista = new ArrayList<EventiMotiviVariantiBean>(); 
			
			for(int i=0;i<lista_id_motivi.length;i++) {
			// LF:  creo il bean e lo inserisco nella lista
				EventiMotiviVariantiBean beanMotivi = new EventiMotiviVariantiBean();
				beanMotivi.setIdMotivoVariante((long)(Integer.parseInt(lista_id_motivi[i])));
				beanMotivi.setIdVariante(bean.getIdVariante());
				beanMotivi.setDataIniVariante(bean.getDataInizioVar());
				emvLista.add(beanMotivi);
			
			}
			// LF:  assegno la lista al bean
			bean.setEmvb(emvLista);
		}
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(VarianteBean.class, bean));
		return bean;
	}
	
	/*****************************************************************************************************
	 * Gestisce il salvataggio della Variante
	 * @param bean VarianteBean
	 * @param emBean List&lt;EventiMotiviVarianteBean&gt;
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int save(VarianteBean bean, List<EventiMotiviVariantiBean> emBean,String cfUtente) throws ActionException {
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(VarianteBean.class, bean));
		VarianteManager varManager = new VarianteManager(connection, logger);
		EventiMotiviVariantiManager emvManager = new EventiMotiviVariantiManager(connection, logger);
		// Inserimento della Variante e delle motivazioni come 
		// Bean separati. Per poter inserire la motivazione e' opportuno ottenere prima l'ID della variante
 		try {
 			// *******    Inserimento variante   ********* 
			if (bean.getIdVariante()< 1) {
				varManager.insert(bean, cfUtente);
				
			// prelievo dell'ID variante e inserimento della motivazione
				// espressione per l'iterazione compatta di una lista!
				if(emBean != null){
					for(EventiMotiviVariantiBean eBean : emBean){ 
						eBean.setIdVariante(bean.getIdVariante());
						eBean.setDataIniVariante(bean.getDataInizioVar());
						eBean.setIdStato((long)StatiScheda.IN_DEFINIZIONE);
						emvManager.save(eBean,cfUtente);
					}
				}				
				return 1;
			}
			else {
				if (!varManager.existVariante(bean.getIdVariante(), bean.getDataInizioVar()))
					throw new ActionException("Scheda inesistente");
				int num = varManager.save(bean, cfUtente);
				if( num > 0 ){
					if (bean.getEmvb() !=  null ){
						emvManager.deleteRecord(bean.getIdVariante(), bean.getDataInizioVar());
						for (EventiMotiviVariantiBean emBeanTemp : bean.getEmvb() ){
							emBeanTemp.setIdStato((long)StatiScheda.IN_DEFINIZIONE);
							emvManager.save(emBeanTemp, cfUtente);
						}
					}
					else {
						emvManager.deleteRecord(bean.getIdVariante(), bean.getDataInizioVar());
					}
				}
				return num;
			}
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	
	/******************************************************************************************************
	 * Gestisce la conferma 
	 * @param bean VarianteBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int confirm(VarianteBean bean , String cfUtente)throws ActionException{
		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(ConclusioneBean.class, bean));
		VarianteManager varManager = new VarianteManager(connection,logger);
		EventiMotiviVariantiManager emvManager = new EventiMotiviVariantiManager(connection, logger);
		try{
			if(!varManager.existVariante(bean.getIdVariante(), bean.getDataInizioVar())) throw new ActionException("Scheda inesistente");
			int num = varManager.confirm(bean, cfUtente);
			if( num > 0 ){
				// si esegue il confirm anche sulle motivazioni eventi
				emvManager.deleteRecord(bean.getIdVariante(), bean.getDataInizioVar()); // eliminazione vecchi record
				if(bean.getEmvb() != null){
					for (EventiMotiviVariantiBean emBeanTemp : bean.getEmvb() ){
						emvManager.confirm(emBeanTemp, cfUtente);
					}
				}
			}
			return num;
		}catch(Exception e){
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	
		/**************************************************************************************************
		 * Gestisce la richiesta di annullamento
		 * @param bean RichiestaAnnullamento
		 * @return Timestamp
		 * @throws ActionException
		 */
		public Timestamp richiediAnnullamento(RichiestaAnnullamento bean)throws ActionException {
			String idrecord = bean.getId_record(); // qui e' contenuto idVariante
			Timestamp datainiziorecord = bean.getData_inizio_record(); // qui e' contenuta la data di inizio della Variante
			String idLotto = bean.getId_lotto();
			String blocco = bean.getBlocco();
			String cfUtente = bean.getRichiedente();
			try {
				VarianteManager vm = new VarianteManager(connection, logger);
				Timestamp nuovaDataInizio = vm.copyRecord(Long.parseLong(idrecord), datainiziorecord);
				EventiMotiviVariantiManager emvManager = new EventiMotiviVariantiManager(connection, logger);
				emvManager.copyRecord(Long.parseLong(idrecord), nuovaDataInizio, datainiziorecord);
				if (nuovaDataInizio != null) {
			
					logger.debug("Data nuova: " + nuovaDataInizio);
					if (super.bsa.scriviAnnullamento(bean)){
						List<Object> attributiChiave = new ArrayList<Object>();
						attributiChiave.add(idrecord);
						attributiChiave.add(datainiziorecord);
						LogBloccoDatiManager.loggingCANCELREQ(connection, logger, cfUtente, blocco, attributiChiave);
						return nuovaDataInizio;
					}
				}
			
				//VarianteManager vManager = new VarianteManager(connection, logger);
				//vManager.
			return null;
			} catch (Exception e) {
//				log come fatal demandato al chiamante
				logger.error(e.getMessage());
				//e.printStackTrace();
				throw new ActionException(e);
		}
	}
		
		/**************************************************************************************************
		 * Gestisce la richiesta di cancellazione
		 * @param bean RichiestaAnnullamento
		 * @return Timestamp
		 * @throws ActionException
		 */
		public void richiediCancellazione(RichiestaAnnullamento bean)throws ActionException {
			String idrecord = bean.getId_record(); // qui e' contenuto idVariante
			Timestamp datainiziorecord = bean.getData_inizio_record(); // qui e' contenuta la data di inizio della Variante
			try {
					if (super.bsa.scriviAnnullamento(bean)){
						List<Object> attributiChiave = new ArrayList<Object>();
						attributiChiave.add(idrecord);
						attributiChiave.add(datainiziorecord);
						LogBloccoDatiManager.loggingDELETEREQ(connection, logger, bean.getRichiedente(),  bean.getBlocco(), attributiChiave);
					}
			
				//VarianteManager vManager = new VarianteManager(connection, logger);
				//vManager.
			} catch (Exception e) {
//				log come fatal demandato al chiamante
				logger.error(e.getMessage());
				//e.printStackTrace();
				throw new ActionException(e);
		}
	}	
	
		
		/***********************************************************************************************
		 * Genera la lista di Varianti associate all'aggidicazione
		 * @param idAggiudicazione long
		 * @param dataInizioAggiudicazione Timestamp
		 * @return List&lt;VarianteBean&gt;
		 * @throws ActionException
		 */
		public List<VarianteBean> loadAllByAgg(long idAggiudicazione, Timestamp dataInizioAggiudicazione)
		throws ActionException {
		
		VarianteManager varManager = new VarianteManager(connection, logger);
		EventiMotiviVariantiManager emvManager = new EventiMotiviVariantiManager(connection,logger);
		
		try {
			
			List<VarianteBean> varianti =  varManager.loadMany(idAggiudicazione, dataInizioAggiudicazione);
			
			// Itero la lista varianti
			// per ogni variante cerco gli eventi motivo varianti associati a un determinato idVariante 
			// e dataInizioVariante. 
			// Tale lista viene inserita come parametro Emvb della variante. 
			
			for (int i=0; i<varianti.size();i++) {
				VarianteBean vBean = varianti.get(i);
				varianti.get(i).setEmvb(emvManager.loadMany(vBean.getIdVariante(), vBean.getDataInizioVar()));
			}
			
			
			return varianti;
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}

	}

	
	/****************************************************************************************************
	 * Carica la variante 
	 * @param idVariante long
	 * @param dataInizioVariante Timestamp
	 * @return VarianteBean
	 * @throws ActionException
	 */
	public VarianteBean loadOne(long idVariante, Timestamp dataInizioVariante) throws ActionException {
		return loadOne(idVariante, dataInizioVariante, null);

	}
	
	/*******************************************************************************************************
	 * Carica una variante
	 * @param idVariante long
	 * @param dataInizioVariante Timestamp
	 * @param newDataInizioVariante Timestamp
	 * @return VarianteBean
	 * @throws ActionException
	 */
	public VarianteBean loadOne(long idVariante, 
							   Timestamp dataInizioVariante,
							   Timestamp newDataInizioVariante) throws ActionException {
		
		if (newDataInizioVariante == null)
			newDataInizioVariante = dataInizioVariante;

		VarianteManager varManager = new VarianteManager(connection, logger);

		try {
			// carico la lista dei motivi variante
			EventiMotiviVariantiManager emvManager = new EventiMotiviVariantiManager(connection,logger);
			List<EventiMotiviVariantiBean> lista_motiviVariante = new ArrayList<EventiMotiviVariantiBean>();
			lista_motiviVariante = emvManager.loadMany(idVariante, dataInizioVariante);
			
			// Inserisco nella variante la lista aggiornata dei motivi
			VarianteBean varBean = new VarianteBean();
			varBean = varManager.loadOne(idVariante, newDataInizioVariante);
			if(varBean != null)
				varBean.setEmvb(lista_motiviVariante);
			return varBean;

		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new ActionException(e);
		}
	}
	
	/***********************************************************************************************
	 * Genera la mappa dei motivi variante
	 * @param tipoContratto String
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadMotiviVariante(String tipoContratto,Object o, String dataCreazione) throws ActionException {
		VarianteManager varManager = new VarianteManager(connection,logger);
		try {
			if(dataCreazione.compareTo(Costanti.DATA_DL50) <= 0 || SimogProperties.getInstance().isDataCreatedAfter3042(dataCreazione))
				return varManager.loadMotiviVariantePerData(tipoContratto,o);
			else {
				return varManager.getAllMotiviVariante();
			}
		} catch (SQLException e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		} catch (Exception e) {
			logger.error(e);
			throw new ActionException(e);
		}
		
		
	}
	public AggiudicazioneBean getAggiudicazione(long idAggiudicazione,Timestamp dataInizioAgg) {
		return super.bsa.getAggiudicazione(idAggiudicazione, dataInizioAgg);
	}
	public InfoComuniBean getInfoComuni(long idInfoComuni,Timestamp dataInizioInfo) {
		return super.bsa.getInfoComuni(idInfoComuni, dataInizioInfo);
	}
	/************************************************************************************************
	 * Genera il bean InizioLavori associato all'aggiudicazione
	 * @param idAggiudicazione long	
	 * @param dataInizioAggiudicazione Timestamp
	 * @return InizioLavoriBean
	 * @throws ActionException
	 */
	public InizioLavoriBean getInizioLavori(long idAggiudicazione,Timestamp dataInizioAggiudicazione) throws ActionException{
		InizioLavoriBean inizioLavori = new InizioLavoriBean();
		InizioLavoriManager im = new InizioLavoriManager(connection,logger);
		try{
			inizioLavori = im.load(idAggiudicazione, dataInizioAggiudicazione);
		}catch (Exception e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
		return inizioLavori;
	}	
	
	//MEV 34469 3.04.8
		/***********************************************************************************************
		 * Genera la mappa dei motivi revisione prezzi
		 * @param idMotivoRevPrezzi long
		 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
		 * @return Map&lt;String, String&gt;
		 * @throws ActionException
		 */
		public SortedMap<String, String> loadMotivoRevisionePrezzi() throws ActionException {
			VarianteManager varManager = new VarianteManager(connection,logger);
			try {			    
				
			    return varManager.getAllMotivoRevPrezzi();

			} catch (SQLException e) {
//				log come fatal demandato al chiamante
				logger.error(e);
				throw new ActionException(e);
			} catch (Exception e) {
				logger.error(e);
				throw new ActionException(e);
			}
			
			
		}
	}


