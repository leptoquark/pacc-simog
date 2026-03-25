package it.avlp.simog.db.generated; 
	/*
	*	FILE FINE_LAVORI created lun 17/08/2009 13:45:36:687
	*/

public class FINE_LAVORI {

	public final static String TABLE_NAME = "FINE_LAVORI";


	public final static String ID_ULTIM = "ID_ULTIM";
	public final static String T_ID_ULTIM = TABLE_NAME + "." + ID_ULTIM;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_ULTIM_field;

	public final static String DATA_INIZIO_ULTIM = "DATA_INIZIO_ULTIM";
	public final static String T_DATA_INIZIO_ULTIM = TABLE_NAME + "." + DATA_INIZIO_ULTIM;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_ULTIM_field;

	public final static String DATA_FINE_ULTIM = "DATA_FINE_ULTIM";
	public final static String T_DATA_FINE_ULTIM = TABLE_NAME + "." + DATA_FINE_ULTIM;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_ULTIM_field;

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

	public final static String ID_MOTIVO_INTERR = "ID_MOTIVO_INTERR";
	public final static String T_ID_MOTIVO_INTERR = TABLE_NAME + "." + ID_MOTIVO_INTERR;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_MOTIVO_INTERR_field;

	public final static String ID_MOTIVO_RISOL = "ID_MOTIVO_RISOL";
	public final static String T_ID_MOTIVO_RISOL = TABLE_NAME + "." + ID_MOTIVO_RISOL;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_MOTIVO_RISOL_field;

	public final static String DATA_RISOLUZIONE = "DATA_RISOLUZIONE";
	public final static String T_DATA_RISOLUZIONE = TABLE_NAME + "." + DATA_RISOLUZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_RISOLUZIONE_field;

	public final static String FLAG_ONERI = "FLAG_ONERI";
	public final static String T_FLAG_ONERI = TABLE_NAME + "." + FLAG_ONERI;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_ONERI_field;

	public final static String ONERI_RISOLUZIONE = "ONERI_RISOLUZIONE";
	public final static String T_ONERI_RISOLUZIONE = TABLE_NAME + "." + ONERI_RISOLUZIONE;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public java.math.BigDecimal ONERI_RISOLUZIONE_field;

	public final static String FLAG_POLIZZA = "FLAG_POLIZZA";
	public final static String T_FLAG_POLIZZA = TABLE_NAME + "." + FLAG_POLIZZA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_POLIZZA_field;

	public final static String DATA_ULTIMAZIONE = "DATA_ULTIMAZIONE";
	public final static String T_DATA_ULTIMAZIONE = TABLE_NAME + "." + DATA_ULTIMAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_ULTIMAZIONE_field;

	public final static String NUM_INFORTUNI = "NUM_INFORTUNI";
	public final static String T_NUM_INFORTUNI = TABLE_NAME + "." + NUM_INFORTUNI;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long NUM_INFORTUNI_field;

	public final static String NUM_INF_PERM = "NUM_INF_PERM";
	public final static String T_NUM_INF_PERM = TABLE_NAME + "." + NUM_INF_PERM;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long NUM_INF_PERM_field;

	public final static String NUM_INF_MORT = "NUM_INF_MORT";
	public final static String T_NUM_INF_MORT = TABLE_NAME + "." + NUM_INF_MORT;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long NUM_INF_MORT_field;

	public final static String ID_SCHEDA_LOCALE = "ID_SCHEDA_LOCALE";
	public final static String T_ID_SCHEDA_LOCALE = TABLE_NAME + "." + ID_SCHEDA_LOCALE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String ID_SCHEDA_LOCALE_field;

	public final static String DATA_CONSEGNA = "DATA_CONSEGNA";
	public final static String T_DATA_CONSEGNA = TABLE_NAME + "." + DATA_CONSEGNA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]
	
	public String DATA_CONSEGNA_field;

	//gm nuovo codice 3.0
	public final static String GIORNI_PROROGA = "GIORNI_PROROGA";
	public final static String T_GIORNI_PROROGA = TABLE_NAME + "." + GIORNI_PROROGA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [8]
		// NULLABLE [YES]
	
	public long GIORNI_PROROGA_field;

	public final static String TERMINE_ULTIMAZIONE = "TERMINE_ULTIMAZIONE";
	public final static String T_TERMINE_ULTIMAZIONE = TABLE_NAME + "." + TERMINE_ULTIMAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]
	
	public String TERMINE_ULTIMAZIONE_field;
//	gm fine nuovo codice 3.0
	
	
}
