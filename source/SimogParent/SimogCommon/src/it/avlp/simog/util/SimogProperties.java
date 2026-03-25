package it.avlp.simog.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.apache.log4j.Logger;

import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.exception.SimogException;

public class SimogProperties extends Properties {

	private static final long serialVersionUID = 1L;

	private static final String LOCAL_AUTH = "LOCAL";
	private static final String JDBC_DRIVER = "JDBC_DRIVER";
	private static final String JDBC_STRING = "JDBC_STRING";
	private static final String SIMOG_IDENTIFIER = "SIMOG_IDENTIFIER";
	private static final String WS_AUTH_TARGET_HOST = "WS_AUTH_TARGET_HOST";
	private static final String TABELLE_AGGIORNAMENTO_HISTORY = "TABELLE_AGGIORNAMENTO_HISTORY";
	private static final String TABELLE_AGGIORNAMENTO_DIR = "TABELLE_AGGIORNAMENTO_DIR";
	private static final String ESATTORE_TO_SIMOG_DIR = "ESATTORE_TO_SIMOG_DIR";
	private static final String SIMOG_TO_ESATTORE_DIR = "SIMOG_TO_ESATTORE_DIR";
	private static final String APP_ROOT_DIR = "APP_ROOT_DIR";
	private static final String LOG_CONFIG_FILENAME = "LOG_CONFIG_FILENAME";
	private static final String JDBC_DATASOURCE_NAME = "JDBC_DATASOURCE_NAME";
	private static final String ELEMENTI_VISUALIZZATI = "ELEMENTI_VISUALIZZATI";
	private static final String DIMENSIONE_DOCUMENTO_MAX = "DIMENSIONE_DOCUMENTO_MAX";
	private static final String ABILITAZIONE_DOCUMENTI = "ABILITAZIONE_DOCUMENTI";
	private static final String DURATA_SESSIONE = "DURATA_SESSIONE";
	private static final String DBMS = "DBMS";
	private final static String IMPORTO_LOTTO_AUX = "IMPORTO_LOTTO_AUX";
	private final static String IMPORTO_MIN_LOTTO = "IMPORTO_MIN_LOTTO";
	private final static String IMPORTO_MIN_LOTTO_B2 = "IMPORTO_MIN_LOTTO_B2";
	private final static String AMBIENTE = "AMBIENTE";
	private final static String CONTRIBUTO_URL = "CONTRIBUTO_URL";
	private final static String GIORNI_SCADENZA_ALLEGATI = "GIORNI_SCADENZA_ALLEGATI";

	// isREQUISITIActive
	private static final String DATA_ATTIVAZIONE_REQUISITI = "DATA_ATTIVAZIONE_REQUISITI";

	// is3028_RFWEBGL08Active
	private static final String GIORNI_PUBB_SCADENZA = "GIORNI_PUBB_SCADENZA";

	// is3031_RFWEBGL02Active
	private static final String DATA_ATTIVAZIONE_CUP = "DATA_ATTIVAZIONE_CUP";
	private static final String DIPE_URL = "DIPE_URL";
	private static final String DIPE_USER = "DIPE_USER";
	private static final String DIPE_PWD = "DIPE_PWD";
	private static final String DIPE_TIMEOUT = "DIPE_TIMEOUT";
	private static final String DATA_ATTIVAZIONE_CUPWS = "DATA_ATTIVAZIONE_CUPWS";
	private static final String RGS_URL_PROP = "RGS_URL";
	
	//3.04.9 MEV 40610
	private static final String QUALIFICAZIONE_URL = "QUALIFICAZIONE_URL";
	private static final String QUALIFICAZIONE_IS_QUALIFICATA = "QUALIFICAZIONE_IS_QUALIFICATA";
	private static final String QUALIFICAZIONE_RESPONSE = "QUALIFICAZIONE_RESPONSE";
	private static final String QUALIFICAZIONE_IS_MOCK = "QUALIFICAZIONE_IS_MOCK";

	//3.04.15 MAD 61769
	private static final String CIG_PCP_URL = "CIG_PCP_URL";
	
		
	private BigDecimal importo_min_lotto_b2 = new BigDecimal("0.000");
	private BigDecimal importo_min_lotto = new BigDecimal("0.000");
	private String importo_lotto_aux = null;
	private String ambiente = "";
	private String abilitazioneDocumenti = "N";
	private String dbms = ParametriServlet.MSSQL;
	private Logger logger = null;
	private String contributoUrl = null;
	private String giorniAllegati = null;

	private long max_file_size = -1L;

	// isREQUISITIActive
	private String dataRequisiti = Costanti.DATA_ATTIVAZIONE_REQUISITI;

	// is3028_RFWEBGL08Active
	private long giorni_pubb_scadenza = Long.MAX_VALUE;

	// is3028_RFWEBGL07Active
	private static final String WS_AVCPASS_HOST = "WS_AVCPASS_HOST";

	// is3031_ESCL_AVCPASS
	private static final String DATA_ATTIVAZIONE_ESCL_AVCPASS = "DATA_ATTIVAZIONE_ESCL_AVCPASS";
	private String dataEsclAvcpass = "20140101";

	// is3031_RFWEBGL02Active
	private String dataAttivazioneCup = Costanti.DATA_MAX; // in assenza, considero attivata nel futuro
	private String DIPEUrl;
	private String DIPEUser;
	private String DIPEPwd;
	private long DIPETimeout = 24 * 365; // un anno
	private String dataAttivazioneCupWs = Costanti.DATA_MAX; // in assenza, considero attivata nel futuro

	private String RGS_URL = "";

	public String getRGS_URL() {
		return RGS_URL;
	}

	public void setRGS_URL(String rGS_URL) {
		RGS_URL = rGS_URL;
	}
	
	//3.04.9 MEV 40610
	private String QUALIFICAZIONEurl;
	
	//3.04.15 MAD 61769
	private String cigPcpUrl;

	/**
	 * variabile dalla quale si prende il valore per la paginazione dei log e delle
	 * richieste annullamento
	 */
	private static final int paginazioneLogAndRichA = 20;

	// PP aggiunto per controllo sessione
	private static Integer durataSessione = new Integer(-1);

	private String fileConfigName = null;

	private static final String trustStore = "trustStore";
	private static final String trustStorePassword = "trustStorePassword";

	/*
	 * costanti e variabili per autenticazione SAML
	 */

	public static final String SAML_CERTIFICATE = "SAML_CERTIFICATE";
	public static final String SAML_LOGIN_URL = "SAML_LOGIN_URL";
	public static final String SAML_LOGOUT_URL = "SAML_LOGOUT_URL";
	public static final String SAML_PROFILE_URL = "SAML_PROFILE_URL";

