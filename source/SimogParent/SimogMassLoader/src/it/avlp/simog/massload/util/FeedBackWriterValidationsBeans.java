package it.avlp.simog.massload.util;

import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.flusso.OperazioneScheda;
import it.avlp.simog.massload.xmlbeans.AnomSchedaAType;
import it.avlp.simog.massload.xmlbeans.AnomaliaType;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack;
import it.avlp.simog.massload.xmlbeans.FeedBackDocument.FeedBack.AnomalieSchede;
import it.avlp.simog.massload.xmlbeans.FlussoType;
import it.avlp.simog.massload.xmlbeans.LivelloType;
import it.avlp.simog.massload.xmlbeans.RecIdSchedaInsType;
import it.avlp.simog.massload.xmlbeans.TipiOperazioneType;
import it.avlp.simog.massload.xmlbeans.TipiSchedeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

public class FeedBackWriterValidationsBeans extends FeedBackWriterBase{

	@SuppressWarnings("unused")
	private Logger logger;

	
	
//	<xsd:complexType name="AnomaliaType">
//	   <xsd:attribute name="CODICE" type="xsd:string" />
//	   <xsd:attribute name="DESCRIZIONE" type="xsd:string" use="required" />
//	   <xsd:attribute name="LIVELLO" use="required" type="simog:LivelloType"/>
//	   <xsd:attribute name="ELEMENTO" type="simog:InteroType" use="required" />
//	   <xsd:attribute name="SCHEDA" type="simog:TipiSchedeType"/>
//	   <xsd:attribute name="PROGRESSIVO" type="simog:InteroType"/>
//	   <xsd:attribute name="CAMPO_XML" type="simog:NomeCampoType" use="optional"/>
//     <xsd:attribute name="ID_SCHEDA_SIMOG" type="simog:IdSchedaType" use="optional"/>           
//     <xsd:attribute name="ID_SCHEDA_LOCALE" type="simog:IdSchedaType" use="optional"/>
	
	public FeedBackWriterValidationsBeans(Logger logger, String userName) {
		this.logger = logger;
	}
//		  <xsd:complexType name="RecIdSchedaInsType">
//	      <xsd:attribute name="SCHEDA" type="simog:TipiSchedeType" use="required"/>
//	      <xsd:attribute name="ID_SCHEDA_SIMOG" type="simog:IdSchedaType" use="required"/>           
//	      <xsd:attribute name="ID_SCHEDA_LOCALE" type="simog:IdSchedaType" use="optional"/>
//	      <xsd:attribute name="OPERAZIONE" type="simog:TipiOperazioneType" use="required"/>
	/**
	 * Metodo che si occupa di scrivere sul feedback il contenuto della lista di validationBean (estesi)
	 * 
	 * @param feedBack
	 * @param massloaderValidationsBean
	 * @param operazione
	 */
	public void fillMassloaderFeedBack(FeedBack feedBack, List<SchedaSpecificaValidationBean> massloaderValidationsBean, String operazione){
		for(SchedaSpecificaValidationBean validation : massloaderValidationsBean){
			this.fillMassloaderFeedBack(feedBack, validation, operazione);
		}
	}
	
	/**
	 * Restituisce un oggetto anomalie costruito con i valori dei validation bean, in modo tale che sia possibile
	 * settare in seguito il cui, o cmq associare la lista delle anomalia nel tag anomalie di competenza
	 * 
	 * @param feedBack
	 * @param massloaderValidationsBean
	 * @param operazione
	 * @return
	 */
	public AnomalieSchede costruisciListaAnomalia(FeedBack feedBack, List<SchedaSpecificaValidationBean> massloaderValidationsBean, String operazione, AnomalieSchede anomalieCorrenti, String cig, String cui, int progressivo){
		
		//istanza non vincolata alla stringa xml che si andra' a scrivere
		if(anomalieCorrenti == null){
			anomalieCorrenti = AnomalieSchede.Factory.newInstance();
			anomalieCorrenti.setCIG(cig);
			anomalieCorrenti.setCUI(cui);
			anomalieCorrenti.setPROGRESSIVO(progressivo);
		}

		
		
		// itera per la scrittura delle anomlia
		for(SchedaSpecificaValidationBean validation : massloaderValidationsBean){
			
			if(!validation.getSeverity().equals(ValidationBean.VALBEAN_SEV_INFO)){
				this.fillAnomalia(anomalieCorrenti.addNewAnomalia(), validation, operazione);
			}else{
				this.fillIdScheda(anomalieCorrenti.addNewIdScheda(),validation,operazione);
			}
		}
		return anomalieCorrenti;
	}
	
	public void addListAnomaliaSafely(FeedBack feedBack, AnomalieSchede anomaliePerIlCuiCorrente, String cui){
		// il cui che arriva come argomento dovrebbe essere quello dell'esito dell'operazione (quindi sicuro)
		// altrimenti quello presente nell'anomalia che sarebbe quello eventualemnte contenuto nel tag cui nel xml
		if(anomaliePerIlCuiCorrente != null){
			String local_cui = cui != null ? cui : anomaliePerIlCuiCorrente.getCUI();
			AnomalieSchede anomalieCorrenti = getAnomalieFromFeedBack(feedBack, anomaliePerIlCuiCorrente.getCIG(), local_cui, anomaliePerIlCuiCorrente.getPROGRESSIVO());
			AnomaliaType[] all = new AnomaliaType[anomaliePerIlCuiCorrente.getAnomaliaArray().length + anomalieCorrenti.getAnomaliaArray().length ];
			AnomaliaType[] external = anomaliePerIlCuiCorrente.getAnomaliaArray();
			AnomaliaType[] internal = anomalieCorrenti.getAnomaliaArray();
			for(int i = 0; i < external.length; i++){
				all[i] = external[i];
			}
			int actualSize = anomaliePerIlCuiCorrente.getAnomaliaArray().length;
			for(int i = 0; i < internal.length; i++){
				all[i+actualSize] = internal[i];
			}
			anomalieCorrenti.setAnomaliaArray(all);
		}

	}
	
