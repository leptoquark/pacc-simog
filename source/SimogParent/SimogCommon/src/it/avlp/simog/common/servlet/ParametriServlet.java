package it.avlp.simog.common.servlet;

import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.db.generated.ALLEGATI;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.PUBBLICAZIONI;

public interface ParametriServlet {

	public final static String MESSAGGIO = "MESSAGGIO";

	public final static String IS_ORGANO = "IS_ORGANO";

	public final static String LOGOUT_URL = "LOGOUT_URL";
	public final static String CHANGEPROF_URL = "CHANGEPROF_URL";
	public final static String LOGIN_URL = "LOGIN_URL";

	public final static String DELEGHE_CIG = "deleghe_cig";

	public final static String SCELTA_PROFILO = "SCELTA_PROFILO_ATTR";
	public final static String SRV_SCELTA_PROFILO = "srvSceltaProfilo";
	public final static String SRV_AUTENTICAZIONE = "checkAuthentication";
	public final static String GARA_PUBBLICABILE = "GARA_PUBBLICABILE";
	public final static String BEAN_INFO_GARA = "BEAN_INFO_GARA";
	// nomi bean
	public final static String UTENTE = "UTENTE";
	public final static String SERVICE_AVAILABLE = "SERVICE_AVAILABLE";
	public final static String ERRORBEAN = "ERRORE";
	public final static String NEWSBEAN = "NEWS";
	public final static String TABLEBEAN = "TABLEBEAN";
	public final static String STORICOPARTECIPANTE = "STORICOPARTECIPANTE";
	public final static String STORICORESPONSABILE = "STORICORESPONSABILE";
	public final static String DATI_PREINSERT_TABLEBEAN = "DATI_PREINSERT_TABLEBEAN";
	public final static String DATI_COMUNI_TABLEBEAN = "DATI_COMUNI_TABLEBEAN";
	public final static String LISTA_AGGIUDICAZIONI_TABLEBEAN = "LISTA_AGGIUDICAZIONI_TABLEBEAN";
	public final static String DATI_AGGIUDICAZIONE_TABLEBEAN = "DATI_AGGIUDICAZIONE_TABLEBEAN";
	public final static String DATI_GARA_TABLEBEAN = "DATI_GARA_TABLEBEAN";
	public final static String CONDIZIONI_AGG_BEAN = "CONDIZIONI_AGG_BEAN";
	//MAD 3.04.9 MEV 40610
	public final static String NUM_LISTA_GARE = "NUM_LISTA_GARE";
	// TICKET ALM #3835
	public final static String CONDIZIONI_LOTTO_BEAN = "CONDIZIONI_LOTTO_BEAN";
	public final static String CONDIZIONI_LOTTO_SEL = "CONDIZIONI_LOTTO_SEL";
	// FINE TICKET ALM #3835
	public final static String DETTAGLIO_PARTECIPANTE_TABLEBEAN = "DETTAGLIO_PARTECIPANTE_TABLEBEAN";
	public final static String DATI_ENTE_CONTRATTO_TABLEBEAN = "DATI_ENTE_CONTRATTO_TABLEBEAN";
	public final static String LISTA_CPV = "CPV_LIST";

	public final static String FROM_GARE = "FROMGARE";
	public final static String FROM_RICERCA = "FROMRICERCA";

	public final static String checkIfOK = "checkIfOK";

	public final static String ALLEGATO1 = "ALLEGATO1";
	public final static String ALLEGATO2 = "ALLEGATO2";
	public final static String ALLEGATO3 = "ALLEGATO3";
	public final static String ALLEGATO_RETTIFICA = "ALLEGATO_RETTIFICA";
	public final static String ALLEGATO_AVVISO_AGGIUDICAZIONE = "ALLEGATO_AVVISO_AGGIUDICAZIONE";
	public final static String DESCPREF = "DESC";

	public final static String ALLEGATO1DESC = "ALLEGATO1" + DESCPREF;
	public final static String ALLEGATO2DESC = "ALLEGATO2" + DESCPREF;
	public final static String ALLEGATO3DESC = "ALLEGATO3" + DESCPREF;
	public final static String ALLEGATO_RETTIFICA_DESC = "ALLEGATO_RETTIFICA" + DESCPREF;
	public final static String ALLEGATO_AVVISO_AGGIUDICAZIONE_DESC = "ALLEGATO_AVVISO_AGGIUDICAZIONE" + DESCPREF;
	public final static String TIPODOC = "TIPODOC";
	public final static String RETFIELD = "RETFIELD";
	public final static String IDALLEGATO = "IDALLEGATO";
	public final static String NOMEFILE = "NOMEFILE";
	public final static String NOTEALL = "NOTEALL";
	public final static String DOCUMENTI = "DOCUMENTI";
	public final static String PRESENTE = "PRESENTE";
	public final static String TIPO_ALLEGATO = "TIPO_ALLEGATO";

	//MEV 34186
	public final static String PATH_FILE = "PATH_FILE";
	//FINE MEV 34186
		
//	public final static String DETTAGLIO_VARIAZIONI = "DETTAGLIO_VARIAZIONI";
	public static final String ACTION_AGGIUNGI_LOTTO = "creazioneLotto";
	public static final String ACTION_AGGIUNGI_GARA = "creazioneGara";

	// gm aggiunto per pubblicazione bando di gara
	public static final String ACTION_CARICA_GARA = "caricaGara";
	public static final String ACTION_SALVA_BANDO_GARA = "salvaBandoGara";
	public static final String ACTION_SALVA_ADESIONE_GARA = "salvaAdesioneGara";
	public static final String ACTION_VALIDA_PUBBLICAZIONE_GARA = "validaPubblicazioneGara";
	public static final String JSP_PUBBLICA_BANDO_GARA = "pubblicaBandoGara.jsp";
	public static final String JSP_POPUP_AVVISO_AGGIUDICAZIONE = "popupPubblicazione.jsp";
	public static final String SRV_AVVISO_AGGIUDICAZIONE = "avvisoAggiudicazione";
	public static final String SRV_BANDO_GARA = "bandoGara";
	public static final String PUBBLICAZIONE = "pubblicazione";
	public static final String TIPO_PUBBLICAZIONE = "tipo_pubblicazione";
	public static final String TIPO_OPERAZIONE = "tipo_operazione";
	public static final String SRV_GESTIONE_RETTIFICA = "gestioneRettifica";
	public static final String JSP_TRASMETTI_RETTIFICA = "trasmettiRettifica.jsp";
	public static final String PUBBLICAZIONE_BANDO_GARA = "pubblicazioneBando";
	public static final String PUBBLICAZIONE_LETT_INV = "pubblicazioneInvito";
	public static final String PUBBLICAZIONE_AVVISO = "pubblicazioneAvviso";
	public static final String PUBBLICAZIONE_RETTIFICA = "pubblicazioneRettifica";
	public static final String PUBBLICAZIONE_RETTIFICA_AVVISO_AGG = "pubblicazioneRettificaAvvisoAgg";
	public static final String PUBBLICAZIONE_PROCEDURA_RISTRETTA_COMPLETA = "pubblicazioneProceduraRistrettaCompleta";
	public static final String ACTION_CARICA_RETTIFICA = "caricaRettifica";
	public static final String ACTION_SALVA_RETTIFICA = "salvaRettifica";
	public static final String ACTION_CARICA_RETTIFICA_AVVISO = "caricaRettificaAvviso";
	public static final String ACTION_SALVA_RETTIFICA_AVVISO = "salvaRettificaAvviso";
	public static final String ACTION_CONFERMA_RETTIFICA_ADMIN = "confermaRettificaAdmin";
	public static final String ACTION_CONFERMA_RETTIFICA_AVVISO = "confermaRettifica";
	public static final String ACTION_CARICA_RETTIFICA_BANDO = "caricaRettificaBando";
	public static final String ACTION_MODIFICA_CONTRATTO_ESCLUSO = "modificaContrattoEscluso";
	public static final String ACTION_MODIFICA_RIPETIZIONI = "modificaRipetizioni";

