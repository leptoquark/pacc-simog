package it.avlp.simog.servlet.subappalti;

import it.avcp.simog.managers.subappalti.SubappaltiManager;
import it.avlp.simog.actions.collaudo.CollaudoAction;
import it.avlp.simog.actions.subappalti.SubappaltiAction;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.beans.subappalti.SchedaSubAppalti;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.common.action.BaseRichiestaAnnullamento;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletSospensioni;
import it.avlp.simog.common.servlet.ParametriServletSubappalti;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.factory.AnnullamentoFactory;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.factory.ValidatorFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import it.avlp.simog.beans.subappalti.SubappaltatoreBean; 

public class SrvSchedaSubappalti extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5660954310115825564L;

	@Override
	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if(checkSession(request)){
			if (currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()) {
				try{
					
					request.setAttribute(PSBD.NOME_SCHEDA,IdentificativoSchede.getSubAppalti().getDecodificaVN()) ;

					
					visualizzaListaParametriValori(request, response);
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					currentActiveConnection.setAutoCommit(false);
					
					String action = request.getParameter("toDo");
					if(action == null)
						action = request.getParameter(PSBD.ACTION_TYPE);
					
					/** Consente solo l'operazione di caricamento all'Osservaotre regionale */
					if(action == null || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA())
						action = PSBD.ACTION_LOAD;
					
					HttpSession session = request.getSession();
					String pagina = null; 
										 
					SubappaltiAction sAction = new SubappaltiAction(currentActiveConnection,logger);
					InfoGaraBean infoGaraBean =getDatiGara(request.getSession());
					SubappaltiBean bean = new SubappaltiBean(); 
					SchedaSubAppalti schedaSubappalti = new SchedaSubAppalti();
					schedaSubappalti.setSubAppaltiFE(bean); 
					AggiudicazioneBean aggBea = sAction.getAggiudicazione(infoGaraBean .getIdAggiudicazione(), infoGaraBean .getDataInizioAggiudicazione());
					schedaSubappalti.setAggiudicazione(aggBea);
					InfoComuniBean infBea = sAction.getInfoComuni(aggBea.getIdInfo(), aggBea.getDataInizioInfo());
					schedaSubappalti.setInfoComuni(infBea);
					InizioLavoriBean inizioLavori = sAction.getInizioLavori(aggBea.getIdAggiudicazione(), aggBea.getDataInizioAggiudicazione());
					schedaSubappalti.setInizioLavori(inizioLavori);
	 
					//gm nuovo codice 3.0
					List<AggiudicatarioBean> aggiudicatari = sAction.getAggiudicatari(aggBea.getIdAggiudicazione(), aggBea.getDataInizioAggiudicazione());
					schedaSubappalti.setAggiudicatari(aggiudicatari);
					//gm fine nuovo codice 3.0
					
					SimogValidator validator = ValidatorFactory.getValidator(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI, currentActiveConnection, logger);
					
					
					
					if(PSBD.ACTION_SALVA.equalsIgnoreCase(action)){
						bean = sAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						schedaSubappalti.setSubAppaltiFE(bean); 
						if(bean.getIdRecord() < 1 && isRefresh(request)){
							
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
							pagina = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?"
							+ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + infoGaraBean.getIdLotto() +
							"&"+ParametriServlet.FIELD_NAME_ID_INFO+"="+infoGaraBean.getIdInfo()
							+"&"+ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO+"="+infoGaraBean.getDataInizioInfo();
							
							sAction.sendValidations(request, validator.getEccezioni());
							
							forward(pagina, request, response);
							return;
						}
						
						if(validator.valida(schedaSubappalti, null)){
							boolean inserimento = bean.getIdRecord() <1;
							sAction.save(bean, currentUser.getLogin());
							
							//TICKET ALM - 3.04.3
							SubappaltiManager subManager = new SubappaltiManager(currentActiveConnection,this.logger);
							//Fai scadere le precedenti ditte e, se sono state indicate, inserisci le nuove
							subManager.expireDitteSubappaltatrici(bean);
							for (SubappaltatoreBean subBean : bean.getSubappaltatori()) {
								subBean.setIdSubappalto(bean.getIdRecord());
								subBean.setDataInizioSubappalto(bean.getDataInizioRecord());
								subManager.insertDitteSubappaltatrici(subBean);
							}
							//FINE TICKET ALM - 3.04.3 #4198
							
							commit(currentActiveConnection);
							if(inserimento){
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_023);
							}else {
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_024);
							}
							bean = sAction.loadOne(bean.getIdRecord(), bean.getDataInizioRecord());
							schedaSubappalti.setSubAppaltiFE(bean);
						}
					}
					else if(PSBD.ACTION_CONFERMA.equalsIgnoreCase(action)){
						bean = sAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						schedaSubappalti.setSubAppaltiFE(bean);
						if(validator.valida(schedaSubappalti, null)){
							sAction.confirm(bean, currentUser.getLogin());
							
							//TICKET ALM - 3.04.3
							SubappaltiManager subManager = new SubappaltiManager(currentActiveConnection,this.logger);
							//Fai scadere le precedenti ditte e, se sono state indicate, inserisci le nuove
							subManager.expireDitteSubappaltatrici(bean);
							for (SubappaltatoreBean subBean : bean.getSubappaltatori()) {
								subBean.setIdSubappalto(bean.getIdRecord());
								subBean.setDataInizioSubappalto(bean.getDataInizioRecord());
								subManager.insertDitteSubappaltatrici(subBean);
							}
							//FINE TICKET ALM - 3.04.3 #4198
							
							commit(currentActiveConnection);
							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_025);
							bean = sAction.loadOne(bean.getIdRecord(), bean.getDataInizioRecord());
							schedaSubappalti.setSubAppaltiFE(bean);
							if(isZero(bean.getImportoEffettivo()))	// XXX Condizioni che permettono una parziale modifica di una scheda confermata
								schedaSubappalti.setModificabile(true);
						}
					}
					else if(PSBD.ACTION_CARICA_JSP_ANNULLAMENTO.equalsIgnoreCase(action)){
						bean = sAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());

						boolean isOk = (sAction.bsa.hasSchedaRichDelete(String.valueOf(bean.getIdRecord()), IdentificativoSchede.TAB_SUBAPPALTO, false).getFullSize()==0);
						if (isOk){
							request.setAttribute(PSBD.TAB, ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI);
							request.setAttribute(ParametriServlet.SHOW_DATI_COMUNI, "true");
							String dest = ParametriServlet.JSP_RICHIEDI_ANNULLAMENTO + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGaraBean.getIdAggiudicazione();
							dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGaraBean.getDataInizioAggiudicazione();
							dest+="&" + ParametriServletSubappalti.FIELD_NAME_ID_RECORD + "=" + bean.getIdRecord();
							dest+="&" + ParametriServletSubappalti.FIELD_NAME_ID_RECORD + "=" + bean.getDataInizioRecord();
							forward(dest, request, response);
							return;
						}
						else{
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					else if(PSBD.ACTION_CARICA_JSP_CANCELLAZIONE.equalsIgnoreCase(action)){
						bean = sAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());

						boolean isOk = (sAction.bsa.hasSchedaRichDelete(String.valueOf(bean.getIdRecord()), IdentificativoSchede.TAB_SUBAPPALTO, true).getFullSize()==0);
						if (isOk){
							request.setAttribute(PSBD.TAB, ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI);
							request.setAttribute(ParametriServlet.SHOW_DATI_COMUNI, "true");
							String dest = ParametriServlet.JSP_RICHIEDI_CANCELLAZIONE + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGaraBean.getIdAggiudicazione();
							dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGaraBean.getDataInizioAggiudicazione();
							dest+="&" + ParametriServletSubappalti.FIELD_NAME_ID_RECORD + "=" + bean.getIdRecord();
							dest+="&" + ParametriServletSubappalti.FIELD_NAME_ID_RECORD + "=" + bean.getDataInizioRecord();
							forward(dest, request, response);
							return;
						}
						else{
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					else if(PSBD.ACTION_RICHIESTA_ANNULLAMENTO.equalsIgnoreCase(action)){
						bean = sAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						schedaSubappalti.setSubAppaltiFE(bean);
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
						raBean.setBlocco(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI);
						Timestamp nuovadata = null;
						
						nuovadata = sAction.richiediAnnullamento(raBean);
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
                              bean = sAction.loadOne(bean.getIdRecord(), bean.getDataInizioRecord());
                          }
                          else{
                              rollback(currentActiveConnection);
                              validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
                              //Un bean vuoto con il numero progessivo settato
                              bean = new SubappaltiBean();
                          }
                       }
                       else{                          
      						if(successo){
      							commit(currentActiveConnection);
      							bean.setDataInizioRecord(nuovadata);
      							schedaSubappalti.setSubAppaltiFE(bean);
      							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_080);
      							bean = sAction.loadOne(bean.getIdRecord(), bean.getDataInizioRecord());
      						}
      						else{
      							rollback(currentActiveConnection);
      							validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
      							bean = new SubappaltiBean();
      						}
                       }
						schedaSubappalti.setSubAppaltiFE(bean);
					}
					else if(PSBD.ACTION_RICHIESTA_CANCELLAZIONE.equalsIgnoreCase(action)){
						bean = sAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						schedaSubappalti.setSubAppaltiFE(bean);
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
						raBean.setBlocco(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI);
						
						sAction.richiediCancellazione(raBean);

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
                              bean = new SubappaltiBean();
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
      						schedaSubappalti.setSubAppaltiFE(bean);
      						validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_082);
      						bean = sAction.loadOne(bean.getIdRecord(), bean.getDataInizioRecord());
                        }
						schedaSubappalti.setSubAppaltiFE(bean);
					}
					else if(PSBD.ACTION_REIMPOSTA.equalsIgnoreCase(action)){
						bean = sAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						if(bean.getIdRecord() > 0){
							bean = sAction.loadOne(bean.getIdRecord(), bean.getDataInizioRecord());
							schedaSubappalti.setSubAppaltiFE(bean);
							validator.valida(schedaSubappalti, null);
						}
					}
					
					else if(PSBD.ACTION_MODIFICA.equalsIgnoreCase(action)){
						bean = sAction.getBean(request,infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
						/* Carico la scheda da modificare, aggiungo le modifiche e confermo */
						SubappaltiBean reviewBean = sAction.loadOne(bean.getIdRecord(), bean.getDataInizioRecord());
						reviewBean.setImportoEffettivo(bean.getImportoEffettivo());
						schedaSubappalti.setSubAppaltiFE(reviewBean);
						if(validator.valida(schedaSubappalti, null)){
							sAction.confirm(reviewBean, currentUser.getLogin());
							commit(currentActiveConnection);
							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_024);
							if(isZero(bean.getImportoEffettivo()))	// XXX Condizioni che permettono una parziale modifica di una scheda confermata
								schedaSubappalti.setModificabile(true);
						} 
						else schedaSubappalti.setModificabile(true);
					}

				List<SubappaltiBean> beanList = sAction.loadAllByAgg(infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione());
				session.setAttribute("lista_subappalti", beanList);
				schedaSubappalti.setNavigationBean(getNavBean(infoGaraBean.getIdAggiudicazione(), ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI, request.getSession()));
				
				if ( bean != null )  {
					bean.setOkCancellazione(sAction.bsa.isCancellabile(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI, 
							bean.getIdRecord(), 
							bean.getDataInizioRecord(),
							bean.getIdStato(),
							infoGaraBean.getTipoEnte(), 
							infoGaraBean.getTipoContratto(),
							bean.getIdAggiudicazione(),
							bean.getDataInizioAggiudicazione()));		
				}
				
				CollaudoAction collaudoAction = new CollaudoAction(currentActiveConnection,logger);
				CollaudoBean collaudoBean = collaudoAction.load(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
				if(collaudoBean.getEsitoCollaudo() == null){
					schedaSubappalti.setAggiungibile(true);
				}else{
					schedaSubappalti.setAggiungibile(false);
				}
				
				/***************** DETTAGLI RICH ANN ************************/
				
				TableBean tabRichAnn = sAction.bsa.getRichAnnByScheda(
						String.valueOf(schedaSubappalti.getSubAppaltiFE().getIdRecord()), 
						ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI, false);
				
				request.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
				
				/***************** END              *************************/
				
				request.setAttribute("schedaSubappalti", schedaSubappalti);
				request.setAttribute("categorie", sAction.laodCategorie(null));	
				
				//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
				if(bean != null && bean.getIdStato() == StatiScheda.CONFERMATO)
				    sAction.sendValidations(request, validator.getEccezioni().getAllInfoEWarn());
				else
					sAction.sendValidations(request, validator.getEccezioni());
				
				
				//TICKET ALM - 3.04.3 #4198
				setDatiGara(infoGaraBean, request.getSession());
				
				forward(ParametriServletSubappalti.JSP_SCHEDA_SUBAPPALTI, request, response);
				return;
			}catch (Exception e) {
				//e.printStackTrace();
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
	public void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) req.getSession().getAttribute(UTENTE);
		if (checkSession(req)) {
			if (currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()) {
				try {
					currentActiveConnection = getSimogConnection(req.getSession().getId(),getClass().getName());
					setDatiAggiudicazione(req);
					String action = req.getParameter("toDo");
					HttpSession session = req.getSession();
					SubappaltiBean bean = null;
					SchedaSubAppalti schedaSubappalti = new SchedaSubAppalti();

					Timestamp dataInizioScheda = null;
					InfoGaraBean infoGara = getDatiGara(req.getSession());
				
					SubappaltiAction sAction = new SubappaltiAction(currentActiveConnection,logger);

					InfoGaraBean infoGaraBean =getDatiGara(req.getSession());
					AggiudicazioneBean aggBea = sAction.getAggiudicazione(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
					List<AggiudicatarioBean> aggiudicatari = sAction.getAggiudicatari(aggBea.getIdAggiudicazione(), aggBea.getDataInizioAggiudicazione());
					schedaSubappalti.setAggiudicazione(aggBea);
					InfoComuniBean infBea = sAction.getInfoComuni(aggBea.getIdInfo(), aggBea.getDataInizioInfo());
					schedaSubappalti.setInfoComuni(infBea);
					InizioLavoriBean inizioLavori = sAction.getInizioLavori(aggBea.getIdAggiudicazione(), aggBea.getDataInizioAggiudicazione());
					schedaSubappalti.setInizioLavori(inizioLavori);
					schedaSubappalti.setAggiudicatari(aggiudicatari);
					
					if("loadAll".equalsIgnoreCase(action)){
						
						List<SubappaltiBean> beanList = sAction.loadAllByAgg(infoGara.getIdAggiudicazione(),infoGara.getDataInizioAggiudicazione());
						session.setAttribute("lista_subappalti", beanList);
						req.setAttribute("categorie", sAction.laodCategorie(dataInizioScheda));
						
					}
					else if ("load".equalsIgnoreCase(action)) {
						String toEdit = req.getParameter("toEdit");
						try{
							List<SubappaltiBean> beanList =(List<SubappaltiBean>) session.getAttribute("lista_subappalti");


							if("-1".equals(toEdit)){
								bean = new SubappaltiBean();
								schedaSubappalti.setSubAppaltiFE(bean);								
							}
							else{ 
								bean = beanList.get(Integer.parseInt(toEdit));
								schedaSubappalti.setSubAppaltiFE(bean);

								bean.setOkCancellazione(sAction.bsa.isCancellabile(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI, 
										bean.getIdRecord(), 
										bean.getDataInizioRecord(),
										bean.getIdStato(),
										infoGaraBean.getTipoEnte(), 
										infoGaraBean.getTipoContratto(),
										bean.getIdAggiudicazione(),
										bean.getDataInizioAggiudicazione()));		
								
								/***/
								SimogValidator validator = ValidatorFactory.getValidator(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI, currentActiveConnection, logger);
								
								validator.valida(schedaSubappalti, null);
								
								//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
								if(bean != null && bean.getIdStato() == StatiScheda.CONFERMATO)
								    sAction.sendValidations(req, validator.getEccezioni().getAllInfoEWarn());
								else
									sAction.sendValidations(req, validator.getEccezioni());
								
								/***/
							}						
						}catch (Exception e) {
							//e.printStackTrace();
							logger.fatal(e);
						}
					}
					
					if(bean == null){
						bean = new SubappaltiBean();
						schedaSubappalti.setSubAppaltiFE(bean);					
					}
					
					schedaSubappalti.setNavigationBean(getNavBean(infoGara.getIdAggiudicazione(), ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI, req.getSession()));
					CollaudoAction collaudoAction = new CollaudoAction(currentActiveConnection,logger);
					CollaudoBean collaudoBean = collaudoAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
					if(collaudoBean.getEsitoCollaudo() == null){
						schedaSubappalti.setAggiungibile(true);
					}else{
						schedaSubappalti.setAggiungibile(false);
					}
					
					if(isZero(bean.getImportoEffettivo()))	// XXX Condizioni che permettono una parziale modifica di una scheda confermata
						schedaSubappalti.setModificabile(true);

					/***************** DETTAGLI RICH ANN ************************/
					
					TableBean tabRichAnn = sAction.bsa.getRichAnnByScheda(
							String.valueOf(schedaSubappalti.getSubAppaltiFE().getIdRecord()), 
							ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI, false);
					
					req.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
					
					/***************** END              *************************/
					
					/*HashMap<String,String> tipologicaM = new HashMap<String,String>();
					tipologicaM.put("tipo01", "tipo01");
					tipologicaM.put("tipo02", "tipo02");
					tipologicaM.put("tipo03", "tipo03");*/
					req.setAttribute(ParametriServlet.TIPO_AGGIUDICATARIO_BEAN, sAction.loadTipoAggiudicatario(dataInizioScheda)); 
					req.setAttribute("schedaSubappalti",schedaSubappalti);
					req.setAttribute("categorie", sAction.laodCategorie(dataInizioScheda));
					
					forward(ParametriServletSubappalti.JSP_SCHEDA_SUBAPPALTI, req, resp);
					return;
				} catch (Exception e) {
				//	e.printStackTrace();
					logger.fatal(e);
					rollback(currentActiveConnection);
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
	private boolean isZero(BigDecimal o){
		return(o == null || (o.doubleValue() == 0));
	}
}
