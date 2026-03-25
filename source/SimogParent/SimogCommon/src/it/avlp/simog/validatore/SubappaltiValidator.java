package it.avlp.simog.validatore;

import it.avcp.simog.managers.cpv.CPVEUManager;

import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.CpvLotto;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.subappalti.SchedaSubAppalti;
import it.avlp.simog.beans.subappalti.SubappaltatoreBean;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.CATEGORIA;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

public class SubappaltiValidator extends SimogValidator {

	public SubappaltiValidator(Connection connection, Logger logger) {
		super(connection, logger);
		
	}

	@Override
	public boolean valida(Object bean, String section) {
		if(bean != null){
			SchedaSubAppalti ssubBea = (SchedaSubAppalti)bean;
			valida(ssubBea);
			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() == 0;
		}return false;
	}
	private void valida(SchedaSubAppalti ssubBea){
		List<SubappaltiBean> sbs = ssubBea.getSubAppalti();
		int progressivo = 1;
		for(SubappaltiBean sb : sbs){
			valida(ssubBea,sb,progressivo);
			progressivo++;
		}
	}
	/******************************************************************************************************
	 * Validatore per i subappalti relativo alle informazioni di :
	 * <ul>
	 * <li>Codice fiscale ditta
	 * <li>Data autorizzazione
	 * <li>Importo presunto
	 * <li>Categoria
	 * <li>Cpv
	 * <li>Importo effettivo
	 * </ul>
	 * 
	 * @param ssubBea SchedaSubAppalti
	 * @param sb SubappaltiBean
	 */
	private void valida(SchedaSubAppalti ssubBea,SubappaltiBean sb, int progressivo){
		String dataInizioLavoriStipula = ssubBea.getInizioLavori().getDataStipula();
		
		String dataCreazione ="";
		Lotto l = null;
		  try {
	        LottoManager lm = new LottoManager(connection,logger);
	        GaraManager gm = new GaraManager(connection,logger);
	        l = lm.getLotto(ssubBea.getInfoComuni().getIdLotto());
	        
	      //MEV 37328 - 3.04.8.1 FASE 2
    		Gara gara = gm.getGara(l.getId_Gara()) ;
    		
     		boolean isOsservCompetente = SimogProperties.getInstance().isOsservatorioRegionaleCompetente(gara.getID_OSSERVATORIO());			
     		
     		if(isOsservCompetente)
     		{
     			mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_294);
     		}
     		//FINE MEV 37328
     		
	        dataCreazione = gm.getGara(l.getId_Gara()).getData_creazione();
		  } catch (Exception e) {
			  e.printStackTrace();
			  logger.fatal(e.getMessage());
		  }
		  
		//TICKET ALM #19731 - 3.04.5.2
//		if(SimogProperties.getInstance().isDataCreatedAfter30452(dataCreazione) && 
//				Costanti.FLAG_VALORE_NO.equals(ssubBea.getAggiudicazione().getFlagRichSubappalto()))
//			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_278.replace("$1", "Scheda Subappalto"));
		
        // PP 3.02.3.3 per i multi lotto si considera la data maggiore tra le aggiudicazioni
		String dataVerbaleAggiudicazione = ssubBea.getAggiudicazione().getDataVerbaleAggiudicazione();
		
		if(ssubBea.getAggiudicazione().getDatiEconomici() != null){
           dataVerbaleAggiudicazione = ssubBea.getAggiudicazione().getDatiEconomici().getDataVerbaleAggiudicazione();
        }
		
		
		//MEV 36771 3.04.8.1
//		if(isEmpty(sb.getCfDitta())){
//			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Codice fiscale ditta"),progressivo);
//		//MEV 36771 3.04.8.1 Se non arriva il flag lo consideriamo italiano e applichiamo il controllo formale sul cf 
//		}else if(sb.getFlagDittaSubEstera() == null || sb.getFlagDittaSubEstera().equals("") || sb.getFlagDittaSubEstera().equals("S")){
//			sb.setCfDitta(sb.getCfDitta().trim());
//			if(!validaPartitaIva(sb.getCfDitta()) && !validaCodiceFiscale(sb.getCfDitta())){ // PP validazione partita iva e codice fiscale 01.02.2010 Obino 
//				// 07.10.2010 cambiato in warning, come da specifiche 2.9 e 3.0
//				mEccezioni.addValidationWarnProgressivo(Messaggi.SIMOG_VALIDAZIONE_117.replace("$1", "Codice fiscale ditta"),progressivo);
//			}
//		}
		
		if(isEmpty(sb.getCfDitta())){
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Codice fiscale ditta"),progressivo);
		}
		
