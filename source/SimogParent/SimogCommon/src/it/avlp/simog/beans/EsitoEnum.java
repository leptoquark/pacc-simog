package it.avlp.simog.beans;

import java.util.HashMap;
import java.util.Map;

import it.avlp.simog.db.Costanti;
import it.avlp.simog.util.SimogProperties;

public enum EsitoEnum {

	  AGGIUDICATA("1", "Aggiudicata")
	, ANNULLATA("2","Annullata/Revocata successivamente alla pubblicazione")
	, DESERTA("3","Deserta")
	, SENZA_ESITO("4","Senza esito a seguito di offerte irregolari/inammissibili, non congrue o non appropriate")
	, PROPOSTA_AGGIUDICAZIONE("5","Proposta di aggiudicazione")
	, ANNULLATA_PRIMA("6","Annullata/Revocata prima dell'apertura delle buste amministrative")
	, ANNULLATA_DOPO("7","Annullata/Revocata dopo l'apertura delle buste amministrative")
	;
	  
    private String codEsito;
    private String descrizione;
    
    public String codice() {return codEsito;}
    public String descrizione() {return descrizione;}
    
    EsitoEnum(String codEsito, String descrizione){
    	this.codEsito = codEsito;
    	this.descrizione = descrizione;
    }
    
    public static EsitoEnum getEnumByCodice(String codice) 
    {
    	EsitoEnum lista [] = values();
    	for(int i=0; i<lista.length;i++) {
    		if (lista[i].codice().equals(codice))
    			return lista[i];
    	}
    	return null;
    }
    
    public static Map<String,String> loadEsitiProcedura(String dataCreazione, String dataScadenza, int idfdelega)
    {
    	Map<String,String> rs = new HashMap<String, String>();
    	EsitoEnum lista [] = values();
    	for(int i=0; i<lista.length;i++) {
    		//Non caricare le voci "Aggiudicata" e "Proposta di Aggiudicazione" se la gara ha completato solo la prima fase di perfezionamento
    		if(dataScadenza==null && (lista[i].codice()==AGGIUDICATA.codice() || lista[i].codice().equals(PROPOSTA_AGGIUDICAZIONE.codice())))
    			continue;

	    		//Non caricare la voce "Aggiudicata" se la gara ha la delega "Proposta di Aggiudicazione"
	    		if(idfdelega==Costanti.IDF_PROPOSTA_AGGIUDICAZIONE && lista[i].codice().equals(AGGIUDICATA.codice()))
	    			continue;
	    		
	    		//Non caricare la voce "Proposta di aggiudicazione" se la gara non ha delega "Proposta di Aggiudicazione" oppure non ha proprio delega
	    		if((idfdelega == 0 || idfdelega!=Costanti.IDF_PROPOSTA_AGGIUDICAZIONE) && lista[i].codice().equals(PROPOSTA_AGGIUDICAZIONE.codice()))
	    			continue;
    		
	    		//Se la gara e' stata creata dopo simog 3.04.5.1 non caricare la vecchia voce di annullamento
	    		if(lista[i].codice()==ANNULLATA.codice() && SimogProperties.getInstance().isDataCreatedAfter30452(dataCreazione)) 
	    			continue;
	    		
	    		//Se la gara e' stata creata prima del rilascio di simog 3.04.5.1 non caricare la nuova voce di annullamento
	    		if((lista[i].codice()==ANNULLATA_PRIMA.codice() || lista[i].codice()==ANNULLATA_DOPO.codice()) 
	    				&& !SimogProperties.getInstance().isDataCreatedAfter30452(dataCreazione)) 
	    			continue;
	    		
	    		
    		rs.put(lista[i].codice(), lista[i].descrizione());
    	}
    	return rs;
    }
    
} 
