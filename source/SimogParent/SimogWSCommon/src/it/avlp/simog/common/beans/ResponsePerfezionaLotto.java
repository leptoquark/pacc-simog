package it.avlp.simog.common.beans;


/**************************************************************************
 * La classe estende Response, oltre a gestire le variabili ereditate <br>
 * <lu>
 * <li>error : String
 * <li>success : String
 * </lu><br><br>
 * introduce le variabili<br><br>
 * <lu>
 * <li>messaggio : String 
 */
public class ResponsePerfezionaLotto  extends Response {

	private String messaggio;
	
	public ResponsePerfezionaLotto() {
		super();
	}	
	public String getError() {
		return super.getError();
	}
	public boolean isSuccess() {
		return super.isSuccess();
	}
	public void setError(String error) {
		super.setError(error);
	}
	public void setSuccess(boolean success) {
		super.setSuccess(success);
	}
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
}
