package it.avlp.simog.db.generated; 
	/*
	*	FILE RICHIESTA_ANNULLAMENTO created ven 01/02/2008 14:47:53:038
	*/

public class RICHIESTA_ANNULLAMENTO {

	public final static String TABLE_NAME = "RICHIESTA_ANNULLAMENTO";


	public final static String ID_RICHIESTA = "ID_RICHIESTA";
	public final static String T_ID_RICHIESTA = TABLE_NAME + "." + ID_RICHIESTA;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_RICHIESTA_field;

	public final static String DATA_INIZIO = "DATA_INIZIO";
	public final static String T_DATA_INIZIO = TABLE_NAME + "." + DATA_INIZIO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_field;

	public final static String ID_RECORD = "ID_RECORD";
	public final static String T_ID_RECORD = TABLE_NAME + "." + ID_RECORD;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_RECORD_field;

	public final static String DATA_INIZIO_RECORD = "DATA_INIZIO_RECORD";
	public final static String T_DATA_INIZIO_RECORD = TABLE_NAME + "." + DATA_INIZIO_RECORD;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_RECORD_field;

	public final static String BLOCCO = "BLOCCO";
	public final static String T_BLOCCO = TABLE_NAME + "." + BLOCCO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public String BLOCCO_field;

	public final static String RICHIEDENTE = "RICHIEDENTE";
	public final static String T_RICHIEDENTE = TABLE_NAME + "." + RICHIEDENTE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public char RICHIEDENTE_field;

	public final static String MOTIVO_RICHIESTA = "MOTIVO_RICHIESTA";
	public final static String T_MOTIVO_RICHIESTA = TABLE_NAME + "." + MOTIVO_RICHIESTA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1000]
		// NULLABLE [YES]

	public String MOTIVO_RICHIESTA_field;

	public final static String DATA_FINE = "DATA_FINE";
	public final static String T_DATA_FINE = TABLE_NAME + "." + DATA_FINE;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_field;

	public final static String ESITO = "ESITO";
	public final static String T_ESITO = TABLE_NAME + "." + ESITO;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char ESITO_field;

	public final static String MOTIVO_ESITO = "MOTIVO_ESITO";
	public final static String T_MOTIVO_ESITO = TABLE_NAME + "." + MOTIVO_ESITO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String MOTIVO_ESITO_field;

	public final static String DECISORE = "DECISORE";
	public final static String T_DECISORE = TABLE_NAME + "." + DECISORE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String DECISORE_field;

	public final static String ID_LOTTO = "ID_LOTTO";
	public final static String T_ID_LOTTO = TABLE_NAME + "." + ID_LOTTO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_LOTTO_field;
	
	public final static String CANCELLAZIONE = "CANCELLAZIONE";
	public final static String T_CANCELLAZIONE = TABLE_NAME + "." + CANCELLAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String CANCELLAZIONE_field;

    public final static String ID_MOTIVO_RICH = "ID_MOTIVO_RICH";
    public final static String T_ID_MOTIVO_RICH = TABLE_NAME + "." + ID_MOTIVO_RICH;
        // COLUMN TYPE [varchar]
        // COLUMN SIZE [1]
        // NULLABLE [YES]

    public String ID_MOTIVO_RICH_field;
}
