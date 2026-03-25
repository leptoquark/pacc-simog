package it.avlp.simog.massload.esito;

import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.EsitoOperazioneBean;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.ValidationBean;

import java.util.ArrayList;
import java.util.List;

public class EsitoValidazioneBean extends EsitoOperazioneBean {

	private List<SchedaSpecificaValidationBean> listOfValidations;
	private String nomeScheda;
	private String cig;
	private String cui;
	private int progressivoSchedaCompleta;
	public String id_locale;
	public String id_simog;
	
	/**
	 * @param nomeScheda
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 */
	public EsitoValidazioneBean(String nomeScheda, String cig, String cui,int progressivoSchedaCompleta) {
		this.nomeScheda = nomeScheda;
		this.cig = cig;
		this.cui = cui;
		this.progressivoSchedaCompleta = progressivoSchedaCompleta;
	}

//	/**
//	 * L'id locale se presente nel file xml ce lo metto
//	 * @deprecated
//	 * 
//	 * @param nomeScheda
//	 * @param cig
//	 * @param cui
//	 * @param id_locale
//	 * @param progressivoSchedaCompleta
//	 */
//	public EsitoValidazioneBean(String nomeScheda, String cig, String cui,String id_locale, int progressivoSchedaCompleta) {
//		this.nomeScheda = nomeScheda;
//		this.cig = cig;
//		this.cui = cui;
//		this.progressivoSchedaCompleta = progressivoSchedaCompleta;
//		this.id_locale = id_locale;
//	}
	
	  /********************************************************************************************************
    * Restituisce una lista di messaggi con il grado di severita' richiesto
    * @param severity Stringa che indica il grado di severita' 
    * @return AllValidationBeans
    ********************************************************************************************************/
   public List<SchedaSpecificaValidationBean> getAllBySeverity(String severity){
      
      List<SchedaSpecificaValidationBean> temp = new ArrayList<SchedaSpecificaValidationBean>();
      
      for (int i=0;i<listOfValidations.size();i++){
         if (severity.equals(listOfValidations.get(i).getSeverity()))
            temp.add(listOfValidations.get(i));
      }
            
      return temp;
   }
   
	/**
	 * @return
	 */
	public List<SchedaSpecificaValidationBean> getListOfValidations() {
		return listOfValidations;
	}

	/**
	 * @param listOfValidations
	 */
	public void setListOfValidations(List<SchedaSpecificaValidationBean> listOfValidations) {
		this.listOfValidations = listOfValidations;
	}
	/**
	 * Effettua l'add alla lista esistente, se non esiste crea una nuova istanza.
	 * 
	 * @param listOfValidations
	 */
	public void setListOfValidationsByConversion(List<ValidationBean> listOfValidations) {
		if(this.listOfValidations == null) this.listOfValidations = new ArrayList<SchedaSpecificaValidationBean>();
		
		for(ValidationBean validationCorrente : listOfValidations){

				this.listOfValidations.add(new SchedaSpecificaValidationBean(validationCorrente.getMessage(), 
						validationCorrente.getSeverity(), validationCorrente.getElemento(), progressivoSchedaCompleta,validationCorrente.getProgressivo(), nomeScheda, cig, cui, id_simog, id_locale));

		}
	}

	/**
	 * da utilizzare quando si passa ad uno ad uno gli elementi di una lista al validatore, in modo da ripristinare il corretto valore dell'elemento
	 * 
	 * @param listOfValidations
	 * @param overrideElementoInValidation: il valore dal setttare nel tag elemento per il feedback
	 */
	public void setListOfValidationsByConversion(List<ValidationBean> listOfValidations, int overrideProgressivoSchedaMultipla) {
		if(this.listOfValidations == null) this.listOfValidations = new ArrayList<SchedaSpecificaValidationBean>();
		
		for(ValidationBean validationCorrente : listOfValidations){

				this.listOfValidations.add(new SchedaSpecificaValidationBean(validationCorrente.getMessage(), 
						validationCorrente.getSeverity(), validationCorrente.getElemento(), progressivoSchedaCompleta,overrideProgressivoSchedaMultipla, nomeScheda, cig, cui, id_simog, id_locale));

		}
	}
}
