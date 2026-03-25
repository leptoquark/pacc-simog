package it.avlp.simog.massload;

/**
 * Eccezione che serve a gestire le situazioni nelle quali non si puo' andare avanti
 * con le operazioni previste dal masslaoder.
 * Nel caso specifico e' nata per gestire la situazione di anagrafiche NON valide.
 * 
 * @author vletizia
 *
 */
public class MassLoaderInterruptException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1647799860956141313L;

	public MassLoaderInterruptException() {
		super();
	}

	public MassLoaderInterruptException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public MassLoaderInterruptException(String arg0) {
		super(arg0);
	}

	public MassLoaderInterruptException(Throwable arg0) {
		super(arg0);
	}

}
