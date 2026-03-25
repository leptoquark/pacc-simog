package it.avlp.simog.tabmanager.app;

import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.tabmanager.TabManager;
import it.avlp.simog.tabmanager.xml.parser.TabelleManagerSAXParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.methods.GetMethod;

public class SrvAggiornaTabelle extends ServletBase {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1389010879880240922L;
	


//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		perform( request, response );
//	}
	
	
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean errors = false;
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		
        String errList = "";
        
		if ( checkSession(request) ) {
			if ( currentUser.isAmministratore() ) {
				
				try {
					File tempStoreFolder = new File ( configuration.getAggiornamentoTabelleDir() );
					
					File [] listaCaricamentiAttivi = tempStoreFolder.listFiles( new FileNameChecker(logger) );
					
					if ( listaCaricamentiAttivi != null && listaCaricamentiAttivi.length > 0 ) {
						File aggiornamentiCompletatiDir = new File ( configuration.getAggiornamentoTabelleDirHistory() );
						
						if ( ! aggiornamentiCompletatiDir.exists() ) {
							if ( ! aggiornamentiCompletatiDir.mkdir() ) {
								throw new SimogException ("Impossibile creare la directory di archiviazione");
							}
						}
						
						logger.debug( "Sono disponibili [" + listaCaricamentiAttivi.length + "] File per l'aggiornamento");
						
						currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
						currentActiveConnection.setAutoCommit(false);

						
						TabManager tabManager = new TabManager(currentActiveConnection, logger);
						
						for ( int i = 0; i < listaCaricamentiAttivi.length; i++ ) {
							File currentFile = listaCaricamentiAttivi[i];
							errors = false;
							if ( currentFile.isFile() ) {
								logger.info ( "Caricamento in corso del file [" + currentFile.getAbsolutePath() + "]" );
								
								BufferedReader reader = null;
								
								try {
									TabelleManagerSAXParser tabelleParser = new TabelleManagerSAXParser ( logger );
										
									reader = new BufferedReader ( new FileReader ( currentFile ) );
									String read = null;
									StringBuffer fullFile = new StringBuffer();
									
									while ( ( read = reader.readLine() ) != null ) {
										if ( read != "\n" ) {
											fullFile.append(read);
										}
									}
									tabelleParser.parse( fullFile.toString() );
									
									/* Procede all'eliminazione logica delle tuple */									
									
									try {
										if ( tabelleParser.getTableDELETE().getRowsCount() > 0 ) {
											tabManager.cancella( tabelleParser.getTableDELETE(), tabelleParser.getTableName(), getTodayDate() );
											commit(currentActiveConnection);
										}
									} catch ( Exception e ) {
										try {
											rollback(currentActiveConnection);
										} catch ( Exception ex ) {
											logger.fatal ( SIMOG_UPD_001 + " AZIONE TENTATA: ROLLBACK DURANTE ELIMINAZIONE", e );
										}							
										errors  = true;
										errList += "<br>" + tabManager.getLastErr();
										logger.error ( SIMOG_UPD_001 + " AZIONE TENTATA: ELIMINAZIONE", e );
									}
									/* Procede all'inserimento delle tuple */
									try {
										if ( tabelleParser.getTableINSERT().getRowsCount() > 0 ) {
											tabManager.inserisci( tabelleParser.getTableINSERT(), tabelleParser.getTableName(), getTodayDate() );
											commit(currentActiveConnection);
										}
									} catch ( Exception e ) {
										try {
											rollback(currentActiveConnection);
										} catch ( Exception ex ) {
											logger.fatal ( SIMOG_UPD_001 + " AZIONE TENTATA: ROLLBACK DURANTE INSERIMENTO", e );	
										}							
										errors = true;
                                        errList += "<br>" + tabManager.getLastErr();

										logger.error ( SIMOG_UPD_001 + " AZIONE TENTATA: INSERIMENTO", e );
									}
									
									/* Procede all'aggiornamento delle tuple */
									
									try {
										if ( tabelleParser.getTableUPDATE().getRowsCount() > 0 ) {
											tabManager.aggiorna( tabelleParser.getTableUPDATE(), tabelleParser.getTableName(), getTodayDate() );
											commit(currentActiveConnection);
										}
									} catch ( Exception e ) {
										try {
											rollback(currentActiveConnection);
										} catch ( Exception ex ) {
											logger.fatal ( SIMOG_UPD_001 + " AZIONE TENTATA: ROLLBACK DURANTE AGGIORNAMENTO", e );
										}
										errors = true;
                                        errList += "<br>" + tabManager.getLastErr();

										logger.error ( SIMOG_UPD_001 + " AZIONE TENTATA: AGGIORNAMENTO", e );				
									}
									
									reader.close();
									reader = null;	
									
									if ( ! errors ) {
										File historyFile = new File ( aggiornamentiCompletatiDir, currentFile.getName() + "_" + getFullTodayDate() + ".xml" );
										if ( currentFile.renameTo(historyFile) ) {
											logger.debug ( "Completato spostamento del file [" + historyFile.getAbsolutePath() + "]");
										} else {
											logger.fatal ("Impossibile archiviare il file elaborato [" + currentFile.getAbsolutePath() + "] nella directory [" + aggiornamentiCompletatiDir + "]");
										}
									}
								} catch ( Exception e ) {
									errors = true;
                                    errList += "<br>" + e.getMessage();

									logger.fatal ("Impossibile elaborare il file [" + currentFile.getAbsolutePath() + "]", e);
								} finally {
									try {
										reader.close();
										logger.debug ( "Chiuso file [" + currentFile.getAbsolutePath() + "]" );
									} catch ( Exception e ) {}
									reader = null;
								}
							}					
						}
					}
					if ( ! errors ) {
						sendMessage(request, response, Messaggi.SIMOG_UPLOAD_005, SRV_VISUALIZZA_CARICAMENTI );
						return;
					} else {
						sendError(request, response, Messaggi.SIMOG_UPD_001 + errList, SRV_VISUALIZZA_CARICAMENTI );
						return;
					}
				} catch (Exception e) {
				   errList += "<BR>" + e.getMessage();
					sendError(request, response, Messaggi.SIMOG_UPD_001 + errList, ParametriServlet.JSP_GESTIONE_TABELLE, e);
					return;
				} finally {
					closeConnection(request.getSession().getId(),getClass().getName());
				}
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE);
				return;
			}
		} else {
			sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE);
			return;
		}
	}

	
}
