package it.avlp.simog.massload;

import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.flusso.OperazioneScheda;
import it.avlp.simog.flusso.business.LoadSituazioneBusiness;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.massload.actions.VarAnagSchedeAction;
import it.avlp.simog.massload.esito.EsitoOperazioneCancellazioneBean;
import it.avlp.simog.massload.manager.DbManager;
import it.avlp.simog.massload.util.FeedBackWriterValidationsBeans;
import it.avlp.simog.massload.validation.MassloaderValidator;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack;
import it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType;
import it.avlp.simog.massload.xmlbeans.RecVarAnagType;
import it.avlp.simog.massload.xmlbeans.VarAnagType;
import it.avlp.simog.ws.commons.GaraLottoManager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Livello supplementare di astrazione per lo spostamento delle operazioni
 * ognuno nel suo livello
 * 
 * @author pp
 *
 */
public class VarAnagLayer {

	private static String INVOKER = "VarAnagLayer";
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
	
	public VarAnagLayer(Connection con, Logger logger, FeedBackWriterValidationsBeans feedBackWriter, FeedBack feedback, String user, OrigineSchedaEnum origine){
		this.con = con;
		this.CONNECTIONID = con.toString();
		this.logger = logger;
		this.feedBackWriter = feedBackWriter;
		this.feedback = feedback;
		this.user = user;
		this.origine = origine;
	}

