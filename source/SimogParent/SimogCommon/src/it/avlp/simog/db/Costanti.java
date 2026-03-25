package it.avlp.simog.db;

public class Costanti {
	
	// versioni per il controllo dei dati da inserire nel consultagara
   public static final String VERS_MAX = "9.99.9.9"; 
	public static final String VERS_302_1_3 = "3.02.1.3";
	public static final String VERS_302_1_4 = "3.02.1.4";
	public static final String VERS_302_1_6 = "3.02.1.6";
	public static final String VERS_302_2_0 = "3.02.2.0";
   public static final String VERS_302_5_0 = "3.02.5.0";
   public static final String VERS_302_8_0 = "3.02.8.0";
   public static final String VERS_303_0_0 = "3.03.0.0";
   public static final String VERS_303_1_0 = "3.03.1.0";
   // INT87
   public static final String VERS_303_3_0 = "3.03.3.0";

   // enti aggregatori
   public static final String VERS_303_5_0 = "3.03.5.0";
   
   //TICKET ALM #3835
   public static final String VERS_304_2_0 = "3.04.2.0";

   //TICKET ALM - 3.04.3
   public static final String VERS_304_3_0 = "3.04.3.0";
   
   //TICKET ALM - 3.04.4
   public static final String VERS_304_4_0 = "3.04.4.0";
   
   //TICKET ALM - 3.04.5
   public static final String VERS_304_5_0 = "3.04.5.0";
   public static final String VERS_304_5_1 = "3.04.5.1";
   
   public static final String VERS_304_6_0 = "3.04.6.0";
   
   // comuni
	public static final String FLAG_VALORE_SI = "S";
	public static final String FLAG_VALORE_NO = "N";
	public static final String FLAG_VALORE_Q = "Q";
	
	public static final String FLAG_VALORE_TRUE = "true";
	public static final String FLAG_VALORE_FALSE = "false";

	//2.10 aggiunta la costante importo lotto massimo e fuori scala
	
    public static final float IMPORTO_LOTTO_5150000 = 5150000;
    public static final float IMPORTO_LOTTO_1000000 = 1000000;
    public static final float IMPORTO_LOTTO_500000 = 500000;
    public static final float IMPORTO_LOTTO_150000 = 150000;
    public static final float IMPORTO_LOTTO_412000 = 412000;
    public static final float IMPORTO_FUORI_SCALA = -1;
    public static final float IMPORTO_LOTTO_4845000 = 4845000;
    public static final float IMPORTO_LOTTO_387000 = 387000;
    public static final float IMPORTO_LOTTO_40000 = 40000;
    public static final float IMPORTO_LOTTO_20000 = 20000;

    // rimpiazza importo_lotto_aux del file simog.ini
    public static final String IMPORTO_FUORI_SCALA_STRING = "-1.00";
    public static final String IMPORTO_FUORI_SCALA_STRING_3D = "-1.000";

    // data per attivazione nuova normativa sottosoglia
    public static final String DATA_NUOVI_SOTTO = "20110712";
	// soggetto partecipante
	public static final String CODICE_STATO_ITALIANO = "IT";
	public static final String NOME_STATO_ITALIANO = "Italia"; 

	//tipi di operazione per pubblicazione
    public static final String PUBBLICAZIONE_BANDO = "1";
    public static final String PUBBLICAZIONE_AVVISOAGG = "2";
    public static final String PUBBLICAZIONE_LETTINV = "3";
    public static final String PUBBLICAZIONE_ELENCOINV = "4";
    public static final String PUBBLICAZIONE_RETTIFICA = "5";

	
	// aggiudicazione
	public static final String TIPO_SCHEDA_LAVORI = "L";
	public static final String TIPO_SCHEDA_FORNITURE = "F";
	public static final String TIPO_SCHEDA_SERVIZI = "S";
	
	public static final String TIPO_ENTE_SPECIALE = "S";
	public final static String TIPO_ENTE_ORDINARIO="O";