	/*
	 * costanti per ClamAv
	 */
	public static final String CLAMAV_HOST = "CLAMAV_HOST";
	public static final String CLAMAV_PORT = "CLAMAV_PORT";
	public static final String CLAMAV_TIMEOUT = "CLAMAV_TIMEOUT";

	/*
	 * costanti per WS anagrafe
	 */
	public static final String WSANAG_USER = "WSANAG_USER";
	public static final String WSANAG_PWD = "WSANAG_PWD";
	public static final String WSANAG_URL = "WSANAG_URL";

	private String samlCertificate = null;
	private String samlLoginUrl = null;
	private String samlLogoutUrl = null;
	private String samlProfileUrl = null;

	private String clamHost = null;
	private String clamPort = null;
	private String clamTO = null;

	private String wsAnagUser = null;
	private String wsAnagPwd = null;
	private String wsAnagUrl = null;

	public static final String PATH_ALLEGATI = "PATH_ALLEGATI";
	private String pathAllegati = null;

	// UN is3030_RFWEBGL00Active
	public static final String CODICI_PROCEDURA_RISTRETTA = "CODICI_PROCEDURA_RISTRETTA";
	private String codiciProceduraRistretta = "";

	// INT85
	private static final String DATA_ATTIVAZIONE_BLOCCOCIG = "DATA_ATTIVAZIONE_BLOCCOCIG";
	private String dataBloccoCig = Costanti.DATA_MAX;

	// INT87
	private static final String DATA_ATTIVAZIONE_DL133 = "DATA_ATTIVAZIONE_DL133";
	private String dataDL133 = Costanti.DATA_MAX;

	// is30350_RFWEBGL01Active
	private static final String DATA_ATTIVAZIONE_ENTIAGGWEB = "DATA_ATTIVAZIONE_ENTIAGGWEB";
	private String dataAttivEaggWeb = Costanti.DATA_MAX;
	private static final String DATA_ATTIVAZIONE_ENTIAGGWS = "DATA_ATTIVAZIONE_ENTIAGGWS";
	private String dataAttivEaggWS = Costanti.DATA_MAX;
	private static final String LINK_ENTIAGG = "LINK_ENTIAGG";
	private String linkEntiagg = "";

	// is3042
	private static final String DATA_ATTIVAZIONE_3042 = "DATA_ATTIVAZIONE_3042";
	private String dataAttivazione3042 = Costanti.DATA_MAX;
	private static final String DATA_ATTIVAZIONE_ULTIMAZIONE_LAVORI = "DATA_ATTIVAZIONE_ULTIMAZIONE_LAVORI";
	private String dataAttUltimazioneLavori = Costanti.DATA_MAX;

	// is3043
	private static final String DATA_ATTIVAZIONE_3043 = "DATA_ATTIVAZIONE_3043";
	private String dataAttivazione3043 = Costanti.DATA_MAX;

	// is3044
	private static final String DATA_ATTIVAZIONE_3044 = "DATA_ATTIVAZIONE_3044";
	private String dataAttivazione3044 = Costanti.DATA_MAX;
	private static final String CF_SA_ESCLUSE_SOGG_AGG = "CF_SA_ESCLUSE_SOGG_AGG";
	private String cfSaEscluseSoggAgg = "";
	private static final String DATA_OBBLIGHI_COMUNICATIVI_SPECIALI = "DATA_OBBLIGHI_COMUNICATIVI_SPECIALI";
	private String dataObblighiComunicativiSpeciali = "";
	private static final String GENERACIG_DB = "GENERACIG_DB";
	private String generaCIGDB = "";
	private static final String ATTIVAZIONE_SOGG_AGGR = "ATTIVAZIONE_SOGG_AGGR";
	private String attivazioneSoggAggr = "";
	private static final String ELEMENTI_EXPORT = "ELEMENTI_EXPORT";

	// is3045
	private static final String DATA_ATTIVAZIONE_3045 = "DATA_ATTIVAZIONE_3045";
	private String dataAttivazione3045 = Costanti.DATA_MAX;
	private static final String URL_WS_AUSA = "URL_WS_AUSA";
	private String urlWsAusa = "";

	private static final String DATA_SBLOCCA_CANTIERI = "DATA_SBLOCCA_CANTIERI";
	private String dataSbloccaCantieri = "20190618";
	private static final String ESCLUSI_SOGLIA_ANOMALIA = "ESCLUSI_SOGLIA_ANOMALIA";
	private String cfEsclusiSogliaAnomalia = "";

	// is30451
	private static final String DATA_ATTIVAZIONE_30452 = "DATA_ATTIVAZIONE_30452";
	private String dataAttivazione30452 = Costanti.DATA_MAX;

	private static final String DATA_ATTIVAZIONE_3046 = "DATA_ATTIVAZIONE_3046";
	private String dataAttivazione3046 = Costanti.DATA_MAX;

	// is3045
	private static final String DATA_ATTIVAZIONE_3047 = "DATA_ATTIVAZIONE_3047";
	private String dataAttivazione3047 = Costanti.DATA_3047;

	private static final String ID_APPALTI_PUBBLICI = "ID_APPALTI_PUBBLICI";
	
	//MEV 39162 3.04.8.1
	private String dataAttivazione30481 = Costanti.DATA_30481;

	// TED
	private static final String USERNAME_TED = "USERNAME_TED";
	private String usernameTed;
	private static final String PWD_TED = "PWD_TED";
	private String pwdTed;
	private static final String XSD_TED = "XSD_TED";
	private String xsdTed;
	private static final String URL_TED = "URL_TED";
	private String urlTed;
	private static final String DATA_ATTIVAZIONE_PPP = "DATA_ATTIVAZIONE_PPP";
	private String dataAttivazionePPP;
	private static final String IDMODREAL_PPP = "IDMODREAL_PPP";
	private String idModRealPPP;
	private String idAppaltiPubblici;
	
	// MEV 37328 3.04.8.1
	private static final String OSSERVATORI_REGIONALI = "OSSERVATORI_REGIONALI";
	private String osservatoriRegionali = ""; 
	// FINE 
	
	// MEV 46181 3.04.11
	private static final String SOGGETTI_NON_BLOCCATI = "SOGGETTI_NON_BLOCCATI";
	private String soggettiNonBloccati = ""; 
	// FINE 
	
	//MEV 37010 3.04.8.1
	private static final String DATA_LINEEGUIDA_DEROGA_ADESIONE = "DATA_LINEEGUIDA_DEROGA_ADESIONE";
	private String dataLineeGuidaDerogaAdesione;
	private static final String DATA_ATTIVAZIONE_MEV37010 = "DATA_ATTIVAZIONE_MEV37010";
	private String dataAttivazioneMev37010;

