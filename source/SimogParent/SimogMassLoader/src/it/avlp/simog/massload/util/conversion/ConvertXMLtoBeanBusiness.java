package it.avlp.simog.massload.util.conversion;

import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.CondizioneAggBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.beans.aggiudicazione.RequisitiBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.aggiudicazione.TipoFinanziamentoBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.inizio.PosizioneAggiudicatarioBean;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.beans.stipula.StipulaBean;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.beans.variante.EventiMotiviVariantiBean;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.massload.util.conversion.impl.ConvertAccordo;
import it.avlp.simog.massload.util.conversion.impl.ConvertAdesione;
import it.avlp.simog.massload.util.conversion.impl.ConvertAggiudicazione;
import it.avlp.simog.massload.util.conversion.impl.ConvertAnagrafiche;
import it.avlp.simog.massload.util.conversion.impl.ConvertAvanzamento;
import it.avlp.simog.massload.util.conversion.impl.ConvertCollaudo;
import it.avlp.simog.massload.util.conversion.impl.ConvertConclusione;
import it.avlp.simog.massload.util.conversion.impl.ConvertInizioLavori;
import it.avlp.simog.massload.util.conversion.impl.ConvertRitardo;
import it.avlp.simog.massload.util.conversion.impl.ConvertSospensione;
import it.avlp.simog.massload.util.conversion.impl.ConvertSottoEscluso;
import it.avlp.simog.massload.util.conversion.impl.ConvertStipula;
import it.avlp.simog.massload.util.conversion.impl.ConvertSubAppalto;
import it.avlp.simog.massload.util.conversion.impl.ConvertVariante;
import it.avlp.simog.massload.util.duplicated.RimuoviDuplicatiBusiness;
import it.avlp.simog.massload.util.duplicated.feedBack.AggiudicatarioDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.AnagraficaPartecipanteDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.AnagraficaResponsabileDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.CondizioneDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.DittaAusiliariaDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.EventiDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.FinanziamentoDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.IncaricatoDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.PosizioneDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.RequisitoDuplicateFeedBack;
import it.avlp.simog.massload.util.duplicated.feedBack.TipoAppaltoDuplicateFeedBack;
import it.avlp.simog.massload.xmlbeans.AccordoBonarioType;
import it.avlp.simog.massload.xmlbeans.AggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType;
import it.avlp.simog.massload.xmlbeans.AppaltoType;
import it.avlp.simog.massload.xmlbeans.AvanzamentoType;
import it.avlp.simog.massload.xmlbeans.CollaudoType;
import it.avlp.simog.massload.xmlbeans.ConclusioneType;
import it.avlp.simog.massload.xmlbeans.CondizioneType;
import it.avlp.simog.massload.xmlbeans.DatiComuniType;
import it.avlp.simog.massload.xmlbeans.DittaAusiliariaType;
import it.avlp.simog.massload.xmlbeans.FinanziamentoType;
import it.avlp.simog.massload.xmlbeans.IncaricatoType;
import it.avlp.simog.massload.xmlbeans.InizioType;
import it.avlp.simog.massload.xmlbeans.PosizioneType;
import it.avlp.simog.massload.xmlbeans.PubblicazioneType;
import it.avlp.simog.massload.xmlbeans.RecMotivoVarType;
import it.avlp.simog.massload.xmlbeans.RecVarianteType;
import it.avlp.simog.massload.xmlbeans.RequisitoType;
import it.avlp.simog.massload.xmlbeans.ResponsabileType;
import it.avlp.simog.massload.xmlbeans.RitardoType;
import it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.SospensioneType;
import it.avlp.simog.massload.xmlbeans.SottoEsclusoType;
import it.avlp.simog.massload.xmlbeans.StipulaType;
import it.avlp.simog.massload.xmlbeans.SubappaltoType;
import it.avlp.simog.massload.xmlbeans.TipiAppaltoType;
import it.avlp.simog.util.ConvertDatiComuni;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;



/**
 * Classe che si occupa esclusivamente della conversione dei dati che hanno corrispondenza
 * tra xml e simog bean, qualora alcuni dati necessitino di dati provenienti da schede diverse
 * dal quella in conversione, oppura dal db, occorrera' effettuare il setting a parte.
 * 
 * NOTA:  che il settaggio degli attributi multipli per una scheda hanno un flag che 
 * permette di abilitare la ricerca e la rimozione dei duplicati, se il sistema trova 
 * dei duplicati li rimuove e la variabile booleana di classe containsDuplicate sara' valorizzata a true
 * e l'oggetto di classe validation bean conterra le specifiche di feedback, tali variabili saranno "resettate"
 * ad ogni chiamata ad una funzione "multipla" della classe.
 * 
 * @author vletizia
 *
 */
