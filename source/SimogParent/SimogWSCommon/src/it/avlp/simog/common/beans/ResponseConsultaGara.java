package it.avlp.simog.common.beans;


/**
 * La classe estende response ed oltre a gestire le variabili ereditate
 * error di tipo Stringa e success di tipo boolean introduce 
 * GaraXML di tipo Stringa
 *
 */
public class ResponseConsultaGara extends Response {

	private String GaraXML = null;

	public ResponseConsultaGara() {
		super();
	}
	public void setGaraXML (String GaraXML){
		this.GaraXML  = GaraXML;
	}
	public String getGaraXML (){
		return this.GaraXML ;
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
