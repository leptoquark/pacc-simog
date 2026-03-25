package it.avlp.simog.ws.beans;

import it.avlp.simog.ws.massload.xmlbeans.DeltaGaraTED;
import it.avlp.simog.ws.massload.xmlbeans.DeltaLottoTED;
import it.avlp.simog.ws.massload.xmlbeans.SchedaGaraCig;
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
@XmlType(name = "ResponseConsultaGaraTED")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseConsultaGaraTED {

	@XmlElement
	private SchedaType GaraXML;
	@XmlElement
	private SchedaGaraCig schedaGaraCig;
	@XmlElement
	public boolean success;
	@XmlElement
	private String error;
	@XmlElement
	private DeltaGaraTED deltaGaraTED;
	@XmlElement
	private DeltaLottoTED deltaLottoTED;


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

	public DeltaGaraTED getDeltaGaraTED() {
		return deltaGaraTED;
	}

	public void setDeltaGaraTED(DeltaGaraTED deltaGaraTED) {
		this.deltaGaraTED = deltaGaraTED;
	}

	public DeltaLottoTED getDeltaLottoTED() {
		return deltaLottoTED;
	}

	public void setDeltaLottoTED(DeltaLottoTED deltaLottoTED) {
		this.deltaLottoTED = deltaLottoTED;
	}

	public SchedaGaraCig getSchedaGaraCig() {
		return schedaGaraCig;
	}

	public void setSchedaGaraCig(SchedaGaraCig schedaGaraCig) {
		this.schedaGaraCig = schedaGaraCig;
	}
	
	

}
