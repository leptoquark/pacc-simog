package it.avlp.simog.garamanager.lotto.app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.MisuraPremialeManager;
import it.avcp.simog.managers.aggiudicazione.MotivoDerogaManager;
import it.avcp.simog.managers.cpv.CPVEUManager;
import it.avlp.simog.actions.DelegaDatiSimogAction;
import it.avlp.simog.actions.GaraLottoAction;
import it.avlp.simog.actions.aggiudicazione.Scheda_A_Action;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.CpvLotto;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.MisuraPremialeLottoBean;
import it.avlp.simog.beans.MotivoDerogaLottoBean;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.common.action.CupLottoAggAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriCup;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletLotto;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.CATEGORIA;
import it.avlp.simog.db.generated.DEROGA_QUALIFICAZIONE_SA;
import it.avlp.simog.db.generated.EAGG_CATEGORIE;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

public class SrvInizializzaLotto extends ServletBase {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	protected void perform(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if ( checkSession(request) ) {
			if (  currentUser.isRSSAorRUP() ) {
				try {
					visualizzaListaParametriValori(request, response);
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					request.setAttribute(LOTTO.DATA_COMUNICAZIONE, "");
					//request.setAttribute(ParametriServlet.FROM_GARE, Costanti.FLAG_VALORE_SI);		
					GaraManager gm = new GaraManager(currentActiveConnection, logger);
					LottoManager lm = new LottoManager(currentActiveConnection, logger);
					String idGara = (String)request.getSession().getAttribute(ParametriServlet.SESSION_ID_GARA);
					
					DelegaDatiSimogAction dasAction = new DelegaDatiSimogAction(currentActiveConnection, logger);
					GaraLottoAction gla = new GaraLottoAction(currentActiveConnection,logger, configuration);
					
					Gara gara = new Gara();
					String res = null;
					
					// verifico se ci sono errori in request
					Object errs = request.getAttribute(ERRORBEAN);
					
					

					if (idGara != null ) {					
						gara = gm.getGara(Long.valueOf(idGara), currentUser.getUffici());
						if(gara == null){
							sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
							return;
						}
						
						//Ticket ALM #1198
						//Verifica che la gara sia perfezionata
			    		if(gara.getDATA_PERFEZIONAMENTO_BANDO() != null) {
			    			 sendError(request, response,Messaggi.SIMOG_LOTTO_016, JSP_ERRORE);
						     return;
			    		}
						//Fine Ticket ALM #1198
						
						//gm nuovo codice simog 3.06
			    		String sceltaContraenteParameter = request.getParameter(ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE);
						if(gara.getID_MODO_REAL()==Costanti.MODOREAL_ADESIONE || gara.getID_MODO_REAL()==Costanti.MODOREAL_CONCESSIONE){
							//gm solo in fase di creazione lotto si precompila il campo scelta contraente
							if(sceltaContraenteParameter==null)
								request.setAttribute(ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE, String.valueOf(Costanti.CON_COM_ADESIONE));
						}
						//3.04.8 34190 fix
                  if(gara.getID_MODO_REAL()==Costanti.MODOREAL_ADESIONE_NOCOMPET || gara.getID_MODO_REAL()==Costanti.MODOREAL_CONCESSIONE_NOCOMPET){
                  	//gm solo in fase di creazione lotto si precompila il campo scelta contraente
                  	if(sceltaContraenteParameter==null)
                  		request.setAttribute(ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE, String.valueOf(Costanti.AFF_DIR_ADESIONE));
						}

                  // pp organi costituzionali, precompilazione scelta contraente
                  if(gara.isOrganoCost() && sceltaContraenteParameter==null)
                	  request.setAttribute(ParametriServlet.FIELD_NAME_SCELTA_CONTRAENTE, Costanti.SCELTA_CONTRAENTE_OOCC);
                  
                  if( SimogFlags.is3031_RFWEBGL02Active() )
                  {
                     List<CupLottoAggExt> listaCup = gla.getBeanCup(request);
                     if( !listaCup.isEmpty() ){
                        CupLottoAggAction claAction = new CupLottoAggAction(currentActiveConnection, logger);

                        claAction.settingDatiDIPE(listaCup);
                     } 

                     request.setAttribute(ParametriCup.PARAM_ELENCO_CUP, listaCup);
                  }
                  
                //TICKET ALM #4219 - 3.04.4
					List<CpvLotto> listaCpv = gla.getBeanCPVSecondarie(request);
					request.setAttribute("elencoCPVSecondarie", listaCpv);
                 
                     //Caricamento voci
                     AggiudicazioniManager aMan = new AggiudicazioniManager(currentActiveConnection, logger);
                     MotivoDerogaManager motivoDerogaManager = new MotivoDerogaManager(currentActiveConnection, logger);
                     MisuraPremialeManager misuraPremialeManager = new MisuraPremialeManager(currentActiveConnection, logger);
                     
                     request.setAttribute(ParametriServlet.TIPO_APPALTO_BEAN_L, aMan.caricaLottoComboAppalto(Costanti.TIPO_SCHEDA_LAVORI, gara.getData_creazione()));
                     request.setAttribute(ParametriServlet.TIPO_APPALTO_BEAN_F, aMan.caricaLottoComboAppalto(Costanti.TIPO_SCHEDA_FORNITURE, gara.getData_creazione()));
                     
                     request.setAttribute(ParametriServletLotto.TIPO_APPALTO_LOTTO_L, gla.getBeanTipoAppLotto(request, -1L, PSBD.FIELD_NAME_TIPO_APPALTO_AGG_L));
                     request.setAttribute(ParametriServletLotto.TIPO_APPALTO_LOTTO_SF, gla.getBeanTipoAppLotto(request, -1L, PSBD.FIELD_NAME_TIPO_APPALTO_AGG_SF));

                     request.setAttribute(ParametriServlet.MOTIVO_DEROGA_BEAN, motivoDerogaManager.caricaMotivoDeroga(gara.getData_creazione()));
                     request.setAttribute(ParametriServlet.MISURA_PREMIALE_BEAN, misuraPremialeManager.caricaMisurePremiali(gara.getData_creazione()));
                     
                     request.setAttribute(ParametriServletLotto.MOTIVO_DEROGA_BEAN_SELECTED, gla.getBeanMotivoDerogaLotto(request, -1L, PSBD.FIELD_NAME_MOTIVO_DEROGA));
                     request.setAttribute(ParametriServletLotto.MISURA_PREMIALE_BEAN_SELECTED, gla.getBeanMisuraPremialeLotto(request, -1L, PSBD.FIELD_NAME_MISURA_PREMIALE));

                     
                     request.setAttribute(ParametriServlet.FIELD_NAME_SOMMA_URGENZA, gara.getURGENZA_DL133());
                     request.setAttribute(ParametriServlet.FIELD_NAME_MOTIVO_URGENZA, gara.getID_ESTREMA_URGENZA());

                     //3.04.9 40610
                     AccessiDB dbManager = null;
             		 dbManager = new AccessiDB(currentActiveConnection, logger);
                     request.setAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_BEAN, dbManager.executeSelectDerogaQualificazioneSA(DEROGA_QUALIFICAZIONE_SA.TABLE_NAME, DEROGA_QUALIFICAZIONE_SA.DATA_FINE_VALIDITA,DEROGA_QUALIFICAZIONE_SA.DATA_INIZIO_VALIDITA, DEROGA_QUALIFICAZIONE_SA.DESCRIZIONE, PageHelper.getCurrentDate(), false));
                     List<String> listaDerogaQualificazioneSA= new ArrayList<String>();
                     request.setAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_BEAN_SELECTED, listaDerogaQualificazioneSA);

                  
                   //TICKET ALM #3835
					//Carica la lista delle condizioni
					Scheda_A_Action saAction = new Scheda_A_Action(currentActiveConnection, logger);
					request.setAttribute(ParametriServlet.CONDIZIONI_LOTTO_BEAN, saAction.loadCondizioniAggiuntive(PageHelper.getCurrentDate()));
					request.setAttribute(ParametriServlet.CONDIZIONI_LOTTO_SEL, gla.getBeanCondizioniInit(request, -1L,Integer.parseInt(idGara)));
                 
                  //FINE TICKET ALM #3835
                  
                    
					res = dasAction.getDelegaCIGMessage(gara.getID_OSSERVATORIO(), PageHelper.getCurrentUtilDate(), currentUser);

						
						if (res != null && errs instanceof AllValidationBeans) {
							AllValidationBeans temp = (AllValidationBeans)errs;
							temp.addValidationInfo(res);
							request.setAttribute(ERRORBEAN, temp);
						}
						
						String oggettoPar = request.getParameter(ParametriServlet.FIELD_NAME_OGGETTO_LOTTO);
						if(oggettoPar==null && gara.getNumeroLotti() != null && gara.getNumeroLotti() == 1)
							request.setAttribute(ParametriServlet.FIELD_NAME_OGGETTO_LOTTO, gara.getOggetto());
						
						request.setAttribute(ParametriServlet.IS_ORGANO, gara.isOrganoCost() ? Costanti.FLAG_VALORE_SI : Costanti.FLAG_VALORE_NO);
						
						
						//MEV 37010 3.04.8.1
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
								java.util.List<Lotto> lottoListAQ = lm.getLottoByCigWS(gara.getCIG_ACC_QUADRO());
								if (lottoListAQ != null && !lottoListAQ.isEmpty()) {
									Lotto lottoAQ = lottoListAQ.get(0);

									if (lottoAQ.getData_Pubblicazione() != null) {
										String dataPubblicazioneAQ = PageHelper.getFormattedDBDate(lottoAQ.getData_Pubblicazione());
										String dataCreazioneGaraAdesione = PageHelper.getFormattedDBDate(gara.getData_creazione());
										//Se il cig AQ padre pubblicato prima della data nel file di configurazione e la gara di adesione creata dopo la data nel file di configurazione e dopo la 3.04.7
										//mostrare il nuovo flag deroga adesione
										if (dataPubblicazioneAQ != null && dataCreazioneGaraAdesione != null && SimogProperties.getInstance()
												.isDataCreatedBeforeDerogaAdesione(dataPubblicazioneAQ) &&
												SimogProperties.getInstance()
												.isDataCreatedAfterDerogaAdesione(dataCreazioneGaraAdesione) &&
												SimogProperties.getInstance()
												.isDataCreatedAfter3047(dataCreazioneGaraAdesione)) {
											mostraCampoDerogaAdesione = "true"; //da usare nelle jsp per mostrare oppure no il campo
										}//se i dati pari opportunita sono presenti nel AQ allora il figlio eredita tutti i campi dal padre
										else if (lottoAQ.getFLAG_PNRR_PNC()!= null && !lottoAQ.getFLAG_PNRR_PNC().equals("")) {

											//eredita comunque perchè i dati possono essere integrati per cig AQ pubblicati prima della 3.04.7
											//3.04.9 mev 41375 ereditati e quindi non sono modificabili solo se il padre AQ ha il pnrr a SI
											request.setAttribute(ParametriServlet.FLAG_PNRR_PNC, lottoAQ.getFLAG_PNRR_PNC());
											if (lottoAQ.getFLAG_PNRR_PNC().equals("S")) {
												isEreditati = "true"; 
												request.setAttribute(ParametriServlet.FLAG_PREVISIONE_QUOTA, lottoAQ.getFLAG_PREVISIONE_QUOTA());
												request.setAttribute(ParametriServlet.QUOTA_GIOVANILE, lottoAQ.getQuotaGiovanile());
												request.setAttribute(ParametriServlet.QUOTA_FEMMINILE, lottoAQ.getQuotaFemminile());
												List<MotivoDerogaLottoBean> motivoDerogaLottoBeans = gla.getBeanMotivoDerogaLotto(request, lottoAQ.getId_Lotto(), PSBD.FIELD_NAME_MOTIVO_DEROGA);
												if (motivoDerogaLottoBeans.isEmpty())
													motivoDerogaLottoBeans = motivoDerogaManager.loadManyNoFineValidita(lottoAQ.getId_Lotto());				
												request.setAttribute(ParametriServletLotto.MOTIVO_DEROGA_BEAN_SELECTED, motivoDerogaLottoBeans);
							                    request.setAttribute(ParametriServlet.FLAG_MISURE_PREMIALI,lottoAQ.getFLAG_MISURE_PREMIALI());
							                    List<MisuraPremialeLottoBean> misuraPremialeLottoBeans = gla.getBeanMisuraPremialeLotto(request, lottoAQ.getId_Lotto(), PSBD.FIELD_NAME_MISURA_PREMIALE);
							                    if(misuraPremialeLottoBeans.isEmpty()) {
							                    	misuraPremialeLottoBeans = misuraPremialeManager.loadManyNoFineValidita(lottoAQ.getId_Lotto());
							                    }
												request.setAttribute(ParametriServletLotto.MISURA_PREMIALE_BEAN_SELECTED, misuraPremialeLottoBeans);
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
						}
						request.setAttribute(ParametriServlet.MOSTRA_DEROGA_QUALIFICAZIONE_SA, mostraCampoDerogaQualificazioneSA);
					}
					
					//TICKET ALM - 3.04.2 NG
					   setTabelleUtilita(request, currentActiveConnection, gara.getData_creazione(), gara.isOrganoCost(), null);
                    //FINE TICKET ALM - 3.04.2 NG
					
					//TICKET ALM - 3.04.4
					 //Recupera le categorie selezionate in gara e inseriscile nella lista delle opzioni selezionabili
					 if(gara.getData_creazione().compareTo(SimogProperties.getInstance().getDataAttivazione3044()) >= 0) {
						 Map<String, String> listaCategorie = new AccessiDB(currentActiveConnection,logger).getTipologica(EAGG_CATEGORIE.TABLE_NAME, EAGG_CATEGORIE.COD_CATEGORIA, EAGG_CATEGORIE.DESCRIZIONE, EAGG_CATEGORIE.DATA_INIZIO_VALIDITA, EAGG_CATEGORIE.DATA_FINE_VALIDITA, PageHelper.parseTimeYMD(gara.getData_creazione()));
						 Map<String,String> listaCategorieLotto = new HashMap<String,String>();	 
						 for(String codGara : gara.getCatMercArray()) {

							 for (Map.Entry<String, String> entry : listaCategorie.entrySet()) {
								 if(codGara.equals(entry.getKey()))
									 listaCategorieLotto.put(entry.getKey(), entry.getValue());
							 }
							 
						 }
						 
						 request.setAttribute(LISTA_CATEGORIE_LOTTO, listaCategorieLotto);
						 
						 //Se nella gara e' stata selezionata una sola categoria al DPCM, autoselezionare il campo di selezione della categoria
						 if(gara.getCatMercArray()!=null && gara.getCatMercArray().length==1)
							 request.setAttribute(FIELD_NAME_CATEGORIA_LOTTO, gara.getCatMercArray()[0]);
						 
						 
						 //TICKET ALM #22951
  						 if(gara.getData_creazione().compareTo(SimogProperties.getInstance().getDataAttivazione3046()) >= 0) {
  							 
  							 if(gara.getCIG_ACC_QUADRO()!=null && !"".equals(gara.getCIG_ACC_QUADRO())) {
  								
  	  							 CPVEUManager cpvMan = new CPVEUManager(currentActiveConnection, logger);
								 Map<String,String> listaCPV = new HashMap<String,String>();
								 java.util.List<Lotto> lottoListAccQ = lm.getLottoByCigWS(gara.getCIG_ACC_QUADRO());
								 if (lottoListAccQ != null && !lottoListAccQ.isEmpty()) {
								 Lotto lottoAccQ = lottoListAccQ.get(0);
								 String cpvPrevalente = lottoAccQ.getId_CPV();
								listaCPV.put(cpvPrevalente, cpvPrevalente+" - "+(cpvMan.getCPVDesc(cpvPrevalente)));
								for(CpvLotto cpvSecondaria : lottoAccQ.getElencoCpvSecondarie()) {
									cpvSecondaria.setDescrizione(cpvMan.getCPVDesc(cpvSecondaria.getIdCpv()));
									listaCPV.put(cpvSecondaria.getIdCpv(), cpvSecondaria.getIdCpv()+" - "+cpvSecondaria.getDescrizione());
								}
							
							    request.setAttribute("LISTA_CPV_ADESIONE", listaCPV);
						     }
  						 }
						 
					 }
					 //FINE TICKET ALM - 3.04.4
					 
					
					
					if (errs instanceof AllValidationBeans)
						forward(JSP_INSERISCI_LOTTO, request, response);
					else
						sendMessage(request, response, res, JSP_INSERISCI_LOTTO);
					}

				} catch ( Exception sqle ) {
					sendError(request, response, Messaggi.SIMOG_LOTTO_002, JSP_ERRORE, sqle);
					return;
				} finally {
					closeConnection(request.getSession().getId(),getClass().getName());
				}
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				return;
			}
		} else {
			sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE);
			return;
		}
	}

}
