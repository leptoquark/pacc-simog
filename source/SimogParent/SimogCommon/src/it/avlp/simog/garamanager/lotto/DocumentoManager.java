package it.avlp.simog.garamanager.lotto;

import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.DOCUMENTO;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DocumentoManager extends AccessiDB {
	
	/***********************************************************************************************
	 * Stringa relativa alla PreparedStatement per l'inserimento nella Tabella Documento di un record;
	 * i parametri interessati sono :
	 * <ul>
	 * <li>nome documento
	 * <li>documento
	 * <li>id lotto
	 * </ul>
	 * 
	 */
	private final static String INSERIMENTO_DOCUMENTO =
		"INSERT INTO "
		+ DOCUMENTO.TABLE_NAME
		+ " (" + DOCUMENTO.NOMEDOCUMENTO 
		+ ", " + DOCUMENTO.DOCUMENTO
		+ ", " + DOCUMENTO.ID_LOTTO
		+ " ) VALUES (?, ?, ?)";
	

	
	public DocumentoManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	
	
	
	/***********************************************************************************************
	 * Memorizza un documento passando il nome del documento, il documento come ByteArrayOutputStream e l'id del Lotto.   
	 *
	 * @param nomeDocumento String
	 * @param boutDocumento ByteArrayOutputStream
	 * @param idLotto String
	 * @throws SQLException
	 */
	public void storeDocumento ( String nomeDocumento, ByteArrayOutputStream boutDocumento, String idLotto ) throws SQLException {
		
		PreparedStatement pstmt = null;
				
		try {
			
						
			pstmt = activeConnection.prepareStatement(INSERIMENTO_DOCUMENTO);
			pstmt.setObject(1, nomeDocumento); 
	        pstmt.setObject(2, boutDocumento.toByteArray()); // db looks at data.length
	        pstmt.setObject(3, idLotto);
	        
	        int res = pstmt.executeUpdate();
	        activeConnection.commit();
	        
	        logger.info (res + " righe aggiunte su DOCUMENTO" );
	        logger.info ("Completato caricamento File [" + nomeDocumento + "]" );
		
		} catch ( SQLException sqle ) {
			String message = "Impossibile Archiviare il documento";
			//L'errore viene loggato qui, il chiamante fa solo un senderror
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
