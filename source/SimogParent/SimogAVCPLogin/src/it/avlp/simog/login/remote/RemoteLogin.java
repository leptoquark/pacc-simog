/**
 * 
 */
package it.avlp.simog.login.remote;


import it.avcp.sicurezza.dto.ex.xsd.Check_login;
import it.avcp.sicurezza.dto.ex.xsd.Collaborazione;
import it.avcp.sicurezza.service.ex.LoginPortType;
import it.avcp.sicurezza.service.ex.LoginPortTypeProxy;
import it.avcp.simog.auth.manager.ws.Ilogin;
import it.avcp.simog.auth.manager.ws.IloginProxy;
import it.avlp.simog.login.MasterLogin;
import it.avlp.simog.ws.xmlbeans.CheckLoginDocument;
import it.avlp.simog.ws.xmlbeans.CollaborazioneType;
import it.avlp.simog.ws.xmlbeans.ProfiloType;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

/**
 * @author vletizia
 * 
 * Nota: final per la sicurezza, la classe non e' ulteriormente implementabile
 * 
 */
public final class RemoteLogin extends MasterLogin {

	public RemoteLogin(Logger logger){
		this.logger = logger;
	}

	
	/* (non-Javadoc)
	 * @see it.avlp.simog.login.MasterLogin#subClassImplementationForLogin(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected String subClassImplementationForLogin(String targetHost, String login, String password, String simogIdentifier) throws RemoteException{
		String loginResponse = new String();
		
		try{
			logger.debug( "Attenzione! accesso remoto abilitato - sara' invocato il WS remoto" );
			
			// TODO: PP OLD LOGIN ANCORA ATTIVO, il nuovo non sarà mai usato
			if(true){
				// 18.10.2012 patch per nuovo autenticatore IAM, devo usare il nuovo stub
			   if("NEW:".equals(targetHost.substring(0,4))){
			      String veroHost = targetHost == null ? null : targetHost.replace("NEW:", "");
			      
			      it.avcp.iam.service.wsdl.impl.IloginProxy  remoteAVCPAuthenticationProxy = null; 
                  
                  remoteAVCPAuthenticationProxy = new it.avcp.iam.service.wsdl.impl.IloginProxy(veroHost);
                  
                  logger.info("Inoltrata richiesta autenticazione WS Remoto AVCP (IAM) [" + remoteAVCPAuthenticationProxy.getEndpoint() + "]");

                  it.avcp.iam.service.wsdl.impl.Ilogin loginManager = remoteAVCPAuthenticationProxy.getIlogin();
      
                  loginResponse = loginManager.check_login(login, password, simogIdentifier );
			   }
			   else{
	                IloginProxy remoteAVCPAuthenticationProxy = null; 
	                
	                if (targetHost == null)
	                    remoteAVCPAuthenticationProxy = new IloginProxy();
	                else
	                    remoteAVCPAuthenticationProxy = new IloginProxy(targetHost);
	                
	                logger.info("Inoltrata richiesta autenticazione WS Remoto AVCP [" + remoteAVCPAuthenticationProxy.getEndpoint() + "]");

	                Ilogin loginManager = remoteAVCPAuthenticationProxy.getIlogin();
	    
	                loginResponse = loginManager.check_login(login, password, simogIdentifier );    
			   }      
				
			// PP NEW LOGIN (engineering, oramai abbandonato)
			}else{
				LoginPortTypeProxy remoteAVCPAuthenticationProxy = new LoginPortTypeProxy();
		
				if (targetHost != null)
					remoteAVCPAuthenticationProxy.setEndpoint(targetHost);
				
				logger.info("Inoltrata richiesta autenticazione NUOVO WS Remoto AVCP [" + remoteAVCPAuthenticationProxy.getEndpoint() + "]");
		
				LoginPortType loginManager = remoteAVCPAuthenticationProxy.getLoginPortType();
		
				Check_login responseXML = loginManager.check_login(login, password, simogIdentifier);
		
				// devo ricostruire l'xml per non toccare il codice successivo
				loginResponse = this.getXMLfromNewLogin(responseXML);
			}
			logger.debug ( "Ricevuta risposta [" + loginResponse + "]" );
			
		}catch (RemoteException re) {
			throw re;
		}

		return loginResponse;
	}
	/**
				if (true){
					// PP cambiata chiamataIloginProxy remoteAVCPAuthenticationProxy = new IloginProxy();
					IloginProxy remoteAVCPAuthenticationProxy = null; 
					
					if (configuration.getWS_AUTH_TARGET_HOST() == null)
						remoteAVCPAuthenticationProxy = new IloginProxy();
					else
						remoteAVCPAuthenticationProxy = new IloginProxy(configuration.getWS_AUTH_TARGET_HOST());
					
					logger.info("Inoltrata richiesta autenticazione WS Remoto AVCP [" + remoteAVCPAuthenticationProxy.getEndpoint() + "]");
	
					Ilogin loginManager = remoteAVCPAuthenticationProxy.getIlogin();
		
					loginResponse = loginManager.check_login(login, password, configuration.getSIMOG_IDENTIFIER() );
					logger.debug ( "Ricevuta risposta [" + loginResponse + "]" );
				}
				else {
					LoginPortTypeProxy remoteAVCPAuthenticationProxy = new LoginPortTypeProxy();

					if (configuration.getWS_AUTH_TARGET_HOST() != null)
						remoteAVCPAuthenticationProxy.setEndpoint(configuration.getWS_AUTH_TARGET_HOST());
					
					logger.info("Inoltrata richiesta autenticazione NUOVO WS Remoto AVCP [" + remoteAVCPAuthenticationProxy.getEndpoint() + "]");
	
					LoginPortType loginManager = remoteAVCPAuthenticationProxy.getLoginPortType();
		
					Check_login responseXML = loginManager.check_login(login, password, configuration.getSIMOG_IDENTIFIER());

					// devo ricostruire l'xml per non toccare il codice successivo
					xMan.getXMLfromNewLogin(responseXML);
					
					logger.debug ( "Ricevuta risposta [" + loginResponse + "]" );
				}
	 **/
	/**
	 * riscostruzione del file xml a partre dai dati ritornati dal nuovo servizio di login 
	 */
	private String getXMLfromNewLogin(Check_login inpData){
		
		String retVal = "";
		
		CheckLoginDocument dLogin = CheckLoginDocument.Factory.newInstance();
		
		// tag iniziale
		dLogin.addNewCheckLogin();
		
		// dati del soggetto
		if(inpData.getSoggetto() != null){
			dLogin.getCheckLogin().addNewSoggetto();
			dLogin.getCheckLogin().getSoggetto().setCognome(inpData.getSoggetto().getCognome());
			dLogin.getCheckLogin().getSoggetto().setNome(inpData.getSoggetto().getNome());
			dLogin.getCheckLogin().getSoggetto().setTel(inpData.getSoggetto().getTel());
			dLogin.getCheckLogin().getSoggetto().setFax(inpData.getSoggetto().getFax());
			dLogin.getCheckLogin().getSoggetto().setEmail(inpData.getSoggetto().getEmail());
			dLogin.getCheckLogin().getSoggetto().setAdminOr(inpData.getSoggetto().getAdmin_or());
		}
		
		// collaborazioni
		if(inpData.getCollaborazioni() != null){
			dLogin.getCheckLogin().addNewCollaborazioni();
			for (int i = 0; i < inpData.getCollaborazioni().length; i++) {
				Collaborazione coll = inpData.getCollaborazioni(i);
				
				// nuova collaborazione
				CollaborazioneType elem = dLogin.getCheckLogin().getCollaborazioni().addNewCollaborazione();
					
				// indice della collaborazione
				elem.setIndex(coll.getIndex());
				
				// azienda
				elem.addNewAzienda();
				elem.getAzienda().setCodiceFiscale(coll.getAzienda().getCodice_fiscale());
				elem.getAzienda().setDenominazione(coll.getAzienda().getDenominazione());
				elem.getAzienda().setIdOsservatorio(coll.getAzienda().getId_osservatorio());

				// ufficio
				elem.addNewUfficio();
				elem.getUfficio().setIdUfficio(coll.getUfficio().getId_ufficio());
				elem.getUfficio().setDenominazione(coll.getUfficio().getDenominazione());
				elem.getUfficio().setProfilo(coll.getUfficio().getProfilo());
			}
		}
		
		// stato
		if(inpData.getStato() != null){
			dLogin.getCheckLogin().setStato(inpData.getStato());
		}

		// ritorno l'xml
		retVal = dLogin.toString();
		
		return retVal;
	}

