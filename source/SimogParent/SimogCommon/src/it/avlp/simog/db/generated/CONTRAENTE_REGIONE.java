package it.avlp.simog.db.generated; 
	/*
	 * // is3028_RFWEBGL00Active
	 * 
	*	FILE CONTRAENTE_REGIONE created ven 15/02/2013 13:09:26:890
	*/

public class CONTRAENTE_REGIONE {

	public final static String TABLE_NAME = "CONTRAENTE_REGIONE";


	public final static String ID_RECORD = "ID_RECORD";
	public final static String T_ID_RECORD = TABLE_NAME + "." + ID_RECORD;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_RECORD_field;

	public final static String ID_OSSERVATORIO = "ID_OSSERVATORIO";
	public final static String T_ID_OSSERVATORIO = TABLE_NAME + "." + ID_OSSERVATORIO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [3]
		// NULLABLE [NO]

	public String ID_OSSERVATORIO_field;

	public final static String ID_EQUIVALENTE = "ID_EQUIVALENTE";
	public final static String T_ID_ID_EQUIVALENTE = TABLE_NAME + "." + ID_EQUIVALENTE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public String ID_EQUIVALENTE_field;

	public final static String ID_SCELTA_AVCP = "ID_SCELTA_AVCP";
	public final static String T_ID_SCELTA_AVCP = TABLE_NAME + "." + ID_SCELTA_AVCP;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_SCELTA_AVCP_field;

	public final static String ID_SCELTA_OSS = "ID_SCELTA_OSS";
	public final static String T_ID_SCELTA_OSS = TABLE_NAME + "." + ID_SCELTA_OSS;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_SCELTA_OSS_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [256]
		// NULLABLE [YES]

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

   public final static String TIPO_CONTRATTO = "TIPO_CONTRATTO";
   public final static String T_TIPO_CONTRATTO = TABLE_NAME + "." + TIPO_CONTRATTO;
      // COLUMN TYPE [varchar]
      // COLUMN SIZE [1]
      // NULLABLE [YES]

   public String TIPO_CONTRATTO_field;
}
