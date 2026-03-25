package it.avlp.simog.garamanager.app;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import it.avlp.simog.beans.InfoRettificaBean;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.rettifica.InfoRettifica;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;

public class SrvAnnullaRettifica extends ServletBase implements ParametriServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void perform(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		  Connection currentActiveConnection = null;
		  HttpSession currentActiveSession = request.getSession();
	      Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
	      if ( checkSession(request) ) {
	         if (  ! currentUser.isAVLP()  ) {
	            
	            try {
	               currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
	               currentActiveConnection.setAutoCommit(false);
	               
	               setTabelleUtilita(request, currentActiveConnection, PageHelper.getCurrentDate(), false, null);
	               
	               
	               Object messageBean = request.getAttribute(ParametriServlet.ERRORBEAN);
	               if ( messageBean != null ) {
	                  request.setAttribute(ERRORBEAN, messageBean);
	               }
	               
	               // UN Caricare le informazioni sulla gara da modificare. Se idGara è null gara da creare
	               String idGara = request.getParameter(ParametriServlet.SESSION_ID_GARA);
	               if(idGara == null) 
	                  idGara = request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA);
	               
	               GaraManager gm = new GaraManager(currentActiveConnection, logger); 
	               
	               InfoRettifica infoRettifica = new InfoRettifica();
	               InfoRettificaBean infoRettificaBean = infoRettifica.getInfoRettifica(gm, idGara);

	               if (idGara != null ) {      
	            	   
	                  boolean result = gm.updateFlagSospesoPubblicazioni(infoRettificaBean.getIdPubblicazione());
	                  
	                  if(!result) {
	                	  
	                	  sendError(request, response, Messaggi.SIMOG_SQL_001, JSP_ERRORE );
		                  return;
	                  }
	                  
	                
	         		 String targetPage = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara
	                         + "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;
	         		
	         		sendMessage(request, response, "Rettifica annulata" + " ID_GARA [" + idGara + "]", targetPage);
 
	               }
	                            	               
	            } catch ( Exception sqle ) {
	                   rollback(currentActiveConnection);
	               sqle.printStackTrace();
	               sendError(request, response, SIMOG_GARA_005, JSP_ERRORE, sqle);
	               return;
	            } finally {
	                commit(currentActiveConnection);
	               closeConnection(request.getSession().getId(),getClass().getName());
	            }
	         } else {
	            sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
	            return;
	         }
	      } else {
	         sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
	         return;
	      }
	     

	}

}
