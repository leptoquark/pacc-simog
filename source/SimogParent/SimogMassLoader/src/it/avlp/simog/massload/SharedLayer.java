package it.avlp.simog.massload;

import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.CigException;
import it.avlp.simog.flusso.OperazioneScheda;
import it.avlp.simog.massload.actions.GenericSchedeAction;
import it.avlp.simog.massload.bean.IdsSchedaXML;
import it.avlp.simog.massload.caricamento.CaricamentoBusiness;
import it.avlp.simog.massload.esito.EsitiOperazioneControllo;
import it.avlp.simog.massload.esito.EsitoOperazioneControlloBean;
import it.avlp.simog.massload.esito.EsitoOperazioneValidateAnaPartecipanti;
import it.avlp.simog.massload.esito.EsitoOperazioneValidateAnaResposabili;
import it.avlp.simog.massload.separate.SeparatorBusiness;
import it.avlp.simog.massload.util.FeedBackWriterBase;
import it.avlp.simog.massload.util.FeedBackWriterValidationsBeans;
import it.avlp.simog.massload.util.MassloaderCleaner;
import it.avlp.simog.massload.util.conversion.ConvertXMLtoBeanBusiness;
import it.avlp.simog.massload.validation.MassloaderValidator;
import it.avlp.simog.massload.xmlbeans.AggiudicatariType;
import it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.ResponsabiliType;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * Classe per le operazioni comuni tra operazioni di inserimento e quelle di modifica
 * ovvero la validazione formale e la separazione
 * delle schede da modificare e quelle da inserire.s
 * 
 * @author vletizia
 *
 */
public class SharedLayer {

	// oggetti condivisi dai diversi layer per questo vengono passati come argomento
	
	private Connection con;
	private Logger logger;
	private FeedBackWriterValidationsBeans feedBackWriter;
	private FeedBack feedback;
	private String user;
	
	private String cig;
	
	
	public int numeroSchedeInErrore = 0;
	
	// serve a gestire la gestione delle schede a livello cig (rollback e non processamento)
	public ArrayList<String> listOfCigNonValidi;
	
	// oggetti da recuperare una volta effettuata l'operazione
	private List<SoggettoPartecipanteBean> listOfAnaPartecipanti;
	private List<SoggettoResponsabileBean> listOfAnaResponsabili;
	
	
	public SharedLayer(Connection con, Logger logger,FeedBackWriterValidationsBeans feedBackWriter, FeedBack feedback,String user) {
		this.con = con;
		this.logger = logger;
		this.feedBackWriter = feedBackWriter;
		this.feedback = feedback;
		this.user = user;
	}
	
