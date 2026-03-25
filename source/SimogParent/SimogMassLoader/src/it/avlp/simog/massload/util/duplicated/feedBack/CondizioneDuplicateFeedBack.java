package it.avlp.simog.massload.util.duplicated.feedBack;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.massload.xmlbeans.CondizioneType;

public class CondizioneDuplicateFeedBack extends DuplicateFeedBack {

	private CondizioneType[] listOfCondizione;
	
	public CondizioneDuplicateFeedBack(ValidationBean duplicateError, boolean containsDuplicate, CondizioneType[] listOfCondizione){
		super(duplicateError, containsDuplicate);
		this.listOfCondizione = listOfCondizione;
	}
	
	public ValidationBean getDuplicateError() {
		return duplicateWarning;
	}

	public boolean isContainsDuplicate() {
		return containsDuplicate;
	}

	public CondizioneType[] getListOfCondizione() {
		return listOfCondizione;
	}
}
