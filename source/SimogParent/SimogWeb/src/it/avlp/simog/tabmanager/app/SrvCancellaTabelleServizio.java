package it.avlp.simog.tabmanager.app;

import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.servlet.ServletBase;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SrvCancellaTabelleServizio extends ServletBase {
	private static final long serialVersionUID = 1L;
	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		perform(request, response);
//	}
	
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if ( checkSession(request) && currentUser.isAmministratore() ) {			
			try {

				cancellaAggiornamentiDir(configuration.getAggiornamentoTabelleDir());  // caricamenti attivi
				
				setFileInfo(request, response);  // setto le liste dei caricamenti in request
				
				forward(JSP_AGGIORNAMENTI_STATO, request, response);
				return;
				
			} catch ( Exception e ) {
				sendError( request, response, SIMOG_UPLOAD_000, JSP_AGGIORNAMENTI_STATO, e );
				return;
			}
		} else {
			sendError( request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			return;
		}
	}
	
	/*********************************************************************************
	 * Cancella i file presenti nella direcory passata  in input
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws Exception 
	 */
	protected void cancellaAggiornamentiDir(String tabella_servizio_dir) throws Exception{
		File aggiornamentiDir = new File ( tabella_servizio_dir );
		if(aggiornamentiDir.exists()){
			String absolutePath = aggiornamentiDir.getAbsolutePath();
			String [] listaCaricamenti = aggiornamentiDir.list( new FileNameChecker(logger) );
			for(String filename: listaCaricamenti){
				if( !(new File(absolutePath +"/"+ filename)).delete() ){
					throw new Exception("Errore durante la cancellazione"); 	
				}
			}
		}
	}
	
	/*********************************************************************************
	 * Imposta il numero dei caricamenti attivi e di quelli completati
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void setFileInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		File aggiornamentiAttiviDir = new File ( configuration.getAggiornamentoTabelleDir() );
		String [] listaCaricamentiAttivi = aggiornamentiAttiviDir.list( new FileNameChecker(logger) );
		
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
	
	
}
