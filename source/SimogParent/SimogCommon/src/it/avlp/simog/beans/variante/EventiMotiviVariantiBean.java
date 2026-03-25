package it.avlp.simog.beans.variante;
import java.sql.Timestamp;
/**
 * Questo Bean contiene i dati relativi a una ROW della tabella EVENTI_MOTIVI_VARIANTI
 * piu la descrizione contenuta nella tabella MOTIVI_VARIANTI
 * 
 * **/

public class EventiMotiviVariantiBean {
	
	private Long idRecord;
	private Timestamp dataIniRecord;
	private Timestamp dataFinRecord;
	private Long idStato;
	/* fk */
	private Long idVariante;	
	private Timestamp dataIniVariante; 
	/* ref for motivi_variante */
	private Long idMotivoVariante;
	/* descrizione da motivi varianti */
	private String descrizione;
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Long getIdRecord() {
		return idRecord;
	}
	public void setIdRecord(Long idRecord) {
		this.idRecord = idRecord;
	}
	public Timestamp getDataIniRecord() {
		return dataIniRecord;
	}
	public void setDataIniRecord(Timestamp dataIniRecord) {
		this.dataIniRecord = dataIniRecord;
	}
	public Timestamp getDataFinRecord() {
		return dataFinRecord;
	}
	public void setDataFinRecord(Timestamp dataFinRecord) {
		this.dataFinRecord = dataFinRecord;
	}
	public Long getIdStato() {
		return idStato;
	}
	public void setIdStato(Long idStato) {
		this.idStato = idStato;
	}
	public Long getIdVariante() {
		return idVariante;
	}
	public void setIdVariante(Long idVariante) {
		this.idVariante = idVariante;
	}
	public Timestamp getDataIniVariante() {
		return dataIniVariante;
	}
	public void setDataIniVariante(Timestamp dataIniVariante) {
		this.dataIniVariante = dataIniVariante;
	}
	public Long getIdMotivoVariante() {
		return idMotivoVariante;
	}
	public void setIdMotivoVariante(Long idMotivoVariante) {
		this.idMotivoVariante = idMotivoVariante;
	}
	

}
