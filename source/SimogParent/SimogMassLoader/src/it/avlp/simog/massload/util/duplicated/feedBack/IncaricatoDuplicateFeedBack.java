package it.avlp.simog.massload.util.duplicated.feedBack;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.massload.xmlbeans.IncaricatoType;

public class IncaricatoDuplicateFeedBack extends DuplicateFeedBack {

	private IncaricatoType[] listOfIncaricati;
	
	public IncaricatoDuplicateFeedBack(ValidationBean duplicateError, boolean containsDuplicate, IncaricatoType[] listOfIncaricati) {
		super(duplicateError, containsDuplicate);
		this.listOfIncaricati = listOfIncaricati;
	}
	public ValidationBean getDuplicateError() {
		return duplicateWarning;
	}

	public boolean isContainsDuplicate() {
		return containsDuplicate;
	}
	public IncaricatoType[] getListOfIncaricati() {
		return listOfIncaricati;
	}

	
}
