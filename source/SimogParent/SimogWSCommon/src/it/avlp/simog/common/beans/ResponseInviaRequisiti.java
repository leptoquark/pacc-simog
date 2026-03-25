package it.avlp.simog.common.beans;

public class ResponseInviaRequisiti extends ResponseModificaLotto {

	public String getError() {
		return super.getError();
	}

	public String getMessaggio() {
		return super.getMessaggio();
	}

	public boolean isSuccess() {
		return super.isSuccess();
	}

	public void setError(String error) {
		super.setError(error);
	}

	public void setMessaggio(String messaggio) {
		super.setMessaggio(messaggio);
	}

	public void setSuccess(boolean success) {
		super.setSuccess(success);
	}

}
