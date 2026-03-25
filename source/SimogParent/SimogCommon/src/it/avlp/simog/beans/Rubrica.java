package it.avlp.simog.beans;

import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;

public class Rubrica {
	private long id_soggetto_partecipante;
	private String data_inizio_sogg;
	//private String data_fine_sogg;
	private String codice_fiscale;
	private String denominazione;
	private String camera_commercio;
	private String cf_rappresentante;
	private String partitaIva;
	private String indirizzo;
	private String civico;
	private String cap;
	private String provincia;
	private String citta;
	private String cognome;
	private String nome;
	private String id_stato;
	private String flagEsteri;
	
	private String data_fine;
	public String getCamera_commercio() {
		return camera_commercio;
	}
	public void setCamera_commercio(String camera_commercio) {
		this.camera_commercio = camera_commercio;
	}
	public String getCf_rappresentante() {
		return cf_rappresentante;
	}
	public void setCf_rappresentante(String cf_rappresentante) {
		this.cf_rappresentante = cf_rappresentante;
	}
	public String getCodice_fiscale() {
		return codice_fiscale;
	}
	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}
	public String getData_fine() {
		return data_fine;
	}
	public void setData_fine(String data_fine) {
		this.data_fine = data_fine;
	}
	public String getData_inizio_sogg() {
		return data_inizio_sogg;
	}
	public void setData_inizio_sogg(String data_inizio_sogg) {
		this.data_inizio_sogg = data_inizio_sogg;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	public long getId_soggetto_partecipante() {
		return id_soggetto_partecipante;
	}
	public void setId_soggetto_partecipante(long id_soggetto_partecipante) {
		this.id_soggetto_partecipante = id_soggetto_partecipante;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getId_stato() {
		return id_stato;
	}
	public void setId_stato(String id_stato) {
		this.id_stato = id_stato;
	}
	public String getFlagEsteri() {
		return flagEsteri;
	}
	public void setFlagEsteri(String flagEsteri) {
		this.flagEsteri = flagEsteri;
	}	
	/* warning non setto tutti i campi */

	/**********************************************************************************************
	 * Rubrica <b>converti</b> ( SoggettoPartecipanteBean )<br> 
	 * Converte un SoggettoPartecipanteBean in una Rubrica
	 * @param spb : SoggettoPartecipanteBean
	 * @return Rubrica
	 **********************************************************************************************/
	public static Rubrica converti(SoggettoPartecipanteBean spb){
		Rubrica r = new Rubrica();
		r.setCamera_commercio(spb.getCameraCommercio());
		r.setCap(spb.getCap());
		r.setCf_rappresentante(spb.getCfRappresentante());
		r.setCitta(spb.getCitta());
		r.setCivico(spb.getCivico());
		r.setCodice_fiscale(spb.getCodiceFiscale());
		r.setCognome(spb.getCognome());
		r.setDenominazione(spb.getDenominazione());
		r.setId_soggetto_partecipante(spb.getIdSoggettoPartecipante());
		r.setIndirizzo(spb.getIndirizzo());
		r.setNome(spb.getNome());
		r.setPartitaIva(spb.getPartitaIva());
		r.setProvincia(spb.getProvincia());
		r.setId_stato(spb.getId_stato());
		r.setFlagEsteri(spb.getFlagEsteri());
		
		if(spb.getDataInizioSogg()!=null)
			r.setData_inizio_sogg(spb.getDataInizioSogg().toString());
		return r;
	}
	
	
}
