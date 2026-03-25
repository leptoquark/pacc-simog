package it.avlp.simog.db.generated; 
	/*
	*	FILE DOCUMENTO_REQUISITO_GARA created mer 05/12/2012 13:04:34:427
	*/

public class DOCUMENTO_REQUISITO_GARA {

	public final static String TABLE_NAME = "DOCUMENTO_REQUISITO_GARA";


	public final static String COD_DOC_REQ_GARA = "COD_DOC_REQ_GARA";
	public final static String T_COD_DOC_REQ_GARA = TABLE_NAME + "." + COD_DOC_REQ_GARA;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_DOC_REQ_GARA_field;

	public final static String DESCRIZIONE_DOCUMENTO = "DESCRIZIONE_DOCUMENTO";
	public final static String T_DESCRIZIONE_DOCUMENTO = TABLE_NAME + "." + DESCRIZIONE_DOCUMENTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [800]
		// NULLABLE [YES]

	public String DESCRIZIONE_DOCUMENTO_field;

	public final static String EMETTITORE = "EMETTITORE";
	public final static String T_EMETTITORE = TABLE_NAME + "." + EMETTITORE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [300]
		// NULLABLE [YES]

	public String EMETTITORE_field;

	public final static String FAX = "FAX";
	public final static String T_FAX = TABLE_NAME + "." + FAX;
		// COLUMN TYPE [numeric]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public long FAX_field;

	public final static String TELEFONO = "TELEFONO";
	public final static String T_TELEFONO = TABLE_NAME + "." + TELEFONO;
		// COLUMN TYPE [numeric]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public long TELEFONO_field;

	public final static String MAIL = "MAIL";
	public final static String T_MAIL = TABLE_NAME + "." + MAIL;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [80]
		// NULLABLE [YES]

	public String MAIL_field;

	public final static String MAIL_PEC = "MAIL_PEC";
	public final static String T_MAIL_PEC = TABLE_NAME + "." + MAIL_PEC;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [80]
		// NULLABLE [YES]

	public String MAIL_PEC_field;

	public final static String COD_REQUISITO_GARA = "COD_REQUISITO_GARA";
	public final static String T_COD_REQUISITO_GARA = TABLE_NAME + "." + COD_REQUISITO_GARA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_REQUISITO_GARA_field;

	public final static String COD_TIPO_DOC_REQ = "COD_TIPO_DOC_REQ";
	public final static String T_COD_TIPO_DOC_REQ = TABLE_NAME + "." + COD_TIPO_DOC_REQ;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_TIPO_DOC_REQ_field;
}
