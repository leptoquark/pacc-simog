package it.avlp.simog.validatore;

import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.DittaAusiliariaManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBeanComparator;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.CondizioneAggBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.aggiudicazione.TipoFinanziamentoBean;
import it.avlp.simog.common.action.InfoComuniSharedAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.rubricamanager.RubricaManager;
import it.avlp.simog.util.SimogProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class SchedaAdesioneValidator extends SimogValidator {
	
	public SchedaAdesioneValidator(Connection connection, Logger logger) {
		super(connection, logger);
	}
	public void clearExceptions(){
		this.mEccezioni.clear();
	}
	
	
	//MAC 34266 3.04.8
	DittaAusiliariaManager dittaAusiliariaManager = new DittaAusiliariaManager(connection, logger);
	
	@Override
	public boolean valida(Object bean, String section) {
		try{
			Scheda_A saBean = (Scheda_A) bean;
			
			// PP 28.10.2013 aggiunto controllo per esistenza scheda aggiudicazione accordo
			// quadro
         AggiudicazioneBean aggiudicazioneAccQuadro = new AggiudicazioneBean();        
         AggiudicatarioManager aggMan = new AggiudicatarioManager(connection, logger);
         InfoComuniSharedAction iAction = new InfoComuniSharedAction(connection, logger);
			if (saBean.getInfoGara().getCIG_ACC_QUADRO() != null
					&& !"".equals(saBean.getInfoGara().getCIG_ACC_QUADRO().trim())) {
            //gm nuovo codice per l'aggiudicazione accordo quadro
            try{
					aggiudicazioneAccQuadro = iAction
							.getBeanAggiudicazioneAccQuadro(saBean.getInfoGara().getCIG_ACC_QUADRO());

               // controllo per verificare che l'aggiudicazione dell'accordo quadro esista
               if(aggiudicazioneAccQuadro.getIdAggiudicazione() == 0){                     
						mEccezioni.addValidationField("label_Aggiudicazione");
						mEccezioni.addValidationErr(Messaggi.SIMOG_AGGIUDICAZIONI_084.replace("$1",
								"[" + saBean.getInfoGara().getCIG_ACC_QUADRO() + "]"));
               }
				} catch (Exception e) {
					mEccezioni.addValidationField("label_Aggiudicazione");
					mEccezioni.addValidationErr(Messaggi.SIMOG_AGGIUDICAZIONI_084.replace("$1",
							"[" + saBean.getInfoGara().getCIG_ACC_QUADRO() + "]"));
            }
         }

			validaAggiudicazione(saBean.getAggiudicazione(), saBean.getCondizioni(), saBean.getTipoLavoro(),
					saBean.getTipoFS(), saBean.getInfoComuni(),
					getImportoTotaleFinanziamento(saBean.getFinanziamenti()),
					saBean.getInfoGara().getImportoLotto().floatValue());
			
			List<AggiudicatarioBean> aggiudicatariList = aggMan.loadManyByIdAggiudicazione(aggiudicazioneAccQuadro.getIdAggiudicazione(),false);

			/*** TICKET ALM - 3.04.3 */
			//Da controllare solo se non si tratta di variazione anagrafica
			if(!saBean.getAggiudicazione().isValidaVariazione())
				//MAC 34266  3.04.8 aggiunto id_aggiudicazione 
				validaAggiudicatari(saBean.getAggiudicatari(),
						saBean.getAggiudicazione().getDataInizioAggiudicazione(),aggiudicazioneAccQuadro.getIdAggiudicazione());

			
			// postvalidazione aggiudicatari, devono esistere nell'accordo quadro (a meno che non ci sia almeno un aggiudicatario di tipo Associazione di categoria)
			if(aggiudicazioneAccQuadro.getIdAggiudicazione() > 0){ 
			   
			   //TICKET ALM #11168 - 3.04.4
//			   List<AggiudicatarioBean> aggList = aggMan.loadMany(aggiudicazioneAccQuadro.getIdAggiudicazione(), aggiudicazioneAccQuadro.getDataInizioAggiudicazione(), false);
			   List<AggiudicatarioBean> aggList = aggMan.loadManyByIdInfoAgg(aggiudicazioneAccQuadro.getIdInfo());

			   
			   
			 //Verifica se tra gli aggiudicatari ce ne sia almeno uno di tipo "Associazione di categoria"
				boolean assCategoria = false;
				for(AggiudicatarioBean item : aggList) {
					if(item.getIdTipoAgg()==Costanti.TIPODITTA_ASS_CAT) {
						assCategoria=true;
						break;
					}
				}
			   if(!assCategoria) {
				   int indice = 0;
				   for(AggiudicatarioBean item : saBean.getAggiudicatari()){
				      indice ++;
		            boolean found = false;
				      for(AggiudicatarioBean quadro: aggList){
				         String statoQuadro = quadro.getSoggettoPartecipante().getId_stato();
							if (statoQuadro == null)
								statoQuadro = "";
				         String statoItem = item.getSoggettoPartecipante().getId_stato();
							if (statoItem == null)
								statoItem = "";
				         
							if (quadro.getSoggettoPartecipante().getCodiceFiscale().equalsIgnoreCase(
									item.getSoggettoPartecipante().getCodiceFiscale()) && statoQuadro.equals(statoItem)) {
				            found = true;
				         }
				      }
				      
				      if(!found)
							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_179.replace("$1", String.valueOf(indice))
									+ " dell'accordo quadro");
				      
				   }
			   }
			}
			/* FINE TICKET ALM - 3.04.3 */
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			long dataCreazioneTime=0;
			try {
				GaraManager gm = new GaraManager(connection, logger);
				Gara gara = gm.getGara(saBean.getInfoGara().getIdGara());
				dataCreazioneTime = sdf.parse(gara.getData_creazione()).getTime();
			} catch (ParseException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			
			validaResponsabili(saBean.getResponsabili(),saBean.getInfoComuni());
			validaFinanziamenti(saBean.getFinanziamenti(), saBean.getInfoComuni(),
					saBean.getAggiudicazione().getDataInizioAggiudicazione(),dataCreazioneTime);

			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() == 0;
		}catch (Exception e) {
			logger.fatal(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	/*******************************************************************************************************
	 * Validatore per i Finanziamenti della Scheda Adesione
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃ  di una tipologia a posteriori 
	 * @param finanziamenti List&lt;TipoFinanziamentoBean&gt;
	 */
	private void validaFinanziamenti(List<TipoFinanziamentoBean> finanziamenti, InfoComuniBean infoComuni, Object o,long dataCreazioneTime) {
		int i = 1;
		
// xxx: *!*!*!*! PP verificare le condizioni (chiedere ad AVCP), per ora assumo stesso comportamento di iniziolavori
if(SimogFlags.is30230_RFWEBSC02Active()){
	try{
		//deve essere valorizzato se si riferisce a lavori e settore ordinario
		// TICKET ALM #14639 - 3.04.5 controlli obbligatorieta' finanziamenti: per gare
		// create dopo la 3.04.5 e' obbligatorio indicare almeno un finanziamento
		if(dataCreazioneTime < SimogProperties.getInstance().getDataAttivazione3045Timestamp()) {
			if (isLavori(infoComuni.getTipoContratto()) && isEmptyOrZero(finanziamenti)
					&& isOrdinario(infoComuni.getFlagEnteSpeciale()))
			     throw new Exception();
		} else if (isEmptyOrZero(finanziamenti))
			    throw new Exception();
	} catch (Exception e) {
		mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "un finanziamento"),i);
	}
}

		if(!isEmptyOrZero(finanziamenti)){
		    for(TipoFinanziamentoBean fin: finanziamenti){
			//il tipo di finanziamento deve essere un valore presente in elenco
		    	try{
		            if(!isTipoFinValido(fin.getIdFinanziamento(),o))
		            	throw new Exception();
				} catch (Exception e) {
					mEccezioni.addValidationErrElemento(
							Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Tipo di finanziamento"), i);
		    	}
		    	
		    	//TICKET ALM #19520
				if(dataCreazioneTime < SimogProperties.getInstance().getDataAttivazione3045Timestamp()) {
					if (isEmptyOrZero(fin.getImporto()) && isLavori(infoComuni.getTipoContratto())
							&& isOrdinario(infoComuni.getFlagEnteSpeciale()))
						mEccezioni.addValidationErrElemento(
								Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "l'importo finanziamento"), i);
				} else if (isEmptyOrZero(fin.getImporto()))
					    mEccezioni.addValidationErrElemento(
							    Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "l'importo finanziamento"), i);
		    	
		    	//l'importo del finanziamento deve essere un numero valido
			    if(!isEmptyOrZero(fin.getImporto())) {
				    try{
					    if(!validateDecimalPart(fin.getImporto(),3))
						    throw new Exception();
				    }catch (Exception e) {
						mEccezioni.addValidationErrElemento(
								Messaggi.SIMOG_VALIDAZIONE_118.replace("$1", "Importo finanziamento"), i);
				    }
			    }
			    i++;
		    }
	    }
	}
	/****************************************************************************************************
	 * Validatore per i responsabili
	 * 
	 * @param responsabili List&lt;ResponsabileBean&gt;
	 * @param infoComuni : InfoComuniBean
	 */
	private void validaResponsabili(List<ResponsabileBean> responsabili, InfoComuniBean infoComuni) {
		int i = 1;
		if(responsabili.size()==0){
			mEccezioni.addValidationWarnElemento(Messaggi.SIMOG_VALIDAZIONE_168, i);		
		}
		for(ResponsabileBean resp: responsabili){
			
           if(resp.getSoggettoPartecipante() != null && resp.getSoggettoResponsabile() == null)
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_209
						.replace("$1", "Incaricato - Flag persona giuridica 'S' non ammesso").replace("$2", "'N'"), i);

			try{
				if(isEmptyOrZero(resp.getIdRuolo()))
					throw new Exception();
				else if(!isRuoloValido(resp.getIdRuolo(), PSBD.SEZIONE_RQ,resp.getDataInizioScheda()))
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationErrElemento(
						Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Ruolo del soggetto incaricato"), i);
			}
			i++;
		}
	}
	
	/******************************************************************************************************
	 * Validatore per gli aggiudicatari
	 * 
	 * @param aggiudicatari List&lt;AggiudicatarioBean&gt;
	 */
	//MAC 34266 3.04.8 aggiunto id_aggiudicazione
	private void validaAggiudicatari(List<AggiudicatarioBean> aggiudicatari,Object o,Long idAggiudicazione) {
		
		//MAC 34266 3.04.8
		List<DittaAusiliariaBean> listCfAusiliarie = new ArrayList<DittaAusiliariaBean>();
		if (!aggiudicatari.isEmpty()) {
			try {
				
				listCfAusiliarie = dittaAusiliariaManager.loadManyCFByAggiudicazione(idAggiudicazione);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		// fine MAC 34266  3.04.8
		
		
		int i = 1;
		TreeMap<AggiudicatarioBean, String> tm = new TreeMap<AggiudicatarioBean, String>(
				new AggiudicatarioBeanComparator());
		int i1 = 1;
		for(AggiudicatarioBean aggiud: aggiudicatari){		
			//controllo aggiudicatari duplicati al livello scheda
			if(tm.containsKey(aggiud)){
				//errore con il numero
				mEccezioni.addValidationErrElemento(
						Messaggi.SIMOG_VALIDAZIONE_187.replace("$1", "Sezione Aggiudicatari"), i);

			}else{
				tm.put(aggiud, Integer.toString(i1));
			}
			
			
			// MAC 34266  3.04.8
			
			for(DittaAusiliariaBean ausiliaria: listCfAusiliarie){	
				if ((int)aggiud.getIdAggiudicatario() == (int)ausiliaria.getIdAggiudicatario()) {
					if(ausiliaria.getSoggettoPartecipante().getCodiceFiscale() == null || ausiliaria.getSoggettoPartecipante().getCodiceFiscale().equals("")){
						if(!isEmptyOrZero(aggiud.getFlagAvvalimento()))
							mEccezioni.addValidationErrElemento(
									Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il codice fiscale ditta ausiliaria"), i);
						
					}else {
						try{ // PP il cf deve essere controllato solo per aggiudicatario italiano
							if ((isEmpty(aggiud.getSoggettoPartecipante().getId_stato()) || Costanti.CODICE_STATO_ITALIANO
									.equals(aggiud.getSoggettoPartecipante().getId_stato().trim()))
								&&  !validaPartitaIva(ausiliaria.getSoggettoPartecipante().getCodiceFiscale()) && !isCFValido(ausiliaria.getSoggettoPartecipante().getCodiceFiscale()))
								throw new Exception();
						}catch (Exception e) {
							mEccezioni.addValidationErrElemento(
									Messaggi.SIMOG_VALIDAZIONE_117.replace("$1", "Codice fiscale ditta ausiliaria"), i);
						}
					}	
				}
			}
			// fine MAC 34266 3.04.8
			
//			//da togliere dopo TEST MAC 34266 3.04.8
//			if(isEmpty(aggiud.getCfAusiliaria())){
//				if(!isEmptyOrZero(aggiud.getFlagAvvalimento()))
//					mEccezioni.addValidationErrElemento(
//							Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il codice fiscale ditta ausiliaria"), i);
//				
//			}else {
//				try{ // PP il cf deve essere controllato solo per aggiudicatario italiano
//					if ((isEmpty(aggiud.getSoggettoPartecipante().getId_stato()) || Costanti.CODICE_STATO_ITALIANO
//							.equals(aggiud.getSoggettoPartecipante().getId_stato().trim()))
//						&&  !validaPartitaIva(aggiud.getCfAusiliaria()) && !isCFValido(aggiud.getCfAusiliaria()))
//						throw new Exception();
//				}catch (Exception e) {
//					mEccezioni.addValidationErrElemento(
//							Messaggi.SIMOG_VALIDAZIONE_117.replace("$1", "Codice fiscale ditta ausiliaria"), i);
//				}
//			}	
//			//fine da togliere dopo il test
			
			try{
				if(isEmptyOrZero(aggiud.getIdTipoAgg()))
					throw new Exception();
				else if(!isTipoAggValido(aggiud.getIdTipoAgg(),o))
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
						"tipologia del soggetto aggiudicatario/affidatario"), i);
			}			
			try{
				if(Costanti.TIPODITTA_LIKE_ATI == aggiud.getIdTipoAgg() && isEmpty(aggiud.getRuolo()))
					throw new Exception();
				if(!isEmpty(aggiud.getRuolo())){
					if(!this.validaRuolo(aggiud.getRuolo())){
						mEccezioni.addValidationField("label_Ruolo");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1","campo Ruolo"));
					}
				}
			}catch (Exception e) {
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1",
						"il ruolo dell' aggiudicatario nel raggruppamento"), i);
			}
			i++;
			i1++;
		}
		if(i==1){
			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Aggiudicatario"),i);
		}		
	}
	private boolean validaRuolo(String ruolo) {
		return Costanti.MANDATARIA.equals(ruolo) || Costanti.MANDANTE.equals(ruolo);
	}
	/*****************************************************************************************************
	 * Validatore per le aggiudicazioni
	 * 
	 * @param aggiudicazione AggiudicazioniBean
	 * @param condizioni List&lt;CondizioneAggBean&gt;
	 * @param compL List&lt;TipoAppaltoAggBean&gt;
	 * @param compF List&lt;TipoAppaltoAggBean&gt;
	 * @param infoComuni InfoComuniBean
	 */
	private void validaAggiudicazione(AggiudicazioneBean aggiudicazione, List<CondizioneAggBean> condizioni,
			List<TipoAppaltoAggBean> compL, List<TipoAppaltoAggBean> compF, InfoComuniBean infoComuni,
			double importo_totale_finanziamento, float importo_lotto_CIG) {
		/******************Istat and Nuts validation: BEGIN    ***********************/
		
		// PP organi costituzionali
		//GaraManager gm = new GaraManager(connection, logger);
		boolean isOrgano = false;

		// PP B302.2.0
		if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive() && aggiudicazione.isValidaVariazione()){
			
			if(aggiudicazione.getIdMotivoVarCO() == null) {
				mEccezioni.addValidationField("label_MotivoVariazione");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Motivazione della variazione anagrafica"));
			}else{	
				try{
					if (!isMotivoVarCOValido(aggiudicazione.getIdMotivoVarCO(),
							aggiudicazione.getDataInizioAggiudicazione()))
						throw new Exception();
					else //TICKET ALM #3582 #4194
					if (SimogFlags.is3043Active()
							&& (infoComuni.getID_MODO_REAL() != Costanti.MODOREAL_CONCESSIONE_LAVORI
									&& infoComuni.getID_MODO_REAL() != Costanti.MODOREAL_FINANZA_DI_PROGETTO)
							&& aggiudicazione.getIdMotivoVarCO().equals(Costanti.MOTIVO_SOCIETA_PROGETTO)) {
				mEccezioni.addValidationField("label_MotivoVariazione");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_232.replace("$1",
								"Motivazione della variazione anagrafica "));
		}
			//FINE TICKET ALM #3582 4194
				}catch (Exception e) {
					mEccezioni.addValidationField("label_MotivoVariazione");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Motivazione della variazione anagrafica"));
				}
			}
			
		}
		

		
		// ISTAT e NUTS, entrambi valorizzati
