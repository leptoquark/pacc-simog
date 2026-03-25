package it.avcp.simog.manager.cup;

import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.cup.CupLottoAgg;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.generated.CUP_LOTTO_AGG;
import it.avlp.simog.db.generated.LOTTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class CupLottoAggManager extends AccessiDB {

   /**
    * Costruttore
    * 
    * @param currentActiveConnection
    * @param logger
    */
   public CupLottoAggManager(Connection currentActiveConnection, Logger logger) {
      super(currentActiveConnection, logger);
   }
   
/*
 * DELETE CUP
 ********************************************************   
 */

   private final static String WHERE_STATO =  "    AND (" + CUP_LOTTO_AGG.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
         +" OR " + CUP_LOTTO_AGG.T_ID_STATO + " = " + StatiScheda.CONFERMATO+")";

   private static String LOGIC_DELETED_CUP_LOTTO =
      "UPDATE "
      + CUP_LOTTO_AGG.TABLE_NAME
      + " SET "
      + CUP_LOTTO_AGG.DATA_FINE_CUP + " = ?,"
      + CUP_LOTTO_AGG.ID_STATO + " = ?"
      ;

   private static String DELETE_CUP_LOTTO =  
      "DELETE FROM " 
      + CUP_LOTTO_AGG.TABLE_NAME
      ;
         
   private static String DELETE_CUP_LOTTO_COND =  
      " WHERE " 
      + CUP_LOTTO_AGG.ID_LOTTO + " = ? " // AND " + CUP_LOTTO_AGG.DATA_FINE_CUP + " IS NULL"
      + WHERE_STATO
      ; 
   
   private static String DELETE_CUP_LOTTO_AGG_COND = 
      " WHERE " 
      + CUP_LOTTO_AGG.ID_AGGIUDICAZIONE + " = ?" 
      + " AND " 
      + CUP_LOTTO_AGG.DATA_INIZIO_AGG + " = ? " // AND " + CUP_LOTTO_AGG.DATA_FINE_CUP + " IS NULL"
      + WHERE_STATO
      ;      
   
   
   /**
    * Cancella tutti i CUP di un lotto
    * 
    * @param idLotto
    * @param idAggiudicazione
    * @param dataInizioAggiudicazione
    * @return
    * @throws SQLException
    */
   public int deleteCup(Long idLotto, boolean logic) throws SQLException {
      String query = (logic ? LOGIC_DELETED_CUP_LOTTO : DELETE_CUP_LOTTO) + DELETE_CUP_LOTTO_COND;
      return _deleteCup(query, idLotto, null, null, logic);
   }
   
   /**
    * Cancella tutti i CUP di una aggiudicazione
    * 
    * @param idLotto
    * @param idAggiudicazione
    * @param dataInizioAggiudicazione
    * @return
    * @throws SQLException
    */
   public int deleteCup(Long idAggiudicazione, Timestamp dataInizioAggiudicazione, boolean logic) throws SQLException {
      String query = (logic ? LOGIC_DELETED_CUP_LOTTO : DELETE_CUP_LOTTO) + DELETE_CUP_LOTTO_AGG_COND;
      return _deleteCup(query, null, idAggiudicazione, dataInizioAggiudicazione, logic);
   }   
   
   
   public int _deleteCup(String query, Long idLotto, Long idAggiudicazione, Timestamp dataInizioAggiudicazione, boolean logic) throws SQLException {

      logger.debug("Elimina CUP del lotto[" + idLotto + "] aggiudicazione[" + idAggiudicazione + "] Query Eseguita[" + query + "]");   
      
      PreparedStatement pstmt = null;
      
      try 
      {
         int idx = 0;
         pstmt = activeConnection.prepareStatement(query);
         
         if(logic){
            pstmt.setTimestamp(++idx, getNow());
            pstmt.setInt(++idx, StatiScheda.ANNULLATO);
         }
         if( idLotto != null )
            pstmt.setLong(++idx, idLotto.longValue());
         
         if( idAggiudicazione != null )
            pstmt.setLong(++idx, idAggiudicazione.longValue());
         
         if( dataInizioAggiudicazione != null )
            pstmt.setTimestamp(++idx, dataInizioAggiudicazione);
         
         return pstmt.executeUpdate();
         
      }
      finally{
         close(null, pstmt);
      }
      
   }
   
   
/*
 * INSERT CUP
 ********************************************************   
 */  
   
   private static String ADD_CUP_LOTTO_AGG = 
     "INSERT INTO " 
      + CUP_LOTTO_AGG.TABLE_NAME 
      + "( " + CUP_LOTTO_AGG.DATA_INIZIO_CUP 
      + ", " + CUP_LOTTO_AGG.CUP 
      + ", " + CUP_LOTTO_AGG.ID_LOTTO 
      + ", " + CUP_LOTTO_AGG.ID_AGGIUDICAZIONE 
      + ", " + CUP_LOTTO_AGG.DATA_INIZIO_AGG
      + ", " + CUP_LOTTO_AGG.OK_UTENTE
      + ", " + CUP_LOTTO_AGG.ID_STATO
      + ", " + CUP_LOTTO_AGG.DATA_FINE_CUP
      + ") VALUES ("
      + "?, ?, ?, ?, ?, ?, ?, ?)";
   
   
   /**
    * Inserisci CUP
    * 
    * @param elencoCup
    * @return
    * @throws SQLException
    */
   public int addCup(List<CupLottoAggExt> elencoCup, boolean confermato) throws SQLException {
      
      logger.debug("Inserisci elenco CUP | Query Eseguita[" + ADD_CUP_LOTTO_AGG + "]");   
      
      PreparedStatement pstmt = null;
      try
      {
         int created = 0;
         pstmt = activeConnection.prepareStatement(ADD_CUP_LOTTO_AGG);
         
         if( elencoCup != null ){
            for(CupLottoAgg currentCup: elencoCup)
            {
               int idx = 0;
               pstmt.setTimestamp(++idx, getNow());
               pstmt.setString(++idx, currentCup.getCup());
               pstmt.setLong(++idx, currentCup.getIdLotto());
               
               if( currentCup.getIdAggiudicazione() != null )
                  pstmt.setLong(++idx, currentCup.getIdAggiudicazione());
               else
                  pstmt.setNull(++idx, Types.BIGINT);
               
               if( currentCup.getDataInizioAgg() != null )
                  pstmt.setTimestamp(++idx, currentCup.getDataInizioAgg());
               else
                  pstmt.setNull(++idx, Types.TIMESTAMP);
               
               pstmt.setString(++idx, currentCup.getOkUtente() == null ? Costanti.FLAG_VALORE_NO : currentCup.getOkUtente());
               
               pstmt.setInt(++idx, confermato ? StatiScheda.CONFERMATO : StatiScheda.IN_DEFINIZIONE);
               
               if( confermato )
                  pstmt.setTimestamp(++idx, getNow());
               else
                  pstmt.setNull(++idx, Types.TIMESTAMP);
   
               created = pstmt.executeUpdate();
            }
         }
         return created;
      }
      finally {
         close(null, pstmt);
      }
   }
   
   
/*
 * UPDATE CUP
 * - aggiorna OK_UTENTE
 * - aggiorna ID_AGGIUDICAZIONE e DATA_INIZIO_AGG
 ********************************************************   
 */  
   
   private static String UPDATE_OK_UTENTE = 
     "UPDATE " 
     + CUP_LOTTO_AGG.TABLE_NAME
     + " SET "
     + CUP_LOTTO_AGG.OK_UTENTE + " = ? "
     + " WHERE " 
     + CUP_LOTTO_AGG.ID_LOTTO + " = ?"
     + " AND "
     + CUP_LOTTO_AGG.CUP + " = ?"
     + WHERE_STATO;
     
   
   public int confirmValidCup(List<CupLottoAggExt> listaCupValidi) throws SQLException {
      
      logger.debug("Conferma CUP validi | Query Eseguita[" + UPDATE_OK_UTENTE + "]"); 
      
      PreparedStatement pstmt = null;
      int updated = 0;
      
      if(listaCupValidi == null) return 0;
      
      try 
      {
         pstmt = activeConnection.prepareStatement(UPDATE_OK_UTENTE);
         
         for(CupLottoAgg currentCup: listaCupValidi)
         {
            int idx = 0;
            pstmt.setString(++idx, currentCup.getOkUtente());
            pstmt.setLong(++idx, currentCup.getIdLotto());
            pstmt.setString(++idx, currentCup.getCup());
            
            updated = pstmt.executeUpdate();
         }
         
         return updated;
      }
      finally{
         close(null, pstmt);
      }
   }
   
   private static String UPDATE_CUP_DATI_AGG = 
      "UPDATE " 
      + CUP_LOTTO_AGG.TABLE_NAME
      + " SET " + CUP_LOTTO_AGG.ID_AGGIUDICAZIONE + " = ?"
      + "," + CUP_LOTTO_AGG.DATA_INIZIO_AGG + " = ?"
      + " WHERE " 
      + CUP_LOTTO_AGG.ID_LOTTO + " = ?"
      + WHERE_STATO
      ;
   
   
   /**
    * Aggiunge ai cup del lotto le informazioni sull'aggiudicazione
    * - idAggiudicazione
    * - dataInizioAggiudicazione
    * 
    * @param idLotto
    * @param idAggiudicazione
    * @param dataInizioAgg
    * @return
    * @throws SQLException
    */
   public int completaDatiAggCup(Long idLotto, Long idAggiudicazione, Timestamp dataInizioAgg) throws SQLException {
      
      logger.debug("Completa dati CUP | Query Eseguita[" + UPDATE_CUP_DATI_AGG + "]"); 
      
      PreparedStatement pstmt = null;
      int updated = 0;
      
      try 
      {
         pstmt = activeConnection.prepareStatement(UPDATE_CUP_DATI_AGG);
         
         int idx = 0;
         
         if( idAggiudicazione != null )
            pstmt.setLong(++idx, idAggiudicazione);
         else
            pstmt.setNull(++idx, Types.BIGINT);
         
         if( dataInizioAgg != null )
            pstmt.setTimestamp(++idx, dataInizioAgg);
         else
            pstmt.setNull(++idx, Types.TIMESTAMP);
         
         pstmt.setLong(++idx, idLotto);
         
         updated = pstmt.executeUpdate();
         
         return updated;
      }
      finally{
         close(null, pstmt);
      }
   }
   
   private static String RIPRISTINA_CUP = 
         "UPDATE " 
         + CUP_LOTTO_AGG.TABLE_NAME
         + " SET "
         + CUP_LOTTO_AGG.ID_STATO + " = ? "
         + "," + CUP_LOTTO_AGG.DATA_FINE_CUP + " = null "
         + " WHERE " 
         + CUP_LOTTO_AGG.ID_LOTTO + " = ?";         
       
   private static String RIPRISTINA_CUP_CONF = 
         "UPDATE " 
         + CUP_LOTTO_AGG.TABLE_NAME
         + " SET "
         + CUP_LOTTO_AGG.ID_STATO + " = ? "
         + " WHERE " 
         + CUP_LOTTO_AGG.ID_LOTTO + " = ?";         

   public int ripristinaCup(Long idLotto, int stato) throws SQLException {
       
       
       PreparedStatement pstmt = null;
       int updated = 0;
       
       try 
       {
          pstmt = activeConnection.prepareStatement(
                stato == StatiScheda.CONFERMATO ? RIPRISTINA_CUP_CONF: RIPRISTINA_CUP);
          
          pstmt.setLong(1, stato);
          pstmt.setLong(2, idLotto);
          updated = pstmt.executeUpdate();
          
          return updated;
       }
       finally{
          close(null, pstmt);
       }
    }

   private static String SGANCIA_CUP_DATI_AGG_LOTTO = 
         "UPDATE " 
         + CUP_LOTTO_AGG.TABLE_NAME
         + " SET " + CUP_LOTTO_AGG.ID_AGGIUDICAZIONE + " = null"
         + "," + CUP_LOTTO_AGG.DATA_INIZIO_AGG + " = null"
         + " WHERE " 
         + CUP_LOTTO_AGG.ID_AGGIUDICAZIONE + " = ?"
         + " AND " 
         + CUP_LOTTO_AGG.DATA_INIZIO_AGG + " = ?"
         + WHERE_STATO
         ;

   
   /**
    * Setta a null i campi idAgiudicazione e dataInizioAggiudicazione del CUP
    * 
    * @param idLotto
    * @return
    * @throws SQLException
    */
   public int cancellaDatiAggCup(Long idAggiudicazione, Timestamp dataInizioAgg) throws SQLException {
      
      logger.debug("Sgancia i dati CUP dall'aggiudicazione[" + idAggiudicazione +"] dataInzio[" + dataInizioAgg + "] | Query Eseguita[" + SGANCIA_CUP_DATI_AGG_LOTTO + "]"); 
      
      PreparedStatement pstmt = null;
      int updated = 0;
      
      try 
      {
         pstmt = activeConnection.prepareStatement(SGANCIA_CUP_DATI_AGG_LOTTO);
         
         int idx = 0;
         
         pstmt.setLong(++idx, idAggiudicazione);
         pstmt.setTimestamp(++idx, dataInizioAgg);
         
         updated = pstmt.executeUpdate();
         
         return updated;
      }
      finally{
         close(null, pstmt);
      }
   }
   
/*
 * LISTA CUP
 ********************************************************   
 */      
     
   private static String ELENCO_CUP_BY_LOTTO =  
     "SELECT "
     + CUP_LOTTO_AGG.ID_CUP_LOTTO_AGG
     + "," + CUP_LOTTO_AGG.DATA_INIZIO_CUP
     + "," + CUP_LOTTO_AGG.DATA_FINE_CUP
     + "," + CUP_LOTTO_AGG.ID_LOTTO
     + "," + CUP_LOTTO_AGG.ID_AGGIUDICAZIONE
     + "," + CUP_LOTTO_AGG.DATA_INIZIO_AGG
     + "," + CUP_LOTTO_AGG.CUP
     + "," + CUP_LOTTO_AGG.OK_UTENTE
     + "," + CUP_LOTTO_AGG.ID_STATO
     + " FROM " 
     + CUP_LOTTO_AGG.TABLE_NAME
     + " WHERE "
     + CUP_LOTTO_AGG.ID_LOTTO + " = ?"  
     ;

   private static String ELENCO_CUP_BY_LOTTO_AGG =  
     ELENCO_CUP_BY_LOTTO
     + " OR "
     + "("
     + CUP_LOTTO_AGG.ID_AGGIUDICAZIONE + " = ?" 
     + " AND " 
     + CUP_LOTTO_AGG.DATA_INIZIO_AGG + " = ?"
     + WHERE_STATO
     + ")"
     ;
     
     
   /**
    * Restiuisce l'elenco dei CUP di un lotto o di una aggiudicazione
    * 
    * @param idLotto
    * @param idAggiudicazione
    * @param dataInizioAggiudicazioneR
    * @return
    * @throws SQLException
    */
   public List<CupLottoAggExt> getElencoCup(Long idLotto, Long idAggiudicazione, Timestamp dataInizioAggiudicazione, boolean ignoraStato) throws SQLException {
      
      String query = (idAggiudicazione == null ? ELENCO_CUP_BY_LOTTO : ELENCO_CUP_BY_LOTTO_AGG) + (ignoraStato ? "" : WHERE_STATO);
      
      logger.debug("Elenco CUP del lotto[" + idLotto + "] aggiudicazione[" + idAggiudicazione + "] dataInizio[" + dataInizioAggiudicazione + "] Query Eseguita[" + query + "]");  
      List<CupLottoAggExt> elencoCup = new LinkedList<CupLottoAggExt>();
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try
      {
         pstmt = activeConnection.prepareStatement(query);
         pstmt.setLong(1, idLotto == null ? 0 : idLotto.longValue());
         
         if( idAggiudicazione != null )
            pstmt.setLong(2, idAggiudicazione.longValue());

         if( dataInizioAggiudicazione != null )
            pstmt.setTimestamp(3, dataInizioAggiudicazione);       
         
         rs = pstmt.executeQuery();
         
         while( rs.next() ){
            CupLottoAggExt item = new CupLottoAggExt();
            item.setIdCupLottoAgg(rs.getLong(CUP_LOTTO_AGG.ID_CUP_LOTTO_AGG));
            item.setDataInizioCup(rs.getTimestamp(CUP_LOTTO_AGG.DATA_INIZIO_CUP));
            item.setDataFineCup(rs.getTimestamp(CUP_LOTTO_AGG.DATA_FINE_CUP));
            item.setIdLotto(rs.getLong(CUP_LOTTO_AGG.ID_LOTTO));
            item.setIdAggiudicazione((Long)rs.getObject(CUP_LOTTO_AGG.ID_AGGIUDICAZIONE));
            item.setDataInizioAgg(rs.getTimestamp(CUP_LOTTO_AGG.DATA_INIZIO_AGG));
            item.setCup(rs.getString(CUP_LOTTO_AGG.CUP));
            item.setOkUtente(rs.getString(CUP_LOTTO_AGG.OK_UTENTE));
            item.setIdStato(rs.getInt(CUP_LOTTO_AGG.ID_STATO));
            elencoCup.add(item);
         }
         
         return elencoCup;
      }
      finally{
         close(rs, pstmt);
      }
      
   }
   
  
   private static String ELENCO_CUP_BY_GARA =  
      "SELECT "
      + CUP_LOTTO_AGG.T_ID_CUP_LOTTO_AGG
      + "," + CUP_LOTTO_AGG.T_DATA_INIZIO_CUP
      + "," + CUP_LOTTO_AGG.T_DATA_FINE_CUP
      + "," + CUP_LOTTO_AGG.T_ID_LOTTO
      + "," + CUP_LOTTO_AGG.T_ID_AGGIUDICAZIONE
      + "," + CUP_LOTTO_AGG.T_DATA_INIZIO_AGG
      + "," + CUP_LOTTO_AGG.T_CUP
      + "," + CUP_LOTTO_AGG.T_OK_UTENTE
      + "," + CUP_LOTTO_AGG.ID_STATO
      + "," + LOTTO.T_CIG
      + "," + LOTTO.T_CIG_KKK
      + " FROM " + CUP_LOTTO_AGG.TABLE_NAME
      + "," + LOTTO.TABLE_NAME
      + " WHERE "
      + CUP_LOTTO_AGG.T_ID_LOTTO + " = " + LOTTO.T_ID_LOTTO
      //+ " AND "
      //+ CUP_LOTTO_AGG.DATA_FINE_CUP + " IS NULL"
      + WHERE_STATO
      + " AND "
      + LOTTO.T_ID_GARA + " = ?"
      + " ORDER BY "
      + LOTTO.T_CIG + "," + LOTTO.CIG_KKK
      ;   
   //MAC 34162 3.04.8.1
   private static String ELENCO_CUP_BY_GARA_NO_LOTTI_CANCELLATI =  
		      "SELECT "
		      + CUP_LOTTO_AGG.T_ID_CUP_LOTTO_AGG
		      + "," + CUP_LOTTO_AGG.T_DATA_INIZIO_CUP
		      + "," + CUP_LOTTO_AGG.T_DATA_FINE_CUP
		      + "," + CUP_LOTTO_AGG.T_ID_LOTTO
		      + "," + CUP_LOTTO_AGG.T_ID_AGGIUDICAZIONE
		      + "," + CUP_LOTTO_AGG.T_DATA_INIZIO_AGG
		      + "," + CUP_LOTTO_AGG.T_CUP
		      + "," + CUP_LOTTO_AGG.T_OK_UTENTE
		      + "," + CUP_LOTTO_AGG.ID_STATO
		      + "," + LOTTO.T_CIG
		      + "," + LOTTO.T_CIG_KKK
		      + " FROM " + CUP_LOTTO_AGG.TABLE_NAME
		      + "," + LOTTO.TABLE_NAME
		      + " WHERE "
		      + CUP_LOTTO_AGG.T_ID_LOTTO + " = " + LOTTO.T_ID_LOTTO
		      + " AND "
		      + LOTTO.DATA_CANCELLAZIONE_LOTTO + " IS NULL"
		      + WHERE_STATO
		      + " AND "
		      + LOTTO.T_ID_GARA + " = ?"
		      + " ORDER BY "
		      + LOTTO.T_CIG + "," + LOTTO.CIG_KKK
		      ;    
   /**
    * Restiuisce l'elenco dei CUP di una gara
    * 
    * @param idGara
    * @return
    * @throws SQLException
    */
   public List<CupLottoAggExt> getElencoCupGara(Long idGara) throws SQLException {
      
      logger.debug("Elenco CUP della gara[" + idGara + "] Query Eseguita[" + ELENCO_CUP_BY_GARA + "]");  
      
      List<CupLottoAggExt> elencoCup = new LinkedList<CupLottoAggExt>();
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try
      {
         pstmt = activeConnection.prepareStatement(ELENCO_CUP_BY_GARA);
         pstmt.setLong(1, idGara.longValue());
        
         rs = pstmt.executeQuery();
         
         while( rs.next() ){
            CupLottoAggExt item = new CupLottoAggExt();
            item.setIdCupLottoAgg(rs.getLong(CUP_LOTTO_AGG.ID_CUP_LOTTO_AGG));
            item.setDataInizioCup(rs.getTimestamp(CUP_LOTTO_AGG.DATA_INIZIO_CUP));
            item.setDataFineCup(rs.getTimestamp(CUP_LOTTO_AGG.DATA_FINE_CUP));
            item.setIdLotto(rs.getLong(CUP_LOTTO_AGG.ID_LOTTO));
            item.setIdAggiudicazione((Long)rs.getObject(CUP_LOTTO_AGG.ID_AGGIUDICAZIONE));
            item.setDataInizioAgg(rs.getTimestamp(CUP_LOTTO_AGG.DATA_INIZIO_AGG));
            item.setCup(rs.getString(CUP_LOTTO_AGG.CUP));
            item.setOkUtente(rs.getString(CUP_LOTTO_AGG.OK_UTENTE));
            item.setCig(rs.getString(LOTTO.CIG) + rs.getString(LOTTO.CIG_KKK));
            item.setIdStato(rs.getInt(CUP_LOTTO_AGG.ID_STATO));
            elencoCup.add(item);
         }
         
         return elencoCup;
      }
      finally{
         close(rs, pstmt);
      }
      
   }
   /**
    * MAC 34162 3.04.8.1
    * Restiuisce l'elenco dei CUP di una gara di lotti non cancellati
    * 
    * @param idGara
    * @return
    * @throws SQLException
    */
   public List<CupLottoAggExt> getElencoCupGaraNoLottiCancellati(Long idGara) throws SQLException {
      
      logger.debug("Elenco CUP della gara[" + idGara + "] Query Eseguita[" + ELENCO_CUP_BY_GARA + "]");  
      
      List<CupLottoAggExt> elencoCup = new LinkedList<CupLottoAggExt>();
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try
      {
         pstmt = activeConnection.prepareStatement(ELENCO_CUP_BY_GARA_NO_LOTTI_CANCELLATI);
         pstmt.setLong(1, idGara.longValue());
        
         rs = pstmt.executeQuery();
         
         while( rs.next() ){
            CupLottoAggExt item = new CupLottoAggExt();
            item.setIdCupLottoAgg(rs.getLong(CUP_LOTTO_AGG.ID_CUP_LOTTO_AGG));
            item.setDataInizioCup(rs.getTimestamp(CUP_LOTTO_AGG.DATA_INIZIO_CUP));
            item.setDataFineCup(rs.getTimestamp(CUP_LOTTO_AGG.DATA_FINE_CUP));
            item.setIdLotto(rs.getLong(CUP_LOTTO_AGG.ID_LOTTO));
            item.setIdAggiudicazione((Long)rs.getObject(CUP_LOTTO_AGG.ID_AGGIUDICAZIONE));
            item.setDataInizioAgg(rs.getTimestamp(CUP_LOTTO_AGG.DATA_INIZIO_AGG));
            item.setCup(rs.getString(CUP_LOTTO_AGG.CUP));
            item.setOkUtente(rs.getString(CUP_LOTTO_AGG.OK_UTENTE));
            item.setCig(rs.getString(LOTTO.CIG) + rs.getString(LOTTO.CIG_KKK));
            item.setIdStato(rs.getInt(CUP_LOTTO_AGG.ID_STATO));
            elencoCup.add(item);
         }
         
         return elencoCup;
      }
      finally{
         close(rs, pstmt);
      }
      
   }
   /*FINE MAC 34162 */
    
   /**
    * Aggiorna l'elenco dei CUP di un lotto
    * 
    * @param lotto
    * @return
    * @throws SQLException
    */
   public boolean updateElencoCup(Lotto lotto, boolean confermato) throws SQLException {
      int deleted = deleteCup(lotto.getId_Lotto(),  false);
      int created = addCup(lotto.getElencoCup(), confermato);
      return created > 0;
   }
   
   /**
    * Aggiorna l'elenco dei CUP di una aggiudicazione
    * 
    * @param elencoCup
    * @param idAggiudicazione
    * @param dataInizioAggiudicazione
    * @return
    * @throws SQLException
    */
   public boolean updateElencoCup(List<CupLottoAggExt> elencoCup, Long idAggiudicazione, Timestamp dataInizioAggiudicazione, boolean confermato) throws SQLException {
      int deleted = deleteCup(idAggiudicazione, dataInizioAggiudicazione, false);
      int created = addCup(elencoCup, confermato);
      return created > 0;
   }   
   
   /**
    * Settaggio di ID_LOTTO nell'elenco dei CUP di un lotto
    * 
    * @param lotto
    */
   public void settingIdLotto(Lotto lotto){
      if(lotto.getElencoCup() != null){
         for(CupLottoAgg item: lotto.getElencoCup()){
            item.setIdLotto(lotto.getId_Lotto());
         }
      }
   }
   
   
   /**
    * metodo per la storicizzazione di un record
    * 
    * @param id_record String
    * @param data_inizio_record Timestamp
    * @param vecchiaData Timestamp
    * @return boolean
    * @throws SQLException
    */
   public boolean copyRecord(String id_record, Timestamp data_inizio_record, Timestamp vecchiaData) throws SQLException{
      String QUERY_UPDATE_OLD_RECORD =
         "UPDATE "+CUP_LOTTO_AGG.TABLE_NAME+ " SET "
         + CUP_LOTTO_AGG.ID_STATO+ " = ?,"
         + CUP_LOTTO_AGG.DATA_INIZIO_AGG + " = ? "
         
         +" WHERE "
         +CUP_LOTTO_AGG.T_ID_AGGIUDICAZIONE+" = ?"
         +" AND "+CUP_LOTTO_AGG.T_DATA_INIZIO_AGG + " = ?"
         +" AND "+CUP_LOTTO_AGG.T_ID_STATO + "=" + StatiScheda.CONFERMATO;
      
      String QUERY_COPY_RECORD =
      "INSERT INTO "+CUP_LOTTO_AGG.TABLE_NAME+" ("
         + CUP_LOTTO_AGG.ID_CUP_LOTTO_AGG
         +","+CUP_LOTTO_AGG.ID_AGGIUDICAZIONE
         +","+CUP_LOTTO_AGG.DATA_INIZIO_AGG
         +","+CUP_LOTTO_AGG.CUP
         +","+CUP_LOTTO_AGG.ID_LOTTO
         +","+CUP_LOTTO_AGG.OK_UTENTE
         +","+CUP_LOTTO_AGG.DATA_INIZIO_CUP
         +","+CUP_LOTTO_AGG.DATA_FINE_CUP
         +","+CUP_LOTTO_AGG.ID_STATO
         +" ) "
         +"SELECT "
         + CUP_LOTTO_AGG.ID_CUP_LOTTO_AGG
         +","+CUP_LOTTO_AGG.ID_AGGIUDICAZIONE
         +","+CUP_LOTTO_AGG.DATA_INIZIO_AGG
         +","+CUP_LOTTO_AGG.CUP
         +","+CUP_LOTTO_AGG.ID_LOTTO
         +","+CUP_LOTTO_AGG.OK_UTENTE
         +", ?"
         +", ?"
         +", ?"
         +" FROM "+CUP_LOTTO_AGG.TABLE_NAME
         +" WHERE "
         +CUP_LOTTO_AGG.ID_AGGIUDICAZIONE + " = ? AND "
         +CUP_LOTTO_AGG.DATA_INIZIO_AGG + " = ?"
         + " AND " + CUP_LOTTO_AGG.ID_STATO + " = " + StatiScheda.CONFERMATO;
         
      PreparedStatement stmt = null;
      PreparedStatement stmt2 = null;
      try{
         int index = 1;
         stmt = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD,CUP_LOTTO_AGG.TABLE_NAME));
         stmt.setTimestamp(index++, getNow()); //data_inizio_cup
         stmt.setNull(index++, Types.TIMESTAMP); // data_fine_cup
         stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE); //stato scheda
         stmt.setLong(index++, Long.parseLong(id_record));
         stmt.setTimestamp(index++, data_inizio_record);
         int rowsCopied = stmt.executeUpdate();
         if(rowsCopied > 0){
            index = 1;
            stmt2 = activeConnection.prepareStatement(QUERY_UPDATE_OLD_RECORD);
            stmt2.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA); //stato scheda
            stmt2.setTimestamp(index++, vecchiaData); //data_inizio_aggiudicazione
            stmt2.setLong(index++, Long.parseLong(id_record));
            stmt2.setTimestamp(index++, data_inizio_record);
            rowsCopied = stmt2.executeUpdate();
            return (rowsCopied>0);
         }
         else {
            logger.debug("CUP_LOTTO_AGG_MANAGER.copyRecord: Nessun record da copiare");
            return true;
         }
      
      }
      finally{
         close(null, stmt2);
         close(null, stmt);
      }
   }     
   
   
   private static String QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_CUP = 
      "UPDATE "
      + CUP_LOTTO_AGG.TABLE_NAME
      + " SET " + CUP_LOTTO_AGG.ID_STATO + " = ?,"
      + CUP_LOTTO_AGG.DATA_FINE_CUP + " = " + buildGetDate() +
      "  WHERE "
      + CUP_LOTTO_AGG.ID_AGGIUDICAZIONE + " = ?"
      + " AND " 
      + CUP_LOTTO_AGG.DATA_INIZIO_AGG + " = ?";

   /**
    * metodo per l'aggiornamento di un record
    * 
    * @param idRecord String
    * @param dataInizioRecord Timestamp
    * @param stato_scheda String
    * @return int - affected row count
    * @throws SQLException
    */
   public int updateRecord(String idRecord, Timestamp dataInizioRecord, String stato_scheda ) throws SQLException{
      
      int numRow = -1; 
      PreparedStatement stmt = null;
      ResultSet rs = null;
      try {
         stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_CUP);
         logger.debug("query per l'UPDATE del record con richiesta annullamento:::" + QUERY_UPDATE_RECORD_RICH_ANNULLAMENTO_CUP);

         stmt.setString(1, stato_scheda);
         logger.debug(1 + ": "+stato_scheda);
         
         stmt.setInt(2, Integer.parseInt(idRecord));
         logger.debug(2 + ": "+idRecord);
         
         stmt.setTimestamp(3,dataInizioRecord);
         logger.debug(3 + ": "+dataInizioRecord);
         numRow = stmt.executeUpdate();
      } finally {
         close(rs, stmt);
      }
      return numRow;
   }

   private static String QUERY_SBLOCCA = 
         "UPDATE "
         + CUP_LOTTO_AGG.TABLE_NAME
         + " SET "
         + CUP_LOTTO_AGG.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE
         + "," + CUP_LOTTO_AGG.DATA_FINE_CUP + " = null"
         + " WHERE " 
         + CUP_LOTTO_AGG.ID_LOTTO + " IN ("
         + "SELECT " + LOTTO.ID_LOTTO + " FROM " + LOTTO.TABLE_NAME
            + " WHERE " + LOTTO.ID_GARA + " = ? AND " + LOTTO.DATA_CANCELLAZIONE_LOTTO + " IS NULL )";   

   public void sbloccaCup(long id_Gara) throws SQLException {
      PreparedStatement stmt = activeConnection.prepareStatement(QUERY_SBLOCCA);
      logger.debug("querysbloccaCUP: " + QUERY_SBLOCCA);

      try{
         stmt.setLong(1, id_Gara);
         
         stmt.execute();
      }finally{
         close(null,stmt);
      }   
   }  
   
}
