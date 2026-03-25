package it.avlp.simog.servlet.aggiudicazione;

import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.bdncp.BdncpManager;
import it.avlp.simog.actions.DelegaDatiSimogAction;
import it.avlp.simog.actions.aggiudicazione.InfoComuniAction;
import it.avlp.simog.beans.EsitoEnum;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.beans.VO;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.common.action.BaseRichiestaAnnullamento;
import it.avlp.simog.common.action.RequisitiGLAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.db.generated.FINE_LAVORI;
import it.avlp.simog.db.generated.INIZIO_LAVORI;
import it.avlp.simog.db.generated.R129;
import it.avlp.simog.db.generated.STIPULA;
import it.avlp.simog.db.generated.SUBAPPALTI;
import it.avlp.simog.db.generated.VARIANTI;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.factory.AnnullamentoFactory;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.validatore.SimogFlusso;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.TipoFlusso;
import it.avlp.simog.validatore.factory.ValidatorFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SrvDatiComuni extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7147215868373215565L;

	public void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	   
	   String action = request.getParameter("toDo");
       if("qry".equalsIgnoreCase(action)){

          String settore = request.getParameter("s");
          String contratto = request.getParameter("c");
          String escluso = ("true".equals(request.getParameter("e")) ? Costanti.FLAG_VALORE_SI : Costanti.FLAG_VALORE_NO);
          Integer modoReal = Integer.valueOf(request.getParameter("m"));
          String dataPubb = request.getParameter("d");
          Float importo =  Float.valueOf(request.getParameter("i"));
          
          SimogFlusso fl = new SimogFlusso(settore, contratto, escluso, modoReal.intValue(), dataPubb, importo.floatValue());
          
          PrintWriter out = response.getWriter();
          out.println(fl.getDescrizioneTipoFlusso());
       }
       else
		perform(request, response);
	}

	@Override
	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		
		String forwPage = PSBD.JSP_DATI_COMUNI;
		
		if (checkSession(request)) {
			if (currentUser.isRUP() || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA()) {
				try{
					
					request.setAttribute(PSBD.NOME_SCHEDA,IdentificativoSchede.getDatiComuni().getDecodificaVN()) ;
					
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					currentActiveConnection.setAutoCommit(false);
					String action = request.getParameter("toDo");
					if (action == null || action.equalsIgnoreCase("load") || currentUser.isOssReg() || currentUser.isAmministratore() || currentUser.isRASA() )
						action = PSBD.ACTION_LOAD;
					InfoGaraBean infoGara = getDatiGara(request.getSession());
					
					
					InfoComuniAction iAction = new InfoComuniAction(currentActiveConnection,logger);
					InfoComuniBean infoComuniBean = new InfoComuniBean();
					Timestamp dataInizioScheda= null;
					Scheda_A scheda_a = new Scheda_A();
					SimogValidator scheda_a_val = ValidatorFactory.getValidator(IdentificativoSchede.TAB_AGGIUDICAZIONE, currentActiveConnection, logger);
					
					//******************* creo il TableBean per le informazioni sulla Presa Incarico *********************
					TableBean tBean = null;
					AggiudicazioniManager aggMan = new AggiudicazioniManager(currentActiveConnection, logger);
					
					tBean = aggMan.getInfoPresaIncarico(infoGara.getIdInfo());
					//Metto la TableBean nella Request
					request.setAttribute("infoPresaIncarico" , tBean );
					
					List<AggiudicazioneBean> listaAggiudicazioni = aggMan.getAggiudicazioniList(infoGara.getIdInfo(), infoGara.getDataInizioInfo());
					//Verifica presenta aggiudicazioni
					boolean hasAwards = listaAggiudicazioni.size()>0;
					
					//*****************************************************************************************************
					
					DelegaDatiSimogAction dasAction = new DelegaDatiSimogAction(currentActiveConnection, logger);
					String res = dasAction.getDelegaSchedeMessage(infoGara.getIdOsservatorio(), PageHelper.getCurrentUtilDate(), currentUser);
					boolean cantModify = res != null && currentUser.isRSSAorRUP();
					
//					MAC 34163 3.04.8
					
					if (infoGara.getIdInfo() == 0) {
						long idGara = infoGara.getIdGara();
						Gara gara = new Gara();
						GaraManager gm = new GaraManager(currentActiveConnection, logger);
						gara = gm.getGara(Long.valueOf(idGara), currentUser.getUffici());
						if(gara == null){
							sendError(request, response, Messaggi.SIMOG_DELEGA_004, JSP_ERRORE );
							return;
						}
					}
					
//					FINE MAC 34163
					
					if(cantModify){
						//aggiungo l'errore e cambio l'azione di default a load, cosi non puo salvare o fare altro
//						sendError(request, res);
						scheda_a_val.getEccezioni().addValidationErr(res);
						
						if (listaAggiudicazioni.size()>0)
							action = PSBD.ACTION_LOAD;
					}
					request.getSession().setAttribute("delegaSchede", cantModify);
					
               /*     CHECK BLOCCO AVCPASS   */
                  //verifico se posso modificare i dati (blocco avcpass)
                  // richiamo il servizio AVCPASS
                  // GaraManager gm = new GaraManager(currentActiveConnection, logger);
                  // Gara gara = gm.getGara(infoGara.getIdGara());
                  //RequisitiGLAction requisitiGLAction = new RequisitiGLAction(currentActiveConnection, logger);
                  // List<Lotto> listaLotti = requisitiGLAction.getLottoList( infoGara.getIdGara() );
                  LottoManager lman = new LottoManager(currentActiveConnection, logger);
                  List<Lotto> lotto = lman.getLottoByCigWS(infoGara.getFullCIG());
                  
                  AVCPassAction avpa = new AVCPassAction(currentActiveConnection, logger, configuration);
                  Boolean blocco = avpa.isAVCPass(null, lotto, AVCPassFunzioneEnum.WEB_ACQUISIZIONE_SCHEDE_AGGIUNTIVE.getCodice());
                  //non applicare il blocco avcpass se la gara e' una delega di tipo Proposta di Aggiudicazione
                  if(blocco) {
                	  GaraManager gm = new GaraManager(currentActiveConnection, logger);
		                Gara gara = gm.getGara(infoGara.getIdGara());
		                blocco = gara.getID_F_DELEGATE()!=Costanti.DELEGA4;
                  }
                  
                  
                  if (blocco){
                     //aggiungo l'errore e cambio l'azione di default a load, cosi non puo salvare o fare altro
                     // uso il livello info perche altrimenti non si vede!!!
                     scheda_a_val.getEccezioni().addValidationInfo(Messaggi.SIMOG_AVCPASS_001);
                     
                     if (listaAggiudicazioni.size()>0)
                        action = PSBD.ACTION_LOAD;
                     request.getSession().setAttribute("delegaSchede", true);
                  }
               
               request.setAttribute("readonlyDelegante", "readable");
               if(SimogProperties.getInstance().isDataCreatedAfter3045(infoGara.getDataCreazioneGara())) 
					request.setAttribute("readonlyDelegante", "readonly");
                              
					//gm aggiunto controllo per dati comuni di adesione ad accordo quadro,
					//se l'aggiudicazione dell'accordo quadro non e stata confermata mando un errore
					if(infoGara.getCIG_ACC_QUADRO()!=null && !"".equals(infoGara.getCIG_ACC_QUADRO().trim())){
						List<AggiudicatarioBean> aggiudicatariAccQuadro = new ArrayList<AggiudicatarioBean>();
						AggiudicazioneBean aggiudicazioneAccQuadro = new AggiudicazioneBean();			
						//gm nuovo codice per l'aggiudicazione accordo quadro
						try{
							aggiudicazioneAccQuadro = iAction.getBeanAggiudicazioneAccQuadro(infoGara.getCIG_ACC_QUADRO());
						}
						catch (Exception e){
							logger.error("Errore nella ricerca dell'aggiudicazione dell'accordo quadro", e);
							sendError(request, response, Messaggi.SIMOG_AGGIUDICAZIONI_002, ParametriServlet.JSP_GESTIONE_SCHEDE);
						}
						try{
		    				aggiudicatariAccQuadro = iAction.getBeanAggiudicatariAccQuadro(aggiudicazioneAccQuadro);
						}
						catch (Exception e){
							logger.error("Errore nella ricerca degli aggiudicatari dell'accordo quadro", e);
							sendError(request, response, Messaggi.SIMOG_AGGIUDICAZIONI_002, ParametriServlet.JSP_GESTIONE_SCHEDE);	
						}
						// gm controllo per verificare che l'aggiudicazione dell'accordo quadro esista
						if(aggiudicazioneAccQuadro.getIdAggiudicazione() == 0 || aggiudicatariAccQuadro.size() == 0){							
                            scheda_a_val.getEccezioni().addValidationErr(Messaggi.SIMOG_AGGIUDICAZIONI_084.replace("$1", "["+infoGara.getCIG_ACC_QUADRO()+"]"));
							//sendMessage(request, response, Messaggi.SIMOG_AGGIUDICAZIONI_084.replace("$1", "["+infoGara.getCIG_ACC_QUADRO()+"]"), ParametriServlet.JSP_GESTIONE_SCHEDE);
						}
					}
					
                    if(PSBD.ACTION_LOAD.equalsIgnoreCase(action)){
						infoComuniBean = iAction.load(infoGara.getIdInfo(), infoGara.getDataInizioInfo());
						
						scheda_a.setInfoComuni(infoComuniBean);
						scheda_a.setInfoGara(infoGara);
						dataInizioScheda = infoComuniBean.getDataInizioInfo();
						scheda_a_val.valida(scheda_a,IdentificativoSchede.TAB_INFO_COMUNI);
						
						
						//TICKET ALM #13519 - 3.04.4.1
						if(SimogProperties.getInstance().isDataCreatedAfter3044(infoGara.getDataCreazioneGara())) {
							GaraManager gm = new GaraManager(currentActiveConnection, logger);
			                Gara gara = gm.getGara(infoGara.getIdGara());
			                if(Costanti.FLAG_VALORE_SI.equals(gara.getFlagSAAgente())) {
			                	List<String> datiStorici = gm.getDatiStoriciGaraDelegata(infoGara.getIdGara());
			                	if(!datiStorici.isEmpty() && !currentUser.getLogin().equals(datiStorici.get(0)))
			                		request.setAttribute("readonlyDelegante", "readonly");
			                }
						}
						
						//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
						if(infoComuniBean != null && infoComuniBean.getIdStato() == StatiScheda.CONFERMATO)
							iAction.sendValidations(request, scheda_a_val.getEccezioni().getAllInfoEWarn());
						else
							iAction.sendValidations(request, scheda_a_val.getEccezioni());
					}
					else if(PSBD.ACTION_SALVA.equalsIgnoreCase(action)){	
						
						infoComuniBean = iAction.getBean(request);

						if (infoComuniBean.getCfRup() == null)
							infoComuniBean.setCfRup(currentUser.getLogin());

						infoComuniBean.setIdLotto(infoGara.getIdLotto());
						infoComuniBean.setHasAwards(hasAwards);
						scheda_a.setInfoComuni(infoComuniBean);
						scheda_a.setInfoGara(infoGara);

						if(infoComuniBean.getIdInfo() < 1 && isRefresh(request)){						
							scheda_a_val.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
							iAction.sendValidations(request, scheda_a_val.getEccezioni());
						// se la scheda esiste impedisco l'operazione
						}else if(infoComuniBean.getIdInfo() < 1 && iAction.checkScheda(infoComuniBean.getCig())){								 				
							scheda_a_val.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_000);
							iAction.sendValidations(request, scheda_a_val.getEccezioni());
						}
						else if(scheda_a_val.valida(scheda_a,IdentificativoSchede.TAB_INFO_COMUNI)){
							infoComuniBean.setIdLotto(infoGara.getIdLotto());
							infoComuniBean.setCigCycle(infoGara.getCigCicle());
							infoComuniBean.setCig(infoGara.getCig());						

							infoComuniBean.setCfAmministrazione(infoGara.getCfAmministrazione());
							infoComuniBean.setDenAmministrazione(infoGara.getDenomAmministrazione());
							
							//TICKET ALM #659 - 3.04.3
							//Se la gara e' successiva la creazione della 3.04.3 ed e' accordo quadro/convenzione, recupera la durata dalla gara
							if(SimogProperties.getInstance().isDataCreatedAfter3043(infoGara.getDataCreazioneGara()) &&
								!SimogProperties.getInstance().isDataCreatedAfter3046(infoGara.getDataCreazioneGara()) &&
								SimogFlags.isAccordoQuadroOrConvenzione(infoGara.getID_MODO_REAL())) {
								GaraManager gm = new GaraManager(currentActiveConnection,logger);
								Gara g = gm.getGara(infoGara.getIdGara());
								infoComuniBean.setDurataConvenzione(g.getDurataGiorni());
							}
							//FINE TICKET ALM #659 - 3.04.3
							
							//scheda_a_val.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_009);
							boolean inserimento = infoComuniBean.getIdInfo() <1;
							iAction.save(infoComuniBean, currentUser.getLogin());
							
							// aggiornamento dei campi gara e lotto
							aggMan.updateGaraLotto(currentActiveConnection, logger, infoComuniBean);
							
							commit(currentActiveConnection);
							if(inserimento){
								scheda_a_val.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_023);
								infoGara.setIdInfo(infoComuniBean.getIdInfo());
								infoGara.setDataInizioInfo(infoComuniBean.getDataInizioInfo());
								infoGara.setCfRup(currentUser.getLogin());
								
								//PP tipo scheda e tipo contrattto
								infoGara.setTipoContratto(infoComuniBean.getTipoContratto());
								infoGara.setTipoEnte(infoComuniBean.getFlagEnteSpeciale());
							}
							else scheda_a_val.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_009);
							infoComuniBean = iAction.load(infoGara.getIdInfo(), infoGara.getDataInizioInfo());
						}
						
					}
					else if(PSBD.ACTION_CONFERMA.equalsIgnoreCase(action)){
						infoComuniBean = iAction.getBean(request);
						infoComuniBean.setIdLotto(infoGara.getIdLotto());
						infoComuniBean.setHasAwards(hasAwards);
						scheda_a.setInfoComuni(infoComuniBean);
						scheda_a.setInfoGara(infoGara);

						scheda_a_val.valida(scheda_a,IdentificativoSchede.TAB_INFO_COMUNI);
						if(scheda_a_val.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0){

							//TICKET ALM #659 - 3.04.3
							//Se la gara e' successiva la creazione della 3.04.3 ed e' accordo quadro/convenzione, recupera la durata dalla gara
							if(SimogProperties.getInstance().isDataCreatedAfter3043(infoGara.getDataCreazioneGara()) &&
								!SimogProperties.getInstance().isDataCreatedAfter3046(infoGara.getDataCreazioneGara()) &&
								SimogFlags.isAccordoQuadroOrConvenzione(infoGara.getID_MODO_REAL())) {
								GaraManager gm = new GaraManager(currentActiveConnection,logger);
								Gara g = gm.getGara(infoGara.getIdGara());
								infoComuniBean.setDurataConvenzione(g.getDurataGiorni());
							}
							//FINE TICKET ALM #659 - 3.04.3
							
							iAction.confirm(infoComuniBean, currentUser.getLogin());	

							// aggiornamento dei campi gara e lotto
							aggMan.updateGaraLotto(currentActiveConnection, logger, infoComuniBean);

							commit(currentActiveConnection);
							scheda_a_val.getEccezioni().addValidationInfo(Messaggi.SIMOG_AGGIUDICAZIONI_012);
							infoComuniBean = iAction.load(infoGara.getIdInfo(), infoGara.getDataInizioInfo());
						}
					}
					else if(PSBD.ACTION_CARICA_JSP_ANNULLAMENTO.equalsIgnoreCase(action)){

						infoComuniBean = iAction.load(infoGara.getIdInfo(), infoGara.getDataInizioInfo());
						boolean isOk = (iAction.bsa.hasSchedaRichDelete(String.valueOf(infoGara.getIdInfo()), IdentificativoSchede.TAB_INFO_COMUNI, false).getFullSize()==0);
						
						if (isOk){
							request.setAttribute(PSBD.TAB, IdentificativoSchede.TAB_INFO_COMUNI);
							String dest = ParametriServlet.JSP_RICHIEDI_ANNULLAMENTO + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + infoGara.getIdLotto();
							dest+="&" + ParametriServlet.FIELD_NAME_ID_INFO + "=" + infoGara.getIdInfo();
							dest+="&" + ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO + "=" + infoGara.getDataInizioInfo();
							dest+="&" + ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE + "=" + infoComuniBean.getPubblicazione().getIdPubblicazione();
							dest+="&" + ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB + "=" + infoComuniBean.getPubblicazione().getDataInizioPubblicazione();
						
							forward(dest, request, response);
							return;
						}
						else{
							scheda_a_val.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "modifica"));
						}
					}
					else if(PSBD.ACTION_CARICA_JSP_CANCELLAZIONE.equalsIgnoreCase(action)){
						
						infoComuniBean = iAction.load(infoGara.getIdInfo(), infoGara.getDataInizioInfo());
						boolean isOk = (iAction.bsa.hasSchedaRichDelete(String.valueOf(infoGara.getIdInfo()), IdentificativoSchede.TAB_INFO_COMUNI, true).getFullSize()==0);
						
						if (isOk){
							request.setAttribute(PSBD.TAB, IdentificativoSchede.TAB_INFO_COMUNI);
							String dest = ParametriServlet.JSP_RICHIEDI_CANCELLAZIONE + "?" + ParametriServlet.FIELD_NAME_ID_LOTTO + "=" + infoGara.getIdLotto();
							dest+="&" + ParametriServlet.FIELD_NAME_ID_INFO + "=" + infoGara.getIdInfo();
							dest+="&" + ParametriServlet.FIELD_NAME_DATA_INIZIO_INFO + "=" + infoGara.getDataInizioInfo();
							dest+="&" + ParametriServlet.FIELD_NAME_ID_PUBBLICAZIONE + "=" + infoComuniBean.getPubblicazione().getIdPubblicazione();
							dest+="&" + ParametriServlet.FIELD_NAME_DATA_INIZIO_PUB + "=" + infoComuniBean.getPubblicazione().getDataInizioPubblicazione();
							
							forward(dest, request, response);
							return;
						}
						else{
							scheda_a_val.getEccezioni().addValidationErr(Messaggi.SIMOG_VALIDAZIONE_191.replace("$1", "cancellazione"));
						}
					}
					else if(PSBD.ACTION_RICHIESTA_ANNULLAMENTO.equalsIgnoreCase(action)){
						infoComuniBean = iAction.load(infoGara.getIdInfo(), infoGara.getDataInizioInfo());
						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_ANNULLAMENTO);
						String idLotto = Long.toString(infoGara.getIdLotto());
						
						RichiestaAnnullamento raBean = new RichiestaAnnullamento();
						raBean.setId_lotto(idLotto);
						raBean.setMotivo_richiesta(motivazione);
	                    raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						raBean.setRichiedente(currentUser.getLogin());
						boolean successo = false;
						raBean.setId_pub(Long.toString(infoComuniBean.getPubblicazione().getIdPubblicazione()));
						raBean.setData_inizio_pub(infoComuniBean.getPubblicazione().getDataInizioPubblicazione());
						
						raBean.setId_info(Long.toString(infoComuniBean.getIdInfo()));
						raBean.setData_inizio_info(infoComuniBean.getDataInizioInfo());
						raBean.setBlocco(IdentificativoSchede.TAB_INFO_COMUNI);
						raBean.setId_record(Long.toString(infoComuniBean.getIdInfo()));
						raBean.setData_inizio_record(infoComuniBean.getDataInizioInfo());

						Timestamp nuovadata = null;
						
						nuovadata = iAction.richiediAnnullamento(raBean);
						
						successo = nuovadata != null;	
						
                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(raBean.getBlocco(), currentActiveConnection, logger);
                           
                           raBean.setDecisore(currentUser.getLogin());
                           raBean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
                           raBean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
                           
                           successo = annAction.gestisciRichiesta(raBean,currentUser.getLogin());
                        
                           if(successo){
                              infoGara.setDataInizioInfo(nuovadata);
                              scheda_a_val.getEccezioni().addValidationInfo(Messaggi.SIMOG_RICHIESTA_ANNULLAMENTO_002);
                              commit(currentActiveConnection);
                          }
                          else{
                              rollback(currentActiveConnection);
                              scheda_a_val.getEccezioni().addValidationErr(SIMOG_RICHIESTA_ANNULLAMENTO_001);
                          }
 
                         //TICKET ALM #13519 - 3.04.4.1
   						if(SimogProperties.getInstance().isDataCreatedAfter3044(infoGara.getDataCreazioneGara())) {
   							GaraManager gm = new GaraManager(currentActiveConnection, logger);
   			                Gara gara = gm.getGara(infoGara.getIdGara());
   			                if(Costanti.FLAG_VALORE_SI.equals(gara.getFlagSAAgente())) {
   			                	List<String> datiStorici = gm.getDatiStoriciGaraDelegata(infoGara.getIdGara());
   			                	if(!datiStorici.isEmpty() && !currentUser.getLogin().equals(datiStorici.get(0)))
   			                		request.setAttribute("readonlyDelegante", "readonly");
   			                }
   						}
                        infoComuniBean = iAction.load(infoGara.getIdInfo(), infoGara.getDataInizioInfo());
						
					}
					
					else if(PSBD.ACTION_RICHIESTA_CANCELLAZIONE.equalsIgnoreCase(action)){
						infoComuniBean = iAction.load(infoGara.getIdInfo(), infoGara.getDataInizioInfo());
						String motivazione = request.getParameter(PSBD.MOTIVAZIONE_CANCELLAZIONE);
						String idLotto = Long.toString(infoGara.getIdLotto());
						
						RichiestaAnnullamento raBean = new RichiestaAnnullamento();
						raBean.setCancellazione(Costanti.FLAG_VALORE_NO) ;
						raBean.setId_lotto(idLotto);
						raBean.setMotivo_richiesta(motivazione);
						raBean.setIdMotivo(request.getParameter(PSBD.FIELD_NAME_MOTIVO_RICH)); // PP 3.02.3
						raBean.setRichiedente(currentUser.getLogin());
						raBean.setId_pub(Long.toString(infoComuniBean.getPubblicazione().getIdPubblicazione()));
						raBean.setData_inizio_pub(infoComuniBean.getPubblicazione().getDataInizioPubblicazione());
						raBean.setId_info(Long.toString(infoComuniBean.getIdInfo()));
						raBean.setData_inizio_info(infoComuniBean.getDataInizioInfo());
						raBean.setBlocco(IdentificativoSchede.TAB_INFO_COMUNI);
						raBean.setId_record(Long.toString(infoComuniBean.getIdInfo()));
						raBean.setData_inizio_record(infoComuniBean.getDataInizioInfo());
						
						iAction.richiediCancellazione(raBean);
						
	                      // 3.02.2.1 accettazione immediata richiesta
                           boolean successo;

                           BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(raBean.getBlocco(), currentActiveConnection, logger);
                            
                           raBean.setDecisore(currentUser.getLogin());
                           raBean.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
                           raBean.setMotivo_esito(RichiestaAnnullamento.MOTIVO_ESITO_DIRETTO);
                           
                           successo = annAction.gestisciRichiestaCancellazione(raBean,currentUser.getLogin());
                    
                           if(successo){
                               scheda_a_val.getEccezioni().addValidationInfo(Messaggi.SIMOG_RICHIESTA_CANCELLAZIONE_002);
                               commit(currentActiveConnection);
                               
                               // forward a lista aggiudicazioni
                               forwPage = ParametriServlet.SRV_GESTIONE_SCHEDE;
                               String fromRicerca = (String) request.getAttribute(ParametriServlet.FROM_RICERCA);
                               if(request.getSession().getAttribute(ParametriServlet.STORIA_PAGINAZIONE) != null 
                                     && !"".equals(request.getSession().getAttribute(ParametriServlet.STORIA_PAGINAZIONE))){
                                  forwPage += "?" + request.getSession().getAttribute(ParametriServlet.STORIA_PAGINAZIONE);
                                 }
                              else {
                                  if(fromRicerca != null) forwPage += "?"+ParametriServlet.FROM_RICERCA+"="+fromRicerca;
                              }
                           }
                           else{
                               rollback(currentActiveConnection);
                               scheda_a_val.getEccezioni().addValidationErr(SIMOG_RICHIESTA_CANCELLAZIONE_001);
                           }
  
						
						infoComuniBean = iAction.load(infoGara.getIdInfo(), infoGara.getDataInizioInfo());
					}
					
					// pre carico i campi da gara e lotto solo la prima volta
					if(infoComuniBean.getFLAG_ESCLUSO() == null){
						infoComuniBean.setFlagEnteSpeciale(infoGara.getTIPO_SCHEDA_GARA());
						infoComuniBean.setID_MODO_REAL(infoGara.getID_MODO_REAL());
						
						/// bah String categ = (String) request.getAttribute("categoriaPrevalente");
						
						
						// PP infoComuniBean.setTipoContratto("FB".equals(categ) ? Costanti.TIPO_SCHEDA_FORNITURE : "FS".equals(categ) 
						// PP													? Costanti.TIPO_SCHEDA_SERVIZI : Costanti.TIPO_SCHEDA_LAVORI);
						// PP il tipo contratto viene da Gara
						infoComuniBean.setTipoContratto(infoGara.getTipoContratto());
						
						infoComuniBean.setFLAG_ESCLUSO(infoGara.getFLAG_ESCLUSO());
						infoComuniBean.setID_ESCLUSIONE(infoGara.getID_ESCLUSIONE());		
						
						// preimposto i dati pubblicazione se esiste la pubblicazione bando
						if(infoGara.getIdPubblicazione() != 0)
							iAction.loadPubbFromBando(infoGara.getIdPubblicazione(), infoGara.getDataInizioPubblicazione(), infoComuniBean);
					}
					
					// abilitazione pulsante richiesta cancellazione
					if (infoComuniBean != null ) 			
						infoComuniBean.setOkCancellazione(iAction.bsa.isCancellabile(IdentificativoSchede.TAB_INFO_COMUNI, 
																				 infoComuniBean.getIdInfo(), 
																				 infoComuniBean.getDataInizioInfo(),
																				 infoComuniBean.getIdStato(),
																				 infoComuniBean.getFlagEnteSpeciale(), 
																				 infoComuniBean.getTipoContratto(),
																				 infoComuniBean.getIdInfo(), // solo per dati comuni si usa idinfo
																				 infoComuniBean.getDataInizioInfo()));
					
					// messaggio nessuna aggiudicazione presente
					if(!infoComuniBean.isHasAwards()){
						scheda_a_val.getEccezioni().addValidationInfo(SIMOG_AGGIUDICAZIONI_007);

					}
					
					/***************** DETTAGLI RICH ANN ************************/
					
					TableBean tabRichAnn = iAction.bsa.getRichAnnByScheda(
							String.valueOf(infoComuniBean.getIdInfo()), 
							IdentificativoSchede.TAB_INFO_COMUNI, false);
					
					request.setAttribute(PSBD.TAB_RICHANN, tabRichAnn);
					
					/***************** END              *************************/
					
					//Verifica, per ciascuna aggiudicazione, la non presenza di schede oltre a quella di aggiudicazione
					//FIXME: UN se cambiano le specifiche di flusso (WorkFlowController) va cambiato se necessario
					boolean onlyAward = true;
					for(AggiudicazioneBean Abean: listaAggiudicazioni){
						boolean existInizio = aggMan.isEmptyRs(INIZIO_LAVORI.TABLE_NAME,INIZIO_LAVORI.ID_INIZIO, Abean.getIdAggiudicazione(), Abean.getDataInizioAggiudicazione(), AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE).getState() > 0
											|| aggMan.isEmptyRs(STIPULA.TABLE_NAME,STIPULA.ID_STIPULA, Abean.getIdAggiudicazione(), Abean.getDataInizioAggiudicazione(), AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE).getState() > 0;

						boolean existFine = aggMan.isEmptyRs(FINE_LAVORI.TABLE_NAME,FINE_LAVORI.ID_ULTIM,  Abean.getIdAggiudicazione(), Abean.getDataInizioAggiudicazione(), AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE).getState() > 0;
						boolean existVariante = aggMan.isEmptyRs(VARIANTI.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE, Abean.getIdAggiudicazione(), Abean.getDataInizioAggiudicazione(), AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE).getState() > 0;
						boolean existSubAppalti = aggMan.isEmptyRs(SUBAPPALTI.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE,  Abean.getIdAggiudicazione(), Abean.getDataInizioAggiudicazione(), AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE).getState() > 0;
						boolean existRitardo = aggMan.isEmptyRs(R129.TABLE_NAME,AGGIUDICAZIONI.ID_AGGIUDICAZIONE,  Abean.getIdAggiudicazione(), Abean.getDataInizioAggiudicazione(), AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE).getState() > 0;	
						onlyAward = onlyAward && !(existInizio || existFine || existVariante ||  existSubAppalti || existRitardo);
					}
					infoComuniBean.setOnlyAwards(onlyAward);
					//logger.debug("----- onlyAwards -------> "+onlyAward);
					
					logger.debug(ObjectIntrospector.propertiesInfo(VO.class, infoComuniBean));
					//gm nuovo controllo 3.02 per nascondere gli errori di validazione su schede confermate
					if(infoComuniBean != null && infoComuniBean.getIdStato() == StatiScheda.CONFERMATO)
						iAction.sendValidations(request, scheda_a_val.getEccezioni().getAllInfoEWarn());
					else
						iAction.sendValidations(request, scheda_a_val.getEccezioni());
			
					//aggiungo la prevalorizzazione nel caso di primo caricamento
