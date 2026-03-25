package it.avlp.simog.ws.beans;

import it.avlp.simog.beans.CIGBean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * La classe estende response ed oltre a gestire le variabili ereditate<br>
 * error : Stringa <br>
 * success : boolean <br>
 * introduce le variabili<br>
 * cig : CIGBean<br>
 * 
 */
@XmlType(name = "ResponseInserisciLotto")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseInserisciLotto {

	@XmlElement
	private CIGBean cig;
	@XmlElement
	public boolean success;
	@XmlElement
	private String error;

	// is3031_RNFDBGL01Active
	@XmlElement
   private it.avlp.simog.ws.massload.xmlbeans.CUPLOTTOType CUPLOTTO;
	
   public it.avlp.simog.ws.massload.xmlbeans.CUPLOTTOType getCUPLOTTO() {
      return CUPLOTTO;
   }

   public void setCUPLOTTO(it.avlp.simog.ws.massload.xmlbeans.CUPLOTTOType cUPLOTTO) {
      CUPLOTTO = cUPLOTTO;
   }

	public CIGBean getCig() {
		return cig;
	}

	public void setCig(CIGBean cig) {
		this.cig = cig;
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
