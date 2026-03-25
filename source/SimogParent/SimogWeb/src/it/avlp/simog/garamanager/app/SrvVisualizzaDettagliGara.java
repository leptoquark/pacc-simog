package it.avlp.simog.garamanager.app;

import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avcp.simog.managers.cpv.CPVEUManager;
import it.avcp.simog.managers.invitati.InvitatiManager;
import it.avlp.simog.actions.GaraLottoAction;
import it.avlp.simog.actions.RequisitiGLWebAction;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.CpvLotto;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InvitatoBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.MessageBean;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.RequisitoGara;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.RequisitoGara.Documento;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.common.action.RequisitiGLAction;
import it.avlp.simog.common.actions.BandoGaraAction;
import it.avlp.simog.common.servlet.PSReq;
import it.avlp.simog.common.servlet.ParametriCup;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.CPV_LOTTO;
import it.avlp.simog.db.generated.EAGG_CATEGORIE;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.log.LogManager;
import it.avlp.simog.rettifica.InfoRettifica;
import it.avlp.simog.servlet.BeanUtilsServlet;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.validatore.RequisitiGLValidator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.catalina.connector.Request;

public class SrvVisualizzaDettagliGara extends BeanUtilsServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	/**
	 * @see ServletBase#perform(HttpServletRequest, HttpServletResponse)
	 */
	public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		HttpSession currentActiveSession = request.getSession();
		if (checkSession(request)) {
			String idGara = (String) getValueFromContexts(String.class, SESSION_ID_GARA, request);

			try {

				currentActiveConnection = getSimogConnection(request.getSession().getId(), getClass().getName());

				GaraManager garaManager = new GaraManager(currentActiveConnection, logger);

				TableBean dettagliGara = null;
				TableBean dettagliGaraPaginazione = null;
				TableBeanRow firstRow = null;

				// Ticket #20055
				if (currentUser.isRUP()) {
					InfoRettifica infoRettifica = new InfoRettifica();
					boolean result = infoRettifica.checkRettifica(garaManager, idGara);

					request.setAttribute(ParametriServlet.RETTIFICA_GARA_LOTTI, result);
				} else {
					request.setAttribute(ParametriServlet.RETTIFICA_GARA_LOTTI, false);
				}

				int startRow = 0;
				if (SimogFlags.is30233_RFWEBGL05Active()) {

					String action = request.getParameter(ACTION_GET_LIST);
					request.setAttribute(ACTION_GET_LIST, action);
					String fromRicerca = request.getParameter(ParametriServlet.FROM_RICERCA);
					request.setAttribute(ParametriServlet.FROM_RICERCA, fromRicerca);

					currentActiveSession.removeAttribute(ParametriCup.FROM_ELENCO_CUP);

					boolean actionNeeded = action != null;
					if (action == null)
						action = STILL;

					if (actionNeeded) {
						String startRowS = request.getParameter(START_ROW);
						startRow = Integer.parseInt(startRowS);
						if (action.equalsIgnoreCase(REGRESS)
								&& (startRow - configuration.getMaxElementiPerPagina() >= 0)) {
							startRow = startRow - configuration.getMaxElementiPerPagina();
						} else if (action.equalsIgnoreCase(PROGRESS)) {// CF aggiunto controllo if. In questo quando
																		// viene premuto il tasto
							// "EsportaElenco", la variabile startRow mantiene il valore passotogli dalla
							// jsp con valore 0.
							startRow = startRow + configuration.getMaxElementiPerPagina();
						}
					}
				}
				request.setAttribute(ParametriServlet.START_ROW, new Integer(startRow));
				request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI,
						configuration.getELEMENTI_VISUALIZZATI());

				if (currentUser.isRSSAorRUP()) {
					dettagliGara = garaManager.getDettagliGaraByIdGaraRSSA(idGara,
							"VIS".equals(request.getParameter(ParametriServlet.FROM_RICERCA)) ? new Hashtable()
									: currentUser.getUffici());
					// TICKET MAC #10467
					request.setAttribute(ParametriServlet.RUP_CREATO_GARA,
							new LogManager(currentActiveConnection, logger).getCFRUP(Long.parseLong(idGara)));

					// TICKET ALM - 3.04.3
					// Se la query non ha risultati significa che si sta aprendo una gara delegata o
					// un accordo quadro non di competenza
					request.setAttribute(ParametriServlet.IS_DELEGA, "NOK"); // Inizializza attributo
					request.setAttribute(ParametriServlet.IS_ACC_QUADRO_NC, "NOK");// Inizializza attributo
					if (!SimogFlags.is3043Active() || dettagliGara.getRowsCount() > 0) {
						dettagliGaraPaginazione = garaManager.getDettagliGaraByIdGaraNew(idGara, startRow,
								Integer.valueOf(configuration.getELEMENTI_VISUALIZZATI()),
								"VIS".equals(request.getParameter(ParametriServlet.FROM_RICERCA)) ? new Hashtable()
										: currentUser.getUffici());

						// gm nuovo codice pubblicazione bando
						BandoGaraAction bga = new BandoGaraAction(currentActiveConnection, logger, configuration);
						String garaPubblicabile = bga.garaPubblicabile(dettagliGara);
						// gm fine nuovo codice pubblicazione bando
						firstRow = dettagliGara.getRow(0);
						if (dettagliGara.getRowsCount() <= 0)
							throw new Exception("!! Nessuna riga per la query !! idGara:" + idGara);
						currentActiveSession.setAttribute(SESSION_ID_GARA, idGara);
						// Integer progressivoProssimoLotto = new Integer(dettagliGara.getFullSize());
						Integer progressivoProssimoLotto = 0; // PP new Integer(dettagliGara.getFullSize() + 1);
						// PP conto i valori univoci degli id_lotti per capire quanti ce ne sono in
						// realta'
						// la query ritorna pi&ugrave; righe per ogni lotto!
						Vector lotti = dettagliGara.getColumn(LOTTO.ID_LOTTO);
						Vector unici = new Vector();
						for (int i = 0; i < lotti.size(); i++) {
							if (!unici.contains(lotti.get(i)))
								unici.add(lotti.get(i));
						}
						// Se il primo id_lotto e' 0, la gara non ha lotti e quindi si il progressivo si
						// imposta ad 1.
						progressivoProssimoLotto = !"0".equals(firstRow.getNulledField(LOTTO.ID_LOTTO))
								? unici.size() + 1
								: 1;

						currentActiveSession.setAttribute(SESSION_NUMERO_LOTTI_CREATI, progressivoProssimoLotto);
						// CAMBIATO IL NOME DELLE VARIABILI IN SESSIONE ALTRIMENTI VANNO IN CONFLITTO
						// CON I PARAMETRI DI RICERCA SE SI USA IL TASTO DI BACK
						// gm nuovo codice pubblicazione bando
						currentActiveSession.setAttribute(ParametriServlet.GARA_PUBBLICABILE, garaPubblicabile);
						// gm fine nuovo codice pubblicazione bando
						currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_OGGETTO_GARA_1,
								firstRow.getNulledField(GARA.TABLE_NAME + GARA.OGGETTO));
						currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE_1,
								firstRow.getNulledField(GARA.ID_STAZIONE_APPALTANTE));
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_CREAZIONE_GARA_1,
								PageHelper.getFormattedDate(firstRow.getNulledField(GARA.DATA_CREAZIONE)));
						currentActiveSession.setAttribute("IDMODREAL_PER_LOTTO",
								firstRow.getNulledField(GARA.ID_MODO_REAL));
					} else {
						String cfAmmAgente = "";
						// TICKET ALM #659 - 3.04.4
						cfAmmAgente = garaManager.getCfAmmDelegata(Long.parseLong(idGara), 0,
								currentUser.getAmministrazioni());
						if (!"".equals(cfAmmAgente)) {
							dettagliGara = garaManager.getDettagliGaraByIdGara(idGara);

							dettagliGaraPaginazione = garaManager.getDettagliGaraByIdGaraNew(idGara, startRow,
									Integer.valueOf(configuration.getELEMENTI_VISUALIZZATI()), null);
							// TICKET MAC #10467
							request.setAttribute(ParametriServlet.RUP_CREATO_GARA,
									new LogManager(currentActiveConnection, logger).getCFRUP(Long.parseLong(idGara)));

							firstRow = dettagliGara.getRow(0);
							request.setAttribute(ParametriServlet.IS_DELEGA, "OK");
						} else {

							if (garaManager.isGaraAccordoQuadroNonDiCompetenza(idGara)
									|| garaManager.isGaraPostDelega(idGara, null, currentUser.getUffici())) {
								dettagliGara = garaManager.getDettagliGaraByIdGara(idGara);
								dettagliGaraPaginazione = garaManager.getDettagliGaraByIdGaraNew(idGara, startRow,
										Integer.valueOf(configuration.getELEMENTI_VISUALIZZATI()), null);
								// TICKET MAC #10467
								request.setAttribute(ParametriServlet.RUP_CREATO_GARA,
										new LogManager(currentActiveConnection, logger)
												.getCFRUP(Long.parseLong(idGara)));

								firstRow = dettagliGara.getRow(0);

								request.setAttribute(ParametriServlet.IS_ACC_QUADRO_NC, "OK");
							} else
								throw new Exception("!! Nessuna riga per la query !! idGara:" + idGara);
						}
					}
				} else {
					dettagliGara = garaManager.getDettagliGaraByIdGara(idGara);

					dettagliGaraPaginazione = garaManager.getDettagliGaraByIdGaraNew(idGara, startRow,
							Integer.valueOf(configuration.getELEMENTI_VISUALIZZATI()), null);
					// TICKET MAC #10467
					request.setAttribute(ParametriServlet.RUP_CREATO_GARA,
							new LogManager(currentActiveConnection, logger).getCFRUP(Long.parseLong(idGara)));

					firstRow = dettagliGara.getRow(0);
				}

				// gm nuovo attributo per pubblicazione adesioni in simog 3.06
				Boolean adesioneValida = null;
				adesioneValida = setAdesioneValida(dettagliGara, currentActiveConnection);

				// PP 3.02.1.6 forzo a null l'adesione per far apparire sempre la pagina di
				// pubblicazione
				if (SimogFlags.is30216Active() && adesioneValida != null && adesioneValida == true)
					adesioneValida = null;

				// PP organi costituzionali, utilizzo il flag per fare la pubblicazione senza la
				// pagina di richiesta,
				// nel caso in cui la gara sia per O.C. e non sia richiesta la pubblicità

				Gara gara = garaManager.getGara(Long.valueOf(idGara));
				request.setAttribute(ParametriServlet.IS_ORGANO,
						gara.isOrganoCost() ? Costanti.FLAG_VALORE_SI : Costanti.FLAG_VALORE_NO);

				request.setAttribute(ParametriServlet.ADESIONE_VALIDA, adesioneValida);

				boolean campoEsistente = false;
				String indiceGara = null;
				TableBeanRow currentRow = null;
				InvitatiManager invitatiManager = new InvitatiManager(currentActiveConnection, logger);
				ArrayList<InvitatoBean> listaInvitati = new ArrayList<InvitatoBean>();

				String previousGara = null;
				ArrayList<String> indiciElencoInvitati = new ArrayList<String>();
				LottoManager lm = new LottoManager(currentActiveConnection, logger);
				CPVEUManager cpvman = new CPVEUManager(currentActiveConnection, logger);
				dettagliGaraPaginazione.addColumn(CPV_LOTTO.TABLE_NAME);
				for (int rowIndex = 0; rowIndex < dettagliGaraPaginazione.getTableSize(); rowIndex++) {
					currentRow = dettagliGaraPaginazione.getRow(rowIndex);

					if (currentRow.getNulledField(LOTTO.ID_LOTTO) != null
							&& !"".equals(currentRow.getNulledField(LOTTO.ID_LOTTO))) {
						long idLotto = Long.parseLong(currentRow.getNulledField(LOTTO.ID_LOTTO));
						List<CpvLotto> cpvLottoList = lm.selectCpvLotto(idLotto);
						if (!cpvLottoList.isEmpty()) {
							String cpvHtml = "";
							for (CpvLotto cpvsec : cpvLottoList)
								cpvHtml += "<p>" + cpvsec.getIdCpv() + " " + cpvman.getCPVDesc(cpvsec.getIdCpv())
										+ "</p>";

							currentRow.addFieldValue(CPV_LOTTO.TABLE_NAME, cpvHtml);
						} else
							currentRow.addFieldValue(CPV_LOTTO.TABLE_NAME, "");
					} else
						currentRow.addFieldValue(CPV_LOTTO.TABLE_NAME, "");

					// verifica che il campo sia stato inserito correttamente
					campoEsistente = currentRow.existField(Costanti.PRESENTI_INVITATI);

					indiceGara = currentRow.getNulledField(GARA.ID_GARA);
					if (previousGara == null || !previousGara.equals(indiceGara)) {
						listaInvitati = invitatiManager.carica(Long.parseLong(indiceGara));

						if (listaInvitati != null && listaInvitati.size() > 0) {
							indiciElencoInvitati.add("SI");
						} else {
							indiciElencoInvitati.add("NO");
						}
					}
					
					// fix 40610 3.04.8
					if (currentRow.getNulledField(LOTTO.ID_DEROGA_QUALIFICAZIONE_SA) != null && !currentRow.getNulledField(LOTTO.ID_DEROGA_QUALIFICAZIONE_SA).equals("")) {
						String descrDerogaQualificazioneSA = lm.getDerogaQualificazioneSAByID(Long.parseLong(currentRow.getNulledField(LOTTO.ID_DEROGA_QUALIFICAZIONE_SA)));
						if (descrDerogaQualificazioneSA == null) {
							currentRow.addFieldValue(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_VISUAL, "");
							//request.setAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_VISUAL, "");
						}else {
							currentRow.addFieldValue(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_VISUAL, descrDerogaQualificazioneSA);
							//request.setAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_VISUAL, descrDerogaQualificazioneSA);
						}
					}else {
						currentRow.addFieldValue(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_VISUAL, "");
						//request.setAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_VISUAL, "");
					}

					previousGara = indiceGara;
				}

				// FC aggiunta colonna al tableBean che indica se la gara specificata nella riga
				// ha un lettera di invito oppure no
				if (!indiciElencoInvitati.isEmpty()) {
					dettagliGara.addColumn(Costanti.PRESENTI_INVITATI, indiciElencoInvitati.get(0));
					for (int i = 1; i < indiciElencoInvitati.size(); i++)
						currentRow.addFieldValue(Costanti.PRESENTI_INVITATI, indiciElencoInvitati.get(i));
				}

				Boolean idReal = null;
				idReal = setOkChangeIdReal(request, dettagliGara, currentActiveConnection);
				request.setAttribute(ParametriServlet.ID_REAL, idReal);
				// gm creazione della lista dello storico pubblicazioni da inserire
				// nell'apposito pannello della jsp
				List<PubblicazioneBean> storicoPubblicazioni = new ArrayList<PubblicazioneBean>();
				PubblicazioneManager pubManager = new PubblicazioneManager(currentActiveConnection, logger);
				storicoPubblicazioni = pubManager.getStoricoPubblicazioniGara(Long.parseLong(idGara));

				// PP 27.05.2016 patch per gare con allegati solo perfezionate, se non inserisco
				// un elemento fittizio non si vedranno gli allegati
				// PP 29.09.2016 non va bene le gare vecchie mandano in errore
				// if(gara.getID_STATO_GARA() == StatiScheda.CONFERMATO && (storicoPubblicazioni
				// == null || storicoPubblicazioni.size() == 0)){
				if (gara.getDATA_PERFEZIONAMENTO_BANDO() != null
						&& (storicoPubblicazioni == null || storicoPubblicazioni.size() == 0)) {
					storicoPubblicazioni = new ArrayList<PubblicazioneBean>();
					PubblicazioneBean elem = new PubblicazioneBean();
					elem.setTipoOperazione("*");
					elem.setDataInizioPubblicazione(PageHelper.parseTimeYMD(gara.getDATA_PERFEZIONAMENTO_BANDO()));
					elem.setIdPubblicazione(-gara.getId_Gara()); // valore negativo fittizio funge da flag
					storicoPubblicazioni.add(elem);
				}

				request.setAttribute(ParametriServlet.STORICO_PUBBLICAZIONI, storicoPubblicazioni);

				List<String> datiStoricoPresaInCarico = garaManager.getDatiStoriciGaraDelegata(Long.valueOf(idGara));
				request.setAttribute(ParametriServlet.DATI_STORICO_DELEGA, datiStoricoPresaInCarico);

				request.setAttribute(TABLEBEAN, dettagliGaraPaginazione);

				// request.setAttribute(TABLEBEAN, dettagliGara);
				request.setAttribute(ParametriServlet.FROM_GARE, request.getParameter(ParametriServlet.FROM_GARE));
				request.setAttribute(ParametriServlet.FROM_RICERCA,
						request.getParameter(ParametriServlet.FROM_RICERCA));

				// TICKET ALM #659 - 3.04.4
				if (currentUser.isRUP()) {
					boolean delegata = garaManager.checkPresaInCaricoDelega(gara.getId_Gara(), // Verifica se la gara e'
																								// prendibile
							gara.getID_F_DELEGATE(), currentUser.getAmministrazioniByProfilo(ProfiloEnum.RUP),
							gara.getNumeroLotti() != null ? gara.getNumeroLotti() : 0);
					request.setAttribute(ParametriServlet.HAS_AGG_CONFERMATE, delegata ? "OK" : "NOK");
				}
				// FINE TICKET ALM #659 - 3.04.4

				// TICKET ALM - 3.04.2 NG
				setTabelleUtilita(request, currentActiveConnection, firstRow.getNulledField(GARA.DATA_CREAZIONE), true,
						null
				// garaManager.isOrganoCost(gara.getCF_AMMINISTRAZIONE(),
				// gara.getData_creazione())
				);

				// FINE TICKET ALM - 3.04.2 NG
				currentActiveSession.setAttribute(SESSION_ID_GARA, idGara);

				//MEV 3.04.10 43227
				String dataScadenzaPagamentiPerf = null;
				String dataScadenzaRichiestaInvito = null;
				boolean isProcRistretta = false;
				String dataLetteraInvito = null;
				//FINE MEV 3.04.10 43227
				if (configuration.getDataRequisiti().compareTo(PageHelper.getCurrentDate()) <= 0) {

					Timestamp currentDatetime = new AccessiDB(currentActiveConnection, logger).getNow();

					// PP devo considerare la data di creazione della gara se esiste
					if (gara.getData_creazione() != null)
						currentDatetime = PageHelper.parseTimeYMD(gara.getData_creazione());

					currentActiveConnection.setAutoCommit(false);

					RequisitiGLAction requisitiGLAction = new RequisitiGLAction(currentActiveConnection, logger);
					RequisitiGLWebAction webAction = new RequisitiGLWebAction(currentActiveConnection, logger);

					String action = request.getParameter(PSReq.SRV_ACTION_NAME);

					if (PSReq.ACTION_SALVA.equalsIgnoreCase(action)) {

						List<RequisitoGara> listaRequisitiGaraWeb = webAction.getListaRequistiGaraFromRequest(request);

						webAction.aggiornaCodiceDettaglio(listaRequisitiGaraWeb, requisitiGLAction, currentDatetime);

						// @ **** is3030_RFWEBGL02Active
						boolean bloccoAVCPass = false;
						if (SimogFlags.is3030_RFWEBGL02Active()) {
							RequisitiGLValidator reqValidator = new RequisitiGLValidator(currentActiveConnection,
									logger);
							AVCPassAction avpa = new AVCPassAction(currentActiveConnection, logger, configuration);
							boolean bloccoAdd = avpa.isAVCPass(gara, null,
									AVCPassFunzioneEnum.WEB_REQUISITI_CREATE.getCodice());
							boolean bloccoMod = avpa.isAVCPass(gara, null,
									AVCPassFunzioneEnum.WEB_REQUISITI_UPDATE.getCodice());
							boolean bloccoDel = avpa.isAVCPass(gara, null,
									AVCPassFunzioneEnum.WEB_REQUISITI_DELETE.getCodice());
							boolean validoAVCPass = reqValidator.validaAVCPassPrivileges(Long.valueOf(idGara),
									listaRequisitiGaraWeb, bloccoAdd, bloccoMod, bloccoDel);
							bloccoAVCPass = !validoAVCPass;
							if (bloccoAVCPass) {
								requisitiGLAction.getEccezioni().add(reqValidator.getEccezioni());
							}
						}

						boolean esitoPositivo = false;
						if (!bloccoAVCPass) {
							esitoPositivo = requisitiGLAction.updateRequisitiGara(listaRequisitiGaraWeb,
									Long.parseLong(idGara), currentDatetime, !SimogFlags.is3028_RNFDBDT01Active());
						}

						// # **** is3030_RFWEBGL02Active

						if (esitoPositivo) {
							GaraLottoAction garalotti = new GaraLottoAction(currentActiveConnection, logger,
									configuration);
							Gara lGara = garalotti.loadGaraFromDBSenzaValidazione(request);

							/** Log aggiorna requisiti Gara */
							LogManager logManager = new LogManager(currentActiveConnection, logger);
							logManager.log(getTodayDate(), lGara.getID_STAZIONE_APPALTANTE(), currentUser.getLogin(),
									"", LogManager.MOD_REQUISITI, lGara.getCF_AMMINISTRAZIONE(), "", idGara);

							// Ricaricamento dei requisiti
							List<RequisitoGara> listaRequisitoGara = requisitiGLAction
									.getRequisitoGaraList(Long.parseLong(idGara));
							webAction.aggiornaListaDocumenti(listaRequisitoGara);
							request.setAttribute(PSReq.LISTA_REQUISITI_GARA, listaRequisitoGara);

							sendMessage(request, SIMOG_GARA_015.replace("$1", "(requisiti)"));

						} else {
							// Errori da ritornare
							if (requisitiGLAction.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR)
									.getSize() > 0)
								request.setAttribute(ERRORBEAN, requisitiGLAction.getEccezioni());
							else
								sendMessage(request, SIMOG_GARA_015.replace("$1", "(nessuna modifica necessaria)"));

							// Mantenere lo stato dei requsiti al momento del salvataggio
							request.setAttribute(PSReq.LISTA_REQUISITI_GARA, listaRequisitiGaraWeb);
						}

						request.setAttribute(PSReq.CURRENT_TAB_INDEX, "1");

					} else if (PSReq.ACTION_ELIMINA.equalsIgnoreCase(action)) {
						List<RequisitoGara> listaRequisitiGaraWeb = webAction.getListaRequistiGaraFromRequest(request);

						webAction.aggiornaCodiceDettaglio(listaRequisitiGaraWeb, requisitiGLAction, currentDatetime);

						boolean esitoPositivo = requisitiGLAction.revocaRequisitiByGara(Long.parseLong(idGara),
								!SimogFlags.is3028_RNFDBDT01Active());

						if (esitoPositivo) {
							GaraLottoAction garalotti = new GaraLottoAction(currentActiveConnection, logger,
									configuration);
							Gara lGara = garalotti.loadGaraFromDBSenzaValidazione(request);

							/** Log aggiorna requisiti Gara */
							LogManager logManager = new LogManager(currentActiveConnection, logger);
							logManager.log(getTodayDate(), lGara.getID_STAZIONE_APPALTANTE(), currentUser.getLogin(),
									"", LogManager.MOD_REQUISITI, lGara.getCF_AMMINISTRAZIONE(), "", idGara);

							// Ricaricamento dei requisiti
							List<RequisitoGara> listaRequisitoGara = requisitiGLAction
									.getRequisitoGaraList(Long.parseLong(idGara));
							webAction.aggiornaListaDocumenti(listaRequisitoGara);
							request.setAttribute(PSReq.LISTA_REQUISITI_GARA, listaRequisitoGara);

							sendMessage(request, SIMOG_GARA_015.replace("$1", "(eliminazione requisiti)"));

						} else {
							// Errori da ritornare
							if (requisitiGLAction.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR)
									.getSize() > 0)
								request.setAttribute(ERRORBEAN, requisitiGLAction.getEccezioni());
							else
								sendMessage(request, SIMOG_GARA_015.replace("$1", "(nessuna modifica necessaria)"));

							// Mantenere lo stato dei requsiti al momento del salvataggio
							// request.setAttribute(PSReq.LISTA_REQUISITI_GARA, listaRequisitiGaraWeb);
						}

						request.setAttribute(PSReq.CURRENT_TAB_INDEX, "1");
					} else {

						List<RequisitoGara> listaRequisitoGara = requisitiGLAction
								.getRequisitoGaraList(Long.parseLong(idGara));
						webAction.aggiornaListaDocumenti(listaRequisitoGara);

						boolean okRequisiti = true; // per default abilitata gestione
						if (SimogFlags.is3028_RFWEBGL01Active()) {
							RequisitiGLValidator rqvl = new RequisitiGLValidator(currentActiveConnection, logger);
							rqvl.validaRequisitoOE(rqvl.getImportoGara(dettagliGara), gara.getData_creazione());

							okRequisiti = rqvl.getEsito().isRequisiti();
						}

						if (currentUser.isRUP() && !"VIS".equals(request.getParameter(ParametriServlet.FROM_RICERCA))
								&& listaRequisitoGara.isEmpty() && okRequisiti
								&& gara.getDATA_PERFEZIONAMENTO_BANDO() == null) {
							listaRequisitoGara
									.addAll(requisitiGLAction.getRequisitoObbligatorioGaraList(currentDatetime));
							webAction.aggiornaListaDocumenti(listaRequisitoGara);

							// devo verificare se esistono già messaggi
							String mesg = "Sono stati preimpostati i requisiti di partecipazione di ordine generale, confermarli nella sezione 'Gestione dei requisiti'";

							/* UN Sostituzione con sistema AllValidationBean */
//                        if(request.getAttribute(ParametriServlet.ERRORBEAN) != null){
//                           mesg = ((MessageBean) request.getAttribute(ParametriServlet.ERRORBEAN)).getMessage() + "<br>" + mesg; 
//                        }
//                                                
							if (request.getAttribute(ParametriServlet.ERRORBEAN) != null) {
								Object errorBean = request.getAttribute(ParametriServlet.ERRORBEAN);
								if (errorBean instanceof AllValidationBeans) {
									((AllValidationBeans) errorBean).addValidationInfo(mesg);
									request.setAttribute(ERRORBEAN, errorBean);
								} else {
									mesg = ((MessageBean) request.getAttribute(ParametriServlet.ERRORBEAN)).getMessage()
											+ "<br>" + mesg;
								}
							} else
								sendMessage(request, mesg);
						}
						request.setAttribute(PSReq.LISTA_REQUISITI_GARA, listaRequisitoGara);

					}

					currentActiveConnection.commit();

					Map<String, String> requisitiMap = requisitiGLAction.getRequisitiFacoltativiMap(currentDatetime);

					if (SimogFlags.is3028_RFWEBGL05Active()) {
						Map<String, TreeMap<String, String>> requisitiPerTipologiaMap = requisitiGLAction
								.getRequisitiPerTipologiaMap(requisitiMap, currentDatetime);
						request.setAttribute(PSReq.MAPPA_REQUISITI_PER_TIPOLOGIA, requisitiPerTipologiaMap);
					}

					Map<String, String> requisitiOBMap = requisitiGLAction.getRequisitiObbligatoriMap(currentDatetime);
					Map<String, String> requisitiUsoMap = requisitiGLAction
							.getRequisitiFacoltativiUsoMap(currentDatetime);
					List<Lotto> listaLotti = requisitiGLAction.getLottoList(Long.parseLong(idGara));

					// TICKET ALM - 3.04.4
					request.setAttribute(PSReq.INFO_COMUNI_I_FASE, "OK");
					for (Lotto lotto : listaLotti) {
						InfoComuniManager icm = new InfoComuniManager(currentActiveConnection, logger);
						if (icm.checkDatiComuni(new InfoComuniBean(), lotto.getCIG())) {
							request.setAttribute(PSReq.INFO_COMUNI_I_FASE, "NOK");
							break;
						}
						//MEV 3.04.10 43227
						dataScadenzaPagamentiPerf = lotto.getDATA_SCADENZA_PAGAMENTI();
						dataScadenzaRichiestaInvito = lotto.getDataScadenzaRichiestaInvito();
						dataLetteraInvito = lotto.getDataLetteraInvito();
						String codiciProceduraRistretta = configuration.getCodiciProceduraRistretta();
						boolean proceduraRistretta = false;
						if (codiciProceduraRistretta != null && !"".equals(codiciProceduraRistretta)) {
							LottoManager lottoManager = new LottoManager(currentActiveConnection, logger);
							codiciProceduraRistretta = codiciProceduraRistretta.replaceAll(Costanti.FLAG_PROCEDURA_NEGOZIATA,
									"");// non ci interessa la flag "x"
							proceduraRistretta = lottoManager.isProceduraRistretta(gara.getId_Gara(), codiciProceduraRistretta);
						} else {
							throw new SimogException(
									"Parametro[" + SimogProperties.CODICI_PROCEDURA_RISTRETTA + "] mancante in simog.ini");
						}
						//FINE MEV 3.04.10 43227
					}
					// FINE TICKET ALM - 3.04.4

					/* MAPPA DEI DOCUMENTI OBBLIGATORI PER REQUISITO SELEZIONABILE */

					Map<String, String> mappaCodiceDocumenti = new TreeMap<String, String>();
					for (String codiceDettaglioRequisito : requisitiMap.keySet()) {
						List<Documento> listaDocumentiOB = requisitiGLAction
								.getDocumentiObbligatoriList(codiceDettaglioRequisito, currentDatetime);
						String listaDocumentiString = webAction.convertToListaDocumentiString(listaDocumentiOB);
						mappaCodiceDocumenti.put(codiceDettaglioRequisito, listaDocumentiString);
					}

					/* FINE */

					request.setAttribute(PSReq.MAPPA_REQUISITI, requisitiMap);
					request.setAttribute(PSReq.MAPPA_REQUISITI_OB, requisitiOBMap);
					request.setAttribute(PSReq.MAPPA_REQ_F_USO, requisitiUsoMap);
					request.setAttribute(PSReq.MAPPA_REQ_DOC_OB, mappaCodiceDocumenti);
					request.setAttribute(PSReq.LISTA_LOTTI, listaLotti);

					request.setAttribute(PSReq.SIMOG_PROPERTIES, configuration);

					// verifico se posso modificare i dati (blocco avcpass)
					request.setAttribute(PSReq.BLOCCO_AVCPASS, new Boolean(false));
					if (!SimogFlags.is3030_RFWEBGL02Active()) { // esclude is3028_RFWEBGL07Active
						if (SimogFlags.is3028_RFWEBGL07Active()) {
							List<RequisitoGara> lista = (List<RequisitoGara>) request
									.getAttribute(PSReq.LISTA_REQUISITI_GARA);
							// se la gara rientra nel controllo avcpass
							if (lista != null && !lista.isEmpty()) {
								// richiamo il servizio AVCPASS
								AVCPassAction avpa = new AVCPassAction(currentActiveConnection, logger, configuration);
								Boolean blocco = avpa.isAVCPass(gara, null, null);
								request.setAttribute(PSReq.BLOCCO_AVCPASS, blocco);
							}
						}
					}

				} // SimogFlags.is3025_REQUISITIActive()

				String codiciProceduraRistretta = configuration.getCodiciProceduraRistretta();
				boolean proceduraRistretta = false;
				if (codiciProceduraRistretta != null && !"".equals(codiciProceduraRistretta)) {
					LottoManager lottoManager = new LottoManager(currentActiveConnection, logger);
					codiciProceduraRistretta = codiciProceduraRistretta.replaceAll(Costanti.FLAG_PROCEDURA_NEGOZIATA,
							"");// non ci interessa la flag "x"
					proceduraRistretta = lottoManager.isProceduraRistretta(gara.getId_Gara(), codiciProceduraRistretta);
				} else {
					throw new SimogException(
							"Parametro[" + SimogProperties.CODICI_PROCEDURA_RISTRETTA + "] mancante in simog.ini");
				}
				request.setAttribute("flgProceduraRistretta", proceduraRistretta);

				// reperisco le descrizioni delle categorie associate alla gara

				AccessiDB adb = new AccessiDB(currentActiveConnection, logger);
				String[] descrizioni = new String[gara.getCatMerc().size()];
				int i = 0;
				for (Iterator<String> iterator = gara.getCatMerc().iterator(); iterator.hasNext();) {
					String elem = (String) iterator.next();
					String desc = "";
					Map<String, String> ret = adb.getTipologica(EAGG_CATEGORIE.TABLE_NAME, EAGG_CATEGORIE.COD_CATEGORIA,
							EAGG_CATEGORIE.DESCRIZIONE, EAGG_CATEGORIE.DATA_INIZIO_VALIDITA,
							EAGG_CATEGORIE.DATA_FINE_VALIDITA, PageHelper.parseTimeYMD(gara.getData_creazione()));

					descrizioni[i++] = ret.get(elem);
				}
				request.setAttribute(ParametriServlet.EAGG_CATEGSEL_BEAN, descrizioni);
				
				// fix 34470 3.04.8
				String linkAffidamentoDiretto = gara.getLINK_AFFIDAMENTO_DIRETTO();
				if (linkAffidamentoDiretto == null) {
					request.setAttribute(ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO, "");
				}else {
					request.setAttribute(ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO, linkAffidamentoDiretto);
				}
				
				
				
				
				//MEV 37010 3.04.8.1
				//mostrare pulsante integra dati pari opportunita
				String currentDate = PageHelper.getCurrentDate();
				//SE LA MEV è ATTIVATA 
				String mostraIntegraPariOpportunita = "false";
				if (currentDate.compareTo(SimogProperties.getInstance().getDataAttivazioneMev37010()) >= 0) {
					
					// MEV 46937 3.04.12
//					if (gara.getID_MODO_REAL() == Costanti.MODOREAL_ACCORDO_QUADRO ||
//							gara.getID_MODO_REAL() == Costanti.MODOREAL_CONVENZIONE ||
//							gara.getID_MODO_REAL() == Costanti.MODOREAL_ACCORDO) {
					// FINE MEV 46937 3.04.12
//						if (gara.getDataInizioPubblicazione() != null && SimogProperties.getInstance()
//								.isDataCreatedAfterDerogaAdesione(PageHelper.getFormattedDBDateTime(gara.getDataInizioPubblicazione().toString()).substring(0,8)) 
//								&&
//								SimogProperties.getInstance()
//								.isDataCreatedBefore3047(PageHelper.getFormattedDBDateTime(gara.getDataInizioPubblicazione().toString()).substring(0,8))
//								) 
						if(gara.getData_creazione() != null && !gara.getData_creazione().equals("") && isDataCreatedBefore3047(gara.getData_creazione())){

							mostraIntegraPariOpportunita = "true";
						}
					
					//}
					
				}
				request.setAttribute(ParametriServlet.MOSTRA_INTEGRA_PARI_OPPORTUNITA, mostraIntegraPariOpportunita);
				
				// FINE MEV 37010 3.04.8.1
				
				//MEV 3.04.10 43227
				//mostrare pulsante modifica dati perfezionamento
				
				String mostraModificaDatiPerfezionamento = "false"; 
				
				//se proc ristretta e fase 2 
				if ((dataScadenzaPagamentiPerf != null && !"".equals(dataScadenzaPagamentiPerf))
						&&(proceduraRistretta)
						&& (dataScadenzaRichiestaInvito != null && !"".equals(dataScadenzaRichiestaInvito))
						&& (dataLetteraInvito != null && !"".equals(dataLetteraInvito))) {
					//il pulsante viene mostrato solo se la data odierna è precedente o uguale alla data di scadenza pagamenti
					if (dataScadenzaPagamentiPerf != null && 
							currentDate.compareTo(dataScadenzaPagamentiPerf) <= 0) {
						mostraModificaDatiPerfezionamento = "true";
					}
					//se proc ristretta fase 1
				}else if ((dataScadenzaRichiestaInvito != null && !"".equals(dataScadenzaRichiestaInvito))
						&& (proceduraRistretta)) {
					//il pulsante viene mostrato solo se la data odierna è precedente o uguale alla data di scadenza richiesta invito
					if (dataScadenzaRichiestaInvito != null && 
							currentDate.compareTo(dataScadenzaRichiestaInvito) <= 0) {
						mostraModificaDatiPerfezionamento = "true";
					}
				// se caso normale controllo la data scadenza pagamenti
				}else if (dataScadenzaPagamentiPerf != null && !"".equals(dataScadenzaPagamentiPerf)) {
					if (dataScadenzaPagamentiPerf != null && 
							currentDate.compareTo(dataScadenzaPagamentiPerf) <= 0) {
						mostraModificaDatiPerfezionamento = "true";
					}
				}
					
					
				
				request.setAttribute(ParametriServlet.MOSTRA_MODIFICA_DATI_PERFEZIONAMENTO, mostraModificaDatiPerfezionamento);
				
				// FINE MEV 3.04.10 43227
				
				//MEV 53643 3.04.13
				//mostrare pulsante integra cpv
				
				String mostraModificaCPV = "true";
				request.setAttribute(ParametriServlet.MOSTRA_MODIFICA_CPV, mostraModificaCPV);			
				
				// FINE MEV 37010 3.04.8.1
				
				//- MAD 68089 3.04.16 Inizio
				//- Setto la variabile per un eventuale disattivazione del pulsante
				request.setAttribute(ParametriServlet.MOSTRA_MODIFICA_CAT_SOA, "true");
				//- MAD 68089 3.04.16 Fine
				
				forward(JSP_VISUALIZZA_DETTAGLIO_GARA, request, response);
			} catch (Exception sqle) {
				sqle.printStackTrace();
				rollback(currentActiveConnection);
				sendError(request, response, SIMOG_GARA_005, JSP_ERRORE, sqle);
				return;
			} finally {
				closeConnection(request.getSession().getId(), getClass().getName());
			}
		} else {
			sendError(request, response, Messaggi.SIMOG_GARA_005, JSP_ERRORE);
			return;
		}
	}

