package it.avlp.simog.db.generated; 
	/*
	*	FILE LOG created lun 17/08/2009 13:45:36:687
	*/

public class LOG {

	public final static String TABLE_NAME = "LOG";


	public final static String DATA_MODIFICA = "DATA_MODIFICA";
	public final static String T_DATA_MODIFICA = TABLE_NAME + "." + DATA_MODIFICA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [NO]

	public String DATA_MODIFICA_field;

	public final static String ID_SA_RIFERIMENTO = "ID_SA_RIFERIMENTO";
	public final static String T_ID_SA_RIFERIMENTO = TABLE_NAME + "." + ID_SA_RIFERIMENTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [40]
		// NULLABLE [NO]

	public String ID_SA_RIFERIMENTO_field;

	public final static String CF_UTENTE = "CF_UTENTE";
	public final static String T_CF_UTENTE = TABLE_NAME + "." + CF_UTENTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [NO]

	public String CF_UTENTE_field;

	public final static String CF_AMMINISTRAZIONE = "CF_AMMINISTRAZIONE";
	public final static String T_CF_AMMINISTRAZIONE = TABLE_NAME + "." + CF_AMMINISTRAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [NO]

	public String CF_AMMINISTRAZIONE_field;

	public final static String CIG_LOTTO = "CIG_LOTTO";
	public final static String T_CIG_LOTTO = TABLE_NAME + "." + CIG_LOTTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [YES]

	public String CIG_LOTTO_field;

	public final static String DESCRIZIONE_AZIONE = "DESCRIZIONE_AZIONE";
	public final static String T_DESCRIZIONE_AZIONE = TABLE_NAME + "." + DESCRIZIONE_AZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1024]
		// NULLABLE [NO]

	public String DESCRIZIONE_AZIONE_field;

	public final static String ID_LOTTO = "ID_LOTTO";
	public final static String T_ID_LOTTO = TABLE_NAME + "." + ID_LOTTO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_LOTTO_field;

	public final static String ID_RECORD = "ID_RECORD";
	public final static String T_ID_RECORD = TABLE_NAME + "." + ID_RECORD;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long Id_record_field;

	public final static String ID_GARA = "ID_GARA";
	public final static String T_ID_GARA = TABLE_NAME + "." + ID_GARA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_GARA_field;
}
