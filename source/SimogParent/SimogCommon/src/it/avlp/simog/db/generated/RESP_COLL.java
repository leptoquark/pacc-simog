package it.avlp.simog.db.generated; 
	/*
	*	FILE RESP_COLL created lun 17/08/2009 13:45:36:687
	*/

public class RESP_COLL {

	public final static String TABLE_NAME = "RESP_COLL";


	public final static String ID_COLLAUDO = "ID_COLLAUDO";
	public final static String T_ID_COLLAUDO = TABLE_NAME + "." + ID_COLLAUDO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_COLLAUDO_field;

	public final static String DATA_INIZIO_COLL = "DATA_INIZIO_COLL";
	public final static String T_DATA_INIZIO_COLL = TABLE_NAME + "." + DATA_INIZIO_COLL;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_COLL_field;

	public final static String ID_RESPONSABILE = "ID_RESPONSABILE";
	public final static String T_ID_RESPONSABILE = TABLE_NAME + "." + ID_RESPONSABILE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_RESPONSABILE_field;

	public final static String DATA_INIZIO_RES = "DATA_INIZIO_RES";
	public final static String T_DATA_INIZIO_RES = TABLE_NAME + "." + DATA_INIZIO_RES;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_RES_field;

	public final static String ID_STATO = "ID_STATO";
	public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STATO_field;

	public final static String ID_RUOLO = "ID_RUOLO";
	public final static String T_ID_RUOLO = TABLE_NAME + "." + ID_RUOLO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [YES]

	public String ID_RUOLO_field;

	public final static String ID_RECORD = "ID_RECORD";
	public final static String T_ID_RECORD = TABLE_NAME + "." + ID_RECORD;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_RECORD_field;

	public final static String DATA_INIZIO_RECORD = "DATA_INIZIO_RECORD";
	public final static String T_DATA_INIZIO_RECORD = TABLE_NAME + "." + DATA_INIZIO_RECORD;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_RECORD_field;

	public final static String DATA_FINE_RECORD = "DATA_FINE_RECORD";
	public final static String T_DATA_FINE_RECORD = TABLE_NAME + "." + DATA_FINE_RECORD;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_RECORD_field;
}
