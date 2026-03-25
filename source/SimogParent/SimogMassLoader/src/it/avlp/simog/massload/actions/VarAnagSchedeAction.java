package it.avlp.simog.massload.actions;

import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.beans.EsitoControlloStatiSchede;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.IdsScheda;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.collaudo.SchedaCollaudo;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.inizio.PosizioneAggiudicatarioBean;
import it.avlp.simog.beans.inizio.SchedaInizioLavori;
import it.avlp.simog.common.action.CollaudoSharedAction;
import it.avlp.simog.common.action.InizioLavoriSharedAction;
import it.avlp.simog.common.action.PosizioneAggiudicataroSharedAction;
import it.avlp.simog.common.action.ResponsabileInizioSharedAction;
import it.avlp.simog.common.action.Scheda_A_SharedAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.generated.DITTE_AUSILIARIE;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.massload.cancellazione.eccezione.EliminazioneFallitaException;
import it.avlp.simog.massload.cancellazione.manager.EliminazioneSchedeManager;
import it.avlp.simog.massload.cancellazione.report.ReportCancellazioneScheda;
import it.avlp.simog.massload.caricamento.CaricamentoBusiness;
import it.avlp.simog.massload.caricamento.SchedeConstructor;
import it.avlp.simog.massload.esito.EsitoOperazioneCancellazioneBean;
import it.avlp.simog.massload.esito.EsitoValidazioneBean;
import it.avlp.simog.massload.util.conversion.ConvertXMLtoBeanBusiness;
import it.avlp.simog.massload.validation.MassloaderValidator;
import it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType;
import it.avlp.simog.massload.xmlbeans.RecVarAnagType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class VarAnagSchedeAction extends GenericSchedeAction{

	private Connection con;
	private Logger logger;
	private String cfUtente;
	
	/** tipi di variazione autorizzati **/
	public final static int VARIAZBYCUI = 1;
	public final static int VARIAZBYIDSIMOG = 2;
	public final static int VARIAZBYIDLOCALE = 3;
	
//	private int tipoCancellazioneAttuale;

	public VarAnagSchedeAction(Connection con, Logger logger, String cfUtente, OrigineSchedaEnum origine){
	   
	   super(con, logger, origine);
	   
		this.con = con;
		this.logger = logger;
		this.cfUtente = cfUtente;
	}
	
	/**
	 * Controlla la correttezza "Formale" della scheda da variare in ingresso il che comprende
	 * - Controllo valorizzazione CIG / CUI 
	 * - Controllo cig substring cui
	 * - controllo che la scheda in oggetto sia parte del cui indicato
	 * 
	 * @param schedaDaEliminare
	 * @return
	 */
	public EsitoOperazioneCancellazioneBean controllaCorrettezzaSchedeDaVariare(RecVarAnagType schedaDaVariare ) throws NotFound,SQLException{//,Exception{
		
		RecIdSchedaElimType riferimento = schedaDaVariare.getRiferimento();
		EsitoOperazioneCancellazioneBean esito = new EsitoOperazioneCancellazioneBean();
		String cui = riferimento.getCUI();
		String cig = riferimento.getCIG();
		
		boolean isVariabile = true;
		String riepilogoScheda = this.costruisciRiferimentiSchedaString(riferimento);
		boolean isValidCui = cui != null && !"".equals(cui.trim());
		boolean isInValidCig = cig == null || "".equals(cig.trim());
		boolean isInValidNomeScheda = (riferimento.isSetIDSCHEDALOCALE() 
										|| riferimento.isSetIDSCHEDASIMOG()) 
									&& !riferimento.isSetSCHEDA();
		SchedaSpecificaValidationBean validation = null;
// controllo formale
		// cig valido ?
		if(isInValidCig){
			isVariabile = false;
			esito.setMessaggioErrore(Messaggi.SIMOG_MASSLOADER_195.replace("$1", "CIG"));

			validation = new SchedaSpecificaValidationBean(
						Messaggi.SIMOG_MASSLOADER_195.replace("$1", "CIG"), 
							ValidationBean.VALBEAN_SEV_ERR,
							riferimento.getSCHEDA().toString(), 
							riferimento.getCIG(), 
							riferimento.getCUI(),
							riferimento.getIDSCHEDASIMOG(),
							riferimento.getIDSCHEDALOCALE());

		}else{
			// cui valido ?
			if(isValidCui){
				String cigFromCui = CIGBean.getRealCIG(cui.substring(0, 10));
				isVariabile = cigFromCui.equals(cig);
				if(!isVariabile){
					esito.setMessaggioErrore(Messaggi.SIMOG_MASSLOADER_177.replace("$1", cig).replace("$2",cui));

					validation = new SchedaSpecificaValidationBean(Messaggi.SIMOG_MASSLOADER_177.replace("$1", cig).replace("$2",cui), ValidationBean.VALBEAN_SEV_ERR,
							riferimento.getSCHEDA().toString(), riferimento.getCIG(), riferimento.getCUI(),
							riferimento.getIDSCHEDASIMOG(),riferimento.getIDSCHEDALOCALE());
				}
				// controllo preventivo per essere sicuri che sia valorizzato il nome scheda
				if( isVariabile && isInValidNomeScheda){
					esito.setMessaggioErrore(Messaggi.SIMOG_MASSLOADER_190);

					validation = new SchedaSpecificaValidationBean(Messaggi.SIMOG_MASSLOADER_190, ValidationBean.VALBEAN_SEV_ERR,
						riferimento.getSCHEDA() == null ? "": riferimento.getSCHEDA().toString(),
						riferimento.getCIG(), riferimento.getCUI(),
						riferimento.getIDSCHEDASIMOG(),riferimento.getIDSCHEDALOCALE());

					isVariabile = false;
				}
				// controllo che se e' una scheda tra quelle ammesse
				if( isVariabile && !isInValidNomeScheda){
					if(riferimento.getSCHEDA() != null 
						&& !IdentificativoSchede.ADESIONE.equals(riferimento.getSCHEDA().toString())
						&& !IdentificativoSchede.AGGIUDICAZIONE.equals(riferimento.getSCHEDA().toString())
						&& !IdentificativoSchede.COLLAUDO.equals(riferimento.getSCHEDA().toString())
						&& !IdentificativoSchede.ESCLUSO.equals(riferimento.getSCHEDA().toString())
						&& !IdentificativoSchede.FASE_INIZIALE.equals(riferimento.getSCHEDA().toString())
						&& !IdentificativoSchede.SOTTOSOGLIA.equals(riferimento.getSCHEDA().toString())
					   ){
						
						esito.setMessaggioErrore(Messaggi.SIMOG_MASSLOADER_198.replace("$1", riferimento.getSCHEDA().toString()));

						validation = new SchedaSpecificaValidationBean(Messaggi.SIMOG_MASSLOADER_198.replace("$1", riferimento.getSCHEDA().toString()), 
								ValidationBean.VALBEAN_SEV_ERR,
								riferimento.getSCHEDA().toString(), riferimento.getCIG(), riferimento.getCUI(),
								riferimento.getIDSCHEDASIMOG(),riferimento.getIDSCHEDALOCALE());

						isVariabile = false;				
					}

				}
				
				// se ancora variabile controlla afferenza cui -> scheda (riutilizzato EliminazioneSchedeManager)
				// posso controllare solo se esiste idlocale o idsimog
				// altrimenti la scheda interesata dalla variazione sarà quella reperita tramite CUI
				if(isVariabile && riferimento.isSetSCHEDA() && (riferimento.isSetIDSCHEDALOCALE() || riferimento.isSetIDSCHEDASIMOG())){
					String idLocale = riferimento.getIDSCHEDALOCALE();
					String idScheda = riferimento.getIDSCHEDASIMOG();
					IdentificativoSchede identificativo = IdentificativoSchede.findIdentificativoByName(riferimento.getSCHEDA().toString());
					boolean byIdSimog = riferimento.isSetIDSCHEDASIMOG();
					EliminazioneSchedeManager eliminazioneManager = new EliminazioneSchedeManager(con, logger, cfUtente);
					isVariabile = eliminazioneManager.controllaAfferenzaCUIConScheda(cui, idScheda, idLocale, identificativo, byIdSimog);
					if(!isVariabile){
						
						esito.setMessaggioErrore(Messaggi.SIMOG_MASSLOADER_191.replace("$1", riferimento.getSCHEDA().toString()));
						
						validation = new SchedaSpecificaValidationBean(Messaggi.SIMOG_MASSLOADER_191.replace("$1", riferimento.getSCHEDA().toString()), ValidationBean.VALBEAN_SEV_ERR,
								riferimento.getSCHEDA().toString(), riferimento.getCIG(), riferimento.getCUI(),
								riferimento.getIDSCHEDASIMOG(),riferimento.getIDSCHEDALOCALE());

					}
				}

			}		
		}
		this.fillEsitoOperazione(isVariabile, validation, esito, riferimento, riepilogoScheda);
		return esito;
	}

	/**
	 * Controlla i parametri per la variazione, e chiama la variazione anagrafica.
	 * @param validator2 
	 * 
	 * 
	 * @param schedaCorrente
	 * @param listResp 
	 * @param listPart 
	 * @return
	 */
	public EsitoOperazioneCancellazioneBean variazioneScheda(MassloaderValidator validator, 
	      SituazioneSchedeAttuale situazioneAttuale, RecVarAnagType schedaCorrente, 
	      List<SoggettoPartecipanteBean> listPart, List<SoggettoResponsabileBean> listResp){
		
		EsitoOperazioneCancellazioneBean esito = new EsitoOperazioneCancellazioneBean();

		try{
			ReportCancellazioneScheda reportSingolaScheda = null;	
			
			
			RecIdSchedaElimType riferimento = schedaCorrente.getRiferimento();
			String cui = riferimento.getCUI();
			String cig = riferimento.getCIG();
			String nomeScheda = riferimento.isSetSCHEDA() ? riferimento.getSCHEDA().toString(): null;
			IdentificativoSchede identificativo = IdentificativoSchede.findIdentificativoByName(nomeScheda);
			String idLocale = riferimento.getIDSCHEDALOCALE();
			String idScheda = riferimento.getIDSCHEDASIMOG();

            boolean esitoCheck = true;
            EsitoValidazioneBean beanEsito = new EsitoValidazioneBean(nomeScheda, cig, cui, 0);

			reportSingolaScheda = new ReportCancellazioneScheda();
            
            IdsScheda identificativoScheda = new IdsScheda();
            identificativoScheda.setCig(cig);   
            identificativoScheda.setCui(cui);
            identificativoScheda.setIdentificativo(identificativo);
            identificativoScheda.setIdScheda(idScheda);
            identificativoScheda.setIdLocale(idLocale);
            reportSingolaScheda.setSchede(identificativoScheda);

			CaricamentoBusiness loader = new CaricamentoBusiness(con, logger);
			
			if(IdentificativoSchede.COLLAUDO.equals(nomeScheda)){
				
				CollaudoSharedAction cAction = new CollaudoSharedAction(con, logger);
				CollaudoBean collaudo = loader.caricaCollaudo(situazioneAttuale.getStatoCollaudo().getIdAggiudicazione(), situazioneAttuale.getStatoCollaudo().getDataInizioAggiudicazione());
                List<AccordoBean> accordi = loader.caricaAccordi(situazioneAttuale.getStatoCollaudo().getIdAggiudicazione(), situazioneAttuale.getStatoCollaudo().getDataInizioAggiudicazione());
				ConclusioneBean conclusione = loader.caricaConclusione(situazioneAttuale.getStatoConclusione().getIdAggiudicazione(), situazioneAttuale.getStatoConclusione().getDataInizioAggiudicazione());
                InizioLavoriBean inizio = loader.caricaInizioLavori(situazioneAttuale.getStatoCollaudo().getIdAggiudicazione(), situazioneAttuale.getStatoCollaudo().getDataInizioAggiudicazione());
                AggiudicazioneBean aggiud = loader.caricaAggiudicazione(cui);
				InfoComuniBean dcBean = loader.caricaDatiComuni(cig);
				
				ConvertXMLtoBeanBusiness converter = new ConvertXMLtoBeanBusiness();
				List<ResponsabileBean> respXML = converter.convertiIncaricatiCollaudo(schedaCorrente.getResponsabiliArray(), true);
                // validazione motivo variazione
                collaudo.setValidaVariazione(true);
                collaudo.setIdMotivoVarCO(schedaCorrente.getMOTIVO());

                SchedaCollaudo scheda = new SchedaCollaudo();
                
				// verifica delle anagrafiche dei responsabili
				if(respXML != null){
      				for (Iterator<ResponsabileBean> iterator = respXML.iterator(); iterator.hasNext();) {
                        ResponsabileBean responsabileBean = (ResponsabileBean) iterator.next();
                        
                        // aggiungo alla lista dei responsabili l'anagrafica, presa dall'xml o dal db
                        // il metodo scrive anche sul db se occorre aggiornare(inserire l'anagrafica
                        super.modificaAnagraficaResponsabili(responsabileBean, listResp,
                              responsabileBean.getSoggettoResponsabile().getCodiceFiscaleResponsabile(), false);
                        
                        if (responsabileBean.getSoggettoResponsabile().getIdResponsabile() == 0){
                           // anagrafica non trovata, abortisco
                           esitoCheck = false;
                           
                           List<ValidationBean> listOfValidations = new ArrayList<ValidationBean>(); 
                           listOfValidations.add(
                                 new ValidationBean(
                                       Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE).replace("$2",responsabileBean.getSoggettoResponsabile().getCodiceFiscaleResponsabile())
                                       , ValidationBean.VALBEAN_SEV_ERR, 0));
                           beanEsito.setListOfValidationsByConversion(listOfValidations);
                           
                           break;
                        }
      				}
                }
				
				// integrazione dei dati per la validazione
                scheda.setCollaudo(collaudo);
                
                if(respXML != null && !respXML.isEmpty())
                   scheda.setIncaricati(respXML);
                
                scheda.setInfoComuni(dcBean);
                scheda.setAccordiBonario(accordi);
                scheda.setAggiudicazione(aggiud);
                scheda.setConclusione(conclusione); // necessario al validatore massloader
                scheda.setInizioLavori(inizio);

				boolean successo = false;
                
                // finora tutto ok
				if (esitoCheck){
				
      				EsitoValidazioneBean esitoVal = validator.validaCollaudo(scheda, cig, cui, 0);
				
      				// validazione fallita restituisco gli errori nel feedback
                   if(!esitoVal.isEsitoOperazione()){ 
                      esitoCheck = false;
                      reportSingolaScheda.setEsitoOperazione(false);
                      reportSingolaScheda.setListOfValidationsBeans(esitoVal.getListOfValidations());
                      esito.setReportSingolaScheda(reportSingolaScheda);
                      esito.setSingola(true);
                      esito.setEsitoOperazione(false);
                    }
                   else{
                      // effettuo l'aggiornamento
                      String motivazione = StatiScheda.VARIAZIONE_CO_STRING;
                      String idLotto = Long.toString(dcBean.getIdLotto());
                  
                      RichiestaAnnullamento raBean = new RichiestaAnnullamento();
                      raBean.setId_lotto(idLotto);
                      raBean.setMotivo_richiesta(motivazione);
                      raBean.setRichiedente(cfUtente);
                      
                      raBean.setId_record(Long.toString(collaudo.getIdCollaudo()));
                      raBean.setData_inizio_record(collaudo.getDataIniColl());
                      raBean.setBlocco(identificativo.getBlocco());
                      Timestamp nuovadata = null;
                  
         				nuovadata = cAction.gestisciVariazioniCO(collaudo, raBean,  cfUtente,dcBean.getFlagEnteSpeciale());
         				successo = nuovadata != null;
         				
                        reportSingolaScheda.setEsitoOperazione(successo);

                        esito.setReportSingolaScheda(reportSingolaScheda);
                        esito.setSingola(true);
                        esito.setEsitoOperazione(successo);
                   }
                }
			}
			else if(IdentificativoSchede.FASE_INIZIALE.equals(nomeScheda)){
                
                InizioLavoriSharedAction ilAction = new InizioLavoriSharedAction(con, logger);
                InizioLavoriBean inizio = loader.caricaInizioLavori(situazioneAttuale.getStatoInizioLavori().getIdAggiudicazione(), situazioneAttuale.getStatoInizioLavori().getDataInizioAggiudicazione());
                AggiudicazioneBean aggiud = loader.caricaAggiudicazione(cui);
                List<AggiudicatarioBean> aggiudicatari = loader.caricaAggiudicatari(aggiud.getIdAggiudicazione(), aggiud.getDataInizioAggiudicazione());
                InfoComuniBean dcBean = loader.caricaDatiComuni(cig);
                
                ResponsabileInizioSharedAction risAction = new ResponsabileInizioSharedAction(con, logger);
                PosizioneAggiudicataroSharedAction pasAction = new PosizioneAggiudicataroSharedAction(con, logger);
                
                ConvertXMLtoBeanBusiness converter = new ConvertXMLtoBeanBusiness();
                List<ResponsabileBean> respXML = converter.convertiIncaricatiInizioLavori(schedaCorrente.getResponsabiliArray(), true);
                List<PosizioneAggiudicatarioBean> posizXML = converter.convertiPosizioniInizioLavori(schedaCorrente.getPosizioniArray(), true);

               
                // validazione motivo variazione
                inizio.setValidaVariazione(true);
                inizio.setIdMotivoVarCO(schedaCorrente.getMOTIVO());

                SchedaInizioLavori scheda = new SchedaInizioLavori();
                
                // verifica delle anagrafiche dei responsabili
                if(respXML != null){              
                   for (Iterator<ResponsabileBean> iterator = respXML.iterator(); iterator.hasNext();) {
                     ResponsabileBean responsabileBean = (ResponsabileBean) iterator.next();
                     
                     // aggiungo alla lista dei responsabili l'anagrafica, presa dall'xml o dal db
                     // il metodo scrive anche sul db se occorre aggiornare(inserire l'anagrafica
                     super.modificaAnagraficaResponsabili(responsabileBean, listResp,
                           responsabileBean.getSoggettoResponsabile().getCodiceFiscaleResponsabile(), false);
                     
                     if (responsabileBean.getSoggettoResponsabile().getIdResponsabile() == 0){
                        // anagrafica non trovata, abortisco
                        esitoCheck = false;
                        
                        List<ValidationBean> listOfValidations = new ArrayList<ValidationBean>(); 
                        listOfValidations.add(
                              new ValidationBean(
                                    Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE).replace("$2",responsabileBean.getSoggettoResponsabile().getCodiceFiscaleResponsabile())
                                    , ValidationBean.VALBEAN_SEV_ERR, 0));
                        beanEsito.setListOfValidationsByConversion(listOfValidations);

                        break;
                     }
                   }
                }

                // verifica delle anagrafiche per le posizioni, serve ad adeguare posizXML con i dati anagrafici del soggetto
                if(posizXML != null){  
                   
                   for (Iterator<PosizioneAggiudicatarioBean> iterator = posizXML.iterator(); iterator.hasNext();) {
                      PosizioneAggiudicatarioBean posBean = (PosizioneAggiudicatarioBean) iterator.next();
                      
                      // aggiungo alla lista dei responsabili l'anagrafica, presa dall'xml o dal db
                      // il metodo scrive anche sul db se occorre aggiornare(inserire l'anagrafica
                      
                      super.modificaAnagraficaAggiudicatari(posBean, null, null, null,  listPart, 
                            posBean.getSoggettoPartecipante().getCodiceFiscale(), 
                            posBean.getSoggettoPartecipante().getId_stato(), false, false, false);
                      
                      if (posBean.getSoggettoPartecipante().getIdSoggettoPartecipante() == 0){
                         // anagrafica non trovata, abortisco
                         esitoCheck = false;
                         
                         List<ValidationBean> listOfValidations = new ArrayList<ValidationBean>(); 
                         listOfValidations.add(
                               new ValidationBean(
                                     Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_POSIZIONE).replace("$2",posBean.getSoggettoPartecipante().getCodiceFiscale())
                                     , ValidationBean.VALBEAN_SEV_ERR, 0));
                         beanEsito.setListOfValidationsByConversion(listOfValidations);

                         break;
                      }
                    }
                }

                // integrazione dei dati per la validazione
                scheda.setAggiudicazione(aggiud);
                scheda.setAggiudicatari(aggiudicatari);
                scheda.setInfoComuni(dcBean);
                scheda.setDatiInizio(inizio);
                scheda.setResponsabiliInizio(risAction.loadMany(inizio.getIdInizioLavori(), inizio.getDataInizioLavori(), false));
                scheda.setPosizioneAggiudicatari(pasAction.loadMany(inizio.getIdInizioLavori(), inizio.getDataInizioLavori(), false));
                
                if(respXML != null && !respXML.isEmpty())
                   scheda.setResponsabiliInizio(respXML);
                
                if(posizXML != null && !posizXML.isEmpty())
                   scheda.setPosizioneAggiudicatari(posizXML);

                boolean successo = false;
                
                // finora tutto ok
                if (esitoCheck){
                
                    EsitoValidazioneBean esitoVal = validator.validaInizioLavori(scheda, cig, cui, 0);
                
                    // validazione fallita restituisco gli errori nel feedback
                   if(!esitoVal.isEsitoOperazione()){ 
                      esitoCheck = false;
                      reportSingolaScheda.setEsitoOperazione(false);
                      reportSingolaScheda.setListOfValidationsBeans(esitoVal.getListOfValidations());
                      esito.setReportSingolaScheda(reportSingolaScheda);
                      esito.setSingola(true);
                      esito.setEsitoOperazione(false);
                    }
                   else{
                      // effettuo l'aggiornamento
                      String motivazione = StatiScheda.VARIAZIONE_CO_STRING;
                      String idLotto = Long.toString(dcBean.getIdLotto());
                  
                      RichiestaAnnullamento raBean = new RichiestaAnnullamento();
                      raBean.setId_lotto(idLotto);
                      raBean.setMotivo_richiesta(motivazione);
                      raBean.setRichiedente(cfUtente);
                      
                      raBean.setId_record(Long.toString(inizio.getIdInizioLavori()));
                      raBean.setData_inizio_record(inizio.getDataInizioLavori());
                      raBean.setId_pub(Long.toString(inizio.getPubblicazione().getIdPubblicazione()));
                      raBean.setData_inizio_pub(inizio.getPubblicazione().getDataInizioPubblicazione());
                      raBean.setBlocco(identificativo.getBlocco());
                      Timestamp nuovadata = null;
                  
                        nuovadata = ilAction.gestisciVariazioniCO(scheda, raBean,  cfUtente);
                        successo = nuovadata != null;
                        
                        reportSingolaScheda.setEsitoOperazione(successo);

                        esito.setReportSingolaScheda(reportSingolaScheda);
                        esito.setSingola(true);
                        esito.setEsitoOperazione(successo);
                   }
                }
            }   
            else if(IdentificativoSchede.AGGIUDICAZIONE.equals(nomeScheda)
                     || IdentificativoSchede.SOTTOSOGLIA.equals(nomeScheda)
                     || IdentificativoSchede.ESCLUSO.equals(nomeScheda)
                     || IdentificativoSchede.ADESIONE.equals(nomeScheda)
                 ){
               
               Scheda_A_SharedAction saAction = new Scheda_A_SharedAction(con, logger);
               
               InfoComuniBean dcBean = loader.caricaDatiComuni(cig);
               
               Scheda_A saBean = null;
              
               
               if(IdentificativoSchede.AGGIUDICAZIONE.equals(nomeScheda))
                  saBean = saAction.load(situazioneAttuale.getStatoAggiudicazione().getIdAggiudicazione(), situazioneAttuale.getStatoAggiudicazione().getDataInizioAggiudicazione(), dcBean.getFlagEnteSpeciale(), false, dcBean.getIdLotto());
               else if(IdentificativoSchede.SOTTOSOGLIA.equals(nomeScheda))
                  saBean = saAction.load(situazioneAttuale.getStatoSottosoglia().getIdAggiudicazione(), situazioneAttuale.getStatoSottosoglia().getDataInizioAggiudicazione(), dcBean.getFlagEnteSpeciale(), false, dcBean.getIdLotto());
               else if(IdentificativoSchede.ESCLUSO.equals(nomeScheda))
                  saBean = saAction.load(situazioneAttuale.getStatoEscluso().getIdAggiudicazione(), situazioneAttuale.getStatoEscluso().getDataInizioAggiudicazione(), dcBean.getFlagEnteSpeciale(), false, dcBean.getIdLotto());
               else if(IdentificativoSchede.ADESIONE.equals(nomeScheda))
                  saBean = saAction.load(situazioneAttuale.getStatoAdesione().getIdAggiudicazione(), situazioneAttuale.getStatoAdesione().getDataInizioAggiudicazione(), dcBean.getFlagEnteSpeciale(), false, dcBean.getIdLotto());
                              
               saBean.setInfoComuni(dcBean);
               saBean.setInfoGara(getInfoGara(cig));
               
               // PP patch per nuova gestione CUP imposto a NO il flag altrimenti non passa la validazione
               if (saBean.getFlagCUP() == null)
                  saBean.setFlagCUP(Costanti.FLAG_VALORE_NO);
               
               ConvertXMLtoBeanBusiness converter = new ConvertXMLtoBeanBusiness();
               SchedeConstructor constructor = new SchedeConstructor(converter);
               
               if(IdentificativoSchede.AGGIUDICAZIONE.equals(nomeScheda)){
                  Map<String, List<ResponsabileBean>> respXML = converter.convertiIncaricatiAggiudicazione(schedaCorrente.getResponsabiliArray(), true, PSBD.SEZIONE_RA);
                  
                  if(respXML.get(PSBD.SEZIONE_RA) != null && !respXML.get(PSBD.SEZIONE_RA).isEmpty())
                     saBean.setResponsabili(respXML.get(PSBD.SEZIONE_RA));
                  
                  if(respXML.get(PSBD.SEZIONE_PA) != null && !respXML.get(PSBD.SEZIONE_PA).isEmpty())
                     saBean.setPrestazioni(respXML.get(PSBD.SEZIONE_PA));
               }
               else if(IdentificativoSchede.SOTTOSOGLIA.equals(nomeScheda)){
                  Map<String, List<ResponsabileBean>> respXML = converter.convertiIncaricatiAggiudicazione(schedaCorrente.getResponsabiliArray(), true, PSBD.SEZIONE_RS);
                  
                  if(respXML.get(PSBD.SEZIONE_RS) != null && !respXML.get(PSBD.SEZIONE_RS).isEmpty())
                     saBean.setResponsabili(respXML.get(PSBD.SEZIONE_RS));
               }
               else if(IdentificativoSchede.ESCLUSO.equals(nomeScheda)){
                  Map<String, List<ResponsabileBean>> respXML = converter.convertiIncaricatiAggiudicazione(schedaCorrente.getResponsabiliArray(), true, PSBD.SEZIONE_RE);
                  
                  if(respXML.get(PSBD.SEZIONE_RE) != null && !respXML.get(PSBD.SEZIONE_RE).isEmpty())
                     saBean.setResponsabili(respXML.get(PSBD.SEZIONE_RE));
               }
               else if(IdentificativoSchede.ADESIONE.equals(nomeScheda)){
                  Map<String, List<ResponsabileBean>> respXML = converter.convertiIncaricatiAggiudicazione(schedaCorrente.getResponsabiliArray(), true, PSBD.SEZIONE_RQ);

                  if(respXML.get(PSBD.SEZIONE_RQ) != null && !respXML.get(PSBD.SEZIONE_RQ).isEmpty())
                     saBean.setResponsabili(respXML.get(PSBD.SEZIONE_RQ));
               }
               
               // validazione motivo variazione
               saBean.getAggiudicazione().setValidaVariazione(true);
               saBean.getAggiudicazione().setIdMotivoVarCO(schedaCorrente.getMOTIVO());

               // verifica delle anagrafiche dei responsabili
               if(saBean.getResponsabili() != null){
                  for (Iterator<ResponsabileBean> iterator = saBean.getResponsabili().iterator(); iterator.hasNext();) {
                    ResponsabileBean responsabileBean = (ResponsabileBean) iterator.next();
                    
                    // aggiungo alla lista dei responsabili l'anagrafica, presa dall'xml o dal db
                    // il metodo scrive anche sul db se occorre aggiornare(inserire l'anagrafica
                    super.modificaAnagraficaResponsabili(responsabileBean, listResp,
                          responsabileBean.getSoggettoResponsabile().getCodiceFiscaleResponsabile(), false);
                    
                    if (responsabileBean.getSoggettoResponsabile().getIdResponsabile() == 0){
                       // anagrafica non trovata, abortisco
                       esitoCheck = false;
                       
                       List<ValidationBean> listOfValidations = new ArrayList<ValidationBean>(); 
                       listOfValidations.add(
                             new ValidationBean(
                                   Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE).replace("$2",responsabileBean.getSoggettoPartecipante().getCodiceFiscale())
                                   , ValidationBean.VALBEAN_SEV_ERR, 0));
                       beanEsito.setListOfValidationsByConversion(listOfValidations);

                       break;
                    }
                  }
               }
               
               // verifica delle anagrafiche dei progettisti
               if(saBean.getPrestazioni() != null && esitoCheck == true){
                  for (Iterator<ResponsabileBean> iterator = saBean.getPrestazioni().iterator(); iterator.hasNext();) {
                    ResponsabileBean responsabileBean = (ResponsabileBean) iterator.next();
                    
                    // aggiungo alla lista dei responsabili l'anagrafica, presa dall'xml o dal db
                    // il metodo scrive anche sul db se occorre aggiornare(inserire l'anagrafica
                    if(responsabileBean.getSoggettoResponsabile()!=null)
                       super.modificaAnagraficaResponsabili(responsabileBean, listResp,
                             responsabileBean.getSoggettoResponsabile().getCodiceFiscaleResponsabile(), false);
                    
                    if(responsabileBean.getSoggettoPartecipante()!=null)
                       super.modificaAnagraficaAggiudicatari(null, null, null, responsabileBean,  listPart, 
                             responsabileBean.getSoggettoPartecipante().getCodiceFiscale(), 
                             responsabileBean.getSoggettoPartecipante().getId_stato(), false, false, true);

                    if (responsabileBean.getSoggettoResponsabile().getIdResponsabile() == 0
                          && responsabileBean.getSoggettoPartecipante().getIdSoggettoPartecipante() == 0){
                       // anagrafica non trovata, abortisco
                       esitoCheck = false;
                       
                       List<ValidationBean> listOfValidations = new ArrayList<ValidationBean>(); 
                       listOfValidations.add(
                             new ValidationBean(
                                   Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_PROGETTISTA)
                                      .replace("$2",
                                            responsabileBean.getSoggettoResponsabile()!=null
                                            ? responsabileBean.getSoggettoResponsabile().getCodiceFiscaleResponsabile()
                                            : responsabileBean.getSoggettoPartecipante().getCodiceFiscale()
                                      )
                                   , ValidationBean.VALBEAN_SEV_ERR, 0));
                       beanEsito.setListOfValidationsByConversion(listOfValidations);

                       break;
                    }
                  }
               }

               List<AggiudicatarioBean> aggiudXML = converter.convertiAggiudicatari(schedaCorrente.getAggiudicatariArray(), true);
               List<DittaAusiliariaBean> ditteXML = converter.convertiDitteAusiliarie(schedaCorrente.getDitteAusiliarieArray(), true);
               
               // sostituisco gli aggiudicatari, solo se inviati
               if(aggiudXML != null && !aggiudXML.isEmpty())
                  saBean.setAggiudicatari(aggiudXML);

               constructor.associaAusiliarie(ditteXML, saBean.getAggiudicatari());

               // verifica delle anagrafiche degli aggiudicatari
               if(saBean.getAggiudicatari() != null && esitoCheck == true){
                  for (Iterator<AggiudicatarioBean> iterator = saBean.getAggiudicatari().iterator(); iterator.hasNext();) {
                    AggiudicatarioBean aggiudBean = (AggiudicatarioBean) iterator.next();
                    
                    // aggiungo alla lista dei responsabili l'anagrafica, presa dall'xml o dal db
                    // il metodo scrive anche sul db se occorre aggiornare(inserire l'anagrafica
                    
                    super.modificaAnagraficaAggiudicatari(null, aggiudBean, null, null,  listPart, 
                          aggiudBean.getSoggettoPartecipante().getCodiceFiscale(), 
                          aggiudBean.getSoggettoPartecipante().getId_stato(), true, false, false);
                    
                    if (aggiudBean.getSoggettoPartecipante().getIdSoggettoPartecipante() == 0){
                       // anagrafica non trovata, abortisco
                       esitoCheck = false;
                       
                       List<ValidationBean> listOfValidations = new ArrayList<ValidationBean>(); 
                       listOfValidations.add(
                             new ValidationBean(
                                   Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_PARTECIPANTE).replace("$2",aggiudBean.getSoggettoPartecipante().getCodiceFiscale())
                                   , ValidationBean.VALBEAN_SEV_ERR, 0));
                       beanEsito.setListOfValidationsByConversion(listOfValidations);
                       
                       break;
                    }
                  }
               }

               // verifica delle anagrafiche delle ditte ausiliarie
               if(saBean.getAggiudicatari() != null && esitoCheck == true){
                  for (Iterator<AggiudicatarioBean> iterator = saBean.getAggiudicatari().iterator(); iterator.hasNext();) {
                    AggiudicatarioBean aggiudBean = (AggiudicatarioBean) iterator.next();

                    if ( aggiudBean.getDitteAusiliarie() != null && !aggiudBean.getDitteAusiliarie().isEmpty()){
                       for (Iterator<DittaAusiliariaBean> iteratorD = aggiudBean.getDitteAusiliarie().iterator(); iteratorD.hasNext();) {
                          DittaAusiliariaBean dittasBean = (DittaAusiliariaBean) iteratorD.next();
   
                          super.modificaAnagraficaAggiudicatari(null,null, dittasBean, null, listPart, 
                                dittasBean.getSoggettoPartecipante().getCodiceFiscale(), 
                                dittasBean.getSoggettoPartecipante().getId_stato(), false, true, false);
                          
                          if (dittasBean.getSoggettoPartecipante().getIdSoggettoPartecipante() == 0){
                             // anagrafica non trovata, abortisco
                             esitoCheck = false;
                             
                             List<ValidationBean> listOfValidations = new ArrayList<ValidationBean>(); 
                             listOfValidations.add(
                                   new ValidationBean(
                                         Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_AUSILIARIA).replace("$2",dittasBean.getSoggettoPartecipante().getCodiceFiscale())
                                         , ValidationBean.VALBEAN_SEV_ERR, 0));
                             beanEsito.setListOfValidationsByConversion(listOfValidations);

                             break;
                          }
                       }
                    }
                  }
               }
              
               boolean successo = false;
               
               // finora tutto ok
               if (esitoCheck){
                  EsitoValidazioneBean esitoVal = null;
                  
                  if(IdentificativoSchede.AGGIUDICAZIONE.equals(nomeScheda))
                     esitoVal = validator.validaAggiudicazione(saBean, cig, cui, 0);
                  else if(IdentificativoSchede.SOTTOSOGLIA.equals(nomeScheda))
                     esitoVal = validator.validaSottosoglia(saBean, cig, cui, 0);
                  else if(IdentificativoSchede.ESCLUSO.equals(nomeScheda))
                     esitoVal = validator.validaEscluso(saBean, cig, cui, 0);
                  else if(IdentificativoSchede.ADESIONE.equals(nomeScheda))
                     esitoVal = validator.validaAdesione(saBean, cig, cui, 0);
                   
               
                   // validazione fallita restituisco gli errori nel feedback
                  if(!esitoVal.isEsitoOperazione()){ 
                     esitoCheck = false;
                     reportSingolaScheda.setEsitoOperazione(false);
                     reportSingolaScheda.setListOfValidationsBeans(esitoVal.getListOfValidations());
                     esito.setReportSingolaScheda(reportSingolaScheda);
                     esito.setSingola(true);
                     esito.setEsitoOperazione(false);
                   }
                  else{
                     // effettuo l'aggiornamento
                     String motivazione = StatiScheda.VARIAZIONE_CO_STRING;
                     String idLotto = Long.toString(dcBean.getIdLotto());
                 
                     RichiestaAnnullamento raBean = new RichiestaAnnullamento();
                     raBean.setId_lotto(idLotto);
                     raBean.setMotivo_richiesta(motivazione);
                     raBean.setRichiedente(cfUtente);
                     
                     raBean.setId_record(Long.toString(saBean.getAggiudicazione().getIdAggiudicazione()));
                     raBean.setData_inizio_record(saBean.getAggiudicazione().getDataInizioAggiudicazione());
                     raBean.setBlocco(identificativo.getBlocco());
                     Timestamp nuovadata = null;
                 
                       nuovadata = saAction.gestisciVariazioniCO(saBean, raBean,  cfUtente, dcBean.getFlagEnteSpeciale());
                       successo = nuovadata != null;
                       
                       reportSingolaScheda.setEsitoOperazione(successo);

                       esito.setReportSingolaScheda(reportSingolaScheda);
                       esito.setSingola(true);
                       esito.setEsitoOperazione(successo);
                  }
               }
           }
           else{
              // qualcosa non va!
              throw new NotFound();
           }
			
			// se c'è stato un errore da qualche parte riporto la situazione
			if(!esitoCheck){
               reportSingolaScheda.setEsitoOperazione(false);
               
               //PP
               if(beanEsito.getListOfValidations()!= null)
                  reportSingolaScheda.setListOfValidationsBeans(beanEsito.getListOfValidations());
               
               esito.setReportSingolaScheda(reportSingolaScheda);
               esito.setSingola(true);
               esito.setEsitoOperazione(false);
			}
			   
		}catch(EliminazioneFallitaException efe){
			String messaggioErrore = "Si e' incorsi in eccezione EliminazioneFallitaException durante l'operazione di variazione di una scheda ";
			logger.error(messaggioErrore + ": " + efe.getMessage());
			fillEsitoOperazioneErrore(esito, efe);
		}catch(NotFound notFound){
			// se capita qui e' un errore di implementazione
			String messaggioErrore = "Si e' incorsi in eccezione NotFound durante l'operazione di variazione di una scheda ";
			logger.error(messaggioErrore + ": " + notFound.getMessage());
			fillEsitoOperazioneErrore(esito, notFound, false, messaggioErrore);
		}catch(SQLException sqle){
			String messaggioErrore = "Si e' incorsi in eccezione SQL durante l'operazione di variazione di una scheda ";
			logger.fatal(messaggioErrore + ": " + sqle.getMessage());
			fillEsitoOperazioneErrore(esito, sqle, false, messaggioErrore);
		}catch(Exception e){
			String messaggioErrore = "Si e' incorsi in eccezione imprevista durante l'operazione di variazione di una scheda ";
			logger.fatal(messaggioErrore + ": " + e.getMessage());
			fillEsitoOperazioneErrore(esito, e, false, messaggioErrore);
		}
		
		return esito;
	}

	/**
	 * Costruisce un toString dei dati del tipo XML RecIdSchedaElimType
	 * 
	 * @param CIG
	 * @param CUI
	 * @param SCHEDA
	 * @param idSimog
	 * @param idLocale
	 * @return
	 */
	private String costruisciRiferimentiSchedaString(RecIdSchedaElimType schedaDaEliminare){
		
		String CIG = schedaDaEliminare.getCIG();
		String CUI = !"".equals(schedaDaEliminare.getCUI()) ? schedaDaEliminare.getCUI() : "Elemento BLANK"; 
		String SCHEDA = schedaDaEliminare.isSetSCHEDA() ? schedaDaEliminare.getSCHEDA().toString() : "Elemento NON presente";
		String idSimog = schedaDaEliminare.isSetIDSCHEDASIMOG() ? schedaDaEliminare.getIDSCHEDASIMOG(): "Elemento NON presente";
		String idLocale = schedaDaEliminare.isSetIDSCHEDALOCALE() ? schedaDaEliminare.getIDSCHEDALOCALE(): "Elemento NON presente";
		
		StringBuffer output = new StringBuffer();
		output.append("Riepilogo Scheda:\r\n");
		output.append("\tNome scheda: "+SCHEDA+ "\r\n");
		output.append("\tCIG: "+CIG+"\r\n");
		output.append("\tCUI: "+CUI+"\r\n");
		output.append("\tID_SIMOG: "+idSimog+"\r\n");
		output.append("\tID_LOCALE: "+idLocale+"\r\n");
		return output.toString();
	}
	
	/**
	 * Aggiunge alla stringa in ingresso l'esito dell'operazione
	 * 
	 * @param esitoOperazione
	 * @param riferimentiScheda
	 * @return
	 */
	private String aggiungiEsitoOperazioneString(boolean esitoOperazione, String riferimentiScheda){
		return riferimentiScheda + ( esitoOperazione ? "\tOperazione eseguita con Successo \r\n" : "\tOperazione Fallita \r\n");
		
	}
	/**
	 * Accoda al primo parametro in ingresso il secono solamente se il secondo paramentro e' non nullo 
	 * e non vuoto.
	 * 
	 * @param riferimentiScheda
	 * @param messaggioErrore
	 * @return
	 */
	private String aggiungiEsitoMessaggioOperazioneFallita(String riferimentiScheda,String messaggioErrore){
		if(messaggioErrore != null && !"".equals(messaggioErrore.trim())){
			return riferimentiScheda + "\tMessaggio: " +messaggioErrore + "\r\n";
			
		}return riferimentiScheda;
		
	}
	
	/**
	 * Controlla che nel report sia tutto ok
	 * 
	 * @param reportSingolaScheda
	 * @return
	 */
	private boolean controllaReport(ReportCancellazioneScheda reportSingolaScheda){		
		return reportSingolaScheda.getEsitoOperazione();
	}
					
	/**
	 * Valorizza l'esito operazione:
	 * - in caso il booleano sia false valorizza anche l'oggetto report nell'esito operazione, accoda alla stringa di riepilogo
	 * 		l'esito dell'operazione.
	 * 
	 * @param schedaDaEliminare
	 * @return
	 */
	private void fillEsitoOperazione(boolean isOperazioneConSuccesso, EsitoControlloStatiSchede esitoStati, EsitoOperazioneCancellazioneBean esito, RecIdSchedaElimType schedaDaEliminare, String riepilogoScheda ) throws NotFound{
		if(!isOperazioneConSuccesso){
			ReportCancellazioneScheda reportCorrente = new ReportCancellazioneScheda();	
			IdsScheda rifsScheda = new IdsScheda();
			
			reportCorrente.setSchede(rifsScheda);
			// ADDED
			reportCorrente.setListOfValidationsBeans(esitoStati.getListOfValidationBeans());
			
			rifsScheda.setCig(schedaDaEliminare.getCIG());
			rifsScheda.setCui(schedaDaEliminare.getCUI());
			rifsScheda.setIdLocale(schedaDaEliminare.getIDSCHEDALOCALE());
			rifsScheda.setIdScheda(schedaDaEliminare.getIDSCHEDASIMOG());
			
			if(schedaDaEliminare.getSCHEDA() != null){
				rifsScheda.setIdentificativo(IdentificativoSchede.findIdentificativoByName(schedaDaEliminare.getSCHEDA().toString()));
			}
			esito.setReportSingolaScheda(reportCorrente);
			esito.setSingola(true);
		}		
		esito.setEsitoOperazione(isOperazioneConSuccesso);
		
		// NON viene usata
		
			// aggiungo l'esito dell'operazione alla stringa descrittiva
			riepilogoScheda = this.aggiungiEsitoOperazioneString(isOperazioneConSuccesso, riepilogoScheda);
			// aggiungo il messaggio (operazione effettuata solamente se messaggio di errore non null e non vuoto)
			riepilogoScheda = this.aggiungiEsitoMessaggioOperazioneFallita(riepilogoScheda, esito.getMessaggioErrore());
			// rimuovo la formattazione
			riepilogoScheda = riepilogoScheda.replaceAll("\r?\n"," ").replace("\t", "");
			esito.setMessaggioInfo(riepilogoScheda);
			
		// end
		
	}	
	/**
	 * @param isOperazioneConSuccesso
	 * @param esitoStati
	 * @param esito
	 * @param schedaDaEliminare
	 * @param riepilogoScheda
	 * @throws NotFound
	 */
	private void fillEsitoOperazione(boolean isOperazioneConSuccesso, SchedaSpecificaValidationBean validation, EsitoOperazioneCancellazioneBean esito, RecIdSchedaElimType schedaDaEliminare, String riepilogoScheda ) throws NotFound{
		if(!isOperazioneConSuccesso){
			ReportCancellazioneScheda reportCorrente = new ReportCancellazioneScheda();	
			IdsScheda rifsScheda = new IdsScheda();
			
			reportCorrente.setSchede(rifsScheda);
			// ADDED
			reportCorrente.addListOfValidationsBeans(validation);
			
			rifsScheda.setCig(schedaDaEliminare.getCIG());
			rifsScheda.setCui(schedaDaEliminare.getCUI());
			rifsScheda.setIdLocale(schedaDaEliminare.getIDSCHEDALOCALE());
			rifsScheda.setIdScheda(schedaDaEliminare.getIDSCHEDASIMOG());
			
			if(schedaDaEliminare.getSCHEDA() != null){
				rifsScheda.setIdentificativo(IdentificativoSchede.findIdentificativoByName(schedaDaEliminare.getSCHEDA().toString()));
			}
			esito.setReportSingolaScheda(reportCorrente);
			esito.setSingola(true);
		}		
		esito.setEsitoOperazione(isOperazioneConSuccesso);
		// aggiungo l'esito dell'operazione alla stringa descrittiva
		riepilogoScheda = this.aggiungiEsitoOperazioneString(isOperazioneConSuccesso, riepilogoScheda);
		// aggiungo il messaggio (operazione effettuata solamente se messaggio di errore non null e non vuoto)
		riepilogoScheda = this.aggiungiEsitoMessaggioOperazioneFallita(riepilogoScheda, esito.getMessaggioErrore());
		// rimuovo la formattazione
		riepilogoScheda = riepilogoScheda.replaceAll("\r?\n"," ").replace("\t", "");
		esito.setMessaggioInfo(riepilogoScheda);
		
	}	

	/**
	 * Valorizza il bean per l'esito della operazione
	 * 
	 * @param esitoBean
	 * @param e
	 * @param esito
	 * @param messaggioErrore
	 */
	private void fillEsitoOperazioneErrore(EsitoOperazioneCancellazioneBean esitoBean, Exception e, boolean esito, String messaggioErrore){
		esitoBean.setEccezioneLocale(e);
		esitoBean.setEsitoOperazione(esito);
		esitoBean.setMessaggioErrore(messaggioErrore);	
	}
	
	/**
	 * Valorizza l'esito operazione con i dati contenuti nell'eccezione..
	 * 
	 * @param esitoBean
	 * @param efe
	 */
	private void fillEsitoOperazioneErrore(EsitoOperazioneCancellazioneBean esitoBean, EliminazioneFallitaException efe){
		esitoBean.setMessaggioErrore(efe.getMessage());	
		ReportCancellazioneScheda reportSingle = esitoBean.getReportSingolaScheda();
		if(reportSingle == null){ 
			reportSingle = new ReportCancellazioneScheda();
		}
		reportSingle.setSchede(efe.getRiferimenti());
		esitoBean.setSingola(true);
		esitoBean.setReportSingolaScheda(reportSingle);
	}
	/**
	 * Valorizza alcuni dati del bean di esito
	 * 
	 * @param esitoBean
	 * @param reportPiuSchede
	 * @param reportSingolaScheda
	 * @param isSingola
	 * @param beanInfo
	 */
	private void fillEsitoOperazioneInfo(EsitoOperazioneCancellazioneBean esitoBean, 
										ReportCancellazioneScheda reportSingolaScheda, boolean isSingola, String beanInfo){
		esitoBean.setSingola(isSingola);
		boolean esitoOperazione = false;
		esitoBean.setReportSingolaScheda(reportSingolaScheda);
		esitoOperazione = this.controllaReport(reportSingolaScheda);
			
		beanInfo = this.aggiungiEsitoOperazioneString(esitoOperazione, beanInfo);
		esitoBean.setMessaggioInfo(beanInfo);
		esitoBean.setEsitoOperazione(esitoOperazione);
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
	
	/**
	 * Si occupa di identificare il tipo di variazione in base ai parametri nel tipo "RecIdSchedaElimType"
	 * 
	 * @param schedaDaEliminare
	 * @return
	 * @throws NotFound
	 */
	public int tipodiVariazione(RecIdSchedaElimType schedaDaEliminare) {
		
		// cig e cui obbligatori per decidere
		if(!"".equals(schedaDaEliminare.getCUI()) && !"".equals(schedaDaEliminare.getCIG())){
			if(schedaDaEliminare.isSetSCHEDA()){
				if(!schedaDaEliminare.isSetIDSCHEDALOCALE()&& !schedaDaEliminare.isSetIDSCHEDASIMOG()){
					//  schede singole
					return VARIAZBYCUI;
				}

				// se settato solo simog
				if(schedaDaEliminare.isSetIDSCHEDASIMOG()){
					return VARIAZBYIDSIMOG;
				}
				
				// se settato solo locale
				if(schedaDaEliminare.isSetIDSCHEDALOCALE()){
					return VARIAZBYIDLOCALE;
				}
			}
		}return 0;	// nessuna combinazione ammessa	
	}

}
