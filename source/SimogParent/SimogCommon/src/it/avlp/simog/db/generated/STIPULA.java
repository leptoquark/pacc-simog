package it.avlp.simog.db.generated;

public class STIPULA {
	
	public final static String TABLE_NAME = "STIPULA";
	
	public final static String ID_STIPULA = "ID_STIPULA";
	public final static String T_ID_STIPULA = TABLE_NAME + "." + ID_STIPULA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STIPULA_field;

	public final static String DATA_INIZIO_STIPULA = "DATA_INIZIO_STIPULA";
	public final static String T_DATA_INIZIO_STIPULA = TABLE_NAME + "." + DATA_INIZIO_STIPULA;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_STIPULA_field;
	
	public final static String DATA_FINE_STIPULA = "DATA_FINE_STIPULA";
	public final static String T_DATA_FINE_STIPULA = TABLE_NAME + "." + DATA_FINE_STIPULA;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_FINE_STIPULA_field;

	public final static String DATA_STIPULA_CONTRATTO = "DATA_STIPULA_CONTRATTO";
	public final static String T_DATA_STIPULA_CONTRATTO = TABLE_NAME + "." + DATA_STIPULA_CONTRATTO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public String DATA_STIPULA_CONTRATTO_field;
	
	public final static String DATA_DECORRENZA = "DATA_DECORRENZA";
	public final static String T_DATA_DECORRENZA = TABLE_NAME + "." + DATA_DECORRENZA;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public String DATA_DECORRENZA_field;

	public final static String DATA_SCADENZA = "DATA_SCADENZA";
	public final static String T_DATA_SCADENZA = TABLE_NAME + "." + DATA_SCADENZA;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public String DATA_SCADENZA_field;

	
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
	
	public final static String ID_STATO = "ID_STATO";
	public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STATO_field;
	
	public final static String ID_SCHEDA_LOCALE = "ID_SCHEDA_LOCALE";
	public final static String T_ID_SCHEDA_LOCALE = TABLE_NAME + "." + ID_SCHEDA_LOCALE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String ID_SCHEDA_LOCALE_field;
}
