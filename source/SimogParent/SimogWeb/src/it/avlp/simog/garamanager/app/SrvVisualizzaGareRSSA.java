package it.avlp.simog.garamanager.app;

import it.avlp.simog.beans.SoglieImpEnum;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.GenericUtilValidator;

import java.io.IOException;
import java.sql.Connection;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SrvVisualizzaGareRSSA extends ServletBase {

	public void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		
		perform(request, response);
	}

	private static final long serialVersionUID = -4427708044851647738L;
	
	/**
	 * @see ServletBase#perform(HttpServletRequest, HttpServletResponse)
	 */
	public void perform(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		// disattivata
		return;
		
//		
//		Connection currentActiveConnection = null;
//		HttpSession currentActiveSession = request.getSession();
//		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
//		if ( checkSession(request) ) {
//			if ( currentUser.isRSSAorRUP() ) {				
//
//				request.setAttribute(ParametriServlet.FROM_GARE, Costanti.FLAG_VALORE_SI);					
//
//				visualizzaListaParametriValori(request, response);
//				
//				int startRow = 0;
//				
//				String action = request.getParameter(ParametriServlet.ACTION_GET_LIST);
//				
//				boolean actionNeeded = action != null;
//				
//				currentActiveSession.setAttribute(ParametriServlet.STORIA_PAGINAZIONE,this.getQueryString(request));
//				
//				if ( actionNeeded ) {
//					String startRowS = request.getParameter(ParametriServlet.START_ROW);
//					startRow = Integer.parseInt(startRowS);
//					if ( action.equalsIgnoreCase(ParametriServlet.REGRESS) ) {
//						startRow = startRow - configuration.getMaxElementiPerPagina();
//					} else {
//						startRow = startRow + configuration.getMaxElementiPerPagina();
//					}
//				}
//				
//				String dataPubblicazione_da = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_DATA_PUBBLICAZIONE_START));
//				logger.debug ( "dataPubblicazione_da [" + dataPubblicazione_da + "]" );			
//				
//				String dataPubblicazione_a = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_DATA_PUBBLICAZIONE_END));	
//				logger.debug ( "dataPubblicazione_a [" + dataPubblicazione_a + "]" );			
//				
//				String dataScadenza_da = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_SCADENZA_START));
//				logger.debug ( "dataScadenza_da [" + dataScadenza_da + "]" );
//				
//				String dataScadenza_a = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_SCADENZA_END));
//				logger.debug ( "dataScadenza_a [" + dataScadenza_a + "]" );
//	
//				String cig = request.getParameter(ParametriServlet.FIELD_NAME_CIG);
//				String numeroGara = request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA);
//				String oggettoGara = request.getParameter(ParametriServlet.FIELD_NAME_OGGETTO_GARA);
//				String oggettoLotto = request.getParameter(ParametriServlet.FIELD_NAME_OGGETTO_LOTTO);
//				String idStazioneAppaltante = request.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
//				String id_soglia = request.getParameter(ParametriServlet.ID_SOGLIA_IMPORTO);
//
//	
//				if ( request.getParameter("chiChiama") != null ) {
//					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_CIG, cig );
//					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_OGGETTO_GARA, oggettoGara );
//					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_OGGETTO_LOTTO, oggettoLotto );
//					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE, idStazioneAppaltante);
//					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_ID_GARA, numeroGara );
//					currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_LOTTO_DA, dataPubblicazione_da );
//					currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_LOTTO_A, dataPubblicazione_a );
//					currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_A, dataScadenza_a );
//					currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_DA, dataScadenza_da );	
//					currentActiveSession.setAttribute(ParametriServlet.ID_SOGLIA_IMPORTO, id_soglia );
//				}
//				
//				cig = (String)currentActiveSession.getAttribute(FIELD_NAME_CIG);
//				oggettoGara = (String)currentActiveSession.getAttribute(FIELD_NAME_OGGETTO_GARA);
//				oggettoLotto  = (String)currentActiveSession.getAttribute(FIELD_NAME_OGGETTO_LOTTO);
//				idStazioneAppaltante = (String)currentActiveSession.getAttribute(FIELD_NAME_ID_STAZIONE_APPALTANTE);
//				numeroGara = (String)currentActiveSession.getAttribute(FIELD_NAME_ID_GARA);
//				dataPubblicazione_da = (String)currentActiveSession.getAttribute(SESSION_DATA_PUBBLICAZIONE_LOTTO_DA);
//				dataPubblicazione_a = (String)currentActiveSession.getAttribute(SESSION_DATA_PUBBLICAZIONE_LOTTO_A);
//				dataScadenza_da = (String)currentActiveSession.getAttribute(SESSION_DATA_SCADENZA_DA);
//				dataScadenza_a = (String)currentActiveSession.getAttribute(SESSION_DATA_SCADENZA_A);
//				id_soglia = (String)currentActiveSession.getAttribute(ID_SOGLIA_IMPORTO);
//				
//				Hashtable listaSARiferimento = new Hashtable();
//				
//				if ( "tutte".equalsIgnoreCase(idStazioneAppaltante) ) {
//					listaSARiferimento = currentUser.getUffici();
//					logger.debug("Ricerca Gare relative a tutte le stazioni appaltanti [" + currentUser.getUffici().keys() + "]");
//				} else {
//					logger.debug("Ricerca Gare relative a stazione appaltante [" + idStazioneAppaltante + "]");
//					listaSARiferimento.put(idStazioneAppaltante, currentUser.getUffici().get(idStazioneAppaltante));
//				}
//
//				
//				try {
//					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
//					
//					// controllo parametri immessi
//
//					GenericUtilValidator val = new GenericUtilValidator(currentActiveConnection, logger);
//					 
//					if(val.isEmpty(oggettoGara) && val.isEmpty(oggettoLotto) && val.isEmpty(cig) && val.isEmpty(idStazioneAppaltante)
//						&& val.isEmpty(numeroGara) && val.isEmpty(dataScadenza_a) && val.isEmpty(dataScadenza_da) 
//						&& val.isEmpty(dataPubblicazione_a) && val.isEmpty(dataPubblicazione_da)  
//						&& val.isEmpty(id_soglia)
//					)
//					{
//						sendError(request, response, Messaggi.SIMOG_RIC_003, JSP_GESTIONE_GARE_RSSA);
//						return;
//					}
//
//					// Validazione del Numero Gara
//					if ( numeroGara != null ) {
//						if (numeroGara.length() > 8 || !PageHelper.isNumeric(numeroGara)){
//							sendError(request, response, SIMOG_GARA_018, JSP_GESTIONE_GARE_RSSA);
//							return;
//						}
//					}
//					
//					if ( cig != null ) {
//						if ( cig.length() != 0 && cig.length() != 10 ) {
//							if(dataPubblicazione_da != null && dataPubblicazione_a != null) {
//								currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE, true);
//							}
//							else { currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE, false); }
//							if(dataScadenza_da != null && dataScadenza_a != null) {
//								currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, true);
//							}
//							else { currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, false);	}
//							sendError(request, response, SIMOG_GARA_010, JSP_GESTIONE_GARE_RSSA);
//							return;
//						}
//					}
//					
//					if((dataPubblicazione_da != null && dataPubblicazione_a != null ) && dataPubblicazione_a.compareTo( dataPubblicazione_da ) < 0 ) {
//						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE, true);
//						if( dataScadenza_da != null && dataScadenza_a != null ) {
//							currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, true);
//						}
//						else {
//							currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, false);
//						}					
//						sendError(request, response, SIMOG_GARA_011, JSP_GESTIONE_GARE_RSSA);
//						return;
//					}
//					else if(dataPubblicazione_da != null && dataPubblicazione_a != null ) {
//						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE, true);
//					}
//					else
//						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE, false);
//					
//					
//					if((dataScadenza_da != null && dataScadenza_a != null ) && dataScadenza_a.compareTo( dataScadenza_da ) < 0 ) {
//						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, true);
//						sendError(request, response, SIMOG_GARA_012, JSP_GESTIONE_GARE_RSSA);
//						return;
//					}
//					else if((dataScadenza_da != null && dataScadenza_a != null ) && dataScadenza_a.compareTo( dataScadenza_da ) >= 0 ) {
//						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, true);
//					}
//					else {
//						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE, false);
//					}
//					
//					//Recupero soglie min e max importo
//					SoglieImpEnum soglia = SoglieImpEnum.getEnumByCodice(id_soglia);
//					
//					TableBean garaList = null;
//					
//					if(cig != null){
//					GaraManager garaManager = new GaraManager(currentActiveConnection, logger);				
//					
//					logger.debug("Esecuzione GaraList");
//	
//					garaList = garaManager.getGaraList(
//							oggettoGara,
//							oggettoLotto,
//							cig,
//							numeroGara,
//							listaSARiferimento,
//							dataScadenza_a,
//							dataScadenza_da,
//							dataPubblicazione_a,
//							dataPubblicazione_da,
//							startRow,
//							configuration.getMaxElementiPerPagina(),
//							false, null,null, null, null,
//							soglia != null ? soglia.min() : null,
//							soglia != null ? soglia.max() : null,
//							null);
//					}// se non e' stata eseguita getgaralist semplice forward alla pagina di ricerca
//					if(garaList == null){
//						forward(JSP_GESTIONE_GARE_RSSA, request, response);
//						return;
//					}else if ( garaList.size() == 0 ) {
//						sendMessage(request, response, Messaggi.SIMOG_RIC_001, JSP_GESTIONE_GARE_RSSA);
//						return;
//					} else {
//						request.setAttribute(ParametriServlet.TABLEBEAN, garaList);
//						request.setAttribute(ParametriServlet.START_ROW, new Integer(startRow) );
//						request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getELEMENTI_VISUALIZZATI());					
//						
//						forward(ParametriServlet.JSP_VISUALIZZA_GARE_RSSA, request, response);
//					}
//				} catch ( Exception sqle ) {
//					sendError(request, response, sqle.getMessage(), JSP_GESTIONE_GARE_RSSA, sqle);
//					return;
//				} finally {
//					closeConnection(request.getSession().getId(),getClass().getName());
//				}
//			} else {
//				sendError(request, response, SIMOG_LOGIN_004, JSP_ERRORE );	
//				return;
//			}
//		} else {
//			sendError(request, response, SIMOG_LOGIN_003, JSP_ERRORE );
//			return;
//		}
	}

}