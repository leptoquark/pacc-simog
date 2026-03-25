package it.avlp.simog.common.servlet;

import it.avlp.simog.beans.RequisitoGara;
import it.avlp.simog.db.generated.DETTAGLIO_REQUISITO;
import it.avlp.simog.db.generated.DOCUMENTO_REQUISITO_GARA;
import it.avlp.simog.db.generated.REQUISITO_GARA;


public interface PSReq {
    
   public final static String PREFIX_REQUISITO_GARA       = "RequisitoGara";
   public final static String PREFIX_REQUISITO_DOC        = "Documento";
   
   public final static String FIELD_NAME_REQ_ID           = REQUISITO_GARA.COD_REQUISITO_GARA;
   public final static String FIELD_NAME_REQ_REQUISITO    = DETTAGLIO_REQUISITO.CODICE;
   public final static String FIELD_NAME_REQ_DESCRIZIONE  = REQUISITO_GARA.DESCRIZIONE;
   public final static String FIELD_NAME_REQ_VALORE       = REQUISITO_GARA.VALORE;
   public final static String FIELD_NAME_REQ_ESCLUSIONE   = REQUISITO_GARA.FLG_CONDIZIONE_ESCLUSIONE;
   public final static String FIELD_NAME_REQ_COMPROVAOFF  = REQUISITO_GARA.FLG_COMPROVA_IN_OFFERTA;
   public final static String FIELD_NAME_REQ_AVVALIMENTO  = REQUISITO_GARA.FLG_AVVALIMENTO;
   public final static String FIELD_NAME_REQ_BANDO_TIPO   = REQUISITO_GARA.FLG_BANDO_TIPO;
   public final static String FIELD_NAME_REQ_RISERVATEZZA = REQUISITO_GARA.FLG_RISERVATEZZA;
   
   public final static String argsReqGara = 
         "'" + FIELD_NAME_REQ_DESCRIZIONE 
         + "','" + FIELD_NAME_REQ_VALORE
         + "','" + FIELD_NAME_REQ_ESCLUSIONE
         + "','" + FIELD_NAME_REQ_COMPROVAOFF
         + "','" + FIELD_NAME_REQ_AVVALIMENTO
         + "','" + FIELD_NAME_REQ_BANDO_TIPO
         + "','" + FIELD_NAME_REQ_RISERVATEZZA
         + "'";
   
   public final static String argsReqGaraNascosti = 
         "'" + FIELD_NAME_REQ_REQUISITO 
         + "','" + FIELD_NAME_REQ_DESCRIZIONE
         + "','" + FIELD_NAME_REQ_VALORE
         + "','" + FIELD_NAME_REQ_ESCLUSIONE
         + "','" + FIELD_NAME_REQ_COMPROVAOFF
         + "','" + FIELD_NAME_REQ_AVVALIMENTO
         + "','" + FIELD_NAME_REQ_BANDO_TIPO
         + "','" + FIELD_NAME_REQ_RISERVATEZZA
         + "'";

   public final static String FIELD_NAME_REQ_DOC_ID = DOCUMENTO_REQUISITO_GARA.COD_DOC_REQ_GARA;
   public final static String FIELD_NAME_REQ_DOC_COD_REQ_GARA = DOCUMENTO_REQUISITO_GARA.COD_REQUISITO_GARA;
   public final static String FIELD_NAME_REQ_DOC_TIPO = DOCUMENTO_REQUISITO_GARA.COD_TIPO_DOC_REQ;
   public final static String FIELD_NAME_REQ_DOC_DESCRIZIONE = DOCUMENTO_REQUISITO_GARA.DESCRIZIONE_DOCUMENTO;
   public final static String FIELD_NAME_REQ_DOC_EMETTITORE = DOCUMENTO_REQUISITO_GARA.EMETTITORE;
   public final static String FIELD_NAME_REQ_DOC_TELEFONO = DOCUMENTO_REQUISITO_GARA.TELEFONO;
   public final static String FIELD_NAME_REQ_DOC_FAX = DOCUMENTO_REQUISITO_GARA.FAX;
   public final static String FIELD_NAME_REQ_DOC_MAIL = DOCUMENTO_REQUISITO_GARA.MAIL;
   public final static String FIELD_NAME_REQ_DOC_MAIL_PEC = DOCUMENTO_REQUISITO_GARA.MAIL_PEC;
   
