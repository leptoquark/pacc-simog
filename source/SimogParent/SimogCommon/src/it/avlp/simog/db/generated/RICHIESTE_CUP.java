package it.avlp.simog.db.generated; 
	/*
	*	FILE RICHIESTE_CUP created mar 08/04/2014 15:18:27:703
	*/

public class RICHIESTE_CUP {

	public final static String TABLE_NAME = "RICHIESTE_CUP";


	public final static String ID_RICHIESTA = "ID_RICHIESTA";
	public final static String T_ID_RICHIESTA = TABLE_NAME + "." + ID_RICHIESTA;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_RICHIESTA_field;

	public final static String DATA_RICHIESTA = "DATA_RICHIESTA";
	public final static String T_DATA_RICHIESTA = TABLE_NAME + "." + DATA_RICHIESTA;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_RICHIESTA_field;

	public final static String CUP = "CUP";
	public final static String T_CUP = TABLE_NAME + "." + CUP;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [15]
		// NULLABLE [NO]

	public String CUP_field;

	public final static String ULT_DATA_WS = "ULT_DATA_WS";
	public final static String T_ULT_DATA_WS = TABLE_NAME + "." + ULT_DATA_WS;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date ULT_DATA_WS_field;

	public final static String DATA_ESITO = "DATA_ESITO";
	public final static String T_DATA_ESITO = TABLE_NAME + "." + DATA_ESITO;
	
	public final static String COD_INVESTIMENTO_RGS = "COD_INVESTIMENTO_RGS";
	public final static String T_COD_INVESTIMENTO_RGS = TABLE_NAME + "." + COD_INVESTIMENTO_RGS;
	
	public final static String FLAG_PNRR_PNC_RGS = "FLAG_PNRR_PNC_RGS";
	public final static String T_FLAG_PNRR_PNC_RGS = TABLE_NAME + "." + FLAG_PNRR_PNC_RGS;
	
	
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_ESITO_field;

	public final static String ESITO_RICHIESTA = "ESITO_RICHIESTA";
	public final static String T_ESITO_RICHIESTA = TABLE_NAME + "." + ESITO_RICHIESTA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1024]
		// NULLABLE [YES]

	public String ESITO_RICHIESTA_field;

	public final static String VALIDO = "VALIDO";
	public final static String T_VALIDO = TABLE_NAME + "." + VALIDO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String VALIDO_field;

	public final static String DATA_RICONCIL = "DATA_RICONCIL";
	public final static String T_DATA_RICONCIL = TABLE_NAME + "." + DATA_RICONCIL;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_RICONCIL_field;

	public final static String UTE_RICONCIL = "UTE_RICONCIL";
	public final static String T_UTE_RICONCIL = TABLE_NAME + "." + UTE_RICONCIL;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [16]
		// NULLABLE [YES]

	public String UTE_RICONCIL_field;
	
	public final static String STATO = "STATO";
	public final static String TEMATICA = "TEMATICA";
}