	public void fillMassloaderFeedBack(FeedBack feedBack, SchedaSpecificaValidationBean validation, String operazione){
		String cig = validation.getCig();
		String cui = validation.getCui();
		int progressivo = validation.getProgressivoSchedaCompleta();			
		
		AnomalieSchede anomalieCorrenti = getAnomalieFromFeedBack(feedBack, cig, cui, progressivo);
		if(!validation.getSeverity().equals(ValidationBean.VALBEAN_SEV_INFO) || validation.getMessage().equals(Messaggi.SIMOG_MASSLOADER_204)){
			this.fillAnomalia(anomalieCorrenti.addNewAnomalia(), validation, operazione);
		}else{
			this.fillIdScheda(anomalieCorrenti.addNewIdScheda(),validation,operazione);
		}
	}

	/**
	 * Metodo che si occupa di trascrivere sul feedback il contenuto della lista di validationBean (NON estesi)
	 * 
	 * @param feedBack
	 * @param validationsBeans
	 * @param operazione
	 * @param nomeScheda
	 * @param cig
	 * @param cui
	 * @param progressivo
	 * @param idLocale
	 * @param idSimog
	 */
	public void fillStandardFeedBack(FeedBack feedBack, List<ValidationBean> validationsBeans, String operazione, String nomeScheda, String cig, String cui, int progressivo, String idLocale, String idSimog){
		// PATCH - VL - 09-02-2010, PER ESSERE SICURI DI NONO TENTARE L'ITERAZIONE SU UN NULL
		if(validationsBeans != null && !validationsBeans.isEmpty()){
			for(ValidationBean validation : validationsBeans){
				
				AnomalieSchede anomalieCorrenti = getAnomalieFromFeedBack(feedBack, cig, cui, progressivo);
				if(!validation.getSeverity().equals(ValidationBean.VALBEAN_SEV_INFO)){
					this.fillAnomalia(anomalieCorrenti.addNewAnomalia(), validation, nomeScheda, idLocale, idSimog, operazione);
				}else{
					this.fillIdScheda(anomalieCorrenti.addNewIdScheda(),validation,operazione, nomeScheda, idLocale, idSimog);
				}
			}	
		}
	}
	
	/**
	 * Scrive un elmento anomalia
	 * 
	 * @param anomalia
	 * @param validation
	 */
	private void fillAnomalia(AnomaliaType anomalia, SchedaSpecificaValidationBean validation, String operazione){
//		PATCH - VL - 05-03-2010 VALIDAZIONE DATI_COMUNI QUANDO NOMI SCHEDA NON VALIDI (NULL,EMPTY,INESISTENTI)
//		if(TipiSchedeType.Enum.forString(validation.getNomeScheda()) != null){
			anomalia.setSCHEDA(this.getEnumFromNomeScheda(validation.getNomeScheda()));
			
//		}
		
		
		anomalia.setDESCRIZIONE(aggiungOperazione(validation.getMessage(), operazione));
		
//		if(validation.getIdLocale() != null || !"".equals(validation.getIdLocale())){
//			anomalia.setIDSCHEDALOCALE(validation.getIdLocale());
//		}
//		if(validation.getIdSimog() != null && !"".equals(validation.getIdSimog())){
//			anomalia.setIDSCHEDASIMOG(validation.getIdSimog());
//		}
//		centralizzazione
		this.setIdLocAndSimog(validation.getIdLocale(), validation.getIdSimog(), anomalia);
		
		anomalia.setELEMENTO(validation.getElemento());
		anomalia.setLIVELLO(LivelloType.Enum.forString(validation.getSeverity()));	
		// TODO: TROVARE UN MODO PER LA VALORIZZAZIONE DI TALE CAMPO
		anomalia.setCAMPOXML("");
		anomalia.setCODICE(validation.getCodiceErrore());
		anomalia.setPROGRESSIVO(validation.getProgressivo()); // "progressivo schede multiple.."
//		logger.debug("Anomalia Appena Aggiunta: \r\n\t" + anomalia.toString());
		
	}
	/**
	 * Scrive un elmento anomalia
	 * 
	 * @param anomalia
	 * @param validation
	 * @param nomeScheda
	 * @param idLocale
	 * @param idSimog
	 */
	private void fillAnomalia(AnomaliaType anomalia, ValidationBean validation, String nomeScheda, String idLocale, String idSimog, String operazione ){
//		anomalia.setSCHEDA(TipiSchedeType.Enum.forString(nomeScheda));
		
//		if(TipiSchedeType.Enum.forString(nomeScheda) != null){
//			anomalia.setSCHEDA(TipiSchedeType.Enum.forString(nomeScheda));
//		}
//		PATCH - VL - 05-03-2010
		anomalia.setSCHEDA(this.getEnumFromNomeScheda(nomeScheda));
		anomalia.setDESCRIZIONE(aggiungOperazione(validation.getMessage(),operazione));
		
//		if(idLocale != null && !"".equals(idLocale)){
//			anomalia.setIDSCHEDALOCALE(idLocale);
//		}
//		
//		if(idSimog != null && !"".equals(idSimog)){
//			anomalia.setIDSCHEDASIMOG(idSimog);
//		}
//		centralizzazione
		this.setIdLocAndSimog(idLocale, idSimog, anomalia);
		
		anomalia.setELEMENTO(validation.getElemento());
		anomalia.setLIVELLO(LivelloType.Enum.forString(validation.getSeverity()));	
		// TODO: Implementare un qualche tipo di di match errore campo.. oppure aggiungere un ulteriore campo nel validationBean
		//			con associato un "decoder" beanSimog.fieldName = xml.attributeName
		anomalia.setCAMPOXML("");
		anomalia.setCODICE(validation.getCodiceErrore());
		anomalia.setPROGRESSIVO(validation.getProgressivo()); // "progressivo schede multiple.."
//		logger.debug("Anomalia Appena Aggiunta: \r\n\t" + anomalia.toString());
	}
	
