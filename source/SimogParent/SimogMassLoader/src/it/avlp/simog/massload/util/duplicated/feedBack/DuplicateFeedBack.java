/**
 * 
 */
package it.avlp.simog.massload.util.duplicated.feedBack;

import it.avlp.simog.beans.ValidationBean;

/**
 * @author vletizia
 *
 */
public abstract class DuplicateFeedBack {

	protected ValidationBean duplicateWarning = null;
	protected boolean containsDuplicate = false;
	
	protected DuplicateFeedBack(ValidationBean duplicateError, boolean containsDuplicate){
		this.duplicateWarning = duplicateError;
		this.containsDuplicate = containsDuplicate;
	}
}
