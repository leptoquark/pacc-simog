package it.avlp.simog.db.generated; 
	/*
	*	FILE TIPO_APPALTO_AGG created lun 17/08/2009 13:45:36:687
	*/

public class TIPO_APPALTO_AGG {

	public final static String TABLE_NAME = "TIPO_APPALTO_AGG";


	public final static String ID_TIPOAPP_AGG = "ID_TIPOAPP_AGG";
	public final static String T_ID_TIPOAPP_AGG = TABLE_NAME + "." + ID_TIPOAPP_AGG;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_TIPOAPP_AGG_field;

	public final static String DATA_INIZIO_TIPOAPP = "DATA_INIZIO_TIPOAPP";
	public final static String T_DATA_INIZIO_TIPOAPP = TABLE_NAME + "." + DATA_INIZIO_TIPOAPP;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_TIPOAPP_field;

	public final static String DATA_FINE_TIPOAPP = "DATA_FINE_TIPOAPP";
	public final static String T_DATA_FINE_TIPOAPP = TABLE_NAME + "." + DATA_FINE_TIPOAPP;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_TIPOAPP_field;

	public final static String ID_STATO = "ID_STATO";
	public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STATO_field;

	public final static String ID_AGGIUDICAZIONE = "ID_AGGIUDICAZIONE";
	public final static String T_ID_AGGIUDICAZIONE = TABLE_NAME + "." + ID_AGGIUDICAZIONE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_AGGIUDICAZIONE_field;

	public final static String DATA_INIZIO_AGGIUDICAZIONE = "DATA_INIZIO_AGGIUDICAZIONE";
	public final static String T_DATA_INIZIO_AGGIUDICAZIONE = TABLE_NAME + "." + DATA_INIZIO_AGGIUDICAZIONE;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_AGGIUDICAZIONE_field;

	public final static String ID_APPALTO = "ID_APPALTO";
	public final static String T_ID_APPALTO = TABLE_NAME + "." + ID_APPALTO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_APPALTO_field;

	// is3031_RFWEBGL00Active
	public final static String ID_LOTTO = "ID_LOTTO";
   public final static String T_ID_LOTTO = TABLE_NAME + "." + ID_LOTTO;
      // COLUMN TYPE [bigint]
      // COLUMN SIZE [19]
      // NULLABLE [NO]

   public long ID_LOTTO_field;

}
