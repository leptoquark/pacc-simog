package it.avlp.simog.actions.accordo;

import it.avcp.simog.managers.accordo.AccordoManager;
import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avlp.simog.actions.BaseAction;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.common.servlet.ParametriServletAccordo;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


public class AccordoAction extends BaseAction{

	public static String CLAZZ = "AccordoAction";

	public AccordoAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);

	}
	// *************************************************************************************
	// *                                    GETBEAN
	/***************************************************************************************
	 * Il metodo si occupa della generazione del Bean Accordo prelevando le informazioni riguardanti:
	 * <ul>
	 * <li>Id Accordo
	 * <li>Data Accordo
	 * <li>Data Fine Accordo
	 * <li>Data Inizio Accordo
	 * <li>Num Riserve
	 * <li>Oneri Derivanti
	 * </ul>
	 * direttamente dalla request mentre i dati relativi a 
	 * <ul>
	 * <li>Data Inizio Aggiudicazione
	 * <li>Id Aggiudicazione
	 * </ul> 
	 * dai parametri in ingresso idAggiudicazione e dataInizioAggiudicazione
	 * 
	 * 
	 * @param request HttpServletRequest 
	 * @param idAggiudicazione long contenente l'id dell'aggiudicazione
	 * @param dataInizioAggiudicazione Timestamp contenete la data di inizio dell'aggiudicazione 
	 * @return AccoroBean
	 * @throws ActionException
	 */
	public AccordoBean getBean(HttpServletRequest request,long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws ActionException {
		String mtd = "getBean";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		
		AccordoBean bean = new AccordoBean();
		bean.setIdAccordo((getLongReqParameter(request, -1,
				ParametriServletAccordo.FIELD_NAME_ID_ACCORDO)));
		bean.setDataAccordo(getStringReqParameter(request, null,
				ParametriServletAccordo.FIELD_NAME_DATA_ACCORDO));
		bean.setDataFineAccordo(getTimestampReqParameter(request,null, 
				ParametriServletAccordo.FIELD_NAME_DATA_FINE_ACC));
		bean.setDataInizioAccordo(getTimestampReqParameter(request, null, 
				ParametriServletAccordo.FIELD_NAME_DATA_INIZIO_ACC));
		bean.setDataInizioAggiudicazione(dataInizioAggiudicazione);
		bean.setIdAggiudicazione(idAggiudicazione);
		bean.setNumeroRiserve(getIntReqParameter(request, 0, 
				ParametriServletAccordo.FIELD_NAME_NUM_RISERVE));
		bean.setOneriDerivanti(getBigDecimalReqParameter(request, null,
				ParametriServletAccordo.FIELD_NAME_ONERI_DERIVANTI));
		bean.setOneriDerivantiStr(getStringReqParameter(request, null,
				ParametriServletAccordo.FIELD_NAME_ONERI_DERIVANTI));		
		logger.debug(logPrefix
				+ ObjectIntrospector.propertiesInfo(AccordoBean.class, bean));
		return bean;
	}
	
	//******************************************************************************************
	//                                       SAVE
	/*******************************************************************************************
	 * Effettua il salvataggio dei dati. Se l'id Accordo contenuto nel bean 
	 * in ingresso non � presente viene eseguito un inserimento nella tabella 
	 * Accordi altrimenti si controlla l'esistenza di tale Id. Se l'id non 
	 * esiste si lancia un'eccezione legata all'inesistenza della scheda, se
	 * l'id � presente nel DB vine effettuato il salvataggio dei dati sul DB.
	 * 
	 * @param bean AccordoBean contenente i dati da inserire
	 * @param cfUtente String con il codice fiscale dell'utente 
	 * @throws ActionException
	 */
	public int save(AccordoBean bean, String cfUtente) throws ActionException {
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(AccordoBean.class, bean));
		AccordoManager accManager = new AccordoManager(connection, logger);
 		try {
			if (bean.getIdAccordo()< 1){
				accManager.insert(bean, cfUtente);
				return 1;
			}else{
				if (!accManager.existAccordo(bean.getIdAccordo(), bean.getDataInizioAccordo()) )
					throw new ActionException("Scheda inesistente");
				return accManager.save(bean, cfUtente);
			}
		} catch (Exception e) {
			//fatal logging demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	
	  // ************************************************************************************
	  // *                                    CONFIRM
		/************************************************************************************
		 * Il metodo si occupa di confermare la scheda di accordo. Prima della confrema si effettua un controllo
		 * sull'id e sulla Data di Inizio dell'accordo, se questi non sono presenti sul DB viene sollevata l'eccezione 
		 * altrimenti viene eseeguita la conferma dei dati nel bean.  
		 * 
		 * @param bean AccordoBean contenente le informazioni relative alla scheda accordo da confermare
		 * @param cfUtente String contenente il codice fiscale dell'utente connesso. 
		 * @throws ActionException
		 */
		public int confirm(AccordoBean bean, String cfUtente) throws ActionException {
			String mtd = "confirm";
			String logPrefix = CLAZZ + "." + mtd + ": ";
			logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(AccordoBean.class, bean));
			AccordoManager accManager = new AccordoManager(connection, logger);
	
			try {
				if (!accManager.existAccordo(bean.getIdAccordo(), 
											  bean.getDataInizioAccordo()))
					throw new ActionException("Scheda inesistente");
	
				return accManager.confirm(bean, cfUtente);
	
			} catch (Exception e) {
				//fatal logging demandato al chiamante
				logger.error(e);
				throw new ActionException(e);
			}
		}
		
      // *****************************************************************************************
	  // *                            RICHIEDIANNULLAMENTO
		/*****************************************************************************************
		 * Viene aggiornato lo storico della scheda accordo, la scheda attuale diventa 
		 * quella contenuta nel bean passato come parametro in ingresso, Restituisce 
		 * la nuova data di accordo che corrisponde alla data attuale. 
		 * 
		 * @param bean sRichiestaAnnullamento con i dati del nuovo Bean
		 * @return Timestamp relativa alla nuova data di accordo. 
		 * @throws ActionException
		 */
		public Timestamp richiediAnnullamento(RichiestaAnnullamento bean) throws ActionException {
				String idrecord = bean.getId_record(); // qui � contenuto idSospensioni
				Timestamp datainiziorecord = bean.getData_inizio_record(); // qui � contenuta la data di inizio della Sospensione 
				String blocco = bean.getBlocco();
				String cfUtente = bean.getRichiedente();
				try {
					Timestamp nuovaDataInizio = (new AccordoManager(connection, logger).copyRecord(Long.parseLong(idrecord), datainiziorecord));
				
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
				
					return null;
				} catch (Exception e) {
					//log come fatal demandato al chiamante
					logger.error(e.getMessage());
				//	e.printStackTrace();
					throw new ActionException(e);
			}
		}
		
		// RICHIEDI CANCELLAZIONE
		
		public void richiediCancellazione(RichiestaAnnullamento bean) throws ActionException {
			
			String idrecord = bean.getId_record(); // qui � contenuto idSospensioni
			Timestamp datainiziorecord = bean.getData_inizio_record(); // qui � contenuta la data di inizio della Sospensione 

			try {
				
				if (super.bsa.scriviAnnullamento(bean)){
					List<Object> attributiChiave = new ArrayList<Object>();
					attributiChiave.add(idrecord);
					attributiChiave.add(datainiziorecord);
					LogBloccoDatiManager.loggingDELETEREQ(connection, logger, bean.getRichiedente(), bean.getBlocco(), attributiChiave);							
				}						
			
			}
			catch (Exception e) {
				//log come fatal demandato al chiamante
				logger.error(e.getMessage());
			//	e.printStackTrace();
				throw new ActionException(e);
		}
	}
		
		  // ************************************************************************************
		  // *                                LOADALLBYAGG
	    /************************************************************************************
		 * Ottiene tutti gli accordi associati ad una aggiudicazione
		 * @param idAggiudicazione
		 * @param dataInizioAggiudicazione
		 * @return List&lt;AccordoBean&gt;
		 * @throws ActionException
		 */
		public List<AccordoBean> loadAllByAgg(long idAggiudicazione, Timestamp dataInizioAggiudicazione)
		throws ActionException {
		
		AccordoManager accManager = new AccordoManager(connection, logger);

		try {

			return accManager.loadMany(idAggiudicazione, dataInizioAggiudicazione);

		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}

	}
	  // **************************************************************************************************
	  // *                                             LOADONE  	
		/**************************************************************************************************
		 * Restituisce un AccordoBean identificato attraverso idAccordo e dataInizioAccordo.  
		 * 
		 * @param idAccordo long per l'id dell'accordo
		 * @param dataInizioAccordo Timestamp contenente la data di inizio accordo
		 * @return AccordoBean
		 * @throws ActionException
		 */
		public AccordoBean loadOne(long idAccordo, Timestamp dataInizioAccordo) throws ActionException {
			return loadOne(idAccordo, dataInizioAccordo, null);

		}
		
	   //**************************************************************************************************
	  // * 		                                       LOADONE  
	    /**************************************************************************************************
	     * Restituisce un AccordoBean identificato attraverso idAccordo e newDataInizioAccordo. 
	     * Se newDataInizioAccordo � null viene effettuata la ricerca in base a idAccordo e 
	     * dataInizioAccordo.  
	     * 
		 * @param idAccordo
		 * @param dataInizioAccordo
		 * @param newDataInizioAccordo
		 * @return AccordoBean
		 * @throws ActionException
		 */
		public AccordoBean loadOne(long idAccordo, Timestamp dataInizioAccordo,
				Timestamp newDataInizioAccordo) throws ActionException {
			
			if (newDataInizioAccordo == null)
				newDataInizioAccordo = dataInizioAccordo;

			AccordoManager accManager = new AccordoManager(connection, logger);

			try {

				return accManager.loadOne(idAccordo, newDataInizioAccordo);

			} catch (Exception e) {
//				log come fatal demandato al chiamante
				logger.error(e.getMessage());
			//	e.printStackTrace();
				throw new ActionException(e);
			}
		}
		/**************************************************************************************************
		 * Ottiene le informazioni di inizio lavori associate all'aggiudicazione
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
//				log come fatal demandato al chiamante
				logger.error(e.getMessage());
				//e.printStackTrace();
				throw new ActionException(e);
			}
			return inizioLavori;
		}
		public AggiudicazioneBean getAggiudicazione(long idAggiudicazione,Timestamp dataInizioAgg) {
			return this.bsa.getAggiudicazione(idAggiudicazione, dataInizioAgg);
		}
		public InfoComuniBean getInfoComuni(long idInfoComuni,Timestamp dataInizioInfo) {
			return this.bsa.getInfoComuni(idInfoComuni, dataInizioInfo);
		}
		
		
		
}
