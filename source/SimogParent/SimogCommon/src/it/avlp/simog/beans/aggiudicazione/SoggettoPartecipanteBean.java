package it.avlp.simog.beans.aggiudicazione;

import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.util.Base64Coder;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

/*********************************************************************************************
 * La classe definisce e gestisce i seguenti parametri per la modellazione del Soggetto Partecipante:
 * <ul>
 * <li>long : idSoggettoPartecipante
 * <li>Timestamp : dataInizioSogg
 * <li>String : codiceFiscale
 *  <li>String : denominazione
 *	 <li>String : cameraCommercio
 *	 <li>String : partitaIva
 *	 <li>String : civico
 *	 <li>String : cap
 *	 <li>String : provincia
 *	 <li>String : citta
 *	 <li>String : cfRappresentante
 *	 <li>String : nome
 *	 <li>String : cognome
 *	 <li>String : indirizzo
 * </ul>
 * con i relativi metodi di get e set
 * @author Steponweb
 *
 */
public class SoggettoPartecipanteBean {
	
	private long idSoggettoPartecipante;
	private Timestamp dataInizioSogg;
	private String codiceFiscale;
	private String denominazione;
	private String cameraCommercio;
	private String partitaIva;
	private String civico;
	private String cap;
	private String provincia;
	private String citta;
	private String cfRappresentante;
	private String nome;
	private String cognome;
	private String indirizzo;
	private String id_stato;
	private String flagEsteri;
	private boolean modifica;
	
	public Timestamp getDataInizioSogg() {
		return dataInizioSogg;
	}
	public void setDataInizioSogg(Timestamp dataInizioSogg) {
		this.dataInizioSogg = dataInizioSogg;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getDenominazione() {
		return denominazione;
	}
   public String getDenominazionePulita() {
      return denominazione == null ? null : denominazione.replaceAll("'", "" + (char) 180);
   }
	
   public String getDenominazionePulitaSl() {
      return denominazione == null ? null : denominazione.replaceAll("'", "\\\\'");
   }
   
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getCameraCommercio() {
		return cameraCommercio;
	}
	public void setCameraCommercio(String cameraCommercio) {
		this.cameraCommercio = cameraCommercio;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public String getCfRappresentante() {
		return cfRappresentante;
	}
	public void setCfRappresentante(String cfRappresentante) {
		this.cfRappresentante = cfRappresentante;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
	public long getIdSoggettoPartecipante() {
		return idSoggettoPartecipante;
	}
	public void setIdSoggettoPartecipante(long idSoggettoPartecipante) {
		this.idSoggettoPartecipante = idSoggettoPartecipante;
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
	public boolean isModifica() {
		return modifica;
	}
	public void setModifica(boolean modifica) {
		this.modifica = modifica;
	}
	
	public String getDatiModifica(){
		
		String ret="";
		
		if (modifica) {
			String [] dati = new String[14];
			
			// costruzione della stringa così come arriva dalla pagina
			dati[0] = String.valueOf(idSoggettoPartecipante).trim();
			dati[1] = codiceFiscale;
			dati[2] = denominazione;
			dati[3] = cameraCommercio;
			dati[4] = partitaIva;
			dati[5] = indirizzo;
			dati[6] = civico;
			dati[7] = citta;
			dati[8] = provincia;
			dati[9] = cap;
			dati[10] = cfRappresentante;
			dati[11] = cognome;
			dati[12] = nome;
			dati[13] = id_stato;
			
			ret = Base64Coder.encodeString(StringUtils.join(dati, PSBD.SEP_VARANAG));
		}
		return ret;
	}	
}
