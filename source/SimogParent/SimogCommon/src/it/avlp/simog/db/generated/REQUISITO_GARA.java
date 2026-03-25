package it.avlp.simog.db.generated; 
	/*
	*	FILE REQUISITO_GARA created mer 05/12/2012 13:04:34:427
	*/

public class REQUISITO_GARA {

	public final static String TABLE_NAME = "REQUISITO_GARA";


	public final static String COD_REQUISITO_GARA = "COD_REQUISITO_GARA";
	public final static String T_COD_REQUISITO_GARA = TABLE_NAME + "." + COD_REQUISITO_GARA;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_REQUISITO_GARA_field;

	public final static String DESCRIZIONE = "DESCRIZIONE";
	public final static String T_DESCRIZIONE = TABLE_NAME + "." + DESCRIZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [80]
		// NULLABLE [YES]

	public String DESCRIZIONE_field;

	public final static String VALORE = "VALORE";
	public final static String T_VALORE = TABLE_NAME + "." + VALORE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String VALORE_field;

	public final static String DATA_INIZIO_VALIDITA = "DATA_INIZIO_VALIDITA";
	public final static String T_DATA_INIZIO_VALIDITA = TABLE_NAME + "." + DATA_INIZIO_VALIDITA;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_INIZIO_VALIDITA_field;

	public final static String DATA_REVOCA = "DATA_REVOCA";
	public final static String T_DATA_REVOCA = TABLE_NAME + "." + DATA_REVOCA;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_REVOCA_field;

	public final static String FLG_CONDIZIONE_ESCLUSIONE = "FLG_CONDIZIONE_ESCLUSIONE";
	public final static String T_FLG_CONDIZIONE_ESCLUSIONE = TABLE_NAME + "." + FLG_CONDIZIONE_ESCLUSIONE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLG_CONDIZIONE_ESCLUSIONE_field;

	public final static String FLG_COMPROVA_IN_OFFERTA = "FLG_COMPROVA_IN_OFFERTA";
	public final static String T_FLG_COMPROVA_IN_OFFERTA = TABLE_NAME + "." + FLG_COMPROVA_IN_OFFERTA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLG_COMPROVA_IN_OFFERTA_field;

	public final static String FLG_AVVALIMENTO = "FLG_AVVALIMENTO";
	public final static String T_FLG_AVVALIMENTO = TABLE_NAME + "." + FLG_AVVALIMENTO;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLG_AVVALIMENTO_field;

	public final static String FLG_BANDO_TIPO = "FLG_BANDO_TIPO";
	public final static String T_FLG_BANDO_TIPO = TABLE_NAME + "." + FLG_BANDO_TIPO;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLG_BANDO_TIPO_field;

	public final static String FLG_RISERVATEZZA = "FLG_RISERVATEZZA";
	public final static String T_FLG_RISERVATEZZA = TABLE_NAME + "." + FLG_RISERVATEZZA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLG_RISERVATEZZA_field;

	public final static String COD_LOTTO = "COD_LOTTO";
	public final static String T_COD_LOTTO = TABLE_NAME + "." + COD_LOTTO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long COD_LOTTO_field;

	public final static String COD_GARA = "COD_GARA";
	public final static String T_COD_GARA = TABLE_NAME + "." + COD_GARA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long COD_GARA_field;

	public final static String COD_DETT_REQUISITO = "COD_DETT_REQUISITO";
	public final static String T_COD_DETT_REQUISITO = TABLE_NAME + "." + COD_DETT_REQUISITO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long COD_DETT_REQUISITO_field;

	public final static String MASTER = "MASTER";
	public final static String T_MASTER = TABLE_NAME + "." + MASTER;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String MASTER_field;
}
