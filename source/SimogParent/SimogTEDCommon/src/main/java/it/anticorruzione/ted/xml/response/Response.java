/**
 * Web Service TED
 */
package it.anticorruzione.ted.xml.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import it.anticorruzione.ted.xml.DeltaGara;
import it.anticorruzione.ted.xml.DeltaLotto;

@XmlAccessorType(XmlAccessType.FIELD)
public class Response {
	@XmlElement(name = "deltaGara")
	public DeltaGara deltaGara;

	@XmlElement(name = "deltaLotto")
	public List<DeltaLotto> deltaLotto;

	@XmlElement(name = "success")
	public boolean success;

	@XmlElement(name = "error")
	private String error;

	/**
	 * @return the deltaGara
	 */
	public DeltaGara getDeltaGara() {
		return deltaGara;
	}

	/**
	 * @param deltaGara the deltaGara to set
	 */
	public void setDeltaGara(DeltaGara deltaGara) {
		this.deltaGara = deltaGara;
	}

	/**
	 * @return the deltaLotto
	 */
	public List<DeltaLotto> getDeltaLotto() {
		return deltaLotto;
	}

	/**
	 * @param deltaLotto the deltaLotto to set
	 */
	public void setDeltaLotto(List<DeltaLotto> deltaLotto) {
		this.deltaLotto = deltaLotto;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
}