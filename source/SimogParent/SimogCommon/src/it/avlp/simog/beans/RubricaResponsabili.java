package it.avlp.simog.beans;

import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;

import java.sql.Timestamp;

public class RubricaResponsabili {
	
	private long id_soggetto_responsabile;
	private String data_inizio_res;
	private String codice_fiscale_responsabile;
	private String cognome;
	private String nome;
	private String telefono;
	private String fax;
	private String email;
	private String data_fine_res;
	
	private String indirizzo;
	private String cap;
	private String comuneIstat;
	
	private String isEstero;
	
	public String getCodice_fiscale_responsabile() {
		return codice_fiscale_responsabile;
	}
	public void setCodice_fiscale_responsabile(String codice_fiscale_responsabile) {
		this.codice_fiscale_responsabile = codice_fiscale_responsabile;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getData_fine_res() {
		return data_fine_res;
	}
	public void setData_fine_res(String data_fine_res) {
		this.data_fine_res = data_fine_res;
	}
	public String getData_inizio_res() {
		return data_inizio_res;
	}
	public void setData_inizio_res(String data_inizio_res) {
		this.data_inizio_res = data_inizio_res;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public long getId_soggetto_responsabile() {
		return id_soggetto_responsabile;
	}
	public void setId_soggetto_responsabile(long id_soggetto_responsabile) {
		this.id_soggetto_responsabile = id_soggetto_responsabile;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	
	public String getComuneIstat() {
		return comuneIstat;
	}
	public void setComuneIstat(String comuneIstat) {
		this.comuneIstat = comuneIstat;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	public String getIsEstero() {
		return isEstero;
	}
	public void setIsEstero(String isEstero) {
		this.isEstero = isEstero;
	}
	/*************************************************************************************
	 * RubricaResponsabili <b>converti</b> ( SoggettoResponsabileBean )<br>
	 * Converte un SoggettoresponsabileBean in una RubricaResponsabili
	 * @param srb : SoggettoResponsabileBean
	 * @return RubricaResponsabili
	 *************************************************************************************/
	public static RubricaResponsabili converti(SoggettoResponsabileBean srb){
		RubricaResponsabili rr = new RubricaResponsabili();
		rr.setCap(srb.getCap());
		rr.setCodice_fiscale_responsabile(srb.getCodiceFiscaleResponsabile());
		rr.setCognome(srb.getCognome());
		rr.setComuneIstat(srb.getComuneIstat());
		rr.setEmail(srb.getEmail());
		rr.setFax(srb.getFax());
		rr.setId_soggetto_responsabile(srb.getIdResponsabile());
		rr.setIndirizzo(srb.getIndirizzo());
		rr.setNome(srb.getNome());
		rr.setTelefono(srb.getTelefono());	
		rr.setIsEstero(srb.getFlagSoggettoEstero());
		
		if(srb.getDataInizioRes() != null)
			rr.setData_inizio_res(srb.getDataInizioRes().toString());
		return rr;
	}

}
