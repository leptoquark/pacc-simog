package it.avlp.simog.db.generated; 
	/*
	*	FILE INDICE_DISPERSIONE created mar 11/05/2010 11:40:20:375
	*/

public class INDICE_DISPERSIONE {

	public final static String TABLE_NAME = "INDICE_DISPERSIONE";


	public final static String ANNO = "ANNO";
	public final static String T_ANNO = TABLE_NAME + "." + ANNO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [4]
		// NULLABLE [NO]

	public String ANNO_field;

	public final static String TIPO_SETTORE = "TIPO_SETTORE";
	public final static String T_TIPO_SETTORE = TABLE_NAME + "." + TIPO_SETTORE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [NO]

	public String TIPO_SETTORE_field;

	public final static String TIPO_CONTRATTO = "TIPO_CONTRATTO";
	public final static String T_TIPO_CONTRATTO = TABLE_NAME + "." + TIPO_CONTRATTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [NO]

	public String TIPO_CONTRATTO_field;

	public final static String INDICE = "INDICE";
	public final static String T_INDICE = TABLE_NAME + "." + INDICE;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [NO]

	public java.math.BigDecimal INDICE_field;

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
