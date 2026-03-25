package it.avlp.simog.db.generated; 
	/*
	*	FILE ESEC_MOTIVI_VARIANTI created mar 09/10/2007 11:38:24:193
	*/

public class ESEC_MOTIVI_VARIANTI {

	public final static String TABLE_NAME = "ESEC_MOTIVI_VARIANTI";


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

	public final static String ID_STATO = "ID_STATO";
	public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STATO_field;

	public final static String ID_AVANZAMENTO = "ID_AVANZAMENTO";
	public final static String T_ID_AVANZAMENTO = TABLE_NAME + "." + ID_AVANZAMENTO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_AVANZAMENTO_field;

	public final static String DATA_INIZIO_AVANZAMENTO = "DATA_INIZIO_AVANZAMENTO";
	public final static String T_DATA_INIZIO_AVANZAMENTO = TABLE_NAME + "." + DATA_INIZIO_AVANZAMENTO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_AVANZAMENTO_field;

	public final static String ID_MOTIVO_VAR = "ID_MOTIVO_VAR";
	public final static String T_ID_MOTIVO_VAR = TABLE_NAME + "." + ID_MOTIVO_VAR;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_MOTIVO_VAR_field;
}
