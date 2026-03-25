package it.avlp.simog.db.generated; 
	/*
	*	FILE ALLEGATI created lun 22/11/2010 11:58:52:125
	*/

public class ALLEGATI {

	public final static String TABLE_NAME = "ALLEGATI";


	public final static String ID_ALLEGATO = "ID_ALLEGATO";
	public final static String T_ID_ALLEGATO = TABLE_NAME + "." + ID_ALLEGATO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_ALLEGATO_field;

	public final static String ID_GARA = "ID_GARA";
	public final static String T_ID_GARA = TABLE_NAME + "." + ID_GARA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_GARA_field;

	public final static String NOME_FILE = "NOME_FILE";
	public final static String T_NOME_FILE = TABLE_NAME + "." + NOME_FILE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [255]
		// NULLABLE [NO]

	public String NOME_FILE_field;

	public final static String TIPO_DOC = "TIPO_DOC";
	public final static String T_TIPO_DOC = TABLE_NAME + "." + TIPO_DOC;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [5]
		// NULLABLE [NO]

	public String TIPO_DOC_field;

	public final static String NOTE = "NOTE";
	public final static String T_NOTE = TABLE_NAME + "." + NOTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [255]
		// NULLABLE [YES]

	public String NOTE_field;

	public final static String ESITO_CHECK = "ESITO_CHECK";
	public final static String T_ESITO_CHECK = TABLE_NAME + "." + ESITO_CHECK;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [255]
		// NULLABLE [YES]

	public String ESITO_CHECK_field;

	public final static String DATA_UPLOAD = "DATA_UPLOAD";
	public final static String T_DATA_UPLOAD = TABLE_NAME + "." + DATA_UPLOAD;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_UPLOAD_field;
	
	public final static String ID_PUBBLICAZIONE = "ID_PUBBLICAZIONE";
	public final static String T_ID_PUBBLICAZIONE = TABLE_NAME + "." + ID_PUBBLICAZIONE;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_PUBBLICAZIONE_field;

	public final static String DATA_INIZIO_PUBB = "DATA_INIZIO_PUBB";
	public final static String T_DATA_INIZIO_PUBB = TABLE_NAME + "." + DATA_INIZIO_PUBB;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]
	
	//MEV 34186 3.04.8
	public final static String PATH_FILE = "PATH_FILE"; 
	public final static String T_PATH_FILE = TABLE_NAME + "." + PATH_FILE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [255]
		// NULLABLE [NO]
	

	public java.sql.Date DATA_INIZIO_PUBB_field;


}