	/**
	 * Metodo che si occupa di eseguire:</p>
	 * - la validazioe delle anagrafiche</p>
	 * - la validazione formale (con relativa rimozione di componenti non valide)</p>
	 * - la separazione delle schede da inserire da quelle da modificare</p>
	 * 
	 * @param listOfSchede
	 * @param at
	 * @param rt
	 * @param converter
	 * @param loader
	 * @param action
	 * @param validator
	 * @return
	 */
	public Map<Integer, ArrayList<IdsSchedaXML>> eseguiOperazione(List<DatiAggiudicazioneType> listOfSchede, AggiudicatariType at, ResponsabiliType rt,
			ConvertXMLtoBeanBusiness converter,CaricamentoBusiness loader, GenericSchedeAction action, MassloaderValidator validator){
		Map<Integer, ArrayList<IdsSchedaXML>> listeSeparate = null;
		try{
//			Qui NON Serve solo select
//			con.setAutoCommit(false);
			// oggetto che si occupa della rimozione degli oggetti non validi
			MassloaderCleaner cleaner = new MassloaderCleaner(validator);		
			// controllo le anagrafiche se queste non valide non proseguo.. quindi
			EsitoOperazioneValidateAnaPartecipanti esitPart = cleaner.validaAnagrafichePartecipanti(at, converter);
			// NOTA: VISTO CHE LA VALIDAZIONE DELLA ANAGRAFICA DI UN RESPONSABILE PRATICAMENTE TIRA FUORI SOLO WARNING
			// QUESTO CASO E' REMOTO..( VALIDAZIONE FALLITA SULLA ANA RESPONSABILE )
			EsitoOperazioneValidateAnaResposabili esitResp = cleaner.validaAnagraficheResponsabili(rt, converter);
			
			listOfAnaPartecipanti = null;
			listOfAnaResponsabili = null;
			
			if(!esitPart.isEsitoOperazione() || !esitResp.isEsitoOperazione()){
				
				if(!esitPart.isEsitoOperazione()){
					logger.debug("Esiste almeno una anagrafica \"Partecipante\" NON valida.");
					feedBackWriter.fillStandardFeedBack(feedback, esitPart.getListOfValidation(), OperazioneScheda.INSERIMENTO, null, null, null, 0, null, null);
				}if(!esitResp.isEsitoOperazione()){
					logger.debug("Esiste almeno una anagrafica \"Incaricato\" NON valida.");
					feedBackWriter.fillStandardFeedBack(feedback, esitResp.getListOfValidation(), OperazioneScheda.INSERIMENTO, null, null, null, 0, null, null);
				}
				throw new MassLoaderInterruptException("Sono state rilevate della anagrafiche non valide si interrompono dunque le operazione di inserimento/modifica sul file xml corrente");
			
			}else{
				// manca la scrittura di warning relative ai duplicati delle anagrafiche.
				listOfAnaPartecipanti = esitPart.getListOfValidAnaPartecipante();
				listOfAnaResponsabili = esitResp.getListOfValidAnaResponsabile();
				
				// PATCH - VL - CONTROLLO DI NON NULLITA'..
				if(esitPart.getListOfValidation() != null && !esitPart.getListOfValidation().isEmpty()){
					feedBackWriter.fillStandardFeedBack(feedback, esitPart.getListOfValidation(), OperazioneScheda.INSERIMENTO, null, null, null, 0, null, null);				
				}
				if(esitResp.getListOfValidation() != null && !esitResp.getListOfValidation().isEmpty()){
					feedBackWriter.fillStandardFeedBack(feedback, esitResp.getListOfValidation(), OperazioneScheda.INSERIMENTO, null, null, null, 0, null, null);
				}
			}
			
			if (listOfSchede != null) {
			   List<IdsSchedaXML> listOfIdScheda = action.caricaFlusso(listOfSchede, this.numeroSchedeInErrore);
   			
      			// rimozione componenti(Scheda completa Livello CIG) non formalmente corrette				
      			EsitiOperazioneControllo esitoControllo = cleaner.rimuoviPartiFormalmenteNonValide(listOfIdScheda, at, rt, action);
   			
      			List<IdsSchedaXML> listaSchedeValide = new ArrayList<IdsSchedaXML>();
      			ArrayList<EsitoOperazioneControlloBean> listOfEsito = esitoControllo.getListOfEsiti();
      			for(EsitoOperazioneControlloBean esito : listOfEsito){
      				if(esito.isEsitoOperazione()){
      					logger.debug("Rilevata scheda valida");
      					listaSchedeValide.add(esito.getSchedaCorrente());
      				}else{
      					logger.debug("Rilevata scheda NON valida");
      					
      					List<SchedaSpecificaValidationBean> listOfValidations = esito.getAllValidationBeans();
      					// patch - vl - 27-01-2010 qui ancora non so l'operazione di competenza quindi null per l'operazione
      					feedBackWriter.fillMassloaderFeedBack(feedback, listOfValidations, null);
      					
      					if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
      					this.listOfCigNonValidi.add(esito.getSchedaCorrente().getCig());
      				}
      			}
      			
      			// Se sono presenti schede valide dopo la validazione formale
      			if(listaSchedeValide != null && listaSchedeValide.size() > 0){
      				
      				SeparatorBusiness separator = new SeparatorBusiness();
      				listeSeparate = separator.separaSchede(listaSchedeValide);
      			}
      //			logger.debug("\r\n\t\t\t####\r\nInserimento-Modifica FeedBack..:\r\n" +feedback.toString()+"\r\n\t\t\t####\r\n");
   			}
   			return listeSeparate;
			
		// eccezione lanciata unicamente dal fallimento della validazione delle anagrafiche @see line 109
		}catch(MassLoaderInterruptException mlie){
			mlie.printStackTrace();
			// incremento il contatore degli errori (comunque in questo caso i valori saranno elaborate 1 e errore 1 visto che si skippa tutto il file)
			this.numeroSchedeInErrore++;
			
			// PP 20.11.2015
         if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
         if (!this.listOfCigNonValidi.contains(cig))
            this.listOfCigNonValidi.add(cig);
			
         feedBackWriter.fillMessaggioErroreAnagrafiche(feedback, cig);
			return listeSeparate;
		// patch - vl - eccezione cig malformato
		}catch(CigException cige){
			// tolto di mezzo tanto va sul feedback cige.printStackTrace();
			// incremento il contatore degli errori (comunque in questo caso i valori saranno elaborate 1 e errore 1 visto che si skippa tutto il file)

		   // PP 20.11.2015
         if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
         if (!this.listOfCigNonValidi.contains(cig))
            this.listOfCigNonValidi.add(cig);
			
         this.numeroSchedeInErrore++;
			feedBackWriter.fillMessaggioErroreCigDatiComuni(feedback, cige.getCig(), cige.getCui(), cige.getProgressivo(), cige.getMessage());
			return listeSeparate;
		}catch(Exception e){
			logger.fatal("Eccezione: " + e.getMessage());
			e.printStackTrace();
			//  try catch applicativo.. costruttore di validation bean un po' mongoloide 
			try{
	            String mesg = e.getMessage() + " - " + FeedBackWriterBase.getStack(e);
	            throw new Exception(mesg);
//			   String mesg = Messaggi.SIMOG_MASSLOADER_205 + " - " + e.getClass().getName() + " - " + FeedBackWriterBase.getStack(e);
//				
//				ValidationBean validation = new ValidationBean(mesg,ValidationBean.VALBEAN_SEV_ERR,0);
//				List<ValidationBean> list = new ArrayList<ValidationBean>();
//				list.add(validation);
//				feedBackWriter.fillStandardFeedBack(feedback, list, "", "", "", "", 0, "", "");
			}catch(Exception e1){}

			return listeSeparate;
		}
	}

