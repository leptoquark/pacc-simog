package it.avlp.simog.db.generated; 
	/*
	*	FILE ART_ESCLUSIONE created gio 17/09/2009 13:17:54:562
	*/

public class ART_ESCLUSIONE {

	public final static String TABLE_NAME = "ART_ESCLUSIONE";


	public final static String ID_ESCLUSIONE = "ID_ESCLUSIONE";
	public final static String T_ID_ESCLUSIONE = TABLE_NAME + "." + ID_ESCLUSIONE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_ESCLUSIONE_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public String DESCRIZIONE_field;

	//TICKET ALM #2847 - tipologica ART_ESCLUSIONE
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
	
	//TICKET ALM - 3.04.2 2005
	public final static String REGIME_ESCLUSIONE = "REGIME_ESCLUSIONE";
	public final static String T_REGIME_ESCLUSIONE = TABLE_NAME + "." + REGIME_ESCLUSIONE;
	
	public String REGIME_ESCLUSIONE_field;
}
