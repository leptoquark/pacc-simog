package it.avlp.simog.db.generated; 
	/*
	*	FILE COLLAUDO created lun 17/08/2009 13:45:36:687
	*/

public class COLLAUDO {

	public final static String TABLE_NAME = "COLLAUDO";


	public final static String ID_COLLAUDO = "ID_COLLAUDO";
	public final static String T_ID_COLLAUDO = TABLE_NAME + "." + ID_COLLAUDO;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_COLLAUDO_field;

	public final static String DATA_INIZIO_COLL = "DATA_INIZIO_COLL";
	public final static String T_DATA_INIZIO_COLL = TABLE_NAME + "." + DATA_INIZIO_COLL;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_COLL_field;

	public final static String DATA_FINE_COLL = "DATA_FINE_COLL";
	public final static String T_DATA_FINE_COLL = TABLE_NAME + "." + DATA_FINE_COLL;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_COLL_field;

	public final static String ID_STATO = "ID_STATO";
	public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STATO_field;

	public final static String ID_AGGIUDICAZIONE = "ID_AGGIUDICAZIONE";
	public final static String T_ID_AGGIUDICAZIONE = TABLE_NAME + "." + ID_AGGIUDICAZIONE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_AGGIUDICAZIONE_field;

	public final static String DATA_INIZIO_AGGIUDICAZIONE = "DATA_INIZIO_AGGIUDICAZIONE";
	public final static String T_DATA_INIZIO_AGGIUDICAZIONE = TABLE_NAME + "." + DATA_INIZIO_AGGIUDICAZIONE;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_AGGIUDICAZIONE_field;

	public final static String DATA_REGOLARE_ESEC = "DATA_REGOLARE_ESEC";
	public final static String T_DATA_REGOLARE_ESEC = TABLE_NAME + "." + DATA_REGOLARE_ESEC;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_REGOLARE_ESEC_field;

	public final static String DATA_COLLAUDO_STAT = "DATA_COLLAUDO_STAT";
	public final static String T_DATA_COLLAUDO_STAT = TABLE_NAME + "." + DATA_COLLAUDO_STAT;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_COLLAUDO_STAT_field;

	public final static String MODO_COLLAUDO = "MODO_COLLAUDO";
	public final static String T_MODO_COLLAUDO = TABLE_NAME + "." + MODO_COLLAUDO;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char MODO_COLLAUDO_field;

	public final static String DATA_NOMINA_COLL = "DATA_NOMINA_COLL";
	public final static String T_DATA_NOMINA_COLL = TABLE_NAME + "." + DATA_NOMINA_COLL;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_NOMINA_COLL_field;

	public final static String DATA_INIZIO_OPER = "DATA_INIZIO_OPER";
	public final static String T_DATA_INIZIO_OPER = TABLE_NAME + "." + DATA_INIZIO_OPER;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_INIZIO_OPER_field;

	public final static String DATA_CERT_COLLAUDO = "DATA_CERT_COLLAUDO";
	public final static String T_DATA_CERT_COLLAUDO = TABLE_NAME + "." + DATA_CERT_COLLAUDO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_CERT_COLLAUDO_field;

	public final static String DATA_DELIBERA = "DATA_DELIBERA";
	public final static String T_DATA_DELIBERA = TABLE_NAME + "." + DATA_DELIBERA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_DELIBERA_field;

	public final static String ESITO_COLLAUDO = "ESITO_COLLAUDO";
	public final static String T_ESITO_COLLAUDO = TABLE_NAME + "." + ESITO_COLLAUDO;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char ESITO_COLLAUDO_field;

	public final static String IMP_FINALE_LAVORI = "IMP_FINALE_LAVORI";
	public final static String T_IMP_FINALE_LAVORI = TABLE_NAME + "." + IMP_FINALE_LAVORI;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMP_FINALE_LAVORI_field;

	public final static String IMP_FINALE_SERVIZI = "IMP_FINALE_SERVIZI";
	public final static String T_IMP_FINALE_SERVIZI = TABLE_NAME + "." + IMP_FINALE_SERVIZI;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMP_FINALE_SERVIZI_field;

	public final static String IMP_FINALE_FORNIT = "IMP_FINALE_FORNIT";
	public final static String T_IMP_FINALE_FORNIT = TABLE_NAME + "." + IMP_FINALE_FORNIT;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMP_FINALE_FORNIT_field;

	public final static String IMP_FINALE_SICUR = "IMP_FINALE_SICUR";
	public final static String T_IMP_FINALE_SICUR = TABLE_NAME + "." + IMP_FINALE_SICUR;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMP_FINALE_SICUR_field;

	public final static String IMP_PROGETTAZIONE = "IMP_PROGETTAZIONE";
	public final static String T_IMP_PROGETTAZIONE = TABLE_NAME + "." + IMP_PROGETTAZIONE;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMP_PROGETTAZIONE_field;

	public final static String IMP_DISPOSIZIONE = "IMP_DISPOSIZIONE";
	public final static String T_IMP_DISPOSIZIONE = TABLE_NAME + "." + IMP_DISPOSIZIONE;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMP_DISPOSIZIONE_field;