	//MEV 3.04.10 43227
	public static final String ACTION_MODIFICA_DATI_PERFEZIONAMENTO = "modificaDatiPerfezionamento";
	public static final String ACTION_MODIFICA_CPV = "modificaCPV";
	public static final String DATA_SCADENZA_PAGAMENTI = "dataScadenzaPagamenti";
	public static final String ORA_SCADENZA = "oraScadenzaPagamenti";
	public static final String DATA_SCADENZA_RICHIESTA_INVITO = "dataScadenzaRichiestaInvito";
	public static final String MOSTRA_DATI_FASE_UNO = "mostraDatiFaseUno";
	public static final String MOSTRA_DATI_FASE_DUE = "mostraDatiFaseDue";
	//MEV 3.04.10 43227
	
	//MEV 3.04.8.1
	public static final String ACTION_INTEGRA_PARI_OPPORTNITA = "integraPariOpportunita";
	//MEV 37010 3.04.8.1
		
	// INT87
	public static final String ACTION_MODIFICA_DL133 = "modificaDL133";

	public static final String ACTION_CARICA_AVVISO = "caricaAvviso";
	public static final String ACTION_SALVA_AVVISO = "salvaAvviso";

	// is3030_RFWEBGL00Active
	public static final String ACTION_CARICA_INVITO = "caricaInvito";
	public static final String ACTION_SALVA_INVITO = "salvaInvito";
	public static final String JSP_PUBBLICAZIONE_INVITO = "pubblicazioneInvito.jsp";

	// Indica il separatore dei decimali provenienti dal DB
	public static final String DEC_SEPARATOR = ".";

	// queryString della paginazione per l'operazione di back
	public static final String STORIA_PAGINAZIONE = "STORIA_PAGINAZIONE";

	// JSP
	public static final String JSP_ERRORE = "errore.jsp";
	public static final String JSP__LOGIN = "login.jsp";
//	public static final String JSP_HOME = "home.jsp";
	public static final String JSP_INSERISCI_LOTTO = "InserisciLotto.jsp";
	public static final String JSP_LISTA_GARE = "ListaGare.jsp";
	public static final String JSP_VISUALIZZA_GARE_RSSA = "elencoGareRSSA.jsp";

	public static final String JSP_GESTIONE_GARE_EXT = "gestioneGareEXT.jsp";
	public static final String SRV_GESTIONE_GARE_EXT = "ricercaGareExt";

	public static final String JSP_GESTIONE_GARE_RSSA = JSP_ERRORE; // PP disabilitata "gestioneGareRSSA.jsp";
	public static final String SRV_GESTIONE_GARE_RSSA = JSP_ERRORE; // PP disabilitata "ricercaGare";

	public static final String JSP_GESTIONE_SCHEDE = "gestioneSchede.jsp";
	public static final String SRV_GESTIONE_SCHEDE = "ricercaGareRUP_CS";

	public static final String JSP_GESTIONE_AGGIUDICAZIONI = "viewAggiudicazioni.jsp";
	public static final String JSP_GESTIONE_AGGIUDICAZIONI_MULTILOTTO = "viewAggiudicazioniMultilotto.jsp";
	public static final String JSP_GESTIONE_TABELLE = "tabelleManager.jsp";
	public static final String JSP_DEBUG_VISUALIZZA_PARAMETRI = "visualizzaParametri.jsp";
	public static final String JSP_LISTA_INFORMAZIONI = "scheda1/listaInformazioni.jsp";
	public static final String JSP_SCHEDA_ADESIONE = "scheda1/schedaAdesione.jsp";
	public static final String JSP_SOTTOSOGLIA = "scheda1/schedaSottosoglia.jsp";
	public static final String JSP_ESCLUSI = "scheda1/schedaEsclusi.jsp";
	public static final String JSP_CONSULTA_LOG = "consultazioneLog.jsp";
	public static final String JSP_CONSULTA_LOG_OPERAZIONI = "consultaLogOperazioni.jsp";
	public static final String CONSULTA_LOG = "consultaLog";
	public static final String CONSULTA_LOG_OPERAZIONI = "consultaLogOperazioni";
	public static final String CONSULTA_RICH_ANN = "richiestaAnnullamento";

	public static final String JSP_NUOVA_GARA = "nuovaGara.jsp";
	public static final String JSP_CANCELLA_GARA = "cancellaGara.jsp";
	public static final String JSP_VISUALIZZA_DETTAGLIO_GARA = "visualizzaDettaglio.jsp";
	public static final String JSP_INSERT_DATI_COMUNI_AGGIUDICAZIONE = "insertDatiComuniAgg.jsp";
	public static final String JSP_MODIFICA_LOTTO = "modificaLotto.jsp";
	public static final String JSP_PERFEZIONA_LOTTO = "perfezionaLotto.jsp";
	public static final String JSP_CANCELLA_LOTTO = "cancellaLotto.jsp";
	public static final String JSP_VISUALIZZA_LOTTO = "visualizzaLotto.jsp";
	public static final String JSP_AVCP_HOME = "homeAVCP.jsp";
	public static final String JSP_AMM_HOME = "homeADMIN.jsp";
	public static final String JSP_RSSA_HOME = "homeRSSA.jsp";
	public static final String JSP_RUP_CS_HOME = "homeRUP.jsp";
	public static final String JSP_OSSREG_HOME = "homeOSR.jsp";
	public static final String JSP_VISUALIZZA_LOG = "visualizzaLog.jsp";
	public static final String JSP_VISUALIZZA_LOG_OPERAZIONI = "visualizzaLogOperazioni.jsp";
	public static final String JSP_VISUALIZZA_TRANSAZIONI = "visualizzaTransazioni.jsp";
	public static final String JSP_AGGIORNAMENTI_TABELLA_ELENCO = "aggiornamentiTabellaElenco.jsp";
	public static final String JSP_AGGIORNAMENTI_TABELLA_DETTAGLIO = "aggiornamentiTabellaDettaglio.jsp";
	public static final String JSP_AGGIORNAMENTI_STATO = "aggiornamentiListaUpload.jsp";
	public static final String JSP_RICERCA_TRANSAZIONI = "transazioniManager.jsp";
	public static final String JSP_GESTISCI_DOCUMENTI = "documentiManager.jsp";
	public static final String JSP_RICERCA_DOCUMENTI_LOTTO_ESITO = "RisultatoRicercaLotti.jsp";
	public static final String JSP_VISUALIZZA_LOTTI = "visualizzaLotti.jsp";
	public static final String JSP_RICERCA_DOCUMENTI_LOTTO = "RicercaPubblica.jsp";
	public static final String JSP_RICHIEDI_ANNULLAMENTO = "richiediAnnullamento.jsp";
	public static final String JSP_RICHIEDI_CANCELLAZIONE = "richiestaCancellazione.jsp";
	public static final String JSP_RICHIEDI_PRESAINCARICO = "richiediPresaInCarico.jsp";
	public static final String JSP_RICHIEDI_PRESAINCARICOGARA = "richiediPresaInCaricoGara.jsp";
	public static final String JSP_RICERCA_CPV = "ricercaCPV.jsp";

	public static final String JSP_PROFILO = "sceltaProfilo.jsp";

	public static final String JSP_GESTISCI_ALLEGATI = "popupAllegati.jsp";
	public static final String JSP_STORICO_ALLEGATI = "popupStoricoAllegati.jsp";

	// parametri di passaggio
	public static final String INSERIMENTO = "inserimento";
	public static final String MODIFICA = "modifica";
	public static final String PERFEZIONAMENTO = "perfezionamento";
	public static final String CANCELLAZIONE = "cancellazione";
	public static final String PERFEZIONAMENTO_SENZA_LOTTI = "perfezionamento_senza_lotti";
	public static final String CANCELLAZIONE_SENZA_LOTTI = "cancellazione_senza_lotti";
	public static final String PERFEZIONAMENTO_PROC_RISTRETTA_FASE1 = "perfezionamento_procedura_ristretta_fase1";
	public static final String PERFEZIONAMENTO_PROC_RISTRETTA_FASE2 = "perfezionamento_procedura_ristretta_fase2";
	public static final String PERFEZIONAMENTO_PROC_MISTA = "perfezionamento_procedura_mista";

