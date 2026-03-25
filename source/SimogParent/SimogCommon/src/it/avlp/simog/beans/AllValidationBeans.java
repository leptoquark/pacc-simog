package it.avlp.simog.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * @author pp
 * 
 * Contiene le informazioni relative a tutte le eccezioni i validazione rilevate
 */
public class AllValidationBeans extends MessageBean{
	
	private ArrayList <ValidationBean> mErrors = new ArrayList <ValidationBean> ();
	private HashMap<String, String> fieldToHighlight = new HashMap<String, String>();
	protected Logger logger =  Logger.getLogger("SIMOG_LOGGER");
	public AllValidationBeans() {
		super("");
	}

	
	/*********************************************************************************************
	 * Aggiunge un ValidationBean alla lista mErrors restituendo la nuova dimensione della 
	 * lista.
	 * param bean : Un ValidationBean contenente la info di validazione
	 * return int : un intero che rappresenta la dimensione della lista dopo l'inserimento
	 *********************************************************************************************/
	private int add (ValidationBean bean) {
		mErrors.add(bean);
		mErrors.trimToSize();
		return mErrors.size();
	}
	/**
	 * Metodo per l'aggiunta della lista dei validations bean di un AllValidationBean
	 * in un'altro 
	 * 
	 * @param avb
	 */
	public void add(AllValidationBeans avb){
		List<ValidationBean> lvb = avb.getAll();
		for(ValidationBean vb : lvb){
			this.mErrors.add(vb);
		}
		this.fieldToHighlight = avb.getFieldToHighlight();
	}
	/*********************************************************************************************
	 * Aggiunge un messaggio al validatore 
	 * @param message Stringa contenente il messaggio
	 * @return int - intero 
	 *********************************************************************************************/
	public int addValidationInfo (String message)  { 
		return addValidationInfoElemento(message, 0);
	}
	
	
	/**********************************************************************************************
	 * Aggiunge il messaggio di info nella lista dei messaggi mError, restituisce un intero relativo alla
	 * nuova dimensione della lista
	 * @param message Stringa per il messaggio
	 * @param elemento int
	 * @return int relativo alla lunghezza della lista contenente i messaggi di validazione
	 **********************************************************************************************/
	public int addValidationInfoElemento (String message, int elemento)  {
		try {
			ValidationBean bean = new ValidationBean(message, ValidationBean.VALBEAN_SEV_INFO, elemento);
			
			mErrors.add(bean);
			mErrors.trimToSize();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			e.printStackTrace();
		}
		
		return mErrors.size();
	}
	public int addValidationInfoProgressivo (String message, int progressivo)  {
		try {
			ValidationBean bean = new ValidationBean(message, ValidationBean.VALBEAN_SEV_INFO, 0);
			bean.setProgressivo(progressivo);
			mErrors.add(bean);
			mErrors.trimToSize();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			e.printStackTrace();
		}
		
		return mErrors.size();
	}

	/**********************************************************************************************
	 * Aggiunge un messaggio di validazione di tipo Warning
	 * @param message Stringa per il messaggio
	 * @return int relativo al numero dei messaggi di validazione presenti in totale
	 **********************************************************************************************/
	public int addValidationWarn (String message)  { 
		return addValidationWarnElemento(message, 0);
	}

	/***********************************************************************************************
	 * int <b>addValidationWarn</b> ( String, int )<br>
	 * Inserisce il messaggio di validazione nella lista dei messaggi di validazione
	 * @param message Stringa per il messaggio
	 * @param elemento int
	 * @return int - rappresenta il numero di messaggi al momento presenti nella lista
	 ***********************************************************************************************/
	public int addValidationWarnElemento (String message, int elemento)  {
		try {
			ValidationBean bean = new ValidationBean(message, ValidationBean.VALBEAN_SEV_WARN, elemento);
			
			mErrors.add(bean);
			mErrors.trimToSize();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			e.printStackTrace();
		}
		
		return mErrors.size();
	}
	public int addValidationWarnProgressivo (String message, int progressivo)  {
		try {
			ValidationBean bean = new ValidationBean(message, ValidationBean.VALBEAN_SEV_WARN, 0);
			bean.setProgressivo(progressivo);
			
			mErrors.add(bean);
			mErrors.trimToSize();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			e.printStackTrace();
		}
		
		return mErrors.size();
	}

	/****************************************************************************************************
	 * int <b>addValidatorError</b> ( String ) <br>
	 * aggiunge un messaggio nella lista dei messaggi di validazione di tipo error
	 * @param message stringa per il messaggio
	 * @return int relativo al numero di nessaggi presenti in lista
	 ****************************************************************************************************/
	public int addValidationErr (String message) { 
		return addValidationErrElemento(message, 0);
	}

