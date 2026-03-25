package it.avlp.simog.db.generated; 
	/*
	*	FILE GARA created gio 14/12/2006 12:14:41:734
	*/

public interface GARA {

	public final static String TABLE_NAME = "GARA";
	
	public final static Boolean IDENTITY = Boolean.TRUE;

	public final static String ID_GARA = "ID_GARA";
	public final static String T_ID_GARA = "GARA.ID_GARA";
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long getId_Gara();

	public final static String OGGETTO = "OGGETTO";
	public final static String T_OGGETTO = "GARA.OGGETTO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1024]
		// NULLABLE [NO]

	public String getOggetto();

	public final static String DATA_CREAZIONE = "DATA_CREAZIONE";
	public final static String T_DATA_CREAZIONE = "GARA.DATA_CREAZIONE";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [NO]

	public String getData_creazione();

	public final static String CF_UTENTE = "CF_UTENTE";
	public final static String T_CF_UTENTE = "GARA.CF_UTENTE";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [NO]

	public String getCF_UTENTE();

	public final static String ID_STAZIONE_APPALTANTE = "ID_STAZIONE_APPALTANTE";
	public final static String T_ID_STAZIONE_APPALTANTE = "GARA.ID_STAZIONE_APPALTANTE";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [40]
		// NULLABLE [NO]

	public String getID_STAZIONE_APPALTANTE();

	public final static String DENOM_STAZIONE_APPALTANTE = "DENOM_STAZIONE_APPALTANTE";
	public final static String T_DENOM_STAZIONE_APPALTANTE = "GARA.DENOM_STAZIONE_APPALTANTE";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [80]
		// NULLABLE [NO]

	public String getDENOM_STAZIONE_APPALTANTE();

	public final static String CF_AMMINISTRAZIONE = "CF_AMMINISTRAZIONE";
	public final static String T_CF_AMMINISTRAZIONE = "GARA.CF_AMMINISTRAZIONE";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [NO]

	public String getCF_AMMINISTRAZIONE();

	public final static String DENOM_AMMINISTRAZIONE = "DENOM_AMMINISTRAZIONE";
	public final static String T_DENOM_AMMINISTRAZIONE = "GARA.DENOM_AMMINISTRAZIONE";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [80]
		// NULLABLE [NO]

	public String getDENOM_AMMINISTRAZIONE();
	
	public final static String ID_OSSERVATORIO = "ID_OSSERVATORIO";
	public final static String T_ID_OSSERVATORIO = "GARA.ID_OSSERVATORIO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [3]
		// NULLABLE [NO]
		
	public String getID_OSSERVATORIO();
		
	public final static String ID_STATO = "ID_STATO_GARA";
	public final static String T_ID_STATO = "GARA.ID_STATO_GARA";
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long getID_STATO_GARA();
		
	public final static String DATA_COMUN = "DATA_COMUN";
	public final static String T_DATA_COMUN = "GARA.DATA_COMUN";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String getDATA_COMUN();
	
	public final static String DATA_TERMINE_PAGAMENTO = "DATA_TERMINE_PAGAMENTO";
	public final static String T_DATA_TERMINE_PAGAMENTO = "GARA.DATA_TERMINE_PAGAMENTO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String getDATA_TERMINE_PAGAMENTO();
	
	public final static String DATA_INIB_PAGAM = "DATA_INIB_PAGAM";
	public final static String T_DATA_INIB_PAGAM = "GARA.DATA_INIB_PAGAM";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String getDATA_INIB_PAGAM();

	public final static String DATA_CANCELLAZIONE_GARA = "DATA_CANCELLAZIONE_GARA";
	public final static String T_DATA_CANCELLAZIONE_GARA = "GARA.DATA_CANCELLAZIONE_GARA";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String getDATA_CANCELLAZIONE_GARA();

	public final static String DATA_CONFERMA_GARA = "DATA_CONFERMA_GARA";
	public final static String T_DATA_CONFERMA_GARA = "GARA.DATA_CONFERMA_GARA";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String getDATA_CONFERMA_GARA();
	
