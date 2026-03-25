package it.anticorruzione.ted.enums;


public enum TypeNoticeEnum {

	F01(1,"F01","Avviso di preinformazione"),
	F02(2,"F02","Bando di gara"),
	F03(3,"F03","Avviso di aggiudicazione di appalto"),
	F14(4,"F14","Avviso relativo a informazioni complementari o modifiche"),
	F20(5,"F20","Avviso di modifica");
	
	
	private long idTipo;
	private String tipo;
    private String descrizione;
    
	public long getIdTipo() { return idTipo;}
    public String getTipo() {return tipo;}
    public String getDescrizione() {return descrizione;}
    
    
    TypeNoticeEnum(int idTipo, String tipo, String descrizione){
    	this.idTipo = idTipo;
    	this.tipo = tipo;
    	this.descrizione = descrizione;
    }
    
    public static TypeNoticeEnum getTypeNotice(String input) {
    	TypeNoticeEnum lista [] = values();
    	for(int i=0; i<lista.length;i++) {
    		if (lista[i].getTipo().equals(input))
    			return lista[i];
    	}
    	return null;
    }
}
