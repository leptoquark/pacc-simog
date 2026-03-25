package it.avlp.simog.validatore;

import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.sospensioni.SchedaSospensione;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.db.generated.MOTIVI_SOSPENSIONE;
import it.avlp.simog.errormessage.Messaggi;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import java.sql.SQLException;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.SimogProperties;

public class SospensioniValidator extends SimogValidator {

	public SospensioniValidator(Connection connection, Logger logger) {
		super(connection, logger);
		
	}

	@Override
	public boolean valida(Object bean, String section) {
		if(bean != null){
			SchedaSospensione ssosBea = (SchedaSospensione)bean;
			valida(ssosBea);
			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
		}
		return false;
	}
	private void valida(SchedaSospensione ssosBea){
		List<SospensioniBean> sospensioni = ssosBea.getSospensioni();
		int progressivo = 1;
		
		//MEV 37328 - 3.04.8.1 FASE 2
		GaraManager gm = new GaraManager(connection,logger);
		LottoManager lt = new LottoManager(connection,logger);
		Gara gara = new Gara();
		try {
			Lotto lotto = lt.getLotto(ssosBea.getInfoComuni().getIdLotto());			
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
		 		
		for(SospensioniBean sb : sospensioni){
			valida(ssosBea,sb,progressivo);
			progressivo++;
		}
	}
	/******************************************************************************************
	 * Validatore per la scheda sospensioni
	 * 
	 * @param ssosBea SchedaSospensioni
	 * @param sospensione SospensioniBean
	 */
	private void valida(SchedaSospensione ssosBea,SospensioniBean sospensione,int progressivo){
		//bla bla
		InizioLavoriBean ib = ssosBea.getInizioLavori();
		String dataInizioLavoriStipula = null;
		if(ib != null){ 
			// PP modificato a seguito di richiesta del 10.11.08 dataInizioLavoriStipula = ssosBea.getInizioLavori().getDataStipula();
			dataInizioLavoriStipula = ssosBea.getInizioLavori().getDataVerbaleInizio();
		}
		
		//2.10 aggiunto controllo 14.1.1.2
		if(!isEmpty(sospensione.getDataVerbSosp())){
			//caso bean di confronto non nullo
			if(ib != null){
				if(!isDate(sospensione.getDataVerbSosp())){
					mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Sospensione - Data verbale sospensione "),progressivo);	
				}
				else{
					if(!isEmpty(dataInizioLavoriStipula)&&isDate(dataInizioLavoriStipula)){
	    				if(isDateBigger(dataInizioLavoriStipula,sospensione.getDataVerbSosp()))
						//se minore campo nove iniziolavori
						mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_155.replace("$1", "Scheda Sospensione - Data verbale sospensione ").replace("$2", "effettivo inizio"),progressivo);						
					}
					else{
						if(!isEmpty(sospensione.getDataVerbRipr())){
							if(!isDate(sospensione.getDataVerbRipr())){
								mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Sospensione - Data verbale ripresa "),progressivo);					
							}else{
								if(isDateBiggerEq(sospensione.getDataVerbSosp(),sospensione.getDataVerbRipr())){
									//minore di campo 2 errore
									mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_156.replace("$1", "Scheda Sospensione - Data verbale ripresa  "),progressivo);	
								}
							}
						}
					}
				}
				// nel caso del bean di confronto nullo
			}else{
				if(!isEmpty(sospensione.getDataVerbRipr())){
					if(!isDate(sospensione.getDataVerbRipr())){
						mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda Sospensione - Data verbale ripresa "),progressivo);					
					}else{
						if(isDateBiggerEq(sospensione.getDataVerbSosp(),sospensione.getDataVerbRipr())){
							//minore di campo 2 errore
							mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_156.replace("$1", "Scheda Sospensione - Data verbale ripresa  "),progressivo);	
						}
					}
				}
			}
		}
		else{
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Scheda Sospensione - Data sospensione "),progressivo);
		}
		//2.10 fine controllo 14.1.1.2
		
		if(sospensione.getIdMotivoSosp()<0 || !this.validaTipologicaMotiviSospensione(sospensione.getDataInizioSosp(), sospensione.getIdMotivoSosp())){
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Sospensione - Motivo sospensione "),progressivo);
		}
/* DISATTIVATO 15-01-09
  		if(isEmpty(sospensione.getFlagSuperoTemp())){
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Sospensione - Superamento del quarto del tempo "));
		}
*/
		if(isEmpty(sospensione.getFlagRiserve())){
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Sospensione - Iscrizione di riserve "),progressivo);
		}
		if(isEmpty(sospensione.getFlagVerbale())){
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda Sospensione - Verbali non sottoscritti "),progressivo);
		}		
	}
	/**
	 * @param data Object Timestamp or String[yyytmmdd]
	 * @param id Object Long or String[rapresenting an id]
	 * @return boolean
	 */
	private boolean validaTipologicaMotiviSospensione(Object data,Object id){
		if(id == null){ return false; }
		return super.validaTipologica(MOTIVI_SOSPENSIONE.TABLE_NAME, MOTIVI_SOSPENSIONE.ID_MOTIVO_SOSP, MOTIVI_SOSPENSIONE.DESCRIZIONE, MOTIVI_SOSPENSIONE.DATA_FINE_VALIDITA,data,id);		
	}

}
