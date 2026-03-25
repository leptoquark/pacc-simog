package it.avlp.simog.common.servlet;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.db.generated.R129;

public interface ParametriServletR129 {
	public static final String TAB_SCHEDA_R129 = IdentificativoSchede.TAB_RITARDO; // "SchedaR129";
	public static final String SRV_SCHEDA_R129 = "srvSchedaR129";
	public static final String JSP_SCHEDA_R129 = "schedaR129.jsp";
	public static final String LISTA_R129 = "lista_r129";
	public static final String LOAD_R129 = "r129Load";
	public static final String R129_CURRENT = "currentBeanR129";
	
	public static final String FIELD_NAME_TIPO_COMUNICAZIONE = R129.TIPO_COMUN;
	public static final String FIELD_NAME_DURATA_SOSPENSIONE = R129.DURATA_SOSP;
	public static final String FIELD_NAME_MOTIVAZIONE_SOSPENSIONE = R129.MOTIVO_SOSP;
	public static final String FIELD_NAME_DATA_IST_RECESSO = R129.DATA_IST_RECESSO;
	public static final String FIELD_NAME_FLAG_ISTANZA_RECESSO = R129.FLAG_ACCOLTA;
	public static final String FIELD_NAME_FLAG_TARDIVA = R129.FLAG_TARDIVA;
	public static final String FIELD_NAME_FLAG_RIPRESA = R129.FLAG_RIPRESA;
	public static final String FIELD_NAME_FLAG_RISERVE = R129.FLAG_RISERVA;
	public static final String FIELD_NAME_RIMBORSO_SPESE = R129.IMPORTO_SPESE;
	public static final String FIELD_NAME_ONERI = R129.IMPORTO_ONERI;
	public static final String FIELD_NAME_ID_RECORD = R129.ID_RECORD;
	public static final String FIELD_NAME_DATA_INIZIO_RECORD = R129.DATA_INIZIO;
	public static final String FIELD_NAME_DATA_TERMINE = R129.DATA_TERMINE;
	public static final String FIELD_NAME_DATA_COMUNICAZIONE = R129.DATA_COMUNIC;
	public static final String FIELD_NAME_DATA_CONSEGNA_LAVORI = R129.DATA_CONSEGNA_LAVORI;
	
	
	
	
	
}
