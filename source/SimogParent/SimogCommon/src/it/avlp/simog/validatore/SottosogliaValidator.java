package it.avlp.simog.validatore;

import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avlp.simog.beans.EsitoEnum;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBeanComparator;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.CondizioneAggBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.beans.cup.CupLottoAgg;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.CATEGORIA_SA;
import it.avlp.simog.db.generated.CONDIZIONI;
import it.avlp.simog.db.generated.TIPOLOGIA_SA;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class SottosogliaValidator extends SimogValidator {

	public SottosogliaValidator(Connection connection, Logger logger) {
		super(connection, logger);
		// TODO Auto-generated constructor stub
	}

	public void clearExceptions(){
		this.mEccezioni.clear();
	}
	@Override
	public boolean valida(Object bean, String section) {
		try{
			Scheda_A saBean = (Scheda_A) bean;
			if(IdentificativoSchede.TAB_INFO_COMUNI.equalsIgnoreCase(section))
				validaDatiComuni(saBean.getInfoComuni(),saBean.getInfoGara().getImportoLotto().floatValue());
			else {
				
				if(section == null) 
					validaDatiComuni(saBean.getInfoComuni(),saBean.getInfoGara().getImportoLotto().floatValue());
				validaAggiudicazione(saBean.getAggiudicazione(),saBean.getCondizioni(), saBean.getInfoComuni(),
				      saBean.getInfoGara().getID_MODO_REAL(), saBean.getInfoGara().getIdOsservatorio());
				validaAggiudicatari(saBean.getAggiudicatari(),saBean.getAggiudicazione().getDataInizioAggiudicazione());
				validaResponsabili(saBean.getResponsabili(),saBean.getInfoComuni()); 

            if( SimogFlags.is3031_RFWEBGL02Active() 
                  && !SimogProperties.getInstance().isCUPLotto(saBean.getInfoGara().getDataCreazioneGara())
                  && SimogProperties.getInstance().isCUPAttivo()){
               Lotto lotto = new Lotto();
               lotto.setElencoCup(saBean.getElencoCup());
               lotto.setFLAG_CUP(saBean.getFlagCUP());
               lotto.setTIPO_CONTRATTO_LOTTO(saBean.getInfoComuni().getTipoContratto());
               lotto.setElencoTipoAppaltoLottoL(saBean.getTipoLavoro());
               validaFlageCodiciCUP(lotto);//Ticket ALM #2432
            }
            
			}
			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() == 0;
		}catch (Exception e) {
			logger.fatal(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	/****************************************************************************************************
	 * Validatore per i responsabili
	 * 
	 * @param responsabili List&lt;ResponsabileBean&gt;
	 * @param infoComuni : InfoComuniBean
	 */
	private void validaResponsabili(List<ResponsabileBean> responsabili,
			InfoComuniBean infoComuni) {
		int i = 1;
		if(responsabili.size()==0){
			mEccezioni.addValidationWarnElemento(Messaggi.SIMOG_VALIDAZIONE_168, i);		
		}
		for(ResponsabileBean resp: responsabili){
		   
           if(resp.getSoggettoPartecipante() != null && resp.getSoggettoResponsabile() == null)
              mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_209.replace("$1", "Incaricato - Flag persona giuridica 'S' non ammesso").replace("$2", "'N'"),i);

			try{
				if(isEmptyOrZero(resp.getIdRuolo()))
					throw new Exception();
				else if(!isRuoloValido(resp.getIdRuolo(), PSBD.SEZIONE_RS,resp.getDataInizioScheda()))
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Ruolo del soggetto incaricato"),i);
			}
			i++;
		}
	}
	
	/******************************************************************************************************
	 * Validatore per gli aggiudicatari
	 * 
	 * @param aggiudicatari List&lt;AggiudicatarioBean&gt;
	 */
	private void validaAggiudicatari(List<AggiudicatarioBean> aggiudicatari,Object o) {
		//controllo per distinguere le righe della lista per validazione Web
		int riga = 0;
		long idGruppoPrecedente = -1;
		
		int i = 1;
		TreeMap<AggiudicatarioBean, String> tm = new TreeMap<AggiudicatarioBean, String>(new AggiudicatarioBeanComparator());
		int i1 = 1;
		for(AggiudicatarioBean aggiud: aggiudicatari){	
			//le righe avranno valore diverso per Web e Massloader
			if(SimogFlags.isFromWeb()){
				//se sto entrando in un nuovo gruppo (o un nuovo singolo) aggiorno la riga
				if(aggiud.getIdGruppo()!=idGruppoPrecedente || aggiud.getIdGruppo()==0)
			    	riga = riga+1;
				idGruppoPrecedente = aggiud.getIdGruppo();
			}
			else
				//se vengo dal massloader
				riga = i;
			//controllo aggiudicatari duplicati al livello scheda
			if(tm.containsKey(aggiud)){
				//errore con il numero
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_187.replace("$1", "Sezione Aggiudicatari"+(SimogFlags.isFromWeb()?", soggetto con codice fiscale: "+aggiud.getSoggettoPartecipante().getCodiceFiscale():"")), riga);

			}else{
				tm.put(aggiud, Integer.toString(i1));
			}
			/*
			if(isEmpty(aggiud.getCfAusiliaria())){
				if(!isEmptyOrZero(aggiud.getFlagAvvalimento()))
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il codice fiscale ditta ausiliaria"),i);
				
			}else {
				try{ // PP il cf deve essere controllato solo per aggiudicatario italiano
					if( (isEmpty(aggiud.getSoggettoPartecipante().getId_stato()) || Costanti.CODICE_STATO_ITALIANO.equals(isEmpty(aggiud.getSoggettoPartecipante().getId_stato().trim())))
							&&  !validaPartitaIva(aggiud.getCfAusiliaria()) && !isCFValido(aggiud.getCfAusiliaria()))
						throw new Exception();
				}catch (Exception e) {
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_117.replace("$1", "Codice fiscale ditta ausiliaria"),i);
				}
			}
			*/
			try{
				if(isEmptyOrZero(aggiud.getIdTipoAgg()))
					throw new Exception();
				else if(!isTipoAggValido(aggiud.getIdTipoAgg(),o))
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "tipologia del soggetto aggiudicatario/affidatario"),riga);
			}
			/*
			try{
				if(Costanti.TIPODITTA_LIKE_ATI == aggiud.getIdTipoAgg() && isEmpty(aggiud.getRuolo()))
					throw new Exception();
				if(!isEmpty(aggiud.getRuolo())){
					if(!this.validaRuolo(aggiud.getRuolo())){
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1","campo Ruolo"));
					}
				}
			}catch (Exception e) {
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il ruolo dell' aggiudicatario nel raggruppamento"),i);
			}
			i++;i1++;
			*/
			try{
				if(Costanti.TIPODITTA_LIKE_ATI == aggiud.getIdTipoAgg() && isEmpty(aggiud.getRuolo()))
					throw new Exception();
				if(!isEmpty(aggiud.getRuolo())){
					if(!this.validaRuolo(aggiud.getRuolo())){
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1","campo Ruolo"));
					}
				}
			}catch (Exception e) {
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il ruolo dell' aggiudicatario nel raggruppamento"),riga);
			}
			
			//Ticket ALM #2592
		/*	try {
				if(Costanti.TIPODITTA_SINGOLA == aggiud.getIdTipoAgg() && !isEmpty(aggiud.getRuolo()))
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_231.replace("$1", "tipologia del soggetto aggiudicatario/affidatario" ), riga);
			}*/
			//FINE Ticket ALM #2592
			
			
			//gm nuovo per codice gruppo
			if(!isEmpty(aggiud.getIdGruppo()) && !isNumber(String.valueOf(aggiud.getIdGruppo())))
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "Progressivo Raggruppamento"),riga);
			validaCodiceGruppo(i, aggiud, aggiudicatari);
			i++;i1++;
		}
		if(i==1){
			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Aggiudicatario"),riga);
		}
	}
	private boolean validaRuolo(String ruolo) {
		return Costanti.MANDATARIA.equals(ruolo) || Costanti.MANDANTE.equals(ruolo);
	}
	
	/*****************************************************************************************************
	 * Validatore per il codice gruppo degli Aggiudicatari
	 * 
	 * @param i int 
	 * @param aggCorrente AggiudicatarioBean
	 * @param aggiudicatari List&lt;AggiudicatarioBean&gt;
	 */
	private void validaCodiceGruppo(int i, AggiudicatarioBean aggCorrente, List<AggiudicatarioBean> aggiudicatari){
		int mandatarie = 0;
		int mandanti = 0;
		//gm se l'aggCorrente ha un codice gruppo
		if(!isEmpty(aggCorrente.getIdGruppo()) && aggCorrente.getIdGruppo()!=0){
			//gm e l'aggCorrente ha un tipo agg per il quale è previsto il codice gruppo
			if(aggCorrente.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_ATI || aggCorrente.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_CONSORZIO){
		        for(AggiudicatarioBean agg:aggiudicatari){
		            //gm se l'agg della lista ha un codice gruppo e tipo agg
			      	if(!isEmpty(agg.getIdGruppo()) && agg.getIdGruppo()!=0 && !isEmpty(agg.getIdTipoAgg())){
				        //gm e se l'agg della lista ha lo stesso codice gruppo e tipo agg dell'aggCorrente
                        if(agg.getIdGruppo()==aggCorrente.getIdGruppo() &&
                    		agg.getIdTipoAgg()==aggCorrente.getIdTipoAgg()){
                    	    //gm controllo il ruolo dell'aggiudicatario
                        	if(!isEmpty(agg.getRuolo())){
            				    if(Costanti.MANDATARIA.equals(agg.getRuolo()))
            					    mandatarie++;
            				    if(Costanti.MANDANTE.equals(agg.getRuolo()))
            					    mandanti++;
            			    }
                    	    //gm se non ha un ruolo sollevo un errore
                        	/*il controllo esiste già in validaAggiudicatari
                    	    else{
                    			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il ruolo dell'Aggiudicatario"),i);
                    	    }
                    	    */
                        }
                        //gm se lo stesso codice gruppo è usato per un tipo agg diverso sollevo un errore
                        else if(agg.getIdGruppo()==aggCorrente.getIdGruppo() &&
                        	agg.getIdTipoAgg()!=aggCorrente.getIdTipoAgg()){
        					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_187.replace("$1", "Il Progressivo Raggruppamento è stato usato per tipi di aggiudicatario diversi"),i);
                        }
			        }
		        }
		        //gm il controllo su mandante e mandatario va fatto solo per ATI
		        if(aggCorrente.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_ATI){
		        if(mandatarie>1)
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_213.replace("$1", "mandatario"),i);
		        if(mandatarie<1)
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_212.replace("$1", "mandatario"),i);
	            if(mandanti<1)
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_212.replace("$1", "mandante"),i);
		    }
		    }
			//gm altrimenti non posso inserire un codice gruppo per un tipo agg che non lo prevede
			else{
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Progressivo Raggruppamento"),i);
			}
		}
		//gm altrimenti se non ha codice gruppo ma è un tipo agg ATI o Consorzio sollevo un errore
		else{
			if(aggCorrente.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_ATI || aggCorrente.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_CONSORZIO)
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Progressivo Raggruppamento"),i);
		}
	}
	
	
	/*****************************************************************************************************
	 * Validatore per le aggiudicazioni
	 * 
	 * @param aggiudicazione AggiudicazioniBean
	 * @param condizioni List&lt;CondizioneAggBean&gt;
	 * @param compL List&lt;TipoAppaltoAggBean&gt;
	 * @param compF List&lt;TipoAppaltoAggBean&gt;
	 * @param infoComuni InfoComuniBean
	 * @param idOss 
	 */
	private void validaAggiudicazione(AggiudicazioneBean aggiudicazione,List<CondizioneAggBean> condizioni,
			InfoComuniBean infoComuni, int modoRealizz, String idOss) {
		/******************Istat and Nuts validation: BEGIN    ***********************/
		
		// PP organi costituzionali
       // PP organi costituzionali
       GaraManager gm = new GaraManager(connection, logger);
       boolean isOrgano = false;
       
       if( SimogFlags.isOrganiCostActive()){
           try {
               isOrgano = gm.isOrganoCost(infoComuni.getCfAmministrazione(), aggiudicazione.getDataInizioAggiudicazione());
           } catch (SQLException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
           } catch (Exception e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
           }
       }
       
		// PP B302.2.0
		if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive() && aggiudicazione.isValidaVariazione()){
			
			if(aggiudicazione.getIdMotivoVarCO() == null)
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Motivazione della variazione anagrafica"));
			else{	
				try{
					if(!isMotivoVarCOValido(aggiudicazione.getIdMotivoVarCO(), aggiudicazione.getDataInizioAggiudicazione()))
						throw new Exception();
					else 	//TICKET ALM #3582 #4194
						if(SimogFlags.is3043Active() && 
								(infoComuni.getID_MODO_REAL()!=Costanti.MODOREAL_CONCESSIONE_LAVORI && 
										infoComuni.getID_MODO_REAL()!=Costanti.MODOREAL_FINANZA_DI_PROGETTO) &&
								aggiudicazione.getIdMotivoVarCO().equals(Costanti.MOTIVO_SOCIETA_PROGETTO)) {
							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_232.replace("$1", "Motivazione della variazione anagrafica "));
						}
						//FINE TICKET ALM #3582 4194
				}catch (Exception e) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Motivazione della variazione anagrafica"));
				}
			}
			
		
			
		}
		
		// entrambi valorizzati
		if(!isEmpty(aggiudicazione.getLuogoNuts()) && !isEmpty(aggiudicazione.getLuogoIstat())){
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_141.replace("$1", "Codice ISTAT").replace("$2", "codice NUTS"));
		}	
		// nessuno valorizzato
		else if(isEmpty(aggiudicazione.getLuogoNuts()) && isEmpty(aggiudicazione.getLuogoIstat())){
			// PP organi costituzionali
			if( SimogFlags.isOrganiCostActive() == false || isOrgano == false){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_109.replace("$1", "Codice ISTAT").replace("$2", "codice NUTS"));
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_109.replace("$1", "Codice NUTS").replace("$2", "codice ISTAT"));
			}
			else{
		    	mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "codice ISTAT"));
			    mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "codice NUTS"));  						
			}
		}
		else {
			// controllo validita' se valorizzato
			if(!isEmpty(aggiudicazione.getLuogoIstat()) || (isStringEmptyValue(aggiudicazione.getLuogoIstat()))){	
				try{
					if(!isNumber(aggiudicazione.getLuogoIstat())) 
						throw new Exception();
					else if(!istatValido(aggiudicazione.getLuogoIstat(),aggiudicazione.getDataInizioAggiudicazione())) 
						throw new Exception();
				}
				catch (Exception e) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "codice ISTAT"));
				}
			}
			// controllo validita' se valorizzato
			if(!isEmpty(aggiudicazione.getLuogoNuts()) || (isStringEmptyValue(aggiudicazione.getLuogoNuts()))){
				try{
					/*if(!isNumber(aggiudicazione.getLuogoNuts())) 
						throw new Exception();
					else*/ 
					if(!nutsValido(aggiudicazione.getLuogoNuts(),aggiudicazione.getDataInizioAggiudicazione())) 
						throw new Exception();
					
				}
				catch (Exception e) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "codice NUTS"));
				}
			}
		}

		/******************Istat and Nuts validation: END    ***********************/
		
		
      if(!SimogFlags.is3031_RFWEBGL02Active()
            || (SimogFlags.is3031_RFWEBGL02Active()
                  && !SimogProperties.getInstance().isCUPAttivo())){
         if(!isEmpty(aggiudicazione.getCup())){
            try{
               if(!validateLength(aggiudicazione.getCup(), Costanti.CUP_LENGTH))
                  throw new Exception();
            }
            catch (Exception e) {
                  mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "codice CUP"));
               }
         }
      }     
		
		// PP 3.02.1.6
		if(SimogFlags.is30216Active()){
			if(!isEmpty(aggiudicazione.getImportoAttuazioneSicurezza())){
				try{
					if(!isPositive(aggiudicazione.getImportoAttuazioneSicurezza()))
						throw new Exception();
					
				}catch (Exception e) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "l'importo totale per l'attuazione della sicurezza"));
				}
			}
			else mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "l'importo totale per l'attuazione della sicurezza"));
		}
