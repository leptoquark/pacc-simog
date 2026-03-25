package it.avlp.simog.ws.beans;

import it.avlp.simog.ws.massload.xmlbeans.CUPLOTTOType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**************************************************************************
 * La classe estende Response, oltre a gestire le variabili ereditate <br>
 * <lu>
 * <li>error : String
 * <li>success : String
 * </lu><br><br>
 * introduce le variabili<br><br>
 * <lu>
 * <li>messaggio : String 
 */
@XmlType(name = "ResponsePubblicazioneBando")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponsePubblicazioneBando {

	@XmlElement
	private String messaggio;
	@XmlElement
	public boolean success;
	@XmlElement
	private String error;
	
	  // is3031_RNFDBGL01Active
   @XmlElement
   private it.avlp.simog.ws.massload.xmlbeans.CUPLOTTOType CUPLOTTO[];
   
   public CUPLOTTOType[] getCUPLOTTO() {
      return CUPLOTTO;
   }

   public void setCUPLOTTO(CUPLOTTOType[] cUPLOTTO) {
      CUPLOTTO = cUPLOTTO;
   }

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
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
