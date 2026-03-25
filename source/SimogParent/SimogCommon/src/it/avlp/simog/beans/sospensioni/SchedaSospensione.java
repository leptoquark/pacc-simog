package it.avlp.simog.beans.sospensioni;

import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.NavigationFlow;
import it.avlp.simog.beans.VO;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;

import java.util.ArrayList;
import java.util.List;

public class SchedaSospensione extends NavigationFlow implements VO{

	private List<SospensioniBean> sospensioni;
	private AggiudicazioneBean aggiudicazione;
	private InfoComuniBean infoComuni;
	private InizioLavoriBean inizioLavori;
	private boolean modificabile = false; 

	public InizioLavoriBean getInizioLavori() {
		return inizioLavori;
	}

	public void setInizioLavori(InizioLavoriBean inizioLavori) {
		this.inizioLavori = inizioLavori;
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

	public List<SospensioniBean> getSospensioni() {
		return sospensioni;
	}

	public void setSospensioni(List<SospensioniBean> sospensioni){
		this.sospensioni = sospensioni;
	}
	public SospensioniBean getSospensioneFE()throws Exception{
		if(sospensioni != null && !sospensioni.isEmpty() && sospensioni.size() == 1){
			SospensioniBean sosp = null;
			for(SospensioniBean s : sospensioni){
				sosp = s;
			}return sosp;
		}else{
			throw new Exception("La lista delle sospensioni e' vuota oppure contiene piu di un'elemento , il metodo serve unicamente per il frontend dove si suppone l'accordo sia unico");
		}
	}
	public void setSospensioneFE(SospensioniBean sospensione){
		if(sospensione != null){
			this.sospensioni = null;
			this.sospensioni = new ArrayList<SospensioniBean>();
			this.sospensioni.add(sospensione);
		}
	}

	public boolean isModificabile() {
		return modificabile;
	}

	public void setModificabile(boolean modificabile) {
		this.modificabile = modificabile;
	}

}
