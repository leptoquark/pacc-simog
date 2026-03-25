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

public class SrvVisualizzaTabelleServizio extends ServletBase {
	
	boolean AscDesc = false;

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		perform( request, response );
		AscDesc = !AscDesc;
	}
	
	private static final long serialVersionUID = -4146606657004412143L;

	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if ( checkSession(request)  ) {
			if ( currentUser.isAmministratore() ) {
			
				String dataUltimaModificaRichiesta = request.getParameter(ParametriServlet.TAB_SERVIZIO_DATA);
				
				String fieldToSort = request.getParameter(ParametriServlet.ORDER_FIELD);
				
				String tabellaCorrente = request.getParameter(FIELD_NAME_TABELLA_SERVIZIO);
				
				try {
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					TabManager tabManager = new TabManager(currentActiveConnection, logger);
					
					TableBean variazioni = tabManager.getStatoVariazioniByTabella( tabellaCorrente, dataUltimaModificaRichiesta, fieldToSort, AscDesc );
					
					if ( dataUltimaModificaRichiesta != null ) {
						request.setAttribute(ParametriServlet.TAB_SERVIZIO_DATA, dataUltimaModificaRichiesta);
					}
					
					request.setAttribute(ParametriServlet.TABLEBEAN, variazioni);
					request.setAttribute(ParametriServlet.FIELD_NAME_TABELLA_SERVIZIO, tabellaCorrente);
					
					request.setAttribute(ParametriServlet.FIELD_NAME_TABELLA_INFO, tabManager.getTabellaInfo(tabellaCorrente));
					
					forward(ParametriServlet.JSP_AGGIORNAMENTI_TABELLA_DETTAGLIO, request, response);
				} catch (Exception e) {
					sendError(request, response, e.getMessage(), ParametriServlet.JSP_GESTIONE_TABELLE, e);
					return;
				} finally {
					closeConnection(request.getSession().getId(),getClass().getName());
				}
			} else {
				sendError(request, response, SIMOG_LOGIN_004, JSP_ERRORE );
				return;
			}
		} else {
			sendError(request, response, SIMOG_LOGIN_003, JSP_ERRORE );
			return;
		}
	}

	
}