		if(sb.getFlagDittaSubEstera() == null || sb.getFlagDittaSubEstera().equals("")){
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Flag ditta estera"),progressivo);
		}else if(sb.getFlagDittaSubEstera().equals("N")) {
			sb.setCfDitta(sb.getCfDitta().trim());
			if(!validaPartitaIva(sb.getCfDitta()) && !validaCodiceFiscale(sb.getCfDitta())){ // PP validazione partita iva e codice fiscale 01.02.2010 Obino 
				// 07.10.2010 cambiato in warning, come da specifiche 2.9 e 3.0
				mEccezioni.addValidationWarnProgressivo(Messaggi.SIMOG_VALIDAZIONE_117.replace("$1", "Codice fiscale ditta"),progressivo);
			}
		}
		//MEV 36771 3.04.8.1
		
		//TICKET ALM - 3.04.3 #4198
		//Se sono stati indicati subappaltatori, controllare che il codice fiscale sia corretto
		if(sb.getSubappaltatori()!= null && sb.getSubappaltatori().size() > 0) {
			int index = 1;
			for (int i = 0; i < sb.getSubappaltatori().size(); i++) {
				index++;
			     SubappaltatoreBean subappaltatore = sb.getSubappaltatori().get(i);
				try {
					if(!validaPartitaIva(subappaltatore.getSoggettoPartecipante().getCodiceFiscale()) 
		    	     	&& !validaCodiceFiscale(subappaltatore.getSoggettoPartecipante().getCodiceFiscale()))
		    	         throw new Exception();
				    
				    }
				    catch (Exception e) {
				        mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_117.replace("$1", "Scheda SubAppalto - Codice fiscale subappaltatore "+subappaltatore.getSoggettoPartecipante().getCodiceFiscale()),index);
				    }
			
			    try {
			    	
			    	if(subappaltatore.getSoggettoPartecipante().getCodiceFiscale().equals(sb.getCfDitta()))
			    		throw new Exception();
				    } catch(Exception e) {
				    	mEccezioni.addValidationField("label_DittaMandataria");
				    	 mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_256);
				    }

			}
		}
		//FINE TICKET ALM - 3.04.3 #4198
		
		/* gm nuovo codice 3.0 per il campo codice fiscale ditta aggiudicatrice */
		if(isEmpty(sb.getCfAggiudicatario())){
			//se il cf non è valorizzato e l'aggiudicazione ha più di un aggiudicatario
			//viene lanciato un messaggio di errore
			if(ssubBea.getAggiudicatari()!=null && !ssubBea.getAggiudicatari().isEmpty()){ 
				if(!ssubBea.isUnicoAggiudicatario())   // ssubBea.getAggiudicatari().size()>1
    				mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Codice fiscale ditta aggiudicatrice"),progressivo);
			}
		}
		else{
			//se il cf è valorizzato deve essere tra quelli dell'aggiudicazione
			sb.setCfAggiudicatario(sb.getCfAggiudicatario().trim());
			boolean found = false;
			for (int i = 0; i < ssubBea.getAggiudicatari().size(); i++) {
				AggiudicatarioBean elem = ssubBea.getAggiudicatari().get(i);
				if(elem.getSoggettoPartecipante().getCodiceFiscale().equals(sb.getCfAggiudicatario()))
					found = true;
			}
			if (!found)
				mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Codice fiscale ditta aggiudicatrice"),progressivo);
			
//	NO PP		if(!validaPartitaIva(sb.getCfAggiudicatario()) && !validaCodiceFiscale(sb.getCfAggiudicatario())){ // PP validazione partita iva e codice fiscale 01.02.2010 Obino 
//				// 07.10.2010 cambiato in warning, come da specifiche 2.9 e 3.0
//				mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_117.replace("$1", "Codice fiscale ditta aggiudicatrice"),progressivo);
//			}
		}
				
		if(!isEmpty(sb.getDataAutorizzazione())){
			if(!isDate(sb.getDataAutorizzazione())){
				mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Scheda SubAppalto - Data autorizzazione "),progressivo);	
			}
			else{
				if(isDateBiggerEq(dataInizioLavoriStipula,sb.getDataAutorizzazione())){
					mEccezioni.addValidationWarnProgressivo(Messaggi.SIMOG_VALIDAZIONE_155.replace("$1", "Scheda SubAppalto - Data autorizzazione ").replace("$2", "stipula del contratto"),progressivo);	
				}
				/* gm nuovo codice 3.0 per il campo data autorizzazione */
				if(ssubBea.getAggiudicazione()!=null){
					//se la data autorizzazione è antecedente alla data verbale aggiudicazione
					//viene lanciato un messaggio di warning
				    if(dataVerbaleAggiudicazione!=null){					
        				if(isDateLower(sb.getDataAutorizzazione(),dataVerbaleAggiudicazione))
		        			mEccezioni.addValidationWarnProgressivo(Messaggi.SIMOG_VALIDAZIONE_155.replace("$1", "Scheda SubAppalto - Data autorizzazione ").replace("$2", "aggiudicazione"),progressivo);					
				    }
				}
			}
		}
		//sb.getOggettoSubappalto(); libero
		if(isEmptyOrZero(sb.getImportoPresunto())){
			//ERR CAMPo obligatorio
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Scheda SubAppalto - Importo presunto "),progressivo);	
		}
		if(isEmpty(sb.getIdCategoria()) || !this.validaTipologicaCategorie(sb.getDataInizioRecord(), sb.getIdCategoria())){
			//selezionare una delle opzioni
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda SubAppalto - Categoria "),progressivo);	
		}
		if(isEmpty(sb.getIdCpv())){
			//selezionare una delle opzioni
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Scheda SubAppalto - Cpv "),progressivo);	
		}
		else{
           CPVEUManager cManager = new CPVEUManager ( connection, logger );
           try{
               if ( !cManager.checkCPV(sb.getIdCpv(), PageHelper.getCurrentDate()) ) {
                   logger.debug( "Inserito valore non valido [" + sb.getIdCpv() + "] per CPV" );
                   mEccezioni.addValidationField("label_ValoreCPV");
                   mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_019);
               }
               
               
              
               if(SimogProperties.getInstance().isDataCreatedAfter3044(dataCreazione)) {
	               if(l.getId_CPV()!=null && !l.getId_CPV().equals(sb.getIdCpv()) && !checkCpvSecondarie(sb.getIdCpv(), l.getElencoCpvSecondarie())) {
	            	   mEccezioni.addValidationField("label_ValoreCPV");
	            	   mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_244);
	               }
               }
               //FINE TICKET ALM - 3.04.4
               
