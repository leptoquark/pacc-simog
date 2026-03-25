package it.avlp.simog.error;

/**
 * Eccezione definita per gestire gli errori / eccezioni delle validazioni XML
 *
 */
public class SimogWsXmlException extends SimogWSException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2227026057429492250L;
	private String error = null;



	/**
	 * Costruttore
	 * @param arg0 String 
	 */
	public SimogWsXmlException(String codiceErrore,String descrizioneAggiuntiva) {
		super( descrizioneAggiuntiva != null ? new ErrorManager(codiceErrore).getError() + " - " + descrizioneAggiuntiva : new ErrorManager(codiceErrore).getError());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {
		return super.getMessage();
	}

	public void printStackTrace() {
		super.printStackTrace();
	}
}
