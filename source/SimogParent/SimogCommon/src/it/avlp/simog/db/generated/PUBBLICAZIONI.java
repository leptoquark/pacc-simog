package it.avlp.simog.db.generated; 
	/*
	*	FILE PUBBLICAZIONI created lun 17/08/2009 13:45:36:687
	*/

public class PUBBLICAZIONI {

	public final static String TABLE_NAME = "PUBBLICAZIONI";


	public final static String ID_PUBBLICAZIONE = "ID_PUBBLICAZIONE";
	public final static String T_ID_PUBBLICAZIONE = TABLE_NAME + "." + ID_PUBBLICAZIONE;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_PUBBLICAZIONE_field;

	public final static String DATA_INIZIO_PUBB = "DATA_INIZIO_PUBB";
	public final static String T_DATA_INIZIO_PUBB = TABLE_NAME + "." + DATA_INIZIO_PUBB;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_PUBB_field;

	public final static String DATA_FINE_PUBB = "DATA_FINE_PUBB";
	public final static String T_DATA_FINE_PUBB = TABLE_NAME + "." + DATA_FINE_PUBB;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_PUBB_field;

	public final static String DATA_ALBO = "DATA_ALBO";
	public final static String T_DATA_ALBO = TABLE_NAME + "." + DATA_ALBO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_ALBO_field;

	public final static String DATA_GUCE = "DATA_GUCE";
	public final static String T_DATA_GUCE = TABLE_NAME + "." + DATA_GUCE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_GUCE_field;

	public final static String DATA_GURI = "DATA_GURI";
	public final static String T_DATA_GURI = TABLE_NAME + "." + DATA_GURI;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_GURI_field;

	public final static String QUOTIDIANI_NAZ = "QUOTIDIANI_NAZ";
	public final static String T_QUOTIDIANI_NAZ = TABLE_NAME + "." + QUOTIDIANI_NAZ;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int QUOTIDIANI_NAZ_field;

	public final static String QUOTIDIANI_REG = "QUOTIDIANI_REG";
	public final static String T_QUOTIDIANI_REG = TABLE_NAME + "." + QUOTIDIANI_REG;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int QUOTIDIANI_REG_field;

	public final static String ID_STATO = "ID_STATO";
	public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STATO_field;

	public final static String PROFILO_COMMITTENTE = "PROFILO_COMMITTENTE";
	public final static String T_PROFILO_COMMITTENTE = TABLE_NAME + "." + PROFILO_COMMITTENTE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char PROFILO_COMMITTENTE_field;

	public final static String SITO_MINISTERO_INF_TRASP = "SITO_MINISTERO_INF_TRASP";
	public final static String T_SITO_MINISTERO_INF_TRASP = TABLE_NAME + "." + SITO_MINISTERO_INF_TRASP;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char SITO_MINISTERO_INF_TRASP_field;

	public final static String SITO_OSSERVATORIO_CP = "SITO_OSSERVATORIO_CP";
	public final static String T_SITO_OSSERVATORIO_CP = TABLE_NAME + "." + SITO_OSSERVATORIO_CP;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char SITO_OSSERVATORIO_CP_field;

	public final static String DATA_BORE = "DATA_BORE";
	public final static String T_DATA_BORE = TABLE_NAME + "." + DATA_BORE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_BORE_field;

	public final static String PERIODICI = "PERIODICI";
	public final static String T_PERIODICI = TABLE_NAME + "." + PERIODICI;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]
	
	public final static String ID_SCHEDA_LOCALE = "ID_SCHEDA_LOCALE";
	public final static String T_ID_SCHEDA_LOCALE = TABLE_NAME + "." + ID_SCHEDA_LOCALE;
	// COLUMN TYPE [varchar] (?)
	// COLUMN SIZE [50]		(?)
	// NULLABLE [YES]		(?)
	
	public int PERIODICI_field;
	
	//gm nuovo codice pubblicazione bando 3.0
	
	public final static String NUMERO_GUCE = "NUMERO_GUCE";
	public final static String T_NUMERO_GUCE = TABLE_NAME + "." + NUMERO_GUCE;
		// COLUMN TYPE [varchar2]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String NUMERO_GUCE_field;
	
	public final static String NUMERO_GURI = "NUMERO_GURI";
	public final static String T_NUMERO_GURI = TABLE_NAME + "." + NUMERO_GURI;
		// COLUMN TYPE [varchar2]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String NUMERO_GURI_field;
	
	public final static String NUMERO_BORE = "NUMERO_BORE";
	public final static String T_NUMERO_BORE = TABLE_NAME + "." + NUMERO_BORE;
		// COLUMN TYPE [varchar2]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String NUMERO_BORE_field;
	
	public final static String LINK_SITO_COMMITTENTE = "LINK_SITO_COMMITTENTE";
	public final static String T_LINK_SITO_COMMITTENTE = TABLE_NAME + "." + LINK_SITO_COMMITTENTE;
		// COLUMN TYPE [varchar2]
		// COLUMN SIZE [250]
		// NULLABLE [YES]

	public String LINK_SITO_COMMITTENTE_field;
	
	//gm fine nuovo codice pubblicazione bando 3.0

	public final static String TIPO_OPERAZIONE = "TIPO_OPERAZIONE";
	public final static String T_TIPO_OPERAZIONE = TABLE_NAME + "." + TIPO_OPERAZIONE;
	// COLUMN TYPE [varchar] (?)
	// COLUMN SIZE [50]		(?)
	// NULLABLE [YES]		(?)

	public final static String FLAG_BENICULT = "FLAG_BENICULT";
	public final static String T_FLAG_BENICULT = TABLE_NAME + "." + FLAG_BENICULT;

	public final static String FLAG_SOSPESO = "FLAG_SOSPESO";
	public final static String T_FLAG_SOSPESO = TABLE_NAME + "." + FLAG_SOSPESO;
	
	//MARRA MEV 34470  3.04.8

	public final static String LINK_AFFIDAMENTO_DIRETTO = "LINK_AFFIDAMENTO_DIRETTO";
	public final static String T_LINK_AFFIDAMENTO_DIRETTO = TABLE_NAME + "." + LINK_AFFIDAMENTO_DIRETTO;
		// COLUMN TYPE [varchar2]
		// COLUMN SIZE [250]
		// NULLABLE [YES]
	//FINE MEV
}
