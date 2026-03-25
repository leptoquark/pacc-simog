package it.avlp.simog.validatore;

import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.inizio.PosizioneAggiudicatarioBean;
import it.avlp.simog.beans.inizio.SchedaInizioLavori;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.SimogProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class InizioLavoriValidator extends SimogValidator {

	public InizioLavoriValidator(Connection connection, Logger logger) {
		super(connection, logger);
	
	}

	@Override
	public boolean valida(Object bean, String section) {
		if(bean != null){
			SchedaInizioLavori ilBean = (SchedaInizioLavori) bean;		
			validaInizioLavori(ilBean.getDatiInizio(), ilBean.getInfoComuni(),ilBean.getAggiudicazione());
			// mancano id_aggiudicazione e data inizio (le fk key) quando usato dal
			// massloader
			// logger.debug("[validatore inizio] -
			// "+ObjectIntrospector.propertiesInfo(AggiudicazioneBean.class,
			// ilBean.getAggiudicazione()));
			aggiungiSezione(ilBean.getResponsabiliInizio(), section);
			valida(ilBean.getResponsabiliInizio());
			validaPosizioniAggiud(ilBean.getPosizioneAggiudicatari(),
								ilBean.getAggiudicazione().getIdAggiudicazione(),
								ilBean.getAggiudicazione().getDataInizioAggiudicazione(),
								ilBean.getAggiudicatari());	
			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
		}else{
			return false;
		}
		
	}

	/****************************************************************************************************
	 * Validatore per Posizione Aggiudicatario
	 * 
	 * @param posizioneAggiudicatari List&lt;PosizioneAggiudicatarioBean&gt;
	 */
	private void validaPosizioniAggiud(List<PosizioneAggiudicatarioBean> posizioneAggiudicatari, long idAggiudicazione,
			Timestamp dataInizio, List<AggiudicatarioBean> lab) {
		int i = 1;
		for(PosizioneAggiudicatarioBean posizioneAggiudicatario : posizioneAggiudicatari){
			validaPosizioneAggiud(posizioneAggiudicatario,i,idAggiudicazione,dataInizio,lab);
			i++;
		}
	}
	/****************************************************************************************************
	 * Validatore per la Posizione Aggiudicatario
	 * 
	 * @param posizioneAggiudicatario PosizioneAggiudicatario
	 */
	private void validaPosizioneAggiud(PosizioneAggiudicatarioBean posizioneAggiudicatario, int indice,
			long idAggiudicazione, Timestamp dataInizio, List<AggiudicatarioBean> lab) {
		// logger.debug("[validaPosizioneAggiud] - int "+indice+",long
		// "+idAggiudicazione+",Timestamp "+dataInizio);
		//la posizione deve avere l'anagrafica in aggiudicazione
		/** se non ho una base di dati consistente **/ 
		if(!super.isSQLConnectionEnabled){
			this.validaPosizioneAggiudSenzaDB(posizioneAggiudicatario, indice, idAggiudicazione, dataInizio, lab);
			return;
		}
		/** altrimenti controllo normale **/
		List<AggiudicatarioBean> l = null;
		if(idAggiudicazione != 0 && dataInizio != null){
			logger.info("Validazione posizione aggiudicatario(FaseInzialeLavori) in modalita' \"WEB\"");
			AggiudicatarioManager am = new AggiudicatarioManager(connection,logger);		
			try{
				l = am.loadMany(idAggiudicazione, dataInizio, false);
			}catch(Exception e){
				logger.debug("eccezione durante la validazione delle posizioniAggiudicatari per la \"Fase Iniziale\"");
				mEccezioni.addValidationErrElemento(
						Messaggi.SIMOG_SQL_008.replace("$1", "di validazione delle posizioniAggiudicatari"), indice);
			}
		}else{
			logger.info("Validazione posizione aggiudicatario(FaseInzialeLavori) in modalita' \"MASSLOADER\"");
			l = lab;
		}
		if(!this.validaPosizioneByAggiudicazione(l, 
				posizioneAggiudicatario.getSoggettoPartecipante().getCodiceFiscale(), 
				posizioneAggiudicatario.getSoggettoPartecipante().getId_stato())){
			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_179.replace("$1", "Fase Iniziale"),indice);
		}else{			
			//do validazione controllo valori campi
			if(isEmpty(posizioneAggiudicatario.getCodiceINPS()))
				mEccezioni.addValidationWarn(
						Messaggi.SIMOG_VALIDAZIONE_178.replace("$2", "INPS").replace("$1", "Posizione Contributiva"));
			if(isEmpty(posizioneAggiudicatario.getCodiceINAIL()))
				mEccezioni.addValidationWarn(
						Messaggi.SIMOG_VALIDAZIONE_178.replace("$2", "INAIL").replace("$1", "Posizione Contributiva"));
			if(isEmpty(posizioneAggiudicatario.getCodiceCassa()))
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_178.replace("$2", "cassa edile o previdenza")
						.replace("$1", "Posizione Contributiva"));

			if(isEmpty(posizioneAggiudicatario.getCodiceINAIL()) && isEmpty(posizioneAggiudicatario.getCodiceINPS())){
				//cassa required
				if(!isEmpty(posizioneAggiudicatario.getCodiceCassa())){
					mEccezioni.addValidationWarnElemento(
							Messaggi.SIMOG_VALIDAZIONE_129.replace("$1", "Scheda Inizio - Codice cassa "), indice);
	
				}
			}
		}
	}

	private void validaPosizioneAggiudSenzaDB(PosizioneAggiudicatarioBean posizioneAggiudicatario, int indice,
			long idAggiudicazione, Timestamp dataInizio, List<AggiudicatarioBean> lab) {
		// logger.debug("[validaPosizioneAggiud] - int "+indice+",long
		// "+idAggiudicazione+",Timestamp "+dataInizio);
		//la posizione deve avere l'anagrafica in aggiudicazione
		List<AggiudicatarioBean> l = null;
		logger.info("Validazione posizione aggiudicatario(FaseInzialeLavori) in modalita' \"MASSLOADER\"");
		l = lab;
		if(!this.validaPosizioneByAggiudicazione(l, 
				posizioneAggiudicatario.getSoggettoPartecipante().getCodiceFiscale(), 
				posizioneAggiudicatario.getSoggettoPartecipante().getId_stato())){
			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_179.replace("$1", "Fase Iniziale"),indice);
		}else{			
			//do validazione controllo valori campi
			if(isEmpty(posizioneAggiudicatario.getCodiceINPS()))
				mEccezioni.addValidationWarn(
						Messaggi.SIMOG_VALIDAZIONE_178.replace("$2", "INPS").replace("$1", "Posizione Contributiva"));
			if(isEmpty(posizioneAggiudicatario.getCodiceINAIL()))
				mEccezioni.addValidationWarn(
						Messaggi.SIMOG_VALIDAZIONE_178.replace("$2", "INAIL").replace("$1", "Posizione Contributiva"));
			if(isEmpty(posizioneAggiudicatario.getCodiceCassa()))
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_178.replace("$2", "cassa edile o previdenza")
						.replace("$1", "Posizione Contributiva"));

			if(isEmpty(posizioneAggiudicatario.getCodiceINAIL()) && isEmpty(posizioneAggiudicatario.getCodiceINPS())){
				//cassa required
				if(!isEmpty(posizioneAggiudicatario.getCodiceCassa())){
					mEccezioni.addValidationWarnElemento(
							Messaggi.SIMOG_VALIDAZIONE_129.replace("$1", "Scheda Inizio - Codice cassa "), indice);
	
				}
			}
		}		
	}
	/**
	 * metodo che si occupa di controllare che l'aggiudicatario della fase iniziale
	 * sia contenuto tra quelli di schedaa (e' molto orientato al massloader)
	 * 
	 * @param l lista degli aggiudicatari di scheda a
	 * @param cf stringa con il cf della posizione
	 * @param idPaese del paese
	 * @return boolean
	 */
	private boolean validaPosizioneByAggiudicazione(List<AggiudicatarioBean> l,String cf, String idPaese){
		// nel caso sia nulla (ovvero errore sql ritorna true l'errore e' gia stato
		// aggiunto )
		if(l == null){
			return true;
		//altrimenti
		}else{
			String paese = (idPaese != null) ? idPaese : ""; // il codice paese da validare   
			for(AggiudicatarioBean ab : l){
				String ab_paese = (ab.getSoggettoPartecipante().getId_stato() != null)
						? ab.getSoggettoPartecipante().getId_stato()
						: "";
				String ab_codice = ab.getSoggettoPartecipante().getCodiceFiscale();
			
				if( ab_codice.equals(cf) && ab_paese.equals(paese) )
					return true;
			
				/*
				if((ab.getSoggettoPartecipante().getCodiceFiscale().equals(cf) && 
						(paese == null || "".equals(paese))) ||
					(ab.getSoggettoPartecipante().getCodiceFiscale().equals(cf) && 
						(paese.equals(idPaese) || PaesiManager.CODICE_STATO_ITALIANO.equals(idPaese) || PaesiManager.CODICE_STATO_ITALIANO.equals(paese)))){
					
	//				logger.debug("[3] - true");
					return true;
				}
				 */
			}
			return false;
		}
	}

	private void validaInizioLavori(InizioLavoriBean datiInizio,InfoComuniBean infoComuni,AggiudicazioneBean aggBea) {
		
		boolean obbligatorietaCampi = true;
		try {
			Lotto lotto = new LottoManager(connection,logger).getLotto(infoComuni.getIdLotto());
			Gara gara = new GaraManager(connection,logger).getGara(lotto.getId_Gara());
			String settore = gara.getTIPO_SCHEDA_GARA();
			String dataPubb = lotto.getData_Pubblicazione();
			//Se la gara è stata pubblicata dopo il 1/1/2020 oppure riguarda i settori ordinari, rendi obbligatori i campi dove richiesto
			obbligatorietaCampi = dataPubb.compareTo("20200101") >=0 || Costanti.TIPO_ENTE_ORDINARIO.equals(settore);
			//Se la gara è stata creata prima della 3.04.3 e prevede esclusione, non rendere i dati obbligatori
			if(!SimogProperties.getInstance().isDataCreatedAfter3043(gara.getData_creazione()) && Costanti.FLAG_VALORE_SI.equals(lotto.getFLAG_ESCLUSO()))
				obbligatorietaCampi = false;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// PP B302.2.0
		if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive() && datiInizio.isValidaVariazione()){
			
			if(datiInizio.getIdMotivoVarCO() == null) {
				mEccezioni.addValidationField("label_MotivoVariazione");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Motivazione della variazione anagrafica"));
			}else{	
				try{
					if(!isMotivoVarCOValido(datiInizio.getIdMotivoVarCO(), datiInizio.getDataInizioLavori()))
						throw new Exception();
					else //TICKET ALM #3582 #4194
					if (SimogFlags.is3043Active()
							&& (infoComuni.getID_MODO_REAL() != Costanti.MODOREAL_CONCESSIONE_LAVORI
									&& infoComuni.getID_MODO_REAL() != Costanti.MODOREAL_FINANZA_DI_PROGETTO)
							&& aggBea.getIdMotivoVarCO().equals(Costanti.MOTIVO_SOCIETA_PROGETTO)) {
						  mEccezioni.addValidationField("label_MotivoVariazione");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_232.replace("$1",
								"Motivazione della variazione anagrafica "));
						}
						//FINE TICKET ALM #3582 4194
				}catch (Exception e) {
					mEccezioni.addValidationField("label_MotivoVariazione");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Motivazione della variazione anagrafica"));
				}
			}

		}

		String dataVerbaleAggiudicazione = aggBea.getDataVerbaleAggiudicazione();
		
		// PP 3.02.3.3 per i multi lotto si considera la data maggiore tra le
		// aggiudicazioni
		if(SimogFlags.is30233_RFWEBSC04Active() && aggBea.getDatiEconomici() != null){
		   dataVerbaleAggiudicazione = aggBea.getDatiEconomici().getDataVerbaleAggiudicazione();
		}
		
		//do base controller on fileds
		//[B] campo data
		if(!isEmpty(datiInizio.getDataIniProgEsec())){
			if(!isDate(datiInizio.getDataIniProgEsec())){
				mEccezioni.addValidationField("label_DataInizioProgEsecutiva");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1",
						"Scheda Inizio - data inizio della prog. esecutiva"));
			}
		}
		//[B] campo data superiore al quella sopra
		if(!isEmpty(datiInizio.getDataAppProgEsec())){
			if(!isDate(datiInizio.getDataAppProgEsec())){
				mEccezioni.addValidationField("label_DataApprovazioneEsecutiva");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Inizio - data approvazione esecutiva"));
			}else{ 
				if(!isEmpty(datiInizio.getDataIniProgEsec())){ // PP errore, era usato getDataAppProgEsec
					if(!isDateBigger(datiInizio.getDataAppProgEsec(), datiInizio.getDataIniProgEsec())){
						mEccezioni.addValidationField("label_DataApprovazioneEsecutiva");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_123.replace("$1",
								"Scheda Inizio - data approvazione esecutiva"));
					}
				}
			}
		}
		if(obbligatorietaCampi) {
			if(!isFlag(datiInizio.getFlagFrazionata())){
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Inizio - consegna frazionata"));
			}

			// PP 01.07.2009 diversificato messaggio in base al tipo
			String msgCons = infoComuni.getTipoContratto().equals(Costanti.TIPO_SCHEDA_LAVORI)
					? "data verbale prima consegna lavori"
					: "Data verbale di avvio della prima fase dell'esecuzione del contratto";
			//obligatorio se si
			if(isYFlag(datiInizio.getFlagFrazionata())){
				if(isEmpty(datiInizio.getDataVerbaleCons())){
					mEccezioni.addValidationField("label_DataVerbaleConsegnaLavori");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Scheda Inizio - " +  msgCons));
				}
			}
		
		
		//Se non &egrave; vuoto controllo le condizioni
			if(!isEmpty(datiInizio.getDataVerbaleCons())){
				if(!isDate(datiInizio.getDataVerbaleCons())){
					mEccezioni.addValidationField("label_DataVerbaleConsegnaLavori");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Inizio - " +  msgCons));
				}else{
					if(!isEmpty(datiInizio.getDataStipula())){
						/**
						 * mod 05102008 modificato il controllo a magiore uguale con la condizione della
						 * flag sotto riserva di legge
						 */
						if (!isDateBiggerEq(datiInizio.getDataVerbaleCons(), datiInizio.getDataStipula())
								&& !isYFlag(datiInizio.getFlagRiserva())) {
							mEccezioni.addValidationWarn(
									Messaggi.SIMOG_VALIDAZIONE_126.replace("$1", "Scheda Inizio - " + msgCons));
						}
					}
					if(isDateYearBigger(datiInizio.getDataVerbaleCons())){
						mEccezioni.addValidationWarn(
								Messaggi.SIMOG_VALIDAZIONE_127.replace("$1", "Scheda Inizio - " + msgCons));
					}
				}
			}
		
			if(!isFlag(datiInizio.getFlagRiserva())){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Inizio - flag riserva"));
			}
		
		}
		
		if(!isEmpty(datiInizio.getDataVerbaleInizio())){
			if(!isDate(datiInizio.getDataVerbaleInizio())){
				mEccezioni.addValidationField("label_DataEffettivoInizioLavori");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1",
						"Scheda Inizio - data effettivo inizio lavori/servizi/forniture"));
			}else{ 
				if(!isEmpty(datiInizio.getDataStipula())){
				/**
					 * Il controllo bloccante sul campo 18 della fase iniziale di esecuzione del
					 * contratto secondo il quale campo18 deve essere > campo9 va applicato solo se
					 * campo17 vale NO
					 */
					if (!isDateBiggerEq(datiInizio.getDataVerbaleInizio(), datiInizio.getDataStipula())
							&& !isYFlag(datiInizio.getFlagRiserva())) {
						mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_128.replace("$1",
								"Scheda Inizio - data effettivo inizio lavori/servizi/forniture"));
					}else{
						if(isDateYearBigger(datiInizio.getDataVerbaleInizio())){
							mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_127.replace("$1",
									"Scheda Inizio - data effettivo inizio lavori/servizi/forniture"));
						}
					}
				}	
			}
		} else {
			mEccezioni.addValidationField("label_DataEffettivoInizioLavori");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", 
					"Scheda Inizio - data effettivo inizio lavori/servizi/forniture"));
			
		}
		
		//---- pubblicazione esito procedura di selezione ---
		//validaPubblicazioneBase(datiInizio.getPubblicazione());
		//---- end ----
		//--- contratto appalto -----

		
		//--------------------PUBBLICAZIONE------------------------//
		//dataVerbaleAggiudicazione
