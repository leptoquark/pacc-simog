package it.avlp.simog.massload.util;

import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.massload.MassLoader;
import it.avlp.simog.massload.xmlbeans.AnomaliaType;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede;
import it.avlp.simog.massload.xmlbeans.FlussoType;
import it.avlp.simog.massload.xmlbeans.LivelloType;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

import org.apache.xmlbeans.XmlCursor;

public abstract class FeedBackWriterBase {
	
	/**
	 * Metodo che si occupa di restituire un "anomalieschede" valido ovvero, se ne esiste gia uno
	 * lo ritorna in modo da poter aggiungere anomalie / successi, altrimenti ne crea una istanza 
	 * che e' ereditariamente attaccata al feedback
	 * 
	 * @param feedBack
	 * @param cig
	 * @param cui
	 * @param progressivo
	 * @return
	 */
   
	protected AnomalieSchede getAnomalieFromFeedBack(FeedBack feedBack, String cig, String cui, int progressivo){
		AnomalieSchede[] anomalieSchede = feedBack.getAnomalieSchedeArray();
		AnomalieSchede anomaliaSchede = null;
		
		// PATCH - VL - CUI CON IL NOVE (ARRIVA SENZA OCCORRE SETTARLO SE NEL CIG E' PRESENTE)
		cui = patchCuiWithoutNine(cig, cui);
		
		for(int i = 0; i < anomalieSchede.length; i++){
			AnomalieSchede anomaliaSchedeCorrente = anomalieSchede[i];
			if(equals(anomaliaSchedeCorrente, cig, cui, progressivo)) anomaliaSchede = anomaliaSchedeCorrente;
		}
		if(anomaliaSchede == null){ 
			anomaliaSchede = feedBack.addNewAnomalieSchede();
			anomaliaSchede.setCIG(cig);
			anomaliaSchede.setCUI(cui);
			anomaliaSchede.setPROGRESSIVO(progressivo);
		}
		return anomaliaSchede;
	}
	/**
	 * Mi interessa solo il valore del cig 
	 * 
	 * @param feedBack
	 * @param cig
	 * @return
	 */
	protected static AnomalieSchede getAnomalieFromFeedBackOnlyCig(FeedBack feedBack, String cig){
		AnomalieSchede[] anomalieSchede = feedBack.getAnomalieSchedeArray();
		AnomalieSchede anomaliaSchede = null;
		
		// prima cerco tra i soli cig senza cui
		for(int i = 0; i < anomalieSchede.length; i++){
			AnomalieSchede anomaliaSchedeCorrente = anomalieSchede[i];
			if(equalsOnlyCig(anomaliaSchedeCorrente, cig) && anomaliaSchedeCorrente.getCUI() == null) anomaliaSchede = anomaliaSchedeCorrente;
		}
		// poi cerco con cui associato (nel caso in cui non sia stato trovato)
		if(anomaliaSchede == null){ 
			for(int i = 0; i < anomalieSchede.length; i++){
				AnomalieSchede anomaliaSchedeCorrente = anomalieSchede[i];
				if(equalsOnlyCig(anomaliaSchedeCorrente, cig)) anomaliaSchede = anomaliaSchedeCorrente;
			}
		}
		// altrimenti ne creo una
		if(anomaliaSchede == null){ 
			anomaliaSchede = feedBack.addNewAnomalieSchede();
			anomaliaSchede.setCIG(cig);
			anomaliaSchede.setPROGRESSIVO(0);
		}
		return anomaliaSchede;
	}
	/**
	 * Nel caso in cui il cig riporti il 9 del vecchio sistema della somma urgenza,
	 * occorre aggiungerlo alla cui, che altrimenti mostrera' lo 0
	 * 
	 * @param cig
	 * @param cui
	 * @return
	 */
	protected String patchCuiWithoutNine(String cig, String cui){
		
		if(cui != null && !"".equals(cui)){
			if(cig != null && cig.length() == 10){
				if(cig.substring(0, 1).equals("9")){
					return cig.concat("-").concat(cui.split("-")[1]);
				}
			}
		}return cui;
	}
//	protected boolean equals(AnomalieSchede anomaliaSchedeCorrente, String cig, String cui, int progressivo ){
//		String localCui = anomaliaSchedeCorrente.getCUI();
//		if(isValid(anomaliaSchedeCorrente.getCIG()) && anomaliaSchedeCorrente.getCIG().equals(cig))
//			// se entrambe i cui sono validi
//			if(anomaliaSchedeCorrente.getCUI()!= null && cui != null){
//				// compara i cui
//				if(anomaliaSchedeCorrente.getCUI().equals(cui)){
//					if(anomaliaSchedeCorrente.getPROGRESSIVO() == progressivo){
//						return true;
//					}
//				}
//			// se entrambe i cui sono non validi
//			}else if((localCui == null || "".equals(localCui)) && (cui == null  || "".equals(cui))){
//				// compara i progressivi
//				if(anomaliaSchedeCorrente.getPROGRESSIVO() == progressivo){
//					return true;
//				}
//			}
//		return false;
//	}
	/**
	 * Metodo equals che mi permette di determinare se l'anomalia corrente e' quella che ha come attributi
	 * gli parametri ultimi tre parametri.
	 *  
	 *  Nota: che per le stringhe null e stringa vuota sono equivalenti.
	 *  Nota2: AnomalieSchede anomaliaSchedeCorrente deve essere NON nullo!
	 * 
	 * @param anomaliaSchedeCorrente
	 * @param cig
	 * @param cui
	 * @param progressivo
	 * @return
	 */	
	protected boolean equals(AnomalieSchede anomaliaSchedeCorrente, String cig, String cui, int progressivo ){
		
		String anomalieCorrenteCig = anomaliaSchedeCorrente.getCIG();
		String anomalieCorrenteCui = anomaliaSchedeCorrente.getCUI();
		int anomalieCorrenteProgressivo = anomaliaSchedeCorrente.getPROGRESSIVO();
		
		if(isEquals(anomalieCorrenteCig, cig)){
			if(anomalieCorrenteProgressivo == progressivo){
				if(isEquals(anomalieCorrenteCui, cui)){
					return true;
				}
			}
		}return false;
	}
	protected static boolean equals(AnomalieSchede anomaliaSchedeOriginale, AnomalieSchede anomaliaSchedeCopia ){
		
		String anomalieCorrenteCig = anomaliaSchedeOriginale.getCIG();
		String anomalieCorrenteCui = anomaliaSchedeOriginale.getCUI();
		int anomalieCorrenteProgressivo = anomaliaSchedeOriginale.getPROGRESSIVO();
		
		if(isEqualsStatic(anomalieCorrenteCig, anomaliaSchedeCopia.getCIG())){
			if(anomalieCorrenteProgressivo == anomaliaSchedeCopia.getPROGRESSIVO()){
				if(isEqualsStatic(anomalieCorrenteCui, anomaliaSchedeCopia.getCUI())){
					return true;
				}
			}
		}return false;
	}
	protected static boolean equalsOnlyCig(AnomalieSchede anomaliaSchedeCorrente, String cig){
		
		String anomalieCorrenteCig = anomaliaSchedeCorrente.getCIG();
		
		if(isEqualsStatic(anomalieCorrenteCig, cig)){
					return true;
		}return false;
	}
//	/**
//	 * Ritorna false solamente se una delle due stringhe e' non nulla e non vuota
//	 * 
//	 * @param string1
//	 * @param string2
//	 * @return
//	 */
//	private boolean isBothStringEmptyOrNull(String string1, String string2){
//		
//		String localS1 = string1 == null ? "" : string1;
//		String localS2 = string2 == null ? "" : string2; 
//		return isEmptyString(localS1) && isEmptyString(localS2);
//	}
	/**
	 * Controlla se le due stringhe sono uguali, le stringhe null sono valorizzate con stringa vuota
	 * 
	 * @param string1
	 * @param string2
	 * @return
	 */
	private boolean isEquals(String string1, String string2){
		
		String localS1 = string1 == null ? "" : string1;
		String localS2 = string2 == null ? "" : string2; 
		return localS1.equals(localS2);		
	}
	private static boolean isEqualsStatic(String string1, String string2){
		
		String localS1 = string1 == null ? "" : string1;
		String localS2 = string2 == null ? "" : string2; 
		return localS1.equals(localS2);		
	}
//	/**
//	 * Ritorna true se la stringa e' vuota
//	 * 
//	 * @param string: stringa non nulla, altrimenti nullPointer
//	 * @return
//	 */
//	private boolean isEmptyString(String string){
//		return "".equals(string);
//	}
	
//	private boolean isValid(String string){
//		if(string != null) return true;
//		return false;
//	}
	
