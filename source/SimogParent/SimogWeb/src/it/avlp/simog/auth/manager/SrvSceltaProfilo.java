package it.avlp.simog.auth.manager;

import it.avlp.simog.actions.DelegaDatiSimogAction;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SrvSceltaProfilo
 */
public class SrvSceltaProfilo extends ServletBase implements Servlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public SrvSceltaProfilo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if (checkSession(request)) {
			HashMap<String, String> paginaProfilo = new HashMap();
			paginaProfilo
					.put(ProfiloEnum.AMMINISTRATORE.codice(), JSP_AMM_HOME);
			paginaProfilo.put(ProfiloEnum.AVLP.codice(), JSP_AVCP_HOME);
			paginaProfilo.put(ProfiloEnum.RSSAOLD.codice(), JSP_RSSA_HOME);
			paginaProfilo.put(ProfiloEnum.RUP.codice(), JSP_RUP_CS_HOME);
			paginaProfilo.put(ProfiloEnum.OSSREG.codice(), JSP_OSSREG_HOME);
			paginaProfilo.put(ProfiloEnum.RASA.codice(), JSP_OSSREG_HOME);
            
			if(SimogFlags.isOSSNActive())
                  paginaProfilo.put(ProfiloEnum.OSSNAZ.codice(), JSP_OSSREG_HOME);
			
            boolean done = true;
			String selectedProfileReq = request
					.getParameter(ParametriServlet.SCELTA_PROFILO);
			String selectedProfileAttr = (String) request
					.getAttribute(ParametriServlet.SCELTA_PROFILO);
			String nextPage = ParametriServlet.JSP_ERRORE;
			if (selectedProfileReq != null) {
				if (currentUser.getProfili().containsKey(selectedProfileReq)) {
					currentUser.getProfili().clear();
					currentUser.setProfilo(selectedProfileReq);
					nextPage = paginaProfilo.get(selectedProfileReq);
				}
			} else if (selectedProfileAttr != null) {
				if (currentUser.getProfili().containsKey(selectedProfileAttr)) {
					currentUser.getProfili().clear();
					currentUser.setProfilo(selectedProfileAttr);
					nextPage = paginaProfilo.get(selectedProfileAttr);
				}
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_001,
						JSP_PROFILO);
			}
			try {
				currentActiveConnection = getSimogConnection(request
						.getSession().getId(), getClass().getName());

				DelegaDatiSimogAction ddsAction = new DelegaDatiSimogAction(
						currentActiveConnection, logger);

				String message = ddsAction.getDelegaAllMessage(PageHelper.getIncreasedDate(
									PageHelper.getCurrentDate(), 0).getTime(), currentUser);
				
				sendMessage(request, response, message,nextPage);
				
/** PP solo info
				if (message == null || currentUser.isRSSAorRUP())
					forward(nextPage, request, response);
				else {
					request.getSession().invalidate();
					sendError(request, response, message, JSP_ERRORE);
				}
**/
			} catch (Exception e) {
				e.printStackTrace();
				logger.fatal(e);
				rollback(currentActiveConnection);
				sendError(request, response, Messaggi.SIMOG_LOGIN_002,
						JSP_ERRORE, e);
			}
		    finally {		
			    closeConnection(request.getSession().getId(),getClass().getName());
		    }	

		}

	}

}
