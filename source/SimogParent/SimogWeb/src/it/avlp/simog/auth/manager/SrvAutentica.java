package it.avlp.simog.auth.manager;


import it.avcp.iam.model.impl.IAMPrincipalImpl;
import it.avcp.simog.auth.RicercaProfiloRASA;
import it.avcp.simog.auth.XmlManager;
import it.avcp.simog.managers.bdncp.BdncpManager;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.RubricaResponsabili;
import it.avlp.simog.beans.SoglieImpEnum;
import it.avlp.simog.beans.StazioneAppaltante;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.login.LoginManager;
import it.avlp.simog.login.iaa.IAACostanti;
import it.avlp.simog.rubricamanager.RubricaResponsabiliManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.xmlbeans.SoggettoType;

import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("unchecked")
public class SrvAutentica extends ServletBase {

	private static final long serialVersionUID = 1L;
	
	private static HashMap <String, String> paginaProfilo = new HashMap() ;
	
//	static {
//		paginaProfilo.put ( ProfiloEnum.AMMINISTRATORE.codice(), JSP_AMM_HOME );
//		paginaProfilo.put ( ProfiloEnum.AVLP.codice(), JSP_AVCP_HOME );
//		paginaProfilo.put ( ProfiloEnum.RSSA.codice(), JSP_RSSA_HOME );
//		paginaProfilo.put ( ProfiloEnum.RUP.codice(), JSP_RUP_CS_HOME );
////		 PP rimosso paginaProfilo.put ( ProfiloUtente.CS, JSP_RUP_CS_HOME );
//	}
	


