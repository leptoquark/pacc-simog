package it.avlp.simog.beans.accordi;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.common.servlet.PSBD;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AccordoBean {
	
	private long idAccordo;
	private Timestamp dataInizioAccordo;
	private Timestamp dataFineAccordo;
	private long idStato;
	private String descrizioneStato;
	private long idAggiudicazione;
	private Timestamp dataInizioAggiudicazione;
	private String dataAccordo;
	private BigDecimal oneriDerivanti;
	private String oneriDerivantiStr;
	private int numeroRiserve; 
	private boolean okCancellazione ;
	
	public boolean isOkCancellazione() {
		return okCancellazione;
	}
	public void setOkCancellazione(boolean okCancellazione) {
		this.okCancellazione = okCancellazione;
	}
	private String idLocale;
	
	public String getIdLocale() {
		return idLocale;
	}

	public void setIdLocale(String idLocale) {
		this.idLocale = idLocale;
	}

	/**
	 * Getter e Setter dei parametri della classe
	 */
	
	public long getIdAccordo() {
		return idAccordo;
	}
	public void setIdAccordo(long idAccordo) {
		this.idAccordo = idAccordo;
	}
	public Timestamp getDataInizioAccordo() {
		return dataInizioAccordo;
	}
	public void setDataInizioAccordo(Timestamp dataInizioAccordo) {
		this.dataInizioAccordo = dataInizioAccordo;
	}
	public Timestamp getDataFineAccordo() {
		return dataFineAccordo;
	}
	public void setDataFineAccordo(Timestamp dataFineAccordo) {
		this.dataFineAccordo = dataFineAccordo;
	}
	public long getIdStato() {
		return idStato;
	}
	public void setIdStato(long idStato) {
		this.idStato = idStato;
	}
	public String getDescrizioneStato() {
		return descrizioneStato;
	}
	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	public long getIdAggiudicazione() {
		return idAggiudicazione;
	}
	public void setIdAggiudicazione(long idAggiudicazione) {
		this.idAggiudicazione = idAggiudicazione;
	}
	public Timestamp getDataInizioAggiudicazione() {
		return dataInizioAggiudicazione;
	}
	public void setDataInizioAggiudicazione(Timestamp dataInizioAggiudicazione) {
		this.dataInizioAggiudicazione = dataInizioAggiudicazione;
	}
	public void setDataAccordo(String dataAccordo) {
		this.dataAccordo = dataAccordo;
	}
	public String getDataAccordo() {
		return dataAccordo;
	}
	public BigDecimal getOneriDerivanti() {
		return oneriDerivanti;
	}
	public void setOneriDerivanti(BigDecimal oneriDerivanti) {
		this.oneriDerivanti = oneriDerivanti;
	}
	public int getNumeroRiserve() {
		return numeroRiserve;
	}
	public void setNumeroRiserve(int numeroRiserve) {
		this.numeroRiserve = numeroRiserve;
	}
	
	// *****************************************************************
	// *****************************************************************
	
	/****************************************************************************************
	 * Il metodo verifica se l'id stato equivale a Confermato.
	 * @return boolean - True se lo stato � confermato, False altrimenti
	 */
	public boolean isConfirmed(){
			
			return idStato == StatiScheda.CONFERMATO;
			
		}
	/****************************************************************************************
	 * Il metodo determina se � stata fatta una richiesta di annullamento o meno.
	 * @return boolean -  True se � stata fatta una richiesta di annullamento, False altrimenti
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
	
	public String getOneriDerivantiStr() {
		return oneriDerivantiStr;
	}
	public void setOneriDerivantiStr(String oneriDerivantiStr) {
		this.oneriDerivantiStr = oneriDerivantiStr;
	}
	

	
}
