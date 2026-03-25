package it.avlp.simog.common.beans;


/**
 * 
 * La Classe estende response e ne permette la gestione dei 
 * parametri error : Stringa e success : boolean 
 *
 */
@Deprecated
public class ResponeModificaGaraLotto extends Response  {

	public ResponeModificaGaraLotto(){
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
}
