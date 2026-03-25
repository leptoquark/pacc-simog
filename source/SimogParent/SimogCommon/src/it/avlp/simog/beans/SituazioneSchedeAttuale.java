package it.avlp.simog.beans;

import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.beans.stipula.StipulaBean;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.db.Costanti;

import java.util.ArrayList;
import java.util.List;

/**
 * Oggetto che memorizza lo stato del flusso con cardinalita' pari alle aggiudicazioni
 * ovvero ha cardinalita' 1 a 1 con il CUI.
 * Qualora si procedesse per CIG occore una collezione di questo oggetto per "fotografarne" gli
 * stati e la situazione.
 * 
 * 
 * @author vletizia
 *
 */
public class SituazioneSchedeAttuale implements ValorizzaSituazioneByBeanMulti,ValorizzaSituazioneByBean,Cloneable{
	
	private boolean multilotto;
	private boolean principale;
	
	private StatoScheda statoDatiComuni = new StatoScheda();
	private StatoScheda statoAggiudicazione = new StatoScheda();
	private StatoScheda statoInizioLavori = new StatoScheda();
	private ArrayList<StatoScheda> listOfStatoAvanzamento = new ArrayList<StatoScheda>();
	private StatoScheda statoConclusione = new StatoScheda();
	private StatoScheda statoCollaudo = new StatoScheda();
	private StatoScheda statoSottosoglia = new StatoScheda();
	private StatoScheda statoEscluso = new StatoScheda();
	private StatoScheda statoAdesione = new StatoScheda();
	private StatoScheda	statoStipula = new StatoScheda();
	private ArrayList<StatoScheda> listOfStatoRitardo = new ArrayList<StatoScheda>();
	private ArrayList<StatoScheda> listOfStatoAccordi = new ArrayList<StatoScheda>();
	private ArrayList<StatoScheda> listOfStatoSospensioni = new ArrayList<StatoScheda>();
	private ArrayList<StatoScheda> listOfStatoVarianti = new ArrayList<StatoScheda>();
	private ArrayList<StatoScheda> listOfStatoSubAppalti = new ArrayList<StatoScheda>();
	
	public StatoScheda getStatoDatiComuni() {
		return statoDatiComuni;
	}
	
	public void setStatoDatiComuni(StatoScheda statoDatiComuni) {
		this.statoDatiComuni = statoDatiComuni;
	}
	public void setStatoDatiComuni(InfoComuniBean infoComuniBean, String cig, int inRichiesta, boolean obblighiComunicativiSpeciali) {
		statoDatiComuni = new StatoScheda();
		if(infoComuniBean != null && infoComuniBean.getIdInfo() > 0){
			statoDatiComuni.setEsistente(true);
			
			boolean isAggiudicata = infoComuniBean.getEsitoProcedura().equals(EsitoEnum.AGGIUDICATA.codice());
			statoDatiComuni.setAggiudicata(isAggiudicata);
			
			boolean isSettoriSpeciali = infoComuniBean.getFlagEnteSpeciale().equalsIgnoreCase(Costanti.FLAG_VALORE_SI);
			statoDatiComuni.setSettoriSpeciali(isSettoriSpeciali);
			
			boolean isTipoLavori = infoComuniBean.getTipoContratto().equalsIgnoreCase(Costanti.TIPO_SCHEDA_LAVORI);
			statoDatiComuni.setLavori(isTipoLavori);
			
			statoDatiComuni.setStato(new Long(infoComuniBean.getIdStato()).intValue());
			
			statoDatiComuni.setInRichiesta(inRichiesta);
			
			statoDatiComuni.setIdRecord(infoComuniBean.getIdInfo());
			statoDatiComuni.setDataInizioRecord(infoComuniBean.getDataInizioInfo());
			statoDatiComuni.setIdLocale(infoComuniBean.getIdLocale());
			statoDatiComuni.setCig(cig);
			
         statoDatiComuni.setOrigine(infoComuniBean.getOrigine());
         
         statoDatiComuni.setObblighiComunicativiSpeciali(obblighiComunicativiSpeciali);
		}
	}
	
	
	public StatoScheda getStatoAggiudicazione() {
		return statoAggiudicazione;
	}
	public void setStatoAggiudicazione(StatoScheda statoAggiudicazione) {
		this.statoAggiudicazione = statoAggiudicazione;
	}
	
