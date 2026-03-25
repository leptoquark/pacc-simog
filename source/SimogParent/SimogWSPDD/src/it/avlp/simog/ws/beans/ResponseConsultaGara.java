package it.avlp.simog.ws.beans;

import it.avlp.simog.ws.massload.xmlbeans.SchedaType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * La classe estende response ed oltre a gestire le variabili ereditate error di
 * tipo Stringa e success di tipo boolean introduce GaraXML di tipo Stringa
 * 
 */
@XmlType(name = "ResponseConsultaGara")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseConsultaGara {

	@XmlElement
	private SchedaType GaraXML;
	@XmlElement
	public boolean success;
	@XmlElement
	private String error;


	public SchedaType getGaraXML() {
		return GaraXML;
	}

	public void setGaraXML(SchedaType garaXML) {
		GaraXML = garaXML;
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
