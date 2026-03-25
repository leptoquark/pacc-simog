package it.avlp.simog.servlet.stipula;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.avlp.simog.actions.PubblicazioneAction;
import it.avlp.simog.actions.inizio.InizioLavoriAction;
import it.avlp.simog.actions.inizio.PosizioneAggiudicataroAction;
import it.avlp.simog.actions.inizio.ResponsabileInizioAction;
import it.avlp.simog.actions.stipula.StipulaAction;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.inizio.PosizioneAggiudicatarioBean;
import it.avlp.simog.beans.inizio.SchedaInizioLavori;
import it.avlp.simog.beans.stipula.SchedaStipula;
import it.avlp.simog.beans.stipula.StipulaBean;
import it.avlp.simog.common.action.AggiudicatarioAction;
import it.avlp.simog.common.action.BaseRichiestaAnnullamento;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletInizioLavori;
import it.avlp.simog.common.servlet.ParametriServletStipula;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.factory.AnnullamentoFactory;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.StipulaValidator;
import it.avlp.simog.validatore.factory.ValidatorFactory;

public class SrvStipula extends ServletBase {

	private static final long serialVersionUID = 7867708757143858709L;

	@Override
	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if(checkSession(request)){
			if(currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()){
				try{
					request.setAttribute(PSBD.NOME_SCHEDA,IdentificativoSchede.getStipula().getDecodificaVN()) ;
					
					setDatiAggiudicazione(request);
					visualizzaListaParametriValori(request, response);
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					currentActiveConnection.setAutoCommit(false);
					StipulaAction sAction = new StipulaAction(currentActiveConnection, logger);
					
					String pagina= ParametriServletStipula.JSP_STIPULA;
					String action = request.getParameter("toDo");
					if(action == null || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA())
						action = PSBD.ACTION_LOAD;
					InfoGaraBean infoGara = getDatiGara(request.getSession());
					SchedaStipula schedaStipula = new SchedaStipula();			
					StipulaBean datiStipula = new StipulaBean();
					//Timestamp dataInizioScheda = null;
					
                    StipulaValidator stipulaValidator = new StipulaValidator(currentActiveConnection, logger);
					//caricamento dell'aggiudicazione
                    AggiudicazioneBean aggBea = sAction.getAggiudicazione(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
					schedaStipula.setAggiudicazione(aggBea);
                    //caricamento degli aggiudicatari
					AggiudicatarioAction aggAction = new AggiudicatarioAction(currentActiveConnection, logger);
					List <AggiudicatarioBean> aggiud = aggAction.loadMany(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), false);
					schedaStipula.setAggiudicatari(aggiud);

					
					if(PSBD.ACTION_LOAD.equalsIgnoreCase(action)){
						datiStipula = sAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						schedaStipula.setStipula(datiStipula);
						/*
						PubblicazioneBean pubblicazione = sAction.getPubblicazione(infoGara.getIdPubblicazione(), infoGara.getDataInizioPubblicazione());
						if(pubblicazione!=null)
				    		datiStipula.setPubblicazione(pubblicazione);
						schedaStipula.setStipula(datiStipula);
						*/
						//dataInizioScheda = datiStipula.getDataInizioStipula();
						//la scheda viene validata solo se esiste!
						if(datiStipula != null && datiStipula.getIdStipula() > 0)
							stipulaValidator.valida(schedaStipula, null);
							
					}
					else if(PSBD.ACTION_SALVA.equalsIgnoreCase(action)){
						datiStipula = sAction.getBean(request);
						schedaStipula.setStipula(datiStipula);
						
						if(datiStipula.getIdStipula() < 1 && isRefresh(request)){						
							stipulaValidator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
							sAction.sendValidations(request, stipulaValidator.getEccezioni());
							request.setAttribute(ParametriServletStipula.SCHEDA_STIPULA, schedaStipula);
							forward(ParametriServletInizioLavori.JSP_INIZIO_LAVORI, request, response);		
							return;
						}
						
						// se la scheda esiste impedisco l'operazione
						if(datiStipula.getIdStipula() < 1 && sAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione())!=null && sAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione()).getIdStipula()>0){						
							stipulaValidator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
							sAction.sendValidations(request, stipulaValidator.getEccezioni());
							request.setAttribute(ParametriServletStipula.SCHEDA_STIPULA, schedaStipula);
							forward(ParametriServletInizioLavori.JSP_INIZIO_LAVORI, request, response);		
							return;
						}
						
						if(stipulaValidator.valida(schedaStipula, null)){
							boolean inserimento = datiStipula.getIdStipula() < 1;
							
							sAction.save(datiStipula, currentUser.getLogin());
								
							commit(currentActiveConnection);
							if(inserimento)
								stipulaValidator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_023);
							else stipulaValidator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_024);
								
							datiStipula.setPubblicazione(sAction.getPubblicazione(datiStipula.getPubblicazione().getIdPubblicazione(), datiStipula.getPubblicazione().getDataInizioPubblicazione()));
							schedaStipula.setStipula(sAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione()));
						}		
					}
					else if(PSBD.ACTION_CONFERMA.equalsIgnoreCase(action)){
						datiStipula = sAction.getBean(request);
						schedaStipula.setStipula(datiStipula);
						
							if(stipulaValidator.valida(schedaStipula, null)){
								
								sAction.confirm(datiStipula, currentUser.getLogin());
								commit(currentActiveConnection);
								stipulaValidator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_025);
								
								datiStipula.setPubblicazione(sAction.getPubblicazione(datiStipula.getPubblicazione().getIdPubblicazione(), datiStipula.getPubblicazione().getDataInizioPubblicazione()));
								schedaStipula.setStipula(sAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione()));
							}
