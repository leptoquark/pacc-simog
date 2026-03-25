package it.avlp.simog.massload.util.conversion;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.massload.bean.IdsSchedaXML;
import it.avlp.simog.massload.esito.EsitoControlloFormaleIds;
import it.avlp.simog.massload.xmlbeans.AccordoBonarioType;
import it.avlp.simog.massload.xmlbeans.AvanzamentoType;
import it.avlp.simog.massload.xmlbeans.RitardoType;
import it.avlp.simog.massload.xmlbeans.SospensioneType;
import it.avlp.simog.massload.xmlbeans.SubappaltoType;
import it.avlp.simog.massload.xmlbeans.VarianteType;

import java.util.ArrayList;


/**
 * Bean che contiene lo stato della scheda DatiAggiudicazioneType
 * 
 * TODO: potrei approfondire lo stato, andando nello specifico tipo boolean isPresentRespAggiudicazione 
 * 
 * @author vletizia
 *
 */
public class SituazioneAttualeSchedeXml {

	private boolean isPresentCUI = false;
	
	private boolean multilotto;
	private boolean principale;
	
	private boolean isPresentDatiComuni = false;
	private boolean isPresentDatiComuniIdSimog = false;
	private boolean isPresentDatiComuniIdLocale = false;
	private String datiComuniIdSimog;
	private String datiComuniIdLocale;
	
	public void setStatoXmlDatiComuni(SituazioneAttualeSchedeXml situazioneAttualeXml){
		this.isPresentDatiComuni = situazioneAttualeXml.isPresentDatiComuni();
		this.isPresentDatiComuniIdSimog = situazioneAttualeXml.isPresentDatiComuniIdSimog();
		this.isPresentDatiComuniIdLocale =  situazioneAttualeXml.isPresentDatiComuniIdLocale();
		this.datiComuniIdSimog = situazioneAttualeXml.getDatiComuniIdSimog();
		this.datiComuniIdLocale = situazioneAttualeXml.getDatiComuniIdLocale();
	}
	
	private boolean isPresentSchedaCompleta = false;
	
	private boolean isPresentAggiudicazione = false;
	private boolean isPresentAggiudicazioneIdSimog = false;
	private boolean isPresentAggiudicazioneIdLocale = false;
	private String aggiudicazioneIdSimog;
	private String aggiudicazioneIdLocale;
	
	public void setStatoXmlAggiudicazione(SituazioneAttualeSchedeXml situazioneAttualeXml){
		this.isPresentAggiudicazione = situazioneAttualeXml.isPresentAggiudicazione();
		this.isPresentAggiudicazioneIdSimog = situazioneAttualeXml.isPresentAggiudicazioneIdSimog();
		this.isPresentAggiudicazioneIdLocale =  situazioneAttualeXml.isPresentAggiudicazioneIdLocale();
		this.aggiudicazioneIdSimog = situazioneAttualeXml.getAggiudicazioneIdSimog();
		this.aggiudicazioneIdLocale = situazioneAttualeXml.getAggiudicazioneIdLocale();
		this.isPresentSchedaCompleta = true;
		this.multilotto = situazioneAttualeXml.multilotto;
		this.principale = situazioneAttualeXml.principale;
	}
	
	private boolean isPresentAdesione = false;
	private boolean isPresentAdesioneIdSimog = false;
	private boolean isPresentAdesioneIdLocale = false;
	private String adesioneIdSimog;
	private String adesioneIdLocale;
	
	public void setStatoXmlAdesione(SituazioneAttualeSchedeXml situazioneAttualeXml){
		this.isPresentAdesione = situazioneAttualeXml.isPresentAdesione();
		this.isPresentAdesioneIdSimog = situazioneAttualeXml.isPresentAdesioneIdSimog();
		this.isPresentAdesioneIdLocale =  situazioneAttualeXml.isPresentAdesioneIdLocale();
		this.adesioneIdSimog = situazioneAttualeXml.getAdesioneIdSimog();
		this.adesioneIdLocale = situazioneAttualeXml.getAdesioneIdLocale();
		this.isPresentSchedaCompleta = true;
	}
	
	private boolean isPresentSottosoglia = false;
	private boolean isPresentSottosogliaIdSimog = false;
	private boolean isPresentSottosogliaIdLocale = false;
	private String sottosogliaIdSimog;
	private String sottosogliaIdLocale;
	
	public void setStatoXmlSottosoglia(SituazioneAttualeSchedeXml situazioneAttualeXml){
		this.isPresentSottosoglia = situazioneAttualeXml.isPresentSottosoglia;
		this.isPresentSottosogliaIdSimog = situazioneAttualeXml.isPresentSottosogliaIdSimog();
		this.isPresentSottosogliaIdLocale =  situazioneAttualeXml.isPresentSottosogliaIdLocale();
		this.sottosogliaIdSimog = situazioneAttualeXml.getSottosogliaIdSimog();
		this.sottosogliaIdLocale = situazioneAttualeXml.getSottosogliaIdLocale();
		this.isPresentSchedaCompleta = true;
	}
	
	private boolean isPresentEscluso = false;
	private boolean isPresentEsclusoIdSimog = false;
	private boolean isPresentEsclusoIdLocale = false;
	private String esclusoIdSimog;
	private String esclusoIdLocale;
	
	public void setStatoXmlEscluso(SituazioneAttualeSchedeXml situazioneAttualeXml){
		this.isPresentEscluso = situazioneAttualeXml.isPresentEscluso;
		this.isPresentEsclusoIdSimog = situazioneAttualeXml.isPresentEsclusoIdSimog();
		this.isPresentEsclusoIdLocale =  situazioneAttualeXml.isPresentEsclusoIdLocale();
		this.esclusoIdSimog = situazioneAttualeXml.getEsclusoIdSimog();
		this.esclusoIdLocale = situazioneAttualeXml.getEsclusoIdLocale();
		this.isPresentSchedaCompleta = true;
	}
	
	private boolean isPresentInizioLavori = false;
	private boolean isPresentInizioLavoriIdSimog = false;
	private boolean isPresentInizioLavoriIdLocale = false;
	private String inizioLavoriIdSimog;
	private String inizioLavoriIdLocale;
	
	public void setStatoXmlInizio(SituazioneAttualeSchedeXml situazioneAttualeXml){
		this.isPresentInizioLavori = situazioneAttualeXml.isPresentInizioLavori();
		this.isPresentInizioLavoriIdSimog = situazioneAttualeXml.isPresentInizioLavoriIdSimog();
		this.isPresentInizioLavoriIdLocale =  situazioneAttualeXml.isPresentInizioLavoriIdLocale();
		this.inizioLavoriIdSimog = situazioneAttualeXml.getInizioLavoriIdSimog();
		this.inizioLavoriIdLocale = situazioneAttualeXml.getInizioLavoriIdLocale();
		this.isPresentSchedaCompleta = true;
	}
	
	private boolean isPresentStipula = false;
	private boolean isPresentStipulaIdSimog = false;
	private boolean isPresentStipulaIdLocale = false;
	private String stipulaIdSimog;
	private String stipulaIdLocale;
	
