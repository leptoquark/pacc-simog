package it.avlp.simog.garamanager.app;

import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avlp.simog.actions.ElencoInvitatiAction;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InvitatoBean;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.servlet.ServletBase;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SrvGestioneElencoInvitati extends ServletBase implements ParametriServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		perform(request, response);
	}

	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url = ParametriServlet.JSP_ELENCO_INVITATAI;
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		try{
		    currentActiveConnection = getSimogConnection(request.getSession().getId(),  getClass().getName());
		    currentActiveConnection.setAutoCommit(false);

		    if ( checkSession(request) ) {
				
		    	    /********************* BEGIN Gestione Actions *************************/
		    		
		    		String action = request.getParameter("toDo");
				    
					String idGara = request.getParameter(SESSION_ID_GARA);
					String actionType = request.getParameter(PSBD.ACTION_SALVA);
					
					
					TableBean dettagliGara = null;
			    	GaraManager garaManager = new GaraManager(currentActiveConnection, logger);	
			    	ElencoInvitatiAction eia = new ElencoInvitatiAction(currentActiveConnection,logger);

			    	if(currentUser.isRUP()){
			    		dettagliGara = garaManager.getDettagliGaraByIdGaraRSSA( idGara, currentUser.getUffici() );
			    	}else if(currentUser.isAmministratore()){
			    		dettagliGara = garaManager.getDettagliGaraByIdGara(idGara);
			    	}
			    	

			    	request.setAttribute(SESSION_ID_GARA, idGara);
			    	request.setAttribute(TABLEBEAN, dettagliGara);	
			    	
					
					  if ( ParametriServlet.ACTION_NEW.equals(action) )	{
					     
	                      if( SimogFlags.is3030_RFWEBGL02Active() ){
	                         String targetPage = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara
	                                           + "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;  
	                         
	                         AVCPassAction avpa = new AVCPassAction(currentActiveConnection, logger, configuration);
	                         if( avpa.isAVCPass(new Gara(Long.valueOf(idGara)), null, AVCPassFunzioneEnum.WEB_GESTIONE_ELENCO_INVITATI.getCodice()) ){
	                            AllValidationBeans msgs = new AllValidationBeans();
	                            msgs.addValidationErr(SIMOG_AVCPASS_001);
	                            sendValidations(request, response, msgs, targetPage);  
	                            return;
	                         }
	                      }					      
					     
						  ArrayList<InvitatoBean> invitatiBean=new ArrayList<InvitatoBean>();
						  invitatiBean=eia.caricaInviati(idGara);
					 	   request.setAttribute("invitati", invitatiBean);
						 forward(ParametriServlet.JSP_ELENCO_INVITATAI, request, response);
					  }else if(PSBD.ACTION_SALVA.equals(actionType)){
						  
						  ArrayList<InvitatoBean> invitati=eia.getBean(request);
						  eia.valida(invitati);
						  InvitatoBean invitato;
						   
						  
						  //request.setAttribute("invitati", invitati);
						  if(eia.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize()>0){
							  request.setAttribute("invitati", invitati);
							  sendValidations(request, response, eia.getEccezioni(), url);
						  }else{
							  eia.cancellaInvitato(idGara);		
							  for(int i=0; i<invitati.size(); i++){
								  invitato=invitati.get(i); 
								  eia.inserisciInvitato(invitato);
							  }
							  currentActiveConnection.commit();
							  request.setAttribute(SESSION_ID_GARA, idGara);
							  sendMessage(request, response, Messaggi.SIMOG_GARA_025, ParametriServlet.SRV_VISUALIZZA_DETTAGLIO);
							 // forward(ParametriServlet.SRV_VISUALIZZA_DETTAGLIO, request, response);
							  
							
							
	  
							 
						  }
						  
					  }
		    	
		    }
		    else{
	             sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
	                return;
	             }
		    
		}catch ( SimogException se ) {
		    se.printStackTrace();
		    rollback(currentActiveConnection);					
		    sendError( request, response, se.getMessage(), JSP_ERRORE);
		return;
	    } 
	    catch ( Exception e ) {
		    e.printStackTrace();
		    rollback(currentActiveConnection);
		    logger.fatal ( e.getMessage());
		    sendError(request, response, e.getMessage(), JSP_ERRORE);
		return;
	    } 
	    finally {		
		    closeConnection(request.getSession().getId(),getClass().getName());
	    }	
	}
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