// tolto controllo sul terzo livello, errore mio!
//               else{
//                   boolean ok = !"0".equals(sb.getIdCpv().substring(2,3)) && !"0".equals(sb.getIdCpv().substring(3,4));
//                   
//                   ok = ok || cManager.getBranch(sb.getIdCpv().substring(0,2), 
//                         sb.getIdCpv().substring(2,3),
//                         sb.getIdCpv().substring(3,4),
//                         sb.getIdCpv().substring(4,5),
//                         sb.getIdCpv().substring(5,8)).size()==0;
//                   
//                   if (!ok){
//                       logger.debug( "Inserito valore non valido [" + sb.getIdCpv() + "] per CPV" );
//                       mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_019);
//                   }
//               }
           }catch(SQLException sqle){
               logger.fatal(sqle.getMessage());
               mEccezioni.addValidationField("label_ValoreCPV");               
               mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_019);
           }catch(Exception e){
               logger.fatal(e.getMessage());
               mEccezioni.addValidationField("label_ValoreCPV");               
               mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_019);
           }
		   
		}
		
		// PP 02.07.2009 corretto controllo, prendeva anche il campo vuoto
		if(!isEmpty(sb.getImportoEffettivo())){
			if(!isNumberDecimal(sb.getImportoEffettivo().toString()))
			//errore
			mEccezioni.addValidationErrProgressivo(Messaggi.SIMOG_VALIDAZIONE_139.replace("$1", "Scheda SubAppalto - Importo effettivo "),progressivo);	
		}
		//else if(isEmptyOrZero(sb.getImportoEffettivo())){
			//warn
			//mEccezioni.addValidationWarnProgressivo(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Scheda SubAppalto - Importo effettivo "),progressivo);	
		//}
		
	}

	/**
	 * @param data Object Timestamp or String[yyytmmdd]
	 * @param id Object Long or String[rapresenting an id]
	 * @return boolean
	 */
	private boolean validaTipologicaCategorie(Object data,Object id){
		if(id == null){ return false; }
		return super.validaTipologica(CATEGORIA.TABLE_NAME, CATEGORIA.ID_CATEGORIA, CATEGORIA.DESCRIZIONE, CATEGORIA.DATA_FINE_VALIDITA, data, id);
	}

	/**
	 * Verifica se la CPV indicata nel subappalto sia presente tra le cpv secondarie del lotto
	 * @param idCpv
	 * @param elencoCpvSecondarie
	 * @return
	 */
	private boolean checkCpvSecondarie(String idCpv, List<CpvLotto> elencoCpvSecondarie) {
		boolean res=false;
		for(CpvLotto cpv : elencoCpvSecondarie) {
			if(cpv.getIdCpv().equals(idCpv))
				res=true;
		}
		return res;
	}
}
