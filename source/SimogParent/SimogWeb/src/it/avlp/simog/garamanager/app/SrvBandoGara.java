package it.avlp.simog.garamanager.app;

import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avlp.simog.actions.GaraLottoAction;
import it.avlp.simog.actions.PubblicazioneAction;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.AllegatoBean;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.MotivazioniBean;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.RequisitoGara;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.common.action.CupLottoAggAction;
import it.avlp.simog.common.actions.BandoGaraAction;
import it.avlp.simog.common.contributo.GestioneContributoWrapperBeanClient;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriCup;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletLotto;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.CONTRAENTE_REGIONE;
import it.avlp.simog.db.generated.DEROGA_QUALIFICAZIONE_SA;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.AllegatoManager;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.RequisitiGLManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.validatore.LottoValidator;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SrvBandoGara extends ServletBase implements ParametriServlet {
	
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
		    // PP esagerata currentActiveConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);	

		    if ( checkSession(request) ) {
		    	if ( currentUser.isRSSAorRUP()){ 
				
		    	    /********************* BEGIN Gestione Actions *************************/
		            //gm caricamento di tutte le info necessarie
		    		BandoGaraAction bga = new BandoGaraAction(currentActiveConnection,logger, configuration);
		    		
		    		final String action = request.getParameter("toDo");
		    		//String action = request.getParameter(PSBD.ACTION_TYPE);
				    MotivazioniBean motiviCanc = new MotivazioniBean();
					motiviCanc.loadAll(currentActiveConnection, logger, false);
				    
					String idGara = request.getParameter(SESSION_ID_GARA);
					
					//carico i dati dell'aggiudicazione necessari
	    	    	String idAggiudicazione = request.getParameter(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE);
	    	    	String dataInizioAggiudicazione = request.getParameter(PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE);
	    	    	
			    	TableBean dettagliGara = null;
			    	GaraManager garaManager = new GaraManager(currentActiveConnection, logger);	

					Gara garaCheck = null; //garaManager.getGara(Long.parseLong(idGara));
					garaCheck = garaManager.getGara(Long.valueOf(idGara), currentUser.getUffici());
					if(garaCheck == null){
						sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
						return;
					}

			    	dettagliGara = garaManager.getDettagliGaraByIdGaraRSSA( idGara, currentUser.getUffici() );
			    	
			    	if(SimogFlags.is3028_RFWEBGL00Active()){
			    	   // devo impostare la scelta contraente equivalente
			    	  dettagliGara.addColumn(CONTRAENTE_REGIONE.ID_EQUIVALENTE);
			    	   LottoManager lm = new LottoManager(currentActiveConnection, logger);
			         for(int i = 0; i < dettagliGara.getTableSize(); i++){
			            if(!"".equals(dettagliGara.getRow(i).getNulledField(LOTTO.CIG))){
			               String scelta = lm.getSceltaContraenteAVCP(null, Long.valueOf(dettagliGara.getRow(i).getNulledField(LOTTO.ID_SCELTA_CONTRAENTE)));
			               
			               dettagliGara.getRow(i).addFieldValue(CONTRAENTE_REGIONE.ID_EQUIVALENTE, scelta);
			            }
			            else
			               dettagliGara.getRow(i).addFieldValue(CONTRAENTE_REGIONE.ID_EQUIVALENTE, "");
			         }
			    	}
					String tipoPubblicazione = request.getParameter(ParametriServlet.TIPO_PUBBLICAZIONE);
					request.setAttribute(ParametriServlet.TIPO_PUBBLICAZIONE, tipoPubblicazione);
//					String osserv = null;
//					if(dettagliGara != null && dettagliGara.size() > 0){
//						TableBeanRow currentRow = dettagliGara.getRow(0);
//						osserv = currentRow.getNulledField(GARA.ID_OSSERVATORIO);
//					}
/** PP solo info					
					DelegaDatiSimogAction dasAction = new DelegaDatiSimogAction(currentActiveConnection, logger);
					String res = dasAction.getDelegaCIGMessage(osserv, PageHelper.getCurrentUtilDate(), currentUser);
					if(res != null && currentUser.isRSSAorRUP()){
						sendError(request,response, res, SRV_VISUALIZZA_DETTAGLIO + "?" + ParametriServlet.FROM_GARE+"=" + Costanti.FLAG_VALORE_SI );
						
						return;
					}
**/				
					
					if( SimogFlags.is3031_RFWEBGL02Active() ){
					   CupLottoAggAction claAction = new CupLottoAggAction(currentActiveConnection, logger);
					   List<CupLottoAggExt> elencoCupGara = claAction.getElencoCupGara(Long.parseLong(idGara));
					/**   
			         if (elencoCupGara != null){
			            // imposto solo i dati che mi servono
			            Lotto lt = new Lotto();
			            lt.setId_Gara(Long.valueOf(idGara));
			            lt.setElencoCup(elencoCupGara);
			            bga.validaCodiciCUPPerf(lt);
			            // rileggo i dati
			            elencoCupGara = claAction.getElencoCupGara(Long.parseLong(idGara));
			         }*/

					   request.setAttribute(ParametriCup.PARAM_ELENCO_CUP_GARA, elencoCupGara);
					}
					
			    	//gm reinvio dei parametri per la visualizzazione della gara
					request.setAttribute(SESSION_ID_GARA, idGara);
			    	request.setAttribute(TABLEBEAN, dettagliGara);			    	
			    	request.setAttribute(ParametriServlet.FROM_GARE, request.getParameter(ParametriServlet.FROM_GARE));							    	
			    	request.setAttribute(ParametriServletLotto.MOTIVAZIONI_LIST, motiviCanc.loadMotivazioni());
			    	
					// PP organi costituzionali
			    	request.setAttribute(ParametriServlet.IS_ORGANO, garaCheck.isOrganoCost() ? Costanti.FLAG_VALORE_SI : Costanti.FLAG_VALORE_NO);
			    	
			    	String url = ParametriServlet.JSP_PUBBLICA_BANDO_GARA;
			    	String allBando = request.getParameter(ParametriServlet.ALLEGATO1);
			    	String allDisci = request.getParameter(ParametriServlet.ALLEGATO2);
			    	String allInvito = request.getParameter(ParametriServlet.ALLEGATO3);
			    	String allAvviso = request.getParameter(ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE);
		        	boolean isPubblicazione = false;
		        	boolean isInvitabile = false;
		        	boolean isBandoObbligatorio = false;
		        	
		        	////MEV 43345 3.04.10
		        	String currentDate = PageHelper.getCurrentDate();
                    AccessiDB dbManager = null;
            		 dbManager = new AccessiDB(currentActiveConnection, logger);
                    request.setAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_BEAN, dbManager.executeSelectDerogaQualificazioneSA(DEROGA_QUALIFICAZIONE_SA.TABLE_NAME, DEROGA_QUALIFICAZIONE_SA.DATA_FINE_VALIDITA,DEROGA_QUALIFICAZIONE_SA.DATA_INIZIO_VALIDITA, DEROGA_QUALIFICAZIONE_SA.DESCRIZIONE, PageHelper.getCurrentDate(), false));
                    List<String> listaDerogaQualificazioneSA= new ArrayList<String>();
                    request.setAttribute(ParametriServlet.DEROGA_QUALIFICAZIONE_SA_BEAN_SELECTED, listaDerogaQualificazioneSA);
                    
					String mostraCampoDerogaQualificazioneSA = "false";
					//se la MEV sul controllo della qualificazione sa è attiva
					if (currentDate.compareTo(SimogProperties.getInstance().getAttivazioneMevQualificazioneSA()) >= 0) {
						LottoManager lm = new LottoManager(currentActiveConnection, logger);
						Map <String,Lotto> elencoLotti = bga.caricaElencoLotti(dettagliGara);
						//se il lotto è uno
						if (elencoLotti.size() == 1) {
							Iterator<Entry<String, Lotto>> iterator = elencoLotti.entrySet().iterator();
			    			Entry<String, Lotto> actualValue = iterator.next();
			    			Lotto lottoQualificazioneSA = lm.getLotto(actualValue.getValue().getId_Lotto());
							//Se la data creazione del lotto è minore del 1 Luglio allora mostro la sezione per la deroga della qualificazione SA
			    			if (lottoQualificazioneSA.getDataCreazione().compareTo(SimogProperties.getInstance().getAttivazioneMevQualificazioneSA()) < 0 ) {
								mostraCampoDerogaQualificazioneSA = "true";
							}
						}
						
		    			
					}
					request.setAttribute(ParametriServlet.MOSTRA_DEROGA_QUALIFICAZIONE_SA, mostraCampoDerogaQualificazioneSA);
					// FINE MEV 43345 3.04.10
					
		        	GestioneContributoWrapperBeanClient gcwbc = new GestioneContributoWrapperBeanClient(configuration.getContributoUrl(), currentActiveConnection, logger);
			    	//gm action caricamento della gara per pubblicare il bando
		    	    if ( ParametriServlet.ACTION_CARICA_GARA.equals(action) )	{
			    	//if ( PSBD.ACTION_LOAD.equals(action) )	{
	
			    	    	// cerco eventuali allegati presenti per la gara
			    	    	AllegatoManager aMan = new AllegatoManager(currentActiveConnection, logger);
			    	    	AllegatoBean aBean = new AllegatoBean();
			    	    	aBean.setIdGara(Integer.parseInt(idGara));
			    	    	aBean.setTipoDoc(PubblicazioneBean.TipoDocumento.BANDO.getCodice());
			    	    	List aBeans = aMan.load(aBean);
			    	    	if(aBeans.size()>0){
			    	    		aBean = (AllegatoBean)aBeans.get(0);
			    	    		allBando = String.valueOf(aBean.getIdAllegato()).trim();
			    	    	}
			    	    		
			    	    	aBean = new AllegatoBean();
			    	    	aBean.setIdGara(Integer.parseInt(idGara));
			    	    	aBean.setTipoDoc(PubblicazioneBean.TipoDocumento.DISCIPLINARE.getCodice());
			    	    	aBeans = aMan.load(aBean);
			    	    	if(aBeans.size()>0){
			    	    		aBean = (AllegatoBean)aBeans.get(0);
			    	    		allDisci = String.valueOf(aBean.getIdAllegato()).trim();
			    	    	}
			    	    	
			    	    	aBean = new AllegatoBean();
			    	    	aBean.setIdGara(Integer.parseInt(idGara));
			    	    	aBean.setTipoDoc(PubblicazioneBean.TipoDocumento.LETTERA_INVITO.getCodice());
			    	    	aBeans = aMan.load(aBean);
			    	    	if(aBeans.size()>0){
			    	    		aBean = (AllegatoBean)aBeans.get(0);
			    	    		allInvito = String.valueOf(aBean.getIdAllegato()).trim();
			    	    	}
					    	request.setAttribute(ParametriServlet.ALLEGATO1, allBando);
					    	request.setAttribute(ParametriServlet.ALLEGATO2, allDisci);
					    	request.setAttribute(ParametriServlet.ALLEGATO3, allInvito);
					    	
					    	if(allBando != null)
					    		request.setAttribute(ParametriServlet.ALLEGATO1DESC, ParametriServlet.PRESENTE);
					    	else
					    		request.setAttribute(ParametriServlet.ALLEGATO1DESC, "");
					    	
					    	if(allDisci != null)
					    		request.setAttribute(ParametriServlet.ALLEGATO2DESC, ParametriServlet.PRESENTE);
					    	else
					    		request.setAttribute(ParametriServlet.ALLEGATO2DESC, "");
					    	if(allInvito != null)
					    		request.setAttribute(ParametriServlet.ALLEGATO3DESC, ParametriServlet.PRESENTE);
					    	else
					    		request.setAttribute(ParametriServlet.ALLEGATO3DESC, "");
					    	
					   // 	request.setAttribute(ParametriServlet.TIPO_PUBBLICAZIONE, tipoPubblicazione);
	
					    	//is3031_ESCL_AVCPASS()  e gara non ancora pubblicata
					    	if(SimogFlags.is3031_ESCL_AVCPASS() 
					    	     && configuration.getDataEsclAvcpass().compareTo(garaCheck.getData_creazione()) <= 0
					    	     && garaCheck.getDATA_PERFEZIONAMENTO_BANDO()==null){
					    	   String messaggio="";
	
	                     boolean hasRequisiti = false;
	                     boolean hasEsclusione = isEsclusa(dettagliGara);
	                     
	                     RequisitiGLManager rqm = new RequisitiGLManager(currentActiveConnection, logger);
	                     List<RequisitoGara> lista = rqm.getRequisitoGaraList(Long.valueOf(idGara));
	                     hasRequisiti = lista.size() > 0;                        
	                  // analizzo la situazione per costruire il popup di avviso incongruenze esclusione AVCPass
				    	   
				    	   // La gara per settore speciale o almeno un lotto escluso, ma l'utente ha 
				    	   // inserito i requisiti o non ha indicato la gara come esclusa AVCPass
                 //3.04.7.1 commento tutti i controlli su requisiti e avcpass
//				    	   if(hasRequisiti){
//                      if(garaCheck.getTIPO_SCHEDA_GARA().equals(Costanti.TIPO_ENTE_SPECIALE) 
// 				    	       && Costanti.FLAG_VALORE_NO.equals(garaCheck.getESCLUSO_AVCPASS())){
// 				    	      messaggio += "- per la gara stato specificato 'Settore Speciale', ma non stata indicata come esclusa dal sistema AVCPass";
// 				    	   }
//                      
//                      if(hasEsclusione 
//                            && Costanti.FLAG_VALORE_NO.equals(garaCheck.getESCLUSO_AVCPASS())){
//                         messaggio += "\\n- per almeno un lotto indicato un articolo di esclusione, ma la gara non � stata indicata come esclusa dal sistema AVCPass";
//                      }
//                      // se messaggio valorizzato e ci sono i requisiti inseriti avviso
//                      if(!"".equals(messaggio) && hasRequisiti){
//                         messaggio += "\\n- sono presenti i requisiti per una gara che dovrebbe essere esclusa";
//                      }
//				    	   
//                      // L'utente ha indicato la gara come esclusa, ma questa non � per settori 
// 				    	   // speciali e non ha almeno un lotto con articolo di esclusione, e l'utente ha 
// 				    	   // inserito i requisiti
//                      if(!garaCheck.getTIPO_SCHEDA_GARA().equals(Costanti.TIPO_ENTE_SPECIALE) 
//                            && Costanti.FLAG_VALORE_SI.equals(garaCheck.getESCLUSO_AVCPASS())){
//                           messaggio += "\\n- per la gara non stato specificato 'Settore Speciale', ma stata indicata come esclusa dal sistema AVCPass";
//                        }
//                      if(!garaCheck.getTIPO_SCHEDA_GARA().equals(Costanti.TIPO_ENTE_SPECIALE) 
//                            && !hasEsclusione 
//                            && Costanti.FLAG_VALORE_SI.equals(garaCheck.getESCLUSO_AVCPASS())){
//                         messaggio += "\\n- per nessun lotto stato indicato un articolo di esclusione, ma la gara stata indicata come esclusa dal sistema AVCPass";
//                      }
//                   }
//				    	   if(!"".equals(messaggio))
//				    	      messaggio = "ATTENZIONE: rilevate incongruenze, verificarle prima di proseguire\\n\\n" + messaggio;
//				    	   request.setAttribute(ParametriServlet.MESSAGGIO, messaggio);
					    	}
					    	
					    	forward(url, request, response);
		    	    }
		    	    else if (ParametriServlet.ACTION_CARICA_AVVISO.equals(action)){
		    	       
                        if( SimogFlags.is3030_RFWEBGL02Active() ){
                            String targetPage = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara
                                              + "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;   
      
                            AVCPassAction avpa = new AVCPassAction(currentActiveConnection, logger, configuration); 
                            if( avpa.isAVCPass(garaCheck, null, AVCPassFunzioneEnum.WEB_PUBBLICA_AVVISO.getCodice()) ){
                               AllValidationBeans msgs = new AllValidationBeans();
                               msgs.addValidationErr(SIMOG_AVCPASS_001);
                               sendValidations(request, response, msgs, targetPage);  
                               return;
                            }
                        }   		    	       
		    	
		    	    	// cerco eventuali allegati presenti per la gara
		    	    	AllegatoManager aMan = new AllegatoManager(currentActiveConnection, logger);
		    	    	AllegatoBean aBean = new AllegatoBean();
		    	    	aBean.setIdGara(Integer.parseInt(idGara));
		    	    	aBean.setTipoDoc(PubblicazioneBean.TipoDocumento.AVVISO.getCodice());
		    	    	List aBeans = aMan.load(aBean);
		    	    	if(aBeans.size()>0){
		    	    		aBean = (AllegatoBean)aBeans.get(0);
		    	    		allAvviso = String.valueOf(aBean.getIdAllegato()).trim();
		    	    	}
		    	    	request.setAttribute(ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE, allAvviso);
		    	    	if(allAvviso != null)
				    		request.setAttribute(ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE_DESC, ParametriServlet.PRESENTE);
				    	else
				    		request.setAttribute(ParametriServlet.ALLEGATO_AVVISO_AGGIUDICAZIONE_DESC, "");	
				  
		    //	    	request.setAttribute(ParametriServlet.TIPO_PUBBLICAZIONE, tipoPubblicazione);
		    	    	request.setAttribute(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE, idAggiudicazione);
		    	    	request.setAttribute(PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE, dataInizioAggiudicazione);
		    	    	
		    	    	forward(url, request, response);
		    	    }
		    	
		        	//gm se la action e la validazione della pubblicazione bando gara
		    	    else if (ParametriServlet.ACTION_SALVA_BANDO_GARA.equals(action))
		    	    {
	               PubblicazioneAction pubAction = new PubblicazioneAction(currentActiveConnection, logger);
		    	    	//gm recupero dei parametri da jsp
		    	    	String [] lottiDaCancellare = request.getParameterValues(ParametriServletLotto.FIELD_NAME_LOTTI_CANCELLARE);
		    	    	String id_motivazione = request.getParameter(ParametriServletLotto.FIELD_NAME_MOTIVAZIONE); 
				    	String note_canc = request.getParameter(ParametriServletLotto.FIELD_NAME_NOTE);
		    	        String dataPubblicazione = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_DATA_PUBBLICAZIONE));
				    	String dataScadenza = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_DATA_SCADENZA));
				    	String oraScadenza = request.getParameter(ParametriServlet.FIELD_NAME_ORA_SCADENZA);
				    	// MARRA MEV 34470 3.04.8
                        String linkAffidDiretto = request.getParameter(ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO);
				    	// FINE MEV 3.04.8
                        
				    	// UN is3030_RFWEBGL00Active - Procedura ristretta
				    	String dataScadenzaRichiestaInvito = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO));
				    	String dataLetteraInvito = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_DATA_LETTERA_INVITO));
				    	
			    		// PP 3.02.1.6 // se arriva null e una adesione senza dialogo
			    		if(SimogFlags.is30216Active() == true && dataScadenza == null && dataPubblicazione != null
			    		     && (Costanti.MODOREAL_ADESIONE_NOCOMPET==garaCheck.getID_MODO_REAL() || Costanti.MODOREAL_CONCESSIONE_NOCOMPET==garaCheck.getID_MODO_REAL())) //3.04.8 34190 fix
			    			dataScadenza = dataPubblicazione; //ageHelper.formatDate(PageHelper.getIncreasedDate(dataPubblicazione, 1).getTime());

				    	//gm reinvio dei parametri per la loro modifica e/o conferma
				    	request.setAttribute(ParametriServletLotto.FIELD_NAME_LOTTI_CANCELLARE, lottiDaCancellare);
				    	request.setAttribute(ParametriServletLotto.FIELD_NAME_MOTIVAZIONE, id_motivazione);
				    	request.setAttribute(ParametriServletLotto.FIELD_NAME_NOTE, note_canc);
				    	
				    	// MARRA MEV 34470 3.04.8
				    	request.setAttribute(ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO, linkAffidDiretto);				    	
				    	// FINE MEV 3.04.8
				    	
				    	request.setAttribute(ParametriServlet.ALLEGATO1, allBando);
				    	request.setAttribute(ParametriServlet.ALLEGATO2, allDisci);
				    	request.setAttribute(ParametriServlet.ALLEGATO3, allInvito);

				    	//gm lottiDaPerfezionare ottenuto dalla differenza tra elencoLotti e lottiDaCancellare
		    	    	Map <String,Lotto> lottiDaPerfezionare = new HashMap<String,Lotto>();
		    	    	Map <String,Lotto> elencoLotti = bga.caricaElencoLotti(dettagliGara);
		    	    	lottiDaPerfezionare = bga.caricaElencoLottiDaPerfezionare(elencoLotti,lottiDaCancellare);
		    	    	//gm elencoLottiDaCancellare ottenuto da lottiDaCancellare
		    	    	Map <String,Lotto> elencoLottiDaCancellare = new HashMap<String,Lotto>();
		    	    	elencoLottiDaCancellare = bga.caricaElencoLottiDaCancellare(elencoLotti,lottiDaCancellare);
		    	    	//gm elencoLottiDaPerfezionareOPerfezionati ottenuto da lotti da perfezionare pi� lotti gia perfezionati
		    	    	Map <String,Lotto> lottiDaPerfezionareOPerfezionati = new HashMap<String,Lotto>();
		    	    	lottiDaPerfezionareOPerfezionati = bga.caricaElencoLottiDaPerfezionareOPerfezionati(elencoLotti,lottiDaCancellare);
				    	
				    	PubblicazioneBean pubblicazione = new PubblicazioneBean();
				    		
				    	BigDecimal totLotti = gcwbc.getImportoGara(lottiDaPerfezionareOPerfezionati.values(), false, false);
                        
				    	//Ticket 31061