	// Parametri inizializzazione
	public static final String LOG_CONFIG_FILENAME = "LOG_CONFIG_FILENAME";
	public static final String CONFIG_FILENAME = "CONFIG_FILENAME";
	public static final String TIPOLOGIA_BEAN = "TIPOLOGIA_BEAN";
	public static final String CATEGORIA_BEAN = "CATEGORIA_BEAN";
	public static final String CATEGORIA_SCORPORABILE_BEAN = "CATEGORIA_SCORPORABILE_BEAN";
	public static final String SCELTA_CONTRAENTE_BEAN = "SCELTA_CONTRAENTE_BEAN";
	public static final String CRITERI_AGGIUDICAZIONE_BEAN = "CA_BEAN";
	public static final String MOTIVO_DEROGA_BEAN = "MOTIVO_DEROGA_BEAN";
	public static final String MISURA_PREMIALE_BEAN = "MISURA_PREMIALE_BEAN";
	public static final String MODO_INDIZIONE_GARA = "MODO_INDIZIONE_BEAN";
	public static final String CATEGORIA_SA_BEAN = "CATEGORIA_SA_BEAN";
	public static final String TIPOLOGIA_SA_BEAN = "TIPOLOGIA_SA_BEAN";
	public static final String CATEGORIA_SCORPORABILE = "CATEGORIA_SCORPORABILE";
	public static final String TIPO_CATEGORIA_BEAN = "TIPO_CATEGORIA_BEAN";
	// public static final String TIPO_APPALTO_BEAN = "TIPO_APPALTO_BEAN";
	public static final String TIPO_APPALTO_BEAN_F = "TIPO_APPALTO_BEAN_F";
	public static final String TIPO_APPALTO_BEAN_L = "TIPO_APPALTO_BEAN_L";
	public static final String TIPO_PRESTAZIONE_BEAN = "TIPO_PRESTAZIONE_BEAN";
	public static final String CLASSI_IMPORTO_BEAN = "CLASSI_IMPORTO_BEAN";
	public static final String TIPO_AGGIUDICATARIO_BEAN = "TIPO_AGGIUDICATARIO_BEAN";
	public static final String RUOLI_RESPONSABILE_BEAN = "RUOLI_RESPONSABILE_BEAN";
	public static final String RUOLI_PRESTAZIONE_BEAN = "RUOLI_PRESTAZIONE_BEAN";
	public static final String TIPO_FINANZIAMENTO_BEAN = "TIPO_FINANZIAMENTO_BEAN";
	public static final String TIPO_STRUMENTO_BEAN = "TIPO_STRUMENTO_BEAN";

	public static final String MOTIVO_VCO_BEAN = "MOTIVO_VCO_BEAN";
	public static final String MOTIVO_RICH_BEAN = "MOTIVO_RICH_BEAN";

	public static final String NEXT_PAGE = "next";
	// Aggiunta della costante relativa alle motivazioni di una Sospensione
	public static final String MOTIVI_SOSPENSIONE_BEAN = "MOTIVI_SOSPENSIONE_BEAN";
	public static final String MOTIVI_INTERRUZIONE_BEAN = "MOTIVI_INTERRUZIONE_BEAN";

	public static final String MODO_REALIZZAZIONE = "MODO_REALIZZAZIONE_BEAN";
	public static final String ARTICOLO_ESCLUSIONE = "ART_ESCLUSIONE_BEAN";
	public static final String MODO_RIAGG_BEAN = "MODO_RIAGG_BEAN";

	public static final String FIELD_NAME_CREA_SCHEDA = "SCH_CREA_FLAG";

	// isINT87_RFSIMOGWEB01Active
	public static final String EAGG_MOTIVI_BEAN = "EAGG_MOTIVI_BEAN";
	public static final String EAGG_CATEGORIE_BEAN = "EAGG_CATEGORIE_BEAN";
	public static final String EAGG_CATEGSEL_BEAN = "EAGG_CATEGSEL_BEAN";

	/*
	 * I nomi dei campi vengono parametrizzati
	 */
	public static final String FIELD_NAME_NUMERO_LOTTI = "NUMERO_LOTTI";
	public static final String FIELD_NAME_DURATA_GIORNI = "DURATA_GIORNI";
	public static final String FIELD_NAME_DURATA_RINNOVI_RIPETIZIONI = "DURATA_RINNOVI_RIPETIZIONI";
	public static final String FIELD_NAME_DURATA_AFFIDAMENTO_IN_GIORNI = "DURATA_AFFIDAMENTO_IN_GIORNI";
	public static final String FIELD_NAME_ESCLUSO = "escluso";
	public static final String FIELD_NAME_ID_ESCLUSIONE = "id_esclusione";
	public static final String FIELD_NAME_SOMMA_URGENZA = "somma_urgenza";
	public static final String FIELD_NAME_MOTIVO_URGENZA = "motivo_urgenza";
	public static final String FIELD_NAME_TIPOLOGIA = "tipologia";
	public static final String FIELD_NAME_CPV = "cpv";
	public static final String FIELD_NAME_SCELTA_CONTRAENTE = AGGIUDICAZIONI.ID_SCELTA_CONTRAENTE;
	public static final String FIELD_NAME_CATEGORIA_PREVALENTE = "sel_categoria_prevalente";
	public static final String FIELD_NAME_CATEGORIA = "categoria";
	public static final String FIELD_NAME_OGGETTO_LOTTO = "oggetto_lotto";
	public static final String FIELD_NAME_OGGETTO_LOTTO_RIPROPOSIZIONE = "oggetto_lotto";
	public static final String FIELD_NAME_OGGETTO_GARA = "OGGETTOGARA";
	
	//3.04.9 MEV 40610
	public static final String FIELD_NAME_DEROGA_QUALIICAZIONE_SA = "sel_deroga_qualificazione_sa";
	public static final String FIELD_NAME_DEROGA_QUALIICAZIONE_SA_DESCR = "deroga_qualificazione_sa";
	public static final String DEROGA_QUALIFICAZIONE_SA_BEAN = "DEROGA_QUALIFICAZIONE_SA_BEAN";
	public static final String FIELD_NAME_DEROGA_QUALIICAZIONE_SA_LOTTO = "idDerogaQualificazione";
	public static final String DEROGA_QUALIFICAZIONE_SA_BEAN_SELECTED = "DEROGA_QUALIFICAZIONE_SA_BEAN_SELECTED";
	//fine 3.04.9 MEV 40610
	/**
	 * ADDED TO PREVENT OVERRIDE OF SESSION VARIABLES, SEARCH VS INSERT NEW LOTTO
	 */
	public static final String FIELD_NAME_OGGETTO_GARA_1 = FIELD_NAME_OGGETTO_GARA + "_1";
	public static final String FIELD_NAME_OGGETTO_GARA_RIPROPOSIZIONE = "OGGETTOGARA";

	public static final String FIELD_NAME_ID_STAZIONE_APPALTANTE = "idStazioneAppaltante";
	/**
	 * ADDED TO PREVENT OVERRIDE OF SESSION VARIABLES, SEARCH VS INSERT NEW LOTTO
	 */
	public static final String FIELD_NAME_ID_STAZIONE_APPALTANTE_1 = FIELD_NAME_ID_STAZIONE_APPALTANTE + "_1";

	public static final String FIELD_NAME_CIG_ACC_QUADRO = "CIGQUADRO";

