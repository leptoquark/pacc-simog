package it.avlp.simog.db.generated; 
	/*
	*	FILE AGGIUDICAZIONI created lun 17/08/2009 13:45:36:687
	*/

public class AGGIUDICAZIONI {

	public final static String TABLE_NAME = "AGGIUDICAZIONI";


	public final static String ID_AGGIUDICAZIONE = "ID_AGGIUDICAZIONE";
	public final static String T_ID_AGGIUDICAZIONE = TABLE_NAME + "." + ID_AGGIUDICAZIONE;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_AGGIUDICAZIONE_field;

	public final static String DATA_INIZIO_AGGIUDICAZIONE = "DATA_INIZIO_AGGIUDICAZIONE";
	public final static String T_DATA_INIZIO_AGGIUDICAZIONE = TABLE_NAME + "." + DATA_INIZIO_AGGIUDICAZIONE;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_AGGIUDICAZIONE_field;

	public final static String ID_INFO = "ID_INFO";
	public final static String T_ID_INFO = TABLE_NAME + "." + ID_INFO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_INFO_field;

	public final static String DATA_INIZIO_INFO = "DATA_INIZIO_INFO";
	public final static String T_DATA_INIZIO_INFO = TABLE_NAME + "." + DATA_INIZIO_INFO;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_INIZIO_INFO_field;

	public final static String NUM_IMPRESE_INVITATE = "NUM_IMPRESE_INVITATE";
	public final static String T_NUM_IMPRESE_INVITATE = TABLE_NAME + "." + NUM_IMPRESE_INVITATE;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int NUM_IMPRESE_INVITATE_field;

	public final static String NUM_IMPRESE_RICHIEDENTI = "NUM_IMPRESE_RICHIEDENTI";
	public final static String T_NUM_IMPRESE_RICHIEDENTI = TABLE_NAME + "." + NUM_IMPRESE_RICHIEDENTI;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int NUM_IMPRESE_RICHIEDENTI_field;

	public final static String NUM_IMPRESE_OFFERENTI = "NUM_IMPRESE_OFFERENTI";
	public final static String T_NUM_IMPRESE_OFFERENTI = TABLE_NAME + "." + NUM_IMPRESE_OFFERENTI;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int NUM_IMPRESE_OFFERENTI_field;

	public final static String NUM_OFFERTE_AMMESSE = "NUM_OFFERTE_AMMESSE";
	public final static String T_NUM_OFFERTE_AMMESSE = TABLE_NAME + "." + NUM_OFFERTE_AMMESSE;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int NUM_OFFERTE_AMMESSE_field;

	public final static String DATA_VERBALE_AGGIUDICAZIONE = "DATA_VERBALE_AGGIUDICAZIONE";
	public final static String T_DATA_VERBALE_AGGIUDICAZIONE = TABLE_NAME + "." + DATA_VERBALE_AGGIUDICAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_VERBALE_AGGIUDICAZIONE_field;

	public final static String DATA_SCADENZA_RICHIESTA_INVITO = "DATA_SCADENZA_RICHIESTA_INVITO";
	public final static String T_DATA_SCADENZA_RICHIESTA_INVITO = TABLE_NAME + "." + DATA_SCADENZA_RICHIESTA_INVITO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_SCADENZA_RICHIESTA_INVITO_field;

	public final static String DATA_SCADENZA_PRES_OFFERTA = "DATA_SCADENZA_PRES_OFFERTA";
	public final static String T_DATA_SCADENZA_PRES_OFFERTA = TABLE_NAME + "." + DATA_SCADENZA_PRES_OFFERTA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_SCADENZA_PRES_OFFERTA_field;

	public final static String ID_MODALITA_GARA = "ID_MODALITA_GARA";
	public final static String T_ID_MODALITA_GARA = TABLE_NAME + "." + ID_MODALITA_GARA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_MODALITA_GARA_field;

	public final static String DATA_FINE_AGGIUDICAZIONE = "DATA_FINE_AGGIUDICAZIONE";
	public final static String T_DATA_FINE_AGGIUDICAZIONE = TABLE_NAME + "." + DATA_FINE_AGGIUDICAZIONE;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [YES]

