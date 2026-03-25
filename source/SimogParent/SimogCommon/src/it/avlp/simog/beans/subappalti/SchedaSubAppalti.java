package it.avlp.simog.beans.subappalti;

import it.avlp.simog.beans.DatiEconomiciBean;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.NavigationFlow;
import it.avlp.simog.beans.VO;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SchedaSubAppalti extends NavigationFlow implements VO{

	private List<SubappaltiBean> subAppalti;
	private AggiudicazioneBean aggiudicazione;
	private InfoComuniBean infoComuni;
	private InizioLavoriBean inizioLavori;
	private boolean modificabile = false; 

	//gm nuovo codice 3.0
/**/private List <AggiudicatarioBean> aggiudicatari = null;

   // PP 3.02.3.3
   private DatiEconomiciBean datiEconomici;
   
   public DatiEconomiciBean getDatiEconomici() {
      return datiEconomici;
   }
   
   public void setDatiEconomici(DatiEconomiciBean datiEconomici) {
      this.datiEconomici = datiEconomici;
   }
    
   public boolean getHasDatiEconomici() {
      return this.datiEconomici != null;
   }

	public List<AggiudicatarioBean> getAggiudicatari() {
	   if(this.aggiudicatari == null)
	      return new ArrayList<AggiudicatarioBean>(); // per evitare nullpointerS
	   else
	      return aggiudicatari;
	}
	public void setAggiudicatari(List<AggiudicatarioBean> aggiudicatari) {
		this.aggiudicatari = aggiudicatari;
	}

	public Map<String, String> getAggiudicatariCombo(){
		HashMap<String,String> retVal = new HashMap<String,String>();
		
		if (aggiudicatari != null && aggiudicatari.size() > 0){
			
			for (Iterator iter = aggiudicatari.iterator(); iter.hasNext();) {
				AggiudicatarioBean element = (AggiudicatarioBean) iter.next();
				retVal.put(element.getSoggettoPartecipante().getCodiceFiscale(), 
							element.getSoggettoPartecipante().getCodiceFiscale() 
								+ " - " + element.getSoggettoPartecipante().getDenominazione());
			}			
		}
		return retVal;
	}
	//gm fine nuovo codice 3.0
	
	/*
	 * controlla se esiste un solo aggiudicatario
	 * se esistono due codici gruppo diversi non è unico
	 * se esistono CF diversi di tipo impresa singola non è unico
	 * 
	 */
	public boolean isUnicoAggiudicatario(){

		boolean ret = true;
		long  lastGruppo = 0;
		String lastCF = "";
		
		for (Iterator iter = aggiudicatari.iterator(); iter.hasNext();) {
			AggiudicatarioBean element = (AggiudicatarioBean) iter.next();
			
			if(lastGruppo == 0 && element.getIdGruppo() > 0)
				lastGruppo = element.getIdGruppo();
			// controllo sui gruppi degli RTI
			if(element.getIdGruppo() > 0 && element.getIdGruppo() != lastGruppo){
				ret = false;
				break;
			}
			// controllo sui CF delle imprese singole
			if(element.getIdGruppo() == 0){
				if(lastCF.equals("")) lastCF = element.getSoggettoPartecipante().getCodiceFiscale();
				
				if( !lastCF.equals("") && lastCF.compareTo(element.getSoggettoPartecipante().getCodiceFiscale()) != 0){
					ret = false;
					break;					
				}
			}
		}			

		return ret;
	}
	
	public List<SubappaltiBean> getSubAppalti() {
		return subAppalti;
	}
	public void setSubAppalti(List<SubappaltiBean> subAppalti) {
		this.subAppalti = subAppalti;
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
	public SubappaltiBean getSubAppaltiFE()throws Exception{
		if(subAppalti != null && !subAppalti.isEmpty() && subAppalti.size() == 1){
			SubappaltiBean ss = null;
			for(SubappaltiBean s : subAppalti){
				ss = s;
			}return ss;
		}else{
			throw new Exception("La lista dei subappalti e' vuota oppure contiene piu di un'elemento , il metodo serve unicamente per il frontend dove si suppone l'accordo sia unico");
		}		
	}
	public void setSubAppaltiFE(SubappaltiBean s){
		if(s != null){
			this.subAppalti = null;
			this.subAppalti = new ArrayList<SubappaltiBean>();
			this.subAppalti.add(s);
		}
	}

	public boolean isModificabile() {
		return modificabile;
	}

	public void setModificabile(boolean modificabile) {
		this.modificabile = modificabile;
	}
	
}
