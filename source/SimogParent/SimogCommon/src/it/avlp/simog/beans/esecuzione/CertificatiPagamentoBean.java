package it.avlp.simog.beans.esecuzione;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CertificatiPagamentoBean {
	
	private long idAvanzamento;
	private Timestamp dataInizioAvanzamento;
	private int numStatoAvanzamento;
	private String dataEmissione;
	private BigDecimal importoPagamento;
	private BigDecimal importoInteressi;
	
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
	public int getNumStatoAvanzamento() {
		return numStatoAvanzamento;
	}
	public void setNumStatoAvanzamento(int numStatoAvanzamento) {
		this.numStatoAvanzamento = numStatoAvanzamento;
	}
	public String getDataEmissione() {
		return dataEmissione;
	}
	public void setDataEmissione(String dataEmissione) {
		this.dataEmissione = dataEmissione;
	}
	public BigDecimal getImportoPagamento() {
		return importoPagamento;
	}
	public void setImportoPagamento(BigDecimal importoPagamento) {
		this.importoPagamento = importoPagamento;
	}
	public BigDecimal getImportoInteressi() {
		return importoInteressi;
	}
	public void setImportoInteressi(BigDecimal importoInteressi) {
		this.importoInteressi = importoInteressi;
	}

}
