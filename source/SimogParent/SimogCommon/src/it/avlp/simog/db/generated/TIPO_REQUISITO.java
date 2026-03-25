package it.avlp.simog.db.generated; 
	/*
	*	FILE TIPO_REQUISITO created mer 05/12/2012 13:04:34:427
	*/

public class TIPO_REQUISITO {

	public final static String TABLE_NAME = "TIPO_REQUISITO";


	public final static String COD_TIPO_REQUISITO = "COD_TIPO_REQUISITO";
	public final static String T_COD_TIPO_REQUISITO = TABLE_NAME + "." + COD_TIPO_REQUISITO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_TIPO_REQUISITO_field;

	public final static String CODICE = "CODICE";
	public final static String T_CODICE = TABLE_NAME + "." + CODICE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [NO]

	public String CODICE_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1024]
		// NULLABLE [NO]

	public String DESCRIZIONE_field;

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
		// NULLABLE [NO]

	public java.sql.Date DATA_FINE_VALIDITA_field;
}
