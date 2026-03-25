package it.avlp.simog.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.ErrorBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.MessageBean;
import it.avlp.simog.beans.NavigationBean;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.AFFIDAMENTI_RISERVATI;
import it.avlp.simog.db.generated.ART_ESCLUSIONE;
import it.avlp.simog.db.generated.ART_ESTREMA_URGENZA_SOMMA_URGENZA;
import it.avlp.simog.db.generated.CATEGORIA;
import it.avlp.simog.db.generated.EAGG_CATEGORIE;
import it.avlp.simog.db.generated.EAGG_MOTIVI;
import it.avlp.simog.db.generated.FUNZIONI_DELEGATE;
import it.avlp.simog.db.generated.MODALITA_GARA;
import it.avlp.simog.db.generated.MODALITA_INDIZIONE_ALLEGATO_IX;
import it.avlp.simog.db.generated.MODI_REALIZZAZIONE;
import it.avlp.simog.db.generated.MODO_INDIZIONE;
import it.avlp.simog.db.generated.STRUMENTI_SVOLGIMENTO_PROCEDURE;
import it.avlp.simog.db.generated.TIPI_CATEGORIA;
import it.avlp.simog.db.generated.TIPOLOGIA;
import it.avlp.simog.db.hsql.HSqlManager;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;


public abstract class ServletBase extends HttpServlet implements ParametriServlet, Messaggi {
		
	private class DatiConnessione {
		public int counter;
		public Connection conn;
		public DatiConnessione(Connection conn) {
			this.conn = conn;
			this.counter = 0;
		}
	}
	
	protected static HashMap <String, DatiConnessione> connectionMap = new HashMap<String, DatiConnessione>();
	
	/* Impostazioni DATASOURCE */
	private static InitialContext initialContext = null;
	private static DataSource dataSource = null;
	protected static Logger logger = null;
	protected static SimogProperties configuration = null;
		
	public static Map<String, String> motivoDeroga; //TODO: TROVARE MODO MIGLIORE DI PASSARE I DATI AD ALTRE CLASSI
	public static Map<String, String> misuraPremiale; //TODO: TROVARE MODO MIGLIORE DI PASSARE I DATI AD ALTRE CLASSI
//	protected String currentCheckingField = null;
//	protected Utente currentUser = null;
//	protected String prossimaPagina = null;
	
	// virtuale 
	protected static HSqlManager virtualDB = null;
 
	protected final void forward (String nextPage, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		forward ( nextPage, request, response, true );
	}
	
	
	/*******************************************************************************************************
	 * Effettua il forward verso la pagina controllando, nel caso in cui 
	 * requestedSlash sie true, se l'URL della pagina inizi con uno slash.
	 * Nel caso mancasse lo slash lo aggiunge. Qualora requestedSlash sia False 
	 * tale controllo non viene eseguito   
	 * 
	 * @param nextPage String
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param requestedSlash boolean
	 * @throws ServletException
	 * @throws IOException
	 */
	protected final void forward(String nextPage, HttpServletRequest request, HttpServletResponse response, boolean requestedSlash) throws ServletException, IOException {
		if ( requestedSlash ) {
			if ( ! nextPage .startsWith("/") ) {
				//logger.debug("CHAMATA ALLA PAGINA [" + nextPage + "] effettuata senza /");
				nextPage = "/" + nextPage;
			}			
		}
		// Garantisce Content-Type UTF-8 per tutte le risposte HTML
		if (!response.isCommitted()) {
			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
		}
		logger.debug ( "Trying RequestDispatcher" );
		RequestDispatcher rd = getServletContext().getRequestDispatcher(nextPage);
		logger.debug ( "Trying forward to [" + nextPage + "]" );
		
		if (!response.isCommitted())
			//response.reset();
			rd.forward(request, response);
		
		return;
	}
	
	/*******************************************************************************************************
	 * Il metodo permette di visualizzare attraverso il Logger tutti i parametri, ed i valori associati, 
	 * che sono contenuti all'interno della request
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void visualizzaListaParametriValori (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.debug( "------------------------------------------ ");
		logger.debug("Visualizzazione valori [" + request.getRequestURL() + "]" );
		
		for ( Enumeration e = request.getParameterNames(); e.hasMoreElements(); ) {
			String currentParamName = (String) e.nextElement();
			String[] currentValue = request.getParameterValues(currentParamName);
			
			for ( int i = 0; i < currentValue.length; i++ ) {
				logger.debug("* Parametro [" + currentParamName + "] valore [" + i + "] [" + currentValue[i] + "]");
			}
		}
		logger.debug( "------------------------------------------ ");
	}
	
	
	/*************************************************************************************************
	 * Comunica se la sessione risulta attiva per l'utente
	 * @param request HttpServletRequest
	 * @return boolean True se la connessione e' presente , False altrimenti 
	 * @throws IOException
	 * @throws ServletException
	 */
	protected boolean checkSession(HttpServletRequest request) throws IOException, ServletException {
		
		//Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		// La sessione in questo momento deve essere gia' attiva
		HttpSession currentActiveSession = (HttpSession) request.getSession(false);
		
		boolean accessGranted = ( currentActiveSession != null );
		
		//il servizio dev'essere disponibile se l'utente non e' amministratore
		accessGranted = accessGranted && checkService(request) && checkServiceTime(request);	
		
		if ( accessGranted) {
			//logger.debug("accessGranted e' true (1)");
			accessGranted = ( ( currentActiveSession.getAttribute(UTENTE) ) != null ); 
			//logger.debug("Utente:" + currentUser.getLogin());
			//logger.debug("accessGranted finale: " + (accessGranted ? "true" : "false"));
		}
		return (accessGranted);
	}

