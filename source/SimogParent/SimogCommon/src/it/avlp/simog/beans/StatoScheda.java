package it.avlp.simog.beans;


import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StatoScheda implements Cloneable{

	public final static int NESSUNARICHIESTA = 0;
	public final static int RICHIESTACANCELLAZIONE = 1;
	public final static int RICHIESTAANNULLAMENTO = 2;
	
	private int stato;
	
	private boolean asConfermata = false; // vale a dire bean da XML e valido	
	private boolean aggiudicata = false;
	private boolean lavori = false;
	private boolean settoriSpeciali = false;	
	private boolean esistente = false;
	private boolean isFromXml = false;
	//gm nuovo codice fromDb
	private boolean isFromDb = false;
	
	/***************** tutti i riferimenti utili della scheda BEGIN *****************************/
	/**  NOTA: se il flaf isFromXml non sono presenti questi riferimenti per ovvie ragioni  **/
	
		// rifermenti scheda corrente
		private long idRecord;
		private Timestamp dataInizioRecord;
		private String idLocale;
		
		// se e' lo stato di una scheda diversa da Aggiudicazione e Dati Comuni ho i riferimenti per l'aggiudicazione
		private long idAggiudicazione;
		private Timestamp dataInizioAggiudicazione;
			
		// riferimenti generici piu alto livello 
			// cig presente nello stato di dati comuni e aggiudicazione
		private String cig;
			// cui presente solo nello stato di aggiudicazione
		private String cui;
		
		//is3028_RFWEBSC00Active
		private int origine;
		
		private boolean obblighiComunicativiSpeciali=false;
		
	/***************** tutti i riferimenti utili della scheda END *****************************/
	
	public String toString(){
		String toString = "\r\nRiepilogo riferimenti Scheda \r\n";
		toString += "\t idRecord: "+(idRecord != 0 ? idRecord : "Elmento NON presente")+"\r\n";
		toString += "\t DataInizioRecord: "+(dataInizioRecord != null ? dataInizioRecord : "Elmento NON presente")+"\r\n";
		toString += "\t idLocale: "+(idLocale != null ? idLocale : "Elmento NON presente")+"\r\n";
		toString += "\t idAggiudicazione: "+(idAggiudicazione != 0 ? idAggiudicazione : "Elmento NON presente")+"\r\n";
		toString += "\t DataInizioAggiudicazione: "+(dataInizioAggiudicazione != null ? dataInizioAggiudicazione : "Elmento NON presente")+"\r\n";
		toString += "\t cig: "+(cig != null ? cig : "Elmento NON presente")+"\r\n";
		toString += "\t cui: "+(cui != null ? cui : "Elmento NON presente")+"\r\n";
		toString += "\r\nRiepilogo stato Scheda: \r\n";
		toString += "\t Stato: "+stato+"\r\n";
		toString += "\t asConfermata: "+asConfermata+"\r\n";
		toString += "\t isFromXml: "+isFromXml+"\r\n";
		toString += "\t aggiudicata: "+aggiudicata+"\r\n";
		toString += "\t lavori: "+lavori+"\r\n";
		toString += "\t settoriSpeciali: "+settoriSpeciali+"\r\n";
		toString += "\t confermata: "+this.isConfermata()+"\r\n";
		toString += "\t inRichiesta annullamento: "+this.isInRichiestaAnnullamento+"\r\n";
		toString += "\t inRichiesta cancellazione: "+this.isInRichiestaCancellazione+"\r\n";
		toString += " \r\n"; 
		return toString;
	}
	
	private boolean isInRichiestaAnnullamento = false;
	private boolean isInRichiestaCancellazione = false;
	
	public int getStatoScheda(){
		return this.stato;
	}
	public boolean isInDefinizione() {
		return this.stato == StatiScheda.IN_DEFINIZIONE;
	}
	/**
	 * Nota che questo flag e' vero anche nel caso:
	 * - MASSLOADER: beanFromXml valido (lo si considera inserito)
	 * @return
	 */
	public boolean isConfermata() {
		return this.stato == StatiScheda.CONFERMATO;
	}
	public void impostaAsConfermato(){
		asConfermata = true;
	}
	public void setAsConfermato(boolean asConfermato){
		asConfermata = true;
	}	
	public boolean isAsConfermata() {
		return asConfermata;
	}
	public boolean isInRichiestaAnnullamento() {
		return this.isInRichiestaAnnullamento;
	}
	public void setStato(int stato){
		this.stato = stato;
	}
	/**
	 * Ritorna true se la scheda e' esistente sul db oppure da xml (dopo il datamerge)
	 * @return
	 */
	public boolean isEsistente() {
		return esistente;
	}
	/**
	 * Metodo safe per controllare l'effettiva presenza della scheda sul db.. watch out
	 * @return
	 */
	public boolean isEsistenteDb() {
		return esistente && !isFromXml;
	}
	public void setEsistente(boolean esitente) {
		this.esistente = esitente;
	}
	public boolean isAggiudicata() {
		return aggiudicata;
	}
	public void setAggiudicata(boolean aggiudicata) {
		this.aggiudicata = aggiudicata;
	}
	public boolean isLavori() {
		return lavori;
	}
	public void setLavori(boolean lavori) {
		this.lavori = lavori;
	}
	public boolean isSettoriSpeciali() {
		return settoriSpeciali;
	}
	public void setSettoriSpeciali(boolean settoriSpeciali) {
		this.settoriSpeciali = settoriSpeciali;
	}
	public boolean isInRichiestaCancellazione() {
		return isInRichiestaCancellazione;
	}
	public void setInRichiestaCancellazione(boolean isInRichiestaCancellazione) {
		this.isInRichiestaCancellazione = isInRichiestaCancellazione;
	}
	public void setInRichiesta(int codiceRichiesta){
		
		if(codiceRichiesta == RICHIESTAANNULLAMENTO) this.isInRichiestaAnnullamento = true;
		if(codiceRichiesta == RICHIESTACANCELLAZIONE) this.isInRichiestaCancellazione = true;
		return;

	}
	public long getIdRecord() {
		return idRecord;
	}
	public String getIdRecordAsString(){
		if(this.idRecord == 0 || this.idRecord == -1) return "";
		return String.valueOf(idRecord);
	}
	public void setIdRecord(long idRecord) {
		this.idRecord = idRecord;
	}
	public Timestamp getDataInizioRecord() {
		return dataInizioRecord;
	}
	public void setDataInizioRecord(Timestamp dataInizioRecord) {
		this.dataInizioRecord = dataInizioRecord;
	}
	public String getIdLocale() {
		return idLocale;
	}
	public void setIdLocale(String idLocale) {
		this.idLocale = idLocale;
	}
	public String getCig() {
		return cig;
	}
	public void setCig(String cig) {
		this.cig = cig;
	}
	public String getCui() {
		return cui;
	}
	public void setCui(String cui) {
		this.cui = cui;
	}
	public long getIdAggiudicazione() {
		return idAggiudicazione;
	}
	public void setIdAggiudicazione(long idAggiudicazione) {
		this.idAggiudicazione = idAggiudicazione;
	}
	public Timestamp getDataInizioAggiudicazione() {
		return dataInizioAggiudicazione;
	}
	public void setDataInizioAggiudicazione(Timestamp dataInizioAggiudicazione) {
		this.dataInizioAggiudicazione = dataInizioAggiudicazione;
	}
	public boolean isFromXml() {
		return isFromXml;
	}
	public void setFromXml(boolean isFromXml) {
		this.isFromXml = isFromXml;
	}
	//gm nuovo codice fromDb
	public boolean isFromDb() {
		return isFromDb;
	}
	public void setFromDb(boolean isFromDb) {
		this.isFromDb = isFromDb;
	}
	
	/**
	 * Serve in fase di workflow
	 */
	public void impostaComeEsistenteEDaXml(String cig,String cui, String idLocale){
		this.isFromXml = true;
		this.esistente = true;
		this.asConfermata = true;
		this.cig = cig;
		this.cui = cui;
		this.idLocale = idLocale;
	}
	
	/**
	 * Crea un'array list delle dimensioni dell'argomento valorizzato con stati scheda
	 * che sono impostati come presi da xml.
	 * 
	 * @param size
	 * @return
	 */
	public static ArrayList<StatoScheda> impostaTutteDaXML(String cig,String cui, List<?> list, Class<?> clazz){
		try{
			ArrayList<StatoScheda> listOfStati = new ArrayList<StatoScheda>();
			int i = 0;
			Iterator<?> iterator = list.iterator();
			while(iterator.hasNext()){
				StatoScheda statoScheda = new StatoScheda();
				Object obj = iterator.next();
				Method mGetIdLocale = clazz.getMethod("getIdLocale", (Class[])null);
				String idLocale = (String)mGetIdLocale.invoke(clazz.cast(obj), (Object[])null);
				statoScheda.impostaComeEsistenteEDaXml(cig,cui, idLocale);
				listOfStati.add(statoScheda);
				i++;
			}return listOfStati;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Controlla se una lista di stati proviene dal xml
	 * 
	 * @param size
	 * @return
	 */
	public static boolean isFromXML(ArrayList<StatoScheda> listOfStati){
		boolean isFromXml = false;
		if(listOfStati != null && !listOfStati.isEmpty()){
			isFromXml = true;
			for(StatoScheda statoCorrente : listOfStati){
				isFromXml = isFromXml && statoCorrente.isFromXml();
			}
		}return isFromXml;
	}
	
	/**
	 * crea una copia di questa istanza non vincolata con l'ereditarieta'
	 * 
	 * @see java.lang.Object#clone()
	 */
	public StatoScheda clone() throws CloneNotSupportedException{
		StatoScheda statoScheda = (StatoScheda)super.clone();
		return statoScheda;
	}
	
	public static StatoScheda getStatoSchedaPresenteDb(){
		StatoScheda statoCorrente = new StatoScheda();
		statoCorrente.setEsistente(true);
		return statoCorrente;
	}
	public static ArrayList<StatoScheda> getListStatoSchedaPresenteDb(){
		ArrayList<StatoScheda> listOfStatiCorrenti = new ArrayList<StatoScheda>();
		listOfStatiCorrenti.add(StatoScheda.getStatoSchedaPresenteDb());
		return listOfStatiCorrenti;
	}
   public int getOrigine() {
      return origine;
   }
   public void setOrigine(int i) {
      this.origine = i;
   }
   
	public boolean isObblighiComunicativiSpeciali() {
		return obblighiComunicativiSpeciali;
	}
	public void setObblighiComunicativiSpeciali(boolean obblighiComunicativiSpeciali) {
		this.obblighiComunicativiSpeciali = obblighiComunicativiSpeciali;
	}

	
}
