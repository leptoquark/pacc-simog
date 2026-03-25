package it.avlp.simog.common.servlet;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.db.generated.SOSPENSIONI;

public interface ParametriServletSospensioni {
	public static final String TAB_SCHEDA_SOSPENSIONI = IdentificativoSchede.TAB_SOSPENSIONE; // "SchedaSospensioni";
	public static final String SRV_SCHEDA_SOSPENSIONI = "srvSchedaSospensioni"; // Parametro da inserire nel WEB.XML
	public static final String JSP_SCHEDA_SOSPENSIONI = "schedaSospensioni.jsp";
	public static final String LISTA_SOSPENSIONI = "lista_sospensioni";
	public static final String LOAD_SOSPENSIONI = "sospensioniLoad";
	public static final String SOSPENSIONI_CURRENT = "currentBeanSospensioni";
	
	public static final String FIELD_NAME_ID_SOSPENSIONE = SOSPENSIONI.ID_SOSPENSIONE;
	public static final String FIELD_NAME_DATA_INIZIO_SOSP = SOSPENSIONI.DATA_INIZIO_SOSP;
	public static final String FIELD_NAME_DATA_FINE_SOSP = SOSPENSIONI.DATA_FINE_SOSP;
	public static final String FIELD_NAME_ID_STATO = SOSPENSIONI.ID_STATO;
	public static final String FIELD_NAME_ID_AGGIUDICAZIONE = SOSPENSIONI.ID_AGGIUDICAZIONE;
	public static final String FIELD_NAME_DATA_INIZIO_AGGIUDICAZIONE = SOSPENSIONI.DATA_INIZIO_AGGIUDICAZIONE;
	public static final String FIELD_NAME_DATA_VERB_SOSP = SOSPENSIONI.DATA_VERB_SOSP;
	public static final String FIELD_NAME_DATA_VERB_RIPR = SOSPENSIONI.DATA_VERB_RIPR;
	public static final String FIELD_NAME_ID_MOTIVO_SOSP = SOSPENSIONI.ID_MOTIVO_SOSP;
	public static final String FIELD_NAME_FLAG_SUPERO_TEMP = SOSPENSIONI.FLAG_SUPERO_TEMP;
	public static final String FIELD_NAME_FLAG_RISERVE = SOSPENSIONI.FLAG_RISERVE;
	public static final String FIELD_NAME_FLAG_VERBALE = SOSPENSIONI.FLAG_VERBALE;
	

	
}