//		3.04.8 34190 fix
		try{
			if(Costanti.MODOREAL_ADESIONE_NOCOMPET != modoRealizz && Costanti.MODOREAL_CONCESSIONE_NOCOMPET != modoRealizz){
				if (isEmptyOrZero(aggiudicazione.getImportoComplessivo())) {
					throw new Exception();
			    }
			}
		}
		catch (Exception e) {
		    mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_211.replace("$1", "l'importo complessivo dell'appalto"));
		}
		//2.10 fine controllo 9.1.1.35
		
	
		if(!isEmpty(aggiudicazione.getImportoDisposizione())){
			
			//2.10 aggiunto controllo 9.1.1.36
			try{
				if(aggiudicazione.getImportoDisposizione().doubleValue()==0)
					throw new Exception();
			}
		    catch (Exception e) {
			    mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "l'importo totale delle somme a disposizione"));
		    }
			try{
				//N.B. verifico se è negativo, isPositive verifica se n>=0
				if(!isPositive(aggiudicazione.getImportoDisposizione().doubleValue()))
					throw new Exception();
				
			}
			catch (Exception e) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "l'importo totale delle somme a disposizione"));
			}
			try{
				// verifico se contiene al massimo due cifre decimali
				if(!isNumberDecimal(aggiudicazione.getImportoDisposizione().toString())||!validateDecimalPart(aggiudicazione.getImportoDisposizione(),3))
					throw new Exception();
		
			}
			catch (Exception e) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1", "importo totale delle somme a disposizione"));
			}
			//2.10 fine controllo 9.1.1.36
			
		}
		else mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "l'importo totale delle somme a disposizione"));	
		
		//2.10 aggiunto controllo 9.1.1.37
		
		//TICKET ALM - 3.04.2 NG
		LottoManager lm = new LottoManager(connection,logger);
		String dataCreazione = null;
		try{
		 dataCreazione = gm.getGara(lm.getLotto(infoComuni.getIdLotto()).getId_Gara()).getData_creazione();
		
//		 3.04.8 34190 fix
			if(isEmptyOrZero(aggiudicazione.getIdSceltaContraente())){
				if(Costanti.MODOREAL_ADESIONE_NOCOMPET != modoRealizz && Costanti.MODOREAL_CONCESSIONE_NOCOMPET != modoRealizz)
					throw new Exception();
			}
			else if(!sceltaContraenteValida(aggiudicazione.getIdSceltaContraente(),dataCreazione, isOrgano, idOss))
				throw new Exception();
		}
		catch (Exception e) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Procedura di scelta contraente"));
		}
		
		try{
			if(!isFlag(aggiudicazione.getAstaElettronica()))
				throw new Exception();
		}
		catch (Exception e) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Ricorso all'asta elettronica"));
		}
		
      long sceltaEquiv = aggiudicazione.getIdSceltaContraente();
      
      // ricavo la scelta contraente equivalente
      if(SimogFlags.is3028_RFWEBGL00Active()){
         try {
            sceltaEquiv = Long.valueOf(lm.getSceltaContraenteAVCP(null, aggiudicazione.getIdSceltaContraente()));
         } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      
		try{
			if(Costanti.COND_SPB == sceltaEquiv || Costanti.COND_SPBG == sceltaEquiv){
				if(isEmptyOrZero(condizioni)){
					throw new Exception();
				}
				else{
					this.validaCondizioni(condizioni,dataCreazione);
				}
			}
			
		}
		catch (Exception e) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Condizioni che giustificano il ricorso alla procedura negoziata senza previa pubblicazione di un bando oppure senza previa indizione di una gara"));
		}
		
		try{
			if(Costanti.COND_SPB != sceltaEquiv && Costanti.COND_SPBG != sceltaEquiv){
				if(!isEmptyOrZero(condizioni)){
					throw new Exception();
				}
			}
						
		}
		catch (Exception e) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Condizioni che giustificano il ricorso alla procedura negoziata senza previa pubblicazione di un bando oppure senza previa indizione di una gara"));
		}
		
		
		
	
        //Ribasso aggiudicazione 2.10 - se non valorizzato
		
		if (!isEmpty(aggiudicazione.getPercRibassoAgg())) {
			
		try{
     			if(!validateDecimalPart(aggiudicazione.getPercRibassoAgg(),5))
     				throw new Exception();
    	    	if(!isInRange(aggiudicazione.getPercRibassoAgg(), new BigDecimal(0), BigDecimal.valueOf(99.99999)))
     				throw new Exception();
			}
			catch (Exception e) {
    	    	mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "ribasso aggiudicazione"));
			}
			}
			if(!isEmptyOrZero(aggiudicazione.getPercRibassoAgg()) && !isEmptyOrZero(aggiudicazione.getPercOffAumento())){
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_141.replace("$1", "Ribasso aggiudicazione").replace("$2", "Offerta in aumento"));
				}
		//2.10 fine controllo 9.1.1.84
		
		if(!isEmpty(aggiudicazione.getPercOffAumento())){
    		if(!isPositive(aggiudicazione.getPercOffAumento()))
	    		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "offerta in aumento"));
		
		    if(!validateDecimalPart(aggiudicazione.getPercOffAumento(),5))
		    	mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "offerta in aumento"));
		}
		//2.10 fine controllo 9.1.1.85
		
		//2.10 aggiunto controllo 9.1.1.86
		if (isEmpty(aggiudicazione.getImportoAggiudicazione()))
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_175.replace("$1","di aggiudicazione/affidamento"));
		else{
			try{
				if(!isPositive(aggiudicazione.getImportoAggiudicazione()))
						throw new Exception();
			}
			catch (Exception e){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_174);
			}
			
			try{
				if(isBiggerThanIndiceDispersione(aggiudicazione.getImportoAggiudicazione().doubleValue(),
						getYearData(PageHelper.formatDateOrNull(aggiudicazione.getDataVerbaleAggiudicazione())), 
						infoComuni.getFlagEnteSpeciale(), infoComuni.getTipoContratto(),
						PageHelper.formatDateOrNull(aggiudicazione.getDataVerbaleAggiudicazione()))){
				    throw new Exception();
			    }
		}
			catch (Exception e) {
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_205.replace("$1","importo di aggiudicazione/affidamento"));
			}
		}
		//2.10 fine controllo 9.1.1.86
		
		//2.10 aggiunto controllo 9.1.1.87
		
		if(isEmpty(aggiudicazione.getDataVerbaleAggiudicazione())){
			//controllo se la modalit&agrave di indizione della gara &egrave "Avviso periodico indicativo"
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1", "data di aggiudicazione definitiva o definizione della procedura negoziata"));
		}
		else {
			try{
				if(!isDate(aggiudicazione.getDataVerbaleAggiudicazione()))
					throw new Exception();
			}
			catch (Exception e) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Data di aggiudicazione definitiva o definizione della procedura negoziata"));
			}
			try{
				if(!isInDefinedRange(aggiudicazione.getDataVerbaleAggiudicazione()))
					throw new Exception();
			}
			catch (Exception e) {
//				 PP 20110919 passa a warning Piccinini  mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_161.replace("$1", "Data di aggiudicazione definitiva o definizione della procedura negoziata"));
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_161.replace("$1", "Data di aggiudicazione definitiva o definizione della procedura negoziata"));
			}
		}
	
		// PP data stipula obbligatoria
		if(isEmpty(aggiudicazione.getDataStipula())){
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1", "data stipula del contratto"));
		}
		else {
			try{
				if(!isDate(aggiudicazione.getDataStipula()))
					throw new Exception();
			}
			catch (Exception e) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Data stipula del contratto"));
			}
		}	
		
		// PP durata contrattuale  obbligatorio
		if(isEmptyOrZero(aggiudicazione.getDurataContrattuale())){
			//controllo se la modalit&agrave di indizione della gara &egrave "Avviso periodico indicativo"
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1", "Durata contrattuale in giorni"));
		}
	}
			
		
		
	
	/*
	 * ***************************************			
	 * Validazione info comuni aggiudicazioni
	 * **************************************  
	 */
	/*********************************************************************************************
	 * Validatore per le info comuni delle aggiudicazioni
	 * 
	 * @param infoComuni InfoComuniBean
	 */
	private void validaDatiComuni(InfoComuniBean infoComuni, float importo_lotto) {
		
		if(isEmptyOrZero(infoComuni.getTipoContratto()))
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Categoria prevalente nell'appalto"));
		if(isEmptyOrZero(infoComuni.getFlagEnteSpeciale()))
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Tipo Settore"));
/*
		if(isEmptyOrZero(infoComuni.getID_MODO_REAL()))
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Modalita' di realizzazione"));		
*/		
		if(isEmpty(infoComuni.getFLAG_ESCLUSO())){
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Contratto escluso"));
		}
		/*else{
			if (Costanti.FLAG_VALORE_SI.equals(infoComuni.getFLAG_ESCLUSO()) && isEmptyOrZero(infoComuni.getID_ESCLUSIONE()))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Articolo esclusione"));

			if (Costanti.FLAG_VALORE_NO.equals(infoComuni.getFLAG_ESCLUSO()) && !isEmptyOrZero(infoComuni.getID_ESCLUSIONE()))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_190.replace("$1", "Articolo esclusione"));
		}*/
		
		// verifica dati che indirizzano il flusso di acquisizione
		// FIXMATO: PP abilitare quando si attiva il nuovo flusso per i sottosoglia ed esclusi
		try {
			if (!checkTipoFlusso(infoComuni))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_188);
		} catch (SQLException e1) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_SQL_008.replace("$1", "lettura lotto per controllo flusso acquisizione"));		
		}
		
		// PP 28.10.2009 diventa nascosto su web e opzionale su Massloader, su richiesta di Obino
		try{
//			if(isEmptyOrZero(infoComuni.getIdCategSa())) 
//				throw new Exception();
//			else 
			if(!isEmpty(infoComuni.getIdCategSa()) && !categoriaSaValida(infoComuni.getIdCategSa(),infoComuni.getDataInizioInfo()))
	    		throw new Exception();
				
		}catch (Exception e) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Categoria Stazione Appaltante"));
		}
			
		
		if(isYFlag(infoComuni.getFlagSAAgente())){
			//flag sa agente = true : cf valorizzato e valido, denominazione presente
			try{
				if (!tipologiaSaValida(infoComuni.getTipologiaSA(),infoComuni.getDataInizioInfo())) throw new Exception();
				
			}catch (Exception e){
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Tipologia Stazione Appaltante"));
			}
			try {
				if (isEmptyOrZero(infoComuni.getCfAmmAgente())) throw new Exception();
				
			}catch (Exception e){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Codice fiscale dell'Amm.ne per conto della quale agisce la SA"));
			}	
		
//			try {
//				if (!validaPartitaIva(infoComuni.getCfAmmAgente())) throw new Exception();
//				
//			}catch (Exception e){
//				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "codice fiscale dell'Amm.ne per conto della quale agisce la SA"));
//			}	
			try {
				if (isEmptyOrZero(infoComuni.getDenAmmAgente())) throw new Exception();
				
			}catch (Exception e){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Denominazione dell'Amm.ne per conto della quale agisce la SA"));
			}	
		
		
		}
		else {
			
			if(infoComuni.getTipologiaSA()>0)
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Tipologia Stazione Appaltante"));
			if(!isEmptyOrZero(infoComuni.getCfAmmAgente()))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Codice fiscale soggetto per cui agisce la SA"));
			if(!isEmptyOrZero(infoComuni.getDenAmmAgente()))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Denominazione soggetto per cui agisce la SA"));
		
		}
		
		//valida Esito Procedura
		if(isEmpty(infoComuni.getEsitoProcedura())){
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Esito della Procedura - Stato attuale"));
		//TICKET ALM #3752
		}else 
		   if(EsitoEnum.getEnumByCodice(infoComuni.getEsitoProcedura()) == null){
			 mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Esito della Procedura"));	
           }	
		//FINE TICKET ALM #3752
		else{
			if(infoComuni.isHasAwards() && !infoComuni.getEsitoProcedura().equals(EsitoEnum.AGGIUDICATA.codice())){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_185.replace("$1", "Esito della procedura - Stato attuale"));
			}
		}

		//validazione dati pubblicazione
		validaPubblicazioneBase(infoComuni.getPubblicazione(),importo_lotto);
	
	}
	
	/**
	 * Controllo su tabella della tipologia stazione appaltante
	 * 
	 * @param IdCategoria
	 * @param o data inizio della scheda invocante (estensione della validitÃ  a posteriori)
	 * @return boolean - true se il codice esiste in tabella
	 *         false se non esiste
	 * @throws SQLException
	 */
	private Boolean tipologiaSaValida(long idTipologia,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if(!super.isSQLConnectionEnabled){ return true; } 
		
		AccessiDB adb = new AccessiDB(connection, logger);

		return adb.getTipologica(TIPOLOGIA_SA.TABLE_NAME, 
				TIPOLOGIA_SA.ID_TIPOLOGIA_SA, 
				TIPOLOGIA_SA.DESCRIZIONE, 
				TIPOLOGIA_SA.DATA_FINE_VALIDITA,o).containsKey(String.valueOf(idTipologia).trim());				
	}	
	
	/**
	 * @param idCategoria
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃ  di una tipologia a posteriori
	 * @return Boolean
	 * @throws SQLException
	 */
	private Boolean categoriaSaValida(String idCategoria,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if(!super.isSQLConnectionEnabled){ return true; } 
		
		AccessiDB adb = new AccessiDB(connection, logger);
		return adb.getTipologica(CATEGORIA_SA.TABLE_NAME, 
							CATEGORIA_SA.ID_CATEG_SA, 
							CATEGORIA_SA.DESCRIZIONE, 
							CATEGORIA_SA.DATA_FINE_VALIDITA,o).containsKey(idCategoria);				
	}
	/*
	public boolean istatValido(String istat,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
	/*
		if(!super.isSQLConnectionEnabled){ return true; } 
		
		IstatManager im = new IstatManager(connection,logger);
		return im.isValid(istat,o);
	}
	
	public boolean nutsValido(String nuts,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
	/*	
		if(!super.isSQLConnectionEnabled){ return true; } 
		
		NutsManager nm = new NutsManager(connection,logger);
		return nm.isValid(nuts,o);
	}
	*/
	
	/**
	 * @param tipoLSF
	 * @param idLotto
	 * @param tipoEnte
	 * @param tipoContratto
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃ  di una tipologia a posteriori
	 * @return boolean
	 * @throws SQLException
	 */
