package it.avlp.simog.ws.beans;

import it.avlp.simog.beans.Collaborazioni;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * La classe estende Response ed oltre a gestire le variabili ereditate success
 * : boolean e error : String introduce, ticket : String e coll :
 * Collaborazioni.
 * 
 */
@XmlType(name = "ResponseCheckLogin")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseCheckLogin {

	@XmlElement
	private String ticket;
	@XmlElement
	private Collaborazioni coll = null;
	@XmlElement
	public boolean success;
	@XmlElement
	private String error;
	
	public ResponseCheckLogin() {
		super();
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Collaborazioni getColl() {
		return coll;
	}

	public void setColl(Collaborazioni coll) {
		this.coll = coll;
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
