package it.avlp.simog.validatore;

import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.avanzamento.SchedaAvanzamento;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import it.avlp.simog.util.SimogProperties;

public class AvanzamentiValidator extends SimogValidator  {
	
	private final int MAX_DENOM_AVANZ = 250;
	private static final int MAX_GIORNI_SCOSTAMENTO = 99;

	public AvanzamentiValidator(Connection connection, Logger logger) {
		super(connection, logger);
	
	}
	@Override
	public boolean valida(Object bean, String section) {
		if(bean != null){
			SchedaAvanzamento ava = (SchedaAvanzamento)bean;
			valida(ava);
			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
		}else{
			return false;
		}	
	}
	private void valida(SchedaAvanzamento sava){
		List<AvanzamentoBean> list = sava.getAvanzamenti();
		int progressivo = 1;
		
		//MEV 37328 - 3.04.8.1 FASE 2
		GaraManager gm = new GaraManager(connection,logger);
		LottoManager lt = new LottoManager(connection,logger);
		Gara gara = new Gara();
		try {
			Lotto lotto = lt.getLotto(sava.getInfoComuni().getIdLotto());			
			gara = gm.getGara(lotto.getId_Gara());		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
 		boolean isOsservCompetente = SimogProperties.getInstance().isOsservatorioRegionaleCompetente(gara.getID_OSSERVATORIO());			
 		
 		if(isOsservCompetente)
 		{
 			mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_294);
 		}
 		//FINE MEV 37328
		 		
		for(AvanzamentoBean ava : list){
			valida(ava,sava.getAggiudicazione(),sava.getInfoComuni(),sava.getVarianti(),progressivo);
			progressivo++;
		}
	}
	/***********************************************************************************************************************
	 * Viene effettuata la validazione sui seguenti campi 
	 * <ul>
	 * <li>Modalita' di pagamento
	 * <li>Importo Anticipazione
	 * <li>Data Anticipazione
	 * <li>Data Stato di Avanzamento
	 * <li>Importo Sal
	 * <li>Data di emissione del certificato di pagamento
	 * <li>Importo Certificato
	 * <li>scostamento registrato in numero di giorni
	 * <li>numero di giorni di proroga concessi
	 * </ul>
	 * @param ava AvanzamentoBean 
	 * @param aggBea AggiudicazioneBean
	 * @param infBea InfocomuniBean
	 */
	private void valida(AvanzamentoBean ava, AggiudicazioneBean aggBea,InfoComuniBean infBea, List<VarianteBean> varianti, int progressivo){
		
		//commentato per MEV 43789 3.04.10.1
		//TICKET ALM 13444 (punto 2)
//		LottoManager lm = new LottoManager(connection,logger);
//		float importoLotto = Costanti.IMPORTO_FUORI_SCALA;
//		try {
//			importoLotto = lm.getLotto(infBea.getIdLotto()).getImporto_Lotto().floatValue();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		
//		if(importoLotto != Costanti.IMPORTO_FUORI_SCALA 
//                && importoLotto < Costanti.IMPORTO_LOTTO_500000 && ava.getIdAvanzamento()<=0){
//			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_269.replace("$1", "avanzamento"),progressivo);
//          }
		//FINE TICKET ALM 13444 (punto 2)
		//fine commentato per MEV 43789 3.04.10.1

		//distinzione fatta per via della necessita di alcuni dati
		//che per il web risiedono non si sa bene dove (probabilmente sul db)
		//nel caso invece dei services potrebero gia essere disponibili
		if(isEmpty(ava.getFlagPagamento())){
			mEccezioni.addValidationField("label_ModPagamento");
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Avanzamenti - Modalita di pagamento "),progressivo);
		}
		String dataVerbaleAggiudicazione = aggBea.getDataVerbaleAggiudicazione();
        String dataVerbaleAggiudicazioneMin = aggBea.getDataVerbaleAggiudicazione();

		// PP 3.02.3.3 per i multi lotto si considera la data maggiore tra le aggiudicazioni
        if(SimogFlags.is30233_RFWEBSC04Active() && aggBea.getDatiEconomici() != null){
           dataVerbaleAggiudicazione = aggBea.getDatiEconomici().getDataVerbaleAggiudicazione();
           dataVerbaleAggiudicazioneMin = aggBea.getDatiEconomici().getDataVerbaleAggiudicazioneMin();
        }

		//String tipoContratto = infBea.getTipoContratto();
        //S11.09
		double importoComplessivo = getImportoComplessivoAppalto(aggBea.getImportoLavori(),aggBea.getImportoServizi(),aggBea.getImportoForniture(),
				aggBea.getImportoAttuazioneSicurezza(),aggBea.getImportoNonAssog(),aggBea.getImportoProgettazione());	

		// PP 3.02.3.3 per i multi lotto si considera la somma degli importi
		if(SimogFlags.is30233_RFWEBSC04Active() && aggBea.getDatiEconomici() != null){
           importoComplessivo = aggBea.getDatiEconomici().getImportoComplessivoApp().doubleValue();//S12.09
        }
		
		BigDecimal importo_complessivo_variante = getImportoComplessivoAppaltoVariante(getUltimaVariante(varianti));
		/*
		 * supponendo che sia l'utente ad immettere il numero (ci interessa solo quando e 1 ovvero il primo avanzamento)
		 */
		if(ava.getNumeroAvanzamento() == 1){
			
			//2.10 aggiunto controllo 11.1.1.4
			if(!isEmptyOrZero(ava.getImportoAnticipazione())){
				try{
					//se l'importo è > di tutti gli importi complessivi
				    if(ava.getImportoAnticipazione().doubleValue() > importoComplessivo){
				    	if(getUltimaVariante(varianti)!=null){
				    		if(ava.getImportoAnticipazione().compareTo(importo_complessivo_variante)>0)
				    			throw new Exception();
				    	}
				    	else
				    		throw new Exception();
				    }
				}
				catch (Exception e){
				    mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_132.replace("$1", "Scheda Avanzamenti - importo anticipazione"),progressivo);
				}
			}
			//2.10 fine controllo 11.1.1.4
		}
		//se il campo è obbligatorio
		else if(!isEmptyOrZero(ava.getImportoAnticipazione()))
		    mEccezioni.addValidationWarnProgressivo(Messaggi.SIMOG_VALIDAZIONE_176.replace("$1", "Scheda Avanzamenti - importo anticipazione"),progressivo);
		
