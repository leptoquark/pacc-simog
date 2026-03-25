package it.avlp.simog.garamanager;

import it.avlp.simog.beans.RequisitoGara;
import it.avlp.simog.beans.RequisitoGara.Documento;
import it.avlp.simog.common.servlet.PSReq;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.DETTAGLIO_REQUISITO;
import it.avlp.simog.db.generated.DOCUMENTO_REQUISITO;
import it.avlp.simog.db.generated.DOCUMENTO_REQUISITO_GARA;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.REQUISITO;
import it.avlp.simog.db.generated.REQUISITO_GARA;
import it.avlp.simog.db.generated.TIPO_DOCUMENTO_REQ;
import it.avlp.simog.db.generated.TIPO_FONTE_DOCUMENTO;
import it.avlp.simog.db.generated.TIPO_REQUISITO;
import it.avlp.simog.db.generated.TIPO_USO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

// is3025_REQUISITIActive

public class RequisitiGLManager extends AccessiDB {


	/*********************************************************************************************
	 * Costruttore
	 * 
	 * @param currentActiveConnection
	 * @param logger
	 */
	public RequisitiGLManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}	
	
	
    private final String GET_REQUISITI_GARA_BY_ID_GARA = 
        "SELECT "
        + REQUISITO_GARA.COD_REQUISITO_GARA 
        + "," + DETTAGLIO_REQUISITO.T_COD_DETT_REQUISITO
        + "," + DETTAGLIO_REQUISITO.T_CODICE
        + "," + REQUISITO_GARA.T_DESCRIZIONE
        + "," + REQUISITO_GARA.T_VALORE
        + "," + REQUISITO_GARA.T_COD_GARA
        + "," + REQUISITO_GARA.T_FLG_AVVALIMENTO
        + "," + REQUISITO_GARA.T_FLG_BANDO_TIPO
        + "," + REQUISITO_GARA.T_FLG_COMPROVA_IN_OFFERTA
        + "," + REQUISITO_GARA.T_FLG_CONDIZIONE_ESCLUSIONE
        + "," + REQUISITO_GARA.T_FLG_RISERVATEZZA
        + "," + TIPO_USO.T_CODICE + " AS " + Costanti.TIPOUSO_CODICE_AS_USO 
        + " FROM "
        + REQUISITO_GARA.TABLE_NAME
        + " JOIN " 
        + DETTAGLIO_REQUISITO.TABLE_NAME
        + " ON " + REQUISITO_GARA.T_COD_DETT_REQUISITO + " = " + DETTAGLIO_REQUISITO.T_COD_DETT_REQUISITO
        + " JOIN " 
        + TIPO_USO.TABLE_NAME
        + " ON " + TIPO_USO.T_COD_TIPO_USO + " = " + DETTAGLIO_REQUISITO.T_COD_TIPO_USO   
        + " WHERE "
        + REQUISITO_GARA.T_DATA_REVOCA + " IS NULL "
        + " AND "
        + REQUISITO_GARA.T_MASTER + " IS NOT NULL "
        + " AND "
        + REQUISITO_GARA.T_COD_GARA + " = ? "
        ;
	
