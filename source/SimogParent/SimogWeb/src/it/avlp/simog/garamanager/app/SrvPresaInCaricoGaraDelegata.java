package it.avlp.simog.garamanager.app;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avlp.simog.beans.EsitoEnum;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.StazioneAppaltante;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.log.LogManager;
import it.avlp.simog.servlet.ServletBase;

/**
 * Servlet implementation class SrvPresaInCaricoGaraDelegata
 */
public class SrvPresaInCaricoGaraDelegata extends ServletBase implements ParametriServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);

		Connection currentActiveConnection = null;
        // int isolationLevel = 0;
		try{
			
			if ( checkSession(request) ) {
				
				currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());	
				currentActiveConnection.setAutoCommit(false);
				
				if ( currentUser.isRSSAorRUP()) {
					
					
					String action = request.getParameter("action");
					String idGara = request.getParameter(ParametriServlet.SESSION_ID_GARA);

					if(action.equals("load")) {
						 GaraManager gm = new GaraManager(currentActiveConnection, logger);	
						String cfAmmDelegante = gm.getCfAmmDelegata(Long.parseLong(idGara),0,currentUser.getAmministrazioni());
						request.setAttribute(ParametriServlet.SESSION_ID_GARA, idGara);
						request.setAttribute(ParametriServlet.CF_AMM_DELEGANTE, cfAmmDelegante);
                        String redirectUrl = JSP_PRESA_IN_CARICO_GARA_DELEGATA+"?"+ParametriServlet.SESSION_ID_GARA+"="+idGara;	
						sendMessage(request, response, SIMOG_GARA_026, redirectUrl);
						
					} else if(action.equals("save")) {

						String stazioneAppaltanteSelezionata = getSASelected(request);
						StazioneAppaltante currentSA =null;
						 for ( java.util.Enumeration e = currentUser.getUfficiByProfilo(currentUser.getProfiloEnum()).elements(); e.hasMoreElements(); ) {
							  currentSA =  (StazioneAppaltante)e.nextElement();
							 if(currentSA.getIdUfficio().equals(stazioneAppaltanteSelezionata))
								 break;
						 }
						
						 if(currentSA!=null && !"".equals(idGara)) {
							 GaraManager gm = new GaraManager(currentActiveConnection, logger);	
							 LottoManager lottoman = new LottoManager(currentActiveConnection,logger);
							 LogManager lm = new LogManager(currentActiveConnection,logger);
							 InfoComuniManager icm = new InfoComuniManager(currentActiveConnection, logger);
							 gm.eseguiPresaInCaricoGaraDelegata(currentSA,currentUser.getLogin(),idGara);
                             gm.eseguiPresaInCaricoInfoAggiudicazioni(currentSA,currentUser.getLogin(),idGara);
							 
                             //Se la delega e' proposta di aggiudicazione e le schede dati comuni sono con esito 'proposta di aggiudicazione',
                             //le schede vanno rimesse in stato In Definizione
                             int idf = gm.getGara(Long.parseLong(idGara)).getID_F_DELEGATE();
                             if(idf==Costanti.IDF_PROPOSTA_AGGIUDICAZIONE) {
	                             List<Lotto> lotti = lottoman.getListaLotti(Long.parseLong(idGara));
								 
								 for(Lotto lotto : lotti) {
									InfoComuniBean infoComuni = icm.getInfoComuniByCig(lotto.getFullCIG());
									if(infoComuni!=null && EsitoEnum.PROPOSTA_AGGIUDICAZIONE.codice().equals(infoComuni.getEsitoProcedura())) {
										icm.updateStato(infoComuni, (long)StatiScheda.IN_DEFINIZIONE);
									}
								 }
                             }
                             
							 gm.setDataPresaInCaricoDelega(Long.parseLong(idGara));
							 lm.log(getTodayDate(), stazioneAppaltanteSelezionata, currentUser.getLogin(), "", LogManager.UPD_PRESA_CARICO_DELEGATA, currentSA.getAmministrazione().getCodiceFiscale(), "", idGara );
							 commit(currentActiveConnection);
								request.setAttribute(ParametriServlet.SESSION_ID_GARA, idGara);
								String redirectUrl = JSP_GESTIONE_SCHEDE;
								sendMessage(request, response, SIMOG_GARA_027.replace("$1",idGara).replace("$2", currentSA.getAmministrazione().getDenominazioneAmministrazione()+" - "+currentSA.getDenominazione()) , redirectUrl);
							 
						 } else {
							 sendError(request, response, Messaggi.SIMOG_VALIDAZIONE_000, JSP_GESTIONE_SCHEDE );
							 return;
						 }

				
					}
					
				}
	
			} else {
				sendError(request, response, Messaggi.SIMOG_LOGIN_003, JSP_ERRORE);
				return;
			}	
		}catch (Exception e) {
			e.printStackTrace();
			sendError(request, response, Messaggi.SIMOG_VALIDAZIONE_000, JSP_GESTIONE_SCHEDE );
			return;
		} finally {
			closeConnection(request.getSession().getId(),getClass().getName());
		}
		
	}
	
	private String getSASelected(HttpServletRequest request) {
		return request.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
	}
	
}
