/**
 * Web Service TED
 */
package it.anticorruzione.ted.db.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WS_SESSIONS")
public class WsSession implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SESSION_ID")
	private Long sessionId;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "XMLAUTH", columnDefinition= "Text")
	private String xmlAuth;

	@Column(name = "SESSION_END")
	private Date sessionEnd;

	@Column(name = "TICKET")
	private String ticket;

	/**
	 * @return the sessionId
	 */
	public Long getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the xmlAuth
	 */
	public String getXmlAuth() {
		return xmlAuth;
	}

	/**
	 * @param xmlAuth the xmlAuth to set
	 */
	public void setXmlAuth(String xmlAuth) {
		this.xmlAuth = xmlAuth;
	}

	/**
	 * @return the sessionEnd
	 */
	public Date getSessionEnd() {
		return sessionEnd;
	}

	/**
	 * @param sessionEnd the sessionEnd to set
	 */
	public void setSessionEnd(Date sessionEnd) {
		this.sessionEnd = sessionEnd;
	}

	/**
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}