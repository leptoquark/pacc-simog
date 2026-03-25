package it.avlp.simog.validatore;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.accordi.SchedaAccordo;
import it.avlp.simog.errormessage.Messaggi;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.util.SimogProperties;
import java.sql.SQLException;

public class AccordiBonariValidator extends SimogValidator {

	public AccordiBonariValidator(Connection connection, Logger logger) {
		super(connection, logger);
		
	}

	@Override
	public boolean valida(Object bean, String section) {
		if(bean != null){
			SchedaAccordo saccBea = (SchedaAccordo)bean;
			valida(saccBea);
			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
		}
		return true;
	}
	private void valida(SchedaAccordo saccBea){
		List<AccordoBean> lab = saccBea.getAccordi();
		int progressivo = 1;
		
		//MEV 37328 - 3.04.8.1 FASE 2
		GaraManager gm = new GaraManager(connection,logger);
		LottoManager lt = new LottoManager(connection,logger);
		Gara gara = new Gara();
		try {
			Lotto lotto = lt.getLotto(saccBea.getInfoComuni().getIdLotto());			
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
		 		
		for(AccordoBean ab : lab){
			valida(saccBea,ab,progressivo);
			progressivo++;
		}
	}
	
	/******************************************************************************************************
	 * Valida i campi della Scheda Accordo
	 * <ul>
	 * <li>Data Accordo
	 * <li>Oneri Derivanti
	 * <li>Numero riserve
	 * </ul>
	 * @param saccBea Scheda Accordo
	 * @param ab AccordoBean
	 */
	private void valida(SchedaAccordo saccBea, AccordoBean ab, int progressivo){
		String dataInizioLavoriStipula = saccBea.getInizioLavori().getDataStipula();
		if(isEmpty(ab.getDataAccordo())){
			mEccezioni.addValidationField("label_DataAccordo");
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Scheda accordo - Data accordo "),progressivo);
		}else{
			if(!isDate(ab.getDataAccordo())){
				//err
				mEccezioni.addValidationField("label_DataAccordo");
				mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda accordo - Data accordo "),progressivo);
			}else{
				if (dataInizioLavoriStipula!=null && isDate(dataInizioLavoriStipula)){
				    if(isDateBigger(dataInizioLavoriStipula, ab.getDataAccordo())) {
				    	mEccezioni.addValidationField("label_DataAccordo");
					    mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_155.replace("$1", "Scheda accordo - Data accordo ").replace("$2", "stipula del contratto"),progressivo);
				    }
				}
			}
		}
		if(!isEmpty(ab.getOneriDerivanti())){
			if(ab.getOneriDerivanti().compareTo(new BigDecimal(0))== 0){
				//warn
				mEccezioni.addValidationWarnProgressivo(Messaggi.SIMOG_VALIDAZIONE_160.replace("$1", "Scheda accordo - Oneri derivanti "),progressivo);
			}
		}
		if(!isEmpty(ab.getNumeroRiserve())){
			if(!isNumber(""+ab.getNumeroRiserve())){
				//err inserire solo numneri
				mEccezioni.addValidationField("label_NumRiserve");
				mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_139.replace("$1", "Scheda accordo - Numero riserve "),progressivo);
			}
		}	
	}

}
