package it.avlp.simog.db.generated; 
	/*
	*	FILE ATTI_AGGIUNTIVI created mar 09/10/2007 11:38:24:193
	*/

public class ATTI_AGGIUNTIVI {

	public final static String TABLE_NAME = "ATTI_AGGIUNTIVI";


	public final static String ID_RECORD = "ID_RECORD";
	public final static String T_ID_RECORD = TABLE_NAME + "." + ID_RECORD;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_RECORD_field;

	public final static String DATA_INIZIO_RECORD = "DATA_INIZIO_RECORD";
	public final static String T_DATA_INIZIO_RECORD = TABLE_NAME + "." + DATA_INIZIO_RECORD;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_RECORD_field;

	public final static String DATA_FINE_RECORD = "DATA_FINE_RECORD";
	public final static String T_DATA_FINE_RECORD = TABLE_NAME + "." + DATA_FINE_RECORD;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_RECORD_field;

	public final static String ID_STATO = "ID_STATO";
	public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STATO_field;

	public final static String ID_AVANZAMENTO = "ID_AVANZAMENTO";
	public final static String T_ID_AVANZAMENTO = TABLE_NAME + "." + ID_AVANZAMENTO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_AVANZAMENTO_field;

	public final static String DATA_INIZIO_AVANZAMENTO = "DATA_INIZIO_AVANZAMENTO";
	public final static String T_DATA_INIZIO_AVANZAMENTO = TABLE_NAME + "." + DATA_INIZIO_AVANZAMENTO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_AVANZAMENTO_field;

	public final static String DATA_ATTO = "DATA_ATTO";
	public final static String T_DATA_ATTO = TABLE_NAME + "." + DATA_ATTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [NO]

	public String DATA_ATTO_field;

	public final static String OGGETTO = "OGGETTO";
	public final static String T_OGGETTO = TABLE_NAME + "." + OGGETTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String OGGETTO_field;

	public final static String IMPORTO = "IMPORTO";
	public final static String T_IMPORTO = TABLE_NAME + "." + IMPORTO;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_field;

	public final static String DATA_ACCORDO = "DATA_ACCORDO";
	public final static String T_DATA_ACCORDO = TABLE_NAME + "." + DATA_ACCORDO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_ACCORDO_field;

	public final static String ONERI = "ONERI";
	public final static String T_ONERI = TABLE_NAME + "." + ONERI;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal ONERI_field;

	public final static String INTERESSI = "INTERESSI";
	public final static String T_INTERESSI = TABLE_NAME + "." + INTERESSI;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal INTERESSI_field;
}