	public static final String FIELD_NAME_ID_STAZIONE_APPALTANTE_RIPROPOSIZIONE = "idStazioneAppaltante";
	public static final String FIELD_NAME_DENOMINAZIONE_STAZIONE_APPALTANTE = "denominazioneStazioneAppaltante";
	public static final String FIELD_NAME_DENOMINAZIONE_AMMINISTRAZIONE = "denominazioneAmministrazione";
	public static final String FIELD_NAME_CF_AMMINISTRAZIONE = "cfAmministrazione";
	public static final String FIELD_NAME_CF_AMMINISTRAZIONE_RIPROPOSIZIONE = "cfAmministrazione";
	public static final String FIELD_NAME_CIG = "CIG";
	public static final String FIELD_NAME_CHECKMIE = "CHECKMIE";
	// X-XX: check cig completo
	public static final String FIELD_NAME_CIG_RIPROPOSIZIONE = "CIG_COMPLETO";
	public static final String FIELD_NAME_CIG_KKK = "cig_kkk";
	public static final String FIELD_NAME_CIG_PART = "cig_part";
	public static final String FIELD_NAME_CIG_CYCLE = "cig_cycle";
	public static final String FIELD_NAME_TABELLA_SERVIZIO = "TABELLA_SERVIZIO";
	public static final String FIELD_NAME_FILE_AGGIORNAMENTO = "txtNomeFile";
	public static final String FIELD_NAME_LOGIN = "login";
	public static final String FIELD_NAME_PASS = "pass";
	public static final String FIELD_NAME_IMPORTO_LOTTO_EURO = "importoLottoEuro";
	public static final String FIELD_NAME_IMPORTO_LOTTO_CENTESIMI = "importoLottoCentesimi";
	public static final String FIELD_NAME_RICHIESTA_ANNULLAMENTO = "richiestaAnnullamento";
	public static final String FIELD_NAME_RICHIESTA_ANNULLAMENTO_RIPROPOSIZIONE = "richiestaAnnullamentoR";
	public static final String FIELD_NAME_DATA_SCADENZA_AAAA_START = "AAAAdataScadenza_da";
	public static final String FIELD_NAME_DATA_SCADENZA_MM_START = "MMdataScadenza_da";
	public static final String FIELD_NAME_DATA_SCADENZA_DD_START = "DDdataScadenza_da";
	public static final String FIELD_NAME_DATA_SCADENZA = "scadenzaPubData";

	// PP is3025_RFWEBGL02Active
	public static final String FIELD_NAME_ORA_SCADENZA = "scadenzaPubOra";

	// UN is3030_RFWEBGL00Active
	public static final String FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO = "dataScadenzaRichiestaInvito";
	public static final String FIELD_NAME_DATA_LETTERA_INVITO = "dataLetteraInvito";

	public static final String FIELD_NAME_DATA_SCADENZA_AAAA_END = "AAAAdataScadenza_a";
	public static final String FIELD_NAME_DATA_SCADENZA_MM_END = "MMdataScadenza_a";
	public static final String FIELD_NAME_DATA_SCADENZA_DD_END = "DDdataScadenza_a";
	public static final String FIELD_NAME_RICHIESTA_AGGIUDICATE = "richiestaAggiudicate";
	public static final String FIELD_NAME_TABELLA_INFO = "FIELD_NAME_TABELLA_INFO";

	public static final String FIELD_NAME_IMPORTO_GARA_EURO = "importoGaraEuro";
	public static final String FIELD_NAME_IMPORTO_GARA_CENTESIMI = "importoGaraCentesimi";
	public static final String FIELD_NAME_ID_STATO_GARA = "ID_STATO_GARA";
	public static final String FIELD_NAME_DATA_CONFERMA_GARA = "DATA_CONFERMA_GARA";
	public static final String FIELD_NAME_DATA_CREAZIONE_GARA = "DATA_CREAZIONE_GARA";

	public static final String FIELD_NAME_DATA_LOG_AAAA_START = "FIELD_NAME_DATA_LOG_AAAA_START";
	public static final String FIELD_NAME_DATA_LOG_MM_START = "FIELD_NAME_DATA_LOG_MM_START";
	public static final String FIELD_NAME_DATA_LOG_DD_START = "FIELD_NAME_DATA_LOG_DD_START";
	public static final String FIELD_NAME_DATA_START_LOG = "startLogData";
	public static final String FIELD_NAME_DATA_LOG_AAAA_END = "FIELD_NAME_DATA_LOG_AAAA_END";
	public static final String FIELD_NAME_DATA_LOG_MM_END = "FIELD_NAME_DATA_LOG_MM_END";
	public static final String FIELD_NAME_DATA_LOG_DD_END = "FIELD_NAME_DATA_LOG_DD_END";
	public static final String FIELD_NAME_DATA_END_LOG = "endLogData";
	public static final String FIELD_NAME_DATA_PUBBLICAZIONE = "dataPubblicazione";
	public static final String FIELD_NAME_DATA_PUBBLICAZIONE_AAAA_START = "AAAAdataPubblicazione_da";
	public static final String FIELD_NAME_DATA_PUBBLICAZIONE_MM_START = "MMdataPubblicazione_da";
	public static final String FIELD_NAME_DATA_PUBBLICAZIONE_DD_START = "DDdataPubblicazione_da";
	public static final String FIELD_NAME_DATA_PUBBLICAZIONE_START = "dataPubblicazioneStart";
	public static final String FIELD_NAME_DATA_PUBBLICAZIONE_END = "dataPubblicazioneEnd";
	public static final String FIELD_NAME_DATA_PUBBLICAZIONE_AAAA_END = "AAAAdataPubblicazione_a";
	public static final String FIELD_NAME_DATA_PUBBLICAZIONE_MM_END = "MMdataPubblicazione_a";
	public static final String FIELD_NAME_DATA_PUBBLICAZIONE_DD_END = "DDdataPubblicazione_a";

	public static final String FIELD_NAME_DATA_SCADENZA_AAAA = "AAAAdataScadenza";
	public static final String FIELD_NAME_DATA_SCADENZA_MM = "MMdataScadenza";
	public static final String FIELD_NAME_DATA_SCADENZA_DD = "DDdataScadenza";

	public static final String FIELD_NAME_SCADENZA_START = "dataScadenzaStart";
	public static final String FIELD_NAME_SCADENZA_END = "dataScadenzaEnd";

	public static final String FIELD_NAME_DATA_PUBBLICAZIONE_AAAA = "AAAAdataPubblicazione";
	public static final String FIELD_NAME_DATA_PUBBLICAZIONE_MM = "MMdataPubblicazione";
	public static final String FIELD_NAME_DATA_PUBBLICAZIONE_DD = "DDdataPubblicazione";

	public static final String FIELD_NAME_ALBO_PRETORIO_AAAA = "ALBO_PRETORIO_AAAA";
	public static final String FIELD_NAME_ALBO_PRETORIO_MM = "ALBO_PRETORIO_MM";
	public static final String FIELD_NAME_ALBO_PRETORIO_DD = "ALBO_PRETORIO_DD";

	public static final String FIELD_NAME_GAZZETTA_UFFICIALE_CE_AAAA = "GAZZETTA_UFFICIALE_CE_AAAA";
	public static final String FIELD_NAME_GAZZETTA_UFFICIALE_CE_MM = "GAZZETTA_UFFICIALE_CE_MM";
	public static final String FIELD_NAME_GAZZETTA_UFFICIALE_CE_DD = "GAZZETTA_UFFICIALE_CE_DD";

	public static final String FIELD_NAME_GAZZETTA_UFFICIALE_RI_AAAA = "GAZZETTA_UFFICIALE_RI_AAAA";
	public static final String FIELD_NAME_GAZZETTA_UFFICIALE_RI_MM = "GAZZETTA_UFFICIALE_RI_MM";
	public static final String FIELD_NAME_GAZZETTA_UFFICIALE_RI_DD = "GAZZETTA_UFFICIALE_RI_DD";

	public static final String FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE_AAAA = "AAAAbur";
	public static final String FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE_MM = "MMbur";
	public static final String FIELD_NAME_BOLLETTINO_UFFICIALE_REGIONALE_DD = "DDbur";

	public static final String FIELD_NAME_CF_OPERATORE = "cfUtente";
	public static final String FIELD_NAME_CF_OPERATORE_RIPROPOSIZIONE = "cfUtente";

	public static final String FIELD_NAME_ID_INFO = "id_info";
	public static final String FIELD_NAME_IDLOTTO = "id_lotto";
	public static final String FIELD_NAME_DATA_INIZIO_INFO = "data_inizio_info";
	public static final String FIELD_NAME_DATA_FINE = "data_fine";

	public static final String S_FIELD_NAME_PROCEDURA_ACC = "SiProc_Acc";
	public static final String N_FIELD_NAME_PROCEDURA_ACC = "NoProc_Acc";

	public static final String S_FIELD_NAME_PREINFORMAZIONE = "SiPreinf";
	public static final String N_FIELD_NAME_PREINFORMAZIONE = "NoPreinf";

