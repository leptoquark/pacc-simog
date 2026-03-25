package it.avlp.simog.beans.ws;

import java.sql.Timestamp;

public class WsSessions {

	private long sessionId;
	private String userId;
	private String xmlAuth;
	private String userStatus;	
	private Timestamp sessionStart;
	private Timestamp sessionEnd;
	private String ticket;
	private String lastError;
	private String sessionStatus;
	private String comando;
	private int collaborazione;
	private String rpntId;//TICKET ALM - 3.04.3
	
	
	public String getLastError() {
		return lastError;
	}
	public void setLastError(String lastError) {
		this.lastError = lastError;
	}
	public Timestamp getSessionEnd() {
		return sessionEnd;
	}
	public void setSessionEnd(Timestamp sessionEnd) {
		this.sessionEnd = sessionEnd;
	}
	public long getSessionId() {
		return sessionId;
	}
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}
	public Timestamp getSessionStart() {
		return sessionStart;
	}
	public void setSessionStart(Timestamp sessionStart) {
		this.sessionStart = sessionStart;
	}
	public String getSessionStatus() {
		return sessionStatus;
	}
	public void setSessionStatus(String sessionStatus) {
		this.sessionStatus = sessionStatus;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getXmlAuth() {
		return xmlAuth;
	}
	public void setXmlAuth(String xmlAuth) {
		this.xmlAuth = xmlAuth;
	}
	public String getComando() {
		return comando;
	}
	public void setComando(String comando) {
		this.comando = comando;
	}
	public int getCollaborazione() {
		return collaborazione;
	}
	public void setCollaborazione(int collaborazione) {
		this.collaborazione = collaborazione;
	}
	public String toString(){
		String status = null;
		status = "\r\nSession Id : "	+this.sessionId+"\r\n";
		status += "User Id: "		+this.userId+"\r\n";
		if(this.xmlAuth!=null){
			status += "XmlAuth: "		+"NOT null\r\n";
		}else{
			status += "XmlAuth: "		+"null\r\n";
		}
		status += "User Status: "	+this.userStatus+"\r\n";
		status += "Session Start: "	+this.sessionStart+"\r\n";
		status += "Session End: "	+this.sessionEnd+"\r\n";
		status += "Ticket: "		+this.ticket+"\r\n";
		status += "LastError: "		+this.lastError+"\r\n";
		status += "Session Status: "+this.sessionStatus+"\r\n";
		status += "Comando: "		+this.comando+"\r\n";
		status += "Collaborazione: "+this.collaborazione+"\r\n";
		return status;
	}
	//TICKET ALM - 3.04.3
	public String getRpntId() {
		return rpntId;
	}
	public void setRpntId(String rpntId) {
		this.rpntId = rpntId;
	}
	
	
}
