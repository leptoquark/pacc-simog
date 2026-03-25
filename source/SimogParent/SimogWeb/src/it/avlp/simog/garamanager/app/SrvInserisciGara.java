package it.avlp.simog.garamanager.app;

import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avlp.simog.actions.DelegaDatiSimogAction;
import it.avlp.simog.actions.GaraLottoAction;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.log.LogManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SrvInserisciGara extends ServletBase implements ParametriServlet {
	
	private static final long serialVersionUID = 1L;
	

	public void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		perform(request, response);
	}
	
	protected void perform(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		
		HttpSession currentActiveSession = request.getSession();
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		visualizzaListaParametriValori(request, response);
		Connection currentActiveConnection = null;
        // int isolationLevel = 0;
        
		if ( checkSession(request) ) {
			if ( currentUser.isRSSAorRUP() || currentUser.isAmministratore()) {
				
				String requestingURL = currentUser.isAmministratore() ? JSP_GESTIONE_GARE_EXT : JSP_GESTIONE_SCHEDE;		
				
				try {
					currentActiveConnection = getSimogConnection(request.getSession().getId(),  getClass().getName());
					//isolationLevel = currentActiveConnection.getTransactionIsolation();
				    //logger.debug("current isolation level: " + isolationLevel);
					
					currentActiveConnection.setAutoCommit(false);
//					 PP esagerata currentActiveConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);	
					//logger.debug("new isolation level: " + currentActiveConnection.getTransactionIsolation());

					//visualizzaListaParametriValori(request, response);		
					
					if(isRefresh(request)){
						try{
							requestingURL = ParametriServlet.SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" 
								+ currentActiveSession.getAttribute( SESSION_ID_GARA ).toString()
								+ "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;
							sendError(request, response, Messaggi.SIMOG_VALIDAZIONE_000, requestingURL );
							return;
						}catch (Exception e) {
							sendError(request, response, Messaggi.SIMOG_VALIDAZIONE_000, requestingURL );
							return;
						}
					}
					
					
					
					
					/********************* BEGIN Gestione Actions *************************/

					String action = request.getParameter("toDo");
					// INT87
	             if ( ParametriServlet.ACTION_MODIFICA_DL133.equals(action) ) {
	                  //modifica solo il valore del flag
	                long idGara = cambiaDL133(request, response, currentActiveConnection);
	                  
                   commit(currentActiveConnection);
	                  
                     String redirectUrl = SRV_VISUALIZZA_DETTAGLIO+"?"+ParametriServlet.SESSION_ID_GARA+"="+idGara
                     + "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;
                     
                     sendMessage(request, response, SIMOG_GARA_015.replace("$1", "numero ["+String.valueOf(idGara)+"]"), redirectUrl);
	               }
	             else	if ( ParametriServlet.ACTION_AGGIUNGI_GARA.equals(action) )	{
						//AGGIUNGI GARA
						long idGara = creaNuovaGara(request, response, currentActiveConnection);
						
						if(idGara > 0){
							commit(currentActiveConnection);
						
							String redirectUrl = SRV_VISUALIZZA_DETTAGLIO+"?"+ParametriServlet.SESSION_ID_GARA+"="+idGara
							+ "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;
							
							sendMessage(request, response, SIMOG_GARA_014.replace("$1", "numero ["+String.valueOf(idGara)+"]"), redirectUrl);
						}
					}
					else if ( ParametriServlet.ACTION_SALVA.equals(action) ){
//						 messaggi da ritornare
			    		AllValidationBeans msgs = new AllValidationBeans();
						//SALVA
						long idGara = salvaGara(request, response, currentActiveConnection, msgs);
						
						commit(currentActiveConnection);
					
						String redirectUrl = SRV_VISUALIZZA_DETTAGLIO+"?"+ParametriServlet.SESSION_ID_GARA+"="+idGara
						+ "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;	
					
						msgs.addValidationInfo(SIMOG_GARA_015.replace("$1", "numero ["+String.valueOf(idGara)+"]"));
						
						sendValidations(request, response, msgs, redirectUrl);
					}
					else if(ParametriServlet.SBLOCCA_GARA.equals(action) ){
					   
						GaraLottoAction garalotti= new GaraLottoAction(currentActiveConnection, logger, configuration);
						
						String id_gara = request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA);
						
						Gara lGara = garalotti.sbloccaGaraLotto(id_gara);
						
						/** Log sblocco Gara */
						LogManager logManager = new LogManager(currentActiveConnection, logger);
						logManager.log(getTodayDate(), lGara.getID_STAZIONE_APPALTANTE(), currentUser.getLogin(), "", LogManager.SBLOCCO_GARA, lGara.getCF_AMMINISTRAZIONE(), "", id_gara);

						commit(currentActiveConnection);
						
						String redirectUrl = JSP_GESTIONE_GARE_EXT;	
						
						String message = SIMOG_GARA_023.replace("$1", "numero ["+id_gara+"]");
						
						sendMessage(request, response, message, redirectUrl);	
					}else if(ParametriServlet.MODIFICA_REALIZZAZIONE_GARA.equals(action)){
						
						GaraLottoAction garalotti= new GaraLottoAction(currentActiveConnection, logger, configuration);
						
						String id_gara=request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA);
						
                        if( SimogFlags.is3030_RFWEBGL02Active() ){
                           AVCPassAction avpa = new AVCPassAction(currentActiveConnection, logger, configuration); 
                           if( avpa.isAVCPass(new Gara(Long.valueOf(id_gara)), null, AVCPassFunzioneEnum.WEB_MOV_TO_ACCORDO_QUADRO.getCodice()) ){
                              String targetPage = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + id_gara + "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;                               
                              AllValidationBeans msgs = new AllValidationBeans();
                              msgs.addValidationErr(SIMOG_AVCPASS_001);
                              sendValidations(request, response, msgs, targetPage);                            
                              return;
                           }
                        } 						
						
                        
                        //TICKET ALM #2847 - modalita realizzazione
                        GaraManager gm = new GaraManager(currentActiveConnection,logger);
                        String dataCreazione = gm.getGara(Long.parseLong(id_gara)).getData_creazione();
                        Gara gara;
                        if(SimogProperties.getInstance().isDataCreatedAfter3042(dataCreazione))
						    gara = garalotti.modificaRealizzazioneGara(id_gara, Costanti.MODOREAL_ACCORDO_QUADRO);//17 per le nuove gare
                        else
                        	gara = garalotti.modificaRealizzazioneGara(id_gara, Costanti.MODOREAL_ACCORDO);	//9 per le gare antecedenti l'attivazione la 3.04.2
                        //FINE TICKET ALM #2847 - modalita realizzazione
						
						/** Log modifica modalità di realizzazione  */
						LogManager logManager = new LogManager(currentActiveConnection, logger);
						logManager.log(getTodayDate(), gara.getID_STAZIONE_APPALTANTE(), currentUser.getLogin(), "", "Modifica in accordo quadro", gara.getCF_AMMINISTRAZIONE(), "", id_gara);

						commit(currentActiveConnection);
						
						String redirectUrl = JSP_GESTIONE_GARE_EXT;	
						
						String message = SIMOG_GARA_024.replace("$1", "numero ["+id_gara+"]");
						
						sendMessage(request, response, message, redirectUrl);	
					}
					
					/********************* END Gestione Actions *************************/
		
					// commit(currentActiveConnection);
					
					/** added ActionException perche e' l'eccezione lanciata dalle classi action */
				} catch ( ActionException ae ) {
					ae.printStackTrace();
					rollback(currentActiveConnection);					
					sendError( request, response, ae.getMessage(), requestingURL);
					return;
				} catch ( SimogException se ) {
					se.printStackTrace();
					rollback(currentActiveConnection);					
					sendError( request, response, se.getMessage(), requestingURL);
					return;
				} catch ( Exception e ) {
					e.printStackTrace();
					rollback(currentActiveConnection);
					logger.fatal ( e.getMessage());
					sendError(request, response, Messaggi.SIMOG_GARA_001, requestingURL, e);
					return;
				} finally {
				//	rollbackOrcommit(currentActiveConnection);					
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

	
	/************************************************************************************************
	 * Crea una nuova Gara 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param currentActiveConnection Connection
	 * @throws ServletException
	 * @throws IOException
	 */
	protected long creaNuovaGara(HttpServletRequest request,HttpServletResponse response, Connection currentActiveConnection)
		throws ServletException, IOException, ActionException, Exception {
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);			
		GaraLottoAction gla = new GaraLottoAction(currentActiveConnection,logger, configuration);
		Object obj = null;
		   
		/** il load fa anche la validazione */
		// obsoleto obj = gla.loadGaraFromRequestWithoutValidation(request);
//		Gara g = obj == null? null : (Gara)obj;
		
		/** PP solo info
		DelegaDatiSimogAction dasAction = new DelegaDatiSimogAction(currentActiveConnection, logger);
		if(g != null){
			String res = dasAction.getDelegaCIGMessage(g.getID_OSSERVATORIO(), PageHelper.getCurrentUtilDate(), currentUser);
			if(res != null && currentUser.isRSSAorRUP()){
//				sendError(request,response, res, SRV_VISUALIZZA_DETTAGLIO + "?" + ParametriServlet.FROM_GARE+"=");
				AllValidationBeans avb = new AllValidationBeans();
				avb.addValidationErr(res);
 				request.setAttribute(ParametriServlet.FIELD_NAME_ID_GARA, request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA));
				request.setAttribute(ParametriServlet.FIELD_NAME_ID_STATO_GARA, request.getParameter(ParametriServlet.FIELD_NAME_ID_STATO_GARA));
				request.setAttribute(ParametriServlet.FIELD_NAME_DATA_CONFERMA_GARA, request.getParameter(ParametriServlet.FIELD_NAME_DATA_CONFERMA_GARA));			
				sendValidations(request, response, avb, ParametriServlet.SRV_INIZIALIZZA_GARA);
				return 0;
			}
		}
**/				

//UN non so se previsto blocco creazione nuova gara
//        if( SimogFlags.is3030_RFWEBGL02Active() ){
//           String idGara = request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA);
//           Gara gara = new Gara();
//           gara.setIdGara(Long.valueOf(idGara));
//           AVCPassAction avpa = new AVCPassAction(currentActiveConnection, logger, configuration); 
//           if( avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_GARA_CREATE.getCodice()) ){
//              request.getSession().removeAttribute("requestNO");
//              request.setAttribute(ParametriServlet.FIELD_NAME_ID_GARA, request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA));
//              request.setAttribute(ParametriServlet.FIELD_NAME_ID_STATO_GARA, request.getParameter(ParametriServlet.FIELD_NAME_ID_STATO_GARA));
//              request.setAttribute(ParametriServlet.FIELD_NAME_DATA_CONFERMA_GARA, request.getParameter(ParametriServlet.FIELD_NAME_DATA_CONFERMA_GARA));         
//              sendError(request, response, SIMOG_AVCPASS_001, ParametriServlet.SRV_INIZIALIZZA_GARA);
//              return 0;
//           }
//        } 		

		// PP 05.08.2016 se esiste in sessione un numero gara prende i dati da quello, azzero per sicurezza
		// FIXME PP non lo so ci devo pensare request.getSession().setAttribute(ParametriServlet.SESSION_ID_GARA, "0");
		
		obj = gla.load(request, GaraLottoAction.TIPO_GARA);
		
		if(obj instanceof AllValidationBeans ){
	      
         AllValidationBeans avb = new AllValidationBeans();
		   
         if(obj instanceof AllValidationBeans){
            /** merge dei 2 risultati */
            avb.add((AllValidationBeans)obj);
         }

	      request.getSession().removeAttribute("requestNO");
			request.setAttribute(ParametriServlet.FIELD_NAME_ID_GARA, request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA));
			request.setAttribute(ParametriServlet.FIELD_NAME_ID_STATO_GARA, request.getParameter(ParametriServlet.FIELD_NAME_ID_STATO_GARA));
			request.setAttribute(ParametriServlet.FIELD_NAME_DATA_CONFERMA_GARA, request.getParameter(ParametriServlet.FIELD_NAME_DATA_CONFERMA_GARA));			

			sendValidations(request, response, avb, ParametriServlet.SRV_INIZIALIZZA_GARA);
			return 0;						
		}
		
	    // azzero i campi se la SA non è comune non capoluogo di provincia
      if(SimogFlags.isINT85_RFWEBGL01Active()
            && configuration.isINT85Attivo()
            && !Costanti.COD_SA_COMUNE.equals(((Gara)obj).getTIPOSA_BDNCP())){
         ((Gara)obj).setSCELTA_LEGGE89(0);
      }


		/** inserimento della gara */
		Gara gara = (Gara)obj;         

		gara = gla.insertGara(gara); 		//Restituisce Gara con l'id gara settato  
		request.setAttribute(ParametriServlet.FIELD_NAME_ID_GARA, request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA));
		
		/** Log Gara */
		LogManager logManager = new LogManager(currentActiveConnection, logger);
		//logManager.log(getTodayDate(), gara.getID_STAZIONE_APPALTANTE(), currentUser.getLogin(), "", LogManager.INS_GARA, gara.getCF_AMMINISTRAZIONE(), "", String.valueOf(gara.getId_Gara()));
		
		//Ticket ALM #692
		logManager.log(gara.getData_creazione()!=null ? gara.getData_creazione() : getTodayDate(), gara.getID_STAZIONE_APPALTANTE(), currentUser.getLogin(), "", LogManager.INS_GARA, gara.getCF_AMMINISTRAZIONE(), "", String.valueOf(gara.getId_Gara()));

		
		return gara.getId_Gara();
	}


	private List<String> arrayToList(Object[] obj2) {
      ArrayList<String> ret = new ArrayList<String>(); 
      
      for (int i = 0; i < obj2.length; i++) {
         ret.add((String) obj2[i]);
      }	      
	   return ret;
   }

   /************************************************************************************************
	 * Salva una Gara 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param currentActiveConnection Connection
	 * @throws ServletException
	 * @throws IOException
	 */
	protected long salvaGara(HttpServletRequest request,HttpServletResponse response, Connection currentActiveConnection, 
			AllValidationBeans msgs)
		throws ServletException, IOException, ActionException, Exception {
						
		GaraLottoAction gla = new GaraLottoAction(currentActiveConnection,logger, configuration);	
		Object obj = null;
		
		/** il load fa anche la validazione */
		obj = gla.load(request, GaraLottoAction.TIPO_GARA);      

      if(obj instanceof AllValidationBeans){
         
         AllValidationBeans avb = new AllValidationBeans();
         
         if(obj instanceof AllValidationBeans){
            /** merge dei 2 risultati */
            avb.add((AllValidationBeans)obj);
         }

         request.getSession().removeAttribute("requestNO");
         request.setAttribute(ParametriServlet.FIELD_NAME_ID_GARA, request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA));
         request.setAttribute(ParametriServlet.FIELD_NAME_ID_STATO_GARA, request.getParameter(ParametriServlet.FIELD_NAME_ID_STATO_GARA));
         request.setAttribute(ParametriServlet.FIELD_NAME_DATA_CONFERMA_GARA, request.getParameter(ParametriServlet.FIELD_NAME_DATA_CONFERMA_GARA));        
         request.setAttribute(ParametriServlet.EAGG_CATEGSEL_BEAN, request.getParameterValues(ParametriServlet.EAGG_CATEGSEL_BEAN));                  
         
         sendValidations(request, response, avb, ParametriServlet.SRV_INIZIALIZZA_GARA);
         return 0;                  
      }
 
		/** Settaggio id_gara */
		Gara gara = (Gara)obj;
		gara.setIdGara(Long.valueOf(request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA)));
		request.setAttribute(ParametriServlet.FIELD_NAME_ID_GARA, gara.getId_Gara());
		request.setAttribute(ParametriServlet.FIELD_NAME_ID_STATO_GARA, String.valueOf(gara.getID_STATO_GARA()));
		request.setAttribute(ParametriServlet.FIELD_NAME_DATA_CONFERMA_GARA, String.valueOf(gara.getDATA_CONFERMA_GARA()));
		
		// la modifica della gara fatta dall'amministratore ricalcola il contributo gara con la nuova logica
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);

		// azzero i campi se la SA non è comune non capoluogo di provincia, solo se sono RUP
		if(SimogFlags.isINT85_RFWEBGL01Active()
		      && configuration.isINT85Attivo()
		      && currentUser.isRSSAorRUP()
		      && !Costanti.COD_SA_COMUNE.equals(((Gara)obj).getTIPOSA_BDNCP())){
		   gara.setSCELTA_LEGGE89(0);
		}
		
		/** salvataggio della gara */
		gla.saveGara(gara, currentUser.isAmministratore(), msgs);

		/** Log Gara */
		LogManager logManager = new LogManager(currentActiveConnection, logger);
		logManager.log(getTodayDate(), gara.getID_STAZIONE_APPALTANTE(), gara.getCF_UTENTE(), "", LogManager.SAV_GARA, gara.getCF_AMMINISTRAZIONE(), "", String.valueOf(gara.getId_Gara()));
		
		return gara.getId_Gara();				
	}

	//INT87
   /************************************************************************************************
    * Aggiorna il flag DL133 
    * @param request HttpServletRequest
    * @param response HttpServletResponse
    * @param currentActiveConnection Connection
    * @throws ServletException
    * @throws IOException
    */
   protected long cambiaDL133(HttpServletRequest request,HttpServletResponse response, Connection currentActiveConnection)
      throws ServletException, IOException, ActionException, Exception {
                  
      GaraLottoAction gla = new GaraLottoAction(currentActiveConnection,logger, configuration); 
      
      /** il load fa anche la validazione */
      Gara gara = (Gara) gla.loadGaraFromDBSenzaValidazione(request);
      
      // altero il valore del flag
      gara.setURGENZA_DL133(("on".equals(request.getParameter("hidden"+ParametriServlet.FIELD_NAME_URGENZA_DL133)) 
            || Costanti.FLAG_VALORE_SI.equals(request.getParameter("hidden"+ParametriServlet.FIELD_NAME_URGENZA_DL133))) 
            ? Costanti.FLAG_VALORE_SI : Costanti.FLAG_VALORE_NO);
      
      //TICKET ALM - 3.04.2 2905
      int idUrgenza = Costanti.FLAG_VALORE_NO.equals(gara.getURGENZA_DL133()) ||
    		          request.getParameter( ParametriServlet.FIELD_NAME_ESTREMA_URGENZA )==null || 
    		          "".equals(request.getParameter( ParametriServlet.FIELD_NAME_ESTREMA_URGENZA )) ? 0 : Integer.valueOf((String) request.getParameter( ParametriServlet.FIELD_NAME_ESTREMA_URGENZA ));
      gara.setID_ESTREMA_URGENZA(idUrgenza);
      //FINE TICKET ALM - 3.04.2 2905

      gara.setIdGara(Long.valueOf(request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA)));
      request.setAttribute(ParametriServlet.FIELD_NAME_ID_GARA, gara.getId_Gara());
      request.setAttribute(ParametriServlet.FIELD_NAME_ID_STATO_GARA, String.valueOf(gara.getID_STATO_GARA()));
      request.setAttribute(ParametriServlet.FIELD_NAME_DATA_CONFERMA_GARA, String.valueOf(gara.getDATA_CONFERMA_GARA()));
            
      /** salvataggio della gara */
      gla.saveDL133(gara);

      /** Log Gara */
      LogManager logManager = new LogManager(currentActiveConnection, logger);
      logManager.log(getTodayDate(), gara.getID_STAZIONE_APPALTANTE(), gara.getCF_UTENTE(), "", LogManager.MOD_DL133, gara.getCF_AMMINISTRAZIONE(), "", String.valueOf(gara.getId_Gara()));
      
      return gara.getId_Gara();           
   }

}