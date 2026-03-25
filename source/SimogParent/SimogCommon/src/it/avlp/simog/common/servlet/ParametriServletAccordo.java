package it.avlp.simog.common.servlet;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.db.generated.ACCORDI;

public interface ParametriServletAccordo {

	/**
	 * Tale interfaccia permette di memorizzare all'interno di costanti i nomi relativi a
	 * 	-Tabella scheda
	 * 	-Servlet scheda
	 * 	-Scheda JSP
	 *  -lista
	 *  -load
	 *  -currentBean
	 *  -nonche' i campi della Tabella relativa. 
	 */
	
	public static final String TAB_SCHEDA_ACCORDO = IdentificativoSchede.TAB_ACCORDO; //"SchedaAccordo";
	public static final String SRV_SCHEDA_ACCORDO = "srvSchedaAccordo"; // Parametro da inserire nel WEB.XML
	public static final String JSP_SCHEDA_ACCORDO = "schedaAccordo.jsp";
	public static final String LISTA_ACCORDI = "lista_accordo";
	public static final String LOAD_ACCORDO = "accordo";
	public static final String ACCORDO_CURRENT = "currentBeanAccordo";
	
	public static final String FIELD_NAME_ID_ACCORDO = ACCORDI.ID_ACCORDO;
	public static final String FIELD_NAME_DATA_ACCORDO = ACCORDI.DATA_ACCORDO;
	public static final String FIELD_NAME_DATA_FINE_ACC = ACCORDI.DATA_FINE_ACC;
	public static final String FIELD_NAME_DATA_INIZIO_ACC = ACCORDI.DATA_INIZIO_ACC;
	public static final String FIELD_NAME_DATA_INIZIO_AGGIUDICAZIONE = ACCORDI.DATA_INIZIO_AGGIUDICAZIONE;
	public static final String FIELD_NAME_ID_AGGIUDICAZIONE = ACCORDI.ID_AGGIUDICAZIONE;
	public static final String FIELD_NAME_ID_STATO = ACCORDI.ID_STATO;
	public static final String FIELD_NAME_NUM_RISERVE = ACCORDI.NUM_RISERVE;
	public static final String FIELD_NAME_ONERI_DERIVANTI = ACCORDI.ONERI_DERIVANTI;
	
	
	
}
