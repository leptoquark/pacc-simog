package it.avlp.simog.beans;

import java.util.HashMap;
import java.util.Map;

import it.avlp.simog.db.Costanti;
import it.avlp.simog.util.SimogProperties;

public enum AnnullataEnum {

	  ANNULLATA_PRIMA("1", "Il venir meno della fonte di finanziamento")
	, ANNULLATA_DOPO("2","Disposizioni sovraordinate")
	;
	  
    private String codAnnullata;
    private String descrizione;
    
    public String codice() {return codAnnullata;}
    public String descrizione() {return descrizione;}
    
    AnnullataEnum(String codEsito, String descrizione){
    	this.codAnnullata = codEsito;
    	this.descrizione = descrizione;
    }
    
    public static AnnullataEnum getEnumByCodice(String codice) 
    {
    	AnnullataEnum lista [] = values();
    	for(int i=0; i<lista.length;i++) {
    		if (lista[i].codice().equals(codice))
    			return lista[i];
    	}
    	return null;
    }
    
    public static Map<String,String> loadAnnullata()
    {
    	Map<String,String> rs = new HashMap<String, String>();
    	AnnullataEnum lista [] = values();
    	for(int i=0; i<lista.length;i++) {
    		rs.put(lista[i].codice(), lista[i].descrizione());
    	}
    	return rs;
    }
    
} 
