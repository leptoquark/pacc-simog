package it.avlp.simog.validatore;

import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.conclusione.SchedaConclusione;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.MOTIVI_INTERRUZIONE;
import it.avlp.simog.db.generated.MOTIVI_RISOLUZIONE;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.SimogProperties;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class ConclusioneValidator extends SimogValidator {

	public ConclusioneValidator(Connection connection, Logger logger) {
		super(connection, logger);
		
	}

	@Override
	public boolean valida(Object bean, String section) {
		if(bean != null){
			SchedaConclusione sconBea = (SchedaConclusione)bean;
			valida(sconBea ,sconBea.getInfoComuni().getTipoContratto());
			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
		}else{
			return false;
		}
	}
	/********************************************************************************************************
	 * Validatore per la Scheda Conclusione
	 * 
	 * @param sconBea Scheda Conclusione
	 */
	private void valida(SchedaConclusione sconBea,String tipoContratto){
		
		ConclusioneBean conBea = sconBea.getConclusione();
		String dataVerbaleAggiudicazione = sconBea.getAggiudicazione().getDataVerbaleAggiudicazione();
        String dataVerbaleAggiudicazioneMin = sconBea.getAggiudicazione().getDataVerbaleAggiudicazione();
		
	      // PP 3.02.3.3 per i multi lotto si considera la data maggiore tra le aggiudicazioni
        if(SimogFlags.is30233_RFWEBSC04Active() && sconBea.getAggiudicazione().getDatiEconomici() != null){
           dataVerbaleAggiudicazione = sconBea.getAggiudicazione().getDatiEconomici().getDataVerbaleAggiudicazione();
           dataVerbaleAggiudicazioneMin = sconBea.getAggiudicazione().getDatiEconomici().getDataVerbaleAggiudicazioneMin();
        }

		BigDecimal importoComplessivo = new BigDecimal(0);
		importoComplessivo = calcola(sconBea.getAggiudicazione(),importoComplessivo);
		
	      // PP 3.02.3.3 per i multi lotto si considera la somma degli importi
        if(SimogFlags.is30233_RFWEBSC04Active() && sconBea.getAggiudicazione().getDatiEconomici() != null){
           importoComplessivo = sconBea.getAggiudicazione().getDatiEconomici().getImportoComplessivoApp();
        }

        
      //TICKET ALM #2847 - Motivi interruzione e risoluzione
    	//TICKET ALM #3915
		LottoManager lm = new LottoManager(connection, logger);
		GaraManager gm = new GaraManager(connection,logger);
		boolean isNotSommaUrgenza = false;
		Lotto lotto = new Lotto();
		Gara gara = new Gara();
	    try {
			 lotto = lm.getLotto(sconBea.getInfoComuni().getIdLotto());
		     gara = gm.getGara(lotto.getId_Gara());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	  //MEV 37328 - 3.04.8.1 FASE 2
 		boolean isOsservCompetente = SimogProperties.getInstance().isOsservatorioRegionaleCompetente(gara.getID_OSSERVATORIO());			
 		
 		if(isOsservCompetente)
 		{
 			mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_294);
 		}
 		//FINE MEV 37328
 		
	    //TICKET ALM #14499
        if(!SimogProperties.getInstance().isDataAfterObblighiComunicativiSpeciali(gara.getData_creazione()))
			if (sconBea.getInfoComuni().getFlagEnteSpeciale().equals(Costanti.FLAG_VALORE_SI)
					&& isEmptyOrZero(conBea.getMotiviInterruzione())) {
				mEccezioni.addValidationField("label_MotiviInterruzione");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1",
						"Scheda Conclusione - Causa dell' interruzione anticipata "));
			}
		
		boolean valid=false;
		if (!isEmptyOrZero(conBea.getMotiviInterruzione())
				&& !this.validaTipologicaInterruzione(gara.getData_creazione(), conBea.getMotiviInterruzione())) {
			mEccezioni.addValidationField("label_MotiviInterruzione");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
					"Scheda Conclusione - Causa dell' interruzione anticipata "));
	    } else
	    	valid = true;
		
		
		if(!isEmptyOrZero(conBea.getMotiviInterruzione()) && valid){			
			if(conBea.getMotiviInterruzione().equals(new Long(Costanti.RISOLUZIONE_CONTRATTO))){
				//added check di validit&agrave; tipologica
				if (isEmptyOrZero(conBea.getMotiviRisoluzione())) {
					//campo obbligatorio se campo 2 valorizzato
					mEccezioni.addValidationField("label_MotiviRisoluzione");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_136.replace("$2", "\"Causa dell'interruzione anticipata\" ")
									.replace("$1", "Scheda Conclusione - Motivi risoluzione "));
				} else if (!this.validaTipologicaRisoluzione(gara.getData_creazione(), conBea.getMotiviRisoluzione())) {
					mEccezioni.addValidationField("label_MotiviRisoluzione");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Conclusione - Motivi risoluzione "));
				}
			}
		}
		//FINE TICKET ALM #2847 - Motivi interruzione e risoluzione
		

		//TICKET ALM #3437 MAC
		//Se si sta inviando una interruzione non anticipata, verifica che sia presente una scheda inizio lavori. Controllo da applicare solo via web.
		if(SimogFlags.isFromWeb() && !SimogFlags.isAccordoQuadroOrConvenzione(gara.getID_MODO_REAL())) {
			InizioLavoriManager ilm = new InizioLavoriManager(connection,logger);
			boolean inizioLavoriExists = false;
			try {
				inizioLavoriExists = ilm.existInizioLavoriByAgg(sconBea.getAggiudicazione().getIdAggiudicazione(), 
						                                        sconBea.getAggiudicazione().getDataInizioAggiudicazione(),
						                                        true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// Se non e' stata inserita una scheda inizio lavori confermata e non si tratta
			// di interruzione anticipata, mostra un errore
			if (!inizioLavoriExists && isEmptyOrZero(conBea.getMotiviInterruzione())) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_243.replace("$1", "Scheda Conclusione"));
	     	}
		}
		//FINE TICKET ALM #3437
		

		//2.10 aggiunto controllo 12.1.1.4
		if(isEmptyOrZero(conBea.getDataRisoluzione())){
			if(!isEmptyOrZero(conBea.getMotiviInterruzione())&& valid) 
				if (conBea.getMotiviInterruzione().equals(new Long(Costanti.RISOLUZIONE_CONTRATTO))
						&& (isServizi(tipoContratto) || (isForniture(tipoContratto)))) {
					mEccezioni.addValidationField("label_DataConclusioneAnticipata");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1",
							"Scheda Conclusione - Data conclusione anticipata "));
				}
			    if(!isEmptyOrZero(conBea.getMotiviInterruzione())&& valid)  
				if (isLavori(tipoContratto)) {
					mEccezioni.addValidationField("label_DataInterruzioneAnticipata");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1",
							"Scheda Conclusione - Data interruzione anticipata "));
		}
		} else {
			if(!isDate(conBea.getDataRisoluzione())){
				//data formalemente non corretta
				mEccezioni.addValidationField("label_DataInterruzioneAnticipata");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1",
						"Scheda Conclusione - Data interruzione anticipata "));
			} else if (isDateBigger(dataVerbaleAggiudicazioneMin, conBea.getDataRisoluzione())) {
			
				//TICKET ALM #3915
				Gara g = new Gara();
				try {
					g = gm.getGara(lotto.getId_Gara());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isNotSommaUrgenza = String.valueOf(g.getURGENZA_DL133()).equals(Costanti.FLAG_VALORE_NO);
				
				// Controllo se il lotto prevede l'esecuzione di lavori di somma urgenza. In
				// questo caso non deve esserci errore
				if (!isNotSommaUrgenza) {
					mEccezioni.addValidationField("label_DataInterruzioneAnticipata");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_119.replace("$1",
							"Scheda Conclusione - Data interruzione anticipata "));
				} else
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_119.replace("$1",
							"Scheda Conclusione - Data interruzione anticipata "));
			    //FINE TICKET ALM #3915
			}
		}
		//2.10 fine controllo 12.1.1.4
				
		/*VECCHIO CONTROLLO 12.1.1.4
		if(tipoContratto.equals(Costanti.TIPO_SCHEDA_LAVORI))
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_136.replace("$2", "\"Causa dell'interruzione anticipata\" ").replace("$1", "Scheda Conclusione - Data interruzione anticipata "));
		else
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_136.replace("$2", "\"Causa dell'interruzione anticipata\" ").replace("$1", "Scheda Conclusione - Data conclusione anticipata "));
				   FINE VECCHIO CONTROLLO 12.1.1.4*/
				
		// Ticket ALM #650: aggiunto "Risoluzione contrattuale" tra i motivi che rendono
		// obbligatorio il campo degli Oneri Economici
		if (conBea.getMotiviInterruzione().equals(Costanti.RECESSO_SA)
				|| conBea.getMotiviInterruzione().equals(Costanti.RECESSO_APP)
				|| conBea.getMotiviInterruzione().equals(Costanti.RISOLUZIONE_CONTRATTUALE)) {
			if (isEmpty(conBea.getFlagOneri())) {
			//selzionare almeno un valore tra quelli disp
				mEccezioni.addValidationField("label_FlagOneri");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Conclusione - Flag oneri "));
			}
		}
        
		// PP 22.10.08 obino dice che non e' obbligatorio
		//else{
		//	if(tipoContratto.equals(TIPO_SCHEDA_SERVIZI) || tipoContratto.equals(TIPO_SCHEDA_FORNITURE)){
		//		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Conclusione - Causa dell'interruzione anticipata "));
		//	}			
		//}
		
		logger.debug("[tipo contratto in dati comuni] - "+tipoContratto);
		logger.debug("[isEmpty(conBea.getMotiviInterruzione() in dati comuni] - "+isEmptyOrZero(conBea.getMotiviInterruzione()));
		if(!isEmpty(conBea.getFlagOneri())){
			if(conBea.getFlagOneri().equals(Costanti.SENZA_ONERI)){
				//solo importo ammesso per questo campo anche 0
				//mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Conclusione - Motivi interruzione "));
				if(!isEmptyOrZero(conBea.getOneriRisoluzione())){
					//Risoluzione/rescissione senza oneri! Verificare.
					mEccezioni.addValidationField("label_ImportoOneri");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_137.replace("$1", "Scheda Conclusione - Oneri risoluzione "));
				}
			}else{
				if(isEmptyOrZero(conBea.getOneriRisoluzione())){
					//Inserire importo oneri economici
					mEccezioni.addValidationField("label_ImportoOneri");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_138.replace("$1", "Scheda Conclusione - Oneri risoluzione "));
				}else if(importoComplessivo.compareTo(conBea.getOneriRisoluzione())<0){
					//Valore elevato, verificare
					mEccezioni.addValidationField("label_ImportoOneri");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_132.replace("$1", "Scheda Conclusione - Oneri risoluzione "));
				}
			}
		}
		
		// PP modifica richiesta da Piccinini il 28/09/2010
		if(isEmpty(conBea.getFlagPolizza()) && !isEmptyOrZero(conBea.getMotiviInterruzione())){
			//Selezionare almeno un valore tra quelli previsti
			mEccezioni.addValidationField("label_FlagPolizza");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Conclusione - Flag polizza "));
		}
		
		// PP modifica richiesta da Obino il 11.06.2009 diventa OIF
		if(!isEmpty(conBea.getDataUltimazione())){
			if(!isDate(conBea.getDataUltimazione())){
				//from non corretta
				mEccezioni.addValidationField("label_DataUltimazione");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Conclusione - Data ultimazione "));
			} else if (isDateBigger(dataVerbaleAggiudicazioneMin, conBea.getDataUltimazione())) {
					//Data antecedente la data di aggiudicazione, verificare

				//TICKET ALM #3915 - 3.04.4 
				Gara g = new Gara();
				try {
					g = gm.getGara(lotto.getId_Gara());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isNotSommaUrgenza = String.valueOf(g.getURGENZA_DL133()).equals(Costanti.FLAG_VALORE_NO);
				
				// Controllo se il lotto prevede l'esecuzione di lavori di somma urgenza. In
				// questo caso non deve esserci errore
				if (!isNotSommaUrgenza) {
					mEccezioni.addValidationField("label_DataUltimazione");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_119.replace("$1", "Scheda Conclusione - Data ultimazione "));
				}
			  }
		}
		// PP modifica richiesta da Obino il 11.06.2009 controllo su data risoluzione
		if(isEmpty(conBea.getDataUltimazione()) && isEmpty(conBea.getDataRisoluzione())){
			//Data ultimazione necessaria se se non valorizzata la data di risoluzione
			mEccezioni.addValidationField("label_DataUltimazione");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1", "ultimazione "));	
		}
		
		//2.10 aggiunto controllo 12.1.1.10
		if(!isEmpty(conBea.getNumInfortuni())){
			if(!isNumber(String.valueOf(conBea.getNumInfortuni()))){
				//inserire un numero
				mEccezioni.addValidationField("label_NumeroInfortuni");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_139.replace("$1", "Scheda Conclusione - Numero infortuni "));
			} else if (conBea.getNumInfortuni() > Costanti.MAX_INFORTUNI) {
				//verificare il numero (warn)
				mEccezioni.addValidationWarn(
						Messaggi.SIMOG_VALIDAZIONE_140.replace("$1", "Scheda Conclusione - Numero infortuni "));
			} else if (!isEmpty(conBea.getNumInfPerm()) && !isEmpty(conBea.getNumInfMort())) {
				if (conBea.getNumInfortuni() < (conBea.getNumInfPerm() + conBea.getNumInfMort())) {
					mEccezioni.addValidationField("label_NumeroInfortuniPermanenti");
					mEccezioni.addValidationField("label_NumeroInfortuniMortali");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_208
							.replace("$1", "Scheda Conclusione - Numero totale degli infortuni")
							.replace("$2", "numero degli infortuni mortali e permanenti"));
			     }
			}
		}
		//2.10 fine controllo 12.1.1.10

		//2.10 aggiunto controllo 12.1.1.11
		if(!isEmpty(conBea.getNumInfPerm())){
			try{
				if(!isNumber(String.valueOf(conBea.getNumInfPerm())))
					throw new Exception();
				else if (!isPositive(conBea.getNumInfPerm()))	
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationField("label_NumeroInfortuniPermanenti");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_139.replace("$1",
						"Scheda Conclusione - Numero infortuni permanenti"));
            }
		}
		//2.10 fine controllo 12.1.1.11
			
		//2.10 aggiunto controllo 12.1.1.12
		if(!isEmpty(conBea.getNumInfMort())){
			try{
				if(!isNumber(String.valueOf(conBea.getNumInfMort())))
					throw new Exception();
				else if (!isPositive(conBea.getNumInfMort()))	
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationField("label_NumeroInfortuniMortali");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_139.replace("$1", "Scheda Conclusione - Numero infortuni mortali"));
            }
		}
		//2.10 fine controllo 12.1.1.12
		
		//gm nuovo codice 3.0 per i campi data consegna, data ultimazione e giorni proroga
		
		// modifica richiesta da Piccinini il 29.03.2011 -  if (isEmpty(conBea.getDataConsegna())){
		if (isEmptyOrZero(conBea.getMotiviInterruzione()) && isEmpty(conBea.getDataConsegna())){
			//data consegna è obbligatoria
			if (isLavori(tipoContratto)) {
				// se lavori, il label di data consegna nel front-end è data verbale consegna
				// definitiva
				mEccezioni.addValidationField("label_DataVerbaleConsegna");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Data verbale consegna definitiva"));

			} else {
				// altrimenti, il label di data consegna nel front-end è data verbale di avvio
				// dell' esecuzione del contratto
				mEccezioni.addValidationField("label_DataVerbaleAvvio");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1",
						"Data verbale di avvio dell'esecuzione del contratto"));
			}
		}	
   
		//3.01 attenzione c'è stata una variazione rispetto al PDF -> fatto
		// - se non valorizzato e campo4 non valorizzato - bloccante  - inserire il termine contrattuale ultimazione lavori/servizi/forniture
		/**/
		//Il campo Termine contrattuale ultimazione L/S/F deve essere valorizzato
		if (isEmptyOrZero(conBea.getMotiviInterruzione()) 
				&& isEmpty(conBea.getTermineUltimazione()) 
				&& isEmpty(conBea.getDataRisoluzione())){

		   // PP rilassato controllo per aggiudicazione successive a data di riferimento
				if (DATA_BLOCCANTI.compareTo(dataVerbaleAggiudicazioneMin) >= 0) {
					mEccezioni.addValidationField("label_TermineContrattualeUltimazione");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1",
							"Termine contrattuale di ultimazione lavori/servizi/forniture"));
				} else {
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1",
							"Termine contrattuale di ultimazione lavori/servizi/forniture"));
		   }
		} else {
			if(isEmptyOrZero(conBea.getMotiviInterruzione()) && !isDate(conBea.getTermineUltimazione())) {
				mEccezioni.addValidationField("label_TermineContrattualeUltimazione");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1",
					"Scheda Conclusione - Termine contrattuale di ultimazione lavori/servizi/forniture "));
			}
		}
		
		//Il campo Numero di giorni di proroga se valorizzato deve essere un campo numerico maggiore di zero
		//inltre il campo dovrebbe assumere un valore non superiore ai 99 giorni
		if(!isEmpty(conBea.getGiorniProroga())){
			if(!isNumber(""+conBea.getGiorniProroga()) || conBea.getGiorniProroga() < 0){
				mEccezioni.addValidationField("label_GiorniProroga");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_139.replace("$1", "Giorni di proroga"));
			} else {
				if (conBea.getGiorniProroga() > 99)
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_133.replace("$1", "Giorni di proroga"));
				
			}
			
            if(SimogFlags.is3028_RFWEBSC02Active()){
				if (conBea.getGiorniProroga() > MAX_GIORNI) {
					mEccezioni.addValidationField("label_GiorniProroga");
                    mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_132.replace("$1", "Giorni di proroga"));
            }
		  }	
		}
		
	}
	/**
	 * @param data Object Timestamp or String[yyytmmdd]
	 * @param id Object Long or String[rapresenting an id]
	 * @return boolean
	 */
	private boolean validaTipologicaInterruzione(Object data,Object id){
		if(id == null){ return false; }
		//TICKET ALM #2847 - Motivi interruzione
		if(SimogFlags.is3042Active()) 
			return super.validaTipologicaWithData(MOTIVI_INTERRUZIONE.TABLE_NAME, MOTIVI_INTERRUZIONE.ID_MOTIVO_INTERR, MOTIVI_INTERRUZIONE.DESCRIZIONE, MOTIVI_INTERRUZIONE.DATA_INIZIO_VALIDITA, MOTIVI_INTERRUZIONE.DATA_FINE_VALIDITA,data,id);
	    else 
		    return super.validaTipologica(MOTIVI_INTERRUZIONE.TABLE_NAME, MOTIVI_INTERRUZIONE.ID_MOTIVO_INTERR, MOTIVI_INTERRUZIONE.DESCRIZIONE, MOTIVI_INTERRUZIONE.DATA_FINE_VALIDITA,data,id);
	}
	/**
	 * @param data Object Timestamp or String[yyytmmdd]
	 * @param id Object Long or String[rapresenting an id]
	 * @return boolean
	 */
	private boolean validaTipologicaRisoluzione(Object data,Object id){
		if(id == null){ return false; }
		//TICKET ALM #2847 - Motivi risoluzione
		if(SimogFlags.is3042Active()) 
		     return super.validaTipologicaWithData(MOTIVI_RISOLUZIONE.TABLE_NAME, MOTIVI_RISOLUZIONE.ID_MOTIVO_RISOL, MOTIVI_RISOLUZIONE.DESCRIZIONE, MOTIVI_RISOLUZIONE.DATA_INIZIO_VALIDITA, MOTIVI_RISOLUZIONE.DATA_FINE_VALIDITA,data,id);
		else
			 return super.validaTipologica(MOTIVI_RISOLUZIONE.TABLE_NAME, MOTIVI_RISOLUZIONE.ID_MOTIVO_RISOL, MOTIVI_RISOLUZIONE.DESCRIZIONE, MOTIVI_RISOLUZIONE.DATA_FINE_VALIDITA,data,id);
		
	}
}
