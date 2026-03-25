package it.avlp.simog.garamanager.app;

import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avlp.simog.actions.PubblicazioneAction;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.AllegatoBean;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.common.action.GestioneRettificaAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.ALLEGATI;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.garamanager.AllegatoManager;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.PubblicazioneValidator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SrvGestioneRettifica extends ServletBase implements ParametriServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		perform(request, response);
	}
	
	protected void perform(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		try{
		    currentActiveConnection = getSimogConnection(request.getSession().getId(),  getClass().getName());
		    currentActiveConnection.setAutoCommit(false);
//		  PP esagerata currentActiveConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);	

		    if ( checkSession(request) ) {	    				
		        //gm caricamento di tutte le info necessarie
		  		GestioneRettificaAction gra = new GestioneRettificaAction(currentActiveConnection,logger);
		   		String action = request.getParameter("toDo");			    
				String idGara = request.getParameter(SESSION_ID_GARA);
			   	GaraManager garaManager = new GaraManager(currentActiveConnection, logger);	
			   	LottoManager lottoManager = new LottoManager(currentActiveConnection, logger);	
				Gara gara = null; //garaManager.getGara(Long.parseLong(idGara));
				gara = garaManager.getGara(Long.valueOf(idGara), currentUser.getUffici());
				if(gara == null){
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
					return;
				}
				
				//dettagli gara necessari per verificare isPubblicabile
				TableBean dettagliGara = null;
				if (!currentUser.isRUP())
					dettagliGara = garaManager.getDettagliGaraByIdGara(idGara);
				else
    				dettagliGara = garaManager.getDettagliGaraByIdGaraRSSA( idGara, currentUser.getUffici() );
				
				PubblicazioneManager pubManager = new PubblicazioneManager(currentActiveConnection, logger);	
			   	PubblicazioneBean pubblicazione = pubManager.getPubblicazione(gara.getIdPubblicazione(), gara.getDataInizioPubblicazione());
    	    	String tipoAllegato = request.getParameter(ParametriServlet.TIPO_ALLEGATO);
				String tipoOperazione = request.getParameter(ParametriServlet.TIPO_OPERAZIONE);

				java.util.List<Lotto> listaLotti = lottoManager.getListaLotti(Long.valueOf(idGara));
				if (listaLotti == null || listaLotti.isEmpty()) {
					sendError(request, response, Messaggi.SIMOG_VALIDAZIONE_000, ParametriServlet.JSP_ERRORE);
					return;
				}
				Lotto l = listaLotti.get(0);

			   	//gm reinvio dei parametri per la visualizzazione della gara
				request.setAttribute(SESSION_ID_GARA, idGara);  	
			   	request.setAttribute(ParametriServlet.TIPO_ALLEGATO, tipoAllegato);		
			   	request.setAttribute("GARA", gara);	
                //String operazione = PubblicazioneBean.TipoOperazione.getEnumByTipo(pubblicazione.getTipoOperazione()).getDescr(); 	 
                request.setAttribute(ParametriServlet.TIPO_OPERAZIONE, tipoOperazione);	
			    
                Object dataPubb = request.getParameter(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE);
                if(dataPubb!=null) {
                	request.setAttribute(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE, (String)dataPubb);
                    } else {
	                	if(l.getData_Pubblicazione()!=null)
	                	   request.setAttribute(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE, PageHelper.getFormattedDate(l.getData_Pubblicazione()));
	                }
                
                Object dataScadenzaInvito = request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO);
                if(dataScadenzaInvito != null) {
                	request.setAttribute(ParametriServlet.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO, (String)dataScadenzaInvito);
		        } else {
                	if(l.getDataScadenzaRichiestaInvito()!=null)
                	    request.setAttribute(ParametriServlet.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO, PageHelper.getFormattedDate(l.getDataScadenzaRichiestaInvito()));
                	else
                		request.setAttribute(ParametriServlet.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO,"");
                }
                
                Object dataScadenzaPagamenti = request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA);
                request.setAttribute("DateRettifica", "");
                if(dataScadenzaPagamenti != null) {
                	String dataPag = PageHelper.getFormattedDBDate((String)dataScadenzaPagamenti);
                	String disablefields = PageHelper.getCurrentDate().compareTo(dataPag) > 0 ? "disabled" : "";
                	request.setAttribute(ParametriServlet.FIELD_NAME_DATA_SCADENZA, (String)dataScadenzaPagamenti);
                	request.setAttribute("DateRettifica", disablefields);
		        } else {
                	if(l.getDATA_SCADENZA_PAGAMENTI()!=null) {
                		String dataPag = l.getDATA_SCADENZA_PAGAMENTI();
                		String disablefields = PageHelper.getCurrentDate().compareTo(dataPag) > 0 ? "disabled" : "";
                	   request.setAttribute(ParametriServlet.FIELD_NAME_DATA_SCADENZA, PageHelper.getFormattedDate(dataPag));
                	   request.setAttribute("DateRettifica", disablefields);
                	} else
                		request.setAttribute(ParametriServlet.FIELD_NAME_DATA_SCADENZA,"");
                }
                
                Object dataLetteraInvito = request.getParameter(ParametriServlet.FIELD_NAME_DATA_LETTERA_INVITO);
                if(dataLetteraInvito != null) {
                	request.setAttribute(ParametriServlet.FIELD_NAME_DATA_LETTERA_INVITO, (String)dataLetteraInvito);
		        } else {
                	if(l.getDataLetteraInvito()!=null)
                	   request.setAttribute(ParametriServlet.FIELD_NAME_DATA_LETTERA_INVITO, PageHelper.getFormattedDate(l.getDataLetteraInvito()));
                	else
                		request.setAttribute(ParametriServlet.FIELD_NAME_DATA_LETTERA_INVITO,"");
                }
                
                
                Object oraScad = request.getParameter(ParametriServlet.FIELD_NAME_ORA_SCADENZA);      
                if(oraScad != null) {
                	request.setAttribute(ParametriServlet.FIELD_NAME_ORA_SCADENZA, (String)oraScad);
		        } else {
                	if(l.getORA_SCADENZA()!=null)
                	   request.setAttribute(ParametriServlet.FIELD_NAME_ORA_SCADENZA, l.getORA_SCADENZA());
                	else
                		request.setAttribute(ParametriServlet.FIELD_NAME_ORA_SCADENZA, "");
                }
                
                String idAggiudicazione = request.getParameter(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE);
    	    	String dataInizioAgg = request.getParameter(PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE);
                String noteRettifica = request.getParameter(ParametriServlet.FIELD_NAME_NOTE_ALLEGATO);
    	    	request.setAttribute(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE, idAggiudicazione);
	    		request.setAttribute(PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE, dataInizioAgg);
	    		request.setAttribute(ParametriServlet.FIELD_NAME_NOTE_ALLEGATO, noteRettifica);

	    		//gm creazione della lista dello storico pubblicazioni da inserire nell'apposito pannello della jsp
	    		List <PubblicazioneBean> storicoPubblicazioni = new ArrayList<PubblicazioneBean>();
	    		if (ParametriServlet.ACTION_CARICA_RETTIFICA.equals(action) ||
		        	ParametriServlet.ACTION_SALVA_RETTIFICA.equals(action)){   
    	    		storicoPubblicazioni = pubManager.getStoricoPubblicazioniGara(Long.parseLong(idGara));
	    		}
	    		if (ParametriServlet.ACTION_CARICA_RETTIFICA_AVVISO.equals(action) ||
			        ParametriServlet.ACTION_SALVA_RETTIFICA_AVVISO.equals(action)){   
	    	    	storicoPubblicazioni = pubManager.getStoricoPubblicazioniAggiudicazione(Long.parseLong(idAggiudicazione), PageHelper.parseTime(dataInizioAgg));
		    	}
	    		request.setAttribute(ParametriServlet.STORICO_PUBBLICAZIONI, storicoPubblicazioni);
				  
			   	// cerco eventuali allegati presenti al primo caricamento della servlet
			   	String allRettifica = request.getParameter(ParametriServlet.ALLEGATO_RETTIFICA);
	        	AllegatoManager aMan = new AllegatoManager(currentActiveConnection, logger);
	        	AllegatoBean aBean = new AllegatoBean();
	        	aBean.setIdGara(Integer.parseInt(idGara));
	        	if (ParametriServlet.ACTION_CARICA_RETTIFICA.equals(action) ||
	        		ParametriServlet.ACTION_SALVA_RETTIFICA.equals(action)){
	    	    	aBean.setTipoDoc(PubblicazioneBean.TipoDocumento.RETTIFICA.getCodice());	        		
	        	}
                if (ParametriServlet.ACTION_CARICA_RETTIFICA_AVVISO.equals(action) ||
               		ParametriServlet.ACTION_SALVA_RETTIFICA_AVVISO.equals(action)){
	    	    	aBean.setTipoDoc(PubblicazioneBean.TipoDocumento.RETTIFICAAVVISO.getCodice());	        		
	        	}
	        	List aBeans = aMan.load(aBean);
	        	if(aBeans.size()>0){
	        		aBean = (AllegatoBean)aBeans.get(0);
	        		allRettifica = String.valueOf(aBean.getIdAllegato()).trim();
	        	}
	    	    		
			   	request.setAttribute(ParametriServlet.ALLEGATO_RETTIFICA, allRettifica);   	    	
		    	if(allRettifica != null)
		    		request.setAttribute(ParametriServlet.ALLEGATO_RETTIFICA_DESC, ParametriServlet.PRESENTE);
		    	else
	    	   		request.setAttribute(ParametriServlet.ALLEGATO_RETTIFICA_DESC, "");
			   	String url = ParametriServlet.JSP_TRASMETTI_RETTIFICA;
			    if ( currentUser.isRSSAorRUP()){ 
			    	/********************* BEGIN Gestione Actions RUP*************************/
			    	//gm action caricamento della gara per la rettifica
		    	    if (ParametriServlet.ACTION_CARICA_RETTIFICA.equals(action) || ParametriServlet.ACTION_CARICA_RETTIFICA_AVVISO.equals(action)){  	
				    	forward(url, request, response);
		    	    }
		        	//gm se la action è la validazione della rettifica
		    	    else if (ParametriServlet.ACTION_SALVA_RETTIFICA.equals(action)){
		    	    		    					    	
				    	PubblicazioneBean pubbNuova = new PubblicazioneBean();			    		
				    	PubblicazioneAction pubAction = new PubblicazioneAction(currentActiveConnection, logger);
				    	pubbNuova = pubAction.getBean(request);
				    	//gm mantengo memoria del flag beni culturali originale
				    	pubbNuova.setFlag_benicult(pubblicazione.getFlag_benicult());
		    	     
				    	gra.validaPubblicazioneRettifica(pubbNuova, ParametriServlet.PUBBLICAZIONE_RETTIFICA);
		    	        	
		    	        //gm nuovo codice estensione pubblicazione bandi
		    	        gra.validaAllegatiRettifica(allRettifica, noteRettifica);
		    	        pubbNuova.setTipoOperazione(PubblicazioneBean.TipoOperazione.RETTIFICA.getCodice());
		    	        request.setAttribute(ParametriServlet.PUBBLICAZIONE, pubbNuova);	    	
		    	        
						String dataPubblicazioneStr = null;
		    	        String dataTermineStr = null;
		    	        String dataScadInvitoStr = null;
		    	        String dataLettInvitoStr = null;
		    	        String oraScadStr = null;
						
	    	        if((l.getDATA_SCADENZA_PAGAMENTI()!=null && PageHelper.getCurrentDate().compareTo(l.getDATA_SCADENZA_PAGAMENTI())<=0)
	    	        		||
	    	        	(l.getDataScadenzaRichiestaInvito()!=null && PageHelper.getCurrentDate().compareTo(l.getDataScadenzaRichiestaInvito())<=0)) {
						
							dataPubblicazioneStr = dataPubb != null && !"".equals(dataPubb) ? (String) dataPubb : null;
							dataTermineStr = dataScadenzaPagamenti != null && !"".equals(dataScadenzaPagamenti) ? (String) dataScadenzaPagamenti : null;
							dataScadInvitoStr = dataScadenzaInvito != null && !"".equals(dataScadenzaInvito) ? (String) dataScadenzaInvito : null;
							dataLettInvitoStr = dataLetteraInvito != null && !"".equals(dataLetteraInvito) ? (String) dataLetteraInvito : null;
							oraScadStr = oraScad != null && !"".equals(oraScad) ? (String) oraScad : null;
							   
							
						   String resValidazione =  gra.validaRettificaDate(dataPubblicazioneStr, 
																			dataTermineStr,
																			dataScadInvitoStr,
																			dataLettInvitoStr,
																			oraScadStr,
																			l);
						   if(resValidazione!=null)
							   gra.getEccezioni().addValidationErr(resValidazione);
						}
						
				    	//se non ho avuto errori nella validazione dei bean
			    	    if(gra.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() == 0){
				    		//effettuo la rettifica
			    	    	ArrayList <AllegatoBean> listaAllegati = new ArrayList<AllegatoBean>();
				        	aMan = new AllegatoManager(currentActiveConnection, logger);
			    	    	aBean = new AllegatoBean();
			    	    	aBean.setIdGara(Integer.parseInt(idGara));
			    	    	aBean.setTipoDoc(PubblicazioneBean.TipoDocumento.RETTIFICA.getCodice());
			    	    	aBeans = aMan.load(aBean);
			    	    	if(aBeans.size()>0){
			    	    		aBean = (AllegatoBean)aBeans.get(0);
			    	    		//gm nuovo codice per note rettifica
			    	    		aBean.setNote(noteRettifica);
			    	    		listaAllegati.add(aBean);
			    	    	}
				    	   			    	    
			    	    	gra.pubblicaRettifica(currentUser.getLogin(), gara, pubbNuova, listaAllegati); 
			    	    	
			    	    	 if(dataPubblicazioneStr!=null) {
			    	    	
			    	    	String dataPubbDefinitiva = null;
			    	    	if(dataPubblicazioneStr!=null)
			    	    		dataPubbDefinitiva = PageHelper.getFormattedDBDate(dataPubblicazioneStr);
			    	    	
			    	    	String dataTermineDefinitiva = null;
			    	    	if(dataTermineStr!=null)
			    	    		dataTermineDefinitiva = PageHelper.getFormattedDBDate(dataTermineStr);
			    	    	
			    	    	String dataScadInvitoDefinitiva = null;
			    	    	if(dataScadInvitoStr!=null)
			    	    		dataTermineDefinitiva = PageHelper.getFormattedDBDate(dataScadInvitoStr);
			    	    	
			    	    	String dataLettInvitoDef = null;
			    	    	if(dataLettInvitoStr!=null)
			    	    		dataLettInvitoDef = PageHelper.getFormattedDBDate(dataLettInvitoStr);
			    	    	
			    	    	lottoManager.updateDatePubblicazione(dataPubbDefinitiva, 
			    	    			dataTermineDefinitiva, 
			    	    			dataScadInvitoDefinitiva, 
			    	    			dataLettInvitoDef, oraScadStr, l.getId_Gara());
			    	    	 }
			    	    	
			    	    	//commit di tutte le operazioni
			        	    currentActiveConnection.commit();
						    sendMessage(request, response, SIMOG_GARA_019.replace("$1", "rettifica").replace("$2", "numero ["+String.valueOf(idGara)+"]"), 
								ParametriServlet.SRV_GESTIONE_SCHEDE+"?"+ParametriServlet.FIELD_NAME_ID_GARA+"="+String.valueOf(idGara)+"&"+ParametriServlet.FROM_GARE+"="+ Costanti.FLAG_VALORE_SI+"&"+ParametriServlet.FROM_RICERCA+"="+ Costanti.FLAG_VALORE_NO);
				    	}
				    	//se ho avuto errori nella validazione dei bean, invio gli errori all'url
				    	else{
				    		logger.error ( "rettifica della pubblicazione fallita" );
							sendValidations(request, response, gra.getEccezioni(), url);
				    	}
		    	    }
		    	    else if(ParametriServlet.ACTION_SALVA_RETTIFICA_AVVISO.equals(action)){
		    	  	
		    	    	PubblicazioneBean pubbNuova = new PubblicazioneBean();			    		
				    	PubblicazioneAction pubAction = new PubblicazioneAction(currentActiveConnection, logger);
				    	pubbNuova = pubAction.getBean(request);
				    	//gm mantengo memoria del flag beni culturali originale
				    	pubbNuova.setFlag_benicult(pubblicazione.getFlag_benicult());
		    	        //PubblicazioneValidator pv = new PubblicazioneValidator(currentActiveConnection, logger);
                      
		    	    	gra.validaPubblicazioneRettifica(pubbNuova, ParametriServlet.PUBBLICAZIONE_RETTIFICA_AVVISO_AGG);
		    	        //gm nuovo codice estensione pubblicazione bandi
		    	        gra.validaAllegatiRettifica(allRettifica, noteRettifica);
		    	        //gm inserimento obbligatorio del profilo committente "SI"
		    	        //pubbNuova.setProfiloCommitente(Costanti.FLAG_VALORE_SI);
		    	        pubbNuova.setTipoOperazione(PubblicazioneBean.TipoOperazione.RETTIFICA.getCodice());
		    	        request.setAttribute(ParametriServlet.PUBBLICAZIONE, pubbNuova);
		    	  
		    	      //se non ho avuto errori nella validazione dei bean
			    	    if(gra.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() == 0){
				    		//effettuo la rettifica
			    	    	ArrayList <AllegatoBean> listaAllegati = new ArrayList<AllegatoBean>();
				        	aMan = new AllegatoManager(currentActiveConnection, logger);
			    	    	aBean = new AllegatoBean();
			    	    	aBean.setIdGara(Integer.parseInt(idGara));
			    	    	aBean.setTipoDoc(PubblicazioneBean.TipoDocumento.RETTIFICAAVVISO.getCodice());
			    	    	aBeans = aMan.load(aBean);
			    	    	if(aBeans.size()>0){
			    	    		aBean = (AllegatoBean)aBeans.get(0);
			    	    		//gm nuovo codice per note rettifica
			    	    		aBean.setNote(noteRettifica);
			    	    		listaAllegati.add(aBean);
			    	    	}
				    	   	
				    	    long idAgg = Long.parseLong(idAggiudicazione);
				    	    Timestamp dataAgg = PageHelper.parseTime(dataInizioAgg);
				    	    	
		    	   	        gra.pubblicaRettificaAvviso(currentUser.getLogin(), Long.parseLong(idGara), pubbNuova, idAgg, dataAgg, listaAllegati); 
	    	                
                            AggiudicazioniManager am = new AggiudicazioniManager(currentActiveConnection, logger);
                            AggiudicazioneBean aggbea = am.getAggiudicazioni(Long.parseLong(idAggiudicazione), PageHelper.parseTime(dataInizioAgg), false);
                            InfoComuniManager icm = new InfoComuniManager(currentActiveConnection, logger);
                            InfoComuniBean icb = icm.getInfoComuniByCig(aggbea.getCig());

		    	   	        //commit di tutte le operazioni
		       	            currentActiveConnection.commit();

		       	            sendMessage(request, response, SIMOG_GARA_019.replace("$1","rettifica avviso").replace("$2", "numero ["+String.valueOf(idGara)+"]"),                            
					        	ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + icb.getIdLotto() 
					        );
			    	    }
			    	    else{
				    		logger.error ( "rettifica della pubblicazione fallita" );
							sendValidations(request, response, gra.getEccezioni(), url);
				    	}
		    	    }
		    	    //gm altrimenti non possono essere effettuate rettifiche
			    	else{
			    		sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE);
						return;  
			    	}
			    	/********************* END Gestione Actions RUP*************************/

		    	}
		    	else if (currentUser.isAmministratore()){
		    		
			    	/********************* BEGIN Gestione Actions ADMIN*************************/
		    	    if(ParametriServlet.ACTION_CONFERMA_RETTIFICA_ADMIN.equals(action)){
		    	       
                       if( SimogFlags.is3030_RFWEBGL02Active() ){
                          AVCPassAction avpa = new AVCPassAction(currentActiveConnection, logger, configuration); 
                          if( avpa.isAVCPass(gara, null, AVCPassFunzioneEnum.WEB_CONFERMA_RETTIFICA.getCodice()) ){
                             String targetPage = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara + "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;                               
                             AllValidationBeans msgs = new AllValidationBeans();
                             msgs.addValidationErr(SIMOG_AVCPASS_001);
                             sendValidations(request, response, msgs, targetPage);                             
                             return;
                          }
                       } 		    	       
		    	       
	    	    	    gra.confermaRettifica(currentUser.getLogin(), idGara, pubblicazione); 
	    	    	    //commit di tutte le operazioni
		        	    currentActiveConnection.commit();
	    	    	    sendMessage(request, response, SIMOG_GARA_019.replace("$1","rettifica").replace("$2", "numero ["+String.valueOf(idGara)+"]"), 
							ParametriServlet.SRV_VISUALIZZA_DETTAGLIO+"?"+ParametriServlet.SESSION_ID_GARA+"&"+ParametriServlet.FROM_GARE+"="+ Costanti.FLAG_VALORE_SI);
	    	    	    //sendMessage(request, response, SIMOG_GARA_019.replace("$1", "numero ["+String.valueOf(idGara)+"]"), 
			    		//    ParametriServlet.SRV_GESTIONE_GARE_EXT);				    	
	    	        }
		    	    //gm altrimenti non possono essere effettuate rettifiche
			    	else{
			    		sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE);
						return;  
			    	}
			    	/********************* END Gestione Actions ADMIN*************************/

		    	}
		    	//gm else di currentUser
			    else {
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE);
					return;
				}
	    	/********************* END Gestione Actions *************************/		    
	    	
		    }
		    //gm else di checkSession
		    else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE);
				return;
			}
		}		
	    catch ( ActionException ae ) {
		    ae.printStackTrace();
	    	rollback(currentActiveConnection);					
		    sendError( request, response, ae.getMessage(), JSP_ERRORE);
		return;
	    }
	    catch ( SimogException se ) {
		    se.printStackTrace();
		    rollback(currentActiveConnection);					
		    sendError( request, response, se.getMessage(), JSP_ERRORE);
		return;
	    } 
	    catch ( Exception e ) {
		    e.printStackTrace();
		    rollback(currentActiveConnection);
		    logger.fatal ( e.getMessage());
		    sendError(request, response, e.getMessage(), JSP_ERRORE);
		return;
	    } 
	    finally {		
		    closeConnection(request.getSession().getId(),getClass().getName());
	    }		
	}
}