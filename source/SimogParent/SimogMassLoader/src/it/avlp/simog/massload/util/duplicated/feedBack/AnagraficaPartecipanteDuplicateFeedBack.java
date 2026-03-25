package it.avlp.simog.massload.util.duplicated.feedBack;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.massload.xmlbeans.AggiudicatarioType;

public class AnagraficaPartecipanteDuplicateFeedBack extends DuplicateFeedBack {

	private AggiudicatarioType[] arrayOfAnagrafichePartecipanti;
	
	public AnagraficaPartecipanteDuplicateFeedBack(ValidationBean duplicateError, boolean containsDuplicate, AggiudicatarioType[] arrayOfAnagrafichePartecipanti) {
		super(duplicateError, containsDuplicate);
		this.arrayOfAnagrafichePartecipanti = arrayOfAnagrafichePartecipanti;
	}

	public AggiudicatarioType[] getArrayOfAnagrafichePartecipanti() {
		return arrayOfAnagrafichePartecipanti;
	}

	public ValidationBean getDuplicateError() {
		return duplicateWarning;
	}

	public boolean isContainsDuplicate() {
		return containsDuplicate;
	}
}
