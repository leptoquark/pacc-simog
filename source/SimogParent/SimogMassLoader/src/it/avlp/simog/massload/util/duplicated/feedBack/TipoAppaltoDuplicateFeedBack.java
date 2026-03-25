package it.avlp.simog.massload.util.duplicated.feedBack;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.massload.xmlbeans.TipiAppaltoType;

public class TipoAppaltoDuplicateFeedBack extends DuplicateFeedBack{


	private TipiAppaltoType[] listOfTipoAppalto;
	
	public TipoAppaltoDuplicateFeedBack(ValidationBean duplicateError, boolean containsDuplicate, TipiAppaltoType[] listOfTipoAppalto) {
		super(duplicateError,containsDuplicate);
		this.listOfTipoAppalto = listOfTipoAppalto;
	}

	public ValidationBean getDuplicateError() {
		return duplicateWarning;
	}

	public boolean isContainsDuplicate() {
		return containsDuplicate;
	}

	public TipiAppaltoType[] getListOfTipoAppalto() {
		return listOfTipoAppalto;
	}
	
	
	
}
