package it.avlp.simog.beans;

import java.util.List;

/**
 * Questa classe nasce dall'esigenza di poter recuperare maggiori informazioni dal bean della 
 * validazione, al fine di scrivere un feedback piu preciso.
 * 
 * @author brins
 *
 */
public class SchedaSpecificaValidationBean extends ValidationBean {

	private static final long serialVersionUID = 1L;
	
	private String nomeScheda;
	private int progressivoSchedaCompleta;
	private String cig;
	private String cui;
	private String idSimog;
	private String idLocale;
	/**
	 * @param message
	 * @param severity
	 * @param elemento: progressivita' all'interno di elementi multipli
	 * @param nomeScheda: vedi le costanti in IdentificativoScheda
	 * @throws Exception
	 */
	public SchedaSpecificaValidationBean(String message, String severity, 
										int elemento,int progressivoSchedaCompleta,int progressivoSchedaMultipla, 
										String nomeScheda, String cig, String cui){
//		super(message, severity, elemento);
		super();
		this.message = message;
		this.severity = severity;
		this.elemento = elemento;
		this.progressivo = progressivoSchedaMultipla;
		this.nomeScheda = nomeScheda;
		this.progressivoSchedaCompleta = progressivoSchedaCompleta;
		setCodiceErrore(message);
		this.cig = cig;
		this.cui = cui;
	}
	public SchedaSpecificaValidationBean(String message, String severity, 
			int elemento,int progressivoSchedaCompleta,int progressivoSchedaMultipla, 
			String nomeScheda, String cig, String cui, String idSimog, String idLocale){
//		super(message, severity, 0);
		super();
		this.message = message;
		this.severity = severity;
		this.nomeScheda = nomeScheda;
		this.elemento = elemento;
		this.progressivo = progressivoSchedaMultipla;
		this.progressivoSchedaCompleta = progressivoSchedaCompleta;
		this.idLocale = idLocale;
		this.idSimog = idSimog;
		setCodiceErrore(message);
		this.cig = cig;
		this.cui = cui;
	}
	public SchedaSpecificaValidationBean(String message, String severity, 
			String nomeScheda, String cig, String cui, String idSimog, String idLocale){
//		super(message, severity, 0);
		super();
		this.message = message;
		this.severity = severity;
		this.nomeScheda = nomeScheda;
		this.progressivo = 0;
		this.progressivoSchedaCompleta = 0;
		this.idLocale = idLocale;
		this.idSimog = idSimog;
		setCodiceErrore(message);
		this.cig = cig;
		this.cui = cui;
	}
	public String getNomeScheda() {
		return nomeScheda;
	}
	public int getProgressivoSchedaCompleta() {
		return progressivoSchedaCompleta;
	}
	public String getCig() {
		return cig;
	}
	public String getCui() {
		return cui;
	}
	public String getIdSimog() {
		return idSimog;
	}
	public void setIdSimog(String idSimog) {
		this.idSimog = idSimog;
	}
	public String getIdLocale() {
		return idLocale;
	}
	public void setIdLocale(String idLocale) {
		this.idLocale = idLocale;
	}
	public void setProgressivo(int progressivo){
		this.progressivo = progressivo;
	}
	/**
	 * Costruisce un'oggetto di tipo SchedaSpecificaValidationBean a partire dagli argomenti passati
	 * compresi i riferimenti alla scheda.
	 * 
	 * @param statoScheda
	 * @param nomeScheda
	 * @param message
	 * @return
	 */
	public static SchedaSpecificaValidationBean getThisKindOfValidationBeanErr(StatoScheda statoScheda,int elemento, int progressivoSchedaCompleta, int progressivoSchedaMulti, String nomeScheda,String message){
		String cig = statoScheda.getCig();
		String cui = statoScheda.getCui();
		String idSimog = statoScheda.getIdRecordAsString();
		String idLocale = statoScheda.getIdLocale();
		SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(message, ValidationBean.VALBEAN_SEV_ERR, 
				elemento,progressivoSchedaCompleta, progressivoSchedaMulti,
				nomeScheda, cig, cui,idSimog, idLocale);
		
		return validation;
	}
//	public static SchedaSpecificaValidationBean getThisKindOfValidationBeanErr(StatoScheda statoScheda,String nomeScheda,String message){
//		String cig = statoScheda.getCig();
//		String cui = statoScheda.getCui();
//		String idSimog = statoScheda.getIdRecordAsString();
//		String idLocale = statoScheda.getIdLocale();
//		SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(message, ValidationBean.VALBEAN_SEV_ERR, nomeScheda, cig,
//				cui,idSimog, idLocale);
//		
//		return validation;
//	}
	
	/**
	 * Costruisce un'oggetto di tipo SchedaSpecificaValidationBean a partire dagli argomenti passati senza gli id
	 * supporto per le schede multiple.
	 * 
	 * @param statoScheda
	 * @param nomeScheda
	 * @param message
	 * @return
	 */
	public static SchedaSpecificaValidationBean getThisKindOfValidationBeanErrMulti(StatoScheda statoScheda, int elemento, int progressivoSchedaCompleta, int progressivoSchedaMulti, String nomeScheda,String message){
		String cig = statoScheda.getCig();
		String cui = statoScheda.getCui();
		SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(message, ValidationBean.VALBEAN_SEV_ERR, 
				elemento,progressivoSchedaCompleta, progressivoSchedaMulti,
				nomeScheda, cig, cui, null, null);
		
		return validation;
	}
	
	/**
	 * Controlla se nella lista e contenuto almeno un warning
	 * 
	 * @param validations
	 * @return
	 */
	public static boolean checkForWarnings(List<?> validations){
		for(Object validazioneCorrente : validations){
			if(((ValidationBean)validazioneCorrente).getSeverity().equals(ValidationBean.VALBEAN_SEV_WARN)) return true;
		}return false;
	}
	

	public static SchedaSpecificaValidationBean getErrorForIdsProblems(String nomeScheda, String messaggio, String cig, String cui){


		SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(messaggio, ValidationBean.VALBEAN_SEV_ERR, 
				nomeScheda, cig, cui,"", "");
		
		return validation;
	}
//	@deprecated	
//	public static List<SchedaSpecificaValidationBean> getErrorForIdsProblemsMulti(String nomeScheda, String messaggio, String cig, String cui){
//
//		SchedaSpecificaValidationBean validation = new SchedaSpecificaValidationBean(messaggio, ValidationBean.VALBEAN_SEV_ERR, 
//				nomeScheda, cig, cui,"", "");
//		
//		List<SchedaSpecificaValidationBean> list = new ArrayList<SchedaSpecificaValidationBean>();
//		list.add(validation);
//		
//		return list;
//	}	
	

}
