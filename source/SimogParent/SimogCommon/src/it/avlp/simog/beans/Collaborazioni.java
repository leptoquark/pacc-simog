package it.avlp.simog.beans;

	/**
	 * Classe Bean (contenitore) che contiene un'array (rpc complyence)
	 * di bean Collaborazione, offre dei metodi di ricerca sulle
	 * listaCollaborazioni (su ogni attributo), e in piu un metodo toString()
	 * 
	 * **/

public class Collaborazioni {

	private Collaborazione[] collaborazioni = null;
	
	//--- costruttore no-args come da specifiche rpc-1.1 ---//
	public Collaborazioni(){
		collaborazioni = new Collaborazione[]{};
	}
	public void setCollaborazioni(Collaborazione[] collaborazioni){
		this.collaborazioni = collaborazioni;
	}
	public Collaborazione[] getCollaborazioni(){
		return this.collaborazioni;
	}
	/**
	 * metodo per recuperare una collaborazione per indice
	 * @param index : String
	 * 
	 */
	public Collaborazione getPerIndice(String index){
		for(int i = 0;i<this.collaborazioni.length;i++){			
			Collaborazione coll = this.collaborazioni[i];
			if(coll.getIndex().equals(index)){
				return coll;
			}
		}return null;
	}
	/*	
	 * 	questo sbagliata cardinalit� potrebbe contenere un'insieme di oggetti
	 * 	che contentgono il valore per adesso ritorna il primo che trova..
	 * */
	public Collaborazione getPerAziendaDenominazione(String azienda_denominazione){
		for(int i = 0;i<this.collaborazioni.length;i++){			
			Collaborazione coll = this.collaborazioni[i];
			if(coll.getAzienda_denominazione().equals(azienda_denominazione)){
				return coll;
			}
		}return null;		
	}
	/*	
	 * 	questo sbagliata cardinalit� potrebbe contenere un'insieme di oggetti
	 * 	che contentgono il valore per adesso ritorna il primo che trova..
	 * */
	public Collaborazione getPerAziendaCodiceFiscale(String azienda_codiceFiscale){
		for(int i = 0;i<this.collaborazioni.length;i++){			
			Collaborazione coll = this.collaborazioni[i];
			if(coll.getAzienda_codiceFiscale().equals(azienda_codiceFiscale)){
				return coll;
			}
		}return null;			
	}
	/*	
	 * 	questo sbagliata cardinalit� potrebbe contenere un'insieme di oggetti
	 * 	che contentgono il valore per adesso ritorna il primo che trova..
	 * */
	public Collaborazione getPerUfficioDenominazione(String ufficio_denominazione){
		for(int i = 0;i<this.collaborazioni.length;i++){			
			Collaborazione coll = this.collaborazioni[i];
			if(coll.getUfficio_denominazione().equals(ufficio_denominazione)){
				return coll;
			}
		}return null;		
	}
	/*	
	 * 	questo sbagliata cardinalit� potrebbe contenere un'insieme di oggetti
	 * 	che contentgono il valore per adesso ritorna il primo che trova..
	 * */
	public Collaborazione getPerUfficioProfilo(String ufficio_profilo){
		for(int i = 0;i<this.collaborazioni.length;i++){			
			Collaborazione coll = this.collaborazioni[i];
			if(coll.getUfficio_profilo().equals(ufficio_profilo)){
				return coll;
			}
		}return null;		
	}
	/*	
	 * 	questo sbagliata cardinalit� potrebbe contenere un'insieme di oggetti
	 * 	che contentgono il valore per adesso ritorna il primo che trova..
	 * */
	public Collaborazione getPerUfficioId(String ufficio_id){
		for(int i = 0;i<this.collaborazioni.length;i++){			
			Collaborazione coll = this.collaborazioni[i];
			if(coll.getUfficio_id().equals(ufficio_id)){
				return coll;
			}
		}return null;		
	}
	/**
	 * metodo per recuperare la collaborazione associata al codicefiscale amministratore
	 * e al codicefiscale stazione appaltante
	 * @param cfamm : String
	 * @param cfsa : String
	 * 
	 */
	public Collaborazione getPerCFAMMandCFSA(String cfamm,String cfsa){
		if(this.collaborazioni != null){
			for(int i = 0;i<this.collaborazioni.length;i++){			
				Collaborazione coll = this.collaborazioni[i];
				if(coll.getAzienda_codiceFiscale().equals(cfamm)
				&& coll.getUfficio_id().equals(cfsa)){
					return coll;
				}
			}return null;
		}return null;
	}
	public String toString(){
		String list = "\r\n";
		for(int i = 0;i<this.collaborazioni.length;i++){			
			Collaborazione coll = this.collaborazioni[i];
			list += "Indice Collaborazione: "+coll.getIndex()+"\r\n"+coll.toString()+"\r\n";
		}
		return list;
	}

}
