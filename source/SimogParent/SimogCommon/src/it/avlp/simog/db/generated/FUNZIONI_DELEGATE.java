package it.avlp.simog.db.generated; 
	/*
	*	FILE FUNZIONI_DELEGATE created mer 27/02/2019 14:46:00:000
	*/
//TICKET ALM #659 - 3.04.4
public class FUNZIONI_DELEGATE {

	public final static String TABLE_NAME = "FUNZIONI_DELEGATE";


	public final static String ID_F_DELEGATE = "ID_F_DELEGATE";
	public final static String T_ID_F_DELEGATE = TABLE_NAME + "." + ID_F_DELEGATE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long Id_f_delegate_field;

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
}
