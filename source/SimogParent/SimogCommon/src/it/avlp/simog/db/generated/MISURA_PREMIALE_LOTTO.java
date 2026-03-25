package it.avlp.simog.db.generated; 


public class MISURA_PREMIALE_LOTTO {

	public final static String TABLE_NAME = "LOTTO_MISURA_PREMIALE";
	
	public final static String ID_MISURA_PREMIALE_LOTTO = "ID_LOTTO_MISURA_PREMIALE";
	public final static String T_ID_MISURA_PREMIALELOTTO = TABLE_NAME + "." + ID_MISURA_PREMIALE_LOTTO;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_LOTTO_MISURA_PREMIALE_field;
	
	public final static String ID_LOTTO = "ID_LOTTO";
	public final static String T_ID_LOTTO = TABLE_NAME + "." + ID_LOTTO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_LOTTO_field;
	
	public final static String ID_MISURA_PREMIALE = "ID_MISURA_PREMIALE";
	public final static String T_ID_MISURA_PREMIALE = TABLE_NAME + "." + ID_MISURA_PREMIALE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_MISURA_PREMIALE_field;
	
	public final static String DATA_INIZIO_VALIDITA = "DATA_INIZIO_VALIDITA";
	public final static String T_DATA_INIZIO_VALIDITA = TABLE_NAME + "." + DATA_INIZIO_VALIDITA;
		// COLUMN TYPE [varchar] (8)
		// COLUMN SIZE [23]
		// NULLABLE [NO]

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
