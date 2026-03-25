package it.avlp.simog.common.beans;


public class ResponseInserisciGara extends Response{

	private String id_gara;

	public String getId_gara() {
		return id_gara;
	}

	public void setId_gara(String id_gara) {
		this.id_gara = id_gara;
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
