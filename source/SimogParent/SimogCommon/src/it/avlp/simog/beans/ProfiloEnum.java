package it.avlp.simog.beans;

public enum ProfiloEnum {

	  AMMINISTRATORE("99", "Amministratore del Sistema")
	, AVLP("98","Operatore Sistema Riscossione")
	, RSSAOLD("5","Richiesta CIG (Responsabile SIMOG per la Stazione Appaltante)") // PP elimina RSSA // PP RESPONSABILE SIMOG PER LA STAZIONE APPALTANTE
	, RUP("1","Comunicazione dati ex art. 213 comma 9 Dlgs 50/16 (Responsabile del procedimento)") // PP RESPONSABILE UNICO DEL PROCEDIMENTO
	, OSSREG("66","Osservatorio Regionale")
    , OSSNAZ("101","Referente Simog Osservatorio Nazionale")
    , RPNT("RPNT","Responsabile Piattaforma Negoziazione Telematica")
    , RASA("19","Responsabile Anagrafe Stazione Appaltante")
	;
// PP eliminato	, CS("7", "COMPILATORE SCHEDA");

	public static final String REGIONE_ZERO = "000"; // nessuna limitazione regionale
	public static final String REGIONE_999 = "999";  // nessuna limitazione regionale (solo helpdesk)
	public static final String REGIONE_099 = "099";	
	public static final String REGIONE_020 = "020";  // Sardegna
    private String codProfilo;
    private String descrizione;
    
    public String codice() {return codProfilo;}
    public String descrizione() {return descrizione;}
    
    ProfiloEnum(String codProfilo, String descrizione){
    	this.codProfilo = codProfilo;
    	this.descrizione = descrizione;
    }
    
    public static ProfiloEnum getEnumByProfilo(String profilo) 
    {
    	ProfiloEnum lista [] = values();
    	for(int i=0; i<lista.length;i++) {
    		if (lista[i].codice().equals(profilo))
    			return lista[i];
    	}
    	return null;
    }
} 
