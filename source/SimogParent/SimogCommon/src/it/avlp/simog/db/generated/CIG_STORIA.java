package it.avlp.simog.db.generated; 
	/*
	*	FILE CIG_STORIA created lun 17/08/2009 13:45:36:687
	*/

public class CIG_STORIA {

	public final static String TABLE_NAME = "CIG_STORIA";


	public final static String ID_RECORD = "ID_RECORD";
	public final static String T_ID_RECORD = TABLE_NAME + "." + ID_RECORD;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_RECORD_field;

	public final static String DATA_ATTRIBUZIONE = "DATA_ATTRIBUZIONE";
	public final static String T_DATA_ATTRIBUZIONE = TABLE_NAME + "." + DATA_ATTRIBUZIONE;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_ATTRIBUZIONE_field;

	public final static String APPLICAZIONE = "APPLICAZIONE";
	public final static String T_APPLICAZIONE = TABLE_NAME + "." + APPLICAZIONE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [20]
		// NULLABLE [NO]

	public char APPLICAZIONE_field;

	public final static String CF_UTENTE = "CF_UTENTE";
	public final static String T_CF_UTENTE = TABLE_NAME + "." + CF_UTENTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [NO]

	public String CF_UTENTE_field;

	public final static String CF_AMMINISTRAZIONE = "CF_AMMINISTRAZIONE";
	public final static String T_CF_AMMINISTRAZIONE = TABLE_NAME + "." + CF_AMMINISTRAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CF_AMMINISTRAZIONE_field;

	public final static String ID_STAZIONE_APPALTANTE = "ID_STAZIONE_APPALTANTE";
	public final static String T_ID_STAZIONE_APPALTANTE = TABLE_NAME + "." + ID_STAZIONE_APPALTANTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String ID_STAZIONE_APPALTANTE_field;

	public final static String CIG_CICLE = "CIG_CICLE";
	public final static String T_CIG_CICLE = TABLE_NAME + "." + CIG_CICLE;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [NO]

	public int CIG_CICLE_field;

	public final static String CIG = "CIG";
	public final static String T_CIG = TABLE_NAME + "." + CIG;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [7]
		// NULLABLE [NO]

	public String CIG_field;

	public final static String CIG_KKK = "CIG_KKK";
	public final static String T_CIG_KKK = TABLE_NAME + "." + CIG_KKK;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [3]
		// NULLABLE [NO]

	public String CIG_KKK_field;

	public final static String ADMIN_OR = "ADMIN_OR";
	public final static String T_ADMIN_OR = TABLE_NAME + "." + ADMIN_OR;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [3]
		// NULLABLE [YES]

	public String ADMIN_OR_field;
}
