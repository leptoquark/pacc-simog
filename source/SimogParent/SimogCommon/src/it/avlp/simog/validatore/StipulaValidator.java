package it.avlp.simog.validatore;

import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.stipula.SchedaStipula;
import it.avlp.simog.beans.stipula.StipulaBean;
import it.avlp.simog.errormessage.Messaggi;

import java.sql.Connection;
import org.apache.log4j.Logger;

public class StipulaValidator extends SimogValidator {

	public StipulaValidator(Connection connection, Logger logger) {
		super(connection, logger);	
	}
	
	static final int QUOTIDIANI_NAZ_MAX = 20;
	static final int QUOTIDIANI_LOC_MAX = 20;
	
	@Override
	public boolean valida(Object bean, String section) {
		if(bean != null){
			SchedaStipula ssBean = (SchedaStipula) bean;		
			validaStipula(ssBean.getStipula(), ssBean.getAggiudicazione());
			//mancano id_aggiudicazione e data inizio (le fk key) quando usato dal massloader
			//logger.debug("[validatore inizio] - "+ObjectIntrospector.propertiesInfo(AggiudicazioneBean.class,  ilBean.getAggiudicazione()));
			
			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
		}else{
			return false;
		}	
	}
	
	private void validaStipula(StipulaBean datiStipula,AggiudicazioneBean aggBea) {
		PubblicazioneBean pubblicazione = datiStipula.getPubblicazione();
		String dataVerbaleAggiudicazione = aggBea.getDataVerbaleAggiudicazione();
		
		//gm controllo dei campi di pubblicazione
		if(pubblicazione!=null){
			//gm controllo campo 5.1.2
			if(!isEmpty(pubblicazione.getDataGuce())){
				if(!isDate(pubblicazione.getDataGuce())) {
					mEccezioni.addValidationField("label_DatatGUCE");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Stipula - data GUCE"));
				} else{
					if(isDateLower(pubblicazione.getDataGuce(),dataVerbaleAggiudicazione)) {
						mEccezioni.addValidationField("label_DatatGUCE");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_155.replace("$1", "Scheda Stipula - data GUCE").replace("$2", "Aggiudicazione"));
					}
				}
			}
			//gm fine controllo campo 5.1.2
			
			//gm controllo campo 5.1.3
			if(!isEmpty(pubblicazione.getDataGuri())){
				if(!isDate(pubblicazione.getDataGuri())) {
					mEccezioni.addValidationField("label_DatatGORI");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Stipula - data GURI"));
				}else{
					if(isDateLower(pubblicazione.getDataGuri(),dataVerbaleAggiudicazione)) {
						mEccezioni.addValidationField("label_DatatGORI");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_155.replace("$1", "Scheda Stipula - data GURI").replace("$2", "Aggiudicazione"));
					}
				}
			}
			//gm fine controllo campo 5.1.3
			
			//gm controllo campo 5.1.4
			if(!isEmpty(pubblicazione.getQuotidianiNaz())){
				if(!isNumber(pubblicazione.getQuotidianiNaz().toString())) {
					mEccezioni.addValidationField("label_QN");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_139.replace("$1", "Scheda Stipula - Quotidiani Nazionali"));
				} else{
					if(pubblicazione.getQuotidianiNaz()>QUOTIDIANI_NAZ_MAX) {
						mEccezioni.addValidationField("label_QN");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_132.replace("$1", "Scheda Stipula - Quotidiani Nazionali"));
					}
				}
			}
			//gm fine controllo campo 5.1.4
			
			//gm controllo campo 5.1.5
			if(!isEmpty(pubblicazione.getQuotidianiReg())){
				if(!isNumber(pubblicazione.getQuotidianiReg().toString())) {
					mEccezioni.addValidationField("label_QL");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_139.replace("$1", "Scheda Stipula - Quotidiani Locali"));
				}else{
					if(pubblicazione.getQuotidianiReg()>QUOTIDIANI_LOC_MAX) {
						mEccezioni.addValidationField("label_QL");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_132.replace("$1", "Scheda Stipula - Quotidiani Locali"));
					}
				}
			}
			//gm controllo campo 5.1.5
			
			//gm controllo campo 5.1.6
			try{
				if(!isFlag(pubblicazione.getProfiloCommitente())) throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationField("label_PC");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Stipula - Profilo del committente"));
			}
			if(isEmpty(pubblicazione.getDataGuce()) && isEmpty(pubblicazione.getDataGuri()) &&
					isEmptyOrZero(pubblicazione.getQuotidianiNaz()) && isEmptyOrZero(pubblicazione.getQuotidianiReg()))
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_206.replace("$1", "pubblicazione dell'appalto"));
			//gm controllo campo 5.1.6
			
			//gm controllo campo 5.1.7
			try{
				if(!isFlag(pubblicazione.getSitoMinisteroInfTrasp())) throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationField("label_SMI");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Stipula - Sito Ministero Infrastrutture"));
			}
			//gm controllo campo 5.1.7
			
			//gm controllo campo 5.1.8
			try{
				if(!isFlag(pubblicazione.getSitoOsservatorioCP())) throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationField("label_SCP");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Stipula - Sito Informatico Osservatorio Contratti Pubblici"));
			}
			//gm controllo campo 5.1.8
		}
		
		//gm controllo dei campi di stipula contratto
		//gm controllo campo 5.1.9
		if(!isEmpty(datiStipula.getDataStipulaContratto())){
			if(!isDate(datiStipula.getDataStipulaContratto())){
				mEccezioni.addValidationField("label_DataStipula");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Stipula - data stipula del contratto"));
			}
			else{
				if(isDateLower(datiStipula.getDataStipulaContratto(),dataVerbaleAggiudicazione)) {
					mEccezioni.addValidationField("label_DataStipula");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_119.replace("$1", "Scheda Stipula - data stipula del contratto"));
				}
				if(!isEmpty(datiStipula.getDataScadenza()) && isDate(datiStipula.getDataScadenza())){
					if(isDateLower(datiStipula.getDataScadenza(),datiStipula.getDataStipulaContratto())) {
						mEccezioni.addValidationField("label_DataScadenza");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_155.replace("$1", "Scheda Stipula - data scadenza del contratto").replace("$2", "stipula del contratto"));
					}
				}			
			}
		}
		//gm fine controllo campo 5.1.9
		
		//gm controllo campo 5.1.10
		if(!isEmpty(datiStipula.getDataDecorrenza())){
			if(!isDate(datiStipula.getDataDecorrenza())){
				mEccezioni.addValidationField("label_DataDecorrenza");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Stipula - data decorrenza del contratto"));
			}
		}
		//gm fine controllo campo 5.1.10
		
		//gm controllo campo 5.1.11
		if(!isEmpty(datiStipula.getDataScadenza())){
			if(!isDate(datiStipula.getDataScadenza())){
				mEccezioni.addValidationField("label_SchedaScadenza");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Stipula - data scadenza del contratto"));
			}
		}
		//gm fine controllo campo 5.1.11
	}
}
