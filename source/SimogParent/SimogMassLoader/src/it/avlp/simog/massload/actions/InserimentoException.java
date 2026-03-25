/**
 * 
 */
package it.avlp.simog.massload.actions;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.flusso.OperazioneScheda;

/**
 * @author vletizia
 *
 */
public class InserimentoException extends GenericOpException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 260254093605249761L;

	private OperazioneScheda inserimento = OperazioneScheda.getInserimento();
	
	/**
	 * 
	 */
	public InserimentoException(IdentificativoSchede identificativo, String cig, String cui, String idLocale, String idSimog, String messaggio) {
		super(identificativo, cig, cui, idSimog, idLocale, messaggio);
	}

	/**
	 * @param message
	 */
	public InserimentoException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InserimentoException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InserimentoException(String message, Throwable cause) {
		super(message, cause);
	}

	public OperazioneScheda getOperazione() {
		return inserimento;
	}
}