   public final static String argsReqGaraDoc = 
         "'" +  FIELD_NAME_REQ_DOC_DESCRIZIONE 
         + "','" + FIELD_NAME_REQ_DOC_EMETTITORE
         + "','" + FIELD_NAME_REQ_DOC_TELEFONO
         + "','" + FIELD_NAME_REQ_DOC_FAX
         + "','" + FIELD_NAME_REQ_DOC_MAIL
         + "','" + FIELD_NAME_REQ_DOC_MAIL_PEC
         + "'";
   
   public final static String argsReqGaraDocNascosti = 
         "'" + FIELD_NAME_REQ_DOC_DESCRIZIONE 
         + "','" + FIELD_NAME_REQ_DOC_EMETTITORE
         + "','" + FIELD_NAME_REQ_DOC_TELEFONO
         + "','" + FIELD_NAME_REQ_DOC_FAX
         + "','" + FIELD_NAME_REQ_DOC_MAIL
         + "','" + FIELD_NAME_REQ_DOC_MAIL_PEC
         + "','" + FIELD_NAME_REQ_DOC_TIPO
         + "'";
   
   public final static String FIELD_NAME_REQ_DOC_LISTA_DOCUMENTI = "lista_documenti_requisito";
   public final static String FIELD_NAME_REQ_TIPO_USO = "requisito_tipoUSO";
   public final static String FIELD_NAME_REQ_DOC_OB_MAP = "mappaReqDocOBMap";
   public final static String FIELD_NAME_REQ_F_USO_MAP = "mappaReqUsoMap";
   
   //Attributi in request
   public static final String LISTA_REQUISITI_GARA = "listaRequisitoGara"; 
   public static final String MAPPA_REQUISITI = "requisitiMap";
   public static final String MAPPA_REQUISITI_PER_TIPOLOGIA = "requisitiPerTipologiaMap";
   public static final String MAPPA_REQUISITI_OB = "requisitiOBMap";
   public static final String MAPPA_REQ_F_USO = "requisitiUSOMap";
   public static final String LISTA_DOCUMENTI = "listaDocumenti";
   public static final String LISTA_DOCUMENTI_OB = "listaDocumentiOB";
   public static final String MAPPA_REQ_DOC_OB = "mappaRequisitoDocumentiOB";
   public static final String LISTA_LOTTI = "listaLotti";
   public static final String MAX_INDEX_REQUISTI = "maxIndex" + PREFIX_REQUISITO_GARA;
   public static final String NUM_LOTTI = "numeroLottiGara";
   public static final String CURRENT_TAB_INDEX = "currentTabIndex";

   public static final String BLOCCO_AVCPASS = "BLOCCO_AVCPASS";

   //Actions
   public static final String ACTION_SALVA = "salva_requisito";
   public static final String ACTION_ELIMINA = "elimina_requisiti";
   
   public static final String SRV_REQUISITI_GL = "srvRequisitiGL";
   public static final String SRV_ACTION_NAME = "toDo";
   
   public static final String CODICE_REQUISITO_NON_CODIFICATO = "999";
   public static final String CODICE_DOCUMENTO_NON_CODIFICATO = "999";
   
   public static final int MARKER_999 = 1000000;
   
   public static final Long ND_REQUISITO_ID = -1L;
   
   public static final String SIMOG_PROPERTIES = "SIMOG_PROPERTIES";
   //JSP
   public static final String JSP_POPUP_DOCUMENTI_REQUISITI = "scheda1/popupDocumentiReq.jsp";
   
   
   //Paginazione
   public static final int ELEMENT_FOR_PAGE = 30;
   
   // Tipologie USO di un requisito
   public static final String USO_OB = RequisitoGara.TIPO_USO_OB;
   public static final String USO_OM = RequisitoGara.TIPO_USO_OM;
   public static final String USO_AR = RequisitoGara.TIPO_USO_AR;
   public static final String USO_FA = RequisitoGara.TIPO_USO_FA;
   public static final String USO_AA = RequisitoGara.TIPO_USO_AA;
   
   public static final String INFO_COMUNI_I_FASE = "infoComuniIFase";
   
}
