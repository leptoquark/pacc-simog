/**
 * Web Service TED
 */
package it.anticorruzione.ted.xml;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class DeltaLotto {
	@XmlElement(name = "cig")
	public String cig;

	@XmlElement(name = "dataInserimento")
	private Date dataInserimento;

	@XmlElement(name = "dataInizioValidita")
	private Date dataInizioValidita;

	@XmlElement(name = "deltaLotto")
	private String deltaLotto;

	/**
	 * @return the cig
	 */
	public String getCig() {
		return cig;
	}

	/**
	 * @param cig the cig to set
	 */
	public void setCig(String cig) {
		this.cig = cig;
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
	 * @return the deltaLotto
	 */
	public String getDeltaLotto() {
		return deltaLotto;
	}

	/**
	 * @param deltaLotto the deltaLotto to set
	 */
	public void setDeltaLotto(String deltaLotto) {
		this.deltaLotto = deltaLotto;
	}
}