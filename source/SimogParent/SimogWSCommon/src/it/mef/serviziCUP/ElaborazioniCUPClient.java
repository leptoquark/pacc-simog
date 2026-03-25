package it.mef.serviziCUP;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlOptions;

import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.RichiestaCUP;
import it.avlp.simog.beans.cup.CupLottoAgg;
import it.avlp.simog.common.action.BaseSharedAction.AVCPassSemaforo;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.garamanager.lotto.RichiesteCUPManager;
import it.avlp.simog.util.Base64Coder;
import it.avlp.simog.util.SimogProperties;
import it.mef.serviziCUP.rest.CupRestClient;
import it.mef.serviziCUP.rest.RestCupConstants;
import it.mef.serviziCUP.rest.dto.CupDataDto;
import it.mef.serviziCUP.rest.dto.CupRequest;
import it.mef.serviziCUP.rest.dto.CupResponse;
import it.mef.serviziCUP.types.Richiesta_RichiestaRispostaSincrona_RichiestaDettaglioCUP_Type;
import it.mef.serviziCUP.types.Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type;
import noNamespace.ACQUISTOBENIDocument.ACQUISTOBENI;
import noNamespace.CONCESSIONECONTRIBUTINOUNITAPRODUTTIVEDocument.CONCESSIONECONTRIBUTINOUNITAPRODUTTIVE;
import noNamespace.CONCESSIONEINCENTIVIUNITAPRODUTTIVEDocument.CONCESSIONEINCENTIVIUNITAPRODUTTIVE;
import noNamespace.CUPCUMULATIVODocument.CUPCUMULATIVO;
import noNamespace.DETTAGLIOCUPDocument;
import noNamespace.LAVORIPUBBLICIDocument.LAVORIPUBBLICI;
import noNamespace.PARTECIPAZIONARIECONFERIMCAPITALEDocument.PARTECIPAZIONARIECONFERIMCAPITALE;
import noNamespace.REALIZZACQUISTOSERVIZIFORMAZIONEDocument.REALIZZACQUISTOSERVIZIFORMAZIONE;
import noNamespace.REALIZZACQUISTOSERVIZINOFORMAZIONERICERCADocument.REALIZZACQUISTOSERVIZINOFORMAZIONERICERCA;
import noNamespace.REALIZZACQUISTOSERVIZIRICERCADocument.REALIZZACQUISTOSERVIZIRICERCA;
import noNamespace.RICHIESTADETTAGLIOCUPDocument;
import noNamespace.RICHIESTADETTAGLIOCUPDocument.RICHIESTADETTAGLIOCUP;

public class ElaborazioniCUPClient {

	public enum DIPEEsiti {

		// il verde è ok si va avanti
		// il giallo è indisponibilità del servizio
		// il rosso è un problema dei WS e quindi vado avanti, senza dare per validato
		// il codice cup
		ELABORAZIONE_ESEGUITA("Elaborazione completata correttamente", AVCPassSemaforo.VERDE),
		RICHIESTA_NON_PRESENTE("Riferimento a id richiesta non presente", AVCPassSemaforo.GIALLO),
		ERRORE_NELLA_ELABORAZIONE_DEL_MESSAGGIO("Errore nella struttura della richiesta inviata",
				AVCPassSemaforo.GIALLO),
		ERRORE_APPLICATIVO("Errore interno ai WS DIPE", AVCPassSemaforo.GIALLO),
		RIFIUTO_APPLICATIVO("Rifiuto della richiesta inviata", AVCPassSemaforo.ROSSO),
		SERVIZIO_DISATTIVATO("* Codice CUP validato d'ufficio", AVCPassSemaforo.VERDE),
		INATTESA_VALIDAZIONE("* In attesa di validazione DIPE", AVCPassSemaforo.GIALLO);

		private String descrizione;
		private AVCPassSemaforo semaforo;

		public String descrizione() {
			return descrizione;
		}

		DIPEEsiti(String descrizione, AVCPassSemaforo sema) {
			this.descrizione = descrizione;
			this.setSemaforo(sema);
		}

