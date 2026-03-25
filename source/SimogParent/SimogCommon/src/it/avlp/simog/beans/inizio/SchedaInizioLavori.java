package it.avlp.simog.beans.inizio;

import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.NavigationFlow;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.VO;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;

import java.util.List;

public class SchedaInizioLavori extends NavigationFlow implements VO{
	private InizioLavoriBean datiInizio;
	private InfoComuniBean infoComuni;
	private AggiudicazioneBean aggiudicazione;
	//need Aggiudicatari di aggiudicazione per fare la validazione da massloader
	private List<AggiudicatarioBean> aggiudicatari;
	//-- end --
	private List<ResponsabileBean> responsabiliInizio;
	private List<PosizioneAggiudicatarioBean> posizioneAggiudicatari;
	
	
	
	public InizioLavoriBean getDatiInizio() {
		return datiInizio;
	}
	public void setDatiInizio(InizioLavoriBean datiInizio) {
		this.datiInizio = datiInizio;
	}
	public List<ResponsabileBean> getResponsabiliInizio() {
		return responsabiliInizio;
	}
	public void setResponsabiliInizio(List<ResponsabileBean> responsabiliInizio) {
		this.responsabiliInizio = responsabiliInizio;
	}
	public List<PosizioneAggiudicatarioBean> getPosizioneAggiudicatari() {
		return posizioneAggiudicatari;
	}
	public void setPosizioneAggiudicatari(
			List<PosizioneAggiudicatarioBean> posizioneAggiudicatari) {
		this.posizioneAggiudicatari = posizioneAggiudicatari;
	}
	public InfoComuniBean getInfoComuni() {
		return infoComuni;
	}
	public void setInfoComuni(InfoComuniBean infoComuni) {
		this.infoComuni = infoComuni;
	}
	public AggiudicazioneBean getAggiudicazione() {
		return aggiudicazione;
	}
	public void setAggiudicazione(AggiudicazioneBean aggiudicazione) {
		this.aggiudicazione = aggiudicazione;
	}
	//vedi commento sul field
	
	public List<AggiudicatarioBean> getAggiudicatari() {
		return aggiudicatari;
	}
	public void setAggiudicatari(List<AggiudicatarioBean> aggiudicatari) {
		this.aggiudicatari = aggiudicatari;
	}
	
	
	

}
