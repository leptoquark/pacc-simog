package it.avlp.simog.db.generated; 
	/*
	*	FILE SIMOG_PUBBLICAZIONE_BANDO created lun 22/11/2010 11:58:52:125
	*/

public class SIMOG_PUBBLICAZIONE_BANDO {

	public final static String TABLE_NAME = "SIMOG_PUBBLICAZIONE_BANDO";


	public final static String ID_GARA = "ID_GARA";
	public final static String T_ID_GARA = TABLE_NAME + "." + ID_GARA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_GARA_field;

	public final static String OGGETTO_GARA = "OGGETTO_GARA";
	public final static String T_OGGETTO_GARA = TABLE_NAME + "." + OGGETTO_GARA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1024]
		// NULLABLE [YES]

	public String OGGETTO_GARA_field;

	public final static String DATA_CREAZIONE_GARA = "DATA_CREAZIONE_GARA";
	public final static String T_DATA_CREAZIONE_GARA = TABLE_NAME + "." + DATA_CREAZIONE_GARA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_CREAZIONE_GARA_field;

	public final static String CF_UTENTE = "CF_UTENTE";
	public final static String T_CF_UTENTE = TABLE_NAME + "." + CF_UTENTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CF_UTENTE_field;

	public final static String ID_STAZIONE_APPALTANTE = "ID_STAZIONE_APPALTANTE";
	public final static String T_ID_STAZIONE_APPALTANTE = TABLE_NAME + "." + ID_STAZIONE_APPALTANTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [40]
		// NULLABLE [YES]

	public String ID_STAZIONE_APPALTANTE_field;

	public final static String DENOM_STAZIONE_APPALTANTE = "DENOM_STAZIONE_APPALTANTE";
	public final static String T_DENOM_STAZIONE_APPALTANTE = TABLE_NAME + "." + DENOM_STAZIONE_APPALTANTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [YES]

	public String DENOM_STAZIONE_APPALTANTE_field;

	public final static String CF_AMMINISTRAZIONE = "CF_AMMINISTRAZIONE";
	public final static String T_CF_AMMINISTRAZIONE = TABLE_NAME + "." + CF_AMMINISTRAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [20]
		// NULLABLE [YES]

	public String CF_AMMINISTRAZIONE_field;

	public final static String DENOM_AMMINISTRAZIONE = "DENOM_AMMINISTRAZIONE";
	public final static String T_DENOM_AMMINISTRAZIONE = TABLE_NAME + "." + DENOM_AMMINISTRAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [YES]

	public String DENOM_AMMINISTRAZIONE_field;

	public final static String ID_OSSERVATORIO = "ID_OSSERVATORIO";
	public final static String T_ID_OSSERVATORIO = TABLE_NAME + "." + ID_OSSERVATORIO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [3]
		// NULLABLE [YES]

	public String ID_OSSERVATORIO_field;

	public final static String ID_STATO_GARA = "ID_STATO_GARA";
	public final static String T_ID_STATO_GARA = TABLE_NAME + "." + ID_STATO_GARA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char ID_STATO_GARA_field;

	public final static String DATA_COMUN = "DATA_COMUN";
	public final static String T_DATA_COMUN = TABLE_NAME + "." + DATA_COMUN;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_COMUN_field;

	public final static String IMPORTO_GARA = "IMPORTO_GARA";
	public final static String T_IMPORTO_GARA = TABLE_NAME + "." + IMPORTO_GARA;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_GARA_field;

	public final static String IMPORTO_SA_GARA = "IMPORTO_SA_GARA";
	public final static String T_IMPORTO_SA_GARA = TABLE_NAME + "." + IMPORTO_SA_GARA;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_SA_GARA_field;

	public final static String DATA_INIB_PAGAM = "DATA_INIB_PAGAM";
	public final static String T_DATA_INIB_PAGAM = TABLE_NAME + "." + DATA_INIB_PAGAM;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_INIB_PAGAM_field;

	public final static String DATA_TERMINE_PAGAMENTO = "DATA_TERMINE_PAGAMENTO";
	public final static String T_DATA_TERMINE_PAGAMENTO = TABLE_NAME + "." + DATA_TERMINE_PAGAMENTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_TERMINE_PAGAMENTO_field;

	public final static String DATA_CANCELLAZIONE_GARA = "DATA_CANCELLAZIONE_GARA";
	public final static String T_DATA_CANCELLAZIONE_GARA = TABLE_NAME + "." + DATA_CANCELLAZIONE_GARA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_CANCELLAZIONE_GARA_field;

	public final static String DATA_CONFERMA_GARA = "DATA_CONFERMA_GARA";
	public final static String T_DATA_CONFERMA_GARA = TABLE_NAME + "." + DATA_CONFERMA_GARA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_CONFERMA_GARA_field;

	public final static String TIPO_SCHEDA_GARA = "TIPO_SCHEDA_GARA";
	public final static String T_TIPO_SCHEDA_GARA = TABLE_NAME + "." + TIPO_SCHEDA_GARA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String TIPO_SCHEDA_GARA_field;

	public final static String ID_MODO_GARA = "ID_MODO_GARA";
	public final static String T_ID_MODO_GARA = TABLE_NAME + "." + ID_MODO_GARA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_MODO_GARA_field;

	public final static String ID_MODO_REAL = "ID_MODO_REAL";
	public final static String T_ID_MODO_REAL = TABLE_NAME + "." + ID_MODO_REAL;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_MODO_REAL_field;

	public final static String ID_MOTIVAZIONE_CANC = "ID_MOTIVAZIONE_CANC";
	public final static String T_ID_MOTIVAZIONE_CANC = TABLE_NAME + "." + ID_MOTIVAZIONE_CANC;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_MOTIVAZIONE_CANC_field;

	public final static String NOTE_CANC_GARA = "NOTE_CANC_GARA";
	public final static String T_NOTE_CANC_GARA = TABLE_NAME + "." + NOTE_CANC_GARA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1000]
		// NULLABLE [YES]

	public String NOTE_CANC_GARA_field;

	public final static String CIG_ACC_QUADRO = "CIG_ACC_QUADRO";
	public final static String T_CIG_ACC_QUADRO = TABLE_NAME + "." + CIG_ACC_QUADRO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public String CIG_ACC_QUADRO_field;

	public final static String DATA_PERFEZIONAMENTO_BANDO = "DATA_PERFEZIONAMENTO_BANDO";
	public final static String T_DATA_PERFEZIONAMENTO_BANDO = TABLE_NAME + "." + DATA_PERFEZIONAMENTO_BANDO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_PERFEZIONAMENTO_BANDO_field;

	public final static String ID_LOTTO = "ID_LOTTO";
	public final static String T_ID_LOTTO = TABLE_NAME + "." + ID_LOTTO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_LOTTO_field;

	public final static String CIG_CICLE = "CIG_CICLE";
	public final static String T_CIG_CICLE = TABLE_NAME + "." + CIG_CICLE;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int CIG_CICLE_field;

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

	public String CIG_KKK_field;

	public final static String OGGETTO_LOTTO = "OGGETTO_LOTTO";
	public final static String T_OGGETTO_LOTTO = TABLE_NAME + "." + OGGETTO_LOTTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1024]
		// NULLABLE [YES]

	public String OGGETTO_LOTTO_field;

	public final static String SOMMA_URGENZA = "SOMMA_URGENZA";
	public final static String T_SOMMA_URGENZA = TABLE_NAME + "." + SOMMA_URGENZA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char SOMMA_URGENZA_field;

	public final static String IMPORTO_LOTTO = "IMPORTO_LOTTO";
	public final static String T_IMPORTO_LOTTO = TABLE_NAME + "." + IMPORTO_LOTTO;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_LOTTO_field;

	public final static String IMPORTO_SA = "IMPORTO_SA";
	public final static String T_IMPORTO_SA = TABLE_NAME + "." + IMPORTO_SA;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_SA_field;

	public final static String IMPORTO_IMPRESA = "IMPORTO_IMPRESA";
	public final static String T_IMPORTO_IMPRESA = TABLE_NAME + "." + IMPORTO_IMPRESA;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_IMPRESA_field;

	public final static String DATA_PUBBLICAZIONE = "DATA_PUBBLICAZIONE";
	public final static String T_DATA_PUBBLICAZIONE = TABLE_NAME + "." + DATA_PUBBLICAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_PUBBLICAZIONE_field;

	public final static String DATA_COMUNICAZIONE = "DATA_COMUNICAZIONE";
	public final static String T_DATA_COMUNICAZIONE = TABLE_NAME + "." + DATA_COMUNICAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_COMUNICAZIONE_field;

	public final static String ID_TIPOLOGIA = "ID_TIPOLOGIA";
	public final static String T_ID_TIPOLOGIA = TABLE_NAME + "." + ID_TIPOLOGIA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [YES]

	public String ID_TIPOLOGIA_field;

	public final static String ID_CPV = "ID_CPV";
	public final static String T_ID_CPV = TABLE_NAME + "." + ID_CPV;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [YES]

	public String ID_CPV_field;

	public final static String ID_SCELTA_CONTRAENTE = "ID_SCELTA_CONTRAENTE";
	public final static String T_ID_SCELTA_CONTRAENTE = TABLE_NAME + "." + ID_SCELTA_CONTRAENTE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_SCELTA_CONTRAENTE_field;

	public final static String ID_CATEGORIA_PREVALENTE = "ID_CATEGORIA_PREVALENTE";
	public final static String T_ID_CATEGORIA_PREVALENTE = TABLE_NAME + "." + ID_CATEGORIA_PREVALENTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [12]
		// NULLABLE [YES]

	public String ID_CATEGORIA_PREVALENTE_field;

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

	public final static String ID_MOTIVAZIONE_CANC_LOTTO = "ID_MOTIVAZIONE_CANC_LOTTO";
	public final static String T_ID_MOTIVAZIONE_CANC_LOTTO = TABLE_NAME + "." + ID_MOTIVAZIONE_CANC_LOTTO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_MOTIVAZIONE_CANC_LOTTO_field;

	public final static String NOTE_CANC_LOTTO = "NOTE_CANC_LOTTO";
	public final static String T_NOTE_CANC_LOTTO = TABLE_NAME + "." + NOTE_CANC_LOTTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1000]
		// NULLABLE [YES]

	public String NOTE_CANC_LOTTO_field;

	public final static String TIPO_CONTRATTO_LOTTO = "TIPO_CONTRATTO_LOTTO";
	public final static String T_TIPO_CONTRATTO_LOTTO = TABLE_NAME + "." + TIPO_CONTRATTO_LOTTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String TIPO_CONTRATTO_LOTTO_field;

	public final static String FLAG_ESCLUSO = "FLAG_ESCLUSO";
	public final static String T_FLAG_ESCLUSO = TABLE_NAME + "." + FLAG_ESCLUSO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String FLAG_ESCLUSO_field;

	public final static String ID_ESCLUSIONE = "ID_ESCLUSIONE";
	public final static String T_ID_ESCLUSIONE = TABLE_NAME + "." + ID_ESCLUSIONE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_ESCLUSIONE_field;

	public final static String DATA_CREAZIONE_LOTTO = "DATA_CREAZIONE_LOTTO";
	public final static String T_DATA_CREAZIONE_LOTTO = TABLE_NAME + "." + DATA_CREAZIONE_LOTTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_CREAZIONE_LOTTO_field;

	public final static String LUOGO_NUTS = "LUOGO_NUTS";
	public final static String T_LUOGO_NUTS = TABLE_NAME + "." + LUOGO_NUTS;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public String LUOGO_NUTS_field;

	public final static String LUOGO_ISTAT = "LUOGO_ISTAT";
	public final static String T_LUOGO_ISTAT = TABLE_NAME + "." + LUOGO_ISTAT;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public String LUOGO_ISTAT_field;

	public final static String IMPORTO_ATTUAZIONE_SICUREZZA = "IMPORTO_ATTUAZIONE_SICUREZZA";
	public final static String T_IMPORTO_ATTUAZIONE_SICUREZZA = TABLE_NAME + "." + IMPORTO_ATTUAZIONE_SICUREZZA;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_ATTUAZIONE_SICUREZZA_field;

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
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_PUBB_field;

	public final static String DATA_FINE_PUBB = "DATA_FINE_PUBB";
	public final static String T_DATA_FINE_PUBB = TABLE_NAME + "." + DATA_FINE_PUBB;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_PUBB_field;

	public final static String DATA_ALBO = "DATA_ALBO";
	public final static String T_DATA_ALBO = TABLE_NAME + "." + DATA_ALBO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_ALBO_field;

	public final static String DATA_GUCE = "DATA_GUCE";
	public final static String T_DATA_GUCE = TABLE_NAME + "." + DATA_GUCE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_GUCE_field;

	public final static String DATA_GURI = "DATA_GURI";
	public final static String T_DATA_GURI = TABLE_NAME + "." + DATA_GURI;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_GURI_field;

	public final static String QUOTIDIANI_NAZ = "QUOTIDIANI_NAZ";
	public final static String T_QUOTIDIANI_NAZ = TABLE_NAME + "." + QUOTIDIANI_NAZ;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int QUOTIDIANI_NAZ_field;

	public final static String QUOTIDIANI_REG = "QUOTIDIANI_REG";
	public final static String T_QUOTIDIANI_REG = TABLE_NAME + "." + QUOTIDIANI_REG;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int QUOTIDIANI_REG_field;

	public final static String ID_STATO_PUBB = "ID_STATO_PUBB";
	public final static String T_ID_STATO_PUBB = TABLE_NAME + "." + ID_STATO_PUBB;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STATO_PUBB_field;

	public final static String PROFILO_COMMITTENTE = "PROFILO_COMMITTENTE";
	public final static String T_PROFILO_COMMITTENTE = TABLE_NAME + "." + PROFILO_COMMITTENTE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char PROFILO_COMMITTENTE_field;

	public final static String SITO_MINISTERO_INF_TRASP = "SITO_MINISTERO_INF_TRASP";
	public final static String T_SITO_MINISTERO_INF_TRASP = TABLE_NAME + "." + SITO_MINISTERO_INF_TRASP;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char SITO_MINISTERO_INF_TRASP_field;

	public final static String SITO_OSSERVATORIO_CP = "SITO_OSSERVATORIO_CP";
	public final static String T_SITO_OSSERVATORIO_CP = TABLE_NAME + "." + SITO_OSSERVATORIO_CP;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char SITO_OSSERVATORIO_CP_field;

	public final static String DATA_BORE = "DATA_BORE";
	public final static String T_DATA_BORE = TABLE_NAME + "." + DATA_BORE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_BORE_field;

	public final static String PERIODICI = "PERIODICI";
	public final static String T_PERIODICI = TABLE_NAME + "." + PERIODICI;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int PERIODICI_field;

	public final static String NUMERO_GURI = "NUMERO_GURI";
	public final static String T_NUMERO_GURI = TABLE_NAME + "." + NUMERO_GURI;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String NUMERO_GURI_field;

	public final static String NUMERO_GUCE = "NUMERO_GUCE";
	public final static String T_NUMERO_GUCE = TABLE_NAME + "." + NUMERO_GUCE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String NUMERO_GUCE_field;

	public final static String NUMERO_BORE = "NUMERO_BORE";
	public final static String T_NUMERO_BORE = TABLE_NAME + "." + NUMERO_BORE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String NUMERO_BORE_field;

	public final static String LINK_SITO_COMMITTENTE = "LINK_SITO_COMMITTENTE";
	public final static String T_LINK_SITO_COMMITTENTE = TABLE_NAME + "." + LINK_SITO_COMMITTENTE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [250]
		// NULLABLE [YES]

	public String LINK_SITO_COMMITTENTE_field;

	public final static String TIPO_OPERAZIONE = "TIPO_OPERAZIONE";
	public final static String T_TIPO_OPERAZIONE = TABLE_NAME + "." + TIPO_OPERAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String TIPO_OPERAZIONE_field;

	public final static String FLAG_BENICULT = "FLAG_BENICULT";
	public final static String T_FLAG_BENICULT = TABLE_NAME + "." + FLAG_BENICULT;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String FLAG_BENICULT_field;
}
