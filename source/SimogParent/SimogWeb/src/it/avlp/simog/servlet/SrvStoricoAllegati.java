package it.avlp.simog.servlet;

import it.avlp.simog.beans.AllegatoBean;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.action.AllegatiAction;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.AllegatoManager;
import it.avlp.simog.util.PageHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SrvStoricoAllegati extends ServletBase {
		private static final long serialVersionUID = 8735505757954978191L;

		
		public void doGet(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException {
			perform(request, response);
		}
		
		protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			Connection currentActiveConnection = null;
			Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
			String requestingUrl = ParametriServlet.JSP_STORICO_ALLEGATI;

			if ( checkSession(request) ) {
				if ( true || currentUser.isRSSAorRUP() ) { // PP la possono usare tutti
					
					try {
						currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
						String action = (String)request.getParameter(ParametriServlet.ACTION);					
						
						//gm visualizzazione di un file
						if("view".equals(action)){
							String idAllegato = request.getParameter(ParametriServlet.IDALLEGATO);
							if (idAllegato == null)
								idAllegato = (String) request.getSession().getAttribute(ParametriServlet.IDALLEGATO);
							
							AllegatoBean aBean = new AllegatoBean();
							AllegatiAction allAct = new AllegatiAction(aBean, configuration, currentActiveConnection, logger);
							requestingUrl = JSP_ERRORE;
							AllegatoBean abView = allAct.load(idAllegato, true);
							
							if(abView == null)
								throw new Exception(allAct.getEsitoCheck());
								
							response.setHeader("Pragma", "no-cache");  
							response.setHeader("Cache-control", "private");  
							response.setDateHeader("Expires", 0);  
							response.setHeader("Content-disposition", "attachment; filename=\""+ abView.getNomeFile() + "\"");
							response.setContentType("application/pdf");
							response.setContentLength((int) abView.getBout().length);
							
							java.io.OutputStream out = response.getOutputStream();
							
							ByteArrayOutputStream aaa = new ByteArrayOutputStream();
							aaa.write(abView.getBout());
							aaa.writeTo(out);
							out.flush();
							out.close();
							aaa.reset();
						}
						//gm caricamento dei file
						else{
							String idPubblicazione = null;					
							String dataInizioPub = null;
							
							idPubblicazione = request.getParameter(ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE);
							dataInizioPub = request.getParameter(ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB);	
							
							AllegatoManager aman = new AllegatoManager(currentActiveConnection,logger);
							List<AllegatoBean>storicoAllegati = new ArrayList<AllegatoBean>();
							
							if(Integer.parseInt(idPubblicazione) < 0)
							   storicoAllegati = aman.getAllegatiByGara(- Long.parseLong(idPubblicazione));
							else
							   storicoAllegati = aman.getAllegatiByPubblicazione(Long.parseLong(idPubblicazione), PageHelper.parseTime(dataInizioPub));
							
							// PP 3.02.2 aggiungo la gestione del messaggio per allegati non trovati
							for (AllegatoBean bean : storicoAllegati) {
								// provo a leggere il contenuto, non ho altro modo per sapere se esiste
								AllegatiAction allAct = new AllegatiAction(bean, configuration, currentActiveConnection, logger);
								AllegatoBean out = allAct.load(String.valueOf(bean.getIdAllegato()), true);
								// se il bean è null c'e'stato un problema e copio l'esito nel bean
								if (out == null)
									bean.setEsitoCheck(allAct.getEsitoCheck());
									
								allAct = null;
							}
							
							request.setAttribute(ParametriServlet.STORICO_ALLEGATI, storicoAllegati);
							
							forward(requestingUrl, request, response);
						}
					} catch ( Exception e ) {
						e.printStackTrace();
						
						sendError(request, response, Messaggi.SIMOG_UPLOAD_007 +" - MOTIVO: " + e.getMessage(), requestingUrl, e);
					
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