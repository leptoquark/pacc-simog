package it.avlp.simog.massload.validation;

import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.beans.CUPLOTTO;
import it.avlp.simog.beans.CodiciCup;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.Rubrica;
import it.avlp.simog.beans.RubricaResponsabili;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.beans.StatoScheda;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.accordi.SchedaAccordo;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.avanzamento.SchedaAvanzamento;
import it.avlp.simog.beans.collaudo.SchedaCollaudo;
import it.avlp.simog.beans.conclusione.SchedaConclusione;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.inizio.SchedaInizioLavori;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.beans.r129.SchedaR129;
import it.avlp.simog.beans.sospensioni.SchedaSospensione;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.beans.stipula.SchedaStipula;
import it.avlp.simog.beans.subappalti.SchedaSubAppalti;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.beans.variante.SchedaVariante;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.common.action.CupLottoAggAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletAccordo;
import it.avlp.simog.common.servlet.ParametriServletAvanzamento;
import it.avlp.simog.common.servlet.ParametriServletCollaudo;
import it.avlp.simog.common.servlet.ParametriServletR129;
import it.avlp.simog.common.servlet.ParametriServletRubrica;
import it.avlp.simog.common.servlet.ParametriServletSchedaB4;
import it.avlp.simog.common.servlet.ParametriServletSospensioni;
import it.avlp.simog.common.servlet.ParametriServletSubappalti;
import it.avlp.simog.common.servlet.ParametriServletVariante;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.massload.bean.IdCodiceFiscale;
import it.avlp.simog.massload.bean.IdsSchedaXML;
import it.avlp.simog.massload.esito.EsitoControlloFormaleIds;
import it.avlp.simog.massload.esito.EsitoOperazioneControlloEsistenzaAnagrafiche;
import it.avlp.simog.massload.esito.EsitoOperazioneControlloIds;
import it.avlp.simog.massload.esito.EsitoOperazioneValidateAnaPartecipanti;
import it.avlp.simog.massload.esito.EsitoOperazioneValidateAnaResposabili;
import it.avlp.simog.massload.esito.EsitoValidazioneBean;
import it.avlp.simog.massload.util.conversion.ConvertXMLtoBeanBusiness;
import it.avlp.simog.massload.util.conversion.SituazioneAttualeSchedeXml;
import it.avlp.simog.massload.xmlbeans.AdesioneType;
import it.avlp.simog.massload.xmlbeans.AggiudicatariType;
import it.avlp.simog.massload.xmlbeans.AggiudicatarioType;
import it.avlp.simog.massload.xmlbeans.AggiudicazioneType;
import it.avlp.simog.massload.xmlbeans.DatiCollaudoType;
import it.avlp.simog.massload.xmlbeans.DatiComuniType;
import it.avlp.simog.massload.xmlbeans.DatiInizioType;
import it.avlp.simog.massload.xmlbeans.IncaricatoType;
import it.avlp.simog.massload.xmlbeans.PosizioneType;
import it.avlp.simog.massload.xmlbeans.ResponsabileType;
import it.avlp.simog.massload.xmlbeans.ResponsabiliType;
import it.avlp.simog.massload.xmlbeans.SchedaCompletaType;
import it.avlp.simog.massload.xmlbeans.SchedaEsclusoType;
import it.avlp.simog.massload.xmlbeans.SchedaSottosogliaType;
import it.avlp.simog.massload.xmlbeans.SoggAggiudicatarioType;
import it.avlp.simog.rubricamanager.RubricaManager;
import it.avlp.simog.rubricamanager.RubricaResponsabiliManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.validatore.SchedaAValidator;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.factory.ValidatorFactory;
import it.mef.serviziCUP.ElaborazioniCUPClient;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

public class MassloaderValidator {

	
	private Connection con;
	private Logger logger;
	private SimogProperties confSimog;
   private CUPLOTTO cuplotto = null;
	
	/**
	 * Classe che si occupa della <strong>validazione</strong>:</p>
	 * - validazione base sui dati xml.</p>
	 * - validazione delle schede sugli oggetti convertiti.</p>
	 * 
	 * @param con
	 * @param logger
	 */
	public MassloaderValidator(Connection con, Logger logger) {
		this.con = con;
		this.logger = logger;
//      try {
         this.confSimog = SimogProperties.createInstance(SimogProperties.DEF_CONFIG, logger);
//      } catch (SimogException e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      }
	}
	/**
	 * Controlla che i CUI combacino con i CIG
	 * - caso cig = substring di cui OK
	 * - caso cui = blank o null OK
	 * - else KO
	 * 
	 * @param datiAggiudicazione
	 * @return
	 */
	public boolean controllaMatchCigToCui(String cig, SchedaCompletaType schedaCompleta){
		// valorizza il cig ricavato dal cui
		String cui = schedaCompleta.getCUI().trim();
		String cigFromCui = null;

		boolean schede = schedaCompleta.isSetDatiAccordi()
		      || schedaCompleta.isSetDatiAvanzamenti()
		      || schedaCompleta.isSetDatiCollaudo()
		      || schedaCompleta.isSetDatiConclusione()
		      || schedaCompleta.isSetDatiInizio()
		      || schedaCompleta.isSetDatiRitardi()
		      || schedaCompleta.isSetDatiSospensioni()
		      || schedaCompleta.isSetDatiStipula()
		      || schedaCompleta.isSetDatiSubappalti()
		      || schedaCompleta.isSetDatiVarianti();
		      
            
		// se il cui e' blank va bene, ma solo se non ci sono schede aggiuntive
		// PP 12.07.2014 ... e non c'è anche l'aggiudicazione!		
		if("".equals(cui)){
		   // ci sono schede aggiuntive ci deve essere l'aggiudicazione (primo inserimento)
		   // PP 10.09.2014 mancava controllo settaggio adesione!
		    if(schede == true && !(schedaCompleta.isSetAdesione() || schedaCompleta.isSetAggiudicazione() || schedaCompleta.isSetEscluso() || schedaCompleta.isSetSottosoglia()))
		       return false;

		    return true;
		}else{
			// se il cui e' maggiore di 11 cig+"-"
			if(cui.trim().length() > 11){
				cigFromCui = CIGBean.getRealCIG(cui.split("-")[0]);
				return cig.equals(cigFromCui) 
				      || cig.equals(cui.split("-")[0]); // PP 03/12/2012 aggiunto controllo su cui originale
			}
			
			return false;
		}
	}
	
