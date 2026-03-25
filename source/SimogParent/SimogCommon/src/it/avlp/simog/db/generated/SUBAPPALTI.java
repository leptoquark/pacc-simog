package it.avlp.simog.db.generated; 
	/*
	*	FILE SUBAPPALTI created lun 17/08/2009 13:45:36:687
	*/

public class SUBAPPALTI {

	public final static String TABLE_NAME = "SUBAPPALTI";


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

 	public final static String CF_DITTA = "CF_DITTA";
	public final static String T_CF_DITTA = TABLE_NAME + "." + CF_DITTA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CF_DITTA_field;
 
	public final static String DATA_AUTORIZZAZIONE = "DATA_AUTORIZZAZIONE";
	public final static String T_DATA_AUTORIZZAZIONE = TABLE_NAME + "." + DATA_AUTORIZZAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_AUTORIZZAZIONE_field;

	public final static String OGGETTO_SUBAPPALTO = "OGGETTO_SUBAPPALTO";
	public final static String T_OGGETTO_SUBAPPALTO = TABLE_NAME + "." + OGGETTO_SUBAPPALTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1000]
		// NULLABLE [YES]

	public String OGGETTO_SUBAPPALTO_field;

	public final static String IMPORTO_PRESUNTO = "IMPORTO_PRESUNTO";
	public final static String T_IMPORTO_PRESUNTO = TABLE_NAME + "." + IMPORTO_PRESUNTO;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_PRESUNTO_field;

	public final static String IMPORTO_EFFETTIVO = "IMPORTO_EFFETTIVO";
	public final static String T_IMPORTO_EFFETTIVO = TABLE_NAME + "." + IMPORTO_EFFETTIVO;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_EFFETTIVO_field;

	public final static String ID_CATEGORIA = "ID_CATEGORIA";
	public final static String T_ID_CATEGORIA = TABLE_NAME + "." + ID_CATEGORIA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [YES]

	public String ID_CATEGORIA_field;

	public final static String ID_CPV = "ID_CPV";
	public final static String T_ID_CPV = TABLE_NAME + "." + ID_CPV;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [YES]

	public String ID_CPV_field;

	public final static String ID_SCHEDA_LOCALE = "ID_SCHEDA_LOCALE";
	public final static String T_ID_SCHEDA_LOCALE = TABLE_NAME + "." + ID_SCHEDA_LOCALE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String ID_SCHEDA_LOCALE_field;
	
	//gm nuovo codice 3.0
	public final static String CF_AGGIUDICATARIO = "CF_AGGIUDICATARIO";
	public final static String T_CF_AGGIUDICATARIO = TABLE_NAME + "." + CF_AGGIUDICATARIO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CF_AGGIUDICATARIO_field;
	//gm fine nuovo codice 3.0
	
	//MEV 36771 3.04.8.1
	public final static String FLAG_DITTA_SUB_ESTERA = "FLAG_DITTA_SUB_ESTERA";
	public final static String T_FLAG_DITTA_SUB_ESTERA = TABLE_NAME + "." + FLAG_DITTA_SUB_ESTERA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String FLAG_DITTA_SUB_ESTERA_field;
	//MEV 36771 3.04.8.1

}
