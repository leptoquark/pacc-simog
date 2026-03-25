package it.avlp.simog.validatore;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import it.avcp.simog.managers.bdncp.BdncpManager;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.ART_ESTREMA_URGENZA_SOMMA_URGENZA;
import it.avlp.simog.db.generated.EAGG_CATEGORIE;
import it.avlp.simog.db.generated.EAGG_MOTIVI;
import it.avlp.simog.db.generated.FUNZIONI_DELEGATE;
import it.avlp.simog.db.generated.MODALITA_INDIZIONE_ALLEGATO_IX;
import it.avlp.simog.db.generated.MODI_REALIZZAZIONE;
import it.avlp.simog.db.generated.MODO_INDIZIONE;
import it.avlp.simog.db.generated.STRUMENTI_SVOLGIMENTO_PROCEDURE;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.IniziativaManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

public class GaraValidator extends SimogValidator  {

	public GaraValidator(Connection connection, Logger logger) {
		super(connection, logger);
		
	}
	@Override
	public boolean valida(Object bean, String section) {
		if(bean != null){
			Gara gara = (Gara)bean;
			valida(gara);
			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() == 0;
	}
		return false;
	}

	/********************************************************************************************************
	 * Validatore per la Gara
	 * 
	 * @param gara Gara
	 */
	private void valida(Gara gara){
		
// controlli per dati provenienti dai WS 20-11-2009
		
		if( gara.getCF_UTENTE() == null || "".equals(gara.getCF_UTENTE().trim())){
			//SIMOG_VALIDAZIONE_112
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Codice Fiscale Utente") );
		}
		if( gara.getCF_AMMINISTRAZIONE() == null || "".equals(gara.getCF_AMMINISTRAZIONE().trim())){
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Codice Fiscale Amministrazione") );
		} else if((gara.getId_Gara()<=0 || SimogProperties.getInstance().isDataCreatedAfter3045(gara.getData_creazione()) ) //TICKET ALM #12088 - 3.04.5
				     && (gara.getCodiceAusa()==null || "".equals(gara.getCodiceAusa()))
				     && (SimogProperties.getInstance().getUrlWsAusa() != null && !"".equals(SimogProperties.getInstance().getUrlWsAusa()))) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_271.replace("$1", gara.getCF_AMMINISTRAZIONE()) );
			return;
		}
		if( gara.getDENOM_AMMINISTRAZIONE() == null || "".equals(gara.getDENOM_AMMINISTRAZIONE().trim())){
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Denominazione Amministrazione") );
		}
		if( gara.getID_STAZIONE_APPALTANTE() == null || "".equals(gara.getID_STAZIONE_APPALTANTE().trim())){
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Codice Stazione Appaltante") );
		}
		if(gara.getDENOM_STAZIONE_APPALTANTE() == null || "".equals(gara.getDENOM_STAZIONE_APPALTANTE().trim())){
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Denominazione Stazione Appaltante" ) );
		}
