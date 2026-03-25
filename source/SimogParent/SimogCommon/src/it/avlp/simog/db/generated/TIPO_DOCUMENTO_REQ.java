package it.avlp.simog.db.generated; 
	/*
	*	FILE TIPO_DOCUMENTO_REQ created mer 05/12/2012 13:04:34:427
	*/

public class TIPO_DOCUMENTO_REQ {

	public final static String TABLE_NAME = "TIPO_DOCUMENTO_REQ";


	public final static String COD_TIPO_DOC_REQ = "COD_TIPO_DOC_REQ";
	public final static String T_COD_TIPO_DOC_REQ = TABLE_NAME + "." + COD_TIPO_DOC_REQ;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_TIPO_DOC_REQ_field;

	public final static String CODICE = "CODICE";
	public final static String T_CODICE = TABLE_NAME + "." + CODICE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [NO]

	public String CODICE_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [80]
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

	public final static String COD_TIPO_FONTE_DOC = "COD_TIPO_FONTE_DOC";
	public final static String T_COD_TIPO_FONTE_DOC = TABLE_NAME + "." + COD_TIPO_FONTE_DOC;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long COD_TIPO_FONTE_DOC_field;
}
