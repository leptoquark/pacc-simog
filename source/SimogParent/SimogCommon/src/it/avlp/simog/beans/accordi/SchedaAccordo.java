package it.avlp.simog.beans.accordi;

import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.NavigationFlow;
import it.avlp.simog.beans.VO;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;

import java.util.ArrayList;
import java.util.List;
public class SchedaAccordo extends NavigationFlow implements VO {

	private List<AccordoBean> accordi;
	private AggiudicazioneBean aggiudicazione;
	private InfoComuniBean infoComuni;
	private InizioLavoriBean inizioLavori;
	private boolean aggiungibile = true;
	
	public List<AccordoBean> getAccordi() {
		return accordi;
	}
	public void setAccordi(List<AccordoBean> accordi) {
		this.accordi = accordi;
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
	/* metodi da usare solo nel frontend che suppone che la lista accori contenga un solo elemento !*/
	/* altrimenti lancia un'eccezione */
	
	
	/*************************************************************************************************
	 * Il metodo Restituisce l'accordo<br><br>
	 * <b>ATTENZIONE</b> 
	 * <i>il metodo � utilizzabile esclusivamente nel FrontEnd dove l'accordo risulta 
	 * unico</i><br><br> 
	 * 
	 * @return AccordoBean
	 * @throws Exception
	 */
	public AccordoBean getAccordoFE()throws Exception{
		if(accordi != null && !accordi.isEmpty() && accordi.size() == 1){
			AccordoBean accordo = null;
			for(AccordoBean ab : accordi){
				accordo = ab;
			}return  accordo;
		}else{
			throw new Exception("La lista degli accordi e' vuota oppure contiene piu di un'elemento , il metodo serve unicamente per il frontend dove si suppone l'accordo sia unico");
		}
	}
	
	
	/*****************************************************************************************************
	 * Aggiunge un accordo alla lista di accordi 
	 * @param accordo AccordoBean che contiene le informazioni di accordo
	 */
	public void setAccordoFE(AccordoBean accordo){
		if(accordo != null){
			this.accordi = null;
			this.accordi = new ArrayList<AccordoBean>();
			this.accordi.add(accordo);
		}
	}
	
}
