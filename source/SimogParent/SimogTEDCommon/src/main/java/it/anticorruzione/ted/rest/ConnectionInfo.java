package it.anticorruzione.ted.rest;

import it.avlp.simog.util.SimogProperties;

public class ConnectionInfo {

	private String username;
	private String password;
	private String url;
	
	//Costruttore usato solo per test
	public ConnectionInfo(boolean test) {
		username = "TEDUTDD8";
		password = "TEDeSender";
		url = "https://esentool.ted.europa.eu/api/simulation/latest/notice/";
	}
	
	public ConnectionInfo() {
		this.username = SimogProperties.getInstance().getUsernameTed();
		this.password = SimogProperties.getInstance().getPwdTed();
		this.url = SimogProperties.getInstance().getUrlTed();
	}
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getUrl() {
		return url;
	}
	
	
}
