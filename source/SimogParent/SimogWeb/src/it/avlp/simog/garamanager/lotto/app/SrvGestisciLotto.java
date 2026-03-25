package it.avlp.simog.garamanager.lotto.app;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.avcp.simog.manager.cup.CupLottoAggManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.CondizioniManager;
import it.avcp.simog.managers.aggiudicazione.MisuraPremialeManager;
import it.avcp.simog.managers.aggiudicazione.MotivoDerogaManager;
import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avcp.simog.managers.cpv.CPVEUManager;
import it.avlp.simog.actions.GaraLottoAction;
import it.avlp.simog.actions.aggiudicazione.Scheda_A_Action;
import it.avlp.simog.beans.CondizioneLottoBean;
import it.avlp.simog.beans.CpvLotto;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IniziativaSoggAggr;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.MessageBean;
import it.avlp.simog.beans.MisuraPremialeLottoBean;
import it.avlp.simog.beans.MotivazioniBean;
import it.avlp.simog.beans.MotivoDerogaLottoBean;
import it.avlp.simog.beans.MotivoDerogaLottoBean;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.common.action.CupLottoAggAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriCup;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletLotto;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.DEROGA_QUALIFICAZIONE_SA;
import it.avlp.simog.db.generated.EAGG_CATEGORIE;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.IniziativaManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.log.LogManager;
import it.avlp.simog.rettifica.InfoRettifica;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;
import java.sql.SQLException;

