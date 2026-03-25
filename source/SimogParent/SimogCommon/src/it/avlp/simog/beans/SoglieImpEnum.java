package it.avlp.simog.beans;

import it.avlp.simog.db.SimogFlags;

import java.util.HashMap;
import java.util.Map;


public enum SoglieImpEnum {

	 S0("0", "indeterminata", "-2", "-1")
	,S1("1", "da 0 fino ad un importo inferiore a 150.000", "0","149999.999")
	,S2("2", "da 150.000 fino ad un importo inferiore a 500.000", "150000","499999.999")
	,S3("3", "da 500.000 fino ad un importo inferiore a 1.000.000", "500000","999999.999")
	,S4("4", "da 1.000.000 fino ad un importo inferiore a 5.000.000", "1000000","4999999.999")
	,S5("5", "oltre 5.000.000", "5000000", "999999999999.999")
	// is3027_SOGLIAActive nuove fasce
    ,N0("0", "indeterminata", "-2", "-1")
    ,N1("11", "da 0 fino ad un importo inferiore a 40.000", "0","40000.000")
    ,N2("12", "da 40.000 fino ad un importo inferiore a 500.000", "40000.001","499999.999")
    ,N3("13", "da 500.000 fino ad un importo inferiore a 1.000.000", "500000","999999.999")
    ,N4("14", "da 1.000.000 fino ad un importo inferiore a 5.000.000", "1000000","4999999.999")
    ,N5("15", "oltre 5.000.000", "5000000", "999999999999.999")
	;

    private String codSoglia;
    private String descrizione;
    private String min;
    private String max;
    
    public String codice() {return codSoglia;}
    public String descrizione() {return descrizione;}
    public String min() {return min;}
    public String max() {return max;}
    
    SoglieImpEnum(String codSoglia, String descrizione, String min, String max){
    	this.codSoglia = codSoglia;
    	this.descrizione = descrizione;
    	this.min = min;
    	this.max = max;
    }
    
    public static SoglieImpEnum getEnumByCodice(String codice) 
    {
    	SoglieImpEnum lista [] = values();
    	for(int i=0; i<lista.length;i++) {
    		if (lista[i].codice().equals(codice))
    			return lista[i];
    	}
    	return null;
    }
    
    public static Map<String, String> loadSoglieImporto()
    {
    	Map<String, String> rs = new HashMap<String, String>(); 
    	SoglieImpEnum lista [] = values();
    	int low = (SimogFlags.is3027_SOGLIAActive() ? 6 : 0);
    	int high = (SimogFlags.is3027_SOGLIAActive() ? 12 : 6);
    	for(int i=low; i<high;i++) {
    		rs.put(lista[i].codice(), lista[i].descrizione());
    	}
    	return rs;
    }
    
} 