//	private boolean tipoLSFValido(long tipoLSF, long idLotto, String tipoEnte, String tipoContratto,Object o)throws SQLException{
//		/** se non ho una base dati valida ritorno true **/
//		if(!super.isSQLConnectionEnabled){ return true; } 
//		
//		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);	
//		
//		return  man.caricaComboAppalto(tipoEnte, tipoContratto,o).containsKey(String.valueOf(tipoLSF));
//				
//		
//	}
	/**
	 * @param idTipoPrest
	 * @param idLotto
	 * @param tipoEnte
	 * @param tipoContratto
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃ  di una tipologia a posteriori
	 * @return boolean
	 * @throws SQLException
	 */
//	private boolean tipoPrestazioneValido(long idTipoPrest,long idLotto, String tipoEnte, String tipoContratto,Object o) throws SQLException{
//		/** se non ho una base dati valida ritorno true **/
//		if(!super.isSQLConnectionEnabled){ return true; } 
//		
//		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);	
//		return man.caricaComboPrestazione(idLotto, tipoEnte,tipoContratto,o).containsKey(String.valueOf(idTipoPrest));
//	}
	
	/**
	 * @param idCriterio
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃ  di una tipologia a posteriori
	 * @return Boolean
	 * @throws SQLException
	 */
//	private Boolean criterioAggiudicazioneValido(long idCriterio,Object o) throws SQLException{
//		/** se non ho una base dati valida ritorno true **/
//		if(!super.isSQLConnectionEnabled){ return true; } 
//		
//		AccessiDB adb = new AccessiDB(connection, logger);		
//		return adb.getTipologica(MODALITA_GARA.TABLE_NAME, MODALITA_GARA.ID_MODALITA_GARA, MODALITA_GARA.DESCRIZIONE, MODALITA_GARA.DATA_FINE_VALIDITA,o).containsKey(String.valueOf(idCriterio));				
//	}

	/**
	 * @param idModo
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃ  di una tipologia a posteriori
	 * @return Boolean
	 * @throws SQLException
	 */
