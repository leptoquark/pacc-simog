package it.avlp.simog.db.generated; 
	/*
	*	FILE SOGGETTI_RESPONSABILI created lun 17/08/2009 13:45:36:687
	*/

public class SOGGETTI_RESPONSABILI {

	public final static String TABLE_NAME = "SOGGETTI_RESPONSABILI";


	public final static String ID_RESPONSABILE = "ID_RESPONSABILE";
	public final static String T_ID_RESPONSABILE = TABLE_NAME + "." + ID_RESPONSABILE;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_RESPONSABILE_field;

	public final static String DATA_INIZIO_RES = "DATA_INIZIO_RES";
	public final static String T_DATA_INIZIO_RES = TABLE_NAME + "." + DATA_INIZIO_RES;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_RES_field;

	public final static String CODICE_FISCALE_RESPONSABILE = "CODICE_FISCALE_RESPONSABILE";
	public final static String T_CODICE_FISCALE_RESPONSABILE = TABLE_NAME + "." + CODICE_FISCALE_RESPONSABILE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CODICE_FISCALE_RESPONSABILE_field;

	public final static String COGNOME = "COGNOME";
	public final static String T_COGNOME = TABLE_NAME + "." + COGNOME;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String COGNOME_field;

	public final static String NOME = "NOME";
	public final static String T_NOME = TABLE_NAME + "." + NOME;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String NOME_field;

	public final static String TELEFONO = "TELEFONO";
	public final static String T_TELEFONO = TABLE_NAME + "." + TELEFONO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String TELEFONO_field;

	public final static String EMAIL = "EMAIL";
	public final static String T_EMAIL = TABLE_NAME + "." + EMAIL;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [64]
		// NULLABLE [YES]

	public String EMAIL_field;

	public final static String FAX = "FAX";
	public final static String T_FAX = TABLE_NAME + "." + FAX;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String FAX_field;

	public final static String INDIRIZZO = "INDIRIZZO";
	public final static String T_INDIRIZZO = TABLE_NAME + "." + INDIRIZZO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [100]
		// NULLABLE [YES]

	public String INDIRIZZO_field;

	public final static String CAP = "CAP";
	public final static String T_CAP = TABLE_NAME + "." + CAP;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [5]
		// NULLABLE [YES]

	public String CAP_field;

	public final static String COMUNE_ISTAT = "COMUNE_ISTAT";
	public final static String T_COMUNE_ISTAT = TABLE_NAME + "." + COMUNE_ISTAT;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String COMUNE_ISTAT_field;

	public final static String DATA_FINE_RES = "DATA_FINE_RES";
	public final static String T_DATA_FINE_RES = TABLE_NAME + "." + DATA_FINE_RES;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_RES_field;
}
