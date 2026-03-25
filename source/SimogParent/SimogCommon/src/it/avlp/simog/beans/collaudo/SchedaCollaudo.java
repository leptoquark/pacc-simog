package it.avlp.simog.beans.collaudo;

import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.NavigationFlow;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.VO;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
//2.10 aggiunto per la modifica del 13.1.1.2
import it.avlp.simog.beans.inizio.InizioLavoriBean;

import java.util.List;

public class SchedaCollaudo extends NavigationFlow implements VO {
	
	private ConclusioneBean conclusione;
	private List<AccordoBean> accordiBonario;
	private CollaudoBean collaudo;
	private List<ResponsabileBean> incaricati;
	private InfoComuniBean infoComuni;
	private AggiudicazioneBean aggiudicazione;
	//2.10 aggiunto per la modifica del 13.1.1.2
	private InizioLavoriBean inizioLavori;
	
	//2.10 aggiunto per la modifica del 13.1.1.2
	public InizioLavoriBean getInizioLavori() {
		return inizioLavori;
	}
	//2.10 aggiunto per la modifica del 13.1.1.2
	public void setInizioLavori(InizioLavoriBean inizioLavori) {
		this.inizioLavori = inizioLavori;	
	}
	public CollaudoBean getCollaudo() {
		return collaudo;
	}
	public void setCollaudo(CollaudoBean collaudo) {
		this.collaudo = collaudo;
	}
	public List<ResponsabileBean> getIncaricati() {
		return incaricati;
	}
	public void setIncaricati(List<ResponsabileBean> incaricati) {
		this.incaricati = incaricati;
		this.collaudo.setRespBean(incaricati);
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
	public ConclusioneBean getConclusione() {
		return conclusione;
	}
	public void setConclusione(ConclusioneBean conclusione) {
		this.conclusione = conclusione;
	}
	public List<AccordoBean> getAccordiBonario() {
		return accordiBonario;
	}
	public void setAccordiBonario(List<AccordoBean> accordiBonario) {
		this.accordiBonario = accordiBonario;
	}

}
