package it.avlp.simog.db.generated; 
	/*
	*	FILE AGGIUDICATARIO created lun 17/08/2009 13:45:36:687
	*/

public class DITTE_AUSILIARIE {

	public final static String TABLE_NAME = "DITTE_AUSILIARIE";

	public final static String ID_RECORD = "ID_RECORD";
	public final static String T_ID_RECORD = TABLE_NAME + "." + ID_RECORD;
		// COLUMN TYPE [bigint]
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
		// NULLABLE [NO]

	public java.sql.Date DATA_FINE_RECORD_field;
	
	public final static String ID_AGGIUDICAZIONE = "ID_AGGIUDICAZIONE";
	public final static String T_ID_AGGIUDICAZIONE = TABLE_NAME + "." + ID_AGGIUDICAZIONE;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_AGGIUDICAZIONE_field;
	
	public final static String DATA_INIZIO_AGGIUDICAZIONE = "DATA_INIZIO_AGGIUDICAZIONE";
	public final static String T_DATA_INIZIO_AGGIUDICAZIONE = TABLE_NAME + "." + DATA_INIZIO_AGGIUDICAZIONE;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_AGGIUDICAZIONE_field;
	
	public final static String ID_AGGIUDICATARIO = "ID_AGGIUDICATARIO";
	public final static String T_ID_AGGIUDICATARIO = TABLE_NAME + "." + ID_AGGIUDICATARIO;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_AGGIUDICATARIO_field;
	
	public final static String DATA_INIZIO_AGGIUDICATARIO = "DATA_INIZIO_AGGIUDICATARIO";
	public final static String T_DATA_INIZIO_AGGIUDICATARIO = TABLE_NAME + "." + DATA_INIZIO_AGGIUDICATARIO;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long DATA_INIZIO_AGGIUDICATARIO_field;
	
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


	public final static String ID_STATO_SCHEDA = "ID_STATO_SCHEDA";
	public final static String T_ID_STATO_SCHEDA = TABLE_NAME + "." + ID_STATO_SCHEDA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STATO_SCHEDA_field;

	public final static String FLAG_AVVALIMENTO = "FLAG_AVVALIMENTO";
	public final static String T_FLAG_AVVALIMENTO = TABLE_NAME + "." + FLAG_AVVALIMENTO;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_AVVALIMENTO_field;
}
