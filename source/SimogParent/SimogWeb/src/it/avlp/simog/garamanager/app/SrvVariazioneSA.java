package it.avlp.simog.garamanager.app;

import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avcp.simog.managers.variazioneSA.VariazioneSAManager;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.Amministrazione;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.StazioneAppaltante;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.actions.VariazioneSAAction;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletConclusioni;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.log.LogManager;
import it.avlp.simog.servlet.ActionEnum;
import it.avlp.simog.servlet.BeanUtilsServlet;
import it.avlp.simog.util.ObjectIntrospector;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.AxisFault;

public class SrvVariazioneSA extends BeanUtilsServlet implements ParametriServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		perform(request, response);
	}
	
	protected void perform(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		Connection currentActiveConnection = null;
		visualizzaListaParametriValori(request, response);
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		String dest = "variazioneSA.jsp";
		
		try{
		    currentActiveConnection = getSimogConnection(request.getSession().getId(),  getClass().getName());
		    currentActiveConnection.setAutoCommit(false);
		   	String message = null;

			LogManager logManager = new LogManager(currentActiveConnection, logger);
			
		    if ( checkSession(request) && currentUser.isAmministratore() ) {
		    	VariazioneSAAction saAction = new VariazioneSAAction(currentActiveConnection,logger, configuration);
		    	VariazioneSAManager saManager = new VariazioneSAManager(currentActiveConnection,logger);
		    	ActionEnum action = getOperazione(request); 
		    	logger.info("Operazione richiesta: [ " + action.name() + " ]");
		    	//Long idGara =(Long) getValueFromContexts(Long.class, "idGara", request);
		    	Long idGara = Long.parseLong(request.getParameter(ParametriServlet.SESSION_ID_GARA));
		    	//gm nuovo codice simog 3.06
				String idMotivo = request.getParameter(ParametriServlet.ID_MOTIVO_VARIAZIONE_SA);
		
				request.setAttribute("motiviVariazione", saManager.loadMotiviVariazioneSA());
                request.setAttribute(ParametriServlet.SESSION_ID_GARA, idGara);
                request.setAttribute(ParametriServlet.MOTIVI_VARIAZIONE_SA, idMotivo);
                request.setAttribute(ParametriServlet.ID_MOTIVO_VARIAZIONE_SA, idMotivo);
                
		    	switch (action) {
				case NONE:
				   
	                if( SimogFlags.is3030_RFWEBGL02Active() ){
	                   AVCPassAction avpa = new AVCPassAction(currentActiveConnection, logger, configuration); 
	                   if( avpa.isAVCPass(new Gara(Long.valueOf(idGara)), null, AVCPassFunzioneEnum.WEB_VARIAZIONE_SA.getCodice()) ){
                          String targetPage = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara + "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;                               
                          AllValidationBeans msgs = new AllValidationBeans();
                          msgs.addValidationErr(SIMOG_AVCPASS_001);
                          sendValidations(request, response, msgs, targetPage);   
	                      return;
	                   }
	                } 				   
				   
					Amministrazione amm1 =(Amministrazione) super.getObjectFromRequest(Amministrazione.class, request);
					logger.debug(ObjectIntrospector.propertiesInfo(Amministrazione.class, amm1));			
			    	List<StazioneAppaltante> saList1 = new ArrayList<StazioneAppaltante>();	    	
			    	request.setAttribute("resultList", saList1);
					
					break;
					
				case LOAD:
					Amministrazione amm =(Amministrazione) super.getObjectFromRequest(Amministrazione.class, request);
					logger.debug(ObjectIntrospector.propertiesInfo(Amministrazione.class, amm));		
			    	List<StazioneAppaltante> saList = new ArrayList<StazioneAppaltante>();
			    	try{
			    		saList = saAction.getSAList(amm, configuration.getWsAnagUrl(), configuration.getWsAnagUser(), configuration.getWsAnagPwd());
			    		if(saList.size() == 0)
				    		message = SIMOG_RIC_001;
			    	}
			    	
			    	catch(AxisFault se) {
			    		message = se.getFaultString();
			    	}
			    	request.setAttribute("resultList", saList);
					
					break;
					
				case SELECT:
					Map<String, String> mappaMotivi = saManager.loadMotiviVariazioneSA();
					amm =(Amministrazione) super.getObjectFromRequest(Amministrazione.class, request);
					logger.debug(ObjectIntrospector.propertiesInfo(Amministrazione.class, amm));		
			    	saList = new ArrayList<StazioneAppaltante>();
			    	try{
			    		saList = saAction.getSAList(amm, configuration.getWsAnagUrl(), configuration.getWsAnagUser(), configuration.getWsAnagPwd());
			    		if(saList.size() == 0)
				    		message = SIMOG_RIC_001;
			    	}    	
			    	catch(AxisFault se) {
			    		message = se.getFaultString();
			    	}
			    	request.setAttribute("resultList", saList);
			    	
					if(mappaMotivi.get(String.valueOf(idMotivo))!=null){	
			     		StazioneAppaltante sa = (StazioneAppaltante) super.getObjectFromRequest(StazioneAppaltante.class, request);
			    		logger.debug(ObjectIntrospector.propertiesInfo(StazioneAppaltante.class, sa));
			    		if ( saAction.doVariazione(idGara, Long.parseLong(idMotivo), sa) ){
		    				message = SIMOG_VARIAZIONE_SA_001; 
						
			    			logManager.log(getTodayDate(), sa.getIdUfficio(), 
									currentUser.getLogin(), "", LogManager.VAR_SA, 
									sa.getCodiceFiscaleAmministrazione(), "", String.valueOf(idGara));
			    			commit(currentActiveConnection);
			    		}
					
			    		//request.setAttribute(ParametriServlet.FROM_GARE, Costanti.FLAG_VALORE_SI);	
			    		dest = ParametriServlet.SRV_VISUALIZZA_DETTAGLIO;
					}
					else{
						dest = "variazioneSA.jsp";
				        sendError(request, response, SIMOG_VALIDAZIONE_104.replace("$1","Motivazione della variazione"), dest);				
					}
					break;

				default:
					break;
				}
		    	request.setAttribute(SESSION_ID_GARA, idGara);
		    	request.setAttribute("idGara", idGara);
		    	if(message != null)
		    		sendMessage(request, message);
		    	forward(dest , request, response);
		    	return;
		    }
		    // else di checkSession
		    else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE);
				return;
			}
		}		
	    catch ( ActionException ae ) {
		    ae.printStackTrace();
	    	rollback(currentActiveConnection);					
		    sendError( request, response, ae.getMessage(), JSP_ERRORE);
		return;
	    }
	    catch ( SimogException se ) {
		    se.printStackTrace();
		    rollback(currentActiveConnection);					
		    sendError( request, response, se.getMessage(), JSP_ERRORE);
		return;
	    } 
	    catch ( Exception e ) {
		    e.printStackTrace();
		    rollback(currentActiveConnection);
		    logger.fatal ( e.getMessage());
		    sendError(request, response, e.getMessage(), JSP_ERRORE);
		return;
	    } 
	    finally {		
	    	
		    closeConnection(request.getSession().getId(),getClass().getName());
	    }		
	}

	
}