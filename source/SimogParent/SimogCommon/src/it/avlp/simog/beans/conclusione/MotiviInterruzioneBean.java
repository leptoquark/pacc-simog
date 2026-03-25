package it.avlp.simog.beans.conclusione;

import java.sql.Timestamp;

public class MotiviInterruzioneBean {
	
	private Long idMotivoInterruzione;
	private String descrizione;
	private Timestamp dataFineValidita;
	private Timestamp dataUltimaModifica;
	public Long getIdMotivoInterruzione() {
		return idMotivoInterruzione;
	}
	public void setIdMotivoInterruzione(Long idMotivoInterruzione) {
		this.idMotivoInterruzione = idMotivoInterruzione;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Timestamp getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Timestamp dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	public Timestamp getDataUltimaModifica() {
		return dataUltimaModifica;
	}
	public void setDataUltimaModifica(Timestamp dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}
}