	/**
	 * Aggiunge l'operazione alla fine del messaggio
	 * 
	 * @param messaggio
	 * @param operazione
	 * @return
	 */
	private String aggiungOperazione(String messaggio, String operazione){
		// aggiungo operazione alla stringa di messaggio
		if(operazione == null || "".equals(operazione)) return messaggio; // patch 27-01-2010 ritornava nel caso di operazione vuota "[OP: ]"
		return messaggio + " [OP:" +operazione+"]";
	}
	
	/**
	 * Rimuove se la trova la stringa dell'operazione dal messaggio per il feedback oldFashion
	 * 
	 * @param messaggio
	 * @param operazione
	 * @return
	 */
	private static String rimuoviOperazione(String messaggio){
		OperazioneScheda[] operazioniDisponibili = new OperazioneScheda[]{OperazioneScheda.getInserimento(),
																		OperazioneScheda.getModifica(),
																		OperazioneScheda.getCancellazione()};
		// eliminazione della stringa che indica l'operazione nel messaggio
		for(int i = 0; i < operazioniDisponibili.length; i++){
			String stringaDaRimuovere = " [OP:"+operazioniDisponibili[i].getNomeOperazione()+"]";
//			System.out.println("messaggio: "+ messaggio + " , stringa da rim: " +stringaDaRimuovere);
			if(messaggio != null && messaggio.contains(stringaDaRimuovere)){
//				System.out.println("Stringa risultante: "+messaggio.substring(0, messaggio.length() - stringaDaRimuovere.length()));
				return messaggio.substring(0, messaggio.length() - stringaDaRimuovere.length());
			}
		}
		return messaggio;
	}
	/**
	 * Scrive un elmento idScheda 
	 * 
	 * @param success
	 * @param validation
	 * @param operazione
	 */
	public void fillIdScheda(RecIdSchedaInsType success, SchedaSpecificaValidationBean validation, String operazione){
		success.setSCHEDA(TipiSchedeType.Enum.forString(validation.getNomeScheda()));
		// patch - vl - 27-01-2010 se viene da schared layer (non dovrebbe mai accadere..) devo aggiungere l'operazione solamente se non e' nulla o vuota
		if(operazione != null && !"".equals(operazione)){
			success.setOPERAZIONE(TipiOperazioneType.Enum.forString(operazione));
		}
		if(validation.getIdLocale() != null && !"".equals(validation.getIdLocale())){
		   success.setIDSCHEDALOCALE(validation.getIdLocale());
		}
		success.setIDSCHEDASIMOG(validation.getIdSimog());
//		logger.debug("IdScheda Appena Aggiunta: \r\n\t" + success.toString());
	}
	/**
	 * Scrive un elmento idScheda 
	 * 
	 * @param success
	 * @param validation
	 * @param operazione
	 * @param nomeScheda
	 * @param idLocale
	 * @param idSimog
	 */
	public void fillIdScheda(RecIdSchedaInsType success, ValidationBean validation, String operazione, String nomeScheda, String idLocale, String idSimog ){
		success.setSCHEDA(TipiSchedeType.Enum.forString(nomeScheda));
		success.setOPERAZIONE(TipiOperazioneType.Enum.forString(operazione));
	    if(idLocale != null && !"".equals(idLocale)){
	       success.setIDSCHEDALOCALE(idLocale);
	    }
	    success.setIDSCHEDASIMOG(idSimog);
//		logger.debug("IdScheda Appena Aggiunta: \r\n\t" + success.toString());
	}
	
	/**
	 * @param feedBack
	 */
	public void fillMessaggioErroreAnagrafiche(FeedBack feedBack, String cig){
		AnomalieSchede anomaliaSchede = feedBack.addNewAnomalieSchede();
		
		// imposto il cig con il primo trovato nel file così creo una sezione per accorpare le anomalie senza cig
		anomaliaSchede.setCIG(cig);
		anomaliaSchede.setPROGRESSIVO(0);
		
		AnomaliaType anomalia = anomaliaSchede.addNewAnomalia();
		anomalia.setDESCRIZIONE(Messaggi.SIMOG_MASSLOADER_199);
		anomalia.setCODICE(getCodiceDalMessaggio(Messaggi.SIMOG_MASSLOADER_199, false));
		anomalia.setSCHEDA(this.getEnumFromNomeScheda(null));
		anomalia.setLIVELLO(LivelloType.ERRORE);
		anomalia.setELEMENTO(0);
		
		
//		logger.debug("Anomalia Appena Aggiunta: \r\n\t" + anomalia.toString());
	}
	
