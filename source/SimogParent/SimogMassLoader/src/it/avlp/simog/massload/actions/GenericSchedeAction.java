package it.avlp.simog.massload.actions;

import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avlp.simog.beans.EsitoControlloStatiSchede;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.Rubrica;
import it.avlp.simog.beans.RubricaResponsabili;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.beans.StatoScheda;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.comparators.SoggettiPartecipantiComparator;
import it.avlp.simog.beans.comparators.SoggettiResponsabiliComparator;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.inizio.PosizioneAggiudicatarioBean;
import it.avlp.simog.beans.subappalti.SubappaltatoreBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.CigException;
import it.avlp.simog.flusso.OperazioneScheda;
import it.avlp.simog.flusso.WorkFlowController;
import it.avlp.simog.flusso.bean.CrossedFields;
import it.avlp.simog.flusso.business.LoadSituazioneBusiness;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.massload.bean.IdsSchedaXML;
import it.avlp.simog.massload.caricamento.CaricamentoBusiness;
import it.avlp.simog.massload.esito.EsitoOperazioneControlloLogico;
import it.avlp.simog.massload.util.CigTool;
import it.avlp.simog.massload.util.FeedBackWriterValidationsBeans;
import it.avlp.simog.massload.util.conversion.SituazioneAttualeSchedeXml;
import it.avlp.simog.massload.util.conversion.XmlSituationParser;
import it.avlp.simog.massload.xmlbeans.AppaltoAdesioneType;
import it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.DatiComuniType;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.TipiSchedeType;
import it.avlp.simog.massload.xmlbeans.TrasferimentoDatiDocument.TrasferimentoDati;
import it.avlp.simog.rubricamanager.RubricaManager;
import it.avlp.simog.rubricamanager.RubricaResponsabiliManager;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.TipoFlusso;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class GenericSchedeAction {

	protected Connection con;
	protected Logger logger;
	protected ArrayList<SchedaSpecificaValidationBean> esitiOperazioni;
	protected OrigineSchedaEnum origine;
	
	public GenericSchedeAction(Connection con, Logger logger, OrigineSchedaEnum origine){
		this.con = con;
		this.logger = logger;
		this.origine = origine;
	}
	
	protected void addError(StatoScheda statoSchedaCorrente, String nomeScheda,
			String messaggio) {
		this.esitiOperazioni.add(SchedaSpecificaValidationBean
				.getThisKindOfValidationBeanErr(statoSchedaCorrente, 0,
						0, 0, nomeScheda, messaggio));
	}
	
	private EsitoControlloStatiSchede controllaFlussoRiaggiudicato(SituazioneSchedeAttuale situazioneAttuale){
		EsitoControlloStatiSchede esito = new EsitoControlloStatiSchede();
		boolean notRiaggiud = true;
		AggiudicazioniManager man = new AggiudicazioniManager(con, logger);
		try{
			StatoScheda datiComuni = situazioneAttuale.getStatoDatiComuni();
			StatoScheda aggiudicazione = situazioneAttuale.getStatoAggiudicazioneSottotipo(); //una delle tipologie di scheda A
			
			if(datiComuni.isEsistenteDb() && aggiudicazione.isEsistenteDb()){
				int progCui = getProgCui(aggiudicazione.getCui());
				if(progCui > 0)
					notRiaggiud = !man.isRevocataWithNewAgg(datiComuni.getIdRecord(), datiComuni.getDataInizioRecord(), progCui);
				else 
					notRiaggiud = true;
			}
			else
				notRiaggiud = true;
		if(!notRiaggiud){
			esito.getListOfValidationBeans().add(SchedaSpecificaValidationBean.getThisKindOfValidationBeanErr(situazioneAttuale.getStatoAggiudicazioneSottotipo(), 0, 0, 0, IdentificativoSchede.AGGIUDICAZIONE, Messaggi.SIMOG_MASSLOADER_206));
		}
		}catch (Exception e) {
			e.printStackTrace();
			esito.setEccezioneLocale(e);
		}
		esito.setEsitoOperazione(notRiaggiud);
		
		return esito;
		
	}
	private int getProgCui(String cui)throws Exception {
		if(cui == null || cui.trim().length() == 0)
			return -1;
		return Integer.parseInt(cui.substring(cui.length() -1));
	}


	/**
	 * Controlla che nel flusso presente nel db non ci siano schede "in definizione" o "in richiesta"
	 * XXX: E' qui che occorre intervenire nel caso in cui si decida che le schede in definizione vanno bene
	 * 
	 * @param listOfSituazioni
	 * @return
	 */
	public EsitoControlloStatiSchede controllaFlussoPreInserimento(SituazioneSchedeAttuale situazioneAttuale, String cig, String cui){
//		EsitoOperazioneBean esito = new EsitoOperazioneBean();
		
		WorkFlowController workFlow = new WorkFlowController();
		// il secondo parametro non serve a nulla..
		boolean isInseribile = false;
		EsitoControlloStatiSchede esitoDefinitivo = new EsitoControlloStatiSchede();
		
		EsitoControlloStatiSchede esitoStatiRichiesta = workFlow.isNotFlussoInRichiesta(situazioneAttuale, null, cig , cui);
		isInseribile = esitoStatiRichiesta.isEsitoOperazione();
		if(!isInseribile) esitoDefinitivo.addListOfValidationBeans(esitoStatiRichiesta.getListOfValidationBeans());
		

		EsitoControlloStatiSchede esitoStatiDefinizione = workFlow.isNotFlussoInDefinizione(situazioneAttuale);
		
		isInseribile = esitoStatiDefinizione.isEsitoOperazione();
		if(!isInseribile) esitoDefinitivo.addListOfValidationBeans(esitoStatiDefinizione.getListOfValidationBeans());
		if(isInseribile){
			EsitoControlloStatiSchede esito = controllaFlussoRiaggiudicato(situazioneAttuale);
			if(!esito.isEsitoOperazione()){
				esitoDefinitivo.addListOfValidationBeans(esito.getListOfValidationBeans());
				isInseribile = false;
				esitoDefinitivo.setEsitoOperazione(false);
			}
		}
		
		esitoDefinitivo.setEsitoOperazione(isInseribile);
		return esitoDefinitivo;
	}
	public EsitoOperazioneControlloLogico controllaEsistenzaRiaggiudicazione(IdsSchedaXML kiaveCorrente){
		EsitoOperazioneControlloLogico ecl = new EsitoOperazioneControlloLogico();
		boolean allValido = true;
		CaricamentoBusiness loader = new CaricamentoBusiness(con, logger);
		for(SchedaCompletaType sc : kiaveCorrente.getScheda().getSchedaCompletaArray()){
			if(sc.getAggiudicazione().getAppalto().getPROGCUIRIAGGIUDICATO() > 0){
				String cuiToCheck = kiaveCorrente.getCig() + "-" + sc.getAggiudicazione().getAppalto().getPROGCUIRIAGGIUDICATO();
				AggiudicazioneBean bean;
				try {
					bean = loader.caricaAggiudicazione(cuiToCheck);
					//se non trovo l'aggiudicazione invio un errore
					if(bean == null || bean.getIdAggiudicazione() < 1){
						ecl.setEsitoOperazione(false);
						allValido = false;
						ecl.getListOfValidationsBeans().add(SchedaSpecificaValidationBean.getThisKindOfValidationBeanErr(kiaveCorrente.getSituazioneAttuale().getStatoAggiudicazioneSottotipo(), 0, 0, 0, IdentificativoSchede.AGGIUDICAZIONE, Messaggi.SIMOG_MASSLOADER_210));
						ecl.addListOfCigNonValidi(kiaveCorrente.getCig());
					}
					//altrimenti controllo che esista una scheda conclusione per l'aggiudicazione corrente
					else{
						ConclusioneBean conclusione = null;
						conclusione = loader.caricaConclusione(bean.getIdAggiudicazione(), bean.getDataInizioAggiudicazione());
						if(conclusione == null || conclusione.getIdUltim() < 1){
							ecl.setEsitoOperazione(false);
							allValido = false;
							ecl.getListOfValidationsBeans().add(SchedaSpecificaValidationBean.getThisKindOfValidationBeanErr(kiaveCorrente.getSituazioneAttuale().getStatoAggiudicazioneSottotipo(), 0, 0, 0, IdentificativoSchede.AGGIUDICAZIONE, Messaggi.SIMOG_MASSLOADER_173.replace("$1","Conclusioni").replace("$2", "riaggiudicare la Scheda Aggiudicazione corrente senza prima inserire").replace("$3",IdentificativoSchede.FINE_LAVORI)));
							ecl.addListOfCigNonValidi(kiaveCorrente.getCig());
						}
						else{
					        //gm controllo se esistono già riaggiudicazioni per questa scheda
				            List<AggiudicazioneBean> beans;
				        	//carico tutte le aggiudicazioni in base al cig corrente
				        	beans = loader.caricaAggiudicazioniByCIG(kiaveCorrente.getCig());
				        	//se non esistono, posso riaggiudicare, altrimenti vado avanti con i controlli
			        		if(beans != null && beans.size()>0){
				 	        	//creo un'aggiudicazione di riferimento, sarà l'ultima creata per il cig
			  	    	    	AggiudicazioneBean aggUltima = null;
			    		    	for(AggiudicazioneBean agg : beans){
				    	    		if(aggUltima == null)
				    		    		aggUltima = agg;
				    		    	else{
				    		    		if(agg.getProgCUI() > aggUltima.getProgCUI())
				    			    		aggUltima = agg;
				    		    	}
				        		}
				    	    	//controllo che l'ultima aggiudicazione sia quella che vogliamo riaggiudicare, altrimenti sollevo un errore
				    	    	if(sc.getAggiudicazione().getAppalto().getPROGCUIRIAGGIUDICATO() != aggUltima.getProgCUI()){
				    		    	ecl.setEsitoOperazione(false);
				    		    	allValido = false;
					    	    	ecl.getListOfValidationsBeans().add(SchedaSpecificaValidationBean.getThisKindOfValidationBeanErr(kiaveCorrente.getSituazioneAttuale().getStatoAggiudicazioneSottotipo(), 0, 0, 0, 
				    				IdentificativoSchede.AGGIUDICAZIONE, Messaggi.SIMOG_MASSLOADER_178.replace("$1", "Aggiudicazione").replace("$2", "Aggiudicazione nuova. Eliminare prima la scheda Aggiudicazione nuova.").replace("$3", "riaggiudicata")));
				    		    	ecl.addListOfCigNonValidi(kiaveCorrente.getCig());
					        	}					
				        	}
		    	    	}
					}
				}
				catch (Exception e) {
					
					e.printStackTrace();
					
					ecl.setEsitoOperazione(false);
					return ecl;
				}		
			}	
		}
		if(allValido){
			ecl.addSchedaValida(kiaveCorrente);
			ecl.setEsitoOperazione(true);
		}
		return ecl;		
	}
	
	public EsitoOperazioneControlloLogico controllaEsistenzaAggiudicazione(IdsSchedaXML kiaveCorrente){
		
		EsitoOperazioneControlloLogico ecl = new EsitoOperazioneControlloLogico();
		boolean allValido = true;
		CaricamentoBusiness loader = new CaricamentoBusiness(con, logger);
		for(SchedaCompletaType sc : kiaveCorrente.getScheda().getSchedaCompletaArray()){
			//se la scheda xml da inserire non è una riaggiudicazione vado avanti
			
			//gm, per aggiudicazioni soprasoglia controllo anche che non si richieda la riaggiudicazione
			if((sc.getAggiudicazione()!=null && sc.getAggiudicazione().getAppalto()!=null && sc.getAggiudicazione().getAppalto().getPROGCUIRIAGGIUDICATO() == 0) ||
				sc.getSottosoglia()!=null || sc.getEscluso()!=null || sc.getAdesione()!=null){
				List <AggiudicazioneBean> beans = null;
				try {
					beans = loader.caricaAggiudicazioniByCIG(kiaveCorrente.getCig());
					//se già esiste un'aggiudicazione inserita
					if(beans != null && beans.size()>0){
						ecl.setEsitoOperazione(false);
						allValido = false;
						if(sc.getSottosoglia()!=null){
						    ecl.getListOfValidationsBeans().add(SchedaSpecificaValidationBean.getThisKindOfValidationBeanErr(kiaveCorrente.getSituazioneAttuale().getStatoAggiudicazioneSottotipo(), 0, 0, 0, IdentificativoSchede.AGGIUDICAZIONE, Messaggi.SIMOG_MASSLOADER_178.replace("$1", "Aggiudicazione sotto la soglia dei 150.000 euro").replace("$2", "Aggiudicazione").replace("$3", "inseribile")));
						}
						else if(sc.getEscluso()!=null){
						    ecl.getListOfValidationsBeans().add(SchedaSpecificaValidationBean.getThisKindOfValidationBeanErr(kiaveCorrente.getSituazioneAttuale().getStatoAggiudicazioneSottotipo(), 0, 0, 0, IdentificativoSchede.AGGIUDICAZIONE, Messaggi.SIMOG_MASSLOADER_178.replace("$1", "Contratti esclusi in tutto o in parte dall'ambito dell'applicazione del codice").replace("$2", "Aggiudicazione").replace("$3", "inseribile")));
						}
						else if(sc.getAdesione()!=null){
						    ecl.getListOfValidationsBeans().add(SchedaSpecificaValidationBean.getThisKindOfValidationBeanErr(kiaveCorrente.getSituazioneAttuale().getStatoAggiudicazioneSottotipo(), 0, 0, 0, IdentificativoSchede.AGGIUDICAZIONE, Messaggi.SIMOG_MASSLOADER_178.replace("$1", "Adesione").replace("$2", "Adesione").replace("$3", "inseribile")));
						}
						else{
						    ecl.getListOfValidationsBeans().add(SchedaSpecificaValidationBean.getThisKindOfValidationBeanErr(kiaveCorrente.getSituazioneAttuale().getStatoAggiudicazioneSottotipo(), 0, 0, 0, IdentificativoSchede.AGGIUDICAZIONE, Messaggi.SIMOG_MASSLOADER_178.replace("$1", "Aggiudicazione").replace("$2", "Aggiudicazione").replace("$3", "inseribile")));
						}
						ecl.addListOfCigNonValidi(kiaveCorrente.getCig());
					}
				} catch (Exception e) {
					
					e.printStackTrace();
					
					ecl.setEsitoOperazione(false);
					return ecl;
				}
			}		
		}
		if(allValido){
			ecl.addSchedaValida(kiaveCorrente);
			ecl.setEsitoOperazione(true);
		}
		return ecl;
	}

	/**
	 * Metodo che si occupa di caricare la "situazione attuale" ovvero quali e in quale stato
	 * sono i dati sul db, ma anche la "situazione attuale" dei dati xml, inoltre modifica
	 * gli oggetti in ingresso in modo da avere una cardinalita' 1 a 1 tra cig e cui,
	 * avremo dunque la possibilita' che la lista ritornata sia di dimensioni maggiori
	 * della lista in ingresso.
	 * 
	 * <strong>Attenzione !</strong>
	 * Ho introdotto un controllo che mi permette di gestire il caso di piu schede (livello cig)
	 * con lo stesso cig.
	 * Siccome il caricamento della situazione del db viene fatto a monte devo fare in modo
	 * che lo stato della situazione per lo stesso cig sia "sincronizzata" tra tutti gli oggetti
	 * che lo condividono, in modo da rilevare i cambiamenti di stati senza dover ricaricare ogni
	 * volta la situazione del DB.
	 * 
	 * NOTA: questo codice suppone che ad ogni operazione di inserimento e cancellazione si aggiorni
	 * 		la situazione senza far perdere la sincronizzazione (ereditarieta')
	 *  
	 * @param listOfSchede
	 * @return
	 */
	public ArrayList<IdsSchedaXML> caricaFlusso(List<DatiAggiudicazioneType> listOfSchede, int numErr) throws Exception, CigException{
		
		XmlSituationParser xmlSituationParser = new XmlSituationParser();
		LoadSituazioneBusiness loader = new LoadSituazioneBusiness(con, logger, this.origine);
		
		ArrayList<IdsSchedaXML> listOfKeys = new ArrayList<IdsSchedaXML>();
		int counterSchedeCIG = 1;
		Map<String,ArrayList<SituazioneSchedeAttuale>> mapOflistOfSituazioniPerCigUguali = null;
		CigTool cigTool = new CigTool();
		for(DatiAggiudicazioneType schedaCorrente : listOfSchede){	

			int numeroSchedeComplete = xmlSituationParser.containsSchedaCompleta(schedaCorrente);
			if( numeroSchedeComplete > 0){
			
			List<SchedaCompletaType> listOfSchedaCompleta = Arrays.asList(schedaCorrente.getSchedaCompletaArray());

				int counterSchedeComplete = 1;
				for(SchedaCompletaType schedaCompletaCorrente : listOfSchedaCompleta){
					IdsSchedaXML idScheda = new IdsSchedaXML();
					
					// gestione campi crossed
					CrossedFields crossFields = null;
					try{
			          String localCui = schedaCorrente.getSchedaCompletaArray().length > 0 ? schedaCorrente.getSchedaCompletaArray(0).getCUI() : "";

						 crossFields = loader.loadCrossedFieldsForInfoComuni(schedaCorrente.getDatiComuni().getCIG(), localCui);
					}catch(SQLException sqle){
						// forward sqlexception
						throw sqle;
					
					// eccezione nel caso in cui il controllo formale effettuato sul cig ha esito negativo
                    }catch(CigException e){
                       // PP 3.02.3.1 provo ad andare avanti, ma incremento il numero di schede in errore
                       // perche' non viene settato altrimenti
                       numErr++;
                     throw (e);
                       
					// eccezione imprevista
					}catch(Exception e){
						// crea un'eccezione ad hoc che mi permette un feedback
						String cig = schedaCorrente.getDatiComuni().getCIG();
						String cui = schedaCompletaCorrente.getCUI() != null && !"".equals(schedaCompletaCorrente.getCUI()) ? schedaCompletaCorrente.getCUI() : null;
						throw new CigException(cig,cui,counterSchedeComplete, e );
					}
					idScheda.setCrossFieldsForInfoComuniValidation(crossFields);

					// upper level
					idScheda.setCig(schedaCorrente.getDatiComuni().getCIG());
					DatiAggiudicazioneType datiModificati = xmlSituationParser.creaDatiAggiudicazione(schedaCompletaCorrente, schedaCorrente.getDatiComuni(), schedaCorrente.getPubblicazione());
					idScheda.setScheda(datiModificati);
					SituazioneAttualeSchedeXml xmlSituazione = xmlSituationParser.parse(datiModificati);
					idScheda.setSituazioneAttualeXml(xmlSituazione);

//					boolean isPresentSomeIdLocale = xmlSituazione.thereIsSomeIdLocale();
					SituazioneSchedeAttuale situazioneAttuale = null;
					
					// se esite il cui
					if(schedaCompletaCorrente.getCUI() != null && !"".equals(schedaCompletaCorrente.getCUI())){
						// pur troppo il caricamento della situazione per il cui corrente e' fatta prima del controllo formale che
						try{
							
							situazioneAttuale = loader.loadSituazioneByCUI(schedaCompletaCorrente.getCUI());
// XXX: VL Controllare di chi e' la competenza per settare il flag che indica la provenienza dal xml per tutte le schede, altrimenti modifica le 
//          condizioni per le schede per essere validate
							
//							if(schedaCompletaCorrente.isSetAggiudicazione()){
//								situazioneAttuale.getStatoAggiudicazione().setFromXml(true);
//							}
							
						}catch(Exception e){
							logger.debug("Rilevato CUI non valido in fase di caricamento della situazione attuale sara' formalizzato in fase di controllo formale");
						}

					// se invece non e' presente il cui
					}
					else{
						/**
						  * <strong>Attenzione !</strong>
						  * Porzione di codice sensibile, vedi l'attenzione nel javadoc del metodo.
						  * Nota che qui non devono essere bindati in modo ereditario le schede senza cui, altrimenti per l'inserimento di quella
						  * successiva alla prima ritrovo i dati della precendente, ed erroroneamente ne deduce che  le schede sono gia presenti
						  * Solo i dati comuni devono essere bindati altrimenti inserisce di nuovo dati comuni !!
						  **/
						ArrayList<SituazioneSchedeAttuale> listOfSituazioni  = null;
						// se l'operazione non e' mai stata effettuata per il cig corrente
						if(!cigTool.isAlreadyOperazioneEffettuata(schedaCorrente.getDatiComuni().getCIG())){
							// se la mappa e' null
							if(mapOflistOfSituazioniPerCigUguali == null) mapOflistOfSituazioniPerCigUguali = new TreeMap<String, ArrayList<SituazioneSchedeAttuale>>();
							
							// calcola la situazione, memorizzala in una mappa, e assegna alla lista che sara' settata nel bean IdSchedaXmL
							ArrayList<SituazioneSchedeAttuale> listOfSituazioniTemp = loader.loadSituazioneByCIG(schedaCorrente.getDatiComuni().getCIG());
							SituazioneSchedeAttuale situazioneTemp = new SituazioneSchedeAttuale();
							
							// se esistono i dati comuni, condividili
							if(listOfSituazioniTemp.get(0).getStatoDatiComuni().getIdRecord() > 0){
								
								// una situazione vale l'altra per info comuni condivisi ma valido solo nel caso di id presenti
								if(xmlSituazione.isPresentDatiComuni() && (xmlSituazione.isPresentDatiComuniIdLocale() || xmlSituazione.isPresentDatiComuniIdSimog() )){
									situazioneTemp.setStatoDatiComuni(listOfSituazioniTemp.get(0).getStatoDatiComuni());
								
									// altrimenti per forzare la cancellazione impongo tutte le schede esistenti
								}else{
									// questo serve a far capire all'applicativo che deve effettuare la cancellazione per cig
									situazioneTemp = SituazioneSchedeAttuale.getSituazionePresenteAllInDb();
								}
							}	
							// azzero la lista
							listOfSituazioniTemp = new ArrayList<SituazioneSchedeAttuale>();
							// aggiungo l'elemento
							listOfSituazioniTemp.add(situazioneTemp);
							// aggiungo alla mappa che memorizza le situazioni associate al cig
							mapOflistOfSituazioniPerCigUguali.put(schedaCorrente.getDatiComuni().getCIG(), listOfSituazioniTemp);
							// assegna la lista temporanea all'altra lista
							listOfSituazioni = listOfSituazioniTemp;
							// memorizza in cigtool il fatto che loperazione per il cig e' gia stata effettuata
							cigTool.add(schedaCorrente.getDatiComuni().getCIG(), true);
						// altrimenti recupera semplicemente la situazione (dei soli dati comuni in questo caso) dalla mappa (che qui e' sicuramente instanziata)	
						}else{
							// Importante lega in modo ereditario gli info comuni tra le varie schede complete.. altrimenti duplicazione di info_comuni
							StatoScheda statoDatiComuni = mapOflistOfSituazioniPerCigUguali.get(schedaCorrente.getDatiComuni().getCIG()).get(0).getStatoDatiComuni();
							SituazioneSchedeAttuale situazioneTemp = new SituazioneSchedeAttuale();
							situazioneTemp.setStatoDatiComuni(statoDatiComuni);
							ArrayList<SituazioneSchedeAttuale> listOfSituazioniTemp = new ArrayList<SituazioneSchedeAttuale>();
							listOfSituazioniTemp.add(situazioneTemp);
							listOfSituazioni = listOfSituazioniTemp;
							
						}

						if(listOfSituazioni.size() == 1) 
						   situazioneAttuale = listOfSituazioni.get(0);
						else{
							throw new Exception("Situazione anomala.. risultano presenti 0 o piu di una situazione attuale!");
						}
					}
					idScheda.setSituazioneAttuale(situazioneAttuale);
					// lower lever
					idScheda.setCui(schedaCompletaCorrente.getCUI());
					idScheda.setPresentSomeSchedaCompleta(true);
	
					idScheda.setCardinalitaSchedaCig(counterSchedeCIG);
					idScheda.setCardinalitaSchedaCompleta(counterSchedeComplete);
					
					listOfKeys.add(idScheda);
					
					counterSchedeComplete++;
				}
			}else{
				IdsSchedaXML idScheda = new IdsSchedaXML();
				idScheda.setCig(schedaCorrente.getDatiComuni().getCIG());
				idScheda.setScheda(schedaCorrente);
				idScheda.setCardinalitaSchedaCig(counterSchedeCIG);
				SituazioneAttualeSchedeXml xmlSituazione = xmlSituationParser.parse(schedaCorrente);
				idScheda.setSituazioneAttualeXml(xmlSituazione);
				
				/**
				  * <strong>Attenzione !</strong>
				  * Porzione di codice sensibile, vedi l'attenzione nel javadoc del metodo.
				  **/
				ArrayList<SituazioneSchedeAttuale> listOfSituazioni  = null;
				// se l'operazione non e' mai stata effettuata per il cig corrente
				if(!cigTool.isAlreadyOperazioneEffettuata(schedaCorrente.getDatiComuni().getCIG())){
					// se la mappa e' null
					if(mapOflistOfSituazioniPerCigUguali == null) mapOflistOfSituazioniPerCigUguali = new TreeMap<String, ArrayList<SituazioneSchedeAttuale>>();
					
					// calcola la situazione, memorizzala in una mappa, e assegna alla lista che sara' settata nel bean IdSchedaXmL
					ArrayList<SituazioneSchedeAttuale> listOfSituazioniTemp = loader.loadSituazioneByCIG(schedaCorrente.getDatiComuni().getCIG());
					mapOflistOfSituazioniPerCigUguali.put(schedaCorrente.getDatiComuni().getCIG(), listOfSituazioniTemp);
					listOfSituazioni = listOfSituazioniTemp;
					cigTool.add(schedaCorrente.getDatiComuni().getCIG(), true);
				// altrimenti recupera semplicemente la situazione dalla mappa (che qui e' sicuramente instanziata)	
				}else{
					listOfSituazioni = mapOflistOfSituazioniPerCigUguali.get(schedaCorrente.getDatiComuni().getCIG());
				}
				/**
				 * END <attenzione>
				 **/
				// Nel caso in cui qui abbia piu di un'elemento nella lista delle situazioni
				// devo forzare la situazione attuale in modo tale che tutte le schede presenti nel file xml siano
				// presenti anche nel db. perche se associo la situazione di un cui qualunque potrei avere una situazione diversa dalla sit xml
				if(listOfSituazioni == null || listOfSituazioni.size() == 0 || listOfSituazioni.size() > 1){
					
					SituazioneSchedeAttuale situazioneTemp = new SituazioneSchedeAttuale();
					SituazioneSchedeAttuale situazioneForzaCancellazioneEInserimento = SituazioneSchedeAttuale.getSituazionePresenteAllInDb();
					
					// se esistono i dati comuni, condividili
					if(listOfSituazioni.get(0).getStatoDatiComuni().getIdRecord() > 0){
						
						// una situazione vale l'altra per info comuni condivisi ma valido solo nel caso di id presenti
						if(xmlSituazione.isPresentDatiComuni() && (xmlSituazione.isPresentDatiComuniIdLocale() || xmlSituazione.isPresentDatiComuniIdSimog() )){
							situazioneTemp.setStatoDatiComuni(listOfSituazioni.get(0).getStatoDatiComuni());
						
							// altrimenti per forzare la cancellazione impongo tutte le schede esistenti
						}else{
							// questo serve a far capire all'applicativo che deve effettuare la cancellazione per cig
							situazioneTemp = situazioneForzaCancellazioneEInserimento;
						}
					}else{	
						// caso non possibile se esistono diverse "situazioni" ci saranno per forza i dati comuni
						// Nell'eventualita'
						situazioneTemp = new SituazioneSchedeAttuale();
					}
					listOfSituazioni = new ArrayList<SituazioneSchedeAttuale>();
					listOfSituazioni.add(situazioneTemp);
				}
				// gestione campi crossed
            String localCui = schedaCorrente.getSchedaCompletaArray().length > 0 ? schedaCorrente.getSchedaCompletaArray(0).getCUI() : "";
				
            try{
   				CrossedFields crossFields = loader.loadCrossedFieldsForInfoComuni(schedaCorrente.getDatiComuni().getCIG(), localCui);
   				idScheda.setCrossFieldsForInfoComuniValidation(crossFields);
   				idScheda.setSituazioneAttuale(listOfSituazioni.get(0));
   				listOfKeys.add(idScheda);
            }
            catch(CigException e){
                  // PP 3.02.3.1 provo ad andare avanti, ma incremento il numero di schede in errore
                  // perche' non viene settato altrimenti
                  numErr++;
                 throw (e);
            }
         }
			counterSchedeCIG++;
		}
		return listOfKeys;
	}
	/**
	 * Controllo il flusso per tutte le schede del cig corrente..(Schede)
	 * 
	 * @param idScheda
	 * @return
	 */
	protected EsitoOperazioneControlloLogico controllaCorrettezzaFlusso(SituazioneSchedeAttuale situazioneAttuale, OperazioneScheda operazioneCorrente, int progressivoSchedaCompleta, String cig, boolean retro, String user){
		
		EsitoOperazioneControlloLogico esitoFlusso = new EsitoOperazioneControlloLogico();
		esitiOperazioni = new ArrayList<SchedaSpecificaValidationBean>();
		boolean esito = workFlowCaller(situazioneAttuale, operazioneCorrente, progressivoSchedaCompleta,cig, retro, user);
		esitoFlusso.setEsitoOperazione(esito);
		
		if(!esito) 	esitoFlusso.setListOfValidationsBeans(esitiOperazioni);
		if(esito){
			EsitoControlloStatiSchede esitoRiagg = controllaFlussoRiaggiudicato(situazioneAttuale);
			if(!esitoRiagg.isEsitoOperazione()){
				esitoFlusso.addListOfValidationsBeans(esitoRiagg.getListOfValidationBeans());
				esitoFlusso.setEsitoOperazione(false);
			}
			}
		
		return esitoFlusso;
	}
	
	/**
	 * Metodo per il controllo , si ricorda che siccome la validazione e' gia stata fatta
	 * tutte le schede sono valide dal punto di vista formale e logico, si cerca si capire solamente
	 * se sono rispettati i requisiti gerarchici .. in base all'operazione di cui secondo parametro
	 * l'operazione del workflow invocata e' diversa..
	 * 
	 * @param tutteLeSchede
	 * @param identificativo
	 * @param retro, flag che indica la presenza di una scheda aggiduicazione su file xml da trattare come 
	 *        retrocompatibile, se il flusso che deriva è diverso da aggiudicazione
	 * @return
	 */
	private boolean workFlowCaller(SituazioneSchedeAttuale situazioneAttuale, OperazioneScheda operazioneCorrente, int progressivoSchedaCompleta, String cig, boolean retro, String user){
		
		boolean esito = true;
		TipoFlusso tipoFlusso = TipoFlusso.AGGIUDICAZIONE;
		InfoGaraBean igb = null;
		int delega = 0;
		String utente_gara = null;
		String utente_delegata = null;
		boolean postDelega = true;
		try {
			igb = getInfoGara(cig);
			tipoFlusso = SimogValidator.getTipoFlusso(igb);
			
			//TICKET ALM 659 - 3.04.4.1 (build 811)
			GaraManager gm = new GaraManager(con,logger);
			LottoManager lm = new LottoManager(con,logger);
			Lotto lotto = lm.getLottoByCigWS(cig).get(0);
			Gara gara = gm.getGara(lotto.getId_Gara());
			delega = gara.getID_F_DELEGATE();
			utente_gara = gara.getCF_UTENTE();
			utente_delegata = utente_gara;
			List<String> datiStorici=gm.getDatiStoriciGaraDelegata(gara.getId_Gara());
			if(!datiStorici.isEmpty())
				utente_delegata = datiStorici.get(0);
			postDelega = !utente_delegata.equals(utente_gara);
			//FINE TICKET ALM 659 - 3.04.4.1 (build 811)
			
			
			//OK da Obino (mail del 22.05.2014): *! chiesto ad Obino che si deve fare, anomalia menicagli liguria
			// commentato
//			// retrocompatibilità con 2.9
//			// se l'aggiudicazione è precedente al 01012011 ed il flusso individuato non è aggiudicazione lo forzo
//			// si presume che arrivino schede aggiudicazione
//			if(retro && !tipoFlusso.toString().equals(TipoFlusso.AGGIUDICAZIONE.toString()))
//			   tipoFlusso = TipoFlusso.AGGIUDICAZIONE;

		
		// se non esiste flusso scarto l'operazione
		if(tipoFlusso == null){
		   this.esitiOperazioni.add(SchedaSpecificaValidationBean
	                .getThisKindOfValidationBeanErr(situazioneAttuale.getStatoDatiComuni(), 0,
	                        progressivoSchedaCompleta, 0, IdentificativoSchede.DATI_COMUNI, Messaggi.SIMOG_VALIDAZIONE_188));
           esito = false;		   
		}
		else{
      		WorkFlowController workFlow = new WorkFlowController(tipoFlusso);
      		
      		// settaggio di una variabile di classe del workflow che mi serve per il feedback !
      		workFlow.setProgressivoSchedaCompleta(progressivoSchedaCompleta);
      		IdentificativoSchede identificativo = null;
      		
      //		if(situazioneAttuale.getStatoDatiComuni().isEsistente()){
      		if(situazioneAttuale.getStatoDatiComuni().isFromXml()){
      			identificativo = IdentificativoSchede.getDatiComuni();
      			if(!workFlow.isOperazioneEffettuabile(operazioneCorrente, identificativo, situazioneAttuale)){
      				this.esitiOperazioni.addAll(workFlow.getEsitiOperazioni());
      				esito = false;
      			}
      		}
      //		if(situazioneAttuale.getStatoAggiudicazione().isEsistente()){
      		if(situazioneAttuale.getStatoAggiudicazione().isFromXml()){
      			identificativo = IdentificativoSchede.getAggiudicazione();
      			if(!workFlow.isOperazioneEffettuabile(operazioneCorrente, identificativo, situazioneAttuale)){
      				this.esitiOperazioni.addAll(workFlow.getEsitiOperazioni());
      				esito = false;
      			} 
      		}
      		if(situazioneAttuale.getStatoAdesione().isFromXml()){
      			identificativo = IdentificativoSchede.getAdesione();
      			if(!workFlow.isOperazioneEffettuabile(operazioneCorrente, identificativo, situazioneAttuale)){
      				this.esitiOperazioni.addAll(workFlow.getEsitiOperazioni());
      				esito = false;
      			}
      		}
      		
      		if(situazioneAttuale.getStatoSottosoglia().isFromXml()){
      			identificativo = IdentificativoSchede.getSottosoglia();
      			if(!workFlow.isOperazioneEffettuabile(operazioneCorrente, identificativo, situazioneAttuale)){
      				this.esitiOperazioni.addAll(workFlow.getEsitiOperazioni());
      				esito = false;
      			}
      		}
      		
      		if(situazioneAttuale.getStatoEscluso().isFromXml()){
      			identificativo = IdentificativoSchede.getEscluso();
      			if(!workFlow.isOperazioneEffettuabile(operazioneCorrente, identificativo, situazioneAttuale)){
      				this.esitiOperazioni.addAll(workFlow.getEsitiOperazioni());
      				esito = false;
      			}
      		}
      		
      //		if(situazioneAttuale.getStatoInizioLavori().isEsistente()){
      		if(situazioneAttuale.getStatoInizioLavori().isFromXml()){
      			identificativo = IdentificativoSchede.getInizioLavori();
      			if(!workFlow.isOperazioneEffettuabile(operazioneCorrente, identificativo, situazioneAttuale)){
      				this.esitiOperazioni.addAll(workFlow.getEsitiOperazioni());
      				esito = false;
      			} else if(delega!=0 && this.checkDelega(delega,postDelega,utente_gara,user)) {
      				this.esitiOperazioni.add(SchedaSpecificaValidationBean
      						.getThisKindOfValidationBeanErr(situazioneAttuale.getStatoInizioLavori(), 0,
      								progressivoSchedaCompleta, 0, IdentificativoSchede.FASE_INIZIALE, Messaggi.LOADER_APPALTO_005));
      				esito = false;
      			}
      		}
      		
      		if(situazioneAttuale.getStatoStipula().isFromXml()){
      			identificativo = IdentificativoSchede.getStipula();
      			if(!workFlow.isOperazioneEffettuabile(operazioneCorrente, identificativo, situazioneAttuale)){
      				this.esitiOperazioni.addAll(workFlow.getEsitiOperazioni());
      				esito = false;
      			}else if(delega!=0 && this.checkDelegaStipula(delega,postDelega,utente_gara,user)) {
      				this.esitiOperazioni.add(SchedaSpecificaValidationBean
      						.getThisKindOfValidationBeanErr(situazioneAttuale.getStatoStipula(), 0,
      								progressivoSchedaCompleta, 0, IdentificativoSchede.STIPULA, Messaggi.LOADER_APPALTO_005));
      				esito = false;
      			}
      		}
      		
      		if(!situazioneAttuale.getStatoAvanzamento().isEmpty() && StatoScheda.isFromXML(situazioneAttuale.getStatoAvanzamento())){
      			identificativo = IdentificativoSchede.getAvanzamenti();
      			
      			// PP 07.12.2012 SAL ammessi solo sopra i 500.000 euro
      			boolean okSAL = true; 
      			      //igb.getImportoLotto().floatValue() > Costanti.IMPORTO_LOTTO_500000 || igb.getImportoLotto().floatValue() == Costanti.IMPORTO_FUORI_SCALA;
      			
      			if(!okSAL || !workFlow.isOperazioneEffettuabile(operazioneCorrente, identificativo, situazioneAttuale)){
      				this.esitiOperazioni.addAll(workFlow.getEsitiOperazioni());
      				esito = false;
      			} else if(delega!=0 && this.checkDelega(delega,postDelega,utente_gara,user)) {
      				this.esitiOperazioni.add(SchedaSpecificaValidationBean
      						.getThisKindOfValidationBeanErr(situazioneAttuale.getStatoAvanzamento().get(0), 0,
      								progressivoSchedaCompleta, 0, IdentificativoSchede.STATO_AVANZAMENTO, Messaggi.LOADER_APPALTO_005));
      				esito = false;
      			}
      		}
      //		if(situazioneAttuale.getStatoConclusione().isEsistente()){
      		if(situazioneAttuale.getStatoConclusione().isFromXml()){
      			identificativo = IdentificativoSchede.getConclusione();
      			if(!workFlow.isOperazioneEffettuabile(operazioneCorrente, identificativo, situazioneAttuale)){
      				this.esitiOperazioni.addAll(workFlow.getEsitiOperazioni());
      				esito = false;
      			} else if(delega!=0 && this.checkDelega(delega,postDelega,utente_gara,user)) {
      				this.esitiOperazioni.add(SchedaSpecificaValidationBean
      						.getThisKindOfValidationBeanErr(situazioneAttuale.getStatoInizioLavori(), 0,
      								progressivoSchedaCompleta, 0, IdentificativoSchede.FINE_LAVORI, Messaggi.LOADER_APPALTO_005));
      				esito = false;
      			}
      		}
      //		if(situazioneAttuale.getStatoCollaudo().isEsistente()){
      		if(situazioneAttuale.getStatoCollaudo().isFromXml()){
      			identificativo = IdentificativoSchede.getCollaudo();
      			if(!workFlow.isOperazioneEffettuabile(operazioneCorrente, identificativo, situazioneAttuale)){
      				this.esitiOperazioni.addAll(workFlow.getEsitiOperazioni());
      				esito = false;
      			} else if(delega!=0 && this.checkDelega(delega,postDelega,utente_gara,user)) {
      				this.esitiOperazioni.add(SchedaSpecificaValidationBean
      						.getThisKindOfValidationBeanErr(situazioneAttuale.getStatoCollaudo(), 0,
      								progressivoSchedaCompleta, 0, IdentificativoSchede.COLLAUDO, Messaggi.LOADER_APPALTO_005));
      				esito = false;
      			}
      		}
      		if(!situazioneAttuale.getStatoAccordi().isEmpty() && StatoScheda.isFromXML(situazioneAttuale.getStatoAccordi())){
      			identificativo = IdentificativoSchede.getAccordi();
      			if(!workFlow.isOperazioneEffettuabile(operazioneCorrente, identificativo, situazioneAttuale)){
      				this.esitiOperazioni.addAll(workFlow.getEsitiOperazioni());
      				esito = false;
      			} else if(delega!=0 && this.checkDelega(delega,postDelega,utente_gara,user)) {
      				this.esitiOperazioni.add(SchedaSpecificaValidationBean
      						.getThisKindOfValidationBeanErr(situazioneAttuale.getStatoAccordi().get(0), 0,
      								progressivoSchedaCompleta, 0, IdentificativoSchede.ACCORDO_BONARIO, Messaggi.LOADER_APPALTO_005));
      				esito = false;
      			}
      		}
      		if(!situazioneAttuale.getStatoRitardo().isEmpty() && StatoScheda.isFromXML(situazioneAttuale.getStatoRitardo())){
      			identificativo = IdentificativoSchede.getRitardo();
      			if(!workFlow.isOperazioneEffettuabile(operazioneCorrente, identificativo, situazioneAttuale)){
      				this.esitiOperazioni.addAll(workFlow.getEsitiOperazioni());
      				esito = false;
      			} else if(delega!=0 && this.checkDelega(delega,postDelega,utente_gara,user)) {
      				this.esitiOperazioni.add(SchedaSpecificaValidationBean
      						.getThisKindOfValidationBeanErr(situazioneAttuale.getStatoSospensioni().get(0), 0,
      								progressivoSchedaCompleta, 0, IdentificativoSchede.IPOTESI_RECESSO, Messaggi.LOADER_APPALTO_005));
      				esito = false;
      			}
      		}
      		if(!situazioneAttuale.getStatoSospensioni().isEmpty() && StatoScheda.isFromXML(situazioneAttuale.getStatoSospensioni())){
      			identificativo = IdentificativoSchede.getSospensioni();
      			if(!workFlow.isOperazioneEffettuabile(operazioneCorrente, identificativo, situazioneAttuale)){
      				this.esitiOperazioni.addAll(workFlow.getEsitiOperazioni());
      				esito = false;
      			} else if(delega!=0 && this.checkDelega(delega,postDelega,utente_gara,user)) {
      				this.esitiOperazioni.add(SchedaSpecificaValidationBean
      						.getThisKindOfValidationBeanErr(situazioneAttuale.getStatoSospensioni().get(0), 0,
      								progressivoSchedaCompleta, 0, IdentificativoSchede.SOSPENSIONE, Messaggi.LOADER_APPALTO_005));
      				esito = false;
      			}
      		}
      		if(!situazioneAttuale.getStatoSubAppalti().isEmpty() && StatoScheda.isFromXML(situazioneAttuale.getStatoSubAppalti())){
      			identificativo = IdentificativoSchede.getSubAppalti();
      			if(!workFlow.isOperazioneEffettuabile(operazioneCorrente, identificativo, situazioneAttuale)){
      				this.esitiOperazioni.addAll(workFlow.getEsitiOperazioni());
      				esito = false;
      			} else if(delega!=0 && this.checkDelega(delega,postDelega,utente_gara,user)) {
      				this.esitiOperazioni.add(SchedaSpecificaValidationBean
      						.getThisKindOfValidationBeanErr(situazioneAttuale.getStatoSubAppalti().get(0), 0,
      								progressivoSchedaCompleta, 0, IdentificativoSchede.SUBAPPALTO, Messaggi.LOADER_APPALTO_005));
      				esito = false;
      			}
      		}
      		if(!situazioneAttuale.getStatoVarianti().isEmpty() && StatoScheda.isFromXML(situazioneAttuale.getStatoVarianti())){
      			identificativo = IdentificativoSchede.getVarianti();
      			if(!workFlow.isOperazioneEffettuabile(operazioneCorrente, identificativo, situazioneAttuale)){
      				this.esitiOperazioni.addAll(workFlow.getEsitiOperazioni());
      				esito = false;
      			} else if(delega!=0 && this.checkDelega(delega,postDelega,utente_gara,user)) {
      				this.esitiOperazioni.add(SchedaSpecificaValidationBean
      						.getThisKindOfValidationBeanErr(situazioneAttuale.getStatoVarianti().get(0), 0,
      								progressivoSchedaCompleta, 0, IdentificativoSchede.VARIANTE, Messaggi.LOADER_APPALTO_005));
      				esito = false;
      			} else { //TICKET ALM #14626 - 3.04.5
      				if(gara.getData_creazione().compareTo(Costanti.DATA_DL50) < 0 && SimogFlags.isAccordoQuadroOrConvenzione(gara.getID_MODO_REAL())) {
      					this.esitiOperazioni.add(SchedaSpecificaValidationBean
          						.getThisKindOfValidationBeanErr(situazioneAttuale.getStatoVarianti().get(0), 0,
          								progressivoSchedaCompleta, 0, IdentificativoSchede.VARIANTE, Messaggi.SIMOG_MASSLOADER_207.replace("$1","Variante")));
          				esito = false;
      				}
      			}
      		}
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return esito;
	}
	
	/**
	 * Questo controllo effettua i controllo logico, ovvero se esistono, tra le schede su cui
	 * effettuare le operazioni, delle schede in uno stato di "in definizione" "in cancellazione" "in richeista annullamento" "riaggiudicate"
	 * 
	 * @param listOfSchede
	 * @return
	 * @throws Exception
	 * @-deprecated: pare di no viene usato da ModificaLayer.class
	 */
	public EsitoOperazioneControlloLogico controllaCorrettezzaLogica(ArrayList<IdsSchedaXML> listOfSchede) throws Exception{
		EsitoOperazioneControlloLogico esitoControlloLogico = new EsitoOperazioneControlloLogico();
		for(IdsSchedaXML kiaveCorrente : listOfSchede){
			
			EsitoControlloStatiSchede esito = this.controllaFlussoPreInserimento(kiaveCorrente.getSituazioneAttuale(), kiaveCorrente.getCig(), kiaveCorrente.getCui());
			
			
			
			if(esito.isEsitoOperazione()){
				esitoControlloLogico.addSchedaValida(kiaveCorrente);
			}
			else{
				esitoControlloLogico.addListOfValidationsBeans(esito.getListOfValidationBeans());
				esitoControlloLogico.addListOfCigNonValidi(kiaveCorrente.getCig());
			}
				
		}
		if(esitoControlloLogico.getListOfValidationsBeans() == null || esitoControlloLogico.getListOfValidationsBeans().size() == 0) esitoControlloLogico.setSomeSchedeNonValide(false);
		else esitoControlloLogico.setSomeSchedeNonValide(true);
		
		if(esitoControlloLogico.getListOfSchedeValide() != null && esitoControlloLogico.getListOfSchedeValide().size() > 0) esitoControlloLogico.setEsitoOperazione(true);
		else esitoControlloLogico.setEsitoOperazione(false);
		
		return esitoControlloLogico;
	}
	
	/**
	 * migration
	 * Per inserirlo nel flusso delle cancellazioni devo eseguire l'operazione sulla idsscheda corrente
	 * 
	 * @param kiaveCorrente
	 * @return
	 * @throws Exception
	 */
	public EsitoOperazioneControlloLogico controllaCorrettezzaLogica(IdsSchedaXML kiaveCorrente) throws Exception{
		
		EsitoOperazioneControlloLogico esitoControlloLogico = new EsitoOperazioneControlloLogico();
		EsitoControlloStatiSchede esito = this.controllaFlussoPreInserimento(kiaveCorrente.getSituazioneAttuale(), kiaveCorrente.getCig(), kiaveCorrente.getCui());
		
		if(esito.isEsitoOperazione()){
			esitoControlloLogico.addSchedaValida(kiaveCorrente);
		}
		else{
			esitoControlloLogico.addListOfValidationsBeans(esito.getListOfValidationBeans());
			esitoControlloLogico.addListOfCigNonValidi(kiaveCorrente.getCig());
		}
		if(esitoControlloLogico.getListOfValidationsBeans() == null || esitoControlloLogico.getListOfValidationsBeans().size() == 0) esitoControlloLogico.setSomeSchedeNonValide(false);
		else esitoControlloLogico.setSomeSchedeNonValide(true);
		
		if(esitoControlloLogico.getListOfSchedeValide() != null && esitoControlloLogico.getListOfSchedeValide().size() > 0) esitoControlloLogico.setEsitoOperazione(true);
		else esitoControlloLogico.setEsitoOperazione(false);
		
		return esitoControlloLogico;
	}
	
	/**
	 * Metodo di utilita' per costruire il cui dai dati presenti nella scheda a
	 * La forma reale del cui e' cig_cycle-cig, quello che serve a noi e'
	 * cig-progCui !.
	 * Controlla comunque che il cui non sia gia nella forma richiesta
	 * @param schedaA
	 * @return
	 */
	protected String getCuiForDummies(Scheda_A schedaA){
		String cig, cui = null;
		if(schedaA.getAggiudicazione().getCui() != null){
			String temp = schedaA.getAggiudicazione().getCui().split("-")[0];
			if(temp.length() > schedaA.getInfoComuni().getCig().length()){
				temp = temp.substring(0, (schedaA.getInfoComuni().getCig().length()));
			}
			// se e' gia' in forma voluta ritorna quella
			if(temp.equals(schedaA.getInfoComuni().getCig())) return schedaA.getAggiudicazione().getCui();
			// altrimenti continua
			cig = schedaA.getAggiudicazione().getCui().split("-")[1];
			cui = cig + "-" + schedaA.getAggiudicazione().getProgCUI();
		}
		return cui;
	}
	
	/**
	 * Inserisce, modifica, o non fa nulla per l'anagrafica di un responsabile
	 * 
	 * @param listOfAnaResponsabile
	 * @param cf
	 * @return
	 * @throws SQLException
	 */
	public boolean modificaAnagraficaResponsabili(ResponsabileBean rb, List<SoggettoResponsabileBean> listOfAnaResponsabile, String cf, boolean isOnlyCf) throws SQLException{ 
		
		// flag che indica se sia stata trovata l'anagrafica nella lista delle anagrafiche del xml
		boolean found = false;
		
		if(listOfAnaResponsabile != null && listOfAnaResponsabile.size() > 0){
			RubricaResponsabiliManager rrm = new RubricaResponsabiliManager(con,this.logger);
	
			for(SoggettoResponsabileBean anagrafica_responsabile : listOfAnaResponsabile){
				
				if(anagrafica_responsabile.getCodiceFiscaleResponsabile().equals(cf)){
					found = true;
					//se esite
					if(!rrm.checkCF(anagrafica_responsabile.getCodiceFiscaleResponsabile())){
						//FIX-ME: doppia query ?
//						Object[] o = rrm.getSoggettoByCF(anagrafica_responsabile.getCodiceFiscaleResponsabile());
//						anagrafica_responsabile.setIdResponsabile(((Long)o[0]).longValue());
//						anagrafica_responsabile.setDataInizioRes((Timestamp)o[1]);
						//prendo l'anagrafica dal db per effettuare il confronto
						SoggettoResponsabileBean anagrafica_db = rrm.getAllSoggettoResponsabileByCF(anagrafica_responsabile.getCodiceFiscaleResponsabile());
						anagrafica_responsabile.setIdResponsabile(anagrafica_db.getIdResponsabile());
						anagrafica_responsabile.setDataInizioRes(anagrafica_db.getDataInizioRes());
						boolean esitoConfronto = new SoggettiResponsabiliComparator().equals(anagrafica_responsabile, anagrafica_db);
						logger.debug("Ana Res xml: "+ObjectIntrospector.propertiesInfo(SoggettoResponsabileBean.class,anagrafica_responsabile));
						logger.debug("Ana Res db: "+ObjectIntrospector.propertiesInfo(SoggettoResponsabileBean.class,anagrafica_db));
						logger.debug("Risultato del confronto delle anagrafiche Resp: "+esitoConfronto);
						//se le anagrafiche non sono uguali
						if(!esitoConfronto){
							//aggiorna l'anagrafica
							RubricaResponsabili anagrafica_per_db = RubricaResponsabili.converti(anagrafica_responsabile);
							anagrafica_per_db.setId_soggetto_responsabile(anagrafica_db.getIdResponsabile());
							anagrafica_per_db.setData_inizio_res(PageHelper.formatTimeStamp(anagrafica_db.getDataInizioRes()));
							// introdotta gestione inserimenti veloci il secondo parametro viene settato poco sopra
							rrm.aggiornamentoConCancellazioneLogica(anagrafica_responsabile,anagrafica_per_db, anagrafica_responsabile.getDataInizioRes());
						}
					//altrimenti inserisco
					}else{
						Object[] o = rrm.insertPartecipante(RubricaResponsabili.converti(anagrafica_responsabile), false);
						anagrafica_responsabile.setIdResponsabile(((Long)o[0]).longValue());
						anagrafica_responsabile.setDataInizioRes((Timestamp)o[1]);
					}
					if(!isOnlyCf)
						rb.setSoggettoResponsabile(anagrafica_responsabile);
				}
	
			}
			// se non l'hai trovata nel xml caricala dal db (visti i controlli effettuati a monte qui ho la garanzia che esista)
			if(!found){
				if(!isOnlyCf){
					SoggettoResponsabileBean anagrafica_db = rrm.getAllSoggettoResponsabileByCF(rb.getSoggettoResponsabile().getCodiceFiscaleResponsabile());
					rb.setSoggettoResponsabile(anagrafica_db);
				}
			}
		// altrimenti carica i riferimenti dal db..
		}else{
			if(!isOnlyCf){
				RubricaResponsabiliManager rrm = new RubricaResponsabiliManager(con,this.logger);
				SoggettoResponsabileBean anagrafica_db = rrm.getAllSoggettoResponsabileByCF(rb.getSoggettoResponsabile().getCodiceFiscaleResponsabile());
				rb.setSoggettoResponsabile(anagrafica_db);
			}
		}
		
		return true;
	}

	/**
	 * Inserisce o aggiorna o non fa nulla per le anagrafiche degli aggiudicatari, oppure valorizza i dati
	 * dell'anagrafica necessari all'inserimento dell'aggiudicatario ?
	 * 
	 * @param listOfAnapartecipante
	 * @param cf
	 * @param codiceNazione
	 * @return
	 * @throws SQLException
	 */
	public boolean modificaAnagraficaAggiudicatari(
	      PosizioneAggiudicatarioBean bean, 
	      AggiudicatarioBean ab,
	      DittaAusiliariaBean da,
	      ResponsabileBean rb,
	      List<SoggettoPartecipanteBean> listOfAnapartecipante, 
	      String cf, String codiceNazione, 
	      boolean isAggiudicatario, boolean isAusiliaria, boolean isPersonaGiuridicaResp) throws SQLException{ 

		// flag che indica se sia stata trovata l'anagrafica nella lista delle anagrafiche del xml
		boolean found = false;		
		
		if(listOfAnapartecipante != null && listOfAnapartecipante.size() > 0){
			RubricaManager rm = new RubricaManager(con,this.logger);
	
			for(SoggettoPartecipanteBean anagrafica_partecipante : listOfAnapartecipante){
				
				if(anagrafica_partecipante.getId_stato() == null){
					anagrafica_partecipante.setId_stato("");
				} 
				
				// esegui le operazioni di controllo / inserimento solo sul partecipante selezionato (evito cosi di effettuare dei controlli di non esistenza)
				if(anagrafica_partecipante.getCodiceFiscale().equals(cf)
						&& anagrafica_partecipante.getId_stato().equals(codiceNazione)){				
					found = true;
					if(!rm.checkCF(anagrafica_partecipante.getCodiceFiscale(),anagrafica_partecipante.getId_stato())){
						//FIX-ME: doppia query ?
						//esite quindi recupero i dati che mi servono per l'inserimento dell'aggiudicatario
//						Object[] o = rm.getSoggettoByCF(anagrafica_partecipante.getCodiceFiscale(),anagrafica_partecipante.getId_stato());
//						anagrafica_partecipante.setDataInizioSogg((Timestamp)o[1]);
//						anagrafica_partecipante.setIdSoggettoPartecipante(((Long)o[0]).longValue());
						
						SoggettoPartecipanteBean anagrafica_db = rm.getAllSoggettoPartecipanteByCF(anagrafica_partecipante.getCodiceFiscale(), anagrafica_partecipante.getId_stato());
						anagrafica_partecipante.setDataInizioSogg(anagrafica_db.getDataInizioSogg());
						anagrafica_partecipante.setIdSoggettoPartecipante(anagrafica_db.getIdSoggettoPartecipante());
						
						boolean esitoConfronto = new SoggettiPartecipantiComparator().equals(anagrafica_partecipante, anagrafica_db);
						logger.debug("Ana Part xml: "+ObjectIntrospector.propertiesInfo(SoggettoPartecipanteBean.class,anagrafica_partecipante));
						logger.debug("Ana Part db: "+ObjectIntrospector.propertiesInfo(SoggettoPartecipanteBean.class,anagrafica_db));
						logger.debug("Risultato del confronto delle anagrafiche Part: "+esitoConfronto);
						if(!esitoConfronto){
							//aggiorna l'anagrafica
							Rubrica anagrafica_per_db = Rubrica.converti(anagrafica_partecipante);
							anagrafica_per_db.setId_soggetto_partecipante(anagrafica_partecipante.getIdSoggettoPartecipante());
							anagrafica_per_db.setData_inizio_sogg(PageHelper.formatTimeStamp(anagrafica_partecipante.getDataInizioSogg()));
							// nota che il secondo parametro viene settato poco sopra
							rm.aggiornamentoConCancellazioneLogica(anagrafica_partecipante,anagrafica_per_db,anagrafica_partecipante.getDataInizioSogg());
						}			
					}else{
						//altrimenti devo inserire
						Object[] o = rm.insertPartecipante(Rubrica.converti(anagrafica_partecipante), false);
						anagrafica_partecipante.setDataInizioSogg((Timestamp)o[1]);
						anagrafica_partecipante.setIdSoggettoPartecipante(((Long)o[0]).longValue());
					}
					
					if(isAggiudicatario){
						ab.setSoggettoPartecipante(anagrafica_partecipante);
					}else if(isAusiliaria){
						da.setSoggettoPartecipante(anagrafica_partecipante);
					}else if(isPersonaGiuridicaResp){
						rb.setSoggettoPartecipante(anagrafica_partecipante);
					}else{
						bean.setSoggettoPartecipante(anagrafica_partecipante);
					}

				}
				
				//TICKET ALM #3404
				if(!found) {
					analyzeCfToLog(anagrafica_partecipante.getCodiceFiscale(), cf, anagrafica_partecipante.getId_stato(), codiceNazione);
				}
				//FINE TICKET ALM #3404
		
			}
			// se non l'ho trovato nel file xml
			if(!found){
			   SoggettoPartecipanteBean anagrafica_db = null;
				if(isAggiudicatario){
					anagrafica_db = rm.getAllSoggettoPartecipanteByCF(ab.getSoggettoPartecipante().getCodiceFiscale(),ab.getSoggettoPartecipante().getId_stato());
					ab.setSoggettoPartecipante(anagrafica_db);
				}else if(isAusiliaria){
					anagrafica_db = rm.getAllSoggettoPartecipanteByCF(da.getSoggettoPartecipante().getCodiceFiscale(),da.getSoggettoPartecipante().getId_stato());
					da.setSoggettoPartecipante(anagrafica_db);
				} else if(isPersonaGiuridicaResp){
					anagrafica_db = rm.getAllSoggettoPartecipanteByCF(rb.getSoggettoPartecipante().getCodiceFiscale(),rb.getSoggettoPartecipante().getId_stato());
				} else{
					anagrafica_db = rm.getAllSoggettoPartecipanteByCF(bean.getSoggettoPartecipante().getCodiceFiscale(),bean.getSoggettoPartecipante().getId_stato());
					bean.setSoggettoPartecipante(anagrafica_db);
				}
				
				// se il bean è vuoto non esiste l'anagrafica sul DB devo dare errore
				if (anagrafica_db.getCodiceFiscale()== null){
				   return false;
				}
			}
		// carica i dati di riferimento dell'anagrafica, visto che se e' arrivato fino a qui esiste nella base dati ?
		}else{
			RubricaManager rm = new RubricaManager(con,this.logger);
			if(isAggiudicatario){
				SoggettoPartecipanteBean anagrafica_db = rm.getAllSoggettoPartecipanteByCF(ab.getSoggettoPartecipante().getCodiceFiscale(),ab.getSoggettoPartecipante().getId_stato());
				ab.setSoggettoPartecipante(anagrafica_db);
			}else if(isAusiliaria){
				SoggettoPartecipanteBean anagrafica_db = rm.getAllSoggettoPartecipanteByCF(da.getSoggettoPartecipante().getCodiceFiscale(),da.getSoggettoPartecipante().getId_stato());
				da.setSoggettoPartecipante(anagrafica_db);
			
			}else if(isPersonaGiuridicaResp){
				SoggettoPartecipanteBean anagrafica_db = rm.getAllSoggettoPartecipanteByCF(rb.getSoggettoPartecipante().getCodiceFiscale(),rb.getSoggettoPartecipante().getId_stato());
				rb.setSoggettoPartecipante(anagrafica_db);
			} else{
				SoggettoPartecipanteBean anagrafica_db = rm.getAllSoggettoPartecipanteByCF(bean.getSoggettoPartecipante().getCodiceFiscale(),bean.getSoggettoPartecipante().getId_stato());
				bean.setSoggettoPartecipante(anagrafica_db);
			}
		}
		return true;  
	}
	
	
	public boolean modificaAnagraficaAggiudicatariSub(SubappaltatoreBean sb, List<SoggettoPartecipanteBean> listOfAnapartecipante, String cf) throws SQLException{ 

			// flag che indica se sia stata trovata l'anagrafica nella lista delle anagrafiche del xml
			boolean found = false;		
			if(listOfAnapartecipante != null && listOfAnapartecipante.size() > 0){
				RubricaManager rm = new RubricaManager(con,this.logger);
				for(SoggettoPartecipanteBean anagrafica_partecipante : listOfAnapartecipante){
					if(anagrafica_partecipante.getId_stato() == null){
						anagrafica_partecipante.setId_stato("");
					} 
					
					// esegui le operazioni di controllo / inserimento solo sul partecipante selezionato (evito cosi di effettuare dei controlli di non esistenza)
					if(anagrafica_partecipante.getCodiceFiscale().equals(cf)){				
						found = true;
						if(!rm.checkCFNoCountry(anagrafica_partecipante.getCodiceFiscale())){
							//FIX-ME: doppia query ?
							//esite quindi recupero i dati che mi servono per l'inserimento dell'aggiudicatario
//							Object[] o = rm.getSoggettoByCF(anagrafica_partecipante.getCodiceFiscale(),anagrafica_partecipante.getId_stato());
//							anagrafica_partecipante.setDataInizioSogg((Timestamp)o[1]);
//							anagrafica_partecipante.setIdSoggettoPartecipante(((Long)o[0]).longValue());
							
							SoggettoPartecipanteBean anagrafica_db = rm.getAllSoggettoPartecipanteByCFSubappaltatori(anagrafica_partecipante.getCodiceFiscale());
							anagrafica_partecipante.setDataInizioSogg(anagrafica_db.getDataInizioSogg());
							anagrafica_partecipante.setIdSoggettoPartecipante(anagrafica_db.getIdSoggettoPartecipante());
							
							boolean esitoConfronto = new SoggettiPartecipantiComparator().equals(anagrafica_partecipante, anagrafica_db);
							logger.debug("Ana Part xml: "+ObjectIntrospector.propertiesInfo(SoggettoPartecipanteBean.class,anagrafica_partecipante));
							logger.debug("Ana Part db: "+ObjectIntrospector.propertiesInfo(SoggettoPartecipanteBean.class,anagrafica_db));
							logger.debug("Risultato del confronto delle anagrafiche Part: "+esitoConfronto);
							if(!esitoConfronto){
								//aggiorna l'anagrafica
								Rubrica anagrafica_per_db = Rubrica.converti(anagrafica_partecipante);
								anagrafica_per_db.setId_soggetto_partecipante(anagrafica_partecipante.getIdSoggettoPartecipante());
								anagrafica_per_db.setData_inizio_sogg(PageHelper.formatTimeStamp(anagrafica_partecipante.getDataInizioSogg()));
								// nota che il secondo parametro viene settato poco sopra
								rm.aggiornamentoConCancellazioneLogica(anagrafica_partecipante,anagrafica_per_db,anagrafica_partecipante.getDataInizioSogg());
							}			
						}else{
							//altrimenti devo inserire
							Object[] o = rm.insertPartecipante(Rubrica.converti(anagrafica_partecipante), false);
							anagrafica_partecipante.setDataInizioSogg((Timestamp)o[1]);
							anagrafica_partecipante.setIdSoggettoPartecipante(((Long)o[0]).longValue());
						}
						
						   sb.setSoggettoPartecipante(anagrafica_partecipante);

					}
			
				}
				// se non l'ho trovato nel file xml
				if(!found){
				   SoggettoPartecipanteBean anagrafica_db = rm.getAllSoggettoPartecipanteByCFSubappaltatori(sb.getSoggettoPartecipante().getCodiceFiscale());					
					
					
					// se il bean è vuoto non esiste l'anagrafica sul DB devo dare errore
					if (anagrafica_db.getCodiceFiscale()== null){
					   return false;
					} else
						sb.setSoggettoPartecipante(anagrafica_db);
				}
			// carica i dati di riferimento dell'anagrafica, visto che se e' arrivato fino a qui esiste nella base dati ?
			}else{
				RubricaManager rm = new RubricaManager(con,this.logger);
				
					SoggettoPartecipanteBean anagrafica_db = rm.getAllSoggettoPartecipanteByCFSubappaltatori(cf);
					if(anagrafica_db.getCodiceFiscale()==null)
						return false;
					sb.setSoggettoPartecipante(anagrafica_db);
				
			}
			return true;  
		}
	
	
	//TICKET ALM #3404
	private void analyzeCfToLog(String cfAnagrafica, String cfPartecipante, String idStatoAnagrafica, String idStatoPartecipante) {
	
		logger.error("ALM 3404: --------------------------------------------");
		logger.error("ALM 3404: Verifica dati anagrafica aggiudicatari - CF");
		logger.error("ALM 3404: CF ANAGRAFICA: |"+cfAnagrafica+"|");
		logger.error("ALM 3404: CF PARTECIPANTE: |"+cfPartecipante+"|");
		if(cfAnagrafica!=null) logger.error("ALM 3404: Are equals? "+cfAnagrafica.equals(cfPartecipante));
		logger.error("ALM 3404: Verifica dati anagrafica aggiudicatari - STATO ESTERO");
		logger.error("ALM 3404: ID STATO ANAGRAFICA: |"+idStatoAnagrafica+"|");
		logger.error("ALM 3404: ID STATO PARTECIPANTE: |"+idStatoPartecipante+"|");
		if(idStatoAnagrafica!=null) logger.error("ALM 3404: Are equals? "+idStatoAnagrafica.equals(idStatoPartecipante));
		logger.error("ALM 3404: --------------------------------------------");
	}
	//FINE TICKET ALM #3404
	
	/**
	 * Si occupa di valorizzare i progressivi nel bean di feedback
	 * 
	 * @param listaDiTutti
	 * @param posizioneSchedaOperazione: int[] posizioni nel file xml delle schede di cui nome + operazione
	 * @param nomeScheda
	 */
	protected void setProgressivoScheda(ArrayList<SchedaSpecificaValidationBean> listaDiTutti, int[] posizioneSchedaOperazione, String nomeScheda){
		int posizione = 0;
		// introdotto per evitare che due errori sulla stessa scheda diano problemi
		// Map<String,Integer> evitaErroriCardinalita = new LinkedHashMap<String, Integer>();
		
		for(SchedaSpecificaValidationBean schedaCorrente: listaDiTutti){
			if(schedaCorrente.getNomeScheda().equals(nomeScheda)){	

// PP grossa confusione sul significato del progressivo...
//				String key = createKeyForProgressivo(schedaCorrente);
//				// se non c'e' nella mappa..
//				if(evitaErroriCardinalita.isEmpty() || !evitaErroriCardinalita.containsKey(key)){
//				   
//				   int progressivo = posizioneSchedaOperazione[posizione] + 1;
//					
//				   
//					
//					evitaErroriCardinalita.put(key, progressivo);
//					schedaCorrente.setProgressivo(progressivo);
//					posizione++;
//				// altrimenti
//				}else{
//					schedaCorrente.setProgressivo(evitaErroriCardinalita.get(key).intValue());
//				}	
				schedaCorrente.setProgressivo(schedaCorrente.getProgressivoSchedaCompleta());
			}
		}	
	}

//	PP private String createKeyForProgressivo(SchedaSpecificaValidationBean schedaCorrente){
//		String key = schedaCorrente.getNomeScheda()+"_"+schedaCorrente.getCodiceErrore()+"_"+schedaCorrente.getIdSimog()+"_"+schedaCorrente.getIdLocale();
//		logger.debug("Chiave generata per il progressivo corrente: "+key);
//		return key;
//	}
//	private String createKeyForProgressivo(long id_simog, String id_locale){
//		return String.valueOf(id_simog) + "_" + id_locale != null && !"".equals(id_locale) ? id_locale : "0";
//	}
	
	/**
	 * Metodo che si occupa di tornare l'array che indica le posizioni delle schede all'interno del file xml
	 * schede che sono gia state separate tra modifica ed inserimento
	 * Attenzione non e' NULL SAFE
	 * Tratta solamente le schede MULTIPLE
	 * 
	 * TODO: VL - implementare con la reflection ?
	 * 
	 * @param nomeScheda
	 * @param operazione
	 * @return
	 */
	protected int[] getCorrectArray(IdsSchedaXML ids, String nomeScheda, String operazione){
		
		if(operazione.equals(OperazioneScheda.getInserimento().getNomeOperazione())){
			
			if(nomeScheda.equals(IdentificativoSchede.getAccordi().getNomeScheda())){
				return ids.getAccordiPosizioneInserimento();
			}else if(nomeScheda.equals(IdentificativoSchede.getAvanzamenti().getNomeScheda())){
				return ids.getAvanzamentiPosizioneInserimento();
			}else if(nomeScheda.equals(IdentificativoSchede.getRitardo().getNomeScheda())){
				return ids.getRitardiPosizioneInserimento();
			}else if(nomeScheda.equals(IdentificativoSchede.getSospensioni().getNomeScheda())){
				return ids.getSospensioniPosizioneInserimento();
			}else if(nomeScheda.equals(IdentificativoSchede.getSubAppalti().getNomeScheda())){
				return ids.getSubappaltiPosizioneInserimento();
			}else if(nomeScheda.equals(IdentificativoSchede.getVarianti().getNomeScheda())){
				return ids.getVariantiPosizioneInserimento();
			}else{
				throw new RuntimeException("Il nome della scheda["+nomeScheda+"] indicato NON e' valido");
			}
		}else if(operazione.equals(OperazioneScheda.getModifica().getNomeOperazione())){
			
			if(nomeScheda.equals(IdentificativoSchede.getAccordi().getNomeScheda())){
				return ids.getAccordiPosizioneModifica();
			}else if(nomeScheda.equals(IdentificativoSchede.getAvanzamenti().getNomeScheda())){
				return ids.getAvanzamentiPosizioneModifica();
			}else if(nomeScheda.equals(IdentificativoSchede.getRitardo().getNomeScheda())){
				return ids.getRitardiPosizioneModifica();
			}else if(nomeScheda.equals(IdentificativoSchede.getSospensioni().getNomeScheda())){
				return ids.getSospensioniPosizioneModifica();
			}else if(nomeScheda.equals(IdentificativoSchede.getSubAppalti().getNomeScheda())){
				return ids.getSubappaltiPosizioneModifica();
			}else if(nomeScheda.equals(IdentificativoSchede.getVarianti().getNomeScheda())){
				return ids.getVariantiPosizioneModifica();
			}else{
				throw new RuntimeException("Il nome della scheda["+nomeScheda+"] indicato NON e' valido");
			}		
		}else{
			throw new RuntimeException("Operazione indicata NON e' valida");
		}
	}

	/**
	 * Metodo piu' preciso di quello invocato si occupa di valorizzare i progressivi delle schede multiple informazione che
	 * prima non avevo.
	 * Quindi le operazioni di controllo vegono fatte a valle, qui rimedio al progressivo.
	 * 
	 * @param ids
	 * @return
	 */
	protected EsitoOperazioneControlloLogico controllaCorrettezzaFlussoConProgressivo(IdsSchedaXML ids, OperazioneScheda operazioneCorrente, String user){
		
	   // retrocompatibilità 2.9 - 3.02
	   boolean retro = false;
	   
	   if(SimogFlags.is30230_RFMLSC00Active()){
	       String dataAgg = "99999999"; // data confronto controlli non bloccanti
      	   if(ids.getScheda() != null 
      	         && ids.getScheda().getSchedaCompletaArray().length > 0
      	         && ids.getScheda().getSchedaCompletaArray(0) != null
      	         && ids.getScheda().getSchedaCompletaArray(0).getAggiudicazione() != null
      	         && ids.getScheda().getSchedaCompletaArray(0).getAggiudicazione().getAppalto() != null
      	         && ids.getScheda().getSchedaCompletaArray(0).getAggiudicazione().getAppalto().getDATAVERBAGGIUDICAZIONE() != null)
      	      dataAgg = PageHelper.getFormattedCalendarDate(ids.getScheda().getSchedaCompletaArray(0).getAggiudicazione().getAppalto().getDATAVERBAGGIUDICAZIONE()); 
      	      
      	   retro = SimogValidator.DATA_BLOCCANTI.compareTo(dataAgg) > 0;
	   }
	   
	   EsitoOperazioneControlloLogico esitoControlloLogico = this.controllaCorrettezzaFlusso(ids.getSituazioneAttuale(),operazioneCorrente, ids.getCardinalitaSchedaCompleta(),ids.getCig(), retro, user);
		ArrayList<SchedaSpecificaValidationBean> listOfValidation = esitoControlloLogico.getListOfValidationsBeans();
		
		if(listOfValidation != null && !listOfValidation.isEmpty()){
			Iterator<IdentificativoSchede> iteraListaTipiScheda = IdentificativoSchede.iteratorSoleSchedeMultiple();
			while(iteraListaTipiScheda.hasNext()){
				String nomeSchedaCorrente = iteraListaTipiScheda.next().getNomeScheda();
				this.setProgressivoScheda(listOfValidation, this.getCorrectArray(ids, nomeSchedaCorrente, operazioneCorrente.getNomeOperazione()), nomeSchedaCorrente);
			}
		}
		return esitoControlloLogico;
	}	
	/**
	 * Se la variabile vale 0 ritorna una stringa = ""
	 * altrimenti lo string value del numero
	 * 
	 * @param longValue
	 * @return
	 */
	protected String getStringValueOfLong(long longValue){
		if(longValue == 0) return "";
		return String.valueOf(longValue);
	}

	public InfoGaraBean getInfoGara (String CIG) throws Exception{
		InfoComuniManager icm = new InfoComuniManager(con, logger);
		LottoManager lm = new LottoManager(con, logger);
		List<Lotto> lottoByCigWS = lm.getLottoByCigWS(CIG);
		
		InfoGaraBean igBean = null;
		
		try {
			igBean = icm.loadInfoGara(lottoByCigWS.get(0).getId_Lotto());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return igBean;
	}

   public ArrayList<SchedaSpecificaValidationBean> getEsitiOperazioni() {
      return esitiOperazioni;
   }

   public DatiAggiudicazioneType[] replaceDatiAdesione(TrasferimentoDati trasferimentoDati, FeedBackWriterValidationsBeans feedBackWriter, FeedBack feedBack) throws Exception{
      
      LottoManager lottoManager = new LottoManager(con, logger);
      
      for(it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType item: trasferimentoDati.getSchedeArray())
      {
         
         Lotto lotto = null;
         
         List<Lotto> lotti = lottoManager.getLottoByCigWS( item.getDatiComuni().getCIG() );
         
         if (lotti != null && lotti.size() > 0)
            lotto = lotti.get(0);
         
         boolean okModoReal = false;
         
         DatiComuniType dc = item.getDatiComuni();
         
//         3.04.8 34190 fix
         if(dc != null && dc.getMODOREALIZZAZIONE() != null)
            okModoReal = (Integer.parseInt(item.getDatiComuni().getMODOREALIZZAZIONE()) == Costanti.MODOREAL_ADESIONE_NOCOMPET) || (Integer.parseInt(item.getDatiComuni().getMODOREALIZZAZIONE()) == Costanti.MODOREAL_CONCESSIONE_NOCOMPET);
         
         for(SchedaCompletaType schedaA: item.getSchedaCompletaArray() )
         { 
            boolean rimpiazza = okModoReal && schedaA.getAdesione() != null && lotto != null && lotto.getId_Lotto()>0;
            
            if( rimpiazza ){
            
               AppaltoAdesioneType appalto = schedaA.getAdesione().getAppalto();
               
               appalto.setPERCRIBASSOAGG(BigDecimal.ZERO);
               appalto.setPERCOFFAUMENTO(BigDecimal.ZERO);
               
               //Ticket ALM #702: l'importo aggiudicazione e la data verbale
			   //devono corrispondere ai dati del CIG derivato
               
              /* BigDecimal subTotale = BigDecimal.ZERO;
               BigDecimal importoLavori = appalto.getIMPORTOLAVORI();
               BigDecimal importoServizi = appalto.getIMPORTOSERVIZI();
               BigDecimal importoForniture = appalto.getIMPORTOFORNITURE();
               
               if( importoLavori != null ) subTotale = subTotale.add( importoLavori );
               if( importoServizi != null ) subTotale = subTotale.add( importoServizi );
               if( importoForniture != null ) subTotale = subTotale.add( importoForniture );               
               
               appalto.setIMPORTOAGGIUDICAZIONE( subTotale );

               */
                //Data Pubblicazione presa dal DB locale
               appalto.setIMPORTOAGGIUDICAZIONE(lotto.getImporto_Lotto());
               appalto.setDATAAGGIUDICAZIONE( PageHelper.getCalendarFromStringDate(lotto.getData_Pubblicazione()) );
               ////Fine Ticket ALM #702
               
               
               // FIXME !*  PP manca la sostituzione degli aggiudicatari, vedere come fa il web
               
               
               String localCui = item.getSchedaCompletaArray().length > 0 ? item.getSchedaCompletaArray(0).getCUI() : "";

               //Creazione Feedback - Warning Replace Dati
               ValidationBean validation = new SchedaSpecificaValidationBean(
                     Messaggi.SIMOG_AGGIUDICAZIONI_085, ValidationBean.VALBEAN_SEV_WARN,
                     0, 1, 0, TipiSchedeType.ADESIONE.toString(), item.getDatiComuni().getCIG(), localCui);

               List<ValidationBean> validazioni = new ArrayList<ValidationBean>();
               validazioni.add(validation);

               feedBackWriter.fillStandardFeedBack( feedBack , validazioni, null,
                     TipiSchedeType.ADESIONE.toString(), item.getDatiComuni().getCIG(),
                     localCui, 1, null, null);                   

            }
            
         }
      }
      
      return trasferimentoDati.getSchedeArray();
   }

   public OrigineSchedaEnum getOrigine() {
      return origine;
   }

   public Connection getCon() {
      return con;
   }

   public Logger getLogger() {
      return logger;
   }
   
   private boolean checkDelega(int delega, boolean postDelega, String utente_gara, String utente_connesso) {
	   return (delega==Costanti.DELEGA1 || delega==Costanti.DELEGA2) && !postDelega && utente_gara.equals(utente_connesso);
   }
   private boolean checkDelegaStipula(int delega, boolean postDelega, String utente_gara, String utente_connesso) {
	   return delega==Costanti.DELEGA1 && !postDelega && utente_gara.equals(utente_connesso);
   }

}
