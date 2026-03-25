package it.avlp.simog.beans;


import java.util.ArrayList;
import java.util.List;

public class EsitoOperazioneBean {

	protected boolean esitoOperazione = true;
	
	protected String messaggioErrore;
	protected String messaggioAvviso;
	protected String messaggioInfo;
	
	protected Exception eccezioneLocale;
	
	public boolean isEsitoOperazione() {
		return esitoOperazione;
	}
	/**
	 * Deve essere settato in modo esplicito solo a false oppure nella fase del controllo del flusso.
	 * Nel caso invece in cui si setti un report oppure una lista di report deve essere settato
	 * tramite un controllo del valore contenuto nel report e che riporta il boolean dell'esito della
	 * operazione
	 * 
	 * @param esitoOperazione
	 */
	public void setEsitoOperazione(boolean esitoOperazione) {
		this.esitoOperazione = esitoOperazione;
	}
	public String getMessaggioErrore() {
		return messaggioErrore;
	}
	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}
	public String getMessaggioAvviso() {
		return messaggioAvviso;
	}
	public void setMessaggioAvviso(String messaggioAvviso) {
		this.messaggioAvviso = messaggioAvviso;
	}
	public Exception getEccezioneLocale() {
		return eccezioneLocale;
	}
	public void setEccezioneLocale(Exception eccezioneLocale) {
		this.eccezioneLocale = eccezioneLocale;
	}
	public String getMessaggioInfo() {
		return messaggioInfo;
	}
	public void setMessaggioInfo(String messaggioInfo) {
		this.messaggioInfo = messaggioInfo;
	}
//	/**
//	 * Costruisce in base all'array in ingresso e al nome della scheda una lista di validation bean uno per ogni valore false nell'array
//	 * 
//	 * @param array
//	 * @param nomeScheda
//	 * @return
//	 * @throws Exception
//	 */
//	protected List<SchedaSpecificaValidationBean> parseArray(boolean[] array, String nomeScheda, String cig, String cui, int progressivoSchedaCompleta ) throws Exception{ 
//		List<SchedaSpecificaValidationBean> listaErrori = new ArrayList<SchedaSpecificaValidationBean>();
//		for(int i = 0; i < array.length; i++){
//			if(!array[i]){
//				listaErrori.add(new SchedaSpecificaValidationBean("Gli Id non risultano validi ", ValidationBean.VALBEAN_SEV_ERR, i,progressivoSchedaCompleta, nomeScheda, cig, cui));
//			}
//		}
//		return listaErrori; 
//	}
	
	/**
	 * Metodo centralizzato per l'aggiunta di un elemento alla lista passata.
	 * Nota: se la lista passata e' nulla viene istanziata.
	 * 
	 * @param validations
	 * @param validation
	 * @return
	 */
	protected List<SchedaSpecificaValidationBean> addElements(List<SchedaSpecificaValidationBean> validations, SchedaSpecificaValidationBean validation){
		if(validations == null) validations = new ArrayList<SchedaSpecificaValidationBean>();
		validations.add(validation);
		return validations;
	}
}
