package it.avlp.simog.massload.util.duplicated.feedBack;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.massload.xmlbeans.PosizioneType;

public class PosizioneDuplicateFeedBack extends DuplicateFeedBack {

	private PosizioneType[] listOfPosizione;
	
	public PosizioneDuplicateFeedBack(ValidationBean duplicateError, boolean containsDuplicate, PosizioneType[] listOfPosizione) {
		super(duplicateError, containsDuplicate);
		this.listOfPosizione = listOfPosizione;
	}
	public ValidationBean getDuplicateError() {
		return duplicateWarning;
	}

	public boolean isContainsDuplicate() {
		return containsDuplicate;
	}
	public PosizioneType[] getListOfPosizione() {
		return listOfPosizione;
	}
}
