package it.avlp.simog.massload.util.duplicated.feedBack;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType;

public class AggiudicatarioDuplicateFeedBack extends DuplicateFeedBack {

	private SoggAggiudicatarioType[] listOfAggiudicatari;
	
	public AggiudicatarioDuplicateFeedBack(ValidationBean duplicateError, boolean containsDuplicate, SoggAggiudicatarioType[] listOfAggiudicatari) {
		super(duplicateError, containsDuplicate);
		this.listOfAggiudicatari = listOfAggiudicatari;
	}
	public ValidationBean getDuplicateError() {
		return duplicateWarning;
	}

	public boolean isContainsDuplicate() {
		return containsDuplicate;
	}
	public SoggAggiudicatarioType[] getListOfAggiudicatari() {
		return listOfAggiudicatari;
	}

}
