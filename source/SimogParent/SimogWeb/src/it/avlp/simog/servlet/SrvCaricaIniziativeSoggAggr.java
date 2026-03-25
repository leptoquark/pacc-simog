package it.avlp.simog.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.avlp.simog.beans.AllegatoBean;
import it.avlp.simog.beans.IniziativaSoggAggr;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.action.AllegatiAction;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.garamanager.AllegatoManager;
import it.avlp.simog.garamanager.IniziativaManager;
import it.avlp.simog.util.PageHelper;

/**
 * Servlet implementation class SrvCaricaIniziative
 */
public class SrvCaricaIniziativeSoggAggr extends ServletBase implements ParametriServlet {
	private static final long serialVersionUID = 1L;
       

	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
				perform(request, response);
			}
			
			protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				Connection currentActiveConnection = null;
				String requestingUrl = ParametriServlet.JSP_POPUP_SOGG_AGGR;
				String listaIdIniziativeStr = (String)request.getParameter("iniziative");
				String from = (String)request.getParameter("from");
				String[] arrayIdIniziative =  listaIdIniziativeStr.split("~~");
                List<IniziativaSoggAggr> listaIniziative = new ArrayList<IniziativaSoggAggr>();
				if ( checkSession(request) ) {

						 System.out.println("TECHNIS from "+from);
						try {
							currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
                            IniziativaManager im = new IniziativaManager(currentActiveConnection,logger);

                            //Si cicla fino al penultimo elemento in quanto l'ultimo e' una stringa vuota
                            for(int i=0;i<arrayIdIniziative.length;i++) {
                            	String idInizStr = arrayIdIniziative[i];
                            	if (idInizStr != null && !"".equals(idInizStr)) {
                            	    try {
                            	        long idIniz = Long.parseLong(idInizStr);
                            	        java.util.List<IniziativaSoggAggr> listIniz = im.getIniziative(null, null, null, null, idIniz, true);
                            	        if (listIniz != null && !listIniz.isEmpty()) {
                            	            listaIniziative.add(listIniz.get(0));
                            	        }
                            	    } catch (NumberFormatException e) {
                            	        logger.warn("idIniziativa non numerico: " + idInizStr);
                            	    }
                            	}
                            }
	
							request.setAttribute(ParametriServlet.LISTA_INIZIATIVE_DISPONIBILI, listaIniziative);
							request.setAttribute("from", from);
								
								forward(requestingUrl, request, response);
							
						} catch ( Exception e ) {
							e.printStackTrace();
							
							sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
						
						} finally {
							closeConnection(request.getSession().getId(),getClass().getName());
						}
				
				} else {
					sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
					
				}
			}



}
