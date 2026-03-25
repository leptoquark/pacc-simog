package it.avlp.simog.massload.actions;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.flusso.OperazioneScheda;

public class ModificaException extends GenericOpException {

	private OperazioneScheda modifica = OperazioneScheda.getModifica();
	
	public ModificaException(IdentificativoSchede identificativo, String cig, String cui, String idSimog, String idLocale, String messaggio) {
		super(identificativo, cig, cui, idSimog, idLocale, messaggio);

	}
	/**
	 * @param message
	 */
	public ModificaException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ModificaException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ModificaException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 5252314946356625541L;

	@Override
	public OperazioneScheda getOperazione() {
		return modifica;
	}

	
}
