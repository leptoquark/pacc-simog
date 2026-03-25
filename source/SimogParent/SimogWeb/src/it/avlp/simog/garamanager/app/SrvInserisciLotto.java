package it.avlp.simog.garamanager.app;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.avcp.simog.managers.cpv.CPVEUManager;
import it.avcp.simog.managers.luogo.IstatNutsManager;
import it.avlp.simog.actions.GaraLottoAction;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.beans.CpvLotto;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.generated.EAGG_CATEGORIE;
import it.avlp.simog.db.generated.MISURA_PREMIALE;
import it.avlp.simog.db.generated.MOTIVO_DEROGA;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.log.LogManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

public class SrvInserisciLotto extends ServletBase implements ParametriServlet {
	
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		perform(request, response);
	}
	
	protected void perform(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		creaNuovoLotto(request, response);
	}

	
	/************************************************************************************************
	 * Crea un nuovo lotto 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void creaNuovoLotto(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException {
		String id_sa_riferimento = null;
		HttpSession currentActiveSession = request.getSession();
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);

		//String data = getTodayDate();
	
		Connection currentActiveConnection = null;
		visualizzaListaParametriValori(request, response);
	
		if ( checkSession(request) ) {
			if ( currentUser.isRSSAorRUP() ) {
				//request.setAttribute(ParametriServlet.FROM_GARE, Costanti.FLAG_VALORE_SI);		
				String requestingURL = currentUser.isAmministratore() ? JSP_GESTIONE_GARE_EXT : JSP_GESTIONE_SCHEDE;
				
				try {
					currentActiveConnection = getSimogConnection(request.getSession().getId(),  getClass().getName());
				    //logger.debug("current isolation level: " + isolationLevel);
					
					currentActiveConnection.setAutoCommit(false);
					currentActiveConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);					
					//logger.debug("new isolation level: " + currentActiveConnection.getTransactionIsolation());

					//visualizzaListaParametriValori(request, response);
				
					if(isRefresh(request)){
						try{
							requestingURL = ParametriServlet.SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + currentActiveSession.getAttribute( SESSION_ID_GARA ).toString();
							sendError(request, response, Messaggi.SIMOG_VALIDAZIONE_000, requestingURL );
							return;
						}catch (Exception e) {
							sendError(request, response, Messaggi.SIMOG_VALIDAZIONE_000, requestingURL );
							return;
						}
					}
					

					Object lottiCreati = currentActiveSession.getAttribute(SESSION_NUMERO_LOTTI_CREATI);
					//logger.debug( "Per la gara in corso sono presenti lotti [" + lottiCreati + "]" );
					
					int lottoCount =  ( lottiCreati != null ) ? ( (Integer) lottiCreati ).intValue() : 1;
					requestingURL = ParametriServlet.SRV_INIZIALIZZA_LOTTO;
					
					GaraLottoAction gla = new GaraLottoAction(currentActiveConnection,logger, configuration);
					//Object obj = null;
					

					// questo load non fa' anche la validazione 
					Gara gara = gla.loadGaraFromDBSenzaValidazione(request);
					

					String redirectUrl = SRV_VISUALIZZA_DETTAGLIO+"?"+ParametriServlet.SESSION_ID_GARA+"="+gara.getId_Gara()
										+ "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;

/** PP solo info										
					DelegaDatiSimogAction dasAction = new DelegaDatiSimogAction(currentActiveConnection, logger);
					String res = dasAction.getDelegaCIGMessage(gara.getID_OSSERVATORIO(), PageHelper.getCurrentUtilDate(), currentUser);
					if(res != null && currentUser.isRSSAorRUP()){
						sendError(request,response, res,redirectUrl );
						
						return;
						
					}
					
					
**/	
					
					
					/******************************************************************************************/
					/**********************************[ LOTTO ]***********************************************/
					/******************************************************************************************/
				
					Lotto lotto = new Lotto();
					/** il load fa' anche la validazione */
					Object obj1 = gla.load(request, GaraLottoAction.TIPO_LOTTO);
					Object tmp=request.getAttribute(ParametriServlet.ERRORBEAN); 
  					
					Object obj2 = gla.loadCategorieFromRequest(request);
					if(obj1 instanceof AllValidationBeans){					
						/** se non valide */
						if(obj2 instanceof AllValidationBeans){
							AllValidationBeans avb = (AllValidationBeans)obj1;
							/** merge dei 2 risultati */
							avb.add((AllValidationBeans)obj2);
							/** invia */
							sendValidations(request, response, avb, requestingURL);	
							return;
						}	
						/** altrimenti manda */
						sendValidations(request, response, (AllValidationBeans)obj1, requestingURL);
						return;
					}
					if(obj2 instanceof AllValidationBeans){
						/** invia */
						sendValidations(request, response, (AllValidationBeans)obj2, requestingURL);	
						return;
					}
					/** inserimento del lotto */
					String[] categorie = (String[])obj2;
					lotto = (Lotto)obj1;
					lotto.setId_Gara(gara.getId_Gara());
					
					 //TICKET ALM - 3.04.7
                	 if(lotto.getLUOGO_ISTAT()!=null && !"".equals(lotto.getLUOGO_ISTAT())) {
                		 IstatNutsManager inm = new IstatNutsManager(currentActiveConnection,logger);
                		 lotto.setLUOGO_NUTS(inm.getNutsFromIstat(lotto.getLUOGO_ISTAT()));
                	 }
					
                	 // #31047 parita di genere
					lotto = gla.insertLotto(lotto, gara, CIGBean.APPL_SIMOG, currentUser.getAdminOr(),categorie);
					
					id_sa_riferimento = gara.getID_STAZIONE_APPALTANTE();
					/** se c'e' l'id l'inserimento e' sicuramente avvenuto */
					if ( lotto.getId_Lotto() != 0 ) {
						/** logging */
						logger.debug("Inserimento nel LOG di sistema del CIG [" + lotto.getCIG() + lotto.getCIG_kkk() + "]" );
						LogManager logManager = new LogManager(currentActiveConnection, logger);
						logManager.log(
								PageHelper.getDBDateFromTS(new AccessiDB(currentActiveConnection,logger).getNow()),
								id_sa_riferimento,
								currentUser.getLogin(),
								lotto.getCIG() + lotto.getCIG_kkk(),
								LogManager.INS_LOTTO,
								currentUser.getCodiceFiscaleAmministrazioneByIdUfficio(id_sa_riferimento),
								Long.toString( lotto.getId_Lotto() ), 
								Long.toString( lotto.getId_Gara()) );
						/** commit */
						commit(currentActiveConnection);
						/** older stuff lasciate invariate */		
						
						//TICKET ALM - 3.04.2 NG
						   setTabelleUtilita(request, currentActiveConnection, gara.getData_creazione(), gara.isOrganoCost(), null);
	                    //FINE TICKET ALM - 3.04.2 NG
						
						 //TICKET ALM - 3.04.4
						 //Recupera le categorie selezionate in gara e inseriscile nella lista delle opzioni selezionabili
						 if(gara.getData_creazione().compareTo(SimogProperties.getInstance().getDataAttivazione3044()) >= 0) {
							 Map<String, String> listaCategorie = new AccessiDB(currentActiveConnection,logger).getTipologica(EAGG_CATEGORIE.TABLE_NAME, EAGG_CATEGORIE.COD_CATEGORIA, EAGG_CATEGORIE.DESCRIZIONE, EAGG_CATEGORIE.DATA_INIZIO_VALIDITA, EAGG_CATEGORIE.DATA_FINE_VALIDITA, PageHelper.parseTimeYMD(gara.getData_creazione()));
							 Map<String,String> listaCategorieLotto = new HashMap<String,String>();	 
							 for(String codGara : gara.getCatMercArray()) {
	
								 for (Map.Entry<String, String> entry : listaCategorie.entrySet()) {
									 if(codGara.equals(entry.getKey()))
										 listaCategorieLotto.put(entry.getKey(), entry.getValue());
								 }
								 
							 }
							 
							 request.setAttribute(LISTA_CATEGORIE_LOTTO, listaCategorieLotto);
						 }
						 //FINE TICKET ALM - 3.04.4
						 
						request.setAttribute(ParametriServlet.ACTION_AGGIUNGI_LOTTO, ParametriServlet.ACTION_AGGIUNGI_LOTTO);
		
						currentActiveSession.setAttribute(SESSION_NUMERO_LOTTI_CREATI, new Integer(++lottoCount));
						logger.debug ( "Incrementato numero lotti creati [" + lottoCount + "]" );	
						
						//TICKET ALM #3908
      					//Aggiungi warning per importi superiori a 300 mila euro in caso di somme urgenze per beni culturali
                        //messaggi da ritornare
      		    		AllValidationBeans msgs = new AllValidationBeans();
      		    		if (tmp != null) {
      		    			msgs.add((AllValidationBeans)tmp);
      		    			
      		    		}
      					if(lotto.getImporto_Lotto().doubleValue() > Costanti.SOGLIA_BENI_CULTURALI && gara.getID_ESTREMA_URGENZA()==Costanti.TIPO_ESTREMA_URGENZA_BENI_CULTURALI)
      					{
      						msgs.addValidationWarn(Messaggi.SIMOG_LOTTO_027);
      					}
      					//FINE TICKET ALM #3908
      					
      					//TICKET ALM - 3.04.2 NG
      					//Aggiunti warning per importi uguali o superiori a 40k in caso di affidamento diretto
      					if(lotto.getImporto_Lotto().doubleValue() >= Costanti.IMPORTO_LOTTO_40000 && lotto.getId_Scelta_Contraente().equals(Costanti.AFFIDAMENTO_DIRETTO))
      						msgs.addValidationWarn(Messaggi.SIMOG_LOTTO_028);
						
						//TICKET #19858
      					if(lotto.getImporto_opzioni()==null || lotto.getImporto_opzioni().doubleValue()==0)
      						msgs.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "l'importo opzioni/ripetizioni"));
      					
      					
      					//TICKET ALM #13518 - 3.04.4.1
      					if(gara.getCIG_ACC_QUADRO()!=null && !"".equals(gara.getCIG_ACC_QUADRO())){
      						LottoManager lm = new LottoManager(currentActiveConnection,logger);
      						java.util.List<Lotto> lottoListAccQ = lm.getLottoByCigWS(gara.getCIG_ACC_QUADRO());
      						if (lottoListAccQ != null && !lottoListAccQ.isEmpty()) {
      						Lotto lottoAccQ = lottoListAccQ.get(0);
						
//      						List<Lotto> listaLotti = lm.getListaLotti(lotto.getId_Gara());
      						BigDecimal sumImporti = lm.getSommaImportiAdesioni(gara.getCIG_ACC_QUADRO());
//      						for(Lotto lottoEl : listaLotti)
//      							sumImporti.add(lottoEl.getImporto_Lotto());
      						
      						sumImporti.add(lotto.getImporto_Lotto());
      						
      						if(sumImporti.doubleValue() > lottoAccQ.getImporto_Lotto().doubleValue())
      							msgs.addValidationWarn(Messaggi.SIMOG_LOTTO_035);
      						
      						 //TICKET ALM #22951
      						 if(gara.getData_creazione().compareTo(SimogProperties.getInstance().getDataAttivazione3046()) >= 0) {
      							 CPVEUManager cpvMan = new CPVEUManager(currentActiveConnection, logger);
								 Map<String,String> listaCPV = new HashMap<String,String>();
								 String cpvPrevalente = lottoAccQ.getId_CPV();
								listaCPV.put(cpvPrevalente, cpvPrevalente+" - "+(cpvMan.getCPVDesc(cpvPrevalente)));
								for(CpvLotto cpvSecondaria : lottoAccQ.getElencoCpvSecondarie()) {
									cpvSecondaria.setDescrizione(cpvMan.getCPVDesc(cpvSecondaria.getIdCpv()));
									listaCPV.put(cpvSecondaria.getIdCpv(), cpvSecondaria.getIdCpv()+" - "+cpvSecondaria.getDescrizione());
								}
								
								request.setAttribute("LISTA_CPV_ADESIONE", listaCPV);
      						 }
      						}
      						
      					}
      					
      				//TICKET ALM - 3.04.7
						 //Recupera i motivi deroga
						 //if(gara.getData_creazione().compareTo(SimogProperties.getInstance().getDataAttivazione3047()) >= 0) {
							 Map<String, String> listaMotivoDeroga= new AccessiDB(currentActiveConnection,logger).getTipologica(MOTIVO_DEROGA.TABLE_NAME, MOTIVO_DEROGA.DESCRIZIONE, MOTIVO_DEROGA.DATA_INIZIO_VALIDITA, MOTIVO_DEROGA.DATA_FINE_VALIDITA, PageHelper.parseTimeYMD(gara.getData_creazione()));
							 Map<String,String> listaMotivoDerogaLotto = new HashMap<String,String>();	 
							 for(String codGara : gara.getCatMercArray()) {
	
								 for (Map.Entry<String, String> entry : listaMotivoDeroga.entrySet()) {
									 if(codGara.equals(entry.getKey()))
										 listaMotivoDerogaLotto.put(entry.getKey(), entry.getValue());
								 }
								 
							 }
							 
							 request.setAttribute(MOTIVO_DEROGA_TABLEBEAN, listaMotivoDerogaLotto);
							 
							 Map<String, String> listaMisurePremiali= new AccessiDB(currentActiveConnection,logger).getTipologica(MISURA_PREMIALE.TABLE_NAME, MISURA_PREMIALE.DESCRIZIONE, MISURA_PREMIALE.DATA_INIZIO_VALIDITA, MISURA_PREMIALE.DATA_FINE_VALIDITA, PageHelper.parseTimeYMD(gara.getData_creazione()));
							 Map<String,String> listaMisurePremialiLotto = new HashMap<String,String>();	 
							 for(String codGara : gara.getCatMercArray()) {
	
								 for (Map.Entry<String, String> entry : listaMisurePremiali.entrySet()) {
									 if(codGara.equals(entry.getKey()))
										 listaMisurePremialiLotto.put(entry.getKey(), entry.getValue());
								 }
								 
							 }
							 
							 request.setAttribute(MISURA_PREMIALE_TABLEBEAN, listaMisurePremialiLotto);
													 
						 //}
						 //FINE TICKET ALM - 3.04.7
      					
						/***************************************************************************************/
						/*********************** CONSIDERIAMO ANCHE LA SOMMA URGENZA ***************************/
						String currentCIG =  lotto.getCIG() + lotto.getCIG_kkk();
						String sommaUrgenza = String.valueOf(lotto.getSomma_Urgenza()); 
						currentCIG = PageHelper.getCIG( currentCIG,  sommaUrgenza, PageHelper.getCurrentDate() );
						/***************************************************************************************/
						//TICKET ALM #3908
						//sendMessage(request, response, SIMOG_LOTTO_020 + " CIG [" + currentCIG + "]", redirectUrl);	
						msgs.addValidationInfo(SIMOG_LOTTO_020 + " CIG [" + currentCIG + "]");
						msgs.addValidationWarn(SIMOG_LOTTO_020c);//MEV 44995 3.04.11
						sendValidations(request, response, msgs, redirectUrl);
						//FINE TICKET ALM #3908
						return;
					} else {
					    rollback(currentActiveConnection);
//						logger.error ( esitoInserimentoLotto );
						logger.error ( "inserimento o validazione Lotto fallita" );
						sendError(request, response, SIMOG_LOTTO_016, requestingURL);
						//sendValidations(request, response, (AllValidationBeans)obj1, requestingURL);
						return;
					}
					
				/** added ActionException perche e' l'eccezione lanciata dalle classi action */
				} catch ( ActionException ae ) {
					ae.printStackTrace();
					rollback(currentActiveConnection);					
					sendError( request, response, ae.getMessage(), requestingURL);
					return;
				} catch ( SimogException se ) {
					se.printStackTrace();
					rollback(currentActiveConnection);					
					sendError( request, response, se.getMessage(), requestingURL);
					return;
				} catch ( Exception e ) {
					e.printStackTrace();
					rollback(currentActiveConnection);
					logger.fatal ( e.getMessage());
					sendError(request, response, Messaggi.SIMOG_GARA_001, requestingURL, e);
					return;
				} finally {
				//	rollbackOrcommit(currentActiveConnection);					
					closeConnection(request.getSession().getId(),getClass().getName());
				}
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE);					
				return;
			}
		} else {
			sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE);
			return;
		}			
	}
}