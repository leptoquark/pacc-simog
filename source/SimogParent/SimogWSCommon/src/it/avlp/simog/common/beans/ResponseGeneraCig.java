package it.avlp.simog.common.beans;

import it.avlp.simog.beans.CIGBean;

/**
 * La classe estende response ed oltre a gestire le variabili ereditate
 * error di tipo Stringa e success di tipo boolean introduce cig di tipo
 * CIGBean 
 *
 */
public class ResponseGeneraCig extends Response {

	private CIGBean cig;	
	public ResponseGeneraCig() {
		super();
	}	
	public CIGBean getCig() {
		return cig;
	}
	public void setCig(CIGBean cig) {
		this.cig = cig;
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
