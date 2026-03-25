package it.avlp.simog.common.servlet;

import it.avlp.simog.db.generated.CUP_LOTTO_AGG;
import it.avlp.simog.db.generated.LOTTO;

public class ParametriCup {

   public static final String ELENCO_CUP = "CUP";
   public static final String PARAM_ELENCO_CUP = "elencoCup";
   public static final String PARAM_ELENCO_CUP_GARA = "elencoCup";
   
   public static final String TAB_CUP = "TAB_CUP";
   
   public final static String NR_RIGHE_CUP = "nrRighe" + ELENCO_CUP;
   
   public static final String FIELD_NAME_CUP = CUP_LOTTO_AGG.CUP;
   public static final String FIELD_NAME_ID_LOTTO = CUP_LOTTO_AGG.ID_LOTTO;
   public static final String FIELD_NAME_ID_AGG = CUP_LOTTO_AGG.ID_AGGIUDICAZIONE;
   public static final String FIELD_NAME_DATA_INIZIO_AGG = CUP_LOTTO_AGG.DATA_INIZIO_AGG;
   public static final String FIELD_NAME_OK_UTENTE = CUP_LOTTO_AGG.OK_UTENTE;
   public static final String FIELD_NAME_CIG = LOTTO.CIG;
   
   public static final String FIELD_NAME_DATIDIPE = CUP_LOTTO_AGG.TABLE_NAME + "_DATIDIPE";
   public static final String FIELD_NAME_VALIDO = CUP_LOTTO_AGG.TABLE_NAME + "_VALIDO"  ;
   
   public static final String argsCup = "'" + FIELD_NAME_CUP + "'";
   public static final String argsCupNascosti = 
                                 "'" + FIELD_NAME_CUP
                                 + "', '" + FIELD_NAME_ID_LOTTO 
                                 + "', '" + FIELD_NAME_ID_AGG 
                                 + "', '" + FIELD_NAME_DATA_INIZIO_AGG 
                                 + "', '" + FIELD_NAME_OK_UTENTE
                                 + "', '" + FIELD_NAME_VALIDO
                                 + "', '" + FIELD_NAME_DATIDIPE
                                 + "'";
   
   
   public static final String FIELD_FLAG_CUP = "FLAG_CUP";
   
   //????????
//   public static final String FIELD_FLAG_PAR_GEN_MOD1 = "";
//   public static final String FIELD_FLAG_PAR_GEN_MOD2 = "";
   
   public static final String FLAG_PNRR_PNC = "FLAG_PNRR_PNC";
   public static final String FLAG_PREVISIONE_QUOTA = "FLAG_PREVISIONE_QUOTA";
   public static final String FLAG_MISURE_PREMIALI= "FLAG_MISURE_PREMIALI";
   
   
   
   public static final String ACTION_COFERMA_CUP = "confermaCup";
   public static final String ACTION_MODIFICA_DATI_CUP = "modificaDatiCup";
   
   public static final String JSP_PARAM_INTEGRAZIONE_CUP = "paramElencoCigIntegrazioneCup.jsp";
   public static final String JSP_ELENCO_CIG_INTEGRAZIONE_CUP = "visElencoCigIntegrazioneCup.jsp";
   
   public static final String SRV_ELENCO_CIG_INTEGRAZIONE_CUP = "elencoCigIntegrazioneCup";
   public static final String INTEGRAZIONE_CUP = "integrazioneCupMap";
   public static final String PARAM_MOD_INT_CUP = "paramModintegrazioneCup";
   
   public static final String ORDER_FIELD = "order_field";
   public static final String ORDER_FIELD_VERSO = "order_field_verso";
   
   public static final String SRV_INTEGRAZIONE_CIG_UPDATE_CUP = "integrazioneCigUpdateCup";
   public static final String FROM_ELENCO_CUP = "FROM_ELENCO_CUP";
   
   public static final String FIELD_NAME_TEMATICA = "tematica";
}
