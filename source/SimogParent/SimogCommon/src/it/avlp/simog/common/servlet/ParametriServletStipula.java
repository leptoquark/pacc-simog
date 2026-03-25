package it.avlp.simog.common.servlet;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.STIPULA;
import it.avlp.simog.db.generated.PUBBLICAZIONI;

public interface ParametriServletStipula {
	//parametri per scheda stipula
	public static final String ID_STIPULA = STIPULA.ID_STIPULA;
	public static final String DATA_INIZIO_STIPULA = STIPULA.DATA_INIZIO_STIPULA;
	public static final String FIELD_NAME_DATA_STIPULA_CONTRATTO = STIPULA.DATA_STIPULA_CONTRATTO;
	public static final String FIELD_NAME_DATA_DECORRENZA_STIPULA = STIPULA.DATA_DECORRENZA;
	public static final String FIELD_NAME_DATA_SCADENZA_STIPULA = STIPULA.DATA_SCADENZA;
	
	public static final String FIELD_NAME_OGGETTO = LOTTO.OGGETTO;
	public static final String ID_PUBBLICAZIONE = PUBBLICAZIONI.ID_PUBBLICAZIONE;
	public static final String DATA_INIZIO_PUBBLICAZIONE = PUBBLICAZIONI.DATA_INIZIO_PUBB;
	
	public static String TAB_POSIZIONE_AGGIUDICATARI = "TabPosizioneAggiudicatari";
	public static final String ATTRIB_AGGIUDICATARI = "listAggiudicatari";
	public static final String TAB_STIPULA = IdentificativoSchede.TAB_STIPULA;
	public static final String SCHEDA_STIPULA = "schedaStipula";
	public static final String SRV_STIPULA = "SrvStipula";
	public static final String JSP_STIPULA = "schedaB1/schedaStipula.jsp";
	
}
