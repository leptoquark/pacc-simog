package it.avlp.simog.beans.avanzamento;

import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.beans.NavigationFlow;
import it.avlp.simog.beans.VO;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;

import java.util.ArrayList;
import java.util.List;

public class SchedaAvanzamento extends NavigationFlow implements VO{

	private InfoComuniBean infoComuni;
	private AggiudicazioneBean aggiudicazione;
	private List<AvanzamentoBean> avanzamenti;
	
	//2.10 aggiunto per i controlli 11.1.1.4,7,11
	private List <VarianteBean> varianti;
	
	//2.10 aggiunto per i controlli 11.1.1.4,7,11
	public List <VarianteBean> getVarianti() {
		return varianti;
	}
	//2.10 aggiunto per i controlli 11.1.1.4,7,11
	public void setVarianti(List <VarianteBean> varianti) {
		this.varianti = varianti;
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
	/********************************************************************************************
	 * Restituisce la lista degli AvanzamentoBean associata alla scheda
	 * 
	 * @return List&lt;AvanzamentoBean&gt;
	 */
	public List<AvanzamentoBean> getAvanzamenti() {
		return avanzamenti;
	}
	public void setAvanzamenti(List<AvanzamentoBean> avanzamenti) {
		this.avanzamenti = avanzamenti;
	}
	
	
	/********************************************************************************************
	 * Il metodo restituisce l'AvanzamentoBean contenuto nella Lista di AvanzamentoBean 
	 * chiamata "avanzamenti" all'intermno della classe, il metodo serve unicamente 
	 * per il frontend dove si suppone che l'accordo sia unico.
	 * 
	 * @return AvanzamentoBean
	 * @throws Exception
	 */
	public AvanzamentoBean getAvanzamentoFE()throws Exception{
		if(avanzamenti!=null && !avanzamenti.isEmpty() && avanzamenti.size()==1){
			AvanzamentoBean ab = null;
			for(AvanzamentoBean a : avanzamenti){
				ab = a;
			}return ab;
			
		}else{
			throw new Exception("La lista degli avanzamenti e' vuota oppure contiene piu di un'elemento, il metodo serve unicamente per il frontend dove si suppone l'accordo sia unico");
		}
	}

	public void setAvanzamentoFE(AvanzamentoBean a){
		if(a != null){
			avanzamenti = null;
			avanzamenti = new ArrayList<AvanzamentoBean>();
			avanzamenti.add(a);
		}
	}
	
}
