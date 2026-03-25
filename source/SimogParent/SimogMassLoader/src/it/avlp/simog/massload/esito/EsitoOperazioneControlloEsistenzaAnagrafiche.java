package it.avlp.simog.massload.esito;

import it.avlp.simog.beans.EsitoOperazioneBean;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;

import java.util.List;

public class EsitoOperazioneControlloEsistenzaAnagrafiche extends EsitoOperazioneBean {

	/**
	 * 
	 */
	private List<SchedaSpecificaValidationBean> listOfValidation;

	/**
	 * @return
	 */
	public List<SchedaSpecificaValidationBean> getListOfValidation() {
		return listOfValidation;
	}

	/**
	 * @param listOfAnagraficheNonValide
	 */
	public void setListOfValidation(List<SchedaSpecificaValidationBean> listOfAnagraficheNonValide) {
		this.listOfValidation = listOfAnagraficheNonValide;
	}
	/**
	 * @param validation
	 */
	public void addValidation(SchedaSpecificaValidationBean validation){
		this.listOfValidation = super.addElements(this.listOfValidation, validation);
	}
}