/*		prima del 662008
		if(!isEmptyOrZero(datiInizio.getPubblicazione().getDataGuce())){
			try{
				if(!isDate(datiInizio.getPubblicazione().getDataGuce())) throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "GUCE"));
			}
			if(!isDateBigger(datiInizio.getPubblicazione().getDataGuce(), dataVerbaleAggiudicazione)){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_119.replace("$1", "GUCE"));
			}
		}
*/
		if(!isEmptyOrZero(datiInizio.getPubblicazione().getDataGuce())){
			if(!isDate(datiInizio.getPubblicazione().getDataGuce())){
				mEccezioni.addValidationField("label_DataGUCE");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "GUCE"));
			}else{
				if(isDateLower(datiInizio.getPubblicazione().getDataGuce(), dataVerbaleAggiudicazione)){
					mEccezioni.addValidationField("label_DataGUCE");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_119.replace("$1", "GUCE"));
				}
			}		
		}
/*			prima del 662008
		if(!isEmptyOrZero(datiInizio.getPubblicazione().getDataGuri())){
			try{
				if(!isDate(datiInizio.getPubblicazione().getDataGuri())) throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "GURI"));
			}
			if(!isDateBigger(datiInizio.getPubblicazione().getDataGuri(), dataVerbaleAggiudicazione)){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_119.replace("$1", "GURI"));
			}
		}
*/
		if(!isEmptyOrZero(datiInizio.getPubblicazione().getDataGuri())){
			if(!isDate(datiInizio.getPubblicazione().getDataGuri())){ 
				mEccezioni.addValidationField("label_DataGURI");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "GURI"));
			}else{
				if(isDateLower(datiInizio.getPubblicazione().getDataGuri(), dataVerbaleAggiudicazione)){
					mEccezioni.addValidationField("label_DataGURI");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_119.replace("$1", "GURI"));
				}
			}
		}
		if(!isEmpty(datiInizio.getPubblicazione().getQuotidianiNaz())){
			if(datiInizio.getPubblicazione().getQuotidianiNaz()>20){
				mEccezioni.addValidationField("label_QuotidianiNazionali");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_132.replace("$1", "Quotidiani Nazionali"));
			}
		} 
		if(!isEmpty(datiInizio.getPubblicazione().getQuotidianiReg())){
			if(datiInizio.getPubblicazione().getQuotidianiReg()>20){
				mEccezioni.addValidationField("label_QuotidianiNazionali");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_132.replace("$1", "Quotidiani Regionali"));
			}
		}
		
		// commentato perche sta su daticomuni
		
		//2.10 aggiunto controllo 10.1.1.6
		try{
			if (!isFlag(datiInizio.getPubblicazione().getProfiloCommitente()))
				throw new Exception();
		} catch (Exception e) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Profilo del committente"));
		}
		try{
			if (isEmpty(datiInizio.getPubblicazione().getDataGuce())
					&& isEmpty(datiInizio.getPubblicazione().getDataGuri())
					&& isEmptyOrZero(datiInizio.getPubblicazione().getQuotidianiNaz())
					&& isEmptyOrZero(datiInizio.getPubblicazione().getQuotidianiReg()))
				throw new Exception();
		} catch (Exception e) {
			mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_206.replace("$1", "pubblicazione dell'appalto"));
		}
    	//2.10 fine controllo 10.1.1.6
		
        //2.10 esiste controllo 10.1.1.7
		try{
			if (!isFlag(datiInizio.getPubblicazione().getSitoMinisteroInfTrasp()))
				throw new Exception();
		}catch (Exception e) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Sito Ministero Infrastrutture"));
		}
		//2.10 esiste fine controllo 10.1.1.7
		
		//2.10 esiste controllo 10.1.1.8
		try{
			if (!isFlag(datiInizio.getPubblicazione().getSitoOsservatorioCP()))
				throw new Exception();
		}catch (Exception e) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Sito Informatico Osservatorio"));
		}
		//2.10 esiste fine controllo 10.1.1.8
		

				
				//TICKET ALM - 3.04.2 NG #3832-11.1 e 11.2
				// Recupera data pubblicazione gara e verifica sia antecedente la data
				// attivazione simog 3.04.2
				//In questo caso, mantieni i precedenti controlli
				LottoManager lm = new LottoManager(connection,logger);
				GaraManager gm = new GaraManager(connection,logger);
				Lotto lotto = new Lotto();
				Gara gara = new Gara();
				Date dataCreazioneGara = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String flagEstremaUrgenza = null;
				
				try {
					 lotto = lm.getLotto(infoComuni.getIdLotto());
					 gara = gm.getGara(lotto.getId_Gara());
					flagEstremaUrgenza = new GaraManager(connection, logger).getGara(lotto.getId_Gara())
							.getURGENZA_DL133();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				//MEV 37328 - 3.04.8.1 FASE 2
		 		boolean isOsservCompetente = SimogProperties.getInstance().isOsservatorioRegionaleCompetente(gara.getID_OSSERVATORIO());			
		 		
		 		if(isOsservCompetente)
		 		{
		 			mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_294);
		 		}
		 		//FINE MEV 37328
				
		//------------APPALTO--------//
		// deve essere maggiore di aggiudicazione definitiva (bean?) e deve essere una
		// data [B] cel'ho
		//2.10 aggiunto controllo 10.1.1.9
		if(!isEmpty(datiInizio.getDataStipula())){
			
			if(!isDate(datiInizio.getDataStipula())) {
				mEccezioni.addValidationField("label_DataStipula");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Inizio - Data Stipula "));
			
			} else if(!isEmpty(datiInizio.getDataTermine())&&isDate(datiInizio.getDataTermine())){
				if(isDateBigger(datiInizio.getDataStipula(),datiInizio.getDataTermine()))
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_155.replace("$1", "ultimazione")
							.replace("$2", "Scheda Inizio - Data Stipula "));
			} else if (!isEmpty(dataVerbaleAggiudicazione) && isDate(dataVerbaleAggiudicazione)) {
				
				
				
				try {
					 dataCreazioneGara = sdf.parse(gara.getData_creazione().trim());
				} catch (ParseException e1) {
					e1.printStackTrace();
				} 
				
				if (new Timestamp(dataCreazioneGara.getTime()).getTime() < SimogProperties.getInstance()
						.getDataAttivazione3042Timestamp()) {
				
					if(isDateLower(datiInizio.getDataStipula(), dataVerbaleAggiudicazione)) {
						mEccezioni.addValidationField("label_DataStipula");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_155.replace("$1", "stipula")
								.replace("$2", "data di aggiudicazione "));
					}
				} else {
					// Altrimenti, oltre ai precedenti controlli, verifica se il campo S01.17 sia
					// impostato a N
				
					
					if (isNFlag(flagEstremaUrgenza)
							&& isDateLower(datiInizio.getDataStipula(), dataVerbaleAggiudicazione)) {
						mEccezioni.addValidationField("label_DataStipula");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_155.replace("$1", "stipula")
								.replace("$2", "data di aggiudicazione "));
					}

				}
				//FINE TICKET ALM #3832-11.1 e 11.2
			}	
		}
		//2.10 fine controllo 10.1.1.9
		
		// maggiore uguale a quella di sopra, se l'anno e' superiore chidere conferma?
