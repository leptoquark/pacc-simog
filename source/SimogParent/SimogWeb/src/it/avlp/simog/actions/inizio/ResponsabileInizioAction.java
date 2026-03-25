package it.avlp.simog.actions.inizio;

import it.avcp.simog.managers.inizio.ResponsabileInizioManager;
import it.avlp.simog.actions.BaseAction;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.common.action.ResponsabileInizioSharedAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletInizioLavori;
import it.avlp.simog.common.servlet.ParametriServletRubrica;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.rubricamanager.RubricaResponsabiliManager;
import it.avlp.simog.util.Base64Coder;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class ResponsabileInizioAction extends BaseAction{
	public static String CLAZZ = "ResponsabileInizioAction";
	
	public ResponsabileInizioSharedAction risa;
	public ResponsabileInizioAction(Connection activeConnection,
			Logger logger) {
		super(activeConnection, logger);
		this.risa = new ResponsabileInizioSharedAction(activeConnection, logger);
	}
	
	
	/**
	 * Legge dal DB i dati relativi ai responsabili
	 * @param idInizioLavori long
	 * @param dataInizioLavori Timestamp
	 * @param ignoraStato TODO
	 * @return List&lt;ResponsabileBean&gt;
	 * @throws ActionException
	 */
	public List<ResponsabileBean> loadMany(long idInizioLavori,Timestamp dataInizioLavori, boolean ignoraStato) throws ActionException {
		return this.risa.loadMany(idInizioLavori, dataInizioLavori, ignoraStato);
	}
	 
	/**
	 * @param responsabili List&lt;ResponsabileBean&gt;
	 * @param idInizioLavori long
	 * @param dataInizioLavori Timestamp
	 * @throws ActionException
	 */
	public void save(List<ResponsabileBean> responsabili,long idInizioLavori,Timestamp dataInizioLavori) throws ActionException {
		this.risa.save(responsabili, idInizioLavori, dataInizioLavori);
	}
	
	/************************************************************************************************
	 * Salva nel DB i dati inseriti dall'utente e conferma
	 * @param responsabili : List&lt;ResponsabileBean&gt;
	 * @param idInizioLavori long
	 * @param dataInizioLavori Timestamp
	 * @throws ActionException
	 */
	public void confirm(List<ResponsabileBean> responsabili,long idInizioLavori,Timestamp dataInizioLavori) throws ActionException {
		this.risa.confirm(responsabili, idInizioLavori, dataInizioLavori);
	}
	
	
	/*********************************************************************************************************
	 * Gestisce la richiesta di annullamento e storicizzazione dei dati
	 * @param bean RichiestaAnnullamento
	 * @param datavecchia Timestamp
	 * @return boolean
	 * @throws ActionException
	 */
	public boolean richiediAnnullamento(RichiestaAnnullamento bean,Timestamp datavecchia) throws ActionException {
		return this.risa.richiediAnnullamento(bean, datavecchia);
	}
	
	/******************************************************************************************
	 * Ottiene la lista dei responsabili
	 * @param request
	 * @return List&lt;ResponsabileBean&gt;
	 */
	public List<ResponsabileBean> getBean(HttpServletRequest request) {

		int nrRighe = 0;
		
		
		nrRighe = getIntReqParameter(request, 0, PSBD.NR_RIGHE_RESPONSABILI);
		ArrayList<ResponsabileBean> responsabili = new ArrayList<ResponsabileBean>(nrRighe);
		
		String prefix = null;
		
		prefix = "row" + PSBD.RESPONSABILE;
		

		long idInizioLavori = getLongReqParameter(request, 0, ParametriServletInizioLavori.ID_INIZIO_LAVORI);
		Timestamp dataInizioLavori =getTimestampReqParameter(request, null, ParametriServletInizioLavori.DATA_INIZIO_LAVORI);
		int daleggere = nrRighe;
		int i = 0;
		ResponsabileBean nuovoResponsabile = null;
		while(daleggere>0){
			Long idResp = null;
			int idruolo = 0;
			String desRuolo = null;
			Timestamp dataInizioRes = null;
			String nomeRes= null,cognomeRes= null,codiceFisRes = null; 
			idResp = getLongReqParameter(request, -1, prefix + i + PSBD.FIELD_NAME_ID_RESPONSABILE);
			idruolo = getIntReqParameter(request,0,prefix + i + PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE);
			dataInizioRes = getTimestampReqParameter(request, null, prefix + i + PSBD.FIELD_NAME_DATA_INIZIO_RES);
			nomeRes = getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_NOME_RESPONSABILE);
			cognomeRes = getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_COGNOME_RESPONSABILE);
			codiceFisRes = getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE);
			desRuolo = getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE);
			if(idResp > 0){
				nuovoResponsabile = new ResponsabileBean();
				nuovoResponsabile.setIdScheda(idInizioLavori);
				nuovoResponsabile. setDataInizioScheda(dataInizioLavori);
				nuovoResponsabile.setIdRuolo(idruolo);
				nuovoResponsabile.setDescrizioneRuolo(desRuolo);
				 
				SoggettoResponsabileBean srb = new SoggettoResponsabileBean();
				srb.setNome(nomeRes);
				srb.setCognome(cognomeRes);
				srb.setCodiceFiscaleResponsabile(codiceFisRes);
				srb.setIdResponsabile(idResp);
				srb.setDataInizioRes(dataInizioRes);
					
	    		// PP se esistono variazioni anagrafiche le memorizzo
	    		String datiAnag = getStringReqParameter(request, null,prefix + i + PSBD.FIELD_NAME_ANAG);
	    		if (datiAnag != null && !"".equals(datiAnag) && !"*".equals(datiAnag)){
	    			
	    			String [] val = Base64Coder.decodeString(datiAnag).split(PSBD.SEP_VARANAG_S,-1);
	    			
	    			srb.setIdResponsabile(Long.parseLong(val[0]));
	    			srb.setCodiceFiscaleResponsabile(val[1]);
	    			srb.setCognome(val[2]);
	    			srb.setNome(val[3]);
	    			srb.setTelefono(val[4]);
	    			srb.setFax(val[5]);
	    			srb.setEmail(val[6]);
	    			srb.setIndirizzo(val[7]);
	    			srb.setCap(val[8]);
	    			srb.setComuneIstat(val[9]);
	    			
	    			// setto il flag per indicare la successiva modifica
	    			srb.setModifica(true);
	    		}
					
				nuovoResponsabile.setSoggettoResponsabile(srb);
				
				logger.debug("getBean: " + ObjectIntrospector.propertiesInfo(ResponsabileBean.class, nuovoResponsabile));
				responsabili.add(nuovoResponsabile);
				
			}
			daleggere--;
			i++;
		}
		responsabili.trimToSize();
		return responsabili;	
	}
	
	/************************************************************************************************
	 * Effettua la ricerca per codice fiscale, nome  e cognome
	 * @param request HttpServletRequest
	 * @param tab String
	 * @return TableBean
	 * @throws ActionException
	 */
