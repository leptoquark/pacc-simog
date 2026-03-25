package it.avlp.simog.db.generated; 
	/*
	*	FILE EAGG_GARA_CATEGORIE created mer 20/01/2016 12:30:19:088
	*/

public interface EAGG_GARA_CATEGORIE {

	public final static String TABLE_NAME = "EAGG_GARA_CATEGORIE";


	public final static String COD_GARA_CATEG = "COD_GARA_CATEG";
	public final static String T_COD_GARA_CATEG = TABLE_NAME + "." + COD_GARA_CATEG;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long getCOD_GARA_CATEG();

	public final static String COD_GARA = "COD_GARA";
	public final static String T_COD_GARA = TABLE_NAME + "." + COD_GARA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long getCOD_GARA();

	public final static String COD_CATEGORIA = "COD_CATEGORIA";
	public final static String T_COD_CATEGORIA = TABLE_NAME + "." + COD_CATEGORIA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long getCOD_CATEGORIA();
}
