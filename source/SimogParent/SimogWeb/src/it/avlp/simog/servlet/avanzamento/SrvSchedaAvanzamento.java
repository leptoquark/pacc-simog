package it.avlp.simog.servlet.avanzamento;

import it.avlp.simog.actions.avanzamento.AvanzamentoAction;
import it.avlp.simog.actions.collaudo.CollaudoAction;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.avanzamento.SchedaAvanzamento;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.common.action.BaseRichiestaAnnullamento;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletAccordo;
import it.avlp.simog.common.servlet.ParametriServletAvanzamento;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.factory.AnnullamentoFactory;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.factory.ValidatorFactory;
//2.10 aggiunto
import it.avlp.simog.beans.variante.VarianteBean;
//2.10 fine
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SrvSchedaAvanzamento extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1290848013445345610L;

	@Override
	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if(checkSession(request)){
			if (currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()) {
				try{ 
										
					request.setAttribute(PSBD.NOME_SCHEDA,IdentificativoSchede.getAvanzamenti().getDecodificaVN()) ;
					
					visualizzaListaParametriValori(request, response);
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					currentActiveConnection.setAutoCommit(false);
					String action = request.getParameter("toDo");
					if(action == null)
						action = request.getParameter(PSBD.ACTION_TYPE);
					
					/** Consente solo l'operazione di caricamento all'Osservaotre regionale */
					if(action == null || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA())
						action = PSBD.ACTION_LOAD;
					
					HttpSession sessione = request.getSession();
					String pagina = null;
					
					AvanzamentoAction avanzamentoAction = new AvanzamentoAction(currentActiveConnection,logger);
					InfoGaraBean infoGara = getDatiGara(request.getSession());

					SchedaAvanzamento schedaAvanzamento = new SchedaAvanzamento();
					AvanzamentoBean bean = new AvanzamentoBean();
					schedaAvanzamento.setAvanzamentoFE(bean);
					AggiudicazioneBean aggBea = avanzamentoAction.getAggiudicazione(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
					schedaAvanzamento.setAggiudicazione(aggBea);
					InfoComuniBean infBea = avanzamentoAction.getInfoComuni(aggBea.getIdInfo(), aggBea.getDataInizioInfo());
					schedaAvanzamento.setInfoComuni(infBea);
					//set isAggiungibile
					
					//2.10 aggiunto setVarianti
					schedaAvanzamento.setVarianti(avanzamentoAction.getVarianti(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione()));
					//2.10 fine

					SimogValidator validator = ValidatorFactory.getValidator(ParametriServletAvanzamento.TAB_AVANZAMENTO, currentActiveConnection, logger);

					bean = avanzamentoAction.getBean(request,infoGara.getIdAggiudicazione(),infoGara.getDataInizioAggiudicazione());
					
					if ( bean != null ) {
						bean.setOkCancellazione(avanzamentoAction.bsa.isCancellabile(ParametriServletAvanzamento.TAB_AVANZAMENTO, 
								bean.getIdAvanzamento(), 
								bean.getDataInizioAvanzamento(),
								bean.getIdStato(),
								infBea.getFlagEnteSpeciale(), 
								infBea.getTipoContratto(),
								bean.getIdAggiudicazione(),
								bean.getDataInizioAggiudicazione()));					
					}
					
					if(PSBD.ACTION_SALVA.equalsIgnoreCase(action)){
						bean = avanzamentoAction.getBean(request, infoGara.getIdAggiudicazione(),infoGara.getDataInizioAggiudicazione());
						schedaAvanzamento.setAvanzamentoFE(bean);
						if(bean.getIdAvanzamento() < 1 && isRefresh(request)){
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
							pagina = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?"
							+ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + infoGara.getIdLotto() +
							"&"+ParametriServlet.FIELD_NAME_ID_INFO+"="+infoGara.getIdInfo()
							+"&"+ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO+"="+infoGara.getDataInizioInfo();
							
							avanzamentoAction.sendValidations(request, validator.getEccezioni());
							
							forward(pagina, request, response);
							return;
						}
						//change from simple bean to wrapper in input for validator
						if(validator.valida(schedaAvanzamento, null)){
							boolean inserimento = bean.getIdAvanzamento() < 1;
							avanzamentoAction.save(bean, currentUser.getLogin());
							commit(currentActiveConnection);
							if(inserimento)
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_023);
							else validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_024);
							bean = avanzamentoAction.loadOne(bean.getIdAvanzamento(), bean.getDataInizioAvanzamento());
							schedaAvanzamento.setAvanzamentoFE(bean);
						}
					}
					else if(PSBD.ACTION_CONFERMA.equalsIgnoreCase(action)){
						bean = avanzamentoAction.getBean(request, infoGara.getIdAggiudicazione(),infoGara.getDataInizioAggiudicazione());
						schedaAvanzamento.setAvanzamentoFE(bean);
						//change from simple bean to wrapper in input for validator
						if(validator.valida(schedaAvanzamento, null)){
							avanzamentoAction.confirm(bean, currentUser.getLogin());
							commit(currentActiveConnection);
							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_025);
							bean = avanzamentoAction.loadOne(bean.getIdAvanzamento(), bean.getDataInizioAvanzamento());
							schedaAvanzamento.setAvanzamentoFE(bean);
						}
					}
					else if(PSBD.ACTION_CARICA_JSP_ANNULLAMENTO.equalsIgnoreCase(action)){
						bean = avanzamentoAction.getBean(request,infoGara.getIdAggiudicazione(),infoGara.getDataInizioAggiudicazione());
						schedaAvanzamento.setAvanzamentoFE(bean);

						boolean isOk = (avanzamentoAction.bsa.hasSchedaRichDelete(String.valueOf(bean.getIdAvanzamento()), IdentificativoSchede.TAB_AVANZAMENTO, false).getFullSize()==0);
						if (isOk){
							request.setAttribute(PSBD.TAB, ParametriServletAvanzamento.TAB_AVANZAMENTO);
							request.setAttribute(ParametriServlet.SHOW_DATI_COMUNI, "true");
							String dest = ParametriServlet.JSP_RICHIEDI_ANNULLAMENTO + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGara.getIdAggiudicazione();
							dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGara.getDataInizioAggiudicazione();
							dest+="&" + ParametriServletAvanzamento.FIELD_NAME_ID_AVANZAMENTO + "=" + bean.getIdAvanzamento();
							dest+="&" + ParametriServletAvanzamento.FIELD_NAME_DATA_INIZIO_AVANZAMENTO + "=" + bean.getDataInizioAvanzamento();
							forward(dest, request, response);
							return;
						}
						else{
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					else if(PSBD.ACTION_CARICA_JSP_CANCELLAZIONE.equalsIgnoreCase(action)){
						bean = avanzamentoAction.getBean(request,infoGara.getIdAggiudicazione(),infoGara.getDataInizioAggiudicazione());
						schedaAvanzamento.setAvanzamentoFE(bean);

						boolean isOk = (avanzamentoAction.bsa.hasSchedaRichDelete(String.valueOf(bean.getIdAvanzamento()), IdentificativoSchede.TAB_AVANZAMENTO, true).getFullSize()==0);
						if (isOk){
							request.setAttribute(PSBD.TAB, ParametriServletAvanzamento.TAB_AVANZAMENTO);
							request.setAttribute(ParametriServlet.SHOW_DATI_COMUNI, "true");
							String dest = ParametriServlet.JSP_RICHIEDI_CANCELLAZIONE + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGara.getIdAggiudicazione();
							dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGara.getDataInizioAggiudicazione();
							dest+="&" + ParametriServletAvanzamento.FIELD_NAME_ID_AVANZAMENTO + "=" + bean.getIdAvanzamento();
							dest+="&" + ParametriServletAvanzamento.FIELD_NAME_DATA_INIZIO_AVANZAMENTO + "=" + bean.getDataInizioAvanzamento();
							forward(dest, request, response);
							return;
						}
						else{
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}					
					else if(PSBD.ACTION_RICHIESTA_ANNULLAMENTO.equalsIgnoreCase(action)){
						bean = avanzamentoAction.getBean(request, infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						schedaAvanzamento.setAvanzamentoFE(bean);
						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_ANNULLAMENTO);
						String idLotto = Long.toString(infoGara.getIdLotto());
						
						RichiestaAnnullamento raBean = new RichiestaAnnullamento();
						raBean.setId_lotto(idLotto);
						raBean.setMotivo_richiesta(motivazione);
						raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						raBean.setRichiedente(currentUser.getLogin());
						boolean successo = false;
						raBean.setId_record(Long.toString(bean.getIdAvanzamento()));
						raBean.setData_inizio_record(bean.getDataInizioAvanzamento());
						raBean.setBlocco(ParametriServletAvanzamento.TAB_AVANZAMENTO);
						Timestamp nuovadata = null;
						nuovadata = avanzamentoAction.richiestaAnnullamento(raBean);
						successo = nuovadata != null;
						
                        // 3.02.2.1 accettazione immediata richiesta
                        if(SimogFlags.is30230_RFWEBSC03Active()){
                           
                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(raBean.getBlocco(), currentActiveConnection, logger);
                           
                           raBean.setDecisore(currentUser.getLogin());
                           raBean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
                           raBean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
                           
                           successo = annAction.gestisciRichiesta(raBean,currentUser.getLogin());
                        
                           if(successo){
                              bean.setDataInizioAvanzamento(nuovadata);
                              validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RICHIESTA_ANNULLAMENTO_002);
                              commit(currentActiveConnection);
                              bean = avanzamentoAction.loadOne(bean.getIdAvanzamento(), bean.getDataInizioAvanzamento());
                          }
                          else{
                              rollback(currentActiveConnection);
                              validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
                              //Un bean vuoto con il numero progessivo settato
                              bean = new AvanzamentoBean(); 
                              bean.setNumeroAvanzamento(avanzamentoAction.getNextAvanzamento(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione()));
                          }
                       }
                       else{                          
      						if(successo){
      							commit(currentActiveConnection);
      							bean.setDataInizioAvanzamento(nuovadata);
      							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_080);
      							bean = avanzamentoAction.loadOne(bean.getIdAvanzamento(), bean.getDataInizioAvanzamento());
      						}
      						else{
      							rollback(currentActiveConnection);
      							validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
      							//Un bean vuoto con il numero progessivo settato
      							bean = new AvanzamentoBean(); 
      							bean.setNumeroAvanzamento(avanzamentoAction.getNextAvanzamento(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione()));
      						}					
                       }
      				
                        schedaAvanzamento.setAvanzamentoFE(bean);
					}
					else if(PSBD.ACTION_RICHIESTA_CANCELLAZIONE.equalsIgnoreCase(action)){
						bean = avanzamentoAction.getBean(request, infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						schedaAvanzamento.setAvanzamentoFE(bean);
						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_CANCELLAZIONE);
						String idLotto = Long.toString(infoGara.getIdLotto());
						
						RichiestaAnnullamento raBean = new RichiestaAnnullamento();
						raBean.setCancellazione(Costanti.FLAG_VALORE_NO) ;
						raBean.setId_lotto(idLotto);
						raBean.setMotivo_richiesta(motivazione);
						raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						raBean.setRichiedente(currentUser.getLogin());
						raBean.setId_record(Long.toString(bean.getIdAvanzamento()));
						raBean.setData_inizio_record(bean.getDataInizioAvanzamento());
						raBean.setBlocco(ParametriServletAvanzamento.TAB_AVANZAMENTO);
						
						avanzamentoAction.richiestaCancellazione(raBean);
						
                        // 3.02.2.1 accettazione immediata richiesta
                        if(SimogFlags.is30230_RFWEBSC03Active()){
                           boolean successo;

                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(raBean.getBlocco(), currentActiveConnection, logger);
                            
                           raBean.setDecisore(currentUser.getLogin());
                           raBean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
                           raBean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
                           
                           successo = annAction.gestisciRichiestaCancellazione(raBean,currentUser.getLogin());
                    
                           if(successo){
                              validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RICHIESTA_CANCELLAZIONE_002);
                              commit(currentActiveConnection);
                              bean = new AvanzamentoBean();
                              
                               // forward a lista aggiudicazioni
                               pagina = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA
                                   + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO+"="+raBean.getId_lotto()
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
                           bean = avanzamentoAction.loadOne(bean.getIdAvanzamento(), bean.getDataInizioAvanzamento());
                        }
                        
						schedaAvanzamento.setAvanzamentoFE(bean);
					}
					else if(PSBD.ACTION_REIMPOSTA.equalsIgnoreCase(action)){
						bean = avanzamentoAction.getBean(request, infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						bean.setNumeroAvanzamento(avanzamentoAction.getNextAvanzamento(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione()));
						schedaAvanzamento.setAvanzamentoFE(bean);
						if(bean.getIdAvanzamento() > 0){
							bean = avanzamentoAction.loadOne(bean.getIdAvanzamento(), bean.getDataInizioAvanzamento());
							schedaAvanzamento.setAvanzamentoFE(bean);
							validator.valida(schedaAvanzamento, null);
						}
					}
					List<AvanzamentoBean> listaAvanzamenti = avanzamentoAction.loadMany(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
					sessione.setAttribute("listaAvanzamenti", listaAvanzamenti);
					//request.setAttribute("avanzamento", bean);
					schedaAvanzamento.setNavigationBean(getNavBean(infoGara.getIdAggiudicazione(), ParametriServletAvanzamento.TAB_AVANZAMENTO, request.getSession()));
					
					CollaudoAction collaudoAction = new CollaudoAction(currentActiveConnection,logger);
					CollaudoBean collaudoBean = collaudoAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
					if(collaudoBean.getEsitoCollaudo() == null){
						schedaAvanzamento.setAggiungibile(true);
					}else{
						schedaAvanzamento.setAggiungibile(false);
					}
					
					/***************** DETTAGLI RICH ANN ************************/
					
					TableBean tabRichAnn = avanzamentoAction.bsa.getRichAnnByScheda(
							String.valueOf(schedaAvanzamento.getAvanzamentoFE().getIdAvanzamento()), 
							ParametriServletAvanzamento.TAB_AVANZAMENTO, false);
					
					request.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
					
					/***************** END              *************************/
					
					if( bean != null ) {
						bean.setOkCancellazione(avanzamentoAction.bsa.isCancellabile(ParametriServletAvanzamento.TAB_AVANZAMENTO, 
								bean.getIdAvanzamento(), 
								bean.getDataInizioAvanzamento(),
								bean.getIdStato(),
								infBea.getFlagEnteSpeciale(), 
								infBea.getTipoContratto(),
								bean.getIdAggiudicazione(),
								bean.getDataInizioAggiudicazione()));					
					}
					
					request.setAttribute("schedaAvanzamento", schedaAvanzamento);
					
					//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
					if(bean != null && bean.getIdStato() == StatiScheda.CONFERMATO)
						avanzamentoAction.sendValidations(request, validator.getEccezioni().getAllInfoEWarn());
					else
						avanzamentoAction.sendValidations(request, validator.getEccezioni());
					
					forward(ParametriServletAvanzamento.JSP_SCHEDA_AVANZAMENTO, request, response);
					return;
				}catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
					rollback(currentActiveConnection);
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE , e);
					
					return;

				}finally{
					closeConnection(request.getSession().getId(),getClass().getName());
				}
				
			}else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				return;
			}
	 }else{
		 sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
		return;
	 }
	}
	
	@SuppressWarnings("unchecked")
	
	public void doGet(HttpServletRequest req , HttpServletResponse resp)throws ServletException,IOException{
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) req.getSession().getAttribute(UTENTE);
		if (checkSession(req)) {
			if (currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()) {
				try {
					currentActiveConnection = getSimogConnection(req.getSession().getId(),getClass().getName());
					setDatiAggiudicazione(req);
					String action = req.getParameter("toDo");
					HttpSession sessione = req.getSession();
					AvanzamentoBean bean = null;
					SchedaAvanzamento schedaAvanzamento = null;
					InfoGaraBean infoGara = getDatiGara(req.getSession());
					List<AvanzamentoBean> listaAvanzamenti = new ArrayList<AvanzamentoBean>();
					AvanzamentoAction avanzamentoAction = new AvanzamentoAction(currentActiveConnection,logger);
					if("loadAll".equalsIgnoreCase(action)){
						logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>loadALL (ava)");
						listaAvanzamenti = avanzamentoAction.loadMany(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						sessione.setAttribute("listaAvanzamenti", listaAvanzamenti);

						AggiudicazioneBean aggBea = avanzamentoAction.getAggiudicazione(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						req.setAttribute("aggiudicazione",aggBea) ;
					}
					else if("load".equalsIgnoreCase(action)){
						logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>load (ava)");
						String toEdit = req.getParameter("toEdit");
						try{
							listaAvanzamenti = (List<AvanzamentoBean>)sessione.getAttribute("listaAvanzamenti");
							if("-1".equals(toEdit)){ 
								bean = new AvanzamentoBean();
								bean.setNumeroAvanzamento(avanzamentoAction.getNextAvanzamento(infoGara.getIdAggiudicazione(), 
										infoGara.getDataInizioAggiudicazione()));

								schedaAvanzamento = new SchedaAvanzamento();
								schedaAvanzamento.setAvanzamentoFE(bean);
							}
							else{ 
								//caso della selezione di un avanzamento
								bean = listaAvanzamenti.get(Integer.parseInt(toEdit));
								schedaAvanzamento = new SchedaAvanzamento();								
								schedaAvanzamento.setAvanzamentoFE(bean);
								bean.setOkCancellazione(avanzamentoAction.bsa.isCancellabile(ParametriServletAvanzamento.TAB_AVANZAMENTO, 
										bean.getIdAvanzamento(), 
										bean.getDataInizioAvanzamento(),
										bean.getIdStato(),
										infoGara.getTipoEnte(), 
										infoGara.getTipoContratto(),
										bean.getIdAggiudicazione(),
										bean.getDataInizioAggiudicazione()));					
								
								/** adds: validazione \"onload\" dei un singolo avanzamento */
								AggiudicazioneBean aggBea = avanzamentoAction.getAggiudicazione(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
								schedaAvanzamento.setAggiudicazione(aggBea);
								InfoComuniBean infBea = avanzamentoAction.getInfoComuni(aggBea.getIdInfo(), aggBea.getDataInizioInfo());
								logger.debug("[infocomuni sulla servlet(onload singolo)] - "+ObjectIntrospector.propertiesInfo(InfoComuniBean.class, infBea));
								schedaAvanzamento.setInfoComuni(infBea);								

                                // PP non caricava le varianti!
                                schedaAvanzamento.setVarianti(avanzamentoAction.getVarianti(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione()));                               

								SimogValidator validator = ValidatorFactory.getValidator(ParametriServletAvanzamento.TAB_AVANZAMENTO, currentActiveConnection, logger);								
								validator.valida(schedaAvanzamento, null);
								
								//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
								if(bean != null && bean.getIdStato() == StatiScheda.CONFERMATO)
									avanzamentoAction.sendValidations(req, validator.getEccezioni().getAllInfoEWarn());
								else
									avanzamentoAction.sendValidations(req, validator.getEccezioni());
								
								/** end */
								
							}
							
						}catch (Exception e) {
							
							logger.fatal(e);
						}
					}
					if(bean == null){
						logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>bean null (ava)");
						bean = new AvanzamentoBean();
						bean.setNumeroAvanzamento(avanzamentoAction.getNextAvanzamento(infoGara.getIdAggiudicazione(), 
								infoGara.getDataInizioAggiudicazione()));

						schedaAvanzamento = new SchedaAvanzamento();
						schedaAvanzamento.setAvanzamentoFE(bean);
					}
					schedaAvanzamento.setNavigationBean(getNavBean(infoGara.getIdAggiudicazione(), ParametriServletAvanzamento.TAB_AVANZAMENTO, req.getSession()));
					
					CollaudoAction collaudoAction = new CollaudoAction(currentActiveConnection,logger);
					CollaudoBean collaudoBean = collaudoAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
					if(collaudoBean.getEsitoCollaudo() == null){
						schedaAvanzamento.setAggiungibile(true);
					}else{
						schedaAvanzamento.setAggiungibile(false);
					}

					//commentato per MEV 43789 3.04.10.1
	                   // PP se gara sotto i 500K non si possono aggiungere schede avanzamento
//                    if(infoGara.getImportoLotto().floatValue() != Costanti.IMPORTO_FUORI_SCALA 
//                          && infoGara.getImportoLotto().floatValue() < Costanti.IMPORTO_LOTTO_500000){
//                       schedaAvanzamento.setAggiungibile(false);
//                       schedaAvanzamento.setNoInserimenti(true);
//                    } 
                  //fine commentato per MEV 43789 3.04.10.1

					/***************** DETTAGLI RICH ANN ************************/
					
					TableBean tabRichAnn = avanzamentoAction.bsa.getRichAnnByScheda(
							String.valueOf(schedaAvanzamento.getAvanzamentoFE().getIdAvanzamento()), 
							ParametriServletAvanzamento.TAB_AVANZAMENTO, false);
					
					req.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
					
					/***************** END              *************************/
					
					req.setAttribute("schedaAvanzamento", schedaAvanzamento);
					forward(ParametriServletAvanzamento.JSP_SCHEDA_AVANZAMENTO, req, resp);
					return;
				}catch (Exception e) {
					logger.fatal(e);
					rollback(currentActiveConnection);
					sendError(req, resp, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE, e );
					
					return;

				}finally{
					closeConnection(req.getSession().getId(),getClass().getName());
				}
			}else {
				sendError(req, resp, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				return;
			}
		}else{
			 sendError(req, resp, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
				return;
			 }
	}

}
