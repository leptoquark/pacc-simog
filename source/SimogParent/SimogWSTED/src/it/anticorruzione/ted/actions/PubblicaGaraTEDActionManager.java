package it.anticorruzione.ted.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import eu.europa.publications.resource.schema.ted.r209.reception.TedEsenders;
import it.anticorruzione.ted.beans.LottoTED;
import it.anticorruzione.ted.beans.ResponseMessageTED;
import it.anticorruzione.ted.db.entity.TEDNotice;
import it.anticorruzione.ted.enums.LegalBasisEnum;
import it.anticorruzione.ted.enums.StatusNoticeEnum;
import it.anticorruzione.ted.enums.TypeNoticeEnum;
import it.anticorruzione.ted.exception.TEDErrorException;
import it.anticorruzione.ted.notice.F02Generator;
import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.ws.WsSessions;
import it.avlp.simog.common.sql.ConnectionWSManager;
import it.avlp.simog.db.Costanti;
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

public class PubblicaGaraTEDActionManager extends CommonActionManager {

	private DataNotice dataNotice = null;
	private boolean sbloccaGara = false;
	
	public synchronized ResponseMessageTED execute(String ticket, 
			String indexCollaborazione,String id_gara, String codice_direttiva, String codice_formulario, 
			String dataScadenzaPag,String oraScadenza,  String datiPubb, String dataScadenzaRichiestaInvito){
		
		//-------	object declarations		-------//
		        ResponseMessageTED rmt = null;
				Logger logger = null;
				ConnectionWSManager cwsm = null;
				Connection con = null;
				
				if(indexCollaborazione == null || "".equals(indexCollaborazione.trim())){
					indexCollaborazione = "-1";
				}
				else{
					indexCollaborazione = indexCollaborazione.trim();
				}
				
				try{
					logger = LoggerManager.getInstance().getLogger();
					rmt = new ResponseMessageTED();
					WsSessions wss = new WsSessions();
					cwsm = new ConnectionWSManager(logger,ConfigurationManager.getInstance().getSimogProperties());
					WSSessionManager wsm = new WSSessionManager(logger,cwsm);
					con = cwsm.getConnection();
					TicketManager tm = new TicketManager();
					SqlTools sqlt = new SqlTools();
					//-------	 begin operations		--------//	
					cwsm.setAutocommit(false);
					wss.setTicket(ticket);
					wss.setComando("PubblicaBandoTED");
					wss.setCollaborazione(Integer.parseInt(indexCollaborazione));		
					wss = wsm.selectFindValidSession(wss);
					cwsm.commit();
					if(wss != null){
						try{
							tm.validateRequestedActionByProfile(wss,TicketManager.PUBBLICA_GARA_TED);
							if(tm.isValido()){
								logger.info(">>>>utente abilitato al comando richiesto");
								rmt.setSuccess(true);
								cwsm.setIsolation("t_serialize");
								logger.info(">>>> (connnessione settata a transaction serialized)");
								Collaborazione coll = null;
								if(!tm.isOperaComeOsservatorio()){
									coll = tm.getCollaborazione();
								}
								
								wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
								
								if(codice_formulario!=null && TypeNoticeEnum.F01.getTipo().equals(codice_formulario) && wss.getUserId().equals("AAAAAA00A01H501Z")) {
									rmt = sendF01Notice(Long.parseLong(id_gara));
								} else {
								if(codice_direttiva==null)
									codice_direttiva=LegalBasisEnum.DIR_201424EU.getDescrizione();
								if(codice_formulario==null)
									codice_formulario=TypeNoticeEnum.F02.getTipo();
								
								rmt =    startPubblicaGaraTED(id_gara, 
																				codice_direttiva, 
																				codice_formulario, 
																				dataScadenzaPag, 
																				oraScadenza, 
																				datiPubb, 
																				dataScadenzaRichiestaInvito, 
																				con,
																				logger);
								
								if(rmt.isSuccess() && sbloccaGara) {
									sbloccaGaraLotto(Long.valueOf(id_gara), con, logger);
								}
								}
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
									rmt.setSuccess(false);
									rmt.setError(messaggioErrore);
								}			
							}
							
						}
						catch(SimogWSException swe){
							swe.printStackTrace();
							logger.error("indice collaborazione non valido");
							String messaggioErrore = swe.getMyMessage();
							wss.setLastError("collaborazione ["+wss.getCollaborazione()+"] non esiste");				
							wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
							if(wsm.updateSessionAfterOp(wss)){
								cwsm.commit();
								logger.info("aggiornamento del db con l'errore e lo stato a \"IDLE\" effettuato correttamente");
								rmt.setSuccess(false);
								rmt.setError(messaggioErrore);
							}
							return rmt;
						} catch (NumberFormatException e) {
							e.printStackTrace();
							if(cwsm != null){
								cwsm.rollback();
							}
							rmt.setSuccess(false);
							rmt.setError(e.getMessage());
							logger.error("SimogWSException catched: "+e.getMessage());
						} catch (SQLException e) {
							e.printStackTrace();
							if(cwsm != null){
								cwsm.rollback();
							}
							rmt.setSuccess(false);
							rmt.setError(e.getMessage());
							logger.error("SimogWSException catched: "+e.getMessage());
						} catch (Exception e) {
							e.printStackTrace();
							if(cwsm != null){
								cwsm.rollback();
							}
							rmt.setSuccess(false);
							rmt.setError(e.getMessage());
							logger.error("SimogWSException catched: "+e.getMessage());
						}
					}
				}catch(SimogWSException ste){
					ste.printStackTrace();
					if(cwsm != null){
						cwsm.rollback();
					}
					rmt.setSuccess(false);
					rmt.setError(ste.getMyMessage());
					logger.error("SimogWSException catched: "+ste.getMyMessage());
				}finally{
					if(cwsm != null){
						cwsm.closeConnection();
					}
				}

