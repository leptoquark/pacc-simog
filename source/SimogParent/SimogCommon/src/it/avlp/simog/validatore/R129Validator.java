package it.avlp.simog.validatore;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.beans.r129.SchedaR129;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.util.SimogProperties;
import java.sql.Connection;
import java.util.List;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class R129Validator extends SimogValidator{

	public R129Validator(Connection connection, Logger logger) {
		super(connection, logger);
		
	}

	@Override
	public boolean valida(Object bean, String section) {
		if(bean != null){
			SchedaR129 sr129 = (SchedaR129)bean;
			valida(sr129);
			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
		}return false;
	}
	private void valida(SchedaR129 sr129){
		List<R129Bean> lr129 = sr129.getR129s();
		int progressivo = 1;
		for(R129Bean r129 : lr129){
			valida(sr129,r129, progressivo);
			progressivo++;
		}
	}
	/*******************************************************************************************************
	 * Validatore per la Scheda R129
	 * 
	 * @param sr129 Schedar129
	 * @param r129 R129Bean
	 */
	private void valida(SchedaR129 sr129,R129Bean r129,int progressivo){
		
		//MEV 37328 - 3.04.8 FASE 2
		GaraManager gm = new GaraManager(connection,logger);
		LottoManager lt = new LottoManager(connection,logger);
		Gara gara = new Gara();
		try {
			Lotto lotto = lt.getLotto(sr129.getInfoComuni().getIdLotto());			
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
		 		
		/** il bean dal quale recuperiamo la data potrebbe essere nullo
		InizioLavoriBean ilb = sr129.getInizioLavori();
		String dataInizioLavoriStipula = null;
		if(ilb != null){dataInizioLavoriStipula = ilb.getDataStipula();}
		/** end */
		if(isEmpty(r129.getDataTermine())){
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Scheda Ritardi - Data termine "),progressivo);													
		}else{
			if(!isDate(r129.getDataTermine())){
				mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Ritardi - Data termine "),progressivo);										
			}
		}
		if(isEmpty(r129.getDataIstRecesso())){
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Scheda Ritardi - Data istanza recesso "),progressivo);										
		}else{
			if(!isDate(r129.getDataIstRecesso())){
				mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Ritardi - Data istanza recesso "),progressivo);
			}
		}
		if(!isEmpty(r129.getDataConsegna())){
			if(!isDate(r129.getDataConsegna())){
				mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Ritardi - Data della consegna dei lavori"),progressivo);										
			}
			else if(isDateYearBigger(r129.getDataConsegna())){
				mEccezioni.addValidationWarnProgressivo(Messaggi.SIMOG_VALIDAZIONE_127.replace("$1", "Scheda Ritardi - Data della consegna dei lavori"),progressivo);
			}
		}		
		if(isEmpty(r129.getTipoComunicazione())){
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Scheda Ritardi - Tipo comunicazione "),progressivo);													
		}
		if(!isEmpty(r129.getDurataSospensione())){
			if(!isNumber(""+r129.getDurataSospensione())){
				mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_139.replace("$1", "Scheda Ritardi - Durata sospensione "),progressivo);										
			}
		}		
/*		Disabilitato secondo specifiche OSIT 2.9 */
		// PP riabilitato
  		if(isEmpty(r129.getFlagAccolta())){
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Ritardi - Flag accolta "),progressivo);										
		}
  		
		if(isEmpty(r129.getFlagTardiva())){
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Ritardi - Flag tardiva "),progressivo);										
		}
		if(isEmpty(r129.getFlagRipresa())){
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Ritardi - Flag ripresa "),progressivo);										
		}
		if(isEmpty(r129.getFlagRiserva())){
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Ritardi - Flag riserva "),progressivo);										
		}
	}
	
}
