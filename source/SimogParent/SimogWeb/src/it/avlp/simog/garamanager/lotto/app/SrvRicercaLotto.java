package it.avlp.simog.garamanager.lotto.app;

import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.lotto.RicercaLottoManager;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for Servlet: SrvRicercaLotto
 *
 */
 public class SrvRicercaLotto extends it.avlp.simog.servlet.ServletBase implements javax.servlet.Servlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection currentActiveConnection = null;
		String cig = request.getParameter(ParametriServlet.FIELD_NAME_CIG);
		
		String data1 = request.getParameter(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_AAAA_START)
			+ request.getParameter(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_MM_START)
			+ request.getParameter(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_DD_START);
		
		String data2 = request.getParameter(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_AAAA_END)
			+ request.getParameter(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_MM_END)
			+ request.getParameter(ParametriServlet.FIELD_NAME_DATA_PUBBLICAZIONE_DD_END);
		
		String data3 = request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA_AAAA_START)
			+ request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA_MM_START)
			+ request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA_DD_START);
	
		String data4 = request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA_AAAA_END)
			+ request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA_MM_END)
			+ request.getParameter(ParametriServlet.FIELD_NAME_DATA_SCADENZA_DD_END);
		
		try {
			
			currentActiveConnection = this.getSimogConnection(request.getSession().getId(),getClass().getName());
			RicercaLottoManager lotto = new RicercaLottoManager(currentActiveConnection, logger);
			
			java.util.TreeMap map = lotto.getDettagliGara(cig, data1, data2, data3, data4);
			TableBean tab = lotto.getDettagliLotto(cig, data1, data2, data3, data4);
			TableBean docs = lotto.getDocumenti(cig, data1, data2, data3, data4);
			
			request.setAttribute("infogara", map);
			request.setAttribute("infolotto", tab);
			request.setAttribute("docs", docs);
			
			if(tab.getTableSize() == 0) {
				sendMessage(request, response, Messaggi.SIMOG_RIC_001 , JSP_RICERCA_DOCUMENTI_LOTTO);
				return;
			} else {
				forward(JSP_RICERCA_DOCUMENTI_LOTTO_ESITO, request, response);
			}
		} catch(Exception e){
			sendError(request, response,Messaggi.SIMOG_LOTTO_005 , JSP_RICERCA_DOCUMENTI_LOTTO, e);
			return;
		} finally{
			closeConnection(request.getSession().getId(),getClass().getName());
		}
	}
    
	 
}