	//MEV 38205 3.04.8.1
	private static final String SOGLIA_MEV_BIM = "SOGLIA_MEV_BIM";
	private BigDecimal sogliaMevBim;
	
	//3.04.9 MEV 40610
	private static final String ATTIVAZIONE_MEV_QUALIFICAZIONE_SA = "ATTIVAZIONE_MEV_QUALIFICAZIONE_SA";
	private String attivazioneMevQualificazioneSA;
	private String qualificazioneUrl;
	private String qualificazioneIsQualificata;
	private String qualificazioneResponse;
	private String qualificazioneIsMock;
	
	////3.04.11 MEV 44999
	private static final String ATTIVAZIONE_BLOCCO_CIG = "ATTIVAZIONE_BLOCCO_CIG";
	private static String attivazioneBloccoCig;
	
	//3.04.13 MAD 56200
	private static final String AVVISO_PAGINA_LOGIN = "AVVISO_PAGINA_LOGIN";
	private static String avvisoPaginaLogin;
	private static final String AVVISO_CUP_CPV = "AVVISO_CUP_CPV";
	private static String avvisoCupCpv;
	
		
	// SINGLETON
		public final static String DEF_CONFIG = "/opt/SIMOG/simog.ini";
		public final static String DEF_CONFIG_TEST = "C:\\Users\\ISC144\\git\\SIMOG_WEB_feature_3415\\Configurazioni\\simog.ini";
//		public final static String DEF_CONFIG_TEST = "C:\\home\\SIMOG\\simog.ini";


	
	private static SimogProperties sp = null;

	public synchronized static SimogProperties createInstance(String configFileName, Logger logger2) {
		if (sp == null) {
			try {
				sp = new SimogProperties(configFileName, logger2);

			} catch (Exception se) {
				logger2.fatal("errore durante l'instanziazione di SimogProperties: " + se.getMessage());
				se.printStackTrace();
				throw new RuntimeException(se);
			}
		}
		return sp;
	}

	private SimogProperties() {
	}

	/**************************************************************************************
	 * Metodo statico synchronized per la generazione del ConfigurationManager come
	 * Singleton senza logger ne indicazione del file di configurazione, normalmente
	 * non dovrebbe essere chiamata perchè le classi iniziali devono chiamare la
	 * createInstance()
	 * 
	 * @return ConfigurationManager
	 * @throws SimogWSException
	 */
	public synchronized static SimogProperties getInstance() {
		if (sp == null) {
			try {
				if (new File(DEF_CONFIG).exists())
					sp = new SimogProperties(DEF_CONFIG, null);
				else
					sp = new SimogProperties(DEF_CONFIG_TEST, null);

			} catch (SimogException se) {
				System.err.println("ERROR: errore durante l'instanziazione di SimogProperties: " + se.getMessage());
				se.printStackTrace();
				throw new RuntimeException(se);
			}
		}

		if (sp.logger == null)
			System.err.println("ERROR: SimogProperties instanziata con logger a null! verificare le classi chiamanti");

		return sp;
	}

	/********************************************************************************
	 * Costruttore
	 * 
	 * @param fileConfigName String
	 * @param logger         Logger
	 * @throws SimogException
	 */
	private SimogProperties(String fileConfigName, Logger logger) throws SimogException {
		this.logger = logger;
		this.fileConfigName = fileConfigName;
		reload(fileConfigName);
	}

	public Object put(Object chiave, Object valore) {
		if (logger != null)
			logger.debug("Chiave [" + chiave + "] valore [" + valore + "]");
		return super.put(chiave, valore);
	}

