package it.avlp.simog.actions.collaudo;

import it.avcp.simog.managers.collaudo.ResponsabileCollManager;
import it.avlp.simog.actions.BaseAction;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.common.action.IncaricatiCollaudoSharedAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletCollaudo;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.util.Base64Coder;
import it.avlp.simog.util.ObjectIntrospector;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class IncaricatiCollaudoAction extends BaseAction{
	
	public static String CLAZZ = "IncaricatiCollaudoAction";

	public IncaricatiCollaudoSharedAction icsa;

	public IncaricatiCollaudoAction(Connection activeConnection,
			Logger logger) {
		super(activeConnection, logger);
		this.icsa = new IncaricatiCollaudoSharedAction(activeConnection, logger);
	}
	
	/******************************************************************************************************
	 * Carica la lista dei responsabili associati al collaudo 
	 * @param idCollaudo long
	 * @param dataInizioCollaudo Timestamp
	 * @param ignoraStato TODO
	 * @return List&lt;ResponsabileBean&gt;
	 * @throws ActionException
	 */
	public List<ResponsabileBean> loadMany(long idCollaudo , Timestamp dataInizioCollaudo, boolean ignoraStato) throws ActionException {
		return this.icsa.loadMany(idCollaudo, dataInizioCollaudo, ignoraStato);
	}
	
	/*****************************************************************************************************
	 * Gestisce il salvataggio della lista di <code>ResponsabileBean</code> inserendo per ogni responsabile 
	 * <code>idCollaudo</code> e <code>dataInizioCollaudo</code>
	 * @param responsabili List&lt;ResponsabileBean&gt;
	 * @param idCollaudo long
	 * @param dataInizioCollaudo Timestamp
	 * @throws ActionException
	 */
	public void save(List<ResponsabileBean> responsabili,long idCollaudo,Timestamp dataInizioCollaudo) throws ActionException {
		this.icsa.save(responsabili, idCollaudo, dataInizioCollaudo);
	}
	
	/*******************************************************************************************************
	 * Gestisce la conferma dei dati impostatondo lo stato dei record a Confermato
	 * @param responsabili List&lt;ReponsabileBean&gt;
	 * @param idCollaudo long
	 * @param dataInizioCollaudo Timestamp
	 * @throws ActionException
	 */
	public void confirm(List<ResponsabileBean> responsabili,long idCollaudo,Timestamp dataInizioCollaudo) throws ActionException {
		this.icsa.confirm(responsabili, idCollaudo, dataInizioCollaudo);
	}
	
	/*****************************************************************************************************
	 * Gestisce la richiesta di annullamento aggiornando lo storico
	 * @param bean RichiestaAnnullamento
	 * @param datavecchia Timestamp
	 * @return booelan
	 * @throws ActionException
	 */
	public boolean richiediAnnullamento(RichiestaAnnullamento bean,Timestamp datavecchia) throws ActionException {
		return this.icsa.richiediAnnullamento(bean, datavecchia);		
	}
	
	/**************************************************************************************************
	 * Ottiene la lista dei responsabili associati al collaudo. I dati del collaudo 
	 * sono ottenuti dalla <code>Request</code>. 
	 * <p>
	 * I responsabili ed i dati relativi sono passati come parametri nella request. I nome di tali parametri sono determinati 
	 * attraverso la seguente sintassi:
	 * <p>
	 *  <code>prefix + i + nome del parametro da ottenere. </code>
	 * <p>
	 * <ul>
	 * <li>dove prefix risulta essere : "rowIncaricato"
	 * <li>i  &egrave il solito indice che differenzia i vari parametri
	 * <li>dopo di che viene concatenato il nome del campo da voler prelevare.  
	 * </ul>
	 * <b>esempi:</b>
	 * <pre>
	 *     prefix + i + PSBD.FIELD_NAME_ID_RESPONSABILE
	 *     prefix + i + PSBD.FIELD_NAME_COGNOME_RESPONSABILE</pre>
	 * @param request HttpServletRequest
	 * @return List&lt;ResponsabileBean&gt;
	 */
	public List<ResponsabileBean> getBean(HttpServletRequest request) {

		int nrRighe = 0;
		
		
		nrRighe = getIntReqParameter(request, 0, PSBD.NR_RIGHE_RESPONSABILI);
		ArrayList<ResponsabileBean> responsabili = new ArrayList<ResponsabileBean>(nrRighe);
		
		String prefix = null;
		
		prefix = "row" + PSBD.RESPONSABILE;
		

		long idCollaudo = getLongReqParameter(request, 0, ParametriServletCollaudo.FIELD_NAME_ID_COLLAUDO);
		Timestamp dataInizioCollaudo =getTimestampReqParameter(request, null, ParametriServletCollaudo.FIELD_NAME_DATA_INIZIO_COLL);
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
				
				nuovoResponsabile.setIdScheda(idCollaudo);
				nuovoResponsabile. setDataInizioScheda(dataInizioCollaudo);
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
	    		if (datiAnag != null && !"*".equals(datiAnag) && !"*".equals(datiAnag)){
	    			
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
}
