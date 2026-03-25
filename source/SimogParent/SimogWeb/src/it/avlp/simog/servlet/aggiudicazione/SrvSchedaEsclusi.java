package it.avlp.simog.servlet.aggiudicazione;

import it.avcp.simog.managers.aggiudicazione.MultilottoManager;
import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avlp.simog.actions.aggiudicazione.InfoComuniAction;
import it.avlp.simog.actions.aggiudicazione.Scheda_A_Action;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.NavigationBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.common.action.AggiudicatarioAction;
import it.avlp.simog.common.action.BaseRichiestaAnnullamento;
import it.avlp.simog.common.action.CupLottoAggAction;
import it.avlp.simog.common.action.ResponsabileAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriCup;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.factory.AnnullamentoFactory;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.factory.ValidatorFactory;
import it.mef.serviziCUP.ElaborazioniCUPClient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SrvSchedaEsclusi extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (checkSession(request)){
			InfoGaraBean igb = getDatiGara(request.getSession());
			try{
				long idAggiudicazione = Long.parseLong(request.getParameter(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE));
				Timestamp dataInizioAggiudicazione = PageHelper.parseTime(request.getParameter(PSBD.DATA_INIZIO_AGGIUDICAZIONE));
				igb.setIdAggiudicazione(idAggiudicazione);
				igb.setDataInizioAggiudicazione(dataInizioAggiudicazione);
				setDatiGara(igb, request.getSession());
			}catch (Exception e) {
				logger.debug(e);
			}
			perform(request, response);
		}
		else {
			sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			return;
		}
	}

	protected void perform(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if (checkSession(request)){
			if (currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA() ){	
				visualizzaListaParametriValori(request, response);
				String pagina=ParametriServlet.JSP_ESCLUSI;
				String tab = request.getParameter(PSBD.TAB);
				Timestamp dataInizioScheda = null;

				InfoGaraBean infoGara = getDatiGara(request.getSession());
				// Luca In infogara ho l'importo del lotto	come importoLotto ma è null
				
				try{
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					currentActiveConnection.setAutoCommit(false);
					
					request.setAttribute(PSBD.NOME_SCHEDA,IdentificativoSchede.getEscluso().getDecodificaVN()) ;
					
					InfoComuniBean icb = (new InfoComuniAction(currentActiveConnection,logger)).load(infoGara.getIdInfo(), infoGara.getDataInizioInfo());
					String actionType = request.getParameter(PSBD.ACTION_TYPE);
					if(actionType == null  || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA())
						actionType = PSBD.ACTION_LOAD;
					Scheda_A_Action saAction = new Scheda_A_Action(currentActiveConnection, logger);
					Scheda_A saBean = new Scheda_A();
					SimogValidator validator = ValidatorFactory.getValidator(IdentificativoSchede.TAB_ESCLUSI, currentActiveConnection, logger);
					
					String idLotto = Long.toString(infoGara.getIdLotto());

					saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte(), false, infoGara.getIdLotto());
					if(saBean != null && saBean.getAggiudicazione() != null && saBean.getAggiudicazione().getIdAggiudicazione() > 0)
						saBean.setNavigationBean(getNavBean(infoGara.getIdAggiudicazione(), IdentificativoSchede.TAB_ESCLUSI, request.getSession()));
					
					if(PSBD.ACTION_HST_SCHEDA.equalsIgnoreCase(actionType)){

						// cerco la scheda indicata nel parametro ricevuto, ignoro lo stato
						String vid = request.getParameter("vid");
						String vdt = request.getParameter("vdt");
						
						saBean = saAction.load(Long.parseLong(vid), Timestamp.valueOf(vdt), infoGara.getTipoEnte(), true, infoGara.getIdLotto());
						saBean.setInfoComuni(icb);
						saBean.setInfoGara(infoGara);

						dataInizioScheda = saBean.getAggiudicazione().getDataInizioAggiudicazione();
						
						request.setAttribute("protect", "S");
					}
					else if(PSBD.ACTION_RIAGGIUDICAZIONE.equals(actionType)){
						saBean.setNavigationBean(new NavigationBean());
						saBean.getAggiudicazione().setProgCuiRiaggiudicato(saBean.getAggiudicazione().getProgCUI());
						saBean.getAggiudicazione().setProgCUI(0);
						saBean.getAggiudicazione().setIdAggiudicazione(0);
						saBean.getAggiudicazione().setIdStato(0);
						saBean.getAggiudicazione().setDescrizioneStato(null);
						saBean.getNavigationBean().setRiaggiudicata(true);
						saBean.getAggiudicazione().setDataInizioAggiudicazione(null);
						saBean.getAggiudicazione().setImportoAggiudicazione(null);
						saBean.getAggiudicazione().setPercOffAumento(null);
						saBean.getAggiudicazione().setPercRibassoAgg(null);
						saBean.getAggiudicazione().setDataVerbaleAggiudicazione(null);
						saBean.getAggiudicazione().setFlagRichSubappalto(null);
						saBean.getAggiudicazione().setFlagRichSubappalto(null);
						saBean.getPrestazioni().clear();
						saBean.getResponsabili().clear();
						saBean.getAggiudicatari().clear();
						infoGara.setIdAggiudicazione(-1);
						infoGara.setDataInizioAggiudicazione(null);
						
					}
					else if(PSBD.ACTION_VARIAZIONI_ANAGRAFICHE.equalsIgnoreCase(actionType)){
						boolean doVariazione = Boolean.parseBoolean(request.getParameter(PSBD.VAR_ANN));
//						saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte());
						saBean.setInfoComuni(icb);
						saBean.setInfoGara(infoGara);

						if(doVariazione == false){
							request.setAttribute(PSBD.VAR_ANN, true);
//							saBean.getAggiudicazione().setIdStato(StatiScheda.IN_DEFINIZIONE);
							saBean.getAggiudicazione().setDescrizioneStato(StatiScheda.VARIAZIONE_CO_STRING);
							saBean.getAggiudicazione().setIdMotivoVarCO(null);
						}
						else {
							saBean = saAction.getBean(request, PSBD.SEZIONE_RE);
							saBean.setInfoComuni(icb);
							saBean.setInfoGara(infoGara);
							
							// PP B302.2.0 imposto il flag per controllare la validazione
							saBean.getAggiudicazione().setValidaVariazione(true);
	
							if(validator.valida(saAction.esplodiAggiudicatari(saBean), IdentificativoSchede.TAB_ESCLUSI)){
							
								String motivazione = StatiScheda.VARIAZIONE_CO_STRING;
								
								RichiestaAnnullamento bean = new RichiestaAnnullamento();
								bean.setId_lotto(idLotto);
								bean.setMotivo_richiesta(motivazione);
								bean.setRichiedente(currentUser.getLogin());
								boolean successo = false;
								
								bean.setId_record(Long.toString(infoGara.getIdAggiudicazione()));
								bean.setData_inizio_record(infoGara.getDataInizioAggiudicazione());
								Timestamp nuovadata = null;
			
								
								bean.setBlocco(IdentificativoSchede.TAB_ESCLUSI);
								nuovadata = saAction.gestisciVariazioniCO(saBean, bean,  currentUser.getLogin(),infoGara.getTipoEnte());
								successo = nuovadata != null;
								if(successo){
									
									commit(currentActiveConnection);
									infoGara.setDataInizioAggiudicazione(nuovadata);
									validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_083);
									
								}
								else{
									rollback(currentActiveConnection);
									validator.getEccezioni().addValidationErr(SIMOG_VARIAZIONE_CO_001);				
								}
								saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte(), false, infoGara.getIdLotto());
								logger.info("variazione c.o. terminata");
							} else {
								request.setAttribute(PSBD.VAR_ANN, true);
	//							saBean.getAggiudicazione().setIdStato(StatiScheda.IN_DEFINIZIONE);
								saBean.getAggiudicazione().setDescrizioneStato(StatiScheda.VARIAZIONE_CO_STRING);
							}
						}
					}
					
					else if(PSBD.ACTION_LOAD.equalsIgnoreCase(actionType)){
//						saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte());
						saBean.setInfoComuni(icb);
						saBean.setInfoGara(infoGara);

						// PP precompilazione data scadenza presentazione offerta dal lotto se nuova aggiudicazione
						if(saBean.getAggiudicazione().getIdAggiudicazione() == 0){
							saBean.getAggiudicazione().setDataScadenzaPresOfferta(PageHelper.getViewDate(infoGara.getDataScadenzaPagamenti()));
						}
						dataInizioScheda = saBean.getAggiudicazione().getDataInizioAggiudicazione();
						if( infoGara.getIdAggiudicazione() > 0)
							validator.valida(saBean, IdentificativoSchede.TAB_ESCLUSI);
					
					}
					
					// Salva
					else if (PSBD.ACTION_SALVA.equalsIgnoreCase(actionType)) {
						saBean = saAction.getBean(request, PSBD.SEZIONE_RE);
						saBean.setInfoComuni(icb);
						saBean.setInfoGara(infoGara);
						
						
					
						if(saBean.getAggiudicazione().getIdAggiudicazione() < 1 && isRefresh(request)){						
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
							
							saAction.sendValidations(request, validator.getEccezioni());
							
							forward(ParametriServlet.JSP_ERRORE, request, response);
							return;
						}
						
						// se la scheda esiste impedisco l'operazione
						if(saBean.getAggiudicazione().getIdAggiudicazione() < 1 && saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte(), false, infoGara.getIdLotto()).getAggiudicazione().getIdAggiudicazione()>0){						
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
					
							saAction.sendValidations(request, validator.getEccezioni());
							
							forward(ParametriServlet.JSP_ERRORE, request, response);
							return;
						}

                  boolean esito = validator.valida(saAction.esplodiAggiudicatari(saBean), IdentificativoSchede.TAB_ESCLUSI);
                  
                  if( SimogFlags.is3031_RFWEBGL02Active() 
                        && !configuration.isCUPLotto(PageHelper.getFormattedDBDate(saBean.getInfoGara().getDataCreazioneGara()))
                        && configuration.isCUPAttivo()){

                     // Per ogni CUP verifico la situazione
                     ElaborazioniCUPClient cli = new ElaborazioniCUPClient(configuration, logger);
                     Lotto lt = new Lotto();
                     lt.setElencoCup(saBean.getElencoCup());
                   //MAC 36301 3.04.8 cambiato da validaCupDIPE a validaCupDIPEAgg
                     AllValidationBeans eccez = cli.validaCupDIPEAgg(lt, false);
                     if(eccez != null)
                        validator.getEccezioni().add(eccez);
                     
                     // se ci sono errori non salvo
                     if(validator.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() > 0)
                        esito = false;
                  }

						if(esito){
							saBean.getAggiudicazione().setSottotipo(TipoAggiudicazione.E);
							if(infoGara.getIdAggiudicazione() < 1){
								
								saAction.create(saBean, getModifiedFlags(request), currentUser.getLogin());
								infoGara.setIdAggiudicazione(saBean.getAggiudicazione().getIdAggiudicazione());
								infoGara.setDataInizioAggiudicazione(saBean.getAggiudicazione().getDataInizioAggiudicazione());
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_023);
							}
							else {
								saAction.save(saBean, getModifiedFlags(request), currentUser.getLogin());
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_024);
							}
							commit(currentActiveConnection);
							saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte(), false, infoGara.getIdLotto());
						}
												
					} 
					// Conferma
					else if (PSBD.ACTION_CONFERMA.equalsIgnoreCase(actionType)) {
						saBean = saAction.getBean(request, PSBD.SEZIONE_RE);
						saBean.setInfoComuni(icb);
						saBean.setInfoGara(infoGara);

						boolean esito = validator.valida(saAction.esplodiAggiudicatari(saBean), IdentificativoSchede.TAB_ESCLUSI);
                 
						if( SimogFlags.is3031_RFWEBGL02Active() 
                        && !configuration.isCUPLotto(PageHelper.getFormattedDBDate(saBean.getInfoGara().getDataCreazioneGara()))
                        && configuration.isCUPAttivo()){

                     // Per ogni CUP verifico la situazione
                     ElaborazioniCUPClient cli = new ElaborazioniCUPClient(configuration, logger);
                     Lotto lt = new Lotto();
                     lt.setElencoCup(saBean.getElencoCup());
                     //MAC 36301 3.04.8 cambiato da validaCupDIPE a validaCupDIPEAgg
                     AllValidationBeans eccez = cli.validaCupDIPEAgg(lt, true);
                     if(eccez != null)
                        validator.getEccezioni().add(eccez);
                     
                     CupLottoAggAction claAction = new CupLottoAggAction(currentActiveConnection, logger);
                     claAction.settingDatiDIPE(lt.getElencoCup());
                     
                     // non posso confermare se ci sono codici non confermati esplicitamente
                     eccez = claAction.validaCodiciCUPConf(lt);
                     if(eccez != null)
                        validator.getEccezioni().add(eccez);
                     
                     // se ci sono errori non salvo
                     if(validator.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() > 0)
                        esito = false;
                  }

						if(esito){
							saBean.getAggiudicazione().setSottotipo(TipoAggiudicazione.E);
							saAction.confirm(saBean, getModifiedFlags(request), currentUser.getLogin());
							commit(currentActiveConnection);
							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_025);
							saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte(), false, infoGara.getIdLotto());
						}
																		
					} 
					// Jsp Annullamento
					else if (PSBD.ACTION_CARICA_JSP_ANNULLAMENTO.equalsIgnoreCase(actionType)) {
						
						boolean isOk = (saAction.bsa.hasSchedaRichDelete(String.valueOf(infoGara.getIdAggiudicazione()), IdentificativoSchede.TAB_ESCLUSI, false).getFullSize()==0);
						
						if (isOk){
							request.setAttribute(PSBD.TAB, IdentificativoSchede.TAB_ESCLUSI);
							String dest = ParametriServlet.JSP_RICHIEDI_ANNULLAMENTO + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGara.getIdAggiudicazione();
							dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGara.getDataInizioAggiudicazione();
							forward(dest, request, response);
							return;						
						}
						else{
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					// Jsp Cancellazione
					else if (PSBD.ACTION_CARICA_JSP_CANCELLAZIONE.equalsIgnoreCase(actionType)) {
						
						boolean isOk = (saAction.bsa.hasSchedaRichDelete(String.valueOf(infoGara.getIdAggiudicazione()), IdentificativoSchede.TAB_ESCLUSI, true).getFullSize()==0);

						if (isOk){
							request.setAttribute(PSBD.TAB, IdentificativoSchede.TAB_ESCLUSI);
							String dest = ParametriServlet.JSP_RICHIEDI_CANCELLAZIONE + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGara.getIdAggiudicazione();
							dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGara.getDataInizioAggiudicazione();
							forward(dest, request, response);
							return;						
						}
						else{
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					
					// Richiesta annullamento
					else if (PSBD.ACTION_RICHIESTA_ANNULLAMENTO.equalsIgnoreCase(actionType)) {
						//controllo per le aggiudicazioni multilotto
						String codiceContratto = saBean.getAggiudicazione().getCodiceContratto();
						if(codiceContratto!=null && !"".equals(codiceContratto)){
							MultilottoManager mm = new MultilottoManager(currentActiveConnection, logger);
							mm.eliminaGruppo(infoGara.getIdGara(), codiceContratto);
						}
						
						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_ANNULLAMENTO);
						
						RichiestaAnnullamento bean = new RichiestaAnnullamento();
						bean.setId_lotto(idLotto);
						bean.setMotivo_richiesta(motivazione);
						bean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						bean.setRichiedente(currentUser.getLogin());
						bean.setBlocco(IdentificativoSchede.TAB_ESCLUSI);
						boolean successo = false;
						
						bean.setId_record(Long.toString(infoGara.getIdAggiudicazione()));
						bean.setData_inizio_record(infoGara.getDataInizioAggiudicazione());
						Timestamp nuovadata = null;
	
						nuovadata = saAction.richiediAnnullamento(bean);
						
						successo = nuovadata != null;										
						
                        // 3.02.2.1 accettazione immediata richiesta
                        if(SimogFlags.is30230_RFWEBSC03Active()){
                           
                           infoGara.setDataInizioAggiudicazione(nuovadata);
                           
                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(bean.getBlocco(), currentActiveConnection, logger);
                           
                           bean.setDecisore(currentUser.getLogin());
                           bean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
                           bean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
                           
                           successo = annAction.gestisciRichiesta(bean,currentUser.getLogin());
                        
                           if(successo){
                              validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RICHIESTA_ANNULLAMENTO_002);
                              commit(currentActiveConnection);
                          }
                          else{
                              rollback(currentActiveConnection);
                              validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
                          }
                       }
                       else{                          
      						if(successo){
      							commit(currentActiveConnection);
      							infoGara.setDataInizioAggiudicazione(nuovadata);
      							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_080);
      							
      						}
      						else{
      							rollback(currentActiveConnection);
      							validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);				
      						}
                       }
						saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte(), false, infoGara.getIdLotto());
					}
					
					else if (PSBD.ACTION_RICHIESTA_CANCELLAZIONE.equalsIgnoreCase(actionType)) {
						//controllo per le aggiudicazioni multilotto
						String codiceContratto = saBean.getAggiudicazione().getCodiceContratto();
						if(codiceContratto!=null && !"".equals(codiceContratto)){
							MultilottoManager mm = new MultilottoManager(currentActiveConnection, logger);
							mm.eliminaGruppo(infoGara.getIdGara(), codiceContratto);
						}
						
						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_CANCELLAZIONE);
						
						RichiestaAnnullamento bean = new RichiestaAnnullamento();
						bean.setCancellazione(request.getParameter(PSBD.CANC_COMPLETA)) ;
						bean.setId_lotto(idLotto);
						bean.setMotivo_richiesta(motivazione);
						bean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						bean.setRichiedente(currentUser.getLogin());
						bean.setId_record(Long.toString(infoGara.getIdAggiudicazione()));
						bean.setData_inizio_record(infoGara.getDataInizioAggiudicazione());
						bean.setBlocco(IdentificativoSchede.TAB_ESCLUSI);
						
						saAction.richiediCancellazione(bean);
						
                        // 3.02.2.1 accettazione immediata richiesta
                        if(SimogFlags.is30230_RFWEBSC03Active()){
                           boolean successo;

                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(bean.getBlocco(), currentActiveConnection, logger);
                            
                           bean.setDecisore(currentUser.getLogin());
                           bean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
                           bean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
                           
                           successo = annAction.gestisciRichiestaCancellazione(bean,currentUser.getLogin());
                    
                           if(successo){
                              validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RICHIESTA_CANCELLAZIONE_002);
                               commit(currentActiveConnection);
                               
                               // forward a lista aggiudicazioni
                               pagina = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA
                                   + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO+"="+bean.getId_lotto()
                                   + "&" + ParametriServlet.START_ROW + "=0"
                                   + "&" + "toDo=" + PSBD.ACTION_LOAD; // patch se va su daticomuni
                           }
                           else{
                               rollback(currentActiveConnection);
                               validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_CANCELLAZIONE_001);
                           }
                        }
                        else{
      						commit(currentActiveConnection);
      						validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_082);
                        }						
						saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte(), false, infoGara.getIdLotto());
	
					}