//    private final String GET_REQUISITI_GARA_BY_ID_GARA = 
//       "SELECT "
////       + "MAX(" 
//       + REQUISITO_GARA.COD_REQUISITO_GARA 
////        + ") AS " + REQUISITO_GARA.COD_REQUISITO_GARA
//       + "," + DETTAGLIO_REQUISITO.T_COD_DETT_REQUISITO
//       + "," + DETTAGLIO_REQUISITO.T_CODICE
//       + "," + REQUISITO_GARA.T_DESCRIZIONE
//       + "," + REQUISITO_GARA.T_VALORE
//       + "," + REQUISITO_GARA.T_ID_GARA
//       + "," + REQUISITO_GARA.T_FLG_AVVALIMENTO
//       + "," + REQUISITO_GARA.T_FLG_BANDO_TIPO
//       + "," + REQUISITO_GARA.T_FLG_COMPROVA_IN_OFFERTA
//       + "," + REQUISITO_GARA.T_FLG_CONDIZIONE_ESCLUSIONE
//       + "," + REQUISITO_GARA.T_FLG_RISERVATEZZA
//       + "," + TIPO_USO.T_CODICE + " AS USO"
//       + " FROM "
//       + REQUISITO_GARA.TABLE_NAME
//       + " JOIN " 
//       + DETTAGLIO_REQUISITO.TABLE_NAME
//       + " ON " + REQUISITO_GARA.T_COD_DETT_REQUISITO + " = " + DETTAGLIO_REQUISITO.T_COD_DETT_REQUISITO
//       + " JOIN " 
//       + TIPO_USO.TABLE_NAME
//       + " ON " + TIPO_USO.T_COD_TIPO_USO + " = " + DETTAGLIO_REQUISITO.T_COD_TIPO_USO   
//       + " WHERE "
//       + REQUISITO_GARA.T_DATA_REVOCA + " IS NULL "    
//       + " AND "
//       + REQUISITO_GARA.T_COD_REQUISITO_GARA + " IN ("
//       
//             + "SELECT "
//             + "MAX(" + REQUISITO_GARA.T_COD_REQUISITO_GARA + ") AS " + REQUISITO_GARA.COD_REQUISITO_GARA
//             + " FROM "
//             + REQUISITO_GARA.TABLE_NAME
//             + " LEFT OUTER JOIN " 
//             + DOCUMENTO_REQUISITO_GARA.TABLE_NAME
//             + " ON " + DOCUMENTO_REQUISITO_GARA.T_COD_REQUISITO_GARA + " = " + REQUISITO_GARA.T_COD_REQUISITO_GARA
//             + " WHERE "
//             + REQUISITO_GARA.T_ID_GARA + " = ? "
//             + " AND "
//             + REQUISITO_GARA.T_DATA_REVOCA + " IS NULL "
//             + " GROUP BY "
//             + REQUISITO_GARA.T_COD_DETT_REQUISITO
//             + "," + REQUISITO_GARA.T_DESCRIZIONE
//             + "," + REQUISITO_GARA.T_VALORE
//             + "," + REQUISITO_GARA.T_FLG_AVVALIMENTO
//             + "," + REQUISITO_GARA.T_FLG_BANDO_TIPO
//             + "," + REQUISITO_GARA.T_FLG_COMPROVA_IN_OFFERTA
//             + "," + REQUISITO_GARA.T_FLG_CONDIZIONE_ESCLUSIONE
//             + "," + REQUISITO_GARA.T_FLG_RISERVATEZZA    
//             + "," + buildISNULL(DOCUMENTO_REQUISITO_GARA.T_COD_TIPO_DOC_REQ, 0)
//             + "," + buildISNULL(DOCUMENTO_REQUISITO_GARA.DESCRIZIONE_DOCUMENTO, "*")
//             + "," + buildISNULL(DOCUMENTO_REQUISITO_GARA.EMETTITORE, "*")
//             + "," + buildISNULL(DOCUMENTO_REQUISITO_GARA.TELEFONO, 0)
//             + "," + buildISNULL(DOCUMENTO_REQUISITO_GARA.FAX, 0)
//             + "," + buildISNULL(DOCUMENTO_REQUISITO_GARA.MAIL, "*")
//             + "," + buildISNULL(DOCUMENTO_REQUISITO_GARA.MAIL_PEC, "*")
//       
//       + ")"
////       + " GROUP BY "
////       + DETTAGLIO_REQUISITO.T_COD_DETT_REQUISITO
////       + "," + DETTAGLIO_REQUISITO.T_CODICE
////       + "," + REQUISITO_GARA.T_DESCRIZIONE
////       + "," + REQUISITO_GARA.T_VALORE
////       + "," + REQUISITO_GARA.T_ID_GARA
////       + "," + REQUISITO_GARA.T_FLG_AVVALIMENTO
////       + "," + REQUISITO_GARA.T_FLG_BANDO_TIPO
////       + "," + REQUISITO_GARA.T_FLG_COMPROVA_IN_OFFERTA
////       + "," + REQUISITO_GARA.T_FLG_CONDIZIONE_ESCLUSIONE
////       + "," + REQUISITO_GARA.T_FLG_RISERVATEZZA    
//       + " ORDER BY "
//       + REQUISITO_GARA.COD_REQUISITO_GARA
//       ;
    
    
    private final String GET_ID_LOTTO_REQUISITI_GARA =
       "SELECT DISTINCT "
       + REQUISITO_GARA.T_COD_LOTTO
       + " FROM "
       + REQUISITO_GARA.TABLE_NAME
       + " JOIN "
       + LOTTO.TABLE_NAME
       + " ON " + LOTTO.T_ID_LOTTO + " = " + REQUISITO_GARA.T_COD_LOTTO
       + " WHERE "
       + REQUISITO_GARA.T_COD_GARA + " = ? "
       + " AND " + REQUISITO_GARA.T_COD_DETT_REQUISITO + " = ? "
       + " AND " + buildISNULL(REQUISITO_GARA.T_DESCRIZIONE, "") + " = ? "
       + " AND " + buildISNULL(REQUISITO_GARA.T_VALORE, "") + " = ? "
       + " AND " + REQUISITO_GARA.T_FLG_CONDIZIONE_ESCLUSIONE + " = ? "
       + " AND " + REQUISITO_GARA.T_FLG_COMPROVA_IN_OFFERTA + " = ? "
       + " AND " + REQUISITO_GARA.T_FLG_AVVALIMENTO + " = ? "
       + " AND " + REQUISITO_GARA.T_FLG_BANDO_TIPO + " = ? "
       + " AND " + REQUISITO_GARA.T_FLG_RISERVATEZZA + " = ? "
       + " AND " + REQUISITO_GARA.T_COD_LOTTO + " IS NOT NULL "   
       + " AND " + REQUISITO_GARA.T_DATA_REVOCA + " IS NULL "
       + " AND " + LOTTO.T_DATA_CANCELLAZIONE_LOTTO + " IS NULL "
       + " AND " + LOTTO.T_DATA_INIB_PAGAMENTO + " IS NULL "
       ;
    
    private final String GET_DOCUMENTO_GARA_LIST_BY_REQUISITO =
       "SELECT "
       + TIPO_DOCUMENTO_REQ.T_COD_TIPO_DOC_REQ
       + "," + TIPO_DOCUMENTO_REQ.CODICE
       + "," + DOCUMENTO_REQUISITO_GARA.DESCRIZIONE_DOCUMENTO
       + "," + DOCUMENTO_REQUISITO_GARA.EMETTITORE
       + "," + DOCUMENTO_REQUISITO_GARA.TELEFONO
       + "," + DOCUMENTO_REQUISITO_GARA.FAX
       + "," + DOCUMENTO_REQUISITO_GARA.MAIL
       + "," + DOCUMENTO_REQUISITO_GARA.MAIL_PEC
       + " FROM "
       + DOCUMENTO_REQUISITO_GARA.TABLE_NAME
       + " JOIN "
       + TIPO_DOCUMENTO_REQ.TABLE_NAME
       + " ON " + TIPO_DOCUMENTO_REQ.T_COD_TIPO_DOC_REQ + " = " + DOCUMENTO_REQUISITO_GARA.T_COD_TIPO_DOC_REQ
       + " WHERE "
       + DOCUMENTO_REQUISITO_GARA.T_COD_REQUISITO_GARA + " = ? "
       + " AND "
       + TIPO_DOCUMENTO_REQ.T_DATA_INIZIO + " <= ? "  //+ buildGetDate() 
       + " AND (" + TIPO_DOCUMENTO_REQ.T_DATA_FINE + " IS NULL OR " 
       + TIPO_DOCUMENTO_REQ.T_DATA_FINE + " >= ? " // + buildGetDate() 
       + ")"
       ;
    
    private final String GET_DOCUMENTO_REQUISITO_LIST = 
       "SELECT * "
       + " FROM "
       + TIPO_DOCUMENTO_REQ.TABLE_NAME
       + " WHERE "
       + TIPO_DOCUMENTO_REQ.DATA_INIZIO + " <= ?" //+ buildGetDate() 
       + " AND (" + TIPO_DOCUMENTO_REQ.DATA_FINE + " IS NULL OR " 
          + TIPO_DOCUMENTO_REQ.DATA_FINE + " >= ?" //+ buildGetDate() 
        + " )"
       ;

    private final String GET_DOCUMENTO_REQUISITO_FONTE_OE_LIST = 
       "SELECT "
       + TIPO_DOCUMENTO_REQ.T_CODICE
       + " FROM "
       + TIPO_DOCUMENTO_REQ.TABLE_NAME
       + " JOIN "
       + TIPO_FONTE_DOCUMENTO.TABLE_NAME
       + " ON "
       + TIPO_DOCUMENTO_REQ.T_COD_TIPO_FONTE_DOC + " = " + TIPO_FONTE_DOCUMENTO.T_COD_TIPO_FONTE_DOC
       + " WHERE"
       + "(" 
       + TIPO_FONTE_DOCUMENTO.T_CODICE + " = '" + Costanti.TIPOFONTEDOCUMENTO_CODICE_OPERATOREECONOMICO + "' "
       + " OR " 
       + TIPO_FONTE_DOCUMENTO.T_CODICE + " = '" + Costanti.TIPOFONTEDOCUMENTO_CODICE_OPERATOREECONOMICO_AGG_SUBB + "' "
       + ")"
       + " AND "
       + TIPO_DOCUMENTO_REQ.T_DATA_INIZIO + " <= ? " //+ buildGetDate() 
       + " AND (" + TIPO_DOCUMENTO_REQ.T_DATA_FINE + " IS NULL OR " 
       + TIPO_DOCUMENTO_REQ.T_DATA_FINE + " >= ? " //+ buildGetDate() 
       + " )"
       ;
    
    private final String GET_DOCUMENTO_REQUISITO_OBBLIGATORIO_LIST = 
       "SELECT "
       + DOCUMENTO_REQUISITO.T_COD_TIPO_DOC_REQ
       + "," + TIPO_DOCUMENTO_REQ.T_CODICE
       + "," + TIPO_DOCUMENTO_REQ.T_DESCRIZIONE
       + " FROM "
       + DOCUMENTO_REQUISITO.TABLE_NAME
       + " JOIN "
       + TIPO_DOCUMENTO_REQ.TABLE_NAME
       + " ON " + DOCUMENTO_REQUISITO.T_COD_TIPO_DOC_REQ + " = " + TIPO_DOCUMENTO_REQ.T_COD_TIPO_DOC_REQ
       + " WHERE "
       + DOCUMENTO_REQUISITO.COD_DETT_REQUISITO + " = ? "
       + " AND " + DOCUMENTO_REQUISITO.T_DATA_INIZIO + " <= ? " //+ buildGetDate() 
       + " AND (" + DOCUMENTO_REQUISITO.T_DATA_FINE + " IS NULL OR " 
       + DOCUMENTO_REQUISITO.T_DATA_FINE + " >= ?" //+ buildGetDate() 
       + " )"
       + " AND " + TIPO_DOCUMENTO_REQ.T_DATA_INIZIO + " <= ? " //+ buildGetDate() 
       + " AND (" + TIPO_DOCUMENTO_REQ.T_DATA_FINE + " IS NULL OR " 
       + TIPO_DOCUMENTO_REQ.T_DATA_FINE + " >= ?" //+ buildGetDate() 
       + " )"
       ;
    
    private final String GET_DETTAGLIO_REQUISITO_FACOLTATIVO_LIST =
       "SELECT "
       + DETTAGLIO_REQUISITO.T_COD_DETT_REQUISITO
       + "," + DETTAGLIO_REQUISITO.T_DESCRIZIONE
       + "," + DETTAGLIO_REQUISITO.T_CODICE
       + " FROM "
       + DETTAGLIO_REQUISITO.TABLE_NAME
       + " JOIN "
       + TIPO_USO.TABLE_NAME
       + " ON " 
       + DETTAGLIO_REQUISITO.T_COD_TIPO_USO + " = " + TIPO_USO.T_COD_TIPO_USO
       + " WHERE "
       + TIPO_USO.T_CODICE + " IN ('" + RequisitoGara.TIPO_USO_FA + "','" + RequisitoGara.TIPO_USO_OM + "')"
       + " AND " + DETTAGLIO_REQUISITO.T_DATA_INIZIO + " <= ? "
       + " AND (" + DETTAGLIO_REQUISITO.T_DATA_FINE + " IS NULL OR " + DETTAGLIO_REQUISITO.T_DATA_FINE + " >= ?)"
       + " AND " + TIPO_USO.T_DATA_INIZIO + " <= ? "
       + " AND (" + TIPO_USO.T_DATA_FINE + " IS NULL OR " + TIPO_USO.T_DATA_FINE + " >= ?)"
       ;
    
    private final String GET_DETTAGLIO_REQUISITO_FACOLTATIVO_USO_LIST =
       "SELECT "
       + DETTAGLIO_REQUISITO.T_COD_DETT_REQUISITO
       + "," + TIPO_USO.T_CODICE + " AS " + Costanti.TIPOUSO_CODICE_AS_USO 
       + "," + DETTAGLIO_REQUISITO.T_CODICE 
       + " FROM "
       + DETTAGLIO_REQUISITO.TABLE_NAME
       + " JOIN "
       + TIPO_USO.TABLE_NAME
       + " ON " 
       + DETTAGLIO_REQUISITO.T_COD_TIPO_USO + " = " + TIPO_USO.T_COD_TIPO_USO
       + " WHERE "
       + TIPO_USO.T_CODICE + " IN ('" + RequisitoGara.TIPO_USO_FA + "','" + RequisitoGara.TIPO_USO_OM + "')"
       + " AND " + DETTAGLIO_REQUISITO.T_DATA_INIZIO + " <= ? "
       + " AND (" + DETTAGLIO_REQUISITO.T_DATA_FINE + " IS NULL OR " + DETTAGLIO_REQUISITO.T_DATA_FINE + " >= ?)"
       + " AND " + TIPO_USO.T_DATA_INIZIO + " <= ? "
       + " AND (" + TIPO_USO.T_DATA_FINE + " IS NULL OR " + TIPO_USO.T_DATA_FINE + " >= ?)"
       ;
    
    private final String GET_DETTAGLIO_REQUISITO_OBBLIGATORIO_LIST =
       "SELECT "
       + DETTAGLIO_REQUISITO.T_COD_DETT_REQUISITO
       + "," + DETTAGLIO_REQUISITO.T_DESCRIZIONE
       + "," + DETTAGLIO_REQUISITO.T_CODICE
       + " FROM "
       + DETTAGLIO_REQUISITO.TABLE_NAME
       + " JOIN "
       + TIPO_USO.TABLE_NAME
       + " ON " 
       + DETTAGLIO_REQUISITO.T_COD_TIPO_USO + " = " + TIPO_USO.T_COD_TIPO_USO
       + " WHERE "
       + TIPO_USO.T_CODICE + " <> '" + RequisitoGara.TIPO_USO_FA + "'"
       + " AND " + DETTAGLIO_REQUISITO.T_DATA_INIZIO + " <= ? "
       + " AND (" + DETTAGLIO_REQUISITO.T_DATA_FINE + " IS NULL OR " + DETTAGLIO_REQUISITO.T_DATA_FINE + " >= ?)"
       + " AND " + TIPO_USO.T_DATA_INIZIO + " <= ? "
       + " AND (" + TIPO_USO.T_DATA_FINE + " IS NULL OR " + TIPO_USO.T_DATA_FINE + " >= ?)"
       ;
 
    private final String GET_REQUISITI_OBBLIGATORI_GARA = 
       "SELECT "
       + DETTAGLIO_REQUISITO.T_COD_DETT_REQUISITO
       + "," + DETTAGLIO_REQUISITO.T_CODICE
       + "," + DETTAGLIO_REQUISITO.T_DESCRIZIONE
       + "," + TIPO_USO.T_CODICE + " AS " + Costanti.TIPOUSO_CODICE_AS_USO
       + " FROM "
       + DETTAGLIO_REQUISITO.TABLE_NAME
       + " JOIN "
       + TIPO_USO.TABLE_NAME
       + " ON " 
       + DETTAGLIO_REQUISITO.T_COD_TIPO_USO + " = " + TIPO_USO.T_COD_TIPO_USO
       + " WHERE "
       + TIPO_USO.T_CODICE + " NOT IN ('" + RequisitoGara.TIPO_USO_FA + "','" + RequisitoGara.TIPO_USO_AR + "')"
       + " AND " + DETTAGLIO_REQUISITO.T_DATA_INIZIO + " <= ?" //+ buildGetDate()
       + " AND (" + DETTAGLIO_REQUISITO.T_DATA_FINE + " IS NULL OR " 
       + DETTAGLIO_REQUISITO.T_DATA_FINE + " >= ?" //+ buildGetDate() 
       + ")"
       + " AND " + TIPO_USO.T_DATA_INIZIO + " <= ?" //+ buildGetDate()
       + " AND (" + TIPO_USO.T_DATA_FINE + " IS NULL OR " 
       + TIPO_USO.T_DATA_FINE + " >= ?" //+ buildGetDate() 
       + ")"
       ;
    
