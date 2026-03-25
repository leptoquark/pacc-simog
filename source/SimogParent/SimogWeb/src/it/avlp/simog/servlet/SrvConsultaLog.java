package it.avlp.simog.servlet;


import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.log.LogManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.GenericUtilValidator;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SrvConsultaLog extends ServletBase implements ParametriServlet{
	
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
				
				String requestingUrl = ParametriServlet.JSP_CONSULTA_LOG;
			
				if ( currentActiveSession.getAttribute(ParametriServlet.ORDER_FIELD) == null ) {
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
				String saRiferimento = request.getParameter(FIELD_NAME_ID_STAZIONE_APPALTANTE);
				String cfUtente	= request.getParameter(FIELD_NAME_CF_OPERATORE);
				String cfAmministrazione = request.getParameter(FIELD_NAME_CF_AMMINISTRAZIONE);
				String cigLotto	= request.getParameter(FIELD_NAME_CIG);
				String idGara	= request.getParameter(FIELD_NAME_ID_GARA);
				
				if(idGara != null) idGara = idGara.trim();
				
				String dataLog_da = PageHelper.formatDateOrNull(request.getParameter(ParametriServlet.FIELD_NAME_DATA_START_LOG));
				logger.debug ( "dataLog_da [" + dataLog_da + "]" );			
				
				String dataLog_a = PageHelper.formatDateOrNull(request.getParameter(ParametriServlet.FIELD_NAME_DATA_END_LOG)); 
				logger.debug ( "dataLog_a [" + dataLog_a + "]" );			
		
				if ( request.getParameter(FIELD_NAME_ID_STAZIONE_APPALTANTE) != null ) {
					logger.debug( "Impostazione valori di sessione [" + currentActiveSession + "]" );
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE, saRiferimento);
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_CF_OPERATORE, cfUtente);
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE, cfAmministrazione);
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_CIG, cigLotto);		
					currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_ID_GARA, idGara);
					currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_LOG_DA, dataLog_da );
					currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_LOG_A, dataLog_a );
				}
				
				cigLotto = (String)currentActiveSession.getAttribute(ParametriServlet.FIELD_NAME_CIG);
				saRiferimento = (String)currentActiveSession.getAttribute(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
				cfUtente = (String)currentActiveSession.getAttribute(ParametriServlet.FIELD_NAME_CF_OPERATORE);
				cfAmministrazione = (String)currentActiveSession.getAttribute(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
				idGara = (String)currentActiveSession.getAttribute(ParametriServlet.FIELD_NAME_ID_GARA);
				dataLog_da = (String)currentActiveSession.getAttribute(ParametriServlet.SESSION_DATA_LOG_DA);
				dataLog_a = (String)currentActiveSession.getAttribute(ParametriServlet.SESSION_DATA_LOG_A);	
				
				// annullo gli attributi che memorizzano lo stato di errore delle altre pagine (di ConsultaLogOperazioni e GestioneGare)
				currentActiveSession.removeAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG);
				currentActiveSession.removeAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG_SCHEDE);
				currentActiveSession.removeAttribute(ParametriServlet.SESSION_DATA_SCADENZA_INSERITA_GESTIONE_GARE);
				currentActiveSession.removeAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_INSERITA_GESTIONE_GARE);
				
				try {
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					
                   // 3.02.3 PP solo select per questa transazione
                    currentActiveConnection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

					// controllo parametri immessi

					GenericUtilValidator val = new GenericUtilValidator(currentActiveConnection, logger);
				
					if(val.isEmpty(saRiferimento) && val.isEmpty(cfUtente) && val.isEmpty(cfAmministrazione)
						&& val.isEmpty(cigLotto) && val.isEmpty(idGara) && val.isEmpty(dataLog_da) && val.isEmpty(dataLog_a))
					{
						sendError(request, response, Messaggi.SIMOG_RIC_003, requestingUrl);
						return;
					}
					
					if ( cigLotto != null ) {
						if ( cigLotto.length() > 0 && cigLotto.length() != 10 ) {
							sendError(request, response, SIMOG_GARA_010, JSP_CONSULTA_LOG);
							return;
						}
					}
	
					if(dataLog_da != null && dataLog_a != null) {
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG, true);
					}
					else { 
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG, false); 
					}
			
					//IdGara validazione
					if ( idGara != null && !PageHelper.isNumeric(idGara) ){
						sendError(request, response, SIMOG_GARA_018, JSP_CONSULTA_LOG);
						return;				
					}
	
					if ((dataLog_da != null && dataLog_a!= null ) && dataLog_a.compareTo( dataLog_da ) < 0  ) {
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG, true);
						sendError( request, response, "Verificare l'intervallo delle date", JSP_CONSULTA_LOG );
						return;
					}
					else if(dataLog_a != null && dataLog_da != null) {
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG, true);
					}
					else {
						currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG, false);
					}
	
					currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_INSERITA_CONSULTA_LOG, false);
					
//					Map filterMap = new LinkedHashMap();
//					filterMap.put("CF Stazione Appaltante", saRiferimento);
//					filterMap.put("CF Utente", cfUtente);
//					filterMap.put("CF Amministrazione", cfAmministrazione);
//					filterMap.put("CIG Lotto", cigLotto);
				
					logger.debug("Esecuzione LogManager");
					
					LogManager logManager = new LogManager (currentActiveConnection,logger);
		
					TableBean logResults = logManager.getLog(saRiferimento, cfUtente, cigLotto, cfAmministrazione, idGara, dataLog_da, dataLog_a, orderField, ascDesc,startRow,configuration.getPaginazioneLogAndRichA());
								
					if ( logResults.getRowsCount() > 0 ) {
						request.setAttribute(TABLEBEAN, logResults);
						/**----------------------------*/
						request.setAttribute(ParametriServlet.START_ROW, new Integer(startRow) );
						request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getPaginazioneLogAndRichA());					
						request.setAttribute(ParametriServlet.ORDER_FIELD,orderField);
						/**----------------------------*/
//						request.setAttribute("FILTERMAP", filterMap);
						forward(JSP_VISUALIZZA_LOG, request, response);						
					} else {
						sendMessage(request, response, Messaggi.SIMOG_RIC_001, requestingUrl);
						return;
					}
					
				} catch(Exception e ) {
					sendError(request, response, Messaggi.SIMOG_LOG_001, requestingUrl, e);
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