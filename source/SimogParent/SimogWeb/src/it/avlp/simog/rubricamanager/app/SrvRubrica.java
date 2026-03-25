package it.avlp.simog.rubricamanager.app;

import it.avcp.simog.manager.paesi.PaesiManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avlp.simog.beans.Rubrica;
import it.avlp.simog.beans.RubricaResponsabili;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletInizioLavori;
import it.avlp.simog.common.servlet.ParametriServletRubrica;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.SOGGETTI_PARTECIPANTI;
import it.avlp.simog.db.generated.STATI_ESTERI;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.rubricamanager.RubricaManager;
import it.avlp.simog.rubricamanager.RubricaResponsabiliManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.Base64Coder;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.factory.ValidatorFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

/**
 * Servlet implementation class for Servlet: SrvRubrica
 *
 */
 public class SrvRubrica extends ServletBase implements ParametriServlet{
	
	 	
	 	public void doGet(HttpServletRequest request,HttpServletResponse response)
	 	throws ServletException, IOException {
	 		
	 		perform(request, response);
	 	}

	 	private static final long serialVersionUID = 1L;
	 	
	 	private boolean switchOperation(HttpServletRequest request,  String checkValue){
	 		String parameterValue = request.getParameter(ParametriServletRubrica.OPERAZIONE);
	 		return (parameterValue!=null && checkValue.equals(parameterValue));
	 	}
	 	
	 	/**
	 	 * @see ServletBase#perform(HttpServletRequest, HttpServletResponse)
	 	 */
	 	public void perform(HttpServletRequest request, HttpServletResponse response)
	 	throws ServletException, IOException {
	 		String titolo = "";
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

	 			if ( currentUser.isRUP() || currentUser.isCS()) {				
	 				visualizzaListaParametriValori(request, response);
	 				//if (switchOperation(request,"view")) { //|| switchOperation(request, "Cerca")){
	 				if (switchOperation(request,"view") || switchOperation(request,"Indietro")) { //|| switchOperation(request, "Cerca")){
	 					logger.debug("SrvRubrica - il parametro d'ingresso &egrave; parameter = view" );
	 					logger.debug("Passo nell'if VIEW");
	 					performView(request,response);
	 				}else if( switchOperation(request, "Modifica")) {
	 					logger.debug("SrvRubrica - il parametro d'ingresso &egrave; parameter = modifica *****************" );
	 					logger.debug("Passo nell'if Modifica");
	 					performUpdate(request,response);
	 				}else if( switchOperation(request, "Cerca")) {
	 					logger.debug("SrvRubrica - il parametro d'ingresso &egrave; parameter = cerca *****************" );
	 					performView(request,response);
	 				}else if (switchOperation(request, "viewDetail")){
	 					logger.debug("SrvRubrica - il parametro d'ingresso &egrave; parameter = viewDetail *****************" );
	 					performViewDetail(request, response);
	 				}else if (switchOperation(request, "Aggiungi alla rubrica")){
	 					logger.debug("SrvRubrica - il parametro d'ingresso &egrave; parameter = Aggiungi alla rubrica *****************" );
	 					performInsert(request, response);
	 				}else if (switchOperation(request, "Salva")){
	 					logger.debug("SrvRubrica - il parametro d'ingresso &egrave; parameter = Salva *****************" );
	 					performSave(request, response);
	 				}else if (switchOperation(request, "Cancella")){
	 					logger.debug("SrvRubrica - il parametro d'ingresso &egrave; parameter = Cancella *****************" );
	 					performDelete(request, response);
	 				}else if (switchOperation(request, "Aggiungi")){
	 					logger.debug("SrvRubrica - il parametro d'ingresso &egrave; parameter = Aggiungi *****************" );
	 					performInsertPopup(request, response);
	 				}else if (switchOperation(request, "Salva in rubrica")){
	 					logger.debug("SrvRubrica - il parametro d'ingresso &egrave; parameter = Salva in rubrica *****************" );
	 					performSavePopup(request, response);
	 				}else if (switchOperation(request, "Cerca in rubrica")){
	 					logger.debug("SrvRubrica - il parametro d'ingresso &egrave; parameter = Cerca in rubrica *****************" );
	 					performViewPopup(request, response);
	 				}else if (switchOperation(request, "PopUpMod")){
	 					logger.debug("SrvRubrica - il parametro d'ingresso &egrave; parameter = modpopup *****************" );
	 					performModPopup(request, response);
	 				}else if (switchOperation(request, "validaDati")){
	 					performValida(request, response);
	 				}

	 			}
	 		}else {	sendError(request, response, SIMOG_LOGIN_003, JSP_ERRORE);}
	 	}
	 	
	 	//metodo per la visualizzazione dell'elenco dei partecipanti presenti in rubrica
	 	
	 	/******************************************************************************
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
	 			if ( currentUser.isRUP()) {				
	 				visualizzaListaParametriValori(request, response);
	 				
	 				int startRow = 0;
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
	 				
	 				String denominazione ;
	 				String codiceFiscale ;
	 				String id_stato ;
	 				// ******************************************************************************
	 				
	 				if(request.getParameter(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE )!=null)
	 					denominazione=request.getParameter(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE);
	 				else denominazione = "%&";
	 				
	 				if(request.getParameter(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE)!=null)
	 					codiceFiscale=request.getParameter(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE).toUpperCase();
	 				else   codiceFiscale = "%&";	
	 					//codiceFiscale = request.getParameter("codiceFiscale");
	 				
	/*UN*/		if(request.getParameter(ParametriServletRubrica.FIELD_NAME_ID_STATO)!=null)
	 					id_stato=request.getParameter(ParametriServletRubrica.FIELD_NAME_ID_STATO).toUpperCase();	
					else id_stato = "";
	 
	 				if ( request.getParameter("paginazione") != null ) {
	 				
	 				
	 				currentActiveSession.setAttribute(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE, denominazione);
	 				currentActiveSession.setAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE, codiceFiscale);
    /*UN*/		currentActiveSession.setAttribute(ParametriServletRubrica.FIELD_NAME_ID_STATO, id_stato);
	 				}
	 				
	 				if ( currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE) != null )
	 				denominazione = ( (String) currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE) );
	 				if (currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE) != null )
	 				codiceFiscale = (String) currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE);
	/*UN*/ 		if (currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_ID_STATO) != null )
		 			id_stato = (String) currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_ID_STATO);
	 					
	 				//**********************************************************************************
	 				
	 				
	 				
	 				
	 				
	 				try {
	 					
	 					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());

	 					TableBean rubricaList = null;
	 					RubricaManager rubricaManager = new RubricaManager(currentActiveConnection, logger);
	 					logger.debug("Esecuzione getPartecipantiList");
	 					logger.debug("denominazione: "+denominazione);
	 					logger.debug("codice: "+codiceFiscale);
	 					logger.debug("id_stato: "+id_stato);
	 					rubricaList = rubricaManager.getPartecipantiList(
	 							denominazione,
	 							codiceFiscale.toUpperCase(),
	 							id_stato,
	 							startRow,
	 							configuration.getMaxElementiPerPagina()
	 							);
	 					
	 					logger.debug("numero righe:::"+rubricaList.getRowsCount());
 						request.setAttribute(ParametriServlet.START_ROW, new Integer(startRow) );
 						request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getELEMENTI_VISUALIZZATI());
 						request.setAttribute(ParametriServlet.TABLEBEAN, rubricaList);
 						
 						if ( rubricaList.size() == 0 ) {
	 						sendMessage(request, response, Messaggi.SIMOG_RIC_001, ParametriServletRubrica.JSP_GESTIONE_RUBRICA);
	 					} else {
	 						forward(ParametriServletRubrica.JSP_GESTIONE_RUBRICA, request, response);
	 						//forward("scheda1/popupRubricaOK.jsp", request, response);
	 					}
	 				} catch ( Exception sqle ) {
	 					sendError(request, response, sqle.getMessage(), ParametriServletRubrica.JSP_GESTIONE_RUBRICA, sqle);
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
	 	
	 	/************************************************************************************
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
	 				
	 				String denominazione = "";
	 				if(request.getParameter(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE)!=null)
	 					denominazione=request.getParameter(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE);
	 				String codiceFiscale = "";
	 				
	 				if(request.getParameter("codiceFiscale")!=null)
	 					codiceFiscale=request.getParameter("codiceFiscale").toUpperCase();
	 				
	 				String dataFineSoggetto = 
						request.getParameter(ParametriServletRubrica.FIELD_NAME_DATA_FINE_SOGGETTO_AAAA) 
						+ request.getParameter(ParametriServletRubrica.FIELD_NAME_DATA_FINE_SOGGETTO_MM)
						+ request.getParameter(ParametriServletRubrica.FIELD_NAME_DATA_FINE_SOGGETTO_DD);
	 				
	 				String dataInizioSoggetto = null;
	 				if(request.getParameter(ParametriServletRubrica.FIELD_NAME_DATA_INIZIO_SOGGETTO_AAAA) != null){
	 					dataInizioSoggetto =
	 					request.getParameter(ParametriServletRubrica.FIELD_NAME_DATA_INIZIO_SOGGETTO_AAAA) 
						+ request.getParameter(ParametriServletRubrica.FIELD_NAME_DATA_INIZIO_SOGGETTO_MM)
						+ request.getParameter(ParametriServletRubrica.FIELD_NAME_DATA_INIZIO_SOGGETTO_DD);
	 				}
	 				
	 				String telefono = request.getParameter(ParametriServletRubrica.FIELD_NAME_TELEFONO);
	 				String email = request.getParameter(ParametriServletRubrica.FIELD_NAME_EMAIL);
	 				String cameraCommercio = request.getParameter(ParametriServletRubrica.FIELD_NAME_CAMERA_COMMERCIO);
	 				String cfRappresentante = request.getParameter(ParametriServletRubrica.FIELD_NAME_CF_RAPPRESENTANTE);
	 				//String id_partecipante = request.getParameter(ParametriServlet.FIELD_ID_SOGGETTO_PARTECIPANTE);
	 				String id_partecipante = request.getParameter("id_partecipante");
	 				
 					request.setAttribute("denominazione", denominazione );
 					request.setAttribute("codiceFiscale", codiceFiscale.toUpperCase() );
 					request.setAttribute("dataInizioSoggetto", dataInizioSoggetto );
 					
 					request.setAttribute("dataFineSoggetto", dataFineSoggetto );
 					request.setAttribute("telefono", telefono );
 					request.setAttribute("email", email );
 					request.setAttribute("cameraCommercio", cameraCommercio );
 					request.setAttribute("cfRappresentante", cfRappresentante );
 					
	 				

	 				try {

	 					TableBean dettaglioPartecipante = null;					
	 					TableBean storicoPartecipante = null;					
	 					
	 					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
	 									
	 					RubricaManager rubricaManager = new RubricaManager(currentActiveConnection, logger);
	 					logger.debug("Esecuzione GaraList");
	 					logger.debug("denominazione "+denominazione);
	 					logger.debug("codice "+codiceFiscale);			
	 					logger.debug("id_partecipante "+id_partecipante);
	 					
	 					dettaglioPartecipante=rubricaManager.getDettaglioPartecipante(Integer.parseInt(id_partecipante),
						startRow,
						configuration.getMaxElementiPerPagina()
						);
	 					/***/
		 				PaesiManager pm = new PaesiManager(currentActiveConnection,logger);
		 				String id_stato = dettaglioPartecipante.getRow(0).getNulledField(SOGGETTI_PARTECIPANTI.ID_STATO);
		 				if(dataInizioSoggetto == null){
		 					logger.debug("-- null -->"+dataInizioSoggetto);
		 					dataInizioSoggetto = dettaglioPartecipante.getRow(0).getNulledField(SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG);			
		 					request.setAttribute(STATI_ESTERI.TABLE_NAME, pm.loadPaese(id_stato,dataInizioSoggetto));
		 				}
		 				request.setAttribute(SOGGETTI_PARTECIPANTI.ID_STATO, id_stato);
		 				if(id_stato != null && !"".equals(id_stato))
		 					request.setAttribute(ParametriServlet.FLAG_ESTERO, Costanti.FLAG_VALORE_SI);
		 				else
		 					request.setAttribute(ParametriServlet.FLAG_ESTERO, Costanti.FLAG_VALORE_NO);
		 				request.setAttribute("disabled","disabled");
		 				/***/
	 					// Tabella dello storico Partecipante
	 					storicoPartecipante=rubricaManager.getStoricoPartecipante(Integer.parseInt(id_partecipante),
	 							startRow,
	 							configuration.getMaxElementiPerPagina()
	 							);
	 					
	 					logger.debug("numero righe:::"+dettaglioPartecipante.getRowsCount());
	 					logger.debug("numero righe:::"+storicoPartecipante.getRowsCount());
	 					
	 					
	 					if ( dettaglioPartecipante.size() == 0 ) {
	 						sendMessage(request, response, Messaggi.SIMOG_RIC_001, ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA);
	 					} else {
	 						
	 						request.setAttribute(ParametriServlet.TABLEBEAN, dettaglioPartecipante);
	 						request.setAttribute(ParametriServlet.STORICOPARTECIPANTE, storicoPartecipante);
	 						
	 						
	 						request.setAttribute(ParametriServlet.START_ROW, new Integer(startRow) );
	 						request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getELEMENTI_VISUALIZZATI());											
	 						forward(ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, request, response);
	 					}
	 				} catch ( Exception sqle ) {
	 					sendError(request, response, sqle.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, sqle);
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
	 	
	 	/*********************************************************************************
	 	 * Metodo per l'aggiornamento dell'elenco dei partecipanti presenti in rubrica
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
		 				currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
		 				
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
		 				if(request.getParameter(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE)!=null)
		 					denominazione=request.getParameter(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE);
		 				String codiceFiscale = "";
		 				
		 				if(request.getParameter("codiceFiscale")!=null)
		 					codiceFiscale=request.getParameter("codiceFiscale").toUpperCase();
		 				currentActiveSession.setAttribute("codiceFiscale",codiceFiscale);
		 				String dataFineSoggetto = request.getParameter(ParametriServletRubrica.FIELD_NAME_DATA_FINE_SOGGETTO);
					
		 				String dataInizioSoggetto = request.getParameter(ParametriServletRubrica.FIELD_NAME_DATA_INIZIO_SOGGETTO);
						
		 				String cameraCommercio = request.getParameter(ParametriServletRubrica.FIELD_NAME_CAMERA_COMMERCIO);
		 				String cfRappresentante = request.getParameter(ParametriServletRubrica.FIELD_NAME_CF_RAPPRESENTANTE);
		 				
		 				String nome = request.getParameter(ParametriServletRubrica.FIELD_NAME_NOME);
		 				String cognome = request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME);
		 				String indirizzo = request.getParameter(ParametriServletRubrica.FIELD_NAME_INDIRIZZO);
		 				String citta = request.getParameter(ParametriServletRubrica.FIELD_NAME_CITTA);
		 				String provincia = request.getParameter(ParametriServletRubrica.FIELD_NAME_PROVINCIA);
		 				String cap = request.getParameter(ParametriServletRubrica.FIELD_NAME_CAP);
		 				String civico = request.getParameter(ParametriServletRubrica.FIELD_NAME_CIVICO);
		 				String partitaIVA = request.getParameter(ParametriServletRubrica.FIELD_NAME_PARTITA_IVA);
		 				
		 				String id_stato = request.getParameter(ParametriServletRubrica.FIELD_NAME_ID_STATO);		/*UN*/
		 				String flagEsteri = request.getParameter(ParametriServletRubrica.FIELD_NAME_FLAG_ESTERI);	/*UN*/
		 				
		 				
		 				int idPartecipante = Integer.parseInt( request.getParameter(ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_PARTECIPANTE).toString());
		 				java.sql.Timestamp currentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		 				
		 				dataFineSoggetto = PageHelper.formatDate(currentDate);
		 				
		 				Rubrica rubrica = new Rubrica();
		 				rubrica.setCamera_commercio(cameraCommercio);
		 				rubrica.setData_fine(dataFineSoggetto);				
		 				rubrica.setDenominazione(denominazione);
		 				rubrica.setCodice_fiscale(codiceFiscale.toUpperCase());
		 				rubrica.setData_inizio_sogg(dataInizioSoggetto);
		 				rubrica.setCf_rappresentante(cfRappresentante);
		 				rubrica.setId_soggetto_partecipante(idPartecipante);
		 				rubrica.setNome(nome);
		 				rubrica.setCognome(cognome);
		 				rubrica.setIndirizzo(indirizzo);
		 				rubrica.setCivico(civico);
		 				rubrica.setCap(cap);
		 				rubrica.setCitta(citta);
		 				rubrica.setProvincia(provincia);
		 				rubrica.setPartitaIva(partitaIVA);
		 				
		 				rubrica.setId_stato(id_stato);						/*UN*/
		 				rubrica.setFlagEsteri(flagEsteri);					/*UN*/
		 				
		 				//Scheda_A saBean = new Scheda_A();
		 				List<Rubrica> rubricaArray = new ArrayList<Rubrica>();
		 				rubricaArray.add(rubrica);
		 				SimogValidator validator = ValidatorFactory.getValidator(ParametriServletRubrica.TAB_RUBRICA, currentActiveConnection, logger);
		 				
		 				// LUCA verifico se i parametri sono stati modificati, se non lo sono stati invio un messaggio di informazione 
		 				
		 				logger.debug(
		 						"Luca\n\t ModificaPartecipante:"+(String)request.getParameter("ModificaPartecipante")
		 				);
		 				
		 				int modificato = Integer.parseInt((String)request.getParameter("ModificaPartecipante"));
		 				if (modificato == 0) {
		 					validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RUBRICA_007);
		 					request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
		 					forward("rubrica?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+"=viewDetail&id_partecipante="+rubrica.getId_soggetto_partecipante(), request, response);
		 					return;
		 				}
		 				// ************************************************************************************************************
		 				
		 				if(validator.valida(rubricaArray, PSBD.TAB_RUBRICA_AFFIDATARIO)){
		 		
		 				
							RubricaManager rubricaManager = new RubricaManager(currentActiveConnection, logger);
			 				
							//PP l'aggiornamento deve chiudere il vecchio, non modificarlo!!!
							//rubricaManager.modificaPartecipante(rubrica);
							rubricaManager.cancellaPartecipante(rubrica);
												
							boolean updateFlag=false;
							RubricaManager rubricaManagerNew = new RubricaManager(currentActiveConnection, logger);
							if(request.getParameter(ParametriServletRubrica.OPERAZIONE)!=null && "Modifica".equals(request.getParameter(ParametriServletRubrica.OPERAZIONE))){
								updateFlag=true;
							}
							
							rubricaManagerNew.insertPartecipante(rubrica,updateFlag);
							//LogBloccoDatiManager.loggingINSERT(currentActiveConnection,logger,currentUser.getLogin(),"SOGGETTI PARTECIPANTI",idPartecipante);
							
							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RUBRICA_001);
							request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
							logger.debug("tutto ok");
							forward("rubrica?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+"=viewDetail&id_partecipante="+rubrica.getId_soggetto_partecipante(), request, response);
							return;
		 				}
		 				
		 				request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
		 				forward("rubrica?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+"=viewDetail&id_partecipante="+rubrica.getId_soggetto_partecipante(), request, response);
						return;
	 				}catch(SQLException sqle){
	 					sendError(request, response, sqle.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, sqle);
	 				}catch(ClassNotFoundException cnfe){		
	 					sendError(request, response, cnfe.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, cnfe);
	 				}catch(Exception e){		
	 					sendError(request, response, e.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, e);
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
	 	
		/********************************************************************************
		 * Effettua l'inserimento effettuando il forward alla pagina di dettaglio rubrica
		 * @param request HttpServletRequest
		 * @param response HttpServletResponse
		 * @throws ServletException
		 * @throws IOException
		 */
		public void performInsert(HttpServletRequest request, HttpServletResponse response)
	 	throws ServletException, IOException {
			Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
	 		if ( checkSession(request) ) {
	 			if ( currentUser.isRUP() || currentUser.isCS()) {				
	 				visualizzaListaParametriValori(request, response);
	 				try{
	 					logger.debug("sono nel performInsert");
	 					/** paesi: l'id_stato qui deve essere una stringa vuota in quanto non abbiamo bisogno di preselezione
	 					 * e non abbiamo tale informazione (id_stato da soggetto pertecipante) 
	 					 * */
	 					Connection currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
	 					PaesiManager pm = new PaesiManager(currentActiveConnection,logger);
	 					Map<String,String> m = pm.loadPaesi(null);
	 					if(m == null){m = new TreeMap<String,String>();} 
	 					request.setAttribute(STATI_ESTERI.TABLE_NAME, m);
	 					//X-XX: perche'?  request.setAttribute(SOGGETTI_PARTECIPANTI.ID_STATO, "");
	 					/** end */
	 					forward(ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, request, response);
						return;
	 				}catch(Exception e){		
	 					sendError(request, response, e.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, e);
	 				}finally {
						//rollbackOrcommit(currentActiveConnection);
						// PP era commentata!
	 					closeConnection(request.getSession().getId(),getClass().getName());
					}			
	 			}else {
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				}
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			} 		
	 	}
		
		/*******************************************************************************
		 * Effettua il salvataggio degli elementi nella rubrica
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
		 				currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
		 				
	 					logger.debug("sono nel performSave");
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
		 				
		 				//String idPartecipante = request.getParameter(ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_PARTECIPANTE);
		 				String denominazione = request.getParameter(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE);
		 				String codiceFiscale = request.getParameter(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE).toUpperCase();
		 				String cameraCommercio = request.getParameter(ParametriServletRubrica.FIELD_NAME_CAMERA_COMMERCIO);
		 				String cfRappresentante = request.getParameter(ParametriServletRubrica.FIELD_NAME_CF_RAPPRESENTANTE);		 				
		 				String nome = request.getParameter(ParametriServletRubrica.FIELD_NAME_NOME);
		 				String cognome = request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME);
		 				String indirizzo = request.getParameter(ParametriServletRubrica.FIELD_NAME_INDIRIZZO);
		 				String citta = request.getParameter(ParametriServletRubrica.FIELD_NAME_CITTA);
		 				String provincia = request.getParameter(ParametriServletRubrica.FIELD_NAME_PROVINCIA);
		 				String cap = request.getParameter(ParametriServletRubrica.FIELD_NAME_CAP);
		 				String civico = request.getParameter(ParametriServletRubrica.FIELD_NAME_CIVICO);
		 				String partitaIVA = request.getParameter(ParametriServletRubrica.FIELD_NAME_PARTITA_IVA);
		 				String id_stato = request.getParameter(ParametriServletRubrica.FIELD_NAME_ID_STATO);		/*UN*/
		 				String flagEsteri = request.getParameter(ParametriServletRubrica.FIELD_NAME_FLAG_ESTERI);	/*UN*/
		 				
		 				Rubrica rubrica = new Rubrica();
		 				rubrica.setCamera_commercio(cameraCommercio);
		 				//rubrica.setData_fine(dataFineSoggetto);				
		 				rubrica.setDenominazione(denominazione);
		 				rubrica.setCodice_fiscale(codiceFiscale.toUpperCase());
		 				//rubrica.setData_inizio_sogg(dataInizioSoggetto);
		 				rubrica.setCf_rappresentante(cfRappresentante);
		 				//rubrica.setId_soggetto_partecipante(Integer.parseInt(idPartecipante));
		 				rubrica.setNome(nome);
		 				rubrica.setCognome(cognome);
		 				rubrica.setIndirizzo(indirizzo);
		 				rubrica.setCivico(civico);
		 				rubrica.setCap(cap);
		 				rubrica.setCitta(citta);
		 				rubrica.setProvincia(provincia);
		 				rubrica.setPartitaIva(partitaIVA);
		 				rubrica.setId_stato(id_stato);						/*UN*/
		 				rubrica.setFlagEsteri(flagEsteri);					/*UN*/
		 				
		 				List<Rubrica> rubricaArray = new ArrayList<Rubrica>();
		 				rubricaArray.add(rubrica);
		 				SimogValidator validator = ValidatorFactory.getValidator(ParametriServletRubrica.TAB_RUBRICA, currentActiveConnection, logger);
		 				if(validator.valida(rubricaArray, PSBD.TAB_RUBRICA_AFFIDATARIO)){
		 					
		 				
		 					RubricaManager rubricaManager = new RubricaManager(currentActiveConnection, logger);
						
						
		 					if(rubricaManager.checkCF(rubrica.getCodice_fiscale(),rubrica.getId_stato())){
								rubricaManager.insertPartecipante(rubrica,false);
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RUBRICA_002);
								request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
								//imposto la ricerca per far uscire solo quello appena inserito
				 				request.getSession().setAttribute(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE, "");
				 				request.getSession().setAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE, codiceFiscale);
				 				request.getSession().setAttribute(ParametriServletRubrica.FIELD_NAME_ID_STATO, id_stato);

								forward("rubrica?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+"=view", request, response);
								return;
		 					}else{
		 						validator.getEccezioni().addValidationErr(Messaggi.SIMOG_RUBRICA_003);
		 						//request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
				 				//request.setAttribute(ParametriServletRubrica.FIELD_NAME_ID_STATO, id_stato);
				 				//request.setAttribute(ParametriServlet.FLAG_ESTERO, request.getParameter(ParametriServlet.FLAG_ESTERO));
		 						//forward("rubrica?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+"=Aggiungi alla rubrica", request, response);
		 					}
		 					
		 					
		 				}

		 				request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
		 				request.setAttribute(ParametriServlet.FLAG_ESTERO, flagEsteri);
		 				request.setAttribute(ParametriServletRubrica.FIELD_NAME_ID_STATO, id_stato);
		 				
						forward("rubrica?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+"=Aggiungi alla rubrica", request, response);

	 				}catch(SQLException sqle){
	 					sendError(request, response, sqle.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, sqle);
	 				}catch(Exception e){		
	 					sendError(request, response, e.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, e);
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
		 * Effettua la cancellazione di elementi dalla rubrica
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
		 				currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());

	 					logger.debug("sono nel performDelete");
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
		 				String idSoggettoPartecipante = request.getParameter(ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_PARTECIPANTE);
		 				
		 				Rubrica rubrica = new Rubrica();
		 				rubrica.setId_soggetto_partecipante(Integer.parseInt(idSoggettoPartecipante));
		 				rubrica.setData_inizio_sogg(dataInizioSoggetto);
/**
 * XXX Cancellazione Operatore solo se non e' referenziato in nessuna scheda
 */			 				
		 				AggiudicatarioManager aggMan = new AggiudicatarioManager(currentActiveConnection, logger);
		 				List<AggiudicatarioBean> soggReferenceList = aggMan.loadBySogg(Integer.parseInt(idSoggettoPartecipante));
		 				logger.debug(" [CANCELLAZIONE OPERATORE] referenze: "+soggReferenceList.size());
		 				if(soggReferenceList.size() == 0 ){

		 					RubricaManager rubricaManager = new RubricaManager(currentActiveConnection, logger);
		 					rubricaManager.cancellaPartecipante(rubrica);
		 					setMessage ( request, Messaggi.SIMOG_RUBRICA_008.replace("$1", "operatore"));
							//forward(ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, request, response);
		 					forward("rubrica?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+"=view", request, response);
		 				} 
		 				else {
							sendError ( request, Messaggi.SIMOG_RUBRICA_009.replace("$1", "operatore"));
							//forward(ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, request, response);
							forward("rubrica?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+"=view", request, response);
		 				}
					
	 				}catch(SQLException sqle){
	 					sendError(request, response, sqle.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, sqle);
	 				}catch(ClassNotFoundException cnfe){		
	 					sendError(request, response, cnfe.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, cnfe);
	 				}catch(Exception e){		
	 					sendError(request, response, e.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA, e);
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
		
		/****************************************************************************************
		 * Effettua il forward alla jsp di dettaglio rubrica popup  
		 * @param request HttpServletRequest
		 * @param response HttpServletResponse
		 * @throws ServletException
		 * @throws IOException
		 */
		public void performInsertPopup(HttpServletRequest request, HttpServletResponse response)
	 	throws ServletException, IOException {
			Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
			//logger.debug(request.getParameter("parametri"));
			request.setAttribute("parametri",request.getParameter("parametri"));
	 		if ( checkSession(request) ) {
	 			if ( currentUser.isRUP() || currentUser.isCS()) {				
	 				visualizzaListaParametriValori(request, response);
	 				try{
	 					logger.debug("sono nel performInsertPopup");

	 					Connection currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
	 					
	 					//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
	 					//if(request.getParameter(PSBD.TAB).equals(PSBD.TAB_RESPONSABILE_PROCEDIMENTO) || request.getParameter(PSBD.TAB).equals(PSBD.TAB_PRESTAZIONI)){
	 					if(request.getParameter(PSBD.TAB).equals(PSBD.TAB_RESPONSABILE_PROCEDIMENTO) || (request.getParameter(PSBD.TAB).equals(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_INCARICATI))){
	 						forward("scheda1/"+ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RES_POPUP, request, response);
	 					//}else if (request.getParameter(PSBD.TAB).equals("TabAffidatario")){
	 					//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
	 					//}else if (request.getParameter(PSBD.TAB).equals(PSBD.TAB_AFFIDATARIO) || request.getParameter(PSBD.TAB).equals(ParametriServletInizioLavori.TAB_POSIZIONE_AGGIUDICATARI)
	 				    }else if (request.getParameter(PSBD.TAB).equals(PSBD.TAB_AFFIDATARIO) || (request.getParameter(PSBD.TAB).equals(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_OPERATORE_ECONOMICO)) ||
	 						request.getParameter(PSBD.TAB).equals(ParametriServletInizioLavori.TAB_POSIZIONE_AGGIUDICATARI) || request.getParameter(PSBD.TAB).equals(PSBD.TAB_DITTA_AUSILIARIA) || request.getParameter(PSBD.TAB).equals(PSBD.TAB_DITTA_RAGGRUPPAMENTO)){ 						
	 						/** paesi: dato che qui si tratta di aggiungere e non modificare l'id_stato deve essere a stringa vuota
	 						 * se messa a null puo causare problemi
	 						 *  */
		 					PaesiManager pm = new PaesiManager(currentActiveConnection,logger);
		 					Map<String,String> m = pm.loadPaesi(null);
		 					if(m == null){m = new TreeMap<String,String>();} 
		 					request.setAttribute(STATI_ESTERI.TABLE_NAME, m);
		 					/** end */
	 						forward("scheda1/"+ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_POPUP, request, response);
	 					}
	 				}catch(Exception e){		
	 					sendError(request, response, e.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_POPUP, e);
	 				}finally {
						//rollbackOrcommit(currentActiveConnection);
						// PP era commentata!
	 					closeConnection(request.getSession().getId(),getClass().getName());
					}			
	 			}else {
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				}
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			} 		
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
	 									
	 					RubricaManager rubricaManager = new RubricaManager(currentActiveConnection, logger);
		 				PaesiManager pm = new PaesiManager(currentActiveConnection,logger);

		 				String id_partecipante = request.getParameter("id");
		 				String data = request.getParameter("data");
		 				String elem = request.getParameter("elem");

		 				request.setAttribute("id", id_partecipante );
		 				request.setAttribute("data", data );
		 				request.setAttribute("elem", elem );
		 				
		 				if (elem != null  && !"".equals(elem) && !"*".equals(elem)){
		 					// riprendo i dati della sezione
			    			String [] stk = Base64Coder.decodeString(elem).split(PSBD.SEP_VARANAG_S,-1);
		 					int i = 0;
		 					
		 					dettaglioPartecipante = new TableBean();
		 					
		 					TableBeanRow row = new TableBeanRow(dettaglioPartecipante);
		 					
							row.addFieldValue(SOGGETTI_PARTECIPANTI.ID_SOGGETTO_PARTECIPANTE, stk[i++]);
							row.addFieldValue(SOGGETTI_PARTECIPANTI.CODICE_FISCALE, stk[i++]);
							row.addFieldValue(SOGGETTI_PARTECIPANTI.DENOMINAZIONE, stk[i++]);
							row.addFieldValue(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO, stk[i++]);
							row.addFieldValue(SOGGETTI_PARTECIPANTI.PARTITA_IVA, stk[i++]);
							row.addFieldValue(SOGGETTI_PARTECIPANTI.INDIRIZZO, stk[i++]);
							row.addFieldValue(SOGGETTI_PARTECIPANTI.CIVICO, stk[i++]);
							row.addFieldValue(SOGGETTI_PARTECIPANTI.CITTA, stk[i++]);
							row.addFieldValue(SOGGETTI_PARTECIPANTI.PROVINCIA, stk[i++]);						 					
							row.addFieldValue(SOGGETTI_PARTECIPANTI.CAP, stk[i++]);
							row.addFieldValue(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE, stk[i++]);
							row.addFieldValue(SOGGETTI_PARTECIPANTI.COGNOME, stk[i++]);
							row.addFieldValue(SOGGETTI_PARTECIPANTI.NOME, stk[i++]);
							row.addFieldValue(SOGGETTI_PARTECIPANTI.ID_STATO, stk[i++]);
							row.addFieldValue(SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG, data);

							dettaglioPartecipante.addRow(row);
		 				}
		 				else{
		 					// lettura dal db 	 					
		 					dettaglioPartecipante=rubricaManager.getDettaglioPartecipante(Integer.parseInt(id_partecipante),
		 							0, configuration.getMaxElementiPerPagina()
							);
		 				}

		 				String id_stato = dettaglioPartecipante.getRow(0).getNulledField(SOGGETTI_PARTECIPANTI.ID_STATO);
		 				String dataInizioSoggetto = dettaglioPartecipante.getRow(0).getNulledField(SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG);			
		 				request.setAttribute(STATI_ESTERI.TABLE_NAME, pm.loadPaese(id_stato,dataInizioSoggetto));
		 				
		 				request.setAttribute(SOGGETTI_PARTECIPANTI.ID_STATO, id_stato);
		 				if(id_stato != null && !"".equals(id_stato))
		 					request.setAttribute(ParametriServlet.FLAG_ESTERO, Costanti.FLAG_VALORE_SI);
		 				else
		 					request.setAttribute(ParametriServlet.FLAG_ESTERO, Costanti.FLAG_VALORE_NO);			 				

	 					if ( dettaglioPartecipante.size() == 0 ) {
	 						sendMessage(request, response, Messaggi.SIMOG_RIC_001, "scheda1/"+ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RES_POPMODOE);
	 					} else {
	 						request.setAttribute(ParametriServlet.TABLEBEAN, dettaglioPartecipante);
	 						forward("scheda1/"+ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RES_POPMODOE, request, response);
	 					}
	 					}catch(Exception e){		
	 					sendError(request, response, e.getMessage(), "scheda1/"+ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RES_POPMODOE, e);
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

		/*****************************************************************************************
		 * Il metodo viene eseguito sia per Responsabili che per Affidatari. Preleva i dati del soggetto, 
		 * se questo &egrave; presente in rubrica effettua una ricerca altrimenti lo aggiunge. 
		 * 
		 * @param request HttpServletRequest
		 * @param response HttpServletResponse
		 * @throws ServletException
		 * @throws IOException
		 */
		public void performSavePopup(HttpServletRequest request, HttpServletResponse response)
	 	throws ServletException, IOException {
			Connection currentActiveConnection = null;
			Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
	 		if ( checkSession(request) ) {
	 			
	 			if ( currentUser.isRUP()) {				
	 				visualizzaListaParametriValori(request, response);
	 				//Scheda_A saBean = new Scheda_A();
	 				
	 				//if(request.getParameter(PSBD.TAB).equals("TabResponsabileProcedimento")){
	 				if(request.getParameter(PSBD.TAB).equals(PSBD.TAB_RESPONSABILE_PROCEDIMENTO)
	 						  //GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
	 						  //|| request.getParameter(PSBD.TAB).equals(PSBD.TAB_PRESTAZIONI)
	 						  ||(request.getParameter(PSBD.TAB).equals(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_INCARICATI))
	 						// PP errore!   || request.getParameter(PSBD.TAB).equals(PSBD.TAB_DITTA_AUSILIARIA)
	 						// PP errore!  || request.getParameter(PSBD.TAB).equals(PSBD.TAB_DITTA_RAGGRUPPAMENTO)
	 				){
	 					try{
		 					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());

		 					String codFiscRes = request.getParameter(ParametriServletRubrica.FIELD_NAME_CF_RESPONSABILE);
		 					String cognomeRes = request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME);
		 					String nomeRes = request.getParameter(ParametriServletRubrica.FIELD_NAME_NOME);
		 					String telefonoRes = request.getParameter(ParametriServletRubrica.FIELD_NAME_TELEFONO);
		 					String faxRes = request.getParameter(ParametriServletRubrica.FIELD_NAME_FAX);
		 					String emailRes = request.getParameter(ParametriServletRubrica.FIELD_NAME_EMAIL);
		 					String indirizzo = request.getParameter(ParametriServletRubrica.FIELD_NAME_INDIRIZZO);
			 				String cap = request.getParameter(ParametriServletRubrica.FIELD_NAME_CAP);
			 				String comuneIstat = request.getParameter(ParametriServletRubrica.FIELD_NAME_COMUNE_ISTAT);
			 				
			 			
		 					RubricaResponsabili rubricaRes = new RubricaResponsabili();
		 					rubricaRes.setCodice_fiscale_responsabile(codFiscRes);
		 					rubricaRes.setCognome(cognomeRes);
		 					rubricaRes.setTelefono(telefonoRes);
		 					rubricaRes.setFax(faxRes);
		 					rubricaRes.setEmail(emailRes);
		 					rubricaRes.setNome(nomeRes);
		 					rubricaRes.setIndirizzo(indirizzo);
		 					rubricaRes.setCap(cap);
		 					rubricaRes.setComuneIstat(comuneIstat);
			 				
		 					List<RubricaResponsabili> rubricaArray = new ArrayList<RubricaResponsabili>();
			 				rubricaArray.add(rubricaRes);
			 				
			 				//saBean.setResponsabiliXML(rubricaArray);
			 				SimogValidator validator = ValidatorFactory.getValidator(ParametriServletRubrica.TAB_RUBRICA, currentActiveConnection, logger);
			 				if(validator.valida(rubricaArray, PSBD.TAB_RUBRICA_RESPONSABILI)){
			 					RubricaResponsabiliManager rubricaRespManager = new RubricaResponsabiliManager(currentActiveConnection, logger);
			 				
			 					if(rubricaRespManager.checkCF(rubricaRes.getCodice_fiscale_responsabile())){
									rubricaRespManager.insertPartecipante(rubricaRes,false);
									validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RUBRICA_004);
									request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
									//forward("loadBloccoDati2?"+it.avlp.simog.common.servlet.PSBD.ACTION_TYPE+"=Cerca&"+PSBD.TAB+"="+PSBD.TAB_RESPONSABILE_PROCEDIMENTO, request, response);
									//imposto la ricerca per far uscire solo quello appena inserito
					 				forward("rubrica?operazione=Cerca in rubrica", request, response);
					 				return;
								}else{
										
									request.setAttribute("carica","SI");
									validator.getEccezioni().addValidationErr(Messaggi.SIMOG_RUBRICA_003);
									request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
									
									forward("rubrica?operazione=Aggiungi", request, response);
									return;
								}
			 				}
			 				request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
			 				forward("rubrica?operazione=Aggiungi", request, response);
			 				
		 					
		 				}catch(Exception e){		
		 					logger.fatal(e.getMessage());
		 					//sendError(request, response, e.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_POPUP, e);
		 				}finally {
		 					closeConnection(request.getSession().getId(),getClass().getName());
		 				}
	 				//}else if (request.getParameter(PSBD.TAB).equals("TabAffidatario")){
	 			}
	 			else if (request.getParameter(PSBD.TAB).equals(PSBD.TAB_AFFIDATARIO)
	 					    //GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
	 					    ||(request.getParameter(PSBD.TAB).equals(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_OPERATORE_ECONOMICO))
	 						|| request.getParameter(PSBD.TAB).equals(ParametriServletInizioLavori.TAB_POSIZIONE_AGGIUDICATARI)
	 						|| request.getParameter(PSBD.TAB).equals(PSBD.TAB_DITTA_AUSILIARIA)
	 						|| request.getParameter(PSBD.TAB).equals(PSBD.TAB_DITTA_RAGGRUPPAMENTO)){
	 				try{
	 					logger.debug("sono nel performSavePopup ooo");

	 					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());

		 				//int id_partecipante=0;
		 				
		 				//String idPartecipante = request.getParameter(ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_PARTECIPANTE);
		 				String denominazione = request.getParameter(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE);
		 				String codiceFiscale = request.getParameter(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE).toUpperCase();
		 				String cameraCommercio = request.getParameter(ParametriServletRubrica.FIELD_NAME_CAMERA_COMMERCIO);
		 				String cfRappresentante = request.getParameter(ParametriServletRubrica.FIELD_NAME_CF_RAPPRESENTANTE);		 				
		 				String nome = request.getParameter(ParametriServletRubrica.FIELD_NAME_NOME);
		 				String cognome = request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME);
		 				String indirizzo = request.getParameter(ParametriServletRubrica.FIELD_NAME_INDIRIZZO);
		 				String citta = request.getParameter(ParametriServletRubrica.FIELD_NAME_CITTA);
		 				String provincia = request.getParameter(ParametriServletRubrica.FIELD_NAME_PROVINCIA);
		 				String cap = request.getParameter(ParametriServletRubrica.FIELD_NAME_CAP);
		 				String civico = request.getParameter(ParametriServletRubrica.FIELD_NAME_CIVICO);
		 				String partitaIVA = request.getParameter(ParametriServletRubrica.FIELD_NAME_PARTITA_IVA);
		 				String id_stato = request.getParameter(ParametriServletRubrica.FIELD_NAME_ID_STATO);		/*UN*/
		 				String flagEsteri = request.getParameter(ParametriServletRubrica.FIELD_NAME_FLAG_ESTERI);	/*UN*/
		 				
		 				Rubrica rubrica = new Rubrica();
		 				rubrica.setCamera_commercio(cameraCommercio);
		 				//rubrica.setData_fine(dataFineSoggetto);				
		 				rubrica.setDenominazione(denominazione);
		 				rubrica.setCodice_fiscale(codiceFiscale.toUpperCase());
		 				//rubrica.setData_inizio_sogg(dataInizioSoggetto);
		 				rubrica.setCf_rappresentante(cfRappresentante);
		 				//rubrica.setId_soggetto_partecipante(Integer.parseInt(idPartecipante));
		 				rubrica.setNome(nome);
		 				rubrica.setCognome(cognome);
		 				rubrica.setIndirizzo(indirizzo);
		 				rubrica.setCivico(civico);
		 				rubrica.setCap(cap);
		 				rubrica.setCitta(citta);
		 				rubrica.setProvincia(provincia);
		 				rubrica.setPartitaIva(partitaIVA);
		 				rubrica.setId_stato(id_stato);						/*UN*/
		 				rubrica.setFlagEsteri(flagEsteri);					/*UN*/
		 				
		 				List<Rubrica> rubricaArray = new ArrayList<Rubrica>();
		 				rubricaArray.add(rubrica);

						RubricaManager rubricaManager = new RubricaManager(currentActiveConnection, logger);

		 				SimogValidator validator = ValidatorFactory.getValidator(ParametriServletRubrica.TAB_RUBRICA, currentActiveConnection, logger);
		 				if(validator.valida(rubricaArray, PSBD.TAB_RUBRICA_AFFIDATARIO)){
							
							if(rubricaManager.checkCF(rubrica.getCodice_fiscale(),rubrica.getId_stato())){
							    rubricaManager.insertPartecipante(rubrica,false);
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RUBRICA_002);
								request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
				 				request.getSession().setAttribute(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE, "");
				 				request.getSession().setAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE, codiceFiscale);
				 				request.getSession().setAttribute(ParametriServletRubrica.FIELD_NAME_ID_STATO, id_stato);
								
				 				
				 				forward("rubrica?operazione=Cerca in rubrica", request, response);
								return;
							}else{
								validator.getEccezioni().addValidationErr(Messaggi.SIMOG_RUBRICA_003);
		 						//request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
								//sendError(request, response, Messaggi.SIMOG_RUBRICA_006, "rubrica?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+"=Aggiungi");
							}
		 				}
						request.setAttribute(ParametriServlet.FLAG_ESTERO, flagEsteri);
		 				request.setAttribute(ParametriServletRubrica.FIELD_NAME_ID_STATO, id_stato);
		 				request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());

		 				forward("rubrica?operazione=Aggiungi", request, response);
	 				
	 				}catch(SQLException sqle){
	 					logger.fatal(sqle.getMessage());
	 					//sendError(request, response, sqle.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_POPUP, sqle);
	 				}catch(Exception e){	
	 					logger.fatal(e.getMessage());
	 					//sendError(request, response, e.getMessage(), ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_POPUP, e);
	 				}finally {
	 					
	 					request.setAttribute(ParametriServlet.FIELD_NAME_ID_INFO, 
	 							request.getParameter(ParametriServlet.FIELD_NAME_ID_INFO));
	 					// ID_LOTTO
	 					request.setAttribute(ParametriServlet.FIELD_NAME_ID_LOTTO, 
	 							request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO));
	 					// ID_AGGIUDICAZIONE
	 					request.setAttribute(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE, 
	 							request.getParameter(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE));
	 					
	 					// DATA_INIZIO_AGGIUDICAZIONE
	 					request.setAttribute(PSBD.DATA_INIZIO_AGGIUDICAZIONE, 
	 							request.getParameter(PSBD.DATA_INIZIO_AGGIUDICAZIONE));
	 					
	 					request.setAttribute(PSBD.TAB, 
	 							request.getParameter(PSBD.TAB));
	 					
	 					request.setAttribute(ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO, 
	 							request.getParameter(ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO));
	 					
	 					
						//rollbackOrcommit(currentActiveConnection);
						closeConnection(request.getSession().getId(),getClass().getName());
	 				}	
	 			}
	 			}else {
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				}
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			}
	 		
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
			
			Connection currentActiveConnection = null;
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
	 			if ( currentUser.isRUP() || currentUser.isCS()) {				
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
	 				
	 				if(request.getParameter(PSBD.TAB).equalsIgnoreCase(PSBD.TAB_RESPONSABILE_PROCEDIMENTO)
	 					//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
	 					//|| tab.equalsIgnoreCase(PSBD.TAB_PRESTAZIONI) ){
	 					|| (request.getParameter(PSBD.TAB).equalsIgnoreCase(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_INCARICATI))){
	 					// codiceFiscale = (request.getParameter(PSBD.FIELD_NAME_PRESTAZIONE_CODICEFISCALE)!=null) ? request.getParameter(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE) : "";
	 					codiceFiscale = (request.getParameter(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE)!=null) ? request.getParameter(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE) : "";
		 				logger.debug("codiceFiscale - "+codiceFiscale);
		 				
		 				//cognome = (request.getParameter(PSBD.FIELD_NAME_PRESTAZIONE_COGNOME)!=null) ? request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME) : "";
		 				cognome = (request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME)!=null) ? request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME) : "";
		 				logger.debug("cognome - "+cognome);
		 				
		 				//nome = (request.getParameter(PSBD.FIELD_NAME_PRESTAZIONE_NOME)!=null) ? request.getParameter(ParametriServletRubrica.FIELD_NAME_NOME) : "";
		 				nome = (request.getParameter(ParametriServletRubrica.FIELD_NAME_NOME)!=null) ? request.getParameter(ParametriServletRubrica.FIELD_NAME_NOME) : "";
		 				logger.debug("nome - "+nome);
		 				
		 				//***********************************************************************************************************
		 				// Questa parte � utile per prelevare codice fiscale e cognome e nome nel caso della paginazione 
		 				// per poter rieffettuare la query
		 				
		 				if ( request.getParameter("paginazione") != null ) {
		 				currentActiveSession.setAttribute(ParametriServletRubrica.FIELD_NAME_NOME, nome);	
		 				currentActiveSession.setAttribute(ParametriServletRubrica.FIELD_NAME_COGNOME, cognome);
		 				currentActiveSession.setAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE, codiceFiscale);
		 				}
		 				
		 				if ( currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_COGNOME) != null ){
		 				cognome = ( (String) currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_COGNOME) );
		 				logger.debug("Cognome in sessione - "+cognome);
		 				}
		 				
		 				if (currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_NOME) != null ){
		 				nome = ((String) currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_NOME) );	
		 				logger.debug("Nome in sessione - "+nome);
		 				} 
		 				
		 				if (currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE) != null ) {
		 				codiceFiscale = (String) currentActiveSession.getAttribute(ParametriServletRubrica.FIELD_NAME_CODICE_FISCALE);
		 				logger.debug("codiceFiscale in sessione - "+codiceFiscale);
		 				}
		 				
		 				//***********************************************************************************************************
		 				
		 				
	 				}
	 				// sezione operatori economici
	 				else{
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
	 				}
	 				
	 					
	 				try {
	 					
	 					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());

	 					TableBean rubricaList = null;
	 					
	 					// If per distiguere i casi di Affidatari e Responsabili
	 					//---------------------------------------------------------------------------------------------------------------
	 					//                                                   AFFIDATARI
	 					//---------------------------------------------------------------------------------------------------------------
	 					if(request.getParameter(PSBD.TAB).equals(PSBD.TAB_AFFIDATARIO)
	 							//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
	 		 					|| (request.getParameter(PSBD.TAB).equalsIgnoreCase(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_OPERATORE_ECONOMICO))
	 							|| request.getParameter(PSBD.TAB).equals(ParametriServletInizioLavori.TAB_POSIZIONE_AGGIUDICATARI)
	 							|| request.getParameter(PSBD.TAB).equals(PSBD.TAB_DITTA_AUSILIARIA)
	 							|| request.getParameter(PSBD.TAB).equals(PSBD.TAB_DITTA_RAGGRUPPAMENTO)){
	 						// In caso di affidatari devo considerare Cognome/Denominazione e codice fiscale
	 						RubricaManager rubricaManager = new RubricaManager(currentActiveConnection, logger);
		 					logger.debug("Esecuzione getPartecipantiList");
		 					logger.debug("cognome "+cognome);
		 					logger.debug("nome "+nome);
		 					logger.debug("codice "+codiceFiscale);		 					
		 					rubricaList = rubricaManager.getPartecipantiList(cognome, 
		 																	 codiceFiscale,
		 																	 id_stato,
		 																	 startRow, 
		 																	 configuration.getMaxElementiPerPagina());
		 					
		 					logger.debug("numero righe:::"+rubricaList.getRowsCount());
		 				// ----------------------------------------------------------------------------------------------------------------
		 				//                                                   RESPONSABILI
		 				// ----------------------------------------------------------------------------------------------------------------
	 					}else if(request.getParameter(PSBD.TAB).equalsIgnoreCase(PSBD.TAB_RESPONSABILE_PROCEDIMENTO) || 
	 						//GM NUOVO CONTROLLO OPERATORI ECONOMICI PER PRESTAZIONI
	 						//request.getParameter(PSBD.TAB).equalsIgnoreCase(PSBD.TAB_PRESTAZIONI)){
	 		 				(request.getParameter(PSBD.TAB).equalsIgnoreCase(PSBD.TAB_PRESTAZIONI) && request.getAttribute("titleRubrica").equals(ParametriServletRubrica.RUBRICA_INCARICATI))){
	 						// Nel Caso di Responsabili devo considerare Cognome Nome e Codice Fiscale
	 						RubricaResponsabiliManager rubricaResManager = new RubricaResponsabiliManager(currentActiveConnection, logger);
	 						// rubricaList = rubricaResManager.getSoggettiPartecipantiRubTab(cognome,nome,codiceFiscale);
	 						rubricaList = rubricaResManager.getPartecipantiList(cognome+" "+nome, // escamotage LUCA  
	 																			codiceFiscale, 
	 																			startRow, 
	 																			configuration.getMaxElementiPerPagina());
	 						logger.debug("numero righe:::"+rubricaList.getRowsCount());
	 						
	 					}
	 					// ------------------------------------------------- fine if -----------------------------------------------------
	 					
	 					request.setAttribute(ParametriServlet.TABLEBEAN, rubricaList);
 						request.setAttribute(ParametriServlet.START_ROW, new Integer(startRow) );
 						request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getELEMENTI_VISUALIZZATI());
	 					
	 					if ( rubricaList.size() == 0 ) {
	 						sendMessage(request, response, Messaggi.SIMOG_RIC_001, "scheda1/"+ParametriServletRubrica.JSP_RUBRICA_POPUP);
	 					} else {
	 						forward("scheda1/popupRubrica.jsp", request, response);
	 					}
	 				} catch ( Exception sqle ) {
	 					sendError(request, response, sqle.getMessage(), "scheda1/"+ParametriServletRubrica.JSP_RUBRICA_POPUP, sqle);
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
		
		/*********************************************************************************
		 * Effettua la validazione di elementi in rubrica 
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
							
	 					RubricaManager rubricaManager = new RubricaManager(currentActiveConnection, logger);
		 				PaesiManager pm = new PaesiManager(currentActiveConnection,logger);

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
 						
		 				String idstato = dettaglioPartecipante.getRow(0).getNulledField(SOGGETTI_PARTECIPANTI.ID_STATO);
		 				String dataInizio = dettaglioPartecipante.getRow(0).getNulledField(SOGGETTI_PARTECIPANTI.DATA_INIZIO_SOGG);			
		 				request.setAttribute(STATI_ESTERI.TABLE_NAME, pm.loadPaese(idstato,dataInizio));
		 				
		 				request.setAttribute(SOGGETTI_PARTECIPANTI.ID_STATO, idstato);
		 				if(idstato != null && !"".equals(idstato))
		 					request.setAttribute(ParametriServlet.FLAG_ESTERO, Costanti.FLAG_VALORE_SI);
		 				else
		 					request.setAttribute(ParametriServlet.FLAG_ESTERO, Costanti.FLAG_VALORE_NO);			 				

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
		 				if(request.getParameter(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE)!=null)
		 					denominazione=request.getParameter(ParametriServletRubrica.FIELD_NAME_DENOMINAZIONE);
		 				String codiceFiscale = "";
		 				
		 				if(request.getParameter("codiceFiscale")!=null)
		 					codiceFiscale=request.getParameter("codiceFiscale").toUpperCase();
		 				
		 				String dataFineSoggetto = request.getParameter(ParametriServletRubrica.FIELD_NAME_DATA_FINE_SOGGETTO);
					
		 				String dataInizioSoggetto = request.getParameter(ParametriServletRubrica.FIELD_NAME_DATA_INIZIO_SOGGETTO);
						
		 				String cameraCommercio = request.getParameter(ParametriServletRubrica.FIELD_NAME_CAMERA_COMMERCIO);
		 				String cfRappresentante = request.getParameter(ParametriServletRubrica.FIELD_NAME_CF_RAPPRESENTANTE);
		 				
		 				String nome = request.getParameter(ParametriServletRubrica.FIELD_NAME_NOME);
		 				String cognome = request.getParameter(ParametriServletRubrica.FIELD_NAME_COGNOME);
		 				String indirizzo = request.getParameter(ParametriServletRubrica.FIELD_NAME_INDIRIZZO);
		 				String citta = request.getParameter(ParametriServletRubrica.FIELD_NAME_CITTA);
		 				String provincia = request.getParameter(ParametriServletRubrica.FIELD_NAME_PROVINCIA);
		 				String cap = request.getParameter(ParametriServletRubrica.FIELD_NAME_CAP);
		 				String civico = request.getParameter(ParametriServletRubrica.FIELD_NAME_CIVICO);
		 				String partitaIVA = request.getParameter(ParametriServletRubrica.FIELD_NAME_PARTITA_IVA);
		 				
		 				String id_stato = request.getParameter(ParametriServletRubrica.FIELD_NAME_ID_STATO);		/*UN*/
		 				String flagEsteri = request.getParameter(ParametriServletRubrica.FIELD_NAME_FLAG_ESTERI);	/*UN*/
		 				
		 				
		 				int idPartecipante = Integer.parseInt( request.getParameter(ParametriServletRubrica.FIELD_NAME_ID_SOGGETTO_PARTECIPANTE).toString());
		 				java.sql.Timestamp currentDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		 				
		 				dataFineSoggetto = PageHelper.formatDate(currentDate);
		 				
		 				Rubrica rubrica = new Rubrica();
		 				rubrica.setCamera_commercio(cameraCommercio);
		 				rubrica.setData_fine(dataFineSoggetto);				
		 				rubrica.setDenominazione(denominazione);
		 				rubrica.setCodice_fiscale(codiceFiscale.toUpperCase());
		 				rubrica.setData_inizio_sogg(dataInizioSoggetto);
		 				rubrica.setCf_rappresentante(cfRappresentante);
		 				rubrica.setId_soggetto_partecipante(idPartecipante);
		 				rubrica.setNome(nome);
		 				rubrica.setCognome(cognome);
		 				rubrica.setIndirizzo(indirizzo);
		 				rubrica.setCivico(civico);
		 				rubrica.setCap(cap);
		 				rubrica.setCitta(citta);
		 				rubrica.setProvincia(provincia);
		 				rubrica.setPartitaIva(partitaIVA);
		 				
		 				rubrica.setId_stato(id_stato);						/*UN*/
		 				rubrica.setFlagEsteri(flagEsteri);					/*UN*/
		 				
		 				//Scheda_A saBean = new Scheda_A();
		 				List<Rubrica> rubricaArray = new ArrayList<Rubrica>();
		 				rubricaArray.add(rubrica);
		 				SimogValidator validator = ValidatorFactory.getValidator(ParametriServletRubrica.TAB_RUBRICA, currentActiveConnection, logger);
		 						 				
		 				String actionTarget = "";
		 				if(validator.valida(rubricaArray, PSBD.TAB_RUBRICA_AFFIDATARIO)){
		 					
		 					actionTarget= "?okVal=S";
		 					validator.getEccezioni().addValidationInfo("Dati OK, premi SALVA per memorizzare, o Annulla per ingorare le modifiche");
		 					request.setAttribute("okVal",Costanti.FLAG_VALORE_SI);

		 					request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
							forward("scheda1/" + ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RES_POPMODOE+ actionTarget, request, response);
							return;
}
		 				else{
							actionTarget = "=PopUpMod";
						}

	 					request.setAttribute(ParametriServlet.ERRORBEAN, validator.getEccezioni());
						forward("rubrica?"+it.avlp.simog.common.servlet.ParametriServletRubrica.OPERAZIONE+actionTarget, request, response);
						return;
						
	 				}catch(Exception e){		
	 					sendError(request, response, e.getMessage(), "scheda1/" +ParametriServletRubrica.JSP_DETTAGLIO_RUBRICA_RES_POPMODOE, e);
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
		
	 				
}