package it.avlp.simog.massload.esito;

import it.avlp.simog.beans.EsitoOperazioneBean;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.massload.bean.IdsSchedaXML;

import java.util.ArrayList;
import java.util.List;

public class EsitoOperazioneControlloBean extends EsitoOperazioneBean {

	private IdsSchedaXML schedaCorrente;
	
	private boolean isValidCig = true;
	private SchedaSpecificaValidationBean cigValidation;
	
	private boolean isValidCfRup = true;
	private SchedaSpecificaValidationBean cfRupValidation;
	
	private boolean isMatchCigCui = true;
	private SchedaSpecificaValidationBean cigCuiValidation;
	
	private boolean existCui = true;
	private SchedaSpecificaValidationBean cuiValidation;
	
//	private EsitoOperazioneValidateAnagrafiche anaAgg;
	private boolean areValidPosizioni = true;
	private List<SchedaSpecificaValidationBean> posizioniValidation;
	
//	private EsitoOperazioneValidateAnagrafiche anaRes;
	private boolean areValidIncaricati = true;
	private List<SchedaSpecificaValidationBean> incaricatiValidation;
	
//	private EsitoOperazioneControlloIds controlliIdSimog;
	private boolean areValidIds = true;
	private List<SchedaSpecificaValidationBean> idsValidation;
	




//	public EsitoOperazioneControlloIds getControlliIdSimog() {
//		return controlliIdSimog;
//	}
//
//	public void setControlliIdSimog(EsitoOperazioneControlloIds controlliIdSimog) {
//		this.controlliIdSimog = controlliIdSimog;
//	}

	public boolean isAreValidPosizioni() {
		return areValidPosizioni;
	}

	public void setAreValidPosizioni(boolean areValidPosizioni) {
		this.areValidPosizioni = areValidPosizioni;
	}

	public List<SchedaSpecificaValidationBean> getPosizioniValidation() {
		return posizioniValidation;
	}

	public void setPosizioniValidation(
			List<SchedaSpecificaValidationBean> posizioniValidation) {
		this.posizioniValidation = posizioniValidation;
	}

	public boolean isAreValidIncaricati() {
		return areValidIncaricati;
	}

	public void setAreValidIncaricati(boolean areValidIncaricati) {
		this.areValidIncaricati = areValidIncaricati;
	}

	public List<SchedaSpecificaValidationBean> getIncaricatiValidation() {
		return incaricatiValidation;
	}

	public void setIncaricatiValidation(
			List<SchedaSpecificaValidationBean> incaricatiValidation) {
		this.incaricatiValidation = incaricatiValidation;
	}

	public boolean isAreValidIds() {
		return areValidIds;
	}

	public void setAreValidIds(boolean areValidIds) {
		this.areValidIds = areValidIds;
	}

	public List<SchedaSpecificaValidationBean> getIdsValidation() {
		return idsValidation;
	}


	public void setIdsValidation(List<SchedaSpecificaValidationBean> idsValidation) {
		this.idsValidation = idsValidation;
	}
	/**
	 * Se la lista e gia presente aggiunge solamente
	 * @param idsValidation
	 */
	public void addIdsValidation(List<SchedaSpecificaValidationBean> idsValidation) {
		if(this.idsValidation == null)
			this.idsValidation = idsValidation;
		else
			this.idsValidation.addAll(idsValidation);
	}

	public IdsSchedaXML getSchedaCorrente() {
		return schedaCorrente;
	}

	public void setSchedaCorrente(IdsSchedaXML schedaCorrente) {
		this.schedaCorrente = schedaCorrente;
	}

	public boolean isValidCig() {
		return isValidCig;
	}

	public void setValidCig(boolean isValidCig) {
		this.isValidCig = isValidCig;
	}

	public boolean isValidCfRup() {
		return isValidCfRup;
	}

	public void setValidCfRup(boolean isValidCfRup) {
		this.isValidCfRup = isValidCfRup;
	}

	public boolean isMatchCigCui() {
		return isMatchCigCui;
	}

	public void setMatchCigCui(boolean isMatchCigCui) {
		this.isMatchCigCui = isMatchCigCui;
	}

	public boolean isExistCui() {
		return existCui;
	}

