package it.avlp.simog.massload.esito;

import it.avlp.simog.beans.EsitoOperazioneBean;
import it.avlp.simog.beans.ValidationBean;

import java.util.List;

public class EsitoOperazioneValidateAnagrafiche extends EsitoOperazioneBean{
	
	/**
	 * 
	 */
	private List<ValidationBean> listOfValidation;

	/**
	 * @return
	 */
	public List<ValidationBean> getListOfValidation() {
		return listOfValidation;
	}

	/**
	 * @param listOfAnagraficheNonValide
	 */
	public void setListOfValidation(List<ValidationBean> listOfAnagraficheNonValide) {
		this.listOfValidation = listOfAnagraficheNonValide;
	}

}