	public java.sql.Date DATA_FINE_AGGIUDICAZIONE_field;

	public final static String CUI = "CUI";
	public final static String T_CUI = TABLE_NAME + "." + CUI;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [15]
		// NULLABLE [NO]

	public String CUI_field;

	public final static String PROG_CUI = "PROG_CUI";
	public final static String T_PROG_CUI = TABLE_NAME + "." + PROG_CUI;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [NO]

	public int PROG_CUI_field;

	public final static String IMPORTO_AGGIUDICAZIONE = "IMPORTO_AGGIUDICAZIONE";
	public final static String T_IMPORTO_AGGIUDICAZIONE = TABLE_NAME + "." + IMPORTO_AGGIUDICAZIONE;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_AGGIUDICAZIONE_field;
	
	public final static String IMPORTO_COMPLESSIVO = "IMPORTO_COMPLESSIVO";
	public final static String T_IMPORTO_COMPLESSIVO = TABLE_NAME + "." + IMPORTO_COMPLESSIVO;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_COMPLESSIVO_field;


	public final static String ID_SCELTA_CONTRAENTE = "ID_SCELTA_CONTRAENTE";
	public final static String T_ID_SCELTA_CONTRAENTE = TABLE_NAME + "." + ID_SCELTA_CONTRAENTE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_SCELTA_CONTRAENTE_field;

	public final static String IMPORTO_LAVORI = "IMPORTO_LAVORI";
	public final static String T_IMPORTO_LAVORI = TABLE_NAME + "." + IMPORTO_LAVORI;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_LAVORI_field;

	public final static String IMPORTO_SERVIZI = "IMPORTO_SERVIZI";
	public final static String T_IMPORTO_SERVIZI = TABLE_NAME + "." + IMPORTO_SERVIZI;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_SERVIZI_field;

	public final static String IMPORTO_FORNITURE = "IMPORTO_FORNITURE";
	public final static String T_IMPORTO_FORNITURE = TABLE_NAME + "." + IMPORTO_FORNITURE;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_FORNITURE_field;

	public final static String IMPORTO_ATTUAZIONE_SICUREZZA = "IMPORTO_ATTUAZIONE_SICUREZZA";
	public final static String T_IMPORTO_ATTUAZIONE_SICUREZZA = TABLE_NAME + "." + IMPORTO_ATTUAZIONE_SICUREZZA;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_ATTUAZIONE_SICUREZZA_field;

	public final static String IMPORTO_DISPOSIZIONE = "IMPORTO_DISPOSIZIONE";
	public final static String T_IMPORTO_DISPOSIZIONE = TABLE_NAME + "." + IMPORTO_DISPOSIZIONE;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_DISPOSIZIONE_field;

	public final static String IMPORTO_PROGETTAZIONE = "IMPORTO_PROGETTAZIONE";
	public final static String T_IMPORTO_PROGETTAZIONE = TABLE_NAME + "." + IMPORTO_PROGETTAZIONE;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMPORTO_PROGETTAZIONE_field;

	public final static String SISTEMA_QUALIFICAZIONE = "SISTEMA_QUALIFICAZIONE";
	public final static String T_SISTEMA_QUALIFICAZIONE = TABLE_NAME + "." + SISTEMA_QUALIFICAZIONE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char SISTEMA_QUALIFICAZIONE_field;

	public final static String CRITERI_SELEZIONE_STABILITI_SA = "CRITERI_SELEZIONE_STABILITI_SA";
	public final static String T_CRITERI_SELEZIONE_STABILITI_SA = TABLE_NAME + "." + CRITERI_SELEZIONE_STABILITI_SA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char CRITERI_SELEZIONE_STABILITI_SA_field;

	public final static String ID_STATO = "ID_STATO";
	public final static String T_ID_STATO = TABLE_NAME + "." + ID_STATO;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_STATO_field;