//	public TableBean cerca(HttpServletRequest request, String tab)throws ActionException{
//
//		RubricaResponsabiliManager rubMan = new RubricaResponsabiliManager(connection, logger);
//		
//		String COD_FISC = null;
//		String cognome = null;
//		String nome = null;
//		
//		
//		COD_FISC = getStringReqParameter(request, "", ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE);
//		cognome = getStringReqParameter(request, "", ParametriServletRubrica.FIELD_NAME_COGNOME);
//		nome = getStringReqParameter(request, "", ParametriServletRubrica.FIELD_NAME_NOME);
//		
//		TableBean result = null;
//		try {
//			//result = rubMan.getSoggettiPartecipantiRubTab(DENOM,COD_FISC);
//			result = rubMan.getSoggettiPartecipantiRubTab(cognome,nome,COD_FISC);
//			
//			sendMessage(request, Messaggi.SIMOG_AGGIUDICAZIONI_050);
//		} catch (Exception e) {
////			log come fatal demandato al chiamante
//			logger.error(e.getMessage());
//			//e.printStackTrace();
//			throw new ActionException(e);
//		} 
//		return result;
//	}
	
	/************************************************************************************************
	 * Carica i ruoli relativi alla sezione 
	 * @param sezione String
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadRuoliSezione(String sezione,Object o)throws ActionException{
		return this.risa.loadRuoliSezione(sezione, o);
	}
	

}
