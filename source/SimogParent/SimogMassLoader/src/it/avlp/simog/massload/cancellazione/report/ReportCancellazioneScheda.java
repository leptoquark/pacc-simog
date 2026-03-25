package it.avlp.simog.massload.cancellazione.report;

import it.avlp.simog.beans.IdsScheda;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.ValidationBean;

import java.util.ArrayList;
import java.util.List;

public class ReportCancellazioneScheda {

	private boolean esitoOperazione;
	private IdsScheda scheda;
	private List<SchedaSpecificaValidationBean> listOfValidationsBeans;
	
	public boolean getEsitoOperazione() {
		return esitoOperazione;
	}
	public void setEsitoOperazione(boolean esitoOperazione) {
		this.esitoOperazione = esitoOperazione;
	}
	public IdsScheda getSchede() {
		return scheda;
	}
	public void setSchede(IdsScheda scheda) {
		this.scheda = scheda;
	}
	public String toString(){
		String toString = "Riepilogo Report Cancellazione: \r\n";
		toString += "\t esitoOperazione: "+esitoOperazione+"\r\n";
		String schedaAsString = this.scheda != null ? this.scheda.toString(): "Identificativo scheda nullo";
		toString += "\t scheda: "+schedaAsString+"\r\n";
		return toString;
	}
	public List<SchedaSpecificaValidationBean> getListOfValidationsBeans() {
		return listOfValidationsBeans;
	}
	public void setListOfValidationsBeans(
			List<SchedaSpecificaValidationBean> listOfValidationsBeans) {
		this.listOfValidationsBeans = listOfValidationsBeans;
	}
	public void addListOfValidationsBeans(SchedaSpecificaValidationBean validation) {
		if(this.listOfValidationsBeans == null) this.listOfValidationsBeans = new ArrayList<SchedaSpecificaValidationBean>();
		this.listOfValidationsBeans.add(validation);
	}
	
	public SchedaSpecificaValidationBean getSuccess(){
		return new SchedaSpecificaValidationBean("",ValidationBean.VALBEAN_SEV_INFO,0,0,0,
				scheda.getIdentificativo().getNomeScheda(),scheda.getCig(),scheda.getCui(),scheda.getIdScheda(),scheda.getIdLocale());
	}
}
