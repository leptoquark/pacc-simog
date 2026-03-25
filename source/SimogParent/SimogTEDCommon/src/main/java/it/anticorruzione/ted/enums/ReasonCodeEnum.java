package it.anticorruzione.ted.enums;

public enum ReasonCodeEnum {

	CP("CP","NOT_PUBLISHED_010 - Cancel Publication - Pubblicazione cancellata su richiesta dell'utente"),
	CPV("CPV","NOT_PUBLISHED_020 - Wrong CPV - TED Message: "),
	DU("DU","NOT_PUBLISHED_030 - Duplicate - TED Message"),
	HR("HR","NOT_PUBLISHED_040 - Heading Authorization Refused - TED Message"),
	ILD("ILD","NOT_PUBLISHED_050 - Illegible Demfax - TED Message"),
	IN("IN","NOT_PUBLISHED_060 - Incomplete document - TED Message"),
	MD("MD","NOT_PUBLISHED_070 - Modification - TED Message"),
	NP("NP","NOT_PUBLISHED_080 - Not for Publication. - TED Message"),
	NA("NA","NOT_PUBLISHED_090 - No answer to DemFax - TED Message"),
	OD("OD","NOT_PUBLISHED_100 - Other Departement - TED Message"),
	OT("OT","NOT_PUBLISHED_110 - Other Reason - TED Message"),
	PNP("PNP","NOT_PUBLISHED_120 - Prepared not published - TED Message"),
	WFN("WNF","NOT_PUBLISHED_130 - Wrong Form - TED Message"),
	WFI("WFI","NOT_PUBLISHED_140 - Wrong Form Awarding authority information - TED Message"),
	WL("WL","NOT_PUBLISHED_150 - Wrong Language - TED Message"),
	NDX("NDX","NOT_PUBLISHED_160 - NoDocExt Already Exist - TED Message");
	
	private String tipo;
    private String descrizione;

    public String getTipo() {return tipo;}
    public String getDescrizione() {return descrizione;}
    
    
    ReasonCodeEnum(String tipo, String descrizione){
    	this.tipo = tipo;
    	this.descrizione = descrizione;
    }
    
    public static ReasonCodeEnum getReasonCodeMessage(String input) {
    	ReasonCodeEnum lista [] = values();
    	for(int i=0; i<lista.length;i++) {
    		if (lista[i].getTipo().equals(input))
    			return lista[i];
    	}
    	return null;
    }

}