	public List<SoggettoPartecipanteBean> getListOfAnaPartecipanti() {
		return listOfAnaPartecipanti;
	}

	public List<SoggettoResponsabileBean> getListOfAnaResponsabili() {
		return listOfAnaResponsabili;
	}
	
	/**
	 * Metodo che si occupa di filtrare tra tutte le "schede" ricevute le schede che hanno provocato quale errore
	 * in una qualunque delle operazioni.
	 * 
	 * @param all
	 * @return
	 */
	public DatiAggiudicazioneType[] filterSchedePerEliminareICigNonValidi(DatiAggiudicazioneType[] all, ArrayList<String> listOfCigNonValidi){
		ArrayList<DatiAggiudicazioneType> listOfDatiFiltrati = new ArrayList<DatiAggiudicazioneType>();
		for(int i = 0; i < all.length; i++){
			// se non e' contenuto nella lista dei cig non validi aggiungi alla lista dei filtrati
			if(!listOfCigNonValidi.contains(all[i].getDatiComuni().getCIG())){
				listOfDatiFiltrati.add(all[i]);
			}else{
				this.numeroSchedeInErrore++;
			}
		}
		return(DatiAggiudicazioneType[]) listOfDatiFiltrati.toArray(new DatiAggiudicazioneType[listOfDatiFiltrati.size()]);
	}
	
	/**
	 * Filtra le liste eliminando i cig non validi
	 * 
	 * @param listeSeparate
	 * @param listOfCigNonValidi
	 * @return
	 */
	public Map<Integer, ArrayList<IdsSchedaXML>> filterSchedePerEliminareICigNonValidi(Map<Integer, ArrayList<IdsSchedaXML>> listeSeparate, ArrayList<String> listOfCigNonValidi){
		Map<Integer, ArrayList<IdsSchedaXML>> listeSeparateEfiltrate = new TreeMap<Integer, ArrayList<IdsSchedaXML>>();
		if(listeSeparate != null){
			Set<Integer> keys = listeSeparate.keySet();
			for(Integer currentKey : keys){
				listeSeparateEfiltrate.put(currentKey, filterSchedePerEliminareICigNonValidi(listeSeparate.get(currentKey), listOfCigNonValidi));
			}
		}
		return listeSeparateEfiltrate;
	}

	/**
	 * Filtra le liste eliminando i cig non validi
	 * 
	 * @param listeSeparate
	 * @param listOfCigNonValidi
	 * @return
	 */
	public ArrayList<IdsSchedaXML> filterSchedePerEliminareICigNonValidi(ArrayList<IdsSchedaXML> lista, ArrayList<String> listOfCigNonValidi){
		ArrayList<IdsSchedaXML> listaFiltrata = new ArrayList<IdsSchedaXML>();
		for(IdsSchedaXML ids : lista){
			// se sono presenti i dati comuni prendo il cig da li'
			if(ids.getScheda().getDatiComuni() != null){
				if(!listOfCigNonValidi.contains(ids.getScheda().getDatiComuni().getCIG())){
					listaFiltrata.add(ids);
				}else{
					this.numeroSchedeInErrore++;
				}
			// altrimenti lo prendo dal contenitore
			}else{
				if(!listOfCigNonValidi.contains(ids.getCig())){
					listaFiltrata.add(ids);
				}else{
					this.numeroSchedeInErrore++;
				}
			}
		}
		return listaFiltrata;
	}

	public String getCig() {
		return cig;
	}

	public void setCig(String cig) {
		this.cig = cig;
	}
}
