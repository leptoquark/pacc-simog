/**
 * Web Service TED
 */
package it.anticorruzione.ted.xml.ticket;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CheckLogin {
	@XmlElement(name = "soggetto")
	private Soggetto soggetto;

	@XmlElement(name = "collaborazioni")
	private Collaborazioni collaborazioni;

	@XmlElement(name = "stato")
	private String stato;

	/**
	 * @return the soggetto
	 */
	public Soggetto getSoggetto() {
		return soggetto;
	}

	/**
	 * @param soggetto the soggetto to set
	 */
	public void setSoggetto(Soggetto soggetto) {
		this.soggetto = soggetto;
	}

	/**
	 * @return the collaborazioni
	 */
	public Collaborazioni getCollaborazioni() {
		return collaborazioni;
	}

	/**
	 * @param collaborazioni the collaborazioni to set
	 */
	public void setCollaborazioni(Collaborazioni collaborazioni) {
		this.collaborazioni = collaborazioni;
	}

	/**
	 * @return the stato
	 */
	public String getStato() {
		return stato;
	}

	/**
	 * @param stato the stato to set
	 */
	public void setStato(String stato) {
		this.stato = stato;
	}
}