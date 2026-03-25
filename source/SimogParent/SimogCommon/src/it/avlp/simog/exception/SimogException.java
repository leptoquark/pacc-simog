package it.avlp.simog.exception;

public class SimogException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public SimogException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	
	public SimogException(String arg0) {
		super(arg0);
	}
	public String getMessage() {
		return (super.getMessage());
		}
}
