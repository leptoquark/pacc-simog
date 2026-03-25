package it.avlp.simog.massload.util.duplicated.feedBack;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.massload.xmlbeans.DittaAusiliariaType;

public class DittaAusiliariaDuplicateFeedBack extends DuplicateFeedBack {

	private DittaAusiliariaType[] listOfDitteAusiliarie;
	
	public DittaAusiliariaDuplicateFeedBack(ValidationBean duplicateError, boolean containsDuplicate, DittaAusiliariaType[] listOfDitteAusiliarie) {
		super(duplicateError, containsDuplicate);
		this.listOfDitteAusiliarie = listOfDitteAusiliarie;
	}
	public ValidationBean getDuplicateError() {
		return duplicateWarning;
	}

	public boolean isContainsDuplicate() {
		return containsDuplicate;
	}
	public DittaAusiliariaType[] getListOfDitteAusiliarie() {
		return listOfDitteAusiliarie;
	}

}
