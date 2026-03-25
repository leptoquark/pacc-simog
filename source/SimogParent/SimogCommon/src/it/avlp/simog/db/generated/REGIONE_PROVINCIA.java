package it.avlp.simog.db.generated; 
	/*
	*	FILE REGIONE_PROVINCIA created lun 17/08/2009 13:45:36:687
	*/

public class REGIONE_PROVINCIA {

	public final static String TABLE_NAME = "REGIONE_PROVINCIA";


	public final static String ID_REGIONE = "ID_REGIONE";
	public final static String T_ID_REGIONE = TABLE_NAME + "." + ID_REGIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [2]
		// NULLABLE [NO]

	public String Id_regione_field;

	public final static String ID_PROVINCIA = "ID_PROVINCIA";
	public final static String T_ID_PROVINCIA = TABLE_NAME + "." + ID_PROVINCIA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [3]
		// NULLABLE [NO]

	public String Id_provincia_field;

	public final static String DENOM_REGIONE = "DENOM_REGIONE";
	public final static String T_DENOM_REGIONE = TABLE_NAME + "." + DENOM_REGIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public String Denom_regione_field;

	public final static String DENOM_PROVINCIA = "DENOM_PROVINCIA";
	public final static String T_DENOM_PROVINCIA = TABLE_NAME + "." + DENOM_PROVINCIA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public String Denom_provincia_field;

	public final static String SIGLA_PROVINCIA = "SIGLA_PROVINCIA";
	public final static String T_SIGLA_PROVINCIA = TABLE_NAME + "." + SIGLA_PROVINCIA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [2]
		// NULLABLE [NO]

	public char Sigla_provincia_field;

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
