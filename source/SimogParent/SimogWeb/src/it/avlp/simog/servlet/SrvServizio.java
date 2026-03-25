package it.avlp.simog.servlet;

import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.SimogException;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: SrvServizio
 *
 */
 public class SrvServizio extends it.avlp.simog.servlet.ServletBase implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
    public SrvServizio() {
		super();
	}   	
    
 	public void doGet(HttpServletRequest request,HttpServletResponse response)
 	throws ServletException, IOException {
 		
 		// perform(request, response);
 	}
    
		
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// perform(request,response);
	}

//TODO: PP attivare la funzione vedi mainmenu.inc inserire popup conferma
	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if (checkSession(request) && currentUser != null) {
			if (currentUser.isAmministratore()) {
				try {
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
				
					AccessiDB adb = new AccessiDB(currentActiveConnection,logger);
					Boolean serviceAvailable = (Boolean)request.getSession().getAttribute(it.avlp.simog.common.servlet.ParametriServlet.SERVICE_AVAILABLE);
					request.getSession().removeAttribute(SERVICE_AVAILABLE);
					request.getSession().invalidate();
					if(serviceAvailable.booleanValue()){
						adb.disableService();
						sendMessage(request, response, "Servizio disabilitato con successo", ParametriServlet.JSP__LOGIN);
						
					}else{
						adb.enableService();
						sendMessage(request, response, "Servizio abilitato con successo", ParametriServlet.JSP__LOGIN);
					}
				} catch (SimogException e) {
					logger.fatal(e.getMessage());
					//e.printStackTrace();
					throw new ServletException(e);
				}
				finally{
					closeConnection(request.getSession().getId(),getClass().getName());
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
	
}