package it.avlp.simog.db.generated; 
	/*
	*	FILE REQUISITI created lun 17/08/2009 13:45:36:687
	*/

public class REQUISITI {

	public final static String TABLE_NAME = "REQUISITI";


	public final static String ID_REQUISITO = "ID_REQUISITO";
	public final static String T_ID_REQUISITO = TABLE_NAME + "." + ID_REQUISITO;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_REQUISITO_field;

	public final static String DATA_INIZIO_REQ = "DATA_INIZIO_REQ";
	public final static String T_DATA_INIZIO_REQ = TABLE_NAME + "." + DATA_INIZIO_REQ;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_REQ_field;

	public final static String DATA_FINE_REQ = "DATA_FINE_REQ";
	public final static String T_DATA_FINE_REQ = TABLE_NAME + "." + DATA_FINE_REQ;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_REQ_field;

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

	public final static String ID_CATEGORIA = "ID_CATEGORIA";
	public final static String T_ID_CATEGORIA = TABLE_NAME + "." + ID_CATEGORIA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [YES]

	public String ID_CATEGORIA_field;

	public final static String CLASSE_IMPORTO = "CLASSE_IMPORTO";
	public final static String T_CLASSE_IMPORTO = TABLE_NAME + "." + CLASSE_IMPORTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [5]
		// NULLABLE [YES]

	public String CLASSE_IMPORTO_field;

	public final static String PREVALENTE = "PREVALENTE";
	public final static String T_PREVALENTE = TABLE_NAME + "." + PREVALENTE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char PREVALENTE_field;

	public final static String SCORPORABILE = "SCORPORABILE";
	public final static String T_SCORPORABILE = TABLE_NAME + "." + SCORPORABILE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char SCORPORABILE_field;

	public final static String SUBAPPALTABILE = "SUBAPPALTABILE";
	public final static String T_SUBAPPALTABILE = TABLE_NAME + "." + SUBAPPALTABILE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char SUBAPPALTABILE_field;
}
