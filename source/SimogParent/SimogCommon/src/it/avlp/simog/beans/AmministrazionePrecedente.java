package it.avlp.simog.beans;


/**
 * Bean rapprensentante Amministrazione Precedente
 *
 */
@Deprecated
public class AmministrazionePrecedente {

	
	public String codiceFiscale                          = null;
    public String denominazioneAmministrazione           = null;
	public String codiceCategoria                        = null;
	public String codiceFiscalePerContoSa                = null;
	public String denominazioneAmministrazionePerContoSa = null;

	public AmministrazionePrecedente(String codiceFiscale
									,String denominazione
									,String codiceCategoria
									,String codiceFiscalePerContoSa
									,String denominazioneAmministrazionePerContoSa) {
		this.codiceFiscale                          = codiceFiscale							;
		this.denominazioneAmministrazione           = denominazione 						;
		this.codiceCategoria                        = codiceCategoria       				;
		this.codiceFiscalePerContoSa                = codiceFiscalePerContoSa      		    ;
		this.denominazioneAmministrazionePerContoSa = denominazioneAmministrazionePerContoSa;
	}

	public AmministrazionePrecedente() {
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public String getDenominazioneAmministrazione() {
		return denominazioneAmministrazione;
	}

	public void setDenominazioneAmministrazione(String denominazioneAmministrazione) {
		this.denominazioneAmministrazione = denominazioneAmministrazione;
	}

	/**
	 * @param codiceFiscale The codiceFiscale to set.
	 */
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCodiceCategoria() {
		return codiceCategoria;
	}

	public void setCodiceCategoria(String codiceCategoria) {
		this.codiceCategoria = codiceCategoria;
	}

	public String getCodiceFiscalePerContoSa() {
		return codiceFiscalePerContoSa;
	}

	public void setCodiceFiscalePerContoSa(String codiceFiscalePerContoSa) {
		this.codiceFiscalePerContoSa = codiceFiscalePerContoSa;
	}

	public String getDenominazioneAmministrazionePerContoSa() {
		return denominazioneAmministrazionePerContoSa;
	}

	public void setDenominazioneAmministrazionePerContoSa(
			String denominazioneAmministrazionePerContoSa) {
		this.denominazioneAmministrazionePerContoSa = denominazioneAmministrazionePerContoSa;
	}
}
