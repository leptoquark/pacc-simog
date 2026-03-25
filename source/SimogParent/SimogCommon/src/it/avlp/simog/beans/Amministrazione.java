package it.avlp.simog.beans;


/**
 * Bean rapprensentante una amministrazione
 *
 */
public class Amministrazione implements java.io.Serializable{

	
	public String codiceFiscale = null;
	public String denominazioneAmministrazione = null;
	public String id_osservatorio = null;
	
	// INT85
	String tipoSA = "";
	
	public String getTipoSA() {
      return tipoSA == null ? "" : tipoSA;
   }

   public void setTipoSA(String tipoSA) {
      this.tipoSA = tipoSA;
   }
   // INT85 fine
   
   /**
	 * Costruttore con parametri 
	 * 
	 * @param codiceFiscale
	 * @param denominazione
	 */
	public Amministrazione(String codiceFiscale, String denominazione) {
		this.codiceFiscale = codiceFiscale;
		this. denominazioneAmministrazione = denominazione;
	}

	/**
	 * Costruttore no args
	 */
	public Amministrazione() {
        codiceFiscale = null;
        denominazioneAmministrazione = new String();		
	}

	/**
	 * getter
	 * 
	 * @return string codice fiscale or null
	 */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	/**
	 * getter
	 * 
	 * @return string denominazione amministrazione or null
	 */
	public String getDenominazioneAmministrazione() {
		return denominazioneAmministrazione;
	}

	/**
	 * setter 
	 *
	 * @param denominazioneAmministrazione
	 */
	public void setDenominazioneAmministrazione(String denominazioneAmministrazione) {
		this.denominazioneAmministrazione = denominazioneAmministrazione;
	}

	/**
	 * setter
	 * 
	 * @param codiceFiscale
	 */
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	/**
	 * getter
	 * 
	 * @return id_osservatorio String
	 */
	public String getId_osservatorio() {
		return id_osservatorio;
	}

	/**
	 * setter
	 * 
	 * @param id_osservatorio String
	 */
	public void setId_osservatorio(String id_osservatorio) {
		this.id_osservatorio = id_osservatorio;
	}
}