	public void setStatoXmlStipula(SituazioneAttualeSchedeXml situazioneAttualeXml){
		this.isPresentStipula = situazioneAttualeXml.isPresentStipula();
		this.isPresentStipulaIdSimog = situazioneAttualeXml.isPresentStipulaIdSimog();
		this.isPresentStipulaIdLocale =  situazioneAttualeXml.isPresentStipulaIdLocale();
		this.stipulaIdSimog = situazioneAttualeXml.getStipulaIdSimog();
		this.stipulaIdLocale = situazioneAttualeXml.getStipulaIdLocale();
		this.isPresentSchedaCompleta = true;
	}
	
	
	private boolean isPresentAvanzamenti = false;
	private boolean[] isPresentAvanzamentiIdSimog;
	private boolean[] isPresentAvanzamentiIdLocale;
	private String[] avanzamentiIdSimog;
	private String[] avanzamentiIdLocale;
	
	public void setStatoXmlAvanzamenti(SituazioneAttualeSchedeXml situazioneAttualeSchedeXml,IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica, boolean isInserimento  ){
		AvanzamentoType[] avanzamenti = null;
		
		if(isInserimento){
			if(newInstanceInserimento.getScheda().getSchedaCompletaArray() != null &&
					newInstanceInserimento.getScheda().getSchedaCompletaArray(0).getDatiAvanzamenti() != null	)
				avanzamenti = newInstanceInserimento.getScheda().getSchedaCompletaArray(0).getDatiAvanzamenti().getAvanzamentoArray();
		}else{
			if(newInstanceModifica.getScheda().getSchedaCompletaArray() != null &&
					newInstanceModifica.getScheda().getSchedaCompletaArray(0).getDatiAvanzamenti() != null	)
				avanzamenti = newInstanceModifica.getScheda().getSchedaCompletaArray(0).getDatiAvanzamenti().getAvanzamentoArray();
		}	
		
		if(avanzamenti != null && avanzamenti.length > 0){
			
			isPresentSchedaCompleta = true;
			isPresentAvanzamenti = true;
			isPresentAvanzamentiIdSimog = new boolean[avanzamenti.length];
			isPresentAvanzamentiIdLocale = new boolean[avanzamenti.length];
			avanzamentiIdSimog = new String[avanzamenti.length];
			avanzamentiIdLocale = new String[avanzamenti.length];
			
			for(int i = 0; i < avanzamenti.length; i++){
				AvanzamentoType avanzamentoCorrente = avanzamenti[i];
				
				isPresentAvanzamentiIdSimog[i] = avanzamentoCorrente.getIDSCHEDASIMOG() != null;
				if(isPresentAvanzamentiIdSimog[i]) avanzamentiIdSimog[i] = avanzamentoCorrente.getIDSCHEDASIMOG();
				
				isPresentAvanzamentiIdLocale[i] = avanzamentoCorrente.getIDSCHEDALOCALE() != null;
				if(isPresentAvanzamentiIdLocale[i]) avanzamentiIdLocale[i] = avanzamentoCorrente.getIDSCHEDALOCALE();
				
			}
		}
	}
	
	private boolean isPresentConclusione = false;
	private boolean isPresentConclusioneIdSimog = false;
	private boolean isPresentConclusioneIdLocale = false;
	private String conclusioneIdSimog;
	private String conclusioneIdLocale;
	
	public void setStatoXmlConclusione(SituazioneAttualeSchedeXml situazioneAttualeXml){
		this.isPresentConclusione = situazioneAttualeXml.isPresentConclusione();
		this.isPresentConclusioneIdSimog = situazioneAttualeXml.isPresentConclusioneIdSimog();
		this.isPresentConclusioneIdLocale =  situazioneAttualeXml.isPresentConclusioneIdLocale();
		this.conclusioneIdSimog = situazioneAttualeXml.getConclusioneIdSimog();
		this.conclusioneIdLocale = situazioneAttualeXml.getConclusioneIdLocale();
		this.isPresentSchedaCompleta = true;
	}
	
	private boolean isPresentCollaudo = false;
	private boolean isPresentCollaudoIdSimog = false;
	private boolean isPresentCollaudoIdLocale = false;
	private String collaudoIdSimog;
	private String collaudoIdLocale;
	
	public void setStatoXmlCollaudo(SituazioneAttualeSchedeXml situazioneAttualeXml){
		this.isPresentCollaudo = situazioneAttualeXml.isPresentCollaudo();
		this.isPresentCollaudoIdSimog = situazioneAttualeXml.isPresentCollaudoIdSimog();
		this.isPresentCollaudoIdLocale =  situazioneAttualeXml.isPresentCollaudoIdLocale();
		this.collaudoIdSimog = situazioneAttualeXml.getCollaudoIdSimog();
		this.collaudoIdLocale = situazioneAttualeXml.getCollaudoIdLocale();
		this.isPresentSchedaCompleta = true;
	}
	
	private boolean isPresentRitardo = false;
	private boolean[] isPresentRitardoIdSimog;
	private boolean[] isPresentRitardoIdLocale;
	private String[] ritardoIdSimog;
	private String[] ritardoIdLocale;
	
	public void setStatoXmlRitardi(SituazioneAttualeSchedeXml situazioneAttualeSchedeXml,IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica, boolean isInserimento ){
		RitardoType[] ritadi = null;
		
		if(isInserimento){
			if(newInstanceInserimento.getScheda().getSchedaCompletaArray() != null &&
					newInstanceInserimento.getScheda().getSchedaCompletaArray(0).getDatiRitardi() != null)
				ritadi = newInstanceInserimento.getScheda().getSchedaCompletaArray(0).getDatiRitardi().getRitardoArray();
		}else{
			if(newInstanceModifica.getScheda().getSchedaCompletaArray() != null &&
					newInstanceModifica.getScheda().getSchedaCompletaArray(0).getDatiRitardi() != null)
				ritadi = newInstanceModifica.getScheda().getSchedaCompletaArray(0).getDatiRitardi().getRitardoArray();
		}	
		
		if(ritadi != null && ritadi.length > 0){
			
			isPresentSchedaCompleta = true;
			isPresentRitardo = true;
			isPresentRitardoIdSimog = new boolean[ritadi.length];
			isPresentRitardoIdLocale = new boolean[ritadi.length];
			ritardoIdSimog = new String[ritadi.length];
			ritardoIdLocale = new String[ritadi.length];
			
			for(int i = 0; i < ritadi.length; i++){
				RitardoType ritardoCorrente = ritadi[i];
				
				isPresentRitardoIdSimog[i] = ritardoCorrente.getIDSCHEDASIMOG() != null;
				if(isPresentRitardoIdSimog[i]) ritardoIdSimog[i] = ritardoCorrente.getIDSCHEDASIMOG();
				
				isPresentRitardoIdLocale[i] = ritardoCorrente.getIDSCHEDALOCALE() != null;
				if(isPresentRitardoIdLocale[i]) ritardoIdLocale[i] = ritardoCorrente.getIDSCHEDALOCALE();
				
			}
		}
	}
	