//						}else{
//							sendError(request, "Esiste una scheda che deve essere confermata, prima di poter confermare questa");
//						}
					}else if(PSBD.ACTION_CARICA_JSP_ANNULLAMENTO.equalsIgnoreCase(action)){
						datiStipula = sAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						schedaStipula.setStipula(datiStipula);

						boolean isOk = (sAction.bsa.hasSchedaRichDelete(String.valueOf(datiStipula.getIdStipula()), IdentificativoSchede.TAB_STIPULA, false).getFullSize()==0);
						if (isOk){
							request.setAttribute(PSBD.TAB, IdentificativoSchede.TAB_STIPULA);
							String dest = ParametriServlet.JSP_RICHIEDI_ANNULLAMENTO + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + infoGara.getIdLotto();
							dest+="&" + ParametriServletStipula.ID_STIPULA + "=" + datiStipula.getIdStipula();
							dest+="&" + ParametriServletStipula.DATA_INIZIO_STIPULA + "=" + datiStipula.getDataInizioStipula();
							dest+="&" + ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE + "=" + datiStipula.getPubblicazione().getIdPubblicazione();
							dest+="&" + ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB + "=" + datiStipula.getPubblicazione().getDataInizioPubblicazione();
							
							forward(dest, request, response);
							return;
						}
						else{
							stipulaValidator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					else if(PSBD.ACTION_CARICA_JSP_CANCELLAZIONE.equalsIgnoreCase(action)){
						
						datiStipula = sAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						schedaStipula.setStipula(datiStipula);
												
						boolean isOk = (sAction.bsa.hasSchedaRichDelete(String.valueOf(datiStipula.getIdStipula()), IdentificativoSchede.TAB_STIPULA, true).getFullSize()==0);
						if (isOk){
							request.setAttribute(PSBD.TAB, IdentificativoSchede.TAB_STIPULA);
							String dest = ParametriServlet.JSP_RICHIEDI_CANCELLAZIONE + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + infoGara.getIdLotto();
							dest+="&" + ParametriServletStipula.ID_STIPULA + "=" + datiStipula.getIdStipula();
							dest+="&" + ParametriServletStipula.DATA_INIZIO_STIPULA + "=" + datiStipula.getDataInizioStipula();
							dest+="&" + ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE + "=" + datiStipula.getPubblicazione().getIdPubblicazione();
							dest+="&" + ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB + "=" + datiStipula.getPubblicazione().getDataInizioPubblicazione();
							
							forward(dest, request, response);
							return;
						}
						else{
							stipulaValidator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					else if(PSBD.ACTION_RICHIESTA_ANNULLAMENTO.equalsIgnoreCase(action)){
						datiStipula = sAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());

						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_ANNULLAMENTO);
						String idLotto = Long.toString(infoGara.getIdLotto());
						
						RichiestaAnnullamento raBean = new RichiestaAnnullamento();
						raBean.setId_lotto(idLotto);
						raBean.setMotivo_richiesta(motivazione);
						raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						raBean.setRichiedente(currentUser.getLogin());
						boolean successo = false;
						raBean.setId_pub(Long.toString(datiStipula.getPubblicazione().getIdPubblicazione()));
						raBean.setData_inizio_pub(datiStipula.getPubblicazione().getDataInizioPubblicazione());
						
						raBean.setId_record(String.valueOf(datiStipula.getIdStipula()));
						raBean.setData_inizio_record(datiStipula.getDataInizioStipula());
						raBean.setBlocco(IdentificativoSchede.TAB_STIPULA);
						Timestamp nuovadata = null;
						//Timestamp datavecchia = raBean.getData_inizio_record();
						nuovadata = sAction.richiediAnnullamento(raBean);
						raBean.setData_inizio_record(nuovadata);

						successo = nuovadata != null;			
						
                        // 3.02.2.1 accettazione immediata richiesta
                        if(SimogFlags.is30230_RFWEBSC03Active()){
                           
                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(raBean.getBlocco(), currentActiveConnection, logger);
                           
                           raBean.setDecisore(currentUser.getLogin());
                           raBean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
                           raBean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
                           
                           successo = annAction.gestisciRichiesta(raBean,currentUser.getLogin());
                        
                           if(successo){
                              datiStipula.setDataInizioStipula(nuovadata);
                              stipulaValidator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RICHIESTA_ANNULLAMENTO_002);
                              commit(currentActiveConnection);
                          }
                          else{
                              rollback(currentActiveConnection);
                              stipulaValidator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
                          }
                       }
                       else{   						
      						if(successo){		
      							datiStipula.setDataInizioStipula(nuovadata);
      							stipulaValidator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_080);
      							commit(currentActiveConnection);
      						}
      						else{
      							rollback(currentActiveConnection);
      							stipulaValidator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
      						}
                       }
						datiStipula = sAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						schedaStipula.setStipula(datiStipula);
					}
					
					else if(PSBD.ACTION_RICHIESTA_CANCELLAZIONE.equalsIgnoreCase(action)){
						
						datiStipula = sAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());

						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_CANCELLAZIONE);
						String idLotto = Long.toString(infoGara.getIdLotto());
						
						RichiestaAnnullamento raBean = new RichiestaAnnullamento();
						raBean.setCancellazione(Costanti.FLAG_VALORE_NO) ;
						raBean.setId_lotto(idLotto);
						raBean.setMotivo_richiesta(motivazione);
						raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						raBean.setRichiedente(currentUser.getLogin());
						raBean.setId_pub(Long.toString(datiStipula.getPubblicazione().getIdPubblicazione()));
						raBean.setData_inizio_pub(datiStipula.getPubblicazione().getDataInizioPubblicazione());
						raBean.setId_record(String.valueOf(datiStipula.getIdStipula()));
						raBean.setData_inizio_record(datiStipula.getDataInizioStipula());
						raBean.setBlocco(IdentificativoSchede.TAB_STIPULA);
						
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
                              stipulaValidator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RICHIESTA_CANCELLAZIONE_002);
                               commit(currentActiveConnection);
                               
                               // forward a lista aggiudicazioni
                               pagina = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA
                                   + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO+"="+raBean.getId_lotto()
                                   + "&" + ParametriServlet.START_ROW + "=0"
                                   + "&" + "toDo=" + PSBD.ACTION_LOAD; // patch se va su daticomuni
                           }
                           else{
                               rollback(currentActiveConnection);
                               stipulaValidator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_CANCELLAZIONE_001);
                           }
                        }
                        else{						
                           stipulaValidator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_082);
                           commit(currentActiveConnection);
                        }
						datiStipula = sAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						schedaStipula.setStipula(datiStipula);
					}

					sAction.sendValidations(request, stipulaValidator.getEccezioni());
									
					schedaStipula.setNavigationBean(getNavBean(infoGara.getIdAggiudicazione(), IdentificativoSchede.TAB_STIPULA, request.getSession()));
					request.setAttribute(ParametriServletStipula.SCHEDA_STIPULA, schedaStipula);
					
					if ( schedaStipula.getStipula() != null  ) {
						schedaStipula.getStipula().setOkCancellazione(sAction.bsa.isCancellabile(IdentificativoSchede.TAB_STIPULA, 
								schedaStipula.getStipula().getIdStipula(), 
								schedaStipula.getStipula().getDataInizioStipula(),
								schedaStipula.getStipula().getIdStato(),
								infoGara.getTipoEnte(), 
								infoGara.getTipoContratto(),
								schedaStipula.getStipula().getIdAggiudicazione(),
								schedaStipula.getStipula().getDataInizioAggiudicazione()));
					}
					
					
					/***************** DETTAGLI RICH ANN ************************/
					
					TableBean tabRichAnn = sAction.bsa.getRichAnnByScheda(
							String.valueOf(schedaStipula.getStipula().getIdStipula()), 
							IdentificativoSchede.TAB_STIPULA, false);
					
					request.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
					
					/***************** END              *************************/
					
					forward(pagina , request, response);
					return;
				}catch (Exception e) {
					e.printStackTrace();
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
	
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		perform(req, resp);
//	}
}