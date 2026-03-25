package it.avlp.simog.massload.util;

import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avlp.simog.beans.EsitoEnum;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.CigException;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.massload.actions.GenericSchedeAction;
import it.avlp.simog.massload.bean.IdsSchedaXML;
import it.avlp.simog.massload.esito.EsitiOperazioneControllo;
import it.avlp.simog.massload.esito.EsitoOperazioneControlloBean;
import it.avlp.simog.massload.esito.EsitoOperazioneControlloEsistenzaAnagrafiche;
import it.avlp.simog.massload.esito.EsitoOperazioneControlloIds;
import it.avlp.simog.massload.esito.EsitoOperazioneValidateAnaPartecipanti;
import it.avlp.simog.massload.esito.EsitoOperazioneValidateAnaResposabili;
import it.avlp.simog.massload.util.conversion.ConvertXMLtoBeanBusiness;
import it.avlp.simog.massload.validation.MassloaderValidator;
import it.avlp.simog.massload.xmlbeans.AggiudicatariType;
import it.avlp.simog.massload.xmlbeans.DatiAggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.EsitoProceduraType;
import it.avlp.simog.massload.xmlbeans.EsitoProceduraType.Enum;
import it.avlp.simog.massload.xmlbeans.ResponsabiliType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.ws.commons.GaraLottoManager;

import java.util.List;

public class MassloaderCleaner {

	public static final String ESITOCIG = "0";
	public static final String ESITOCFRUP = "1";
	public static final String ESITOMATCH = "2";
	public static final String ESITOCUI = "3";
	public static final String ESITOANAGRAFICAAGGIUDICATARI = "4";
	public static final String ESITOANAGRAFICARESPONSABILI = "5";
	
	private MassloaderValidator validator;
	
	/**
	 * I due parametri in ingresso servono alla creazione di una istanza di massloader validator
	 * che viene usato per effettuare alcuni controlli sul db, quali ad esempio il controllo di 
	 * esistenza cig e cui.
	 * 
	 * @param con: sql connection usato in sola lettura
	 * @param logger: log4j logger
	 */
//	public MassloaderCleaner(Connection con, Logger logger){
//		this.validator = new MassloaderValidator(con, logger);
//	}
	/**
	 * Classe che si occupa della pulizia dei dati xml, ovverosia tramite l'oggetto validatore
	 * <strong>controlla che i dati siano validi</strong>.
	 * 
	 * @param validator
	 */
	public MassloaderCleaner(MassloaderValidator validator){
		this.validator = validator;
	}
	
