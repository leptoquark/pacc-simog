package it.avlp.simog.db.generated; 
	/*
	*	FILE LOG_OPERAZIONI created lun 17/08/2009 13:45:36:687
	*/

public class LOG_OPERAZIONI {

	public final static String TABLE_NAME = "LOG_OPERAZIONI";


	public final static String ID_LOG = "ID_LOG";
	public final static String T_ID_LOG = TABLE_NAME + "." + ID_LOG;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_LOG_field;

	public final static String CF_UTENTE = "CF_UTENTE";
	public final static String T_CF_UTENTE = TABLE_NAME + "." + CF_UTENTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [NO]

	public String CF_UTENTE_field;

	public final static String OPERAZIONE = "OPERAZIONE";
	public final static String T_OPERAZIONE = TABLE_NAME + "." + OPERAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public String OPERAZIONE_field;

	public final static String DATA_OPERAZIONE = "DATA_OPERAZIONE";
	public final static String T_DATA_OPERAZIONE = TABLE_NAME + "." + DATA_OPERAZIONE;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_OPERAZIONE_field;

	public final static String BLOCCO_DATI = "BLOCCO_DATI";
	public final static String T_BLOCCO_DATI = TABLE_NAME + "." + BLOCCO_DATI;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public String BLOCCO_DATI_field;

	public final static String ID_RECORD = "ID_RECORD";
	public final static String T_ID_RECORD = TABLE_NAME + "." + ID_RECORD;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [NO]

	public String ID_RECORD_field;
}
