package it.avlp.simog.validatore;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import it.avlp.simog.beans.Rubrica;
import it.avlp.simog.beans.RubricaResponsabili;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.generated.STATI_ESTERI;
import it.avlp.simog.errormessage.Messaggi;

public class RubricaValidator extends SimogValidator {

	public RubricaValidator(Connection connection, Logger logger) {
		super(connection, logger);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean valida(Object bean, String section) {
		
		if(PSBD.TAB_RUBRICA_RESPONSABILI.equals(section))
			validaResponsabili((List<RubricaResponsabili>) bean);
		else if(PSBD.TAB_RUBRICA_AFFIDATARIO.equals(section))
			validaAggiudicatari((List<Rubrica>) bean);
		return getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
	}

	/**
	 * Gestisce la diverisficazione della validazione nei casi in cui l'aggiudicatario sia
	 * italiano oppure sia estero.
	 * UN
	 * @param aggiudicatario Rubrica
	 * @param i	int
	 * @return boolean
	 */
	private boolean _switch(Rubrica aggiudicatario, int i) {
		if(!isYFlag(aggiudicatario.getFlagEsteri()))
			return valida_italiano(aggiudicatario,i);
		else
			return valida_estero(aggiudicatario,i);	
	}
	
	/**
	 * Validazione aggiudicatari italiani
	 * UN
	 * @param aggiudicatario Rubrica
	 * @param i	int
	 * @return boolean
	 */
	private boolean valida_italiano(Rubrica aggiudicatario, int i) {
		boolean vOk = true;
		// Valida il codice fiscale
		if(!isEmpty(aggiudicatario.getCodice_fiscale())){
			try{
				if(!validaPartitaIva(aggiudicatario.getCodice_fiscale()) && !validaCodiceFiscale(aggiudicatario.getCodice_fiscale())) {
					vOk = false;
					throw new Exception();
				}
			}catch (Exception e) {
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_117.replace("$1", "Codice fiscale / Partita iva dell'operatore economico o affidatario"),i);
			}
		}else{
			vOk = false;
			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il Codice fiscale / Partita iva dell'operatore economico o affidatario"),i);
		}		
		
		// Valida CodiceFiscale del rapresentante
         if(!isEmpty(aggiudicatario.getCf_rappresentante())){
            // solo warning non bloccante
            if(!validaCodiceFiscale(aggiudicatario.getCf_rappresentante())){
               mEccezioni.addValidationWarnElemento(Messaggi.SIMOG_VALIDAZIONE_117.replace("$1", "Codice fiscale del legale rappresentante"),i); 
            }              
         }
         else{
            vOk = false;
            mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il codice fiscale del legale rappresentante"),i);
         }		   
		
   	
		return vOk;
	}

	/**
	 * Validazione aggiudicatari esteri
	 * UN
	 * @param aggiudicatario Rubrica
	 * @param i	int
	 * @return boolean
	 */
	private boolean valida_estero(Rubrica aggiudicatario, int i) {
		boolean vOk = true;
		// Valida il codice fiscale
		if(isEmpty(aggiudicatario.getCodice_fiscale())) {
			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il Codice fiscale / Partita iva dell'operatore economico o affidatario"),i);
		}
		else
			vOk = false;
		return vOk;
	}
	
	/******************************************************************************************************
	 * Validatore per gli Aggiudicatari
	 * 
	 * @param bean List&lt;Rubrica&gt;
	 */
	private void validaAggiudicatari(List<Rubrica> bean) {
		int i = 1;
		for(Rubrica aggiudicatario: bean){
			
			
			// VL - 19-01-2010 rimosso l'if senza parentesi perche faceva saltare la codizione di controllo sulla denominazione
			// 			e perche era inutile visto che non c'era un corpo dell'if.
			//if(!_switch(aggiudicatario, i))

			//UN Diversifica la validazione a seconda se l'aggiudicatario e' italiano oppure no. 
			_switch(aggiudicatario, i);
			// Valida la denominazione
			if(isEmpty(aggiudicatario.getDenominazione()))
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "la denominazione"),i);		
			else{
				if( (!isValidChars(aggiudicatario.getDenominazione())))
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1", "Denominazione"),i);
			}

