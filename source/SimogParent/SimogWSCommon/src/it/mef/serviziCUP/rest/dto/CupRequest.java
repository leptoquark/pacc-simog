package it.mef.serviziCUP.rest.dto;

import java.util.List;

public class CupRequest {

	List<String> elencoCup;

	public CupRequest() {
	}

	public CupRequest(List<String> elencoCup) {
		this.elencoCup = elencoCup;
	}

	public List<String> getElencoCup() {
		return elencoCup;
	}

	public void setElencoCup(List<String> elencoCup) {
		this.elencoCup = elencoCup;
	}

}