// fine		
		
		
		
		if ( gara.getOggetto() == null || gara.getOggetto().trim().length() == 0 ) {
			mEccezioni.addValidationField("label_OggettoGara");
			mEccezioni.addValidationErr(Messaggi.SIMOG_GARA_008 );
		}
		/*gm nuovo codice simog 3.04, eliminato importo gara ed aggiunto numero lotti
		if (gara.getIMPORTO_GARA() == null || !(gara.getIMPORTO_GARA().compareTo(new BigDecimal(0)) > 0)
				&& !(gara.getIMPORTO_GARA().compareTo(new BigDecimal(1).negate()) == 0)) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_GARA_013 );
		}
		*/
		
		// MEV 34188 - 3.04.8.1 fase 2 - controllo che se scelta la voce dalla LISTA MOTIVAZIONI CIG il codice amministrazione va verificato
		//che sia presente nella tabella "LISTA_SOGGETTI_AGGREGATORI"
		if(gara.getCOD_MOTIVO_EAGG() == 3) {
			try 
			{
				GaraManager gm = new GaraManager(connection, logger);
				if(!gm.checkSoggettoAggregatorePresente(gara.getCF_AMMINISTRAZIONE()))
				{
					mEccezioni.addValidationField("label_EAGG_MOTIVI");; // DA CHIARIRE CON KATIA
					mEccezioni.addValidationErr(Messaggi.SIMOG_SOGG_AGGREG_VALIDAZIONE_001);  // DA CHIARIRE CON KATIA
				}
			}
			catch(Exception e)
			{
				
			}
		}
		//FINE MEV 34188
		
		//TICKET ALM #14289 - 3.04.5
		if(gara.getNumeroLotti()==null) {
			mEccezioni.addValidationField("label_NumeroLotti");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Numero Lotti della Gara" ) );
		}
		//FINE TICKET ALM #14289
		
		//gm nuovo codice simog 3.04, il numero lotti da web è di default 0, se è null è dal WS
		if(!isEmpty(gara.getNumeroLotti()) && gara.getNumeroLotti().equals(new Integer(0))) {
			mEccezioni.addValidationField("label_NumeroLotti");
   			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "Numero Lotti della Gara" ) );
		}
		if(isEmptyOrZero(gara.getTIPO_SCHEDA_GARA())) {
			mEccezioni.addValidationField("label_SettoreGara");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Settore del contratto"));
		}
		else if(!Costanti.TIPO_ENTE_SPECIALE.equals(gara.getTIPO_SCHEDA_GARA()) && !Costanti.TIPO_ENTE_ORDINARIO.equals(gara.getTIPO_SCHEDA_GARA())){
			mEccezioni.addValidationField("label_SettoreGara");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Settore del contratto"));
		}
		else{	
			// modo indizione solo per settori speciali
			if(Costanti.TIPO_ENTE_SPECIALE.equals(gara.getTIPO_SCHEDA_GARA()) && isEmptyOrZero(gara.getID_MODO_GARA()) && isEmptyOrZero(gara.getID_ALLEGATO_IX())) {
				mEccezioni.addValidationField("label_ModalitaIndizioneSettSpec");
				mEccezioni.addValidationField("label_ModalitaIndizioneAllegatoIX");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Modalita' di indizione (settori speciali) oppure Modalita' di indizione servizi di cui all'allegato IX"));
			}
			if(Costanti.TIPO_ENTE_ORDINARIO.equals(gara.getTIPO_SCHEDA_GARA()) && !isEmptyOrZero(gara.getID_MODO_GARA())) {
				mEccezioni.addValidationField("label_ModalitaIndizioneSettSpec");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_189.replace("$1", "Modalita' di indizione (settori speciali)"));	
			}
		}
			
		try {
			if(!isEmptyOrZero(gara.getID_MODO_GARA())){
				if(!modoIndizioneValido(gara.getID_MODO_GARA(), PageHelper.getCurrentDate())) {
					mEccezioni.addValidationField("label_ModalitaIndizioneSettSpec");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Modalita' di indizione (settori speciali)"));
				}
			}
			if(isEmptyOrZero(gara.getID_MODO_REAL())) {
				mEccezioni.addValidationField("label_ModalitaRealizzazione");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Modalita' di realizzazione"));
			}
			else{
				//TICKET ALM - 3.04.2 NG
				if(!modoRealValido(gara.getID_MODO_REAL(),gara.getData_creazione())) {
					mEccezioni.addValidationField("label_ModalitaRealizzazione");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Modalita' di realizzazione"));
				}
				//FINE TICKET ALM - 3.04.2 NG
			}
			
			// TICKET ALM - 3.04.3 #659
			boolean isGaraCreatedAfter3043 = (SimogProperties.getInstance().isDataCreatedAfter3043(gara.getData_creazione()));
			//ticket 20057
			boolean isGaraCreatedAfter3046 = (!SimogProperties.getInstance().isDataCreatedAfter3046(gara.getData_creazione()));
			if(isGaraCreatedAfter3046 && isGaraCreatedAfter3043) {
				if(SimogFlags.isAccordoQuadroOrConvenzione(gara.getID_MODO_REAL()) && isEmptyOrZero(gara.getDurataGiorni()))  {
					mEccezioni.addValidationField("label_DurataConvAccordoQuadro");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Durata della convenzione o accordo quadro in giorni"));
				}
	
				if(!SimogFlags.isAccordoQuadroOrConvenzione(gara.getID_MODO_REAL()) && !isEmptyOrZero(gara.getDurataGiorni()))  {
					mEccezioni.addValidationField("label_DurataConvAccordoQuadro");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Durata della convenzione o accordo quadro in giorni"));
				}
			} else {
				if(!isEmptyOrZero(gara.getDurataGiorni())) {
					mEccezioni.addValidationField("label_DurataConvAccordoQuadro");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Durata della convenzione o accordo quadro in giorni"));
				}
			}

			
			//TICKET ALM - 3.04.2 NG 
			if(SimogFlags.is3042Active() && SimogProperties.getInstance().isDataCreatedAfter3042(gara.getData_creazione())) {
				if(isEmptyOrZero(gara.getID_SVOLGIMENTO())) {
					mEccezioni.addValidationField("label_StrumentiSvolgProc");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Strumenti per lo svolgimento delle procedure"));
				} else {
					if(!strumentoSvolgimentoValido(gara.getID_SVOLGIMENTO(), gara.getData_creazione())) {
						mEccezioni.addValidationField("label_StrumentiSvolgProc");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Strumenti per lo svolgimento delle procedure"));
					}
					//TICKET ALM #19668
//					else if(SimogFlags.isAccordoQuadroOrConvenzione(gara.getID_MODO_REAL()) && !SimogFlags.isSvolgimentoAccordoQuadro(gara.getID_SVOLGIMENTO())) {
//						mEccezioni.addValidationField("label_StrumentiSvolgProc");
//						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_261.replace("$1", "Strumenti per lo svolgimento delle procedure"));
//					}
					else if(SimogFlags.isSvolgimentoAccordoQuadro(gara.getID_SVOLGIMENTO()) && !SimogFlags.isSvolgimentoAllowed(gara.getID_MODO_REAL())) {
						mEccezioni.addValidationField("label_StrumentiSvolgProc");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_262.replace("$1", "Strumenti per lo svolgimento delle procedure"));
					}
				}
				//Campo non consentito per gare antecedenti
			} else if(!isEmptyOrZero(gara.getID_SVOLGIMENTO()) ) {
				mEccezioni.addValidationField("label_StrumentiSvolgProc");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Strumenti per lo svolgimento delle procedure"));
			}
			//FINE TICKET ALM - 3.04.2 NG 
			

			//TICKET ALM - 3.04.2 NG 
			if(SimogFlags.is3042Active() && SimogProperties.getInstance().isDataCreatedAfter3042(gara.getData_creazione())) {
				if(isYFlag(gara.getURGENZA_DL133()) && isEmptyOrZero(gara.getID_ESTREMA_URGENZA()) ) {
					mEccezioni.addValidationField("label_MotivoUrgenza");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Motivo urgenza"));
				} else {
					if(isYFlag(gara.getURGENZA_DL133()) && !isEmptyOrZero(gara.getID_ESTREMA_URGENZA()) && !estremaUrgenzaValido(gara.getID_ESTREMA_URGENZA(), PageHelper.getCurrentDate())) {
						mEccezioni.addValidationField("label_MotivoUrgenza");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Motivo urgenza"));
					}
					else if(isNFlag(gara.getURGENZA_DL133()) && !isEmptyOrZero(gara.getID_ESTREMA_URGENZA())) {
						mEccezioni.addValidationField("label_MotivoUrgenza");
						mEccezioni.addValidationField("label_EstremaUrgenza");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Motivo urgenza")+". Il campo 'Estrema urgenza/Esecuzione di lavori di somma urgenza' e' stato impostato a NO");
					}
				}
			} else {
				//Campo non consentito per gare antecedenti
				if(!isEmptyOrZero(gara.getID_ESTREMA_URGENZA())) {
					mEccezioni.addValidationField("label_MotivoUrgenza");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Motivo urgenza"));
				}
			}
			//FINE TICKET ALM - 3.04.2 NG 
			
			//TICKET  ALM - 3.04.2 2005
			if(SimogFlags.is3042Active() && SimogProperties.getInstance().isDataCreatedAfter3042(gara.getData_creazione())) {
				if(!isEmptyOrZero(gara.getID_ALLEGATO_IX()) && !isEmptyOrZero(gara.getID_MODO_GARA())) {
					mEccezioni.addValidationField("label_ModalitaIndizioneAllegatoIX");
					mEccezioni.addValidationErr(Messaggi.SIMOG_LOTTO_030.replace("$1", "Modalita' di indizione (settori speciali)").replace("$2", "Modalita' di indizione servizi di cui all'allegato IX")); 
				}
				else
				  if(!isEmptyOrZero(gara.getID_ALLEGATO_IX()) && !modalitaIndizioneArtIXValido(gara.getID_ALLEGATO_IX(), PageHelper.getCurrentDate())) {
					  mEccezioni.addValidationField("label_ModalitaIndizioneAllegatoIX");
					 mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Modalita' di indizione servizi di cui all'allegato IX"));
				  }
			} else  //Campo non consentito per gare antecedenti
				if(!isEmptyOrZero(gara.getID_ALLEGATO_IX())) {
					mEccezioni.addValidationField("label_ModalitaIndizioneAllegatoIX");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Modalita' di indizione servizi di cui all'allegato IX"));
				}
				//FINE TICKET  ALM - 3.04.2 2005
			
			// controllo cig associato ad adesione accordo quadro
			//MEV 34190 3.04.8 aggiunti controlli su voci MODOREAL_CONCESSIONE e MODOREAL_CONCESSIONE_NOCOMPET
			if(!isEmptyOrZero(gara.getID_MODO_REAL()) 
					&& (Costanti.MODOREAL_ADESIONE == gara.getID_MODO_REAL() || Costanti.MODOREAL_ADESIONE_NOCOMPET == gara.getID_MODO_REAL()
							|| Costanti.MODOREAL_CONCESSIONE == gara.getID_MODO_REAL() || Costanti.MODOREAL_CONCESSIONE_NOCOMPET == gara.getID_MODO_REAL()) 
					&& isEmpty(gara.getCIG_ACC_QUADRO())){
				// deve essere indicato un CIG
				mEccezioni.addValidationField("label_CIG_AccQuadro");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "CIG relativo all'accordo quadro/convenzione"));			
			}
			
			
			// non discende da accordo quadro
			//MEV 34190 3.04.8 aggiunti controlli && Costanti.MODOREAL_CONCESSIONE != gara.getID_MODO_REAL() && Costanti.MODOREAL_CONCESSIONE_NOCOMPET != gara.getID_MODO_REAL()
			if(!isEmptyOrZero(gara.getID_MODO_REAL()) 
					&& Costanti.MODOREAL_ADESIONE != gara.getID_MODO_REAL() && Costanti.MODOREAL_ADESIONE_NOCOMPET != gara.getID_MODO_REAL() 
					&& Costanti.MODOREAL_CONCESSIONE != gara.getID_MODO_REAL() && Costanti.MODOREAL_CONCESSIONE_NOCOMPET != gara.getID_MODO_REAL()
					&& !isEmpty(gara.getCIG_ACC_QUADRO())){
				// non deve essere indicato un CIG
				mEccezioni.addValidationField("label_CIG_AccQuadro");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "CIG relativo all'accordo quadro/convenzione"));			
			}
			if(!isEmptyOrZero(gara.getID_MODO_REAL()) 
					&& (Costanti.MODOREAL_ADESIONE == gara.getID_MODO_REAL() || Costanti.MODOREAL_ADESIONE_NOCOMPET == gara.getID_MODO_REAL() || Costanti.MODOREAL_CONCESSIONE == gara.getID_MODO_REAL() || Costanti.MODOREAL_CONCESSIONE_NOCOMPET == gara.getID_MODO_REAL()) 
					&& !isEmpty(gara.getCIG_ACC_QUADRO())){
				// verifica se il cig indicato è relativo ad una gara accordo quadro
				LottoManager lm = new LottoManager(connection, logger);
				try {
					 List<Lotto> ret = lm.getLottoByCigWS(gara.getCIG_ACC_QUADRO());
					 
					 if(ret.size() == 0) {
						mEccezioni.addValidationField("label_CIG_AccQuadro");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_008);
					 }
					 else{
						 if(!isEmpty(ret.get(0).getDATA_CANCELLAZIONE_LOTTO())
							 || !isEmpty(ret.get(0).getDATA_INIB_PAGAMENTO())) {
							 mEccezioni.addValidationField("label_CIG_AccQuadro");
							 mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_008);
						 }
						 else{
							 GaraManager gm = new GaraManager(connection, logger);
							 Gara garaAcc = gm.getGara(ret.get(0).getId_Gara());
							 if(garaAcc == null) {
								 mEccezioni.addValidationField("label_CIG_AccQuadro");
								 mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_008);
							 }
							 else{
								 if(!isEmpty(garaAcc.getDATA_CANCELLAZIONE_GARA())
									|| !isEmpty(garaAcc.getDATA_INIB_PAGAM())
									 || (!SimogFlags.isAccordoQuadroOrConvenzione(garaAcc.getID_MODO_REAL()) && 
											 !SimogFlags.isSvolgimentoAccordoQuadro(garaAcc.getID_SVOLGIMENTO()))) {
									 mEccezioni.addValidationField("label_CIG_AccQuadro");
									 mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_008);									 
								 }
							 }
						 }						 
					 }
				} catch (Exception e) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_008);
				}
			}	
			
			if( SimogFlags.is3031_ESCL_AVCPASS() ){
      			if( SimogFlags.isFromWeb() ){
      			    boolean isCreazioneGara = isEmpty(gara.getId_Gara());
         			if( isCreazioneGara && !isFlag(gara.getESCLUSO_AVCPASS()) 
         			      && SimogProperties.getInstance().getDataEsclAvcpass().compareTo(PageHelper.getCurrentDate()) <= 0) {
         				mEccezioni.addValidationField("label_GaraEsclusaAVCPass");
         				//3.04.7.1
         				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Gara esclusa dall'acquisizione obbligatoria dei requisiti ai fini FVOE"));
         			}
         			if( !isCreazioneGara && !isFlag(gara.getESCLUSO_AVCPASS()) 
         			      && SimogProperties.getInstance().getDataEsclAvcpass().compareTo(gara.getData_creazione()) <= 0) {
         				mEccezioni.addValidationField("label_GaraEsclusaAVCPass");
         				//3.04.7.1
         				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Gara esclusa dall'acquisizione obbligatoria dei requisiti ai fini FVOE"));
         			}
      			} 
      			else {
      			   //FIXMATO !* PP per ora solo warning ma va innalzato il controllo ad adeguamenti effettuati
                   if( !isFlag(gara.getESCLUSO_AVCPASS()) ) {
                	   mEccezioni.addValidationField("label_GaraEsclusaAVCPass");
                	 //3.04.7.1
                	   mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Gara esclusa dall'acquisizione obbligatoria dei requisiti ai fini FVOE"));
                   }     			   
      			}
			}
			
         //INT85 postvalidazione per dichiarazione utente
