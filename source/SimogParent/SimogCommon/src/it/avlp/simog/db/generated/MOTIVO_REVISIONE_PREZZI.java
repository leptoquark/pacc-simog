package it.avlp.simog.db.generated; 
	/*
	*	MEV 34469 3.04.8
	*/

public class MOTIVO_REVISIONE_PREZZI {

	public final static String TABLE_NAME = "MOTIVO_REVISIONE_PREZZI";


	public final static String ID_MOTIVO_REV_PREZZI = "ID_MOTIVO_REVISIONE_PREZZI";
	public final static String T_ID_MOTIVO_REV_PREZZI = TABLE_NAME + "." + ID_MOTIVO_REV_PREZZI;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_MOTIVO_VAR_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [100]
		// NULLABLE [NO]

	public String DESCRIZIONE_field;

	//TICKET ALM #2847 - Variante
	public final static String DATA_INIZIO_VALIDITA = "DATA_INIZIO_VALIDITA";
	public final static String T_DATA_INIZIO_VALIDITA = TABLE_NAME + "." + DATA_INIZIO_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_INIZIO_VALIDITA_field;
	
	public final static String DATA_FINE_VALIDITA = "DATA_FINE_VALIDITA";
	public final static String T_DATA_FINE_VALIDITA = TABLE_NAME + "." + DATA_FINE_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_FINE_VALIDITA_field;
}
