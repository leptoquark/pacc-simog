package it.avlp.simog.common.beans;

//TICKET ALM - 3.04.4
public class ResponseConsultaIniziativa extends Response {

	private String IniziativaXML = null;

	public ResponseConsultaIniziativa() {
		super();
	}
	public void setIniziativaXML (String IniziativaXML){
		this.IniziativaXML  = IniziativaXML;
	}
	public String getIniziativaXML (){
		return this.IniziativaXML ;
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
