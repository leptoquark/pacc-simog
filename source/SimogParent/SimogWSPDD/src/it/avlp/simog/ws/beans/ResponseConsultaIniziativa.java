package it.avlp.simog.ws.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import it.avlp.simog.ws.massload.xmlbeans.IniziativaWS;

@XmlType(name = "ResponseConsultaIniziativa")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseConsultaIniziativa {

	@XmlElement
	private IniziativaWS IniziativaXML;
	@XmlElement
	public boolean success;
	@XmlElement
	private String error;


	public IniziativaWS getIniziativaXML() {
		return IniziativaXML;
	}

	public void setIniziativaXML(IniziativaWS iniziativaXML) {
		this.IniziativaXML = iniziativaXML;
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
