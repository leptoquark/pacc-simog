package it.avlp.simog.db.generated; 
	/*
	*	FILE EAGG_CATEGORIE_SOGLIE created mer 20/01/2016 12:30:19:088
	*/

import java.math.BigDecimal;

public interface EAGG_CATEGORIE_SOGLIE {

	public final static String TABLE_NAME = "EAGG_CATEGORIE_SOGLIE";


	public final static String COD_CATEGORIA = "COD_CATEGORIA";
	public final static String T_COD_CATEGORIA = TABLE_NAME + "." + COD_CATEGORIA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long getCOD_CATEGORIA();

	public final static String IMPORTO_SOGLIA = "IMPORTO_SOGLIA";
	public final static String T_IMPORTO_SOGLIA = TABLE_NAME + "." + IMPORTO_SOGLIA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public BigDecimal getIMPORTO_SOGLIA();

	public final static String DATA_INIZIO = "DATA_INIZIO";
	public final static String T_DATA_INIZIO = TABLE_NAME + "." + DATA_INIZIO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date getDATA_INIZIO();

	public final static String DATA_FINE = "DATA_FINE";
	public final static String T_DATA_FINE = TABLE_NAME + "." + DATA_FINE;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date getDATA_FINE_VALIDITA();

}