	/**
	 * Controlla l'esistenza di una aggiudicazione associata al cui (si suppone che lo stato attuale del flusso
	 * compreda tutte schede confermate)
	 * 
	 * @param schedaCompleta
	 * @return
	 */
	public boolean controllaEsistenzaCui(SchedaCompletaType schedaCompleta){
		try{
			String CUI = schedaCompleta.getCUI();
			AggiudicazioniManager am = new AggiudicazioniManager(con , logger);
			// XX-X: watch out TODAY
			AggiudicazioneBean aggiudicazione = am.getAggiudicazioneByProgAndCui(CUI, false);
			if(aggiudicazione != null && aggiudicazione.getIdAggiudicazione() > 0){
				logger.debug("La ricerca per CUI ai fini del controllo di esistenza ha dato esito: Positivo");
				return true;
			}else{
				logger.debug("La ricerca per CUI ai fini del controllo di esistenza ha dato esito: Negativo");
			}
			return false;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			return false;
		}
	}
	/**
	 * Controlla che esita il CIG sul DB
	 * 
	 * @param datiAggiudicazione
	 * @return
	 */
	public boolean controllaEsistenzaCig(DatiComuniType datiComuni){
		String fullCig = datiComuni.getCIG();
		try{
			GaraManager garaManager = new GaraManager( con, logger);
			// PP 9.07.2010 inserita data corrente come parametro di ricerca in data scadenza pagamenti così scarto i CIG
			// che non sono ancora scaduti
//			TableBean tb = garaManager.getGaraList(null, null, fullCig, null, new Hashtable(),  PageHelper.getCurrentDate(), null , null, null,0,1, true, null,null, null,null,null,null,null,null);
			TableBean tb = garaManager.getGaraList(null, null, fullCig, null, new Hashtable(),  null, null , null, null,0,1, true, null,null, null,null,null,null,null,null);

			//introdotto controllo di non nullita' piu dimensione vettore > 0
			if(tb.getRowsCount() == 0           // nessuna riga
					|| (tb.getRowsCount()>0     // lotto cancellato
							&& (!tb.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO,0).equals("")
								|| !tb.getNulledField(LOTTO.DATA_INIB_PAGAMENTO,0).equals("")))
					|| (tb.getRowsCount()>0     // lotto non perfezionato
							&& tb.getNulledField(LOTTO.DATA_PUBBLICAZIONE,0).equals(""))		
				){ 
				return false;
			}
			return true;
		}catch(SQLException sqle){
			sqle.printStackTrace();
			return false;
		}
	}
	/**
	 * Controlla che il cf rup sia valido ovvero che esista sul db oppure sia presente nelle anagrafiche
	 * 
	 * @param datiAggiudicazione
	 * @return
	 */
	public boolean controllaValiditaCfRup(String cf_rup, ResponsabiliType rt ){
		
		try{
			SchedaAValidator val = new SchedaAValidator(this.con, this.logger);
			boolean isvalid = val.validaCodiceFiscale(cf_rup);
			
			if(isvalid){
				
				boolean found = false;
				
				if(rt != null && rt.getResponsabileArray() != null){
					ResponsabileType[] arrayOfResponsabili = rt.getResponsabileArray();
					
					// cerco il codice fiscale prima sulle anagrafiche nel caso in cui debba inserirlo
					for(int i = 0; i < arrayOfResponsabili.length; i++){
						ResponsabileType responsabileCorrente = arrayOfResponsabili[i];
						if(responsabileCorrente.getCODICEFISCALERESPONSABILE().equals(cf_rup)) found = true;
					}
				}

				// se non l'ho trovato devo andare a cercare sul db
				if(!found){	
					RubricaResponsabiliManager rrm = new RubricaResponsabiliManager(con,logger);
					return !rrm.checkCF(cf_rup);
				}else{
					return true;
				}
			}
			return false;
		}catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
	}

	/**
	 * Controlla che per ogni aggiudicatario / posizione sia presente l'anagrafica, sul db o sul xml
	 * - Le schede  che hanno aggiudicatari/posizioni sono solo aggiudicazione e inizio lavori
	 * - creo un'oggetto di tutte le anagrafiche che hanno corrispondenza nelle schede in modo
	 * 		da non inserire anagrafiche che non ci interessano.
	 * TODO: cambiare il tipo delle anagrafiche in ingresso visto che faccio la coversione a monte..
	 * 
	 * @param idScheda
	 * @param at
	 * @return
	 * @throws Exception
	 */
	public EsitoOperazioneControlloEsistenzaAnagrafiche controllaEsistenzaAnagrafichePerPosizioni(IdsSchedaXML idScheda, AggiudicatariType at) throws Exception{
		
		// controllo esistenza anagrafica(sul db o sul xml) per ogni posizione
		EsitoOperazioneControlloEsistenzaAnagrafiche esito = new EsitoOperazioneControlloEsistenzaAnagrafiche();
		
		List<AggiudicatarioType> listOfAggiudicatario = new ArrayList<AggiudicatarioType>();
		if(at != null){
			listOfAggiudicatario = Arrays.asList(at.getAggiudicatarioArray());
		}
		
		if(idScheda.getSituazioneAttualeXml().isPresentAggiudicazione()){
			AggiudicazioneType aggiudicazione = idScheda.getScheda().getSchedaCompletaArray(0).getAggiudicazione();
			if(aggiudicazione.getAggiudicatariArray() != null){
				List<SoggAggiudicatarioType> listOfSoggAggiudicatari = Arrays.asList(aggiudicazione.getAggiudicatariArray());
				int i = 0;
				for(SoggAggiudicatarioType sogg : listOfSoggAggiudicatari){
					RubricaManager rm = new RubricaManager(con, logger);
					// se non e' presente sul xml
					if(!isPresent(new IdCodiceFiscale(sogg.getCODICEFISCALEAGGIUDICATARIO(), sogg.getCODICESTATO()),listOfAggiudicatario)){
						
						// prova sul db
						if(rm.checkCF(sogg.getCODICEFISCALEAGGIUDICATARIO(), sogg.getCODICESTATO())){
							// NON e' presente neanche sul db..
							esito.setEsitoOperazione(false);
							String avviso = Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_PARTECIPANTE).replace("$2",sogg.getCODICEFISCALEAGGIUDICATARIO());
							SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
									i, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.AGGIUDICAZIONE, idScheda.getCig(), idScheda.getCui());
							esito.addValidation(validation);
							i++;
						}
					}
				}
			}
			
		}
		if(idScheda.getSituazioneAttualeXml().isPresentAdesione()){
			AdesioneType adesione = idScheda.getScheda().getSchedaCompletaArray(0).getAdesione();
			if(adesione.getAggiudicatariArray() != null){
				List<SoggAggiudicatarioType> listOfSoggAggiudicatari = Arrays.asList(adesione.getAggiudicatariArray());
				int i = 0;
				for(SoggAggiudicatarioType sogg : listOfSoggAggiudicatari){
					RubricaManager rm = new RubricaManager(con, logger);
					// se non e' presente sul xml
					if(!isPresent(new IdCodiceFiscale(sogg.getCODICEFISCALEAGGIUDICATARIO(), sogg.getCODICESTATO()),listOfAggiudicatario)){
						
						// prova sul db
						if(rm.checkCF(sogg.getCODICEFISCALEAGGIUDICATARIO(), sogg.getCODICESTATO())){
							// NON e' presente neanche sul db..
							esito.setEsitoOperazione(false);
							String avviso = Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_PARTECIPANTE).replace("$2",sogg.getCODICEFISCALEAGGIUDICATARIO());
							SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
									i, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.ADESIONE, idScheda.getCig(), idScheda.getCui());
							esito.addValidation(validation);
							i++;
						}
					}
				}
			}
			
		}
		if(idScheda.getSituazioneAttualeXml().isPresentSottosoglia()){
			SchedaSottosogliaType aggiudicazione = idScheda.getScheda().getSchedaCompletaArray(0).getSottosoglia();
			if(aggiudicazione.getAggiudicatariArray() != null){
				List<SoggAggiudicatarioType> listOfSoggAggiudicatari = Arrays.asList(aggiudicazione.getAggiudicatariArray());
				int i = 0;
				for(SoggAggiudicatarioType sogg : listOfSoggAggiudicatari){
					RubricaManager rm = new RubricaManager(con, logger);
					// se non e' presente sul xml
					if(!isPresent(new IdCodiceFiscale(sogg.getCODICEFISCALEAGGIUDICATARIO(), sogg.getCODICESTATO()),listOfAggiudicatario)){
						
						// prova sul db
						if(rm.checkCF(sogg.getCODICEFISCALEAGGIUDICATARIO(), sogg.getCODICESTATO())){
							// NON e' presente neanche sul db..
							esito.setEsitoOperazione(false);
							String avviso = Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_PARTECIPANTE).replace("$2",sogg.getCODICEFISCALEAGGIUDICATARIO());
							SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
									i, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.SOTTOSOGLIA, idScheda.getCig(), idScheda.getCui());
							esito.addValidation(validation);
							i++;
						}
					}
				}
			}
			
		}
		if(idScheda.getSituazioneAttualeXml().isPresentEscluso()){
			SchedaEsclusoType aggiudicazione = idScheda.getScheda().getSchedaCompletaArray(0).getEscluso();
			if(aggiudicazione.getAggiudicatariArray() != null){
				List<SoggAggiudicatarioType> listOfSoggAggiudicatari = Arrays.asList(aggiudicazione.getAggiudicatariArray());
				int i = 0;
				for(SoggAggiudicatarioType sogg : listOfSoggAggiudicatari){
					RubricaManager rm = new RubricaManager(con, logger);
					// se non e' presente sul xml
					if(!isPresent(new IdCodiceFiscale(sogg.getCODICEFISCALEAGGIUDICATARIO(), sogg.getCODICESTATO()),listOfAggiudicatario)){
						
						// prova sul db
						if(rm.checkCF(sogg.getCODICEFISCALEAGGIUDICATARIO(), sogg.getCODICESTATO())){
							// NON e' presente neanche sul db..
							esito.setEsitoOperazione(false);
							String avviso = Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_PARTECIPANTE).replace("$2",sogg.getCODICEFISCALEAGGIUDICATARIO());
							SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
									i, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.ESCLUSO, idScheda.getCig(), idScheda.getCui());
							esito.addValidation(validation);
							i++;
						}
					}
				}
			}
			
		}
		if(idScheda.getSituazioneAttualeXml().isPresentInizioLavori()){
			DatiInizioType datiInzio = idScheda.getScheda().getSchedaCompletaArray(0).getDatiInizio();
			if(datiInzio.getPosizioniArray() != null){
				List<PosizioneType> listOfPosizioni = Arrays.asList(datiInzio.getPosizioniArray());
				for(PosizioneType pos : listOfPosizioni){
					int i = 0;
					RubricaManager rm = new RubricaManager(con, logger);
					if(!isPresent(new IdCodiceFiscale(pos.getCODICEFISCALEAGGIUDICATARIO(), pos.getCODICESTATO()), listOfAggiudicatario)){
						// prova sul db
						if(rm.checkCF(pos.getCODICEFISCALEAGGIUDICATARIO(), pos.getCODICESTATO())){
							// NON e' presente neanche sul db..
							esito.setEsitoOperazione(false);
							String avviso = Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_PARTECIPANTE).replace("$2",pos.getCODICEFISCALEAGGIUDICATARIO());
							SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
									i, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.FASE_INIZIALE, idScheda.getCig(), idScheda.getCui());
							esito.addValidation(validation);
							i++;
						}
					}
					
				}
			}
		}

		return esito;
	}
	/**
	 * Dice se la chiave codice fiscale id stato ha una corrispondenza nella lista delle anagrafiche
	 * aggiudicatari.
	 * 
	 * @param idCf
	 * @param listOfAggiudicatario
	 * @return
	 */
	private boolean isPresent(IdCodiceFiscale idCf, List<AggiudicatarioType> listOfAggiudicatario){

		if(listOfAggiudicatario != null && listOfAggiudicatario.size() > 0){
			for(AggiudicatarioType agg : listOfAggiudicatario){
				if(idCf.equals(agg.getCODICEFISCALEAGGIUDICATARIO(), agg.getCODICESTATO())) return true;
			}
		}return false;

	}

	/**
	 *  Controlla che per ogni incaricato / resposabile sia presente l'anagrafica, sul db o sul xml
	 *  - le schede che hanno incaricati sono aggiudicazione e collaudo e inizio
	 *   - creo un'oggetto di tutte le anagrafiche che hanno corrispondenza nelle schede in modo
	 * 		da non inserire anagrafiche che non ci interessano.
	 * TODO: convertire il tipo in ingresso per le anagrafiche visto che faccio la conversione a monte.
	 * 
	 * @param idScheda
	 * @param rt
	 * @return
	 * @throws Exception
	 */
	public EsitoOperazioneControlloEsistenzaAnagrafiche 
	            controllaEsistenzaAnagrafichePerResponsabili(IdsSchedaXML idScheda, 
	                  ResponsabiliType rt,
	                  AggiudicatariType at) throws Exception{
		
		EsitoOperazioneControlloEsistenzaAnagrafiche esito = new EsitoOperazioneControlloEsistenzaAnagrafiche();
		
		List<ResponsabileType> listOfResponsabili = new ArrayList<ResponsabileType>();
		if(rt != null){
			listOfResponsabili = Arrays.asList(rt.getResponsabileArray());
		}
		
      List<AggiudicatarioType> listOfAggiudicatario = new ArrayList<AggiudicatarioType>();
        if(at != null){
            listOfAggiudicatario = Arrays.asList(at.getAggiudicatarioArray());
        }

		if(idScheda.getSituazioneAttualeXml().isPresentAggiudicazione()){
			AggiudicazioneType aggiudicazione = idScheda.getScheda().getSchedaCompletaArray(0).getAggiudicazione();
			if(aggiudicazione.getIncaricatiArray() != null){
				List<IncaricatoType> listOfIncaricati = Arrays.asList(aggiudicazione.getIncaricatiArray());
				int i = 0;
                RubricaResponsabiliManager rrm = new RubricaResponsabiliManager(con,this.logger);
                RubricaManager rm = new RubricaManager(con, logger);

                for(IncaricatoType inc : listOfIncaricati){

				   if(PSBD.SEZIONE_PA.equals(inc.getSEZIONE().toString())
				         && inc.getPERSONAGIURIDICA() != null
				         && Costanti.FLAG_VALORE_SI.equals(inc.getPERSONAGIURIDICA().toString())){
				      // progettisti persone giuridiche
                      if(!isPresent(new IdCodiceFiscale(inc.getCODICEFISCALERESPONSABILE(), inc.getCODICESTATO()),listOfAggiudicatario)){
   	                  // prova sul db
   	                  if(rm.checkCF(inc.getCODICEFISCALERESPONSABILE(), inc.getCODICESTATO())){
      	                     // NON e' presente neanche sul db..
      	                     esito.setEsitoOperazione(false);
      	                     String avviso = Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_PROGETTISTA).replace("$2",inc.getCODICEFISCALERESPONSABILE());
      	                     SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
      	                                    i, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.AGGIUDICAZIONE, idScheda.getCig(), idScheda.getCui());
      	                     esito.addValidation(validation);
      	                     i++;
   	                     }
                      }
                   }				      
				   else{ // responsabili e progettisti persone fisiche
      				   if(!isPresent(inc.getCODICEFISCALERESPONSABILE(), listOfResponsabili)){
      						if(rrm.checkCF(inc.getCODICEFISCALERESPONSABILE())){
      							esito.setEsitoOperazione(false);
      							String avviso = Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE).replace("$2",inc.getCODICEFISCALERESPONSABILE());
      							esito.addValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR, 
      									i, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.AGGIUDICAZIONE, idScheda.getCig(), idScheda.getCui()));
      							i++;
      						}
      					}
	                }
				}
			}
		}
		
		if(idScheda.getSituazioneAttualeXml().isPresentAdesione()){
			AdesioneType aggiudicazione = idScheda.getScheda().getSchedaCompletaArray(0).getAdesione();
			if(aggiudicazione.getIncaricatiArray() != null){
				List<IncaricatoType> listOfIncaricati = Arrays.asList(aggiudicazione.getIncaricatiArray());
				int i = 0;
				for(IncaricatoType inc : listOfIncaricati){
					if(!isPresent(inc.getCODICEFISCALERESPONSABILE(), listOfResponsabili)){
						RubricaResponsabiliManager rrm = new RubricaResponsabiliManager(con,this.logger);
						if(rrm.checkCF(inc.getCODICEFISCALERESPONSABILE())){
							esito.setEsitoOperazione(false);
							String avviso = Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE).replace("$2",inc.getCODICEFISCALERESPONSABILE());
							esito.addValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR, 
									i, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.ADESIONE, idScheda.getCig(), idScheda.getCui()));
							i++;
						}
					}
				}
			}
		}
		
		if(idScheda.getSituazioneAttualeXml().isPresentSottosoglia()){
			SchedaSottosogliaType aggiudicazione = idScheda.getScheda().getSchedaCompletaArray(0).getSottosoglia();
			if(aggiudicazione.getIncaricatiArray() != null){
				List<IncaricatoType> listOfIncaricati = Arrays.asList(aggiudicazione.getIncaricatiArray());
				int i = 0;
				for(IncaricatoType inc : listOfIncaricati){
					if(!isPresent(inc.getCODICEFISCALERESPONSABILE(), listOfResponsabili)){
						RubricaResponsabiliManager rrm = new RubricaResponsabiliManager(con,this.logger);
						if(rrm.checkCF(inc.getCODICEFISCALERESPONSABILE())){
							esito.setEsitoOperazione(false);
							String avviso = Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE).replace("$2",inc.getCODICEFISCALERESPONSABILE());
							esito.addValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR, 
									i, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.SOTTOSOGLIA, idScheda.getCig(), idScheda.getCui()));
							i++;
						}
					}
				}
			}
		}
		
		if(idScheda.getSituazioneAttualeXml().isPresentEscluso()){
			SchedaEsclusoType aggiudicazione = idScheda.getScheda().getSchedaCompletaArray(0).getEscluso();
			if(aggiudicazione.getIncaricatiArray() != null){
				List<IncaricatoType> listOfIncaricati = Arrays.asList(aggiudicazione.getIncaricatiArray());
				int i = 0;
				for(IncaricatoType inc : listOfIncaricati){
					if(!isPresent(inc.getCODICEFISCALERESPONSABILE(), listOfResponsabili)){
						RubricaResponsabiliManager rrm = new RubricaResponsabiliManager(con,this.logger);
						if(rrm.checkCF(inc.getCODICEFISCALERESPONSABILE())){
							esito.setEsitoOperazione(false);
							String avviso = Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE).replace("$2",inc.getCODICEFISCALERESPONSABILE());
							esito.addValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR, 
									i, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.ESCLUSO, idScheda.getCig(), idScheda.getCui()));
							i++;
						}
					}
				}
			}
		}
		if(idScheda.getSituazioneAttualeXml().isPresentInizioLavori()){
			DatiInizioType datiInzio = idScheda.getScheda().getSchedaCompletaArray(0).getDatiInizio();
			if(datiInzio.getIncaricatiArray() != null){
				List<IncaricatoType> listOfIncaricati = Arrays.asList(datiInzio.getIncaricatiArray());
				int i = 0;
				for(IncaricatoType inc : listOfIncaricati){
					if(!isPresent(inc.getCODICEFISCALERESPONSABILE(), listOfResponsabili)){
						RubricaResponsabiliManager rrm = new RubricaResponsabiliManager(con,this.logger);
						if(rrm.checkCF(inc.getCODICEFISCALERESPONSABILE())){
							esito.setEsitoOperazione(false);
							String avviso = Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE).replace("$2",inc.getCODICEFISCALERESPONSABILE());
							esito.addValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR, 
									i, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.FASE_INIZIALE, idScheda.getCig(), idScheda.getCui()));
							i++;
						}
					}
				}
			}
		}

		
		if(idScheda.getSituazioneAttualeXml().isPresentCollaudo()){
			DatiCollaudoType datiCollaudo = idScheda.getScheda().getSchedaCompletaArray(0).getDatiCollaudo();
			if(datiCollaudo.getIncaricatiArray() != null){
				List<IncaricatoType> listOfIncaricati = Arrays.asList(datiCollaudo.getIncaricatiArray());
				int i = 0;
				for(IncaricatoType inc : listOfIncaricati){
					if(!isPresent(inc.getCODICEFISCALERESPONSABILE(), listOfResponsabili)){
						RubricaResponsabiliManager rrm = new RubricaResponsabiliManager(con,this.logger);
						if(rrm.checkCF(inc.getCODICEFISCALERESPONSABILE())){
							esito.setEsitoOperazione(false);
							String avviso = Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE).replace("$2",inc.getCODICEFISCALERESPONSABILE());
							esito.addValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR, 
									i, idScheda.getCardinalitaSchedaCompleta(),0, IdentificativoSchede.COLLAUDO, idScheda.getCig(), idScheda.getCui()));
							i++;
						}
					}
				}
			}
		}

		return esito;
	}
	/**
	 * Dice se e' presente l'anagrafica associata al responsabile
	 * 
	 * @param cf
	 * @param listOfResponsabili
	 * @return
	 */
	private boolean isPresent(String cf, List<ResponsabileType> listOfResponsabili){

		if(listOfResponsabili != null && listOfResponsabili.size() > 0){
			for(ResponsabileType res : listOfResponsabili){
				if(cf.equals(res.getCODICEFISCALERESPONSABILE())) return true;
			}
		}return false;

	}
	/**
	 * Metodo centralizzato per la costruzione di un'oggetto esito per la validazione,
	 * recuperando i dati contenuti nel validatore
	 * 
	 * @param esitoValidazione
	 * @param validatore
	 * @param nomeScheda
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @param isValid
	 * @param idLocale
	 * @param idSimog
	 * @return
	 */
	private EsitoValidazioneBean costruisciEsito(EsitoValidazioneBean esitoValidazione, SimogValidator validatore, String nomeScheda, String cig, String cui, int progressivoSchedaCompleta, boolean isValid, String idLocale, String idSimog){
		
		if(esitoValidazione == null){
			esitoValidazione = new EsitoValidazioneBean(nomeScheda, cig, cui, progressivoSchedaCompleta);
			// isEsitoOperazione e' inizializzato a true quindi OK
		}
		// per mantenere la coerenza della tabella di verita'
		isValid = isValid && esitoValidazione.isEsitoOperazione();
		esitoValidazione.setEsitoOperazione(isValid);
		// gli id in caso ci siano
		esitoValidazione.id_locale = idLocale;
		esitoValidazione.id_simog = idSimog;
		
		esitoValidazione.setListOfValidationsByConversion(validatore.getEccezioni().getAll());
		return esitoValidazione;
	} 
	/**
	 * Metodo centralizzato per la costruzione di un'oggetto esito per la validazione,
	 * recuperando i dati contenuti nel validatore, e' in genere usato per l'esito delle
	 * validazioni di schede multiple che necessitano nel caso di "iterazione esterna" di sovrascrivere
	 * l'attributo elemento che identifica la posizione della scheda.
	 * 
	 * @param esitoValidazione
	 * @param validatore
	 * @param nomeScheda
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @param isValid
	 * @param idLocale
	 * @param idSimog
	 * @param overrideProgressivoSchedaMultipla
	 * @return
	 */
	private EsitoValidazioneBean costruisciEsito(EsitoValidazioneBean esitoValidazione, SimogValidator validatore, String nomeScheda, String cig, String cui, int progressivoSchedaCompleta, boolean isValid, String idLocale, String idSimog, int overrideProgressivoSchedaMultipla){
		
		if(esitoValidazione == null){
			esitoValidazione = new EsitoValidazioneBean(nomeScheda, cig, cui, progressivoSchedaCompleta);
			// isEsitoOperazione e' inizializzato a true quindi OK
		}
		// per mantenere la coerenza della tabella di verita'
		isValid = isValid && esitoValidazione.isEsitoOperazione();
		esitoValidazione.setEsitoOperazione(isValid);
		// gli id in caso ci siano
		esitoValidazione.id_locale = idLocale;
		esitoValidazione.id_simog = idSimog;
		
		esitoValidazione.setListOfValidationsByConversion(validatore.getEccezioni().getAll(), overrideProgressivoSchedaMultipla);
		return esitoValidazione;
	} 
	/**
	 * Il primo parametro viene passato dall'esterno in modo da poter invocare lo stesso metodo e aggiungere
	 * dati all'oggetto in modo progressivo.
	 * 
	 * @param esitoValidazione
	 * @param allval
	 * @param nomeScheda
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @param isValid
	 * @param idLocale
	 * @param idSimog
	 * @return
	 */
	private EsitoValidazioneBean costruisciEsito(EsitoValidazioneBean esitoValidazione, AllValidationBeans allval, String nomeScheda, String cig, String cui, int progressivoSchedaCompleta, boolean isValid, String idLocale, String idSimog){
		if(esitoValidazione == null){
			esitoValidazione = new EsitoValidazioneBean(nomeScheda, cig, cui, progressivoSchedaCompleta);
			// isEsitoOperazione e' inizializzato a true quindi OK
		}
		// per mantenere la coerenza della tabella di verita'
		isValid = isValid && esitoValidazione.isEsitoOperazione();
		esitoValidazione.setEsitoOperazione(isValid);
		
		//
		esitoValidazione.id_locale = idLocale;
		esitoValidazione.id_simog = idSimog;
		
		// aggiunge alla lista eventuali validation beans
		esitoValidazione.setListOfValidationsByConversion(allval.getAll());
		return esitoValidazione;
	}
	/**
	 * Ritorna null se validazione completata senza errori, una lista di validation
	 * nel caso contrario
	 * 
	 * @param schedaA
	 * @return
	 * @throws SimogException
	 */
	public EsitoValidazioneBean validaDatiComuni(Scheda_A schedaA, String cig, String cui, int progressivoSchedaCompleta) throws SimogException{ 
		// recupero identificativi
		String idLocale = schedaA.getInfoComuni().getIdLocale() != null ? schedaA.getInfoComuni().getIdLocale() : "";
		String idSimog = schedaA.getInfoComuni().getIdInfo() != 0 ? String.valueOf(schedaA.getInfoComuni().getIdInfo()) : "";
		
		SimogValidator validatore = ValidatorFactory.getValidator(IdentificativoSchede.TAB_INFO_COMUNI, con, logger);
		boolean esito = validatore.valida(schedaA, IdentificativoSchede.TAB_INFO_COMUNI);
		   
		EsitoValidazioneBean esitoValidazione = null;
		return this.costruisciEsito(esitoValidazione, validatore, IdentificativoSchede.DATI_COMUNI, cig, cui, progressivoSchedaCompleta, esito, idLocale, idSimog);
	}
	/**
	 * Validazione delle aggiudicazioni
	 * 
	 * Scheda_A contains/needs
	 * 
	 * Lotto,InfoComuniBean, AggiudicazioneBean, List<ResponsabileBean>, List<ResponsabileBean>, 
	 * List<AggiudicatarioBean>, List<RequisitiBean>, List<CondizioneAggBean>, List<TipoAppaltoAggBean>, 
	 * List<TipoAppaltoAggBean>, List<TipoFinanziamentoBean>
	 * 
	 * @param schedaA
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @return
	 * @throws SimogException
	 */
	public EsitoValidazioneBean validaAggiudicazione(Scheda_A schedaA, String cig, String cui, int progressivoSchedaCompleta) throws SimogException{
		
		String idLocale = schedaA.getAggiudicazione().getIdLocale() != null ? schedaA.getAggiudicazione().getIdLocale() : "";
		String idSimog = schedaA.getAggiudicazione().getIdInfo() != 0 ? String.valueOf(schedaA.getAggiudicazione().getIdInfo()) : "";
		
		SimogValidator validatore = ValidatorFactory.getValidator(IdentificativoSchede.TAB_AGGIUDICAZIONE, con, logger);
		boolean esito = validatore.valida(schedaA, IdentificativoSchede.TAB_AGGIUDICAZIONE);
		
      // postvalidazione codiciCUP
      if(SimogFlags.is3031_RFWEBGL02Active()
            && !SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())
            && SimogProperties.getInstance().isCUPAttivo()){
		   
		   validaCupDIPE(schedaA, validatore);
		   
		   // esito negativo se ci sono errori bloccanti
		   esito &= validatore.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
		}
      
		EsitoValidazioneBean esitoValidazione = null;
		return this.costruisciEsito(esitoValidazione, validatore, IdentificativoSchede.AGGIUDICAZIONE, cig, cui, progressivoSchedaCompleta, esito, idLocale, idSimog);
	}
	
	/**
	 * Validazione delle adesioni
	 * 
	 * Scheda_A contains/needs
	 * 
	 * Lotto,InfoComuniBean, AggiudicazioneBean, List<ResponsabileBean>, List<ResponsabileBean>, 
	 * List<AggiudicatarioBean>, List<RequisitiBean>, List<CondizioneAggBean>, List<TipoAppaltoAggBean>, 
	 * List<TipoAppaltoAggBean>, List<TipoFinanziamentoBean>
	 * 
	 * @param schedaA
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @return
	 * @throws SimogException
	 */
	public EsitoValidazioneBean validaAdesione(Scheda_A schedaA, String cig, String cui, int progressivoSchedaCompleta) throws SimogException{
		
		String idLocale = schedaA.getAggiudicazione().getIdLocale() != null ? schedaA.getAggiudicazione().getIdLocale() : "";
		String idSimog = schedaA.getAggiudicazione().getIdInfo() != 0 ? String.valueOf(schedaA.getAggiudicazione().getIdInfo()) : "";
		
		SimogValidator validatore = ValidatorFactory.getValidator(IdentificativoSchede.TAB_ADESIONE, con, logger);
		boolean esito = validatore.valida(schedaA, IdentificativoSchede.TAB_ADESIONE);
		EsitoValidazioneBean esitoValidazione = null;
		return this.costruisciEsito(esitoValidazione, validatore, IdentificativoSchede.ADESIONE, cig, cui, progressivoSchedaCompleta, esito, idLocale, idSimog);
	}
	/*
	 * Validazione delle schede sottosoglia
	 * 
	 * Scheda_A contains/needs
	 * 
	 * Lotto,InfoComuniBean, AggiudicazioneBean, List<ResponsabileBean>, List<ResponsabileBean>, 
	 * List<AggiudicatarioBean>, List<RequisitiBean>, List<CondizioneAggBean>, List<TipoAppaltoAggBean>, 
	 * List<TipoAppaltoAggBean>, List<TipoFinanziamentoBean>
	 * 
	 * @param schedaA
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @return
	 * @throws SimogException
	 */
	public EsitoValidazioneBean validaSottosoglia(Scheda_A schedaA, String cig, String cui, int progressivoSchedaCompleta) throws SimogException{
		
		String idLocale = schedaA.getAggiudicazione().getIdLocale() != null ? schedaA.getAggiudicazione().getIdLocale() : "";
		String idSimog = schedaA.getAggiudicazione().getIdInfo() != 0 ? String.valueOf(schedaA.getAggiudicazione().getIdInfo()) : "";
		
		SimogValidator validatore = ValidatorFactory.getValidator(IdentificativoSchede.TAB_SOTTOSOGLIA, con, logger);
		boolean esito = validatore.valida(schedaA, IdentificativoSchede.TAB_SOTTOSOGLIA);
		
      // postvalidazione codiciCUP
      if(SimogFlags.is3031_RFWEBGL02Active()
            && !SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())
            && SimogProperties.getInstance().isCUPAttivo()){
         
         validaCupDIPE(schedaA, validatore);
         
         // esito negativo se ci sono errori bloccanti
         esito &= validatore.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
      }

		EsitoValidazioneBean esitoValidazione = null;
		return this.costruisciEsito(esitoValidazione, validatore, IdentificativoSchede.SOTTOSOGLIA, cig, cui, progressivoSchedaCompleta, esito, idLocale, idSimog);
	}
	
	/*
	 * Validazione delle schede contratti esclusi
	 * 
	 * Scheda_A contains/needs
	 * 
	 * Lotto,InfoComuniBean, AggiudicazioneBean, List<ResponsabileBean>, List<ResponsabileBean>, 
	 * List<AggiudicatarioBean>, List<RequisitiBean>, List<CondizioneAggBean>, List<TipoAppaltoAggBean>, 
	 * List<TipoAppaltoAggBean>, List<TipoFinanziamentoBean>
	 * 
	 * @param schedaA
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @return
	 * @throws SimogException
	 */
	public EsitoValidazioneBean validaEscluso(Scheda_A schedaA, String cig, String cui, int progressivoSchedaCompleta) throws SimogException{
		
		String idLocale = schedaA.getAggiudicazione().getIdLocale() != null ? schedaA.getAggiudicazione().getIdLocale() : "";
		String idSimog = schedaA.getAggiudicazione().getIdInfo() != 0 ? String.valueOf(schedaA.getAggiudicazione().getIdInfo()) : "";
		
		SimogValidator validatore = ValidatorFactory.getValidator(IdentificativoSchede.TAB_ESCLUSI, con, logger);
		boolean esito = validatore.valida(schedaA, IdentificativoSchede.TAB_ESCLUSI);
		
      // postvalidazione codiciCUP
      if(SimogFlags.is3031_RFWEBGL02Active()
            && !SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())
            && SimogProperties.getInstance().isCUPAttivo()){

         validaCupDIPE(schedaA, validatore);
         
         // esito negativo se ci sono errori bloccanti
         esito &= validatore.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
      }

		EsitoValidazioneBean esitoValidazione = null;
		return this.costruisciEsito(esitoValidazione, validatore, IdentificativoSchede.TAB_ESCLUSI, cig, cui, progressivoSchedaCompleta, esito, idLocale, idSimog);
	}



	/**
	 * Validazione inizio lavori
	 * 
	 * SchedaInizioLavori contains / needs
	 * 
	 * InizioLavoriBean, InfoComuniBean infoComuni, AggiudicazioneBean aggiudicazione,
	 * List<ResponsabileBean>, List<PosizioneAggiudicatarioBean>, List<AggiudicatarioBean>
	 * 
	 * NOTA: la lista di aggiudicatariobean sono gli aggiudicatari delle aggiudicazioni
	 * 
	 * @param schedaInizio
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @return
	 * @throws SimogException
	 */
	public EsitoValidazioneBean validaInizioLavori(SchedaInizioLavori schedaInizio, String cig, String cui, int progressivoSchedaCompleta) throws SimogException{
		// pre validazione
		AllValidationBeans allval = null;
		EsitoValidazioneBean esitoValidazione = null;
		String idLocale = "";
		String idSimog = "";
		if(schedaInizio.getAggiudicazione() == null){
			if(allval == null) allval = new AllValidationBeans();
			String avviso = Messaggi.SIMOG_MASSLOADER_173.replace("$1", IdentificativoSchede.AGGIUDICAZIONE).replace("$2", "Validare").replace("$3", IdentificativoSchede.FASE_INIZIALE);
			allval.addValidationErr(avviso);
		}
		// interrompi prima la validazione..
		if(allval != null){			
			return this.costruisciEsito(esitoValidazione, allval, IdentificativoSchede.FASE_INIZIALE, cig, cui, progressivoSchedaCompleta, false, idLocale, idSimog);
		}
		idLocale = schedaInizio.getDatiInizio().getIdLocale() != null ? schedaInizio.getDatiInizio().getIdLocale() : "";
		idSimog = schedaInizio.getDatiInizio().getIdInizioLavori() != 0 ? String.valueOf(schedaInizio.getDatiInizio().getIdInizioLavori()) : "";
		
		SimogValidator validatore = ValidatorFactory.getValidator(IdentificativoSchede.TAB_INIZIO_LAVORI, con, logger);
		boolean esito = validatore.valida(schedaInizio, PSBD.SEZIONE_IN);
		return this.costruisciEsito(esitoValidazione, validatore, IdentificativoSchede.FASE_INIZIALE, cig, cui, progressivoSchedaCompleta, esito, idLocale, idSimog);
	}
	
	/**
	 * Validazione stipula
	 * 
	 * SchedaStipula contains / needs
	 * 
	 * InizioLavoriBean, InfoComuniBean infoComuni, AggiudicazioneBean aggiudicazione,
	 * List<ResponsabileBean>, List<PosizioneAggiudicatarioBean>, List<AggiudicatarioBean>
	 * 
	 * NOTA: la lista di aggiudicatariobean sono gli aggiudicatari delle aggiudicazioni
	 * 
	 * @param schedaInizio
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @return
	 * @throws SimogException
	 */
	public EsitoValidazioneBean validaStipula(SchedaStipula schedaInizio, String cig, String cui, int progressivoSchedaCompleta) throws SimogException{
		// pre validazione
		AllValidationBeans allval = null;
		EsitoValidazioneBean esitoValidazione = null;
		String idLocale = "";
		String idSimog = "";
		if(schedaInizio.getAggiudicazione() == null){
			if(allval == null) allval = new AllValidationBeans();
			String avviso = Messaggi.SIMOG_MASSLOADER_173.replace("$1", IdentificativoSchede.AGGIUDICAZIONE).replace("$2", "Validare").replace("$3", IdentificativoSchede.STIPULA);
			allval.addValidationErr(avviso);
		}
		// interrompi prima la validazione..
		if(allval != null){			
			return this.costruisciEsito(esitoValidazione, allval, IdentificativoSchede.STIPULA, cig, cui, progressivoSchedaCompleta, false, idLocale, idSimog);
		}
		idLocale = schedaInizio.getStipula().getIdLocale() != null ? schedaInizio.getStipula().getIdLocale() : "";
		idSimog = schedaInizio.getStipula().getIdStipula() != 0 ? String.valueOf(schedaInizio.getStipula().getIdStipula()) : "";
		
		SimogValidator validatore = ValidatorFactory.getValidator(IdentificativoSchede.TAB_STIPULA, con, logger);
		boolean esito = validatore.valida(schedaInizio, PSBD.SEZIONE_IN);
		return this.costruisciEsito(esitoValidazione, validatore, IdentificativoSchede.STIPULA, cig, cui, progressivoSchedaCompleta, esito, idLocale, idSimog);
	}
	
	/**
	 * Validazione degli avanzamenti
	 * 
	 * SchedaAvanzamento contains / needs
	 * 
	 * InfoComuniBean,AggiudicazioneBean aggiudicazione,List<AvanzamentoBean>
	 * 
	 * @param schedeAvanzamento
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @return
	 * @throws SimogException
	 */
	public EsitoValidazioneBean validaAvanzamenti(SchedaAvanzamento schedeAvanzamento,int[] posizioneNelFileXml, String cig, String cui, int progressivoSchedaCompleta) throws SimogException{
		// pre validazione
		AllValidationBeans allval = null;
		EsitoValidazioneBean esitoValidazione = null;
		String idLocale = "";
		String idSimog = "";
		// FIX-ME: vl - qui il progressivo non ci sara' patch it
		if(schedeAvanzamento.getAggiudicazione() == null){
			if(allval == null) allval = new AllValidationBeans();
			String avviso = Messaggi.SIMOG_MASSLOADER_173.replace("$1", IdentificativoSchede.AGGIUDICAZIONE).replace("$2", "Validare").replace("$3", IdentificativoSchede.STATO_AVANZAMENTO);
			for(int i = 0; i < posizioneNelFileXml.length;i++ ){
				allval.addValidationErrProgressivo(avviso, posizioneNelFileXml[i] + 1);
			}
		}


		// interrompi prima la validazione..
		if(allval != null){
			return this.costruisciEsito(esitoValidazione, allval, IdentificativoSchede.STATO_AVANZAMENTO, cig, cui, progressivoSchedaCompleta, false, idLocale, idSimog);
		}
		
		
		boolean esito = true;
		
		List<AvanzamentoBean> tuttiGliAvanzamenti = schedeAvanzamento.getAvanzamenti();
		// itero all'esterno per poter individuare gli id (simog e locale)
		int progressivoLocale = 0;
//		List<AvanzamentoBean> temp = null;
		for(AvanzamentoBean avanzamentoCorrente : tuttiGliAvanzamenti){
			 schedeAvanzamento.setAvanzamentoFE(avanzamentoCorrente);
			 SimogValidator validatore = ValidatorFactory.getValidator(ParametriServletAvanzamento.TAB_AVANZAMENTO, con, logger);
			 esito = validatore.valida(schedeAvanzamento, null);		
			 idLocale = avanzamentoCorrente.getIdLocale() != null ? avanzamentoCorrente.getIdLocale() : "";
			 idSimog = avanzamentoCorrente.getIdAvanzamento() != 0 ? String.valueOf(avanzamentoCorrente.getIdAvanzamento()) : "";
			 esitoValidazione = this.costruisciEsito(esitoValidazione, validatore, IdentificativoSchede.STATO_AVANZAMENTO, cig, cui, progressivoSchedaCompleta, esito, idLocale, idSimog, posizioneNelFileXml[progressivoLocale] + 1);
			 progressivoLocale++;
		}
		// ci rimetto tutti gli avanzamenti
		schedeAvanzamento.setAvanzamenti(tuttiGliAvanzamenti);
		return esitoValidazione;
	}

	/**
	 * Validazione dei conclusione
	 * 
	 * SchedaConclusione contains / needs
	 * 
	 * ConclusioneBean, AggiudicazioneBean, InfoComuniBean
	 * 
	 * @param schedaConclusione
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @return
	 * @throws SimogException
	 */
	public EsitoValidazioneBean validaConclusione(SchedaConclusione schedaConclusione, String cig, String cui, int progressivoSchedaCompleta) throws SimogException{ 
		// pre validazione
		AllValidationBeans allval = null;
		EsitoValidazioneBean esitoValidazione = null;
		String idLocale = "";
		String idSimog = "";
		if(schedaConclusione.getAggiudicazione() == null){
			if(allval == null) allval = new AllValidationBeans();
			String avviso = Messaggi.SIMOG_MASSLOADER_173.replace("$1", IdentificativoSchede.AGGIUDICAZIONE).replace("$2", "Validare").replace("$3", IdentificativoSchede.FINE_LAVORI);
			allval.addValidationErr(avviso);
		}
		// interrompi prima la validazione..
		if(allval != null){
			return this.costruisciEsito(esitoValidazione, allval, IdentificativoSchede.FINE_LAVORI, cig, cui, progressivoSchedaCompleta, false, idLocale, idSimog);
		}
		idLocale = schedaConclusione.getConclusione().getIdLocale() != null ? schedaConclusione.getConclusione().getIdLocale() : "";
		idSimog = schedaConclusione.getConclusione().getIdUltim() != 0 ? String.valueOf(schedaConclusione.getConclusione().getIdUltim()) : "";
		
		SimogValidator validatore = ValidatorFactory.getValidator(ParametriServletSchedaB4.TAB_FINE_LAVORI, con, logger);
		boolean esito = validatore.valida(schedaConclusione, null);
		return this.costruisciEsito(esitoValidazione, validatore, IdentificativoSchede.FINE_LAVORI, cig, cui, progressivoSchedaCompleta, esito, idLocale, idSimog);
	}	
	
	/**
	 * Validazione dei ritardi
	 * 
	 * SchedaR129 contains / needs
	 * 
	 * List<R129Bean>, AggiudicazioneBean, InfoComuniBean, InizioLavoriBean
	 * 
	 * @param schedeRitardi
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @return
	 * @throws SimogException
	 */
	public EsitoValidazioneBean validaRitardi(SchedaR129 schedeRitardi,int[] posizioneNelFileXml, String cig, String cui, int progressivoSchedaCompleta) throws SimogException{ 
		String idLocale = "";
		String idSimog = "";
		
		
		List<R129Bean> tuttiIRitardi = schedeRitardi.getR129s();
		boolean esito = true;
		EsitoValidazioneBean esitoValidazione = null;
		int progressivoLocale = 0;
		for(R129Bean ritardoCorrente : tuttiIRitardi){
			
			schedeRitardi.setRitardoFE(ritardoCorrente);			
			idLocale = ritardoCorrente.getIdLocale() != null ? ritardoCorrente.getIdLocale() : "";
			idSimog = ritardoCorrente.getIdRecord() != 0 && ritardoCorrente.getIdRecord() > 0 ? String.valueOf(ritardoCorrente.getIdRecord()) : "";
			
			SimogValidator validatore = ValidatorFactory.getValidator(ParametriServletR129.TAB_SCHEDA_R129, con, logger);
			esito = validatore.valida(schedeRitardi, null);
			esitoValidazione = this.costruisciEsito(esitoValidazione, validatore, IdentificativoSchede.IPOTESI_RECESSO, cig, cui, progressivoSchedaCompleta, esito, idLocale, idSimog, posizioneNelFileXml[progressivoLocale] + 1);
			progressivoLocale++;
		}
		schedeRitardi.setR129s(tuttiIRitardi);
		return esitoValidazione;
	}
	
	/**
	 * Validazione delle sospensioni
	 * 
	 * SchedaSospensione contains / needs
	 * 
	 * List<SospensioniBean>,AggiudicazioneBean,InfoComuniBean,InizioLavoriBean
	 * 
	 * @param schedeSospensioni
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @return
	 * @throws SimogException
	 */
	public EsitoValidazioneBean validaSospensioni(SchedaSospensione schedeSospensioni,int[] posizioneNelFileXml, String cig, String cui, int progressivoSchedaCompleta) throws SimogException{ 
		String idLocale = "";
		String idSimog = "";
		
		
		EsitoValidazioneBean esitoValidazione = null;
		boolean esito = true; 
		List<SospensioniBean> tutteLeSospensioni = schedeSospensioni.getSospensioni();
		int progressivoLocale = 0;
		for(SospensioniBean sospensioneCorrente : tutteLeSospensioni){
			
			schedeSospensioni.setSospensioneFE(sospensioneCorrente);
			idLocale = sospensioneCorrente.getIdLocale() != null ? sospensioneCorrente.getIdLocale() : "";
			idSimog = sospensioneCorrente.getIdSospensione() != 0 ? String.valueOf(sospensioneCorrente.getIdSospensione()) : "";
			
			SimogValidator validatore = ValidatorFactory.getValidator(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI, con, logger);
			esito = validatore.valida(schedeSospensioni, null);		
			esitoValidazione = this.costruisciEsito(esitoValidazione, validatore, IdentificativoSchede.SOSPENSIONE, cig, cui, progressivoSchedaCompleta, esito, idLocale, idSimog, posizioneNelFileXml[progressivoLocale] + 1);
			progressivoLocale++;
		}
		schedeSospensioni.setSospensioni(tutteLeSospensioni);
		return esitoValidazione;
	}
		
	/**
	 * Validazione dei subappalti
	 * 
	 * schedeSubAppalti contains / needs
	 * 
	 * List<SubappaltiBean>,AggiudicazioneBean,InfoComuniBean,InizioLavoriBean
	 * 
	 * @param schedeSubAppalti
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @return
	 * @throws SimogException
	 */
	public EsitoValidazioneBean validaSubAppalti(SchedaSubAppalti schedeSubAppalti,int[] posizioneNelFileXml, String cig, String cui, int progressivoSchedaCompleta) throws SimogException{ 
		
		// XXX: SPECIFICHE NON CORRETTE..(subappalti non necessita di iniziolavori) PER CUI INIZIO LAVORI VUOTO
		if(schedeSubAppalti.getInizioLavori() == null){ schedeSubAppalti.setInizioLavori(new InizioLavoriBean()); }
		
		
		EsitoValidazioneBean esitoValidazione = null;
		String idLocale = "";
		String idSimog = "";
		boolean esito = true; 
		List<SubappaltiBean> tuttiISubAppalti = schedeSubAppalti.getSubAppalti();
		int progressivoLocale = 0;
		for(SubappaltiBean subAppaltoCorrente : tuttiISubAppalti){
			
			schedeSubAppalti.setSubAppaltiFE(subAppaltoCorrente);
			idLocale = subAppaltoCorrente.getIdLocale() != null ? subAppaltoCorrente.getIdLocale()  : "" ;
			idSimog = subAppaltoCorrente.getIdRecord() != 0 && subAppaltoCorrente.getIdRecord() > 0 ? String.valueOf(subAppaltoCorrente.getIdRecord()) : "";
			
			SimogValidator validatore = ValidatorFactory.getValidator(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI, con, logger);
			esito = validatore.valida(schedeSubAppalti, null);	
			esitoValidazione = this.costruisciEsito(esitoValidazione, validatore, IdentificativoSchede.SUBAPPALTO, cig, cui, progressivoSchedaCompleta, esito, idLocale, idSimog,posizioneNelFileXml[progressivoLocale] + 1);
			progressivoLocale++;
		}
		schedeSubAppalti.setSubAppalti(tuttiISubAppalti);
		return esitoValidazione;
	}	
	
	/**
	 * Validazione degli accordi
	 * 
	 * schedeAccordi contains / needs
	 * 
	 * List &lt;AccordoBean&gt;,AggiudicazioneBean,InfoComuniBean,InizioLavoriBean
	 * 
	 * @param schedeAccordi
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @return
	 * @throws SimogException
	 */
	public EsitoValidazioneBean validaAccordi(SchedaAccordo schedeAccordi,int[] posizioneNelFileXml, String cig, String cui, int progressivoSchedaCompleta) throws SimogException{ 
		// prima delle validazioni "Ufficiali" a rischio Null Pointer devo controllare di avere tutti le schede richieste		
		AllValidationBeans allval = null;
		EsitoValidazioneBean esitoValidazione = null;
		String idLocale = "";
		String idSimog = "";
		// FIX-ME: vl - qui il progressivo non ci sara' patch it
// PP non serve verificare la scheda aggiudicazione, basta inizio lavori
//		if(schedeAccordi.getAggiudicazione() == null){
//			allval = new AllValidationBeans();
//			String avviso = Messaggi.SIMOG_MASSLOADER_173.replace("$1", IdentificativoSchede.AGGIUDICAZIONE).replace("$2", "Validare").replace("$3", IdentificativoSchede.ACCORDO_BONARIO);
//			//			allval.addValidationErr(avviso);
//			for(int i = 0; i < posizioneNelFileXml.length;i++ ){
//				allval.addValidationErrProgressivo(avviso, posizioneNelFileXml[i] + 1);
//			}
//		}	
		if(schedeAccordi.getInizioLavori() == null){
			if(allval == null) allval = new AllValidationBeans();
			String avviso = Messaggi.SIMOG_MASSLOADER_173.replace("$1", IdentificativoSchede.FASE_INIZIALE).replace("$2", "Validare").replace("$3", IdentificativoSchede.ACCORDO_BONARIO);
//			allval.addValidationErr(avviso);
			for(int i = 0; i < posizioneNelFileXml.length;i++ ){
				allval.addValidationErrProgressivo(avviso, posizioneNelFileXml[i] + 1);
			}
		}
		// interrompi prima la validazione..
		if(allval != null){
			return this.costruisciEsito(esitoValidazione, allval, IdentificativoSchede.ACCORDO_BONARIO, cig, cui, progressivoSchedaCompleta, false, idLocale, idSimog);
		}
		
		boolean esito = true; 
		int progressivoLocale = 0;
		List<AccordoBean> tuttiGliAccordi = schedeAccordi.getAccordi();
		
		for(AccordoBean accordoCorrente : tuttiGliAccordi){
			
			idLocale = accordoCorrente.getIdLocale() != null ? accordoCorrente.getIdLocale() : "";
			idSimog = accordoCorrente.getIdAccordo() > 0 ? String.valueOf(accordoCorrente.getIdAccordo()) : "";
			schedeAccordi.setAccordoFE(accordoCorrente);
			
			SimogValidator validatore = ValidatorFactory.getValidator(ParametriServletAccordo.TAB_SCHEDA_ACCORDO, con, logger);
			esito = validatore.valida(schedeAccordi, null);
			esitoValidazione = this.costruisciEsito(esitoValidazione,validatore, IdentificativoSchede.ACCORDO_BONARIO, cig, cui, progressivoSchedaCompleta, esito, idLocale, idSimog, posizioneNelFileXml[progressivoLocale] + 1);
			progressivoLocale++;
		}
		schedeAccordi.setAccordi(tuttiGliAccordi);
		return esitoValidazione;
	}

	/**
	 * Validazione del collaudo
	 * 
	 * schedaCollaudo contains / needs
	 * 
	 * InfoComuniBean,AggiudicazioneBean,ConclusioneBean,List&lt;AccordoBean&gt;,CollaudoBean,List<ResponsabileBean>,
	 * 
	 * @param schedaCollaudo
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @return
	 * @throws SimogException
	 */
	public EsitoValidazioneBean validaCollaudo(SchedaCollaudo schedaCollaudo, String cig, String cui, int progressivoSchedaCompleta) throws SimogException{ 
		// prima delle validazioni "Ufficiali" a rischio Null Pointer devo controllare di avere tutti le schede richieste		
		AllValidationBeans allval = null;
		EsitoValidazioneBean esitoValidazione = null;
		String idLocale = "";
		String idSimog = "";
		if(schedaCollaudo.getConclusione() == null){
			if(allval == null) allval = new AllValidationBeans();
			String avviso = Messaggi.SIMOG_MASSLOADER_173.replace("$1", IdentificativoSchede.FINE_LAVORI).replace("$2", "Validare").replace("$3", IdentificativoSchede.COLLAUDO);
			allval.addValidationErr(avviso);
		}
		// interrompi prima la validazione..
		if(allval != null){
			return this.costruisciEsito(esitoValidazione, allval, IdentificativoSchede.COLLAUDO, cig, cui, progressivoSchedaCompleta, false, idLocale, idSimog);
		}
		idLocale = schedaCollaudo.getCollaudo().getIdLocale() != null ? schedaCollaudo.getCollaudo().getIdLocale() : "";
		idSimog = schedaCollaudo.getCollaudo().getIdCollaudo() != 0 ? String.valueOf(schedaCollaudo.getCollaudo().getIdCollaudo()) : "";
		
		SimogValidator validatore = ValidatorFactory.getValidator(ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO, con, logger);
		boolean esito = validatore.valida(schedaCollaudo, PSBD.SEZIONE_CO);
		return this.costruisciEsito(esitoValidazione,validatore, IdentificativoSchede.COLLAUDO, cig, cui, progressivoSchedaCompleta, esito, idLocale, idSimog);
	}
	

	/**
	 * Validazione delle varianti
	 * 
	 * schedeVarianti contains / needs
	 * 
	 * List<VarianteBean>,AggiudicazioneBean,InfoComuniBean,InizioLavoriBean
	 * 
	 * @param schedeVarianti
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @return
	 * @throws SimogException
	 */
	public EsitoValidazioneBean validaVariante(SchedaVariante schedeVarianti,int[] posizioneNelFileXml, String cig, String cui, int progressivoSchedaCompleta) throws SimogException{ 
		String idLocale = "";
		String idSimog = "";
		
		boolean esito = true; 
		EsitoValidazioneBean esitoValidazione = null;
		int progressivoLocale = 0;
		List<VarianteBean> tutteLeVarianti = schedeVarianti.getVarianti();
		
		for(VarianteBean varianteCorrente: tutteLeVarianti){
			
			schedeVarianti.setVarianteFE(varianteCorrente);
			idLocale = varianteCorrente.getIdLocale() != null ? varianteCorrente.getIdLocale() : "";
			idSimog = varianteCorrente.getIdVariante() != 0 ? String.valueOf(varianteCorrente.getIdVariante()) : "";
			
			SimogValidator validatore = ValidatorFactory.getValidator(ParametriServletVariante.TAB_SCHEDA_VARIANTE, con, logger);
			esito = validatore.valida(schedeVarianti, null);			
			esitoValidazione = this.costruisciEsito(esitoValidazione, validatore, IdentificativoSchede.VARIANTE, cig, cui, progressivoSchedaCompleta, esito, idLocale, idSimog, posizioneNelFileXml[progressivoLocale] + 1);
			progressivoLocale++;
		}
		
		schedeVarianti.setVarianti(tutteLeVarianti);
		return esitoValidazione;
	}
	
	/**
	 * metodo che si occupa del controllo della validita degli id simog per tutte le schede, ritorna un oggetto
	 * che permette di risalire alla scheda in errore..
	 * 
	 * @param situazioneAttualeDb
	 * @param situazioneAttualeXml
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @return
	 * @throws Exception
	 */
	public EsitoOperazioneControlloIds controllaMatchIdXmlWithIdDbSimog(SituazioneSchedeAttuale situazioneAttualeDb, SituazioneAttualeSchedeXml situazioneAttualeXml, String cig, String cui, int progressivoSchedaCompleta) throws Exception{
		
		EsitoOperazioneControlloIds esito = new EsitoOperazioneControlloIds(); 

		// se esiste l'entita xml che rappresenta la scheda
		if(situazioneAttualeXml.isPresentDatiComuni()){		
			// se e' valorizzato l'id simog
			if(situazioneAttualeXml.isPresentDatiComuniIdSimog()){
				// se esiste sul db controlla
				if( situazioneAttualeDb.getStatoDatiComuni().isEsistente()){
					boolean isDatiComuniOk = this.controllaIdsSimog(situazioneAttualeXml.getDatiComuniIdSimog(),  
							situazioneAttualeDb.getStatoDatiComuni());
					if(!isDatiComuniOk){
						esito.setEsitoOperazione(false);
						String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getDatiComuniIdSimog()+"]");
						esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
								0, progressivoSchedaCompleta,0, IdentificativoSchede.DATI_COMUNI, cig, cui));
					}
				// se non esiste sul db errore ?
				}else{
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getDatiComuniIdSimog()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.DATI_COMUNI, cig, cui));
				}
			}
			
		}
		if(situazioneAttualeXml.isPresentAggiudicazione()){

			if(situazioneAttualeXml.isPresentAggiudicazioneIdSimog()){
				if(situazioneAttualeDb.getStatoAggiudicazione().isEsistente()){
					boolean isAggiudicazioneOk = this.controllaIdsSimog(situazioneAttualeXml.getAggiudicazioneIdSimog(), 
							situazioneAttualeDb.getStatoAggiudicazione());
					if(!isAggiudicazioneOk){
						esito.setEsitoOperazione(false);
						String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getAggiudicazioneIdSimog()+"]");
						esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
								0, progressivoSchedaCompleta,0, IdentificativoSchede.AGGIUDICAZIONE, cig, cui));
					}
				}else{
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getAggiudicazioneIdSimog()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.AGGIUDICAZIONE, cig, cui));
				}
			}
		}
		
		if(situazioneAttualeXml.isPresentAdesione()){

			if(situazioneAttualeXml.isPresentAdesioneIdSimog()){
				if(situazioneAttualeDb.getStatoAdesione().isEsistente()){
					boolean isAdesioneOk = this.controllaIdsSimog(situazioneAttualeXml.getAdesioneIdSimog(), 
							situazioneAttualeDb.getStatoAdesione());
					if(!isAdesioneOk){
						esito.setEsitoOperazione(false);
						String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getAdesioneIdSimog()+"]");
						esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
								0, progressivoSchedaCompleta,0, IdentificativoSchede.ADESIONE, cig, cui));
					}
				}else{
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getAdesioneIdSimog()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.ADESIONE,cig, cui));
				}
			}
		}
		
		if(situazioneAttualeXml.isPresentSottosoglia()){

			if(situazioneAttualeXml.isPresentSottosogliaIdSimog()){
				if(situazioneAttualeDb.getStatoSottosoglia().isEsistente()){
					boolean isSottosogliaOk = this.controllaIdsSimog(situazioneAttualeXml.getSottosogliaIdSimog(), 
							situazioneAttualeDb.getStatoSottosoglia());
					if(!isSottosogliaOk){
						esito.setEsitoOperazione(false);
						String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getSottosogliaIdSimog()+"]");
						esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
								0, progressivoSchedaCompleta,0, IdentificativoSchede.SOTTOSOGLIA, cig, cui));
					}
				}else{
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getSottosogliaIdSimog()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.SOTTOSOGLIA, cig, cui));
				}
			}
		}
		
		if(situazioneAttualeXml.isPresentEscluso()){

			if(situazioneAttualeXml.isPresentEsclusoIdSimog()){
				if(situazioneAttualeDb.getStatoEscluso().isEsistente()){
					boolean isAggiudicazioneOk = this.controllaIdsSimog(situazioneAttualeXml.getEsclusoIdSimog(), 
							situazioneAttualeDb.getStatoEscluso());
					if(!isAggiudicazioneOk){
						esito.setEsitoOperazione(false);
						String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getEsclusoIdSimog()+"]");
						esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
								0, progressivoSchedaCompleta,0, IdentificativoSchede.ESCLUSO, cig, cui));
					}
				}else{
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getEsclusoIdSimog()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.ESCLUSO, cig, cui));
				}
			}
		}
		if(situazioneAttualeXml.isPresentInizioLavori()){	

			if(situazioneAttualeXml.isPresentInizioLavoriIdSimog()){
				if(situazioneAttualeDb.getStatoInizioLavori().isEsistente()){
					boolean isInizioOk = this.controllaIdsSimog(situazioneAttualeXml.getInizioLavoriIdSimog(), 
							situazioneAttualeDb.getStatoInizioLavori());
					if(!isInizioOk){
						esito.setEsitoOperazione(false);
						String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getInizioLavoriIdSimog()+"]");
						esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
								0, progressivoSchedaCompleta,0, IdentificativoSchede.FASE_INIZIALE, cig, cui));
					}
				}else{
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getInizioLavoriIdSimog()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.FASE_INIZIALE, cig, cui));
				}
			}
		}
		
		if(situazioneAttualeXml.isPresentStipula()){	

			if(situazioneAttualeXml.isPresentStipulaIdSimog()){
				if(situazioneAttualeDb.getStatoStipula().isEsistente()){
					boolean isInizioOk = this.controllaIdsSimog(situazioneAttualeXml.getStipulaIdSimog(), 
							situazioneAttualeDb.getStatoStipula());
					if(!isInizioOk){
						esito.setEsitoOperazione(false);
						String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getStipulaIdSimog()+"]");
						esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
								0, progressivoSchedaCompleta,0, IdentificativoSchede.STIPULA, cig, cui));
					}
				}else{
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getStipulaIdSimog()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.STIPULA, cig, cui));
				}
			}
		}
		if(situazioneAttualeXml.isPresentAvanzamenti()){
			
			int lenght = situazioneAttualeXml.getAvanzamentiIdSimog().length;	
			int i = 0;
			while(i < lenght){
				boolean temp = this.controllaIdsSimog(situazioneAttualeXml.getAvanzamentiIdSimog(), 
						i,
						situazioneAttualeDb.getStatoAvanzamento(),
						situazioneAttualeXml.getIsPresentAvanzamentiIdSimog()
						);
				if(!temp){
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getAvanzamentiIdSimog()[i]+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,i, IdentificativoSchede.STATO_AVANZAMENTO, cig, cui));
				}
				i++;
			}
		}
		if(situazioneAttualeXml.isPresentConclusione()){

			if(situazioneAttualeXml.isPresentConclusioneIdSimog()){
				if(situazioneAttualeDb.getStatoConclusione().isEsistente()){
					boolean isConclusioneOk = this.controllaIdsSimog(situazioneAttualeXml.getConclusioneIdSimog(),
							situazioneAttualeDb.getStatoConclusione());
					if(!isConclusioneOk){
						esito.setEsitoOperazione(false);
						String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getConclusioneIdSimog()+"]");
						esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
								0, progressivoSchedaCompleta,0, IdentificativoSchede.FINE_LAVORI, cig, cui));
					}
				}else{
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getConclusioneIdSimog()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.FINE_LAVORI, cig, cui));
				}
			}
		}
		if(situazioneAttualeXml.isPresentCollaudo()){	

			if(situazioneAttualeXml.isPresentCollaudoIdSimog()){
				if(situazioneAttualeDb.getStatoCollaudo().isEsistente()){
					boolean isCollaudoOk = this.controllaIdsSimog(situazioneAttualeXml.getCollaudoIdSimog(),
							situazioneAttualeDb.getStatoCollaudo());
					if(!isCollaudoOk){
						esito.setEsitoOperazione(false);
						String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getCollaudoIdSimog()+"]");
						esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
								0, progressivoSchedaCompleta,0, IdentificativoSchede.COLLAUDO, cig, cui));
					}
				}else{
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getCollaudoIdSimog()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.COLLAUDO, cig, cui));
				}
			}
		}
		if(situazioneAttualeXml.isPresentAccordi()){
			
			int lenght = situazioneAttualeXml.getAccordiIdSimog().length;
			int i = 0;
			while(i < lenght){
				boolean temp = this.controllaIdsSimog(situazioneAttualeXml.getAccordiIdSimog(), 
						i,
						situazioneAttualeDb.getStatoAccordi(),
						situazioneAttualeXml.getIsPresentAccordiIdSimog()
						);
				if(!temp){
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getAccordiIdSimog()[i]+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,i, IdentificativoSchede.ACCORDO_BONARIO, cig, cui));
				}
				i++;
			}
		}
		if(situazioneAttualeXml.isPresentRitardo()){
			int lenght = situazioneAttualeXml.getRitardoIdSimog().length;
			int i = 0;
			while(i < lenght){
				boolean temp = this.controllaIdsSimog(situazioneAttualeXml.getRitardoIdSimog(), 
						i,
						situazioneAttualeDb.getStatoRitardo(),
						situazioneAttualeXml.getIsPresentRitardoIdSimog()
						);
				 if(!temp){
					 esito.setEsitoOperazione(false);
					 String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getRitardoIdSimog()[i]+"]");
					 esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
								0, progressivoSchedaCompleta,i, IdentificativoSchede.IPOTESI_RECESSO, cig, cui));
				 }
				i++;
			}	
		}
		if(situazioneAttualeXml.isPresentSospensioni()){
			int lenght = situazioneAttualeXml.getSospensioniIdSimog().length;
			int i = 0;
			while(i < lenght){
				boolean temp = this.controllaIdsSimog(situazioneAttualeXml.getSospensioniIdSimog(),
						i,
						situazioneAttualeDb.getStatoSospensioni(),
						situazioneAttualeXml.getIsPresentSospensioniIdSimog()
						);
				 if(!temp){
					 esito.setEsitoOperazione(false);
					 String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getSospensioniIdSimog()[i]+"]");
					 esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
								0, progressivoSchedaCompleta,i, IdentificativoSchede.SOSPENSIONE, cig, cui));
				 }
				i++;
			}	
		}
		if(situazioneAttualeXml.isPresentSubAppalti()){
			int lenght = situazioneAttualeXml.getSubAppaltiIdSimog().length;
			int i = 0;
			while(i < lenght){
				boolean temp = this.controllaIdsSimog(situazioneAttualeXml.getSubAppaltiIdSimog(), 
						i,
						situazioneAttualeDb.getStatoSubAppalti(),
						situazioneAttualeXml.getIsPresentSubAppaltiIdSimog()
						);
				 if(!temp){
					 esito.setEsitoOperazione(false);
					 String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getSubAppaltiIdSimog()[i]+"]");
					 esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
								0, progressivoSchedaCompleta,i, IdentificativoSchede.SUBAPPALTO, cig, cui));
				 }
				i++;	
			}
		}
		if(situazioneAttualeXml.isPresentVarianti()){
			int lenght = situazioneAttualeXml.getVariantiIdSimog().length;
			int i = 0;
			while(i < lenght){
				boolean temp = this.controllaIdsSimog(situazioneAttualeXml.getVariantiIdSimog(),
						i,
						situazioneAttualeDb.getStatoVarianti(),
						situazioneAttualeXml.getIsPresentVariantiIdSimog()
						);
				if(!temp){
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_SIMOG + "["+situazioneAttualeXml.getVariantiIdSimog()[i]+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,i, IdentificativoSchede.VARIANTE, cig, cui));
				}
				i++;
			}
		}
		return esito;
	}

	/**
	 * Se esiste la scheda sia sul db che sul file xml controlla la coerenza tra id locale fornito nel file xml con l'id locale
	 * ritornato tramite la ricerca effettuata o tramite cig o tramite cui (a seconda)
	 * 
	 * NOTA: per evitare impatti, nel caso in cui il controllo si effettui per i soli dati comuni si suggerisce di passare
	 * stringa vuota.
	 * 
	 * @param situazioneAttualeDb
	 * @param situazioneAttualeXml
	 * @param cig
	 * @param cui
	 * @param progressivoSchedaCompleta
	 * @return
	 * @throws Exception
	 */
	public EsitoOperazioneControlloIds controllaMatchIdXmlWithIdDbLocale(SituazioneSchedeAttuale situazioneAttualeDb, SituazioneAttualeSchedeXml situazioneAttualeXml, String cig, String cui, int progressivoSchedaCompleta) throws Exception{
		
		EsitoOperazioneControlloIds esito = new EsitoOperazioneControlloIds(); 

		if(situazioneAttualeXml.isPresentDatiComuni() && situazioneAttualeDb.getStatoDatiComuni().isEsistente()){		

			if(situazioneAttualeXml.isPresentDatiComuniIdLocale()){
				boolean isDatiComuniOk = this.controllaIdsLocale(situazioneAttualeXml.getDatiComuniIdLocale(),  
						situazioneAttualeDb.getStatoDatiComuni());
				if(!isDatiComuniOk){
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_LOCALE + "["+situazioneAttualeXml.getDatiComuniIdLocale()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.DATI_COMUNI, cig, cui));
				}
									
			}
		}
		if(situazioneAttualeXml.isPresentAggiudicazione() && situazioneAttualeDb.getStatoAggiudicazione().isEsistente()){

			if(situazioneAttualeXml.isPresentAggiudicazioneIdLocale()){
				boolean isAggiudicazioneOk = this.controllaIdsLocale(situazioneAttualeXml.getAggiudicazioneIdLocale(), 
						situazioneAttualeDb.getStatoAggiudicazione());
				if(!isAggiudicazioneOk){
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_LOCALE + "["+situazioneAttualeXml.getAggiudicazioneIdLocale()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.AGGIUDICAZIONE, cig, cui));
				}
			}
		}
		
		if(situazioneAttualeXml.isPresentAdesione() && situazioneAttualeDb.getStatoAdesione().isEsistente()){

			if(situazioneAttualeXml.isPresentAdesioneIdLocale()){
				boolean isAdesioneOk = this.controllaIdsLocale(situazioneAttualeXml.getAdesioneIdLocale(), 
						situazioneAttualeDb.getStatoAdesione());
				if(!isAdesioneOk){
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_LOCALE + "["+situazioneAttualeXml.getAdesioneIdLocale()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.ADESIONE, cig, cui));
				}
			}
		}
		
		if(situazioneAttualeXml.isPresentSottosoglia() && situazioneAttualeDb.getStatoSottosoglia().isEsistente()){

			if(situazioneAttualeXml.isPresentSottosogliaIdLocale()){
				boolean isSottosogliaOk = this.controllaIdsLocale(situazioneAttualeXml.getSottosogliaIdLocale(), 
						situazioneAttualeDb.getStatoSottosoglia());
				if(!isSottosogliaOk){
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_LOCALE + "["+situazioneAttualeXml.getSottosogliaIdLocale()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.SOTTOSOGLIA, cig, cui));
				}
			}
		}
		
		if(situazioneAttualeXml.isPresentEscluso() && situazioneAttualeDb.getStatoEscluso().isEsistente()){

			if(situazioneAttualeXml.isPresentEsclusoIdLocale()){
				boolean isEsclusoOk = this.controllaIdsLocale(situazioneAttualeXml.getEsclusoIdLocale(), 
						situazioneAttualeDb.getStatoEscluso());
				if(!isEsclusoOk){
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_LOCALE + "["+situazioneAttualeXml.getEsclusoIdLocale()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.ESCLUSO, cig, cui));
				}
			}
		}
		if(situazioneAttualeXml.isPresentInizioLavori() && situazioneAttualeDb.getStatoInizioLavori().isEsistente()){	

			if(situazioneAttualeXml.isPresentInizioLavoriIdLocale()){
				boolean isInizioOk = this.controllaIdsLocale(situazioneAttualeXml.getInizioLavoriIdLocale(), 
						situazioneAttualeDb.getStatoInizioLavori());
				if(!isInizioOk){
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_LOCALE + "["+situazioneAttualeXml.getInizioLavoriIdLocale()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.FASE_INIZIALE, cig, cui));
				}
			}
		}
		
		if(situazioneAttualeXml.isPresentStipula() && situazioneAttualeDb.getStatoStipula().isEsistente()){	

			if(situazioneAttualeXml.isPresentStipulaIdLocale()){
				boolean isInizioOk = this.controllaIdsLocale(situazioneAttualeXml.getStipulaIdLocale(), 
						situazioneAttualeDb.getStatoStipula());
				if(!isInizioOk){
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_LOCALE + "["+situazioneAttualeXml.getStipulaIdLocale()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.STIPULA, cig, cui));
				}
			}
		}

		if(situazioneAttualeXml.isPresentConclusione() && situazioneAttualeDb.getStatoConclusione().isEsistente()){

			if(situazioneAttualeXml.isPresentConclusioneIdLocale()){
				boolean isConclusioneOk = this.controllaIdsLocale(situazioneAttualeXml.getConclusioneIdLocale(),
						situazioneAttualeDb.getStatoConclusione());
				if(!isConclusioneOk){
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_LOCALE + "["+situazioneAttualeXml.getConclusioneIdLocale()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.FINE_LAVORI, cig, cui));
				}
			}
		}
		if(situazioneAttualeXml.isPresentCollaudo() && situazioneAttualeDb.getStatoCollaudo().isEsistente()){	

			if(situazioneAttualeXml.isPresentCollaudoIdLocale()){
				boolean isCollaudoOk = this.controllaIdsLocale(situazioneAttualeXml.getCollaudoIdLocale(),
						situazioneAttualeDb.getStatoCollaudo());
				if(!isCollaudoOk){
					esito.setEsitoOperazione(false);
					String avviso = Messaggi.SIMOG_MASSLOADER_195.replace("$1", Costanti.ID_LOCALE + "["+situazioneAttualeXml.getCollaudoIdLocale()+"]");
					esito.addIdsValidation(new SchedaSpecificaValidationBean(avviso, ValidationBean.VALBEAN_SEV_ERR,
							0, progressivoSchedaCompleta,0, IdentificativoSchede.COLLAUDO, cig, cui));
				}
			}
		}

		return esito;
	}
	/**
	 * Metodo da usare con le sole schede "singole", effettua il controllo che l'idRecord contenuto
	 * nell'oggetto statoscheda sia lo stesso dell'idsimog passato.
	 * Il controllo viene effettuato solamente se lo stato scheda e' esistente (ovverosia sia stata trovata la scheda sul db)
	 * - Ritorna true se lo stato scheda corrente e' esistente e se sono uguali idRecord e idSimog
	 * - Ritorna false se lo stato scheda corrente NON esiste oppure se non sono uguali i due id
	 * 
	 * @param idSimog
	 * @param statoSchedaCorrente
	 * @return
	 */
	private boolean controllaIdsSimog(String idSimog, StatoScheda statoSchedaCorrente){
		if(statoSchedaCorrente.isEsistente()){
			return String.valueOf(statoSchedaCorrente.getIdRecord()).equals(idSimog);
		}return false;
	}
	/**
	 * A differnenza del metodo controllaIdsSimog(..), controlla il match tra i due unicamente
	 * se:
	 * - esiste la scheda sul db
	 * - se l'id_locale sul file xml e' presente (questo perche potrebbero voler usare per qualunque motivo l'id simog in seguito)
	 * - XXX: VL - PATCH - se l'id locale � nullo sulla base dati e valido sul xml devo aggiornare l'id.
	 * 				questo metodo viene invocato solamente per le schede singole quindi non ho bisogno di un flag che mi
	 * 				indichi il tipo (singola o multipla) della scheda che si sta controllando
	 * 
	 * Altrimenti ritorna true
	 * 
	 * @param idLocale
	 * @param statoSchedaCorrente
	 * @return
	 */
	private boolean controllaIdsLocale(String idLocale, StatoScheda statoSchedaCorrente){
		// eseguo il confronto effettivo solamente se sono valide le seguenti condizioni
		if(statoSchedaCorrente.isEsistente() && idLocale != null){
			// se la scheda e' presente sul db ma il campo id_locale e' nullo c'e' stato un'errore
			if(statoSchedaCorrente.getIdLocale() == null){ 
				//XXX: VL - PATCH - deve ritornare true qui in modo da aggiornare il record con l'id_locale inviato
				return true;
			}
			return String.valueOf(statoSchedaCorrente.getIdLocale()).equals(idLocale);
		}return true;
	}	
	/**
	 * Controllo per le schede multiple se trova nella collezione di schede trovate nel db
	 * una scheda che contiene lo stesso idSimog ritorna true, false altrimenti.
	 * 
	 * - se l'idSimog e' valorizzato e trovo l'id cercato ritorno true
	 * - se l'id simog NON e' valorizzato ritorno true (NON ho bisogno del confronto)
	 * - se l'id simog e' valorizzato e non trovo l'id cercato ritorno false
	 * - se l'id simog e' valorizzato e non sono risutate schede sul db ritorno false
	 * - se l'id simog NON e' valorizzato e non sono risutate schede sul db ritorno true
	 * 
	 * @param idsSimog
	 * @param i
	 * @param statiScheda
	 * @param isIdSimogs
	 * @return
	 */
	private boolean controllaIdsSimog(String[] idsSimog, int i, ArrayList<StatoScheda> statiScheda, boolean[] isIdSimogs){

		String idSimog = idsSimog[i];
		
		boolean isIdSimog = isIdSimogs[i];
		boolean esito = false;
		// se e' settato l'id-simog
		if(isIdSimog){
			if(statiScheda == null || statiScheda.size() == 0) return false;
			// se trovo l'id tra le schede caricate dal db l'id_simog e' valido.
			for(StatoScheda statoSchedaX : statiScheda){
				 if(statoSchedaX.isEsistente()){
					 if(idSimog.equals(String.valueOf(statoSchedaX.getIdRecord()))){
						 esito = true;
					 }
				}
			}return esito;

		// se non e' settato va bene
		}return true;
		
	}	
