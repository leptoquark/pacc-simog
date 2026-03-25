package it.avlp.simog.servlet.variante;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.avlp.simog.actions.collaudo.CollaudoAction;
import it.avlp.simog.actions.variante.VarianteAction;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.variante.EventiMotiviVariantiBean;
import it.avlp.simog.beans.variante.SchedaVariante;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.common.action.BaseRichiestaAnnullamento;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletVariante;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.factory.AnnullamentoFactory;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.factory.ValidatorFactory;

public class SrvSchedaVarianti extends ServletBase {

	

	private static final long serialVersionUID = 7307174823446910241L;

	/**********************************
	 * 		metodo GET
	 **********************************/
	
	@SuppressWarnings("unchecked")
	
	public void doGet (HttpServletRequest req,
			HttpServletResponse resp)  throws ServletException, IOException {
		Connection currentActiveConnection = null;
		HttpSession currentActiveSession = req.getSession();
		Utente currentUser = (Utente) req.getSession().getAttribute(UTENTE);
		if (checkSession(req)) {
			
			if (currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()) {
				try {
					setDatiAggiudicazione(req);
					currentActiveConnection = getSimogConnection(req.getSession().getId(),getClass().getName());
					
					String action = req.getParameter("toDo");
					Timestamp dataInizioScheda = null;
					VarianteBean bean = null;
					SchedaVariante schedaVariante = null;
					InfoGaraBean infoGara = getDatiGara(req.getSession());
					VarianteAction varAction = new VarianteAction(currentActiveConnection,logger);
					
					if("loadAll".equalsIgnoreCase(action)){
						//richiesta visualizzazione/modifica/conferma schede Variante
						
						List<VarianteBean> beanList = varAction.loadAllByAgg(infoGara.getIdAggiudicazione(),infoGara.getDataInizioAggiudicazione());
						currentActiveSession.setAttribute("lista_variante", beanList);
						
						AggiudicazioneBean aggBea = varAction.getAggiudicazione(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						req.setAttribute("aggiudicazione",aggBea) ;

					}
					else if ("load".equalsIgnoreCase(action)) {
						//modifica scheda
						String toEdit = req.getParameter("toEdit");
						
						try{
							List<VarianteBean> beanList =(List<VarianteBean>) currentActiveSession.getAttribute("lista_variante");
							if("-1".equals(toEdit)){
								bean = new VarianteBean();
								schedaVariante = new SchedaVariante();
								schedaVariante.setVarianteFE(bean);
							}else{ 
								bean = beanList.get(Integer.parseInt(toEdit));
								schedaVariante = new SchedaVariante();
								schedaVariante.setVarianteFE(bean);

								InfoGaraBean infoGaraBean =getDatiGara(req.getSession());

								bean.setOkCancellazione(varAction.bsa.isCancellabile(ParametriServletVariante.TAB_SCHEDA_VARIANTE, 
										bean.getIdVariante(), 
										bean.getDataInizioVar(),
										bean.getIdStato(),
										infoGaraBean.getTipoEnte(), 
										infoGaraBean.getTipoContratto(),
										bean.getIdAggiudicazione(),
										bean.getDataInizioAggiudicazione()));	
								
								/** adds: validazione del singolo bean quando ne viene richiesta la visualizzazione */
								AggiudicazioneBean aggBea = varAction.getAggiudicazione(infoGaraBean .getIdAggiudicazione(), infoGaraBean .getDataInizioAggiudicazione());
								schedaVariante.setAggiudicazione(aggBea);
								InfoComuniBean infBea = varAction.getInfoComuni(aggBea.getIdInfo(), aggBea.getDataInizioInfo());
								schedaVariante.setInfoComuni(infBea);
								InizioLavoriBean inizioLavori = varAction.getInizioLavori(aggBea.getIdAggiudicazione(), aggBea.getDataInizioAggiudicazione());
								schedaVariante.setInizioLavori(inizioLavori);
								SimogValidator validator = ValidatorFactory.getValidator(ParametriServletVariante.TAB_SCHEDA_VARIANTE, currentActiveConnection, logger);
								validator.valida(schedaVariante, null);
								
								//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
								if(bean != null && bean.getIdStato() == StatiScheda.CONFERMATO)
								    varAction.sendValidations(req, validator.getEccezioni().getAllInfoEWarn());
								else
									varAction.sendValidations(req, validator.getEccezioni());
								
								/** end */
							}
						
						}catch (Exception e) {
						//	e.printStackTrace();
							logger.fatal(e);
						}
					}if(bean == null){
						bean = new VarianteBean();
						schedaVariante = new SchedaVariante();
						schedaVariante.setVarianteFE(bean);
					}
					schedaVariante.setNavigationBean(getNavBean(infoGara.getIdAggiudicazione(), ParametriServletVariante.TAB_SCHEDA_VARIANTE, req.getSession()));

					CollaudoAction collaudoAction = new CollaudoAction(currentActiveConnection,logger);
					CollaudoBean collaudoBean = collaudoAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
					if(collaudoBean.getEsitoCollaudo() == null){
						schedaVariante.setAggiungibile(true);
					}else{
						schedaVariante.setAggiungibile(false);
					}
					
					/***************** DETTAGLI RICH ANN ************************/
					
					TableBean tabRichAnn = varAction.bsa.getRichAnnByScheda(
							String.valueOf(schedaVariante.getVarianteFE().getIdVariante()), 
							ParametriServletVariante.TAB_SCHEDA_VARIANTE, false);
					
					req.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
					
					/***************** END              *************************/
					
					//TICKET ALM #2847 - Varianti
					if(SimogFlags.is3042Active()) {
						req.setAttribute("schedaVariante", schedaVariante);
						
					    req.setAttribute(ParametriServletVariante.BEAN_MOTIVI_VARIANTE, varAction.loadMotiviVariante(infoGara.getTipoContratto(),infoGara.getDataCreazioneGara(), infoGara.getDataCreazioneGara()));
					    
					    req.setAttribute("idMotivoVarianteAttr",idMotivoVariante(schedaVariante));
					    req.setAttribute("idSelectedMotivRevPrezzi",schedaVariante.getVarianteFE().getIdMotivoRevPrezzi());
					    req.setAttribute("motivi_revisione_prezzi", varAction.loadMotivoRevisionePrezzi());
					
					}
					//FINE TICKET ALM #2847 - Varianti
					
					setDataCreazione(infoGara.getDataCreazioneGara(), req.getSession());
					
					forward(ParametriServletVariante.JSP_SCHEDA_VARIANTE, req, resp);
					return;
				}catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
					
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
		HttpSession currentActiveSession = request.getSession();
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		
		if (checkSession(request)) {
			
			if (currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()) {
				try{
					
					request.setAttribute(PSBD.NOME_SCHEDA,IdentificativoSchede.getVarianti().getDecodificaVN()) ;
					
					visualizzaListaParametriValori(request, response);
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					currentActiveConnection.setAutoCommit(false);
					String action = request.getParameter("toDo");
					if(action == null)
						action = request.getParameter(PSBD.ACTION_TYPE);
					
					/** Consente solo l'operazione di caricamento all'Osservaotre regionale */
					if(action == null || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA())
						action = PSBD.ACTION_LOAD;
					
					String pagina = null;
					VarianteAction varAction = new VarianteAction(currentActiveConnection,logger);
					InfoGaraBean infoGaraBean =getDatiGara(request.getSession());
					VarianteBean bean = new VarianteBean();
										
					SchedaVariante schedaVariante = new SchedaVariante();
					schedaVariante.setVarianteFE(bean);
					AggiudicazioneBean aggBea = varAction.getAggiudicazione(infoGaraBean .getIdAggiudicazione(), infoGaraBean .getDataInizioAggiudicazione());
					schedaVariante.setAggiudicazione(aggBea);
					InfoComuniBean infBea = varAction.getInfoComuni(aggBea.getIdInfo(), aggBea.getDataInizioInfo());
					schedaVariante.setInfoComuni(infBea);
					InizioLavoriBean inizioLavori = varAction.getInizioLavori(aggBea.getIdAggiudicazione(), aggBea.getDataInizioAggiudicazione());
					schedaVariante.setInizioLavori(inizioLavori);
					SimogValidator validator = ValidatorFactory.getValidator(ParametriServletVariante.TAB_SCHEDA_VARIANTE, currentActiveConnection, logger);
					
					
					
					if(PSBD.ACTION_SALVA.equalsIgnoreCase(action)){
						// Carica il bean di Varianti e la lista dei motivi varinate, effettua il save.
						bean = varAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione()); 
						schedaVariante.setVarianteFE(bean);
						
						if(bean.getIdVariante() < 1 && isRefresh(request)){
							// caso in cui non ci siano varianti in sessione e si stia effettuando il refresh
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000); // inserisce un errore
							//pagina = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?"
							// generazione della stringa per la pagina 
							pagina =  ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?"
									+ ParametriServlet.FIELD_NAME_ID_LOTTO 
									+ "=" + infoGaraBean.getIdLotto() 
									+ "&" + ParametriServlet.FIELD_NAME_ID_INFO
									+ "=" + infoGaraBean.getIdInfo()
									+ "&" + ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO
									+ "=" + infoGaraBean.getDataInizioInfo();
							
							varAction.sendValidations(request, validator.getEccezioni());
							
							forward(pagina, request, response);
							return;
						}
					
						if(validator.valida(schedaVariante, null)){
							boolean inserimento = bean.getIdVariante() < 1;
							varAction.save(bean,bean.getEmvb(),currentUser.getLogin());
							commit(currentActiveConnection);
							// controllo sull'inserimento
							if(inserimento)
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_023);
							else validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_024);
							
							bean = varAction.loadOne(bean.getIdVariante(), bean.getDataInizioVar());
							schedaVariante.setVarianteFE(bean);
						}
						else //MEV 34469 3.04.8 SE LA  VALIDAZIONE NON PASSA RICARICO TUTTE QUANTE LE VOCI DELLE MOTIVAZIONI PREZZI
						{
//							bean.setIdMotivoRevPrezzi(0);
//							bean.setHasErrorsVariante("true");
							schedaVariante.setVarianteFE(bean);
						}
							
							
							
					}
					/****************************************************************************
					 *  Gestione della action CONFERMA
					 ****************************************************************************/
					else if(PSBD.ACTION_CONFERMA.equalsIgnoreCase(action)){
						bean = varAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						schedaVariante.setVarianteFE(bean);
						if(validator.valida(schedaVariante, null)){
							varAction.confirm(bean, currentUser.getLogin());
							commit(currentActiveConnection);
							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_025);
							bean = varAction.loadOne(bean.getIdVariante(), bean.getDataInizioVar());
							schedaVariante.setVarianteFE(bean);
						}
					}
					
					/*****************************************************************************
					 *  Gestione della Action per l'ANNULLAMENTO
					 *****************************************************************************/
					else if(PSBD.ACTION_CARICA_JSP_ANNULLAMENTO.equalsIgnoreCase(action)){
						bean = varAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						schedaVariante.setVarianteFE(bean);

						boolean isOk = (varAction.bsa.hasSchedaRichDelete(String.valueOf(bean.getIdVariante()), IdentificativoSchede.TAB_VARIANTE, false).getFullSize()==0);
						if (isOk){
							request.setAttribute(PSBD.TAB, ParametriServletVariante.TAB_SCHEDA_VARIANTE);
							
							String dest = ParametriServlet.JSP_RICHIEDI_ANNULLAMENTO + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGaraBean.getIdAggiudicazione();
							dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGaraBean.getDataInizioAggiudicazione();
							dest+="&" + ParametriServletVariante.FIELD_NAME_ID_VARIANTE + "=" + bean.getIdVariante();
							dest+="&" + ParametriServletVariante.FIELD_NAME_ID_VARIANTE + "=" + bean.getDataInizioVar();
							forward(dest, request, response);
							return;
						}
						else{
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					
					else if(PSBD.ACTION_CARICA_JSP_CANCELLAZIONE.equalsIgnoreCase(action)){
						bean = varAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						schedaVariante.setVarianteFE(bean);

						boolean isOk = (varAction.bsa.hasSchedaRichDelete(String.valueOf(bean.getIdVariante()), IdentificativoSchede.TAB_VARIANTE, true).getFullSize()==0);
						if (isOk){
							request.setAttribute(PSBD.TAB, ParametriServletVariante.TAB_SCHEDA_VARIANTE);
							
							String dest = ParametriServlet.JSP_RICHIEDI_CANCELLAZIONE  + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGaraBean.getIdAggiudicazione();
							dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGaraBean.getDataInizioAggiudicazione();
							dest+="&" + ParametriServletVariante.FIELD_NAME_ID_VARIANTE + "=" + bean.getIdVariante();
							dest+="&" + ParametriServletVariante.FIELD_NAME_ID_VARIANTE + "=" + bean.getDataInizioVar();
							forward(dest, request, response);
							return;
						}
						else{
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					
					/*****************************************************************************
					 *  Gestione della RICHIESTA ANNULLAMENTO
					 *****************************************************************************/
					else if(PSBD.ACTION_RICHIESTA_ANNULLAMENTO.equalsIgnoreCase(action)){
						bean = varAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						
						schedaVariante.setVarianteFE(bean);
						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_ANNULLAMENTO);
						String idLotto = Long.toString(infoGaraBean.getIdLotto());
						
						RichiestaAnnullamento raBean = new RichiestaAnnullamento();
						raBean.setId_lotto(idLotto);
						raBean.setMotivo_richiesta(motivazione);
						raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						raBean.setRichiedente(currentUser.getLogin());
						boolean successo = false;
						raBean.setId_record(Long.toString(bean.getIdVariante()));
						raBean.setData_inizio_record(bean.getDataInizioVar());
						raBean.setBlocco(ParametriServletVariante.TAB_SCHEDA_VARIANTE);
						Timestamp nuovadata = null;
						
						nuovadata = varAction.richiediAnnullamento(raBean);
						
						successo = nuovadata != null;			

                        // 3.02.2.1 accettazione immediata richiesta
                        if(SimogFlags.is30230_RFWEBSC03Active()){
                           
                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(raBean.getBlocco(), currentActiveConnection, logger);
                           
                           raBean.setDecisore(currentUser.getLogin());
                           raBean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
                           raBean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
                           
                           successo = annAction.gestisciRichiesta(raBean,currentUser.getLogin());
                        
                           if(successo){
                              bean.setDataInizioVar(nuovadata);
                              validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RICHIESTA_ANNULLAMENTO_002);
                              commit(currentActiveConnection);
                              bean = varAction.loadOne(bean.getIdVariante(),bean.getDataInizioVar());
                          }
                          else{
                              rollback(currentActiveConnection);
                              validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
                              //Un bean vuoto con il numero progessivo settato
                              bean = new VarianteBean();
                          }
                       }
                       else{                          
      						// gestione dell'esito della richiesta
      						if(successo){
      							commit(currentActiveConnection);
      							bean.setDataInizioVar(nuovadata);
      							schedaVariante.setVarianteFE(bean);
      							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_080);
      							bean = varAction.loadOne(bean.getIdVariante(),bean.getDataInizioVar());
      						}
      						else{
      							
      							rollback(currentActiveConnection);
      							validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
      							bean = new VarianteBean();
      						}
                       }
						schedaVariante.setVarianteFE(bean);
					}
					
					/*****************************************************************************
					 *  Gestione della RICHIESTA CANCELLAZIONE
					 *****************************************************************************/
					else if(PSBD.ACTION_RICHIESTA_CANCELLAZIONE.equalsIgnoreCase(action)){
						bean = varAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						
						schedaVariante.setVarianteFE(bean);
						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_CANCELLAZIONE);
						String idLotto = Long.toString(infoGaraBean.getIdLotto());
						
						RichiestaAnnullamento raBean = new RichiestaAnnullamento();
						raBean.setCancellazione(Costanti.FLAG_VALORE_NO) ;
						raBean.setId_lotto(idLotto);
						raBean.setMotivo_richiesta(motivazione);
						raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						raBean.setRichiedente(currentUser.getLogin());
						raBean.setId_record(Long.toString(bean.getIdVariante()));
						raBean.setData_inizio_record(bean.getDataInizioVar());
						raBean.setBlocco(ParametriServletVariante.TAB_SCHEDA_VARIANTE);
						
						varAction.richiediCancellazione(raBean);
						
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
                              bean = new VarianteBean();
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
      						// gestione dell'esito della richiesta
      						commit(currentActiveConnection);
      						schedaVariante.setVarianteFE(bean);
      						validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_082);
      						bean = varAction.loadOne(bean.getIdVariante(),bean.getDataInizioVar());
                        }
						schedaVariante.setVarianteFE(bean);
					}
					
