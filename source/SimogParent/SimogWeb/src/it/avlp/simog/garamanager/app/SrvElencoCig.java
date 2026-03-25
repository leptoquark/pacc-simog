package it.avlp.simog.garamanager.app;

import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.GenericUtilValidator;

import java.io.IOException;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SrvElencoCig extends ServletBase{

	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {

	   // richiamo da paginazione
	   if(request.getParameter("nav")!=null){
	      perform(request, response);
	   }
	   else{
      	   Connection currentActiveConnection = null;
      	   
          //Recupero elenco SA abilitate e metto in sessione
          try {
                Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
                currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
                GaraManager gm = new GaraManager(currentActiveConnection, logger);
                // Map<String, String> lista = gm.getSAList((String) currentUser.getAmministrazioni().keySet().toArray()[0]);
                Map<String, String> lista = gm.getSAList(currentUser.getAmministrazioni());
                
                request.getSession().setAttribute("listaSAsess", lista);
                
                forward("paramElencoCig.jsp", request, response);
             }
          catch ( Exception sqle ) {
             sqle.printStackTrace();
             sendError(request, response, sqle.getMessage(), JSP_ERRORE, sqle);
             return;
         } finally {
             closeConnection(request.getSession().getId(),getClass().getName());
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
				
				boolean actionNeeded = action != null;
				
                String dataPubblicazione_da = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_DATA_PUBBLICAZIONE_START));       
                String dataPubblicazione_a = PageHelper.formatDateOrNull(request.getParameter(FIELD_NAME_DATA_PUBBLICAZIONE_END));  
                String cfRUP = request.getParameter(ParametriServlet.FIELD_NAME_CF_OPERATORE);      
                String idCentro  = request.getParameter(ParametriServlet.FIELD_NAME_CODICE_CC);
                String fromRicerca = request.getParameter(ParametriServlet.FROM_RICERCA);
                boolean fromNav = "yes".equals(request.getParameter("nav"));

				if(action == null)
				   action = STILL;
					
                if(!fromNav){
                    currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_LOTTO_DA, dataPubblicazione_da );
                    currentActiveSession.setAttribute(ParametriServlet.SESSION_DATA_PUBBLICAZIONE_LOTTO_A, dataPubblicazione_a );
                    currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_CF_OPERATORE, cfRUP );
                    currentActiveSession.setAttribute(ParametriServlet.FIELD_NAME_CODICE_CC, idCentro );
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
				
				dataPubblicazione_da = (String)currentActiveSession.getAttribute(SESSION_DATA_PUBBLICAZIONE_LOTTO_DA);		
				dataPubblicazione_a = (String)currentActiveSession.getAttribute(SESSION_DATA_PUBBLICAZIONE_LOTTO_A);	
				cfRUP = (String)currentActiveSession.getAttribute(ParametriServlet.FIELD_NAME_CF_OPERATORE);		
				idCentro  = (String)currentActiveSession.getAttribute(ParametriServlet.FIELD_NAME_CODICE_CC);
				fromRicerca = (String)currentActiveSession.getAttribute(ParametriServlet.FROM_RICERCA);
			
//				request.setAttribute(ParametriServlet.FROM_RICERCA, fromRicerca);		
//				request.setAttribute(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_START, dataPubblicazione_da );
//				request.setAttribute(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_END, dataPubblicazione_a );
//				request.setAttribute(ParametriServlet.FIELD_NAME_CF_OPERATORE, cfRUP );
//				request.setAttribute(ParametriServlet.FIELD_NAME_CODICE_CC, idCentro );
				
				try {
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
                    // controllo parametri immessi
                    GenericUtilValidator val = new GenericUtilValidator(currentActiveConnection, logger);
					
	                Hashtable listaSARiferimento = new Hashtable();
	                if(!val.isEmpty(idCentro)){
	                   listaSARiferimento.put(idCentro, idCentro);
	                }
					 
//					if(val.isEmpty(oggettoGara) && val.isEmpty(oggettoLotto) && val.isEmpty(cig) && val.isEmpty(idStazioneAppaltante)
//						&& val.isEmpty(numeroGara) && val.isEmpty(dataScadenza_a) && val.isEmpty(dataScadenza_da) 
//						&& val.isEmpty(dataPubblicazione_a) && val.isEmpty(dataPubblicazione_da)  
//						&& val.isEmpty(id_soglia) && val.isEmpty(richiestaAnnullamento) && val.isEmpty(richiestaAggiudicate)
//						&& val.isEmpty(cfAmm) && val.isEmpty(cfRUP) && val.isEmpty(soloMie)
//					)
//					{
//						sendError(request, response, Messaggi.SIMOG_RIC_003, currentUser.isAmministratore() ? JSP_GESTIONE_GARE_EXT : JSP_GESTIONE_SCHEDE);
//						return;
//					}

					
					// SE LE DATE DI PUBBLICAZIONE SONO inserite e sbagliate
					if ( (dataPubblicazione_da != null && dataPubblicazione_a != null ) && dataPubblicazione_a.compareTo( dataPubblicazione_da ) < 0 ) 
					{
						sendError(request, response, SIMOG_GARA_011, "paramElencoCig.jsp");
						return;
					}
					
					
					TableBean garaList = null;
					
					GaraManager garaManager = new GaraManager(currentActiveConnection, logger);		
					
					garaList = garaManager.getElencoCig(currentUser.getAmministrazioni(),
					                 cfRUP, listaSARiferimento, dataPubblicazione_a,
							         dataPubblicazione_da, startRow, configuration.getMaxElementiPerPagina());
					
					if ( garaList.size() == 0 ) {
						sendMessage(request, response, Messaggi.SIMOG_RIC_001, "paramElencoCig.jsp");
						return;
					} 
					else {			
						
						 TableBeanRow currentRow = null;
						 String idGara = "";
						 for ( int rowIndex = 0; rowIndex < garaList.getTableSize(); rowIndex++ ) {
					    		currentRow = garaList.getRow(rowIndex); 
					    		idGara = currentRow.getNulledField(LOTTO.ID_GARA);
					    		String currentCIG = currentRow.getNulledField(LOTTO.CIG) + currentRow.getNulledField(LOTTO.CIG_KKK);
						 }
						
						request.setAttribute(ParametriServlet.TABLEBEAN, garaList);
						request.setAttribute(ParametriServlet.START_ROW, new Integer(startRow) );
						request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getELEMENTI_VISUALIZZATI());
						
						forward("visElencoCig.jsp", request, response);
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
