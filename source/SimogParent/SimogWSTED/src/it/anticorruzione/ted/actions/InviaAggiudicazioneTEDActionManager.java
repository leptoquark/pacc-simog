package it.anticorruzione.ted.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import eu.europa.publications.resource.schema.ted.r209.reception.TedEsenders;
import it.anticorruzione.ted.beans.LottoTED;
import it.anticorruzione.ted.beans.ResponseMessageTED;
import it.anticorruzione.ted.db.entity.TEDNotice;
import it.anticorruzione.ted.enums.StatusNoticeEnum;
import it.anticorruzione.ted.enums.TypeNoticeEnum;
import it.anticorruzione.ted.exception.TEDErrorException;
import it.anticorruzione.ted.notice.F03Generator;
import it.anticorruzione.ted.validator.F03Validator;
import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.EsitoEnum;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.error.SimogWSException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.ws.beans.DataNotice;
import it.avlp.simog.ws.commons.ConfigurationManager;
import it.avlp.simog.ws.commons.LoggerManager;
import it.avlp.simog.ws.commons.TicketManager;
import it.avlp.simog.ws.commons.sql.WSSessionManager;
import it.avlp.simog.ws.commons.sql.util.SqlTools;
import it.avlp.simog.ws.massload.xmlbeans.DeltaGaraTED;
import it.avlp.simog.ws.massload.xmlbeans.DeltaLottoTED;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoAggiudicazione;

public class InviaAggiudicazioneTEDActionManager extends CommonActionManager {

	private DataNotice dataNotice = null;
	
