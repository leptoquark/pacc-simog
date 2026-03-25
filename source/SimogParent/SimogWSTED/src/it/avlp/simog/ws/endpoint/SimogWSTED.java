package it.avlp.simog.ws.endpoint;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

import it.anticorruzione.ted.actions.CancellaRichiestaTEDActionManager;
import it.anticorruzione.ted.actions.InviaAggiudicazioneTEDActionManager;
import it.anticorruzione.ted.actions.InviaModificaTEDActionManager;
import it.anticorruzione.ted.actions.InviaRettificaTEDActionManager;
import it.anticorruzione.ted.actions.PubblicaGaraTEDActionManager;
import it.anticorruzione.ted.actions.VerificaTEDActionManager;
import it.anticorruzione.ted.beans.ResponseMessageTED;
import it.anticorruzione.ted.db.entity.Gara;
import it.anticorruzione.ted.db.entity.Lotto;
import it.anticorruzione.ted.db.entity.TedDelta;
import it.anticorruzione.ted.enums.StatusNoticeEnum;
import it.anticorruzione.ted.exception.TEDErrorException;
import it.anticorruzione.ted.service.CancellaDeltaGaraTedService;
import it.anticorruzione.ted.service.CancellaDeltaLottoTedService;
import it.anticorruzione.ted.service.DeltaGaraTedService;
import it.anticorruzione.ted.service.DeltaLottoTedService;
import it.anticorruzione.ted.service.ITEDDbService;
import it.anticorruzione.ted.service.TicketService;
import it.anticorruzione.ted.service.impl.CancellaDeltaGaraTedServiceImpl;
import it.anticorruzione.ted.service.impl.CancellaDeltaLottoTedServiceImpl;
import it.anticorruzione.ted.service.impl.DeltaGaraTedServiceImpl;
import it.anticorruzione.ted.service.impl.DeltaLottoTedServiceImpl;
import it.anticorruzione.ted.service.impl.TEDDbService;
import it.anticorruzione.ted.service.impl.TicketServiceImpl;
import it.anticorruzione.ted.util.MarshallerTED;
import it.anticorruzione.ted.validator.DeltaGaraValidator;
import it.anticorruzione.ted.validator.DeltaLottoValidator;
import it.anticorruzione.ted.xml.ticket.Collaborazione;
import it.avlp.simog.common.actions.ConsultaGaraActionManager;
import it.avlp.simog.common.actions.PubblicazioneBandoActionManager;
import it.avlp.simog.common.util.General;
import it.avlp.simog.error.ErrorManager;
import it.avlp.simog.error.SimogWsXmlException;
import it.avlp.simog.massload.xmlbeans.DeltaGaraWSDocument;
import it.avlp.simog.massload.xmlbeans.DeltaLottoWSDocument;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.ws.beans.ResponseConsultaGaraTED;
import it.avlp.simog.ws.massload.xmlbeans.DeltaGaraTED;
import it.avlp.simog.ws.massload.xmlbeans.DeltaLottoTED;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoAggiudicazione;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoModifica;
import it.avlp.simog.ws.massload.xmlbeans.FormularioAvvisoRettifica;
import it.avlp.simog.ws.massload.xmlbeans.PubblicazioneWS;
import it.avlp.simog.ws.massload.xmlbeans.SchedaGaraCig;
import it.avlp.simog.ws.massload.xmlbeans.SchedaType;

@WebService(targetNamespace = "xmlbeans.massload.simog.avlp.it")
public class SimogWSTED {
	public OutputStream os;
	private static final Logger logger = Logger.getLogger(SimogWSTED.class);
	private TicketService ticketService = new TicketServiceImpl();
	private ITEDDbService tedDb = new TEDDbService();
	private String error;
	
