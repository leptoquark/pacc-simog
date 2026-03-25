package it.avlp.simog.db.generated; 
	/*
	*	FILE LOTTO created gio 14/12/2006 12:14:41:734
	*/

public interface LOTTO {

	public final static String TABLE_NAME = "LOTTO";

	public final static Boolean IDENTITY = Boolean.TRUE;
	
	public final static String ID_LOTTO = "ID_LOTTO";
	public final static String T_ID_LOTTO = "LOTTO.ID_LOTTO";
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long getId_Lotto();

	public final static String CIG_CICLE = "CIG_CICLE";
	public final static String T_CIG_CICLE = "LOTTO.CIG_CICLE";
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [NO]

	public int getCIG_cicle();

	public final static String CIG = "CIG";
	public final static String T_CIG = "LOTTO.CIG";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [7]
		// NULLABLE [NO]

	public String getCIG();

	public final static String CIG_KKK = "CIG_KKK";
	public final static String T_CIG_KKK = "LOTTO.CIG_KKK";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [3]
		// NULLABLE [NO]

	public String getCIG_kkk();

	public final static String OGGETTO = "OGGETTO";
	public final static String T_OGGETTO = "LOTTO.OGGETTO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1024]
		// NULLABLE [NO]

	public String getOggetto();

	public final static String SOMMA_URGENZA = "SOMMA_URGENZA";
	public final static String T_SOMMA_URGENZA = "LOTTO.SOMMA_URGENZA";
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [NO]

	public char getSomma_Urgenza();

	public final static String IMPORTO_LOTTO = "IMPORTO_LOTTO";
	public final static String T_IMPORTO_LOTTO = "LOTTO.IMPORTO_LOTTO";
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public java.math.BigDecimal getImporto_Lotto();

	public final static String IMPORTO_SA = "IMPORTO_SA";
	public final static String T_IMPORTO_SA = "LOTTO.IMPORTO_SA";
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public java.math.BigDecimal getImporto_SA();

	public final static String IMPORTO_IMPRESA = "IMPORTO_IMPRESA";
	public final static String T_IMPORTO_IMPRESA = "LOTTO.IMPORTO_IMPRESA";
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public java.math.BigDecimal getImporto_Impresa();

	public final static String DATA_PUBBLICAZIONE = "DATA_PUBBLICAZIONE";
	public final static String T_DATA_PUBBLICAZIONE = "LOTTO.DATA_PUBBLICAZIONE";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String getData_Pubblicazione();

	public final static String DATA_COMUNICAZIONE = "DATA_COMUNICAZIONE";
	public final static String T_DATA_COMUNICAZIONE = "LOTTO.DATA_COMUNICAZIONE";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String getData_Comunicazione();

	public final static String ID_GARA = "ID_GARA";
	public final static String T_ID_GARA = "LOTTO.ID_GARA";
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long getId_Gara();

	public final static String ID_TIPOLOGIA = "ID_TIPOLOGIA";
	public final static String T_ID_TIPOLOGIA = "LOTTO.ID_TIPOLOGIA";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [NO]

	public String getId_Tipologia();

	public final static String ID_CPV = "ID_CPV";
	public final static String T_ID_CPV = "LOTTO.ID_CPV";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [NO]

	public String getId_CPV();

	public final static String ID_SCELTA_CONTRAENTE = "ID_SCELTA_CONTRAENTE";
	public final static String T_ID_SCELTA_CONTRAENTE = "LOTTO.ID_SCELTA_CONTRAENTE";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [NO]

	public String getId_Scelta_Contraente();

	public final static String ID_CATEGORIA_PREVALENTE = "ID_CATEGORIA_PREVALENTE";
	public final static String T_ID_CATEGORIA_PREVALENTE = "LOTTO.ID_CATEGORIA_PREVALENTE";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [YES]

	public String getId_Categoria_prevalente();

	public final static String DATA_INIB_PAGAMENTO = "DATA_INIB_PAGAMENTO";
	public final static String T_DATA_INIB_PAGAMENTO = "LOTTO.DATA_INIB_PAGAMENTO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String getDATA_INIB_PAGAMENTO();

