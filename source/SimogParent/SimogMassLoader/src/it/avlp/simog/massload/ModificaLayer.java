package it.avlp.simog.massload;

import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.flusso.OperazioneScheda;
import it.avlp.simog.massload.actions.ModificaException;
import it.avlp.simog.massload.actions.ModificaSchedeAction;
import it.avlp.simog.massload.bean.IdsSchedaXML;
import it.avlp.simog.massload.bean.schede.TutteLeSchede;
import it.avlp.simog.massload.caricamento.CaricamentoBusiness;
import it.avlp.simog.massload.esito.EsitoOperazioneControlloLogico;
import it.avlp.simog.massload.esito.EsitoOperazioneInserimentoOModifica;
import it.avlp.simog.massload.esito.EsitoValidazioneBean;
import it.avlp.simog.massload.manager.DbManager;
import it.avlp.simog.massload.util.DataMerger;
import it.avlp.simog.massload.util.FeedBackWriterBase;
import it.avlp.simog.massload.util.FeedBackWriterValidationsBeans;
import it.avlp.simog.massload.util.conversion.ConvertXMLtoBeanBusiness;
import it.avlp.simog.massload.validation.MassloaderValidator;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class ModificaLayer {

	private static String INVOKER = "ModificaLayer";
	
	private Connection con;
	private String CONNECTIONID = "";
	private Logger logger;
	private FeedBackWriterValidationsBeans feedBackWriter;
	private FeedBack feedback;
	private String user;
	private OrigineSchedaEnum origine;
	  
	// serve a gestire la gestione delle schede a livello cig (rollback e non processamento)
	public ArrayList<String> listOfCigNonValidi;
	
	private TreeMap<String, Integer> listOfWarning;
	
	public ModificaLayer(Connection con, Logger logger, 
	      FeedBackWriterValidationsBeans feedBackWriter, FeedBack feedback,
	      String user, OrigineSchedaEnum origine) {
		this.con = con;
		this.CONNECTIONID = con.toString();
		this.logger = logger;
		this.feedBackWriter = feedBackWriter;
		this.feedback = feedback;
		this.user = user;
		this.origine = origine;
	}
	
	public void eseguiOperazione(ModificaSchedeAction action, MassloaderValidator validator, ArrayList<IdsSchedaXML> schedeInModifica,
				List<SoggettoPartecipanteBean> listOfAnaPartecipanti, List<SoggettoResponsabileBean> listOfAnaResponsabili, ConvertXMLtoBeanBusiness converter,
				CaricamentoBusiness loader) throws Exception{
		try{
			DbManager.staticSetAutoCommitFalse(con, logger, INVOKER, CONNECTIONID);
			// effettuo il controllo logico.
			EsitoOperazioneControlloLogico esitoControlloLogico = action.controllaCorrettezzaLogica(schedeInModifica);
			if(esitoControlloLogico.isSomeSchedeNonValide()){
				logger.debug("Al controllo logico sono risutate non valide alcune schede");
				feedBackWriter.fillMassloaderFeedBack(feedback, esitoControlloLogico.getListOfValidationsBeans(), OperazioneScheda.MODIFICA);
				
				if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
				this.listOfCigNonValidi.addAll(esitoControlloLogico.getListOfCigNonValidi());
			}
//			Se ci sono ancora schede valide dopo il controllo formale e logico..
			if(esitoControlloLogico.isEsitoOperazione()){

				ArrayList<IdsSchedaXML> schedeDaModificare = esitoControlloLogico.getListOfSchedeValide();
				
				// basta una istanziazione..							
				DataMerger merger = new DataMerger(converter, loader);
				
				if(schedeDaModificare == null){
					schedeDaModificare = new ArrayList<IdsSchedaXML>();
				}
		
//									ArrayList<IdsSchedaXML> schedeValidate = new ArrayList<IdsSchedaXML>();
				for(IdsSchedaXML ids : schedeDaModificare){
					
					if(this.listOfCigNonValidi == null || (this.listOfCigNonValidi != null && !this.listOfCigNonValidi.contains(ids.getCig()))){
						InfoGaraBean infoGara = action.getInfoGara(ids.getCig());

						TutteLeSchede tutteLeSchede = merger.mergePerModifica(ids.getScheda(), 
								ids.getSituazioneAttuale(), ids.getSituazioneAttualeXml(), 
								ids.getCrossFieldsForInfoComuniValidation(), 
								listOfAnaResponsabili, listOfAnaPartecipanti, infoGara);
						
						if(merger.containsDuplicate){
							feedBackWriter.fillStandardFeedBack(feedback, merger.listOfDuplicateWarning, OperazioneScheda.MODIFICA,
									null, ids.getCig(), ids.getCui(), ids.getCardinalitaSchedaCompleta(), null, null);
							
							
							//(test.getFeedbackXml().getFeedBack(), merger.listOfDuplicateWarning, OperazioneScheda.INSERIMENTO);
						}
						
	// VL  devo controllare che se ce n'e' anche solo una non valida, skippo la scheda completa. ovviamente scrivendo il feedback con gli errori occorsi.
						boolean esisteUnaSchedaNonValida = false;
						String cig = ids.getCig();
						String cui = ids.getCui();
						int progressivoSchedaCompleta = ids.getCardinalitaSchedaCompleta();
						int elemento = ids.getCardinalitaSchedaCig();
						
						/** validazione schede singole.. **/
//						if(ids.getSituazioneAttuale().getStatoDatiComuni().isFromXml()){
						if(ids.getSituazioneAttualeXml().isPresentDatiComuni()){
// PP cui vuoto per i dati comuni							EsitoValidazioneBean esitoDatiComuni = validator.validaDatiComuni(tutteLeSchede.getSchedaA(),cig,cui,progressivoSchedaCompleta);
                     EsitoValidazioneBean esitoDatiComuni = validator.validaDatiComuni(tutteLeSchede.getSchedaA(),cig,"",progressivoSchedaCompleta);
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
							feedBackWriter.fillMassloaderFeedBack(feedback, esitoDatiComuni.getListOfValidations(), OperazioneScheda.MODIFICA);
	
						}
						//if(ids.getSituazioneAttuale().getStatoAggiudicazione().isFromXml()){
						if(ids.getSituazioneAttualeXml().isPresentAggiudicazione()){
							EsitoValidazioneBean esitoAggiudicazione = validator.validaAggiudicazione(tutteLeSchede.getSchedaA(),cig,cui,progressivoSchedaCompleta);
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
							feedBackWriter.fillMassloaderFeedBack(feedback, esitoAggiudicazione.getListOfValidations(), OperazioneScheda.MODIFICA);
	
						}
						if(ids.getSituazioneAttualeXml().isPresentAdesione()){
							EsitoValidazioneBean esitoAdesione = validator.validaAdesione(tutteLeSchede.getSchedaA(),cig,cui,progressivoSchedaCompleta);
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
							feedBackWriter.fillMassloaderFeedBack(feedback, esitoAdesione.getListOfValidations(), OperazioneScheda.MODIFICA);
	
						}
						if(ids.getSituazioneAttualeXml().isPresentSottosoglia()){
							EsitoValidazioneBean esitoAggiudicazione = validator.validaSottosoglia(tutteLeSchede.getSchedaA(),cig,cui,progressivoSchedaCompleta);
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
							feedBackWriter.fillMassloaderFeedBack(feedback, esitoAggiudicazione.getListOfValidations(), OperazioneScheda.MODIFICA);
	
						}
						if(ids.getSituazioneAttualeXml().isPresentEscluso()){
							EsitoValidazioneBean esitoAggiudicazione = validator.validaEscluso(tutteLeSchede.getSchedaA(),cig,cui,progressivoSchedaCompleta);
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
							feedBackWriter.fillMassloaderFeedBack(feedback, esitoAggiudicazione.getListOfValidations(), OperazioneScheda.MODIFICA);
	
						}
//						if(ids.getSituazioneAttuale().getStatoInizioLavori() .isFromXml()){
						if(ids.getSituazioneAttualeXml().isPresentInizioLavori()){
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
							feedBackWriter.fillMassloaderFeedBack(feedback, esitoInizio.getListOfValidations(), OperazioneScheda.MODIFICA);
	
						}
						
						if(ids.getSituazioneAttualeXml().isPresentStipula()){
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
							feedBackWriter.fillMassloaderFeedBack(feedback, esitoStipula.getListOfValidations(), OperazioneScheda.MODIFICA);
	
						}
//						if(ids.getSituazioneAttuale().getStatoConclusione() .isFromXml()){
						if(ids.getSituazioneAttualeXml().isPresentConclusione()){
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
							feedBackWriter.fillMassloaderFeedBack(feedback, esitoFine.getListOfValidations(), OperazioneScheda.MODIFICA);
	
						}
//						if(ids.getSituazioneAttuale().getStatoCollaudo() .isFromXml()){
						if(ids.getSituazioneAttualeXml().isPresentCollaudo()){
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
							feedBackWriter.fillMassloaderFeedBack(feedback, esitoCollaudo.getListOfValidations(), OperazioneScheda.MODIFICA);
	
						}
						
						/** validazione schede multiple **/
//						boolean notEmpty = ids.getSituazioneAttuale().getStatoAvanzamento() != null && ids.getSituazioneAttuale().getStatoAvanzamento().size() > 0;
//						if(notEmpty && ids.getSituazioneAttuale().getStatoAvanzamento().get(0) .isFromXml()){
						if(ids.getSituazioneAttualeXml().isPresentAvanzamenti()){
							EsitoValidazioneBean esitoAvanzamenti = validator.validaAvanzamenti(tutteLeSchede.getSchedeAvanzamento(),ids.getAvanzamentiPosizioneModifica(), cig,cui,progressivoSchedaCompleta);
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
							feedBackWriter.fillMassloaderFeedBack(feedback, esitoAvanzamenti.getListOfValidations(), OperazioneScheda.MODIFICA);
	
						}
//						notEmpty = ids.getSituazioneAttuale().getStatoAccordi() != null && ids.getSituazioneAttuale().getStatoAccordi().size() > 0;
//						if( notEmpty && ids.getSituazioneAttuale().getStatoAccordi().get(0).isFromXml()){
						if(ids.getSituazioneAttualeXml().isPresentAccordi()){
							EsitoValidazioneBean esitoAccordi = validator.validaAccordi(tutteLeSchede.getSchedeAccordo(),ids.getAccordiPosizioneModifica(), cig,cui,progressivoSchedaCompleta);
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
							feedBackWriter.fillMassloaderFeedBack(feedback, esitoAccordi.getListOfValidations(), OperazioneScheda.MODIFICA);
	
						}
//						notEmpty = ids.getSituazioneAttuale().getStatoRitardo() != null && ids.getSituazioneAttuale().getStatoRitardo().size() > 0;
//						if( notEmpty && ids.getSituazioneAttuale().getStatoRitardo().get(0) .isFromXml()){
						if(ids.getSituazioneAttualeXml().isPresentRitardo()){	
							EsitoValidazioneBean esitoRitardi = validator.validaRitardi(tutteLeSchede.getSchedeRitardi(),ids.getRitardiPosizioneModifica(), cig,cui,progressivoSchedaCompleta);
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
							feedBackWriter.fillMassloaderFeedBack(feedback, esitoRitardi.getListOfValidations(), OperazioneScheda.MODIFICA);
	
						}
//						notEmpty = ids.getSituazioneAttuale().getStatoSospensioni() != null && ids.getSituazioneAttuale().getStatoSospensioni().size() > 0;
//						if( notEmpty && ids.getSituazioneAttuale().getStatoSospensioni().get(0) .isFromXml()){
						if( ids.getSituazioneAttualeXml().isPresentSospensioni()){
							EsitoValidazioneBean esitoSospensioni= validator.validaSospensioni(tutteLeSchede.getSchedeSospensione(),ids.getSospensioniPosizioneModifica(),cig,cui,progressivoSchedaCompleta);
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
							feedBackWriter.fillMassloaderFeedBack(feedback, esitoSospensioni.getListOfValidations(), OperazioneScheda.MODIFICA);
	
						}
//						notEmpty = ids.getSituazioneAttuale().getStatoSubAppalti() != null && ids.getSituazioneAttuale().getStatoSubAppalti().size() > 0;
//						if( notEmpty && ids.getSituazioneAttuale().getStatoSubAppalti().get(0) .isFromXml()){
						if( ids.getSituazioneAttualeXml().isPresentSubAppalti()){
							EsitoValidazioneBean esitoSubAppalti = validator.validaSubAppalti(tutteLeSchede.getSchedeSubAppalto(),ids.getSubappaltiPosizioneModifica(),cig,cui,progressivoSchedaCompleta);
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
							feedBackWriter.fillMassloaderFeedBack(feedback, esitoSubAppalti.getListOfValidations(), OperazioneScheda.MODIFICA);
	
						}
//						notEmpty = ids.getSituazioneAttuale().getStatoVarianti() != null && ids.getSituazioneAttuale().getStatoVarianti().size() > 0;
//						if( notEmpty && ids.getSituazioneAttuale().getStatoVarianti().get(0) .isFromXml()){
						if( ids.getSituazioneAttualeXml().isPresentVarianti()){
							EsitoValidazioneBean esitoVarianti = validator.validaVariante(tutteLeSchede.getSchedeVariante(),ids.getVariantiPosizioneModifica(), cig,cui,progressivoSchedaCompleta);
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
							feedBackWriter.fillMassloaderFeedBack(feedback, esitoVarianti.getListOfValidations(), OperazioneScheda.MODIFICA);
	
						}
						if(!esisteUnaSchedaNonValida){
							logger.debug("Procedo con il controllo di flusso");
							EsitoOperazioneControlloLogico esitoFlux = action.controllaCorrettezzaFlussoConProgressivo(ids);
							
							if(esitoFlux.isEsitoOperazione()){
								logger.debug("Procedo con la modifica..");
								try{
									/** Nota che o effettua l'inserimento correttamente oppure lancia un'eccezione..**/
									EsitoOperazioneInserimentoOModifica esitoInserimento = action.modificaScheda(ids, tutteLeSchede);
									logger.debug("Tentativo di modifica.. Riuscito");
									
//									con.commit();
									DbManager.staticCommit(con, logger, INVOKER, CONNECTIONID);
									
									feedBackWriter.fillMassloaderFeedBack(feedback, esitoInserimento.getListOfSuccess(), OperazioneScheda.MODIFICA);
								
								}catch (ModificaException modExc) {
									logger.debug("Tentativo di modifica.. Fallito");
//									con.rollback();
									
		                               // scarico gli errori sul feedback
	                                feedBackWriter.fillMassloaderFeedBack(feedback, action.getEsitiOperazioni(), modExc.getOperazione().getNomeOperazione());

									DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
									
									SchedaSpecificaValidationBean validation = 
										new SchedaSpecificaValidationBean(modExc.getMessaggio(),ValidationBean.VALBEAN_SEV_ERR,
												elemento, progressivoSchedaCompleta,0,
												modExc.getIdentificativo().getNomeScheda(),modExc.getCig(),modExc.getCui(),
												modExc.getIdSimog(),modExc.getIdLocale());
	
									feedBackWriter.fillMassloaderFeedBack(feedback, validation, modExc.getOperazione().getNomeOperazione());
									if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
									this.listOfCigNonValidi.add(ids.getCig());
								}
							}else{
								feedBackWriter.fillMassloaderFeedBack(feedback, esitoFlux.getListOfValidationsBeans(), OperazioneScheda.MODIFICA);
								if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
								this.listOfCigNonValidi.add(ids.getCig());
								// 
//								con.rollback();
								DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
							}
						}else{
							if(this.listOfCigNonValidi == null) this.listOfCigNonValidi = new ArrayList<String>();
							this.listOfCigNonValidi.add(ids.getCig());
							// 
//							con.rollback();
							DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
						}	
					}
				}
			}
//			logger.debug("\r\n\t\t\t####\r\nParziale Modifica FeedBack..:\r\n" +feedback.toString()+"\r\n\t\t\t####\r\n");
			}catch(Exception e){
				e.printStackTrace();
				DbManager.staticRollback(con, logger, INVOKER, CONNECTIONID);
			        String mesg = e.getMessage() + " - " + FeedBackWriterBase.getStack(e);
			        throw new Exception(mesg); 
	                //  try catch applicativo.. costruttore di validation bean un po' mongoloide 
//	                try{
//					String mesg = Messaggi.SIMOG_MASSLOADER_205 + " - " + e.getClass().getName() + " - " + FeedBackWriterBase.getStack(e);
//					ValidationBean validation = new ValidationBean(mesg,ValidationBean.VALBEAN_SEV_ERR,0);
//					List<ValidationBean> list = new ArrayList<ValidationBean>();
//					list.add(validation);
//					feedBackWriter.fillStandardFeedBack(feedback, list, "", "", "", "", 0, "", "");
//				}catch(Exception e1){}
				
			}finally{
				DbManager.staticSetAutoCommitTrue(con, logger, INVOKER, CONNECTIONID);
				logger.debug("Operazioni di Modifica Terminate");
			}
	}
	public TreeMap<String, Integer> getListOfWarning() {
		return listOfWarning;
	}

	public void setListOfWarning(TreeMap<String, Integer> listOfWarning) {
		this.listOfWarning = listOfWarning;
	}
	
}
