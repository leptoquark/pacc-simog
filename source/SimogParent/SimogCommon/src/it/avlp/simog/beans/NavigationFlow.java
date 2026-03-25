package it.avlp.simog.beans;

import it.avlp.simog.db.SimogFlags;

public abstract class NavigationFlow {
	private NavigationBean navigationBean;
	private boolean aggiungibile = true;
	private boolean noInserimenti = false;
	
	// check se variazioni anagrafiche attive
	public boolean isVarAnagActive(){
		return SimogFlags.isVarAnagActive();
	}
	
	public NavigationBean getNavigationBean() {
		return navigationBean;
	}
	public void setNavigationBean(NavigationBean navigationBean) {
		this.navigationBean = navigationBean;
	}
	
	public boolean isReadOnly(){
		if(this.navigationBean != null)
			return this.navigationBean.isReadonly();
		else return false;
	}
	
	public boolean isDelegaScheda(){
		if(this.navigationBean != null)
			return this.navigationBean.isDelegaScheda();
		else return false;
	}
	
	public void setAggiungibile(boolean aggiungibile) {
		this.aggiungibile = aggiungibile;
	}
	public boolean isAggiungibile() {
		return aggiungibile;
	}
	public boolean isRiaggiudicata() {
		return navigationBean != null ? navigationBean.isRiaggiudicata() : false;
	}
	
	public boolean isRiaggiudicabile() {
		return navigationBean != null ? navigationBean.isRiaggiudicabile() : false;
	}

   public boolean isNoInserimenti() {
      return noInserimenti;
   }

   public void setNoInserimenti(boolean noInserimenti) {
      this.noInserimenti = noInserimenti;
   }
}
