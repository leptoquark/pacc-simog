package it.avlp.simog.servlet.accordo;

import it.avlp.simog.actions.accordo.AccordoAction;
import it.avlp.simog.actions.collaudo.CollaudoAction;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.accordi.SchedaAccordo;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.common.action.BaseRichiestaAnnullamento;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletAccordo;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletCollaudo;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.factory.AnnullamentoFactory;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.factory.ValidatorFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SrvSchedaAccordo extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6844497666368077474L;

	
	@SuppressWarnings("unchecked")
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Connection currentActiveConnection = null;
		HttpSession currentActiveSession = req.getSession();
		Utente currentUser = (Utente) req.getSession().getAttribute(UTENTE);
		logger.debug(" -- doget -- ");
      if (checkSession(req)) {
			
			if (currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()) {
				try {
					setDatiAggiudicazione(req);
					currentActiveConnection = getSimogConnection(req.getSession().getId(),getClass().getName());
					String action = req.getParameter("toDo");
					
					HttpSession sessione = req.getSession();
					AccordoBean bean = null;
					SchedaAccordo schedaAccordo = null;
					AccordoAction accAction = new AccordoAction(currentActiveConnection,logger);
					
					InfoGaraBean infoGara = getDatiGara(req.getSession());
					if("loadAll".equalsIgnoreCase(action)){
						logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>loadALL");
						//richiesta visualizzazione/modifica/conferma schede Accordo						
						List<AccordoBean> beanList = accAction.loadAllByAgg(infoGara.getIdAggiudicazione(),infoGara.getDataInizioAggiudicazione());
						sessione.setAttribute("lista_accordo", beanList);
						
						AggiudicazioneBean aggBea = accAction.getAggiudicazione(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						req.setAttribute("aggiudicazione",aggBea) ;

					}
					else if ("load".equalsIgnoreCase(action)) {
						logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>load");
						//modifica scheda
						String toEdit = req.getParameter("toEdit");
						logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+toEdit);
						try{
							List<AccordoBean> beanList =(List<AccordoBean>) currentActiveSession.getAttribute("lista_accordo");
							if("-1".equals(toEdit)){ 
								logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>toedit -1");
								bean = new AccordoBean(); 
								schedaAccordo = new SchedaAccordo();
								schedaAccordo.setAccordoFE(bean); 
							}
							else{ 
								logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>toedit diverso da -1");
								bean = beanList.get(Integer.parseInt(toEdit));
								schedaAccordo = new SchedaAccordo();
								schedaAccordo.setAccordoFE(bean); 
								
								bean.setOkCancellazione(accAction.bsa.isCancellabile(ParametriServletAccordo.TAB_SCHEDA_ACCORDO, 
										bean.getIdAccordo(), 
										bean.getDataInizioAccordo(),
										bean.getIdStato(),
										infoGara.getTipoEnte(), 
										infoGara.getTipoContratto(),
										bean.getIdAggiudicazione(),
										bean.getDataInizioAggiudicazione()));
								
								/** adds: validazione del singolo bean quando ne viene richiesta la visualizzazione */
								InfoGaraBean infoGaraBean =getDatiGara(req.getSession());
								AggiudicazioneBean agg = accAction.getAggiudicazione(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
								schedaAccordo.setAggiudicazione(agg);
								InfoComuniBean inf = accAction.getInfoComuni(infoGaraBean.getIdInfo(), infoGaraBean.getDataInizioInfo());
								schedaAccordo.setInfoComuni(inf);
								schedaAccordo.setInizioLavori(accAction.getInizioLavori(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione()));
								SimogValidator validator = ValidatorFactory.getValidator(ParametriServletAccordo.TAB_SCHEDA_ACCORDO, currentActiveConnection, logger);					
								validator.valida(schedaAccordo, null);
								
								//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
								if(bean != null && bean.getIdStato() == StatiScheda.CONFERMATO)
									accAction.sendValidations(req, validator.getEccezioni().getAllInfoEWarn());
								else
									accAction.sendValidations(req, validator.getEccezioni());
								
								/** end */
							}
						
						}catch (Exception e) {						
							logger.fatal(e);
						}
					}
				
					if(bean == null){
						logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>bean null");
						bean = new AccordoBean();
						schedaAccordo = new SchedaAccordo();
						schedaAccordo.setAccordoFE(bean); 
					}
					
					/*req.setAttribute("accordo", bean);*/
					CollaudoAction collaudoAction = new CollaudoAction(currentActiveConnection,logger);
					CollaudoBean collaudoBean = collaudoAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
					if(collaudoBean.getEsitoCollaudo() == null){
						schedaAccordo.setAggiungibile(true);
					}else{
						schedaAccordo.setAggiungibile(false);
					}
					
					/***************** DETTAGLI RICH ANN ************************/
					
					TableBean tabRichAnn = accAction.bsa.getRichAnnByScheda(
							String.valueOf(schedaAccordo.getAccordoFE().getIdAccordo()), 
							ParametriServletAccordo.TAB_SCHEDA_ACCORDO, false);
					
					req.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
					
					/***************** END              *************************/
					schedaAccordo.setNavigationBean(getNavBean(infoGara.getIdAggiudicazione(), IdentificativoSchede.TAB_ACCORDO, req.getSession()));
					req.setAttribute("schedaAccordo", schedaAccordo);
					
					forward(ParametriServletAccordo.JSP_SCHEDA_ACCORDO, req, resp);
					return;
				}catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
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
	
	@Override
	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		logger.debug(" -->>>>>> perform <<<<<<-- ");
		if (checkSession(request)) {
			
			if (currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()) {
				try{
					
					request.setAttribute(PSBD.NOME_SCHEDA,IdentificativoSchede.getAccordi().getDecodificaVN()) ;
					
					visualizzaListaParametriValori(request, response);
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					currentActiveConnection.setAutoCommit(false);
					String action = request.getParameter("toDo");
					HttpSession sessione = request.getSession();
					if(action == null)
						action = request.getParameter(PSBD.ACTION_TYPE);
					
					/** Consente solo l'operazione di caricamento all'Osservaotre regionale */
					if(action == null || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA())
						action = PSBD.ACTION_LOAD;
					
					String pagina = null;
					AccordoAction accAction = new AccordoAction(currentActiveConnection,logger);
					InfoGaraBean infoGaraBean =getDatiGara(request.getSession());
					/* provo a prendere i bean dalla request */				
					SchedaAccordo schedaAccordo = new SchedaAccordo();
					AccordoBean bean = new AccordoBean();
					schedaAccordo = new SchedaAccordo();
					schedaAccordo.setAccordoFE(bean); 
					AggiudicazioneBean agg = accAction.getAggiudicazione(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
					schedaAccordo.setAggiudicazione(agg);
					InfoComuniBean inf = accAction.getInfoComuni(infoGaraBean.getIdInfo(), infoGaraBean.getDataInizioInfo());
					schedaAccordo.setInfoComuni(inf);
					schedaAccordo.setInizioLavori(accAction.getInizioLavori(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione()));
					SimogValidator validator = ValidatorFactory.getValidator(ParametriServletAccordo.TAB_SCHEDA_ACCORDO, currentActiveConnection, logger);					
					
					if(PSBD.ACTION_SALVA.equalsIgnoreCase(action)){
						bean = accAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						schedaAccordo.setAccordoFE(bean);
						if(bean.getIdAccordo() < 1 && isRefresh(request)){
							
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
							//pagina = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?"
							pagina = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?"
							+ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + infoGaraBean.getIdLotto() +
							"&"+ParametriServlet.FIELD_NAME_ID_INFO+"="+infoGaraBean.getIdInfo()
							+"&"+ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO+"="+infoGaraBean.getDataInizioInfo();
							
							accAction.sendValidations(request, validator.getEccezioni());
							
							forward(pagina, request, response);
							return;
						}
						if(validator.valida(schedaAccordo, null)){
							
							boolean inserimento = bean.getIdAccordo() <1;
							accAction.save(bean, currentUser.getLogin());
							commit(currentActiveConnection);
							if(inserimento) validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_023);
							else validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_024);
							
							bean = accAction.loadOne(bean.getIdAccordo(), bean.getDataInizioAccordo());
							schedaAccordo.setAccordoFE(bean);
						}
							
							
							
					}
					else if(PSBD.ACTION_CONFERMA.equalsIgnoreCase(action)){
						bean = accAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());						
						schedaAccordo.setAccordoFE(bean);
						if(validator.valida(schedaAccordo, null)){
							accAction.confirm(bean, currentUser.getLogin());
							commit(currentActiveConnection);
							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_025);
							bean = accAction.loadOne(bean.getIdAccordo(), bean.getDataInizioAccordo());
							schedaAccordo.setAccordoFE(bean);
						}
					}
					else if(PSBD.ACTION_CARICA_JSP_ANNULLAMENTO.equalsIgnoreCase(action)){
						bean = accAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						schedaAccordo.setAccordoFE(bean);
						
						boolean isOk = (accAction.bsa.hasSchedaRichDelete(String.valueOf(bean.getIdAccordo()), IdentificativoSchede.TAB_ACCORDO, false).getFullSize()==0);
						if (isOk){
							request.setAttribute(PSBD.TAB, ParametriServletAccordo.TAB_SCHEDA_ACCORDO);
							
							String dest = ParametriServlet.JSP_RICHIEDI_ANNULLAMENTO + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGaraBean.getIdAggiudicazione();
							dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGaraBean.getDataInizioAggiudicazione();
							dest+="&" + ParametriServletAccordo.FIELD_NAME_ID_ACCORDO + "=" + bean.getIdAccordo();
							dest+="&" + ParametriServletAccordo.FIELD_NAME_ID_ACCORDO + "=" + bean.getDataInizioAccordo();
							forward(dest, request, response);
							return;
						}
						else{
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					else if(PSBD.ACTION_CARICA_JSP_CANCELLAZIONE.equalsIgnoreCase(action)){
						bean = accAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						schedaAccordo.setAccordoFE(bean);

						boolean isOk = (accAction.bsa.hasSchedaRichDelete(String.valueOf(bean.getIdAccordo()), IdentificativoSchede.TAB_ACCORDO, true).getFullSize()==0);
						if (isOk){
							request.setAttribute(PSBD.TAB, ParametriServletAccordo.TAB_SCHEDA_ACCORDO);
							
							String dest = ParametriServlet.JSP_RICHIEDI_CANCELLAZIONE + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGaraBean.getIdAggiudicazione();
							dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGaraBean.getDataInizioAggiudicazione();
							dest+="&" + ParametriServletAccordo.FIELD_NAME_ID_ACCORDO + "=" + bean.getIdAccordo();
							dest+="&" + ParametriServletAccordo.FIELD_NAME_ID_ACCORDO + "=" + bean.getDataInizioAccordo();
							forward(dest, request, response);
							return;
						}
						else{
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					else if(PSBD.ACTION_RICHIESTA_ANNULLAMENTO.equalsIgnoreCase(action)){
						bean = accAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						schedaAccordo.setAccordoFE(bean);
						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_ANNULLAMENTO);
						String idLotto = Long.toString(infoGaraBean.getIdLotto());
						
						RichiestaAnnullamento raBean = new RichiestaAnnullamento();
						raBean.setId_lotto(idLotto);
						raBean.setMotivo_richiesta(motivazione);
						raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						raBean.setRichiedente(currentUser.getLogin());
						boolean successo = false;
						raBean.setId_record(Long.toString(bean.getIdAccordo()));
						raBean.setData_inizio_record(bean.getDataInizioAccordo());
						raBean.setBlocco(ParametriServletAccordo.TAB_SCHEDA_ACCORDO);
						Timestamp nuovadata = null;
						
						nuovadata = accAction.richiediAnnullamento(raBean);
						
						successo = nuovadata != null;										

                        // 3.02.2.1 accettazione immediata richiesta
                        if(SimogFlags.is30230_RFWEBSC03Active()){
                           
                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(raBean.getBlocco(), currentActiveConnection, logger);
                           
                           raBean.setDecisore(currentUser.getLogin());
                           raBean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
                           raBean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
                           
                           successo = annAction.gestisciRichiesta(raBean,currentUser.getLogin());
                        
                           if(successo){
                              bean.setDataInizioAccordo(nuovadata);
                              validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RICHIESTA_ANNULLAMENTO_002);
                              commit(currentActiveConnection);
                              bean = accAction.loadOne(bean.getIdAccordo(),bean.getDataInizioAccordo());
                          }
                          else{
                              rollback(currentActiveConnection);
                              validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
                              //Un bean vuoto con il numero progessivo settato
                              bean = new AccordoBean(); 
                          }
                       }
                       else{                          
      						if(successo){
      							commit(currentActiveConnection);
      							bean.setDataInizioAccordo(nuovadata);
      							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_080);
      							bean = accAction.loadOne(bean.getIdAccordo(),bean.getDataInizioAccordo());
      						}
      						else{
      							rollback(currentActiveConnection);
      							validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
      							bean = new AccordoBean();
      						}
                       }						
						schedaAccordo.setAccordoFE(bean);
					}
					else if(PSBD.ACTION_RICHIESTA_CANCELLAZIONE.equalsIgnoreCase(action)){
						bean = accAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						schedaAccordo.setAccordoFE(bean);
						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_CANCELLAZIONE);
						String idLotto = Long.toString(infoGaraBean.getIdLotto());
						
						RichiestaAnnullamento raBean = new RichiestaAnnullamento();
						raBean.setCancellazione(Costanti.FLAG_VALORE_NO) ;
						raBean.setId_lotto(idLotto);
						raBean.setMotivo_richiesta(motivazione);
						raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						raBean.setRichiedente(currentUser.getLogin());
						raBean.setId_record(Long.toString(bean.getIdAccordo()));
						raBean.setData_inizio_record(bean.getDataInizioAccordo());
						raBean.setBlocco(ParametriServletAccordo.TAB_SCHEDA_ACCORDO);
						
						accAction.richiediCancellazione(raBean);
						
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
                              bean = new AccordoBean();
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
      						bean = accAction.loadOne(bean.getIdAccordo(),bean.getDataInizioAccordo());
                        }
						
						schedaAccordo.setAccordoFE(bean);
					}
					else if(PSBD.ACTION_REIMPOSTA.equalsIgnoreCase(action)){
						bean = accAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						if(bean.getIdAccordo() > 0){
							bean = accAction.loadOne(bean.getIdAccordo(), bean.getDataInizioAccordo());
							schedaAccordo.setAccordoFE(bean);						
							validator.valida(schedaAccordo, null);
						}
					}	
					List<AccordoBean> beanList = accAction.loadAllByAgg(infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
					sessione.setAttribute(ParametriServletAccordo.LISTA_ACCORDI, beanList);
					//request.setAttribute(ParametriServletAccordo.LOAD_ACCORDO, bean);
					schedaAccordo.setNavigationBean(getNavBean(infoGaraBean.getIdAggiudicazione(), ParametriServletAccordo.TAB_SCHEDA_ACCORDO, request.getSession()));
					
					if(bean != null) {
						bean.setOkCancellazione(accAction.bsa.isCancellabile(ParametriServletAccordo.TAB_SCHEDA_ACCORDO, 
								bean.getIdAccordo(), 
								bean.getDataInizioAccordo(),
								bean.getIdStato(),
								infoGaraBean.getTipoEnte(), 
								infoGaraBean.getTipoContratto(),
								bean.getIdAggiudicazione(),
								bean.getDataInizioAggiudicazione()));
					}
					
					CollaudoAction collaudoAction = new CollaudoAction(currentActiveConnection,logger);
					CollaudoBean collaudoBean = collaudoAction.load(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
					if(collaudoBean.getEsitoCollaudo() == null){
						schedaAccordo.setAggiungibile(true);
					}else{
						schedaAccordo.setAggiungibile(false);
					}
					
					/***************** DETTAGLI RICH ANN ************************/
					
					TableBean tabRichAnn = accAction.bsa.getRichAnnByScheda(
							String.valueOf(schedaAccordo.getAccordoFE().getIdAccordo()), 
							ParametriServletAccordo.TAB_SCHEDA_ACCORDO, false);
					
					request.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
					
					/***************** END              *************************/
					
					request.setAttribute("schedaAccordo", schedaAccordo);
					//request.setAttribute(ParametriServlet.MOTIVI_SOSPENSIONE_BEAN, accAction.loadMotiviSospensione());

					//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
					if(bean != null && bean.getIdStato() == StatiScheda.CONFERMATO)
						accAction.sendValidations(request, validator.getEccezioni().getAllInfoEWarn());
					else
						accAction.sendValidations(request, validator.getEccezioni());
					
					forward(ParametriServletAccordo.JSP_SCHEDA_ACCORDO, request, response);
					return;
				}catch (Exception e) {
					logger.fatal(e);
					//e.printStackTrace();
					rollback(currentActiveConnection);
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE, e );
					
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

}