	private boolean isPresentAccordi = false;
	private boolean[] isPresentAccordiIdSimog;
	private boolean[] isPresentAccordiIdLocale;
	private String[] accordiIdSimog;
	private String[] accordiIdLocale;
	
	public void setStatoXmlAccordi(SituazioneAttualeSchedeXml situazioneAttualeSchedeXml,IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica, boolean isInserimento ){
		AccordoBonarioType[] accordi = null;
		
		if(isInserimento){
			if(newInstanceInserimento.getScheda().getSchedaCompletaArray() != null &&
					newInstanceInserimento.getScheda().getSchedaCompletaArray(0).getDatiAccordi() != null)
				accordi = newInstanceInserimento.getScheda().getSchedaCompletaArray(0).getDatiAccordi().getAccordoBonarioArray();
		}else{
			if(newInstanceModifica.getScheda().getSchedaCompletaArray() != null &&
					newInstanceModifica.getScheda().getSchedaCompletaArray(0).getDatiAccordi() != null)
				accordi = newInstanceModifica.getScheda().getSchedaCompletaArray(0).getDatiAccordi().getAccordoBonarioArray();
		}	
		
		if(accordi != null && accordi.length > 0){
			
			isPresentSchedaCompleta = true;
			isPresentAccordi = true;
			isPresentAccordiIdSimog = new boolean[accordi.length];
			isPresentAccordiIdLocale = new boolean[accordi.length];
			accordiIdSimog = new String[accordi.length];
			accordiIdLocale = new String[accordi.length];
			
			for(int i = 0; i < accordi.length; i++){
				AccordoBonarioType accordoCorrente = accordi[i];
				
				isPresentAccordiIdSimog[i] = accordoCorrente.getIDSCHEDASIMOG() != null;
				if(isPresentAccordiIdSimog[i]) accordiIdSimog[i] = accordoCorrente.getIDSCHEDASIMOG();
				
				isPresentAccordiIdLocale[i] = accordoCorrente.getIDSCHEDALOCALE() != null;
				if(isPresentAccordiIdLocale[i]) accordiIdLocale[i] = accordoCorrente.getIDSCHEDALOCALE();
				
			}
		}		
	}
	
	private boolean isPresentSospensioni = false;
	private boolean[] isPresentSospensioniIdSimog;
	private boolean[] isPresentSospensioniIdLocale;
	private String[] sospensioniIdSimog;
	private String[] sospensioniIdLocale;
	
	public void setStatoXmlSospensioni(SituazioneAttualeSchedeXml situazioneAttualeSchedeXml,IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica, boolean isInserimento ){
		SospensioneType[] sospensioni = null;
		
		if(isInserimento){
			if(newInstanceInserimento.getScheda().getSchedaCompletaArray() != null &&
					newInstanceInserimento.getScheda().getSchedaCompletaArray(0).getDatiSospensioni() != null)
				sospensioni = newInstanceInserimento.getScheda().getSchedaCompletaArray(0).getDatiSospensioni().getSospensioneArray();
		}else{
			if(newInstanceModifica.getScheda().getSchedaCompletaArray() != null &&
					newInstanceModifica.getScheda().getSchedaCompletaArray(0).getDatiSospensioni() != null)
			sospensioni = newInstanceModifica.getScheda().getSchedaCompletaArray(0).getDatiSospensioni().getSospensioneArray();
		}	
		
		if(sospensioni != null && sospensioni.length > 0){
			
			isPresentSchedaCompleta = true;
			isPresentSospensioni = true;
			isPresentSospensioniIdSimog = new boolean[sospensioni.length];
			isPresentSospensioniIdLocale = new boolean[sospensioni.length];
			sospensioniIdSimog = new String[sospensioni.length];
			sospensioniIdLocale = new String[sospensioni.length];
			
			for(int i = 0; i < sospensioni.length; i++){
				SospensioneType sospensioneCorrente = sospensioni[i];
				
				isPresentSospensioniIdSimog[i] = sospensioneCorrente.getIDSCHEDASIMOG() != null;
				if(isPresentSospensioniIdSimog[i]) sospensioniIdSimog[i] = sospensioneCorrente.getIDSCHEDASIMOG();
				
				isPresentSospensioniIdLocale[i] = sospensioneCorrente.getIDSCHEDALOCALE() != null;
				if(isPresentSospensioniIdLocale[i]) sospensioniIdLocale[i] = sospensioneCorrente.getIDSCHEDALOCALE();
				
			}
		}		
	}
	
	private boolean isPresentSubAppalti = false;
	private boolean[] isPresentSubAppaltiIdSimog;
	private boolean[] isPresentSubAppaltiIdLocale;
	private String[] subAppaltiIdSimog;
	private String[] subAppaltiIdLocale;
	
	public void setStatoXmlSubappalti(SituazioneAttualeSchedeXml situazioneAttualeSchedeXml,IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica, boolean isInserimento ){
		SubappaltoType[] subappalti = null;
		
		if(isInserimento){
			if(newInstanceInserimento.getScheda().getSchedaCompletaArray() != null &&
					newInstanceInserimento.getScheda().getSchedaCompletaArray(0).getDatiSubappalti() != null)
				subappalti = newInstanceInserimento.getScheda().getSchedaCompletaArray(0).getDatiSubappalti().getSubappaltoArray();
		}else{
			if(newInstanceModifica.getScheda().getSchedaCompletaArray() != null &&
					newInstanceModifica.getScheda().getSchedaCompletaArray(0).getDatiSubappalti() != null)
				subappalti = newInstanceModifica.getScheda().getSchedaCompletaArray(0).getDatiSubappalti().getSubappaltoArray();
		}	
		
		if(subappalti != null && subappalti.length > 0){
			
			isPresentSchedaCompleta = true;
			isPresentSubAppalti = true;
			isPresentSubAppaltiIdSimog = new boolean[subappalti.length];
			isPresentSubAppaltiIdLocale = new boolean[subappalti.length];
			subAppaltiIdSimog = new String[subappalti.length];
			subAppaltiIdLocale = new String[subappalti.length];
			
			for(int i = 0; i < subappalti.length; i++){
				SubappaltoType subappaltoCorrente = subappalti[i];
				
				isPresentSubAppaltiIdSimog[i] = subappaltoCorrente.getIDSCHEDASIMOG() != null;
				if(isPresentSubAppaltiIdSimog[i]) subAppaltiIdSimog[i] = subappaltoCorrente.getIDSCHEDASIMOG();
				
				isPresentSubAppaltiIdLocale[i] = subappaltoCorrente.getIDSCHEDALOCALE() != null;
				if(isPresentSubAppaltiIdLocale[i]) subAppaltiIdLocale[i] = subappaltoCorrente.getIDSCHEDALOCALE();
				
			}
		}		
	}
	
	private boolean isPresentVarianti = false;
	private boolean[] isPresentVariantiIdSimog;
	private boolean[] isPresentVariantiIdLocale;
	private String[] variantiIdSimog;
	private String[] variantiIdLocale;
	
