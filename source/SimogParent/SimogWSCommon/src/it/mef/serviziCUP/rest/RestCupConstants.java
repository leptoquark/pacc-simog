package it.mef.serviziCUP.rest;

public enum RestCupConstants {
	
	ERRORE_CUP_INESISTENTE(1);
	
	private Integer codiceErrore; 
	
	public Integer getCodiceErrore() {
		return codiceErrore;
	}



	RestCupConstants(Integer codiceErrore){
		this.codiceErrore=codiceErrore; 
		
	}
	
	public static RestCupConstants getErrorByCodiceErrore(Integer codiceErrore) {
		RestCupConstants result=null; 
		for(RestCupConstants constant : values() ) {
			if (constant.getCodiceErrore() == codiceErrore) {
				result=constant; 
				
			}
		}
		return result; 
	}

}
