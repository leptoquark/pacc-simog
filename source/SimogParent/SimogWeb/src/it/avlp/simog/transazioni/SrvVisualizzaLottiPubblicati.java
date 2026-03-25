package it.avlp.simog.transazioni;

import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.servlet.ServletBase;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SrvVisualizzaLottiPubblicati extends ServletBase {
	
	public static final long serialVersionUID = 1;
	
	public void doGet(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException{
		perform( request, response);
	}

	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		visualizzaListaParametriValori(request, response);
		
		if ( checkSession(request) ) {
			if ( currentUser.isAVLP() ) {
				
				String idAmministrazione = request.getParameter(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
				String pageCalling = ParametriServlet.JSP_RICERCA_TRANSAZIONI;

				try {				
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					TransazioniManager tManager= new TransazioniManager(currentActiveConnection,logger);

					TableBean results = null;
					
					if ( idAmministrazione == null ) {
						//results = tManager.getAllLottiPubblicati();
					} else {
						//results = tManager.getLottiPubblicati( idAmministrazione );
					}


					if ( results.getTableSize() > 0 ) {
						request.setAttribute(TABLEBEAN, results);
						forward(ParametriServlet.JSP_VISUALIZZA_LOTTI, request, response);				
					} else {
						sendMessage(request, response, Messaggi.SIMOG_RIC_001, pageCalling);
						return;
					}
				} catch(Exception e){
					String error = Messaggi.SIMOG_TRS_003;
					logger.fatal( error, e);
					sendError ( request, response, error, pageCalling, e );
					return;
				} finally {
					closeConnection(request.getSession().getId(),getClass().getName());
				}
			} else {
				sendError(request, response, SIMOG_LOGIN_003, JSP_ERRORE);
				return;
			}
		} else {
			sendError(request, response, SIMOG_LOGIN_004, JSP_ERRORE);
			return;
		}
	}
		
	/******************************************************************************************
	 * Permette la scrittura del CSV basata sulla TableBean rusult
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param results TableBean
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performCSV( HttpServletRequest request, HttpServletResponse response, TableBean results ) throws ServletException, IOException{

			response.setContentType("application/x-download; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\"LottiPubblicati.csv\"");
			results.writeCSV(response.getWriter(),';');
			response.getWriter().flush();
			response.getWriter().close();
	}

}
