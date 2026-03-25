package it.avlp.simog.massload.util.comparators;



public class ComparatorFactory {

	public static String TIPOAPPALTOLAVORI = "TIPOAPPALTOLAVORI";
	public static String TIPOAPPALTOFORNITURE = "TIPOAPPALTOFORNITURE";
	public static String CONDIZIONI = "CONDIZIONI";
	public static String REQUISITI = "REQUISITI";	
	public static String AGGIUDICATARI = "AGGIUDICATARI";
	public static String DITTE_AUSILIARIE = "DITTEAUSILIARIE";
	public static String INCARICATI = "INCARICATI";
	public static String POSIZIONI = "POSIZIONI";
	public static String EVENTIMOTIVIVARIANTI = "EVENTIMOTIVIVARIANTI";
	public static String FINANZIAMENTI = "FINANZIAMENTI";
	public static String ANAGRAFICAPARTECIPANTE = "ANAGRAFICAPARTECIPANTE";
	public static String ANAGRAFICARESPONSABILE = "ANAGRAFICARESPONSABILE";
	
	/**
	 * Istanziatore statico, che sceglie il tipo dinamico della 
	 * classe in base alla stringa in ingresso, le stringhe valide
	 * sono le costanti statiche definite sopra.
	 * 
	 * @param comparator_ref
	 * @return MultiComparator con come tipo dinamico il comparatore di competenza
	 * @throws ClassNotFoundException, qualora la stringa immessa non corrisponda a quelle
	 * 	autorizzate.
	 */
	public static MultiComparator getInstance(String comparator_ref) throws ClassNotFoundException{
		MultiComparator comparator = null;
		if(TIPOAPPALTOLAVORI.equals(comparator_ref)){
			comparator = new TipoAppaltoLavoriComparator();
		}else if(TIPOAPPALTOFORNITURE.equals(comparator_ref)){
			comparator = new TipoAppaltoFornitureComparator();
		}else if(CONDIZIONI.equals(comparator_ref)){
			comparator = new CondizioneComparator();
		}else if(REQUISITI.equals(comparator_ref)){
			comparator = new RequisitiComparator();
		}else if(AGGIUDICATARI.equals(comparator_ref)){
			comparator = new AggiudicatarioComparator();
		}else if(DITTE_AUSILIARIE.equals(comparator_ref)){
			comparator = new DittaAusiliariaComparator();
		}else if(INCARICATI.equals(comparator_ref)){
			comparator = new IncaricatoComparator();
		}else if(POSIZIONI.equals(comparator_ref)){
			comparator = new PosizioneComparator();
		}else if(EVENTIMOTIVIVARIANTI.equals(comparator_ref)){
			comparator = new EventiMotiviVariatiComparator();
		}else if(FINANZIAMENTI.equals(comparator_ref)){
			comparator = new FinanziamentoComparator();
		}else if(ANAGRAFICAPARTECIPANTE.equals(comparator_ref)){
			comparator = new AnagraficaPartecipanteComparator();
		}else if(ANAGRAFICARESPONSABILE.equals(comparator_ref)){
			comparator = new AnagraficaResponsabileComparator();
		}else{
			throw new ClassNotFoundException("Comparatore non trovato");
		}
		return comparator;
	}
}
