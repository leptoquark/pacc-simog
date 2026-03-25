package it.avlp.simog.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * La classe estende response ed oltre a gestire le variabili ereditate
 * error di tipo Stringa e success di tipo boolean introduce la variabile
 * messaggio di tipo Stringa
 *
 */
@XmlType(name = "ResponseChiudiSession")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseChiudiSession {
	
	@XmlElement
	private String messaggio;
	@XmlElement
	public boolean success;
	@XmlElement
	private String error;

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
