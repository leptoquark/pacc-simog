package it.avlp.simog.actions;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ausa.servizi.avcp.it.wsdl.RicercaSAWS_Client;
import it.avcp.simog.manager.cup.CupLottoAggManager;
import it.avcp.simog.managers.aggiudicazione.MisuraPremialeManager;
import it.avcp.simog.managers.aggiudicazione.MotivoDerogaManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avcp.simog.managers.bdncp.BdncpManager;
import it.avcp.simog.managers.luogo.IstatNutsManager;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.beans.CondizioneLottoBean;
import it.avlp.simog.beans.CpvLotto;
import it.avlp.simog.beans.DerogaQualificazioneSABean;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IniziativaSoggAggr;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.MisuraPremialeBean;
import it.avlp.simog.beans.MisuraPremialeLottoBean;
import it.avlp.simog.beans.MotivoDerogaBean;
import it.avlp.simog.beans.MotivoDerogaLottoBean;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.RequisitoGara;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.StazioneAppaltante;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.common.action.CondizioniAction;
import it.avlp.simog.common.action.RequisitiGLAction;
import it.avlp.simog.common.actions.BandoGaraAction;
import it.avlp.simog.common.contributo.GestioneContributoWrapperBeanClient;
import it.avlp.simog.common.contributo.ParametriContributo;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriCup;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.CATEGORIA;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.MISURA_PREMIALE;
import it.avlp.simog.db.generated.MOTIVO_DEROGA;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.IniziativaManager;
import it.avlp.simog.garamanager.RequisitiGLManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.validatore.GaraValidator;
import it.avlp.simog.validatore.LottoValidator;
import it.mef.serviziCUP.ElaborazioniCUPClient;


public class GaraLottoAction extends BaseAction {

	public static String TIPO_LOTTO = "LOTTO";
	public static String TIPO_GARA = "GARA";
	public static String CLAZZ = "GaraLottoAction";
	private Pattern INVALID_XML_CHARS;

	private SimogProperties config;

	public GaraLottoAction(Connection activeConnection, Logger logger, SimogProperties config) {
		super(activeConnection, logger);
		this.config = config;
		INVALID_XML_CHARS = Pattern
				.compile("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\uD800\uDC00-\uDBFF\uDFFF]");
	}

	/**
	 * @param request       HttpServletRequest
	 * @param witch         String @see {@link #TIPO_LOTTO} or @see
	 *                      {@link #TIPO_GARA}
	 * @param currentAction allowed actions salva/conferma/load
	 * @param id            optional if required action, is salva/conferma
	 * @param dataInizio    optional if required action is salva/conferma
	 * @return Object - Bean or AllValidationBeans
	 */
	public Object load(HttpServletRequest request, String witch) throws ActionException {
		System.out.println("request -----" + request);
		System.out.println("witch -----" + witch);
		return this.loadFromRequest(request, witch);
	}

	/**
	 * metodo per il caricamento del bean specificato dai parametri in request
	 * 
	 * @param request HttpServletRequest
	 * @param witch   String for valid values see : @see {@value #TIPO_GARA} @see
	 *                {@value #TIPO_LOTTO}
	 * @return Object - Bean or AllValidationBeans
	 */
	private Object loadFromRequest(HttpServletRequest request, String witch) throws ActionException {

		if (witch.equals(GaraLottoAction.TIPO_GARA)) {

			return this.loadGaraFromRequest(request);

		} else if (witch.equals(GaraLottoAction.TIPO_LOTTO)) {
			System.out.println("request 2-----------" + request);
			System.out.println("witch 2-----------" + witch);

			return this.loadLottoFromRequest(request);

		} else {

			logger.error("FATAL - UNKNOWN VALUE");
			throw new ActionException("FATAL - UNKNOWN VALUE");
		}
	}

	/**
	 * metodo che si occupa del caricamento della Gara dai parametri in request
	 * 
	 * @param request HttpServletRequest
	 * @return Object - {@link Gara} la gara
	 */

	public Object loadGaraFromRequestWithoutValidation(HttpServletRequest request) throws ActionException {
		return loadGaraFromRequest(request, false);
	}

	private Object loadGaraFromRequest(HttpServletRequest request) throws ActionException {
		return loadGaraFromRequest(request, true);
	}

	private Object loadGaraFromRequest(HttpServletRequest request, boolean validate) throws ActionException {
		try {
			Gara gara = new Gara();
			Gara garaDB = new Gara();

			String oggettoGara = (String) request.getParameter(ParametriServlet.FIELD_NAME_OGGETTO_GARA);
			if (oggettoGara != null)
				oggettoGara = INVALID_XML_CHARS.matcher(oggettoGara).replaceAll("");
			String id_sa_riferimento = (String) request
					.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
			garaDB = (Gara) loadGaraFromDBSenzaValidazione(request);

			if (id_sa_riferimento == null) {
				// sono in modifica da Admin, leggo la SA dal db
				gara.setDATA_TERMINE_PAGAMENTO(garaDB.getDATA_TERMINE_PAGAMENTO());
				gara.setDATA_INIB_PAGAM(garaDB.getDATA_INIB_PAGAM());
			}

			this.setSessionAttribute(request.getSession(), id_sa_riferimento,
					ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
			Utente currentUser = (Utente) request.getSession().getAttribute(ParametriServlet.UTENTE);
			boolean b = ParametriServlet.INSERISCI_IMPORTO_SI
					.equalsIgnoreCase(request.getParameter(ParametriServlet.FIELD_INSERISCI_IMPORTO));
			BigDecimal importoGara = null;
			try {
				if (b) {
					importoGara = new BigDecimal(PageHelper
							.formattaImporto(request.getParameter(ParametriServlet.FIELD_NAME_IMPORTO_GARA_EURO)));
					logger.debug("@@@@@ SI importoGara : " + importoGara);
				} else {
					importoGara = new BigDecimal(1).negate();
					logger.debug("@@@@@ NO importoGara : " + importoGara);
				}
			} catch (NumberFormatException nfe) {
				// logger.error("[FORMAT EXCEPTION]IMPORTO GARA: "+nfe.getMessage());
				importoGara = new BigDecimal(0);
			}

			gara.setOggetto(oggettoGara);
			gara.setID_STAZIONE_APPALTANTE(
					id_sa_riferimento == null ? garaDB.getID_STAZIONE_APPALTANTE() : id_sa_riferimento);
			/**
			 * ATTENZIONE: era usato il metodo [ServletBase]getTodayDate() ora uniformato al
			 * resto data dal db
			 */
			// PP non va bene in modifica!
			// gara.setData_creazione(PageHelper.getDBDateFromTS(new
			// AccessiDB(connection,logger).getNow()));
			gara.setCF_UTENTE(currentUser.getLogin());
			String cfAmministrazione = id_sa_riferimento == null ? garaDB.getCF_AMMINISTRAZIONE()
					: currentUser.getCodiceFiscaleAmministrazioneByIdUfficio(id_sa_riferimento);
			gara.setCF_AMMINISTRAZIONE(cfAmministrazione);
			gara.setDENOM_AMMINISTRAZIONE(id_sa_riferimento == null ? garaDB.getDENOM_AMMINISTRAZIONE()
					: currentUser.getDenomAmministrazByCf(cfAmministrazione));

			if (((gara.getId_Gara() <= 0
					|| SimogProperties.getInstance().isDataCreatedAfter3045(gara.getData_creazione()))
					&& (SimogProperties.getInstance().getUrlWsAusa() != null
							&& !"".equals(SimogProperties.getInstance().getUrlWsAusa())))) {
				RicercaSAWS_Client ausaClient = new RicercaSAWS_Client();
				String codiceAusa = ausaClient.getCodAusaFromCF(cfAmministrazione);
				gara.setCodiceAusa(codiceAusa);
			}

			// Ticket ALM #603: limitazione denominazione centro di costo a 250 caratteri
			// gara.setDENOM_STAZIONE_APPALTANTE(id_sa_riferimento == null ?
			// garaDB.getDENOM_STAZIONE_APPALTANTE() :
			// currentUser.getDenominazioneUfficioById( id_sa_riferimento ) );
			gara.setDENOM_STAZIONE_APPALTANTE(id_sa_riferimento == null ? garaDB.getDENOM_STAZIONE_APPALTANTE()
					: currentUser.getDenominazioneUfficioById(id_sa_riferimento.length() <= 250 ? id_sa_riferimento
							: id_sa_riferimento.substring(0, 249)));
			// Fine Ticket ALM #603

			gara.setID_OSSERVATORIO(id_sa_riferimento == null ? garaDB.getID_OSSERVATORIO()
					: currentUser.getIdOssByCfAmm(cfAmministrazione));
			gara.setIMPORTO_GARA(importoGara);

			gara.setTIPO_SCHEDA_GARA((String) request.getParameter(ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE));

			if (request.getParameter(PSBD.FIELD_NAME_ID_MODO_INDIZIONE) != null)
				gara.setID_MODO_GARA("".equals(request.getParameter(PSBD.FIELD_NAME_ID_MODO_INDIZIONE)) ? 0
						: Integer.valueOf((String) request.getParameter(PSBD.FIELD_NAME_ID_MODO_INDIZIONE)));

			gara.setID_MODO_REAL("".equals(request.getParameter(ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE)) ? 0
					: Integer.valueOf((String) request.getParameter(ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE)));

			// TICKET ALM - 3.04.2 NG
			if (garaDB == null || SimogProperties.getInstance().isDataCreatedAfter3042(garaDB.getData_creazione())) {

				gara.setID_SVOLGIMENTO(
						"".equals(request.getParameter(ParametriServlet.FIELD_NAME_STRUMENTO_SVOLGIMENTO)) ? 0
								: Integer.valueOf((String) request
										.getParameter(ParametriServlet.FIELD_NAME_STRUMENTO_SVOLGIMENTO)));

				gara.setID_ESTREMA_URGENZA("".equals(request.getParameter(ParametriServlet.FIELD_NAME_ESTREMA_URGENZA))
						? 0
						: Integer.valueOf((String) request.getParameter(ParametriServlet.FIELD_NAME_ESTREMA_URGENZA)));

				gara.setID_ALLEGATO_IX("".equals(request.getParameter(ParametriServlet.FIELD_NAME_ALLEGATO_IX)) ? 0
						: Integer.valueOf((String) request.getParameter(ParametriServlet.FIELD_NAME_ALLEGATO_IX)));
			}
			// FINE TICKET ALM - 3.04.2 NG

			gara.setID_STATO_GARA("null".equals(request.getParameter(ParametriServlet.FIELD_NAME_ID_STATO_GARA)) ? 0
					: Long.valueOf((String) request.getParameter(ParametriServlet.FIELD_NAME_ID_STATO_GARA)));

			gara.setDATA_CONFERMA_GARA(
					"null".equals(request.getParameter(ParametriServlet.FIELD_NAME_DATA_CONFERMA_GARA)) ? null
							: (String) request.getParameter(ParametriServlet.FIELD_NAME_DATA_CONFERMA_GARA));

			gara.setCIG_ACC_QUADRO((String) request.getParameter(ParametriServlet.FIELD_NAME_CIG_ACC_QUADRO));

			// gm nuovo campo simog 3.04
			gara.setNumeroLotti("".equals(request.getParameter(ParametriServlet.FIELD_NAME_NUMERO_LOTTI)) ? 0
					: Integer.valueOf((String) request.getParameter(ParametriServlet.FIELD_NAME_NUMERO_LOTTI)));

			// gm nuovo campo simog 3.04
			if (garaDB == null || SimogProperties.getInstance().isDataCreatedAfter3043(garaDB.getData_creazione())) {
				gara.setDurataGiorni(request.getParameter(ParametriServlet.FIELD_NAME_DURATA_GIORNI) == null
						|| "".equals(request.getParameter(ParametriServlet.FIELD_NAME_DURATA_GIORNI)) ? 0
								: Integer.valueOf(
										(String) request.getParameter(ParametriServlet.FIELD_NAME_DURATA_GIORNI)));
			}

			// TICKET ALM #659 - 3.04.4
			if (garaDB == null || SimogProperties.getInstance().isDataCreatedAfter3044(garaDB.getData_creazione())) {
				gara.setFlagSAAgente(request.getParameter(ParametriServlet.FIELD_NAME_FLAG_SA_AGENTE_GARA) == null
						|| "".equals(request.getParameter(ParametriServlet.FIELD_NAME_FLAG_SA_AGENTE_GARA)) ? "N"
								: request.getParameter(ParametriServlet.FIELD_NAME_FLAG_SA_AGENTE_GARA));

				gara.setID_F_DELEGATE(request.getParameter(ParametriServlet.FIELD_NAME_ID_F_DELEGATE) == null
						|| "".equals(request.getParameter(ParametriServlet.FIELD_NAME_ID_F_DELEGATE)) ? 0
								: Integer.valueOf(
										(String) request.getParameter(ParametriServlet.FIELD_NAME_ID_F_DELEGATE)));

				gara.setCF_AMM_AGENTE((String) request.getParameter(ParametriServlet.FIELD_NAME_CF_AMM_AGENTE));
			}
			// FINE TICKET ALM #659 - 3.04.4

			// is3031_ESCL_AVCPASS
			gara.setESCLUSO_AVCPASS("".equals(request.getParameter(ParametriServlet.FIELD_FLAG_ESCLUSO_AVCPASS)) ? null
					: (String) request.getParameter(ParametriServlet.FIELD_FLAG_ESCLUSO_AVCPASS));

			// PP data creazione dal DB sempre
			if (garaDB != null && garaDB.getData_creazione() != null)
				gara.setData_creazione(garaDB.getData_creazione());
			else
				gara.setData_creazione(PageHelper.getCurrentDate());

			// INT85
			if (request.getParameter(ParametriServlet.FIELD_NAME_SCELTA_LEGGE85) != null)
				gara.setSCELTA_LEGGE89(
						Integer.valueOf((String) request.getParameter(ParametriServlet.FIELD_NAME_SCELTA_LEGGE85)));

			// INT85 prendo la tipologia sa dalla collaborazione, se nuova gara

			if (id_sa_riferimento != null) {
				Hashtable uffici = currentUser.getUfficiByProfilo(ProfiloEnum.RUP);
				StazioneAppaltante elem = (StazioneAppaltante) uffici.get(id_sa_riferimento);

				if (elem != null) {
					gara.setTIPOSA_BDNCP(elem.getAmministrazione().getTipoSA());
				}
			}

			// INT87
			if (request.getParameter("hidden" + ParametriServlet.FIELD_NAME_URGENZA_DL133) != null
			// PP ho esagerato! && config.isINT87Attivo(gara.getData_creazione() == null ?
			// PageHelper.getCurrentDate() : gara.getData_creazione())
			)
				gara.setURGENZA_DL133(
						("on".equals(request.getParameter("hidden" + ParametriServlet.FIELD_NAME_URGENZA_DL133))
								|| Costanti.FLAG_VALORE_SI.equals(
										request.getParameter("hidden" + ParametriServlet.FIELD_NAME_URGENZA_DL133)))
												? Costanti.FLAG_VALORE_SI
												: Costanti.FLAG_VALORE_NO);

			// is30350_RFWEBGL01Active
			if (request.getParameter(ParametriServlet.FIELD_NAME_EAGG_COD_MOTIVO) != null)
				gara.setCOD_MOTIVO_EAGG("".equals(request.getParameter(ParametriServlet.FIELD_NAME_EAGG_COD_MOTIVO)) ? 0
						: Integer.valueOf((String) request.getParameter(ParametriServlet.FIELD_NAME_EAGG_COD_MOTIVO)));

			gara.setCatMerc(new ArrayList<String>(Arrays.asList(loadCatMercFromRequest(request))));

			// TICKET ALM - 3.04.4
			gara.setIdGara(getIntReqParameter(request, 0, ParametriServlet.FIELD_NAME_ID_GARA));

			/** validazione del bean */
			Object o = validate ? this.validateGaraOrLotto(gara,null) : null;
			if (o == null) {
				return gara;
			}

			return o;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}
	}

	public Object loadGaraFromDB(HttpServletRequest request) throws ActionException {
		try {
			GaraManager gm = new GaraManager(connection, logger);
			long idGara = Long
					.parseLong(request.getSession().getAttribute(ParametriServlet.SESSION_ID_GARA).toString());
			Gara gara = gm.getGara(idGara);
			logger.debug("gara: \r\n" + ObjectIntrospector.propertiesInfo(Gara.class, gara));
			/** validazione del bean */
			Object o = this.validateGaraOrLotto(gara, null);
			if (o == null) {
				return gara;
			}
			return o;
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}
	}

	public Gara loadGaraFromDBSenzaValidazione(HttpServletRequest request) throws ActionException {
		try {
			GaraManager gm = new GaraManager(connection, logger);

			long idGara = 0;
			try {
				// TICKET ALM #724
				// Recupera l'id della gara memorizzato nella form
				String temp = (String) request.getParameter(ParametriServlet.SESSION_ID_GARA + "_form");
				if (temp != null) {
					idGara = Long.parseLong(temp);
				} // TICKET ALM #6754
				else if (request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA) != null) {
					temp = (String) request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA);
					if (temp != null) {
						idGara = Long.parseLong(temp);
					}
				} else {
					temp = (String) request.getSession().getAttribute(ParametriServlet.SESSION_ID_GARA);

					if (temp != null) {
						idGara = Long.parseLong(temp);
					} else {
						temp = request.getParameter(ParametriServlet.SESSION_ID_GARA);

						if (temp != null) {
							idGara = Long.parseLong(temp);
						} else {
							temp = request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA);

							if (temp != null)
								idGara = Long.parseLong(temp);
						}
					}
				}
				// Fine Ticket ALM #724
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}

