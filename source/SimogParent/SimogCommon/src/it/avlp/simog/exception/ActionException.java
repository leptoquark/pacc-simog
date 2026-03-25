package it.avlp.simog.exception;

public class ActionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5754336037602611733L;

	public ActionException() {
	}

	public ActionException(String arg0) {
		super(arg0);
	}

	public ActionException(Throwable arg0) {
		super(arg0);
	}

	public ActionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