		@WebMethod
		public ResponseMessageTED deltaGaraTED(@WebParam(name = "ticket") String ticket, 
											@WebParam(name = "indexCollaborazione") String indexCollaborazione, 
											@WebParam(name = "id_gara") Long id_gara, 
											@WebParam(name = "deltaGaraTED") DeltaGaraTED deltaGaraTED) {

			
			logger.info("deltaGaraTed - ticket : " + ticket + " - indexCollaborazione : " + indexCollaborazione + " - idGara : " + id_gara);

			Collaborazione collaborazione = ticketService.execute(ticket, indexCollaborazione);

			if(collaborazione != null) {
				
				ResponseMessageTED response = new ResponseMessageTED();
				response.setSuccess(true);
				if(id_gara==null) {
					response.setSuccess(false);
					response.setError("SERVICE_ERROR_G01a - Numero gara - il campo e' obbligatorio");
				} else {
					Gara gara = tedDb.getGaraByIdAndSA(id_gara, collaborazione.getUfficio().getIdUfficio());
					if(gara==null) {
						response.setSuccess(false);
						response.setError("SERVICE_ERROR_G01b - Numero gara - gara inesistente o non di competenza");
					} else if(gara.getDataCancellazioneGara()!=null) {
						response.setSuccess(false);
						response.setError("SERVICE_ERROR_G01c - Numero gara - la gara e' cancellata. Impossibile procedere.");
					} else {
						List<Lotto> lista = tedDb.getListaLotti(gara.getIdGara());
						String resVal = DeltaGaraValidator.validate(deltaGaraTED, gara, lista);
						if(!"".equals(resVal)) {
							response.setSuccess(false);
							response.setError(resVal);
						}
					}
				}
				
				if(!response.isSuccess())
					return response;
				
				String deltaGaraTEDStr = MarshallerTED.marshalDeltaGaraTED(deltaGaraTED);
				XmlOptions opts = new XmlOptions();
				ArrayList<XmlError> errors = new ArrayList<XmlError>();
				opts.setErrorListener(errors);
				try {
					DeltaGaraWSDocument wsdocument = DeltaGaraWSDocument.Factory.parse(deltaGaraTEDStr, opts);
					  if(!wsdocument.validate(opts)){
						  //this.thereIsAnError = true;
		                  logger.info("Entrato nell if");
		                  this.error = "SERVICE_ERROR_111: \r\n";
		                  this.error += this.aggiungiDescrizioneErroriValidazioneXsd(errors);
		                  throw new SimogWsXmlException(ErrorManager.SIMOGWS_XMLMANAGER_XML_01,this.error);
		              }      
				}catch(SimogWsXmlException xmle){
				    //this.thereIsAnError = true;
					logger.error("eccezione durante la validazione del xml: "+xmle.getMessage());
					xmle.printStackTrace();
					// se this error null accoda messaggio, altrimenti valorizza con messaggio
					this.error = this.error != null ? this.error + xmle.getMessage() : xmle.getMessage() ; 
					//return false;
				} catch (XmlException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				DeltaGaraTedService deltaGaraTedService = new DeltaGaraTedServiceImpl();
				 return deltaGaraTedService.execute(id_gara, collaborazione.getUfficio().getIdUfficio(), deltaGaraTEDStr);

			} else {
				ResponseMessageTED response = new ResponseMessageTED();
				response.setSuccess(false);
				response.setError("SIMOGWS_WSSMANAGER_NULL_15 - NON E' STATA TROVATA ALCUNA SESSIONE VALIDA");

				return response;
			}
		} 

		@WebMethod
		public ResponseMessageTED deltaLottoTED(@WebParam(name = "ticket") String ticket, 
												@WebParam(name = "indexCollaborazione") String indexCollaborazione, 
												@WebParam(name = "cig") String cig, 
												@WebParam(name = "deltaLottoTED") DeltaLottoTED deltaLottoTED) {

			
			logger.info("deltaLottoTed - ticket : " + ticket + " - indexCollaborazione : " + indexCollaborazione + " - CIG : " + cig);

			Collaborazione collaborazione = ticketService.execute(ticket, indexCollaborazione);

			if(collaborazione != null) {
				
				ResponseMessageTED response = new ResponseMessageTED();
				response.setSuccess(true);
				if(cig==null) {
					response.setSuccess(false);
					response.setError("SERVICE_ERROR_C02a - CIG - Il campo e' obbligatorio");
				} else {
					Lotto lotto = tedDb.findByCigAndIdStazioneAppaltante(cig, collaborazione.getUfficio().getIdUfficio());
					if(lotto == null) {
						response.setSuccess(false);
						response.setError("SERVICE_ERROR_C02b - CIG - Il lotto e' inesistente o non di competenza");
					} else if (lotto.getDataCancellazioneLotto() != null){
						response.setSuccess(false);
						response.setError("SERVICE_ERROR_C02c - il lotto e' cancellato");
					} else {
						List<Lotto> listaLotti = tedDb.getListaLotti(lotto.getIdGara());
						String resVal = DeltaLottoValidator.validate(deltaLottoTED, lotto,listaLotti.size()>1);
						
						Integer noLot = deltaLottoTED.getNOLOT();
						if(noLot!=null) {
						
							List<TedDelta> listaTedDelta = tedDb.getListaDeltaLotto(lotto.getIdGara());
							for(TedDelta tedDelta : listaTedDelta) {
								if(!tedDelta.getCig().equals(cig) && 
										tedDelta.getNoLot()!=null && 
										tedDelta.getNoLot().intValue()==noLot) {
									resVal+="SERVICE_ERROR_007b  - Numero lotto – identificativo non valido: valore già associato ad altro CIG";
									break;
								}
							}
						}
						
						if(!"".equals(resVal)) {
							response.setSuccess(false);
							response.setError(resVal);
						}
					}
				}
				
				if(!response.isSuccess())
					return response;
				
				Long noLot = null;
				if(deltaLottoTED.getNOLOT()!=null)
					noLot = deltaLottoTED.getNOLOT().longValue();
				
				
				String deltaLottoTEDStr = MarshallerTED.marshalDeltaLottoTED(deltaLottoTED);
				
				XmlOptions opts = new XmlOptions();
				ArrayList<XmlError> errors = new ArrayList<XmlError>();
				opts.setErrorListener(errors);
				try {
					DeltaLottoWSDocument wsdocument = DeltaLottoWSDocument.Factory.parse(deltaLottoTEDStr, opts);
					if(!wsdocument.validate(opts)){
						//this.thereIsAnError = true;
						logger.info("Entrato nell if");
						this.error = "SERVICE_ERROR_111: \r\n";
						this.error += this.aggiungiDescrizioneErroriValidazioneXsd(errors);
						throw new SimogWsXmlException(ErrorManager.SIMOGWS_XMLMANAGER_XML_01,this.error);
					}      
				}catch(SimogWsXmlException xmle){
					//this.thereIsAnError = true;
					logger.error("eccezione durante la validazione del xml: "+xmle.getMessage());
					xmle.printStackTrace();
					// se this error null accoda messaggio, altrimenti valorizza con messaggio
					this.error = this.error != null ? this.error + xmle.getMessage() : xmle.getMessage() ; 
					//return false;
				} catch (XmlException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				DeltaLottoTedService deltaLottoTedService = new DeltaLottoTedServiceImpl();
				return deltaLottoTedService.execute(cig, collaborazione.getUfficio().getIdUfficio(), deltaLottoTEDStr,noLot);

			} else {
				ResponseMessageTED response = new ResponseMessageTED();
				response.setSuccess(false);
				response.setError("SIMOGWS_WSSMANAGER_NULL_15 - NON E' STATA TROVATA ALCUNA SESSIONE VALIDA");

				return response;
			}
		} 
		
		
		@WebMethod
		public ResponseMessageTED cancellaDeltaGaraTED(@WebParam(name = "ticket") String ticket, 
														@WebParam(name = "indexCollaborazione") String indexCollaborazione, 
														@WebParam(name = "id_gara") Long id_gara) {

			
			logger.info("cancellaDeltaGaraTed - ticket : " + ticket + " - indexCollaborazione : " + indexCollaborazione + " - idGara :" + id_gara);

			Collaborazione collaborazione = ticketService.execute(ticket, indexCollaborazione);

			if(collaborazione != null) {
				CancellaDeltaGaraTedService cancellaDeltaGaraTedService = new CancellaDeltaGaraTedServiceImpl();
				ResponseMessageTED response = cancellaDeltaGaraTedService.execute(id_gara, collaborazione.getUfficio().getIdUfficio());

				return response;
			} else {
				ResponseMessageTED response = new ResponseMessageTED();
				response.setSuccess(false);
				response.setError("ticket : " + ticket + " - indexCollaborazione : " + indexCollaborazione + " - TICKET NOT FOUND OR EXPIRED");

				return response;
			}
		}
		
		@WebMethod
		public ResponseMessageTED cancellaDeltaLottoTED(@WebParam(name = "ticket") String ticket,
														@WebParam(name = "indexCollaborazione") String indexCollaborazione, 
														@WebParam(name = "cig") String cig) {

			
			logger.info("cancellaDeltaLottoTed - ticket : " + ticket + " - indexCollaborazione : " + indexCollaborazione + " - CIG : " + cig);

			Collaborazione collaborazione = ticketService.execute(ticket, indexCollaborazione);

			if(collaborazione != null) {
				CancellaDeltaLottoTedService cancellaDeltaLottoTedService = new CancellaDeltaLottoTedServiceImpl();
				ResponseMessageTED response = cancellaDeltaLottoTedService.execute(cig, collaborazione.getUfficio().getIdUfficio());

				return response;
			} else {
				ResponseMessageTED response = new ResponseMessageTED();
				response.setSuccess(false);
				response.setError("ticket : " + ticket + " - indexCollaborazione : " + indexCollaborazione + " - TICKET NOT FOUND OR EXPIRED");

				return response;
			}
		} 
		
		

		@WebMethod
		public ResponseConsultaGaraTED consultaGaraTED(@WebParam(name = "ticket") String ticket, 
														@WebParam(name = "CIG") String CIG,
														@WebParam(name = "id_gara") String id_gara) {

			ResponseConsultaGaraTED ris = new ResponseConsultaGaraTED();

			if ((ticket != null && !ticket.trim().equals("")) && (CIG != null && !CIG.trim().equals("") && (CIG.length() == 10))) {
				SchedaType doc = null;

				it.avlp.simog.common.beans.ResponseConsultaGara responseConsultaGara = ConsultaGaraActionManager.execute(ticket.trim(), CIG.trim(), "3.04.6.0",true);
				String resp = "";
				
				if (responseConsultaGara.isSuccess()){
				   resp = responseConsultaGara.getGaraXML();

//	      		   of = new SchedaObjectFactory();
//	      		   SchedaType schedaType = of.createSchedaType();

				      SchedaType schedaType = new SchedaType();
	      		   try {
	      				String packageName = schedaType.getClass().getPackage().getName();
	     				
	      				JAXBContext jc = JAXBContext.newInstance(packageName);
	      				Unmarshaller u = jc.createUnmarshaller();
	      				ByteArrayInputStream is = new ByteArrayInputStream(resp.getBytes());
	      
	      				doc = (SchedaType) u.unmarshal(is);
	      				
	      				if(id_gara!=null && !"".equals(id_gara))
	      				   ris.setDeltaGaraTED(tedDb.getDeltaGara(Long.parseLong(id_gara)));
	      				
	      				if(CIG!=null && !"".equals(CIG))
		      				   ris.setDeltaLottoTED(tedDb.getDeltaLotto(CIG));
	      				
	      				if (doc != null) {
	      					ris.setGaraXML(doc);
	      					ris.setError(responseConsultaGara.getError());
	      					ris.setSuccess(responseConsultaGara.isSuccess());
	      				}
	      		   } catch (Exception e) {
	      				e.printStackTrace();
	                  ris.setError((new ErrorManager(ErrorManager.SIMOGWS_XMLMANAGER_STRING_05).getError())  + ": " + resp);
	                  ris.setSuccess(false);
	      		   }
				}
				else{
	               ris.setError(responseConsultaGara.getError());
	               ris.setSuccess(responseConsultaGara.isSuccess());			   
				}
			}else if ((ticket != null && !ticket.trim().equals("")) && (id_gara!=null && !"".equals(id_gara))) {
				SchedaGaraCig doc = null;
				it.avlp.simog.common.beans.ResponseConsultaGara responseConsultaGara = ConsultaGaraActionManager.execute(ticket.trim(), id_gara.trim(), "3.04.6.0", true);
				String resp = ""; 
			      
		         if (responseConsultaGara.isSuccess()){
		            resp = responseConsultaGara.getGaraXML();

	      			//of = new SchedaObjectFactory();
	      			SchedaGaraCig schedaGaraCig = new SchedaGaraCig();

	      			try {
	      				String packageName = schedaGaraCig.getClass().getPackage().getName();
	      				JAXBContext jc = JAXBContext.newInstance(packageName);
	      				Unmarshaller u = jc.createUnmarshaller();
	      				ByteArrayInputStream is = new ByteArrayInputStream(resp.getBytes());
	      
	      				doc = (SchedaGaraCig) u.unmarshal(is);
	      
	      				if(id_gara!=null && !"".equals(id_gara))
		      				   ris.setDeltaGaraTED(tedDb.getDeltaGara(Long.parseLong(id_gara)));
		      				
		      			if(CIG!=null && !"".equals(CIG))
			      		       ris.setDeltaLottoTED(tedDb.getDeltaLotto(CIG));
	      				
	      				if (doc != null) {
	      					ris.setSchedaGaraCig(doc);
	      					ris.setError(responseConsultaGara.getError());
	      					ris.setSuccess(responseConsultaGara.isSuccess());
	      				}
	      			} catch (Exception e) {
	      				e.printStackTrace();
	                  ris.setError((new ErrorManager(ErrorManager.SIMOGWS_XMLMANAGER_STRING_05).getError())  + ": " + resp);
	                  ris.setSuccess(false);
	      			}
		         }
		         else{
	                ris.setError(responseConsultaGara.getError());
	                ris.setSuccess(responseConsultaGara.isSuccess());	            
		         }
			} else{
				ris.success = false;
				ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
			}

			return ris;
		}
		
		@WebMethod
		public ResponseMessageTED pubblicaGaraTED(@WebParam(name = "ticket") String ticket, 
				@WebParam(name = "indexCollaborazione") String indexCollaborazione, 
				@WebParam(name = "id_gara") String id_gara, 
				@WebParam(name = "codice_direttiva") String codice_direttiva, 
				@WebParam(name = "codice_formulario") String codice_formulario, 	
				@WebParam(name = "dataScadenzaPag") String dataScadenzaPag, 
				@WebParam(name = "oraScadenza") String oraScadenza, 
				@WebParam(name = "datiPubblicazione") PubblicazioneWS datiPubblicazione, 
				@WebParam(name = "dataScadenzaRichiestaInvito") String dataScadenzaRichiestaInvito, 
				@WebParam(name = "dataLetteraInvito") String dataLetteraInvito
				) {

			ResponseMessageTED ris = null;
			String datiPubblicazioneXml = null;
			
			if ((ticket != null && !ticket.trim().equals("")) && General.isNumber(indexCollaborazione, false)) {

				try {
					//JAXBElement<PubblicazioneWS> gt = pof.createPubblicazione(datiPubblicazione);
					JAXBContext jaxbContext = JAXBContext.newInstance(PubblicazioneWS.class);
					Marshaller m = jaxbContext.createMarshaller();
					os = null;
					if(datiPubblicazione != null){
					   os = new ByteArrayOutputStream();
					   m.marshal(datiPubblicazione, os);
					}
				} catch (JAXBException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if(os != null){
		         int index = os.toString().indexOf("?", 2) + 2;
	            datiPubblicazioneXml = os.toString().substring(index);

				}

				//Valida i dati delta prima di procedere con la pubblicazione su Simog
				PubblicaGaraTEDActionManager action = new PubblicaGaraTEDActionManager();
				ris = action.execute(ticket, indexCollaborazione, id_gara, codice_direttiva, 
															codice_formulario, dataScadenzaPag, oraScadenza, datiPubblicazioneXml, 
															dataScadenzaRichiestaInvito);
				if(ris.isSuccess() && !"F01".equals(codice_formulario)) {
									
					it.avlp.simog.common.beans.ResponsePubblicazioneBando responsePubblicazioneBando = PubblicazioneBandoActionManager.execute(ticket.trim(), indexCollaborazione, 
							PageHelper.getCurrentDate(), dataScadenzaPag, id_gara.trim(), "", datiPubblicazioneXml, 
							"1", null, oraScadenza, dataScadenzaRichiestaInvito, 
							dataLetteraInvito, null, true);
	
					boolean success =  responsePubblicazioneBando.isSuccess();
	//				boolean success=true;
					if(!success){
						ris = new ResponseMessageTED();
						ris.setStatus("SERVICE_ERROR");
						ris.setStatus_msg(responsePubblicazioneBando.getMessaggio());
						ris.setError(responsePubblicazioneBando.getError());
						ris.setSuccess(responsePubblicazioneBando.isSuccess());
					} else {
						//Se tutto è andato a buon fine, procedi con l'invio del formulario al TED
						try {
							ris = action.sendNotice();
						} catch (TEDErrorException e) {
							ris.setStatus(StatusNoticeEnum.TED_ERROR.getStrStatus());
							ris.setStatus_msg(e.getStatusMessage());
						}
					}
					
				}
			} else {
				ris = new ResponseMessageTED();
				ris.success = false;
				ris.setError(new ErrorManager(ErrorManager.SIMOGWS_ACTIONS_APP_01).getError());
			}
			return ris;
		}
		
		@WebMethod
		public ResponseMessageTED verificaTED(@WebParam(name = "ticket") String ticket, 
												@WebParam(name = "indexCollaborazione") String indexCollaborazione, 
												@WebParam(name = "no_doc_ext") String no_doc_ext) {

			ResponseMessageTED ris = new ResponseMessageTED();
			VerificaTEDActionManager action = new VerificaTEDActionManager();
			ris = action.execute(ticket, indexCollaborazione, no_doc_ext);
			return ris;
		} 
		
		@WebMethod
		public ResponseMessageTED inviaAggiudicazioneTED (@WebParam(name = "ticket") String ticket, 
														@WebParam(name = "indexCollaborazione") String indexCollaborazione, 
														@WebParam(name = "cig") String cig, 
														@WebParam(name = "id_gara") String id_gara, 
														@WebParam(name = "formularioAggiudicazione") FormularioAvvisoAggiudicazione formularioAggiudicazione) {

			
			ResponseMessageTED ris = new ResponseMessageTED();
			InviaAggiudicazioneTEDActionManager action = new InviaAggiudicazioneTEDActionManager();
			ris = action.execute(ticket, indexCollaborazione, cig, id_gara, formularioAggiudicazione);
			

			return ris;
		} 
		
		@WebMethod
		public ResponseMessageTED inviaRettificaTED (@WebParam(name = "ticket") String ticket, 
													@WebParam(name = "indexCollaborazione") String indexCollaborazione, 
													@WebParam(name = "id_gara") String id_gara, 
													@WebParam(name = "no_doc_ojs") String no_doc_ojs, 
													@WebParam(name = "formularioRettifica") FormularioAvvisoRettifica formularioRettifica) {

			

			ResponseMessageTED ris = new ResponseMessageTED();
			InviaRettificaTEDActionManager action = new InviaRettificaTEDActionManager();
			ris = action.execute(ticket, indexCollaborazione, id_gara, no_doc_ojs, formularioRettifica);

			return ris;
		} 
		
		@WebMethod
		public ResponseMessageTED inviaAvvisoModificaTED (@WebParam(name = "ticket") String ticket, 
														@WebParam(name = "indexCollaborazione") String indexCollaborazione, 
														@WebParam(name = "cig") String cig, 
														@WebParam(name = "no_doc_ojs") String no_doc_ojs, 
														@WebParam(name = "formularioModifica") FormularioAvvisoModifica formularioModifica) {

			
			ResponseMessageTED ris = new ResponseMessageTED();
			InviaModificaTEDActionManager action = new InviaModificaTEDActionManager();
			ris = action.execute(ticket, indexCollaborazione, cig, no_doc_ojs, formularioModifica);

			return ris;
		} 
		

		@WebMethod
		public ResponseMessageTED cancellaRichiestaTED(@WebParam(name="ticket") String ticket, 
														@WebParam(name = "indexCollaborazione") String indexCollaborazione,
														@WebParam(name = "no_doc_ext") String no_doc_ext) {
			ResponseMessageTED ris = new ResponseMessageTED();
			System.out.println("Sembra andare");
			CancellaRichiestaTEDActionManager action = new CancellaRichiestaTEDActionManager();
			ris = action.execute(ticket, indexCollaborazione, no_doc_ext);
			
			
			return ris;
			
		}
		
		private String aggiungiDescrizioneErroriValidazioneXsd(ArrayList<XmlError> errors){

			String buff = "";
			for(XmlError error : errors){
		    	if(error.getSeverity() == XmlError.SEVERITY_ERROR){	        	
		
		    		if (error.getCursorLocation().getName()!=null)
		        		buff += error.getCursorLocation().getName() + " -> ";
		
		        	buff += (String) error.toString() + "\r\n";
		
		        	logger.debug(">> " + buff);	
		    	}
	    	}return buff;
		}
}