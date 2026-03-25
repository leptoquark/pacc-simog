package it.avlp.simog.common.actions;

import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.AllegatoBean;
import it.avlp.simog.beans.AllegatoType;
import it.avlp.simog.beans.CUPLOTTO;
import it.avlp.simog.beans.CodiciCup;
import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Collaborazioni;
import it.avlp.simog.beans.CollaborazioniRssa;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.RequisitoGara;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.action.AllegatiAction;
import it.avlp.simog.common.action.CupLottoAggAction;
import it.avlp.simog.common.action.GestioneRettificaAction;
import it.avlp.simog.common.beans.ResponsePubblicazioneBando;
import it.avlp.simog.common.contributo.GestioneContributoWrapperBeanClient;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.error.SimogWsXmlException;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.RequisitiGLManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.massload.xmlbeans.PubblicazioneWSDocument;
import it.avlp.simog.util.ConvertDatiComuni;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.validatore.PubblicazioneValidator;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.GaraLottoManager;
import it.avlp.simog.ws.commons.GaraXMLManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class PubblicazioneBandoActionManager {

	/******************************************************************************
	 * effettua il perfezionamento del lotto
	 * @param ticket : String
	 * @param indexCollaborazione : String
 	 * @param dataPubblicazione : String 
	 * @param dataScadenzaPagamenti : String 
	 * @param cUPLOTTO 
	 * @param cig : String
	 * @param type : String
	 * @return ResponsePubblicazioneBando
	 */
	public synchronized static ResponsePubblicazioneBando execute(String ticket, 
			String indexCollaborazione,String dataPubblicazione,
			String dataScadenzaPagamenti,String cigOgara, String progCui, String datiPubb,
			String tipoOperazione, AllegatoType[] allegati,
			String oraScadenza, String dataScadenzaRichiestaInvito, String dataLetteraInvito, CUPLOTTO[] cupVal, boolean fromTED){
		//-------	object declarations		-------//
		ResponsePubblicazioneBando rgc = null;
		Logger logger = null;
		ConnectionWSManager cwsm = null;
		Connection con = null;
		
		if(indexCollaborazione == null || "".equals(indexCollaborazione.trim())){
			indexCollaborazione = "-1";
		}
		else{
			indexCollaborazione = indexCollaborazione.trim();
		}
		
		try{
			logger = LoggerManager.getInstance().getLogger();
			logger.info("-----------	begin  	---------------");
			logger.info("eseguendo: ResponsePubblicazioneBando execute(String ticket, String indexCollaborazione,String datiGara,String cig,String type)");
			rgc = new ResponsePubblicazioneBando();
			WsSessions wss = new WsSessions();
			cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
			WSSessionManager wsm = new WSSessionManager(logger,cwsm);
			con = cwsm.getConnection();
			TicketManager tm = new TicketManager();
			SqlTools sqlt = new SqlTools();
			//-------	 begin operations		--------//	
			cwsm.setAutocommit(false);
			wss.setTicket(ticket);
			wss.setComando("PubblicazioneBando");
			wss.setCollaborazione(Integer.parseInt(indexCollaborazione));		
			wss = wsm.selectFindValidSession(wss);
			cwsm.commit();
			if(wss != null){
				logger.info(">>>>esiste una sessione associata al ticket");
				
				// PP decommentare per disattivare il servizio
				
//				String messaggioErrore = "servizio PubblicaBando non attivo";
//				logger.error(messaggioErrore);
//				wss.setLastError(messaggioErrore);				
//				wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
//				if(wsm.updateSessionAfterOp(wss)){
//					cwsm.commit();
//					logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
//					rgc.setSuccess(false);
//					rgc.setError(messaggioErrore);
//					cwsm.commit();
//				}
//				return rgc;

				try{
				   // imposto che pronvengo dai WS
				   SimogFlags.setFromWS(true);
				   
					// verifica abilitazione utente
					GaraXMLManager gxm = new GaraXMLManager(Costanti.VERS_MAX,con);
					tm.validateRequestedActionByProfile(wss, TicketManager.PUBBLICAZIONE_BANDO);
					if(tm.isValido()){
						logger.info(">>>>utente abilitato al comando richiesto");
						rgc.setSuccess(true);
						rgc.setError("");					
						//cwsm.setIsolation("t_serialize");
						logger.info(">>>> (connessione settata a transaction serialized)");
						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));                      
						
						GaraLottoManager garaLottoManager = new GaraLottoManager(con,logger);
				    	PubblicazioneManager pm = new PubblicazioneManager(con, logger); 
				    	AggiudicazioniManager am = new AggiudicazioniManager(con, logger);
						BandoGaraAction bga = new BandoGaraAction(con,logger, ConfigurationManager.getInstance().getSimogProperties());
					    GestioneRettificaAction gra = new GestioneRettificaAction(con,logger);
                        PubblicazioneValidator pv = new PubblicazioneValidator(con, logger);
					    
						PubblicazioneWSDocument pubb = null;
						Collaborazione col = null;
						CollaborazioniRssa collsRssa = null;
						Collaborazioni colls = null;
						if(!tm.isOperaComeOsservatorio()){		
							col = tm.getCollaborazione();
							colls = tm.getCollaborazioni();
							collsRssa = new CollaborazioniRssa(colls,col);
						}
							
						// verifica visibilita dei dati
						try{
							gxm.checkAuth(cigOgara,tm);
						}catch(SimogWSException swe){
							//validazione stringa xml fallita
							wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							rgc.setSuccess(false);
							String messaggioErrore = swe.getMyMessage();
							rgc.setError(messaggioErrore);
							logger.error("SimogWSException catched: "+messaggioErrore);							
							wss.setLastError(messaggioErrore);				
							wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							if(wsm.updateSessionAfterOp(wss)){
								logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
								rgc.setSuccess(false);
								rgc.setError(messaggioErrore);
								cwsm.commit();
							}else{
								//logger.debug("aggiornamento sessione fallito");
								rgc.setError(messaggioErrore+" e' possibile che la sessione non sia piu valida si prega di rieffettuare il login e ripetere l'operazione, controllando i dati");
							}return rgc;
						}

						try{					    
						    //gm verifica XML dati pubblicazione
				    		if(datiPubb!=null && !"".equals(datiPubb)){
							    try {
								    pubb = (PubblicazioneWSDocument)garaLottoManager.converti(datiPubb, wss.getUserId(),col,garaLottoManager.TIPO_PUBBLICAZIONE);
							    } 
							    catch (SimogWsXmlException e1) {
								    wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
								    rgc.setSuccess(false);
								    String messaggioErrore = e1.getMessage();
								    rgc.setError(messaggioErrore);
								    logger.error("SimogWSException catched: "+messaggioErrore);							
								    wss.setLastError(messaggioErrore);				
								    wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
								    if(wsm.updateSessionAfterOp(wss)){
									    logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
									    rgc.setSuccess(false);
									    rgc.setError(messaggioErrore);
									    cwsm.commit();
								    }
								    else{
									    //logger.debug("aggiornamento sessione fallito");
									    rgc.setError(messaggioErrore+" e' possibile che la sessione non sia piu valida si prega di rieffettuare il login e ripetere l'operazione, controllando i dati");
								    }
								    return rgc;
							    }
				    		}
				    		//gm caricamento dei dati di pubblicazione
				    		PubblicazioneBean pubblicazione = new PubblicazioneBean();
					    	if(pubb!=null)
    			    			pubblicazione = ConvertDatiComuni.getInstance().convertiPubblicazione(pubb.getPubblicazioneWS().getPubblicazione());
					    	
					    	//gm caricamento dei dati della gara
					    	GaraManager gm = new GaraManager(con,logger); 
						    TableBean dettagliGara = null;

						    String idGara = null;
						    if(cigOgara.trim().length()==10){
							    //se la stringa e un cig lotto
							    LottoManager lm = new LottoManager(con,logger);
							    List<Lotto> lotti = lm.getLottoByCigWS(cigOgara);
							    if(lotti!=null){
								    for(Lotto lotto : lotti){
									    if(idGara==null){
    									    idGara=String.valueOf(lotto.getId_Gara());
    									    break;
									    }
    							    }
							    }
							}
						    else{
						    	//altrimenti e un numero gara
						    	idGara=cigOgara;						    
							}							    
						    //gm inizio controlli di congruenza
						    if(idGara==null || "".equals(idGara))
						    	throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_29);
						    dettagliGara = gm.getDettagliGaraByIdGara(idGara);	
						    if(dettagliGara==null || dettagliGara.isEmpty())
						    	throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_30);
					
					    	
					    	//gm controlli su azioni riguardanti le gare (bando gara/lettera d'invito/perfezionamento/rettifica gara)
					    	if (PubblicazioneBean.TipoOperazione.BANDO.getCodice().equals(tipoOperazione) ||
					    		PubblicazioneBean.TipoOperazione.LETTINV.getCodice().equals(tipoOperazione) ||
					    		(PubblicazioneBean.TipoOperazione.RETTIFICA.getCodice().equals(tipoOperazione) &&
						    	(progCui == null || "".equals(progCui)))){
					    			    
							    int rowIndex = 0;
							    TableBeanRow currentRow = null;
							    while(rowIndex<dettagliGara.getTableSize()) {
							    	currentRow = dettagliGara.getRow(rowIndex);
							    	if(currentRow.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO)==null || "".equals(currentRow.getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO)))
							    		break;
							    	else
							    		rowIndex++;
							    }
							    
//						    	TableBeanRow currentRow = dettagliGara.getRow(rowIndex);
						    	//serve il bean gara, lo creo qui
					    		Gara gara = new Gara();
						       	gara = gm.getGara(Long.parseLong(idGara));
						    	
					    	    String dataPerfezionamentoBando = currentRow.getNulledField(GARA.DATA_PERFEZIONAMENTO_BANDO);
					    	    

					    
					    	    boolean pubblicataFase1 = !currentRow.getNulledField(LOTTO.DATA_PUBBLICAZIONE).isEmpty()
                                                  && !currentRow.getNulledField(LOTTO.DATA_SCADENZA_RICHIESTA_INVITO).isEmpty()
                                                  && currentRow.getNulledField(LOTTO.DATA_LETTERA_INVITO).isEmpty()
                                                  && currentRow.getNulledField(LOTTO.DATA_SCADENZA_PAGAMENTI).isEmpty();   					    	       
					    	    

					    	    //per bandi e lettere d'invito la gara non deve essere gia stata pubblicata
						    	if (PubblicazioneBean.TipoOperazione.BANDO.getCodice().equals(tipoOperazione) ||
					    	    	PubblicazioneBean.TipoOperazione.LETTINV.getCodice().equals(tipoOperazione)){
							        if(dataPerfezionamentoBando!=null && !"".equals(dataPerfezionamentoBando))
							            if( !pubblicataFase1)
							                throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_31);
						    	}
						    	//per rettifiche la gara deve aver gia avuto una pubblicazione
						    	if (PubblicazioneBean.TipoOperazione.RETTIFICA.getCodice().equals(tipoOperazione)){
							        if(dataPerfezionamentoBando==null || "".equals(dataPerfezionamentoBando))
							    	    throw new SimogWSException("Non esiste una pubblicazione da rettificare");
							    	//cerco una pubblicazione associata alla gara	                          
	    					    	PubblicazioneBean pubblicazioneGara = pm.getPubblicazione(gara.getIdPubblicazione(), gara.getDataInizioPubblicazione());
                                    //se esistono rettifiche sospese non e possibile pubblicarne altre 
	    					    	if (Costanti.FLAG_VALORE_SI.equals(pubblicazioneGara.getFlag_sospeso()))
							    	    throw new SimogWSException("Esistono gia' rettifiche in sospeso");                            	
								}
						    	/*gm non serve pio in simog 3.04, la pubblicazione e sempre possibile
						        String statoGara = currentRow.getNulledField(GARA.ID_STATO);
								if(!StatiScheda.CONFERMATO_STRING.equals(statoGara))
							    	throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_32);
								*/
						    	

						        // check avcpass
						    	
						    	
						    
						           //Map<String,Lotto> mappaLotti = bga.caricaElencoLotti(dettagliGara);
						           //List<Lotto> listaLotti = new ArrayList<Lotto>( mappaLotti.values() );
						           if(garaLottoManager.isAVCPass(gara, null, null)){
						              throw new SimogWSException(Messaggi.SIMOG_AVCPASS_001);
						           }
						        

								//validazione per bandi gara/lettere d'invito/perfezionamento
								if (PubblicazioneBean.TipoOperazione.BANDO.getCodice().equals(tipoOperazione) ||
							    	PubblicazioneBean.TipoOperazione.LETTINV.getCodice().equals(tipoOperazione)){
							    	
							    	//carico l'elenco di tutti i lotti della gara in una mappa
							        Map<String,Lotto> mappaLotti = bga.caricaElencoLotti(dettagliGara);
						     		Map<String,Lotto> mappaLottiDaPerfezionare = bga.caricaElencoLottiDaPerfezionare(mappaLotti, null);
						    	    //gm nuovo settaggio simog 3.04
						     		Map<String,Lotto> mappaLottiDaPerfezionareOPerfezionati = bga.caricaElencoLottiDaPerfezionareOPerfezionati(mappaLotti,null);

						     		//non si possono piu pubblicare gare senza lotti
						     		if(mappaLottiDaPerfezionareOPerfezionati.size()==0)
								    	throw new SimogWSException("La gara non contiene lotti");
	     		
						     		
						     		boolean isPubblicazione = false;
				            	boolean isInvitabile = false;
				            	boolean isInvito = false;
				            	boolean isBandoObbligatorio = false;
					      				
					     	   	isPubblicazione = bga.isPubblicazione(mappaLottiDaPerfezionareOPerfezionati);
					            isInvitabile = bga.isInvitabile(mappaLottiDaPerfezionareOPerfezionati);
					            //TICKET ALM #3922
					            String dataCreazione = gara.getData_creazione();
					            if(!SimogFlags.is3042Active() || !SimogProperties.getInstance().isDataCreatedAfter3042(dataCreazione))
					               isInvito = (Costanti.FLAG_VALORE_SI.equals(pubblicazione.getFlag_benicult()) && isInvitabile);
					            else
					               isInvito =  isInvitabile;
					            
					            isBandoObbligatorio = bga.isBandoObbligatorio(mappaLottiDaPerfezionareOPerfezionati);
					            
			                    int tipoProcedura = bga.getTipologiaProcedura(mappaLottiDaPerfezionareOPerfezionati,gara.getID_SVOLGIMENTO());
			                    
				    	        // PP organi costituzionali, sempre e solo perefezionamento
				    	        if(gm.isOrganoCost(gara.getCF_AMMINISTRAZIONE(), gara.getData_creazione())){
				    	        	isPubblicazione = false;
				    	        	isBandoObbligatorio = false;
				    	        	isInvitabile = false;
				    	        }

				    	        GestioneContributoWrapperBeanClient gcwbc = new GestioneContributoWrapperBeanClient(ConfigurationManager.getInstance().getSimogProperties().getContributoUrl(), con, logger);
		                        
				    	        BigDecimal totLotti = gcwbc.getImportoGara(mappaLottiDaPerfezionareOPerfezionati.values(), false, false);
			                        
                           //gm nuovo codice simog 3.04       
   			                bga.validaNumeroLotti(gara.getNumeroLotti(), mappaLottiDaPerfezionareOPerfezionati.size());
	                 
   			                boolean hasRequisiti = false;
			                
   			           
   			                   RequisitiGLManager rqm = new RequisitiGLManager(con, logger);
   			                   List<RequisitoGara> lista = rqm.getRequisitoGaraList(Long.valueOf(idGara));
   			                   hasRequisiti = lista.size() > 0;
   			                
   			                
             
                                bga.validaPubblicabilita(mappaLottiDaPerfezionare,gara.getID_SVOLGIMENTO());
                            
                                
							//MAC #3525
                            //Se la gara e' in modalita' adesione a/q senza successivo confronto competitivo
                            //ed e' stata definita una data scadenza, segnala errore
//                                3.04.8 34190 fix
                            if(SimogFlags.is3042Active() && 
                               (Costanti.MODOREAL_ADESIONE_NOCOMPET==gara.getID_MODO_REAL() || Costanti.MODOREAL_CONCESSIONE_NOCOMPET==gara.getID_MODO_REAL()) &&
                               dataScadenzaPagamenti != null &&
                               !"".equals(dataScadenzaPagamenti.trim())) {
                            	throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_40);
                            }
                            //Fine MAC #3525
							
                                
                            
                            //MAC #3525
                            //Se la gara e' in modalita' adesione a/q senza successivo confronto competitivo
                            //ed e' stata definita una data scadenza, segnala errore
//                            3.04.8 34190 fix
                            if(SimogFlags.is3042Active() && 
                               (Costanti.MODOREAL_ADESIONE_NOCOMPET==gara.getID_MODO_REAL() || Costanti.MODOREAL_CONCESSIONE_NOCOMPET==gara.getID_MODO_REAL()) &&
                               dataScadenzaPagamenti != null &&
                               !"".equals(dataScadenzaPagamenti.trim())) {
                            	throw new SimogWSException(ErrorManager.SIMOGWS_GARALOTTOMANAGER_APP_40);
                            }
                            //Fine MAC #3525
                            
                            
                            // Ticket#2015042410000651 PP mancava impostazione data 
//                            3.04.8 34190 fix
                            if(SimogFlags.is30216Active() == true && (dataScadenzaPagamenti == null || "".equals(dataScadenzaPagamenti))
                                 && (Costanti.MODOREAL_ADESIONE_NOCOMPET==gara.getID_MODO_REAL() || Costanti.MODOREAL_CONCESSIONE_NOCOMPET==gara.getID_MODO_REAL()))
                               dataScadenzaPagamenti = dataPubblicazione; 

                                
                             // verifica XML data pubblicazione e data scadenza pagamenti
                             Lotto lotto = new Lotto();
                             lotto.setData_Pubblicazione(dataPubblicazione);
                             lotto.setDataScadenzaPagamenti(dataScadenzaPagamenti);
                             lotto.setDataScadenzaRichiestaInvito(dataScadenzaRichiestaInvito);
                             lotto.setDataLetteraInvito(dataLetteraInvito);
                             
                             if (SimogFlags.is3025_RFWEBGL02Active())
                                lotto.setORA_SCADENZA(oraScadenza);
                             
                             // mi serve id Gara
                             lotto.setId_Gara(gara.getId_Gara());
                             
                             boolean isProcCompleta = false;
                             boolean isProcRistretta = false;
                                if( pubblicataFase1 ){
                                   // se la gara e' stata pubblicata in prima fase, sostituire le date pubblicazione e scadenza richiesta
                                   // con quelle salvate nella prima fase
                                   lotto.setData_Pubblicazione( currentRow.getNulledField(LOTTO.DATA_PUBBLICAZIONE) );
                                   lotto.setDataScadenzaRichiestaInvito( currentRow.getNulledField(LOTTO.DATA_SCADENZA_RICHIESTA_INVITO) );
                                }
                                
                                isProcCompleta = bga.isProceduraCompleta(mappaLottiDaPerfezionareOPerfezionati, lotto,gara.getID_SVOLGIMENTO());
                         
                                isProcRistretta = bga.isProceduraRistretta(mappaLottiDaPerfezionareOPerfezionati,gara.getID_SVOLGIMENTO())
                                               || bga.isProceduraNegoziataRistretta(mappaLottiDaPerfezionareOPerfezionati, lotto,gara.getID_SVOLGIMENTO())
                                               || isProcCompleta;
                             
                             // is3031_ESCL_AVCPASS                                
                                //FIXMATO CON FORZATURA !* PP per ora solo warning ma va innalzato il controllo ad adeguamenti effettuati
                                if(gara.getESCLUSO_AVCPASS() == null)
                                   // forzo l'esclusione per non bloccare la pubblicazione, rischio se non mi mandano i requisiti!
                                   gara.setESCLUSO_AVCPASS(Costanti.FLAG_VALORE_SI);

                             // validazioni per codici cup
                             if(SimogProperties.getInstance().isCUPAttivo()){
                                  CupLottoAggAction claAction = new CupLottoAggAction(con, logger);
                                  // carico ed integro la lista dei codici cup presenti
                                  List<CupLottoAggExt> elencoCupGara = claAction.getElencoCupGara(Long.parseLong(idGara));
                                  
                                  // scansione delle validazioni esplicite
                                  if(cupVal != null){
                                     boolean found = false;
                                     
                                     // tolgo i cup non validi dipe dall'elenco
                                     List<CupLottoAggExt> listaCUPOk = new LinkedList<CupLottoAggExt>();
                                     for (CupLottoAggExt elem : elencoCupGara){
                                        // solo CUP validati DIPE
                                        if (Costanti.FLAG_VALORE_SI.equals(elem.getDatiDIPE().getVALIDO())){
                                           CupLottoAggExt item = new CupLottoAggExt();
                                           item.setCig( elem.getCig() );
                                           item.setCup(elem.getCup());
                                           item.setIdLotto( elem.getIdLotto() );
                                           item.setIdAggiudicazione( elem.getIdAggiudicazione());
                                           item.setDataInizioAgg( elem.getDataInizioAgg());
                                           item.setOkUtente( elem.getOkUtente() );
                                           item.setDatiDIPE(elem.getDatiDIPE());
                                           listaCUPOk.add( item );
                                        }
                                     }
                                     elencoCupGara = new LinkedList<CupLottoAggExt>();
                                     elencoCupGara.addAll(listaCUPOk);
                                     
                                     for (CupLottoAggExt elem : elencoCupGara){
                                        // ricerco nell'elenco cup letto dal db ogni cup indicato 
                                        // dall'utente e se non e confermato, ma l'utente dice SI
                                        // aggiorno sul db
                                        if(!Costanti.FLAG_VALORE_SI.equals(elem.getOkUtente())){
                                           // cup non validato vedo se l'utente mi ha inviato la validazione
                                           for(CUPLOTTO item : cupVal){
                                              if(item.getCODICICUP()!=null){
                                                 for(CodiciCup cupe : item.getCODICICUP()){
                                                    if(elem.getCup().equals(cupe.getCUP()) && Costanti.FLAG_VALORE_SI.equals(cupe.getOK_UTENTE())){
                                                       elem.setOkUtente(cupe.getOK_UTENTE());
                                                       found = true;
                                                    }
                                                 }
                                              }
                                           }
                                        }
                                     }
                                     // se ho trovato conferme aggiorno i record sul db
                                     if (found){
                                        // aggiorno OK_UTENTE
                                        claAction.confirmAllValidCup(elencoCupGara);
                                        
                                        // rileggo i dati per le validazioni successive
                                        elencoCupGara = claAction.getElencoCupGara(Long.parseLong(idGara));
                                     }
                                  }
                                  // validazione per pubblicazione
                                  if (elencoCupGara != null){
                                     // imposto solo i dati che mi servono
                                     Lotto lt = new Lotto();
                                     lt.setId_Gara(Long.valueOf(idGara));
                                     lt.setElencoCup(elencoCupGara);
                                     bga.validaCodiciCUPPerf(lt);
                                  }
                                   
                                  // valorizzo la struttura per restituire in response gli esiti DIPE
                                 // dsadasadad sa la List  
                                 if(elencoCupGara != null && elencoCupGara.size() > 0){
                                     CUPLOTTO[] cupOut = new CUPLOTTO[0];
                                     int i = 0;
                                     boolean cigFound = false;
                                     for(CupLottoAggExt elem : elencoCupGara){
                                        CUPLOTTO temp = null;
                                        cigFound = false;
                                        // cerco elemento con stesso cig se esiste
                                        for(CUPLOTTO item : cupOut){
                                           if(item != null && item.getCIG().equals(elem.getCig())){
                                              temp = item;
                                              cigFound = true;
                                              break;
                                           }
                                        }
                                           
                                        if (!cigFound){
                                           cupOut = Arrays.copyOf(cupOut, cupOut.length+1);
                                           cupOut[cupOut.length-1] = new CUPLOTTO();
                                           temp = cupOut[cupOut.length-1];
                                           temp.setCIG(elem.getCig());
                                           temp.setCODICICUP(new CodiciCup[0]);
                                        }
                                           
                                        // valorizzo il codice cup
                                        //CodiciCup[] arrOut = new CodiciCup[temp.getCODICICUP().length + 1];
                                        
                                        CodiciCup[] arrOut = Arrays.copyOf(temp.getCODICICUP(), temp.getCODICICUP().length+1);
                                        
//                                        for (int j = 0; j < temp.getCODICICUP().length; j++) {
//                                          arrOut[j] = temp.getCODICICUP()[j];
//                                       }
                                        
                                        CodiciCup curr = new CodiciCup();
                                        curr.setCUP(elem.getCup());
                                        curr.setDATI_DIPE(PageHelper.getNulledField(elem.getDatiDIPE().getESITO_RICHIESTA()));
                                        curr.setID_RICHIESTA(PageHelper.getNulledField(String.valueOf(elem.getDatiDIPE().getID_RICHIESTA())));
                                        curr.setOK_UTENTE(elem.getOkUtente()== null ? "N" : elem.getOkUtente());
                                        curr.setVALIDO(elem.getDatiDIPE().getVALIDO()==null ? "N":elem.getDatiDIPE().getVALIDO());
                                        arrOut[arrOut.length-1] = curr;
                                              
                                        temp.setCODICICUP(arrOut);
                                        
                                        //cupOut = Arrays.copyOf(cupO
                                        //cupOut[i++] = temp;
                                        
                                     }
                                     rgc.setCUPLOTTO(cupOut);
                                  }
                             }

                             bga.validaPubblicazione(pubblicazione, isPubblicazione, isInvito, totLotti, hasRequisiti, gara, isProcRistretta);
		                          
		                       if( pubblicataFase1 ) //Esegui fase 2
		                           bga.validaPerfezionamentoProceduraRistretta(lotto);
		                       else 
		                           bga.validaPerfezionamentoLotti(lotto, (mappaLottiDaPerfezionare!=null && !mappaLottiDaPerfezionare.isEmpty()), tipoProcedura);
						    		
						    	    ArrayList<AllegatoBean> allegatiOk = new ArrayList<AllegatoBean>();
						    	    // controllo degli allegati se non ci sono errori di validazione precedenti
						    	    if(bga.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() == 0){
						    	    	
						    	        if(allegati != null){
      						    	    	for (int i = 0; i < allegati.length; i++) {			
      						    				AllegatoType element = allegati[i];
      						    				if(PubblicazioneBean.TipoDocumento.getEnumByTipo(element.getTipoDocumento()) != null){				
      						    		    		AllegatoBean allBean = new AllegatoBean();
      						    					allBean.setTipoDoc(element.getTipoDocumento());	
      						    					allBean.setNomeFile(element.getNomeFile());
      						    					allBean.setBout(element.getDocumento());	
      						    					allBean.setNote(element.getNote());
      						    					allBean.setIdGara(Integer.parseInt(idGara));
      						    					allBean.setFromWS(true);
      						    					
      						    	    			AllegatiAction allAct = new AllegatiAction(allBean, ConfigurationManager.getInstance().getSimogProperties(), con, logger);												    			
      						    	    			// non e riuscita la memorizzazione 
      						    	    			if(allAct.checkAndSave() == 0){
      						    	    			    String msg = new SimogWSException(ErrorManager.SIMOGWS_PUBBLICAZIONE_APP_35).getMyMessage();
      						    	    				bga.getEccezioni().addValidationErr(msg.replace("$1", PubblicazioneBean.TipoDocumento.getEnumByTipo(allBean.getTipoDoc()).getDescr()).replace("$2", allBean.getEsitoCheck()));
      						    	    			}
      						    	    			else 
      						    	    				allegatiOk.add(allBean);
      						    				}								
      						    			}
						    	        }
						    	        
						    	        //Se viene effettuata la pubblicazione del bando verso il TED, il bando non deve essere inserito
						    	        if(isBandoObbligatorio) {
						    	        	isBandoObbligatorio = !fromTED;
						    	        }
						    	        
						    	        if(isProcCompleta)
						    	           bga.validaAllegatiWS(allegatiOk, ParametriServlet.PUBBLICAZIONE_PROCEDURA_RISTRETTA_COMPLETA, isBandoObbligatorio);
						    	        else if(isPubblicazione && !pubblicataFase1)
						    	           bga.validaAllegatiWS(allegatiOk, ParametriServlet.PUBBLICAZIONE_BANDO_GARA, isBandoObbligatorio);
						    	        else if(pubblicataFase1)
						    	           bga.validaAllegatiWS(allegatiOk, ParametriServlet.PUBBLICAZIONE_LETT_INV, isBandoObbligatorio);
/* PP controllo esagerato e per procedura ristretta pericoloso
						    	        if(!isPubblicazione && !isInvito){		    	    	
						    	           //per le gare perfezionabili non sono previsti allegati
						    	           if(allegatiOk.size()>0)	
						    	              throw new SimogWSException("Allegati non previsti per gare da perfezionare");
						    	        }
*/						    		 
						    	    }
						    	    
						    	    // gm fine della validazione   	
						    	    if(bga.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() != 0)
							    		throw new Exception(bga.getEccezioni().toString());
							    	
						    	    //gm nuovo settaggio per simog 3.04
						    	    gara.setNumeroLotti(mappaLottiDaPerfezionareOPerfezionati.size());
						    	    gara.setIMPORTO_GARA(totLotti);
					    	    	
									// messaggi da ritornare
						    		AllValidationBeans msgs = new AllValidationBeans();	
						    		
						    		if( pubblicataFase1 ){ //esegui pubblicazione ristretta fase 2
						    		    bga.pubblicaBandoGaraProceduraRistretta(wss.getUserId(), gara, mappaLottiDaPerfezionareOPerfezionati, 
						    		        dataLetteraInvito, dataScadenzaPagamenti, oraScadenza);
						    		} else {
      								    bga.pubblicaBandoGara(wss.getUserId(), gara, pubblicazione, mappaLottiDaPerfezionare, 
      								    	new HashMap<String,Lotto>(), dataPubblicazione, dataScadenzaPagamenti, 
      								   		null, null, isPubblicazione, isInvito, allegatiOk,
      								   		gcwbc.getImportoGara(mappaLottiDaPerfezionareOPerfezionati.values(), true, false), 
      								   		msgs, oraScadenza, dataScadenzaRichiestaInvito, dataLetteraInvito);
						    		}
								    // commit della transazione
								    cwsm.commit();	    
								    rgc.setSuccess(true);
                                    String msg = new SimogWSException(ErrorManager.SIMOGWS_PUBBLICAZIONE_APP_36).getMyMessage();
							    	rgc.setMessaggio(msg);
								   logger.info("pubblicazione riuscita");								   
								}
								//validazione rettifiche (di gara)
								else if (PubblicazioneBean.TipoOperazione.RETTIFICA.getCodice().equals(tipoOperazione) &&
								    	(progCui == null || "".equals(progCui))){
									gra.validaPubblicazioneRettifica(pubblicazione, ParametriServlet.PUBBLICAZIONE_RETTIFICA);
					    	        //inserisco gli eventuali errori nel BandoGaraAction
									bga.getEccezioni().add(gra.getEccezioni());
									ArrayList<AllegatoBean> allegatiOk = new ArrayList<AllegatoBean>();
                                    if(bga.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() == 0){
						    	    	
                                        if(allegati != null){
      						    	      	for (int i = 0; i < allegati.length; i++) {			
      						    			AllegatoType element = allegati[i];
      						    			if(PubblicazioneBean.TipoDocumento.getEnumByTipo(element.getTipoDocumento()) != null){				
      						    	    		AllegatoBean allBean = new AllegatoBean();
      						   					allBean.setTipoDoc(element.getTipoDocumento());	
      						   					allBean.setNomeFile(element.getNomeFile());
      						    				allBean.setBout(element.getDocumento());	
      						    				allBean.setNote(element.getNote());
      						    				allBean.setIdGara(Integer.parseInt(idGara));
      						    				allBean.setFromWS(true);
      						    					
      						    	    		AllegatiAction allAct = new AllegatiAction(allBean, ConfigurationManager.getInstance().getSimogProperties(), con, logger);												    			
      						    	    		// non e riuscita la memorizzazione 
      						    	    		if(allAct.checkAndSave() == 0){
                                                      String msg = new SimogWSException(ErrorManager.SIMOGWS_PUBBLICAZIONE_APP_35).getMyMessage();
      						    	   				bga.getEccezioni().addValidationErr(msg.replace("$1", PubblicazioneBean.TipoDocumento.getEnumByTipo(allBean.getTipoDoc()).getDescr()).replace("$2", allBean.getEsitoCheck()));
      						    	    		}
      						    	   			else 
      						    	   				allegatiOk.add(allBean);
      						    				}								
      						    			}
                                        }
					    	    		bga.validaAllegatiWS(allegatiOk, ParametriServlet.PUBBLICAZIONE_RETTIFICA, false);
						   			}
									 if(bga.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() != 0)
								    	throw new Exception(bga.getEccezioni().toString());	    	
									gra.pubblicaRettifica(wss.getUserId(), gara, pubblicazione, allegatiOk);
									// commit della transazione
								    cwsm.commit();	    
								    rgc.setSuccess(true);
                                    String msg = new SimogWSException(ErrorManager.SIMOGWS_PUBBLICAZIONE_APP_36).getMyMessage();
							    	rgc.setMessaggio(msg);
								    logger.info("rettifica riuscita");
								}
								else{
									throw new SimogWSException("Il tipo di operazione inserito non e' valido");
								}
					    	}
					    					                         		    	
					    	//gm controlli su avviso di aggiudicazione/rettifica (aggiudicazione)
					    	else if (PubblicazioneBean.TipoOperazione.AVVISOAGG.getCodice().equals(tipoOperazione) ||
						    		(PubblicazioneBean.TipoOperazione.RETTIFICA.getCodice().equals(tipoOperazione) &&
									(progCui != null && !"".equals(progCui)))){
	                            if(progCui != null && !"".equals(progCui)){	                                
	                            	//cerco un'aggiudicazione confermata in base al progCui
	                            	AggiudicazioneBean aggiudicazione = am.getAggiudicazioneByProgAndCui(progCui, true);
	                            	if(aggiudicazione == null)
	                            		throw new SimogWSException("Aggiudicazione non trovata");
	                            	else{
	                            		//controllo che l'aggiudicazione discenda dall'idGara inserito
	                            		InfoComuniManager icm = new InfoComuniManager(con, logger);
	                            		InfoComuniBean infoComuni = icm.load(aggiudicazione.getIdInfo(), aggiudicazione.getDataInizioInfo());
	                            		if(infoComuni == null)
		                            		throw new SimogWSException("Dati Comuni dell'aggiudicazione non trovati");
		                                boolean childOfGara = false;
		                                for(int rowIndex=0; rowIndex < dettagliGara.getTableSize(); rowIndex++){
		                    			    TableBeanRow currentRow = dettagliGara.getRow(rowIndex);
		                    			    if(currentRow!=null){
		                    			    	String idLotto = currentRow.getNulledField(LOTTO.ID_LOTTO);
		                    		            if(infoComuni.getIdLotto() == (Long.parseLong(idLotto)))                   		            	
		                                	        childOfGara = true;
		                    			    }
		                                }
		                                
		                                if(childOfGara == false)
		                            		throw new SimogWSException("Incongruenza tra il Cig ed il progCui");

	                            		//cerco una pubblicazione associata all'aggiudicazione	                          
    	    					    	PubblicazioneBean pubblicazioneAggiudicazione = pm.getPubblicazione(aggiudicazione.getIdPubblicazioneAgg(), aggiudicazione.getDataPubblicazioneAgg());
    	    					    	boolean pubbAggVuota = pv.isEmptyPubblicazione(pubblicazioneAggiudicazione);
    	    					    	//se e un avviso di aggiudicazione, non devono esserci pubblicazioni precedenti
    	    					    	if (PubblicazioneBean.TipoOperazione.AVVISOAGG.getCodice().equals(tipoOperazione) &&
    	    					    	    !pubbAggVuota)
								        	throw new SimogWSException("Esiste gia' un avviso di aggiudicazione");
    	    					    	//se e una rettifica di aggiudicazione, deve esserci una pubblicazione precedente
    	    					    	if (PubblicazioneBean.TipoOperazione.RETTIFICA.getCodice().equals(tipoOperazione) &&
    	    					    		pubbAggVuota)
    								       	throw new SimogWSException("Non esiste un avviso da rettificare");
	                                  	
    	    					    	ArrayList<AllegatoBean> allegatiOk = new ArrayList<AllegatoBean>();
                                        if(bga.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() == 0){

                                            if(allegati != null){
          						    	      	for (int i = 0; i < allegati.length; i++) {			
          						    			AllegatoType element = allegati[i];
          						    			if(PubblicazioneBean.TipoDocumento.getEnumByTipo(element.getTipoDocumento()) != null){				
          						    	    		AllegatoBean allBean = new AllegatoBean();
          						   					allBean.setTipoDoc(element.getTipoDocumento());	
          						   					allBean.setNomeFile(element.getNomeFile());
          						    				allBean.setBout(element.getDocumento());	
          						    				allBean.setNote(element.getNote());
          						    				allBean.setIdGara(Integer.parseInt(idGara));
          						    				allBean.setFromWS(true);
          						    					
          						    	    		AllegatiAction allAct = new AllegatiAction(allBean, ConfigurationManager.getInstance().getSimogProperties(), con, logger);												    			
          						    	    		// non e riuscita la memorizzazione 
          						    	    		if(allAct.checkAndSave() == 0){
                                                          String msg = new SimogWSException(ErrorManager.SIMOGWS_PUBBLICAZIONE_APP_35).getMyMessage();
          						    	   				bga.getEccezioni().addValidationErr(msg.replace("$1", PubblicazioneBean.TipoDocumento.getEnumByTipo(allBean.getTipoDoc()).getDescr()).replace("$2", allBean.getEsitoCheck()));
          						    	    			}
          						    	   			else 
          						    	   				allegatiOk.add(allBean);
          						    				}								
          						    			}
                                            }
    						   			}
    	    					    	
    	    					    	//validazione della rettifica
	       								if (PubblicazioneBean.TipoOperazione.RETTIFICA.getCodice().equals(tipoOperazione)){
	 						    	        gra.validaPubblicazioneRettifica(pubblicazione, ParametriServlet.PUBBLICAZIONE_RETTIFICA);
	 					    	            //inserisco gli eventuali errori nel BandoGaraAction
	 						    	        bga.getEccezioni().add(gra.getEccezioni());
	 						    	        bga.validaAllegatiWS(allegatiOk, ParametriServlet.PUBBLICAZIONE_RETTIFICA_AVVISO_AGG, false);
	 						    	        if(bga.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() != 0)
	 									    	throw new Exception(bga.getEccezioni().toString());	    	
	 										gra.pubblicaRettificaAvviso(wss.getUserId(), Long.parseLong(idGara), pubblicazione, aggiudicazione.getIdAggiudicazione(), aggiudicazione.getDataInizioAggiudicazione(), allegatiOk);
	 										// commit della transazione
	 									    cwsm.commit();	    
	 									    rgc.setSuccess(true);
                                            String msg = new SimogWSException(ErrorManager.SIMOGWS_PUBBLICAZIONE_APP_36).getMyMessage();
	 								    	rgc.setMessaggio(msg);
	 									    logger.info("rettifica riuscita");
	 								
	       								}
	       								//validazione dell'avviso di aggiudicazione
	 						    	    else if (PubblicazioneBean.TipoOperazione.AVVISOAGG.getCodice().equals(tipoOperazione)){
	 						    	    	bga.validaPubblicazioneAvviso(pubblicazione);
	 						    	    	bga.validaAllegatiWS(allegatiOk, ParametriServlet.PUBBLICAZIONE_AVVISO, false);
	 						    	    	if(bga.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() != 0)
	 									    	throw new Exception(bga.getEccezioni().toString());	    	
	 									    bga.pubblicaAvviso(wss.getUserId(), Long.parseLong(idGara), pubblicazione, allegatiOk, aggiudicazione.getIdAggiudicazione(), aggiudicazione.getDataInizioAggiudicazione());
	 										// commit della transazione
	 									    cwsm.commit();	    
	 									    rgc.setSuccess(true);
	 									    String msg = new SimogWSException(ErrorManager.SIMOGWS_PUBBLICAZIONE_APP_36).getMyMessage();
	 								    	rgc.setMessaggio(msg);
	 									    logger.info("avviso di aggiudicazione riuscito");		
	 						    	    }
	 						    	    else{
	 						    	    	throw new SimogWSException("Il tipo di operazione inserito non e' valido");
	 						    	    }
	                            	}
	                            }
	                            //se il cui non esiste sollevo un'eccezione
	                            else
							    	throw new SimogWSException("Il CUI inserito non e' valido");
					    	} else 
					    		if (PubblicazioneBean.TipoOperazione.RETTIFICA_DATE.getCodice().equals(tipoOperazione)){
								
								 LottoManager lm = new LottoManager(con,logger);
								 List<Lotto> lotti = lm.getListaLotti(Long.valueOf(idGara));
								 if(lotti.isEmpty())
									 throw new SimogWSException("Operazione non consentita: non sono presenti lotti");
								 else { 
									    Gara g = gm.getGara(lotti.get(0).getId_Gara());
									    if(g.getID_MODO_REAL()==Costanti.MODOREAL_ADESIONE_NOCOMPET || g.getID_MODO_REAL()==Costanti.MODOREAL_CONCESSIONE_NOCOMPET)
									    	dataScadenzaPagamenti=dataPubblicazione;
									    	
										String res = gra.validaRettificaDate(dataPubblicazione,
																			dataScadenzaPagamenti,
																			dataScadenzaRichiestaInvito, 
																			dataLetteraInvito, 
																			oraScadenza,
																			lotti.get(0));
										if(res!=null) {
											rgc.setSuccess(false);
											rgc.setMessaggio(res);
										} else {
											lm.updateDatePubblicazione(dataPubblicazione, 
																		dataScadenzaPagamenti, 
																		dataScadenzaRichiestaInvito, 
																		dataLetteraInvito, oraScadenza, Long.valueOf(idGara));
											rgc.setSuccess(true);
											String msg = new SimogWSException(ErrorManager.SIMOGWS_PUBBLICAZIONE_APP_36).getMyMessage();
									    	rgc.setMessaggio(msg);
										    logger.info("rettifica riuscita");
										}
								 }
								
					    	}
					    	//se il tipo di operazione non esiste sollevo un'eccezione
					    	else
							    throw new SimogWSException("Il tipo di operazione inserito non e' valido");
						}
						catch(Exception e){
							//gm catch di una generica eccezione perche devo catturare tutte quelle
							//possibili (Exception e SQLException da getLottoByCigWS e getGaraList, 
							//ActionException da pubblicaBandoGara)
							logger.error("pubblicazione/perfezionamento fallito "+e.getMessage());
							
							e.printStackTrace();
							
							if(cwsm != null)
								cwsm.rollback();
							rgc.setSuccess(false);
							//trasformo l'eccezione in SimogWSException per gestirla nel WS
							SimogWSException swe = new SimogWSException(e.getMessage());
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
							}
							else{
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
					}
					else{
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
				}
				catch(SimogWSException swe){
					logger.error("indice collaborazione non valido");
					String messaggioErrore = swe.getMyMessage();
					wss.setLastError("collaborazione ["+wss.getCollaborazione()+"] non esiste");				
					wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
					if(wsm.updateSessionAfterOp(wss)){
						cwsm.commit();
						logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
						rgc.setSuccess(false);
						rgc.setError(messaggioErrore);
					}
					return rgc;
				}
			}		
		}
		catch(SimogWSException swe){
			if(cwsm != null){
				cwsm.rollback();
			}
			rgc.setSuccess(false);
			rgc.setError(swe.getMyMessage());
			logger.error("SimogWSException catched: "+swe.getMyMessage());
		}
		catch(Throwable t){
			t.printStackTrace();
		}
		finally{
			if(cwsm != null){
				cwsm.closeConnection();
			}
		}
		logger.info("----------		END		----------");
		return rgc;

	}
}			