//	private Boolean modoIndizioneValido(long idModo,Object o) throws SQLException{
//		/** se non ho una base dati valida ritorno true **/
//		if(!super.isSQLConnectionEnabled){ return true; } 
//		
//		AccessiDB adb = new AccessiDB(connection, logger);
//	
//		return adb.getTipologica(MODO_INDIZIONE.TABLE_NAME, MODO_INDIZIONE.ID_MODO_GARA, MODO_INDIZIONE.DESCRIZIONE, MODO_INDIZIONE.DATA_FINE_VALIDITA,o).containsKey(String.valueOf(idModo));				
//	}
	
//	private double getSubtotale(BigDecimal l, BigDecimal s , BigDecimal f, BigDecimal sicurezza,BigDecimal progettazione){
//		double temp = 0.00;
//		if(l != null)
//			temp += l.doubleValue();
//		if(s != null)
//			temp += s.doubleValue();
//		if(f != null)
//			temp += f.doubleValue();
//		if(sicurezza != null)
//			temp += sicurezza.doubleValue();
//		if(progettazione != null)
//			temp += progettazione.doubleValue();
//		return temp;
//	}
	/**
	 * variazione di getSubtotale con un'argomento di meno per il nuovo calcolo dell'importo con le percentuali
	 * 
	 * @param l
	 * @param s
	 * @param f
	 * @param progettazione
	 * @return
	 */
	
