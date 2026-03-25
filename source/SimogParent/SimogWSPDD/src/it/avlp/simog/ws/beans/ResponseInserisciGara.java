package it.avlp.simog.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ResponseInserisciGara")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseInserisciGara {

	@XmlElement
	private String id_gara;
	@XmlElement
	public boolean success;
	@XmlElement
	private String error;

	public String getId_gara() {
		return id_gara;
	}

	public void setId_gara(String id_gara) {
		this.id_gara = id_gara;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
