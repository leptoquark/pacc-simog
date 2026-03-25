package it.avlp.simog.common.beans;


/**
 * La classe estende response ed oltre a gestire le variabili ereditate
 * error di tipo Stringa e success di tipo boolean introduce la variabile
 * messaggio di tipo Stringa
 *
 */
public class ResponseChiudiSession extends Response {
	private String messaggio;
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
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
}
