package it.avlp.simog.garamanager.app;

import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.action.CupLottoAggAction;
import it.avlp.simog.common.servlet.ParametriCup;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.validatore.GenericUtilValidator;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SrvElencoCigIntegrazioneCup extends ServletBase{

	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {

	   // richiamo da paginazione
	   if(request.getParameter("nav")!=null){
	      perform(request, response);
	   }
	   else{
      	   // PP non usata    Connection currentActiveConnection = null;
      	   HttpSession currentActiveSession = request.getSession();
      	   
          //Recupero elenco SA abilitate e metto in sessione
          try {
//                Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
//                currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
//                GaraManager gm = new GaraManager(currentActiveConnection, logger);
//                Map<String, String> lista = gm.getSAList((String) currentUser.getAmministrazioni().keySet().toArray()[0]);                
//                request.getSession().setAttribute("listaSAsess", lista);
             
                currentActiveSession.removeAttribute(ParametriCup.ORDER_FIELD);
                currentActiveSession.removeAttribute(ParametriCup.ORDER_FIELD_VERSO);
                
                forward(ParametriCup.JSP_PARAM_INTEGRAZIONE_CUP, request, response);
             }
          catch ( Exception sqle ) {
             sqle.printStackTrace();
             sendError(request, response, sqle.getMessage(), JSP_ERRORE, sqle);
             return;
         } finally {
             // PP non serve più  closeConnection(request.getSession().getId(),getClass().getName());
         }  
	   }
	}
	
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		HttpSession currentActiveSession = request.getSession();
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if ( checkSession(request) ) {
			if ( currentUser.isRUP()) {				
				visualizzaListaParametriValori(request, response);
				
				int startRow = 0;
								
				String action = request.getParameter(ACTION_GET_LIST);
				request.setAttribute(ACTION_GET_LIST, action);

				String orderField = request.getParameter(ParametriServlet.ORDER_FIELD) == null ? null : request.getParameter(ParametriServlet.ORDER_FIELD);
				Boolean ascDesc = (Boolean)currentActiveSession.getAttribute(ParametriCup.ORDER_FIELD_VERSO);
				ascDesc = (ascDesc != null) ?  ascDesc = !ascDesc : true;
				
				boolean actionNeeded = action != null;
				
				String dataPubblicazione_da = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_DATA_PUBBLICAZIONE_START));       
				String dataPubblicazione_a = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_DATA_PUBBLICAZIONE_END));  
          
				String fromRicerca = request.getParameter(ParametriServlet.FROM_RICERCA);
				boolean fromNav = "yes".equals(request.getParameter("nav"));
				boolean ordina = "yes".equals(request.getParameter("ord"));

				if(action == null)
				   action = STILL;
				
		      if( ordina ){
               currentActiveSession.setAttribute(ParametriCup.ORDER_FIELD, orderField);
               currentActiveSession.setAttribute(ParametriCup.ORDER_FIELD_VERSO, ascDesc);
		      }
				
            if(!fromNav)
            {    
               currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_LOTTO_DA, dataPubblicazione_da );
               currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_LOTTO_A, dataPubblicazione_a );
               currentActiveSession.setAttribute(ParametriServlet.FROM_RICERCA, fromRicerca );
            }

            //CF: if obbligatorio per il corretto funzionamento del tasto "ritorna" nella pagina "Dettaglio Gara" 
				currentActiveSession.setAttribute(ParametriServlet.STORIA_PAGINAZIONE,this.getQueryString(request));

				if ( actionNeeded ) {
					String startRowS = request.getParameter(START_ROW);
					startRow = Integer.parseInt(startRowS);
					if ( action.equalsIgnoreCase(REGRESS) && (startRow - configuration.getMaxElementiPerPagina() >=0 ) ) {
						startRow = startRow - configuration.getMaxElementiPerPagina();
					} else if( action.equalsIgnoreCase(PROGRESS)) { 
						startRow = startRow + configuration.getMaxElementiPerPagina();
					}
				}
				
            orderField = (String)currentActiveSession.getAttribute(ParametriCup.ORDER_FIELD);
            ascDesc = (Boolean)currentActiveSession.getAttribute(ParametriCup.ORDER_FIELD_VERSO);
            ascDesc = ascDesc == null ? true : ascDesc;
				
				dataPubblicazione_da = (String)currentActiveSession.getAttribute(SESSION_DATA_PUBBLICAZIONE_LOTTO_DA);		
				dataPubblicazione_a = (String)currentActiveSession.getAttribute(SESSION_DATA_PUBBLICAZIONE_LOTTO_A);	
				fromRicerca = (String)currentActiveSession.getAttribute(ParametriServlet.FROM_RICERCA);
			
				
				try {
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
               // controllo parametri immessi
               GenericUtilValidator val = new GenericUtilValidator(currentActiveConnection, logger);
					
					// SE LE DATE DI PUBBLICAZIONE SONO inserite e sbagliate
					if ( (dataPubblicazione_da != null && dataPubblicazione_a != null ) && dataPubblicazione_a.compareTo( dataPubblicazione_da ) < 0 ) 
					{
						sendError(request, response, SIMOG_GARA_011, ParametriCup.JSP_PARAM_INTEGRAZIONE_CUP);
						return;
					}
					
					
					TableBean garaList = null;
					
					GaraManager garaManager = new GaraManager(currentActiveConnection, logger);		
					
					garaList = garaManager.getElencoCigIntegrazioneCup(null,
					      currentUser.getLogin(), 
					      currentUser.getUfficiByProfilo(ProfiloEnum.RUP), 
					      dataPubblicazione_a,
							dataPubblicazione_da, 
							configuration.getDataAttivazioneCup(),
							orderField,
							ascDesc,
							startRow, 
							configuration.getMaxElementiPerPagina(), null, false);
					
					if ( garaList.size() == 0 ) {
						sendMessage(request, response, Messaggi.SIMOG_RIC_001, ParametriCup.JSP_PARAM_INTEGRAZIONE_CUP);
						return;
					} 
					else {			

		            CupLottoAggAction claAction = new CupLottoAggAction(currentActiveConnection, logger);
		            Map<String, String> integrazioneCupMap = claAction.getIntegrazioneCupDatiMap(garaList);
		            request.setAttribute(ParametriCup.INTEGRAZIONE_CUP, integrazioneCupMap);

						request.setAttribute(ParametriServlet.TABLEBEAN, garaList);
						request.setAttribute(ParametriServlet.START_ROW, new Integer(startRow) );
						request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getELEMENTI_VISUALIZZATI());
						
						forward(ParametriCup.JSP_ELENCO_CIG_INTEGRAZIONE_CUP, request, response);
					}
				} catch ( Exception sqle ) {
					sqle.printStackTrace();
					sendError(request, response, sqle.getMessage(), JSP_ERRORE, sqle);
					return;
				} finally {
					closeConnection(request.getSession().getId(),getClass().getName());
				}
			} else {
				sendError(request, response, SIMOG_LOGIN_004, JSP_ERRORE );	
				return;
			}
		} else {
			sendError(request, response, SIMOG_LOGIN_003, JSP_ERRORE );
			return;
		}
		
	}

}
