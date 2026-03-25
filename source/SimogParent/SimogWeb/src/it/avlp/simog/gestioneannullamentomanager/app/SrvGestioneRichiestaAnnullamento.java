package it.avlp.simog.gestioneannullamentomanager.app;

import it.avlp.simog.actions.GenericAction;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.action.BaseRichiestaAnnullamento;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletRichAnnullamento;
import it.avlp.simog.common.servlet.ParametriServletRubrica;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.factory.AnnullamentoFactory;
import it.avlp.simog.servlet.ServletBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class for Servlet: SrvGestioneRichiestaAnnullamento
 *
 */
 public class SrvGestioneRichiestaAnnullamento extends ServletBase {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = -2187811152695516370L;

	public SrvGestioneRichiestaAnnullamento() {
		super();
	}   	
		
	public void doGet(HttpServletRequest request,HttpServletResponse response)
 	throws ServletException, IOException {
 		
 		perform(request, response);
 	}	
	
	/******************************************************************
	 * Effettua un check sull'operazione. Se questa risulta uguale a checkValue 
	 * viene restituito True, False altrimenti
	 * @param request HttpServletRequest
	 * @param checkValue String
	 * @return boolean
	 */
	private boolean switchOperation(HttpServletRequest request,  String checkValue){
 		String parameterValue = request.getParameter(ParametriServletRubrica.OPERAZIONE);
 		return (parameterValue!=null && checkValue.equals(parameterValue));
 	}

	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if ( checkSession(request) ) {
			if ( currentUser.isAmministratore() || currentUser.isOssReg() || currentUser.isRASA()){
				visualizzaListaParametriValori(request, response);
				if (switchOperation(request,"view") || switchOperation(request, "Torna") ||switchOperation(request,"Visualizza") ){
 					logger.debug("Richieste annullamento - il parametro d'ingresso e' parameter = view" );
 					logger.debug("Passo nell'if VIEW");
 					performView(request,response);
				}else if (switchOperation(request, "viewDetail")){
 					logger.debug("SrvGestioneRichiestaAnnullamento - l'operazione e' viewDetail e il parametro d'ingresso e' parameter = idRichiesta");
 					performViewDetail(request, response);
				}else if (switchOperation(request, "Salva")){
	 				logger.debug("SrvGestioneRichiestaAnnullamento - l'operazione e' Salva *****************" );
	 			
	 				
	 				performSave(request, response);
	 			
	 			}else if (switchOperation(request, "viewChoose")){
	 				
	 				logger.debug("SrvGestioneRichiestaAnnullamento - l'operazione e' viewChoose *****************" );
	 				performViewChoose(request, response);
	 			}
			}else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_004, JSP_ERRORE );
			}
		}else {
			sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE );
		}
	}
	
    /********************************************************************
     * gestisce il salvataggio 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @SuppressWarnings("unused")
	public void performSave(HttpServletRequest request, HttpServletResponse response)
 	throws ServletException, IOException {		
		visualizzaListaParametriValori(request, response);
		
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		
		try{
			
			
			int numRow=-1;
			logger.debug("sono nel performSave");
			String blocco = request.getParameter(ParametriServletRichAnnullamento.FIELD_NAME_BLOCCO);
			
			currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
			currentActiveConnection.setAutoCommit(false);
			BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(blocco, currentActiveConnection, logger);
			
			GenericAction gen = new GenericAction (currentActiveConnection, logger);
			
			RichiestaAnnullamento richAnnullamento = gen.getAnnullamentoBean(request);
			checkValueParameter(request,ParametriServletRichAnnullamento.FIELD_NAME_ESITO,"");
			
			// PP aggiunto decisore
			if(richAnnullamento.getMotivo_esito() != null)
				richAnnullamento.setDecisore(currentUser.getLogin());
			
			boolean result = annAction.gestisciRichiesta(richAnnullamento,currentUser.getLogin());
	
			commit(currentActiveConnection);
			setMessage ( request, Messaggi.SIMOG_RICHIESTA_ANNULLAMENTO_002);
			forward("richiestaAnnullamento?"+it.avlp.simog.common.servlet.ParametriServletRichAnnullamento.OPERAZIONE+"=view&"+ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA+"="+ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA_DA_VALUTARE, request, response);
			
			
			
			
		}catch(SQLException sqle){
				rollback(currentActiveConnection);
				sendError(request, response, sqle.getMessage(), ParametriServletRichAnnullamento.JSP_DETTAGLIO_RICHIESTE_ANNULLAMENTO, sqle);
			}catch(ClassNotFoundException cnfe){
				rollback(currentActiveConnection);
				sendError(request, response, cnfe.getMessage(), ParametriServletRichAnnullamento.JSP_DETTAGLIO_RICHIESTE_ANNULLAMENTO, cnfe);
			}catch(Exception e){
				rollback(currentActiveConnection);
				sendError(request, response, e.getMessage(), ParametriServletRichAnnullamento.JSP_DETTAGLIO_RICHIESTE_ANNULLAMENTO, e);
			}finally {
				closeConnection(request.getSession().getId(),getClass().getName());
			}
			
		
		}

	
	/***********************************************************************************
	 * Effettua un forward all pagina di richieste annullamento e stampa su log 
	 * i parametri della request
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performViewChoose(HttpServletRequest request, HttpServletResponse response)
 	throws ServletException, IOException{
 			
		visualizzaListaParametriValori(request, response);
		forward(ParametriServletRichAnnullamento.JSP_RICHIESTE_ANNULLAMENTO, request, response);
		
	}
	
	/***********************************************************************************
	 * Effettua il forward alla pagina di richieste annullamento 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performView(HttpServletRequest request, HttpServletResponse response)
 	throws ServletException, IOException {
		Connection currentActiveConnection = null;				
		visualizzaListaParametriValori(request, response);

		try {
			Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
			currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
			BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(null, currentActiveConnection, logger);
			String scelta = request.getParameter(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA);
			
			if(scelta==null || "".equals(scelta))
				scelta = (String) request.getAttribute(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA);
			
			String cig_lotto = request.getParameter(ParametriServletRichAnnullamento.FIELD_NAME_FILTRO_CIG);	

			//Verifica della lunghezza del CIG 
			if(cig_lotto!=null && !"".equals(cig_lotto) && cig_lotto.length() != 10){
				sendError(request, response, Messaggi.SIMOG_GARA_010, ParametriServletRichAnnullamento.JSP_RICHIESTE_ANNULLAMENTO);
				return;
			}
			
			String orderField = request.getParameter(ParametriServlet.ORDER_FIELD);
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
			//if(orderField!=null && !"".equals(orderField))
			//	scelta = "Visualizza";
			
			TableBean richiesteAnnullamento = new TableBean();
			richiesteAnnullamento = annAction.getRichiesteAnnullamento(scelta, cig_lotto, currentUser.getAdminOr(), 
					orderField,startRow,configuration.getPaginazioneLogAndRichA(),false); 
			if ( richiesteAnnullamento.size() == 0 ) {
				sendMessage(request, response, Messaggi.SIMOG_RIC_001,ParametriServletRichAnnullamento.JSP_RICHIESTE_ANNULLAMENTO);
			} else {
				request.setAttribute(ParametriServlet.TABLEBEAN, richiesteAnnullamento);
				/**----------------------------*/
				request.setAttribute(ParametriServlet.START_ROW, new Integer(startRow) );
				request.setAttribute(ParametriServlet.CONFIG_ELEMENTI_VISUALIZZATI, configuration.getPaginazioneLogAndRichA());					
				request.setAttribute(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA,scelta);
				request.setAttribute(ParametriServletRichAnnullamento.FIELD_NAME_FILTRO_CIG ,cig_lotto);
				request.setAttribute(ParametriServlet.ORDER_FIELD,orderField);
				/**----------------------------*/
				forward(ParametriServletRichAnnullamento.JSP_RICHIESTE_ANNULLAMENTO, request, response);
			}
		} catch ( Exception sqle ) {
			sendError(request, response, sqle.getMessage(), ParametriServletRichAnnullamento.JSP_RICHIESTE_ANNULLAMENTO, sqle);
		}finally {
			closeConnection(request.getSession().getId(),getClass().getName());
		}
	}
	
	
	/*************************************************************************************
	 * Effettua il forward alla pagina per la visualizzazione dei 
	 * dettagli delle richieste di annullamento
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	public void performViewDetail(HttpServletRequest request, HttpServletResponse response)
 	throws ServletException, IOException {
		
		visualizzaListaParametriValori(request, response);
		int idRichiesta=0;
		Connection currentActiveConnection = null;

		try {
			idRichiesta = Integer.parseInt(request.getParameter(ParametriServletRichAnnullamento.FIELD_NAME_ID_RICHIESTA));
			currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
			BaseRichiestaAnnullamento annAction = AnnullamentoFactory.getAction(null, currentActiveConnection, logger);
			
			TableBean dettaglioRicAnnullamento = null;
			
			dettaglioRicAnnullamento = annAction.getDettaglioRichiestaAnnullamento(idRichiesta);
			
			if ( dettaglioRicAnnullamento.size() == 0 ) {
				sendMessage(request, response, Messaggi.SIMOG_RIC_001, ParametriServletRichAnnullamento.JSP_RICHIESTE_ANNULLAMENTO);
			} else {		
				request.setAttribute(ParametriServletRichAnnullamento.DETTAGLIO_TABLEBEAN, dettaglioRicAnnullamento);
				forward(ParametriServletRichAnnullamento.JSP_DETTAGLIO_RICHIESTE_ANNULLAMENTO, request, response);
			}
		} catch ( Exception sqle ) {
			sendError(request, response, sqle.getMessage(), ParametriServletRichAnnullamento.JSP_RICHIESTE_ANNULLAMENTO, sqle);
		} finally {
			closeConnection(request.getSession().getId(),getClass().getName());
		}
	}
	
	
	/*****************************************************************************
	 * metodo che controlla se sono state valorizzati i campi obbligatori 
	 * @param request HttpServletRequest
	 * @param parameter String
	 * @param label String
	 * @return boolean
	 * @throws Exception
	 */
	private boolean checkValueParameter(HttpServletRequest request, String parameter, String label) throws Exception{
		
		boolean check = true;
		String strParam = "";	
		strParam = request.getParameter(parameter);
		if (parameter.equals(ParametriServletRichAnnullamento.FIELD_NAME_SCELTA_LISTA) && strParam==null ) {
			check = false;
			throw new Exception(Messaggi.SIMOG_RICHIESTA_ANNULLAMENTO_003);
		}
		if (parameter.equals(ParametriServletRichAnnullamento.FIELD_NAME_ESITO) && strParam==null ) {
			check = false;
			throw new Exception(Messaggi.SIMOG_RICHIESTA_ANNULLAMENTO_003);
		}
		return check;
	}
	
}