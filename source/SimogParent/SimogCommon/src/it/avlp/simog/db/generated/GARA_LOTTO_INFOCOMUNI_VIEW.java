package it.avlp.simog.db.generated; 
	/*
	*	FILE GARA_LOTTO_INFOCOMUNI_VIEW created lun 17/08/2009 13:45:36:687
	*/

public class GARA_LOTTO_INFOCOMUNI_VIEW {

	public final static String TABLE_NAME = "GARA_LOTTO_INFOCOMUNI_VIEW";


	public final static String ID_LOTTO = "ID_LOTTO";
	public final static String T_ID_LOTTO = TABLE_NAME + "." + ID_LOTTO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long Id_Lotto_field;

	public final static String CIG_CICLE = "CIG_CICLE";
	public final static String T_CIG_CICLE = TABLE_NAME + "." + CIG_CICLE;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int CIG_cicle_field;

	public final static String CIG = "CIG";
	public final static String T_CIG = TABLE_NAME + "." + CIG;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [7]
		// NULLABLE [YES]

	public String CIG_field;

	public final static String CIG_KKK = "CIG_KKK";
	public final static String T_CIG_KKK = TABLE_NAME + "." + CIG_KKK;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [3]
		// NULLABLE [YES]

	public String CIG_kkk_field;

