package it.avlp.simog.beans;


public class OggettoAppalto {

	
	public String cig            = null;
    public String oggettoAppalto = null;
	public String cpv            = null;
	public String cup            = null;
	public String lotto          = null;
	public String numeroLotti    = null;
	public String prestCompreseNellAppalto  = null;

	public OggettoAppalto(String cig
						 ,String oggettoAppalto
						 ,String cpv
						 ,String cup
						 ,String lotto
						 ,String numeroLotti
						 ,String prestCompreseNellAppalto) {
		this.cig                      = cig						 ;
		this.oggettoAppalto           = oggettoAppalto 			 ;
		this.cpv                      = cpv       				 ;
		this.cup                      = cup      		         ;
		this.lotto                    = lotto                    ; 
		this.numeroLotti              = numeroLotti              ;
		this.prestCompreseNellAppalto = prestCompreseNellAppalto ; 
	}

	public OggettoAppalto() {
	}

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}

	public String getCpv() {
		return cpv;
	}

	public void setCpv(String cpv) {
		this.cpv = cpv;
	}

	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getLotto() {
		return lotto;
	}

	public void setLotto(String lotto) {
		this.lotto = lotto;
	}

	public String getNumeroLotti() {
		return numeroLotti;
	}

	public void setNumeroLotti(String numeroLotti) {
		this.numeroLotti = numeroLotti;
	}

	public String getOggettoAppalto() {
		return oggettoAppalto;
	}

	public void setOggettoAppalto(String oggettoAppalto) {
		this.oggettoAppalto = oggettoAppalto;
	}

	public String getPrestCompreseNellAppalto() {
		return prestCompreseNellAppalto;
	}

	public void setPrestCompreseNellAppalto(String prestCompreseNellAppalto) {
		this.prestCompreseNellAppalto = prestCompreseNellAppalto;
	}

	
	
}
