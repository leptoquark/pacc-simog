/**
 * Web Service TED
 */
package it.anticorruzione.ted.db.repository;

import it.anticorruzione.ted.db.entity.WsSession;

public interface WsSessionRepository {
	public WsSession findByTicket(String ticket);
	public Boolean merge(WsSession wsSession);
}