	public void setStatoXmlVarianti(SituazioneAttualeSchedeXml situazioneAttualeSchedeXml,IdsSchedaXML newInstanceInserimento,IdsSchedaXML newInstanceModifica, boolean isInserimento ){
		VarianteType[] varianti = null;
		
		if(isInserimento){
			if(newInstanceInserimento.getScheda().getSchedaCompletaArray() != null &&
					newInstanceInserimento.getScheda().getSchedaCompletaArray(0).getDatiVarianti() != null)
				varianti = newInstanceInserimento.getScheda().getSchedaCompletaArray(0).getDatiVarianti().getVarianteArray();
		}else{
			if(newInstanceModifica.getScheda().getSchedaCompletaArray() != null &&
					newInstanceModifica.getScheda().getSchedaCompletaArray(0).getDatiVarianti() != null)
				varianti = newInstanceModifica.getScheda().getSchedaCompletaArray(0).getDatiVarianti().getVarianteArray();
		}	
		
		if(varianti != null && varianti.length > 0){
			
			isPresentSchedaCompleta = true;
			isPresentVarianti = true;
			isPresentVariantiIdSimog = new boolean[varianti.length];
			isPresentVariantiIdLocale = new boolean[varianti.length];
			variantiIdSimog = new String[varianti.length];
			variantiIdLocale = new String[varianti.length];
			
			for(int i = 0; i < varianti.length; i++){
				VarianteType varianteCorrente = varianti[i];
				
				isPresentVariantiIdSimog[i] = varianteCorrente.getVariante().getIDSCHEDASIMOG() != null;
				if(isPresentVariantiIdSimog[i]) variantiIdSimog[i] = varianteCorrente.getVariante().getIDSCHEDASIMOG();
				
				isPresentVariantiIdLocale[i] = varianteCorrente.getVariante().getIDSCHEDALOCALE() != null;
				if(isPresentVariantiIdLocale[i]) variantiIdLocale[i] = varianteCorrente.getVariante().getIDSCHEDALOCALE();
				
			}
		}		
	}
	
	
	
