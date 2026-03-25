package it.avlp.simog.massload.actions;

import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.beans.EsitoControlloStatiSchede;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.IdsScheda;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.beans.StatoScheda;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.flusso.OperazioneScheda;
import it.avlp.simog.flusso.WorkFlowController;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.massload.cancellazione.eccezione.EliminazioneFallitaException;
import it.avlp.simog.massload.cancellazione.manager.EliminazioneSchedeManager;
import it.avlp.simog.massload.cancellazione.report.ReportCancellazioneScheda;
import it.avlp.simog.massload.cancellazione.report.ReportCancellazioneSchede;
import it.avlp.simog.massload.esito.EsitoOperazioneCancellazioneBean;
import it.avlp.simog.massload.xmlbeans.RecIdSchedaElimType;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.TipoFlusso;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class EliminazioneSchedeAction {

	private Connection con;
	private Logger logger;
	private String cfUtente;
	
	/** tipi di cancellazione autorizzati **/
	public final static int ELIMBYCIG = 11;
	public final static int ELIMBYCUI = 211;
	public final static int ELIMBYIDSIMOG = 221;
	public final static int ELIMBYIDLOCALE = 222;
	
//	private int tipoCancellazioneAttuale;

	public EliminazioneSchedeAction(Connection con, Logger logger, String cfUtente){
		this.con = con;
		this.logger = logger;
		this.cfUtente = cfUtente;
	}
	
	/**
	 * Controlla la correttezza "Formale" della scheda da cancellare in ingresso il che comprende
	 * - Controllo valorizzazione CIG / CUI 
	 * - Controllo cig substring cui
	 * - controllo che la scheda in oggetto sia parte del cui indicato
	 * 
	 * @param schedaDaEliminare
	 * @return
	 */
	public EsitoOperazioneCancellazioneBean controllaCorrettezzaSchedeDaEliminare(RecIdSchedaElimType schedaDaEliminare ) throws NotFound,SQLException{//,Exception{
		
		EsitoOperazioneCancellazioneBean esito = new EsitoOperazioneCancellazioneBean();
		String cui = schedaDaEliminare.getCUI();
		String cig = schedaDaEliminare.getCIG();
		
		boolean isCancellabile = true;
		String riepilogoScheda = this.costruisciRiferimentiSchedaString(schedaDaEliminare);
		boolean isValidCui = cui != null && !"".equals(cui.trim());
		boolean isInValidCig = cig == null || "".equals(cig.trim());
		boolean isInValidNomeScheda = (schedaDaEliminare.isSetIDSCHEDALOCALE() || schedaDaEliminare.isSetIDSCHEDASIMOG()) && !schedaDaEliminare.isSetSCHEDA();
		SchedaSpecificaValidationBean validation = null;
// controllo formale
		// cig valido ?
		if(isInValidCig){
			isCancellabile = false;
			esito.setMessaggioErrore(Messaggi.SIMOG_MASSLOADER_195.replace("$1", "CIG"));

			validation = new SchedaSpecificaValidationBean(
						Messaggi.SIMOG_MASSLOADER_195.replace("$1", "CIG"), 
							ValidationBean.VALBEAN_SEV_ERR,
							schedaDaEliminare.getSCHEDA().toString(), 
							schedaDaEliminare.getCIG(), 
							schedaDaEliminare.getCUI(),
							schedaDaEliminare.getIDSCHEDASIMOG(),
							schedaDaEliminare.getIDSCHEDALOCALE());

		}else{
			// cui valido ?
			if(isValidCui){
				String cigFromCui = CIGBean.getRealCIG(cui.substring(0, 10));
				isCancellabile = cigFromCui.equals(cig);
				if(!isCancellabile){
					esito.setMessaggioErrore(Messaggi.SIMOG_MASSLOADER_177.replace("$1", cig).replace("$2",cui));

					validation = new SchedaSpecificaValidationBean(Messaggi.SIMOG_MASSLOADER_177.replace("$1", cig).replace("$2",cui), ValidationBean.VALBEAN_SEV_ERR,
							schedaDaEliminare.getSCHEDA().toString(), schedaDaEliminare.getCIG(), schedaDaEliminare.getCUI(),
							schedaDaEliminare.getIDSCHEDASIMOG(),schedaDaEliminare.getIDSCHEDALOCALE());


				}
				// controllo preventivo per essere sicuri che sia valorizzato il nome schedas
				if( isCancellabile && isInValidNomeScheda){
					esito.setMessaggioErrore(Messaggi.SIMOG_MASSLOADER_190);

					validation = new SchedaSpecificaValidationBean(Messaggi.SIMOG_MASSLOADER_190, ValidationBean.VALBEAN_SEV_ERR,
							schedaDaEliminare.getSCHEDA() == null ? "": schedaDaEliminare.getSCHEDA().toString(),
							schedaDaEliminare.getCIG(), schedaDaEliminare.getCUI(),
							schedaDaEliminare.getIDSCHEDASIMOG(),schedaDaEliminare.getIDSCHEDALOCALE());

					isCancellabile = false;
				}
				// controllo che se e' una scheda dati comuni non sia valorizzato il cui. che e' sintomo di errore, perche esistono dati comuni
				if( isCancellabile && !isInValidNomeScheda){
					// erroneamente potrebbe darsi ce si valorizzi il cui per la cancellazione dei dati comuni. Glie la diamo per buona anche se sintomo di errore
					// dato che se esiste una aggiudicazione non posso cancellare i dati comuni.
					if(schedaDaEliminare.getSCHEDA() != null 
							&& IdentificativoSchede.DATI_COMUNI.equals(schedaDaEliminare.getSCHEDA().toString())
							&& !"".equals(schedaDaEliminare.getCUI())){
						
						esito.setMessaggioErrore(Messaggi.SIMOG_VALIDAZIONE_135.replace("$1", "CUI"));

						validation = new SchedaSpecificaValidationBean(Messaggi.SIMOG_VALIDAZIONE_135.replace("$1", "CUI"), ValidationBean.VALBEAN_SEV_ERR,
							schedaDaEliminare.getSCHEDA().toString(), schedaDaEliminare.getCIG(), schedaDaEliminare.getCUI(),
							schedaDaEliminare.getIDSCHEDASIMOG(),schedaDaEliminare.getIDSCHEDALOCALE());

						isCancellabile = false;				
					}

				}
				// se ancora cancellabile controlla afferenza cui -> scheda
				if(isCancellabile && schedaDaEliminare.isSetSCHEDA()){
					String idLocale = schedaDaEliminare.getIDSCHEDALOCALE();
					String idScheda = schedaDaEliminare.getIDSCHEDASIMOG();
					IdentificativoSchede identificativo = IdentificativoSchede.findIdentificativoByName(schedaDaEliminare.getSCHEDA().toString());
					boolean byIdSimog = schedaDaEliminare.isSetIDSCHEDASIMOG();
					EliminazioneSchedeManager eliminazioneManager = new EliminazioneSchedeManager(con, logger, cfUtente);
					isCancellabile = eliminazioneManager.controllaAfferenzaCUIConScheda(cui, idScheda, idLocale, identificativo, byIdSimog);
					if(!isCancellabile){
						
						esito.setMessaggioErrore(Messaggi.SIMOG_MASSLOADER_191.replace("$1", schedaDaEliminare.getSCHEDA().toString()));
						
						validation = new SchedaSpecificaValidationBean(Messaggi.SIMOG_MASSLOADER_191.replace("$1", schedaDaEliminare.getSCHEDA().toString()), ValidationBean.VALBEAN_SEV_ERR,
								schedaDaEliminare.getSCHEDA().toString(), schedaDaEliminare.getCIG(), schedaDaEliminare.getCUI(),
								schedaDaEliminare.getIDSCHEDASIMOG(),schedaDaEliminare.getIDSCHEDALOCALE());

					}
				}

			}		
		}
		this.fillEsitoOperazione(isCancellabile, validation, esito, schedaDaEliminare, riepilogoScheda);
		return esito;
	}

	/**
	 * Controlla la correttezza del flusso(tramite WORKFLOWCONTROLLER) per la scheda da cancellare
	 * NOTA: il bean in ingresso SituazioneSchedeAttuale viene valorizzato serve in inpunt alla cancellazione ne
	 * caso in cui l'esito sia stato positivo
	 * 
	 * @param situazioneAttuale : la situazione attuale vuota
	 * @param schedaDaEliminare : La scheda da eliminare
	 * @return
	 */
	public EsitoOperazioneCancellazioneBean controllaCorrettezzaFlusso(int tipoCancellazioneAttuale ,SituazioneSchedeAttuale situazioneAttuale, RecIdSchedaElimType schedaDaEliminare ){
		
		EsitoOperazioneCancellazioneBean esito = new EsitoOperazioneCancellazioneBean();
		TipoFlusso tipoFlusso = TipoFlusso.AGGIUDICAZIONE;
		try {
			InfoGaraBean igb = getInfoGara(schedaDaEliminare.getCIG());
			tipoFlusso = SimogValidator.getTipoFlusso(igb);
			
       if(tipoFlusso == null)
          tipoFlusso =  TipoFlusso.AGGIUDICAZIONE; // retrocompatibilità ??? oppure devo gestire il null??????

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WorkFlowController workFlowController = new WorkFlowController(tipoFlusso);
		String riepilogoScheda = this.costruisciRiferimentiSchedaString(schedaDaEliminare);
		boolean isCancellabile = false;		
		String nomeScheda = schedaDaEliminare.isSetSCHEDA() ? schedaDaEliminare.getSCHEDA().toString(): null;
		String cig = schedaDaEliminare.getCIG();
		String cui = schedaDaEliminare.getCUI();
		EsitoControlloStatiSchede esitoStato = null;
		try{
			IdentificativoSchede identificativo = null;
			if(nomeScheda != null ) identificativo = IdentificativoSchede.findIdentificativoByName(nomeScheda);
			
			switch (tipoCancellazioneAttuale) {
			case ELIMBYCUI:
				// cancellazione totale dalla aggiudicazione in poi	
				if(tipoFlusso.equals(TipoFlusso.AGGIUDICAZIONE))
					esitoStato = workFlowController.isNotFlussoInRichiesta(situazioneAttuale, IdentificativoSchede.getAggiudicazione(),cig,cui);
				else if(tipoFlusso.equals(TipoFlusso.ADESIONE))
					esitoStato = workFlowController.isNotFlussoInRichiesta(situazioneAttuale, IdentificativoSchede.getAdesione(),cig,cui);
				else if(tipoFlusso.equals(TipoFlusso.SOTTOSOGLIA))
					esitoStato = workFlowController.isNotFlussoInRichiesta(situazioneAttuale, IdentificativoSchede.getSottosoglia(),cig,cui);
				else if(tipoFlusso.equals(TipoFlusso.ESCLUSO))
					esitoStato = workFlowController.isNotFlussoInRichiesta(situazioneAttuale, IdentificativoSchede.getEscluso(),cig,cui);
				//gm aggiunto controllo per cancellazione del flusso stipula
				else if(tipoFlusso.equals(TipoFlusso.STIPULA))
					esitoStato = workFlowController.isNotFlussoInRichiesta(situazioneAttuale, IdentificativoSchede.getStipula(),cig,cui);
			
				isCancellabile = esitoStato.isEsitoOperazione();
				break;
			case ELIMBYIDSIMOG:
				// cancellazione scheda by id simog
				esitoStato = workFlowController.isNotFlussoInRichiesta(situazioneAttuale, identificativo,cig,cui);
				isCancellabile = esitoStato.isEsitoOperazione();
				if(isCancellabile){
					isCancellabile = workFlowController.isOperazioneEffettuabile(OperazioneScheda.getCancellazione(), identificativo, situazioneAttuale);
					if(!isCancellabile){
						esitoStato.setListOfValidationBeans(workFlowController.getEsitiOperazioni());
					}
				}
				break;
			case ELIMBYIDLOCALE:
				// cancellazione scheda by id locale
				esitoStato = workFlowController.isNotFlussoInRichiesta(situazioneAttuale, identificativo,cig,cui);
				isCancellabile = esitoStato.isEsitoOperazione();
				if(isCancellabile){
					isCancellabile = workFlowController.isOperazioneEffettuabile(OperazioneScheda.getCancellazione(), identificativo, situazioneAttuale);
					if(!isCancellabile){
						esitoStato.setListOfValidationBeans(workFlowController.getEsitiOperazioni());
					}
				}
				break;
			default:
				throw new NotFound();
			}
			
			
			if(isCancellabile){
				EsitoControlloStatiSchede esitoRiagg = controllaFlussoRiaggiudicato(situazioneAttuale);
				if(!esitoRiagg.isEsitoOperazione()){
					esitoStato.addListOfValidationBeans(esitoRiagg.getListOfValidationBeans());
					esitoStato.setEsitoOperazione(false);
					isCancellabile = false;
				}
			}
			
			this.fillEsitoOperazione(isCancellabile, esitoStato, esito, schedaDaEliminare, riepilogoScheda);
		}catch(NotFound notFound){
			// errore applicativo interno.. vedi che puoi fare..
			String messaggioErrore = "Si e' incorsi in eccezione NotFound durante il controllo della cancellabilita' di una scheda ";
			logger.error(messaggioErrore + ": " + notFound.getMessage());
			fillEsitoOperazioneErrore(esito, notFound, false, messaggioErrore);
		}
//		catch(Exception e){
//			String messaggioErrore = "Si e' incorsi in eccezione durante il controllo della cancellabilita' di una scheda ";
//			logger.fatal(messaggioErrore + ": " + e.getMessage());
//			fillEsitoOperazioneErrore(esito, e, false, messaggioErrore);
//		}
		return esito;
	}
	

	
	/**
	 * Controlla la correttezza del flusso in questo caso solamente se c'e' qualche scheda in
	 * richiesta di annullamento altrimenti ritorna true.
	 * 
	 * @param tipoCancellazioneAttuale vedi costanti di classe
	 * @param situazioniAttuali una array list di situazioni (cardinalita associata alla aggiudicazione)
	 * @param schedaDaEliminare scheda corrente da eliminare
	 * @return
	 */
	public EsitoOperazioneCancellazioneBean controllaCorrettezzaFlussoCIG(int tipoCancellazioneAttuale, ArrayList<SituazioneSchedeAttuale> situazioniAttuali, RecIdSchedaElimType schedaDaEliminare ){
		
		EsitoOperazioneCancellazioneBean esito = new EsitoOperazioneCancellazioneBean();
		TipoFlusso tipoFlusso = TipoFlusso.AGGIUDICAZIONE;
		try {
			InfoGaraBean igb = getInfoGara(schedaDaEliminare.getCIG());
			tipoFlusso = SimogValidator.getTipoFlusso(igb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(tipoFlusso == null)
		   tipoFlusso =  TipoFlusso.AGGIUDICAZIONE; // retrocompatibilità ??? oppure devo gestire il null??????

		WorkFlowController workFlowController = new WorkFlowController(tipoFlusso);
		/** variabili locali **/
		String riepilogoScheda = this.costruisciRiferimentiSchedaString(schedaDaEliminare);
		boolean isCancellabile = false;
		EsitoControlloStatiSchede esitoStato = null;
		String cig = schedaDaEliminare.getCIG();
		String cui = schedaDaEliminare.getCUI();
		try{
			
			switch (tipoCancellazioneAttuale) {
			case ELIMBYCIG:
				// cancellazione totale dati comuni + (0,n) aggiudicazioni e figli
				for(SituazioneSchedeAttuale situazioneCorrente: situazioniAttuali){
					esitoStato = workFlowController.isNotFlussoInRichiesta(situazioneCorrente, IdentificativoSchede.getDatiComuni(),cig,cui);
					isCancellabile = esitoStato.isEsitoOperazione();
//					if(isCancellabile){
//						EsitoControlloStatiSchede esitoRiagg = controllaFlussoRiaggiudicato(situazioneCorrente) ;
//						if(!esitoRiagg.isEsitoOperazione()){
//							esitoStato.addListOfValidationBeans(esitoRiagg.getListOfValidationBeans());
//							esitoStato.setEsitoOperazione(false);
//							isCancellabile = false;
//						}
//					}
					if(!isCancellabile) break;
				}
				break;
			default:
				throw new NotFound();
			}
			
			
			
			this.fillEsitoOperazione(isCancellabile, esitoStato, esito, schedaDaEliminare, riepilogoScheda);
		}catch(NotFound notFound){
			// TODO: COSTANTI DI ERRORE ?, report scheda ?
			String messaggioErrore = "Si e' incorsi in eccezione NotFound durante il controllo della cancellabilita' di una scheda ";
			logger.error(messaggioErrore + ": " + notFound.getMessage());
			fillEsitoOperazioneErrore(esito, notFound, false, messaggioErrore);
		}catch(Exception e){
			String messaggioErrore = "Si e' incorsi in eccezione durante il controllo della cancellabilita' di una scheda ";
			logger.fatal(messaggioErrore + ": " + e.getMessage());
			fillEsitoOperazioneErrore(esito, e, false, messaggioErrore);
		}
		return esito;
	}

	/**
	 * Controlla i parametri per la cancellazione, e chiama la cancellazione adeguata.
	 * 
	 * 
	 * @param schedaDaEliminare
	 * @return
	 */
	public EsitoOperazioneCancellazioneBean eliminaScheda(SituazioneSchedeAttuale situazioneAttuale, RecIdSchedaElimType schedaDaEliminare){
		
		ReportCancellazioneSchede reportPiuSchede = null;
		ReportCancellazioneScheda reportSingolaScheda = null;	
		
		String cui = schedaDaEliminare.getCUI();
		String cig = schedaDaEliminare.getCIG();
		String nomeScheda = schedaDaEliminare.isSetSCHEDA() ? schedaDaEliminare.getSCHEDA().toString(): null;
		String idLocale = schedaDaEliminare.getIDSCHEDALOCALE();
		String idScheda = schedaDaEliminare.getIDSCHEDASIMOG();
		String riepilogoScheda = this.costruisciRiferimentiSchedaString(schedaDaEliminare);
	
		EliminazioneSchedeManager eliminazioneManager = new EliminazioneSchedeManager(con, logger, cfUtente);
		EsitoOperazioneCancellazioneBean esito = new EsitoOperazioneCancellazioneBean();
		
		
		try{
			IdentificativoSchede identificativo = null;
			if(nomeScheda != null ) identificativo = IdentificativoSchede.findIdentificativoByName(nomeScheda);
			
			int tipoCancellazioneAttuale  = this.tipoDiCancellazione(schedaDaEliminare);
			
			switch (tipoCancellazioneAttuale) {
			case ELIMBYCUI:
				// cancellazione totale dalla aggiudicazione in poi			
				reportPiuSchede = eliminazioneManager.cancellaDaAggiudicazioneByCUI(situazioneAttuale);
				this.fillEsitoOperazioneInfo(esito, reportPiuSchede, reportSingolaScheda, false, riepilogoScheda);
				
				break;
			case ELIMBYIDSIMOG:
				// cancellazione scheda by id simog
				reportSingolaScheda = eliminazioneManager.cancellaScheda(identificativo, cig, cui, null, idLocale, idScheda, true);
				this.fillEsitoOperazioneInfo(esito, reportPiuSchede, reportSingolaScheda, true, riepilogoScheda);
				
				break;
			case ELIMBYIDLOCALE:
				// cancellazione scheda by id locale
				String idAggiudicazione = String.valueOf(situazioneAttuale.getStatoAggiudicazioneSottotipo().getIdRecord());
				reportSingolaScheda = eliminazioneManager.cancellaScheda(identificativo, cig, cui, idAggiudicazione, idLocale, idScheda, false);
				this.fillEsitoOperazioneInfo(esito, reportPiuSchede, reportSingolaScheda, true, riepilogoScheda);
				
				break;
			default:
				throw new NotFound();
			}
		}catch(EliminazioneFallitaException efe){
			String messaggioErrore = "Si e' incorsi in eccezione EliminazioneFallitaException durante l'operazione di cancellazione di una scheda ";
			logger.error(messaggioErrore + ": " + efe.getMessage());
			fillEsitoOperazioneErrore(esito, efe);
		}catch(NotFound notFound){
			// se capita qui e' un errore di implementazione
			String messaggioErrore = "Si e' incorsi in eccezione NotFound durante l'operazione di cancellazione di una scheda ";
			logger.error(messaggioErrore + ": " + notFound.getMessage());
			fillEsitoOperazioneErrore(esito, notFound, false, messaggioErrore);
		}catch(SQLException sqle){
			String messaggioErrore = "Si e' incorsi in eccezione SQL durante l'operazione di cancellazione di una scheda ";
			logger.fatal(messaggioErrore + ": " + sqle.getMessage());
			fillEsitoOperazioneErrore(esito, sqle, false, messaggioErrore);
		}
		return esito;
	}
	/**
	 * Controlla i parametri per la cancellazione, e chiama la cancellazione adeguata.
	 * 
	 * 
	 * @param schedaDaEliminare
	 * @return
	 */
	public EsitoOperazioneCancellazioneBean eliminaScheda(ArrayList<SituazioneSchedeAttuale> situazioniAttuali, RecIdSchedaElimType schedaDaEliminare){
		
		ReportCancellazioneSchede reportPiuSchede = null;
		ReportCancellazioneScheda reportSingolaScheda = null;
		
		String riepilogoScheda = this.costruisciRiferimentiSchedaString(schedaDaEliminare);	
		
		EsitoOperazioneCancellazioneBean esito = new EsitoOperazioneCancellazioneBean();
		EliminazioneSchedeManager eliminazioneManager = new EliminazioneSchedeManager(con, logger, cfUtente);		
		
		try{
			
			int tipoCancellazioneAttuale  = this.tipoDiCancellazione(schedaDaEliminare);
			
			switch (tipoCancellazioneAttuale) {
			case ELIMBYCIG:
				// cancellazione totale dati comuni + (0,n) aggiudicazioni e figli				
				reportPiuSchede = eliminazioneManager.cancellaTuttoByCIG(situazioniAttuali);
				this.fillEsitoOperazioneInfo(esito, reportPiuSchede, reportSingolaScheda, false, riepilogoScheda);				
				break;
			default:
				throw new NotFound();
			}
		}catch(EliminazioneFallitaException efe){
			// dentro l'eccezione ho tutti i riferimenti della scheda che era in cancellazione.. (vedi oggetto idScheda)
			String messaggioErrore = "Si e' incorsi in eccezione EliminazioneFallitaException durante l'operazione di cancellazione di una scheda ";
			logger.error(messaggioErrore + ": " + efe.getMessage());
			fillEsitoOperazioneErrore(esito, efe);
		}catch(NotFound notFound){
			// errore applicativo(interno) si e' tentato di usare un tab non valido NON dovrebbe accadere..
			String messaggioErrore = "Si e' incorsi in eccezione NotFound durante l'operazione di cancellazione di una scheda ";
			logger.error(messaggioErrore + ": " + notFound.getMessage());
			fillEsitoOperazioneErrore(esito, notFound, false, messaggioErrore);
		}catch(SQLException sqle){
			String messaggioErrore = "Si e' incorsi in eccezione SQL durante l'operazione di cancellazione di una scheda ";
			logger.fatal(messaggioErrore + ": " + sqle.getMessage());
			fillEsitoOperazioneErrore(esito, sqle, false, messaggioErrore);
		}
//		catch(Exception e){
//			String messaggioErrore = "Si e' incorsi in eccezione durante l'operazione di cancellazione di una scheda ";
//			logger.fatal(messaggioErrore + ": " + e.getMessage());
//			fillEsitoOperazioneErrore(esito, e, false, messaggioErrore);
//		}
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
	 * Controlla che tutte le operazioni effettuate e riportate nell report
	 * siano true, altrimenti ritorna false, che sar� poi il valore usato 
	 * per il settaggio del bean esitoOperazione.
	 * 
	 * @param reportPiuSchede
	 * @return
	 */
	private boolean controllaReport(ReportCancellazioneSchede reportPiuSchede){
		ArrayList<ReportCancellazioneScheda> reports = reportPiuSchede.getReports();
		if(reports == null)return false;
		for(ReportCancellazioneScheda reportSingolaScheda : reports){
			if(!reportSingolaScheda.getEsitoOperazione()) return false;
		}return true;
	}
	
	/**
	 * Si occupa di identificare il tipo di cancellazione in base ai parametri nel tipo "RecIdSchedaElimType"
	 * 
	 * @param schedaDaEliminare
	 * @return
	 * @throws NotFound
	 */
	public int tipoDiCancellazione(RecIdSchedaElimType schedaDaEliminare) {
		
		// solo dati comuni
		if("".equals(schedaDaEliminare.getCUI())){
			
			if(!schedaDaEliminare.isSetSCHEDA()){
				// cancellazione totale dati comuni + (0,n) aggiudicazioni e figli
				logger.info("La configurazione dei dati indica che si tratta di una Eliminazione a partire dai Dati Comuni");
				return ELIMBYCIG;
			}else{
				if(IdentificativoSchede.DATI_COMUNI.equals(schedaDaEliminare.getSCHEDA().toString())){
					// se settato solo simog
					if(schedaDaEliminare.isSetIDSCHEDASIMOG()){
						logger.info("La configurazione dei dati indica che si tratta di una Eliminazione di una singola scheda tramite id simog, ma trattandosi di Dati Comuni eseguo la cancellazione per cig ");
						return ELIMBYCIG;
					}
					// se settato solo locale
					if(schedaDaEliminare.isSetIDSCHEDALOCALE()){
						logger.info("La configurazione dei dati indica che si tratta di una Eliminazione di una singola scheda tramite id locale, ma trattandosi di Dati Comuni eseguo la cancellazione per cig ");
						return ELIMBYCIG;
					}
				}
			}
				
		// anche altre schede
		}else{
			if(!schedaDaEliminare.isSetSCHEDA()){
				if(!schedaDaEliminare.isSetIDSCHEDALOCALE()&& !schedaDaEliminare.isSetIDSCHEDASIMOG()){
					// cancellazione totale dalla aggiudicazione in poi
					logger.info("La configurazione dei dati indica che si tratta di una Eliminazione a partire da Aggiudicazione");
					return ELIMBYCUI;
				}
			}else{
				// se settato solo simog
				if(schedaDaEliminare.isSetIDSCHEDASIMOG()){
					logger.info("La configurazione dei dati indica che si tratta di una Eliminazione di una singola scheda tramite id simog");
					return ELIMBYIDSIMOG;
				}
				// se settato solo locale
				if(schedaDaEliminare.isSetIDSCHEDALOCALE()){
					logger.info("La configurazione dei dati indica che si tratta di una Eliminazione di una singola scheda tramite id locale");
					return ELIMBYIDLOCALE;
				}
			}
		}
		return 0;		
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
	 * Valorizza l'esito operazione:
	 * - in caso il booleano sia false valorizza anche l'oggetto report nell'esito operazione, accoda alla stringa di riepilogo
	 * 		l'esito dell'operazione.
	 * 
	 * @param schedaDaEliminare
	 * @return
	 */
	private void cotruisciMessaggioErroreRifiuto(EsitoOperazioneCancellazioneBean esito, String idRecord, IdentificativoSchede identificativo ){// throws NotFound{
		esito.setMessaggioErrore("Fallito rifiuto di una richiesta per la scheda ["+identificativo.getNomeScheda()+"]" + " con idRecord ["+idRecord+"]");
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
	private void fillEsitoOperazioneInfo(EsitoOperazioneCancellazioneBean esitoBean, ReportCancellazioneSchede reportPiuSchede,
										ReportCancellazioneScheda reportSingolaScheda, boolean isSingola, String beanInfo){
		esitoBean.setSingola(isSingola);
		boolean esitoOperazione = false;
		if(isSingola){
			esitoBean.setReportSingolaScheda(reportSingolaScheda);
			esitoOperazione = this.controllaReport(reportSingolaScheda);
			
		}else{
			esitoBean.setReportPiuSchede(reportPiuSchede);
			esitoOperazione = this.controllaReport(reportPiuSchede);
		}
		beanInfo = this.aggiungiEsitoOperazioneString(esitoOperazione, beanInfo);
		esitoBean.setMessaggioInfo(beanInfo);
		esitoBean.setEsitoOperazione(esitoOperazione);
	}
	
//	private boolean controllaMatch(StatoScheda statoSchedaCorrente, long idRecord, Timestamp dataInizioRecord) throws Exception{
//		if(statoSchedaCorrente.getIdRecord() == idRecord)
//			if(statoSchedaCorrente.getDataInizioRecord().equals(dataInizioRecord)) return true;
//		
//		return false;
//	}
//	/**
//	 * Metodo che effettua il rifiuto di eventuali richieste in modo massivo a partire da dal CIG ovverosia 
//	 * daticomuni + n aggiudicazioni e figlie
//	 * 
//	 * @param situazioniAttuali
//	 * @param schedaDaEliminare
//	 * @return
//	 */
//	public EsitoOperazioneCancellazioneBean rifiutaDUfficioByCIG(ArrayList<SituazioneSchedeAttuale> situazioniAttuali, RecIdSchedaElimType schedaDaEliminare){
//		EsitoOperazioneCancellazioneBean esito = new EsitoOperazioneCancellazioneBean();
//		try{
//			
//			int i = 0;
//			for(SituazioneSchedeAttuale situazioneCorrente : situazioniAttuali){
//
//				// XX-X: devo modificare lo stato dei dati comuni poiche viene rifiutato alla prima iterazione.
//				if(i > 0 && esito.isEsitoOperazione())
//					situazioniAttuali.get(i).setStatoDatiComuni(new StatoScheda());
//
//				esito = this.rifiutaDUfficioByAggiudicazione(situazioneCorrente, schedaDaEliminare);
//				i++;
//			}
//
//		}catch(SQLException sqle){
//			String messaggioErrore = "Si e' incorsi in eccezione SQL durante il rifiuto di una richiesta di annullamento di una scheda ";
//			logger.fatal(messaggioErrore + ": " + sqle.getMessage());
//			fillEsitoOperazioneErrore(esito, sqle, false, messaggioErrore);
//		}catch(Exception e){
//			String messaggioErrore = "Si e' incorsi in eccezione durante il rifiuto di una richiesta di annullamento di una scheda ";
//			logger.fatal(messaggioErrore + ": " + e.getMessage());
//			fillEsitoOperazioneErrore(esito, e, false, messaggioErrore);
//		}
//		return esito;
//	}
//	/**
//	 * Metodo che si occupa del rifiuto d'ufficio di una richiesta di annullamento  o cancellazione di una o piu schede
//	 * Le richieste rifiutate d'ufficio saranno visibili solo sul log (fatto dagli appoositi manager..)
//	 * Aggiorna lo stato per le schede per le quali e' stata effettuata con successo il rifiuto della richiesta.
//	 * Valorizza un report all'interno dell'oggetto esitoOperazione per eventuali operazioni di feedback. (in genere in caso di insuccesso)
//	 * TO-DO: move to another layer
//	 * FIX-ME: attenzione possibili problemi di sincronia tra le liste di stati e i dati ritornati dal db,
//	 * 			e' stato introdotto un controllo per verificare il match, ma non risolve il possibile problema a
//	 * 			monte, occore definire un'ordinamento ? (XX-X)
//	 * 
//	 * @param situazioneAttuale
//	 * @param CUI
//	 * @param aggiudicazioneBean
//	 * @param byCui
//	 * @return
//	 * @throws SQLException
//	 * @throws Exception
//	 */
//	public EsitoOperazioneCancellazioneBean rifiutaDUfficioByAggiudicazione(SituazioneSchedeAttuale situazioneAttuale, RecIdSchedaElimType schedaDaEliminare) throws SQLException, Exception{	
//		RifiutoAction rifiutoAction = new RifiutoAction(con, logger, cfUtente);
//		EsitoOperazioneCancellazioneBean esito = new EsitoOperazioneCancellazioneBean();
//		String riepilogoScheda = this.costruisciRiferimentiSchedaString(schedaDaEliminare);
//				
//		/** Collaudo **/
//		if(situazioneAttuale.getStatoCollaudo().isEsistente()){
//			
//			IdentificativoSchede identificativo = IdentificativoSchede.getCollaudo();
//				
//			String idRecord = String.valueOf(situazioneAttuale.getStatoCollaudo().getIdRecord());
//			boolean esitoRifiuto = true;
//			if(situazioneAttuale.getStatoCollaudo().isInRichiestaAnnullamento()){
//				esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, false);
//			}
//			if(situazioneAttuale.getStatoCollaudo().isInRichiestaCancellazione()){
//				esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, true);
//			}
//			
//			// gestione esito operazione
//			if(esitoRifiuto){
//				fillStatoScheda(situazioneAttuale.getStatoCollaudo(), StatoScheda.NESSUNARICHIESTA, StatiScheda.CONFERMATO);
//			}else{
//				// nel caso di fallimento posso tornare subito
//				this.cotruisciMessaggioErroreRifiuto(esito, idRecord, identificativo);
//				this.fillEsitoOperazione(false, esito, schedaDaEliminare, riepilogoScheda);
//				return esito;
//			}
//
//		}
//		/** Conclusione **/
//		if(situazioneAttuale.getStatoConclusione().isEsistente()){
//			IdentificativoSchede identificativo = IdentificativoSchede.getConclusione();
//			
//			String idRecord = String.valueOf(situazioneAttuale.getStatoConclusione().getIdRecord());
//			boolean esitoRifiuto = true;
//			if(situazioneAttuale.getStatoConclusione().isInRichiestaAnnullamento()){
//				esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, false);
//			}
//			if(situazioneAttuale.getStatoConclusione().isInRichiestaCancellazione()){
//				
//			}
//			
//			// gestione esito operazione
//			if(esitoRifiuto){
//				fillStatoScheda(situazioneAttuale.getStatoConclusione(), StatoScheda.NESSUNARICHIESTA, StatiScheda.CONFERMATO);
//			}else{
//				// nel caso di fallimento posso tornare subito
//				this.cotruisciMessaggioErroreRifiuto(esito, idRecord, identificativo);
//				this.fillEsitoOperazione(false, esito, schedaDaEliminare, riepilogoScheda);
//				return esito;
//			}
//		}
//		/** Avanzamenti **/
//		ArrayList<StatoScheda> statiSchede = situazioneAttuale.getStatoAvanzamento();
//		if(statiSchede != null && statiSchede.size() > 0){
//			IdentificativoSchede identificativo = IdentificativoSchede.getAvanzamenti();
//			boolean esitoRifiuto = true;
//			for(StatoScheda statoCorrente : statiSchede){
//				if(statoCorrente.isEsistente()){
//					
//					String idRecord = String.valueOf(statoCorrente.getIdRecord());
//					if(statoCorrente.isInRichiestaAnnullamento()){
//						esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, false);
//					}
//					if(statoCorrente.isInRichiestaCancellazione()){
//						esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, true);
//					}
//					
//					// gestione esito operazione
//					if(esitoRifiuto){
//						fillStatoScheda(statoCorrente, StatoScheda.NESSUNARICHIESTA, StatiScheda.CONFERMATO);
//					}else{
//						// nel caso di fallimento posso tornare subito
//						this.cotruisciMessaggioErroreRifiuto(esito, idRecord, identificativo);
//						this.fillEsitoOperazione(false, esito, schedaDaEliminare, riepilogoScheda);
//						return esito;
//					}
//				}
//			}
//		}
//		/** Sospensioni **/
//		statiSchede = situazioneAttuale.getStatoSospensioni();
//		if(statiSchede != null && statiSchede.size() > 0){
//			IdentificativoSchede identificativo = IdentificativoSchede.getSospensioni();
//		
//			for(StatoScheda statoCorrente : statiSchede){			
//				if(statoCorrente.isEsistente()){
//
//					String idRecord = String.valueOf(statoCorrente.getIdRecord());
//					boolean esitoRifiuto = true;
//					if(statoCorrente.isInRichiestaAnnullamento()){
//						esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, false);
//					}
//					if(statoCorrente.isInRichiestaCancellazione()){
//						esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, true);
//					}
//					
//					// gestione esito operazione, se esito positivo aggiorna lo stato..
//					if(esitoRifiuto){
//						fillStatoScheda(statoCorrente, StatoScheda.NESSUNARICHIESTA, StatiScheda.CONFERMATO);
//					}else{
//						// nel caso di fallimento posso tornare subito
//						this.cotruisciMessaggioErroreRifiuto(esito, idRecord, identificativo);
//						this.fillEsitoOperazione(false, esito, schedaDaEliminare, riepilogoScheda);
//						return esito;
//					}
//				}
//			}
//		}
//		/** Accordi **/
//		statiSchede = situazioneAttuale.getStatoAccordi();
//		if(statiSchede != null && statiSchede.size() > 0){
//			IdentificativoSchede identificativo = IdentificativoSchede.getAccordi();
//			for(StatoScheda statoCorrente : statiSchede){
//				if(statoCorrente.isEsistente()){
//
//					String idRecord = String.valueOf(statoCorrente.getIdRecord());
//					boolean esitoRifiuto = true;
//					if(statoCorrente.isInRichiestaAnnullamento()){
//						esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, false);
//					}
//					if(statoCorrente.isInRichiestaCancellazione()){
//						esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, true);
//					}
//
//					// gestione esito operazione
//					if(esitoRifiuto){
//						fillStatoScheda(statoCorrente, StatoScheda.NESSUNARICHIESTA, StatiScheda.CONFERMATO);
//					}else{
//						// nel caso di fallimento posso tornare subito
//						this.cotruisciMessaggioErroreRifiuto(esito, idRecord, identificativo);
//						this.fillEsitoOperazione(false, esito, schedaDaEliminare, riepilogoScheda);
//						return esito;
//					}
//				}
//			}
//		}
//		/** InizioLavori **/
//		if(situazioneAttuale.getStatoInizioLavori().isEsistente()){
//			IdentificativoSchede identificativo = IdentificativoSchede.getInizioLavori();
//			
//			String idRecord = String.valueOf(situazioneAttuale.getStatoInizioLavori().getIdRecord());
//			boolean esitoRifiuto = true;
//			if(situazioneAttuale.getStatoInizioLavori().isInRichiestaAnnullamento()){
//				esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, false);
//			}
//			if(situazioneAttuale.getStatoInizioLavori().isInRichiestaCancellazione()){
//				esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, true);
//			}
//			
//			// gestione esito operazione
//			if(esitoRifiuto){
//				fillStatoScheda(situazioneAttuale.getStatoInizioLavori(), StatoScheda.NESSUNARICHIESTA, StatiScheda.CONFERMATO);
//			}else{
//				// nel caso di fallimento posso tornare subito
//				this.cotruisciMessaggioErroreRifiuto(esito, idRecord, identificativo);
//				this.fillEsitoOperazione(false, esito, schedaDaEliminare, riepilogoScheda);
//				return esito;
//			}
//		}	
//		/** Ipotesi recesso  **/
//		statiSchede = situazioneAttuale.getStatoRitardo();
//		if(statiSchede != null && statiSchede.size() > 0){
//			IdentificativoSchede identificativo = IdentificativoSchede.getRitardo();
//			for(StatoScheda statoCorrente : statiSchede){
//				if(statoCorrente.isEsistente()){
//					
//					String idRecord = String.valueOf(statoCorrente.getIdRecord());
//					boolean esitoRifiuto = true;
//					if(statoCorrente.isInRichiestaAnnullamento()){
//						esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, false);
//					}
//					if(statoCorrente.isInRichiestaCancellazione()){
//						esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, true);
//					}
//					
//					// gestione esito operazione
//					if(esitoRifiuto){
//						fillStatoScheda(statoCorrente, StatoScheda.NESSUNARICHIESTA, StatiScheda.CONFERMATO);
//					}else{
//						// nel caso di fallimento posso tornare subito
//						this.cotruisciMessaggioErroreRifiuto(esito, idRecord, identificativo);
//						this.fillEsitoOperazione(false, esito, schedaDaEliminare, riepilogoScheda);
//						return esito;
//					}
//				}
//			}
//		}
//		/** SubAppalti **/
//		statiSchede = situazioneAttuale.getStatoSubAppalti();
//		if(statiSchede != null && statiSchede.size() > 0){
//			IdentificativoSchede identificativo = IdentificativoSchede.getSubAppalti();
//			for(StatoScheda statoCorrente : statiSchede){
//				if(statoCorrente.isEsistente()){
//
//					String idRecord = String.valueOf(statoCorrente.getIdRecord());
//					boolean esitoRifiuto = true;
//					if(statoCorrente.isInRichiestaAnnullamento()){
//						esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, false);
//					}
//					if(statoCorrente.isInRichiestaCancellazione()){
//						esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, true);
//					}
//					
//					// gestione esito operazione
//					if(esitoRifiuto){
//						fillStatoScheda(statoCorrente, StatoScheda.NESSUNARICHIESTA, StatiScheda.CONFERMATO);
//					}else{
//						// nel caso di fallimento posso tornare subito
//						this.cotruisciMessaggioErroreRifiuto(esito, idRecord, identificativo);
//						this.fillEsitoOperazione(false, esito, schedaDaEliminare, riepilogoScheda);
//						return esito;
//					}
//				}
//				
//			}
//		}
//		/** Varianti **/
//		statiSchede = situazioneAttuale.getStatoVarianti();
//		if(statiSchede != null && statiSchede.size() > 0){
//			IdentificativoSchede identificativo = IdentificativoSchede.getVarianti();
//			for(StatoScheda statoCorrente : statiSchede){
//				if(statoCorrente.isEsistente()){
//
//					String idRecord = String.valueOf(statoCorrente.getIdRecord());
//					boolean esitoRifiuto = true;
//					if(statoCorrente.isInRichiestaAnnullamento()){
//						esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, false);
//					}
//					if(statoCorrente.isInRichiestaCancellazione()){
//						esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, true);
//					}
//					
//					// gestione esito operazione
//					if(esitoRifiuto){
//						fillStatoScheda(statoCorrente, StatoScheda.NESSUNARICHIESTA, StatiScheda.CONFERMATO);
//					}else{
//						// nel caso di fallimento posso tornare subito
//						this.cotruisciMessaggioErroreRifiuto(esito, idRecord, identificativo);
//						this.fillEsitoOperazione(false, esito, schedaDaEliminare, riepilogoScheda);
//						return esito;
//					}
//				}
//				
//			}
//		}		
//		/** Aggiudicazione **/
//		if(situazioneAttuale.getStatoAggiudicazione().isEsistente()){
//			IdentificativoSchede identificativo = IdentificativoSchede.getAggiudicazione();
//			
//			String idRecord = String.valueOf(situazioneAttuale.getStatoAggiudicazione().getIdRecord());
//			boolean esitoRifiuto = true;
//			if(situazioneAttuale.getStatoAggiudicazione().isInRichiestaAnnullamento()){
//				esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, false);
//			}
//			if(situazioneAttuale.getStatoAggiudicazione().isInRichiestaCancellazione()){
//				esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, true);
//			}
//			
//			// gestione esito operazione
//			if(esitoRifiuto){
//				fillStatoScheda(situazioneAttuale.getStatoAggiudicazione(), StatoScheda.NESSUNARICHIESTA, StatiScheda.CONFERMATO);
//			}else{
//				// nel caso di fallimento posso tornare subito
//				this.cotruisciMessaggioErroreRifiuto(esito, idRecord, identificativo);
//				this.fillEsitoOperazione(false, esito, schedaDaEliminare, riepilogoScheda);
//				return esito;
//			}
//		}
//
//		/** Dati comuni **/
//		if(situazioneAttuale.getStatoDatiComuni().isEsistente()){
//			IdentificativoSchede identificativo = IdentificativoSchede.getDatiComuni();
//			
//			String idRecord = String.valueOf(situazioneAttuale.getStatoDatiComuni().getIdRecord());
//			boolean esitoRifiuto = true;
//			if(situazioneAttuale.getStatoDatiComuni().isInRichiestaAnnullamento()){
//				esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, false);
//			}
//			if(situazioneAttuale.getStatoDatiComuni().isInRichiestaCancellazione()){
//				esitoRifiuto = rifiutoAction.rifiuta(identificativo.getDecodificaBlocco(), idRecord, true);
//			}
//			
//			// gestione esito operazione
//			if(esitoRifiuto){
//				fillStatoScheda(situazioneAttuale.getStatoDatiComuni(), StatoScheda.NESSUNARICHIESTA, StatiScheda.CONFERMATO);
//			}else{
//				// nel caso di fallimento posso tornare subito
//				this.cotruisciMessaggioErroreRifiuto(esito, idRecord, identificativo);
//				this.fillEsitoOperazione(false, esito, schedaDaEliminare, riepilogoScheda);
//				return esito;
//			}
//		}
//		this.fillEsitoOperazione(true, esito, schedaDaEliminare, riepilogoScheda);
//		return esito;
//	}
//	
//	/**
//	 * Aggiornamento dello stato della scheda
//	 * 
//	 * @param statoSchedaCorrente
//	 * @param statoSchedaBeanConstant
//	 * @param stato
//	 */
//	private void fillStatoScheda(StatoScheda statoSchedaCorrente, int statoSchedaBeanConstant, int stato){
//		statoSchedaCorrente.setInRichiesta(statoSchedaBeanConstant);
//		statoSchedaCorrente.setStato(stato);
//	}
	
	public InfoGaraBean getInfoGara (String CIG) throws Exception{
		InfoComuniManager icm = new InfoComuniManager(con, logger);
		LottoManager lm = new LottoManager(con, logger);
		List<Lotto> lottoByCigWS = lm.getLottoByCigWS(CIG);
		
		InfoGaraBean igBean = null;
		
		try {
		   if(!lottoByCigWS.isEmpty())
			   igBean = icm.loadInfoGara(lottoByCigWS.get(0).getId_Lotto());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return igBean;
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
				esito.getListOfValidationBeans().add(SchedaSpecificaValidationBean.getThisKindOfValidationBeanErr(situazioneAttuale.getStatoAggiudicazioneSottotipo(), 0, 0, 0, IdentificativoSchede.AGGIUDICAZIONE, Messaggi.SIMOG_MASSLOADER_207));
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


}
