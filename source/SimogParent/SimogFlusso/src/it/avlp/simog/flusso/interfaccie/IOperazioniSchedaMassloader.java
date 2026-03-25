package it.avlp.simog.flusso.interfaccie;

/**
 * Costanti usate dal WORKFLOW per determinare la fattibilita della Operazione corrente:
 * 
 * Le Operazioni permesse per il massloader.
 * 
 * La classe che implementa (oggetto) viene usata come parametro in ingresso in modo 
 * da vincolare il codice alle sole operazioni permesse.
 * 
 * @author vletizia
 *
 */
public interface IOperazioniSchedaMassloader {

	/**
	 * XSD LIST TYPE
	     <xsd:enumeration value="INSERIMENTO"/>
         <xsd:enumeration value="MODIFICA"/>
         <xsd:enumeration value="ELIMINAZIONE"/>
	 */
	/**
	 * - INSERIMENTO: una o piu schede sono in "inserimento" se NON sono presenti gli id (simog e locale)
	 */
	public final String INSERIMENTO = "INSERIMENTO";
	public final int INDICE_INSERIMENTO = 0;
	
	/**
	 * - MODIFICA: una o piu schede sono in "modifica" se sono presenti gli id (simog e locale)
	 */
	public final String MODIFICA = "MODIFICA";
	public final int INDICE_MODIFICA = 1;
	
	/**
	 * - ELIMINAZIONE: una o piu schede sono in "cancellazione" se sono presenti nella apposita sezione del XML 
	 */
	public final String ELIMINAZIONE = "ELIMINAZIONE";
	public final int INDICE_ELIMINAZIONE = 2;
}
