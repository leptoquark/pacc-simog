package it.avlp.simog.servlet.inizio;

import it.avlp.simog.actions.PubblicazioneAction;
import it.avlp.simog.actions.inizio.InizioLavoriAction;
import it.avlp.simog.actions.inizio.PosizioneAggiudicataroAction;
import it.avlp.simog.actions.inizio.ResponsabileInizioAction;
import it.avlp.simog.beans.DatiEconomiciBean;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.RubricaResponsabili;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.comparators.SoggettiResponsabiliComparator;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.inizio.PosizioneAggiudicatarioBean;
import it.avlp.simog.beans.inizio.SchedaInizioLavori;
import it.avlp.simog.common.action.AggiudicatarioAction;
import it.avlp.simog.common.action.BaseRichiestaAnnullamento;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletInizioLavori;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.factory.AnnullamentoFactory;
import it.avlp.simog.rubricamanager.RubricaManager;
import it.avlp.simog.rubricamanager.RubricaResponsabiliManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.factory.ValidatorFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SrvInizioLavori extends ServletBase {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7867708757143858709L;

	@Override
	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if(checkSession(request)){
			if(currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()){
				try{
					request.setAttribute(PSBD.NOME_SCHEDA,IdentificativoSchede.getInizioLavori().getDecodificaVN()) ;
					
					setDatiAggiudicazione(request);
					visualizzaListaParametriValori(request, response);
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					currentActiveConnection.setAutoCommit(false);
					ResponsabileInizioAction riAction = new ResponsabileInizioAction(currentActiveConnection, logger);
					PosizioneAggiudicataroAction paAction = new PosizioneAggiudicataroAction(currentActiveConnection, logger);
					InizioLavoriAction iAction = new InizioLavoriAction(currentActiveConnection, logger);
					PubblicazioneAction pAction = new PubblicazioneAction(currentActiveConnection, logger);
//					R129Action raAction = new R129Action(currentActiveConnection, logger);
					Timestamp dataInizioScheda = null;
					
					String pagina="schedaB1/inizioLavori.jsp";
					String action = request.getParameter("toDo");
					if(action == null || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA())
						action = PSBD.ACTION_LOAD;
					InfoGaraBean infoGara = getDatiGara(request.getSession());
					SchedaInizioLavori schedaLavori = new SchedaInizioLavori();			
					InizioLavoriBean datiLavori = new InizioLavoriBean();
					
					/* primo aprile 
					 * questi oggetti sono cmq da settare magari da qualche parte dove 
					 * si puo mettere in sessione l'oggetto wrapper contente inizio lavori, al momento
					 * funziona ma carica ogni volta gli oggetti che gli servono da db.. mentre dovrebbe essere un'op
					 * once */
					schedaLavori.setInfoComuni(iAction.getInfoComuni(infoGara.getIdInfo(), infoGara.getDataInizioInfo()));
					//adds
					AggiudicazioneBean ab = iAction.getAggiudicazione(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
					schedaLavori.setAggiudicazione(ab);
//					request.setAttribute("nonConfermabile", !this.isConfermabile(raAction, ab));
					//end
//					schedaLavori.setAggiudicazione(iAction.getAggiudicazione(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione()));
					schedaLavori.getInfoComuni().setPubblicazione(iAction.getPubblicazione(schedaLavori.getAggiudicazione().getIdPubblicazioneEsito(), schedaLavori.getAggiudicazione().getDataInizioPubbEsito()));
					/* --- end --- */
					SimogValidator inizioValidator = ValidatorFactory.getValidator(IdentificativoSchede.TAB_INIZIO_LAVORI, currentActiveConnection, logger);				

					AggiudicatarioAction aggAction = new AggiudicatarioAction(currentActiveConnection, logger);
					List <AggiudicatarioBean> aggiud = aggAction.loadMany(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), false);
					Map<String, String> optionList = new HashMap<String, String>();
					
					// nel parametro denominazione oltre a tale informazione vengono concatenate 
					// con la tilde ~ idSoggetto e dataInizioSoggetto
					String id_stato = "";
					
					for ( AggiudicatarioBean corrente: aggiud ) {
						String denominazione = corrente.getSoggettoPartecipante().getDenominazione();
						String idSogg = Long.toString(corrente.getSoggettoPartecipante().getIdSoggettoPartecipante());
						String dataInizioSogg =   corrente.getSoggettoPartecipante().getDataInizioSogg().toString();
						 
						id_stato = (corrente.getSoggettoPartecipante().getId_stato() != null) ? corrente.getSoggettoPartecipante().getId_stato() : "";
						
						optionList.put (denominazione+"~"+idSogg+"~"+dataInizioSogg+"~"+id_stato+"~", corrente.getSoggettoPartecipante().getCodiceFiscale());
									
					}
					request.setAttribute(ParametriServletInizioLavori.ATTRIB_AGGIUDICATARI, optionList);
					
					
					if(PSBD.ACTION_HST_SCHEDA.equalsIgnoreCase(action)){
						// cerco la scheda indicata nel parametro ricevuto, ignoro lo stato
						String vid = request.getParameter("vid");
						String vdt = request.getParameter("vdt");

						datiLavori = iAction.loadById(Long.parseLong(vid), Timestamp.valueOf(vdt));
						schedaLavori.setDatiInizio(datiLavori);
						schedaLavori.setResponsabiliInizio(riAction.loadMany(datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori(), true));
						schedaLavori.setPosizioneAggiudicatari(paAction.loadMany(datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori(), true));
						List<PosizioneAggiudicatarioBean> l = schedaLavori.getPosizioneAggiudicatari();
						int i = 0;
						for(PosizioneAggiudicatarioBean pab : l){
							
							SoggettoPartecipanteBean spb = pab.getSoggettoPartecipante();
							id_stato = spb.getId_stato();
							request.setAttribute("idPaese"+i,id_stato);
							i++;
						}
						
						dataInizioScheda = datiLavori.getDataInizioLavori();

						request.setAttribute("protect", "S");
					}
					else if(PSBD.ACTION_VARIAZIONI_ANAGRAFICHE.equalsIgnoreCase(action) || PSBD.ACTION_VARIAZIONI_ANAGRAFICHE_SAVE.equalsIgnoreCase(action)){
						boolean doVariazione = Boolean.parseBoolean(request.getParameter(PSBD.VAR_ANN));
//						saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte());
						datiLavori = iAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());

						if(doVariazione == false){
							request.setAttribute(PSBD.VAR_ANN, true);
							
							schedaLavori.setDatiInizio(datiLavori);
							schedaLavori.setResponsabiliInizio(riAction.loadMany(datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori(), false));
							schedaLavori.setPosizioneAggiudicatari(paAction.loadMany(datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori(), false));
							schedaLavori.getDatiInizio().setIdMotivoVarCO(null);

							
							dataInizioScheda = datiLavori.getDataInizioLavori();
							//la scheda viene validata solo se esiste!
							//if(datiLavori != null && datiLavori.getIdInizioLavori() > 0)
							//	inizioValidator.valida(schedaLavori, PSBD.SEZIONE_IN);
							schedaLavori.getDatiInizio().setDescrizioneStato(StatiScheda.VARIAZIONE_CO_STRING);
						}
						else {
							schedaLavori.setResponsabiliInizio(riAction.getBean(request));
							schedaLavori.setPosizioneAggiudicatari(paAction.getBean(request));
							////datiLavori = iAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
							datiLavori = iAction.getBean(request);
							
							// PP B302.2.0 imposto il flag per controllare la validazione
							datiLavori.setValidaVariazione(true);

							schedaLavori.setDatiInizio(datiLavori);

							if(inizioValidator.valida(schedaLavori, PSBD.SEZIONE_IN)){
								String motivazione = StatiScheda.VARIAZIONE_CO_STRING;
								
								RichiestaAnnullamento raBean = new RichiestaAnnullamento();
								raBean.setId_lotto(String.valueOf(infoGara.getIdLotto()));
								raBean.setMotivo_richiesta(motivazione);
								raBean.setRichiedente(currentUser.getLogin());
								boolean successo = false;
								raBean.setId_pub(Long.toString(datiLavori.getPubblicazione().getIdPubblicazione()));
								raBean.setData_inizio_pub(datiLavori.getPubblicazione().getDataInizioPubblicazione());
								
								raBean.setId_record(String.valueOf(datiLavori.getIdInizioLavori()));
								raBean.setData_inizio_record(datiLavori.getDataInizioLavori());
								raBean.setBlocco(IdentificativoSchede.TAB_INIZIO_LAVORI);
								Timestamp nuovadata = null;
								
								nuovadata = iAction.gestisciVariazioniCO(schedaLavori, raBean, currentUser.getLogin());
								successo = nuovadata != null;

								if(successo){
									commit(currentActiveConnection);
									inizioValidator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_083);
								}else{
									rollback(currentActiveConnection);
									inizioValidator.getEccezioni().addValidationErr(SIMOG_VARIAZIONE_CO_001);				
								}

								datiLavori = iAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
								schedaLavori.setResponsabiliInizio(riAction.loadMany(datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori(), false));
								schedaLavori.setPosizioneAggiudicatari(paAction.loadMany(datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori(), false));
								schedaLavori.setDatiInizio(datiLavori);
								logger.info("variazione c.o. terminata");
							} else {
								request.setAttribute(PSBD.VAR_ANN, true);
								schedaLavori.getDatiInizio().setDescrizioneStato(StatiScheda.VARIAZIONE_CO_STRING);
							}
						}
					}
					else if(PSBD.ACTION_LOAD.equalsIgnoreCase(action)){
						datiLavori = iAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						
						schedaLavori.setDatiInizio(datiLavori);
						schedaLavori.setResponsabiliInizio(riAction.loadMany(datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori(), false));
						schedaLavori.setPosizioneAggiudicatari(paAction.loadMany(datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori(), false));
						List<PosizioneAggiudicatarioBean> l = schedaLavori.getPosizioneAggiudicatari();
						int i = 0;
						for(PosizioneAggiudicatarioBean pab : l){
							
							SoggettoPartecipanteBean spb = pab.getSoggettoPartecipante();
							id_stato = spb.getId_stato();
							/*
							if(id_stato == null || "".equals(id_stato)){
								id_stato = PaesiManager.CODICE_STATO_ITALIANO;
								pab.getSoggettoPartecipante().setId_stato(id_stato);
							}
							*/
//							logger.debug("[ID_STATO("+i+")]" +id_stato);
							request.setAttribute("idPaese"+i,id_stato);
							logger.debug(ObjectIntrospector.propertiesInfo(SoggettoPartecipanteBean.class,spb));
							i++;
						}
						
						/*/----------- VL - adds 28 marz., modif 1 aprile-----------------//
						//agg
						AggiudicazioneBean aggBea = iAction.getAggiudicazione(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						schedaLavori.setAggiudicazione(aggBea);
						//info
						InfoComuniBean infBea = iAction.getInfoComuni(aggBea.getIdAggiudicazione(), aggBea.getDataInizioAggiudicazione());
						schedaLavori.setInfoComuni(infBea);
						//pubblicazione
						schedaLavori.getInfoComuni().setPubblicazione(iAction.getPubblicazione(schedaLavori.getAggiudicazione().getIdPubblicazioneEsito(), schedaLavori.getAggiudicazione().getDataInizioPubbEsito()));
						//------------------------------------------------/*/
						dataInizioScheda = datiLavori.getDataInizioLavori();
						//la scheda viene validata solo se esiste!
						if(datiLavori != null && datiLavori.getIdInizioLavori() > 0)
							inizioValidator.valida(schedaLavori, PSBD.SEZIONE_IN);
						
						// preimposto i dati pubblicazione se esiste la pubblicazione dell'avviso di aggiudicazione
						if(datiLavori.getIdInizioLavori() == 0 && ab.getIdPubblicazioneAgg() > 0)
							iAction.loadPubbFromAvviso(ab.getIdPubblicazioneAgg(), ab.getDataPubblicazioneAgg(), datiLavori);
						
					}else if(PSBD.ACTION_SALVA.equalsIgnoreCase(action)){
						datiLavori = iAction.getBean(request);
						schedaLavori.setDatiInizio(datiLavori);
						schedaLavori.setResponsabiliInizio(riAction.getBean(request));
						schedaLavori.setPosizioneAggiudicatari(paAction.getBean(request));
						List<PosizioneAggiudicatarioBean> l = schedaLavori.getPosizioneAggiudicatari();
						//try{
						int i = 0;
						for(PosizioneAggiudicatarioBean pab : l){
							SoggettoPartecipanteBean spb = pab.getSoggettoPartecipante();
							id_stato = spb.getId_stato();
							request.setAttribute("idPaese"+i,id_stato);
							i++;
						}
						//}catch(Throwable t){t.printStackTrace();}
						schedaLavori.getDatiInizio().setPubblicazione(pAction.getBean(request));
						
						//CONTROLLO REFRESH - solo se e una nuova scheda
						if(datiLavori.getIdInizioLavori() < 1 && isRefresh(request)){						
							inizioValidator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
							iAction.sendValidations(request, inizioValidator.getEccezioni());
							
							request.setAttribute("schedaLavori", schedaLavori);
							request.setAttribute(ParametriServlet.RUOLI_RESPONSABILE_BEAN, riAction.loadRuoliSezione(PSBD.SEZIONE_IN,dataInizioScheda));
							forward(ParametriServletInizioLavori.JSP_INIZIO_LAVORI, request, response);
							
							return;
						}
						// se la scheda esiste impedisco l'operazione
						if(datiLavori.getIdInizioLavori() < 1 && iAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione())!=null 
								&& iAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione()).getIdInizioLavori()>0){						
							inizioValidator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
							iAction.sendValidations(request, inizioValidator.getEccezioni());
							
							request.setAttribute("schedaLavori", schedaLavori);
							request.setAttribute(ParametriServlet.RUOLI_RESPONSABILE_BEAN, riAction.loadRuoliSezione(PSBD.SEZIONE_IN,dataInizioScheda));
							forward(ParametriServletInizioLavori.JSP_INIZIO_LAVORI, request, response);
							
							return;
						}
						
						if(inizioValidator.valida(schedaLavori, PSBD.SEZIONE_IN)){
							boolean inserimento = datiLavori.getIdInizioLavori() < 1;
							if( iAction.save(datiLavori, currentUser.getLogin()) > 0){
								riAction.save(schedaLavori.getResponsabiliInizio(), datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori());
								paAction.save(schedaLavori.getPosizioneAggiudicatari(), datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori());
							}
							commit(currentActiveConnection);
							if(inserimento)
								inizioValidator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_023);
							else inizioValidator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_024);
							//non carico i responsabili e aggiudicatari perche non servono i dati aggiornati(non serve id e datainizio record), ma quelli di inizio lavori si...
							schedaLavori.setDatiInizio(iAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione()));
						}		
					}else if(PSBD.ACTION_CONFERMA.equalsIgnoreCase(action)){
						datiLavori = iAction.getBean(request);
						schedaLavori.setDatiInizio(datiLavori);
						schedaLavori.setResponsabiliInizio(riAction.getBean(request));
						schedaLavori.setPosizioneAggiudicatari(paAction.getBean(request));
//						if(this.isConfermabile(raAction, ab)){
							if(inizioValidator.valida(schedaLavori, PSBD.SEZIONE_IN)){
								if( iAction.confirm(datiLavori, currentUser.getLogin()) > 0){
									riAction.confirm(schedaLavori.getResponsabiliInizio(), datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori());
									paAction.confirm(schedaLavori.getPosizioneAggiudicatari(), datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori());
								}
								commit(currentActiveConnection);
								inizioValidator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_025);
								
								//non carico i responsabili perche non servono i dati aggiornati, ma quelli di inizio lavori si...
								schedaLavori.setDatiInizio(iAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione()));
							}
