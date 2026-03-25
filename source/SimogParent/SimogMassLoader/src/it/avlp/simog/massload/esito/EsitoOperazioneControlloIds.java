package it.avlp.simog.massload.esito;

import it.avlp.simog.beans.EsitoOperazioneBean;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;

import java.util.List;

public final class EsitoOperazioneControlloIds extends EsitoOperazioneBean {

	private boolean isDatiComuniOk = true;
	private SchedaSpecificaValidationBean datiComuniValidation;
	
	private boolean isAggiudicazioneOk = true;
	private SchedaSpecificaValidationBean AggiudicazioneValidation;
	
	private boolean isInizioOk = true;
	private SchedaSpecificaValidationBean inizioValidation;
	
    private boolean isStipulaOk = true;
    private SchedaSpecificaValidationBean stipulaValidation;

    private boolean isConclusioneOk = true;
	private SchedaSpecificaValidationBean conclusioneValidation;
	
	private boolean isCollaudoOk = true;
	private SchedaSpecificaValidationBean collaudoValidation;
	
	private boolean isAvanzamentiOk = true;
	private List<SchedaSpecificaValidationBean> avanzamentiValidation;
	
	private boolean isAccordiOk = true;
	private List<SchedaSpecificaValidationBean> accordiValidation;
	
	private boolean isRitardiOk = true;
	private List<SchedaSpecificaValidationBean> ritardiValidation;
	
	private boolean isSospensioniOk = true;
	private List<SchedaSpecificaValidationBean> sospensioniValidation;
	
	private boolean isSubappaltiOk = true;
	private List<SchedaSpecificaValidationBean> subappaltiValidation;
	
	private boolean isVariantiOk = true;
	private List<SchedaSpecificaValidationBean> variantiValidation;
	
	//Ticket ALM #1489
    private boolean isAdesioneOk = true;
    private List<SchedaSpecificaValidationBean> adesioneValidation;
    //Fine Ticket ALM #1489
	
	private List<SchedaSpecificaValidationBean> idsValidation;
	
	
	
	
	/**
	 * Costruttore.
	 * NOTA: In fase di istanziazione tutti i flag vengono inizializzati a true il che vuol dire che se e' tutto
	 * 			ok, il che vuol dire che non occorre modificare questo oggetto se non ci sono errori
	 */
	public EsitoOperazioneControlloIds(){}
	