	public final static String TIPO_SCHEDA_LAVORI_DESC="Lavori";
	public final static String TIPO_SCHEDA_FORNITURE_DESC="Forniture";
	public final static String TIPO_SCHEDA_SERVIZI_DESC="Servizi";

	public final static int AVVISO_PERIODICO_INDICATIVO=1;

	public static final int CUP_LENGTH = 15;
	//START_DATE era 20000101 prima della versione 2.10
	public static final String START_DATE = "20080101";		// periodo minimo validita'  data aggiudicazione
	public static final String START_DATE_FE = "01/01/2008";// Start date per il front end
	public static final int COND_SPB = 4;					// senza previa pubblicazione Bando
	public static final int COND_SPBG = 10;					// senza previa pubblicazione Gara
	public static final int COND_SEL = 11;                  // selettive ex art.238 c.7
	
	public static final int PROC_APE = 1;					// procedura aperta
	public static final int PROC_RIS = 2;					// procedura ristretta
	public static final int DIA_COMP = 8;					// dialogo competitivo
	public static final int PROC_NEG_PP = 9;                // procedura negoziata previa pubblicazione
	public static final int PROC_NEG_NO_PP = 4;             // procedura negoziata senza previa pubblicazione
	//TICKET ALM #2847
	public static final int PROC_COMP_NEG = 29;				// procedura competitiva con negoziazione
	public static final int PROC_NEG_SS = 30;				// Procedura negoziata con previa indizione di gara (settori speciali)
	//FINE TICKET ALM #2847

	// PP 05.08.2016 aggiunta per obbligatorieta' bando 
	public static final int PROC_RIS_AVVISI = 13;              // procedura ristretta derivante da avvisi con cui si indice una gara
	
    //Ticket ALM #648
	public static final int PROC_RIS_SEMP = 25;
	
	//gm nuovi campi simog 3.06
	public static final int AFF_DIR_ADESIONE = 18;          // affidamento diretto in adesione ad accordo quadro/convenzione
	public static final int CON_COM_ADESIONE = 19;          // confronto competitivo in adesione ad accordo quadro/convenzione
	
	public static final int PROG_ESTERNO_SA = 2;			// progettista esterno alla S.A.
	public static final long TIPODITTA_LIKE_ATI = 1;		// tipologia ditta ATI
	public static final long TIPODITTA_SINGOLA = 3;		// tipologia ditta Singola - MAC #2592
	public static final long TIPODITTA_ASS_CAT = 5;		// tipologia associazione di categoria
	public static final int TIPO_INCARICATO_ATI = 19;		// tipologia incaricato ATI
	//gm aggiunto per raggruppamenti di impresa
	public static final long TIPODITTA_LIKE_CONSORZIO = 2;  // tipologia ditta Consorzio
	public static final long TIPODITTA_GEIE = 4;	
	public static final long PREZZO_BASSO = 1;				// idModalitaGara relativo a "Prezzo piu' basso"
	//TICKET ALM #2847
	public static final long CRITERIO_MINOR_PREZZO = 4;		// idModalitaGara relativo a "Offerta economicamente piu' vantaggiosa: criterio del minor prezzo"
	public static final long CRITERIO_RAPPORTO_QP = 3;		// idModalitaGara relativo a "Offerta economicamente piu' vantaggiosa: miglior rapporto qualita / prezzo "
	public static final long CRITERIO_COMPETIZIONE = 5;		// idModalitaGara relativo a "Offerta economicamente piu' vantaggiosa: competizione solo in base a criteri qualitativi"
	//FINE TICKET ALM #2847
	
	public static final long OFFERTA_VANTAGGIOSA = 2;		// idModalitaGara relativo a "Offerta piu' vantaggiosa"
	
	public static final String MANDATARIA = "1";			// Ruolo ditta nel raggruppamento "Mandataria"
	public static final String MANDANTE = "2";				// Ruolo ditta nel raggruppamento "Mandante"
	
