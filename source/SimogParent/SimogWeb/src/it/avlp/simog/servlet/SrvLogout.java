package it.avlp.simog.servlet;

import it.avlp.simog.beans.MessageBean;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.errormessage.Messaggi;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: SrvLogout
 *
 */
public class SrvLogout extends ServletBase {

	private static final long serialVersionUID = -1295098973420411260L;

	public void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		
		perform(request, response);
	}
	
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if ( checkSession(request) ) {
			request.getSession().removeAttribute(SERVICE_AVAILABLE);
			request.getSession().invalidate();
			
			MessageBean exitMessage = new MessageBean ( Messaggi.SIMOG_LOGOUT_COMPLETED );
		
			request.setAttribute(ParametriServlet.ERRORBEAN, exitMessage );
		
			forward(ParametriServlet.JSP__LOGIN, request, response);
		} else {
			sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE);
		}
	}   	  	    
}