//					   	String currentDate = PageHelper.getCurrentDate();

				    	if (currentDate.compareTo(SimogProperties.getInstance().getDataAttivazionePPP()) >= 0 ) {				    		
				    		for (Lotto lottoDaPerfezionare: lottiDaPerfezionare.values()) {
				    			LottoValidator lottoValidator = new LottoValidator(currentActiveConnection, logger);
				    			if (lottoValidator.prevalidaPPP(garaManager.getGara(Long.parseLong(idGara)), lottoDaPerfezionare)) {
				    				lottoValidator.getEccezioni().addValidationErr("ERROR - La gara appartiene afferisce al PPP e il/i lotto/i hanno il campo CUP obbligatorio.");
				    			}
				    			
				    		}
				    	}
				    	
				    	//creazione del bean per il perfezionamento dei lotti
				    	Lotto lotto = new Lotto();
			    		lotto.setData_Pubblicazione(dataPubblicazione);
			    		lotto.setDataScadenzaPagamenti(dataScadenza);
			    		
			    		if( SimogFlags.is3030_RFWEBGL00Active() ){
			    		   lotto.setDataScadenzaRichiestaInvito(dataScadenzaRichiestaInvito);
			    		   lotto.setDataLetteraInvito(dataLetteraInvito);
			    		}
			    		
                  // mi serve id Gara
                  lotto.setId_Gara(Long.parseLong(idGara));

			    		if(SimogFlags.is3025_RFWEBGL02Active())
			    		   lotto.setORA_SCADENZA(oraScadenza);
			    		
			    		int tipoProcedura = bga.getTipologiaProcedura(lottiDaPerfezionare,garaCheck.getID_SVOLGIMENTO()); 
			    		
			    		boolean isProcCompleta = false;
			    		boolean isProcRistretta = false;
			    		   isProcCompleta = bga.isProceduraCompleta(lottiDaPerfezionare, lotto,garaCheck.getID_SVOLGIMENTO());
	                        
			    		   isProcRistretta = bga.isProceduraRistretta(lottiDaPerfezionare, garaCheck.getID_SVOLGIMENTO())
	                                      || bga.isProceduraNegoziataRistretta(lottiDaPerfezionare, lotto,garaCheck.getID_SVOLGIMENTO())
	                                      || isProcCompleta;    
	                        
			    		   bga.validaPubblicabilita(lottiDaPerfezionare,garaCheck.getID_SVOLGIMENTO());
			    		
			    		//se esistono lotti da perfezionare, validazione delle date di perfezionamento, 
			    		bga.validaPerfezionamentoLotti(lotto, !lottiDaPerfezionare.isEmpty(), tipoProcedura);
			    		//MEV 43345 3.04.10 solo per gare monolotto, controllo sulla qualificazione
			    		pubblicazione = pubAction.getBean(request);
			    		//se la MEV sul controllo della qualificazione sa è attiva
						if (currentDate.compareTo(SimogProperties.getInstance().getAttivazioneMevQualificazioneSA()) >= 0) {
							if (lottiDaPerfezionareOPerfezionati.size() == 1) {
				    			String dataPubblicazioneQualificazione = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_DATA_PUBBLICAZIONE));
				    			//se la data di pubblicazione inserita è maggiore o uguale al 1 Luglio faccio il controllo sulla qualificazione
				    			if (dataPubblicazioneQualificazione!= null && dataPubblicazioneQualificazione.compareTo(SimogProperties.getInstance().getAttivazioneMevQualificazioneSA()) >= 0) {
					    			LottoManager lm = new LottoManager(currentActiveConnection, logger);
					    			Iterator<Entry<String, Lotto>> iterator = lottiDaPerfezionareOPerfezionati.entrySet().iterator();
					    			Entry<String, Lotto> actualValue = iterator.next();
					    			Lotto lottoQualificazioneSA = lm.getLotto(actualValue.getValue().getId_Lotto());
					    			if (lottoQualificazioneSA.getDataCreazione().compareTo(SimogProperties.getInstance().getAttivazioneMevQualificazioneSA()) < 0 ) {
					    				pubblicazione = bga.validaQualificazioneSAPerfezionamento(pubblicazione, lottoQualificazioneSA, garaCheck, "");//MEV 43345 3.04.10
					    			}
								}
				    			
				    		 }
						}
			    		
			    		
			    		
				    	//creazione del bean per la cancellazione dei lotti
				    	lotto = new Lotto();
			    	    lotto.setId_motivazione(id_motivazione);
			    	    lotto.setNoteCancellazione(note_canc);			
				    	//se esistono lotti da cancellare, validazione dei motivi e note di cancellazione
			    		bga.validaCancellazioneLotti(lotto, !elencoLottiDaCancellare.isEmpty());
  	
