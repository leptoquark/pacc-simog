package it.avlp.simog.massload.util.duplicated.feedBack;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.massload.xmlbeans.ResponsabileType;

public class AnagraficaResponsabileDuplicateFeedBack extends DuplicateFeedBack {

	private ResponsabileType[] arrayOfAnagraficheResponsabili;
	
	public AnagraficaResponsabileDuplicateFeedBack(ValidationBean duplicateError, boolean containsDuplicate,ResponsabileType[] arrayOfAnagraficheResponsabili) {
		super(duplicateError, containsDuplicate);
		this.arrayOfAnagraficheResponsabili = arrayOfAnagraficheResponsabili;
	}

	public ResponsabileType[] getArrayOfAnagraficheResponsabili() {
		return arrayOfAnagraficheResponsabili;
	}
	public ValidationBean getDuplicateError() {
		return duplicateWarning;
	}

	public boolean isContainsDuplicate() {
		return containsDuplicate;
	}

}
