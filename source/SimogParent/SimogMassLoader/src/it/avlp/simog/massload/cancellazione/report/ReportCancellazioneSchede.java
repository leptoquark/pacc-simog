package it.avlp.simog.massload.cancellazione.report;

import it.avlp.simog.beans.SchedaSpecificaValidationBean;

import java.util.ArrayList;


/**
 * Contiene tutte le operazioni effettuate durante una singola
 * cancellazione, utile in particolare per la cancellazione totale
 * 
 * @author vletizia
 *
 */
public class ReportCancellazioneSchede {

	private ArrayList<ReportCancellazioneScheda> reports;

	public ArrayList<ReportCancellazioneScheda> getReports() {
		return reports;
	}

	public void setReports(ArrayList<ReportCancellazioneScheda> reports) {
		this.reports = reports;
	}
	public void addAllReports(ArrayList<ReportCancellazioneScheda> reports) {
		if(this.reports == null){
			
			this.reports = new ArrayList<ReportCancellazioneScheda>();
		}
		
		this.reports.addAll(reports);
	}	
	public void addReport(ReportCancellazioneScheda report){
		if(this.reports == null){
			
			this.reports = new ArrayList<ReportCancellazioneScheda>();
		}
		
		this.reports.add(report);
	}
	
	public String toString(){
		String riepilogo = "Riepilogo collezione di report Cancellazione";
		for(ReportCancellazioneScheda report: reports){
			riepilogo += report.toString();
		}return riepilogo;
	}
	
	/**
	 * Recupera tutta la lista delle validazione dall'interno della lista dei report a livello "scheda"
	 * @return
	 */
	public ArrayList<SchedaSpecificaValidationBean> getAll(){
		ArrayList<SchedaSpecificaValidationBean> all = new ArrayList<SchedaSpecificaValidationBean>();
		for(ReportCancellazioneScheda reportCancellazione : this.reports){
			if(reportCancellazione.getListOfValidationsBeans() != null)
				all.addAll(reportCancellazione.getListOfValidationsBeans());
		}
		return all;
	}
	
	/**
	 * Crea dei bean di validazione degli oggetti idScheda che contengono gli estremi delle schede cancellate con succcesso.
	 * @return
	 */
	public ArrayList<SchedaSpecificaValidationBean> getAllSuccess(){
		ArrayList<SchedaSpecificaValidationBean> all = new ArrayList<SchedaSpecificaValidationBean>();
		for(ReportCancellazioneScheda reportCancellazione : this.reports){
			all.add(reportCancellazione.getSuccess());
		}
		return all;		
	}
	
	
	
	
}
