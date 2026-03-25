package it.avlp.simog.db.generated; 
	/*
	*	FILE VARIANTI created lun 17/08/2009 13:45:36:687
	*/

public class VARIANTI {

	public final static String TABLE_NAME = "VARIANTI";


	public final static String ID_VARIANTE = "ID_VARIANTE";
	public final static String T_ID_VARIANTE = TABLE_NAME + "." + ID_VARIANTE;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_VARIANTE_field;

	public final static String DATA_INIZIO_VAR = "DATA_INIZIO_VAR";
	public final static String T_DATA_INIZIO_VAR = TABLE_NAME + "." + DATA_INIZIO_VAR;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_VAR_field;

	public final static String DATA_FINE_VAR = "DATA_FINE_VAR";
	public final static String T_DATA_FINE_VAR = TABLE_NAME + "." + DATA_FINE_VAR;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_VAR_field;

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

	public final static String IMP_RIDET_LAVORI = "IMP_RIDET_LAVORI";
	public final static String T_IMP_RIDET_LAVORI = TABLE_NAME + "." + IMP_RIDET_LAVORI;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMP_RIDET_LAVORI_field;

	public final static String IMP_RIDET_SERVIZI = "IMP_RIDET_SERVIZI";
	public final static String T_IMP_RIDET_SERVIZI = TABLE_NAME + "." + IMP_RIDET_SERVIZI;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMP_RIDET_SERVIZI_field;

	public final static String IMP_RIDET_FORNIT = "IMP_RIDET_FORNIT";
	public final static String T_IMP_RIDET_FORNIT = TABLE_NAME + "." + IMP_RIDET_FORNIT;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMP_RIDET_FORNIT_field;

	public final static String IMP_SICUREZZA = "IMP_SICUREZZA";
	public final static String T_IMP_SICUREZZA = TABLE_NAME + "." + IMP_SICUREZZA;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMP_SICUREZZA_field;

	public final static String IMP_PROGETTAZIONE = "IMP_PROGETTAZIONE";
	public final static String T_IMP_PROGETTAZIONE = TABLE_NAME + "." + IMP_PROGETTAZIONE;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]
	
	
	public java.math.BigDecimal IMP_PROGETTAZIONE_field;


	
	public final static String ULTERIORI_SOMME = "ULTERIORI_SOMME";
	public final static String T_ULTERIORI_SOMME = TABLE_NAME + "." + ULTERIORI_SOMME;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]
	
	public java.math.BigDecimal ULTERIORI_SOMME_field;
	
	public final static String IMP_DISPOSIZIONE = "IMP_DISPOSIZIONE";
	public final static String T_IMP_DISPOSIZIONE = TABLE_NAME + "." + IMP_DISPOSIZIONE;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMP_DISPOSIZIONE_field;

	public final static String DATA_ATTO_AGGIUNTIVO = "DATA_ATTO_AGGIUNTIVO";
	public final static String T_DATA_ATTO_AGGIUNTIVO = TABLE_NAME + "." + DATA_ATTO_AGGIUNTIVO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_ATTO_AGGIUNTIVO_field;

	public final static String NUM_GIORNI_PROROGA = "NUM_GIORNI_PROROGA";
	public final static String T_NUM_GIORNI_PROROGA = TABLE_NAME + "." + NUM_GIORNI_PROROGA;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int NUM_GIORNI_PROROGA_field;

	public final static String ALTRE_MOTIVAZIONI = "ALTRE_MOTIVAZIONI";
	public final static String T_ALTRE_MOTIVAZIONI = TABLE_NAME + "." + ALTRE_MOTIVAZIONI;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1000]
		// NULLABLE [YES]

	public String ALTRE_MOTIVAZIONI_field;

	public final static String DATA_VERB_APPR = "DATA_VERB_APPR";
	public final static String T_DATA_VERB_APPR = TABLE_NAME + "." + DATA_VERB_APPR;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_VERB_APPR_field;

	public final static String ID_SCHEDA_LOCALE = "ID_SCHEDA_LOCALE";
	public final static String T_ID_SCHEDA_LOCALE = TABLE_NAME + "." + ID_SCHEDA_LOCALE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String ID_SCHEDA_LOCALE_field;
	
	//TICKET ALM - 3.04.3 PT
	public final static String CIG_PROCEDURA = "CIG_PROCEDURA";
	public static final String T_CIG_PROCEDURA =  TABLE_NAME + "." + CIG_PROCEDURA;
	
	public String CIG_PROCECURA_field;
	
	//MEV 34191 3.04.8
		public final static String LINK_VARIANTI = "LINK_VARIANTI";
		public final static String T_LINK_VARIANTI = TABLE_NAME + "." + LINK_VARIANTI;
			// COLUMN TYPE [char]
			// COLUMN SIZE [255]
			// NULLABLE [YES]
		
		public char LINK_VARIANTI_field;
		
	//MEV 34469 3.04.8
	public final static String ID_MOTIVO_REV_PREZZI = "ID_MOTIVO_REV_PREZZI";
	public static final String T_ID_MOTIVO_REV_PREZZI =  TABLE_NAME + "." + ID_MOTIVO_REV_PREZZI;
	
	public String ID_MOTIVO_REV_PREZZI_field;
}