	public final static String ID_TIPO_PRESTAZIONE = "ID_TIPO_PRESTAZIONE";
	public final static String T_ID_TIPO_PRESTAZIONE = TABLE_NAME + "." + ID_TIPO_PRESTAZIONE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_TIPO_PRESTAZIONE_field;

	public final static String CUP = "CUP";
	public final static String T_CUP = TABLE_NAME + "." + CUP;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [15]
		// NULLABLE [YES]

	public String CUP_field;

	public final static String FLAG_ACCORDO_QUADRO = "FLAG_ACCORDO_QUADRO";
	public final static String T_FLAG_ACCORDO_QUADRO = TABLE_NAME + "." + FLAG_ACCORDO_QUADRO;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_ACCORDO_QUADRO_field;

	public final static String LUOGO_ISTAT = "LUOGO_ISTAT";
	public final static String T_LUOGO_ISTAT = TABLE_NAME + "." + LUOGO_ISTAT;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [6]
		// NULLABLE [YES]

	public String LUOGO_ISTAT_field;

	public final static String LUOGO_NUTS = "LUOGO_NUTS";
	public final static String T_LUOGO_NUTS = TABLE_NAME + "." + LUOGO_NUTS;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public String LUOGO_NUTS_field;

	public final static String ASTA_ELETTRONICA = "ASTA_ELETTRONICA";
	public final static String T_ASTA_ELETTRONICA = TABLE_NAME + "." + ASTA_ELETTRONICA;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char ASTA_ELETTRONICA_field;

	public final static String PERC_RIBASSO_AGG = "PERC_RIBASSO_AGG";
	public final static String T_PERC_RIBASSO_AGG = TABLE_NAME + "." + PERC_RIBASSO_AGG;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal PERC_RIBASSO_AGG_field;

	public final static String PERC_OFF_AUMENTO = "PERC_OFF_AUMENTO";
	public final static String T_PERC_OFF_AUMENTO = TABLE_NAME + "." + PERC_OFF_AUMENTO;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal PERC_OFF_AUMENTO_field;

	public final static String DATA_INVITO = "DATA_INVITO";
	public final static String T_DATA_INVITO = TABLE_NAME + "." + DATA_INVITO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_INVITO_field;

	public final static String NUM_MANIF_INTERESSE = "NUM_MANIF_INTERESSE";
	public final static String T_NUM_MANIF_INTERESSE = TABLE_NAME + "." + NUM_MANIF_INTERESSE;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int NUM_MANIF_INTERESSE_field;

	public final static String DATA_MANIF_INTERESSE = "DATA_MANIF_INTERESSE";
	public final static String T_DATA_MANIF_INTERESSE = TABLE_NAME + "." + DATA_MANIF_INTERESSE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_MANIF_INTERESSE_field;

	public final static String FLAG_RICH_SUBAPPALTO = "FLAG_RICH_SUBAPPALTO";
	public final static String T_FLAG_RICH_SUBAPPALTO = TABLE_NAME + "." + FLAG_RICH_SUBAPPALTO;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char FLAG_RICH_SUBAPPALTO_field;

	public final static String NUM_OFFERTE_ESCLUSE = "NUM_OFFERTE_ESCLUSE";
	public final static String T_NUM_OFFERTE_ESCLUSE = TABLE_NAME + "." + NUM_OFFERTE_ESCLUSE;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int NUM_OFFERTE_ESCLUSE_field;

	public final static String OFFERTA_MASSIMO = "OFFERTA_MASSIMO";
	public final static String T_OFFERTA_MASSIMO = TABLE_NAME + "." + OFFERTA_MASSIMO;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal OFFERTA_MASSIMO_field;

	public final static String OFFERTA_MINIMA = "OFFERTA_MINIMA";
	public final static String T_OFFERTA_MINIMA = TABLE_NAME + "." + OFFERTA_MINIMA;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal OFFERTA_MINIMA_field;

	public final static String VAL_SOGLIA_ANOMALIA = "VAL_SOGLIA_ANOMALIA";
	public final static String T_VAL_SOGLIA_ANOMALIA = TABLE_NAME + "." + VAL_SOGLIA_ANOMALIA;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal VAL_SOGLIA_ANOMALIA_field;

