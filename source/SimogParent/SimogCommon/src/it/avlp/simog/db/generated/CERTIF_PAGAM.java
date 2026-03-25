package it.avlp.simog.db.generated; 
	/*
	*	FILE CERTIF_PAGAM created mar 09/10/2007 11:38:24:193
	*/

public class CERTIF_PAGAM {

	public final static String TABLE_NAME = "CERTIF_PAGAM";


	public final static String ID_RECORD = "ID_RECORD";
	public final static String T_ID_RECORD = TABLE_NAME + "." + ID_RECORD;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_RECORD_field;

	public final static String DATA_INIZIO_RECORD = "DATA_INIZIO_RECORD";
	public final static String T_DATA_INIZIO_RECORD = TABLE_NAME + "." + DATA_INIZIO_RECORD;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_RECORD_field;

	public final static String DATA_FINE_RECORD = "DATA_FINE_RECORD";
	public final static String T_DATA_FINE_RECORD = TABLE_NAME + "." + DATA_FINE_RECORD;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_RECORD_field;

	public final static String ID_STATO = "ID_STATO";
	public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STATO_field;

	public final static String ID_AVANZAMENTO = "ID_AVANZAMENTO";
	public final static String T_ID_AVANZAMENTO = TABLE_NAME + "." + ID_AVANZAMENTO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_AVANZAMENTO_field;

	public final static String DATA_INIZIO_AVANZAMENTO = "DATA_INIZIO_AVANZAMENTO";
	public final static String T_DATA_INIZIO_AVANZAMENTO = TABLE_NAME + "." + DATA_INIZIO_AVANZAMENTO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_AVANZAMENTO_field;

	public final static String NUM_STATO_AVANZ = "NUM_STATO_AVANZ";
	public final static String T_NUM_STATO_AVANZ = TABLE_NAME + "." + NUM_STATO_AVANZ;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int NUM_STATO_AVANZ_field;

	public final static String DATA_EMISSIONE = "DATA_EMISSIONE";
	public final static String T_DATA_EMISSIONE = TABLE_NAME + "." + DATA_EMISSIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_EMISSIONE_field;

	public final static String IMPORTO_PAGAM = "IMPORTO_PAGAM";
	public final static String T_IMPORTO_PAGAM = TABLE_NAME + "." + IMPORTO_PAGAM;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_PAGAM_field;

	public final static String IMPORTO_INTER = "IMPORTO_INTER";
	public final static String T_IMPORTO_INTER = TABLE_NAME + "." + IMPORTO_INTER;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_INTER_field;
}
