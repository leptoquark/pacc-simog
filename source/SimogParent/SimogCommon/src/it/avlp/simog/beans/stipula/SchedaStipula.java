package it.avlp.simog.beans.stipula;

import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.NavigationFlow;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.VO;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.inizio.PosizioneAggiudicatarioBean;

import java.util.List;

public class SchedaStipula extends NavigationFlow implements VO{
	private InfoComuniBean infoComuni;
	private StipulaBean stipula;
	//private PubblicazioneBean pubblicazione;
	private AggiudicazioneBean aggiudicazione;	
	private List<AggiudicatarioBean> aggiudicatari;
	private List<ResponsabileBean> responsabiliInizio;
	private List<PosizioneAggiudicatarioBean> posizioneAggiudicatari;
	
	public InfoComuniBean getInfoComuni() {
		return infoComuni;
	}
	public void setInfoComuni(InfoComuniBean infoComuni) {
		this.infoComuni = infoComuni;
	}
	public StipulaBean getStipula() {
		return stipula;
	}
	public void setStipula(StipulaBean stipula) {
		this.stipula = stipula;
	}
	/*
	public PubblicazioneBean getPubblicazione(){
		return pubblicazione;
	}
	public void setPubblicazione(PubblicazioneBean pubblicazione){
		this.pubblicazione = pubblicazione;
	}
	*/
	public List<ResponsabileBean> getResponsabiliInizio() {
		return responsabiliInizio;
	}
	public void setResponsabiliInizio(List<ResponsabileBean> responsabiliInizio) {
		this.responsabiliInizio = responsabiliInizio;
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
	public List<PosizioneAggiudicatarioBean> getPosizioneAggiudicatari() {
		return posizioneAggiudicatari;
	}
	public void setPosizioneAggiudicatari(
			List<PosizioneAggiudicatarioBean> posizioneAggiudicatari) {
		this.posizioneAggiudicatari = posizioneAggiudicatari;
	}
}