		public static DIPEEsiti getEnumBycodice(String codInp) {
			DIPEEsiti[] lista = values();
			for (int i = 0; i < lista.length; i++) {
				if (lista[i].name().equals(codInp))
					return lista[i];
			}
			return null;
		}

		public AVCPassSemaforo getSemaforo() {
			return semaforo;
		}

		public void setSemaforo(AVCPassSemaforo semaforo) {
			this.semaforo = semaforo;
		}
	}

	private DIPEEsiti esito;

	protected Logger logger = null;
	public final String PREFISSO_RICHIESTA = "AVCP_";

	private SimogProperties config;

	// private Connection connection;

	public ElaborazioniCUPClient(SimogProperties config, Logger logger) {
		this.config = config;
		this.logger = logger;
		// this.connection = activeConnection;
		this.esito = DIPEEsiti.ELABORAZIONE_ESEGUITA;
	}

	/**
	 * Richiede al WS il dettaglio CUP
	 * 
	 * @param cup
	 * @param idRichiesta
	 * @return
	 * @throws Exception
	 */
	private String[] getDettaglioCUP(String cup, String idRichiesta) throws Exception {

		String retValArr[] = new String[] { null, null, null };
		String retVal = "";
		System.out
				.println("TB: ====================== INIZIO GETDETTAGLIOCUP(" + cup + ")============================");
		System.out.println("TB: ID RICHIESTA: " + idRichiesta + " CUP: " + cup);
		// verifico se l'url è definito altrimenti non devo fare chiamate e restituisco

		ElaborazioniCUP_ServiceLocator locator = new ElaborazioniCUP_ServiceLocator();

		ElaborazioniCUP_PortType proxy = locator.getElaborazioniCUPPort(new URL(config.getDIPEUrl()));

		// oggetto richiesta
		Richiesta_RichiestaRispostaSincrona_RichiestaDettaglioCUP_Type richiesta = new Richiesta_RichiestaRispostaSincrona_RichiestaDettaglioCUP_Type();
		richiesta.setTitoloRichiesta("richiesta dettaglio CUP: " + cup);
		System.out.println("TB: ID RICHIESTA: " + idRichiesta + " COSTRUZIONE RICHIESTA PER CUP " + cup);
		// costruzione richiesta
		RICHIESTADETTAGLIOCUPDocument richDoc = RICHIESTADETTAGLIOCUPDocument.Factory.newInstance();
		RICHIESTADETTAGLIOCUP sez = richDoc.addNewRICHIESTADETTAGLIOCUP();
		sez.addNewCODICECUP().newCursor().setTextValue(cup);
		sez.addNewIDRICHIESTA().newCursor().setTextValue(idRichiesta);
		sez.addNewUSER().newCursor().setTextValue(config.getDIPEUser());
		sez.addNewPASSWORD().newCursor().setTextValue(config.getDIPEPwd());
//      char[] ch = Base64Coder.encode("<?xml version=\"1.0\"?>\n".concat(richDoc.toString().replace("\t","").replace(" ", "")).getBytes());
//      if (logger != null ) logger.debug("***" + new String(ch));
		if (logger != null)
			logger.debug("***\n" + richDoc.toString());

		// richiesta.setRichiesta(new String(ch).getBytes());
		richiesta.setRichiesta(Base64Coder.stringToBytesASCII(
				"<?xml version=\"1.0\"?>\n".concat(richDoc.toString().replace("\t", "").replace(" ", ""))));
		System.out.println("TB: ID RICHIESTA: " + idRichiesta + " INVIO LA RICHIESTA PER CUP " + cup);
		Risposta_RichiestaRispostaSincrona_RisultatoDettaglioCUP_Type xmlResult = proxy
				.richiestaRispostaSincrona_RichiestaDettaglioCUP(richiesta);
		System.out.println("TB: ID RICHIESTA: " + idRichiesta + " RICHIESTA INVIATA PER CUP " + cup);
		// if (logger != null ) logger.debug("*** risposta ws:" + xmlResult.toString());

		/* Analisi risposta */
		String ret = xmlResult.getEsitoElaborazione().getValue();
		this.esito = DIPEEsiti.getEnumBycodice(ret);
		if (AVCPassSemaforo.ROSSO.codice().equals(esito.semaforo.codice())) {
			// errore applicativo
			System.out.println(
					"TB: ID RICHIESTA: " + idRichiesta + " ERRORE PER CUP " + cup + " MESSAGGIO DI ERRORE:" + ret);
			throw new Exception(Messaggi.SIMOG_SERVIZI_001.replace("$1", ret));
		}

		String dati = "";

		if (xmlResult.getRisposta() == null) {
			System.out.println("TB: ID RICHIESTA: " + idRichiesta + " RISPOSTA NULLA PER CUP " + cup);
			throw new AxisFault("Risposta nulla!");
		}

//      try {
//         //dati = new String(Base64Coder.decode (new String(xmlResult.getRisposta())));
//         dati = new String(xmlResult.getRisposta());
//      } catch (Exception e) {
//         // e.printStackTrace();
//         // se non riesce la conversione assumo risposta rifiuto testuale
//         //dati = new String(xmlResult.getRisposta()); 
//         this.esito = DIPEEsiti.RIFIUTO_APPLICATIVO;
//         retVal = Messaggi.SIMOG_SERVIZI_001.replace("$1", dati);
//         // errore applicativo
//         throw new Exception(retVal);
//      }

		dati = new String(xmlResult.getRisposta());
		System.out.println("TB: ID RICHIESTA: " + idRichiesta + " RISPOSTA PER CUP " + cup + " RISPOSTA XML: " + dati);
		XmlOptions opts = new XmlOptions();
		ArrayList<XmlError> errors = new ArrayList<XmlError>();
		opts.setErrorListener(errors);
		try {
			DETTAGLIOCUPDocument lt = DETTAGLIOCUPDocument.Factory.parse(dati, opts);
			if (!lt.validate(opts)) {
				// se entra qui per me è scarto quindi inoltro il testo di errore
				retVal = Messaggi.SIMOG_SERVIZI_001.replace("$1", "Formato XML restituito dal DIPE non corretto!");
				System.out.println("TB: ID RICHIESTA: " + idRichiesta
						+ " FORMATO XML RESTITUTITO DAL DIPE NON CORRETTO PER CUP " + cup);
				this.esito = DIPEEsiti.ERRORE_APPLICATIVO; // per il semaforo
			} else {
				// estraggo solo le info necessarie
				retVal += lt.getDETTAGLIOCUP().getDATITITOLARERICHIEDENTE().getSoggettoRichiedente();

				// scnadisco la sezione descrizione
				if (lt.getDETTAGLIOCUP().getDESCRIZIONE().isSetACQUISTOBENI()) {
					ACQUISTOBENI temp = lt.getDETTAGLIOCUP().getDESCRIZIONE().getACQUISTOBENI();
					retVal += " - " + temp.getDescSintetica();
				} else if (lt.getDETTAGLIOCUP().getDESCRIZIONE().isSetCONCESSIONECONTRIBUTINOUNITAPRODUTTIVE()) {
					CONCESSIONECONTRIBUTINOUNITAPRODUTTIVE temp = lt.getDETTAGLIOCUP().getDESCRIZIONE()
							.getCONCESSIONECONTRIBUTINOUNITAPRODUTTIVE();
					retVal += " - " + temp.getDescSintetica();
				} else if (lt.getDETTAGLIOCUP().getDESCRIZIONE().isSetCONCESSIONEINCENTIVIUNITAPRODUTTIVE()) {
					CONCESSIONEINCENTIVIUNITAPRODUTTIVE temp = lt.getDETTAGLIOCUP().getDESCRIZIONE()
							.getCONCESSIONEINCENTIVIUNITAPRODUTTIVE();
					retVal += " - " + temp.getDescSintetica();
				} else if (lt.getDETTAGLIOCUP().getDESCRIZIONE().isSetLAVORIPUBBLICI()) {
					LAVORIPUBBLICI temp = lt.getDETTAGLIOCUP().getDESCRIZIONE().getLAVORIPUBBLICI();
					retVal += " - " + temp.getDescSintetica();
				} else if (lt.getDETTAGLIOCUP().getDESCRIZIONE().isSetPARTECIPAZIONARIECONFERIMCAPITALE()) {
					PARTECIPAZIONARIECONFERIMCAPITALE temp = lt.getDETTAGLIOCUP().getDESCRIZIONE()
							.getPARTECIPAZIONARIECONFERIMCAPITALE();
					retVal += " - " + temp.getDescSintetica();
				} else if (lt.getDETTAGLIOCUP().getDESCRIZIONE().isSetREALIZZACQUISTOSERVIZIFORMAZIONE()) {
					REALIZZACQUISTOSERVIZIFORMAZIONE temp = lt.getDETTAGLIOCUP().getDESCRIZIONE()
							.getREALIZZACQUISTOSERVIZIFORMAZIONE();
					retVal += " - " + temp.getDescSintetica();
				} else if (lt.getDETTAGLIOCUP().getDESCRIZIONE().isSetREALIZZACQUISTOSERVIZINOFORMAZIONERICERCA()) {
					REALIZZACQUISTOSERVIZINOFORMAZIONERICERCA temp = lt.getDETTAGLIOCUP().getDESCRIZIONE()
							.getREALIZZACQUISTOSERVIZINOFORMAZIONERICERCA();
					retVal += " - " + temp.getDescSintetica();
				} else if (lt.getDETTAGLIOCUP().getDESCRIZIONE().isSetREALIZZACQUISTOSERVIZIRICERCA()) {
					REALIZZACQUISTOSERVIZIRICERCA temp = lt.getDETTAGLIOCUP().getDESCRIZIONE()
							.getREALIZZACQUISTOSERVIZIRICERCA();
					retVal += " - " + temp.getDescSintetica();
				} else if (lt.getDETTAGLIOCUP().getDESCRIZIONE().isSetCUPCUMULATIVO()) {
					CUPCUMULATIVO temp = lt.getDETTAGLIOCUP().getDESCRIZIONE().getCUPCUMULATIVO();
					retVal += " - " + temp.getDescSintetica();
				}

				retValArr[0] = retVal;
				if (lt.getDETTAGLIOCUP().getDATIGENERALIPROGETTO().isSetStato())
					retValArr[1] = lt.getDETTAGLIOCUP().getDATIGENERALIPROGETTO().getStato();
				if (lt.getDETTAGLIOCUP().getDATIGENERALIPROGETTO().isSetTematica())
					retValArr[2] = lt.getDETTAGLIOCUP().getDATIGENERALIPROGETTO().getTematica();
				// old retVal += " - " +
				// lt.getDETTAGLIOCUP().getDESCRIZIONE().getLAVORIPUBBLICI().getDescSintetica();
			}
		} catch (Exception xmle) {
			xmle.printStackTrace();
			// xml non valido assumo risposta errore testuale
			if (logger != null)
				logger.debug("eccezione durante la validazione del xml: " + xmle.getMessage());
			// crea allarme ..... xmle.printStackTrace();
			retVal = dati; // xmle.getMessage();
			retValArr[0] = retVal;
			this.esito = DIPEEsiti.RIFIUTO_APPLICATIVO; // per il semaforo
			System.out.println(
					"TB: ====================== FINE GETDETTAGLIOCUP(" + cup + ")============================");
			return retValArr;
			// throw new Exception(xmle);
		}
		System.out.println("TB: ====================== FINE GETDETTAGLIOCUP(" + cup + ")============================");
		return retValArr;
	}