//    private final String GET_DETTAGLIO_REQUISITO_LIST = 
//       "SELECT * "
//       + " FROM "
//       + DETTAGLIO_REQUISITO.TABLE_NAME
//       + " JOIN " 
//       + REQUISITO.TABLE_NAME
//       + " ON " + DETTAGLIO_REQUISITO.T_COD_REQUISITO + " = " + REQUISITO.T_COD_REQUISITO
//       + " JOIN " 
//       + TIPO_REQUISITO.TABLE_NAME
//       + " ON " + REQUISITO.T_COD_TIPO_REQUISITO + " = " + TIPO_REQUISITO.T_COD_TIPO_REQUISITO    
//       + " JOIN " 
//       + DISPOSTO_NORMATIVO.TABLE_NAME
//       + " ON " + DISPOSTO_NORMATIVO.T_COD_DISPOSTO_NORMATIVO + " = " + REQUISITO.T_COD_DISPOSTO_NORMATIVO
//       + " JOIN " 
//       + TIPO_UNITA_MISURA.TABLE_NAME
//       + " ON " + TIPO_UNITA_MISURA.T_COD_TIPO_UNITA_MISURA + " = " + DETTAGLIO_REQUISITO.T_COD_TIPO_UNITA_MISURA
//       + " WHERE "
//       + REQUISITO.T_DATA_INIZIO_VALIDITA + " <= " + buildGetDate() + " AND (" + REQUISITO.T_DATA_FINE_VALIDITA + " IS NULL OR " + REQUISITO.T_DATA_FINE_VALIDITA + " >= " + buildGetDate() + ")"
//       + " AND "
//       + TIPO_REQUISITO.T_DATA_INIZIO_VALIDITA + " <= " + buildGetDate() + " AND (" + TIPO_REQUISITO.T_DATA_FINE_VALIDITA + " IS NULL OR " + TIPO_REQUISITO.T_DATA_FINE_VALIDITA + " >= " + buildGetDate() + ")"
//       + " AND "
//       + DISPOSTO_NORMATIVO.T_DATA_INIZIO_VALIDITA + " <= " + buildGetDate() + " AND (" + DISPOSTO_NORMATIVO.T_DATA_FINE_VALIDITA + " IS NULL OR " + DISPOSTO_NORMATIVO.T_DATA_FINE_VALIDITA + " >= " + buildGetDate() + ")"
//       + " AND "
//       + TIPO_UNITA_MISURA.T_DATA_INIZIO_VALIDITA + " <= " + buildGetDate() + " AND (" + TIPO_UNITA_MISURA.T_DATA_FINE_VALIDITA + " IS NULL OR " + TIPO_UNITA_MISURA.T_DATA_FINE_VALIDITA + " >= " + buildGetDate() + ")"
//       ;
    
//    private final String GET_DOCUMENTO_REQUISITO_LIST = 
//       "SELECT * "
//       + " FROM "
//       + DOCUMENTO_REQUISITO.TABLE_NAME
//       + " JOIN "
//       + TIPO_DOCUMENTO_REQ.TABLE_NAME
//       + " ON " + TIPO_DOCUMENTO_REQ.T_COD_TIPO_DOC_REQ + " = " + DOCUMENTO_REQUISITO.T_COD_TIPO_DOC_REQ
//       + " JOIN "
//       + TIPO_FONTE_DOCUMENTO.TABLE_NAME
//       + " ON " + TIPO_FONTE_DOCUMENTO.T_COD_TIPO_FONTE_DOC + " = " + TIPO_DOCUMENTO_REQ.T_COD_TIPO_FONTE_DOC
//       + " WHERE "
//       + DOCUMENTO_REQUISITO.T_DATA_INIZIO_VALIDITA + " <= " + buildGetDate() + " AND (" + DOCUMENTO_REQUISITO.T_DATA_FINE_VALIDITA + " IS NULL OR " + DOCUMENTO_REQUISITO.T_DATA_FINE_VALIDITA + " >= " + buildGetDate() + " )"
//       + " AND "
//       + TIPO_DOCUMENTO_REQ.T_DATA_INIZIO_VALIDITA + " <= " + buildGetDate() + " AND (" + TIPO_DOCUMENTO_REQ.T_DATA_FINE_VALIDITA + " IS NULL OR " + TIPO_DOCUMENTO_REQ.T_DATA_FINE_VALIDITA + " >= " + buildGetDate() + " )"
//       + " AND "
//       + TIPO_FONTE_DOCUMENTO.T_DATA_INIZIO_VALIDITA + " <= " + buildGetDate() + " AND (" + TIPO_FONTE_DOCUMENTO.T_DATA_FINE_VALIDITA + " IS NULL OR " + TIPO_FONTE_DOCUMENTO.T_DATA_FINE_VALIDITA + " >= " + buildGetDate() + " )"
//       ;
    
      private final String GET_REQUISITI_OBBLIGATORI_AR = 
          "SELECT "
          + DETTAGLIO_REQUISITO.T_COD_DETT_REQUISITO
          + "," + DETTAGLIO_REQUISITO.T_CODICE
          + "," + DETTAGLIO_REQUISITO.T_DESCRIZIONE
          + "," + TIPO_USO.T_CODICE + " AS " + Costanti.TIPOUSO_CODICE_AS_USO
          + " FROM "
          + DETTAGLIO_REQUISITO.TABLE_NAME
          + " JOIN "
          + TIPO_USO.TABLE_NAME
          + " ON " 
          + DETTAGLIO_REQUISITO.T_COD_TIPO_USO + " = " + TIPO_USO.T_COD_TIPO_USO
          + " WHERE "
          + TIPO_USO.T_CODICE + " = '" + RequisitoGara.TIPO_USO_AR + "'"
          + " AND " + DETTAGLIO_REQUISITO.T_DATA_INIZIO + " <= ?" //+ buildGetDate()
          + " AND (" + DETTAGLIO_REQUISITO.T_DATA_FINE + " IS NULL OR " 
          + DETTAGLIO_REQUISITO.T_DATA_FINE + " >= ?" //+ buildGetDate() 
          + ")"
          + " AND " + TIPO_USO.T_DATA_INIZIO + " <= ?" //+ buildGetDate()
          + " AND (" + TIPO_USO.T_DATA_FINE + " IS NULL OR " 
          + TIPO_USO.T_DATA_FINE + " >= ?" //+ buildGetDate() 
          + ")"
          ;
     
      
      private final String GET_REQUISITI_OBBLIGATORI_USO = 
          "SELECT "
          + DETTAGLIO_REQUISITO.T_COD_DETT_REQUISITO
          + "," + DETTAGLIO_REQUISITO.T_CODICE
          + "," + DETTAGLIO_REQUISITO.T_DESCRIZIONE
          + "," + TIPO_USO.T_CODICE + " AS " + Costanti.TIPOUSO_CODICE_AS_USO
          + " FROM "
          + DETTAGLIO_REQUISITO.TABLE_NAME
          + " JOIN "
          + TIPO_USO.TABLE_NAME
          + " ON " 
          + DETTAGLIO_REQUISITO.T_COD_TIPO_USO + " = " + TIPO_USO.T_COD_TIPO_USO
          + " WHERE "
          + TIPO_USO.T_CODICE + " = ?"
          + " AND " + DETTAGLIO_REQUISITO.T_DATA_INIZIO + " <= ?" //+ buildGetDate()
          + " AND (" + DETTAGLIO_REQUISITO.T_DATA_FINE + " IS NULL OR " 
          + DETTAGLIO_REQUISITO.T_DATA_FINE + " >= ?" //+ buildGetDate() 
          + ")"
          + " AND " + TIPO_USO.T_DATA_INIZIO + " <= ?" //+ buildGetDate()
          + " AND (" + TIPO_USO.T_DATA_FINE + " IS NULL OR " 
          + TIPO_USO.T_DATA_FINE + " >= ?" //+ buildGetDate() 
          + ")"
          ;
      
    private final String GET_REQUISITI_PER_TIPOLOGIA = 
          "SELECT "
          + TIPO_REQUISITO.T_DESCRIZIONE
          + ", " + DETTAGLIO_REQUISITO.T_COD_DETT_REQUISITO 
          + ", " + DETTAGLIO_REQUISITO.T_CODICE 
          + " FROM "
          + DETTAGLIO_REQUISITO.TABLE_NAME
          + " JOIN "
          + REQUISITO.TABLE_NAME
          + " ON "
          + DETTAGLIO_REQUISITO.T_COD_REQUISITO + " = " + REQUISITO.T_COD_REQUISITO
          + " JOIN "
          + TIPO_REQUISITO.TABLE_NAME
          + " ON "
          + REQUISITO.T_COD_TIPO_REQUISITO + " = " + TIPO_REQUISITO.T_COD_TIPO_REQUISITO
          + " WHERE 1 = 1"
          + " AND " + DETTAGLIO_REQUISITO.T_DATA_INIZIO + " <= ?" //+ buildGetDate()
          + " AND (" + DETTAGLIO_REQUISITO.T_DATA_FINE + " IS NULL OR " 
          + DETTAGLIO_REQUISITO.T_DATA_FINE + " >= ?" //+ buildGetDate() 
          + ")"
          + " AND " + REQUISITO.T_DATA_INIZIO_VALIDITA + " <= ?" //+ buildGetDate()
          + " AND (" + REQUISITO.T_DATA_FINE_VALIDITA + " IS NULL OR " 
          + REQUISITO.T_DATA_FINE_VALIDITA + " >= ?" //+ buildGetDate() 
          + ")"          
          + " AND " + TIPO_REQUISITO.T_DATA_INIZIO_VALIDITA + " <= ?" //+ buildGetDate()
          + " AND (" + TIPO_REQUISITO.T_DATA_FINE_VALIDITA + " IS NULL OR " 
          + TIPO_REQUISITO.T_DATA_FINE_VALIDITA + " >= ?" //+ buildGetDate() 
          + ")"
          + " ORDER BY " + TIPO_REQUISITO.T_COD_TIPO_REQUISITO
          ;     

      