/*** PP vecchia					
					else if (PSBD.ACTION_CERCA.equalsIgnoreCase(actionType)) {						
						TableBean soggetti = null;
						if (PSBD.TAB_AFFIDATARIO.equalsIgnoreCase(tab) ) {
							logger.debug("ACTION:cerca affidatari - START");
							AggiudicatarioAction agg = new AggiudicatarioAction(currentActiveConnection,logger);
							soggetti = agg.cerca(request);	
							logger.debug("ACTION:cerca affidatari - END");
						}
						else{
							logger.debug("ACTION:cerca responsabili - START");
							ResponsabileAction resp = new ResponsabileAction(currentActiveConnection,logger);
							soggetti = resp.cerca(request,tab);
							logger.debug("ACTION:cerca responsabili - END");
						}
						pagina= "scheda1/popupRubrica.jsp";
						request.removeAttribute(PSBD.BLOCCO_DATI_SOGGETTI);
						
						request.setAttribute(PSBD.BLOCCO_DATI_SOGGETTI, soggetti);
					}
****/					
					saBean.setInfoComuni(icb);	/** Dopo ogni load di saBean, infoComuni si perde. Qui viene risettato **/

					if (saBean != null) {
						saBean.getAggiudicazione().setOkCancellazione(saAction.bsa.isCancellabile(IdentificativoSchede.TAB_ESCLUSI, 
								saBean.getAggiudicazione().getIdAggiudicazione(), 
								saBean.getAggiudicazione().getDataInizioAggiudicazione(),
								saBean.getAggiudicazione().getIdStato(),
								saBean.getInfoComuni().getFlagEnteSpeciale(), 
								saBean.getInfoComuni().getTipoContratto(),
								saBean.getAggiudicazione().getIdAggiudicazione(),
								saBean.getAggiudicazione().getDataInizioAggiudicazione()));
					}
					
					
					
					
					if(infoGara.getIdAggiudicazione() < 1){
						// preimposto i valori della eventuale pubblicazione bando (nuts,istat,importo sicurezza)
						if(infoGara.getLUOGO_ISTAT() != null && saBean.getAggiudicazione().getLuogoIstat() == null &&  saBean.getAggiudicazione().getLuogoNuts() == null)
							saBean.getAggiudicazione().setLuogoIstat(infoGara.getLUOGO_ISTAT());
						if(infoGara.getLUOGO_NUTS() != null  && saBean.getAggiudicazione().getLuogoIstat() == null &&  saBean.getAggiudicazione().getLuogoNuts() == null)
							saBean.getAggiudicazione().setLuogoNuts(infoGara.getLUOGO_NUTS());

						// PP 3.02.1.6
						if(infoGara.getIMPORTO_ATTUAZIONE_SICUREZZA() != null && saBean.getAggiudicazione().getImportoAttuazioneSicurezza() == null)					
							saBean.getAggiudicazione().setImportoAttuazioneSicurezza(infoGara.getIMPORTO_ATTUAZIONE_SICUREZZA());
					}
					
					/***************** DETTAGLI RICH ANN ************************/
					
					TableBean tabRichAnn = saAction.bsa.getRichAnnByScheda(
							String.valueOf(saBean.getAggiudicazione().getIdAggiudicazione()), 
							IdentificativoSchede.TAB_ESCLUSI, false);
					
					request.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
					
					/***************** DETTAGLI VAR ANAG ************************/				
					TableBean tabVarAnag = saAction.bsa.getVarAnagByScheda(String.valueOf(saBean.getAggiudicazione().getIdAggiudicazione()), 
							IdentificativoSchede.TAB_ESCLUSI);
					
					request.setAttribute(PSBD.TAB_VARANAG, tabVarAnag);


					/***************** END              *************************/
					
					//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
					if(saBean != null && saBean.getAggiudicazione().getIdStato() == StatiScheda.CONFERMATO
							&& !(PSBD.ACTION_VARIAZIONI_ANAGRAFICHE.equalsIgnoreCase(actionType) || PSBD.ACTION_VARIAZIONI_ANAGRAFICHE_SAVE.equalsIgnoreCase(actionType))
					)
						saAction.sendValidations(request, validator.getEccezioni().getAllInfoEWarn());
					else
						saAction.sendValidations(request, validator.getEccezioni());
					
					request.setAttribute("schedaEsclusi", saAction.implodiAggiudicatari(saBean));
					//request.setAttribute(ParametriServlet.TIPO_APPALTO_BEAN, saAction.loadComboTipoAppalto(request));