	public DIPEEsiti getEsito() {
		return esito;
	}

	/**
	 * richiede il dettaglio cup ai servizi DIPE
	 * 
	 * @param cup
	 * @param idRichiesta
	 * @return
	 * @throws Exception
	 */
	public RichiestaCUP getCUP(String cup) throws Exception {

		RichiestaCUP retVal = null;

		// uso il manager dei WS per prendere una nuova connessione
		// in questo modo riesco a fare una transazione separata da quella
		// principale
		ConnectionWSManager cwsm = new ConnectionWSManager(logger, config);
		cwsm.createConnection(getClass().getName());
		Connection con = cwsm.getConnection();
		cwsm.setAutocommit(false);
		RichiesteCUPManager rcm = new RichiesteCUPManager(con, logger);

		try {
			// verifico se per il record esiste una richiesta
			retVal = rcm.getByCup(cup);

			// non esiste la inserisco
			if (retVal.getCUP() == null) {
				retVal = new RichiestaCUP();
				retVal.setCUP(cup);
				rcm.insert(retVal);
				// rileggo il bean
				retVal = rcm.getByCup(cup);
			}

			// se per la richiesta non è stata fatta mai la chiamata ai WS preparo
			// la call altrimenti non faccio nulla
			if (retVal.getVALIDO() == null) {
				// richiamo servizio se non è disattivato
				if (config.getDIPEUrl() == null || "".equals(config.getDIPEUrl().trim())) {
					// do per buona la verifica se non esiste la url
					this.esito = DIPEEsiti.SERVIZIO_DISATTIVATO;
					retVal.setDATA_ESITO(rcm.getNow());
					retVal.setVALIDO(Costanti.FLAG_VALORE_SI);
					retVal.setESITO_RICHIESTA(DIPEEsiti.SERVIZIO_DISATTIVATO.descrizione());
					rcm.modifica(retVal);
				} else {
					retVal.setULT_DATA_WS(rcm.getNow());
					rcm.modifica(retVal);

					String[] retWSarr = getDettaglioCUP(cup, PREFISSO_RICHIESTA + cup);
					String retWS = retWSarr[0];
					System.out.println(retWSarr);
					// analisi del'esito
					retVal.setESITO_RICHIESTA(retWS);
					retVal.setVALIDO(AVCPassSemaforo.VERDE.codice().equals(this.getEsito().getSemaforo().codice())
							? Costanti.FLAG_VALORE_SI
							: Costanti.FLAG_VALORE_NO);
					retVal.setDATA_ESITO(rcm.getNow());
					retVal.setStato(retWSarr[1]);
					retVal.setTematica(retWSarr[2]);

				}

				// aggiorno audit
				rcm.modifica(retVal);
			}

		} catch (AxisFault e) {
			if (logger != null)
				logger.debug("*** servizi DIPE eccezione: " + e.getMessage());

			System.out.println("TB: ECCEZIONE AXIS");
			e.printStackTrace();

			// qui cade se non risponde il servizio o risponde male
			// verifico se è passato il timeout in ore dalla data di
			// creazione della richiesta, nel caso do per buono il CUP
			if (retVal != null) {
				long tempoRich = retVal.getDATA_RICHIESTA().getTime() + (config.getDIPETimeout() * 60 * 60 * 1000);
				long tempo = rcm.getNow().getTime();
				if (tempoRich < tempo) {
					// passato troppo tempo do per buono il cup
					this.esito = DIPEEsiti.SERVIZIO_DISATTIVATO;
					retVal.setDATA_ESITO(rcm.getNow());
					retVal.setVALIDO(Costanti.FLAG_VALORE_SI);
					retVal.setESITO_RICHIESTA(DIPEEsiti.SERVIZIO_DISATTIVATO.descrizione());
					rcm.modifica(retVal);
				} else {
					// la richiesta esiste ma non è scaduto il tempo
					this.esito = DIPEEsiti.INATTESA_VALIDAZIONE;
					retVal.setESITO_RICHIESTA(DIPEEsiti.INATTESA_VALIDAZIONE.descrizione());
					rcm.modifica(retVal);
				}
			} else {
				this.esito = DIPEEsiti.ERRORE_APPLICATIVO;
				if (logger != null)
					logger.error(e.getFaultString());
				retVal.setESITO_RICHIESTA(DIPEEsiti.INATTESA_VALIDAZIONE.descrizione());
				rcm.modifica(retVal);
				throw new AxisFault(e.getFaultString());
			}
		} catch (Exception e) {
			this.esito = DIPEEsiti.ERRORE_APPLICATIVO;
			if (logger != null)
				logger.error(e);
			e.printStackTrace();
			retVal.setESITO_RICHIESTA(DIPEEsiti.INATTESA_VALIDAZIONE.descrizione());
			rcm.modifica(retVal);
			throw new ActionException(e.getMessage());
		} finally {
			if (cwsm != null) {
				cwsm.commit();
				cwsm.closeConnection();
			}
		}

		return retVal;
	}

