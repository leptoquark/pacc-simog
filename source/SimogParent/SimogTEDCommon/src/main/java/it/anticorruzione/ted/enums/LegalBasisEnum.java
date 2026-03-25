package it.anticorruzione.ted.enums;

public enum LegalBasisEnum {
			
		DIR_201424EU("2014/24/EU"),
		DIR_201425EU("2014/25/EU"),
		DIR_201423EU("2014/23/EU"),
		DIR_200981EC("2009/81/EC");
		
	    private String descrizione;
	    public String getDescrizione() {return descrizione;}
	    
	    
	    LegalBasisEnum(String descrizione){
	    	this.descrizione = descrizione;
	    }
	    
	    public static LegalBasisEnum getLegalBasis(String input) {
	    	LegalBasisEnum lista [] = values();
	    	for(int i=0; i<lista.length;i++) {
	    		if (lista[i].getDescrizione().equals(input))
	    			return lista[i];
	    	}
	    	return null;
	    }
		
}
