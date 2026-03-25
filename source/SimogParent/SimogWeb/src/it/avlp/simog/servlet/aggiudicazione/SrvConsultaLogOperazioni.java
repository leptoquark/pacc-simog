package it.avlp.simog.servlet.aggiudicazione;

import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.GenericUtilValidator;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SrvConsultaLogOperazioni extends ServletBase implements ParametriServlet{
	
	private static final long serialVersionUID = 3781406101060167877L;
	
	
//	protected void doGet(HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException {
//		perform ( request, response );
//	}
	

	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		HttpSession currentActiveSession = request.getSession();
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		visualizzaListaParametriValori( request, response );
		
		if ( checkSession(request) ) {
			if ( currentUser.isAmministratore() ) {
				
				String requestingUrl = ParametriServlet.JSP_CONSULTA_LOG_OPERAZIONI;
			
				if ( currentActiveSession.getAttribute(ParametriServlet.ORDER_FIELD) == null) {
					currentActiveSession.setAttribute(ParametriServlet.ORDER_FIELD, Boolean.FALSE);
				}
				
				boolean ascDesc = ( (Boolean)currentActiveSession.getAttribute(ParametriServlet.ORDER_FIELD) ).booleanValue();
				String orderField = request.getParameter(ParametriServlet.ORDER_FIELD);	
				String actionGetList = request.getParameter(ParametriServlet.ACTION_GET_LIST);
				if(orderField != null && actionGetList == null) { 
					ascDesc = ! ascDesc;
					currentActiveSession.setAttribute(ParametriServlet.ORDER_FIELD, ascDesc );
				}
				
				/** -----------------adds------------------- */
				int startRow = 0;
				
				String action = request.getParameter(ACTION_GET_LIST);
				
				boolean actionNeeded = action != null;

				if ( actionNeeded ) {
					String startRowS = request.getParameter(START_ROW);
					startRow = Integer.parseInt(startRowS);
					if ( action.equalsIgnoreCase(REGRESS) ) {
						startRow = startRow - configuration.getPaginazioneLogAndRichA();
					} else {
						startRow = startRow + configuration.getPaginazioneLogAndRichA();
					}
				}
				/** -----------------adds end--------------------- */
				String cfUtente	= request.getParameter(FIELD_NAME_CF_OPERATORE);
				logger.debug("test filtro di ricerca. Utente impostato: "+cfUtente);
				
				String bloccoDati	= request.getParameter(PSBD.ID_BLOCCO_DATI);
				String fullCIG	= request.getParameter(ParametriServlet.FIELD_NAME_CIG);
				String dataLog_da = PageHelper.formatDateOrNull(request.getParameter(ParametriServlet.FIELD_NAME_DATA_START_LOG)); 

		
				logger.debug ( "dataLog_da [" + dataLog_da + "]" );			
				
				String dataLog_a = PageHelper.formatDateOrNull(request.getParameter(ParametriServlet.FIELD_NAME_DATA_END_LOG)); 
					
		
				logger.debug ( "dataLog_a [" + dataLog_a + "]" );			
		
				if ( cfUtente != null ) {
					logger.debug( "Impostazione valori di sessione [" + currentActiveSession + "]" );
					
					currentActiveSession.setAttribute(FIELD_NAME_CF_OPERATORE, cfUtente);
					currentActiveSession.setAttribute(PSBD.ID_BLOCCO_DATI, bloccoDati);
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_CIG, fullCIG);					
					currentActiveSession.setAttribute(SESSION_DATA_LOG_DA, dataLog_da );
					currentActiveSession.setAttribute(SESSION_DATA_LOG_A, dataLog_a );
				}
				
				fullCIG = (String)currentActiveSession.getAttribute(FIELD_NAME_CIG);
				cfUtente = (String)currentActiveSession.getAttribute(FIELD_NAME_CF_OPERATORE);
				bloccoDati	= (String)currentActiveSession.getAttribute(PSBD.ID_BLOCCO_DATI);
				dataLog_da = (String)currentActiveSession.getAttribute(SESSION_DATA_LOG_DA);
				dataLog_a = (String)currentActiveSession.getAttribute(SESSION_DATA_LOG_A);	

//				Map filterMap = new LinkedHashMap();
//				
//				filterMap.put("CF Utente", cfUtente);
//				filterMap.put("Blocco dati", bloccoDati);
//				filterMap.put("CIG", fullCIG);
				
				// annullo gli attributi di sessione
				currentActiveSession.removeAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE);
				currentActiveSession.removeAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE);
				currentActiveSession.removeAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG);
				currentActiveSession.removeAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG_SCHEDE);
				
				try {
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					// 3.02.2.1 PP solo select per questa transazione
					currentActiveConnection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
					
					// controllo parametri immessi
					GenericUtilValidator val = new GenericUtilValidator(currentActiveConnection, logger);
				
					if(val.isEmpty(fullCIG) && val.isEmpty(cfUtente) && val.isEmpty(bloccoDati)
						&& val.isEmpty(dataLog_da) && val.isEmpty(dataLog_a))
					{
						sendError(request, response, Messaggi.SIMOG_RIC_003, requestingUrl);
						return;
					}

					if ( (dataLog_a != null && dataLog_da != null) && dataLog_a.compareTo( dataLog_da ) < 0 ) {
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG_SCHEDE, true);
						sendError( request, response, "Verificare l'intervallo delle date", JSP_CONSULTA_LOG_OPERAZIONI );
						return;
					}
					else if(dataLog_a != null && dataLog_da != null) {
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG_SCHEDE, true);
					}
					else {
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG_SCHEDE, false);
					}
					
					if ( fullCIG != null ) {
						if ( fullCIG.length() > 0 && fullCIG.length() != 10 ) {
							sendError(request, response, SIMOG_GARA_010, JSP_CONSULTA_LOG_OPERAZIONI);
							return;
						}
					}
					
					
					logger.debug("Esecuzione LogManager");
					
					LogBloccoDatiManager logManager = new LogBloccoDatiManager (currentActiveConnection,logger);
					String nextPage = JSP_VISUALIZZA_LOG_OPERAZIONI;
					TableBean logResults = null;