	/* TICKET ALM - 3.04.3
	 * (non-Javadoc)
	 * @see it.avlp.simog.login.MasterLogin#subClassImplementationForLogin(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected String subClassImplementationForLoginRPNT(String targetHost, String login, String password, String cfrup, String simogIdentifier) throws RemoteException{
		String loginResponse = new String();
		
		try{
			logger.debug( "Attenzione! accesso remoto abilitato - sara' invocato il WS remoto" );
			
			// TODO: PP OLD LOGIN ANCORA ATTIVO, il nuovo non sarà mai usato
			if(true){
				// 18.10.2012 patch per nuovo autenticatore IAM, devo usare il nuovo stub
			   if("NEW:".equals(targetHost.substring(0,4))){
			      String veroHost = targetHost == null ? null : targetHost.replace("NEW:", "");
			      
			      it.avcp.iam.service.wsdl.impl.IloginProxy  remoteAVCPAuthenticationProxy = null; 
                  
                  remoteAVCPAuthenticationProxy = new it.avcp.iam.service.wsdl.impl.IloginProxy(veroHost);
                  
                  logger.info("Inoltrata richiesta autenticazione WS Remoto AVCP (IAM) [" + remoteAVCPAuthenticationProxy.getEndpoint() + "]");

                  it.avcp.iam.service.wsdl.impl.Ilogin loginManager = remoteAVCPAuthenticationProxy.getIlogin();
      
                  loginResponse = loginManager.check_loginRPNT(login, password, cfrup, simogIdentifier );
			   }
			   else{
	                IloginProxy remoteAVCPAuthenticationProxy = null; 
	                
	                if (targetHost == null)
	                    remoteAVCPAuthenticationProxy = new IloginProxy();
	                else
	                    remoteAVCPAuthenticationProxy = new IloginProxy(targetHost);
	                
	                logger.info("Inoltrata richiesta autenticazione WS Remoto AVCP [" + remoteAVCPAuthenticationProxy.getEndpoint() + "]");

	                Ilogin loginManager = remoteAVCPAuthenticationProxy.getIlogin();
	    
	                loginResponse = loginManager.check_loginRPNT(login, password, cfrup, simogIdentifier ); 
			   }      

			}
			
		}catch (RemoteException re) {
			throw re;
		}

		return loginResponse;
	}
	
}
