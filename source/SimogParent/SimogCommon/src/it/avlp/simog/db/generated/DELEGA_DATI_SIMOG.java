package it.avlp.simog.db.generated; 
	/*
	*	FILE DELEGA_DATI_SIMOG created mar 28/09/2010 15:28:37:734
	*/

public class DELEGA_DATI_SIMOG {

	public final static String TABLE_NAME = "DELEGA_DATI_SIMOG";


	public final static String ID_OSSERVATORIO = "ID_OSSERVATORIO";
	public final static String T_ID_OSSERVATORIO = TABLE_NAME + "." + ID_OSSERVATORIO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [3]
		// NULLABLE [NO]

	public String ID_OSSERVATORIO_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [128]
		// NULLABLE [NO]

	public String DESCRIZIONE_field;

	public final static String DELEGA_CIG = "DELEGA_CIG";
	public final static String T_DELEGA_CIG = TABLE_NAME + "." + DELEGA_CIG;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [NO]

	public char DELEGA_CIG_field;

	public final static String DELEGA_CIG_DAL = "DELEGA_CIG_DAL";
	public final static String T_DELEGA_CIG_DAL = TABLE_NAME + "." + DELEGA_CIG_DAL;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DELEGA_CIG_DAL_field;

	public final static String DELEGA_SCHEDE = "DELEGA_SCHEDE";
	public final static String T_DELEGA_SCHEDE = TABLE_NAME + "." + DELEGA_SCHEDE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [NO]

	public char DELEGA_SCHEDE_field;

	public final static String DELEGA_SCHEDE_DAL = "DELEGA_SCHEDE_DAL";
	public final static String T_DELEGA_SCHEDE_DAL = TABLE_NAME + "." + DELEGA_SCHEDE_DAL;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DELEGA_SCHEDE_DAL_field;

	public final static String URL_SISTEMA = "URL_SISTEMA";
	public final static String T_URL_SISTEMA = TABLE_NAME + "." + URL_SISTEMA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [128]
		// NULLABLE [YES]

	public String URL_SISTEMA_field;

	public final static String DATA_FINE_VALIDITA = "DATA_FINE_VALIDITA";
	public final static String T_DATA_FINE_VALIDITA = TABLE_NAME + "." + DATA_FINE_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_FINE_VALIDITA_field;

	public final static String DATA_ULTIMA_MODIFICA = "DATA_ULTIMA_MODIFICA";
	public final static String T_DATA_ULTIMA_MODIFICA = TABLE_NAME + "." + DATA_ULTIMA_MODIFICA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_ULTIMA_MODIFICA_field;
}
