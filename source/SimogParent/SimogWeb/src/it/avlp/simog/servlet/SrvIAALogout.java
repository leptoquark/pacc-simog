package it.avlp.simog.servlet;

import it.avlp.simog.login.iaa.IAACostanti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Logout dell'utente
 */

public class SrvIAALogout extends ServletBase {
	
//	protected void doGet(HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException {
//		perform(request, response);
//	}
	
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		session.removeAttribute(IAACostanti.SIMOG_SAML_RESPONSE);
		request.getSession().removeAttribute(SERVICE_AVAILABLE);
		session.invalidate();

		response.sendRedirect(configuration.getSamlLogoutUrl());
		//forward(configuration.getSamlLogoutUrl(), request, response);	
    }
}
