package it.avlp.simog.massload.cancellazione.eccezione;

import it.avlp.simog.beans.IdsScheda;

/**
 * Eccezione che gestisce il fallimento di un'operazione di cancellazione tornando 
 * la scheda che ha generato il problema.
 * Attenzione: questa eccezione non dovrebbe venire fuori, perche vorrebbe dire 
 * che l'operazione sql di cancellazione non ha dato errore ma non e' stata
 * trovata alcuna scheda li dove se ne aspettava una.
 * In sintesi eccezione che notifica quando l'operazione di cancellazione non ha modificato
 * nulla perche non ha trovato il record.
 * In generale potrebbe succedere che sia gia stata cancellata nello stesso flusso.
 * 
 * @author vletizia
 *
 */
public class EliminazioneFallitaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6803787042218117826L;

	private IdsScheda rifScheda;
	
	public EliminazioneFallitaException(String arg0,IdsScheda rifScheda) {
		super(arg0);
		this.rifScheda = rifScheda;
	}

	public IdsScheda getRiferimenti(){
		return this.rifScheda;
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
	
	

}