	public void fillMessaggioErroreCigDatiComuni(FeedBack feedBack, String cig, String cui, int progressivo, String messaggio){
		AnomalieSchede anomaliaSchede = feedBack.addNewAnomalieSchede();
		// PATCH - VL - 27-01-2010
		anomaliaSchede.setCIG(cig);
		anomaliaSchede.setCUI(cui);
		anomaliaSchede.setPROGRESSIVO(progressivo);
		
		AnomaliaType anomalia = anomaliaSchede.addNewAnomalia();		
//		PATCH - VL - 27-01-2010
//		anomalia.setSCHEDA(TipiSchedeType.Enum.forString(IdentificativoSchede.DATI_COMUNI));
//		CONTROPATCH ^^ - VL - 05-03-2010
		anomalia.setSCHEDA(this.getEnumFromNomeScheda(null));
		
		if(messaggio == null || "".equals(messaggio.trim())){
   		anomalia.setDESCRIZIONE(Messaggi.SIMOG_MASSLOADER_187);
   		anomalia.setCODICE(getCodiceDalMessaggio(Messaggi.SIMOG_MASSLOADER_187, false));
		}
		else{
         anomalia.setDESCRIZIONE(messaggio);
         anomalia.setCODICE(getCodiceDalMessaggio(messaggio, false));		   
		}
		anomalia.setLIVELLO(LivelloType.ERRORE);	
		
		// PP mancava impostazione!
		anomalia.setELEMENTO(0);
	}

	/**
	 *  Crea un Anomalie una Anomalia, con come riferimento il CIG con il codice e il messaggio di errore
	 *  si occupa inoltre di effettuare una sorta di rollback del feedback, ovverosia
	 *  cerca tutti i messaggi di successo per il cig corrente e l'operazione corrente
	 *  e li rimuove
	 *  see current class method removeSuccessForCigEOperazione
	 * 
	 * @param feedBack
	 * @param operazioneCorrente
	 * @param cig
	 */
	public void fillMessaggioErroreOperazioneCig(FeedBack feedBack,OperazioneScheda operazioneCorrente, String cig){
		
		if(operazioneCorrente != null){
			removeSuccessForCigEOperazione(feedBack, operazioneCorrente, cig);
		}
//		AnomalieSchede anomaliaSchede = getAnomalieFromFeedBack(feedBack, cig, null, 0);
//		AnomaliaType anomalia = anomaliaSchede.addNewAnomalia();	
//		anomaliaSchede.setCIG(cig);
//		anomalia.setDESCRIZIONE(Messaggi.SIMOG_MASSLOADER_198);
//		anomalia.setCODICE(getCodiceDalMessaggio(Messaggi.SIMOG_MASSLOADER_198, false));
	}
	
	/**
	 * see current class method fillMessaggioErroreOperazioneCig
	 * see current class method removeSuccessForCigEOperazione
	 * 
	 * @param feedBack
	 * @param operazioneCorrente
	 * @param cigs
	 */
	public void fillMessaggioErroreOperazioniCigs(FeedBack feedBack,OperazioneScheda operazioneCorrente, ArrayList<String> cigs){
		for(String cigCorrente : cigs){
			fillMessaggioErroreOperazioneCig(feedBack,operazioneCorrente, cigCorrente);
		}
	}
	
	/**
	 * Metodo che si occupa della rimozione dei messaggi di successo che hanno cig e operazione come argomento
	 * 
	 * @param feedBack
	 * @param operazioneCorrente
	 * @param cig
	 */
	private void removeSuccessForCigEOperazione(FeedBack feedBack,OperazioneScheda operazioneCorrente, String cig){
		
	   
	   
		// se l'oggetto non e' nullo
		if(feedBack != null){

		    // lavoro su una copia del feedback
	      FeedBack feedcpy = (FeedBack) feedBack.copy();

			// se sono presenti delle anomalie
			if(feedcpy.getAnomalieSchedeArray() != null && feedcpy.getAnomalieSchedeArray().length > 0){
				
				List<AnomalieSchede> listAnomalie = Arrays.asList(feedcpy.getAnomalieSchedeArray());
				int anomalieCounter = 0;
				
				// iterazione anomalie
				for(AnomalieSchede anomalieCorrente : listAnomalie){
					
					// prendi gli idscheda solamente se ho il match tra i cig
					if(cig.equals(anomalieCorrente.getCIG())){
						
						List<RecIdSchedaInsType> listIdScheda = Arrays.asList(anomalieCorrente.getIdSchedaArray().clone());
						int idSchedaCounter = 0;
						
						// tolgo la sezione originaria
						int num = feedBack.getAnomalieSchedeArray(anomalieCounter).getIdSchedaArray().length;
						
						for(int i = 0; i < num; i++)
						   feedBack.getAnomalieSchedeArray(anomalieCounter).removeIdScheda(0);
						
						// iterazione idScheda
						for(RecIdSchedaInsType idSchedaCorrente : listIdScheda){
						   // le operazioni diverse da quella indicata in chiamata vengono rimesse
							if(!operazioneCorrente.getNomeOperazione().equals(idSchedaCorrente.getOPERAZIONE().toString())){
								// PP aggiunta dell'elemento
							   RecIdSchedaInsType temp = feedBack.getAnomalieSchedeArray(anomalieCounter).addNewIdScheda();
							   temp.setIDSCHEDALOCALE(idSchedaCorrente.getIDSCHEDALOCALE());
                        temp.setIDSCHEDASIMOG(idSchedaCorrente.getIDSCHEDASIMOG());
                        temp.setOPERAZIONE(idSchedaCorrente.getOPERAZIONE());
                        temp.setSCHEDA(idSchedaCorrente.getSCHEDA());
							   
								//feedBack.getAnomalieSchedeArray(anomalieCounter).setIdSchedaArray(idSchedaCounter, idSchedaCorrente);
							}
							idSchedaCounter++;
						}
					}
					anomalieCounter++;
				}
			}
		}
	}
	
