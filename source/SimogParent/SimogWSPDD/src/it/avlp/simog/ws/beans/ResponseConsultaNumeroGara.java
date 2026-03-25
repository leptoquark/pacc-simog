package it.avlp.simog.ws.beans;

import it.avlp.simog.ws.massload.xmlbeans.SchedaGaraCig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * La classe estende response ed oltre a gestire le variabili ereditate error di
 * tipo Stringa e success di tipo boolean introduce GaraXML di tipo Stringa
 * 
 */
@XmlType(name = "ResponseConsultaNumeroGara")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseConsultaNumeroGara {

	@XmlElement
	private SchedaGaraCig schedaGaraCig;
	@XmlElement
	public boolean success;
	@XmlElement
	private String error;

	public SchedaGaraCig getSchedaGaraCig() {
		return schedaGaraCig;
	}

	public void setSchedaGaraCig(SchedaGaraCig schedaGaraCig) {
		this.schedaGaraCig = schedaGaraCig;
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