			//adds partita iva.. controllo la validita' se inserito
			// PP 20101022 controllo solo se italiano
			if(!isYFlag(aggiudicatario.getFlagEsteri()) && !isEmpty(aggiudicatario.getPartitaIva())){
				if(!validaPartitaIva(aggiudicatario.getPartitaIva())){
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_117.replace("$1", "Partita Iva"), i);
				}
			}
			if(isEmpty(aggiudicatario.getCognome()))
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il cognome del rappresentante legale"),i);
			else{
				if( (!isValidChars(aggiudicatario.getCognome())))
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1", "Cognome del rappresentante legale"),i);
			}
		
			// PP obino build 121 nome diventa opzionale
			if(isEmpty(aggiudicatario.getNome()))
				mEccezioni.addValidationWarnElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il nome del rappresentante legale"),i);
			else{
				if( (!isValidChars(aggiudicatario.getNome())))
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1", "Nome del rappresentante legale"),i);
			}
			
			if(Costanti.FLAG_VALORE_SI.equals(aggiudicatario.getFlagEsteri()) || (aggiudicatario.getId_stato()!=null && !"".equals(aggiudicatario.getId_stato()))){
				if(!this.validaStatoEstero(aggiudicatario.getId_stato(), aggiudicatario.getData_inizio_sogg())){
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1","campo Stato Estero"),i);
				}
			}
			i++;
		}
		
	}

	/*******************************************************************************************************
	 * Validatore per i responsabili
	 * 
	 * @param bean : List&lt;RubricaResponsabili&gt;
	 */
	private void validaResponsabili(List<RubricaResponsabili> bean) {
		int i = 1;
		for(RubricaResponsabili responsabile: bean){
			if(!isEmpty(responsabile.getCodice_fiscale_responsabile())){
				if(Costanti.FLAG_VALORE_NO.equals(responsabile.getIsEstero())) {
					try{
						//aggiunto controllo per partita iva (NOTA: field adhoc ?)
						//logger.debug("codice fiscale: "+responsabile.getCodice_fiscale_responsabile());
						if(!validaCodiceFiscale(responsabile.getCodice_fiscale_responsabile()) 
								&& !validaPartitaIva(responsabile.getCodice_fiscale_responsabile())){
							throw new Exception();
						}
					}catch (Exception e) {
						mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_117.replace("$1", "Codice fiscale italiano"),i);
					}
				}
			}else{
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il codice fiscale del soggetto"),i);
			}
			
			if(isEmpty(responsabile.getCognome()))
				mEccezioni.addValidationWarnElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il cognome del soggetto"),i);
			else{
				if( (!isValidChars(responsabile.getCognome())))
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1", "Cognome del soggetto"),i);
			}
			
			
			if(isEmpty(responsabile.getNome()))
				mEccezioni.addValidationWarnElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il nome del soggetto"),i);
			else{
				if( (!isValidChars(responsabile.getNome())))
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1", "Nome del soggetto"),i);
			}
			
			
			if(isEmpty(responsabile.getIndirizzo()))
				mEccezioni.addValidationWarnElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "l'indirizzo del soggetto"),i);
			
			if(!isEmpty(responsabile.getTelefono()))
				if(!isNumber(responsabile.getTelefono()))
					mEccezioni.addValidationWarnElemento(Messaggi.SIMOG_VALIDAZIONE_113.replace("$1", "Telefono"),i);
			
			if(!isEmpty(responsabile.getFax()))
				if(!isNumber(responsabile.getFax()))
					mEccezioni.addValidationWarnElemento(Messaggi.SIMOG_VALIDAZIONE_113.replace("$1", "Fax"),i);
			
			if(!isEmpty(responsabile.getEmail()))
				if(!isMail(responsabile.getEmail()))
					mEccezioni.addValidationWarnElemento(Messaggi.SIMOG_VALIDAZIONE_113.replace("$1", "Email"),i);
			
			if(!isEmpty(responsabile.getCap()))
				if(!isNumber(responsabile.getCap()))
					mEccezioni.addValidationWarnElemento(Messaggi.SIMOG_VALIDAZIONE_113.replace("$1", "Cap"),i);
			if(!isEmpty(responsabile.getComuneIstat())){
				try{
					if(!istatValido(responsabile.getComuneIstat(),responsabile.getData_inizio_res()))
						throw new Exception();
					if(!isEmpty(responsabile.getCap()) && responsabile.getCap().length() != 5) {
						mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_119A, i);
					}
				}catch (Exception e) {
					mEccezioni.addValidationWarnElemento(Messaggi.SIMOG_VALIDAZIONE_113.replace("$1", "Codice Istat"),i);
				}
			}
			i++;
		}
		
	}
	
	/***************************************************************************************************
	 * Validatore per il codice istat
	 * 
	 * @param istat String
	 * @return boolean
	 * @throws SQLException
	 */
	/*
	private boolean istatValido(String istat,Object data) throws SQLException{
		/** quando non ho una base dati valida non vado a controllare sul db **/
	/*
		if(!super.isSQLConnectionEnabled){ return true; }
		
		IstatManager im = new IstatManager(connection,logger);
		return im.isValid(istat,data);
	}
	*/
	private boolean validaStatoEstero(String id_stato_estero,Object data){	
		return super.validaTipologica(STATI_ESTERI.TABLE_NAME, STATI_ESTERI.ID_STATO, STATI_ESTERI.DESCRIZIONE, STATI_ESTERI.DATA_FINE_VALIDITA, data, id_stato_estero);
	}

}
