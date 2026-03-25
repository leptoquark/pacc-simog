package it.avlp.simog.servlet.aggiudicazione;

import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.servlet.ServletBase;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: SrvPreInsertDatiComuni
 *
 */
 public class SrvPreInsertDatiComuni  extends ServletBase{
    	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		perform(request, response);
//	}

	
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if ( checkSession(request) ) {
			if ( currentUser.isRUP() || currentUser.isCS() ) {
				try{
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					String id_lotto = request.getParameter(FIELD_NAME_ID_LOTTO);
					
					GaraManager garaman = new GaraManager(currentActiveConnection,logger);
					TableBean dati = garaman.getDatiPreInsertAgg(id_lotto);
					
					request.setAttribute(ParametriServlet.DATI_PREINSERT_TABLEBEAN, dati);
					
					forward(JSP_INSERT_DATI_COMUNI_AGGIUDICAZIONE, request, response);
					
				}
				catch (Exception e) {
					sendError(request, response, SIMOG_AGGIUDICAZIONI_002, JSP_ERRORE, e );
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