	public final static String NUM_OFFERTE_FUORI_SOGLIA = "NUM_OFFERTE_FUORI_SOGLIA";
	public final static String T_NUM_OFFERTE_FUORI_SOGLIA = TABLE_NAME + "." + NUM_OFFERTE_FUORI_SOGLIA;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int NUM_OFFERTE_FUORI_SOGLIA_field;

	public final static String NUM_IMP_ESCL_INSUF_GIUST = "NUM_IMP_ESCL_INSUF_GIUST";
	public final static String T_NUM_IMP_ESCL_INSUF_GIUST = TABLE_NAME + "." + NUM_IMP_ESCL_INSUF_GIUST;
		// COLUMN TYPE [int]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public int NUM_IMP_ESCL_INSUF_GIUST_field;

	public final static String PROCEDURA_ACC = "PROCEDURA_ACC";
	public final static String T_PROCEDURA_ACC = TABLE_NAME + "." + PROCEDURA_ACC;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char PROCEDURA_ACC_field;

	public final static String PREINFORMAZIONE = "PREINFORMAZIONE";
	public final static String T_PREINFORMAZIONE = TABLE_NAME + "." + PREINFORMAZIONE;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char PREINFORMAZIONE_field;

	public final static String TERMINE_RIDOTTO = "TERMINE_RIDOTTO";
	public final static String T_TERMINE_RIDOTTO = TABLE_NAME + "." + TERMINE_RIDOTTO;
		// COLUMN TYPE [char]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public char TERMINE_RIDOTTO_field;

	public final static String ID_MODO_GARA = "ID_MODO_GARA";
	public final static String T_ID_MODO_GARA = TABLE_NAME + "." + ID_MODO_GARA;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [19]
		// NULLABLE [YES]

	public long ID_MODO_GARA_field;

	public final static String COD_STRUMENTO = "COD_STRUMENTO";
	public final static String T_COD_STRUMENTO = TABLE_NAME + "." + COD_STRUMENTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [10]
		// NULLABLE [YES]

	public String COD_STRUMENTO_field;

	public final static String IMP_NON_ASSOG = "IMP_NON_ASSOG";
	public final static String T_IMP_NON_ASSOG = TABLE_NAME + "." + IMP_NON_ASSOG;
		// COLUMN TYPE [decimal]
		// COLUMN SIZE [18]
		// NULLABLE [YES]

	public java.math.BigDecimal IMP_NON_ASSOG_field;

