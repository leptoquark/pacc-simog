package it.avlp.simog.massload.validation;

import it.avlp.simog.beans.ValidationBean;

import java.util.ArrayList;
import java.util.List;

public class ValidationBeanHandler {

	private List<ValidationBean> listaValidazioniSchedaCompleta;
	private String CUI;
	private String CIG;
	
	public ValidationBeanHandler(List<ValidationBean> listaValidazioniSchedaCompleta,
									String CUI,
									String CIG) {
		this.CIG = CIG;
		this.CUI = CUI;
		this.listaValidazioniSchedaCompleta = listaValidazioniSchedaCompleta;
	}

	public List<ValidationBean> getListaValidazioniSchedaCompleta() {
		return listaValidazioniSchedaCompleta;
	}

	/**
	 * assegna alla lista interna la lista passata
	 * @param listaValidazioniSchedaCompleta
	 */
	public void setListaValidazioniSchedaCompleta(List<ValidationBean> listaValidazioniSchedaCompleta) {
		this.listaValidazioniSchedaCompleta = listaValidazioniSchedaCompleta;
	}
	/**
	 * aggiunge alla lista interna la lista passata, evitando di inserire duplicati
	 * @param listaValidazioniSchedaCompleta
	 */
	public void addListaValidazioniSchedaCompleta(List<ValidationBean> listaValidazioniSchedaCompleta){
		if(this.listaValidazioniSchedaCompleta != null){
			for(ValidationBean vb : listaValidazioniSchedaCompleta){
				this.addValidazione(vb);
			}
		}else{
			this.listaValidazioniSchedaCompleta = listaValidazioniSchedaCompleta;
		}
	}
	/**
	 * aggiunge un elemento alla lista
	 * @param vb
	 */
	public void addValidazione(ValidationBean vb){
		if(this.listaValidazioniSchedaCompleta != null){
			if(!this.alreadyExist(vb)){
				this.listaValidazioniSchedaCompleta.add(vb);
			}
		}else{			
			this.listaValidazioniSchedaCompleta = new ArrayList<ValidationBean>();
			this.listaValidazioniSchedaCompleta.add(vb);
		}		
	}
	/**
	 * controlla che non esista già l'elemento
	 * @param vb
	 * @return
	 */
	public boolean alreadyExist(ValidationBean vb){
		if(this.listaValidazioniSchedaCompleta != null){
			for(ValidationBean vb1 : this.listaValidazioniSchedaCompleta){
				if(vb1.getSeverity().equalsIgnoreCase(vb.getSeverity())){
					if(vb1.getElemento() == vb.getElemento()){
						if(vb1.getMessage().equals(vb.getMessage())){
							return true;
						}
					}
				}
			}
		}return false;
	}
	
	public String getCUI() {
		return CUI;
	}

	public void setCUI(String cui) {
		if(cui != null){
			CUI = "".equals(cui) ? null : cui;
		}
	}

	public String getCIG() {
		return CIG;
	}

	public void setCIG(String cig) {
		if(cig != null){
			CIG = "".equals(cig) ? null : cig;
		}
	}
	/**
	 * controlla se esistono errori nella lista locale
	 * @return
	 */
	public boolean checkIfExistError(){
		if(this.listaValidazioniSchedaCompleta != null && this.listaValidazioniSchedaCompleta.size() > 0){
			for(ValidationBean vb : this.listaValidazioniSchedaCompleta){
				if(vb.getSeverity().equalsIgnoreCase(ValidationBean.VALBEAN_SEV_ERR)){
					return true;
				}
			}
		}return false;
	}
	
	
}
