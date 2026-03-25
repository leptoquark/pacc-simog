package it.avlp.simog.garamanager.app;

import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avcp.simog.managers.invitati.InvitatiManager;
import it.avcp.simog.managers.stipula.StipulaManager;
import it.avlp.simog.actions.adesione.SchedaAdesioneAction;
import it.avlp.simog.beans.EsitoEnum;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InvitatoBean;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.SoglieImpEnum;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.stipula.StipulaBean;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.MessageHelper;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.GenericUtilValidator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SrvVisualizzaGareRUP_CS extends ServletBase{

	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		
		perform(request, response);
	}
	
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		HttpSession currentActiveSession = request.getSession();
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if ( checkSession(request) ) {
			//Diego aggiunto profilo RASA
			if ( currentUser.isRUP() || currentUser.isCS() || currentUser.isOssReg() || currentUser.isRASA()) {				
				visualizzaListaParametriValori(request, response);
				
				int startRow = 0;
								
				String action = request.getParameter(ACTION_GET_LIST);
				request.setAttribute(ACTION_GET_LIST, action);
				
				boolean actionNeeded = action != null;
				if(action == null)
					action = STILL;
				//CF: if obbligatorio per il corretto funzionamento del tasto "ritorna" nella pagina "Dettaglio Gara" 
				if(!action.equals(ESPORTAELENCO))
				currentActiveSession.setAttribute(ParametriServlet.STORIA_PAGINAZIONE,this.getQueryString(request));
				
				if ( actionNeeded ) {
					String startRowS = request.getParameter(START_ROW);
					startRow = Integer.parseInt(startRowS);
					if ( action.equalsIgnoreCase(REGRESS) && (startRow - configuration.getMaxElementiPerPagina() >=0 ) ) {
						startRow = startRow - configuration.getMaxElementiPerPagina();
					} else if( action.equalsIgnoreCase(PROGRESS)) {//CF aggiunto controllo if. In questo quando viene premuto il tasto 
						//"EsportaElenco", la variabile startRow mantiene il valore passotogli dalla jsp con valore 0.
						startRow = startRow + configuration.getMaxElementiPerPagina();
					}
				}
				
				String dataPubblicazione_da = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_DATA_PUBBLICAZIONE_START));
				//logger.debug ( "dataPubblicazione_da [" + dataPubblicazione_da + "]" );			
				
				String dataPubblicazione_a = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_DATA_PUBBLICAZIONE_END));	
				//logger.debug ( "dataPubblicazione_a [" + dataPubblicazione_a + "]" );			
				
				String dataScadenza_da = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_SCADENZA_START));
				//logger.debug ( "dataScadenza_da [" + dataScadenza_da + "]" );
				
				String dataScadenza_a = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_SCADENZA_END));
				//logger.debug ( "dataScadenza_a [" + dataScadenza_a + "]" );
	
