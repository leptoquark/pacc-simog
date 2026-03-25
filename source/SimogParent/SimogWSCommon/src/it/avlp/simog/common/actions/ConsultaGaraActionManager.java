package it.avlp.simog.common.actions;

import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.beans.ResponseConsultaGara;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.common.util.General;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.massload.xmlbeans.SchedaGaraCigDocument;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.GaraXMLManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class ConsultaGaraActionManager {

	/***********************************************************************************************
	 * Il metodo permette restituisce l'esito della consulta della gara 
	 * @param ticket : String
	 * @param CIG : String
	 * @param schede : String
	 * @return ResponseConsultaGara
	 */
	public static  ResponseConsultaGara execute(String ticket,String CIG, String schede, boolean allData){
		
		//-------	object declarations		-------//
		ResponseConsultaGara rcg = null;
		Logger logger = null;
		ConnectionWSManager cwsm = null;
		Connection con = null;
		SqlTools sqlt = new SqlTools();
		WsSessions wss = new WsSessions();
		WSSessionManager wsm = null;
		
		
		
		//-------	do some stuff		-------//
		try{
			logger = LoggerManager.getInstance().getLogger();
			logger.info("-----------	begin	---------------");
			logger.info("eseguendo: ResponseConsultaGara execute(String ticket))");
			cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
			wsm = new WSSessionManager(logger,cwsm);
			
			//TICKET ALM #4508
			boolean keyFound = false;
			try {
				if(SimogFlags.is3042Active())
				   keyFound = wsm.checkKey(ticket);
			} catch (SimogWSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//FINE TICKET ALM #4508
			
			con = cwsm.getConnection();
			TicketManager tm = new TicketManager();
			rcg = new ResponseConsultaGara();
			cwsm.setAutocommit(false);
			wss.setTicket(ticket);
			wss.setComando("ConsultaGara");
			wss.setCollaborazione(-1); 
			
			//TICKET ALM #4508
			if(!keyFound) {
				wss = wsm.selectFindValidSession(wss);
				cwsm.commit();
			}
			
			
			
			if(wss != null || keyFound){
				
				logger.info(">>>>esiste una sessione associata al ticket");
				GaraXMLManager gxm = new GaraXMLManager(schede,con);
	
				if(!keyFound)
			       tm.validateRequestedActionByProfile(wss,TicketManager.CONSULTA_GARA);
		
				
				//gm nuovo codice consulta gara
				//creazione della lista dei CIG
				List<String> listaCig = new ArrayList<String>();

				GaraManager garaManager = new GaraManager(con, logger);
				if(CIG.length() == 10)
					//se e' un cig lo aggiungo alla lista
					listaCig.add(CIG);
				else {
					try{
						//altrimenti e' un idGara, quindi ricerco la lista dei suoi cig
    					listaCig = garaManager.getAllCigGara(Long.parseLong(CIG));
					}
				    catch(Exception e){
				    	throw new SimogWSException(e.getMessage());
				    }
				    if(listaCig.isEmpty()){
				    	try{
				    		//se non ho trovato alcun cig per la gara, verifico che la gara esista,
				    		//se la trovo allora e' una gara senza lotti
				    		Gara gara = garaManager.getGara(Long.parseLong(CIG));
				    		if(gara!=null)
				    			//se la gara esiste aggiungo il suo idGara alla lista
				    			listaCig.add(CIG);
					    	else
					    		throw new SimogWSException(ErrorManager.SIMOGWS_XMLBEANMANAGER_APP_01);
				    	}
				    	catch(Exception e){
					    	throw new SimogWSException(e.getMessage());
					    }
				    }
    			}			
				//controllo per l'autorizzazione esteso a tutta la lista di CIG	
				Iterator <String> i = listaCig.iterator();
				boolean noErrors = true;
// pp oldxgara				SchedaCIGDocument schedeMultiple = SchedaCIGDocument.Factory.newInstance();
// pp oldxgara				schedeMultiple.addNewSchedaCIG();
				
				SchedaGaraCigDocument dati = SchedaGaraCigDocument.Factory.newInstance();
				dati.addNewSchedaGaraCig().addNewGara();
				   
				//la lista puo' avere un solo cig, una lista di cig, oppure un idGara
				while(i.hasNext() && noErrors){
					String cig = (String)i.next();		
    				try{
    					    //TICKET ALM #4508
    						//gxm.checkAuth(cig,tm);
    					    gxm.checkAuthKey(cig, tm, keyFound,allData,wss.getUserId());
		    	    }
			        catch(SimogWSException swe){
				  	    //validazione stringa xml fallita
			    	    wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
			    	    rcg.setSuccess(false);
			    	    String messaggioErrore = swe.getMyMessage();
				   	    rcg.setError(messaggioErrore);
				   	    logger.error("SimogWSException catched: "+messaggioErrore);							
				   	    wss.setLastError(messaggioErrore);				
				   	    wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
				   	    if(wsm.updateSessionAfterOp(wss)){
				   		    logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
				   		    rcg.setSuccess(false);
				   		    rcg.setError(messaggioErrore);
				   		    cwsm.commit();
				        }
				   	    else{
					        //logger.debug("aggiornamento sessione fallito");
					   	    rcg.setError(messaggioErrore+" e' possibile che la sessione non sia piu valida si prega di rieffettuare il login e ripetere l'operazione, controllando i dati");
				        }
				    	return rcg;
				    }			
				    try{
				    	//logger.debug(" validita' => TICKETMANAGER: "+tm.isValido()+" , GARAXMLMANAGER: "+gxm.isAuth());
				    	//TICKET ALM #4508
					    if(keyFound || (tm.isValido() && gxm.isAuth())){
					    	logger.info(">>>>utente abilitato al comando richiesto");
					    	rcg.setSuccess(true);
						    rcg.setError("");
						    
						    // wrap della consultazione multipla, se chiamato per numero gara
						    if(CIG.length() != 10){
/*** pp oldxgara
						       SchedaType dcg = schedeMultiple.getSchedaCIG().addNewDatiConsultaGara();
						    	dcg.setDatiGara(gxm.getSd().getScheda().getDatiGara());
						    	if(gxm.getSd().getScheda().isSetDatiScheda())
						    		dcg.setDatiScheda(gxm.getSd().getScheda().getDatiScheda());
						    	if(gxm.getSd().getScheda().isSetResponsabili())
						    		dcg.setResponsabili(gxm.getSd().getScheda().getResponsabili());						    	
						    	if(gxm.getSd().getScheda().isSetAggiudicatari())
						    		dcg.setAggiudicatari(gxm.getSd().getScheda().getAggiudicatari());
                                rcg.setGaraXML(schedeMultiple.xmlText());
***/
					            dati.getSchedaGaraCig().setGara(gxm.getSd().getScheda().getDatiGara().getGara());
						        
						        if(gxm.getSd().getScheda().getDatiGara().getLotto() != null)
						           dati.getSchedaGaraCig().addNewCIG().setStringValue(gxm.getSd().getScheda().getDatiGara().getLotto().getCIG());
						    	
						        rcg.setGaraXML(dati.xmlText());
						    } 
						    else{
						    	// consultazione per CIG
						    	rcg.setGaraXML(gxm.getGaraXML());						    	
						    }
					    	//--- fine operazione
					    	wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
					    	if(wsm.updateSessionAfterOp(wss)){
					    		cwsm.commit();				
					    		logger.info(">>>>aggiornamento dello stato della sessione riuscito");
					    	}
					    }
					    else{
					    	noErrors = false;
						    if(gxm.getErrore()!= null){
							    if(gxm.getErrore().equals("1")){
							    	if (cig.length()<10 && General.isNumber(cig,true)){
							    	    logger.info("Numero Gara non valido, oppure osservatorio non abilitato alla operazione richiesta per il cig richiesto");
							    	    wss.setLastError("Numero Gara [ "+cig+" ] non valido, oppure osservatorio non abilitato per il comando ["+wss.getComando()+"] per il Numero Gara [ "+cig+" ] richiesto");
							    	}
							    	else{
							    	    logger.info("CIG non valido, oppure osservatorio non abilitato alla operazione richiesta per il cig richiesto");
							    	    wss.setLastError("CIG [ "+cig+" ] non valido, oppure osservatorio non abilitato per il comando ["+wss.getComando()+"] per il CIG [ "+cig+" ] richiesto");
							    	}
							    	rcg.setSuccess(false);
								    rcg.setError("["+wss.getLastError()+"]");
							    }
							    else{
							    	logger.info(gxm.getErrore());
							    	wss.setLastError("fallimento della validazione dell'xml per il comando ["+wss.getComando()+"] richiesto per il CIG [ "+CIG+" ] errore [ "+gxm.getErrore()+" ]");
							    	rcg.setSuccess(false);
							    	rcg.setError("["+wss.getLastError()+"]");
							    }
						    }
						    else{
							    logger.info("fallimento della validazione del ticket, associazione comando - profilo, non autorizzata, oppure CIG non valido");
							    wss.setLastError("nessuna collaborazione abilitata al comando ["+wss.getComando()+"] richiesto per il CIG [ "+CIG+" ]");
						    	rcg.setSuccess(false);
							    rcg.setError("["+wss.getLastError()+"]");
					    	}
						    wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
					    	if(wsm.updateSessionAfterOp(wss)){
						    	//logger.info("L'utente non ha alcuna collaborazione abilitata alla consultazione della gara richista");
						    	rcg.setSuccess(false);
					    		rcg.setError(gxm.getErrore());
						    	cwsm.commit();
						    }			
					    }
				    }
				    catch(SimogWSException swe){
					    logger.error("[]indice collaborazione non valido");
					    String messaggioErrore = swe.getMyMessage();
					    wss.setLastError("[]collaborazione ["+wss.getCollaborazione()+"] non esiste");				
					    wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
					    if(wsm.updateSessionAfterOp(wss)){
						    logger.info("[]aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
						    rcg.setSuccess(false);
						    rcg.setError("[]"+messaggioErrore);
						    cwsm.commit();
					    }
					    throw new SimogWSException(swe.getMyMessage());
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
		}
		finally{
		if(cwsm != null){
			cwsm.closeConnection();
		}
		}
		logger.info("----------		END		----------");
		return rcg;
	}
}
