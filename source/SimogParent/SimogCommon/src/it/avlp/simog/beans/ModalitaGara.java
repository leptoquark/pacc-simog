package it.avlp.simog.beans;

public class ModalitaGara {
	
	private long idModalitaGara;
	private String descrizione;
	private String dataFineValidita;
	private String dataUltimaModifica;
	
	public String getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(String dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	public String getDataUltimaModifica() {
		return dataUltimaModifica;
	}
	public void setDataUltimaModifica(String dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public long getIdModalitaGara() {
		return idModalitaGara;
	}
	public void setIdModalitaGara(long idModalitaGara) {
		this.idModalitaGara = idModalitaGara;
	}
}