//      select TR.COD_TIPO_REQUISITO, TR.DESCRIZIONE, DR.CODICE
//      from simog_mev_30.dbo.DETTAGLIO_REQUISITO DR
//      join simog_mev_30.dbo.REQUISITO R on DR.COD_REQUISITO = R.COD_REQUISITO
//      join simog_mev_30.dbo.TIPO_REQUISITO TR on TR.COD_TIPO_REQUISITO = R.COD_TIPO_REQUISITO
//      order by TR.COD_TIPO_REQUISITO      
    
    
    private final String GET_DETTAGLIO_REQUISITO_USO =
          "SELECT "
          + DETTAGLIO_REQUISITO.T_COD_DETT_REQUISITO
          + "," + DETTAGLIO_REQUISITO.T_DESCRIZIONE
          + " FROM "
          + DETTAGLIO_REQUISITO.TABLE_NAME
          + " JOIN "
          + TIPO_USO.TABLE_NAME
          + " ON " 
          + DETTAGLIO_REQUISITO.T_COD_TIPO_USO + " = " + TIPO_USO.T_COD_TIPO_USO
          + " WHERE "
          + TIPO_USO.T_CODICE + " = ?"
          + " AND " + DETTAGLIO_REQUISITO.T_DATA_INIZIO + " <= ? "
          + " AND (" + DETTAGLIO_REQUISITO.T_DATA_FINE + " IS NULL OR " + DETTAGLIO_REQUISITO.T_DATA_FINE + " >= ?)"
          + " AND " + TIPO_USO.T_DATA_INIZIO + " <= ? "
          + " AND (" + TIPO_USO.T_DATA_FINE + " IS NULL OR " + TIPO_USO.T_DATA_FINE + " >= ?)";
    
    /**************************************************************************************************
     **************************************************************************************************/      		
	
    
    /**
     * Restituisce la lista dei requisiti di una gara 
     * @param idGara
     * @return List<RequisitoGara>
     * @throws SQLException
     */
	public List<RequisitoGara> getRequisitoGaraList( long idGara ) throws SQLException {

	   logger.debug("getRequisitoGaraList [" + idGara + "] Query Eseguita[" + GET_REQUISITI_GARA_BY_ID_GARA + "]");
	   
	   List<RequisitoGara> listaRequisitiGara = new ArrayList<RequisitoGara>();
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
           pstmt = activeConnection.prepareStatement(GET_REQUISITI_GARA_BY_ID_GARA);
           pstmt.setLong(1, idGara);
   
           rs = pstmt.executeQuery();
           
           while( rs.next() ){
              
              RequisitoGara currentRequisitoGara = new RequisitoGara(
                    rs.getLong(REQUISITO_GARA.COD_REQUISITO_GARA),
                    rs.getLong(DETTAGLIO_REQUISITO.COD_DETT_REQUISITO),
                    rs.getString(DETTAGLIO_REQUISITO.CODICE),
                    rs.getString(REQUISITO_GARA.DESCRIZIONE),
                    rs.getString(REQUISITO_GARA.VALORE),
                    rs.getString(REQUISITO_GARA.FLG_CONDIZIONE_ESCLUSIONE),
                    rs.getString(REQUISITO_GARA.FLG_COMPROVA_IN_OFFERTA),
                    rs.getString(REQUISITO_GARA.FLG_AVVALIMENTO),
                    rs.getString(REQUISITO_GARA.FLG_BANDO_TIPO),
                    rs.getString(REQUISITO_GARA.FLG_RISERVATEZZA));
              
              currentRequisitoGara.setTipoUso( rs.getString(Costanti.TIPOUSO_FROM_RESULTSET) );
              
              listaRequisitiGara.add(currentRequisitoGara);
           }
   
           return listaRequisitiGara;
           
       } catch (SQLException sqle) {
          logger.error("Non e' stato possibile recuperare la lista dei requisiti della gara[" + idGara + "]", sqle);
          throw sqle;
       } finally {
           close(rs,pstmt);
       }	   
	   
	}
	
	/**
	 * Restituisce la lista dei requisiti obbligatori di una gara
	 * @param dataRif 
	 * @return List<RequisitoGara>
	 * @throws SQLException
	 */
    public List<RequisitoGara> getRequisitoGaraObbligatorioList(Timestamp dataRif) throws SQLException {
       
       logger.debug("Ricerca requisiti obbligatori - Query Eseguita[" + GET_REQUISITI_OBBLIGATORI_GARA + "]");
   
       List<RequisitoGara> listaRequisitiGara = new ArrayList<RequisitoGara>();
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
          pstmt = activeConnection.prepareStatement(GET_REQUISITI_OBBLIGATORI_GARA);
      	   
          pstmt.setTimestamp(1, dataRif);
          pstmt.setTimestamp(2, dataRif);
          pstmt.setTimestamp(3, dataRif);
          pstmt.setTimestamp(4, dataRif);

          rs = pstmt.executeQuery();
          
          while( rs.next() ){
             
             // is3029_MAC01Active preimpostazione a S del falg avvalimento per i requisiti OM precaricati
             String flagAvv = Costanti.FLAGAVVALIMENTO_N;
             
             if (SimogFlags.is3029_MAC01Active()){
                String tipo = rs.getString(Costanti.TIPOUSO_CODICE_AS_USO);
                
                if(RequisitoGara.TIPO_USO_OM.equals(tipo))
                   flagAvv = Costanti.FLAG_VALORE_SI;
             }
             
             RequisitoGara currentRequisitoGara = new RequisitoGara(
                   Long.MIN_VALUE, // ID Non assegnato 
                   rs.getLong(DETTAGLIO_REQUISITO.COD_DETT_REQUISITO),
                   rs.getString(DETTAGLIO_REQUISITO.CODICE),
                   rs.getString(DETTAGLIO_REQUISITO.DESCRIZIONE),
                   "",
                   Costanti.FLAGESCLUSIONE_N,
                   Costanti.FLAGCOMPROVAOFFERTA_N,
                   flagAvv, // is3029_MAC01Active
                   Costanti.FLAGBANDOTIPO_N,
                   Costanti.FLAGRISERVATEZZA_N);
             
             currentRequisitoGara.setTipoUso( rs.getString(Costanti.TIPOUSO_FROM_RESULTSET) );
             
             listaRequisitiGara.add(currentRequisitoGara);
          }
      	   
          return listaRequisitiGara;
          
      } catch (SQLException sqle) {
         logger.error("Non e' stato possibile recuperare la lista dei requisiti obbligatori", sqle);
         throw sqle;
      } finally {
         close(rs,pstmt);
      }       
   
   }

    /**
     * Restituisce la lista dei requisiti obbligatori di una gara
     * @return List<RequisitoGara>
     * @throws SQLException
     */
    public List<RequisitoGara> getRequisitoGaraObbligatorioListAR(Timestamp dataRif) throws SQLException {
       
       logger.debug("Ricerca requisiti obbligatori AR - Query Eseguita[" + GET_REQUISITI_OBBLIGATORI_AR + "]");
   
       List<RequisitoGara> listaRequisitiGara = new ArrayList<RequisitoGara>();
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
          pstmt = activeConnection.prepareStatement(GET_REQUISITI_OBBLIGATORI_AR);
          pstmt.setTimestamp(1, dataRif);
          pstmt.setTimestamp(2, dataRif);
          pstmt.setTimestamp(3, dataRif);
          pstmt.setTimestamp(4, dataRif);

          rs = pstmt.executeQuery();
          
          while( rs.next() ){
             
             RequisitoGara currentRequisitoGara = new RequisitoGara(
                   Long.MIN_VALUE, // ID Non assegnato 
                   rs.getLong(DETTAGLIO_REQUISITO.COD_DETT_REQUISITO),
                   rs.getString(DETTAGLIO_REQUISITO.CODICE),
                   rs.getString(DETTAGLIO_REQUISITO.DESCRIZIONE),
                   "",
                   Costanti.FLAGESCLUSIONE_N,
                   Costanti.FLAGCOMPROVAOFFERTA_N,
                   Costanti.FLAGAVVALIMENTO_N,
                   Costanti.FLAGBANDOTIPO_N,
                   Costanti.FLAGRISERVATEZZA_N);
             
             currentRequisitoGara.setTipoUso( rs.getString(Costanti.TIPOUSO_FROM_RESULTSET) );
             
             listaRequisitiGara.add(currentRequisitoGara);
          }
           
          return listaRequisitiGara;
          
      } catch (SQLException sqle) {
         logger.error("Non e' stato possibile recuperare la lista dei requisiti obbligatori AR", sqle);
         throw sqle;
      } finally {
         close(rs,pstmt);
      }       
   
   }
	
    /**
     * Restituisce la lista dei requisiti obbligatori di una gara
     * @return List<RequisitoGara>
     * @throws SQLException
     */
    public List<RequisitoGara> getRequisitoGaraUso(String tipoUso, Timestamp dataRif) throws SQLException {
       
       logger.debug("getRequisitoGaraUso- Query Eseguita[" + GET_REQUISITI_OBBLIGATORI_USO + "]");
       
       List<RequisitoGara> listaRequisitiGara = new ArrayList<RequisitoGara>();
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
          pstmt = activeConnection.prepareStatement(GET_REQUISITI_OBBLIGATORI_USO);
           
          pstmt.setString(1, tipoUso);
          pstmt.setTimestamp(2, dataRif);
          pstmt.setTimestamp(3, dataRif);
          pstmt.setTimestamp(4, dataRif);
          pstmt.setTimestamp(5, dataRif);

          
          rs = pstmt.executeQuery();
          
          while( rs.next() ){
             
             RequisitoGara currentRequisitoGara = new RequisitoGara(
                   Long.MIN_VALUE, // ID Non assegnato 
                   rs.getLong(DETTAGLIO_REQUISITO.COD_DETT_REQUISITO),
                   rs.getString(DETTAGLIO_REQUISITO.CODICE),
                   rs.getString(DETTAGLIO_REQUISITO.DESCRIZIONE),
                   "",
                   Costanti.FLAGESCLUSIONE_N,
                   Costanti.FLAGCOMPROVAOFFERTA_N,
                   Costanti.FLAGAVVALIMENTO_N,
                   Costanti.FLAGBANDOTIPO_N,
                   Costanti.FLAGRISERVATEZZA_N);
             
             currentRequisitoGara.setTipoUso( rs.getString(Costanti.TIPOUSO_FROM_RESULTSET) );
             
             listaRequisitiGara.add(currentRequisitoGara);
          }
           
          return listaRequisitiGara;
          
      } catch (SQLException sqle) {
         logger.error("Non e' stato possibile recuperare la lista dei requisiti uso", sqle);
         throw sqle;
      } finally {
         close(rs,pstmt);
      }       
   
   }

    /**
	 * Restituisce la lista degli ID_LOTTO a cui fa riferimento il requisito dell gara
	 * @param idGara
	 * @param codiceRequisitoGara
	 * @return List<Long>
	 * @throws SQLException
	 */
	public List<Long> getListaLottiAssociati( long idGara, RequisitoGara requisitoGara ) throws SQLException {
	   
       logger.debug("getListaLottiAssociati [" + idGara + "] Query Eseguita[" + GET_ID_LOTTO_REQUISITI_GARA + "]");
       
       List<Long> listaLotti = new ArrayList<Long>();
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
          
          pstmt = activeConnection.prepareStatement(GET_ID_LOTTO_REQUISITI_GARA);
          pstmt.setLong(1, idGara);
          pstmt.setLong(2, requisitoGara.getCodice_dettaglio());
          pstmt.setString(3, requisitoGara.getDescrizione() != null ? requisitoGara.getDescrizione() : "");
          pstmt.setString(4, requisitoGara.getValore() != null ? requisitoGara.getValore() : "");
          pstmt.setString(5, requisitoGara.getFlag_esclusione());
          pstmt.setString(6, requisitoGara.getFlag_comprova_offerta());
          pstmt.setString(7, requisitoGara.getFlag_avvalimento());
          pstmt.setString(8, requisitoGara.getFlag_bando_tipo());
          pstmt.setString(9, requisitoGara.getFlag_riservatezza());
          
          rs = pstmt.executeQuery();     
          
          while( rs.next() ){
             listaLotti.add( rs.getLong(REQUISITO_GARA.COD_LOTTO) );
          }
          
          return listaLotti;
          
       } catch (SQLException sqle) {
          logger.error("Non e' stato possibile recuperare la lista degli ID_LOTTO del requisito gara[" + idGara + "]", sqle);
          throw sqle;
       } finally {
           close(rs,pstmt);
       }
	}
	
	/**
	 * Resituisce la lista dei documenti associato ad un requisito gara
	 * @param codiceRequisitoGara
	 * @param targetDate 
	 * @return List<Documento>
	 * @throws SQLException
	 */
	public List<Documento> getDocumentiGaraList( long codiceRequisitoGara, Timestamp targetDate ) throws SQLException {
	   
       logger.debug("Ricerca documenti requisiti gara [" + codiceRequisitoGara + "] Query Eseguita[" + GET_DOCUMENTO_GARA_LIST_BY_REQUISITO + "]");
       
       List<Documento> listaDocumentiRequisitiGara = new ArrayList<Documento>();
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
           pstmt = activeConnection.prepareStatement(GET_DOCUMENTO_GARA_LIST_BY_REQUISITO);
           pstmt.setLong(1, codiceRequisitoGara);
           pstmt.setTimestamp(2, targetDate);
           pstmt.setTimestamp(3, targetDate);

           rs = pstmt.executeQuery();
           
           while( rs.next() ){
              
              Documento currentDocumento = new RequisitoGara().new Documento();
              
              currentDocumento.setCodice_tipo_doc( rs.getLong(DOCUMENTO_REQUISITO_GARA.COD_TIPO_DOC_REQ) );
              currentDocumento.setCodice( rs.getString(TIPO_DOCUMENTO_REQ.CODICE) );
              currentDocumento.setDescrizione_documento( rs.getString(DOCUMENTO_REQUISITO_GARA.DESCRIZIONE_DOCUMENTO) );
              currentDocumento.setEmettitore( rs.getString(DOCUMENTO_REQUISITO_GARA.EMETTITORE) );
              
              currentDocumento.setFax("");
              if( rs.getObject(DOCUMENTO_REQUISITO_GARA.FAX) != null){
                 currentDocumento.setFax( String.valueOf(rs.getObject(DOCUMENTO_REQUISITO_GARA.FAX)) );   
              }
              
              currentDocumento.setTelefono("");
              if( rs.getObject(DOCUMENTO_REQUISITO_GARA.TELEFONO) != null ){
                 currentDocumento.setTelefono( String.valueOf(rs.getObject(DOCUMENTO_REQUISITO_GARA.TELEFONO)) );
              }
              
              currentDocumento.setMail( rs.getString(DOCUMENTO_REQUISITO_GARA.MAIL) );
              currentDocumento.setMail_pec( rs.getString(DOCUMENTO_REQUISITO_GARA.MAIL_PEC) );
              currentDocumento.setObbligatorio(false);
              
              listaDocumentiRequisitiGara.add(currentDocumento);
           }
   
           return listaDocumentiRequisitiGara;
           
       } catch (SQLException sqle) {
          logger.error("Non e' stato possibile recuperare la lista dei documenti del requisito gara[" + codiceRequisitoGara + "]", sqle);
          throw sqle;
       } finally {
           close(rs,pstmt);
       }
	}
	
	/**
	 * Restituisce la lista dei documenti obbligatori di un requisito
	 * @param codiceDettaglioRequisito
	 * @return
	 * @throws SQLException
	 */
    public List<Documento> getDocumentiObbligatoriGaraList( String codiceDettaglioRequisito, Timestamp dataRif ) throws SQLException {
	    
	    logger.debug("Ricerca documenti obblig requisito [" + codiceDettaglioRequisito + "] Query Eseguita[" + GET_DOCUMENTO_REQUISITO_OBBLIGATORIO_LIST + "]");
	    
	    List<Documento> listaDocumentiRequisitiGara = new ArrayList<Documento>();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    try {
	        pstmt = activeConnection.prepareStatement(GET_DOCUMENTO_REQUISITO_OBBLIGATORIO_LIST);
	        pstmt.setString(1, codiceDettaglioRequisito);
           pstmt.setTimestamp(2, dataRif);
           pstmt.setTimestamp(3, dataRif);
           pstmt.setTimestamp(4, dataRif);
           pstmt.setTimestamp(5, dataRif);

	        rs = pstmt.executeQuery();
	        
	        while( rs.next() ){
	           
	           Documento currentDocumento = new RequisitoGara().new Documento();
	           
	           currentDocumento.setCodice_tipo_doc( rs.getLong(DOCUMENTO_REQUISITO.COD_TIPO_DOC_REQ) );
	           currentDocumento.setCodice( rs.getString(TIPO_DOCUMENTO_REQ.CODICE) );
	           currentDocumento.setDescrizione_documento( rs.getString(TIPO_DOCUMENTO_REQ.DESCRIZIONE) );
	           currentDocumento.setEmettitore("");
	           currentDocumento.setFax("");
	           currentDocumento.setTelefono("");
	           currentDocumento.setMail("");
	           currentDocumento.setMail_pec("");
	           currentDocumento.setObbligatorio(true);
	           
	           listaDocumentiRequisitiGara.add(currentDocumento);
	        }
	
	        return listaDocumentiRequisitiGara;
	        
	    } catch (SQLException sqle) {
	       logger.error("Non e' stato possibile recuperare la lista dei documenti obbligatori del requisito [" + codiceDettaglioRequisito + "]", sqle);
	       throw sqle;
	    } finally {
	        close(rs,pstmt);
	    }
	 }

	
    
	/**
	 * Restituisce la lista dei documenti
	 * @param timestamp 
	 * @return List<Documento>
	 * @throws SQLException
	 */
    public List<Documento> getDocumentiList(Timestamp targetDate) throws SQLException {
       
       logger.debug("Ricerca documenti - Query Eseguita[" + GET_DOCUMENTO_REQUISITO_LIST + "]");
       
       List<Documento> listaDocumenti = new ArrayList<Documento>();
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
           pstmt = activeConnection.prepareStatement(GET_DOCUMENTO_REQUISITO_LIST);
           pstmt.setTimestamp(1, targetDate);
           pstmt.setTimestamp(2, targetDate);

           rs = pstmt.executeQuery();
           
           while( rs.next() ){
              
              Documento currentDocumento = new RequisitoGara().new Documento();
              
              currentDocumento.setCodice_tipo_doc( rs.getLong(TIPO_DOCUMENTO_REQ.COD_TIPO_DOC_REQ) );
              currentDocumento.setCodice( rs.getString(TIPO_DOCUMENTO_REQ.CODICE) );
              currentDocumento.setDescrizione_documento( rs.getString(TIPO_DOCUMENTO_REQ.DESCRIZIONE) );
              currentDocumento.setObbligatorio(true);
              
              listaDocumenti.add(currentDocumento);
           }
   
           return listaDocumenti;
           
       } catch (SQLException sqle) {
          logger.error("Non e' stato possibile recuperare la lista dei documenti", sqle);
          throw sqle;
       } finally {
           close(rs,pstmt);
       }
    }
	
    /**
     * Restituisce la lista dei documenti da fonte OE
    * @param dataRiferimento 
     * @return List<Documento>
     * @throws SQLException
     */
    public List<String> getCodiceDocumentiFonteOEList(Timestamp dataRiferimento) throws SQLException {
       
       logger.debug("Ricerca documenti fonteoelist- Query Eseguita[" + GET_DOCUMENTO_REQUISITO_FONTE_OE_LIST + "]");
       
       List<String> listaCodiceDocumenti = new ArrayList<String>();
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
           pstmt = activeConnection.prepareStatement(GET_DOCUMENTO_REQUISITO_FONTE_OE_LIST);

           pstmt.setTimestamp(1, dataRiferimento);
           pstmt.setTimestamp(2, dataRiferimento);

           rs = pstmt.executeQuery();
           
           while( rs.next() ){
              String codiceDocumento = rs.getString(TIPO_DOCUMENTO_REQ.CODICE);
              listaCodiceDocumenti.add(codiceDocumento);
           }
   
           return listaCodiceDocumenti;
       } catch (SQLException sqle) {
          logger.error("Non e' stato possibile recuperare la lista dei codici documenti di fonte OE", sqle);
          throw sqle;
       } finally {
           close(rs,pstmt);
       }
    }
    
   /**
    * Restituisce una mappa con il codice e la descrizione dei requisiti facoltativi
    * @param targetDate
    * @return Map<String,String>
    * @throws SQLException
    */
	public Map<String,String> getRequisitiFacoltativiMap(Timestamp targetDate) throws SQLException {
	   return getRequisitiMapByTipoUso(targetDate, GET_DETTAGLIO_REQUISITO_FACOLTATIVO_LIST);
	}
	
   /**
    * Restituisce una mappa con il codice e la descrizione dei requisiti obbligatori
	* @param targetDate
	* @return Map<String,String>
	* @throws SQLException
	*/
	public Map<String,String> getRequisitiObbligatoriMap(Timestamp targetDate) throws SQLException {
	   return getRequisitiMapByTipoUso(targetDate, GET_DETTAGLIO_REQUISITO_OBBLIGATORIO_LIST);
	}
	
   /**
	* Restituisce una mappa con il codice e il tipo uso dei requisiti facoltativi
	* @param targetDate
	* @return Map<String,String>
	* @throws SQLException
	*/
	public Map<String,String> getRequisitiFacoltativiUsoMap(Timestamp targetDate) throws SQLException {
	   return getRequisitiMapByTipoUso(targetDate, GET_DETTAGLIO_REQUISITO_FACOLTATIVO_USO_LIST);
	}
	
   /**
	* Restituisce una mappa con il codice e la descrizione
	* @param targetDate
	* @return Map<String,String>
	* @throws SQLException
	*/
	private Map<String,String> getRequisitiMapByTipoUso(Timestamp targetDate, String query) throws SQLException {
	   
       logger.debug("getRequisitiMapByTipoUso [" + targetDate + "] Query Eseguita[" + query + "]");
       
       Map<String, String> tipologicaMap = new HashMap<String, String>();
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
           pstmt = activeConnection.prepareStatement(query);
           pstmt.setTimestamp(1, targetDate);
           pstmt.setTimestamp(2, targetDate);
           pstmt.setTimestamp(3, targetDate);
           pstmt.setTimestamp(4, targetDate);
   
           rs = pstmt.executeQuery();
           
           while( rs.next() ){
              String codice = String.valueOf(rs.getInt(1) 
                    + (PSReq.CODICE_REQUISITO_NON_CODIFICATO.equals(rs.getString(3)) ? PSReq.MARKER_999 : 0));
              String descrizione = rs.getString(2);
              tipologicaMap.put(codice, descrizione);
           }
   
           return tipologicaMap;
           
       } catch (SQLException sqle) {
          logger.error("Non e' stato possibile recuperare la lista dei requisiti", sqle);
          throw sqle;
       } finally {
           close(rs,pstmt);
       }      
	}
	
   /**
   * Restituisce una mappa con il codice e la descrizione per una data tipologia
   * @param targetDate
   * @return Map<String,String>
   * @throws SQLException
   */
   public Map<String,String> getRequisitiMapUso(Timestamp targetDate, String tipoUso) throws SQLException {

      logger.debug("getRequisitiMapUso [" + targetDate + "] Query Eseguita[" + GET_DETTAGLIO_REQUISITO_USO + "]");

       Map<String, String> tipologicaMap = new HashMap<String, String>();
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
           pstmt = activeConnection.prepareStatement(GET_DETTAGLIO_REQUISITO_USO);
           pstmt.setString(1, tipoUso);
           pstmt.setTimestamp(2, targetDate);
           pstmt.setTimestamp(3, targetDate);
           pstmt.setTimestamp(4, targetDate);
           pstmt.setTimestamp(5, targetDate);
   
           rs = pstmt.executeQuery();
           
           while( rs.next() ){
              String codice = rs.getString(1);
              String descrizione = rs.getString(2);
              tipologicaMap.put(codice, descrizione);
           }
   
           return tipologicaMap;
           
       } catch (SQLException sqle) {
          logger.error("Non e' stato possibile recuperare la lista dei requisiti", sqle);
          throw sqle;
       } finally {
           close(rs,pstmt);
       }      
   }
   
    /**
     * Restituisce una mappa con il codice e la descrizione
     * @param targetDate
     * @return Map<String,String>
     * @throws SQLException
     */
	public Map<String,String> getRequisitiMap(Timestamp targetDate) throws SQLException {	   
	   return this.getTipologica(
	         DETTAGLIO_REQUISITO.TABLE_NAME,
	         DETTAGLIO_REQUISITO.COD_DETT_REQUISITO,
	         DETTAGLIO_REQUISITO.DESCRIZIONE,
	         DETTAGLIO_REQUISITO.DATA_INIZIO,
	         DETTAGLIO_REQUISITO.DATA_FINE,
	         targetDate);
	}
	
	
	/**
     * Restituisce una mappa di relazione codice - codice_dettaglio
     * @return Map<String,String>
     * @throws SQLException
     */
    public Map<String,String> getCodiceMap(Timestamp targetDate) throws SQLException {
       
       return this.getTipologica(
             DETTAGLIO_REQUISITO.TABLE_NAME,
             DETTAGLIO_REQUISITO.COD_DETT_REQUISITO,
             DETTAGLIO_REQUISITO.CODICE,
             DETTAGLIO_REQUISITO.DATA_INIZIO,
             DETTAGLIO_REQUISITO.DATA_FINE,
             targetDate);
    }
    
    /**
     * Restituisce la mappa delle tipologie di documenti
     * @param targetDate
     * @return Map<String,String>
     * @throws SQLException
     */
    public Map<String,String> getCodiceDocMap(Timestamp targetDate) throws SQLException {
       
       return this.getTipologica(
             TIPO_DOCUMENTO_REQ.TABLE_NAME,
             TIPO_DOCUMENTO_REQ.CODICE,
             TIPO_DOCUMENTO_REQ.DESCRIZIONE,
             TIPO_DOCUMENTO_REQ.DATA_INIZIO,
             TIPO_DOCUMENTO_REQ.DATA_FINE,
             targetDate);
    }

	/**
     * Restituisce una mappa di relazione codice - codice_dettaglio
     * @return Map<String,String>
     * @throws SQLException
     */
    public Map<String,String> getCodiceDettaglioReqMap(Timestamp targetDate) throws SQLException {
       
       return this.getTipologica(
             DETTAGLIO_REQUISITO.TABLE_NAME,
             DETTAGLIO_REQUISITO.COD_DETT_REQUISITO,
             DETTAGLIO_REQUISITO.CODICE,
             DETTAGLIO_REQUISITO.DATA_INIZIO,
             DETTAGLIO_REQUISITO.DATA_FINE,
             targetDate);
    }

	/**
     * Restituisce una mappa di relazione cod_tipo_doc_req - codice per la tabella TIPO_DOCUMENTO_REQ
     * @return Map<String,String>
     * @throws SQLException
     */
    public Map<String,String> getCodiceTipoDocReqMap(Timestamp targetDate) throws SQLException {
       
       return this.getTipologica(
    		 TIPO_DOCUMENTO_REQ.TABLE_NAME,
    		 TIPO_DOCUMENTO_REQ.COD_TIPO_DOC_REQ,
    		 TIPO_DOCUMENTO_REQ.CODICE,
    		 TIPO_DOCUMENTO_REQ.DATA_INIZIO,
    		 TIPO_DOCUMENTO_REQ.DATA_FINE,
             targetDate);
    }
    
    
    
    /**
     * Resituisce la mappa dei requisiti per tipologa
     * <descrizione_tipolgia, codice_requisito>
    * @param currentDatetime 
     * @return
     * @throws SQLException
     */
    public Map<String,String> getRequisitiPerTipologiaMap(Timestamp dataRif) throws SQLException {
       
       logger.debug("Ricerca dettaglio requisiti per tipologia - Query Eseguita[" + GET_REQUISITI_PER_TIPOLOGIA + "]");
       
       Map<String, String> tipologicaMap = new HashMap<String, String>();
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
           pstmt = activeConnection.prepareStatement(GET_REQUISITI_PER_TIPOLOGIA);
           pstmt.setTimestamp(1, dataRif);
           pstmt.setTimestamp(2, dataRif);
           pstmt.setTimestamp(3, dataRif);
           pstmt.setTimestamp(4, dataRif);
           pstmt.setTimestamp(5, dataRif);
           pstmt.setTimestamp(6, dataRif);

           rs = pstmt.executeQuery();
           
           while( rs.next() ){
              String descrizioneTipologia = rs.getString(1);
              String codiceRequisito = String.valueOf(rs.getInt(2) 
                    + (PSReq.CODICE_REQUISITO_NON_CODIFICATO.equals(rs.getString(3)) ? PSReq.MARKER_999 : 0));
              tipologicaMap.put(codiceRequisito, descrizioneTipologia);
           }
   
           return tipologicaMap;
           
       } catch (SQLException sqle) {
          logger.error("Non e' stato possibile recuperare la mappa dei requisiti per tipologia", sqle);
          throw sqle;
       } finally {
           close(rs,pstmt);
       }      
    }    
    
    /**************************************************************************************************
     **************************************************************************************************/     
	
