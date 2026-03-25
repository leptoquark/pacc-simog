package it.avlp.simog.massload.actions;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.flusso.OperazioneScheda;

public abstract class GenericOpException extends Exception {

	protected IdentificativoSchede identificativo;
	protected String cig;
	protected String cui;
	protected String idSimog;
	protected String idLocale;
	protected String messaggio;
	
	public GenericOpException(IdentificativoSchede identificativo, String cig, String cui, String idSimog, String idLocale, String messaggio) {
		super();
		this.identificativo = identificativo;
		this.cig = cig;
		this.cui = cui;
		this.idSimog = idSimog;
		this.idLocale = idLocale;
		this.messaggio = messaggio;
	}

	/**
	 * @param message
	 */
	public GenericOpException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public GenericOpException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public GenericOpException(String message, Throwable cause) {
		super(message, cause);
	}	
	
	public abstract OperazioneScheda getOperazione();

	public IdentificativoSchede getIdentificativo() {
		return identificativo;
	}

	public String getCig() {
		return cig;
	}

	public String getCui() {
		return cui;
	}

	public String getIdSimog() {
		return idSimog;
	}

	public String getIdLocale() {
		return idLocale;
	}

	/**
	 * @return the messaggio
	 */
	public String getMessaggio() {
		return messaggio;
	}
}