//		if(isEmpty(datiInizio.getDataEsecutivita())){
//			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1", "Esecutivita' "));
//		}else
		//campo 10 diventato facoltativo
		if(!isEmpty(datiInizio.getDataEsecutivita())){ 
			if(!isDate(datiInizio.getDataEsecutivita())){
				mEccezioni.addValidationField("label_DataEsecutivita");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Inizio - Data Esecutivita' "));
			}else{ 
				// if(!isEmpty(datiInizio.getDataEsecutivita()) &&
				// isDate(datiInizio.getDataEsecutivita())){
				if(!isEmpty(datiInizio.getDataStipula())){
					if(isNFlag(flagEstremaUrgenza) && !isDateBiggerEq(datiInizio.getDataEsecutivita(),datiInizio.getDataStipula())){
						mEccezioni.addValidationField("label_DataEsecutivita");
						mEccezioni.addValidationErr(
								Messaggi.SIMOG_VALIDAZIONE_131.replace("$1", "Scheda Inizio - Data Esecutivita' "));
					}
				}
				//}
				if(isDateYearBigger(datiInizio.getDataEsecutivita())){ 
					mEccezioni.addValidationWarn(
							Messaggi.SIMOG_VALIDAZIONE_127.replace("$1", "Scheda Inizio - Data Esecutivita' "));
				}
			}
		}
		//deve essere un campo valuta
		if(obbligatorietaCampi && isEmpty(datiInizio.getImportoCauzione())){
			mEccezioni.addValidationField("label_ImportoCauzione");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_121.replace("$1", "Scheda Inizio - Importo cauzione "));
		} 
		
		//2.10 aggiunto controllo 10.1.1.16
		String msgConsDef = infoComuni.getTipoContratto().equals(Costanti.TIPO_SCHEDA_LAVORI)
				? "data verbale consegna definitiva"
				: "Data verbale di avvio dell'esecuzione del contratto";
		if(!isEmpty(datiInizio.getDataVerbaleDef())){ 
		    if(!isDate(datiInizio.getDataVerbaleDef())) {
		    	mEccezioni.addValidationField("label_DataVerbaleAvvioEsecuzioneLavori");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Inizio - " + msgConsDef));
		    }else if(isDateYearBigger(datiInizio.getDataVerbaleDef())) {
				mEccezioni.addValidationWarn(
						Messaggi.SIMOG_VALIDAZIONE_127.replace("$1", "Scheda Inizio - " + msgConsDef));
		    } else if(isNFlag(datiInizio.getFlagRiserva())){
		    	if(!isEmpty(datiInizio.getDataStipula())&&isDate(datiInizio.getDataStipula())){
		    		if(isDateLower(datiInizio.getDataVerbaleDef(),datiInizio.getDataStipula()))
						mEccezioni.addValidationWarn(
								Messaggi.SIMOG_VALIDAZIONE_128.replace("$1", "Scheda Inizio - " + msgConsDef));
		    	}
		    }
		}		
		if(obbligatorietaCampi && isNFlag(datiInizio.getFlagFrazionata())){
			try{
				if(isEmpty(datiInizio.getDataVerbaleDef()))
			    	throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationField("label_DataVerbaleAvvioEsecuzioneLavori");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Scheda Inizio - " + msgConsDef));
			}
		}			
		//2.10 fine controllo 10.1.1.16
		
		//2.10 aggiunto controllo 10.1.1.19
		if(!isEmpty(datiInizio.getDataTermine())){ 
			if(!isDate(datiInizio.getDataTermine())) {
				mEccezioni.addValidationField("label_DataFinePrevistaUltimazione");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1",
						"Data termine contrattuale per dare ultimazione ai lavori/servizi/forniture"));
			} else if(isDateYearBigger(datiInizio.getDataTermine())) {
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_127.replace("$1",
						"Data termine contrattuale per dare ultimazione ai lavori/servizi/forniture"));
			}
		}
		/* Ticket ALM #656
		 * Il campo Data Termine del bean InizioLavori deve diventare obbligatorio bloccante
		 */
		else {
			//TICKET ALM #3529
			// Solo per il massloader, verificare che la data sia maggiore della data
			// attivazione della mev
//			if(SimogFlags.isFromMassLoader()) {
//				String dataCreazione = "";
//				try {
//					LottoManager lm = new LottoManager(connection, logger);
//					Lotto lotto = lm.getLotto(infoComuni.getIdLotto());
//					GaraManager gm = new GaraManager(connection, logger);
//				     dataCreazione = gm.getGara(lotto.getId_Gara()).getData_creazione();
//				} catch(Exception e) {
//					e.printStackTrace();
//				}
//				if(SimogProperties.getInstance().isDataUltLavoriCreatedAfter3042(dataCreazione)){
//					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1",
//							"Data fine prevista per dare ultimazione ai lavori/servizi/forniture"));
//				}
//			} else {
			if(obbligatorietaCampi) {
				mEccezioni.addValidationField("label_DataFinePrevistaUltimazione");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1",
						"Data fine prevista per dare ultimazione ai lavori/servizi/forniture"));
			}