	public final static String IMPORTO_GARA = "IMPORTO_GARA";
	public final static String T_IMPORTO_GARA = "GARA.IMPORTO_GARA";
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public java.math.BigDecimal getIMPORTO_GARA();
	
	public final static String IMPORTO_SA_GARA = "IMPORTO_SA_GARA";
	public final static String T_IMPORTO_SA_GARA = "GARA.IMPORTO_SA_GARA";
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public java.math.BigDecimal getIMPORTO_SA_GARA();


	// NEWCIG

	public final static String TIPO_SCHEDA_GARA = "TIPO_SCHEDA_GARA";
	public final static String T_TIPO_SCHEDA_GARA = "GARA.TIPO_SCHEDA_GARA";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String getTIPO_SCHEDA_GARA();

	public final static String ID_MODO_GARA = "ID_MODO_GARA";
	public final static String T_ID_MODO_GARA = "GARA.ID_MODO_GARA";
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public int getID_MODO_GARA();
	
	public final static String ID_MODO_REAL = "ID_MODO_REAL";
	public final static String T_ID_MODO_REAL = "GARA.ID_MODO_REAL";
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public int getID_MODO_REAL();
	
	//TICKET ALM #664
	public final static String ID_SVOLGIMENTO = "ID_SVOLGIMENTO";
	public final static String T_ID_SVOLGIMENTO = "GARA.ID_SVOLGIMENTO";
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public int getID_SVOLGIMENTO();
	//FINE TICKET ALM #664
	

	//TICKET ALM #3832
	public final static String ID_ESTREMA_URGENZA = "ID_ESTREMA_URGENZA";
	public final static String T_ID_ESTREMA_URGENZA = "GARA.ID_ESTREMA_URGENZA";
			// COLUMN TYPE [bigint]
			// COLUMN SIZE [19]
			// NULLABLE [YES]

	public int getID_ESTREMA_URGENZA();
	//FINE TICKET ALM #3832
	
	//TICKET ALM #3834
	public final static String ID_ALLEGATO_IX = "ID_ALLEGATO_IX";
	public final static String T_ID_ALLEGATO_IX = "GARA.ID_ALLEGATO_IX";
				// COLUMN TYPE [bigint]
				// COLUMN SIZE [19]
				// NULLABLE [YES]

	public int getID_ALLEGATO_IX();
	//FINE TICKET ALM #3834
	

