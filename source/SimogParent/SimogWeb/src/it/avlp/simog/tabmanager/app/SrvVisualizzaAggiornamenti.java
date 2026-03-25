package it.avlp.simog.tabmanager.app;

import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.tabmanager.TabManager;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SrvVisualizzaAggiornamenti extends ServletBase {

	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		perform( request, response );
//	}
	
	private static final long serialVersionUID = -4146606657004412143L;

	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if ( checkSession(request) && currentUser.isAmministratore() ) {
			
			String tabellaCorrente = request.getParameter(FIELD_NAME_TABELLA_SERVIZIO);
            String requisiti = request.getParameter(ACTION);
			
			try {
				currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
				TabManager tabManager = new TabManager(currentActiveConnection, logger);
				
				TableBean variazioni = tabManager.getVariazioniByTabella( tabellaCorrente, (requisiti != null) );
							
				request.setAttribute(ParametriServlet.TABLEBEAN, variazioni);
				request.setAttribute(ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO, tabellaCorrente);
				
				forward(ParametriServlet.JSP_AGGIORNAMENTI_TABELLA_ELENCO, request, response);
			} catch (Exception e) {
				sendError(request, response, e.getMessage(), ParametriServlet.JSP_GESTIONE_TABELLE, e);
				return;
			} finally {
				closeConnection(request.getSession().getId(),getClass().getName());
			}
		}
	}

	
}
