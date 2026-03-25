/**
 * Web Service TED
 */
package it.anticorruzione.ted.xml.ticket;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Collaborazione {
	@XmlAttribute(name = "index")
	private String index;

	@XmlElement(name = "azienda")
	private Azienda azienda;

	@XmlElement(name = "ufficio")
	private Ufficio ufficio;

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the azienda
	 */
	public Azienda getAzienda() {
		return azienda;
	}

	/**
	 * @param azienda the azienda to set
	 */
	public void setAzienda(Azienda azienda) {
		this.azienda = azienda;
	}

	/**
	 * @return the ufficio
	 */
	public Ufficio getUfficio() {
		return ufficio;
	}

	/**
	 * @param ufficio the ufficio to set
	 */
	public void setUfficio(Ufficio ufficio) {
		this.ufficio = ufficio;
	}
}