//    private final String REVOCA_REQUISITO_GARA_BY_GARA = 
//        "UPDATE "
//        + REQUISITO_GARA.TABLE_NAME
//	      + " SET "
//	      + REQUISITO_GARA.DATA_REVOCA + " = ? "
//	      + " WHERE "
//	      + REQUISITO_GARA.COD_GARA + " = ? "
//	      + " AND " + REQUISITO_GARA.COD_DETT_REQUISITO + " = ? "
//	      + " AND " + buildISNULL(REQUISITO_GARA.DESCRIZIONE, "") + " = ? "
//	      + " AND " + buildISNULL(REQUISITO_GARA.VALORE, "") + " = ? "
//	      + " AND " + REQUISITO_GARA.FLG_CONDIZIONE_ESCLUSIONE + " = ? "
//	      + " AND " + REQUISITO_GARA.FLG_COMPROVA_IN_OFFERTA + " = ? "
//	      + " AND " + REQUISITO_GARA.FLG_AVVALIMENTO + " = ? "
//	      + " AND " + REQUISITO_GARA.FLG_BANDO_TIPO + " = ? "
//	      + " AND " + REQUISITO_GARA.FLG_RISERVATEZZA + " = ? "
//        + " AND " + REQUISITO_GARA.DATA_REVOCA + " IS NULL "
//	      ;

    private final String REVOCA_REQUISITO_GARA_BY_GARA = 
         "UPDATE "
         + REQUISITO_GARA.TABLE_NAME
         + " SET "
         + REQUISITO_GARA.DATA_REVOCA + " = ? "
         + " WHERE "
         + REQUISITO_GARA.COD_GARA + " = ? "
         + " AND " + REQUISITO_GARA.DATA_REVOCA + " IS NULL "
         ;
    
    private final String REVOCA_REQUISITO_GARA_BY_GARALOTTO = 
         "UPDATE "
         + REQUISITO_GARA.TABLE_NAME
         + " SET "
         + REQUISITO_GARA.DATA_REVOCA + " = ? "
         + " WHERE "
         + REQUISITO_GARA.COD_GARA + " = ? "
         + " AND " + REQUISITO_GARA.COD_LOTTO + " = ? "
         + " AND " + REQUISITO_GARA.DATA_REVOCA + " IS NULL "
         ;

    private final String REVOCA_REQUISITO_GARA_BY_GARA_AND_TIPOUSO = 
         "UPDATE "
         + REQUISITO_GARA.TABLE_NAME
         + " SET "
         + REQUISITO_GARA.DATA_REVOCA + " = ? "
         + " FROM "
         + REQUISITO_GARA.TABLE_NAME
         + " JOIN " 
         + DETTAGLIO_REQUISITO.TABLE_NAME
         + " ON " + REQUISITO_GARA.T_COD_DETT_REQUISITO + " = " + DETTAGLIO_REQUISITO.T_COD_DETT_REQUISITO
         + " JOIN " 
         + TIPO_USO.TABLE_NAME
         + " ON " + TIPO_USO.T_COD_TIPO_USO + " = " + DETTAGLIO_REQUISITO.T_COD_TIPO_USO   
         + " WHERE "
         + REQUISITO_GARA.COD_GARA + " = ? "
         + " AND " + TIPO_USO.T_CODICE + " = ? "  
         + " AND " + REQUISITO_GARA.DATA_REVOCA + " IS NULL " 
         ;    
    
   	private final String ASSOCIAZIONE_REQUISITO_GARA_LOTTO = 
	      "INSERT INTO " + REQUISITO_GARA.TABLE_NAME + "("
	      + REQUISITO_GARA.DESCRIZIONE
	      + "," + REQUISITO_GARA.VALORE
	      + "," + REQUISITO_GARA.DATA_INIZIO_VALIDITA
	      + "," + REQUISITO_GARA.FLG_CONDIZIONE_ESCLUSIONE
	      + "," + REQUISITO_GARA.FLG_COMPROVA_IN_OFFERTA
	      + "," + REQUISITO_GARA.FLG_AVVALIMENTO
	      + "," + REQUISITO_GARA.FLG_BANDO_TIPO
	      + "," + REQUISITO_GARA.FLG_RISERVATEZZA
	      + "," + REQUISITO_GARA.COD_GARA
	      + "," + REQUISITO_GARA.COD_DETT_REQUISITO
	      + "," + REQUISITO_GARA.COD_LOTTO
	      + "," + REQUISITO_GARA.MASTER
	      + ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"
	      ;
	
	private final String ASSOCIAZIONE_REQUISITO_GARA_DOCUMENTO = 
	      "INSERT INTO " + DOCUMENTO_REQUISITO_GARA.TABLE_NAME + "("
	      + DOCUMENTO_REQUISITO_GARA.COD_REQUISITO_GARA
	      + "," + DOCUMENTO_REQUISITO_GARA.COD_TIPO_DOC_REQ
	      + "," + DOCUMENTO_REQUISITO_GARA.DESCRIZIONE_DOCUMENTO
	      + "," + DOCUMENTO_REQUISITO_GARA.EMETTITORE
	      + "," + DOCUMENTO_REQUISITO_GARA.TELEFONO
	      + "," + DOCUMENTO_REQUISITO_GARA.FAX
	      + "," + DOCUMENTO_REQUISITO_GARA.MAIL
	      + "," + DOCUMENTO_REQUISITO_GARA.MAIL_PEC
	      + ") VALUES (?,?,?,?,?,?,?,?)"
	      ;
	
	private final String GET_PK_REQUISTI_NOT_MASTER = 
	      "SELECT "
	      + REQUISITO_GARA.COD_REQUISITO_GARA 
	      + "," + REQUISITO_GARA.COD_DETT_REQUISITO
	      + " FROM "
	      + REQUISITO_GARA.TABLE_NAME
	      + " WHERE "
	      + REQUISITO_GARA.T_DATA_REVOCA + " IS NULL "
	      + " AND "
	      + REQUISITO_GARA.T_MASTER + " IS NULL "
	      + " AND "
	      + REQUISITO_GARA.T_COD_GARA + " = ? "
	      ;
	   
	
    private final String DELETE_REQUISITO_GARA_BY_GARA = 
          "DELETE FROM "
          + REQUISITO_GARA.TABLE_NAME
          + " WHERE "
          + REQUISITO_GARA.COD_GARA + " = ? "
          + " AND " + REQUISITO_GARA.DATA_REVOCA + " IS NULL "
          ;
     
     private final String DELETE_REQUISITO_GARA_BY_GARALOTTO = 
          "DELETE FROM "
          + REQUISITO_GARA.TABLE_NAME
          + " WHERE "
          + REQUISITO_GARA.COD_GARA + " = ? "
          + " AND " + REQUISITO_GARA.COD_LOTTO + " = ? "
          + " AND " + REQUISITO_GARA.DATA_REVOCA + " IS NULL "
          ;

      private final String DELETE_REQUISITO_GARA_BY_GARA_AND_TIPOUSO = 
         "DELETE " 
         + REQUISITO_GARA.TABLE_NAME
         + " FROM "
         + REQUISITO_GARA.TABLE_NAME
         + " JOIN " 
         + DETTAGLIO_REQUISITO.TABLE_NAME
         + " ON " + REQUISITO_GARA.T_COD_DETT_REQUISITO + " = " + DETTAGLIO_REQUISITO.T_COD_DETT_REQUISITO
         + " JOIN " 
         + TIPO_USO.TABLE_NAME
         + " ON " + TIPO_USO.T_COD_TIPO_USO + " = " + DETTAGLIO_REQUISITO.T_COD_TIPO_USO   
         + " WHERE "
         + REQUISITO_GARA.COD_GARA + " = ? "
         + " AND " + TIPO_USO.T_CODICE + " = ? "  
         + " AND " + REQUISITO_GARA.DATA_REVOCA + " IS NULL " 
         ;	
     
      private final String DELETE_DOCUMENTI_REQUISITO_BY_MASTER = 
         "DELETE " 
         + DOCUMENTO_REQUISITO_GARA.TABLE_NAME
         + " FROM "
         + DOCUMENTO_REQUISITO_GARA.TABLE_NAME
         + " JOIN "
         + REQUISITO_GARA.TABLE_NAME
         + " ON "
         + DOCUMENTO_REQUISITO_GARA.T_COD_REQUISITO_GARA + " = " + REQUISITO_GARA.T_COD_REQUISITO_GARA
         + " WHERE "
         + REQUISITO_GARA.COD_GARA + " = ? "
         + " AND "
         + REQUISITO_GARA.DATA_REVOCA + " IS NULL "
         ;
      
      private final String DELETE_DOCUMENTI_REQUISITO_AR_BY_MASTER = 
         "DELETE " 
         + DOCUMENTO_REQUISITO_GARA.TABLE_NAME
         + " FROM "
         + DOCUMENTO_REQUISITO_GARA.TABLE_NAME
         + " JOIN "
         + REQUISITO_GARA.TABLE_NAME
         + " ON " 
         + DOCUMENTO_REQUISITO_GARA.T_COD_REQUISITO_GARA + " = " + REQUISITO_GARA.T_COD_REQUISITO_GARA
         + " JOIN " 
         + DETTAGLIO_REQUISITO.TABLE_NAME
         + " ON " 
         + REQUISITO_GARA.T_COD_DETT_REQUISITO + " = " + DETTAGLIO_REQUISITO.T_COD_DETT_REQUISITO
         + " JOIN " 
         + TIPO_USO.TABLE_NAME
         + " ON " 
         + TIPO_USO.T_COD_TIPO_USO + " = " + DETTAGLIO_REQUISITO.T_COD_TIPO_USO   
         + " WHERE "
         + REQUISITO_GARA.COD_GARA + " = ? "
         + " AND " 
         + TIPO_USO.T_CODICE + " = ? "  
         + " AND " 
         + REQUISITO_GARA.DATA_REVOCA + " IS NULL " 
         ;      

