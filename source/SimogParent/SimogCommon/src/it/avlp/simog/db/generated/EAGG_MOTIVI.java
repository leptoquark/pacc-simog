package it.avlp.simog.db.generated; 
	/*
	*	FILE EAGG_MOTIVI created mer 20/01/2016 12:30:19:088
	*/

public interface EAGG_MOTIVI {

	public final static String TABLE_NAME = "EAGG_MOTIVI";


	public final static String COD_MOTIVO = "COD_MOTIVO";
	public final static String T_COD_MOTIVO = TABLE_NAME + "." + COD_MOTIVO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long getCOD_MOTIVO();

	public final static String CODICE = "CODICE";
	public final static String T_CODICE = TABLE_NAME + "." + CODICE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public String getCODICE();

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [512]
		// NULLABLE [NO]

	public String getDESCRIZIONE();

	public final static String DATA_INIZIO_VALIDITA = "DATA_INIZIO_VALIDITA";
	public final static String T_DATA_INIZIO_VALIDITA = TABLE_NAME + "." + DATA_INIZIO_VALIDITA;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date getDATA_INIZIO_VALIDITA();

	public final static String DATA_FINE_VALIDITA = "DATA_FINE_VALIDITA";
	public final static String T_DATA_FINE_VALIDITA = TABLE_NAME + "." + DATA_FINE_VALIDITA;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date getDATA_FINE_VALIDITA();

	public final static String DATA_ULTIMA_MODIFICA = "DATA_ULTIMA_MODIFICA";
	public final static String T_DATA_ULTIMA_MODIFICA = TABLE_NAME + "." + DATA_ULTIMA_MODIFICA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String getDATA_ULTIMA_MODIFICA();
}
