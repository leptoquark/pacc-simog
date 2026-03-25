package it.avlp.simog.common.servlet;

import it.avlp.simog.db.generated.LOTTO;


public interface ParametriServletLotto {
	public static final String TAB_LOTTO = "Lotto";
	public static final String MOTIVAZIONI_LIST = "MotivazioniList";
	public static final String FIELD_NAME_MOTIVAZIONE = LOTTO.ID_MOTIVAZIONE;
	public static final String FIELD_NAME_NOTE = LOTTO.NOTE_CANC;
	public static final String FIELD_NAME_LOTTI_CANCELLARE = "id_lotti_cancellare";
	
	//is3031_RFWEBGL00Active
	public static final String TIPO_APPALTO_LOTTO_L = "TIPO_APPALTO_LOTTO_L";
	public static final String TIPO_APPALTO_LOTTO_SF = "TIPO_APPALTO_LOTTO_SF";
	
	public static final String TAB_PARI_OPPORTUNITA = "TAB_PARI_OPPORTUNITA";
	public static final String MISURE_PREMIALI = "MISURE_PREMIALI";
	
	public static final String MOTIVO_DEROGA_BEAN_SELECTED = "MOTIVO_DEROGA_BEAN_SELECTED";
	public static final String MISURA_PREMIALE_BEAN_SELECTED = "MISURA_PREMIALE_BEAN_SELECTED";

	//MEV 37010 3.04.8.1
	public static final String IS_EREDITATI = "IS_EREDITATI";
}