	public static final long SOLA_ESECUZIONE = 1;                 //Sola esecuzione
	public static final long PROGETTAZIONE_ESECUZIONE_DEF = 2;    //progettazione ed esecuzione su progetto definitivo
	public static final long PROGETTAZIONE_ESECUZIONE_PRE = 3;    //progettazione ed esecuzione su progetto preliminare
	
	//Condizioni di aggiudicazione
	
	public static final int DLGS_163_ART_57_C2LB = 2;           //D.Lgs 163/2006 art.57,c.2,lett.b
	public static final int DLGS_163_ART_57_C3LBF = 5;          //D.Lgs 163/2006 art.57,c.3,lett.b solo forniture
	public static final int DLGS_163_ART_57_C3LDF = 7;          //D.Lgs 163/2006 art.57,c.3,lett.d solo forniture
	public static final int DLGS_163_ART_57_C5LASA1 = 9;        //D.Lgs 163/2006 art.57,c.5,lett.a,sub.a1
	public static final int DLGS_163_ART_57_C5LASA2 = 10;       //D.Lgs 163/2006 art.57,c.5,lett.a,sub.a2
	public static final int DLGS_163_ART_57_C5LB = 11;          //D.Lgs 163/2006 art.57,c.5,lett.b
	public static final int DLGS_163_ART_221_C1LC = 14;         //D.Lgs 163/2006 art.221,c.1,lett.c solo settori speciali
	public static final int DLGS_163_ART_221_C1LE = 16;         //D.Lgs 163/2006 art.221,c.1,lett.e solo settori speciali
	public static final int DLGS_163_ART_221_C1LF = 17;         //D.Lgs 163/2006 art.221,c.1,lett.f solo settori speciali
	public static final int DLGS_163_ART_221_C1LG = 18;         //D.Lgs 163/2006 art.221,c.1,lett.g solo settori speciali	
	//2.10 aggiunte costanti non ancora presenti in DB per il controllo 9.1.1.41
	public static final int DLGS_163_ART_221_C1LJ = 23;         //D.Lgs 163/2006 art.221,c.1,lett.j solo settori speciali
	public static final int DLGS_163_ART_221_C1LK = 24;         //D.Lgs 163/2006 art.221,c.1,lett.k solo settori speciali
	public static final int DLGS_163_ART_99_C5_ART108_C6 = 28;  //D.Lgs 163/2006 art.99,c.5,art.108,c.6
	//2.10 fine costanti non ancora presenti in DB per il controllo 9.1.1.41
	
	// Conclusione
	public static final long RISOLUZIONE_CONTRATTO = 2;		// idMotivoInterruzione relativo a "Risoluzione contrattuale"
	public static final long RECESSO_SA = 4;				// idMotivoInterruzione relativo a "Recesso della stazione appaltante"
	public static final long RECESSO_APP = 5;				// idMotivoInterruzione relativo a "Recesso dell'appaltatore"
	public static final String SENZA_ONERI = "0";			// Flag Oneri "Senza Oneri"
	public static final long MAX_INFORTUNI = 9;             //numero infortuni massimo tollerato
	public static final long MIN_INFORTUNI = 0;             //numero infortuni minimo tollerato
	public static final long REVOCA_AGGIUDICAZIONE_DEFINITIVA = 1;
	public static final long RISOLUZIONE_CONTRATTUALE = 2;
	public static final long FALLIMENTO_SOGGETTO = 3;

	// R129
	public static final String TIPCOM_RITARDO = "R";		// tipo comunicazione RITARDO
	public static final String TIPCOM_SOSPENSIONE = "S";	// tipo comunicazione SOSPENSIONE

	public static final int MODOREAL_ACCORDO = 9;				// accordo quadro o convenzione
	public static final int MODOREAL_ADESIONE = 2;				// discendente da a/q con dialogo
	public static final int MODOREAL_ADESIONE_NOCOMPET = 11;	// discendente da a/q senza dialogo
	//MEV 34190 3.04.8
	public static final int MODOREAL_CONCESSIONE = 20;				// concessione discendente da a/q con dialogo
	public static final int MODOREAL_CONCESSIONE_NOCOMPET = 21;	// concessione discendente da a/q senza dialogo
	