//		if(!isEmpty(aggiudicazione.getLuogoNuts()) && !isEmpty(aggiudicazione.getLuogoIstat())){
//			mEccezioni.addValidationField("label_Istat");
//			mEccezioni.addValidationField("label_NUTS");
//			mEccezioni.addValidationErr(
//					Messaggi.SIMOG_VALIDAZIONE_141.replace("$1", "Codice ISTAT").replace("$2", "codice NUTS"));
//		}	
//		// ISTAT e NUTS, nessuno valorizzato
//		else 
			if(isEmpty(aggiudicazione.getLuogoNuts()) && isEmpty(aggiudicazione.getLuogoIstat())){
			// PP organi costituzionali
			if( SimogFlags.isOrganiCostActive() == false || isOrgano == false){
				mEccezioni.addValidationField("label_NUTS");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_109.replace("$1", "Codice ISTAT").replace("$2", "codice NUTS"));
				mEccezioni.addValidationField("label_Istat");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_109.replace("$1", "Codice NUTS").replace("$2", "codice ISTAT"));
			} else {
		    	mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "codice ISTAT"));
			    mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "codice NUTS"));  						
			}
		} else {
			// controllo validita' ISTAT se valorizzato
			if(!isEmpty(aggiudicazione.getLuogoIstat()) || (isStringEmptyValue(aggiudicazione.getLuogoIstat()))){	
				try{
					if(!isNumber(aggiudicazione.getLuogoIstat())) 
						throw new Exception();
					else if(!istatValido(aggiudicazione.getLuogoIstat(),aggiudicazione.getDataInizioAggiudicazione())) 
						throw new Exception();
				} catch (Exception e) {
					mEccezioni.addValidationField("label_Istat");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "codice ISTAT"));
				}
			}
			// controllo validita' NUTS se valorizzato
			if(!isEmpty(aggiudicazione.getLuogoNuts()) || (isStringEmptyValue(aggiudicazione.getLuogoNuts()))){
				try{
					/*if(!isNumber(aggiudicazione.getLuogoNuts())) 
						throw new Exception();
					else*/ 
					if(!nutsValido(aggiudicazione.getLuogoNuts(),aggiudicazione.getDataInizioAggiudicazione())) 
						throw new Exception();
					
				} catch (Exception e) {
					mEccezioni.addValidationField("label_NUTS");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "codice NUTS"));
				}
			}
		}
        // controllo Codice strumento di programmazione
		try{
			if (!isEmpty(aggiudicazione.getCodStrumento()) && !isStrumentoValido(aggiudicazione.getCodStrumento(),
					aggiudicazione.getDataInizioAggiudicazione()))
				throw new Exception();
		}catch (Exception e) {
			mEccezioni.addValidationField("label_StrumentoProgrammazione");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Strumento di programmazione"));
		}
		// controllo importo di adesione componente lavori
		if(!isEmpty(aggiudicazione.getImportoLavori())){
			if (!isPositive(aggiudicazione.getImportoLavori())
					|| !isNumberDecimal(aggiudicazione.getImportoLavori().toString())
					|| !validateDecimalPart(aggiudicazione.getImportoLavori(), 3)) {
				mEccezioni.addValidationField("label_ImportoLavori");
			        mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1","Componente lavori"));
		}
		}
		// controllo importo di adesione componente servizi
		if(!isEmpty(aggiudicazione.getImportoServizi())){
			if (!isPositive(aggiudicazione.getImportoServizi())
					|| !isNumberDecimal(aggiudicazione.getImportoServizi().toString())
					|| !validateDecimalPart(aggiudicazione.getImportoServizi(), 3)) {
				mEccezioni.addValidationField("label_ImportoServizi");
			        mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1","Componente servizi"));
		}
		}
		// controllo importo di adesione componente forniture
		if(!isEmpty(aggiudicazione.getImportoForniture())){
			if (!isPositive(aggiudicazione.getImportoForniture())
					|| !isNumberDecimal(aggiudicazione.getImportoForniture().toString())
					|| !validateDecimalPart(aggiudicazione.getImportoForniture(), 3)) {
				mEccezioni.addValidationField("label_ImportoForniture");
			        mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1","Componente forniture"));
		}
		}
		
		//Ticket ALM #647
		// controllo importo di adesione componente sicurezza
		if(!isEmpty(aggiudicazione.getImportoAttuazioneSicurezza())){
			if (!isPositive(aggiudicazione.getImportoAttuazioneSicurezza())
					|| !isNumberDecimal(aggiudicazione.getImportoAttuazioneSicurezza().toString())
					|| !validateDecimalPart(aggiudicazione.getImportoAttuazioneSicurezza(), 3)) {
				mEccezioni.addValidationField("label_ImportoSicurezza");
			        mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1","Importo sicurezza"));
		}
		}
		// controllo importo di adesione componente progettazione
		if(!isEmpty(aggiudicazione.getImportoProgettazione())){
			if (!isPositive(aggiudicazione.getImportoProgettazione())
					|| !isNumberDecimal(aggiudicazione.getImportoProgettazione().toString())
					|| !validateDecimalPart(aggiudicazione.getImportoProgettazione(), 3)) {
				mEccezioni.addValidationField("label_ImportoProgettazione");
			        mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1","Componente progettazione"));
		}
		}
		// controllo importo di adesione componente non assoggetata
		if(!isEmpty(aggiudicazione.getImportoNonAssog())){
			if (!isPositive(aggiudicazione.getImportoNonAssog())
					|| !isNumberDecimal(aggiudicazione.getImportoNonAssog().toString())
					|| !validateDecimalPart(aggiudicazione.getImportoNonAssog(), 3)) {
				mEccezioni.addValidationField("label_ImportoNonAssoggettato");
				mEccezioni
						.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_118.replace("$1", "Componente non assoggettata"));
			}
		}
		//Fine Ticket ALM #647
		

		   // almeno uno degli importi deve essere valorizzato
			if (isEmptyOrZero(aggiudicazione.getImportoLavori()) && isEmptyOrZero(aggiudicazione.getImportoServizi())
		         && isEmptyOrZero(aggiudicazione.getImportoForniture()) ){
				mEccezioni.addValidationField("label_ImportoLavoriServiziForniture");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_206.replace("$1", "importi lavori, servizi, forniture"));
		   }
			
			//TICKET ALM 19197
			if (isEmpty(aggiudicazione.getImportoAggiudicazione())) {
				mEccezioni.addValidationField("label_IA"); 
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_175.replace("$1","di aggiudicazione/affidamento"));
			}else{
				try{
					if(!isPositive(aggiudicazione.getImportoAggiudicazione()))
							throw new Exception();
				} catch (Exception e) {
					mEccezioni.addValidationField("label_IA"); 
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_174);
				}
			}
			//FINE TICKET ALM 19197
		
	}	

	/**
	 * @param idTipo
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃ  di una tipologia a posteriori
	 * @return boolean
	 * @throws SQLException
	 */
	private boolean isTipoAggValido(long idTipo,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		AggiudicatarioManager rman = new AggiudicatarioManager(connection, logger);
		return rman.loadTipoAggiudicatario(o).containsKey(String.valueOf(idTipo));
	}
	/**
	 * @param idTipo
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃ  di una tipologia a posteriori
	 * @return boolean
	 * @throws SQLException
	 */
	private boolean isTipoFinValido(String idTipo,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		AggiudicazioniManager rman = new AggiudicazioniManager(connection, logger);
		return rman.loadFinanziamenti(o).containsKey(String.valueOf(idTipo));
	}
	
	/**
	 * @param idStrumento
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃ  di una tipologia a posteriori
	 * @return boolean
	 * @throws SQLException
	 */
	private boolean isStrumentoValido(String idStrumento,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		AggiudicazioniManager rman = new AggiudicazioniManager(connection, logger);
		return rman.loadStrumenti(o).containsKey(String.valueOf(idStrumento));
	}
	
	/**
	 * @param codFisc String
	 * @return boolean
	 * @throws SQLException
	 */
	private boolean isCFValido(String codFisc) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		RubricaManager rman = new RubricaManager(connection, logger);
		return rman.getPartecipantiList("", codFisc, "", 0, 99).size() > 0; // True se esiste almeno un Soggetto di
																			// codice codFisc
	}

	/**
	 * verifica la validità formale del codice fiscale usa la funzione disponibile nella classe estesa
	 * @param cf String
	 * @return boolean
	 */
	public boolean validaCodiceFiscale(String cf) {
		return super.validaCodiceFiscale(cf);
	}
	
	/**
	 * calcola l'importo totale dei finanziamenti
	 * @param List<TipoFinanziamentoBean>
	 * @return double
	 */
	private double getImportoTotaleFinanziamento(List<TipoFinanziamentoBean> finanziamenti) {
		
	    double temp = 0;
	    if(!isEmpty(finanziamenti)){   
            for(TipoFinanziamentoBean fin: finanziamenti){	    
                if (!isEmpty(fin.getImporto()))
                    temp+=fin.getImporto().doubleValue();
                }
            }
        return temp;
    }
}