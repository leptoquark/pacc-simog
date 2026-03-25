package it.avlp.simog.error;

public class SimogWSException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3657892627807907630L;
	private String error = null;

	/**
	 * Costruttore
	 */
	public SimogWSException() {
		super();
	}

	/**
	 * Costruttore
	 * @param arg0 String 
	 */
	public SimogWSException(String arg0) {
		super(arg0);
		this.error = arg0;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage() {
		return super.getMessage();
	}

	/**
	 * Restituisce il messaggio associato all'errore
	 * @return String 
	 */
	public String getMyMessage(){
		//se trova l'errore nella lista di error manager ritorna il messaggio trovato
		//altrimenti il messaggio in input
		ErrorManager em = new ErrorManager(this.error);
		String messaggio = em.getError();
		if(messaggio == null){
			em = new ErrorManager(ErrorManager.SIMOGWS_UNDEFINEND_ERR_01);
			//controllo introdotto per poter ritornare la stringa degli errori dell validatore
			//quindi caso particolare
			if(this.error != null && this.error.contains("ERRORE")){
				messaggio = this.error;
			}else{
				//altrimenti caso indefinito
			messaggio = em.getError()+ " - "+ (this.error == null ? "GETMYMESSAGE - NULL MESSAGE VALUE" : this.error);
		}
		}
		return messaggio;
	}
	/**
	 * @return String
	 */
	public String getCode(){return this.error;}
	/**
	 * @see java.lang.Throwable#printStackTrace()
	 */
	public void printStackTrace() {
		super.printStackTrace();
	}

}