	public boolean isPresentDatiComuni() {
		return isPresentDatiComuni;
	}
	public void setPresentDatiComuni(boolean isPresentDatiComuni) {
		this.isPresentDatiComuni = isPresentDatiComuni;
	}
	public boolean isPresentAggiudicazione() {
		return isPresentAggiudicazione;
	}
	public void setPresentAggiudicazione(boolean isPresentAggiudicazione) {
		this.isPresentAggiudicazione = isPresentAggiudicazione;
	}
	public boolean isPresentInizioLavori() {
		return isPresentInizioLavori;
	}
	public void setPresentInizioLavori(boolean isPresentInizioLavori) {
		this.isPresentInizioLavori = isPresentInizioLavori;
	}
	public boolean isPresentAvanzamenti() {
		return isPresentAvanzamenti;
	}
	public void setPresentAvanzamenti(boolean isPresentAvanzamenti) {
		this.isPresentAvanzamenti = isPresentAvanzamenti;
	}
	public boolean isPresentConclusione() {
		return isPresentConclusione;
	}
	public void setPresentConclusione(boolean isPresentConclusione) {
		this.isPresentConclusione = isPresentConclusione;
	}
	public boolean isPresentCollaudo() {
		return isPresentCollaudo;
	}
	public void setPresentCollaudo(boolean isPresentCollaudo) {
		this.isPresentCollaudo = isPresentCollaudo;
	}
	public boolean isPresentRitardo() {
		return isPresentRitardo;
	}
	public void setPresentRitardo(boolean isPresentRitardo) {
		this.isPresentRitardo = isPresentRitardo;
	}
	public boolean isPresentAccordi() {
		return isPresentAccordi;
	}
	public void setPresentAccordi(boolean isPresentAccordi) {
		this.isPresentAccordi = isPresentAccordi;
	}
	public boolean isPresentSospensioni() {
		return isPresentSospensioni;
	}
	public void setPresentSospensioni(boolean isPresentSospensioni) {
		this.isPresentSospensioni = isPresentSospensioni;
	}
	public boolean isPresentSubAppalti() {
		return isPresentSubAppalti;
	}
	public void setPresentSubAppalti(boolean isPresentSubAppalti) {
		this.isPresentSubAppalti = isPresentSubAppalti;
	}
	public boolean isPresentVarianti() {
		return isPresentVarianti;
	}
	public void setPresentVarianti(boolean isPresentVarianti) {
		this.isPresentVarianti = isPresentVarianti;
	}
	public boolean isPresentCUI() {
		return isPresentCUI;
	}
	public void setPresentCUI(boolean isPresentCUI) {
		this.isPresentCUI = isPresentCUI;
	}
	public boolean isPresentSchedaCompleta() {
		return isPresentSchedaCompleta;
	}
	public void setPresentSchedaCompleta(boolean isPresentSchedaCompleta) {
		this.isPresentSchedaCompleta = isPresentSchedaCompleta;
	}
	public boolean isPresentDatiComuniIdSimog() {
		return isPresentDatiComuniIdSimog;
	}
	public boolean isPresentDatiComuniIdLocale() {
		return isPresentDatiComuniIdLocale;
	}
	public String getDatiComuniIdSimog() {
		return datiComuniIdSimog;
	}
	public String getDatiComuniIdLocale() {
		return datiComuniIdLocale;
	}
	public boolean isPresentAggiudicazioneIdSimog() {
		return isPresentAggiudicazioneIdSimog;
	}
	public boolean isPresentAggiudicazioneIdLocale() {
		return isPresentAggiudicazioneIdLocale;
	}
	public String getAggiudicazioneIdSimog() {
		return aggiudicazioneIdSimog;
	}
	public String getAggiudicazioneIdLocale() {
		return aggiudicazioneIdLocale;
	}
	public boolean isPresentInizioLavoriIdSimog() {
		return isPresentInizioLavoriIdSimog;
	}
	public boolean isPresentInizioLavoriIdLocale() {
		return isPresentInizioLavoriIdLocale;
	}
	public String getInizioLavoriIdSimog() {
		return inizioLavoriIdSimog;
	}
	public String getInizioLavoriIdLocale() {
		return inizioLavoriIdLocale;
	}
	public boolean[] getIsPresentAvanzamentiIdSimog() {
		return isPresentAvanzamentiIdSimog;
	}
	public boolean[] getIsPresentAvanzamentiIdLocale() {
		return isPresentAvanzamentiIdLocale;
	}
	public String[] getAvanzamentiIdSimog() {
		return avanzamentiIdSimog;
	}
	public String[] getAvanzamentiIdLocale() {
		return avanzamentiIdLocale;
	}
	public boolean isPresentConclusioneIdSimog() {
		return isPresentConclusioneIdSimog;
	}
	public boolean isPresentConclusioneIdLocale() {
		return isPresentConclusioneIdLocale;
	}
	public String getConclusioneIdSimog() {
		return conclusioneIdSimog;
	}
	public String getConclusioneIdLocale() {
		return conclusioneIdLocale;
	}
	public boolean isPresentCollaudoIdSimog() {
		return isPresentCollaudoIdSimog;
	}
	public boolean isPresentCollaudoIdLocale() {
		return isPresentCollaudoIdLocale;
	}
	public String getCollaudoIdSimog() {
		return collaudoIdSimog;
	}
	public String getCollaudoIdLocale() {
		return collaudoIdLocale;
	}
	public boolean[] getIsPresentRitardoIdSimog() {
		return isPresentRitardoIdSimog;
	}
	public boolean[] getIsPresentRitardoIdLocale() {
		return isPresentRitardoIdLocale;
	}
	public String[] getRitardoIdSimog() {
		return ritardoIdSimog;
	}
	public String[] getRitardoIdLocale() {
		return ritardoIdLocale;
	}
	public boolean[] getIsPresentAccordiIdSimog() {
		return isPresentAccordiIdSimog;
	}
	public boolean[] getIsPresentAccordiIdLocale() {
		return isPresentAccordiIdLocale;
	}
	public String[] getAccordiIdSimog() {
		return accordiIdSimog;
	}
	public String[] getAccordiIdLocale() {
		return accordiIdLocale;
	}
	public boolean[] getIsPresentSospensioniIdSimog() {
		return isPresentSospensioniIdSimog;
	}
	public boolean[] getIsPresentSospensioniIdLocale() {
		return isPresentSospensioniIdLocale;
	}
	public String[] getSospensioniIdSimog() {
		return sospensioniIdSimog;
	}
	public String[] getSospensioniIdLocale() {
		return sospensioniIdLocale;
	}
	public boolean[] getIsPresentSubAppaltiIdSimog() {
		return isPresentSubAppaltiIdSimog;
	}
	public boolean[] getIsPresentSubAppaltiIdLocale() {
		return isPresentSubAppaltiIdLocale;
	}
	public String[] getSubAppaltiIdSimog() {
		return subAppaltiIdSimog;
	}
	public String[] getSubAppaltiIdLocale() {
		return subAppaltiIdLocale;
	}
	public boolean[] getIsPresentVariantiIdSimog() {
		return isPresentVariantiIdSimog;
	}
	public boolean[] getIsPresentVariantiIdLocale() {
		return isPresentVariantiIdLocale;
	}
	public String[] getVariantiIdSimog() {
		return variantiIdSimog;
	}
	public String[] getVariantiIdLocale() {
		return variantiIdLocale;
	}
	public void setPresentDatiComuniIdSimog(boolean isPresentDatiComuniIdSimog) {
		this.isPresentDatiComuniIdSimog = isPresentDatiComuniIdSimog;
	}
	public void setPresentDatiComuniIdLocale(boolean isPresentDatiComuniIdLocale) {
		this.isPresentDatiComuniIdLocale = isPresentDatiComuniIdLocale;
	}
	public void setDatiComuniIdSimog(String datiComuniIdSimog) {
		this.datiComuniIdSimog = datiComuniIdSimog;
	}
	public void setDatiComuniIdLocale(String datiComuniIdLocale) {
		this.datiComuniIdLocale = datiComuniIdLocale;
	}
	public void setPresentAggiudicazioneIdSimog(
			boolean isPresentAggiudicazioneIdSimog) {
		this.isPresentAggiudicazioneIdSimog = isPresentAggiudicazioneIdSimog;
	}
	public void setPresentAggiudicazioneIdLocale(
			boolean isPresentAggiudicazioneIdLocale) {
		this.isPresentAggiudicazioneIdLocale = isPresentAggiudicazioneIdLocale;
	}
	public void setAggiudicazioneIdSimog(String aggiudicazioneIdSimog) {
		this.aggiudicazioneIdSimog = aggiudicazioneIdSimog;
	}
	public void setAggiudicazioneIdLocale(String aggiudicazioneIdLocale) {
		this.aggiudicazioneIdLocale = aggiudicazioneIdLocale;
	}
	public void setPresentInizioLavoriIdSimog(boolean isPresentInizioLavoriIdSimog) {
		this.isPresentInizioLavoriIdSimog = isPresentInizioLavoriIdSimog;
	}
	public void setPresentInizioLavoriIdLocale(boolean isPresentInizioLavoriIdLocale) {
		this.isPresentInizioLavoriIdLocale = isPresentInizioLavoriIdLocale;
	}
	public void setInizioLavoriIdSimog(String inizioLavoriIdSimog) {
		this.inizioLavoriIdSimog = inizioLavoriIdSimog;
	}
	public void setInizioLavoriIdLocale(String inizioLavoriIdLocale) {
		this.inizioLavoriIdLocale = inizioLavoriIdLocale;
	}
	public void setIsPresentAvanzamentiIdSimog(boolean[] isPresentAvanzamentiIdSimog) {
		this.isPresentAvanzamentiIdSimog = isPresentAvanzamentiIdSimog;
	}
	public void setIsPresentAvanzamentiIdLocale(
			boolean[] isPresentAvanzamentiIdLocale) {
		this.isPresentAvanzamentiIdLocale = isPresentAvanzamentiIdLocale;
	}
	public void setAvanzamentiIdSimog(String[] avanzamentiIdSimog) {
		this.avanzamentiIdSimog = avanzamentiIdSimog;
	}
	public void setAvanzamentiIdLocale(String[] avanzamentiIdLocale) {
		this.avanzamentiIdLocale = avanzamentiIdLocale;
	}
	public void setPresentConclusioneIdSimog(boolean isPresentConclusioneIdSimog) {
		this.isPresentConclusioneIdSimog = isPresentConclusioneIdSimog;
	}
	public void setPresentConclusioneIdLocale(boolean isPresentConclusioneIdLocale) {
		this.isPresentConclusioneIdLocale = isPresentConclusioneIdLocale;
	}
	public void setConclusioneIdSimog(String conclusioneIdSimog) {
		this.conclusioneIdSimog = conclusioneIdSimog;
	}
	public void setConclusioneIdLocale(String conclusioneIdLocale) {
		this.conclusioneIdLocale = conclusioneIdLocale;
	}
	public void setPresentCollaudoIdSimog(boolean isPresentCollaudoIdSimog) {
		this.isPresentCollaudoIdSimog = isPresentCollaudoIdSimog;
	}
	public void setPresentCollaudoIdLocale(boolean isPresentCollaudoIdLocale) {
		this.isPresentCollaudoIdLocale = isPresentCollaudoIdLocale;
	}
	public void setCollaudoIdSimog(String collaudoIdSimog) {
		this.collaudoIdSimog = collaudoIdSimog;
	}
	public void setCollaudoIdLocale(String collaudoIdLocale) {
		this.collaudoIdLocale = collaudoIdLocale;
	}
	public void setIsPresentRitardoIdSimog(boolean[] isPresentRitardoIdSimog) {
		this.isPresentRitardoIdSimog = isPresentRitardoIdSimog;
	}
	public void setIsPresentRitardoIdLocale(boolean[] isPresentRitardoIdLocale) {
		this.isPresentRitardoIdLocale = isPresentRitardoIdLocale;
	}
	public void setRitardoIdSimog(String[] ritardoIdSimog) {
		this.ritardoIdSimog = ritardoIdSimog;
	}
	public void setRitardoIdLocale(String[] ritardoIdLocale) {
		this.ritardoIdLocale = ritardoIdLocale;
	}
	public void setIsPresentAccordiIdSimog(boolean[] isPresentAccordiIdSimog) {
		this.isPresentAccordiIdSimog = isPresentAccordiIdSimog;
	}
	public void setIsPresentAccordiIdLocale(boolean[] isPresentAccordiIdLocale) {
		this.isPresentAccordiIdLocale = isPresentAccordiIdLocale;
	}
	public void setAccordiIdSimog(String[] accordiIdSimog) {
		this.accordiIdSimog = accordiIdSimog;
	}
	public void setAccordiIdLocale(String[] accordiIdLocale) {
		this.accordiIdLocale = accordiIdLocale;
	}
	public void setIsPresentSospensioniIdSimog(boolean[] isPresentSospensioniIdSimog) {
		this.isPresentSospensioniIdSimog = isPresentSospensioniIdSimog;
	}
	public void setIsPresentSospensioniIdLocale(
			boolean[] isPresentSospensioniIdLocale) {
		this.isPresentSospensioniIdLocale = isPresentSospensioniIdLocale;
	}
	public void setSospensioniIdSimog(String[] sospensioniIdSimog) {
		this.sospensioniIdSimog = sospensioniIdSimog;
	}
	public void setSospensioniIdLocale(String[] sospensioniIdLocale) {
		this.sospensioniIdLocale = sospensioniIdLocale;
	}
	public void setIsPresentSubAppaltiIdSimog(boolean[] isPresentSubAppaltiIdSimog) {
		this.isPresentSubAppaltiIdSimog = isPresentSubAppaltiIdSimog;
	}
	public void setIsPresentSubAppaltiIdLocale(boolean[] isPresentSubAppaltiIdLocale) {
		this.isPresentSubAppaltiIdLocale = isPresentSubAppaltiIdLocale;
	}
	public void setSubAppaltiIdSimog(String[] subAppaltiIdSimog) {
		this.subAppaltiIdSimog = subAppaltiIdSimog;
	}
	public void setSubAppaltiIdLocale(String[] subAppaltiIdLocale) {
		this.subAppaltiIdLocale = subAppaltiIdLocale;
	}
	public void setIsPresentVariantiIdSimog(boolean[] isPresentVariantiIdSimog) {
		this.isPresentVariantiIdSimog = isPresentVariantiIdSimog;
	}
	public void setIsPresentVariantiIdLocale(boolean[] isPresentVariantiIdLocale) {
		this.isPresentVariantiIdLocale = isPresentVariantiIdLocale;
	}
	public void setVariantiIdSimog(String[] variantiIdSimog) {
		this.variantiIdSimog = variantiIdSimog;
	}
	public void setVariantiIdLocale(String[] variantiIdLocale) {
		this.variantiIdLocale = variantiIdLocale;
	}
	
