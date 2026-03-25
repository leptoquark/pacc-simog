package it.avlp.simog.db.generated;

public class MISURA_PREMIALE {
	public final static String TABLE_NAME = "MISURA_PREMIALE";
	
	public final static String ID_MISURA = "Id_Misura_Premiale";
	public final static String T_ID_MISURA= TABLE_NAME + "." + ID_MISURA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long Id_misura_field;

	public final static String DESCRIZIONE = "Descrizione";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1024]
		// NULLABLE [NO]

	public String Descrizione_field;

	//TICKET ALM #2847
	public final static String DATA_INIZIO_VALIDITA = "Data_Inizio_Validita";
	public final static String T_DATA_INIZIO_VALIDITA = TABLE_NAME + "." + DATA_INIZIO_VALIDITA;
		// COLUMN TYPE [varchar] (8)
		// NULLABLE [YES]

	public String Data_inizio_validita_field;
	//FINE TICKET ALM #2847
	
	public final static String DATA_FINE_VALIDITA = "Data_Fine_Validita";
	public final static String T_DATA_FINE_VALIDITA = TABLE_NAME + "." + DATA_FINE_VALIDITA;
		// COLUMN TYPE [varchar] (8)
		// NULLABLE [YES]

	public String Data_fine_validita_field;

	public final static String DATA_ULTIMA_MODIFICA = "Data_Ultima_Modifica";
	public final static String T_DATA_ULTIMA_MODIFICA = TABLE_NAME + "." + DATA_ULTIMA_MODIFICA;
		// COLUMN TYPE [varchar] (8)
		// NULLABLE [YES]

	public String Data_ultima_modifica_field;
}
