package it.anticorruzione.ted.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PUBBLICAZIONI")
public class Pubblicazioni implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_PUBBLICAZIONE")
	private Long idPubblicazione;
	
	@Column(name = "DATA_INIZIO_PUBB")
	private Date dataInizioPubb;
	
	@Column(name = "DATA_GUCE")
	private String dataGuce;
	
	@Column(name = "ID_STATO")
	private Long idStato;
	
	@Column(name = "NUMERO_GUCE")
	private String numeroGuce;

	public Long getIdPubblicazione() {
		return idPubblicazione;
	}

	public void setIdPubblicazione(Long idPubblicazione) {
		this.idPubblicazione = idPubblicazione;
	}

	public Date getDataInizioPubb() {
		return dataInizioPubb;
	}

	public void setDataInizioPubb(Date dataInizioPubb) {
		this.dataInizioPubb = dataInizioPubb;
	}

	public String getDataGuce() {
		return dataGuce;
	}

	public void setDataGuce(String dataGuce) {
		this.dataGuce = dataGuce;
	}

	public Long getIdStato() {
		return idStato;
	}

	public void setIdStato(Long idStato) {
		this.idStato = idStato;
	}

	public String getNumeroGuce() {
		return numeroGuce;
	}

	public void setNumeroGuce(String numeroGuce) {
		this.numeroGuce = numeroGuce;
	}
	
	
	
}
