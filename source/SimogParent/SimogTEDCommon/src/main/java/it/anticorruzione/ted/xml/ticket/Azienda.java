/**
 * Web Service TED
 */
package it.anticorruzione.ted.xml.ticket;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Azienda {
	@XmlElement(name = "denominazione")
	private String denominazione;

	@XmlElement(name = "codice_fiscale")
	private String codiceFiscale;

	@XmlElement(name = "id_osservatorio")
	private String idOsservatorio;

	/**
	 * @return the denominazione
	 */
	public String getDenominazione() {
		return denominazione;
	}

	/**
	 * @param denominazione the denominazione to set
	 */
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	/**
	 * @return the codiceFiscale
	 */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	/**
	 * @param codiceFiscale the codiceFiscale to set
	 */
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	/**
	 * @return the idOsservatorio
	 */
	public String getIdOsservatorio() {
		return idOsservatorio;
	}

	/**
	 * @param idOsservatorio the idOsservatorio to set
	 */
	public void setIdOsservatorio(String idOsservatorio) {
		this.idOsservatorio = idOsservatorio;
	}
}