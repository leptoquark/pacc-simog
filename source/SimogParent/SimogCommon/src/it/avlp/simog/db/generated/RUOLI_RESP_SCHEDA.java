package it.avlp.simog.db.generated; 
	/*
	*	FILE RUOLI_RESP_SCHEDA created lun 17/08/2009 13:45:36:687
	*/

public class RUOLI_RESP_SCHEDA {

	public final static String TABLE_NAME = "RUOLI_RESP_SCHEDA";


	public final static String ID_SCHEDA = "ID_SCHEDA";
	public final static String T_ID_SCHEDA = TABLE_NAME + "." + ID_SCHEDA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [2]
		// NULLABLE [NO]

	public char ID_SCHEDA_field;

	public final static String ID_CONTRATTO = "ID_CONTRATTO";
	public final static String T_ID_CONTRATTO = TABLE_NAME + "." + ID_CONTRATTO;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [NO]

	public char ID_CONTRATTO_field;

	public final static String ID_ENTE = "ID_ENTE";
	public final static String T_ID_ENTE = TABLE_NAME + "." + ID_ENTE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [NO]

	public char ID_ENTE_field;

	public final static String ID_RUOLO = "ID_RUOLO";
	public final static String T_ID_RUOLO = TABLE_NAME + "." + ID_RUOLO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [NO]

	public String Id_ruolo_field;

	public final static String DATA_FINE_VALIDITA = "DATA_FINE_VALIDITA";
	public final static String T_DATA_FINE_VALIDITA = TABLE_NAME + "." + DATA_FINE_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String Data_fine_validita_field;

	public final static String DATA_ULTIMA_MODIFICA = "DATA_ULTIMA_MODIFICA";
	public final static String T_DATA_ULTIMA_MODIFICA = TABLE_NAME + "." + DATA_ULTIMA_MODIFICA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String Data_ultima_modifica_field;
}