				return rmt;	
	       }
	
	public synchronized ResponseMessageTED startPubblicaGaraTED(String id_gara, String codice_direttiva, 
			String codice_formulario, String dataScadenzaPag,String oraScadenza,  String datiPubb, 
			String dataScadenzaRichiestaInvito,  
			Connection con, Logger logger) throws NumberFormatException, SQLException, Exception{
		ResponseMessageTED ris = new ResponseMessageTED();
		//default a false
		ris.setSuccess(false);
		ris.setStatus(statusSE);
		
		String esitoValidazione = garaValidation(id_gara);
		
		//Validazione campi
		if(esitoValidazione!=null) {
			ris.setStatus_msg(esitoValidazione);
			return ris;
		}
		if(dataScadenzaPag!=null && dataScadenzaPag.compareTo(PageHelper.getCurrentDate())<=0) {
			ris.setStatus_msg(statusSE+"_P01a – Termine per il ricevimento delle offerte (data) – La data deve essere successiva alla data corrente");
			return ris;
		}
		
			GaraManager gm = new GaraManager(con,logger);
			Gara g = gm.getGara(Long.parseLong(id_gara)); 

			if(g==null) {
				ris.setStatus_msg(statusSE+"_000 - Numero gara: la gara non esiste");
				return ris;
			}
			
				DeltaGaraTED deltaGaraTED = tedDb.getDeltaGara(g.getId_Gara());
				if(deltaGaraTED==null) {
					ris.setStatus_msg(statusSE+"_000 - Dati delta gara non presenti");
					return ris;
				}
				
				LottoManager lm = new LottoManager(con,logger);
				List<Lotto> listaLotti = lm.getListaLotti(g.getId_Gara());
				
				TEDNotice notice = tedDb.findByTypeAndIdGara(TypeNoticeEnum.F02, g.getId_Gara());
				Long statusNotice = Long.valueOf(0);
				if(notice!=null)
					statusNotice = tedDb.getLastTEDStatus(notice.getIdTedNotice()).getIdTedStatus();
				
				if(listaLotti.size()>0) {
					Lotto lotto1 = listaLotti.get(0);
					if(lotto1.getData_Pubblicazione() != null && checkNoticeAndLastStatus(notice, statusNotice)) {
						ris.setStatus_msg(statusSE+"_101 - La gara risulta essere pubblicata su Simog. Non è possibile procedere.");
					    return ris;
					} 
				}
				
					List<LottoTED> listaLottoTED = new ArrayList<LottoTED>();
					boolean checkNuts = true;
					for(Lotto lotto : listaLotti) {
						if(lotto.getLUOGO_NUTS()==null || "".equals(lotto.getLUOGO_NUTS())) {
							checkNuts=false;
							break;
						}
						
						if(lotto.getId_CPV()!=null) {
							
							String startCpv = lotto.getId_CPV().substring(0,2);
							int intCpv = Integer.parseInt(startCpv);
							if(Costanti.TIPO_SCHEDA_FORNITURE.equals(lotto.getTIPO_CONTRATTO_LOTTO())) {
								if(intCpv!=48 && intCpv>44)
									ris.setStatus_msg(statusSE+"_000 - "+lotto.getFullCIG()+": la CPV prevalente indicata non è coerente con il tipo contratto (Forniture: CPV da 0 a 44 e che iniziano con 48. Lavori: CPV con 45. Servizi: da 49 a 98)");
							} else if(Costanti.TIPO_SCHEDA_LAVORI.equals(lotto.getTIPO_CONTRATTO_LOTTO()) && intCpv!=45)
								   ris.setStatus_msg(statusSE+"_000 - "+lotto.getFullCIG()+": la CPV prevalente indicata non è coerente con il tipo contratto (Forniture: CPV da 0 a 44 e che iniziano con 48. Lavori: CPV con 45. Servizi: da 49 a 98)");
							else if(Costanti.TIPO_SCHEDA_SERVIZI.equals(lotto.getTIPO_CONTRATTO_LOTTO())) {
								if(intCpv < 49 || intCpv > 98)
									 ris.setStatus_msg(statusSE+"_000 - "+lotto.getFullCIG()+": la CPV prevalente indicata non è coerente con il tipo contratto (Forniture: CPV da 0 a 44 e che iniziano con 48. Lavori: CPV con 45. Servizi: da 49 a 98)");	
							}
						}
						
						LottoTED lottoTed = new LottoTED();
						lotto.setDataScadenzaPagamenti(dataScadenzaPag);
						lotto.setDataScadenzaRichiestaInvito(dataScadenzaRichiestaInvito);
						lottoTed.setLotto(lotto);
						
						
						DeltaLottoTED deltaLottoTED = tedDb.getDeltaLotto(lotto.getFullCIG());
						if(deltaLottoTED!=null) {
							lottoTed.setDeltaLottoTED(deltaLottoTED);
							listaLottoTED.add(lottoTed);
						}
					}
				
				boolean checkDeltaLotto=listaLottoTED.size()==listaLotti.size();
				if(!checkNuts) {
					ris.setStatus_msg(statusSE+"_000 - Per uno o piu' lotti non e' stato indicato il codice NUTS");
					return ris;
				}
				
				if(!checkDeltaLotto) {
					ris.setStatus_msg(statusSE+"_000 - Uno o più lotti non presentano dati delta");
					return ris;
				}
					
					//Controllo per future evoluzioni
//					LegalBasisEnum lbEnum = LegalBasisEnum.getLegalBasis(codice_direttiva);
//					TypeNoticeEnum tnEnum = TypeNoticeEnum.getTypeNotice(codice_formulario);
//					
//					if(lbEnum==null)
//						ris.setStatus_msg(statusSE+"_000 - Codice direttiva: inserire un valore valido ");
//					else if(tnEnum==null)
//						ris.setStatus_msg(statusSE+"_000 - Codice formulario: inserire un valore valido ");
//					else {
						ris.setSuccess(true);
					    
					    //Se è stato precedentemente inviato un formulario verifica che non sia stato pubblicato
					    if(notice!=null && notice.getNoDocOjs()!=null && !"".equals(notice.getNoDocOjs())) {
					    	ris.setStatus(StatusNoticeEnum.PUBLISHED.getStrStatus());
					    	ris.setStatus_msg("TED_ERROR_500 - Impossibile elaborare la richiesta: il formulario risulta già pubblicato");
					    } else {
					    	//Se c'è già un formulario ancora in_progress, verifica che non sia stato pubblicato su TED
						   boolean check = checkPreviousPublication(notice);
							if(check) {
								dataNotice = new DataNotice();						
								
								dataNotice.setGara(g);
								dataNotice.setDeltaGaraTED(deltaGaraTED);
								
								dataNotice.setListaLotti(listaLottoTED);
								
								dataNotice.setOraScadenzaPag(oraScadenza);
								sbloccaGara=true;
								
//								ris = sendNotice(dataNotice);
							} else {
								
						    	ris.setStatus(StatusNoticeEnum.PUBLISHED.getStrStatus());
						    	ris.setStatus_msg("TED_ERROR_500 - Impossibile elaborare la richiesta: il formulario risulta già pubblicato");
							}
					    }
//					}
				
			
		  
	
		return ris;
	}
	
	public ResponseMessageTED sendNotice() throws TEDErrorException {
		
		
		String newNoDocExt = createNoDocExt();
		dataNotice.setNoDocExt(newNoDocExt);
		dataNotice.setEsenderlogin(SimogProperties.getInstance().getUsernameTed());
		F02Generator f02Gen = new F02Generator(dataNotice, SimogProperties.getInstance().getXsdTed());
		TedEsenders tedEsender = f02Gen.createNotice();
		
		return saveAndSendToTED(dataNotice, tedEsender.toString(), TypeNoticeEnum.F02,null);
	    
	}

