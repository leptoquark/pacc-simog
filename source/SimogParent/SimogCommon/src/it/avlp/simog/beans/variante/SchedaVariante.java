package it.avlp.simog.beans.variante;

import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.NavigationFlow;
import it.avlp.simog.beans.VO;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;

import java.util.ArrayList;
import java.util.List;

public class SchedaVariante extends NavigationFlow implements VO{

	private List<VarianteBean> varianti;
	private AggiudicazioneBean aggiudicazione;
	private InfoComuniBean infoComuni;
	private InizioLavoriBean inizioLavori;
	
	public List<VarianteBean> getVarianti() {
		return varianti;
	}
	public void setVarianti(List<VarianteBean> varianti) {
		this.varianti = varianti;
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
	public VarianteBean getVarianteFE()throws Exception{
		if(varianti != null && !varianti.isEmpty() && varianti.size()==1){
			VarianteBean vb = null;
			for(VarianteBean v : varianti){
				vb = v;
			}return vb;
		}else {
			throw new Exception("La lista delle varianti e' vuota oppure contiene piu di un'elemento , il metodo serve unicamente per il frontend dove si suppone l'accordo sia unico");
		}
	}
	public void setVarianteFE(VarianteBean bean){
		if(bean != null){
			this.varianti = null;
			this.varianti = new ArrayList<VarianteBean>();
			this.varianti.add(bean);
		}
	}
}
