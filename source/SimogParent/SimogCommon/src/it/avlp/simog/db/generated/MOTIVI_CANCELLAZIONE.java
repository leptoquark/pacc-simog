package it.avlp.simog.db.generated; 
	/*
	*	FILE MOTIVI_CANCELLAZIONE created lun 21/09/2009 15:52:32:750
	*/

public class MOTIVI_CANCELLAZIONE {

	public final static String TABLE_NAME = "MOTIVI_CANCELLAZIONE";


	public final static String ID_MOTIVO_CANC = "ID_MOTIVO_CANC";
	public final static String T_ID_MOTIVO_CANC = TABLE_NAME + "." + ID_MOTIVO_CANC;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_MOTIVO_CANC_field;

	public final static String GARA_LOTTO = "GARA_LOTTO";
	public final static String T_GARA_LOTTO = TABLE_NAME + "." + GARA_LOTTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [NO]

	public String GARA_LOTTO_field;

	public final static String NOTA_OBBLIGATORIA = "NOTA_OBBLIGATORIA";
	public final static String T_NOTA_OBBLIGATORIA = TABLE_NAME + "." + NOTA_OBBLIGATORIA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [NO]

	public String NOTA_OBBLIGATORIA_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [100]
		// NULLABLE [NO]

	public String DESCRIZIONE_field;

	public final static String DATA_FINE_VALIDITA = "DATA_FINE_VALIDITA";
	public final static String T_DATA_FINE_VALIDITA = TABLE_NAME + "." + DATA_FINE_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_FINE_VALIDITA_field;

	public final static String DATA_ULTIMA_MODIFICA = "DATA_ULTIMA_MODIFICA";
	public final static String T_DATA_ULTIMA_MODIFICA = TABLE_NAME + "." + DATA_ULTIMA_MODIFICA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_ULTIMA_MODIFICA_field;
}
