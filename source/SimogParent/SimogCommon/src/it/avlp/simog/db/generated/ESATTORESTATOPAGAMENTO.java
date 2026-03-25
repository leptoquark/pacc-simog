package it.avlp.simog.db.generated; 
	/*
	*	FILE ESATTORESTATOPAGAMENTO created lun 17/08/2009 13:45:36:687
	*/

public class ESATTORESTATOPAGAMENTO {

	public final static String TABLE_NAME = "ESATTORESTATOPAGAMENTO";


	public final static String ID_STATO_PAGAMENTO = "ID_STATO_PAGAMENTO";
	public final static String T_ID_STATO_PAGAMENTO = TABLE_NAME + "." + ID_STATO_PAGAMENTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [3]
		// NULLABLE [NO]

	public String Id_stato_pagamento_field;

	public final static String DESCRIZIONE_STATO_PAGAMENTO = "DESCRIZIONE_STATO_PAGAMENTO";
	public final static String T_DESCRIZIONE_STATO_PAGAMENTO = TABLE_NAME + "." + DESCRIZIONE_STATO_PAGAMENTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [NO]

	public String Descrizione_stato_pagamento_field;

	public final static String DATA_ULTIMA_MODIFICA = "DATA_ULTIMA_MODIFICA";
	public final static String T_DATA_ULTIMA_MODIFICA = TABLE_NAME + "." + DATA_ULTIMA_MODIFICA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [NO]

	public String Data_ultima_modifica_field;

	public final static String DATA_FINE_VALIDITA = "DATA_FINE_VALIDITA";
	public final static String T_DATA_FINE_VALIDITA = TABLE_NAME + "." + DATA_FINE_VALIDITA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public String Data_fine_validita_field;
}
