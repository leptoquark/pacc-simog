package it.avlp.simog.db.generated; 
	/*
	*	FILE DITTE_SUBAPPALTATRICI created lun 17/08/2009 13:45:36:687
	*/
//TICKET ALM - 3.04.3
public class DITTE_SUBAPPALTATRICI {

	public final static String TABLE_NAME = "DITTE_SUBAPPALTATRICI";

	public final static String ID_DITTE_SUBAPPALTATRICI  = "ID_DITTE_SUBAPPALTATRICI";
	public final static String T_ID_DITTE_SUBAPPALTATRICI  = TABLE_NAME + "." + ID_DITTE_SUBAPPALTATRICI ;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_DITTE_SUBAPPALTATRICI_field;
	
	public final static String DATA_INIZIO = "DATA_INIZIO";
	public final static String T_DATA_INIZIO = TABLE_NAME + "." + DATA_INIZIO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_field;
	
	public final static String DATA_FINE = "DATA_FINE";
	public final static String T_DATA_FINE = TABLE_NAME + "." + DATA_FINE;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_FINE_field;
	
	public final static String ID_SUBAPPALTO = "ID_SUBAPPALTO";
	public final static String T_ID_SUBAPPALTO = TABLE_NAME + "." + ID_SUBAPPALTO;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_SUBAPPALTO_field;
	
	public final static String DATA_INIZIO_SUBAPPALTO = "DATA_INIZIO_SUBAPPALTO";
	public final static String T_DATA_INIZIO_SUBAPPALTO = TABLE_NAME + "." + DATA_INIZIO_SUBAPPALTO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_SUBAPPALTO_field;
	

	public long DATA_INIZIO_AGGIUDICATARIO_field;
	
	public final static String ID_SOGGETTO_PARTECIPANTE = "ID_SOGGETTO_PARTECIPANTE";
	public final static String T_ID_SOGGETTO_PARTECIPANTE = TABLE_NAME + "." + ID_SOGGETTO_PARTECIPANTE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_SOGGETTO_PARTECIPANTE_field;

	public final static String DATA_INIZIO_SOGG = "DATA_INIZIO_SOGG";
	public final static String T_DATA_INIZIO_SOGG = TABLE_NAME + "." + DATA_INIZIO_SOGG;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_SOGG_field;

}
