package it.avlp.simog.db.generated; 
	/*
	*	FILE CUP_LOTTO_AGG created lun 01/04/2014 11:02:00:000
	*/

public interface CUP_LOTTO_AGG {

	public final static String TABLE_NAME = "CUP_LOTTO_AGG";


	public final static String ID_CUP_LOTTO_AGG = "ID_CUP_LOTTO_AGG";
	public final static String T_ID_CUP_LOTTO_AGG = TABLE_NAME + "." + ID_CUP_LOTTO_AGG;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public final static String DATA_INIZIO_CUP = "DATA_INIZIO_CUP";
	public final static String T_DATA_INIZIO_CUP = TABLE_NAME + "." + DATA_INIZIO_CUP;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public final static String DATA_FINE_CUP = "DATA_FINE_CUP";
	public final static String T_DATA_FINE_CUP = TABLE_NAME + "." + DATA_FINE_CUP;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]
	
   public final static String ID_LOTTO = "ID_LOTTO";
   public final static String T_ID_LOTTO = TABLE_NAME + "." + ID_LOTTO;
      // COLUMN TYPE [bigint identity]
      // COLUMN SIZE [19]
      // NULLABLE [NO]	
	
	public final static String ID_AGGIUDICAZIONE = "ID_AGGIUDICAZIONE";
	public final static String T_ID_AGGIUDICAZIONE = TABLE_NAME + "." + ID_AGGIUDICAZIONE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public final static String DATA_INIZIO_AGG = "DATA_INIZIO_AGG";
	public final static String T_DATA_INIZIO_AGG = TABLE_NAME + "." + DATA_INIZIO_AGG;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

   public final static String CUP = "CUP";
   public final static String T_CUP = TABLE_NAME + "." + CUP;
      // COLUMN TYPE [varchar]
      // COLUMN SIZE [15]
      // NULLABLE [YES]
   
   public final static String OK_UTENTE = "OK_UTENTE";
   public final static String T_OK_UTENTE = TABLE_NAME + "." + OK_UTENTE;
      // COLUMN TYPE [varchar]
      // COLUMN SIZE [1]
      // NULLABLE [YES]
   
   public final static String ID_STATO = "ID_STATO";
   public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
      // COLUMN TYPE [bigint]
      // COLUMN SIZE [19]
      // NULLABLE [NO]

}
