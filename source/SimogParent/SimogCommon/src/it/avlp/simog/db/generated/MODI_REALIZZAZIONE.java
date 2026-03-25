package it.avlp.simog.db.generated; 
	/*
	*	FILE MODI_REALIZZAZIONE created gio 27/08/2009 09:22:55:406
	*/

public class MODI_REALIZZAZIONE {

	public final static String TABLE_NAME = "MODI_REALIZZAZIONE";


	public final static String ID_MODO_REAL = "ID_MODO_REAL";
	public final static String T_ID_MODO_REAL = TABLE_NAME + "." + ID_MODO_REAL;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_MODO_REAL_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [100]
		// NULLABLE [NO]

	public String DESCRIZIONE_field;

	//TICKET ALM #2847
	public final static String DATA_INIZIO_VALIDITA = "DATA_INIZIO_VALIDITA";
	public final static String T_DATA_INIZIO_VALIDITA = TABLE_NAME + "." + DATA_INIZIO_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_INIZIO_VALIDITA_field;
	//FINE TICKET ALM #2847
	
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
