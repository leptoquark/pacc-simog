package it.avlp.simog.validatore;

import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.collaudo.SchedaCollaudo;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import java.sql.SQLException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.SimogProperties;

import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;

public class CollaudoValidator extends SimogValidator {

	public CollaudoValidator(Connection connection, Logger logger) {
		super(connection, logger);
		
	}

	@Override
	public boolean valida(Object bean, String section) {
		if(bean != null){
			SchedaCollaudo scolBea = (SchedaCollaudo)bean;
			// spostata la validazione dei responsabili, perche ricorrente, nell'upper class
			// in modo da centralizzarla
			aggiungiSezione(scolBea.getIncaricati(), section);
			valida(scolBea.getIncaricati());
			valida(scolBea);
			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
		}else{
			return false;
		}
	}

    private boolean validaIdMotivoVarCO(CollaudoBean colBea, InfoComuniBean infoBea){
       
       boolean retVal = true;
       if(colBea.getIdMotivoVarCO() == null){
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Motivazione della variazione anagrafica"));
          retVal = false;
		} else {
           try{
               if(!isMotivoVarCOValido(colBea.getIdMotivoVarCO(), colBea.getDataIniColl()))
                   throw new Exception();
               else //TICKET ALM #3582 #4194
				if (SimogFlags.is3043Active() && SimogFlags.isVarAnagActive() && colBea.isValidaVariazione()
						&& (infoBea.getID_MODO_REAL() != Costanti.MODOREAL_CONCESSIONE_LAVORI
								&& infoBea.getID_MODO_REAL() != Costanti.MODOREAL_FINANZA_DI_PROGETTO)
						&& colBea.getIdMotivoVarCO().equals(Costanti.MOTIVO_SOCIETA_PROGETTO)) {
           			mEccezioni.addValidationField("label_MotivoVariazione");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_232.replace("$1", "Motivazione della variazione anagrafica "));
        		}
        		//FINE TICKET ALM #3582 4194
           }catch (Exception e) {
              retVal = false;
              mEccezioni.addValidationField("label_MotivoVariazione");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Motivazione della variazione anagrafica"));
           }
       }
       return retVal;
    }

	/******************************************************************************************************
	 * Validatore per la Scheda collaudo sui campi :
	 * <ul>
	 * <li>data collaudo statico
	 * <li>data certificato regolare esecuzione
	 * <li>modalita' collaudo
	 * <li>data nomina collaudatore
	 * <li>data inizio collaudo
	 * <li>data redazione certificato
	 * <li>data delibera
	 * <li>esito callaudo
	 * <li>importo finale servizi
	 * <li>importo finale forniture
	 * <li>importo finale lavori
	 * <li>importo finale sicurezza
	 * <li>importo progettazione
	 * <li>somme a disposizione
	 * </ul>
	 * 
	 * @param scolBea SchedaCollaudo
	 */
	private void valida(SchedaCollaudo scolBea){
		AggiudicazioneBean aggBea = scolBea.getAggiudicazione();
		InfoComuniBean infoBea = scolBea.getInfoComuni();
		
		//MEV 37328 - 3.04.8.1 FASE 2
		LottoManager lm2 = new LottoManager(connection, logger);
		GaraManager gm = new GaraManager(connection, logger);
	    Lotto lotto2 = new Lotto();
	    Gara gara = new Gara();
	    try {
	         lotto2 = lm2.getLotto(infoBea.getIdLotto());
	         gara = gm.getGara(lotto2.getId_Gara());
	       
	 		boolean isOsservCompetente = SimogProperties.getInstance().isOsservatorioRegionaleCompetente(gara.getID_OSSERVATORIO());			
	 		
	 		if(isOsservCompetente)
	 		{
	 			mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_292);
	 		}
	 		
		} catch (SQLException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	     } catch (Exception e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	     }	
 		//FINE MEV 37328
			    
		ConclusioneBean conBea = scolBea.getConclusione();
		//List<AccordoBean> laccBea = scolBea.getAccordiBonario();
		CollaudoBean colBea = scolBea.getCollaudo();
		//logger.debug(ObjectIntrospector.propertiesInfo(CollaudoBean.class, colBea));
		// logger.debug(ObjectIntrospector.propertiesInfo(ConclusioneBean.class,
		// conBea));
		
		// PP B302.2.0
		if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive() && colBea.isValidaVariazione()){
		   validaIdMotivoVarCO(colBea,infoBea);
		}
		
		double importoComplessivo = (getImportoComplessivoAppalto(aggBea.getImportoLavori(),aggBea.getImportoServizi(),
				aggBea.getImportoForniture(), aggBea.getImportoAttuazioneSicurezza(), aggBea.getImportoNonAssog(),
				aggBea.getImportoProgettazione()))
				+ (aggBea.getImportoDisposizione() == null ? 0 : aggBea.getImportoDisposizione().doubleValue());
		
		// String dataInizioCollaudo =
		// convertTimestampToString(colBea.getDataIniColl());
		String dataVerbaleAggiudicazione = aggBea.getDataVerbaleAggiudicazione();
		BigDecimal[] big = new BigDecimal[] { colBea.getImpFinaleLavori(), colBea.getImpFinaleServizi(),
				colBea.getImpFinaleFornit() };
		BigDecimal subtotale = new BigDecimal(0);
		subtotale = calcola(big, subtotale);
		/*
		int numRiserve = calcolaRiserve(laccBea);
		BigDecimal oneriComplessiviDerivati = calcolaOneriComplessiviDerivati(laccBea);
		*/
		
		//2.10 controllo 13.1.1.2
		//Campo 2 : datacollaudo statico OK ( uguale )
		if(!isEmpty(colBea.getDataCollaudoStat())){
			if(!isDate(colBea.getDataCollaudoStat())){
				//data form
				mEccezioni.addValidationField("label_DataCollaudoStatico");	
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Collaudo - Data collaudo statico "));
			} else {
				// PP 01.07.2009 il campo data ultimazione diventa facoltativo, il controllo
				// viene effettuato solo
				// se questo è valorizzato
				if(scolBea.getInizioLavori()!=null){
					if (!isEmpty(scolBea.getInizioLavori().getDataVerbaleInizio())
							&& isDate(scolBea.getInizioLavori().getDataVerbaleInizio())) {
					    //deve essere maggiore del campo 18 di "esecuzione iniziale"
						if (isDateLowerEq(colBea.getDataCollaudoStat(),
								scolBea.getInizioLavori().getDataVerbaleInizio())) {
							mEccezioni.addValidationField("label_DataCollaudoStatico");	
						    //data antecedente la data di ultimazione
							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_144
									.replace("$1", "Scheda Collaudo - Data collaudo statico ")
									.replace("$2", "data di effettivo inizio"));
						}
					}
				}
			}
		}
		//2.10 fine controllo 13.1.1.2
		
		//2.10 aggiunto controllo 13.1.1.3
		if(!isEmpty(colBea.getDataRegolareEsec())){
			if(!isDate(colBea.getDataRegolareEsec())){
				mEccezioni.addValidationField("label_DataRegolareEsecuzione");	
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Collaudo - Data regolare esecuzione "));
			} else if (conBea != null && isDateLower(colBea.getDataRegolareEsec(), conBea.getDataUltimazione())) {
				mEccezioni.addValidationField("label_DataRegolareEsecuzione");	
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_144.replace("$1", "Scheda Collaudo - Data regolare esecuzione ")
								.replace("$2", "data di ultimazione"));
			}
		}
		if(isEmpty(colBea.getDataRegolareEsec())&&isEmpty(colBea.getModoCollaudo())) {
			mEccezioni.addValidationField("label_DataRegolareEsecuzione");
			mEccezioni.addValidationField("label_ModoCollaudo");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_180);
		}
		if(!isEmpty(colBea.getDataRegolareEsec())&&!isEmpty(colBea.getModoCollaudo())) {
			mEccezioni.addValidationField("label_DataRegolareEsecuzione");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_181);
		}
		if(!isEmpty(colBea.getDataRegolareEsec())){
			if (importoComplessivo > Costanti.IMPORTO_LOTTO_1000000
					|| importoComplessivo == Costanti.IMPORTO_FUORI_SCALA) {
		    	if (isLavori(infoBea.getTipoContratto()))
					// redigere certificato di collaudo in luogo del certificato di regolare
					// esecuzione
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_145.replace("$1",
							"Scheda Collaudo - Data regolare esecuzione "));
		    }
		}
		//2.10 fine controllo 13.1.1.3
		
		//2.10 aggiunto controllo 13.1.1.4
		if(!isEmpty(colBea.getModoCollaudo())&&!isEmpty(colBea.getDataRegolareEsec())) {
			mEccezioni.addValidationField("label_ModoCollaudo");	
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_182);
		}
		if(!isEmpty(colBea.getModoCollaudo())){
			//gm aggiunto codice per adesione accordo quadro
			if ((!TipoAggiudicazione.Q.equals(aggBea.getSottotipo())
					&& importoComplessivo <= Costanti.IMPORTO_LOTTO_500000
					&& importoComplessivo != Costanti.IMPORTO_FUORI_SCALA)
					|| (TipoAggiudicazione.Q.equals(aggBea.getSottotipo())
							&& (importoComplessivo > Costanti.IMPORTO_LOTTO_1000000
									|| importoComplessivo == Costanti.IMPORTO_FUORI_SCALA))) {
		    	if (isLavori(infoBea.getTipoContratto()))
					mEccezioni.addValidationWarn(
							Messaggi.SIMOG_VALIDAZIONE_150.replace("$1", "Scheda Collaudo - Modalità di collaudo "));
		    }
		}				
		//2.10 fine controllo 13.1.1.4		
		
		/*
		if(!isEmpty(colBea.getDataRegolareEsec()) && (!isEmpty(colBea.getDataCertCollaudo())&& isDate(colBea.getDataCertCollaudo()))){
			//Presenza del certificato di collaudo
		}
		else if(!isEmpty(colBea.getDataRegolareEsec()) && isEmpty(colBea.getDataCertCollaudo())){			
			//isdate
			if(!isDate(colBea.getDataRegolareEsec())){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Collaudo - Data regolare esec "));
			}
			else{
				//maggiore campo9
				if(isDateBiggerEq(conBea.getDataUltimanzione(),colBea.getDataRegolareEsec())){
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_144.replace("$1", "Scheda Collaudo - Data regolare esec ").replace("$2", conBea.getDataUltimanzione()));
				}
				//se 37 maggiore di			
				else if(importoComplessivo.compareTo(new BigDecimal(10000000))>0){
					//redigere certificato di collaudo in luogo del certificato di regolare esecuzione
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_145.replace("$1", "Scheda Collaudo - Data regolare esec "));
				}
			}
		}
		*/
		
	//campo 5 : data nomina collaudatore OK
		if(isEmpty(colBea.getDataNominaColl())){
			//se campo 4  valorizzato errore
			if(!isEmpty(colBea.getModoCollaudo())){
//				mEccezioni.addValidationErr("Scheda Collaudo - " + Messaggi.SIMOG_VALIDAZIONE_107.replace("$1", " nomina collaudatore / Commissione"));
				mEccezioni.addValidationField("label_DataNominaCollaudatore");	
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_107.replace("$1", " nomina collaudatore / Commissione"));
			}
		} else {
			if(!isDate(colBea.getDataNominaColl())) {
				mEccezioni.addValidationField("label_DataNominaCollaudatore");	
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1",
						"Scheda Collaudo - Data nomina collaudatore / Commissione"));
			}
		}
			/*
			else{
				//deve essere piu piccolo del campo 3 (se c'e')
				if(!isEmpty(colBea.getDataRegolareEsec()) &&  isDate(colBea.getDataRegolareEsec())){ 
					if(isDateBigger(colBea.getDataNominaColl(),colBea.getDataRegolareEsec())){
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_146.replace("$1", "Scheda Collaudo - Data nomina collaudatore / Commissione"));
					}
				}
			}
			*/
		
		//campo 6 : data inizio collaudo
		if(isEmpty(colBea.getDataIniOper())){
			//se campo 4  valorizzato errore
			if(!isEmpty(colBea.getModoCollaudo())){
				mEccezioni.addValidationField("label_DataInizioOperColl");	
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_107.replace("$1", " inizio operazioni di collaudo "));
			}
		} else {
			if(!isDate(colBea.getDataIniOper())){
				mEccezioni.addValidationField("label_DataInizioOperColl");	
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Collaudo - Data inizio collaudo "));
			} else {
				//deve essere >= campo 5
				//if(dataIniOp < dataNomina) => eccezione
				if(!isEmpty(colBea.getDataNominaColl()) &&  isDate(colBea.getDataNominaColl())){
					if(isDateLower(colBea.getDataIniOper(),colBea.getDataNominaColl())){
						mEccezioni.addValidationField("label_DataInizioOperColl");	
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_148.replace("$1",
								"Scheda Collaudo - Data inizio collaudo "));
					}
				}
			}
		}
				//deve essere <= campo 3   (see esiste)