	/**
	 * Metodo che si occupa della validazione delle anagrafiche.
	 * Si e' deciso che il controllo della validita' delle anagrafiche deve essere fatto all'inizio (AS controllo formale)
	 * per cui essendo degli elementi cross cig, se una di queste non risulta valida viene scartato tutto il 
	 * il file xml.
	 * 
	 * @param at
	 * @param converter
	 * @return
	 */
	public EsitoOperazioneValidateAnaPartecipanti validaAnagrafichePartecipanti(AggiudicatariType at, ConvertXMLtoBeanBusiness converter){
		return validator.validaAnagrafichePosizioni(at, converter);
	}
	/**
	 * Metodo che si occupa della validazione delle anagrafiche.
	 * Si e' deciso che il controllo della validita' delle anagrafiche deve essere fatto all'inizio (AS controllo formale)
	 * per cui essendo degli elementi cross cig, se una di queste non risulta valida viene scartato tutto il 
	 * il file xml.
	 * 
	 * @param rt
	 * @param converter
	 * @return
	 */
	public EsitoOperazioneValidateAnaResposabili validaAnagraficheResponsabili(ResponsabiliType rt, ConvertXMLtoBeanBusiness converter){
		return validator.validaAnagraficheIncaricati(rt, converter);
	}
	/**
	 * Effettua tutti i controlli formali (su tutto l'xml):
	 * - rimuove gli IdsSchedaXML(livello CUI) non validi ritornando una lista di bean di validazione (simog validator extended style)
	 * - ritorna la lista degli IdsSchedaXML validi
	 * 
	 * - controllo esistenza cig </p>
	 * - controllo validita' cf_rup </p>
	 * - controllo validita' cig -> cui </p>
	 * - controllo esistenza cui </p>
	 * - controllo presenza anagrafica aggiudicatari </p>
	 * - controllo presenza anagrafica responsabili </p>
	 * - controllo della validita' degli id simog </p>
	 * - TODO: NON e' stato trovato un modo per controllare gli id_locale delle schede multiple, mentre e' possibile per le schede singole
	 * - TODO: VL Introdurre controllo correttezza sezioni riportate per gli incaricati, 
	 * 		al momento viene sovrascritto tale valore (vedi iniziolavorivalidator)
	 * 
	 * @param listOfIdScheda list of IdsSchedaXML che contiene tutte le informazioni relative ad un CUI!
	 * @param at wrap an array of aggiudicatari
	 * @param rt wrap an array of responsabili
	 * @param action 
	 * @param origine 
	 * @return
	 */
	public EsitiOperazioneControllo rimuoviPartiFormalmenteNonValide(List<IdsSchedaXML> listOfIdScheda, AggiudicatariType at, ResponsabiliType rt, GenericSchedeAction action) throws Exception {
		
		EsitiOperazioneControllo esiti = new EsitiOperazioneControllo();
		
		// faccio una prima pulizia.. controlli formali
		int i = 0;
		for(IdsSchedaXML idScheda : listOfIdScheda){

			EsitoOperazioneControlloBean esitoControllo = new EsitoOperazioneControlloBean();
			esitoControllo.setSchedaCorrente(idScheda);
			
			DatiAggiudicazioneType datiAggiudicazioneCorrente = idScheda.getScheda();
			boolean esitoCig = validator.controllaEsistenzaCig(datiAggiudicazioneCorrente.getDatiComuni());

			// se non esiste il cig errore livello cig
			if(!esitoCig){
				esitoControllo.setValidCig(esitoCig);
				esitoControllo.setCigValidation(new SchedaSpecificaValidationBean(Messaggi.SIMOG_MASSLOADER_182, ValidationBean.VALBEAN_SEV_ERR,
						0, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.DATI_COMUNI, idScheda.getCig(), ""));
				esitoControllo.setEsitoOperazione(false);
			}else{
			   
				  LottoManager lm = new LottoManager(action.getCon(), action.getLogger());
	               List<Lotto> listaDiLotti = lm.getLottoByCigWS(idScheda.getCig());
	               
	               Lotto lotto = listaDiLotti != null && listaDiLotti.size() > 0 
	                           ? listaDiLotti.get(0) : null; 
				
	         //verifica blocco AVCPASS
	         if(SimogFlags.is3028_RFWEBGL07Active()){
	            // controllo solo se non è avcpass che sta mandando i dati
	            if (action.getOrigine().code() != OrigineSchedaEnum.AVCPASS.code()){
	               
	               
	               GaraLottoManager glm = new GaraLottoManager(action.getCon(), action.getLogger());
	               if(glm.isAVCPass(null, listaDiLotti, AVCPassFunzioneEnum.ML_SCHEDA_AGGIUNTIVE_UPDATE.getCodice())){

	                  esitoControllo.setValidCig(false);
	                  esitoControllo.setCigValidation(new SchedaSpecificaValidationBean(Messaggi.SIMOG_AVCPASS_001, ValidationBean.VALBEAN_SEV_ERR,
	                        0, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.DATI_COMUNI, idScheda.getCig(), ""));
	                  esitoControllo.setEsitoOperazione(false);
	               }
	            }
	         } 

		         //TICKET ALM #10468 - 3.04.4  
		         Enum esitoType = datiAggiudicazioneCorrente.getDatiComuni().getESITOPROCEDURA();
		         if(lotto.getDATA_SCADENZA_PAGAMENTI()==null && (esitoType==null 
                                                                          || EsitoEnum.AGGIUDICATA.codice().equals(String.valueOf(esitoType.intValue()))
		        		                                                  || EsitoEnum.PROPOSTA_AGGIUDICAZIONE.codice().equals(String.valueOf(esitoType.intValue()))
		        		                                        ))  {   
		        	 esitoControllo.setValidCig(false);
						esitoControllo.setCigValidation(new SchedaSpecificaValidationBean(Messaggi.SIMOG_VALIDAZIONE_265.replace("$1", "Esito della procedura - Stato attuale"), ValidationBean.VALBEAN_SEV_ERR,
								0, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.DATI_COMUNI, idScheda.getCig(), ""));
						esitoControllo.setEsitoOperazione(false);
		         } else {
		         
				
				boolean esitoCfRup = validator.controllaValiditaCfRup(datiAggiudicazioneCorrente.getDatiComuni().getCFRUP(), rt);
				
				// altro errore al livello di cig
				if(!esitoCfRup){
					esitoControllo.setValidCfRup(esitoCfRup);
					esitoControllo.setCfRupValidation(
							new SchedaSpecificaValidationBean(
									Messaggi.SIMOG_MASSLOADER_172.replace("$1", "RUP (dati comuni)").replace("$2",
									datiAggiudicazioneCorrente.getDatiComuni().getCFRUP()), 
									ValidationBean.VALBEAN_SEV_ERR,
									0, idScheda.getCardinalitaSchedaCompleta(),0, 
									IdentificativoSchede.DATI_COMUNI, idScheda.getCig(), idScheda.getCui()));
					esitoControllo.setEsitoOperazione(false);
				// errori a livello CUI
				}else{
					
					if(idScheda.getScheda().getSchedaCompletaArray().length > 1){ throw new Exception("Dimensione inaspettata"); }
					
					boolean esitoMatch = true;
					boolean esitoCui = true;
					boolean esitoAnagraficheA = true;
					boolean esitoAnagraficheI = true;
					boolean esitoIdSimog = true;
					boolean esitoIdLocale = true;
					boolean esitoCoerenzaId = true;
					
					EsitoOperazioneControlloEsistenzaAnagrafiche anaAgg = null;
					EsitoOperazioneControlloEsistenzaAnagrafiche anaRes = null;
					
					EsitoOperazioneControlloIds controlloIdSimog = null;
					EsitoOperazioneControlloIds controlloIdLocale = null;
					
					EsitoOperazioneControlloIds controlloCoerenzaId = null;
					
					if(idScheda.isPresentSomeSchedaCompleta()){
						SchedaCompletaType schedaCompletaCorrente = idScheda.getScheda().getSchedaCompletaArray(0);
						esitoMatch = validator.controllaMatchCigToCui(datiAggiudicazioneCorrente.getDatiComuni().getCIG(), schedaCompletaCorrente);
						
						if(esitoMatch){
							// gestione cui = "" ovvero scheda da inserire..
							esitoCui = schedaCompletaCorrente.getCUI().equals("") ? true : validator.controllaEsistenzaCui(schedaCompletaCorrente);
						}
						anaAgg = validator.controllaEsistenzaAnagrafichePerPosizioni(idScheda, at);
						esitoAnagraficheA = anaAgg.isEsitoOperazione();
						
						anaRes = validator.controllaEsistenzaAnagrafichePerResponsabili(idScheda, rt, at);
						esitoAnagraficheI = anaRes.isEsitoOperazione();
						
						// i controlli che necessitano della situazione attuale vengono effettuati solamente se il cui e' valido (caso caricamento tramite cui)
						if(esitoMatch && esitoCui){
							controlloIdSimog = validator.controllaMatchIdXmlWithIdDbSimog(idScheda.getSituazioneAttuale(), idScheda.getSituazioneAttualeXml(),
									idScheda.getCig(),idScheda.getCui(),idScheda.getCardinalitaSchedaCompleta());
							esitoIdSimog = controlloIdSimog.isEsitoOperazione();
							
							controlloCoerenzaId = validator.controllaCoerenzaIds(idScheda.getSituazioneAttualeXml(), idScheda.getCig(), idScheda.getCui());
							esitoCoerenzaId = controlloCoerenzaId.isEsitoOperazione();
							// il controllo degli id locali funziona solamente se ha passato il controllo di coerenza (per piu di una scheda vedi condizione per entrare qui) 
							if(esitoCoerenzaId){
								controlloIdLocale = validator.controllaMatchIdXmlWithIdDbLocale(idScheda.getSituazioneAttuale(), idScheda.getSituazioneAttualeXml(),
										idScheda.getCig(),idScheda.getCui(),idScheda.getCardinalitaSchedaCompleta());
								esitoIdLocale = controlloIdLocale.isEsitoOperazione();
							}
						}
					// per il controllo dei soli id sui dati comuni
					}else{
						// nel caso della sola scheda dati comuni non occorre il controllo di coerenza
						controlloIdSimog = validator.controllaMatchIdXmlWithIdDbSimog(idScheda.getSituazioneAttuale(), idScheda.getSituazioneAttualeXml(),
								idScheda.getCig(),idScheda.getCui() != null ? idScheda.getCui() : "",idScheda.getCardinalitaSchedaCompleta());
						esitoIdSimog = controlloIdSimog.isEsitoOperazione();
						
						controlloIdLocale = validator.controllaMatchIdXmlWithIdDbLocale(idScheda.getSituazioneAttuale(), idScheda.getSituazioneAttualeXml(),
								idScheda.getCig(),idScheda.getCui() != null ? idScheda.getCui() : "",idScheda.getCardinalitaSchedaCompleta());
						esitoIdLocale = controlloIdLocale.isEsitoOperazione();
					}
					boolean esito = esitoMatch && esitoCfRup && esitoAnagraficheA && esitoAnagraficheI && esitoCui && esitoIdSimog && esitoIdLocale && esitoCoerenzaId;
					
					if(!esito){
						esitoControllo.setEsitoOperazione(false);
						// se non c'e' corrispondenza cig cui
						if(!esitoMatch){
							esitoControllo.setMatchCigCui(esitoMatch);
							String avviso = Messaggi.SIMOG_MASSLOADER_177.replace("$1", idScheda.getCig()).replace("$2",idScheda.getCui());
							esitoControllo.setCigCuiValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
									0, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.DATI_COMUNI, idScheda.getCig(), idScheda.getCui()));
						}
						// se il un cui non e' risultato valido
						if(!esitoCui){
							esitoControllo.setExistCui(false);
							esitoControllo.setCuiValidation(new SchedaSpecificaValidationBean(Messaggi.SIMOG_MASSLOADER_186, ValidationBean.VALBEAN_SEV_ERR,
									0, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.DATI_COMUNI, idScheda.getCig(), idScheda.getCui()));
						
						// i controlli che necessitano della situazione attuale vengono effettuati solamente se il cui e' valido (caso caricamento tramite cui)
						}else{
							// se qualche id simog e' valorizzato e non risuta corretto
							if(!esitoIdSimog){
								esitoControllo.setAreValidIds(false);
								esitoControllo.addIdsValidation(controlloIdSimog.getIdsValidation());
							}
							// se qualche id simog e' valorizzato e non risuta corretto
							if(!esitoIdLocale){
								esitoControllo.setAreValidIds(false);
								esitoControllo.addIdsValidation(controlloIdLocale.getIdsValidation());
							}
							// se solo qualche id e' valorizzato (ERRORE)
							if(!esitoCoerenzaId){
								esitoControllo.setAreValidIds(false);
								esitoControllo.addIdsValidation(controlloCoerenzaId.getIdsValidation());
							}
						}
						// se non esiste un'anagrafica per un cf
						if(!esitoAnagraficheA){
							esitoControllo.setAreValidPosizioni(false);
							esitoControllo.setPosizioniValidation(anaAgg.getListOfValidation());
						}
						// se non esiste un'anagrafica per un cf
						if(!esitoAnagraficheI){
							esitoControllo.setAreValidIncaricati(false);
							esitoControllo.setIncaricatiValidation(anaRes.getListOfValidation());
						}
						
					}else{
						esitoControllo.setEsitoOperazione(true);
					}					
				}
		       }
			}
			esiti.addListOfEsiti(esitoControllo);
			i++;
		}
		return esiti;
	}
}
