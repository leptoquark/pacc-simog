package it.avlp.simog.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.log.LogManager;
import it.avlp.simog.util.PageHelper;


public class SrvPresaInCaricoGara extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7147215868373215565L;

	@Override
	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if (checkSession(request)) {
			if (currentUser.isRUP()) {
				try{
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					currentActiveConnection.setAutoCommit(false);
					String action = request.getParameter("toDo");
					Long idGara = Long.parseLong(request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA));
					request.setAttribute(ParametriServlet.FIELD_NAME_ID_GARA, idGara.toString());
					String estremi = request.getParameter(PSBD.MOTIVAZIONE_ANNULLAMENTO);
					if (action == null){
						action = PSBD.ACTION_CARICA_JSP_ANNULLAMENTO;
					}			
					
					GaraManager gm = new GaraManager(currentActiveConnection, logger);
					
					Gara gara = gm.getGara(idGara);
					
					if("Conferma".equalsIgnoreCase(action)){
						if(estremi != null && !estremi.trim().equals("")){
						   
                     gara.setCF_UTENTE(currentUser.getLogin());
                     gara.setPROVV_PRESA_CARICO(estremi);
                     
                     // salvo
                     gm.updateGaraPresaInCarico(gara);
                     
                     LogManager logManager = new LogManager(currentActiveConnection, logger);
                     logManager.log(
                           PageHelper.getDBDateFromTS(new AccessiDB(currentActiveConnection,logger).getNow()),
                           gara.getID_STAZIONE_APPALTANTE(),
                           currentUser.getLogin(),
                           "",
                           LogManager.PRESA_CARICO,
                           gara.getCF_AMMINISTRAZIONE(),
                           "",
                           String.valueOf(gara.getId_Gara()));
                     
							commit(currentActiveConnection);
							String requestingURL = ParametriServlet.SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara;
							sendMessage(request, response, Messaggi.SIMOG_AGGIUDICAZIONI_081.replace("$1", "Presa in carico Gara"), requestingURL);
							return;
						}else{
							sendError(request, response, Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Estremi provvedimento di nomina"), ParametriServlet.JSP_RICHIEDI_PRESAINCARICOGARA );
							return;
						}
					}
					else if(PSBD.ACTION_CARICA_JSP_ANNULLAMENTO.equalsIgnoreCase(action)){
						String dest = ParametriServlet.JSP_RICHIEDI_PRESAINCARICOGARA + "?" + ParametriServlet.SESSION_ID_GARA + "=" + idGara;
						forward(dest, request, response);
						return;
					}
					
					forward(ParametriServlet.JSP_ERRORE , request, response);
					
					return;
				} catch (Exception e) {
					logger.fatal(e);
					//e.printStackTrace();
					rollback(currentActiveConnection);
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE , e);
					
					return;

				} finally {
					closeConnection(request.getSession().getId(),getClass().getName());
				}
			}
		}

	}
	

}
