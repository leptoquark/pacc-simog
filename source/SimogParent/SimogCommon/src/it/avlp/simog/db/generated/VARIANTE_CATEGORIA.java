package it.avlp.simog.db.generated; 
	/*
	*	FILE VARIANTE_CATEGORIA created lun 17/08/2009 13:45:36:687
	*/

public class VARIANTE_CATEGORIA {

	public final static String TABLE_NAME = "VARIANTE_CATEGORIA";


	public final static String ID_CONTRATTO = "ID_CONTRATTO";
	public final static String T_ID_CONTRATTO = TABLE_NAME + "." + ID_CONTRATTO;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [NO]

	public char ID_CONTRATTO_field;

	public final static String ID_MOTIVO_VAR = "ID_MOTIVO_VAR";
	public final static String T_ID_MOTIVO_VAR = TABLE_NAME + "." + ID_MOTIVO_VAR;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long Id_motivo_var_field;

	//TICKET ALM #2847 - Varianti
	public final static String DATA_INIZIO_VALIDITA = "DATA_INIZIO_VALIDITA";
	public final static String T_DATA_INIZIO_VALIDITA = TABLE_NAME + "." + DATA_INIZIO_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String Data_inizio_validita_field;
	
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