		//2.10 aggiunto controllo 11.1.1.5
		if(!isEmptyOrZero(ava.getImportoAnticipazione()) && isEmpty(ava.getDataAnticipazione()) && isLavori(infBea.getTipoContratto()))
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1", "Scheda Avanzamenti - Data anticipazione "),progressivo);
		if(isEmptyOrZero(ava.getImportoAnticipazione()) && !isEmpty(ava.getDataAnticipazione()))
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_135.replace("$1", "Scheda Avanzamenti - Data anticipazione "),progressivo);
		if(!isEmpty(ava.getDataAnticipazione())){
			if(!isDate(ava.getDataAnticipazione()))
			    mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Avanzamenti - Data anticipazione "),progressivo);
		    else if(!isEmpty(dataVerbaleAggiudicazioneMin)&&isDate(dataVerbaleAggiudicazioneMin)){
		        if(isDateLower(ava.getDataAnticipazione(), dataVerbaleAggiudicazioneMin ))
				    mEccezioni.addValidationWarnProgressivo(Messaggi.SIMOG_VALIDAZIONE_119.replace("$1", "Scheda Avanzamenti - Data anticipazione "),progressivo);
		    }
		}
		//2.10 fine controllo 11.1.1.5
		
		//2.10 aggiunto controllo 11.1.1.6
		if(!isEmpty(ava.getDataRaggiungimento())){
		    if(!isDate(ava.getDataRaggiungimento())) {
		    	mEccezioni.addValidationField("label_DataAvanz");
			    mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Avanzamenti - Data stato di avanzamento "),progressivo);
		    } else if(!isEmpty(dataVerbaleAggiudicazioneMin)&&isDate(dataVerbaleAggiudicazioneMin)){
		        if(isDateLower(ava.getDataRaggiungimento(), dataVerbaleAggiudicazioneMin)) {
		        	mEccezioni.addValidationField("label_DataAvanz");
				    mEccezioni.addValidationWarnProgressivo(Messaggi.SIMOG_VALIDAZIONE_119.replace("$1", "Scheda Avanzamenti - Data stato di avanzamento "),progressivo);			
		        }
		    }
		}
		else {
			mEccezioni.addValidationField("label_DataAvanz");
		    mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1", "Scheda Avanzamenti - Data stato di avanzamento"),progressivo);		
		//2.10 fine controllo 11.1.1.6
		}
	//< campo 37
		// PP 04.03.2009 da specifiche può essere zero !
		// if(isEmptyOrZero(ava.getImportoSal())){
		
		//2.10 aggiunto controllo 11.1.1.7
		if(isEmpty(ava.getImportoSal())){
			mEccezioni.addValidationField("label_ImportoAvanz");
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Scheda Avanzamenti - importo stato avanzamento"),progressivo);
		} 
		else{
			if (!isNumberDecimal(ava.getImportoSal().toString())) {
				  mEccezioni.addValidationField("label_ImportoAvanz");
				    mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_139.replace("$1","Scheda Avanzamenti - importo stato avanzamento"),progressivo);
			} else{
			    try{
				     //Ticket ALM #649-2340
					if(getUltimaVariante(varianti)!=null){
						if(ava.getImportoSal().doubleValue() > importo_complessivo_variante.doubleValue())
							throw new Exception();
					} else {
						if (ava.getImportoSal().doubleValue() > importoComplessivo)
							throw new Exception();
					} 
			    	//Fine Ticket ALM #649-2340
			    }
			    catch (Exception e){
			    	mEccezioni.addValidationField("label_ImportoAvanz");
	    		    mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_132.replace("$1", "Scheda Avanzamenti - importo stato avanzamento"),progressivo);
			    }
			}
		}
		//2.10 fine controllo 11.1.1.7

		//2.10 aggiunto controllo 11.1.1.8
		if(!isEmpty(ava.getDataCertificato())){
			if(!isDate(ava.getDataCertificato())){
				mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Avanzamenti - Data di emissione del certificato di pagamento "),progressivo);
			}
			else if(isDateLower(ava.getDataCertificato(), dataVerbaleAggiudicazioneMin )){
				mEccezioni.addValidationWarnProgressivo(Messaggi.SIMOG_VALIDAZIONE_119.replace("$1", "Scheda Avanzamenti - Data di emissione del certificato di pagamento "),progressivo);
			}
		}
		//2.10 fine controllo 11.1.1.8
		
		//2.10 aggiunto controllo 11.1.1.9
		if(!isEmpty(ava.getImportoCertificato())){
			if(!isNumberDecimal(ava.getImportoCertificato().toString()))
			    mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_139.replace("$1","Scheda Avanzamenti - importo certificato"),progressivo);
			else if(ava.getImportoCertificato().doubleValue() > importoComplessivo){
				if(getUltimaVariante(varianti)!=null){
					//MAC 3.04.6
		    		if(ava.getImportoCertificato().doubleValue() > importo_complessivo_variante.doubleValue()){
		    			mEccezioni.addValidationField("label_ImportoCertificato");
		    			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_132.replace("$1", "Scheda Avanzamenti - importo certificato"));
					}
				}
				else {
					mEccezioni.addValidationField("label_ImportoCertificato");
	    			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_132.replace("$1", "Scheda Avanzamenti - importo certificato"));
			}
		}		
		}		
		//2.10 fine controllo 11.1.1.9
		
		/*** PP eliminato a seguito richiesta del 10.11.08 else if(ava.getImportoCertificato().compareTo(ava.getImportoSal()) != 0){
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_134.replace("$1", "Scheda Avanzamenti - importo certificato"));
		} ***/			
		// 2 valori ritardo , anticipo (su lavori anche puntuale)
		
		//controllo 11.1.1.10
		if(isLavori(infBea.getTipoContratto()) && isEmpty(ava.getFlagRitardo())){
		     	mEccezioni.addValidationField("label_FlagRitardo");				
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Avanzamenti - avanzamento raggiunto, rispetto al crono programma"));
		//fine controllo 11.1.1.10
	     }
		//2.10 aggiunto controllo 11.1.1.11
			 
		if(isEmptyOrZero(ava.getNumeroGiorniScost())){
			if(isLavori(infBea.getTipoContratto())){
			    if(AvanzamentoBean.AVANZ_ANTICIPO.equals(ava.getFlagRitardo())||AvanzamentoBean.AVANZ_RITARDO.equals(ava.getFlagRitardo())) {
			    	mEccezioni.addValidationField("label_ScostNumGiorni");	
				    mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Scheda Avanzamenti - scostamento registrato in numero di giorni"),progressivo);
			    }
		    }
		}
			
		if(!isEmptyOrZero(ava.getNumeroGiorniScost())){
			//controllo fittizio, è sempre true
		    if(!isNumber(String.valueOf(ava.getNumeroGiorniScost()))) {
		    	mEccezioni.addValidationField("label_ScostNumGiorni");	
			    mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_139.replace("$1", "Scheda Avanzamenti - scostamento registrato in numero di giorni"),progressivo);
		    }else if(ava.getNumeroGiorniScost() > MAX_GIORNI_SCOSTAMENTO) {
		    	mEccezioni.addValidationField("label_ScostNumGiorni");	
			    mEccezioni.addValidationWarnProgressivo(Messaggi.SIMOG_VALIDAZIONE_133.replace("$1", "Scheda Avanzamenti - scostamento registrato in numero di giorni"),progressivo);
		    }
      		if(SimogFlags.is3028_RFWEBSC02Active()){
      		   if(ava.getNumeroGiorniScost() > MAX_GIORNI) {
      			   mEccezioni.addValidationField("label_ScostNumGiorni");	
      		      mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_132.replace("$1", "Scheda Avanzamenti - scostamento registrato in numero di giorni"),progressivo);
      		      }
      		   }
		}
		//2.10 fine controllo 11.1.1.11
	
