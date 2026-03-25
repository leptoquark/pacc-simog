package it.avlp.simog.servlet.aggiudicazione;

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
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.NavigationBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.TipoFlusso;
import it.avlp.simog.validatore.WorkFlowController;
import it.avlp.simog.common.action.MultilottoAction;
import it.avlp.simog.common.action.RequisitiGLAction;

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

import org.omg.CosNaming.NamingContextPackage.NotFound;
/**
 * Servlet implementation class for Servlet: SrvRiepilogoScheda
 *
 */
 public class SrvVisualizzaRiepilogoScheda extends ServletBase{
    
	private static final long serialVersionUID = 1L;

//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		perform(request, response);
//	}
	
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		HttpSession currentActiveSession = request.getSession();
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if ( checkSession(request) ) {
			if ( currentUser.isRUP() || currentUser.isCS() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA() ) {
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
					
					//Risali fino alla gara dall'id del lotto
					Gara g = new GaraManager(currentActiveConnection,logger).getGara(new LottoManager(currentActiveConnection, logger).getLotto(Long.parseLong(idLotto)).getId_Gara());
					if(Costanti.FLAG_VALORE_SI.equals(g.getFlagSAAgente()) && 
							g.getID_F_DELEGATE()==Costanti.DELEGA3 && 
							!g.getCF_AMMINISTRAZIONE().equals(g.getCF_AMM_AGENTE()) && 
						    currentUser.isRUP() &&
							!g.getCF_UTENTE().equals(currentUser.getLogin())) {
						
						if (!currentUser.getAmministrazioni().containsKey(g.getCF_AMMINISTRAZIONE())) { //	MAC 48142
							setDelega("OK",request);}
                    }
					else if(Costanti.FLAG_VALORE_SI.equals(g.getFlagSAAgente()) && 
							g.getCF_AMMINISTRAZIONE().equals(g.getCF_AMM_AGENTE()) && 
						    currentUser.isRUP() &&
							!g.getCF_UTENTE().equals(currentUser.getLogin()) && infoComuniBean!=null 
							&& !currentUser.getLogin().equals(infoComuniBean.getCfRup())) { 
						if (!currentUser.getAmministrazioni().containsKey(g.getCF_AMMINISTRAZIONE())) {//	MAC 48142
							setDelega("OK",request);
                     }}	

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
					AggiudicazioniManager aggman = new AggiudicazioniManager(currentActiveConnection, logger);
									
					//TableBean listaAggiudicazioni = aggman.getListaAggiudicazioni(Long.parseLong(id_info),startRow,configuration.getMaxElementiPerPagina());
					List<AggiudicazioneBean> listaAggiudicazioni =  aggman.getAggiudicazioniList(infoGara.getIdInfo(),infoGara.getDataInizioInfo());				
					currentActiveSession.setAttribute("listaAggiudicazioni", listaAggiudicazioni);
 					
                	//gm gestione multilotto simog 3.05
			    		//se è presente almeno un'aggiudicazione
		    			if(currentUser.isRUP() && listaAggiudicazioni.size()>0){
		    				AggiudicazioneBean aggCorrente = listaAggiudicazioni.get(0);
			    		   	for(AggiudicazioneBean agg : listaAggiudicazioni){
			        			if(agg.getProgCUI()>aggCorrente.getProgCUI()){
			    	    			//la mia aggCorrente sarà l'ultima creata, l'unica ancora attiva
			    	    			aggCorrente = agg;
			    	    		}
				        	}
			    			String desc = aggCorrente.getDescrizioneStato();
			    			//le sottosoglia non possono diventare multilotto, inoltre deve essere confermata(non confermata in richiesta di cancellazione)
			    			if((TipoAggiudicazione.A.equals(aggCorrente.getSottotipo()) ||
			    				TipoAggiudicazione.E.equals(aggCorrente.getSottotipo()) ||
			    				TipoAggiudicazione.Q.equals(aggCorrente.getSottotipo()))&&"Confermato".equals(desc)){
			    		 
			    		    	MultilottoManager mm = new MultilottoManager(currentActiveConnection, logger);
				        		List<AggiudicazioneBean> listaAggiudicazioniStessaGara =  mm.getAggiudicazioniListMultilotto(Long.parseLong(idLotto));	
				        		List<AggiudicazioneBean> listaAggiudicazioniConfermateStessaGara =  new ArrayList<AggiudicazioneBean>();	
                                for(AggiudicazioneBean agg : listaAggiudicazioniStessaGara){
                                	if("Confermato".equals(agg.getDescrizioneStato()))
                                	listaAggiudicazioniConfermateStessaGara.add(agg);
                                }
				        		AggiudicatarioManager am = new AggiudicatarioManager(currentActiveConnection,logger);
				        		List<AggiudicatarioBean> aggiudicatariCorrenti =  am.loadMany(aggCorrente.getIdAggiudicazione(), aggCorrente.getDataInizioAggiudicazione(), false);

				    	    	//se sono presenti aggiudicazioni per la stessa gara
				    	    	if(listaAggiudicazioniConfermateStessaGara.size()>0){
				    	    		MultilottoAction mla = new MultilottoAction(currentActiveConnection, logger);
				    	    		Map<String,List<AggiudicazioneBean>> mappaMultilotto = new HashMap <String,List<AggiudicazioneBean>>();
				    		    	mappaMultilotto = mla.getMappaMultilotto(aggCorrente, listaAggiudicazioniConfermateStessaGara);
				    		    	//se la mappa ha dimensioni non nulle, posso accorpare in un contratto multilotto
				    		    	if(mappaMultilotto.size()>0){
				    		    		request.setAttribute(ParametriServlet.AGGIUDICATARI_CORRENTI, aggiudicatariCorrenti);
				    		    		request.setAttribute(ParametriServlet.AGGIUDICAZIONE_CORRENTE, aggCorrente);
				    		    		request.setAttribute(ParametriServlet.MAPPA_MULTILOTTO, mappaMultilotto);
					    	    		forward(JSP_GESTIONE_MULTILOTTO_NEW, request, response);
							       }
					           }
				    	   }
		    			}
					//}
					//gm fine gestione multilotto simog 3.05

					
					
//					if(request.getParameter(ParametriServlet.SHOW_DATI_COMUNI)==null)
//						request.setAttribute(ParametriServlet.SHOW_DATI_COMUNI, "false");
//					else
//						request.setAttribute(ParametriServlet.SHOW_DATI_COMUNI, request.getParameter(ParametriServlet.SHOW_DATI_COMUNI));
				
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
                  //GaraManager gm = new GaraManager(currentActiveConnection, logger);
                  //Gara gara = gm.getGara(infoGara.getIdGara());
                  //RequisitiGLAction requisitiGLAction = new RequisitiGLAction(currentActiveConnection, logger);
                  //List<Lotto> listaLotti = requisitiGLAction.getLottoList( infoGara.getIdGara() );
                  LottoManager lman = new LottoManager(currentActiveConnection, logger);
                  List<Lotto> lotto = lman.getLottoByCigWS(infoGara.getFullCIG());
                  AVCPassAction avpa = new AVCPassAction(currentActiveConnection, logger, configuration);
                  Boolean blocco = avpa.isAVCPass(null, lotto, AVCPassFunzioneEnum.WEB_ACQUISIZIONE_SCHEDE_AGGIUNTIVE.getCodice());
                  if (blocco){
                     //aggiungo l'errore 
                     cantModify = true;
                     request.getSession().setAttribute("delegaSchede", cantModify);
                     sendError(request,Messaggi.SIMOG_AVCPASS_001);
                  }
               }
               
					if (listaAggiudicazioni.size()==0 || !infoComuniBean.isConfirmed()) {
						if(infoComuniBean != null && 
								infoComuniBean.isConfirmed() && 
								infoComuniBean.getEsitoProcedura().equals(String.valueOf(Costanti.AGGIUDICATA))) {
							TipoFlusso flusso = SimogValidator.getTipoFlusso(infoGara);
							linkedHash.put("0", this.getDefaultScheda(flusso,infoGara.getDataCreazioneGara()));
							currentActiveSession.setAttribute("navigationMap", linkedHash);
							forward(JSP_GESTIONE_AGGIUDICAZIONI, request, response);
							return;
						} else {
							forward(PSBD.SRV_DATI_COMUNI, request, response);
							return;
						}
					}/* rilascio scheda a, non serve il menu a tendina! */
					else{
//TODO: PUNTO DI INTRODUZIONE DELLA SITUAZIONE..
					
						WorkFlowController na = new WorkFlowController(currentActiveConnection, logger);

						/*************************************/
						
						//TICKET ALM #659 - 3.04.4
						//Verifica se la gara e' delegata a un'altra amministrazione
						int garaDelegata = 0;
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
						
						   boolean ret = allValidator.validaInfoComuni(infoComuniBean);
						   
						   if(!ret && !infoComuniBean.isConfirmed())
						      ret = false;
						   else
						      ret = true;
						   
						   request.setAttribute("InfoComuniValid", ret);

						 	
					//********************************************************************************************/
						
						currentActiveSession.setAttribute("navigationMap", linkedHash);
						
					}
					
					forward(JSP_GESTIONE_AGGIUDICAZIONI, request, response);
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

private LinkedHashMap<String, NavigationBean> getDefaultScheda(TipoFlusso flusso, String dataCreazione) {
	LinkedHashMap<String,NavigationBean> map = new LinkedHashMap<String,NavigationBean>();
	NavigationBean nb = new NavigationBean();
    System.out.println("TECHNIS getDefaultScheda start");
	 try {
		 if (flusso.equals(TipoFlusso.ADESIONE)){
				nb.setViewName(IdentificativoSchede.getAdesione().getDecodificaVN());
				nb.setPath(ParametriServlet.SRV_SCHEDA_ADESIONE);
				 System.out.println("TECHNIS getDefaultScheda ADESIONE");
		 } else if(flusso.equals(TipoFlusso.ESCLUSO)){
			 if(!SimogFlags.is3043Active() || !SimogProperties.getInstance().isDataCreatedAfter3043(dataCreazione)) {
				 System.out.println("TECHNIS getDefaultScheda ESCLUSI 1");
				 nb.setViewName(IdentificativoSchede.getEscluso().getDecodificaVN());
				 nb.setPath(ParametriServlet.SRV_SCHEDA_ESCLUSI);
			 } else {
     		    nb.setViewName(IdentificativoSchede.getAggiudicazione().getDecodificaVN());
     		    nb.setPath(ParametriServlet.SRV_SCHEDA_A);
     		   System.out.println("TECHNIS getDefaultScheda ESCLUSI 2");
      	   }
		 }else if(flusso.equals(TipoFlusso.SOTTOSOGLIA)){
			 nb.setViewName(IdentificativoSchede.getSottosoglia().getDecodificaVN());
			 nb.setPath(ParametriServlet.SRV_SCHEDA_SOTTOSOGLIA);
			 System.out.println("TECHNIS getDefaultScheda SOTTOSOGLIA");
		 }else if(flusso.equals(TipoFlusso.STIPULA)){
			 nb.setViewName(IdentificativoSchede.getAggiudicazione().getDecodificaVN());
			 nb.setPath(ParametriServlet.SRV_SCHEDA_A);
			 System.out.println("TECHNIS getDefaultScheda STIPULA");
		 }else if(flusso.equals(TipoFlusso.AGGIUDICAZIONE)){
			 nb.setViewName(IdentificativoSchede.getAggiudicazione().getDecodificaVN());
			 nb.setPath(ParametriServlet.SRV_SCHEDA_A);
			 System.out.println("TECHNIS getDefaultScheda AGGIUDICAZIONE");
		 }
	 } catch (NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 map.put(nb.getTab(), nb);
	return map;
}   	  	    
}