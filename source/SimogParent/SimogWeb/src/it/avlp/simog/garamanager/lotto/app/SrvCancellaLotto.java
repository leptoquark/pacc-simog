package it.avlp.simog.garamanager.lotto.app;

import it.avcp.avcpass.AVCPassAction;
import it.avcp.avcpass.AVCPassFunzioneEnum;
import it.avcp.simog.manager.cup.CupLottoAggManager;
import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avlp.simog.actions.GaraLottoAction;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.MotivazioniBean;
import it.avlp.simog.beans.Utente;
import it.avlp.simog.common.action.RequisitiGLAction;
import it.avlp.simog.common.contributo.GestioneContributoWrapperBeanClient;
import it.avlp.simog.common.contributo.ParametriContributo;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletLotto;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.log.LogManager;
import it.avlp.simog.servlet.ServletBase;
import it.avlp.simog.util.PageHelper;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SrvCancellaLotto extends ServletBase {

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

		

		String redirectUrl = currentUser.isAmministratore() ? JSP_GESTIONE_GARE_EXT : JSP_GESTIONE_SCHEDE;
		
		if(currentUser.isAmministratore())
			redirectUrl = ParametriServlet.SRV_GESTIONE_GARE_EXT ;
		else if(currentUser.isRSSAorRUP())
			redirectUrl = ParametriServlet.SRV_VISUALIZZA_DETTAGLIO ;
		
//		if(request.getSession().getAttribute(ParametriServlet.STORIA_PAGINAZIONE) != null && !"".equals(request.getSession().getAttribute(ParametriServlet.STORIA_PAGINAZIONE))){
//			redirectUrl += "?" + request.getSession().getAttribute(ParametriServlet.STORIA_PAGINAZIONE);
//		}
		//MAC 36255 3.04.8 aggiunto motivoEsclusioneGara
		String motivoEsclusioneGara = null;
		
		if (checkSession(request) && (currentUser.isAmministratore() ||  currentUser.isRSSAorRUP())) {
			try {
				currentActiveConnection = getSimogConnection(request.getSession().getId(),getClass().getName());
				currentActiveConnection.setAutoCommit(false);
				
				visualizzaListaParametriValori(request, response);
				
				String idLotto = request.getParameter("idLotto");
				
				logger.debug("SrvCancellaLotto : perform - sto per chiamare LottoManager (id_lotto=" + idLotto + ")");

				LottoManager lottoManager = new LottoManager( currentActiveConnection, logger);
				
				LogManager logManager = new LogManager(currentActiveConnection, logger);
				
				MotivazioniBean motiviCanc = new MotivazioniBean();
				motiviCanc.loadAll(currentActiveConnection, logger, false);
				
				String cig = request.getParameter(ParametriServlet.FIELD_NAME_CIG);
				String cfSARiferimento = request.getParameter(ParametriServlet.FIELD_NAME_ID_STAZIONE_APPALTANTE);
				String cfAmministrazione = request.getParameter(ParametriServlet.FIELD_NAME_CF_AMMINISTRAZIONE);
				String cfUtente = currentUser.getLogin();
				String idGara = request.getParameter(ParametriServlet.FIELD_NAME_ID_GARA);
				String id_motivazione = request.getParameter(ParametriServletLotto.FIELD_NAME_MOTIVAZIONE); 
				String note_canc = request.getParameter(ParametriServletLotto.FIELD_NAME_NOTE);
				
				//Validazione Motivazione/Note 
				String errorUrl = SRV_GESTISCI_LOTTO + "?action=cancella&idLotto="+idLotto;
				
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

            GaraManager gm = new GaraManager(currentActiveConnection, logger);
            Lotto lottoDb = lottoManager.getLotto(Long.parseLong(idLotto));
            Gara garaDb = gm.getGara(lottoDb.getId_Gara());

// FIXMato: PP cosi no puo funzionare, per ora restano i requisiti, associati al lotto cancellato
             if(SimogFlags.is3025_REQUISITIActive()){
                Timestamp currentDatetime = new AccessiDB(currentActiveConnection, logger).getNow();
                
                //PP devo considerare la data di creazione della gara se esiste
                if(garaDb.getData_creazione() != null)
                   currentDatetime = PageHelper.parseTimeYMD(garaDb.getData_creazione());
   
                // revoca dei requisiti associati al lotto
                RequisitiGLAction rqa = new RequisitiGLAction(currentActiveConnection, logger);
                rqa.revocaRequisitiByLotto(Long.valueOf(idGara), Long.valueOf(idLotto), currentDatetime, false);
             }				
				
				boolean pubblicato = false; // PP ex riscossione request.getParameter("pubblicato") != null;
				int cancella = lottoManager.cancellaLotto(getTodayDate(), idLotto, id_motivazione, note_canc, pubblicato);
				
				if( SimogFlags.is3031_RFWEBGL02Active() && cancella > 0 ){
   				//cancellazione logica CUP
   				CupLottoAggManager claMan = new CupLottoAggManager(currentActiveConnection, logger);
   				claMan.deleteCup(Long.parseLong(idLotto), true);
				   
   				//cancellazione logica Tipologie Appalto
   				TipoAppaltoManager taMan = new TipoAppaltoManager(currentActiveConnection, logger);
   				taMan.deleteAppaltiLotto(Long.parseLong(idLotto));
				}
				
				// PP se si cancella un lotto occorre ricalcolare importo gara e importo contributo sa
				// va fatto sulla situazione finale con il lotto cancellato
//				GaraManager gm = new GaraManager(currentActiveConnection, logger);
//				Lotto lottoDb = lottoManager.getLotto(Long.parseLong(idLotto));
//				Gara garaDb = gm.getGara(lottoDb.getId_Gara());
	    		GestioneContributoWrapperBeanClient gcwbc = new GestioneContributoWrapperBeanClient (configuration.getContributoUrl(), currentActiveConnection, logger);

	    		// messaggi da ritornare
	    		AllValidationBeans msgs = new AllValidationBeans();
	    		
				if(garaDb.getDATA_CONFERMA_GARA() != null || garaDb.getDATA_PERFEZIONAMENTO_BANDO() != null){
	                if(SimogFlags.isGRIGLIA_CONTRIBUTO()){
	                   // devo ricalcolare i contributi di tutti i lotti e della gara
	                   Map<String, Lotto> lotti = lottoManager.getMappaLotti(garaDb.getId_Gara());
	                   
	                   gcwbc.ricalcola(garaDb, lotti);
	                }
	                else{			
      					// PP devo ricalcolare l'importo contributo gara se questa e perfezionata
      					BigDecimal impGaraCalcolo = gcwbc.getImportoGara(lottoManager.getListaLotti(Long.parseLong(idGara)), true, true);
      					
      					Gara garaCalc = (Gara) garaDb.clone();
      					garaCalc.setIMPORTO_GARA(impGaraCalcolo);
      					
      					//MAC 36255 3.04.8
				        //se la gara e singolo lotto salvo in una variabile la modalita esclusione da passare a getContributoSA
						Map<String, Lotto> lotti = lottoManager.getMappaLotti(garaDb.getId_Gara());
						if (lotti.size()==1) {
							Map.Entry<String, Lotto> entry = lotti.entrySet().iterator().next();
				        	 motivoEsclusioneGara = String.valueOf(entry.getValue().getID_ESCLUSIONE()).trim();
						}
      					
						//MAC 36255 3.04.8 aggiunto motivoEsclusioneGara
      					ParametriContributo params = new ParametriContributo(garaCalc, null, motivoEsclusioneGara,
      							garaCalc.getDATA_CONFERMA_GARA() != null
      		    				? PageHelper.getCalendarFromStringDate(garaCalc.getDATA_CONFERMA_GARA())
      		    				: PageHelper.getCalendarFromStringDate(garaCalc.getDATA_PERFEZIONAMENTO_BANDO())
      		    				);
      					
      					BigDecimal importo = gcwbc.getContributoSA(params);
      					if(!gcwbc.hasErrors())
      						garaDb.setIMPORTO_SA_GARA(importo);
                          else if(SimogFlags.is30230_NRFWEBXX00Active())
                             garaDb.setIMPORTO_SA_GARA(new BigDecimal(Costanti.IMPORTO_FUORI_SCALA));

      					garaDb.setIMPORTO_GARA(gcwbc.getImportoGara(lottoManager.getListaLotti(Long.parseLong(idGara)), false, false));
	                }
	                
					// controllo messaggio di ritorno
					msgs.add(gcwbc.getErrors());

					// sfrutto la modifica della gara passando le stesse impostazioni 
					GaraLottoAction gla = new GaraLottoAction(currentActiveConnection, logger, configuration);
					AllValidationBeans msg2 = new AllValidationBeans();
					gla.saveGara(garaDb, false, msg2);
//					 controlo messaggio di ritorno
					msgs.add(msg2);
				}
				
				logger.debug("Esito della cancellazione - Aggiornate tuple [" + cancella + "]");
				
				if( cancella > 0 ){
					logManager.log(getTodayDate(), cfSARiferimento, cfUtente, cig, LogManager.DEL_LOTTO, cfAmministrazione, idLotto, idGara);

					commit(currentActiveConnection);
					
					msgs.addValidationInfo(SIMOG_LOTTO_017 + " CIG [" + cig + "]");
					
					sendValidations(request, response, msgs, redirectUrl);
					return;
				} else {
					rollback(currentActiveConnection);	
					
					sendError(request, response, SIMOG_LOTTO_017e, redirectUrl );
					return;
				}
			} catch (Exception sqle) {
				rollback(currentActiveConnection);					
				sendError(request, response, sqle.getMessage(), redirectUrl, sqle);
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
