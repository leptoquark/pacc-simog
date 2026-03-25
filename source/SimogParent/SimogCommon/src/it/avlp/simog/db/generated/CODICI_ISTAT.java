package it.avlp.simog.db.generated; 
	/*
	*	FILE CODICI_ISTAT created lun 17/08/2009 13:45:36:687
	*/

public class CODICI_ISTAT {

	public final static String TABLE_NAME = "CODICI_ISTAT";


	public final static String ID_COMUNE = "ID_COMUNE";
	public final static String T_ID_COMUNE = TABLE_NAME + "." + ID_COMUNE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [6]
		// NULLABLE [NO]

	public String Id_comune_field;

	public final static String ID_PROVINCIA = "ID_PROVINCIA";
	public final static String T_ID_PROVINCIA = TABLE_NAME + "." + ID_PROVINCIA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [3]
		// NULLABLE [NO]

	public String Id_provincia_field;

	public final static String DENOMINAZIONE = "DENOMINAZIONE";
	public final static String T_DENOMINAZIONE = TABLE_NAME + "." + DENOMINAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [100]
		// NULLABLE [NO]

	public String Denominazione_field;

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
