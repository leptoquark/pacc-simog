package it.avlp.simog.beans.stipula;

import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.VO;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.common.servlet.PSBD;

import java.sql.Timestamp;
import java.util.List;

public class StipulaBean implements VO{
	private long idStipula;
	private Timestamp dataInizioStipula;
	private Timestamp dataFineStipula;
	private String dataStipulaContratto;
	private String dataDecorrenza;
	private String dataScadenza;
	private long idAggiudicazione;
	private Timestamp dataInizioAggiudicazione;
	//private long idPubblicazione;
	//private Timestamp dataInizioPubblicazione;
	private int idStato;
	private String idLocale;
	private String descrizioneStato;
	private boolean okCancellazione;
	private PubblicazioneBean pubblicazione;
	
	public void setIdStipula(long idStipula){
		this.idStipula = idStipula;
	}
	public long getIdStipula(){
		return this.idStipula;
	}
	public void setDataInizioStipula(Timestamp dataInizioStipula){
		this.dataInizioStipula = dataInizioStipula;
	}
	public Timestamp getDataInizioStipula(){
		return this.dataInizioStipula;
	}
	public void setDataFineStipula(Timestamp dataFineStipula){
		this.dataFineStipula = dataFineStipula;
	}
	public Timestamp getDataFineStipula(){
		return this.dataFineStipula;
	}
	public void setDataStipulaContratto(String dataStipulaContratto){
		this.dataStipulaContratto = dataStipulaContratto;
	}
	public String getDataStipulaContratto(){
		return this.dataStipulaContratto;
	}
	public void setDataDecorrenza(String dataDecorrenza){
		this.dataDecorrenza = dataDecorrenza;
	}
	public String getDataDecorrenza(){
		return this.dataDecorrenza;
	}
	public void setDataScadenza(String dataScadenza){
		this.dataScadenza = dataScadenza;
	}
	public String getDataScadenza(){
		return this.dataScadenza;
	}
	public void setIdAggiudicazione(long idAggiudicazione){
		this.idAggiudicazione = idAggiudicazione;
	}
	public long getIdAggiudicazione(){
		return this.idAggiudicazione;
	}
	public void setDataInizioAggiudicazione(Timestamp dataInizioAggiudicazione){
		this.dataInizioAggiudicazione = dataInizioAggiudicazione;
	}
	public Timestamp getDataInizioAggiudicazione(){
		return this.dataInizioAggiudicazione;
	}
	public void setPubblicazione (PubblicazioneBean pubblicazione){
		this.pubblicazione = pubblicazione;
	}
	public PubblicazioneBean getPubblicazione(){
		return this.pubblicazione;
	}
	/*
	public void setIdPubblicazione(long idPubblicazione){
		this.idPubblicazione = idPubblicazione;
	}
	public long getIdPubblicazione(){
		return this.idPubblicazione;
	}
	public void setDataInizioPubblicazione(Timestamp dataInizioPubblicazione){
		this.dataInizioPubblicazione = dataInizioPubblicazione;
	}
	public Timestamp getDataInizioPubblicazione(){
		return this.dataInizioPubblicazione;
	}
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
	
    public boolean isConfirmed(){
		return idStato == StatiScheda.CONFERMATO;	
	}
	public int getIdStato() {
		return idStato;
	}
	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}
	public String getIdLocale() {
		return idLocale;
	}

	public void setIdLocale(String idLocale) {
		this.idLocale = idLocale;
	}
	public String getDescrizioneStato() {
		return descrizioneStato;
	}

	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	public boolean isOkCancellazione() {
		return okCancellazione;
	}
	public void setOkCancellazione(boolean okCancellazione) {
		this.okCancellazione = okCancellazione;
	}
}
