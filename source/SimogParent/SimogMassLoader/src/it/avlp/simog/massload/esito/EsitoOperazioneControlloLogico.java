package it.avlp.simog.massload.esito;

import it.avlp.simog.beans.EsitoOperazioneBean;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.massload.bean.IdsSchedaXML;
import it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta l'esisto del controllo logico di tutto l'xml (eccetto le parti rimosse per errori formali)
 *  
 * @author vletizia
 *
 */
public class EsitoOperazioneControlloLogico extends EsitoOperazioneBean {

	private boolean someSchedeNonValide;
//	private ArrayList<IdsSchedaXML> listOfSchedeNonValideSecondoFlusso;
	private ArrayList<IdsSchedaXML> listOfSchedeValide = new ArrayList<IdsSchedaXML>();
	private ArrayList<SchedaSpecificaValidationBean> listOfValidationsBeans = new ArrayList<SchedaSpecificaValidationBean>();
	private ArrayList<String> listOfCigNonValidi = new ArrayList<String>();
	
	
	
//	/**
//	 * Viene settato tramite un controllo sulla lista delle schede valide secondo il flusso la variabile della
//	 * super classe per l'esito dell'operazione, se la lista contiene almeno un elemento il flag sara' true,
//	 * se e' vuota o nulla sara' false.
//	 * 
//	 * Se esiste almeno una scheda non valida il flag locale someschedenonvalide avra' valore true.
//	 * 
//	 * @param listOfSchedeNonValideSecondoFlusso
//	 * @param listOfSchedeValide
//	 */
//	public EsitoOperazioneControlloLogico( ArrayList<IdsSchedaXML> listOfSchedeNonValideSecondoFlusso, ArrayList<IdsSchedaXML> listOfSchedeValide) {
//		this.listOfSchedeNonValideSecondoFlusso = listOfSchedeNonValideSecondoFlusso;
//		this.listOfSchedeValide = listOfSchedeValide;
//		
//		if(listOfSchedeValide == null || listOfSchedeValide.size() == 0) super.setEsitoOperazione(false);
//		else super.setEsitoOperazione(true);
//		
//		if(listOfSchedeNonValideSecondoFlusso != null && listOfSchedeNonValideSecondoFlusso.size() > 0) someSchedeNonValide = true;
//		else someSchedeNonValide = false;
//	}

//	public ArrayList<IdsSchedaXML> getListOfSchedeNonValideSecondoFlusso() {
//		return listOfSchedeNonValideSecondoFlusso;
//	}
//
//	public void setListOfSchedeNonValideSecondoFlusso(ArrayList<IdsSchedaXML> listOfSchedeNonValideSecondoFlusso) {
//		this.listOfSchedeNonValideSecondoFlusso = listOfSchedeNonValideSecondoFlusso;
//	}

	public ArrayList<String> getListOfCigNonValidi() {
		return listOfCigNonValidi;
	}
	public void setListOfCigNonValidi(ArrayList<String> listOfCigNonValidi) {
		this.listOfCigNonValidi = listOfCigNonValidi;
	}
	public void addListOfCigNonValidi(String cigCorrente) {
		if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
		this.listOfCigNonValidi.add(cigCorrente);
	}
	public ArrayList<IdsSchedaXML> getListOfSchedeValide() {
		return listOfSchedeValide;
	}
	public ArrayList<DatiAggiudicazioneType> getListOfSchedeValideAsDatiAggiudicazioni() {
		ArrayList<DatiAggiudicazioneType> list = new ArrayList<DatiAggiudicazioneType>();
		for(IdsSchedaXML ids : this.listOfSchedeValide){
			list.add(ids.getScheda());
		}return list;
	}

	public void setListOfSchedeValide(ArrayList<IdsSchedaXML> listOfSchedeValide) {
		this.listOfSchedeValide = listOfSchedeValide;
	}
	public void addListOfSchedeValide(ArrayList<IdsSchedaXML> listOfSchedeValide) {
		if(this.listOfSchedeValide == null) this.listOfSchedeValide = new ArrayList<IdsSchedaXML>();
		this.listOfSchedeValide.addAll(listOfSchedeValide);
	}
	public void addSchedaValida(IdsSchedaXML schedaValida) {
		if(this.listOfSchedeValide == null) this.listOfSchedeValide = new ArrayList<IdsSchedaXML>();
		this.listOfSchedeValide.add(schedaValida);
	}
	

	public boolean isSomeSchedeNonValide() {
		return someSchedeNonValide;
	}

	public void setSomeSchedeNonValide(boolean someSchedeNonValide) {
		this.someSchedeNonValide = someSchedeNonValide;
	}

	public ArrayList<SchedaSpecificaValidationBean> getListOfValidationsBeans() {
		return listOfValidationsBeans;
	}

	public void setListOfValidationsBeans(
			ArrayList<SchedaSpecificaValidationBean> listOfValidationsBeans) {
		this.listOfValidationsBeans = listOfValidationsBeans;
	}
	public void addListOfValidationsBeans(List<SchedaSpecificaValidationBean> listOfValidationsBeans) {
		if(this.listOfValidationsBeans == null) this.listOfValidationsBeans = new ArrayList<SchedaSpecificaValidationBean>();
		this.listOfValidationsBeans.addAll(listOfValidationsBeans);
	}
	
	
	
	
}
