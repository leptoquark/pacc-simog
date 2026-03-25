package it.avlp.simog.garamanager.app;

import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.MotivazioniBean;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.action.RequisitiGLAction;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletLotto;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.log.LogManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SrvCancellaGara extends ServletBase {

	private static final long serialVersionUID = -4427708044851647738L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		perform(request, response);
	}

	public void perform(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection currentActiveConnection = null;
		Utente currentUser = (Utente) request.getSession().getAttribute(UTENTE);
		visualizzaListaParametriValori(request, response);


		if (checkSession(request) && (currentUser.isAmministratore() || currentUser.isRSSAorRUP())) {
			try {
				currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
				
				visualizzaListaParametriValori(request, response);
				
				
				String idGara = request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA);
				String idStato = request.getParameter(ParametriServlet.FIELD_NAME_ID_STATO_GARA);
				boolean pubblicato = false; //PP ex riscossioneboolean pubblicato = (idStato != null && "Confermato".equalsIgnoreCase(idStato)) ? true : false;
				
				String cfSARiferimento = request.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
					   cfSARiferimento = cfSARiferimento != null ? cfSARiferimento : ""; 
				String cfAmministrazione = request.getParameter(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
					   cfAmministrazione = cfAmministrazione != null ? cfAmministrazione : ""; 
				String cfUtente = currentUser.getLogin();
				String id_motivazione = request.getParameter(ParametriServletLotto.FIELD_NAME_MOTIVAZIONE); 
				String note_canc = request.getParameter(ParametriServletLotto.FIELD_NAME_NOTE);

				//Validazione Motivazione/Note 
				String errorUrl = SRV_INIZIALIZZA_GARA + "?action=cancella&" + ParametriServlet.SESSION_ID_GARA + "=" + idGara;

				MotivazioniBean motiviCanc = new MotivazioniBean();
				motiviCanc.loadAll(currentActiveConnection, logger, true);
				
				if(id_motivazione == null || id_motivazione.trim().length() == 0){
					sendError(request, response, Messaggi.SIMOG_LOTTO_023.replace("$1","Motivazione"), errorUrl );
					return;
				} 
				else if(motiviCanc.isNotaObbligatoria(id_motivazione) && (note_canc == null || note_canc.trim().length() == 0)){
					sendError(request, response, Messaggi.SIMOG_LOTTO_023.replace("$1","Nota"), errorUrl );
					return;
				}
				if (note_canc.trim().length() > 1000){	//maxlength campo note 1000 caratteri
					sendError(request, response, Messaggi.SIMOG_VALIDAZIONE_184.replace("$1", "Note").replace("$2", "1000"), errorUrl );
					return;					
				}
				

				logger.debug("SrvCancellaGara : perform - sto per chiamare GaraManager (id_gara=" + idGara + ")");
				LogManager logManager = new LogManager(currentActiveConnection, logger);
				GaraManager garaManager = new GaraManager( currentActiveConnection, logger);
				
				// INTRODUZIONE BLOCCO CANCELLAZIONE SE ESISTONO FIGLI 23-11-2009 VL
//				
//				Questa cosa non serve al momento dato che una volta inserito un lotto la gara non e' piu cancellabile
//				
//				Gara gara = garaManager.getGara(Long.parseLong(idGara));				
//				int id_modo_real = gara.getID_MODO_REAL();
//				
//				// se e' un'accordo quadro devo controllare l'esistenza di figli
//				if( id_modo_real == Costanti.MODOREAL_ACCORDO ){
//					
//					LottoManager lottoManager = new LottoManager( currentActiveConnection, logger);
//					List<String> listaDiCig = lottoManager.getCigByIdGara(Long.parseLong(idGara));
//					
//					// se la lista ritornata non e' vuota
//					if(!listaDiCig.isEmpty()){
//						// iterazione sui cig per controllo individuale
//						for(String cigCorrente : listaDiCig){
//							
//							// effettuo il controllo
//							boolean esitoCorrente = garaManager.controllaEsistenzaCigFigli(cigCorrente);
//							
//							// se trova un cig con delle dipendenze pagina di errore
//							if(esitoCorrente){	
//								
//								// setta errore
//								sendError(request, response, SIMOG_VALIDAZIONE_191, errorUrl );
//								
//								logger.debug("Cig con dipendenze: " + cigCorrente);
//								logger.debug("lista completa di Cig: " + listaDiCig.toString());
//								
//								return; // interrompi esecuzione metodo
//							}
//						}
//					}
//				}
//				
//				/** FINE CONTROLLO**/
				
				int cancella = garaManager.cancelGara(getTodayDate(), idGara, pubblicato, id_motivazione, note_canc);
				logger.debug("Esito della cancellazione - Aggiornate tuple [" + cancella + "]");
				
                if(SimogFlags.is3025_REQUISITIActive()
                      && configuration.getDataRequisiti().compareTo(PageHelper.getCurrentDate())<=0){
                   // revoca dei requisiti associati alla gara
                   RequisitiGLAction rqa = new RequisitiGLAction(currentActiveConnection, logger);
                   rqa.revocaRequisitiByGara(Long.valueOf(idGara), !SimogFlags.is3028_RNFDBDT01Active());
                }
                
				if( cancella > 0 ){
					logManager.log(getTodayDate(), cfSARiferimento, cfUtente, "", LogManager.DEL_GARA, cfAmministrazione, "", idGara);
					
					String targetPage =SRV_VISUALIZZA_DETTAGLIO + "?" + SESSION_ID_GARA + "=" + idGara
                     + "&" + ParametriServlet.FROM_GARE + "=" + Costanti.FLAG_VALORE_SI;
					sendMessage(request, response, SIMOG_GARA_017 + " ID_GARA [" + idGara + "]", targetPage); //currentUser.isAmministratore() ? JSP_GESTIONE_GARE_EXT : JSP_GESTIONE_SCHEDE);
					return;
				} else {
					sendError(request, response, SIMOG_GARA_017e, currentUser.isAmministratore() ? JSP_GESTIONE_GARE_EXT : JSP_GESTIONE_SCHEDE );
					return;
				}
			} catch (Exception sqle) {
				sendError(request, response, sqle.getMessage(), currentUser.isAmministratore() ? JSP_GESTIONE_GARE_EXT : JSP_GESTIONE_SCHEDE, sqle);
				return;
			} finally {
				closeConnection(request.getSession().getId(),getClass().getName());
			}
		} else {
			sendError(request, response, SIMOG_LOGIN_004, JSP_ERRORE);
			return;
		}

	}
}