	/**
	 * Cerca il tag anomalie senza cig, nel caso lo trovi lo sposta le anomalia ivi contenuta 
	 * nel primo tag anomalie con cig.
	 * 
	 * @param feedback
	 * @param cig
	 * @return FeedBackDocument
	 */
	private static FeedBackDocument spostaAnomalieSenzaCig(FeedBackDocument feedDoc, FeedBack feedback, String cig){
		int feedLength = feedback.getAnomalieSchedeArray().length;
		
//		AnomalieSchede anomalie1 = AnomalieSchede.Factory.newInstance();
//		anomalie1.setCIG(cig);			
//		anomalie1.setPROGRESSIVO(0);
			
		AnomSchedaAType anomaliaOut = null; // sezione che raccoglie le anomalie senza cig
		
		// rintraccio la prima sezione con il cig e sposto quelle senza cig dentro questa
		for(int i=0; i< feedLength;i++){
			if (feedback.getAnomalieSchedeArray()[i].getCIG()!= null 
					&& !"".equals(feedback.getAnomalieSchedeArray()[i].getCIG())){
					
					anomaliaOut = feedback.getAnomalieSchedeArray()[i];
					
					break;
			}
		}
		
		// se ho una sezione ...
		if (anomaliaOut != null){
			// cerco le sezioni senza cig
			for(int i=0; i< feedLength;i++){
				if (feedback.getAnomalieSchedeArray()[i].getCIG()== null 
						|| "".equals(feedback.getAnomalieSchedeArray()[i].getCIG())){
					
						ArrayList<AnomaliaType> listAnomLia = new ArrayList<AnomaliaType>();
						
						for(int j = 0; j < anomaliaOut.getAnomaliaArray().length; j++){
							listAnomLia.add(anomaliaOut.getAnomaliaArray(j));
						}

						for(int j = 0; j < feedback.getAnomalieSchedeArray()[i].getAnomaliaArray().length; j++){
							listAnomLia.add(feedback.getAnomalieSchedeArray()[i].getAnomaliaArray(j));
						}
						// sostituisco la sezione
						anomaliaOut.setAnomaliaArray(fromListToArrayAT(listAnomLia));
						
						// stessa cosa per gli diScheda
						ArrayList<RecIdSchedaInsType> listScheda = new ArrayList<RecIdSchedaInsType>();
						
						for(int j = 0; j < anomaliaOut.getIdSchedaArray().length; j++){
							listScheda.add(anomaliaOut.getIdSchedaArray(j));
						}

						for(int j = 0; j < feedback.getAnomalieSchedeArray()[i].getIdSchedaArray().length; j++){
							listScheda.add(feedback.getAnomalieSchedeArray()[i].getIdSchedaArray(j));
						}
						// sostituisco la sezione
						anomaliaOut.setIdSchedaArray(fromListToArrayIT(listScheda));		
				}
			}		
			
			// ricreo la sezione anomalie prendendo solo le sezioni con cig valorizzato
			boolean ok = false;
			AnomalieSchede[] anomLie = feedback.getAnomalieSchedeArray();
			ArrayList<AnomalieSchede> listAnomLie = new ArrayList<AnomalieSchede>();
			for(int i = 0; i < anomLie.length; i++){
				if(anomLie[i].getCIG() != null && !"".equals(anomLie[i].getCIG())){
					ok = true;
					listAnomLie.add(anomLie[i]);
				}
			}
			
			if(ok){
				feedDoc.getFeedBack().setAnomalieSchedeArray(fromListToArrayAS(listAnomLie));
			}
		}
		else{
			feedDoc.setFeedBack(feedback); // riporto il feedback originale se non ho sezioni CIG
		}
		
		return feedDoc;

		// caso anomalia anagrafiche, ho almeno una sezione senza CIG ed una con CIG
		
		// *** PP si perde gli id schedaaaaaaa
//		if(feedLength == 2){
//			if(
//				feedback.getAnomalieSchedeArray()[0].getCIG() == null 
//					|| "".equals(feedback.getAnomalieSchedeArray()[0].getCIG())
//				&& feedback.getAnomalieSchedeArray()[1].getCIG() == null 
//					|| "".equals(feedback.getAnomalieSchedeArray()[1].getCIG())						
//			){
//				AnomaliaType anomalia1 = feedback.getAnomalieSchedeArray()[0].getAnomaliaArray(0);
//				RecIdSchedaInsType[] ids1 = feedback.getAnomalieSchedeArray()[0].getIdSchedaArray();
//				
//				if(feedback.getAnomalieSchedeArray()[1].getAnomaliaArray().length > 0){
//					AnomaliaType anomalia2 = feedback.getAnomalieSchedeArray()[1].getAnomaliaArray(0);				
//					RecIdSchedaInsType[] ids2 = feedback.getAnomalieSchedeArray()[0].getIdSchedaArray();
//					anomalie1.setAnomaliaArray(new AnomaliaType[]{anomalia1,anomalia2});		
//				}
//				else
//					anomalie1.setAnomaliaArray(new AnomaliaType[]{anomalia1});
//
//
//				feedDoc.getFeedBack().setAnomalieSchedeArray(new AnomalieSchede[]{anomalie1});
//				return feedDoc;
//			}
//		}
//		
////		altrimenti
//		anomalie1 = getAnomalieFromFeedBackOnlyCig(feedback, cig);
//		boolean ok = false;
//		AnomalieSchede[] anomLie = feedback.getAnomalieSchedeArray();
//		ArrayList<AnomalieSchede> listAnomLie = new ArrayList<AnomalieSchede>();
//		ArrayList<AnomaliaType> listAnomLia = new ArrayList<AnomaliaType>();
//		for(int i = 0; i < anomLie.length; i++){
//			if(anomLie[i].getCIG() == null || "".equals(anomLie[i].getCIG())){
//				ok = true;
//				for(int j = 0; j < anomLie[i].getAnomaliaArray().length; j++){
//					listAnomLia.add(anomLie[i].getAnomaliaArray(j));
//				}
//			// per evitare di avere un doppione l'ho gia preso prima
//			}else if(!equals(anomLie[i], anomalie1)){
//				listAnomLie.add(anomLie[i]);
//			}
//		}
//		if(ok){
//			anomalie1.setAnomaliaArray(fromListToArray(listAnomLia));
//			listAnomLie.add(anomalie1);
//			feedDoc.getFeedBack().setAnomalieSchedeArray(fromListToArray(listAnomLie));
//			return feedDoc;
//		}
//		return null;
	}
	
