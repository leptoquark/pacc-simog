package it.avlp.simog.transazioni;

import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SrvVisualizzaTransazioni extends ServletBase {
	
	public static final long serialVersionUID = 1;
	
	public void doGet(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException{
		perform( request, response);
	}

	protected void perform(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		visualizzaListaParametriValori(request, response);
		
		if ( checkSession(request) ) {
			if ( currentUser.isAVLP() || currentUser.isRSSAorRUP() ) {
				
				String id_gara = request.getParameter( ParametriServlet.SESSION_ID_GARA );
				
				String pageCalling = currentUser.isAVLP() ? JSP_RICERCA_TRANSAZIONI : ParametriServlet.SRV_VISUALIZZA_DETTAGLIO + "?" + ParametriServlet.SESSION_ID_GARA +"=" + id_gara;
				
				String idLotto = request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO);
				
				String cfSARiferimento = null;
				String cfRSSA = null;
				String cfAmministrazione = null;
				String cigLotto = null;
				String fromDataPub = null;
				String toDataPub = null;
				String fromDataScad = null;
				String toDataScad = null;
				String idGara = null;					//UN idGara si riferisce al filtro di ricerca transazioni (AVLP)
				boolean includiNonPagati = false;
				
				logger.debug(" ---- id_gara [" + id_gara +"] ----");
				
				if ( id_gara == null ) {			//UN Se valorizzato provengo da dettaglio Gara
					// recupero e normalizzazione dei filtri nominali;
					cfSARiferimento = request.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
					cfRSSA = request.getParameter(ParametriServlet.FIELD_NAME_CF_OPERATORE);
					cfAmministrazione = request.getParameter(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
					cigLotto = request.getParameter(ParametriServlet.FIELD_NAME_CIG);
					idGara = request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA);
					logger.debug(" ---- idGara [" + idGara +"] ----");
					
					cfSARiferimento = ( cfSARiferimento == null ) ? cfSARiferimento : cfSARiferimento.trim();
					cfRSSA = ( cfRSSA == null ) ? cfRSSA : cfRSSA.trim();
					cfAmministrazione = ( cfAmministrazione == null ) ? cfAmministrazione : cfAmministrazione.trim();
					includiNonPagati = request.getParameter("includiNonPagati") != null;
					
					if ( cigLotto != null ) {
						cigLotto = cigLotto.trim();
						if ( cigLotto.length() != 0 && cigLotto.length() != 10 ) {
							sendError(request, response, "Verificare la correttezza del CIG", pageCalling );
							return;
						}
					}
					
					if ( idGara != null ) {
						idGara = idGara.trim();
						if ( idGara.length() != 0 && !PageHelper.isNumeric(idGara) ) {
							sendError(request, response, "Verificare la correttezza di ID Gara", pageCalling );
							return;
						}
						id_gara = idGara;
					}
					
					// controlla che ci sia almeno un filtro nominale 
					
					/*********************************
					 * Viene eliminato questo controllo 
					 * su richiesta di AVCP
					 * 
					boolean testImmissione = cfSARiferimento.length() > 0 || cfRSSA.length() > 0 || cfAmministrazione.length() > 0 || cigLotto.length() > 0;
					
					if ( ! testImmissione ) {
						sendError(request, response, Messaggi.SIMOG_TRS_001, JSP_RICERCA_TRANSAZIONI);
						return;
					}
					*/
	
					// recupero filtri temporali
					fromDataPub =
						request.getParameter(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_AAAA_START)
						+ request.getParameter(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_MM_START)
						+ request.getParameter(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_DD_START);
					
					toDataPub =
						request.getParameter(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_AAAA_END)
						+ request.getParameter(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_MM_END)
						+ request.getParameter(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_DD_END);
			
			
					fromDataScad =
						request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA_AAAA_START)
						+ request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA_MM_START)
						+ request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA_DD_START);
					
					toDataScad = 
						request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA_AAAA_END)
						+ request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA_MM_END)
						+ request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA_DD_END);
					
					// esecuzione query
				}
				
				try {
					String selectedField = request.getParameter(ParametriServlet.ORDER_FIELD);
					currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
					TransazioniManager tManager= new TransazioniManager(currentActiveConnection,logger);
					
					TableBean results = null;
					
					//logger.debug("--- idLotto : [" + idLotto + "] ---");
					
					
					if ( currentUser.isAVLP() ) {		// Ricerca Transazioni
						logger.debug("--- AVLP eseguo getFullTransazioni ---");
						results = tManager.getFullTransazioni( cfSARiferimento, cfRSSA, cigLotto, cfAmministrazione, 
								id_gara, fromDataPub, toDataPub, fromDataScad, toDataScad, selectedField, includiNonPagati );
						// XXX Se il numero di transazioni supera il massimo consentito viene lanciato un avviso
						if(results.getFullSize() >= TransazioniManager.MAX_TRANSAZIONI){ 
							sendError(request, Messaggi.SIMOG_RIC_002.replace("$1", String.valueOf(TransazioniManager.MAX_TRANSAZIONI)));
						}
					}
					else if (currentUser.isRSSAorRUP()) {
						if ( idLotto != null ) {		// La ricerca dei pagamenti relativi al lotto (DettaglioGara)
							logger.debug("--- RSSA eseguo getTransazioni ---");
							results = tManager.getTransazioni( idLotto, cfAmministrazione, selectedField );								
						}
						else if ( id_gara != null ) {	// La ricerca pagamenti relativi alla gara (DettaglioGara)
							logger.debug("--- RSSA eseguo getTransazioniGara ---");
							results = tManager.getTransazioniGara( id_gara, cfAmministrazione, selectedField );									
						}
						else {							// Se id_lotto e id_gara sono null, c'è qualcosa che non va
							sendError ( request, response, SIMOG_TRS_001, JSP_GESTIONE_GARE_RSSA );
							return;							
						}
					}
					
					
					if ( results.getTableSize() > 0 ) {
						if ( request.getParameter("CSV") != null ) {
							performCSV( request, response, results);
						} else {
							request.setAttribute(TABLEBEAN, results);
							forward(JSP_VISUALIZZA_TRANSAZIONI, request, response);	
						}
					} else {
						sendMessage(request, response, Messaggi.SIMOG_RIC_001, pageCalling);
						return;
					}
				} catch(Exception e){
					logger.fatal("ERRORE : in SrvVisualizzaTransazioni " + e);
					sendError ( request, response, SIMOG_TRS_001, pageCalling, e );
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
	
		
	/*****************************************************************
	 * Si occupa della scrittura del CSV basata sulla TableBan result
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param results TableBean
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performCSV( HttpServletRequest request, HttpServletResponse response, TableBean results ) throws ServletException, IOException{
			logger.debug("--- eseguo performCVS ---");
			response.setContentType("application/x-download; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\"Transazioni.csv\"");
			results.writeCSV(response.getWriter(),';');
			response.getWriter().flush();
			response.getWriter().close();
	}

}
