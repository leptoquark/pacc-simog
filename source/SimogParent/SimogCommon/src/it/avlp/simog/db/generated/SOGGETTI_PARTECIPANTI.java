package it.avlp.simog.db.generated; 
	/*
	*	FILE SOGGETTI_PARTECIPANTI created lun 17/08/2009 13:45:36:687
	*/

public class SOGGETTI_PARTECIPANTI {

	public final static String TABLE_NAME = "SOGGETTI_PARTECIPANTI";


	public final static String ID_SOGGETTO_PARTECIPANTE = "ID_SOGGETTO_PARTECIPANTE";
	public final static String T_ID_SOGGETTO_PARTECIPANTE = TABLE_NAME + "." + ID_SOGGETTO_PARTECIPANTE;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_SOGGETTO_PARTECIPANTE_field;

	public final static String DATA_INIZIO_SOGG = "DATA_INIZIO_SOGG";
	public final static String T_DATA_INIZIO_SOGG = TABLE_NAME + "." + DATA_INIZIO_SOGG;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_SOGG_field;

	public final static String CODICE_FISCALE = "CODICE_FISCALE";
	public final static String T_CODICE_FISCALE = TABLE_NAME + "." + CODICE_FISCALE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [NO]

	public String CODICE_FISCALE_field;

	public final static String DENOMINAZIONE = "DENOMINAZIONE";
	public final static String T_DENOMINAZIONE = TABLE_NAME + "." + DENOMINAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [NO]

	public String DENOMINAZIONE_field;

	public final static String CAMERA_COMMERCIO = "CAMERA_COMMERCIO";
	public final static String T_CAMERA_COMMERCIO = TABLE_NAME + "." + CAMERA_COMMERCIO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public String CAMERA_COMMERCIO_field;

	public final static String PARTITA_IVA = "PARTITA_IVA";
	public final static String T_PARTITA_IVA = TABLE_NAME + "." + PARTITA_IVA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String PARTITA_IVA_field;

	public final static String CIVICO = "CIVICO";
	public final static String T_CIVICO = TABLE_NAME + "." + CIVICO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public String CIVICO_field;

	public final static String CAP = "CAP";
	public final static String T_CAP = TABLE_NAME + "." + CAP;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public String CAP_field;

	public final static String PROVINCIA = "PROVINCIA";
	public final static String T_PROVINCIA = TABLE_NAME + "." + PROVINCIA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [2]
		// NULLABLE [YES]

	public String PROVINCIA_field;

	public final static String CITTA = "CITTA";
	public final static String T_CITTA = TABLE_NAME + "." + CITTA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String CITTA_field;

	public final static String CF_RAPPRESENTANTE = "CF_RAPPRESENTANTE";
	public final static String T_CF_RAPPRESENTANTE = TABLE_NAME + "." + CF_RAPPRESENTANTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CF_RAPPRESENTANTE_field;

	public final static String DATA_FINE_SOGG = "DATA_FINE_SOGG";
	public final static String T_DATA_FINE_SOGG = TABLE_NAME + "." + DATA_FINE_SOGG;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_SOGG_field;

	public final static String NOME = "NOME";
	public final static String T_NOME = TABLE_NAME + "." + NOME;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String NOME_field;

	public final static String COGNOME = "COGNOME";
	public final static String T_COGNOME = TABLE_NAME + "." + COGNOME;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String COGNOME_field;

	public final static String INDIRIZZO = "INDIRIZZO";
	public final static String T_INDIRIZZO = TABLE_NAME + "." + INDIRIZZO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String INDIRIZZO_field;

	public final static String ID_STATO = "ID_STATO";
	public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [3]
		// NULLABLE [YES]

	public String ID_STATO_field;
}
