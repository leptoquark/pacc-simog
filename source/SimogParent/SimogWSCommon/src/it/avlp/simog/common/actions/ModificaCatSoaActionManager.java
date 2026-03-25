package it.avlp.simog.common.actions;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Collaborazioni;
import it.avlp.simog.beans.CollaborazioniRssa;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.beans.ResponseModificaCPV;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.error.SimogWsXmlException;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.massload.xmlbeans.LottoWSDocument;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;

public class ModificaCatSoaActionManager {
	
	
	/**
	 * @param idLotto
	 * @param catPrevalente
	 * @param catScorporabile
	 * @return 'OK' in caso di successo altrimenti un messaggio di errore
	 */
	public synchronized static String execute( String ticket, String indexCollaborazione, String cig, String catPrevalente, String[] catScorporabile ) {
		
		String msgResponse = "OK";
		Logger logger = null;
		ConnectionWSManager cwsm = null;
		Connection con = null;
		
		// per indicare la provenienza
		SimogFlags.setFromWS(true);
		
		if(indexCollaborazione == null || "".equals(indexCollaborazione.trim())){
			indexCollaborazione = "-1";
		}else{
			indexCollaborazione = indexCollaborazione.trim();
		}
		
		if(cig == null || cig.trim().isEmpty() )			
			return "Il CIG deve essere valorizzato";
		
		if( (catPrevalente == null || catPrevalente.trim().isEmpty()) && (catScorporabile == null || catScorporabile.length <= 0) )
			return "Nessun valore inserito per categoria prevalente e/o scorporabile";
		
		try{
			
			logger = LoggerManager.getInstance().getLogger();			
			WsSessions wss = new WsSessions();
			cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
			WSSessionManager wsm = new WSSessionManager(logger,cwsm);
			con = cwsm.getConnection();
			TicketManager tm = new TicketManager();
			SqlTools sqlt = new SqlTools();
			
			//-------	 begin operations		--------//	
			cwsm.setAutocommit(false);
			wss.setTicket(ticket);
			wss.setComando("ModificaCategoriaSOA");
			wss.setCollaborazione(Integer.parseInt(indexCollaborazione));		
			wss = wsm.selectFindValidSession(wss);
			cwsm.commit();
			if(wss != null){
				logger.info(">>>>esiste una sessione associata al ticket");
				try{
					tm.validateRequestedActionByProfile(wss, TicketManager.MODIFICA_CAT_SOA);
					
					if(tm.isValido()){
						
						logger.info(">>>>utente abilitato al comando richiesto");
										
						//cwsm.setIsolation("t_serialize");
						logger.info(">>>> (connnessione settata a transaction serialized)");
						//la validazione viene fatta al caricamento, se non passa la validazione passa direttamente al catch
						LottoManager lottoManager = new LottoManager(con,logger);
						LottoWSDocument lotto = null;
						
						try{
							Collaborazione coll = null;
							if(!tm.isOperaComeOsservatorio()){
								coll = tm.getCollaborazione();
							}
							lotto = null;//(LottoWSDocument)garaLottoManager.converti(datiGara, wss.getUserId(),coll,garaLottoManager.TIPO_LOTTO);
						}catch(SimogWsXmlException swe){
							//validazione stringa xml fallita
							wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							msgResponse = swe.getMessage();
//							
							logger.error("SimogWSException catched: "+ msgResponse);
							wss.setLastError(msgResponse);				
							wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							if(wsm.updateSessionAfterOp(wss)){
								logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");								
								cwsm.commit();
							}else{
								//nel messaggio d'errore si prega di rieffettuare il login
								logger.error("aggiornamento sessione fallito");
								msgResponse = (msgResponse +" e' possibile che la sessione non sia piu valida si prega di rieffettuare il login e ripetere l'operazione, controllando i dati");
							}
							return msgResponse;
						}
						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
						try{
							
							Collaborazione col = null;
							CollaborazioniRssa collsRssa = null;
							Collaborazioni colls = null;
							if(!tm.isOperaComeOsservatorio()){	
								col = tm.getCollaborazione();
								colls = tm.getCollaborazioni();
								collsRssa = new CollaborazioniRssa(colls,col);
							}
							
							/* Aggiornamento Categoria Prevalente */
							List<Lotto> lottoList = lottoManager.getLottoByCigWS( cig );
							
							if( lottoList == null || lottoList.isEmpty() )
								return "Nessun lotto recuperato dal CIG " + cig;
							
							Lotto l = lottoList.get(0);
							
							lottoManager.updateCategoriaSoa( l, catPrevalente );
							
							//- Aggiornamento categoria scorporabile
							if(catScorporabile != null)
								lottoManager.updateLottoCategorieScorporabili( String.valueOf( l.getId_Lotto() ), catScorporabile );
									
							//- Settaggio esito
							logger.info("integrazione riuscita");
							
						}catch(SimogWSException swe){
							logger.info("integrazione fallita "+swe.getMyMessage());
							if(cwsm != null){
								cwsm.rollback();
							}
							
							msgResponse = swe.getMyMessage();

							logger.error("SimogWSException catched: "+ msgResponse);						
							wss.setLastError( msgResponse );				
							wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							if(wsm.updateSessionAfterOp(wss)){
								logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
								cwsm.commit();
							}else{
								logger.error("aggiornamento sessione fallito");
							}
							return msgResponse;
						}
						//cwsm.setIsolation("t_read_committed");
						if(wsm.updateSessionAfterOp(wss)){
							cwsm.commit();				
							logger.info(">>>>aggiornamento dello stato della sessione riuscito");
						}
					}else{
						logger.info("fallimento della validazione del ticket associazione comando - profilo non autorizzata");
						String messaggioErrore = "collaborazione ["+wss.getCollaborazione()+"] non abilitata al comando ["+wss.getComando()+"] richiesto";
						wss.setLastError(messaggioErrore);				
						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
						if(wsm.updateSessionAfterOp(wss)){
							cwsm.commit();
							logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
							msgResponse = messaggioErrore;
						}			
					}
				//caso in cui l'indice passato non sia valido
				}catch(SimogWSException swe){
					logger.error("indice collaborazione non valido");
					String messaggioErrore = swe.getMyMessage();
					wss.setLastError("collaborazione ["+wss.getCollaborazione()+"] non esiste");				
					wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
					if(wsm.updateSessionAfterOp(wss)){
						cwsm.commit();
						logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");						
						msgResponse = messaggioErrore;
						cwsm.commit();
					}
					return msgResponse;
				}
			}		
		}catch(SimogWSException swe){
			if(cwsm != null){
				cwsm.rollback();
			}
			
			msgResponse =swe.getMyMessage();
			logger.error("SimogWSException catched: "+ swe.getMyMessage());
		}
		catch(Exception swe){
		   swe.printStackTrace();
         if(cwsm != null){
            cwsm.rollback();
         }

         msgResponse = swe.getMessage();
         logger.error("Exception catched: "+swe.getMessage());
      }
		finally{
			if(cwsm != null){
				cwsm.closeConnection();
			}
		}
		logger.info("----------		END		----------");
		return msgResponse;

		
	}

}
