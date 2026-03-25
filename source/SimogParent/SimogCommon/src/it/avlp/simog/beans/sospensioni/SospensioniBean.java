package it.avlp.simog.beans.sospensioni;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.util.DateEvaluatorFactory;
import it.avlp.simog.util.PageHelper;

import java.sql.Timestamp;


public class SospensioniBean {
	private long idSospensione;
	private Timestamp dataInizioSosp ;
	private Timestamp dataFineSosp ;
	private long idStato;
	private long idAggiudicazione;
	private Timestamp dataInizioAggiudicazione;
	private String dataVerbSosp;
	private String dataVerbRipr;
	private long idMotivoSosp;
	private String flagSuperoTemp;
	private String flagRiserve;
	private String flagVerbale;
	private String descrizioneStato;
	private String descrizioneMotivo;
	private boolean okCancellazione ;
	
	private String idLocale;
	
	public String getIdLocale() {
		return idLocale;
	}

	public void setIdLocale(String idLocale) {
		this.idLocale = idLocale;
	}

	/***************************************************************************************************
	 * Il metodo si preoccupa di calcolare i giorni che intercorrono 
	 * tra la data di Fine Sospensione e quella di Inizio Sospensione
	 * @return int - restituisce l'intero associato ai giorni di differenza 
	 * 			 tra le date di inzio e fine sospensione
	 */
	public int getGiorniProroga()
	{
		if (dataVerbSosp == null || dataVerbRipr == null)
			return 0;
				
		// MOD PP SIMOG-40 29.04.2009 corretta impostazione dei parametri per il calcolo della differenza tra le date
		return DateEvaluatorFactory.getDateTimeDifference(PageHelper.getCalendarFromStringDate(PageHelper.getFormattedDBDate(dataVerbSosp)).getTime(), 
				PageHelper.getCalendarFromStringDate(PageHelper.getFormattedDBDate(dataVerbRipr)).getTime(), null,null, DateEvaluatorFactory.KEY_DAY);
	}
	
	public String getDescrizioneMotivo() {
		return descrizioneMotivo;
	}

	public void setDescrizioneMotivo(String descrizioneMotivo) {
		this.descrizioneMotivo = descrizioneMotivo;
	}

	/***************************************************************************************************************
	 * determina se sia stato richiesto un annullamento o meno
	 * 
	 * @return boolean - True se � stato rischiesto l'annullamento, false altrimenti 
	 */
	public boolean isRichAnn(){
		if(descrizioneStato != null)
			return (descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTO_ANNULLAMENTO));
		else return false;
	}
	public boolean isRichDelete(){
		if(descrizioneStato != null)
			return (descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTA_CANCELLAZIONE)
					|| descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTA_CANC_TOTALE));
		else return false;
	}
	
	public String getDescrizioneStato() {
		return descrizioneStato;
	}
	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	public long getIdSospensione() {
		return idSospensione;
	}
	public void setIdSospensione(long idSospensione) {
		this.idSospensione = idSospensione;
	}
	public Timestamp getDataInizioSosp() {
		return dataInizioSosp;
	}
	public void setDataInizioSosp(Timestamp dataInizioSosp) {
		this.dataInizioSosp = dataInizioSosp;
	}
	public Timestamp getDataFineSosp() {
		return dataFineSosp;
	}
	public void setDataFineSosp(Timestamp dataFineSosp) {
		this.dataFineSosp = dataFineSosp;
	}
	public long getIdStato() {
		return idStato;
	}
	public void setIdStato(long idStato) {
		this.idStato = idStato;
	}
	public long getIdAggiudicazione() {
		return idAggiudicazione;
	}
	public void setIdAggiudicazione(long idAggiudicazione) {
		this.idAggiudicazione = idAggiudicazione;
	}
	
	public String getDataVerbSosp() {
		return dataVerbSosp;
	}
	public void setDataVerbSosp(String dataVerbSosp) {
		this.dataVerbSosp = dataVerbSosp;
	}
	public String getDataVerbRipr() {
		return dataVerbRipr;
	}
	public void setDataVerbRipr(String dataVerbRipr) {
		this.dataVerbRipr = dataVerbRipr;
	}
	public long getIdMotivoSosp() {
		return idMotivoSosp;
	}
	public void setIdMotivoSosp(long idMotivoSosp) {
		this.idMotivoSosp = idMotivoSosp;
	}
	public String getFlagSuperoTemp() {
		return flagSuperoTemp;
	}
	public void setFlagSuperoTemp(String flagSuperoTemp) {
		this.flagSuperoTemp = flagSuperoTemp;
	}
	public String getFlagRiserve() {
		return flagRiserve;
	}
	public void setFlagRiserve(String flagRiserve) {
		this.flagRiserve = flagRiserve;
	}
	public String getFlagVerbale() {
		return flagVerbale;
	}
	public void setFlagVerbale(String flagVerbale) {
		this.flagVerbale = flagVerbale;
	}
	
	public boolean isConfirmed(){
		
		return idStato == StatiScheda.CONFERMATO;
		
	}

	public Timestamp getDataInizioAggiudicazione() {
		return dataInizioAggiudicazione;
	}

	public void setDataInizioAggiudicazione(Timestamp dataInizioAggiudicazione) {
		this.dataInizioAggiudicazione = dataInizioAggiudicazione;
	}

	public boolean isOkCancellazione() {
		return okCancellazione;
	}

	public void setOkCancellazione(boolean okCancellazione) {
		this.okCancellazione = okCancellazione;
	}
	
	
}