	//TICKET ALM #2847
	public static final int MODOREAL_ACCORDO_QUADRO = 17;			// accordo quadro
	public static final int MODOREAL_CONVENZIONE = 18;				// convenzione
	//FINE TICKET ALM #2847

	public static final String SOGGETTO_PARTECIPANTE = "Soggetto Partecipante";
	public static final String SOGGETTO_RESPONSABILE = "Soggetto Responsabile";
    public static final String SOGGETTO_PROGETTISTA = "Soggetto Progettista";
    public static final String SOGGETTO_POSIZIONE = "Soggetto Posizione";
    public static final String SOGGETTO_AUSILIARIA = "Soggetto Ditta Ausiliaria";
	
	public static final String ID_SIMOG = "Id Simog";
	public static final String ID_LOCALE = "Id Locale";
	
	public static final String IN_RICHIESTA_ANNULLAMENTO = "In richiesta di Modifica";
	public static final String IN_CANCELLAZIONE = "In Richiesta di Cancellazione";
	public static final String IN_DEFINIZIONE = "In Definizione";

	public static final String DEFAULT_DATE = "19000101";		// data di default da usare quandp manca il dato (patch)

	//old pp public static final int MODO_RIAGG_ART113 = 1;				
	//old pp public static final int MODO_RIAGG_ART140 = 2;		
	
	//INVITO
	public static final String PRESENTI_INVITATI = "presentiInvitati";
	public static final String STORICO_HAS_LETTERA_INVITO = "storicoHasLetteraInvito";

	public static final int CSV_MAX_RECORDS=1000;

	// pp organi costituzionali
	public static final String SCELTA_CONTRAENTE_OOCC = "20"; // riservata a OOCC
	public static final String CATEGORIA_PREV_OOCC = "999"; // categoria non definita per OOCC
    public static final String ART_ESCLUSIONE_OOCC = "12"; // deciso anche con BDNCP
    
    // FIXato: !*!* IMPOSTARE QUELLA GIUSTA sul simog.ini!!!
    //is3025_REQUISITIActive
    public static final String DATA_ATTIVAZIONE_REQUISITI = "20130101";
    
    //is3024_NOPERFActive
    public static final String DATA_NOPERF = "20130101";

    //is3027_SOGLIAActive
    //Ticket ALM #2383
    //Data della nuova soglia stabilita da comunicato pubblicato in Gazzetta Ufficiale 254
    public static final String DATA_NUOVA_SOGLIA = "20131029";

    // Costanti utilizzate nelle query
 
    public static final String TIPOUSO_CODICE_AS_USO = "USO";
    
    public static final String TIPOFONTEDOCUMENTO_CODICE_OPERATOREECONOMICO = "OE";
    public static final String TIPOFONTEDOCUMENTO_CODICE_OPERATOREECONOMICO_AGG_SUBB = "OEAGGSUB";
    // duplicato public static final String TIPOUSO_CODICE_MODIFICABILE = "M";
    // duplucato public static final String TIPOUSO_CODICE_FACOLTATIVO = "F";
    // public static final String TIPOUSO_CODICE_SISTEMARISCOSSIONE = "AR";
    public static final String FLAGESCLUSIONE_N = FLAG_VALORE_NO;
    public static final String FLAGCOMPROVAOFFERTA_N = FLAG_VALORE_NO;
    public static final String FLAGAVVALIMENTO_N = FLAG_VALORE_NO;
    public static final String FLAGBANDOTIPO_N = FLAG_VALORE_NO;
    public static final String FLAGRISERVATEZZA_N = FLAG_VALORE_NO;
    public static final String TIPOUSO_FROM_RESULTSET = "USO";
    public static final String TIPO_REK_MASTER = "M";
   
    // spostato in messaggi public static final String MSG_AVCPASS = "ATTENZIONE: la gara e' attualmente gestita dal sistema AVCPASS, non sono consentite operazioni di modifica dei dati.";
    
//  3.04.7.1 cambiato avcpass in fvoe
    public static final String AVCPASS_ALERT = "(*) Il campo e' protetto perche' validato dal sistema FVOE";
    