   /**
	 * @see ServletBase#perform(HttpServletRequest, HttpServletResponse)
	 */
	protected void perform(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		Connection currentActiveConnection = null;
		HttpSession currentActiveSession = request.getSession();
		
		paginaProfilo.clear();
		paginaProfilo.put ( ProfiloEnum.AMMINISTRATORE.codice(), JSP_AMM_HOME );
		paginaProfilo.put ( ProfiloEnum.AVLP.codice(), JSP_AVCP_HOME );
		paginaProfilo.put ( ProfiloEnum.RSSAOLD.codice(), JSP_RSSA_HOME );
		paginaProfilo.put ( ProfiloEnum.RUP.codice(), JSP_RUP_CS_HOME );
		paginaProfilo.put ( ProfiloEnum.OSSREG.codice(), JSP_OSSREG_HOME );
		paginaProfilo.put ( ProfiloEnum.RASA.codice(), JSP_AMM_HOME );

	      // pagina di rimando per errori
        String nextPage = ParametriServlet.JSP_ERRORE; 

		String samlResponse = null;
		
		logger.debug("*** Richiesta autenticazione ");                  
		// verifico se ho una asserzione in request, è buona sempre quella che arriva da IAA
		if (request.getParameter(IAACostanti.SAML_RESPONSE) != null){			
			samlResponse = (String) request.getParameter(IAACostanti.SAML_RESPONSE);
			logger.debug("*** SAML IAA da request: " +  samlResponse);					
		} 
		else {
		   // vedo se esiste una SAML IAM
		   try {
            samlResponse = getSamlFromIAM();
           } catch (Exception e) {
              e.printStackTrace(); 
               sendError(request, response, SIMOG_LOGIN_001, nextPage );
           }
		   
		   if (samlResponse != null){           
	           logger.debug("*** SAML IAM da request: " +  samlResponse);                  
	       } 
	       else{
	           logger.debug("*** SAML da request e' NULL");     
		   }
		}
// PP 19.04.2012
//		else if (currentActiveSession.getAttribute(IAACostanti.SIMOG_SAML_RESPONSE) != null){
//				// se ne esiste una salvata in sessione prendo quella
//				samlResponse = (String) currentActiveSession.getAttribute(IAACostanti.SIMOG_SAML_RESPONSE);
//logger.debug("*** SAML da sessione: " +  samlResponse);					
//		}
		
		
		// login tradizionale?
		String login = request.getParameter(ParametriServlet.FIELD_NAME_LOGIN);
		if(login != null) login = login.toUpperCase().trim(); 
			
		String password = request.getParameter(ParametriServlet.FIELD_NAME_PASS);
		
		// se arrivo qui senza SAML e senza login c'è qualcosa che non va, ripropongo la pagina di login interna
		if(samlResponse == null && login == null){
		   forward(JSP__LOGIN, request, response);
           return;
		}
		
		//  PP eliminato per verifica crash dei server logger.info( "Tentativo di Accesso con credenziali [" + login + "] " +( password.length()>0 ? "e Codice Controllo: " + CodiceControllo.getCodiceControllo(password) :"") ); 

		int stato = -1;
		String adminOr = "";
		String messaggioEsito = "Impossibile accedere";
//old		String prossimaPagina = ParametriServlet.JSP_RSSA_HOME;

		String loginResponse = "";

		// PP nuovo parse tramite XmlBeans
		XmlManager xMan = new XmlManager(logger);

		// url di logout di default o saml
		if(configuration.getSamlLogoutUrl() != null){
			currentActiveSession.setAttribute(ParametriServlet.LOGOUT_URL, "IAALogout");
			currentActiveSession.setAttribute(ParametriServlet.CHANGEPROF_URL, "IAACambiaProfilo");
			currentActiveSession.setAttribute(ParametriServlet.LOGIN_URL, configuration.getSamlLoginUrl());
		}
		else{
			currentActiveSession.setAttribute(ParametriServlet.LOGOUT_URL, "logout");
			currentActiveSession.setAttribute(ParametriServlet.CHANGEPROF_URL, null);
			currentActiveSession.setAttribute(ParametriServlet.LOGIN_URL, JSP__LOGIN);
		}
		
		try {
			currentActiveConnection = getSimogConnection(currentActiveSession.getId(),getClass().getName());
			try{
				// login tramite IAA
				if (samlResponse != null) {
					
					LoginManager loginManager = new LoginManager(logger,LoginManager.TipoLogin.IAA);
					loginResponse = loginManager.login(configuration.getSamlCertificate(), samlResponse, null, null);	
					if(loginResponse == null){
				    	sendError(request, response, SIMOG_LOGIN_002, nextPage );
				    	return;
					}
					
					// imposto il codice utente
					login = loginManager.getUserName();
					
					// prendere da bdncp i dati mancanti
					// cfamm, idosservatorio, idufficio

// PP				currentActiveConnection = getSimogConnection(currentActiveSession.getId(),getClass().getName());
					BdncpManager bdncpm = new BdncpManager(currentActiveConnection, logger);
					
					BdncpManager.DatiAmmUff dati = bdncpm.loadDatiAmm(loginManager.getCodAmm(), loginManager.getCodUff());
					if (dati != null){
						loginManager.getLogin().addAmmInfo(dati.getCfAmmin(), dati.getIdOsservatorio(), dati.getIdUfficio(), dati.getDenomUfficio());					
						loginResponse = loginManager.getCld().xmlText();
					}
					else{
                       loginManager.getLogin().addAmmInfo(IAACostanti.DUMMY_VAL, ProfiloEnum.REGIONE_ZERO, IAACostanti.DUMMY_VAL, IAACostanti.DUMMY_VAL);                   
                       loginResponse = loginManager.getCld().xmlText();
					}

// DEBUUUGGGG
//					loginManager.getCld().getCheckLogin().getCollaborazioni().getCollaborazioneArray(0).getAzienda().setCodiceFiscale("00000000000");
//					loginManager.getCld().getCheckLogin().getCollaborazioni().getCollaborazioneArray(0).getAzienda().setIdOsservatorio("012");
//					loginManager.getCld().getCheckLogin().getCollaborazioni().getCollaborazioneArray(0).getUfficio().setIdUfficio("11111");
//					loginResponse = loginManager.getCld().xmlText();
// DEBUUUGGGG
					// login effettuato salvo l'asserzione SAML in sessione per riusarla in caso di ritorno
// PP 19.04.2012					currentActiveSession.setAttribute(IAACostanti.SIMOG_SAML_RESPONSE, samlResponse);
				} 
				else {
					// login tramite pagina di accesso
					LoginManager loginManager = new LoginManager(logger,configuration.isLocalAuth());
					loginResponse = loginManager.login(configuration.getWS_AUTH_TARGET_HOST(),login, password, configuration.getSIMOG_IDENTIFIER());
				}
				
		    } catch(Exception e) {
		       e.printStackTrace();
		       logger.error(e.getMessage());
		       
		       // errore del caricamento del file di test xml
//		    	if(e instanceof IOException){
		    		
			    	sendError(request, response, SIMOG_LOGIN_001, nextPage );
			    	return;
			    	
			    // errore dell'invocazione del servizio remoto	
//		    	}else if(e instanceof RemoteException) {
//		    		
//		    		throw (RemoteException)e;
//		    		
		    	// varie ed eventuali	
//		    	}else{
//		    		
//		    		throw e;	
//		    	}
		    }  

			// PP nuovo parse tramite XmlBeans
		    
		    logger.debug(loginResponse);
		    
			xMan.validaXsd(loginResponse);
			
			Utente currentUser = null;
			
			stato = Integer.parseInt(xMan.getXmlBean().getStato());
			messaggioEsito = xMan.getXmlBean().getMessaggio();
			
			SoggettoType soggetto = xMan.getXmlBean().getSoggetto();
			
			if(soggetto !=null)	adminOr = soggetto.getAdminOr();
			
			if(stato >= 0 && messaggioEsito == null) {
				currentUser = new Utente(xMan.getXmlBean(),logger);
				if(currentUser.getProfili().size() == 0) {
					messaggioEsito = "Nessun profilo trovato per l'utente "+login;
				}
			}
			
			// utente validato
			if ( (stato >= 0 && messaggioEsito == null) || (stato == 0 && messaggioEsito != null && !ProfiloEnum.REGIONE_ZERO.equals(adminOr))) { 
				currentUser = new Utente(xMan.getXmlBean(),logger);
				
				currentUser.setLogin(login);

				// PP organi costituzionali, memorizzo se ha almeno una collaborazione su un OC
				if(currentUser.getProfilo() != null){
					GaraManager gm = new GaraManager(currentActiveConnection, logger);
					Object[] arrAmm = (Object[]) currentUser.getAmministrazioni().keySet().toArray();
					for (int i = 0; i < arrAmm.length; i++) {
						if(gm.isOrganoCost((String)arrAmm[i], null)){
							currentUser.setOrgano(true);
							break;
						}
					}
				}
				
				if(currentUser.isCaricaRasa()) {
					String wsIam = configuration.getWS_AUTH_TARGET_HOST();
					if(wsIam.contains("NEW"))
						wsIam = wsIam.split("NEW:")[1];
					
					wsIam = wsIam.replace("Ilogin", "rs/gestioneProfilo/ricercaProfilo");
					RicercaProfiloRASA rest = new RicercaProfiloRASA(wsIam);
					List<String> cfRasa = rest.callRicercaProfiloAUSA(login);
					for(String cf : cfRasa)
						currentUser.addListaRasa(cf);
					currentUser.setDenomRasa(rest.getDenominazioniRasa());
				}
				
				// INT 85 integrazione informazioni su tipo di comune
				if(configuration.isINT85Attivo()){
				   BdncpManager bm = new BdncpManager(currentActiveConnection, logger);

				   Hashtable <String, StazioneAppaltante> uffici = currentUser.getUfficiByProfilo(ProfiloEnum.RUP);
				   for(StazioneAppaltante elem : uffici.values()){
				      // prima di fare la query cerco nellla lista che sto aggiornando
				      String tipoSA = currentUser.getTipoSA(elem.getCodiceFiscaleAmministrazione());
				      if("".equals(tipoSA) || tipoSA == null){
				         String ret = "";
				         if(!SimogFlags.isNUOVO_TIPOSAActive()) 
				            ret = bm.loadTipoSA(elem.getCodiceFiscaleAmministrazione());
				         else
				            ret = bm.loadTipoSANew(elem.getIdUfficio());
				         
				         elem.getAmministrazione().setTipoSA(ret);
				      }
				      else{
				         elem.getAmministrazione().setTipoSA(tipoSA);
				      }
				   }
				}
				
				logger.info ( "Accesso eseguito [" + currentUser.getLogin() + "] da [" + request.getRemoteAddr() + "]");
				currentActiveSession = request.getSession(true);
				currentActiveSession.setAttribute(UTENTE, currentUser);
				
				// non ha collaborazioni ed e' RUP -> errore
				if(stato == 0 && currentUser.isRUP()){
					currentActiveSession.removeAttribute(UTENTE);
					sendError(request, response, Messaggi.SIMOG_LOGIN_002, nextPage );
					return;
				}
				
				//preventing refresh
				currentActiveSession.setAttribute(ParametriServlet.checkIfOK, new Integer(0));

				//Fasce di importo per la ricerca delle gare
				currentActiveSession.setAttribute(ParametriServlet.SOGLIE_IMPORTO, SoglieImpEnum.loadSoglieImporto());
				
				// verifica se il sistema è operativo
				try {
					checkService(request);
					
					if((Boolean) currentActiveSession.getAttribute(SERVICE_AVAILABLE) == false){
						forward("ServizioNonDisponibile.jsp", request, response);
						return;
					}
					
				} catch (Exception e) {		        
					logger.fatal( "checkService: Eccezione: " + e.getMessage() );
					sendError(request, response, e.getMessage(), nextPage );
					return;
				}
				
				// verifica se il sistema è sospeso (ore notturne)
				try {
					checkServiceTime(request);
					
					if((Boolean) currentActiveSession.getAttribute(SERVICE_AVAILABLE) == false){
						forward("ServizioNonDisponibile.jsp", request, response);
						return;
					}
					
				} catch (Exception e) {		        
					logger.fatal( "checkServiceTime: Eccezione: " + e.getMessage() );
					sendError(request, response, e.getMessage(), nextPage );
					return;
				}

				// creazione automatica responsabile
				if(currentUser.hasRUP()){
// PP				currentActiveConnection = getSimogConnection(currentActiveSession.getId(),getClass().getName());
					RubricaResponsabiliManager rubrman = new RubricaResponsabiliManager(currentActiveConnection, logger); 
					if(rubrman.checkCF(currentUser.getLogin().toUpperCase())){
						RubricaResponsabili rek = new RubricaResponsabili();
						rek.setCodice_fiscale_responsabile(currentUser.getLogin().toUpperCase());
						rek.setCognome(currentUser.getCognome());
						rek.setEmail(currentUser.getEmail());
						rek.setFax(currentUser.getFax());
						rek.setNome(currentUser.getNome());
						rek.setTelefono(currentUser.getTel());
						
						rubrman.insertPartecipante(rek, false);
					}
				}
				
				if(currentUser.getProfili().size() == 1){
					request.setAttribute(SCELTA_PROFILO, currentUser.getProfili().keys().nextElement());
					forward(SRV_SCELTA_PROFILO, request, response);
				} else 
					forward(JSP_PROFILO, request, response);
				
				
			} else {
				logger.warn( "Esito negativo da richiesta di autenticazione LOGIN[" + login + "] STATO[" + stato + "] MESSAGGIO[" + messaggioEsito + "] richiedente[" + request.getRemoteAddr() + "]" );
				sendError(request, response, messaggioEsito, samlResponse != null ? nextPage : JSP__LOGIN);
			}
		} catch (Exception se) {
			se.printStackTrace();
			sendError(request, response, SIMOG_LOGIN_001, nextPage, se);
		}
		finally{
			if (currentActiveConnection != null) closeConnection(currentActiveSession.getId(),getClass().getName());
		}
	}

