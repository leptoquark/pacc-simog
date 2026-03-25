package it.avlp.simog.garamanager.app;

import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avcp.simog.managers.invitati.InvitatiManager;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InvitatoBean;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.SoglieImpEnum;
import it.avlp.simog.beans.StazioneAppaltante;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.CIG_STORIA;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.GenericUtilValidator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SrvVisualizzaGareEXT extends ServletBase {

	public void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		
		perform(request, response);
	}

	private static final long serialVersionUID = -4427708044851647738L;
	
	/**
	 * @see ServletBase#perform(HttpServletRequest, HttpServletResponse)
	 */
	public void perform(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		HttpSession currentActiveSession = request.getSession();
		if ( checkSession(request) ) {
			if ( ! currentUser.isRSSA() ) {
				visualizzaListaParametriValori(request, response);
				
				int startRow = 0;

				String action = request.getParameter(ParametriServlet.ACTION_GET_LIST);
				//CF: if obbligatorio per il corretto funzionamento del tasto "ritorna" nella pagina "Dettaglio Gara" 
				if(action == null || !action.equals(ESPORTAELENCO))
				currentActiveSession.setAttribute(ParametriServlet.STORIA_PAGINAZIONE,this.getQueryString(request));
				
				boolean actionNeeded = action != null;

				if ( actionNeeded ) {
					String startRowS = request.getParameter(ParametriServlet.START_ROW);
					startRow = Integer.parseInt(startRowS);
					if ( action.equalsIgnoreCase(ParametriServlet.REGRESS) ) {
						startRow = startRow - configuration.getMaxElementiPerPagina();
					} else if( action.equalsIgnoreCase(PROGRESS)){ //CF aggiunto controllo if. In questo quando viene premuto il tasto 
						//"EsportaElenco", la variabile startRow mantiene il valore passotogli dalla jsp con valore 0.
						startRow = startRow + configuration.getMaxElementiPerPagina();
					}
				}
				
				String dataPubblicazione_da = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_DATA_PUBBLICAZIONE_START));
				logger.debug ( "dataPubblicazione_da [" + dataPubblicazione_da + "]" );			
				
				String dataPubblicazione_a = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_DATA_PUBBLICAZIONE_END));	
				logger.debug ( "dataPubblicazione_a [" + dataPubblicazione_a + "]" );			
				
				String dataScadenza_da = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_SCADENZA_START));
				logger.debug ( "dataScadenza_da [" + dataScadenza_da + "]" );
				
				String dataScadenza_a = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_SCADENZA_END));
				logger.debug ( "dataScadenza_a [" + dataScadenza_a + "]" );
				
				String cig = request.getParameter(ParametriServlet.FIELD_NAME_CIG);
			
				String oggettoGara = request.getParameter(ParametriServlet.FIELD_NAME_OGGETTO_GARA);
				String oggettoLotto = request.getParameter(ParametriServlet.FIELD_NAME_OGGETTO_LOTTO);
				String idStazioneAppaltante = request.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
				String numeroGara = request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA);
                if (numeroGara!=null) numeroGara = numeroGara.trim();

				String richiestaAnnullamento = request.getParameter(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO);
				String id_soglia = request.getParameter(ParametriServlet.ID_SOGLIA_IMPORTO);
				
                String cfAmm = request.getParameter(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);

				String fromRicerca = request.getParameter(ParametriServlet.FROM_RICERCA);
				request.setAttribute(ParametriServlet.FROM_RICERCA, fromRicerca);

				if ( request.getParameter("chiChiama") != null ) {
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
					currentActiveSession.setAttribute(ParametriServlet.ID_SOGLIA_IMPORTO, id_soglia );
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE, cfAmm );
					//*
				}
	
				cig = (String)currentActiveSession.getAttribute(FIELD_NAME_CIG);
				oggettoGara = (String)currentActiveSession.getAttribute(FIELD_NAME_OGGETTO_GARA);
				oggettoLotto  = (String)currentActiveSession.getAttribute(FIELD_NAME_OGGETTO_LOTTO);
				idStazioneAppaltante = (String)currentActiveSession.getAttribute(FIELD_NAME_ID_STAZIONE_APPALTANTE);
				numeroGara = (String)currentActiveSession.getAttribute(FIELD_NAME_ID_GARA);
                if (numeroGara!=null) numeroGara = numeroGara.trim();

				dataPubblicazione_da = (String)currentActiveSession.getAttribute(SESSION_DATA_PUBBLICAZIONE_LOTTO_DA);
				dataPubblicazione_a = (String)currentActiveSession.getAttribute(SESSION_DATA_PUBBLICAZIONE_LOTTO_A);
				dataScadenza_da = (String)currentActiveSession.getAttribute(SESSION_DATA_SCADENZA_DA);
				dataScadenza_a = (String)currentActiveSession.getAttribute(SESSION_DATA_SCADENZA_A);
				richiestaAnnullamento = (String)currentActiveSession.getAttribute(ParametriServlet.FIELD_NAME_RICHIESTA_ANNULLAMENTO);
				id_soglia = (String)currentActiveSession.getAttribute(ID_SOGLIA_IMPORTO);
				cfAmm = (String)currentActiveSession.getAttribute(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
				//*
				
				currentActiveSession.removeAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE);
				currentActiveSession.removeAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE);
				currentActiveSession.removeAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG);
				currentActiveSession.removeAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG_SCHEDE);
				
				try {
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					
					// controllo parametri immessi

					GenericUtilValidator val = new GenericUtilValidator(currentActiveConnection, logger);
					 
					if(val.isEmpty(oggettoGara) && val.isEmpty(oggettoLotto) && val.isEmpty(cig)
						&& val.isEmpty(numeroGara) && val.isEmpty(dataScadenza_a) && val.isEmpty(dataScadenza_da) 
						&& val.isEmpty(dataPubblicazione_a) && val.isEmpty(dataPubblicazione_da) && val.isEmpty(richiestaAnnullamento) 
						&& val.isEmpty(id_soglia) && val.isEmpty(idStazioneAppaltante)
						&& val.isEmpty(cfAmm)
					)
					{
						sendError(request, response, Messaggi.SIMOG_RIC_003, JSP_GESTIONE_GARE_EXT);
						return;
					}

					// Validazione del Numero Gara
					if ( numeroGara != null ) {
						if (numeroGara.length() > 8 || !PageHelper.isNumeric(numeroGara)){
							sendError(request, response, SIMOG_GARA_018, JSP_GESTIONE_GARE_EXT);
							return;
						}
					}	
					
                    if ( cig != null && !"".equals(cig)) {
                       if ( !PageHelper.isValidCIG(cig)){
							if(dataPubblicazione_da != null && dataPubblicazione_a != null ) {
								currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE, true);
							}
							if(dataScadenza_da != null && dataScadenza_a != null ) {
								currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, true);
							}
							sendError(request, response, SIMOG_GARA_010, JSP_GESTIONE_GARE_EXT);
							return;
						}	
					}
					
					if((dataPubblicazione_da != null && dataPubblicazione_a != null ) && dataPubblicazione_a.compareTo( dataPubblicazione_da ) < 0 	) 
					{
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE, true);
						if(dataScadenza_da != null && dataScadenza_a != null) {
							currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, true);
						}
						sendError(request, response, SIMOG_GARA_011, JSP_GESTIONE_GARE_EXT);
						return;
					}	// se le date sono inserite e superano la validazione
					else if((dataPubblicazione_da != null && dataPubblicazione_a != null ) && dataPubblicazione_a.compareTo( dataPubblicazione_da ) >= 0 ) {
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE, true);
						
					}
					else {	// se non sono inserite
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE, false);
					}
					
					if((dataScadenza_da != null && dataScadenza_a != null ) && dataScadenza_a.compareTo( dataScadenza_da ) < 0 ) {
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, true);
						sendError(request, response, SIMOG_GARA_012, JSP_GESTIONE_GARE_EXT);
						return;
					}	//  se le date sono inserite e superano la validazione
					else if((dataScadenza_da != null && dataScadenza_a != null ) && dataScadenza_a.compareTo( dataScadenza_da ) >= 0 ) {
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, true);						
					}	//se non sono inserite
					else {
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, false);
					}
										
					Hashtable listaSARiferimento = new Hashtable();
					
					if ( idStazioneAppaltante != null && ! "".equalsIgnoreCase( idStazioneAppaltante.trim() )  ) {
						logger.debug("Ricerca Gare relative a stazione appaltante [" + idStazioneAppaltante + "]");
						StazioneAppaltante staz = new StazioneAppaltante();
						
						if(currentUser.getUffici().containsKey(idStazioneAppaltante)){
							staz = (StazioneAppaltante)currentUser.getUffici().get(idStazioneAppaltante);
							
						}
						listaSARiferimento.put(idStazioneAppaltante, staz);
					}
	
					//Recupero soglie min e max importo
					SoglieImpEnum soglia = SoglieImpEnum.getEnumByCodice(id_soglia);
					
					TableBean garaList = null;
					//Nel table bean vengono stampate solo i primi 1000 record trovati
					int start;
					if(!ESPORTAELENCO.equals(action))
						start=configuration.getMaxElementiPerPagina();
					else 
						start=configuration.getELEMENTI_EXPORT();
					
					GaraManager garaManager = new GaraManager(currentActiveConnection, logger);				
	
					logger.debug("Esecuzione GaraList");
	
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
							false,
							richiestaAnnullamento,null, null, null,
							soglia != null ? soglia.min() : null,
							soglia != null ? soglia.max() : null, 
							cfAmm, null);
					
					if ( garaList.size() == 0 ) {
						sendMessage(request, response, Messaggi.SIMOG_RIC_001, JSP_GESTIONE_GARE_EXT);
						return;
					}
					else{
						String indiceGara=null;
						TableBeanRow currentRow=null;
						InvitatiManager invitatiManager = new InvitatiManager(currentActiveConnection, logger);
						ArrayList<InvitatoBean> listaInvitati= new ArrayList<InvitatoBean>();
						
						String previousGara = null;
						//gm aggiunto anche lo storico delle pubblicazioni
						List <PubblicazioneBean> storicoPubblicazioni = new ArrayList<PubblicazioneBean>();
						PubblicazioneManager pubManager = new PubblicazioneManager(currentActiveConnection, logger);
						InfoComuniManager icm = new InfoComuniManager(currentActiveConnection,logger);
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
								
					    		if(currentRow.getNulledField(INFO_AGGIUDICAZIONI.ID_INFO)!=null && 
										!"".equals(currentRow.getNulledField(INFO_AGGIUDICAZIONI.ID_INFO)) && 
										!"-1".equals(currentRow.getNulledField(INFO_AGGIUDICAZIONI.ID_INFO))) {
					    			long idInfoAgg = Long.parseLong(currentRow.getNulledField(INFO_AGGIUDICAZIONI.ID_INFO));
									Timestamp dataInizioInfo = PageHelper.parseTime(currentRow.getNulledField(INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO));
									InfoComuniBean icbean = icm.load(idInfoAgg, dataInizioInfo);
									
									currentRow.addFieldValue("DC_ESITO_PROCEDURA", icbean.getEsitoProcedura());	
									currentRow.addFieldValue("DC_STATO", String.valueOf(icbean.getIdStato()));	
									
					    		}else {
					    			currentRow.addFieldValue("DC_ESITO_PROCEDURA", "");
						    		currentRow.addFieldValue("DC_STATO", "");	
					    		}
								currentRow.addFieldValue("HAS_AGGIUDICAZIONE", "");
					    		currentRow.addFieldValue("HAS_STIPULA", "");
					    		
						 }
					}
					request.setAttribute(ParametriServlet.TABLEBEAN, garaList);
					request.setAttribute(ParametriServlet.START_ROW, new Integer(startRow) );
					request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getELEMENTI_VISUALIZZATI());
					request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_EXPORT, configuration.getELEMENTI_EXPORT());
					//Se la action è "ESPORTAELENCO" viene richiamato il metodo "performCSV" che si occupa di creare il file .excel
					//contenente la lista di gare e lotti desiderata
					if (action!=null && action.equals(ESPORTAELENCO)) {	
						int limit = garaList.getFullSize();
						if(limit > configuration.getELEMENTI_EXPORT())
							limit = configuration.getELEMENTI_EXPORT();
						TableBean outBean = new TableBean();
						outBean.put(GARA.IMPORTO_GARA, garaList.getColumn(GARA.IMPORTO_GARA));
						outBean.put(GARA.DENOM_STAZIONE_APPALTANTE, garaList.getColumn(GARA.DENOM_STAZIONE_APPALTANTE));
						outBean.put(GARA.CF_AMMINISTRAZIONE, garaList.getColumn(GARA.CF_AMMINISTRAZIONE));
						outBean.put(GARA.ID_MODO_GARA, garaList.getColumn(GARA.ID_MODO_GARA));
						outBean.put(GARA.CIG_ACC_QUADRO, garaList.getColumn(GARA.CIG_ACC_QUADRO));
						outBean.put(GARA.DATA_PERFEZIONAMENTO_BANDO, garaList.getColumn(GARA.DATA_PERFEZIONAMENTO_BANDO));
						outBean.put(GARA.ID_STAZIONE_APPALTANTE, garaList.getColumn(GARA.ID_STAZIONE_APPALTANTE));
						outBean.put(GARA.DATA_CREAZIONE, garaList.getColumn(GARA.DATA_CREAZIONE));
						outBean.put(GARA.ID_MODO_REAL, garaList.getColumn(GARA.ID_MODO_REAL));
						outBean.put(GARA.TIPO_SCHEDA_GARA, garaList.getColumn(GARA.TIPO_SCHEDA_GARA));
						outBean.put(GARA.ID_GARA, garaList.getColumn(GARA.ID_GARA));
						outBean.put(GARA.DENOM_AMMINISTRAZIONE, garaList.getColumn(GARA.DENOM_AMMINISTRAZIONE));
						outBean.put(GARA.IMPORTO_SA_GARA, garaList.getColumn(GARA.IMPORTO_SA_GARA));
						outBean.put(GARA.OGGETTO, garaList.getColumn(GARA.OGGETTO));
						
						outBean.put(STATI_SCHEDA.DESCRIZIONE, garaList.getColumn("STATOSCHEDA"));
						outBean.put(CIG_STORIA.APPLICAZIONE, garaList.getColumn(CIG_STORIA.APPLICAZIONE));
						outBean.put(INFO_AGGIUDICAZIONI.CF_RUP, garaList.getColumn(INFO_AGGIUDICAZIONI.CF_RUP));
						
						outBean.put(LOTTO.SOMMA_URGENZA, garaList.getColumn(LOTTO.SOMMA_URGENZA));
						outBean.put(LOTTO.DATA_PUBBLICAZIONE, garaList.getColumn(LOTTO.DATA_PUBBLICAZIONE)); 
						outBean.put(LOTTO.LUOGO_NUTS, garaList.getColumn(LOTTO.LUOGO_NUTS));						
						outBean.put(LOTTO.ID_SCELTA_CONTRAENTE, garaList.getColumn(LOTTO.ID_SCELTA_CONTRAENTE));
						outBean.put(LOTTO.DATA_INIB_PAGAMENTO, garaList.getColumn(LOTTO.DATA_INIB_PAGAMENTO));
						outBean.put(LOTTO.FLAG_ESCLUSO, garaList.getColumn(LOTTO.FLAG_ESCLUSO));				
						outBean.put(LOTTO.ID_ESCLUSIONE, garaList.getColumn(LOTTO.ID_ESCLUSIONE));			
						outBean.put(LOTTO.TRIENNIO_ANNO_INIZIO, garaList.getColumn(LOTTO.TRIENNIO_ANNO_INIZIO));
						outBean.put(LOTTO.TRIENNIO_ANNO_FINE, garaList.getColumn(LOTTO.TRIENNIO_ANNO_FINE));				
						outBean.put(LOTTO.DATA_SCADENZA_PAGAMENTI, garaList.getColumn(LOTTO.DATA_SCADENZA_PAGAMENTI));	
						outBean.put(LOTTO.IMPORTO_LOTTO, garaList.getColumn(LOTTO.IMPORTO_LOTTO));			
						outBean.put(LOTTO.ANNUALE_CUI_MININF, garaList.getColumn(LOTTO.ANNUALE_CUI_MININF));
						outBean.put(LOTTO.CIG_KKK, garaList.getColumn(LOTTO.CIG_KKK));						
						outBean.put(LOTTO.TIPO_CONTRATTO_LOTTO, garaList.getColumn(LOTTO.TIPO_CONTRATTO_LOTTO));	
						outBean.put(LOTTO.DATA_CREAZIONE_LOTTO, garaList.getColumn(LOTTO.DATA_CREAZIONE_LOTTO));
						outBean.put(LOTTO.LUOGO_ISTAT, garaList.getColumn(LOTTO.LUOGO_ISTAT));
						outBean.put(LOTTO.DATA_CANCELLAZIONE_LOTTO, garaList.getColumn(LOTTO.DATA_CANCELLAZIONE_LOTTO));
						outBean.put(LOTTO.TABLE_NAME + LOTTO.OGGETTO, garaList.getColumn(LOTTO.TABLE_NAME + LOTTO.OGGETTO));
						outBean.put(LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA, garaList.getColumn(LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA));
						outBean.put(LOTTO.CIG, garaList.getColumn(LOTTO.CIG));
						outBean.put(LOTTO.IMPORTO_IMPRESA, garaList.getColumn(LOTTO.IMPORTO_IMPRESA));						
						outBean.put(LOTTO.TRIENNIO_PROGRESSIVO, garaList.getColumn(LOTTO.TRIENNIO_PROGRESSIVO));
						
						outBean.setFullSize(garaList.getFullSize());
						outBean.setRowsAdded(limit);
						
						
					
						performCSV( request, response, outBean);
						//performCSV( request, response, garaList);
						
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
	
				} catch ( Exception sqle ) {
					sendError(request, response, sqle.toString(), JSP_GESTIONE_GARE_EXT, sqle);
					return;
				} finally {
					closeConnection(request.getSession().getId(),getClass().getName());
				}
			} else {
				sendError(request, response, SIMOG_LOGIN_004, JSP_ERRORE );
				return;
			}
		} else {
			sendError(request, response, SIMOG_LOGIN_003, JSP_ERRORE );
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
	    
		results.writeCSV(wrt,';');
		
		wrt.flush();
		wrt.close();
	}
	

		
	
}