package it.avlp.simog.beans.aggiudicazione;

import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.NavigationFlow;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.Rubrica;
import it.avlp.simog.beans.RubricaResponsabili;
import it.avlp.simog.beans.cup.CupLottoAggExt;

import java.util.ArrayList;
import java.util.List;



public class Scheda_A extends NavigationFlow {

	/*
	 * dati della scheda
	 */
	private InfoGaraBean infoGara;
	private InfoComuniBean infoComuni; 
	private AggiudicazioneBean aggiudicazione;
	private List<ResponsabileBean> responsabili;
	private List<ResponsabileBean> prestazioni;
	private List<AggiudicatarioBean> aggiudicatari;
	private List<RequisitiBean> requisiti;	
	private List<CondizioneAggBean> condizioni;
	private List<TipoAppaltoAggBean> tipoLavoro;
	private List<TipoAppaltoAggBean> tipoFS;
	private List<TipoFinanziamentoBean> finanziamenti;
	private List<CupLottoAggExt> elencoCup;
	private String flagCUP;
	
//	private String flagPnrrPnc;
//	private String flagPrevisioneQuota;
//	private double quotaGiovanile;
//	private double quotaFemminile;

//	private Object selMotivoDeroga;
//	
	// il seguente bean � valorizzato solo da caricamento batch
	private List<Rubrica> aggiudicatariXML;
	
	// il seguente bean � valorizzato solo da caricamento batch
	private List<RubricaResponsabili> responsabiliXML;

	
	public List<RubricaResponsabili> getResponsabiliXML() {
		return responsabiliXML;
	}
	public void setResponsabiliXML(List<RubricaResponsabili> responsabiliXML) {
		this.responsabiliXML = responsabiliXML;
	}
	public List<Rubrica> getAggiudicatariXML() {
		return aggiudicatariXML;
	}
	public void setAggiudicatariXML(List<Rubrica> aggiudicatariXML) {
		this.aggiudicatariXML = aggiudicatariXML;
	}
	public List<AggiudicatarioBean> getAggiudicatari() {
		return aggiudicatari;
	}
	public void setAggiudicatari(List<AggiudicatarioBean> aggiudicatari) {
		this.aggiudicatari = aggiudicatari;
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
	public List<ResponsabileBean> getResponsabili() {
		return responsabili;
	}
	public void setResponsabili(List<ResponsabileBean> responsabili) {
		this.responsabili = responsabili;
	}
	public InfoGaraBean getInfoGara() {
		return infoGara;
	}
	public void setInfoGara(InfoGaraBean infoGara) {
		this.infoGara = infoGara;
	}
	public List<RequisitiBean> getRequisiti() {
		return requisiti;
	}
	public void setRequisiti(List<RequisitiBean> requisiti) {
		this.requisiti = requisiti;
	}
	public List<CondizioneAggBean> getCondizioni() {
		return condizioni;
	}
	public void setCondizioni(List<CondizioneAggBean> condizioni) {
		this.condizioni = condizioni;
	}
	public List<TipoAppaltoAggBean> getTipoLavoro() {
		return tipoLavoro;
	}
	
	
	/****************************************************************************
	 * Il metodo si occupa di inserire in una lista di elementi di tipo TopoAppaltoAggBean
	 * tutti gli elementi contenuti nelle due Collection tipoLavoro e tipoFS. Collezioni relative 
	 * a Lavori, Forniture e Servizi (FS). 
	 * 
	 * NON usare per aggiornamenti del bean!!!
	 * @return List&lt;TipoAppaltoAggBean&gt; - lista ottenuta dalla concatenazione di tipoLavoro e tipoFS
	 */
	public List<TipoAppaltoAggBean> getTipoAppalto() {
		ArrayList<TipoAppaltoAggBean> arrN = new ArrayList<TipoAppaltoAggBean>();
		
		if(tipoLavoro!= null)
		   arrN.addAll(tipoLavoro);
		
		if(tipoFS != null)
		   arrN.addAll(tipoFS);
		
		arrN.trimToSize();
		
		return arrN;
	}
	
	/****************************************************************************
	 * Il metodo restituisce una lista ottenuta dalla concatenazione di <i>responsabili</i> con <i>prestazioni</i>. 
	 * @return List&lt;ResponsabileBean&gt;
	 */
	public List<ResponsabileBean> getAllResponsabili() {
		ArrayList<ResponsabileBean> arrN = new ArrayList<ResponsabileBean>();
		
		if(responsabili!= null)
		   arrN.addAll(responsabili);
		
	    if(prestazioni!= null)
	       arrN.addAll(prestazioni);

		arrN.trimToSize();
		
		return arrN;
	}
	public void setTipoLavoro(List<TipoAppaltoAggBean> tipoLavoro) {
		this.tipoLavoro = tipoLavoro;
	}
	public List<ResponsabileBean> getPrestazioni() {
		return prestazioni;
	}
	public void setPrestazioni(List<ResponsabileBean> prestazioni) {
		this.prestazioni = prestazioni;
	}
	public List<TipoFinanziamentoBean> getFinanziamenti() {
		return finanziamenti;
	}
	public void setFinanziamenti(List<TipoFinanziamentoBean> finanziamenti) {
		this.finanziamenti = finanziamenti;
	}
	public List<TipoAppaltoAggBean> getTipoFS() {
		return tipoFS;
	}
	public void setTipoFS(List<TipoAppaltoAggBean> tipoSF) {
		this.tipoFS = tipoSF;
	}
	public boolean isRiaggiudicazione() {
		return aggiudicazione != null && aggiudicazione.getProgCuiRiaggiudicato() > 0;
	}
   public List<CupLottoAggExt> getElencoCup() {
      return elencoCup;
   }
   public void setElencoCup(List<CupLottoAggExt> elencoCup) {
      this.elencoCup = elencoCup;
   }
   public String getFlagCUP() {
      return flagCUP;
   }
   public void setFlagCUP(String flagCUP) {
      this.flagCUP = flagCUP;
   }
   
//   public String getFlagParGenMod1() {
//	      return flagParGenMod1;
//	}
//   public void setFlagParGenMod1(String flagParGenMod1) {
//	   this.flagParGenMod1 = flagParGenMod1;
//   }
//   
//   public String getFlagParGenMod2() {
//	      return flagParGenMod2;
//	}
//   public void setFlagParGenMod2(String flagParGenMod2) {
//	   this.flagParGenMod2 = flagParGenMod2;
//   }
   
   
   
//   public void setSelMotiviDeroga(Object selMotivoDeroga) {
//	   this.selMotivoDeroga = selMotivoDeroga;
//   }
//   
//   public String getFlagPnrrPnc() {
//	return flagPnrrPnc;
//}
//public void setFlagPnrrPnc(String flagPnrrPnc) {
//	this.flagPnrrPnc = flagPnrrPnc;
//}
//public String getFlagPrevisioneQuota() {
//	return flagPrevisioneQuota;
//}
//public void setFlagPrevisioneQuota(String flagPrevisioneQuota) {
//	this.flagPrevisioneQuota = flagPrevisioneQuota;
//}
//public double getQuotaFemminile() {
//	return quotaFemminile;
//}
//public Object getSelMotiviDeroga() {
//	   return this.selMotivoDeroga;
//   }
//   
//   
//   public double getQuotaGiovanile() {
//	      return quotaGiovanile;
//	}
//   public void setQuotaGiovanile(double quotaGiovanile) {
//	   this.quotaGiovanile = quotaGiovanile;
//   }
//
//   public void setQuotaFemminile(double quotaFemminile) {
//	   this.quotaFemminile = quotaFemminile;
//   }
   
   
	
}
