package it.avlp.simog.db.generated; 
	/*
	*	FILE INIZIO_LAVORI created lun 17/08/2009 13:45:36:687
	*/

public class INIZIO_LAVORI {

	public final static String TABLE_NAME = "INIZIO_LAVORI";


	public final static String ID_INIZIO = "ID_INIZIO";
	public final static String T_ID_INIZIO = TABLE_NAME + "." + ID_INIZIO;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_INIZIO_field;

	public final static String DATA_INIZIO_INIZIO = "DATA_INIZIO_INIZIO";
	public final static String T_DATA_INIZIO_INIZIO = TABLE_NAME + "." + DATA_INIZIO_INIZIO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_INIZIO_field;

	public final static String DATA_FINE_INIZIO = "DATA_FINE_INIZIO";
	public final static String T_DATA_FINE_INIZIO = TABLE_NAME + "." + DATA_FINE_INIZIO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_INIZIO_field;

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

	public final static String DATA_STIPULA = "DATA_STIPULA";
	public final static String T_DATA_STIPULA = TABLE_NAME + "." + DATA_STIPULA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_STIPULA_field;

	public final static String DATA_ESECUTIVITA = "DATA_ESECUTIVITA";
	public final static String T_DATA_ESECUTIVITA = TABLE_NAME + "." + DATA_ESECUTIVITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_ESECUTIVITA_field;

	public final static String IMPORTO_CAUZ = "IMPORTO_CAUZ";
	public final static String T_IMPORTO_CAUZ = TABLE_NAME + "." + IMPORTO_CAUZ;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_CAUZ_field;

	public final static String DATA_INI_PROG_ESEC = "DATA_INI_PROG_ESEC";
	public final static String T_DATA_INI_PROG_ESEC = TABLE_NAME + "." + DATA_INI_PROG_ESEC;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_INI_PROG_ESEC_field;

	public final static String DATA_APP_PROG_ESEC = "DATA_APP_PROG_ESEC";
	public final static String T_DATA_APP_PROG_ESEC = TABLE_NAME + "." + DATA_APP_PROG_ESEC;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_APP_PROG_ESEC_field;

	public final static String FLAG_FRAZIONATA = "FLAG_FRAZIONATA";
	public final static String T_FLAG_FRAZIONATA = TABLE_NAME + "." + FLAG_FRAZIONATA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_FRAZIONATA_field;

	public final static String DATA_VERBALE_CONS = "DATA_VERBALE_CONS";
	public final static String T_DATA_VERBALE_CONS = TABLE_NAME + "." + DATA_VERBALE_CONS;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_VERBALE_CONS_field;

	public final static String DATA_VERBALE_DEF = "DATA_VERBALE_DEF";
	public final static String T_DATA_VERBALE_DEF = TABLE_NAME + "." + DATA_VERBALE_DEF;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_VERBALE_DEF_field;

	public final static String FLAG_RISERVA = "FLAG_RISERVA";
	public final static String T_FLAG_RISERVA = TABLE_NAME + "." + FLAG_RISERVA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_RISERVA_field;

	public final static String DATA_VERB_INIZIO = "DATA_VERB_INIZIO";
	public final static String T_DATA_VERB_INIZIO = TABLE_NAME + "." + DATA_VERB_INIZIO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_VERB_INIZIO_field;

	public final static String DATA_TERMINE = "DATA_TERMINE";
	public final static String T_DATA_TERMINE = TABLE_NAME + "." + DATA_TERMINE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_TERMINE_field;

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
		// NULLABLE [YES]

	public java.sql.Date DATA_INIZIO_PUBB_field;

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
