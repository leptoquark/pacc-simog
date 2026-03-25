package it.avlp.simog.servlet.adesione;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.avcp.simog.managers.aggiudicazione.MultilottoManager;
import it.avlp.simog.actions.adesione.SchedaAdesioneAction;
import it.avlp.simog.actions.aggiudicazione.InfoComuniAction;
import it.avlp.simog.actions.aggiudicazione.Scheda_A_Action;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.common.action.BaseRichiestaAnnullamento;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.factory.AnnullamentoFactory;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.factory.ValidatorFactory;

public class SrvSchedaAdesione extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (checkSession(request)){
			InfoGaraBean igb = getDatiGara(request.getSession());
			try{
				long idAggiudicazione = Long.parseLong(request.getParameter(PSBD.FIELD_NAME_ID_AGGIUDICAZIONE));
				Timestamp dataInizioAggiudicazione = PageHelper.parseTime(request.getParameter(PSBD.DATA_INIZIO_AGGIUDICAZIONE));
				igb.setIdAggiudicazione(idAggiudicazione);
				igb.setDataInizioAggiudicazione(dataInizioAggiudicazione);
				setDatiGara(igb, request.getSession());
			}catch (Exception e) {
				logger.debug(e);
			}
			perform(request, response);
		}
		else {
			sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			return;
		}
	}
	
	private List<AggiudicatarioBean> getAggiudicatariSelezionati(Connection activeConnection, String cigAccQuadro, List<AggiudicatarioBean> aggiudicatariSelezionati, 
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SchedaAdesioneAction saa = new SchedaAdesioneAction(activeConnection, logger);
		List<AggiudicatarioBean> aggiudicatariAccQuadro = new ArrayList<AggiudicatarioBean>();
		AggiudicazioneBean aggiudicazioneAccQuadro = new AggiudicazioneBean();
		
		//gm nuovo codice per l'aggiudicazione accordo quadro
		try{
			aggiudicazioneAccQuadro = saa.getBeanAggiudicazioneAccQuadro(cigAccQuadro);
		}
		catch (Exception e){
			logger.error("Errore nella ricerca dell'aggiudicazione dell'accordo quadro", e);
			sendError(request, response, Messaggi.SIMOG_AGGIUDICAZIONI_002, ParametriServlet.JSP_GESTIONE_SCHEDE);
		}
		try{
			aggiudicatariAccQuadro = saa.getBeanAggiudicatariAccQuadroByAcc(aggiudicazioneAccQuadro);
		}
		catch (Exception e){
			logger.error("Errore nella ricerca degli aggiudicatari dell'accordo quadro", e);
			sendError(request, response, Messaggi.SIMOG_AGGIUDICAZIONI_002, ParametriServlet.JSP_GESTIONE_SCHEDE);	
		}
		
		
//		List<AggiudicatarioBean> aggToAdd = new ArrayList<AggiudicatarioBean>();
		for (AggiudicatarioBean aggiudicatario:aggiudicatariAccQuadro){
			for (AggiudicatarioBean aggiudicatarioSel:aggiudicatariSelezionati){
				if (aggiudicatario.getSoggettoPartecipante().getIdSoggettoPartecipante() == aggiudicatarioSel.getSoggettoPartecipante().getIdSoggettoPartecipante()) {
					aggiudicatario.setSelezionato(1);
					break;
				}
			}								
		}
		
		boolean checkAssCat = false;
		for (AggiudicatarioBean aggiudicatario:aggiudicatariAccQuadro){
			if(!checkAssCat && aggiudicatario.getIdTipoAgg()==Costanti.TIPODITTA_ASS_CAT) {
				checkAssCat = true;
				break;
			}
		}
		
		if(checkAssCat) {
			for (AggiudicatarioBean aggiudicatarioSel:aggiudicatariSelezionati){
				boolean found = false;
				for (AggiudicatarioBean aggiudicatario:aggiudicatariAccQuadro){
					if (aggiudicatario.getSoggettoPartecipante().getIdSoggettoPartecipante() == aggiudicatarioSel.getSoggettoPartecipante().getIdSoggettoPartecipante()) {
						found = true;
						break;
					} 
				}
				
				if(!found) {
					aggiudicatarioSel.setSelezionato(1);
					aggiudicatariAccQuadro.add(aggiudicatarioSel);
				}
			}	
		}
		
		return aggiudicatariAccQuadro;
	}
	
	private List<AggiudicatarioBean> getAggiudicatariSelezionatiById(Connection activeConnection, String cigAccQuadro, List<AggiudicatarioBean> aggiudicatariSelezionati, 
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SchedaAdesioneAction saa = new SchedaAdesioneAction(activeConnection, logger);
		List<AggiudicatarioBean> aggiudicatariAccQuadro = new ArrayList<AggiudicatarioBean>();
		AggiudicazioneBean aggiudicazioneAccQuadro = new AggiudicazioneBean();
		
		//gm nuovo codice per l'aggiudicazione accordo quadro
		try{
			aggiudicazioneAccQuadro = saa.getBeanAggiudicazioneAccQuadro(cigAccQuadro);
		}
		catch (Exception e){
			logger.error("Errore nella ricerca dell'aggiudicazione dell'accordo quadro", e);
			sendError(request, response, Messaggi.SIMOG_AGGIUDICAZIONI_002, ParametriServlet.JSP_GESTIONE_SCHEDE);
		}
		try{
			aggiudicatariAccQuadro = saa.getBeanAggiudicatariAccQuadroByAcc(aggiudicazioneAccQuadro);
		}
		catch (Exception e){
			logger.error("Errore nella ricerca degli aggiudicatari dell'accordo quadro", e);
			sendError(request, response, Messaggi.SIMOG_AGGIUDICAZIONI_002, ParametriServlet.JSP_GESTIONE_SCHEDE);	
		}
		
		
		for (AggiudicatarioBean aggiudicatario:aggiudicatariAccQuadro){
					
			for (AggiudicatarioBean aggiudicatarioSel:aggiudicatariSelezionati){
				if (aggiudicatario.getSoggettoPartecipante().getIdSoggettoPartecipante() == aggiudicatarioSel.getSoggettoPartecipante().getIdSoggettoPartecipante()) {
					aggiudicatario.setSelezionato(1);
					break;
				} 
			}	
		}
		
		boolean checkAssCat = false;
		for (AggiudicatarioBean aggiudicatario:aggiudicatariAccQuadro){
			if(!checkAssCat && aggiudicatario.getIdTipoAgg()==Costanti.TIPODITTA_ASS_CAT) {
				checkAssCat = true;
				break;
			}
		}
//		List<AggiudicatarioBean> aggToAdd = new ArrayList<AggiudicatarioBean>(); 
		if(checkAssCat) {
			for (AggiudicatarioBean aggiudicatarioSel:aggiudicatariSelezionati){
				boolean found = false;
				for (AggiudicatarioBean aggiudicatario:aggiudicatariAccQuadro){
					if (aggiudicatario.getSoggettoPartecipante().getIdSoggettoPartecipante() == aggiudicatarioSel.getSoggettoPartecipante().getIdSoggettoPartecipante()) {
						found = true;
						break;
					} 
				}
				
				if(!found) {
					aggiudicatarioSel.setSelezionato(1);
					aggiudicatariAccQuadro.add(aggiudicatarioSel);
				}
			}	
		}
		
		return aggiudicatariAccQuadro;
	}

	protected void perform(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if (checkSession(request)){
			if (currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA() ){	
				visualizzaListaParametriValori(request, response);
				String pagina=ParametriServlet.JSP_SCHEDA_ADESIONE;
				String tab = request.getParameter(PSBD.TAB);
				Timestamp dataInizioScheda = null;

				InfoGaraBean infoGara = getDatiGara(request.getSession());	
    			
				try{ 
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					currentActiveConnection.setAutoCommit(false);

					request.setAttribute(PSBD.NOME_SCHEDA,IdentificativoSchede.getAdesione().getDecodificaVN()) ;					
					InfoComuniBean icb = (new InfoComuniAction(currentActiveConnection,logger)).load(infoGara.getIdInfo(), infoGara.getDataInizioInfo());
					String actionType = request.getParameter(PSBD.ACTION_TYPE);
					if(actionType == null  || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA())
						actionType = PSBD.ACTION_LOAD;
					Scheda_A_Action saAction = new Scheda_A_Action(currentActiveConnection, logger);
					Scheda_A saBean = new Scheda_A();
					SimogValidator validator = ValidatorFactory.getValidator(IdentificativoSchede.TAB_ADESIONE, currentActiveConnection, logger);				
					String idLotto = Long.toString(infoGara.getIdLotto());
					saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte(), false, infoGara.getIdLotto());		
					if(saBean != null && saBean.getAggiudicazione() != null && saBean.getAggiudicazione().getIdAggiudicazione() > 0)
						saBean.setNavigationBean(getNavBean(infoGara.getIdAggiudicazione(), IdentificativoSchede.TAB_ADESIONE, request.getSession()));
					
					// Caricamento pagina daticomuni.jsp
					
					if(PSBD.ACTION_HST_SCHEDA.equalsIgnoreCase(actionType)){

						// cerco la scheda indicata nel parametro ricevuto, ignoro lo stato
						String vid = request.getParameter("vid");
						String vdt = request.getParameter("vdt");
						
						saBean = saAction.load(Long.parseLong(vid), Timestamp.valueOf(vdt), infoGara.getTipoEnte(), true, infoGara.getIdLotto());
						saBean.setInfoComuni(icb);
						saBean.setInfoGara(infoGara);

						dataInizioScheda = saBean.getAggiudicazione().getDataInizioAggiudicazione();
						
						request.setAttribute("protect", "S");
					}
					else if(PSBD.ACTION_VARIAZIONI_ANAGRAFICHE.equalsIgnoreCase(actionType)){
						boolean doVariazione = Boolean.parseBoolean(request.getParameter(PSBD.VAR_ANN));
//						saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte());
						saBean.setInfoComuni(icb);
						saBean.setInfoGara(infoGara);

						// PP B302.2.0 imposto il flag per controllare la validazione
						saBean.getAggiudicazione().setValidaVariazione(true);

						//!!SM MEV #4113 15-01-2019 Inizio 
						List<AggiudicatarioBean> aggiudicatariSelezionati = saBean.getAggiudicatari();
						//!!SM MEV #4113 Fine
						
						
						if(doVariazione == false){
							request.setAttribute(PSBD.VAR_ANN, true);
//							saBean.getAggiudicazione().setIdStato(StatiScheda.IN_DEFINIZIONE);
							saBean.getAggiudicazione().setDescrizioneStato(StatiScheda.VARIAZIONE_CO_STRING);
							saBean.getAggiudicazione().setIdMotivoVarCO(null);
						}
						else 
						{
							saBean = saAction.getBean(request, PSBD.SEZIONE_RQ);
							saBean.setInfoComuni(icb);
							saBean.setInfoGara(infoGara);
							
							// PP B302.2.0 imposto il flag per controllare la validazione
							saBean.getAggiudicazione().setValidaVariazione(true); 
							
							if(validator.valida(saBean, IdentificativoSchede.TAB_ADESIONE)){
							
								String motivazione = StatiScheda.VARIAZIONE_CO_STRING;
	
								RichiestaAnnullamento bean = new RichiestaAnnullamento();
								bean.setId_lotto(idLotto);
								bean.setMotivo_richiesta(motivazione);
								bean.setRichiedente(currentUser.getLogin());
								boolean successo = false;
								
								bean.setId_record(Long.toString(infoGara.getIdAggiudicazione()));
								bean.setData_inizio_record(infoGara.getDataInizioAggiudicazione());
								Timestamp nuovadata = null;
			
								saBean = saAction.getBean(request, PSBD.SEZIONE_RQ);
								saBean.setInfoComuni(icb);
								saBean.setInfoGara(infoGara);
								
								//!!SM MEV #4113 15-01-2019 Inizio 
								saBean.setAggiudicatari(aggiudicatariSelezionati);
								//!!SM MEV #4113 Fine
																
								bean.setBlocco(IdentificativoSchede.TAB_ADESIONE);
								nuovadata = saAction.gestisciVariazioniCO(saBean, bean,  currentUser.getLogin(),infoGara.getTipoEnte());
								successo = nuovadata != null;
								if(successo){
									
									commit(currentActiveConnection);
									infoGara.setDataInizioAggiudicazione(nuovadata);
									validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_083);
									
								}
								else{
									rollback(currentActiveConnection);
									validator.getEccezioni().addValidationErr(SIMOG_VARIAZIONE_CO_001);				
								}
								saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte(), false, infoGara.getIdLotto());
								logger.info("variazione c.o. terminata");								
								//!!SM MEV #4113 15-01-2019 Inizio 
								 if (infoGara.getCIG_ACC_QUADRO()!=null && !"".equals(infoGara.getCIG_ACC_QUADRO().trim())) {
									if(saBean.getAggiudicazione().getIdStato()==StatiScheda.CONFERMATO)
										saBean.setAggiudicatari(getAggiudicatariSelezionati(currentActiveConnection, infoGara.getCIG_ACC_QUADRO(), saBean.getAggiudicatari(), 
												request, response));
									else if(saBean.getAggiudicazione().getIdStato()==StatiScheda.IN_DEFINIZIONE) {
										List<AggiudicatarioBean> listaAgg = getAggiudicatariSelezionatiById(currentActiveConnection, infoGara.getCIG_ACC_QUADRO(), saBean.getAggiudicatari(), 
												request, response);
										saBean.setAggiudicatari(listaAgg);
										if(checkAssCategoria(listaAgg))
											request.setAttribute("ASS_CATEGORIA", "1");
									}
								}
								//!!SM MEV #4113 Fine
							}
							else {
								request.setAttribute(PSBD.VAR_ANN, true);
	//							saBean.getAggiudicazione().setIdStato(StatiScheda.IN_DEFINIZIONE);
								saBean.getAggiudicazione().setDescrizioneStato(StatiScheda.VARIAZIONE_CO_STRING);
							}
						}
					}
					
					else if(PSBD.ACTION_LOAD.equalsIgnoreCase(actionType)){
//						saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte());
						saBean.setInfoComuni(icb);
						saBean.setInfoGara(infoGara);
						
						if(infoGara.getCIG_ACC_QUADRO()!=null && !"".equals(infoGara.getCIG_ACC_QUADRO().trim()) 
						      && saBean.getAggiudicazione().getIdAggiudicazione() == 0){

							SchedaAdesioneAction saa = new SchedaAdesioneAction(currentActiveConnection, logger);
							List<AggiudicatarioBean> aggiudicatariAccQuadro = new ArrayList<AggiudicatarioBean>();
							AggiudicazioneBean aggiudicazioneAccQuadro = new AggiudicazioneBean();
							
							//gm nuovo codice per l'aggiudicazione accordo quadro
							try{
								aggiudicazioneAccQuadro = saa.getBeanAggiudicazioneAccQuadro(infoGara.getCIG_ACC_QUADRO());
							}
							catch (Exception e){
								logger.error("Errore nella ricerca dell'aggiudicazione dell'accordo quadro", e);
								sendError(request, response, Messaggi.SIMOG_AGGIUDICAZIONI_002, ParametriServlet.JSP_GESTIONE_SCHEDE);
							}
							try{
			    				aggiudicatariAccQuadro = saa.getBeanAggiudicatariAccQuadroByAcc(aggiudicazioneAccQuadro);
			    				
							}
							catch (Exception e){
								logger.error("Errore nella ricerca degli aggiudicatari dell'accordo quadro", e);
								sendError(request, response, Messaggi.SIMOG_AGGIUDICAZIONI_002, ParametriServlet.JSP_GESTIONE_SCHEDE);	
							}
							// gm controllo per verificare che l'aggiudicazione dell'accordo quadro esista
							if(aggiudicazioneAccQuadro.getIdAggiudicazione() == 0 || aggiudicatariAccQuadro.size() == 0){							
							   sendMessage(request, response, Messaggi.SIMOG_AGGIUDICAZIONI_084.replace("$1", "["+infoGara.getCIG_ACC_QUADRO()+"]"), ParametriServlet.JSP_GESTIONE_SCHEDE);
							}
							
							saBean.setAggiudicazione(saa.getAggAccQuadroBase(aggiudicazioneAccQuadro));
							saBean.setAggiudicatari(aggiudicatariAccQuadro);
							if(checkAssCategoria(aggiudicatariAccQuadro))
								request.setAttribute("ASS_CATEGORIA", "1");
							//gm fine nuovo codice per l'aggiudicazione accordo quadro
														
	                        // PP precompilazione data scadenza presentazione offerta dal lotto se nuova aggiudicazione
							saBean.getAggiudicazione().setDataScadenzaPresOfferta(PageHelper.getViewDate(infoGara.getDataScadenzaPagamenti()));
						}
						//!!SM MEV #4113 15-01-2019 Inizio 
						else if (infoGara.getCIG_ACC_QUADRO()!=null && !"".equals(infoGara.getCIG_ACC_QUADRO().trim())) {
							if(saBean.getAggiudicazione().getIdStato()==StatiScheda.CONFERMATO)
								saBean.setAggiudicatari(getAggiudicatariSelezionati(currentActiveConnection, infoGara.getCIG_ACC_QUADRO(), saBean.getAggiudicatari(), 
										request, response));
							else if(saBean.getAggiudicazione().getIdStato()==StatiScheda.IN_DEFINIZIONE) {
								List<AggiudicatarioBean> listaAgg = getAggiudicatariSelezionatiById(currentActiveConnection, infoGara.getCIG_ACC_QUADRO(), saBean.getAggiudicatari(), 
										request, response);
								saBean.setAggiudicatari(listaAgg);
								if(checkAssCategoria(listaAgg))
									request.setAttribute("ASS_CATEGORIA", "1");
							}
						}
						//!!SM MEV #4113 Fine

							   //se la modalita' di realizzazione della gara e' adesione ad accordo quadro/convenzione senza successivo confronto competitivo
						//3.04.8 34190 fix	   
						if( (saBean.getInfoGara().getID_MODO_REAL() == Costanti.MODOREAL_ADESIONE_NOCOMPET || saBean.getInfoGara().getID_MODO_REAL() == Costanti.MODOREAL_CONCESSIONE_NOCOMPET) 
									   && saBean.getAggiudicazione().getIdAggiudicazione() < 1 ){ 
	      						   saBean.getAggiudicazione().setPercRibassoAgg(BigDecimal.ZERO);
	      						   saBean.getAggiudicazione().setPercOffAumento(BigDecimal.ZERO);
	      						  
	      						   Lotto lotto = null;
	      						   LottoManager lm = new LottoManager(currentActiveConnection, logger);
	      						   List<Lotto> lotti = lm.getLottoByCigWS(saBean.getInfoGara().getCig()+saBean.getInfoGara().getCigKKK());

	      						   if (lotti != null && lotti.size() > 0) {
	      				               lotto = lotti.get(0);
		      						   BigDecimal importo_lotto = lotto.getImporto_Lotto();
		      						   String data_verbale = lotto.getData_Pubblicazione();
		      						   saBean.getAggiudicazione().setImportoAggiudicazione( importo_lotto );
		      						   saBean.getAggiudicazione().setDataVerbaleAggiudicazione(data_verbale);
	      						   }
	                               //Fine Ticket ALM #702
	      						   
	      						   validator.getEccezioni().addValidationWarn(Messaggi.SIMOG_AGGIUDICAZIONI_085);
							   }
						
						
						dataInizioScheda = saBean.getAggiudicazione().getDataInizioAggiudicazione();
						if( infoGara.getIdAggiudicazione() > 0)
							validator.valida(saBean, IdentificativoSchede.TAB_ADESIONE);			
					}				
					// Salva
					else if (PSBD.ACTION_SALVA.equalsIgnoreCase(actionType)) {
						saBean = saAction.getBean(request, PSBD.SEZIONE_RQ);

						saBean.setInfoComuni(icb);
						saBean.setInfoGara(infoGara);
				
						if(saBean.getAggiudicazione().getIdAggiudicazione() < 1 && isRefresh(request)){						
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
					
							saAction.sendValidations(request, validator.getEccezioni());
							
							forward(ParametriServlet.JSP_ERRORE, request, response);
							return;
						}
						// se la scheda esiste impedisco l'operazione
						if(saBean.getAggiudicazione().getIdAggiudicazione() < 1 && saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte(), false, infoGara.getIdLotto()).getAggiudicazione().getIdAggiudicazione()>0){						
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
					
							saAction.sendValidations(request, validator.getEccezioni());
							
							forward(ParametriServlet.JSP_ERRORE, request, response);
							return;
						}

						if(validator.valida(saBean, IdentificativoSchede.TAB_ADESIONE)){
							saBean.getAggiudicazione().setSottotipo(TipoAggiudicazione.Q);
							
							// PP 10.06.2016 (obino) forzatura scelta contraente per adesione senza SCC
							//3.04.8 34190 fix
							if ((Costanti.MODOREAL_ADESIONE_NOCOMPET == infoGara.getID_MODO_REAL() || Costanti.MODOREAL_CONCESSIONE_NOCOMPET == infoGara.getID_MODO_REAL())
							      && saBean.getAggiudicazione().getIdSceltaContraente() <= 0){
							   saBean.getAggiudicazione().setIdSceltaContraente(Costanti.AFF_DIR_ADESIONE);
							}
							
							if(infoGara.getIdAggiudicazione() < 1){
								saAction.create(saBean, getModifiedFlags(request), currentUser.getLogin());
								infoGara.setIdAggiudicazione(saBean.getAggiudicazione().getIdAggiudicazione());
								infoGara.setDataInizioAggiudicazione(saBean.getAggiudicazione().getDataInizioAggiudicazione());
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_023);
							}
							else {
							   
	                     // PP 10.06.2016 (obino) forzatura scelta contraente per adesione senza SCC
								//3.04.8 34190 fix
	                     if ((Costanti.MODOREAL_ADESIONE_NOCOMPET == infoGara.getID_MODO_REAL() || Costanti.MODOREAL_CONCESSIONE_NOCOMPET == infoGara.getID_MODO_REAL())
	                           && saBean.getAggiudicazione().getIdSceltaContraente() <= 0){
	                        saBean.getAggiudicazione().setIdSceltaContraente(Costanti.AFF_DIR_ADESIONE);
	                     }
								saAction.save(saBean, getModifiedFlags(request), currentUser.getLogin());
								validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_024);
							}
							commit(currentActiveConnection);
							saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte(), false, infoGara.getIdLotto());

							//!!SM MEV #4113 15-01-2019 Inizio 
							 if (infoGara.getCIG_ACC_QUADRO()!=null && !"".equals(infoGara.getCIG_ACC_QUADRO().trim())) {
									if(saBean.getAggiudicazione().getIdStato()==StatiScheda.CONFERMATO)
										saBean.setAggiudicatari(getAggiudicatariSelezionati(currentActiveConnection, infoGara.getCIG_ACC_QUADRO(), saBean.getAggiudicatari(), 
												request, response));
									else if(saBean.getAggiudicazione().getIdStato()==StatiScheda.IN_DEFINIZIONE) {
										List<AggiudicatarioBean> listaAgg = getAggiudicatariSelezionatiById(currentActiveConnection, infoGara.getCIG_ACC_QUADRO(), saBean.getAggiudicatari(), 
												request, response);
										saBean.setAggiudicatari(listaAgg);
										if(checkAssCategoria(listaAgg))
											request.setAttribute("ASS_CATEGORIA", "1");
									}
								}
							//!!SM MEV #4113 Fine
						} else {
							//!!SM MEV #4113 15-01-2019 Inizio 
							 if (infoGara.getCIG_ACC_QUADRO()!=null && !"".equals(infoGara.getCIG_ACC_QUADRO().trim())) {
									if(saBean.getAggiudicazione().getIdStato()==StatiScheda.CONFERMATO)
										saBean.setAggiudicatari(getAggiudicatariSelezionati(currentActiveConnection, infoGara.getCIG_ACC_QUADRO(), saBean.getAggiudicatari(), 
												request, response));
//									else if(saBean.getAggiudicazione().getIdStato()==StatiScheda.IN_DEFINIZIONE) //TICKET ALM #14728
									else {
										List<AggiudicatarioBean> listaAgg = getAggiudicatariSelezionatiById(currentActiveConnection, infoGara.getCIG_ACC_QUADRO(), saBean.getAggiudicatari(), 
												request, response);
										saBean.setAggiudicatari(listaAgg);
										if(checkAssCategoria(listaAgg))
											request.setAttribute("ASS_CATEGORIA", "1");
									}
								}
							//!!SM MEV #4113 Fine
						}
					} 
					// Conferma
					else if (PSBD.ACTION_CONFERMA.equalsIgnoreCase(actionType)) {
						saBean = saAction.getBean(request, PSBD.SEZIONE_RQ);
						saBean.setInfoComuni(icb);
						saBean.setInfoGara(infoGara);

				  //!!SM MEV #4113 15-01-2019 Inizio
/*						
                  // [Ticket#2015060810000642] [Ticket#2015052888012592]
                  // sovrascrivo sempre gli aggiudicatari perche' non arrivano dal web
                  SchedaAdesioneAction saa = new SchedaAdesioneAction(currentActiveConnection, logger);
                  List<AggiudicatarioBean> aggiudicatariAccQuadro = new ArrayList<AggiudicatarioBean>();
                  AggiudicazioneBean aggiudicazioneAccQuadro = new AggiudicazioneBean();
                  
                  try{
                     aggiudicazioneAccQuadro = saa.getBeanAggiudicazioneAccQuadro(infoGara.getCIG_ACC_QUADRO());
                  }
                  catch (Exception e){
                     logger.error("Errore nella ricerca dell'aggiudicazione dell'accordo quadro", e);
                     sendError(request, response, Messaggi.SIMOG_AGGIUDICAZIONI_002, ParametriServlet.JSP_GESTIONE_SCHEDE);
                  }
                  try{
                     aggiudicatariAccQuadro = saa.getBeanAggiudicatariAccQuadro(aggiudicazioneAccQuadro);
                  }
                  catch (Exception e){
                     logger.error("Errore nella ricerca degli aggiudicatari dell'accordo quadro", e);
                     sendError(request, response, Messaggi.SIMOG_AGGIUDICAZIONI_002, ParametriServlet.JSP_GESTIONE_SCHEDE);   
                  }
                  
                  saBean.setAggiudicatari(aggiudicatariAccQuadro);
*/                  
				  saBean.setAggiudicatari(saBean.getAggiudicatari());
				  //!!SM MEV #4113 Fine

                  
				  if(validator.valida(saBean, IdentificativoSchede.TAB_ADESIONE)){
					saBean.getAggiudicazione().setSottotipo(TipoAggiudicazione.Q);
							
                     // PP 10.06.2016 (obino) forzatura scelta contraente per adesione senza SCC
                     if ((Costanti.MODOREAL_ADESIONE_NOCOMPET == infoGara.getID_MODO_REAL() || Costanti.MODOREAL_CONCESSIONE_NOCOMPET == infoGara.getID_MODO_REAL())
                           && saBean.getAggiudicazione().getIdSceltaContraente() <= 0) {
                        saBean.getAggiudicazione().setIdSceltaContraente(Costanti.AFF_DIR_ADESIONE);
                     }
                     
					 saAction.confirm(saBean, getModifiedFlags(request), currentUser.getLogin());
					 commit(currentActiveConnection);
					 validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_025);
					 saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte(), false, infoGara.getIdLotto());					 
					 //!!SM MEV #4113 15-01-2019 Inizio
					 //Commmentato per fare in modo che nella scheda confermata siano visualizzati solamente gli aggiudicatari selezionati
