package it.avlp.simog.beans;

import java.sql.Timestamp;

import it.avlp.simog.db.generated.CPV_LOTTO;

public class CpvLotto implements CPV_LOTTO {

	private long idCPVLotto;
	private Timestamp dataInizio;
	private Timestamp dataFin;
	private long idLotto;
	private String idCpv;
	private String descrizione;
	
	
	public long getIdCPVLotto() {
		return idCPVLotto;
	}
	public void setIdCPVLotto(long idCPVLotto) {
		this.idCPVLotto = idCPVLotto;
	}
	public Timestamp getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Timestamp getDataFin() {
		return dataFin;
	}
	public void setDataFin(Timestamp dataFin) {
		this.dataFin = dataFin;
	}
	public long getIdLotto() {
		return idLotto;
	}
	public void setIdLotto(long idLotto) {
		this.idLotto = idLotto;
	}
	public String getIdCpv() {
		return idCpv;
	}
	public void setIdCpv(String idCpv) {
		this.idCpv = idCpv;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	
	
}
