package it.avlp.simog.common.servlet;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.db.generated.EVENTI_MOTIVI_VARIANTI;
import it.avlp.simog.db.generated.MOTIVO_REVISIONE_PREZZI;
import it.avlp.simog.db.generated.PUBBLICAZIONI;
import it.avlp.simog.db.generated.VARIANTI;

public interface ParametriServletVariante {
	
	public static final String TAB_SCHEDA_VARIANTE = IdentificativoSchede.TAB_VARIANTE; //"SchedaVariante";
	public static final String SRV_SCHEDA_VARIANTE = "SrvSchedaVarianti"; // Parametro da inserire nel WEB.XML
	public static final String JSP_SCHEDA_VARIANTE = "SchedaVarianti.jsp";
	public static final String LISTA_VARIANTI = "lista_varianti";
	public static final String LOAD_VARIANTE = "variante";
	public static final String VARIANTE_CURRENT = "currentBeanVariante";
	
	final String FIELD_NAME_ID_VARIANTE = VARIANTI.ID_VARIANTE;
	public static final String FIELD_NAME_ALTRE_MOTIVAZIONI = VARIANTI.ALTRE_MOTIVAZIONI;
	public static final String FIELD_NAME_DATA_ATTO_AGGIUNTIVO = VARIANTI.DATA_ATTO_AGGIUNTIVO;
	public static final String FIELD_NAME_DATA_FINE_VAR = VARIANTI.DATA_FINE_VAR;
	public static final String FIELD_NAME_DATA_INIZIO_AGGIUDICAZIONE = VARIANTI.DATA_INIZIO_AGGIUDICAZIONE;
	public static final String FIELD_NAME_DATA_INIZIO_VAR = VARIANTI.DATA_INIZIO_VAR;
	public static final String FIELD_NAME_DATA_VERB_APPR = VARIANTI.DATA_VERB_APPR;
	public static final String FIELD_NAME_ID_AGGIUDICAZIONE = VARIANTI.ID_AGGIUDICAZIONE;
	public static final String FIELD_NAME_ID_STATO = VARIANTI.ID_STATO;
	public static final String FIELD_NAME_IMP_DISPOSIZIONE = VARIANTI.IMP_DISPOSIZIONE;
	public static final String FIELD_NAME_IMP_PROGETTAZIONE = VARIANTI.IMP_PROGETTAZIONE;
	public static final String FIELD_NAME_IMP_DIRET_FORNIT = VARIANTI.IMP_RIDET_FORNIT;
	public static final String FIELD_NAME_IMP_RIDET_LAVORI = VARIANTI.IMP_RIDET_LAVORI;
	public static final String FIELD_NAME_IMP_RIDET_SERVIZI = VARIANTI.IMP_RIDET_SERVIZI;
	public static final String FIELD_NAME_IMP_SICUREZZA = VARIANTI.IMP_SICUREZZA;
	public static final String FIELD_NAME_NUM_GIORNI_PROROGA = VARIANTI.NUM_GIORNI_PROROGA;
	public static final String FIELD_NAME_ID_MOTIVAZIONE = EVENTI_MOTIVI_VARIANTI.ID_MOTIVO_VAR;
	public static final String FIELD_NAME_ULTERIORI_SOMME = VARIANTI.ULTERIORI_SOMME;
	public static final String FIELD_NAME_CIG_PROCEDURA = VARIANTI.CIG_PROCEDURA; //TICKET ALM - 3.04.3 PT
	public static final String BEAN_MOTIVI_VARIANTE = "motivi_variante";
	
	//MEV 34191 3.04.8
	public static final String FIELD_NAME_LINK_VARIANTI = VARIANTI.LINK_VARIANTI;
	
	//MEV 34469 3.04.8
	public final String FIELD_NAME_ID_MOTIVO_REV_PREZZI = MOTIVO_REVISIONE_PREZZI.ID_MOTIVO_REV_PREZZI;
	//
	
}
