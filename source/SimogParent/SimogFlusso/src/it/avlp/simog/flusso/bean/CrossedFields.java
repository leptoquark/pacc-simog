package it.avlp.simog.flusso.bean;

import it.avlp.simog.beans.PubblicazioneBean;

public class CrossedFields {

	private int ID_MODO_REAL;
	private String FLAG_ESCLUSO;
	private int ID_ESCLUSIONE;
	private long idLotto;
	
	// ...
	private String cfAmministrazione;
	private String denAmministrazione;
	private String cfStazioneAppaltante;
	private String denStazioneAppaltante;
	private String codiceCC;
	private String denomCC;
	
	// BANDI - PP - aggiunti campi di pubblicazione 
	private PubblicazioneBean pubblicazione;
	
	//is3028_RFWEBSC00Active
   private String    luogoIstat;
   private String    luogoNuts;
   private long      idSceltaContraente;
   
   private String tipoSettore;
   private String flagOrdinario;
	
	public int getID_MODO_REAL() {
		return ID_MODO_REAL;
	}
	public void setID_MODO_REAL(int id_modo_real) {
		ID_MODO_REAL = id_modo_real;
	}
	public String getFLAG_ESCLUSO() {
		return FLAG_ESCLUSO;
	}
	public void setFLAG_ESCLUSO(String flag_escluso) {
		FLAG_ESCLUSO = flag_escluso;
	}
	public int getID_ESCLUSIONE() {
		return ID_ESCLUSIONE;
	}
	public void setID_ESCLUSIONE(int id_esclusione) {
		ID_ESCLUSIONE = id_esclusione;
	}
	public long getIdLotto() {
		return idLotto;
	}
	public void setIdLotto(long idLotto) {
		this.idLotto = idLotto;
	}
	public String getCfAmministrazione() {
		return cfAmministrazione;
	}
	public void setCfAmministrazione(String cfAmministrazione) {
		this.cfAmministrazione = cfAmministrazione;
	}
	public String getDenAmministrazione() {
		return denAmministrazione;
	}
	public void setDenAmministrazione(String denAmministrazione) {
		this.denAmministrazione = denAmministrazione;
	}
	public String getCfStazioneAppaltante() {
		return cfStazioneAppaltante;
	}
	public void setCfStazioneAppaltante(String cfStazioneAppaltante) {
		this.cfStazioneAppaltante = cfStazioneAppaltante;
	}
	public String getDenStazioneAppaltante() {
		return denStazioneAppaltante;
	}
	public void setDenStazioneAppaltante(String denStazioneAppaltante) {
		this.denStazioneAppaltante = denStazioneAppaltante;
	}
	public String getCodiceCC() {
		return codiceCC;
	}
	public void setCodiceCC(String codiceCC) {
		this.codiceCC = codiceCC;
	}
	public String getDenomCC() {
		return denomCC;
	}
	public void setDenomCC(String denomCC) {
		this.denomCC = denomCC;
	}
	public PubblicazioneBean getPubblicazione() {
		return pubblicazione;
	}
	public void setPubblicazione(PubblicazioneBean pubblicazione) {
		this.pubblicazione = pubblicazione;
	}
   public String getLuogoIstat() {
      return luogoIstat;
   }
   public void setLuogoIstat(String luogoIstat) {
      this.luogoIstat = luogoIstat;
   }
   public String getLuogoNuts() {
      return luogoNuts;
   }
   public void setLuogoNuts(String luogoNuts) {
      this.luogoNuts = luogoNuts;
   }
   public long getIdSceltaContraente() {
      return idSceltaContraente;
   }
   public void setIdSceltaContraente(long idSceltaContraente) {
      this.idSceltaContraente = idSceltaContraente;
   }
public String getTipoSettore() {
	return tipoSettore;
}
public void setTipoSettore(String tipoSettore) {
	this.tipoSettore = tipoSettore;
}
public String getFlagOrdinario() {
	return flagOrdinario;
}
public void setFlagOrdinario(String flagOrdinario) {
	this.flagOrdinario = flagOrdinario;
}
	
	
}