					/****************************************************************************
					 *  Gestione della action REIMPOSTA
					 ****************************************************************************/
					else if(PSBD.ACTION_REIMPOSTA.equalsIgnoreCase(action)){
						bean = varAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						if(bean.getIdVariante() > 0){
							bean = varAction.loadOne(bean.getIdVariante(), bean.getDataInizioVar());
							schedaVariante.setVarianteFE(bean);
							validator.valida(schedaVariante, null);
						}
					}
					
					/*******************************************************************************************************
					 *  In beanList viene memorizzata la lista dei Bean contenenti le varianti e messa in sessione
					 *******************************************************************************************************/
					
					List<VarianteBean> beanList = varAction.loadAllByAgg(infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
					currentActiveSession.removeAttribute("lista_variante");
					currentActiveSession.removeAttribute("variante");
					//MEV 34469 3.04.8
					currentActiveSession.removeAttribute("idSelectedMotivRevPrezzi");
					currentActiveSession.removeAttribute("idMotivoVarianteAttr");
					currentActiveSession.removeAttribute("motivi_revisione_prezzi");
					//FINE
					currentActiveSession.setAttribute("lista_variante", beanList);
					schedaVariante.setNavigationBean(getNavBean(infoGaraBean.getIdAggiudicazione(), ParametriServletVariante.TAB_SCHEDA_VARIANTE, request.getSession()));
					
					if ( bean != null ) {
						bean.setOkCancellazione(varAction.bsa.isCancellabile(ParametriServletVariante.TAB_SCHEDA_VARIANTE, 
								bean.getIdVariante(), 
								bean.getDataInizioVar(),
								bean.getIdStato(),
								infoGaraBean.getTipoEnte(), 
								infoGaraBean.getTipoContratto(),
								bean.getIdAggiudicazione(),
								bean.getDataInizioAggiudicazione()));	
					}
					
					CollaudoAction collaudoAction = new CollaudoAction(currentActiveConnection,logger);
					CollaudoBean collaudoBean = collaudoAction.load(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
					if(collaudoBean.getEsitoCollaudo() == null){
						schedaVariante.setAggiungibile(true);
					}else{
						schedaVariante.setAggiungibile(false);
					}
					
					/***************** DETTAGLI RICH ANN ************************/
					
					TableBean tabRichAnn = varAction.bsa.getRichAnnByScheda(
							String.valueOf(schedaVariante.getVarianteFE().getIdVariante()), 
							ParametriServletVariante.TAB_SCHEDA_VARIANTE, false);
					
					request.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
					
					/***************** END              *************************/
					
					request.setAttribute("schedaVariante", schedaVariante);
					request.setAttribute(ParametriServletVariante.BEAN_MOTIVI_VARIANTE, varAction.loadMotiviVariante(infoGaraBean.getTipoContratto(),infoGaraBean.getDataCreazioneGara(), infoGaraBean.getDataCreazione()));
					
					//MEV MARRA 34469 3.04.8
					
					request.setAttribute("idMotivoVarianteAttr",idMotivoVariante(schedaVariante));
					request.setAttribute("idSelectedMotivRevPrezzi",schedaVariante.getVarianteFE().getIdMotivoRevPrezzi());
					request.setAttribute("motivi_revisione_prezzi", varAction.loadMotivoRevisionePrezzi());
					//
					//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
					if(bean != null && bean.getIdStato() == StatiScheda.CONFERMATO)
					    varAction.sendValidations(request, validator.getEccezioni().getAllInfoEWarn());
					else
						varAction.sendValidations(request, validator.getEccezioni());
					
					forward(ParametriServletVariante.JSP_SCHEDA_VARIANTE, request, response);
					return;
					
				}catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
					rollback(currentActiveConnection);
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
					
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
	
	private String idMotivoVariante(SchedaVariante sv)
	{		
		try 
		{
			List<EventiMotiviVariantiBean> listEmvb;
			listEmvb = sv.getVarianteFE().getEmvb();
			if(listEmvb != null && listEmvb.size() != 0)
			{
				for(EventiMotiviVariantiBean embv1 : listEmvb)
			    {
					if(embv1.getIdMotivoVariante() == 22)
			    	  return "true";
					else
					  return "false";
			    }
			}
			else
			{
				return "false";
			}
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
	    
	    return "false";
	}

}

