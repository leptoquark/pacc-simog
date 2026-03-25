package it.avlp.simog.beans.aggiudicazione.luogo;

public class IstatBean implements java.io.Serializable{
	private String idRegione;
	private String denomRegione;
	private String idProvincia="000";
	private String denomProvincia;
	private String idComune="000000";
	private String denomComune;
	
	
	/****************************************************************************
	 * Costruttore della classe IstatBean 
	 * @param idRegione
	 * @param denomRegione
	 * @param idProvincia
	 * @param denomProvincia
	 * @param idComune
	 * @param denomComune
	 */
	public IstatBean(String idRegione, String denomRegione, String idProvincia,
			String denomProvincia, String idComune, String denomComune) {
		super();
		this.idRegione = idRegione;
		this.denomRegione = denomRegione;
		this.idProvincia = idProvincia;
		this.denomProvincia = denomProvincia;
		this.idComune = idComune;
		this.denomComune = denomComune;
	}
	
	
	public IstatBean() {
		super();
	
	}


	public String getIdRegione() {
		return idRegione;
	}
	public void setIdRegione(String idRegione) {
		this.idRegione = idRegione;
	}
	public String getDenomRegione() {
		return denomRegione;
	}
	public void setDenomRegione(String denomRegione) {
		this.denomRegione = denomRegione;
	}
	public String getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
	public String getDenomProvincia() {
		return denomProvincia;
	}
	public void setDenomProvincia(String denomProvincia) {
		this.denomProvincia = denomProvincia;
	}
	public String getIdComune() {
		return idComune;
	}
	public void setIdComune(String idComune) {
		this.idComune = idComune;
	}
	public String getDenomComune() {
		return denomComune;
	}
	public void setDenomComune(String denomComune) {
		this.denomComune = denomComune;
	}
	

}