			Gara gara = gm.getGara(idGara);
			logger.debug("gara: \r\n" + ObjectIntrospector.propertiesInfo(Gara.class, gara));
			/** validazione del bean */
			return gara;
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}
	}

	/**
	 * metodo che si occupa del caricamento del Lotto dai parametri in request
	 * 
	 * @param request HttpServletRequest
	 * @return Object - Bean or AllValidationBeans
	 */
	private Object loadLottoFromRequest(HttpServletRequest request) throws ActionException {
		try {

			String oggettoLotto = request.getParameter(ParametriServlet.FIELD_NAME_OGGETTO_LOTTO);
			if (oggettoLotto != null)
				oggettoLotto = INVALID_XML_CHARS.matcher(oggettoLotto).replaceAll("");
//			BigDecimal importoLottoNum = new BigDecimal(Costanti.IMPORTO_FUORI_SCALA);
			// campo successivo sottoposto a condizione
			boolean b = ParametriServlet.INSERISCI_IMPORTO_SI
					.equalsIgnoreCase(request.getParameter(ParametriServlet.FIELD_INSERISCI_IMPORTO));
			BigDecimal importoLotto = null;
			try {
				if (b) {
					importoLotto = new BigDecimal(PageHelper
							.formattaImporto(request.getParameter(ParametriServlet.FIELD_NAME_IMPORTO_LOTTO_EURO)));
				} else {
					importoLotto = new BigDecimal(1).negate();
				}
			} catch (NumberFormatException nfe) {
				// logger.error("[FORMAT EXCEPTION]IMPORTO LOTTO: "+nfe.getMessage());
				importoLotto = new BigDecimal(0);
			}
			// end
			char sommaUrgenza = (request.getParameter(ParametriServlet.FIELD_NAME_SOMMA_URGENZA) == null) ? 'N' : 'S';
			String oidLotto = request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO);
			long idLotto = 0;
			if (oidLotto != null && oidLotto instanceof String) {
				idLotto = Long.parseLong((String) oidLotto);
			}

//			if(oidLotto != null && oidLotto instanceof Long){
//				idLotto = ((Long)oidLotto).longValue();
//			}

			String tipologia = request.getParameter(ParametriServlet.FIELD_NAME_TIPOLOGIA);
			String cpvSelezionata = request.getParameter(ParametriServlet.FIELD_NAME_CPV);
			String sceltaContraente = request.getParameter(ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE);
			String motivoDeroga = request.getParameter(ParametriServlet.FIELD_NAME_MOTIVO_DEROGA);
			// TICKET ALM - 3.04.3 #2846
			String motivoCollegamento = request.getParameter(ParametriServlet.FIELD_NAME_MOTIVO);
			// TICKET ALM - 3.04.3 #2846
			String categoriaPrevalente = request.getParameter(ParametriServlet.FIELD_NAME_CATEGORIA_PREVALENTE);
			/* gm nuovo codice 3.0 */
			String annoInizio = request.getParameter(ParametriServlet.FIELD_NAME_TRIENNIO_ANNO_INIZIO);
			String annoFine = request.getParameter(ParametriServlet.FIELD_NAME_TRIENNIO_ANNO_FINE);
			String annoProgressivo = request.getParameter(ParametriServlet.FIELD_NAME_TRIENNIO_ANNO_PROGRESSIVO);
			String annualCuiMinInf = request.getParameter(ParametriServlet.FIELD_NAME_ANNUALE_CUI_MININF);

			// PP B302.3.3
			String flagPrevRip = request.getParameter(ParametriServlet.FIELD_FLAG_PREVEDE_RIP);
			if ("".equals(flagPrevRip))
				flagPrevRip = null;

			// Ticket #20058 - 09 - 02 - 21

			int durataRinnoviRipetizioni;
			try {
				durataRinnoviRipetizioni = Integer
						.parseInt(request.getParameter(ParametriServlet.FIELD_NAME_DURATA_RINNOVI_RIPETIZIONI));
			} catch (NumberFormatException e) {
				durataRinnoviRipetizioni = 0;
			}

			/// int durataRinnoviRipetizioni =
			/// Integer.parseInt(request.getParameter(ParametriServlet.FIELD_NAME_DURATA_RINNOVI_RIPETIZIONI
			/// ));
			if (durataRinnoviRipetizioni < 0) {
				durataRinnoviRipetizioni = 0;
			}

			String flagRipetiz = request.getParameter(ParametriServlet.FIELD_FLAG_RIPETIZIONE);
			if ("".equals(flagRipetiz))
				flagRipetiz = null;

			String cigRipetiz = request.getParameter(ParametriServlet.FIELD_CIG_ORIGINE_RIP);

			// gm nuovo codice pubblicazione bando 3.0
			// String luogoIstat = request.getParameter(PSBD.FIELD_NAME_LUOGO_ISTAT);
			// String luogoNuts = request.getParameter(PSBD.FIELD_NAME_LUOGO_NUTS );
			BigDecimal importo_attuazione_sicurezza = null;
			try {
				importo_attuazione_sicurezza = new BigDecimal(PageHelper
						.formattaImporto(request.getParameter(ParametriServlet.FIELD_NAME_IMPORTO_SICUREZZA)));
			} catch (NumberFormatException nfe) {
				// logger.error("[FORMAT EXCEPTION]IMPORTO LOTTO: "+nfe.getMessage());
				importo_attuazione_sicurezza = null;
			}
			// gm fine nuovo codice pubblicazione bando 3.0

			Lotto lotto = new Lotto();
			lotto.setId_Lotto(idLotto);
			lotto.setOggetto(oggettoLotto);
			lotto.setSomma_Urgenza(sommaUrgenza);
			lotto.setImporto_Lotto(importoLotto);
			lotto.setId_Tipologia(tipologia);
			lotto.setId_CPV(cpvSelezionata);

//	         if(SimogFlags.is3028_RFWEBGL00Active()){
//	               // normalizzazione scelta contraente
//	               if(sceltaContraente != null && !"".equals(sceltaContraente.trim())){
//	                  // separo i valori della scelta contraente se Ã¨ personalizzata
//	                  if(sceltaContraente.length()>3){
//	                     lotto.setID_SCELTA_OSS(new Long(sceltaContraente.substring(2)));
//	                     sceltaContraente = new AccessiDB(connection, logger).getSceltaContraenteAVCP(null, lotto.getID_SCELTA_OSS());
//	                  }
//	               }
//	            }

			lotto.setId_Scelta_Contraente(sceltaContraente);
			// TICKET ALM - 3.04.3 #2846
			lotto.setID_MOTIVO_COLL_CIG(motivoCollegamento);
			// FINE TICKET ALM - 3.04.3 #2846
			lotto.setId_Categoria_prevalente(categoriaPrevalente);

			// PP B302.3.3
			lotto.setFLAG_PREVEDE_RIP(flagPrevRip);
			// Ticket #20058 - 09 - 02 - 21
			lotto.setDurataRipetizioni(durataRinnoviRipetizioni);

			lotto.setFLAG_RIPETIZIONE(flagRipetiz);
			lotto.setCIG_ORIGINE_RIP(cigRipetiz);
			if (request.getParameter(ParametriServlet.FIELD_NAME_CIG) != null) {
				lotto.setCig(CIGBean.getCIGPart(request.getParameter(ParametriServlet.FIELD_NAME_CIG)));
				lotto.setCig_kkk(CIGBean.getCIGKKK(request.getParameter(ParametriServlet.FIELD_NAME_CIG)));
			}

			long idGara = 0;

			/*
			 * Ticket ALM #2452 try { idGara =
			 * Long.parseLong(request.getSession().getAttribute(
			 * ParametriServlet.SESSION_ID_GARA ).toString()); } catch (Exception e) { // se
			 * non Ã¨ in sessoine la prendo dalla request idGara =
			 * Long.parseLong(request.getParameter(ParametriServlet.SESSION_ID_GARA
			 * ).toString());
			 * 
			 * }
			 */
			idGara = Long.parseLong(request.getParameter(ParametriServlet.SESSION_ID_GARA + "_form").toString());
			// Fine Ticket ALM #2452

			// TICKET ALM #2845
			lotto.setFLAG_DL50(request.getParameter(ParametriServlet.FIELD_FLAG_DL50));
			lotto.setPRIMA_ANNUALITA(request.getParameter(ParametriServlet.FIELD_NAME_PRIMA_ANNUALITA));
			// FINE TICKET ALM #2845

			// Ticket #20057 - 09 - 02 - 21

			int durataAffidamentoInGiorni;
			try {
				durataAffidamentoInGiorni = Integer
						.parseInt(request.getParameter(ParametriServlet.FIELD_NAME_DURATA_AFFIDAMENTO_IN_GIORNI));
			} catch (NumberFormatException e) {
				System.out.println(
						"DurataAffidamentoIngiorni: Ã¨ stata inserita una Stringa anzichÃ¨ un valore int -  setto il valore a 0");
				durataAffidamentoInGiorni = 0;
			}

			if (durataAffidamentoInGiorni < 0) {
				durataAffidamentoInGiorni = 0;
			}
			lotto.setDurataAffidamentoGiorni(durataAffidamentoInGiorni);

			// TICKET ALM #3835
			if (request.getParameter(ParametriServlet.FIELD_NAME_AFF_RISERVATI) != null)
				lotto.setID_AFF_RISERVATI(
						Integer.parseInt("0" + request.getParameter(ParametriServlet.FIELD_NAME_AFF_RISERVATI)));

			lotto.setCondizioni(getBeanCondizioni(request, idLotto));
			// FINE TICKET ALM #3835

			// TICKET ALM #3836
			lotto.set_FLAG_REGIME(request.getParameter(ParametriServlet.FIELD_FLAG_REGIME));
			if (request.getParameter(ParametriServlet.FIELD_NAME_ART_REGIME) != null)
				lotto.setID_ART_REGIME(
						Integer.parseInt("0" + request.getParameter(ParametriServlet.FIELD_NAME_ART_REGIME)));
			// FINE TICKET ALM #3836

			lotto.setId_Gara(idGara);

			lotto.setTIPO_CONTRATTO_LOTTO(request.getParameter(ParametriServlet.FIELD_NAME_TIPO_CONTRATTO));
			lotto.setFLAG_ESCLUSO(request.getParameter(ParametriServlet.FIELD_NAME_ESCLUSO));
			//MEV 38205
			lotto.setFLAG_USO_METODI_EDILIZIA(request.getParameter(ParametriServlet.FIELD_NAME_FLAG_USO_METODI_EDILIZIA));
			
			/* gm nuovo codice 3.0 */
			if (annoInizio != null && !annoInizio.trim().equals(""))
				lotto.setTriennio_anno_inizio(annoInizio);

			if (annoFine != null && !annoFine.trim().equals(""))
				lotto.setTriennio_anno_fine(annoFine);

			if (annoProgressivo != null && !annoProgressivo.trim().equals(""))
				lotto.setTriennio_progressivo(annoProgressivo);

			if (annualCuiMinInf != null && !annualCuiMinInf.trim().equals(""))
				lotto.setAnnuale_cui_mininf(annualCuiMinInf);

			// gm nuovo codice pubblicazione bando 3.0
			lotto.setLUOGO_ISTAT(getStringReqParameter(request, null, ParametriServlet.FIELD_NAME_LUOGO_ISTAT));
			lotto.setLUOGO_NUTS(getStringReqParameter(request, null, ParametriServlet.FIELD_NAME_LUOGO_NUTS));
			lotto.setIMPORTO_ATTUAZIONE_SICUREZZA(importo_attuazione_sicurezza);
			// gm fine nuovo codice pubblicazione bando 3.0

			if (request.getParameter(ParametriServlet.FIELD_NAME_ID_ESCLUSIONE) != null)
				lotto.setID_ESCLUSIONE(
						Integer.parseInt("0" + request.getParameter(ParametriServlet.FIELD_NAME_ID_ESCLUSIONE)));

			if (SimogFlags.is3031_RFWEBGL02Active()) {
				lotto.setFLAG_CUP(getStringReqParameter(request, null, ParametriCup.FIELD_FLAG_CUP));
				lotto.setElencoCup(getBeanCup(request)); // Carico elenco CUP dalla request
			}

			// TICKET 31047 - Pari opportunitÃ 

//			String dataCreazioneGara = PageHelper.getFormattedDBDate(
//					(String) request.getSession().getAttribute(ParametriServlet.SESSION_DATA_CREAZIONE_GARA_1));
			
			GaraManager gm = new GaraManager (connection, logger);
			String dataCreazioneGara = gm.getGara(idGara).getData_creazione();

			//MEV 37010 3.04.8.1
			if (request.getParameter(ParametriServlet.FLAG_DEROGA_ADESIONE) != null
					&& !"".equals(request.getParameter(ParametriServlet.FLAG_DEROGA_ADESIONE))) {
				lotto.setFLAG_DEROGA_ADESIONE(getStringReqParameter(request, null, ParametriServlet.FLAG_DEROGA_ADESIONE));
			}
			
			//MEV 34696 setFLAG_PNRR_PNC fatto fuori dall'if 3.04.8
			lotto.setFLAG_PNRR_PNC(getStringReqParameter(request, null, ParametriServlet.FLAG_PNRR_PNC));
            if (SimogProperties.getInstance().isDataCreatedAfter3047(dataCreazioneGara)) {
//				   lotto.setFlagParGenMod1(getStringReqParameter(request, null, ParametriServlet.FIELD_FLAG_PAR_GEN_MOD1));
//				   lotto.setFlagParGenMod2(getStringReqParameter(request, null, ParametriServlet.FIELD_FLAG_PAR_GEN_MOD2));
				
				lotto.setFLAG_PREVISIONE_QUOTA(getStringReqParameter(request, null, ParametriServlet.FLAG_PREVISIONE_QUOTA));
				lotto.setFLAG_MISURE_PREMIALI(getStringReqParameter(request, null, ParametriServlet.FLAG_MISURE_PREMIALI));
				String quotaFem = getStringReqParameter(request, null, ParametriServlet.QUOTA_FEMMINILE);
				String quotaGiov = getStringReqParameter(request, null, ParametriServlet.QUOTA_GIOVANILE);
				if (quotaFem != null) {
					//Double tempQuotaDouble = Double.valueOf(quotaFem.replace(',', '.'));
					BigDecimal tempQuota = new BigDecimal(quotaFem.replace(',', '.'));
					lotto.setQuotaFemminile(tempQuota);
				}
				if (quotaGiov != null) {
					//Double tempQuotaDouble = Double.valueOf(quotaGiov.replace(',', '.'));
					BigDecimal tempQuota = new BigDecimal(quotaGiov.replace(',', '.'));
					lotto.setQuotaGiovanile(tempQuota);
				} else {
					// gestire null
				}
			}

			// TICKET ALM #4219 - 3.04.4
			lotto.setElencoCpvSecondarie(getBeanCPVSecondarie(request));

			lotto.setElencoTipoAppaltoLottoL(
					getBeanTipoAppLotto(request, lotto.getId_Lotto(), PSBD.FIELD_NAME_TIPO_APPALTO_AGG_L));
			lotto.setElencoTipoAppaltoLottoF(
					getBeanTipoAppLotto(request, lotto.getId_Lotto(), PSBD.FIELD_NAME_TIPO_APPALTO_AGG_SF));

			lotto.setElencoMotivoDeroga(getBeanMotivoDeroga(request, PSBD.FIELD_NAME_MOTIVO_DEROGA));
			lotto.setElencoMisurePremiali(getBeanMisurePremiali(request, PSBD.FIELD_NAME_MISURA_PREMIALE));
			
			//3.04.9 MEV 40610
			lotto.setDerogaQualificazioneSA(request.getParameter(PSBD.FIELD_NAME_DEROGA_QUALIFICAZIONE_SA));
			if (lotto.getCIG() == null || "".equals(lotto.getCIG())) {
				if (request.getParameter(ParametriServlet.FLAG_IS_KO) != null
						&& !"".equals(request.getParameter(ParametriServlet.FLAG_IS_KO))) {
					lotto.setFlagIsQualificataKO(request.getParameter(ParametriServlet.FLAG_IS_KO));
				}
			}
			//fine 3.04.9 MEV 40610
			
			// TICKET ALM #3835
			if (SimogFlags.is3042Active()) {
				lotto.setCondizioni(getBeanCondizioni(request, lotto.getId_Lotto()));
			} // FINE TICKET ALM #3835

			// TICKET ALM #4222 - 3.04.4
			if (request.getParameter(ParametriServlet.FIELD_NAME_CATEGORIA_LOTTO) != null)
				lotto.setCOD_CATEGORIA(request.getParameter(ParametriServlet.FIELD_NAME_CATEGORIA_LOTTO));
			if (request.getParameter(ParametriServlet.FIELD_NAME_FLAG_SA_NO_DPCM) != null
					&& !"".equals(request.getParameter(ParametriServlet.FIELD_NAME_FLAG_SA_NO_DPCM)))
				lotto.setFlagNoAdesione("S");
			else
				lotto.setFlagNoAdesione("N");
			if (request.getParameter(ParametriServlet.FIELD_NAME_FLAG_SA_NO_CLASSIFICATA) != null
					&& !"".equals(request.getParameter(ParametriServlet.FIELD_NAME_FLAG_SA_NO_CLASSIFICATA)))
				lotto.setFlagSANonClass("S");
			else
				lotto.setFlagSANonClass("N");
			if (request.getParameter(ParametriServlet.FIELD_NAME_CIG_INIZIATIVA_SEL) != null
					&& !"".equals(request.getParameter(ParametriServlet.FIELD_NAME_CIG_INIZIATIVA_SEL)))
				lotto.setCigIniziativa((String) request.getParameter(ParametriServlet.FIELD_NAME_CIG_INIZIATIVA_SEL));
			// FINE TICKET ALM #4222 - 3.04.4

			// TICKET ALM #31047 - 3.04.7
//			if(request.getParameter(ParametriServlet.FIELD_FLAG_PAR_GEN_MOD1) != null && !"".equals(request.getParameter(ParametriServlet.FIELD_FLAG_PAR_GEN_MOD1))) {				
//				lotto.setFlagParGenMod1(request.getParameter(ParametriServlet.FIELD_FLAG_PAR_GEN_MOD1) );
//			}
//			
//			if(request.getParameter(ParametriServlet.FIELD_FLAG_PAR_GEN_MOD2) != null && !"".equals(request.getParameter(ParametriServlet.FIELD_FLAG_PAR_GEN_MOD2))) {				
//				lotto.setFlagParGenMod2(request.getParameter(ParametriServlet.FIELD_FLAG_PAR_GEN_MOD2));
//			}

			if (request.getParameter(ParametriServlet.FLAG_PNRR_PNC) != null
					&& !"".equals(request.getParameter(ParametriServlet.FLAG_PNRR_PNC))) {
				lotto.setFLAG_PNRR_PNC(request.getParameter(ParametriServlet.FLAG_PNRR_PNC));
			}
			
			//MEV 37010 3.04.8.1
			if (request.getParameter(ParametriServlet.FLAG_DEROGA_ADESIONE) != null
					&& !"".equals(request.getParameter(ParametriServlet.FLAG_DEROGA_ADESIONE))) {
				lotto.setFLAG_DEROGA_ADESIONE(request.getParameter(ParametriServlet.FLAG_DEROGA_ADESIONE));
			}
			//MEV 37010 3.04.8.1

			if (request.getParameter(ParametriServlet.FLAG_PREVISIONE_QUOTA) != null
					&& !"".equals(request.getParameter(ParametriServlet.FLAG_PREVISIONE_QUOTA))) {
				lotto.setFLAG_PREVISIONE_QUOTA(request.getParameter(ParametriServlet.FLAG_PREVISIONE_QUOTA));
			}

			if (request.getParameter(ParametriServlet.FLAG_MISURE_PREMIALI) != null
					&& !"".equals(request.getParameter(ParametriServlet.FLAG_MISURE_PREMIALI))) {
				lotto.setFLAG_MISURE_PREMIALI(request.getParameter(ParametriServlet.FLAG_MISURE_PREMIALI));
			}

			if (request.getParameter(ParametriServlet.QUOTA_FEMMINILE) != null
					&& !"".equals(request.getParameter(ParametriServlet.QUOTA_FEMMINILE))) {
				//Double tempQuotaDouble = Double
						//.valueOf(request.getParameter(ParametriServlet.QUOTA_FEMMINILE).replace(',', '.'));
				BigDecimal tempQuota = new BigDecimal(request.getParameter(ParametriServlet.QUOTA_FEMMINILE).replace(',', '.'));
				lotto.setQuotaFemminile(tempQuota);
			}

			if (request.getParameter(ParametriServlet.QUOTA_GIOVANILE) != null
					&& !"".equals(request.getParameter(ParametriServlet.QUOTA_GIOVANILE))) {
				//Double tempQuotaDouble = Double
						//.valueOf(request.getParameter(ParametriServlet.QUOTA_GIOVANILE).replace(',', '.'));
				BigDecimal tempQuota = new BigDecimal(request.getParameter(ParametriServlet.QUOTA_GIOVANILE).replace(',', '.'));
				lotto.setQuotaGiovanile(tempQuota);
			}

//			if (request.getParameter(ParametriServlet.MOTIVO_DEROGA_SELECTED_TABLEBEAN) != null
//					&& !"".equals(request.getParameter(ParametriServlet.MOTIVO_DEROGA_SELECTED_TABLEBEAN))) {
//				lotto.setSelMotiviDeroga(request.getParameter(ParametriServlet.MOTIVO_DEROGA_SELECTED_TABLEBEAN));
//			}
//
//			if (request.getParameter(ParametriServlet.MISURA_PREMIALE_SELECTED_TABLEBEAN) != null
//					&& !"".equals(request.getParameter(ParametriServlet.MISURA_PREMIALE_SELECTED_TABLEBEAN))) {
//				lotto.setSEL_MISURA_PREMIALE(request.getParameter(ParametriServlet.MISURA_PREMIALE_SELECTED_TABLEBEAN));
//			}

			// TICKET ALM 13691 - 3.04.5
			BigDecimal importo_opzioni = null;
			try {
				importo_opzioni = new BigDecimal(
						PageHelper.formattaImporto(request.getParameter(ParametriServlet.FIELD_NAME_IMPORTO_OPZIONI)));
			} catch (NumberFormatException nfe) {
				importo_opzioni = null;
			}
			lotto.setImporto_opzioni(importo_opzioni);

			Object o = this.validateGaraOrLotto(lotto,request);
			if (o == null) {
				return lotto;
			}
			return o;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}
	}

	/**
	 * Carica le categorie dalla request e le valida, se fallisce la validazione
	 * ritorna un AllValidationBean con gli errori riscontrati
	 * 
	 * @param request
	 * @param idLotto
	 * @return Object String[] or AllvalidationBean
	 * @throws ActionException
	 */
	public Object loadCategorieFromRequest(HttpServletRequest request) throws ActionException {
		String[] categorie = request.getParameterValues(ParametriServlet.FIELD_NAME_CATEGORIA);
		Object o = null;
		if (categorie != null) {
			o = this.validateCategorie(categorie);
			if (o instanceof AllValidationBeans) {
				return o;
			} else {
				return categorie;
			}
		} else {
			return new String[0];
		}
	}

	/**
	 * Carica i dati del lotto dalla request e li incapsula in un istanza di Lotto
	 * 
	 * @param request
	 * @return
	 */
	public Lotto loadLottoPerfFromRequest(HttpServletRequest request) {

		String dataScadenzaPagamenti = PageHelper
				.formatDateOrNull(request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA));
		String dataPubblicazione = PageHelper
				.formatDateOrNull(request.getParameter(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE));
		String idLotto = request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO);
		String oraScadenza = request.getParameter(ParametriServlet.FIELD_NAME_ORA_SCADENZA);
		String dataScadenzaRichiestaInvito = PageHelper
				.formatDateOrNull(request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO));
		String dataLetteraInvito = PageHelper
				.formatDateOrNull(request.getParameter(ParametriServlet.FIELD_NAME_DATA_LETTERA_INVITO));

		Lotto lotto = new Lotto();

		// PP 16012012 per il calcolo corretto del contributo servono tutti i dati del
		// lotto, li prendo dal DB
		LottoManager lm = new LottoManager(connection, logger);
		try {
			lotto = lm.getLotto(Long.parseLong(idLotto));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// impostazione dei nuovi valori
		// lotto.setId_Lotto(Long.parseLong(idLotto));
		lotto.setData_Pubblicazione(dataPubblicazione);
		lotto.setDataScadenzaPagamenti(dataScadenzaPagamenti);
		if (SimogFlags.is3025_RFWEBGL02Active())
			lotto.setORA_SCADENZA(oraScadenza);

		if (SimogFlags.is3030_RFWEBGL00Active()) {
			lotto.setDataScadenzaRichiestaInvito(dataScadenzaRichiestaInvito);
			lotto.setDataLetteraInvito(dataLetteraInvito);
		}

		long idGara = 0;

		try {
			idGara = Long.parseLong(request.getSession().getAttribute(ParametriServlet.SESSION_ID_GARA).toString());
		} catch (Exception e) {
			// se non Ã¨ in sessoine la prendo dalla request
			idGara = Long.parseLong(request.getParameter(ParametriServlet.SESSION_ID_GARA).toString());
		}

		lotto.setId_Gara(idGara);

		return lotto;
	}

	/**
	 * Resituisce un istanza lotto con i dati presi dalla request, se i dati sono
	 * validi. Se i dati non sono validi resituisce un oggetto AllValidationsBean
	 * 
	 * @param request
	 * @return
	 */
	public Object loadValidLottoPerfFromRequest(HttpServletRequest request) {

		Lotto lotto = this.loadLottoPerfFromRequest(request);

		// validazione
		Object o = this.validaPerfezionamentoLotto(lotto);
		if (o != null) {
			return o;
		}
		return lotto;
	}

	/**
	 * metodo per la validazione dei bean
	 * 
	 * @param o Object puo essere GaraBean o LottoBean
	 * @return Object null se la validazione ha avuto esito positivo
	 *         AllValidationBeans in caso contrario
	 */
	private Object validateGaraOrLotto(Object o, HttpServletRequest request) throws ActionException {
			if (o instanceof Gara) {

			GaraValidator gv = new GaraValidator(connection, logger);
			if (gv.valida(o, null)) {
				return null;
			} else {
				return gv.getEccezioni();
			}

		} else if (o instanceof Lotto) {
//			logger.debug(ObjectIntrospector.propertiesInfo(Lotto.class, (Lotto)o));
			LottoValidator lv = new LottoValidator(connection, logger);

			// gestione precedente i CUP
			if (!SimogFlags.is3031_RFWEBGL02Active()
					|| (SimogFlags.is3031_RFWEBGL02Active() && !SimogProperties.getInstance().isCUPAttivo())) {
//				if (lv.valida(o, null)) {
				if (lv.valida(o, null, request.getSession().getId())) { //3.04.9 MEV 40610
					return null;
				} else {
					return lv.getEccezioni();
				}
			} else {
				// nuova gestione con validazione CUP tramite DIPE
//				if (lv.valida(o, null)) {
				if (lv.valida(o, null, request.getSession().getId())) { //3.04.9 MEV 40610

					// Per ogni CUP verifico la situazione
					ElaborazioniCUPClient cli = new ElaborazioniCUPClient(config, logger);
					Lotto lt = (Lotto) o;
					AllValidationBeans eccez = cli.validaCupDIPE(lt, true);
					if (eccez != null)
						lv.getEccezioni().add(eccez);

					// Validazione tramite servizio RGS
					AllValidationBeans eccezRgs = null;
					try {
						eccezRgs = cli.validaRGS(lt);
						if (eccezRgs != null) {
							lv.getEccezioni().add(eccezRgs);
						}
					} catch (Exception e) {
						 e.printStackTrace();
					}

					// propago le eccezioni se ce ne sono
			//		if (	lv.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_WARN).getSize() == 0 &&
			//				lv.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0)
					sendValidations(request, lv.getEccezioni());
					
					if (lv.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0)
						return null;
					else
						return lv.getEccezioni();
				} else {
					return lv.getEccezioni();
				}
			}
		} else {

			logger.error("UNEXPECTED INPUT TYPE");
			throw new ActionException("UNEXPECTED TYPE");

		}
	}

	/**
	 * return null or allvalidationbean
	 * 
	 * @param categorie
	 * @param idLotto
	 * @return
	 */
	private Object validateCategorie(String[] categorie) throws ActionException {
		try {
			LottoValidator sv = new LottoValidator(connection, logger);
			boolean b, b1 = true;
			b = true;
			AllValidationBeans avb = new AllValidationBeans();
			for (int i = 0; i < categorie.length; i++) {
				b1 = sv.validaTipologica(CATEGORIA.TABLE_NAME, CATEGORIA.ID_CATEGORIA, CATEGORIA.DESCRIZIONE,
						CATEGORIA.DATA_FINE_VALIDITA, null, categorie[i]);
				b &= b1;
				if (!b1) {
					avb.addValidationErrElemento("Categoria non valida", i);
				}
			}
			if (b) {
				return null;
			} else {
				return avb;
			}
		} catch (Exception e) {
			throw new ActionException(e.getMessage());
		}

	}

	private Object validaPerfezionamentoLotto(Lotto lotto) {
		LottoValidator lv = new LottoValidator(connection, logger);
		lv.setGiorni(config.getGiorni_pubb_scadenza());
		boolean b = lv.valida(lotto, ParametriServlet.PERFEZIONAMENTO);
		if (b) {
			return null;
		}
		return lv.getEccezioni();
	}

	/******************************************************************************************/
	/************************************
	 * DB ACTIONS
	 ********************************************/
	/******************************************************************************************/
	/**
	 * metodo per l'inserimento di una gara
	 * 
	 * @param gara      Gara
	 * @param categorie
	 * @return Gara contente l'id
	 * @throws ActionException
	 */
	public Gara insertGara(Gara gara) throws ActionException {
		try {
			GaraManager gm = new GaraManager(connection, logger);
			gara.setIdGara(gm.creaNuovaGara(gara));

			// TICKET TICKET ALM #659 - 3.04.4
			if (gara.getFlagSAAgente() != null && !"".equals(gara.getFlagSAAgente())
					&& Costanti.FLAG_VALORE_SI.equals(gara.getFlagSAAgente())) {
				BdncpManager bm = new BdncpManager(connection, logger);
				gara.setDEN_AMM_AGENTE(bm.loadDenSA(gara.getCF_AMM_AGENTE()));
				gm.insertFunzioniDelegateGara(gara);
			}
			// FINE TICKET ALM #659 - 3.04.4
			return gara;

		} catch (Exception e) {
//			e.printStackTrace();
			throw new ActionException(e.getMessage());
		}
	}

	/**
	 * metodo per il salvataggio di una gara
	 * 
	 * @param gara Gara
	 * @return TODO
	 * @return void
	 * @throws ActionException
	 */
	public void saveGara(Gara gara, boolean ricalcola, AllValidationBeans msgs) throws ActionException {
		try {
			GaraManager gm = new GaraManager(connection, logger);

			// ricalcolo se modifica da amministratore e gara confermata (pregresso) o gara
			// pubblicata)
			if (ricalcola && (gara.getDATA_CONFERMA_GARA() != null || gara.getDATA_PERFEZIONAMENTO_BANDO() != null)) {

// basato sui lotti ? secondo me no, sono recuperi del pregresso				
//				TableBean lista = gm.getDettagliGaraByIdGara(String.valueOf(gara.getId_Gara()));
//				BigDecimal totale = new BigDecimal(0);
//				BigDecimal importo = new BigDecimal(0);
//				// sommo gli importi dei lotti attivi
//				for (int i = 0; i < lista.getFullSize(); i++) {
//					if ("".equals(lista.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO, i))){
//						importo = new BigDecimal(lista.getNulledField(LOTTO.IMPORTO_LOTTO, i));
//						if (importo.doubleValue() == -1){
//							totale = BigDecimal.valueOf(-1);
//							break;
//						}
//						totale.add(importo);
//					}
//				}

				GestioneContributoWrapperBeanClient gcwbc = new GestioneContributoWrapperBeanClient(
						config.getContributoUrl(), connection, logger);

				if (SimogFlags.isGRIGLIA_CONTRIBUTO()) {
					// devo ricalcolare i contributi di tutti i lotti e della gara
					LottoManager lm = new LottoManager(connection, logger);
					Map<String, Lotto> lotti = lm.getMappaLotti(gara.getId_Gara());

					gcwbc.ricalcola(gara, lotti);

					// devo aggiornare i lotti, per i quali dovrebbe essere stato ricalcolato il
					// contributo
					for (Iterator<Lotto> iterator = lotti.values().iterator(); iterator.hasNext();) {
						Lotto elem = iterator.next();
						lm.modificaLotto(elem);
					}
				}

				// controllo messaggio di ritorno
				msgs.add(gcwbc.getErrors());
			}
			// aggiornamento gara
			gm.saveGara(gara);

			// TICKET TICKET ALM #659 - 3.04.4
			if (gara.getFlagSAAgente() != null && !"".equals(gara.getFlagSAAgente())) {
				if (Costanti.FLAG_VALORE_SI.equals(gara.getFlagSAAgente())) {
					BdncpManager bm = new BdncpManager(connection, logger);
					gara.setDEN_AMM_AGENTE(bm.loadDenSA(gara.getCF_AMM_AGENTE()));

					String cfRupDelegata = gm.getCFRupGaraDelegata(gara.getId_Gara());
					if (cfRupDelegata != null) {
						gara.setCF_UTENTE(cfRupDelegata);
						gm.updateFunzioniDelegateGara(gara);
					} else {
						gm.insertFunzioniDelegateGara(gara);
					}
				} else
					gm.deleteFunzioniDelegateGara(gara.getId_Gara());
			}
			// FINE TICKET ALM #659 - 3.04.4

		} catch (Exception e) {
//			e.printStackTrace();
			throw new ActionException(e.getMessage());
		}
	}

