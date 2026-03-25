/**
 * Web Service TED
 */
package it.anticorruzione.ted.xml.ticket;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Soggetto {
	@XmlElement(name = "cognome")
	private String cognome;

	@XmlElement(name = "nome")
	private String nome;

	@XmlElement(name = "tel")
	private String tel;

	@XmlElement(name = "fax")
	private String fax;

	@XmlElement(name = "admin_or")
	private String adminOr;

	/**
	 * @return the cognome
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * @param cognome the cognome to set
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the adminOr
	 */
	public String getAdminOr() {
		return adminOr;
	}

	/**
	 * @param adminOr the adminOr to set
	 */
	public void setAdminOr(String adminOr) {
		this.adminOr = adminOr;
	}
}