//   private void setAVCPassFunctionRequest(HttpServletRequest request, AVCPassAction avpa, Gara gara) throws Exception {
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_MODIFICA_LOTTO, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_LOTTO_UPDATE.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_PERFEZIONA_LOTTO, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_LOTTO_PERFEZIONA.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_CANCELLA_LOTTO, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_LOTTO_DELETE.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_RIPRISTINO_LOTTO, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_RIPRISTINO_LOTTI_CANCELLATI.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_MOD_CONTRATTO_ESCLUSO, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_CONTRATTO_ESCLUSO_UPDATE.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_MOD_DATI_RIPETIZIONI, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_DATI_RIPETIZIONI_UPDATE.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_MODIFICA_GARA, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_GARA_UPDATE.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_PUBBLICA_RETTIFICA, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_PUBBLICA_RETTIFICA.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_SBLOCCA_GARA, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_GARA_SBLOCCA.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_ELENCO_INVITATI, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_GESTIONE_ELENCO_INVITATI.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_MOV_ACCORDO_QUADRO, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_MOV_TO_ACCORDO_QUADRO.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_VARIAZIONE_SA, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_VARIAZIONE_SA.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_PRESAINCARICO_GARA, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_GARA_PRESA_IN_CARICO.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_CANCELLA_GARA, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_GARA_DELETE.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_RIPRISTINA_GARA, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_GARA_RIPRISTINA.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_AGGIUNGI_LOTTO, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_LOTTO_CREATE.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_PERF_PROCEDURA_RISTRETTA , new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_LOTTO_PERF_PROC_RISTRETTA.getCodice())));
//      request.setAttribute(ParametriServlet.BLOCCO_AVCPASS_CONFERMA_RETTIFICA, new Boolean(avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_CONFERMA_RETTIFICA.getCodice())));
//   }

	private Boolean setOkChangeIdReal(HttpServletRequest request, TableBean dettagliGara,
			Connection currentActiveConnection) {
		Boolean idReal = true;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		boolean pubblicata_gara = false;
		String modoRealizzazioneGara;
		String idModoReal;

		if (currentUser.isAmministratore()) {

			if (dettagliGara != null) {
				pubblicata_gara = (!"".equals(dettagliGara.getRow(0).getNulledField(GARA.DATA_PERFEZIONAMENTO_BANDO)));
				modoRealizzazioneGara = Integer.toString(Costanti.MODOREAL_ACCORDO);
				idModoReal = dettagliGara.getRow(0).getNulledField(GARA.ID_MODO_REAL);
				if (pubblicata_gara && !idModoReal.equals(modoRealizzazioneGara)) {
					for (int rowIndex = 0; rowIndex < dettagliGara.getTableSize(); rowIndex++) {
						TableBeanRow currentRow = dettagliGara.getRow(rowIndex);
						if (currentRow != null) {
							String cig = currentRow.getNulledField(LOTTO.CIG);
							if (cig != null && !"".equals(cig)) {
								String cig_kkk = currentRow.getNulledField(LOTTO.CIG_KKK);
								String fullCig = cig + cig_kkk;
								AggiudicazioniManager am = new AggiudicazioniManager(currentActiveConnection, logger);
								List<AggiudicazioneBean> aggiudicazioni = new ArrayList<AggiudicazioneBean>();
								try {
									aggiudicazioni = am.getAggiudicazioniByCIG(fullCig);
									if (aggiudicazioni.size() > 0) {
										idReal = false;
										break;
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

							}
						}
					}
				} else {
					idReal = false;
				}
			} else {
				idReal = false;
			}

		}
		return idReal;
	}

	/*
	 * Metodo per verificare se il tableBean dettagliGara
	 * 
	 * 
	 */
	private Boolean setAdesioneValida(TableBean dettagliGara, Connection currentActiveConnection) {
		Boolean adesioneValida = null;

		if (dettagliGara != null) {
			int idModoReal = 0;
			try {
				idModoReal = Integer.parseInt(dettagliGara.getRow(0).getNulledField(GARA.ID_MODO_REAL));
			} catch (NumberFormatException e) {
			}

			int numLottiGara = 0;
			try {
				numLottiGara = Integer.parseInt(dettagliGara.getRow(0).getNulledField(GARA.NUMERO_LOTTI));
			} catch (NumberFormatException e) {
			}
			int numeroLotti = 0;
			// per le gare la cui modalità di realizzazione non è adesione il campo
			// adesioneValida sarà null
			//3.04.8 34190 fix
			if (Costanti.MODOREAL_ADESIONE_NOCOMPET != idModoReal && Costanti.MODOREAL_ADESIONE != idModoReal && Costanti.MODOREAL_CONCESSIONE_NOCOMPET != idModoReal && Costanti.MODOREAL_CONCESSIONE != idModoReal)
				adesioneValida = null;
			// per le altre il campo adesioneValida sarà temporaneamente true
			else {
				adesioneValida = true;

				for (int rowIndex = 0; rowIndex < dettagliGara.getTableSize(); rowIndex++) {
					TableBeanRow currentRow = dettagliGara.getRow(rowIndex);
					if (currentRow != null) {
						String idLotto = currentRow.getNulledField(LOTTO.ID_LOTTO);
						// il confronto con "0" è necessario per le gare senza lotti
						if (!"".equals(idLotto) && !"0".equals(idLotto)) {
							String dataCancellazione = currentRow.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO);
							String dataInibizione = currentRow.getNulledField(LOTTO.DATA_INIB_PAGAMENTO);
							int sceltaContraente = Integer
									.parseInt(currentRow.getNulledField(LOTTO.ID_SCELTA_CONTRAENTE));

							// ricavo la scelta contraente equivalente
							long sceltaEquiv = sceltaContraente;
//	         	         if(SimogFlags.is3028_RFWEBGL00Active()){
//	         	            LottoManager lm = new LottoManager(currentActiveConnection, logger);
//	         	            try {
//	         	               sceltaEquiv = Long.valueOf(lm.getSceltaContraenteAVCP(null, sceltaEquiv));
//	         	            } catch (NumberFormatException e) {
//	         	               // TODO Auto-generated catch block
//	         	               e.printStackTrace();
//	         	            } catch (SQLException e) {
//	         	               // TODO Auto-generated catch block
//	         	               e.printStackTrace();
//	         	            } catch (Exception e) {
//	         	               // TODO Auto-generated catch block
//	         	               e.printStackTrace();
//	         	            }
//	         	         }

							if ((dataCancellazione == null || "".equals(dataCancellazione))
									&& (dataInibizione == null || "".equals(dataInibizione))) {
								// se almeno un lotto ha scelta contraente diversa da quella attesa, il campo
								// adesioneValida diventa false
								//3.04.8 34190 fix
								if ((Costanti.MODOREAL_ADESIONE_NOCOMPET == idModoReal || Costanti.MODOREAL_CONCESSIONE_NOCOMPET == idModoReal)
										&& Costanti.AFF_DIR_ADESIONE != sceltaEquiv)
									adesioneValida = false;
								if ((Costanti.MODOREAL_ADESIONE == idModoReal || Costanti.MODOREAL_CONCESSIONE == idModoReal)
										&& Costanti.CON_COM_ADESIONE != sceltaEquiv)
									adesioneValida = false;

								numeroLotti++;
							}
						}
						// per le gare senza lotti, il campo adesioneValida diventa null
						else
							adesioneValida = null;
					}
				}
				// se il numero dei lotti è diverso da quello dichiarato nella gara
				if (numeroLotti != numLottiGara)
					adesioneValida = false;
			}
		}
		return adesioneValida;
	}
	
	//se la data è precedente al rilascio della 3.04.7 (26/07/2022)
	public boolean isDataCreatedBefore3047(String data) {
		int anno = Integer.parseInt(data.substring(0, 4));
//		logger.info("dentro isDataCreatedBefore3047");
		if (anno < 2022) {
//			logger.info("dentro primo if");
			return true;
		}else if (anno > 2022) {
//			logger.info("dentro secondo if");
			return false;
		}else {
//			logger.info("dentro terzo if");
			int mese = Integer.parseInt(data.substring(4, 6));
			if (mese < 7) {
//				logger.info("dentro quarto if");
				return true;
			}else if (mese > 7) {
//				logger.info("dentro quinto if");
				return false;
			}else {
//				logger.info("dentro sesto if");
				int giorno = Integer.parseInt(data.substring(data.length() - 2));
				if (giorno < 26) {
//					logger.info("dentro settimo if");
					return true;
				}else if (mese > 26) {
//					logger.info("dentro ottavo if");
					return false;
				}else {
//					logger.info("dentro nono if");
					return false;
				}
			}
		}
		
	}
}