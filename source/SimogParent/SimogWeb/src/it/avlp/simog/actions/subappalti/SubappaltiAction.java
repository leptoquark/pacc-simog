package it.avlp.simog.actions.subappalti;

import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avcp.simog.managers.subappalti.SubappaltiManager;
import it.avlp.simog.actions.BaseAction;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.TipoFinanziamentoBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.subappalti.SubappaltatoreBean;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.common.action.DittaAusiliariaAction;
import it.avlp.simog.common.action.Scheda_A_SharedAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletSubappalti;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avlp.simog.util.Base64Coder;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class SubappaltiAction extends BaseAction {
	
	
	public static String CLAZZ = "SubappaltiAction";
	
	public Scheda_A_SharedAction sasa;

	public SubappaltiAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		this.sasa = new Scheda_A_SharedAction(activeConnection, logger);
	}
	
	
	/****************************************************************************************************************
	 * Carica nel bean <code>SubAppaltiBean</code> i dati dei subappalti relativi all'aggiudicazione prelevandoli 
	 * dalla request.  
	 * @param request HttpServletRequest
	 * @param idAggiudicazione long id dell'aggiudicazione
	 * @param dataInizioAggiudicazione Timestamp data di inizio dell'aggiudicazione
	 * @return SubappaltiBean
	 * @throws ActionException
	 */
	public SubappaltiBean getBean(HttpServletRequest request, long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws ActionException{
		String mtd = "getBean";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		SubappaltiBean bean = new SubappaltiBean();
		bean.setCfDitta(getStringReqParameter(request, null, ParametriServletSubappalti.FIELD_NAME_CF_DITTA));
		bean.setFlagDittaSubEstera(getStringReqParameter(request, null, ParametriServletSubappalti.FIELD_FLAG_DITTA_SUB_ESTERA)); //MEV 36771 3.04.8.1
		logger.debug("*******************************************"+ParametriServletSubappalti.FIELD_NAME_CF_DITTA);
		bean.setDataAutorizzazione(getStringReqParameter(request, null, ParametriServletSubappalti.FIELD_NAME_DATA_AUTORIZZAZIONE));
		bean.setDataFineRecord(getTimestampReqParameter(request, null, ParametriServletSubappalti.FIELD_NAME_DATA_FINE_RECORD));
		bean.setDataInizioRecord(getTimestampReqParameter(request, null, ParametriServletSubappalti.FIELD_NAME_DATA_INIZIO_RECORD));
		bean.setDataInizioAggiudicazione(dataInizioAggiudicazione);
		bean.setIdAggiudicazione(idAggiudicazione);
		bean.setIdRecord(getLongReqParameter(request, -1, ParametriServletSubappalti.FIELD_NAME_ID_RECORD));
		bean.setImportoEffettivo(getBigDecimalReqParameter(request, null, ParametriServletSubappalti.FIELD_NAME_IMPORTO_EFFETTIVO));
		bean.setImportoPresunto(getBigDecimalReqParameter(request, null, ParametriServletSubappalti.FIELD_NAME_IMPORTO_PRESUNTO));
		bean.setOggettoSubappalto(getStringReqParameter(request, null, ParametriServletSubappalti.FIELD_NAME_OGGETTO_SUBAPPALTO));
		bean.setIdCategoria(getStringReqParameter(request, null, ParametriServletSubappalti.FIELD_NAME_ID_CATEGORIA));
		bean.setIdCpv(getStringReqParameter(request, null, ParametriServletSubappalti.FIELD_NAME_ID_CPV));
		bean.setSubappaltatori(getSubappaltatori(request));
		bean.setSubappaltatoriString(getStringReqParameter(request,null,PSBD.FIELD_NAME_AGG_LISTA_GRUPPI));
		
		//gm nuovo codice 3.0
		bean.setCfAggiudicatario(getStringReqParameter(request, null, ParametriServletSubappalti.FIELD_NAME_CF_AGGIUDICATARIO));
        //gm fine nuovo codice 3.0
		
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(SubappaltiBean.class, bean));
		return bean;
	}
	
	
	
	/***************************************************************************************************************
	 * Gestisce il salvataggio 
	 * @param bean SubappaltiBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int save(SubappaltiBean bean, String cfUtente)throws ActionException{
		String mtd = "save";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(SubappaltiBean.class, bean));
		SubappaltiManager sManager = new SubappaltiManager(connection,logger);
		try{
			if(bean.getIdRecord() < 1){
				sManager.insert(bean, cfUtente);
				return 1;
			}else{
				if (!sManager.existSubappalti(bean.getIdRecord(), bean
						.getDataInizioRecord()))
					throw new ActionException("Scheda inesistente");
				return sManager.save(bean, cfUtente);
			}
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/**********************************************************************************************
	 * Gestisce la conferma
	 * @param bean SubappaltiBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int confirm(SubappaltiBean bean, String cfUtente)throws ActionException{
		String mtd = "confirm";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(SubappaltiBean.class, bean));
		SubappaltiManager sManager = new SubappaltiManager(connection,logger);
		try{
			if(!sManager.existSubappalti(bean.getIdRecord(), bean.getDataInizioRecord()))
				throw new ActionException("Scheda inesistente");
			
			return sManager.confirm(bean, cfUtente);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/**********************************************************************************************
	 * Gestisce la richiesta di annullamento
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public Timestamp richiediAnnullamento(RichiestaAnnullamento bean)throws ActionException {
		String idRecord = bean.getId_record();
		Timestamp dataInizioRecord = bean.getData_inizio_record();
		String idLotto = bean.getId_lotto();
		String blocco = bean.getBlocco();
		String cfUtente = bean.getRichiedente();
		try {
			Timestamp nuovaDataInizio = (new SubappaltiManager(connection, logger).copyRecord(Long.parseLong(idRecord), dataInizioRecord));
		
			if (nuovaDataInizio != null) {
		
				logger.debug("Data nuova: " + nuovaDataInizio);
				if (super.bsa.scriviAnnullamento(bean)){
					List<Object> attributiChiave = new ArrayList<Object>();
					attributiChiave.add(idRecord);
					attributiChiave.add(dataInizioRecord);
					LogBloccoDatiManager.loggingCANCELREQ(connection, logger, cfUtente, blocco, attributiChiave);
					return nuovaDataInizio;
				}
			}
		
			return null;
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
	}
	
	/**********************************************************************************************
	 * Gestisce la richiesta di cancllazione
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public void richiediCancellazione(RichiestaAnnullamento bean)throws ActionException {
		
		String idRecord = bean.getId_record();
		Timestamp dataInizioRecord = bean.getData_inizio_record();

		try {
		
		
			if (super.bsa.scriviAnnullamento(bean)){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idRecord);
				attributiChiave.add(dataInizioRecord);
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
	
	/*******************************************************************************************************
	 * Carica la lista dei Subappalti associati all'aggiudiazione
	 * @param idAggiudicazione long 
	 * @param dataInizioAggiudicazione Timestamp
	 * @return List&lt;SubappaltiBean&gt;
	 * @throws ActionException
	 */ //TICKET ALM - 3.04.3 #4198
	public List<SubappaltiBean> loadAllByAgg(long idAggiudicazione , Timestamp dataInizioAggiudicazione)throws ActionException{
		SubappaltiManager sManager = new SubappaltiManager(connection , logger);
		List<SubappaltiBean> resT = new ArrayList<SubappaltiBean>();
		List<SubappaltiBean> res = new ArrayList<SubappaltiBean>();
		try{
			resT = sManager.loadMany(idAggiudicazione, dataInizioAggiudicazione);
			for (int i=0;i<resT.size();i++) {
				SubappaltiBean bean = resT.get(i);
				bean.setSubappaltatoriString(creaDitteSubappaltatriciString(bean.getSubappaltatori()));
				res.add(bean);
			}
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
		
		return res;
	}
		
	/************************************************************************************************
	 * Carica il subappalto individuato dai parametri di ingresso
	 * @param idRecord long
	 * @param dataInizioRecord Timestamp
	 * @return SubappaltiBean 
	 * @throws ActionException
	 */
	public SubappaltiBean loadOne(long idRecord , Timestamp dataInizioRecord)throws ActionException{
		SubappaltiBean bean = loadOne(idRecord,dataInizioRecord,null);
		//TICKET ALM - 3.04.3 #4198
		bean.setSubappaltatoriString(creaDitteSubappaltatriciString(bean.getSubappaltatori()));
		//FINE TICKET ALM - 3.04.3 #4198
		return bean;
		}
	
	//TICKET ALM - 3.04.3 #4198
	private String creaDitteSubappaltatriciString(List<SubappaltatoreBean> subappaltatori) {
		String ditteSubStr = "";
	try {
		for (int i=0; i< subappaltatori.size(); i++) {
			SubappaltatoreBean bean = subappaltatori.get(i);
			ditteSubStr+=bean.getSoggettoPartecipante().getDenominazione()+"|";
			ditteSubStr+=bean.getSoggettoPartecipante().getCodiceFiscale()+"|";
			ditteSubStr+=bean.getSoggettoPartecipante().getId_stato()+"|";
			ditteSubStr+=bean.getSoggettoPartecipante().getIdSoggettoPartecipante()+"|";
			ditteSubStr+=bean.getSoggettoPartecipante().getDataInizioSogg().toString()+"|~";
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
		return ditteSubStr;
	}

	
	/*************************************************************************************************
	 * Carica il subappalto individuato dai parametri di ingresso
	 * @param idRecord long
	 * @param datainizioRecord Timestamp
	 * @param newDataInizioRecord Timestamp
	 * @return SubappaltiBean 
	 * @throws ActionException
	 */
	public SubappaltiBean loadOne(long idRecord, Timestamp datainizioRecord , Timestamp newDataInizioRecord)throws ActionException{
		if(newDataInizioRecord == null)newDataInizioRecord = datainizioRecord;
		SubappaltiManager sManager = new SubappaltiManager(connection , logger);
		try{
			return sManager.loadOne(idRecord,newDataInizioRecord);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
	}
	
	/***************************************************************************************************
	 * Carica le categorie 
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> laodCategorie(Object o)throws ActionException{
		SubappaltiManager sManager = new SubappaltiManager(connection,logger);
		try{
			return sManager.laodCategorie(o);
		}catch(SQLException e){
			throw new ActionException(e);
		}
	}
	public AggiudicazioneBean getAggiudicazione(long idAggiudicazione,Timestamp dataInizioAgg) {
		return super.bsa.getAggiudicazione(idAggiudicazione, dataInizioAgg);
	}
	
	public InfoComuniBean getInfoComuni(long idInfoComuni,Timestamp dataInizioInfo) {
		return super.bsa.getInfoComuni(idInfoComuni, dataInizioInfo);
	}
	/***************************************************************************************************
	 * Craica il Bean di Inizio Lavori associato all'aggiudicazione
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
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
		return inizioLavori;
	}
	//gm nuovo codice 3.0
	/*******************************************************************************************************
	 * Carica la lista degli Aggiudicatari associati all'aggiudiazione
	 * @param idAggiudicazione long 
	 * @param dataInizioAggiudicazione Timestamp
	 * @return List&lt;AggiudicatarioBean&gt;
	 * @throws ActionException
	 */
	/**/
	public List<AggiudicatarioBean> getAggiudicatari(long idAggiudicazione , Timestamp dataInizioAggiudicazione)throws ActionException{
		//List <AggiudicatarioBean> aggiudicatari = new ArrayList<AggiudicatarioBean>();
		AggiudicatarioManager am = new AggiudicatarioManager(connection , logger);
		try{
			return am.loadMany(idAggiudicazione, dataInizioAggiudicazione, false);
		}catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e.getMessage());
			//e.printStackTrace();
			throw new ActionException(e);
		}
	}
	//gm fine nuovo codice 3.0
	
	/***************************************************************************************
	 * TICKET ALM - 3.04.3 #4198
	 * Ottine la lista degli subaffidatari contenuti all'interno della request. 
	 *  
	 */ 
	public List<SubappaltatoreBean> getSubappaltatori(HttpServletRequest request) throws ActionException{
  
		ArrayList<SubappaltatoreBean> subappaltatori = new ArrayList<SubappaltatoreBean>();

		String ditteSubStr = getStringReqParameter(request,null,PSBD.FIELD_NAME_AGG_LISTA_GRUPPI);
	    
		if(ditteSubStr != null && !"".equals(ditteSubStr)) {
		   String[] listaDitte = ditteSubStr.split("~");
		   if(listaDitte.length>=1) {
			   for(int i=0;i<listaDitte.length;i++) {
				   String dittaStr = listaDitte[i];
				   String[] fields = dittaStr.split("\\|");
				   if(fields.length>=5) {
					   SubappaltatoreBean subbappaltatoreBean = new SubappaltatoreBean();
					   SoggettoPartecipanteBean soggPB = new SoggettoPartecipanteBean();
					   soggPB.setDenominazione(fields[0]);
					   soggPB.setCodiceFiscale(fields[1]);
					   soggPB.setId_stato(fields[2]);
					   soggPB.setIdSoggettoPartecipante(Long.parseLong(fields[3]));
					   soggPB.setDataInizioSogg( PageHelper.parseTime(fields[4]));
					   subbappaltatoreBean.setSoggettoPartecipante(soggPB);
					   subappaltatori.add(subbappaltatoreBean);
				   }
			   }
		   }
		}
		
		return subappaltatori;
	}

	
  
	
	public Map<String, String> loadTipoAggiudicatario(Object o) throws ActionException{
		
	   return this.sasa.loadTipoAggiudicatario(o);
	}
         
}