//				if(!isEmpty(colBea.getDataRegolareEsec()) &&  isDate(colBea.getDataRegolareEsec())){
//					if(isDateBigger(colBea.getDataIniOper(),colBea.getDataRegolareEsec())){
//						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_147.replace("$1", "Scheda Collaudo - Data inizio collaudo "));
//					}
//				}
		
		//campo 7 : data redazione certificato OK
		// controllo se deve essere inserito il certificato di collaudo o la regolare
		// esecuzione
		if(isEmpty(colBea.getDataCertCollaudo())){
			//se campo 4  valorizzato errore
			if(!isEmpty(colBea.getModoCollaudo())){
				mEccezioni.addValidationField("label_DataRedCert");	
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_107.replace("$1", " redazione certificato di collaudo "));
			}
		} else {
			//controllo se e' una data
			if(!isDate(colBea.getDataCertCollaudo())){
				mEccezioni.addValidationField("label_DataRedCert");	
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1",
						"Scheda Collaudo - Redazione certificato di collaudo"));
			} else {
				//deve essere >= campo 6
				if(!isEmpty(colBea.getDataIniOper()) &&  isDate(colBea.getDataIniOper())){
					if(isDateLower(colBea.getDataCertCollaudo(),colBea.getDataIniOper())){
						mEccezioni.addValidationField("label_DataRedCert");	
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_149.replace("$1",
								"Scheda Collaudo - Data redazione certificato "));
					}
				}
				//gm aggiunto codice per adesione accordo quadro
				if ((!TipoAggiudicazione.Q.equals(aggBea.getSottotipo())
						&& importoComplessivo <= Costanti.IMPORTO_LOTTO_500000
						&& importoComplessivo != Costanti.IMPORTO_FUORI_SCALA)
						|| (TipoAggiudicazione.Q.equals(aggBea.getSottotipo())
								&& (importoComplessivo > Costanti.IMPORTO_LOTTO_1000000
										|| importoComplessivo == Costanti.IMPORTO_FUORI_SCALA))) {
				   	if (isLavori(infoBea.getTipoContratto()))
						mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_209
								.replace("$1", "Scheda Collaudo - Data redazione certificato ")
								.replace("$2", "certificato di regolare esecuzione"));
				}	
			}
		}	
		//campo 8 : data delibera OK
		if(!isEmpty(colBea.getDataDelibera())){
			if(!isDate(colBea.getDataDelibera())){
				mEccezioni.addValidationField("label_DataDelibera");	
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Collaudo - Data delibera "));
			} else {
//				deve essere maggiore campo 87
//				if(isDateLowerEq(colBea.getDataDelibera(),dataVerbaleAggiudicazione)){
//					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_119.replace("$1", "Scheda Collaudo - Data delibera "));
//				}			
				//deve essere >= campo 7
				if(!isEmpty(colBea.getDataCertCollaudo()) &&  isDate(colBea.getDataCertCollaudo())){
					if(isDateLower(colBea.getDataDelibera(),colBea.getDataCertCollaudo())){
						mEccezioni.addValidationField("label_DataDelibera");
						mEccezioni.addValidationErr(
								Messaggi.SIMOG_VALIDAZIONE_183.replace("$1", "Scheda Collaudo - Data delibera "));
					}
				}			
			}
		}
		
		//campo 9 : esito collaudo OK
		if(isEmpty(colBea.getEsitoCollaudo())){
			mEccezioni.addValidationField("label_EsitoCollaudo");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Collaudo - Esito collaudo "));
		}
		
		//-------------------------------------------------------------------------------------------------------------------------------------------------
		//                                                 FORNITURE e SERVIZI
		//-------------------------------------------------------------------------------------------------------------------------------------------------
		
		//2.10 aggiunto controllo 13.2.1.10
		if(!isEmpty(colBea.getImpFinaleLavori())){
			// se il campo importo lavori non e' vuoto verifico che non sia negativo
			if(!isPositive(colBea.getImpFinaleLavori())){
				//errore deve essere maggiore di Zero 
				mEccezioni.addValidationField("label_ImportoLavori");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_151.replace("$1", "Scheda Collaudo - Importo finale lavori "));
			} else if (!isNumberDecimal(colBea.getImpFinaleLavori().toString())
					|| !validateDecimalPart(colBea.getImpFinaleLavori(), 3)) {
				//2 decimali!
				mEccezioni.addValidationField("label_ImportoLavori");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_118.replace("$1", "Scheda Collaudo - Importo finale lavori "));
			}
		}
		if(isLavori(infoBea.getTipoContratto()) && isEmptyOrZero(colBea.getImpFinaleLavori())) {
			mEccezioni.addValidationField("label_ImportoLavori");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "Scheda Collaudo - Importo finale lavori "));
		//2.10 fine controllo 13.2.1.10
		}
		//2.10 aggiunto controllo 13.2.1.11
		if(!isEmpty(colBea.getImpFinaleServizi())){
			// se il campo importo servizi non e' vuoto verifico che non sia negativo
			if(!isPositive(colBea.getImpFinaleServizi())){
				//errore deve essere maggiore di Zero 
				mEccezioni.addValidationField("label_ImportoServizi");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_151.replace("$1", "Scheda Collaudo - Importo finale servizi "));
			}
			//else 
			else if (!isNumberDecimal(colBea.getImpFinaleServizi().toString())
					|| !validateDecimalPart(colBea.getImpFinaleServizi(), 3)) {
			//2 decimali!
				mEccezioni.addValidationField("label_ImportoServizi");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_118.replace("$1", "Scheda Collaudo - Importo finale servizi "));
			}
		}
		if(isServizi(infoBea.getTipoContratto()) && isEmptyOrZero(colBea.getImpFinaleServizi())) {
			mEccezioni.addValidationField("label_ImportoServizi");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "Scheda Collaudo - Importo finale servizi "));
		}
		//2.10 fine controllo 13.2.1.11
		
		//2.10 aggiunto controllo 13.2.1.12
		if(!isEmpty(colBea.getImpFinaleFornit())){
			// se il campo importo forniture non e' vuoto verifico che non sia negativo
			if(!isPositive(colBea.getImpFinaleFornit())){
				//errore deve essere maggiore di Zero 
				mEccezioni.addValidationField("label_ImportoForn");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_151.replace("$1", "Scheda Collaudo - Importo finale forniture "));
			} else if (!isNumberDecimal(colBea.getImpFinaleFornit().toString())
					|| !validateDecimalPart(colBea.getImpFinaleFornit(), 3)) {
				//2 decimali!
				mEccezioni.addValidationField("label_ImportoForn");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_118.replace("$1", "Scheda Collaudo - Importo finale forniture "));
			}
		}
		if(isForniture(infoBea.getTipoContratto()) && isEmptyOrZero(colBea.getImpFinaleFornit())) {
			mEccezioni.addValidationField("label_ImportoForn");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "Scheda Collaudo - Importo finale forniture "));
		//2.10 fine controllo 13.2.1.12
		}
		
		/* CONTROLLI DEPRECATI NEL 13.1.1.10,11,12
		if(isEmptyOrZero(colBea.getImpFinaleFornit()) && (isEmptyOrZero(colBea.getImpFinaleServizi()) && isEmptyOrZero(colBea.getImpFinaleLavori()))){
			// Se importo servizi e' zero e importo lavori e' 0 in questo  
			// caso importo forniture e' obbligatorio, essendo anch'esso vuoto  va inviato il seguente messaggio di errore 
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_142.replace("$1", "Scheda Collaudo - Importo finale forniture ").replace("$2", "\"Importo finale componenti servizi\"").replace("$3","\"Importo finale componenti lavori\""));
		}

		if(isEmptyOrZero(colBea.getImpFinaleServizi()) && (isEmptyOrZero(colBea.getImpFinaleFornit()) && isEmptyOrZero(colBea.getImpFinaleLavori()))){
			// Se importo servizi e forniture non sono inseriti il campo lavori risulta eobbligatorio, essendo anch'esso vuoto va 
			// inviato un errore.  
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_142.replace("$1", "Scheda Collaudo - Importo finale lavori ").replace("$2", "\"Importo finale componenti servizi\"").replace("$3","\"Importo finale componenti forniture\""));
		}

		if(isEmptyOrZero(colBea.getImpFinaleFornit()) && (isEmptyOrZero(colBea.getImpFinaleServizi()) && isEmptyOrZero(colBea.getImpFinaleLavori()))){
			// Se importo lavori e' zero e importo servizi e' zero allora importo forniture risulta obbligatorio. Se invece risulta vuoto o zero va
			// generato un messaggio di errore
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_142.replace("$1", "Scheda Collaudo - Importo finale servizi ").replace("$2", "\"Importo finale componenti forniture\"").replace("$3","\"Importo finale componenti lavori\""));
		}
		*/
		
		// ---------------------------------------------------- FINE SEZIONE RELATIVA
		// CAMPI 10-11-12
		// ---------------------------------------------------------------------------------------
		
		//campo 14 : importo finale sicurezza OK
		//if(isEmpty(colBea.getImpFinaleSicur())){
			//campo obbligatorio
		// mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1",
		// "Scheda Collaudo - Importo finale sicurezza "));
		//}else{
		if(!isEmptyOrZero(colBea.getImpFinaleSicur())){
			if (!isNumberDecimal(colBea.getImpFinaleSicur().toString())
					|| !validateDecimalPart(colBea.getImpFinaleSicur(), 3)) {
				//err
				mEccezioni.addValidationField("label_ImportoSic");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_152.replace("$1", "Scheda Collaudo - Importo finale sicurezza "));
			} else {
				if(colBea.getImpFinaleSicur().compareTo(subtotale)>0){
					//verificare l'importo digitato
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_153.replace("$1",
							"Scheda Collaudo - Importo finale sicurezza "));
				}
			}
		}
		
		
		//campo 15 : importo progettazione OK
		//if(isEmpty(colBea.getImpProgettazione())){
			//campo obbligatori
		// mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1",
		// "Scheda Collaudo - Importo progettazione "));
		//}else{
		if(!isEmptyOrZero(colBea.getImpProgettazione())){
			if (!isNumberDecimal(colBea.getImpProgettazione().toString())
					|| !validateDecimalPart(colBea.getImpProgettazione(), 3)) {
				//err
				mEccezioni.addValidationField("label_ImportoProg");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_152.replace("$1", "Scheda Collaudo - Importo progettazione "));
			} else {
				if(colBea.getImpProgettazione().compareTo(subtotale)>0){
					//err
					mEccezioni.addValidationField("label_ImportoProg");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_153.replace("$1", "Scheda Collaudo - Importo progettazione "));
				}
			}
		}
		
		//campo 17 : somme a disposizione
		if(isEmpty(colBea.getImpDisposizione())){
			mEccezioni.addValidationField("label_ImportoDisp");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "Scheda Collaudo - Importo disposizione "));
		} else {
			if (!isPositive(colBea.getImpDisposizione()) && isLavori(infoBea.getTipoContratto())) {
				mEccezioni.addValidationField("label_ImportoDisp");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "Scheda Collaudo - Importo disposizione "));
			}
			if (!isNumberDecimal(colBea.getImpDisposizione().toString())
					|| !validateDecimalPart(colBea.getImpDisposizione(), 3)) {
				mEccezioni.addValidationField("label_ImportoDisp");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_118.replace("$1", "Scheda Collaudo - Importo disposizione "));
			}
		}