	public void setExistCui(boolean existCui) {
		this.existCui = existCui;
	}

//	public EsitoOperazioneValidateAnagrafiche getAnaAgg() {
//		return anaAgg;
//	}
//
//	public void setAnaAgg(EsitoOperazioneValidateAnagrafiche anaAgg) {
//		this.anaAgg = anaAgg;
//	}
//
//	public EsitoOperazioneValidateAnagrafiche getAnaRes() {
//		return anaRes;
//	}
//
//	public void setAnaRes(EsitoOperazioneValidateAnagrafiche anaRes) {
//		this.anaRes = anaRes;
//	}
	
//	/**
//	 * Metodo che si occupa di costruire una lista di validationBean a partire
//	 * dai dati contenuti in questa istanza.
//	 * Se l'esito dell'operazione e' false ritorna la lista dei validations beans
//	 * altrimenti ritorna null;
//	 * 
//	 * @return
//	 */
//	public List<SchedaSpecificaValidationBean> getAsValidationBeans() throws Exception{
//		if(!super.esitoOperazione){
//			String cig = this.schedaCorrente.getCig();
//			String cui = this.schedaCorrente.getCui();
//			int progressivoSchedaCompleta = this.schedaCorrente.getCardinalitaSchedaCompleta(); 
//			List<SchedaSpecificaValidationBean> listaErrori = new ArrayList<SchedaSpecificaValidationBean>();
//			if(!this.isValidCig){
//				SchedaSpecificaValidationBean invalidCig = new SchedaSpecificaValidationBean("invalidCig", ValidationBean.VALBEAN_SEV_ERR, 0,progressivoSchedaCompleta, IdentificativoSchede.DATI_COMUNI,cig, cui);
//				listaErrori.add(invalidCig);
//			}
//			if(!this.isValidCfRup){
//				SchedaSpecificaValidationBean invalidCfRup = new SchedaSpecificaValidationBean("invalidCfRup", ValidationBean.VALBEAN_SEV_ERR, 0,progressivoSchedaCompleta, IdentificativoSchede.DATI_COMUNI,cig, cui);
//				listaErrori.add(invalidCfRup);
//			}
//			if(!this.existCui){
//				SchedaSpecificaValidationBean invalidCui = new SchedaSpecificaValidationBean("invalidCui", ValidationBean.VALBEAN_SEV_ERR, 0,progressivoSchedaCompleta, IdentificativoSchede.AGGIUDICAZIONE,cig, cui);
//				listaErrori.add(invalidCui);
//			}
//			if(!this.isMatchCigCui){
//				SchedaSpecificaValidationBean invalidCigCui = new SchedaSpecificaValidationBean("invalidCigCui", ValidationBean.VALBEAN_SEV_ERR, 0,progressivoSchedaCompleta, IdentificativoSchede.AGGIUDICAZIONE,cig, cui);
//				listaErrori.add(invalidCigCui);
//			}
//			if(!this.anaAgg.esitoOperazione){
//
//				listaErrori.addAll(this.anaAgg.getAsValidationBeans(cig, cui, progressivoSchedaCompleta));
//			}
//			if(!this.anaRes.esitoOperazione){
//
//				listaErrori.addAll(this.anaAgg.getAsValidationBeans(cig, cui, progressivoSchedaCompleta));
//			}
//			if(!this.controlliIdSimog.esitoOperazione){
//				listaErrori.addAll(this.controlliIdSimog.getAsValidationBeans(cig, cui, progressivoSchedaCompleta));
//			}
//			
//			return listaErrori;
//		}return null;
//	}
	public List<SchedaSpecificaValidationBean> getAllValidationBeans() throws Exception{
		List<SchedaSpecificaValidationBean> allValidations = new ArrayList<SchedaSpecificaValidationBean>();
		if(!this.isValidCig)
			allValidations.add(this.cigValidation);
		if(!this.isValidCfRup)
			allValidations.add(this.cfRupValidation);
		if(!this.isMatchCigCui)
			allValidations.add(this.cigCuiValidation);
		if(!this.existCui)
			allValidations.add(this.cuiValidation);
		if(!this.areValidPosizioni)
			allValidations.addAll(this.posizioniValidation);
		if(!this.areValidIncaricati)
			allValidations.addAll(this.incaricatiValidation);
		if(!this.areValidIds)
			allValidations.addAll(this.idsValidation);
		return allValidations;
	}

	public SchedaSpecificaValidationBean getCigValidation() {
		return cigValidation;
	}

	public void setCigValidation(SchedaSpecificaValidationBean cigValidation) {
		this.cigValidation = cigValidation;
	}

	public SchedaSpecificaValidationBean getCfRupValidation() {
		return cfRupValidation;
	}

	public void setCfRupValidation(SchedaSpecificaValidationBean cfRupValidation) {
		this.cfRupValidation = cfRupValidation;
	}

	public SchedaSpecificaValidationBean getCigCuiValidation() {
		return cigCuiValidation;
	}

	public void setCigCuiValidation(SchedaSpecificaValidationBean cigCuiValidation) {
		this.cigCuiValidation = cigCuiValidation;
	}

	public SchedaSpecificaValidationBean getCuiValidation() {
		return cuiValidation;
	}

	public void setCuiValidation(SchedaSpecificaValidationBean cuiValidation) {
		this.cuiValidation = cuiValidation;
	}

	
	

	
	
	
}
