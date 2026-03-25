package it.avlp.simog.servlet.conclusione;

import it.avlp.simog.actions.conclusione.ConclusioniAction;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.conclusione.SchedaConclusione;
import it.avlp.simog.common.action.BaseRichiestaAnnullamento;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletCollaudo;
import it.avlp.simog.common.servlet.ParametriServletConclusioni;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.factory.AnnullamentoFactory;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.factory.ValidatorFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SrvSchedaConclusioni extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6618187237112656495L;

	@Override
	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if(checkSession(request)){
			if(currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()){
				try{
					
					request.setAttribute(PSBD.NOME_SCHEDA,IdentificativoSchede.getConclusione().getDecodificaVN()) ;
					
					setDatiAggiudicazione(request);
					visualizzaListaParametriValori(request, response);
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					currentActiveConnection.setAutoCommit(false);
					String action = request.getParameter("toDo");
					Timestamp dataInizioScheda = null;
					
					if(action == null || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA())
						action = PSBD.ACTION_LOAD;
					
					String pagina = null;
					
					ConclusioniAction cAction = new ConclusioniAction(currentActiveConnection,logger);
					InfoGaraBean infoGaraBean =getDatiGara(request.getSession());
					ConclusioneBean bean = new ConclusioneBean();
					SchedaConclusione schedaConclusione = new SchedaConclusione();
					schedaConclusione.setConclusione(bean);
					AggiudicazioneBean agg = cAction.getAggiudicazione(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
					schedaConclusione.setAggiudicazione(agg);
					schedaConclusione.setInfoComuni(cAction.getInfoComuni(infoGaraBean.getIdInfo(), infoGaraBean.getDataInizioInfo()));
					
					schedaConclusione.setInizioLavori(cAction.getInizioLavori(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione()));
					schedaConclusione.setAvanzamenti(cAction.getAvanzamenti(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione()));
							
					SimogValidator validator = ValidatorFactory.getValidator(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI, currentActiveConnection, logger);
					
					
					if(PSBD.ACTION_LOAD.equalsIgnoreCase(action)){
						bean = cAction.load(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
						if(bean != null){
							schedaConclusione.setConclusione(bean);
							//add 2608
							dataInizioScheda = bean.getDataIniUltim();
						}
						else{
							// PP imposto i campi ereditati da inizio lavori, solo se vengo dalla prima invocazione
							if(request.getMethod().equals("GET")){
								schedaConclusione.getConclusione().setDataConsegna(schedaConclusione.getInizioLavori().getDataVerbaleDef());
								schedaConclusione.getConclusione().setTermineUltimazione(schedaConclusione.getInizioLavori().getDataTermine());
							}
						}

						if(bean != null && bean.getIdUltim() > 0)
							validator.valida(schedaConclusione, null);
						
						
					}
					else if(PSBD.ACTION_SALVA.equalsIgnoreCase(action)){
						bean = cAction.getBean(request, infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());

						if(bean.getIdUltim() < 1 && isRefresh(request)){
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
							pagina = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?"
							+ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + infoGaraBean.getIdLotto() +
							"&"+ParametriServlet.FIELD_NAME_ID_INFO+"="+infoGaraBean.getIdInfo()
							+"&"+ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO+"="+infoGaraBean.getDataInizioInfo();
							
							cAction.sendValidations(request, validator.getEccezioni());
							
							forward(pagina, request, response);
							return;
						}
						
						// se la scheda esiste impedisco l'operazione
						if(bean.getIdUltim() < 1 && cAction.load(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione())!=null && cAction.load(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione()).getIdUltim()>0){
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
							pagina = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?"
							+ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + infoGaraBean.getIdLotto() +
							"&"+ParametriServlet.FIELD_NAME_ID_INFO+"="+infoGaraBean.getIdInfo()
							+"&"+ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO+"="+infoGaraBean.getDataInizioInfo();
							
							cAction.sendValidations(request, validator.getEccezioni());
							
							forward(pagina, request, response);
							return;
						}
													
						schedaConclusione.setConclusione(bean);
						if(validator.valida(schedaConclusione, null)){
							boolean inserimento = bean.getIdUltim() < 1;
							cAction.save(bean, currentUser.getLogin());
							commit(currentActiveConnection);
							if(inserimento){
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_023);
							}
							else {
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_024);
							}
							bean = cAction.load(bean.getIdAggiudicazione(), bean.getDataInizioAggiudicazione());
							schedaConclusione.setConclusione(bean);
						}
					}
					else if(PSBD.ACTION_CONFERMA.equalsIgnoreCase(action)){
						bean = cAction.getBean(request, infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
						schedaConclusione.setConclusione(bean);
						if(validator.valida(schedaConclusione, null)){
							cAction.confirm(bean, currentUser.getLogin());
							commit(currentActiveConnection);
							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_025);
							bean = cAction.load(bean.getIdAggiudicazione(), bean.getDataInizioAggiudicazione());
							schedaConclusione.setConclusione(bean);
						}
					}
					else if(PSBD.ACTION_CARICA_JSP_ANNULLAMENTO.equalsIgnoreCase(action)){
						bean = cAction.getBean(request, infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
						schedaConclusione.setConclusione(bean);
						
						boolean isOk = (cAction.bsa.hasSchedaRichDelete(String.valueOf(bean.getIdUltim()), IdentificativoSchede.TAB_FINELAVORI, false).getFullSize()==0);
						if (isOk){
							request.setAttribute(PSBD.TAB, ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI);
							request.setAttribute(ParametriServlet.SHOW_DATI_COMUNI, "true");
							String dest = ParametriServlet.JSP_RICHIEDI_ANNULLAMENTO + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGaraBean.getIdAggiudicazione();
							dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGaraBean.getDataInizioAggiudicazione();
							dest+="&" + ParametriServletConclusioni.FIELD_NAME_ID_ULTIM + "=" + bean.getIdUltim();
							dest+="&" + ParametriServletConclusioni.FIELD_NAME_DATA_INIZIO_ULTIM + "=" + bean.getDataIniUltim();
							forward(dest, request, response);
							return;
						}
						else{
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					else if(PSBD.ACTION_CARICA_JSP_CANCELLAZIONE.equalsIgnoreCase(action)){

						bean = cAction.getBean(request, infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
						schedaConclusione.setConclusione(bean);

						boolean isOk = (cAction.bsa.hasSchedaRichDelete(String.valueOf(bean.getIdUltim()), IdentificativoSchede.TAB_FINELAVORI, true).getFullSize()==0);
						if (isOk){
							request.setAttribute(PSBD.TAB, ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI);
							request.setAttribute(ParametriServlet.SHOW_DATI_COMUNI, "true");
							String dest = ParametriServlet.JSP_RICHIEDI_CANCELLAZIONE + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGaraBean.getIdAggiudicazione();
							dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGaraBean.getDataInizioAggiudicazione();
							dest+="&" + ParametriServletConclusioni.FIELD_NAME_ID_ULTIM + "=" + bean.getIdUltim();
							dest+="&" + ParametriServletConclusioni.FIELD_NAME_DATA_INIZIO_ULTIM + "=" + bean.getDataIniUltim();
							forward(dest, request, response);
							return;
						}
						else{
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					else if(PSBD.ACTION_RICHIESTA_ANNULLAMENTO.equalsIgnoreCase(action)){
						bean = cAction.getBean(request, infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
						schedaConclusione.setConclusione(bean);
						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_ANNULLAMENTO);
						String idLotto = Long.toString(infoGaraBean.getIdLotto());
						RichiestaAnnullamento raBean = new RichiestaAnnullamento();
						raBean.setId_lotto(idLotto);
						raBean.setMotivo_richiesta(motivazione);
						raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						raBean.setRichiedente(currentUser.getLogin());
						boolean successo = false;
						raBean.setId_record(Long.toString(bean.getIdUltim()));
						raBean.setData_inizio_record(bean.getDataIniUltim());
						raBean.setBlocco(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI);
						Timestamp nuovadata = null;
						nuovadata = cAction.richiestaAnnullamento(raBean);
						successo = nuovadata != null;

                        // 3.02.2.1 accettazione immediata richiesta
                        if(SimogFlags.is30230_RFWEBSC03Active()){
                           
                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(raBean.getBlocco(), currentActiveConnection, logger);
                           
                           raBean.setDecisore(currentUser.getLogin());
                           raBean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
                           raBean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
                           
                           successo = annAction.gestisciRichiesta(raBean,currentUser.getLogin());
                        
                           if(successo){
                              bean.setDataIniUltim(nuovadata);
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
      							bean.setDataIniUltim(nuovadata);
      							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_080);
      						}
      						else{
      							rollback(currentActiveConnection);
      							validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
      						}
                       }
      					bean = cAction.load(bean.getIdAggiudicazione(), bean.getDataInizioAggiudicazione());
						schedaConclusione.setConclusione(bean);
					}
					else if(PSBD.ACTION_RICHIESTA_CANCELLAZIONE.equalsIgnoreCase(action)){
						bean = cAction.getBean(request, infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
						schedaConclusione.setConclusione(bean);
						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_CANCELLAZIONE);
						String idLotto = Long.toString(infoGaraBean.getIdLotto());
						
						RichiestaAnnullamento raBean = new RichiestaAnnullamento();
						raBean.setCancellazione(Costanti.FLAG_VALORE_NO) ;
						raBean.setId_lotto(idLotto);
						raBean.setMotivo_richiesta(motivazione);
						raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						raBean.setRichiedente(currentUser.getLogin());
						raBean.setId_record(Long.toString(bean.getIdUltim()));
						raBean.setData_inizio_record(bean.getDataIniUltim());
						raBean.setBlocco(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI);
						
						cAction.richiestaCancellazione(raBean);
						
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
                        }
						bean = cAction.load(bean.getIdAggiudicazione(), bean.getDataInizioAggiudicazione());
						schedaConclusione.setConclusione(bean);
					}
					//ConclusioneBean cBean = cAction.load(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
					//schedaConclusione.setConclusione(cBean);
					//request.setAttribute("conclusione", cBean);
					
					if (bean != null ) {
						bean.setOkCancellazione(cAction.bsa.isCancellabile(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI, 
								bean.getIdUltim(), 
								bean.getDataIniUltim(),
								bean.getIdStato(),
								infoGaraBean.getTipoEnte(), 
								infoGaraBean.getTipoContratto(),
								bean.getIdAggiudicazione(),
								bean.getDataInizioAggiudicazione()));
					}
					
					if(bean == null){logger.debug(">>>>>>>>>>>>>>>>>>>>>>bean null");}
					schedaConclusione.setNavigationBean(getNavBean(infoGaraBean.getIdAggiudicazione(), ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI, request.getSession()));
					request.setAttribute("schedaConclusione", schedaConclusione);
					//dataInizioScheda sono per la gestione delle tipologie non piu esitenti ma valide al momento dell'inserimento della scheda
					//TICKET ALM - 3.04.2 NG #2847 - Motivi Interruzione e risoluzione
					if(SimogFlags.is3042Active()) {
						request.setAttribute("motiviInterruzione", cAction.loadMotiviInterruzione(infoGaraBean.getDataCreazioneGara()));
						request.setAttribute("motiviRisoluzione", cAction.loadMotiviRisoluzione(infoGaraBean.getDataCreazioneGara()));
					} else {
					request.setAttribute("motiviInterruzione", cAction.loadMotiviInterruzione(dataInizioScheda));
					request.setAttribute("motiviRisoluzione", cAction.loadMotiviRisoluzione(dataInizioScheda));
					}
					//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
					if(bean != null && bean.getIdStato() == StatiScheda.CONFERMATO)
						cAction.sendValidations(request, validator.getEccezioni().getAllInfoEWarn());
					else
						cAction.sendValidations(request, validator.getEccezioni());
					
					
					/***************** DETTAGLI RICH ANN ************************/
					// PP patch bean puo' essere null
					long idUltim = 0;
					if (schedaConclusione.getConclusione() != null)
					   idUltim = schedaConclusione.getConclusione().getIdUltim();
					TableBean tabRichAnn = cAction.bsa.getRichAnnByScheda(
							String.valueOf(idUltim), 
							ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI, false);
					
					request.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
					
					/***************** END              *************************/
					
					forward(ParametriServletConclusioni.JSP_SCHEDA_CONCLUSIONI, request, response);
					return;
				}catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
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
	
//	protected void doGet(HttpServletRequest req , HttpServletResponse resp)throws ServletException,IOException{		
//		perform(req,resp);
//	}
	
	
}


