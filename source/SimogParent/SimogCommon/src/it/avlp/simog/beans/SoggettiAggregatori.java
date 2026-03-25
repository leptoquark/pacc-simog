package it.avlp.simog.beans;

import it.avlp.simog.db.generated.LISTA_SOGGETTI_AGGREGATORI;

public class SoggettiAggregatori implements LISTA_SOGGETTI_AGGREGATORI {

	private String cf;
	private String id_stazione_appaltante;
	private String den;
	
	@Override
	public String getCF_Sogg_Aggregatore() {
		return cf;
	}

	@Override
	public void setCF_Sogg_Aggregatore(String cf) {
		this.cf = cf;

	}

	@Override
	public String getDenominazione_Sogg_Aggregatore() {
		return den;
	}

	@Override
	public void setDenominazione_Sogg_Aggregatore(String den) {
		this.den = den;

	}

	@Override
	public String getID_STAZIONE_APPALTANTE() {
		// TODO Auto-generated method stub
		return id_stazione_appaltante;
	}

	@Override
	public void setID_STAZIONE_APPALTANTE(String id_stazione_appaltante) {
		this.id_stazione_appaltante=id_stazione_appaltante;
		
	}

}
