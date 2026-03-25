package it.avlp.simog.massload.util.duplicated.feedBack;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.massload.xmlbeans.RequisitoType;

public class RequisitoDuplicateFeedBack extends DuplicateFeedBack {

	private RequisitoType[] listOfRequisito;
	
	public RequisitoDuplicateFeedBack(ValidationBean duplicateError, boolean containsDuplicate, RequisitoType[] listOfRequisito) {
		super(duplicateError, containsDuplicate);
		this.listOfRequisito = listOfRequisito;
	}
	public ValidationBean getDuplicateError() {
		return duplicateWarning;
	}

	public boolean isContainsDuplicate() {
		return containsDuplicate;
	}
	public RequisitoType[] getListOfRequisito() {
		return listOfRequisito;
	}

	
}
