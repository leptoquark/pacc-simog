package it.avlp.simog.massload;

import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.CigException;
import it.avlp.simog.flusso.OperazioneScheda;
import it.avlp.simog.flusso.business.LoadSituazioneBusiness;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.massload.actions.EliminazioneSchedeAction;
import it.avlp.simog.massload.esito.EsitoOperazioneCancellazioneBean;
import it.avlp.simog.massload.manager.DbManager;
import it.avlp.simog.massload.util.FeedBackWriterValidationsBeans;
import it.avlp.simog.massload.util.comparators.SchedeComparator;
import it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack;
import it.avlp.simog.ws.commons.GaraLottoManager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Livello supplementare di astrazione per lo spostamento delle operazioni
 * ognuno nel suo livello
 * 
 * @author vletizia
 *
 */
public class CancellazioneLayer {

	private static String INVOKER = "CancellazioneLayer";
	private String CONNECTIONID = "";
	// oggetti condivisi dai diversi layer per questo vengono passati come argomento
	
	private Connection con;
	private Logger logger;
	private FeedBackWriterValidationsBeans feedBackWriter;
	private FeedBack feedback;
	private String user;
	private OrigineSchedaEnum origine;
	  
	// serve a gestire la gestione delle schede a livello cig (rollback e non processamento)
	public ArrayList<String> listOfCigNonValidi;
	
	public CancellazioneLayer(Connection con, Logger logger, FeedBackWriterValidationsBeans feedBackWriter, 
	      FeedBack feedback, String user, OrigineSchedaEnum origine){
		this.con = con;
		this.CONNECTIONID = con.toString();
		this.logger = logger;
		this.feedBackWriter = feedBackWriter;
		this.feedback = feedback;
		this.user = user;
		this.origine = origine;
	}
	
