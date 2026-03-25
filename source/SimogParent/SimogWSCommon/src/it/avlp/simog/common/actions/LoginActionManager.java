package it.avlp.simog.common.actions;


import it.avcp.simog.auth.RicercaProfiloRest;
import it.avlp.simog.auth.tool.ExternalWSManager;
import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Collaborazioni;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.common.beans.ResponseCheckLogin;
import it.avlp.simog.ws.commons.CollaborazioniManager;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;

import java.sql.Connection;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

/**
 * 
 * Classe che si occupa della gestione del login e della creazione della sessione
 * vers 0.2
 * 
 * **/

public class LoginActionManager {

	/**
	 * Metodo per l'esecuzione della chiusura della sessione
	 * ritorna una:
	 *  ResponseCheckLogin
	 *  - success (boolean)
	 *  - error (not empty if success = false)
	 *  - ticket (not null if success 0 true)
	 *  - Collaborazioni
	 *  	- Collaborazione[] (array of Collaborazione)
	 *  		- Collaborazione
	 *  			- String index;
	 *				- String azienda_denominazione;
	 *				- String azienda_codiceFiscale;
	 *				- String ufficio_denominazione;
	 *				- String ufficio_id;
	 *				- String ufficio_profilo;  	
	 *  
	 */
	
	/**********************************************************************************
	 * Il metodo si occupa di genera la ResponseCheckLogin 
	 * controllando login e password inseriti
	 * @param login
	 * @param password
	 * @return
	 */
	public synchronized static ResponseCheckLogin execute(String login,String password){
		//-------	object declarations		-------//
		Logger logger = null;
		ResponseCheckLogin response = null;
		ConnectionWSManager cwsm = null;
		Connection con = null;		
		try{
			logger = LoggerManager.getInstance().getLogger();
			logger.info("-----------	begin 	---------------");
			logger.info("eseguendo: ResponseCheckLogin execute(String login,String password)");
			response = new ResponseCheckLogin();
			WsSessions wss = new WsSessions();
			cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
			WSSessionManager wsm = new WSSessionManager(logger,cwsm);
			con = cwsm.getConnection();
			cwsm.setAutocommit(false);
			ExternalWSManager ewm = new ExternalWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
			TicketManager tm = new TicketManager();
			SqlTools sqlt = new SqlTools();
			//-------	 begin operations		--------//
			wss.setUserId(login);
			Timestamp time = sqlt.getDBDate(con, logger);
			wss.setSessionStart(time);
			wss.setSessionEnd(sqlt.increseSessionEnd(logger, time));
			wss.setSessionStatus("S");
			wss.setComando("Login");
			//---se inizio sessione inserito correttamente entra nella condizione---//
			if(wsm.insertBeginSession(wss)){
				logger.info(">>>>inserimento della nuova sessione riuscito");
				//---se utente valido entra nella condizione---//
				if(ewm.login(login, password)){
					logger.info(">>>>login valido");
					String xml = ewm.getXMLresponse();
					String ticket = tm.generate(xml);
					String userStatus = ewm.getXmlBean().getStato();
					wss.setXmlAuth(xml);				
					wss.setTicket(ticket);
					wss.setUserStatus(userStatus);
					wss.setSessionStatus("I");
					wss.setSessionEnd(sqlt.increseSessionEnd(logger, wss.getSessionEnd()));
					//---se l'aggiornamento dello stato del db e' riuscito entra nella condizione---//
					if(wsm.updateSessionWithLoginSuccess(wss)){
						cwsm.commit();			
						logger.info(">>>>login effettuato correttamente");
						response.setTicket(ticket);
						response.success = true;
						response.setError("");
						//---
						CollaborazioniManager cm = new CollaborazioniManager();
						Collaborazioni c = cm.getCollaborazioni(ewm.getXmlBean());
						if(c == null){
							// restituisco un bean empty
							c = new Collaborazioni();
						}
						response.setColl(c);				
					}		
				}else{
					response.success = false;
					String err = new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_02).getError();
					response.setError(err);
					logger.error("WARNING: "+err);
				}
			}else{
				response.success = false;
				String err = new ErrorManager(ErrorManager.SIMOGWS_WSSMANAGER_APP_18).getError();
				response.setError(err);
				logger.error("WARNING: "+err);
			}
		}catch(SimogWSException swe){
			if(cwsm != null){
				cwsm.rollback();
			}
			response.setSuccess(false);
			response.setError(swe.getMyMessage());
			logger.error("SimogWSException catched: "+swe.getMyMessage());
		}catch(Exception e){e.printStackTrace();}
		finally{
		if(cwsm != null){
			cwsm.closeConnection();
		}
		}
		logger.info("-----------	end	 	---------------");
		return response;
	}
	


	/**********************************************************************************
	 * Il metodo si occupa di genera la ResponseCheckLogin 
	 * controllando login, password e rup inseriti
	 * @param login
	 * @param password
	 * @param cfrup
	 * @return
	 */ 
	// TICKET ALM - 3.04.3
	public synchronized static ResponseCheckLogin execute(String login,String password, String cfrup){
		//-------	object declarations		-------//
		Logger logger = null;
		ResponseCheckLogin response = null;
		ConnectionWSManager cwsm = null;
		Connection con = null;		
		try{
			logger = LoggerManager.getInstance().getLogger();
			logger.info("-----------	begin 	---------------");
			logger.info("eseguendo: ResponseCheckLogin execute(String login,String password)");
			response = new ResponseCheckLogin();
			WsSessions wss = new WsSessions();
			cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
			WSSessionManager wsm = new WSSessionManager(logger,cwsm);
			con = cwsm.getConnection();
			cwsm.setAutocommit(false);
			ExternalWSManager ewm = new ExternalWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
			TicketManager tm = new TicketManager();
			SqlTools sqlt = new SqlTools();
			//-------	 begin operations		--------//
			wss.setUserId(cfrup);
			wss.setRpntId(login);
			Timestamp time = sqlt.getDBDate(con, logger);
			wss.setSessionStart(time);
			wss.setSessionEnd(sqlt.increseSessionEnd(logger, time));
			wss.setSessionStatus("S");
			wss.setComando("LoginRPNT");
			//---se inizio sessione inserito correttamente entra nella condizione---//
			if(wsm.insertBeginSession(wss)){
				logger.info(">>>>inserimento della nuova sessione riuscito");
				//---se utente valido entra nella condizione---//
				if(ewm.login(login, password)){
					
					//Recupera le collaborazioni dell'utente RPNT e verifica se per questo utente esiste un profilo RPNT
					CollaborazioniManager cm = new CollaborazioniManager();
					Collaborazioni cRpnt = cm.getCollaborazioni(ewm.getXmlBean());
					
					boolean rpntFound = false;
					for(Collaborazione coll : cRpnt.getCollaborazioni()) {
						if(coll.getUfficio_profilo().equals(ProfiloEnum.RPNT.codice()))
						{
							rpntFound=true;
							break;
						}
					}
					
					if(rpntFound) {
					
							String wsIam = ConfigurationManager.getInstance().getSimogProperties().getWS_AUTH_TARGET_HOST();
							if(wsIam.contains("NEW"))
								wsIam = wsIam.split("NEW:")[1];
							
							wsIam = wsIam.replace("Ilogin", "rs/gestioneProfilo/ricercaProfilo");
							RicercaProfiloRest rest = new RicercaProfiloRest(wsIam);
							String xmlRup = rest.callRicercaProfilo(cfrup);
							Collaborazioni c = rest.getCollaborazioniFromXml(xmlRup);
							if(c.getCollaborazioni().length > 0) {
								logger.info(">>>>login valido");
								
								String newXmlAuth = generateXmlAuthRPNT(c.getCollaborazioni());
	
								String xml = ewm.getXMLresponse();
								String ticket = tm.generate(xml);
								String userStatus = ewm.getXmlBean().getStato();
								wss.setXmlAuth(newXmlAuth);				
								wss.setTicket(ticket);
								wss.setUserStatus(userStatus);
								wss.setSessionStatus("I");
								wss.setSessionEnd(sqlt.increseSessionEnd(logger, wss.getSessionEnd()));
								//---se l'aggiornamento dello stato del db e' riuscito entra nella condizione---//
								if(wsm.updateSessionWithLoginSuccess(wss)){
									cwsm.commit();			
									logger.info(">>>>login effettuato correttamente");
									response.setTicket(ticket);
									response.success = true;
									response.setError("");
									//---
									//CollaborazioniManager cm = new CollaborazioniManager();
														 
									if(c == null){
										// restituisco un bean empty
										c = new Collaborazioni();
									}
									response.setColl(c);				
								}
							} else {
								throw new SimogWSException(ErrorManager.SIMOGWS_ACTIONS_APP_03);
							}
					
					} else {
						throw new SimogWSException(ErrorManager.SIMOGWS_ACTIONS_APP_04);
					}
					
				}else{
					response.success = false;
					String err = new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_02).getError();
					response.setError(err);
					logger.error("WARNING: "+err);
				}
			}else{
				response.success = false;
				String err = new ErrorManager(ErrorManager.SIMOGWS_WSSMANAGER_APP_18).getError();
				response.setError(err);
				logger.error("WARNING: "+err);
			}
		}catch(SimogWSException swe){
			if(cwsm != null){
				cwsm.rollback();
			}
			response.setSuccess(false);
			response.setError(swe.getMyMessage());
			logger.error("SimogWSException catched: "+swe.getMyMessage());
		}catch(Exception e){e.printStackTrace();}
		finally{
		if(cwsm != null){
			cwsm.closeConnection();
		}
		}
		logger.info("-----------	end	 	---------------");
		return response;
	}
	
	private static String generateXmlAuthRPNT(Collaborazione[] collaborazioni) {
		String newXmlAuth = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
				"<check_login>" + 
				"<soggetto>" + 
				"<cognome>Rossi</cognome>" + 
				"<nome>Mario</nome>" + 
				"<tel>081332244</tel>" + 
				"<fax>0814433612</fax>" + 
				"<admin_or>000</admin_or>" + 
				"</soggetto>" + 
				"<collaborazioni>";
		
		int counter=0;
		for(Collaborazione coll : collaborazioni) {
			newXmlAuth+="<collaborazione index=\""+coll.getIndex()+"\"><azienda>";
			if(coll.getAzienda_denominazione() == null || "".equals(coll.getAzienda_denominazione()))
				newXmlAuth+="<denominazione/>";
			else
				newXmlAuth+="<denominazione>"+coll.getAzienda_denominazione()+"</denominazione>";
			
			if(coll.getAzienda_codiceFiscale() == null || "".equals(coll.getAzienda_codiceFiscale()))
				newXmlAuth+="<codice_fiscale/>";
			else
			   newXmlAuth+="<codice_fiscale>"+coll.getAzienda_codiceFiscale()+"</codice_fiscale>";
				
			newXmlAuth+="<id_osservatorio>"+("".equals(coll.getIdOsservatorio()) ? "N/A" : coll.getIdOsservatorio())+"</id_osservatorio></azienda><ufficio>";
			
			if(coll.getUfficio_denominazione() == null || "".equals(coll.getUfficio_denominazione()))
				newXmlAuth+="<denominazione/>";
			else
				newXmlAuth+="<denominazione>"+coll.getUfficio_denominazione()+"</denominazione>";
			
			newXmlAuth+="<id_ufficio>"+coll.getUfficio_id()+"</id_ufficio>";
			newXmlAuth+="<profilo>"+ coll.getUfficio_profilo() +"</profilo></ufficio></collaborazione>";
			counter++;
		}
		newXmlAuth+="</collaborazioni><stato>"+counter+"</stato></check_login>";
		
		return newXmlAuth;
		
	}
	
}
