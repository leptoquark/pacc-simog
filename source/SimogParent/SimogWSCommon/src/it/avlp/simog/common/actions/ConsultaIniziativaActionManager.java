package it.avlp.simog.common.actions;

import it.anticorruzione.simog.ws.util.IniziativaConverter;
import it.avcp.simog.managers.luogo.IstatManager;
import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Collaborazioni;
import it.avlp.simog.beans.CollaborazioniRssa;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IniziativaSoggAggr;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.beans.ResponseConsultaIniziativa;
import it.avlp.simog.common.beans.ResponseModificaGara;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.common.util.General;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.IniziativaManager;
import it.avlp.simog.massload.xmlbeans.SchedaGaraCigDocument;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.IniziativaValidator;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.GaraXMLManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class ConsultaIniziativaActionManager {

	/***********************************************************************************************
	 * Il metodo permette restituisce l'esito della consulta della gara 
	 * @param ticket : String
	 * @param CIG : String
	 * @param schede : String
	 * @return ResponseConsultaGara
	 */
	public static  ResponseConsultaIniziativa execute(String ticket,
			                                          String CIG, 
			                                      //    List<String> territori,
			                                          String indexCollaborazione,
			                                          List<String> categorie){
		
		//-------	object declarations		-------//
		ResponseConsultaIniziativa rcg = null;
		Logger logger = null;
		ConnectionWSManager cwsm = null;
		Connection con = null;
		SqlTools sqlt = new SqlTools();
		WsSessions wss = new WsSessions();
		WSSessionManager wsm = null;
		
		if(indexCollaborazione == null || "".equals(indexCollaborazione.trim())){
			indexCollaborazione = "-1";
		}else{
			indexCollaborazione = indexCollaborazione.trim();
		}
		
		//-------	do some stuff		-------//
		try{
					logger = LoggerManager.getInstance().getLogger();
					logger.info("-----------	begin	---------------");
					logger.info("eseguendo: ResponseConsultaIniziativa execute(String ticket))");
					cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
					wsm = new WSSessionManager(logger,cwsm);
								
					con = cwsm.getConnection();
					TicketManager tm = new TicketManager();
					rcg = new ResponseConsultaIniziativa();
					cwsm.setAutocommit(false);
					wss.setTicket(ticket);
					wss.setComando("ConsultaIniziative");
					wss.setCollaborazione(Integer.parseInt(indexCollaborazione));	
					wss = wsm.selectFindValidSession(wss);
					cwsm.commit();
					
					if(wss != null){
						
						logger.info(">>>>esiste una sessione associata al ticket");
					//	GaraXMLManager gxm = new GaraXMLManager(schede,con);
					    tm.validateRequestedActionByProfile(wss,TicketManager.CONSULTA_INIZIATIVA);
						   if(tm.isValido()){
								    IniziativaValidator validator = new IniziativaValidator(con, logger);
								    
								    Collaborazione col = tm.getCollaborazione();

								    List<String> territori = new ArrayList<String>();
			                        if(!ProfiloEnum.REGIONE_099.equals(col.getIdOsservatorio())) {
			                            territori.add(col.getIdOsservatorio().substring(1));
			                        }
								  //Verifica correttezza territori
									IstatManager im = new IstatManager(con,logger);
									boolean foundErr = false;
									for(String terr : territori) {
										if(!im.isRegioneValid(terr))
										  foundErr = true;
									}
									
									if(foundErr) {
										rcg.success= false;
										throw new SimogWSException(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1","campo Territori"));
									} else {
									//Verifica correttezza categorie merceologiche DPCM
										String[] catArray = new String[categorie.size()];
										for(int i=0;i<categorie.size();i++) 
											catArray[i] = categorie.get(i);
									  
										if(!validator.validaCategorie(catArray,PageHelper.getCurrentDate())) {
											rcg.success= false;
											throw new SimogWSException(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1","campo Categoria merceologica"));
										} else {
											IniziativaManager inizMan = new IniziativaManager(con,logger);
											List<IniziativaSoggAggr> listaIniziative = inizMan.getIniziative(CIG, territori, categorie, null,null, true);
											if(listaIniziative.size()<=0) {
												rcg.success=false;
												throw new SimogWSException(ErrorManager.SIMOGWS_ACTIONS_APP_06);
											} else {
												rcg.setIniziativaXML(IniziativaConverter.convertListaIniziativeToXml(listaIniziative));
												rcg.success=true;
											}
										}
									}
									
									//--- fine operazione
							    	wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							    	if(wsm.updateSessionAfterOp(wss)){
							    		cwsm.commit();				
							    		logger.info(">>>>aggiornamento dello stato della sessione riuscito");
							    	}
						    } else {
			    	logger.info("fallimento della validazione del ticket associazione comando - profilo non autorizzata");
					String messaggioErrore = "collaborazione ["+wss.getCollaborazione()+"] non abilitata al comando ["+wss.getComando()+"] richiesto";
					wss.setLastError(messaggioErrore);				
					wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
					if(wsm.updateSessionAfterOp(wss)){
						cwsm.commit();
						logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
						// setto l'errore nell'oggetto di risposta
						ConsultaIniziativaActionManager.setResponseToError(rcg, messaggioErrore);
			    }
			}
		}
				
						
		}catch(SimogWSException swe){
			if(cwsm != null){
				cwsm.rollback();
			}
		    wss.setLastError(swe.getMyMessage());				
		    try {
				wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
				if(wsm.updateSessionAfterOp(wss)){
				    //logger.info("[]aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
				    cwsm.commit();
				}
			} catch (SimogWSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rcg.success = false;
			rcg.setError(swe.getMyMessage());
			logger.error("SimogWSException catched: "+swe.getMyMessage());
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally{
		if(cwsm != null){
			cwsm.closeConnection();
		}
		}
		logger.info("----------		END		----------");
		return rcg;
	}
	
	private static void setResponseToError(ResponseConsultaIniziativa response,String errorMsg){
		response.setSuccess(false);
		response.setError(errorMsg);
	}
}
