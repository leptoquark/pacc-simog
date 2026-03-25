package it.avlp.simog.rubricamanager.app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.avcp.simog.managers.aggiudicazione.ResponsabileManager;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.RubricaResponsabili;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletRubrica;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.SOGGETTI_RESPONSABILI;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.rubricamanager.RubricaResponsabiliManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.Base64Coder;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.factory.ValidatorFactory;

/**
 * Servlet implementation class for Servlet: SrvRubrica
 *
 */
 public class SrvRubricaResponsabili extends ServletBase implements ParametriServlet{
	
	 	
	 	public void doGet(HttpServletRequest request,HttpServletResponse response)
	 	throws ServletException, IOException {
	 		
	 		perform(request, response);
	 	}

	 	private static final long serialVersionUID = 1L;
	 	
	 	
	 	/*********************************************************************************
	 	 * Restituisce True se l'operazione in Rubrica risulta la stessa 
	 	 * di quella indicata nel campo checkValue.
	 	 * @param request HttpServletRequest
	 	 * @param checkValue String
	 	 * @return boolean
	 	 */
	 	private boolean switchOperation(HttpServletRequest request,  String checkValue){
	 		String parameterValue = request.getParameter(ParametriServletRubrica.OPERAZIONE);
	 		logger.debug("parameterValue - "+parameterValue);	 		
	 		return (parameterValue!=null && checkValue.equals(parameterValue));
	 	}
	 	
	 	/**
	 	 * @see ServletBase#perform(HttpServletRequest, HttpServletResponse)
	 	 */
	 	public void perform(HttpServletRequest request, HttpServletResponse response)
	 	throws ServletException, IOException {
	 		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
	 		if ( checkSession(request) ) {
	 			if ( currentUser.isRUP() || currentUser.isCS()) {				
	 				visualizzaListaParametriValori(request, response);
	 				
	 				if("menu".equals(request.getParameter("from"))){
	 					request.getSession().removeAttribute(ParametriServletRubrica.FIELD_NAME_COGNOME);
	 					request.getSession().removeAttribute(ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE);
	 				}
	 				
		 			// conservazione del titolo del titolo
		 			String titolo = (String)request.getAttribute("titleRubrica");
		 			
		 			if (titolo == null || "".equals(titolo))
		 				titolo = request.getParameter("titleRubrica");
		 			
		 			request.setAttribute("titleRubrica", titolo);
	 						
	 				//if (switchOperation(request,"view")) { //|| switchOperation(request, "Cerca")){
	 				if (switchOperation(request,"view") || switchOperation(request,"Indietro")) { //|| switchOperation(request, "Cerca")){
	 					logger.debug("SrvRubrica - il parametro d'ingresso e' parameter = view" );
	 					logger.debug("Passo nell'if VIEW");
	 					performView(request,response);
	 				}else if( switchOperation(request, "Modifica")) {
	 					logger.debug("SrvRubrica - il parametro d'ingresso e' parameter = modifica *****************" );
	 					logger.debug("Passo nell'if Modifica");
	 					performUpdate(request,response);
	 				}else if( switchOperation(request, "Cerca")) {
	 					logger.debug("SrvRubrica - il parametro d'ingresso e' parameter = modifica *****************" );
	 					performView(request,response);
	 				}else if (switchOperation(request, "viewDetail")){
	 					logger.debug("SrvRubrica - il parametro d'ingresso e' parameter = viewDetail *****************" );
	 					performViewDetail(request, response);
	 				}else if (switchOperation(request, "Aggiungi alla rubrica")){
	 					logger.debug("SrvRubrica - il parametro d'ingresso e' parameter = Aggiungi alla rubrica *****************" );
	 					performInsert(request, response);
	 				}else if (switchOperation(request, "Salva")){
	 					logger.debug("SrvRubrica - il parametro d'ingresso e' parameter = Aggiungi alla rubrica *****************" );
	 					performSave(request, response);
	 				}else if (switchOperation(request, "Cancella")){
	 					logger.debug("SrvRubrica - il parametro d'ingresso e' parameter = Cancella *****************" );
	 					performDelete(request, response);
	 				}else if (switchOperation(request, "viewAfterDelete")){
	 					logger.debug("SrvRubrica - il parametro d'ingresso e' parameter = Cancella *****************" );
	 					performViewAfterDelete(request, response);
	 				}else if (switchOperation(request, "PopUpMod")){
	 					performModPopup(request, response);
	 				}else if (switchOperation(request, "validaDati")){
	 					performValida(request, response);
	 				}

	 			}
	 		}else {	sendError(request, response, SIMOG_LOGIN_003, JSP_ERRORE);}
	 	}
	 	
		/****************************************************************************************
		 * Effettua il forward alla jsp di dettaglio rubrica popup  
		 * @param request HttpServletRequest
		 * @param response HttpServletResponse
		 * @throws ServletException
		 * @throws IOException
		 */
		public void performModPopup(HttpServletRequest request, HttpServletResponse response)
	 	throws ServletException, IOException {
			Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
			request.setAttribute("parametri",request.getParameter("parametri"));
	 		if ( checkSession(request) ) {
	 			if ( currentUser.isRUP() || currentUser.isCS()) {				
	 				visualizzaListaParametriValori(request, response);
	 				try{
	 					Connection currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
	 					
	 					TableBean dettaglioPartecipante = null;		
	 									
	 					RubricaResponsabiliManager rubricaManager = new RubricaResponsabiliManager(currentActiveConnection, logger);

		 				String id_responsabile = request.getParameter("id");
		 				String data = request.getParameter("data");
		 				String elem = request.getParameter("elem");

		 				request.setAttribute("id", id_responsabile );
		 				request.setAttribute("data", data );
		 				request.setAttribute("elem", elem );
		 				
		 				if (elem != null  && !"".equals(elem) && !"*".equals(elem)){
		 					// riprendo i dati della sezione
			    			String [] stk = Base64Coder.decodeString(elem).split(PSBD.SEP_VARANAG_S,-1);
		 					int i = 0;
		 					dettaglioPartecipante = new TableBean();
		 					
		 					TableBeanRow row = new TableBeanRow(dettaglioPartecipante);
		 					
		 					row.addFieldValue(SOGGETTI_RESPONSABILI.ID_RESPONSABILE, stk[i++]);
		 					row.addFieldValue(SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE, stk[i++]);
		 					row.addFieldValue(SOGGETTI_RESPONSABILI.COGNOME, stk[i++]);
		 					row.addFieldValue(SOGGETTI_RESPONSABILI.NOME, stk[i++]);
		 					row.addFieldValue(SOGGETTI_RESPONSABILI.TELEFONO, stk[i++]);
		 					row.addFieldValue(SOGGETTI_RESPONSABILI.FAX, stk[i++]);
		 					row.addFieldValue(SOGGETTI_RESPONSABILI.EMAIL, stk[i++]);
		 					row.addFieldValue(SOGGETTI_RESPONSABILI.INDIRIZZO, stk[i++]);
		 					row.addFieldValue(SOGGETTI_RESPONSABILI.CAP, stk[i++]);
		 					row.addFieldValue(SOGGETTI_RESPONSABILI.COMUNE_ISTAT, stk[i++]);
		 					row.addFieldValue(SOGGETTI_RESPONSABILI.DATA_INIZIO_RES, data);
		 							 					
		 					dettaglioPartecipante.addRow(row);
		 				}
		 				else{
		 					// lettura dal db 
		 					dettaglioPartecipante = rubricaManager.getDettaglioPartecipante(Integer.parseInt(id_responsabile),
		 							0,	configuration.getMaxElementiPerPagina()	);		 					
		 				}
	 					if ( dettaglioPartecipante.size() == 0 ) {
	 						sendMessage(request, response, Messaggi.SIMOG_RIC_001, "scheda1/"+ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RES_POPMOD);
	 					} else {
	 						request.setAttribute(ParametriServlet.TABLEBEAN, dettaglioPartecipante);
	 						forward("scheda1/"+ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RES_POPMOD, request, response);
	 					}
	 					}catch(Exception e){		
	 					sendError(request, response, e.getMessage(), "scheda1/"+ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RES_POPMOD, e);
	 				}finally {
	 					closeConnection(request.getSession().getId(),getClass().getName());
					}			
	 			}else {
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				}
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			} 		
	 	}	 	
	 	
	 	/******************************************************************************************
	 	 * Gestisce la visualizzazione della rubrica dei partecipanti tramite 
	 	 * criteri di ricerca per codice fiscale  e denominazione
	 	 * @param request HttpServletRequest
	 	 * @param response HttpServletResponse
	 	 * @throws ServletException
	 	 * @throws IOException
	 	 */
	 	public void performViewAfterDelete(HttpServletRequest request, HttpServletResponse response)
	 	throws ServletException, IOException {
	 		Connection currentActiveConnection = null;
	 		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
	 		if ( checkSession(request) ) {
	 			if ( currentUser.isRUP() || currentUser.isCS()) {				
	 				visualizzaListaParametriValori(request, response);
	 				
	 				int startRow = 0;
	 				String action = request.getParameter(ParametriServlet.ACTION_GET_LIST);
	 				logger.debug("action:::"+action);
	 				boolean actionNeeded = action != null;

	 				if ( actionNeeded ) {
	 					
	 					String startRowS = request.getParameter(ParametriServlet.START_ROW);
	 					
	 					startRow = Integer.parseInt(startRowS);
	 					if ( action.equalsIgnoreCase(ParametriServlet.REGRESS) ) {
	 						startRow = startRow - configuration.getMaxElementiPerPagina();
	 						
	 					} else {
	 						startRow = startRow + configuration.getMaxElementiPerPagina();
	 						
	 					}
	 				}
	 				
	 				String denominazione = "";
	 				String codiceFiscale = "";
	 				
	 				if(request.getParameter(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE_RIC )!=null)
	 					denominazione=request.getParameter(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE_RIC);
	 				
	 			
	 				if(request.getParameter(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE_RIC)!=null)
	 					codiceFiscale=request.getParameter(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE_RIC).toUpperCase();
	 					//codiceFiscale = request.getParameter("codiceFiscale");
	 				
	 				
	 				
	 				try {
	 					
	 					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());

	 					TableBean rubricaList = null;
	 					RubricaResponsabiliManager rubricaManager = new RubricaResponsabiliManager(currentActiveConnection, logger);
	 					logger.debug("Esecuzione getPartecipantiListRes");
	 					logger.debug("denominazione "+denominazione);
	 					logger.debug("codice "+codiceFiscale);
	 					
	 					//passo denominazione e codice come stringa vuota poiche' il codice e' valorizzato sicuramente
	 					//e quindi nella select lo trovo come where condition e il risultato della query sara' sempre
	 					//di zero righe dal momento che come altra condizione ho che la data fine sia null.
	 					
	 					rubricaList = rubricaManager.getPartecipantiList(
	 							denominazione,
	 							codiceFiscale.toUpperCase(),
	 							startRow,
	 							configuration.getMaxElementiPerPagina()
	 							);
	 					
	 					logger.debug("numero righe:::"+rubricaList.getRowsCount());
	 					
 						request.setAttribute(ParametriServlet.TABLEBEAN, rubricaList);
 						request.setAttribute(ParametriServlet.START_ROW, new Integer(startRow) );
 						request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getELEMENTI_VISUALIZZATI());
 						
	 					if ( rubricaList.size() == 0 ) {
	 						sendMessage(request, response, Messaggi.SIMOG_RIC_001, ParametriServletRubrica.JSP_GESTIONE_RUBRICA_RESPONSABILI);
	 					} else {
	 						forward(ParametriServletRubrica.JSP_GESTIONE_RUBRICA_RESPONSABILI, request, response);
	 					}
	 				} catch ( Exception sqle ) {
	 					sendError(request, response, sqle.getMessage(), ParametriServletRubrica.JSP_GESTIONE_RUBRICA_RESPONSABILI, sqle);
	 				} finally {
	 					closeConnection(request.getSession().getId(),getClass().getName());
	 				}
	 			} else {
	 				sendError(request, response, SIMOG_LOGIN_004, JSP_ERRORE );				
	 			}
	 		} else {
	 			sendError(request, response, SIMOG_LOGIN_003, JSP_ERRORE );
	 		}
	 	}
	 	
	 	
	 	/*************************************************************************************
	 	 * metodo per la visualizzazione dell'elenco dei partecipanti presenti in rubrica
	 	 * @param request HttpServletRequest
	 	 * @param response HttpServletResponse
	 	 * @throws ServletException
	 	 * @throws IOException
	 	 */
	 	public void performView(HttpServletRequest request, HttpServletResponse response)
	 	throws ServletException, IOException {
	 		Connection currentActiveConnection = null;
	 		HttpSession currentActiveSession = request.getSession();
	 		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
	 		if ( checkSession(request) ) {
	 			if ( currentUser.isRUP() || currentUser.isCS()) {				
	 				visualizzaListaParametriValori(request, response);
	 				
	 				int startRow = 0;
	 				String action = request.getParameter(ParametriServlet.ACTION_GET_LIST);
	 				logger.debug("action:::"+action);
	 				boolean actionNeeded = action != null;

	 				if ( actionNeeded ) {
	 					
	 					String startRowS = request.getParameter(ParametriServlet.START_ROW);
	 					
	 					startRow = Integer.parseInt(startRowS);
	 					if ( action.equalsIgnoreCase(ParametriServlet.REGRESS) ) {
	 						startRow = startRow - configuration.getMaxElementiPerPagina();
	 						
	 					} else {
	 						startRow = startRow + configuration.getMaxElementiPerPagina();
	 						
	 					}
	 				}
	 				
	 				String denominazione ;
	 				String codiceFiscale ;
	 				
	 				if(request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME )!=null)
	 					denominazione=request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME);
	 				else denominazione = "%&";

	 				if(request.getParameter(ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE)!=null)
	 					codiceFiscale=request.getParameter(ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE).toUpperCase();
	 				else   codiceFiscale = "";

	                if ( request.getParameter("paginazioneResponsabili") != null ) {
	 				
	 				
	 				currentActiveSession.setAttribute(ParametriServletRubrica.FIELD_NAME_COGNOME, denominazione);
	 				currentActiveSession.setAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE, codiceFiscale);
	 				}
	 				
	 				if ( currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_COGNOME) != null )
	 					denominazione = ( (String) currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_COGNOME) );
	 				if (currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE) != null )
	 					codiceFiscale = (String) currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE);
	 					 				
	 				try {
	 					
		 					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
	
		 					TableBean rubricaList = null;
		 					RubricaResponsabiliManager rubricaManager = new RubricaResponsabiliManager(currentActiveConnection, logger);
		 					logger.debug("Esecuzione GaraList");
		 					logger.debug("denominazione "+denominazione);
		 					logger.debug("codice "+codiceFiscale);
		 					
		 					// passo denominazione e codice come stringa vuota poiche' il codice e' valorizzato sicuramente
		 					// e quindi nella select lo trovo come where condition e il risultato della query sar�a' sempre
		 					// di zero righe dal momento che come altra condizione ho che la data fine sia null.
		 				    
		 					
		 					
			 					rubricaList = rubricaManager.getPartecipantiList(
			 							denominazione,
			 							codiceFiscale.toUpperCase(),
			 							startRow,
			 							configuration.getMaxElementiPerPagina()
			 							);
		 					
			 					logger.debug("numero righe:::"+rubricaList.getRowsCount());
			 					request.setAttribute(ParametriServlet.TABLEBEAN, rubricaList);
		 						request.setAttribute(ParametriServlet.START_ROW, new Integer(startRow) );
		 						request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getELEMENTI_VISUALIZZATI());
		 					
		 					if ( rubricaList.size() == 0 ) {
		 						sendMessage(request, response, Messaggi.SIMOG_RIC_001, ParametriServletRubrica.JSP_GESTIONE_RUBRICA_RESPONSABILI);
		 					} else {	 						
		 						forward(ParametriServletRubrica.JSP_GESTIONE_RUBRICA_RESPONSABILI, request, response);
		 					}
	 					
	 				} catch ( Exception sqle ) {
	 					sendError(request, response, sqle.getMessage(), ParametriServletRubrica.JSP_GESTIONE_RUBRICA_RESPONSABILI, sqle);
	 				} finally {
	 					closeConnection(request.getSession().getId(),getClass().getName());
	 				}
	 			} else {
	 				sendError(request, response, SIMOG_LOGIN_004, JSP_ERRORE );				
	 			}
	 		} else {
	 			sendError(request, response, SIMOG_LOGIN_003, JSP_ERRORE );
	 		}
	 	}
	 	
	 	
	 	/*****************************************************************************************
	 	 * metodo per la visualizzazione dell'elenco dei partecipanti presenti in rubrica
	 	 * @param request HttpServletRequest
	 	 * @param response HttpServletResponse
	 	 * @throws ServletException
	 	 * @throws IOException
	 	 */
	 	public void performViewDetail(HttpServletRequest request, HttpServletResponse response)
	 	throws ServletException, IOException {
	 		Connection currentActiveConnection = null;
	 		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
	 		if ( checkSession(request) ) {
	 			if ( currentUser.isRUP() || currentUser.isCS()) {	
	 				
	 				visualizzaListaParametriValori(request, response);
	 				
	 				int startRow = 0;		
	 				//int id_partecipante=0;
	 				String action = request.getParameter(ParametriServlet.ACTION_GET_LIST);
	 				
	 				boolean actionNeeded = action != null;

	 				if ( actionNeeded ) {
	 					String startRowS = request.getParameter(ParametriServlet.START_ROW);
	 					startRow = Integer.parseInt(startRowS);
	 					if ( action.equalsIgnoreCase(ParametriServlet.REGRESS) ) {
	 						startRow = startRow - configuration.getMaxElementiPerPagina();
	 					} else {
	 						startRow = startRow + configuration.getMaxElementiPerPagina();
	 					}
	 				}
	 				
	 				String cognome = "";
	 				if(request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME)!=null)
	 					cognome=request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME);
	 				String codiceFiscale = "";
	 				if(request.getParameter(ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE)!=null)
	 					codiceFiscale=request.getParameter(ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE).toUpperCase();
	 				
	 				String telefono = request.getParameter(ParametriServletRubrica.FIELD_NAME_TELEFONO);
	 				String email = request.getParameter(ParametriServletRubrica.FIELD_NAME_EMAIL);
	 				String fax = request.getParameter(ParametriServletRubrica.FIELD_NAME_FAX);
	 				// nuovi
	 				String indirizzo = request.getParameter(ParametriServletRubrica.FIELD_NAME_INDIRIZZO);
	 				String cap = request.getParameter(ParametriServletRubrica.FIELD_NAME_CAP);
	 				String comuneIstat = request.getParameter(ParametriServletRubrica.FIELD_NAME_COMUNE_ISTAT);
	 				String nome = request.getParameter(ParametriServletRubrica.FIELD_NAME_NOME);
	 				
	 				String id_responsabile = request.getParameter(ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_RESPONSABILE);
	 				
	 				request.setAttribute(ParametriServletRubrica.FIELD_NAME_COGNOME, cognome );
	 				request.setAttribute(ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE, codiceFiscale.toUpperCase() );
	 				request.setAttribute(ParametriServletRubrica.FIELD_NAME_TELEFONO, telefono );
	 				request.setAttribute(ParametriServletRubrica.FIELD_NAME_FAX, fax );				
	 				request.setAttribute(ParametriServletRubrica.FIELD_NAME_EMAIL, email );

	 				//nuovi
	 				request.setAttribute(ParametriServletRubrica.FIELD_NAME_NOME, nome);
	 				request.setAttribute(ParametriServletRubrica.FIELD_NAME_INDIRIZZO, indirizzo);
	 				request.setAttribute(ParametriServletRubrica.FIELD_NAME_CAP, cap);
	 				request.setAttribute(ParametriServletRubrica.FIELD_NAME_COMUNE_ISTAT, comuneIstat);
	 				
	 				try {
	 	
	 					TableBean dettaglioPartecipante = null;		
	 					TableBean storicoResponsabili = null;
	 					
	 					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
	 									
	 					RubricaResponsabiliManager rubricaManager = new RubricaResponsabiliManager(currentActiveConnection, logger);
	 					logger.debug("Esecuzione GaraList");
	 					logger.debug("denominazione "+cognome);
	 					logger.debug("codice "+codiceFiscale);			
	 					logger.debug("id_partecipante "+id_responsabile);
	 					
	 					dettaglioPartecipante = rubricaManager.getDettaglioPartecipante(Integer.parseInt(id_responsabile),
						startRow,
						configuration.getMaxElementiPerPagina()
						);
	 					
	 					storicoResponsabili = rubricaManager.getStoricoResponsabili(
	 							Integer.parseInt(id_responsabile),
	 							startRow,
	 							configuration.getMaxElementiPerPagina()
	 							);
	 					
	 					logger.debug("numero righe:::"+dettaglioPartecipante.getRowsCount());
	 					logger.debug("numero righe:::"+storicoResponsabili.getRowsCount());
	 					
	 					
	 					
	 					if ( dettaglioPartecipante.size() == 0 ) {
	 						sendMessage(request, response, Messaggi.SIMOG_RIC_001, ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RESPONSABILI);
	 					} else {
	 						
	 						request.setAttribute(ParametriServlet.TABLEBEAN, dettaglioPartecipante);
	 						request.setAttribute(ParametriServlet.START_ROW, new Integer(startRow) );
	 						request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getELEMENTI_VISUALIZZATI());	
	 						request.setAttribute(ParametriServlet.STORICORESPONSABILE, storicoResponsabili);
	 						forward(ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RESPONSABILI, request, response);
	 					}
	 				} catch ( Exception sqle ) {
	 					sendError(request, response, sqle.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RESPONSABILI, sqle);
	 				} finally {
	 					closeConnection(request.getSession().getId(),getClass().getName());
	 				}
	 			} else {
	 				sendError(request, response, SIMOG_LOGIN_004, JSP_ERRORE );				
	 			}
	 		} else {
	 			sendError(request, response, SIMOG_LOGIN_003, JSP_ERRORE );
	 		}
	 	}
	 	
	 	/**************************************************************************************
	 	 * Il metodo si occupa della visualizzazione dei dettagli 
	 	 * del soggetto in caso ne venga modificato uno. 
	 	 * @param request HttpServletRequest
	 	 * @param response HttpServletResponse
	 	 * @throws ServletException
	 	 * @throws IOException
	 	 */
	 	public void performUpdate(HttpServletRequest request, HttpServletResponse response)
	 	throws ServletException, IOException {
	 		Connection currentActiveConnection = null;
	 		HttpSession currentActiveSession = request.getSession();
	 		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
	 		if ( checkSession(request) ) {
	 			if ( currentUser.isRUP() || currentUser.isCS()) {				
	 				visualizzaListaParametriValori(request, response);
	 				try{
	 				
		 				int startRow = 0;		
		 				//int id_partecipante=0;
		 				String action = request.getParameter(ParametriServlet.ACTION_GET_LIST);
		 				
		 				boolean actionNeeded = action != null;
	
		 				if ( actionNeeded ) {
		 					
		 					String startRowS = request.getParameter(ParametriServlet.START_ROW);
		 					startRow = Integer.parseInt(startRowS);
		 					if ( action.equalsIgnoreCase(ParametriServlet.REGRESS) ) {
		 						startRow = startRow - configuration.getMaxElementiPerPagina();
		 					} else {
		 						startRow = startRow + configuration.getMaxElementiPerPagina();
		 					}
		 				}
		 				
		 				String denominazione = "";
		 				if(request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME)!=null)
		 					denominazione=request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME);
		 				String codiceFiscale = "";
		 				
		 				if(request.getParameter(ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE)!=null)
		 					codiceFiscale=request.getParameter(ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE).toUpperCase();		 					
		 				currentActiveSession.setAttribute(ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE,codiceFiscale.toUpperCase());
		 				String dataFineSoggetto = request.getParameter(ParametriServletRubrica.FIELD_NAME_DATA_FINE_SOGGETTO);
							
		 				String dataInizioSoggetto = request.getParameter(ParametriServletRubrica.FIELD_NAME_DATA_INIZIO_SOGGETTO);
						
		 				String cfResponsabile = request.getParameter(ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE);
		 				String nome = request.getParameter(ParametriServletRubrica.FIELD_NAME_NOME);
		 				String cognome = request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME);
		 				String email = request.getParameter(ParametriServletRubrica.FIELD_NAME_EMAIL);
		 				String fax = request.getParameter(ParametriServletRubrica.FIELD_NAME_FAX);
		 				String telefono = request.getParameter(ParametriServletRubrica.FIELD_NAME_TELEFONO);
		 				// nuovi
		 				String indirizzo = request.getParameter(ParametriServletRubrica.FIELD_NAME_INDIRIZZO);
		 				String cap = request.getParameter(ParametriServletRubrica.FIELD_NAME_CAP);
		 				String comuneIstat = request.getParameter(ParametriServletRubrica.FIELD_NAME_COMUNE_ISTAT);
		 				
		 				int idResponsabile = Integer.parseInt( request.getParameter(ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_RESPONSABILE).toString());
		 				java.sql.Timestamp currentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		 				
		 				dataFineSoggetto = PageHelper.formatDate(currentDate);
		 				
		 				RubricaResponsabili rubrica = new RubricaResponsabili();
		 				rubrica.setCodice_fiscale_responsabile(cfResponsabile);
		 				rubrica.setData_fine_res(dataFineSoggetto);				
		 				rubrica.setCognome(cognome);
		 				rubrica.setNome(nome);
		 				rubrica.setEmail(email);
		 				rubrica.setFax(fax);
		 				rubrica.setTelefono(telefono);
		 				rubrica.setId_soggetto_responsabile(idResponsabile);
		 				rubrica.setData_inizio_res(dataInizioSoggetto);
		 				//nuovi
		 				rubrica.setIndirizzo(indirizzo);
		 				rubrica.setCap(cap);
		 				rubrica.setComuneIstat(comuneIstat);
		 					 				
		 				currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					
		 		
		 				List<RubricaResponsabili> rubricaArray = new ArrayList<RubricaResponsabili>();
		 				rubricaArray.add(rubrica);
		 				
		 				//saBean.setResponsabiliXML(rubricaArray);
		 				SimogValidator validator = ValidatorFactory.getValidator(ParametriServletRubrica.TAB_RUBRICA, currentActiveConnection, logger);
		 						 				
		 				int modificato = Integer.parseInt((String)request.getParameter("ModificaResponsabile"));
		 				if (modificato == 0 && (rubrica.getComuneIstat() == null || rubrica.getComuneIstat().equals("")) ) {
		 					validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RUBRICA_007);
		 					request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
		 					forward("rubricaResponsabili?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+"=viewDetail&id_partecipante="+rubrica.getId_soggetto_responsabile(), request, response);
		 					return;
		 				}
		 			
		 				if(validator.valida(rubricaArray, PSBD.TAB_RUBRICA_RESPONSABILI)){
							RubricaResponsabiliManager rubricaManager = new RubricaResponsabiliManager(currentActiveConnection, logger);
							rubricaManager.cancellaPartecipante(rubrica);
						
							boolean updateFlag=false;
							
							
						
							if(request.getParameter(ParametriServletRubrica.OPERAZIONE)!=null && "Modifica".equals(request.getParameter(ParametriServletRubrica.OPERAZIONE))){
								updateFlag=true;
							}
							
							rubricaManager.insertPartecipante(rubrica,updateFlag);
							//LogBloccoDatiManager.loggingINSERT(currentActiveConnection,logger,currentUser.getLogin(),"SOGGETTI PARTECIPANTI",idPartecipante);
							
							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RUBRICA_005);
							request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
							//forward(ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, request, response);
							//forward("rubrica?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+"=view", request, response);
							logger.debug("responsabile modificato");
							forward("rubricaResponsabili?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+"=viewDetail&id_partecipante="+rubrica.getId_soggetto_responsabile(), request, response);
							return;
		 				}
		 				request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
		 				forward("rubricaResponsabili?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+"=viewDetail&id_partecipante="+rubrica.getId_soggetto_responsabile(), request, response);
					
	 				}catch(SQLException sqle){
	 					sendError(request, response, sqle.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RESPONSABILI, sqle);
	 				}catch(ClassNotFoundException cnfe){		
	 					sendError(request, response, cnfe.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RESPONSABILI, cnfe);
	 				}catch(Exception e){		
	 					sendError(request, response, e.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RESPONSABILI, e);
	 				}finally {
						//rollbackOrcommit(currentActiveConnection);
						closeConnection(request.getSession().getId(),getClass().getName());
					}	
		
	 			}else {
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				}
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			}
	 		
	 	}
	 	
		/***********************************************************************************
		 * Effettua un forward alla rubrica di dettaglio Responsabili
		 * @param request HttpServletRequest
		 * @param response HttpServletResponse
		 * @throws ServletException
		 * @throws IOException
		 */
		public void performInsert(HttpServletRequest request, HttpServletResponse response)
	 	throws ServletException, IOException {
			Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
	 		
	 		if ( checkSession(request) ) {
	 			if ( currentUser.isRUP()) {
	 				visualizzaListaParametriValori(request, response);
	 				try{

	 					forward(ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RESPONSABILI, request, response);
						return;
	 				}catch(Exception e){		
	 					sendError(request, response, e.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RESPONSABILI, e);
	 				}			
	 			}else {
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				}
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			} 		
	 	}
		
		/*********************************************************************************
		 * Effettua il salvataggio di elementi in rubrica Responsabili
		 * @param request HttpServletRequest
		 * @param response HttpServletResponse
		 * @throws ServletException
		 * @throws IOException
		 */
		public void performSave(HttpServletRequest request, HttpServletResponse response)
	 	throws ServletException, IOException {

			Connection currentActiveConnection = null;
			Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
	 		if ( checkSession(request) ) {
	 			if ( currentUser.isRUP() || currentUser.isCS()) {
	 				visualizzaListaParametriValori(request, response);
	 				try{
		 				int startRow = 0;		
		 				//int id_partecipante=0;
		 				String action = request.getParameter(ParametriServlet.ACTION_GET_LIST);
		 				
		 				boolean actionNeeded = action != null;
	
		 				if ( actionNeeded ) {
		 					
		 					String startRowS = request.getParameter(ParametriServlet.START_ROW);
		 					startRow = Integer.parseInt(startRowS);
		 					if ( action.equalsIgnoreCase(ParametriServlet.REGRESS) ) {
		 						startRow = startRow - configuration.getMaxElementiPerPagina();
		 					} else {
		 						startRow = startRow + configuration.getMaxElementiPerPagina();
		 					}
		 				}
		 				
		 				RubricaResponsabili rubrica = new RubricaResponsabili();
		 			
		 			
		 				String cfRappresentante = request.getParameter(ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE).replaceAll("\\s+","");		 				
		 				String nome = request.getParameter(ParametriServletRubrica.FIELD_NAME_NOME);
		 				String cognome = request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME);
		 				String email = request.getParameter(ParametriServletRubrica.FIELD_NAME_EMAIL);
		 				String fax = request.getParameter(ParametriServletRubrica.FIELD_NAME_FAX);
		 				String telefono = request.getParameter(ParametriServletRubrica.FIELD_NAME_TELEFONO);
		 						 				
		 				//nuovi
		 				String indirizzo = request.getParameter(ParametriServletRubrica.FIELD_NAME_INDIRIZZO);
		 				String cap = request.getParameter(ParametriServletRubrica.FIELD_NAME_CAP);
		 				String comuneIstat = request.getParameter(ParametriServletRubrica.FIELD_NAME_COMUNE_ISTAT);
		 				
		 				String isEstero = request.getParameter(ParametriServlet.FLAG_ESTERO);
		 				
		 				rubrica.setCodice_fiscale_responsabile(cfRappresentante);
		 				//rubrica.setId_soggetto_partecipante(Integer.parseInt(idPartecipante));
		 				rubrica.setNome(nome);
		 				rubrica.setCognome(cognome);
		 				rubrica.setEmail(email);
		 				rubrica.setFax(fax);
		 				rubrica.setTelefono(telefono);
		 				
		 				rubrica.setIndirizzo(indirizzo);
		 				rubrica.setCap(cap);
		 				rubrica.setComuneIstat(comuneIstat);
		 				
		 				rubrica.setIsEstero(isEstero);
		 				
		 				currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
		 				
		 				List<RubricaResponsabili> rubricaArray = new ArrayList<RubricaResponsabili>();
		 				rubricaArray.add(rubrica);
		 				
		 				//saBean.setResponsabiliXML(rubricaArray);
		 				SimogValidator validator = ValidatorFactory.getValidator(ParametriServletRubrica.TAB_RUBRICA, currentActiveConnection, logger);
		 				String actionTarget = "=view";
		 				//Costanti.FLAG_VALORE_NO.equals(request.getParameter(ParametriServlet.FLAG_ESTERO))
		 				if(validator.valida(rubricaArray, PSBD.TAB_RUBRICA_RESPONSABILI)){
							RubricaResponsabiliManager rubricaManager = new RubricaResponsabiliManager(currentActiveConnection, logger);
							
							if(rubricaManager.checkCF(rubrica.getCodice_fiscale_responsabile())){
								rubricaManager.insertPartecipante(rubrica,false);
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RUBRICA_004);
							
							}else{
								actionTarget = "=Aggiungi alla rubrica";
								validator.getEccezioni().addValidationErr(Messaggi.SIMOG_RUBRICA_003);
							
							}
							request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
							forward("rubricaResponsabili?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+actionTarget, request, response);
							return;
		 				}else {
		 					request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
		 					forward(ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RESPONSABILI + "?operazione=Aggiungi alla rubrica", request, response);
		 				}
						
	 				}catch(SQLException sqle){
	 					sendError(request, response, sqle.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RESPONSABILI, sqle);
	 				}catch(Exception e){		
	 					sendError(request, response, e.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RESPONSABILI, e);
	 				}finally {
						//rollbackOrcommit(currentActiveConnection);
						closeConnection(request.getSession().getId(),getClass().getName());
					}	
		
	 			}else {
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				}
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			}
	 		
	 	}
		
		/*********************************************************************************
		 * Effettua la validazione di elementi in rubrica Responsabili
		 * @param request HttpServletRequest
		 * @param response HttpServletResponse
		 * @throws ServletException
		 * @throws IOException
		 */
		public void performValida(HttpServletRequest request, HttpServletResponse response)
	 	throws ServletException, IOException {
			Connection currentActiveConnection = null;
			Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
	 		if ( checkSession(request) ) {
	 			if ( currentUser.isRUP() || currentUser.isCS()) {				
	 				visualizzaListaParametriValori(request, response);
	 				try{
	 					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
	 					
	 					TableBean dettaglioPartecipante = null;		
							
	 					RubricaResponsabiliManager rubricaManager = new RubricaResponsabiliManager(currentActiveConnection, logger);

		 				String id_responsabile = request.getParameter("id");
		 				String data = request.getParameter("data");
		 				String elem = request.getParameter("elem");
		 				String tab = request.getParameter(PSBD.TAB);

		 				request.setAttribute("id", id_responsabile );
		 				request.setAttribute("data", data );
		 				request.setAttribute("elem", elem );
		 				request.setAttribute(PSBD.TAB, tab );
		 				
	 					dettaglioPartecipante = rubricaManager.getDettaglioPartecipante(Integer.parseInt(id_responsabile),
	 							0,	configuration.getMaxElementiPerPagina()	);
	 						 					
 						request.setAttribute(ParametriServlet.TABLEBEAN, dettaglioPartecipante);
 						
		 				int startRow = 0;		
		 				//int id_partecipante=0;
		 				String action = request.getParameter(ParametriServlet.ACTION_GET_LIST);
		 				
		 				boolean actionNeeded = action != null;
	
		 				if ( actionNeeded ) {
		 					
		 					String startRowS = request.getParameter(ParametriServlet.START_ROW);
		 					startRow = Integer.parseInt(startRowS);
		 					if ( action.equalsIgnoreCase(ParametriServlet.REGRESS) ) {
		 						startRow = startRow - configuration.getMaxElementiPerPagina();
		 					} else {
		 						startRow = startRow + configuration.getMaxElementiPerPagina();
		 					}
		 				}
		 				
		 				RubricaResponsabili rubrica = new RubricaResponsabili();
		 			
		 			
		 				String cfRappresentante = request.getParameter(ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE);		 				
		 				String nome = request.getParameter(ParametriServletRubrica.FIELD_NAME_NOME);
		 				String cognome = request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME);
		 				String email = request.getParameter(ParametriServletRubrica.FIELD_NAME_EMAIL);
		 				String fax = request.getParameter(ParametriServletRubrica.FIELD_NAME_FAX);
		 				String telefono = request.getParameter(ParametriServletRubrica.FIELD_NAME_TELEFONO);
		 						 				
		 				//nuovi
		 				String indirizzo = request.getParameter(ParametriServletRubrica.FIELD_NAME_INDIRIZZO);
		 				String cap = request.getParameter(ParametriServletRubrica.FIELD_NAME_CAP);
		 				String comuneIstat = request.getParameter(ParametriServletRubrica.FIELD_NAME_COMUNE_ISTAT);
		 				

		 				rubrica.setCodice_fiscale_responsabile(cfRappresentante);
		 				//rubrica.setId_soggetto_partecipante(Integer.parseInt(idPartecipante));
		 				rubrica.setNome(nome);
		 				rubrica.setCognome(cognome);
		 				rubrica.setEmail(email);
		 				rubrica.setFax(fax);
		 				rubrica.setTelefono(telefono);
		 				
		 				rubrica.setIndirizzo(indirizzo);
		 				rubrica.setCap(cap);
		 				rubrica.setComuneIstat(comuneIstat);
		 				
		 				List<RubricaResponsabili> rubricaArray = new ArrayList<RubricaResponsabili>();
		 				rubricaArray.add(rubrica);
		 				
		 				//saBean.setResponsabiliXML(rubricaArray);
		 				SimogValidator validator = ValidatorFactory.getValidator(ParametriServletRubrica.TAB_RUBRICA, currentActiveConnection, logger);
		 				
		 				String actionTarget = "";
		 				if(validator.valida(rubricaArray, PSBD.TAB_RUBRICA_RESPONSABILI)){
		 					
		 					actionTarget= "?okVal=S";
		 					validator.getEccezioni().addValidationInfo("Dati OK, premi SALVA per memorizzare, o Annulla per ingorare le modifiche");
		 					request.setAttribute("okVal",Costanti.FLAG_VALORE_SI);

		 					request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
							forward("scheda1/" + ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RES_POPMOD+ actionTarget, request, response);
							return;
}
		 				else{
							actionTarget = "=PopUpMod";
						}

	 					request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
						forward("rubricaResponsabili?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+actionTarget, request, response);
						return;
						
	 				}catch(Exception e){		
	 					sendError(request, response, e.getMessage(), "scheda1/" +ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RES_POPMOD, e);
	 				}finally {
						closeConnection(request.getSession().getId(),getClass().getName());
					}	
		
	 			}else {
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				}
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			}
	 		
	 	}

		/*************************************************************************************
		 * Effettua la cancellazione di un elemento dalla Rubrica Responsabili 
		 * @param request HttpServletRequest
		 * @param response HttpServletResponse
		 * @throws ServletException
		 * @throws IOException
		 */
		public void performDelete(HttpServletRequest request, HttpServletResponse response)
	 	throws ServletException, IOException {
			Connection currentActiveConnection = null;
			Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
	 		if ( checkSession(request) ) {
	 			if ( currentUser.isRUP() || currentUser.isCS()) {				
	 				visualizzaListaParametriValori(request, response);
	 				try{
	 					
		 				int startRow = 0;		
		 				//int id_partecipante=0;
		 				String action = request.getParameter(ParametriServlet.ACTION_GET_LIST);
		 				
		 				boolean actionNeeded = action != null;
	
		 				if ( actionNeeded ) {
		 					
		 					String startRowS = request.getParameter(ParametriServlet.START_ROW);
		 					startRow = Integer.parseInt(startRowS);
		 					if ( action.equalsIgnoreCase(ParametriServlet.REGRESS) ) {
		 						startRow = startRow - configuration.getMaxElementiPerPagina();
		 					} else {
		 						startRow = startRow + configuration.getMaxElementiPerPagina();
		 					}
		 				}
		 				
		 				String dataInizioSoggetto = request.getParameter(ParametriServletRubrica.FIELD_NAME_DATA_INIZIO_SOGGETTO);
		 				String idSoggettoPartecipante = request.getParameter(ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_RESPONSABILE);
		 				
		 				RubricaResponsabili rubrica = new RubricaResponsabili();
		 				rubrica.setId_soggetto_responsabile(Integer.parseInt(idSoggettoPartecipante));
		 				rubrica.setData_inizio_res(dataInizioSoggetto);
		 				currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
/**
 * XXX Cancellazione Responsabile solo se non è referenziato in nessuna scheda
 */ 		 				
		 				ResponsabileManager resMan = new ResponsabileManager(currentActiveConnection, logger);
		 				List<ResponsabileBean> resReferenceList = resMan.loadByResp(Integer.parseInt(idSoggettoPartecipante));
		 				logger.debug("[CANCELLAZIONE RESPONSABILE] rowReference::"+resReferenceList.size());
		 				if(resReferenceList.size() == 0) { 

		 					RubricaResponsabiliManager rubricaManager = new RubricaResponsabiliManager(currentActiveConnection, logger);
		 					rubricaManager.cancellaPartecipante(rubrica);
		 					setMessage ( request, Messaggi.SIMOG_RUBRICA_008.replace("$1", "incaricato"));
		 					//forward(ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, request, response);
		 					//XXX UN Perchè l'operazione è viewAfterDelete invece di view? 
		 					forward("rubricaResponsabili?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+"=viewAfterDelete", request, response);
		 				}
		 				else {
		 					sendError ( request, Messaggi.SIMOG_RUBRICA_009.replace("$1", "incaricato"));
		 					//forward(ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, request, response);
		 					//forward("rubricaResponsabili?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+"=viewAfterDelete", request, response);		 					
		 					forward("rubricaResponsabili?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+"=view", request, response);
		 				}
						
	 				}catch(SQLException sqle){
	 					sqle.printStackTrace();
	 					sendError(request, response, sqle.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RESPONSABILI, sqle);
	 				}catch(ClassNotFoundException cnfe){		
	 					sendError(request, response, cnfe.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RESPONSABILI, cnfe);
	 				}catch(Exception e){		
	 					sendError(request, response, e.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RESPONSABILI, e);
	 				}finally {
						//rollbackOrcommit(currentActiveConnection);
						closeConnection(request.getSession().getId(),getClass().getName());
					}
	 			}else {
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				}
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			}
	 		
	 	}
		
		
		
	 				
}