public ResponseMessageTED sendF01Notice(long idGara) throws TEDErrorException {
		
	    dataNotice = new DataNotice();	
		String newNoDocExt = createNoDocExt();
		dataNotice.setNoDocExt(newNoDocExt);
//		dataNotice.setEsenderlogin(SimogProperties.getInstance().getUsernameTed());
//		F02Generator f02Gen = new F02Generator(dataNotice, SimogProperties.getInstance().getXsdTed());
//		TedEsenders tedEsender = f02Gen.createNotice();
		Gara gara = new Gara();
		gara.setIdGara(idGara);
		dataNotice.setGara(gara);
		String f01xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rec:TED_ESENDERS VERSION=\"R2.0.9.S04\" xmlns:cur=\"http://publications.europa.eu/resource/authority/currency\" xmlns:lb=\"http://publications.europa.eu/resource/authority/legal-basis\" xmlns:rec=\"http://publications.europa.eu/resource/schema/ted/R2.0.9/reception\" xmlns:nuts=\"http://publications.europa.eu/resource/schema/ted/2021/nuts\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://publications.europa.eu/resource/schema/ted/R2.0.9/reception TED_ESENDERS.xsd \"><rec:SENDER><rec:IDENTIFICATION><rec:ESENDER_LOGIN>TEDUTDD8</rec:ESENDER_LOGIN><rec:NO_DOC_EXT>2021-200246</rec:NO_DOC_EXT></rec:IDENTIFICATION><rec:CONTACT><rec:ORGANISATION>ANAC</rec:ORGANISATION><rec:COUNTRY VALUE=\"IT\"/><rec:E_MAIL>testmail@anticorruzione.it</rec:E_MAIL></rec:CONTACT></rec:SENDER><rec:FORM_SECTION><rec:F01_2014 CATEGORY=\"ORIGINAL\" FORM=\"F01\" LG=\"IT\"><rec:LEGAL_BASIS VALUE=\"32014L0024\"/><rec:NOTICE TYPE=\"PRI_ONLY\"/><rec:CONTRACTING_BODY><rec:ADDRESS_CONTRACTING_BODY><rec:OFFICIALNAME>Autorita' Nazionale Anticorruzione</rec:OFFICIALNAME><rec:NATIONALID>97584460584</rec:NATIONALID><rec:ADDRESS>Via M. Minghetti, 10</rec:ADDRESS><rec:TOWN>ROMA</rec:TOWN><rec:POSTAL_CODE>000187</rec:POSTAL_CODE><rec:COUNTRY VALUE=\"IT\"/><rec:CONTACT_POINT>MARIO ROSSI</rec:CONTACT_POINT><rec:PHONE>+39 342342343</rec:PHONE><rec:E_MAIL>m.rossi@anticorruzione.it</rec:E_MAIL><rec:FAX>+39 342342342</rec:FAX><nuts:NUTS CODE=\"ITI43\"/><rec:URL_GENERAL>http://www.anticorruzione.it</rec:URL_GENERAL><rec:URL_BUYER>http://www.anticorruzione.it</rec:URL_BUYER></rec:ADDRESS_CONTRACTING_BODY><rec:DOCUMENT_FULL/><rec:URL_DOCUMENT>http://www.anticorruzione.it/tedocument.pdf</rec:URL_DOCUMENT><rec:ADDRESS_FURTHER_INFO_IDEM/><rec:URL_TOOL>http://www.rd.it/rifstrumenti.pdf</rec:URL_TOOL><rec:CA_TYPE_OTHER>Riferimento ad altro settore</rec:CA_TYPE_OTHER><rec:CA_ACTIVITY_OTHER>Settore per la manutenzione dei servizi IT</rec:CA_ACTIVITY_OTHER></rec:CONTRACTING_BODY><rec:OBJECT_CONTRACT ITEM=\"1\"><rec:TITLE><rec:P><rec:FT TYPE=\"SUP\">Pulizia viale fori imperiali</rec:FT></rec:P></rec:TITLE><rec:CPV_MAIN><rec:CPV_CODE CODE=\"45262500\"/></rec:CPV_MAIN><rec:TYPE_CONTRACT CTYPE=\"WORKS\"/><rec:SHORT_DESCR><rec:P><rec:FT TYPE=\"SUB\">Pulizia viale di ingressp per i fori imperiali</rec:FT></rec:P></rec:SHORT_DESCR><rec:VAL_ESTIMATED_TOTAL CURRENCY=\"EUR\">133999.00</rec:VAL_ESTIMATED_TOTAL><rec:NO_LOT_DIVISION/><rec:OBJECT_DESCR ITEM=\"1\"><nuts:NUTS CODE=\"ITI43\"/><rec:MAIN_SITE><rec:P><rec:FT TYPE=\"SUB\">Roma</rec:FT></rec:P></rec:MAIN_SITE><rec:SHORT_DESCR><rec:P><rec:FT TYPE=\"SUB\">Rimozione erbacce</rec:FT></rec:P></rec:SHORT_DESCR><rec:VAL_OBJECT CURRENCY=\"EUR\">133999.00</rec:VAL_OBJECT><rec:RENEWAL/><rec:RENEWAL_DESCR><rec:P><rec:FT TYPE=\"SUB\">Dettagli sul rinnovo contrattuale</rec:FT></rec:P></rec:RENEWAL_DESCR><rec:INFO_ADD><rec:P><rec:FT TYPE=\"SUB\">Finanziamenti UE secondo quanto previsto dal programma Europa Creativa</rec:FT></rec:P></rec:INFO_ADD></rec:OBJECT_DESCR><rec:DATE_PUBLICATION_NOTICE>2021-06-28</rec:DATE_PUBLICATION_NOTICE></rec:OBJECT_CONTRACT><rec:PROCEDURE><rec:NO_CONTRACT_COVERED_GPA/></rec:PROCEDURE><rec:COMPLEMENTARY_INFO><rec:DATE_DISPATCH_NOTICE>2021-06-22</rec:DATE_DISPATCH_NOTICE></rec:COMPLEMENTARY_INFO></rec:F01_2014></rec:FORM_SECTION></rec:TED_ESENDERS>";
		f01xml = f01xml.replace("2021-200246", newNoDocExt).replace("2021-06-22", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		return saveAndSendToTED(dataNotice, f01xml, TypeNoticeEnum.F01,null);
	    
	}
	
	public boolean isSbloccaGara() {
		return sbloccaGara;
	}

	public void setSbloccaGara(boolean sbloccaGara) {
		this.sbloccaGara = sbloccaGara;
	}


	private boolean checkNoticeAndLastStatus(TEDNotice notice, Long status) {
		return notice==null || 
				status.intValue()==StatusNoticeEnum.NOT_PUBLISHED.getIdStato() || 
				status.intValue()==StatusNoticeEnum.RECEPTION_ERROR.getIdStato();
	}
	
}
