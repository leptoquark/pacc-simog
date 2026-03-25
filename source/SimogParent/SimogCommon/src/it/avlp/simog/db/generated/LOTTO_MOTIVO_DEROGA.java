package it.avlp.simog.db.generated;

public interface LOTTO_MOTIVO_DEROGA {
	public final static String TABLE_NAME = "LOTTO_MOTIVO_DEROGA";

	public final static Boolean IDENTITY = Boolean.TRUE;
	
	public final static String ID_LOTTO_MOTIVO_DEROGA = "Id_Lotto_Motivo_Deroga";
	public final static String T_ID_LOTTO_MOTIVO_DEROGA = "LOTTO_MOTIVO_DEROGA.Id_Lotto_Motivo_Deroga";
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]
	
	public long getId_Lotto_Motivo_Deroga();

	
	public final static String ID_LOTTO = "Id_Lotto";
	public final static String T_ID_LOTTO = "LOTTO_MOTIVO_DEROGA.ID_LOTTO";
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]
	
	public long getId_Lotto();

	
	public final static String ID_MOTIVO_DEROGA = "Id_Motivo_Deroga";
	public final static String T_ID_MOTIVO_DEROGA = "LOTTO_MOTIVO_DEROGA.Id_Motivo_Deroga";
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long getId_Motivo_Deroga();


	public final static String DATA_INIZIO_VALIDITA = "Data_Inizio_Validita";
	public final static String T_DATA_INIZIO_VALIDITA = "LOTTO_MOTIVO_DEROGA.Data_Inizio_Validita";
		// COLUMN TYPE [varchar] (8)
		// NULLABLE [YES]

	public String getData_Inizio_Validita();

	public final static String DATA_FINE_VALIDITA = "Data_Fine_Validita";
	public final static String T_DATA_FINE_VALIDITA = "LOTTO_MOTIVO_DEROGA.Data_Fine_Validita";
		// COLUMN TYPE [varchar] (8)
		// NULLABLE [YES]

	public String getData_Fine_Validita();
	
	public final static String DATA_ULTIMA_MODIFICA = "Data_Ultima_Modifica";
	public final static String T_DATA_ULTIMA_MODIFICA = "LOTTO_MOTIVO_DEROGA.Data_Ultima_Modifica";
		// COLUMN TYPE [varchar] (8)
		// NULLABLE [YES]

	public String getData_Ultima_Modifica();
}
