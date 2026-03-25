package it.avlp.simog.servlet.sospensioni;

import it.avlp.simog.actions.conclusione.ConclusioniAction;
import it.avlp.simog.actions.sospensioni.SospensioniAction;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.sospensioni.SchedaSospensione;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.common.action.BaseRichiestaAnnullamento;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletR129;
import it.avlp.simog.common.servlet.ParametriServletSospensioni;
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

public class SrvSchedaSospensioni extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4320797301808093482L;

	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		HttpSession currentActiveSession = request.getSession();
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if (checkSession(request)) {
				
				if (currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()) {
					try{
						
						request.setAttribute(PSBD.NOME_SCHEDA,IdentificativoSchede.getSospensioni().getDecodificaVN()) ;
						
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
						SospensioniAction sospAction = new SospensioniAction(currentActiveConnection,logger);
						currentActiveConnection.setAutoCommit(false);
						InfoGaraBean infoGaraBean =getDatiGara(request.getSession());
						SospensioniBean bean = new SospensioniBean();
						SchedaSospensione schedaSospensioni = new SchedaSospensione();
						schedaSospensioni.setSospensioneFE(bean);
						AggiudicazioneBean aggBea = sospAction.getAggiudicazione(infoGaraBean .getIdAggiudicazione(), infoGaraBean .getDataInizioAggiudicazione());
						schedaSospensioni.setAggiudicazione(aggBea);
						InfoComuniBean infBea = sospAction.getInfoComuni(aggBea.getIdInfo(), aggBea.getDataInizioInfo());
						schedaSospensioni.setInfoComuni(infBea);
						InizioLavoriBean inizioLavori = sospAction.getInizioLavori(aggBea.getIdAggiudicazione(), aggBea.getDataInizioAggiudicazione());
						schedaSospensioni.setInizioLavori(inizioLavori);
						SimogValidator validator = ValidatorFactory.getValidator(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI, currentActiveConnection, logger);
	
						
						if(PSBD.ACTION_SALVA.equalsIgnoreCase(action)){
							bean = sospAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
							schedaSospensioni.setSospensioneFE(bean);
							if(bean.getIdSospensione() < 1 && isRefresh(request)){
								
								validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
								pagina = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?"
								+ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + infoGaraBean.getIdLotto() +
								"&"+ParametriServlet.FIELD_NAME_ID_INFO+"="+infoGaraBean.getIdInfo()
								+"&"+ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO+"="+infoGaraBean.getDataInizioInfo();
								
								sospAction.sendValidations(request, validator.getEccezioni());
								
								forward(pagina, request, response);
								return;
							}
								
							if(validator.valida(schedaSospensioni, null)){
								boolean inserimento = bean.getIdSospensione() <1;
								sospAction.save(bean, currentUser.getLogin());
								commit(currentActiveConnection);
								if(inserimento)
									validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_023);
								else validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_024);
								
								bean = sospAction.loadOne(bean.getIdSospensione(), bean.getDataInizioSosp());
								schedaSospensioni.setSospensioneFE(bean);
							}	
								
						}
						else if(PSBD.ACTION_CONFERMA.equalsIgnoreCase(action)){
							bean = sospAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
							schedaSospensioni.setSospensioneFE(bean);
							if(validator.valida(schedaSospensioni, null)){
								sospAction.confirm(bean, currentUser.getLogin());
								commit(currentActiveConnection);
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_025);
								bean = sospAction.loadOne(bean.getIdSospensione(), bean.getDataInizioSosp());
								schedaSospensioni.setSospensioneFE(bean);
								if(isEmpty(bean.getDataVerbRipr()) || isEmpty(bean.getFlagSuperoTemp()))	// XXX Condizioni che permettono una parziale modifica di una scheda confermata
									schedaSospensioni.setModificabile(true);
							}
						}
						else if(PSBD.ACTION_CARICA_JSP_ANNULLAMENTO.equalsIgnoreCase(action)){
							bean = sospAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
							schedaSospensioni.setSospensioneFE(bean);

							boolean isOk = (sospAction.bsa.hasSchedaRichDelete(String.valueOf(bean.getIdSospensione()), IdentificativoSchede.TAB_SOSPENSIONE, false).getFullSize()==0);
							if (isOk){
								request.setAttribute(PSBD.TAB, ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI);
								
								String dest = ParametriServlet.JSP_RICHIEDI_ANNULLAMENTO + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGaraBean.getIdAggiudicazione();
								dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGaraBean.getDataInizioAggiudicazione();
								dest+="&" + ParametriServletSospensioni.FIELD_NAME_ID_SOSPENSIONE + "=" + bean.getIdSospensione();
								dest+="&" + ParametriServletSospensioni.FIELD_NAME_ID_SOSPENSIONE + "=" + bean.getDataInizioSosp();
								forward(dest, request, response);
								return;
							}
							else{
								validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
							}
						}
						else if(PSBD.ACTION_CARICA_JSP_CANCELLAZIONE.equalsIgnoreCase(action)){
							bean = sospAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
							schedaSospensioni.setSospensioneFE(bean);

							boolean isOk = (sospAction.bsa.hasSchedaRichDelete(String.valueOf(bean.getIdSospensione()), IdentificativoSchede.TAB_SOSPENSIONE, true).getFullSize()==0);
							if (isOk){
								request.setAttribute(PSBD.TAB, ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI);
								
								String dest = ParametriServlet.JSP_RICHIEDI_CANCELLAZIONE + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGaraBean.getIdAggiudicazione();
								dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGaraBean.getDataInizioAggiudicazione();
								dest+="&" + ParametriServletSospensioni.FIELD_NAME_ID_SOSPENSIONE + "=" + bean.getIdSospensione();
								dest+="&" + ParametriServletSospensioni.FIELD_NAME_ID_SOSPENSIONE + "=" + bean.getDataInizioSosp();
								forward(dest, request, response);
								return;
							}
							else{
								validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
							}
						}
						else if(PSBD.ACTION_RICHIESTA_ANNULLAMENTO.equalsIgnoreCase(action)){
							bean = sospAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
							schedaSospensioni.setSospensioneFE(bean);
							String motivazione = request.getParameter(PSBD.MOTIVAZIONE_ANNULLAMENTO);
							String idLotto = Long.toString(infoGaraBean.getIdLotto());
							
							RichiestaAnnullamento raBean = new RichiestaAnnullamento();
							raBean.setId_lotto(idLotto);
							raBean.setMotivo_richiesta(motivazione);
							raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
							raBean.setRichiedente(currentUser.getLogin());
							boolean successo = false;
							raBean.setId_record(Long.toString(bean.getIdSospensione()));
							raBean.setData_inizio_record(bean.getDataInizioSosp());
							raBean.setBlocco(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI);
							Timestamp nuovadata = null;
							
							nuovadata = sospAction.richiediAnnullamento(raBean);
							
							successo = nuovadata != null;										

	                        // 3.02.2.1 accettazione immediata richiesta
	                        if(SimogFlags.is30230_RFWEBSC03Active()){
	                           
	                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(raBean.getBlocco(), currentActiveConnection, logger);
	                           
	                           raBean.setDecisore(currentUser.getLogin());
	                           raBean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
	                           raBean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
	                           
	                           successo = annAction.gestisciRichiesta(raBean,currentUser.getLogin());
	                        
	                           if(successo){
	                              validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RICHIESTA_ANNULLAMENTO_002);
	                              commit(currentActiveConnection);

                                  bean.setDataInizioSosp(nuovadata);
                                  bean = sospAction.loadOne(bean.getIdSospensione(),bean.getDataInizioSosp());                                
	                          }
	                          else{
	                              rollback(currentActiveConnection);
	                              validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
	                              bean = new SospensioniBean(); 
	                          }
	                       }
	                       else{                          

      							if(successo){
      								commit(currentActiveConnection);
      								bean.setDataInizioSosp(nuovadata);
      								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_080);
      								bean = sospAction.loadOne(bean.getIdSospensione(),bean.getDataInizioSosp());
      							}
      							else{
      								
      								rollback(currentActiveConnection);
      								validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
      								bean = new SospensioniBean(); 
      							}
	                       }
      						
	                        schedaSospensioni.setSospensioneFE(bean);
						}
						
						else if(PSBD.ACTION_RICHIESTA_CANCELLAZIONE.equalsIgnoreCase(action)){
							bean = sospAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
							schedaSospensioni.setSospensioneFE(bean);
							String motivazione = request.getParameter(PSBD.MOTIVAZIONE_CANCELLAZIONE);
							String idLotto = Long.toString(infoGaraBean.getIdLotto());
							
							RichiestaAnnullamento raBean = new RichiestaAnnullamento();
							raBean.setCancellazione(Costanti.FLAG_VALORE_NO) ;
							raBean.setId_lotto(idLotto);
							raBean.setMotivo_richiesta(motivazione);
							raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
							raBean.setRichiedente(currentUser.getLogin());
							raBean.setId_record(Long.toString(bean.getIdSospensione()));
							raBean.setData_inizio_record(bean.getDataInizioSosp());
							raBean.setBlocco(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI);
							
							sospAction.richiediCancellazione(raBean);
							
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
	                                schedaSospensioni.setSospensioneFE(bean);
	                                bean = new SospensioniBean(); 
	                               
	                               // forward a lista aggiudicazioni
	                               pagina = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA
	                                   + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO+"="+raBean.getId_lotto()
	                                   + "&" + ParametriServlet.START_ROW + "=0"
	                                   + "&" + "toDo=" + PSBD.ACTION_LOAD; // patch se va su daticomuni
	                           }else{
	                               rollback(currentActiveConnection);
	                               validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_CANCELLAZIONE_001);
	                              }
	                        }
                           else{							
      							commit(currentActiveConnection);
      							schedaSospensioni.setSospensioneFE(bean);
      							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_082);
      							bean = sospAction.loadOne(bean.getIdSospensione(),bean.getDataInizioSosp());
                           }		
							schedaSospensioni.setSospensioneFE(bean);
						}
						
						else if(PSBD.ACTION_REIMPOSTA.equalsIgnoreCase(action)){
							bean = sospAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
							if(bean.getIdSospensione() > 0){
								bean = sospAction.loadOne(bean.getIdSospensione(), bean.getDataInizioSosp());
								schedaSospensioni.setSospensioneFE(bean);
								validator.valida(schedaSospensioni, null);
							}
						}
						else if(PSBD.ACTION_MODIFICA.equalsIgnoreCase(action)){
							bean = sospAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
							/* Carico la scheda da modificare, aggiungo le modifiche e confermo */
							SospensioniBean reviewBean = sospAction.loadOne(bean.getIdSospensione(), bean.getDataInizioSosp());
							reviewBean.setDataVerbRipr(bean.getDataVerbRipr());
							reviewBean.setFlagSuperoTemp(bean.getFlagSuperoTemp());
							schedaSospensioni.setSospensioneFE(reviewBean);
							if(validator.valida(schedaSospensioni, null)){
								sospAction.confirm(reviewBean, currentUser.getLogin());
								commit(currentActiveConnection);
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_024);
								if(isEmpty(reviewBean.getDataVerbRipr()) || isEmpty(reviewBean.getFlagSuperoTemp()))		// XXX Condizioni che permettono una parziale modifica di una scheda confermata
									schedaSospensioni.setModificabile(true); 
							} 
							else schedaSospensioni.setModificabile(true);
						}
						
						List<SospensioniBean> beanList = sospAction.loadAllByAgg(infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						currentActiveSession.setAttribute("lista_sospensioni", beanList);
						schedaSospensioni.setNavigationBean(getNavBean(infoGaraBean.getIdAggiudicazione(), ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI, request.getSession()));
						
						if ( bean != null ) {
							bean.setOkCancellazione(sospAction.bsa.isCancellabile(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI, 
									bean.getIdSospensione(), 
									bean.getDataInizioSosp(),
									bean.getIdStato(),
									infoGaraBean.getTipoEnte(), 
									infoGaraBean.getTipoContratto(),
									bean.getIdAggiudicazione(),
									bean.getDataInizioAggiudicazione()));						
						}
						
						ConclusioniAction cAction = new ConclusioniAction(currentActiveConnection,logger);
						ConclusioneBean conclusionebean = cAction.load(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
						if(conclusionebean != null && conclusionebean.getIdStato() > 0){
							schedaSospensioni.setAggiungibile(false);
						}else{
							schedaSospensioni.setAggiungibile(true);
						}
						
						/***************** DETTAGLI RICH ANN ************************/

						TableBean tabRichAnn = sospAction.bsa.getRichAnnByScheda(
								String.valueOf(schedaSospensioni.getSospensioneFE().getIdSospensione()), 
								ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI, false);
						
						request.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
						
						/***************** END              *************************/
						
						request.setAttribute("schedaSospensioni", schedaSospensioni);
						request.setAttribute(ParametriServlet.MOTIVI_SOSPENSIONE_BEAN, sospAction.loadMotiviSospensione(null));
						
						//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
						if(bean != null && bean.getIdStato() == StatiScheda.CONFERMATO)
						    sospAction.sendValidations(request, validator.getEccezioni().getAllInfoEWarn());
						else
							sospAction.sendValidations(request, validator.getEccezioni());
						
						forward(ParametriServletSospensioni.JSP_SCHEDA_SOSPENSIONI, request, response);
						return;
					}catch (Exception e) {
						logger.fatal(e);
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
	
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) req.getSession().getAttribute(UTENTE);
		if (checkSession(req)) {
			
			if (currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()) {
				try {
				
					currentActiveConnection = getSimogConnection(req.getSession().getId(),getClass().getName());
					setDatiAggiudicazione(req);
					String action = req.getParameter("toDo");
					HttpSession session = req.getSession();
					SospensioniBean bean = null;
					SchedaSospensione sospensione = null;
					Timestamp dataInizioScheda = null;
					InfoGaraBean infoGara = getDatiGara(req.getSession()); 
					SospensioniAction rAction = new SospensioniAction(currentActiveConnection,logger);
					if("loadAll".equalsIgnoreCase(action)){
						//richiesta visualizzazione/modifica/conferma schede sospensione
						
						//InfoGaraBean infoGaraBean = (new InfoComuniAction(currentActiveConnection,logger)).loadInfoGara(idLotto);
						List<SospensioniBean> beanList = rAction.loadAllByAgg(infoGara.getIdAggiudicazione(),infoGara.getDataInizioAggiudicazione());
						session.setAttribute("lista_sospensioni", beanList);
						
						AggiudicazioneBean aggBea = rAction.getAggiudicazione(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						req.setAttribute("aggiudicazione",aggBea) ;

					}
					else if ("load".equalsIgnoreCase(action)) {
						//modifica scheda
						String toEdit = req.getParameter("toEdit");
						
						List<SospensioniBean> beanList =(List<SospensioniBean>) session.getAttribute("lista_sospensioni");
						if("-1".equals(toEdit)){
							bean = new SospensioniBean();
							sospensione = new SchedaSospensione();
							sospensione.setSospensioneFE(bean);
						}
						else{ 
							bean = beanList.get(Integer.parseInt(toEdit));
							sospensione = new SchedaSospensione();
							sospensione.setSospensioneFE(bean);

							SospensioniAction sospAction = new SospensioniAction(currentActiveConnection,logger);
							InfoGaraBean infoGaraBean =getDatiGara(req.getSession());

							bean.setOkCancellazione(sospAction.bsa.isCancellabile(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI, 
									bean.getIdSospensione(), 
									bean.getDataInizioSosp(),
									bean.getIdStato(),
									infoGaraBean.getTipoEnte(), 
									infoGaraBean.getTipoContratto(),
									bean.getIdAggiudicazione(),
									bean.getDataInizioAggiudicazione()));		

							/***/
							AggiudicazioneBean aggBea = sospAction.getAggiudicazione(infoGaraBean .getIdAggiudicazione(), infoGaraBean .getDataInizioAggiudicazione());
							sospensione.setAggiudicazione(aggBea);
							InfoComuniBean infBea = sospAction.getInfoComuni(aggBea.getIdInfo(), aggBea.getDataInizioInfo());
							sospensione.setInfoComuni(infBea);
							InizioLavoriBean inizioLavori = sospAction.getInizioLavori(aggBea.getIdAggiudicazione(), aggBea.getDataInizioAggiudicazione());
							sospensione.setInizioLavori(inizioLavori);
							SimogValidator validator = ValidatorFactory.getValidator(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI, currentActiveConnection, logger);
							validator.valida(sospensione, null);
							
							//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
							if(bean != null && bean.getIdStato() == StatiScheda.CONFERMATO)
							    sospAction.sendValidations(req, validator.getEccezioni().getAllInfoEWarn());
							else
								sospAction.sendValidations(req, validator.getEccezioni());
							
							/***/
						}
						
					}if(bean == null){
						bean = new SospensioniBean();
						sospensione = new SchedaSospensione();
						sospensione.setSospensioneFE(bean);
					}
					
					req.setAttribute(ParametriServlet.MOTIVI_SOSPENSIONE_BEAN, rAction.loadMotiviSospensione(dataInizioScheda));
					sospensione.setNavigationBean(getNavBean(infoGara.getIdAggiudicazione(), ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI, req.getSession()));
					
					ConclusioniAction cAction = new ConclusioniAction(currentActiveConnection,logger);
					ConclusioneBean conclusionebean = cAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
					if(conclusionebean != null && conclusionebean.getIdStato() > 0){
						sospensione.setAggiungibile(false);
					}else{
						sospensione.setAggiungibile(true);
					}
					
					// XXX Condizioni che permettono una parziale modifica di una scheda confermata
					if(isEmpty(sospensione.getSospensioneFE().getDataVerbRipr()) ||
					   isEmpty(sospensione.getSospensioneFE().getFlagSuperoTemp())) 
						sospensione.setModificabile(true);
					
					/***************** DETTAGLI RICH ANN ************************/
					
					TableBean tabRichAnn = rAction.bsa.getRichAnnByScheda(
							String.valueOf(sospensione.getSospensioneFE().getIdSospensione()), 
							ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI, false);
					
					req.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
					
					/***************** END              *************************/
					
					req.setAttribute("schedaSospensioni", sospensione);
					forward(ParametriServletSospensioni.JSP_SCHEDA_SOSPENSIONI, req, resp);
					return;
				}catch (Exception e) {
					logger.fatal(e);					
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

	/***********************************************************************************************
	 * Accerta se una Stringa sia vuota o null. 
	 * @param o Object
	 * @return boolean
	 */
	private boolean isEmpty(String o){
		return(o == null || (o.trim().length() == 0));
	}
}