	public StatoScheda getStatoAggiudicazioneSottotipo(){
		if(findTipoAggiudicazione().equals(TipoAggiudicazione.S))
			return statoSottosoglia;
		if(findTipoAggiudicazione().equals(TipoAggiudicazione.E))
			return statoEscluso;
		if(findTipoAggiudicazione().equals(TipoAggiudicazione.Q))
			return statoAdesione;
		else return statoAggiudicazione;
	}
	
	public TipoAggiudicazione findTipoAggiudicazione(){
		if(statoSottosoglia != null && statoSottosoglia.isEsistente() )
			return TipoAggiudicazione.S;
		else if(statoEscluso != null && statoEscluso.isEsistente())
			return TipoAggiudicazione.E;
		else if(statoAdesione != null && statoAdesione.isEsistente())
			return TipoAggiudicazione.Q;
		else return TipoAggiudicazione.A;
	}
	
	public void setStatoAggiudicazione(AggiudicazioneBean aggiudicazioneBean, int inRichiesta) {
		statoAggiudicazione = new StatoScheda();
		if(aggiudicazioneBean != null && aggiudicazioneBean.getIdAggiudicazione() > 0){
			statoAggiudicazione.setEsistente(true);
			statoAggiudicazione.setStato(new Long(aggiudicazioneBean.getIdStato()).intValue());
			
			statoAggiudicazione.setInRichiesta(inRichiesta);
			
			statoAggiudicazione.setIdRecord(aggiudicazioneBean.getIdAggiudicazione());
			statoAggiudicazione.setIdAggiudicazione(aggiudicazioneBean.getIdAggiudicazione());
			statoAggiudicazione.setDataInizioRecord(aggiudicazioneBean.getDataInizioAggiudicazione());
            statoAggiudicazione.setDataInizioAggiudicazione(aggiudicazioneBean.getDataInizioAggiudicazione());
			statoAggiudicazione.setIdLocale(aggiudicazioneBean.getIdLocale());
			// cui from db = n-cig
			String cig = aggiudicazioneBean.getCui().split("-")[1];
			// cui corretto cig-progcui
			String cui = cig + "-" + aggiudicazioneBean.getProgCUI();
			statoAggiudicazione.setCui(cui);
			statoAggiudicazione.setCig(cig);
			multilotto = aggiudicazioneBean.getCodiceContratto() != null && aggiudicazioneBean.getCodiceContratto().trim().length() > 0;
			multilotto = multilotto && aggiudicazioneBean.getFlagAggiudPrincipale() != null && aggiudicazioneBean.getFlagAggiudPrincipale().trim().length() > 0;
			principale = Costanti.FLAG_VALORE_SI.equals(aggiudicazioneBean.getFlagAggiudPrincipale());
			//gm nuovo codice fromDb
			statoAggiudicazione.setFromDb(true);
			
			statoAggiudicazione.setOrigine(aggiudicazioneBean.getOrigine());
		}
	}
	
	public void setStatoSottosoglia(AggiudicazioneBean aggiudicazioneBean, int inRichiesta) {
		statoSottosoglia = new StatoScheda();
		if(aggiudicazioneBean != null && aggiudicazioneBean.getIdAggiudicazione() > 0){
			statoSottosoglia.setEsistente(true);
			statoSottosoglia.setStato(new Long(aggiudicazioneBean.getIdStato()).intValue());
			
			statoSottosoglia.setInRichiesta(inRichiesta);
			
			statoSottosoglia.setIdRecord(aggiudicazioneBean.getIdAggiudicazione());
			statoSottosoglia.setIdAggiudicazione(aggiudicazioneBean.getIdAggiudicazione());
			statoSottosoglia.setDataInizioRecord(aggiudicazioneBean.getDataInizioAggiudicazione());
			statoSottosoglia.setIdLocale(aggiudicazioneBean.getIdLocale());
			// cui from db = n-cig
			String cig = aggiudicazioneBean.getCui().split("-")[1];
			// cui corretto cig-progcui
			String cui = cig + "-" + aggiudicazioneBean.getProgCUI();
			statoSottosoglia.setCui(cui);
			statoSottosoglia.setCig(cig);
			//gm nuovo codice fromDb
			statoSottosoglia.setFromDb(true);
         statoSottosoglia.setOrigine(aggiudicazioneBean.getOrigine());

		}
	}
	