//					logger.debug("-------------////////---------------");
//					logger.debug("fullcig: " + fullCIG);
//					logger.debug("if condition: " + (fullCIG != null && !fullCIG.equals("") && fullCIG.trim().length() == 10));
//					logger.debug("-------------\\\\\\\\--------------");
					if(fullCIG != null && !fullCIG.equals("") /// ma de che && fullCIG.trim().length() == 10
					){
						logResults = logManager.getLog(cfUtente, bloccoDati, dataLog_da, dataLog_a, orderField, ascDesc,startRow,configuration.getPaginazioneLogAndRichA(),fullCIG);
						nextPage += "?"+ParametriServlet.FIELD_NAME_CIG+"="+fullCIG;
					}else{
						logResults = logManager.getLog(cfUtente, bloccoDati, dataLog_da, dataLog_a, orderField, ascDesc,startRow,configuration.getPaginazioneLogAndRichA());						
					}		
					if ( logResults.getRowsCount() > 0 ) {
						request.setAttribute(TABLEBEAN, logResults);
						/**----------------------------*/
						request.setAttribute(ParametriServlet.START_ROW, new Integer(startRow) );
						request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getPaginazioneLogAndRichA());					
						/**----------------------------*/
//						request.setAttribute("FILTERMAP", filterMap);
//						request.setAttribute(ParametriServlet.ORDER_FIELD,orderField);
						forward(nextPage, request, response);						
					} else {
						sendMessage(request, response, Messaggi.SIMOG_RIC_001, requestingUrl);
						return;
					}
					
				} catch(Exception e ) {
					sendError(request, response, Messaggi.SIMOG_LOG_001, requestingUrl, e);
					e.printStackTrace();
					return;
				} finally {
				
					closeConnection(request.getSession().getId(),getClass().getName());
				}
			} else {
				sendError(request, response, SIMOG_LOGIN_003, JSP_ERRORE);
				return;
			}
		} else {
			sendError(request, response, SIMOG_LOGIN_004, JSP_ERRORE);
			return;
		}
	}
}