//PP non richiesto, tolto
//					if(infoComuniBean.getIdInfo() == 0){
//						infoComuniBean.setEsitoProcedura(EsitoEnum.AGGIUDICATA.codice());
//					}
					request.setAttribute("dati_comuni", infoComuniBean);
					request.setAttribute("listaTipiEnte", iAction.getListaTipiEnte(dataInizioScheda));
					request.setAttribute("listaCategorieSA", iAction.getCategorieSA(dataInizioScheda));
					request.setAttribute("listaTipologieSA", iAction.getTipologieSA(dataInizioScheda));
					//gm nuovo codice dati comuni
					request.setAttribute("listaTipologieProcedura", iAction.getTipologieProcedura(dataInizioScheda));			
					
					/*int[] idExc = new int[0];
					if(dataScadenzaPag==null)
						idExc = new int[] {Costanti.AGGIUDICATA,Costanti.PROPOSTA_AGGIUDICAZIONE};
					else if(idfDelegate==Costanti.PROPOSTA_AGGIUDICAZIONE)
						idExc = new int[] {Costanti.AGGIUDICATA};*/
					java.util.List<Lotto> lottoListCig = lman.getLottoByCigWS(infoGara.getFullCIG());
					if (lottoListCig == null || lottoListCig.isEmpty()) {
						sendError(request, response, Messaggi.SIMOG_VALIDAZIONE_000, ParametriServlet.JSP_ERRORE);
						return;
					}
					Lotto lottoBean = lottoListCig.get(0);
					Gara gara = iAction.getGara(infoGara.getIdGara());
					int idfdelega = gara.getCF_AMM_AGENTE() != null && 
									!gara.getCF_AMMINISTRAZIONE().equals(gara.getCF_AMM_AGENTE()) &&
									(infoComuniBean == null || infoComuniBean.getIdInfo()==0 || (infoComuniBean != null && !infoComuniBean.getCfAmministrazione().equals(gara.getCF_AMM_AGENTE())))
									? gara.getID_F_DELEGATE() : 0;
					
					Map<String,String> listaEsiti = EsitoEnum.loadEsitiProcedura(gara.getData_creazione(),lottoBean.getDATA_SCADENZA_PAGAMENTI(),idfdelega);
					if(infoComuniBean != null && EsitoEnum.PROPOSTA_AGGIUDICAZIONE.codice().equals(infoComuniBean.getEsitoProcedura()) 
							&& infoComuniBean.getIdStato()==StatiScheda.CONFERMATO)
						listaEsiti.put(EsitoEnum.PROPOSTA_AGGIUDICAZIONE.codice(), EsitoEnum.PROPOSTA_AGGIUDICAZIONE.descrizione());
					
					request.setAttribute("listaEsitiProcedura", listaEsiti);
					
					//TICKET ALM - 3.04.2 NG #2847
					//Gara gara = iAction.getGara(infoGara.getIdGara());
					   request.setAttribute("listaModiReal", iAction.getListaModiReal(gara.getData_creazione()));
					//FINE TICKET ALM - 3.04.2 NG #2847
								
					//TICKET ALM - 3.04.2 NG #2847
						request.setAttribute("listaArtEsclusione", iAction.getListaArtEsclusione(gara.getData_creazione(), gara.isOrganoCost()));
					//FINE TICKET ALM - 3.04.2 NG #2847
					
					setDatiGara(infoGara, request.getSession());
					
                    // imposto i valori per il tasto nuova aggiudicazione, dipende dal flusso
                    if(infoComuniBean != null && infoComuniBean.getIdStato() == StatiScheda.CONFERMATO){
                        InfoGaraBean igb = new InfoGaraBean();
                        igb.setID_MODO_REAL(infoComuniBean.getID_MODO_REAL());
                        igb.setFLAG_ESCLUSO(infoComuniBean.getFLAG_ESCLUSO());
                        igb.setImportoLotto(infoGara.getImportoLotto());
                        igb.setDataPubblicazione(infoGara.getDataPubblicazione());
                        igb.setTipoContratto(infoComuniBean.getTipoContratto());
                        igb.setTipoEnte(infoGara.getTipoEnte());
                        
                        TipoFlusso retFlusso;
                           SimogFlusso flusso = new SimogFlusso( igb.getTipoEnte(),  
                        		                                 igb.getTipoContratto(),  
                        		                                 igb.getFLAG_ESCLUSO(),  
                                                                 igb.getID_MODO_REAL(),  
                                                                 igb.getDataPubblicazione(),   
                                                                 igb.getImportoLotto().floatValue(),
                                                                 Costanti.FLAG_VALORE_SI.equals(gara.getFlagSAAgente()) && 
                                                                      !gara.getCF_AMM_AGENTE().equals(infoGara.getCfAmministrazione()) ? gara.getID_F_DELEGATE() : 0,
                                                                 gara.getData_creazione(),
                                                                 gara.getID_SVOLGIMENTO()
                                                                 );
                           retFlusso = flusso.getTipoFlusso();
                        
                        if(retFlusso != null && 
                           PSBD.ACTION_CONFERMA.equalsIgnoreCase(action) && 
                           !hasAwards &&
                           infoComuniBean != null && infoComuniBean.getEsitoProcedura().equals(String.valueOf(Costanti.AGGIUDICATA))
                          ){
                           if (retFlusso.equals(TipoFlusso.ADESIONE)){
//                               request.setAttribute(ParametriServlet.NEXT_PAGE, ParametriServlet.SRV_SCHEDA_ADESIONE);
//                               request.setAttribute(ParametriServlet.DESCPREF, "Aggiungi una nuova Adesione");
                        	   forwPage=ParametriServlet.SRV_SCHEDA_ADESIONE;
                           }
                           if (retFlusso.equals(TipoFlusso.ESCLUSO)){
                        	   //TICKET ALM - 3.04.3 #7303
                        	   //Se la gara e' stata pubblicata successivamente, reindirizza sull'aggiudicazione ordinaria
                        	   if(!SimogFlags.is3043Active() || !SimogProperties.getInstance().isDataCreatedAfter3043(infoGara.getDataCreazioneGara())) {
//	                               request.setAttribute(ParametriServlet.NEXT_PAGE, ParametriServlet.SRV_SCHEDA_ESCLUSI);
//	                               request.setAttribute(ParametriServlet.DESCPREF, "Aggiungi una nuova Aggiudicazione (contratti esclusi)");
                        		   forwPage=ParametriServlet.SRV_SCHEDA_ESCLUSI;
                        	   } else {
//                        		   request.setAttribute(ParametriServlet.NEXT_PAGE, ParametriServlet.SRV_SCHEDA_A);
//                                   request.setAttribute(ParametriServlet.DESCPREF, "Aggiungi una nuova Aggiudicazione");
                        		   forwPage=ParametriServlet.SRV_SCHEDA_A;
                        	   }//FINE TICKET ALM - 3.04.3 #7303
                           }
                           if (retFlusso.equals(TipoFlusso.SOTTOSOGLIA)){
//                               request.setAttribute(ParametriServlet.NEXT_PAGE, ParametriServlet.SRV_SCHEDA_SOTTOSOGLIA);
//                               request.setAttribute(ParametriServlet.DESCPREF, "Aggiungi una nuova Aggiudicazione (sottosoglia)");
                        	   forwPage=ParametriServlet.SRV_SCHEDA_SOTTOSOGLIA;
                           }
                           if (retFlusso.equals(TipoFlusso.STIPULA)){
//                               request.setAttribute(ParametriServlet.NEXT_PAGE, ParametriServlet.SRV_SCHEDA_A);
//                               request.setAttribute(ParametriServlet.DESCPREF, "Aggiungi una nuova Aggiudicazione (accordo quadro)");
                        	   forwPage=ParametriServlet.SRV_SCHEDA_A;
                           }
                           if (retFlusso.equals(TipoFlusso.AGGIUDICAZIONE)){
//                               request.setAttribute(ParametriServlet.NEXT_PAGE, ParametriServlet.SRV_SCHEDA_A);
//                               request.setAttribute(ParametriServlet.DESCPREF, "Aggiungi una nuova Aggiudicazione");
                               forwPage=ParametriServlet.SRV_SCHEDA_A;
                           }
                        }
                    }

					forward(forwPage, request, response);
					return;
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
			}else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				return;
			}
		}else{
			 sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
				return;
			 }
	}
	
// PP spostata in Accessi Db per condivisione con il massloader
//	private void updateGaraLotto(Connection conn, Logger logger, InfoComuniBean bean) throws Exception{
//		GaraManager gm = new GaraManager(conn, logger);
//		LottoManager lm = new LottoManager(conn, logger);
//		
//			TableBean lotto = lm.getLottoByIdLottoAmm(bean.getIdLotto());
//			
//			gm.updateCampiInfoComuni(bean, Long.parseLong(lotto.getNulledField(LOTTO.ID_GARA, 0)));
//
//			lm.updateCampiInfoComuni(bean);		
//	}
	
}