//					request.setAttribute(ParametriServlet.TIPO_APPALTO_BEAN_F, saAction.loadTipiAppaltoContratto(request, Costanti.TIPO_SCHEDA_FORNITURE ,dataInizioScheda));
//					request.setAttribute(ParametriServlet.TIPO_APPALTO_BEAN_L, saAction.loadTipiAppaltoContratto(request, Costanti.TIPO_SCHEDA_LAVORI,dataInizioScheda));
//					request.setAttribute(ParametriServlet.TIPO_PRESTAZIONE_BEAN, saAction.loadComboTipoPrestazione(request,dataInizioScheda));
					
					//TICKET ALM - 3.04.2 NG #2847 - Caricamento scelta contraente
					if(SimogFlags.is3042Active()) {
					    request.setAttribute(ParametriServlet.SCELTA_CONTRAENTE_BEAN, saAction.loadSceltaContraente(infoGara.getDataCreazioneGara(), infoGara.getCfAmministrazione(), infoGara.getIdOsservatorio()));
					} else 
						request.setAttribute(ParametriServlet.SCELTA_CONTRAENTE_BEAN, saAction.loadSceltaContraente(dataInizioScheda, infoGara.getCfAmministrazione(), infoGara.getIdOsservatorio()));
				
					request.setAttribute(ParametriServlet.CONDIZIONI_AGG_BEAN, saAction.loadCondizioniAggiuntive(infoGara.getDataCreazioneGara()));
