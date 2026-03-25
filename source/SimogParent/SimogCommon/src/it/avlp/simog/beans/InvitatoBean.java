package it.avlp.simog.beans;

import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;

import java.sql.Timestamp;

public class InvitatoBean {
	
	private long idInvitato;
	private Timestamp dataInizioInvitato;
	private SoggettoPartecipanteBean soggettoPartecipante;
	private long idStato;
	private long idGara;
	
	
	public long getIdInvitato() {
		return idInvitato;
	}
	public void setIdInvitato(long idInvitato) {
		this.idInvitato = idInvitato;
	}
	public Timestamp getDataInizioInvitato() {
		return dataInizioInvitato;
	}
	public void setDataInizioInvitato(Timestamp dataInizioInvitato) {
		this.dataInizioInvitato = dataInizioInvitato;
	}
	public SoggettoPartecipanteBean getSoggettoPartecipante() {
		return soggettoPartecipante;
	}
	public void setSoggettoPartecipante(
			SoggettoPartecipanteBean soggettoPartecipante) {
		this.soggettoPartecipante = soggettoPartecipante;
	}
	public long getIdStato() {
		return idStato;
	}
	public void setIdStato(long idStato) {
		this.idStato = idStato;
	}
	public long getIdGara() {
		return idGara;
	}
	public void setIdGara(long idGara) {
		this.idGara = idGara;
	}
	
	
   
}