	public static final String S_FIELD_NAME_TERMINE_RIDOTTO = "SiTerm_rid";
	public static final String N_FIELD_NAME_TERMINE_RIDOTTO = "NoTerm_rid";

	// gm nuovi campi dati comuni
	public static final String FIELD_NAME_ID_TIPO_PROCEDURA = INFO_AGGIUDICAZIONI.ID_TIPO_PROCEDURA;
	public static final String FIELD_NAME_DURATA_CONVENZIONE = INFO_AGGIUDICAZIONI.DURATA_CONVENZIONE;
	public static final String FIELD_NAME_FLAG_PROCEDE_STIPULA = INFO_AGGIUDICAZIONI.FLAG_PROCEDE_STIPULA;
	public static final String S_FIELD_NAME_FLAG_PROCEDE_STIPULA = "SiProc_stipula";
	public static final String N_FIELD_NAME_FLAG_PROCEDE_STIPULA = "NoProc_stipula";

	// nuovi campi
	public final static String FIELD_NAME_FLAG_ENTE_SPECIALE = INFO_AGGIUDICAZIONI.FLAG_ENTE_SPECIALE;
	public final static String FIELD_NAME_TIPO_CONTRATTO = INFO_AGGIUDICAZIONI.TIPO_CONTRATTO;
	public final static String FIELD_NAME_CODICE_CC = INFO_AGGIUDICAZIONI.CODICE_CC;
	public final static String FIELD_NAME_DENOM_CC = INFO_AGGIUDICAZIONI.DENOM_CC;
	public final static String FIELD_NAME_FLAG_SA_AGENTE = INFO_AGGIUDICAZIONI.FLAG_SA_AGENTE;
	public final static String FIELD_NAME_ID_TIPO_SA = INFO_AGGIUDICAZIONI.ID_TIPOLOGIA_SA;

	// nuovi da gara

	public static final String FIELD_NAME_ID_CATEG_SA = INFO_AGGIUDICAZIONI.ID_CATEG_SA;
	public static final String FIELD_NAME_CF_STAZIONE_APPALTANTE = INFO_AGGIUDICAZIONI.CF_SA;
	public static final String FIELD_NAME_DENOM_STAZIONE_APPALTANTE = INFO_AGGIUDICAZIONI.DEN_SA;
	public static final String FIELD_NAME_DEN_AMMIN = INFO_AGGIUDICAZIONI.DEN_AMM;
	public static final String FIELD_NAME_CF_AMMIN = INFO_AGGIUDICAZIONI.CF_AMM;
	public static final String FIELD_NAME_CF_AMM_AGENTE = INFO_AGGIUDICAZIONI.CF_AMM_AGENTE;
	public static final String FIELD_NAME_DEN_AMM_AGENTE = INFO_AGGIUDICAZIONI.DEN_AMM_AGENTE;

	public static final String FIELD_NAME_ALBO_PRETORIO = PUBBLICAZIONI.DATA_ALBO;
	public static final String FIELD_NAME_GAZZETTA_UFFICIALE_CE = PUBBLICAZIONI.DATA_GUCE;
	public static final String FIELD_NAME_GAZZETTA_UFFICIALE_RI = PUBBLICAZIONI.DATA_GURI;

	// gm nuovo codice pubblicazione bando 3.0
	public static final String FIELD_NAME_NUMERO_GUCE = PUBBLICAZIONI.NUMERO_GUCE;
	public static final String FIELD_NAME_NUMERO_GURI = PUBBLICAZIONI.NUMERO_GURI;
	public static final String FIELD_NAME_NUMERO_BORE = PUBBLICAZIONI.NUMERO_BORE;
	public static final String FIELD_NAME_LINK_SITO_COMMITTENTE = PUBBLICAZIONI.LINK_SITO_COMMITTENTE;
	public static final String FIELD_NAME_NOTE_ALLEGATO = ALLEGATI.NOTE;
	public static final String STORICO_PUBBLICAZIONI = "storicoPubblicazioni";
	public static final String STORICO_ALLEGATI = "storicoAllegati";
	// gm fine nuovo codice pubblicazione bando 3.0

	public static final String FIELD_NAME_QUOTIDIANI_NAZIONALI = PUBBLICAZIONI.QUOTIDIANI_NAZ;
	public static final String FIELD_NAME_QUOTIDIANI_REGIONALI = PUBBLICAZIONI.QUOTIDIANI_REG;
	
	

	public static final String FIELD_NAME_BOLLETTINO_REGIONALE = PUBBLICAZIONI.DATA_BORE;
	public static final String FIELD_NAME_PERIODICI = PUBBLICAZIONI.PERIODICI;
	// UN nuovo campo ID_GARA
	public static final String FIELD_NAME_ID_GARA = GARA.ID_GARA + "ricerca";

	public static final String FIELD_NAME_SITO_OSSERVATORIO_CP = PUBBLICAZIONI.SITO_OSSERVATORIO_CP;
	public static final String S_FIELD_NAME_SITO_OSSERVATORIO_CP = "Si" + PUBBLICAZIONI.SITO_OSSERVATORIO_CP;
	public static final String N_FIELD_NAME_SITO_OSSERVATORIO_CP = "No" + PUBBLICAZIONI.SITO_OSSERVATORIO_CP;

	public static final String FIELD_NAME_SITO_MIN_INF_TRASP = PUBBLICAZIONI.SITO_MINISTERO_INF_TRASP;
	public static final String S_FIELD_NAME_SITO_MIN_INF_TRASP = "Si" + PUBBLICAZIONI.SITO_MINISTERO_INF_TRASP;
	public static final String N_FIELD_NAME_SITO_MIN_INF_TRASP = "No" + PUBBLICAZIONI.SITO_MINISTERO_INF_TRASP;

	public static final String FIELD_NAME_PROFILO_COMMITTENTE = PUBBLICAZIONI.PROFILO_COMMITTENTE;
	public static final String S_FIELD_NAME_PROFILO_COMMITTENTE = "Si" + PUBBLICAZIONI.PROFILO_COMMITTENTE;
	public static final String N_FIELD_NAME_PROFILO_COMMITTENTE = "No" + PUBBLICAZIONI.PROFILO_COMMITTENTE;

	// gm aggiunto per estensione pubblicazione bandi
	public static final String FIELD_NAME_FLAG_BENICULT = PUBBLICAZIONI.FLAG_BENICULT;
	public static final String S_FIELD_NAME_FLAG_BENICULT = "Si" + PUBBLICAZIONI.FLAG_BENICULT;
	public static final String N_FIELD_NAME_FLAG_BENICULT = "No" + PUBBLICAZIONI.FLAG_BENICULT;
	public static final String FIELD_NAME_FLAG_SOSPESO = PUBBLICAZIONI.FLAG_SOSPESO;
	public static final String S_FIELD_NAME_FLAG_SOSPESO = "Si" + PUBBLICAZIONI.FLAG_SOSPESO;
	public static final String N_FIELD_NAME_FLAG_SOSPESO = "No" + PUBBLICAZIONI.FLAG_SOSPESO;

	public final static String FIELD_NAME_ID_LOTTO = "idLotto";
	public static final String FIELD_NAME_TEXT = "text";
	public static final String FIELD_NAME_ID_PUBBLICAZIONE = "id_pubblicazione";
	public static final String FIELD_NAME_DATA_INIZIO_PUB = "data_inizio_pub";

	public static final String FIELD_NAME_MODO_REALIZZAZIONE = "modoRealizzazione";

	// nuovi campi lotto estremi programma triennale
	// gm nuovo codice 3.0
	public static final String FIELD_NAME_TRIENNIO_ANNO_INIZIO = "TRIENNIO_ANNO_INIZIO";
	public static final String FIELD_NAME_TRIENNIO_ANNO_FINE = "TRIENNIO_ANNO_FINE";
	public static final String FIELD_NAME_TRIENNIO_ANNO_PROGRESSIVO = "TRIENNIO_ANNO_PROGRESSIVO";
	public static final String FIELD_NAME_ANNUALE_CUI_MININF = "ANNUALE_CUI_MININF";