// PP modifica richiesta da Obino il 25.03.2009
//		if(isEmptyOrZero(ava.getNumeroGiorniProroga())&& ava.AVANZ_RITARDO.equals(ava.getFlagRitardo())){
//			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Scheda Avanzamenti - numero di giorni di proroga concessi "));
//		}else
		
		//2.10 aggiunto controllo 11.1.1.12
		if (!isEmpty(ava.getNumeroGiorniProroga())){
			//controllo fittizio, è sempre true
		    if(!isNumber(String.valueOf(ava.getNumeroGiorniProroga()))) {
		    	mEccezioni.addValidationField("label_ProrogaGiorni");	
			    mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_139.replace("$1", "Scheda Avanzamenti - numero di giorni di proroga concessi"),progressivo);
		    } else if(ava.getNumeroGiorniProroga() > MAX_GIORNI_SCOSTAMENTO) {
		    	mEccezioni.addValidationField("label_ProrogaGiorni");	
				mEccezioni.addValidationWarnProgressivo(Messaggi.SIMOG_VALIDAZIONE_133.replace("$1", "Scheda Avanzamenti - numero di giorni di proroga concessi "),progressivo);		
		    }

               if(ava.getNumeroGiorniProroga() > MAX_GIORNI) {
            	   mEccezioni.addValidationField("label_ProrogaGiorni");	
                  mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_132.replace("$1", "Scheda Avanzamenti - numero di giorni di proroga concessi"),progressivo);
               }
		}
		//2.10 fine controllo 11.1.1.12		
	
		//validazione campo Denominazione Stato Avanzamento (Max Length)
		if(!isEmpty(ava.getDenomStatoAvanz()) &&  ava.getDenomStatoAvanz().length() > MAX_DENOM_AVANZ){
			mEccezioni.addValidationField("label_DenomStatoAvanz");		
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_184.replace("$1","Scheda Avanzamenti - Denominazione Stato Avanzamento").replace("$2",String.valueOf(MAX_DENOM_AVANZ)),progressivo);
		}		
		
	    //avanzamenti ammessi solo se importo lotto > 500.000