	/**
	 * Metodo front per il nuovo feedback nel caso in cui sia un feedback per anomalie sulle anagrafiche ritorna
	 * un feedback non nullo e configurato come deciso (tutti i tag anomalia spostati in un tag anomalie con cig valorizzato)
	 * 
	 * @param feedback
	 * @param cig
	 * @return
	 */
	public static FeedBackDocument accorpaAnomalieSenzaCig(FeedBack feedback, String cig, String userName){
		FeedBackDocument feedDoc = FeedBackDocument.Factory.newInstance();
		
		feedDoc.addNewFeedBack();
		
		FeedBackWriterBase.addMassLoaderVersion(feedDoc, userName);
		
		// setto gli info flusso dal feedback precedente
		feedDoc.getFeedBack().setInfoFlusso(feedback.getInfoFlusso());
		feedDoc = spostaAnomalieSenzaCig(feedDoc, feedback, cig);
		return feedDoc;
	}
	/**
	 * Retrocompatibilita' clona il feedback rimuovendo gli attributi elementi non compatibili con il vecchio
	 * tipo FeedBack, inoltre:
	 * - nel caso dell'errore delle anomalie mette i due messaggi di errore nello stesso tag
	 * - nel caso di errori/messaggi sullo stesso cig a livelli diversi (liv cig, e liv cui) vengono inglobati nel cui
	 * 
	 * @param feedback
	 * @return
	 */
	public static FeedBackDocument convertToOldFeedbackComplience(FeedBack feedback, String cig, String userName){
		
		FeedBackDocument oldFashionDocument = null;
		// se feedback non nullo
		if(feedback != null){		
			// se contiene delle anomalie
			if(feedback.getAnomalieSchedeArray() != null && feedback.getAnomalieSchedeArray().length > 0){
				oldFashionDocument = FeedBackDocument.Factory.newInstance();
				FeedBack oldFashion = oldFashionDocument.addNewFeedBack();
				
				FeedBackWriterBase.addMassLoaderVersion(oldFashionDocument, userName);
				
				//XX-X: scordato.
				oldFashion.setInfoFlusso(feedback.getInfoFlusso());
				int feedLength = feedback.getAnomalieSchedeArray().length;
				
// nuova gestione per accorpare nel primo tag anomalie con cig le anomalie senza cig
// Nel caso in cui sia un'errore sulle anagrafiche ritorna il risultato calcolato in questo metodo
				FeedBackDocument feedDoc = spostaAnomalieSenzaCig(oldFashionDocument, feedback, cig);
// PP				if(feedDoc != null) return feedDoc;
// end				
				feedback = feedDoc.getFeedBack(); // mah
				
				// PATCH - VL - 09-02-2010 AVOID NULL POINTER 
				if(feedLength > 0){
					
					AnomalieSchede[] anomalieOldFashion = new AnomalieSchede[feedback.getAnomalieSchedeArray().length];
					int controCounter = 0;
					for(int i = 0; i < feedback.getAnomalieSchedeArray().length; i++){
						
						AnomalieSchede anomalieCorrente = feedback.getAnomalieSchedeArray(i);				
						AnomalieSchede anomalieOldFashionCorrente = AnomalieSchede.Factory.newInstance();
						
						anomalieOldFashionCorrente.setCIG(anomalieCorrente.getCIG());
						if(anomalieCorrente.getCUI() != null && !"".equals(anomalieCorrente.getCUI())){
							anomalieOldFashionCorrente.setCUI(anomalieCorrente.getCUI());
						}
						anomalieOldFashionCorrente.setPROGRESSIVO(anomalieCorrente.getPROGRESSIVO());
						
						// se anomalie contiene anamalia
						if(anomalieCorrente.getAnomaliaArray() != null && anomalieCorrente.getAnomaliaArray().length > 0){
							
	//						AnomaliaType[] anomaliaOldFashion = new AnomaliaType[anomalieCorrente.getAnomaliaArray().length];
							ArrayList<AnomaliaType> anomaliaOldFashion = new ArrayList<AnomaliaType>(); 
							
							for(int a = 0; a < anomalieCorrente.getAnomaliaArray().length; a++){
								
								AnomaliaType anomaliaCorrente = anomalieCorrente.getAnomaliaArray(a);
								
								// avviso escluso usato solo nel caso dell'aggiornamento dell' id locale
								if(!anomaliaCorrente.getLIVELLO().equals(LivelloType.AVVISO)){
									AnomaliaType anomaliaOldFashionCorrente = AnomaliaType.Factory.newInstance();
									
									anomaliaOldFashionCorrente.setCODICE(anomaliaCorrente.getCODICE());
									anomaliaOldFashionCorrente.setDESCRIZIONE(rimuoviOperazione(anomaliaCorrente.getDESCRIZIONE()));
									anomaliaOldFashionCorrente.setLIVELLO(anomaliaCorrente.getLIVELLO());
									anomaliaOldFashionCorrente.setELEMENTO(anomaliaCorrente.getELEMENTO());
									
									
									// aggiungi elemento all'array corrente
	//								anomaliaOldFashion[a] = anomaliaOldFashionCorrente;
									anomaliaOldFashion.add(anomaliaOldFashionCorrente);
								}
							}
	
							// aggiungi array alla "anomalie"

							anomalieOldFashionCorrente.setAnomaliaArray(fromListToArrayAT(anomaliaOldFashion));

	
						}
						// aggiungi "anomalie" all'array
//						anomalieOldFashion[i - controCounter] = anomalieOldFashionCorrente;
						
						// aggiungi "anomalie" all'array solamente se contiene almeno un tag "anomalia", visto che non ci sono tag idscheda del caso positivo
						if(anomalieOldFashionCorrente.getAnomaliaArray() != null && anomalieOldFashionCorrente.getAnomaliaArray().length > 0){
							anomalieOldFashion[i - controCounter] = anomalieOldFashionCorrente;
						}else{
							controCounter++;
						}
					}
					// aggiungi array di anomalie al feedback
					
					oldFashion.setAnomalieSchedeArray(resizeArray(controCounter,feedback.getAnomalieSchedeArray().length,anomalieOldFashion));
				}
			}
		}
		// nuova gestione (per coerenza all'indietro) accorpa tag anomalie con lo stesso cig li dove ci sia un cig e un cig + cui uguale
		return accorpaAnomLieCigACui(oldFashionDocument,0);
	}
	