	public void setStatoEscluso(AggiudicazioneBean aggiudicazioneBean, int inRichiesta) {
		statoEscluso = new StatoScheda();
		if(aggiudicazioneBean != null && aggiudicazioneBean.getIdAggiudicazione() > 0){
			statoEscluso.setEsistente(true);
			statoEscluso.setStato(new Long(aggiudicazioneBean.getIdStato()).intValue());
			
			statoEscluso.setInRichiesta(inRichiesta);
			
			statoEscluso.setIdRecord(aggiudicazioneBean.getIdAggiudicazione());
			statoEscluso.setIdAggiudicazione(aggiudicazioneBean.getIdAggiudicazione());
			statoEscluso.setDataInizioRecord(aggiudicazioneBean.getDataInizioAggiudicazione());
			statoEscluso.setIdLocale(aggiudicazioneBean.getIdLocale());
			// cui from db = n-cig
			String cig = aggiudicazioneBean.getCui().split("-")[1];
			// cui corretto cig-progcui
			String cui = cig + "-" + aggiudicazioneBean.getProgCUI();
			statoEscluso.setCui(cui);
			statoEscluso.setCig(cig);
			//gm nuovo codice fromDb
			statoEscluso.setFromDb(true);
         statoEscluso.setOrigine(aggiudicazioneBean.getOrigine());
		}
	}
	
	public StatoScheda getStatoInizioLavori() {
		return statoInizioLavori;
	}
	public void setStatoInizioLavori(StatoScheda statoInizioLavori) {
		this.statoInizioLavori = statoInizioLavori;
	}
	public void setStatoInizioLavori(InizioLavoriBean inizioLavoriBean, int inRichiesta) {
		statoInizioLavori = new StatoScheda();
		if(inizioLavoriBean != null && inizioLavoriBean.getIdInizioLavori() > 0){
			statoInizioLavori.setEsistente(true);
			statoInizioLavori.setStato(new Long(inizioLavoriBean.getIdStato()).intValue());
			
			statoInizioLavori.setInRichiesta(inRichiesta);
			
			statoInizioLavori.setIdRecord(inizioLavoriBean.getIdInizioLavori());
			statoInizioLavori.setDataInizioRecord(inizioLavoriBean.getDataInizioLavori());
			statoInizioLavori.setIdAggiudicazione(inizioLavoriBean.getIdAggiudicazione());
			statoInizioLavori.setDataInizioAggiudicazione(inizioLavoriBean.getDataInizioAggiudicazione());
			statoInizioLavori.setIdLocale(inizioLavoriBean.getIdLocale());
		}
	}	
	
	
	
	public ArrayList<StatoScheda> getStatoAvanzamento() {
		return listOfStatoAvanzamento;
	}
	public void setStatoAvanzamento(ArrayList<StatoScheda> listOfStatoAvanzamento) {
		this.listOfStatoAvanzamento = listOfStatoAvanzamento;
	}
	public void setStatoAvanzamento(List<AvanzamentoBean> listOfAvanzamenti, ArrayList<Integer>  inRichiesta ) {						
		if(listOfAvanzamenti != null && listOfAvanzamenti.size() > 0){
			int i = 0;
			for(AvanzamentoBean avanzamentoBean : listOfAvanzamenti){
				
				StatoScheda statoAvanzamento = new StatoScheda();
				this.listOfStatoAvanzamento.add(statoAvanzamento);				
				statoAvanzamento.setEsistente(true);
				statoAvanzamento.setStato(new Long(avanzamentoBean.getIdStato()).intValue());
//				statoAvanzamento.setInRichiesta(Integer.valueOf(i));
				// aggiungi lo stato
				statoAvanzamento.setInRichiesta(inRichiesta.get(i));
				statoAvanzamento.setIdRecord(avanzamentoBean.getIdAvanzamento());
				statoAvanzamento.setDataInizioRecord(avanzamentoBean.getDataInizioAvanzamento());
				statoAvanzamento.setIdAggiudicazione(avanzamentoBean.getIdAggiudicazione());
				statoAvanzamento.setDataInizioAggiudicazione(avanzamentoBean.getDataInizioAggiudicazione());
				statoAvanzamento.setIdLocale(avanzamentoBean.getIdLocale());
				
				i++;
			}
		}
	}	
	
