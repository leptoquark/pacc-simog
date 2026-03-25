package it.avlp.simog.db.generated; 
	/*
	*	FILE STATI_AVANZ created lun 17/08/2009 13:45:36:687
	*/

public class STATI_AVANZ {

	public final static String TABLE_NAME = "STATI_AVANZ";


	public final static String ID_AVANZAMENTO = "ID_AVANZAMENTO";
	public final static String T_ID_AVANZAMENTO = TABLE_NAME + "." + ID_AVANZAMENTO;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_AVANZAMENTO_field;

	public final static String DATA_INIZIO_AVANZAMENTO = "DATA_INIZIO_AVANZAMENTO";
	public final static String T_DATA_INIZIO_AVANZAMENTO = TABLE_NAME + "." + DATA_INIZIO_AVANZAMENTO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_AVANZAMENTO_field;

	public final static String DATA_FINE_AVANZAMENTO = "DATA_FINE_AVANZAMENTO";
	public final static String T_DATA_FINE_AVANZAMENTO = TABLE_NAME + "." + DATA_FINE_AVANZAMENTO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_AVANZAMENTO_field;

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

	public final static String NUMERO_AVANZAMENTO = "NUMERO_AVANZAMENTO";
	public final static String T_NUMERO_AVANZAMENTO = TABLE_NAME + "." + NUMERO_AVANZAMENTO;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int NUMERO_AVANZAMENTO_field;

	public final static String FLAG_PAGAMENTO = "FLAG_PAGAMENTO";
	public final static String T_FLAG_PAGAMENTO = TABLE_NAME + "." + FLAG_PAGAMENTO;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_PAGAMENTO_field;

	public final static String DATA_ANTICIPAZIONE = "DATA_ANTICIPAZIONE";
	public final static String T_DATA_ANTICIPAZIONE = TABLE_NAME + "." + DATA_ANTICIPAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_ANTICIPAZIONE_field;

	public final static String IMPORTO_ANTICIPAZIONE = "IMPORTO_ANTICIPAZIONE";
	public final static String T_IMPORTO_ANTICIPAZIONE = TABLE_NAME + "." + IMPORTO_ANTICIPAZIONE;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_ANTICIPAZIONE_field;

	public final static String DATA_RAGGIUNGIMENTO = "DATA_RAGGIUNGIMENTO";
	public final static String T_DATA_RAGGIUNGIMENTO = TABLE_NAME + "." + DATA_RAGGIUNGIMENTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_RAGGIUNGIMENTO_field;

	public final static String IMPORTO_SAL = "IMPORTO_SAL";
	public final static String T_IMPORTO_SAL = TABLE_NAME + "." + IMPORTO_SAL;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_SAL_field;

	public final static String DATA_CERTIFICATO = "DATA_CERTIFICATO";
	public final static String T_DATA_CERTIFICATO = TABLE_NAME + "." + DATA_CERTIFICATO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_CERTIFICATO_field;

	public final static String IMPORTO_CERTIFICATO = "IMPORTO_CERTIFICATO";
	public final static String T_IMPORTO_CERTIFICATO = TABLE_NAME + "." + IMPORTO_CERTIFICATO;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_CERTIFICATO_field;

	public final static String FLAG_RITARDO = "FLAG_RITARDO";
	public final static String T_FLAG_RITARDO = TABLE_NAME + "." + FLAG_RITARDO;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_RITARDO_field;

	public final static String NUM_GIORNI_SCOST = "NUM_GIORNI_SCOST";
	public final static String T_NUM_GIORNI_SCOST = TABLE_NAME + "." + NUM_GIORNI_SCOST;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int NUM_GIORNI_SCOST_field;

	public final static String NUM_GIORNI_PROROGA = "NUM_GIORNI_PROROGA";
	public final static String T_NUM_GIORNI_PROROGA = TABLE_NAME + "." + NUM_GIORNI_PROROGA;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int NUM_GIORNI_PROROGA_field;

	public final static String DENOM_AVANZ = "DENOM_AVANZ";
	public final static String T_DENOM_AVANZ = TABLE_NAME + "." + DENOM_AVANZ;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [YES]

	public String DENOM_AVANZ_field;

	public final static String ID_SCHEDA_LOCALE = "ID_SCHEDA_LOCALE";
	public final static String T_ID_SCHEDA_LOCALE = TABLE_NAME + "." + ID_SCHEDA_LOCALE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String ID_SCHEDA_LOCALE_field;
}
