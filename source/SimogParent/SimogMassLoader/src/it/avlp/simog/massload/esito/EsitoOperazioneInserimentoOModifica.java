package it.avlp.simog.massload.esito;

import it.avlp.simog.beans.EsitoOperazioneBean;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;

import java.util.List;

public class EsitoOperazioneInserimentoOModifica extends EsitoOperazioneBean{

	private List<SchedaSpecificaValidationBean> listOfSuccess;
	private String cui;
	
	

	public String getCui() {
		return cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public List<SchedaSpecificaValidationBean> getListOfSuccess() {
		return listOfSuccess;
	}

	public void setListOfSuccess(List<SchedaSpecificaValidationBean> listOfSuccess) {
		this.listOfSuccess = listOfSuccess;
	}
	
}