	public StatoScheda getStatoConclusione() {
		return statoConclusione;
	}
	public void setStatoConclusione(StatoScheda statoConclusione) {
		this.statoConclusione = statoConclusione;
	}
	public void setStatoConclusione(ConclusioneBean conclusioneBean, int inRichiesta) {
		statoConclusione = new StatoScheda();
		if(conclusioneBean != null && conclusioneBean.getIdUltim() > 0){
			statoConclusione.setEsistente(true);
			statoConclusione.setStato(new Long(conclusioneBean.getIdStato()).intValue());
			
			statoConclusione.setInRichiesta(inRichiesta);
			
			statoConclusione.setIdRecord(conclusioneBean.getIdUltim());
			statoConclusione.setDataInizioRecord(conclusioneBean.getDataIniUltim());
			statoConclusione.setIdAggiudicazione(conclusioneBean.getIdAggiudicazione());
			statoConclusione.setDataInizioAggiudicazione(conclusioneBean.getDataInizioAggiudicazione());
			statoConclusione.setIdLocale(conclusioneBean.getIdLocale());
		}
	}	
	
	public StatoScheda getStatoCollaudo() {
		return statoCollaudo;
	}
	public void setStatoCollaudo(StatoScheda statoCollaudo) {
		this.statoCollaudo = statoCollaudo;
	}
	public void setStatoCollaudo(CollaudoBean collaudoBean, int inRichiesta) {
		statoCollaudo = new StatoScheda();
		if(collaudoBean != null && collaudoBean.getIdCollaudo() > 0){
			statoCollaudo.setEsistente(true);
			statoCollaudo.setStato(new Long(collaudoBean.getIdStato()).intValue());
			
			statoCollaudo.setInRichiesta(inRichiesta);
			
			statoCollaudo.setIdRecord(collaudoBean.getIdCollaudo());
			statoCollaudo.setDataInizioRecord(collaudoBean.getDataIniColl());
			statoCollaudo.setIdAggiudicazione(collaudoBean.getIdAggiudicazione());
			statoCollaudo.setDataInizioAggiudicazione(collaudoBean.getDataIniAggiudicazione());
			statoCollaudo.setIdLocale(collaudoBean.getIdLocale());
		}
	}	
	
	
	public ArrayList<StatoScheda> getStatoRitardo() {
		return listOfStatoRitardo;
	}
	public void setStatoRitardo(ArrayList<StatoScheda> listOfStatoRitardo) {
		this.listOfStatoRitardo = listOfStatoRitardo;
	}
	public void setStatoRitardo(List<R129Bean> listOfR129, ArrayList<Integer>  inRichiesta ) {
		if(listOfR129 != null && listOfR129.size() > 0){
			int i = 0;
			for(R129Bean r129Bean : listOfR129){
				StatoScheda statoRitardo = new StatoScheda();
				this.listOfStatoRitardo.add(statoRitardo);
				
				statoRitardo.setEsistente(true);
				statoRitardo.setStato(new Long(r129Bean.getIdStato()).intValue());
				statoRitardo.setInRichiesta(inRichiesta.get(i).intValue());
				
				statoRitardo.setIdRecord(r129Bean.getIdRecord());
				statoRitardo.setDataInizioRecord(r129Bean.getDataInizioRecord());
				statoRitardo.setIdAggiudicazione(r129Bean.getIdAggiudicazione());
				statoRitardo.setDataInizioAggiudicazione(r129Bean.getDataInizioAggiudicazione());
				statoRitardo.setIdLocale(r129Bean.getIdLocale());
				i++;
				
			}
		}
	}
	
	
	public ArrayList<StatoScheda> getStatoAccordi() {
		return listOfStatoAccordi;
	}
	public void setStatoAccordi(ArrayList<StatoScheda> listOfStatoAccordi) {
		this.listOfStatoAccordi = listOfStatoAccordi;
	}
	public void setStatoAccordi(List<AccordoBean> listOfAccordi, ArrayList<Integer>  inRichiesta  ) {
		
		int i = 0;
		if(listOfAccordi != null && listOfAccordi.size() > 0){
			for(AccordoBean accordoBean : listOfAccordi){
				StatoScheda statoAccordi = new StatoScheda();
				this.listOfStatoAccordi.add(statoAccordi);
				
				statoAccordi.setEsistente(true);
				statoAccordi.setStato(new Long(accordoBean.getIdStato()).intValue());
				statoAccordi.setInRichiesta(inRichiesta.get(i));	
				
				statoAccordi.setIdRecord(accordoBean.getIdAccordo());
				statoAccordi.setDataInizioRecord(accordoBean.getDataInizioAccordo());
				statoAccordi.setIdAggiudicazione(accordoBean.getIdAggiudicazione());
				statoAccordi.setDataInizioAggiudicazione(accordoBean.getDataInizioAggiudicazione());
				statoAccordi.setIdLocale(accordoBean.getIdLocale());
				i++;
			}
		}
	}	
	
	
	public ArrayList<StatoScheda> getStatoSospensioni() {
		return listOfStatoSospensioni;
	}
	public void setStatoSospensioni(ArrayList<StatoScheda> listOfStatoSospensioni) {
		this.listOfStatoSospensioni = listOfStatoSospensioni;
	}
	public void setStatoSospensioni(List<SospensioniBean> listOfSospensioni, ArrayList<Integer>  inRichiesta ) {
		
		if(listOfSospensioni != null && listOfSospensioni.size() > 0){
			int i = 0;
			for(SospensioniBean sospensioniBean : listOfSospensioni){
				StatoScheda statoSospensioni = new StatoScheda();
				this.listOfStatoSospensioni.add(statoSospensioni);
				
				statoSospensioni.setEsistente(true);
				statoSospensioni.setStato(new Long(sospensioniBean.getIdStato()).intValue());
				statoSospensioni.setInRichiesta(inRichiesta.get(i));
				
				statoSospensioni.setIdRecord(sospensioniBean.getIdSospensione());
				statoSospensioni.setDataInizioRecord(sospensioniBean.getDataInizioSosp());
				statoSospensioni.setIdAggiudicazione(sospensioniBean.getIdAggiudicazione());
				statoSospensioni.setDataInizioAggiudicazione(sospensioniBean.getDataInizioAggiudicazione());
				statoSospensioni.setIdLocale(sospensioniBean.getIdLocale());
				i++;	
			}
		}
	}	
	
	
	public ArrayList<StatoScheda> getStatoVarianti() {
		return listOfStatoVarianti;
	}
	public void setStatoVarianti(ArrayList<StatoScheda> listOfStatoVarianti) {
		this.listOfStatoVarianti = listOfStatoVarianti;
	}
	public void setStatoVarianti(List<VarianteBean> listOfVarianti, ArrayList<Integer>  inRichiesta ) {		
		if(listOfVarianti != null && listOfVarianti.size() > 0){
			int i = 0;
			for(VarianteBean varianteBean : listOfVarianti){
				StatoScheda statoVarianti = new StatoScheda();
				this.listOfStatoVarianti.add(statoVarianti);
				
				statoVarianti.setEsistente(true);
				statoVarianti.setStato(new Long(varianteBean.getIdStato()).intValue());
				statoVarianti.setInRichiesta(inRichiesta.get(i));	
				
				statoVarianti.setIdRecord(varianteBean.getIdVariante());
				statoVarianti.setDataInizioRecord(varianteBean.getDataInizioVar());
				statoVarianti.setIdAggiudicazione(varianteBean.getIdAggiudicazione());
				statoVarianti.setDataInizioAggiudicazione(varianteBean.getDataInizioAggiudicazione());
				statoVarianti.setIdLocale(varianteBean.getIdLocale());
				i++;
			}
		}
	}	
	
	
	public ArrayList<StatoScheda> getStatoSubAppalti() {
		return listOfStatoSubAppalti;
	}
	public void setStatoSubAppalti(ArrayList<StatoScheda> listOfStatoSubAppalti) {
		this.listOfStatoSubAppalti = listOfStatoSubAppalti;
	}
	public void setStatoSubAppalti(List<SubappaltiBean> listOfSubAppalti, ArrayList<Integer>  inRichiesta ) {
		if(listOfSubAppalti != null && listOfSubAppalti.size() > 0){
			int i = 0;
			for(SubappaltiBean subappaltiBean : listOfSubAppalti){
				StatoScheda statoSubAppalti = new StatoScheda();
				this.listOfStatoSubAppalti.add(statoSubAppalti);
				
				statoSubAppalti.setEsistente(true);
				statoSubAppalti.setStato(new Long(subappaltiBean.getIdStato()).intValue());
				statoSubAppalti.setInRichiesta(inRichiesta.get(i));	
				
				statoSubAppalti.setIdRecord(subappaltiBean.getIdRecord());
				statoSubAppalti.setDataInizioRecord(subappaltiBean.getDataInizioRecord());
				statoSubAppalti.setIdAggiudicazione(subappaltiBean.getIdAggiudicazione());
				statoSubAppalti.setDataInizioAggiudicazione(subappaltiBean.getDataInizioAggiudicazione());
				statoSubAppalti.setIdLocale(subappaltiBean.getIdLocale());
				
				i++;
			}
		}
	}	
	/**
	 * Controlla se l'id locale sia contenuto in uno degli stati della lista nel qual
	 * caso valorizza l'index (3o parametro) con l'indice dello stato che corrisponde
	 * e ritorna true
	 * 
	 * @param idLocale
	 * @param statiScheda
	 * @param index
	 * @return
	 */
	public boolean controlla(String idLocale, ArrayList<StatoScheda> statiScheda, Integer index){
		boolean controllo = false;
		int posizione = 0;
		for(StatoScheda stato : statiScheda){
			// NOTA: l'id locale dello stato potrebbe essere nullo mentre quello del xml NO
			if(idLocale.equals(stato.getIdLocale())){ 
				index = new Integer(posizione);
				return true; 
			}
			posizione++;
		}return controllo;
	}
	/**
	 * Mi creo una copia dell'istanza non vincolata dall'ereditarieta'
	 * 
	 * @see java.lang.Object#clone()
	 * @deprecated
	 */
	public SituazioneSchedeAttuale clone() throws CloneNotSupportedException {
		SituazioneSchedeAttuale situazioneAttuale = (SituazioneSchedeAttuale)super.clone();
		return situazioneAttuale;
	}
	
