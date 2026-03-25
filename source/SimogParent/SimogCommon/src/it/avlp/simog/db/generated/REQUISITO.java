package it.avlp.simog.db.generated; 
	/*
	*	FILE REQUISITO created mer 05/12/2012 13:04:34:427
	*/

public class REQUISITO {

	public final static String TABLE_NAME = "REQUISITO";


	public final static String COD_REQUISITO = "COD_REQUISITO";
	public final static String T_COD_REQUISITO = TABLE_NAME + "." + COD_REQUISITO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_REQUISITO_field;

	public final static String COD_TIPO_REQUISITO = "COD_TIPO_REQUISITO";
	public final static String T_COD_TIPO_REQUISITO = TABLE_NAME + "." + COD_TIPO_REQUISITO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_TIPO_REQUISITO_field;

	public final static String COD_DISPOSTO_NORMATIVO = "COD_DISPOSTO_NORMATIVO";
	public final static String T_COD_DISPOSTO_NORMATIVO = TABLE_NAME + "." + COD_DISPOSTO_NORMATIVO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_DISPOSTO_NORMATIVO_field;

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
