package it.avlp.simog.db.generated; 
	/*
	*	FILE IMPORTI created lun 17/08/2009 13:45:36:687
	*/

public class IMPORTI {

	public final static String TABLE_NAME = "IMPORTI";


	public final static String ID_IMPORTO = "ID_IMPORTO";
	public final static String T_ID_IMPORTO = TABLE_NAME + "." + ID_IMPORTO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long Id_importo_field;

	public final static String IMPORTO_SA = "IMPORTO_SA";
	public final static String T_IMPORTO_SA = TABLE_NAME + "." + IMPORTO_SA;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public java.math.BigDecimal Importo_sa_field;

	public final static String IMPORTO_AZIENDA = "IMPORTO_AZIENDA";
	public final static String T_IMPORTO_AZIENDA = TABLE_NAME + "." + IMPORTO_AZIENDA;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public java.math.BigDecimal Importo_azienda_field;

	public final static String IMPORTO_LOTTO_MIN = "IMPORTO_LOTTO_MIN";
	public final static String T_IMPORTO_LOTTO_MIN = TABLE_NAME + "." + IMPORTO_LOTTO_MIN;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public java.math.BigDecimal Importo_lotto_min_field;

	public final static String IMPORTO_LOTTO_MAX = "IMPORTO_LOTTO_MAX";
	public final static String T_IMPORTO_LOTTO_MAX = TABLE_NAME + "." + IMPORTO_LOTTO_MAX;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public java.math.BigDecimal Importo_lotto_max_field;

	public final static String DATA_FINE_VALIDITA = "DATA_FINE_VALIDITA";
	public final static String T_DATA_FINE_VALIDITA = TABLE_NAME + "." + DATA_FINE_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String Data_fine_validita_field;

	public final static String DATA_ULTIMA_MODIFICA = "DATA_ULTIMA_MODIFICA";
	public final static String T_DATA_ULTIMA_MODIFICA = TABLE_NAME + "." + DATA_ULTIMA_MODIFICA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String Data_ultima_modifica_field;
}