	/**
	 * Metodo che si occupa di accorpare le eventuali anomalie presenti nel tag anomalie con cig
	 * nel caso sia presente un tag anomalie con cig e cui valorizzato
	 * 
	 * @param feedDoc
	 * @return
	 */
	private static FeedBackDocument accorpaAnomLieCigACui(FeedBackDocument feedDoc, int iterazione){
		// controlla se ci siano dei tag da accorpare, se non ci sono ritorna il feed in ingresso
		if(!daAccorpare(feedDoc, null,iterazione)){
			return feedDoc;
		}
		// prendo il cig del tag anomalie in posizione = iterazione
		String cig = feedDoc.getFeedBack().getAnomalieSchedeArray()[iterazione].getCIG();
		
		AnomalieSchede[] arrayAnomalie = feedDoc.getFeedBack().getAnomalieSchedeArray();
		ArrayList<AnomalieSchede> listAnomLie = new ArrayList<AnomalieSchede>();
		
		AnomalieSchede anomLie = AnomalieSchede.Factory.newInstance();
		ArrayList<AnomaliaType> listAnomLia = new ArrayList<AnomaliaType>();
		
		boolean ok = false;
		for(int i = 0; i < arrayAnomalie.length; i++){
			// se l'anomalia corrente e' quella con il cig corrente crea un tag anomalie e metti
			// i valori delle due anomalie in uno..
			if(cig.equals(arrayAnomalie[i].getCIG())){
				if(arrayAnomalie[i].getCUI() != null && !"".equals(arrayAnomalie[i].getCUI()) ){
					anomLie.setCIG(arrayAnomalie[i].getCIG());
					anomLie.setCUI(arrayAnomalie[i].getCUI());
					anomLie.setPROGRESSIVO(arrayAnomalie[i].getPROGRESSIVO());
				}
				for(int j = 0; j < arrayAnomalie[i].getAnomaliaArray().length; j++){
					listAnomLia.add(arrayAnomalie[i].getAnomaliaArray(j));
				}
				ok = true;
			// altrimenti aggiungi anomalie semplicemente
			}else{
				listAnomLie.add(arrayAnomalie[i]);
			}
			
		}
		if(ok){
			anomLie.setAnomaliaArray(fromListToArrayAT(listAnomLia));
			listAnomLie.add(anomLie);
		}
		// replace dell'array di anomalie che saranno anomalie_originali.length -1 (2 anomalie accorpate in uno..)
		feedDoc.getFeedBack().setAnomalieSchedeArray(fromListToArrayAS(listAnomLie));
		// end edn den
		return accorpaAnomLieCigACui(feedDoc,iterazione++);
	}
	
	/**
	 * Metodo che si occupa di controllare se sia necessario l'accorpamento di due tag anomalie
	 * Non c' e' nulla da accorpare se:
	 * - feedDoc e' nullo
	 * - feedback e' nullo
	 * - anomalie array e' ha dimensione inferiore a 2 
	 * - altrimenti effettua il controllo itero-ricorsivo per ogni cig (multicig)
	 * 
	 * @param feedDoc
	 * @param cig
	 * @param iterazione
	 * @return boolean
	 */
	private static boolean daAccorpare(FeedBackDocument feedDoc, String cig, int iterazione){
		
		if(feedDoc == null || feedDoc.getFeedBack() == null) return false;
		if(feedDoc.getFeedBack().getAnomalieSchedeArray() == null 
				|| feedDoc.getFeedBack().getAnomalieSchedeArray().length < 2 ) return false;
		// altrimenti calcola/cerca
		AnomalieSchede[] arrayAnomalie = feedDoc.getFeedBack().getAnomalieSchedeArray();
		int counter = 0;
		if(cig == null) return daAccorpare(feedDoc, arrayAnomalie[iterazione].getCIG(),iterazione);	
		else{			
			for(int i = 0; i < arrayAnomalie.length; i++){
				AnomalieSchede anomalie = arrayAnomalie[i];
				if(cig.equals(anomalie.getCIG())){
					counter++;
				}
			}
		}return counter == 2;
	}
	/**
	 * Ridimensionia l'array
	 * 
	 * @param controCounter
	 * @param originalLength
	 * @param anomalieOldFashion
	 * @return
	 */
	private static AnomalieSchede[] resizeArray(int controCounter, int originalLength, AnomalieSchede[] anomalieOldFashion){
		int newLength = originalLength - controCounter;
		AnomalieSchede[] anomalieOld = new AnomalieSchede[newLength];
		for(int i = 0; i < newLength; i++){
			anomalieOld[i] = anomalieOldFashion[i];
		}return anomalieOld;
	}

