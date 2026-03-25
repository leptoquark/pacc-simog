package it.avlp.simog.garamanager.lotto.app;


import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avlp.simog.actions.GaraLottoAction;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.actions.BandoGaraAction;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.log.LogManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.validatore.LottoValidator;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SrvPerfezionaLotto extends ServletBase {

	private static final long serialVersionUID = -4427708044851647738L;


	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
	throws ServletException, IOException {
		
		perform(request, response);
}
	
	
	public void perform(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		logger.debug("Richiesta Perfezionamento Lotto");
		
		if ( checkSession(request) && (currentUser.isRSSAorRUP() || currentUser.isAmministratore())) {
			visualizzaListaParametriValori(request, response);
			try {
				currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());				
				currentActiveConnection.setAutoCommit(false);

				GaraLottoAction gla = new GaraLottoAction(currentActiveConnection,logger, configuration);
				String idLotto = request.getParameter(ParametriServlet.FIELD_NAME_ID_LOTTO);
				String idGara= request.getParameter(SESSION_ID_GARA);
				String requestingURL = ParametriServlet.SRV_GESTISCI_LOTTO + "?action=perfeziona&idLotto=" + idLotto;
				Object o = null;

				if( SimogFlags.is3030_RFWEBGL00Active() ){
				   Lotto lotto = gla.loadLottoPerfFromRequest(request);
				   o = gla.validaPerfezionamentoLottoAdmin(lotto);
				} else {
				   o = gla.loadValidLottoPerfFromRequest(request);
				}
				
				if(o instanceof AllValidationBeans){
					sendValidations(request, response, (AllValidationBeans)o, requestingURL);
					return;
				}

//				 messaggi da ritornare
	    		AllValidationBeans msgs = new AllValidationBeans();
	    		
				Lotto lotto = (Lotto)o;
				boolean esito = gla.perfezionaLotto(lotto, msgs);
				if(!esito){
					rollback(currentActiveConnection);
					logger.error ( "perfezionamento o validazione Lotto fallita" );
					sendError(request, response, "Perfezionamento del lotto fallita", requestingURL);
					return;
				}			

				LogManager logManager = new LogManager(currentActiveConnection, logger);
				
				String cfSARiferimento = request.getParameter(FIELD_NAME_ID_STAZIONE_APPALTANTE);
				String cfAmministrazione = request.getParameter(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
				String cfUtente = currentUser.getLogin();
				String cig_part = request.getParameter(ParametriServlet.FIELD_NAME_CIG);
				String cig_kkk = request.getParameter(ParametriServlet.FIELD_NAME_CIG_KKK);
				
				String cig = cig_part + cig_kkk;
				
				logManager.log(getTodayDate(), cfSARiferimento, cfUtente, cig, LogManager.PERF_LOTTO, cfAmministrazione, idLotto, idGara );
			
				String forwardPath =SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara
				+ "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;
				
				/** commit */
				commit(currentActiveConnection);
				
				msgs.addValidationInfo(SIMOG_LOTTO_018);
				
				sendValidations(request, response, msgs, forwardPath);

				return;
			} catch ( Exception sqle ) {
				rollback(currentActiveConnection);
				String urlPerRiproposizioneCig = JSP_ERRORE; // JSP_GESTIONE_SCHEDE+"?"+FIELD_NAME_CIG_RIPROPOSIZIONE+"="+request.getParameter(FIELD_NAME_CIG)+request.getParameter(FIELD_NAME_CIG_KKK);
				sendError(request, response, sqle.getMessage(), urlPerRiproposizioneCig, sqle);
				//sqle.printStackTrace();
				return;
			} finally {
				//rollbackOrcommit(currentActiveConnection);
				closeConnection(request.getSession().getId(),getClass().getName());
			}	
		} else {
			sendError(request, response, SIMOG_LOGIN_004, JSP_ERRORE);
			return;
		}
	}
	
	
	/*************************************************************
	 * Controlla la validita' di una data
	 * @param data la data nel formato yyyyMMdd
	 * @return boolean
	 */
	
//	public boolean validaData(String data){
//		
//		int giorni[] ={31,28,31,30,31,30,31,31,30,31,30,31};
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		
//		try{
//			
//			Date d = sdf.parse(data);
//			Calendar cal = sdf.getCalendar();
//			
//			int a = cal.get(Calendar.YEAR);
//			int m = cal.get(Calendar.MONTH);
//			int g = cal.get(Calendar.DAY_OF_MONTH);
//			
//			if(a%4 == 0)
//				giorni[1] = 29;
//				
//			
//			if(m<0 || m>11)
//				return false;
//			
//			if(g > giorni[m] || g<1)
//				return false;
//			
//			return true;
//			
//		}
//		
//		catch(Exception e){
//			e.printStackTrace();
//			return false;
//		}
//
//	}
	
}
