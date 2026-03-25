package it.avlp.simog.massload.caricamento;

import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.accordi.SchedaAccordo;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.CondizioneAggBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.beans.aggiudicazione.RequisitiBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.aggiudicazione.TipoFinanziamentoBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.avanzamento.SchedaAvanzamento;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.collaudo.SchedaCollaudo;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.conclusione.SchedaConclusione;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.inizio.PosizioneAggiudicatarioBean;
import it.avlp.simog.beans.inizio.SchedaInizioLavori;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.beans.r129.SchedaR129;
import it.avlp.simog.beans.sospensioni.SchedaSospensione;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.beans.stipula.SchedaStipula;
import it.avlp.simog.beans.stipula.StipulaBean;
import it.avlp.simog.beans.subappalti.SchedaSubAppalti;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.beans.variante.EventiMotiviVariantiBean;
import it.avlp.simog.beans.variante.SchedaVariante;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.common.action.CupLottoAggAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriCup;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.flusso.bean.CrossedFields;
import it.avlp.simog.massload.util.conversion.ConvertXMLtoBeanBusiness;
import it.avlp.simog.massload.xmlbeans.AccordoBonarioType;
import it.avlp.simog.massload.xmlbeans.AdesioneType;
import it.avlp.simog.massload.xmlbeans.AggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.AvanzamentoType;
import it.avlp.simog.massload.xmlbeans.CUPLOTTOType;
import it.avlp.simog.massload.xmlbeans.ConclusioneType;
import it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.DatiCUPType;
import it.avlp.simog.massload.xmlbeans.DatiCollaudoType;
import it.avlp.simog.massload.xmlbeans.DatiInizioType;
import it.avlp.simog.massload.xmlbeans.DatiStipulaType;
import it.avlp.simog.massload.xmlbeans.InizioType;
import it.avlp.simog.massload.xmlbeans.RitardoType;
import it.avlp.simog.massload.xmlbeans.SchedaEsclusoType;
import it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType;
import it.avlp.simog.massload.xmlbeans.SospensioneType;
import it.avlp.simog.massload.xmlbeans.SubappaltoType;
import it.avlp.simog.massload.xmlbeans.VarianteType;
import it.avlp.simog.massload.xmlbeans.VariantiType;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <strong>ATTENZIONE !</strong> </p>
 * Al fine del funzionamento delle funzioni offerte da questa classe il tipo xml
 * DatiAggiudicazioneType deve contenere solamente UNA scheda completa [per ottenere tale oggetto controllare "MassloaderCleaner" -> modificaCardinalita(..)]</p>
 * 
 * Siccome i bean sono wrapper (contengono diversi oggetti) e spesso gli stessi oggetti vengono usati
 * in altre schede sarebbe opportuno, anche se facoltativo utilizzare tali oggetti per il settaggio
 * delle altre schede invece di rieffettuare la conversione.</p>
 * 
 * La conversione degli oggetti di tipo lista (multipli) possono restituire warning indicanti la posizione
 * del primo duplicato.</p>
 * 
 * Ad ogni invocazione di un metodo di costruzione e' possibile controllare e recuperare la lista di
 * dei warning da una variabile pubblica di classe, attenzione ad ogni invocazione di metodo tali varibili
 * vengono "resettate".</p>
 * 
 * @author vletizia
 *
 */
public class SchedeConstructor {

	private ConvertXMLtoBeanBusiness converter;
	public List<ValidationBean> listOfDuplicateWarning;
	public boolean containsDuplicate; 
	
	public SchedeConstructor(ConvertXMLtoBeanBusiness converter) {
		this.converter = converter;
	}

	

