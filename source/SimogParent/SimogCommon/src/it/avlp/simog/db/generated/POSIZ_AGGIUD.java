package it.avlp.simog.db.generated; 
	/*
	*	FILE POSIZ_AGGIUD created lun 17/08/2009 13:45:36:687
	*/

public class POSIZ_AGGIUD {

	public final static String TABLE_NAME = "POSIZ_AGGIUD";


	public final static String ID_INIZIO = "ID_INIZIO";
	public final static String T_ID_INIZIO = TABLE_NAME + "." + ID_INIZIO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_INIZIO_field;

	public final static String DATA_INIZIO_INIZIO = "DATA_INIZIO_INIZIO";
	public final static String T_DATA_INIZIO_INIZIO = TABLE_NAME + "." + DATA_INIZIO_INIZIO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_INIZIO_field;

	public final static String ID_SOGGETTO_PARTECIPANTE = "ID_SOGGETTO_PARTECIPANTE";
	public final static String T_ID_SOGGETTO_PARTECIPANTE = TABLE_NAME + "." + ID_SOGGETTO_PARTECIPANTE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_SOGGETTO_PARTECIPANTE_field;

	public final static String DATA_INIZIO_SOGG = "DATA_INIZIO_SOGG";
	public final static String T_DATA_INIZIO_SOGG = TABLE_NAME + "." + DATA_INIZIO_SOGG;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_SOGG_field;

	public final static String ID_STATO = "ID_STATO";
	public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STATO_field;

	public final static String CODICE_INPS = "CODICE_INPS";
	public final static String T_CODICE_INPS = TABLE_NAME + "." + CODICE_INPS;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CODICE_INPS_field;

	public final static String CODICE_INAIL = "CODICE_INAIL";
	public final static String T_CODICE_INAIL = TABLE_NAME + "." + CODICE_INAIL;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CODICE_INAIL_field;

	public final static String CODICE_CASSA = "CODICE_CASSA";
	public final static String T_CODICE_CASSA = TABLE_NAME + "." + CODICE_CASSA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CODICE_CASSA_field;

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
}