// Katia dice no!
//			if(SimogFlags.isINT85_RFWEBGL01Active()
//			      && SimogProperties.getInstance().isINT85Attivo()
//			      && SimogProperties.getInstance().isSAINT85(gara.getData_creazione())
//			      && gara.getTIPOSA_BDNCP() == Costanti.COD_SA_COMUNE
//			      && gara.getSCELTA_LEGGE89() == 0){
//			   mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_228);			      
//			}
         
			// controllo i valori validi per la scelta
         if(SimogFlags.isINT85_RFWEBGL01Active()
               && SimogProperties.getInstance().isINT85Attivo()
               && SimogProperties.getInstance().isSAINT85(gara.getData_creazione())
               && Costanti.COD_SA_COMUNE.equals(gara.getTIPOSA_BDNCP()) 
               && !Costanti.LEGGE89_1.equals(String.valueOf(gara.getSCELTA_LEGGE89()))
               && !Costanti.LEGGE89_2.equals(String.valueOf(gara.getSCELTA_LEGGE89()))){
            // controllo solo se la sezione viene mostrata            
            if(SimogFlags.is30350_RFWEBGL01Active() && SimogProperties.getInstance().isEAGGAttivo(gara.getData_creazione())){
               // no controllo selezionata una categoria diversa da 999
               if(gara.getCatMercArray().length > 1 || gara.getCatMercArray().length == 0 
                     || (gara.getCatMercArray().length == 1 && !Costanti.EAGG_CATMERC_999.equals(gara.getCatMercArray()[0]))){
                  // nulla da segnalare
               }
               else
                  mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", Costanti.LEGGE89_TITLE));
            }
            else
               mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", Costanti.LEGGE89_TITLE));
         }
			
         //is30350_RFWEBGL01Active
         if((SimogFlags.is30350_RFWEBGL01Active() || SimogFlags.is30350_RFWSGL01Active())
               && SimogProperties.getInstance().isEAGGAttivo(gara.getData_creazione())){
            // selezionare almeno una categoria merceologica
            if(gara.getCatMerc().size() == 0)
               mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_228.replace("$1", Costanti.EAGG_LABEL_CATEGORIE)); //Ticket ALM #972
            else 
               validaCategorie(gara.getCatMercArray(), gara.getData_creazione());