    public static final String FLAG_PROCEDURA_NEGOZIATA = "x";
    
    // is3031_RFWEBGL02Active
    public static final long TIPOAPP_MAN_ORDINARIA = 12;
    
    // INT85
    public static final String DATA_MAX = "21000101";
    //ATTENZIONE MODIFICARE DATA
    public static final String DATA_3047 = "20220726";
    public static final String LEGGE89_1 = "1";
    public static final String LEGGE89_2 = "2";
    public static final String COD_SA_COMUNE = "COMNOPROV";
    
  //MEV 39162 3.04.8.1
    public static final String DATA_30481 = "20230517";
    
    /*** prima di modifiche di Obino del 22.12.2014
    public static final String LEGGE89_1_DICH = "Si intende procedere all&apos;acquisizione secondo le modalit&agrave; indicate dall&apos;art.9, comma 4, del D.L.n.66/2014, convertito, con modifiche, dalla Legge n.89/2014 oppure art. 23-ter DL 90/2014";
    public static final String LEGGE89_2_DICH = "Il territorio ricade in una regione a statuto speciale o in una provincia autonoma, che non ha ancora recepito nel proprio ordinamento le disposizioni di cui all&apos;art.9. comma 4, del D.L.n.66/2014, convertito, con modifiche, dalla Legge n.89/2014 oppure art. 23-ter DL 90/2014";
    public static final String  LEGGE89_TITLE = "Informazione obbligatoria ai sensi della L. 23 giugno 2014 n. 89 o del DL 90/2014";
    */
    
    public static final String LEGGE89_1_DICH = "Si intende procedere all&apos;acquisizione secondo le modalit&agrave; indicate dall&apos;art.9, comma 4, del D.L.n.66/2014, convertito, con modifiche, dalla Legge n.89/2014 oppure dall&apos;art. 23-ter D.L. 90/2014, convertito, con modifiche, dalla Legge n. 114/2014 e successive modifiche ed integrazioni";
    public static final String LEGGE89_2_DICH = "Il territorio ricade in una regione a statuto speciale o in una provincia autonoma, che non ha ancora recepito nel proprio ordinamento le disposizioni di cui all&apos;art.9. comma 4, del D.L.n.66/2014, convertito, con modifiche, dalla Legge n.89/2014";
    public static final String  LEGGE89_TITLE = "Disposizioni in materia di acquisizione di lavori, beni e servizi da parte degli enti pubblici (art. 33 comma 3-bis D.Lgs. 163/2006)";
    // INT85 fine
    
    // is30350_RFWEBGL01Active
    public static final String  EAGG_TITLE = "Disposizioni in materia di centralizzazione della spesa pubblica (art. 9 comma 3 D.L. 66/2014)";
    public static final String  EAGG_CATMERC_999 = "999";
    public static final String  EAGG_LABEL_MOTIVI = "Motivazione richiesta CIG";
    public static final String  EAGG_LABEL_CATEGORIE = "Categorie merceologiche oggetto della fornitura di cui al DPCM soggetti aggregatori*";
    public static final String  EAGG_LABEL_INFO = "Dichiarare la motivazione sulla base della quale e' possibile acquisire il CIG. La dichiarazione ha valore di autocertificazione ai fini di eventuali successive verifiche.";
    
    //MAC #2119
    public static final String SCELTA_CONTRAENTE_AFF_DIRETTO = "31";
    
    //TICKET ALM #3832
    public static final double SOGLIA_BENI_CULTURALI = 30000;
    public static final int TIPO_ESTREMA_URGENZA_BENI_CULTURALI = 1;
    public static final int TIPO_ESTREMA_URGENZA_PROTEZIONE_CIVILE= 2;
    //FINE TICKET ALM #3832
    
