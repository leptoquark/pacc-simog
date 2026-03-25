package it.avlp.simog.beans;

import it.avlp.simog.errormessage.Messaggi;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author pp
 * 
 * Contiene le informazioni relative alla eccezione di validazione
 */
public class ValidationBean extends MessageBean {
	
	/*
	 * classi di severit� dell'eccezione
	 */
	public static final String VALBEAN_SEV_INFO = "AVVISO";
	public static final String VALBEAN_SEV_WARN = "ATTENZIONE";
	public static final String VALBEAN_SEV_ERR = "ERRORE";
	
	protected String severity = null;
	protected int elemento = 0;
	protected String codiceErrore = null;
	protected int progressivo = 0;
	

	// nuova gestione, usero una classe che estende questa in modo da gestirmela come piu mi piace
	// pur mantenendo il supporto all'indietro.
	protected ValidationBean(){}
	protected void setProgressivo(int progressivo){ this.progressivo = progressivo; }
	public int getProgressivo(){ return this.progressivo; }
	protected void setCodiceErrore(String messaggioErroreCompleto){ this.codiceErrore = getCodiceDalMessaggio(messaggioErroreCompleto, false); }
	public String getCodiceErrore(){ return this.codiceErrore; }
	
	/***************************************************************************************
	 * Costruttore 
	 * @param message : Stringa
	 * @param severity : Stringa
	 * @param elemento : int
	 * @throws Exception 
	 ***************************************************************************************/
	public ValidationBean(String message, String severity, int elemento) throws Exception {
		super(message);

		if (message==null || "".equals(message)) {
		     try
		      {
		         throw new Exception("Who called me?");
		      }
		      catch( Exception e )
		      {
		    	  System.out.println( "ValidationBean - Messaggio non valido " + 
		                             e.getStackTrace()[1].getClassName() + 
		                             "." +
		                             e.getStackTrace()[1].getMethodName() + 
		                             "()!" );
		         e.printStackTrace();
		      }

			throw new Exception("ValidationBean - Messaggio non valido");
		}
		
		if (!VALBEAN_SEV_INFO.equals(severity) 
				&& !VALBEAN_SEV_WARN.equals(severity)
				&& !VALBEAN_SEV_ERR.equals(severity)) 
			throw new Exception("ValidationBean - Livello severita' non valido");
		
		this.severity = severity;
		this.elemento = elemento;
		this.codiceErrore = getCodiceDalMessaggio(message, false);

	}

	/**
	 * @return livello di severit� dell'eccezione
	 */
	public String getSeverity() {
		return severity;
	}

	public int getElemento() {
		return elemento;
	}
	/**
	 * Isola il codice del messaggio e lo memorizza in locale
	 * @param messaggio
	 */
	private String getCodiceDalMessaggio(String messaggio,boolean isAlreadyCode){
		String temp = "";
		if(!isAlreadyCode){
			if(messaggio != null && !"".equals(messaggio.trim())){
				int position = messaggio.indexOf(" ");
				if(position > 0 && position <= messaggio.length()){
					temp = messaggio.substring(0, position);
					//devo controllare che il codice sia valido
//					@see deprecations..
//					if(isAllowedCode(temp)){
						return temp;
					//altrimenti il codice che identifica messaggio senza codice
//					}else{
//						temp = this.setCodiceAlDefault(temp);
//					}
				}
			}return temp;
		}else{
			return messaggio;
		}
	}
	/**
	 * @deprecated
	 */
	private String SIMOG_MASSLOADER_00 = "SIMOG_MASSLOADER_00";
	/**
	 * @param temp
	 * @return
	 * @deprecated
	 */
	private String setCodiceAlDefault(String temp){
		//StuffDispatcher.getInstance().getLogger().debug("Il codice["+temp+"] non risulta tra i codici");
		return this.SIMOG_MASSLOADER_00;
	}
	/**
	 * Controlla che esista la varibile corrispondente al nome (codice) recuperato
	 * 
	 * @param codice
	 * @return
	 */
	/**
	 * @param codice
	 * @return
	 *  @deprecated
	 */
	private boolean isAllowedCode(String codice){	
		List<Field> listaDeiCampi = Arrays.asList(Messaggi.class.getDeclaredFields());
		for(Field f : listaDeiCampi){
			if(f.getName().equalsIgnoreCase(codice)){
				return true;
			}
		}return false;
	}
}