//					request.setAttribute(ParametriServlet.CRITERI_AGGIUDICAZIONE_BEAN, saAction.loadCriteriAggiudicazione(dataInizioScheda));
//					request.setAttribute(ParametriServlet.MODO_INDIZIONE_GARA, saAction.loadModoIndizione(dataInizioScheda));
//					request.setAttribute(ParametriServlet.CATEGORIA_BEAN, saAction.loadCategoria(dataInizioScheda));
//					request.setAttribute(ParametriServlet.CLASSI_IMPORTO_BEAN, saAction.loadClasseImporto(dataInizioScheda));
					request.setAttribute(ParametriServlet.TIPO_AGGIUDICATARIO_BEAN, saAction.loadTipoAggiudicatario(dataInizioScheda));
					request.setAttribute(ParametriServlet.RUOLI_RESPONSABILE_BEAN, saAction.loadRuoliSezione(PSBD.SEZIONE_RE,dataInizioScheda));
//					request.setAttribute(ParametriServlet.RUOLI_PRESTAZIONE_BEAN, saAction.loadRuoliSezione(PSBD.SEZIONE_PA,dataInizioScheda));
//					request.setAttribute(ParametriServlet.TIPO_FINANZIAMENTO_BEAN, saAction.loadFinanziamenti(dataInizioScheda));
//					request.setAttribute(ParametriServlet.TIPO_STRUMENTO_BEAN, saAction.loadStrumenti(dataInizioScheda));

					// PP B302.2.0
					if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive())
						request.setAttribute(ParametriServlet.MOTIVO_VCO_BEAN, saAction.bsa.loadMotiviVCO(dataInizioScheda));
					else
						request.setAttribute(ParametriServlet.MOTIVO_VCO_BEAN, new HashMap<String,String>());

               if(// PP con la funziona integrazione questo controllo è eccessivo configuration.isCUPLotto(PageHelper.getFormattedDBDate(saBean.getInfoGara().getDataCreazioneGara())) && 
                     configuration.isCUPAttivo()){
                  if(infoGara.getIdAggiudicazione() <= 0){
                     
                     //Prevalorizzazione CUP dell'aggiudicazione con quelli del lotto (competenza lotto)
                     if( SimogFlags.is3031_RFWEBGL02Active() && saBean.getElencoCup() != null && saBean.getElencoCup().isEmpty()){
                        CupLottoAggAction claAction = new CupLottoAggAction(currentActiveConnection, logger);
                        List<CupLottoAggExt> elencoCup = claAction.getElencoCup(infoGara.getIdLotto(), null, null, false);
                        request.setAttribute(ParametriCup.PARAM_ELENCO_CUP, elencoCup);
                     }
                  }
               }              

					setDatiGara(infoGara, request.getSession());
					//TICKET ALM #3835
					setDataCreazione(infoGara.getDataCreazioneGara(), request.getSession());
					//FINE TICKET ALM #3835
					forward(pagina , request, response);
			
				
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
					rollback(currentActiveConnection);
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE, e );
					
					return;

				} finally {
				logger.debug("Chiudo connessione");
					closeConnection(request.getSession().getId(),getClass().getName());
				}			    
				
			}
			else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				return;
			}
		}
		else {
			sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			return;
		}
	}
	
	/*******************************************************************************
	 * Il metodo restituisce un vettore contenente elementi di valore "true"
	 * @param request HttpServletRequest
	 * @return Boolean[]
	 */
	private Boolean[] getModifiedFlags(HttpServletRequest request){
		int num = 8;
		Boolean[] result = new Boolean[num];
		for(int i = 0; i<num;i++){
//			String mod = request.getParameter("Modificato"+i);
//			if(mod != null)
//			  result[i] = mod.equals("1");
//			else result[i] = false;
			result[i] = true;
		}
		return result;		  
	}
}