	/**
	 * Genera un bean che in sintesi afferma che tutte le schede sono presenti sul db..
	 * @return
	 */
	public static SituazioneSchedeAttuale getSituazionePresenteAllInDb(){
		SituazioneSchedeAttuale situazioneCorreAttuale = new SituazioneSchedeAttuale();
		situazioneCorreAttuale.setStatoAccordi(StatoScheda.getListStatoSchedaPresenteDb());
		situazioneCorreAttuale.setStatoAggiudicazione(StatoScheda.getStatoSchedaPresenteDb());
		situazioneCorreAttuale.setStatoSottosoglia(StatoScheda.getStatoSchedaPresenteDb());
		situazioneCorreAttuale.setStatoEscluso(StatoScheda.getStatoSchedaPresenteDb());
		situazioneCorreAttuale.setStatoAvanzamento(StatoScheda.getListStatoSchedaPresenteDb());
		situazioneCorreAttuale.setStatoCollaudo(StatoScheda.getStatoSchedaPresenteDb());
		situazioneCorreAttuale.setStatoConclusione(StatoScheda.getStatoSchedaPresenteDb());
		situazioneCorreAttuale.setStatoDatiComuni(StatoScheda.getStatoSchedaPresenteDb());
		situazioneCorreAttuale.setStatoInizioLavori(StatoScheda.getStatoSchedaPresenteDb());
		situazioneCorreAttuale.setStatoRitardo(StatoScheda.getListStatoSchedaPresenteDb());
		situazioneCorreAttuale.setStatoSospensioni(StatoScheda.getListStatoSchedaPresenteDb());
		situazioneCorreAttuale.setStatoSubAppalti(StatoScheda.getListStatoSchedaPresenteDb());
		situazioneCorreAttuale.setStatoVarianti(StatoScheda.getListStatoSchedaPresenteDb());
		return situazioneCorreAttuale;
	}
	public StatoScheda getStatoSottosoglia() {
		return statoSottosoglia;
	}
	public void setStatoSottosoglia(StatoScheda statoSottosoglia) {
		this.statoSottosoglia = statoSottosoglia;
	}
	public StatoScheda getStatoEscluso() {
		return statoEscluso;
	}
	public void setStatoEscluso(StatoScheda statoEscluso) {
		this.statoEscluso = statoEscluso;
	}

