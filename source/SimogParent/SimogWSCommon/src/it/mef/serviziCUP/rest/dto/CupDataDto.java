package it.mef.serviziCUP.rest.dto;

public class CupDataDto {
	private String cupId;
	private String codiceInvestimento;
	private String tipoPiano;
	private Integer error;
	
	public CupDataDto() {}
	public CupDataDto(String cupId, String codiceInvestimento, String tipoPiano, Integer error) {
		this.cupId = cupId;
		this.codiceInvestimento = codiceInvestimento;
		this.tipoPiano = tipoPiano;
		this.error = error;
	}

	public String getCupId() {
		return cupId;
	}

	public Integer getErrorCode() {
		return error;
	}
	
	public void setCupId(String cupId) {
		this.cupId = cupId;
	}

	public String getCodiceInvestimento() {
		return codiceInvestimento;
	}

	public void setCodiceInvestimento(String codiceInvestimento) {
		this.codiceInvestimento = codiceInvestimento;
	}

	public String getTipoPiano() {
		return tipoPiano;
	}

	public void setTipoPiano(String tipoPiano) {
		
		
		
		this.tipoPiano = tipoPiano;
	}
	
	
	
	

}