	public static final String FIELD_NAME_LUOGO_ISTAT = "LUOGO_ISTAT";
	public static final String FIELD_NAME_LUOGO_NUTS = "LUOGO_NUTS";
	public static final String FIELD_NAME_IMPORTO_SICUREZZA = "IMPORTO_SICUREZZA";
	// gm fine nuovo codice 3.0

	// PP B302.2.0
	public static final String FIELD_FLAG_PREVEDE_RIP = "FLAG_PREVEDE_RIP";
	public static final String FIELD_FLAG_RIPETIZIONE = "FLAG_RIPETIZIONE";
	public static final String FIELD_CIG_ORIGINE_RIP = "CIG_ORIGINE_RIP";

	// UN is3031_ESCL_AVCPASS
	public static final String FIELD_FLAG_ESCLUSO_AVCPASS = "FLAG_ESCLUSO_AVCPASS";

	// INT85
	public static final String FIELD_NAME_SCELTA_LEGGE85 = "SCELTA_LEGGE85";
	public static final String FIELD_NAME_FLAG_LEGGE85 = "FLAG_LEGGE85";
	public static final String FIELD_NAME_TIPOSA_BDNCP = "FIELD_NAME_TIPOSA_BDNCP";

	
		
	// INT87
	public static final String FIELD_NAME_URGENZA_DL133 = "URGENZA_DL133";

	// is30350_RFWEBGL01Active
	public static final String FIELD_NAME_EAGG_COD_MOTIVO = "EAGG_COD_MOTIVO";
	public static final String FIELD_NAME_FLAG_EAGG = "FLAG_LEAGG";

	public static final String SRV_INIZIALIZZA_GARA = "inizializzaGara";
	public static final String SRV_CANCELLA_GARA = "cancellaGara";
	public static final String SRV_AGGIORNA_TABELLE = "aggiornaTabelle";
	public static final String SRV_VISUALIZZA_AGGIORNAMENTI_TABELLE = "visualizzaAggiornamenti";
	public static final String SRV_VISUALIZZA_DETTAGLIO = "visualizzaDettaglio";
	public static final String SRV_VISUALIZZA_CARICAMENTI = "importaFile";
	public static final String SRV_VISUALIZZA_RIEPILOGO_SCHEDA = "visualizzaRiepilogoScheda";
	public static final String SRV_VISUALIZZA_RIEPILOGO_SCHEDE_MULTILOTTO = "visualizzaRiepilogoSchedeMultilotto";
	public static final String SRV_VISUALIZZA_LOTTI = "visualizzaLotti";
	public static final String SRV_GESTISCI_LOTTO = "gestisciLotto";
	public static final String SRV_GESTISCI_DOCUMENTI = "gestisciDocumenti";
	public static final String SRV_INIZIALIZZA_LOTTO = "inizializzaLotto";
	public static final String SRV_GESTISCI_ALLEGATI = "gestisciAllegati";
	public static final String SRV_STORICO_ALLEGATI = "storicoAllegati";

	public static final String SRV_SCHEDA_A = "schedaA";
	public static final String SRV_SCHEDA_ADESIONE = "SrvSchedaAdesione";
	public final static String SRV_SCHEDA_SOTTOSOGLIA = "schedaSottosoglia";
	public final static String SRV_SCHEDA_ESCLUSI = "schedaEsclusi";
	public static final String SRV_LOAD_PRE_INSERT = "preInsertDatiComuni";
	public static final String SRV_INSERT_DATI_COMUNI = "inserisciDatiComuni";
	public static final String SRV_RICERCA_CPV = "ricercaCPV";
	public static final String SRV_RICERCA_ISTAT = "ricercaIstat";
	public static final String SRV_RICERCA_NUTS = "ricercaNuts";

	public static final String SRV_PRESA_IN_CARICO = "presaInCarico";

	/*
	 * I nomi delle variabili di sessione vengono parametrizzate
	 */
	public static final String SESSION_ID_GARA = "ID_GARA";
	public static final String SESSION_DATA_CREAZIONE_GARA = "DATA";
	/**
	 * ADDED TO PREVENT OVERRIDE OF SESSION VARIABLES, SEARCH VS INSERT NEW LOTTO
	 */
	public static final String SESSION_DATA_CREAZIONE_GARA_1 = SESSION_DATA_CREAZIONE_GARA + "_1";
	public static final String SESSION_NUMERO_LOTTI_CREATI = "NUMEROLOTTI";
	public static final String SESSION_DATA_PUBBLICAZIONE_LOTTO_A = "data_pubblicazione_a";
	public static final String SESSION_DATA_PUBBLICAZIONE_LOTTO_DA = "data_pubblicazione_da";
	public static final String SESSION_DATA_PUBBLICAZIONE_LOTTO_A_RIPROPOSIZIONE = "data_pubblicazione_a";
	public static final String SESSION_DATA_PUBBLICAZIONE_LOTTO_DA_RIPROPOSIZIONE = "data_pubblicazione_da";
	public static final String SESSION_DATA_LOG_DA = "SESSION_DATA_LOG_DA";
	public static final String SESSION_DATA_LOG_A = "SESSION_DATA_LOG_A";
	public static final String SESSION_DATA_LOG_DA_RIPROPOSIZIONE = "SESSION_DATA_LOG_DA";
	public static final String SESSION_DATA_LOG_A_RIPROPOSIZIONE = "SESSION_DATA_LOG_A";

	public static final String SESSION_DATA_INSERITA_CONSULTA_LOG = "SESSION_DATA_INSERITA_CONSULTA_LOG";
	public static final String SESSION_DATA_INSERITA_CONSULTA_LOG_SCHEDE = "SESSION_DATA_INSERITA_CONSULTA_LOG_SCHEDE";
	public static final String SESSION_DATI_INSERITI_ERROR_GESTIONE_GARE = "SESSION_DATI_INSERITI_ERROR_GESTIONE_GARE";
	public static final String SESSION_DATA_INTERVALLO_ERROR_CONSULTA_LOG_SCHEDE = "SESSION_DATA_INTERVALLO_ERROR_CONSULTA_LOG_SCHEDE";
	public static final String SESSION_DATA_INTERVALLO_ERROR_CONSULTA_LOG = "SESSION_DATA_INTERVALLO_ERROR_CONSULTA_LOG";
	public static final String SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE = "SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE";
	public static final String SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE = "SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE";

	public static final String SESSION_DATA_SCADENZA_A = "data_scadenza_a";
	public static final String SESSION_DATA_SCADENZA_DA = "data_scadenza_da";
	public static final String SESSION_DATA_SCADENZA_A_RIPROPOSIZIONE = "data_scadenza_a";
	public static final String SESSION_DATA_SCADENZA_DA_RIPROPOSIZIONE = "data_scadenza_da";

	/* public static final String ID_LOTTO = "idLotto"; */
	public static final String A_SCADENZA_PAGAMENTI = "AAAADataScadenzaPagamenti";
	public static final String A_DATA_PUBBLICAZIONE = "AAAADataPubblicazione";
	public static final String TAB_SERVIZIO_DATA = "dataUltimaModifica";
	public static final String TAB_CARICAMENTI_ATTIVI = "caricamentiAttivi";
	public static final String TAB_CARICAMENTI_COMPLETATI = "caricamentiCompletati";
	public static final String TAB_UPLOAD = "importaFile";
	public static final String ORDER_FIELD = "order_field";

	public static final String PUBBLICAZIONE_EFFETTUATA = "PUBBLICAZIONE_EFFETTUATA";

	public final static String TIPOLOGIA_LAVORI_PUBBLICI = "01";
	public static final String TIPO_UTENZA = "TIPO_UTENZA";
	public static final String STATO_PAGAMENTO = "STATO";
	public static final String CANALE_PAGAMENTO = "CANALE";

	public static final String START_ROW = "start";
	/*
	 * public static final String END_ROW = "end";
	 */

	public static final String CONFIG_ELEMENTI_VISUALIZZATI = "CONFIG_ELEMENTI_VISUALIZZATI";
	public static final String CONFIG_ELEMENTI_EXPORT = "CONFIG_ELEMENTI_EXPORT";
	public static final String ACTION_GET_LIST = "ACTION_GET_LIST";
	public static final String ACTION_CERCA = "ACTION_CERCA";
	public static final String ACTION_CPV_GROUPS = "GET_CPV_GROUPS";
	public static final String ACTION_CPV_ALL_LIST = "GET_ALL_CPV";
	public static final String PROGRESS = "PROGRESS";
	public static final String REGRESS = "REGRESS";
	public static final String STILL = "STILL";
	public static final String ESPORTAELENCO = "ESPORTAELENCO";

