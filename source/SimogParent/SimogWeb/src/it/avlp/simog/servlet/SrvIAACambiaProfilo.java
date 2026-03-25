package it.avlp.simog.servlet;

import it.avlp.simog.login.iaa.IAACostanti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Consente di cambiare il profilo con cui l'utente e' loggato
 */
public class SrvIAACambiaProfilo extends ServletBase {
	
	private static final long serialVersionUID = 1L;

//	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
//		perform(arg0, arg1);
//	}

	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getSession().removeAttribute(IAACostanti.SIMOG_SAML_RESPONSE);
		request.getSession().removeAttribute(SERVICE_AVAILABLE);
		request.getSession().invalidate();
		
		response.sendRedirect(configuration.getSamlProfileUrl());
		//forward(configuration.getSamlProfileUrl(), request, response);
	}   	  	    
}
