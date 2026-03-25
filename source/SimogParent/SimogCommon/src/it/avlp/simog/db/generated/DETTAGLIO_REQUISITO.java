package it.avlp.simog.db.generated; 
	/*
	*	FILE DETTAGLIO_REQUISITO created mer 05/12/2012 13:04:34:427
	*/

public class DETTAGLIO_REQUISITO {

	public final static String TABLE_NAME = "DETTAGLIO_REQUISITO";


	public final static String COD_DETT_REQUISITO = "COD_DETT_REQUISITO";
	public final static String T_COD_DETT_REQUISITO = TABLE_NAME + "." + COD_DETT_REQUISITO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_DETT_REQUISITO_field;

	public final static String CODICE = "CODICE";
	public final static String T_CODICE = TABLE_NAME + "." + CODICE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [255]
		// NULLABLE [NO]

	public String CODICE_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1024]
		// NULLABLE [YES]

	public String DESCRIZIONE_field;

	public final static String DATA_INIZIO = "DATA_INIZIO";
	public final static String T_DATA_INIZIO = TABLE_NAME + "." + DATA_INIZIO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_INIZIO_field;

	public final static String DATA_FINE = "DATA_FINE";
	public final static String T_DATA_FINE = TABLE_NAME + "." + DATA_FINE;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_field;

	public final static String COD_REQUISITO = "COD_REQUISITO";
	public final static String T_COD_REQUISITO = TABLE_NAME + "." + COD_REQUISITO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_REQUISITO_field;

	public final static String COD_TIPO_UNITA_MISURA = "COD_TIPO_UNITA_MISURA";
	public final static String T_COD_TIPO_UNITA_MISURA = TABLE_NAME + "." + COD_TIPO_UNITA_MISURA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_TIPO_UNITA_MISURA_field;

	public final static String COD_TIPO_USO = "COD_TIPO_USO";
	public final static String T_COD_TIPO_USO = TABLE_NAME + "." + COD_TIPO_USO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_TIPO_USO_field;
}
