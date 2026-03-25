package it.avlp.simog.common.servlet;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.db.generated.STATI_AVANZ;

public interface ParametriServletAvanzamento {
	
	public static final String TAB_AVANZAMENTO = IdentificativoSchede.TAB_AVANZAMENTO; // "DEsecuzioneContratto";
	public static final String SRV_SCHEDA_AVANZAMENTO = "srvSchedaAvanzamento";
	public static final String JSP_SCHEDA_AVANZAMENTO = "schedaAvanzamento.jsp";
	public static final String LISTA_AVANZAMENTI = "lista_avanzamenti";
	public static final String LOAD_AVANZAMENTO = "AvanzamentoLoad";
	public static final String AVANZAMENTO_CURRENT = "currentBeanAvanzamento";
	
	public static final String FIELD_NAME_ID_AVANZAMENTO = STATI_AVANZ.ID_AVANZAMENTO;
	public static final String FIELD_NAME_DATA_INIZIO_AVANZAMENTO = STATI_AVANZ.DATA_INIZIO_AVANZAMENTO;
	public static final String FIELD_NAME_DATA_FINE_AVANZAMENTO = STATI_AVANZ.DATA_FINE_AVANZAMENTO;
	public static final String FIELD_NAME_NUMERO_AVANZAMENTO = STATI_AVANZ.NUMERO_AVANZAMENTO;
	public static final String FIELD_NAME_FLAG_PAGAMENTO = STATI_AVANZ.FLAG_PAGAMENTO;
	public static final String FIELD_NAME_DATA_ANTICIPAZIONE = STATI_AVANZ.DATA_ANTICIPAZIONE;
	public static final String FIELD_NAME_IMPORTO_ANTICIPAZIONE = STATI_AVANZ.IMPORTO_ANTICIPAZIONE;
	public static final String FIELD_NAME_DATA_RAGGIUNGIMENTO = STATI_AVANZ.DATA_RAGGIUNGIMENTO;
	public static final String FIELD_NAME_IMPORTO_SAL = STATI_AVANZ.IMPORTO_SAL;
	public static final String FIELD_NAME_DATA_CERTIFICATO = STATI_AVANZ.DATA_CERTIFICATO;
	public static final String FIELD_NAME_IMPORTO_CERTIFICATO = STATI_AVANZ.IMPORTO_CERTIFICATO;
	public static final String FIELD_NAME_FLAG_RITARDO = STATI_AVANZ.FLAG_RITARDO;
	public static final String FIELD_NAME_NUMERO_GIORNI_SCOST = STATI_AVANZ.NUM_GIORNI_SCOST;
	public static final String FIELD_NAME_NUMERO_GIORNI_PROROGA = STATI_AVANZ.NUM_GIORNI_PROROGA;
	public static final String FIELD_NAME_DENOM_STATO_AVANZ = STATI_AVANZ.DENOM_AVANZ;
}