	/***************************************************************************************************
	 * Effettua il reload dei parametri iniziali dal file di configurazione.
	 * 
	 * @param path String
	 * @throws SimogException
	 */
	public void reload(String path) throws SimogException {

		FileInputStream fis = null;
		String buffer = "";

		try {
			fis = new FileInputStream(fileConfigName);
			load(fis);
			buffer = getProperty(IMPORTO_LOTTO_AUX);
			if (buffer != null)
				importo_lotto_aux = buffer;
			buffer = getProperty(DBMS);
			if (buffer != null)
				dbms = buffer;
			buffer = getProperty(ABILITAZIONE_DOCUMENTI);
			if (buffer != null)
				abilitazioneDocumenti = buffer;

			buffer = getProperty(AMBIENTE);
			if (buffer != null)
				ambiente = buffer.toUpperCase();

			buffer = getProperty(WS_AUTH_TARGET_HOST);
			if (buffer != null)
				ambiente += LOCAL_AUTH.equalsIgnoreCase(buffer) ? " - Autenticazione Locale" : "";

			buffer = getProperty(DIMENSIONE_DOCUMENTO_MAX);
			if (buffer != null)
				max_file_size = Long.parseLong(buffer);

			buffer = getProperty(IMPORTO_MIN_LOTTO);
			if (buffer != null)
				importo_min_lotto = (new BigDecimal(buffer));

			buffer = getProperty(IMPORTO_MIN_LOTTO_B2);
			if (buffer != null)
				importo_min_lotto_b2 = (new BigDecimal(buffer));

			buffer = getProperty(DURATA_SESSIONE);
			if (buffer != null)
				durataSessione = Integer.parseInt(buffer);

			buffer = getProperty(SAML_CERTIFICATE);
			if (buffer != null)
				samlCertificate = buffer;
			buffer = getProperty(SAML_LOGIN_URL);
			if (buffer != null)
				samlLoginUrl = buffer;
			buffer = getProperty(SAML_LOGOUT_URL);
			if (buffer != null)
				samlLogoutUrl = buffer;
			buffer = getProperty(SAML_PROFILE_URL);
			if (buffer != null)
				samlProfileUrl = buffer;

			buffer = getProperty(CLAMAV_HOST);
			if (buffer != null)
				clamHost = buffer;
			buffer = getProperty(CLAMAV_PORT);
			if (buffer != null)
				clamPort = buffer;
			buffer = getProperty(CLAMAV_TIMEOUT);
			if (buffer != null)
				clamTO = buffer;

			buffer = getProperty(PATH_ALLEGATI);
			if (buffer != null)
				pathAllegati = buffer;

			buffer = getProperty(WSANAG_USER);
			if (buffer != null)
				wsAnagUser = buffer;

			buffer = getProperty(WSANAG_PWD);
			if (buffer != null)
				wsAnagPwd = buffer;

			buffer = getProperty(WSANAG_URL);
			if (buffer != null)
				wsAnagUrl = buffer;

			buffer = getProperty(CONTRIBUTO_URL);
			if (buffer != null)
				contributoUrl = buffer;

			buffer = getProperty(GIORNI_SCADENZA_ALLEGATI);
			if (buffer != null)
				giorniAllegati = buffer;

			buffer = getProperty(DATA_ATTIVAZIONE_REQUISITI);
			if (buffer != null)
				dataRequisiti = buffer;

			// is3028_RFWEBGL08Active
			buffer = getProperty(GIORNI_PUBB_SCADENZA);
			if (buffer != null)
				giorni_pubb_scadenza = (new Long(buffer));

			// UN is3030_RFWEBGL00Active
			buffer = getProperty(CODICI_PROCEDURA_RISTRETTA);
			if (buffer != null)
				codiciProceduraRistretta = buffer;

			buffer = getProperty(DATA_ATTIVAZIONE_ESCL_AVCPASS);
			if (buffer != null)
				dataEsclAvcpass = buffer;

			buffer = getProperty(DATA_ATTIVAZIONE_CUP);
			if (buffer != null)
				dataAttivazioneCup = buffer;
			
			//3.04.9 MEV 40610
			buffer = getProperty(QUALIFICAZIONE_URL);
			if (buffer != null)
				setQualificazioneUrl(buffer);
			
			//3.04.9 MEV 40610
			buffer = getProperty(CIG_PCP_URL);
			if (buffer != null)
				setCigPcpUrl(buffer);
			
			buffer = getProperty(DATA_ATTIVAZIONE_CUPWS);
			if (buffer != null)
				dataAttivazioneCupWs = buffer;

			buffer = getProperty(DIPE_URL);
			if (buffer != null)
				DIPEUrl = buffer;
			buffer = getProperty(DIPE_USER);
			if (buffer != null)
				DIPEUser = buffer;
			buffer = getProperty(DIPE_PWD);
			if (buffer != null)
				DIPEPwd = buffer;
			buffer = getProperty(DIPE_TIMEOUT);
			if (buffer != null)
				DIPETimeout = (new Long(buffer));

			buffer = getProperty(RGS_URL_PROP);
			if (buffer != null)
				RGS_URL = buffer;

			// INT85
			buffer = getProperty(DATA_ATTIVAZIONE_BLOCCOCIG);
			if (buffer != null)
				dataBloccoCig = buffer;

			// INT87
			buffer = getProperty(DATA_ATTIVAZIONE_DL133);
			if (buffer != null)
				dataDL133 = buffer;

			// isINT87_RFSIMOGWEB01Active
			buffer = getProperty(DATA_ATTIVAZIONE_ENTIAGGWEB);
			if (buffer != null)
				dataAttivEaggWeb = buffer;
			buffer = getProperty(DATA_ATTIVAZIONE_ENTIAGGWS);
			if (buffer != null)
				dataAttivEaggWS = buffer;
			buffer = getProperty(LINK_ENTIAGG);
			if (buffer != null)
				linkEntiagg = buffer;

			// is3042
			buffer = getProperty(DATA_ATTIVAZIONE_3042);
			if (buffer != null)
				dataAttivazione3042 = buffer;

			buffer = getProperty(DATA_ATTIVAZIONE_ULTIMAZIONE_LAVORI);
			if (buffer != null)
				dataAttUltimazioneLavori = buffer;

			// is3043
			buffer = getProperty(DATA_ATTIVAZIONE_3043);
			if (buffer != null)
				dataAttivazione3043 = buffer;

			// is3044
			buffer = getProperty(DATA_ATTIVAZIONE_3044);
			if (buffer != null)
				dataAttivazione3044 = buffer;
			// is3047
			buffer = getProperty(DATA_ATTIVAZIONE_3047);
			if (buffer != null)
				dataAttivazione3047 = buffer;

			buffer = getProperty(CF_SA_ESCLUSE_SOGG_AGG);
			if (buffer != null)
				cfSaEscluseSoggAgg = buffer;

			buffer = getProperty(DATA_OBBLIGHI_COMUNICATIVI_SPECIALI);
			if (buffer != null)
				dataObblighiComunicativiSpeciali = buffer;

			buffer = getProperty(GENERACIG_DB);
			if (buffer != null)
				generaCIGDB = buffer;

			buffer = getProperty(ATTIVAZIONE_SOGG_AGGR);
			if (buffer != null)
				attivazioneSoggAggr = buffer;

			buffer = getProperty(DATA_ATTIVAZIONE_3045);
			if (buffer != null)
				dataAttivazione3045 = buffer;

			buffer = getProperty(URL_WS_AUSA);
			if (buffer != null)
				urlWsAusa = buffer;
			buffer = getProperty(URL_WS_AUSA);
			if (buffer != null)
				urlWsAusa = buffer;

			buffer = getProperty(ESCLUSI_SOGLIA_ANOMALIA);
			if (buffer != null)
				cfEsclusiSogliaAnomalia = buffer;

			buffer = getProperty(DATA_ATTIVAZIONE_30452);
			if (buffer != null)
				dataAttivazione30452 = buffer;

			buffer = getProperty(DATA_ATTIVAZIONE_3046);
			if (buffer != null)
				dataAttivazione3046 = buffer;

			buffer = getProperty(USERNAME_TED);
			if (buffer != null)
				usernameTed = buffer;

			buffer = getProperty(PWD_TED);
			if (buffer != null)
				pwdTed = buffer;

			buffer = getProperty(XSD_TED);
			if (buffer != null)
				xsdTed = buffer;

			buffer = getProperty(URL_TED);
			if (buffer != null)
				urlTed = buffer;

			buffer = getProperty(DATA_ATTIVAZIONE_PPP);
			if (buffer != null)
				dataAttivazionePPP = buffer;

			buffer = getProperty(ID_APPALTI_PUBBLICI);
			if (buffer != null)
				idAppaltiPubblici = buffer;

			buffer = getProperty(IDMODREAL_PPP);
			if (buffer != null)
				idModRealPPP = buffer;
			
			//MEV 37010 3.04.8.1
			buffer = getProperty(DATA_LINEEGUIDA_DEROGA_ADESIONE);
			if (buffer != null)
				dataLineeGuidaDerogaAdesione = buffer;
			
			buffer = getProperty(DATA_ATTIVAZIONE_MEV37010);
			if (buffer != null)
				dataAttivazioneMev37010 = buffer;

			//FINE MEV 37010 3.04.8.1
			
			//MEV 38205 3.04.8.1
			buffer = getProperty(SOGLIA_MEV_BIM);
			if (buffer != null)
				sogliaMevBim = BigDecimal.valueOf(Double.valueOf(buffer));
			//MEV 38205 3.04.8.1
			
			//MEV 37328 3.04.8.1
			buffer = getProperty(OSSERVATORI_REGIONALI);
			if (buffer != null)
				osservatoriRegionali = buffer;
			
			// MEV 46181 3.04.11
			buffer = getProperty(SOGGETTI_NON_BLOCCATI);
			if (buffer != null)
				soggettiNonBloccati = buffer;
			
			//3.04.9 MEV 40610
			buffer = getProperty(ATTIVAZIONE_MEV_QUALIFICAZIONE_SA);
			if (buffer != null)
				attivazioneMevQualificazioneSA = buffer;
			
			buffer = getProperty(QUALIFICAZIONE_IS_MOCK);
			if (buffer != null)
				setQualificazioneIsMock(buffer);
			
			buffer = getProperty(QUALIFICAZIONE_RESPONSE);
			if (buffer != null)
				setQualificazioneResponse(buffer);
			
			buffer = getProperty(QUALIFICAZIONE_IS_QUALIFICATA);
			if (buffer != null)
				setQualificazioneIsQualificata(buffer);
			
			//3.04.11 MEV 44999
			buffer = getProperty(ATTIVAZIONE_BLOCCO_CIG);
			if (buffer != null)
				attivazioneBloccoCig = buffer;
			
			//3.04.13 MAD 56200
			buffer = getProperty(AVVISO_PAGINA_LOGIN);
			if (buffer != null)
				avvisoPaginaLogin = buffer;
			
			buffer = getProperty(AVVISO_CUP_CPV);
			if (buffer != null)
				avvisoCupCpv = buffer;

		} catch (IOException ioe) {
			String message = "Errore nella lettura dei parametri iniziali";
			if (logger != null)
				logger.fatal(message, ioe);
			throw new SimogException(message);
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException io) {
			}
		}
	}

	/**
	 * is3031_WEBGL02Active verifica se è attiva la nuova logica CUP tiene conto
	 * dell ambiente web o WS/ML in cui gira la classe
	 * 
	 * @return
	 */
	public boolean isCUPAttivo() {
		return (SimogFlags.isFromWeb()
				&& PageHelper.getFormattedDBDate(PageHelper.getCurrentDate()).compareTo(this.dataAttivazioneCup) >= 0)
				|| (SimogFlags.isFromWS() && PageHelper.getFormattedDBDate(PageHelper.getCurrentDate())
						.compareTo(this.dataAttivazioneCupWs) >= 0);
	}

	/**
	 * is3031_WEBGL02Active verifica se la competenza per la gestione del CUP è di
	 * aggiudicazione o lotto in base alla data di creazione della gara
	 * 
	 * @return
	 */
	public boolean isCUPLotto(String dataCreazione) {
		return PageHelper.getFormattedDBDate(dataCreazione).compareTo(this.dataAttivazioneCup) >= 0;
	}

	/*******************************************************************************
	 * Carica i dati di configurazione dal BufferedInputStream passato in ingresso
	 * 
	 * @param fis1 BufferedInputStream
	 * @throws SimogException
	 */
