package it.avlp.simog.garamanager.app;

import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avlp.simog.actions.DelegaDatiSimogAction;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.ErrorBean;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoRettificaBean;
import it.avlp.simog.beans.MotivazioniBean;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.StazioneAppaltante;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletLotto;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.log.LogManager;
import it.avlp.simog.rettifica.InfoRettifica;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avlp.simog.beans.PubblicazioneBean;


public class SrvInizializzaGara extends ServletBase implements ParametriServlet {

	private static final long serialVersionUID = 1L;

	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {

      Connection currentActiveConnection = null;
      HttpSession currentActiveSession = request.getSession();
      Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
      if ( checkSession(request) ) {
         if (  ! currentUser.isAVLP()  ) {
            currentActiveSession.setAttribute(SESSION_NUMERO_LOTTI_CREATI, new Integer(0));
            currentActiveSession.setAttribute(SESSION_ID_GARA, null);            
            
            try {
               currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
               currentActiveConnection.setAutoCommit(false);
               
               setTabelleUtilita(request, currentActiveConnection, PageHelper.getCurrentDate(), false, null);
               
               
               Object messageBean = request.getAttribute(ParametriServlet.ERRORBEAN);
               if ( messageBean != null ) {
                  request.setAttribute(ERRORBEAN, messageBean);
               }
               
               // UN Caricare le informazioni sulla gara da modificare. Se idGara è null gara da creare
               String idGara = request.getParameter(ParametriServlet.SESSION_ID_GARA);
               if(idGara == null) 
                  idGara = request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA);
               
               GaraManager gm = new GaraManager(currentActiveConnection, logger); 
               
               DelegaDatiSimogAction dasAction = new DelegaDatiSimogAction(currentActiveConnection, logger);
               Gara gara = null;
               String res = null;
               
               if (idGara != null ) {              
                  gara = gm.getGara(Long.valueOf(idGara), currentUser.getUffici());
                  if(gara == null){
                     sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
                     return;
                  }

                  //Ticket #20055
				   if(currentUser.isRUP()) {
					  InfoRettifica infoRettifica = new InfoRettifica();
					  boolean result = infoRettifica.checkRettifica(gm,idGara);
					  if(result) {
						  request.setAttribute(ParametriServlet.RETTIFICA_GARA_LOTTI, result);
					  }
                  } else
                	  request.setAttribute(ParametriServlet.RETTIFICA_GARA_LOTTI, false);
                  ///---

                  //TICKET ALM - 3.04.2 NG
                  if(SimogFlags.is3042Active() && gara!=null)
                	     setTabelleUtilita(request, currentActiveConnection, gara.getData_creazione(), false, null);
                  else
                		 setTabelleUtilita(request, currentActiveConnection, PageHelper.getCurrentDate(), false, null);
                  //FINE TICKET ALM - 3.04.2 NG
                  
                  request.setAttribute(ParametriServlet.FIELD_NAME_ID_GARA, gara.getId_Gara());
                  request.setAttribute(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE, gara.getID_STAZIONE_APPALTANTE());
                  request.setAttribute(ParametriServlet.FIELD_NAME_OGGETTO_GARA, gara.getOggetto());
                  
                  request.setAttribute(ParametriServlet.FIELD_NAME_IMPORTO_GARA_EURO, 
                        gara.getIMPORTO_GARA() != null ? gara.getIMPORTO_GARA().toString() : null);

                  //gm nuovo campo simog 3.04
                  request.setAttribute(ParametriServlet.FIELD_NAME_NUMERO_LOTTI, String.valueOf(gara.getNumeroLotti()));

                  // TICKET ALM - 3.04.3 #659
                  request.setAttribute(ParametriServlet.FIELD_NAME_DURATA_GIORNI, String.valueOf(gara.getDurataGiorni()));

                   //TICKET ALM #659 - 3.04.4
					request.setAttribute(ParametriServlet.FIELD_NAME_FLAG_SA_AGENTE_GARA, gara.getFlagSAAgente());
					request.setAttribute(ParametriServlet.FIELD_NAME_ID_F_DELEGATE, String.valueOf(gara.getID_F_DELEGATE()));
					request.setAttribute(ParametriServlet.FIELD_NAME_CF_AMM_AGENTE, gara.getCF_AMM_AGENTE());
					request.setAttribute(ParametriServlet.FIELD_NAME_DEN_AMM_AGENTE, gara.getDEN_AMM_AGENTE());
					//FINE TICKET ALM #659 - 3.04.4
                  
                  request.setAttribute(ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE, String.valueOf(gara.getID_MODO_REAL()));
                  
                  //TICKET ALM #664
                  request.setAttribute(ParametriServlet.FIELD_NAME_STRUMENTO_SVOLGIMENTO, String.valueOf(gara.getID_SVOLGIMENTO()));
                  //FINE TICKET ALM #664
                  
                  //TICKET ALM #3832
                  request.setAttribute(ParametriServlet.FIELD_NAME_ESTREMA_URGENZA, String.valueOf(gara.getID_ESTREMA_URGENZA()));
                  //FINE TICKET ALM #3832
                  
                  //TICKET ALM #3834
                  request.setAttribute(ParametriServlet.FIELD_NAME_ALLEGATO_IX, String.valueOf(gara.getID_ALLEGATO_IX()));
                  //FINE TICKET ALM #3834
                  
                  request.setAttribute(PSBD.FIELD_NAME_ID_MODO_INDIZIONE, String.valueOf(gara.getID_MODO_GARA()));
                  request.setAttribute(ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE, gara.getTIPO_SCHEDA_GARA());
                  request.setAttribute(ParametriServlet.FIELD_NAME_ID_STATO_GARA, String.valueOf(gara.getID_STATO_GARA()));
                  request.setAttribute(ParametriServlet.FIELD_NAME_DATA_CONFERMA_GARA, String.valueOf(gara.getDATA_CONFERMA_GARA()));
                  request.setAttribute(GARA.DATA_COMUN, gara.getDATA_COMUN());
                  request.setAttribute(ParametriServlet.FIELD_NAME_CIG_ACC_QUADRO, gara.getCIG_ACC_QUADRO());
                  request.setAttribute(ParametriServlet.FIELD_NAME_DATA_CREAZIONE_GARA, gara.getData_creazione());
                  
                  // is3031_ESCL_AVCPASS
                  request.setAttribute(ParametriServlet.FIELD_FLAG_ESCLUSO_AVCPASS, gara.getESCLUSO_AVCPASS());
                  
                  if (SimogFlags.isINT85_RFWEBGL01Active()){
                     request.setAttribute(ParametriServlet.FIELD_NAME_SCELTA_LEGGE85, String.valueOf(gara.getSCELTA_LEGGE89()));
                     request.setAttribute(ParametriServlet.FIELD_NAME_TIPOSA_BDNCP, gara.getTIPOSA_BDNCP());
                  }
                  
                  request.setAttribute(ParametriServlet.ACTION, request.getParameter("toDo"));

                  // INT87
                  if(SimogFlags.isINT87_RFSIMOGWEB01Active() 
                     // PP ho esagerato! && configuration.isINT87Attivo(gara.getData_creazione() == null ? PageHelper.getCurrentDate(): gara.getData_creazione())
                     )
                     request.setAttribute(ParametriServlet.FIELD_NAME_URGENZA_DL133, gara.getURGENZA_DL133());
                  
                  // is30350_RFWEBGL01Active
                  if(SimogFlags.is30350_RFWEBGL01Active()){
                     request.setAttribute(ParametriServlet.FIELD_NAME_EAGG_COD_MOTIVO, String.valueOf(gara.getCOD_MOTIVO_EAGG()));
                     request.setAttribute(ParametriServlet.EAGG_CATEGSEL_BEAN, gara.getCatMercArray());
                  }
                  

                  LottoManager lm = new LottoManager(currentActiveConnection,logger);
                  String listaCodSel = lm.selectCodCatMercGara(Long.parseLong(idGara));
                  request.setAttribute(ParametriServlet.EAGG_CATLOTTO, listaCodSel);
                  
               }

               request.getSession().removeAttribute(ParametriServlet.STORIA_PAGINAZIONE);

               // PP informazioni per controllare la delega CIG
               List<String> deleghe = new ArrayList<String>();
               for ( java.util.Enumeration e = currentUser.getUfficiByProfilo(currentUser.getProfiloEnum()).elements(); e.hasMoreElements();){
            
                  StazioneAppaltante sa = (StazioneAppaltante)e.nextElement();
                  
                  String oss = sa.getAmministrazione().getId_osservatorio();
                  deleghe.add(dasAction.getDelegaCIGMessage(oss, PageHelper.getCurrentUtilDate(), currentUser));
               }              
               request.setAttribute(ParametriServlet.DELEGHE_CIG,deleghe);
               
               String azione = request.getParameter(ParametriServlet.ACTION);
               
              if( SimogFlags.is3030_RFWEBGL02Active() ){
                 AVCPassAction avpa = new AVCPassAction(currentActiveConnection, logger, configuration); 
                 String targetPage = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara
                       + "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;   
                 
                 String codiceAVCPassAction = "cancella".equals(azione) ? AVCPassFunzioneEnum.WEB_GARA_DELETE.getCodice()
                                            : "ripristina".equals(azione) ? AVCPassFunzioneEnum.WEB_GARA_RIPRISTINA.getCodice()
                                            : AVCPassFunzioneEnum.WEB_GARA_UPDATE.getCodice();
                 
                 if( avpa.isAVCPass(gara, null, codiceAVCPassAction) ){
                    AllValidationBeans msgs = new AllValidationBeans();
                    msgs.addValidationErr(SIMOG_AVCPASS_001);
                    sendValidations(request, response, msgs, targetPage);
                    return;
                 }
              }         

              // INT85 imposto il flag per la visualizzazione della sezione
              request.setAttribute(ParametriServlet.FIELD_NAME_FLAG_LEGGE85, Costanti.FLAG_VALORE_NO);
              if(SimogFlags.isINT85_RFWEBGL01Active() 
                    && configuration.isINT85Attivo()){

                 String param = (String) (request.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE) != null 
                    ? request.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE)
                    : request.getAttribute(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE));
                    
                 if((param != null) && currentUser.isRSSAorRUP()
                       && (gara == null || configuration.isSAINT85(gara.getData_creazione()))){
                    
                    StazioneAppaltante sa = (StazioneAppaltante) currentUser.getUfficiByProfilo(ProfiloEnum.RUP).get(param);
                    if(sa != null && Costanti.COD_SA_COMUNE.equals(sa.getAmministrazione().getTipoSA()))
                       request.setAttribute(ParametriServlet.FIELD_NAME_FLAG_LEGGE85, Costanti.FLAG_VALORE_SI);
                 }                 
              }
              
              // is30350_RFWEBGL01Active visibilità sezione
              if(SimogFlags.is30350_RFWEBGL01Active() && (configuration.isEAGGAttivo(gara == null ? PageHelper.getCurrentDate() : gara.getData_creazione()))){
                 request.setAttribute(ParametriServlet.FIELD_NAME_FLAG_EAGG, Costanti.FLAG_VALORE_SI);

                 String[] lista = gara == null ? null : gara.getCatMercArray();

                 if(lista == null)
                    lista = new String[0];
                 
                 // dinamicamente decido se mostrare o meno la sezione dei comuni poiché questa sezione ha la priorità
                 if(Costanti.FLAG_VALORE_SI.equals(request.getAttribute(ParametriServlet.FIELD_NAME_FLAG_LEGGE85))){
                    // la sezione non va mostrata se sono selezionate categorie oltre la categoria 999
                    if(lista.length > 1 || lista.length == 0 
                          || (lista.length == 1 && !Costanti.EAGG_CATMERC_999.equals(lista[0]))){
                       request.setAttribute(ParametriServlet.FIELD_NAME_FLAG_LEGGE85, Costanti.FLAG_VALORE_NO);
                       // azzero anche il campo 
                       request.setAttribute(ParametriServlet.FIELD_NAME_SCELTA_LEGGE85, null);
                    }
                 }
              }

              
              if ("cancella".equals(azione)){
/** PP solo info
                  if(gara  != null) {
                     res = dasAction.getDelegaCIGMessage(gara.getID_OSSERVATORIO(), PageHelper.getCurrentUtilDate(), currentUser);
                  }
**/                  
                  // preparo i dati per la visualizzazione
                  TableBean dati = gm.getDettagliGaraByIdGara(idGara);
                  request.setAttribute(TABLEBEAN,dati);
                  
                  MotivazioniBean motiviCanc = new MotivazioniBean();
                  motiviCanc.loadAll(currentActiveConnection, logger, true);

                  request.setAttribute(ParametriServletLotto.MOTIVAZIONI_LIST, motiviCanc.loadMotivazioni());
                  request.setAttribute(ParametriServlet.ACTION, azione);
                  
                  // fix 34470 3.04.8
//                  PubblicazioneManager pubManager = new PubblicazioneManager(currentActiveConnection, logger);
//                  PubblicazioneBean pubblicazione = pubManager.getPubblicazione(gara.getIdPubblicazione(), gara.getDataInizioPubblicazione());
                  String linkAffidamentoDiretto = gara.getLINK_AFFIDAMENTO_DIRETTO();
                  if (linkAffidamentoDiretto == null) {
					request.setAttribute(ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO, "");
                  }else {
					request.setAttribute(ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO, linkAffidamentoDiretto);
                  }
					
                  
                  if(messageBean == null)
                     forward(JSP_CANCELLA_GARA, request, response);
                  else
                     sendError(request, response, ((ErrorBean) messageBean).getMessage(), JSP_CANCELLA_GARA);
                  
                  return;
               }
               
               if ("ripristina".equals(azione)){
                  
                       String targetPage = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara
                                         + "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;
                  
                       gm.ripristinaGara(idGara, gara.getDATA_CONFERMA_GARA()!= null);
                       
                       LogManager logManager = new LogManager(currentActiveConnection, logger);
                       logManager.log(getTodayDate(), gara.getID_STAZIONE_APPALTANTE(), currentUser.getLogin(), "", LogManager.RIPR_GARA, gara.getCF_AMMINISTRAZIONE(), "", idGara );

                       // forward(targetPage, request, response);
                       sendMessage(request, response, "Gara ripristinata" + " ID_GARA [" + idGara + "]", targetPage);
                       return;
                    }
               else{
                  if(messageBean != null){
                     
                     if(messageBean instanceof AllValidationBeans)
                        sendValidations(request, response, (AllValidationBeans)messageBean, JSP_NUOVA_GARA);
                     else
                        sendError(request, response, ((ErrorBean) messageBean).getMessage(), JSP_NUOVA_GARA);                     
                  }
                  else
                     sendMessage(request, response, res, JSP_NUOVA_GARA);
                  
                  return;
               }              
               
            } catch ( Exception sqle ) {
                   rollback(currentActiveConnection);
               sqle.printStackTrace();
               sendError(request, response, SIMOG_GARA_005, JSP_ERRORE, sqle);
               return;
            } finally {
                commit(currentActiveConnection);
               closeConnection(request.getSession().getId(),getClass().getName());
            }
         } else {
            sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
            return;
         }
      } else {
         sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
         return;
      }
   	}


	//@Override
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		HttpSession currentActiveSession = request.getSession();
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if ( checkSession(request) ) {
			if (  ! currentUser.isAVLP()  ) {
				currentActiveSession.setAttribute(SESSION_NUMERO_LOTTI_CREATI, new Integer(0));
				currentActiveSession.setAttribute(SESSION_ID_GARA, null);				
				
				try {
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					currentActiveConnection.setAutoCommit(false);
					
					setTabelleUtilita(request, currentActiveConnection, PageHelper.getCurrentDate(), false, null);
					
					
					Object messageBean = request.getAttribute(ParametriServlet.ERRORBEAN);
					if ( messageBean != null ) {
						request.setAttribute(ERRORBEAN, messageBean);
					}
					
					// UN Caricare le informazioni sulla gara da modificare. Se idGara è null gara da creare
					String idGara = request.getParameter(ParametriServlet.SESSION_ID_GARA);
					if(idGara == null) 
					   idGara = request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA);
					
					GaraManager gm = new GaraManager(currentActiveConnection, logger); 
					
					DelegaDatiSimogAction dasAction = new DelegaDatiSimogAction(currentActiveConnection, logger);
					Gara gara = null;
					String res = null;
					
					if (idGara != null ) {					
						gara = gm.getGara(Long.valueOf(idGara), currentUser.getUffici());
						if(gara == null){
							sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
							return;
						}
/** PP solo info		
						res = dasAction.getDelegaCIGMessage(gara.getID_OSSERVATORIO(), PageHelper.getCurrentUtilDate(), currentUser);
 				if(res != null && currentUser.isRSSAorRUP()){
							sendError(request,response, res, SRV_VISUALIZZA_DETTAGLIO + "?" + ParametriServlet.FROM_GARE+"=" + Costanti.FLAG_VALORE_SI );
							return;
						}
**/						
						request.setAttribute(ParametriServlet.FIELD_NAME_ID_GARA, gara.getId_Gara());
						request.setAttribute(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE, gara.getID_STAZIONE_APPALTANTE());
						request.setAttribute(ParametriServlet.FIELD_NAME_OGGETTO_GARA, gara.getOggetto());
						
						request.setAttribute(ParametriServlet.FIELD_NAME_IMPORTO_GARA_EURO, 
								gara.getIMPORTO_GARA() != null ? gara.getIMPORTO_GARA().toString() : null);

						//gm nuovo campo simog 3.04
						request.setAttribute(ParametriServlet.FIELD_NAME_NUMERO_LOTTI, String.valueOf(gara.getNumeroLotti()));
						
						//nuovo campo 659
						request.setAttribute(ParametriServlet.FIELD_NAME_DURATA_GIORNI, String.valueOf(gara.getDurataGiorni()));

						
						//TICKET ALM #659 - 3.04.4
						 System.out.println("TECHNIS flagSAAgente "+gara.getFlagSAAgente()+" !");
						request.setAttribute(ParametriServlet.FIELD_NAME_FLAG_SA_AGENTE_GARA, gara.getFlagSAAgente());
						request.setAttribute(ParametriServlet.FIELD_NAME_ID_F_DELEGATE, String.valueOf(gara.getID_F_DELEGATE()));
						request.setAttribute(ParametriServlet.FIELD_NAME_CF_AMM_AGENTE, gara.getCF_AMM_AGENTE());
						request.setAttribute(ParametriServlet.FIELD_NAME_DEN_AMM_AGENTE, gara.getDEN_AMM_AGENTE());
						//FINE TICKET ALM #659 - 3.04.4
						
						request.setAttribute(ParametriServlet.FIELD_NAME_MODO_REALIZZAZIONE, String.valueOf(gara.getID_MODO_REAL()));
						
						//TICKET ALM #664
		                  request.setAttribute(ParametriServlet.FIELD_NAME_STRUMENTO_SVOLGIMENTO, String.valueOf(gara.getID_SVOLGIMENTO()));
		                //FINE TICKET ALM #664
		                  
		                //TICKET ALM #3832
		                  request.setAttribute(ParametriServlet.FIELD_NAME_ESTREMA_URGENZA, String.valueOf(gara.getID_ESTREMA_URGENZA()));
		                  //FINE TICKET ALM #3832
		                  
		                //TICKET ALM #3834
		                request.setAttribute(ParametriServlet.FIELD_NAME_ALLEGATO_IX, String.valueOf(gara.getID_ALLEGATO_IX()));
		                //FINE TICKET ALM #3834
						
						request.setAttribute(PSBD.FIELD_NAME_ID_MODO_INDIZIONE, String.valueOf(gara.getID_MODO_GARA()));
						request.setAttribute(ParametriServlet.FIELD_NAME_FLAG_ENTE_SPECIALE, gara.getTIPO_SCHEDA_GARA());
						request.setAttribute(ParametriServlet.FIELD_NAME_ID_STATO_GARA, String.valueOf(gara.getID_STATO_GARA()));
						request.setAttribute(ParametriServlet.FIELD_NAME_DATA_CONFERMA_GARA, String.valueOf(gara.getDATA_CONFERMA_GARA()));
						request.setAttribute(GARA.DATA_COMUN, gara.getDATA_COMUN());
						request.setAttribute(ParametriServlet.FIELD_NAME_CIG_ACC_QUADRO, gara.getCIG_ACC_QUADRO());
                  request.setAttribute(ParametriServlet.FIELD_NAME_DATA_CREAZIONE_GARA, gara.getData_creazione());
						
						// is3031_ESCL_AVCPASS
						request.setAttribute(ParametriServlet.FIELD_FLAG_ESCLUSO_AVCPASS, gara.getESCLUSO_AVCPASS());
						
						if (SimogFlags.isINT85_RFWEBGL01Active()){
	                  request.setAttribute(ParametriServlet.FIELD_NAME_SCELTA_LEGGE85, String.valueOf(gara.getSCELTA_LEGGE89()));
                     request.setAttribute(ParametriServlet.FIELD_NAME_TIPOSA_BDNCP, gara.getTIPOSA_BDNCP());
						}
						
                  request.setAttribute(ParametriServlet.ACTION, request.getParameter("toDo"));

                  // INT87
			         if(SimogFlags.isINT87_RFSIMOGWEB01Active() 
			            // PP ho esagerato! && configuration.isINT87Attivo(gara.getData_creazione() == null ? PageHelper.getCurrentDate(): gara.getData_creazione())
			            )
                     request.setAttribute(ParametriServlet.FIELD_NAME_URGENZA_DL133, gara.getURGENZA_DL133());
                  
                  // is30350_RFWEBGL01Active
                  if(SimogFlags.is30350_RFWEBGL01Active()){
                     request.setAttribute(ParametriServlet.FIELD_NAME_EAGG_COD_MOTIVO, String.valueOf(gara.getCOD_MOTIVO_EAGG()));
                     request.setAttribute(ParametriServlet.EAGG_CATEGSEL_BEAN, gara.getCatMercArray());
                  }
                  
                  //request.setAttribute(ParametriServlet.FROM_GARE, Costanti.FLAG_VALORE_SI);		
					}
/** PP solo info
  
 					else {
						if(currentUser.getOsservatori().values().size() == 1 && currentUser.isRSSAorRUP()){
							String res = dasAction.getDelegaCIGMessage(currentUser.getOsservatori().elements().nextElement(), PageHelper.getCurrentUtilDate(), currentUser);
							if(res != null && currentUser.isRSSAorRUP()){
								sendError(request,response, res, JSP_NUOVA_GARA + "?" + ParametriServlet.FROM_GARE+"=" );
								return;
							}
						}
					}
**/					
					request.getSession().removeAttribute(ParametriServlet.STORIA_PAGINAZIONE);

					// PP informazioni per controllare la delega CIG
					List<String> deleghe = new ArrayList<String>();
					for ( java.util.Enumeration e = currentUser.getUfficiByProfilo(currentUser.getProfiloEnum()).elements(); e.hasMoreElements();){
				
						StazioneAppaltante sa = (StazioneAppaltante)e.nextElement();
						
						String oss = sa.getAmministrazione().getId_osservatorio();
						deleghe.add(dasAction.getDelegaCIGMessage(oss, PageHelper.getCurrentUtilDate(), currentUser));
					}					
					request.setAttribute(ParametriServlet.DELEGHE_CIG,deleghe);
					
					String azione = request.getParameter(ParametriServlet.ACTION);
					
              if( SimogFlags.is3030_RFWEBGL02Active() ){
                 AVCPassAction avpa = new AVCPassAction(currentActiveConnection, logger, configuration); 
                 String targetPage = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara
                       + "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;   
                 
                 String codiceAVCPassAction = "cancella".equals(azione) ? AVCPassFunzioneEnum.WEB_GARA_DELETE.getCodice()
                                            : "ripristina".equals(azione) ? AVCPassFunzioneEnum.WEB_GARA_RIPRISTINA.getCodice()
                                            : AVCPassFunzioneEnum.WEB_GARA_UPDATE.getCodice();
                 
                 if( avpa.isAVCPass(gara, null, codiceAVCPassAction) ){
                    AllValidationBeans msgs = new AllValidationBeans();
                    msgs.addValidationErr(SIMOG_AVCPASS_001);
                    sendValidations(request, response, msgs, targetPage);
                    return;
                 }
              }			

              // INT85 imposto il flag per la visualizzazione della sezione
              request.setAttribute(ParametriServlet.FIELD_NAME_FLAG_LEGGE85, Costanti.FLAG_VALORE_NO);
              if(SimogFlags.isINT85_RFWEBGL01Active() 
                    && configuration.isINT85Attivo()){

                 String param = (String) (request.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE) != null 
                    ? request.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE)
                    : request.getAttribute(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE));
                    
                 if((param != null) && currentUser.isRSSAorRUP()
                       && (gara == null || configuration.isSAINT85(gara.getData_creazione()))){
                    
                    StazioneAppaltante sa = (StazioneAppaltante) currentUser.getUfficiByProfilo(ProfiloEnum.RUP).get(param);
                    if(sa != null && Costanti.COD_SA_COMUNE.equals(sa.getAmministrazione().getTipoSA()))
                       request.setAttribute(ParametriServlet.FIELD_NAME_FLAG_LEGGE85, Costanti.FLAG_VALORE_SI);
                 }                 
              }
              
              // is30350_RFWEBGL01Active visibilità sezione
              if(SimogFlags.is30350_RFWEBGL01Active() && (configuration.isEAGGAttivo(gara == null ? PageHelper.getCurrentDate() : gara.getData_creazione()))){
                 request.setAttribute(ParametriServlet.FIELD_NAME_FLAG_EAGG, Costanti.FLAG_VALORE_SI);

                 String[] lista = (String[]) (request.getParameterValues(ParametriServlet.FIELD_NAME_CATEGORIA) != null 
                       ? request.getParameterValues(ParametriServlet.FIELD_NAME_CATEGORIA)
                       : request.getAttribute(ParametriServlet.FIELD_NAME_CATEGORIA));

                 if(lista == null)
                    lista = new String[0];
                 
                 // dinamicamente decido se mostrare o meno la sezione dei comuni poiché questa sezione ha la priorità
                 if(Costanti.FLAG_VALORE_SI.equals(request.getAttribute(ParametriServlet.FIELD_NAME_FLAG_LEGGE85))){
                    // la sezione non va mostrata se sono selezionate categorie oltre la categoria 999
                    if(lista.length > 1 || lista.length == 0 
                          || (lista.length == 1 && !Costanti.EAGG_CATMERC_999.equals(lista[0]))){
                       request.setAttribute(ParametriServlet.FIELD_NAME_FLAG_LEGGE85, Costanti.FLAG_VALORE_NO);
                       // azzero anche il campo 
                       request.setAttribute(ParametriServlet.FIELD_NAME_SCELTA_LEGGE85, null);
                    }
                 }

                 String param = (String) (request.getParameter(ParametriServlet.FIELD_NAME_EAGG_COD_MOTIVO) != null 
                       ? request.getParameter(ParametriServlet.FIELD_NAME_EAGG_COD_MOTIVO)
                       : request.getAttribute(ParametriServlet.FIELD_NAME_EAGG_COD_MOTIVO));
                 
                 request.setAttribute(ParametriServlet.FIELD_NAME_EAGG_COD_MOTIVO, param);
                 request.setAttribute(ParametriServlet.EAGG_CATEGSEL_BEAN, lista);                 
              }
              
              
              if ("cancella".equals(azione)){
/** PP solo info
						if(gara  != null) {
							res = dasAction.getDelegaCIGMessage(gara.getID_OSSERVATORIO(), PageHelper.getCurrentUtilDate(), currentUser);
						}
**/						
						// preparo i dati per la visualizzazione
						TableBean dati = gm.getDettagliGaraByIdGara(idGara);
						request.setAttribute(TABLEBEAN,dati);
						
						MotivazioniBean motiviCanc = new MotivazioniBean();
						motiviCanc.loadAll(currentActiveConnection, logger, true);

						request.setAttribute(ParametriServletLotto.MOTIVAZIONI_LIST, motiviCanc.loadMotivazioni());
						request.setAttribute(ParametriServlet.ACTION, azione);
						
						// fix 34470 3.04.8
//	                  	PubblicazioneManager pubManager = new PubblicazioneManager(currentActiveConnection, logger);
//	                  	PubblicazioneBean pubblicazione = pubManager.getPubblicazione(gara.getIdPubblicazione(), gara.getDataInizioPubblicazione());
						String linkAffidamentoDiretto = gara.getLINK_AFFIDAMENTO_DIRETTO();
						if (linkAffidamentoDiretto == null) {
							request.setAttribute(ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO, "");
						}else {
							request.setAttribute(ParametriServlet.FIELD_NAME_LINK_AFFIDAMENTO_DIRETTO, linkAffidamentoDiretto);
						}
						
						if(messageBean == null)
						   forward(JSP_CANCELLA_GARA, request, response);
						else
						   sendError(request, response, ((ErrorBean) messageBean).getMessage(), JSP_CANCELLA_GARA);
						
						return;
					}
					
               if ("ripristina".equals(azione)){
                  
                       String targetPage = SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara
                                         + "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;
                  
                       gm.ripristinaGara(idGara, gara.getDATA_CONFERMA_GARA()!= null);
                       
                       LogManager logManager = new LogManager(currentActiveConnection, logger);
                       logManager.log(getTodayDate(), gara.getID_STAZIONE_APPALTANTE(), currentUser.getLogin(), "", LogManager.RIPR_GARA, gara.getCF_AMMINISTRAZIONE(), "", idGara );

                       // forward(targetPage, request, response);
                       sendMessage(request, response, "Gara ripristinata" + " ID_GARA [" + idGara + "]", targetPage);
                       return;
                    }
					else{
						if(messageBean != null){
						   
						   if(messageBean instanceof AllValidationBeans)
						      sendValidations(request, response, (AllValidationBeans)messageBean, JSP_NUOVA_GARA);
						   else
						      sendError(request, response, ((ErrorBean) messageBean).getMessage(), JSP_NUOVA_GARA);
							
						   
						   //forward(JSP_NUOVA_GARA, request, response);
						}
						else
							sendMessage(request, response, res, JSP_NUOVA_GARA);
						
						return;
					} 
					
				} catch ( Exception sqle ) {
	                rollback(currentActiveConnection);
					sqle.printStackTrace();
					sendError(request, response, SIMOG_GARA_005, JSP_ERRORE, sqle);
					return;
				} finally {
				    commit(currentActiveConnection);
					closeConnection(request.getSession().getId(),getClass().getName());
				}
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
				return;
			}
		} else {
			sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
			return;
		}
	}
}