//	private double getSubtotale1(BigDecimal l, BigDecimal s , BigDecimal f, BigDecimal progettazione){
//		double temp = 0.00;
//		if(l != null)
//			temp += l.doubleValue();
//		if(s != null)
//			temp += s.doubleValue();
//		if(f != null)
//			temp += f.doubleValue();
//		if(progettazione != null)
//			temp += progettazione.doubleValue();
//		return temp;
//	}
	//private boolean subtotaleOK(BigDecimal l, BigDecimal s , BigDecimal f){
//	private boolean subtotaleOK(BigDecimal l, BigDecimal s , BigDecimal f, BigDecimal sicurezza,BigDecimal progettazione){
//		return getSubtotale(l, s, f,sicurezza,progettazione) >= 150000.00;
//	}
	//	private boolean importoAggOK(BigDecimal importoAgg, BigDecimal l, BigDecimal s , BigDecimal f, BigDecimal rib, boolean flagAumento){
	/**
	 * formula ((campo32+campo34)*(1-campo84/100))+campo33+campo33A oppure ((campo32+campo34)*(1+campo85/100))+campo33+campo33A
	 * nel caso di offerta in aumento.
	 * */
//	private boolean importoAggOK(BigDecimal importoAgg, 
//									BigDecimal lavori, 
//									BigDecimal servizi , 
//									BigDecimal forniture, 
//									BigDecimal ribasso,
//									BigDecimal progettazione,
//									BigDecimal sicurezza,
//									BigDecimal nonAssog,
//									int tipo){
//		double temp = ribasso != null ? ribasso.doubleValue() : 0.00;
//		double importo = ribasso != null ? importoAgg.doubleValue() : 0.00;
//		//se tipo = 0 aumento, 1 ribasso, 2 o altro ignora percentuale
//		double impCalcolato = getSubtotale1(lavori, servizi, forniture,progettazione) * (tipo == 0 ? (1+temp/100) : tipo == 1 ?(1-temp/100) : 1) 
//		       				+ (sicurezza != null ? sicurezza.doubleValue() : 0) + (nonAssog != null ? nonAssog.doubleValue() : 0);
//		//settaggio importoCalcolato alla stessa precisione di importo aggiudicazione		
//		impCalcolato = new BigDecimal(impCalcolato, new MathContext(importoAgg.precision())).doubleValue();
//		return (impCalcolato == importo);
//	}	

	//2.10 aggiunto nuovo controllo (somma campi) 9.1.1.28bis
	