	public final static String DATA_SCADENZA_PAGAMENTI = "DATA_SCADENZA_PAGAMENTI";
	public final static String T_DATA_SCADENZA_PAGAMENTI = "LOTTO.DATA_SCADENZA_PAGAMENTI";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String getDATA_SCADENZA_PAGAMENTI();

	public final static String DATA_CANCELLAZIONE_LOTTO = "DATA_CANCELLAZIONE_LOTTO";
	public final static String T_DATA_CANCELLAZIONE_LOTTO = "LOTTO.DATA_CANCELLAZIONE_LOTTO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String getDATA_CANCELLAZIONE_LOTTO();
	
	public final static String ID_MOTIVAZIONE = "ID_MOTIVAZIONE";
	public final static String T_ID_MOTIVAZIONE = "LOTTO.ID_MOTIVAZIONE";
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String getId_motivazione();
	
	public final static String NOTE_CANC = "NOTE_CANC";
	public final static String T_NOTE_CANC = "LOTTO.NOTE_CANC";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1000]
		// NULLABLE [YES]

	public String getNoteCancellazione();

	public final static String DATA_CREAZIONE_LOTTO = "DATA_CREAZIONE_LOTTO";
	public final static String T_DATA_CREAZIONE_LOTTO = "LOTTO.DATA_CREAZIONE_LOTTO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String getDataCreazione();
	
	public final static String TIPO_CONTRATTO_LOTTO = "TIPO_CONTRATTO_LOTTO";
	public final static String T_TIPO_CONTRATTO_LOTTO = "LOTTO.TIPO_CONTRATTO_LOTTO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String getTIPO_CONTRATTO_LOTTO();
	

	public final static String FLAG_ESCLUSO = "FLAG_ESCLUSO";
	public final static String T_FLAG_ESCLUSO = "LOTTO.FLAG_ESCLUSO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String getFLAG_ESCLUSO();


	public final static String ID_ESCLUSIONE = "ID_ESCLUSIONE";
	public final static String T_ID_ESCLUSIONE = "LOTTO.ID_ESCLUSIONE";
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public int getID_ESCLUSIONE();
		
	//gm nuovo codice 3.0
	public final static String TRIENNIO_ANNO_INIZIO = "TRIENNIO_ANNO_INIZIO";
	public final static String T_TRIENNIO_ANNO_INIZIO = "LOTTO.TRIENNIO_ANNO_INIZIO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [4]
		// NULLABLE [YES]
	public String getTRIENNIO_ANNO_INIZIO();
	
	public final static String TRIENNIO_ANNO_FINE = "TRIENNIO_ANNO_FINE";	
	public final static String T_TRIENNIO_ANNO_FINE = "LOTTO.TRIENNIO_ANNO_FINE";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [4]
		// NULLABLE [YES]
	public String getTRIENNIO_ANNO_FINE();
	
	public final static String  TRIENNIO_PROGRESSIVO = "TRIENNIO_PROGRESSIVO";	
	public final static String  T_TRIENNIO_PROGRESSIVO = "LOTTO.TRIENNIO_PROGRESSIVO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]
	public String getTRIENNIO_PROGRESSIVO();
	
	public final static String  ANNUALE_CUI_MININF = "ANNUALE_CUI_MININF";	
	public final static String  T_ANNUALE_CUI_MININF = "LOTTO.ANNUALE_CUI_MININF";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [22]
		// NULLABLE [YES]
	public String getANNUALE_CUI_MININF();	
	//gm fine nuovo codice 3.0
	
	//gm nuovo codice pubblicazione bando 3.0
	public final static String  LUOGO_ISTAT = "LUOGO_ISTAT";	
	public final static String  T_LUOGO_ISTAT = "LOTTO.LUOGO_ISTAT";
	// COLUMN TYPE [varchar]
	// COLUMN SIZE [10]
	// NULLABLE [YES]
    public String getLUOGO_ISTAT();
    
    public final static String LUOGO_NUTS = "LUOGO_NUTS";	
	public final static String  T_LUOGO_NUTS = "LOTTO.LUOGO_NUTS";
	// COLUMN TYPE [varchar]
	// COLUMN SIZE [10]
	// NULLABLE [YES]
    public String getLUOGO_NUTS();
    
    public final static String IMPORTO_ATTUAZIONE_SICUREZZA = "IMPORTO_ATTUAZIONE_SICUREZZA";
	public final static String T_IMPORTO_ATTUAZIONE_SICUREZZA = "LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA";
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public java.math.BigDecimal getIMPORTO_ATTUAZIONE_SICUREZZA();
	//gm fine nuovo codice pubblicazione bando 3.0

	public final static String FLAG_PREVEDE_RIP = "FLAG_PREVEDE_RIP";
	public final static String T_FLAG_PREVEDE_RIP = "LOTTO.FLAG_PREVEDE_RIP";
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]


	public String getFLAG_PREVEDE_RIP();
	

	//TICKET ALM #2845
	public final static String FLAG_DL50 = "FLAG_DL50";
	public final static String T_FLAG_DL50 = "LOTTO.FLAG_DL50";
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]


	public String getFLAG_DL50();
	
	public final static String PRIMA_ANNUALITA = "PRIMA_ANNUALITA";
	public final static String T_PRIMA_ANNUALITA = "LOTTO.PRIMA_ANNUALITA";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [4]
		// NULLABLE [YES]


	public String getPRIMA_ANNUALITA();
	
	//FINE TICKET ALM #2845
	
	
	//TICKET #2846
	public final static String ID_MOTIVO = "ID_MOTIVO";
	public final static String T_ID_MOTIVO = "LOTTO.ID_MOTIVO";
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]


	public String getID_MOTIVO_COLL_CIG();
	
	//FINE TICKET #2846

	//TICKET ALM #3835
	public final static String ID_AFF_RISERVATI = "ID_AFF_RISERVATI";
	public final static String T_ID_AFF_RISERVATI = "LOTTO.ID_AFF_RISERVATI";
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]


	public int getID_AFF_RISERVATI();
	
	//FINE TICKET ALM #3835

	//TICKET ALM #3836
	public final static String FLAG_REGIME = "FLAG_REGIME";
	public final static String T_FLAG_REGIME = "LOTTO.FLAG_REGIME";
	//public final static String ID_ART_REGIME = "ID_ART_REGIME";
	//public final static String T_ID_ART_REGIME = "LOTTO.ID_ART_REGIME";
	//FINE TICKET ALM #3836
	

	public final static String FLAG_RIPETIZIONE = "FLAG_RIPETIZIONE";
	public final static String T_FLAG_RIPETIZIONE = "LOTTO.FLAG_RIPETIZIONE";
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String getFLAG_RIPETIZIONE();
	
	public final static String CIG_ORIGINE_RIP = "CIG_ORIGINE_RIP";
	public final static String T_CIG_ORIGINE_RIP = "LOTTO.CIG_ORIGINE_RIP";
		// COLUMN TYPE [char]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public String getCIG_ORIGINE_RIP();	

	// PP is3025_RFWEBGL02Active()
    public final static String ORA_SCADENZA = "ORA_SCADENZA";
    public final static String T_ORA_SCADENZA = "LOTTO.ORA_SCADENZA";
        // COLUMN TYPE [char]
        // COLUMN SIZE [5]
        // NULLABLE [YES]

    public String getORA_SCADENZA(); 
    
    public final static String DATA_SCADENZA_RICHIESTA_INVITO = "DATA_SCADENZA_RICHIESTA_INVITO";
    public final static String T_DATA_SCADENZA_RICHIESTA_INVITO = "LOTTO.DATA_SCADENZA_RICHIESTA_INVITO";
        // COLUMN TYPE [varchar]
        // COLUMN SIZE [8]
        // NULLABLE [YES]

