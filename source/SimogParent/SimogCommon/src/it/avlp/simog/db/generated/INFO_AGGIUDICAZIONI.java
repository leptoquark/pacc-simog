package it.avlp.simog.db.generated; 
	/*
	*	FILE INFO_AGGIUDICAZIONI created lun 17/08/2009 13:45:36:687
	*/

public class INFO_AGGIUDICAZIONI {

	public final static String TABLE_NAME = "INFO_AGGIUDICAZIONI";


	public final static String ID_INFO = "ID_INFO";
	public final static String T_ID_INFO = TABLE_NAME + "." + ID_INFO;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_INFO_field;

	public final static String DATA_INIZIO_INFO = "DATA_INIZIO_INFO";
	public final static String T_DATA_INIZIO_INFO = TABLE_NAME + "." + DATA_INIZIO_INFO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_INFO_field;

	public final static String ID_LOTTO = "ID_LOTTO";
	public final static String T_ID_LOTTO = TABLE_NAME + "." + ID_LOTTO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_LOTTO_field;

	public final static String CIG_CICLE = "CIG_CICLE";
	public final static String T_CIG_CICLE = TABLE_NAME + "." + CIG_CICLE;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [NO]

	public int CIG_CICLE_field;

	public final static String CIG = "CIG";
	public final static String T_CIG = TABLE_NAME + "." + CIG;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [7]
		// NULLABLE [NO]

	public String CIG_field;

	public final static String DATA_FINE_INFO = "DATA_FINE_INFO";
	public final static String T_DATA_FINE_INFO = TABLE_NAME + "." + DATA_FINE_INFO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_INFO_field;

	public final static String ID_STATO = "ID_STATO";
	public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STATO_field;

	public final static String ID_PUBBLICAZIONE = "ID_PUBBLICAZIONE";
	public final static String T_ID_PUBBLICAZIONE = TABLE_NAME + "." + ID_PUBBLICAZIONE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_PUBBLICAZIONE_field;

	public final static String DATA_INIZIO_PUBB = "DATA_INIZIO_PUBB";
	public final static String T_DATA_INIZIO_PUBB = TABLE_NAME + "." + DATA_INIZIO_PUBB;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_PUBB_field;

	public final static String FLAG_ENTE_SPECIALE = "FLAG_ENTE_SPECIALE";
	public final static String T_FLAG_ENTE_SPECIALE = TABLE_NAME + "." + FLAG_ENTE_SPECIALE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String FLAG_ENTE_SPECIALE_field;

	public final static String TIPO_CONTRATTO = "TIPO_CONTRATTO";
	public final static String T_TIPO_CONTRATTO = TABLE_NAME + "." + TIPO_CONTRATTO;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char TIPO_CONTRATTO_field;

	public final static String CF_AMM = "CF_AMM";
	public final static String T_CF_AMM = TABLE_NAME + "." + CF_AMM;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CF_AMM_field;

	public final static String DEN_AMM = "DEN_AMM";
	public final static String T_DEN_AMM = TABLE_NAME + "." + DEN_AMM;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [YES]

	public String DEN_AMM_field;

	public final static String CF_SA = "CF_SA";
	public final static String T_CF_SA = TABLE_NAME + "." + CF_SA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CF_SA_field;

	public final static String DEN_SA = "DEN_SA";
	public final static String T_DEN_SA = TABLE_NAME + "." + DEN_SA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [YES]

	public String DEN_SA_field;

	public final static String CODICE_CC = "CODICE_CC";
	public final static String T_CODICE_CC = TABLE_NAME + "." + CODICE_CC;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String CODICE_CC_field;

	public final static String DENOM_CC = "DENOM_CC";
	public final static String T_DENOM_CC = TABLE_NAME + "." + DENOM_CC;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [YES]

	public String DENOM_CC_field;

	public final static String ID_CATEG_SA = "ID_CATEG_SA";
	public final static String T_ID_CATEG_SA = TABLE_NAME + "." + ID_CATEG_SA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [YES]

	public String ID_CATEG_SA_field;

	public final static String FLAG_SA_AGENTE = "FLAG_SA_AGENTE";
	public final static String T_FLAG_SA_AGENTE = TABLE_NAME + "." + FLAG_SA_AGENTE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_SA_AGENTE_field;

	public final static String CF_AMM_AGENTE = "CF_AMM_AGENTE";
	public final static String T_CF_AMM_AGENTE = TABLE_NAME + "." + CF_AMM_AGENTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CF_AMM_AGENTE_field;

	public final static String DEN_AMM_AGENTE = "DEN_AMM_AGENTE";
	public final static String T_DEN_AMM_AGENTE = TABLE_NAME + "." + DEN_AMM_AGENTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [YES]

	public String DEN_AMM_AGENTE_field;

	public final static String ID_TIPOLOGIA_SA = "ID_TIPOLOGIA_SA";
	public final static String T_ID_TIPOLOGIA_SA = TABLE_NAME + "." + ID_TIPOLOGIA_SA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_TIPOLOGIA_SA_field;

	public final static String CF_RUP = "CF_RUP";
	public final static String T_CF_RUP = TABLE_NAME + "." + CF_RUP;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [16]
		// NULLABLE [YES]

	public String CF_RUP_field;

	public final static String PROVV_PRESA_CARICO = "PROVV_PRESA_CARICO";
	public final static String T_PROVV_PRESA_CARICO = TABLE_NAME + "." + PROVV_PRESA_CARICO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [YES]

	public String PROVV_PRESA_CARICO_field;

	public final static String ESITO_PROCEDURA = "ESITO_PROCEDURA";
	public final static String T_ESITO_PROCEDURA = TABLE_NAME + "." + ESITO_PROCEDURA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char ESITO_PROCEDURA_field;

	public final static String ID_SCHEDA_LOCALE = "ID_SCHEDA_LOCALE";
	public final static String T_ID_SCHEDA_LOCALE = TABLE_NAME + "." + ID_SCHEDA_LOCALE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String ID_SCHEDA_LOCALE_field;
	
	//gm nuovi campi dati comuni
	public final static String ID_TIPO_PROCEDURA = "ID_TIPO_PROCEDURA";
	public final static String T_ID_TIPO_PROCEDURA = TABLE_NAME + "." + ID_TIPO_PROCEDURA;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_TIPO_PROCEDURA_field;
	
	public final static String DURATA_CONVENZIONE = "DURATA_CONVENZIONE";
	public final static String T_DURATA_CONVENZIONE = TABLE_NAME + "." + DURATA_CONVENZIONE;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public int DURATA_CONVENZIONE_field;
	
	public final static String FLAG_PROCEDE_STIPULA = "FLAG_PROCEDE_STIPULA";
	public final static String T_FLAG_PROCEDE_STIPULA = TABLE_NAME + "." + FLAG_PROCEDE_STIPULA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [YES]

	public String FLAG_PROCEDE_STIPULA_field;
	
    public final static String ORIGINE = "ORIGINE";
    public final static String T_ORIGINE = TABLE_NAME + "." + ORIGINE;
        // COLUMN TYPE [bigint]
        // COLUMN SIZE [19]
        // NULLABLE [YES]

    public String ORIGINE_field;	
    
}
