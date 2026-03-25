package it.avlp.simog.servlet;

import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.MultilottoManager;
import it.avlp.simog.actions.AllValidationAction;
import it.avlp.simog.actions.DelegaDatiSimogAction;
import it.avlp.simog.actions.aggiudicazione.InfoComuniAction;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.NavigationBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.common.action.MultilottoAction;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.errormessage.Messaggi;
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
 public class SrvEditMultilotto extends ServletBase{
    
	private static final long serialVersionUID = 1L;

//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		perform(request, response);
//	}
	
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		HttpSession currentActiveSession = request.getSession();
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if ( checkSession(request) ) {
			if ( currentUser.isRUP()) {
				try {
					visualizzaListaParametriValori(request, response);
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					currentActiveConnection.setAutoCommit(false);
				    currentActiveConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);	
				
                    String action = request.getParameter("toDo");
					String idLotto = request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO);							
					String codiceContratto = request.getParameter(PSBD.FIELD_NAME_CODICE_CONTRATTO);
                   
					MultilottoAction mla = new MultilottoAction(currentActiveConnection,logger);
					MultilottoManager mm = new MultilottoManager(currentActiveConnection,logger);
	        		List<AggiudicazioneBean> listaAggiudicazioniStessoContratto =  mm.getAggiudicazioniListMultilotto(codiceContratto, Long.parseLong(idLotto));

					if(action==null)
						action = ParametriServlet.ACTION_NEW;
					
                    if(ParametriServlet.ACTION_NEW.equals(action)){
                    	request.setAttribute(ParametriServlet.LISTA_AGGIUDICAZIONI, listaAggiudicazioniStessoContratto);
    	        		request.setAttribute(ParametriServlet.FIELD_NAME_ID_LOTTO, idLotto);
    	        		request.setAttribute(PSBD.FIELD_NAME_CODICE_CONTRATTO, codiceContratto);
    	        		forward(JSP_EDIT_MULTILOTTO, request, response);
					}
                    else if(ParametriServlet.ACTION_MODIFICA_GRUPPO.equals(action)){
    					String [] aggiudicazioniDaEliminare = request.getParameterValues(ParametriServlet.AGGIUDICAZIONI_DA_ELIMINARE);
    	    	    	List<String> listaAggDaEliminare = mla.fromArrayToList(aggiudicazioniDaEliminare);
    					mla.validaModificaGruppo(listaAggiudicazioniStessoContratto, listaAggDaEliminare);
						
    					if(mla.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize()==0){
				    		mla.modificaGruppo(listaAggiudicazioniStessoContratto, listaAggDaEliminare);
				    		
				    		currentActiveConnection.commit();
				    		
				    		String url = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + 
			    			ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + idLotto;
			    			forward(url, request, response);
						}
						else{
							logger.error ( "fallita la modifica del gruppo multilotto" );
							request.setAttribute(ParametriServlet.LISTA_AGGIUDICAZIONI, listaAggiudicazioniStessoContratto);
	    	        		request.setAttribute(ParametriServlet.FIELD_NAME_ID_LOTTO, idLotto);
	    	        		request.setAttribute(PSBD.FIELD_NAME_CODICE_CONTRATTO, codiceContratto);
	    					request.setAttribute(ParametriServlet.AGGIUDICAZIONI_DA_ELIMINARE, aggiudicazioniDaEliminare);
	    	       					
							String url = JSP_EDIT_MULTILOTTO;
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