//		LottoManager lm = new LottoManager(connection, logger);
//		Lotto lotto = new Lotto();
//         try {
//            lotto = lm.getLotto(infBea.getIdLotto());
//         } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//         } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//         }
//		
//        if( lotto.getImporto_Lotto().floatValue() <= Costanti.IMPORTO_LOTTO_500000){
//            mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_MASSLOADER_207.replace("$1","Appalto con importo a base d'asta minore o uguale a 500.000 euro"), progressivo);
//        }       

	}
	
	private VarianteBean getUltimaVariante(List<VarianteBean> varianti){
		VarianteBean result = null;
		if(!isEmpty(varianti)){
			for(VarianteBean variante : varianti){
				//cerco la variante con data verbale approvazione più recente
				if(result==null
				         ||isDateBigger(variante.getDataVerbaleApprovazione(),result.getDataVerbaleApprovazione())
				         // PP 14/10/2013 migliorato controllo per varianti con stessa data approvazione
				         ||(isDateBiggerEq(variante.getDataVerbaleApprovazione(),result.getDataVerbaleApprovazione())
				            && getImportoComplessivoAppaltoVariante(variante).floatValue() > getImportoComplessivoAppaltoVariante(result).floatValue()
				            )
				)
					result = variante;
			}
		}
		return result;
	}
	
	private BigDecimal getImportoComplessivoAppaltoVariante(VarianteBean variante){
		BigDecimal temp = new BigDecimal(0);
		if (variante!=null){
		    if(variante.getImpRidetLavori() != null)
			    temp = temp.add(variante.getImpRidetLavori());
		    if(variante.getImpRidetServizi() != null)
		    	temp = temp.add(variante.getImpRidetServizi());
		    if(variante.getImpRidetFornit() != null)
		    	temp = temp.add(variante.getImpRidetFornit());
		    if(variante.getImpSicurezza() != null)
		    	temp = temp.add(variante.getImpSicurezza());
		    if(variante.getImpProgettazione() != null)
		    	temp = temp.add(variante.getImpProgettazione());
		    if(variante.getUlterioriSomme()!= null)
		    	temp = temp.add(variante.getUlterioriSomme());
		}
		return temp;
	}
}