public class ConvertXMLtoBeanBusiness {
	
	public ValidationBean duplicateWarning = null;
	public boolean containsDuplicate = false;
	
	public String tipoSettore = "";
	
	
	/************************* AGGIUDICAZIONI STUFF **************************/
	/**
	 * @param appalto
	 * @return
	 */
	public AggiudicazioneBean converti(AppaltoType appalto){
		return ConvertAggiudicazione.getInstance().converti(appalto, tipoSettore);
	}
	
	/************************* ADESIONE STUFF **************************/
	/**
	 * @param appalto
	 * @return
	 */
	public AggiudicazioneBean converti(AppaltoAdesioneType appalto){
		return ConvertAdesione.getInstance().converti(appalto, tipoSettore);
	}
	
	
	/************************* SOTTOSOGLIA STUFF **************************/
	/**
	 * @param appalto
	 * @return
	 */
	public AggiudicazioneBean converti(SottoEsclusoType sottoEscl, TipoAggiudicazione sottotipo){
		return ConvertSottoEscluso.getInstance().converti(sottoEscl, tipoSettore, sottotipo);
	}
	/**
	 * @param tipiAppaltoForniture
	 * @param removeDuplicate
	 * @return
	 * @throws ClassNotFoundException : ha come origine la factory di istanziazione della implementazione specifica per il controllo dei duplicati
	 * @throws Exception : ha come origine l'istanziazione del validation bean livello errore non esistente
	 */
	public List<TipoAppaltoAggBean> convertiF(TipiAppaltoType[] tipiAppaltoForniture, boolean removeDuplicate) throws ClassNotFoundException, Exception{
		// se devo controllare e rimuovere duplicati
		if(removeDuplicate){
			duplicateWarning = null; containsDuplicate = false; 
			RimuoviDuplicatiBusiness remover = new RimuoviDuplicatiBusiness();
			TipoAppaltoDuplicateFeedBack feedBack = remover.rimuoviDuplicatiAppaltoForniture(tipiAppaltoForniture);
			if(feedBack.isContainsDuplicate()){
				containsDuplicate = feedBack.isContainsDuplicate();
				duplicateWarning = feedBack.getDuplicateError();
			}return ConvertAggiudicazione.getInstance().convertiTipoAppaltiForniture(feedBack.getListOfTipoAppalto());
		}
		// altrimenti
		return ConvertAggiudicazione.getInstance().convertiTipoAppaltiForniture(tipiAppaltoForniture);
	}
	/**
	 * @param tipiAppaltoLavori
	 * @param removeDuplicate
	 * @return
	 * @throws ClassNotFoundException : ha come origine la factory di istanziazione della implementazione specifica per il controllo dei duplicati
	 * @throws Exception : ha come origine l'istanziazione del validation bean livello errore non esistente
	 */
	public List<TipoAppaltoAggBean> convertiL(TipiAppaltoType[] tipiAppaltoLavori, boolean removeDuplicate) throws ClassNotFoundException, Exception{
		// se devo controllare e rimuovere duplicati
		if(removeDuplicate){
			duplicateWarning = null; containsDuplicate = false; 
			RimuoviDuplicatiBusiness remover = new RimuoviDuplicatiBusiness();
			TipoAppaltoDuplicateFeedBack feedBack = remover.rimuoviDuplicatiAppaltoLavori(tipiAppaltoLavori);
			if(feedBack.isContainsDuplicate()){
				containsDuplicate = feedBack.isContainsDuplicate();
				duplicateWarning = feedBack.getDuplicateError();
			}return ConvertAggiudicazione.getInstance().convertiTipoAppaltiForniture(feedBack.getListOfTipoAppalto());
		}
		// altrimenti
		return ConvertAggiudicazione.getInstance().convertiTipoAppaltiLavori(tipiAppaltoLavori);
	}	
	/**
	 * @param finanziamenti
	 * @param removeDuplicate
	 * @return
	 * @throws ClassNotFoundException : ha come origine la factory di istanziazione della implementazione specifica per il controllo dei duplicati
	 * @throws Exception : ha come origine l'istanziazione del validation bean livello errore non esistente
	 
	 * MEV 27/06/2022 rimozione del controllo sui finanziamenti duplicati in quanto devono essere 
	 * sommati e non rimossi
	 */
	public List<TipoFinanziamentoBean> convertiFinanziamenti(FinanziamentoType[] finanziamenti, boolean removeDuplicate) throws ClassNotFoundException, Exception{
		// se devo controllare e rimuovere duplicati
//		if(removeDuplicate){
//			duplicateWarning = null; containsDuplicate = false; 
//			RimuoviDuplicatiBusiness remover = new RimuoviDuplicatiBusiness();
//			FinanziamentoDuplicateFeedBack feedBack = remover.rimuoviDuplicatiFinanziamenti(finanziamenti);
//			if(feedBack.isContainsDuplicate()){
//				containsDuplicate = feedBack.isContainsDuplicate();
//				duplicateWarning = feedBack.getDuplicateError();
//			}return ConvertAggiudicazione.getInstance().convertiFinanziamenti(feedBack.getListOfFinanziamenti());
//		}
		// altrimenti
		return ConvertAggiudicazione.getInstance().convertiFinanziamenti(finanziamenti);
	}
	/**
	 * @param condizioni
	 * @param removeDuplicate
	 * @return
	 * @throws ClassNotFoundException : ha come origine la factory di istanziazione della implementazione specifica per il controllo dei duplicati
	 * @throws Exception : ha come origine l'istanziazione del validation bean livello errore non esistente
	 */
	public List<CondizioneAggBean> convertiCondizioni(CondizioneType[] condizioni, boolean removeDuplicate) throws ClassNotFoundException, Exception{
		// se devo controllare e rimuovere duplicati
		if(removeDuplicate){
			duplicateWarning = null; containsDuplicate = false; 
			RimuoviDuplicatiBusiness remover = new RimuoviDuplicatiBusiness();
			CondizioneDuplicateFeedBack feedBack = remover.rimuoviDuplicatiCondizioni(condizioni);
			if(feedBack.isContainsDuplicate()){
				containsDuplicate = feedBack.isContainsDuplicate();
				duplicateWarning = feedBack.getDuplicateError();
			}return ConvertAggiudicazione.getInstance().convertiCondizioni(feedBack.getListOfCondizione());
		}
		// altrimenti
		return ConvertAggiudicazione.getInstance().convertiCondizioni(condizioni);
	}
	/**
	 * @param requisiti
	 * @param removeDuplicate
	 * @return
	 * @throws ClassNotFoundException : ha come origine la factory di istanziazione della implementazione specifica per il controllo dei duplicati
	 * @throws Exception : ha come origine l'istanziazione del validation bean livello errore non esistente
	 */
	public List<RequisitiBean> convertiRequisiti(RequisitoType[] requisiti, boolean removeDuplicate) throws ClassNotFoundException, Exception{
		// se devo controllare e rimuovere duplicati
		if(removeDuplicate){
			duplicateWarning = null; containsDuplicate = false; 
			RimuoviDuplicatiBusiness remover = new RimuoviDuplicatiBusiness();
			RequisitoDuplicateFeedBack feedBack = remover.rimuoviDuplicatiRequisiti(requisiti);
			if(feedBack.isContainsDuplicate()){
				containsDuplicate = feedBack.isContainsDuplicate();
				duplicateWarning = feedBack.getDuplicateError();
			}return ConvertAggiudicazione.getInstance().convertiRequisiti(feedBack.getListOfRequisito());
		}
		// altrimenti
		return ConvertAggiudicazione.getInstance().convertiRequisiti(requisiti);
	}
	/**
	 * @param aggiudicatario
	 * @param removeDuplicate
	 * @return
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public List<AggiudicatarioBean> convertiAggiudicatari(SoggAggiudicatarioType[] aggiudicatario, boolean removeDuplicate) throws ClassNotFoundException, Exception{
		// se devo controllare e rimuovere duplicati
		if(removeDuplicate){
			duplicateWarning = null; containsDuplicate = false; 
			RimuoviDuplicatiBusiness remover = new RimuoviDuplicatiBusiness();
			AggiudicatarioDuplicateFeedBack feedBack = remover.rimuoviDuplicatiAggiudicatari(aggiudicatario);
			if(feedBack.isContainsDuplicate()){
				containsDuplicate = feedBack.isContainsDuplicate();
				duplicateWarning = feedBack.getDuplicateError();
			}return ConvertAggiudicazione.getInstance().convertiAggiudicatari(feedBack.getListOfAggiudicatari());
		}
		// altrimenti
		return ConvertAggiudicazione.getInstance().convertiAggiudicatari(aggiudicatario);		
	}
	
	/**
	 * @param aggiudicatario
	 * @param removeDuplicate
	 * @return
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public List<DittaAusiliariaBean> convertiDitteAusiliarie(DittaAusiliariaType[] ditta, boolean removeDuplicate) throws ClassNotFoundException, Exception{
		// se devo controllare e rimuovere duplicati
		if(removeDuplicate){
			duplicateWarning = null; containsDuplicate = false; 
			RimuoviDuplicatiBusiness remover = new RimuoviDuplicatiBusiness();
			DittaAusiliariaDuplicateFeedBack feedBack = remover.rimuoviDuplicatiDitteAusiliarie(ditta);
			if(feedBack.isContainsDuplicate()){
				containsDuplicate = feedBack.isContainsDuplicate();
				duplicateWarning = feedBack.getDuplicateError();
			}return ConvertAggiudicazione.getInstance().convertiDitteAusiliarie(feedBack.getListOfDitteAusiliarie());
		}
		// altrimenti
		return ConvertAggiudicazione.getInstance().convertiDitteAusiliarie(ditta);		
	}
	
	/**
	 * @param incaricato
	 * @param removeDuplicate
	 * @param sezioneResp TODO
	 * @return
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public Map<String, List<ResponsabileBean>> convertiIncaricatiAggiudicazione(IncaricatoType[] incaricato, boolean removeDuplicate, String sezioneResp) throws ClassNotFoundException, Exception{
		List<ResponsabileBean> listOfResponsabiliDaSeparare = null;
		Map<String, List<ResponsabileBean>> mappaResponsabiliSeparati = new TreeMap<String, List<ResponsabileBean>>();
		// se devo controllare e rimuovere duplicati
		if(removeDuplicate){
			duplicateWarning = null; containsDuplicate = false; 
			RimuoviDuplicatiBusiness remover = new RimuoviDuplicatiBusiness();
			IncaricatoDuplicateFeedBack feedBack = remover.rimuoviDuplicatiIncaricati(incaricato);
			if(feedBack.isContainsDuplicate()){
				containsDuplicate = feedBack.isContainsDuplicate();
				duplicateWarning = feedBack.getDuplicateError();
			}
			listOfResponsabiliDaSeparare = ConvertAggiudicazione.getInstance()
												.convertiIncaricati(feedBack.getListOfIncaricati());
			mappaResponsabiliSeparati.put(PSBD.SEZIONE_PA, getRespPA(listOfResponsabiliDaSeparare));
			mappaResponsabiliSeparati.put(sezioneResp, getRespRA(listOfResponsabiliDaSeparare));
			return mappaResponsabiliSeparati;

		}
		// altrimenti
		listOfResponsabiliDaSeparare = ConvertAggiudicazione.getInstance().convertiIncaricati(incaricato);
		mappaResponsabiliSeparati.put(PSBD.SEZIONE_PA, getRespPA(listOfResponsabiliDaSeparare));
		mappaResponsabiliSeparati.put(sezioneResp, getRespRA(listOfResponsabiliDaSeparare));
		return mappaResponsabiliSeparati;
	}
	/**
	 * Da una lista di reponsabili misti PA e RA restituisce una lista con i soli PA
	 * @param incaricati
	 * @return
	 */
	private List<ResponsabileBean> getRespPA(List<ResponsabileBean> incaricati){
		List<ResponsabileBean> lprestazioni = new ArrayList<ResponsabileBean>();
		for(ResponsabileBean rb : incaricati){
			if(rb.getSezione().equalsIgnoreCase(PSBD.SEZIONE_PA)){
				lprestazioni.add(rb);
			}
		}return lprestazioni;
	}
	/**
	 * Da una lista di reponsabili misti PA e RA restituisce una lista con i soli RA
	 * @return List&lt;ResponsabileBean&gt;
	 */
	private List<ResponsabileBean> getRespRA(List<ResponsabileBean> incaricati){
		List<ResponsabileBean> lresponsabili = new ArrayList<ResponsabileBean>();
		for(ResponsabileBean rb : incaricati){
			if(!rb.getSezione().equalsIgnoreCase(PSBD.SEZIONE_PA)){
				lresponsabili.add(rb);
			}
		}return lresponsabili;
	}	
	/************************* DATI COMUNI STUFF *************************/
	/**
	 * @param datiComuni
	 * @return
	 */
	public InfoComuniBean converti(DatiComuniType datiComuni){
		return ConvertDatiComuni.getInstance().converti(datiComuni);
	}
	/**
	 * @param pubblicazione
	 * @return
	 */
	public PubblicazioneBean converti(PubblicazioneType pubblicazione){
		return ConvertDatiComuni.getInstance().convertiPubblicazione(pubblicazione);
	}
	
