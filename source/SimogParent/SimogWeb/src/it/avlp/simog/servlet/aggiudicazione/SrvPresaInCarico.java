package it.avlp.simog.servlet.aggiudicazione;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.MultilottoManager;
import it.avlp.simog.actions.DelegaDatiSimogAction;
import it.avlp.simog.actions.aggiudicazione.InfoComuniAction;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.StazioneAppaltante;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;


public class SrvPresaInCarico extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7147215868373215565L;

	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	@Override
	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		HttpSession currentActiveSession = request.getSession();
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if (checkSession(request)) {
			if (currentUser.isRUP()) {
				try{
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					currentActiveConnection.setAutoCommit(false);
					String action = request.getParameter("toDo");
					Long idLotto = Long.parseLong(request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO));

					if (action == null){
						action = PSBD.ACTION_CARICA_JSP_ANNULLAMENTO;
					}			

					String action2 = request.getParameter(ACTION_GET_LIST);
					int startRow = 0;
					if(action2 == null)
						action2 = STILL;
					InfoComuniAction iAction = new InfoComuniAction(currentActiveConnection,logger);
					InfoGaraBean infoGara = null;					
					InfoComuniBean infoComuniBean = new InfoComuniBean();					
					infoGara = iAction.loadInfoGara(idLotto);						
					infoComuniBean = iAction.load(infoGara.getIdInfo(), infoGara.getDataInizioInfo());
					
               request.setAttribute(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO, String.valueOf(infoComuniBean.isRichAnn() || infoComuniBean.isRichDelete()));

					/*     CHECK DELEGA   */
					DelegaDatiSimogAction dasAction = new DelegaDatiSimogAction(currentActiveConnection, logger);
					String res = dasAction.getDelegaSchedeMessage(infoGara.getIdOsservatorio(), PageHelper.getCurrentUtilDate(), currentUser);
					boolean cantModify = res != null && currentUser.isRSSAorRUP();
					if(cantModify){
						
							String startRowS = request.getParameter(START_ROW);
							startRow = Integer.parseInt(startRowS);
							if ( PROGRESS.equalsIgnoreCase(action) ) {
								startRow = startRow - configuration.getMaxElementiPerPagina();
							} else if ( REGRESS.equalsIgnoreCase(action) &&  (startRow - configuration.getMaxElementiPerPagina() >=0 ))  {
								startRow = startRow + configuration.getMaxElementiPerPagina();
		 					}
						
						
						request.setAttribute("delegaSchede", cantModify);
						
						request.setAttribute(START_ROW, startRowS != null ? Integer.parseInt(startRowS) : startRow);
						request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getELEMENTI_VISUALIZZATI());
						sendError(request,response, res, ParametriServlet.SRV_GESTIONE_SCHEDE + "?" + ParametriServlet.ACTION_GET_LIST + "=" + action
								+ "&" + ParametriServlet.START_ROW + "=" + startRow);
						
						return;
					}
					
               /*     CHECK BLOCCO AVCPASS   */
                  //verifico se posso modificare i dati (blocco avcpass)
                  // richiamo il servizio AVCPASS
                  //GaraManager gm = new GaraManager(currentActiveConnection, logger);
                  //Gara gara = gm.getGara(infoGara.getIdGara());
                  //RequisitiGLAction requisitiGLAction = new RequisitiGLAction(currentActiveConnection, logger);
                  //List<Lotto> listaLotti = requisitiGLAction.getLottoList( infoGara.getIdGara() );
					   LottoManager lman = new LottoManager(currentActiveConnection, logger);
                  List<Lotto> lotto = lman.getLottoByCigWS(infoGara.getFullCIG());
                  
                  AVCPassAction avpa = new AVCPassAction(currentActiveConnection, logger, configuration);
                  Boolean blocco = avpa.isAVCPass(null, lotto, AVCPassFunzioneEnum.WEB_DATI_COMUNI_PRESA_IN_CARICO.getCodice());
                  if (blocco){
                     String startRowS = request.getParameter(START_ROW);
                     startRow = Integer.parseInt(startRowS);
                     if ( PROGRESS.equalsIgnoreCase(action) ) {
                        startRow = startRow - configuration.getMaxElementiPerPagina();
                     } else if ( REGRESS.equalsIgnoreCase(action) &&  (startRow - configuration.getMaxElementiPerPagina() >=0 ))  {
                        startRow = startRow + configuration.getMaxElementiPerPagina();
                     }
                                       
                     request.setAttribute(START_ROW, startRowS != null ? Integer.parseInt(startRowS) : startRow);
                     request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getELEMENTI_VISUALIZZATI());
                     request.setAttribute("delegaSchede", true);
                     
                     String targetPage = ParametriServlet.SRV_GESTIONE_SCHEDE + "?" + ParametriServlet.ACTION_GET_LIST + "=" + action + "&" + ParametriServlet.START_ROW + "=" + startRow;
                     
                     AllValidationBeans msgs = new AllValidationBeans();
                     msgs.addValidationErr(SIMOG_AVCPASS_001);
                     sendValidations(request, response, msgs, targetPage); 
                     
                     return;
                  }
					   
               //entra qui se e' la prima volta che passa da questa servlet
					//metto in sessione i dati che mi servono
					if(currentActiveSession.getAttribute("dati_gara_infocomuni") == null ){
						logger.debug(">>>>>>>>>metto in sessione l'oggetto che mi serve (1a)");
						currentActiveSession.setAttribute("dati_gara_infocomuni", infoComuniBean);
					}					
					if("Conferma".equalsIgnoreCase(action)){
						logger.debug(">>>>>>>>>condizione di 2o passaggio ok (2a)");
						//controllo che la sessione sia stata passata correttamente
						if(currentActiveSession.getAttribute("dati_gara_infocomuni") != null ){
							//ok passata correttamente ora devo eseguire le operazioni sul db
							logger.debug(">>>>>>>update dati gara (2a)");
							InfoComuniBean icb = (InfoComuniBean)currentActiveSession.getAttribute("dati_gara_infocomuni");
							String bla = request.getParameter(PSBD.MOTIVAZIONE_ANNULLAMENTO);
							if(!bla.trim().equals("")){
								
								
								String stazioneAppaltanteSelezionata = getSASelected(request);
								StazioneAppaltante currentSA =null;
								if(stazioneAppaltanteSelezionata!=null) {
								 for ( java.util.Enumeration e = currentUser.getUfficiByProfilo(currentUser.getProfiloEnum()).elements(); e.hasMoreElements(); ) {
									  currentSA =  (StazioneAppaltante)e.nextElement();
									 if(currentSA.getIdUfficio().equals(stazioneAppaltanteSelezionata))
										 break;
								 }
								}
								
								//TICKET ALM 18257
								//Verifica se si sta prendendo in carico un CIG appartenente a un contratto multilotto
								AggiudicazioniManager am  = new AggiudicazioniManager(currentActiveConnection, logger);
								List<AggiudicazioneBean> listAgg = am.getAggiudicazioniByCIG(infoGara.getFullCIG());
								if(listAgg.size()>0 && listAgg.get(0).getCodiceContratto()!=null) {
									String codiceContratto = listAgg.get(0).getCodiceContratto();
								MultilottoManager mm = new MultilottoManager(currentActiveConnection,logger);
								InfoComuniManager icm = new InfoComuniManager(currentActiveConnection, logger);
  									List<AggiudicazioneBean> listAggMulti = mm.getAggiudicazioniListMultilotto(infoGara.getIdLotto());
  									
									for(AggiudicazioneBean ab : listAggMulti) {
										if(codiceContratto.equals(ab.getCodiceContratto())) {
										InfoComuniBean currIcb = icm.getInfoComuniByCig(ab.getCig());
											currIcb.setProvvPresaCarico(bla);
											if(currentSA!=null) {
												GaraManager gm = new GaraManager(currentActiveConnection,logger);
												int idf = gm.getGara(infoGara.getIdGara()).getID_F_DELEGATE();
					                             gm.eseguiPresaInCaricoInfoAggiudicazioniSingoloCIG(currentSA,
					                            		 currentUser.getLogin(),
					                            		 infoGara.getIdLotto(), 
					                            		 idf==Costanti.IDF_PROPOSTA_AGGIUDICAZIONE, true);
											} else
										iAction.presaInCarico(currIcb, currentUser.getLogin());
									}
									}
									
								} else {
									icb.setProvvPresaCarico(bla);
									if(currentSA!=null) {
										GaraManager gm = new GaraManager(currentActiveConnection,logger);
										int idf = gm.getGara(infoGara.getIdGara()).getID_F_DELEGATE();
			                             gm.eseguiPresaInCaricoInfoAggiudicazioniSingoloCIG(currentSA,
			                            		 currentUser.getLogin(),
			                            		 infoGara.getIdLotto(), 
			                            		 idf==Costanti.IDF_PROPOSTA_AGGIUDICAZIONE, true);
			                             //ticket #31062  caricare tutti i lotti --> prendere tutte le schede dati comuni--> controllare il CDC 
			                             //--> se tutti uguali far diventare la gara non delegata ma di proprieta'
			                             List<Lotto> listLotti = lman.getListaLotti(infoGara.getIdGara());
			                             
			                             InfoComuniManager infoComuniManager = new InfoComuniManager(currentActiveConnection, logger);
			                             
			                             String codiceCCControllo = null;
			                             int tempCounter = 0;
			                             for (Lotto lottotemp : listLotti) {
			                            	 List<InfoComuniBean> listInfoComuni = infoComuniManager.getInfoComuniByIdLotto(lottotemp.getId_Lotto());                         	 
											for (InfoComuniBean bean : listInfoComuni) {
												if(codiceCCControllo == null || codiceCCControllo == "") {
													codiceCCControllo = bean.getCodiceCC();
												}
												if(codiceCCControllo.equals(bean.getCodiceCC())) {
													tempCounter++;
												}
											}
										}
			                             
			                             //ticket #31062
			                             if(tempCounter == listLotti.size()) {
			                            	 gm.eseguiPresaInCaricoGaraDelegata(currentSA,currentUser.getLogin(),String.valueOf(infoGara.getIdGara()));
			                            	 gm.setDataPresaInCaricoDelega(infoGara.getIdGara());
			                             }

									} else {										
										iAction.presaInCarico(icb, currentUser.getLogin());
									}
								}
								
								commit(currentActiveConnection);
								currentActiveSession.removeAttribute("dati_gara_infocomuni");
								sendMessage(request, response, Messaggi.SIMOG_AGGIUDICAZIONI_081.replace("$1", "Presa in carico"), ParametriServlet.JSP_GESTIONE_SCHEDE);
								return;
							}else{
								
								String cfAmmAgente = "";
								//TICKET ALM #659 - 3.04.4
								request.setAttribute(ParametriServlet.CF_AMM_DELEGANTE, "");
								 GaraManager garaManager = new GaraManager(currentActiveConnection, logger);
								 cfAmmAgente = garaManager.getCfAmmDelegata(infoGara.getIdGara(), 0, currentUser.getAmministrazioni());
								if(!"".equals(cfAmmAgente)) {
									   
								   request.setAttribute(ParametriServlet.CF_AMM_DELEGANTE, cfAmmAgente);
								
								}
								
							   sendError(request, response, Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Presa in carico"), ParametriServlet.JSP_RICHIEDI_PRESAINCARICO );
								return;
							}
						}
						
					}
					else if(PSBD.ACTION_CARICA_JSP_ANNULLAMENTO.equalsIgnoreCase(action)){
						
						String cfAmmAgente = "";
						String idFunzDelega = "";
						//TICKET ALM #659 - 3.04.4
						request.setAttribute(ParametriServlet.CF_AMM_DELEGANTE, "");
						 GaraManager garaManager = new GaraManager(currentActiveConnection, logger);
						 
						 //MAD 58518 3.04.14
						 idFunzDelega = garaManager.getIdFunzDelega(infoGara.getIdGara());
						 if (!idFunzDelega.equals("3")) {
							 cfAmmAgente = garaManager.getCfAmmDelegata(infoGara.getIdGara(), 0, currentUser.getAmministrazioni());
						 }
						 
						if(!"".equals(cfAmmAgente)) {
							   
						   request.setAttribute(ParametriServlet.CF_AMM_DELEGANTE, cfAmmAgente);
						
						}
						
						logger.debug(">>>>>>>>forward jsp form (1a)");
						String dest = ParametriServlet.JSP_RICHIEDI_PRESAINCARICO + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + infoGara.getIdLotto();
						dest+="&" + ParametriServlet.FIELD_NAME_ID_INFO + "=" + infoGara.getIdInfo();
						dest+="&" + ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO + "=" + infoGara.getDataInizioInfo();
						dest+="&" + ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO + "=" + String.valueOf(infoComuniBean.isRichAnn() || infoComuniBean.isRichDelete());
						forward(dest, request, response);
						return;
					}
					
					forward(ParametriServlet.JSP_ERRORE , request, response);
					
					return;
				} catch (Exception e) {
					logger.fatal(e);
					//e.printStackTrace();
					rollback(currentActiveConnection);
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE , e);
					
					return;

				} finally {
				logger.debug("Chiudo connessione");
				
					closeConnection(request.getSession().getId(),getClass().getName());
				}
				

			}
		}

	}
	
	private String getSASelected(HttpServletRequest request) {
		return request.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
	}
}
