package it.avlp.simog.beans.conclusione;

import java.sql.Timestamp;

public class ResponsabileFineBean {
	private long idUltimazione;
	private Timestamp dataInizioUltimazione;
	private long idResponsabile;
	private Timestamp dataInizioResponsabile;
	private int idRuolo;
	public long getIdUltimazione() {
		return idUltimazione;
	}
	public void setIdUltimazione(long idUltimazione) {
		this.idUltimazione = idUltimazione;
	}
	public Timestamp getDataInizioUltimazione() {
		return dataInizioUltimazione;
	}
	public void setDataInizioUltimazione(Timestamp dataInizioUltimazione) {
		this.dataInizioUltimazione = dataInizioUltimazione;
	}
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
	

}
