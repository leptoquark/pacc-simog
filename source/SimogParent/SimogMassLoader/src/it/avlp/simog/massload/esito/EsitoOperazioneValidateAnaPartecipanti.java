package it.avlp.simog.massload.esito;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;

import java.util.List;

public class EsitoOperazioneValidateAnaPartecipanti extends EsitoOperazioneValidateAnagrafiche {

	ValidationBean warnings;
	List<SoggettoPartecipanteBean> listOfValidAnaPartecipante;
	
	public List<SoggettoPartecipanteBean> getListOfValidAnaPartecipante() {
		return listOfValidAnaPartecipante;
	}
	public void setListOfValidAnaPartecipante(
			List<SoggettoPartecipanteBean> listOfValidAnaPartecipante) {
		this.listOfValidAnaPartecipante = listOfValidAnaPartecipante;
	}
	public ValidationBean getWarnings() {
		return warnings;
	}
	public void setWarnings(ValidationBean warnings) {
		this.warnings = warnings;
	}
	
	
	
}
