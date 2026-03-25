package it.avlp.simog.db.generated; 
	/*
	*	FILE TIPO_STRUMENTO created lun 17/08/2009 13:45:36:687
	*/

public class TIPO_STRUMENTO {

	public final static String TABLE_NAME = "TIPO_STRUMENTO";


	public final static String ID_STRUMENTO = "ID_STRUMENTO";
	public final static String T_ID_STRUMENTO = TABLE_NAME + "." + ID_STRUMENTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [NO]

	public String Id_strumento_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [256]
		// NULLABLE [NO]

	public String Descrizione_field;

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