if(SimogFlags.is30350_UNACATEGActive()){
   if(gara.getCatMerc().size() > 1)
      mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_169.replace("$1", Costanti.EAGG_LABEL_CATEGORIE).replace("$2", "merceologica"));
}
      

            String dataCheck = gara.getData_creazione();
            if(dataCheck==null || "".equals(dataCheck))
            {
            	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
            	dataCheck = sdf.format(new java.util.Date());
            }

            //TICKET ALM #4222 - 3.04.4
            //Verifico se nei lotti della gara ci sono categorie non selezionate dall'utente
            if(SimogProperties.getInstance().isDataCreatedAfter3044(gara.getData_creazione()) && 
            		SimogProperties.getInstance().isDataCreatedAfterSoggAggr(dataCheck)) {

                //Campo Motivazione Richiesta CIG non piu' previsto 
            	if(gara.getCOD_MOTIVO_EAGG() > 0)
            		 mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", Costanti.EAGG_LABEL_MOTIVI));  
            	
            	if(gara.getCatMercArray().length > 0 && gara.getId_Gara()!=0) {
            		 GaraManager gm = new GaraManager(connection, logger);
            		List<Long> categorieInLotto = gm.getListaCatMercInLotto(gara.getId_Gara());
            		for(Long catLotto : categorieInLotto) {
            			boolean found = false;
            			for(int i=0;i<gara.getCatMercArray().length;i++) {
            				Long catGara = Long.parseLong(gara.getCatMercArray()[i]);
            				if(catLotto.intValue()==catGara.intValue())
            					found=true;
            			}
            			
            			if(!found) {
            			     String catMercDesc = gm.getDescrCat(catLotto);
            				 mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_257.replace("$1", catMercDesc)); 
            			}
            			
            		}
            	}
            	
            } else {
                // se selezionata almeno una diversa da 999 obbligo di indicare la motivazione
                if((gara.getCatMerc().size() == 1 && !gara.getCatMerc().contains(Costanti.EAGG_CATMERC_999)
                         || gara.getCatMerc().size() > 1)
                      && gara.getCOD_MOTIVO_EAGG() == 0)
                   mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", " il campo " + Costanti.EAGG_LABEL_MOTIVI));
                
                // motivo deve essere valido e attivo in tipologica
                if(gara.getCOD_MOTIVO_EAGG() > 0 && !motivoValido(gara.getCOD_MOTIVO_EAGG(), gara.getData_creazione()))
                   mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", Costanti.EAGG_LABEL_MOTIVI));  
                
		                if(SimogProperties.getInstance().isDataCreatedAfter3044(gara.getData_creazione())){
		                	if(gara.getCatMercArray().length > 0 && gara.getId_Gara()!=0) {
		               		 GaraManager gm = new GaraManager(connection, logger);
		               		List<Long> categorieInLotto = gm.getListaCatMercInLotto(gara.getId_Gara());
		               		for(Long catLotto : categorieInLotto) {
		               			boolean found = false;
		               			for(int i=0;i<gara.getCatMercArray().length;i++) {
		               				Long catGara = Long.parseLong(gara.getCatMercArray()[i]);
		               				if(catLotto.intValue()==catGara.intValue())
		               					found=true;
		               			}
		               			
		               			if(!found) {
		               			     String catMercDesc = gm.getDescrCat(catLotto);
		               				 mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_257.replace("$1", catMercDesc)); 
		               			}
		               			
		               			
		               		}
		               		
		               	}
                }
                
            }
            //FINE TICKET ALM #4222 - 3.04.4
            
            
         }         
         
         
       //TICKET ALM #659 - 3.04.4
         if(SimogProperties.getInstance().isDataCreatedAfter3044(gara.getData_creazione())) {
        	 if(isEmpty(gara.getFlagSAAgente())) {
        		 mEccezioni.addValidationField("label_SA_AgenteGara");
        		 mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "La stazione appaltante agisce per conto di altro soggetto singolo?"));	
        	 }
        	 else if(Costanti.FLAG_VALORE_SI.equals(gara.getFlagSAAgente())) {  //Se e' stato messo il flag a SI e non e' stata scelta una funzione, mostra errore per campo obbligatorio
        		       if(gara.getID_F_DELEGATE()<=0) {
        		    	   mEccezioni.addValidationField("label_FunzioniDelegate");
        		           mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Funzioni Delegate"));
        		       }
        		       else if(!funzioniDelegateValido(gara.getID_F_DELEGATE(),gara.getData_creazione())) { //Controllo validita' id
        		    	   mEccezioni.addValidationField("label_FunzioniDelegate");
        		    	   mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Funzioni Delegate"));
        		       }
        		       
        		       if(gara.getCF_AMM_AGENTE()==null || "".equals(gara.getCF_AMM_AGENTE())) {
        		    	   mEccezioni.addValidationField("label_CF_AmmAgente");
        		    	   mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "Codice fiscale soggetto per conto del quale agisce la S.A. (in caso di soggetto singolo)"));
        		       }
        		       else {
        		    	     //TICKET ALM #24945
        		    	     if(gara.getCF_AMM_AGENTE().equals(gara.getCF_AMMINISTRAZIONE())) {
        		    	    	 GaraManager gm = new GaraManager(connection,logger);
        		    	    	 List<String> datidelega = gm.getDatiStoriciGaraDelegata(gara.getId_Gara());
        		    	    	 //Controlla che la gara non sia stata presa in carico dalla delegante. In caso negativo, mostra l'errore
        		    	    	 if(datidelega==null || datidelega.isEmpty() || (datidelega.size()>0 && "".equals(datidelega.get(5)))) {
        		    	    	 mEccezioni.addValidationField("label_CF_AmmAgente");
              		    	     mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_279.replace("$1", "Codice fiscale soggetto per conto del quale agisce la S.A. (in caso di soggetto singolo)"));
        		    	    	 }
        		    	     } else {        		    	   
		        		    	 //TICKET ALM - 3.04.3
		       					//Controllo in BDNCP sul CF della SA delegante
        		    	    	BdncpManager bm = new BdncpManager(connection,logger);
 	       						String denSA = bm.loadDenSA(gara.getCF_AMM_AGENTE());
 	       						if("".equals(denSA) || denSA == null) { // FASE 3 - 3.04.9 MAC 35066 AGGIUNTO IL PEZZO || denSA == null
 	       							mEccezioni.addValidationField("label_CF_AmmAgente");
 	       							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_236.replace("$1", "Codice fiscale dell'Amm.ne per conto della quale agisce la SA"));	
 	       						}
        		    	     }
        		       }
        		    	   
        	 } else //Controlli campi non previsti
        		 if(Costanti.FLAG_VALORE_NO.equals(gara.getFlagSAAgente())) {
        			    if(gara.getID_F_DELEGATE()>0) {
        			    	mEccezioni.addValidationField("label_FunzioniDelegate");
        			    	mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Funzioni Delegate"));
        			    }
        			    if(gara.getCF_AMM_AGENTE()!=null && !"".equals(gara.getCF_AMM_AGENTE())) {
        			    	mEccezioni.addValidationField("label_CF_AmmAgente");
        			    	mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Codice fiscale soggetto per conto del quale agisce la S.A. (in caso di soggetto singolo)")); 
        			    }
        		 } 
         } else {
        	 if(!isEmpty(gara.getFlagSAAgente())) {
        		 mEccezioni.addValidationField("label_SA_AgenteGara");
        		 mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "La stazione appaltante agisce per conto di altro soggetto singolo?"));
        	 }
        	 if(!isEmptyOrZero(gara.getID_F_DELEGATE())) {
        		 mEccezioni.addValidationField("label_FunzioniDelegate");
        		 mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Funzioni Delegate"));
        	 }
        	 if(!isEmpty(gara.getCF_AMM_AGENTE())) {
        		 mEccezioni.addValidationField("label_CF_AmmAgente");
        		 mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Codice fiscale dell'Amm.ne per conto della quale agisce la SA"));	
        	 }
         }
         //FINE TICKET ALM #659 - 3.04.4
         
         //TICKET ALM #11490 - 3.04.4
         if(SimogProperties.getInstance().isDataCreatedAfter3044(gara.getData_creazione()) && gara.getId_Gara()!=0) {
        	 if(!isEmptyOrZero(gara.getID_MODO_GARA()) && gara.getID_MODO_GARA()!=Costanti.ID_MODO_GARA_2) {
        		 LottoManager lm = new LottoManager(connection, logger);
        		 String cigErr = "";
        		 List<Lotto> listLotti = lm.getListaLotti(gara.getId_Gara());
        		 for(Lotto lotto : listLotti) {
        			 if(Costanti.TIPO_SCELTA_CONTRAENTE_SS.equals(lotto.getId_Scelta_Contraente()) && (lotto.getCondizioni()==null || lotto.getCondizioni().isEmpty())) {
        				 if(cigErr.length()>0)
        					 cigErr+=", ";
        				 
        				 cigErr += lotto.getFullCIG();
        			 }
        		 }
        		 if(cigErr.length()>0)
        			  mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_268+cigErr);
        	 }
         }
         //FINE TICKET ALM #11490 - 3.04.4
         
       IniziativaManager im = new IniziativaManager(connection,logger);
       String cigStr = im.checkCigIniziativaFromModificaGara(gara);
       if(!"".equals(cigStr)) {
    	 //A seconda se sia stato indicato o meno il cig, mostra un diverso errore
     	  if(gara.getCIG_ACC_QUADRO()==null || "".equals(gara.getCIG_ACC_QUADRO()))
     	     mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_267a.replace("$1", "CIG relativo all'accordo quadro/convenzione").replace("$2", cigStr));
     	  else
     		 mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_267b.replace("$1", "CIG relativo all'accordo quadro/convenzione").replace("$2", cigStr));

       }
         
		} catch (SQLException e) {
			e.printStackTrace();
		}  catch (Exception e) {
			e.printStackTrace();
		}				
	}
	
	/**
	 * @param idModo
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validita  di una tipologia a posteriori
	 * @return Boolean
	 * @throws SQLException
	 */
	private Boolean modoIndizioneValido(long idModo,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		AccessiDB adb = new AccessiDB(connection, logger);
	
		return adb.getTipologica(MODO_INDIZIONE.TABLE_NAME, MODO_INDIZIONE.ID_MODO_GARA, MODO_INDIZIONE.DESCRIZIONE,
				MODO_INDIZIONE.DATA_FINE_VALIDITA, o).containsKey(String.valueOf(idModo));
	}

	/**
	 * @param idModo
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validita' di una tipologia a posteriori
	 * @return Boolean
	 * @throws SQLException
	 */
	private Boolean modoRealValido(long idModo,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		AccessiDB adb = new AccessiDB(connection, logger);
	   
		//TICKET ALM #2847
			return adb.getTipologicaWithData(MODI_REALIZZAZIONE.TABLE_NAME, MODI_REALIZZAZIONE.ID_MODO_REAL,
					MODI_REALIZZAZIONE.DESCRIZIONE, MODI_REALIZZAZIONE.DATA_INIZIO_VALIDITA,
					MODI_REALIZZAZIONE.DATA_FINE_VALIDITA, o).containsKey(String.valueOf(idModo));

	}
	
	/** metodo per controllare che il numero dei lotti dichiarato in fase di creazione gara
	 * sia uguale al numero dei lotti da perfezionare in fase di perfezionamento / pubblicazione;
	 * solo se vengo dai WS il numeroLottiGara può essere null, allora restituisco solo un warning
	 * @param numeroLottiGara, numeroLottiDaPerfezionare
	 */
	
	public void validaNumeroLotti(Integer numeroLottiGara, int numeroLottiDaPerfezionare){
		if(numeroLottiGara==null)
			mEccezioni
					.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_208.replace("$1", "Numero dei Lotti da perfezionare")
							.replace("$2", "Numero totale dei Lotti della Gara"));
		else if(numeroLottiGara!=numeroLottiDaPerfezionare)
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_208.replace("$1", "Numero dei Lotti da perfezionare")
					.replace("$2", "Numero totale dei Lotti della Gara"));
	}

   @Override
	public boolean validaTipologica(String nomeTabella, String campoId, String campoDescrizione, String campoValidita,
			String campoFineVal, Timestamp data, Object id) {
      
		return super.validaTipologica(nomeTabella, campoId, campoDescrizione, campoValidita, campoFineVal, data, id);
   }

   /**
    * @param idMotivo
    * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validita  di una tipologia a posteriori
    * @return Boolean
    * @throws SQLException
    */
   private Boolean motivoValido(long idMotivo, Object o) throws SQLException{
      /** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
      
      AccessiDB adb = new AccessiDB(connection, logger);
   
		return adb.getTipologica(EAGG_MOTIVI.TABLE_NAME, EAGG_MOTIVI.COD_MOTIVO, EAGG_MOTIVI.DESCRIZIONE,
				EAGG_MOTIVI.DATA_INIZIO_VALIDITA, EAGG_MOTIVI.DATA_FINE_VALIDITA, PageHelper.parseTimeYMD((String) o))
				.containsKey(String.valueOf(idMotivo));
   }

   /**
    * Metodo che si occupa di validare tutte le categorie ovvero controlla l'esistenza 
    * di tutti gli id contenuti nella mappa
    * 
    * @return boolean
    */
   public boolean validaCategorie(String[] categorie, Object data){
      int local_error = 0;
      boolean valida = true;
      for (int i = 0; i < categorie.length; i++) {
			valida = validaTipologica(EAGG_CATEGORIE.TABLE_NAME, EAGG_CATEGORIE.COD_CATEGORIA,
					EAGG_CATEGORIE.DESCRIZIONE, EAGG_CATEGORIE.DATA_INIZIO_VALIDITA, EAGG_CATEGORIE.DATA_FINE_VALIDITA,
					PageHelper.parseTimeYMD((String) data), categorie[i]);
         if(!valida){ 
				mEccezioni.addValidationErrElemento(
						Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "campo Categoria merceologica"), i);
            local_error++;
         }
      }
      return local_error == 0;
   }
   
   //TICKET ALM #664
	/**
	 * @param idStrumento
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validita' di una tipologia a posteriori
	 * @return Boolean
	 * @throws SQLException
	 */
	private Boolean strumentoSvolgimentoValido(long idStrumentoSvolgimento,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		AccessiDB adb = new AccessiDB(connection, logger);
	
		return adb
				.getTipologica(STRUMENTI_SVOLGIMENTO_PROCEDURE.TABLE_NAME,
						STRUMENTI_SVOLGIMENTO_PROCEDURE.ID_SVOLGIMENTO, STRUMENTI_SVOLGIMENTO_PROCEDURE.DESCRIZIONE,
						STRUMENTI_SVOLGIMENTO_PROCEDURE.DATA_FINE_VALIDITA, o)
				.containsKey(String.valueOf(idStrumentoSvolgimento));
	}
	//FINE TICKET ALM #664
   
	   //TICKET ALM #3832
		/**
		 * @param idEstremaUrgenza
		 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validita' di una tipologia a posteriori
		 * @return Boolean
		 * @throws SQLException
		 */
		private Boolean estremaUrgenzaValido(long idEstremaUrgenza,Object o) throws SQLException{
			/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
			
			AccessiDB adb = new AccessiDB(connection, logger);
		
		return adb.getTipologica(ART_ESTREMA_URGENZA_SOMMA_URGENZA.TABLE_NAME,
				ART_ESTREMA_URGENZA_SOMMA_URGENZA.ID_ESTREMA_URGENZA, ART_ESTREMA_URGENZA_SOMMA_URGENZA.DESCRIZIONE,
				ART_ESTREMA_URGENZA_SOMMA_URGENZA.DATA_FINE_VALIDITA, o).containsKey(String.valueOf(idEstremaUrgenza));
		}
		//FINE TICKET ALM #3832
		
		
		//TICKET ALM #3834
		/**
		 * @param idModalitaIndizioneAllegatoIX
		 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validita' di una tipologia a posteriori
		 * @return Boolean
		 * @throws SQLException
		 */
		private Boolean modalitaIndizioneArtIXValido(long idModalitaIndizioneAllegatoIX,Object o) throws SQLException{
			/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
			
			AccessiDB adb = new AccessiDB(connection, logger);
		
		return adb.getTipologica(MODALITA_INDIZIONE_ALLEGATO_IX.TABLE_NAME,
				MODALITA_INDIZIONE_ALLEGATO_IX.ID_ALLEGATO_IX, MODALITA_INDIZIONE_ALLEGATO_IX.DESCRIZIONE,
				MODALITA_INDIZIONE_ALLEGATO_IX.DATA_FINE_VALIDITA, o)
				.containsKey(String.valueOf(idModalitaIndizioneAllegatoIX));
		}
		//FINE TICKET ALM #3834
		
		//TICKET ALM #659 - 3.04.4
		private Boolean funzioniDelegateValido(long idFDelegate, Object o) throws SQLException{
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
			
			AccessiDB adb = new AccessiDB(connection, logger);
		
		return adb.getTipologicaWithData(FUNZIONI_DELEGATE.TABLE_NAME, FUNZIONI_DELEGATE.ID_F_DELEGATE,
				FUNZIONI_DELEGATE.DESCRIZIONE, FUNZIONI_DELEGATE.DATA_INIZIO_VALIDITA,
				FUNZIONI_DELEGATE.DATA_FINE_VALIDITA, o).containsKey(String.valueOf(idFDelegate));
		
		}
}
