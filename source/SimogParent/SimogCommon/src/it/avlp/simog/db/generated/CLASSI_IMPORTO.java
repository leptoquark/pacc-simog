package it.avlp.simog.db.generated; 
	/*
	*	FILE CLASSI_IMPORTO created lun 17/08/2009 13:45:36:687
	*/

public class CLASSI_IMPORTO {

	public final static String TABLE_NAME = "CLASSI_IMPORTO";


	public final static String ID_CODICE = "ID_CODICE";
	public final static String T_ID_CODICE = TABLE_NAME + "." + ID_CODICE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [5]
		// NULLABLE [NO]

	public String ID_CODICE_field;

	public final static String IMPORTO_DA = "IMPORTO_DA";
	public final static String T_IMPORTO_DA = TABLE_NAME + "." + IMPORTO_DA;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [NO]

	public java.math.BigDecimal IMPORTO_DA_field;

	public final static String IMPORTO_A = "IMPORTO_A";
	public final static String T_IMPORTO_A = TABLE_NAME + "." + IMPORTO_A;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [NO]

	public java.math.BigDecimal IMPORTO_A_field;

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
	
	//is3028_NRFDBDT04Active
	public final static String TITOLO = "TITOLO";
	public final static String T_TITOLO = TABLE_NAME + "." + TITOLO;
	   // COLUMN TYPE [varchar]
	   // COLUMN SIZE [10]
	   // NULLABLE [YES]

	public String TITOLO_field;
}