	public final static String OGGETTO = "OGGETTO";
	public final static String T_OGGETTO = TABLE_NAME + "." + OGGETTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1024]
		// NULLABLE [YES]

	public String Oggetto_field;

	public final static String SOMMA_URGENZA = "SOMMA_URGENZA";
	public final static String T_SOMMA_URGENZA = TABLE_NAME + "." + SOMMA_URGENZA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char Somma_Urgenza_field;

	public final static String IMPORTO_LOTTO = "IMPORTO_LOTTO";
	public final static String T_IMPORTO_LOTTO = TABLE_NAME + "." + IMPORTO_LOTTO;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public java.math.BigDecimal Importo_Lotto_field;

	public final static String IMPORTO_SA = "IMPORTO_SA";
	public final static String T_IMPORTO_SA = TABLE_NAME + "." + IMPORTO_SA;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public java.math.BigDecimal Importo_SA_field;

	public final static String IMPORTO_IMPRESA = "IMPORTO_IMPRESA";
	public final static String T_IMPORTO_IMPRESA = TABLE_NAME + "." + IMPORTO_IMPRESA;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public java.math.BigDecimal Importo_Impresa_field;

	public final static String DATA_PUBBLICAZIONE = "DATA_PUBBLICAZIONE";
	public final static String T_DATA_PUBBLICAZIONE = TABLE_NAME + "." + DATA_PUBBLICAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String Data_Pubblicazione_field;

	public final static String DATA_COMUNICAZIONE = "DATA_COMUNICAZIONE";
	public final static String T_DATA_COMUNICAZIONE = TABLE_NAME + "." + DATA_COMUNICAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String Data_Comunicazione_field;

	public final static String ID_GARA = "ID_GARA";
	public final static String T_ID_GARA = TABLE_NAME + "." + ID_GARA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long Id_Gara_field;

	public final static String ID_TIPOLOGIA = "ID_TIPOLOGIA";
	public final static String T_ID_TIPOLOGIA = TABLE_NAME + "." + ID_TIPOLOGIA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [YES]

	public String Id_Tipologia_field;

	public final static String ID_CPV = "ID_CPV";
	public final static String T_ID_CPV = TABLE_NAME + "." + ID_CPV;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [YES]

	public String Id_CPV_field;

	public final static String ID_SCELTA_CONTRAENTE = "ID_SCELTA_CONTRAENTE";
	public final static String T_ID_SCELTA_CONTRAENTE = TABLE_NAME + "." + ID_SCELTA_CONTRAENTE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long Id_Scelta_Contraente_field;

	public final static String ID_CATEGORIA_PREVALENTE = "ID_CATEGORIA_PREVALENTE";
	public final static String T_ID_CATEGORIA_PREVALENTE = TABLE_NAME + "." + ID_CATEGORIA_PREVALENTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [YES]

	public String Id_Categoria_prevalente_field;

	public final static String DATA_INIB_PAGAMENTO = "DATA_INIB_PAGAMENTO";
	public final static String T_DATA_INIB_PAGAMENTO = TABLE_NAME + "." + DATA_INIB_PAGAMENTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_INIB_PAGAMENTO_field;

	public final static String DATA_SCADENZA_PAGAMENTI = "DATA_SCADENZA_PAGAMENTI";
	public final static String T_DATA_SCADENZA_PAGAMENTI = TABLE_NAME + "." + DATA_SCADENZA_PAGAMENTI;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_SCADENZA_PAGAMENTI_field;

	public final static String DATA_CANCELLAZIONE_LOTTO = "DATA_CANCELLAZIONE_LOTTO";
	public final static String T_DATA_CANCELLAZIONE_LOTTO = TABLE_NAME + "." + DATA_CANCELLAZIONE_LOTTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_CANCELLAZIONE_LOTTO_field;

	public final static String ID_INFO = "ID_INFO";
	public final static String T_ID_INFO = TABLE_NAME + "." + ID_INFO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_INFO_field;

	public final static String DATA_INIZIO_INFO = "DATA_INIZIO_INFO";
	public final static String T_DATA_INIZIO_INFO = TABLE_NAME + "." + DATA_INIZIO_INFO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_INIZIO_INFO_field;

	public final static String DATA_FINE_INFO = "DATA_FINE_INFO";
	public final static String T_DATA_FINE_INFO = TABLE_NAME + "." + DATA_FINE_INFO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_INFO_field;

	public final static String STATO_INFO = "STATO_INFO";
	public final static String T_STATO_INFO = TABLE_NAME + "." + STATO_INFO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long STATO_INFO_field;

	public final static String ID_PUBBLICAZIONE = "ID_PUBBLICAZIONE";
	public final static String T_ID_PUBBLICAZIONE = TABLE_NAME + "." + ID_PUBBLICAZIONE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_PUBBLICAZIONE_field;

	public final static String DATA_INIZIO_PUBB = "DATA_INIZIO_PUBB";
	public final static String T_DATA_INIZIO_PUBB = TABLE_NAME + "." + DATA_INIZIO_PUBB;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_INIZIO_PUBB_field;

	public final static String ID_CATEG_SA = "ID_CATEG_SA";
	public final static String T_ID_CATEG_SA = TABLE_NAME + "." + ID_CATEG_SA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [YES]

	public String ID_CATEG_SA_field;

	public final static String CF_AMM_AGENTE = "CF_AMM_AGENTE";
	public final static String T_CF_AMM_AGENTE = TABLE_NAME + "." + CF_AMM_AGENTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CF_AMM_AGENTE_field;

	public final static String DEN_AMM_AGENTE = "DEN_AMM_AGENTE";
	public final static String T_DEN_AMM_AGENTE = TABLE_NAME + "." + DEN_AMM_AGENTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [YES]

	public String DEN_AMM_AGENTE_field;

	public final static String FLAG_ENTE_SPECIALE = "FLAG_ENTE_SPECIALE";
	public final static String T_FLAG_ENTE_SPECIALE = TABLE_NAME + "." + FLAG_ENTE_SPECIALE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String FLAG_ENTE_SPECIALE_field;

	public final static String TIPO_CONTRATTO = "TIPO_CONTRATTO";
	public final static String T_TIPO_CONTRATTO = TABLE_NAME + "." + TIPO_CONTRATTO;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char TIPO_CONTRATTO_field;

	public final static String CF_AMM = "CF_AMM";
	public final static String T_CF_AMM = TABLE_NAME + "." + CF_AMM;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CF_AMM_field;

	public final static String DEN_AMM = "DEN_AMM";
	public final static String T_DEN_AMM = TABLE_NAME + "." + DEN_AMM;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [YES]

	public String DEN_AMM_field;

	public final static String CF_SA = "CF_SA";
	public final static String T_CF_SA = TABLE_NAME + "." + CF_SA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CF_SA_field;

	public final static String DEN_SA = "DEN_SA";
	public final static String T_DEN_SA = TABLE_NAME + "." + DEN_SA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [YES]

	public String DEN_SA_field;

	public final static String CODICE_CC = "CODICE_CC";
	public final static String T_CODICE_CC = TABLE_NAME + "." + CODICE_CC;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String CODICE_CC_field;

	public final static String DENOM_CC = "DENOM_CC";
	public final static String T_DENOM_CC = TABLE_NAME + "." + DENOM_CC;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [YES]

	public String DENOM_CC_field;

	public final static String ID_TIPOLOGIA_SA = "ID_TIPOLOGIA_SA";
	public final static String T_ID_TIPOLOGIA_SA = TABLE_NAME + "." + ID_TIPOLOGIA_SA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_TIPOLOGIA_SA_field;

	public final static String FLAG_SA_AGENTE = "FLAG_SA_AGENTE";
	public final static String T_FLAG_SA_AGENTE = TABLE_NAME + "." + FLAG_SA_AGENTE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_SA_AGENTE_field;

	public final static String CF_RUP = "CF_RUP";
	public final static String T_CF_RUP = TABLE_NAME + "." + CF_RUP;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [16]
		// NULLABLE [YES]

	public String CF_RUP_field;

	public final static String PROVV_PRESA_CARICO = "PROVV_PRESA_CARICO";
	public final static String T_PROVV_PRESA_CARICO = TABLE_NAME + "." + PROVV_PRESA_CARICO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [YES]

	public String PROVV_PRESA_CARICO_field;

	public final static String OGGETTO_GARA = "OGGETTO_GARA";
	public final static String T_OGGETTO_GARA = TABLE_NAME + "." + OGGETTO_GARA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1024]
		// NULLABLE [NO]

	public String OGGETTO_GARA_field;

	public final static String DATA_CREAZIONE = "DATA_CREAZIONE";
	public final static String T_DATA_CREAZIONE = TABLE_NAME + "." + DATA_CREAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [NO]

	public String Data_creazione_field;

	public final static String CF_UTENTE = "CF_UTENTE";
	public final static String T_CF_UTENTE = TABLE_NAME + "." + CF_UTENTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [NO]

	public String CF_UTENTE_field;

	public final static String ID_STAZIONE_APPALTANTE = "ID_STAZIONE_APPALTANTE";
	public final static String T_ID_STAZIONE_APPALTANTE = TABLE_NAME + "." + ID_STAZIONE_APPALTANTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [40]
		// NULLABLE [NO]

	public String ID_STAZIONE_APPALTANTE_field;

	public final static String DENOM_STAZIONE_APPALTANTE = "DENOM_STAZIONE_APPALTANTE";
	public final static String T_DENOM_STAZIONE_APPALTANTE = TABLE_NAME + "." + DENOM_STAZIONE_APPALTANTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [NO]

	public String DENOM_STAZIONE_APPALTANTE_field;

	public final static String CF_AMMINISTRAZIONE = "CF_AMMINISTRAZIONE";
	public final static String T_CF_AMMINISTRAZIONE = TABLE_NAME + "." + CF_AMMINISTRAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [NO]

	public String CF_AMMINISTRAZIONE_field;

	public final static String DENOM_AMMINISTRAZIONE = "DENOM_AMMINISTRAZIONE";
	public final static String T_DENOM_AMMINISTRAZIONE = TABLE_NAME + "." + DENOM_AMMINISTRAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [NO]

	public String DENOM_AMMINISTRAZIONE_field;
}