//	private double getImportoTotaleFinanziamento(
//			List<TipoFinanziamentoBean> finanziamenti) {
//		
//	    double temp = 0;
//	    if(!isEmpty(finanziamenti)){   
//            for(TipoFinanziamentoBean fin: finanziamenti){	    
//                if (!isEmpty(fin.getImporto()))
//                    temp+=fin.getImporto().doubleValue();
//                }
//            }
//        return temp;
//    }
	//2.10 fine nuovo controllo (somma campi) 9.1.1.28bis
	
	/**
	 * @param idTipo
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃ  di una tipologia a posteriori
	 * @return boolean
	 * @throws SQLException
	 */
	private boolean isTipoAggValido(long idTipo,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if(!super.isSQLConnectionEnabled){ return true; } 
		
		AggiudicatarioManager rman = new AggiudicatarioManager(connection, logger);
		return rman.loadTipoAggiudicatario(o).containsKey(String.valueOf(idTipo));
	}
	/**
	 * @param idTipo
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃ  di una tipologia a posteriori
	 * @return boolean
	 * @throws SQLException
	 */
//	private boolean isTipoFinValido(String idTipo,Object o) throws SQLException{
//		/** se non ho una base dati valida ritorno true **/
//		if(!super.isSQLConnectionEnabled){ return true; } 
//		
//		AggiudicazioniManager rman = new AggiudicazioniManager(connection, logger);
//		return rman.loadFinanziamenti(o).containsKey(String.valueOf(idTipo));
//	}
	/**
	 * @param idLotto
	 * @param idTipo
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃ  di una tipologia a posteriori
	 * @return boolean
	 * @throws SQLException
	 */