// INT87
	/**
	 * metodo per la modifica del flag DL133
	 * 
	 * @param gara Gara
	 * @return TODO
	 * @return void
	 * @throws ActionException
	 */
	public void saveDL133(Gara gara) throws ActionException {
		try {
			GaraManager gm = new GaraManager(connection, logger);

			// aggiornamento gara
			gm.saveDL133(gara);

		} catch (Exception e) {
//       e.printStackTrace();
			throw new ActionException(e.getMessage());
		}
	}

	/**
	 * @param lotto              Lotto
	 * @param gara               Gara
	 * @param richiedenteCIG     String
	 * @param adminOr            String
	 * @param misuraPremialeList
	 * @param motivoDerogaList
	 * @return Lotto contente l'id
	 * @throws ActionException
	 */
	public Lotto insertLotto(Lotto lotto, Gara gara, String richiedenteCIG, String adminOr, String[] categorie)
			throws ActionException {
		try {
			LottoManager lm = new LottoManager(connection, logger);
			MotivoDerogaManager motivoDerogaManager = new MotivoDerogaManager(connection, logger);
			MisuraPremialeManager misuraPremialeManager = new MisuraPremialeManager(connection, logger);

			lotto = lm.creaNuovoLotto(gara, lotto, richiedenteCIG, adminOr);

			if (categorie == null) {
				categorie = new String[0];
			}
			this.insertCategorie(lm, lotto, categorie);

			if (SimogFlags.is3031_RFWEBGL02Active() && SimogProperties.getInstance().isCUPAttivo()) {
				CupLottoAggManager cupMan = new CupLottoAggManager(connection, logger);
				cupMan.settingIdLotto(lotto);
				cupMan.addCup(lotto.getElencoCup(), false);
			}

			if (SimogFlags.is3031_RFWEBGL00Active() && SimogProperties.getInstance().isCUPAttivo()) {
				TipoAppaltoManager talMan = new TipoAppaltoManager(connection, logger);
				talMan.settingIdLotto(lotto);
				talMan.aggiornaTipoAppaltoLotto(lotto, false);
			}

			// TICKET ALM - 3.04.2 NG
			// Inserisci/Aggiorna le condizioni associate al lotto
			if (SimogFlags.is3042Active()
					&& SimogProperties.getInstance().isDataCreatedAfter3042(gara.getData_creazione())) {
				/*
				 * CondizioniManager cman = new CondizioniManager(connection,logger);
				 * cman.deleteCondizioniLotto(lotto.getId_Lotto()); for(CondizioneLottoBean clb
				 * : lotto.getCondizioni()) { clb.setIdLotto(lotto.getId_Lotto());
				 * cman.saveCondBean(clb); }
				 */
				CondizioniAction ca = new CondizioniAction(connection, logger);
				ca.saveCondizioniLotto(lotto.getCondizioni(), lotto.getId_Lotto());

			}
			// FINE TICKET ALM - 3.04.2 NG

			// TICKET ALM #4219 - 3.04.4
			// Elimina duplicati
			if (lotto.getElencoCpvSecondarie() != null && lotto.getElencoCpvSecondarie().size() > 0) {
				lotto.setElencoCpvSecondarie(this.verificaDuplicati(lotto.getElencoCpvSecondarie()));
				for (CpvLotto cpvSec : lotto.getElencoCpvSecondarie()) {
					lm.insertCpvLotto(lotto.getId_Lotto(), cpvSec);
				}
			}
			// FINE TICKET ALM #4219 - 3.04.4

			// TICKET ALM #4223-#4224 - 3.04.4
			IniziativaManager im = new IniziativaManager(connection, logger);

			boolean flagNoDpcmDB = false;
			boolean flagNoSaClassDB = false;
			List<Long> autodichiarazioniDB = im.getAutodichiarazioni(lotto.getId_Lotto(), lotto.getCOD_CATEGORIA());
			boolean flagNoDpcmUser = lotto.getFlagNoAdesione() != null && !"".equals(lotto.getFlagNoAdesione())
					&& Costanti.FLAG_VALORE_SI.equals(lotto.getFlagNoAdesione());
			boolean flagNoSaClassUser = lotto.getFlagSANonClass() != null && !"".equals(lotto.getFlagSANonClass())
					&& Costanti.FLAG_VALORE_SI.equals(lotto.getFlagSANonClass());

			for (Long auto : autodichiarazioniDB) {
				if (auto == Costanti.INIZIATIVE_NON_IDONEE)
					flagNoDpcmDB = true;
				if (auto == Costanti.SA_NON_CLASSIFICATA)
					flagNoSaClassDB = true;
			}

			// Se le autodichiarazioni non sono presenti su db ma sono state richieste,
			// vanno inserite
			if (!flagNoDpcmDB && flagNoDpcmUser) {
				im.insertAutodichiarazione(Long.parseLong(lotto.getCOD_CATEGORIA()), lotto.getId_Lotto(),
						Costanti.INIZIATIVE_NON_IDONEE);
			}
			if (!flagNoSaClassDB && flagNoSaClassUser) {
				im.insertAutodichiarazione(Long.parseLong(lotto.getCOD_CATEGORIA()), lotto.getId_Lotto(),
						Costanti.SA_NON_CLASSIFICATA);
			}

			// SOLO WEB
			// Se non sono state inviate autodichiarazioni ma si sta aderendo a una
			// iniziativa, azzerare anche in questo caso le autodichiarazioni valide
			if (SimogFlags.isFromWeb() && !flagNoDpcmUser && !flagNoSaClassUser
					&& !Costanti.EAGG_CATMERC_999.equals(lotto.getCOD_CATEGORIA())
					&& lotto.getCigIniziativa() != null) {
				IniziativaManager inizMan = new IniziativaManager(connection, logger);
				java.util.List<IniziativaSoggAggr> listaIniz = inizMan.getIniziative(lotto.getCigIniziativa(), null, null, null, null, true);
				if (listaIniz != null && !listaIniz.isEmpty()) {
				IniziativaSoggAggr iniziativa = listaIniz.get(0);
				if (gara.getID_MODO_REAL() == Costanti.MODOREAL_ADESIONE || gara.getID_MODO_REAL() == Costanti.MODOREAL_ADESIONE_NOCOMPET) {
					int idModReal = iniziativa.getFlagConfrontoComp() != null
							&& Costanti.FLAG_VALORE_SI.equals(iniziativa.getFlagConfrontoComp())
									? Costanti.MODOREAL_ADESIONE
									: Costanti.MODOREAL_ADESIONE_NOCOMPET;
					new GaraManager(connection, logger).updateCIGAccQ(lotto.getId_Gara(), lotto.getCigIniziativa(),
							idModReal);
				}
//				3.04.8 34190 fix
				if (gara.getID_MODO_REAL() == Costanti.MODOREAL_CONCESSIONE || gara.getID_MODO_REAL() == Costanti.MODOREAL_CONCESSIONE_NOCOMPET) {
					int idModReal = iniziativa.getFlagConfrontoComp() != null
							&& Costanti.FLAG_VALORE_SI.equals(iniziativa.getFlagConfrontoComp())
									? Costanti.MODOREAL_CONCESSIONE
									: Costanti.MODOREAL_CONCESSIONE_NOCOMPET;
					new GaraManager(connection, logger).updateCIGAccQ(lotto.getId_Gara(), lotto.getCigIniziativa(),
							idModReal);
				}
				}
			}
			// FINE TICKET ALM #4223-#4224 - 3.04.4

			// #31047 PARITA DI GENERE
			motivoDerogaManager.createMotivoDerogaLottoRelation(lotto.getId_Lotto(), lotto.getElencoMotivoDeroga());
			misuraPremialeManager.createMisuraPremialeLottoRelation(lotto.getId_Lotto(),lotto.getElencoMisurePremiali());

			return lotto;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException(e.getMessage(), e);
		}
	}

	/**
	 * metodo per la modifica del lotto
	 * 
	 * @param lotto
	 * @param categorie
	 * @return
	 * @throws ActionException
	 */
	public boolean modificaLotto(Lotto lotto, String[] categorie, boolean ricalcola, AllValidationBeans msgs)
			throws ActionException {
		try {
			LottoManager lm = new LottoManager(connection, logger);
			int count = 0;

			// prendo i data gara
			GaraManager gm = new GaraManager(connection, logger);
			Gara gara = gm.getGara(lotto.getId_Gara());

			GestioneContributoWrapperBeanClient gcwbc = new GestioneContributoWrapperBeanClient(
					config.getContributoUrl(), connection, logger);

			// la modifica lotto da parte dell'amministratore deve ricalcolare il
			// contributo, se il lotto Ã¨ confermato
			if (ricalcola && lotto.getData_Pubblicazione() != null) {

				// chiamo il calcolo
				ParametriContributo parLotto = new ParametriContributo(gara, lotto,
						PageHelper.getCalendarFromStringDate(lotto.getData_Pubblicazione()), connection, logger);

				boolean isRipetizione = Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_RIPETIZIONE())
						|| Costanti.COLL_CIG_RIP.equals(lotto.getID_MOTIVO_COLL_CIG());

				BigDecimal importo = !isRipetizione ? gcwbc.getContributoOE(parLotto) : new BigDecimal(0);
				if (!gcwbc.hasErrors())
					lotto.setImporto_Impresa(importo);
				else
					lotto.setImporto_Impresa(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA));

				// controlo messaggio di ritorno
				msgs.add(gcwbc.getErrors());

				lotto.setImporto_SA(new BigDecimal(0));
			}

			if (lotto.getImporto_SA() == null)
				lotto.setImporto_SA(new BigDecimal(0));
			if (lotto.getImporto_Impresa() == null)
				lotto.setImporto_Impresa(new BigDecimal(0));

			// TICKET ALM - 3.04.7
			if (lotto.getLUOGO_ISTAT() != null && !"".equals(lotto.getLUOGO_ISTAT())) {
				IstatNutsManager inm = new IstatNutsManager(connection, logger);
				lotto.setLUOGO_NUTS(inm.getNutsFromIstat(lotto.getLUOGO_ISTAT()));
			}

			count += lm.modificaLotto(lotto);
			count += lm.updateLottoCategorieScorporabili(String.valueOf(lotto.getId_Lotto()), categorie);

			// TICKET ALM #640
			// Sblocca stato cup IN_DEFINIZIONE solo se utente non Ã¨ amministratore
			// if( SimogFlags.is3031_RFWEBGL02Active() &&
			// SimogProperties.getInstance().isCUPAttivo()){
			boolean confermato = false;
			if (ricalcola && lotto.getData_Pubblicazione() != null)
				confermato = true;

			if (SimogFlags.is3031_RFWEBGL02Active() && SimogProperties.getInstance().isCUPAttivo()) {
				CupLottoAggManager cupMan = new CupLottoAggManager(connection, logger);
				cupMan.settingIdLotto(lotto);
				cupMan.updateElencoCup(lotto, confermato);
			}

			if (SimogFlags.is3031_RFWEBGL00Active() && SimogProperties.getInstance().isCUPAttivo()) {
				TipoAppaltoManager talMan = new TipoAppaltoManager(connection, logger);
				talMan.aggiornaTipoAppaltoLotto(lotto, confermato);// TICKET ALM #8170 MAC
			}

			MotivoDerogaManager motivoDerogaManager = new MotivoDerogaManager(connection, logger);
			motivoDerogaManager.aggiornaMotivoDerogaLotto(lotto);

			MisuraPremialeManager misuraPremialeManager = new MisuraPremialeManager(connection, logger);
			misuraPremialeManager.aggiornaMisuraPremialeLotto(lotto);

			// TICKET ALM - 3.04.2 NG
			if (SimogFlags.is3042Active()
					&& SimogProperties.getInstance().isDataCreatedAfter3042(gara.getData_creazione())) {
				CondizioniAction ca = new CondizioniAction(connection, logger);
				ca.saveCondizioniLotto(lotto.getCondizioni(), lotto.getId_Lotto());
			} // FINE TICKET ALM - 3.04.2 NG

			// TICKET ALM #4219 - 3.04.4
			lm.deleteCpvLotto(lotto.getId_Lotto());
			// Elimina duplicati
			if (lotto.getElencoCpvSecondarie() != null && lotto.getElencoCpvSecondarie().size() > 0) {
				lotto.setElencoCpvSecondarie(this.verificaDuplicati(lotto.getElencoCpvSecondarie()));
				for (CpvLotto cpvSec : lotto.getElencoCpvSecondarie())
					lm.insertCpvLotto(lotto.getId_Lotto(), cpvSec);
			}
			// FINE TICKET ALM #4219 - 3.04.4

			// TICKET ALM #4223-#4224 - 3.04.4
			IniziativaManager im = new IniziativaManager(connection, logger);

			boolean flagNoDpcmDB = false;
			boolean flagNoSaClassDB = false;
			List<Long> autodichiarazioniDB = im.getAutodichiarazioni(lotto.getId_Lotto(), lotto.getCOD_CATEGORIA());
			boolean flagNoDpcmUser = lotto.getFlagNoAdesione() != null && !"".equals(lotto.getFlagNoAdesione())
					&& Costanti.FLAG_VALORE_SI.equals(lotto.getFlagNoAdesione());
			boolean flagNoSaClassUser = lotto.getFlagSANonClass() != null && !"".equals(lotto.getFlagSANonClass())
					&& Costanti.FLAG_VALORE_SI.equals(lotto.getFlagSANonClass());

			for (Long auto : autodichiarazioniDB) {
				if (auto == Costanti.INIZIATIVE_NON_IDONEE)
					flagNoDpcmDB = true;
				if (auto == Costanti.SA_NON_CLASSIFICATA)
					flagNoSaClassDB = true;
			}

			// Se non ci sono autodichiarazioni nel db e' possibile che la categoria sia
			// cambiata.
			// Per sicurezza, far scadere tutte le autodichiarazioni valide
			if (Costanti.EAGG_CATMERC_999.equals(lotto.getCOD_CATEGORIA()) || autodichiarazioniDB.size() == 0)
				im.expireAutodichiarazione(lotto.getId_Lotto(), 0);

			// Se c'era un'autodichiarazone nel db e il flag dell'utente e' no, far scadere
			// l'autodichiarazione
			if (flagNoDpcmDB && !flagNoDpcmUser)
				im.expireAutodichiarazione(lotto.getId_Lotto(), Costanti.INIZIATIVE_NON_IDONEE);
			if (flagNoSaClassDB && !flagNoDpcmUser)
				im.expireAutodichiarazione(lotto.getId_Lotto(), Costanti.SA_NON_CLASSIFICATA);

			// Se le autodichiarazioni non sono presenti su db ma sono state richieste,
			// vanno inserite
			if (!flagNoDpcmDB && flagNoDpcmUser) {
				im.insertAutodichiarazione(Long.parseLong(lotto.getCOD_CATEGORIA()), lotto.getId_Lotto(),
						Costanti.INIZIATIVE_NON_IDONEE);
			}
			if (!flagNoSaClassDB && flagNoSaClassUser) {
				im.insertAutodichiarazione(Long.parseLong(lotto.getCOD_CATEGORIA()), lotto.getId_Lotto(),
						Costanti.SA_NON_CLASSIFICATA);
			}

			// SOLO WEB
			// Se non sono state inviate autodichiarazioni ma si sta aderendo a una
			// iniziativa, azzerare anche in questo caso le autodichiarazioni valide
			if (SimogFlags.isFromWeb() && !flagNoDpcmUser && !flagNoSaClassUser
					&& !Costanti.EAGG_CATMERC_999.equals(lotto.getCOD_CATEGORIA())
					&& lotto.getCigIniziativa() != null) {
				im.expireAutodichiarazione(lotto.getId_Lotto(), 0);
				IniziativaManager inizMan = new IniziativaManager(connection, logger);
				java.util.List<IniziativaSoggAggr> listaIniz = inizMan.getIniziative(lotto.getCigIniziativa(), null, null, null, null, true);
				if (listaIniz != null && !listaIniz.isEmpty()) {
				IniziativaSoggAggr iniziativa = listaIniz.get(0);
				if (gara.getID_MODO_REAL() == Costanti.MODOREAL_ADESIONE || gara.getID_MODO_REAL() == Costanti.MODOREAL_ADESIONE_NOCOMPET) {
					int idModReal = iniziativa.getFlagConfrontoComp() != null
							&& Costanti.FLAG_VALORE_SI.equals(iniziativa.getFlagConfrontoComp())
									? Costanti.MODOREAL_ADESIONE
									: Costanti.MODOREAL_ADESIONE_NOCOMPET;
					gm.updateCIGAccQ(lotto.getId_Gara(), lotto.getCigIniziativa(), idModReal);
				}
				//3.04.8 34190 fix
				if (gara.getID_MODO_REAL() == Costanti.MODOREAL_CONCESSIONE || gara.getID_MODO_REAL() == Costanti.MODOREAL_CONCESSIONE_NOCOMPET) {
					int idModReal = iniziativa.getFlagConfrontoComp() != null
							&& Costanti.FLAG_VALORE_SI.equals(iniziativa.getFlagConfrontoComp())
									? Costanti.MODOREAL_CONCESSIONE
									: Costanti.MODOREAL_CONCESSIONE_NOCOMPET;
					gm.updateCIGAccQ(lotto.getId_Gara(), lotto.getCigIniziativa(), idModReal);
				}
				}
			}

			// FINE TICKET ALM #4223-#4224 - 3.04.4

