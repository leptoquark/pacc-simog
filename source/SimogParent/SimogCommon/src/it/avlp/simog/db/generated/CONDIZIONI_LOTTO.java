package it.avlp.simog.db.generated; 
	/*
	*	FILE CONDIZIONI_AGG created lun 17/08/2009 13:45:36:687
	*/
//TICKET ALM #3835
public class CONDIZIONI_LOTTO {

	public final static String TABLE_NAME = "CONDIZIONI_LOTTO";

	
	public final static String ID_CONDIZIONE_LOTTO = "ID_CONDIZIONE_LOTTO";
	public final static String T_ID_CONDIZIONELOTTO = TABLE_NAME + "." + ID_CONDIZIONE_LOTTO;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_CONDIZIONE_LOTTO_field;

	public final static String ID_STATO = "ID_STATO";
	public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STATO_field;

	public final static String ID_LOTTO = "ID_LOTTO";
	public final static String T_ID_LOTTO = TABLE_NAME + "." + ID_LOTTO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_LOTTO_field;

	public final static String ID_CONDIZIONE = "ID_CONDIZIONE";
	public final static String T_ID_CONDIZIONE = TABLE_NAME + "." + ID_CONDIZIONE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_CONDIZIONE_field;
	
	public final static String DATA_INIZIO_COND = "DATA_INIZIO_COND";
	public final static String T_DATA_INIZIO_COND = TABLE_NAME + "." + DATA_INIZIO_COND;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_COND_field;
	
	public final static String DATA_FINE_COND = "DATA_FINE_COND";
	public final static String T_DATA_FINE_COND = TABLE_NAME + "." + DATA_FINE_COND;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_COND_field;
	
}
