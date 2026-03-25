package it.avlp.simog.garamanager.lotto.app;

import it.avlp.simog.beans.Utente;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.lotto.EliminaDocumentoManager;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: SrvCancellaDocumento
 *
 */
 public class SrvCancellaDocumento extends it.avlp.simog.servlet.ServletBase implements javax.servlet.Servlet {
    
	 /**
	 * 
	 */
	private static final long serialVersionUID = -3538848331324874555L;

		protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			Connection currentActiveConnection = null;
			Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
			String nextPage = "gestisciLotto?action=modifica&idLotto=" + request.getParameter(FIELD_NAME_ID_LOTTO);
			
			if ( checkSession(request) ) {
				if ( currentUser.isRSSAorRUP() ) {
					try {
						currentActiveConnection = this.getSimogConnection(request.getSession().getId(),getClass().getName());
						
						EliminaDocumentoManager lista = new EliminaDocumentoManager( currentActiveConnection, logger );
						String id=request.getParameter("idDoc");
						lista.delete(id, currentUser.getUffici());
						sendMessage(request, response, Messaggi.SIMOG_UPLOAD_010, nextPage);
						return;

					} catch (Exception e) {
						sendError(request, response,Messaggi.SIMOG_UPLOAD_011, JSP_ERRORE, e );
						return;
					} finally {
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

//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		perform(request, response);
//	}  
}