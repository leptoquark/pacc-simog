package it.avlp.simog.flusso.interfaccie;

/**
 * Costanti usate dal WORKFLOW per determinare la fattibilit� della Operazione corrente:
 * 
 * Le Operazioni permesse per il web (SimogWeb).
 * 
 * La classe che implementa (oggetto) viene usata come parametro in ingresso in modo 
 * da vincolare il codice alle sole operazioni permesse.
 * 
 * 
 * @author vletizia
 *
 */
public interface IOperazioniSchedaWeb {

	/**
	 * - CREAZIONE: Operazione di creazione (primo inserimento)
	 */
	public final String CREAZIONE = "CREAZIONE";
	public final int INDICE_CREAZIONE = 3;
	
	/**
	 * - CONFERMA: Operazione di conferma
	 */
	public final String CONFERMA = "CONFERMA";
	public final int INDICE_CONFERMA = 4;
	
	/**
	 * - RICHIESTA_ANNULLAMENTO: operazione di richiesta annullamento
	 */
	public final String RICHIESTA_ANNULLAMENTO = "RICHIESTA_ANNULLAMENTO";
	public final int INDICE_RICHIESTA_ANNULLAMENTO = 5;
	
	/**
	 * - PRESA_IN_CARICO operazione di presa in carico.
	 */
	public final String PRESA_IN_CARICO = "PRESA_IN_CARICO";
	public final int INDICE_PRESA_IN_CARICO = 6;
	
	/**
	 * - VARIAZIONE_ANAG operazione di variazione anagrafica.
	 */
	public final String VARIAZIONE_ANAG = "VARIAZIONE_ANAG";
	public final int INDICE_VARIAZIONE_ANAG = 7;
    /**
     * - VARIAZIONE_SA operazione di variazione SA
     */
    public final String VARIAZIONE_SA = "VARIAZIONE_SA";
    public final int INDICE_VARIAZIONE_SA = 8;
}
