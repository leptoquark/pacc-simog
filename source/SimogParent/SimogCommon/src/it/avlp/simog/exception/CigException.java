/**
 * 
 */
package it.avlp.simog.exception;

/**
 * Eccezione per gestire un cig malformato
 * 
 * @author vletizia
 *
 */
public class CigException extends Exception {
	
	private String cig;
	private String cui; 
	private int progressivo;
	
	/**
	 * 
	 */
	public CigException() {
	}
	
	public CigException(String cig, String cui, int progressivo, Throwable t){
		super(t.getMessage());
		this.cig = cig;
		this.cui = cui;
		this.progressivo = progressivo;
	}

	/**
	 * @param arg0
	 */
	public CigException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public CigException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CigException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public String getCig() {
		return cig;
	}

	public String getCui() {
		return cui;
	}

	public int getProgressivo() {
		return progressivo;
	}

}
