package it.avlp.simog.common.actions;

import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.MultilottoManager;
import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Collaborazioni;
import it.avlp.simog.beans.CollaborazioniRssa;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.action.InfoComuniSharedAction;
import it.avlp.simog.common.beans.ResponsePresaCarico;
import it.avlp.simog.common.servlet.PSBD;
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
import java.util.List;

import org.apache.log4j.Logger;

public class PresaInCaricoActionManager {

	/******************************************************************************
	 * effettua la presa in carico della gara o dei dati comuni
     * @param String: ticket
     * @param String: indexCollaborazione, indiice della collaborazione con la quale si intende effettuare l'operazione
     * @param String: numero gara o cig (cig richiesto se presa in carico sui dati comuni)
     * @param String: estremi presa in carico
     * @param String: flag Dati comuni (decide se operare sui dati comuni o sulla gara)
     * @return ResponsePresaCarico
	 */
	public synchronized static ResponsePresaCarico execute(String ticket, String indexCollaborazione,
         String garaOcig,String estremiProvv, String flagDatiComuni){
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
			logger.info("eseguendo: ResponsePresaCarico execute(String ticket, String indexCollaborazione, String garaOcig,String estremiProvv, String flagDatiComuni)");
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
			wss.setComando("PresaInCarico");
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
	                  GaraLottoManager garaLottoManager = new GaraLottoManager(con,logger);
							Collaborazione col = null;						
                     CollaborazioniRssa collsRssa = null;
                     Collaborazioni colls = null;
                     if(!tm.isOperaComeOsservatorio()){     
                        col = tm.getCollaborazione();
                        colls = tm.getCollaborazioni();
                        collsRssa = new CollaborazioniRssa(colls,col);
                     }
                     							
							if(Costanti.FLAG_VALORE_NO.equals(flagDatiComuni)){
							   boolean blocco = false;
/* PP presa in carico gara sempre!
 							   GaraManager gm = new GaraManager(con, logger);
							   Gara gara = gm.getGara(Long.valueOf(garaOcig));
                        try {
                           AVCPassAction avpa = new AVCPassAction(con, logger, ConfigurationManager.getInstance().getSimogProperties());

                           blocco  = avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WS_GARA_PRESA_IN_CARICO.getCodice());
                        } catch (Exception e) {
                           // TODO Auto-generated catch block
                          logger.fatal(e.getMessage());
                        }
*/                           
                        if(blocco){
                           throw new SimogWSException(Messaggi.SIMOG_AVCPASS_001);
                        }
                        else{
   
   							   // presa in carico gara
      							if(garaLottoManager.presaInCarico(wss.getUserId(),indexCollaborazione, collsRssa,garaOcig, false, tm.getAdminOr(), estremiProvv)){
      								rgc.setSuccess(true);
      								rgc.setMessaggio("operazione effettuata correttamente");
      							}else{
      								throw new SimogWSException(garaLottoManager.getError());
      							}
                        }
							}
							else{
							   // presa in carico dati comuni
                        boolean blocco = false;
		                  
                        
                        //List<Lotto> listaLotti = lman.getListaLotti(lotto.get(0).getId_Gara());
                        //Gara gara = gm.getGara(lotto.get(0).getId_Gara());
                        
                        InfoComuniManager infoMan = new InfoComuniManager(con, logger);
                        InfoComuniBean icb = infoMan.getInfoComuniByCig(garaOcig);
                        // non esiste
                        if(icb.getIdInfo() == 0)
                           throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_38);
                        
                        // check avcpass
                        if(SimogFlags.is3028_RFWEBGL07Active()){
                           LottoManager lman = new LottoManager(con, logger);
                           //GaraManager gm = new GaraManager(con, logger);
                           List<Lotto> lotto = lman.getLottoByCigWS(garaOcig);

                           /*     CHECK BLOCCO AVCPASS   */
                           //verifico se posso modificare i dati (blocco avcpass)
                           // richiamo il servizio AVCPASS
                           try {
                              AVCPassAction avpa = new AVCPassAction(con, logger, ConfigurationManager.getInstance().getSimogProperties());
                           
                              blocco  = avpa.isAVCPass(null, lotto, AVCPassFunzioneEnum.WS_DATI_COMUNI_PRESA_IN_CARICO.getCodice());
                           } catch (Exception e) {
                              // TODO Auto-generated catch block
                             logger.fatal(e.getMessage());
                           }
                        }	                           

	                     if(blocco){
                           throw new SimogWSException(Messaggi.SIMOG_AVCPASS_001);
                        }
                        else{
                              icb.setProvvPresaCarico(estremiProvv);
                              icb.setCfRup(wss.getUserId());
                              InfoComuniSharedAction iAction = new InfoComuniSharedAction(con, logger);
                              try {
                            	  
                            	//TICKET ALM 18257
  								//Verifica se si sta prendendo in carico un CIG appartenente a un contratto multilotto
                            	  if(garaOcig.length()==10) {
			  								
			  								
			  								LottoManager lman = new LottoManager(con, logger);
			  								AggiudicazioniManager am  = new AggiudicazioniManager(con, logger);
			  								List<Lotto> lotto = lman.getLottoByCigWS(garaOcig);
			  								List<AggiudicazioneBean> listAgg = am.getAggiudicazioniByCIG(garaOcig);
			  								
//			  								List<AggiudicazioneBean> listAgg = mm.getAggiudicazioniListMultilotto(lotto.get(0).getId_Lotto());
			  								if(listAgg.size()>0 && listAgg.get(0).getCodiceContratto()!=null) {
			  									String codiceContratto = listAgg.get(0).getCodiceContratto();
			  									MultilottoManager mm = new MultilottoManager(con,logger);
			  									InfoComuniManager icm = new InfoComuniManager(con, logger);
			  									List<AggiudicazioneBean> listAggMulti = mm.getAggiudicazioniListMultilotto(lotto.get(0).getId_Lotto());
			  									for(AggiudicazioneBean ab : listAggMulti) {
			  										if(codiceContratto.equals(ab.getCodiceContratto())) {
			  										InfoComuniBean currIcb = icm.getInfoComuniByCig(ab.getCig());
			  										currIcb.setProvvPresaCarico(estremiProvv);
			  										iAction.presaInCarico(currIcb, wss.getUserId());
			  										}
			  									}
			  									
			  								} else {
			  									 iAction.presaInCarico(icb, wss.getUserId());
			  								}
                            	  
                            	  } else //FINE TICKET ALM 18257
                                 iAction.presaInCarico(icb, wss.getUserId());
                              } catch (Exception e) {
                                 throw new SimogWSException(e.getMessage());
                              }
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
