package it.avlp.simog.db.generated; 
	/*
	*	FILE TIPOLOGIA_PROCEDURA created lun 17/08/2009 13:45:36:687
	*/

public class TIPOLOGIA_PROCEDURA {

	public final static String TABLE_NAME = "TIPOLOGIA_PROCEDURA";


	public final static String ID_TIPOLOGIA_PROCEDURA = "ID_TIPOLOGIA_PROCEDURA";
	public final static String T_ID_TIPOLOGIA_PROCEDURA = TABLE_NAME + "." + ID_TIPOLOGIA_PROCEDURA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_TIPOLOGIA_PROCEDURA_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [100]
		// NULLABLE [NO]

	public String DESCRIZIONE_field;

	public final static String DATA_FINE_VALIDITA = "DATA_FINE_VALIDITA";
	public final static String T_DATA_FINE_VALIDITA = TABLE_NAME + "." + DATA_FINE_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_FINE_VALIDITA_field;

	public final static String DATA_ULTIMA_MODIFICA = "DATA_ULTIMA_MODIFICA";
	public final static String T_DATA_ULTIMA_MODIFICA = TABLE_NAME + "." + DATA_ULTIMA_MODIFICA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_ULTIMA_MODIFICA_field;
}
