package it.avlp.simog.garamanager.lotto.app;

import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.lotto.DocumentoManager;
import it.avlp.simog.servlet.ServletBase;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;

public class SrvGestioneDocumenti extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8735505757954978191L;

	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		HttpSession currentActiveSession = request.getSession();
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if ( checkSession(request) ) {
			if ( currentUser.isRSSAorRUP() ) {
				
				String requestingUrl = "";
				String requestingUrlError = "";
				
				try {
					String idLottoCorrente = (String)currentActiveSession.getAttribute(FIELD_NAME_ID_LOTTO);					
					requestingUrlError = ParametriServlet.JSP_GESTISCI_DOCUMENTI + "?" + FIELD_NAME_ID_LOTTO + "=" + idLottoCorrente;
					requestingUrl = ParametriServlet.SRV_GESTISCI_LOTTO + "?" + ParametriServlet.ACTION + "=modifica&" + FIELD_NAME_ID_LOTTO + "=" + idLottoCorrente;
					// /gestisciLotto?action=modifica&idLotto=40
					
					int maxUploadSize = configuration.getMax_file_size();
					MultipartParser parser = new MultipartParser(request, maxUploadSize);
					
					ParamPart part2 = (ParamPart)parser.readNextPart();
					FilePart part1 = (FilePart)parser.readNextPart();					
				
					String nomeFile = part1.getFileName();
					//String idLottoCorrente = part2.getStringValue()					

					
					
					ByteArrayOutputStream bout = new ByteArrayOutputStream();
					part1.writeTo(bout);					
					
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					DocumentoManager documentoManager = new DocumentoManager( currentActiveConnection, logger );
					documentoManager.storeDocumento(nomeFile, bout, idLottoCorrente);
					
					
					logger.info ( SIMOG_UPLOAD_006 + " NOME FILE [" + nomeFile + "] per ID_LOTTO [" + idLottoCorrente + "] UTENTE [" + currentUser.getLogin() +"]" );
					sendMessage(request, response, Messaggi.SIMOG_UPLOAD_006, requestingUrl);
					return;

					/* ********************************* */
					
				} catch ( Exception e ) {
				//	e.printStackTrace();
					
					sendError(request, response, Messaggi.SIMOG_UPLOAD_007 +" - MOTIVO: " + e.getMessage(), requestingUrlError, e);
				
				} finally {
					closeConnection(request.getSession().getId(),getClass().getName());
				}
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				
			}
		} else {
			sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			
		}
	}
}