//	/**
//	 * Setta la data revoca dei requisiti di una gara
//	 * @param listaRequisitiGara
//	 * @param idGara
//	 * @return int Numero record aggiornati
//	 * @throws SQLException
//	 */
//	public int revocaRequisitiGara( List<RequisitoGara> listaRequisitiGara, long idGara) throws SQLException {
//	   
//       logger.debug("Revoca requisiti gara [" + idGara + "] - Query Eseguita[" + REVOCA_REQUISITO_GARA_BY_GARA + "]");
//       
//       PreparedStatement pstmt = null;
//       int updated = 0;
//
//       try{	 
//          
//          pstmt = activeConnection.prepareStatement(REVOCA_REQUISITO_GARA_BY_GARA);
//          pstmt.setTimestamp(1, getNow());
//          pstmt.setLong(2, idGara);
//          
//          for( RequisitoGara currentRequisito: listaRequisitiGara ){
//
//             pstmt.setLong(3, currentRequisito.getCodice_dettaglio());
//             
//             pstmt.setString(4, currentRequisito.getDescrizione() != null ? currentRequisito.getDescrizione() : "");
//             pstmt.setString(5, currentRequisito.getValore() != null ? currentRequisito.getValore() : "");
//             
//             pstmt.setString(6, currentRequisito.getFlag_esclusione());
//             pstmt.setString(7, currentRequisito.getFlag_comprova_offerta());
//             pstmt.setString(8, currentRequisito.getFlag_avvalimento());
//             pstmt.setString(9, currentRequisito.getFlag_bando_tipo());
//             pstmt.setString(10, currentRequisito.getFlag_riservatezza());
//             
//             updated += pstmt.executeUpdate();
//          }
//          
//          return updated;
//          
//       } catch (SQLException sqle) {
//          logger.error("Non e' stato possibile revocare i requisiti della gara[" + idGara + "]", sqle);
//          throw sqle;
//       } finally {
//          pstmt.close();
//       }	   
//	}
	   
  /**
   * Revoca i requisiti di una gara. 
   * La cancellazione puo' essere logica o fisica.
   * @param listaRequisitiGara
   * @param idGara
   * @return int Numero record aggiornati
   * @throws SQLException
   */     
  public int revocaRequisitiGara( long idGara, boolean logica ) throws SQLException {
     
       String query = logica ? REVOCA_REQUISITO_GARA_BY_GARA : DELETE_REQUISITO_GARA_BY_GARA;
     
//       logger.debug("Revoca requisiti gara [" + idGara + "] - Query Eseguita[" + query + "]");
       
       PreparedStatement pstmt = null;
       int updated = 0;

       try{    
          int i = 0;
          pstmt = activeConnection.prepareStatement(query);
          if(logica)
             pstmt.setTimestamp(++i, getNow());
          pstmt.setLong(++i, idGara);
          
          updated += pstmt.executeUpdate();
          
          return updated;
          
       } catch (SQLException sqle) {
          String tipoRevoca = logica ? "logica" : "fisica";
          logger.error("Non e' possibile la revoca " + tipoRevoca + " dei requisiti della gara[" + idGara + "]", sqle);
          throw sqle;
       } finally {
          pstmt.close();
       }     
  }

	/**
	 * Revoca i requisiti della gara identificata dall'id passato come parametro ed in base al tipo d'uso specificato.
	 * La cancellazione puo' essere logica o fisica.
	 * @param listaRequisitiGara
	 * @param idGara
	 * @param tipoUso
	 * @throws SQLException
	 */
	public void revocaRequisitiGaraByGaraAndTipoUso( long idGara, String tipoUso, boolean logica ) throws SQLException {
	   
	   String query = logica ? REVOCA_REQUISITO_GARA_BY_GARA_AND_TIPOUSO : DELETE_REQUISITO_GARA_BY_GARA_AND_TIPOUSO;
	   
//       logger.debug("Revoca requisiti gara [" + idGara + "] associati al tipo d'uso [" + tipoUso + "] - Query Eseguita[" + query + "]");
       
       PreparedStatement pstmt = null;

       try{	 
          int i = 0;
          pstmt = activeConnection.prepareStatement(query);
          if(logica)
             pstmt.setTimestamp(++i, getNow());
          pstmt.setLong(++i, idGara);
          pstmt.setString(++i, tipoUso);
          pstmt.executeUpdate();
	
       } catch (SQLException sqle) {
          String tipoRevoca = logica ? "logica" : "fisica";
          logger.error("Non e' possibile la revoca " + tipoRevoca + " dei requisiti della gara [" + idGara + "] associati al tipo d'uso [" + tipoUso + "]", sqle);
          throw sqle;
       } finally {
          pstmt.close();
       }	   
	}
	
	/**
	 * Inserisce un requisito ad una gara (per ogni lotto)
	 * @param listaRequisitiGara
	 * @param idGara
	 * @return int Numero di record inseriti
	 * @throws SQLException
	 */
	public int insertRequisitiGara( List<RequisitoGara> listaRequisitiGara, long idGara ) throws SQLException {
	   
//       logger.debug("Update requisiti gara [" + idGara + "] - Query Eseguita[" + ASSOCIAZIONE_REQUISITO_GARA_LOTTO + "]");
       
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       int inserted = 0;

       try{
          
          pstmt = activeConnection.prepareStatement(
                createInsertQuery(ASSOCIAZIONE_REQUISITO_GARA_LOTTO, REQUISITO_GARA.COD_REQUISITO_GARA));
          
          for( RequisitoGara currentRequisito: listaRequisitiGara ){
             
             int i = 0;
             pstmt.setString(++i, currentRequisito.getDescrizione());
             pstmt.setString(++i, currentRequisito.getValore());
             pstmt.setTimestamp(++i, getNow());
             pstmt.setString(++i, currentRequisito.getFlag_esclusione());
             pstmt.setString(++i, currentRequisito.getFlag_comprova_offerta());
             pstmt.setString(++i, currentRequisito.getFlag_avvalimento());
             pstmt.setString(++i, currentRequisito.getFlag_bando_tipo());
             pstmt.setString(++i, currentRequisito.getFlag_riservatezza());
            
             pstmt.setLong(++i, idGara);
             pstmt.setLong(++i, currentRequisito.getCodice_dettaglio());
             
             i++;
             
             if( currentRequisito.getLotti_associati().size() == 0 ){
                pstmt.setNull(i, Types.BIGINT);
                pstmt.setString(i+1, Costanti.TIPO_REK_MASTER);
                pstmt.executeQuery();
                inserted++;
             } 
             else {
                int count = currentRequisito.getLotti_associati().size();
                for( Long currentIdLotto: currentRequisito.getLotti_associati() ){
                   pstmt.setLong(i, currentIdLotto);
                   pstmt.setNull(i+1, Types.VARCHAR);
                   if( --count == 0 ){ pstmt.setString(i+1, Costanti.TIPO_REK_MASTER); }
                   pstmt.execute();
                   inserted++;
                }
             }
             
             rs = pstmt.getResultSet();
             if( rs.next() ){
                currentRequisito.setCodice_requisito_gara( rs.getLong(REQUISITO_GARA.COD_REQUISITO_GARA) );
             } else {
                throw new SQLException("Non e' stato possibile recuperare il nuovo codice per il requisito di codice[" + currentRequisito.getCodice_requisito_gara() + "]");
             }
             
          }
          
          return inserted;
          
       } catch (SQLException sqle) {
          logger.error("Non e' stato possibile aggiornare i requisiti della gara[" + idGara + "]", sqle);
          throw sqle;
       } finally {
          pstmt.close();
       }
       
	}
	
	/**
	 * Inserisce i documenti di ogni requisito di una gara
	 * @param listaRequisitiGara
	 * @param idGara
	 * @return
	 * @throws SQLException
	 */
	public int insertDocumentiRequisito( List<RequisitoGara> listaRequisitiGara, long idGara ) throws SQLException {
	       
//	   logger.debug("Update documenti requisiti gara [" + idGara + "] - Query Eseguita[" + ASSOCIAZIONE_REQUISITO_GARA_DOCUMENTO + "]");
	       
	   PreparedStatement pstmt = null;
	   int inserted = 0;

	   try{
	          pstmt = activeConnection.prepareStatement(ASSOCIAZIONE_REQUISITO_GARA_DOCUMENTO);
	          
	          for( RequisitoGara currentRequisito: listaRequisitiGara ){
	             
	             List<Documento> listaDocumenti = currentRequisito.getDocumenti();
	             
	             for( Documento currentDocumento: listaDocumenti ){
	                
      	             int i = 0;
      	             pstmt.setLong(++i, currentRequisito.getCodice_requisito_gara());
      	             pstmt.setLong(++i, currentDocumento.getCodice_tipo_doc());
      	             pstmt.setString(++i, currentDocumento.getDescrizione_documento());
                     pstmt.setString(++i, currentDocumento.getEmettitore());
                     
                     if( !isEmpty( currentDocumento.getTelefono() ) ){
                        pstmt.setInt(++i, Integer.parseInt(currentDocumento.getTelefono()) );
                     } else {
                        pstmt.setNull(++i, Types.NUMERIC);
                     }
                     
                     if( !isEmpty( currentDocumento.getFax() ) ){
                        pstmt.setInt(++i, Integer.parseInt(currentDocumento.getFax()) );
                     } else {
                        pstmt.setNull(++i, Types.NUMERIC);
                     }
                     
                     pstmt.setString(++i, currentDocumento.getMail());
                     pstmt.setString(++i, currentDocumento.getMail_pec());
                     
                     inserted += pstmt.executeUpdate();
	             }
	          }
	          return inserted;
	          
	   } catch (SQLException sqle) {
	       logger.error("Non e' stato possibile aggiornare i documenti dei requisiti della gara[" + idGara + "]", sqle);
	       throw sqle;
	   } finally {
	       pstmt.close();
	   }
	}
	
   /**
    * Restituisce una mappa con il codice e la descrizione
    * 
    * @param targetDate
    * @return Map<String,String>
    * @throws SQLException
    */
   public String getDescTipoUso(long idDett) throws SQLException {

      String query =
            "SELECT "
            + TIPO_USO.T_DESCRIZIONE
            + " FROM "
            + DETTAGLIO_REQUISITO.TABLE_NAME
            + " JOIN "
            + TIPO_USO.TABLE_NAME
            + " ON " 
            + DETTAGLIO_REQUISITO.T_COD_TIPO_USO + " = " + TIPO_USO.T_COD_TIPO_USO
            + " WHERE "
            + DETTAGLIO_REQUISITO.COD_DETT_REQUISITO + " = ?"
            ;
         
      String retVal = "";

      logger.debug("getDescTipoUso - Query Eseguita[" + query + "]");      
      
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
         pstmt = activeConnection.prepareStatement(query);
         pstmt.setLong(1, idDett);

         rs = pstmt.executeQuery();

         if (rs.next()) {
            retVal = rs.getString(1);
         }

         return retVal;

      } catch (SQLException sqle) {
         logger.error(
               "Non e' stato possibile recuperare la descrizione del tipo uso", sqle);
         throw sqle;
      } finally {
         close(rs, pstmt);
      }
   }
	
	private boolean isEmpty(String field){
	   return field == null || "".equals(field);
	}
	
	/**
	 * Revoca l'associazione requisito gara - lotto 
	 * La cancellazione puo' essere logica o fisica.
	 * @param idGara
	 * @param idLotto
	 * @return
	 * @throws SQLException
	 */
	public int revocaRequisitiLotto( long idGara, long idLotto, boolean logica) throws SQLException {
	     
	   String query = logica ? REVOCA_REQUISITO_GARA_BY_GARALOTTO : DELETE_REQUISITO_GARA_BY_GARALOTTO;
	   
//       logger.debug("Revoca requisiti lotto [" + idGara + "] - Query Eseguita[" + query + "]");
       
       PreparedStatement pstmt = null;
       int updated = 0;
       try{    
          int i = 0;
          pstmt = activeConnection.prepareStatement(query);
          if(logica)
             pstmt.setTimestamp(++i, getNow());
          pstmt.setLong(++i, idGara);
          pstmt.setLong(++i, idLotto);
          
          updated = pstmt.executeUpdate();
          
          return updated;
          
       } catch (SQLException sqle) {
          String tipoRevoca = logica ? "logica" : "fisica";
          logger.error("Non e' possibile la revoca " + tipoRevoca + " dei requisiti del lotto [" + idLotto + "] gara[" + idGara + "]", sqle);
          throw sqle;
       } finally {
          pstmt.close();
       }     
	}	

    /**
     * Restituisce la lista dei requisiti di una gara 
     * @param idGara
     * @return List<RequisitoGara>
     * @throws SQLException
     */
    public TableBean getRequisitiNonMaster( long idGara ) throws SQLException {

       logger.debug("Ricerca requisiti non master [" + idGara + "] Query Eseguita[" + GET_PK_REQUISTI_NOT_MASTER + "]");
       
       TableBean listaRequisitiGara = null;

       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
           pstmt = activeConnection.prepareStatement(GET_PK_REQUISTI_NOT_MASTER);
           pstmt.setLong(1, idGara);
   
           rs = pstmt.executeQuery();
           
           listaRequisitiGara = new TableBean(rs);

           return listaRequisitiGara;
           
       } catch (SQLException sqle) {
          logger.error("Non e' stato possibile recuperare la lista dei requisiti non master della gara[" + idGara + "]", sqle);
          throw sqle;
       } finally {
           close(rs,pstmt);
       }       
       
    }
   
    /**
     * Eliminazione dei documenti di un requisito di una gara
     * @param idGara
     * @return
     * @throws SQLException
     */
    public int deleteDocumenti( long idGara ) throws SQLException {
       
//       logger.debug("Eliminazione documenti dei requisiti della gara[" + idGara + "] - Query Eseguita[" + DELETE_DOCUMENTI_REQUISITO_BY_MASTER + "]");
       
       PreparedStatement pstmt = null;
       int updated = 0;
       try{    
          pstmt = activeConnection.prepareStatement(DELETE_DOCUMENTI_REQUISITO_BY_MASTER);
          pstmt.setLong(1, idGara);
          
          updated = pstmt.executeUpdate();
          
          return updated;
          
       } catch (SQLException sqle) {
          logger.error("Non e' stato possibile eliminare i documenti del requisito della gara[" + idGara + "]", sqle);
          throw sqle;
       } finally {
          pstmt.close();
       }        
    }
    
    /**
     * Eliminazione dei documenti di un requisito di una gara del tipo uso specificato
     * @param idGara
     * @return
     * @throws SQLException
     */
    public int deleteDocumentiByTipoUso( long idGara, String tipoUso ) throws SQLException {
       
//       logger.debug("Eliminazione documenti dei requisiti della gara[" + idGara + "] tipo uso[" + tipoUso + "]- Query Eseguita[" + DELETE_DOCUMENTI_REQUISITO_AR_BY_MASTER + "]");
       
       PreparedStatement pstmt = null;
       int updated = 0;
       try{    
          pstmt = activeConnection.prepareStatement(DELETE_DOCUMENTI_REQUISITO_AR_BY_MASTER);
          pstmt.setLong(1, idGara);
          pstmt.setString(2, tipoUso);
          
          updated = pstmt.executeUpdate();
          
          return updated;
          
       } catch (SQLException sqle) {
          logger.error("Non e' stato possibile eliminare i documenti del requisito della gara[" + idGara + "] tipo uso [" + tipoUso + "]", sqle);
          throw sqle;
       } finally {
          pstmt.close();
       }        
    }    
}
