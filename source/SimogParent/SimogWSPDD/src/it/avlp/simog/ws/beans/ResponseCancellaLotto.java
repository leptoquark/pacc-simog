package it.avlp.simog.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import it.avlp.simog.common.beans.Response;

/**
 * La classe estende Response, oltre a gestire le variabili ereditate <br>
 * <lu>
 * <li>error : String
 * <li>success : String
 * </lu><br><br>
 * introduce le variabili<br><br>
 * <lu>
 * <li>messaggio : String
 * 
 *
 */
@XmlType(name = "ResponseCancellaLotto")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseCancellaLotto {
	
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
