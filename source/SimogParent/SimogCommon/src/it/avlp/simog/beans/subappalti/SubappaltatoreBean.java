package it.avlp.simog.beans.subappalti;

import java.sql.Timestamp;

import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
//TICKET ALM - 3.04.3
public class SubappaltatoreBean {

	private long idDitteSubappaltatrici = -1;
	private Timestamp dataInizio;
	private Timestamp dataFine;
	private long idSubappalto;
	private Timestamp dataInizioSubappalto;
	private SoggettoPartecipanteBean soggettoPartecipante;
	
	public long getIdDitteSubappaltatrici() {
		return idDitteSubappaltatrici;
	}
	public void setIdDitteSubappaltatrici(long idDitteSubappaltatrici) {
		this.idDitteSubappaltatrici = idDitteSubappaltatrici;
	}
	public Timestamp getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Timestamp getDataFine() {
		return dataFine;
	}
	public void setDataFine(Timestamp dataFine) {
		this.dataFine = dataFine;
	}
	public long getIdSubappalto() {
		return idSubappalto;
	}
	public void setIdSubappalto(long idSubappalto) {
		this.idSubappalto = idSubappalto;
	}
	public Timestamp getDataInizioSubappalto() {
		return dataInizioSubappalto;
	}
	public void setDataInizioSubappalto(Timestamp dataInizioSubappalto) {
		this.dataInizioSubappalto = dataInizioSubappalto;
	}
	public SoggettoPartecipanteBean getSoggettoPartecipante() {
		return soggettoPartecipante;
	}
	public void setSoggettoPartecipante(SoggettoPartecipanteBean soggettoPartecipante) {
		this.soggettoPartecipante = soggettoPartecipante;
	}


	
	
 
}
