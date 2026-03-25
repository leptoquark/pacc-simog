package it.avlp.simog.servlet.r129;

import it.avlp.simog.actions.conclusione.ConclusioniAction;
import it.avlp.simog.actions.r129.R129Action;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.beans.r129.SchedaR129;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.common.action.BaseRichiestaAnnullamento;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletAvanzamento;
import it.avlp.simog.common.servlet.ParametriServletR129;
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

public class SrvSchedaR129 extends ServletBase {

	
	private static final long serialVersionUID = -6287737755341032437L;

	@Override
	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if (checkSession(request)) {
				
				if (currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()) {
					try{
						request.setAttribute(PSBD.NOME_SCHEDA,IdentificativoSchede.getRitardo().getDecodificaVN()) ;

						
						visualizzaListaParametriValori(request, response);
						currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
						String action = request.getParameter("toDo");
						if(action == null)
							action = request.getParameter(PSBD.ACTION_TYPE);
						
						/** Consente solo l'operazione di caricamento all'Osservaotre regionale */
						if(action == null || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA())
							action = PSBD.ACTION_LOAD;
						
						HttpSession session = request.getSession();
						String pagina = null;
						R129Action rAction = new R129Action(currentActiveConnection,logger);
						currentActiveConnection.setAutoCommit(false);
						InfoGaraBean infoGaraBean =getDatiGara(request.getSession());
						R129Bean bean = new R129Bean();
						SchedaR129 schedaR129 = new SchedaR129();
						schedaR129.setRitardoFE(bean);
						AggiudicazioneBean aggBea = rAction.getAggiudicazione(infoGaraBean .getIdAggiudicazione(), infoGaraBean .getDataInizioAggiudicazione());
						schedaR129.setAggiudicazione(aggBea);
						InfoComuniBean infBea = rAction.getInfoComuni(aggBea.getIdInfo(), aggBea.getDataInizioInfo());
						schedaR129.setInfoComuni(infBea);
						InizioLavoriBean inizioLavori = rAction.getInizioLavori(aggBea.getIdAggiudicazione(), aggBea.getDataInizioAggiudicazione());
						schedaR129.setInizioLavori(inizioLavori);
						SimogValidator validator = ValidatorFactory.getValidator(ParametriServletR129.TAB_SCHEDA_R129, currentActiveConnection, logger);
						
						if(PSBD.ACTION_SALVA.equalsIgnoreCase(action)){
							bean = rAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
							schedaR129.setRitardoFE(bean);
							if(bean.getIdRecord() < 1 && isRefresh(request)){
								validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
								pagina = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?"
								+ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + infoGaraBean.getIdLotto() +
								"&"+ParametriServlet.FIELD_NAME_ID_INFO+"="+infoGaraBean.getIdInfo()
								+"&"+ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO+"="+infoGaraBean.getDataInizioInfo();
								
								rAction.sendValidations(request, validator.getEccezioni());
								
								forward(pagina, request, response);
								return;
							}
							if(validator.valida(schedaR129, null)){
								boolean inserimento = bean.getIdRecord() <1;
								rAction.save(bean, currentUser.getLogin());
								commit(currentActiveConnection);
								if(inserimento)
									validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_023);
								else validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_024);
								bean = rAction.loadOne(bean.getIdRecord(),bean.getDataInizioRecord());
								schedaR129.setRitardoFE(bean);
							}
						}
						else if(PSBD.ACTION_CONFERMA.equalsIgnoreCase(action)){
							bean = rAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
							schedaR129.setRitardoFE(bean);
							if(validator.valida(schedaR129, null)){
								rAction.confirm(bean, currentUser.getLogin());
								commit(currentActiveConnection);
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_025);
								bean = rAction.loadOne(bean.getIdRecord(),bean.getDataInizioRecord());
								schedaR129.setRitardoFE(bean);								
							}
						}
						else if(PSBD.ACTION_CARICA_JSP_ANNULLAMENTO.equalsIgnoreCase(action)){
							bean = rAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
							schedaR129.setRitardoFE(bean);
							
							boolean isOk = (rAction.bsa.hasSchedaRichDelete(String.valueOf(bean.getIdRecord()), IdentificativoSchede.TAB_RITARDO, false).getFullSize()==0);
							if (isOk){
								request.setAttribute(PSBD.TAB, ParametriServletR129.TAB_SCHEDA_R129);
								request.setAttribute(ParametriServlet.SHOW_DATI_COMUNI, "true");
								String dest = ParametriServlet.JSP_RICHIEDI_ANNULLAMENTO + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGaraBean.getIdAggiudicazione();
								dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGaraBean.getDataInizioAggiudicazione();
								dest+="&" + ParametriServletR129.FIELD_NAME_ID_RECORD + "=" + bean.getIdRecord();
								dest+="&" + ParametriServletR129.FIELD_NAME_ID_RECORD + "=" + bean.getDataInizioRecord();
								forward(dest, request, response);
								return;
							}
							else{
								validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
							}
						}
						else if(PSBD.ACTION_CARICA_JSP_CANCELLAZIONE.equalsIgnoreCase(action)){
							bean = rAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
							schedaR129.setRitardoFE(bean);

							boolean isOk = (rAction.bsa.hasSchedaRichDelete(String.valueOf(bean.getIdRecord()), IdentificativoSchede.TAB_RITARDO, true).getFullSize()==0);
							if (isOk){
								request.setAttribute(PSBD.TAB, ParametriServletR129.TAB_SCHEDA_R129);
								request.setAttribute(ParametriServlet.SHOW_DATI_COMUNI, "true");
								String dest = ParametriServlet.JSP_RICHIEDI_CANCELLAZIONE + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGaraBean.getIdAggiudicazione();
								dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGaraBean.getDataInizioAggiudicazione();
								dest+="&" + ParametriServletR129.FIELD_NAME_ID_RECORD + "=" + bean.getIdRecord();
								dest+="&" + ParametriServletR129.FIELD_NAME_ID_RECORD + "=" + bean.getDataInizioRecord();
								forward(dest, request, response);
								return;
							}
							else{
								validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
							}
						}
						else if(PSBD.ACTION_RICHIESTA_ANNULLAMENTO.equalsIgnoreCase(action)){
							bean = rAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
							schedaR129.setRitardoFE(bean);
							String motivazione = request.getParameter(PSBD.MOTIVAZIONE_ANNULLAMENTO);
							String idLotto = Long.toString(infoGaraBean.getIdLotto());
							
							RichiestaAnnullamento raBean = new RichiestaAnnullamento();
							raBean.setId_lotto(idLotto);
							raBean.setMotivo_richiesta(motivazione);
		                    raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
							raBean.setRichiedente(currentUser.getLogin());
							boolean successo = false;
							raBean.setId_record(Long.toString(bean.getIdRecord()));

							
							raBean.setData_inizio_record(bean.getDataInizioRecord());
							raBean.setBlocco(ParametriServletR129.TAB_SCHEDA_R129);
							Timestamp nuovadata = null;
							
							nuovadata = rAction.richiediAnnullamento(raBean);
							
							successo = nuovadata != null;										

	                        // 3.02.2.1 accettazione immediata richiesta
	                        if(SimogFlags.is30230_RFWEBSC03Active()){
	                           
	                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(raBean.getBlocco(), currentActiveConnection, logger);
	                           
	                           raBean.setDecisore(currentUser.getLogin());
	                           raBean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
	                           raBean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
	                           
	                           successo = annAction.gestisciRichiesta(raBean,currentUser.getLogin());
	                        
	                           if(successo){
	                              bean.setDataInizioRecord(nuovadata);
	                              validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RICHIESTA_ANNULLAMENTO_002);
	                              commit(currentActiveConnection);
	                              bean = rAction.loadOne(bean.getIdRecord(),bean.getDataInizioRecord());
	                          }
	                          else{
	                              rollback(currentActiveConnection);
	                              validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
	                              //Un bean vuoto con il numero progessivo settato
	                              bean = new R129Bean();
	                          }
	                       }
	                       else{                          							
      							if(successo){
      								
      								commit(currentActiveConnection);
      								bean.setDataInizioRecord(nuovadata);
      								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_080);
      								bean = rAction.loadOne(bean.getIdRecord(),bean.getDataInizioRecord());
      							}
      							else{
      								
      								rollback(currentActiveConnection);
      								validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
      								bean = new R129Bean();
      							}
                           }
							schedaR129.setRitardoFE(bean);		
							
						}
						
						else if(PSBD.ACTION_RICHIESTA_CANCELLAZIONE.equalsIgnoreCase(action)){
							bean = rAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
							schedaR129.setRitardoFE(bean);
							String motivazione = request.getParameter(PSBD.MOTIVAZIONE_CANCELLAZIONE);
							String idLotto = Long.toString(infoGaraBean.getIdLotto());
							
							RichiestaAnnullamento raBean = new RichiestaAnnullamento();
							raBean.setCancellazione(Costanti.FLAG_VALORE_NO) ;
							raBean.setId_lotto(idLotto);
							raBean.setMotivo_richiesta(motivazione);
							raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
							raBean.setRichiedente(currentUser.getLogin());
							raBean.setId_record(Long.toString(bean.getIdRecord()));
							raBean.setData_inizio_record(bean.getDataInizioRecord());
							raBean.setBlocco(ParametriServletR129.TAB_SCHEDA_R129);
							
							rAction.richiediCancellazione(raBean);
							
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
	                              bean = new R129Bean();
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
      							bean = rAction.loadOne(bean.getIdRecord(),bean.getDataInizioRecord());
                            }                           
							
							schedaR129.setRitardoFE(bean);	
						}
						
						else if(PSBD.ACTION_REIMPOSTA.equalsIgnoreCase(action)){
							bean = rAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
							if(bean.getIdRecord() > 0){
								bean = rAction.loadOne(bean.getIdRecord(),bean.getDataInizioRecord());
								schedaR129.setRitardoFE(bean);
								validator.valida(schedaR129, null);
							}
						}
						
						List<R129Bean> beanList = rAction.loadAllByAgg(infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						session.setAttribute("lista_r129", beanList);
						schedaR129.setNavigationBean(getNavBean(infoGaraBean.getIdAggiudicazione(), ParametriServletR129.TAB_SCHEDA_R129, request.getSession()));
						
						if ( bean != null )  {
							bean.setOkCancellazione(rAction.bsa.isCancellabile(ParametriServletR129.TAB_SCHEDA_R129, 
									bean.getIdRecord(), 
									bean.getDataInizioRecord(),
									bean.getIdStato(),
									infoGaraBean.getTipoEnte(), 
									infoGaraBean.getTipoContratto(),
									bean.getIdAggiudicazione(),
									bean.getDataInizioAggiudicazione()));
						}
						
						ConclusioniAction cAction = new ConclusioniAction(currentActiveConnection,logger);
						ConclusioneBean conclusionebean = cAction.load(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());

						if((inizioLavori != null && inizioLavori.getIdStato() > 0) || (conclusionebean != null && conclusionebean.getIdStato() > 0))
							schedaR129.setAggiungibile(false);
						else
							schedaR129.setAggiungibile(true);
						
						/***************** DETTAGLI RICH ANN ************************/
						
						TableBean tabRichAnn = rAction.bsa.getRichAnnByScheda(
								String.valueOf(schedaR129.getRitardoFE().getIdRecord()), 
								ParametriServletR129.TAB_SCHEDA_R129, false);
						
						request.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
						
						/***************** END              *************************/
						
						request.setAttribute("schedaR129", schedaR129);
						
						//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
						if(bean != null && bean.getIdStato() == StatiScheda.CONFERMATO)
						    rAction.sendValidations(request, validator.getEccezioni().getAllInfoEWarn());
						else
							rAction.sendValidations(request, validator.getEccezioni());
						
						forward(ParametriServletR129.JSP_SCHEDA_R129, request, response);
						return;
					}catch (Exception e) {
						logger.fatal(e);
					//	e.printStackTrace();
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
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Connection currentActiveConnection = null;
		HttpSession currentActiveSession = req.getSession();
		Utente currentUser = (Utente) req.getSession().getAttribute(UTENTE);
      if (checkSession(req)) {
			
			if (currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()) {
				try {
					setDatiAggiudicazione(req);
					currentActiveConnection = getSimogConnection(req.getSession().getId(),getClass().getName());
					String action = req.getParameter("toDo");
					InfoGaraBean infoGara = getDatiGara(req.getSession());	
					R129Bean bean = null;
					SchedaR129 schedaR129 = new SchedaR129();
					R129Action rAction = new R129Action(currentActiveConnection,logger);
					InfoGaraBean infoGaraBean =getDatiGara(req.getSession());
					AggiudicazioneBean aggBea = rAction.getAggiudicazione(infoGaraBean .getIdAggiudicazione(), infoGaraBean .getDataInizioAggiudicazione());
					schedaR129.setAggiudicazione(aggBea);
					InfoComuniBean infBea = rAction.getInfoComuni(aggBea.getIdInfo(), aggBea.getDataInizioInfo());
					schedaR129.setInfoComuni(infBea);
					InizioLavoriBean inizioLavori = rAction.getInizioLavori(aggBea.getIdAggiudicazione(), aggBea.getDataInizioAggiudicazione());
					schedaR129.setInizioLavori(inizioLavori);
					
					if("loadAll".equalsIgnoreCase(action)){
						//richiesta visualizzazione/modifica/conferma schede r129					
										
						List<R129Bean> beanList = rAction.loadAllByAgg(infoGara.getIdAggiudicazione(),infoGara.getDataInizioAggiudicazione());
						currentActiveSession.setAttribute("lista_r129", beanList);						
					}
					else if ("load".equalsIgnoreCase(action)) {
						//modifica scheda
						String toEdit = req.getParameter("toEdit");						
						try{
							List<R129Bean> beanList =(List<R129Bean>) currentActiveSession.getAttribute("lista_r129");
							if("-1".equals(toEdit)){
								bean = new R129Bean();
								schedaR129.setRitardoFE(bean);
							}
							else{ 
								bean = beanList.get(Integer.parseInt(toEdit));
								schedaR129.setRitardoFE(bean);

								bean.setOkCancellazione(rAction.bsa.isCancellabile(ParametriServletR129.TAB_SCHEDA_R129, 
										bean.getIdRecord(), 
										bean.getDataInizioRecord(),
										bean.getIdStato(),
										infoGaraBean.getTipoEnte(), 
										infoGaraBean.getTipoContratto(),
										bean.getIdAggiudicazione(),
										bean.getDataInizioAggiudicazione()));

								/** adds: validazione del singolo bean quando ne viene richiesta la visualizzazione */
								SimogValidator validator = ValidatorFactory.getValidator(ParametriServletR129.TAB_SCHEDA_R129, currentActiveConnection, logger);
								validator.valida(schedaR129, null);
								
								//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
								if(bean != null && bean.getIdStato() == StatiScheda.CONFERMATO)
								    rAction.sendValidations(req, validator.getEccezioni().getAllInfoEWarn());
								else
									rAction.sendValidations(req, validator.getEccezioni());
								
								/** end */
							}
						
						}catch (Exception e) {
							
							logger.fatal(e);
						}
					}
					
					// SchedaR129 nuova
					if(bean == null){
						bean = new R129Bean();
						schedaR129.setRitardoFE(bean);
						/** Setto Data della consegna dei lavori al caricamento */
						String dataConsegna = inizioLavori.getDataVerbaleDef() != null ? inizioLavori.getDataVerbaleDef() 
											: (inizioLavori.getDataVerbaleCons() != null ? inizioLavori.getDataVerbaleCons() : null);
						schedaR129.getRitardoFE().setDataConsegna(dataConsegna);
						/** End */
					}
					
					/***************** DETTAGLI RICH ANN ************************/
					
					TableBean tabRichAnn = rAction.bsa.getRichAnnByScheda(
							String.valueOf(schedaR129.getRitardoFE().getIdRecord()), 
							ParametriServletR129.TAB_SCHEDA_R129, false);
					
					req.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
					
					/***************** END              *************************/
					
					schedaR129.setNavigationBean(getNavBean(infoGara.getIdAggiudicazione(), ParametriServletR129.TAB_SCHEDA_R129, req.getSession()));
					req.setAttribute("schedaR129", schedaR129);
					
					ConclusioniAction cAction = new ConclusioniAction(currentActiveConnection,logger);
					ConclusioneBean conclusionebean = cAction.load(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());

					if((inizioLavori != null && inizioLavori.getIdStato() > 0) || (conclusionebean != null && conclusionebean.getIdStato() > 0))
						schedaR129.setAggiungibile(false);
					else
						schedaR129.setAggiungibile(true);
					
					forward(ParametriServletR129.JSP_SCHEDA_R129, req, resp);
					return;
				}catch (Exception e) {
					logger.fatal(e);
					//e.printStackTrace();
					sendError(req, resp, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
					
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