	protected boolean isFlag(String val) {

		return val != null && (Costanti.FLAG_VALORE_SI.equals(val) || Costanti.FLAG_VALORE_NO.equals(val));
	}

	protected boolean isYFlag(String val) {

		return isFlag(val) && Costanti.FLAG_VALORE_SI.equalsIgnoreCase(val);
	}

	protected boolean isNFlag(String val) {

		return isFlag(val) && Costanti.FLAG_VALORE_NO.equalsIgnoreCase(val);
	}

	/**
	 * TICKET 31047 - PARI OPPORTUNITA' TODO: servizio rgs. Specifiche richieste
	 * verifica esistenza del CUP ed è PNRR o PNC
	 * 
	 * @param lt
	 * @return RGSObj, AllValidationBeans
	 */
	public AllValidationBeans validaRGS(Lotto lt) throws Exception {

		// uso il manager dei WS per prendere una nuova connessione
		// in questo modo riesco a fare una transazione separata da quella
		// principale
		ConnectionWSManager cwsm = new ConnectionWSManager(logger, config);
		cwsm.createConnection(getClass().getName());
		Connection con = cwsm.getConnection();
		cwsm.setAutocommit(false);
		RichiesteCUPManager rcm = new RichiesteCUPManager(con, logger);

		AllValidationBeans mEccezioni = new AllValidationBeans();

		try {

			CupRestClient cupRgsRestClient = new CupRestClient(config.getRGS_URL());

			// dobbiamo verificare che il CUP che inviamo esista negli archivi RGS

			List<String> elencoCupString = new ArrayList<String>();

			for (int i = 0; i < lt.getElencoCup().size(); i++) {
				elencoCupString.add(lt.getElencoCup().get(i).getCup());

			}

			CupRequest cupRequestRgs = new CupRequest(elencoCupString);
			CupResponse cr = cupRgsRestClient.callRgs(cupRequestRgs);

			Integer iCup = 0;

			
			if(cr != null && cr.getItems() != null) {
				
				for (CupDataDto dataDto : cr.getItems()) {
					if (dataDto.getErrorCode() == null || (dataDto.getErrorCode() != null && dataDto.getErrorCode() == 1)) {
						RestCupConstants constants = RestCupConstants.getErrorByCodiceErrore(dataDto.getErrorCode());
						switch (constants) {
						// CHECK 4)

						case ERRORE_CUP_INESISTENTE:
							//mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_281);
							break;
						}
					} else {

						String FLAG_PNRR_PNC_RGS = ((dataDto.getTipoPiano() != null && !dataDto.getTipoPiano().isEmpty())
								? "S"
								: "N");

						RichiestaCUP richiestaCUP = estraiCupdaListaCupDipe(cupRequestRgs.getElencoCup().get(iCup));

						String idTematica = rcm.getDescrizioneTematica(richiestaCUP.getIdTematica());

						if (isNFlag(lt.getFLAG_PNRR_PNC()) && isYFlag(FLAG_PNRR_PNC_RGS) && !idTematica.isEmpty()) {
							mEccezioni.addValidationField("label_FlagPnrrPncLotto");
//							mEccezioni.addValidationField("label_FlagCUPLotto");
							mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_281);
						} else if (isNFlag(lt.getFLAG_PNRR_PNC()) && isNFlag(FLAG_PNRR_PNC_RGS) && !idTematica.isEmpty()) {
							mEccezioni.addValidationField("label_FlagPnrrPncLotto");
//							mEccezioni.addValidationField("label_FlagCUPLotto");
							mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_281);
						} else if (isNFlag(lt.getFLAG_PNRR_PNC()) && isYFlag(FLAG_PNRR_PNC_RGS) && idTematica.isEmpty()) {
							mEccezioni.addValidationField("label_FlagPnrrPncLotto");
//							mEccezioni.addValidationField("label_FlagCUPLotto");
							mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_281);
						}
//						else {
							// CHECK 5 POSITIVO)
//							if (isYFlag(lt.getFLAG_PNRR_PNC()) && isYFlag(FLAG_PNRR_PNC_RGS)) {
	//
//								richiestaCUP.setFLAG_PNRR_PNC_RGS(FLAG_PNRR_PNC_RGS);
//								richiestaCUP.setCOD_INVESTIMENTO_RGS(dataDto.getCodiceInvestimento());
	//
//								// Salvataggio dati
//								rcm.modificaFlagRGS(richiestaCUP);
	//
//							}
	//
//							// CHECK 6)
//							if (isNFlag(lt.getFLAG_PNRR_PNC()) && isNFlag(FLAG_PNRR_PNC_RGS)) {
	//
//								richiestaCUP.setFLAG_PNRR_PNC_RGS(FLAG_PNRR_PNC_RGS);
//								richiestaCUP.setCOD_INVESTIMENTO_RGS(dataDto.getCodiceInvestimento());
	//
//								// Salvataggio dati
//								rcm.modificaFlagRGS(richiestaCUP);
	//
//							}
//						}
						richiestaCUP.setFLAG_PNRR_PNC_RGS(FLAG_PNRR_PNC_RGS);
						richiestaCUP.setCOD_INVESTIMENTO_RGS(dataDto.getCodiceInvestimento());

						rcm.modificaFlagRGS(richiestaCUP);
					}
					iCup++;
				}
			} else {
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_287.replace("$1", "Impossibile accedere al servizio RGS"));
			}
			
			// scorriamo qui la lista dei cup associati al lotto
			
		} catch (Exception e) {
			this.esito = DIPEEsiti.ERRORE_APPLICATIVO;
			if (logger != null)
				logger.error(e);
			e.printStackTrace();
		} finally {
			if (cwsm != null) {
				cwsm.commit();
				cwsm.closeConnection();
			}
		}

		// mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_225.replace("$1",cupLottoAgg.getCup()));

		return mEccezioni;
	}

	private RichiestaCUP estraiCupdaListaCupDipe(String string) {

		for (RichiestaCUP richiestaCUP : listaCupDipe) {

			if (richiestaCUP.getCUP().equals(string)) {

				return richiestaCUP;
			}

		}

		return null;

	}

	List<RichiestaCUP> listaCupDipe = new ArrayList<RichiestaCUP>();

	/***
	 * verifica i codici cup vers il dipe
	 * 
	 * @param lt
	 * @param conferma
	 * @return
	 */
	public AllValidationBeans validaCupDIPE(Lotto lt, boolean okMsg) {

		AllValidationBeans mEccezioni = new AllValidationBeans();

		// Per ogni CUP verifico la situazione, se tutti i cup sono validi
		// e almeno uno non ha okUtente allora chiedo la conferma
		ElaborazioniCUPClient cli = new ElaborazioniCUPClient(config, logger);

		if (lt.getElencoCup() != null && lt.getElencoCup().size() > 0) {

			boolean tuttiValidi = true;
			boolean daConfermare = false;
			listaCupDipe = new ArrayList<RichiestaCUP>();

			for (CupLottoAgg cupLottoAgg : lt.getElencoCup()) {

				RichiestaCUP retVal = null;
				// vedo la richiesta DIPE
				try {
					retVal = cli.getCUP(cupLottoAgg.getCup());
					listaCupDipe.add(retVal);
					
				} catch (Exception e) {
					e.printStackTrace();
				}

				// TB Ticket ALM #710
				// Distinguere l'errore di codice CUP non valido dall'errore di connessione al
				// WS
				/*
				 * if(AVCPassSemaforo.ROSSO.codice().equals(cli.getEsito().getSemaforo().codice(
				 * )) || (retVal != null &&
				 * Costanti.FLAG_VALORE_NO.equals(retVal.getVALIDO()))){
				 * mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_225.replace("$1",
				 * cupLottoAgg.getCup())); tuttiValidi = false; }
				 */

				if (AVCPassSemaforo.ROSSO.codice().equals(cli.getEsito().getSemaforo().codice())) {
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_230);
					tuttiValidi = false;
				} else if (retVal != null && Costanti.FLAG_VALORE_NO.equals(retVal.getVALIDO())) {
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_225.replace("$1", cupLottoAgg.getCup()));
					tuttiValidi = false;
				} else if (retVal != null && retVal.getStato() != null && !"Attivo".equals(retVal.getStato())) {
					//FASE 3 3.04.9 -  MAC 40447 Tolto il Warning(addValidationWarn) e aggiunto il controllo bloccante(addValidationErr)
					//sul controllo dello stato della richiesta CUP
//					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_225a.replace("$1", cupLottoAgg.getCup())
//							.replace("$2", retVal.getStato()));
//					tuttiValidi = false;
					//FINE FASE 3 3.04.9 -  MAC 40447
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_225a.replace("$1", cupLottoAgg.getCup())
							.replace("$2", retVal.getStato()));
					tuttiValidi = false;
				}

				// Fine Ticket ALM #710

				// se cup non valido spengo il flag per chiedere conferma esplicita
				if (retVal == null || retVal.getVALIDO() == null || !Costanti.FLAG_VALORE_SI.equals(retVal.getVALIDO()))
					tuttiValidi = false;

				// Se non è confermato accendo la conferma
				if (!Costanti.FLAG_VALORE_SI.equals(cupLottoAgg.getOkUtente()))
					daConfermare = true;
			}

			// messaggio bloccante per conferma esplicita
			if (daConfermare && tuttiValidi && okMsg) {
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_226);
			}
		}
		return mEccezioni;
	}
	
	//MAC CUP 3.04.8 tolto controllo sullo stato del cup per aggiudicazione
		public AllValidationBeans validaCupDIPEAgg(Lotto lt, boolean okMsg) {

			AllValidationBeans mEccezioni = new AllValidationBeans();

			// Per ogni CUP verifico la situazione, se tutti i cup sono validi
			// e almeno uno non ha okUtente allora chiedo la conferma
			ElaborazioniCUPClient cli = new ElaborazioniCUPClient(config, logger);

			if (lt.getElencoCup() != null && lt.getElencoCup().size() > 0) {

				boolean tuttiValidi = true;
				boolean daConfermare = false;
				listaCupDipe = new ArrayList<RichiestaCUP>();

				for (CupLottoAgg cupLottoAgg : lt.getElencoCup()) {

					RichiestaCUP retVal = null;
					// vedo la richiesta DIPE
					try {
						retVal = cli.getCUP(cupLottoAgg.getCup());
						listaCupDipe.add(retVal);
						
					} catch (Exception e) {
						e.printStackTrace();
					}

					// TB Ticket ALM #710
					// Distinguere l'errore di codice CUP non valido dall'errore di connessione al
					// WS
					/*
					 * if(AVCPassSemaforo.ROSSO.codice().equals(cli.getEsito().getSemaforo().codice(
					 * )) || (retVal != null &&
					 * Costanti.FLAG_VALORE_NO.equals(retVal.getVALIDO()))){
					 * mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_225.replace("$1",
					 * cupLottoAgg.getCup())); tuttiValidi = false; }
					 */

					if (AVCPassSemaforo.ROSSO.codice().equals(cli.getEsito().getSemaforo().codice())) {
						mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_230);
						tuttiValidi = false;
					} else if (retVal != null && Costanti.FLAG_VALORE_NO.equals(retVal.getVALIDO())) {
						mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_225.replace("$1", cupLottoAgg.getCup()));
						tuttiValidi = false;
					} else if (retVal != null && retVal.getStato() != null) {
						mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_225a.replace("$1", cupLottoAgg.getCup())
								.replace("$2", retVal.getStato()));
						tuttiValidi = false;
					}

					// Fine Ticket ALM #710

					// se cup non valido spengo il flag per chiedere conferma esplicita
					if (retVal == null || retVal.getVALIDO() == null || !Costanti.FLAG_VALORE_SI.equals(retVal.getVALIDO()))
						tuttiValidi = false;

					// Se non e confermato accendo la conferma
					if (!Costanti.FLAG_VALORE_SI.equals(cupLottoAgg.getOkUtente()))
						daConfermare = true;
				}

				// messaggio bloccante per conferma esplicita
				if (daConfermare && tuttiValidi && okMsg) {
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_226);
				}
			}
			return mEccezioni;
		}
		//FINE MAC CUP 3.04.8
}
