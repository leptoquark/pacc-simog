package it.anticorruzione.ted.enums;

public enum StatusNoticeEnum {
	
	RECEPTION_ERROR(1,"RECEPTION_ERROR","Errore schema validation o business validation"),
	TED_ERROR(2, "TED_ERROR","Errore dal servizio TED"),
	RECEIVED(3,"RECEIVED","Il formulario e' stato preso in carico"),
	IN_PROGRESS(4,"IN_PROGRESS","Formulario in progress"),
	NOT_PUBLISHED(5,"NOT_PUBLISHED","Formulario non pubblicato"),
	PUBLISHED(6,"PUBLISHED","Formulario pubblicato"),
	VALIDATION_ACCEPTED(7, "VALIDATION_ACCEPTED","Validazione superata (disponibile solo in qualificazione)"),
	QUALITY_SKIPPED(8,"QUALITY_SKIPPED","Saltato controllo qualita' (disponibile solo in qualificazione)"),
	QUALITY_ACCEPTED(9,"QUALITY_ACCEPTED","Controllo qualita' superato (disponibile solo in qualificazione)"),
	QUALIFICATION_ERROR(10,"QUALIFICATION_ERROR", "Errore controllo qualità (disponibile solo in qualificazione)");
	
	
	private int idStato;
	private String status;
	private String descrizione;
	
	StatusNoticeEnum(int idStato, String status,String descrizione){
		this.idStato=idStato;
		this.status=status;
    	this.descrizione = descrizione;
    }

	
    public String getDescrizione() {return descrizione;}
    
    
    public int getIdStato() {
		return idStato;
	}




	public String getStrStatus() {
		return status;
	}
	
	public static StatusNoticeEnum findStatusByStr(String status) {
		for(StatusNoticeEnum stat : StatusNoticeEnum.values()) {
			if(stat.getStrStatus().equals(status))
				return stat;
		}
		
		return null;
	}
	
	public static StatusNoticeEnum findStatusById(int status) {
		for(StatusNoticeEnum stat : StatusNoticeEnum.values()) {
			if(stat.getIdStato()==status)
				return stat;
		}
		
		return null;
	}

}