//			}
		}
		//Fine Ticket ALM #3529
		//Fine Ticket ALM #656
		
		try{
		    if(isNFlag(datiInizio.getFlagFrazionata())){
		    	if(!isEmpty(datiInizio.getDataStipula())&&isDate(datiInizio.getDataStipula())){
        	        if(isDateLowerEq(datiInizio.getDataTermine(),datiInizio.getDataStipula())){  
			            if(!isEmpty(datiInizio.getDataVerbaleInizio())&&isDate(datiInizio.getDataVerbaleInizio())){
        	                if(isDateLowerEq(datiInizio.getDataTermine(),datiInizio.getDataVerbaleInizio()))
        	                	throw new Exception();
						} else
			            	throw new Exception();
        	        }
		    	}
		    }
		} catch (Exception e) {
			mEccezioni.addValidationField("label_DataFinePrevistaUltimazione");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_128
					.replace("$1", "Data termine contrattuale per dare ultimazione ai lavori/servizi/forniture")
					.concat(" o con data di effettivo inizio"));
		}
		if(isYFlag(datiInizio.getFlagFrazionata()))
			mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_207.replace("$1",
					"Data termine contrattuale per dare ultimazione ai lavori/servizi/forniture"));
		
		//2.10 fine controllo 10.1.1.19

		//---- end -------------		
	}
}