//	public void myReload(BufferedInputStream fis1) throws SimogException {
//		
//		String buffer = "";
//
//		try {
//			load(fis1);
//			buffer = getProperty(IMPORTO_LOTTO_AUX);
//			if (buffer != null)
//				importo_lotto_aux = buffer;
//			buffer = getProperty(DBMS);
//			if (buffer != null)
//				dbms = buffer;
//			buffer = getProperty("ABILITAZIONE_DOCUMENTI");
//			if (buffer != null)
//				abilitazioneDocumenti = buffer; 
//			buffer = getProperty(WS_AUTH_TARGET_HOST);
//			if (buffer != null)
//				ambiente = LOCAL_AUTH.equalsIgnoreCase(buffer) ? " - Autenticazione Locale" : ""; 
//			
//			buffer = getProperty(DIMENSIONE_DOCUMENTO_MAX);
//			if (buffer != null)
//				max_file_size = Long.parseLong(buffer);
//			
//			buffer = getProperty(IMPORTO_MIN_LOTTO);
//			if (buffer != null)
//				importo_min_lotto =  (new BigDecimal(buffer));
//			
//			buffer = getProperty(IMPORTO_MIN_LOTTO_B2);
//			if (buffer != null)
//				importo_min_lotto_b2 =  (new BigDecimal(buffer));
//		} catch (IOException ioe) {
//			String message = "Errore nella lettura dei parametri iniziali"; 
//			logger.fatal ( message, ioe );
//			throw new SimogException ( message ); 
//		} finally {
//			try {
//				if(fis1 != null)	fis1.close();
//			} catch (IOException io) {}
//		}	
//	}	
//

	public String getProperty(String param) {
		String paramValue = super.getProperty(param);
		if (paramValue == null) {
			if (logger != null)
				logger.info("Parametro [" + param + "] non inizializzato");
		}
		// logger.debug("getting Param [" + param + "] value [" + paramValue + "]");
		return paramValue;
	}

	/**
	 * @return String the dB_Driver.
	 */
	public String getJDBCDriver() {
		return getProperty(JDBC_DRIVER);
	}

	/**
	 * @return String the JDBC String Connection.
	 */
	public String getJDBCString() {
		return getProperty(JDBC_STRING);
	}

	public String getTrustorePath() {
		return getProperty(trustStore);
	}

	public String getTrustStorePassword() {
		return getProperty(trustStorePassword);
	}

	/**
	 * @return String the logFileName.
	 */
	public String getLogFileName() {
		return getProperty(LOG_CONFIG_FILENAME);
	}

	/**
	 * @return String la directory contenente i file XML
	 */

	public String getAppRoot() {
		return getFixedDirectory(APP_ROOT_DIR);
	}

	/**
	 * @return String la directory contenente i file XML da inviare al sistema
	 *         ESATTORE
	 */

	public String getSimogToEsattoreDir() {
		return getFixedDirectory(SIMOG_TO_ESATTORE_DIR);
	}

	/**
	 * @return String la directory contenente i file XML da inviare al sistema SIMOG
	 */

	public String getEsattoreToSimogDir() {
		return getFixedDirectory(ESATTORE_TO_SIMOG_DIR);
	}

	/**
	 * @return String la directory contenente i file XML elaborati dal sistema SIMOG
	 */

	public String getHistoryDir() {
		return getFixedDirectory("HISTORY_DIR");
	}

	public String getAggiornamentoTabelleDir() {
		return getProperty(TABELLE_AGGIORNAMENTO_DIR);
	}

	private String getFixedDirectory(String name) {
		String currentFieldValue = getProperty(name);
		if (currentFieldValue != null) {
			if (!currentFieldValue.endsWith("/")) {
				currentFieldValue += "/";
			}
		}
		return currentFieldValue;
	}

	/**
	 * @return String the sIMOG_IDENTIFIER.
	 */
	public String getSIMOG_IDENTIFIER() {
		return getProperty(SIMOG_IDENTIFIER);
	}

	/**
	 * @return String the wS_AUTH_TARGET_HOST.
	 */
	public String getWS_AUTH_TARGET_HOST() {
		return getProperty(WS_AUTH_TARGET_HOST);
	}

	public String getAggiornamentoTabelleDirHistory() {
		return getProperty(TABELLE_AGGIORNAMENTO_HISTORY);
	}

	public String getJDBCDataSourceName() {
		return getProperty(JDBC_DATASOURCE_NAME);
	}

	/**
	 * @return String the ELEMENTI_VISUALIZZATI
	 */
	public String getELEMENTI_VISUALIZZATI() {
		return getProperty(ELEMENTI_VISUALIZZATI);
	}

	public int getELEMENTI_EXPORT() {
		return getProperty(ELEMENTI_EXPORT) != null ? Integer.parseInt(getProperty(ELEMENTI_EXPORT)) : -1;
	}

	public int getMaxElementiPerPagina() {
		int maxElementiPerPagina = -1;
		if (getELEMENTI_VISUALIZZATI() != null) {
			maxElementiPerPagina = Integer.parseInt(getELEMENTI_VISUALIZZATI());
		}
		return maxElementiPerPagina;
	}

	/**
	 * @return int the max_file_size
	 */
	public int getMax_file_size() {
		return Integer.parseInt(getProperty(DIMENSIONE_DOCUMENTO_MAX));
	}

	public boolean isDocumentiAbilitato() {
		return Costanti.FLAG_VALORE_SI.equalsIgnoreCase(abilitazioneDocumenti);
	}

	public BigDecimal getImportoMinLotto() {
		return importo_min_lotto;
	}

	public BigDecimal getImportoMinLottoB2() {
		return importo_min_lotto_b2;
	}

	public String getDBMS() {
		return dbms;
	}

	public Integer getDurataSessione() {
		return durataSessione;
	}

