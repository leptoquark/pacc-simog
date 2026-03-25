/**
 * 
 */
package it.avcp.simog.managers.bdncp;

import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.VIEW_DATIAMM;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * @author vletizia
 *
 */
public class BdncpManager extends AccessiDB {

	/*
	 * bean dati amministrazione e ufficio
	 */
	public class DatiAmmUff {
		String cfAmmin;
		String idOsservatorio;
		String idUfficio;
		String DenomUfficio;
		
		public String getCfAmmin() {
			return cfAmmin;
		}
		public void setCfAmmin(String cfAmmin) {
			this.cfAmmin = cfAmmin;
		}
		public String getIdOsservatorio() {
			return idOsservatorio;
		}
		public void setIdOsservatorio(String idOsservatorio) {
			this.idOsservatorio = idOsservatorio;
		}
		public String getIdUfficio() {
			return idUfficio;
		}
		public void setIdUfficio(String idUfficio) {
			this.idUfficio = idUfficio;
		}
      public String getDenomUfficio() {
         return DenomUfficio;
      }
      public void setDenomUfficio(String denomUfficio) {
         DenomUfficio = denomUfficio;
      } 
      
	}
	
	private static String BDNCP_SERVER = "DBMS_OLTP.";
	/**
	 * 
	 */
	public BdncpManager() {}

	/**
	 * @param currentActiveConnection
	 * @param logger
	 */
	public BdncpManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	private final String QUERY_SELECT_DATIAMM = 
	"select vi.codice_centro_di_costo, 	vi.cf_amministrazione, 	right('000' + ltrim(vi.codice_osservatorio), 3) as codice_osservatorio"
	+ " , denom_centro_di_costo"
	+ " from " 
	+  (isProduzione() ? BDNCP_SERVER : "") 
	+ "bdncp.dbo.v_StazioniAppaltanti  as vi with(nolock)" // PP aggiunto with(nolock) per evitare possibili deadlock 19.04.2012
//	+ " inner join " +  (isProduzione() ? BDNCP_SERVER : "") + "bdncp.dbo.stazione_appaltante as sa"
//	+ "	on sa.cod_stazione_appaltante = vi.cod_stazione_appaltante"
	+ " where vi.cod_stazione_appaltante = ?";

	/**
	 * metodo per il recupero delle informazioni 
	 * cfamm, idosservatorio, idufficio
	 * 
	 * @return Map&lt;String,String&gt; id,descrizione
	 * @throws SQLException
	 */
	public DatiAmmUff loadDatiAmm(String codAmm, String codUff)throws SQLException{
		
		DatiAmmUff retVal = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		if(codUff == null || "".equals(codUff))
			return null;

      try{		
 	    	pstmt = activeConnection.prepareStatement(QUERY_SELECT_DATIAMM);
    		pstmt.setString(1, codUff);	
	    	//pstmt.setString(2, codAmm);	 non usato
	    	rs = pstmt.executeQuery();

    		if(rs.next()){
	    		retVal = new DatiAmmUff();
 
    			retVal.cfAmmin = rs.getString(VIEW_DATIAMM.CFAMM);
	    		retVal.idOsservatorio = rs.getString(VIEW_DATIAMM.IDOSS);
		    	retVal.idUfficio = rs.getString(VIEW_DATIAMM.GUIDUFF);
		    	
		    	retVal.setDenomUfficio(rs.getString(VIEW_DATIAMM.DENUFF));		 
	    	}
		}
		catch(SQLException e){
		    logger.fatal("*** errore durante ricerca dati amministrazione - codamm:" + codAmm + " coduff:" + codUff);
            logger.fatal("*** eccezione: " + e.getMessage());
			// throw e;
		}
		finally{
	    	close(rs, pstmt);
		}
		return retVal;
	}	
	
