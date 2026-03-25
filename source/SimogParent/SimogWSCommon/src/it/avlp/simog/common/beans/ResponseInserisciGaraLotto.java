package it.avlp.simog.common.beans;

import it.avlp.simog.beans.CIGBean;

/**
 * La classe estende response ed oltre a gestire le variabili ereditate<br>
 * error : Stringa <br>success : boolean <br>introduce le variabili<br>
 * cig : CIGBean<br>
 * id_gara : long<br>
 *
 */
public class ResponseInserisciGaraLotto extends Response  {
	
	private CIGBean cig;	
	private long id_gara;
	
	public long getId_gara() {
		return id_gara;
	}
	public void setId_gara(long id_gara) {
		this.id_gara = id_gara;
	}
	public ResponseInserisciGaraLotto() {
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
}
