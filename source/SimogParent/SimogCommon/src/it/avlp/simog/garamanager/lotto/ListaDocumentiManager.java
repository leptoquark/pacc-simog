package it.avlp.simog.garamanager.lotto;


import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.DOCUMENTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class ListaDocumentiManager extends AccessiDB {
	
	// estrazione della lista documenti relativi a un lotto
	
	private final static String LISTA_DOCUMENTI =
		"SELECT * FROM "
		+ DOCUMENTO.TABLE_NAME
		+ " WHERE "
		+ DOCUMENTO.ID_LOTTO
		+ " = ?";
	

	
	/*******************************************************************************
	 * costruttore
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public ListaDocumentiManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	

	/*******************************************************************************
	 * Genera una lista dei documenti del lotto
	 * @param idLotto long
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList getListaDocumenti( long idLotto ) throws Exception {

		PreparedStatement pstmt = null;
		ArrayList lista = new ArrayList();
		ResultSet rs = null;
		try {
			logger.debug("Esecuzione query [" + LISTA_DOCUMENTI + "] con parametro idLotto[" + idLotto + "]");
			pstmt = activeConnection.prepareStatement(LISTA_DOCUMENTI);			
	        
	        pstmt.setObject(1, idLotto);
	        
	        rs = pstmt.executeQuery();
	        
	        while(rs.next()){
	        	DocumentoBean doc = new DocumentoBean();
	        	doc.setId_documento(rs.getInt("id_documento"));
	        	doc.setId_lotto((int)idLotto);
	        	doc.setNomeDocumento(rs.getString("nomeDocumento"));
	        	
	        	lista.add(doc);
	        }		
		} catch ( SQLException sqle ) {
			String message = "Errore di lettura";
			logger.fatal(message, sqle );
			throw sqle;
		} finally {
			close(rs,pstmt);
//			try {
//				pstmt.close();
//			} catch ( Exception e ) {}
//			pstmt = null;
		}
		return lista;
	}

}

