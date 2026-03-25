package it.anticorruzione.ted.beans;

import java.util.List;

import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.ws.massload.xmlbeans.DeltaLottoTED;

public class LottoTED {

	public Lotto lotto;
	public DeltaLottoTED deltaLottoTED;
	public AggiudicazioneBean aggiudicazione;
	public List<AggiudicatarioBean> aggiudicatari;
	
	
	public Lotto getLotto() {
		return lotto;
	}
	public void setLotto(Lotto lotto) {
		this.lotto = lotto;
	}
	public DeltaLottoTED getDeltaLottoTED() {
		return deltaLottoTED;
	}
	public void setDeltaLottoTED(DeltaLottoTED deltaLottoTED) {
		this.deltaLottoTED = deltaLottoTED;
	}
	public AggiudicazioneBean getAggiudicazione() {
		return aggiudicazione;
	}
	public void setAggiudicazione(AggiudicazioneBean aggiudicazione) {
		this.aggiudicazione = aggiudicazione;
	}
	public List<AggiudicatarioBean> getAggiudicatari() {
		return aggiudicatari;
	}
	public void setAggiudicatari(List<AggiudicatarioBean> aggiudicatari) {
		this.aggiudicatari = aggiudicatari;
	}
	
	
	
	
}
