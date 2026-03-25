package it.avlp.simog.actions.inizio;

import it.avlp.simog.actions.BaseAction;
import it.avlp.simog.actions.PubblicazioneAction;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.inizio.SchedaInizioLavori;
import it.avlp.simog.common.action.InizioLavoriSharedAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletInizioLavori;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.exception.ActionException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class InizioLavoriAction extends BaseAction {

	public InizioLavoriSharedAction ilsa;
	
	public InizioLavoriAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
		this.ilsa = new InizioLavoriSharedAction(activeConnection, logger);
	}
	
	/********************************************************************************************
	 * Gestisce il salvataggio 
	 * @param bean InizioLavoriBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int save(InizioLavoriBean bean,String cfUtente)throws ActionException{
		return this.ilsa.save(bean, cfUtente);
	}			
	
	
	/******************************************************************************************************
	 * Gestisce la conferma
	 * @param bean InizioLavoriBean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public int confirm(InizioLavoriBean bean,String cfUtente)throws ActionException{
		return this.ilsa.confirm(bean, cfUtente);
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
		return this.ilsa.load(idAggiudicazione, dataInizioAgg);
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
		return this.ilsa.loadById(id, dataInizio);
	}
	
	/***************************************************************************************************
	 * Gestisce la richiesta di cancellazione e la storicizzazione dei dati
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public void richiediCancellazione(RichiestaAnnullamento bean) throws ActionException {
		this.ilsa.richiediCancellazione(bean);
	}
	
	/**********************************************************************************************
	 * restituisce il bean di Inizio Lavori
	 * @param request HttpServletRequest
	 * @return InizioLavotiBean
	 * @throws ActionException
	 */
	public InizioLavoriBean getBean(HttpServletRequest request) throws ActionException{
		InizioLavoriBean ilb = new InizioLavoriBean();
		//dati aggiudicazione
		ilb.setIdAggiudicazione(getDatiGara(request).getIdAggiudicazione());
		ilb.setDataInizioAggiudicazione(getDatiGara(request).getDataInizioAggiudicazione());
		//dati pubblicazione
		ilb.setPubblicazione((new PubblicazioneAction(connection,logger)).getBean(request));
		//altri...
		ilb.setDataStipula(getStringReqParameter(request, null , ParametriServletInizioLavori.FIELD_NAME_DATA_STIPULA));
		ilb.setDataEsecutivita(getStringReqParameter(request, null, ParametriServletInizioLavori.FIELD_NAME_DATA_ESECUTIVITA));
		ilb.setImportoCauzione(getBigDecimalReqParameter(request, new BigDecimal(0), ParametriServletInizioLavori.FIELD_NAME_IMPORTO_CAUZIONE));
		ilb.setDataIniProgEsec(getStringReqParameter(request, null, ParametriServletInizioLavori.FIELD_NAME_DATA_INI_PROG_ESEC));
		ilb.setDataAppProgEsec(getStringReqParameter(request, null, ParametriServletInizioLavori.FIELD_NAME_DATA_APP_PROG_ESEC));
		ilb.setFlagFrazionata(getStringReqParameter(request, null, ParametriServletInizioLavori.FIELD_NAME_CONSEGNA_FRAZIONATA));
		ilb.setDataVerbaleCons(getStringReqParameter(request, null, ParametriServletInizioLavori.FIELD_NAME_DATA_VERB_PRIMA_CONSEGNA));
		ilb.setDataVerbaleDef(getStringReqParameter(request, null, ParametriServletInizioLavori.FIELD_NAME_DATA_VERB_CONSEGNA_DEF));
		ilb.setFlagRiserva(getStringReqParameter(request, null, ParametriServletInizioLavori.FIELD_NAME_CONSEGNA_RISERVA));
		ilb.setDataVerbaleInizio(getStringReqParameter(request, null, ParametriServletInizioLavori.FIELD_NAME_DATA_VERB_INIZIO));
		ilb.setDataTermine(getStringReqParameter(request, null, ParametriServletInizioLavori.FIELD_NAME_DATA_TERMINE));
		ilb.setIdInizioLavori(getLongReqParameter(request, -1, ParametriServletInizioLavori.ID_INIZIO_LAVORI));
		ilb.setDataInizioLavori(getTimestampReqParameter(request, null, ParametriServletInizioLavori.DATA_INIZIO_LAVORI));
		
		// PP B302.2.0
		if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive())
			ilb.setIdMotivoVarCO(getStringReqParameter(request, null, PSBD.FIELD_NAME_MOTIVO_CO));

		return ilb;
	}
	
	public AggiudicazioneBean getAggiudicazione(long idAggiudicazione,Timestamp dataInizioAgg){
		return this.bsa.getAggiudicazione(idAggiudicazione, dataInizioAgg);
	}
	
	public InfoComuniBean getInfoComuni(long idInfoComuni,Timestamp dataInizioInfo){
		return this.bsa.getInfoComuni(idInfoComuni, dataInizioInfo);
	}
	/*******************************************************************************************************
	 * Restituisce il bean della pubblicazione  
	 * @param idPubblicazione long
	 * @param dataInizioPubblicazione Timestamp
	 * @return PubblicazioneBan
	 * @throws ActionException
	 */
	public PubblicazioneBean getPubblicazione(long idPubblicazione,Timestamp dataInizioPubblicazione) throws ActionException{
		return this.ilsa.getPubblicazione(idPubblicazione, dataInizioPubblicazione);
	}

	/*****************************************************************************************************
	 * Carica le info pubblicazioni dal'avviso di aggiudicazione pubblicato, se esiste 
	 * @param idPubb long
	 * @param dataInizioPubb Timestamp
	 * @return void
	 * @throws ActionException
	 */
	public void loadPubbFromAvviso(long idPubb, Timestamp dataInizioPubb, InizioLavoriBean icb) throws ActionException{
		this.ilsa.loadPubbFromAvviso(idPubb, dataInizioPubb, icb);
	}

	public Timestamp annulla(Connection conn, RichiestaAnnullamento bean) throws ActionException {
		return this.ilsa.annulla(conn, bean);
	}

	public Timestamp gestisciVariazioniCO(SchedaInizioLavori saBean, RichiestaAnnullamento rab, String cfUtente)throws Exception{
		return this.ilsa.gestisciVariazioniCO(saBean, rab, cfUtente);
	}
}