    //TICKET ALM #3835
    public static final String TIPO_SCELTA_CONTRAENTE_AFF_DIRETTO_ART_5 = "12";
    public static final String TIPO_SCELTA_CONTRAENTE_AFF_RISERVATO = "32";
    //FINE TICKET ALM #3835
    
    //TICKET ALM #4194 #3582 (3.04.3)
    public static final String MOTIVO_SOCIETA_PROGETTO = "5";
    public static final int MODOREAL_CONCESSIONE_LAVORI = 3;
    public static final int MODOREAL_CONCESSIONE_SF = 4;
    public static final int MODOREAL_FINANZA_DI_PROGETTO = 5;
    public static final int MODOREAL_LOCFIN_OPEREPUBBLICHE = 8;
    public static final int MODOREAL_DISPONIBILITA = 13;
    
    //TICKET ALM #2847
    public static final int STURMENTI_MODALITA_CARTACEA = 1;
    
    //TICKET ALM #3834
	public static final int ART_REGIME_SERVIZI_SOCIALI = 37;
	public static final int ART_REGIME_RISERVATI = 39;
	public static final int ART_REGIME_RISTORAZIONE = 40;
	
	//TICKET ALM - 3.04.3
	public static final String COLL_CIG_RIP = "1";
	public static final String COLL_CIG_CONS_SUPPL = "2";
	public static final String COLL_CIG_SUPPL = "3";
	public static final String COLL_CIG_II_FASE = "9";
	public static final String COLL_CIG_AFF_DIRETTO = "8";
	public static final String COLL_CIG_NESSUNA = "10";
	public static final String SC_AFF_DIRETTO_SUPPL = "36";
	public static final int COND_CONS_COMPL = 39;
	public static final int COND_II_FASE = 42;
	public static final int COND_RIP = 43;
	public static final int COND_GARA_ANNULLATA = 45;
	public static final int DELEGA1 = 1;
	public static final int DELEGA2 = 2;
	public static final int DELEGA3 = 3;
	public static final int DELEGA4 = 4;
	//MEV 34191 3.04.8
	public static final int SOPRAVVENUTE_ESIGENZE = 7;
	//FINE MEV 34191 3.04.8
	public static final int ALTRE_CAUSE = 8;
	public static final int PROROGA_TECNICA = 18;
	
	//3.04.8 - MEV 34469
	public static final long MOTIVO_REVISIONE_PREZZI = 22;
	//FINE
	
	//TICKET ALM - 3.04.2 NG
	public static final String AFFIDAMENTO_DIRETTO = "15";
	public static final String AFFIDAMENTO_RISERVATO = "32";
	
	//TICKET ALM - 3.04.4
	public static final int INIZIATIVE_NON_IDONEE = 1;
	public static final int SA_NON_CLASSIFICATA = 2;
	public static final int AGGIUDICATA=1;
	public static final int IDF_PROPOSTA_AGGIUDICAZIONE=4;
	public static final int SVOLGIMENTO_ACCORDO_QUADRO=6;
	public static final int ID_MODO_GARA_2 = 2;
	public static final String TIPO_SCELTA_CONTRAENTE_SS = "10";
	public static final String DATA_DL50 = "20160418";
	
	//3.04.5
	public static final int ID_MODO_REAL_IDEE = 10;
	public static final int TIPO_AGG_CAT = 5;
	
	//3.04.5.1
	public static final int ID_MODO_PRECOM = 19;
	public static final String ID_MOTIVO_PRECOM = "11";
	
	//3.04.6
	public static final int SVOLGIMENTO_SDA = 7;
	public static final int SVOLGIMENTO_ASTA_ELETTRONICA = 2;
	public static final int PAT_INN = 34;
	public static final String DEFAULT_DATA_PUBB_TED = "19701231";
	public static final String DEFAULT_DATA_PUBB_TED_DATA = "31/12/1970";
	
	//3.04.7
	public static final int TIPO_ESTREMA_URGENZA_L120 = 6;
	public static final String DATA_INIZIO_DL120 = "20200717";
	public static final String DATA_FINE_DL120 = "20230630";
	

	
	
}	