public class SrvGestisciLotto extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see ServletBase#perform(HttpServletRequest, HttpServletResponse)
	 */

	public void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);

		if (checkSession(request)) {
			if (!currentUser.isAVLP()) {
				try {
					currentActiveConnection = getSimogConnection(request.getSession().getId(), getClass().getName());
					currentActiveConnection.setAutoCommit(false);

					logger.debug("Avvio Visualizzazione Lotto");

					long idLotto = Long.parseLong(request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO));

					logger.debug("Elaborazione Lotto [" + idLotto + "]");

					LottoManager lottoManager = new LottoManager(currentActiveConnection, logger);
					GaraManager gm = new GaraManager(currentActiveConnection, logger);
					// ListaDocumentiManager lista = new ListaDocumentiManager(
					// currentActiveConnection, logger );

					TableBean infoLotto = null;

					logger.debug("Ricerca da parte dell'utente [" + currentUser.getLogin() + "] su ID_LOTTO [" + idLotto
							+ "]");

					// pp organi costituzionali
					TableBeanRow tbr = null;

					// TICKET ALM - 3.04.3
					// Verifica se l'utente e' RUP di una SA che ha delegato la gestione dela gara
					// appartenente a questo lotto
					boolean isUserDelegante = false;
					if (currentUser.isRSSAorRUP())
						isUserDelegante = currentUser.getAmministrazioni()
								.get(gm.getCfAmmDelegata(0, idLotto, currentUser.getAmministrazioni())) != null;

					request.setAttribute(ParametriServlet.IS_DELEGA, "NOK"); // Inizializza attributo
					request.setAttribute(ParametriServlet.IS_ACC_QUADRO_NC, "NOK");// Inizializza attributo
					if (isUserDelegante) {
						request.setAttribute(ParametriServlet.IS_DELEGA, "OK");
					}

					if (currentUser.isRSSAorRUP() && !isUserDelegante) {
						infoLotto = lottoManager.getLottoByIdLottoRSSA(idLotto, currentUser.getUffici());
					} else {
						infoLotto = lottoManager.getLottoByIdLottoAmm(idLotto);
					}

					// Se ancora non ci sono righe allora e' lotto di una gara accordo quadro non di
					// competenza
					if (infoLotto.size() == 0) {
						Lotto lotto = lottoManager.getLotto(idLotto);
						if (lottoManager.checkCigAccQuadro(lotto.getCIG() + lotto.getCIG_kkk()) || gm
								.isGaraPostDelega(String.valueOf(lotto.getId_Gara()), null, currentUser.getUffici())) {
							request.setAttribute(ParametriServlet.IS_ACC_QUADRO_NC, "OK");
							infoLotto = lottoManager.getLottoByIdLottoAmm(idLotto);
						}
					}

					tbr = infoLotto.getRow(0);

					String idGara = tbr.getNulledField(GARA.ID_GARA);

					Gara gara = gm.getGara(Long.valueOf(idGara));

					// Ticket #20055
					if (currentUser.isRUP()) {
						InfoRettifica infoRettifica = new InfoRettifica();
						boolean result = infoRettifica.checkRettifica(gm, idGara);
						if (result) {
							request.setAttribute(ParametriServlet.RETTIFICA_GARA_LOTTI, result);
						}
					} else
						request.setAttribute(ParametriServlet.RETTIFICA_GARA_LOTTI, false);

					// TICKET ALM #659 - 3.04.4
					if (isUserDelegante) {
						request.setAttribute(ParametriServlet.HAS_AGG_CONFERMATE, currentUser.isRUP() && // Utente e' un
																											// RUP
								currentUser.getAmministrazioniByProfilo(ProfiloEnum.RUP).size() > 0 && // il RUP ha
																										// delle
																										// collaborazioni
								gm.checkPresaInCaricoDelega(gara.getId_Gara(), // Verifica se la gara e' prendibile
										gara.getID_F_DELEGATE(),
										currentUser.getAmministrazioniByProfilo(ProfiloEnum.RUP), gara.getNumeroLotti())
												? "OK"
												: "NOK");
					}
					// FINE TICKET ALM #659 - 3.04.4

					// TICKET MAC #10467
					request.setAttribute(ParametriServlet.RUP_CREATO_GARA,
							new LogManager(currentActiveConnection, logger).getCFRUP(Long.parseLong(idGara)));

					String actionType = request.getParameter(ParametriServlet.ACTION);

					GaraLottoAction gla = new GaraLottoAction(currentActiveConnection, logger, configuration);

					String dataCancellazioneLotto = tbr.getNulledField(Lotto.DATA_CANCELLAZIONE_LOTTO);
					boolean ignoraStato = !"".equals(dataCancellazioneLotto);

					// PP se competenza aggiudicazione devo escludere i record cancellati!
					ignoraStato = ignoraStato && configuration.isCUPLotto(gara.getData_creazione());

					CupLottoAggAction claAction = new CupLottoAggAction(currentActiveConnection, logger);
					List<CupLottoAggExt> elencoCup = gla.getBeanCup(request);

					if (!elencoCup.isEmpty()) {
						claAction.settingDatiDIPE(elencoCup);
					} else {
						elencoCup = claAction.getElencoCup(idLotto, null, null, ignoraStato);
					}

					request.setAttribute(ParametriCup.PARAM_ELENCO_CUP, elencoCup);

					// TICKET ALM #4219 - 3.04.4
					// Caricamento CPV Secondarie
					List<CpvLotto> listaCpvWeb = gla.getBeanCPVSecondarie(request);
					String cpvHtml = "";
					if (listaCpvWeb.isEmpty()) {
						List<CpvLotto> listaCpvDb = lottoManager.selectCpvLotto(idLotto);
						listaCpvWeb = new LinkedList<CpvLotto>();
						CPVEUManager cpvman = new CPVEUManager(currentActiveConnection, logger);
						for (CpvLotto cpv : listaCpvDb) {
							cpvHtml += "<p>" + cpv.getIdCpv() + " " + cpvman.getCPVDesc(cpv.getIdCpv()) + "</p>";
							cpv.setDescrizione(cpvman.getCPVDesc(cpv.getIdCpv()));
							listaCpvWeb.add(cpv);
						}
					}
					request.setAttribute("elencoCPVSecondarie", listaCpvWeb);
					request.setAttribute("elencoCPVSecondarieHtml", cpvHtml);

					// Lotto lotto = lottoManager.getLotto(idLotto);
					// Caricamento voci
					AggiudicazioniManager aMan = new AggiudicazioniManager(currentActiveConnection, logger);
					request.setAttribute(ParametriServlet.TIPO_APPALTO_BEAN_L,
							aMan.caricaLottoComboAppalto(Costanti.TIPO_SCHEDA_LAVORI, gara.getData_creazione()));
					request.setAttribute(ParametriServlet.TIPO_APPALTO_BEAN_F,
							aMan.caricaLottoComboAppalto(Costanti.TIPO_SCHEDA_FORNITURE, gara.getData_creazione()));

					// TICKET ALM #3835
					Scheda_A_Action saAction = new Scheda_A_Action(currentActiveConnection, logger);
					HashMap<String, String> mapCond = (HashMap<String, String>) saAction
							.loadCondizioniAggiuntive(PageHelper.getCurrentDate());
					request.setAttribute(ParametriServlet.CONDIZIONI_LOTTO_BEAN, mapCond);
					List<CondizioneLottoBean> elencoCondizioni = gla.getBeanCondizioni(request, idLotto);
					if (elencoCondizioni.isEmpty()) {
						CondizioniManager cm = new CondizioniManager(currentActiveConnection, logger);
						elencoCondizioni = cm.loadManyCondizioniLotto(idLotto, ignoraStato);
					}
					request.setAttribute(ParametriServlet.CONDIZIONI_LOTTO_SEL, elencoCondizioni);
					// FINE TICKET ALM #3835
					// Caricamento voci selezionate

					TipoAppaltoManager talMan = new TipoAppaltoManager(currentActiveConnection, logger);
					MisuraPremialeManager mpmMan = new MisuraPremialeManager(currentActiveConnection, logger);
					MotivoDerogaManager mdmMan = new MotivoDerogaManager(currentActiveConnection, logger);

					List<TipoAppaltoAggBean> elencoTipiAppaltoLottoL = gla.getBeanTipoAppLotto(request, idLotto,PSBD.FIELD_NAME_TIPO_APPALTO_AGG_L);
					if (elencoTipiAppaltoLottoL.isEmpty())
						elencoTipiAppaltoLottoL = talMan.loadMany(idLotto, Costanti.TIPO_SCHEDA_LAVORI,gara.getTIPO_SCHEDA_GARA(), ignoraStato);

					List<TipoAppaltoAggBean> elencoTipiAppaltoLottoSF = gla.getBeanTipoAppLotto(request, idLotto,PSBD.FIELD_NAME_TIPO_APPALTO_AGG_SF);
					if (elencoTipiAppaltoLottoSF.isEmpty())
						elencoTipiAppaltoLottoSF = talMan.loadMany(idLotto, Costanti.TIPO_SCHEDA_FORNITURE,gara.getTIPO_SCHEDA_GARA(), ignoraStato);

					request.setAttribute(ParametriServletLotto.TIPO_APPALTO_LOTTO_L, elencoTipiAppaltoLottoL);
					request.setAttribute(ParametriServletLotto.TIPO_APPALTO_LOTTO_SF, elencoTipiAppaltoLottoSF);

 
					MotivoDerogaManager motivoDerogaManager = new MotivoDerogaManager(currentActiveConnection, logger);
					MisuraPremialeManager misuraPremialeManager = new MisuraPremialeManager(currentActiveConnection, logger);
					
					request.setAttribute(ParametriServlet.MOTIVO_DEROGA_BEAN, motivoDerogaManager.caricaMotivoDeroga(gara.getData_creazione()));
					
					List<MotivoDerogaLottoBean> motivoDerogaLottoBeans = gla.getBeanMotivoDerogaLotto(request, idLotto, PSBD.FIELD_NAME_MOTIVO_DEROGA);
					if (motivoDerogaLottoBeans.isEmpty())
						motivoDerogaLottoBeans = motivoDerogaManager.loadManyNoFineValidita(idLotto);				
					request.setAttribute(ParametriServletLotto.MOTIVO_DEROGA_BEAN_SELECTED, motivoDerogaLottoBeans);
                    
					request.setAttribute(ParametriServlet.MISURA_PREMIALE_BEAN, misuraPremialeManager.caricaMisurePremiali(gara.getData_creazione()));
					
					List<MisuraPremialeLottoBean> misuraPremialeLottoBeans = gla.getBeanMisuraPremialeLotto(request, idLotto, PSBD.FIELD_NAME_MISURA_PREMIALE);
                    if(misuraPremialeLottoBeans.isEmpty()) {
                    	misuraPremialeLottoBeans = misuraPremialeManager.loadManyNoFineValidita(idLotto);
                    }
					request.setAttribute(ParametriServletLotto.MISURA_PREMIALE_BEAN_SELECTED, misuraPremialeLottoBeans);


//					// INSERISCO LE TIPOLOGICHE IN SESSIONE
//					List<MotivoDerogaLottoBean> elencoMotivoDeroga = gla.getBeanMotivoDeroga(request, PSBD.FIELD_NAME_MOTIVO_DEROGA);
//					request.setAttribute(ParametriServlet.MOTIVO_DEROGA_TABLEBEAN, elencoMotivoDeroga);
//					List<MisuraPremialeBean> elencoMisuraPremiale = gla.getBeanMisurePremiali(request, PSBD.FIELD_NAME_MISURA_PREMIALE);
//					request.setAttribute(ParametriServlet.MISURA_PREMIALE_TABLEBEAN, elencoMisuraPremiale);
//
//					// CARICO I FLAG IN SESSIONE 
//					// Prima si cerca in sessione
//					List<MotivoDerogaLottoBean> motivoDerogaLottoBean = gla.getBeanMisurePremiali(request, idLotto,PSBD.FIELD_NAME_TIPO_APPALTO_AGG_L);
//					// Se in sessione non ci sono si caricano dal db
//					if (elencoTipiAppaltoLottoL.isEmpty())
//						elencoTipiAppaltoLottoL = talMan.loadMany(idLotto, Costanti.TIPO_SCHEDA_LAVORI,gara.getTIPO_SCHEDA_GARA(), ignoraStato);
//
//					List<TipoAppaltoAggBean> elencoTipiAppaltoLottoSF = gla.getBeanTipoAppLotto(request, idLotto,PSBD.FIELD_NAME_TIPO_APPALTO_AGG_SF);
//					if (elencoTipiAppaltoLottoSF.isEmpty())
//						elencoTipiAppaltoLottoSF = talMan.loadMany(idLotto, Costanti.TIPO_SCHEDA_FORNITURE,gara.getTIPO_SCHEDA_GARA(), ignoraStato);
//
//					request.setAttribute(ParametriServletLotto.TIPO_APPALTO_LOTTO_L, elencoTipiAppaltoLottoL);
//					request.setAttribute(ParametriServletLotto.TIPO_APPALTO_LOTTO_SF, elencoTipiAppaltoLottoSF);

					
					
					
					
					
					

					// TICKET ALM #4222 - 3.04.4
					// Recupera info sulle autodichiarazioni o sull'adesionea una iniziativa
					IniziativaManager im = new IniziativaManager(currentActiveConnection, logger);
					Lotto lottoDb = lottoManager.getLotto(idLotto);
					List<Long> autodichiarazioni = im.getAutodichiarazioni(idLotto, lottoDb.getCOD_CATEGORIA());
					boolean isAuto = false;
					for (Long auto : autodichiarazioni) {
						if (auto == Costanti.INIZIATIVE_NON_IDONEE) {
							isAuto = true;
							request.setAttribute(ParametriServlet.FIELD_NAME_FLAG_SA_NO_DPCM, "OK");
						}
						if (auto == Costanti.SA_NON_CLASSIFICATA) {
							isAuto = true;
							request.setAttribute(ParametriServlet.FIELD_NAME_FLAG_SA_NO_CLASSIFICATA, "OK");
						}
					}
					if (!isAuto && gara.getCIG_ACC_QUADRO() != null) {
						List<IniziativaSoggAggr> cigIniziativa = im.getIniziative(gara.getCIG_ACC_QUADRO(), null, null,
								null, null, false);
						if (cigIniziativa.size() > 0)
							request.setAttribute(ParametriServlet.FIELD_NAME_CIG_INIZIATIVA_SEL,
									gara.getCIG_ACC_QUADRO());
					}
					// FINE TICKET ALM #4222 - 3.04.4

					request.setAttribute(TABLEBEAN, infoLotto);
					request.setAttribute(ParametriServlet.ACTION, actionType);

					logger.debug("Avvio elaborazione Tabelle di utilita'");

					TableBeanRow firstRow = infoLotto.getRow(0);

					// TICKET ALM - 3.04.2 NG
					if (SimogFlags.is3042Active())
						setTabelleUtilita(request, currentActiveConnection,
								firstRow.getNulledField(GARA.DATA_CREAZIONE), gara.isOrganoCost(), null);
					else
						setTabelleUtilita(request, currentActiveConnection, PageHelper.getCurrentDate(),
								gara.isOrganoCost(), null);
					// FINE TICKET ALM - 3.04.2 NG

					// TICKET ALM - 3.04.4
					// Recupera le categorie selezionate in gara e inseriscile nella lista delle
					// opzioni selezionabili
					if (gara.getData_creazione()
							.compareTo(SimogProperties.getInstance().getDataAttivazione3044()) >= 0) {
						Map<String, String> listaCategorie = new AccessiDB(currentActiveConnection, logger)
								.getTipologica(EAGG_CATEGORIE.TABLE_NAME, EAGG_CATEGORIE.COD_CATEGORIA,
										EAGG_CATEGORIE.DESCRIZIONE, EAGG_CATEGORIE.DATA_INIZIO_VALIDITA,
										EAGG_CATEGORIE.DATA_FINE_VALIDITA,
										PageHelper.parseTimeYMD(gara.getData_creazione()));
						Map<String, String> listaCategorieLotto = new HashMap<String, String>();
						for (String codGara : gara.getCatMercArray()) {

							for (Map.Entry<String, String> entry : listaCategorie.entrySet()) {
								if (codGara.equals(entry.getKey()))
									listaCategorieLotto.put(entry.getKey(), entry.getValue());
							}

						}

						request.setAttribute(LISTA_CATEGORIE_LOTTO, listaCategorieLotto);
					}
					// FINE TICKET ALM - 3.04.4

					request.setAttribute(ParametriServlet.FIELD_NAME_MOTIVO_URGENZA,gara.getID_ESTREMA_URGENZA());
					
					//3.04.9 40610
                    AccessiDB dbManager = null;
            		dbManager = new AccessiDB(currentActiveConnection, logger);
                    request.setAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_BEAN, dbManager.executeSelectDerogaQualificazioneSA(DEROGA_QUALIFICAZIONE_SA.TABLE_NAME, DEROGA_QUALIFICAZIONE_SA.DATA_FINE_VALIDITA, DEROGA_QUALIFICAZIONE_SA.DATA_INIZIO_VALIDITA, DEROGA_QUALIFICAZIONE_SA.DESCRIZIONE, PageHelper.getCurrentDate(), false));
                    List<String> listaDerogaQualificazioneSA= new ArrayList<String>();
                    request.setAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_BEAN_SELECTED, listaDerogaQualificazioneSA);

					
					logger.debug("Tabelle di utilita' correttamente impostate");

					// TICKET ALM #22951
					if (gara.getData_creazione()
							.compareTo(SimogProperties.getInstance().getDataAttivazione3046()) >= 0) {

						if (gara.getCIG_ACC_QUADRO() != null && !"".equals(gara.getCIG_ACC_QUADRO())) {
							LottoManager lm = new LottoManager(currentActiveConnection, logger);
							java.util.List<Lotto> lottoListAccQ = lm.getLottoByCigWS(gara.getCIG_ACC_QUADRO());
							if (lottoListAccQ != null && !lottoListAccQ.isEmpty()) {
							CPVEUManager cpvMan = new CPVEUManager(currentActiveConnection, logger);
							Map<String, String> listaCPV = new HashMap<String, String>();
							Lotto lottoAccQ = lottoListAccQ.get(0);
							String cpvPrevalente = lottoAccQ.getId_CPV();
							listaCPV.put(cpvPrevalente, cpvPrevalente + " - " + (cpvMan.getCPVDesc(cpvPrevalente)));
							for (CpvLotto cpvSecondaria : lottoAccQ.getElencoCpvSecondarie()) {
								cpvSecondaria.setDescrizione(cpvMan.getCPVDesc(cpvSecondaria.getIdCpv()));
								listaCPV.put(cpvSecondaria.getIdCpv(),
										cpvSecondaria.getIdCpv() + " - " + cpvSecondaria.getDescrizione());
							}

							request.setAttribute("LISTA_CPV_ADESIONE", listaCPV);
							}
						}
					}
					
					// fix 34470 3.04.8
