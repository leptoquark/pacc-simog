package it.avlp.simog.massload;

import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.beans.StatoScheda;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.flusso.OperazioneScheda;
import it.avlp.simog.flusso.business.LoadSituazioneBusiness;
import it.avlp.simog.massload.actions.EliminazioneSchedeAction;
import it.avlp.simog.massload.actions.InserimentoException;
import it.avlp.simog.massload.actions.InserimentoSchedeAction;
import it.avlp.simog.massload.bean.IdsSchedaXML;
import it.avlp.simog.massload.bean.schede.TutteLeSchede;
import it.avlp.simog.massload.caricamento.CaricamentoBusiness;
import it.avlp.simog.massload.esito.EsitoOperazioneCancellazioneBean;
import it.avlp.simog.massload.esito.EsitoOperazioneControlloLogico;
import it.avlp.simog.massload.esito.EsitoOperazioneInserimentoOModifica;
import it.avlp.simog.massload.esito.EsitoValidazioneBean;
import it.avlp.simog.massload.manager.DbManager;
import it.avlp.simog.massload.util.CigTool;
import it.avlp.simog.massload.util.DataMerger;
import it.avlp.simog.massload.util.FeedBackWriterBase;
import it.avlp.simog.massload.util.FeedBackWriterValidationsBeans;
import it.avlp.simog.massload.util.conversion.ConvertXMLtoBeanBusiness;
import it.avlp.simog.massload.validation.MassloaderValidator;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede;
import it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class InserimentoLayer {

	private static String INVOKER = "InserimentoLayer";
	private String CONNECTIONID = "";
	private Connection con;
	private Logger logger;
	private FeedBackWriterValidationsBeans feedBackWriter;
	private FeedBack feedback;
	private String user;
	private OrigineSchedaEnum origine;
	
	// serve a gestire la gestione delle schede a livello cig (rollback e non processamento)
	public ArrayList<String> listOfCigNonValidi;
	
	private TreeMap<String, Integer> listOfWarning;
	
	public InserimentoLayer(Connection con, Logger logger,
	      FeedBackWriterValidationsBeans feedBackWriter, 
	      FeedBack feedback,
	      String user,
	      OrigineSchedaEnum origine) {
		this.con = con;
		this.CONNECTIONID = con.toString();
		this.logger = logger;
		this.feedBackWriter = feedBackWriter;
		this.feedback = feedback;
		this.user = user;
		this.origine = origine;
	}
	
	public void eseguiOperazione(InserimentoSchedeAction action, MassloaderValidator validator, ArrayList<IdsSchedaXML> schedeInInserimento,
			List<SoggettoPartecipanteBean> listOfAnaPartecipanti, List<SoggettoResponsabileBean> listOfAnaResponsabili, ConvertXMLtoBeanBusiness converter,
			CaricamentoBusiness loader) throws Exception{
		try{			
//			con.setAutoCommit(false);
			DbManager.staticSetAutoCommitFalse(con, logger, INVOKER, CONNECTIONID);
			
			if(!"<SchedaCompleta/>".equals(schedeInInserimento.get(0).getScheda().xmlText())){
				/**
				  *  siccome ho modificato la cardinalita' devo controllare di NON invocare la cancellazione piu volte sullo stesso cig
				  *  siccome in ogni caso anche quando la scheda di inserimento non ha elementi capita qui, effettuo un controllo che mi permette 
				  *  di  evitare di effettuare un'operazione quando non e' necessario
				  * 
				  **/
				CigTool cigTool = new CigTool();
				
				for(IdsSchedaXML ids : schedeInInserimento){

					// se la scheda necessita di cancellazione		
					boolean necessitaDiCancellazione = ids.isNeedCancellazione();
					if(necessitaDiCancellazione){
						 
						String actualCIG = ids.getCig();
						// se NON e' gia stata cancellata
						if(!cigTool.isAlreadyOperazioneEffettuata(ids.getCig())){
							
							logger.info("CIG che necessita cancellazione: " + actualCIG);
							// devo ricaricare le situazioni per cig (per via della cardinalita')
							LoadSituazioneBusiness situazioneLoader = new LoadSituazioneBusiness(con, logger, this.origine);
							ArrayList<SituazioneSchedeAttuale> situazioniDbCigAttuale = situazioneLoader.loadSituazioneByCIG(actualCIG);
							
							// ricostruisco l'oggetto (tag) che mi serve per lo strato di cancellazione
							RecIdSchedaElimType schedaDaCancellareCorrente = RecIdSchedaElimType.Factory.newInstance();
							schedaDaCancellareCorrente.setCIG(actualCIG);
							schedaDaCancellareCorrente.setCUI(""); // e' indispensabile settare la stringa vuota
							// istanzio il business di cancellazione
							EliminazioneSchedeAction eliminazioneAction = new EliminazioneSchedeAction(con, logger, user);
							// controllo per sicurezza che il cig sia cancellabile	(Nel caso dell'inserimento dovrebbe gia essere stato controllato.. lo levo qui ?)							
							EsitoOperazioneCancellazioneBean esitoControlloCorrettezza = eliminazioneAction.controllaCorrettezzaFlussoCIG(EliminazioneSchedeAction.ELIMBYCIG,
																								situazioniDbCigAttuale, schedaDaCancellareCorrente);
							// se e' cancellabile	
							if(esitoControlloCorrettezza.isEsitoOperazione()){	
							   
							   // is3028_RFWEBSC00Active

							   StatoScheda savDc = situazioniDbCigAttuale.get(0).getStatoDatiComuni();
							   StatoScheda savAg = situazioniDbCigAttuale.get(0).getStatoAggiudicazione();
                        StatoScheda savEs = situazioniDbCigAttuale.get(0).getStatoEscluso();
                        StatoScheda savSo = situazioniDbCigAttuale.get(0).getStatoSottosoglia();
                        
                        // PP 07.10.2014 si perdeva l'origine per le adesioni
                        StatoScheda savAd = situazioniDbCigAttuale.get(0).getStatoAdesione();
                        
								// cancella
								EsitoOperazioneCancellazioneBean esitoEliminazione = eliminazioneAction.eliminaScheda(situazioniDbCigAttuale, schedaDaCancellareCorrente);
								
								// se l'operazione di cancellazione e' avvenuta con successo
								if(esitoEliminazione.isEsitoOperazione()){
									// aggiungi al cig tool per evitare di ripertere la stessa azione per lo stesso cig
									cigTool.add(actualCIG, true);
									// azzero lo stato della situazione attuale del db visto che ho cancellato tutto
									
									ids.setSituazioneAttuale(new SituazioneSchedeAttuale());
									
									// is3028_RFWEBSC00Active ripristino l'origine per dati comuni e aggiudicazione
									ids.getSituazioneAttuale().getStatoDatiComuni().setOrigine(savDc.getOrigine());
									ids.getSituazioneAttuale().getStatoAggiudicazione().setOrigine(savAg.getOrigine());
									ids.getSituazioneAttuale().getStatoEscluso().setOrigine(savEs.getOrigine());
									ids.getSituazioneAttuale().getStatoSottosoglia().setOrigine(savSo.getOrigine());
									
									// PP 07.10.2014 si perdeva l'origine per le adesioni
                           ids.getSituazioneAttuale().getStatoAdesione().setOrigine(savAd.getOrigine());

									// devo aggiungere il bind ereditario alle situazioni che coinvolgono tutti i dati comuni..
									bindDatiComuniOverSituazioni(ids.getSituazioneAttuale(), schedeInInserimento, actualCIG);
								}else{
//									feedback per il fallimento della cancellazione della scheda
									List<SchedaSpecificaValidationBean> listOfvalidations =
										esitoEliminazione.isSingola() ?  esitoEliminazione.getReportSingolaScheda().getListOfValidationsBeans() : esitoEliminazione.getReportPiuSchede().getAll();
									feedBackWriter.fillMassloaderFeedBack(feedback, listOfvalidations, OperazioneScheda.getCancellazione().getNomeOperazione());
//										XX-X: gestione livello cig
									if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
									this.listOfCigNonValidi.add(schedaDaCancellareCorrente.getCIG());	
									//
//									con.rollback();
									DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
								}
							}else{
//								feedback per il fallimento della validazione della correttezza del flusso	
								List<SchedaSpecificaValidationBean> listOfvalidations =
									esitoControlloCorrettezza.isSingola() ?  esitoControlloCorrettezza.getReportSingolaScheda().getListOfValidationsBeans() : esitoControlloCorrettezza.getReportPiuSchede().getAll();
								feedBackWriter.fillMassloaderFeedBack(feedback, listOfvalidations, OperazioneScheda.getCancellazione().getNomeOperazione());
//									XX-X: gestione livello cig
								if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
								this.listOfCigNonValidi.add(schedaDaCancellareCorrente.getCIG());
								//
//								con.rollback();
								DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
							}
						
						}
						
//VL - 15-12-2009 NON piu valido se e' gia stata cancellata, aggiorna solo la situazione attuale db (vuota)
//						Ora vale il discorso dell'ereditarieta', ovvero se la cancellazione ha successo aggiorno a vuoto la situazione attuale
//						che sara' si spera ereditaria, si effettua l'inserimento e viene ancora aggiornata la situazione attuale
//						il successivo cig nel file se uguale dovrebbe gia contenere gli aggiornamenti ..Verificare..
//						else{
//							ids.setSituazioneAttuale(new SituazioneSchedeAttuale());
//						}
					}


					// completate le eventuali operazioni di cancellazione esegui l'operazione di inserimento					
					// solamente se l'operazione ha avuto successo, ovverosia se il cig non e' presente nella lista dei cig non validi
					if(this.listOfCigNonValidi == null || (this.listOfCigNonValidi != null && !this.listOfCigNonValidi.contains(ids.getCig()))){
						
						controllaEInserisci(action, validator, ids, listOfAnaPartecipanti, listOfAnaResponsabili, converter, loader);
						
					}
								
				}

//				logger.debug("\r\n\t\t\t####\r\nParziale Inserimento FeedBack..:\r\n" +feedback.toString()+"\r\n\t\t\t####\r\n");
			}
		}catch(Exception e){
			e.printStackTrace();
			DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
            String mesg = e.getMessage() + " - " + FeedBackWriterBase.getStack(e);
			throw new Exception(mesg);
			
			//  try catch applicativo.. costruttore di validation bean un po' mongoloide 
//			try{
//				String mesg = Messaggi.SIMOG_MASSLOADER_205 + " - " + e.getClass().getName() + " - " + FeedBackWriterBase.getStack(e);
//				ValidationBean validation = new ValidationBean(mesg,ValidationBean.VALBEAN_SEV_ERR,0);
//				List<ValidationBean> list = new ArrayList<ValidationBean>();
//				list.add(validation);
//				feedBackWriter.fillStandardFeedBack(feedback, list, "", "", "", "", 0, "", "");
//			}catch(Exception e1){}
			
		}finally{
			DbManager.staticSetAutoCommitTrue(con, logger, INVOKER, CONNECTIONID);
			logger.debug("Operazioni di inserimento Terminate");
		}
	}
	
	/**
	 * Attenzione possibili problemi cross cui.. quindi il settaggio ereditario qui avviene sulla situazione
	 * dei soli dati comuni..
	 * 
	 * @param situazioneCorrente
	 * @param schedeInInserimento
	 * @param cig
	 */
	public void bindDatiComuniOverSituazioni(SituazioneSchedeAttuale situazioneCorrente,ArrayList<IdsSchedaXML> schedeInInserimento, String cig){
		for(IdsSchedaXML idsCorrente : schedeInInserimento){
			if(idsCorrente.getCig().equals(cig)){
				idsCorrente.getSituazioneAttuale().setStatoDatiComuni(situazioneCorrente.getStatoDatiComuni());
			}
		}
	}
	/**
	 * Astratto in un metodo per semplicita' di integrazione nel flusso delle cancellazioni.
	 * Effettua le operazioni di controllo e inserimento, il metodo effettua il commit nel caso di inserimenti effettuati con il successo
	 * 
	 * @throws Exception
	 */
	private void controllaEInserisci(
				InserimentoSchedeAction action, 
				MassloaderValidator validator,
//VL - 15/12/2009				ArrayList<IdsSchedaXML> schedeInInserimento,
				IdsSchedaXML ids,
			List<SoggettoPartecipanteBean> listOfAnaPartecipanti, 
			List<SoggettoResponsabileBean> listOfAnaResponsabili, 
			ConvertXMLtoBeanBusiness converter,
			CaricamentoBusiness loader)throws Exception{
		
	   
	   EsitoValidazioneBean esitoDatiComuni = null;
	   EsitoValidazioneBean esitoAggiudicazione = null;
	   EsitoValidazioneBean esitoAdesione = null;
	   
		// effettuo il controllo logico.
//VL - 15/12/2009				EsitoOperazioneControlloLogico esitoControlloLogico = action.controllaCorrettezzaLogica(schedeInInserimento);
		EsitoOperazioneControlloLogico esitoControlloLogico = action.controllaCorrettezzaLogica(ids);
		
		if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
		
		if(esitoControlloLogico.isSomeSchedeNonValide()){
			logger.debug("Al controllo logico sono risutate non valide alcune schede");
			feedBackWriter.fillMassloaderFeedBack(feedback, esitoControlloLogico.getListOfValidationsBeans(), OperazioneScheda.INSERIMENTO);
			
			this.listOfCigNonValidi.addAll(esitoControlloLogico.getListOfCigNonValidi());
		}
		//controllo per riaggiudicazione
		if(esitoControlloLogico.isEsitoOperazione() && ids.getSituazioneAttualeXml().isPresentAggiudicazione()){
			esitoControlloLogico = action.controllaEsistenzaRiaggiudicazione(ids);
			feedBackWriter.fillMassloaderFeedBack(feedback, esitoControlloLogico.getListOfValidationsBeans(), OperazioneScheda.INSERIMENTO);
			this.listOfCigNonValidi.addAll(esitoControlloLogico.getListOfCigNonValidi());
		}
		//gm nuovo controllo per permettere l'inserimento di una sola aggiudicazione, di qualsiasi sottotipo
		if(esitoControlloLogico.isEsitoOperazione()){
			if(ids.getSituazioneAttualeXml().isPresentAggiudicazione() ||
				ids.getSituazioneAttualeXml().isPresentEscluso() ||
				ids.getSituazioneAttualeXml().isPresentSottosoglia() ||
				ids.getSituazioneAttualeXml().isPresentAdesione()){
	        		esitoControlloLogico = action.controllaEsistenzaAggiudicazione(ids);
	        		feedBackWriter.fillMassloaderFeedBack(feedback, esitoControlloLogico.getListOfValidationsBeans(), OperazioneScheda.INSERIMENTO);
	        		this.listOfCigNonValidi.addAll(esitoControlloLogico.getListOfCigNonValidi());
			}
		}
    	// Se ci sono ancora schede valide dopo il controllo formale e logico..
		if(esitoControlloLogico.isEsitoOperazione()){

			//ArrayList<IdsSchedaXML> schedeDaInserire = esitoControlloLogico.getListOfSchedeValide();
			
			// basta una istanziazione..							
			DataMerger merger = new DataMerger(converter, loader);
			
			// per evitare nullpointer nel caso in cui la lista sia nulla per via di qualche eccezione
//			if(schedeDaInserire == null){
//				schedeDaInserire = new ArrayList<IdsSchedaXML>();
//			}
	
//			for(IdsSchedaXML ids : schedeDaInserire){
				
				if(this.listOfCigNonValidi == null || (this.listOfCigNonValidi != null && !this.listOfCigNonValidi.contains(ids.getCig()))){
					
					InfoGaraBean infoGara = action.getInfoGara(ids.getCig());
					TutteLeSchede tutteLeSchede = merger.mergePerInserimento(ids.getScheda(), 
							ids.getSituazioneAttuale(), ids.getSituazioneAttualeXml(), 
							ids.getCrossFieldsForInfoComuniValidation(), 
							listOfAnaResponsabili, listOfAnaPartecipanti, infoGara);
					
					if(merger.containsDuplicate){
						feedBackWriter.fillStandardFeedBack(feedback, merger.listOfDuplicateWarning, OperazioneScheda.INSERIMENTO,
								null, ids.getCig(), ids.getCui(), ids.getCardinalitaSchedaCompleta(), null, null);
						
						
						//(test.getFeedbackXml().getFeedBack(), merger.listOfDuplicateWarning, OperazioneScheda.INSERIMENTO);
					}
					boolean esisteUnaSchedaNonValida = false;
					String cig = ids.getCig();
					String cui = ids.getCui();
					
					String cuiFromXml = ids.getSituazioneAttualeXml().isPresentCUI() ? ids.getScheda().getSchedaCompletaArray(0).getCUI() : cui;
					
					int progressivoSchedaCompleta = ids.getCardinalitaSchedaCompleta();
					int elemento = ids.getCardinalitaSchedaCig();

					/** validazione schede singole.. **/
					if(ids.getSituazioneAttuale().getStatoDatiComuni().isFromXml()){
// PP cui vuoto per i dati comuni						esitoDatiComuni = validator.validaDatiComuni(tutteLeSchede.getSchedaA(),cig,cui,progressivoSchedaCompleta);
                 esitoDatiComuni = validator.validaDatiComuni(tutteLeSchede.getSchedaA(),cig,"",progressivoSchedaCompleta);

				      if(SimogFlags.is3028_RFWEBSC00Active()){
				         // se ci sono errori ma la scheda mi sta arrivando da avcpass metto comunque esito positivo
				         if(this.origine.code() == OrigineSchedaEnum.AVCPASS.code())
				            esitoDatiComuni.setEsitoOperazione(true);
				      }

						if(!esitoDatiComuni.isEsitoOperazione()){
							esisteUnaSchedaNonValida = true;
							if(SchedaSpecificaValidationBean.checkForWarnings(esitoDatiComuni.getListOfValidations())){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}else{
							if(esitoDatiComuni.getListOfValidations() != null && esitoDatiComuni.getListOfValidations().size() > 0){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}
						feedBackWriter.fillMassloaderFeedBack(feedback, esitoDatiComuni.getListOfValidations(), OperazioneScheda.INSERIMENTO);

					}
					AnomalieSchede anomaliePerIlCuiCorrente = null;
					if(ids.getSituazioneAttuale().getStatoAggiudicazione().isFromXml()){
						esitoAggiudicazione = validator.validaAggiudicazione(tutteLeSchede.getSchedaA(),cig,cui,progressivoSchedaCompleta);
						
                  if(SimogFlags.is3028_RFWEBSC00Active()){
                     // se ci sono errori ma la scheda mi sta arrivando da avcpass metto comunque esito positivo
                     if(this.origine.code() == OrigineSchedaEnum.AVCPASS.code())
                        esitoAggiudicazione.setEsitoOperazione(true);
                  }
                  
						if(!esitoAggiudicazione.isEsitoOperazione()){
							esisteUnaSchedaNonValida = true;
							if(SchedaSpecificaValidationBean.checkForWarnings(esitoAggiudicazione.getListOfValidations())){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}else{
							if(esitoAggiudicazione.getListOfValidations() != null && esitoAggiudicazione.getListOfValidations().size() > 0){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}
						anomaliePerIlCuiCorrente = feedBackWriter.costruisciListaAnomalia(feedback, esitoAggiudicazione.getListOfValidations(), OperazioneScheda.INSERIMENTO,null,cig,cuiFromXml,progressivoSchedaCompleta);

					}
					
					if(ids.getSituazioneAttuale().getStatoAdesione().isFromXml()){
						esitoAdesione = validator.validaAdesione(tutteLeSchede.getSchedaA(),cig,cui,progressivoSchedaCompleta);
						if(!esitoAdesione.isEsitoOperazione()){
							esisteUnaSchedaNonValida = true;
							if(SchedaSpecificaValidationBean.checkForWarnings(esitoAdesione.getListOfValidations())){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}else{
							if(esitoAdesione.getListOfValidations() != null && esitoAdesione.getListOfValidations().size() > 0){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}
						anomaliePerIlCuiCorrente = feedBackWriter.costruisciListaAnomalia(feedback, esitoAdesione.getListOfValidations(), OperazioneScheda.INSERIMENTO,null,cig,cuiFromXml,progressivoSchedaCompleta);

					}
					
					if(ids.getSituazioneAttuale().getStatoSottosoglia().isFromXml()){
						esitoAggiudicazione = validator.validaSottosoglia(tutteLeSchede.getSchedaA(),cig,cui,progressivoSchedaCompleta);
						
                  if(SimogFlags.is3028_RFWEBSC00Active()){
                     // se ci sono errori ma la scheda mi sta arrivando da avcpass metto comunque esito positivo
                     if(this.origine.code() == OrigineSchedaEnum.AVCPASS.code())
                        esitoAggiudicazione.setEsitoOperazione(true);
                  }
                  
						if(!esitoAggiudicazione.isEsitoOperazione()){
							esisteUnaSchedaNonValida = true;
							if(SchedaSpecificaValidationBean.checkForWarnings(esitoAggiudicazione.getListOfValidations())){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}else{
							if(esitoAggiudicazione.getListOfValidations() != null && esitoAggiudicazione.getListOfValidations().size() > 0){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}
						anomaliePerIlCuiCorrente = feedBackWriter.costruisciListaAnomalia(feedback, esitoAggiudicazione.getListOfValidations(), OperazioneScheda.INSERIMENTO,null,cig,cuiFromXml,progressivoSchedaCompleta);

					}
					
					if(ids.getSituazioneAttuale().getStatoEscluso().isFromXml()){
						esitoAggiudicazione = validator.validaEscluso(tutteLeSchede.getSchedaA(),cig,cui,progressivoSchedaCompleta);
						
                  if(SimogFlags.is3028_RFWEBSC00Active()){
                     // se ci sono errori ma la scheda mi sta arrivando da avcpass metto comunque esito positivo
                     if(this.origine.code() == OrigineSchedaEnum.AVCPASS.code())
                        esitoAggiudicazione.setEsitoOperazione(true);
                  }
                  
						if(!esitoAggiudicazione.isEsitoOperazione()){
							esisteUnaSchedaNonValida = true;
							if(SchedaSpecificaValidationBean.checkForWarnings(esitoAggiudicazione.getListOfValidations())){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}else{
							if(esitoAggiudicazione.getListOfValidations() != null && esitoAggiudicazione.getListOfValidations().size() > 0){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}
						anomaliePerIlCuiCorrente = feedBackWriter.costruisciListaAnomalia(feedback, esitoAggiudicazione.getListOfValidations(), OperazioneScheda.INSERIMENTO,null,cig,cuiFromXml,progressivoSchedaCompleta);

					}
					if(ids.getSituazioneAttuale().getStatoInizioLavori() .isFromXml()){
						EsitoValidazioneBean esitoInizio = validator.validaInizioLavori(tutteLeSchede.getSchedaInizio(),cig,cui,progressivoSchedaCompleta);
						if(!esitoInizio.isEsitoOperazione()){
							esisteUnaSchedaNonValida = true;
							if(SchedaSpecificaValidationBean.checkForWarnings(esitoInizio.getListOfValidations())){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}else{
							if(esitoInizio.getListOfValidations() != null && esitoInizio.getListOfValidations().size() > 0){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}
						anomaliePerIlCuiCorrente = feedBackWriter.costruisciListaAnomalia(feedback, esitoInizio.getListOfValidations(), OperazioneScheda.INSERIMENTO,anomaliePerIlCuiCorrente,cig,cuiFromXml,progressivoSchedaCompleta);

					}
					if(ids.getSituazioneAttuale().getStatoStipula().isFromXml()){
						EsitoValidazioneBean esitoStipula = validator.validaStipula(tutteLeSchede.getSchedaStipula(),cig,cui,progressivoSchedaCompleta);
						if(!esitoStipula.isEsitoOperazione()){
							esisteUnaSchedaNonValida = true;
							if(SchedaSpecificaValidationBean.checkForWarnings(esitoStipula.getListOfValidations())){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}else{
							if(esitoStipula.getListOfValidations() != null && esitoStipula.getListOfValidations().size() > 0){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}
						anomaliePerIlCuiCorrente = feedBackWriter.costruisciListaAnomalia(feedback, esitoStipula.getListOfValidations(), OperazioneScheda.INSERIMENTO,anomaliePerIlCuiCorrente,cig,cuiFromXml,progressivoSchedaCompleta);
					}
					if(ids.getSituazioneAttuale().getStatoConclusione() .isFromXml()){
						EsitoValidazioneBean esitoFine = validator.validaConclusione(tutteLeSchede.getSchedaConclusione(),cig,cui,progressivoSchedaCompleta);
						if(!esitoFine.isEsitoOperazione()){
							esisteUnaSchedaNonValida = true;
							if(SchedaSpecificaValidationBean.checkForWarnings(esitoFine.getListOfValidations())){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}else{
							if(esitoFine.getListOfValidations() != null && esitoFine.getListOfValidations().size() > 0){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}
						anomaliePerIlCuiCorrente = feedBackWriter.costruisciListaAnomalia(feedback, esitoFine.getListOfValidations(), OperazioneScheda.INSERIMENTO,anomaliePerIlCuiCorrente,cig,cuiFromXml,progressivoSchedaCompleta);

					}
					if(ids.getSituazioneAttuale().getStatoCollaudo() .isFromXml()){
						EsitoValidazioneBean esitoCollaudo = validator.validaCollaudo(tutteLeSchede.getSchedaCollaudo(),cig,cui,progressivoSchedaCompleta);
						if(!esitoCollaudo.isEsitoOperazione()){
							esisteUnaSchedaNonValida = true;
							if(SchedaSpecificaValidationBean.checkForWarnings(esitoCollaudo.getListOfValidations())){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}else{
							if(esitoCollaudo.getListOfValidations() != null && esitoCollaudo.getListOfValidations().size() > 0){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}
						anomaliePerIlCuiCorrente = feedBackWriter.costruisciListaAnomalia(feedback, esitoCollaudo.getListOfValidations(), OperazioneScheda.INSERIMENTO,anomaliePerIlCuiCorrente,cig,cuiFromXml,progressivoSchedaCompleta);

					}
					
					/** validazione schede multiple **/
					boolean notEmpty = ids.getSituazioneAttuale().getStatoAvanzamento() != null && ids.getSituazioneAttuale().getStatoAvanzamento().size() > 0;
					
					if(notEmpty && ids.getSituazioneAttuale().getStatoAvanzamento().get(0) .isFromXml()){
						EsitoValidazioneBean esitoAvanzamenti = validator.validaAvanzamenti(tutteLeSchede.getSchedeAvanzamento(),ids.getAvanzamentiPosizioneInserimento(),cig,cui,progressivoSchedaCompleta);
						if(!esitoAvanzamenti.isEsitoOperazione()){
							esisteUnaSchedaNonValida = true;
							if(SchedaSpecificaValidationBean.checkForWarnings(esitoAvanzamenti.getListOfValidations())){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}else{
							if(esitoAvanzamenti.getListOfValidations() != null && esitoAvanzamenti.getListOfValidations().size() > 0){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}
						anomaliePerIlCuiCorrente = feedBackWriter.costruisciListaAnomalia(feedback, esitoAvanzamenti.getListOfValidations(), OperazioneScheda.INSERIMENTO,anomaliePerIlCuiCorrente,cig,cuiFromXml,progressivoSchedaCompleta);

					}
					notEmpty = ids.getSituazioneAttuale().getStatoAccordi() != null && ids.getSituazioneAttuale().getStatoAccordi().size() > 0;
					if( notEmpty && ids.getSituazioneAttuale().getStatoAccordi().get(0).isFromXml()){
						EsitoValidazioneBean esitoAccordi = validator.validaAccordi(tutteLeSchede.getSchedeAccordo(),ids.getAccordiPosizioneInserimento(),cig,cui,progressivoSchedaCompleta);
						if(!esitoAccordi.isEsitoOperazione()){
							esisteUnaSchedaNonValida = true;
							if(SchedaSpecificaValidationBean.checkForWarnings(esitoAccordi.getListOfValidations())){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}else{
							if(esitoAccordi.getListOfValidations() != null && esitoAccordi.getListOfValidations().size() > 0){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}
						anomaliePerIlCuiCorrente = feedBackWriter.costruisciListaAnomalia(feedback, esitoAccordi.getListOfValidations(), OperazioneScheda.INSERIMENTO,anomaliePerIlCuiCorrente,cig,cuiFromXml,progressivoSchedaCompleta);

					}
					notEmpty = ids.getSituazioneAttuale().getStatoRitardo() != null && ids.getSituazioneAttuale().getStatoRitardo().size() > 0;
					if( notEmpty && ids.getSituazioneAttuale().getStatoRitardo().get(0) .isFromXml()){
						EsitoValidazioneBean esitoRitardi = validator.validaRitardi(tutteLeSchede.getSchedeRitardi(),ids.getRitardiPosizioneInserimento(),cig,cui,progressivoSchedaCompleta);
						if(!esitoRitardi.isEsitoOperazione()){
							esisteUnaSchedaNonValida = true;
							if(SchedaSpecificaValidationBean.checkForWarnings(esitoRitardi.getListOfValidations())){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}else{
							if(esitoRitardi.getListOfValidations() != null && esitoRitardi.getListOfValidations().size() > 0){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}
						anomaliePerIlCuiCorrente = feedBackWriter.costruisciListaAnomalia(feedback, esitoRitardi.getListOfValidations(), OperazioneScheda.INSERIMENTO,anomaliePerIlCuiCorrente,cig,cuiFromXml,progressivoSchedaCompleta);

					}
					notEmpty = ids.getSituazioneAttuale().getStatoSospensioni() != null && ids.getSituazioneAttuale().getStatoSospensioni().size() > 0;
					if( notEmpty && ids.getSituazioneAttuale().getStatoSospensioni().get(0) .isFromXml()){
						EsitoValidazioneBean esitoSospensioni= validator.validaSospensioni(tutteLeSchede.getSchedeSospensione(),ids.getSospensioniPosizioneInserimento(), cig,cui,progressivoSchedaCompleta);
						if(!esitoSospensioni.isEsitoOperazione()){
							esisteUnaSchedaNonValida = true;
							if(SchedaSpecificaValidationBean.checkForWarnings(esitoSospensioni.getListOfValidations())){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}else{
							if(esitoSospensioni.getListOfValidations() != null && esitoSospensioni.getListOfValidations().size() > 0){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}
						anomaliePerIlCuiCorrente = feedBackWriter.costruisciListaAnomalia(feedback, esitoSospensioni.getListOfValidations(), OperazioneScheda.INSERIMENTO,anomaliePerIlCuiCorrente,cig,cuiFromXml,progressivoSchedaCompleta);

					}
					notEmpty = ids.getSituazioneAttuale().getStatoSubAppalti() != null && ids.getSituazioneAttuale().getStatoSubAppalti().size() > 0;
					if( notEmpty && ids.getSituazioneAttuale().getStatoSubAppalti().get(0) .isFromXml()){
						EsitoValidazioneBean esitoSubAppalti = validator.validaSubAppalti(tutteLeSchede.getSchedeSubAppalto(),ids.getSubappaltiPosizioneInserimento(),cig,cui,progressivoSchedaCompleta);
						if(!esitoSubAppalti.isEsitoOperazione()){
							esisteUnaSchedaNonValida = true;
							if(SchedaSpecificaValidationBean.checkForWarnings(esitoSubAppalti.getListOfValidations())){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}else{
							if(esitoSubAppalti.getListOfValidations() != null && esitoSubAppalti.getListOfValidations().size() > 0){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}
						anomaliePerIlCuiCorrente = feedBackWriter.costruisciListaAnomalia(feedback, esitoSubAppalti.getListOfValidations(), OperazioneScheda.INSERIMENTO,anomaliePerIlCuiCorrente,cig,cuiFromXml,progressivoSchedaCompleta);

					}
					notEmpty = ids.getSituazioneAttuale().getStatoVarianti() != null && ids.getSituazioneAttuale().getStatoVarianti().size() > 0;
					if( notEmpty && ids.getSituazioneAttuale().getStatoVarianti().get(0) .isFromXml()){
						EsitoValidazioneBean esitoVarianti = validator.validaVariante(tutteLeSchede.getSchedeVariante(),ids.getVariantiPosizioneInserimento(),cig,cui,progressivoSchedaCompleta);
						if(!esitoVarianti.isEsitoOperazione()){
							esisteUnaSchedaNonValida = true;
							if(SchedaSpecificaValidationBean.checkForWarnings(esitoVarianti.getListOfValidations())){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}else{
							if(esitoVarianti.getListOfValidations() != null && esitoVarianti.getListOfValidations().size() > 0){
								Integer currentValueForThisCig = listOfWarning.get(ids.getCig());
								if(currentValueForThisCig == null) listOfWarning.put(ids.getCig(), new Integer(1));
							}
						}
						anomaliePerIlCuiCorrente = feedBackWriter.costruisciListaAnomalia(feedback, esitoVarianti.getListOfValidations(), OperazioneScheda.INSERIMENTO,anomaliePerIlCuiCorrente,cig,cuiFromXml,progressivoSchedaCompleta);

					}
					if(!esisteUnaSchedaNonValida || this.origine.code() == OrigineSchedaEnum.AVCPASS.code()){
						logger.debug("Procedo con il controllo di flusso");
						EsitoOperazioneControlloLogico esitoFlux = action.controllaCorrettezzaFlussoConProgressivo(ids,user);
						
						if(esitoFlux.isEsitoOperazione()){
							logger.debug("Procedo con l'inserimento..");
							try{
							   boolean noConfirmAgg = this.origine.code() == OrigineSchedaEnum.AVCPASS.code() 
                              && ((esitoAggiudicazione != null && !esitoAggiudicazione.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).isEmpty())
                                    || (esitoAdesione != null && !esitoAdesione.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).isEmpty())
                                  );
							   
								/** Nota che o effettua l'inserimento correttamente oppure lancia un'accezione..**/
								EsitoOperazioneInserimentoOModifica esitoInserimento 
								   = action.inserisciScheda(ids, tutteLeSchede,
								         this.origine.code() == OrigineSchedaEnum.AVCPASS.code() 
								            && !(esitoDatiComuni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).isEmpty()),
	                                 noConfirmAgg
								         );
								logger.debug("Tentativo di inserimento.. Riuscito");

								//								con.commit();
								DbManager.staticCommit(con, logger, INVOKER, CONNECTIONID);
								
								feedBackWriter.fillMassloaderFeedBack(feedback, esitoInserimento.getListOfSuccess(), OperazioneScheda.INSERIMENTO);
								cui = esitoInserimento.getCui();
								//logger.debug("\r\n\t\t\t####\r\nParziale Inserimento FeedBack..:\r\n" +feedback.toString()+"\r\n\t\t\t####\r\n");
							}catch (InserimentoException insExc) {
								logger.debug("Tentativo di inserimento.. Fallito");
								// scarico gli errori sul feedback
                                feedBackWriter.fillMassloaderFeedBack(feedback, action.getEsitiOperazioni(), insExc.getOperazione().getNomeOperazione());

//								con.rollback();
								DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
								
								SchedaSpecificaValidationBean validation = 
									new SchedaSpecificaValidationBean(insExc.getMessaggio(),ValidationBean.VALBEAN_SEV_ERR,
											elemento, progressivoSchedaCompleta,0,
											insExc.getIdentificativo().getNomeScheda(),insExc.getCig(),action.amIInsertingAggiudicazioni ? "" : insExc.getCui(),
											insExc.getIdSimog(),insExc.getIdLocale());
//								}
								feedBackWriter.fillMassloaderFeedBack(feedback, validation, insExc.getOperazione().getNomeOperazione());
								if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
								this.listOfCigNonValidi.add(ids.getCig());
							}
						}else{
							feedBackWriter.fillMassloaderFeedBack(feedback, esitoFlux.getListOfValidationsBeans(), OperazioneScheda.INSERIMENTO);
							if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
							this.listOfCigNonValidi.add(ids.getCig());
							// 
//							con.rollback();
							DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
						}
					}else{
						if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
						this.listOfCigNonValidi.add(ids.getCig());
						// 
//						con.rollback();
						DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
					}
					// XXX: scrittura dei warnings
					feedBackWriter.addListAnomaliaSafely(feedback, anomaliePerIlCuiCorrente, cui);
				}				
//			}
		}else{
			// nel caso in cui abbia effettuato la cancellazione e fallisce la validazione della scheda rollback, caso ancora non gestito.
//			Il log e il feedback viene fatto alla riga 192
			DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
			
		}
		
	}

	public TreeMap<String, Integer> getListOfWarning() {
		return listOfWarning;
	}

	public void setListOfWarning(TreeMap<String, Integer> listOfWarning) {
		this.listOfWarning = listOfWarning;
	}	
}