// UN getter inutile. Riproposto in caso di generazione    
//    public String getDATA_SCADENZA_RICHIESTA_INVITO();
    
    public final static String DATA_LETTERA_INVITO = "DATA_LETTERA_INVITO";
    public final static String T_DATA_LETTERA_INVITO = "LOTTO.DATA_LETTERA_INVITO";
        // COLUMN TYPE [varchar]
        // COLUMN SIZE [8]
        // NULLABLE [YES]
    
 // UN getter inutile. Riproposto in caso di generazione
//    public final static String  FLAG_PAR_GEN_MOD1 = "FLAG_PAR_GEN_MOD1";
//    public final static String T_FLAG_PAR_GEN_MOD1 = "LOTTO.FLAG_PAR_GEN_MOD1"; // TICKET 31047: controlla modifiche sul db
    // COLUMN TYPE [char]
    // COLUMN SIZE [1]
    // NULLABLE [NO]
    
    // UN getter inutile. Riproposto in caso di generazione
//    public final static String  FLAG_PAR_GEN_MOD2 = "FLAG_PAR_GEN_MOD2";
//    public final static String T_FLAG_PAR_GEN_MOD2 = "LOTTO.FLAG_PAR_GEN_MOD2"; // TICKET 31047: controlla modifiche sul db
    // COLUMN TYPE [char]
    // COLUMN SIZE [1]
    // NULLABLE [YES]
    
	public final static String FLAG_PNRR_PNC = "FLAG_PNRR_PNC";
    public final static String T_FLAG_PNRR_PNC = "LOTTO.FLAG_PNRR_PNC";

    public final static String FLAG_PREVISIONE_QUOTA = "FLAG_PREVISIONE_QUOTA";
    public final static String T_FLAG_PREVISIONE_QUOTA = "LOTTO.FLAG_PREVISIONE_QUOTA";
    
    public final static String FLAG_MISURE_PREMIALI= "FLAG_MISURE_PREMIALI";
    public final static String T_FLAG_MISURE_PREMIALI= "LOTTO.FLAG_MISURE_PREMIALI";
    
    public final static String QUOTA_GIOVANILE = "QUOTA_GIOVANILE";
    public final static String T_QUOTA_GIOVANILE = "LOTTO.QUOTA_GIOVANILE";
    
    
    public final static String QUOTA_FEMMINILE = "QUOTA_FEMMINILE";
    public final static String T_QUOTA_FEMMINILE = "LOTTO.QUOTA_FEMMINILE";
    
 
 