//	private boolean isTipoCatValido( long idLotto, String idTipo,Object o) throws SQLException{
//		/** se non ho una base dati valida ritorno true **/
//		if(!super.isSQLConnectionEnabled){ return true; } 
//		
//		RequisitiManager rman = new RequisitiManager(connection, logger);
////		boolean b = rman.caricaCategorie(idLotto,o).containsKey(String.valueOf(idTipo));				
////		logger.debug("[2Â§Â§Â§Â§2] - ("+b+")" + o + "\n\r" + rman.caricaCategorie(idLotto,o).toString());
////		logger.debug("Id tipo: " + idTipo);
//		return rman.caricaCategorie(o).containsKey(String.valueOf(idTipo));
//	}
	/**
	 * @param idCI
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃ  di una tipologia a posteriori
	 * @return boolean
	 * @throws SQLException
	 */
//	private boolean isCIValida(String idCI,Object o) throws SQLException{
//		/** se non ho una base dati valida ritorno true **/
//		if(!super.isSQLConnectionEnabled){ return true; } 
//		
//		RequisitiManager rman = new RequisitiManager(connection, logger);
//		return rman.caricaClassiImporto(o).containsKey(String.valueOf(idCI));
//	}

	private boolean isInDefinedRange(String dataVerbaleAggiudicazione)throws SQLException {
		/** se non ho una base dati valida ritorno true **/
		if(!super.isSQLConnectionEnabled){ return true; } 
		
		AccessiDB adb = new AccessiDB(connection,logger);
		String now =  PageHelper.getDBDateFromTS(adb.getNow());
		
		dataVerbaleAggiudicazione = PageHelper.getFormattedDBDate(dataVerbaleAggiudicazione);
		if(Costanti.START_DATE.compareTo(dataVerbaleAggiudicazione) <= 0 && now.compareTo(dataVerbaleAggiudicazione) >= 0)
			return true;
		else return false;
	}
	
	/**
	 * @param idStrumento
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃ  di una tipologia a posteriori
	 * @return boolean
	 * @throws SQLException
	 */