/** 
 *XXX UN Aggiunto filtro cfRUP e gestione filtro ID stazione appaltante per osservatorio regionale
 */
				
				String cig = request.getParameter(ParametriServlet.FIELD_NAME_CIG);
				String numeroGara = request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA);
				if (numeroGara!=null) numeroGara = numeroGara.trim();
				   
				String richiestaAnnullamento = request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO);
				String oggettoGara = request.getParameter(ParametriServlet.FIELD_NAME_OGGETTO_GARA);
				String oggettoLotto = request.getParameter(ParametriServlet.FIELD_NAME_OGGETTO_LOTTO);
				String idStazioneAppaltante = request.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
				String richiestaAggiudicate = request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_AGGIUDICATE);
				String cfRUP = request.getParameter(ParametriServlet.FIELD_NAME_CF_OPERATORE);
				String stazioneAppaltanteSelezionata = request.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
				
				//SimogFlags.is30233_RFWEBGL03
				String soloMie = request.getParameter(ParametriServlet.FIELD_NAME_CHECKMIE);
				
				if(!action.equals(ESPORTAELENCO))
					currentActiveSession.setAttribute("stazioneSelezionata", stazioneAppaltanteSelezionata);
				
				String id_soglia = request.getParameter(ParametriServlet.ID_SOGLIA_IMPORTO);
				String cfAmm = request.getParameter(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
				
				String fromRicerca = request.getParameter(ParametriServlet.FROM_RICERCA);
				request.setAttribute(ParametriServlet.FROM_RICERCA, fromRicerca);
				
				if ( request.getParameter("chiChiama") != null && action.equals(ESPORTAELENCO) == false) {
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_CIG, cig );
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_OGGETTO_GARA, oggettoGara );
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_OGGETTO_LOTTO, oggettoLotto );
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE, idStazioneAppaltante);
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_ID_GARA, numeroGara );
					currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_LOTTO_DA, dataPubblicazione_da );
					currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_LOTTO_A, dataPubblicazione_a );
					currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_A, dataScadenza_a );
					currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_DA, dataScadenza_da );				
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO, richiestaAnnullamento );	
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_RICHIESTA_AGGIUDICATE, richiestaAggiudicate );
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_CF_OPERATORE, cfRUP );
					currentActiveSession.setAttribute(ParametriServlet.ID_SOGLIA_IMPORTO, id_soglia );
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE, cfAmm );
					//SimogFlags.is30233_RFWEBGL03
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_CHECKMIE, soloMie );
				}
				//se non vengo da pubblicazione o rettifiche prendo l'id gara dalla sessione
				if(!Costanti.FLAG_VALORE_NO.equals(fromRicerca)){
					numeroGara = (String)currentActiveSession.getAttribute(FIELD_NAME_ID_GARA);
				}
				
                if (numeroGara!=null) numeroGara = numeroGara.trim();

				cig = (String)currentActiveSession.getAttribute(FIELD_NAME_CIG);
				oggettoGara = (String)currentActiveSession.getAttribute(FIELD_NAME_OGGETTO_GARA);
				oggettoLotto  = (String)currentActiveSession.getAttribute(FIELD_NAME_OGGETTO_LOTTO);
				idStazioneAppaltante = (String)currentActiveSession.getAttribute(FIELD_NAME_ID_STAZIONE_APPALTANTE);
				dataPubblicazione_da = (String)currentActiveSession.getAttribute(SESSION_DATA_PUBBLICAZIONE_LOTTO_DA);
				dataPubblicazione_a = (String)currentActiveSession.getAttribute(SESSION_DATA_PUBBLICAZIONE_LOTTO_A);
				dataScadenza_da = (String)currentActiveSession.getAttribute(SESSION_DATA_SCADENZA_DA);
				dataScadenza_a = (String)currentActiveSession.getAttribute(SESSION_DATA_SCADENZA_A);
				richiestaAnnullamento = (String)currentActiveSession.getAttribute(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO);
				richiestaAggiudicate = (String)currentActiveSession.getAttribute(ParametriServlet.FIELD_NAME_RICHIESTA_AGGIUDICATE);
				cfRUP = (String)currentActiveSession.getAttribute(ParametriServlet.FIELD_NAME_CF_OPERATORE);
				id_soglia = (String)currentActiveSession.getAttribute(ID_SOGLIA_IMPORTO);
				cfAmm = (String)currentActiveSession.getAttribute(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
				
				//SimogFlags.is30233_RFWEBGL03
				soloMie = (String)currentActiveSession.getAttribute(ParametriServlet.FIELD_NAME_CHECKMIE);
				
				Hashtable listaSARiferimento = new Hashtable();
				
				if(currentUser.isRUP()){

					if ( idStazioneAppaltante == null || "tutte".equalsIgnoreCase(idStazioneAppaltante) ) {
						listaSARiferimento = currentUser.getUfficiByProfilo(ProfiloEnum.RUP);
						logger.debug("Ricerca Gare relative a tutte le stazioni appaltanti [" + currentUser.getUffici().keys() + "]");
					} else if("SA_DELEGATE".equals(idStazioneAppaltante)) {//TICKET ALM #659 - 3.04.4
						
					   //Recupera le SA delegate
						try {
							currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
						} catch (SimogException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						GaraManager gm = new GaraManager(currentActiveConnection,logger);
						try {

							listaSARiferimento = gm.getSADelegateFromAmministrazioni(currentUser.getAmministrazioni());

						} catch (Exception e) {
	
							e.printStackTrace();
						}
						
						if(listaSARiferimento.size()<=0) {
							sendError(request, response, MessageHelper.getMessage(request, "errore.SIMOG_RIC_005"), JSP_GESTIONE_SCHEDE);
							return;
						}
						//FINE TICKET ALM - 3.04.3
						
					} else {
						logger.debug("Ricerca Gare relative a stazione appaltante [" + idStazioneAppaltante + "]");
						listaSARiferimento.put(idStazioneAppaltante, currentUser.getUfficiByProfilo(currentUser.getProfiloEnum()).get(idStazioneAppaltante));
					}
				}
				else if (currentUser.isOssReg()){
					if(idStazioneAppaltante != null && idStazioneAppaltante.trim().length() > 0)
						listaSARiferimento.put(idStazioneAppaltante, currentUser.getUfficiByProfilo(ProfiloEnum.OSSREG));
				}
				if(!action.equals(ESPORTAELENCO)){	
					currentActiveSession.removeAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE);
					currentActiveSession.removeAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE);
				}
				try {
					if(currentActiveConnection==null)
				    	currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					
					// controllo parametri immessi

					GenericUtilValidator val = new GenericUtilValidator(currentActiveConnection, logger);
					 
					//Diego - Qualora Ã¨ un utente RASA bypasso il controllo di valorizzazione di almeno un criterio di ricerca del Form
					if(currentUser.isRASA()) {
						cfAmm = "---";
					}
					 
					if(val.isEmpty(oggettoGara) && val.isEmpty(oggettoLotto) && val.isEmpty(cig) && val.isEmpty(idStazioneAppaltante)
						&& val.isEmpty(numeroGara) && val.isEmpty(dataScadenza_a) && val.isEmpty(dataScadenza_da) 
						&& val.isEmpty(dataPubblicazione_a) && val.isEmpty(dataPubblicazione_da)  
						&& val.isEmpty(id_soglia) && val.isEmpty(richiestaAnnullamento) && val.isEmpty(richiestaAggiudicate)
						&& val.isEmpty(cfAmm) && val.isEmpty(cfRUP) && val.isEmpty(soloMie)
					)
					{
						sendError(request, response, MessageHelper.getMessage(request, "errore.SIMOG_RIC_003"), currentUser.isAmministratore() ? JSP_GESTIONE_GARE_EXT : JSP_GESTIONE_SCHEDE);
						return;
					}

					// Validazione del Numero Gara
					if ( numeroGara != null ) {
						if (numeroGara.length() > 8 || !PageHelper.isNumeric(numeroGara)){
							sendError(request, response, MessageHelper.getMessage(request, "errore.SIMOG_GARA_018"), currentUser.isAmministratore() ? JSP_GESTIONE_GARE_EXT : JSP_GESTIONE_SCHEDE);
							return;
						}
					}	
									
					if ( cig != null && !"".equals(cig)) {
						if ( !PageHelper.isValidCIG(cig)){
							if(dataPubblicazione_da != null && dataPubblicazione_a != null) {
								currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE, true);
							}
							else { currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE, false); }
							
							if(dataScadenza_da != null && dataScadenza_a != null) {
								currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, true);
							}
							else { currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, false);	}
							
							sendError(request, response, MessageHelper.getMessage(request, "errore.SIMOG_GARA_010"), currentUser.isAmministratore() ? JSP_GESTIONE_GARE_EXT : JSP_GESTIONE_SCHEDE);
							return;
						}
					}	
					
					// SE LE DATE DI PUBBLICAZIONE SONO inserite e sbagliate
					if ( (dataPubblicazione_da != null && dataPubblicazione_a != null ) && dataPubblicazione_a.compareTo( dataPubblicazione_da ) < 0 ) 
					{
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE, true);
						if( dataScadenza_da != null && dataScadenza_a != null ) {
							currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, true);
						}
						else {
							currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, false);
						}
						sendError(request, response, MessageHelper.getMessage(request, "errore.SIMOG_GARA_011"), currentUser.isAmministratore() ? JSP_GESTIONE_GARE_EXT : JSP_GESTIONE_SCHEDE);
						return;
					}
					else if((dataPubblicazione_da != null && dataPubblicazione_a != null ) && dataPubblicazione_a.compareTo( dataPubblicazione_da ) >= 0 ) {
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE, true);
					}
					else {
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE, false);
					}
					
					// SE LE DATE DI SCADENZA SONO INSERITE ED ERRATE
					if((dataScadenza_da != null && dataScadenza_a != null ) && dataScadenza_a.compareTo( dataScadenza_da ) < 0 ) {
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, true);
						sendError(request, response, MessageHelper.getMessage(request, "errore.SIMOG_GARA_012"), currentUser.isAmministratore() ? JSP_GESTIONE_GARE_EXT : JSP_GESTIONE_SCHEDE);
						return;
					}
					else if((dataScadenza_da != null && dataScadenza_a != null ) && dataScadenza_a.compareTo( dataScadenza_da ) >= 0 ) {
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, true);
					}
					else {
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, false);
					}
					
					//Recupero soglie min e max importo
					SoglieImpEnum soglia = SoglieImpEnum.getEnumByCodice(id_soglia);
					
					TableBean garaList = null;
					int start;
					if(!action.equals(ESPORTAELENCO) || currentUser.isRUP())
						start=configuration.getMaxElementiPerPagina();
					else 
						start=configuration.getELEMENTI_EXPORT();
			
					if(action.equals(ESPORTAELENCO)){
						start=configuration.getELEMENTI_EXPORT();
						startRow=0;
					}
										
					GaraManager garaManager = new GaraManager(currentActiveConnection, logger);		
					
					logger.debug("Esecuzione GaraList");
					
					if(!"SA_DELEGATE".equals(idStazioneAppaltante)) {
						
					//Diego - duplicato solo per i profili rasa	
						if(currentUser.isRASA()) {
							
							  ArrayList listCfAmm =  new ArrayList(currentUser.getListaRASA());
							  int a =0;
							
							  if(idStazioneAppaltante!=null && !"".equals(idStazioneAppaltante))
								  listaSARiferimento.put(idStazioneAppaltante,idStazioneAppaltante);
								  
							garaList = garaManager.getGaraListRasa(
									oggettoGara,
									oggettoLotto,
									cig,
									numeroGara,
									listaSARiferimento, 
									dataScadenza_a,
									dataScadenza_da,
									dataPubblicazione_a,
									dataPubblicazione_da,
									startRow,
									start,
									true, richiestaAnnullamento,
									null, 
									richiestaAggiudicate,
									cfRUP,
									soglia != null ? soglia.min() : null,
									soglia != null ? soglia.max() : null,
									listCfAmm,
									"on".equals(soloMie) ? currentUser.getLogin(): null);
						}else {
						
						garaList = garaManager.getGaraList(
								oggettoGara,
								oggettoLotto,
								cig,
								numeroGara,
								listaSARiferimento, 
								dataScadenza_a,
								dataScadenza_da,
								dataPubblicazione_a,
								dataPubblicazione_da,
								startRow,
								start,
								true, richiestaAnnullamento,
								currentUser.isOssReg() ? currentUser.getAdminOr() : null, 
								richiestaAggiudicate,
								currentUser.isOssReg() ? cfRUP : null,
								soglia != null ? soglia.min() : null,
								soglia != null ? soglia.max() : null,
								cfAmm,
								"on".equals(soloMie) ? currentUser.getLogin(): null);
						}
						
						
						if(garaList.size()==0 && cig != null && !"".equals(cig) && !currentUser.isOssReg()) {
							//ricerca di gare che non sono state prese in carico dalla delegante
							GaraManager gm = new GaraManager(currentActiveConnection,logger);
							listaSARiferimento = gm.getSADelegateFromAmministrazioni(currentUser.getAmministrazioni());
							garaList = garaManager.getGaraDelegataList(
									oggettoGara,
									oggettoLotto,
									cig,
									numeroGara,
									listaSARiferimento, 
									dataScadenza_a,
									dataScadenza_da,
									dataPubblicazione_a,
									dataPubblicazione_da,
									startRow,
									start,
									true, richiestaAnnullamento,
									currentUser.isOssReg() ? currentUser.getAdminOr() : null, 
									richiestaAggiudicate,
									currentUser.isOssReg() ? cfRUP : null,
									soglia != null ? soglia.min() : null,
									soglia != null ? soglia.max() : null,
									cfAmm,
									"on".equals(soloMie) ? currentUser.getLogin(): null,currentUser.getAmministrazioniByProfilo(ProfiloEnum.RUP));
							listaSARiferimento = currentUser.getAmministrazioniByProfilo(ProfiloEnum.RUP);
						}
						
					} else {

						garaList = garaManager.getGaraDelegataList(
								oggettoGara,
								oggettoLotto,
								cig,
								numeroGara,
								listaSARiferimento, 
								dataScadenza_a,
								dataScadenza_da,
								dataPubblicazione_a,
								dataPubblicazione_da,
								startRow,
								start,
								true, richiestaAnnullamento,
								currentUser.isOssReg() ? currentUser.getAdminOr() : null, 
								richiestaAggiudicate,
								currentUser.isOssReg() ? cfRUP : null,
								soglia != null ? soglia.min() : null,
								soglia != null ? soglia.max() : null,
								cfAmm,
								"on".equals(soloMie) ? currentUser.getLogin(): null,currentUser.getAmministrazioniByProfilo(ProfiloEnum.RUP));
					}
					
					//TICKET ALM - 3.04.3 #3916
					//Se non sono stati trovati risultati, cerca tra le gare accordo quadro non di competenza (ma solo se e' stato indicato almeno gara o cig come filtro)
					request.setAttribute(ParametriServlet.IS_ACC_QUADRO_NC, "NOK");//Inizializza attributo
					if(garaList.size()==0 && currentUser.isOssReg()
							&& ((numeroGara != null && numeroGara.trim().length() > 0)
						      || (cig != null && cig.trim().length() > 0))) {
						garaList = garaManager.getGaraListAccQ(
								oggettoGara,
								oggettoLotto,
								cig,
								numeroGara,
								currentUser.getUfficiByProfilo(ProfiloEnum.RUP), 
								dataScadenza_a,
								dataScadenza_da,
								dataPubblicazione_a,
								dataPubblicazione_da,
								startRow,
								start,
								true, richiestaAnnullamento,
								currentUser.isOssReg() ? currentUser.getAdminOr() : null, 
								richiestaAggiudicate,
								currentUser.isOssReg() ? cfRUP : null,
								soglia != null ? soglia.min() : null,
								soglia != null ? soglia.max() : null);
						request.setAttribute(ParametriServlet.IS_ACC_QUADRO_NC, "OK");
					}//FINE TICKET ALM - 3.04.3 #3916
					
					if(garaList.size()==0 && currentUser.isRUP()) {
						garaList = 	garaManager.getGaraListPostDelega(
								oggettoGara,
								oggettoLotto,
								cig,
								numeroGara,
								listaSARiferimento, 
								dataScadenza_a,
								dataScadenza_da,
								dataPubblicazione_a,
								dataPubblicazione_da,
								startRow,
								start,
								true, richiestaAnnullamento,
								currentUser.isOssReg() ? currentUser.getAdminOr() : null, 
								richiestaAggiudicate,
								currentUser.isOssReg() ? cfRUP : null,
								soglia != null ? soglia.min() : null,
								soglia != null ? soglia.max() : null,
								cfAmm,
								"on".equals(soloMie) ? currentUser.getLogin(): null);
						request.setAttribute(ParametriServlet.IS_ACC_QUADRO_NC, "OK");
					}
					
					
					if ( garaList.size() == 0 ) {

/** is3030_RFWEBGL03Active **/
					    if( currentUser.isOssReg() //PP solo per osservatori
					      && (numeroGara != null && numeroGara.trim().length() > 0)
					      || (cig != null && cig.trim().length() > 0))
					    {
      	                    garaList = garaManager.getGaraList(
      	                            oggettoGara,
      	                            oggettoLotto,
      	                            cig,
      	                            numeroGara,
      	                            listaSARiferimento, 
      	                            dataScadenza_a,
      	                            dataScadenza_da,
      	                            dataPubblicazione_a,
      	                            dataPubblicazione_da,
      	                            startRow,
      	                            start,
      	                            true, richiestaAnnullamento,
      	                            null, // NO AdminOR
      	                            richiestaAggiudicate,
      	                            currentUser.isOssReg() ? cfRUP : null,
      	                            soglia != null ? soglia.min() : null,
      	                            soglia != null ? soglia.max() : null,
      	                            cfAmm,
      	                            "on".equals(soloMie) ? currentUser.getLogin(): null);
      	                    
      	                    if( garaList.size() != 0 ){
      	                    	//MEV 34978 modificato messaggio errore
      	                       sendMessage(request, response, Messaggi.SIMOG_VALIDAZIONE_008, currentUser.isAmministratore() ? JSP_GESTIONE_GARE_EXT : JSP_GESTIONE_SCHEDE);
      	                       return;
      	                    }
					    }
/** is3030_RFWEBGL03Active **/	
					    
						sendMessage(request, response, Messaggi.SIMOG_RIC_001, currentUser.isAmministratore() ? JSP_GESTIONE_GARE_EXT : JSP_GESTIONE_SCHEDE);
						return;
					} else {
						
						
						String indiceGara=null;
						TableBeanRow currentRow=null;
						InvitatiManager invitatiManager = new InvitatiManager(currentActiveConnection, logger);
						ArrayList<InvitatoBean> listaInvitati= new ArrayList<InvitatoBean>();
						
						String previousGara = null;
						//gm aggiunto anche lo storico delle pubblicazioni
						List <PubblicazioneBean> storicoPubblicazioni = new ArrayList<PubblicazioneBean>();
						PubblicazioneManager pubManager = new PubblicazioneManager(currentActiveConnection, logger);

						 for ( int rowIndex = 0; rowIndex < garaList.getTableSize(); rowIndex++ ) { 	
							    currentRow = garaList.getRow(rowIndex); 
								
								indiceGara = currentRow.getNulledField(GARA.ID_GARA);
								//carico invitati e storico solo se ho una nuova gara, 
								//altrimenti sono validi quelli precedenti
								if(previousGara == null || !previousGara.equals(indiceGara)){
						    		listaInvitati=invitatiManager.carica(Long.parseLong(indiceGara));
						    		storicoPubblicazioni = pubManager.getStoricoPubblicazioniGara(Long.parseLong(indiceGara));
								}
						        previousGara = indiceGara;
						        
						    	if(listaInvitati!=null && listaInvitati.size()>0){
						    		currentRow.addFieldValue(Costanti.PRESENTI_INVITATI, "SI");	
						    	}else{
						    		currentRow.addFieldValue(Costanti.PRESENTI_INVITATI, "NO");	
						    	}
					    		if(storicoHasInvito(storicoPubblicazioni)){
					    			currentRow.addFieldValue(Costanti.STORICO_HAS_LETTERA_INVITO, "SI");	
						    	}else{
						    		currentRow.addFieldValue(Costanti.STORICO_HAS_LETTERA_INVITO, "NO");	
						    	}
						 }

						// PP 28.10.2013 la variabile impostata poi non viene usata da nessuna parte!

						TableBeanRow tRow = null;
						SchedaAdesioneAction saa = new SchedaAdesioneAction(currentActiveConnection, logger);
						for(int i = 0; i < garaList.getTableSize(); i++){
							tRow = garaList.getRow(i);
							String cigAQ = tRow.getNulledField(GARA.CIG_ACC_QUADRO);
							if(cigAQ != null && !cigAQ.trim().equals("")) { 
									if(1 > saa.getBeanAggiudicazioneAccQuadro(cigAQ).getIdAggiudicazione() )
										tRow.addFieldValue(ParametriServlet.FIELD_NAME_CREA_SCHEDA, Costanti.FLAG_VALORE_TRUE);
									else
										tRow.addFieldValue(ParametriServlet.FIELD_NAME_CREA_SCHEDA, Costanti.FLAG_VALORE_FALSE);
							}
							else //aggiungo comunque...puo creare se non ha il cigAQ
								tRow.addFieldValue(ParametriServlet.FIELD_NAME_CREA_SCHEDA, Costanti.FLAG_VALORE_TRUE);
							
						}
						
						//Verifica schede per funzione delega
						InfoComuniManager icm = new InfoComuniManager(currentActiveConnection,logger);
						AggiudicazioniManager am = new AggiudicazioniManager(currentActiveConnection,logger);
						StipulaManager sm = new StipulaManager(currentActiveConnection,logger);
						for(int i = 0; i < garaList.getTableSize(); i++){
							tRow = garaList.getRow(i);
							if(tRow.getNulledField(INFO_AGGIUDICAZIONI.ID_INFO)!=null && 
									!"".equals(tRow.getNulledField(INFO_AGGIUDICAZIONI.ID_INFO)) && 
									!"-1".equals(tRow.getNulledField(INFO_AGGIUDICAZIONI.ID_INFO))) {
								long idInfoAgg = Long.parseLong(tRow.getNulledField(INFO_AGGIUDICAZIONI.ID_INFO));
								Timestamp dataInizioInfo = PageHelper.parseTime(tRow.getNulledField(INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO));
								InfoComuniBean icbean = icm.load(idInfoAgg, dataInizioInfo);

								tRow.addFieldValue("DC_ESITO_PROCEDURA", icbean.getEsitoProcedura());	
								tRow.addFieldValue("DC_STATO", String.valueOf(icbean.getIdStato()));	
								
								String idDelegaStr = tRow.getNulledField("T_ID_F_DELEGATE");
								if(idDelegaStr!=null && !"".equals(idDelegaStr)) {
									int idDelega = Integer.parseInt(idDelegaStr);
									if(idDelega==Costanti.DELEGA1 || idDelega==Costanti.DELEGA2) {
										List<AggiudicazioneBean> listagg = am.getAggiudicazioniList(idInfoAgg, dataInizioInfo);
										if(!listagg.isEmpty()) {
											AggiudicazioneBean agg = listagg.get(listagg.size()-1);
											if(agg!=null && agg.getIdStato()==2) {
												tRow.addFieldValue("HAS_AGGIUDICAZIONE", "TRUE");
												StipulaBean stipula = sm.load(agg.getIdAggiudicazione(), agg.getDataInizioAggiudicazione());
												if(stipula!=null && stipula.getIdStato()==2) {
													tRow.addFieldValue("HAS_STIPULA", "TRUE");
												} else {
													tRow.addFieldValue("HAS_STIPULA", "");
												}
											} else {
												tRow.addFieldValue("HAS_AGGIUDICAZIONE", "");
												tRow.addFieldValue("HAS_STIPULA", "");
											}
										} else {
											tRow.addFieldValue("HAS_AGGIUDICAZIONE", "");
											tRow.addFieldValue("HAS_STIPULA", "");
										}
									} else {
										tRow.addFieldValue("HAS_AGGIUDICAZIONE", "");
										tRow.addFieldValue("HAS_STIPULA", "");
									}
								} else {
									tRow.addFieldValue("HAS_AGGIUDICAZIONE", "");
									tRow.addFieldValue("HAS_STIPULA", "");
								}
							} else {
								tRow.addFieldValue("HAS_AGGIUDICAZIONE", "");
								tRow.addFieldValue("HAS_STIPULA", "");
								tRow.addFieldValue("DC_ESITO_PROCEDURA", "");
								tRow.addFieldValue("DC_STATO", "");	
							}
						}
						
						request.setAttribute(ParametriServlet.TABLEBEAN, garaList);
						request.setAttribute(ParametriServlet.START_ROW, new Integer(startRow) );
						request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getELEMENTI_VISUALIZZATI());
						request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_EXPORT, new Integer(configuration.getELEMENTI_EXPORT()));
						if (SimogFlags.isEsportaCSV() == true &&  action!=null && action.equals(ESPORTAELENCO)) {
							TableBean outBean = new TableBean();
							int limit = garaList.getFullSize();
							if(limit > configuration.getELEMENTI_EXPORT())
								limit = configuration.getELEMENTI_EXPORT();
							// PP se spezzata,  elimino l'ultima riga
							for (int i = 0;	i < limit;	i++) {
																
								TableBeanRow newRow = new TableBeanRow (outBean);	
								// il prefisso "xx_" serve per ordinare le colonne in fase di creazione del csv
								newRow.addFieldValue ( "01_Amministrazione Competente", garaList.getNulledField(GARA.DENOM_AMMINISTRAZIONE, i));
								newRow.addFieldValue ( "02_Stazione Appaltante", garaList.getNulledField(GARA.DENOM_STAZIONE_APPALTANTE, i));
								newRow.addFieldValue ( "03_Numero Gara", garaList.getNulledField(GARA.ID_GARA, i));
								newRow.addFieldValue ( "04_Oggetto della Gara", garaList.getNulledField(GARA.OGGETTO, i));
								newRow.addFieldValue ( "05_Data Creazione", garaList.getNulledField(GARA.DATA_CREAZIONE, i));
								newRow.addFieldValue ( "06_Data perfezionamento bando", garaList.getNulledField(GARA.DATA_PERFEZIONAMENTO_BANDO, i));
								newRow.addFieldValue ( "07_Stato gara", garaList.getNulledField("STATOSCHEDA", i));
								
								newRow.addFieldValue ( "08_CIG",  garaList.getNulledField(LOTTO.CIG, i) + garaList.getNulledField(LOTTO.CIG_KKK, i));
								newRow.addFieldValue ( "09_Oggetto Lotto", garaList.getNulledField(LOTTO.TABLE_NAME + LOTTO.OGGETTO, i));
								
								
								String temp = garaList.getNulledField(LOTTO.IMPORTO_LOTTO, i);
								temp = PageHelper.getFormattedImportoNoEuro(temp);
								
								//Ticket ALM #1917
								newRow.addFieldValue ( "10_Importo lotto", garaList.getNulledField("STATOSCHEDA", i).equalsIgnoreCase("ANNULLATO") ? "" : temp);
								
								newRow.addFieldValue ( "11_Data pubblicazione", garaList.getNulledField(LOTTO.DATA_PUBBLICAZIONE, i));
							
								
								/*Ticket ALM #669
								  Se il lotto ha una data di cancellazione valorizzata, impostare "CANCELLATO" come stato.
								  Altrimenti effettua verifica su data pubblicazione.
								*/
								boolean cancellato = ! "".equals( garaList.getNulledField( LOTTO.DATA_CANCELLAZIONE_LOTTO, i ) ) || ! "".equals( garaList.getNulledField( LOTTO.DATA_INIB_PAGAMENTO, i ) ); 
								 String statoLotto = ! "".equalsIgnoreCase( garaList.getNulledField( LOTTO.DATA_PUBBLICAZIONE, i ) ) ? "PERFEZIONATO" : "IN LAVORAZIONE"; 
								 temp = cancellato ? "CANCELLATO" : statoLotto; 
								//Fine ticket #669-#1917
								//newRow.addFieldValue ("12_Stato lotto", temp);
								newRow.addFieldValue ("12_Stato lotto", garaList.getNulledField("STATOSCHEDA", i).equalsIgnoreCase("ANNULLATO") ? "" : temp);
							}
							
							outBean.setFullSize(garaList.getFullSize());
							outBean.setRowsAdded(limit);
						
							performCSV( request, response, outBean);
							//performCSV( request, response, garaList);
							
							return;
							
						}else{
							
							
							String href=ParametriServlet.SRV_GESTIONE_SCHEDE;
							//TICKET ALM 13521 - 3.04.4.1
							//Solo per schede dati comuni e riepilogo scheda
							if(currentUser.isAmministratore())
					    		href = ParametriServlet.SRV_GESTIONE_GARE_EXT ;
					     	else if(currentUser.isRSSA())
					    		href = ParametriServlet.SRV_GESTIONE_GARE_RSSA ;
					   		if(request.getSession().getAttribute(ParametriServlet.STORIA_PAGINAZIONE) != null && !"".equals(request.getSession().getAttribute(ParametriServlet.STORIA_PAGINAZIONE))){
					   			href += "?" + request.getSession().getAttribute(ParametriServlet.STORIA_PAGINAZIONE);
							   }
					   		else {
					   			if(fromRicerca != null) href += "?"+ParametriServlet.FROM_RICERCA+"="+fromRicerca;
					   		}
					   		request.getSession().setAttribute("ultimaRicerca",href);
							forward(ParametriServlet.JSP_VISUALIZZA_GARE_RSSA, request, response);
						}
					}
				} catch ( Exception sqle ) {
					sqle.printStackTrace();
					sendError(request, response, sqle.getMessage(), JSP_ERRORE, sqle);
					return;
				} finally {
					closeConnection(request.getSession().getId(),getClass().getName());
				}
			} else {
				sendError(request, response, MessageHelper.getMessage(request, "errore.SIMOG_LOGIN_004"), JSP_ERRORE );	
				return;
			}
		} else {
			sendError(request, response, MessageHelper.getMessage(request, "errore.SIMOG_LOGIN_003"), JSP_ERRORE );
			return;
		}
		
	}
	
	/*****************************************************************
	 * Si occupa della scrittura del CSV basata sulla TableBan result
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param results TableBean
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performCSV( HttpServletRequest request, HttpServletResponse response, TableBean results ) throws ServletException, IOException{
			logger.debug("--- eseguo performCVS ---");

			// PP response.setContentType("application/text/csv");
			// PP response.setHeader("Content-Disposition", "attachment; filename=elencoGaraLotti.csv");
		    response.setContentType("text/csv; charset=UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setHeader("Content-Disposition", "attachment;filename=\"elencoGaraLotti.csv\"");
		 
		    PrintWriter wrt = response.getWriter();
		    
			results.writeCSVOrdered(wrt,';');
			
			wrt.flush();
			wrt.close();
			

//			Cookie[] myCookies = request.getCookies();
//			Cookie cookie = null;
//		
//			if(myCookies != null)
//			{
//				for(int i = 0; i < myCookies.length; i++)
//				{
//					cookie = myCookies[i];
//					if(cookie.getName() != null &&
//							cookie.getName().equals("JSESSIONID"))
//					{
//						break;
//					}
//				}
//			}
//			response.addCookie(cookie);

	}
		
}
