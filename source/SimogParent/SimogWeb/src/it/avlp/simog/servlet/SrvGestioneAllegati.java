package it.avlp.simog.servlet;

import it.avlp.simog.beans.AllegatoBean;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.action.AllegatiAction;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.util.PageHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;

public class SrvGestioneAllegati extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8735505757954978191L;

	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		perform(request, response);
	}
	
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		String requestingUrl = ParametriServlet.JSP_GESTISCI_ALLEGATI;

		if ( checkSession(request) ) {
			if ( currentUser.isRSSAorRUP() ) {
				
				try {
					AllegatoBean aBean = new AllegatoBean();
										
					String pathFile = null;
					
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());

					String action = (String)request.getParameter(ParametriServlet.ACTION);					

					String idGara = null;					
					String tipoDoc = null;					
					String retField = null;				
					String nomeFile = null;
					ByteArrayOutputStream bout = new ByteArrayOutputStream();
					String idAllegato = null;
					String note = null;
					
					String cType = (String)request.getContentType();		
										
					idAllegato = request.getParameter(ParametriServlet.IDALLEGATO);
					if (idAllegato == null)
						idAllegato = (String) request.getSession().getAttribute(ParametriServlet.IDALLEGATO);
					
					retField = request.getParameter(ParametriServlet.RETFIELD);			
					if (retField == null)
						retField = (String) request.getSession().getAttribute(ParametriServlet.RETFIELD);
					
					idGara = request.getParameter(SESSION_ID_GARA);
					if (idGara == null)
						idGara = (String) request.getSession().getAttribute(SESSION_ID_GARA);
					
					tipoDoc = request.getParameter(ParametriServlet.TIPODOC);
					if (tipoDoc == null)
						tipoDoc = (String) request.getSession().getAttribute(ParametriServlet.TIPODOC);
										
					note = PageHelper.getNulledField(request.getParameter(ParametriServlet.NOTEALL));
					if (note == null)
						note = PageHelper.getNulledField((String)request.getSession().getAttribute(ParametriServlet.NOTEALL));
					
					nomeFile = request.getParameter(ParametriServlet.NOMEFILE);
					if(nomeFile == null)
						nomeFile = (String) request.getSession().getAttribute(ParametriServlet.NOMEFILE);
					
					AllegatiAction allAct = new AllegatiAction(aBean, configuration, currentActiveConnection, logger);
					
					if("view".equals(action)){
						requestingUrl = JSP_ERRORE;
						AllegatoBean abView = allAct.load(idAllegato, true);
						
						// disattivati per problema con IE8
						//response.setHeader("Pragma", "no-cache");  
						// response.setHeader("Cache-control", "private");  
						response.setDateHeader("Expires", 0);  
						response.setHeader("Content-disposition", "attachment; filename=\""+ abView.getNomeFile() + "\"");
						response.setContentType("application/pdf");
//	                      response.setContentType("application/octet-stream");
	                      
	                      response.setContentLength((int) abView.getBout().length);
						
						java.io.OutputStream out = response.getOutputStream();
						
						ByteArrayOutputStream aaa = new ByteArrayOutputStream();
						aaa.write(abView.getBout());
						aaa.writeTo(out);
						out.flush();
						out.close();
						aaa.reset();
					}
					else {
						request.getSession().setAttribute(ParametriServlet.TIPODOC, tipoDoc);
						request.getSession().setAttribute(ParametriServlet.RETFIELD, retField);
						request.getSession().setAttribute(ParametriServlet.NOTEALL, note);
						request.getSession().setAttribute(ParametriServlet.SESSION_ID_GARA, idGara);
						request.getSession().setAttribute(ParametriServlet.IDALLEGATO, idAllegato);
						request.getSession().setAttribute(ParametriServlet.NOMEFILE, nomeFile);
						
						//MEV 34186 3.04.8
						request.getSession().setAttribute(ParametriServlet.PATH_FILE, pathFile);
						//FINE MEV 34186 3.04.8
	
						if(cType != null){
							// sono nel popup
							MultipartParser parser = null;
							try {
								parser = new MultipartParser(request, configuration.getMax_file_size());
							} catch (Exception e) {
	
								sendError(request, response, Messaggi.SIMOG_UPLOAD_007 +" - MOTIVO: " + e.getMessage(), requestingUrl);
								return;
							}
							Object buff = parser.readNextPart();
							
							while (buff != null){
								if (buff instanceof ParamPart){
									ParamPart part2 = (ParamPart)buff;
									
									if(ParametriServlet.TIPODOC.equals(part2.getName()))
										tipoDoc = part2.getStringValue();
									else if(ParametriServlet.RETFIELD.equals(part2.getName()))
										retField = part2.getStringValue();
									else if(ParametriServlet.SESSION_ID_GARA.equals(part2.getName()))
										idGara = part2.getStringValue();
									else if(ParametriServlet.IDALLEGATO.equals(part2.getName()))
										idAllegato = part2.getStringValue();
									else if(ParametriServlet.NOTEALL.equals(part2.getName()))
										note = part2.getStringValue();
									
									//MEV 34186 3.04.8
									else if(ParametriServlet.PATH_FILE.equals(part2.getName()))
										pathFile = part2.getStringValue();
									//FINE MEV 34186 3.04.8
								}
								else if(buff instanceof FilePart){
									FilePart part1 = (FilePart)buff;					
									nomeFile = part1.getFileName();
									part1.writeTo(bout);													
								}
								// lettura successiva
								buff = parser.readNextPart();
							}
						}
						else {
							// vengo dal chiamante
							tipoDoc = request.getParameter(ParametriServlet.TIPODOC);
	
							if(request.getParameter(ParametriServlet.SESSION_ID_GARA) != null)
								idGara = request.getParameter(ParametriServlet.SESSION_ID_GARA);	
						}
						
						// fill del bean
						aBean.setTipoDoc(tipoDoc);	
						if(nomeFile == null)
							aBean.setNomeFile("");
						else
							aBean.setNomeFile(nomeFile);
						
						aBean.setBout(bout);	
						aBean.setNote(note);
						
						if(idGara != null)
							aBean.setIdGara(Integer.parseInt(idGara));
						if(idAllegato != null)
							aBean.setIdAllegato(Integer.parseInt(idAllegato));
						
						//MEV 34186 3.04.8
						if(pathFile != null)
							aBean.setPathFile(pathFile);
	                    //FINE MEV
						
						if("abort".equals(action)){
							allAct.delete(idAllegato);
							idAllegato = null;
						}
						else if("save".equals(action) && nomeFile != null && request.getMethod().equals("POST")){		
							int idAll = allAct.checkAndSave();
							if(idAll > 0){
								sendMessage(request, Messaggi.SIMOG_UPLOAD_006);
								note = "";
								idAllegato = String.valueOf(idAll);
							}
							else{
								idAllegato = null;
								sendError(request, Messaggi.SIMOG_UPLOAD_007+" - MOTIVO: " + allAct.getEsitoCheck());	
							}
						}
						
						// caricamento allegati
						if(aBean.getTipoDoc() != null)
							request.setAttribute(ParametriServlet.DOCUMENTI, allAct.load(aBean));
						
						request.getSession().setAttribute(ParametriServlet.TIPODOC, tipoDoc);
						request.getSession().setAttribute(ParametriServlet.RETFIELD, retField);
						request.getSession().setAttribute(ParametriServlet.NOTEALL, note);
						request.getSession().setAttribute(SESSION_ID_GARA, idGara);
						request.getSession().setAttribute(ParametriServlet.IDALLEGATO, idAllegato);
						request.getSession().setAttribute(ParametriServlet.NOMEFILE, nomeFile);
												
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