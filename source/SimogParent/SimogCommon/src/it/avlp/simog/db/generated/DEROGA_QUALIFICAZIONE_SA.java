package it.avlp.simog.db.generated; 
	/*
	*	3.04.9 MEV 40610
	*/

public class DEROGA_QUALIFICAZIONE_SA {

	public final static String TABLE_NAME = "DEROGA_QUALIFICAZIONE_SA";


	public final static String ID_DEROGA_QUALIFICAZIONE = "Id_Deroga_Qualificazione";
	public final static String T_ID_DEROGA_QUALIFICAZIONE = TABLE_NAME + "." + ID_DEROGA_QUALIFICAZIONE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_DEROGA_QUALIFICAZIONE_field;

	public final static String DESCRIZIONE = "Descrizione";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [100]
		// NULLABLE [NO]

	public String DESCRIZIONE_field;

	//TICKET ALM #2847 - Variante
	public final static String DATA_INIZIO_VALIDITA = "Data_Inizio_Validita";
	public final static String T_DATA_INIZIO_VALIDITA = TABLE_NAME + "." + DATA_INIZIO_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_INIZIO_VALIDITA_field;
	
	public final static String DATA_FINE_VALIDITA = "Data_Fine_Validita";
	public final static String T_DATA_FINE_VALIDITA = TABLE_NAME + "." + DATA_FINE_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_FINE_VALIDITA_field;
	
	public final static String DATA_ULTIMA_MODIFICA = "Data_Ultima_Modifica";
	public final static String T_DATA_ULTIMA_MODIFICA = TABLE_NAME + "." + DATA_ULTIMA_MODIFICA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_ULTIMA_MODIFICA_field;
}
