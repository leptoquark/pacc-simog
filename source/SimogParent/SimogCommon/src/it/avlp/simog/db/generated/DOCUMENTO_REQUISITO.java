package it.avlp.simog.db.generated; 
	/*
	*	FILE DOCUMENTO_REQUISITO created mer 05/12/2012 13:04:34:427
	*/

public class DOCUMENTO_REQUISITO {

	public final static String TABLE_NAME = "DOCUMENTO_REQUISITO";


	public final static String COD_DOC_REQUISITO = "COD_DOC_REQUISITO";
	public final static String T_COD_DOC_REQUISITO = TABLE_NAME + "." + COD_DOC_REQUISITO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_DOC_REQUISITO_field;

	public final static String COD_TIPO_DOC_REQ = "COD_TIPO_DOC_REQ";
	public final static String T_COD_TIPO_DOC_REQ = TABLE_NAME + "." + COD_TIPO_DOC_REQ;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_TIPO_DOC_REQ_field;

	public final static String COD_DETT_REQUISITO = "COD_DETT_REQUISITO";
	public final static String T_COD_DETT_REQUISITO = TABLE_NAME + "." + COD_DETT_REQUISITO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_DETT_REQUISITO_field;

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
}
