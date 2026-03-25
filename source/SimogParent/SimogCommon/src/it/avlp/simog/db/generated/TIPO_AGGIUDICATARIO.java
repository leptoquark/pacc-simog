package it.avlp.simog.db.generated; 
	/*
	*	FILE TIPO_AGGIUDICATARIO created lun 17/08/2009 13:45:36:687
	*/

public class TIPO_AGGIUDICATARIO {

	public final static String TABLE_NAME = "TIPO_AGGIUDICATARIO";


	public final static String ID_TIPOAGG = "ID_TIPOAGG";
	public final static String T_ID_TIPOAGG = TABLE_NAME + "." + ID_TIPOAGG;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long Id_tipoagg_field;

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