// forse non serve la servlet richiama anche il savegara
//            if(ricalcola && lotto.getData_Pubblicazione() != null && SimogFlags.isGRIGLIA_CONTRIBUTO()){
//               // devo ricalcolare i contributi della gara 
//               Map<String, Lotto> lotti = lm.getMappaLotti(gara.getId_Gara());
//               
//               gcwbc.calcola(gara, lotti);
//               gm.saveGara(gara);
//            }

			return count > 0;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw new ActionException(e.getMessage());
		}
	}

	/**
	 * metodo per il perfezionamento del lotto
	 * 
	 * @param lotto
	 * @return
	 * @throws ActionException
	 */
	public boolean perfezionaLotto(Lotto lotto, AllValidationBeans msgs) throws ActionException {
		try {
			LottoManager lottoManager = new LottoManager(connection, logger);

			GaraManager gm = new GaraManager(connection, logger);
			Gara gara = gm.getGara(lotto.getId_Gara());

			// mi serve l'importo riginale del lotto
			TableBean lottoDb = lottoManager.getLottoByIdLottoAmm(lotto.getId_Lotto());
			lotto.setImporto_Lotto(new BigDecimal(lottoDb.getNulledField(LOTTO.IMPORTO_LOTTO, 0)));

			GestioneContributoWrapperBeanClient gcwbc = new GestioneContributoWrapperBeanClient(
					config.getContributoUrl(), connection, logger);

			ParametriContributo parLotto = new ParametriContributo(gara, lotto,
					PageHelper.getCalendarFromStringDate(lotto.getData_Pubblicazione()), connection, logger);

			BigDecimal impImpresa = new BigDecimal(0);

			boolean isRipetizione = Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_RIPETIZIONE())
					|| Costanti.COLL_CIG_RIP.equals(lotto.getID_MOTIVO_COLL_CIG());

			if (!isRipetizione) {
				BigDecimal importo = gcwbc.getContributoOE(parLotto);
				if (!gcwbc.hasErrors())
					impImpresa = importo;
				else if (SimogFlags.is30230_NRFWEBXX00Active())
					impImpresa = new BigDecimal(Costanti.IMPORTO_FUORI_SCALA);
			}
			// controlo messaggio di ritorno
			msgs.add(gcwbc.getErrors());

			int esito = lottoManager.perfezionaLotto(Long.toString(lotto.getId_Lotto()), lotto.getData_Pubblicazione(),
					lotto.getDATA_SCADENZA_PAGAMENTI(), impImpresa, lotto.getORA_SCADENZA(),
					lotto.getDataScadenzaRichiestaInvito(), lotto.getDataLetteraInvito());

			// ricalcolo gara
			if (SimogFlags.isGRIGLIA_CONTRIBUTO()) {
				// devo ricalcolare il contributo della gara
				Map<String, Lotto> lotti = lottoManager.getMappaLotti(gara.getId_Gara());

				gcwbc.ricalcola(gara, lotti);

				// controlo messaggio di ritorno
				msgs.add(gcwbc.getErrors());

				// forzo lo stato della gara a confermata se ancora non lo Ã¨
				if (gara.getDATA_CONFERMA_GARA() == null && gara.getDATA_PERFEZIONAMENTO_BANDO() == null) {
					gara.setDATA_CONFERMA_GARA(lotto.getData_Pubblicazione());
					gara.setID_STATO_GARA(StatiScheda.CONFERMATO);
				}
				// sfrutto la modifica della gara passando le stesse impostazioni
				gm.saveGara(gara);
			}

			return esito > 0;
		} catch (Exception e) {
//			e.printStackTrace();
			logger.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}
	}

	/**
	 * metodo per l'iserimento delle categorie per lotto
	 * 
	 * @param lm
	 * @param lotto
	 * @param categorie
	 * @throws ActionException
	 */
	private void insertCategorie(LottoManager lm, Lotto lotto, String[] categorie) throws ActionException {
		try {

			lm.inserisciLottoCategoriaScorporabile(categorie, lotto.getId_Lotto());

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}
	}

	/******************************************************************************************/
	/****************************
	 * PARAMETER & NAVIGATION
	 ****************************************/

	/******************************************************************************************/
