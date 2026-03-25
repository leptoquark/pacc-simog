package it.avlp.simog.massload.esito;

import java.util.ArrayList;

/**
 * Contiene una collezione di esiti controllo ogni esisto
 * ha cardinalita' 1 a 1 con un cui
 * 
 * @author vletizia
 *
 */
public class EsitiOperazioneControllo {

	ArrayList<EsitoOperazioneControlloBean> listOfEsiti = new ArrayList<EsitoOperazioneControlloBean>();

	public ArrayList<EsitoOperazioneControlloBean> getListOfEsiti() {
		return listOfEsiti;
	}

	public void setListOfEsiti(ArrayList<EsitoOperazioneControlloBean> listOfEsiti) {
		this.listOfEsiti = listOfEsiti;
	}
	
	public void addListOfEsiti(EsitoOperazioneControlloBean esitoSchedaCorrente){
		this.listOfEsiti.add(esitoSchedaCorrente);
	}
	
}
