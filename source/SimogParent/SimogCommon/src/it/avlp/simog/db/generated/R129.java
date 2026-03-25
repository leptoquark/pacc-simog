package it.avlp.simog.db.generated; 
	/*
	*	FILE R129 created lun 17/08/2009 13:45:36:687
	*/

public class R129 {

	public final static String TABLE_NAME = "R129";


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

	public final static String ID_RECORD = "ID_RECORD";
	public final static String T_ID_RECORD = TABLE_NAME + "." + ID_RECORD;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_RECORD_field;

	public final static String DATA_INIZIO = "DATA_INIZIO";
	public final static String T_DATA_INIZIO = TABLE_NAME + "." + DATA_INIZIO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_field;

	public final static String DATA_FINE = "DATA_FINE";
	public final static String T_DATA_FINE = TABLE_NAME + "." + DATA_FINE;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_field;

	public final static String DATA_COMUNIC = "DATA_COMUNIC";
	public final static String T_DATA_COMUNIC = TABLE_NAME + "." + DATA_COMUNIC;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [NO]

	public String DATA_COMUNIC_field;

	public final static String DATA_TERMINE = "DATA_TERMINE";
	public final static String T_DATA_TERMINE = TABLE_NAME + "." + DATA_TERMINE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_TERMINE_field;

	public final static String ID_STATO = "ID_STATO";
	public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STATO_field;

	public final static String TIPO_COMUN = "TIPO_COMUN";
	public final static String T_TIPO_COMUN = TABLE_NAME + "." + TIPO_COMUN;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [NO]

	public char TIPO_COMUN_field;

	public final static String DURATA_SOSP = "DURATA_SOSP";
	public final static String T_DURATA_SOSP = TABLE_NAME + "." + DURATA_SOSP;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long DURATA_SOSP_field;

	public final static String MOTIVO_SOSP = "MOTIVO_SOSP";
	public final static String T_MOTIVO_SOSP = TABLE_NAME + "." + MOTIVO_SOSP;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1000]
		// NULLABLE [YES]

	public String MOTIVO_SOSP_field;

	public final static String DATA_IST_RECESSO = "DATA_IST_RECESSO";
	public final static String T_DATA_IST_RECESSO = TABLE_NAME + "." + DATA_IST_RECESSO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_IST_RECESSO_field;

	public final static String FLAG_ACCOLTA = "FLAG_ACCOLTA";
	public final static String T_FLAG_ACCOLTA = TABLE_NAME + "." + FLAG_ACCOLTA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_ACCOLTA_field;

	public final static String FLAG_TARDIVA = "FLAG_TARDIVA";
	public final static String T_FLAG_TARDIVA = TABLE_NAME + "." + FLAG_TARDIVA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_TARDIVA_field;

	public final static String FLAG_RIPRESA = "FLAG_RIPRESA";
	public final static String T_FLAG_RIPRESA = TABLE_NAME + "." + FLAG_RIPRESA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_RIPRESA_field;

	public final static String FLAG_RISERVA = "FLAG_RISERVA";
	public final static String T_FLAG_RISERVA = TABLE_NAME + "." + FLAG_RISERVA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_RISERVA_field;

	public final static String IMPORTO_SPESE = "IMPORTO_SPESE";
	public final static String T_IMPORTO_SPESE = TABLE_NAME + "." + IMPORTO_SPESE;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_SPESE_field;

	public final static String IMPORTO_ONERI = "IMPORTO_ONERI";
	public final static String T_IMPORTO_ONERI = TABLE_NAME + "." + IMPORTO_ONERI;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_ONERI_field;

	public final static String DATA_CONSEGNA_LAVORI = "DATA_CONSEGNA_LAVORI";
	public final static String T_DATA_CONSEGNA_LAVORI = TABLE_NAME + "." + DATA_CONSEGNA_LAVORI;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_CONSEGNA_LAVORI_field;

	public final static String ID_SCHEDA_LOCALE = "ID_SCHEDA_LOCALE";
	public final static String T_ID_SCHEDA_LOCALE = TABLE_NAME + "." + ID_SCHEDA_LOCALE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String ID_SCHEDA_LOCALE_field;
}