//					 saBean.setAggiudicatari(getAggiudicatariSelezionati(currentActiveConnection, infoGara.getCIG_ACC_QUADRO(), saBean.getAggiudicatari(), 
//								request, response));
					 //!!SM MEV #4113 Fine
				  }	else {
						//!!SM MEV #4113 15-01-2019 Inizio 
					  if (infoGara.getCIG_ACC_QUADRO()!=null && !"".equals(infoGara.getCIG_ACC_QUADRO().trim())) {
							if(saBean.getAggiudicazione().getIdStato()==StatiScheda.CONFERMATO)
								saBean.setAggiudicatari(getAggiudicatariSelezionati(currentActiveConnection, infoGara.getCIG_ACC_QUADRO(), saBean.getAggiudicatari(), 
										request, response));
							else if(saBean.getAggiudicazione().getIdStato()==StatiScheda.IN_DEFINIZIONE) {
								List<AggiudicatarioBean> listaAgg = getAggiudicatariSelezionatiById(currentActiveConnection, infoGara.getCIG_ACC_QUADRO(), saBean.getAggiudicatari(), 
										request, response);
								saBean.setAggiudicatari(listaAgg);
								if(checkAssCategoria(listaAgg))
									request.setAttribute("ASS_CATEGORIA", "1");
							}
						}
						//!!SM MEV #4113 Fine
					}																
				} 
					// Jsp Annullamento
					else if (PSBD.ACTION_CARICA_JSP_ANNULLAMENTO.equalsIgnoreCase(actionType)) {
						boolean isOk = (saAction.bsa.hasSchedaRichDelete(String.valueOf(infoGara.getIdAggiudicazione()), IdentificativoSchede.TAB_ADESIONE, false).getFullSize()==0);
						
						if (isOk){
							request.setAttribute(PSBD.TAB, IdentificativoSchede.TAB_ADESIONE);
							String dest = ParametriServlet.JSP_RICHIEDI_ANNULLAMENTO + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGara.getIdAggiudicazione();
							dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGara.getDataInizioAggiudicazione();
							forward(dest, request, response);
							return;						
						}
						else{
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					// Jsp Cancellazione
					else if (PSBD.ACTION_CARICA_JSP_CANCELLAZIONE.equalsIgnoreCase(actionType)) {					
						boolean isOk = (saAction.bsa.hasSchedaRichDelete(String.valueOf(infoGara.getIdAggiudicazione()), IdentificativoSchede.TAB_ADESIONE, true).getFullSize()==0);
						
						if (isOk){
							request.setAttribute(PSBD.TAB, IdentificativoSchede.TAB_ADESIONE);
							String dest = ParametriServlet.JSP_RICHIEDI_CANCELLAZIONE + "?" + PSBD.FIELD_NAME_ID_AGGIUDICAZIONE + "=" + infoGara.getIdAggiudicazione();
							dest+="&" + PSBD.DATA_INIZIO_AGGIUDICAZIONE + "=" + infoGara.getDataInizioAggiudicazione();
							forward(dest, request, response);
							return;						
						}
						else{
							validator.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "cancellazione"));
						}
					}				
					// Richiesta annullamento
					else if (PSBD.ACTION_RICHIESTA_ANNULLAMENTO.equalsIgnoreCase(actionType)) {
						//controllo per le aggiudicazioni multilotto
						String codiceContratto = saBean.getAggiudicazione().getCodiceContratto();
						if(codiceContratto!=null && !"".equals(codiceContratto)){
							MultilottoManager mm = new MultilottoManager(currentActiveConnection, logger);
							mm.eliminaGruppo(infoGara.getIdGara(), codiceContratto);
						}
						
						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_ANNULLAMENTO);
						
						RichiestaAnnullamento bean = new RichiestaAnnullamento();
						bean.setId_lotto(idLotto);
						bean.setMotivo_richiesta(motivazione);
						bean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						bean.setRichiedente(currentUser.getLogin());
						bean.setBlocco(IdentificativoSchede.TAB_ADESIONE);
						boolean successo = false;
						
						bean.setId_record(Long.toString(infoGara.getIdAggiudicazione()));
						bean.setData_inizio_record(infoGara.getDataInizioAggiudicazione());
						Timestamp nuovadata = null;
	
						nuovadata = saAction.richiediAnnullamento(bean);					
						successo = nuovadata != null;										
						
                        // 3.02.2.1 accettazione immediata richiesta
                        if(SimogFlags.is30230_RFWEBSC03Active()){
                           
                           infoGara.setDataInizioAggiudicazione(nuovadata);
                           
                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(bean.getBlocco(), currentActiveConnection, logger);
                           
                           bean.setDecisore(currentUser.getLogin());
                           bean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
                           bean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
                           
                           successo = annAction.gestisciRichiesta(bean,currentUser.getLogin());
                        
                           if(successo){
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
      							infoGara.setDataInizioAggiudicazione(nuovadata);
      							validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_080);						
      						}
      						else{
      							rollback(currentActiveConnection);
      							validator.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);				
      						}
                       }
      					saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte(), false, infoGara.getIdLotto());
						//!!SM MEV #4113 15-01-2019 Inizio 
      					 if (infoGara.getCIG_ACC_QUADRO()!=null && !"".equals(infoGara.getCIG_ACC_QUADRO().trim())) {
								if(saBean.getAggiudicazione().getIdStato()==StatiScheda.CONFERMATO)
									saBean.setAggiudicatari(getAggiudicatariSelezionati(currentActiveConnection, infoGara.getCIG_ACC_QUADRO(), saBean.getAggiudicatari(), 
											request, response));
								else if(saBean.getAggiudicazione().getIdStato()==StatiScheda.IN_DEFINIZIONE) {
									List<AggiudicatarioBean> listaAgg = getAggiudicatariSelezionatiById(currentActiveConnection, infoGara.getCIG_ACC_QUADRO(), saBean.getAggiudicatari(), 
											request, response);
									saBean.setAggiudicatari(listaAgg);
									if(checkAssCategoria(listaAgg))
										request.setAttribute("ASS_CATEGORIA", "1");
								}
							}
						//!!SM MEV #4113 Fine
					}			
					else if (PSBD.ACTION_RICHIESTA_CANCELLAZIONE.equalsIgnoreCase(actionType)) {
						//controllo per le aggiudicazioni multilotto
						String codiceContratto = saBean.getAggiudicazione().getCodiceContratto();
						if(codiceContratto!=null && !"".equals(codiceContratto)){
							MultilottoManager mm = new MultilottoManager(currentActiveConnection, logger);
							mm.eliminaGruppo(infoGara.getIdGara(), codiceContratto);
						}
						
						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_CANCELLAZIONE);
						
						RichiestaAnnullamento bean = new RichiestaAnnullamento();
						bean.setCancellazione(request.getParameter(PSBD.CANC_COMPLETA)) ;
						bean.setId_lotto(idLotto);
						bean.setMotivo_richiesta(motivazione);
						bean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						bean.setRichiedente(currentUser.getLogin());
						bean.setId_record(Long.toString(infoGara.getIdAggiudicazione()));
						bean.setData_inizio_record(infoGara.getDataInizioAggiudicazione());
						bean.setBlocco(IdentificativoSchede.TAB_ADESIONE);
						saAction.richiediCancellazione(bean);
						
                        // 3.02.2.1 accettazione immediata richiesta
                        if(SimogFlags.is30230_RFWEBSC03Active()){
                           boolean successo;

                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(bean.getBlocco(), currentActiveConnection, logger);
                            
                           bean.setDecisore(currentUser.getLogin());
                           bean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
                           bean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
                           
                           successo = annAction.gestisciRichiestaCancellazione(bean,currentUser.getLogin());
                    
                           if(successo){
                              validator.getEccezioni().addValidationInfo(Messaggi.SIMOG_RICHIESTA_CANCELLAZIONE_002);
                               commit(currentActiveConnection);
                               
                               // forward a lista aggiudicazioni
                               pagina = ParametriServlet.SRV_VISUALIZZA_RIEPILOGO_SCHEDA
                                   + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO+"="+bean.getId_lotto()
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
						saBean = saAction.load(infoGara.getIdAggiudicazione(), infoGara.getDataInizioAggiudicazione(), infoGara.getTipoEnte(), false, infoGara.getIdLotto());
						//!!SM MEV #4113 15-01-2019 Inizio 
						 if (infoGara.getCIG_ACC_QUADRO()!=null && !"".equals(infoGara.getCIG_ACC_QUADRO().trim())) {
								if(saBean.getAggiudicazione().getIdStato()==StatiScheda.CONFERMATO)
									saBean.setAggiudicatari(getAggiudicatariSelezionati(currentActiveConnection, infoGara.getCIG_ACC_QUADRO(), saBean.getAggiudicatari(), 
											request, response));
								else if(saBean.getAggiudicazione().getIdStato()==StatiScheda.IN_DEFINIZIONE) {
									List<AggiudicatarioBean> listaAgg = getAggiudicatariSelezionatiById(currentActiveConnection, infoGara.getCIG_ACC_QUADRO(), saBean.getAggiudicatari(), 
											request, response);
									saBean.setAggiudicatari(listaAgg);
									if(checkAssCategoria(listaAgg))
										request.setAttribute("ASS_CATEGORIA", "1");
								}
							}
						//!!SM MEV #4113 Fine
					}	
/*** PP vecchia					
					else if (PSBD.ACTION_CERCA.equalsIgnoreCase(actionType)) {						
						TableBean soggetti = null;
						if (PSBD.TAB_AFFIDATARIO.equalsIgnoreCase(tab) ) {
							logger.debug("ACTION:cerca affidatari - START");
							AggiudicatarioAction agg = new AggiudicatarioAction(currentActiveConnection,logger);
							soggetti = agg.cerca(request);	
							logger.debug("ACTION:cerca affidatari - END");
						}
						else{
							logger.debug("ACTION:cerca responsabili - START");
							ResponsabileAction resp = new ResponsabileAction(currentActiveConnection,logger);
							soggetti = resp.cerca(request,tab);
							logger.debug("ACTION:cerca responsabili - END");
						}
						pagina= "scheda1/popupRubrica.jsp";
						request.removeAttribute(PSBD.BLOCCO_DATI_SOGGETTI);
						
						request.setAttribute(PSBD.BLOCCO_DATI_SOGGETTI, soggetti);
					}			
****/					
					saBean.setInfoComuni(icb);	/** Dopo ogni load di saBean, infoComuni si perde. Qui viene risettato **/

					if (saBean != null) {
						saBean.getAggiudicazione().setOkCancellazione(saAction.bsa.isCancellabile(IdentificativoSchede.TAB_ADESIONE, 
								saBean.getAggiudicazione().getIdAggiudicazione(), 
								saBean.getAggiudicazione().getDataInizioAggiudicazione(),
								saBean.getAggiudicazione().getIdStato(),
								saBean.getInfoComuni().getFlagEnteSpeciale(), 
								saBean.getInfoComuni().getTipoContratto(),
								saBean.getAggiudicazione().getIdAggiudicazione(),
								saBean.getAggiudicazione().getDataInizioAggiudicazione()));
					}			
					// preimposto i valori della eventuale pubblicazione bando (nuts,istat)
					if(saBean.getAggiudicazione().getIdAggiudicazione() < 1 && saBean.getAggiudicazione().getLuogoIstat()==null){
					    if(infoGara.getLUOGO_ISTAT() != null )
						    saBean.getAggiudicazione().setLuogoIstat(infoGara.getLUOGO_ISTAT());
					}
					if(saBean.getAggiudicazione().getIdAggiudicazione() < 1 && saBean.getAggiudicazione().getLuogoNuts()==null){
					    if(infoGara.getLUOGO_NUTS() != null )
						    saBean.getAggiudicazione().setLuogoNuts(infoGara.getLUOGO_NUTS());
					}
					
					/***************** DETTAGLI RICH ANN ************************/
					
					TableBean tabRichAnn = saAction.bsa.getRichAnnByScheda(
							String.valueOf(saBean.getAggiudicazione().getIdAggiudicazione()), 
							IdentificativoSchede.TAB_ADESIONE, false);
					
					request.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);

					/***************** DETTAGLI VAR ANAG ************************/				
					TableBean tabVarAnag = saAction.bsa.getVarAnagByScheda(String.valueOf(saBean.getAggiudicazione().getIdAggiudicazione()), 
							IdentificativoSchede.TAB_ADESIONE);
					
					request.setAttribute(PSBD.TAB_VARANAG, tabVarAnag);

					
					/***************** END              *************************/
					
					//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
					if(saBean != null && saBean.getAggiudicazione().getIdStato() == StatiScheda.CONFERMATO
							&& !(PSBD.ACTION_VARIAZIONI_ANAGRAFICHE.equalsIgnoreCase(actionType) || PSBD.ACTION_VARIAZIONI_ANAGRAFICHE_SAVE.equalsIgnoreCase(actionType))
					)
						saAction.sendValidations(request, validator.getEccezioni().getAllInfoEWarn());
					else
						saAction.sendValidations(request, validator.getEccezioni());
					
					request.setAttribute("schedaA", saBean);
					//request.setAttribute(ParametriServlet.TIPO_APPALTO_BEAN, saAction.loadComboTipoAppalto(request));
					//request.setAttribute(ParametriServlet.TIPO_APPALTO_BEAN_F, saAction.loadTipiAppaltoContratto(request, Costanti.TIPO_SCHEDA_FORNITURE ,dataInizioScheda));
					//request.setAttribute(ParametriServlet.TIPO_APPALTO_BEAN_L, saAction.loadTipiAppaltoContratto(request, Costanti.TIPO_SCHEDA_LAVORI,dataInizioScheda));
					request.setAttribute(ParametriServlet.TIPO_PRESTAZIONE_BEAN, saAction.loadComboTipoPrestazione(request,dataInizioScheda));
					request.setAttribute(ParametriServlet.SCELTA_CONTRAENTE_BEAN, saAction.loadSceltaContraente(infoGara.getDataCreazioneGara(), infoGara.getCfAmministrazione(), infoGara.getIdOsservatorio()));
					//request.setAttribute(ParametriServlet.CONDIZIONI_AGG_BEAN, saAction.loadCondizioniAggiuntive(dataInizioScheda));
					//request.setAttribute(ParametriServlet.CRITERI_AGGIUDICAZIONE_BEAN, saAction.loadCriteriAggiudicazione(dataInizioScheda));
					//request.setAttribute(ParametriServlet.MODO_INDIZIONE_GARA, saAction.loadModoIndizione(dataInizioScheda));
					request.setAttribute(ParametriServlet.CATEGORIA_BEAN, saAction.loadCategoria(dataInizioScheda, infoGara.getCfAmministrazione()));
					request.setAttribute(ParametriServlet.CLASSI_IMPORTO_BEAN, saAction.loadClasseImporto(dataInizioScheda));
					request.setAttribute(ParametriServlet.TIPO_AGGIUDICATARIO_BEAN, saAction.loadTipoAggiudicatario(dataInizioScheda));
					request.setAttribute(ParametriServlet.RUOLI_RESPONSABILE_BEAN, saAction.loadRuoliSezione(PSBD.SEZIONE_RQ,dataInizioScheda));
					request.setAttribute(ParametriServlet.RUOLI_PRESTAZIONE_BEAN, saAction.loadRuoliSezione(PSBD.SEZIONE_PA,dataInizioScheda));
					request.setAttribute(ParametriServlet.TIPO_FINANZIAMENTO_BEAN, saAction.loadFinanziamenti(dataInizioScheda));
					request.setAttribute(ParametriServlet.TIPO_STRUMENTO_BEAN, saAction.loadStrumenti(dataInizioScheda));
					
					// PP B302.2.0
					if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive())
						request.setAttribute(ParametriServlet.MOTIVO_VCO_BEAN, saAction.bsa.loadMotiviVCO(dataInizioScheda));
					else
						request.setAttribute(ParametriServlet.MOTIVO_VCO_BEAN, new HashMap<String,String>());

					setDatiGara(infoGara, request.getSession());
					forward(pagina , request, response);				
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
					rollback(currentActiveConnection);
					sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE, e );				
					return;
				} finally {
				logger.debug("Chiudo connessione");
					closeConnection(request.getSession().getId(),getClass().getName());
				}			    			
			}
			else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				return;
			}
		}
		else {
			sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			return;
		}
	}
	
	private boolean checkAssCategoria(List<AggiudicatarioBean> listaAgg) {
		boolean res = false;
		for(AggiudicatarioBean bean : listaAgg) {
			if(bean.getIdTipoAgg()==Costanti.TIPODITTA_ASS_CAT) {
				res = true;
				break;
			}
		}
		System.out.println("TB checkAssCategoria: "+res);
		return res;
	}

	/*******************************************************************************
	 * Il metodo restituisce un vettore contenente elementi di valore "true"
	 * @param request HttpServletRequest
	 * @return Boolean[]
	 */
	private Boolean[] getModifiedFlags(HttpServletRequest request){
		int num = 8;
		Boolean[] result = new Boolean[num];
		for(int i = 0; i<num;i++){
//			String mod = request.getParameter("Modificato"+i);
//			if(mod != null)
//			  result[i] = mod.equals("1");
//			else result[i] = false;
			result[i] = true;
		}
		return result;		  
	}
}
