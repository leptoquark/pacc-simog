package it.avlp.simog.db.generated; 
	/*
	*	FILE FINANZIAMENTI_AGG created lun 17/08/2009 13:45:36:687
	*/

public class FINANZIAMENTI_AGG {

	public final static String TABLE_NAME = "FINANZIAMENTI_AGG";


	public final static String ID_FINANZ_AGG = "ID_FINANZ_AGG";
	public final static String T_ID_FINANZ_AGG = TABLE_NAME + "." + ID_FINANZ_AGG;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_FINANZ_AGG_field;

	public final static String DATA_INIZIO_FINAGG = "DATA_INIZIO_FINAGG";
	public final static String T_DATA_INIZIO_FINAGG = TABLE_NAME + "." + DATA_INIZIO_FINAGG;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_FINAGG_field;

	public final static String DATA_FINE_FINAGG = "DATA_FINE_FINAGG";
	public final static String T_DATA_FINE_FINAGG = TABLE_NAME + "." + DATA_FINE_FINAGG;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_FINAGG_field;

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

	public final static String ID_FINANZIAMENTO = "ID_FINANZIAMENTO";
	public final static String T_ID_FINANZIAMENTO = TABLE_NAME + "." + ID_FINANZIAMENTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [NO]

	public String ID_FINANZIAMENTO_field;

	public final static String IMPORTO_FINANZIAMENTO = "IMPORTO_FINANZIAMENTO";
	public final static String T_IMPORTO_FINANZIAMENTO = TABLE_NAME + "." + IMPORTO_FINANZIAMENTO;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [NO]

	public java.math.BigDecimal IMPORTO_FINANZIAMENTO_field;
}
