package it.avlp.simog.servlet.collaudo;

import it.avcp.simog.managers.accordo.AccordoManager;
import it.avcp.simog.managers.sospensioni.SospensioniManager;
import it.avcp.simog.managers.subappalti.SubappaltiManager;
import it.avcp.simog.managers.variante.VarianteManager;
import it.avlp.simog.actions.collaudo.CollaudoAction;
import it.avlp.simog.actions.collaudo.IncaricatiCollaudoAction;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.RubricaResponsabili;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.collaudo.SchedaCollaudo;
import it.avlp.simog.beans.comparators.SoggettiResponsabiliComparator;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.common.action.BaseRichiestaAnnullamento;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletCollaudo;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.factory.AnnullamentoFactory;
import it.avlp.simog.rubricamanager.RubricaManager;
import it.avlp.simog.rubricamanager.RubricaResponsabiliManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.factory.ValidatorFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SrvCollaudo extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2089045461195923558L;

	@Override
	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if(checkSession(request)){
			if(currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()){
				try{
					request.setAttribute(PSBD.NOME_SCHEDA,IdentificativoSchede.getCollaudo().getDecodificaVN()) ;

					
					setDatiAggiudicazione(request);
					visualizzaListaParametriValori(request, response);
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					currentActiveConnection.setAutoCommit(false);
					String action = request.getParameter("toDo");
					if(action == null || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA())
						action = PSBD.ACTION_LOAD;
					String pagina = null;
					CollaudoAction cAction = new CollaudoAction(currentActiveConnection,logger);
					IncaricatiCollaudoAction iAction = new IncaricatiCollaudoAction(currentActiveConnection,logger);
					InfoGaraBean infoGaraBean = getDatiGara(request.getSession());
					HttpSession sessione = request.getSession();
					CollaudoBean bean;
					List<ResponsabileBean> responsabili;
					SchedaCollaudo schedaCollaudo;
					bean = new CollaudoBean();
					Timestamp dataInizioScheda = null;
					schedaCollaudo = new SchedaCollaudo();
					schedaCollaudo.setAccordiBonario(cAction.getAccordoBonario(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione()));
					schedaCollaudo.setConclusione(cAction.getConclusione(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione()));
					schedaCollaudo.setAggiudicazione(cAction.getAggiudicazione(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione()));
					schedaCollaudo.setInfoComuni(cAction.getInfoComuni(infoGaraBean.getIdInfo(), infoGaraBean.getDataInizioInfo()));
					
					//2.10 aggiunto il bean inizioLavori
					schedaCollaudo.setInizioLavori(cAction.getInizioLavori(infoGaraBean.getIdAggiudicazione(),infoGaraBean.getDataInizioAggiudicazione()));
					//2.10 fatto
					
					responsabili = new ArrayList<ResponsabileBean>();
					schedaCollaudo.setCollaudo(bean);
					schedaCollaudo.setIncaricati(responsabili);

					SimogValidator validator = ValidatorFactory.getValidator(ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO, currentActiveConnection, logger);
					
					if(PSBD.ACTION_HST_SCHEDA.equalsIgnoreCase(action)){
						// cerco la scheda indicata nel parametro ricevuto, ignoro lo stato
						String vid = request.getParameter("vid");
						String vdt = request.getParameter("vdt");

						bean = cAction.loadById(Long.parseLong(vid), Timestamp.valueOf(vdt));
						if(bean != null)
							bean.setRespBean(iAction.loadMany(bean.getIdCollaudo(), bean.getDataIniColl(), true));
						schedaCollaudo.setCollaudo(bean);
						dataInizioScheda = bean.getDataIniColl();

						request.setAttribute("protect", "S");
					}
					else if(PSBD.ACTION_VARIAZIONI_ANAGRAFICHE.equalsIgnoreCase(action) || PSBD.ACTION_VARIAZIONI_ANAGRAFICHE_SAVE.equalsIgnoreCase(action)){
						boolean doVariazione = Boolean.parseBoolean(request.getParameter(PSBD.VAR_ANN));
						bean = cAction.load(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
						
						dataInizioScheda = bean.getDataIniColl();
						
						if(doVariazione == false){
							request.setAttribute(PSBD.VAR_ANN, true);
							schedaCollaudo.setCollaudo(bean);
							if(bean != null)
								bean.setRespBean(iAction.loadMany(bean.getIdCollaudo(), bean.getDataIniColl(), false));
							
							if(bean != null && bean.getIdCollaudo() > 0)
								validator.valida(schedaCollaudo, PSBD.SEZIONE_CO);
							schedaCollaudo.getCollaudo().setDescrizioneStato(StatiScheda.VARIAZIONE_CO_STRING);
							schedaCollaudo.getCollaudo().setIdMotivoVarCO(null);
						}
						else {
							bean = cAction.getBean(request, infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
							responsabili = iAction.getBean(request);
							bean.setRespBean(responsabili);

							// PP B302.2.0 imposto il flag per controllare la validazione
							bean.setValidaVariazione(true);
													
							schedaCollaudo.setCollaudo(bean);
							
							if(validator.valida(schedaCollaudo, PSBD.SEZIONE_CO)){
								String motivazione = StatiScheda.VARIAZIONE_CO_STRING;
								
								String idLotto = Long.toString(infoGaraBean.getIdLotto());
								
								RichiestaAnnullamento raBean = new RichiestaAnnullamento();
								raBean.setId_lotto(idLotto);
								raBean.setMotivo_richiesta(motivazione);
								raBean.setRichiedente(currentUser.getLogin());
								boolean successo = false;
								
								raBean.setId_record(Long.toString(bean.getIdCollaudo()));
								raBean.setData_inizio_record(bean.getDataIniColl());
								raBean.setBlocco(ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO);
								Timestamp nuovadata = null;
								
								nuovadata = cAction.gestisciVariazioniCO(bean, raBean,  currentUser.getLogin(),infoGaraBean.getTipoEnte());
								successo = nuovadata != null;
								
								if(successo){
									commit(currentActiveConnection);;
									validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_083);
								}
								else{
									rollback(currentActiveConnection);
									validator.getEccezioni().addValidationErr(SIMOG_VARIAZIONE_CO_001);				
								}
								bean = cAction.load(bean.getIdAggiudicazione(), bean.getDataIniAggiudicazione());
								bean.setRespBean(iAction.loadMany(bean.getIdCollaudo(), bean.getDataIniColl(), false));
								schedaCollaudo.setCollaudo(bean);
								logger.info("variazione c.o. terminata");
						}else {
							request.setAttribute(PSBD.VAR_ANN, true);
							schedaCollaudo.getCollaudo().setDescrizioneStato(StatiScheda.VARIAZIONE_CO_STRING);
						}
					}
					
					}
					else if(PSBD.ACTION_LOAD.equals(action)){
						bean = cAction.load(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
						if(bean != null)
							bean.setRespBean(iAction.loadMany(bean.getIdCollaudo(), bean.getDataIniColl(), false));
						schedaCollaudo.setCollaudo(bean);
						dataInizioScheda = bean.getDataIniColl();
						if(bean != null && bean.getIdCollaudo() > 0)
							validator.valida(schedaCollaudo, PSBD.SEZIONE_CO);
						
						
					}
					else if(PSBD.ACTION_SALVA.equals(action)){
						bean = cAction.getBean(request, infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
						schedaCollaudo.setCollaudo(bean);
						if(bean.getIdCollaudo() < 1 && isRefresh(request)){
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
						if(bean.getIdCollaudo() < 1 && cAction.load(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione()).getIdCollaudo()>0){								 				
					    	validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
						    //iAction.sendValidations(request, validator.getEccezioni());
					    	pagina = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA + "?"
							+ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + infoGaraBean.getIdLotto() +
							"&"+ParametriServlet.FIELD_NAME_ID_INFO+"="+infoGaraBean.getIdInfo()
							+"&"+ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO+"="+infoGaraBean.getDataInizioInfo();
							
							cAction.sendValidations(request, validator.getEccezioni());

							forward(pagina, request, response);
							return;
					    }
						
						responsabili = iAction.getBean(request);
						bean.setRespBean(responsabili);
						schedaCollaudo.setCollaudo(bean);
						if(validator.valida(schedaCollaudo, PSBD.SEZIONE_CO)){
							boolean inserimento = bean.getIdCollaudo() < 1;
							if( cAction.save(bean, currentUser.getLogin()) > 0){
								iAction.save(responsabili, bean.getIdCollaudo(), bean.getDataIniColl());
							}
							commit(currentActiveConnection);;
							if(inserimento)
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_023);
							else validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_024);
							bean = cAction.load(bean.getIdAggiudicazione(), bean.getDataIniAggiudicazione());
							bean.setRespBean(iAction.loadMany(bean.getIdCollaudo(), bean.getDataIniColl(), false));
							schedaCollaudo.setCollaudo(bean);
						}
					}
					else if(PSBD.ACTION_CONFERMA.equals(action)){
						bean = cAction.getBean(request, infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
						responsabili = iAction.getBean(request);
						bean.setRespBean(responsabili);
						schedaCollaudo.setCollaudo(bean);
						if(validator.valida(schedaCollaudo, PSBD.SEZIONE_CO)){
							if( cAction.confirm(bean, currentUser.getLogin()) > 0){
								iAction.confirm(responsabili, bean.getIdCollaudo(), bean.getDataIniColl());
							}
							commit(currentActiveConnection);;
							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_025);
							bean = cAction.load(bean.getIdAggiudicazione(), bean.getDataIniAggiudicazione());
							bean.setRespBean(iAction.loadMany(bean.getIdCollaudo(), bean.getDataIniColl(), false));
							schedaCollaudo.setCollaudo(bean);
						}
					}
					else if(PSBD.ACTION_CARICA_JSP_ANNULLAMENTO.equalsIgnoreCase(action)){
						bean = cAction.getBean(request, infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
						schedaCollaudo.setCollaudo(bean);

						boolean isOk = (cAction.bsa.hasSchedaRichDelete(String.valueOf(bean.getIdCollaudo()), IdentificativoSchede.TAB_COLLAUDO, false).getFullSize()==0);
						if (isOk){
							request.setAttribute(PSBD.TAB, ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO);
							request.setAttribute(ParametriServlet.SHOW_DATI_COMUNI, "true");
							String dest = ParametriServlet.JSP_RICHIEDI_ANNULLAMENTO + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGaraBean.getIdAggiudicazione();
							dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGaraBean.getDataInizioAggiudicazione();
							dest+="&" + ParametriServletCollaudo.FIELD_NAME_ID_COLLAUDO + "=" + bean.getIdCollaudo();
							dest+="&" + ParametriServletCollaudo.FIELD_NAME_DATA_INIZIO_COLL + "=" + bean.getDataIniColl();
							forward(dest, request, response);
							return;
						}
						else{
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					else if(PSBD.ACTION_CARICA_JSP_CANCELLAZIONE.equalsIgnoreCase(action)){
						bean = cAction.getBean(request, infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
						schedaCollaudo.setCollaudo(bean);

						boolean isOk = (cAction.bsa.hasSchedaRichDelete(String.valueOf(bean.getIdCollaudo()), IdentificativoSchede.TAB_COLLAUDO, true).getFullSize()==0);
						if (isOk){
							request.setAttribute(PSBD.TAB, ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO);
							request.setAttribute(ParametriServlet.SHOW_DATI_COMUNI, "true");
							String dest = ParametriServlet.JSP_RICHIEDI_CANCELLAZIONE + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGaraBean.getIdAggiudicazione();
							dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGaraBean.getDataInizioAggiudicazione();
							dest+="&" + ParametriServletCollaudo.FIELD_NAME_ID_COLLAUDO + "=" + bean.getIdCollaudo();
							dest+="&" + ParametriServletCollaudo.FIELD_NAME_DATA_INIZIO_COLL + "=" + bean.getDataIniColl();
							forward(dest, request, response);
							return;
						}
						else{
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					else if(PSBD.ACTION_RICHIESTA_ANNULLAMENTO.equalsIgnoreCase(action)){
						bean = cAction.getBean(request, infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
						schedaCollaudo.setCollaudo(bean);
						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_ANNULLAMENTO);
						String idLotto = Long.toString(infoGaraBean.getIdLotto());
						RichiestaAnnullamento raBean = new RichiestaAnnullamento();
						raBean.setId_lotto(idLotto);
						raBean.setMotivo_richiesta(motivazione);
						raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						raBean.setRichiedente(currentUser.getLogin());
						boolean successo = false;
						raBean.setId_record(Long.toString(bean.getIdCollaudo()));
						raBean.setData_inizio_record(bean.getDataIniColl());
						raBean.setBlocco(ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO);
						
						Timestamp nuovadata = null, datavecchia = bean.getDataIniColl();
						nuovadata = cAction.annullaCollaudo(currentActiveConnection, raBean);
						successo = nuovadata != null; //  iAction.richiediAnnullamento(raBean, datavecchia);

                        // 3.02.2.1 accettazione immediata richiesta
                        if(SimogFlags.is30230_RFWEBSC03Active()){
                           
                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(raBean.getBlocco(), currentActiveConnection, logger);
                           
                           raBean.setDecisore(currentUser.getLogin());
                           raBean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
                           raBean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
                           
                           successo = annAction.gestisciRichiesta(raBean,currentUser.getLogin());
                        
                           if(successo){
                              bean.setDataIniColl(nuovadata);
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
      							commit(currentActiveConnection);;
      							bean.setDataIniColl(nuovadata);
      							schedaCollaudo.setCollaudo(bean);
      							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_080);
      						}
      						else{
      							rollback(currentActiveConnection);
      							validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
      						}
                       }
						bean = cAction.load(bean.getIdAggiudicazione(), bean.getDataIniAggiudicazione());
						schedaCollaudo.setCollaudo(bean);
					}
					else if(PSBD.ACTION_RICHIESTA_CANCELLAZIONE.equalsIgnoreCase(action)){
						bean = cAction.getBean(request, infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
						schedaCollaudo.setCollaudo(bean);
						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_CANCELLAZIONE);
						String idLotto = Long.toString(infoGaraBean.getIdLotto());
						
						RichiestaAnnullamento raBean = new RichiestaAnnullamento();
						raBean.setCancellazione(Costanti.FLAG_VALORE_NO) ;
						raBean.setId_lotto(idLotto);
						raBean.setMotivo_richiesta(motivazione);
						raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						raBean.setRichiedente(currentUser.getLogin());
						raBean.setId_record(Long.toString(bean.getIdCollaudo()));
						raBean.setData_inizio_record(bean.getDataIniColl());
						raBean.setBlocco(ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO);
						
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
      						commit(currentActiveConnection);;
      						validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_082);
                        }
						bean = cAction.load(bean.getIdAggiudicazione(), bean.getDataIniAggiudicazione());
						schedaCollaudo.setCollaudo(bean);
					}
					//Vittore 21/04/2008 - Eliminazione reload bean (vuoto) della scheda di collaudo
					//bean = cAction.load(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
					//schedaCollaudo.setCollaudo(bean);

// PP on richiesto, tolto					
					//prevalorizzazione del campo lavori estesi
//					if(bean != null && bean.getIdCollaudo() == 0){
//						bean.setFlagLavoriEstesi(FLAG_VALORE_NO);
//					}
					if ( bean != null ) {
						bean.setOkCancellazione(cAction.bsa.isCancellabile(ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO, 
								bean.getIdCollaudo(), 
								bean.getDataIniColl(),
								bean.getIdStato(),
								infoGaraBean.getTipoEnte(), 
								infoGaraBean.getTipoContratto(),
								bean.getIdAggiudicazione(),
								bean.getDataIniAggiudicazione()));
					}
					
					request.setAttribute("collaudo", bean);
					schedaCollaudo.setNavigationBean(getNavBean(infoGaraBean.getIdAggiudicazione(), ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO, request.getSession()));
					request.setAttribute("schedaCollaudo", schedaCollaudo);
					AccordoManager accManager = new AccordoManager(currentActiveConnection,logger);
					List<AccordoBean> accordi = new ArrayList<AccordoBean>();
					accordi = accManager.loadMany(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
					int numeroRiserve = 0;
					BigDecimal oneri = new BigDecimal(0);
					for(AccordoBean a : accordi){
						numeroRiserve = numeroRiserve + a.getNumeroRiserve();
						if(a.getOneriDerivanti() != null){
							oneri = oneri.add(a.getOneriDerivanti());
						}
					}
					List<SubappaltiBean> listaSubappalti = new ArrayList<SubappaltiBean>();
					SubappaltiManager sManager = new SubappaltiManager(currentActiveConnection,logger);
					listaSubappalti = sManager.loadMany(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());

					sessione.setAttribute("listaSubappalti", listaSubappalti);
					
					List<SospensioniBean> listaSospensioni = new ArrayList<SospensioniBean>();
					SospensioniManager sospManager = new SospensioniManager(currentActiveConnection,logger);
					listaSospensioni = sospManager.loadMany(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
					sessione.setAttribute("listaSospensioni", listaSospensioni);
					
					int totaleRiserveAvanzate;
					
					
					List<VarianteBean> listaVarianti = new ArrayList<VarianteBean>();
					VarianteManager vManager = new VarianteManager(currentActiveConnection,logger);
					listaVarianti = vManager.loadMany(infoGaraBean.getIdAggiudicazione(), infoGaraBean.getDataInizioAggiudicazione());
					sessione.setAttribute("listaVarianti", listaVarianti);
					
					/***************** DETTAGLI RICH ANN ************************/
					
					TableBean tabRichAnn = cAction.bsa.getRichAnnByScheda(
							String.valueOf(schedaCollaudo.getCollaudo().getIdCollaudo()), 
							ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO, false);
					
					request.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
					
					/***************** DETTAGLI VAR ANAG ************************/				
					TableBean tabVarAnag = cAction.bsa.getVarAnagByScheda(String.valueOf(schedaCollaudo.getCollaudo().getIdCollaudo()), 
							ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO);
					
					request.setAttribute(PSBD.TAB_VARANAG, tabVarAnag);

					/***************** END              *************************/
					
					request.setAttribute("numRiserve", numeroRiserve);
					request.setAttribute("oneriDerivanti", oneri);
					request.setAttribute(ParametriServlet.RUOLI_RESPONSABILE_BEAN, cAction.loadRuoliSezione(PSBD.SEZIONE_CO,dataInizioScheda));
					
					//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
					if(bean != null && bean.getIdStato() == StatiScheda.CONFERMATO
							&& !(PSBD.ACTION_VARIAZIONI_ANAGRAFICHE.equalsIgnoreCase(action) || PSBD.ACTION_VARIAZIONI_ANAGRAFICHE_SAVE.equalsIgnoreCase(action))
					)
						cAction.sendValidations(request, validator.getEccezioni().getAllInfoEWarn());
					else
						cAction.sendValidations(request, validator.getEccezioni());
					
					// PP B302.2.0
					if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive())
						request.setAttribute(ParametriServlet.MOTIVO_VCO_BEAN, cAction.bsa.loadMotiviVCO(dataInizioScheda));
					else
						request.setAttribute(ParametriServlet.MOTIVO_VCO_BEAN, new HashMap<String,String>());

					setDataCreazione(infoGaraBean.getDataCreazioneGara(), request.getSession());
					
					forward(ParametriServletCollaudo.JSP_SCHEDA_COLLAUDO, request, response);
					return;
					}catch (Exception e) {
					//	e.printStackTrace();
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
	
	
//	protected void doGet(HttpServletRequest request , HttpServletResponse response)throws ServletException,IOException {
//		perform(request, response);
//	}
}
