package it.avlp.simog.db.generated; 
	/*
	*	FILE ESATTORETIPOUTENZA created lun 17/08/2009 13:45:36:687
	*/

public class ESATTORETIPOUTENZA {

	public final static String TABLE_NAME = "ESATTORETIPOUTENZA";


	public final static String ID_TIPO_UTENZA = "ID_TIPO_UTENZA";
	public final static String T_ID_TIPO_UTENZA = TABLE_NAME + "." + ID_TIPO_UTENZA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [2]
		// NULLABLE [NO]

	public String Id_tipo_utenza_field;

	public final static String DESCRIZIONE_TIPO_UTENZA = "DESCRIZIONE_TIPO_UTENZA";
	public final static String T_DESCRIZIONE_TIPO_UTENZA = TABLE_NAME + "." + DESCRIZIONE_TIPO_UTENZA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public String Descrizione_tipo_utenza_field;

	public final static String DATA_ULTIMA_MODIFICA = "DATA_ULTIMA_MODIFICA";
	public final static String T_DATA_ULTIMA_MODIFICA = TABLE_NAME + "." + DATA_ULTIMA_MODIFICA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [NO]

	public String Data_ultima_modifica_field;

	public final static String DATA_FINE_VALIDITA = "DATA_FINE_VALIDITA";
	public final static String T_DATA_FINE_VALIDITA = TABLE_NAME + "." + DATA_FINE_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public String Data_fine_validita_field;
}
