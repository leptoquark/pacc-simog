package it.avlp.simog.massload.esito;

import it.avlp.simog.beans.EsitoOperazioneBean;
import it.avlp.simog.massload.cancellazione.report.ReportCancellazioneScheda;
import it.avlp.simog.massload.cancellazione.report.ReportCancellazioneSchede;

import java.util.ArrayList;

public class EsitoOperazioneCancellazioneBean extends EsitoOperazioneBean{
	
	private ReportCancellazioneScheda reportSingolaScheda;
	private boolean isSingola;
	private ReportCancellazioneSchede reportPiuSchede;
	
	public String toString(){
		String toString = "Riepilogo Esito Operazione: \r\n";
		toString += "\t esitoOperazione: "+esitoOperazione+"\r\n";
		toString += "\t messaggioErrore: "+(messaggioErrore != null ? messaggioErrore : "Messaggio non Presente")+"\r\n";
		toString += "\t messaggioAvviso: "+(messaggioAvviso != null ? messaggioAvviso : "Messaggio non Presente")+"\r\n";
		toString += "\t messaggioInfo: \r\n\r\n\t"+ (messaggioInfo != null ? messaggioInfo : "Messaggio non Presente" )+"\r\n";
		String eccezione = eccezioneLocale != null ? "\t" + eccezioneLocale.getMessage(): "Eccezione non Presente";
		toString += "\t eccezioneLocale: "+eccezione+"\r\n";
		toString += "\t isSingola: "+isSingola+"\r\n";
		toString += "\r\n";
		if(reportSingolaScheda != null){
			toString += "\t\t"+reportSingolaScheda.toString();
		}else if(reportPiuSchede != null){
			ArrayList<ReportCancellazioneScheda> reports = reportPiuSchede.getReports();
			reports.trimToSize();
			for(ReportCancellazioneScheda reportCorrente : reports){
				toString += "\t\t"+reportCorrente.toString();
			}
		}
		return toString;		
	}
	

	public ReportCancellazioneScheda getReportSingolaScheda() {
		return reportSingolaScheda;
	}
	public void setReportSingolaScheda(ReportCancellazioneScheda reportSingolaScheda) {
		this.reportSingolaScheda = reportSingolaScheda;
	}
	public boolean isSingola() {
		return isSingola;
	}
	public void setSingola(boolean isSingola) {
		this.isSingola = isSingola;
	}
	public ReportCancellazioneSchede getReportPiuSchede() {
		return reportPiuSchede;
	}
	public void setReportPiuSchede(ReportCancellazioneSchede reportPiuSchede) {
		this.reportPiuSchede = reportPiuSchede;
	}
	

	
	
}