	public static final String CPV_DIV = "CPV_DIV";
	public static final String CPV_GRP = "CPV_GRP";
	public static final String CPV_CLS = "CPV_CLS";
	public static final String CPV_CTG = "CPV_CTG";

	public static final String HELP_GUIDA_RSSA = "help/GuidaSimog_RSSA.pdf";
	public static final String HELP_GUIDA_RUP = "help/GuidaSimog_RUP.pdf";
	public static final String HELP_GUIDA_RUPOC = "help/GuidaSimog_RUPOC.pdf";
	public static final String HELP_GUIDA_AVLP = "help/GuidaSimog_AVLP.pdf";
	public static final String HELP_GUIDA_ADMIN = "help/GuidaSimog_ADMIN.pdf";
	public static final String HELP_GUIDA_OSR = "help/GuidaSimog_OSR.pdf";
	public static final String HELP_GUIDA_RASA = "help/GuidaSimog_RASA.pdf";

	public static final String DOC_GESTIONE = "gestisciDocumenti";
	public static final String DOC_UPLOAD = "upload";

	public static final String ACTION = "action";

	public static final String FIELD_INSERISCI_IMPORTO = "inserisci_importo";
	public static final String INSERISCI_IMPORTO_SI = "SI";
	public static final String INSERISCI_IMPORTO_NO = "NO";

	public static final String ACTION_SALVA = "Salva";
	public static final String ACTION_CONFERMA = "Conferma";
	public static final String ACTION_REIMPOSTA = "Reimposta";
	public static final String ACTION_NEW = "crea";
	public static final String ACTION_RICHIEDI_ANNULLA = "Richiedi Modifica";

	public static final String SHOW_DATI_COMUNI = "show_dati_comuni";

	public static final String MSSQL = "mssql";
	public static final String MYSQL = "mysql";

	// last adds
	public static final String FLAG_ESTERO = "FLAG_ESTERO";

	// UN last last adds
	public static final String SOGLIE_IMPORTO = "soglie_importo";
	public static final String ID_SOGLIA_IMPORTO = "id_soglia_importo";
	public static final String FIELD_NAME_ESITO_PROCEDURA = INFO_AGGIUDICAZIONI.ESITO_PROCEDURA;

	public static final String SRV_VARIAZIONE_SA = "variazioneSA";

	public static final String SBLOCCA_GARA = "sbloccaGara";
	public static final String MODIFICA_REALIZZAZIONE_GARA = "modificaRealizzazioneGara";
	public static final String ID_REAL = "idReal";
	public static final String SRV_GESTIONE_ELENCO_INVITATI = "gestioneElencoInvitati";
	public static final String JSP_ELENCO_INVITATAI = "elencoInvitati.jsp";

	// multilotto
	public static final String JSP_GESTIONE_MULTILOTTO_NEW = "gestioneMultilotto.jsp";
	public static final String JSP_EDIT_MULTILOTTO = "editMultilotto.jsp";
	public static final String SRV_GESTIONE_MULTILOTTO_NEW = "gestioneMultilotto";
	public static final String SRV_EDIT_MULTILOTTO = "editMultilotto";

	public static final String ACTION_AGGIUNGI_SINGOLA = "action_singola";
	public static final String ACTION_AGGIUNGI_AL_GRUPPO = "action_aggiungiGruppo";
	public static final String ACTION_MODIFICA_GRUPPO = "action_modificaGruppo";
	public static final String ACTION_CREA_NUOVO_GRUPPO = "action_nuovoGruppo";
	public static final String AGGIUDICAZIONI_DA_AGGIUNGERE = "aggiudicazioniDaAggiungere";
	public static final String AGGIUDICAZIONI_DA_ELIMINARE = "aggiudicazioniDaEliminare";
	public static final String AGGIUDICAZIONE_PRINCIPALE = "aggiudicazionePrincipale";
	public static final String CODICE_SELEZIONATO = "codiceSelezionato";
	public static final String SET_CODICI_CONTRATTO = "setCodiciContratto";
	public static final String AGGIUDICAZIONE_CORRENTE = "aggiudicazioneCorrente";
	public static final String LISTA_AGGIUDICAZIONI = "listaAggiudicazioni";
	public static final String AGGIUDICATARI_CORRENTI = "aggiudicatariCorrenti";
	public static final String MAPPA_MULTILOTTO = "mappaMultilotto";

	public static final String MOTIVI_VARIAZIONE_SA = "motiviVariazioneSA";
	public static final String ID_MOTIVO_VARIAZIONE_SA = "idMotivoVariazioneSA";

	public static final String ADESIONE_VALIDA = "adesioneValida";

	// is3030_RFWEBGL02Active
	public static final String BLOCCO_AVCPASS_MODIFICA_LOTTO = "BLOCCO_AVCPASS_MODIFICA_LOTTO";
	public static final String BLOCCO_AVCPASS_PERFEZIONA_LOTTO = "BLOCCO_AVCPASS_PERFEZIONA_LOTTO";
	public static final String BLOCCO_AVCPASS_MOD_CONTRATTO_ESCLUSO = "BLOCCO_AVCPASS_MOD_CONTRATTO_ESCLUSO";
	public static final String BLOCCO_AVCPASS_MOD_DATI_RIPETIZIONI = "BLOCCO_AVCPASS_MOD_DATI_RIPETIZIONI";
	public static final String BLOCCO_AVCPASS_CANCELLA_LOTTO = "BLOCCO_AVCPASS_CANCELLA_LOTTO";
	public static final String BLOCCO_AVCPASS_RIPRISTINO_LOTTO = "BLOCCO_AVCPASS_RIPRISTINO_LOTTO";
	public static final String BLOCCO_AVCPASS_MODIFICA_GARA = "BLOCCO_AVCPASS_MODIFICA_GARA";
	public static final String BLOCCO_AVCPASS_PUBBLICA_RETTIFICA = "BLOCCO_AVCPASS_PUBBLICA_RETTIFICA";
	public static final String BLOCCO_AVCPASS_ELENCO_INVITATI = "BLOCCO_AVCPASS_ELENCO_INVITATI";
	public static final String BLOCCO_AVCPASS_SBLOCCA_GARA = "BLOCCO_AVCPASS_SBLOCCA_GARA";
	public static final String BLOCCO_AVCPASS_MOV_ACCORDO_QUADRO = "BLOCCO_AVCPASS_MOV_ACCORDO_QUADRO";
	public static final String BLOCCO_AVCPASS_VARIAZIONE_SA = "BLOCCO_AVCPASS_VARIAZIONE_SA";
	public static final String BLOCCO_AVCPASS_PRESAINCARICO_GARA = "BLOCCO_AVCPASS_PRESAINCARICO_GARA";
	public static final String BLOCCO_AVCPASS_CANCELLA_GARA = "BLOCCO_AVCPASS_CANCELLA_GARA";
	public static final String BLOCCO_AVCPASS_RIPRISTINA_GARA = "BLOCCO_AVCPASS_RIPRISTINA_GARA";
	public static final String BLOCCO_AVCPASS_AGGIUNGI_LOTTO = "BLOCCO_AVCPASS_AGGIUNGI_LOTTO";
	public static final String BLOCCO_AVCPASS_PERF_PROCEDURA_RISTRETTA = "BLOCCO_AVCPASS_PERF_PROCEDURA_RISTRETTA";
	public static final String BLOCCO_AVCPASS_CONFERMA_RETTIFICA = "BLOCCO_AVCPASS_CONFERMA_RETTIFICA";
	public static final String BLOCCO_AVCPASS_AGGIUNGI_REQUISITO = "BLOCCO_AVCPASS_AGGIUNGI_REQUISITO";
	public static final String BLOCCO_AVCPASS_MODIFICA_REQUISITO = "BLOCCO_AVCPASS_MODIFICA_REQUISITO";
	public static final String BLOCCO_AVCPASS_CANCELLA_REQUISITO = "BLOCCO_AVCPASS_CANCELLA_REQUISITO";

