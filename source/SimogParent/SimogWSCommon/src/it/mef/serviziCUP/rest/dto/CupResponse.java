package it.mef.serviziCUP.rest.dto;

import java.util.List;

public class CupResponse {
	
	private List<CupDataDto> items;

	public CupResponse(List<CupDataDto> items) {
		this.items = items;
	}

	public List<CupDataDto> getItems() {
		return items;
	}

	public void setItems(List<CupDataDto> items) {
		this.items = items;
	}
	
	public CupResponse() {}
	
	
	
}