//	private boolean isStrumentoValido(String idStrumento,Object o) throws SQLException{
//		/** se non ho una base dati valida ritorno true **/
//		if(!super.isSQLConnectionEnabled){ return true; } 
//		
//		AggiudicazioniManager rman = new AggiudicazioniManager(connection, logger);
//		return rman.loadStrumenti(o).containsKey(String.valueOf(idStrumento));
//	}
	
	/**
	 * @param codFisc String
	 * @return boolean
	 * @throws SQLException
	 */
//	private boolean isCFValido(String codFisc) throws SQLException{
//		/** se non ho una base dati valida ritorno true **/
//		if(!super.isSQLConnectionEnabled){ return true; } 
//		
//		RubricaManager rman = new RubricaManager(connection, logger);
//		return rman.getPartecipantiList("", codFisc, "", 0, 99).size() > 0; // True se esiste almeno un Soggetto di codice codFisc
//	}

	private boolean isBiggerThanIndiceDispersione(double importo_complessivo_appalto,String anno, String tipoSettore, String tipoContratto, Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if(!super.isSQLConnectionEnabled){ return true; } 
		
		if (anno == null)
			return false;
		
		AggiudicazioniManager rman = new AggiudicazioniManager(connection, logger);
		//true se l'importo complessivo è maggiore dell'indice dispersione
		BigDecimal importo =  rman.getIndiceDispersione(anno, tipoSettore, tipoContratto, o);
		if (importo == null)
			return false;
		else
			return (importo_complessivo_appalto > importo.doubleValue()); 
	}

	/**
	 * verifica la validità formale del codice fiscale usa la funzione disponibile nella classe estesa
	 * @param cf String
	 * @return boolean
	 */
	public boolean validaCodiceFiscale(String cf) {
		return super.validaCodiceFiscale(cf);
	}
	
	public boolean validaCondizioni(List<CondizioneAggBean> condizioni){
		boolean esito_locale = true;
		int counter = 1;
		int local_error = 0;
		for(CondizioneAggBean condizione : condizioni){
			String id = String.valueOf(condizione.getIdCondizione());
			esito_locale = super.validaTipologica(CONDIZIONI.TABLE_NAME, CONDIZIONI.ID_CONDIZIONE, CONDIZIONI.DESCRIZIONE, CONDIZIONI.T_DATA_FINE_VALIDITA, condizione.getDataInizioCond(), id);
			if(!esito_locale){
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1","campo Condizione"), counter);
				local_error++;
			}
			counter++;
		}
		if(local_error == 0){return true;}
		return false;
	}
	
	public boolean validaCondizioni(List<CondizioneAggBean> condizioni, Object dataRif){
		boolean esito_locale = true;
		int counter = 1;
		int local_error = 0;
		for(CondizioneAggBean condizione : condizioni){
			String id = String.valueOf(condizione.getIdCondizione());
			
			//TICKET ALM #2847
			//esito_locale = super.validaTipologica(CONDIZIONI.TABLE_NAME, CONDIZIONI.ID_CONDIZIONE, CONDIZIONI.DESCRIZIONE, CONDIZIONI.T_DATA_FINE_VALIDITA, dataRif, id);
			esito_locale = super.validaTipologicaWithData(CONDIZIONI.TABLE_NAME, CONDIZIONI.ID_CONDIZIONE, CONDIZIONI.DESCRIZIONE, CONDIZIONI.T_DATA_INIZIO_VALIDITA , CONDIZIONI.T_DATA_FINE_VALIDITA, dataRif, id);
			if(!esito_locale){
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1","campo Condizione"), counter);
				local_error++;
			}
			counter++;
		}
		if(local_error == 0){return true;}
		return false;
	}

}
