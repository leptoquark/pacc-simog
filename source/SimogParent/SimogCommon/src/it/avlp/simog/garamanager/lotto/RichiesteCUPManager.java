package it.avlp.simog.garamanager.lotto;


import it.avlp.simog.beans.RichiestaCUP;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.RICHIESTE_CUP;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class RichiesteCUPManager extends AccessiDB {

   private final String LETTURA = "SELECT * "
         + " FROM "
         + RICHIESTE_CUP.TABLE_NAME
         + " WHERE "
         + RICHIESTE_CUP.ID_RICHIESTA + "= ?"
         ;  	

   private final String LETTURA_CHECK = "SELECT * "
         + " FROM "
         + RICHIESTE_CUP.TABLE_NAME
         + " WHERE "
         + RICHIESTE_CUP.DATA_ESITO + " IS NULL "
         ;     
   private final String LETTURA_IDCUP = "SELECT * "
         + " FROM "
         + RICHIESTE_CUP.TABLE_NAME
         + " WHERE "
         + RICHIESTE_CUP.CUP + "= ?"
         ;     
   
   private  String INSERIMENTO="INSERT INTO " + RICHIESTE_CUP.TABLE_NAME + " ("
         + RICHIESTE_CUP.DATA_RICHIESTA
         + ", " + RICHIESTE_CUP.CUP + ") VALUES(?,?)";
   

   private final String AGGIORNAMENTO =
   "UPDATE "
		   + RICHIESTE_CUP.TABLE_NAME
		   + " SET "
		   +RICHIESTE_CUP.COD_INVESTIMENTO_RGS + "= ?"
		   +", "+RICHIESTE_CUP.FLAG_PNRR_PNC_RGS + "= ?"
		   +", " +RICHIESTE_CUP.ULT_DATA_WS + "= ?" 
		   + ", " + RICHIESTE_CUP.DATA_ESITO + " =?" 
		   + ", " + RICHIESTE_CUP.ESITO_RICHIESTA + " =?" 
		   + ", " + RICHIESTE_CUP.VALIDO + " =?" 
		   + ", " + RICHIESTE_CUP.DATA_RICONCIL + " =?" 
		   + ", " + RICHIESTE_CUP.UTE_RICONCIL + "=?" 
		   + ", " + RICHIESTE_CUP.STATO+" =?"
		   + ", " + RICHIESTE_CUP.TEMATICA+" = ?"
		   + " WHERE "
		   + RICHIESTE_CUP.ID_RICHIESTA + " = ?"; 

   
   
   private final String AGGIORNAMENTO_FLAG =
   "UPDATE "
		   + RICHIESTE_CUP.TABLE_NAME
		   + " SET "
		   +RICHIESTE_CUP.COD_INVESTIMENTO_RGS + "= ?"
		   +", "+RICHIESTE_CUP.FLAG_PNRR_PNC_RGS + "= ?"
		   + " WHERE "
		   + RICHIESTE_CUP.ID_RICHIESTA + " = ?"; 

   
   
   
   
   public final static String CANCELLAZIONE =
	        "DELETE FROM "
	        + RICHIESTE_CUP.TABLE_NAME
	        + " WHERE "
	        + RICHIESTE_CUP.ID_RICHIESTA + "=?";
	     

	/*********************************************************************************************
	 * Costruttore
	 * 
	 * @param currentActiveConnection
	 * @param logger
	 */
	public RichiesteCUPManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}	
	
	/*********************************************************************************************
	 * restituisce una richiesta identificata da id richiesta
	 * 
	 * @param idRichiesta
	 * @return RichiestaCUP
	 * @throws SQLException
	 * @throws Exception
	 */
	public RichiestaCUP get(long idRichiesta)throws SQLException,Exception{
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		RichiestaCUP l = null;

		try{
	 		pstmt = activeConnection.prepareStatement(LETTURA);
			pstmt.setLong(1, idRichiesta);
			rs = pstmt.executeQuery();
			l = new RichiestaCUP();
			
			if(rs.next()){
				fillBean(rs, l);
			}
		}
		finally{
           if(rs != null) rs.close();
           if(pstmt != null) pstmt.close();
		}

		return l;	
	}
	
   
   /*********************************************************************************************
    * restituisce una lista di CUP ancora non validati
    * 
    * @return RichiestaCUP
    * @throws SQLException
    * @throws Exception
    */
   public List<RichiestaCUP> getNotChecked()throws SQLException,Exception{
      PreparedStatement pstmt = null;  
      ResultSet rs = null;
      List<RichiestaCUP> l = new ArrayList<RichiestaCUP>();

      try{
         pstmt = activeConnection.prepareStatement(LETTURA_CHECK);
         rs = pstmt.executeQuery();
         
         while(rs.next()){
            RichiestaCUP item = new RichiestaCUP();
            fillBean(rs, item);
            l.add(item);
         }
      }
      finally{
           if(rs != null) rs.close();
           if(pstmt != null) pstmt.close();
      }

      return l;   
   }
   
	/*********************************************************************************************
	 * elimina un record in base all'id
	 * @param pk long per l'id della richiesta
	 * @return int - Restituisce il numero di righe eliminate
	 * @throws SQLException
	 */
	public int cancella(long pk) throws SQLException {
		
		PreparedStatement ps = null;
		
		try {
			ps = activeConnection.prepareStatement(CANCELLAZIONE);
			ps.setLong(1, pk);
			return ps.executeUpdate();
		} finally {
			try {
				ps.close();
			} catch ( Exception e ) {}
			ps = null;
		}
	}	
	
	/** inserimento nuovo record
	 * @param RichiestaCUP bean (valorizzare solo id_cuplottoagg)
	 * @return id richiesta
	 * @throws SQLException
	 */
	public long insert ( RichiestaCUP bean) throws SQLException {

	   PreparedStatement ps = null;
		
		ResultSet rs = null;
		try {
			
			int index = 1;
			
			ps = activeConnection.prepareStatement(createInsertQuery(INSERIMENTO,RICHIESTE_CUP.ID_RICHIESTA));
			
			ps.setTimestamp(index++, getNow()); 
         ps.setString(index++, bean.getCUP());      
	         
         ps.execute();
			rs = ps.getResultSet();
			rs.next();
			long idr = rs.getLong(RICHIESTE_CUP.ID_RICHIESTA);
			
			
			return idr;
		} finally {		
			//try {
			close(rs,ps);
		}
	}

	
	/******************************************************************************************************
	 * modifica il record richiesta
	 * 
	 * @param bean : bean contenente i parametri da inserire
	 * @return int : indica il numero di elementi aggiornati
	 * @throws SQLException
	 */
	public int modifica ( RichiestaCUP bean ) throws SQLException {

		PreparedStatement update = null;
				
		int idx = 0;
		try {
			
			update = activeConnection.prepareStatement(AGGIORNAMENTO);
			update.setString(++idx, bean.getCOD_INVESTIMENTO_RGS());
			 update.setString(++idx, bean.getFLAG_PNRR_PNC_RGS());
			 update.setTimestamp(++idx, bean.getULT_DATA_WS());
	         update.setTimestamp(++idx, bean.getDATA_ESITO());
			 update.setString(++idx, bean.getESITO_RICHIESTA());
	         update.setString(++idx, bean.getVALIDO());
	         update.setTimestamp(++idx, bean.getDATA_RICONCIL());
	         update.setString(++idx, bean.getUTE_RICONCIL());
                        
			
			if(bean.getStato()!=null)
				update.setString(++idx, bean.getStato());
			else
				update.setNull(++idx, java.sql.Types.VARCHAR);
			
			if(bean.getTematica()!=null)
				update.setString(++idx, bean.getTematica());
//				update.setString(++idx, bean.getIdTematica());
			else
				update.setNull(++idx, java.sql.Types.VARCHAR);
			
			update.setLong(++idx, bean.getID_RICHIESTA());
			int result = update.executeUpdate();
			
			return result;
		} finally {
			try {
				update.close();
			} catch ( Exception e ) {}
			update = null;			
		}
	}	

	/******************************************************************************************************
	 * modifica il record richiesta
	 * 
	 * @param bean : bean contenente i parametri da inserire
	 * @return int : indica il numero di elementi aggiornati
	 * @throws SQLException
	 */
	public int modificaFlagRGS ( RichiestaCUP bean ) throws SQLException {

		PreparedStatement update = null;
				
		int idx = 0;
		try {
			
			update = activeConnection.prepareStatement(AGGIORNAMENTO_FLAG);
			update.setString(++idx, bean.getCOD_INVESTIMENTO_RGS());
			update.setString(++idx, bean.getFLAG_PNRR_PNC_RGS());
			update.setLong(++idx, bean.getID_RICHIESTA());
			
			int result = update.executeUpdate();
			
			return result;
		} finally {
			try {
				update.close();
			} catch ( Exception e ) {}
			update = null;			
		}
	}	

	
	private void fillBean(ResultSet rs, RichiestaCUP l) throws SQLException,Exception {

	   l.setDATA_ESITO(rs.getTimestamp(RICHIESTE_CUP.DATA_ESITO));
	   l.setDATA_RICHIESTA(rs.getTimestamp(RICHIESTE_CUP.DATA_RICHIESTA));
	   l.setDATA_RICONCIL(rs.getTimestamp(RICHIESTE_CUP.DATA_RICONCIL));
	   l.setESITO_RICHIESTA(rs.getString(RICHIESTE_CUP.ESITO_RICHIESTA));
	   l.setCUP(rs.getString(RICHIESTE_CUP.CUP));
	   l.setID_RICHIESTA(rs.getLong(RICHIESTE_CUP.ID_RICHIESTA));
	   l.setULT_DATA_WS(rs.getTimestamp(RICHIESTE_CUP.ULT_DATA_WS));
	   l.setUTE_RICONCIL(rs.getString(RICHIESTE_CUP.UTE_RICONCIL));
	   l.setVALIDO(rs.getString(RICHIESTE_CUP.VALIDO));
	   l.setStato(rs.getString(RICHIESTE_CUP.STATO));
	   l.setIdTematica(rs.getString(RICHIESTE_CUP.TEMATICA));
	   l.setTematica(rs.getString(RICHIESTE_CUP.TEMATICA));
	   if(l.getTematica()!=null && !"".equals(l.getTematica())) {
		   String descTematica = getDescrizioneTematica(l.getIdTematica());
		   l.setTematica(descTematica);
	   }
	}

   /*********************************************************************************************
    * restituisce una richiesta identificata da id cup lotto agg
    * 
    * @param idCupLottoAgg
    * @return RichiestaCUP
    * @throws SQLException
    * @throws Exception
    */   
	public RichiestaCUP getByCup(String cup) throws Exception {
      PreparedStatement pstmt = null;  
      ResultSet rs = null;
      RichiestaCUP l = null;

      try{
         pstmt = activeConnection.prepareStatement(LETTURA_IDCUP);
         pstmt.setString(1, cup);
         rs = pstmt.executeQuery();
         l = new RichiestaCUP();
         
         if(rs.next()){
            fillBean(rs, l);
         }
      }
      finally{
           if(rs != null) rs.close();
           if(pstmt != null) pstmt.close();
      }

      return l;   
   }	
	
	public String getDescrizioneTematica(String codTematica) throws Exception {
		PreparedStatement pstmt = null;  
	      ResultSet rs = null;
	      String desc=null;
	      
	      try{
	    	  pstmt = activeConnection.prepareStatement("SELECT DESCRIZIONE_TEMATICA from TEMATICA_PNRR_CUP where ID_TEMATICA=?");
	          if(codTematica != null) {
	        	  pstmt.setLong(1, Long.parseLong(codTematica));
	          } else {
	        	  pstmt.setNull(1, Types.VARCHAR);
	          }
	          rs = pstmt.executeQuery();
	          if(rs.next())
	        	  desc = rs.getString("DESCRIZIONE_TEMATICA");
	      }
	      finally{
	           if(rs != null) rs.close();
	           if(pstmt != null) pstmt.close();
	      }
	      return desc;
	}
}
