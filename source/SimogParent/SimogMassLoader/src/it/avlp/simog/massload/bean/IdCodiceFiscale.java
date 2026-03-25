package it.avlp.simog.massload.bean;

import org.apache.log4j.Logger;

public class IdCodiceFiscale {

	private String codiceFiscale;
	private String codiceStato;
	public IdCodiceFiscale(String codiceFiscale, String codiceStato) {
		super();
		this.codiceFiscale = codiceFiscale;
		this.codiceStato = codiceStato == null ? "" : codiceStato;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getCodiceStato() {
		return codiceStato;
	}
	public void setCodiceStato(String codiceStato) {
		this.codiceStato = codiceStato == null ? "" : codiceStato;
	}
	
	public boolean equals(String codiceFiscale, String codiceStato){
		if(this.codiceFiscale.equals(codiceFiscale)){
			if(this.codiceStato.equals(codiceStato)) return true;
		}
		
		//TICKET ALM #3404
		analyzeCfToLog(this.codiceFiscale, codiceFiscale,this.codiceStato,codiceStato);
		//FINE TICKET ALM #3404
		return false;
	}
	
	//TICKET ALM #3404
	private void analyzeCfToLog(String cfAnagrafica, String cfPartecipante, String idStatoAnagrafica, String idStatoPartecipante) {
	   Logger logger = Logger.getLogger("MASSLOADER_LOGGER");
		logger.error("ALM 3404: --------------------------------------------");
		logger.error("ALM 3404: Verifica dati anagrafica aggiudicatari - CF");
		logger.error("ALM 3404: CF ANAGRAFICA: |"+cfAnagrafica+"|");
		logger.error("ALM 3404: CF PARTECIPANTE: |"+cfPartecipante+"|");
		if(cfAnagrafica!=null) logger.error("ALM 3404: Are equals? "+cfAnagrafica.equals(cfPartecipante));
		logger.error("ALM 3404: Verifica dati anagrafica aggiudicatari - STATO ESTERO");
		logger.error("ALM 3404: ID STATO ANAGRAFICA: |"+idStatoAnagrafica+"|");
		logger.error("ALM 3404: ID STATO PARTECIPANTE: |"+idStatoPartecipante+"|");
		if(idStatoAnagrafica!=null) logger.error("ALM 3404: Are equals? "+idStatoAnagrafica.equals(idStatoPartecipante));
		logger.error("ALM 3404: --------------------------------------------");
	
	}
	//FINE TICKET ALM #3404
	
}
