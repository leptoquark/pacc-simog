package it.avlp.simog.common.servlet;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.db.generated.COLLAUDO;

public interface ParametriServletCollaudo {
	
	public static final String TAB_SCHEDA_COLLAUDO = IdentificativoSchede.TAB_COLLAUDO; //"SchedaCollaudo";
	public static final String SRV_SCHEDA_COLLAUDO = "srvCollaudo";
	public static final String JSP_SCHEDA_COLLAUDO = "schedaCollaudo.jsp";
	public static final String LISTA_COLLAUDO = "lista_Collaudo";
	public static final String LOAD_COLLAUDO = "CollaudoLoad";
	public static final String COLLAUDO_CURRENT = "currentBeanCollaudo";
	
	public static final String FIELD_NAME_AMM_IMPORTO_DEF = COLLAUDO.AMM_IMPORTO_DEF;
	public static final String FIELD_NAME_AMM_IMPORTO_RICH = COLLAUDO.AMM_IMPORTO_RICH;
	public static final String FIELD_NAME_AMM_NUM_DADEF = COLLAUDO.AMM_NUM_DADEF;
	public static final String FIELD_NAME_AMM_NUM_DEFINITE = COLLAUDO.AMM_NUM_DEFINITE;
	public static final String FIELD_NAME_ARB_IMPORTO_DEF = COLLAUDO.ARB_IMPORTO_DEF;
	public static final String FIELD_NAME_ARB_IMPORTO_RICH = COLLAUDO.ARB_IMPORTO_RICH;
	public static final String FIELD_NAME_ARB_NUM_DADEF = COLLAUDO.ARB_NUM_DADEF;
	public static final String FIELD_NAME_ARB_NUM_DEFINITE = COLLAUDO.ARB_NUM_DEFINITE;
	public static final String FIELD_NAME_DATA_CERT_COLLAUDO = COLLAUDO.DATA_CERT_COLLAUDO;
	public static final String FIELD_NAME_DATA_COLLAUDO_STAT = COLLAUDO.DATA_COLLAUDO_STAT;
	public static final String FIELD_NAME_DATA_DELIBERA = COLLAUDO.DATA_DELIBERA;
	public static final String FIELD_NAME_DATA_FINE_COLL = COLLAUDO.DATA_FINE_COLL;
	public static final String FIELD_NAME_DATA_INIZIO_COLL = COLLAUDO.DATA_INIZIO_COLL;
	public static final String FIELD_NAME_DATA_INIZIO_OPER = COLLAUDO.DATA_INIZIO_OPER;
	public static final String FIELD_NAME_DATA_NOMINA_COLL = COLLAUDO.DATA_NOMINA_COLL;
	public static final String FIELD_NAME_DATA_REGOLARE_ESEC = COLLAUDO.DATA_REGOLARE_ESEC;
	public static final String FIELD_NAME_ESITO_COLLAUDO = COLLAUDO.ESITO_COLLAUDO;
	public static final String FIELD_NAME_GIU_IMPORTO_DEF = COLLAUDO.GIU_IMPORTO_DEF;
	public static final String FIELD_NAME_GIU_IMPORTO_RICH = COLLAUDO.GIU_IMPORTO_RICH;
	public static final String FIELD_NAME_GIU_NUM_DADEF = COLLAUDO.GIU_NUM_DADEF;
	public static final String FIELD_NAME_GIU_NUM_DEFINITE = COLLAUDO.GIU_NUM_DEFINITE;
	public static final String FIELD_NAME_TRA_IMPORTO_DEF = COLLAUDO.TRA_IMPORTO_DEF;
	public static final String FIELD_NAME_TRA_IMPORTO_RICH = COLLAUDO.TRA_IMPORTO_RICH;
	public static final String FIELD_NAME_TRA_NUM_DADEF = COLLAUDO.TRA_NUM_DADEF;
	public static final String FIELD_NAME_TRA_NUM_DEFINITE = COLLAUDO.TRA_NUM_DEFINITE;
	public static final String FIELD_NAME_ID_COLLAUDO= COLLAUDO.ID_COLLAUDO;
	public static final String FIELD_NAME_IMP_DISPOSIZIONE = COLLAUDO.IMP_DISPOSIZIONE;
	public static final String FIELD_NAME_IMP_FINALE_FORNIT = COLLAUDO.IMP_FINALE_FORNIT;
	public static final String FIELD_NAME_IMP_FINALE_LAVORI =COLLAUDO.IMP_FINALE_LAVORI;
	public static final String FIELD_NAME_IMP_FINALE_SICUR = COLLAUDO.IMP_FINALE_SICUR;
	public static final String FIELD_NAME_IMP_FINALE_SERVIZI = COLLAUDO.IMP_FINALE_SERVIZI;
	public static final String FIELD_NAME_IMP_PROGETTAZIONE = COLLAUDO.IMP_PROGETTAZIONE;
	public static final String FIELD_NAME_MODO_COLLAUDO = COLLAUDO.MODO_COLLAUDO;
	public static final String FIELD_NAME_IMP_FINALE_TOTALE = "finale";
	
	public static final String FIELD_NAME_LAVORI_ANNUALI_ESTESI = COLLAUDO.LAVORI_ESTESI;
	
	//costanti per aggiungere i valori calcolati nella scheda
	public static final String SUBTOTALE = "sub";
	public static final String SUBTOTALE2 = "sub2";
	

}
