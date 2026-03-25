package it.avlp.simog.massload.util;

import it.avlp.simog.massload.validation.ValidationBeanHandler;

import java.util.Map;

@Deprecated
public class FeedBackWriter {

	private Map<Integer,Map<Integer,ValidationBeanHandler>> trace;
	private int errori;
	private int warning;
	private String SIMOG_MASSLOADER_00 = "SIMOG_MASSLOADER_00";
	
//	public FeedBackWriter(Map<Integer, Map<Integer, ValidationBeanHandler>> trace) {
//		this.trace = trace;
//		this.errori = 0;
//		this.warning = 0;
//		this.calcolaInfo();
//	}
//	public FeedBackWriter(){}
//	
//	public void setProblemsInserimento(Map<Integer, Map<Integer, ValidationBeanHandler>> trace){
//		this.trace = trace;
//		this.errori = 0;
//		this.warning = 0;
//		this.calcolaInfo();		
//	}
//	/**
//	 * Metodo che si occupa di iterare sulla mappa per ottenere il
//	 * numero di schede in errore e in warning
//	 */
//	private void calcolaInfo(){
//		Set<Integer> progressivi = this.trace.keySet();
//		//iterazione progressivi
//		for(Integer Progressivo : progressivi){
//			Set<Integer> sub_progressivi = this.trace.get(Progressivo).keySet();
//			//contatori che servono a limitare l'incremento per ogni scheda di 1 errore e 1 warning
//			int limitError = 0;
//			int limitWarning = 0;
//			//iterazione sub_progressivi
//			for(Integer sub_progressivo : sub_progressivi){
//				List<ValidationBean> listOfValidationBean = this.trace.get(Progressivo).get(sub_progressivo).getListaValidazioniSchedaCompleta();
//				if(listOfValidationBean != null){
//					//iterazione lista validazioni
//					for(ValidationBean validationBean : listOfValidationBean){
//						if(limitError == 0 && validationBean.getSeverity().equalsIgnoreCase(ValidationBean.VALBEAN_SEV_ERR)){
//							this.errori++;
//							limitError++;
//						}
//						if(limitWarning == 0 && validationBean.getSeverity().equalsIgnoreCase(ValidationBean.VALBEAN_SEV_WARN)){
//							this.warning++;
//							limitWarning++;
//						}
//						//se ho gia' trovato entrambe posso uscire dal ciclo
//						if(limitError == 1 && limitWarning == 1){ break;}
//					}
//					//se ho gia' trovato entrambe posso uscire dal ciclo
//					if(limitError == 1 && limitWarning == 1){ break;}
//				}
//			}
//		}
//	}
//	/**
//	 * Metodo che scrive le info flusso sull'oggetto passato
//	 * 
//	 * @param feedBack
//	 */
//	public void writeInfo(FeedBack feedBack,int numElaborate,int numCaricate){
//		feedBack.getInfoFlusso().setNUMELABORATE(numElaborate);
//		feedBack.getInfoFlusso().setNUMERRORE(this.errori);
//		feedBack.getInfoFlusso().setNUMWARNING(this.warning);
//		feedBack.getInfoFlusso().setNUMCARICATE(numCaricate);
//
//	}
//	/**
//	 * Metodo che scrive le info flusso sull'oggetto passato
//	 * 
//	 * @param feedBack
//	 */
//	public static void writeInfo(FlussoType flu,Calendar data,int numElaborate,int numErrore,int numWarning,int numCaricate){
//		flu.setDATAELABORAZIONE(data);
//		flu.setNUMELABORATE(numElaborate);
//		flu.setNUMERRORE(numErrore);
//		flu.setNUMWARNING(numWarning);
//		flu.setNUMCARICATE(numCaricate);
//
//	}
//	public static void writeAnomalie(AnomalieSchede as,String cig,int progressivo){
//		as.setCIG(cig);
//		as.setPROGRESSIVO(progressivo);
//	}
//	/**
//	 * @param a
//	 * @param codice
//	 * @param elemento
//	 * @param livello @see validationBean.VALBEAN_SEV_XXX costant
//	 * @param descrizione
//	 */
//	public static void writeAnomalia(AnomaliaType a,String codice,int elemento,String livello,String descrizione){
//        a.setCODICE(codice);
//        a.setELEMENTO(elemento);
//        a.setLIVELLO(LivelloType.Enum.forString(livello));	        
//        a.setDESCRIZIONE(descrizione);
//	}
//	/**
//	 * Metodo che scrive le anomalie sull'oggetto passato
//	 * 
//	 * @param feedBack
//	 */
//	public void writeAnomalie(FeedBack feedBack){
//		Set<Integer> progressivi = this.trace.keySet();
//		//iterazione progressivi
//		for(Integer progressivo : progressivi){
//			Set<Integer> sub_progressivi = this.trace.get(progressivo).keySet();
//			//iterazione sub_progressivi
//			for(Integer sub_progressivo : sub_progressivi){
//				List<ValidationBean> listOfValidationBean = this.trace.get(progressivo).get(sub_progressivo).getListaValidazioniSchedaCompleta();
//				AnomalieSchede as = feedBack.addNewAnomalieSchede();
//				if(listOfValidationBean != null && !listOfValidationBean.isEmpty()){					
//					this.writeAnomalieConValidation(as, this.trace.get(progressivo).get(sub_progressivo), progressivo);
//				}else{
//					this.writeAnomalieSenzaValidation(as, this.trace.get(progressivo).get(sub_progressivo), progressivo);
//				}
//			}
//		}
//	}
	
//	/**
//	 * Metodo che si occupa di scrivere l'esito delle operazioni di cancellazione.
//	 * 
//	 * @param feedBack
//	 */
//	public void writeAnomalieIdScheda(FeedBack feedBack, Map<String,ArrayList<ReportCancellazioneScheda>> reportsGroupByCui, boolean isSuccess, String riepilogoScheda){
//
//		Set<String> cuiCigS = reportsGroupByCui.keySet();
//		for(String cuiOrCig : cuiCigS){
//			boolean isCui = cuiOrCig.length() > 10;
//			ArrayList<ReportCancellazioneScheda> listOfreports = reportsGroupByCui.get(cuiOrCig);
//			for(ReportCancellazioneScheda report : listOfreports){
//				
//				AnomalieSchede[] anomalies = feedBack.getAnomalieSchedeArray();
//				AnomalieSchede anomalie = null;
//				// non esistono anomalie
//				if(anomalies == null){
//					anomalie = feedBack.addNewAnomalieSchede();
//					anomalie.setCIG(report.getSchede().getCig());
//					anomalie.setCUI("");
//					anomalie.setPROGRESSIVO(0);
//				// esistono anomalie, devo controllare che non esista l'anomalia con il mio cig / cui
//				}else{
//					// flag che notifica se e' presente o meno una anomalie con la chiave cig / cui uguale 
//					boolean isPresent = false;
//					
//					for(int i = 0; i < anomalies.length; i++){
//						
//						String cui = report.getSchede().getCui();
//						String cig = report.getSchede().getCig();
//						AnomalieSchede anomalieInner = anomalies[i];
//						
//						// se la chiave della mappa e' un cui
//						if(isCui){
//							if(cig.equals(anomalieInner.getCIG()) && cui.equals(anomalieInner.getCUI())){
//								isPresent = true;
//								anomalie = anomalieInner;
//							}
//						// se la chiave della mappa e' un cig
//						}else{
//							if(anomalieInner.getCIG().equals(cig) && (anomalieInner.getCUI() == null || anomalieInner.getCUI().equals(""))){
//								isPresent = true;
//								anomalie = anomalieInner;
//							}
//						}
//					}
//					// se non e' presente devo aggiungere una Anomalie
//					if(!isPresent){
//						anomalie = feedBack.addNewAnomalieSchede();
//						anomalie.setCIG(report.getSchede().getCig());
//						anomalie.setCUI(report.getSchede().getCui());
//						anomalie.setPROGRESSIVO(0);
//					}
//					
//					if(isSuccess){
//						RecIdSchedaInsType idSchedad = anomalie.addNewIdScheda();
//						idSchedad.setIDSCHEDALOCALE(report.getSchede().getIdLocale());
//						idSchedad.setIDSCHEDASIMOG(report.getSchede().getIdScheda());
//						idSchedad.setOPERAZIONE(getOperazioneCancellazioneEnum());
//						// questo e' possibile perche la nomenclatura corrisponde
//						idSchedad.setSCHEDA(getNomeSchedaEnumByName(report.getSchede().getIdentificativo().getNomeScheda()));
//					}else{
//						AnomaliaType anomalia = anomalie.addNewAnomalia();
//						anomalia.setCODICE(SIMOG_MASSLOADER_00);
//						anomalia.setDESCRIZIONE(riepilogoScheda);
//						anomalia.setELEMENTO(0);
//						anomalia.setLIVELLO(getErroreEnum());
//						anomalia.setPROGRESSIVO(0);
//						// caso fallimento cancellazione per CIG
//						if(report.getSchede().getIdentificativo() != null){
//							anomalia.setSCHEDA(getNomeSchedaEnumByName(report.getSchede().getIdentificativo().getNomeScheda()));
//						}
//					}
//				}
//			}
//		}
//	}
	
//	private TipiOperazioneType.Enum getOperazioneEnumByName(String operazione){
//		return TipiOperazioneType.Enum.forString(operazione);
//	}
//	private TipiOperazioneType.Enum getOperazioneCancellazioneEnum(){
//		return TipiOperazioneType.ELIMINAZIONE;
//	}
//	private TipiSchedeType.Enum getNomeSchedaEnumByName(String nome){
//		return TipiSchedeType.Enum.forString(nome);
//	}
//	private LivelloType.Enum getErroreEnum(){
//		return LivelloType.ERRORE;
//	}
	
//	/**
//	 * Metodo che scrive una "AnomalieSchede" laddove non c'e' una lista di validation,
//	 * quindi nessun errore ne warning
//	 * 
//	 * @param anomalieSchede
//	 * @param massValidation
//	 * @param progressivo
//	 */
//	private void writeAnomalieSenzaValidation(AnomalieSchede anomalieSchede,ValidationBeanHandler massValidation,Integer progressivo){
//		anomalieSchede.setCIG(massValidation.getCIG());	
//		//non dovrebbe mai accadere perche in caso di successo ho sempre il cui
//		if(massValidation.getCUI() != null && !"".equals(massValidation.getCUI())){
//			anomalieSchede.setCUI(massValidation.getCUI()) ;
//		}
//		//ho aggiunto +1 perche l'indice mi pare parta da 0
//		anomalieSchede.setPROGRESSIVO(progressivo+1);
//	}
//	/**
//	 * Metodo che scrive una "AnomalieSchede" laddove c'e' una lista di validation,
//	 * quindi devo iterare per aggiungere sub_oggetti di tipo "anomalia"
//	 * 
//	 * @param anomalieSchede
//	 * @param massValidation
//	 * @param progressivo
//	 */
//	private void writeAnomalieConValidation(AnomalieSchede anomalieSchede,ValidationBeanHandler massValidation,Integer progressivo){
//		anomalieSchede.setCIG(massValidation.getCIG());	
//		//puo' accadere perche in caso di inserimento di aggiudicazione non effettuato non ho un cui
//		if(massValidation.getCUI() != null && !"".equals(massValidation.getCUI())){
//			anomalieSchede.setCUI(massValidation.getCUI()) ;
//		}
//		//ho aggiunto +1 perche l'indice mi pare parta da 0
//		anomalieSchede.setPROGRESSIVO(progressivo+1);
//		List<ValidationBean> listOfValidationBean = massValidation.getListaValidazioniSchedaCompleta();
//		for(ValidationBean validationBean : listOfValidationBean){
//			this.writeAnomalia(validationBean,anomalieSchede.addNewAnomalia());
//		}
//	}
//	/**
//	 * Metodo che scrive un "anomalia" figlia di una "anomalie"
//	 * 
//	 * @param validationBean
//	 * @param anomalia
//	 */
//	private void writeAnomalia(ValidationBean validationBean,AnomaliaType anomalia){
//		anomalia.setDESCRIZIONE(this.pulisciDalCodiceMessaggio(validationBean.getMessage()));
//		//ho aggiunto +1 perche l'indice mi pare parta da 0
//		//anomalia.setELEMENTO(validationBean.getElemento()+1);
//		//a volte si a volte no.. meglio da 0 che da 2..
//		anomalia.setELEMENTO(validationBean.getElemento());
//		anomalia.setLIVELLO(LivelloType.Enum.forString(validationBean.getSeverity()));
//		anomalia.setCODICE(this.getCodiceDalMessaggio(validationBean.getMessage(),false));
//	}
//	/**
//	 * Metodo che si occupa di rimuovere il codice dell'avviso dalla
//	 * descrizione dell'avviso
//	 * 
//	 * @param messaggio
//	 */
//	private String pulisciDalCodiceMessaggio(String messaggio){
//		String temp = "";
//		if(messaggio != null && !"".equals(messaggio.trim())){
//			int position = messaggio.indexOf("-");
//			if(position > 0 && position <= messaggio.length()){
//				temp = messaggio.substring(position + 2);
//			}else{
//				temp = messaggio;
//			}
//		}return temp;				
//	}

//	/**
//	 * Isola il codice del messaggio e lo memorizza in locale
//	 * @param messaggio
//	 */
//	private String getCodiceDalMessaggio(String messaggio,boolean isAlreadyCode){
//		String temp = "";
//		if(!isAlreadyCode){
//			if(messaggio != null && !"".equals(messaggio.trim())){
//				int position = messaggio.indexOf(" ");
//				if(position > 0 && position <= messaggio.length()){
//					temp = messaggio.substring(0, position);
//					//devo controllare che il codice sia valido
//					if(isAllowedCode(temp)){
//						return temp;
//					//altrimenti il codice che identifica messaggio senza codice
//					}else{
//						temp = this.setCodiceAlDefault(temp);
//					}
//				}
//			}return temp;
//		}else{
//			return messaggio;
//		}
//	}
//	private String setCodiceAlDefault(String temp){
//		//StuffDispatcher.getInstance().getLogger().debug("Il codice["+temp+"] non risulta tra i codici");
//		return this.SIMOG_MASSLOADER_00;
//	}
//	/**
//	 * Controlla che esista la varibile corrispondente al nome (codice) recuperato
//	 * 
//	 * @param codice
//	 * @return
//	 */
//	private boolean isAllowedCode(String codice){	
//		List<Field> listaDeiCampi = Arrays.asList(Messaggi.class.getDeclaredFields());
//		for(Field f : listaDeiCampi){
//			if(f.getName().equalsIgnoreCase(codice)){
//				return true;
//			}
//		}return false;
//	}
}
