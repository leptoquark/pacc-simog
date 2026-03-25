package it.avlp.simog.common.servlet;

public interface ParametriServletRubrica {
	
	public static final String JSP_GESTIONE_RUBRICA = "rubrica.jsp";
	//gm aggiunto per ditte ausiliarie
	public static final String JSP_GESTIONE_RUBRICA_DITTA_AUSILIARIA = "rubricaDittaAusiliaria.jsp";
	public static final String JSP_RUBRICA_DITTA_AUSILIARIA_POPUP= "popupRubricaDittaAusiliaria.jsp";
	public static final String JSP_RUBRICA_DITTA_RAGGRUPPAMENTO_POPUP= "popupRubricaRaggruppamento.jsp";
	public static final String JSP_RUBRICA_DITTA_RAGGRUPPAMENTO_POPUP_SUB= "popupRubricaRaggruppamentoSub.jsp";//TICKET ALM - 3.04.3 #4198
	public static final String JSP_GESTIONE_RUBRICA_RESPONSABILI = "rubricaResponsabili.jsp";
	public static final String JSP_DETTAGLIO_RUBRICA = "dettaglioPartecipante.jsp";
	public static final String JSP_DETTAGLIO_RUBRICA_RESPONSABILI = "dettaglioResponsabile.jsp";
	public static final String JSP_DETTAGLIO_RUBRICA_POPUP= "popupRubricaInsert.jsp";
	public static final String JSP_DETTAGLIO_RUBRICA_RES_POPUP= "popupRubricaInsertRes.jsp";
	public static final String JSP_RUBRICA_POPUP= "popupRubrica.jsp";
	public static final String JSP_DETTAGLIO_RUBRICA_RES_POPMOD= "popupRubricaModRes.jsp";
	public static final String JSP_DETTAGLIO_RUBRICA_RES_POPMODOE= "popupRubricaModOE.jsp";
	public static final String TAB_RUBRICA = "rubAggiudInc";
	public static final String TAB_INCARICATI = "rubAggiudInc";
	public static final String TAB_RESPONSABILI = "rubAggiudInc";
	public static final String FIELD_NAME_DENOMINAZIONE = "denominazione";
	public static final String FIELD_NAME_ID_SOGGETTO_PARTECIPANTE = "idPartecipante";
	public static final String FIELD_NAME_ID_SOGGETTO_RESPONSABILE = "idResponsabile";
	
	public static final String FIELD_NAME_DATA_INIZIO_SOGGETTO = "dataInizioSogg";
	public static final String FIELD_NAME_DATA_FINE_SOGGETTO = "dataFineSogg";
	
	public static final String FIELD_NAME_DENOMINAZIONE_RIC = "denominazioneRic";
	public static final String FIELD_NAME_CODICE_FISCALE_RIC = "codiceFiscaleRic";
	
	public static final String FIELD_NAME_PARTITA_IVA = "partitaIVA";
	public static final String FIELD_NAME_INDIRIZZO = "indirizzo";
	public static final String FIELD_NAME_CIVICO = "civico";
	public static final String FIELD_NAME_CAP = "CAP";
	public static final String FIELD_NAME_CITTA = "CITTA";
	public static final String FIELD_NAME_PROVINCIA = "PROVINCIA";
	public static final String FIELD_NAME_COGNOME = "COGNOME";
	public static final String FIELD_NAME_NOME = "NOME";
	// nuovo
	public static final String FIELD_NAME_COMUNE_ISTAT = "COMUNE_ISTAT";

	public static final String FIELD_NAME_DATA_INIZIO_SOGGETTO_AAAA = "AAAAdataInizioSogg"; 
	public static final String FIELD_NAME_DATA_FINE_SOGGETTO_AAAA = "AAAAdataFineSogg";
	public static final String FIELD_NAME_DATA_INIZIO_SOGGETTO_MM = "MMdataInizioSogg";
	public static final String FIELD_NAME_DATA_FINE_SOGGETTO_MM = "MMdataFineSogg";
	public static final String FIELD_NAME_DATA_INIZIO_SOGGETTO_DD = "DDdataInizioSogg";
	public static final String FIELD_NAME_DATA_FINE_SOGGETTO_DD = "DDdataFineSogg";	
	public static final String FIELD_NAME_CODICE_FISCALE = "codiceFiscale";
	public static final String FIELD_NAME_CAMERA_COMMERCIO = "cameraCommercio";
	public static final String FIELD_NAME_CF_RAPPRESENTANTE = "cfRappresentante";
	public static final String FIELD_NAME_CF_RESPONSABILE = "cfResponsabile";
	public static final String FIELD_NAME_TELEFONO = "telefono";
	public static final String FIELD_NAME_EMAIL = "email";
	public static final String FIELD_NAME_FAX = "fax";
	
	
	public static final String OPERAZIONE = "operazione";

	public static final String FIELD_NAME_ID_STATO = "ID_STATO";
	public static final String FIELD_NAME_FLAG_ESTERI = "FLAG_ESTERO";
	
	//GM NUOVO CODICE OPERATORI ECONOMICI ED INCARICATI PER PRESTAZIONI
	public static final String RUBRICA_INCARICATI = "Rubrica Incaricati";
	public static final String RUBRICA_OPERATORE_ECONOMICO = "Rubrica Operatore Economico";
}