	public StatoScheda getStatoAdesione() {
		return statoAdesione;
	}

	public void setStatoAdesione(StatoScheda statoAdesione) {
		this.statoAdesione = statoAdesione;
	}
	
	public void setStatoAdesione(AggiudicazioneBean aggiudicazioneBean, int inRichiesta) {
		statoAdesione = new StatoScheda();
		if(aggiudicazioneBean != null && aggiudicazioneBean.getIdAggiudicazione() > 0){
			statoAdesione.setEsistente(true);
			statoAdesione.setStato(new Long(aggiudicazioneBean.getIdStato()).intValue());
			
			statoAdesione.setInRichiesta(inRichiesta);
			
			statoAdesione.setIdRecord(aggiudicazioneBean.getIdAggiudicazione());
			statoAdesione.setIdAggiudicazione(aggiudicazioneBean.getIdAggiudicazione());
			statoAdesione.setDataInizioRecord(aggiudicazioneBean.getDataInizioAggiudicazione());
			statoAdesione.setIdLocale(aggiudicazioneBean.getIdLocale());
			// cui from db = n-cig
			String cig = aggiudicazioneBean.getCui().split("-")[1];
			// cui corretto cig-progcui
			String cui = cig + "-" + aggiudicazioneBean.getProgCUI();
			statoAdesione.setCui(cui);
			statoAdesione.setCig(cig);
			//gm nuovo codice fromDb
			statoAdesione.setFromDb(true);
         statoAdesione.setOrigine(aggiudicazioneBean.getOrigine());

		}
	}

