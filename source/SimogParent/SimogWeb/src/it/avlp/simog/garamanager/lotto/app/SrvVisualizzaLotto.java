package it.avlp.simog.garamanager.lotto.app;

import it.avlp.simog.beans.Utente;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Deprecated
public class SrvVisualizzaLotto extends ServletBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see ServletBase#perform(HttpServletRequest, HttpServletResponse)
	 */
	
	public void perform(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		if ( checkSession(request) ) {
		
			try {
				currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
			
				long idLotto = Long.parseLong(request.getParameter("idLotto"));
				LottoManager lottoManager = new LottoManager( currentActiveConnection, logger);
		
				TableBean infoLotto = null;

				
				/*
				 * Un RSSA visualizza solo i lotti di competenza
				 */
				if ( currentUser.isRSSAorRUP() ) {
					lottoManager.getLottoByIdLottoRSSA( idLotto, currentUser.getUffici() );
				} else {
					/*
					 * Amministratore e AVCP visualizzano i dettagli relativi a tutti i lotti
					 */
					lottoManager.getLottoByIdLottoAmm( idLotto );					
				}
				request.setAttribute(TABLEBEAN, infoLotto);
				// PP messa data corrente ma forse la classe non e' usata
				setTabelleUtilita(request, currentActiveConnection, PageHelper.getCurrentDate(), false, null);
			
				forward(JSP_MODIFICA_LOTTO, request, response);

			} catch (Exception e) {
				sendError(request, response, SIMOG_LOTTO_011, JSP_ERRORE, e );
				return;
			} finally {
				closeConnection(request.getSession().getId(),getClass().getName());
			}	
		} else {
			sendError(request, response, SIMOG_LOGIN_003, JSP_ERRORE);
			return;
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		perform(request, response);
//	}
}