	public final static String AMM_NUM_DEFINITE = "AMM_NUM_DEFINITE";
	public final static String T_AMM_NUM_DEFINITE = TABLE_NAME + "." + AMM_NUM_DEFINITE;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int AMM_NUM_DEFINITE_field;

	public final static String AMM_NUM_DADEF = "AMM_NUM_DADEF";
	public final static String T_AMM_NUM_DADEF = TABLE_NAME + "." + AMM_NUM_DADEF;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int AMM_NUM_DADEF_field;

	public final static String AMM_IMPORTO_RICH = "AMM_IMPORTO_RICH";
	public final static String T_AMM_IMPORTO_RICH = TABLE_NAME + "." + AMM_IMPORTO_RICH;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal AMM_IMPORTO_RICH_field;

	public final static String AMM_IMPORTO_DEF = "AMM_IMPORTO_DEF";
	public final static String T_AMM_IMPORTO_DEF = TABLE_NAME + "." + AMM_IMPORTO_DEF;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal AMM_IMPORTO_DEF_field;

	public final static String ARB_NUM_DEFINITE = "ARB_NUM_DEFINITE";
	public final static String T_ARB_NUM_DEFINITE = TABLE_NAME + "." + ARB_NUM_DEFINITE;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int ARB_NUM_DEFINITE_field;

	public final static String ARB_NUM_DADEF = "ARB_NUM_DADEF";
	public final static String T_ARB_NUM_DADEF = TABLE_NAME + "." + ARB_NUM_DADEF;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int ARB_NUM_DADEF_field;

	public final static String ARB_IMPORTO_RICH = "ARB_IMPORTO_RICH";
	public final static String T_ARB_IMPORTO_RICH = TABLE_NAME + "." + ARB_IMPORTO_RICH;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal ARB_IMPORTO_RICH_field;

	public final static String ARB_IMPORTO_DEF = "ARB_IMPORTO_DEF";
	public final static String T_ARB_IMPORTO_DEF = TABLE_NAME + "." + ARB_IMPORTO_DEF;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal ARB_IMPORTO_DEF_field;

	public final static String GIU_NUM_DEFINITE = "GIU_NUM_DEFINITE";
	public final static String T_GIU_NUM_DEFINITE = TABLE_NAME + "." + GIU_NUM_DEFINITE;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int GIU_NUM_DEFINITE_field;

	public final static String GIU_NUM_DADEF = "GIU_NUM_DADEF";
	public final static String T_GIU_NUM_DADEF = TABLE_NAME + "." + GIU_NUM_DADEF;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int GIU_NUM_DADEF_field;

	public final static String GIU_IMPORTO_RICH = "GIU_IMPORTO_RICH";
	public final static String T_GIU_IMPORTO_RICH = TABLE_NAME + "." + GIU_IMPORTO_RICH;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal GIU_IMPORTO_RICH_field;

	public final static String GIU_IMPORTO_DEF = "GIU_IMPORTO_DEF";
	public final static String T_GIU_IMPORTO_DEF = TABLE_NAME + "." + GIU_IMPORTO_DEF;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal GIU_IMPORTO_DEF_field;

	public final static String TRA_NUM_DEFINITE = "TRA_NUM_DEFINITE";
	public final static String T_TRA_NUM_DEFINITE = TABLE_NAME + "." + TRA_NUM_DEFINITE;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int TRA_NUM_DEFINITE_field;

	public final static String TRA_NUM_DADEF = "TRA_NUM_DADEF";
	public final static String T_TRA_NUM_DADEF = TABLE_NAME + "." + TRA_NUM_DADEF;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int TRA_NUM_DADEF_field;

	public final static String TRA_IMPORTO_RICH = "TRA_IMPORTO_RICH";
	public final static String T_TRA_IMPORTO_RICH = TABLE_NAME + "." + TRA_IMPORTO_RICH;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal TRA_IMPORTO_RICH_field;

	public final static String TRA_IMPORTO_DEF = "TRA_IMPORTO_DEF";
	public final static String T_TRA_IMPORTO_DEF = TABLE_NAME + "." + TRA_IMPORTO_DEF;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal TRA_IMPORTO_DEF_field;

	public final static String LAVORI_ESTESI = "LAVORI_ESTESI";
	public final static String T_LAVORI_ESTESI = TABLE_NAME + "." + LAVORI_ESTESI;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char LAVORI_ESTESI_field;

	public final static String ID_SCHEDA_LOCALE = "ID_SCHEDA_LOCALE";
	public final static String T_ID_SCHEDA_LOCALE = TABLE_NAME + "." + ID_SCHEDA_LOCALE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String ID_SCHEDA_LOCALE_field;

	public final static String ID_MOTIVO_VAR_CO = "ID_MOTIVO_VAR_CO";
	public final static String T_ID_MOTIVO_VAR = TABLE_NAME + "." + ID_MOTIVO_VAR_CO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String ID_MOTIVO_VAR_CO_field;
}
