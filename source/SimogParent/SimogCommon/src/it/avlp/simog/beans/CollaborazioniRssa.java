package it.avlp.simog.beans;


import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper per le collaborazioni in modo tale da 
 * gestire la possibilita' di modifica di una gara
 * 
 * NOTA. per poter modificare la gara o il cfamm
 * deve essere uguale a quello della gara oppure
 * l'utente deve avere una collaborazione con ruolo RSSA
 * della amministrazione della gara
 *
 */
public class CollaborazioniRssa {

	ArrayList<String> cfAmmDoveRssa;
	Collaborazione coll;
	
	private static final String SEP = ",";
	/**
	 * Costruttore, contestualmente costruisco un lista delle amministrazioni
	 * in cui l'utente e' RSSA o RUP che svolge le sue funzioni da SIMOG_MEV in poi
	 * Memorizzo inoltre la collaborazione scelta (scelta effettuata tramite indice)
	 * 
	 * @param colls
	 * @param coll
	 */
	public CollaborazioniRssa(Collaborazioni colls,Collaborazione coll){
		this.coll = coll;
		cfAmmDoveRssa = new ArrayList<String>();
		List<Collaborazione> collaborazioni = java.util.Arrays.asList(colls.getCollaborazioni());
		for(Collaborazione collaborazione : collaborazioni){
			//se e' una collaborazione con ruolo rssa
			if( (collaborazione.getUfficio_profilo().equals(ProfiloEnum.RSSAOLD.codice())) || (collaborazione.getUfficio_profilo().equals(ProfiloEnum.RUP.codice())) ){
				// se non e' gia presente nella lista
				if(!cfAmmDoveRssa.contains(collaborazione.getAzienda_codiceFiscale() + SEP + collaborazione.getUfficio_id())){
					//aggiungi
					cfAmmDoveRssa.add(collaborazione.getAzienda_codiceFiscale() + SEP + collaborazione.getUfficio_id());
				}
			}
		}
	}
	public boolean checkCollaborazione(String cfAmm, String idUfficio){
		//se la collaborazione scelta fa' riferimento alla stessa stazione appaltante della gara
		if((this.coll.getAzienda_codiceFiscale() + SEP + this.coll.getUfficio_id()).equals(cfAmm + SEP + idUfficio)){
			return true;
		//altrimenti cerca se ha una collaborazione con quella stazione appaltante con ruolo rssa
		//che equivale a dire controlla se nella lista c'e' id stazione
		}else{
			return cfAmmDoveRssa.contains(cfAmm + SEP + idUfficio);
		}
	}
	
	public Collaborazione getCollaborazione(){
		return this.coll;
	}
	public ArrayList<String> getCfAmmDoveRssa() {
		ArrayList<String> idSa = new ArrayList();
		
		for (int i = 0; i < cfAmmDoveRssa.size(); i++) {
			String riga = cfAmmDoveRssa.get(i);
			String[] cf = riga.split(SEP);
			idSa.add(cf[1]);
		}
		return idSa;
	}
}