   private String getSamlFromIAM() throws Exception {
      String retVal = null;

      Subject caller = null;

      try {
         caller = (Subject) PolicyContext
               .getContext("javax.security.auth.Subject.container");
      } catch (PolicyContextException e) {
         logger.fatal("*** IAM:" + e.getMessage());
         throw (e);
      }

      IAMPrincipalImpl iam = null;

      if (caller != null) {
         Set<Principal> set = caller.getPrincipals();

         for (Principal principal : set) {
            if (principal != null) {
               // logger.debug("Prinncipal != null" + principal);
               // logger.debug("IAMPrincipalImpl.class: " +
               // IAMPrincipalImpl.class.getName());
               // logger.debug("principal.class: " +
               // principal.getClass().getName());

               if (principal instanceof IAMPrincipalImpl) {
                  // logger.debug("Principal is IAM " + principal);
                  iam = (IAMPrincipalImpl) principal;
//                  logger.debug("IAM: " + iam);
//                  logger.debug("IAM.getSAML: " + iam.getSAML());
                  retVal = iam.getSAML();
               }
            }
         }
      }

      return retVal;
   }

	/*
	 * //Diego metodo Mock test public String test () {
	 * 
	 * String test = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
	 * "<check_login>\r\n" + "	<soggetto>\r\n" +
	 * "		<cognome>Rossi</cognome>\r\n" + "		<nome>Mario</nome>\r\n" +
	 * "		<tel>081332244</tel>\r\n" + "		<fax>0814433612</fax>\r\n" +
	 * "		<admin_or>009</admin_or>\r\n" + "	</soggetto>\r\n" +
	 * "	<collaborazioni>\r\n" + "		<collaborazione index=\"0\">\r\n" +
	 * "			<azienda>\r\n" + "				<denominazione/>\r\n" +
	 * "				<codice_fiscale/>\r\n" +
	 * "				<id_osservatorio>N/A</id_osservatorio>\r\n" +
	 * "			</azienda>\r\n" + "			<ufficio>\r\n" +
	 * "				<denominazione/>\r\n" +
	 * "				<id_ufficio>null</id_ufficio>\r\n" +
	 * "				<profilo>66</profilo>\r\n" + "			</ufficio>\r\n" +
	 * "		</collaborazione>\r\n" + "		<collaborazione index=\"1\">\r\n" +
	 * "			<azienda>\r\n" +
	 * "				<denominazione>COMUNE DI FIRENZE</denominazione>\r\n" +
	 * "				<codice_fiscale>01307110484</codice_fiscale>\r\n" +
	 * "				<id_osservatorio>009</id_osservatorio>\r\n" +
	 * "			</azienda>\r\n" + "			<ufficio>\r\n" +
	 * "				<denominazione>P.O. COORDINAMENTO PEDAGOGICO SERVIZIO SCUOLA DELL&apos;INFANZIA</denominazione>\r\n"
	 * +
	 * "				<id_ufficio>78EAA611-A984-46F1-B8D5-678C7314767D</id_ufficio>\r\n"
	 * + "				<profilo>1</profilo>\r\n" + "			</ufficio>\r\n" +
	 * "		</collaborazione>\r\n" + "		<collaborazione index=\"2\">\r\n" +
	 * "			<azienda>\r\n" +
	 * "				<denominazione>RASA1</denominazione>\r\n" +
	 * "				<codice_fiscale>01307110484</codice_fiscale>\r\n" +
	 * "				<id_osservatorio>19</id_osservatorio>\r\n" +
	 * "			</azienda>\r\n" + "			<ufficio>\r\n" +
	 * "				<denominazione>TEST 2</denominazione>\r\n" +
	 * "				<id_ufficio>ED9F0AA4-A910-4B55-A713-DA51D06E3CA6</id_ufficio>\r\n"
	 * + "				<profilo>19</profilo>\r\n" + "			</ufficio>\r\n" +
	 * "		</collaborazione>\r\n" + "		<collaborazione index=\"8\">\r\n" +
	 * "			<azienda>\r\n" +
	 * "				<denominazione>RASA2</denominazione>\r\n" +
	 * "				<codice_fiscale>81001430644</codice_fiscale>\r\n" +
	 * "				<id_osservatorio>19</id_osservatorio>\r\n" +
	 * "			</azienda>\r\n" + "			<ufficio>\r\n" +
	 * "				<denominazione>TEST 2 RASA</denominazione>\r\n" +
	 * "				<id_ufficio>ED9F0AA4-A910-4B55-A713-DA51D06E3CA6</id_ufficio>\r\n"
	 * + "				<profilo>19</profilo>\r\n" + "			</ufficio>\r\n" +
	 * "		</collaborazione>\r\n" + "     <collaborazione index=\"0\">\r\n" +
	 * "			<azienda>\r\n" +
	 * "				<denominazione>AUTORITA DELLA VIGILANZA CONTRATTI PUBBLICI</denominazione>\r\n"
	 * + "				<codice_fiscale>97163520584</codice_fiscale>\r\n" +
	 * "		 		<id_osservatorio>014</id_osservatorio>\r\n" +
	 * "		    </azienda>\r\n" + "		    <ufficio>\r\n" +
	 * "		    	<denominazione>VIGILANZA LAVORI</denominazione>\r\n" +
	 * "		    	<id_ufficio>91A37730-3958-43F6-A6F7-726A7C1F664E</id_ufficio>\r\n"
	 * + "		    	<profilo>101</profilo>\r\n" + "		    </ufficio>\r\n" +
	 * "     </collaborazione>\r\n" + "	</collaborazioni>\r\n" +
	 * "	<stato>7</stato>\r\n" + "</check_login>\r\n" + ""; return test; }
	 */
}