package it.avlp.simog.db.generated; 
	/*
	*	FILE CODICI_NUTS created lun 17/08/2009 13:45:36:687
	*/

public class CODICI_NUTS {

	public final static String TABLE_NAME = "CODICI_NUTS";


	public final static String ID_NUTS = "ID_NUTS";
	public final static String T_ID_NUTS = TABLE_NAME + "." + ID_NUTS;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [NO]

	public String Id_nuts_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public String Descrizione_field;

	public final static String LIVELLO = "LIVELLO";
	public final static String T_LIVELLO = TABLE_NAME + "." + LIVELLO;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [NO]

	public int Livello_field;

	public final static String ID_REGIONE = "ID_REGIONE";
	public final static String T_ID_REGIONE = TABLE_NAME + "." + ID_REGIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [2]
		// NULLABLE [YES]

	public String Id_regione_field;

	public final static String DATA_FINE_VALIDITA = "DATA_FINE_VALIDITA";
	public final static String T_DATA_FINE_VALIDITA = TABLE_NAME + "." + DATA_FINE_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String Data_fine_validita_field;

	public final static String DATA_ULTIMA_MODIFICA = "DATA_ULTIMA_MODIFICA";
	public final static String T_DATA_ULTIMA_MODIFICA = TABLE_NAME + "." + DATA_ULTIMA_MODIFICA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String Data_ultima_modifica_field;
}