	// TICKET ALM #664
	public static final String FIELD_NAME_STRUMENTO_SVOLGIMENTO = "strumentoSvolgimento";
	public static final String STRUMENTO_SVOLGIMENTO = "STRUMENTO_SVOLGIMENTO_BEAN";
	// FINE TICKET ALM #664

	// TICKET ALM #2845
	public static final String FIELD_FLAG_DL50 = "FLAG_DL50";
	public static final String FIELD_NAME_PRIMA_ANNUALITA = "PRIMA_ANNUALITA";
	// FINE TICKET ALM #2845

	// TICKET ALM #3832
	public static final String FIELD_NAME_ESTREMA_URGENZA = "estremaUrgenza";
	public static final String ESTREMA_URGENZA = "ESTREMA_URGENZA_BEAN";
	// FINE TICKET ALM #3832

	// TICKET ALM #3834
	public static final String FIELD_NAME_ALLEGATO_IX = "allegatoIX";
	public static final String ALLEGATO_IX = "ALLEGATO_IX_BEAN";
	// FINE TICKET ALM #3834

	// TICKET #2846
	public static final String FIELD_NAME_MOTIVO = "motivoCollegamento";
	public static final String MOTIVO_COLLEGAMENTO_BEAN = "MOTIVO_COLLEGAMENTO_BEAN";
	// FINE TICKET #2846

	// TICKET ALM #3835
	public static final String FIELD_NAME_AFF_RISERVATI = "affRiservati";
	public static final String AFF_RISERVATI = "AFF_RISERVATI_BEAN";
	// FINE TICKET ALM #3835

	// TICKET ALM #3836
	public static final String FIELD_FLAG_REGIME = "FLAG_REGIME";
	public static final String FIELD_NAME_ART_REGIME = "artRegime";
	public static final String ART_REGIME = "ART_REGIME_BEAN";
	// FINE TICKET ALM #3836

	// TICKET ALM - 3.04.3
	public static final String FIELD_NAME_FUNZIONI_DELEGATE = "idFunzioniDelegate";

	// TICKET ALM - 3.04.3 #659
	// Servlet caricamento presa in carico gara delegata
	public static final String SRV_PRESA_IN_CARICO_GARA_DELEGATA = "presaInCaricoGaraDelegata";
	public static final String JSP_PRESA_IN_CARICO_GARA_DELEGATA = "presaInCaricoGaraDelegata.jsp";
	public static final String HAS_AGG_CONFERMATE = "hasAggConfermate";
	public static final String CF_AMM_DELEGANTE = "cfAmmDelegante";
	public static final String IS_DELEGA = "isDelega";
	public static final String IS_ACC_QUADRO_NC = "isAccQuadroNc";
	public static final String DEFAULT_MOTIVO_COLL_CIG = "10";

	// TICKET ALM #659 - 3.04.4
	public final static String FIELD_NAME_FLAG_SA_AGENTE_GARA = "flagSAAgente";
	public static final String FIELD_NAME_ID_F_DELEGATE = "idFDelegate";
	public static final String ID_F_DELEGATE = "ID_F_DELEGATE_BEAN";
	// FINE TICKET ALM #659 - 3.04.4

	// TICKET ALM - 3.04.4
	public static final String LISTA_CATEGORIE_LOTTO = "LISTA_CATEGORIE_LOTTO_BEAN";
	public static final String FIELD_NAME_CATEGORIA_LOTTO = "idCatLotto";
	public static final String LISTA_INIZIATIVE_DISPONIBILI = "LISTA_INIZIATIVE_DISPONIBILI";
	public static final String FIELD_NAME_CHECK_INIZIATIVE = "FIELD_NAME_CHECK_INIZIATIVE";
	public static final String VALUE_CHECK_INIZIATIVE = "VALUE_CHECK_INIZIATIVE";
	public static final String JSP_POPUP_SOGG_AGGR = "popupAdesioneSoggAggr.jsp";
	public static final String FIELD_NAME_FLAG_SA_NO_DPCM = "FIELD_NAME_FLAG_SA_NO_DPCM";
	public static final String FIELD_NAME_FLAG_SA_NO_CLASSIFICATA = "FIELD_NAME_FLAG_SA_NO_CLASSIFICATA";
	public static final String FIELD_NAME_CIG_INIZIATIVA_SEL = "FIELD_NAME_CIG_INIZIATIVA_SEL";
	public static final String DATI_STORICO_DELEGA = "datiStoricoDelega";
	public static final String RUP_CREATO_GARA = "rupCreatoGara";
	public static final String EAGG_CATLOTTO = "EAGG_CATLOTTO";

	// TICKET ALM - 3.04.5
	public static final String FIELD_NAME_IMPORTO_OPZIONI = "IMPORTO_OPZIONI";

	// Ticket #20055
	public static final String RETTIFICA_GARA_LOTTI = "RETTIFICA_GARA_LOTTI";

	// Ticket 20055
	public static final String ACTION_ANULLA_RETTIFICA = "ANNULLA_RETTIFICA";

	// Ticket 31047
//	public static final String FIELD_FLAG_PAR_GEN_MOD1 = "FLAG_PAR_GEN_MOD1";
//	public static final String FIELD_FLAG_PAR_GEN_MOD2 = "FLAG_PAR_GEN_MOD2";
//	public static final String FLAG_PNRR_PNC = "FLAG_PNRR_PNC";

	public static final String FLAG_PNRR_PNC = "FLAG_PNRR_PNC";
	public static final String FLAG_PREVISIONE_QUOTA = "FLAG_PREVISIONE_QUOTA";
	public static final String FLAG_MISURE_PREMIALI = "FLAG_MISURE_PREMIALI";
	public static final String QUOTA_FEMMINILE = "QUOTA_FEMMINILE";
	public static final String QUOTA_GIOVANILE = "QUOTA_GIOVANILE";

	public static final String FIELD_NAME_MOTIVO_DEROGA = "motivoDeroga";
	public static final String MOTIVO_URGENZA= "motivoURGENZA";

	public static final String MOTIVO_DEROGA_TABLEBEAN = "MOTIVO_DEROGA_TABLEBEAN";
	public static final String MOTIVO_DEROGA_SELECTED_TABLEBEAN = "MOTIVO_DEROGA_SELECTED_TABLEBEAN";

	public static final String MISURA_PREMIALE_TABLEBEAN = "MISURA_PREMIALE_TABLEBEAN";
	public static final String MISURA_PREMIALE_SELECTED_TABLEBEAN = "MISURA_PREMIALE_SELECTED_TABLEBEAN";
	 
	// MARRA MEV 34470 3.04.8
	public static final String FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO = "linkAffidDiretto";
	
	//MEV 37010 3.04.8.1
	public static final String FLAG_DEROGA_ADESIONE = "flagDerogaAdesione";
	public static final String MOSTRA_DEROGA_ADESIONE = "mostraDerogaAdesione";
	public static final String MOSTRA_INTEGRA_PARI_OPPORTUNITA = "mostraIntegraPariOpportunita";
		
	//FIX 40610
	public static final String FLAG_IS_KO = "flagIsKo";
	
	//MEV 3.04.10 43227
	public static final String MOSTRA_MODIFICA_DATI_PERFEZIONAMENTO = "mostraModificaDatiPerfezionamento";
	
	//MEV 53643 3.04.13
	public static final String MOSTRA_MODIFICA_CPV = "mostraModificaCPV";
	
	//MAD 68089 3.04.16
	public static final String MOSTRA_MODIFICA_CAT_SOA = "mostraModificaCategoriaSoa";
	public static final String ACTION_MODIFICA_CAT_SOA = "modificaCategoriaSOA";
	
	//MEV 38205 3.04.8.1
	public static final String FIELD_NAME_FLAG_USO_METODI_EDILIZIA = "flagUsoMetodiEdilizia";

	//3.04.9 MEV 40610
	public static final String MOSTRA_DEROGA_QUALIFICAZIONE_SA = "mostraDerogaQualificazioneSA";
	public static final String DEROGA_QUALIFICAZIONE_SA_VISUAL = "derogaQualificazioneSAVisual";
}