	/************************* ACCORDO STUFF *************************/
	/**
	 * @param accordo
	 * @return
	 */
	public AccordoBean converti(AccordoBonarioType accordo){
		return ConvertAccordo.getInstance().converti(accordo);
	}
	
	
	
	/************************* AVANZAMENTO STUFF *************************/
	
	/**
	 * @param avanzamento
	 * @return
	 */
	public AvanzamentoBean converti(AvanzamentoType avanzamento, int numeroAvanzamento){
		return ConvertAvanzamento.getInstance().converti(avanzamento, numeroAvanzamento);
	}
	
	
	/************************* CONCLUSIONE STUFF *************************/
	/**
	 * @param conclusione
	 * @return
	 */
	public ConclusioneBean converti(ConclusioneType conclusione){
		return ConvertConclusione.getInstance().converti(conclusione);
	}
	
	/************************* COLLAUDO STUFF *************************/
	/**
	 * @param collaudo
	 * @return
	 */
	public CollaudoBean converti(CollaudoType collaudo){
		return ConvertCollaudo.getInstance().converti(collaudo);
	}
	/**
	 * @param incaricati
	 * @param removeDuplicate
	 * @return
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public List<ResponsabileBean> convertiIncaricatiCollaudo(IncaricatoType[] incaricati, boolean removeDuplicate) throws ClassNotFoundException, Exception{
		if(removeDuplicate){
			this.containsDuplicate = false; this.duplicateWarning = null;
			RimuoviDuplicatiBusiness remover = new RimuoviDuplicatiBusiness();
			IncaricatoDuplicateFeedBack feedBack = remover.rimuoviDuplicatiIncaricati(incaricati);
			if(feedBack.isContainsDuplicate()){
				containsDuplicate = feedBack.isContainsDuplicate();
				duplicateWarning = feedBack.getDuplicateError();
			}return ConvertCollaudo.getInstance().convertiIncaricati(feedBack.getListOfIncaricati());
		}
		return ConvertCollaudo.getInstance().convertiIncaricati(incaricati);
	}
	
	/************************* VARIANTE STUFF *************************/
	