//	/**
//	@deprecated: perche non � possibile sapere (per le schede multiple) a che scheda faccia riferimento con id_locale 
//					se a una scheda che si vuole inserire oppure una scheda che si intende modificare.
//	 * @param idsLocale
//	 * @param i
//	 * @param statiScheda
//	 * @param isIdLocales
//	 * @return
//	 */
//	private boolean controllaIdsLocale(String[] idsLocale, int i, ArrayList<StatoScheda> statiScheda, boolean[] isIdLocales){
//
//		String idLocale = idsLocale[i];
//		
//		boolean isIdLocale = isIdLocales[i];
//		boolean esito = false;
//		// se e' settato l'id-simog
//		if(isIdLocale){
//			if(statiScheda == null || statiScheda.size() == 0) return true;
//			// se trovo l'id tra le schede caricate dal db l'id_simog e' valido.
//			for(StatoScheda statoSchedaX : statiScheda){
//				 if(statoSchedaX.isEsistente()){
//					 if(idLocale.equals(String.valueOf(statoSchedaX.getIdLocale()))){
//						 esito = true;
//					 }
//				}
//			}return esito;
//
//		// se non e' settato va bene
//		}return true;
//		
//	}	
	/**
	 * Effettua una doppia conversione, ed inseguito effettua la validazione tramite validatore 
	 * 
	 * (TODO: assurdo che il validatore non sia allineato per la validazione del bean dedicato all'anagrafica !)
	 * @param at
	 * @param converter
	 * @return
	 */
	public EsitoOperazioneValidateAnaPartecipanti validaAnagrafichePosizioni(AggiudicatariType at,ConvertXMLtoBeanBusiness converter){
		EsitoOperazioneValidateAnaPartecipanti esito = new EsitoOperazioneValidateAnaPartecipanti();
		
		// conversione delle anagrafiche, le liste devono comunque essere non nulle..
		List<SoggettoPartecipanteBean> listOfAnagrafichePartecipanti = new ArrayList<SoggettoPartecipanteBean>();
		if(at != null){
			listOfAnagrafichePartecipanti = converter.convertiAnagraficheAggiudicatari(at.getAggiudicatarioArray(), true);
			if(converter.containsDuplicate){
				esito.setWarnings(converter.duplicateWarning);
			}

		}
		// conversione
		List<Rubrica> aggiudicatari = new ArrayList<Rubrica>();
		for(SoggettoPartecipanteBean aggiudicatario : listOfAnagrafichePartecipanti){
			Rubrica soggpart = new Rubrica();
			soggpart.setCamera_commercio(aggiudicatario.getCameraCommercio());
			soggpart.setCap(aggiudicatario.getCap());
			soggpart.setCf_rappresentante(aggiudicatario.getCfRappresentante());
			soggpart.setCitta(aggiudicatario.getCitta());
			soggpart.setCivico(aggiudicatario.getCivico());
			soggpart.setCodice_fiscale(aggiudicatario.getCodiceFiscale());
			soggpart.setCognome(aggiudicatario.getCognome());
			soggpart.setDenominazione(aggiudicatario.getDenominazione());
			soggpart.setIndirizzo(aggiudicatario.getIndirizzo());
			soggpart.setNome(aggiudicatario.getNome());
			soggpart.setPartitaIva(aggiudicatario.getPartitaIva());
			soggpart.setProvincia(aggiudicatario.getProvincia());
			soggpart.setId_stato(aggiudicatario.getId_stato());
			soggpart.setFlagEsteri(aggiudicatario.getFlagEsteri());		
			aggiudicatari.add(soggpart);
		}
		
		// validazione..
		SimogValidator validator = null;
		boolean esitoValidazione = false;
		try{		
			validator = ValidatorFactory.getValidator(ParametriServletRubrica.TAB_RUBRICA, con, logger);		
			esitoValidazione = validator.valida(aggiudicatari, PSBD.TAB_RUBRICA_AFFIDATARIO );
		}catch (SimogException se) {
			// handle exception: sempre errore di factory.. ovverosia il tab passato non e' corretto o non piu valido
			se.printStackTrace();
		}
		esito.setEsitoOperazione(esitoValidazione);
//		if(!esitoValidazione){
			esito.setListOfValidation(validator.getEccezioni().getAll());
//		}else{
//			esito.setListOfValidAnaPartecipante(listOfAnagrafichePartecipanti);
//		}
		if(esitoValidazione){
			esito.setListOfValidAnaPartecipante(listOfAnagrafichePartecipanti);
		}
		return esito;
	}
	/**
	 * Effettua una doppia conversione, ed inseguito effettua la validazione tramite validatore 
	 * 
	 * (TODO: assurdo che il validatore non sia allineato per la validazione del bean dedicato all'anagrafica !)
	 * 
	 * @param rt
	 * @param converter
	 * @return
	 */
	public EsitoOperazioneValidateAnaResposabili validaAnagraficheIncaricati(ResponsabiliType rt, ConvertXMLtoBeanBusiness converter){
		
		EsitoOperazioneValidateAnaResposabili esito = new EsitoOperazioneValidateAnaResposabili();
		
		// conversione dal tipo xml a corrispondente simog
		List<SoggettoResponsabileBean> listOfAnagraficheResponsabili = new ArrayList<SoggettoResponsabileBean>();
		if(rt != null){
			listOfAnagraficheResponsabili = converter.convertiAnagraficheResponsabili(rt.getResponsabileArray(), true);
			if(converter.containsDuplicate){
				esito.setWarnings(converter.duplicateWarning);
			}
		}
		// conversione..
		List<RubricaResponsabili> responsabili = new ArrayList<RubricaResponsabili>();
		for( SoggettoResponsabileBean responsabile : listOfAnagraficheResponsabili){
			RubricaResponsabili soggresp = new RubricaResponsabili();
			soggresp.setCap(responsabile.getCap());
			soggresp.setCodice_fiscale_responsabile(responsabile.getCodiceFiscaleResponsabile());
			soggresp.setCognome(responsabile.getCognome());
			soggresp.setComuneIstat(responsabile.getComuneIstat());
			soggresp.setEmail(responsabile.getEmail());
			soggresp.setFax(responsabile.getFax());
			soggresp.setIndirizzo(responsabile.getIndirizzo());
			soggresp.setNome(responsabile.getNome());
			soggresp.setTelefono(responsabile.getTelefono());
			soggresp.setIsEstero(responsabile.getFlagSoggettoEstero());
			
			responsabili.add(soggresp);
		}
		
		
		// validazione..
		SimogValidator validator = null;
		boolean esitoValidazione = false;
		try{
			validator = ValidatorFactory.getValidator(ParametriServletRubrica.TAB_RUBRICA, con, logger);
			esitoValidazione = validator.valida(responsabili, PSBD.TAB_RUBRICA_RESPONSABILI);
		}catch (SimogException se) {
			// handle exception: sempre errore di factory.. ovverosia il tab passato non e' corretto o non piu valido
			se.printStackTrace();
		}
		esito.setEsitoOperazione(esitoValidazione);
//		if(!esitoValidazione){
			esito.setListOfValidation(validator.getEccezioni().getAll());
//		}else{
//			esito.setListOfValidAnaResponsabile(listOfAnagraficheResponsabili);
//		}
		
		if(esitoValidazione){
			esito.setListOfValidAnaResponsabile(listOfAnagraficheResponsabili);
		}
		return esito;
	}
	
	/**
	 * Metodo che si occupa di controllare la coerenza degli id_locale/id_simog ovverosia
	 * se una scheda contiene un id_simog oppure un id_locale tutte le schede devono contenere
	 * uno di questi id altrimenti errore
	 * 
	 * @param datiAggiudicazioneCorrente
	 * @return
	 */
	public EsitoOperazioneControlloIds controllaCoerenzaIds(SituazioneAttualeSchedeXml situazioneXmlCorrente, String cig, String cui){
		
		// il default per l'esito tutti i flag sono ad OK
		EsitoOperazioneControlloIds esitoIds = new EsitoOperazioneControlloIds();
		
		ArrayList<String> listOfNomiSchedeNonCoerenti = new ArrayList<String>();
		
		EsitoControlloFormaleIds esitoForm = situazioneXmlCorrente.situazioneIds();
		
		String messaggio = null;
		// se e' una lista di errori (schede senza id lista degli stessi messaggi per ogi scheda senza id)
		if(esitoForm.isErrorOnlyOnSomeSchedeVoid()){
			messaggio = Messaggi.SIMOG_MASSLOADER_200;
			listOfNomiSchedeNonCoerenti = esitoForm.getListOfNomiSchedeNonValideVoid();
			
			// se la lista non e' vuota c'e' un'errore.
			if(!listOfNomiSchedeNonCoerenti.isEmpty()){
				esitoIds.setEsitoOperazione(false);
				for(String nomeSchedaNonValida : listOfNomiSchedeNonCoerenti){
					
					try{
						
						this.fillEsitoNegativoSchedaIds(IdentificativoSchede.findIdentificativoByName(nomeSchedaNonValida), esitoIds, cig, cui, messaggio);
						
					}catch (Exception e) {
						
						e.printStackTrace();
						
					}
				}
			}
		}
		// se e' una lista di errori (schede CON id lista degli stessi messaggi per ogi scheda CON id, quando richiesto)
		if(esitoForm.isErrorOnlyOnSomeSchedeLoaded()){
			messaggio = Messaggi.SIMOG_MASSLOADER_201;
			listOfNomiSchedeNonCoerenti = esitoForm.getListOfNomiSchedeNonValideLoaded();
			
			// se la lista non e' vuota c'e' un'errore.
			if(!listOfNomiSchedeNonCoerenti.isEmpty()){
				esitoIds.setEsitoOperazione(false);
				for(String nomeSchedaNonValida : listOfNomiSchedeNonCoerenti){
					
					try{
						
						this.fillEsitoNegativoSchedaIds(IdentificativoSchede.findIdentificativoByName(nomeSchedaNonValida), esitoIds, cig, cui, messaggio);
						
					}catch (Exception e) {
						
						e.printStackTrace();
						
					}
				}
			}
		}
		// errore generico per tutte le schede quando sono presenti sia id_simog che id_locali, notare il primo argomento con stringa vuota.
		if(esitoForm.isErrorOverAllSchede()){
			esitoIds.setEsitoOperazione(false);
			esitoIds.addIdsValidation(SchedaSpecificaValidationBean.getErrorForIdsProblems("", esitoForm.getErrore(), cig, cui));
		}
			
		return esitoIds;
	}
	
	/**
	 * @param identificativo
	 * @param esitoIds
	 */
	private void fillEsitoNegativoSchedaIds(IdentificativoSchede identificativo, EsitoOperazioneControlloIds esitoIds, String cig, String cui, String messaggio){
		
		int switcher = identificativo.getIndiceScheda();

		
		
		switch (switcher) {
			case IdentificativoSchede.INDICE_DATI_COMUNI:
				esitoIds.setDatiComuniOk(false);
				esitoIds.addIdsValidation(SchedaSpecificaValidationBean.getErrorForIdsProblems(identificativo.getNomeScheda(), messaggio, cig, cui));
				break;
			case IdentificativoSchede.INDICE_AGGIUDICAZIONE:
				esitoIds.setAggiudicazioneOk(false);
				esitoIds.addIdsValidation(SchedaSpecificaValidationBean.getErrorForIdsProblems(identificativo.getNomeScheda(), messaggio, cig, cui));
				break;
			case IdentificativoSchede.INDICE_SOTTOSOGLIA:
				esitoIds.setAggiudicazioneOk(false);
				esitoIds.addIdsValidation(SchedaSpecificaValidationBean.getErrorForIdsProblems(identificativo.getNomeScheda(), messaggio, cig, cui));
				break;
			case IdentificativoSchede.INDICE_ESCLUSO:
				esitoIds.setAggiudicazioneOk(false);
				esitoIds.addIdsValidation(SchedaSpecificaValidationBean.getErrorForIdsProblems(identificativo.getNomeScheda(), messaggio, cig, cui));
				break;
			case IdentificativoSchede.INDICE_ACCORDO_BONARIO:
				esitoIds.setAccordiOk(false);
				esitoIds.addIdsValidation(SchedaSpecificaValidationBean.getErrorForIdsProblems(identificativo.getNomeScheda(), messaggio, cig, cui));
				break;
			case IdentificativoSchede.INDICE_STATO_AVANZAMENTO:
				esitoIds.setAvanzamentiOk(false);
				esitoIds.addIdsValidation(SchedaSpecificaValidationBean.getErrorForIdsProblems(identificativo.getNomeScheda(), messaggio, cig, cui));
				break;
			case IdentificativoSchede.INDICE_COLLAUDO:
				esitoIds.setCollaudoOk(false);
				esitoIds.addIdsValidation(SchedaSpecificaValidationBean.getErrorForIdsProblems(identificativo.getNomeScheda(), messaggio, cig, cui));
				break;
			case IdentificativoSchede.INDICE_FINE_LAVORI:
				esitoIds.setConclusioneOk(false);
				esitoIds.addIdsValidation(SchedaSpecificaValidationBean.getErrorForIdsProblems(identificativo.getNomeScheda(), messaggio, cig, cui));
				break;
			case IdentificativoSchede.INDICE_FASE_INIZIALE:
				esitoIds.setInizioOk(false);
				esitoIds.addIdsValidation(SchedaSpecificaValidationBean.getErrorForIdsProblems(identificativo.getNomeScheda(), messaggio, cig, cui));
				break;
            case IdentificativoSchede.INDICE_STIPULA:
               esitoIds.setStipulaOk(false);
               esitoIds.addIdsValidation(SchedaSpecificaValidationBean.getErrorForIdsProblems(identificativo.getNomeScheda(), messaggio, cig, cui));
               break;
			case IdentificativoSchede.INDICE_IPOTESI_RECESSO:
				esitoIds.setRitardiOk(false);
				esitoIds.addIdsValidation(SchedaSpecificaValidationBean.getErrorForIdsProblems(identificativo.getNomeScheda(), messaggio, cig, cui));
				break;
			case IdentificativoSchede.INDICE_SOSPENSIONE:
				esitoIds.setSospensioniOk(false);
				esitoIds.addIdsValidation(SchedaSpecificaValidationBean.getErrorForIdsProblems(identificativo.getNomeScheda(), messaggio, cig, cui));
				break;
			case IdentificativoSchede.INDICE_SUBAPPALTO:
				esitoIds.setSubappaltiOk(false);
				esitoIds.addIdsValidation(SchedaSpecificaValidationBean.getErrorForIdsProblems(identificativo.getNomeScheda(), messaggio, cig, cui));
				break;
			case IdentificativoSchede.INDICE_VARIANTE:
				esitoIds.setVariantiOk(false);
				esitoIds.addIdsValidation(SchedaSpecificaValidationBean.getErrorForIdsProblems(identificativo.getNomeScheda(), messaggio, cig, cui));
				break;
			case IdentificativoSchede.INDICE_ADESIONE: //Ticket ALM #1489
				esitoIds.setAdesioneOk(false);
				esitoIds.addIdsValidation(SchedaSpecificaValidationBean.getErrorForIdsProblems(identificativo.getNomeScheda(), messaggio, cig, cui));
				break; //Fine TICKET ALM #1489
			
		}
	}
	
   void validaCupDIPE(Scheda_A schedaA, SimogValidator validatore){
      
      if(schedaA.getElencoCup()==null) return;
      
      CupLottoAggAction claAction = new CupLottoAggAction(con, logger);
      
      // Per ogni CUP verifico la situazione
      ElaborazioniCUPClient cli = new ElaborazioniCUPClient(confSimog, logger);
      Lotto lt = new Lotto();
      lt.setElencoCup(schedaA.getElencoCup());
    //MAC 36301 3.04.8 cambiato da validaCupDIPE a validaCupDIPEAgg
      AllValidationBeans eccez = cli.validaCupDIPEAgg(lt, true);
      if(eccez != null)
         validatore.getEccezioni().add(eccez);
   
      // non posso confermare se ci sono codici non confermati esplicitamente
      eccez = claAction.validaCodiciCUPConf(lt);
      if(eccez != null)
         validatore.getEccezioni().add(eccez);
      // aggiorno i dati DIPE nei bean
      claAction.settingDatiDIPE(schedaA.getElencoCup());
   
      // valorizzo la struttura per restituire in response gli esiti DIPE
      if(schedaA.getElencoCup() != null && schedaA.getElencoCup().size() > 0){
         if(this.cuplotto == null)
            this.cuplotto = new CUPLOTTO();
         
         cuplotto.setCIG(schedaA.getInfoGara().getFullCIG());//schedaA.getElencoCup().get(0).getCig());
         CodiciCup[] codici = new CodiciCup[schedaA.getElencoCup().size()];
         int i = 0;
         
         for(CupLottoAggExt elem : schedaA.getElencoCup()){
               CodiciCup item = new CodiciCup();
               item.setCUP(elem.getCup());
               item.setID_RICHIESTA(String.valueOf(elem.getDatiDIPE().getID_RICHIESTA()));
               item.setDATI_DIPE(elem.getDatiDIPE().getESITO_RICHIESTA());
               item.setVALIDO(elem.getDatiDIPE().getVALIDO()==null ? "N":elem.getDatiDIPE().getVALIDO());
               item.setOK_UTENTE(elem.getOkUtente()== null ? "N" : elem.getOkUtente());
               codici[i++] = item;
         }
         cuplotto.setCODICICUP(codici);
      }
   }

   public CUPLOTTO getCuplotto() {
      return cuplotto;
   }
   public void setCuplotto(CUPLOTTO cuplotto) {
      this.cuplotto = cuplotto;
   }
}
