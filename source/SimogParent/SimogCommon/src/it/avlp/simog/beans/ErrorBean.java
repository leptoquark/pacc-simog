package it.avlp.simog.beans;

public class ErrorBean extends MessageBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7402468832933708686L;

	private Throwable error = null;
	
	
	public ErrorBean ( String message ) {
		this(message, null);
	}
	
	public ErrorBean ( String message, Throwable error ) {
		super ( message );
		this.error = error;
	}

	/**
	 * @return Returns the error.
	 */
	public Throwable getError() {
		return error;
	}	
}
