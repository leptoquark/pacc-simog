package it.avlp.simog.beans.r129;

import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.NavigationFlow;
import it.avlp.simog.beans.VO;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;

import java.util.ArrayList;
import java.util.List;

public class SchedaR129 extends NavigationFlow implements VO{
	
	private List<R129Bean> r129s;
	private AggiudicazioneBean aggiudicazione;
	private InfoComuniBean infoComuni;
	private InizioLavoriBean inizioLavori;
	
	public List<R129Bean> getR129s() {
		return r129s;
	}
	public void setR129s(List<R129Bean> r129s) {
		this.r129s = r129s;
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
	public InizioLavoriBean getInizioLavori() {
		return inizioLavori;
	}
	public void setInizioLavori(InizioLavoriBean inizioLavori) {
		this.inizioLavori = inizioLavori;
	}
	public R129Bean getRitardoFE()throws Exception{
		if(r129s != null && !r129s.isEmpty() && r129s.size() == 1){
			R129Bean ritardo = null;
			for(R129Bean r1 : r129s){
				ritardo = r1;
			}return  ritardo;
		}else{
			throw new Exception("La lista dei ritardi � vuota oppure contiene piu di un'elemento , il metodo serve unicamente per il frontend dove si suppone l'accordo sia unico");
		}
	}
	public void setRitardoFE(R129Bean ritardo){
		if(ritardo != null){
			this.r129s = null;
			this.r129s = new ArrayList<R129Bean>();
			this.r129s.add(ritardo);
		}
	}
}