//			    		pubblicazione = new PubblicazioneBean();
//		    	      pubblicazione = pubAction.getBean(request);
                     
				    	// gm nuovo codice pubblicazione bando 3.0
				      	isPubblicazione = bga.isPubblicazione(lottiDaPerfezionareOPerfezionati);
				      	isBandoObbligatorio = bga.isBandoObbligatorio(lottiDaPerfezionareOPerfezionati);
                        isInvitabile = bga.isInvitabile(lottiDaPerfezionareOPerfezionati);
		    	        boolean isInvito = (Costanti.FLAG_VALORE_SI.equals(pubblicazione.getFlag_benicult()) && isInvitabile);
		    	        
		    	        // PP organi costituzionali, sempre e solo perefezionamento
		    	        if(garaManager.isOrganoCost(garaCheck.getCF_AMMINISTRAZIONE(), garaCheck.getData_creazione())){
		    	        	isPubblicazione = false;
		    	        	isBandoObbligatorio = false;
		    	        	isInvitabile = false;
		    	        }
		    	        
		    	        //gm nuovo codice simog 3.04
		    	        bga.validaNumeroLotti(garaCheck.getNumeroLotti(), lottiDaPerfezionareOPerfezionati.size());
			            
                     boolean hasRequisiti = false;
                     
                     if(SimogFlags.is3025_REQUISITIActive()){
                        RequisitiGLManager rqm = new RequisitiGLManager(currentActiveConnection, logger);
                        List<RequisitoGara> lista = rqm.getRequisitoGaraList(Long.valueOf(idGara));
                        hasRequisiti = lista.size() > 0;
                     }

		    	        bga.validaPubblicazione(pubblicazione, isPubblicazione, isInvito, totLotti, hasRequisiti, garaCheck, isProcRistretta);
		    	        
			            bga.validaAllegati(allBando,allDisci, allInvito, isPubblicazione, isInvito, isBandoObbligatorio, isProcCompleta);
			            
			            
			            
				   		//tengo memoria dei dati di pubblicazione solo se e una pubblicazione o invito,
			            //per il perfezionamento i dati non devono essere inseriti
			            if (isPubblicazione || isInvito) 
    			            request.setAttribute(ParametriServlet.PUBBLICAZIONE, pubblicazione);   		    	
			    	    // gm fine nuovo codice pubblicazione bando 3.0
				    	
				    	//se non ho avuto errori nella validazione dei bean
			    	    if(bga.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() == 0){
				    		//effettuo la pubblicazione
			    	        GaraLottoAction gla = new GaraLottoAction(currentActiveConnection,logger, configuration);
				    	    Gara gara = gla.loadGaraFromDBSenzaValidazione(request);
				    	    
				        	ArrayList <AllegatoBean> listaAllegati = new ArrayList<AllegatoBean>();
				        	AllegatoManager aMan = new AllegatoManager(currentActiveConnection, logger);
			    	    	AllegatoBean aBean = new AllegatoBean();
			    	    	aBean.setIdGara(Integer.parseInt(idGara));
			    	    	aBean.setTipoDoc(PubblicazioneBean.TipoDocumento.BANDO.getCodice());
			    	    	List aBeans = aMan.load(aBean);
			    	    	if(aBeans.size()>0){
			    	    		aBean = (AllegatoBean)aBeans.get(0);
			    	    		listaAllegati.add(aBean);
			    	    	}
			    	    	aBean = new AllegatoBean();
			    	    	aBean.setIdGara(Integer.parseInt(idGara));
			    	    	aBean.setTipoDoc(PubblicazioneBean.TipoDocumento.DISCIPLINARE.getCodice());
			    	    	aBeans = aMan.load(aBean);
			    	    	if(aBeans.size()>0){
			    	    		aBean = (AllegatoBean)aBeans.get(0);
			    	    		listaAllegati.add(aBean);
			    	    	}
			    	    	aBean = new AllegatoBean();
			    	    	aBean.setIdGara(Integer.parseInt(idGara));
			    	    	aBean.setTipoDoc(PubblicazioneBean.TipoDocumento.LETTERA_INVITO.getCodice());
			    	    	aBeans = aMan.load(aBean);
			    	    	if(aBeans.size()>0){
			    	    		aBean = (AllegatoBean)aBeans.get(0);
			    	    		listaAllegati.add(aBean);
			    	    	}
			    	   
			    	    	//gm nuovo settaggio per simog 3.04
				    	    garaCheck.setNumeroLotti(lottiDaPerfezionareOPerfezionati.size());
				    	    garaCheck.setIMPORTO_GARA(totLotti);
			    	    	
//							 messaggi da ritornare
				    		AllValidationBeans msgs = new AllValidationBeans();
				    		
				    	    bga.pubblicaBandoGara(currentUser.getLogin(), garaCheck, pubblicazione, 
				    	    		lottiDaPerfezionare, elencoLottiDaCancellare, dataPubblicazione, dataScadenza, 
				    	    		id_motivazione, note_canc, isPubblicazione, isInvito, listaAllegati, 
				    	    		gcwbc.getImportoGara(lottiDaPerfezionareOPerfezionati.values(), true, false), 
				    	    		msgs, oraScadenza, dataScadenzaRichiestaInvito, dataLetteraInvito); 
			    	    	
			    	    	//commit di tutte le operazioni
			        	    currentActiveConnection.commit();

			        	    String redirectUrl = ParametriServlet.SRV_GESTIONE_SCHEDE+"?"+ParametriServlet.FIELD_NAME_ID_GARA+"="+String.valueOf(idGara)+"&"+ParametriServlet.FROM_GARE+"="+ Costanti.FLAG_VALORE_SI+"&"+ParametriServlet.FROM_RICERCA+"="+ Costanti.FLAG_VALORE_NO;
					    	
			        	    if(isPubblicazione || isInvito){ 	
                                String operazione = PubblicazioneBean.TipoOperazione.getEnumByTipo(pubblicazione.getTipoOperazione()).getDescr(); 
						        msgs.addValidationInfo(SIMOG_GARA_019.replace("$1",operazione).replace("$2", "numero ["+String.valueOf(idGara)+"]"));
				    	    }
				    	    else{
						        msgs.addValidationInfo(SIMOG_GARA_020.replace("$1", "numero ["+String.valueOf(idGara)+"]"));
				    	    }
	
			        	    sendValidations(request, response, msgs, redirectUrl);
				    	}
				    	//se ho avuto errori nella validazione dei bean, invio gli errori all'url
				    	else{
			   // 	    	request.setAttribute(ParametriServlet.TIPO_PUBBLICAZIONE, tipoPubblicazione);
				    		//logger.error ( "pubblicazione della gara fallita" );
							sendValidations(request, response, bga.getEccezioni(), url);
				    	}
		    	    }
		    	    else if (ParametriServlet.ACTION_SALVA_AVVISO.equals(action)){
		    	    	PubblicazioneBean pubblicazione = new PubblicazioneBean();
		    	    	PubblicazioneAction pubAction = new PubblicazioneAction(currentActiveConnection, logger);
		    	        pubblicazione = pubAction.getBean(request);
		    	        bga.validaPubblicazioneAvviso(pubblicazione);
		    	        bga.validaAllegatiAvviso(allAvviso);
		    	        request.setAttribute(ParametriServlet.PUBBLICAZIONE, pubblicazione);
		    	        
		    	        //se non ho avuto errori nella validazione dei bean
			    	    if(bga.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() == 0){
				     
			    	    	ArrayList <AllegatoBean> listaAllegati = new ArrayList<AllegatoBean>();
				        	AllegatoManager aMan = new AllegatoManager(currentActiveConnection, logger);
			    	    	AllegatoBean aBean = new AllegatoBean();
			    	    	aBean.setIdGara(Integer.parseInt(idGara));
			    	    	aBean.setTipoDoc(PubblicazioneBean.TipoDocumento.AVVISO.getCodice());
			    	    	List aBeans = aMan.load(aBean);
			    	    	if(aBeans.size()>0){
			    	    		aBean = (AllegatoBean)aBeans.get(0);
			    	    		listaAllegati.add(aBean);
			    	    	}    
				    	    long idAgg = Long.parseLong(idAggiudicazione);
				    	    Timestamp dataAgg = PageHelper.parseTime(dataInizioAggiudicazione);
				    	    
			    	    	bga.pubblicaAvviso(currentUser.getLogin(), Long.parseLong(idGara), pubblicazione, listaAllegati, idAgg, dataAgg); 

			    	    	
                            AggiudicazioniManager am = new AggiudicazioniManager(currentActiveConnection, logger);
                            AggiudicazioneBean aggbea = am.getAggiudicazioni(Long.parseLong(idAggiudicazione), PageHelper.parseTime(dataInizioAggiudicazione), false);
                            InfoComuniManager icm = new InfoComuniManager(currentActiveConnection, logger);
                            InfoComuniBean icb = icm.getInfoComuniByCig(aggbea.getCig());
                            
			    	    	//commit di tutte le operazioni
			        	    currentActiveConnection.commit();
				    	    
                            String operazione = PubblicazioneBean.TipoOperazione.getEnumByTipo(pubblicazione.getTipoOperazione()).getDescr(); 

						    //ParametriServlet.SRV_SCHEDA_A+"?"+PSBD.FIELD_NAME_ID_AGGIUDICAZIONE+"="+ idAggiudicazione +
						    //"&"+PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE+"="+dataInizioAggiudicazione
                            sendMessage(request, response, SIMOG_GARA_019.replace("$1",operazione).replace("$2", "numero ["+String.valueOf(idGara)+"]"), 
					        	ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + icb.getIdLotto() 
					        );
				    	}
				    	//se ho avuto errori nella validazione dei bean, invio gli errori all'url
				    	else{
		//	    	    	request.setAttribute(ParametriServlet.TIPO_PUBBLICAZIONE, tipoPubblicazione);
			    	    	request.setAttribute(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE, idAggiudicazione);
			    	    	request.setAttribute(PSBD.FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE, dataInizioAggiudicazione);
			    	    	
				    		//logger.error ( "pubblicazione della gara fallita" );
							sendValidations(request, response, bga.getEccezioni(), url);
				    	}
		    	    }
		    	  //gm se la action e il perfezionamento di una gara con modo realizzazione di tipo adesione 
		    	    else if (ParametriServlet.ACTION_SALVA_ADESIONE_GARA.equals(action)){
			    	    	//gm lottiDaPerfezionare ottenuto dalla differenza tra elencoLotti e lottiDaCancellare
			    	    	Map <String,Lotto> lottiDaPerfezionare = new HashMap<String,Lotto>();
			    	    	lottiDaPerfezionare = bga.caricaElencoLottiDaPerfezionare(bga.caricaElencoLotti(dettagliGara),null);
			    	    	//gm elencoLottiDaPerfezionareOPerfezionati ottenuto da lotti da perfezionare pi� lotti gia perfezionati
			    	    	Map <String,Lotto> lottiDaPerfezionareOPerfezionati = new HashMap<String,Lotto>();
			    	    	lottiDaPerfezionareOPerfezionati = bga.caricaElencoLottiDaPerfezionareOPerfezionati(bga.caricaElencoLotti(dettagliGara),null);
			    	    	//gm nuovo settaggio per simog 3.04
				    	    garaCheck.setNumeroLotti(lottiDaPerfezionareOPerfezionati.size());
				    	    			    	    
				    	    BigDecimal totLotti = gcwbc.getImportoGara(lottiDaPerfezionareOPerfezionati.values(), false, false);
	                        
				    	    garaCheck.setIMPORTO_GARA(totLotti);
	
				    	    String dataPubblicazione = PageHelper.getCurrentDate();
					    	String dataScadenza = PageHelper.getFormattedIncreasedDate();
					    
	//						 messaggi da ritornare
				    		AllValidationBeans msgs = new AllValidationBeans();
				    		
				    	    bga.pubblicaBandoGara(currentUser.getLogin(), garaCheck, null, lottiDaPerfezionare, null, 
				    	    		dataPubblicazione, dataScadenza, null, null, false, false, null, 
				    	    		gcwbc.getImportoGara(lottiDaPerfezionareOPerfezionati.values(), true, false), msgs, null,
				    	    		null,null); 
			    	    	
			    	    	//commit di tutte le operazioni
			        	    currentActiveConnection.commit();
	
			        	    String redirectUrl = ParametriServlet.SRV_GESTIONE_SCHEDE+"?"+ParametriServlet.FIELD_NAME_ID_GARA+"="+String.valueOf(idGara)+"&"+ParametriServlet.FROM_GARE+"="+ Costanti.FLAG_VALORE_SI+"&"+ParametriServlet.FROM_RICERCA+"="+ Costanti.FLAG_VALORE_NO;
					    	
					        msgs.addValidationInfo(SIMOG_GARA_020.replace("$1", "numero ["+String.valueOf(idGara)+"]"));
	
			        	    sendValidations(request, response, msgs, redirectUrl);
		    	    }
		    	    //is3030_RFWEBGL00Active
		    	    else if(ParametriServlet.ACTION_CARICA_INVITO.equals(action)){
		    	       
		                    if( SimogFlags.is3030_RFWEBGL02Active() ){
		                        String targetPage = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara
		                                          + "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;   
		    
		                        AVCPassAction avpa = new AVCPassAction(currentActiveConnection, logger, configuration); 
		                        if( avpa.isAVCPass(garaCheck, null, AVCPassFunzioneEnum.WEB_LOTTO_PERF_PROC_RISTRETTA.getCodice()) ){
		                           AllValidationBeans msgs = new AllValidationBeans();
		                           msgs.addValidationErr(SIMOG_AVCPASS_001);
		                           sendValidations(request, response, msgs, targetPage);  
		                           return;
		                        }
		                    }	    	       
			    	       
			    	        String redirectUrl = ParametriServlet.JSP_PUBBLICAZIONE_INVITO;
			    	       
	                        // cerco eventuali allegati presenti per la gara
	                        AllegatoManager aMan = new AllegatoManager(currentActiveConnection, logger);
	                        AllegatoBean aBean = new AllegatoBean();
	                        List aBeans = aMan.load(aBean);
	                        aBean = new AllegatoBean();
	                        aBean.setIdGara(Integer.parseInt(idGara));
	                        aBean.setTipoDoc(PubblicazioneBean.TipoDocumento.LETTERA_INVITO.getCodice());
	                        aBeans = aMan.load(aBean);
	                        if(aBeans.size()>0){
	                            aBean = (AllegatoBean)aBeans.get(0);
	                            allInvito = String.valueOf(aBean.getIdAllegato()).trim();
	                        }
	                        request.setAttribute(ParametriServlet.ALLEGATO3, allInvito);
	                        
	                        if(allInvito != null)
	                            request.setAttribute(ParametriServlet.ALLEGATO3DESC, ParametriServlet.PRESENTE);
	                        else
	                            request.setAttribute(ParametriServlet.ALLEGATO3DESC, "");
	                        
	                        
	                        String dataPubblicazione = dettagliGara.getRow(0).getNulledField(LOTTO.DATA_PUBBLICAZIONE);
	                        String dataScadenzaRichiestaInvito = dettagliGara.getRow(0).getNulledField(LOTTO.DATA_SCADENZA_RICHIESTA_INVITO);
	                        String dataLetteraInvito = dettagliGara.getRow(0).getNulledField(LOTTO.DATA_LETTERA_INVITO);
	                        String dataScadenzaPagamenti = dettagliGara.getRow(0).getNulledField(LOTTO.DATA_SCADENZA_PAGAMENTI);
	                        String oraScadenzaPagamenti = dettagliGara.getRow(0).getNulledField(LOTTO.ORA_SCADENZA);
	                        
	                        //TICKET ALM #10632 MAC
	                        if(dataPubblicazione==null || "".equals(dataPubblicazione)) {
	                        	LottoManager lm = new LottoManager(currentActiveConnection,logger);
	                        	List<Lotto> l = lm.getListaLotti(Long.parseLong(idGara));
	                        	if(l.size()>0) {
	                        		dataPubblicazione = l.get(0).getData_Pubblicazione();
	                        		if(dataScadenzaRichiestaInvito==null || "".equals(dataScadenzaRichiestaInvito))
	                        			dataScadenzaRichiestaInvito = l.get(0).getDataScadenzaRichiestaInvito();
	                        	}
	                        }//FINE TICKET ALM #10632 MAC
	                        
	                        request.setAttribute(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE, PageHelper.getFormattedDate(dataPubblicazione));
	                        request.setAttribute(ParametriServlet.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO, PageHelper.getFormattedDate(dataScadenzaRichiestaInvito));
	                        request.setAttribute(ParametriServlet.FIELD_NAME_DATA_LETTERA_INVITO, PageHelper.getFormattedDate(dataLetteraInvito));
	                        request.setAttribute(ParametriServlet.FIELD_NAME_DATA_SCADENZA, PageHelper.getFormattedDate(dataScadenzaPagamenti));
	                        request.setAttribute(ParametriServlet.FIELD_NAME_ORA_SCADENZA, PageHelper.getFormattedDate(oraScadenzaPagamenti));
		                        
			    	        forward(redirectUrl, request, response);
		    	    }
		    	  //is3030_RFWEBGL00Active
                    else if(ParametriServlet.ACTION_SALVA_INVITO.equals(action)){
                       
                       String dataPubblicazione = PageHelper.formatDateOrNull(request.getParameter(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE));
                       String dataScadenzaRichiestaInvito = PageHelper.formatDateOrNull(request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO));
                       String dataLetteraInvito = PageHelper.formatDateOrNull(request.getParameter(ParametriServlet.FIELD_NAME_DATA_LETTERA_INVITO));
                       String dataScadenzaPagamenti = PageHelper.formatDateOrNull(request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA));
                       String oraScadenzaPagamenti = PageHelper.formatDateOrNull(request.getParameter(ParametriServlet.FIELD_NAME_ORA_SCADENZA));
                       
                       String allegato3 = request.getParameter(ParametriServlet.ALLEGATO3);
                       String allegato3Desc = request.getParameter(ParametriServlet.ALLEGATO3DESC);
                       
                       Lotto lotto = new Lotto();
                       lotto.setId_Gara(Long.valueOf(idGara));
                       lotto.setData_Pubblicazione(dataPubblicazione);
                       lotto.setDataScadenzaRichiestaInvito(dataScadenzaRichiestaInvito);
                       lotto.setDataLetteraInvito(dataLetteraInvito);
                       lotto.setDataScadenzaPagamenti(dataScadenzaPagamenti);
                       lotto.setORA_SCADENZA(oraScadenzaPagamenti);
                       
                       //validazione pubblicazione fase 2
                       //TICKET ALM #648
                       //Recupero flag per verifica lettera invito
                       bga.validaPerfezionamentoProceduraRistretta(lotto);
                       
                       PubblicazioneManager pubManager = new PubblicazioneManager(currentActiveConnection, logger);
                       List<PubblicazioneBean> pubblicazioneList = pubManager.getStoricoPubblicazioniGara(garaCheck.getId_Gara());
                       String flagBeniCult = "S";

                       //TICKET ALM #3922
                       String dataCreazione = garaCheck.getData_creazione();
                       if(!SimogFlags.is3042Active() || !SimogProperties.getInstance().isDataCreatedAfter3042(dataCreazione)) {
	                       if(!pubblicazioneList.isEmpty() && pubblicazioneList.size() > 0 && pubblicazioneList.get(pubblicazioneList.size()-1).getFlag_benicult() != null)
	                    	   flagBeniCult = pubblicazioneList.get(pubblicazioneList.size()-1).getFlag_benicult().trim();
                       }
                       if(!SimogFlags.is3042Active() || !SimogProperties.getInstance().isDataCreatedAfter3042(dataCreazione))
                          bga.validaAllegati(null, null, allegato3, false, flagBeniCult.equals("S") ? true : false, false, false);
                       else
                          bga.validaAllegati(null, null, allegato3, false, true, false, false);
                       //FINE TICKET ALM #3922
                       //FINE TICKET ALM #648
                       
                       AllValidationBeans msgs = new AllValidationBeans();
                       msgs.add( bga.getEccezioni() );
                       
                       if(msgs.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() == 0){
                          
                          Map <String,Lotto> lottiDaPerfezionare = bga.caricaElencoLotti(dettagliGara);
                          
                          //pubblicazione bando (fase 2)   
                          
                          bga.pubblicaBandoGaraProceduraRistretta(currentUser.getLogin(), garaCheck, lottiDaPerfezionare, dataLetteraInvito, dataScadenzaPagamenti, oraScadenzaPagamenti);
                          
                          //commit di tutte le operazioni
                          currentActiveConnection.commit();
                          
                          url = ParametriServlet.SRV_GESTIONE_SCHEDE+"?"+ParametriServlet.FIELD_NAME_ID_GARA+"="+String.valueOf(idGara)+"&"+ParametriServlet.FROM_GARE+"="+ Costanti.FLAG_VALORE_SI+"&"+ParametriServlet.FROM_RICERCA+"="+ Costanti.FLAG_VALORE_NO;
                          
                          msgs.addValidationInfo(SIMOG_GARA_020.replace("$1", "numero ["+String.valueOf(idGara)+"]"));
                       }  
                       else {
                          
                          request.setAttribute(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE, PageHelper.getFormattedDate(dataPubblicazione));
                          request.setAttribute(ParametriServlet.FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO, PageHelper.getFormattedDate(dataScadenzaRichiestaInvito));
                          request.setAttribute(ParametriServlet.FIELD_NAME_DATA_LETTERA_INVITO, PageHelper.getFormattedDate(dataLetteraInvito));
                          request.setAttribute(ParametriServlet.FIELD_NAME_DATA_SCADENZA, PageHelper.getFormattedDate(dataScadenzaPagamenti));
                          request.setAttribute(ParametriServlet.FIELD_NAME_ORA_SCADENZA, PageHelper.getFormattedDate(oraScadenzaPagamenti));
                          
                          request.setAttribute(ParametriServlet.ALLEGATO3, allegato3);
                          request.setAttribute(ParametriServlet.ALLEGATO3DESC, allegato3Desc);
                          
                          url = ParametriServlet.JSP_PUBBLICAZIONE_INVITO;
                       }
                       
                       //ritorno a visualizza dettaglio
                       
                       sendValidations(request, response, msgs, url);                       
                    }	
		    	    // is3031_RFWEBGL02Active
                else if(ParametriCup.ACTION_COFERMA_CUP.equals(action))
                {
                     PubblicazioneAction pubAction = new PubblicazioneAction(currentActiveConnection, logger);
                     CupLottoAggAction claAction = new CupLottoAggAction(currentActiveConnection, logger);
                     List<CupLottoAggExt> listaCUPvalidi = pubAction.getListaCupLottoByRequest(request);
                     claAction.confirmAllValidCup(listaCUPvalidi);
                     
                     currentActiveConnection.commit();
                     
                     // Caricamenti necessari per presentare la pagina con i dati immessi prima della conferma dei cup
                     PubblicazioneBean pubblicazione = pubAction.getBean(request);
                     request.setAttribute(ParametriServlet.PUBBLICAZIONE, pubblicazione);  
                     
                     String id_motivazione = request.getParameter(ParametriServletLotto.FIELD_NAME_MOTIVAZIONE); 
                     request.setAttribute(ParametriServletLotto.FIELD_NAME_MOTIVAZIONE, id_motivazione);
                     
                     List<CupLottoAggExt> elencoCupGara = claAction.getElencoCupGara(Long.parseLong(idGara));
                     request.setAttribute(ParametriCup.PARAM_ELENCO_CUP_GARA, elencoCupGara);
                     
                     AllValidationBeans retMsgs = new AllValidationBeans();
                     retMsgs.addValidationInfo("I codici CUP validi sono stati esplicitamente confermati");
                     sendValidations(request, response, retMsgs, url); 
                }                  
		    	    else {
						sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE);
						return;
					}
		    	/********************* END Gestione Actions *************************/		    
		    	}
		    	//gm else di currentUser
			    else {
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE);
					return;
				}
		    }
		    //gm else di checkSession
		    else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE);
				return;
			}
		}		
	    catch ( Exception e ) {
          logger.fatal ( e.getMessage());
		    e.printStackTrace();
		    rollback(currentActiveConnection);
		    sendError(request, response, e.getMessage(), JSP_ERRORE);
		return;
	    } 
	    finally {		
		    closeConnection(request.getSession().getId(),getClass().getName());
	    }		
	}
	/*
	 * verifica se la gara ha almeno un lotto attivo con flag esclusione
	 */
   private boolean isEsclusa(TableBean dettagliGara) {
      boolean retVal = false;
      for (int i = 0; i < dettagliGara.getFullSize(); i++) {
         if(dettagliGara.getRow(i).getNulledField(LOTTO.DATA_CANCELLAZIONE_LOTTO)!=null
            && Costanti.FLAG_VALORE_SI.equals(dettagliGara.getRow(i).getNulledField(LOTTO.FLAG_ESCLUSO))){
            retVal = true;
            break;
         }
      }
      
      return retVal;
   }
   
   

}