	/**
	 * Metodo che "itera" tra le varibili di classe per controllare se almeno
	 * una scheda contiene un idLocale..
	 * 
	 * @return
	 */
	public boolean thereIsSomeIdLocale(){
		boolean esito = false;
		esito = isPresentDatiComuniIdLocale 
		      || isPresentAggiudicazioneIdLocale
		      || isPresentEsclusoIdLocale
		      || isPresentSottosogliaIdLocale
		      || isPresentAdesioneIdLocale
		      || isPresentInizioLavoriIdLocale 
		      || isPresentConclusioneIdLocale
			  || isPresentCollaudoIdLocale;
		if(!esito){
			esito = iteraArrayBooleano(isPresentAccordiIdLocale) 
						|| iteraArrayBooleano(isPresentAvanzamentiIdLocale) 
						|| iteraArrayBooleano(isPresentRitardoIdLocale) 
						|| iteraArrayBooleano(isPresentSospensioniIdLocale)
						|| iteraArrayBooleano(isPresentSubAppaltiIdLocale) 
						|| iteraArrayBooleano(isPresentVariantiIdLocale) ;
		}
		return esito;
	}
	/**
	 * Metodo che "itera" tra le varibili di classe per controllare se almeno
	 * una scheda contiene un idSimog..
	 * 
	 * @return
	 */
	public boolean thereIsSomeIdSimog(){
		boolean esito = false;
		esito = isPresentDatiComuniIdSimog 
		      || isPresentAggiudicazioneIdSimog
		      || isPresentEsclusoIdSimog
		      || isPresentSottosogliaIdSimog
		      || isPresentAdesioneIdSimog
		      || isPresentInizioLavoriIdSimog 
		      || isPresentConclusioneIdSimog
			  || isPresentCollaudoIdSimog;
		if(!esito){
			esito = iteraArrayBooleano(isPresentAccordiIdSimog) 
						|| iteraArrayBooleano(isPresentAvanzamentiIdSimog) 
						|| iteraArrayBooleano(isPresentRitardoIdSimog) 
						|| iteraArrayBooleano(isPresentSospensioniIdSimog)
						|| iteraArrayBooleano(isPresentSubAppaltiIdSimog) 
						|| iteraArrayBooleano(isPresentVariantiIdSimog) ;
		}
		return esito;
	}	
	/**
	 * @deprecated-comment
	 * Ritorna una lista di NomiScheda non vuota (default empty)
	 * se trova delle schede non coerenti ovvero sia sono presenti id locali o simog ma non su tutte le schede.
	 * 
	 * In sintesi se i due metodi lincati sotto ritornano true effettuo un controllo piu' esteso
	 * dato che i metodi menzionati non tengono conto dell'esistenza delle schede (i flag sono di default false o null per le multiple)
	 * 
	 * @see thereIsSomeIdLocale()
	 * @see thereIsSomeIdSimog()
	 * 
	 * @return
	 */
	/**
	 * Controlla la coerenza dei soli id locale
	 * 
	 * @return
	 */
	public EsitoControlloFormaleIds situazioneIds(){
		
		EsitoControlloFormaleIds esito = new EsitoControlloFormaleIds();
		ArrayList<String> listaNomiSchedeNonValideVoid = new ArrayList<String>();
		ArrayList<String> listaNomiSchedeNonValideLoaded = new ArrayList<String>();
		
		// se esiste almeno un id_locale e un id_simog e' un'errore perche non e' permesso il funzionamento ibrido (id_locale + id_simog)
		if(this.thereIsSomeIdLocale() && this.thereIsSomeIdSimog()){
			// errore tutti.. break flux
			esito.setErrorOverAllSchede(true);
			esito.setErrore(Messaggi.SIMOG_MASSLOADER_202);
			esito.setEsitoOperazione(false);
			return esito;
		}
		// se esiste anche solo un id_locale deve essere presente in tutte le schede. 
		if(this.thereIsSomeIdLocale()){
			if(isPresentDatiComuni)
				if(!isPresentDatiComuniIdLocale) listaNomiSchedeNonValideVoid.add(IdentificativoSchede.DATI_COMUNI);
			
			if(isPresentAggiudicazione)
				if(!isPresentAggiudicazioneIdLocale) listaNomiSchedeNonValideVoid.add(IdentificativoSchede.AGGIUDICAZIONE);
			
            if(isPresentAdesione)
               if(!isPresentAdesioneIdLocale) listaNomiSchedeNonValideVoid.add(IdentificativoSchede.ADESIONE);
           
            if(isPresentEscluso)
               if(!isPresentEsclusoIdLocale) listaNomiSchedeNonValideVoid.add(IdentificativoSchede.ESCLUSO);
           
            if(isPresentSottosoglia)
               if(!isPresentSottosogliaIdLocale) listaNomiSchedeNonValideVoid.add(IdentificativoSchede.SOTTOSOGLIA);
           
			if(isPresentInizioLavori)
				if(!isPresentInizioLavoriIdLocale) listaNomiSchedeNonValideVoid.add(IdentificativoSchede.FASE_INIZIALE);
			
			if(isPresentAvanzamenti)
				if(!isPresentForAll(isPresentAvanzamentiIdLocale)) listaNomiSchedeNonValideVoid.add(IdentificativoSchede.STATO_AVANZAMENTO);
			
			if(isPresentConclusione)
				if(!isPresentConclusioneIdLocale) listaNomiSchedeNonValideVoid.add(IdentificativoSchede.FINE_LAVORI);
			
			if(isPresentCollaudo)
				if(!isPresentCollaudoIdLocale) listaNomiSchedeNonValideVoid.add(IdentificativoSchede.COLLAUDO);
			
			if(isPresentRitardo)
				if(!isPresentForAll(isPresentRitardoIdLocale)) listaNomiSchedeNonValideVoid.add(IdentificativoSchede.IPOTESI_RECESSO);
			
			if(isPresentSospensioni)
				if(!isPresentForAll(isPresentSospensioniIdLocale)) listaNomiSchedeNonValideVoid.add(IdentificativoSchede.SOSPENSIONE);
			
			if(isPresentSubAppalti)
				if(!isPresentForAll(isPresentSubAppaltiIdLocale)) listaNomiSchedeNonValideVoid.add(IdentificativoSchede.SUBAPPALTO);
			
			if(isPresentVarianti)
				if(!isPresentForAll(isPresentVariantiIdLocale)) listaNomiSchedeNonValideVoid.add(IdentificativoSchede.VARIANTE);
			
			if(isPresentAccordi)
				if(!isPresentForAll(isPresentAccordiIdLocale)) listaNomiSchedeNonValideVoid.add(IdentificativoSchede.ACCORDO_BONARIO);
		
		}
		// se esiste anche un solo id_simog effettua un controllo
		if(this.thereIsSomeIdSimog()){
			
			// se NON e' presente il cui tutte le schede diverse da dati comuni non devono avere id_simog
			if(!isPresentCUI){
				
				if(isPresentAggiudicazione)
					if(isPresentAggiudicazioneIdSimog) listaNomiSchedeNonValideLoaded.add(IdentificativoSchede.AGGIUDICAZIONE);
				
                if(isPresentAdesione)
                   if(isPresentAdesioneIdSimog) listaNomiSchedeNonValideLoaded.add(IdentificativoSchede.ADESIONE);
                
                if(isPresentEscluso)
                   if(isPresentEsclusoIdSimog) listaNomiSchedeNonValideLoaded.add(IdentificativoSchede.ESCLUSO);
                
                if(isPresentSottosoglia)
                   if(isPresentSottosogliaIdSimog) listaNomiSchedeNonValideLoaded.add(IdentificativoSchede.SOTTOSOGLIA);

                if(isPresentInizioLavori)
					if(isPresentInizioLavoriIdSimog) listaNomiSchedeNonValideLoaded.add(IdentificativoSchede.FASE_INIZIALE);
				
				if(isPresentAvanzamenti)
					if(isPresentForAll(isPresentAvanzamentiIdSimog)) listaNomiSchedeNonValideLoaded.add(IdentificativoSchede.STATO_AVANZAMENTO);
				
				if(isPresentConclusione)
					if(isPresentConclusioneIdSimog) listaNomiSchedeNonValideLoaded.add(IdentificativoSchede.FINE_LAVORI);
				
				if(isPresentCollaudo)
					if(isPresentCollaudoIdSimog) listaNomiSchedeNonValideLoaded.add(IdentificativoSchede.COLLAUDO);
				
				if(isPresentRitardo)
					if(!isPresentForAll(isPresentRitardoIdSimog)) listaNomiSchedeNonValideLoaded.add(IdentificativoSchede.IPOTESI_RECESSO);
				
				if(isPresentSospensioni)
					if(!isPresentForAll(isPresentSospensioniIdSimog)) listaNomiSchedeNonValideLoaded.add(IdentificativoSchede.SOSPENSIONE);
				
				if(isPresentSubAppalti)
					if(!isPresentForAll(isPresentSubAppaltiIdSimog)) listaNomiSchedeNonValideLoaded.add(IdentificativoSchede.SUBAPPALTO);
				
				if(isPresentVarianti)
					if(!isPresentForAll(isPresentVariantiIdSimog)) listaNomiSchedeNonValideLoaded.add(IdentificativoSchede.VARIANTE);
				
				if(isPresentAccordi)
					if(!isPresentForAll(isPresentAccordiIdSimog)) listaNomiSchedeNonValideLoaded.add(IdentificativoSchede.ACCORDO_BONARIO);				
			}			
			// se e' presente il cui (attezione all'inserimento progressivo...)
			if(isPresentCUI){
				
				// ed e' presente l'aggiudicazione 
				if(isPresentAggiudicazione){
					// l'aggiudicazione deve riportare l'id_simog
					if(!isPresentAggiudicazioneIdSimog){
						esito.setErrorOverAllSchede(true);
						esito.setErrore(Messaggi.SIMOG_MASSLOADER_203);
						esito.setEsitoOperazione(false);
						return esito;
					}// listaNomiSchedeNonValide.add(IdentificativoSchede.AGGIUDICAZIONE);
				}
				else if(isPresentAdesione){
                   // l'aggiudicazione deve riportare l'id_simog
                   if(!isPresentAdesioneIdSimog){
                       esito.setErrorOverAllSchede(true);
                       esito.setErrore(Messaggi.SIMOG_MASSLOADER_203);
                       esito.setEsitoOperazione(false);
                       return esito;
                   }
               }
                else if(isPresentEscluso){
                   // l'aggiudicazione deve riportare l'id_simog
                   if(!isPresentEsclusoIdSimog){
                       esito.setErrorOverAllSchede(true);
                       esito.setErrore(Messaggi.SIMOG_MASSLOADER_203);
                       esito.setEsitoOperazione(false);
                       return esito;
                   }
               }
                else if(isPresentSottosoglia){
                   // l'aggiudicazione deve riportare l'id_simog
                   if(!isPresentSottosogliaIdSimog){
                       esito.setErrorOverAllSchede(true);
                       esito.setErrore(Messaggi.SIMOG_MASSLOADER_203);
                       esito.setEsitoOperazione(false);
                       return esito;
                   }
               }
				
			}
		}
		// se la lista void e' non vuota c'e' qualche errore
		if(!listaNomiSchedeNonValideVoid.isEmpty()){
			esito.setErrorOnlyOnSomeSchedeVoid(true);
			esito.setListOfNomiSchedeNonValideVoid(listaNomiSchedeNonValideVoid);
			esito.setEsitoOperazione(false);
		}
		// se la lista loaded e' non vuota c'e' qualche errore
		if(!listaNomiSchedeNonValideLoaded.isEmpty()){
			esito.setErrorOnlyOnSomeSchedeLoaded(true);
			esito.setListOfNomiSchedeNonValideLoaded(listaNomiSchedeNonValideLoaded);
			esito.setEsitoOperazione(false);
		}
		return esito;
	}
	/**
	 * Controlla se contiene almeno un true nel array
	 * 
	 * @param array
	 * @return
	 */
	public boolean iteraArrayBooleano(boolean[] array){
		if(array != null){
			for(int i = 0; i < array.length; i++){
				if(array[i]) return true;
			}
		}return false;
	}
	
