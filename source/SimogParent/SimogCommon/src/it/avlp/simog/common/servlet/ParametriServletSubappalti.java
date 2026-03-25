package it.avlp.simog.common.servlet;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.db.generated.SOGGETTI_PARTECIPANTI;
import it.avlp.simog.db.generated.SUBAPPALTI;

public interface ParametriServletSubappalti {
	
	public static final String TAB_SCHEDA_SUBAPPALTI = IdentificativoSchede.TAB_SUBAPPALTO; //"SchedaSubappalti";
	public static final String SRV_SCHEDA_SUBAPPALTI = "srvSchedaSubappalti";
	public static final String JSP_SCHEDA_SUBAPPALTI = "schedaSubappalti.jsp";
	public static final String LISTA_SUBAPPALTI = "lista_Subappalti";
	public static final String LOAD_SUBAPPALTI = "SubappaltiLoad";
	public static final String SUBAPPALTI_CURRENT = "currentBeanSubappalti";
	public static final String TAB_SUBAFFIDATARIO_SELECTED = "TabSubAffidatarioSelected";
	public static final String TAB_SUBAFFIDATARIO = "TabSubAffidatario";
	
	public static final String TAB_ADD_SUBAFFIDATARIO = "TabAddSubAffidatario";
	
	public static final String FIELD_NAME_ID_RECORD = SUBAPPALTI.ID_RECORD;
 	public static final String FIELD_NAME_CF_DITTA = SUBAPPALTI.CF_DITTA;
 	public static final String FIELD_FLAG_DITTA_SUB_ESTERA = SUBAPPALTI.FLAG_DITTA_SUB_ESTERA; //MEV 36771 3.04.8.1
 	public static final String FIELD_NAME_DATA_AUTORIZZAZIONE = SUBAPPALTI.DATA_AUTORIZZAZIONE;
	public static final String FIELD_NAME_DATA_FINE_RECORD = SUBAPPALTI.DATA_FINE_RECORD;
	public static final String FIELD_NAME_DATA_INIZIO_RECORD = SUBAPPALTI.DATA_INIZIO_RECORD;
	public static final String FIELD_NAME_IMPORTO_EFFETTIVO = SUBAPPALTI.IMPORTO_EFFETTIVO;
	public static final String FIELD_NAME_IMPORTO_PRESUNTO = SUBAPPALTI.IMPORTO_PRESUNTO;
	public static final String FIELD_NAME_OGGETTO_SUBAPPALTO = SUBAPPALTI.OGGETTO_SUBAPPALTO;
	public static final String FIELD_NAME_ID_CATEGORIA = SUBAPPALTI.ID_CATEGORIA;
	public static final String FIELD_NAME_ID_CPV = SUBAPPALTI.ID_CPV;
 	 
 
	//gm nuovo codice 3.0
	public static final String FIELD_NAME_CF_AGGIUDICATARIO = SUBAPPALTI.CF_AGGIUDICATARIO;
	//gm fine nuovo codice 3.0
	

	  
	
}
