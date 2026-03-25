package it.avlp.simog.db.generated; 
	/*
	*	FILE SOSPENSIONI created lun 17/08/2009 13:45:36:687
	*/

public class SOSPENSIONI {

	public final static String TABLE_NAME = "SOSPENSIONI";


	public final static String ID_SOSPENSIONE = "ID_SOSPENSIONE";
	public final static String T_ID_SOSPENSIONE = TABLE_NAME + "." + ID_SOSPENSIONE;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_SOSPENSIONE_field;

	public final static String DATA_INIZIO_SOSP = "DATA_INIZIO_SOSP";
	public final static String T_DATA_INIZIO_SOSP = TABLE_NAME + "." + DATA_INIZIO_SOSP;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_SOSP_field;

	public final static String DATA_FINE_SOSP = "DATA_FINE_SOSP";
	public final static String T_DATA_FINE_SOSP = TABLE_NAME + "." + DATA_FINE_SOSP;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_SOSP_field;

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

	public final static String DATA_VERB_SOSP = "DATA_VERB_SOSP";
	public final static String T_DATA_VERB_SOSP = TABLE_NAME + "." + DATA_VERB_SOSP;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_VERB_SOSP_field;

	public final static String DATA_VERB_RIPR = "DATA_VERB_RIPR";
	public final static String T_DATA_VERB_RIPR = TABLE_NAME + "." + DATA_VERB_RIPR;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_VERB_RIPR_field;

	public final static String ID_MOTIVO_SOSP = "ID_MOTIVO_SOSP";
	public final static String T_ID_MOTIVO_SOSP = TABLE_NAME + "." + ID_MOTIVO_SOSP;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_MOTIVO_SOSP_field;

	public final static String FLAG_SUPERO_TEMP = "FLAG_SUPERO_TEMP";
	public final static String T_FLAG_SUPERO_TEMP = TABLE_NAME + "." + FLAG_SUPERO_TEMP;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_SUPERO_TEMP_field;

	public final static String FLAG_RISERVE = "FLAG_RISERVE";
	public final static String T_FLAG_RISERVE = TABLE_NAME + "." + FLAG_RISERVE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_RISERVE_field;

	public final static String FLAG_VERBALE = "FLAG_VERBALE";
	public final static String T_FLAG_VERBALE = TABLE_NAME + "." + FLAG_VERBALE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_VERBALE_field;

	public final static String ID_SCHEDA_LOCALE = "ID_SCHEDA_LOCALE";
	public final static String T_ID_SCHEDA_LOCALE = TABLE_NAME + "." + ID_SCHEDA_LOCALE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String ID_SCHEDA_LOCALE_field;
}
