package it.anticorruzione.ted.actions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.europa.publications.resource.schema.ted.r209.reception.AwardContractF03;
import eu.europa.publications.resource.schema.ted.r209.reception.TedEsenders;
import it.anticorruzione.ted.beans.AwardedContract;
import it.anticorruzione.ted.beans.Contractor;
import it.anticorruzione.ted.beans.LottoTED;
import it.anticorruzione.ted.beans.ResponseMessageTED;
import it.anticorruzione.ted.db.entity.TEDNotice;
import it.anticorruzione.ted.db.entity.TEDSubmit;
import it.anticorruzione.ted.enums.TypeNoticeEnum;
import it.anticorruzione.ted.notice.F14Generator;
import it.anticorruzione.ted.notice.F20Generator;
import it.anticorruzione.ted.util.UtilityClass;
import it.anticorruzione.ted.validator.F14Validator;
import it.anticorruzione.ted.validator.F20Validator;
import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avlp.simog.beans.Collaborazione;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
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
import it.avlp.simog.ws.massload.xmlbeans.DeltaLottoWS;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoModifica;

public class InviaModificaTEDActionManager extends CommonActionManager {

	public ResponseMessageTED execute(String ticket, String indexCollaborazione, String cig, String no_doc_ojs,
			FormularioAvvisoModifica formularioModifica) {
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
			wss.setComando("inviaRettificaTED");
			wss.setCollaborazione(Integer.parseInt(indexCollaborazione));		
			wss = wsm.selectFindValidSession(wss);
			cwsm.commit();
			if(wss != null){
				logger.info(">>>>esiste una sessione associata al ticket");
				try{
					tm.validateRequestedActionByProfile(wss,TicketManager.RETTIFICA_TED);
					if(tm.isValido()){
						logger.info(">>>>utente abilitato al comando richiesto");
						cwsm.setIsolation("t_serialize");
						logger.info(">>>> (connnessione settata a transaction serialized)");
						Collaborazione coll = null;
						if(!tm.isOperaComeOsservatorio()){
							coll = tm.getCollaborazione();
						}
						
						wss.setSessionEnd(sqlt.increseSessionEnd(logger, sqlt.getDBDate(con, logger)));
		
						ris = startInviaModificaTED(cig, no_doc_ojs,formularioModifica,con,logger);
						
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
						if(cwsm != null){
							cwsm.rollback();
						}
						ris.setSuccess(false);
						ris.setError(e.getMessage());
						logger.error("SimogWSException catched: "+e.getMessage());
					}  catch (Exception e) {
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

	private ResponseMessageTED startInviaModificaTED(String cig, String no_doc_ojs,
			FormularioAvvisoModifica formularioModifica, Connection con, Logger logger) {
		ResponseMessageTED ris = new ResponseMessageTED();
		//default a false
		ris.setSuccess(false);
		ris.setStatus(statusSE);
		
		LottoManager lm = new LottoManager(con, logger);
		GaraManager gm = new GaraManager(con,logger);
		AggiudicazioniManager am = new AggiudicazioniManager(con,logger);
		AggiudicatarioManager aggm = new AggiudicatarioManager(con, logger);
		try {
			List<Lotto> listaLotti = lm.getLottoByCigWS(cig);
			if(listaLotti.isEmpty()) {
				ris.setStatus_msg("SERVICE_ERROR_P07 – CIG inesistente o non di competenza");
				return ris;
			} else {
				Lotto lotto = listaLotti.get(0);
				Gara g = gm.getGara(lotto.getId_Gara());
				long idLotto = lotto.getId_Lotto();
				String esitoValidation = noDocOjsValidation(no_doc_ojs, TypeNoticeEnum.F03,idLotto,lotto.getId_Gara());
				if(esitoValidation!=null && !"".equals(esitoValidation)) {
					ris.setStatus_msg(esitoValidation);
					return ris;
				}
				
				esitoValidation = F20Validator.valida(formularioModifica,tedDb);
				if(esitoValidation!=null && !"".equals(esitoValidation)) {
					ris.setStatus_msg(esitoValidation);
					return ris;
				}
				
				TEDNotice originalNotice = tedDb.findNoticeByNoDocOjsByLotto(no_doc_ojs,null,0);
				TEDSubmit originalSubmit = tedDb.findSubmitByIdNotice(originalNotice.getIdTedNotice());
				TEDNotice notice = tedDb.findByTypeAndIdLotto(TypeNoticeEnum.F20,idLotto);
				
				DataNotice dataNotice = new DataNotice();									
				
				dataNotice.setGara(g);
				DeltaGaraTED deltaGaraTED = tedDb.getDeltaGara(g.getId_Gara());
				dataNotice.setDeltaGaraTED(deltaGaraTED);
				
				List<LottoTED> listaLottoTED = new ArrayList<LottoTED>();
				LottoTED lottoTed = new LottoTED();
				lottoTed.setLotto(lotto);
				lottoTed.setDeltaLottoTED(tedDb.getDeltaLotto(cig));
				
				List<AggiudicazioneBean> listaAgg =  am.getAggiudicazioniByCIG(cig);
				if(!listaAgg.isEmpty()) {
					AggiudicazioneBean aggId = listaAgg.get(listaAgg.size()-1);
					lottoTed.setAggiudicazione(am.getAggiudicazioni(aggId.getIdAggiudicazione(), 
																	aggId.getDataInizioAggiudicazione(), false));
					lottoTed.setAggiudicatari(aggm.loadMany(aggId.getIdAggiudicazione(), aggId.getDataInizioAggiudicazione(), false));
				}
				
				listaLottoTED.add(lottoTed);
				
				dataNotice.setListaLotti(listaLottoTED);
				
				dataNotice.setNoticeNumberOjs(no_doc_ojs);
				dataNotice.setOriginalDataDispatch(UtilityClass.dateToStringTED(originalSubmit.getDataRequest(), true));
				dataNotice.setOriginalNoDocExt(originalNotice.getNoDocExt());
				dataNotice.setFormularioModifica(formularioModifica);
				AwardedContract awardedContract = getAwardedContract(originalSubmit.getXmlRequest(), cig);
				if(awardedContract==null) {
					ris.setStatus_msg("SERVICE_ERROR_110 - Per il CIG indicato non risulta un formulario di aggiudicazione aggiudicato");
					return ris;
				} 
				
				if(awardedContract.getValTotal()==null) {
					awardedContract.setValTotal(lottoTed.getAggiudicazione().getImportoAggiudicazione());
				}
				
				dataNotice.setAwardedContract(awardedContract);
				
				
				
				//Se è stato precedentemente inviato un formulario, se questo è IN_PROGRESS cancellalo
				checkPreviousPublication(notice);
				
				ris = sendNotice(dataNotice,lotto);
			}
		} catch (SQLException e) {
			ris.setStatus_msg(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			ris.setStatus_msg(e.getMessage());
			e.printStackTrace();
		}
		
		return ris;
	}

	private ResponseMessageTED sendNotice(DataNotice dataNotice, Lotto lotto) {
		String newNoDocExt = createNoDocExt();
		dataNotice.setNoDocExt(newNoDocExt);
		dataNotice.setEsenderlogin(SimogProperties.getInstance().getUsernameTed());
		F20Generator f20gen = new F20Generator(dataNotice, SimogProperties.getInstance().getXsdTed());
		TedEsenders tedEsender = f20gen.createNotice();
		String f20str = tedEsender.toString();
		f20str = f20str.replaceAll("CONTRACTOR2", "CONTRACTOR").replaceAll("CONTRACTOR1", "CONTRACTOR");
		try {
			return saveAndSendToTED(dataNotice, f20str,TypeNoticeEnum.F20,lotto);
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private AwardedContract getAwardedContract(String xmlRequest, String cig) {
		AwardedContract res = null;
		try {
				Document doc = UtilityClass.getDocumentFromXmlString(xmlRequest);
				NodeList hiList = doc.getElementsByTagName("rec:AWARD_CONTRACT");
				for (int i = 0; i < hiList.getLength(); i++) {
					Node child = hiList.item(i);
				    NodeList awardContractChilds = child.getChildNodes();
				    boolean checkCig = false;
				    for(int y=0;y<awardContractChilds.getLength();y++) {
				    	Node awChild = awardContractChilds.item(y);
				    	String nodeName = awChild.getNodeName();
				    	String nodeText = awChild.getTextContent();
				    	if(!checkCig && nodeName.equals("rec:LOT_NO") && nodeText.equals(cig)) {
				    		checkCig=true;
				    	}
				    	if(checkCig && nodeName.equals("rec:AWARDED_CONTRACT")) {
				    		res = new AwardedContract();
				    		NodeList awardedNodes = awChild.getChildNodes();
				    		for(int z=0;z<awardedNodes.getLength();z++) {
				    			Node awardedNode = awardedNodes.item(z);
				    			if(awardedNode.getNodeName().equals("rec:DATE_CONCLUSION_CONTRACT")) {
				    				res.setDateConclusionContract(awardedNode.getTextContent());
				    			}
				    			if(awardedNode.getNodeName().equals("rec:CONTRACTORS")) {
				    				NodeList contractorsList = awardedNode.getChildNodes();
				    				for(int a=0;a<contractorsList.getLength();a++) {
				    					Node contractorList = contractorsList.item(a);
				    					if(contractorList.getNodeName().equals("rec:CONTRACTOR")) {
				    						res.addContractor(getContractor(contractorList));
				    					}
				    				}
				    			}
				    			if(awardedNode.getNodeName().equals("rec:VALUES")) {
				    				NodeList valuesList = awardedNode.getChildNodes();
				    				for(int b=0;b<valuesList.getLength();b++) {
				    					Node value = valuesList.item(b);
				    					if(value.getNodeName().equals("rec:VAL_TOTAL"))
				    						res.setValTotal(new BigDecimal(Double.valueOf(value.getTextContent())));
				    				}
				    			}
				    		}
				    	}
				    }
				}
				return res;
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Contractor getContractor(Node contractorList) {
		Contractor contractor = new Contractor();
		NodeList elContractor = contractorList.getChildNodes();
		for(int b=0;b<elContractor.getLength();b++) {
			Node elemento = elContractor.item(b);
			if(elemento.getNodeName().equals("rec:ADDRESS_CONTRACTOR")) {
				NodeList addrsContractorElemens = elemento.getChildNodes();
				for(int c=0;c<addrsContractorElemens.getLength();c++) {
					Node addrsChild = addrsContractorElemens.item(c);
					if(addrsChild.getNodeName().equals("rec:OFFICIALNAME"))
						contractor.setOfficialname(addrsChild.getTextContent());
					else if(addrsChild.getNodeName().equals("rec:NATIONALID"))
						contractor.setNationalid(addrsChild.getTextContent());
					else if(addrsChild.getNodeName().equals("rec:ADDRESS"))
						contractor.setAddress(addrsChild.getTextContent());
					else if(addrsChild.getNodeName().equals("rec:TOWN"))
						contractor.setTown(addrsChild.getTextContent());
					else if(addrsChild.getNodeName().equals("rec:COUNTRY"))
						contractor.setCountry(addrsChild.getAttributes().getNamedItem("VALUE").getTextContent());
					else if(addrsChild.getNodeName().equals("rec:POSTAL_CODE"))
						contractor.setPostalcode(addrsChild.getTextContent());
					else if(addrsChild.getNodeName().equals("rec:PHONE"))
						contractor.setPhone(addrsChild.getTextContent());
					else if(addrsChild.getNodeName().equals("rec:E_MAIL"))
						contractor.setEmail(addrsChild.getTextContent());
					else if(addrsChild.getNodeName().equals("rec:FAX"))
						contractor.setFax(addrsChild.getTextContent());
					else if(addrsChild.getNodeName().equals("nuts:NUTS"))
						contractor.setNuts(addrsChild.getAttributes().getNamedItem("CODE").getTextContent());
					else if(addrsChild.getNodeName().equals("rec:URL"))
						contractor.setUrl(addrsChild.getTextContent());
				}
			}
			if(elemento.getNodeName().equals("rec:SME")) {
				contractor.setSme(true);
			}
			if(elemento.getNodeName().equals("rec:NO_SME")) {
				contractor.setSme(false);
			}
		}
		
		return contractor;
	}
	
}