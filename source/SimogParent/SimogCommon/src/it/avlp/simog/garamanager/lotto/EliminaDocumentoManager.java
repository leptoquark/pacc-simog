package it.avlp.simog.garamanager.lotto;


import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.DOCUMENTO;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.LOTTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;

import org.apache.log4j.Logger;

public class EliminaDocumentoManager extends AccessiDB {
	
	
	
	private final static String ELIMINA_DOCUMENTI =
		"DELETE FROM "+DOCUMENTO.TABLE_NAME+" WHERE "+DOCUMENTO.ID_DOCUMENTO+"=? AND" +
		"(SELECT "+GARA.ID_STAZIONE_APPALTANTE+" FROM "+GARA.TABLE_NAME+", "+LOTTO.TABLE_NAME+", "+DOCUMENTO.TABLE_NAME+
		" WHERE "+DOCUMENTO.T_ID_LOTTO+" = "+LOTTO.T_ID_LOTTO+" AND "+GARA.T_ID_GARA+" = "+LOTTO.T_ID_GARA+" AND "+DOCUMENTO.ID_DOCUMENTO+"=?)"; 

	

	public EliminaDocumentoManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	
	/*********************************************************************************************
	 * elimina un documento
	 * 
	 * @param id_documento String
	 * @param listaSARiferimento Hashtable
	 * @throws SQLException
	 */
	public void delete(String id_documento, Hashtable listaSARiferimento) throws SQLException {

		PreparedStatement pstmt = null;
		
		
		try {
			
			String query = ELIMINA_DOCUMENTI + addInCondition(listaSARiferimento.keys());
			logger.debug("esecuzione di: "+query);
			
			pstmt = activeConnection.prepareStatement(query);			
	        
	        pstmt.setInt(1, Integer.parseInt(id_documento));
	        pstmt.setInt(2, Integer.parseInt(id_documento));
	        pstmt = this.fillPstmt(pstmt, 3, listaSARiferimento);
	        
	        int res=pstmt.executeUpdate();
	        logger.info("["+res+" documenti eliminati]");
	        
		
		} catch ( SQLException sqle ) {
			String message = "Errore di lettura";
			logger.fatal(message, sqle );
			throw sqle;
			
		} finally {
			try {
				pstmt.close();
			} catch ( Exception e ) {}
			pstmt = null;
		}
		
	}
}

