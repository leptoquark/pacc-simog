package it.avlp.simog.db.generated; 
	/*
	*	FILE MODI_RIAGGIUD created lun 17/08/2009 13:45:36:687
	*/

public class MODI_RIAGGIUD {

	public final static String TABLE_NAME = "MODI_RIAGGIUD";


	public final static String ID_MODO_RIAGGIUD = "ID_MODO_RIAGGIUD";
	public final static String T_ID_MODO_RIAGGIUD = TABLE_NAME + "." + ID_MODO_RIAGGIUD;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [NO]

	public String Id_modo_riaggiud_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [256]
		// NULLABLE [NO]

	public String Descrizione_field;

	//TICKET ALM #2847 - Modalita Riaggiudicazione
	public final static String DATA_INIZIO_VALIDITA = "DATA_INIZIO_VALIDITA";
	public final static String T_DATA_INIZIO_VALIDITA = TABLE_NAME + "." + DATA_INIZIO_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String Data_inizio_validita_field;	
	
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
