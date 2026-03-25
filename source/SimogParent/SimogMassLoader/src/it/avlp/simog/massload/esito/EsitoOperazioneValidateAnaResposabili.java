package it.avlp.simog.massload.esito;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;

import java.util.List;

public class EsitoOperazioneValidateAnaResposabili extends EsitoOperazioneValidateAnagrafiche{

	ValidationBean warnings;
	List<SoggettoResponsabileBean> listOfValidAnaResponsabile;
	
	public List<SoggettoResponsabileBean> getListOfValidAnaResponsabile() {
		return listOfValidAnaResponsabile;
	}
	public void setListOfValidAnaResponsabile(
			List<SoggettoResponsabileBean> listOfValidAnaResponsabile) {
		this.listOfValidAnaResponsabile = listOfValidAnaResponsabile;
	}
	public ValidationBean getWarnings() {
		return warnings;
	}
	public void setWarnings(ValidationBean warnings) {
		this.warnings = warnings;
	}
	
	
	
}
