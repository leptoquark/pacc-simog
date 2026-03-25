package it.avlp.simog.garamanager.lotto.app;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.avlp.simog.actions.GaraLottoAction;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.CpvLotto;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.common.action.CupLottoAggAction;
import it.avlp.simog.common.contributo.GestioneContributoWrapperBeanClient;
import it.avlp.simog.common.contributo.ParametriContributo;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriCup;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.LOTTO_MOTIVO_DEROGA;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.log.LogManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.LottoValidator;
import it.mef.serviziCUP.ElaborazioniCUPClient;
import it.avcp.simog.managers.aggiudicazione.MotivoDerogaManager;
import it.avcp.simog.managers.aggiudicazione.MisuraPremialeManager;

public class SrvModificaLotto extends ServletBase {

	private static final long serialVersionUID = -4427708044851647738L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		perform(request, response);
	}

	public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		//MAC 36255 3.04.8 aggiunto motivoEsclusioneGara
		String motivoEsclusioneGara = null;
		if (checkSession(request)) {
			if (currentUser.isRSSAorRUP() || currentUser.isAmministratore()) {

				visualizzaListaParametriValori(request, response);

				logger.debug("Utente abilitato alla modifica di lotti");
				try {
					currentActiveConnection = getSimogConnection(request.getSession().getId(), getClass().getName());
					currentActiveConnection.setAutoCommit(false);
					/** NEW IMPLEMENTS */
					long idLotto = Long.parseLong(request.getParameter(FIELD_NAME_ID_LOTTO));

					GaraLottoAction gla = new GaraLottoAction(currentActiveConnection, logger, configuration);

					// gm nuovo codice simog 3.04
					LottoManager lottoManager = new LottoManager(currentActiveConnection, logger);
					GestioneContributoWrapperBeanClient gcwbc = new GestioneContributoWrapperBeanClient(
							configuration.getContributoUrl(), currentActiveConnection, logger);

					String action = request.getParameter(ParametriServlet.ACTION);

					if (action.equals(ParametriServlet.ACTION_MODIFICA_RIPETIZIONI)) {
						String requestingURL = "/gestisciLotto?action=" + ParametriServlet.ACTION_MODIFICA_RIPETIZIONI
								+ "&" + FIELD_NAME_ID_LOTTO + "=" + idLotto;

						// in questo caso carico il lotto da db ed aggiungo i valori delle ripetizioni

						String flagPrevRip = request.getParameter(ParametriServlet.FIELD_FLAG_PREVEDE_RIP);
						String flagRipetiz = request.getParameter(ParametriServlet.FIELD_FLAG_RIPETIZIONE);
						String cigRipetiz = request.getParameter(ParametriServlet.FIELD_CIG_ORIGINE_RIP);
						String flagCup = request.getParameter(ParametriCup.FIELD_FLAG_CUP);
						String flagPnrrPnc = request.getParameter(ParametriServlet.FLAG_PNRR_PNC);
						String flagDerogaAdesione = request.getParameter(ParametriServlet.FLAG_DEROGA_ADESIONE); //MEV 37010 3.04.8.1
						String flagPrevisioneQuota = request.getParameter(ParametriServlet.FLAG_PREVISIONE_QUOTA);
						String flagMisurePremiali = request.getParameter(ParametriServlet.FLAG_MISURE_PREMIALI);
						String quotaGiovanile = request.getParameter(ParametriServlet.QUOTA_GIOVANILE);
						String quotaFemminile = request.getParameter(ParametriServlet.QUOTA_FEMMINILE);

						Object selMotivoDeroga = request
								.getParameter(ParametriServlet.MOTIVO_DEROGA_SELECTED_TABLEBEAN);

						// Ticket #20058 - 09 - 02 - 21
						int durataRinnoviRipetizioni = 0;
						try {
							durataRinnoviRipetizioni = Integer.parseInt(
									request.getParameter(ParametriServlet.FIELD_NAME_DURATA_RINNOVI_RIPETIZIONI));
						} catch (NumberFormatException e) {

							System.out.println(
									"durataRinnoviRipetizioni nessun valore valorizzato dall'utente. Valore di default 0");
						}

						// TICKET ALM - 3.04.3
						String idMotivoColl = request.getParameter(ParametriServlet.FIELD_NAME_MOTIVO);

						// TODO: METTERE LA CHIAMATA SQL ALLA LISTA DA PRESELEZIONARE
						String idsMotivoDeroga = "0";
						// Creo una stringa con la lista degli id da selezionare
						for (int i = 1; i < 6; i++) {
							idsMotivoDeroga += ";" + i;
						}

						TableBean infoLotto = null;
						infoLotto = lottoManager.getLottoByIdLottoRSSA(idLotto, currentUser.getUffici());
						infoLotto.setField(LOTTO.FLAG_PREVEDE_RIP, flagPrevRip);
						infoLotto.setField(LOTTO.FLAG_RIPETIZIONE, flagRipetiz);
						infoLotto.setField(LOTTO.CIG_ORIGINE_RIP, cigRipetiz);
						infoLotto.setField(LOTTO.FLAG_CUP, flagCup);
						infoLotto.setField(LOTTO.FLAG_PNRR_PNC, flagPnrrPnc);
						infoLotto.setField(LOTTO.FLAG_DEROGA_ADESIONE, flagDerogaAdesione); //MEV 37010 3.04.8.1
						infoLotto.setField(LOTTO.FLAG_PREVISIONE_QUOTA, flagPrevisioneQuota);
						infoLotto.setField(LOTTO.FLAG_MISURE_PREMIALI, flagMisurePremiali);
						infoLotto.setField(LOTTO.QUOTA_FEMMINILE, quotaFemminile);
						infoLotto.setField(LOTTO.QUOTA_GIOVANILE, quotaGiovanile);
						infoLotto.setField(LOTTO_MOTIVO_DEROGA.ID_MOTIVO_DEROGA, idsMotivoDeroga);
						// Ticket #20058 - 09 - 02 - 21

						infoLotto.setField(LOTTO.DURATA_RINNOVI_RIPETIZIONI, String.valueOf(durataRinnoviRipetizioni));

						// TICKET ALM - 3.04.3
						infoLotto.setField(LOTTO.ID_MOTIVO, idMotivoColl);

						Lotto lotto = new Lotto();
						lotto.setId_Lotto(idLotto);
						lotto.setCig(infoLotto.getNulledField(LOTTO.CIG, 0));
						lotto.setCig_cicle(Integer.valueOf(infoLotto.getNulledField(LOTTO.CIG_CICLE, 0)).intValue());
						lotto.setCig_kkk(infoLotto.getNulledField(LOTTO.CIG_KKK, 0));
						lotto.setFLAG_PREVEDE_RIP(flagPrevRip);
						lotto.setFLAG_RIPETIZIONE(flagRipetiz);
						lotto.setCIG_ORIGINE_RIP(cigRipetiz);
						lotto.setFLAG_CUP(flagCup);
						lotto.setFLAG_PNRR_PNC(flagPnrrPnc);
						lotto.setFLAG_DEROGA_ADESIONE(flagDerogaAdesione); //MEV 37010 3.04.8.1
						lotto.setFLAG_PREVISIONE_QUOTA(flagPrevisioneQuota);
						lotto.setFLAG_MISURE_PREMIALI(flagMisurePremiali);

						if (quotaGiovanile != null) {
							// Double tempQuotaDouble = Double.valueOf(quotaGiov.replace(',', '.'));
							BigDecimal tempQuota = new BigDecimal(quotaGiovanile.replace(',', '.'));
							lotto.setQuotaGiovanile(tempQuota);
						}

						if (quotaFemminile != null) {
							// Double tempQuotaDouble = Double.valueOf(quotaFem.replace(',', '.'));
							BigDecimal tempQuota = new BigDecimal(quotaFemminile.replace(',', '.'));
							lotto.setQuotaFemminile(tempQuota);
						}

						lotto.setID_MOTIVO_COLL_CIG(idMotivoColl);// TICKET ALM - 3.04.3
						// Ticket #20058 - 09 - 02 - 21

						lotto.setId_Gara(Long.parseLong(request.getParameter(SESSION_ID_GARA)));
						lotto.setDurataRipetizioni(durataRinnoviRipetizioni);

						// lotto.setSelMotiviDeroga(idsMotivoDeroga);

						LottoValidator lv = new LottoValidator(currentActiveConnection, logger);
						lv.valida(lotto, ParametriServlet.ACTION_MODIFICA_RIPETIZIONI);

						request.setAttribute(TABLEBEAN, infoLotto);
						request.setAttribute(ParametriServlet.ACTION, action);

						if (lv.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0) {

							// messaggi da ritornare
							AllValidationBeans msgs = new AllValidationBeans();

							String idGara = request.getParameter(SESSION_ID_GARA);
							GaraManager gm = new GaraManager(currentActiveConnection, logger);
							Gara gara = gm.getGara(Long.parseLong(idGara));

							Lotto lottoDb = lottoManager.getLotto(idLotto);

							lottoDb.setFLAG_PREVEDE_RIP(flagPrevRip);
							lottoDb.setFLAG_RIPETIZIONE(flagRipetiz);
							lottoDb.setCIG_ORIGINE_RIP(cigRipetiz);
							// Ticket #20058 - 09 - 02 - 21
							lottoDb.setDurataRipetizioni(durataRinnoviRipetizioni);

							// TICKET ALM - 3.04.3
							lottoDb.setID_MOTIVO_COLL_CIG(idMotivoColl);

							lottoManager.updateRipetizioni(lotto);

							// non log amministrativo
							String completeCIG = request.getParameter(FIELD_NAME_CIG);
							String cfSARiferimento = request
									.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
							String cfAmministrazione = request
									.getParameter(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
							String cfUtente = currentUser.getLogin();

							LogManager logManager = new LogManager(currentActiveConnection, logger);
							logManager.log(getTodayDate(), cfSARiferimento, cfUtente, completeCIG,
									LogManager.MOD_RIPETIZ, cfAmministrazione, Long.toString(idLotto), idGara);

							/** commit */
							commit(currentActiveConnection);

							String forwardPath = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara + "&"
									+ ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;

							msgs.addValidationInfo(Messaggi.SIMOG_LOTTO_012);

							sendValidations(request, response, msgs, forwardPath);
							return;
						} else {
							rollback(currentActiveConnection);
							sendValidations(request, response, lv.getEccezioni(), requestingURL);
							return;
						}
					} else if (action.equals(ParametriServlet.ACTION_MODIFICA_CONTRATTO_ESCLUSO)) {
						String requestingURL = "/gestisciLotto?action="
								+ ParametriServlet.ACTION_MODIFICA_CONTRATTO_ESCLUSO + "&" + FIELD_NAME_ID_LOTTO + "="
								+ idLotto;

						// in questo caso carico il lotto da db ed aggiungo i valori contratto escluso

						String flagEscluso = request.getParameter(ParametriServlet.FIELD_NAME_ESCLUSO);
						int idEsclusione = Integer
								.parseInt("0" + request.getParameter(ParametriServlet.FIELD_NAME_ID_ESCLUSIONE));

						// TICKET ALM - 3.04.2 2805
						String flagRegime = request.getParameter(ParametriServlet.FIELD_FLAG_REGIME);
						int idArtRegime = request.getParameter(ParametriServlet.FIELD_NAME_ART_REGIME) == null
								|| "".equals(request.getParameter(ParametriServlet.FIELD_NAME_ART_REGIME)) ? 0
										: Integer.parseInt(
												"0" + request.getParameter(ParametriServlet.FIELD_NAME_ART_REGIME));

						TableBean infoLotto = null;
						infoLotto = lottoManager.getLottoByIdLottoRSSA(idLotto, currentUser.getUffici());
						infoLotto.setField(LOTTO.FLAG_ESCLUSO, flagEscluso);
						infoLotto.setField(LOTTO.ID_ESCLUSIONE, String.valueOf(idEsclusione));
						infoLotto.setField(LOTTO.FLAG_REGIME, flagRegime);

						Lotto lotto = new Lotto();
						lotto.setId_Lotto(idLotto);
						lotto.setFLAG_ESCLUSO(flagEscluso);
						lotto.setID_ESCLUSIONE(idEsclusione);

						// TICKET ALM - 3.04.2 2805
						lotto.set_FLAG_REGIME(flagRegime);
						lotto.setID_ART_REGIME(idArtRegime);
						lotto.setId_Gara(Long.parseLong(request.getParameter(SESSION_ID_GARA)));
						lotto.setFLAG_RIPETIZIONE(lottoManager.getValueField(LOTTO.FLAG_RIPETIZIONE, idLotto));
						lotto.setID_MOTIVO_COLL_CIG(lottoManager.getValueField(LOTTO.ID_MOTIVO, idLotto));
						LottoValidator lv = new LottoValidator(currentActiveConnection, logger);
						lv.valida(lotto, ParametriServlet.ACTION_MODIFICA_CONTRATTO_ESCLUSO);

						request.setAttribute(TABLEBEAN, infoLotto);
						request.setAttribute(ParametriServlet.ACTION, action);

						if (lv.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0) {

							// messaggi da ritornare
							AllValidationBeans msgs = new AllValidationBeans();

							String idGara = request.getParameter(SESSION_ID_GARA);
							GaraManager gm = new GaraManager(currentActiveConnection, logger);
							Gara gara = gm.getGara(Long.parseLong(idGara));
							Lotto lottoDb = lottoManager.getLotto(idLotto);

							// devo ricalcolare il contributo impresa, con i nuovi valori di esclusione
							lottoDb.setFLAG_ESCLUSO(flagEscluso);
							lottoDb.setID_ESCLUSIONE(idArtRegime != 0 ? idArtRegime : idEsclusione);
							lottoDb.set_FLAG_REGIME(flagRegime);// TICKET ALM - 3.04.2 2805

							ParametriContributo params = new ParametriContributo(gara, lottoDb,
									PageHelper.getCalendarFromStringDate(lottoDb.getData_Pubblicazione()),
									currentActiveConnection, logger);

							boolean isRipetizione = Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_RIPETIZIONE())
									|| Costanti.COLL_CIG_RIP.equals(lotto.getID_MOTIVO_COLL_CIG());

							BigDecimal importo = !isRipetizione ? gcwbc.getContributoOE(params) : new BigDecimal(0);
							if (!gcwbc.hasErrors())
								lotto.setImporto_Impresa(importo);
							else
								lotto.setImporto_Impresa(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA));

							// controlo messaggio di ritorno
							msgs.add(gcwbc.getErrors());

							lottoManager.updateCampiEsclusoLotto(lotto);

							// non scriveva il log amministrativo
							String completeCIG = request.getParameter(FIELD_NAME_CIG);
							String cfSARiferimento = request
									.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
							String cfAmministrazione = request
									.getParameter(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
							String cfUtente = currentUser.getLogin();

							LogManager logManager = new LogManager(currentActiveConnection, logger);
							logManager.log(getTodayDate(), cfSARiferimento, cfUtente, completeCIG,
									LogManager.MOD_ESCLUSO, cfAmministrazione, Long.toString(idLotto), idGara);

							// PP devo anche ricalcolare i contributi lotto e gara
							if (gara.getDATA_CONFERMA_GARA() != null || gara.getDATA_PERFEZIONAMENTO_BANDO() != null) {

								if (SimogFlags.isGRIGLIA_CONTRIBUTO()) {
									// devo ricalcolare i contributi di tutti i lotti e della gara
									Map<String, Lotto> lotti = lottoManager.getMappaLotti(gara.getId_Gara());

									gcwbc.ricalcola(gara, lotti);
								} else {
									// PP devo ricalcolare l'importo contributo gara se questa e perfezionata
									BigDecimal impGaraCalcolo = gcwbc.getImportoGara(
											lottoManager.getListaLotti(Long.parseLong(idGara)), true, true);

									Gara garaCalc = (Gara) gara.clone();
									garaCalc.setIMPORTO_GARA(impGaraCalcolo);
									
		
									
									//MAC 36255 3.04.8
							        //se la gara e singolo lotto salvo in una variabile la modalita esclusione da passare a getContributoSA
									Map<String, Lotto> lotti = lottoManager.getMappaLotti(gara.getId_Gara());
									if (lotti.size()==1) {
										Map.Entry<String, Lotto> entry = lotti.entrySet().iterator().next();
							        	 motivoEsclusioneGara = String.valueOf(entry.getValue().getID_ESCLUSIONE()).trim();
									}
									
									//MAC 36255 3.04.8 aggiunto motivoEsclusioneGara
									params = new ParametriContributo(garaCalc, null, motivoEsclusioneGara,
											garaCalc.getDATA_CONFERMA_GARA() != null
													? PageHelper
															.getCalendarFromStringDate(garaCalc.getDATA_CONFERMA_GARA())
													: PageHelper.getCalendarFromStringDate(
															garaCalc.getDATA_PERFEZIONAMENTO_BANDO()));

									importo = gcwbc.getContributoSA(params);
									if (!gcwbc.hasErrors())
										gara.setIMPORTO_SA_GARA(importo);
									else if (SimogFlags.is30230_NRFWEBXX00Active())
										gara.setIMPORTO_SA_GARA(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA));
								}

								// controlo messaggio di ritorno
								msgs.add(gcwbc.getErrors());

								// sfrutto la modifica della gara passando le stesse impostazioni
								gla.saveGara(gara, false, msgs);
							}

							/** commit */
							commit(currentActiveConnection);

							String forwardPath = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara + "&"
									+ ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;

							msgs.addValidationInfo(Messaggi.SIMOG_LOTTO_012);

							sendValidations(request, response, msgs, forwardPath);
							return;
						} else {
							rollback(currentActiveConnection);
							sendValidations(request, response, lv.getEccezioni(), requestingURL);
							return;
						}
					} else if (action.equals(ParametriCup.ACTION_MODIFICA_DATI_CUP)) {
						String requestingURL = "/gestisciLotto?action=" + ParametriCup.ACTION_MODIFICA_DATI_CUP + "&"
								+ FIELD_NAME_ID_LOTTO + "=" + idLotto;
						String idGara = request.getParameter(SESSION_ID_GARA);
						String vis_forwardPath = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara + "&"
								+ ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;

						String fromIntegrazioneCigCup = (String) request.getSession()
								.getAttribute(ParametriCup.FROM_ELENCO_CUP);
						if (fromIntegrazioneCigCup != null && "visCup".equals(fromIntegrazioneCigCup)) {
							vis_forwardPath = ParametriCup.SRV_ELENCO_CIG_INTEGRAZIONE_CUP + "?nav=yes";
						}

						String completeCIG = request.getParameter(FIELD_NAME_CIG);

						// TODO: METTERE LA CHIAMATA SQL ALLA LISTA DA PRESELEZIONARE

						Lotto lotto = lottoManager.getLotto(idLotto);

						lotto.setId_Gara(Long.parseLong(idGara));
						lotto.setFLAG_CUP((String) request.getParameter(ParametriCup.FIELD_FLAG_CUP));
						lotto.setFLAG_PNRR_PNC((String) request.getParameter(ParametriServlet.FLAG_PNRR_PNC));
						lotto.setFLAG_PREVISIONE_QUOTA(
								(String) request.getParameter(ParametriServlet.FLAG_PREVISIONE_QUOTA));
						lotto.setFLAG_MISURE_PREMIALI(
								(String) request.getParameter(ParametriServlet.FLAG_MISURE_PREMIALI));

						if (request.getParameter(ParametriServlet.QUOTA_GIOVANILE) != null && !request.getParameter(ParametriServlet.QUOTA_GIOVANILE).isEmpty()) {
							BigDecimal qGiov = new BigDecimal(request.getParameter(ParametriServlet.QUOTA_GIOVANILE).replace(',', '.'));
							lotto.setQuotaGiovanile(qGiov);
						}

						if (request.getParameter(ParametriServlet.QUOTA_FEMMINILE) != null && !request.getParameter(ParametriServlet.QUOTA_FEMMINILE).isEmpty()) {
							BigDecimal qFem = new BigDecimal(request.getParameter(ParametriServlet.QUOTA_FEMMINILE).replace(',', '.'));
							lotto.setQuotaFemminile(qFem);
						}

						lotto.setElencoCup(gla.getBeanCup(request));
						lotto.setElencoMisurePremiali(gla.getBeanMisurePremiali(request, ""));
						lotto.setElencoMotivoDeroga(gla.getBeanMotivoDeroga(request, ""));

						String urgenzaDL133_session = "";

						Integer selectedMotivo = (Integer) request
								.getAttribute(ParametriServlet.FIELD_NAME_MOTIVO_URGENZA);
						if (selectedMotivo != null
								&& selectedMotivo.equals(Costanti.TIPO_ESTREMA_URGENZA_PROTEZIONE_CIVILE)) {
							urgenzaDL133_session = "S";
						}

						lotto.setElencoTipoAppaltoLottoL(gla.getBeanTipoAppLotto(request, lotto.getId_Lotto(),
								PSBD.FIELD_NAME_TIPO_APPALTO_AGG_L));
						lotto.setElencoTipoAppaltoLottoF(gla.getBeanTipoAppLotto(request, lotto.getId_Lotto(),
								PSBD.FIELD_NAME_TIPO_APPALTO_AGG_SF));

						// lotto.setElencoMotivoDeroga( gla.getBeanMotivoDeroga(request,
						// lotto.getId_Lotto(), PSBD.FIELD_NAME_MOTIVO_DEROGA) );

						LottoValidator lv = new LottoValidator(currentActiveConnection, logger);
						boolean valido = lv.valida(lotto, ParametriCup.ACTION_MODIFICA_DATI_CUP);

						// controllo che il lotto sia buono per la funzione di integrazione
// 19.08.2014 secondo Piccinini non e corretto, deve essere sempre consentita l'integrazione                  

//                  GaraManager gm = new GaraManager(currentActiveConnection, logger);
//                  TableBean esiste = gm.getElencoCigIntegrazioneCup(null, 
//                        currentUser.getLogin(), 
//                        currentUser.getUfficiByProfilo(ProfiloEnum.RUP), null, null, 
//                        configuration.getDataAttivazioneCup(), 
//                        null, false, 0, 1, completeCIG, true);
//                  
//                  if(esiste.getFullSize() == 0){                     
//                     lv.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_007.replace("$1", "ERRORE non sono soddisfatti i criteri per la funzione di integrazione"));
//                  }

						// Validazione esistenza in DIPE
						ElaborazioniCUPClient cli = new ElaborazioniCUPClient(configuration, logger);
						AllValidationBeans eccez = cli.validaCupDIPE(lotto, true);
						if (eccez != null)
							lv.getEccezioni().add(eccez);

						// verifico nuovamente se ha passato tutte le validazioni
						valido = lv.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;

						if (valido) {
							// Update: flag, elencoCUp, tipologie appalto
							CupLottoAggAction claAction = new CupLottoAggAction(currentActiveConnection, logger);
							claAction.updateDatiCupLotto(lotto);

							commit(currentActiveConnection);

							AllValidationBeans msgs = new AllValidationBeans();
							msgs.addValidationInfo(Messaggi.SIMOG_LOTTO_012);

							sendValidations(request, response, msgs, vis_forwardPath);
							return;
						} else {
							// torna alla pagina
							rollback(currentActiveConnection);
							sendValidations(request, response, lv.getEccezioni(), requestingURL);
							return;
						}

					
					}else if (action.equals(ParametriServlet.ACTION_INTEGRA_PARI_OPPORTNITA)) { //MEV 37010 3.04.8.1

						String requestingURL = "/gestisciLotto?action=" + ParametriServlet.ACTION_INTEGRA_PARI_OPPORTNITA
								+ "&" + FIELD_NAME_ID_LOTTO + "=" + idLotto;

						// in questo caso carico il lotto da db ed aggiungo i valori delle ripetizioni

						
						String flagPnrrPnc = request.getParameter(ParametriServlet.FLAG_PNRR_PNC);
						String flagDerogaAdesione = request.getParameter(ParametriServlet.FLAG_DEROGA_ADESIONE); //MEV 37010 3.04.8.1
						String flagPrevisioneQuota = request.getParameter(ParametriServlet.FLAG_PREVISIONE_QUOTA);
						String flagMisurePremiali = request.getParameter(ParametriServlet.FLAG_MISURE_PREMIALI);
						String quotaGiovanile = request.getParameter(ParametriServlet.QUOTA_GIOVANILE);
						String quotaFemminile = request.getParameter(ParametriServlet.QUOTA_FEMMINILE);

						

						

				
						// TODO: METTERE LA CHIAMATA SQL ALLA LISTA DA PRESELEZIONARE
						String idsMotivoDeroga = "0";
						// Creo una stringa con la lista degli id da selezionare
						for (int i = 1; i < 6; i++) {
							idsMotivoDeroga += ";" + i;
						}

						TableBean infoLotto = null;
						infoLotto = lottoManager.getLottoByIdLottoRSSA(idLotto, currentUser.getUffici());
						
						infoLotto.setField(LOTTO.FLAG_PNRR_PNC, flagPnrrPnc);
						infoLotto.setField(LOTTO.FLAG_DEROGA_ADESIONE, flagDerogaAdesione); //MEV 37010 3.04.8.1
						infoLotto.setField(LOTTO.FLAG_PREVISIONE_QUOTA, flagPrevisioneQuota);
						infoLotto.setField(LOTTO.FLAG_MISURE_PREMIALI, flagMisurePremiali);
						infoLotto.setField(LOTTO.QUOTA_FEMMINILE, quotaFemminile);
						infoLotto.setField(LOTTO.QUOTA_GIOVANILE, quotaGiovanile);
						infoLotto.setField(LOTTO_MOTIVO_DEROGA.ID_MOTIVO_DEROGA, idsMotivoDeroga);
						// Ticket #20058 - 09 - 02 - 21
						
						
						
						//MEV 37010 3.04.8.1
						Lotto lotto = new Lotto();
						lotto.setId_Lotto(idLotto);
						lotto.setCig(infoLotto.getNulledField(LOTTO.CIG, 0));
						lotto.setCig_cicle(Integer.valueOf(infoLotto.getNulledField(LOTTO.CIG_CICLE, 0)).intValue());
						lotto.setCig_kkk(infoLotto.getNulledField(LOTTO.CIG_KKK, 0));
						lotto.setFLAG_PNRR_PNC(flagPnrrPnc);
						lotto.setFLAG_DEROGA_ADESIONE(flagDerogaAdesione); //MEV 37010 3.04.8.1
						lotto.setFLAG_PREVISIONE_QUOTA(flagPrevisioneQuota);
						lotto.setFLAG_MISURE_PREMIALI(flagMisurePremiali);

						if (quotaGiovanile != null) {
							// Double tempQuotaDouble = Double.valueOf(quotaGiov.replace(',', '.'));
							BigDecimal tempQuota = new BigDecimal(quotaGiovanile.replace(',', '.'));
							lotto.setQuotaGiovanile(tempQuota);
						}

						if (quotaFemminile != null) {
							// Double tempQuotaDouble = Double.valueOf(quotaFem.replace(',', '.'));
							BigDecimal tempQuota = new BigDecimal(quotaFemminile.replace(',', '.'));
							lotto.setQuotaFemminile(tempQuota);
						}
						
						
						
						MotivoDerogaManager motivoDerogaManager = new MotivoDerogaManager(currentActiveConnection, logger);
						MisuraPremialeManager misuraPremialeManager = new MisuraPremialeManager(currentActiveConnection, logger);

						
						motivoDerogaManager.updateDataFineValiditaMotivoDerogaLotto(lotto.getId_Lotto());
						misuraPremialeManager.updateDataFineValiditaMisuraPremialeLotto(lotto.getId_Lotto());
						lotto.setElencoMotivoDeroga(gla.getBeanMotivoDeroga(request, PSBD.FIELD_NAME_MOTIVO_DEROGA));
						lotto.setElencoMisurePremiali(gla.getBeanMisurePremiali(request, PSBD.FIELD_NAME_MISURA_PREMIALE));
						//FINE MEV 37010 3.04.8.1
						
						//MEV 37010 3.04.8.1 sovrascrivo i dati integrati delle pari opportunità ai CIG figli se ci sono
						GaraManager gm = new GaraManager(currentActiveConnection, logger);
						//mi prendo la lista dei lotti delle gare di adesione all'AQ
						List<Lotto> LottiAdesioni = lottoManager.getLottiAdesioniByCigAQ(lotto.getCIG()+lotto.getCIG_kkk());
						
						if (!LottiAdesioni.isEmpty()) {
							for (Lotto lottoAdesione : LottiAdesioni) {
								lottoAdesione.setFLAG_PNRR_PNC(flagPnrrPnc);
								lottoAdesione.setFLAG_DEROGA_ADESIONE(flagDerogaAdesione); //MEV 37010 3.04.8.1
								lottoAdesione.setFLAG_PREVISIONE_QUOTA(flagPrevisioneQuota);
								lottoAdesione.setFLAG_MISURE_PREMIALI(flagMisurePremiali);

								if (quotaGiovanile != null) {
									// Double tempQuotaDouble = Double.valueOf(quotaGiov.replace(',', '.'));
									BigDecimal tempQuota = new BigDecimal(quotaGiovanile.replace(',', '.'));
									lottoAdesione.setQuotaGiovanile(tempQuota);
								}

								if (quotaFemminile != null) {
									// Double tempQuotaDouble = Double.valueOf(quotaFem.replace(',', '.'));
									BigDecimal tempQuota = new BigDecimal(quotaFemminile.replace(',', '.'));
									lottoAdesione.setQuotaFemminile(tempQuota);
								}
								
								motivoDerogaManager.updateDataFineValiditaMotivoDerogaLotto(lottoAdesione.getId_Lotto());
								misuraPremialeManager.updateDataFineValiditaMisuraPremialeLotto(lottoAdesione.getId_Lotto());
								lottoAdesione.setElencoMotivoDeroga(gla.getBeanMotivoDeroga(request, PSBD.FIELD_NAME_MOTIVO_DEROGA));
								lottoAdesione.setElencoMisurePremiali(gla.getBeanMisurePremiali(request, PSBD.FIELD_NAME_MISURA_PREMIALE));
								
								motivoDerogaManager.createMotivoDerogaLottoRelation(lottoAdesione.getId_Lotto(), lottoAdesione.getElencoMotivoDeroga());
								misuraPremialeManager.createMisuraPremialeLottoRelation(lottoAdesione.getId_Lotto(),lottoAdesione.getElencoMisurePremiali());
								lottoManager.updatePariOpportunita(lottoAdesione);
							}
						}
						//FINE MEV 37010 3.04.8.1 sovrascrivo i dati integrati delle pari opportunità ai CIG figli se ci sono
						
						
						// Ticket #20058 - 09 - 02 - 21

						lotto.setId_Gara(Long.parseLong(request.getParameter(SESSION_ID_GARA)));
						

						// lotto.setSelMotiviDeroga(idsMotivoDeroga);

						LottoValidator lv = new LottoValidator(currentActiveConnection, logger);
						lv.valida(lotto, ParametriServlet.ACTION_INTEGRA_PARI_OPPORTNITA);

						request.setAttribute(TABLEBEAN, infoLotto);
						request.setAttribute(ParametriServlet.ACTION, action);

						if (lv.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0) {

							// messaggi da ritornare
							AllValidationBeans msgs = new AllValidationBeans();

							String idGara = request.getParameter(SESSION_ID_GARA);
							Gara gara = gm.getGara(Long.parseLong(idGara));

							

							//MEV 37010 3.04.8.1
							motivoDerogaManager.createMotivoDerogaLottoRelation(lotto.getId_Lotto(), lotto.getElencoMotivoDeroga());
							misuraPremialeManager.createMisuraPremialeLottoRelation(lotto.getId_Lotto(),lotto.getElencoMisurePremiali());
							lottoManager.updatePariOpportunita(lotto);
							//MEV 37010 3.04.8.1

							// non log amministrativo
							String completeCIG = request.getParameter(FIELD_NAME_CIG);
							String cfSARiferimento = request
									.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
							String cfAmministrazione = request
									.getParameter(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
							String cfUtente = currentUser.getLogin();

							LogManager logManager = new LogManager(currentActiveConnection, logger);
							logManager.log(getTodayDate(), cfSARiferimento, cfUtente, completeCIG,
									LogManager.INT_PARI_OPPORTUNITA, cfAmministrazione, Long.toString(idLotto), idGara);

							/** commit */
							commit(currentActiveConnection);

							String forwardPath = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara + "&"
									+ ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;

							msgs.addValidationInfo(Messaggi.SIMOG_LOTTO_012);

							sendValidations(request, response, msgs, forwardPath);
							return;
						} else {
							rollback(currentActiveConnection);
							sendValidations(request, response, lv.getEccezioni(), requestingURL);
							return;
						}
					
					}else if (action.equals(ParametriServlet.ACTION_MODIFICA_DATI_PERFEZIONAMENTO)) { //MEV 3.04.10 43227

						String requestingURL = "/gestisciLotto?action=" + ParametriServlet.ACTION_MODIFICA_DATI_PERFEZIONAMENTO
								+ "&" + FIELD_NAME_ID_LOTTO + "=" + idLotto;

						

						
						String dataScadenzaPagamenti = request.getParameter(ParametriServlet.DATA_SCADENZA_PAGAMENTI);
						String dataScadenzaRichiestaInvito= request.getParameter(ParametriServlet.DATA_SCADENZA_RICHIESTA_INVITO); //MEV 37010 3.04.8.1
						String oraScadenzaPagamenti = request.getParameter(ParametriServlet.ORA_SCADENZA);
						

						

						

				
						// TODO: METTERE LA CHIAMATA SQL ALLA LISTA DA PRESELEZIONARE
//						String idsMotivoDeroga = "0";
//						// Creo una stringa con la lista degli id da selezionare
//						for (int i = 1; i < 6; i++) {
//							idsMotivoDeroga += ";" + i;
//						}

						TableBean infoLotto = null;
						infoLotto = lottoManager.getLottoByIdLottoRSSA(idLotto, currentUser.getUffici());
						
						infoLotto.setField(LOTTO.DATA_SCADENZA_PAGAMENTI, dataScadenzaPagamenti);
						infoLotto.setField(LOTTO.DATA_SCADENZA_RICHIESTA_INVITO, dataScadenzaRichiestaInvito); 
						infoLotto.setField(LOTTO.ORA_SCADENZA, oraScadenzaPagamenti);
						
						// Ticket #20058 - 09 - 02 - 21
						
						try {
							
							if (dataScadenzaPagamenti != null && !"".equals(dataScadenzaPagamenti)) {
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
								dataScadenzaPagamenti = sdf2.format(sdf.parse(dataScadenzaPagamenti));
							}
							if (dataScadenzaRichiestaInvito != null && !"".equals(dataScadenzaRichiestaInvito)) {
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
								dataScadenzaRichiestaInvito = sdf2.format(sdf.parse(dataScadenzaRichiestaInvito));
							}
						} catch (ParseException e) {
						    e.printStackTrace();
						}
						
						//MEV 37010 3.04.8.1
						Lotto lotto = new Lotto();
						lotto.setId_Lotto(idLotto);
						lotto.setCig(infoLotto.getNulledField(LOTTO.CIG, 0));
						lotto.setCig_cicle(Integer.valueOf(infoLotto.getNulledField(LOTTO.CIG_CICLE, 0)).intValue());
						lotto.setCig_kkk(infoLotto.getNulledField(LOTTO.CIG_KKK, 0));
						lotto.setDataScadenzaPagamenti(dataScadenzaPagamenti);
						lotto.setDataScadenzaRichiestaInvito(dataScadenzaRichiestaInvito); 
						lotto.setORA_SCADENZA(oraScadenzaPagamenti);
						lotto.setId_Scelta_Contraente(infoLotto.getNulledField(LOTTO.ID_SCELTA_CONTRAENTE, 0));
						lotto.setData_Pubblicazione(infoLotto.getNulledField(LOTTO.DATA_PUBBLICAZIONE, 0));
						
						
						
						
						
						
						
						
						
						// Ticket #20058 - 09 - 02 - 21

						lotto.setId_Gara(Long.parseLong(request.getParameter(SESSION_ID_GARA)));
						

						// lotto.setSelMotiviDeroga(idsMotivoDeroga);

						LottoValidator lv = new LottoValidator(currentActiveConnection, logger);
						lv.valida(lotto, ParametriServlet.ACTION_MODIFICA_DATI_PERFEZIONAMENTO);

						request.setAttribute(TABLEBEAN, infoLotto);
						request.setAttribute(ParametriServlet.ACTION, action);

						if (lv.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0) {

							// messaggi da ritornare
							AllValidationBeans msgs = new AllValidationBeans();

							String idGara = request.getParameter(SESSION_ID_GARA);
							//Gara gara = gm.getGara(Long.parseLong(idGara));

							
							lottoManager.updateModificaDatiPerfezionamento(lotto);
							

							// non log amministrativo
							String completeCIG = request.getParameter(FIELD_NAME_CIG);
							String cfSARiferimento = request
									.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
							String cfAmministrazione = request
									.getParameter(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
							String cfUtente = currentUser.getLogin();

							LogManager logManager = new LogManager(currentActiveConnection, logger);
							logManager.log(getTodayDate(), cfSARiferimento, cfUtente, completeCIG,
									LogManager.INT_PARI_OPPORTUNITA, cfAmministrazione, Long.toString(idLotto), idGara);

							/** commit */
							commit(currentActiveConnection);

							String forwardPath = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara + "&"
									+ ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;

							msgs.addValidationInfo(Messaggi.SIMOG_LOTTO_012);

							sendValidations(request, response, msgs, forwardPath);
							return;
						} else {
							rollback(currentActiveConnection);
							sendValidations(request, response, lv.getEccezioni(), requestingURL);
							return;
						}
					
					} else if(action.equals(ParametriServlet.ACTION_MODIFICA_CAT_SOA)) { /* MAD 68089 3.04.16 Inizio */
						
						String requestingURL = "/gestisciLotto?action=" + ParametriServlet.ACTION_MODIFICA_CAT_SOA
								+ "&" + FIELD_NAME_ID_LOTTO + "=" + idLotto;						
						
						Lotto lotto = new Lotto();												

						lotto.setId_Lotto(idLotto);						
						lotto.setId_Gara(Long.parseLong(request.getParameter(SESSION_ID_GARA)));
						
						//- Lettura della categoria Prevalente
						String categoriaPrevalente = request.getParameter(ParametriServlet.FIELD_NAME_CATEGORIA_PREVALENTE);
						
						//- Lettura delle categorie scorporabili
						Object obj2 = gla.loadCategorieFromRequest(request);
						String[] categorie = (String[])obj2;
						
						LottoValidator lv = new LottoValidator(currentActiveConnection, logger);
						lv.valida(lotto, ParametriServlet.ACTION_MODIFICA_CAT_SOA);

						//- Se non ci sono errori di validazione allora eseguo l'update
						if (lv.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0) {

							// messaggi da ritornare
							AllValidationBeans msgs = new AllValidationBeans();		
							
							//- Recupero la categoria prevalente e scorporabile per il log	
							Lotto preMod = lottoManager.getLotto(idLotto);									
							
							String catPrevPreMod = ( preMod.getId_Categoria_prevalente() == null || preMod.getId_Categoria_prevalente().isEmpty() ) ? "-" : preMod.getId_Categoria_prevalente().trim();
							
							Map<String, String> catScorpPreMod = preMod.getCategorieScorporabili();
							if(catScorpPreMod == null)
								catScorpPreMod = new HashMap<String, String>();
							
							String catScorpLogStr = "-";
							
							boolean catPrevModificata = false;
							boolean catScorpModificata = false;
							
							/** Controllo se la categoria prevalente e' stata modificata */
							if( catPrevPreMod != null && categoriaPrevalente != null && !catPrevPreMod.trim().equals(categoriaPrevalente.trim()) )
								catPrevModificata = true;
							
							/** Controllo se la categoria scorporabile e' stata modificata */
							if(catScorpPreMod != null && categorie != null) {
								
								/** Controllo se le categorie scorporabili sono state 'Rimosse' quindi vale come modifica */
								if(categorie.length < catScorpPreMod.size() )
								{
									catScorpModificata = true;
								}
								else {
									for( String c : categorie ) {
										if(!catScorpPreMod.containsKey(c)) /** Almeno una categoria e' stata modificata */
											catScorpModificata = true;
									}
								}									
								
								
							}
							
							/** Imposto il log per le categorie scorporabili */
							if( catScorpPreMod != null && !catScorpPreMod.isEmpty() ) {
								
								catScorpLogStr = "@@";								
								for(String k : catScorpPreMod.keySet()) 
								{									
									//- Creo la stringa per la tabella log
									catScorpLogStr = catScorpLogStr + ", " + k;
								}
								catScorpLogStr = catScorpLogStr.replace("@@, ", "");
							}
							
							
							/** Esecuzione dell'update della categoria prevalente */
							if(catPrevModificata)
								lottoManager.updateCategoriaSoa(lotto, categoriaPrevalente);   
							
							
							/** Esecuzione dell'update della categoria scorporabile */
							if( catScorpModificata && categorie != null ) {

								//- Aggiornamento/Inserimento associazione Lotto-CategoriaScorporabile
								lottoManager.updateLottoCategorieScorporabili( String.valueOf( lotto.getId_Lotto() ), categorie );
							}

							
							/** In caso di Update OK Effettuo l'insert nella tabella REL_LOTTO_CATEGORIA_SCORPORABILE_STORICO */
							if( catScorpModificata ) {
								
								if( catScorpPreMod != null && !catScorpPreMod.isEmpty() ) {								
									for(String k : catScorpPreMod.keySet()) 
									{
										//- Effettuo l'insert nella tabella REL_LOTTO_CATEGORIA_SCORPORABILE_STORICO
										lottoManager.insertRelLottoCatScorpStorico(idLotto, k);
									}
								}
								else { //- Caso in cui  precedentemente non vi erano categorie scorporabili selezionate
									
									lottoManager.insertRelLottoCatScorpStorico(idLotto, "-");
								}
								
							}
							

							//- Scrittura del LOG in caso di update OK
							String idGara = request.getParameter(SESSION_ID_GARA);
							String completeCIG = request.getParameter(FIELD_NAME_CIG);
							String cfSARiferimento = request
									.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
							String cfAmministrazione = request
									.getParameter(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
							String cfUtente = currentUser.getLogin();
							 
							String logDescription = "";
							
							if(catPrevModificata)
								logDescription = logDescription + "Categoria Prevalente [" + catPrevPreMod + "]";
							
							if(catPrevModificata && catScorpModificata) 
								logDescription = logDescription + " e ";
							
							if(catScorpModificata) 
								logDescription = logDescription + "Categoria Scorporabile ["+ catScorpLogStr + "]";
							
							if(!catPrevModificata && !catScorpModificata)
								logDescription = "Nessuna modifica effettuata";
								
							logDescription = LogManager.MODFIFICA_CAT_SOA + logDescription;
							
							LogManager logManager = new LogManager(currentActiveConnection, logger);
							logManager.log(getTodayDate(), cfSARiferimento, cfUtente, completeCIG,
									logDescription, cfAmministrazione, Long.toString(idLotto), idGara);
							
							
  							/* commit */
							commit(currentActiveConnection);
							
							//- Ritorno alla pagina di dettaglio lotto
							String forwardPath = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara + "&"
									+ ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;

							//- Messaggio Update OK
							msgs.addValidationInfo(Messaggi.SIMOG_LOTTO_012);

							sendValidations(request, response, msgs, forwardPath);
							return;
							
						} else {
							rollback(currentActiveConnection);
							sendValidations(request, response, lv.getEccezioni(), requestingURL);
							return;
						}
						
						/* MAD 68089 3.04.16 Fine */
						
					}else if (action.equals(ParametriServlet.ACTION_MODIFICA_CPV)) { //MEV 53643 3.04.13

						String requestingURL = "/gestisciLotto?action=" + ParametriServlet.ACTION_MODIFICA_CPV
								+ "&" + FIELD_NAME_ID_LOTTO + "=" + idLotto;
						

						

						Lotto lotto = new Lotto();
						
						String cpvPREV = request.getParameter(ParametriServlet.FIELD_NAME_CPV);
						List<CpvLotto> elencoCpv = new LinkedList<CpvLotto>();
						elencoCpv = getBeanCPVSecondarie(request);
						
						lottoManager.deleteCpvLotto(idLotto);
						// Elimina duplicati
						if (elencoCpv != null && elencoCpv.size() > 0) {
							lotto.setElencoCpvSecondarie(this.verificaDuplicati(elencoCpv));
							for (CpvLotto cpvSec : lotto.getElencoCpvSecondarie()) {
								lottoManager.insertCpvLotto(idLotto, cpvSec);
							}
						}
						

						TableBean infoLotto = null;
						infoLotto = lottoManager.getLottoByIdLottoRSSA(idLotto, currentUser.getUffici());
//						
//						
						
	
						lotto.setId_Lotto(idLotto);
						lotto.setCig(infoLotto.getNulledField(LOTTO.CIG, 0));
						lotto.setCig_cicle(Integer.valueOf(infoLotto.getNulledField(LOTTO.CIG_CICLE, 0)).intValue());
						lotto.setCig_kkk(infoLotto.getNulledField(LOTTO.CIG_KKK, 0));
						lotto.setId_CPV(cpvPREV);
						lotto.setElencoCpvSecondarie(elencoCpv);
						
						
						
						
						
						
						
						
						
						// Ticket #20058 - 09 - 02 - 21
//
						lotto.setId_Gara(Long.parseLong(request.getParameter(SESSION_ID_GARA)));
//						

//
						LottoValidator lv = new LottoValidator(currentActiveConnection, logger);
						lv.valida(lotto, ParametriServlet.ACTION_MODIFICA_CPV);
//
//						request.setAttribute(TABLEBEAN, infoLotto);
//						request.setAttribute(ParametriServlet.ACTION, action);
//
						if (lv.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0) {
//
//							// messaggi da ritornare
							AllValidationBeans msgs = new AllValidationBeans();
//
							String idGara = request.getParameter(SESSION_ID_GARA);
							//Gara gara = gm.getGara(Long.parseLong(idGara));
//
//							
							lottoManager.updateCPV(lotto);
//							
//
							// non log amministrativo
							String completeCIG = request.getParameter(FIELD_NAME_CIG);
							String cfSARiferimento = request
									.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
							String cfAmministrazione = request
									.getParameter(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
							String cfUtente = currentUser.getLogin();

							LogManager logManager = new LogManager(currentActiveConnection, logger);
							logManager.log(getTodayDate(), cfSARiferimento, cfUtente, completeCIG,
									LogManager.MODFIFICA_CPV, cfAmministrazione, Long.toString(idLotto), idGara);
//
//							/** commit */
							commit(currentActiveConnection);

							String forwardPath = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara + "&"
									+ ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;

							msgs.addValidationInfo(Messaggi.SIMOG_LOTTO_012);

							sendValidations(request, response, msgs, forwardPath);
							return;
						} else {
							rollback(currentActiveConnection);
							sendValidations(request, response, lv.getEccezioni(), requestingURL);
							return;
						}
					
					}// gm altrimenti e' una normale modifica
					else {
						String requestingURL = "/gestisciLotto?action=modifica&" + FIELD_NAME_ID_LOTTO + "=" + idLotto;

						String idGara = request.getParameter(SESSION_ID_GARA);

						Object obj = null;

						Lotto lotto = new Lotto();

						/** il load fa' anche la validazione */
						Object obj1 = gla.load(request, GaraLottoAction.TIPO_LOTTO);

						Object tmp = request.getAttribute(ParametriServlet.ERRORBEAN);

						Object obj2 = gla.loadCategorieFromRequest(request);
						if (obj1 instanceof AllValidationBeans) {

							/** se non valide */
							if (obj2 instanceof AllValidationBeans) {
								AllValidationBeans avb = (AllValidationBeans) obj1;
								/** merge dei 2 risultati */
								avb.add((AllValidationBeans) obj2);
								/** invia */
								sendValidations(request, response, avb, requestingURL);
								return;
							}
							/** altrimenti manda */
							sendValidations(request, response, (AllValidationBeans) obj1, requestingURL);
							return;
						}
						if (obj2 instanceof AllValidationBeans) {
							/** invia */
							sendValidations(request, response, (AllValidationBeans) obj2, requestingURL);
							return;
						}
						lotto = (Lotto) obj1;
						String[] categorie = (String[]) obj2;
						lotto.setId_Lotto(idLotto);

						// messaggi da ritornare
						AllValidationBeans msgs = new AllValidationBeans();
						if (tmp != null) {
							msgs.add((AllValidationBeans) tmp);
						}
						// PP patch per amministratore e calcolo contributo, se il lotto e perfezionato
						// il ricalcolo deve
						// essere fatto con la data pubblicazione originale
						if (currentUser.isAmministratore()) {
							LottoManager lMan = new LottoManager(currentActiveConnection, logger);

							TableBean lottoDB = lMan.getLottoByIdLottoAmm(lotto.getId_Lotto());
							if (!"".equals(lottoDB.getNulledField(LOTTO.DATA_PUBBLICAZIONE, 0)))
								lotto.setData_Pubblicazione(lottoDB.getNulledField(LOTTO.DATA_PUBBLICAZIONE, 0));

							// TICKET ALM #2921
							if (!"".equals(lottoDB.getNulledField(LOTTO.ORA_SCADENZA, 0)))
								lotto.setORA_SCADENZA(lottoDB.getNulledField(LOTTO.ORA_SCADENZA, 0));
							// FINE TICKET ALM #2921

						}

						boolean esito = gla.modificaLotto(lotto, categorie, currentUser.isAmministratore(), msgs);
						if (!esito) {
							rollback(currentActiveConnection);
							logger.error("modifica o validazione Lotto fallita");
							sendError(request, response, "Modifica del lotto fallita", requestingURL);
							return;
						}
						/** END */
						/*
						 * Inserimento informazioni di log amministrativo
						 */
						LogManager logManager = new LogManager(currentActiveConnection, logger);

						logger.debug("Esecuzione GaraManager DAO");

						String completeCIG = request.getParameter(FIELD_NAME_CIG);
						String cfSARiferimento = request
								.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
						String cfAmministrazione = request.getParameter(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
						String cfUtente = currentUser.getLogin();

						// gm aggiornamento dell'importo gara dopo la modifica del lotto da admin (simog
						// 3.04)
						if (currentUser.isAmministratore()) {
							GaraManager gm = new GaraManager(currentActiveConnection, logger);
							Gara gara = gm.getGara(Long.parseLong(idGara));
							if (gara.getDATA_CONFERMA_GARA() != null || gara.getDATA_PERFEZIONAMENTO_BANDO() != null) {

								if (SimogFlags.isGRIGLIA_CONTRIBUTO()) {
									// devo ricalcolare i contributi di tutti i lotti e della gara
									Map<String, Lotto> lotti = lottoManager.getMappaLotti(gara.getId_Gara());

									gcwbc.ricalcola(gara, lotti);

									// sfrutto la modifica della gara passando le stesse impostazioni
									gla.saveGara(gara, false, msgs);
								} else {
									TableBean dettagliGara = gm.getDettagliGaraByIdGara(idGara);
									BigDecimal nuovoImportoGara = getCalcoloImportoGara(dettagliGara);
									gm.updateImportoGara(nuovoImportoGara, Long.parseLong(idGara));

									// PP devo ricalcolare l'importo contributo gara se questa e perfezionata
									// sfrutto la modifica della gara passando le stesse impostazioni ed indicando
									// il ricalcolo
									gla.saveGara(gara, currentUser.isAmministratore(), msgs);
								}
							}
						}

						logManager.log(getTodayDate(), cfSARiferimento, cfUtente, completeCIG, LogManager.MOD_LOTTO,
								cfAmministrazione, Long.toString(idLotto), idGara);

						logger.debug("Il CIG del lotto corrente vale [" + completeCIG + "]");

						msgs.addValidationInfo(Messaggi.SIMOG_LOTTO_012 + " CIG [" + completeCIG + "]");

						// TICKET ALM #3908
						// Aggiungi warning per importi superiori a 300 mila euro in caso di somme
						// urgenze per beni culturali
						GaraManager gm = new GaraManager(currentActiveConnection, logger);
						Gara gara = gm.getGara(Long.parseLong(idGara));
						if (lotto.getImporto_Lotto().doubleValue() > Costanti.SOGLIA_BENI_CULTURALI
								&& gara.getID_ESTREMA_URGENZA() == Costanti.TIPO_ESTREMA_URGENZA_BENI_CULTURALI) {
							msgs.addValidationWarn(Messaggi.SIMOG_LOTTO_027);
						}
						// FINE TICKET ALM #3908

						// TICKET ALM - 3.04.2 NG
						// Aggiunti warning per importi uguali o superiori a 40k in caso di affidamento
						// diretto
						if (lotto.getImporto_Lotto().doubleValue() >= Costanti.IMPORTO_LOTTO_40000
								&& lotto.getId_Scelta_Contraente().equals(Costanti.AFFIDAMENTO_DIRETTO))
							msgs.addValidationWarn(Messaggi.SIMOG_LOTTO_028);

						// TICKET #19858
						if (lotto.getImporto_opzioni() == null || lotto.getImporto_opzioni().doubleValue() == 0)
							msgs.addValidationWarn(
									Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "l'importo opzioni/ripetizioni"));

						// TICKET ALM #13518 - 3.04.4.1
						if (gara.getCIG_ACC_QUADRO() != null && !"".equals(gara.getCIG_ACC_QUADRO())) {
							LottoManager lm = new LottoManager(currentActiveConnection, logger);
							java.util.List<Lotto> lottoListAccQ = lm.getLottoByCigWS(gara.getCIG_ACC_QUADRO());
							if (lottoListAccQ != null && !lottoListAccQ.isEmpty()) {
							Lotto lottoAccQ = lottoListAccQ.get(0);

//      						List<Lotto> listaLotti = lm.getListaLotti(lotto.getId_Gara());
							BigDecimal sumImporti = lm.getSommaImportiAdesioni(gara.getCIG_ACC_QUADRO());// for(Lotto
																											// lottoEl :
																											// listaLotti)
//      							sumImporti.add(lottoEl.getImporto_Lotto());
							if (sumImporti == null)
								sumImporti = new BigDecimal(0);

							sumImporti.add(lotto.getImporto_Lotto());

							if (sumImporti.doubleValue() > lottoAccQ.getImporto_Lotto().doubleValue())
								msgs.addValidationWarn(Messaggi.SIMOG_LOTTO_035);

							}
						}

						String forwardPath = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara + "&"
								+ ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;
						// throw new ServletException(forwardPath);
						/** commit */
						commit(currentActiveConnection);

						sendValidations(request, response, msgs, forwardPath);
						return;
					}
				} catch (ActionException ae) {
					System.out.println("TB ActionException ae");
					ae.printStackTrace();
					rollback(currentActiveConnection);
					sendError(request, response, ae.getMessage(),
							currentUser.isAmministratore() ? JSP_GESTIONE_GARE_EXT : JSP_GESTIONE_SCHEDE);
					return;
				} catch (SimogException se) {
					System.out.println("TB SimogException se");
					se.printStackTrace();
					rollback(currentActiveConnection);
					sendError(request, response, se.getMessage(),
							currentUser.isAmministratore() ? JSP_GESTIONE_GARE_EXT : JSP_GESTIONE_SCHEDE);
					return;
				} catch (Exception sqle) {
					System.out.println("TB Exception sqle");
					sqle.printStackTrace();
					rollback(currentActiveConnection);
					sendError(request, response, sqle.getMessage(), JSP_ERRORE, sqle);
					return;
				} finally {

					// rollbackOrcommit(currentActiveConnection);
					closeConnection(request.getSession().getId(), getClass().getName());
				}
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE);
				return;
			}
		} else {
			sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE);
			return;
		}
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
	

	/*******************************************************************************************
	 * Ricava un parametro dalla Request assegandogli un parametro di default se vuoto
	 * @param request HttpServletRequest
	 * @param defaultValue int
	 * @param parameterName String
	 * @return int
	 */
	protected int getIntReqParameter(HttpServletRequest request, int defaultValue, String parameterName){
		int result;
		try{
			String value = request.getParameter(parameterName);
			if(value==null || "".equals(value))
				result=defaultValue;
			else
				result= Integer.parseInt(value);
			return result;
		}catch (Exception e) {
			return defaultValue;
		}
	}
	/**
	 * @param dettagliGara
	 * @return BigDecimal
	 */
	// calcolo del nuovo importo gara a seguito della modifica di un lotto da admin
	public BigDecimal getCalcoloImportoGara(TableBean dettagliGara) {
		List<Lotto> elenco = new ArrayList<Lotto>();
		Lotto lotto = null;
		// gm creo l'elenco dei lotti componenti con il loro importo
		if (dettagliGara != null) {
			for (int rowIndex = 0; rowIndex < dettagliGara.getTableSize(); rowIndex++) {
				TableBeanRow currentRow = dettagliGara.getRow(rowIndex);
				if (currentRow != null) {
					String idLotto = currentRow.getNulledField(LOTTO.ID_LOTTO);
					String dataCancellazione = currentRow.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO);
					String dataInibPagamento = currentRow.getNulledField(LOTTO.DATA_INIB_PAGAMENTO);

					// il confronto con "0" e necessario per le gare senza lotti
					if ((!"".equals(idLotto)) && (!"0".equals(idLotto)) && "".equals(dataCancellazione)
							&& "".equals(dataInibPagamento)) {

						lotto = new Lotto();
						BigDecimal importoLotto = new BigDecimal(currentRow.getNulledField(LOTTO.IMPORTO_LOTTO));
						lotto.setImporto_Lotto(importoLotto);
						elenco.add(lotto);
					}
				}
			}
		}
		BigDecimal result = new BigDecimal(0);
		// gm dall'elenco dei lotti ottengo la somma degli importi
		for (Lotto l : elenco) {
			if (l.getImporto_Lotto().compareTo(new BigDecimal(-1)) == 0) {
				result = new BigDecimal(-1);
				break;
			} else {
				result = result.add(l.getImporto_Lotto());
			}
		}
		return result;
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