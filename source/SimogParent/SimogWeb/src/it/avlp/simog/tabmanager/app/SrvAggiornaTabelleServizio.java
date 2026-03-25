package it.avlp.simog.tabmanager.app;

import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.servlet.ServletBase;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

public class SrvAggiornaTabelleServizio extends ServletBase {
	

	private static final long serialVersionUID = -1050430205320873316L;	
	
	private File tempStoreFolder = null;
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setFileInfo(request, response);
		forward( ParametriServlet.JSP_AGGIORNAMENTI_STATO, request, response );
	}
	
	/*********************************************************************************
	 * Recupera il file in tempStoreFolder
	 * @return File
	 */
	private File getTempStoreFolder() {
		if ( tempStoreFolder == null ) {
			tempStoreFolder = new File ( configuration.getAggiornamentoTabelleDir() );
		}
		return tempStoreFolder;
	}
	
	/*********************************************************************************
	 * Imposta il numero dei caricamenti attivi e di quelli completati
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void setFileInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String [] listaCaricamentiAttivi = getTempStoreFolder().list( new FileNameChecker(logger) );
		File aggiornamentiCompletatiDir = new File ( configuration.getAggiornamentoTabelleDirHistory() );
		String [] listaCaricamentiCompletati = aggiornamentiCompletatiDir.list( new FileNameChecker(logger) );
		
		if ( listaCaricamentiAttivi != null && listaCaricamentiAttivi.length > 0 ) {
			logger.debug( "Sono tuttora disponibili [" + listaCaricamentiAttivi.length + "] upload");
			request.setAttribute( ParametriServlet.TAB_CARICAMENTI_ATTIVI, listaCaricamentiAttivi);
		}
		
		if ( listaCaricamentiCompletati != null && listaCaricamentiCompletati.length > 0 ) {
			request.setAttribute( ParametriServlet.TAB_CARICAMENTI_COMPLETATI, listaCaricamentiCompletati);		
		}
	}
	
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if ( checkSession(request) && currentUser.isAmministratore() ) {
			
			try {
				tempStoreFolder = getTempStoreFolder();
				if ( ! tempStoreFolder.exists() ) {
					try {
						tempStoreFolder.mkdir();
						logger.warn ( "Creata cartella richiesta per l'archiviazione degli aggiornamenti tabelle di servizio [" + tempStoreFolder.getAbsolutePath() + "]" );
					} catch ( Exception e ) {
						logger.fatal( Messaggi.SIMOG_UPLOAD_001 + " Cartella richiesta [" + configuration.getAggiornamentoTabelleDir() + "]");
						throw new SimogException ( Messaggi.SIMOG_UPLOAD_001 );
					}
				}
				logger.info ( "Inizio acquisizione File" );
				
				MultipartRequest multi = new MultipartRequest(
						request,
						tempStoreFolder.getAbsolutePath() );
					
				logger.info ( "Completato caricamento File" );

				setFileInfo(request, response);
				
				sendMessage(request,  response, SIMOG_UPLOAD_004, ParametriServlet.JSP_AGGIORNAMENTI_STATO );
				return;
				
			} catch ( Exception e ) {
				sendError( request, response, SIMOG_UPLOAD_000, JSP_GESTIONE_TABELLE, e );
				return;
			}
		} else {
			sendError( request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			return;
		}
	}
}
