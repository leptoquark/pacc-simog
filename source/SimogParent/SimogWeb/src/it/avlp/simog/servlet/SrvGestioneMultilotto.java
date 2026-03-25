package it.avlp.simog.servlet;

import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
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
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletLotto;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.TipoFlusso;
import it.avlp.simog.validatore.WorkFlowController;
import it.avlp.simog.common.action.MultilottoAction;
import it.avlp.simog.common.action.RequisitiGLAction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 public class SrvGestioneMultilotto extends ServletBase{
    
	private static final long serialVersionUID = 1L;

//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		perform(request, response);
//	}
//	
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		HttpSession currentActiveSession = request.getSession();
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if ( checkSession(request) ) {
			if (currentUser.isRUP()) {
				try {
					visualizzaListaParametriValori(request, response);
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					currentActiveConnection.setAutoCommit(false);
				    currentActiveConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);	
					
					InfoComuniAction infoComAction = new InfoComuniAction(currentActiveConnection,logger);
					String idLotto = request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO);							
					InfoGaraBean infoGara = infoComAction.loadInfoGara(Long.parseLong(idLotto));
					MultilottoAction mla = new MultilottoAction(currentActiveConnection,logger);

					String action = request.getParameter("toDo");
					
					String idAggiudicazioneCorrente = request.getParameter(PSBD.FIELD_NAME_AGG_ID_AGGIUDICAZIONE);
					String datainizioAggiudicazioneCorrente = request.getParameter(PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE);
                    AggiudicazioniManager am = new AggiudicazioniManager(currentActiveConnection,logger);
					AggiudicazioneBean aggiudicazioneCorrente = am.getAggiudicazioni(Long.parseLong(idAggiudicazioneCorrente), PageHelper.parseTime(datainizioAggiudicazioneCorrente), false);
                    			
					String codiceSelezionato = request.getParameter(ParametriServlet.CODICE_SELEZIONATO);
					String [] aggiudicazioniDaAggiungere = request.getParameterValues(ParametriServlet.AGGIUDICAZIONI_DA_AGGIUNGERE);
	    	    	List<String> listaAggDaAggiungere = mla.fromArrayToList(aggiudicazioniDaAggiungere);
					//String [] aggiudicazionePrincipale = request.getParameterValues(ParametriServlet.AGGIUDICAZIONE_PRINCIPALE);
	    	    	//List<String> listaAggPrincipale = mla.fromArrayToList(aggiudicazionePrincipale);
					//String idAggPrincipale = listaAggPrincipale.size()==0 ? "" : aggiudicazionePrincipale[0];
					
		    		MultilottoManager mm = new MultilottoManager(currentActiveConnection, logger);
	        		List<AggiudicazioneBean> listaAggiudicazioniStessaGara =  mm.getAggiudicazioniListMultilotto(Long.parseLong(idLotto));
					Map<String,List<AggiudicazioneBean>> mappaMultilotto = new HashMap <String,List<AggiudicazioneBean>>();
    		    	mappaMultilotto = mla.getMappaMultilotto(aggiudicazioneCorrente, listaAggiudicazioniStessaGara);	
					Set<String> setCodiciContratto = mappaMultilotto.keySet();
						
					if(ParametriServlet.ACTION_AGGIUNGI_SINGOLA.equals(action)){
						
						DelegaDatiSimogAction dasAction = new DelegaDatiSimogAction(currentActiveConnection, logger);
						String res = dasAction.getDelegaSchedeMessage(infoGara.getIdOsservatorio(), PageHelper.getCurrentUtilDate(), currentUser);

						boolean cantModify = res != null && currentUser.isRSSAorRUP();
						
						currentActiveSession.setAttribute("delegaSchede", cantModify);
						HashMap<String, LinkedHashMap<String, NavigationBean>> linkedHash = new HashMap<String, LinkedHashMap<String, NavigationBean>> ();
						if(cantModify){						
							sendError(request,res );
						}
						
	               /*     CHECK BLOCCO AVCPASS   */
	               if(SimogFlags.is3028_RFWEBGL07Active()){
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
	               
						List<AggiudicazioneBean> listaAggiudicazioni =  am.getAggiudicazioniList(infoGara.getIdInfo(),infoGara.getDataInizioInfo());				
						InfoComuniBean infoComuniBean = infoComAction.load(infoGara.getIdInfo(), infoGara.getDataInizioInfo());
						String daticomunistato =  String.valueOf(infoComuniBean.getIdStato());

						if (listaAggiudicazioni.size()==0) {	
							currentActiveSession.setAttribute("navigationMap", linkedHash);
							forward(PSBD.SRV_DATI_COMUNI, request, response);
							return;
						}
						else{		
							WorkFlowController na = new WorkFlowController(currentActiveConnection, logger);

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
						forward(JSP_GESTIONE_AGGIUDICAZIONI, request, response);
					}
					else if(ParametriServlet.ACTION_AGGIUNGI_AL_GRUPPO.equals(action)){
						
						mla.aggiungiAlGruppo(codiceSelezionato, idAggiudicazioneCorrente);
						currentActiveConnection.commit();
						String url = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + 
						ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + idLotto;
						forward(url, request, response);
					}
					else if(ParametriServlet.ACTION_CREA_NUOVO_GRUPPO.equals(action)){
						mla.validaNuovoGruppo(listaAggDaAggiungere);
						if(mla.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize()==0){
				    		mla.creaNuovoGruppo(listaAggDaAggiungere, setCodiciContratto, idAggiudicazioneCorrente);
				    		currentActiveConnection.commit();
				    		String url = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + 
			    			ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + idLotto;
			    			forward(url, request, response);
						}
						else{
							logger.error ( "fallita la creazione nuovo gruppo multilotto" );
							AggiudicatarioManager aggm = new AggiudicatarioManager(currentActiveConnection,logger);
			        		List<AggiudicatarioBean> aggiudicatariCorrenti =  aggm.loadMany(aggiudicazioneCorrente.getIdAggiudicazione(), aggiudicazioneCorrente.getDataInizioAggiudicazione(), false);
							request.setAttribute(ParametriServlet.AGGIUDICATARI_CORRENTI, aggiudicatariCorrenti);
							request.setAttribute(ParametriServlet.AGGIUDICAZIONE_CORRENTE, aggiudicazioneCorrente);
							request.setAttribute(ParametriServlet.MAPPA_MULTILOTTO, mappaMultilotto);
							request.setAttribute(ParametriServlet.AGGIUDICAZIONI_DA_AGGIUNGERE, aggiudicazioniDaAggiungere);
							//request.setAttribute(ParametriServlet.AGGIUDICAZIONE_PRINCIPALE, idAggPrincipale);
							
							String url = JSP_GESTIONE_MULTILOTTO_NEW;
							sendValidations(request, response, mla.getEccezioni(), url);
						}
					}
					else{
						String res = "Azione non consentita";
						sendError(request,res);
						String url = ParametriServlet.SRV_GESTIONE_SCHEDE;
						forward(url, request, response);
					}			
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