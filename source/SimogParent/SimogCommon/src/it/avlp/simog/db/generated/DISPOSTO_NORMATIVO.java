package it.avlp.simog.db.generated; 
	/*
	*	FILE DISPOSTO_NORMATIVO created mer 05/12/2012 13:04:34:427
	*/

public class DISPOSTO_NORMATIVO {

	public final static String TABLE_NAME = "DISPOSTO_NORMATIVO";


	public final static String COD_DISPOSTO_NORMATIVO = "COD_DISPOSTO_NORMATIVO";
	public final static String T_COD_DISPOSTO_NORMATIVO = TABLE_NAME + "." + COD_DISPOSTO_NORMATIVO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_DISPOSTO_NORMATIVO_field;

	public final static String CODICE = "CODICE";
	public final static String T_CODICE = TABLE_NAME + "." + CODICE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public String CODICE_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [512]
		// NULLABLE [YES]

	public String DESCRIZIONE_field;

	public final static String DESC_ABBR = "DESC_ABBR";
	public final static String T_DESC_ABBR = TABLE_NAME + "." + DESC_ABBR;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [128]
		// NULLABLE [YES]

	public String DESC_ABBR_field;

	public final static String DATA_INIZIO_VALIDITA = "DATA_INIZIO_VALIDITA";
	public final static String T_DATA_INIZIO_VALIDITA = TABLE_NAME + "." + DATA_INIZIO_VALIDITA;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_VALIDITA_field;

	public final static String DATA_FINE_VALIDITA = "DATA_FINE_VALIDITA";
	public final static String T_DATA_FINE_VALIDITA = TABLE_NAME + "." + DATA_FINE_VALIDITA;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_VALIDITA_field;
}
