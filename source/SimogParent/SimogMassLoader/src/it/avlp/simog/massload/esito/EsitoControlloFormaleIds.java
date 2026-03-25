package it.avlp.simog.massload.esito;

import it.avlp.simog.beans.EsitoOperazioneBean;

import java.util.ArrayList;

public class EsitoControlloFormaleIds extends EsitoOperazioneBean {

	// servono alla gestione di errori specifici su un insieme di schede indica una lista di schede dove gli id non sono validi
	
		// stanno insieme (1) manca l'id
		private boolean isErrorOnlyOnSomeSchedeVoid = false;
		private ArrayList<String> listOfNomiSchedeNonValideVoid = null;
		
		// stanno insieme (3) e' valorizzato quando non deve
		private boolean isErrorOnlyOnSomeSchedeLoaded = false;
		private ArrayList<String> listOfNomiSchedeNonValideLoaded = null;
	
	//  stanno insieme (2) servono alla gestione di errori generici su tutte le schede ad esempio se sono stati rilevati id_simog ed id_locali	
	private boolean isErrorOverAllSchede = false;
	private String errore = null;
	
	public boolean isErrorOverAllSchede() {
		return isErrorOverAllSchede;
	}
	public void setErrorOverAllSchede(boolean isErrorOverAllSchede) {
		this.isErrorOverAllSchede = isErrorOverAllSchede;
	}
	public ArrayList<String> getListOfNomiSchedeNonValideVoid() {
		return listOfNomiSchedeNonValideVoid;
	}
	public void setListOfNomiSchedeNonValideVoid(
			ArrayList<String> listOfNomiSchedeNonValideVoid) {
		this.listOfNomiSchedeNonValideVoid = listOfNomiSchedeNonValideVoid;
	}
	public boolean isErrorOnlyOnSomeSchedeVoid() {
		return isErrorOnlyOnSomeSchedeVoid;
	}
	public void setErrorOnlyOnSomeSchedeVoid(boolean isErrorOnlyOnSomeSchedeVoid) {
		this.isErrorOnlyOnSomeSchedeVoid = isErrorOnlyOnSomeSchedeVoid;
	}
	public String getErrore() {
		return errore;
	}
	public void setErrore(String errore) {
		this.errore = errore;
	}
	public boolean isErrorOnlyOnSomeSchedeLoaded() {
		return isErrorOnlyOnSomeSchedeLoaded;
	}
	public void setErrorOnlyOnSomeSchedeLoaded(boolean isErrorOnlyOnSomeSchedeLoaded) {
		this.isErrorOnlyOnSomeSchedeLoaded = isErrorOnlyOnSomeSchedeLoaded;
	}
	public ArrayList<String> getListOfNomiSchedeNonValideLoaded() {
		return listOfNomiSchedeNonValideLoaded;
	}
	public void setListOfNomiSchedeNonValideLoaded(
			ArrayList<String> listOfNomiSchedeNonValideLoaded) {
		this.listOfNomiSchedeNonValideLoaded = listOfNomiSchedeNonValideLoaded;
	}
	
	
	
}