	public List<SchedaSpecificaValidationBean> getIdsValidation() {
		return idsValidation;
	}
	public void setIdsValidation(List<SchedaSpecificaValidationBean> idsValidation) {
		this.idsValidation = idsValidation;
	}
	public void addIdsValidation(SchedaSpecificaValidationBean validation) {
		this.idsValidation = super.addElements(this.idsValidation , validation);
	}
	public SchedaSpecificaValidationBean getDatiComuniValidation() {
		return datiComuniValidation;
	}
	public void setDatiComuniValidation(
			SchedaSpecificaValidationBean datiComuniValidation) {
		this.datiComuniValidation = datiComuniValidation;
	}
	public SchedaSpecificaValidationBean getAggiudicazioneValidation() {
		return AggiudicazioneValidation;
	}
	public void setAggiudicazioneValidation(
			SchedaSpecificaValidationBean aggiudicazioneValidation) {
		AggiudicazioneValidation = aggiudicazioneValidation;
	}
	public SchedaSpecificaValidationBean getInizioValidation() {
		return inizioValidation;
	}
	public void setInizioValidation(SchedaSpecificaValidationBean inizioValidation) {
		this.inizioValidation = inizioValidation;
	}
	public SchedaSpecificaValidationBean getConclusioneValidation() {
		return conclusioneValidation;
	}
	public void setConclusioneValidation(
			SchedaSpecificaValidationBean conclusioneValidation) {
		this.conclusioneValidation = conclusioneValidation;
	}
	public SchedaSpecificaValidationBean getCollaudoValidation() {
		return collaudoValidation;
	}
	public void setCollaudoValidation(
			SchedaSpecificaValidationBean collaudoValidation) {
		this.collaudoValidation = collaudoValidation;
	}
	public List<SchedaSpecificaValidationBean> getAvanzamentiValidation() {
		return avanzamentiValidation;
	}
	public void setAvanzamentiValidation(
			List<SchedaSpecificaValidationBean> avanzamentiValidation) {
		this.avanzamentiValidation = avanzamentiValidation;
	}
	public List<SchedaSpecificaValidationBean> getAccordiValidation() {
		return accordiValidation;
	}
	public void setAccordiValidation(
			List<SchedaSpecificaValidationBean> accordiValidation) {
		this.accordiValidation = accordiValidation;
	}
	public List<SchedaSpecificaValidationBean> getRitardiValidation() {
		return ritardiValidation;
	}
	public void setRitardiValidation(
			List<SchedaSpecificaValidationBean> ritardiValidation) {
		this.ritardiValidation = ritardiValidation;
	}
	public List<SchedaSpecificaValidationBean> getSospensioniValidation() {
		return sospensioniValidation;
	}
	public void setSospensioniValidation(
			List<SchedaSpecificaValidationBean> sospensioniValidation) {
		this.sospensioniValidation = sospensioniValidation;
	}
	public List<SchedaSpecificaValidationBean> getSubappaltiValidation() {
		return subappaltiValidation;
	}
	public void setSubappaltiValidation(
			List<SchedaSpecificaValidationBean> subappaltiValidation) {
		this.subappaltiValidation = subappaltiValidation;
	}
	public List<SchedaSpecificaValidationBean> getVariantiValidation() {
		return variantiValidation;
	}
	public void setVariantiValidation(
			List<SchedaSpecificaValidationBean> variantiValidation) {
		this.variantiValidation = variantiValidation;
	}
	public boolean isDatiComuniOk() {
		return isDatiComuniOk;
	}
	public void setDatiComuniOk(boolean isDatiComuniOk) {
		this.isDatiComuniOk = isDatiComuniOk;
	}
	public boolean isAggiudicazioneOk() {
		return isAggiudicazioneOk;
	}
	public void setAggiudicazioneOk(boolean isAggiudicazioneOk) {
		this.isAggiudicazioneOk = isAggiudicazioneOk;
	}
	public boolean isInizioOk() {
		return isInizioOk;
	}
	public void setInizioOk(boolean isInizioOk) {
		this.isInizioOk = isInizioOk;
	}
	public boolean isConclusioneOk() {
		return isConclusioneOk;
	}
	public void setConclusioneOk(boolean isConclusioneOk) {
		this.isConclusioneOk = isConclusioneOk;
	}
	public boolean isCollaudoOk() {
		return isCollaudoOk;
	}
	public void setCollaudoOk(boolean isCollaudoOk) {
		this.isCollaudoOk = isCollaudoOk;
	}
	public boolean isAvanzamentiOk() {
		return isAvanzamentiOk;
	}
	public void setAvanzamentiOk(boolean isAvanzamentiOk) {
		this.isAvanzamentiOk = isAvanzamentiOk;
	}
	public boolean isAccordiOk() {
		return isAccordiOk;
	}
	public void setAccordiOk(boolean isAccordiOk) {
		this.isAccordiOk = isAccordiOk;
	}
	public boolean isRitardiOk() {
		return isRitardiOk;
	}
	public void setRitardiOk(boolean isRitardiOk) {
		this.isRitardiOk = isRitardiOk;
	}
	public boolean isSospensioniOk() {
		return isSospensioniOk;
	}
	public void setSospensioniOk(boolean isSospensioniOk) {
		this.isSospensioniOk = isSospensioniOk;
	}
	
	public boolean isSubappaltiOk() {
		return isSubappaltiOk;
	}
	public void setSubappaltiOk(boolean isSubappaltiOk) {
		this.isSubappaltiOk = isSubappaltiOk;
	}
	public boolean isVariantiOk() {
		return isVariantiOk;
	}
	public void setVariantiOk(boolean isVariantiOk) {
		this.isVariantiOk = isVariantiOk;
	}

   public boolean isStipulaOk() {
      return isStipulaOk;
   }

   public void setStipulaOk(boolean isStipulaOk) {
      this.isStipulaOk = isStipulaOk;
   }

   public SchedaSpecificaValidationBean getStipulaValidation() {
      return stipulaValidation;
   }

   public void setStipulaValidation(SchedaSpecificaValidationBean stipulaValidation) {
      this.stipulaValidation = stipulaValidation;
   }
   
   
 //Ticket ALM #1489
   public boolean isAdesioneOk() {
           return isAdesioneOk;
   }

   public void setAdesioneOk(boolean isAdesioneOk) {
           this.isAdesioneOk = isAdesioneOk;
   }

   public List<SchedaSpecificaValidationBean> getAdesioneValidation() {
           return adesioneValidation;
   }

   public void setAdesioneValidation(
                   List<SchedaSpecificaValidationBean> adesioneValidation) {
           this.adesioneValidation = adesioneValidation;
   }
   //Fine Ticket ALM #1489

   
   
	
}
