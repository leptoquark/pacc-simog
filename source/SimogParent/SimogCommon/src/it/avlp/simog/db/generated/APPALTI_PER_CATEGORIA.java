package it.avlp.simog.db.generated; 
	/*
	*	FILE APPALTI_PER_CATEGORIA created lun 17/08/2009 13:45:36:687
	*/

public class APPALTI_PER_CATEGORIA {

	public final static String TABLE_NAME = "APPALTI_PER_CATEGORIA";


	public final static String ID_CATEGORIA = "ID_CATEGORIA";
	public final static String T_ID_CATEGORIA = TABLE_NAME + "." + ID_CATEGORIA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [NO]

	public String Id_categoria_field;

	public final static String ID_APPALTO = "ID_APPALTO";
	public final static String T_ID_APPALTO = TABLE_NAME + "." + ID_APPALTO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long Id_appalto_field;

	public final static String ID_TIPO_CATEGORIA = "ID_TIPO_CATEGORIA";
	public final static String T_ID_TIPO_CATEGORIA = TABLE_NAME + "." + ID_TIPO_CATEGORIA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [NO]

	public String Id_tipo_categoria_field;

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
