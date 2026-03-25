package it.avlp.simog.beans.esecuzione;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AttiAggiuntiviBean {
	
	private long idAvanzamento;
	private Timestamp dataInizioAvanzamento;
	private String dataAtto;
	private String oggetto;
	private BigDecimal importo;
	private String dataAccordo;
	private BigDecimal oneri;
	private BigDecimal interessi;
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
	public String getDataAtto() {
		return dataAtto;
	}
	public void setDataAtto(String dataAtto) {
		this.dataAtto = dataAtto;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public String getDataAccordo() {
		return dataAccordo;
	}
	public void setDataAccordo(String dataAccordo) {
		this.dataAccordo = dataAccordo;
	}
	public BigDecimal getOneri() {
		return oneri;
	}
	public void setOneri(BigDecimal oneri) {
		this.oneri = oneri;
	}
	public BigDecimal getInteressi() {
		return interessi;
	}
	public void setInteressi(BigDecimal interessi) {
		this.interessi = interessi;
	}
	

}