	/****************************************************************************************************
	 * int <b>addValidatorError</b> ( String, int ) <br>
	 * @param message stringa per il messaggio
	 * @param elemento int
	 * @return int relativo al numero di nessaggi presenti in lista
	 ****************************************************************************************************/
	public int addValidationErrElemento (String message, int elemento){
		try {
			ValidationBean bean = new ValidationBean(message, ValidationBean.VALBEAN_SEV_ERR, elemento);
			
			mErrors.add(bean);
			mErrors.trimToSize();
		} catch (Exception e) {
			
			logger.fatal(e.getMessage());
			e.printStackTrace();
		}
		
		return mErrors.size();
	}
	/**
	 * Nuovo parametro della validation bean ovvero il progressivo
	 * che si riferisce alla posizione della scheda multipla corrente.
	 * 
	 * @param message
	 * @param progressivo
	 * @return
	 */
	public int addValidationErrProgressivo (String message, int progressivo){
		try {
			ValidationBean bean = new ValidationBean(message, ValidationBean.VALBEAN_SEV_ERR, 0);
			bean.setProgressivo(progressivo);
			mErrors.add(bean);
			mErrors.trimToSize();
		} catch (Exception e) {
			
			logger.fatal(e.getMessage());
			e.printStackTrace();
		}
		
		return mErrors.size();
	}

	
	/*****************************************************************************************************
	 * pulisce la lista dei messaggi di validazione
	 *****************************************************************************************************/
	public void clear () {
		mErrors.clear();
	}
	
	/*****************************************************************************************************
	 * Restituisce il ValidationBean contenuto nella lista dei messaggi di validazione con indice pari a elem
	 * @param elem int che rappresenta l'indice per il recupero del messaggio di validazione dalla lista
	 * @return ValidationBean
	 *****************************************************************************************************/
	public ValidationBean get(int elem){
		
		if (elem < 0 || elem > mErrors.size()) return null;
		
		return mErrors.get(elem);
	}
	
	/******************************************************************************************************
	 * Restituisce la lunghezza della lista dei messaggi di validazione
	 * @return int - lunghezza della lista dei messaggi di validazione mErrors 
	 ******************************************************************************************************/
	public int getSize(){
				
		return mErrors.size();
	}

	/********************************************************************************************************
	 * Restituisce una lista di messaggi con il grado di severita' richiesto
	 * @param severity Stringa che indica il grado di severita' 
	 * @return AllValidationBeans
	 ********************************************************************************************************/
	public AllValidationBeans getAllBySeverity(String severity){
		
		AllValidationBeans temp = new AllValidationBeans();
		
		for (int i=0;i<mErrors.size();i++){
			if (severity.equals(mErrors.get(i).getSeverity()))
				temp.add(mErrors.get(i));
		}
				
		return temp;
	}
	
	/********************************************************************************************************
	 * GM. Restituisce una lista di messaggi escludendo quelli di errore,
	 * utile in 3.02 per coerenza con il pregresso, con schede già confermate che mostrano errori.
	 * @return AllValidationBeans
	 ********************************************************************************************************/
	public AllValidationBeans getAllInfoEWarn(){
		
		AllValidationBeans temp = new AllValidationBeans();
		
		for (int i=0;i<mErrors.size();i++){
			if (ValidationBean.VALBEAN_SEV_INFO.equals(mErrors.get(i).getSeverity()) 
				// PP no warnings (piccinini)	||	ValidationBean.VALBEAN_SEV_WARN.equals(mErrors.get(i).getSeverity())
				)
				temp.add(mErrors.get(i));
		}		
		return temp;
	}
	
	/*********************************************************************************************************
	 * restituisce tutta la lista dei messaggi di validazione  
	 * @return lista di validationBean
	 *********************************************************************************************************/
	public List<ValidationBean> getAll(){
		return this.mErrors;
	}
	
	/**
	 * Attenzione ha senso invocare questo metodo solo se questa istanza contiene tutti le severita'
	 * di errore, se e' gia stato filtrato non ha senso !
	 * La stranezza sta nel fatto che l'invocazione del metodo "getAllBySeverity(String severity)"
	 * ritorna un'oggetto dello stesso tipo dell'oggetto sul quale si e' invocata l'azione..
	 * 
	 * @return
	 */
	public Map<String, List<ValidationBean>> getListedBySeverity(){
		Map<String, List<ValidationBean>> messaggiDiValidazione = new TreeMap<String, List<ValidationBean>>();
		
		messaggiDiValidazione.put(ValidationBean.VALBEAN_SEV_ERR, getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getAll());
		messaggiDiValidazione.put(ValidationBean.VALBEAN_SEV_WARN, getAllBySeverity(ValidationBean.VALBEAN_SEV_WARN).getAll());
		messaggiDiValidazione.put(ValidationBean.VALBEAN_SEV_INFO, getAllBySeverity(ValidationBean.VALBEAN_SEV_INFO).getAll());

		return messaggiDiValidazione;
	}


	@Override
	public String toString() {
		
		String retVal = "";
		for (Iterator iter = this.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getAll().iterator(); iter.hasNext();) {
			ValidationBean element = (ValidationBean) iter.next();
			
			retVal = retVal + element.getSeverity() + " - " + element.getMessage() + "\n\r";
		}
		
		return retVal;
	}

   /********************************************************************************************************
    * Restituisce true se esiste il codice messaggio richiesto
    ********************************************************************************************************/
   public boolean existMessage(String codice){
      
      boolean retVal = false;
      
      for (int i=0;i<mErrors.size();i++){
         if (codice.equals(mErrors.get(i).getCodiceErrore()))
            retVal = true;
         break;
      }
            
      return retVal;
   }

	public void addValidationField (String field) { 
		 fieldToHighlight.put(field, field);
	}


	public HashMap<String, String> getFieldToHighlight() {
		return fieldToHighlight;
	}
	
	
}
