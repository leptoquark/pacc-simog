package it.avlp.simog.db.generated; 
	/*
	*	FILE MOTIVO_DEROGA_AGG created lun 17/08/2009 13:45:36:687
	*/
//TICKET ALM #3835
public class MOTIVO_DEROGA_LOTTO {

	public final static String TABLE_NAME = "LOTTO_MOTIVO_DEROGA";

	
	public final static String ID_LOTTO_MOTIVO_DEROGA = "ID_LOTTO_MOTIVO_DEROGA";
	public final static String T_ID_LOTTO_MOTIVO_DEROGA = TABLE_NAME + "." + ID_LOTTO_MOTIVO_DEROGA;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_LOTTO_MOTIVO_DEROGA_field;

	public final static String ID_LOTTO = "ID_LOTTO";
	public final static String T_ID_LOTTO = TABLE_NAME + "." + ID_LOTTO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_LOTTO_field;

	public final static String ID_MOTIVO_DEROGA = "ID_MOTIVO_DEROGA";
	public final static String T_ID_MOTIVO_DEROGA = TABLE_NAME + "." + ID_MOTIVO_DEROGA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_MOTIVO_DEROGA_field;
	
	public final static String DATA_INIZIO_VALIDITA = "DATA_INIZIO_VALIDITA";
	public final static String T_DATA_INIZIO_VALIDITA = TABLE_NAME + "." + DATA_INIZIO_VALIDITA;
		// COLUMN TYPE [varchar] (8)
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public String DATA_INIZIO_VALIDITA_field;
	
	public final static String DATA_FINE_VALIDITA = "DATA_FINE_VALIDITA";
	public final static String T_DATA_FINE_VALIDITA = TABLE_NAME + "." + DATA_FINE_VALIDITA;
		// COLUMN TYPE [varchar] (8)
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public String DATA_FINE_VALIDITA_field;
	
	public final static String DATA_ULTIMA_MODIFICA = "DATA_ULTIMA_MODIFICA";
	public final static String T_DATA_ULTIMA_MODIFICA = TABLE_NAME + "." + DATA_ULTIMA_MODIFICA;
		// COLUMN TYPE [varchar] (8)
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public String DATA_ULTIMA_MODIFICA_field;
}
