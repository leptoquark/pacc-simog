package it.avlp.simog.beans;

public class StatiScheda {

	public static final int IN_DEFINIZIONE= 1;
	public static final int CONFERMATO= 2;
	public static final int ANNULLAMENTO_RICHIESTA= 3;
	public static final int ANNULLATO= 4;
	public static final int ELIMINATO= 5;
	public static final int PRESA_IN_CARICO = 6;

	public static final String IN_DEFINIZIONE_STRING = String.valueOf(IN_DEFINIZIONE).trim();
	public static final String CONFERMATO_STRING = String.valueOf(CONFERMATO).trim();
	public static final String ANNULLAMENTO_RICHIESTA_STRING =  String.valueOf(ANNULLAMENTO_RICHIESTA).trim();
	public static final String ANNULLATO_STRING = String.valueOf(ANNULLATO).trim();
	public static final String ELIMINATO_STRING = String.valueOf(ELIMINATO).trim();
	public static final String PRESA_IN_CARICO_STRING = String.valueOf(PRESA_IN_CARICO).trim();

	// PP B302.2.0
	public static final String VARIAZIONE_CO_STRING = "Variazioni Anagrafiche C.O.";
		
	private int idStato;
	private String descrizione;
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getIdStato() {
		return idStato;
	}
	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}
}
