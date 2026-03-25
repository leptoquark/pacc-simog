package it.avlp.simog.common.actions;

import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avlp.simog.beans.Amministrazione;
import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Collaborazioni;
import it.avlp.simog.beans.CollaborazioniRssa;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.StazioneAppaltante;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.action.InfoComuniSharedAction;
import it.avlp.simog.common.beans.ResponsePresaCarico;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.GaraLottoManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;

import java.sql.Connection;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import it.avlp.simog.beans.InfoGaraBean;

public class PresaInCaricoGaraDelegataActionManager {

	/******************************************************************************
	 * TICKET ALM - 3.04.3
	 * effettua la presa in carico della gara o dei dati comuni
     * @param String: ticket
     * @param String: indexCollaborazione, indiice della collaborazione con la quale si intende effettuare l'operazione
     * @param String: numero gara 
     * @return ResponsePresaCarico
	 */
	public synchronized static ResponsePresaCarico execute(String ticket, String indexCollaborazione, String gara, boolean isGara){
		//-------	object declarations		-------//
		   ResponsePresaCarico rgc = null;
			Logger logger = null;
			ConnectionWSManager cwsm = null;
			Connection con = null;
			
			if(indexCollaborazione == null || "".equals(indexCollaborazione.trim())){
				indexCollaborazione = "-1";
			}else{
				indexCollaborazione = indexCollaborazione.trim();
			}
			try{
				logger = LoggerManager.getInstance().getLogger();
				logger.info("-----------	begin  	---------------");
				logger.info("eseguendo: ResponsePresaCarico execute(String ticket, String indexCollaborazione, String gara)");
				rgc = new ResponsePresaCarico();
				WsSessions wss = new WsSessions();
				cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
				WSSessionManager wsm = new WSSessionManager(logger,cwsm);
				con = cwsm.getConnection();
				TicketManager tm = new TicketManager();
				SqlTools sqlt = new SqlTools();
				//-------	 begin operations		--------//	
				cwsm.setAutocommit(false);
				wss.setTicket(ticket);
				wss.setComando("PresaInCaricoGaraDelegata");
				wss.setCollaborazione(Integer.parseInt(indexCollaborazione));		
				wss = wsm.selectFindValidSession(wss);
				cwsm.commit();
				if(wss != null){
					logger.info(">>>>esiste una sessione associata al ticket");
					try{      
					   tm.validateRequestedActionByProfile(wss,TicketManager.PRESA_CARICO);
						if(tm.isValido()){
							logger.info(">>>>utente abilitato al comando richiesto");
							rgc.setSuccess(true);
							rgc.setError("");					
							//cwsm.setIsolation("t_serialize");
							logger.info(">>>> (connnessione settata a transaction serialized)");
							
							wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							try{
		                  GaraManager garaManager = new GaraManager(con,logger);
		                  LottoManager lottoManager = new LottoManager(con,logger);
								Collaborazione col = null;						
	                     CollaborazioniRssa collsRssa = null;
	                     Collaborazioni colls = null;
	                     if(!tm.isOperaComeOsservatorio()){     
	                        col = tm.getCollaborazione();
	                        colls = tm.getCollaborazioni();
	                        collsRssa = new CollaborazioniRssa(colls,col);
	                     }
	                     							
	                     Gara g = null;
	                     Long idLotto = null;
	                     if(!isGara) {
	                    	 
	                    	 List<Lotto> listaL = lottoManager.getLottoByCigWS(gara);
	                    	 if(listaL==null || listaL.isEmpty())
	                    		 throw new SimogWSException(Messaggi.SIMOG_RIC_001);
	                    	 else {
	                    		 idLotto = listaL.get(0).getId_Lotto();
	                    		 g = garaManager.getGara(listaL.get(0).getId_Gara());
	                    	 }
	                    	 
	                     } else {
	                     
	                     //Verifica che la gara esista
		                     g = garaManager.getGara(Long.parseLong(gara));
	                     if(g==null)
	                    	 throw new SimogWSException(Messaggi.SIMOG_RIC_001);
	                     }
	                     
	                     //Verifica che la gara sia delegata in base alla collaborazione scelta
	                     Hashtable collaborazione = new Hashtable();
	                     collaborazione.put(col.getAzienda_codiceFiscale(), col.getAzienda_codiceFiscale());

	                     int idfDelega = g.getID_F_DELEGATE();
	                     if(idfDelega == 0 || (!"".equals(g.getCF_AMM_AGENTE()) && g.getCF_AMMINISTRAZIONE().equals(g.getCF_AMM_AGENTE())))
	                    	 throw new SimogWSException(Messaggi.SIMOG_RIC_006.replace("$1", gara));
	                     else if(idfDelega == Costanti.DELEGA3)
	                         throw new SimogWSException(Messaggi.SIMOG_RIC_009.replace("$1", gara));
	                     //Verifica se la gara e' nelle condizioni di poter essere presa in carico a seconda
	                     //del tipo di delega selezionato
	                     if(!garaManager.checkPresaInCaricoDelega(g.getId_Gara(),idfDelega, collaborazione,g.getNumeroLotti().intValue())) {
	                    	 if((idfDelega==Costanti.DELEGA1 || idfDelega==Costanti.DELEGA2) && isGara)//aggiunto && isGara MAC 36769 3.04.8.1
	                    	    throw new SimogWSException(Messaggi.SIMOG_RIC_007.replace("$1", gara));
	                    	 else if(idfDelega==Costanti.DELEGA4 && isGara)//aggiunto && isGara MAC 36769 3.04.8.1
	                    	    throw new SimogWSException(Messaggi.SIMOG_RIC_008.replace("$1", gara));
	                     }
	                   //MAC 36769 3.04.8.1 commentato rige sotto
	                     //Procedere con la presa in carico
	                     StazioneAppaltante sa = new StazioneAppaltante();
	                     sa.setIdUfficio(col.getUfficio_id());
	                     sa.setDenominazione(col.getUfficio_denominazione());
	                     Amministrazione amm = new Amministrazione();
	                     amm.setCodiceFiscale(col.getAzienda_codiceFiscale());
	                     amm.setDenominazioneAmministrazione(col.getAzienda_denominazione());
	                     amm.setId_osservatorio(col.getIdOsservatorio());
	                     sa.setAmministrazione(amm);
	                     
	                     //Se e' stato indicato il numero gara, esegui anche la presa in carico della gara
	                     if(isGara) {
	                     garaManager.eseguiPresaInCaricoGaraDelegata(sa, wss.getUserId(), gara);
	                     garaManager.setDataPresaInCaricoDelega(g.getId_Gara());
	                         garaManager.eseguiPresaInCaricoInfoAggiudicazioni(sa, wss.getUserId(), gara);
	                     } else { //MAC 36769 3.04.8.1
	                    	 garaManager.eseguiPresaInCaricoInfoAggiudicazioniSingoloCIG(sa, wss.getUserId(), idLotto, idfDelega==Costanti.DELEGA4, false);
	                    	 //ticket #31062  caricare tutti i lotti --> prendere tutte le schede dati comuni--> controllare il CDC 
	                         //--> se tutti uguali far diventare la gara non delegata ma di proprieta'
	                    	 LottoManager lman = new LottoManager(con, logger);
	                    	 InfoComuniManager iManager = new InfoComuniManager(con, logger);
	             			InfoGaraBean igb = iManager.loadInfoGara(idLotto);
	     					InfoGaraBean infoGara = null;									
	     					infoGara = igb;
	                    	 List<Lotto> listLotti = lman.getListaLotti(infoGara.getIdGara());
	                         
	                         InfoComuniManager infoComuniManager = new InfoComuniManager(con, logger);
	                         
	                         String codiceCCControllo = null;
	                         int tempCounter = 0;
	                         for (Lotto lottotemp : listLotti) {
	                        	 List<InfoComuniBean> listInfoComuni = infoComuniManager.getInfoComuniByIdLotto(lottotemp.getId_Lotto());                         	 
								for (InfoComuniBean bean : listInfoComuni) {
									if(codiceCCControllo == null || codiceCCControllo == "") {
										codiceCCControllo = bean.getCodiceCC();
									}
									if(codiceCCControllo.equals(bean.getCodiceCC())) {
										tempCounter++;
									}
								}
							}
	                         
	                         //ticket #31062
	                         if(tempCounter == listLotti.size()) {
	                        	 garaManager.eseguiPresaInCaricoGaraDelegata(sa, wss.getUserId(), String.valueOf(g.getId_Gara()));
	                             garaManager.setDataPresaInCaricoDelega(g.getId_Gara());
	                         }
	                     }
	                     		
	                     		
								logger.info("presa in carico riuscita");
							}catch(SimogWSException swe){
								logger.error("presa in carico fallita "+swe.getMyMessage());
								if(cwsm != null){
									cwsm.rollback();
								}
								rgc.setSuccess(false);
								rgc.setError(swe.getMyMessage());
								logger.error("SimogWSException catched: "+swe.getMyMessage());
								String messaggioErrore = swe.getMyMessage();
								wss.setLastError(messaggioErrore);				
								wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
								if(wsm.updateSessionAfterOp(wss)){
									logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
									rgc.setSuccess(false);
									rgc.setError(messaggioErrore);
									cwsm.commit();
								}else{
									logger.error("aggiornamento sessione fallito");
									rgc.setError(messaggioErrore+" e' possibile che la sessione non sia piu valida si prega di rieffettuare il login e ripetere l'operazione, controllando i dati");
								}
								return rgc;
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
								logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
								rgc.setSuccess(false);
								rgc.setError(messaggioErrore);
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
							rgc.setSuccess(false);
							rgc.setError(messaggioErrore);
							cwsm.commit();
						}
						return rgc;
					}
				}		
			}catch(SimogWSException swe){
				if(cwsm != null){
					cwsm.rollback();
				}
				rgc.setSuccess(false);
				rgc.setError(swe.getMyMessage());
				logger.error("SimogWSException catched: "+swe.getMyMessage());
			}catch(Throwable t){t.printStackTrace();}
			finally{
				if(cwsm != null){
					cwsm.closeConnection();
				}
			}
			logger.info("----------		END		----------");
			return rgc;

		}
}
