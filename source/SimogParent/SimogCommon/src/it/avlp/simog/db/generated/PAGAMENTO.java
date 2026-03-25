package it.avlp.simog.db.generated; 
	/*
	*	FILE PAGAMENTO created gio 14/12/2006 12:14:41:734
	*/

public interface PAGAMENTO {

	public final static String TABLE_NAME = "PAGAMENTO";

	public final static boolean IDENTITY = true;

	public final static String ID_PAGAMENTO = "ID_PAGAMENTO";
	public final static String T_ID_PAGAMENTO = "PAGAMENTO.ID_PAGAMENTO";
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long getId_pagamento();

	public final static String CIG_LOTTO = "CIG_LOTTO";
	public final static String T_CIG_LOTTO = "PAGAMENTO.CIG_LOTTO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [11]
		// NULLABLE [NO]

	public String getCIG_lotto();

	public final static String CODICE_TRANSAZIONE = "CODICE_TRANSAZIONE";
	public final static String T_CODICE_TRANSAZIONE = "PAGAMENTO.CODICE_TRANSAZIONE";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [16]
		// NULLABLE [NO]

	public String getCodice_transazione();

	public final static String IMPORTO_PAGATO = "IMPORTO_PAGATO";
	public final static String T_IMPORTO_PAGATO = "PAGAMENTO.IMPORTO_PAGATO";
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [NO]

	public java.math.BigDecimal getImporto_pagato();

	public final static String DATA_PAGAMENTO = "DATA_PAGAMENTO";
	public final static String T_DATA_PAGAMENTO = "PAGAMENTO.DATA_PAGAMENTO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [NO]

	public String getData_pagamento();

	public final static String DATA_CONTABILIZZAZIONE = "DATA_CONTABILIZZAZIONE";
	public final static String T_DATA_CONTABILIZZAZIONE = "PAGAMENTO.DATA_CONTABILIZZAZIONE";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [NO]

	public String getData_contabilizzazione();

	public final static String ID_SA_RIFERIMENTO = "ID_SA_RIFERIMENTO";
	public final static String T_ID_SA_RIFERIMENTO = "PAGAMENTO.ID_SA_RIFERIMENTO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [40]
		// NULLABLE [NO]

	public String getId_sa_riferimento();

	public final static String CANALE_PAGAMENTO = "CANALE_PAGAMENTO";
	public final static String T_CANALE_PAGAMENTO = "PAGAMENTO.CANALE_PAGAMENTO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [NO]

	public String getCanale_pagamento();

	public final static String STATO_PAGAMENTO = "STATO_PAGAMENTO";
	public final static String T_STATO_PAGAMENTO = "PAGAMENTO.STATO_PAGAMENTO";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [3]
		// NULLABLE [NO]

	public String getStato_pagamento();

	public final static String TIPO_UTENZA = "TIPO_UTENZA";
	public final static String T_TIPO_UTENZA = "PAGAMENTO.TIPO_UTENZA";
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [2]
		// NULLABLE [NO]

	public String getTipo_utenza();

	public final static String ID_LOTTO = "ID_LOTTO";
	public final static String T_ID_LOTTO = "PAGAMENTO.ID_LOTTO";
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long getId_lotto();

	public final static String ID_GARA = "ID_GARA";
	public final static String T_ID_GARA = "PAGAMENTO.ID_GARA";
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long getId_gara();
}