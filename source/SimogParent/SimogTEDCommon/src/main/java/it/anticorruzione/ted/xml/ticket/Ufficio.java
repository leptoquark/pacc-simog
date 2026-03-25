/**
 * Web Service TED
 */
package it.anticorruzione.ted.xml.ticket;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Ufficio {
	@XmlElement(name = "denominazione")
	private String denominazione;

	@XmlElement(name = "id_ufficio")
	private String idUfficio;

	@XmlElement(name = "profilo")
	private String profilo;

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
	 * @return the idUfficio
	 */
	public String getIdUfficio() {
		return idUfficio;
	}

	/**
	 * @param idUfficio the idUfficio to set
	 */
	public void setIdUfficio(String idUfficio) {
		this.idUfficio = idUfficio;
	}

	/**
	 * @return the profilo
	 */
	public String getProfilo() {
		return profilo;
	}

	/**
	 * @param profilo the profilo to set
	 */
	public void setProfilo(String profilo) {
		this.profilo = profilo;
	}
}