	/**
	 * Eseque tutte le operazioni relative alle variazioni anagrafiche
	 * @param list2 
	 * @param list 
	 * 
	 * @param varAnagTypes
	 */
	public void esequiOperazione(MassloaderValidator validator, VarAnagType[] schede, List<SoggettoPartecipanteBean> list, List<SoggettoResponsabileBean> list2){
		try{
			if(schede != null && schede.length > 0){
				
				DbManager.staticSetAutoCommitFalse(con, logger,INVOKER, CONNECTIONID);
				
				//ArrayList<SituazioneSchedeAttuale> situazioniAttuali = new ArrayList<SituazioneSchedeAttuale>();
				SituazioneSchedeAttuale situazioneAttuale = new SituazioneSchedeAttuale();
				
				for(int i = 0; i< schede.length; i++){
								
					RecVarAnagType schedaCorrente = schede[i].getVariazioneAnagArray(0);
					RecIdSchedaElimType riferimento = schedaCorrente.getRiferimento();
					String cig = riferimento.getCIG();		
					
					// 	soltanto se il cig non risulta nella lista dei cig Validi continua con il processamento della riga corrente
					if(this.listOfCigNonValidi == null || (this.listOfCigNonValidi != null && !this.listOfCigNonValidi.contains(cig))){
						try{
							String cui = riferimento.getCUI();
							String nomeScheda = riferimento.isSetSCHEDA() && riferimento.getSCHEDA() != null ? riferimento.getSCHEDA().toString(): null;								
							String idLocale = riferimento.getIDSCHEDALOCALE();
							String idScheda = riferimento.getIDSCHEDASIMOG();
							IdentificativoSchede identificativo = null;
							
							VarAnagSchedeAction action = new VarAnagSchedeAction(con, logger, user, origine);
							EsitoOperazioneCancellazioneBean esito = action.controllaCorrettezzaSchedeDaVariare(schedaCorrente);

							int tipodiVariazione = -1;
							
							//	SE CONTROLLO CORRETTEZZA FORMALE OK PROCEDI
							if(esito.isEsitoOperazione()){									
								if(nomeScheda != null ) identificativo = IdentificativoSchede.findIdentificativoByName(nomeScheda);

								LoadSituazioneBusiness loader = new LoadSituazioneBusiness(con, logger, this.origine);
									
								tipodiVariazione = action.tipodiVariazione(riferimento);

						    	if(tipodiVariazione == VarAnagSchedeAction.VARIAZBYCUI){
						    		situazioneAttuale = loader.loadSituazioneByCUI(cui);
						    	}else if(tipodiVariazione == VarAnagSchedeAction.VARIAZBYIDLOCALE){
						    		situazioneAttuale = loader.loadSituazioneAttualeByIdLocale(identificativo, cig, cui, idScheda, idLocale);
						    	}else if(tipodiVariazione == VarAnagSchedeAction.VARIAZBYIDSIMOG){
						    		situazioneAttuale = loader.loadSituazioneAttualeByIdSimog(identificativo, cui, idScheda);
						    	}
						    	else{
						    	   // nessuna combinazione ammessa riporto sul feedback e salto la scheda
						    	   
                                   SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(
                                         Messaggi.SIMOG_MASSLOADER_211.replace("$1", "variazione anagrafica").replace("$2", " modificare (mancanza ID_LOCALE o ID_SIMOG)"), ValidationBean.VALBEAN_SEV_ERR,
                                         nomeScheda, cig, cui, idScheda, idLocale);

                                 feedBackWriter.fillMassloaderFeedBack(feedback, validation, OperazioneScheda.getVariazioneAnag().getNomeOperazione());
                                 if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
                                 this.listOfCigNonValidi.add(riferimento.getCIG());  
                                 DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
                                 
                                 continue; // salto la scheda
						    	}

	                             //verifica esistenza sezioni      
						    	 if(schedaCorrente.getResponsabiliArray().length == 0
						    	       && schedaCorrente.getAggiudicatariArray().length == 0
						    	       && schedaCorrente.getDitteAusiliarieArray().length == 0
						    	       && schedaCorrente.getPosizioniArray().length == 0){
						    	    
                                    SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(
                                          Messaggi.SIMOG_MASSLOADER_213, ValidationBean.VALBEAN_SEV_ERR,
                                          nomeScheda, cig, cui, idScheda, idLocale);

                                  feedBackWriter.fillMassloaderFeedBack(feedback, validation, OperazioneScheda.getVariazioneAnag().getNomeOperazione());
                                  
                                  continue;
						    	 }
						    	 
						    	//verifica esistenza scheda da variare  	
						    	if(  (IdentificativoSchede.AGGIUDICAZIONE.equals(nomeScheda) && situazioneAttuale.getStatoAggiudicazione().isEsistente()==false)
						    	  || (IdentificativoSchede.ADESIONE.equals(nomeScheda) && situazioneAttuale.getStatoAdesione().isEsistente()==false)
						    	  || (IdentificativoSchede.ESCLUSO.equals(nomeScheda) && situazioneAttuale.getStatoEscluso().isEsistente()==false)
                                  || (IdentificativoSchede.SOTTOSOGLIA.equals(nomeScheda) && situazioneAttuale.getStatoSottosoglia().isEsistente()==false)
                                  || (IdentificativoSchede.FASE_INIZIALE.equals(nomeScheda) && situazioneAttuale.getStatoInizioLavori().isEsistente()==false)
                                  || (IdentificativoSchede.COLLAUDO.equals(nomeScheda) && situazioneAttuale.getStatoCollaudo().isEsistente()==false)
						    	){
						    		List<SchedaSpecificaValidationBean> listOfvalidations = new ArrayList<SchedaSpecificaValidationBean>();
						    		SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(
											Messaggi.SIMOG_MASSLOADER_211.replace("$1", "variazione anagrafica").replace("$2", " modificare"), ValidationBean.VALBEAN_SEV_ERR,
											nomeScheda, cig, cui, idScheda, idLocale);

						    		listOfvalidations.add(validation);
									feedBackWriter.fillMassloaderFeedBack(feedback, listOfvalidations, OperazioneScheda.getVariazioneAnag().getNomeOperazione());
//										XX-X: gestione livello cig
									if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
									this.listOfCigNonValidi.add(riferimento.getCIG());	
									DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
									
									continue; // salto la scheda
						    	}

	                      //verifica blocco AVCPASS
	                     if(SimogFlags.is3028_RFWEBGL07Active()){
                            //GaraManager gman = new GaraManager(con, logger);
                            LottoManager lman = new LottoManager(con, logger);
                            GaraLottoManager glm = new GaraLottoManager(con, logger);
                            List<Lotto> listaLotti = lman.getLottoByCigWS(cig);
                            //Long idGara = listaLotti.get(0).getId_Gara();
                            //Gara gara = gman.getGara(idGara);
	                        if(glm.isAVCPass(null, listaLotti, AVCPassFunzioneEnum.ML_VARIAZIONE_ANAGRAFICA.getCodice())){
	                           List<SchedaSpecificaValidationBean> listOfvalidations = new ArrayList<SchedaSpecificaValidationBean>();
	                           SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(Messaggi.SIMOG_AVCPASS_001, ValidationBean.VALBEAN_SEV_ERR,
	                                 nomeScheda, cig, cui, idScheda, idLocale);

	                           listOfvalidations.add(validation);
	                           feedBackWriter.fillMassloaderFeedBack(feedback, listOfvalidations, OperazioneScheda.getVariazioneAnag().getNomeOperazione());
//	                            XX-X: gestione livello cig
	                           if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
	                           this.listOfCigNonValidi.add(riferimento.getCIG()); 
	                           DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
	                           
	                           continue; // salto la scheda
	                        }
	                     }
	                     
						    	//verifica stato scheda da variare , deve essere confermata
                       if(  (IdentificativoSchede.AGGIUDICAZIONE.equals(nomeScheda) && situazioneAttuale.getStatoAggiudicazione().isConfermata()==false)
                         || (IdentificativoSchede.ADESIONE.equals(nomeScheda) && situazioneAttuale.getStatoAdesione().isConfermata()==false)
                         || (IdentificativoSchede.ESCLUSO.equals(nomeScheda) && situazioneAttuale.getStatoEscluso().isConfermata()==false)
                         || (IdentificativoSchede.SOTTOSOGLIA.equals(nomeScheda) && situazioneAttuale.getStatoSottosoglia().isConfermata()==false)
                         || (IdentificativoSchede.FASE_INIZIALE.equals(nomeScheda) && situazioneAttuale.getStatoInizioLavori().isConfermata()==false)
                         || (IdentificativoSchede.COLLAUDO.equals(nomeScheda) && situazioneAttuale.getStatoCollaudo().isConfermata()==false)
                       ){
                           List<SchedaSpecificaValidationBean> listOfvalidations = new ArrayList<SchedaSpecificaValidationBean>();
                           SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(
                                   Messaggi.SIMOG_MASSLOADER_214.replace("$1", nomeScheda), ValidationBean.VALBEAN_SEV_ERR,
                                   nomeScheda, cig, cui, idScheda, idLocale);

                           listOfvalidations.add(validation);
                           feedBackWriter.fillMassloaderFeedBack(feedback, listOfvalidations, OperazioneScheda.getVariazioneAnag().getNomeOperazione());
//                                      XX-X: gestione livello cig
                           if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
                           this.listOfCigNonValidi.add(riferimento.getCIG());  
                           DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
                       }						    	
						     else{
						    		/*
						    		 * controlli OK, richiamo la variazione anagrafica
						    		 */
									esito = action.variazioneScheda(validator, situazioneAttuale, schedaCorrente, list, list2);
	
									if(esito.isEsitoOperazione()){										
									// feedback per il successo della operazione di VARIAZIONE.
										feedBackWriter.fillMassloaderFeedBack(feedback, 
												esito.getReportSingolaScheda().getSuccess(), OperazioneScheda.getVariazioneAnag().getNomeOperazione());
										logger.debug("Operazione di variazione anagrafica effettuata correttamente: " + cig + " - " + nomeScheda );

							            // finalizzo le variazioni sul db
										DbManager.staticCommit(con, logger, INVOKER, CONNECTIONID);
									}else{
										// feedback per il fallimento della VARIAZIONE della scheda
										List<SchedaSpecificaValidationBean> listOfvalidations = esito.getReportSingolaScheda().getListOfValidationsBeans() ;
										feedBackWriter.fillMassloaderFeedBack(feedback, listOfvalidations, OperazioneScheda.getVariazioneAnag().getNomeOperazione());
										// XX-X: gestione livello cig
										if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
										this.listOfCigNonValidi.add(riferimento.getCIG());	

										DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
									}
								}
							}else{
								List<SchedaSpecificaValidationBean> listOfvalidations =
									esito.isSingola() ?  esito.getReportSingolaScheda().getListOfValidationsBeans() : esito.getReportPiuSchede().getAll();
								feedBackWriter.fillMassloaderFeedBack(feedback, listOfvalidations, OperazioneScheda.getVariazioneAnag().getNomeOperazione());
	
	//							XX-X: gestione livello cig
								if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
								this.listOfCigNonValidi.add(schedaCorrente.getRiferimento().getCIG());
								//
//								con.rollback();
								DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
							}
						}catch (Exception e) {
//							XX-X: gestione livello cig
							logger.fatal("Eccezione: " + e.getMessage());
							e.printStackTrace();
							logger.info("Aggiungo il CIG["+schedaCorrente.getRiferimento().getCIG()+"] alla lista dei CIG non validi");
							if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
							this.listOfCigNonValidi.add(schedaCorrente.getRiferimento().getCIG());
							
//							try{
//								con.rollback();
//							}catch(SQLException sqle){
//								sqle.printStackTrace();
//							}
							DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
						}
					}
				}
			}
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
			logger.debug("Operazioni di  variazione anagrafica terminate");
		}
	}
}
