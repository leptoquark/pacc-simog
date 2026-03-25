package it.avlp.simog.db.generated; 
	/*
	*	FILE ACCORDI created lun 17/08/2009 13:45:36:687
	*/

public class ACCORDI {

	public final static String TABLE_NAME = "ACCORDI";


	public final static String ID_ACCORDO = "ID_ACCORDO";
	public final static String T_ID_ACCORDO = TABLE_NAME + "." + ID_ACCORDO;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_ACCORDO_field;

	public final static String DATA_INIZIO_ACC = "DATA_INIZIO_ACC";
	public final static String T_DATA_INIZIO_ACC = TABLE_NAME + "." + DATA_INIZIO_ACC;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_ACC_field;

	public final static String DATA_FINE_ACC = "DATA_FINE_ACC";
	public final static String T_DATA_FINE_ACC = TABLE_NAME + "." + DATA_FINE_ACC;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_ACC_field;

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

	public final static String DATA_ACCORDO = "DATA_ACCORDO";
	public final static String T_DATA_ACCORDO = TABLE_NAME + "." + DATA_ACCORDO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_ACCORDO_field;

	public final static String ONERI_DERIVANTI = "ONERI_DERIVANTI";
	public final static String T_ONERI_DERIVANTI = TABLE_NAME + "." + ONERI_DERIVANTI;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal ONERI_DERIVANTI_field;

	public final static String NUM_RISERVE = "NUM_RISERVE";
	public final static String T_NUM_RISERVE = TABLE_NAME + "." + NUM_RISERVE;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int NUM_RISERVE_field;

	public final static String ID_SCHEDA_LOCALE = "ID_SCHEDA_LOCALE";
	public final static String T_ID_SCHEDA_LOCALE = TABLE_NAME + "." + ID_SCHEDA_LOCALE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String ID_SCHEDA_LOCALE_field;
}