//	/**
//	 * metodo per il forward dei valori in request (new set della request) 
//	 * per la ripresentazione della form
//	 * 
//	 * @param request HttpServletRequest
//	 * @param o Object bean gara o lotto
//	 * @param witch String for valid values see : @see {@value #GARA} @see {@value #LOTTO}
//	 */
//	private void forwardBean(HttpServletRequest request,Object o,String witch)throws ActionException{
//		
////		if(witch.equals(GaraLottoAction.GARA)){
////			
////			request.setAttribute(GaraLottoAction.GARA, o);
////			
////		}else if(witch.equals(GaraLottoAction.LOTTO)){
////			
////			request.setAttribute(GaraLottoAction.LOTTO, o);
////			
////		}else{
////			
////			logger.error("FATAL - UNKNOWN INPUT VALUE");
////			throw new ActionException("FATAL - UNKNOWN VALUE");
////		}		
//	}
	/**
	 * metodo per il setting di un'attributo in sessione
	 * 
	 * @param session HttpSession
	 * @param o       Object oggetto da mettere in sessione
	 * @param name    key per recuperare l'attributo
	 */
	public void setSessionAttribute(HttpSession session, Object o, String name) {
		// session.setAttribute( ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE,
		// id_sa_riferimento);
		session.setAttribute(name, o);
		logger.debug("NEW SESSION VAR: [NAME] " + name + " [VALUE]" + o);
	}

	/**
	 * Esegue le operazioni di sblocco gara, revocando anche i requisiti di tipo
	 * 'AR'. Per comodita' ritorna il bean gara che serve per il log applicativo
	 * 
	 * @param sessionIdGara
	 * @return Gara
	 * @throws ActionException
	 */
	public Gara sbloccaGaraLotto(String sessionIdGara) throws ActionException {
		try {
			long idGara = Long.parseLong(sessionIdGara);

			PubblicazioneManager pubblicazione = new PubblicazioneManager(connection, logger);
			pubblicazione.sbloccaPubblicazione(idGara);

			GaraManager garaManager = new GaraManager(connection, logger);
			LottoManager lottoManager = new LottoManager(connection, logger);
			
			//TODO 3.04.10
			//getListaLotti(idGara) e mi prendo la data pubblicazione solo del primo lotto (tanto è uguale a tutti)
			//salvo la data pubblicazione pre sblocco nella nuova colonna nella tabella gara DATA_PUBB_PRE_SBLOCCO

			lottoManager.sbloccaLotto(idGara);
			garaManager.sbloccaGara(idGara);

			Gara gara = garaManager.getGara(Long.parseLong(sessionIdGara));

			if (SimogFlags.is3031_RFWEBGL00Active() && config.isCUPLotto(gara.getData_creazione())) {
				TipoAppaltoManager tam = new TipoAppaltoManager(connection, logger);
				tam.sbloccaTipiAppalto(gara.getId_Gara());
			}

			if (SimogFlags.is3031_RFWEBGL02Active() && config.isCUPLotto(gara.getData_creazione())) {
				CupLottoAggManager cam = new CupLottoAggManager(connection, logger);
				cam.sbloccaCup(gara.getId_Gara());
			}

			if (SimogFlags.is3025_REQUISITIActive()
					&& config.getDataRequisiti().compareTo(PageHelper.getCurrentDate()) <= 0) {
				RequisitiGLManager requisitiGLManager = new RequisitiGLManager(connection, logger);

				RequisitiGLAction rqa = new RequisitiGLAction(connection, logger);

				boolean revocaLogica = true; // Revoca sempre logica

				// revoca dei requisiti automatici "AR"
				if (!revocaLogica)
					requisitiGLManager.deleteDocumentiByTipoUso(idGara, RequisitoGara.TIPO_USO_AR);
				requisitiGLManager.revocaRequisitiGaraByGaraAndTipoUso(idGara, RequisitoGara.TIPO_USO_AR, revocaLogica);

				if (SimogFlags.is3028_RNFDBDT00Active()) {
					// carico tutti i requisiti esistenti, compresi i documenti
					List<RequisitoGara> lista = rqa.getRequisitoGaraList(idGara);

					// revoco i requisiti esistenti
					rqa.revocaRequisitiByGara(idGara, revocaLogica);

					// inserisco di nuovo i requisiti, uso il manager perchÃ¨ l'action fa la
					// validazione
					requisitiGLManager.insertRequisitiGara(lista, idGara);

					// inserisco i documenti associati
					requisitiGLManager.insertDocumentiRequisito(lista, idGara);

					// duplico i riferimenti anche ai record non master
					if (SimogFlags.is3028_RNFDBDT03Active()) {
						rqa.insertDocumentiNonMaster(lista, idGara);
					}
				}
			}
			
			

			return gara;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}
	}

	/**
	 * Esegue le operazioni di modifica per la realizzazione di una gara, per
	 * comodita' ritorna il bean gara che serve per il log applicativo
	 * 
	 * @param sessionIdGara
	 * @return Gara
	 * @throws ActionException
	 */
	public Gara modificaRealizzazioneGara(String sessionIdGara, int idModoReal) throws ActionException {
		try {
			long IdGara = Long.parseLong(sessionIdGara);

			GaraManager garaManager = new GaraManager(connection, logger);
			garaManager.modificaRealizzazioneGara(IdGara, idModoReal);

			Gara gara = garaManager.getGara(Long.parseLong(sessionIdGara));

			if (SimogFlags.isGRIGLIA_CONTRIBUTO()) {
				// devo ricalcolare i contributi di tutti i lotti e della gara
				LottoManager lm = new LottoManager(connection, logger);
				Map<String, Lotto> lotti = lm.getMappaLotti(gara.getId_Gara());
				GestioneContributoWrapperBeanClient gcwbc = new GestioneContributoWrapperBeanClient(
						config.getContributoUrl(), connection, logger);
				gcwbc.ricalcola(gara, lotti);

				// devo aggiornare i lotti, per i quali dovrebbe essere stato ricalcolato il
				// contributo
				for (Iterator<Lotto> iterator = lotti.values().iterator(); iterator.hasNext();) {
					Lotto elem = iterator.next();
					lm.modificaLotto(elem);
				}

				garaManager.saveGara(gara);
			}

			return gara;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}
	}

	/**
	 * Valida il perfezionamento di un lotto singolo da parte dell'amministratore
	 * 
	 * @param lotto
	 * @return
	 * @throws ActionException
	 */
	public Object validaPerfezionamentoLottoAdmin(Lotto lotto) throws ActionException {
		try {
			GaraManager garaManager = new GaraManager(connection, logger);
			BandoGaraAction bga = new BandoGaraAction(connection, logger, config);
			LottoValidator lv = new LottoValidator(connection, logger);
			lv.setGiorni(config.getGiorni_pubb_scadenza());

			TableBean dettagliGara = garaManager.getDettagliGaraByIdGara(String.valueOf(lotto.getId_Gara()));

			Map<String, Lotto> lottiDaPerfezionareOPerfezionati = bga
					.caricaElencoLottiDaPerfezionareOPerfezionati(bga.caricaElencoLotti(dettagliGara), null);

			Gara gara = garaManager.getGara(lotto.getId_Gara());

			int tipoProcedura = bga.getTipologiaProcedura(lottiDaPerfezionareOPerfezionati, gara.getID_SVOLGIMENTO());

			boolean valido = true;

			switch (tipoProcedura) {
			case 1:
				valido = lv.valida(lotto, ParametriServlet.PERFEZIONAMENTO);
				break;
			case 2:
				valido = lv.valida(lotto, ParametriServlet.PERFEZIONAMENTO_PROC_RISTRETTA_FASE2);
				break;
			case 3:
				valido = lv.valida(lotto, ParametriServlet.PERFEZIONAMENTO_PROC_MISTA);
				break;
			}

			return valido ? lotto : lv.getEccezioni();

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ActionException(e.getMessage());
		}
	}

	public List<CupLottoAggExt> getBeanCup(HttpServletRequest request) {
		int nrElencoCup = getIntReqParameter(request, 0, ParametriCup.NR_RIGHE_CUP);
		List<CupLottoAggExt> elencoCup = new LinkedList<CupLottoAggExt>();
		String prefix = "row" + ParametriCup.ELENCO_CUP;
		int idx = 0;

		while (idx <= nrElencoCup) {
			String name = prefix + idx + ParametriCup.FIELD_NAME_CUP;
			String idCupString = request.getParameter(name);
			if (idCupString != null) {
				CupLottoAggExt item = new CupLottoAggExt();
				item.setCup(request.getParameter(prefix + idx + ParametriCup.FIELD_NAME_CUP));
				item.setIdLotto(getLongReqParameter(request, -1L, prefix + idx + ParametriCup.FIELD_NAME_ID_LOTTO));
				item.setIdAggiudicazione(
						getLongReqParameter(request, null, prefix + idx + ParametriCup.FIELD_NAME_ID_AGG));
				item.setDataInizioAgg(getTimestampReqParameter(request, null,
						prefix + idx + ParametriCup.FIELD_NAME_DATA_INIZIO_AGG));
				item.setOkUtente(
						getStringReqParameter(request, null, prefix + idx + ParametriCup.FIELD_NAME_OK_UTENTE));
				elencoCup.add(item);
			}
			idx++;
		}
		return elencoCup;
	}

	public List<CpvLotto> getBeanCPVSecondarie(HttpServletRequest request) {
		int nrElencoCpv = getIntReqParameter(request, 0, "NrCPV");
		int maxIndex = getIntReqParameter(request, 0, "maxCpvIndex");
		List<CpvLotto> elencoCpv = new LinkedList<CpvLotto>();
		String prefix = "rowCPV";
		int idx = 0;

		System.out.println("TECHNIS Numero CPV " + nrElencoCpv);
		System.out.println("TECHNIS Indice Max cpv " + maxIndex);

		while (idx <= maxIndex) {
			String name = prefix + idx;
			System.out.println("TECHNIS idx " + idx);
			String idCupString = request.getParameter(name + "CodCPV");
			System.out.println("TECHNIS idCupString " + idCupString);
			if (idCupString != null) {
				CpvLotto item = new CpvLotto();
				item.setIdCpv(idCupString);
				item.setDescrizione(request.getParameter(name + "DescrizioneCPV"));
				elencoCpv.add(item);

			}
			idx++;
			System.out.println("TECHNIS elencoCpv " + elencoCpv.size());
			if (elencoCpv.size() >= nrElencoCpv)
				break;
		}
		return elencoCpv;
	}

	public List<MotivoDerogaBean> getBeanMotivoDeroga(HttpServletRequest request, String param) {

		List<MotivoDerogaBean> elencoMotivoDeroga = new ArrayList<MotivoDerogaBean>();
		String[] arraySelectedELements = request.getParameterValues(PSBD.FIELD_NAME_MOTIVO_DEROGA);
		List<String> elenco = arraySelectedELements != null ? Arrays.asList(arraySelectedELements)
				: new ArrayList<String>();

		AccessiDB adb = new AccessiDB(connection, logger);
		try {
			elencoMotivoDeroga = adb.getMotivoDerogaWithDataById(MOTIVO_DEROGA.TABLE_NAME, elenco);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return elencoMotivoDeroga;

	}

	public List<MisuraPremialeBean> getBeanMisurePremiali(HttpServletRequest request, String param) {

		List<MisuraPremialeBean> elencoMisuraPremiale = new ArrayList<MisuraPremialeBean>();
		String[] arraySelectedELements = request.getParameterValues(PSBD.FIELD_NAME_MISURA_PREMIALE);
		List<String> elenco = arraySelectedELements != null ? Arrays.asList(arraySelectedELements)
				: new ArrayList<String>();

		AccessiDB adb = new AccessiDB(connection, logger);
		try {
			elencoMisuraPremiale = adb.getMisuraPremialeWithDataById(MISURA_PREMIALE.TABLE_NAME, elenco);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return elencoMisuraPremiale;

	}
	
	//3.04.9 MEV 40610
	public List<DerogaQualificazioneSABean> getBeanDerogaQualificazioneSA(HttpServletRequest request, String param) {

		List<DerogaQualificazioneSABean> elencoDerogaQualificazioneSA = new ArrayList<DerogaQualificazioneSABean>();
		String[] arraySelectedELements = request.getParameterValues(PSBD.FIELD_NAME_DEROGA_QUALIFICAZIONE_SA);
		List<String> elenco = arraySelectedELements != null ? Arrays.asList(arraySelectedELements)
				: new ArrayList<String>();

		
		LottoManager lm = new LottoManager(connection, logger);
		try {
			elencoDerogaQualificazioneSA = lm.getValidDerogaQualificazioneSA();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return elencoDerogaQualificazioneSA;

	}
	//fine 3.04.9 MEV 40610

	public List<TipoAppaltoAggBean> getBeanTipoAppLotto(HttpServletRequest request, long idLotto, String param) {
		List<TipoAppaltoAggBean> elencoTipoAppLotto = new ArrayList<TipoAppaltoAggBean>();
		String[] arrayLSF = request.getParameterValues(param);
		List<String> elenco = arrayLSF != null ? Arrays.asList(arrayLSF) : new ArrayList<String>();
		for (String _idAppalto : elenco) {
			if (_idAppalto == null)
				continue;
			TipoAppaltoAggBean currentTipoApp = new TipoAppaltoAggBean();
			currentTipoApp.setIdAppalto(Long.parseLong(_idAppalto));
			currentTipoApp.setIdLotto(idLotto);
			currentTipoApp.setIdStato(StatiScheda.CONFERMATO);

			elencoTipoAppLotto.add(currentTipoApp);
		}
		return elencoTipoAppLotto;
	}

	public List<MotivoDerogaLottoBean> getBeanMotivoDerogaLotto(HttpServletRequest request, long idLotto,
			String param) {
		List<MotivoDerogaLottoBean> elencoMotivoDerogaLotto = new ArrayList<MotivoDerogaLottoBean>();
		String[] arrayLSF = request.getParameterValues(param);
		List<String> elenco = arrayLSF != null ? Arrays.asList(arrayLSF) : new ArrayList<String>();
		for (String idMotivoDeroga : elenco) {
			if (idMotivoDeroga == null)
				continue;
			MotivoDerogaLottoBean currentMotivoDerogaLotto = new MotivoDerogaLottoBean();
			currentMotivoDerogaLotto.setIdMotivoDeroga(Long.parseLong(idMotivoDeroga));
			currentMotivoDerogaLotto.setIdLotto(idLotto);

//			currentTipoApp.setIdAppalto(Long.parseLong(idMotivoDeroga));
//			currentTipoApp.setIdLotto(idLotto);
//			currentTipoApp.setIdStato(StatiScheda.CONFERMATO);

			elencoMotivoDerogaLotto.add(currentMotivoDerogaLotto);
		}
		return elencoMotivoDerogaLotto;
	}

	public List<MisuraPremialeLottoBean> getBeanMisuraPremialeLotto(HttpServletRequest request, long idLotto,
			String param) {
		List<MisuraPremialeLottoBean> elencoMisuraPremialeLotto = new ArrayList<MisuraPremialeLottoBean>();
		String[] arrayLSF = request.getParameterValues(param);
		List<String> elenco = arrayLSF != null ? Arrays.asList(arrayLSF) : new ArrayList<String>();
		for (String idMisuraPremiale : elenco) {
			if (idMisuraPremiale == null)
				continue;
			MisuraPremialeLottoBean currentMisuraPremialeLotto = new MisuraPremialeLottoBean();
			currentMisuraPremialeLotto.setIdMisuraPremiale(Long.parseLong(idMisuraPremiale));
			currentMisuraPremialeLotto.setIdLotto(idLotto);

			elencoMisuraPremialeLotto.add(currentMisuraPremialeLotto);
		}
		return elencoMisuraPremialeLotto;
	}

//   public List<TipoAppaltoLottoBean> getBeanTipoAppLotto(HttpServletRequest request, long idLotto) 
//   {
//      List<TipoAppaltoLottoBean> elencoTipoAppLottoL = getBeanTipoAppLottoParam(request, idLotto, PSBD.FIELD_NAME_TIPO_APPALTO_AGG_L);
//      List<TipoAppaltoLottoBean> elencoTipoAppLottoSF = getBeanTipoAppLottoParam(request, idLotto, PSBD.FIELD_NAME_TIPO_APPALTO_AGG_SF);
//      List<TipoAppaltoLottoBean> elencoUnito = new LinkedList<TipoAppaltoLottoBean>();
//      elencoUnito.addAll(elencoTipoAppLottoL);
//      elencoUnito.addAll(elencoTipoAppLottoSF);
//      return elencoUnito;
//   }

	/**
	 * Carica le categorie merceologiche dalla request e le valida, se fallisce la
	 * validazione ritorna un AllValidationBean con gli errori riscontrati
	 * 
	 * @param request
	 * @param idGara
	 * @return Object String[] or AllvalidationBean
	 * @throws ActionException
	 */
	public String[] loadCatMercFromRequest(HttpServletRequest request) throws ActionException {
		String[] categorie = request.getParameterValues(ParametriServlet.FIELD_NAME_CATEGORIA);
		Object o = null;
		if (categorie != null)
			return categorie;
		else
			return new String[0];
	}

	// TICKET ALM #3835
	/******************************************************************************************
	 * Ottiene la lista delle condizioni impostando per ogni condizione prelevata
	 * dalla request
	 * <ul>
	 * <li>Id Aggiudicazione
	 * <li>Data Inizio Aggiudicazione
	 * <li>Id Condizione
	 * </ul>
	 * 
	 * @param request HttpServletRequest
	 * @param gara
	 * @return List&lt;CondizioneAggBean&gt;
	 * @throws ActionException
	 */
	@SuppressWarnings("unchecked")
	public List<CondizioneLottoBean> getBeanCondizioni(HttpServletRequest request, long idLotto)
			throws ActionException {
		String mtd = "getBean";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + "START");
		ArrayList<CondizioneLottoBean> condizioni = new ArrayList<CondizioneLottoBean>();
		CondizioneLottoBean cond = null;
		String[] checked = request.getParameterValues(PSBD.FIELD_NAME_CONDIZIONI_LOTTO);
		for (int i = 0; checked != null && i < checked.length; i++) {
			cond = new CondizioneLottoBean();
			cond.setIdLotto(idLotto);
			cond.setIdCondizione(Long.parseLong(checked[i]));
			condizioni.add(cond);
			logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(cond.getClass(), cond));
		}
		logger.debug(logPrefix + "END");
		condizioni.trimToSize();
		return condizioni;

	}

	/******************************************************************************************
	 * Ottiene la lista delle condizioni impostando per ogni condizione prelevata
	 * dalla request
	 * <ul>
	 * <li>Id Aggiudicazione
	 * <li>Data Inizio Aggiudicazione
	 * <li>Id Condizione
	 * </ul>
	 * 
	 * @param request HttpServletRequest
	 * @param gara
	 * @return List&lt;CondizioneAggBean&gt;
	 * @throws ActionException
	 */
	@SuppressWarnings("unchecked")
	public List<CondizioneLottoBean> getBeanCondizioniInit(HttpServletRequest request, long idLotto, int idGara)
			throws ActionException {
		String mtd = "getBean";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		logger.debug(logPrefix + "START");
		ArrayList<CondizioneLottoBean> condizioni = new ArrayList<CondizioneLottoBean>();

		// TICKET ALM - 3.04.3 #4213
		boolean foundFromRequest = false;

		String[] checked = request.getParameterValues(PSBD.FIELD_NAME_CONDIZIONI_LOTTO);
		for (int i = 0; checked != null && i < checked.length; i++) {
			CondizioneLottoBean cond = new CondizioneLottoBean();
			cond.setIdLotto(idLotto);
			cond.setIdCondizione(Long.parseLong(checked[i]));
			condizioni.add(cond);
			logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(cond.getClass(), cond));
			foundFromRequest = true;
		}
		logger.debug(logPrefix + "END");
		condizioni.trimToSize();

		// Se dalla request non sono state ricavate condizioni, verifica se esistono
		// altri lotti
		if (!foundFromRequest && SimogFlags.is3043Active()) {
			LottoManager lm = new LottoManager(connection, logger);
			try {
				List<Lotto> list = lm.getListaLotti(idGara);
				// ricava solo il primo lotto
				Lotto primoLotto;
				if (list.size() > 0) {
					primoLotto = list.get(0);
					// Se il primo lotto e' procedura negoziata allora carica le condizioni
					if ((Costanti.COND_SPB == Integer.parseInt(primoLotto.getId_Scelta_Contraente())
							|| Costanti.COND_SPBG == Integer.parseInt(primoLotto.getId_Scelta_Contraente()))) {
						// TICKET ALM - 3.04.3 #4213
						// Qui vengono assegnate le condizioni di default
						for (CondizioneLottoBean elem : primoLotto.getCondizioni()) {
							CondizioneLottoBean cond = new CondizioneLottoBean();
							cond.setIdLotto(idLotto);
							cond.setIdCondizione(elem.getIdCondizione());
							condizioni.add(cond);
							logger.debug(logPrefix + ObjectIntrospector.propertiesInfo(cond.getClass(), cond));
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return condizioni;

	}

	private List<CpvLotto> verificaDuplicati(List<CpvLotto> listaCpvOriginale) {
		List<CpvLotto> listaCpvNoDuplicati = new ArrayList<CpvLotto>();

		// Cicla tutte le cpv
		for (CpvLotto cpvOrig : listaCpvOriginale) {
			boolean duplicate = false;

			// Cicla le cpv gia' inserite
			for (CpvLotto cpv : listaCpvNoDuplicati) {
				// Se gia' esiste una cpv con lo stesso codice, non inserirla nella nuova lista
				if (cpvOrig.getIdCpv().equals(cpv.getIdCpv())) {
					duplicate = true;
					break;
				}
			}

			if (!duplicate)
				listaCpvNoDuplicati.add(cpvOrig);

		}

		return listaCpvNoDuplicati;
	}
}
