/**
 * Web Service TED
 */
package it.anticorruzione.ted.xml;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DeltaGara {
	@XmlElement(name = "idGara")
	public Long idGara;

	@XmlElement(name = "dataInserimento")
	private Date dataInserimento;

	@XmlElement(name = "dataInizioValidita")
	private Date dataInizioValidita;

	@XmlElement(name = "deltaGara")
	private String deltaGara;

	/**
	 * @return the idGara
	 */
	public Long getIdGara() {
		return idGara;
	}

	/**
	 * @param idGara the idGara to set
	 */
	public void setIdGara(Long idGara) {
		this.idGara = idGara;
	}

	/**
	 * @return the dataInserimento
	 */
	public Date getDataInserimento() {
		return dataInserimento;
	}

	/**
	 * @param dataInserimento the dataInserimento to set
	 */
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	/**
	 * @return the dataInizioValidita
	 */
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	/**
	 * @param dataInizioValidita the dataInizioValidita to set
	 */
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	/**
	 * @return the deltaGara
	 */
	public String getDeltaGara() {
		return deltaGara;
	}

	/**
	 * @param deltaGara the deltaGara to set
	 */
	public void setDeltaGara(String deltaGara) {
		this.deltaGara = deltaGara;
	}
}