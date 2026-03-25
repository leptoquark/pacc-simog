package it.avlp.simog.actions.inizio;

import it.avcp.simog.managers.inizio.PosizAggiudManager;
import it.avlp.simog.actions.BaseAction;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.Rubrica;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.inizio.PosizioneAggiudicatarioBean;
import it.avlp.simog.common.action.PosizioneAggiudicataroSharedAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletInizioLavori;
import it.avlp.simog.common.servlet.ParametriServletRubrica;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.rubricamanager.RubricaManager;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class PosizioneAggiudicataroAction extends BaseAction {
	public static String CLAZZ = "PosizioneAggiudicataroAction";
	
	public PosizioneAggiudicataroSharedAction pasa;
	
	public PosizioneAggiudicataroAction(Connection activeConnection,
			Logger logger) {
		super(activeConnection, logger);
		this.pasa = new PosizioneAggiudicataroSharedAction(activeConnection, logger);
	}
	
	/**************************************************************************************************
	 * restituisce la lista delle posizioni degli aggiudicatari
	 * @param request HttpServletRequest
	 * @return List&lt;PosizioneAggiudicatarioBean&gt;
	 */
	public List<PosizioneAggiudicatarioBean> getBean(HttpServletRequest request){
		int nrPosizioni = getIntReqParameter(request, 0, ParametriServletInizioLavori.NR_RIGHE_POSIZIONI);
		ArrayList<PosizioneAggiudicatarioBean> posizioniAgg = new ArrayList<PosizioneAggiudicatarioBean>();
		
//		logger.debug( "--------------!!!!!-------------------- ");;
//		
//		for ( Enumeration e = request.getParameterNames(); e.hasMoreElements(); ) {
//			String currentParamName = (String) e.nextElement();
//			String[] currentValue = request.getParameterValues(currentParamName);
//			
//			for ( int i = 0; i < currentValue.length; i++ ) {
//				logger.debug("* Parametro [" + currentParamName + "] valore [" + i + "] [" + currentValue[i] + "]");
//			}
//		}
//		logger.debug( "---------------!!!!!---------------------- ");
		String prefix = "row" + ParametriServletInizioLavori.POSIZIONE_AGGIUDICATARIO;
		long idInizioLavori = getLongReqParameter(request, -1, ParametriServletInizioLavori.ID_INIZIO_LAVORI);
		Timestamp dataInizioLavori = getTimestampReqParameter(request, null, ParametriServletInizioLavori.DATA_INIZIO_LAVORI);
		int daleggere = nrPosizioni;
		int i = 0;
		PosizioneAggiudicatarioBean posBean = null;
		while(daleggere>0){

				
			long idSoggetto = getLongReqParameter(request, -1,  prefix + i + PSBD.FIELD_NAME_AGG_ID_SOGG_POSIZIONI);
			if(idSoggetto > 0){
				posBean = new PosizioneAggiudicatarioBean();
				posBean.setIdInizioLavori(idInizioLavori);
				posBean.setDataInizioLavori(dataInizioLavori);
				posBean.setCodiceCassa(getStringReqParameter(request,null, prefix + i + ParametriServletInizioLavori.FIELD_NAME_CODICE_CASSA));
				posBean.setCodiceINAIL(getStringReqParameter(request,null, prefix + i + ParametriServletInizioLavori.FIELD_NAME_CODICE_INAIL));
				posBean.setCodiceINPS(getStringReqParameter(request,null, prefix + i + ParametriServletInizioLavori.FIELD_NAME_CODICE_INPS));
				
				SoggettoPartecipanteBean nuovoSoggettoPartecipante = new SoggettoPartecipanteBean();
				nuovoSoggettoPartecipante.setIdSoggettoPartecipante(idSoggetto);
				nuovoSoggettoPartecipante.setDataInizioSogg(getTimestampReqParameter(request, null, prefix + i + PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG_POSIZIONI));
				nuovoSoggettoPartecipante.setDenominazione(getStringReqParameter(request,null,prefix + i + PSBD.FIELD_NAME_AGG_DENOMINAZIONE));
	/*UN-X-XX*/	nuovoSoggettoPartecipante.setCodiceFiscale(getStringReqParameter(request,null,prefix + i + PSBD.FIELD_NAME_COD_FISC_POSIZIONI));
	/*UN-X-XX*/	nuovoSoggettoPartecipante.setId_stato(getStringReqParameter(request,"",prefix + i + PSBD.FIELD_NAME_AGG_ID_PAESE));
				if (Costanti.CODICE_STATO_ITALIANO.equals(nuovoSoggettoPartecipante.getId_stato())) nuovoSoggettoPartecipante.setId_stato("");
				/*
				String s = getStringReqParameter(request,null,prefix + i + PSBD.FIELD_NAME_COD_FISC_POSIZIONI);
				if(s.contains("-")){
//					logger.debug("[!!!!!!!] -> "+s.substring(5));
//					logger.debug("[!!!!!!!] -> "+s.substring(0,2));
					nuovoSoggettoPartecipante.setCodiceFiscale(s.substring(5));
					nuovoSoggettoPartecipante.setId_stato(s.substring(0,2));
				}
				else{
					String idPaese = "idPaese"+i;
//					logger.debug("[!!!!!!!] -> "+s+" , "+idPaese+" - "+(String)request.getParameter(idPaese));	
					nuovoSoggettoPartecipante.setCodiceFiscale(s);
					nuovoSoggettoPartecipante.setId_stato((String)request.getParameter(idPaese));
//					logger.debug("["+i+"] - "+"["+nuovoSoggettoPartecipante.getId_stato()+"]");

				}
				*/
				//logger.debug("getBean: " + ObjectIntrospector.propertiesInfo(PosizioneAggiudicatarioBean.class, posBean));
				
				posBean.setSoggettoPartecipante(nuovoSoggettoPartecipante);
				logger.debug(ObjectIntrospector.propertiesInfo(SoggettoPartecipanteBean.class, nuovoSoggettoPartecipante));
				posizioniAgg.add(posBean);
			}
			daleggere--;
			i++;
		}
		posizioniAgg.trimToSize();
		return posizioniAgg;
	}
	
	
	/**
	 * metodo per il caricamento delle posizioni aggiudicatario legate all'id/datainizio di inizio lavori
	 * @param idInizioLavori long	
	 * @param dataInizioLavori Timestamp
	 * @param ignoraStato TODO
	 * @return List&lt;PosizioneAggiudicatarioBean&gt;
	 * @throws ActionException
	 */
	public List<PosizioneAggiudicatarioBean> loadMany(long idInizioLavori,Timestamp dataInizioLavori, boolean ignoraStato) throws ActionException {
		return this.pasa.loadMany(idInizioLavori, dataInizioLavori, ignoraStato);
	}
	 

	/**
	 * metodo che inserisci nel db la lista di posizioni aggiudicatario nel db
	 * @param aggiudicatari List&lt;PosizioneAggiudicatarioBean&gt;
	 * @param idInizioLavori long
	 * @param dataInizioLavori Timestamp
	 * @throws ActionException
	 */
	public void save(List<PosizioneAggiudicatarioBean> aggiudicatari,long idInizioLavori,Timestamp dataInizioLavori) throws ActionException {
		this.pasa.save(aggiudicatari, idInizioLavori, dataInizioLavori);
	}

	/**
	 * metodo che conferma le posizioni aggiudicaztario della lista in ingresso
	 * 
	 * @param aggiudicatari List&lt;PosizioneAggiudicatarioBean&gt;
	 * @param idInizioLavori long
	 * @param dataInizioLavori Timestamp
	 * @throws ActionException
	 */
	public void confirm(List<PosizioneAggiudicatarioBean> aggiudicatari,long idInizioLavori,Timestamp dataInizioLavori) throws ActionException {
		this.pasa.confirm(aggiudicatari, idInizioLavori, dataInizioLavori);
	}
	
	
	/******************************************************************************************************
	 * Gestisce la richiesta di annullamento
	 * @param bean RichiestaAnnullamento
	 * @param datavecchia Timestamp
	 * @return boolean
	 * @throws ActionException
	 */
	public boolean richiediAnnullamento(RichiestaAnnullamento bean,Timestamp datavecchia) throws ActionException {
		return this.pasa.richiediAnnullamento(bean, datavecchia);
	}
	
	
	
	//PRESI DA AGGIUDICATARIO SCHEDA A::..... 
	/***********************************************************************************
	 * Effettua la ricerca attraverso i campi denominazione Codice fiscale 
	 * @param request HttpServletRequest
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
//			result = rubMan.getSoggettiPartecipantiRubTab(rub.getDenominazione(), rub.getCodice_fiscale());
//			
//			sendMessage(request, Messaggi.SIMOG_AGGIUDICAZIONI_050);
//		} catch (SQLException e) {
////			log come fatal demandato al chiamante
//			logger.error(e.getMessage());
//			//e.printStackTrace();
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
	
	/*******************************************************************************************************
	 * Impsta lo stato dei filtri di ricerca
	 * @param request HttpServletRequest
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


}
