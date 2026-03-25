package it.avlp.simog.common.servlet;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.db.generated.FINE_LAVORI;

public interface ParametriServletConclusioni {
	
	public static final String TAB_SCHEDA_CONCLUSIONI = IdentificativoSchede.TAB_FINELAVORI; //"SchedaConclusioni";
	public static final String SRV_SCHEDA_CONCLUSIONI = "srvSchedaConclusioni";
	public static final String JSP_SCHEDA_CONCLUSIONI = "schedaConclusioni.jsp";
	public static final String LISTA_CONCLUSIONI = "lista_Conclusioni";
	public static final String LOAD_CONCLUSIONI = "ConclusioniLoad";
	public static final String CONCLUSIONI_CURRENT = "currentBeanConclusioni";
	
	public static final String FIELD_NAME_MOTIVO_INTERR = FINE_LAVORI.ID_MOTIVO_INTERR;
	public static final String FIELD_NAME_MOTIVO_RISOL = FINE_LAVORI.ID_MOTIVO_RISOL;
	public static final String FIELD_NAME_ID_ULTIM = FINE_LAVORI.ID_ULTIM;
	public static final String FIELD_NAME_DATA_INIZIO_ULTIM = FINE_LAVORI.DATA_INIZIO_ULTIM;
	public static final String FIELD_NAME_DATA_FINE_ULTIM = FINE_LAVORI.DATA_FINE_ULTIM;
	public static final String FIELD_NAME_DATA_RISOLUZIONE = FINE_LAVORI.DATA_RISOLUZIONE;
	public static final String FIELD_NAME_FLAG_ONERI = FINE_LAVORI.FLAG_ONERI;
	public static final String FIELD_NAME_ONERI_RISOLUZIONE = FINE_LAVORI.ONERI_RISOLUZIONE;
	public static final String FIELD_NAME_FLAG_POLIZZA = FINE_LAVORI.FLAG_POLIZZA;
	public static final String FIELD_NAME_DATA_ULTIMAZIONE = FINE_LAVORI.DATA_ULTIMAZIONE;
	public static final String FIELD_NAME_NUMERO_INFORTUNI = FINE_LAVORI.NUM_INFORTUNI;
	public static final String FIELD_NAME_NUM_INF_PERM = FINE_LAVORI.NUM_INF_PERM;
	public static final String FIELD_NAME_NUM_INF_MORT = FINE_LAVORI.NUM_INF_MORT;

	public static final String FIELD_NAME_DATA_CONSEGNA = FINE_LAVORI.DATA_CONSEGNA;
	
	//gm nuovo codice 3.0
	public static final String FIELD_NAME_GIORNI_PROROGA = FINE_LAVORI.GIORNI_PROROGA;
	public static final String FIELD_NAME_TERMINE_ULTIMAZIONE = FINE_LAVORI.TERMINE_ULTIMAZIONE;
	//gm fine nuovo codice 3.0
}
