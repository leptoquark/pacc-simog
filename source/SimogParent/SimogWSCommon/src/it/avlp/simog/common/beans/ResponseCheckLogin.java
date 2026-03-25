package it.avlp.simog.common.beans;

import it.avlp.simog.beans.Collaborazioni;

/**
 * La classe estende Response ed oltre a gestire le variabili 
 * ereditate success : boolean e error : String introduce, 
 * ticket : String e coll : Collaborazioni.   
 *
 */
public class ResponseCheckLogin extends Response {
	
	private String ticket;
	private Collaborazioni coll = null;
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
	public String getError() {
		return super.getError();
	}
	public boolean isSuccess() {
		return super.isSuccess();
	}
	public void setError(String error) {
		super.setError(error);
	}
	public void setSuccess(boolean success) {
		super.setSuccess(success);
	}
	
}
