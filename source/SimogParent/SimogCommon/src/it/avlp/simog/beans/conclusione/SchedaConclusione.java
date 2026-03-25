package it.avlp.simog.beans.conclusione;

import java.util.List;

import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.NavigationFlow;
import it.avlp.simog.beans.VO;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;

public class SchedaConclusione extends NavigationFlow implements VO{

	private ConclusioneBean conclusione;
	private AggiudicazioneBean aggiudicazione;
	private InfoComuniBean infoComuni;
	private InizioLavoriBean inizioLavori;
	private List<AvanzamentoBean> avanzamenti;
	
	public List<AvanzamentoBean> getAvanzamenti() {
		return avanzamenti;
	}
	public void setAvanzamenti(List<AvanzamentoBean> avanzamenti) {
		this.avanzamenti = avanzamenti;
	}
	public InizioLavoriBean getInizioLavori() {
		return inizioLavori;
	}
	public void setInizioLavori(InizioLavoriBean inizioLavori) {
		this.inizioLavori = inizioLavori;
	}
	public ConclusioneBean getConclusione() {
		return this.conclusione;
	}
	public void setConclusione(ConclusioneBean conclusione) {
		this.conclusione = conclusione;
	}
	public AggiudicazioneBean getAggiudicazione() {
		return aggiudicazione;
	}
	public void setAggiudicazione(AggiudicazioneBean aggiudicazione) {
		this.aggiudicazione = aggiudicazione;
	}
	public InfoComuniBean getInfoComuni() {
		return infoComuni;
	}
	public void setInfoComuni(InfoComuniBean infoComuni) {
		this.infoComuni = infoComuni;
	}
}
