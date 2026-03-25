/**
 * Web Service TED
 */
package it.anticorruzione.ted.service;

import it.anticorruzione.ted.xml.ticket.Collaborazione;

public interface TicketService {
	public Collaborazione execute(String ticket, String indexCollaborazione);
}