	public ResponseMessageTED execute(String ticket, String indexCollaborazione, String cig, String id_gara,
			FormularioAvvisoAggiudicazione formularioAggiudicazione) {
		ResponseMessageTED ris = null;
		Logger logger = null;
		ConnectionWSManager cwsm = null;
		Connection con = null;
		if(indexCollaborazione == null || "".equals(indexCollaborazione.trim())){
			indexCollaborazione = "-1";
		}else{
			indexCollaborazione = indexCollaborazione.trim();
		}
	
	try{
		logger = LoggerManager.getInstance().getLogger();
		ris = new ResponseMessageTED();
		WsSessions wss = new WsSessions();
		cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
		WSSessionManager wsm = new WSSessionManager(logger,cwsm);
		con = cwsm.getConnection();
		TicketManager tm = new TicketManager();
		SqlTools sqlt = new SqlTools();
		//-------	 begin operations		--------//	
		cwsm.setAutocommit(false);
		wss.setTicket(ticket);
		wss.setComando("inviaAggiudicazioneTED");
		wss.setCollaborazione(Integer.parseInt(indexCollaborazione));		
		wss = wsm.selectFindValidSession(wss);
		cwsm.commit();
		if(wss != null){
			logger.info(">>>>esiste una sessione associata al ticket");
			try{
				tm.validateRequestedActionByProfile(wss,TicketManager.AGG_TED);
				if(tm.isValido()){
					logger.info(">>>>utente abilitato al comando richiesto");
					cwsm.setIsolation("t_serialize");
					logger.info(">>>> (connnessione settata a transaction serialized)");
					Collaborazione coll = null;
					if(!tm.isOperaComeOsservatorio()){
						coll = tm.getCollaborazione();
					}
					
					wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
					
					ris = startInviaAggiudicazioneTED(id_gara, cig,formularioAggiudicazione,con,logger);
					
					//--- fine operazione
			    	wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
			    	if(wsm.updateSessionAfterOp(wss)){
			    		cwsm.commit();				
			    		logger.info(">>>>aggiornamento dello stato della sessione riuscito");
			    	}
					
					
				} else{
					logger.info("fallimento della validazione del ticket associazione comando - profilo non autorizzata");
					String messaggioErrore = "collaborazione ["+wss.getCollaborazione()+"] non abilitata al comando ["+wss.getComando()+"] richiesto";
					wss.setLastError(messaggioErrore);				
					wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
					if(wsm.updateSessionAfterOp(wss)){
						cwsm.commit();
						logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
						ris.setSuccess(false);
						ris.setError(messaggioErrore);
					}			
				}
			} catch(SimogWSException ste){
					logger.error("indice collaborazione non valido");
					String messaggioErrore = ste.getMyMessage();
					wss.setLastError("collaborazione ["+wss.getCollaborazione()+"] non esiste");				
					wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
					if(wsm.updateSessionAfterOp(wss)){
						logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
						ris.setSuccess(false);
						ris.setError(messaggioErrore);
						cwsm.commit();
					}
					return ris;
				} catch (NumberFormatException e) {
					e.printStackTrace();
					if(cwsm != null){
						cwsm.rollback();
					}
					ris.setSuccess(false);
					ris.setError(e.getMessage());
					logger.error("SimogWSException catched: "+e.getMessage());
				} catch (SQLException e) {
					if(cwsm != null){
						cwsm.rollback();
					}
					ris.setSuccess(false);
					ris.setError(e.getMessage());
					logger.error("SimogWSException catched: "+e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
					if(cwsm != null){
						cwsm.rollback();
					}
					ris.setSuccess(false);
					ris.setError(e.getMessage());
					logger.error("SimogWSException catched: "+e.getMessage());
				}
		}
		
		}catch(SimogWSException ste){
			if(cwsm != null){
				cwsm.rollback();
			}
			ris.setSuccess(false);
			ris.setError(ste.getMyMessage());
			logger.error("SimogTEDException catched: "+ste.getMyMessage());
		}finally{
			if(cwsm != null){
				cwsm.closeConnection();
			}
		}
	
		return ris;		
	}

	public synchronized ResponseMessageTED startInviaAggiudicazioneTED(String id_gara, String cig,
			FormularioAvvisoAggiudicazione formularioAggiudicazione, Connection con,
			Logger logger) throws SQLException, Exception {

		ResponseMessageTED ris = new ResponseMessageTED();
		//default a false
		ris.setSuccess(false);
		ris.setStatus(statusSE);
		String error = validateParams(id_gara, cig, con, logger);
		if("".equals(error))
		error+=F03Validator.validate(id_gara, cig, con, logger, formularioAggiudicazione);
		
		
		if(!"".equals(error)) {
			ris.setError(error);
			return ris;
		}
		
		TEDNotice notice = null;
		if(id_gara!=null && !"".equals(id_gara))
			notice = tedDb.findByTypeAndIdGara(TypeNoticeEnum.F03, Long.valueOf(id_gara));
		else {
			LottoManager lm = new LottoManager(con,logger);
			Lotto l = lm.getLottoByCigWS(cig).get(0);
			notice = tedDb.findByTypeAndIdLotto(TypeNoticeEnum.F03, l.getId_Lotto());
		}
		
		boolean check = checkPreviousPublication(notice);
//		boolean check=true;
		
		if(check) {
			DeltaGaraTED deltaGaraTED = null;
			List<LottoTED> listaLottoTED = new ArrayList<LottoTED>();
			GaraManager gm = new GaraManager(con,logger);
			LottoManager lm = new LottoManager(con,logger);
			AggiudicazioniManager am = new AggiudicazioniManager(con, logger);
			AggiudicatarioManager aggm = new AggiudicatarioManager(con, logger);
			TEDNotice f02Not = new TEDNotice();
			
			long idGara = 0;
			Lotto lotto = null;
			if(id_gara!=null && !"".equals(id_gara)) {
				idGara=Long.valueOf(id_gara);
				f02Not = tedDb.findByTypeAndIdGara(TypeNoticeEnum.F02, idGara);
				List<Lotto> listaLotti = lm.getListaLotti(idGara);
				for(Lotto l : listaLotti) {
					LottoTED lottoTed = new LottoTED();
					lottoTed.setLotto(l);
					DeltaLottoTED deltaLottoTED = tedDb.getDeltaLotto(l.getFullCIG());
					if(deltaLottoTED!=null) {
						List<AggiudicazioneBean> listaAgg =  am.getAggiudicazioniByCIG(l.getFullCIG());
						if(!listaAgg.isEmpty()) {
							AggiudicazioneBean aggId = listaAgg.get(listaAgg.size()-1);
							lottoTed.setAggiudicazione(am.getAggiudicazioni(aggId.getIdAggiudicazione(), 
																			aggId.getDataInizioAggiudicazione(), false));
							lottoTed.setAggiudicatari(aggm.loadMany(aggId.getIdAggiudicazione(), aggId.getDataInizioAggiudicazione(), false));
						}
						
						lottoTed.setDeltaLottoTED(deltaLottoTED);
						listaLottoTED.add(lottoTed);
					}
				}
			} else if(cig!=null && !"".equals(cig)) {
				
				lotto = lm.getLottoByCigWS(cig).get(0);
				f02Not = tedDb.findByTypeAndIdGara(TypeNoticeEnum.F02, lotto.getId_Gara());
				LottoTED lottoTed = new LottoTED();
//				lotto.setDataScadenzaPagamenti(dataScadenzaPag);
				lottoTed.setLotto(lotto);
				
				
				DeltaLottoTED deltaLottoTED = tedDb.getDeltaLotto(lotto.getFullCIG());
				if(deltaLottoTED!=null) {
					List<AggiudicazioneBean> listaAgg =  am.getAggiudicazioniByCIG(lotto.getFullCIG());
					if(!listaAgg.isEmpty()) {
						AggiudicazioneBean aggId = listaAgg.get(listaAgg.size()-1);
						lottoTed.setAggiudicazione(am.getAggiudicazioni(aggId.getIdAggiudicazione(), 
																		aggId.getDataInizioAggiudicazione(), false));
						lottoTed.setAggiudicatari(aggm.loadMany(aggId.getIdAggiudicazione(), aggId.getDataInizioAggiudicazione(), false));
					}
					lottoTed.setDeltaLottoTED(deltaLottoTED);
					listaLottoTED.add(lottoTed);
				}
				
				idGara=lotto.getId_Gara();
			}
			
			Gara g = gm.getGara(idGara);
			
			deltaGaraTED=tedDb.getDeltaGara(idGara);
			
			dataNotice = new DataNotice();
			dataNotice.setGara(g);
			dataNotice.setDeltaGaraTED(deltaGaraTED);
			dataNotice.setListaLotti(listaLottoTED);
			dataNotice.setFormularioAgg(formularioAggiudicazione);
			dataNotice.setNoticeNumberOjs(f02Not.getNoDocOjs());
//			dataNotice.setOraScadenzaPag(oraScadenza);
			
			ris = sendNotice(lotto);
		} else {
			
	    	ris.setStatus(StatusNoticeEnum.PUBLISHED.getStrStatus());
	    	ris.setStatus_msg("TED_ERROR_500 - Impossibile elaborare la richiesta: il formulario risulta gi� pubblicato");
		}
		
		
		return ris;
	}

	public ResponseMessageTED sendNotice(Lotto lotto) throws TEDErrorException {
		
		
		String newNoDocExt = createNoDocExt();
		dataNotice.setNoDocExt(newNoDocExt);
		dataNotice.setEsenderlogin(SimogProperties.getInstance().getUsernameTed());
		F03Generator f03Gen = new F03Generator(dataNotice, SimogProperties.getInstance().getXsdTed());
		TedEsenders tedEsender = f03Gen.createNotice();
		String f03str = tedEsender.toString();
		f03str = f03str.replaceAll("CONTRACTOR2", "CONTRACTOR").replaceAll("CONTRACTOR1", "CONTRACTOR");
		
		return saveAndSendToTED(dataNotice, f03str, TypeNoticeEnum.F03,lotto);
	    
	}
	
	private String validateParams(String id_gara, String cig, Connection con, Logger logger)
			throws SQLException, Exception {
		String error = "";
		
		
		if((id_gara==null || "".equals(id_gara))  && (cig==null || "".equals(cig)))
			error+=statusSE+"_P05 - Indicare CIG o numero gara\n";
		else if(id_gara!=null && !"".equals(id_gara) && cig!=null && !"".equals(cig))
			error+=statusSE+"_P05b - CIG e numero gara: indicare un solo valore\n";
		
		if(id_gara!=null && !PageHelper.isNumeric(id_gara)) {
			error+=statusSE+"_000 - Numero gara: formato non valido\n";
		}
		
		long idGara = 0;
		if(cig!=null) {
			LottoManager lm = new LottoManager(con, logger);
			List<Lotto> listalotto = lm.getLottoByCigWS(cig);
			if(listalotto==null || listalotto.isEmpty())
				error+=statusSE+"_P06 - CIG inesistente o non di competenza\n";
			else
				idGara=listalotto.get(0).getId_Gara();
		}
		
		if(id_gara!=null) {
			GaraManager gm = new GaraManager(con,logger);
			Gara gara = gm.getGara(Long.valueOf(id_gara));
			if(gara==null)
				error+=statusSE+"_P07 - Numero gara insistente o non di competenza\n";
			else {
				idGara=gara.getId_Gara();
			}
		}
		
		if(idGara!=0) {
			//Verifica se è stato inviato un F02 e se è confermato
			TEDNotice notice = tedDb.findByTypeAndIdGara(TypeNoticeEnum.F02, idGara);
			if(notice==null || notice.getNoDocOjs()==null)
				error+=statusSE+"_102 - Impossibile inviare il formulario di aggiudicazione in quanto non risulta presente il formulario di pubblicazione del bando\n";
		}
		
			LottoManager lm = new LottoManager(con, logger);
			if(cig!=null) {
				List<Lotto> listalotto = lm.getLottoByCigWS(cig);
				Lotto lotto = listalotto.get(0);
				//Verifica se è già presente un F03 confermato (solo per il singolo CIG)
				TEDNotice notice = tedDb.findByTypeAndIdLotto(TypeNoticeEnum.F03, lotto.getId_Lotto());
				if(notice!=null && notice.getNoDocOjs()!=null) {
					error+=statusSE+"_000 - Impossibile inviare il formulario di aggiudicazione in quanto esiste già un formulario di aggiudicazione per il CIG "+lotto.getFullCIG()+"\n";
				}
				
				InfoComuniManager icm = new InfoComuniManager(con,logger);
				InfoComuniBean infoComuniBean = icm.getInfoComuniByCig(cig);
				if(infoComuniBean==null || infoComuniBean.getEsitoProcedura().equals(EsitoEnum.PROPOSTA_AGGIUDICAZIONE.codice()))
					error+=statusSE+"_000 - Impossibile inviare il formulario di aggiudicazione: su Simog non risulta presente la scheda dati comuni per il CIG "+lotto.getFullCIG()+"\n";
//				else if(infoComuniBean.getEsitoProcedura().equals(EsitoEnum.AGGIUDICATA.codice())){
//					AggiudicazioniManager am = new AggiudicazioniManager(con,logger);
//					List<AggiudicazioneBean> listaAgg = am.getAggiudicazioniByCIG(lotto.getFullCIG());
//					if(listaAgg==null || listaAgg.isEmpty())
//						error+=statusSE+"_000 - Impossibile inviare il formulario di aggiudicazione: su Simog non risulta presente una scheda aggiudicazione confermata per il CIG "+lotto.getFullCIG()+"\n";
//					else {
//						List<AggiudicatarioBean> aggiudicatari = aggm.loadMany(listaAgg.get(0).getIdAggiudicazione(), 
//																				listaAgg.get(0).getDataInizioAggiudicazione(), 
//																				false);
//						for(AggiudicatarioBean agg : aggiudicatari) {
//							if(agg.getSoggettoPartecipante().getCitta()==null || "".equals(agg.getSoggettoPartecipante().getCitta()))
//								error+=statusSE+"_000 - Aggiudicatario "+agg.getSoggettoPartecipante().getDenominazione()+" : nella rubrica di questo soggetto non è indicata la città. Procedere con l'aggiornamento della rubrica OE di Simog\n";
//						}
//					}
//				}
					
				
			}
			
			if(id_gara!=null) {
				List<Lotto> listaLotto = lm.getListaLotti(Long.valueOf(id_gara));
				InfoComuniManager icm = new InfoComuniManager(con,logger);
				AggiudicatarioManager aggm = new AggiudicatarioManager(con, logger);
				AggiudicazioniManager am = new AggiudicazioniManager(con,logger);
				for(Lotto l : listaLotto) {
					//Verifica se è già presente un F03 confermato (per tutti i lotti)
					TEDNotice notice = tedDb.findByTypeAndIdLotto(TypeNoticeEnum.F03, l.getId_Lotto());
					if(notice!=null && notice.getNoDocOjs()!=null) {
						error+=statusSE+"_000 - Impossibile inviare il formulario di aggiudicazione in quanto esiste già un formulario di aggiudicazione per il CIG "+l.getFullCIG()+"\n";
					}
					
					InfoComuniBean infoComuniBean = icm.getInfoComuniByCig(l.getFullCIG());
					if(infoComuniBean.getEsitoProcedura()==null || infoComuniBean.getEsitoProcedura().equals(EsitoEnum.PROPOSTA_AGGIUDICAZIONE.codice()))
						error+=statusSE+"_000 - Impossibile inviare il formulario di aggiudicazione: su Simog non risulta presente la scheda dati comuni per il CIG "+l.getFullCIG()+"\n";
					else if(infoComuniBean.getEsitoProcedura().equals(EsitoEnum.AGGIUDICATA.codice())){
						List<AggiudicazioneBean> listaAgg = am.getAggiudicazioniByCIG(l.getFullCIG());
						if(listaAgg==null || listaAgg.isEmpty())
							error+=statusSE+"_000 - Impossibile inviare il formulario di aggiudicazione: su Simog non risulta presente una scheda aggiudicazione confermata per il CIG "+l.getFullCIG()+"\n";
						else {
							List<AggiudicatarioBean> aggiudicatari = aggm.loadMany(listaAgg.get(0).getIdAggiudicazione(), 
																					listaAgg.get(0).getDataInizioAggiudicazione(), 
																					false);
							for(AggiudicatarioBean agg : aggiudicatari) {
								if(agg.getSoggettoPartecipante().getCitta()==null || "".equals(agg.getSoggettoPartecipante().getCitta()))
									error+=statusSE+"_000 - Aggiudicatario "+agg.getSoggettoPartecipante().getDenominazione()+" : nella rubrica di questo soggetto non � indicata la citt�. Procedere con l'aggiornamento della rubrica OE di Simog\n";
							}
						}
					}
				}
				
			}
		
		return error;
	}

}