	/**
	 * ATTENZIONE codice sensibile al contesto!
	 * @return
	 */
	private boolean isProduzione(){
		boolean retVal = false;
		try {
			retVal = this.activeConnection.getMetaData().getURL().toLowerCase().indexOf("dbmsoltp02") > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}

   private final String QUERY_SELECT_TIPOSA = 
   "select top 1 " + VIEW_DATIAMM.TIPOSA         
   + " from " 
   +  (isProduzione() ? BDNCP_SERVER : "") 
   + "bdncp.dbo.v_StazioniAppaltanti  as vi with(nolock)" // PP aggiunto with(nolock) per evitare possibili deadlock 19.04.2012
   + " where " + VIEW_DATIAMM.CFAMM + " = ?";

   
   /** INT 85
    * metodo per il recupero delle informazioni 
    * cfamm
    * 
    * @return in codice
    * @throws SQLException
    */
   public String loadTipoSA(String codAmm)throws SQLException{
      
      PreparedStatement pstmt = null;  
      ResultSet rs = null;
      String retVal = "";
      
      if(codAmm == null || "".equals(codAmm))
         return retVal;

      try{
         // verifico se l'oggetto esiste prima di fare query
         boolean okVista = false;
         final String checkVista = "select OBJECT_ID('BDNCP.dbo.V_STAZIONIAPPALTANTI', 'V')";
         pstmt = activeConnection.prepareStatement(checkVista);
         rs = pstmt.executeQuery();
         if(rs.next()){
            okVista = rs.getString(1) != null;
            
            close(rs, pstmt);
            
            if(!okVista){
               return retVal;
            }
         }
      
         pstmt = activeConnection.prepareStatement(QUERY_SELECT_TIPOSA);
         pstmt.setString(1, codAmm);   
         //pstmt.setString(2, codAmm);  non usato
         rs = pstmt.executeQuery();

         if(rs.next()){
            retVal = rs.getString(VIEW_DATIAMM.TIPOSA);
         }
      }
      catch(SQLException e){
          logger.fatal("*** errore durante ricerca tipoSA amministrazione - codamm:" + codAmm) ;
            logger.fatal("*** eccezione: " + e.getMessage());
         // throw e;
      }
      finally{
         close(rs, pstmt);
      }
      return retVal;
   }  

   /** INT 85
    * metodo per il recupero delle informazioni 
    * idUfficio
    * 
    * @return in codice
    * @throws SQLException
    */
   public String loadTipoSANew(String codUff)throws SQLException{
      
      PreparedStatement pstmt = null;  
      ResultSet rs = null;
      String retVal = "";

      String QUERY_SELECT_TIPOSANEW = 
            "select " + VIEW_DATIAMM.TIPOSA         
            + " from " 
            +  (isProduzione() ? BDNCP_SERVER : "") 
            + "bdncp.dbo.f_get_dati_CodiceCdC(?)";

      if(codUff == null || "".equals(codUff))
         return retVal;

      try{
         pstmt = activeConnection.prepareStatement(QUERY_SELECT_TIPOSANEW);
         pstmt.setString(1, codUff);   
         rs = pstmt.executeQuery();

         if(rs.next()){
            retVal = rs.getString(VIEW_DATIAMM.TIPOSA);
         }
      }
      catch(SQLException e){
          logger.fatal("*** errore durante ricerca tipoSANEW ufficio:" + codUff) ;
            logger.fatal("*** eccezione: " + e.getMessage());
         // throw e;
      }
      finally{
         close(rs, pstmt);
      }
      return retVal;
   }
   
   
   //TICKET ALM - 3.04.3
   public String loadDenSA(String codUff)throws SQLException{
	      
	      PreparedStatement pstmt = null;  
	      ResultSet rs = null;
	      String retVal = "";

	      

	      //QUERY COMMENTATA IN SEGUITO ALLA MAC 35066
	       String QUERY_LOAD_DEN_SA =
	   		   "select top 1 " + VIEW_DATIAMM.DEN_SA         
	   		   + " from " 
	   		   +  (isProduzione() ? BDNCP_SERVER : "") 
	   		   + " bdncp.dbo.v_StazioniAppaltanti  as vi with(nolock)" // PP aggiunto with(nolock) per evitare possibili deadlock 19.04.2012
	   		   + " where " + VIEW_DATIAMM.CFAMM + " = ?";

				//QUERY COMMENTATA IN SEGUITO ALLA MAC 35066
				// String QUERY_LOAD_DEN_SA =
				//		   "select top 1 " + VIEW_DATIAMM.DEN_SA         
				//		   + " from " 
				//		   +  (isProduzione() ? BDNCP_SERVER : "") 
				//		   + " bdncp.dbo.v_StazioniAppaltanti  as vi with(nolock)" // PP aggiunto with(nolock) per evitare possibili deadlock 19.04.2012
				//		   + " where " + VIEW_DATIAMM.CFAMM + " = ?";

				 // FASE 3 - 3.04.9 MAC 35066 
				 String FUNCTION_RICERCA_ANAC_AUSA = "SELECT * FROM BDNCP.AUSA.F_AUSA_RICERCA_ANAGRAFICA_DA_CODICE_FISCALE(?)"; 
 


		if(codUff == null || "".equals(codUff))
		   return retVal;
		
		try{
			  // FASE 3 - 3.04.9 MAC 35066
		    CallableStatement cstmt = activeConnection.prepareCall(FUNCTION_RICERCA_ANAC_AUSA); 
		    cstmt.setString(1, codUff);
		    cstmt.execute();
		    rs = cstmt.getResultSet();
			  //FINE FASE 3
			  
		//   RIGHE 274-277 COMMENTATE IN SEGUITO ALLA MAC 35066
		//   pstmt = activeConnection.prepareStatement(QUERY_LOAD_DEN_SA);
		//   pstmt.setString(1, codUff);   
		//   rs = pstmt.executeQuery();
		
		   if(rs.next()){
		      retVal = rs.getString(VIEW_DATIAMM.RAG_SOC_SA);
		   }
		}
		catch(SQLException e){
		    logger.fatal("*** errore durante ricerca tipoSANEW ufficio:" + codUff) ;
		      logger.fatal("*** eccezione: " + e.getMessage());
		   // throw e;
		}
		finally{
		   close(rs, pstmt);
		}
		return retVal;
		} 
   
   
   public String checkSmartCig(String codNum) throws SQLException{
	      
	      PreparedStatement pstmt = null;  
	      ResultSet rs = null;
	     String retVal = "";
	      
	      String QUERY_LOTTO = "SELECT top 1 NUM_LOTTO "
	    		  + " from " 
		   		   +  (isProduzione() ? BDNCP_SERVER : "") 
		   		   + " BDNCP.DBO.LOTTO "
		   		   + " WHERE NUM_LOTTO = ?"
		   		   + " AND DATA_ANNULLAMENTO IS NULL AND DATA_INIBIZIONE_PAGAMENTI IS NULL";
	      
	      
	      try{
		         pstmt = activeConnection.prepareStatement(QUERY_LOTTO);
		         pstmt.setString(1, codNum);   
		         rs = pstmt.executeQuery();

		         if(rs.next()){
		            retVal = rs.getString("NUM_LOTTO");
		         }
		      }
		      catch(SQLException e){
		          logger.fatal("*** errore durante ricerca NUM_LOTTO in BDNCP.LOTTO:" + codNum) ;
		            logger.fatal("*** eccezione: " + e.getMessage());
		         // throw e;
		      }
	      
	      return retVal;
	      
   }
   
   //FINE TICKET ALM - 3.04.3
   
}