	public StatoScheda getStatoStipula() {
		return statoStipula;
	}

	public void setStatoStipula(StatoScheda statoStipula) {
		this.statoStipula = statoStipula;
	}
	
	public void setStatoStipula(StipulaBean stipula, int inRichiesta) {
		statoStipula = new StatoScheda();
		if(stipula != null && stipula.getIdStipula() > 0){
			statoStipula.setEsistente(true);
			statoStipula.setStato(new Long(stipula.getIdStato()).intValue());
			
			statoStipula.setInRichiesta(inRichiesta);
			
			statoStipula.setIdRecord(stipula.getIdStipula());
			statoStipula.setDataInizioRecord(stipula.getDataInizioStipula());
			statoStipula.setIdAggiudicazione(stipula.getIdAggiudicazione());
			statoStipula.setDataInizioAggiudicazione(stipula.getDataInizioAggiudicazione());
			statoStipula.setIdLocale(stipula.getIdLocale());
		}
	}

	public StatoScheda getByIdScheda(int indiceScheda) {
		switch(indiceScheda){
			case IdentificativoSchede.INDICE_ACCORDO_BONARIO:
				return getStatoAccordi().size() > 0 ? getStatoAccordi().get(0) : null;
			case IdentificativoSchede.INDICE_ADESIONE:
				return getStatoAdesione();
			case IdentificativoSchede.INDICE_AGGIUDICAZIONE:
				return getStatoAggiudicazione();
			case IdentificativoSchede.INDICE_COLLAUDO:
				return getStatoCollaudo();
			case IdentificativoSchede.INDICE_DATI_COMUNI:
				return getStatoDatiComuni();
			case IdentificativoSchede.INDICE_ESCLUSO:
				return getStatoEscluso();
			case IdentificativoSchede.INDICE_FASE_INIZIALE:
				return getStatoInizioLavori();
			case IdentificativoSchede.INDICE_FINE_LAVORI:
				return getStatoConclusione();
			case IdentificativoSchede.INDICE_IPOTESI_RECESSO:
				return getStatoRitardo().size() > 0 ? getStatoRitardo().get(0) : null;
			case IdentificativoSchede.INDICE_SOSPENSIONE:
				return getStatoSospensioni().size() > 0 ? getStatoSospensioni().get(0) : null;
			case IdentificativoSchede.INDICE_SOTTOSOGLIA:
				return getStatoSottosoglia();
			case IdentificativoSchede.INDICE_STATO_AVANZAMENTO:
				return getStatoAvanzamento().size() > 0 ? getStatoAvanzamento().get(0) : null;
			case IdentificativoSchede.INDICE_STIPULA:
				return getStatoStipula();
			case IdentificativoSchede.INDICE_SUBAPPALTO:
				return getStatoSubAppalti().size() > 0 ? getStatoSubAppalti().get(0) : null;
			case IdentificativoSchede.INDICE_VARIANTE:
				return getStatoVarianti().size() > 0 ? getStatoVarianti().get(0) : null;
		}
		
		return null;
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