	/**
	 * Costruzione e valorizzazione di un bean scheda_a.
	 * 
	 * @param datiAggiudicazione
	 * @return
	 */
	public Scheda_A constructSchedaA(DatiAggiudicazioneType datiAggiudicazione, InfoComuniBean infoComuni, 
									 AggiudicazioneBean aggiudicazione, List<AggiudicatarioBean> aggiudicatari, 
									 CrossedFields crossedFields, InfoGaraBean infoGara) throws UnexpectedException, ClassNotFoundException, Exception{ 
		
		this.containsDuplicate = false;
		this.listOfDuplicateWarning = null;
		Scheda_A schedaA = new Scheda_A();
		List<ValidationBean> listOfDuplicateWarnings = new ArrayList<ValidationBean>();
		
		// PP Imposto i dati gara
		schedaA.setInfoGara(infoGara);
		
		// se sono settati questi due bean in ingreso valorizzo con questi
		if(infoComuni != null && aggiudicazione != null && aggiudicatari != null){
			
			// gestione crossedFields
			infoComuni.setIdLotto(crossedFields.getIdLotto());
			//gm questi campi saranno prelevati dall'xml, se non ci sono, li prendo dai crossedFields
			if(infoComuni.getID_ESCLUSIONE()==0)
    			infoComuni.setID_ESCLUSIONE(crossedFields.getID_ESCLUSIONE());
			if(infoComuni.getID_MODO_REAL()==0)
		    	infoComuni.setID_MODO_REAL(crossedFields.getID_MODO_REAL());
			if(infoComuni.getFLAG_ESCLUSO()==null || "".equals(infoComuni.getFLAG_ESCLUSO()))
		    	infoComuni.setFLAG_ESCLUSO(crossedFields.getFLAG_ESCLUSO());
         
			// in aggiunta quando i dati arrivano da xml devo sovrascrivere alcuni campi
			infoComuni.setCfAmministrazione(crossedFields.getCfAmministrazione());
			infoComuni.setDenAmministrazione(crossedFields.getDenAmministrazione());
			infoComuni.setCfStazioneAppaltante(crossedFields.getCfStazioneAppaltante());
			infoComuni.setDenStazioneAppaltante(crossedFields.getDenStazioneAppaltante());
			infoComuni.setCodiceCC(crossedFields.getCodiceCC());
			infoComuni.setDenomCC(crossedFields.getDenomCC());
			
			infoComuni.setTipoContratto(crossedFields.getTipoSettore());
			infoComuni.setFlagEnteSpeciale(crossedFields.getFlagOrdinario());
            //FIXXME: gm per adesso imponiamo che i dati di pubblicazione siano inseriti
			//con l'xml dei dati comuni, e non sovrascritti con quelli della gara pubblicata
			// PP - BANDI : i dati di pubblicita sono sempre quelli della gara pubblicata, se esiste
			
			if(crossedFields.getPubblicazione()!=null){
				PubblicazioneBean puBean = crossedFields.getPubblicazione();
				infoComuni.setPubblicazione(puBean);
			}
			
			schedaA.setInfoComuni(infoComuni);
			schedaA.setAggiudicazione(aggiudicazione);
			schedaA.setAggiudicatari(aggiudicatari);
			
			// PP qui ho gli infoComuni settati, salvo il tipo settore
			converter.tipoSettore = schedaA.getInfoComuni().getFlagEnteSpeciale();
			
			// ho un solo elemento
			if(datiAggiudicazione.getSchedaCompletaArray() != null && datiAggiudicazione.getSchedaCompletaArray().length > 0){
				if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");
				
				AggiudicazioneType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getAggiudicazione();
				if(aggiudicazioneLocale != null){
					
					AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto());
					
					if(!SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())
	                  && SimogProperties.getInstance().isCUPAttivo()){
					   // ignoro il cup vecchia struttura e carico la lista
					   aggiudicazioneBean.setCup("");
					   
					   if(aggiudicazioneLocale.isSetCUPLOTTO()){
					      schedaA.setElencoCup(convertiListaCup(aggiudicazioneLocale.getCUPLOTTO()));					      
					   }
					   
					   if(aggiudicazioneLocale.getAppalto().isSetFLAGCUP())
					      schedaA.setFlagCUP(aggiudicazioneLocale.getAppalto().getFLAGCUP().toString());
					}
					
					List<CondizioneAggBean> listOfCondizioni = converter.convertiCondizioni(aggiudicazioneLocale.getCondizioniArray(),true);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
					
					List<TipoFinanziamentoBean> listOfFinanziamenti = converter.convertiFinanziamenti(aggiudicazioneLocale.getFinanziamentiArray(),true);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
					
					List<RequisitiBean> listOfRequisiti = converter.convertiRequisiti(aggiudicazioneLocale.getRequisitiArray(),true);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
					
					List<TipoAppaltoAggBean> listOfForniture = converter.convertiF(aggiudicazioneLocale.getTipiAppaltoFornArray(), true);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
					
					List<TipoAppaltoAggBean> listOfLavori = converter.convertiL(aggiudicazioneLocale.getTipiAppaltoLavArray(),true);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
					
					Map<String, List<ResponsabileBean>> responsabili = converter.convertiIncaricatiAggiudicazione(aggiudicazioneLocale.getIncaricatiArray(), true, PSBD.SEZIONE_RA);
					
					List<ResponsabileBean> listOfResponsabili = responsabili.get(PSBD.SEZIONE_RA);
					List<ResponsabileBean> listOfPrestazioni = responsabili.get(PSBD.SEZIONE_PA);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);

					List<AggiudicatarioBean> listOfAggiudicatari = converter.convertiAggiudicatari(aggiudicazioneLocale.getAggiudicatariArray(), true);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
					
					List<DittaAusiliariaBean> listOfDitteAusiliarie = converter.convertiDitteAusiliarie(aggiudicazioneLocale.getDitteAusiliarieArray(), true);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
					
					// assegno le ditte ausiliarie agli aggiudicatari
					associaAusiliarie(listOfDitteAusiliarie, listOfAggiudicatari);
					
					schedaA.setAggiudicazione(aggiudicazioneBean);
					schedaA.setCondizioni(listOfCondizioni);
					schedaA.setFinanziamenti(listOfFinanziamenti);
					schedaA.setRequisiti(listOfRequisiti);
					schedaA.setTipoFS(listOfForniture);
					schedaA.setTipoLavoro(listOfLavori);
					schedaA.setAggiudicatari(listOfAggiudicatari);
					schedaA.setResponsabili(listOfResponsabili);
					schedaA.setPrestazioni(listOfPrestazioni);
				}
			}
			
			
			return schedaA;
		}
		// se solo info comuni presente nel db
		if(infoComuni != null && aggiudicazione == null && aggiudicatari == null){
			
			// gestione crossedFields
			infoComuni.setIdLotto(crossedFields.getIdLotto());
			//gm questi campi saranno prelevati dall'xml, se non ci sono, li prendo dai crossedFields
			if(infoComuni.getID_ESCLUSIONE()==0)
    			infoComuni.setID_ESCLUSIONE(crossedFields.getID_ESCLUSIONE());
			if(infoComuni.getID_MODO_REAL()==0)
		    	infoComuni.setID_MODO_REAL(crossedFields.getID_MODO_REAL());
			if(infoComuni.getFLAG_ESCLUSO()==null || "".equals(infoComuni.getFLAG_ESCLUSO()))
		    	infoComuni.setFLAG_ESCLUSO(crossedFields.getFLAG_ESCLUSO());
			
         // in aggiunta quando i dati arrivano da xml devo sovrascrivere alcuni campi
         infoComuni.setCfAmministrazione(crossedFields.getCfAmministrazione());
         infoComuni.setDenAmministrazione(crossedFields.getDenAmministrazione());
         infoComuni.setCfStazioneAppaltante(crossedFields.getCfStazioneAppaltante());
         infoComuni.setDenStazioneAppaltante(crossedFields.getDenStazioneAppaltante());
         infoComuni.setCodiceCC(crossedFields.getCodiceCC());
         infoComuni.setDenomCC(crossedFields.getDenomCC());
         
         infoComuni.setTipoContratto(crossedFields.getTipoSettore());
		 infoComuni.setFlagEnteSpeciale(crossedFields.getFlagOrdinario());
         
			schedaA.setInfoComuni(infoComuni);
		}
		// settaggio dei dati comuni..
		if(datiAggiudicazione.getDatiComuni() != null && infoComuni == null){
			InfoComuniBean infoComuniLocale = converter.converti(datiAggiudicazione.getDatiComuni());
			PubblicazioneBean pubblicazione = converter.converti(datiAggiudicazione.getPubblicazione());
			infoComuniLocale.setPubblicazione(pubblicazione);
			
			 //FIXXME: gm per adesso imponiamo che i dati di pubblicazione siano inseriti
			//con l'xml dei dati comuni, e non sovrascritti con quelli della gara pubblicata
			// PP - BANDI : i dati di pubblicita sono sempre quelli della gara pubblicata, se esiste
			
			if(crossedFields.getPubblicazione()!=null){
				PubblicazioneBean puBean = crossedFields.getPubblicazione();
				infoComuniLocale.setPubblicazione(puBean);
			}
			
			// gestione crossedFields
			infoComuniLocale.setIdLotto(crossedFields.getIdLotto());
			//gm questi campi saranno prelevati dall'xml, se non ci sono, li prendo dai crossedFields
			if(infoComuniLocale.getID_ESCLUSIONE()==0)
    			infoComuniLocale.setID_ESCLUSIONE(crossedFields.getID_ESCLUSIONE());
			if(infoComuniLocale.getID_MODO_REAL()==0)
		    	infoComuniLocale.setID_MODO_REAL(crossedFields.getID_MODO_REAL());
			if(infoComuniLocale.getFLAG_ESCLUSO()==null || "".equals(infoComuniLocale.getFLAG_ESCLUSO()))
		    	infoComuniLocale.setFLAG_ESCLUSO(crossedFields.getFLAG_ESCLUSO());
			
			
			// in aggiunta quando i dati arrivano da xml devo sovrascrivere alcuni campi
			infoComuniLocale.setCfAmministrazione(crossedFields.getCfAmministrazione());
			infoComuniLocale.setDenAmministrazione(crossedFields.getDenAmministrazione());
			infoComuniLocale.setCfStazioneAppaltante(crossedFields.getCfStazioneAppaltante());
			infoComuniLocale.setDenStazioneAppaltante(crossedFields.getDenStazioneAppaltante());
			infoComuniLocale.setCodiceCC(crossedFields.getCodiceCC());
			infoComuniLocale.setDenomCC(crossedFields.getDenomCC());
			
			infoComuniLocale.setTipoContratto(crossedFields.getTipoSettore());
			infoComuniLocale.setFlagEnteSpeciale(crossedFields.getFlagOrdinario());
			
			schedaA.setInfoComuni(infoComuniLocale);
		}
		
		// PP qui ho gli infoComuni settati, salvo il tipo settore
		converter.tipoSettore = schedaA.getInfoComuni().getFlagEnteSpeciale();
		
		// ho un solo elemento
		if(datiAggiudicazione.getSchedaCompletaArray() != null && datiAggiudicazione.getSchedaCompletaArray().length > 0){
			if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");
			
			AggiudicazioneType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getAggiudicazione();
			if(aggiudicazioneLocale != null){
				
				AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto());
				
            if(SimogFlags.is3031_RFWEBGL02Active()
                  && !SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())
                  && SimogProperties.getInstance().isCUPAttivo()){
               // ignoro il cup vecchia struttura e carico la lista
               aggiudicazioneBean.setCup("");
               
               if(aggiudicazioneLocale.isSetCUPLOTTO()){
                  schedaA.setElencoCup(convertiListaCup(aggiudicazioneLocale.getCUPLOTTO()));                     
               }

               if(aggiudicazioneLocale.getAppalto().isSetFLAGCUP())
                  schedaA.setFlagCUP(aggiudicazioneLocale.getAppalto().getFLAGCUP().toString());
            }
				
				List<CondizioneAggBean> listOfCondizioni = converter.convertiCondizioni(aggiudicazioneLocale.getCondizioniArray(),true);
				if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
				
				List<TipoFinanziamentoBean> listOfFinanziamenti = converter.convertiFinanziamenti(aggiudicazioneLocale.getFinanziamentiArray(),true);
				if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
				
				List<RequisitiBean> listOfRequisiti = converter.convertiRequisiti(aggiudicazioneLocale.getRequisitiArray(),true);
				if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
				
				List<TipoAppaltoAggBean> listOfForniture = converter.convertiF(aggiudicazioneLocale.getTipiAppaltoFornArray(), true);
				if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
				
				List<TipoAppaltoAggBean> listOfLavori = converter.convertiL(aggiudicazioneLocale.getTipiAppaltoLavArray(),true);
				if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
				
				Map<String, List<ResponsabileBean>> responsabili = converter.convertiIncaricatiAggiudicazione(aggiudicazioneLocale.getIncaricatiArray(), true, PSBD.SEZIONE_RA);
				
				List<ResponsabileBean> listOfResponsabili = responsabili.get(PSBD.SEZIONE_RA);
				List<ResponsabileBean> listOfPrestazioni = responsabili.get(PSBD.SEZIONE_PA);
				if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);

				List<AggiudicatarioBean> listOfAggiudicatari = converter.convertiAggiudicatari(aggiudicazioneLocale.getAggiudicatariArray(), true);
				if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
	
				List<DittaAusiliariaBean> listOfDitteAusiliarie = converter.convertiDitteAusiliarie(aggiudicazioneLocale.getDitteAusiliarieArray(), true);
				if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
				
                // assegno le ditte ausiliarie agli aggiudicatari
                associaAusiliarie(listOfDitteAusiliarie, listOfAggiudicatari);

				schedaA.setAggiudicazione(aggiudicazioneBean);
				schedaA.setCondizioni(listOfCondizioni);
				schedaA.setFinanziamenti(listOfFinanziamenti);
				schedaA.setRequisiti(listOfRequisiti);
				schedaA.setTipoFS(listOfForniture);
				schedaA.setTipoLavoro(listOfLavori);
				schedaA.setAggiudicatari(listOfAggiudicatari);
				schedaA.setResponsabili(listOfResponsabili);
				schedaA.setPrestazioni(listOfPrestazioni);
			}
			
		}
		if(listOfDuplicateWarnings != null && listOfDuplicateWarnings.size() > 0){
			containsDuplicate = true;
			this.listOfDuplicateWarning = listOfDuplicateWarnings;
		}
		
		return schedaA;
	}
	
	
	/** concersione codici cup
	 * @param cuplotto
	 * @return
	 */
	private List<CupLottoAggExt> convertiListaCup(CUPLOTTOType cuplotto) {
      List<CupLottoAggExt> ret = new ArrayList<CupLottoAggExt>() ;
	   
      if(cuplotto != null){
         for(DatiCUPType elem : cuplotto.getCODICICUPArray()){
            CupLottoAggExt bean = new CupLottoAggExt();
            bean.setCig(cuplotto.getCIG());
            bean.setCup(elem.getCUP());
            
            // PP preimposto a SI per chi non lo manda!
            bean.setOkUtente(Costanti.FLAG_VALORE_SI);
            if (elem.isSetOKUTENTE())
               bean.setOkUtente(elem.getOKUTENTE().toString());
            
            ret.add(bean);
         }
      }
      return ret;
   }



   private List<DittaAusiliariaBean> getDittaOldFashion(AggiudicatarioBean ab) {
		
			DittaAusiliariaBean daus = new DittaAusiliariaBean();
			daus.setCfAusiliaria(ab.getCfAusiliaria());
			
			SoggettoPartecipanteBean spb = new SoggettoPartecipanteBean();
			spb.setCodiceFiscale(daus.getCfAusiliaria());
			daus.setCodiceFiscaleAggiudicatario(ab.getSoggettoPartecipante().getCodiceFiscale());
			daus.setFlagAvvalimento(ab.getFlagAvvalimento());
			daus.setId_statoAggiudicatario(String.valueOf(ab.getIdStato()));
			daus.setSoggettoPartecipante(spb);
			/*gm impongo che lo stato della ditta aux sia lo stesso dell'aggiudicatario,
			  perche lo stato del spb e null quando lo carico dall'xml.
			  daus.setIdStato(Long.parseLong(spb.getId_stato()));
			 */
		   	daus.setIdStato(ab.getIdStato());
				
			List<DittaAusiliariaBean> diList =  new ArrayList<DittaAusiliariaBean>();
			diList.add(daus);
			return diList;
	}



	private Map<String, List<DittaAusiliariaBean>> raggruppaDittePerAggiudicatario(List<DittaAusiliariaBean> listOfDitteAusiliarie) {
		Map<String, List<DittaAusiliariaBean>> dm = new HashMap<String, List<DittaAusiliariaBean>>();
		for(DittaAusiliariaBean ditta: listOfDitteAusiliarie){
			String key = ditta.getCodiceFiscaleAggiudicatario() + ditta.getId_statoAggiudicatario();
			if(!dm.containsKey(key))
				dm.put(key, new ArrayList<DittaAusiliariaBean>());
			dm.get(key).add(ditta);
		}
		return dm;
	}

	/**
	 * Costruzione e valorizzazione di un bean scheda_a.
	 * 
	 * @param datiAggiudicazione
	 * @return
	 */
	public Scheda_A constructSchedaAdesione(DatiAggiudicazioneType datiAggiudicazione, InfoComuniBean infoComuni, 
									 AggiudicazioneBean aggiudicazione, List<AggiudicatarioBean> aggiudicatari, 
									 CrossedFields crossedFields, InfoGaraBean infoGara) throws UnexpectedException, ClassNotFoundException, Exception{ 
		
		this.containsDuplicate = false;
		this.listOfDuplicateWarning = null;
		Scheda_A schedaA = new Scheda_A();
		List<ValidationBean> listOfDuplicateWarnings = new ArrayList<ValidationBean>();
		
		// PP Imposto i dati gara
		schedaA.setInfoGara(infoGara);
		
		// se sono settati questi due bean in ingreso valorizzo con questi
		if(infoComuni != null && aggiudicazione != null && aggiudicatari != null){
			
			// gestione crossedFields
			infoComuni.setIdLotto(crossedFields.getIdLotto());
			//gm questi campi saranno prelevati dall'xml, se non ci sono, li prendo dai crossedFields
			if(infoComuni.getID_ESCLUSIONE()==0)
    			infoComuni.setID_ESCLUSIONE(crossedFields.getID_ESCLUSIONE());
			if(infoComuni.getID_MODO_REAL()==0)
		    	infoComuni.setID_MODO_REAL(crossedFields.getID_MODO_REAL());
			if(infoComuni.getFLAG_ESCLUSO()==null || "".equals(infoComuni.getFLAG_ESCLUSO()))
		    	infoComuni.setFLAG_ESCLUSO(crossedFields.getFLAG_ESCLUSO());
			
         // in aggiunta quando i dati arrivano da xml devo sovrascrivere alcuni campi
         infoComuni.setCfAmministrazione(crossedFields.getCfAmministrazione());
         infoComuni.setDenAmministrazione(crossedFields.getDenAmministrazione());
         infoComuni.setCfStazioneAppaltante(crossedFields.getCfStazioneAppaltante());
         infoComuni.setDenStazioneAppaltante(crossedFields.getDenStazioneAppaltante());
         infoComuni.setCodiceCC(crossedFields.getCodiceCC());
         infoComuni.setDenomCC(crossedFields.getDenomCC());
         
			 //FIXXME: gm per adesso imponiamo che i dati di pubblicazione siano inseriti
			//con l'xml dei dati comuni, e non sovrascritti con quelli della gara pubblicata
			// PP - BANDI : i dati di pubblicita sono sempre quelli della gara pubblicata, se esiste
			
			if(crossedFields.getPubblicazione()!=null){
				PubblicazioneBean puBean = crossedFields.getPubblicazione();
				infoComuni.setPubblicazione(puBean);
			}
			
			schedaA.setInfoComuni(infoComuni);
			schedaA.setAggiudicazione(aggiudicazione);
			schedaA.setAggiudicatari(aggiudicatari);
			
			//gm spostato qui, dopo schedaA.setInfoComuni(infoComuni) perche 
			//altrimenti da NullPointerException
			// PP qui ho gli infoComuni settati, salvo il tipo settore
			converter.tipoSettore = schedaA.getInfoComuni().getFlagEnteSpeciale();
			
			// ho un solo elemento
			if(datiAggiudicazione.getSchedaCompletaArray() != null && datiAggiudicazione.getSchedaCompletaArray().length > 0){
				if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");
				
				AdesioneType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getAdesione();
				if(aggiudicazioneLocale != null){
					
					AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto());
					
					
					
					List<TipoFinanziamentoBean> listOfFinanziamenti = converter.convertiFinanziamenti(aggiudicazioneLocale.getFinanziamentiArray(),true);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
					
					
					
					Map<String, List<ResponsabileBean>> responsabili = converter.convertiIncaricatiAggiudicazione(aggiudicazioneLocale.getIncaricatiArray(), true, PSBD.SEZIONE_RQ);
					
					List<ResponsabileBean> listOfResponsabili = responsabili.get(PSBD.SEZIONE_RQ);
					List<ResponsabileBean> listOfPrestazioni = responsabili.get(PSBD.SEZIONE_PA);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);

					List<AggiudicatarioBean> listOfAggiudicatari = converter.convertiAggiudicatari(aggiudicazioneLocale.getAggiudicatariArray(), true);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
		
					List<DittaAusiliariaBean> listOfDitteAusiliarie = converter.convertiDitteAusiliarie(aggiudicazioneLocale.getDitteAusiliarieArray(), true);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
					
                    // assegno le ditte ausiliarie agli aggiudicatari
                    associaAusiliarie(listOfDitteAusiliarie, listOfAggiudicatari);
					
					schedaA.setAggiudicazione(aggiudicazioneBean);
					
					schedaA.setFinanziamenti(listOfFinanziamenti);
				
					// PP forzo gli aggiudicatari dell AQ schedaA.setAggiudicatari(listOfAggiudicatari);
					schedaA.setAggiudicatari(aggiudicatari);
					
					schedaA.setResponsabili(listOfResponsabili);
					schedaA.setPrestazioni(listOfPrestazioni);
				}
			}
			
			return schedaA;
		}
		// se solo info comuni presente nel db
		if(infoComuni != null && aggiudicazione == null ){ // PP no buono per adesione && aggiudicatari == null){
			
			// gestione crossedFields
			infoComuni.setIdLotto(crossedFields.getIdLotto());
			//gm questi campi saranno prelevati dall'xml, se non ci sono, li prendo dai crossedFields
			if(infoComuni.getID_ESCLUSIONE()==0)
    			infoComuni.setID_ESCLUSIONE(crossedFields.getID_ESCLUSIONE());
			if(infoComuni.getID_MODO_REAL()==0)
		    	infoComuni.setID_MODO_REAL(crossedFields.getID_MODO_REAL());
			if(infoComuni.getFLAG_ESCLUSO()==null || "".equals(infoComuni.getFLAG_ESCLUSO()))
		    	infoComuni.setFLAG_ESCLUSO(crossedFields.getFLAG_ESCLUSO());	
			
         // in aggiunta quando i dati arrivano da xml devo sovrascrivere alcuni campi
         infoComuni.setCfAmministrazione(crossedFields.getCfAmministrazione());
         infoComuni.setDenAmministrazione(crossedFields.getDenAmministrazione());
         infoComuni.setCfStazioneAppaltante(crossedFields.getCfStazioneAppaltante());
         infoComuni.setDenStazioneAppaltante(crossedFields.getDenStazioneAppaltante());
         infoComuni.setCodiceCC(crossedFields.getCodiceCC());
         infoComuni.setDenomCC(crossedFields.getDenomCC());
         
			schedaA.setInfoComuni(infoComuni);
		}
		// settaggio dei dati comuni..
		if(datiAggiudicazione.getDatiComuni() != null && infoComuni == null){
			InfoComuniBean infoComuniLocale = converter.converti(datiAggiudicazione.getDatiComuni());
			PubblicazioneBean pubblicazione = converter.converti(datiAggiudicazione.getPubblicazione());
			infoComuniLocale.setPubblicazione(pubblicazione);
			
			// PP - BANDI : i dati di pubblicita sono sempre quelli della gara pubblicata, se esiste
			 //FIXXME: gm per adesso imponiamo che i dati di pubblicazione siano inseriti
			//con l'xml dei dati comuni, e non sovrascritti con quelli della gara pubblicata
			
			if(crossedFields.getPubblicazione()!=null){
				PubblicazioneBean puBean = crossedFields.getPubblicazione();
				infoComuniLocale.setPubblicazione(puBean);
			}
			
			
			// gestione crossedFields
			infoComuniLocale.setIdLotto(crossedFields.getIdLotto());
			//gm questi campi saranno prelevati dall'xml, se non ci sono, li prendo dai crossedFields
			if(infoComuniLocale.getID_ESCLUSIONE()==0)
				infoComuniLocale.setID_ESCLUSIONE(crossedFields.getID_ESCLUSIONE());
			if(infoComuniLocale.getID_MODO_REAL()==0)
				infoComuniLocale.setID_MODO_REAL(crossedFields.getID_MODO_REAL());
			if(infoComuniLocale.getFLAG_ESCLUSO()==null || "".equals(infoComuniLocale.getFLAG_ESCLUSO()))
				infoComuniLocale.setFLAG_ESCLUSO(crossedFields.getFLAG_ESCLUSO());
			
			
			// in aggiunta quando i dati arrivano da xml devo sovrascrivere alcuni campi
			infoComuniLocale.setCfAmministrazione(crossedFields.getCfAmministrazione());
			infoComuniLocale.setDenAmministrazione(crossedFields.getDenAmministrazione());
			infoComuniLocale.setCfStazioneAppaltante(crossedFields.getCfStazioneAppaltante());
			infoComuniLocale.setDenStazioneAppaltante(crossedFields.getDenStazioneAppaltante());
			infoComuniLocale.setCodiceCC(crossedFields.getCodiceCC());
			infoComuniLocale.setDenomCC(crossedFields.getDenomCC());
			
			schedaA.setInfoComuni(infoComuniLocale);
		}
		
		// PP qui ho gli infoComuni settati, salvo il tipo settore
		converter.tipoSettore = schedaA.getInfoComuni().getFlagEnteSpeciale();
		
		// ho un solo elemento
		if(datiAggiudicazione.getSchedaCompletaArray() != null && datiAggiudicazione.getSchedaCompletaArray().length > 0){
			if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");
			
			
			AdesioneType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getAdesione();
			if(aggiudicazioneLocale != null){
				
				AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto());
				
				
				
				List<TipoFinanziamentoBean> listOfFinanziamenti = converter.convertiFinanziamenti(aggiudicazioneLocale.getFinanziamentiArray(),true);
				if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
				
			
				
				
				
				Map<String, List<ResponsabileBean>> responsabili = converter.convertiIncaricatiAggiudicazione(aggiudicazioneLocale.getIncaricatiArray(), true, PSBD.SEZIONE_RQ);
				
				List<ResponsabileBean> listOfResponsabili = responsabili.get(PSBD.SEZIONE_RQ);
				List<ResponsabileBean> listOfPrestazioni = responsabili.get(PSBD.SEZIONE_PA);
				if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);

				// PP non serve List<AggiudicatarioBean> listOfAggiudicatari = converter.convertiAggiudicatari(aggiudicazioneLocale.getAggiudicatariArray(), true);
				// if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
	
				
				schedaA.setAggiudicazione(aggiudicazioneBean);
				
				schedaA.setFinanziamenti(listOfFinanziamenti);
				
            // PP forzo gli aggiudicatari dell AQ schedaA.setAggiudicatari(listOfAggiudicatari);
            schedaA.setAggiudicatari(aggiudicatari);
            
            schedaA.setResponsabili(listOfResponsabili);
				schedaA.setPrestazioni(listOfPrestazioni);
			}
			
		}
		if(listOfDuplicateWarnings != null && listOfDuplicateWarnings.size() > 0){
			containsDuplicate = true;
			this.listOfDuplicateWarning = listOfDuplicateWarnings;
		}
		
		return schedaA;
	}
	
	/**
	 * Costruzione e valorizzazione di un bean scheda_a.
	 * 
	 * @param datiAggiudicazione
	 * @return
	 */
	public Scheda_A constructSchedaSottosoglia(DatiAggiudicazioneType datiAggiudicazione, InfoComuniBean infoComuni, 
									 AggiudicazioneBean aggiudicazione, List<AggiudicatarioBean> aggiudicatari, 
									 CrossedFields crossedFields, InfoGaraBean infoGara) throws UnexpectedException, ClassNotFoundException, Exception{ 
		
		this.containsDuplicate = false;
		this.listOfDuplicateWarning = null;
		Scheda_A schedaA = new Scheda_A();
		List<ValidationBean> listOfDuplicateWarnings = new ArrayList<ValidationBean>();
		
		// PP Imposto i dati gara
		schedaA.setInfoGara(infoGara);
		
		// se sono settati questi due bean in ingreso valorizzo con questi
		if(infoComuni != null && aggiudicazione != null && aggiudicatari != null){
			
			// gestione crossedFields
			infoComuni.setIdLotto(crossedFields.getIdLotto());
			//gm questi campi saranno prelevati dall'xml, se non ci sono, li prendo dai crossedFields
			if(infoComuni.getID_ESCLUSIONE()==0)
    			infoComuni.setID_ESCLUSIONE(crossedFields.getID_ESCLUSIONE());
			if(infoComuni.getID_MODO_REAL()==0)
		    	infoComuni.setID_MODO_REAL(crossedFields.getID_MODO_REAL());
			if(infoComuni.getFLAG_ESCLUSO()==null || "".equals(infoComuni.getFLAG_ESCLUSO()))
		    	infoComuni.setFLAG_ESCLUSO(crossedFields.getFLAG_ESCLUSO());
			
         // in aggiunta quando i dati arrivano da xml devo sovrascrivere alcuni campi
         infoComuni.setCfAmministrazione(crossedFields.getCfAmministrazione());
         infoComuni.setDenAmministrazione(crossedFields.getDenAmministrazione());
         infoComuni.setCfStazioneAppaltante(crossedFields.getCfStazioneAppaltante());
         infoComuni.setDenStazioneAppaltante(crossedFields.getDenStazioneAppaltante());
         infoComuni.setCodiceCC(crossedFields.getCodiceCC());
         infoComuni.setDenomCC(crossedFields.getDenomCC());
         
			// PP qui ho gli infoComuni settati, salvo il tipo settore
			// converter.tipoSettore = schedaA.getInfoComuni().getFlagEnteSpeciale();

			// PP - BANDI : i dati di pubblicita sono sempre quelli della gara pubblicata, se esiste
			 //FIXXME: gm per adesso imponiamo che i dati di pubblicazione siano inseriti
			//con l'xml dei dati comuni, e non sovrascritti con quelli della gara pubblicata
			if(crossedFields.getPubblicazione()!=null){
				PubblicazioneBean puBean = crossedFields.getPubblicazione();
				infoComuni.setPubblicazione(puBean);
			}

			schedaA.setInfoComuni(infoComuni);
			converter.tipoSettore = schedaA.getInfoComuni().getFlagEnteSpeciale();
			schedaA.setAggiudicazione(aggiudicazione);
			schedaA.setAggiudicatari(aggiudicatari);
			// ho un solo elemento
			if(datiAggiudicazione.getSchedaCompletaArray() != null && datiAggiudicazione.getSchedaCompletaArray().length > 0){
				if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");
				
				SchedaSottosogliaType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getSottosoglia();
				if(aggiudicazioneLocale != null){
					
					AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto(), TipoAggiudicazione.S);
					
               if(SimogFlags.is3031_RFWEBGL02Active()
                     && !SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())
                     && SimogProperties.getInstance().isCUPAttivo()){
	                  // ignoro il cup vecchia struttura e carico la lista
	                  aggiudicazioneBean.setCup("");
	                  
	                  if(aggiudicazioneLocale.isSetCUPLOTTO()){
	                     schedaA.setElencoCup(convertiListaCup(aggiudicazioneLocale.getCUPLOTTO()));                     
	                  }
	                  
	                  if(aggiudicazioneLocale.getAppalto().isSetFLAGCUP())
	                     schedaA.setFlagCUP(aggiudicazioneLocale.getAppalto().getFLAGCUP().toString());
	            }

					List<CondizioneAggBean> listOfCondizioni = converter.convertiCondizioni(aggiudicazioneLocale.getCondizioniArray(),true);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
					
					
					
					Map<String, List<ResponsabileBean>> responsabili = converter.convertiIncaricatiAggiudicazione(aggiudicazioneLocale.getIncaricatiArray(), true, PSBD.SEZIONE_RS);
					
					List<ResponsabileBean> listOfResponsabili = responsabili.get(PSBD.SEZIONE_RS);
					List<ResponsabileBean> listOfPrestazioni = responsabili.get(PSBD.SEZIONE_PA);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);

					List<AggiudicatarioBean> listOfAggiudicatari = converter.convertiAggiudicatari(aggiudicazioneLocale.getAggiudicatariArray(), true);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
		
					
					
					schedaA.setAggiudicazione(aggiudicazioneBean);
					schedaA.setCondizioni(listOfCondizioni);
					
					schedaA.setAggiudicatari(listOfAggiudicatari);
					schedaA.setResponsabili(listOfResponsabili);
					schedaA.setPrestazioni(listOfPrestazioni);
				}
			}
			
			return schedaA;
		}
		// se solo info comuni presente nel db
		if(infoComuni != null && aggiudicazione == null && aggiudicatari == null){
			
			// gestione crossedFields
			infoComuni.setIdLotto(crossedFields.getIdLotto());
			//gm questi campi saranno prelevati dall'xml, se non ci sono, li prendo dai crossedFields
			if(infoComuni.getID_ESCLUSIONE()==0)
    			infoComuni.setID_ESCLUSIONE(crossedFields.getID_ESCLUSIONE());
			if(infoComuni.getID_MODO_REAL()==0)
		    	infoComuni.setID_MODO_REAL(crossedFields.getID_MODO_REAL());
			if(infoComuni.getFLAG_ESCLUSO()==null || "".equals(infoComuni.getFLAG_ESCLUSO()))
		    	infoComuni.setFLAG_ESCLUSO(crossedFields.getFLAG_ESCLUSO());
			
         // in aggiunta quando i dati arrivano da xml devo sovrascrivere alcuni campi
         infoComuni.setCfAmministrazione(crossedFields.getCfAmministrazione());
         infoComuni.setDenAmministrazione(crossedFields.getDenAmministrazione());
         infoComuni.setCfStazioneAppaltante(crossedFields.getCfStazioneAppaltante());
         infoComuni.setDenStazioneAppaltante(crossedFields.getDenStazioneAppaltante());
         infoComuni.setCodiceCC(crossedFields.getCodiceCC());
         infoComuni.setDenomCC(crossedFields.getDenomCC());
         
			schedaA.setInfoComuni(infoComuni);
			converter.tipoSettore = schedaA.getInfoComuni().getFlagEnteSpeciale();
		}
		// settaggio dei dati comuni..
		if(datiAggiudicazione.getDatiComuni() != null && infoComuni == null){
			InfoComuniBean infoComuniLocale = converter.converti(datiAggiudicazione.getDatiComuni());
			PubblicazioneBean pubblicazione = converter.converti(datiAggiudicazione.getPubblicazione());
			infoComuniLocale.setPubblicazione(pubblicazione);
			
			// PP - BANDI : i dati di pubblicita sono sempre quelli della gara pubblicata, se esiste
			 //FIXXME: gm per adesso imponiamo che i dati di pubblicazione siano inseriti
			//con l'xml dei dati comuni, e non sovrascritti con quelli della gara pubblicata
			
			if(crossedFields.getPubblicazione()!=null){
				PubblicazioneBean puBean = crossedFields.getPubblicazione();
				infoComuniLocale.setPubblicazione(puBean);
			}
			
			// gestione crossedFields
			infoComuniLocale.setIdLotto(crossedFields.getIdLotto());
			//gm questi campi saranno prelevati dall'xml, se non ci sono, li prendo dai crossedFields
			if(infoComuniLocale.getID_ESCLUSIONE()==0)
    			infoComuniLocale.setID_ESCLUSIONE(crossedFields.getID_ESCLUSIONE());
			if(infoComuniLocale.getID_MODO_REAL()==0)
		    	infoComuniLocale.setID_MODO_REAL(crossedFields.getID_MODO_REAL());
			if(infoComuniLocale.getFLAG_ESCLUSO()==null || "".equals(infoComuniLocale.getFLAG_ESCLUSO()))
		    	infoComuniLocale.setFLAG_ESCLUSO(crossedFields.getFLAG_ESCLUSO());
			

			// in aggiunta quando i dati arrivano da xml devo sovrascrivere alcuni campi
			infoComuniLocale.setCfAmministrazione(crossedFields.getCfAmministrazione());
			infoComuniLocale.setDenAmministrazione(crossedFields.getDenAmministrazione());
			infoComuniLocale.setCfStazioneAppaltante(crossedFields.getCfStazioneAppaltante());
			infoComuniLocale.setDenStazioneAppaltante(crossedFields.getDenStazioneAppaltante());
			infoComuniLocale.setCodiceCC(crossedFields.getCodiceCC());
			infoComuniLocale.setDenomCC(crossedFields.getDenomCC());
			
			schedaA.setInfoComuni(infoComuniLocale);
			converter.tipoSettore = schedaA.getInfoComuni().getFlagEnteSpeciale();
		}
		
		// PP qui ho gli infoComuni settati, salvo il tipo settore
		
		
		// ho un solo elemento
		if(datiAggiudicazione.getSchedaCompletaArray() != null && datiAggiudicazione.getSchedaCompletaArray().length > 0){
			if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");
			
			SchedaSottosogliaType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getSottosoglia();
			if(aggiudicazioneLocale != null){
				
				AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto(), TipoAggiudicazione.S);
				
            if(SimogFlags.is3031_RFWEBGL02Active()
                  && !SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())
                  && SimogProperties.getInstance().isCUPAttivo()){
               // ignoro il cup vecchia struttura e carico la lista
               aggiudicazioneBean.setCup("");
               
               if(aggiudicazioneLocale.isSetCUPLOTTO()){
                  schedaA.setElencoCup(convertiListaCup(aggiudicazioneLocale.getCUPLOTTO()));                     
               }

               if(aggiudicazioneLocale.getAppalto().isSetFLAGCUP())
                  schedaA.setFlagCUP(aggiudicazioneLocale.getAppalto().getFLAGCUP().toString());               
            }

				
				List<CondizioneAggBean> listOfCondizioni = converter.convertiCondizioni(aggiudicazioneLocale.getCondizioniArray(),true);
				if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
				
				Map<String, List<ResponsabileBean>> responsabili = converter.convertiIncaricatiAggiudicazione(aggiudicazioneLocale.getIncaricatiArray(), true, PSBD.SEZIONE_RS);
				
				List<ResponsabileBean> listOfResponsabili = responsabili.get(PSBD.SEZIONE_RS);
				List<ResponsabileBean> listOfPrestazioni = responsabili.get(PSBD.SEZIONE_PA);
				if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);

				List<AggiudicatarioBean> listOfAggiudicatari = converter.convertiAggiudicatari(aggiudicazioneLocale.getAggiudicatariArray(), true);
				if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
	
				
				schedaA.setAggiudicazione(aggiudicazioneBean);
				
				schedaA.setCondizioni(listOfCondizioni);
				schedaA.setAggiudicatari(listOfAggiudicatari);
				schedaA.setResponsabili(listOfResponsabili);
				schedaA.setPrestazioni(listOfPrestazioni);
			}
			
		}
		if(listOfDuplicateWarnings != null && listOfDuplicateWarnings.size() > 0){
			containsDuplicate = true;
			this.listOfDuplicateWarning = listOfDuplicateWarnings;
		}
		
		return schedaA;
	}
	
	/**
	 * Costruzione e valorizzazione di un bean scheda_a.
	 * 
	 * @param datiAggiudicazione
	 * @return
	 */
	public Scheda_A constructSchedaEscluso(DatiAggiudicazioneType datiAggiudicazione, InfoComuniBean infoComuni, 
									 AggiudicazioneBean aggiudicazione, List<AggiudicatarioBean> aggiudicatari, 
									 CrossedFields crossedFields, InfoGaraBean infoGara) throws UnexpectedException, ClassNotFoundException, Exception{ 
		
		this.containsDuplicate = false;
		this.listOfDuplicateWarning = null;
		Scheda_A schedaA = new Scheda_A();
		List<ValidationBean> listOfDuplicateWarnings = new ArrayList<ValidationBean>();
		
		// PP Imposto i dati gara
		schedaA.setInfoGara(infoGara);
		
		// se sono settati questi due bean in ingreso valorizzo con questi
		if(infoComuni != null && aggiudicazione != null && aggiudicatari != null){
			
			// gestione crossedFields
			infoComuni.setIdLotto(crossedFields.getIdLotto());
			//gm questi campi saranno prelevati dall'xml, se non ci sono, li prendo dai crossedFields
			if(infoComuni.getID_ESCLUSIONE()==0)
    			infoComuni.setID_ESCLUSIONE(crossedFields.getID_ESCLUSIONE());
			if(infoComuni.getID_MODO_REAL()==0)
		    	infoComuni.setID_MODO_REAL(crossedFields.getID_MODO_REAL());
			if(infoComuni.getFLAG_ESCLUSO()==null || "".equals(infoComuni.getFLAG_ESCLUSO()))
		    	infoComuni.setFLAG_ESCLUSO(crossedFields.getFLAG_ESCLUSO());
			
         // in aggiunta quando i dati arrivano da xml devo sovrascrivere alcuni campi
         infoComuni.setCfAmministrazione(crossedFields.getCfAmministrazione());
         infoComuni.setDenAmministrazione(crossedFields.getDenAmministrazione());
         infoComuni.setCfStazioneAppaltante(crossedFields.getCfStazioneAppaltante());
         infoComuni.setDenStazioneAppaltante(crossedFields.getDenStazioneAppaltante());
         infoComuni.setCodiceCC(crossedFields.getCodiceCC());
         infoComuni.setDenomCC(crossedFields.getDenomCC());
         
			schedaA.setInfoComuni(infoComuni);
			schedaA.setAggiudicazione(aggiudicazione);
			schedaA.setAggiudicatari(aggiudicatari);
			
			// PP qui ho gli infoComuni settati, salvo il tipo settore
			converter.tipoSettore = schedaA.getInfoComuni().getFlagEnteSpeciale();

			// PP - BANDI : i dati di pubblicita sono sempre quelli della gara pubblicata, se esiste
			 //FIXXME: gm per adesso imponiamo che i dati di pubblicazione siano inseriti
			//con l'xml dei dati comuni, e non sovrascritti con quelli della gara pubblicata
			
			if(crossedFields.getPubblicazione()!=null){
				PubblicazioneBean puBean = crossedFields.getPubblicazione();
				infoComuni.setPubblicazione(puBean);
			}
			
			
			// ho un solo elemento
			if(datiAggiudicazione.getSchedaCompletaArray() != null && datiAggiudicazione.getSchedaCompletaArray().length > 0){
				if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");
				
				SchedaEsclusoType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getEscluso();
				if(aggiudicazioneLocale != null){
					
					AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto(), TipoAggiudicazione.E);
					
               if(SimogFlags.is3031_RFWEBGL02Active()
                     && !SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())
                     && SimogProperties.getInstance().isCUPAttivo()){
	                  // ignoro il cup vecchia struttura e carico la lista
	                  aggiudicazioneBean.setCup("");
	                  
	                  if(aggiudicazioneLocale.isSetCUPLOTTO()){
	                     schedaA.setElencoCup(convertiListaCup(aggiudicazioneLocale.getCUPLOTTO()));                     
	                  }

	                  if(aggiudicazioneLocale.getAppalto().isSetFLAGCUP())
	                     schedaA.setFlagCUP(aggiudicazioneLocale.getAppalto().getFLAGCUP().toString());
	             }

					Map<String, List<ResponsabileBean>> responsabili = converter.convertiIncaricatiAggiudicazione(aggiudicazioneLocale.getIncaricatiArray(), true, PSBD.SEZIONE_RE);
					
					List<ResponsabileBean> listOfResponsabili = responsabili.get(PSBD.SEZIONE_RE);
					List<ResponsabileBean> listOfPrestazioni = responsabili.get(PSBD.SEZIONE_PA);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);

					List<AggiudicatarioBean> listOfAggiudicatari = converter.convertiAggiudicatari(aggiudicazioneLocale.getAggiudicatariArray(), true);
					if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
		
					
					schedaA.setAggiudicazione(aggiudicazioneBean);
					
					
					schedaA.setAggiudicatari(listOfAggiudicatari);
					schedaA.setResponsabili(listOfResponsabili);
					schedaA.setPrestazioni(listOfPrestazioni);
				}
			}
			
			return schedaA;
		}
		// se solo info comuni presente nel db
		if(infoComuni != null && aggiudicazione == null && aggiudicatari == null){
			
			// gestione crossedFields
			infoComuni.setIdLotto(crossedFields.getIdLotto());
			//gm questi campi saranno prelevati dall'xml, se non ci sono, li prendo dai crossedFields
			if(infoComuni.getID_ESCLUSIONE()==0)
    			infoComuni.setID_ESCLUSIONE(crossedFields.getID_ESCLUSIONE());
			if(infoComuni.getID_MODO_REAL()==0)
		    	infoComuni.setID_MODO_REAL(crossedFields.getID_MODO_REAL());
			if(infoComuni.getFLAG_ESCLUSO()==null || "".equals(infoComuni.getFLAG_ESCLUSO()))
		    	infoComuni.setFLAG_ESCLUSO(crossedFields.getFLAG_ESCLUSO());
			
         // in aggiunta quando i dati arrivano da xml devo sovrascrivere alcuni campi
         infoComuni.setCfAmministrazione(crossedFields.getCfAmministrazione());
         infoComuni.setDenAmministrazione(crossedFields.getDenAmministrazione());
         infoComuni.setCfStazioneAppaltante(crossedFields.getCfStazioneAppaltante());
         infoComuni.setDenStazioneAppaltante(crossedFields.getDenStazioneAppaltante());
         infoComuni.setCodiceCC(crossedFields.getCodiceCC());
         infoComuni.setDenomCC(crossedFields.getDenomCC());
         
			schedaA.setInfoComuni(infoComuni);
		}
		// settaggio dei dati comuni..
		if(datiAggiudicazione.getDatiComuni() != null && infoComuni == null){
			InfoComuniBean infoComuniLocale = converter.converti(datiAggiudicazione.getDatiComuni());
			PubblicazioneBean pubblicazione = converter.converti(datiAggiudicazione.getPubblicazione());
			infoComuniLocale.setPubblicazione(pubblicazione);
			
			// PP - BANDI : i dati di pubblicita sono sempre quelli della gara pubblicata, se esiste
			 //FIXXME: gm per adesso imponiamo che i dati di pubblicazione siano inseriti
			//con l'xml dei dati comuni, e non sovrascritti con quelli della gara pubblicata
			
			if(crossedFields.getPubblicazione()!=null){
				PubblicazioneBean puBean = crossedFields.getPubblicazione();
				infoComuniLocale.setPubblicazione(puBean);
			}
			
			// gestione crossedFields
			infoComuniLocale.setIdLotto(crossedFields.getIdLotto());
			//gm questi campi saranno prelevati dall'xml, se non ci sono, li prendo dai crossedFields
			if(infoComuniLocale.getID_ESCLUSIONE()==0)
    			infoComuniLocale.setID_ESCLUSIONE(crossedFields.getID_ESCLUSIONE());
			if(infoComuniLocale.getID_MODO_REAL()==0)
		    	infoComuniLocale.setID_MODO_REAL(crossedFields.getID_MODO_REAL());
			if(infoComuniLocale.getFLAG_ESCLUSO()==null || "".equals(infoComuniLocale.getFLAG_ESCLUSO()))
		    	infoComuniLocale.setFLAG_ESCLUSO(crossedFields.getFLAG_ESCLUSO());
			
			
			// in aggiunta quando i dati arrivano da xml devo sovrascrivere alcuni campi
			infoComuniLocale.setCfAmministrazione(crossedFields.getCfAmministrazione());
			infoComuniLocale.setDenAmministrazione(crossedFields.getDenAmministrazione());
			infoComuniLocale.setCfStazioneAppaltante(crossedFields.getCfStazioneAppaltante());
			infoComuniLocale.setDenStazioneAppaltante(crossedFields.getDenStazioneAppaltante());
			infoComuniLocale.setCodiceCC(crossedFields.getCodiceCC());
			infoComuniLocale.setDenomCC(crossedFields.getDenomCC());
			
			schedaA.setInfoComuni(infoComuniLocale);
		}
		
		// PP qui ho gli infoComuni settati, salvo il tipo settore
		converter.tipoSettore = schedaA.getInfoComuni().getFlagEnteSpeciale();
		
		// ho un solo elemento
		if(datiAggiudicazione.getSchedaCompletaArray() != null && datiAggiudicazione.getSchedaCompletaArray().length > 0){
			if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");
			
			SchedaEsclusoType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getEscluso();
			if(aggiudicazioneLocale != null){
				
				AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto(), TipoAggiudicazione.E);
				
            if(SimogFlags.is3031_RFWEBGL02Active()
                  && !SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())
                  && SimogProperties.getInstance().isCUPAttivo()){
               // ignoro il cup vecchia struttura e carico la lista
               aggiudicazioneBean.setCup("");
               
               if(aggiudicazioneLocale.isSetCUPLOTTO()){
                  schedaA.setElencoCup(convertiListaCup(aggiudicazioneLocale.getCUPLOTTO()));                     
               }

               if(aggiudicazioneLocale.getAppalto().isSetFLAGCUP())
                  schedaA.setFlagCUP(aggiudicazioneLocale.getAppalto().getFLAGCUP().toString());
            }

				
				Map<String, List<ResponsabileBean>> responsabili = converter.convertiIncaricatiAggiudicazione(aggiudicazioneLocale.getIncaricatiArray(), true, PSBD.SEZIONE_RE);
				
				List<ResponsabileBean> listOfResponsabili = responsabili.get(PSBD.SEZIONE_RE);
				List<ResponsabileBean> listOfPrestazioni = responsabili.get(PSBD.SEZIONE_PA);
				if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);

				List<AggiudicatarioBean> listOfAggiudicatari = converter.convertiAggiudicatari(aggiudicazioneLocale.getAggiudicatariArray(), true);
				if(converter.containsDuplicate) listOfDuplicateWarnings.add(converter.duplicateWarning);
	
				
				schedaA.setAggiudicazione(aggiudicazioneBean);
				
				schedaA.setAggiudicatari(listOfAggiudicatari);
				schedaA.setResponsabili(listOfResponsabili);
				schedaA.setPrestazioni(listOfPrestazioni);
			}
			
		}
		if(listOfDuplicateWarnings != null && listOfDuplicateWarnings.size() > 0){
			containsDuplicate = true;
			this.listOfDuplicateWarning = listOfDuplicateWarnings;
		}
		
		return schedaA;
	}

	/**
	 * @param datiAggiudicazione
	 * @param loadAllFromXML flag che indica se caricare alcuni oggetti necessari dal xml oppure dagli altri parametri (diversi dal tipo xml)
	 * @param infoComuni
	 * @param aggiudicazione
	 * @param listOfAggAggiudicatari
	 * @return
	 */
	public SchedaInizioLavori constructSchedaInizio(DatiAggiudicazioneType datiAggiudicazione, boolean loadAllFromXML,
			InfoComuniBean infoComuni,AggiudicazioneBean aggiudicazione, List<AggiudicatarioBean> listOfAggAggiudicatari) throws UnexpectedException, ClassNotFoundException, Exception{
		
		this.containsDuplicate = false;
		this.listOfDuplicateWarning = null;
		SchedaInizioLavori schedaInizio = new SchedaInizioLavori();
		List<ValidationBean> listOfDuplicateWarning = new ArrayList<ValidationBean>();
		
		if(datiAggiudicazione.getSchedaCompletaArray() != null){
			if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");

			DatiInizioType datiInizio = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiInizio();
			
			if(datiInizio != null){
				
				InizioLavoriBean inizioBean = converter.converti(datiInizio.getInizio());
				PubblicazioneBean pubbean = converter.convertiPubblicazioneInizioLavori(datiInizio.getPubblicazioneEsito());
				inizioBean.setPubblicazione(pubbean);
				
				List<ResponsabileBean> listOfResponsabili = converter.convertiIncaricatiInizioLavori(datiInizio.getIncaricatiArray(), true);			
				if(converter.containsDuplicate) listOfDuplicateWarning.add(converter.duplicateWarning);
				
				List<PosizioneAggiudicatarioBean> listOfPosizioni = converter.convertiPosizioniInizioLavori(datiInizio.getPosizioniArray(), true);			
				if(converter.containsDuplicate) listOfDuplicateWarning.add(converter.duplicateWarning);
				
				schedaInizio.setDatiInizio(inizioBean);
				schedaInizio.setResponsabiliInizio(listOfResponsabili);
				schedaInizio.setPosizioneAggiudicatari(listOfPosizioni);
				
				// se devo caricare da xml
				if(loadAllFromXML){
					
					InfoComuniBean infoComuniLocale = converter.converti(datiAggiudicazione.getDatiComuni());
					PubblicazioneBean pubblicazione = converter.converti(datiAggiudicazione.getPubblicazione());
					infoComuniLocale.setPubblicazione(pubblicazione);
					
					AggiudicazioneType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getAggiudicazione();
					AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto());
					
					List<AggiudicatarioBean> listOfAggiudicatari = converter.convertiAggiudicatari(aggiudicazioneLocale.getAggiudicatariArray(), true);
					if(converter.containsDuplicate) listOfDuplicateWarning.add(converter.duplicateWarning);
	
					schedaInizio.setInfoComuni(infoComuniLocale);
					schedaInizio.setAggiudicazione(aggiudicazioneBean);
					schedaInizio.setAggiudicatari(listOfAggiudicatari);
				// se posso caricare tramite gli altri parametri
				}else{
					
					schedaInizio.setInfoComuni(infoComuni);
					schedaInizio.setAggiudicazione(aggiudicazione);
					schedaInizio.setAggiudicatari(listOfAggAggiudicatari);
				}
			}
		}
		if(listOfDuplicateWarning != null && listOfDuplicateWarning.size() > 0){
			containsDuplicate = true;
			this.listOfDuplicateWarning = listOfDuplicateWarning;
		}
		return schedaInizio;		
	}
	
	/**
	 * @param datiAggiudicazione
	 * @param loadAllFromXML flag che indica se caricare alcuni oggetti necessari dal xml oppure dagli altri parametri (diversi dal tipo xml)
	 * @param infoComuni
	 * @param aggiudicazione
	 * @param listOfAggAggiudicatari
	 * @return
	 */
	public SchedaStipula constructSchedaStipula(DatiAggiudicazioneType datiAggiudicazione, boolean loadAllFromXML,
			InfoComuniBean infoComuni,AggiudicazioneBean aggiudicazione, List<AggiudicatarioBean> listOfAggAggiudicatari) throws UnexpectedException, ClassNotFoundException, Exception{
		
		this.containsDuplicate = false;
		this.listOfDuplicateWarning = null;
		SchedaStipula schedaStipula = new SchedaStipula();
		List<ValidationBean> listOfDuplicateWarning = new ArrayList<ValidationBean>();
		
		if(datiAggiudicazione.getSchedaCompletaArray() != null){
			if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");

			DatiStipulaType datiInizio = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiStipula();
			
			if(datiInizio != null){
				
				StipulaBean inizioBean = converter.converti(datiInizio.getStipula());
				PubblicazioneBean pubbean = converter.convertiPubblicazioneInizioLavori(datiInizio.getPubblicazioneEsito());
				inizioBean.setPubblicazione(pubbean);
				
				
				
				schedaStipula.setStipula(inizioBean);
				
				
				// se devo caricare da xml
				if(loadAllFromXML){
					
					InfoComuniBean infoComuniLocale = converter.converti(datiAggiudicazione.getDatiComuni());
					PubblicazioneBean pubblicazione = converter.converti(datiAggiudicazione.getPubblicazione());
					infoComuniLocale.setPubblicazione(pubblicazione);
					
					AggiudicazioneType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getAggiudicazione();
					AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto());
					
					List<AggiudicatarioBean> listOfAggiudicatari = converter.convertiAggiudicatari(aggiudicazioneLocale.getAggiudicatariArray(), true);
					if(converter.containsDuplicate) listOfDuplicateWarning.add(converter.duplicateWarning);
	
					schedaStipula.setInfoComuni(infoComuniLocale);
					schedaStipula.setAggiudicazione(aggiudicazioneBean);
					schedaStipula.setAggiudicatari(listOfAggiudicatari);
				// se posso caricare tramite gli altri parametri
				}else{
					
					schedaStipula.setInfoComuni(infoComuni);
					schedaStipula.setAggiudicazione(aggiudicazione);
					schedaStipula.setAggiudicatari(listOfAggAggiudicatari);
				}
			}
		}
		if(listOfDuplicateWarning != null && listOfDuplicateWarning.size() > 0){
			containsDuplicate = true;
			this.listOfDuplicateWarning = listOfDuplicateWarning;
		}
		return schedaStipula;		
	}
	
	/**
	 * @param datiAggiudicazione
	 * @param loadAllFromXML
	 * @param infoComuni
	 * @param aggiudicazione
	 * @return
	 * @throws UnexpectedException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public SchedaAvanzamento constructSchedaAvanzamenti(DatiAggiudicazioneType datiAggiudicazione, boolean loadAllFromXML,
			InfoComuniBean infoComuni,AggiudicazioneBean aggiudicazione, int positionStart,
			List<VarianteBean> varianti) throws UnexpectedException, ClassNotFoundException, Exception{
		
		this.containsDuplicate = false;
		this.listOfDuplicateWarning = null;
		SchedaAvanzamento schedaAvanzamento = new SchedaAvanzamento();
		List<ValidationBean> listOfDuplicateWarning = new ArrayList<ValidationBean>();

		if(datiAggiudicazione.getSchedaCompletaArray() != null){
			if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");

			List<AvanzamentoBean> listoOfAvanzamenti = new ArrayList<AvanzamentoBean>();
			if(datiAggiudicazione.getSchedaCompletaArray()[0].getDatiAvanzamenti() != null){
				AvanzamentoType[] avanzamenti = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiAvanzamenti().getAvanzamentoArray();
				for(int i = 0; i < avanzamenti.length; i++){
					AvanzamentoBean avanzamentoCorrente = converter.converti(avanzamenti[i], i + positionStart);
					listoOfAvanzamenti.add(avanzamentoCorrente);
				}
				schedaAvanzamento.setAvanzamenti(listoOfAvanzamenti);
				
				if(loadAllFromXML){
	
					InfoComuniBean infoComuniLocale = converter.converti(datiAggiudicazione.getDatiComuni());
					PubblicazioneBean pubblicazione = converter.converti(datiAggiudicazione.getPubblicazione());
					infoComuniLocale.setPubblicazione(pubblicazione);
					
					AggiudicazioneType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getAggiudicazione();
					AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto());
	
					schedaAvanzamento.setInfoComuni(infoComuniLocale);
					schedaAvanzamento.setAggiudicazione(aggiudicazioneBean);
					
					VariantiType datiVariante = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiVarianti();
					if(datiVariante != null){
						List<VarianteBean> listOfVarianti = new ArrayList<VarianteBean>();
						VarianteType[] locVarianti = datiVariante.getVarianteArray();
						for(int i = 0; i < locVarianti.length; i++){
							VarianteType varianteCorrente = locVarianti[i];	
							
							VarianteBean varianteBeanCorrente = converter.converti(varianteCorrente.getVariante());
							List<EventiMotiviVariantiBean> listOfEventi = converter.convertiEventi(varianteCorrente.getMotiviArray(), true);
							if(converter.containsDuplicate) listOfDuplicateWarning.add(converter.duplicateWarning);
							varianteBeanCorrente.setEmvb(listOfEventi);
							
							listOfVarianti.add(varianteBeanCorrente);
						}
						schedaAvanzamento.setVarianti(listOfVarianti);
					}
				}else{
					schedaAvanzamento.setInfoComuni(infoComuni);
					schedaAvanzamento.setAggiudicazione(aggiudicazione);
					schedaAvanzamento.setVarianti(varianti);
				}
			}
		}
		if(listOfDuplicateWarning != null && listOfDuplicateWarning.size() > 0){
			containsDuplicate = true;
			this.listOfDuplicateWarning = listOfDuplicateWarning;
		}
		return schedaAvanzamento;
	}
	
	/**
	 * @param datiAggiudicazione
	 * @param loadAllFromXML
	 * @param infoComuni
	 * @param aggiudicazione
	 * @return
	 * @throws UnexpectedException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public SchedaConclusione constructSchedaConclusione(DatiAggiudicazioneType datiAggiudicazione, boolean loadAllFromXML,
			InfoComuniBean infoComuni,AggiudicazioneBean aggiudicazione) throws UnexpectedException, ClassNotFoundException, Exception{
		
		this.containsDuplicate = false;
		this.listOfDuplicateWarning = null;
		SchedaConclusione schedaConclusione = new SchedaConclusione();
		List<ValidationBean> listOfDuplicateWarning = new ArrayList<ValidationBean>();

		if(datiAggiudicazione.getSchedaCompletaArray() != null){
			if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");

			ConclusioneType conclusione = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiConclusione();
			if(conclusione != null){
				schedaConclusione.setConclusione(converter.converti(conclusione));
				
				if(loadAllFromXML){
	
					InfoComuniBean infoComuniLocale = converter.converti(datiAggiudicazione.getDatiComuni());
					PubblicazioneBean pubblicazione = converter.converti(datiAggiudicazione.getPubblicazione());
					infoComuniLocale.setPubblicazione(pubblicazione);
					
					AggiudicazioneType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getAggiudicazione();
					AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto());
	
					schedaConclusione.setInfoComuni(infoComuniLocale);
					schedaConclusione.setAggiudicazione(aggiudicazioneBean);
					
				}else{
					
					schedaConclusione.setInfoComuni(infoComuni);
					schedaConclusione.setAggiudicazione(aggiudicazione);
				}
			}
			
		}
		if(listOfDuplicateWarning != null && listOfDuplicateWarning.size() > 0){
			containsDuplicate = true;
			this.listOfDuplicateWarning = listOfDuplicateWarning;
		}
		return schedaConclusione;
	}

	/**
	 * @param datiAggiudicazione
	 * @param loadAllFromXML
	 * @param infoComuni
	 * @param aggiudicazione
	 * @return
	 * @throws UnexpectedException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public SchedaR129 constructSchedaRitardi(DatiAggiudicazioneType datiAggiudicazione, boolean loadAllFromXML,
			InfoComuniBean infoComuni,AggiudicazioneBean aggiudicazione) throws UnexpectedException, ClassNotFoundException, Exception{
		
		this.containsDuplicate = false;
		this.listOfDuplicateWarning = null;
		SchedaR129 schedeRitardi = new SchedaR129();
		List<ValidationBean> listOfDuplicateWarning = new ArrayList<ValidationBean>();

		if(datiAggiudicazione.getSchedaCompletaArray() != null){
			if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");

			
			List<R129Bean> listOfRitardi = new ArrayList<R129Bean>();
			if(datiAggiudicazione.getSchedaCompletaArray()[0].getDatiRitardi() !=  null){
				RitardoType[] ritardi = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiRitardi().getRitardoArray();
				for(int i = 0; i < ritardi.length; i++ ){
					R129Bean ritardoCorrente = converter.converti(ritardi[i]);
					listOfRitardi.add(ritardoCorrente);
				}
				schedeRitardi.setR129s(listOfRitardi);
				
				if(loadAllFromXML){
	
					InfoComuniBean infoComuniLocale = converter.converti(datiAggiudicazione.getDatiComuni());
					PubblicazioneBean pubblicazione = converter.converti(datiAggiudicazione.getPubblicazione());
					infoComuniLocale.setPubblicazione(pubblicazione);
					
					AggiudicazioneType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getAggiudicazione();
					AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto());
	
					schedeRitardi.setInfoComuni(infoComuniLocale);
					schedeRitardi.setAggiudicazione(aggiudicazioneBean);
					
				}else{
					
					schedeRitardi.setInfoComuni(infoComuni);
					schedeRitardi.setAggiudicazione(aggiudicazione);
				}
			}
			
		}
		if(listOfDuplicateWarning != null && listOfDuplicateWarning.size() > 0){
			containsDuplicate = true;
			this.listOfDuplicateWarning = listOfDuplicateWarning;
		}
		return schedeRitardi;
	}
	
	/**
	 * @param datiAggiudicazione
	 * @param loadAllFromXML
	 * @param infoComuni
	 * @param aggiudicazione
	 * @param inizioLavori
	 * @return
	 * @throws UnexpectedException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public SchedaSospensione constructSchedaSospensioni(DatiAggiudicazioneType datiAggiudicazione, boolean loadAllFromXML,
			InfoComuniBean infoComuni,AggiudicazioneBean aggiudicazione, InizioLavoriBean inizioLavori) throws UnexpectedException, ClassNotFoundException, Exception{
		
		this.containsDuplicate = false;
		this.listOfDuplicateWarning = null;
		SchedaSospensione schedeSospensioni = new SchedaSospensione();
		List<ValidationBean> listOfDuplicateWarning = new ArrayList<ValidationBean>();

		if(datiAggiudicazione.getSchedaCompletaArray() != null){
			if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");

			List<SospensioniBean> listOfSospensioni = new ArrayList<SospensioniBean>();
			if(datiAggiudicazione.getSchedaCompletaArray()[0].getDatiSospensioni() != null){
				SospensioneType[] sospensioni = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiSospensioni().getSospensioneArray();
				for(int i = 0; i < sospensioni.length; i++){
					SospensioniBean sospensioneCorrente = converter.converti(sospensioni[i]);
					listOfSospensioni.add(sospensioneCorrente);
				}
				schedeSospensioni.setSospensioni(listOfSospensioni);
				
				if(loadAllFromXML){
	
					InfoComuniBean infoComuniLocale = converter.converti(datiAggiudicazione.getDatiComuni());
					PubblicazioneBean pubblicazione = converter.converti(datiAggiudicazione.getPubblicazione());
					infoComuniLocale.setPubblicazione(pubblicazione);
					
					AggiudicazioneType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getAggiudicazione();
					AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto());
	
					DatiInizioType datiInizio = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiInizio();
					InizioLavoriBean inizioBean = converter.converti(datiInizio.getInizio());
					PubblicazioneBean pubbean = converter.convertiPubblicazioneInizioLavori(datiInizio.getPubblicazioneEsito());
					inizioBean.setPubblicazione(pubbean);
								
					schedeSospensioni.setInizioLavori(inizioBean);
					schedeSospensioni.setInfoComuni(infoComuniLocale);
					schedeSospensioni.setAggiudicazione(aggiudicazioneBean);
					
				}else{
					
					schedeSospensioni.setInizioLavori(inizioLavori);
					schedeSospensioni.setInfoComuni(infoComuni);
					schedeSospensioni.setAggiudicazione(aggiudicazione);
				}
			}
			
		}
		if(listOfDuplicateWarning != null && listOfDuplicateWarning.size() > 0){
			containsDuplicate = true;
			this.listOfDuplicateWarning = listOfDuplicateWarning;
		}
		return schedeSospensioni;
	}
	
	/**
	 * @param datiAggiudicazione
	 * @param loadAllFromXML
	 * @param infoComuni
	 * @param aggiudicazione
	 * @param inizioLavori
	 * @return
	 * @throws UnexpectedException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public SchedaSubAppalti constructSchedaSubAppalti(DatiAggiudicazioneType datiAggiudicazione, boolean loadAllFromXML,
			InfoComuniBean infoComuni,AggiudicazioneBean aggiudicazione, InizioLavoriBean inizioLavori,
			List<AggiudicatarioBean> aggiudicatari) throws UnexpectedException, ClassNotFoundException, Exception{
		
		this.containsDuplicate = false;
		this.listOfDuplicateWarning = null;
		SchedaSubAppalti schedeSubAppalti = new SchedaSubAppalti();
		List<ValidationBean> listOfDuplicateWarning = new ArrayList<ValidationBean>();

		if(datiAggiudicazione.getSchedaCompletaArray() != null){
			if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");

			List<SubappaltiBean> listOfSubAppalto = new ArrayList<SubappaltiBean>();
			if(datiAggiudicazione.getSchedaCompletaArray()[0].getDatiSubappalti() != null){
				SubappaltoType[] subAppalti = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiSubappalti().getSubappaltoArray();
				for(int i = 0; i <subAppalti.length; i++){
					SubappaltiBean subAppaltoCorrente = converter.converti(subAppalti[i]);
					listOfSubAppalto.add(subAppaltoCorrente);
				}
				schedeSubAppalti.setSubAppalti(listOfSubAppalto);
				
				if(loadAllFromXML){
	
					InfoComuniBean infoComuniLocale = converter.converti(datiAggiudicazione.getDatiComuni());
					PubblicazioneBean pubblicazione = converter.converti(datiAggiudicazione.getPubblicazione());
					infoComuniLocale.setPubblicazione(pubblicazione);
					
					AggiudicazioneType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getAggiudicazione();
					AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto());
	
					DatiInizioType datiInizio = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiInizio();
					InizioLavoriBean inizioBean = converter.converti(datiInizio.getInizio());
					PubblicazioneBean pubbean = converter.convertiPubblicazioneInizioLavori(datiInizio.getPubblicazioneEsito());
					inizioBean.setPubblicazione(pubbean);

					List<AggiudicatarioBean> listOfAggiudicatari = converter.convertiAggiudicatari(aggiudicazioneLocale.getAggiudicatariArray(), true);
					
					schedeSubAppalti.setInizioLavori(inizioBean);
					schedeSubAppalti.setInfoComuni(infoComuniLocale);
					schedeSubAppalti.setAggiudicazione(aggiudicazioneBean);
					schedeSubAppalti.setAggiudicatari(listOfAggiudicatari);					
				}else{
					schedeSubAppalti.setInizioLavori(inizioLavori);
					schedeSubAppalti.setInfoComuni(infoComuni);
					schedeSubAppalti.setAggiudicazione(aggiudicazione);
					schedeSubAppalti.setAggiudicatari(aggiudicatari);
				}
			}
			
		}
		if(listOfDuplicateWarning != null && listOfDuplicateWarning.size() > 0){
			containsDuplicate = true;
			this.listOfDuplicateWarning = listOfDuplicateWarning;
		}
		return schedeSubAppalti;
	}
	
	/**
	 * @param datiAggiudicazione
	 * @param loadAllFromXML
	 * @param infoComuni
	 * @param aggiudicazione
	 * @param inizioLavori
	 * @return
	 * @throws UnexpectedException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public SchedaAccordo constructSchedaAccordi(DatiAggiudicazioneType datiAggiudicazione, boolean loadAllFromXML,
			InfoComuniBean infoComuni,AggiudicazioneBean aggiudicazione, InizioLavoriBean inizioLavori) throws UnexpectedException, ClassNotFoundException, Exception{
		
		this.containsDuplicate = false;
		this.listOfDuplicateWarning = null;
		SchedaAccordo schedeAccordi = new SchedaAccordo();
		List<ValidationBean> listOfDuplicateWarning = new ArrayList<ValidationBean>();

		if(datiAggiudicazione.getSchedaCompletaArray() != null){
			if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");

			List<AccordoBean> listOfAccordi = new ArrayList<AccordoBean>();
			
			if(datiAggiudicazione.getSchedaCompletaArray()[0].getDatiAccordi() != null){
				AccordoBonarioType[] accordi = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiAccordi().getAccordoBonarioArray();
				for(int i = 0; i < accordi.length; i++){
					AccordoBean accordoCorrente = converter.converti(accordi[i]);
					listOfAccordi.add(accordoCorrente);
				}
				schedeAccordi.setAccordi(listOfAccordi);
				
				if(loadAllFromXML){
	
					InfoComuniBean infoComuniLocale = converter.converti(datiAggiudicazione.getDatiComuni());
					PubblicazioneBean pubblicazione = converter.converti(datiAggiudicazione.getPubblicazione());
					infoComuniLocale.setPubblicazione(pubblicazione);
					
					AggiudicazioneType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getAggiudicazione();
					AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto());
					
					DatiInizioType datiInizio = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiInizio();
					InizioLavoriBean inizioBean = converter.converti(datiInizio.getInizio());
					PubblicazioneBean pubbean = converter.convertiPubblicazioneInizioLavori(datiInizio.getPubblicazioneEsito());
					inizioBean.setPubblicazione(pubbean);
								
					schedeAccordi.setInizioLavori(inizioBean);
					schedeAccordi.setInfoComuni(infoComuniLocale);
					schedeAccordi.setAggiudicazione(aggiudicazioneBean);
					
				}else{
					schedeAccordi.setInizioLavori(inizioLavori);
					schedeAccordi.setInfoComuni(infoComuni);
					schedeAccordi.setAggiudicazione(aggiudicazione);
				}
			}
			
		}
		if(listOfDuplicateWarning != null && listOfDuplicateWarning.size() > 0){
			containsDuplicate = true;
			this.listOfDuplicateWarning = listOfDuplicateWarning;
		}
		return schedeAccordi;
	}
	
	/**
	 * @param datiAggiudicazione
	 * @param loadAllFromXML
	 * @param infoComuni
	 * @param aggiudicazione
	 * @param conclusione
	 * @param listOfAccordi
	 * @return
	 * @throws UnexpectedException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public SchedaCollaudo constructSchedaCollaudo(DatiAggiudicazioneType datiAggiudicazione, boolean loadAllFromXML,
			InfoComuniBean infoComuni,AggiudicazioneBean aggiudicazione, ConclusioneBean conclusione,
				List<AccordoBean> listOfAccordi, InizioLavoriBean inizioLavori) throws UnexpectedException, ClassNotFoundException, Exception{
		
		this.containsDuplicate = false;
		this.listOfDuplicateWarning = null;
		SchedaCollaudo schedaCollaudo = new SchedaCollaudo();
		List<ValidationBean> listOfDuplicateWarning = new ArrayList<ValidationBean>();

		if(datiAggiudicazione.getSchedaCompletaArray() != null){
			if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");

			DatiCollaudoType datiCollaudo = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiCollaudo();
			if(datiCollaudo != null){
				CollaudoBean collaudo = converter.converti(datiCollaudo.getCollaudo());
				
				List<ResponsabileBean> listOfResponsabili = converter.convertiIncaricatiCollaudo(datiCollaudo.getIncaricatiArray(), true);
				if(converter.containsDuplicate) listOfDuplicateWarning.add(converter.duplicateWarning);
				
				schedaCollaudo.setCollaudo(collaudo);
				schedaCollaudo.setIncaricati(listOfResponsabili);
				
				if(loadAllFromXML){
	
					InfoComuniBean infoComuniLocale = converter.converti(datiAggiudicazione.getDatiComuni());
					PubblicazioneBean pubblicazione = converter.converti(datiAggiudicazione.getPubblicazione());
					infoComuniLocale.setPubblicazione(pubblicazione);
					
					AggiudicazioneType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getAggiudicazione();
					AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto());
	
					ConclusioneType conclusioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiConclusione();
					InizioType inizioLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiInizio().getInizio();
					
					List<AccordoBean> listOfAccordiLocale = new ArrayList<AccordoBean>();
					AccordoBonarioType[] accordi = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiAccordi().getAccordoBonarioArray();
					for(int i = 0; i < accordi.length; i++){
						AccordoBean accordoCorrente = converter.converti(accordi[i]);
						listOfAccordiLocale.add(accordoCorrente);
					}
					
					schedaCollaudo.setAccordiBonario(listOfAccordiLocale);
					schedaCollaudo.setConclusione(converter.converti(conclusioneLocale));
					schedaCollaudo.setInfoComuni(infoComuniLocale);
					schedaCollaudo.setAggiudicazione(aggiudicazioneBean);
					schedaCollaudo.setInizioLavori(converter.converti(inizioLocale));					
				}else{
					
					schedaCollaudo.setAccordiBonario(listOfAccordi);
					schedaCollaudo.setConclusione(conclusione);
					schedaCollaudo.setInfoComuni(infoComuni);
					schedaCollaudo.setInizioLavori(inizioLavori);
					schedaCollaudo.setAggiudicazione(aggiudicazione);
				}
			}
			
		}
		if(listOfDuplicateWarning != null && listOfDuplicateWarning.size() > 0){
			containsDuplicate = true;
			this.listOfDuplicateWarning = listOfDuplicateWarning;
		}
		return schedaCollaudo;
	}
	
	/**
	 * @param datiAggiudicazione
	 * @param loadAllFromXML
	 * @param infoComuni
	 * @param aggiudicazione
	 * @param inizioLavori
	 * @return
	 * @throws UnexpectedException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public SchedaVariante constructSchedaVarianti(DatiAggiudicazioneType datiAggiudicazione, boolean loadAllFromXML,
			InfoComuniBean infoComuni,AggiudicazioneBean aggiudicazione, InizioLavoriBean inizioLavori) throws UnexpectedException, ClassNotFoundException, Exception{
		
		this.containsDuplicate = false;
		this.listOfDuplicateWarning = null;
		SchedaVariante schedeVarianti = new SchedaVariante();
		List<ValidationBean> listOfDuplicateWarning = new ArrayList<ValidationBean>();

		if(datiAggiudicazione.getSchedaCompletaArray() != null){
			if(datiAggiudicazione.getSchedaCompletaArray().length > 1) throw new UnexpectedException("Non e' rispettata la cardinalita' richiesta di 1 a 1 cig - cui");

			List<VarianteBean> listOfVarianti = new ArrayList<VarianteBean>();
			VariantiType datiVariante = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiVarianti();
			if(datiVariante != null){
				VarianteType[] varianti = datiVariante.getVarianteArray();
				for(int i = 0; i < varianti.length; i++){
					VarianteType varianteCorrente = varianti[i];	
					
					VarianteBean varianteBeanCorrente = converter.converti(varianteCorrente.getVariante());
					List<EventiMotiviVariantiBean> listOfEventi = converter.convertiEventi(varianteCorrente.getMotiviArray(), true);
					if(converter.containsDuplicate) listOfDuplicateWarning.add(converter.duplicateWarning);
					varianteBeanCorrente.setEmvb(listOfEventi);
					
					listOfVarianti.add(varianteBeanCorrente);
				}
				schedeVarianti.setVarianti(listOfVarianti);
				
				
				if(loadAllFromXML){
	
					InfoComuniBean infoComuniLocale = converter.converti(datiAggiudicazione.getDatiComuni());
					PubblicazioneBean pubblicazione = converter.converti(datiAggiudicazione.getPubblicazione());
					infoComuniLocale.setPubblicazione(pubblicazione);
					
					AggiudicazioneType aggiudicazioneLocale = datiAggiudicazione.getSchedaCompletaArray()[0].getAggiudicazione();
					AggiudicazioneBean aggiudicazioneBean = converter.converti(aggiudicazioneLocale.getAppalto());
	
					DatiInizioType datiInizio = datiAggiudicazione.getSchedaCompletaArray()[0].getDatiInizio();
					InizioLavoriBean inizioBean = converter.converti(datiInizio.getInizio());
					PubblicazioneBean pubbean = converter.convertiPubblicazioneInizioLavori(datiInizio.getPubblicazioneEsito());
					inizioBean.setPubblicazione(pubbean);
								
					schedeVarianti.setInizioLavori(inizioBean);
					schedeVarianti.setInfoComuni(infoComuniLocale);
					schedeVarianti.setAggiudicazione(aggiudicazioneBean);
					
				}else{
					schedeVarianti.setInizioLavori(inizioLavori);
					schedeVarianti.setInfoComuni(infoComuni);
					schedeVarianti.setAggiudicazione(aggiudicazione);
				}
			}
			
		}
		if(listOfDuplicateWarning != null && listOfDuplicateWarning.size() > 0){
			containsDuplicate = true;
			this.listOfDuplicateWarning = listOfDuplicateWarning;
		}
		return schedeVarianti;
	}
	
	public void associaAusiliarie (List<DittaAusiliariaBean> listOfDitteAusiliarie, List<AggiudicatarioBean> listOfAggiudicatari){
	   Map<String, List<DittaAusiliariaBean>> ditteByAggiud = raggruppaDittePerAggiudicatario(listOfDitteAusiliarie);
	   for(AggiudicatarioBean agg: listOfAggiudicatari){
	       String key = agg.getSoggettoPartecipante().getCodiceFiscale() + agg.getSoggettoPartecipante().getId_stato();
	       if(ditteByAggiud.containsKey(key))
	           agg.setDitteAusiliarie(ditteByAggiud.get(key));
	       else if(agg.getCfAusiliaria() != null && agg.getCfAusiliaria().trim().length() > 0)
	           agg.setDitteAusiliarie(getDittaOldFashion(agg));
	   }
	   
	}
	
}