// PP 12.08.09 SCHEDE_V30
//			/* se maggiore campo 36 errore */			
//			else if(colBea.getImpDisposizione().compareTo(aggBea.getImportoDisposizione())>0){
//				//err
//				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_154.replace("$1", "Scheda Collaudo - Importo disposizione "));
//			}	
		//campo aggiunto : lavori annuali estesi
		if(isEmpty(colBea.getFlagLavoriEstesi())){
			mEccezioni.addValidationField("label_LavoriAnnEstesi");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1",
					"Scheda Collaudo - Lavori annuali estesi a piu' esercizi "));
		}
		
		
		/* mancano i campi 19 e 20 */
		//-- non c'e bisogno di cotrollarli insieme ai campi dal 26 al 28 --//
		validaAmm(colBea,new BigDecimal(importoComplessivo));
		validaArb(colBea,new BigDecimal(importoComplessivo));
		validaGiu(colBea,new BigDecimal(importoComplessivo));
		validaTra(colBea,new BigDecimal(importoComplessivo));
	}
	/*****************************************************************************************************
	 * Validatore per la parte amministrativa, effettua controlli sul formato relativo ai campi :
	 * <ul>
	 * <li>numero definite via amministrativa
	 * <li>Numero da definite via amministrativa
	 * <li>importo totale definizione amministrativa
	 * <li>importo totale definizione via amministrativa
	 * <li>Importo totale richiesto via amministrativa
	 * </ul>
	 * Viene inoltre controllato che l'importo totale definite via mministrativa non sia maggiore del campo 18
	 * 
	 * @param colBea CollaudoBean 
	 * @param big BigDecimal
	 */
	private void validaAmm(CollaudoBean colBea,BigDecimal big){
		BigDecimal campo18 = new BigDecimal(0);
		BigDecimal[] bigA = new BigDecimal[]{big,colBea.getImpDisposizione()};
		campo18 = calcola(bigA, campo18);
		
		// ------------------------------------- Numero definite via amministrativa
		// --------------------------------------------
		if(!isEmpty(colBea.getAmmNumDefinite())){
			if(!isNumber(""+colBea.getAmmNumDefinite())){
				mEccezioni.addValidationField("label_NumDefAmm");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1",
						"Scheda Collaudo - Numero definite via amministrativa "));
			}
		}
		// ----------------------------------- Numero da definite via amministrativa
		// -------------------------------------------
		if(!isEmpty(colBea.getAmmNumDaDef())){
			if(!isNumber(""+colBea.getAmmNumDaDef())){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1",
						"Scheda Collaudo - Numero da definite via amministrativa "));
			}
		}
		 
		if((isNumber(""+colBea.getAmmNumDefinite()) && (colBea.getAmmNumDefinite()>0)) 
				|| (isNumber(""+colBea.getAmmNumDaDef())&& (colBea.getAmmNumDaDef()>0))){
			
			// ------------------------------ Importo totale definizione via amministrativa
			// -------------------------------------------
			
			if(isEmpty(colBea.getAmmImportoDef())){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_136
						.replace("$1", "Scheda Collaudo - Importo totale definizione via amministrativa")
						.replace("$2", "Numero definite via amministrativa o Numero da definite via amministrativa"));
			}else{
				//se maggionre campo 18 errore
				if(colBea.getAmmImportoDef().compareTo(campo18)>0){
					//err
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_153.replace("$1",
							"Scheda Collaudo - Importo totale definizione via amministrativa"));
				}
			}
			if(isEmptyOrZero(colBea.getAmmImportoRich())){
				//err
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_136
						.replace("$1", "Scheda Collaudo - Importo totale definizione via amministrativa")
						.replace("$2", "Numero definite via amministrativa o Numero da definite via amministrativa"));
			}else{
				//se maggiore campo 18 errore
				
				// ------------------------------ Importo totale richiesto via amministrativa
				// -------------------------------------------
				
				if(colBea.getAmmImportoRich().compareTo(campo18)>0){
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_153.replace("$1",
							"Scheda Collaudo - Importo totale richiesto via amministrativa"));
				}
			}	
		}	
	}
	
	
	/*****************************************************************************************************************
	 * Effettua controlli sul formato relativo ai campi :
	 * <ul>
	 * <li>numero definite via arbitrale
	 * <li>Numero da definite via arbitrale
	 * <li>importo totale definizione arbitrale
	 * <li>importo totale definizione via arbitrale
	 * <li>Importo totale richiesto via arbitrale
	 * </ul>
	 * 
	 * @param colBea CollaudoBean
	 * @param big BigDecimal
	 */
	private void validaArb ( CollaudoBean colBea,BigDecimal big ){
		BigDecimal campo18 = new BigDecimal(0);
		BigDecimal[] bigA = new BigDecimal[]{big,colBea.getImpDisposizione()};
		campo18 = calcola(bigA, campo18);
		if(!isEmpty(colBea.getArbNumDefinite())){
			if(!isNumber(""+colBea.getArbNumDefinite())){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1",
						"Scheda Collaudo - Numero definite via arbitrale "));
			}
		}
		if(!isEmpty(colBea.getArbNumDaDef())){
			if(!isNumber(""+colBea.getArbNumDaDef())){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1",
						"Scheda Collaudo - Numero da definite via arbitrale "));
			}
		}
		if((isNumber(""+colBea.getArbNumDefinite()) && (colBea.getArbNumDefinite()>0)) 
				|| (isNumber(""+colBea.getArbNumDaDef())&& (colBea.getArbNumDaDef()>0))){
			if(isEmpty(colBea.getArbImportoDef())){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_136
						.replace("$1", "Scheda Collaudo - Importo totale definizione via arbitrale")
						.replace("$2", "Numero definite via arbitrale o Numero da definite via arbitrale"));
			}else{
				//se maggiore del campo 18 errore
				if(colBea.getArbImportoDef().compareTo(campo18)>0){
					//err
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_153.replace("$1",
							"Scheda Collaudo - Importo totale definizione via arbitrale"));
				}
			}
			if(isEmptyOrZero(colBea.getArbImportoRich())){
				//err
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_136
						.replace("$1", "Scheda Collaudo - Importo totale definizione via arbitrale")
						.replace("$2", "Numero definite via arbitrale o Numero da definite via arbitrale"));
			}else{
				//se maggiore del campo 18 errore
				if(colBea.getArbImportoRich().compareTo(campo18)>0){
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_153.replace("$1",
							"Scheda Collaudo - Importo totale richiesto via arbitrale"));
				}
			}	
		}		
	}
	
	
	/**********************************************************************************************************************
	 * Validatore per la parte amministrativa, effettua controlli sul formato relativo ai campi :
	 * <ul>
	 * <li>numero definite via giudiziale
	 * <li>Numero da definite via giudiziale
	 * <li>importo totale definizione giudiziale
	 * <li>importo totale definizione via giudiziale
	 * <li>Importo totale richiesto via giudiziale
	 * </ul>
	 * 
	 * @param colBea
	 * @param big
	 */
	private void validaGiu(CollaudoBean colBea,BigDecimal big){
		BigDecimal campo18 = new BigDecimal(0);
		BigDecimal[] bigA = new BigDecimal[]{big,colBea.getImpDisposizione()};
		campo18 = calcola(bigA, campo18);
		if(!isEmpty(colBea.getGiuNumDefinite())){
			if(!isNumber(""+colBea.getGiuNumDefinite())){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1",
						"Scheda Collaudo - Numero definite via giudiziale "));
			}
		}
		if(!isEmpty(colBea.getGiuNumDaDef())){
			if(!isNumber(""+colBea.getGiuNumDaDef())){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1",
						"Scheda Collaudo - Numero da definite via giudiziale "));
			}
		}
		if((isNumber(""+colBea.getGiuNumDefinite()) && (colBea.getGiuNumDefinite()>0)) 
				|| (isNumber(""+colBea.getGiuNumDaDef())&& (colBea.getGiuNumDaDef()>0))){
			if(isEmpty(colBea.getGiuImportoDef())){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_136
						.replace("$1", "Scheda Collaudo - Importo totale definizione via giudiziale")
						.replace("$2", "Numero definite via giudiziale o Numero da definite via giudiziale"));
			}else{
				//se maggionre campo 18 errore
				if(colBea.getGiuImportoDef().compareTo(campo18)>0){
					//err
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_153.replace("$1",
							"Scheda Collaudo - Importo totale definizione via giudiziale"));
				}
			}
			if(isEmptyOrZero(colBea.getGiuImportORich())){
				//err
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_136
						.replace("$1", "Scheda Collaudo - Importo totale definizione via giudiziale")
						.replace("$2", "Numero definite via giudiziale o Numero da definite via giudiziale"));
			}else{
				//se maggiore campo 18 errore
				if(colBea.getGiuImportORich().compareTo(campo18)>0){
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_153.replace("$1",
							"Scheda Collaudo - Importo totale richiesto via giudiziale"));
				}
			}	
		}		
	}
	
	/**********************************************************************************************************************
	 * Validatore per la parte amministrativa, effettua controlli sul formato relativo ai campi :
	 * <ul>
	 * <li>numero definite via transattiva
	 * <li>Numero da definite via transattiva
	 * <li>importo totale definizione transattiva
	 * <li>importo totale definizione via transattiva
	 * <li>Importo totale richiesto via transattiva
	 * </ul>
	 * 
	 * @param colBea
	 * @param big
	 */
	private void validaTra(CollaudoBean colBea,BigDecimal big){
		BigDecimal campo18 = new BigDecimal(0);
		BigDecimal[] bigA = new BigDecimal[]{big,colBea.getImpDisposizione()};
		campo18 = calcola(bigA, campo18);
		if(!isEmpty(colBea.getTraNumDefinite())){
			if(!isNumber(""+colBea.getTraNumDefinite())){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1",
						"Scheda Collaudo - Numero definite via transattiva "));
			}
		}
		if(!isEmpty(colBea.getTraNumDaDef())){
			if(!isNumber(""+colBea.getTraNumDaDef())){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1",
						"Scheda Collaudo - Numero da definite via transattiva "));
			}
		}
		if((isNumber(""+colBea.getTraNumDefinite()) && (colBea.getTraNumDefinite()>0)) 
				|| (isNumber(""+colBea.getTraNumDaDef())&& (colBea.getTraNumDaDef()>0))){
			if(isEmpty(colBea.getTraImportoDef())){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_136
						.replace("$1", "Scheda Collaudo - Importo totale definizione via transattiva")
						.replace("$2", "Numero definite via transattiva o Numero da definite via transattiva"));
			}else{
				//se maggionre campo 18 errore
				if(colBea.getTraImportoDef().compareTo(campo18)>0){
					//err
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_153.replace("$1",
							"Scheda Collaudo - Importo totale definizione via transattiva"));
				}
			}
			if(isEmptyOrZero(colBea.getTraImportoRich())){
				//err
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_136
						.replace("$1", "Scheda Collaudo - Importo totale definizione via transattiva")
						.replace("$2", "Numero definite via transattiva o Numero da definite via transattiva"));
			}else{
				//se maggiore campo 18 errore
				if(colBea.getTraImportoRich().compareTo(campo18)>0){
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_153.replace("$1",
							"Scheda Collaudo - Importo totale richiesto via transattiva"));
				}
			}	
		}		
	}
	

	private String convertTimestampToString(Timestamp data){
		if(data != null){
			String date = PageHelper.getFormattedDBDate(PageHelper.formatTimeStamp(data));
			return date;
		}
		return null;
	}
	/*
	private int calcolaRiserve(List<AccordoBean> accordi){
		int sum = 0;
		for(AccordoBean ab : accordi){
			sum += ab.getNumeroRiserve();
		}return sum;
	}
	private BigDecimal calcolaOneriComplessiviDerivati(List<AccordoBean> accordi){
		BigDecimal sum = new BigDecimal(0);
		for(AccordoBean ab : accordi){
			sum.add(ab.getOneriDerivanti());
		}return sum;
	} 
	*/
}
