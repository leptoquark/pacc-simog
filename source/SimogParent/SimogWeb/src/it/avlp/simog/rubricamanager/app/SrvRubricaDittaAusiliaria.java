package it.avlp.simog.rubricamanager.app;

import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletRubrica;
import it.avlp.simog.servlet.ServletBase;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class for Servlet: SrvRubrica
 *
 */
 public class SrvRubricaDittaAusiliaria extends ServletBase implements ParametriServlet{
	
	 	
	 	public void doGet(HttpServletRequest request,HttpServletResponse response)
	 	throws ServletException, IOException {
	 		
	 		perform(request, response);
	 	} 

	 	private static final long serialVersionUID = 1L;
	 	
	 	/**
	 	 * @see ServletBase#perform(HttpServletRequest, HttpServletResponse)
	 	 */
	 	public void perform(HttpServletRequest request, HttpServletResponse response)
	 	throws ServletException, IOException {
	 		
	 		String titolo = "";
	 		
	 		//gm nuovo per dati aggiudicatario
	 		String idRowAggiud = request.getParameter(PSBD.ID_TABELLA_AFFIDATARI);
	 		String denominazione = request.getParameter(PSBD.FIELD_NAME_AGG_DENOMINAZIONE);
	 		String cf = request.getParameter(PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO);
	 		String idPaese = request.getParameter(PSBD.FIELD_NAME_AGG_ID_PAESE);
	 		String ruolo = request.getParameter(PSBD.FIELD_NAME_AGG_RUOLO);
	 		String avval = request.getParameter(PSBD.FIELD_NAME_AGG_FLAG_AVVALIMENTO);
	 		String idGruppo = request.getParameter(PSBD.FIELD_NAME_AGG_ID_GRUPPO);
	 		
	 		request.setAttribute(PSBD.ID_TABELLA_AFFIDATARI, idRowAggiud);	 		
	 		request.setAttribute(PSBD.FIELD_NAME_AGG_DENOMINAZIONE, denominazione);
	 		request.setAttribute(PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO, cf);
	 		request.setAttribute(PSBD.FIELD_NAME_AGG_ID_PAESE, idPaese);
	 		request.setAttribute(PSBD.FIELD_NAME_AGG_RUOLO, ruolo);
	 		request.setAttribute(PSBD.FIELD_NAME_AGG_FLAG_AVVALIMENTO, avval);
	 		request.setAttribute(PSBD.FIELD_NAME_AGG_ID_GRUPPO, idGruppo);
	 		
	 		request.setAttribute(PSBD.VAR_ANN, request.getParameter(PSBD.VAR_ANN));
	 		
	 		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
	 		if ( checkSession(request) ) {
	 			
	 			// conservazione del titolo del titolo
	 			titolo = (String)request.getAttribute("titleRubrica");
	 			
	 			if (titolo == null || "".equals(titolo))
	 				titolo = request.getParameter("titleRubrica");
	 			
	 			request.setAttribute("titleRubrica", titolo);
	 			
 				if("menu".equals(request.getParameter("from"))){
 					request.getSession().removeAttribute(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE);
 					request.getSession().removeAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE);
 				}	 			
		 		
	 			//logger.debug("parameterValue - "+parameterValue);	 		

	 			if ( true) {				
	 				visualizzaListaParametriValori(request, response);

	 				performViewPopup(request, response);
	 			}
	 		}else {	sendError(request, response, SIMOG_LOGIN_003, JSP_ERRORE);}
	 	}
	 	
		/*************************************************************************************
		 * Effettua una ricerca per la rubrica di popup  
		 * @param request HttpServletRequest
		 * @param response HttpServletResponse
		 * @throws ServletException
		 * @throws IOException
		 */
		public void performViewPopup(HttpServletRequest request, HttpServletResponse response)		/*UN: aggiunto filtro per id_stato */
	 	throws ServletException, IOException {
			
			//Connection currentActiveConnection = null;
			Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
			String tab = request.getParameter(PSBD.TAB);
			HttpSession currentActiveSession = request.getSession();
			if ("menu".equals(request.getParameter("from"))){
				currentActiveSession.removeAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE);
				currentActiveSession.removeAttribute(ParametriServletRubrica.FIELD_NAME_NOME);
				currentActiveSession.removeAttribute(ParametriServletRubrica.FIELD_NAME_COGNOME);
				currentActiveSession.removeAttribute(ParametriServletRubrica.FIELD_NAME_ID_STATO);
			}
	 		if ( checkSession(request) ) {
	 			if ( true) {				
	 				visualizzaListaParametriValori(request, response);
	 				
	 				int startRow = 0;
	 				String action = request.getParameter(ParametriServlet.ACTION_GET_LIST);
	 				boolean actionNeeded = action != null;
	 				
	 				// ----------------------------------------------------------------------------------------------------------
	 				//   Parte per l'incremento o il decremento relativo alla righe da cui far visualizzare il resultset 
	 				//   in caso di paginazione                                                                         
	 				if ( actionNeeded ) {
	 					
	 					String startRowS = request.getParameter(ParametriServlet.START_ROW);
	 					
	 					startRow = Integer.parseInt(startRowS);
	 					if ( action.equalsIgnoreCase(ParametriServlet.REGRESS) ) {
	 						startRow = startRow - configuration.getMaxElementiPerPagina();
	 						//logger.Debug("startRow:"+startRow);
	 					} else {
	 						startRow = startRow + configuration.getMaxElementiPerPagina();
	 						//logger.Debug("startRow:"+startRow);
	 					}
	 				}
	 				// -----------------------------------------------------------------------------------------------------------
	 				
	 				String codiceFiscale = null;
	 				String cognome = null;
	 				String nome = null;
	 				String id_stato = null;
	 				codiceFiscale = (request.getParameter(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE)!=null) ? request.getParameter(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE) : "";
	 				logger.debug("codiceFiscale nei Parametri- "+codiceFiscale);
	 				
	 				cognome = (request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME)!=null) ? request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME) : "";
	 				logger.debug("cognome nei Parametri- "+cognome);
	 						 					
	 				id_stato = (request.getParameter(ParametriServletRubrica.FIELD_NAME_ID_STATO)!=null) ? request.getParameter(ParametriServletRubrica.FIELD_NAME_ID_STATO) : "";
	 				logger.debug("id_stato - "+id_stato);

	 				// PP se vengo da un inserimento (denominazione valorizzato, uso i valori in sessione per cercare
	 				if(request.getParameter(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE)!=null){
	 					cognome = "";
	 					codiceFiscale = (String) request.getSession().getAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE);
		 				id_stato = (String) request.getSession().getAttribute(ParametriServletRubrica.FIELD_NAME_ID_STATO);
	 				}
	 				else {

	 				//nome = ""; // PP non usato per ricerca aggiudicatari
	 				
	 				//*************************************************************************************************
	 				// Questa parte � utile per prelevare codice fiscale e cognome/denominazione nel caso della 
	 				// paginazione per poter rieffettuare la query.
	 				
	 				if ( request.getParameter("paginazione") != null ) {
	 				
	 				currentActiveSession.setAttribute(ParametriServletRubrica.FIELD_NAME_COGNOME, cognome);
	 				currentActiveSession.setAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE, codiceFiscale);
	 				currentActiveSession.setAttribute(ParametriServletRubrica.FIELD_NAME_ID_STATO, id_stato);
	 				}
	 				
	 				if ( currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_COGNOME) != null ){
	 				cognome = ( (String) currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_COGNOME) );
	 				logger.debug("Denominazione/Cognome in sessione - "+cognome);
	 				}
	 				if (currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE) != null ) {
	 				codiceFiscale = (String) currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE);
	 				logger.debug("codiceFiscale in sessione - "+codiceFiscale);
	 				}
	 				
	 				if (currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_ID_STATO) != null ) {
			 		id_stato = (String) currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_ID_STATO);
			 		logger.debug("id_stato in sessione - "+id_stato);
			 		}
	 				//**************************************************************************************************
		 				
	 				}
	 				
					forward("scheda1/"+ParametriServletRubrica.JSP_RUBRICA_DITTA_AUSILIARIA_POPUP, request, response);

	 			} else {
	 				sendError(request, response, SIMOG_LOGIN_004, JSP_ERRORE );				
	 			}
	 		} else {
	 			sendError(request, response, SIMOG_LOGIN_003, JSP_ERRORE );
	 		}
		}	 				
}