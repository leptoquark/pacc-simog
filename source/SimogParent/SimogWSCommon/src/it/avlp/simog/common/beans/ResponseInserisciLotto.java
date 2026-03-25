package it.avlp.simog.common.beans;

import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.beans.CUPLOTTO;

/**
 *La classe estende response ed oltre a gestire le variabili ereditate<br>
 * error : Stringa <br>success : boolean <br>introduce le variabili<br>
 * cig : CIGBean<br>
 * 
 */
public class ResponseInserisciLotto extends Response  {
	
	private CIGBean cig;	
   private CUPLOTTO CUPLOTTO;

	public ResponseInserisciLotto() {
		super();
	}	
	public CIGBean getCig() {
		return cig;
	}
	public void setCig(CIGBean cig) {
		this.cig = cig;
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
   public CUPLOTTO getCUPLOTTO() {
      return CUPLOTTO;
   }
   public void setCUPLOTTO(CUPLOTTO cUPLOTTO) {
      CUPLOTTO = cUPLOTTO;
   }
}