// UN getter inutile. Riproposto in caso di generazione
//    public String getDATA_LETTERA_INVITO();    
    
    public final static String FLAG_CUP = "FLAG_CUP";
    public final static String T_FLAG_CUP = "LOTTO.FLAG_CUP";
       // COLUMN TYPE [char]
       // COLUMN SIZE [1]
       // NULLABLE [YES]
    
    // Ticket #20058 - 09 - 02 - 21
    public final static String DURATA_RINNOVI_RIPETIZIONI = "DURATA_RINNOVI_RIPETIZIONI";
    
 // Ticket #20057 - 09 - 02 - 21
    public final static String DURATA_AFFIDAMENTO_IN_GIORNI = "DURATA_AFFIDAMENTO_IN_GIORNI";
    
    public String getFLAG_CUP();

    //TICKET ALM - 3.04.4
    public final static String COD_CATEGORIA = "COD_CATEGORIA";
	public final static String T_COD_CATEGORIA = TABLE_NAME + "." + COD_CATEGORIA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public String getCOD_CATEGORIA();
	
	public final static String IMPORTO_OPZIONI = "IMPORTO_OPZIONI";
	public final static String T_IMPORTO_OPZIONI = TABLE_NAME +"."+IMPORTO_OPZIONI;
	
	//MEV 37010 3.04.8.1
	public final static String FLAG_DEROGA_ADESIONE = "FLAG_DEROGA_ADESIONE";
    public final static String T_FLAG_DEROGA_ADESIONE = "LOTTO.FLAG_DEROGA_ADESIONE";

	 //MEV 38205 3.04.8.1
    public final static String FLAG_USO_METODI_EDILIZIA = "FLAG_USO_METODI_EDILIZIA";
    public final static String T_FLAG_USO_METODI_EDILIZIA = "LOTTO.FLAG_USO_METODI_EDILIZIA";
    
    //3.04.9 MEV 40610
    public final static String ID_DEROGA_QUALIFICAZIONE_SA = "ID_DEROGA_QUALIFICAZIONE_SA";
    public final static String T_ID_DEROGA_QUALIFICAZIONE_SA = "LOTTO.ID_DEROGA_QUALIFICAZIONE_SA";
    
  //3.04.9 MEV 40610
    public final static String FLAG_IS_QUALIFICATA_KO = "FLAG_IS_QUALIFICATA_KO";
    public final static String T_FLAG_IS_QUALIFICATA_KO = "LOTTO.FLAG_IS_QUALIFICATA_KO";
	
    

    
}
