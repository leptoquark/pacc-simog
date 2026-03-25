package it.avlp.simog.beans;

import it.avlp.simog.db.Costanti;

/**
 * Bean rappresentante un CIG
 *
 */
public class CIGBean {
	private String cig;
	private int cigCicle;
	private String cigKKK;
	
	//PP campi per storica attribuzione CIG
	private String applicazione;
	private String cfUtente;
	private String cfAmministrazione;
	private String cfStazione;
	
	public static final String APPL_SIMOG = "SIMOG";
	public static final String APPL_WS = "GENERACIG"; // PP il campo ito char e saltano i controlli !
    public static final String APPL_TEST = "TEST";
	
	public CIGBean(){}
	public CIGBean(String applicazione, String cfUtente, String cfAmministrazione, String cfStazione) {
		this.applicazione = applicazione;
		this.cfUtente = cfUtente;
		this.cfAmministrazione = cfAmministrazione;
		this.cfStazione = cfStazione;
	}
	
	/**
	 * metodi per la gestione della codifica della somma urgenza
	 */
	public final static String CIFRA_SOMMA_URGENZA = "9";
	public final static String ZERO = "0";
		
	/**
	 * verifica se il CIG / CUI ha la cifra di somma urgenza
	 * @param feCig
	 * @return true se esiste il nove all'inizio, altrimenti false
	 */
	public static boolean isSommaUrgenza(String feCig){
		
		return CIFRA_SOMMA_URGENZA.equals(feCig.substring(0,1));
	}

	/**
	 * scrematura della somma urgenza dal codice CIG o CUI
	 * @param feCig
	 * @return il CIG / CUI senza la cifra di somma urgenza
	 */
	public static String getRealCIG(String feCig){
		
		String retVal = feCig;
		
		if (isSommaUrgenza(feCig)) {
			//MAC #27466 - verifica se il CIG che inizia con 9 se sia un CIG reale oppure sia un cig che inizia con 0
			long cig = Long.parseLong(feCig.substring(0, 7));
			long kkk = (cig*211)%4091;
			String cigKKK = Long.toHexString(kkk).toUpperCase();
			cigKKK = "000" + cigKKK ;
			cigKKK = cigKKK.substring(cigKKK.length() - 3);
			if(!feCig.substring(7).equalsIgnoreCase(cigKKK))
			    retVal =  ZERO + feCig.substring(1);
		}
		
		return retVal;
	}

	/**
	 * restituisce il cig completo a partire dalle sue componenti
	 * @param flagSommaUrgenza
	 * @param cig
	 * @param cigKKK
	 * @return
	 */
	public static String getFullCIG(String flagSommaUrgenza, String cig, String cigKKK, String dataCreazione){ 
		
		String retVal = cig + cigKKK;
		
		// PP aggiunto per sicurezza controllo sulla prima cifra
		if ((dataCreazione == null || "".equals(dataCreazione)) 
			 && Costanti.FLAG_VALORE_SI.equals(flagSommaUrgenza)
			 && cig.substring(0,1).equals(ZERO))
			retVal =  CIFRA_SOMMA_URGENZA + cig.substring(1) + cigKKK;
	
		return retVal;
	}

	/**
	 * restituisce la parte numerica del CIG
	 * @param feCig
	 * @return il CIG 
	 */
	public static String getCIGPart(String feCig){
		
		String retVal = feCig;
		// PP aggiunto per sicurezza controllo sulla prima cifra che deve essere zero
		retVal = isSommaUrgenza(feCig) && feCig.substring(0,1).equals(ZERO) ? ZERO + feCig.substring(1,7) : feCig.substring(0,7);	
		return retVal;
	}

	/**
	 * restituisce il KKK
	 * @param feCig
	 * @return il CIG 
	 */
	public static String getCIGKKK(String feCig){
		
		String retVal = feCig;
		
		retVal = feCig.substring(7,10);
		return retVal;
	}

	/**
	 * aggiunge la somm aurgenza al cig
	 * @param flagSommaUrgenza
	 * @param cig
	 * @param cigKKK
	 * @return
	 */
	public static String getFeCig(String flagSommaUrgenza, String cig, String dataCreazione){
		
		String retVal = cig ;
			
		// PP aggiunto per sicurezza controllo sulla prima cifra che deve essere zero
		if ((dataCreazione == null || "".equals(dataCreazione)) 
			&& Costanti.FLAG_VALORE_SI.equals(flagSommaUrgenza)
			&& cig.substring(0, 1).equals(ZERO))
			retVal =  CIFRA_SOMMA_URGENZA + cig.substring(1);
	
		return retVal;
	}

	public String getCig() {
		return cig;
	}
	public void setCig(String cig) {
		this.cig = cig;
	}
	public int getCigCicle() {
		return cigCicle;
	}
	public void setCigCicle(int cigCicle) {
		this.cigCicle = cigCicle;
	}
	public String getCigKKK() {
		return cigKKK;
	}
	public void setCigKKK(String cigKKK) {
		this.cigKKK = cigKKK;
	}
	public String getApplicazione() {
		return applicazione;
	}
	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}
	public String getCfAmministrazione() {
		return cfAmministrazione;
	}
	public void setCfAmministrazione(String cfAmministrazione) {
		this.cfAmministrazione = cfAmministrazione;
	}
	public String getCfStazione() {
		return cfStazione;
	}
	public void setCfStazione(String cfStazione) {
		this.cfStazione = cfStazione;
	}
	public String getCfUtente() {
		return cfUtente;
	}
	public void setCfUtente(String cfUtente) {
		this.cfUtente = cfUtente;
	}
	

}