	/**
	 * @param variante
	 * @return
	 */
	public VarianteBean converti(RecVarianteType variante){
		return ConvertVariante.getInstance().converti(variante);
	}
	
	/**
	 * @param eventiVarianti
	 * @return
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public List<EventiMotiviVariantiBean> convertiEventi(RecMotivoVarType[] eventiVarianti, boolean removeDuplicate) throws ClassNotFoundException, Exception{
		if(removeDuplicate){
			this.containsDuplicate = false; this.duplicateWarning = null;
			RimuoviDuplicatiBusiness remover = new RimuoviDuplicatiBusiness();
			EventiDuplicateFeedBack feedBack = remover.rimuoviDuplicatiEventi(eventiVarianti);
			if(feedBack.isContainsDuplicate()){
				containsDuplicate = feedBack.isContainsDuplicate();
				duplicateWarning = feedBack.getDuplicateError();
			}return ConvertVariante.getInstance().convertiEventi(feedBack.getListOfEventi());
		}
		return ConvertVariante.getInstance().convertiEventi(eventiVarianti);		
	}
	
	/************************* INIZIO LAVORI STUFF *************************/
	
	/**
	 * @param inizioLavori
	 * @return
	 */
	public InizioLavoriBean converti(InizioType inizioLavori){
		return ConvertInizioLavori.getInstance().converti(inizioLavori);
	}
	/**
	 * @param pubblicazione
	 * @return
	 */
	public PubblicazioneBean convertiPubblicazioneInizioLavori(PubblicazioneType pubblicazione){
		return ConvertInizioLavori.getInstance().convertiPubblicazione(pubblicazione);
	}
	/**
	 * @param posizioni
	 * @param removeDuplicate
	 * @return
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public List<PosizioneAggiudicatarioBean> convertiPosizioniInizioLavori(PosizioneType[] posizioni, boolean removeDuplicate) throws ClassNotFoundException, Exception{
		if(removeDuplicate){
			this.containsDuplicate = false; this.duplicateWarning = null;
			RimuoviDuplicatiBusiness remover = new RimuoviDuplicatiBusiness();
			PosizioneDuplicateFeedBack feedBack = remover.rimuoviDuplicatiPosizioni(posizioni);
			if(feedBack.isContainsDuplicate()){
				containsDuplicate = feedBack.isContainsDuplicate();
				duplicateWarning = feedBack.getDuplicateError();
			}return ConvertInizioLavori.getInstance().convertiPosizioni(feedBack.getListOfPosizione());
		}
		return ConvertInizioLavori.getInstance().convertiPosizioni(posizioni);
	}
	/**
	 * @param incaricato
	 * @param removeDuplicate
	 * @return
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public List<ResponsabileBean> convertiIncaricatiInizioLavori(IncaricatoType[] incaricato, boolean removeDuplicate) throws ClassNotFoundException, Exception{
		if(removeDuplicate){
			this.containsDuplicate = false; this.duplicateWarning = null;
			RimuoviDuplicatiBusiness remover = new RimuoviDuplicatiBusiness();
			IncaricatoDuplicateFeedBack feedBack = remover.rimuoviDuplicatiIncaricati(incaricato);
			if(feedBack.isContainsDuplicate()){
				containsDuplicate = feedBack.isContainsDuplicate();
				duplicateWarning = feedBack.getDuplicateError();
			}return ConvertInizioLavori.getInstance().convertiIncaricati(feedBack.getListOfIncaricati());
		}
		return ConvertInizioLavori.getInstance().convertiIncaricati(incaricato);
	}
	/************************* ISOSPENSIONE STUFF *************************/
	public SospensioniBean converti(SospensioneType sospensione){
		return ConvertSospensione.getInstance().converti(sospensione);
	}
	