	/*****************************************************************************************************
	 * Effettua un check sul servizio e annota i risultati sul logger. 
	 * @param request HttpServletRequest
	 * @throws IOException
	 * @throws ServletException
	 */
	protected boolean checkService(HttpServletRequest request) throws IOException, ServletException {

		AccessiDB adb;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
// PP commentata accessidb che non serve se checkservice è vuota
//		try {
//			adb = new AccessiDB(getSimogConnection(request.getSession().getId(),getClass().getName()),logger);
//		} catch (SimogException e) {
//			e.printStackTrace();
//			throw new ServletException ( e.getMessage() );
//		}
		Boolean serviceGranted = true;
// PP		serviceGranted= new Boolean(adb.checkService());
//		if ( serviceGranted) {
//			logger.debug("serviceGranted e' true");			
//		}
		// PP forzatura per testare quando è fuori servizio
		if (!serviceGranted && currentUser.isAmministratore() )
			serviceGranted=true;
		
		request.getSession().setAttribute(SERVICE_AVAILABLE, serviceGranted);
// PP 		closeConnection(request.getSession().getId(),getClass().getName());
		return (serviceGranted);
	}

	/*****************************************************************************************************
	 * Effettua un check sull'orario per disabilitare il servizio 
	 * @param request HttpServletRequest
	 * @throws IOException
	 * @throws ServletException
	 */
	protected boolean checkServiceTime(HttpServletRequest request) throws IOException, ServletException {

		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		
		Boolean serviceGranted = false;
		
		Date data = new Date();
		
		
		serviceGranted = data.getHours() >= 6 && data.getHours() <= 21;
		
		// PP forzatura per testare quando è fuori servizio
		if (!serviceGranted && currentUser != null 
		      && (currentUser.isAmministratore() || "AAAAAA00A01H501Z".equals(currentUser.getLogin())))
			serviceGranted=true;

		
		request.getSession().setAttribute(SERVICE_AVAILABLE, serviceGranted);
		return (serviceGranted);
	}
	/***************************************************************************************************+
	 * Inserisce un messaggio come attributi della request 
	 * @param request HttpServletRequest
	 * @param message String
	 */
	protected void setMessage ( HttpServletRequest request, String message) {
		MessageBean messageBean = new MessageBean ( message );
		request.setAttribute( ERRORBEAN, messageBean );
		
	}
	
	
	public synchronized final void init() throws ServletException {
		
		SimogFlags.setFromWeb(true);
		
		if ( logger == null ) {
		
			/*
			 * Inizializzazione del Logger tramite parametro di inizializzazione
			 * che prende in ingresso il NOME_FILE LOG4J path assoluto
			 * contenente le informazioni del LOGGER
			 */
		
			for ( Enumeration e = getInitParameterNames(); e.hasMoreElements(); ) {
				String currentInitParam = (String) e.nextElement();
				log ( "INIT : [" + currentInitParam + "] + [" + getInitParameter(currentInitParam) + "]" );
			}

			String logConfigFileName = getInitParameter(LOG_CONFIG_FILENAME);
		
			log ( "Parametro di configurazione LOG [" + LOG_CONFIG_FILENAME + "]" );
			log ( "La configurazione del log sara' utilizzata dal file [" + logConfigFileName + "]" );
				
//			PropertyConfigurator.configure(getServletContext().getRealPath(logConfigFileName));
			PropertyConfigurator.configure(logConfigFileName);
			logger = Logger.getLogger("SIMOG_LOGGER");
			logger.debug("configurazione log :" +logConfigFileName);
			logger.debug("LOGGER applicativo inizializzato correttamente");
		}
		
		if ( configuration == null ) {
			String configFileName = getInitParameter(CONFIG_FILENAME);
			
			log ( "Parametro di configurazione APPLICAZIONE [" + CONFIG_FILENAME + "]" );
			log ( "L'applicazione utilizza la configurazione impostata in [" + configFileName + "]" );			
			
			
			try {
				logger.debug("configurazione simog :" +configFileName);
// configuration = new SimogProperties(getServletContext().getRealPath(configFileName), logger);
				configuration = SimogProperties.createInstance(configFileName, logger);

				if(configuration.getJDBCDriver()!=null){
					logger.debug ( "Caricamento in corso del driver [" + configuration.getJDBCDriver() + "]" );
					Class.forName(configuration.getJDBCDriver());
				}

				//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				
				/* QUESTO PRIMA STAVA DENTRO SRV AUTENTICA */
//				logger.info ( "Inizializzazione componenti di sicurezza..." );				
//				System.setProperty("javax.net.ssl.trustStore", configuration.getTrustorePath() );			
//				logger.info ( "Caricamento TrustStore [" + configuration.getTrustorePath() + "]" );
//				
//				System.setProperty("javax.net.ssl.trustStorePassword", configuration.getTrustStorePassword());
// PP dati sensibili				logger.info ( "Caricamento TrustStore Password [" + configuration.getTrustStorePassword() + "]" );			
//				logger.info ( "Inizializzazione componenti di sicurezza completata" );
				/* ****************************** */
				
			} catch ( Exception se ) {
				se.printStackTrace();
				logger.fatal ( "ERRORE durante caricamento del driver [" + se.getMessage() + "]" );
				throw new ServletException ( se.getMessage() );
			}
		}
		
		/**
		 * Inizializzazione DB in memoria per CPV. ISTAT, NUTS
		 * ATTENZIONE lasciare a null la variabile se non si vuole attivare la cache
		 */
		if(virtualDB == null){
			try {
				Connection tempConn = getSimogConnection("fakeId", this.getClass().getName());
				
                // 3.02.2.1 PP solo select per questa transazione
				tempConn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

				virtualDB = HSqlManager.getInstance(tempConn, logger);
				closeConnection("fakeId", this.getClass().getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
 
	
	public void doPost(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException {
			// Imposta Content-Type UTF-8 per tutte le risposte HTML
			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			perform(request, response);
	}

    public void doGet(HttpServletRequest request,HttpServletResponse response)
          throws ServletException, IOException {
			// Imposta Content-Type UTF-8 per tutte le risposte HTML
			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
           perform(request, response);
      }
	/*******************************************************************************************************
	 * Viene aggiunto un messaggio di errore e si effettua il forward verso la pagina indicata. 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param errorMessage String
	 * @param targetPage String
	 * @param requestedSlash boolean , True si controlla se l'URL inizi con lo slash, False non viene
	 * fatto alcun controllo
	 * @throws IOException
	 * @throws ServletException
	 */
	protected final void sendError ( HttpServletRequest request, HttpServletResponse response, String errorMessage, String targetPage, boolean requestedSlash ) throws IOException, ServletException {
//		logger.fatal(errorMessage);
		logger.debug ( "SENDING ERROR [" + errorMessage + "] TO [" + targetPage + "]");
		ErrorBean errorBean = new ErrorBean(errorMessage);
		request.setAttribute(ERRORBEAN, errorBean );
		forward( targetPage, request, response, requestedSlash );		
	}
	
	/*******************************************************************************************************
	 * Viene inserito un errore ed eseguito il forward verso la pagina 
	 * indicata controllando se l'url inizi con uno slash o meno
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param errorMessage String
	 * @param targetPage String
	 * @throws IOException
	 * @throws ServletException
	 */
	protected final void sendError ( HttpServletRequest request, HttpServletResponse response, String errorMessage, String targetPage ) throws IOException, ServletException {
		sendError( request, response, errorMessage, targetPage, true);
	}

	/****************************************************************************************************
	 * Viene inserito un messaggio di errore ed eseguito il forward 
	 * alla pagina indicata
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param errorMessage String
	 * @param page String
	 * @param currentException Exception
	 * @throws IOException
	 * @throws ServletException
	 */
	protected final void sendError ( HttpServletRequest request, HttpServletResponse response, String errorMessage, String page, Exception currentException ) throws IOException, ServletException {

	   // ottengo un ID univoco che comunico all'utente per la diagnostica dell'anomalia
	   Utente user = (Utente) request.getSession().getAttribute(ParametriServlet.UTENTE);
	   String userName = "NOUSER";
	   if (user != null)
	      userName = user.getLogin();
	   
	   String idSess = PageHelper.formatDateTimeCompr(new Date()) + "_" + request.getSession().getId() + "_" + userName;	   
	   
	   logger.debug("pagina di rinvio per eccezione:::"+page + " per errore: " + currentException.getMessage());

		Thread thread = Thread.currentThread();
		StackTraceElement[] vste = new StackTraceElement[0];
		try{
			vste = thread != null ? thread.getStackTrace() : vste;
		}catch (Exception e) {
			e.printStackTrace();
		}
		String buff = "";
        for (StackTraceElement ste : vste)
            buff = buff + ste.toString() + "\r\n";

//		ErrorBean errorBean = new ErrorBean(errorMessage + " ECCEZIONE: [" + currentException.getMessage() + "]<br><br>" + (buff.length() == 0 ? currentException : buff ) );
	   ErrorBean errorBean = new ErrorBean(errorMessage + " FAULT_ID: [" + idSess + "]" );

	   logger.fatal("*** ECCEZIONE *** ID: " + idSess 
	          + "\n\r*** MSG: " + currentException.getMessage() 
	          + "\n\r*** TRACE: " + buff);
		request.setAttribute(ERRORBEAN, errorBean );
		forward( page, request, response );
	}	
	
	/****************************************************************************************************
	 * Aggiunge il messaggio ed esegue il forward alla pagina
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param message String
	 * @param page String
	 * @throws IOException
	 * @throws ServletException
	 */
	protected final void sendMessage ( HttpServletRequest request, HttpServletResponse response, String message, String page ) throws IOException, ServletException {
		MessageBean errorBean = new MessageBean(message);
		request.setAttribute(ERRORBEAN, errorBean );
		forward( page, request, response );
	}	
	
	/*****************************************************************************
	 * Inserisce il messaggio 
	 * @param request HttpServletRequest
	 * @param message String
	 * @throws IOException
	 * @throws ServletException
	 */
	protected final void sendMessage ( HttpServletRequest request, String message ) 
	throws IOException, ServletException {

		MessageBean errorBean = new MessageBean(message);
		request.setAttribute(ParametriServlet.ERRORBEAN, errorBean );
	}
	
	/******************************************************************************
	 * Inserisce il messaggio di errore
	 * @param request HttpServletRequest
	 * @param message String
	 * @throws IOException
	 * @throws ServletException
	 */
	protected final void sendError ( HttpServletRequest request, String message ) 
	throws IOException, ServletException {

		ErrorBean errorBean = new ErrorBean(message);
		request.setAttribute(ParametriServlet.ERRORBEAN, errorBean );
	}
	
	protected abstract void perform(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException;
		

	/***********************************************************************************
	 * Ottiene una connessione 
	 * @return Connection
	 * @throws SQLException
	 * @throws NamingException
	 */
	private synchronized Connection getNewConnection() throws SQLException, NamingException {
		if ( configuration.getJDBCDataSourceName() != null && getDataSource() != null ) {
			logger.debug ( "Tentativo connessione DataSource in corso" );
			return getDataSource().getConnection();
		} else {
			logger.debug ( "Tentativo di Connessione locale] su " 
					+ configuration.getJDBCString());			
			return DriverManager.getConnection(configuration.getJDBCString());
		}
	}
	

	protected Connection getSimogConnection(String sid, String callingClass) throws SimogException {
		return getSimogConnection(sid, callingClass, true);
	}
	
	/***************************************************************************
	 * Restituisce l'attuale connessione attiva
	 * @param sid String
	 * @param callingClass String
	 * @return Connection
	 * @throws SimogException
	 */
	private synchronized Connection getSimogConnection(String sid, String callingClass, boolean caching) throws SimogException {

		DatiConnessione lConn = null;

		try{
			// controllo se esiste gi� una sessione
			synchronized(connectionMap){
				lConn = connectionMap.get(sid);
				if(!caching || lConn == null) {
					lConn = new DatiConnessione(getNewConnection());
					
					// PP patch per autocommit MySql
					if(configuration.getDBMS().equalsIgnoreCase(ParametriServlet.MYSQL))
						lConn.conn.setAutoCommit(false);
					
					connectionMap.put(sid, lConn);
				} 
				
				if (lConn.counter >= Integer.MAX_VALUE){
					logger.error ( "*** CONNMAXVALUE Raggiunto valore massimo per counter: resettato");
					lConn.counter = 0;
				}
				lConn.counter++;
				
			}
			
			logger.debug( "*** CONNREQ: " + sid 
					+ " classe: " + callingClass + " -> richSess: " + String.valueOf(lConn.counter)
					+ " totSess: " + String.valueOf(connectionMap.size()) + " " + (caching ? " CACHING " : "") + " CONN:" + lConn.conn );		
		
			return lConn.conn;
		} catch (Exception sqle) {
			logger.fatal(Messaggi.SIMOG_SQL_001, sqle);
			throw new SimogException(Messaggi.SIMOG_SQL_001);
		}
	}
	
	/*****************************************************************************************
	 * effettua la chiusura della connessione 
	 */
	protected synchronized void closeConnection (String sid, String callingClass) {
		DatiConnessione lConn = null;
		
		try{
			// controllo se esiste già una sessione
			synchronized(connectionMap){
				lConn = connectionMap.get(sid);
				if(lConn == null) {
					logger.error ( "*** CONN NOTOCLOSE: " + sid + " classe: " + callingClass);
					//throw new RuntimeException("nessuna connessione da chiudere: " + sid + " classe: " + callingClass);
					return;
				} 
					
				if (lConn.counter <= Integer.MIN_VALUE){
					logger.debug ( "*** CONNMINVALUE Raggiunto valore minimo per counter: resettato");
					lConn.counter = 1;
				}
				lConn.counter--;
					
				if(lConn.counter <= 0){
					// PP per sicurezza rollback prima di chiudere
					if(lConn.conn.getAutoCommit() == false)
						lConn.conn.rollback();
					
					lConn.conn.close();
					connectionMap.remove(sid);
				}

				logger.debug("*** CONNCLS: " + sid 
						+ " classe: " + callingClass + " -> richSess: " + String.valueOf(lConn.counter)
						+ " totSess " + String.valueOf(connectionMap.size()));		
			}
		
			return;
			
		} catch (Exception sqle) {
			logger.error(Messaggi.SIMOG_SQL_001, sqle);
		}
	}

	/***************************************************************************************
	 * Imposta le tabelle applicative
	 * @param request HttpServletRequest
	 * @param idOss 
	 * @throws SQLException
	 */
	protected final void setTabelleUtilita(HttpServletRequest request, Connection lConn, String dataVal, boolean isOrganoCost, String idOss) throws SQLException {

		logger.debug ( "Inizializzazione delle tabelle applicative" );
		AccessiDB dbManager = null;
		try {
			dbManager = new AccessiDB(lConn, logger);
			logger.debug ( "Connessione al db avvenuta correttamente" );
//				TableBean condizioniAgg = dbManager.executeSelect(CONDIZIONI.TABLE_NAME, CONDIZIONI.DATA_FINE_VALIDITA, CONDIZIONI.DESCRIZIONE);
			TableBean tipologia = dbManager.executeSelect(TIPOLOGIA.TABLE_NAME, TIPOLOGIA.DATA_FINE_VALIDITA, TIPOLOGIA.DESCRIZIONE, dataVal, isOrganoCost);
			
			// pp organi costituzionali
			Map<String, String> sceltaContraente = null;
			
			sceltaContraente = dbManager.loadSceltaContraente(dataVal, isOrganoCost, idOss);

			// TICKET ALM - 3.04.3 #2846
			LinkedHashMap<String, String> motivoCollegamento = null;
			
			motivoCollegamento = dbManager.loadMotivoCollegamento(dataVal);
            // FINE TICKET ALM - 3.04.3 #2846
			
			//TICKET 31047
			motivoDeroga = dbManager.loadMotivoDeroga(dataVal);
			misuraPremiale= dbManager.loadMisuraPremiale(dataVal);
			
			//TableBean categoriaSA = dbManager.executeSelect(CATEGORIA_SA.TABLE_NAME, CATEGORIA_SA.DATA_FINE_VALIDITA, CATEGORIA_SA.DESCRIZIONE);
				//logger.debug(ObjectIntrospector.propertiesInfo(categoriaSA.getRow(0).getClass(), categoriaSA.getRow(0)));
			TableBean categoria = dbManager.executeSelect(CATEGORIA.TABLE_NAME, CATEGORIA.DATA_FINE_VALIDITA, CATEGORIA.DESCRIZIONE, dataVal, isOrganoCost);
				//TableBean tipologiaSA = dbManager.executeSelect(TIPOLOGIA_SA.TABLE_NAME, TIPOLOGIA_SA.DATA_FINE_VALIDITA, TIPOLOGIA_SA.DESCRIZIONE);
			
			//TICKET ALM #2847
			//Criteri di aggiudicazione
			TableBean modalitaGara = dbManager.executeSelectWithData(MODALITA_GARA.TABLE_NAME,MODALITA_GARA.DATA_INIZIO_VALIDITA, MODALITA_GARA.DATA_FINE_VALIDITA, MODALITA_GARA.DESCRIZIONE, dataVal, isOrganoCost);
//				TableBean tipoAppalto = dbManager.executeSelect(TIPI_APPALTI.TABLE_NAME	, TIPI_APPALTI.DATA_FINE_VALIDITA, TIPI_APPALTI.DESCRIZIONE);
//				TableBean tipiCategoria = dbManager.executeSelect(TIPI_CATEGORIA.TABLE_NAME, TIPI_CATEGORIA.DATA_FINE_VALIDITA, TIPI_CATEGORIA.DESCRIZIONE);
//				logger.debug("tipo categoria ::: " + ObjectIntrospector.propertiesInfo(tipiCategoria.getRow(0).getClass(), tipiCategoria.getRow(0)));
			
			
			Map<String, String> modoIndizione = dbManager.getTipologica(MODO_INDIZIONE.TABLE_NAME, MODO_INDIZIONE.ID_MODO_GARA, MODO_INDIZIONE.DESCRIZIONE, MODO_INDIZIONE.DATA_FINE_VALIDITA,dataVal);
			request.setAttribute(MODO_INDIZIONE_GARA, modoIndizione);
 
			Map<String, String> listaTipiEnte = dbManager.getTipologica(TIPI_CATEGORIA.TABLE_NAME, TIPI_CATEGORIA.ID_TIPO_CATEGORIA, TIPI_CATEGORIA.DESCRIZIONE, TIPI_CATEGORIA.DATA_FINE_VALIDITA,dataVal);
			request.setAttribute("listaTipiEnte", listaTipiEnte);

            // pp organi costituzionali
			//TICKET ALM - 3.04.2 2005
			 Map<String, String> listaEsclusioni;
			 Map<String, String> listaModiReal;
			
			   listaEsclusioni = dbManager.getTipologicaDescrWithDP(ART_ESCLUSIONE.TABLE_NAME, ART_ESCLUSIONE.ID_ESCLUSIONE, ART_ESCLUSIONE.DESCRIZIONE, ART_ESCLUSIONE.DATA_FINE_VALIDITA,ART_ESCLUSIONE.DATA_INIZIO_VALIDITA,dataVal, isOrganoCost, ART_ESCLUSIONE.REGIME_ESCLUSIONE, "E");
			   listaModiReal = dbManager.getTipologicaWithData(MODI_REALIZZAZIONE.TABLE_NAME, MODI_REALIZZAZIONE.ID_MODO_REAL, MODI_REALIZZAZIONE.DESCRIZIONE, MODI_REALIZZAZIONE.DATA_INIZIO_VALIDITA, MODI_REALIZZAZIONE.DATA_FINE_VALIDITA,dataVal);
				
			
			request.setAttribute(ARTICOLO_ESCLUSIONE, listaEsclusioni);
			request.setAttribute(MODO_REALIZZAZIONE, listaModiReal);
			//FINE TICKET ALM - 3.04.2 2005
			
			//TICKET ALM #664
			Map<String, String> listaStrumentiSvolgimento = dbManager.getTipologica(STRUMENTI_SVOLGIMENTO_PROCEDURE.TABLE_NAME, STRUMENTI_SVOLGIMENTO_PROCEDURE.ID_SVOLGIMENTO, STRUMENTI_SVOLGIMENTO_PROCEDURE.DESCRIZIONE, STRUMENTI_SVOLGIMENTO_PROCEDURE.DATA_FINE_VALIDITA, dataVal);
			request.setAttribute(STRUMENTO_SVOLGIMENTO, listaStrumentiSvolgimento);
			//FINE TICKET ALM #664
			
			//TICKET ALM #3832
			Map<String,String> listaArtEstremaUrgenza = dbManager.getTipologica(ART_ESTREMA_URGENZA_SOMMA_URGENZA.TABLE_NAME, ART_ESTREMA_URGENZA_SOMMA_URGENZA.ID_ESTREMA_URGENZA, ART_ESTREMA_URGENZA_SOMMA_URGENZA.DESCRIZIONE, ART_ESTREMA_URGENZA_SOMMA_URGENZA.DATA_FINE_VALIDITA, dataVal);
			request.setAttribute(ESTREMA_URGENZA, listaArtEstremaUrgenza);
			//FINE TICKET ALM #3832
			
			//TICKET ALM #3834
			Map<String,String> listaModalitaAllIX = dbManager.getTipologica(MODALITA_INDIZIONE_ALLEGATO_IX.TABLE_NAME, MODALITA_INDIZIONE_ALLEGATO_IX.ID_ALLEGATO_IX, MODALITA_INDIZIONE_ALLEGATO_IX.DESCRIZIONE, MODALITA_INDIZIONE_ALLEGATO_IX.DATA_FINE_VALIDITA, dataVal);
			request.setAttribute(ALLEGATO_IX, listaModalitaAllIX);
			//FINE TICKET ALM #3834
			
//				request.setAttribute(TIPO_APPALTO_BEAN, tipoAppalto);
//				request.setAttribute(TIPO_CATEGORIA_BEAN, tipiCategoria);
				//request.setAttribute(CATEGORIA_SA_BEAN, categoriaSA);
			request.setAttribute(TIPOLOGIA_BEAN, tipologia);
			request.setAttribute(SCELTA_CONTRAENTE_BEAN, sceltaContraente);
			// TICKET ALM - 3.04.3 #2846
			request.setAttribute(MOTIVO_COLLEGAMENTO_BEAN, motivoCollegamento);
			// FINE TICKET ALM - 3.04.3 #2846
			request.setAttribute(CATEGORIA_BEAN, categoria);
				//request.setAttribute(TIPOLOGIA_SA_BEAN, tipologiaSA);
			request.setAttribute(CRITERI_AGGIUDICAZIONE_BEAN, modalitaGara);
//				request.setAttribute(CONDIZIONI_AGG_BEAN, condizioniAgg);
			//TICKET 31047
			request.setAttribute(MOTIVO_DEROGA_TABLEBEAN, motivoDeroga);
			request.setAttribute(MISURA_PREMIALE_TABLEBEAN, misuraPremiale);

			//TICKET ALM #3835
			Map<String,String> listaAffidamentiRiservati = dbManager.getTipologicaDescr(AFFIDAMENTI_RISERVATI.TABLE_NAME, AFFIDAMENTI_RISERVATI.ID_AFF_RISERVATI, AFFIDAMENTI_RISERVATI.DESCRIZIONE, AFFIDAMENTI_RISERVATI.DATA_FINE_VALIDITA, dataVal, isOrganoCost);
			request.setAttribute(AFF_RISERVATI, listaAffidamentiRiservati);
			//FINE TICKET ALM #3835
			
			//TICKET ALM - 3.04.2 2005
			Map<String,String> listaArticoliRegime = dbManager.getTipologicaDescrWithDP(ART_ESCLUSIONE.TABLE_NAME, ART_ESCLUSIONE.ID_ESCLUSIONE, ART_ESCLUSIONE.DESCRIZIONE, ART_ESCLUSIONE.DATA_FINE_VALIDITA,ART_ESCLUSIONE.DATA_INIZIO_VALIDITA,dataVal, isOrganoCost, ART_ESCLUSIONE.REGIME_ESCLUSIONE, "P");
			request.setAttribute(ART_REGIME, listaArticoliRegime);
			//FINE TICKET ALM - 3.04.2 2005
			
			//TICKET ALM #659 - 3.04.4
			Map<String,String> listaFunzioniDelegate = dbManager.getTipologica(FUNZIONI_DELEGATE.TABLE_NAME, FUNZIONI_DELEGATE.ID_F_DELEGATE, FUNZIONI_DELEGATE.DESCRIZIONE, FUNZIONI_DELEGATE.DATA_FINE_VALIDITA, dataVal);
			request.setAttribute(ID_F_DELEGATE, listaFunzioniDelegate);
			//FINE TICKET ALM #659 - 3.04.4
			
			if(SimogFlags.is30350_RFWEBGL01Active()){
	         Map<String, String> listaEaggMotivi = dbManager.getTipologica(
	               EAGG_MOTIVI.TABLE_NAME,
	               EAGG_MOTIVI.COD_MOTIVO,
	               EAGG_MOTIVI.DESCRIZIONE,
	               EAGG_MOTIVI.DATA_INIZIO_VALIDITA,
	               EAGG_MOTIVI.DATA_FINE_VALIDITA,
	               PageHelper.parseTimeYMD(dataVal));
	         
	         request.setAttribute(EAGG_MOTIVI_BEAN, listaEaggMotivi);
			   
            TableBean listaEaggCateg = dbManager.executeSelect(           
                  EAGG_CATEGORIE.TABLE_NAME,
                  EAGG_CATEGORIE.DATA_INIZIO_VALIDITA,
                  EAGG_CATEGORIE.COD_CATEGORIA,
                  PageHelper.getSqlDateFromYMD(dataVal),
                  isOrganoCost,
                  EAGG_CATEGORIE.DATA_FINE_VALIDITA
                  );
            
            request.setAttribute(EAGG_CATEGORIE_BEAN, listaEaggCateg);
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
			logger.fatal(Messaggi.SIMOG_SQL_000, e);
			throw new RuntimeException ( Messaggi.SIMOG_SQL_000 );
		} 
	}
	
	/********************************************************************************
	 * Effettua il rollback
	 */
	protected void rollback(Connection lConn) {
			try {
				
				// PP rollback solo se non sono in autocommit
				if (lConn.getAutoCommit() == false)
					lConn.rollback();
				else
					logger.error("Attenzione: invocato rollback su transazione autocommit ***" + lConn + "***");
					
			} catch(SQLException sqlRollback) {
				logger.fatal("Errore durante l'operazione di rollback", sqlRollback);
			}catch(Exception e){
				logger.fatal("Errore durante l'operazione di rollback", e);
			}
	}
	
	/*****************************************************************************
	 * Effettua il commit
	 * 
	 */
	protected void commit(Connection lConn) {
		try {
			
			// PP commit solo se non sono in autocommit
			if (lConn.getAutoCommit() == false)
				lConn.commit();
			else
				logger.error("Attenzione: invocato commit su transazione autocommit ***" + lConn + "***");
				

		} catch(SQLException sqlRollback) {
			logger.fatal("Errore durante l'operazione di rollback", sqlRollback);
		}
}
	
	/***********************************************************************
	 * ottiene la data di oggi nel formato yyyyMMdd
	 * @return String contenete la data attuale
	 */
	protected String getTodayDate() {
		return new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
	}
	
	/***********************************************************************
	 * Ottiene la data di oggi in forma dettagliata secondo il formato 
	 * yyyyMMddHHmmssSSS
	 * @return String
	 */
	protected String getFullTodayDate() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
	}
	

	/*********************************************************************
	 * Ottiene l'initialContext, se non esiste ne crea uno
	 * @return InitialContext
	 * @throws NamingException
	 */
	private InitialContext getInitialContext() throws NamingException {
		if ( initialContext == null ) {
			logger.debug ( "Ricerca Contesto locale" );
			initialContext = new InitialContext();
			logger.debug ( "Ottenuto contesto locale" );
		}
		return initialContext;
	}
	
	/*********************************************************************
	 * Ottiene il DataSource
	 * @return DataSource
	 * @throws NamingException
	 */
	protected DataSource getDataSource() throws NamingException {
		if ( dataSource == null ) {
			logger.debug ( "Necessaria inizializzazione del DataSource [" + configuration.getJDBCDataSourceName() + "]" );
			dataSource = (DataSource) getInitialContext().lookup(configuration.getJDBCDataSourceName());
		}
		return dataSource;
	}
	
	/*********************************************************************
	 * Restituisce True se la data risulta inserita False se tutti i campi risultano vuoti
	 * @param year String
	 * @param month String
	 * @param day String
	 * @return boolean
	 */
	protected boolean isSelected(String year,String month,String day){
		if(!year.equals("")&&!month.equals("")&&!day.equals(""))
			return true;
		return false;
	}
	
	
	protected final void sendValidations ( HttpServletRequest request, HttpServletResponse response, AllValidationBeans bean, String targetPage, boolean requestedSlash ) throws IOException, ServletException {
		request.setAttribute(ERRORBEAN, bean );
		forward( targetPage, request, response, requestedSlash );		
	}
	
	protected final void sendValidations ( HttpServletRequest request, HttpServletResponse response, AllValidationBeans bean, String targetPage ) throws IOException, ServletException {
		sendValidations( request, response, bean, targetPage, true);
	}

	/************************************************************************************
	 * Inserisce il Bean con tutte le eccezioni di validazione rilevate ed 
	 * effettua il forward alla pagina indicata
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param bean AllValidationBeans
	 * @param page String
	 * @param currentException Exception
	 * @throws IOException
	 * @throws ServletException
	 */
	protected final void sendValidations ( HttpServletRequest request, HttpServletResponse response, AllValidationBeans bean, String page, Exception currentException ) throws IOException, ServletException {
		request.setAttribute(ERRORBEAN, bean );
		forward( page, request, response );
	}
	
	protected void ricostruisci(TableBean tabella){
		
	}
	/*************************************************************************
	 * Indica se sia stato effetuato un refresh o meno
	 * @param request HttpServletRequest
	 * @return boolean
	 */
	protected boolean isRefresh(HttpServletRequest request){
		
			int sessionValue = 0;
			int requestValue = 0;
			try{
				Object attr = request.getSession().getAttribute(checkIfOK);
				if (attr != null) {
					sessionValue = Integer.parseInt(attr.toString());
				}
				String paramVal = request.getParameter(checkIfOK);
				if (paramVal != null) {
					requestValue = Integer.parseInt(paramVal);
				}
			}catch (Exception e) {
				logger.warn("Il parametro checkIfOK non esiste");
			}
			if(requestValue == sessionValue + 1){
				request.getSession().removeAttribute(checkIfOK);
				request.removeAttribute(checkIfOK);
				request.getSession().setAttribute(checkIfOK, new Integer(requestValue % 4000000));
				return false;
			}
			else {
				return true;
			
			}
		}

	/*******************************************************
	 * Ottine i dati relativi alla Gara
	 * @return InfoGaraBean
	 */
	public InfoGaraBean getDatiGara(HttpSession sess) {
		InfoGaraBean datiGara = null;
		try{
			datiGara =(InfoGaraBean) sess.getAttribute("dati_gara");
		}catch (Exception e) {
			logger.fatal(e.getMessage());
			//e.printStackTrace();
		}
		return datiGara;
	}

	/********************************************************
	 * Imposta i dati della Gara
	 * @param datiGara InfoGaraBean
	 * @param sess HttpSession
	 */
	public void setDatiGara(InfoGaraBean datiGara, HttpSession sess) {
		try{
			sess.removeAttribute("dati_gara");
			sess.setAttribute("dati_gara", datiGara);
		}catch (Exception e) {
			//nothing here
		}
		
	
		
	}	
	
	//TICKET ALM #3835
	/********************************************************
	 * Imposta la data di creazione della gara
	 * @param String dataCreazione
	 * @param sess HttpSession
	 */
	public void setDataCreazione(String dataCreazione, HttpSession sess) {
		try{
			sess.removeAttribute("data_creazione");
			sess.setAttribute("data_creazione", dataCreazione);
		}catch (Exception e) {
			//nothing here
		}
		
	
		
	}	
	
	public void setDelega(String delega, HttpServletRequest request) {
		try{
			request.setAttribute("isDelegante", delega);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*******************************************************
	 * Imposta i dati relativi all'aggiudicazione
	 * @param request HttpServletRequest
	 */
	public void setDatiAggiudicazione(HttpServletRequest request){
			
			try{
				long idAggiudicazione = Long.parseLong(request.getParameter(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE));
				Timestamp dataInizioAggiudicazione = PageHelper.parseTime(request.getParameter(PSBD.DATA_INIZIO_AGGIUDICAZIONE));
				
				if(dataInizioAggiudicazione != null){
					InfoGaraBean igb = getDatiGara(request.getSession());
					igb.setIdAggiudicazione(idAggiudicazione);
					igb.setDataInizioAggiudicazione(dataInizioAggiudicazione);
					setDatiGara(igb, request.getSession());
				}
			}catch (Exception e) {
				
			}
			
			
	}
	
	
	/********************************************************
	 * Ottiene il NavigationBean per la gestione del flusso 
	 * delle schede 
	 * @param idAggiudicazione Long
	 * @param tab String
	 * @param sess HttpSession
	 * @return NavigationBean
	 */
	protected NavigationBean getNavBean(Long idAggiudicazione, String tab, HttpSession sess){
		if(idAggiudicazione == null || idAggiudicazione < 1)
			return new NavigationBean();
		//************************ gestione flusso schede    ***************/
		HashMap<String, LinkedHashMap<String, NavigationBean>> navMap =(HashMap<String, LinkedHashMap<String, NavigationBean>>) sess.getAttribute("navigationMap");
		
		if (navMap == null)
			return new NavigationBean();
		
		LinkedHashMap<String, NavigationBean> currentMap = (LinkedHashMap<String, NavigationBean>) navMap.get(String.valueOf(idAggiudicazione));
		if(currentMap == null)
			return new NavigationBean();
		return currentMap.get(tab); 
	}
	/**
	 * metodo creato per poter fare il forward della query string che punta alla pagina
	 * attuale, in modo da poter fare il \"back\" nella pagina di dettaglio della gara.
	 * Al momento l'unica cosa da memorizzare &egrave; la paginazione, i parametri della 
	 * ricerca dovrebbero essere in sessione
	 * 
	 * @param request
	 * @return
	 */
	public String getQueryString(HttpServletRequest request){
		String queryString = request.getQueryString();
		if(queryString == null || "".equals(queryString)){
			String action = request.getParameter(ACTION_GET_LIST);
			int startRow = 0;
			boolean actionNeeded = action != null;
			if ( actionNeeded ) {
				String startRowS = request.getParameter(START_ROW);
				startRow = Integer.parseInt(startRowS);
				if ( action.equalsIgnoreCase(REGRESS) ) {
					startRow = startRow - configuration.getMaxElementiPerPagina();					
				} else {
					startRow = startRow + configuration.getMaxElementiPerPagina();
				}
				queryString = ACTION_GET_LIST + "=" + action+ "&" + START_ROW + "="+startRow;
			}else{queryString = "";}
		}return queryString;
	}	
	
   public boolean storicoHasInvito(List<PubblicazioneBean> storico){
      boolean hasInvito = false;
      if(storico!=null && !storico.isEmpty()){
         for(PubblicazioneBean pub : storico){
            if(pub.getTipoOperazione().equals(PubblicazioneBean.TipoOperazione.LETTINV.getCodice()))
               hasInvito = true;
         }
      }
      return hasInvito;
   }	
}	
