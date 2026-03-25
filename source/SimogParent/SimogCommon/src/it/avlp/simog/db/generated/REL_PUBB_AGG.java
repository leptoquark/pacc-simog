package it.avlp.simog.db.generated;

public class REL_PUBB_AGG {
	public final static String TABLE_NAME = "REL_PUBB_AGG";


	public final static String ID_GARA = "ID_GARA";
	public final static String T_ID_GARA = TABLE_NAME + "." + ID_GARA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_GARA_field;
	
	public final static String ID_PUBBLICAZIONE = "ID_PUBBLICAZIONE";
	public final static String T_ID_PUBBLICAZIONE = TABLE_NAME + "." + ID_PUBBLICAZIONE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_PUBBLICAZIONE_field;

	public final static String DATA_INIZIO_PUBB = "DATA_INIZIO_PUBB";
	public final static String T_DATA_INIZIO_PUBB = TABLE_NAME + "." + DATA_INIZIO_PUBB;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_PUBB_field;
	
	public final static String ID_AGGIUDICAZIONE = "ID_AGGIUDICAZIONE";
	public final static String T_ID_AGGIUDICAZIONE = TABLE_NAME + "." + ID_AGGIUDICAZIONE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_AGGIUDICAZIONE_field;

	public final static String DATA_INIZIO_AGG = "DATA_INIZIO_AGG";
	public final static String T_DATA_INIZIO_AGG = TABLE_NAME + "." + DATA_INIZIO_AGG;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_AGG_field;
}