	/************************* SUBAPPALTO STUFF *************************/
	public SubappaltiBean converti(SubappaltoType subappalto){
		return ConvertSubAppalto.getInstance().converti(subappalto);
	}
	/************************* RITARDO STUFF *************************/
	public R129Bean converti(RitardoType ritardo){
		return ConvertRitardo.getInstance().converti(ritardo);
	}
	
	/**************************************************************************/
	/**********************	ANAGRAFICHE STUFF  ********************************/
	/**************************************************************************/
	
	/**
	 * @param arrayDiAnaPartecipanti
	 * @param removeDuplicate
	 * @return
	 * @throws Exception
	 */
	public List<SoggettoPartecipanteBean> convertiAnagraficheAggiudicatari(AggiudicatarioType[] arrayDiAnaPartecipanti, boolean removeDuplicate){
		if(removeDuplicate){
			this.containsDuplicate = false; this.duplicateWarning = null;
			RimuoviDuplicatiBusiness remover = new RimuoviDuplicatiBusiness();
			AnagraficaPartecipanteDuplicateFeedBack feedBack = remover.rimuoviDuplicatiAnaPartecipanti(arrayDiAnaPartecipanti);
			if(feedBack.isContainsDuplicate()){
				containsDuplicate = feedBack.isContainsDuplicate();
				duplicateWarning = feedBack.getDuplicateError();
			}return ConvertAnagrafiche.getInstance().convertiAnagraficheAggiudicatari(feedBack.getArrayOfAnagrafichePartecipanti());
		}
		return ConvertAnagrafiche.getInstance().convertiAnagraficheAggiudicatari(arrayDiAnaPartecipanti);
	}
	/**
	 * @param arrayDiAnaResponsabili
	 * @param removeDuplicate
	 * @return
	 * @throws Exception
	 */
	public List<SoggettoResponsabileBean> convertiAnagraficheResponsabili(ResponsabileType[] arrayDiAnaResponsabili, boolean removeDuplicate){
		if(removeDuplicate){
			this.containsDuplicate = false; this.duplicateWarning = null;
			RimuoviDuplicatiBusiness remover = new RimuoviDuplicatiBusiness();
			AnagraficaResponsabileDuplicateFeedBack feedBack = remover.rimuoviDuplicatiAnaResponsabili(arrayDiAnaResponsabili);
			if(feedBack.isContainsDuplicate()){
				containsDuplicate = feedBack.isContainsDuplicate();
				duplicateWarning = feedBack.getDuplicateError();
			}return ConvertAnagrafiche.getInstance().convertiAnagraficheResponsabili(feedBack.getArrayOfAnagraficheResponsabili());
		}
		return ConvertAnagrafiche.getInstance().convertiAnagraficheResponsabili(arrayDiAnaResponsabili);	
	}


	public StipulaBean converti(StipulaType stipula) {
		return ConvertStipula.getInstance().converti(stipula);
	}
}
