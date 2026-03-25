package it.avlp.simog.beans;

	/**
	 * Classe Bean (contenitore) che rappresenta una collaborazione
	 * oltre hai metodi getter e setter offre un metodo toString()
	 * per verificarne il contenuto
	 * 
	 * **/

public class Collaborazione {
	private String index;
	private String azienda_denominazione;
	private String azienda_codiceFiscale;
	private String ufficio_denominazione;
	private String ufficio_id;
	private String ufficio_profilo;
	private String idOsservatorio; 
	
	//--- costruttore no-args come da specifiche rpc-1.1 ---//
	public Collaborazione(){}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getAzienda_denominazione() {
		return azienda_denominazione;
	}
	public void setAzienda_denominazione(String azienda_denominazione) {
		this.azienda_denominazione = azienda_denominazione;
	}
	public String getAzienda_codiceFiscale() {
		return azienda_codiceFiscale;
	}
	public void setAzienda_codiceFiscale(String azienda_codiceFiscale) {
		this.azienda_codiceFiscale = azienda_codiceFiscale;
	}
	public String getUfficio_denominazione() {
		return ufficio_denominazione;
	}
	public void setUfficio_denominazione(String ufficio_denominazione) {
		this.ufficio_denominazione = ufficio_denominazione;
	}
	public String getUfficio_id() {
		return ufficio_id;
	}
	public void setUfficio_id(String ufficio_id) {
		this.ufficio_id = ufficio_id;
	}
	public String getUfficio_profilo() {
		return ufficio_profilo;
	}
	public void setUfficio_profilo(String ufficio_profilo) {
		this.ufficio_profilo = ufficio_profilo;
	}
	public String toString(){
		String collaborazione = "";
		collaborazione += "Denominazione Azienda : "+azienda_denominazione	+"\r\n";
		collaborazione += "Codice Fiscale Azienda: "+azienda_codiceFiscale	+"\r\n";
		collaborazione += "Id osservatorio       : "+idOsservatorio			+"\r\n";
		collaborazione += "Denominazione ufficio : "+ufficio_denominazione	+"\r\n";
		collaborazione += "Id Ufficio            : "+ufficio_id				+"\r\n";
		collaborazione += "Profilo Ufficio       : "+ufficio_profilo		+"\r\n";
		return collaborazione;
	}
	public String getIdOsservatorio() {
		return idOsservatorio;
	}
	public void setIdOsservatorio(String idOsservatorio) {
		this.idOsservatorio = idOsservatorio;
	}
	
}
