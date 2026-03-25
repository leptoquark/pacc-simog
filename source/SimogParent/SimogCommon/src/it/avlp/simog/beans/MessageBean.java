package it.avlp.simog.beans;

import java.io.Serializable;

public class MessageBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7402468832933708686L;
	protected String message = null;
	

	public MessageBean ( String message ) {
		this.message = message;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	
	// nuova gestione, usero una classe che estende questa in modo da gestirmela come piu mi piace
	// pur mantenendo il supporto all'indietro.
	protected MessageBean(){}
//	protected void setMessage(String message){
//		this.message = message;
//	}
	
}
