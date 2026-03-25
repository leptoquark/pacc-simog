package it.avlp.simog.massload.util.duplicated.feedBack;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.massload.xmlbeans.FinanziamentoType;

public class FinanziamentoDuplicateFeedBack extends DuplicateFeedBack {

	private FinanziamentoType[] listOfFinanziamenti;
	
	public FinanziamentoDuplicateFeedBack(ValidationBean duplicateError,boolean containsDuplicate, FinanziamentoType[] listOfFinanziamenti) {
		super(duplicateError, containsDuplicate);
		this.listOfFinanziamenti = listOfFinanziamenti;
	}
	public ValidationBean getDuplicateError() {
		return duplicateWarning;
	}

	public boolean isContainsDuplicate() {
		return containsDuplicate;
	}
	public FinanziamentoType[] getListOfFinanziamenti() {
		return listOfFinanziamenti;
	}
	
	
}