	/**
	 * Metodo di appoggio che controlla che tutti i valori di un'array booleano
	 * siano true, altrimenti ritorna false 
	 * 
	 * @param listOfBool
	 * @return
	 */
	private boolean isPresentForAll(boolean[] listOfBool){
		
		for(int i = 0; i < listOfBool.length; i++){
			if(!listOfBool[i]) return false;
		}return true;
	}
//	public void setDatiComuni(DatiComuniType datiComuni){
//		if(datiComuni.isSetIDSCHEDALOCALE()){
//			this.isPresentDatiComuniIdLocale = true;
//			this.datiComuniIdLocale = datiComuni.get
//		}
//		if(datiComuni.isSetIDSCHEDASIMOG()){
//			this.isPresentDatiComuniIdSimog = true;
//			this.datiComuniIdSimog = 
//		}
//	}



	public boolean isPresentSottosoglia() {
		return isPresentSottosoglia;
	}



	public void setPresentSottosoglia(boolean isPresentSottosoglia) {
		this.isPresentSottosoglia = isPresentSottosoglia;
	}



	public boolean isPresentSottosogliaIdSimog() {
		return isPresentSottosogliaIdSimog;
	}



	public void setPresentSottosogliaIdSimog(boolean isPresentSottosogliaIdSimog) {
		this.isPresentSottosogliaIdSimog = isPresentSottosogliaIdSimog;
	}