	/**
	 * Utility converte una lista di anomaliaType in un'array (xmlbean comply)
	 * 
	 * @param anomaliaOldFashion
	 * @return
	 */
	private static AnomaliaType[] fromListToArrayAT(ArrayList<AnomaliaType> anomaliaOldFashion){
		return (AnomaliaType[])anomaliaOldFashion.toArray(new AnomaliaType[anomaliaOldFashion.size()]);
	}
	
	/**
	 * Utility converte una lista di anomaliaType in un'array (xmlbean comply)
	 * 
	 * @param anomaliaOldFashion
	 * @return
	 */
	private static RecIdSchedaInsType[] fromListToArrayIT(ArrayList<RecIdSchedaInsType> anomaliaOldFashion){
		return (RecIdSchedaInsType[])anomaliaOldFashion.toArray(new RecIdSchedaInsType[anomaliaOldFashion.size()]);
	}
	/**
	 * Utility di conversione lista -> array
	 * 
	 * @param anomLie
	 * @return
	 */
	private static AnomalieSchede[] fromListToArrayAS(ArrayList<AnomalieSchede> anomLie){
		return (AnomalieSchede[])anomLie.toArray(new AnomalieSchede[anomLie.size()]);
	}
	
	/**
	 * Valorizza l'enum  con quello corrispondente ai dati comuni nel caso in cui il nome scheda non sia valido
	 * 
	 * @param nomeScheda
	 * @return
	 */
	private TipiSchedeType.Enum getEnumFromNomeScheda(String nomeScheda){
		if(nomeScheda == null || "".equals(nomeScheda) || TipiSchedeType.Enum.forString(nomeScheda) == null){
			return TipiSchedeType.DATI_COMUNI;
		}return TipiSchedeType.Enum.forString(nomeScheda);
	}
	
	/**
	 * Valorizza gli attributi idlocale e idsimog nel caso in cui siano validi gli argomenti (controllo separato per ogni id)
	 * 
	 * @param idLocale
	 * @param idSimog
	 * @param anomalia
	 */
	private void setIdLocAndSimog(String idLocale, String idSimog, AnomaliaType anomalia){
		
		if(idLocale != null && !"".equals(idLocale)){
			anomalia.setIDSCHEDALOCALE(idLocale);
		}
		if(idSimog != null && !"".equals(idSimog)){
			anomalia.setIDSCHEDASIMOG(idSimog);
		}
	}
	
	/**
	 * Nel caso di una eccezione non gestita aggiungi un anomalie ed una anomalia e scrivi un messaggio specifico,
	 * l'argomento non deve essere nullo ma se al suo interno l'oggetto feed � nullo non c'e problema viene costruito
	 * 
	 * PATCH - VL - 11-02-2010, si e' tentato di introdurre una gestione di feed per errori fatal..
	 * 
	 * @param feedback
	 */
	public void writeUnandledException(FeedBackDocument feedDoc, String cig, String payLoad, String userName){
		FeedBack feedBack = feedDoc.getFeedBack();
		
		// gestione feedback nulli, costruzione di..
		if( feedBack == null || feedBack.getInfoFlusso() == null){
			if(feedBack == null)
		      feedBack = feedDoc.addNewFeedBack();
			
			FlussoType infoFlusso = feedBack.addNewInfoFlusso();
			infoFlusso.setNUMELABORATE(1);
			infoFlusso.setNUMERRORE(1);
			infoFlusso.setNUMCARICATE(0);
			infoFlusso.setNUMWARNING(0);
			infoFlusso.setDATAELABORAZIONE(Calendar.getInstance());
			
			FeedBackWriterBase.addMassLoaderVersion(feedDoc, userName);
		}
		else{
		   FlussoType infoFlusso = feedBack.getInfoFlusso();
		   infoFlusso.setNUMERRORE(infoFlusso.getNUMERRORE()+1);
		   if(infoFlusso.getNUMELABORATE() == 0)
		      infoFlusso.setNUMELABORATE(1);
		}
		   
		
		// standard
		AnomalieSchede anomalie = feedBack.addNewAnomalieSchede();
		anomalie.setCIG(cig);
		anomalie.setPROGRESSIVO(0);
		AnomaliaType anomalia = anomalie.addNewAnomalia();
		anomalia.setSCHEDA(getEnumFromNomeScheda(null));
		anomalia.setELEMENTO(0);
		anomalia.setLIVELLO(LivelloType.ERRORE);
		
		anomalia.setDESCRIZIONE(Messaggi.SIMOG_MASSLOADER_205
		                        + (payLoad != null ? " - " + payLoad : ""));
		anomalia.setCODICE(getCodiceDalMessaggio(Messaggi.SIMOG_MASSLOADER_205, false));	
	}

}