//	public void setDurataSessione(Integer durataSessione) {
//		SimogProperties.durataSessione = durataSessione;
//	}
	/**
	 * metodo che serve a ritornare un parametro non preso dal simog ini, serve alla
	 * paginazione dei log e delle richieste annullamento
	 * 
	 * @return
	 */
	public int getPaginazioneLogAndRichA() {
		return paginazioneLogAndRichA;
	}

	/***
	 * ritorna tru se l'autenticazione � locale
	 */
	public boolean isLocalAuth() {
		return LOCAL_AUTH.compareToIgnoreCase(this.getWS_AUTH_TARGET_HOST()) == 0;
	}

	/***
	 * ritorna tru se l'autenticazione � locale
	 */
	public static boolean isLocalAuth(String target) {
		return LOCAL_AUTH.compareToIgnoreCase(target) == 0;
	}

	public String getSamlCertificate() {
		return samlCertificate;
	}

	public String getSamlLoginUrl() {
		return samlLoginUrl;
	}

	public String getSamlLogoutUrl() {
		return samlLogoutUrl;
	}

	public String getSamlProfileUrl() {
		return samlProfileUrl;
	}

	public String getClamHost() {
		return clamHost;
	}

	public String getClamPort() {
		return clamPort;
	}

	public String getClamTO() {
		return clamTO;
	}

	public String getPathAllegati() {
		return pathAllegati;
	}

	public String getWsAnagPwd() {
		return wsAnagPwd;
	}

	public String getWsAnagUser() {
		return wsAnagUser;
	}

	public String getWsAnagUrl() {
		return wsAnagUrl;
	}

	public String getContributoUrl() {
		return contributoUrl;
	}

	public String getGiorniAllegati() {
		return giorniAllegati;
	}

	public String getDataRequisiti() {
		return dataRequisiti;
	}

	public long getGiorni_pubb_scadenza() {
		return giorni_pubb_scadenza;
	}

	public String getWS_AVCPASS_HOST() {
		return getProperty(WS_AVCPASS_HOST);
	}

	public String getCodiciProceduraRistretta() {
		return codiciProceduraRistretta;
	}

	public String getDataEsclAvcpass() {
		return dataEsclAvcpass;
	}

	public String getDataAttivazioneCup() {
		return this.dataAttivazioneCup;
	}

	public String getDataAttivazioneCupWs() {
		return dataAttivazioneCupWs;
	}

	public String getDIPEUrl() {
		return DIPEUrl;
	}

	public String getDIPEUser() {
		return DIPEUser;
	}

	public String getDIPEPwd() {
		return DIPEPwd;
	}

	public long getDIPETimeout() {
		return DIPETimeout;
	}

	public String getImporto_lotto_aux() {
		return importo_lotto_aux;
	}

	public String getAmbiente() {
		return ambiente;
	}

	// INT85
	public String getDataBloccoCig() {
		return dataBloccoCig;
	}

	/**
	 * restituisce true se la data corrente è maggiore o uguale alla data di
	 * attivazione della MEV INT85
	 * 
	 * @return
	 */
	public boolean isINT85Attivo() {
		return PageHelper.getCurrentDate().compareTo(this.dataBloccoCig) >= 0;
	}

	// INT87
	public String getDataDL133() {
		return dataBloccoCig;
	}

	/**
	 * restituisce true se la data di riferimento (formato YYYYMMDD) è maggiore o
	 * uguale alla data di attivazione della MEV INT87
	 * 
	 * @param dataRif
	 * @return
	 */
	public boolean isINT87Attivo(String dataRif) {
		return dataRif.compareTo(this.dataDL133) >= 0;
	}

	/**
	 * restituisce true se la data di riferimento (data creazione gara) è maggiore o
	 * uguale alla data di attivazione della mev
	 * 
	 * @param datarif
	 * @return
	 */
	public boolean isSAINT85(String datarif) {
		return datarif.compareTo(getDataBloccoCig()) >= 0;
	}

	/**
	 * is3031_WEBGL02Active verifica se è attiva la nuova logica ENTI AGGREGATORI
	 * tiene conto dell ambiente web o WS/ML in cui gira la classe
	 * 
	 * @return
	 */
	public boolean isEAGGAttivo(String datarif) {
		return (SimogFlags.isFromWeb() && datarif.compareTo(this.dataAttivEaggWeb) >= 0)
				|| (SimogFlags.isFromWS() && datarif.compareTo(this.dataAttivEaggWS) >= 0);
	}

	public String getFileConfigName() {
		return fileConfigName;
	}

	public String toStringa() {

		String out = "";

		SimogProperties sf = getInstance();

		for (Method field : sf.getClass().getDeclaredMethods()) {
			field.setAccessible(true);
			String name = field.getName();
			Object value = "";
			if ("get".equals(name.substring(0, 3)) || "is".equals(name.substring(0, 2))) {
				try {
					value = field.invoke(sf, null);
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out = out.concat("$1: $2<br>".replace("$1", name).replace("$2", value.toString()));
			}
		}

		return out;

	}

	public String getLinkEntiagg() {
		return linkEntiagg;
	}

	public void setLinkEntiagg(String linkEntiagg) {
		this.linkEntiagg = linkEntiagg;
	}

//is3042
	public String getDataAttivazione3042() {
		return dataAttivazione3042;
	}

	// is3043
	public String getDataAttivazione3043() {
		return dataAttivazione3043;
	}

	// is3044
	public String getDataAttivazione3044() {
		return dataAttivazione3044;
	}

	// is3045
	public String getDataAttivazione3045() {
		return dataAttivazione3045;
	}

	// is3047
	public String getDataAttivazione3047() {
		return dataAttivazione3047;
	}

	// is30452
	public String getDataAttivazione30452() {
		return dataAttivazione30452;
	}

	// is3046
	public String getDataAttivazione3046() {
		return dataAttivazione3046;
	}

	public long getDataAttivazione3042Timestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			return sdf.parse(dataAttivazione3042).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public long getDataAttivazione3044Timestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			return sdf.parse(dataAttivazione3044).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public long getDataAttivazione3045Timestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			return sdf.parse(dataAttivazione3045).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public long getDataSbloccaCantieriTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			return sdf.parse(dataSbloccaCantieri).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public boolean isDataCreatedAfter3043(String datarif) {
		return datarif == null || datarif.equals("") || datarif.compareTo(getDataAttivazione3043()) >= 0;
	}

	public long getDataAttivazione3043Timestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			return sdf.parse(dataAttivazione3043).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public boolean isDataCreatedAfter3042(String datarif) {
		return datarif == null || datarif.equals("") || datarif.compareTo(getDataAttivazione3042()) >= 0;
	}

	public boolean isDataCreatedAfter3047(String dataGara) {
		return dataGara == null || dataGara.equals("") || dataGara.compareTo(getDataAttivazione3047()) > 0;
	}

	//MEV 37010 3.04.8.1
	public boolean isDataCreatedBefore3047(String dataGara) {
//		logger.info("DATA ATTIVAZIONE 3047 " + getDataAttivazione3047());
		return dataGara == null || dataGara.equals("") || dataGara.compareTo(getDataAttivazione3047()) < 0;
	}
	//MEV 37010 3.04.8.1
		
	public String getDataAttivazioneUltLavori() {
		return dataAttUltimazioneLavori;
	}

	public String getDataObblighiComunicativiSpeciali() {
		return dataObblighiComunicativiSpeciali;
	}

	public boolean isDataAfterObblighiComunicativiSpeciali(String datarif) {
		return datarif.compareTo(getDataObblighiComunicativiSpeciali()) >= 0;
	}

	public boolean isDataUltLavoriCreatedAfter3042(String datarif) {
		return datarif.compareTo(getDataAttivazioneUltLavori()) >= 0;
	}

	public boolean isDataCreatedAfter3044(String datarif) {
		return datarif == null || datarif.equals("") || datarif.compareTo(getDataAttivazione3044()) >= 0;
	}

	// is3044
	public String getDataAttivazioneSoggAggr() {
		return attivazioneSoggAggr;
	}

	public boolean isDataCreatedAfterSoggAggr(String datarif) {
		return datarif == null || datarif.equals("") || datarif.compareTo(getDataAttivazioneSoggAggr()) >= 0;
	}

	// TICKET ALM #4223 - 3.04.4
	public String getCfSaEscluseSoggAggStr() {
		return cfSaEscluseSoggAgg;
	}

	public String[] getCfSaEscluseSoggAggArray() {
		return cfSaEscluseSoggAgg == null || "".equals(cfSaEscluseSoggAgg) ? new String[0]
				: cfSaEscluseSoggAgg.split(";");
	}

	public boolean isCfEsclusa(String cfsa_input) {
		String[] cfEscluse = getCfSaEscluseSoggAggArray();
		for (String cfesclusa : cfEscluse) {
			if (cfsa_input.equals(cfesclusa))
				return true;
		}
		return false;
	}

	public boolean getCIGFromDB() {
		return generaCIGDB != null && !generaCIGDB.isEmpty() && "S".equals(generaCIGDB);
	}
	// FINE TICKET ALM #4223 - 3.04.4

	public boolean isDataCreatedAfter3045(String datarif) {
		return datarif == null || datarif.equals("") || datarif.compareTo(getDataAttivazione3045()) >= 0;
	}

	public boolean isDataCreatedAfter30452(String datarif) {
		return datarif == null || datarif.equals("") || datarif.compareTo(getDataAttivazione30452()) >= 0;
	}

	public boolean isDataCreatedAfter3046(String datarif) {
		return datarif == null || datarif.equals("") || datarif.compareTo(getDataAttivazione3046()) >= 0;
	}

	public String getUrlWsAusa() {
		return urlWsAusa;
	}

	public String getCfEsclusiSogliaAnomalia() {
		return cfEsclusiSogliaAnomalia;
	}

	public String[] getArrayCfEsclusiSogliaAnomalia() {
		return getCfEsclusiSogliaAnomalia().split(",");
	}

	//MEV 34187 3.04.8
	public boolean isOssEsclusaSogliaAnomalia(String idOssGara) {
		boolean isOssEsclusa = false;
		String[] arrayCfEscluse = SimogProperties.getInstance().getArrayCfEsclusiSogliaAnomalia();
		for (int i = 0; i < arrayCfEscluse.length; i++) {
			if (idOssGara.equals(arrayCfEscluse[i]))
				isOssEsclusa = true;
		}

		return isOssEsclusa;
	}

	public String getUsernameTed() {
		return usernameTed;
	}

	public void setUsernameTed(String usernameTed) {
		this.usernameTed = usernameTed;
	}

	public String getPwdTed() {
		return pwdTed;
	}

	public void setPwdTed(String pwdTed) {
		this.pwdTed = pwdTed;
	}

	public String getXsdTed() {
		return xsdTed;
	}

	public void setXsdTed(String xsdTed) {
		this.xsdTed = xsdTed;
	}

	public String getUrlTed() {
		return urlTed;
	}

	public void setUrlTed(String urlTed) {
		this.urlTed = urlTed;
	}

	public String getDataAttivazionePPP() {
		return dataAttivazionePPP;
	}

	public String getIdModRealPPP() {
		return idModRealPPP;
	}

	public String getIdAppaltiPubblici() {
		return idAppaltiPubblici;
	}
	
	//MEV 37010 3.04.8.1
	public String getDataLineeGuidaDerogaAdesione() {
		return dataLineeGuidaDerogaAdesione;
	}
	
	public boolean isDataCreatedAfterDerogaAdesione(String datarif) {
		return datarif == null || datarif.equals("") || datarif.compareTo(getDataLineeGuidaDerogaAdesione()) >= 0;
	}
	
	public boolean isDataCreatedBeforeDerogaAdesione(String datarif) {
		return datarif == null || datarif.equals("") || datarif.compareTo(getDataLineeGuidaDerogaAdesione()) < 0;
	}
	
	public String getDataAttivazioneMev37010() {
		return dataAttivazioneMev37010;
	}
	
	public boolean isDataCreatedAfterAttivazioneMev37010(String datarif) {
		return datarif == null || datarif.equals("") || datarif.compareTo(getDataAttivazioneMev37010()) >= 0;
	}
	
	public boolean isDataCreatedBeforeAttivazioneMev37010(String datarif) {
		return datarif == null || datarif.equals("") || datarif.compareTo(getDataAttivazioneMev37010()) < 0;
	}
	//MEV 37010 3.04.8.1
	
	//3.04.9 MEV 40610
	public String getAttivazioneMevQualificazioneSA() {
		return attivazioneMevQualificazioneSA;
	}
	
	public boolean isDataCreatedAfterAttivazioneMevQualificazioneSA(String datarif) {
		return datarif == null || datarif.equals("") || datarif.compareTo(getAttivazioneMevQualificazioneSA()) >= 0;
	}
	
	public boolean isDataCreatedBeforeAttivazioneMevQualificazioneSA(String datarif) {
		return datarif == null || datarif.equals("") || datarif.compareTo(getAttivazioneMevQualificazioneSA()) < 0;
	}
	//fine 3.04.9 MEV 40610

	//MEV 38205 3.04.8.1
	public BigDecimal getSogliaMevBim() {
		return sogliaMevBim;
	}

	public void setSogliaMevBim(BigDecimal sogliaMevBim) {
		this.sogliaMevBim = sogliaMevBim;
	}

	//MEV 38205 3.04.8.1
	
	//MEV 37328 3.04.8.1
	public String getOsservatoriRegionali() {
		return osservatoriRegionali;
	}
	
	public String[] getArrayOsservatoriRegionali() {
		return getOsservatoriRegionali().split(",");
	}
	
	// MEV 46181 3.04.11
	public String getSoggettiNonBloccati() {
		return soggettiNonBloccati;
	}
	
	public String[] getArraySoggettiNonBloccati() {
		return getSoggettiNonBloccati().split(",");
	}
	
	//MAD 56200 3.04.13
	public String getAvvisoPaginaLogin() {
		return avvisoPaginaLogin;
	}	
	
	public String getAvvisoCupCpv() {
		return avvisoCupCpv;
	}
	
	//MEV 37328 - 3.04.8.1 FASE 2
	public boolean isOsservatorioRegionaleCompetente(String idOssGara) {
		boolean isOssCompetente = false;
		String[] arrayOssIncluso = SimogProperties.getInstance().getArrayOsservatoriRegionali();
		for (int i = 0; i < arrayOssIncluso.length; i++) {
			if (idOssGara.equals(arrayOssIncluso[i]))
			{
				isOssCompetente = true;
				break;
			}
			 
		}

		return isOssCompetente;
	}
	//FINE MEV 37328
	
	// MEV 46181 3.04.11
	public boolean isSoggettoNonBloccato(String cfAmmGara) {
		boolean isSoggNonBloccato = false;
		String[] arraySoggIncluso = SimogProperties.getInstance().getArraySoggettiNonBloccati();
		for (int i = 0; i < arraySoggIncluso.length; i++) {
			if (cfAmmGara.equals(arraySoggIncluso[i]))
			{
				isSoggNonBloccato = true;
				break;
			}
			 
		}

		return isSoggNonBloccato;
	}
	//FINE MEV


	//MEV 39162 3.04.8.1
	public String getDataAttivazione30481() {
		return dataAttivazione30481;
	}

	public void setDataAttivazione30481(String dataAttivazione30481) {
		this.dataAttivazione30481 = dataAttivazione30481;
	}
	
	public boolean isDataCreatedAfter30481(String dataGara) {
		return dataGara == null || dataGara.equals("") || dataGara.compareTo(getDataAttivazione30481()) > 0;
	}
	//FINE MEV 39162 3.04.8.1
	
	//3.04.9 MEV 40610
	public String getQUALIFICAZIONEurl() {
		return QUALIFICAZIONEurl;
	}

	public void setQUALIFICAZIONEurl(String qUALIFICAZIONEurl) {
		QUALIFICAZIONEurl = qUALIFICAZIONEurl;
	}

	public String getQualificazioneUrl() {
		return qualificazioneUrl;
	}

	public void setQualificazioneUrl(String qualificazioneUrl) {
		this.qualificazioneUrl = qualificazioneUrl;
	}
	
	//3.04.15 MAD 61769
	public String getCigPcpUrl() {
		return cigPcpUrl;
	}

	public void setCigPcpUrl(String cigPcpUrl) {
		this.cigPcpUrl = cigPcpUrl;
	}

	public String getQualificazioneIsQualificata() {
		return qualificazioneIsQualificata;
	}

	public void setQualificazioneIsQualificata(String qualificazioneIsQualificata) {
		this.qualificazioneIsQualificata = qualificazioneIsQualificata;
	}

	public String getQualificazioneResponse() {
		return qualificazioneResponse;
	}

	public void setQualificazioneResponse(String qualificazioneResponse) {
		this.qualificazioneResponse = qualificazioneResponse;
	}

	public String getQualificazioneIsMock() {
		return qualificazioneIsMock;
	}

	public void setQualificazioneIsMock(String qualificazioneIsMock) {
		this.qualificazioneIsMock = qualificazioneIsMock;
	}
	
	//3.04.11 MEV 44999
	public static String getAttivazioneBloccoCig() {
		return attivazioneBloccoCig;
	}
	
	public static boolean isDataAfterAttivazioneBloccoCig(String datarif) {
		return datarif == null || datarif.equals("") || datarif.compareTo(getAttivazioneBloccoCig()) >= 0;
	}
	
	public boolean isDataBeforeAttivazioneBloccoCig(String datarif) {
		return datarif == null || datarif.equals("") || datarif.compareTo(getAttivazioneBloccoCig()) < 0;
	}
	//fine 3.04.11 MEV 44999
}
