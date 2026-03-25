package it.avlp.simog.db.generated; 
	/*
	*	FILE MOTIVI_SOSPENSIONE created lun 17/08/2009 13:45:36:687
	*/

public class MOTIVI_SOSPENSIONE {

	public final static String TABLE_NAME = "MOTIVI_SOSPENSIONE";


	public final static String ID_MOTIVO_SOSP = "ID_MOTIVO_SOSP";
	public final static String T_ID_MOTIVO_SOSP = TABLE_NAME + "." + ID_MOTIVO_SOSP;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_MOTIVO_SOSP_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public String DESCRIZIONE_field;

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