	/**
	 * !ATTENZIONE CODICE DUPLICATO, E' GIA PRESENTE IN VALIDATION BEAN
	 * Isola il codice del messaggio e lo ritorna
	 * 
	 * @param messaggioCompleto
	 * @param isAlreadyCode
	 * @return
	 */
	protected String getCodiceDalMessaggio(String messaggioCompleto, boolean isAlreadyCode){
		String temp = "";
		if(!isAlreadyCode){
			if(messaggioCompleto != null && !"".equals(messaggioCompleto.trim())){
				int position = messaggioCompleto.indexOf(" ");
				if(position > 0 && position <= messaggioCompleto.length()){
					return messaggioCompleto.substring(0, position);
				}
			}return temp;
		}else{
			return messaggioCompleto;
		}
	}
	/**
	 * Metodo che scrive le info flusso sull'oggetto passato
	 * 
	 * @param feedBack
	 */
	public void writeInfo(FeedBack feedBack,int numElaborate,int numCaricate, int errori, int warning){
		feedBack.getInfoFlusso().setNUMELABORATE(numElaborate);
		feedBack.getInfoFlusso().setNUMERRORE(errori);
		feedBack.getInfoFlusso().setNUMWARNING(warning);
		feedBack.getInfoFlusso().setNUMCARICATE(numCaricate);

	}
	/**
	 * Metodo che scrive le info flusso sull'oggetto passato
	 * 
	 * @param feedBack
	 */
	public static void writeInfo(FlussoType flu,Calendar data,int numElaborate,int numErrore,int numWarning,int numCaricate){
		flu.setDATAELABORAZIONE(data);
		flu.setNUMELABORATE(numElaborate);
		flu.setNUMERRORE(numErrore);
		flu.setNUMWARNING(numWarning);
		flu.setNUMCARICATE(numCaricate);

	}
	/**
	 * Valorizza nell'oggetto passato AnomalieSchede as il secondo e il terzo parametro
	 * 
	 * @param as
	 * @param cig
	 * @param progressivo
	 */
	public static void writeAnomalie(AnomalieSchede as,String cig,int progressivo){
		as.setCIG(cig);
		as.setPROGRESSIVO(progressivo);
	}
	/**
	 * @param a
	 * @param codice
	 * @param elemento
	 * @param livello @see validationBean.VALBEAN_SEV_XXX costant
	 * @param descrizione
	 */
	public static void writeAnomalia(AnomaliaType a,String codice,int elemento,String livello,String descrizione){
        a.setCODICE(codice);
        a.setELEMENTO(elemento);
        a.setLIVELLO(LivelloType.Enum.forString(livello));	        
        a.setDESCRIZIONE(descrizione);
	}
	
	
	public static void addMassLoaderVersion(FeedBackDocument feedback, String userName){
		XmlCursor cur = feedback.newCursor();
		cur.toFirstChild();
        //cur.toNextToken();
		
		cur.insertComment(new MassLoader(OrigineSchedaEnum.ND).MASSLOADER_VERSION + " Utente " + userName + " ");		
	}
	
	public static String getStack(Exception e){
		
		// solo per eccezioni non applicative
		
		if(e!= null && e.getMessage() != null && e.getMessage().indexOf("SIMOG") >= 0)
			return "";
		
		// nessuno stack su nullpointer
        if(e instanceof NullPointerException)
           return "";
        
      // StackTraceElement[] vste = e.getStackTrace();  
      
//		Thread thread = Thread.currentThread();
//		StackTraceElement[] vste = new StackTraceElement[0];
//		try{
//			if(thread != null && thread.getStackTrace() != null)
//			   vste = thread.getStackTrace();
//		}catch (Exception ex) {
//			ex.printStackTrace();
//			return ("ECCEZIONE in getStack!: " + ex.getMessage());
//		}
		
		String buff = "";
		
//		if(vste != null){
//		   for (StackTraceElement ste : vste)
//            buff = buff + ste.toString() + "\t";
//		}

		StringWriter sw = new StringWriter();
      e.printStackTrace(new PrintWriter(sw));
      buff = sw.toString().replaceAll("[\\t\\n\\r]+"," ");
      
		return buff;
	}
}
