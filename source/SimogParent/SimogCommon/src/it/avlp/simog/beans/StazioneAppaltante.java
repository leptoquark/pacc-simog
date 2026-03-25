package it.avlp.simog.beans;

import java.io.Serializable;

public class StazioneAppaltante implements  Serializable {
	
	
	/*
	protected String denominazioneAmministrazione = null;
	protected String codiceFiscaleAmministrazione = null;
	*/
	
	private Amministrazione amministrazione = new Amministrazione();
	
	protected String denominazione = null;
	protected String idUfficio = null;
	
	
	public StazioneAppaltante() {}
	
	public String getDenominazione() {
		return denominazione;
	}
	

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	

	/**
	 * @return Returns the codiceFiscaleAmministrazione.
	 */
	public String getCodiceFiscaleAmministrazione() {
		return amministrazione.getCodiceFiscale();
	}

	/**
	 * @return Returns the denominazioneAmministrazione.
	 */
	public String getDenominazioneAmministrazione() {
		return amministrazione.getDenominazioneAmministrazione();
	}

	/**
	 * @return Returns the idUfficio.
	 */
	public String getIdUfficio() {
		return idUfficio;
	}
/*
	public void setCodiceFiscaleAmministrazione(String codiceFiscaleAmministrazione) {
		this.amministrazione.setCodiceFiscale(codiceFiscaleAmministrazione);
	}

	public void setDenominazioneAmministrazione(String denominazioneAmministrazione) {
		this.amministrazione.setDenominazioneAmministrazione(denominazioneAmministrazione);
	}
*/
	/**
	 * @param idUfficio The idUfficio to set.
	 */
	public void setIdUfficio(String idUfficio) {
		this.idUfficio = idUfficio;
	}

	/**
	 * @param amministrazione The amministrazione to set.
	 */
	public void setAmministrazione(Amministrazione amministrazione) {
		this.amministrazione = amministrazione;
	}
	
	/**
	 * @param amministrazione The amministrazione to get.
	 */
	public Amministrazione getAmministrazione() {
		return this.amministrazione;
	}

	
}