	public final static String ID_SCHEDA_LOCALE = "ID_SCHEDA_LOCALE";
	public final static String T_ID_SCHEDA_LOCALE = TABLE_NAME + "." + ID_SCHEDA_LOCALE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]

	public String ID_SCHEDA_LOCALE_field;
	
	
	public final static String DURATA_CONVENZIONE = "DURATA_CONVENZIONE";
	public final static String T_DURATA_CONVENZIONE = TABLE_NAME+"."+DURATA_CONVENZIONE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [8]
		// NULLABLE [YES]
	public Long DURATA_CONVENZIONE_field;
	
	//gm nuovo codice 3.0
	public final static String OPERE_URBANIZZAZIONE = "OPERE_URBANIZZAZIONE";
	public final static String T_OPERE_URBANIZZAZIONE = TABLE_NAME+"."+OPERE_URBANIZZAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [YES]
	public String OPERE_URBANIZZAZIONE_field;
	//gm fine nuovo codice 3.0
	
	
	
	public final static String DURATA_CONTRATTUALE = "DURATA_CONTRATTUALE";
	public final static String T_DURATA_CONTRATTUALE = TABLE_NAME+"."+DURATA_CONTRATTUALE;
		// COLUMN TYPE [bigint]
		// COLUMN SIZE [8]
		// NULLABLE [YES]
	public Long DURATA_CONTRATTUALE_field;
	
	public final static String DATA_STIPULA = "DATA_STIPULA";
	public final static String T_DATA_STIPULA = TABLE_NAME + "." + DATA_STIPULA;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String DATA_STIPULA_field;
	
	public final static String TERMINE_CONTRATTUALE = "TERMINE_CONTRATTUALE";
	public final static String T_TERMINE_CONTRATTUALE = TABLE_NAME + "." + TERMINE_CONTRATTUALE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [8]
		// NULLABLE [YES]

	public String TERMINE_CONTRATTUALE_field;
	
	public final static String SOTTOTIPO = "SOTTOTIPO";
	public final static String T_SOTTOTIPO = TABLE_NAME + "." + SOTTOTIPO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [15]
		// NULLABLE [YES]
	
	public String SOTTOTIPO_field;
	
	public final static String MODALITA_RIAGGIUDICAZIONE = "MODALITA_RIAGGIUDICAZIONE";
	public final static String T_MODALITA_RIAGGIUDICAZIONE = TABLE_NAME + "." + MODALITA_RIAGGIUDICAZIONE;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [15]
		// NULLABLE [YES]
	
	public String MODALITA_RIAGGIUDICAZIONE_field;
	
	public final static String PROG_CUI_RIAGGIUDICATO = "PROG_CUI_RIAGGIUDICATO";
	public final static String T_PROG_CUI_RIAGGIUDICATO = TABLE_NAME + "." + PROG_CUI_RIAGGIUDICATO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [15]
		// NULLABLE [YES]
	
	public String PROG_CUI_RIAGGIUDICATO_field;
	
	//gm nuovo per appalti multilotto
	public final static String CODICE_CONTRATTO = "CODICE_CONTRATTO";
	public final static String T_CODICE_CONTRATTO = TABLE_NAME + "." + CODICE_CONTRATTO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [50]
		// NULLABLE [YES]
	
	public String CODICE_CONTRATTO_field;
	
	public final static String FLAG_AGGIUD_PRINCIPALE = "FLAG_AGGIUD_PRINCIPALE";
	public final static String T_FLAG_AGGIUD_PRINCIPALE = TABLE_NAME + "." + FLAG_AGGIUD_PRINCIPALE;
	// COLUMN TYPE [varchar]
	// COLUMN SIZE [50]
	// NULLABLE [YES]

    public String FLAG_AGGIUD_PRINCIPALE_field;
    
    public final static String ID_PUBBLICAZIONE_AGG = "ID_PUBBLICAZIONE_AGG";
	public final static String T_ID_PUBBLICAZIONE_AGG = TABLE_NAME + "." + ID_PUBBLICAZIONE_AGG;
		// COLUMN TYPE [bigint identity]
		// COLUMN SIZE [19]
		// NULLABLE [NO]

	public long ID_PUBBLICAZIONE_AGG_field;

	public final static String DATA_INIZIO_PUBB_AGG = "DATA_INIZIO_PUBB_AGG";
	public final static String T_DATA_INIZIO_PUBB_AGG = TABLE_NAME + "." + DATA_INIZIO_PUBB_AGG;
		// COLUMN TYPE [datetime]
		// COLUMN SIZE [23]
		// NULLABLE [NO]

	public java.sql.Date DATA_INIZIO_PUBB_AGG_field;

	public final static String ID_MOTIVO_VAR_CO = "ID_MOTIVO_VAR_CO";
	public final static String T_ID_MOTIVO_VAR = TABLE_NAME + "." + ID_MOTIVO_VAR_CO;
		// COLUMN TYPE [varchar]
		// COLUMN SIZE [1]
		// NULLABLE [YES]

	public String ID_MOTIVO_VAR_CO_field;
	
    public final static String ORIGINE = "ORIGINE";
    public final static String T_ORIGINE = TABLE_NAME + "." + ORIGINE;
        // COLUMN TYPE [bigint]
        // COLUMN SIZE [19]
        // NULLABLE [YES]

    public String ORIGINE_field;	
    
    public final static String RELAZIONE_UNICA = "RELAZIONE_UNICA";
    public final static String T_RELAZIONE_UNICA = TABLE_NAME + "."+RELAZIONE_UNICA;
    // COLUMN TYPE [varchar]
    // COLUMN SIZE [1]
    // NULLABLE [YES]

}