	/**
	 * Eseque tutte le operazioni relative alla cancellazione..
	 * 
	 * @param schedeDaElminare
	 */
	public void esequiOperazione(RecIdSchedaElimType[] schedeDaElminare){
		try{
			if(schedeDaElminare != null && schedeDaElminare.length > 0){
				
//				con.setAutoCommit(false);
				DbManager.staticSetAutoCommitFalse(con, logger,INVOKER, CONNECTIONID);
				
				Comparator<RecIdSchedaElimType> comparator = new SchedeComparator();				
				List<RecIdSchedaElimType> listOfRichCanc = Arrays.asList(schedeDaElminare);
				Collections.sort(listOfRichCanc, comparator);
				ArrayList<SituazioneSchedeAttuale> situazioniAttuali = new ArrayList<SituazioneSchedeAttuale>();
				SituazioneSchedeAttuale situazioneAttuale = new SituazioneSchedeAttuale();
				
				String cigPrecedente = null;
				
				for(RecIdSchedaElimType schedaDaCancellareCorrente : listOfRichCanc){
								
					String cig = schedaDaCancellareCorrente.getCIG();
					
// gestione eventuali rollback al livello cig, quindi faccio il commit solamente quando cambia il cig (la lista e' ordinata per cig appunto)
// Il rollback invece puo avvenire, per un qualunque errore [formale / flusso / eccezione] ed effettuera il rollback ti tutte le operazioni
// per quel cig, la lista listOfCigNonValidi si occupera' invece di prevenire ulteriori operazione su elmenti riportanti il cig in errore.
					if(cigPrecedente != null && !cigPrecedente.equals(cig)){
						
//						con.commit();
						DbManager.staticCommit(con, logger,INVOKER, CONNECTIONID);
						
						cigPrecedente = cig;
					}
					if(cigPrecedente == null){
						cigPrecedente = cig;
					}

	// soltanto se il cig non risulta nella lista dei cig Validi continua con il processamento della riga di cancellazione corrente
					if(this.listOfCigNonValidi == null || (this.listOfCigNonValidi != null && !this.listOfCigNonValidi.contains(cig))){
						try{
							String cui = schedaDaCancellareCorrente.getCUI();
							String nomeScheda = schedaDaCancellareCorrente.isSetSCHEDA() && schedaDaCancellareCorrente.getSCHEDA() != null ? schedaDaCancellareCorrente.getSCHEDA().toString(): null;								
							String idLocale = schedaDaCancellareCorrente.getIDSCHEDALOCALE();
							String idScheda = schedaDaCancellareCorrente.getIDSCHEDASIMOG();
							IdentificativoSchede identificativo = null;
							
							EliminazioneSchedeAction action = new EliminazioneSchedeAction(con, logger, user);

	                     // verifica esistenza CIG 
							InfoGaraBean igb = action.getInfoGara(schedaDaCancellareCorrente.getCIG());
							if(igb == null){
                                 SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(
                                       Messaggi.SIMOG_VALIDAZIONE_008, ValidationBean.VALBEAN_SEV_ERR,
                                       nomeScheda, cig, cui, idScheda, idLocale);
         
                                 feedBackWriter.fillMassloaderFeedBack(feedback, validation, OperazioneScheda.getCancellazione().getNomeOperazione());
                                 if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
                                 this.listOfCigNonValidi.add(schedaDaCancellareCorrente.getCIG());  
                                 DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);							
                                  
                                 continue;
							}
							
							// verifica blocco AVCPASS
      				        if(SimogFlags.is3028_RFWEBGL07Active()){
                             //GaraManager gman = new GaraManager(con, logger);
      				           LottoManager lman = new LottoManager(con, logger);
      				           GaraLottoManager glm = new GaraLottoManager(con, logger);
      				           List<Lotto> listaLotti = lman.getLottoByCigWS(cig);
      				           //Gara gara = gman.getGara(igb.getIdGara());
      				           if(glm.isAVCPass(null, listaLotti, AVCPassFunzioneEnum.ML_SCHEDA_AGGIUNTIVE_DELETE.getCodice())){
            	                    SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(
            	                             Messaggi.SIMOG_AVCPASS_001, ValidationBean.VALBEAN_SEV_ERR,
            	                             nomeScheda, cig, cui, idScheda, idLocale);

           	                        feedBackWriter.fillMassloaderFeedBack(feedback, validation, OperazioneScheda.getCancellazione().getNomeOperazione());
            	                    if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
            	                    this.listOfCigNonValidi.add(schedaDaCancellareCorrente.getCIG());  
            	                    DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);                    

            	                    continue;
      				            }
      				         }
							
							EsitoOperazioneCancellazioneBean esito = action.controllaCorrettezzaSchedeDaEliminare(schedaDaCancellareCorrente);
							int tipoCancellazione = -1;
		//									SE CONTROLLO CORRETTEZZA FORMALE OK PROCEDI
							if(esito.isEsitoOperazione()){
								tipoCancellazione = action.tipoDiCancellazione(schedaDaCancellareCorrente);
		//										SE IL TIPO CANCELLAZIONE E' TRAMITE CIG CONTROLLO CORRETTEZZA FLUSSO E' DIVERSO
								if(tipoCancellazione == EliminazioneSchedeAction.ELIMBYCIG){
		
									LoadSituazioneBusiness loader = new LoadSituazioneBusiness(con, logger, this.origine);
									situazioniAttuali = loader.loadSituazioneByCIG(cig);
									
		// XXX: e' stato deciso che fino a comunicazione contraria, se esiste una scheda "in richiesta" non deve essere
		// possibile cancellare / modificare / inserire schede.
									
									// implementazione per il rifiuto d'ufficio delle richieste (contraddice il fixme di sopra.)
									//esito = action.rifiutaDUfficioByCIG(situazioniAttuali, schedaDaCancellareCorrente, cig);
		
									
									esito = action.controllaCorrettezzaFlussoCIG(tipoCancellazione,situazioniAttuali, schedaDaCancellareCorrente);
		//											SE I CONTROLLI SONO TUTTI OK EFFETTUA LA CANCELLAZIONE (ITERATIVA IN QUESTO CASO + SITUAZIONI IN QUANTO + AGGIUDICAZIONI)
									if(esito.isEsitoOperazione()){
										
										esito = action.eliminaScheda(situazioniAttuali, schedaDaCancellareCorrente);
										
										if(esito.isEsitoOperazione()){
											// feedback per il successo della operazione di cancellazione.
											if(esito.isSingola()){
												feedBackWriter.fillMassloaderFeedBack(feedback, 
														esito.getReportSingolaScheda().getSuccess(), OperazioneScheda.getCancellazione().getNomeOperazione());
											}else{
												feedBackWriter.fillMassloaderFeedBack(feedback, 
														esito.getReportPiuSchede().getAllSuccess(), OperazioneScheda.getCancellazione().getNomeOperazione());
											}
		
										}else{
											// feedback per il fallimento della operazione di cancellazione.
											logger.error("Contenuto EsitoOperazione: " + esito.toString());
											List<SchedaSpecificaValidationBean> listOfvalidations =
												esito.isSingola() ? esito.getReportSingolaScheda().getListOfValidationsBeans() : esito.getReportPiuSchede().getAll();
											feedBackWriter.fillMassloaderFeedBack(feedback, listOfvalidations, OperazioneScheda.getCancellazione().getNomeOperazione());
		
											Exception e = esito.getEccezioneLocale();
											if(e != null) e.printStackTrace();
	//										XX-X: gestione livello cig
											if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
											this.listOfCigNonValidi.add(schedaDaCancellareCorrente.getCIG());
											//
//											con.rollback();
											DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
										}
									}else{
										// feedback per il fallimento della validazione della correttezza del flusso
										List<SchedaSpecificaValidationBean> listOfvalidations =
											esito.isSingola() ?  esito.getReportSingolaScheda().getListOfValidationsBeans() : esito.getReportPiuSchede().getAll();
		
										feedBackWriter.fillMassloaderFeedBack(feedback, listOfvalidations, OperazioneScheda.getCancellazione().getNomeOperazione());
	//									XX-X: gestione livello cig
										if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
										this.listOfCigNonValidi.add(schedaDaCancellareCorrente.getCIG());
										//
//										con.rollback();
										DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
									}
		//										SE TIPO DI CANCELLAZIONE DIVERSO DA CIG 
								}else{
									
									
									if(nomeScheda != null ) identificativo = IdentificativoSchede.findIdentificativoByName(nomeScheda);
									LoadSituazioneBusiness loader = new LoadSituazioneBusiness(con, logger, this.origine);
									
							    	if(tipoCancellazione == EliminazioneSchedeAction.ELIMBYCUI){
							    		situazioneAttuale = loader.loadSituazioneByCUI(cui);
							    	}else if(tipoCancellazione == EliminazioneSchedeAction.ELIMBYIDLOCALE){
							    		situazioneAttuale = loader.loadSituazioneAttualeByIdLocale(identificativo, cig, cui, idScheda, idLocale);
							    	}else if(tipoCancellazione == EliminazioneSchedeAction.ELIMBYIDSIMOG){
							    		situazioneAttuale = loader.loadSituazioneAttualeByIdSimog(identificativo, cui, idScheda);
							    	}else{
							    	   // tipo di cancellazione non ammesso riporto sul feedback
	                                   SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(
	                                         Messaggi.SIMOG_MASSLOADER_211.replace("$1", "cancellazione").replace("$2", " cancellare (incongruenza CIG-CUI-ID SCHEDA)"), ValidationBean.VALBEAN_SEV_ERR,
	                                         nomeScheda, cig, cui, idScheda, idLocale);

      	                                 feedBackWriter.fillMassloaderFeedBack(feedback, validation, OperazioneScheda.getCancellazione().getNomeOperazione());
      	                                 if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
      	                                 this.listOfCigNonValidi.add(schedaDaCancellareCorrente.getCIG());  
      	                                 DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
							    	}
							    	
							    	//gm se non ho trovato alcuna scheda aggiudicazione per la ricerca corrente  	
							    	if(tipoCancellazione > 0 && situazioneAttuale.getStatoAggiudicazione().isEsistente()==false &&
							    		situazioneAttuale.getStatoAdesione().isEsistente()==false &&
							    		situazioneAttuale.getStatoEscluso().isEsistente()==false &&
							    		situazioneAttuale.getStatoSottosoglia().isEsistente()==false ){
							    		List<SchedaSpecificaValidationBean> listOfvalidations = new ArrayList<SchedaSpecificaValidationBean>();
							    		SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(
												Messaggi.SIMOG_MASSLOADER_211.replace("$1", "cancellazione").replace("$2", " eliminare"), ValidationBean.VALBEAN_SEV_ERR,
												nomeScheda, cig, cui, idScheda, idLocale);
													

							    		listOfvalidations.add(validation);
										feedBackWriter.fillMassloaderFeedBack(feedback, listOfvalidations, OperazioneScheda.getCancellazione().getNomeOperazione());
//										XX-X: gestione livello cig
										if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
										this.listOfCigNonValidi.add(schedaDaCancellareCorrente.getCIG());	
										DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
							    	}
							    	else{
							    		
							    	
		//XXX: VL  Al momento non e' contemplata l'eventualita'.
									
									// esito = action.rifiutaDUfficioByAggiudicazione(situazioneAttuale, cui, null, schedaDaCancellareCorrente, true);
									
									esito = action.controllaCorrettezzaFlusso(tipoCancellazione, situazioneAttuale, schedaDaCancellareCorrente);
		//											SE CONTROLLO OK ELIMINA TUTTE
									if(esito.isEsitoOperazione()){
										
										esito = action.eliminaScheda(situazioneAttuale, schedaDaCancellareCorrente);
	
										if(esito.isEsitoOperazione()){										
											// feedback per il successo della operazione di cancellazione.
											if(esito.isSingola()){
												feedBackWriter.fillMassloaderFeedBack(feedback, 
														esito.getReportSingolaScheda().getSuccess(), OperazioneScheda.getCancellazione().getNomeOperazione());
											}else{
												feedBackWriter.fillMassloaderFeedBack(feedback, 
														esito.getReportPiuSchede().getAllSuccess(), OperazioneScheda.getCancellazione().getNomeOperazione());
											}
										}else{
	//										feedback per il fallimento della cancellazione della scheda
											List<SchedaSpecificaValidationBean> listOfvalidations =
												esito.isSingola() ?  esito.getReportSingolaScheda().getListOfValidationsBeans() : esito.getReportPiuSchede().getAll();
											feedBackWriter.fillMassloaderFeedBack(feedback, listOfvalidations, OperazioneScheda.getCancellazione().getNomeOperazione());
	//										XX-X: gestione livello cig
											if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
											this.listOfCigNonValidi.add(schedaDaCancellareCorrente.getCIG());	
											//
//											con.rollback();
											DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
										}
									}else{
		//								feedback per il fallimento della validazione della correttezza del flusso	
										List<SchedaSpecificaValidationBean> listOfvalidations =
											esito.isSingola() ?  esito.getReportSingolaScheda().getListOfValidationsBeans() : esito.getReportPiuSchede().getAll();
										feedBackWriter.fillMassloaderFeedBack(feedback, listOfvalidations, OperazioneScheda.getCancellazione().getNomeOperazione());
	//									XX-X: gestione livello cig
										if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
										this.listOfCigNonValidi.add(schedaDaCancellareCorrente.getCIG());
										//
//										con.rollback();
										DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
									}
									}
								}
							}else{
								List<SchedaSpecificaValidationBean> listOfvalidations =
									esito.isSingola() ?  esito.getReportSingolaScheda().getListOfValidationsBeans() : esito.getReportPiuSchede().getAll();
								feedBackWriter.fillMassloaderFeedBack(feedback, listOfvalidations, OperazioneScheda.getCancellazione().getNomeOperazione());
	
	//							XX-X: gestione livello cig
								if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
								this.listOfCigNonValidi.add(schedaDaCancellareCorrente.getCIG());
								//
//								con.rollback();
								DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
							}
						}catch (Exception e) {
//							XX-X: gestione livello cig
							logger.fatal("Eccezione: " + e.getMessage());
							e.printStackTrace();
							logger.info("Aggiungo il CIG["+schedaDaCancellareCorrente.getCIG()+"] alla lista dei CIG non validi");
							if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
							this.listOfCigNonValidi.add(schedaDaCancellareCorrente.getCIG());
							
//							try{
//								con.rollback();
//							}catch(SQLException sqle){
//								sqle.printStackTrace();
//							}
							DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
						}
					}
				}
				// nel caso in cui ci sia un solo cig (quindi ancora non e' stato effettuato il rollback..)
//				con.commit();
				DbManager.staticCommit(con, logger, INVOKER, CONNECTIONID);
				
//				logger.debug("\r\n\t\t\t####\r\nCancellazione FeedBack..:\r\n" +feedback.toString()+"\r\n\t\t\t####\r\n");
			}
			logger.debug("Operazioni di eliminazione effettuate correttamente");

		}
//		catch(SQLException sqle){
//			
//			logger.fatal("Eccezione: " + sqle.getMessage());
//			
//			sqle.printStackTrace();
////			try{
////				con.rollback();
////			}catch (Exception e) {}
//			DbManager.staticRollback(con, logger);
//			
//		}
		
		finally{
			DbManager.staticSetAutoCommitTrue(con, logger, INVOKER, CONNECTIONID);
			logger.debug("Operazioni di cancellazione Termitate");
		}
	}
}