//					PubblicazioneManager pubManager = new PubblicazioneManager(currentActiveConnection, logger);
//					PubblicazioneBean pubblicazione = pubManager.getPubblicazione(gara.getIdPubblicazione(), gara.getDataInizioPubblicazione());
					String linkAffidamentoDiretto = gara.getLINK_AFFIDAMENTO_DIRETTO();
					if (linkAffidamentoDiretto == null) {
						request.setAttribute(ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO, "");
					}else {
						request.setAttribute(ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO, linkAffidamentoDiretto);
					}

					String targetPage = null;

					MotivazioniBean motiviCanc = new MotivazioniBean();
					motiviCanc.loadAll(currentActiveConnection, logger, false);

					String codiciProceduraRistretta = configuration.getCodiciProceduraRistretta();
					boolean proceduraRistretta = false;
					if (codiciProceduraRistretta != null && !"".equals(codiciProceduraRistretta)) {
						codiciProceduraRistretta = codiciProceduraRistretta
								.replaceAll(Costanti.FLAG_PROCEDURA_NEGOZIATA, "");// non ci interessa la flag "x"
						proceduraRistretta = lottoManager.isProceduraRistretta(gara.getId_Gara(),
								codiciProceduraRistretta);
					} else {
						throw new SimogException(
								"Parametro[" + SimogProperties.CODICI_PROCEDURA_RISTRETTA + "] mancante in simog.ini");
					}
					request.setAttribute("flgProceduraRistretta", proceduraRistretta);
					
					request.setAttribute(ParametriServlet.FLAG_PNRR_PNC, lottoDb.getFLAG_PNRR_PNC());
					request.setAttribute(ParametriServlet.FLAG_PREVISIONE_QUOTA, lottoDb.getFLAG_PREVISIONE_QUOTA());
					request.setAttribute(ParametriServlet.QUOTA_GIOVANILE, lottoDb.getQuotaGiovanile());
					request.setAttribute(ParametriServlet.QUOTA_FEMMINILE, lottoDb.getQuotaFemminile());
					List<MotivoDerogaLottoBean> motivoDerogaLottoBeansAQ = gla.getBeanMotivoDerogaLotto(request, lottoDb.getId_Lotto(), PSBD.FIELD_NAME_MOTIVO_DEROGA);
					if (motivoDerogaLottoBeans.isEmpty())
						motivoDerogaLottoBeans = motivoDerogaManager.loadManyNoFineValidita(lottoDb.getId_Lotto());				
					request.setAttribute(ParametriServletLotto.MOTIVO_DEROGA_BEAN_SELECTED, motivoDerogaLottoBeans);
                    request.setAttribute(ParametriServlet.FLAG_MISURE_PREMIALI,lottoDb.getFLAG_MISURE_PREMIALI());
                    List<MisuraPremialeLottoBean> misuraPremialeLottoBeansAQ = gla.getBeanMisuraPremialeLotto(request, lottoDb.getId_Lotto(), PSBD.FIELD_NAME_MISURA_PREMIALE);
                    if(misuraPremialeLottoBeans.isEmpty()) {
                    	misuraPremialeLottoBeans = misuraPremialeManager.loadManyNoFineValidita(lottoDb.getId_Lotto());
                    }
					request.setAttribute(ParametriServletLotto.MISURA_PREMIALE_BEAN_SELECTED, misuraPremialeLottoBeans);
				
					
					//MEV 37010 3.04.8.1 se cig di adesione
					String currentDate = PageHelper.getCurrentDate();
					//SE LA MEV è ATTIVATA 
					String mostraCampoDerogaAdesione = "false";
					String isEreditati = "false";
					if (currentDate.compareTo(SimogProperties.getInstance().getDataAttivazioneMev37010()) >= 0) {
					
					if (gara.getID_MODO_REAL() == Costanti.MODOREAL_ADESIONE_NOCOMPET ||
							gara.getID_MODO_REAL() == Costanti.MODOREAL_ADESIONE ||
							gara.getID_MODO_REAL() == Costanti.MODOREAL_CONCESSIONE ||
							gara.getID_MODO_REAL() == Costanti.MODOREAL_CONCESSIONE_NOCOMPET ) {
						
						try {
							java.util.List<Lotto> lottoListAQ = lottoManager.getLottoByCigWS(gara.getCIG_ACC_QUADRO());
							if (lottoListAQ != null && !lottoListAQ.isEmpty()) {
							Lotto lottoAQ = lottoListAQ.get(0);

							if (lottoAQ.getData_Pubblicazione() != null) {
								String dataPubblicazioneAQ = PageHelper.getFormattedDBDate(lottoAQ.getData_Pubblicazione());
								String dataCreazioneGaraAdesione = PageHelper.getFormattedDBDate(gara.getData_creazione());
								//Se il cig AQ padre pubblicato prima della data nel file di configurazione e la gara di adesione creata dopo la data nel file di configurazione
								if (SimogProperties.getInstance()
										.isDataCreatedBeforeDerogaAdesione(dataPubblicazioneAQ) &&
										SimogProperties.getInstance()
										.isDataCreatedAfterDerogaAdesione(dataCreazioneGaraAdesione) &&
										SimogProperties.getInstance()
										.isDataCreatedAfter3047(dataCreazioneGaraAdesione)) {
									mostraCampoDerogaAdesione = "true"; //da usare nelle jsp per mostrare oppure no il campo
									
								}//Se il cig AQ padre pubblicato dopo la 3.04.7 o se i dati pari opportunita sono stati integrati allora il figlio eredita tutti i campi dal padre
								else if (lottoAQ.getFLAG_PNRR_PNC()!= null && !lottoAQ.getFLAG_PNRR_PNC().equals("")) {
//3.04.9 mev 41375 commentato tutto perchè si prende i valori dal lotto salvato correttamente
//									request.setAttribute(ParametriServlet.FLAG_PNRR_PNC, lottoAQ.getFLAG_PNRR_PNC());
//									request.setAttribute(ParametriServlet.FLAG_PREVISIONE_QUOTA, lottoAQ.getFLAG_PREVISIONE_QUOTA());
//									request.setAttribute(ParametriServlet.QUOTA_GIOVANILE, lottoAQ.getQuotaGiovanile());
//									request.setAttribute(ParametriServlet.QUOTA_FEMMINILE, lottoAQ.getQuotaFemminile());
//									List<MotivoDerogaLottoBean> motivoDerogaLottoBeansAQ = gla.getBeanMotivoDerogaLotto(request, lottoAQ.getId_Lotto(), PSBD.FIELD_NAME_MOTIVO_DEROGA);
//									if (motivoDerogaLottoBeans.isEmpty())
//										motivoDerogaLottoBeans = motivoDerogaManager.loadManyNoFineValidita(lottoAQ.getId_Lotto());				
//									request.setAttribute(ParametriServletLotto.MOTIVO_DEROGA_BEAN_SELECTED, motivoDerogaLottoBeans);
//				                    request.setAttribute(ParametriServlet.FLAG_MISURE_PREMIALI,lottoAQ.getFLAG_MISURE_PREMIALI());
//				                    List<MisuraPremialeLottoBean> misuraPremialeLottoBeansAQ = gla.getBeanMisuraPremialeLotto(request, lottoAQ.getId_Lotto(), PSBD.FIELD_NAME_MISURA_PREMIALE);
//				                    if(misuraPremialeLottoBeans.isEmpty()) {
//				                    	misuraPremialeLottoBeans = misuraPremialeManager.loadManyNoFineValidita(lottoAQ.getId_Lotto());
//				                    }
//									request.setAttribute(ParametriServletLotto.MISURA_PREMIALE_BEAN_SELECTED, misuraPremialeLottoBeans);
									//3.04.9 mev 41375 ereditati e quindi non sono modificabili solo se il padre AQ ha il pnrr a SI
									if (lottoAQ.getFLAG_PNRR_PNC().equals("S")) {
										isEreditati = "true"; 
									}
									
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
					
					}
					request.setAttribute(ParametriServletLotto.IS_EREDITATI, isEreditati);
					request.setAttribute(ParametriServlet.MOSTRA_DEROGA_ADESIONE, mostraCampoDerogaAdesione);
				
					
					//MEV 37010 3.04.8.1
					
					// 3.04.9 40610
					String mostraCampoDerogaQualificazioneSA = "false";
					if (currentDate.compareTo(SimogProperties.getInstance().getAttivazioneMevQualificazioneSA()) >= 0) {
						mostraCampoDerogaQualificazioneSA = "true";
						request.setAttribute(PSBD.FIELD_NAME_DEROGA_QUALIFICAZIONE_SA, lottoDb.getDerogaQualificazioneSA());
					}
					// fix 40610 3.04.9
					if (lottoDb.getDerogaQualificazioneSA() != null && !lottoDb.getDerogaQualificazioneSA().equals("")) {
						String descrDerogaQualificazioneSA = lottoManager.getDerogaQualificazioneSAByID(Long.parseLong(lottoDb.getDerogaQualificazioneSA()));
						if (descrDerogaQualificazioneSA == null) {
							request.setAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_VISUAL, "");
							//request.setAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_VISUAL, "");
						}else {
							request.setAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_VISUAL, descrDerogaQualificazioneSA);
							//request.setAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_VISUAL, descrDerogaQualificazioneSA);
						}
					}else {
						request.setAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_VISUAL, "");
						//request.setAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_VISUAL, "");
					}

					request.setAttribute(ParametriServlet.MOSTRA_DEROGA_QUALIFICAZIONE_SA, mostraCampoDerogaQualificazioneSA);

					//MEV 3.04.10 43227
								
					String mostraDatiFase1 = "false";
					String mostraDatiFase2 = "false";
					if ((lottoDb.getDATA_SCADENZA_PAGAMENTI() != null && !"".equals(lottoDb.getDATA_SCADENZA_PAGAMENTI()))
							&&(lottoDb.getId_Scelta_Contraente().equals("2") || lottoDb.getId_Scelta_Contraente().equals("13") || lottoDb.getId_Scelta_Contraente().equals("25"))
							&& (lottoDb.getDataScadenzaRichiestaInvito() != null && !"".equals(lottoDb.getDataScadenzaRichiestaInvito()))
							&& (lottoDb.getDataLetteraInvito() != null && !"".equals(lottoDb.getDataLetteraInvito()))) {
							mostraDatiFase2 = "true";
						}else if ((lottoDb.getDataScadenzaRichiestaInvito() != null && !"".equals(lottoDb.getDataScadenzaRichiestaInvito()))
								&& (lottoDb.getId_Scelta_Contraente().equals("2") || lottoDb.getId_Scelta_Contraente().equals("13") || lottoDb.getId_Scelta_Contraente().equals("25"))) {
							mostraDatiFase1 = "true";
						}else  {
							mostraDatiFase2 = "true";
						}
						
					
					request.setAttribute(ParametriServlet.MOSTRA_DATI_FASE_UNO, mostraDatiFase1);
					request.setAttribute(ParametriServlet.MOSTRA_DATI_FASE_DUE, mostraDatiFase2);
					
					// FINE MEV 3.04.10 43227
					//FINE MEV 3.04.10 43227
					if (actionType.equals("modifica")) {
						targetPage = JSP_MODIFICA_LOTTO;
					} else if (actionType.equals("perfeziona")) {
						targetPage = JSP_PERFEZIONA_LOTTO;
					} else if (actionType.equals("cancella")) {
						request.setAttribute(ParametriServletLotto.MOTIVAZIONI_LIST, motiviCanc.loadMotivazioni());
						targetPage = JSP_CANCELLA_LOTTO;
					} else if (actionType.equals("dettaglioLotto")) {
						targetPage = JSP_VISUALIZZA_LOTTO;
					} else if (actionType.equals(ParametriServlet.ACTION_MODIFICA_CONTRATTO_ESCLUSO)) {
						targetPage = JSP_MODIFICA_LOTTO;
					} else if (actionType.equals(ParametriServlet.ACTION_MODIFICA_RIPETIZIONI)) {
						targetPage = JSP_MODIFICA_LOTTO;
					} else if (actionType.equals(ParametriServlet.ACTION_INTEGRA_PARI_OPPORTNITA)) { //MEV 37010 3.04.8.1
						//la riga di sotto va commentata perchè se è un adesione e ha dati ereditati per modificare i dati pari opportunità vanno modificati quelli del padre AQ/Conv integrandoli
						//in quanto una adesione eredita i dati dal padre con l'integrazione al padre verranno ereditati anche ai figli
						//quindi se AQ/Conv ha sezione pari opportunità compilata allora l'adesione eredita e non può integrare (se non cambiare il pnrr pnc a NO come indicato nella mev 41375)
						//se AQ/Conv ha sezione pari opportunità non compilata allora all'adesione si può integrare
						//request.setAttribute(ParametriServletLotto.IS_EREDITATI, "false"); //se sto integrando è perchè voglio poterli modificare MEV 46937 3.04.12
						targetPage = JSP_MODIFICA_LOTTO;
					}else if (actionType.equals(ParametriServlet.ACTION_MODIFICA_DATI_PERFEZIONAMENTO)) { //MEV 3.04.10 43227
						targetPage = JSP_MODIFICA_LOTTO;
					}else if (actionType.equals(ParametriServlet.ACTION_MODIFICA_CPV)) { //MEV 53643 3.04.13
						targetPage = JSP_MODIFICA_LOTTO;
					} else if (actionType.equals(ParametriServlet.ACTION_MODIFICA_CAT_SOA)) { //- MAD 68089 3.04.16
						targetPage = JSP_MODIFICA_LOTTO;
					} else if (actionType.equals(ParametriCup.ACTION_MODIFICA_DATI_CUP)) {
						request.setAttribute(ParametriCup.PARAM_MOD_INT_CUP, Boolean.TRUE);
						targetPage = JSP_MODIFICA_LOTTO;
					} else if (actionType.equals("ripristina")) {
						Lotto lotto = lottoManager.getLotto(idLotto);
						lottoManager.ripristinaLotto(idLotto);

						if (configuration.isCUPLotto(gara.getData_creazione())) {
							TipoAppaltoManager tam = new TipoAppaltoManager(currentActiveConnection, logger);
							tam.ripristinaAppaltiLotto(idLotto,
									lotto.getData_Pubblicazione() != null ? StatiScheda.CONFERMATO
											: StatiScheda.IN_DEFINIZIONE);
						}

						if (configuration.isCUPLotto(gara.getData_creazione())) {
							CupLottoAggManager cam = new CupLottoAggManager(currentActiveConnection, logger);
							cam.ripristinaCup(idLotto, lotto.getData_Pubblicazione() != null ? StatiScheda.CONFERMATO
									: StatiScheda.IN_DEFINIZIONE);
						}

						LogManager logManager = new LogManager(currentActiveConnection, logger);
						logManager.log(getTodayDate(), gara.getID_STAZIONE_APPALTANTE(), currentUser.getLogin(),
								lotto.getCIG() + lotto.getCIG_kkk(), LogManager.RIPR_LOTTO,
								gara.getCF_AMMINISTRAZIONE(), Long.toString(idLotto), idGara);

						targetPage = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara + "&"
								+ ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;

						request.setAttribute(ERRORBEAN, new MessageBean("CIG ripristinato"));
					} else {
						sendError(request, response, SIMOG_LOGIN_003, JSP_ERRORE);
						return;
					}
					request.setAttribute(ParametriServlet.ACTION, actionType);
					forward(targetPage, request, response);

				} catch (Exception e) {
					rollback(currentActiveConnection);
					e.printStackTrace();
					sendError(request, response, SIMOG_LOTTO_011, JSP_ERRORE, e);
					return;
				} finally {
					commit(currentActiveConnection);
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

//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		perform(request, response);
//	}
}