//						}else{
//							sendError(request, "Esiste una scheda che deve essere confermata, prima di poter confermare questa");
//						}
					}else if(PSBD.ACTION_CARICA_JSP_ANNULLAMENTO.equalsIgnoreCase(action)){

						datiLavori = iAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						schedaLavori.setDatiInizio(datiLavori);

						boolean isOk = (iAction.bsa.hasSchedaRichDelete(String.valueOf(datiLavori.getIdInizioLavori()), IdentificativoSchede.TAB_INIZIO_LAVORI, false).getFullSize()==0);
						if (isOk){
							request.setAttribute(PSBD.TAB, IdentificativoSchede.TAB_INIZIO_LAVORI);
							String dest = ParametriServlet.JSP_RICHIEDI_ANNULLAMENTO + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + infoGara.getIdLotto();
							dest+="&" + ParametriServletInizioLavori.ID_INIZIO_LAVORI + "=" + datiLavori.getIdInizioLavori();
							dest+="&" + ParametriServletInizioLavori.DATA_INIZIO_LAVORI + "=" + datiLavori.getDataInizioLavori();
							dest+="&" + ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE + "=" + datiLavori.getPubblicazione().getIdPubblicazione();
							dest+="&" + ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB + "=" + datiLavori.getPubblicazione().getDataInizioPubblicazione();
							
							forward(dest, request, response);
							return;
						}
						else{
							inizioValidator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					else if(PSBD.ACTION_CARICA_JSP_CANCELLAZIONE.equalsIgnoreCase(action)){
						
						datiLavori = iAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						schedaLavori.setDatiInizio(datiLavori);
												
						boolean isOk = (iAction.bsa.hasSchedaRichDelete(String.valueOf(datiLavori.getIdInizioLavori()), IdentificativoSchede.TAB_INIZIO_LAVORI, true).getFullSize()==0);
						if (isOk){
							request.setAttribute(PSBD.TAB, IdentificativoSchede.TAB_INIZIO_LAVORI);
							String dest = ParametriServlet.JSP_RICHIEDI_CANCELLAZIONE + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + infoGara.getIdLotto();
							dest+="&" + ParametriServletInizioLavori.ID_INIZIO_LAVORI + "=" + datiLavori.getIdInizioLavori();
							dest+="&" + ParametriServletInizioLavori.DATA_INIZIO_LAVORI + "=" + datiLavori.getDataInizioLavori();
							dest+="&" + ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE + "=" + datiLavori.getPubblicazione().getIdPubblicazione();
							dest+="&" + ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB + "=" + datiLavori.getPubblicazione().getDataInizioPubblicazione();
							
							forward(dest, request, response);
							return;
						}
						else{
							inizioValidator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					else if(PSBD.ACTION_RICHIESTA_ANNULLAMENTO.equalsIgnoreCase(action)){
						datiLavori = iAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());

						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_ANNULLAMENTO);
						String idLotto = Long.toString(infoGara.getIdLotto());
						
						RichiestaAnnullamento raBean = new RichiestaAnnullamento();
						raBean.setId_lotto(idLotto);
						raBean.setMotivo_richiesta(motivazione);
						raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						raBean.setRichiedente(currentUser.getLogin());
						boolean successo = false;
						raBean.setId_pub(Long.toString(datiLavori.getPubblicazione().getIdPubblicazione()));
						raBean.setData_inizio_pub(datiLavori.getPubblicazione().getDataInizioPubblicazione());
						
						raBean.setId_record(String.valueOf(datiLavori.getIdInizioLavori()));
						raBean.setData_inizio_record(datiLavori.getDataInizioLavori());
						raBean.setBlocco(IdentificativoSchede.TAB_INIZIO_LAVORI);
						Timestamp nuovadata = null;
						Timestamp datavecchia = raBean.getData_inizio_record();
						nuovadata = iAction.annulla(currentActiveConnection, raBean);
						raBean.setData_inizio_record(nuovadata);
						successo = nuovadata != null; // && riAction.richiediAnnullamento(raBean, datavecchia) && paAction.richiediAnnullamento(raBean, datavecchia);										
						
                        // 3.02.2.1 accettazione immediata richiesta
                        if(SimogFlags.is30230_RFWEBSC03Active()){
                           
                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(raBean.getBlocco(), currentActiveConnection, logger);
                           
                           raBean.setDecisore(currentUser.getLogin());
                           raBean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
                           raBean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
                           
                           successo = annAction.gestisciRichiesta(raBean,currentUser.getLogin());
                        
                           if(successo){
                              datiLavori.setDataInizioLavori(nuovadata);
                              inizioValidator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RICHIESTA_ANNULLAMENTO_002);
                              commit(currentActiveConnection);
                          }
                          else{
                              rollback(currentActiveConnection);
                              inizioValidator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
                          }
                       }
                       else{                          
      						if(successo){
      							
      							datiLavori.setDataInizioLavori(nuovadata);
      							inizioValidator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_080);
      							commit(currentActiveConnection);
      						}
      						else{
      							rollback(currentActiveConnection);
      							inizioValidator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
      						}
                       }						
						datiLavori = iAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						schedaLavori.setResponsabiliInizio(riAction.loadMany(datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori(), false));
						schedaLavori.setPosizioneAggiudicatari(paAction.loadMany(datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori(), false));
						schedaLavori.setDatiInizio(datiLavori);
					}
					
					else if(PSBD.ACTION_RICHIESTA_CANCELLAZIONE.equalsIgnoreCase(action)){
						
						datiLavori = iAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());

						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_CANCELLAZIONE);
						String idLotto = Long.toString(infoGara.getIdLotto());
						
						RichiestaAnnullamento raBean = new RichiestaAnnullamento();
						raBean.setCancellazione(Costanti.FLAG_VALORE_NO) ;
						raBean.setId_lotto(idLotto);
						raBean.setMotivo_richiesta(motivazione);
						raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						raBean.setRichiedente(currentUser.getLogin());
						raBean.setId_pub(Long.toString(datiLavori.getPubblicazione().getIdPubblicazione()));
						raBean.setData_inizio_pub(datiLavori.getPubblicazione().getDataInizioPubblicazione());
						raBean.setId_record(String.valueOf(datiLavori.getIdInizioLavori()));
						raBean.setData_inizio_record(datiLavori.getDataInizioLavori());
						raBean.setBlocco(IdentificativoSchede.TAB_INIZIO_LAVORI);
						
						iAction.richiediCancellazione(raBean);

                        // 3.02.2.1 accettazione immediata richiesta
                        if(SimogFlags.is30230_RFWEBSC03Active()){
                           boolean successo;

                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(raBean.getBlocco(), currentActiveConnection, logger);
                            
                           raBean.setDecisore(currentUser.getLogin());
                           raBean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
                           raBean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
                           
                           successo = annAction.gestisciRichiestaCancellazione(raBean,currentUser.getLogin());
                    
                           if(successo){
                              inizioValidator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RICHIESTA_CANCELLAZIONE_002);
                               commit(currentActiveConnection);
                               
                               // forward a lista aggiudicazioni
                               pagina = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA
                                   + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO+"="+raBean.getId_lotto()
                                   + "&" + ParametriServlet.START_ROW + "=0"
                                   + "&" + "toDo=" + PSBD.ACTION_LOAD; // patch se va su daticomuni
                           }
                           else{
                               rollback(currentActiveConnection);
                               inizioValidator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_CANCELLAZIONE_001);
                           }
                        }
                        else{
                           inizioValidator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_082);
                           commit(currentActiveConnection);
                        }
                        
						datiLavori = iAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione());
						schedaLavori.setResponsabiliInizio(riAction.loadMany(datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori(), false));
						schedaLavori.setPosizioneAggiudicatari(paAction.loadMany(datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori(), false));
						schedaLavori.setDatiInizio(datiLavori);
					}
					//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
					if(datiLavori != null && datiLavori.getIdStato() == StatiScheda.CONFERMATO
							&& !(PSBD.ACTION_VARIAZIONI_ANAGRAFICHE.equalsIgnoreCase(action) || PSBD.ACTION_VARIAZIONI_ANAGRAFICHE_SAVE.equalsIgnoreCase(action))
					)
						iAction.sendValidations(request, inizioValidator.getEccezioni().getAllInfoEWarn());
					else
						iAction.sendValidations(request, inizioValidator.getEccezioni());
					
					request.setAttribute(ParametriServlet.RUOLI_RESPONSABILE_BEAN, riAction.loadRuoliSezione(PSBD.SEZIONE_IN,dataInizioScheda));
						
					schedaLavori.setNavigationBean(getNavBean(infoGara.getIdAggiudicazione(), IdentificativoSchede.TAB_INIZIO_LAVORI, request.getSession()));
					request.setAttribute("schedaLavori", schedaLavori);
					
					if ( schedaLavori.getDatiInizio() != null  ) {
						schedaLavori.getDatiInizio().setOkCancellazione(iAction.bsa.isCancellabile(IdentificativoSchede.TAB_INIZIO_LAVORI, 
								schedaLavori.getDatiInizio().getIdInizioLavori(), 
								schedaLavori.getDatiInizio().getDataInizioLavori(),
								schedaLavori.getDatiInizio().getIdStato(),
								infoGara.getTipoEnte(), 
								infoGara.getTipoContratto(),
								schedaLavori.getDatiInizio().getIdAggiudicazione(),
								schedaLavori.getDatiInizio().getDataInizioAggiudicazione()));
					}
					
					/***************** DETTAGLI RICH ANN ************************/
					
					TableBean tabRichAnn = iAction.bsa.getRichAnnByScheda(
							String.valueOf(schedaLavori.getDatiInizio().getIdInizioLavori()), 
							IdentificativoSchede.TAB_INIZIO_LAVORI, false);
					
					request.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
					
					/***************** DETTAGLI VAR ANAG ************************/				
					TableBean tabVarAnag = iAction.bsa.getVarAnagByScheda(String.valueOf(schedaLavori.getDatiInizio().getIdInizioLavori()), 
							IdentificativoSchede.TAB_INIZIO_LAVORI);
					
					request.setAttribute(PSBD.TAB_VARANAG, tabVarAnag);

					// PP B302.2.0
					if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive())
						request.setAttribute(ParametriServlet.MOTIVO_VCO_BEAN, iAction.bsa.loadMotiviVCO(dataInizioScheda));
					else
						request.setAttribute(ParametriServlet.MOTIVO_VCO_BEAN, new HashMap<String,String>());

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
	
//	/**
//	 * Metod che si occupa di controlla che tutte le eventuali schede \"R129\" siano confermate
//	 * prima di permetterne la conferma.
//	 * 
//	 * @param raAction
//	 * @param ab
//	 * @return
//	 * @throws ActionException
//	 */
//	private boolean isConfermabile(R129Action raAction,AggiudicazioneBean ab) throws ActionException{
//		boolean isConfermabile = true;
//		List<R129Bean> rl = raAction.loadAllByAgg(ab.getIdAggiudicazione(), ab.getDataInizioAggiudicazione());
//		for(R129Bean rb : rl){
//			isConfermabile &= rb.getIdStato() == 2;
//		}return isConfermabile;
//	}

}
