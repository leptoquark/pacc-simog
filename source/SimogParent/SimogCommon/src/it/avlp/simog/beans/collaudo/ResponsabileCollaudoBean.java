package it.avlp.simog.beans.collaudo;

import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;

import java.sql.Timestamp;

public class ResponsabileCollaudoBean {

	private long idResponsabile;
	private Timestamp dataInizioResponsabile;
	private int idRuolo;
	private long idCollaudo;
	private Timestamp dataInizioCollaudo;
	private Long idStato;

	private SoggettoResponsabileBean soggettoResp;

	public long getIdResponsabile() {
		return idResponsabile;
	}

	public void setIdResponsabile(long idResponsabile) {
		this.idResponsabile = idResponsabile;
	}

	public Timestamp getDataInizioResponsabile() {
		return dataInizioResponsabile;
	}

	public void setDataInizioResponsabile(Timestamp dataInizioResponsabile) {
		this.dataInizioResponsabile = dataInizioResponsabile;
	}

	public int getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(int idRuolo) {
		this.idRuolo = idRuolo;
	}

	public long getIdCollaudo() {
		return idCollaudo;
	}

	public void setIdCollaudo(long idCollaudo) {
		this.idCollaudo = idCollaudo;
	}

	public Timestamp getDataInizioCollaudo() {
		return dataInizioCollaudo;
	}

	public void setDataInizioCollaudo(Timestamp dataInizioCollaudo) {
		this.dataInizioCollaudo = dataInizioCollaudo;
	}

	public Long getIdStato() {
		return idStato;
	}

	public void setIdStato(Long idStato) {
		this.idStato = idStato;
	}

	public SoggettoResponsabileBean getSoggettoResp() {
		return soggettoResp;
	}

	public void setSoggettoResp(SoggettoResponsabileBean soggettoResp) {
		this.soggettoResp = soggettoResp;
	}

}
