package it.avlp.simog.servlet.aggiudicazione;

import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.MultilottoManager;
import it.avcp.simog.managers.conclusione.ConclusioniManager;
import it.avlp.simog.actions.AllValidationAction;
import it.avlp.simog.actions.DelegaDatiSimogAction;
import it.avlp.simog.actions.aggiudicazione.InfoComuniAction;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.NavigationBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.common.action.RequisitiGLAction;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.TipoFlusso;
import it.avlp.simog.validatore.WorkFlowController;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class for Servlet: SrvRiepilogoScheda
 *
 */
 public class SrvVisualizzaRiepilogoSchedeMultilotto extends ServletBase{
    
	private static final long serialVersionUID = 1L;

//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		perform(request, response);
//	}
	
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		HttpSession currentActiveSession = request.getSession();
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if ( checkSession(request) ) {
			if ( currentUser.isRUP() || currentUser.isCS() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()) {
				try {

					visualizzaListaParametriValori(request, response);
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					//setTabelleUtilita(request);										
					String action = request.getParameter(ACTION_GET_LIST);
					int startRow = 0;
					boolean actionNeeded = action != null;
					if(action == null)
						action = STILL;
					InfoComuniAction infoComAction = new InfoComuniAction(currentActiveConnection,logger);
					String idLotto = request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO);						
					InfoGaraBean infoGara = infoComAction.loadInfoGara(Long.parseLong(idLotto));
					
					String categoriaPrevalente = infoComAction.loadTipoLotto(Long.parseLong(idLotto));
					request.setAttribute("categoriaPrevalente", categoriaPrevalente);
					InfoComuniBean infoComuniBean = infoComAction.load(infoGara.getIdInfo(), infoGara.getDataInizioInfo());
					currentActiveSession.setAttribute("datiComuni", infoComuniBean);
					String daticomunistato =  String.valueOf(infoComuniBean.getIdStato());
					request.setAttribute("daticomunistato", daticomunistato); 
					setDatiGara(infoGara, request.getSession());
					if ( actionNeeded ) {
						String startRowS = request.getParameter(START_ROW);
						startRow = Integer.parseInt(startRowS);
						if ( action.equalsIgnoreCase(REGRESS) ) {
							startRow = startRow - configuration.getMaxElementiPerPagina();
						} else {
							startRow = startRow + configuration.getMaxElementiPerPagina();
						}
						
					}				
					
					//Parte per la gestione dell'elenco di eventuali aggiudicazioni gia' inserite
					request.setAttribute(ParametriServlet.START_ROW, new Integer(startRow) );
					request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getELEMENTI_VISUALIZZATI());
					//AggiudicazioniManager aggman = new AggiudicazioniManager(currentActiveConnection, logger);
					//TableBean listaAggiudicazioni = aggman.getListaAggiudicazioni(Long.parseLong(id_info),startRow,configuration.getMaxElementiPerPagina());
					//List<AggiudicazioneBean> listaAggiudicazioni =  aggman.getAggiudicazioniList(infoGara.getIdInfo(),infoGara.getDataInizioInfo());				
                    String codiceContratto = request.getParameter(PSBD.FIELD_NAME_CODICE_CONTRATTO);
					MultilottoManager mm = new MultilottoManager(currentActiveConnection, logger);
                    List<AggiudicazioneBean> listaAggiudicazioni =  mm.getAggiudicazioniListMultilotto(codiceContratto,Long.parseLong(idLotto));				
					currentActiveSession.setAttribute("listaAggiudicazioni", listaAggiudicazioni);
					//carico le info comuni di ogni aggiudicazione
					Map<Long,InfoComuniBean> mapInfoComuniMulti = new HashMap<Long,InfoComuniBean>();
					for(AggiudicazioneBean agg : listaAggiudicazioni){
						InfoComuniBean infoComuniAgg = infoComAction.load(agg.getIdInfo(), agg.getDataInizioInfo());
						mapInfoComuniMulti.put(agg.getIdAggiudicazione(),infoComuniAgg);
					}
					currentActiveSession.setAttribute("mapInfoComuniMulti", mapInfoComuniMulti);
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_ID_LOTTO, idLotto);

					
					/*     CHECK DELEGA   */
					DelegaDatiSimogAction dasAction = new DelegaDatiSimogAction(currentActiveConnection, logger);
					String res = dasAction.getDelegaSchedeMessage(infoGara.getIdOsservatorio(), PageHelper.getCurrentUtilDate(), currentUser);

					boolean cantModify = res != null && currentUser.isRSSAorRUP();
					
					currentActiveSession.setAttribute("delegaSchede", cantModify);
					HashMap<String, LinkedHashMap<String, NavigationBean>> linkedHash = new HashMap<String, LinkedHashMap<String, NavigationBean>> ();
					if(cantModify){						
						sendError(request,res );
					}
					
                  /*     CHECK BLOCCO AVCPASS   */
                  if(SimogFlags.is3028_RFWEBGL07Active() && !SimogFlags.is3030_RFWEBGL02Active()){
                     //verifico se posso modificare i dati (blocco avcpass)
                     // richiamo il servizio AVCPASS
                     AVCPassAction avpa = new AVCPassAction(currentActiveConnection, logger, configuration);
                     GaraManager gm = new GaraManager(currentActiveConnection, logger);
                     Gara gara = gm.getGara(infoGara.getIdGara());
                     //RequisitiGLAction requisitiGLAction = new RequisitiGLAction(currentActiveConnection, logger);
                     //List<Lotto> listaLotti = requisitiGLAction.getLottoList( infoGara.getIdGara() );
    
                     Boolean blocco = avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_ACQUISIZIONE_SCHEDE_AGGIUNTIVE.getCodice());
                     if (blocco){
                        //aggiungo l'errore 
                        cantModify = true;
                        request.getSession().setAttribute("delegaSchede", cantModify);
                        sendError(request,Messaggi.SIMOG_AVCPASS_001);
                     }
                  }
               
					if (listaAggiudicazioni.size()==0) {	
						currentActiveSession.setAttribute("navigationMap", linkedHash);
						forward(PSBD.SRV_DATI_COMUNI, request, response);
						return;
					}/* rilascio scheda a, non serve il menu a tendina! */
					else{
//TODO: PUNTO DI INTRODUZIONE DELLA SITUAZIONE..
					
						WorkFlowController na = new WorkFlowController(currentActiveConnection, logger);

						/*************************************/
						
						//TICKET ALM #659 - 3.04.4
						//Verifica se la gara e' delegata a un'altra amministrazione
						int garaDelegata = 0;
						GaraManager gm = new GaraManager(currentActiveConnection, logger);
						Gara g = gm.getGara(infoGara.getIdGara());
						if("S".equals(g.getFlagSAAgente()) && 
								(Costanti.DELEGA1==g.getID_F_DELEGATE() || Costanti.DELEGA2==g.getID_F_DELEGATE()) &&
								!g.getCF_AMM_AGENTE().equals(g.getCF_AMMINISTRAZIONE()) &&
								!g.getCF_AMM_AGENTE().equals(infoComuniBean.getCfAmministrazione())
								)
							garaDelegata = g.getID_F_DELEGATE();
						
						//TICKET ALM #9759 - 3.04.4
						boolean recessoSa = false;
						ConclusioniManager cm = new ConclusioniManager(currentActiveConnection,logger);
						
						
						/***********************************************/
                           for(AggiudicazioneBean bean: listaAggiudicazioni){
                        	   ConclusioneBean cBean = cm.load(bean.getIdAggiudicazione(), bean.getDataInizioAggiudicazione());
                        	   if(cBean!=null)
                        		   recessoSa = cBean.getMotiviInterruzione() == Costanti.RECESSO_SA;
                              linkedHash.put(String.valueOf(bean.getIdAggiudicazione()), 
                                             na.createNavigationBean3023(
                                                   bean, 
                                                   infoGara.getTipoEnte(), 
                                                   daticomunistato.equals(StatiScheda.ANNULLAMENTO_RICHIESTA_STRING)
                                                      || infoComuniBean.isRichDelete(), 
                                                   infoComuniBean.getTipoContratto(), 
                                                   cantModify, 
                                                   infoGara.getFLAG_ESCLUSO(), 
                                                   infoGara.getID_MODO_REAL(), 
                                                   infoGara.getDataPubblicazione(), 
                                                   infoGara.getImportoLotto().floatValue(), garaDelegata,infoGara.getDataCreazioneGara(),g.getID_SVOLGIMENTO()));//TICKET ALM - 3.04.3 #659
                          }                                                    
					//*********************** MARCA LE SCHEDE NON VALIDE CON UN ASTERISCO ************************

						AllValidationAction allValidator = new AllValidationAction(infoGara, currentActiveConnection, logger, configuration);
						for(AggiudicazioneBean bean: listaAggiudicazioni){
							HashMap<String, Boolean> report = allValidator.validationReport(bean.getIdAggiudicazione(), bean.getDataInizioAggiudicazione());
							na.markViewName(report, linkedHash.get(String.valueOf(bean.getIdAggiudicazione())));				
						}
						request.setAttribute("InfoComuniValid", allValidator.validaInfoComuni(infoComuniBean));
						 	
					//********************************************************************************************/
						
						currentActiveSession.setAttribute("navigationMap", linkedHash);
						
					}
					
					forward(JSP_GESTIONE_AGGIUDICAZIONI_MULTILOTTO, request, response);
				}
				catch (Exception e) { 
					e.printStackTrace();
					sendError(request, response, SIMOG_AGGIUDICAZIONI_006, JSP_ERRORE, e );
					return;
				}
				finally {
					closeConnection(request.getSession().getId(),getClass().getName());
				}	
			}
			else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				return;
			}
		}
		else {
			sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			return;
		}
	}   	  	    
}