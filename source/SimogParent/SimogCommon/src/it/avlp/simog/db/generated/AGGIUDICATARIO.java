package it.avlp.simog.db.generated; 
	/*
	*	FILE AGGIUDICATARIO created lun 17/08/2009 13:45:36:687
	*/

public class AGGIUDICATARIO {

	public final static String TABLE_NAME = "AGGIUDICATARIO";


	public final static String ID_SOGGETTO_PARTECIPANTE = "ID_SOGGETTO_PARTECIPANTE";
	public final static String T_ID_SOGGETTO_PARTECIPANTE = TABLE_NAME + "." + ID_SOGGETTO_PARTECIPANTE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_SOGGETTO_PARTECIPANTE_field;

	public final static String ID_AGGIUDICAZIONE = "ID_AGGIUDICAZIONE";
	public final static String T_ID_AGGIUDICAZIONE = TABLE_NAME + "." + ID_AGGIUDICAZIONE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_AGGIUDICAZIONE_field;

	public final static String DATA_INIZIO_SOGG = "DATA_INIZIO_SOGG";
	public final static String T_DATA_INIZIO_SOGG = TABLE_NAME + "." + DATA_INIZIO_SOGG;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_SOGG_field;

	public final static String RUOLO = "RUOLO";
	public final static String T_RUOLO = TABLE_NAME + "." + RUOLO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String RUOLO_field;

	public final static String ID_STATO = "ID_STATO";
	public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STATO_field;

	public final static String DATA_INIZIO_AGGIUDICAZIONE = "DATA_INIZIO_AGGIUDICAZIONE";
	public final static String T_DATA_INIZIO_AGGIUDICAZIONE = TABLE_NAME + "." + DATA_INIZIO_AGGIUDICAZIONE;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_AGGIUDICAZIONE_field;

	public final static String PERCENTUALE = "PERCENTUALE";
	public final static String T_PERCENTUALE = TABLE_NAME + "." + PERCENTUALE;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal PERCENTUALE_field;

	public final static String DATA_INIZIO = "DATA_INIZIO";
	public final static String T_DATA_INIZIO = TABLE_NAME + "." + DATA_INIZIO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_field;

	public final static String DATA_FINE = "DATA_FINE";
	public final static String T_DATA_FINE = TABLE_NAME + "." + DATA_FINE;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_field;

	public final static String ID_TIPOAGG = "ID_TIPOAGG";
	public final static String T_ID_TIPOAGG = TABLE_NAME + "." + ID_TIPOAGG;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_TIPOAGG_field;

	public final static String FLAG_AVVALIMENTO = "FLAG_AVVALIMENTO";
	public final static String T_FLAG_AVVALIMENTO = TABLE_NAME + "." + FLAG_AVVALIMENTO;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_AVVALIMENTO_field;

	public final static String CF_AUSILIARIA = "CF_AUSILIARIA";
	public final static String T_CF_AUSILIARIA = TABLE_NAME + "." + CF_AUSILIARIA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String CF_AUSILIARIA_field;

	public final static String ID_AGGIUDICATARIO = "ID_AGGIUDICATARIO";
	public final static String T_ID_AGGIUDICATARIO = TABLE_NAME + "." + ID_AGGIUDICATARIO;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_AGGIUDICATARIO_field;
	
	//gm aggiunto per raggruppamenti di impresa
	public final static String ID_GRUPPO = "ID_GRUPPO";
	public final static String T_ID_GRUPPO = TABLE_NAME + "." + ID_GRUPPO;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_GRUPPO_field;
	
	// Rinaldo ticket 654 ////////
	public final static String IMPORTO_AGGIUDICATARIO = "IMPORTO_AGGIUDICATARIO";
	public final static String T_IMPORTO_AGGIUDICATARIO = TABLE_NAME + "." + IMPORTO_AGGIUDICATARIO;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]
	
	public java.math.BigDecimal IMPORTO_AGGIUDICATARIOE_field;
	
	public final static String PERC_RIBASSO_AGGIUDICATARIO = "PERC_RIBASSO_AGGIUDICATARIO";
	public final static String T_PERC_RIBASSO_AGGIUDICATARIO = TABLE_NAME + "." + PERC_RIBASSO_AGGIUDICATARIO;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]
	
	public java.math.BigDecimal PERC_RIBASSO_AGGIUDICATARIO_field;
	
	public final static String PERC_AUMENTO_AGGIUDICATARIO = "PERC_AUMENTO_AGGIUDICATARIO";
	public final static String T_PERC_AUMENTO_AGGIUDICATARIO = TABLE_NAME + "." + PERC_AUMENTO_AGGIUDICATARIO;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]
	
	public java.math.BigDecimal PERC_AUMENTO_AGGIUDICATARIO_field;
	///////////////////////////////
}