	public boolean isPresentSottosogliaIdLocale() {
		return isPresentSottosogliaIdLocale;
	}



	public void setPresentSottosogliaIdLocale(boolean isPresentSottosogliaIdLocale) {
		this.isPresentSottosogliaIdLocale = isPresentSottosogliaIdLocale;
	}



	public String getSottosogliaIdSimog() {
		return sottosogliaIdSimog;
	}



	public void setSottosogliaIdSimog(String sottosogliaIdSimog) {
		this.sottosogliaIdSimog = sottosogliaIdSimog;
	}



	public String getSottosogliaIdLocale() {
		return sottosogliaIdLocale;
	}



	public void setSottosogliaIdLocale(String sottosogliaIdLocale) {
		this.sottosogliaIdLocale = sottosogliaIdLocale;
	}



	public boolean isPresentEscluso() {
		return isPresentEscluso;
	}



	public void setPresentEscluso(boolean isPresentEscluso) {
		this.isPresentEscluso = isPresentEscluso;
	}



	public boolean isPresentEsclusoIdSimog() {
		return isPresentEsclusoIdSimog;
	}



	public void setPresentEsclusoIdSimog(boolean isPresentEsclusoIdSimog) {
		this.isPresentEsclusoIdSimog = isPresentEsclusoIdSimog;
	}



	public boolean isPresentEsclusoIdLocale() {
		return isPresentEsclusoIdLocale;
	}



	public void setPresentEsclusoIdLocale(boolean isPresentEsclusoIdLocale) {
		this.isPresentEsclusoIdLocale = isPresentEsclusoIdLocale;
	}



	public String getEsclusoIdSimog() {
		return esclusoIdSimog;
	}



	public void setEsclusoIdSimog(String esclusoIdSimog) {
		this.esclusoIdSimog = esclusoIdSimog;
	}



	public String getEsclusoIdLocale() {
		return esclusoIdLocale;
	}



	public void setEsclusoIdLocale(String esclusoIdLocale) {
		this.esclusoIdLocale = esclusoIdLocale;
	}



	public boolean isPresentStipula() {
		return isPresentStipula;
	}



	public void setPresentStipula(boolean isPresentStipula) {
		this.isPresentStipula = isPresentStipula;
	}



	public boolean isPresentStipulaIdSimog() {
		return isPresentStipulaIdSimog;
	}



	public void setPresentStipulaIdSimog(boolean isPresentStipulaIdSimog) {
		this.isPresentStipulaIdSimog = isPresentStipulaIdSimog;
	}



	public boolean isPresentStipulaIdLocale() {
		return isPresentStipulaIdLocale;
	}



	public void setPresentStipulaIdLocale(boolean isPresentStipulaIdLocale) {
		this.isPresentStipulaIdLocale = isPresentStipulaIdLocale;
	}



	public String getStipulaIdSimog() {
		return stipulaIdSimog;
	}



	public void setStipulaIdSimog(String stipulaIdSimog) {
		this.stipulaIdSimog = stipulaIdSimog;
	}



	public String getStipulaIdLocale() {
		return stipulaIdLocale;
	}



	public void setStipulaIdLocale(String stipulaIdLocale) {
		this.stipulaIdLocale = stipulaIdLocale;
	}



	public boolean isPresentAdesione() {
		return isPresentAdesione;
	}



	public void setPresentAdesione(boolean isPresentAdesione) {
		this.isPresentAdesione = isPresentAdesione;
	}



	public boolean isPresentAdesioneIdSimog() {
		return isPresentAdesioneIdSimog;
	}



	public void setPresentAdesioneIdSimog(boolean isPresentAdesioneIdSimog) {
		this.isPresentAdesioneIdSimog = isPresentAdesioneIdSimog;
	}



	public boolean isPresentAdesioneIdLocale() {
		return isPresentAdesioneIdLocale;
	}



	public void setPresentAdesioneIdLocale(boolean isPresentAdesioneIdLocale) {
		this.isPresentAdesioneIdLocale = isPresentAdesioneIdLocale;
	}



	public String getAdesioneIdSimog() {
		return adesioneIdSimog;
	}



	public void setAdesioneIdSimog(String adesioneIdSimog) {
		this.adesioneIdSimog = adesioneIdSimog;
	}



	public String getAdesioneIdLocale() {
		return adesioneIdLocale;
	}



	public void setAdesioneIdLocale(String adesioneIdLocale) {
		this.adesioneIdLocale = adesioneIdLocale;
	}



	public boolean isMultilotto() {
		return multilotto;
	}



	public void setMultilotto(boolean multilotto) {
		this.multilotto = multilotto;
	}



	public boolean isPrincipale() {
		return principale;
	}



	public void setPrincipale(boolean principale) {
		this.principale = principale;
	}
	
	
	
	
	
}
