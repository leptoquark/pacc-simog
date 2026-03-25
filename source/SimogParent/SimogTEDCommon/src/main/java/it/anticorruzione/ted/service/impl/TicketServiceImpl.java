/**
 * Web Service TED
 */
package it.anticorruzione.ted.service.impl;

import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import it.anticorruzione.ted.db.entity.WsSession;
import it.anticorruzione.ted.db.repository.WsSessionRepository;
import it.anticorruzione.ted.db.repositoryImp.WsSessionRepositoryImpl;
import it.anticorruzione.ted.service.TicketService;
import it.anticorruzione.ted.xml.ticket.CheckLogin;
import it.anticorruzione.ted.xml.ticket.Collaborazione;
import it.anticorruzione.ted.xml.ticket.Ufficio;

public class TicketServiceImpl implements TicketService {
	private static final Logger logger = Logger.getLogger(TicketService.class);

	private WsSessionRepository wsSessionRepository = new WsSessionRepositoryImpl();

	@Override
	public Collaborazione execute(String ticket, String indexCollaborazione) {
		try {
			WsSession wsSession = wsSessionRepository.findByTicket(ticket);

			if(wsSession != null) {
				Calendar calendar = Calendar.getInstance();

				if(wsSession.getSessionEnd().after(calendar.getTime())) {
					calendar.add(Calendar.MINUTE, 10);
					
					wsSession.setSessionEnd(calendar.getTime());
					
					wsSessionRepository.merge(wsSession);

					DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

					Element element = documentBuilderFactory.newDocumentBuilder().parse(new ByteArrayInputStream(wsSession.getXmlAuth().getBytes())).getDocumentElement();
					CheckLogin checkLogin = JAXBContext.newInstance(CheckLogin.class).createUnmarshaller().unmarshal(element, CheckLogin.class).getValue();

					List<Collaborazione> listCollaborazione = checkLogin.getCollaborazioni().getCollaborazione();

					if(listCollaborazione != null) {
						for (int cont = 0 ; cont < listCollaborazione.size() ; cont++) {
							Collaborazione collaborazione = listCollaborazione.get(cont);

							Ufficio ufficio = collaborazione.getUfficio();

							if(collaborazione.getIndex().equals(indexCollaborazione)) {
								logger.info("ticket : " + ticket + " - indexCollaborazione : " + collaborazione.getIndex() + " - denominazione : " + ufficio.getDenominazione() + " - idUfficio : " + ufficio.getIdUfficio() + " - profilo : " + ufficio.getProfilo());

								return collaborazione;
							}
						}
					}

					logger.info("ticket : " + ticket + " - indexCollaborazione : " + indexCollaborazione + " - COLLABORAZIONE NOT FOUND");
				} else {
					logger.info("ticket : " + ticket + " - indexCollaborazione : " + indexCollaborazione + " - TICKET EXPIRED");
				}
			} else {
				logger.info("ticket : " + ticket + " - indexCollaborazione : " + indexCollaborazione + " - TICKET NOT FOUND");
			}
		} catch (Exception exception) {
			logger.error(exception);
		}

		return null;
	}
}