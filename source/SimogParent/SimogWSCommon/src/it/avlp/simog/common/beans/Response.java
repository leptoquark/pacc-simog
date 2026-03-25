package it.avlp.simog.common.beans;

/**
 * La classe permette la gestione delle variabili 
 * success : boolean ,
 * error : String
 *
 */
public class Response {
	public boolean success;
	private String error;
	
	public Response(){}
	
	/**
	 * Ritorna un valore che rappresenta l'esito della operazione richiesta
	 * 
	 * @return boolean
	 */
	 public boolean isSuccess() {
		return success;
	}
	/**
	 * Metodo per la valorizzazione della variabile che rappresenta l'esito della operazione
	 * 
	 * @param success boolean
	 */	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * Metodo che ritorna la stringa contente l'errore e' valorizzato e non nullo
	 * solo nel caso in cui la variabile di classe success e' a false
	 * 
	 * @return String messaggio di errore
	 */	
	public String getError() {
		return error;
	}
	/**
	 * Metodo per valorizzazione della varibile che contiene il messaggio di errore
	 *  
	 * @param error String
	 */	
	public void setError(String error) {
		this.error = error;
	}
	
}
