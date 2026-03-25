package it.avlp.simog.db.generated; 
	/*
	*	FILE SCELTA_CONTRAENTE created lun 17/08/2009 13:45:36:687
	*/

public class SCELTA_CONTRAENTE {

	public final static String TABLE_NAME = "SCELTA_CONTRAENTE";


	public final static String ID_SCELTA_CONTRAENTE = "ID_SCELTA_CONTRAENTE";
	public final static String T_ID_SCELTA_CONTRAENTE = TABLE_NAME + "." + ID_SCELTA_CONTRAENTE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long Id_Scelta_Contraente_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [256]
		// NULLABLE [NO]

	public String Descrizione_field;

	//TICKET ALM #2847
	public final static String DATA_INIZIO_VALIDITA = "DATA_INIZIO_VALIDITA";
	public final static String T_DATA_INIZIO_VALIDITA = TABLE_NAME + "." + DATA_INIZIO_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String Data_inizio_validita_field;
	//FINE TICKET ALM #2847
	
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
	
	public final static String BDNCP_COD = "BDNCP_COD";
}