	public final static String ID_MOTIVAZIONE_CANC = "ID_MOTIVAZIONE_CANC";
	public final static String T_ID_MOTIVAZIONE_CANC = "GARA.ID_MOTIVAZIONE_CANC";
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]
	
	public int getID_MOTIVAZIONE_CANC();
	
	public final static String NOTE_CANC_GARA = "NOTE_CANC_GARA";
	public final static String T_NOTE_CANC_GARA = "GARA.NOTE_CANC_GARA";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1000]
		// NULLABLE [YES]

	public String getNOTE_CANC_GARA();

	public final static String CIG_ACC_QUADRO = "CIG_ACC_QUADRO";
	public final static String T_CIG_ACC_QUADRO = "GARA.CIG_ACC_QUADRO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public String getCIG_ACC_QUADRO();
	
	//gm aggiunto per pubblicazione bando gara
	public static final String DATA_PERFEZIONAMENTO_BANDO = "DATA_PERFEZIONAMENTO_BANDO";
	public static final String T_DATA_PERFEZIONAMENTO_BANDO = "GARA.DATA_PERFEZIONAMENTO_BANDO";
        // COLUMN TYPE [varchar]
	    // COLUMN SIZE [8]
	    // NULLABLE [YES]
	
	public String getDATA_PERFEZIONAMENTO_BANDO();
	
	//gm nuovo codice pubblicazione bando 3.0
	public final static String ID_PUBBLICAZIONE = "ID_PUBBLICAZIONE";
	public final static String T_ID_PUBBLICAZIONE = TABLE_NAME + "." + ID_PUBBLICAZIONE;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long getIdPubblicazione();

	public final static String DATA_INIZIO_PUBB = "DATA_INIZIO_PUBB";
	public final static String T_DATA_INIZIO_PUBB = TABLE_NAME + "." + DATA_INIZIO_PUBB;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Timestamp getDataInizioPubblicazione();
	//gm fine nuovo codice pubblicazione bando 3.0
	
	// 659 nuovo campo simog
	public final static String DURATA_GIORNI = "DURATA_GIORNI";
	public final static String T_DURATA_GIORNI = TABLE_NAME + "." + DURATA_GIORNI;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public Integer getDurataGiorni();
	
	//gm nuovo campo simog 3.04
	public final static String NUMERO_LOTTI = "NUMERO_LOTTI";
	public final static String T_NUMERO_LOTTI = TABLE_NAME + "." + NUMERO_LOTTI;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public Integer getNumeroLotti();
	
	public final static String ID_MOTIVO_VAR = "ID_MOTIVO_VAR";
	public final static String T_ID_MOTIVO_VAR = "GARA.ID_MOTIVO_VAR";
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long getIdMotivoVariazioneSA();

   public static final String PROVV_PRESA_CARICO = "PROVV_PRESA_CARICO";
   public static final String T_PROVV_PRESA_CARICO = "GARA.PROVV_PRESA_CARICO";
       // COLUMN TYPE [varchar]
       // COLUMN SIZE [8]
       // NULLABLE [YES]
   
   public String getPROVV_PRESA_CARICO();
   
   public static final String ESCLUSO_AVCPASS = "ESCLUSO_AVCPASS";
   public static final String T_ESCLUSO_AVCPASS = "GARA.ESCLUSO_AVCPASS";
       // COLUMN TYPE [varchar]
       // COLUMN SIZE [1]
       // NULLABLE [YES]
   
   public String getESCLUSO_AVCPASS();   

   // INT85
   public final static String SCELTA_LEGGE89 = "SCELTA_LEGGE89";
   public final static String T_SCELTA_LEGGE89 = TABLE_NAME + "." + SCELTA_LEGGE89;
      // COLUMN TYPE [int]
      // COLUMN SIZE [4]
      // NULLABLE [YES]

   public int getSCELTA_LEGGE89();

   // INT85
   public final static String TIPOSA_BDNCP = "TIPOSA_BDNCP";
   public final static String T_TIPOSA_BDNCP = TABLE_NAME + "." + TIPOSA_BDNCP;
      // COLUMN TYPE [varchar]
      // COLUMN SIZE [20]
      // NULLABLE [YES]

   public String getTIPOSA_BDNCP();

   // INT87
   public final static String URGENZA_DL133 = "URGENZA_DL133";
   public final static String T_URGENZA_DL133 = TABLE_NAME + "." + URGENZA_DL133;
      // COLUMN TYPE [varchar]
      // COLUMN SIZE [1]
      // NULLABLE [YES]

   public String getURGENZA_DL133();


   // is30350_RFWEBGL01Active
   public final static String COD_MOTIVO_EAGG = "COD_MOTIVO_EAGG";
   public final static String T_COD_MOTIVO_EAGG = TABLE_NAME + "." + COD_MOTIVO_EAGG;
      // COLUMN TYPE [bigint]
      // NULLABLE [YES]

   public int getCOD_MOTIVO_EAGG();
   
   public final static String CODICE_AUSA = "CODICE_AUSA";
   public final static String T_CODICE_AUSA = TABLE_NAME + "." +CODICE_AUSA;
   
   //MAC 42787  3.04.9.2
   public final static String LINK_AFFIDAMENTO_DIRETTO = "LINK_AFFIDAMENTO_DIRETTO";
   public final static String T_LINK_AFFIDAMENTO_DIRETTO = TABLE_NAME + "." + LINK_AFFIDAMENTO_DIRETTO;
  		// COLUMN TYPE [varchar]
  		// COLUMN SIZE [250]
  		// NULLABLE [YES]
  	//FINE MAC

}
