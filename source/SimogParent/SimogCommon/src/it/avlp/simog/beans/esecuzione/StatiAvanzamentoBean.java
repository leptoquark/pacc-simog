package it.avlp.simog.beans.esecuzione;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class StatiAvanzamentoBean {
	private long idAvanzamento;
	private Timestamp dataInizioAvanzamento;
	private long idAggiudicazione;
	private Timestamp dataInizioAggiudicazione;
	private String fase;
	private String dataRaggiungimento;
	private String flagRitardo;
	private int numeroGiorniScost;
	private String flagPagamento;
	private int numeroGiorniSosp;
	private BigDecimal importoVarianti;
	private String altreMotivazioni;
	
	private String idLocale;
	
	public String getIdLocale() {
		return idLocale;
	}

	public void setIdLocale(String idLocale) {
		this.idLocale = idLocale;
	}

	public long getIdAvanzamento() {
		return idAvanzamento;
	}
	public void setIdAvanzamento(long idAvanzamento) {
		this.idAvanzamento = idAvanzamento;
	}
	public Timestamp getDataInizioAvanzamento() {
		return dataInizioAvanzamento;
	}
	public void setDataInizioAvanzamento(Timestamp dataInizioAvanzamento) {
		this.dataInizioAvanzamento = dataInizioAvanzamento;
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
	public String getFase() {
		return fase;
	}
	public void setFase(String fase) {
		this.fase = fase;
	}
	public String getDataRaggiungimento() {
		return dataRaggiungimento;
	}
	public void setDataRaggiungimento(String dataRaggiungimento) {
		this.dataRaggiungimento = dataRaggiungimento;
	}
	public String getFlagRitardo() {
		return flagRitardo;
	}
	public void setFlagRitardo(String flagRitardo) {
		this.flagRitardo = flagRitardo;
	}
	public int getNumeroGiorniScost() {
		return numeroGiorniScost;
	}
	public void setNumeroGiorniScost(int numeroGiorniScost) {
		this.numeroGiorniScost = numeroGiorniScost;
	}
	public String getFlagPagamento() {
		return flagPagamento;
	}
	public void setFlagPagamento(String flagPagamento) {
		this.flagPagamento = flagPagamento;
	}
	public int getNumeroGiorniSosp() {
		return numeroGiorniSosp;
	}
	public void setNumeroGiorniSosp(int numeroGiorniSosp) {
		this.numeroGiorniSosp = numeroGiorniSosp;
	}
	public BigDecimal getImportoVarianti() {
		return importoVarianti;
	}
	public void setImportoVarianti(BigDecimal importoVarianti) {
		this.importoVarianti = importoVarianti;
	}
	public String getAltreMotivazioni() {
		return altreMotivazioni;
	}
	public void setAltreMotivazioni(String altreMotivazioni) {
		this.altreMotivazioni = altreMotivazioni;
	}
	

}
