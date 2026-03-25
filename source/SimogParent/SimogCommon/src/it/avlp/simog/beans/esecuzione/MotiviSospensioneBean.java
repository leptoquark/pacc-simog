package it.avlp.simog.beans.esecuzione;

import java.sql.Timestamp;

public class MotiviSospensioneBean {
	private long idAvanzamento;
	private Timestamp dataInizioAvanzamento;
	private long idMotivo;
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
	public long getIdMotivo() {
		return idMotivo;
	}
	public void setIdMotivo(long idMotivo) {
		this.idMotivo = idMotivo;
	}
	

}
