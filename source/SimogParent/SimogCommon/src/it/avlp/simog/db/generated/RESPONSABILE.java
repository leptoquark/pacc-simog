package it.avlp.simog.db.generated; 
	/*
	*	FILE RESPONSABILE created lun 17/08/2009 13:45:36:687
	*/

public class RESPONSABILE {

	public final static String TABLE_NAME = "RESPONSABILE";


	public final static String ID_INCARICATO = "ID_INCARICATO";
	public final static String T_ID_INCARICATO = TABLE_NAME + "." + ID_INCARICATO;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_INCARICATO_field;

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

	public final static String ID_STATO = "ID_STATO";
	public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

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

	public final static String ID_RESPONSABILE = "ID_RESPONSABILE";
	public final static String T_ID_RESPONSABILE = TABLE_NAME + "." + ID_RESPONSABILE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_RESPONSABILE_field;

	public final static String DATA_INIZIO_RES = "DATA_INIZIO_RES";
	public final static String T_DATA_INIZIO_RES = TABLE_NAME + "." + DATA_INIZIO_RES;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_RES_field;

	public final static String ID_SEZIONE = "ID_SEZIONE";
	public final static String T_ID_SEZIONE = TABLE_NAME + "." + ID_SEZIONE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [2]
		// NULLABLE [YES]

	public char ID_SEZIONE_field;

	public final static String ID_RUOLO = "ID_RUOLO";
	public final static String T_ID_RUOLO = TABLE_NAME + "." + ID_RUOLO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [YES]

	public String ID_RUOLO_field;

	public final static String FLAG_PROG_ESTERNA = "FLAG_PROG_ESTERNA";
	public final static String T_FLAG_PROG_ESTERNA = TABLE_NAME + "." + FLAG_PROG_ESTERNA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_PROG_ESTERNA_field;

	public final static String CIG_PROG_ESTERNA = "CIG_PROG_ESTERNA";
	public final static String T_CIG_PROG_ESTERNA = TABLE_NAME + "." + CIG_PROG_ESTERNA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public String CIG_PROG_ESTERNA_field;

	public final static String DATA_AFF_PROG_ESTERNA = "DATA_AFF_PROG_ESTERNA";
	public final static String T_DATA_AFF_PROG_ESTERNA = TABLE_NAME + "." + DATA_AFF_PROG_ESTERNA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_AFF_PROG_ESTERNA_field;

	public final static String DATA_CONS_PROG_ESTERNA = "DATA_CONS_PROG_ESTERNA";
	public final static String T_DATA_CONS_PROG_ESTERNA = TABLE_NAME + "." + DATA_CONS_PROG_ESTERNA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_CONS_PROG_ESTERNA_field;
	
	public final static String  ID_PARTECIPANTE= "ID_PARTECIPANTE";
	public final static String T_ID_PARTECIPANTE = TABLE_NAME + "." + ID_PARTECIPANTE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_PARTECIPANTE_field;

	public final static String DATA_INIZIO_PART = "DATA_INIZIO_PART";
	public final static String T_DATA_INIZIO_PART = TABLE_NAME + "." + DATA_INIZIO_PART;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_PART_field;
	
	
	public final static String ID_GRUPPO = "ID_GRUPPO";
	public final static String T_ID_GRUPPO = TABLE_NAME +"."+ID_GRUPPO;
	
	public final static String MANDANTE = "MANDANTE";
	public final static String T_MANDANTE = TABLE_NAME +"."+MANDANTE;
}
