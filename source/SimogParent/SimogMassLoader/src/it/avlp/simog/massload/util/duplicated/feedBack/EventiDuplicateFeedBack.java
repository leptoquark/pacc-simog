package it.avlp.simog.massload.util.duplicated.feedBack;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.massload.xmlbeans.RecMotivoVarType;

public class EventiDuplicateFeedBack extends DuplicateFeedBack {

	private RecMotivoVarType[] listOfEventi;
	
	public EventiDuplicateFeedBack(ValidationBean duplicateError, boolean containsDuplicate, RecMotivoVarType[] listOfEventi) {
		super(duplicateError, containsDuplicate);
		this.listOfEventi = listOfEventi;
	}
	public ValidationBean getDuplicateError() {
		return duplicateWarning;
	}

	public boolean isContainsDuplicate() {
		return containsDuplicate;
	}
	public RecMotivoVarType[] getListOfEventi() {
		return listOfEventi;
	}
	
}
