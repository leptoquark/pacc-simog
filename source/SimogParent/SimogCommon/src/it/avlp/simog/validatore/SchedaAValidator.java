package it.avlp.simog.validatore;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.MultilottoManager;
import it.avcp.simog.managers.aggiudicazione.RequisitiManager;
import it.avlp.simog.beans.CondizioneLottoBean;
import it.avlp.simog.beans.EsitoEnum;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBeanComparator;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.CondizioneAggBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.beans.aggiudicazione.RequisitiBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.aggiudicazione.TipoFinanziamentoBean;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.CATEGORIA_SA;
import it.avlp.simog.db.generated.CONDIZIONI;
import it.avlp.simog.db.generated.CONTRAENTE_REGIONE;
import it.avlp.simog.db.generated.MODALITA_GARA;
import it.avlp.simog.db.generated.MODI_RIAGGIUD;
import it.avlp.simog.db.generated.MODO_INDIZIONE;
import it.avlp.simog.db.generated.TIPOLOGIA_PROCEDURA;
import it.avlp.simog.db.generated.TIPOLOGIA_SA;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.rubricamanager.RubricaManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.RetroController;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.beans.InfoGaraBean;
import java.util.Date;

public class SchedaAValidator extends SimogValidator {
	
	public SchedaAValidator(Connection connection, Logger logger) {
		super(connection, logger);
		
// TODO: PP codice da finire per gestire le costanti in una tipologica esterna
//		AccessiDB adb = new AccessiDB(connection, logger);
//		try {
//			Map<String, String> costMap = adb.getTipologica("costanti", "id_costante", "codice + '|' + descrizione", "data_fine_validita", null);
//		} catch (SQLException e) {
//			logger.fatal(e.getMessage());
//			e.printStackTrace();
//			return ;
//		}
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
				
				GaraManager gm = new GaraManager(connection, logger);
				boolean isOrgano = gm.isOrganoCost(saBean.getInfoComuni().getCfAmministrazione(), 
						getDataRiferimento(saBean.getAggiudicazione().getDataInizioAggiudicazione(),
								saBean.getAggiudicazione().getDataValidatore()));
				if(section == null) 
					validaDatiComuni(saBean.getInfoComuni(),saBean.getInfoGara().getImportoLotto().floatValue());
				validaAggiudicazione(saBean.getAggiudicazione(), saBean.getCondizioni(), saBean.getTipoLavoro(),
						saBean.getTipoFS(), saBean.getInfoComuni(),
				      getImportoTotaleFinanziamento(saBean.getFinanziamenti()),
						saBean.getInfoGara().getImportoLotto().doubleValue(), saBean.getInfoGara().getIdOsservatorio(),
						saBean.getAggiudicatari(),saBean.getInfoGara());// TICKET ALM #2847 MEV 34183 3.04.8.1
				validaAggiudicatari(saBean.getAggiudicatari(),
						getDataRiferimento(saBean.getAggiudicazione().getDataInizioAggiudicazione(),
								saBean.getAggiudicazione().getDataValidatore()));
				//gm aggiunto per appalti multilotto
				validaAppaltiMultilotto(saBean.getAggiudicazione(), saBean.getAggiudicatari(),
						saBean.getInfoComuni().getIdLotto());
				validaResponsabili(saBean.getResponsabili(), saBean.getInfoComuni(),
						getDataRiferimento(saBean.getAggiudicazione().getDataInizioAggiudicazione(),
								saBean.getAggiudicazione().getDataValidatore()));
				if(!SimogFlags.isFromWeb())
				    validaProgettisti(saBean.getPrestazioni());
				validaPrestazioni(saBean.getPrestazioni(), saBean.getInfoComuni(),
						!isEmptyOrZero(saBean.getAggiudicazione().getImportoLavori()),
						getDataRiferimento(saBean.getAggiudicazione().getDataInizioAggiudicazione(),
								saBean.getAggiudicazione().getDataValidatore()));
				
				//Controlla finanziamenti e requisiti solo se non e' una riaggiudicazione
				if(saBean.getAggiudicazione() != null && saBean.getAggiudicazione().getProgCuiRiaggiudicato() <= 0){
					validaFinanziamenti(saBean.getFinanziamenti(),saBean.getInfoComuni(),
							getDataRiferimento(saBean.getAggiudicazione().getDataInizioAggiudicazione(),
									saBean.getAggiudicazione().getDataValidatore()));
//	MEV 34181 3.04.8.1				validaRequisiti(saBean.getRequisiti(),saBean.getInfoComuni(), saBean.getInfoComuni().getIdLotto(),
//							getDataRiferimento(saBean.getAggiudicazione().getDataInizioAggiudicazione(),
//									saBean.getAggiudicazione().getDataValidatore()),
//							!isEmptyOrZero(saBean.getAggiudicazione().getImportoLavori()), isOrgano);
				}
				
				 //TICKET ALM #9272 - 3.04.4
				// Controllo di data quality per le aggiudicazioni con piu' aggiudicatari: la
				// somma degli importi dei singoli aggiudicatari
				//deve essere uguale all'importo totale di aggiudicazione
				boolean verificaImportiAgg = false;
				Map<Long,AggiudicatarioBean> consorzi = new HashMap<Long,AggiudicatarioBean>();
				if(saBean.getAggiudicatari().size()>1) {
					int numAgg=0;
					for(AggiudicatarioBean ab : saBean.getAggiudicatari()) {
//						if (ab.getIdTipoAgg() == Costanti.TIPODITTA_SINGOLA || (ab.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_CONSORZIO && (ab.getRuolo()==null || ab.getRuolo().equals(Costanti.MANDATARIA)))
						if (ab.getIdTipoAgg() == Costanti.TIPODITTA_SINGOLA 
						     || ab.getIdTipoAgg() == Costanti.TIPODITTA_GEIE || ab.getIdTipoAgg() == Costanti.TIPODITTA_ASS_CAT
								|| (ab.getRuolo() != null && ab.getRuolo().equals(Costanti.MANDATARIA)))
							numAgg++;
						
						if(ab.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_CONSORZIO) {
							if(ab.getIdGruppo()==0) {
								numAgg++;
							} else if(consorzi.get(ab.getIdGruppo())==null && ab.getImpAggiudicatario()!=null) {
								numAgg++;
								consorzi.put(ab.getIdGruppo(), ab);
							}
						}
					}
					if(numAgg>1)
						verificaImportiAgg=true;
				}
				
				Gara gara = gm.getGara(saBean.getInfoGara().getIdGara());
				if(verificaImportiAgg && saBean.getAggiudicatari().size()>1) {	
						int i=1;
						BigDecimal sum = new BigDecimal(0.000);
						for(AggiudicatarioBean ab : saBean.getAggiudicatari()) {
//						if (ab.getIdTipoAgg() == Costanti.TIPODITTA_SINGOLA || (ab.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_CONSORZIO && (ab.getRuolo()==null || ab.getRuolo().equals(Costanti.MANDATARIA)))
					    if (ab.getIdTipoAgg() == Costanti.TIPODITTA_SINGOLA
							    || ab.getIdTipoAgg() == Costanti.TIPODITTA_GEIE || ab.getIdTipoAgg() == Costanti.TIPODITTA_ASS_CAT
								|| (ab.getRuolo() != null && ab.getRuolo().equals(Costanti.MANDATARIA))) {
									if(ab.getImpAggiudicatario()!=null)
									    sum = sum.add(ab.getImpAggiudicatario());
									else
										mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_254, i);
						} else if (ab.getRuolo() != null && ab.getRuolo().equals(Costanti.MANDANTE)
								&& ab.getImpAggiudicatario() != null && ab.getImpAggiudicatario().doubleValue() > 0)
                                   mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_255b, i);
						
							i++;
						}
						
						if(consorzi.size()>0) {
							Iterator<Entry<Long, AggiudicatarioBean>> iterator = consorzi.entrySet().iterator();
					        while (iterator.hasNext()) {
					        	Map.Entry pair = (Map.Entry)iterator.next();
					        	AggiudicatarioBean ab = (AggiudicatarioBean)pair.getValue();
					            if(ab.getImpAggiudicatario()!=null)
					            	sum = sum.add(ab.getImpAggiudicatario());
					        } 
						}
					
						if( saBean.getAggiudicazione().getImportoAggiudicazione() != null && 
							sum.doubleValue()!=saBean.getAggiudicazione().getImportoAggiudicazione().doubleValue()) {
							if (!SimogFlags.isAccordoQuadroOrConvenzione(gara.getID_MODO_REAL())
									&& !SimogFlags.isSvolgimentoAccordoQuadro(gara.getID_SVOLGIMENTO())) {
								    mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_253);
							} else
									mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_253);
					    }
				} else if(!verificaImportiAgg && saBean.getAggiudicatari().size()>1) {
					int i=1;
					for(AggiudicatarioBean ab : saBean.getAggiudicatari()) {
						if(ab.getImpAggiudicatario()!=null && ab.getImpAggiudicatario().doubleValue()>0)
							mEccezioni.addValidationErrElemento(
									Messaggi.SIMOG_VALIDAZIONE_255.replace("$1", "l'importo dell'aggiudicatario"), i);
						if (ab.getPercRibassoAggiudicatario() != null
								&& ab.getPercRibassoAggiudicatario().doubleValue() > 0)
							mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_255.replace("$1",
									"il campo 'Ribasso di aggiudicazione' dell'aggiudicatario"), i);
						if (ab.getPercAumentoAggiudicatario() != null
								&& ab.getPercAumentoAggiudicatario().doubleValue() > 0)
							mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_255.replace("$1",
									"il campo 'Offerta in aumento' dell'aggiudicatario"), i);
						i++;
					}
				}  else if(saBean.getAggiudicatari().size()==1 ) {
					if (saBean.getAggiudicatari().get(0).getImpAggiudicatario() != null
							&& saBean.getAggiudicatari().get(0).getImpAggiudicatario().doubleValue() > 0) {
						mEccezioni.addValidationErr(
								Messaggi.SIMOG_VALIDAZIONE_255.replace("$1", "l'importo dell'aggiudicatario"));
					}
					if (saBean.getAggiudicatari().get(0).getPercRibassoAggiudicatario() != null
							&& saBean.getAggiudicatari().get(0).getPercRibassoAggiudicatario().doubleValue() > 0) {
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_255.replace("$1",
								"il campo 'Ribasso di aggiudicazione' dell'aggiudicatario"));
					}
					if (saBean.getAggiudicatari().get(0).getPercAumentoAggiudicatario() != null
							&& saBean.getAggiudicatari().get(0).getPercAumentoAggiudicatario().doubleValue() > 0) {
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_255.replace("$1",
								"il campo 'Offerta in aumento' dell'aggiudicatario"));
					}
				} 
				//FINE TICKET ALM #9272 - 3.04.4
				
				if(!SimogProperties.getInstance().isCUPLotto(saBean.getInfoGara().getDataCreazioneGara())
				      && SimogProperties.getInstance().isCUPAttivo()){
               Lotto lotto = new Lotto();
               lotto.setElencoCup(saBean.getElencoCup());
               lotto.setFLAG_CUP(saBean.getFlagCUP());
               lotto.setTIPO_CONTRATTO_LOTTO(saBean.getInfoComuni().getTipoContratto());
               lotto.setElencoTipoAppaltoLottoL(saBean.getTipoLavoro());
               validaFlageCodiciCUP(lotto,gara.getID_MODO_REAL(),gara.getID_SVOLGIMENTO());//Ticket ALM #666
				}
			}
			return mEccezioni.getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR ).getSize() == 0;
		}catch (Exception e) {
			logger.fatal(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	private void validaProgettisti(List<ResponsabileBean> responsabili) {
		int count = 1;
		Map<Long,List<ResponsabileBean>> mappa = new HashMap<Long,List<ResponsabileBean>>();
		for(ResponsabileBean elem : responsabili) {
//			if(elem.getSezione()!=null && elem.getSezione().equals(PSBD.SEZIONE_PA)) {
								
				if(elem.isMandante() && elem.getIdGruppo()==0) {
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_275, count);
				}
				
				if(Costanti.TIPO_INCARICATO_ATI != elem.getIdRuolo() && elem.isMandante()) {
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Incaricato - Mandante"),count);
				}
				if(Costanti.TIPO_INCARICATO_ATI != elem.getIdRuolo() && elem.getIdGruppo() != 0) {
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Incaricato - ID Gruppo"),count);
				}
				
				if(elem.getIdGruppo()!=0 && mappa.get(elem.getIdGruppo()) == null) {
					List<ResponsabileBean> responsabiliGruppo = new ArrayList<ResponsabileBean>();
					for(ResponsabileBean sub : responsabili) {
						if(sub.getIdGruppo()==elem.getIdGruppo())
							responsabiliGruppo.add(sub);
					}

					
					if(responsabiliGruppo.size()==1) {
					     mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_212.replace("$1", "incaricato"), count);
					} else {
						int countMandante=0;
						for(ResponsabileBean r : responsabiliGruppo) {
							if(r.isMandante())
								countMandante++;
						}
						
						//Presente piu' di un mandantario
						if(countMandante==0 || countMandante < responsabiliGruppo.size()-1)
							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_276.replace("$1", String.valueOf(elem.getIdGruppo())));
						
						//Nessun mandatario presente
						if(countMandante==responsabiliGruppo.size())
							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_277.replace("$1", String.valueOf(elem.getIdGruppo())));

						
					}
					mappa.put(elem.getIdGruppo(), responsabiliGruppo);
				}
//			} else { 
//				if(elem.isMandante())
//				      mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Incaricato - Mandante"),count);
//
//				if(elem.getIdGruppo()!=0)
//				      mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Incaricato - ID Gruppo"),count);
//				
//			  }
			}
		}	
	
	/**************************************************************************************************
	 * Validatore per i requisiti
	 * 
	 * @param requisiti List&lt;RequisitiBean&gt;
	 * @param infoComuni InfoComuniBean
	 * @param idLotto long 
	 * @param ifLavori boolean E' true se Importo componente lavori > 0
	 */
	private void validaRequisiti(List<RequisitiBean> requisiti, InfoComuniBean infoComuni, long idLotto, Object o,
			boolean ifLavori, boolean isOrgano) {
		int i = 1;
		int counter = 0;
		//MEV 34181 3.04.8.1 
//		if(requisiti.size()==0 && isOrdinario(infoComuni.getFlagEnteSpeciale())){
//			if(ifLavori)	//Servizi e Forniture ma Importo lavori > 0
//				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_165.replace("$1", "Requisiti"));
//			else if(isLavori(infoComuni.getTipoContratto())){
//				if(isOrgano)
//					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_165.replace("$1", "Requisiti"));
//				else {
//					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_165.replace("$1", "Requisiti"));
//			}
//		}
//		}
//		for (RequisitiBean req: requisiti){
//			try{
//				if(isEmptyOrZero(req.getIdCategoria()))
//					throw new Exception();
//				else if(!isTipoCatValido(idLotto, req.getIdCategoria(),o))
//					throw new Exception();
//			}catch (Exception e) {
//				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Codice Categoria"),
//						i);
//			}
//			try{
//				if(isEmptyOrZero(req.getClasseImporto())){
//					// if(isLavori(infoComuni.getTipoContratto()))
//						throw new Exception();
////					else if(Costanti.FLAG_VALORE_SI.equals(req.getPrevalente()))
////						throw new Exception();
//				}else if(!isCIValida(req.getClasseImporto(),o))
//					throw new Exception();
//			}catch (Exception e) {
//				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Classe d'importo"),
//						i);
//			}
//			if(!isEmpty(req.getPrevalente()) && req.getPrevalente().equals(Costanti.FLAG_VALORE_SI)){
//				counter++;
//			}			
//			
//			if(isEmpty(req.getScorporabile()) || isEmpty(req.getSubAppaltabile())){
//				mEccezioni.addValidationWarnElemento(Messaggi.SIMOG_VALIDAZIONE_177.replace("$1", "Requisiti"),i);		
//			}
//			i++;
//		}
//		//se lavori ed ordinario e nessuna categoria selezionata
//		if (counter == 0 && requisiti.size() > 0 && isLavori(infoComuni.getTipoContratto())
//				&& isOrdinario(infoComuni.getFlagEnteSpeciale())) {
//			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_165.replace("$1", "Requisiti"));
//		}
//		//se lavori ed ordinario e piÃ¹ di una categoria selezionata
//		if (counter > 1 && requisiti.size() > 0 && isLavori(infoComuni.getTipoContratto())
//				&& isOrdinario(infoComuni.getFlagEnteSpeciale())) {
//			mEccezioni.addValidationErr(
//					Messaggi.SIMOG_VALIDAZIONE_169.replace("$1", "Requisiti").replace("$2", "prevalente"));
//		}
//		//se SF e piÃ¹ di una categoria selezionata
//		if(counter > 1 && requisiti.size()>0 && !isLavori(infoComuni.getTipoContratto())){
//			mEccezioni.addValidationErr(
//					Messaggi.SIMOG_VALIDAZIONE_169.replace("$1", "Requisiti").replace("$2", "prevalente"));
//		}
	}
	/*******************************************************************************************************
	 * Validatore per i Finanziamenti
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃƒÂ  di una tipologia a posteriori 
	 * @param finanziamenti List&lt;TipoFinanziamentoBean&gt;
	 */
	private void validaFinanziamenti(List<TipoFinanziamentoBean> finanziamenti, InfoComuniBean infoComuni, Object o) {
		int i = 1;
		
		LottoManager lm2 = new LottoManager(connection, logger);
		GaraManager gm = new GaraManager(connection,logger);
	      Lotto lotto2 = new Lotto();
	      Gara gara = new Gara();
	      try {
	         lotto2 = lm2.getLotto(infoComuni.getIdLotto());
	         gara = gm.getGara(lotto2.getId_Gara());
	      } catch (SQLException e1) {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
	      } catch (Exception e1) {
	         // TODO Auto-generated catch block
	         e1.printStackTrace();
	      }
			
			
			//TICKET ALM #14639 - 3.04.5
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			long dataCreazioneTime=0;
			try {
				dataCreazioneTime = sdf.parse(gara.getData_creazione()).getTime();
			} catch (ParseException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			
		
		
		try{
			//deve essere valorizzato se si riferisce a lavori e settore ordinario
			// TICKET ALM #14639 - 3.04.5 controlli obbligatorieta' finanziamenti: per gare
			// create dopo la 3.04.5 e' obbligatorio indicare almeno un finanziamento in caso di settore lavori (speciali compresi)
			if(dataCreazioneTime < SimogProperties.getInstance().getDataAttivazione3045Timestamp()) {
				if (isLavori(infoComuni.getTipoContratto()) && isEmptyOrZero(finanziamenti)
						&& isOrdinario(infoComuni.getFlagEnteSpeciale()))
				     throw new Exception();
			} else if (isEmptyOrZero(finanziamenti))
				    throw new Exception();
		} catch (Exception e) {
			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "un finanziamento"),i);
		}
		
		if(!isEmptyOrZero(finanziamenti)){
		for(TipoFinanziamentoBean fin: finanziamenti){
			try{
				if(isEmptyOrZero(fin.getIdFinanziamento()))
					throw new Exception();
				else if(!isTipoFinValido(fin.getIdFinanziamento(),o) )
					throw new Exception();
			}catch (Exception e) {
					mEccezioni.addValidationErrElemento(
							Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Tipo di finanziamento"), i);
			}
			//obbligatorio se si riferisce a lavori e settore ordinario
			//TICKET ALM #19520
			if(dataCreazioneTime < SimogProperties.getInstance().getDataAttivazione3045Timestamp()) {
				if (isEmptyOrZero(fin.getImporto()) && isLavori(infoComuni.getTipoContratto())
						&& isOrdinario(infoComuni.getFlagEnteSpeciale()))
					mEccezioni.addValidationErrElemento(
							Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "l'importo finanziamento"), i);
			} else if (isEmptyOrZero(fin.getImporto()))
				    mEccezioni.addValidationErrElemento(
						    Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "l'importo finanziamento"), i);
			//in ogni caso se valorizzato deve essere un numero decimale
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
	/*******************************************************************************************************
	 * Validatore per le prestazioni
	 * 
	 * @param prestazioni
	 * @param infoComuni
	 */
	private void validaPrestazioni(List<ResponsabileBean> prestazioni, InfoComuniBean infoComuni, boolean ifLavori,
			Object o) {
		int i = 1;
		for(ResponsabileBean resp: prestazioni){
			try{
				if(isEmptyOrZero(resp.getIdRuolo()))
					throw new Exception();
				else if(!isRuoloValido(resp.getIdRuolo(), PSBD.SEZIONE_PA,o))
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
						"Tipologia del soggetto incaricato della prestazione"), i);
			}
			
			try{
				if(!isEmptyOrZero(resp.getIdRuolo()) && resp.getIdRuolo() == Costanti.PROG_ESTERNO_SA )
					if(isEmptyOrZero(resp.getCigProgEsterna()))
						throw new Exception();
					
			}catch (Exception e) {
				mEccezioni.addValidationWarnElemento(Messaggi.SIMOG_VALIDAZIONE_116,i);
			}
			
			try{
				if(!isEmptyOrZero(resp.getIdRuolo()) && resp.getIdRuolo() == Costanti.PROG_ESTERNO_SA ){
					if(isEmptyOrZero(resp.getDataAffProgEsterna()))
						mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1",
								"affidamento incarico (per progettazione esterna)"), i);
					else if(!isDate(resp.getDataAffProgEsterna()))
						throw new Exception();
				}
			
			}catch (Exception e) {
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1",
						"Data di affidamento incarico (per progettazione esterna)"), i);
			}
			
			try{
				if(!isEmptyOrZero(resp.getIdRuolo()) && resp.getIdRuolo() == Costanti.PROG_ESTERNO_SA ){
					if(isEmptyOrZero(resp.getDataConsProgEsterna()))
						mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1",
								"consegna progetto (per progettazione esterna)"), i);
					else if(!isDate(resp.getDataConsProgEsterna()))
						throw new Exception();
				}
			
			}catch (Exception e) {
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1",
						"Data di consegna progetto (per progettazione esterna)"), i);
			}
			
			i++;
		}
		if(i==1 && ifLavori){
			mEccezioni.addValidationWarnElemento(Messaggi.SIMOG_VALIDAZIONE_167,i);
		}
	}
	/****************************************************************************************************
	 * Validatore per i responsabili
	 * 
	 * @param responsabili List&lt;ResponsabileBean&gt;
	 * @param infoComuni : InfoComuniBean
	 */
	private void validaResponsabili(List<ResponsabileBean> responsabili, InfoComuniBean infoComuni, Object o) {
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
				else if(!isRuoloValido(resp.getIdRuolo(), PSBD.SEZIONE_RA,o))
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
	private void validaAggiudicatari(List<AggiudicatarioBean> aggiudicatari,Object o) {
		
		//controllo per distinguere le righe della lista per validazione Web
		int riga = 0;
		long idGruppoPrecedente = -1;
		
		int i = 1;
		TreeMap<AggiudicatarioBean, String> tm = new TreeMap<AggiudicatarioBean, String>(
				new AggiudicatarioBeanComparator());
		int i1 = 1;
		for(AggiudicatarioBean aggiud: aggiudicatari){	
			//le righe avranno valore diverso per Web e Massloader
			if(SimogFlags.isFromWeb()){
				//se sto entrando in un nuovo gruppo (o un nuovo singolo) aggiorno la riga
				if(aggiud.getIdGruppo()!=idGruppoPrecedente || aggiud.getIdGruppo()==0)
			    	riga = riga+1;
				idGruppoPrecedente = aggiud.getIdGruppo();
			} else
				//se vengo dal massloader
				riga = i;
			
			//controllo aggiudicatari duplicati al livello scheda
			if(tm.containsKey(aggiud)){
				//errore con il numero
				mEccezioni
						.addValidationErrElemento(
								Messaggi.SIMOG_VALIDAZIONE_187
										.replace("$1",
												"Sezione Aggiudicatari" + (SimogFlags.isFromWeb()
														? ", soggetto con codice fiscale: "
																+ aggiud.getSoggettoPartecipante().getCodiceFiscale()
														: "")),
								riga);

			}else{
				tm.put(aggiud, Integer.toString(i1));
			}
			
			// controllo preventivo per il massloader, le ditte ausiliarie vanno indicate
			// nella mandataria
			// non so se Ã¨ corretto farlo
//			if(SimogFlags.isFlagNoDate() 
//			      && (String.valueOf(Costanti.TIPODITTA_LIKE_ATI).equals(aggiud.getRuolo()) || String.valueOf(Costanti.TIPODITTA_LIKE_CONSORZIO).equals(aggiud.getRuolo()))
//			      && !Costanti.MANDATARIA.equals(aggiud.getRuolo())
//			){
//			   mEccezioni.addValidationErrElemento("???", riga);
//			}
			
			//gm aggiunto per ditte ausiliarie
			if(!isEmpty(aggiud.getDitteAusiliarie()))
				validaDitteAusiliarie(riga, aggiud.getDitteAusiliarie());
			/*gm non serve piÃ¹, adesso Ã¨ gestito dalle le ditte ausiliarie
			if(isEmpty(aggiud.getCfAusiliaria())){
				if(!isEmptyOrZero(aggiud.getFlagAvvalimento()))
					mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il codice fiscale ditta ausiliaria"),i);
			*/
			//}else {
			/*
			try{ // PP il cf deve essere controllato solo per aggiudicatario italiano
				if(isEmpty(aggiud.getSoggettoPartecipante().getId_stato()) || Costanti.CODICE_STATO_ITALIANO.equals(isEmpty(aggiud.getSoggettoPartecipante().getId_stato().trim())))
					//gm non serve piÃ¹, adesso Ã¨ gestito dalle le ditte ausiliarie	
					//&&  !validaPartitaIva(aggiud.getCfAusiliaria()) && !isCFValido(aggiud.getCfAusiliaria()))
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_117.replace("$1", "Codice fiscale ditta ausiliaria"),i);
			}
			*/
			//}
			
			try{
				if(isEmptyOrZero(aggiud.getIdTipoAgg()))
					throw new Exception();
				else if(!isTipoAggValido(aggiud.getIdTipoAgg(),o))
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
						"tipologia del soggetto aggiudicatario/affidatario"), riga);
			}
			try{
				if(Costanti.TIPODITTA_LIKE_ATI == aggiud.getIdTipoAgg() && isEmpty(aggiud.getRuolo()))
					throw new Exception();
				if(!isEmpty(aggiud.getRuolo())){
					if(!this.validaRuolo(aggiud.getRuolo())){
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1","campo Ruolo"));
					}
				}
			}catch (Exception e) {
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1",
						"il ruolo dell' aggiudicatario nel raggruppamento"), riga);
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
				mEccezioni.addValidationErrElemento(
						Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "Progressivo Raggruppamento"), riga);
			validaCodiceGruppo(riga, aggiud, aggiudicatari);
			i++;
			i1++;
		}
		if(i==1){
			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Aggiudicatario"), riga);
		}
	}
	private boolean validaRuolo(String ruolo) {
		return Costanti.MANDATARIA.equals(ruolo) || Costanti.MANDANTE.equals(ruolo);
	}
	
	/*****************************************************************************************************
	 * Validatore per le ditte ausiliarie degli Aggiudicatari
	 * 
	 * @param i int 
	 * @param ditteAusiliarie List&lt;DittaAusiliariaBean&gt;
	 */
	private void validaDitteAusiliarie(int iAgg, List<DittaAusiliariaBean> ditteAusiliarie){
		int uguali = 0;
		
		for(DittaAusiliariaBean dittaBean : ditteAusiliarie){
			// gestione dell'anagrafica dei soggetti partecipanti legati alle ditte
			// ausiliarie
			try{
			    if((isEmpty(dittaBean.getSoggettoPartecipante().getId_stato()) 
			    			|| Costanti.CODICE_STATO_ITALIANO.equals(dittaBean.getSoggettoPartecipante().getId_stato()))
			    		&& !validaPartitaIva(dittaBean.getSoggettoPartecipante().getCodiceFiscale()) 
			    		// PP && !isCFValido(dittaBean.getSoggettoPartecipante().getCodiceFiscale())
						&& !validaCodiceFiscale(dittaBean.getSoggettoPartecipante().getCodiceFiscale()))
			    	throw new Exception();
			    
			} catch (Exception e) {
				mEccezioni
						.addValidationErrElemento(
								Messaggi.SIMOG_VALIDAZIONE_117
										.replace("$1",
												"Sezione Aggiudicatari - Ditte ausiliarie - Codice Fiscale "
														+ dittaBean.getSoggettoPartecipante().getCodiceFiscale()),
								iAgg);
	        }
		    if(!SimogFlags.isFromWeb()){
		    	if(dittaBean.getSoggettoPartecipante()==null)
					mEccezioni
							.addValidationErrElemento(
									Messaggi.SIMOG_VALIDAZIONE_214
											.replace("$1",
													"Sezione Aggiudicatari - Ditte ausiliarie - Codice Fiscale "
															+ dittaBean.getSoggettoPartecipante().getCodiceFiscale()),
									iAgg);
			} else {
				if (dittaBean.getSoggettoPartecipante() == null
						|| dittaBean.getSoggettoPartecipante().getIdSoggettoPartecipante() == 0
						|| dittaBean.getSoggettoPartecipante().getDataInizioSogg() == null)
					mEccezioni
							.addValidationErrElemento(
									Messaggi.SIMOG_VALIDAZIONE_214
											.replace("$1",
													"Sezione Aggiudicatari - Ditte ausiliarie - Codice Fiscale "
															+ dittaBean.getSoggettoPartecipante().getCodiceFiscale()),
									iAgg);
		    
		        List<DittaAusiliariaBean> ditteAusiliarie2 = ditteAusiliarie;
			    for(DittaAusiliariaBean dittaBean2 : ditteAusiliarie2){
					if (dittaBean.getSoggettoPartecipante().getIdSoggettoPartecipante() == dittaBean2
							.getSoggettoPartecipante().getIdSoggettoPartecipante())
					    uguali++;
		    	}
	    		//gestione dei duplicati
		    	if(uguali>1)
					mEccezioni
							.addValidationErrElemento(
									Messaggi.SIMOG_VALIDAZIONE_187.replace("$1",
											"Sezione Aggiudicatari - Ditte ausiliarie" + (SimogFlags.isFromWeb()
													? ", soggetto con codice fiscale: "
															+ dittaBean.getSoggettoPartecipante().getCodiceFiscale()
													: "")),
									iAgg);
		    	uguali = 0;
		    	
		   }
		    
		   //gestione del flag avvalimento
	       if(dittaBean.getFlagAvvalimentoDecod().equals(PSBD.NESSUNO_FLAG_AVVALIMENTO)){
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1",
						"Sezione Aggiudicatari - Ditte ausiliarie - soggetto con codice fiscale "
								+ dittaBean.getSoggettoPartecipante().getCodiceFiscale()
								+ " - Richiesta ricorso avvalimento "),
						iAgg);
	    	}

		}
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
			//gm e l'aggCorrente ha un tipo agg per il quale Ã¨ previsto il codice gruppo
			if (aggCorrente.getIdTipoAgg() == Costanti.TIPODITTA_LIKE_ATI
					|| aggCorrente.getIdTipoAgg() == Costanti.TIPODITTA_LIKE_CONSORZIO) {
		        for(AggiudicatarioBean agg:aggiudicatari){
		            //gm se l'agg della lista ha un codice gruppo e tipo agg
			      	if(!isEmpty(agg.getIdGruppo()) && agg.getIdGruppo()!=0 && !isEmpty(agg.getIdTipoAgg())){
						// gm e se l'agg della lista ha lo stesso codice gruppo e tipo agg
						// dell'aggCorrente
						if (agg.getIdGruppo() == aggCorrente.getIdGruppo()
								&& agg.getIdTipoAgg() == aggCorrente.getIdTipoAgg()) {
                    	    //gm controllo il ruolo dell'aggiudicatario
                        	if(!isEmpty(agg.getRuolo())){
            				    if(Costanti.MANDATARIA.equals(agg.getRuolo()))
            					    mandatarie++;
            				    if(Costanti.MANDANTE.equals(agg.getRuolo()))
            					    mandanti++;
            			    }
                    	    //gm se non ha un ruolo sollevo un errore
                        	/*il controllo esiste giÃ  in validaAggiudicatari
                    	    else{
                    			mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il ruolo dell'Aggiudicatario"),i);
                    	    }
                    	    */
                        }
						// gm se lo stesso codice gruppo Ã¨ usato per un tipo agg diverso sollevo un
						// errore
						else if (agg.getIdGruppo() == aggCorrente.getIdGruppo()
								&& agg.getIdTipoAgg() != aggCorrente.getIdTipoAgg()) {
							mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_187.replace("$1",
									"Il Progressivo Raggruppamento Ã¨ stato usato per tipi di aggiudicatario diversi"),
									i);
                        }
			        }
		        }
		        //gm il controllo su mandante e mandatario va fatto solo per ATI
		        if(aggCorrente.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_ATI){
		            if(mandatarie>1)
						mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_213.replace("$1", "mandatario"),
								i);
		            if(mandatarie<1)
						mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_212.replace("$1", "mandatario"),
								i);
	                if(mandanti<1)
						mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_212.replace("$1", "mandante"),
								i);
			    }
			}
			// gm altrimenti non posso inserire un codice gruppo per un tipo agg che non lo
			// prevede
			else{
				mEccezioni.addValidationErrElemento(
						Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Progressivo Raggruppamento"), i);
			}
		}
		// gm altrimenti se non ha codice gruppo ma Ã¨ un tipo agg ATI o Consorzio
		// sollevo un errore
		else{
			if (aggCorrente.getIdTipoAgg() == Costanti.TIPODITTA_LIKE_ATI
					|| aggCorrente.getIdTipoAgg() == Costanti.TIPODITTA_LIKE_CONSORZIO)
				mEccezioni.addValidationErrElemento(
						Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Progressivo Raggruppamento"), i);
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
			List<TipoAppaltoAggBean> compL, List<TipoAppaltoAggBean> compF, InfoComuniBean infoComuni,
			double importo_totale_finanziamento, double importo_lotto_CIG, String idOss,
			List<AggiudicatarioBean> aggiudicatari,InfoGaraBean infoGara) {
		
		
		//MEV 34183 3.04.8.1 INIZIO
		if (infoGara.getDataInvito() == null || infoGara.getDataInvito().equals("")) {
			aggiudicazione.setDataInvito("");
		}else if ((!aggiudicazione.getDataInvito().equals("") && aggiudicazione.getDataInvito() != null) && (infoGara.getDataInvito() != null && !infoGara.getDataInvito().equals(""))) {
			if (aggiudicazione.getDataInvito().trim().length() != 8) {
				if (!PageHelper.getViewDate(infoGara.getDataInvito()).equals(aggiudicazione.getDataInvito())) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_292.replace("$1",
							"Data invito"));
				}
			}else {
				if (!PageHelper.getViewDate(infoGara.getDataInvito()).equals(PageHelper.getViewDate(aggiudicazione.getDataInvito()))) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_292.replace("$1",
							"Data invito"));
				}
			}
		}else if (infoGara.getDataInvito() != null && !infoGara.getDataInvito().equals("")) {
			aggiudicazione.setDataInvito(PageHelper.getViewDate(infoGara.getDataInvito()));
		}
		
		
		if (infoGara.getDataScadenzaInvito() == null || infoGara.getDataScadenzaInvito().equals("")) {
			aggiudicazione.setDataScadenzaRichiestaInvito("");
		}else if ((infoGara.getDataScadenzaInvito() != null && !infoGara.getDataScadenzaInvito().equals("")) && (aggiudicazione.getDataScadenzaRichiestaInvito() != null && !aggiudicazione.getDataScadenzaRichiestaInvito().equals(""))) {
			if (aggiudicazione.getDataScadenzaRichiestaInvito().trim().length() != 8) {
				if (!PageHelper.getViewDate(infoGara.getDataScadenzaInvito()).equals(aggiudicazione.getDataScadenzaRichiestaInvito())) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_292.replace("$1",
							"Data scadenza richiesta invito"));
				}
			}else {
				if (!PageHelper.getViewDate(infoGara.getDataScadenzaInvito()).equals(PageHelper.getViewDate(aggiudicazione.getDataScadenzaRichiestaInvito()))) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_292.replace("$1",
							"Data scadenza richiesta invito"));
				}
			}
		}else if (infoGara.getDataScadenzaInvito() != null && !infoGara.getDataScadenzaInvito().equals("")) {
			aggiudicazione.setDataScadenzaRichiestaInvito(PageHelper.getViewDate(infoGara.getDataScadenzaInvito()));
		}
		
		
		if (infoGara.getDataScadenzaPagamenti() == null || infoGara.getDataScadenzaPagamenti().equals("")) {
			aggiudicazione.setDataScadenzaPresOfferta("");
		}else if ((infoGara.getDataScadenzaPagamenti() != null && !infoGara.getDataScadenzaPagamenti().equals("")) && (aggiudicazione.getDataScadenzaPresOfferta() != null && !aggiudicazione.getDataScadenzaPresOfferta().equals(""))) {
			if (aggiudicazione.getDataScadenzaPresOfferta().trim().length() != 8) {
				if (!PageHelper.getViewDate(infoGara.getDataScadenzaPagamenti()).equals(aggiudicazione.getDataScadenzaPresOfferta())) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_292.replace("$1",
							"Data scadenza presentazione offerte"));
				}
			}else {
				if (!PageHelper.getViewDate(infoGara.getDataScadenzaPagamenti()).equals(PageHelper.getViewDate(aggiudicazione.getDataScadenzaPresOfferta()))) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_292.replace("$1",
							"Data scadenza presentazione offerte"));
				}
			}
		}else if (infoGara.getDataScadenzaPagamenti() != null && !infoGara.getDataScadenzaPagamenti().equals("")) {
			aggiudicazione.setDataScadenzaPresOfferta(PageHelper.getViewDate(infoGara.getDataScadenzaPagamenti()));
		}
		
		
		//MEV 34183 3.04.8.1 FINE
				
		/******************Istat and Nuts validation: BEGIN    ***********************/

		// PP organi costituzionali
		GaraManager gm = new GaraManager(connection, logger);		
		boolean isOrgano = false;
		
		if( SimogFlags.isOrganiCostActive()){
			try {
				isOrgano = gm.isOrganoCost(infoComuni.getCfAmministrazione(), getDataRiferimento(
						aggiudicazione.getDataInizioAggiudicazione(), aggiudicazione.getDataValidatore()));
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
			
			if(aggiudicazione.getIdMotivoVarCO() == null) {
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Motivazione della variazione anagrafica"));
			}else{	
				try{
					if (!isMotivoVarCOValido(aggiudicazione.getIdMotivoVarCO(), getDataRiferimento(
							aggiudicazione.getDataInizioAggiudicazione(), aggiudicazione.getDataValidatore())))
						throw new Exception();
					else { //TICKET ALM #3582 #4194
							LottoManager lm = new LottoManager(connection,logger);
							int idModReal = 0;
							try {
								 idModReal = gm.getGara(lm.getLotto(infoComuni.getIdLotto()).getId_Gara()).getID_MODO_REAL();
							if (SimogFlags.is3043Active()
									&& (idModReal != Costanti.MODOREAL_CONCESSIONE_LAVORI
											&& idModReal != Costanti.MODOREAL_FINANZA_DI_PROGETTO)
									&& aggiudicazione.getIdMotivoVarCO().equals(Costanti.MOTIVO_SOCIETA_PROGETTO)) {
								mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_232.replace("$1",
										"Motivazione della variazione anagrafica "));
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				      }
					//FINE TICKET ALM #3582 4194
				}catch (Exception e) {
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Motivazione della variazione anagrafica"));
				}
			}
			
		}
		
		// entrambi valorizzati
//		if(!isEmpty(aggiudicazione.getLuogoNuts()) && !isEmpty(aggiudicazione.getLuogoIstat())){
//			mEccezioni.addValidationErr(
//					Messaggi.SIMOG_VALIDAZIONE_141.replace("$1", "Codice ISTAT").replace("$2", "codice NUTS"));
//		}	
//		// nessuno valorizzato
//		else 
		
		
			if(isEmpty(aggiudicazione.getLuogoNuts()) && isEmpty(aggiudicazione.getLuogoIstat())){
			// PP organi costituzionali
			if( SimogFlags.isOrganiCostActive() == false || isOrgano == false){
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_109.replace("$1", "Codice ISTAT").replace("$2", "codice NUTS"));
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_109.replace("$1", "Codice NUTS").replace("$2", "codice ISTAT"));
			} else {
		    	mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "codice ISTAT"));
			    mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "codice NUTS"));  						
			}
		} else {
			// controllo validita' se valorizzato
			if (!isEmpty(aggiudicazione.getLuogoIstat()) || (isStringEmptyValue(aggiudicazione.getLuogoIstat()))) {
				try{
					if(!isNumber(aggiudicazione.getLuogoIstat())) 
						throw new Exception();
					else if (!istatValido(aggiudicazione.getLuogoIstat(), getDataRiferimento(
							aggiudicazione.getDataInizioAggiudicazione(), aggiudicazione.getDataValidatore())))
						throw new Exception();
				} catch (Exception e) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "codice ISTAT"));
				}
			}
			// controllo validita' se valorizzato
			if(!isEmpty(aggiudicazione.getLuogoNuts()) || (isStringEmptyValue(aggiudicazione.getLuogoNuts()))){
				try{
					/*
					 * if(!isNumber(aggiudicazione.getLuogoNuts())) throw new Exception(); else
					 */
					if (!nutsValido(aggiudicazione.getLuogoNuts(), getDataRiferimento(
							aggiudicazione.getDataInizioAggiudicazione(), aggiudicazione.getDataValidatore())))
						throw new Exception();
					
				} catch (Exception e) {
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "codice NUTS"));
				}
			}
		}

		/******************Istat and Nuts validation: END    ***********************/
		
		/**********Tipologia lavoro/servizi/forniture: BEGIN    ******/
		/********Posizione 0: tipologia lavoro     Posizione 1: tipologia ******/
      //PP 20.08.2014 se competenza lotto la data di riferimento Ã¨ quella del lotto
      LottoManager lm2 = new LottoManager(connection, logger);
      Lotto lotto2 = new Lotto();
      Gara gara = new Gara();
      try {
         lotto2 = lm2.getLotto(infoComuni.getIdLotto());
         gara = gm.getGara(lotto2.getId_Gara());
         
       //MEV 37328 - 3.04.8.1 FASE 2
  		boolean isOsservCompetente = SimogProperties.getInstance().isOsservatorioRegionaleCompetente(gara.getID_OSSERVATORIO());			
  		
  		if(isOsservCompetente)
  		{
  			mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_294);
  		}
  		//FINE MEV 37328
  		
      } catch (SQLException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      } catch (Exception e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
		
		
		//TICKET ALM #14639 - 3.04.5
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		long dataCreazioneTime=0;
		try {
			dataCreazioneTime = sdf.parse(gara.getData_creazione()).getTime();
		} catch (ParseException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}		
		if(dataCreazioneTime >= SimogProperties.getInstance().getDataAttivazione3045Timestamp()) {
			if(isEmpty(aggiudicazione.getRelazioneUnica())) {
				mEccezioni.addValidationField("label_RelazioneUnica");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_125.replace("$1",
						"Il sottoscritto dichiara che questa SA ha redatto la Relazione Unica sulle Procedure di Aggiudicazione degli Appalti e che la stessa e' disponibile a richiesta"));
			} else if (!isFlag(aggiudicazione.getRelazioneUnica())) {
				mEccezioni.addValidationField("label_RelazioneUnica");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
						"Il sottoscritto dichiara che questa SA ha redatto la Relazione Unica sulle Procedure di Aggiudicazione degli Appalti e che la stessa e' disponibile a richiesta"));
			}
		} else if (aggiudicazione.getRelazioneUnica() != null && !"".equals(aggiudicazione.getRelazioneUnica())) {
			mEccezioni.addValidationField("label_RelazioneUnica");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
					"Il sottoscritto dichiara che questa SA ha redatto la Relazione Unica sulle Procedure di Aggiudicazione degli Appalti e che la stessa e' disponibile a richiesta"));
		}
		//FINE TICKET ALM #14639 - 3.04.5
		
      if(!SimogFlags.is3031_RFWEBGL02Active()
				|| (SimogFlags.is3031_RFWEBGL02Active() && !SimogProperties.getInstance().isCUPAttivo())) {
   		if(!isEmpty(aggiudicazione.getCup())){
   			try{
   				if(!validateLength(aggiudicazione.getCup(), Costanti.CUP_LENGTH))
   					throw new Exception();
				} catch (Exception e) {
   					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "codice CUP"));
   				}
   		}
		}		
		
		try{
			if(!isFlag(aggiudicazione.getFlagAccordoQuadro()))
				throw new Exception();
		} catch (Exception e) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Flag accordo quadro"));
		}
		


		try{
			//log
			if (infoGara != null && infoGara.getCig() != null) {
				logger.error("CIG ---> " + infoGara.getCig());
				logger.info("CIG ---> " + infoGara.getCig());
			}
			logger.error("compl is Empty or zero ---> " + isEmptyOrZero(compL));
			logger.error("compl size ---> " + compL.size());
			logger.error("infoComuni getTipoContratto ---> " + infoComuni.getTipoContratto());
			logger.error("aggiudicazione getImportoLavori() ---> " + aggiudicazione.getImportoLavori());
			logger.error("SimogFlags is3042Active() ---> " + SimogFlags.is3042Active());
			logger.error("compl  ---> " + compL.toString());
			logger.info("compl is Empty or zero ---> " + isEmptyOrZero(compL));
			logger.info("compl size ---> " + compL.size());
			logger.info("compl  ---> " + compL.toString());
			logger.info("infoComuni getTipoContratto ---> " + infoComuni.getTipoContratto());
			logger.info("aggiudicazione getImportoLavori() ---> " + aggiudicazione.getImportoLavori());
			logger.info("SimogFlags is3042Active() ---> " + SimogFlags.is3042Active());
			System.out.println("compl is Empty or zero ---> " + isEmptyOrZero(compL));
			System.out.println("compl size ---> " + compL.size());
			System.out.println("infoComuni getTipoContratto ---> " + infoComuni.getTipoContratto());
			System.out.println("aggiudicazione getImportoLavori() ---> " + aggiudicazione.getImportoLavori());
			System.out.println("SimogFlags is3042Active() ---> " + SimogFlags.is3042Active());
			//log
//			//da togliere
//			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Tipologia Lavoro"));
			if(isEmptyOrZero(compL)){
				logger.error("dentro if isEmptyOrZero(compL)");
				logger.info("dentro if isEmptyOrZero(compL)");
				if(isLavori(infoComuni.getTipoContratto())) {
					logger.error("dentro isLavori(infoComuni.getTipoContratto())");
					logger.info("dentro isLavori(infoComuni.getTipoContratto())");
					throw new SimogException("dummy");
				} else {
					logger.error("dentro else isLavori(infoComuni.getTipoContratto())");
					logger.info("dentro else isLavori(infoComuni.getTipoContratto())");
					logger.error("dentro else isLavori(infoComuni.getTipoContratto()) !SimogFlags.is3042Active()" + !SimogFlags.is3042Active());
					logger.info("dentro else isLavori(infoComuni.getTipoContratto()) !SimogFlags.is3042Active()" + !SimogFlags.is3042Active());
					logger.error("dentro else isLavori(infoComuni.getTipoContratto()) !isEmptyOrZero(aggiudicazione.getImportoLavori())" + !isEmptyOrZero(aggiudicazione.getImportoLavori()));
					logger.info("dentro else isLavori(infoComuni.getTipoContratto()) !isEmptyOrZero(aggiudicazione.getImportoLavori())" + !isEmptyOrZero(aggiudicazione.getImportoLavori()));
					logger.error("dentro else isLavori(infoComuni.getTipoContratto()) isPositive(aggiudicazione.getImportoLavori())" + isPositive(aggiudicazione.getImportoLavori()));
					logger.info("dentro else isLavori(infoComuni.getTipoContratto()) isPositive(aggiudicazione.getImportoLavori())" + isPositive(aggiudicazione.getImportoLavori()));
					//2.10 cambiato controllo 9.2.1.19 
					//if(!isEmptyOrZero(aggiudicazione.getImportoLavori()))
					//TICKET ALM #3713
					// Disabilitato, tramite flag, controllo bloccante tipologia lavoro per
					// contratti di servizi/forniture in caso di inserimento importo lavori
					if (!SimogFlags.is3042Active() && !isEmptyOrZero(aggiudicazione.getImportoLavori())
							&& isPositive(aggiudicazione.getImportoLavori())) {
						logger.error("dentro if is3042Active isEmptyOrZero isPositive");
						logger.info("dentro if is3042Active isEmptyOrZero isPositive");
						throw new SimogException("dummy");
					}else {
						logger.error("dentro else if is3042Active isEmptyOrZero isPositive");
						logger.info("dentro else if is3042Active isEmptyOrZero isPositive");
					}
						
				}
				     //FINE TICKET ALM #3713
				
				
			}else {		
				logger.error("dentro else isEmptyOrZero(compL)");
				logger.info("dentro else isEmptyOrZero(compL)");
				int i=1;
				for(TipoAppaltoAggBean tipoAppalto : compL ){
					logger.error("ciclo " + i);
					logger.info("ciclo " + i);
					logger.error("dentro ciclo for tipoAppalto get id appalto " + tipoAppalto.getIdAppalto());
					logger.info("dentro ciclo for tipoAppalto get id appalto " + tipoAppalto.getIdAppalto());
					logger.error("dentro ciclo for infoComuni.getIdLotto() " + infoComuni.getIdLotto());
					logger.info("dentro ciclo for infoComuni.getIdLotto() " + infoComuni.getIdLotto());
					logger.error("dentro ciclo for infoComuni.getFlagEnteSpeciale() " + infoComuni.getFlagEnteSpeciale());
					logger.info("dentro ciclo for infoComuni.getFlagEnteSpeciale() " + infoComuni.getFlagEnteSpeciale());
					logger.error("dentro ciclo for SimogFlags.is3031_RFWEBGL02Active() " + SimogFlags.is3031_RFWEBGL02Active());
					logger.info("dentro ciclo for SimogFlags.is3031_RFWEBGL02Active() " + SimogFlags.is3031_RFWEBGL02Active());
					logger.error("dentro ciclo for SimogProperties.getInstance().isCUPAttivo() " + SimogProperties.getInstance().isCUPAttivo());
					logger.info("dentro ciclo for SimogProperties.getInstance().isCUPAttivo() " + SimogProperties.getInstance().isCUPAttivo());
					logger.error("dentro ciclo for gara.getData_creazione() " + gara.getData_creazione());
					logger.info("dentro ciclo for gara.getData_creazione() " + gara.getData_creazione());
					logger.error("dentro ciclo for lotto2.getDataCreazione() " + lotto2.getDataCreazione());
					logger.info("dentro ciclo for lotto2.getDataCreazione() " + lotto2.getDataCreazione());
					logger.error("dentro ciclo for aggiudicazione.getDataInizioAggiudicazione() " + aggiudicazione.getDataInizioAggiudicazione());
					logger.info("dentro ciclo for aggiudicazione.getDataInizioAggiudicazione() " + aggiudicazione.getDataInizioAggiudicazione());
					logger.error("dentro ciclo for aggiudicazione.getDataValidatore() " + aggiudicazione.getDataValidatore());
					logger.info("dentro ciclo for aggiudicazione.getDataValidatore() " + aggiudicazione.getDataValidatore());
					if(!tipoLSFValido(tipoAppalto.getIdAppalto(), infoComuni.getIdLotto(), 
					      infoComuni.getFlagEnteSpeciale(),Costanti.TIPO_SCHEDA_LAVORI,
					      // PP 20.08.2014 se competenza lotto la data di riferimento Ã¨ quella del lotto
							SimogFlags.is3031_RFWEBGL02Active() && SimogProperties.getInstance().isCUPAttivo()
	                  && SimogProperties.getInstance().isCUPLotto(gara.getData_creazione()) 
	                  ? lotto2.getDataCreazione()
											: getDataRiferimento(aggiudicazione.getDataInizioAggiudicazione(),
													aggiudicazione.getDataValidatore()))) {
						logger.error("prima di Messaggi.SIMOG_VALIDAZIONE_104.replace(Tipologia Lavoro), i++");
						logger.info("prima di Messaggi.SIMOG_VALIDAZIONE_104.replace(Tipologia Lavoro), i++");
						mEccezioni.addValidationErrElemento(
								Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Tipologia Lavoro"), i++);
					}
						
				}
				
				if (SimogFlags.is3031_RFWEBGL02Active() && SimogProperties.getInstance().isCUPAttivo()) {
				   LottoManager lm = new LottoManager(connection, logger);
				   Lotto lotto = lm.getLotto(infoComuni.getIdLotto());
				   
				   
			  //Ticket ALM #709
		      //Verifica tipologia lavori
			   boolean found = false;
   		         for (TipoAppaltoAggBean  elem : compL){
   		            if (elem.getIdAppalto() == Costanti.TIPOAPP_MAN_ORDINARIA){
   		               found = true;
   		            } 
   		         }
				   
					// se mi ha detto SI su lotto verifico se Ã¨ lavori e non Ã¨ manutenzione
					// ordinaria, che non Ã¨ ammessa
   		      if( isYFlag(lotto.getFLAG_CUP()) 
   		            // && isLavori(lotto.getTIPO_CONTRATTO_LOTTO()) 
   		            && compL != null){
	   		    	  if (found){
							// PP 18.11.2016 infante solo warning
							// mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1","Tipologia
							// Lavoro"));
							mEccezioni.addValidationWarn(
									Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "Tipologia Lavoro"));
	   		             return;
	   		          }
					} else {
	   		         // se mi ha detto NO non ci deve mettere i cup
						// if(gara.getID_MODO_REAL()!= Costanti.MODOREAL_ACCORDO && !found &&
						// isNFlag(lotto.getFLAG_CUP()) && !isEmpty(lotto.getElencoCup()) ){
   		    	    //TICKET ALM #2847
						if (!SimogFlags.isAccordoQuadroOrConvenzione(gara.getID_MODO_REAL())
								&& !SimogFlags.isSvolgimentoAccordoQuadro(gara.getID_SVOLGIMENTO()) && !found
								&& isNFlag(lotto.getFLAG_CUP()) && !isEmpty(lotto.getElencoCup())) {
							mEccezioni.addValidationField("label_CodiceCUP");
   		    	       mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1","Codici CUP"));
	   		        	 return;
	   		         }
   		      }
   		      //Fine Ticket ALM #709
				}
			}
		} catch (SimogException e) {
			e.printStackTrace();
			mEccezioni.addValidationField("label_TipologiaLavoro");
	      mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Tipologia Lavoro"));
		} catch (Exception e) {
			mEccezioni.addValidationField("label_SchedaAggiudicazione");
         mEccezioni.addValidationErr(Messaggi.SIMOG_SQL_008.replace("$1", "validazione scheda aggiudicazione"));
         e.printStackTrace();
      }

		try{
			if(isEmptyOrZero(compF)){
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_110);
			}else {
				int i=1;
				for(TipoAppaltoAggBean tipoAppalto : compF ){
					if (!tipoLSFValido(tipoAppalto.getIdAppalto(), infoComuni.getIdLotto(),
							infoComuni.getFlagEnteSpeciale(), Costanti.TIPO_SCHEDA_FORNITURE,
			                     // PP 20.08.2014 se competenza lotto la data di riferimento Ã¨ quella del lotto
							SimogFlags.is3031_RFWEBGL02Active() && SimogProperties.getInstance().isCUPAttivo()
			                     && SimogProperties.getInstance().isCUPLotto(gara.getData_creazione()) 
			                     ? lotto2.getDataCreazione()
											: getDataRiferimento(aggiudicazione.getDataInizioAggiudicazione(),
													aggiudicazione.getDataValidatore())))
						mEccezioni.addValidationErrElemento(
								Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Modalita di acquisizione"), i++);
				}
			}
		}catch (Exception e) {
			mEccezioni.addValidationField("label_ModalitaAquisizione");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Modalita di acquisizione"));
		}
		/**********Tipologia lavoro/servizi/forniture: END    ******/
		
		/**********Prestazioni comprese nell appalto: BEGIN    ******/
		try{
			//MAC 34182 - 3.04.8.1 fase 2 aggiunto l'if se il tipoContratto Ã¨ L il tipoPrestazione Ã¨ obbligatorio
			if(infoComuni.getTipoContratto().equalsIgnoreCase("L") && isEmptyOrZero(aggiudicazione.getIdTipoPrestazione()))
				throw new Exception(); //FINE MAC 34182
			else if (!tipoPrestazioneValido(aggiudicazione.getIdTipoPrestazione(), infoComuni.getIdLotto(),
					infoComuni.getFlagEnteSpeciale(), infoComuni.getTipoContratto(), getDataRiferimento(
							aggiudicazione.getDataInizioAggiudicazione(), aggiudicazione.getDataValidatore())))
				throw new Exception();
		}catch (Exception e) {
			mEccezioni.addValidationField("label_PC");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Prestazioni comprese nell'appalto"));
		}
		/**********Prestazioni comprese nell appalto: END    ******/

		// MOD SIMOG-29 PP 14.04.2009 controllo sempre validitÃ  codice strumento su
		// tipologica
		try{
			if (!isEmpty(aggiudicazione.getCodStrumento())
					&& !isStrumentoValido(aggiudicazione.getCodStrumento(), getDataRiferimento(
							aggiudicazione.getDataInizioAggiudicazione(), aggiudicazione.getDataValidatore())))
				throw new Exception();
		}catch (Exception e) {
			mEccezioni.addValidationField("label_SP");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Strumento di programmazione"));
		}

		if(isLavori(infoComuni.getTipoContratto()) && isOrdinario(infoComuni.getFlagEnteSpeciale())){
			try{
				if(isEmpty(aggiudicazione.getCodStrumento()))
					throw new Exception();
			}catch (Exception e) {
				// if( infoComuni.getID_MODO_REAL() != Costanti.MODOREAL_ACCORDO ||
				// !SimogFlags.is3031_RFCMVL03Active() )
				//TICKET ALM #2847
				if (!SimogFlags.isAccordoQuadroOrConvenzione(infoComuni.getID_MODO_REAL())
						|| !SimogFlags.is3031_RFCMVL03Active()) {
					mEccezioni.addValidationField("label_SP");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Strumento di programmazione"));
				} else
					mEccezioni.addValidationWarn(
							Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Strumento di programmazione"));
			}
		} else {
			try {
				if(isEmpty(aggiudicazione.getCodStrumento()))
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_166);
			}	
		}
		
		if(isLavori(infoComuni.getTipoContratto())){
			if(isEmptyOrZero(aggiudicazione.getImportoLavori())){
				mEccezioni.addValidationField("label_IL");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_175.replace("$1","componente lavori"));
			}
		}
		if (isServizi(infoComuni.getTipoContratto())) {
			if(isEmptyOrZero(aggiudicazione.getImportoServizi())){
				mEccezioni.addValidationField("label_IS");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_175.replace("$1","componente servizi"));
			}
		}
		if (isForniture(infoComuni.getTipoContratto())) {
			if(isEmptyOrZero(aggiudicazione.getImportoForniture())){
				mEccezioni.addValidationField("label_IF");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_175.replace("$1","componente forniture"));
			}
		}
		if (!isEmpty(aggiudicazione.getImportoAttuazioneSicurezza())) {
			try{
				if(!isPositive(aggiudicazione.getImportoAttuazioneSicurezza()))
					throw new Exception();
				
			}catch (Exception e) {
				mEccezioni.addValidationField("label_ISic");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1",
						"l'importo totale per l'attuazione della sicurezza"));
			}
		} else
			mEccezioni.addValidationWarn(
					Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "l'importo totale per l'attuazione della sicurezza"));
		
		//2.10 aggiunto controllo 9.1.1.35
		double importo_complessivo_appalto = getImportoComplessivoAppalto(aggiudicazione.getImportoLavori(),
				aggiudicazione.getImportoServizi(), aggiudicazione.getImportoForniture(),
				aggiudicazione.getImportoAttuazioneSicurezza(), aggiudicazione.getImportoNonAssog(),
				aggiudicazione.getImportoProgettazione());
		
		// is3027_SOGLIAActive anche se non dovrebbe passarci non si sa mai, diversifico
		// il controllo perchÃ¨ potrebbe arrivare
		// qualche scheda dal massloader 
		float impMsg = Costanti.IMPORTO_LOTTO_150000;
		try{
		   if(SimogFlags.is3027_SOGLIAActive()){
		      LottoManager lm = new LottoManager(connection, logger);
		      Lotto lotto = lm.getLotto(infoComuni.getIdLotto());
	         boolean isNuovaSoglia = RetroController.is3027_SOGLIA(lotto.getData_Pubblicazione());
	         
	         // soglia portata a 40.000 esatti
	         if(SimogFlags.is30292_MAC01Active()){
   	         if(isNuovaSoglia && importo_complessivo_appalto < Costanti.IMPORTO_LOTTO_40000){
   	            impMsg = Costanti.IMPORTO_LOTTO_40000;
   	            throw new Exception();
   	         }      
				} else {
               if(isNuovaSoglia && importo_complessivo_appalto <= Costanti.IMPORTO_LOTTO_40000){
                  impMsg = Costanti.IMPORTO_LOTTO_40000;
                  throw new Exception();
               }      
            }
	          
	         if(!isNuovaSoglia && importo_complessivo_appalto < Costanti.IMPORTO_LOTTO_150000){
                 throw new Exception();
	          }   
			} else {
		      if(importo_complessivo_appalto<Costanti.IMPORTO_LOTTO_150000){
                 throw new Exception();
		      }
		   }
		} catch (Exception e) {
			mEccezioni.addValidationWarn(
					Messaggi.SIMOG_VALIDAZIONE_115.replace("$1", PageHelper.formattaImporto(new BigDecimal(impMsg))));
	    }
		try{
			if(importo_complessivo_appalto != importo_lotto_CIG)
		        throw new Exception();
		} catch (Exception e) {
			    mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_203);
		}
		try{
			if (isBiggerThanIndiceDispersione(importo_complessivo_appalto,
					getYearData(PageHelper.formatDateOrNull(aggiudicazione.getDataVerbaleAggiudicazione())),
					 infoComuni.getFlagEnteSpeciale(), infoComuni.getTipoContratto(), 
					 PageHelper.formatDateOrNull(aggiudicazione.getDataVerbaleAggiudicazione()))) {
			    throw new Exception();
		    }
		} catch (Exception e) {
		    mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_205.replace("$1", "importo complessivo appalto"));
		}
		//2.10 fine controllo 9.1.1.35
		
	
		if(!isEmpty(aggiudicazione.getImportoDisposizione())){
			
			//2.10 aggiunto controllo 9.1.1.36
			try{
				if(aggiudicazione.getImportoDisposizione().doubleValue()==0)
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationWarn(
						Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "l'importo totale delle somme a disposizione"));
		    }
			try{
				//N.B. verifico se Ã¨ negativo, isPositive verifica se n>=0
				if(!isPositive(aggiudicazione.getImportoDisposizione().doubleValue()))
					throw new Exception();
				
			} catch (Exception e) {
				mEccezioni.addValidationField("label_ISD");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "l'importo totale delle somme a disposizione"));
			}
			try{
				// verifico se contiene al massimo due cifre decimali
				if (!isNumberDecimal(aggiudicazione.getImportoDisposizione().toString())
						|| !validateDecimalPart(aggiudicazione.getImportoDisposizione(), 3))
					throw new Exception();
		
			} catch (Exception e) {
				mEccezioni.addValidationField("label_ISD");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_118.replace("$1", "importo totale delle somme a disposizione"));
			}
			//2.10 fine controllo 9.1.1.36
			
		} else {
			mEccezioni.addValidationField("label_ISD");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "l'importo totale delle somme a disposizione"));
			}
		
		//2.10 aggiunto controllo 9.1.1.37
		
		double importo_complessivo_intervento = aggiudicazione.getImportoDisposizione() == null ? 0
				: aggiudicazione.getImportoDisposizione().doubleValue() + importo_complessivo_appalto;
		try{
			if((importo_totale_finanziamento>0)&&(importo_complessivo_intervento>importo_totale_finanziamento))
				throw new Exception();
		} catch (Exception e) {
			mEccezioni.addValidationWarn(
					Messaggi.SIMOG_VALIDAZIONE_201.replace("$1", "Importo complessivo dell'intervento").replace("$2",
							"importo cumulativo dei finanziamenti"));
		}
		//2.10 fine controllo 9.1.1.37
		
		//TICKET ALM - 3.04.2 NG #3835
		// Se e' stata selezionata una scelta contraente e la gara e' pubblicata
		// successivamente l'attivazione di simog 3.04.2, mostra
		//un errore bloccante
		Lotto l = new Lotto();
	
		try {
			l = new LottoManager(connection,logger).getLotto(infoComuni.getIdLotto());

		} catch (SQLException e) {
			e.printStackTrace();
		} catch(ParseException e) {
	        e.printStackTrace();
	    } catch(Exception e) {
	        e.printStackTrace();
	    } 
		
	    dataCreazioneTime=0;
		try {
			dataCreazioneTime = sdf.parse(gara.getData_creazione().trim()).getTime();
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// Ricava la scelta contraente dall'aggiudicazione se la pubblicazione e'
		// antecedente l'attivazione di simog 3.04.2
		//Altrimenti ricavala dal lotto
		long sceltaEquiv = 0;
		if(dataCreazioneTime < SimogProperties.getInstance().getDataAttivazione3042Timestamp())
           sceltaEquiv = aggiudicazione.getIdSceltaContraente();
		else {
			try {
				sceltaEquiv = Long.parseLong(l.getId_Scelta_Contraente());
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}//FINE TICKET ALM #3835
      
      // ricavo la scelta contraente equivalente
      if(SimogFlags.is3028_RFWEBGL00Active()){
         LottoManager lm = new LottoManager(connection, logger);
         try {
        	 //TICKET ALM #3835
				// sceltaEquiv = Long.valueOf(lm.getSceltaContraenteAVCP(null,
				// aggiudicazione.getIdSceltaContraente()));
        	 sceltaEquiv = Long.valueOf(lm.getSceltaContraenteAVCP(null, sceltaEquiv));
        	 //FINE TICKET ALM #3835
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
			//TICKET ALM #3835
			// Esegui controllo solo se la data di pubblicazione sia antecedente la data di
			// attivazione di simog 3.04.2
			if(dataCreazioneTime < SimogProperties.getInstance().getDataAttivazione3042Timestamp()) {
			      
			
				if(isEmptyOrZero(aggiudicazione.getIdSceltaContraente()))
					throw new Exception();
				
				else if(!sceltaContraenteValida(aggiudicazione.getIdSceltaContraente(),
				      new Timestamp(dataCreazioneTime), isOrgano, idOss))
					throw new Exception();
				else{
	             if(SimogFlags.is3028_RFWEBGL00Active()){
						// la voce sarebbe valida ma se Ã¨ una personalizzata devo vedere se l'ambito di
						// applicazione Ã¨ coerente
	                TableBean record = gm.executeSelectWhere(CONTRAENTE_REGIONE.TABLE_NAME, 
								CONTRAENTE_REGIONE.DATA_FINE_VALIDITA, CONTRAENTE_REGIONE.ID_RECORD, null,
	                      CONTRAENTE_REGIONE.ID_SCELTA_AVCP + "="+aggiudicazione.getIdSceltaContraente()
	                       + " AND " + CONTRAENTE_REGIONE.ID_OSSERVATORIO + "=" + idOss,
	                      null);
	                if(record.getFullSize() > 0){
	                   String tipo = record.getNulledField(CONTRAENTE_REGIONE.TIPO_CONTRATTO, 0);
	                   if(tipo != null && !tipo.contains(infoComuni.getTipoContratto())){
								mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1",
										"Scelta Contraente rispetto alla tipologia di appalto"));
	                   }
	                }
	             }
				}
			} //FINE TICKET ALM #3835
		} catch (Exception e) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Procedura di scelta contraente"));
		}
		
		try{
			if(!isFlag(aggiudicazione.getAstaElettronica()))
				throw new Exception();
		} catch (Exception e) {
			mEccezioni.addValidationField("label_AE");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Ricorso all'asta elettronica"));
		}
		
		// PP controllo campo 38bis, deve essere valorizzato in caso di riaggiudicazione
		// e vuoto in caso contrario
		try{		
			if(aggiudicazione.getModalitaRiaggiudicazione()!= 0 && aggiudicazione.getProgCuiRiaggiudicato() == 0) {
				mEccezioni.addValidationField("label_MR");
				throw new Exception(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
						"Modalita' di riaggiudicazione/affidamento dell'appalto"));
			}
			// PP qui il controllo va bene sempre con la data inizio aggiudicazione
			//if(aggiudicazione.getProgCuiRiaggiudicato() > 0
			//TICKET ALM - 3.04.2 NG #2847 - modalita riaggiudicazione
			if(aggiudicazione.getProgCuiRiaggiudicato() > 0
					   && !modoRiaggiudValido(String.valueOf(aggiudicazione.getModalitaRiaggiudicazione()), 
							gara.getData_creazione())) {
				mEccezioni.addValidationField("label_MR");
				throw new Exception(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
						"Modalita' di riaggiudicazione/affidamento dell'appalto"));
			}
				
		} catch (Exception e) {
			mEccezioni.addValidationField("label_MR");
			mEccezioni.addValidationErr(e.getMessage());
		}
		
		try{
			//TICKET ALM - 3.04.2 NG #3835
			// Effettua il controllo solo se la data di creazione sia antecedente la data di
			// attivazione di simog 3.04.2
			if(dataCreazioneTime < SimogProperties.getInstance().getDataAttivazione3042Timestamp()) {
				if(Costanti.COND_SPB == sceltaEquiv || Costanti.COND_SPBG == sceltaEquiv){
					if(isEmptyOrZero(condizioni)){
						throw new Exception();
					} else {
						this.validaCondizioni(condizioni, gara.getData_creazione());
					}
				}
			}//FINE TICKET ALM #3835
			
		} catch (Exception e) {
			mEccezioni.addValidationField("label_ProceduraNegoziata");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
					"Condizioni che giustificano il ricorso alla procedura negoziata senza previa pubblicazione di un bando oppure senza previa indizione di una gara"));
		}
		
		try{
			
			//TICKET ALM #3835
			// Se la gara e' stata pubblicata in data successiva l'attivazione di simog
			// 3.04.2,
			//verifica che non siano state selezionate le condizioni
			if(dataCreazioneTime >= SimogProperties.getInstance().getDataAttivazione3042Timestamp()
				&& !isEmptyOrZero(condizioni)){
			
					throw new Exception();
			}
		
			// Se la gara di pubblicazione e' antecedente l'attivazione di simog 3.04.2,
			// procedi con il controllo
			if(dataCreazioneTime < SimogProperties.getInstance().getDataAttivazione3042Timestamp()){
				if(Costanti.COND_SPB != sceltaEquiv && Costanti.COND_SPBG != sceltaEquiv){
					if(!isEmptyOrZero(condizioni)){
						throw new Exception();
					}
				}
			}	//FINE TICKET ALM #3835
						
		} catch (Exception e) {
			mEccezioni.addValidationField("label_ProceduraNegoziata");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
					"Condizioni che giustificano il ricorso alla procedura negoziata senza previa pubblicazione di un bando oppure senza previa indizione di una gara"));
		}
		//VECCHIO 9.1.1.41
		
//		try{
//			if(isEmptyOrZero(aggiudicazione.getIdModalitaGara()))
//				throw new Exception();
//			else if(!criterioAggiudicazioneValido(aggiudicazione.getIdModalitaGara(),aggiudicazione.getDataInizioAggiudicazione()))
//				throw new Exception();
//		}catch (Exception e) {
//			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Criteri di aggiudicazione"));
//		}
		
		//FINE VECCHIO 9.1.1.41
		
		//TICKET ALM #660
		// Se la gara e' creata in data antecedente l'attivazione, esegui il precedente
		// controllo
		if(dataCreazioneTime < SimogProperties.getInstance().getDataAttivazione3042Timestamp()){
			if(!isEmpty(condizioni)){
				if(obbligoCondizioniCriteriAggiudicazione(condizioni)){
					try{
					    if(isEmptyOrZero(aggiudicazione.getIdModalitaGara()))
						    throw new Exception();
						// 'else if' Ã¨ il vecchio controllo per verificare che il valore faccia parte
						// della lista
					    else if(!criterioAggiudicazioneValido(aggiudicazione.getIdModalitaGara(),
					    		gara.getData_creazione()))
							throw new Exception();
					} catch (Exception e) {
						 mEccezioni.addValidationField("l_CA");
						mEccezioni.addValidationErr(
								Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Criteri di aggiudicazione"));
					}
				}
			}
		} else {
            try{
			    if(aggiudicazione.getIdModalitaGara() <= 0) {
			    	System.out.println("inizio check");
			    	LottoManager lm = new LottoManager(connection, logger);
			    	Lotto lotto = lm.getLotto(infoComuni.getIdLotto());
			    	String sceltaContraente = lotto.getId_Scelta_Contraente();
			    	int sceltaContraenteInt = Integer.parseInt(sceltaContraente);
			    	boolean checkCriterio = false;
					if (sceltaContraenteInt == 15 || sceltaContraenteInt == 16 || sceltaContraenteInt == 17
							|| sceltaContraenteInt == 18 || sceltaContraenteInt == 20 || sceltaContraenteInt == 31) {
			    			checkCriterio = true;
			    		}
			    	List<CondizioneLottoBean> listaCondizioni = lotto.getCondizioni();
			    	for(CondizioneLottoBean clb : listaCondizioni){
		    			long idCond = clb.getIdCondizione();
						if (idCond == 34 || idCond == 35 || idCond == 36 || idCond == 37 || idCond == 39 || idCond == 41
								|| idCond == 42 || idCond == 43 || idCond == 44)
		    				checkCriterio = true;
				    	}
			    	
			    	if(!checkCriterio)
			    		throw new Exception();
			    					    
					// 'else if' Ã¨ il vecchio controllo per verificare che il valore faccia parte
					// della lista
			    } else //TICKET ALM - 3.04.2 NG #2847
						// if(!criterioAggiudicazioneValido(aggiudicazione.getIdModalitaGara(),
						// getDataRiferimento(aggiudicazione.getDataInizioAggiudicazione(),
						// aggiudicazione.getDataValidatore())))
			    	if(!criterioAggiudicazioneValido(aggiudicazione.getIdModalitaGara(), gara.getData_creazione()))
			    	throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
				mEccezioni.addValidationField("l_CA");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Criteri di aggiudicazione"));
		    }
		}
		//2.10 fine controllo 9.1.1.41
		//FINE TICKET ALM #660
		try{
			if(!isFlag(aggiudicazione.getProceduraAcc()))
				throw new Exception();
		}catch (Exception e) {
			mEccezioni.addValidationField("label_ProceduraAccelerata");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Procedura accelerata"));
		}
		try{
			if(!isFlag(aggiudicazione.getPreinformazione()))
				throw new Exception();
		}catch (Exception e) {
			mEccezioni.addValidationField("label_Preinformazione");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Preinformazione"));
		}
		try{
			if(!isFlag(aggiudicazione.getTermineRidotto()))
				throw new Exception();
		}catch (Exception e) {
			mEccezioni.addValidationField("label_TermineRidotto");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Termine Ridotto"));
		}
		
		// modo indizione solo per settori speciali
		
		//VECCHIO 9.1.1.45
		/*
		if(isSpeciale(infoComuni.getFlagEnteSpeciale()) && isEmptyOrZero(aggiudicazione.getIdModoIndizione())){
			if(isLavori(infoComuni.getTipoContratto()))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Modalita' di indizione"));
			else
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Modalita' di indizione"));
		}
		
		if(isOrdinario(infoComuni.getFlagEnteSpeciale()) && !isEmptyOrZero(aggiudicazione.getIdModoIndizione()))
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_189.replace("$1", "Modalita' di indizione"));	

		try{
			if(!isEmptyOrZero(aggiudicazione.getIdModoIndizione()) && !modoIndizioneValido(aggiudicazione.getIdModoIndizione(),aggiudicazione.getDataInizioAggiudicazione()))
				throw new Exception();
		}catch (Exception e) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Modalita di indizione"));
		}
		*/
		//FINE VECCHIO 9.1.1.45
				
		
		//2.10 aggiunto controllo 9.1.1.45
		//TICKET ALM #2847 - controllo retrocombatibilita su Modalita indizione
		if(dataCreazioneTime < SimogProperties.getInstance().getDataAttivazione3042Timestamp()) {

			try{
				// 'if' Ã¨ il vecchio controllo per verificare che il valore faccia parte della
				// lista
				if (!isEmptyOrZero(aggiudicazione.getIdModoIndizione())
						&& !modoIndizioneValido(aggiudicazione.getIdModoIndizione(), getDataRiferimento(
								aggiudicazione.getDataInizioAggiudicazione(), aggiudicazione.getDataValidatore())))
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationField("label_IndizioneAggiudicazione");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Modalita di indizione"));
			}
			if(isEmptyOrZero(aggiudicazione.getIdModoIndizione())){
				try{
					if (isSpeciale(infoComuni.getFlagEnteSpeciale()) && sceltaEquiv != Costanti.COND_SPBG
			              && sceltaEquiv != Costanti.COND_SEL){
			        	   throw new Exception();
			        }
				} catch (Exception e) {
					mEccezioni.addValidationField("label_IndizioneAggiudicazione");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Modalita di indizione"));
				}
			}
		}
		
		//FINE TICKET ALM #4214-08.4
			//2.10 fine controllo 9.1.1.45
		
		/****************Requisiti Settori Speciali: BEGIN ************/
		//3.0 aggiunto controllo 9.1.1.46
		if(isSpeciale(infoComuni.getFlagEnteSpeciale())){
			try {
				if (isEmpty(aggiudicazione.getCriteriSelezioneStabilitiSA())
						|| isEmpty(aggiudicazione.getSistemaQualificazione()))
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationField("label_RequisitiSettoriSpeciali");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_171.replace("$1", "Requisiti Settori Speciali"));
			}
			//selezionabile solo un valore tra criteri selezione e sistema qualificazione
			try{
				if (isYFlag(aggiudicazione.getCriteriSelezioneStabilitiSA())
						&& isYFlag(aggiudicazione.getSistemaQualificazione()))
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationField("label_CriteriSelezioneStabiliti");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_208
						.replace("$1", "Criteri di selezione stabiliti dalla stazione appaltante")
						.replace("$2", "Sistema di qualificazione interno"));
			}
		} else {
			try {
				if (!isEmpty(aggiudicazione.getCriteriSelezioneStabilitiSA())
						|| !isEmpty(aggiudicazione.getSistemaQualificazione()))
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationField("label_CriteriSelezioneStabiliti");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_172.replace("$1", "Requisiti Settori Speciali"));
			}
		}	
		//3.0 fine controllo 9.1.1.46
		/****************Requesititi Settori Speciali: END 	 ************/
		
		/****************data manifestazione interesse - data richiesta invito: BEGIN ************/
		if(isEmptyOrZero(aggiudicazione.getDataManifInteresse())){
			if(isEmptyOrZero(aggiudicazione.getDataScadenzaRichiestaInvito())){
				// controllo se la modalita' di indizione gara e' settata a "Avviso periodico
				// indicativo"
				
				//TICKET ALM #4214-08.4 e 08.5
				int idModoIndizione=0;
				// Se la gara e' stata pubblicata successivamente l'attivazione, prendi la
				// modalita' indizione dal lotto. Se e' antecedente, prendi la modalita'
				// indizione dall'aggiudicazione
				if(dataCreazioneTime >= SimogProperties.getInstance().getDataAttivazione3042Timestamp()){
					try {
						Gara datiGara = new GaraManager(connection, logger).getGara(
								new LottoManager(connection, logger).getLotto(infoComuni.getIdLotto()).getId_Gara());
						idModoIndizione = datiGara.getID_MODO_GARA();
						int idStrumentoSvolgimento = datiGara.getID_SVOLGIMENTO();
						if (idModoIndizione == Costanti.AVVISO_PERIODICO_INDICATIVO
								|| idStrumentoSvolgimento == Costanti.STURMENTI_MODALITA_CARTACEA) {
							mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1",
									"scadenza per la presentazione delle manifestazioni di interesse"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					idModoIndizione=aggiudicazione.getIdModoIndizione();
					if(idModoIndizione==Costanti.AVVISO_PERIODICO_INDICATIVO){
						mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1",
								"scadenza per la presentazione delle manifestazioni di interesse"));
					}
				}

				//FINE TICKET ALM #4214-08.4 e 08.5
			}
		} else {
			try{
				if(!isDate(aggiudicazione.getDataManifInteresse()))
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationField("label_ScadenzaPresentazioneAggiudicazione");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1",
						"Scadenza per la presentazione delle manifestazioni di interesse"));
			}
			
		}
      
		//2.10 aggiunto controllo 9.1.1.62
		//TICKET ALM #3835-08.4 e 08.5
//		if(dataCreazioneTime >= SimogProperties.getInstance().getDataAttivazione3042Timestamp()){
//			
//			// Se scelta del contraente del lotto e' 2 o 9 e non e' stata indicata la data,
//			// mostrare avviso
//			if((sceltaEquiv == Costanti.PROC_RIS || sceltaEquiv == Costanti.PROC_NEG_PP)
//					&& isEmpty(aggiudicazione.getDataScadenzaRichiestaInvito()))
//				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1",
//						"scadenza per la presentazione della richiesta di invito"));
//			
//			// Se scelta del contraente del lotto e' 2 e 9 e e' stata indicata la data,
//			// mostrare errore bloccante per data non richiesta
//			if (sceltaEquiv != Costanti.PROC_RIS && sceltaEquiv != Costanti.PROC_NEG_PP
//					&& !isEmpty(aggiudicazione.getDataScadenzaRichiestaInvito())) {
//				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
//						"Scadenza per la presentazione della richiesta di invito"));
//			    mEccezioni.addValidationField("label_ScadenzaRichiesteInvitoAggiudicazione");    
//			}
//			
//		} else {
//			if (sceltaEquiv == Costanti.PROC_RIS || sceltaEquiv == Costanti.PROC_NEG_PP) {
//				try{
//				    if(isEmpty(aggiudicazione.getDataScadenzaRichiestaInvito()))
//					    throw new Exception();	
//				} catch (Exception e) {
//					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1",
//							"scadenza per la presentazione della richiesta di invito"));
//					
//			}
//				try{
//					if (!isEmpty(aggiudicazione.getDataScadenzaRichiestaInvito())
//							&& !isDate(aggiudicazione.getDataScadenzaRichiestaInvito()))
//						throw new Exception();
//				} catch (Exception e) {
//					mEccezioni.addValidationField("label_ScadenzaRichiesteInvitoAggiudicazione");  
//					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1",
//							"Scadenza per la presentazione della richiesta di invito"));
//				}
//			} else {
//				try{
//					if(!isEmpty(aggiudicazione.getDataScadenzaRichiestaInvito()))
//						throw new Exception();
//				} catch (Exception e) {
//					mEccezioni.addValidationField("label_ScadenzaRichiesteInvitoAggiudicazione");  
//					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_204.replace("$1",
//							"data di scadenza per la presentazione della richiesta di invito"));
//				}			
//			}
//		}
		//FINE TICKET ALM #3835-08.4 e 08.5
		//2.10 fine controllo 9.1.1.62
		
		/****************data manifestazione interesse - data richiesta invito: END ************/
		
		if(!isEmptyOrZero(aggiudicazione.getDataInvito())){
			try{
				if(!isDate(aggiudicazione.getDataInvito()))
					throw new Exception();
				
			}catch (Exception e) {
				mEccezioni.addValidationField("label_DataInvitoAggiudicazione");  
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1", "Data di invito"));
			}
		}
		
		if(isEmptyOrZero(aggiudicazione.getDataScadenzaPresOfferta())) {
			mEccezioni.addValidationField("label_DataScadenzaOfferteAggiudicazione"); 
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_107.replace("$1", "scadenza per la presentazione delle offerte"));
		}else{
			try{
				if(!isDate(aggiudicazione.getDataScadenzaPresOfferta()))
					throw new Exception();
				
			}catch (Exception e) {
				mEccezioni.addValidationField("label_DataScadenzaOfferteAggiudicazione"); 
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1",
						"Data di scadenza per la presentazione delle offerte"));
			}
		}
		
		if(isEmptyOrZero(aggiudicazione.getNumManifInteresse())){
			if(!isEmptyOrZero(aggiudicazione.getDataManifInteresse()))
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1",
						"il numero soggetti che hanno presentato manifestazione di interesse"));
		} else {
			try{
				if(!isPositive(aggiudicazione.getNumManifInteresse()))
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationField("label_NumSoggettiInteresseAggiudicazione"); 
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_113.replace("$1",
						"Numero soggetti che hanno presentato manifestazione di interesse"));
			}
		}
		

		//PP 06.03.09 deve accettare anche il valore zero
		
		//2.10 aggiunto controllo 9.1.1.66
		if(!isEmptyOrZero(aggiudicazione.getDataScadenzaRichiestaInvito())){
		    if(isEmpty(aggiudicazione.getNumImpreseRichiedenti()))		
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1",
						"il numero soggetti che hanno presentato richiesta di invito"));
		}
		if(!isEmpty(aggiudicazione.getNumImpreseRichiedenti())){
	    	if(!isPositive(aggiudicazione.getNumImpreseRichiedenti())) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_113.replace("$1",
						"Numero soggetti che hanno presentato richiesta di invito"));
		    	mEccezioni.addValidationField("label_NumSoggettiInvitoAggiudicazione"); 	
	    	}
		}
	    //2.10 fine controllo 9.1.1.66
		
		if(isEmptyOrZero(aggiudicazione.getNumImpreseInvitate())){
			if(!isEmptyOrZero(aggiudicazione.getDataInvito())) {
				mEccezioni.addValidationField("label_NumSoggettiPreOffertaAggiudicazione"); 
				//MAC 34197 3.04.8
				mEccezioni.addValidationWarn(
						Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "numero soggetti invitati a presentare offerta"));
			} else
				mEccezioni.addValidationWarn(
						Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "numero soggetti invitati a presentare offerta"));
		} else {
			try{
				if(!isPositive(aggiudicazione.getNumImpreseInvitate()))
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationField("label_NumSoggettiPreOffertaAggiudicazione"); 
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_113.replace("$1", "Numero soggetti invitati a presentare offerta"));
			}
		}
	
		if(isEmptyOrZero(aggiudicazione.getNumImpreseOfferenti())){
			mEccezioni.addValidationWarn(
					Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "numero soggetti che hanno presentato offerta"));
		} else {
			try{
				if(!isPositive(aggiudicazione.getNumImpreseOfferenti()))
					throw new Exception();
				if ((aggiudicazione.getNumImpreseInvitate() > 0)
						&& aggiudicazione.getNumImpreseOfferenti() > aggiudicazione.getNumImpreseInvitate())
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationField("label_NumSoggettiPreOfferta2Aggiudicazione"); 
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_113.replace("$1", "Numero soggetti che hanno presentato offerta"));
			}
		}
		
		if(isEmptyOrZero(aggiudicazione.getNumOfferteAmmesse())){
			if (SimogProperties.getInstance().isDataCreatedAfter3045(gara.getData_creazione())) {
				mEccezioni.addValidationField("l_napo"); 
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "numero soggetti ammessi a presentare un'offerta"));
			} else
				mEccezioni.addValidationWarn(
						Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "numero soggetti ammessi a presentare un'offerta"));
		} else {
			try{
				if (!isPositive(aggiudicazione.getNumOfferteAmmesse())
						|| aggiudicazione.getNumOfferteAmmesse() > aggiudicazione.getNumImpreseOfferenti())
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationField("l_napo"); 
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_113.replace("$1","Numero soggetti ammessi a presentare un'offerta"));
			}
		}
		
		// VALIDAZIONE - Percentuale max e min ribasso
				
		//2.10 aggiunto controllo 9.1.1.70
		
		//TICKET ALM #2847-08.1
		if (isOrdinario(infoComuni.getFlagEnteSpeciale())
				&& (aggiudicazione.getIdModalitaGara() == Costanti.PREZZO_BASSO
						|| aggiudicazione.getIdModalitaGara() == Costanti.CRITERIO_MINOR_PREZZO)
				&& aggiudicazione.getNumOfferteAmmesse() > 1) {
				if(isEmpty(aggiudicazione.getOffertaMassimo())) {
					mEccezioni.addValidationField("label_OfferteMaxRibassoAggiudicazione"); 
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_173.replace("$1", "Offerta di massimo ribasso"));
				}
		}
		//TICKET ALM #2847
		if(!isEmpty(aggiudicazione.getOffertaMassimo())) {
			if (!isInRange(aggiudicazione.getOffertaMassimo(), new BigDecimal(0), BigDecimal.valueOf(99.99999))
					|| !validateDecimalPart(aggiudicazione.getOffertaMassimo(), 5)) {
		    	mEccezioni.addValidationField("label_OfferteMaxRibassoAggiudicazione"); 
		    	mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "Offerta di massimo ribasso"));
			}
		}
		//2.10 fine controllo 9.1.1.70
				
		//2.10 aggiunto controllo 9.1.1.71
		//TICKET ALM #2847-08.2
		if (isOrdinario(infoComuni.getFlagEnteSpeciale())
				&& (aggiudicazione.getIdModalitaGara() == Costanti.PREZZO_BASSO
						|| aggiudicazione.getIdModalitaGara() == Costanti.CRITERIO_MINOR_PREZZO)
				&& aggiudicazione.getNumOfferteAmmesse() > 1) {
				if(isEmpty(aggiudicazione.getOffertaMinima())) {
					mEccezioni.addValidationField("label_OfferteMinRibassoAggiudicazione"); 
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_173.replace("$1", "Offerta di minimo ribasso"));
					}
			}
		if(!isEmpty(aggiudicazione.getOffertaMinima())) {
			if (!isInRange(aggiudicazione.getOffertaMinima(), new BigDecimal(0), BigDecimal.valueOf(99.99999))
					|| !validateDecimalPart(aggiudicazione.getOffertaMinima(), 5)) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "Offerta di minimo ribasso"));
				mEccezioni.addValidationField("label_OfferteMinRibassoAggiudicazione"); 
		    }
		}
		if(!isEmpty(aggiudicazione.getOffertaMinima()) && !isEmpty(aggiudicazione.getOffertaMassimo())){
			if(aggiudicazione.getOffertaMinima().doubleValue() > aggiudicazione.getOffertaMassimo().doubleValue()) {
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "Offerta di minimo ribasso"));
				mEccezioni.addValidationField("label_OfferteMinRibassoAggiudicazione"); 
			    }
			}
		//2.10 fine controllo 9.1.1.71
		
		// VALIDAZIONE - Percentuale Soglia Anomalia
		
		// TICKET ALM #2847-08.3 e 08.4 - se la data e' stata pubblicata
		// antecedentemente l'attivazione della 3042, mantieni il controllo precedente
		int maxNumOfferteAmm = 5;
		if(dataCreazioneTime < SimogProperties.getInstance().getDataAttivazione3042Timestamp()) {
			if(!isEmpty(aggiudicazione.getValSogliaAnomalia())){

				if(aggiudicazione.getIdModalitaGara()==Costanti.OFFERTA_VANTAGGIOSA) {
					mEccezioni.addValidationField("label_ValSogliaAnomaliaAggiudicazione"); 
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_170.replace("$1", "Valore soglia anomalia"));
				}else{
					try{
						if( !validatePercentual(aggiudicazione.getValSogliaAnomalia()) ) {
							throw new Exception();
						}
						//TICKET ALM #11619 - 3.04.4
						if(!Costanti.TIPO_ENTE_SPECIALE.equals(gara.getTIPO_SCHEDA_GARA()) && aggiudicazione.getNumOfferteAmmesse()>=maxNumOfferteAmm){
							if (aggiudicazione.getOffertaMinima().doubleValue() > aggiudicazione.getValSogliaAnomalia()
									.doubleValue()
									|| aggiudicazione.getOffertaMassimo().doubleValue() < aggiudicazione
											.getValSogliaAnomalia().doubleValue()) {
								throw new Exception();
							}
						}
					}

					catch (Exception e) {
						mEccezioni.addValidationField("label_ValSogliaAnomaliaAggiudicazione"); 
						mEccezioni.addValidationErr(
								Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "Val. soglia di anomalia"));
					}
				}
			}

		} else {//Altrimenti applica il nuovo controllo
			//TICKET ALM #13520 - 3.04.4.1
			//MEV 34187 3.04.8
			boolean idOssEsclusa = SimogProperties.getInstance().isOssEsclusaSogliaAnomalia(gara.getID_OSSERVATORIO());
			maxNumOfferteAmm = dataCreazioneTime < SimogProperties.getInstance().getDataSbloccaCantieriTimestamp() ? 5
					: 15;
			if (isEmpty(aggiudicazione.getValSogliaAnomalia())
					&& aggiudicazione.getIdModalitaGara() == Costanti.CRITERIO_MINOR_PREZZO
					&& aggiudicazione.getNumOfferteAmmesse() >= maxNumOfferteAmm && !idOssEsclusa) {
				mEccezioni.addValidationField("label_ValSogliaAnomaliaAggiudicazione"); 
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "il Valore soglia anomalia"));
			} else if (!isEmpty(aggiudicazione.getValSogliaAnomalia())
					&& !validatePercentual(aggiudicazione.getValSogliaAnomalia())) {
				mEccezioni.addValidationField("label_ValSogliaAnomaliaAggiudicazione"); 
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "Val. soglia di anomalia"));
			} else if (!isEmpty(aggiudicazione.getValSogliaAnomalia())
					&& aggiudicazione.getNumOfferteAmmesse() >= maxNumOfferteAmm) {
				//MAC #16550
				boolean check = false;
				if(isEmpty(aggiudicazione.getOffertaMinima())) {
					mEccezioni.addValidationField("label_OfferteMinRibassoAggiudicazione"); 
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "Offerta di minimo ribasso"));
					check = true;
				}
				 if(isEmpty(aggiudicazione.getOffertaMassimo())) {
					mEccezioni.addValidationField("label_OfferteMaxRibassoAggiudicazione"); 
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "Offerta di massimo ribasso"));
					check = true;
				}
				if (!idOssEsclusa && !check && !Costanti.TIPO_ENTE_SPECIALE.equals(gara.getTIPO_SCHEDA_GARA()) 
						&& (aggiudicazione.getOffertaMinima().doubleValue() > aggiudicazione.getValSogliaAnomalia()
								.doubleValue()
						|| aggiudicazione.getOffertaMassimo().doubleValue() < aggiudicazione.getValSogliaAnomalia()
								.doubleValue())) {
					    mEccezioni.addValidationField("label_ValSogliaAnomaliaAggiudicazione"); 
					mEccezioni
							.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "Val. soglia di anomalia"));
					}
				}
		
		}//FINE TICKET ALM #2847-08.3 e 08.4
		
		if(isEmpty(aggiudicazione.getNumOfferteFuoriSoglia())) {
			mEccezioni.addValidationField("label_NumOfferteSogliaAnomaliaAggiudicazione"); 
			mEccezioni
					.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1", "numero offerte soglia anomalia"));
		} else{
			try{
				if(!isPositive(aggiudicazione.getNumOfferteFuoriSoglia()))
					throw new Exception();
				else if(aggiudicazione.getNumOfferteFuoriSoglia() > aggiudicazione.getNumOfferteAmmesse())
					throw new Exception();
			}catch (Exception e) {
				// PATCH - VL - MESSAGGIO DI ERRORE ("NON COERENTE", E NON "NON PRESENTE")
//				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1", "Numero offerte soglia anomalia"));
				mEccezioni.addValidationField("label_NumOfferteSogliaAnomaliaAggiudicazione"); 
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "Numero offerte soglia anomalia"));
			}
		}
		
		if(!isEmpty(aggiudicazione.getNumOfferteEscluse())){
			try{
				if (!isPositive(aggiudicazione.getNumOfferteEscluse())
						|| aggiudicazione.getNumOfferteAmmesse() < aggiudicazione.getNumOfferteEscluse())
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationField("label_NumImpreseEscluseAggiudicazione"); 
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "Numero imprese escluse automaticamente"));
			}
		}
		
		// PP corretto 04.032009 controllo con campo errato
		if(!isEmpty(aggiudicazione.getNumImpEscluseInsufGiust())){
			try{
				if (!isPositive(aggiudicazione.getNumImpEscluseInsufGiust())
						|| aggiudicazione.getNumOfferteAmmesse() < aggiudicazione.getNumImpEscluseInsufGiust())
					throw new Exception();
			}catch (Exception e) {
				mEccezioni.addValidationField("label_NumImpreseEscluse2Aggiudicazione"); 
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1",
						"Numero imprese escluse per ins. giustificazioni"));
			}
		}

		//2.10 aggiunto controllo 9.1.1.84

        //Ribasso aggiudicazione 2.10 - se non valorizzato
		
		if (isEmpty(aggiudicazione.getPercRibassoAgg())) {
			//TICKET ALM #2847
			if (aggiudicazione.getIdModalitaGara() == Costanti.PREZZO_BASSO
					|| aggiudicazione.getIdModalitaGara() == Costanti.CRITERIO_MINOR_PREZZO) {
				try {
					if (isOrdinario(infoComuni.getFlagEnteSpeciale()))
						throw new Exception();
					if (isSpeciale(infoComuni.getFlagEnteSpeciale())) {
						if (isLavori(infoComuni.getTipoContratto())) {
							if (importo_complessivo_appalto == Costanti.IMPORTO_FUORI_SCALA
									|| importo_complessivo_appalto >  Costanti.IMPORTO_LOTTO_4845000) //Costanti.IMPORTO_LOTTO_5150000)
								throw new Exception();
						}
						if ((isServizi(infoComuni.getTipoContratto()) || isForniture(infoComuni.getTipoContratto()))) {
							if (importo_complessivo_appalto == Costanti.IMPORTO_FUORI_SCALA
									|| importo_complessivo_appalto > Costanti.IMPORTO_LOTTO_387000) //Costanti.IMPORTO_412000)
								throw new Exception();
						}
					}
				} catch (Exception e) {
					mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_202);
				}
			}
		} else {
		try{
     			if(!validateDecimalPart(aggiudicazione.getPercRibassoAgg(),5))
     				throw new Exception();
    	    	if(!isInRange(aggiudicazione.getPercRibassoAgg(), new BigDecimal(0), BigDecimal.valueOf(99.99999)))
     				throw new Exception();
			} catch (Exception e) {
    	    	mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "ribasso aggiudicazione"));
			}
			}

			if(!isEmptyOrZero(aggiudicazione.getPercRibassoAgg()) && !isEmptyOrZero(aggiudicazione.getPercOffAumento())){
				mEccezioni.addValidationField("label_RibassoAggiudicazione"); 
				mEccezioni.addValidationField("label_OffertaAumentoAggiudicazione"); 
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_141.replace("$1", "Ribasso aggiudicazione")
					.replace("$2", "Offerta in aumento"));
				}
				
			//MAC 26172
			//#ticket 31725 controllo singolo aggiudicatario, anche se c'Ã¨ un raggrupamento
			boolean isOneAggiugicatario = aggiudicatari != null && aggiudicatari.size() == 1;
			if(!isOneAggiugicatario && aggiudicatari != null && aggiudicatari.size() > 1) {
				long tempIdGruppo = 0;
				for(int i = 0;aggiudicatari.size() > i;i++) {
					if(i == 0){
						tempIdGruppo = aggiudicatari.get(i).getIdGruppo();
					}else if(tempIdGruppo != aggiudicatari.get(i).getIdGruppo()){
						isOneAggiugicatario = false;
						break;
					}
					
					if(i == aggiudicatari.size()-1 && tempIdGruppo != 0) {
						isOneAggiugicatario = true;
					}
				}
			}
			
			if(isOneAggiugicatario && isEmpty(aggiudicazione.getPercRibassoAgg()) && isEmpty(aggiudicazione.getPercOffAumento())) {
				mEccezioni.addValidationField("label_RibassoAggiudicazione"); 
				mEccezioni.addValidationField("label_OffertaAumentoAggiudicazione"); 
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_280.replace("$1", "Ribasso aggiudicazione")
						.replace("$2", "Offerta in aumento"));
			}
		//2.10 fine controllo 9.1.1.84
		
		//2.10 aggiunto controllo 9.1.1.85
		/*
		if(isEmpty(aggiudicazione.getPercOffAumento())){		
			if(aggiudicazione.getIdModalitaGara()==Costanti.PREZZO_BASSO){
			    try{
    				if(isOrdinario(infoComuni.getFlagEnteSpeciale()) || 
    					(isSpeciale(infoComuni.getFlagEnteSpeciale()) && 
    					aggiudicazione.getImportoLavori().doubleValue()>Costanti.IMPORTO_LOTTO_5150000 && 
    					(aggiudicazione.getImportoServizi().doubleValue()+aggiudicazione.getImportoForniture().doubleValue())>Costanti.IMPORTO_LOTTO_412000)){
	    				throw new Exception();
			}
				}
				catch (Exception e){
		    		mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_202);
				}		
			}
		}
		else{
		*/
		if(!isEmpty(aggiudicazione.getPercOffAumento())){
    		if(!isPositive(aggiudicazione.getPercOffAumento())) {
    			mEccezioni.addValidationField("label_OffertaAumentoAggiudicazione"); 
	    		mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "offerta in aumento"));
    		}
		    if(!validateDecimalPart(aggiudicazione.getPercOffAumento(),5)) {
		    	mEccezioni.addValidationField("label_OffertaAumentoAggiudicazione"); 
		    	mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "offerta in aumento"));
		    }
		}
		
		//2.10 fine controllo 9.1.1.85
		
		//2.10 aggiunto controllo 9.1.1.86
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
				
				//TICKET ALM #13697 - 3.04.5
				if (SimogProperties.getInstance().isDataCreatedAfter3045(gara.getData_creazione())) {
					if (aggiudicazione.getProgCuiRiaggiudicato() > 0 && aggiudicazione.getImportoAggiudicazione() != null
							&& aggiudicazione.getImportoAggiudicazione().doubleValue() > l.getImporto_Lotto().doubleValue()) {
						       mEccezioni.addValidationField("label_IA");
						       mEccezioni.addValidationErr(
								Messaggi.SIMOG_VALIDAZIONE_272.replace("$1", "Importo di aggiudicazione/affidamento"));
					}
				}
				
			try{
				if(aggiudicazione.getImportoAggiudicazione().doubleValue()>importo_complessivo_appalto){
				    if (isOrdinario(infoComuni.getFlagEnteSpeciale()))
							throw new Exception();
					}
			} catch (Exception e) {
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_174);
			}
			try{
				if(isBiggerThanIndiceDispersione(aggiudicazione.getImportoAggiudicazione().doubleValue(),
						getYearData(PageHelper.formatDateOrNull(aggiudicazione.getDataVerbaleAggiudicazione())), 
						infoComuni.getFlagEnteSpeciale(), infoComuni.getTipoContratto(),
						PageHelper.formatDateOrNull(aggiudicazione.getDataVerbaleAggiudicazione()))){
				    throw new Exception();
			    }
			} catch (Exception e) {
				mEccezioni.addValidationWarn(
						Messaggi.SIMOG_VALIDAZIONE_205.replace("$1", "importo di aggiudicazione/affidamento"));
		}
			
			//COMMENTATO PER TICKET #19209
//			try {//TICKET ALM #13518 - 3.04.5
//				if(aggiudicazione.getImportoAggiudicazione().doubleValue()>importo_lotto_CIG){
//					if (SimogFlags.isAccordoQuadroOrConvenzione(gara.getID_MODO_REAL())
//							|| SimogFlags.isSvolgimentoAccordoQuadro(gara.getID_SVOLGIMENTO()))
//							throw new Exception();
//					}
//			}catch (Exception e) {
//				mEccezioni.addValidationErr(
//						Messaggi.SIMOG_VALIDAZIONE_270.replace("$1", "importo di aggiudicazione/affidamento"));
//			}
			
		}
		//2.10 fine controllo 9.1.1.86
		
		//2.10 aggiunto controllo 9.1.1.87
		if(isEmptyOrZero(aggiudicazione.getDataVerbaleAggiudicazione())){
			// controllo se la modalit&agrave di indizione della gara &egrave "Avviso
			// periodico indicativo"
			mEccezioni.addValidationField("l_DAD"); 
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_107.replace("$1",
					"aggiudicazione definitiva o definizione della procedura negoziata"));
		} else {
			try{
				if(!isDate(aggiudicazione.getDataVerbaleAggiudicazione()))
					throw new Exception();
			} catch (Exception e) {
				mEccezioni.addValidationField("l_DAD"); 
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_106.replace("$1",
						"Data di aggiudicazione definitiva o definizione della procedura negoziata"));
			}
			try{
				if(!isInDefinedRange(aggiudicazione.getDataVerbaleAggiudicazione()))
					throw new Exception();
			} catch (Exception e) {
//				 PP 20110919 passa a warning Piccinini   mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_161.replace("$1", "Data di aggiudicazione definitiva o definizione della procedura negoziata"));
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_161.replace("$1",
						"Data di aggiudicazione definitiva o definizione della procedura negoziata"));
			}
			try{
				if (!isEmpty(aggiudicazione.getDataScadenzaPresOfferta())
						&& isDate(aggiudicazione.getDataScadenzaPresOfferta())) {
					if (isDateLower(aggiudicazione.getDataVerbaleAggiudicazione(),
							aggiudicazione.getDataScadenzaPresOfferta()))
			            throw new Exception();
				}
			} catch (Exception e) {
				mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_155.replace("$1", "Data di aggiudicazione")
						.replace("$2", "scadenza della presentazione delle offerte"));
			}
			
		}
		//2.10 fine controllo 9.1.1.87
	
		try{
			if(!isFlag(aggiudicazione.getFlagRichSubappalto()))
				throw new Exception();
		} catch (Exception e) {
			mEccezioni.addValidationField("label_RichiestaSubappalto");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Richiesta di subappaltare parte delle prestazioni"));
		}
		
		/*gm nuovo codice 3.0 per il campo opere urbanizzazione*/
		//Il campo Opere di urbanizzazione deve essere valorizzato con "SI" o "NO"
		if(isLavori(infoComuni.getTipoContratto())){
			
			if (SimogProperties.getInstance().isDataCreatedAfter3045(gara.getData_creazione()) && isEmpty(aggiudicazione.getOpereUrbanizzazione())) {
				mEccezioni.addValidationField("label_OpereUrbanizzazione");
    			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1","Opere di urbanizzazione"));	
			}
			
			if (!isEmpty(aggiudicazione.getOpereUrbanizzazione()) && !isFlag(aggiudicazione.getOpereUrbanizzazione())) {
				mEccezioni.addValidationField("label_OpereUrbanizzazione");
    			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1","Opere di urbanizzazione"));	
		     }
		}
		
		//TICKET ALM #2847
			validaPercentualiAggiudicatari(aggiudicatari, aggiudicazione.getIdModalitaGara(), infoComuni,
					importo_complessivo_appalto);
		
	}
			
		/*
			
		//Il campo Durata della convenzione se valorizzato deve essere numerico
		//il campo inoltre deve essere valorizzato se il flag del campo "Il campo Ã¨ un accordo quadro" Ã¨ settato a SI 
		//oppure se la "Tipologia procedura" della scheda DatiComuni vale "Accordo quadro" o "Convenzione" 
		if(isEmptyOrZero(aggiudicazione.getDurataConvenzione())){
			if( (isYFlag(aggiudicazione.getFlagAccordoQuadro())) ){
				// FIXMAH: Controlli da aggiungere dopo l'inserimento del nuovo campo dei dati comuni "Tipologia Procedura 7bis" con la relativa tabella tipologica
				//	|| (infoComuni.getTipologiaPROCEDURA()==1 AccordoQuadro)
				//  || (infoComuni.getTipologiaPROCEDURA()==2 Convenzione)  )
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_111.replace("$1","Durata della convenzione o accordo quadro in giorni"));	
			}		
		}
		else{
			if(!isNumber(""+aggiudicazione.getDurataConvenzione())){
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_113.replace("$1","Durata della convenzione o accordo quadro in giorni"));
			}
	}	
		
	
		//Il campo Opere di urbanizzazione deve essere valorizzato con "SI" o "NO"
		if(!isFlag(aggiudicazione.getOpereUrbanizzazione()))
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1","Opere di urbanizzazione"));	

		*/
		
	
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
		
		//MAC 36769 3.04.8.1
		InfoComuniManager infoComuniManager = new InfoComuniManager(connection, logger);
		List<InfoComuniBean> datiComuniFromDB = null;
		try {
			datiComuniFromDB = infoComuniManager.getInfoComuniByIdLotto(infoComuni.getIdLotto());
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//MAC 36769 3.04.8.1
				
		String dataCreazioneGara="";
		
		if(SimogFlags.is3043Active()) {
			LottoManager lm = new LottoManager(connection,logger);
			GaraManager gm = new GaraManager(connection,logger);
			AccessiDB adb = new AccessiDB(connection,logger);
			try {
				dataCreazioneGara = gm.getGara(lm.getLotto(infoComuni.getIdLotto()).getId_Gara()).getData_creazione()
						.trim();
				//MAC 34161 3.04.8.1
				//andiamo a prendere la Data Scadenza Pagamenti da DB e la data attuale 
				String dataScadenzaPagamenti = lm.getLotto(infoComuni.getIdLotto()).getDATA_SCADENZA_PAGAMENTI();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				if (dataScadenzaPagamenti != null && !dataScadenzaPagamenti.equals("")) {
					Date parsedDate = dateFormat.parse(dataScadenzaPagamenti);
					Timestamp dataScadenzaPagamentiStamp = new java.sql.Timestamp(parsedDate.getTime());
					Timestamp dataAttuale =  adb.getNow();
					//dati comuni non puo' essere creata prima della data scadenza pagamenti				
					//if (dataAttuale.compareTo(dataScadenzaPagamentiStamp)<=0 || (infoComuni.getDataInizioInfo()!=null &&infoComuni.getDataInizioInfo().compareTo(dataScadenzaPagamentiStamp)<=0)) {
					if (dataScadenzaPagamentiStamp!= null && dataAttuale.compareTo(dataScadenzaPagamentiStamp)<=0) {
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_293);
					}
				}
				
				//FINE MAC 34161
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		if (isEmptyOrZero(infoComuni.getTipoContratto())) {
			mEccezioni.addValidationField("label_TipoContratto");
			mEccezioni
					.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Oggetto principale del contratto"));
		}
		if (isEmptyOrZero(infoComuni.getFlagEnteSpeciale())) {
			mEccezioni.addValidationField("label_TipoSettore");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Tipo Settore"));
		}

		if (isEmptyOrZero(infoComuni.getID_MODO_REAL())) {
			mEccezioni.addValidationField("label_ModalitaRealizzazione");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Modalita' di realizzazione"));		
		}
		
		if(isEmpty(infoComuni.getFLAG_ESCLUSO())){
			mEccezioni.addValidationField("label_ContrattoEscluso");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Contratto escluso"));
		}
		/*else{
			if (Costanti.FLAG_VALORE_SI.equals(infoComuni.getFLAG_ESCLUSO()) && isEmptyOrZero(infoComuni.getID_ESCLUSIONE()))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Articolo esclusione"));

			if (Costanti.FLAG_VALORE_NO.equals(infoComuni.getFLAG_ESCLUSO()) && !isEmptyOrZero(infoComuni.getID_ESCLUSIONE()))
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_190.replace("$1", "Articolo esclusione"));
		}*/
		
		// verifica dati che indirizzano il flusso di acquisizione
		try {
			if (!checkTipoFlusso(infoComuni)) {
				mEccezioni.addValidationField("label_TipologiaAcquisizione");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_188);
			}
		} catch (SQLException e1) {
			mEccezioni.addValidationField("label_ControlloLotto");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_SQL_008.replace("$1", "lettura lotto per controllo flusso acquisizione"));
		}
		
		// PP 28.10.2009 diventa nascosto su web e opzionale su Massloader, su richiesta
		// di Obino
		try{
//			if(isEmptyOrZero(infoComuni.getIdCategSa())) 
//				throw new Exception();
//			else 
			if (!isEmpty(infoComuni.getIdCategSa())
					&& !categoriaSaValida(infoComuni.getIdCategSa(), infoComuni.getDataInizioInfo()))
	    		throw new Exception();
				
		}catch (Exception e) {
			mEccezioni.addValidationField("label_CategoriaStazioneAppaltante");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Categoria Stazione Appaltante"));
		}
			
		//TICKET ALM #659 - 3.04.4
		//Verifica retrocompatibilita' campo Tipologia SA
		if (!SimogProperties.getInstance().isDataCreatedAfter3044(dataCreazioneGara)) {
				if(isYFlag(infoComuni.getFlagSAAgente())){
					
					
					
						//flag sa agente = true : cf valorizzato e valido, denominazione presente
						try{
					if (!tipologiaSaValida(infoComuni.getTipologiaSA(), infoComuni.getDataInizioInfo()))
						throw new Exception();
							
						}catch (Exception e){
					mEccezioni.addValidationField("label_TipologiaStazioneAppaltante");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Tipologia Stazione Appaltante"));
						}
					
					
					
					try {
						if (isEmptyOrZero(infoComuni.getCfAmmAgente())) 
							throw new Exception();
		
						
					}catch (Exception e){
					mEccezioni.addValidationField("label_CfAmministrazione");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1",
							"Codice fiscale dell'Amm.ne per conto della quale agisce la SA"));
					}	
				
//					try {
//					if (!validaPartitaIva(infoComuni.getCfAmmAgente()))
//						throw new Exception();
//						
//					}catch (Exception e){
//					mEccezioni.addValidationField("label_CfAmministrazione");
//					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1",
//							"codice fiscale dell'Amm.ne per conto della quale agisce la SA"));
//					}	
					
						try {
					if (isEmptyOrZero(infoComuni.getDenAmmAgente()))
						throw new Exception();
							
						}catch (Exception e){
					mEccezioni.addValidationField("label_DenominazioneAmministrazione");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_112.replace("$1",
							"Denominazione dell'Amm.ne per conto della quale agisce la SA"));
						}	
		
			} else {
				if (infoComuni.getTipologiaSA() > 0) {
					mEccezioni.addValidationField("label_TipologiaStazioneAppaltante");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Tipologia Stazione Appaltante"));
				}
				if (!isEmptyOrZero(infoComuni.getCfAmmAgente())) {
					mEccezioni.addValidationField("label_CfSoggetto");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
							"Codice fiscale soggetto per cui agisce la SA"));
				}
				if (!isEmptyOrZero(infoComuni.getDenAmmAgente())) {
					mEccezioni.addValidationField("label_DenominazioneSoggetto");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_105.replace("$1",
							"Denominazione soggetto per cui agisce la SA"));
				}
				
				}
		} else {
			if (isYFlag(infoComuni.getFlagSAAgente())) {
				mEccezioni.addValidationField("label_StazioneAppaltanteAgente");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_260.replace("$1",
						"La stazione appaltante agisce per conto di altro soggetto singolo?"));
			}
			if (infoComuni.getTipologiaSA() > 0) {
				mEccezioni.addValidationField("label_TipologiaStazioneAppaltante");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Tipologia Stazione Appaltante"));
			}
			if (!isEmptyOrZero(infoComuni.getCfAmmAgente())) {
				mEccezioni.addValidationField("label_CfSoggettoStazioneAppaltante");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Codice fiscale soggetto per cui agisce la SA"));
			}
			if (!isEmptyOrZero(infoComuni.getDenAmmAgente())) {
				mEccezioni.addValidationField("label_DenominazioneSoggettoStazioneAppaltante");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_105.replace("$1", "Denominazione soggetto per cui agisce la SA"));
			}
		}
		
		//valida Esito Procedura
		if(isEmpty(infoComuni.getEsitoProcedura())){
			mEccezioni.addValidationField("label_EsitoProcedura");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_125.replace("$1", "Esito della Procedura - Stato attuale"));
		//TICKET ALM #3752
		} else if (EsitoEnum.getEnumByCodice(infoComuni.getEsitoProcedura()) == null) {
			mEccezioni.addValidationField("label_EsitoProcedura");
			 mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Esito della Procedura"));	
           }	
		//FINE TICKET ALM #3752
		else{
			if(infoComuni.isHasAwards() && !infoComuni.getEsitoProcedura().equals(EsitoEnum.AGGIUDICATA.codice())){
				mEccezioni.addValidationField("label_EsitoProcedura");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_185.replace("$1", "Esito della procedura - Stato attuale"));
			} else {
				GaraManager gm = new GaraManager(connection,logger);
				try {
					Lotto l = new LottoManager(connection,logger).getLotto(infoComuni.getIdLotto());
					Gara g = gm.getGara(l.getId_Gara());
					
					//MEV 37328 - 3.04.8.1 FASE 2
			 		boolean isOsservCompetente = SimogProperties.getInstance().isOsservatorioRegionaleCompetente(g.getID_OSSERVATORIO());			
			 		
			 		if(isOsservCompetente)
			 		{
			 			mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_294);
			 		}
			 		//FINE MEV 37328
			 		
					if (    //Se la gara e' in carico alla delegata e l'esito e' Aggiudicata, riporta errore 
						((g.getCF_AMM_AGENTE() != null && 
							  !g.getCF_AMMINISTRAZIONE().equals(g.getCF_AMM_AGENTE()) && 
							  !g.getCF_AMM_AGENTE().equals(infoComuni.getCfStazioneAppaltante())		   
									) && 
								//o se il cig e' in carico alla delegata e l'esito e' Aggiudicata, riporta errore MAC 36769 3.04.8.1
								(!g.getCF_AMM_AGENTE().equals(datiComuniFromDB.get(0).getCfStazioneAppaltante())))
							&& g.getID_F_DELEGATE() == Costanti.DELEGA4
							&& infoComuni.getEsitoProcedura().equals(EsitoEnum.AGGIUDICATA.codice())) {
						mEccezioni.addValidationField("label_EsitoProcedura");
						mEccezioni.addValidationErr(
								Messaggi.SIMOG_VALIDAZIONE_263.replace("$1", "Esito della procedura - Stato attuale"));
					} else  //Se la gara o il cig e' in carico alla delegante e l'esito e' Proposta di Aggiudicazione, riporta errore 
						if ((g.getCF_AMM_AGENTE() == null || g.getID_F_DELEGATE() != Costanti.DELEGA4 ||
						     g.getCF_AMMINISTRAZIONE().equals(g.getCF_AMM_AGENTE()) ||
							((g.getCF_AMM_AGENTE()!=null && g.getCF_AMM_AGENTE().equals(infoComuni.getCfStazioneAppaltante()))))
							&& infoComuni.getEsitoProcedura().equals(EsitoEnum.PROPOSTA_AGGIUDICAZIONE.codice())) {
						mEccezioni.addValidationField("label_EsitoProcedura");
						mEccezioni.addValidationErr(
								Messaggi.SIMOG_VALIDAZIONE_264.replace("$1", "Esito della procedura - Stato attuale"));
					} else if (l.getDATA_SCADENZA_PAGAMENTI() == null
							&& (infoComuni.getEsitoProcedura().equals(EsitoEnum.PROPOSTA_AGGIUDICAZIONE.codice())
									|| infoComuni.getEsitoProcedura().equals(EsitoEnum.AGGIUDICATA.codice()))) {
						mEccezioni.addValidationField("label_EsitoProcedura");
						mEccezioni.addValidationErr(
								Messaggi.SIMOG_VALIDAZIONE_265.replace("$1", "Esito della procedura - Stato attuale"));
					}else if (!datiComuniFromDB.isEmpty()){//MAC 36769 3.04.8.1
						 
							infoComuni.setCfStazioneAppaltante(datiComuniFromDB.get(0).getCfStazioneAppaltante());
							infoComuni.setDenStazioneAppaltante(datiComuniFromDB.get(0).getDenStazioneAppaltante());
							infoComuni.setCodiceCC(datiComuniFromDB.get(0).getCodiceCC());
							infoComuni.setDenomCC(datiComuniFromDB.get(0).getDenomCC());
						
						
					}
					
					
					//TICKET ALM #19685 - controllo solo per ws
					if(SimogProperties.getInstance().isDataCreatedAfter30452(g.getData_creazione())) {
						if(infoComuni.getEsitoProcedura().equals(EsitoEnum.ANNULLATA.codice()))
							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Esito della Procedura"));	
					} else {
						if(infoComuni.getEsitoProcedura().equals(EsitoEnum.ANNULLATA_PRIMA.codice()) || 
								infoComuni.getEsitoProcedura().equals(EsitoEnum.ANNULLATA_DOPO.codice()))
							mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Esito della Procedura"));	
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
			
			
		
			
		}
		
		//TICKET ALM #659 - 3.04.4
				//Verifica retrocompatibilita' campo Tipologia SA
		if (!SimogProperties.getInstance().isDataCreatedAfter3044(dataCreazioneGara)) {
						//gm validazione nuovi campi dati comuni
						if(isEmptyOrZero(infoComuni.getTipologiaProcedura())){
				if (isYFlag(infoComuni.getFlagSAAgente())) {
					mEccezioni.addValidationField("label_TipologiaProcedura");
								mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Tipologia procedura"));				
						}
			} else {
							try{
					    		if(!tipologiaProceduraValida(infoComuni.getTipologiaProcedura(),infoComuni.getDataInizioInfo())) 
						    		throw new Exception();
				} catch (Exception e) {
					mEccezioni.addValidationField("label_TipologiaProcedura");
								mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1", "Tipologia procedura"));
							}
						}
	
				}
		
		//TICKET ALM - 3.04.3
		//Verifica retrocompatibilita' campo durata gioni convenzione
		if (SimogFlags.is3043Active() && !SimogProperties.getInstance().isDataCreatedAfter3043(dataCreazioneGara)) {
			if(isEmptyOrZero(infoComuni.getDurataConvenzione())){
				//TICKET ALM #2847-07.1
				// Effettua la verifica sulle opzioni ove e' prevista la visualizzazione del
				// messaggio di avviso
				if (SimogFlags.isAccordoQuadroOrConvenzione(infoComuni.getID_MODO_REAL())) {
					mEccezioni.addValidationField("label_DurataConvenzione");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_211.replace("$1",
							"il numero di giorni della Durata Convenzione"));
				}
				//FINE TICKET ALM #2847-07.1
			} else {
				if (!isNumber(String.valueOf(infoComuni.getDurataConvenzione()))) {
					mEccezioni.addValidationField("label_DurataConvenzione");
					mEccezioni.addValidationErr(
							Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "numero di giorni della Durata Convenzione"));
			}
			}
		}
		//FINE TICKET ALM - 3.04.3


		//TICKET ALM #659 - 3.04.4
				//Verifica retrocompatibilita' campo Tipologia SA
		if (!SimogProperties.getInstance().isDataCreatedAfter3044(dataCreazioneGara)) {
							if(isEmpty(infoComuni.getFlagProcedeStipula())){
								if(isYFlag(infoComuni.getFlagSAAgente())){
									// controllo rilassato per il Massloader
									if(SimogFlags.isFromMassLoader())
						mEccezioni.addValidationWarn(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
								"La centrale di committenza procede alla stipula"));
					else {
						mEccezioni.addValidationField("label_CentraleCommittenza");
						mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_104.replace("$1",
								"La centrale di committenza procede alla stipula"));
								}
							}
			} else {
				if (!isFlag(infoComuni.getFlagProcedeStipula())) {
					mEccezioni.addValidationField("label_CentraleCommittenza");
					mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1",
							"campo centrale di committenza procede alla stipula"));
				}
			}
		}
		
		//validazione dati pubblicazione
		validaPubblicazioneBase(infoComuni.getPubblicazione(),importo_lotto);
		
		//ticket #31057 Procedura apertura buste parte 2
		//(non permettere alla SA di cambiare lâ€™esito della gara da â€œAnnullataâ€� ad â€œAggiudicataâ€�)
		if(isReverseAnnullata(infoComuni.getIdLotto(), infoComuni.getEsitoProcedura())) {
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_120);
		}
	
	}
	
	private boolean isReverseAnnullata(long idLotto, String esitoProcedura) {
		
		//se diverso da AGGIUDICATA i controlli non devono essere eseguiti
		if(!EsitoEnum.AGGIUDICATA.codice().equals(esitoProcedura)) {
			return false;
		}
		
		InfoComuniManager infoComuniManager = new InfoComuniManager(connection, logger);
		
		List<InfoComuniBean> listInfoComuni = new ArrayList<InfoComuniBean>();
		
		try {
			listInfoComuni = infoComuniManager.getInfoComuniByIdLotto(idLotto);
		} catch (SQLException e) {
			mEccezioni.addValidationErr(e.getMessage());
			return false;
		} catch (Exception e) {
			mEccezioni.addValidationErr(e.getMessage());
			return false;
		}
		
		if(listInfoComuni == null || listInfoComuni.isEmpty() || listInfoComuni.size() == 1) {
			return false;
		}else{

//			InfoComuniBean ultimoRecord = listInfoComuni.get(0);
//			InfoComuniBean penultimoRecord = listInfoComuni.get(1);			
//			if(ultimoRecord.getIdStato() == StatiScheda.ELIMINATO ||
//				(ultimoRecord.getIdStato() == StatiScheda.IN_DEFINIZIONE && penultimoRecord.getIdStato() == StatiScheda.ANNULLATO)) {
//				return true;
//			}
			
			for (InfoComuniBean infoComuniBean : listInfoComuni) {
				//MAC 39056 3.04.9
                //facciamo lanciare l'eccezione sono se la scheda Dati comuni è confermata e annullata prima dell'apertura delle buste
//                if((infoComuniBean.getIdStato() == StatiScheda.ANNULLATO || infoComuniBean.getIdStato() == StatiScheda.ELIMINATO) 
//                        && EsitoEnum.ANNULLATA_PRIMA.codice().equals(infoComuniBean.getEsitoProcedura())) {
//                    return true;
//                }
                if(infoComuniBean.getIdStato() == StatiScheda.CONFERMATO && EsitoEnum.ANNULLATA_PRIMA.codice().equals(infoComuniBean.getEsitoProcedura())) {
                    return true;
                }
                //FINE MAC 39056
			}
		}
		return false;	
	}
	/**
	 * Metodo per validare gli appalti multilotto
	 * 
	 * @param aggiudicazione
	 * @param idLotto
	 */
	public void validaAppaltiMultilotto(AggiudicazioneBean aggiudicazione, List<AggiudicatarioBean> aggiudicatari,
			long idLotto) {
		boolean flag_agg_duplicato = false;
		boolean aggiudicatario_non_valido = false;
		if (isFlag(aggiudicazione.getFlagAggiudPrincipale()) && isEmpty(aggiudicazione.getCodiceContratto())) {
			mEccezioni.addValidationField("label_AggiudicazionePrincipaleContratto");
			mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_136.replace("$1", "codice identificativo contratto")
					.replace("$2", "Aggiudicazione principale del contratto"));
		}
		if (!isFlag(aggiudicazione.getFlagAggiudPrincipale()) && !isEmpty(aggiudicazione.getCodiceContratto())) {
			mEccezioni.addValidationField("label_AggiudicazionePrincipaleContratto");
			mEccezioni.addValidationErr(
					Messaggi.SIMOG_VALIDAZIONE_136.replace("$1", "Aggiudicazione principale del contratto")
							.replace("$2", "codice identificativo contratto"));
		}
		// valido gli appalti multilotto se Ã¨ stato inserito un codice identificativo
		// contratto
		// ed Ã¨ stato valorizzato il flag Aggiudicazione principale del contratto del
		// contratto del contratto (che Ã¨ obbligatorio, vedi controllo precedente)
		if(!isEmpty(aggiudicazione.getCodiceContratto()) && isFlag(aggiudicazione.getFlagAggiudPrincipale())){
			List<AggiudicazioneBean> aggiudicazioniMulti = getAggiudicazioniMultilotto(
					aggiudicazione.getCodiceContratto(), idLotto);
			List<AggiudicatarioBean> aggiudicatariMulti = getAggiudicatariMultilotto(
					aggiudicazione.getCodiceContratto(), idLotto);
    		//controllo relativo alle aggiudicazioni
    		if(!isEmpty(aggiudicazioniMulti)){
		    	for(AggiudicazioneBean agg : aggiudicazioniMulti){
			    	//confronto l'aggiudicazione corrente con la lista, esclusa se stessa. 
		    		if( (agg.getIdAggiudicazione() != aggiudicazione.getIdAggiudicazione() ) ){
						// se la lista ne contiene giÃ  una con il flag Aggiudicazione principale del
						// contratto del contratto del contratto "SI" ; MAC 34268 - 3.04.8.1 FASE 2 AGGIUNTO CONTROLLO SULLO STATO
				    	if(isYFlag(agg.getFlagAggiudPrincipale()) && isYFlag(aggiudicazione.getFlagAggiudPrincipale()) && (agg.getIdStato() == 2) )
				    		flag_agg_duplicato = true;
				    }
			    }
		    }

    		//MAC 34268 3.04.8.1 qui si verifica l'errore per le aggiudicazioni duplicate
			if (flag_agg_duplicato) {
				mEccezioni.addValidationField("label_AggiudicazionePrincipaleContratto");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_136.replace("$1", "Aggiudicazione principale del contratto")
								.replace("$2", "codice identificativo contratto"));
				mEccezioni.addValidationField("label_AggiudicazionePrincipaleContratto");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_215.replace("$1",
						"Esiste giÃ  un'Aggiudicazione principale del contratto"));
			}
			if (aggiudicatario_non_valido) {
				mEccezioni.addValidationField("label_AggiudicazionePrincipaleContratto");
				mEccezioni.addValidationErr(
						Messaggi.SIMOG_VALIDAZIONE_136.replace("$1", "Aggiudicazione principale del contratto")
								.replace("$2", "codice identificativo contratto"));
				mEccezioni.addValidationField("label_Aggiudicatario");
				mEccezioni.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_215.replace("$1",
						"Aggiudicatario non valido per il codice identificativo contratto inserito"));
			}
		}
	}
	
	/**
	 * Controllo su tabella della tipologia stazione appaltante
	 * 
	 * @param IdCategoria
	 * @param o data inizio della scheda invocante (estensione della validitÃƒÂ  a posteriori)
	 * @return boolean - true se il codice esiste in tabella
	 *         false se non esiste
	 * @throws SQLException
	 */
	private Boolean tipologiaSaValida(long idTipologia,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		AccessiDB adb = new AccessiDB(connection, logger);

		return adb
				.getTipologicaWithData(TIPOLOGIA_SA.TABLE_NAME, TIPOLOGIA_SA.ID_TIPOLOGIA_SA, TIPOLOGIA_SA.DESCRIZIONE,
						TIPOLOGIA_SA.DATA_INIZIO_VALIDITA, TIPOLOGIA_SA.DATA_FINE_VALIDITA, o)
				.containsKey(String.valueOf(idTipologia).trim());
	}	
	
	/**
	 * Controllo su tabella della tipologia procedura
	 * 
	 * @param IdCategoria
	 * @param o data inizio della scheda invocante (estensione della validitÃƒÂ  a posteriori)
	 * @return boolean - true se il codice esiste in tabella
	 *         false se non esiste
	 * @throws SQLException
	 */
	private Boolean tipologiaProceduraValida(long idTipologia,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		AccessiDB adb = new AccessiDB(connection, logger);

		return adb
				.getTipologica(TIPOLOGIA_PROCEDURA.TABLE_NAME, TIPOLOGIA_PROCEDURA.ID_TIPOLOGIA_PROCEDURA,
						TIPOLOGIA_PROCEDURA.DESCRIZIONE, TIPOLOGIA_PROCEDURA.DATA_FINE_VALIDITA, o)
				.containsKey(String.valueOf(idTipologia).trim());
	}	
	
	/**
	 * @param idCategoria
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃƒÂ  di una tipologia a posteriori
	 * @return Boolean
	 * @throws SQLException
	 */
	private Boolean categoriaSaValida(String idCategoria,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		AccessiDB adb = new AccessiDB(connection, logger);
		return adb.getTipologica(CATEGORIA_SA.TABLE_NAME, CATEGORIA_SA.ID_CATEG_SA, CATEGORIA_SA.DESCRIZIONE,
							CATEGORIA_SA.DATA_FINE_VALIDITA,o).containsKey(idCategoria);				
	}
	/*
	 * public boolean istatValido(String istat,Object o) throws SQLException{ /** se
	 * non ho una base dati valida ritorno true
	 **/
	/*
	 * if(!super.isSQLConnectionEnabled){ return true; }
	 * 
	 * IstatManager im = new IstatManager(connection,logger); return
	 * im.isValid(istat,o); }
	 * 
	 * public boolean nutsValido(String nuts,Object o) throws SQLException{ /** se
	 * non ho una base dati valida ritorno true
	 **/
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
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃƒÂ  di una tipologia a posteriori
	 * @return boolean
	 * @throws SQLException
	 */
	private boolean tipoLSFValido(long tipoLSF, long idLotto, String tipoEnte, String tipoContratto, Object o)
			throws SQLException {
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);	
		
		return  man.caricaComboAppalto(tipoEnte, tipoContratto,o).containsKey(String.valueOf(tipoLSF));
				
	}
		
	/**
	 * @param idTipoPrest
	 * @param idLotto
	 * @param tipoEnte
	 * @param tipoContratto
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃƒÂ  di una tipologia a posteriori
	 * @return boolean
	 * @throws SQLException
	 */
	private boolean tipoPrestazioneValido(long idTipoPrest, long idLotto, String tipoEnte, String tipoContratto,
			Object o) throws SQLException {
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);	
		return man.caricaComboPrestazione(idLotto, tipoEnte,tipoContratto,o).containsKey(String.valueOf(idTipoPrest));
	}

	/**
	 * @param modoRiaggiud
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃƒÂ  di una tipologia a posteriori
	 * @return Boolean
	 * @throws SQLException
	 */
	private Boolean modoRiaggiudValido(String modoRiaggiud,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		AccessiDB adb = new AccessiDB(connection, logger);
		//TICKET ALM #2847 - modalita riaggiudicazione
			return adb.getTipologicaWithData(MODI_RIAGGIUD.TABLE_NAME, MODI_RIAGGIUD.ID_MODO_RIAGGIUD,
					MODI_RIAGGIUD.DESCRIZIONE, MODI_RIAGGIUD.DATA_INIZIO_VALIDITA, MODI_RIAGGIUD.DATA_FINE_VALIDITA, o)
					.containsKey(String.valueOf(modoRiaggiud));
	}

	/**
	 * @param idCriterio
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃƒÂ  di una tipologia a posteriori
	 * @return Boolean
	 * @throws SQLException
	 */
	private Boolean criterioAggiudicazioneValido(long idCriterio,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		AccessiDB adb = new AccessiDB(connection, logger);	
		//TICKET ALM #2847
			return adb.getTipologicaWithData(MODALITA_GARA.TABLE_NAME, MODALITA_GARA.ID_MODALITA_GARA,
					MODALITA_GARA.DESCRIZIONE, MODALITA_GARA.DATA_INIZIO_VALIDITA, MODALITA_GARA.DATA_FINE_VALIDITA, o)
					.containsKey(String.valueOf(idCriterio));

	}

	/**
	 * @param idModo
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃƒÂ  di una tipologia a posteriori
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
	
	private double getSubtotale(BigDecimal l, BigDecimal s, BigDecimal f, BigDecimal sicurezza,
			BigDecimal progettazione) {
		double temp = 0.00;
		if(l != null)
			temp += l.doubleValue();
		if(s != null)
			temp += s.doubleValue();
		if(f != null)
			temp += f.doubleValue();
		if(sicurezza != null)
			temp += sicurezza.doubleValue();
		if(progettazione != null)
			temp += progettazione.doubleValue();
		return temp;
	}
	/**
	 * variazione di getSubtotale con un'argomento di meno per il nuovo calcolo dell'importo con le percentuali
	 * 
	 * @param l
	 * @param s
	 * @param f
	 * @param progettazione
	 * @return
	 */
	
	private double getSubtotale1(BigDecimal l, BigDecimal s , BigDecimal f, BigDecimal progettazione){
		double temp = 0.00;
		if(l != null)
			temp += l.doubleValue();
		if(s != null)
			temp += s.doubleValue();
		if(f != null)
			temp += f.doubleValue();
		if(progettazione != null)
			temp += progettazione.doubleValue();
		return temp;
	}
	//private boolean subtotaleOK(BigDecimal l, BigDecimal s , BigDecimal f){
	private boolean subtotaleOK(BigDecimal l, BigDecimal s, BigDecimal f, BigDecimal sicurezza,
			BigDecimal progettazione) {
		return getSubtotale(l, s, f,sicurezza,progettazione) >= 150000.00;
	}

	// private boolean importoAggOK(BigDecimal importoAgg, BigDecimal l, BigDecimal
	// s , BigDecimal f, BigDecimal rib, boolean flagAumento){
	/**
	 * formula ((campo32+campo34)*(1-campo84/100))+campo33+campo33A oppure
	 * ((campo32+campo34)*(1+campo85/100))+campo33+campo33A nel caso di offerta in
	 * aumento.
	 */
	private boolean importoAggOK(BigDecimal importoAgg, BigDecimal l, BigDecimal s, BigDecimal f, BigDecimal rib,
			BigDecimal progettazione, BigDecimal sicurezza, BigDecimal nonAssog, int tipo) {
		double temp = rib != null ? rib.doubleValue() : 0.00;
		double importo = rib != null ? importoAgg.doubleValue() : 0.00;
		//se tipo = 0 aumento, 1 ribasso, 2 o altro ignora percentuale
		double impCalcolato = getSubtotale1(l, s, f, progettazione)
				* (tipo == 0 ? (1 + temp / 100) : tipo == 1 ? (1 - temp / 100) : 1)
		       				+ (sicurezza != null ? sicurezza.doubleValue() : 0) + (nonAssog != null ? nonAssog.doubleValue() : 0);
		//settaggio importoCalcolato alla stessa precisione di importo aggiudicazione		
		impCalcolato = new BigDecimal(impCalcolato, new MathContext(importoAgg.precision())).doubleValue();
		return (impCalcolato == importo);
	}	

	//2.10 aggiunto nuovo controllo (somma campi) 9.1.1.28bis
	
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
	//2.10 fine nuovo controllo (somma campi) 9.1.1.28bis
	
	/**
	 * @param idTipo
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃƒÂ  di una tipologia a posteriori
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
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃƒÂ  di una tipologia a posteriori
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
	 * @param idLotto
	 * @param idTipo
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃƒÂ  di una tipologia a posteriori
	 * @return boolean
	 * @throws SQLException
	 */
	private boolean isTipoCatValido( long idLotto, String idTipo,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		RequisitiManager rman = new RequisitiManager(connection, logger);
//		boolean b = rman.caricaCategorie(idLotto,o).containsKey(String.valueOf(idTipo));				
//		logger.debug("[2Ã‚Â§Ã‚Â§Ã‚Â§Ã‚Â§2] - ("+b+")" + o + "\n\r" + rman.caricaCategorie(idLotto,o).toString());
//		logger.debug("Id tipo: " + idTipo);
		return rman.caricaCategorie(o).containsKey(String.valueOf(idTipo));
	}
	/**
	 * @param idCI
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃƒÂ  di una tipologia a posteriori
	 * @return boolean
	 * @throws SQLException
	 */
	private boolean isCIValida(String idCI,Object o) throws SQLException{
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		RequisitiManager rman = new RequisitiManager(connection, logger);
		return rman.caricaClassiImporto(o).containsKey(String.valueOf(idCI));

	}

	private boolean isInDefinedRange(String dataVerbaleAggiudicazione)throws SQLException {
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		AccessiDB adb = new AccessiDB(connection,logger);
		String now =  PageHelper.getDBDateFromTS(adb.getNow());
		
		dataVerbaleAggiudicazione = PageHelper.getFormattedDBDate(dataVerbaleAggiudicazione);
		if (Costanti.START_DATE.compareTo(dataVerbaleAggiudicazione) <= 0
				&& now.compareTo(dataVerbaleAggiudicazione) >= 0)
			return true;
		else
			return false;
	}
	
	/**
	 * @param idStrumento
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validitÃƒÂ  di una tipologia a posteriori
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

	private boolean isBiggerThanIndiceDispersione(double importo_complessivo_appalto, String anno, String tipoSettore,
			String tipoContratto, Object o) throws SQLException {
		/** se non ho una base dati valida ritorno true **/
		if (!super.isSQLConnectionEnabled) {
			return true;
		}
		
		if (anno == null)
			return false;
		
		AggiudicazioniManager rman = new AggiudicazioniManager(connection, logger);
		//true se l'importo complessivo Ã¨ maggiore dell'indice dispersione
		BigDecimal importo =  rman.getIndiceDispersione(anno, tipoSettore, tipoContratto, o);
		if (importo == null)
			return false;
		else
			return (importo_complessivo_appalto > importo.doubleValue()); 
	}

	/**
	 * verifica la validitÃ  formale del codice fiscale usa la funzione disponibile nella classe estesa
	 * @param cf String
	 * @return boolean
	 */
	public boolean validaCodiceFiscale(String cf) {
		return super.validaCodiceFiscale(cf);
	}
	
	public boolean validaCondizioni(List<CondizioneAggBean> condizioni, Object dataRif){
		boolean esito_locale = true;
		int counter = 1;
		int local_error = 0;
		for(CondizioneAggBean condizione : condizioni){
			String id = String.valueOf(condizione.getIdCondizione());
			//TICKET ALM #2847
			// esito_locale = super.validaTipologica(CONDIZIONI.TABLE_NAME,
			// CONDIZIONI.ID_CONDIZIONE, CONDIZIONI.DESCRIZIONE,
			// CONDIZIONI.T_DATA_FINE_VALIDITA, dataRif, id);
			esito_locale = super.validaTipologicaWithData(CONDIZIONI.TABLE_NAME, CONDIZIONI.ID_CONDIZIONE,
					CONDIZIONI.DESCRIZIONE, CONDIZIONI.T_DATA_INIZIO_VALIDITA, CONDIZIONI.T_DATA_FINE_VALIDITA, dataRif,
					id);
			if(!esito_locale){
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_108.replace("$1", "campo Condizione"),
						counter);
				local_error++;
			}
			counter++;
		}
		if (local_error == 0) {
			return true;
		}
		return false;
	}

	public boolean obbligoCondizioniCriteriAggiudicazione(List<CondizioneAggBean> condizioni){
		boolean esito_locale = true;
		for(CondizioneAggBean condizione : condizioni){
			if (condizione.getIdCondizione() == Costanti.DLGS_163_ART_57_C2LB
					|| condizione.getIdCondizione() == Costanti.DLGS_163_ART_57_C3LBF
					|| condizione.getIdCondizione() == Costanti.DLGS_163_ART_57_C3LDF
					|| condizione.getIdCondizione() == Costanti.DLGS_163_ART_57_C5LASA1
					|| condizione.getIdCondizione() == Costanti.DLGS_163_ART_57_C5LASA2
					|| condizione.getIdCondizione() == Costanti.DLGS_163_ART_57_C5LB
					|| condizione.getIdCondizione() == Costanti.DLGS_163_ART_221_C1LC
					|| condizione.getIdCondizione() == Costanti.DLGS_163_ART_221_C1LE
					|| condizione.getIdCondizione() == Costanti.DLGS_163_ART_221_C1LF
					|| condizione.getIdCondizione() == Costanti.DLGS_163_ART_221_C1LG
					|| condizione.getIdCondizione() == Costanti.DLGS_163_ART_221_C1LJ
					|| condizione.getIdCondizione() == Costanti.DLGS_163_ART_221_C1LK
					|| condizione.getIdCondizione() == Costanti.DLGS_163_ART_99_C5_ART108_C6)
			   esito_locale = false;
		}
		return esito_locale;
	}
	public List<AggiudicazioneBean> getAggiudicazioniMultilotto (String codiceContratto, long idLotto) {
		List<AggiudicazioneBean> aggMulti = new ArrayList<AggiudicazioneBean>();
		MultilottoManager mMan = new MultilottoManager(connection, logger);
		aggMulti = mMan.getAggiudicazioniListMultilotto(codiceContratto, idLotto);
		return aggMulti;
	}
	
	public List<AggiudicatarioBean> getAggiudicatariMultilotto (String codiceContratto, long idLotto) {
		List<AggiudicatarioBean> aggMulti = new ArrayList<AggiudicatarioBean>();
		AggiudicatarioManager aggMan = new AggiudicatarioManager(connection, logger);
		aggMulti = aggMan.getAggiudicatariListMultilotto(codiceContratto, idLotto);
		return aggMulti;
	}
	
	//TICKET ALM #2847-08.5
	private void validaPercentualiAggiudicatari(List<AggiudicatarioBean> aggiudicatari, long idModalitaAgg,
			InfoComuniBean infoComuni, double importo_complessivo_appalto) {
		
		for (int i = 0; i < aggiudicatari.size(); i++) {
			    AggiudicatarioBean aggElem = aggiudicatari.get(i);
				if (isEmpty(aggElem.getPercRibassoAggiudicatario())) {
					if (idModalitaAgg==Costanti.PREZZO_BASSO || idModalitaAgg==Costanti.CRITERIO_MINOR_PREZZO) {
						try {
							if (isOrdinario(infoComuni.getFlagEnteSpeciale()))
								throw new Exception();
							if (isSpeciale(infoComuni.getFlagEnteSpeciale())) {
								if (isLavori(infoComuni.getTipoContratto())) {
									if (importo_complessivo_appalto == Costanti.IMPORTO_FUORI_SCALA
											|| importo_complessivo_appalto >  Costanti.IMPORTO_LOTTO_4845000) //Costanti.IMPORTO_LOTTO_5150000)
										throw new Exception();
								}
							if ((isServizi(infoComuni.getTipoContratto())
									|| isForniture(infoComuni.getTipoContratto()))) {
									if (importo_complessivo_appalto == Costanti.IMPORTO_FUORI_SCALA
											|| importo_complessivo_appalto > Costanti.IMPORTO_LOTTO_387000) //Costanti.IMPORTO_412000)
										throw new Exception();
								}
							}
						} catch (Exception e) {
							mEccezioni.addValidationWarnElemento(Messaggi.SIMOG_VALIDAZIONE_202,i);
						}
					}
			} else {
				try{
		     			if(!validateDecimalPart(aggElem.getPercRibassoAggiudicatario(),5))
		     				throw new Exception();
					if (!isInRange(aggElem.getPercRibassoAggiudicatario(), new BigDecimal(0),
							BigDecimal.valueOf(99.99999)))
		     				throw new Exception();
				} catch (Exception e) {
					mEccezioni.addValidationWarnElemento(
							Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "ribasso aggiudicazione"), i);
					}
			}
			if (!isEmptyOrZero(aggElem.getPercRibassoAggiudicatario())
					&& !isEmptyOrZero(aggElem.getPercAumentoAggiudicatario())) {
				mEccezioni.addValidationErrElemento(Messaggi.SIMOG_VALIDAZIONE_141
						.replace("$1", "Ribasso aggiudicazione").replace("$2", "Offerta in aumento"), i);
				}
			
			if(!isEmpty(aggElem.getPercAumentoAggiudicatario())){
	    		if(!isPositive(aggElem.getPercAumentoAggiudicatario()))
					mEccezioni.addValidationErrElemento(
							Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "offerta in aumento"), i);
			
			    if(!validateDecimalPart(aggElem.getPercAumentoAggiudicatario(),5))
					mEccezioni.addValidationWarnElemento(
							Messaggi.SIMOG_VALIDAZIONE_114.replace("$1", "offerta in aumento"), i);
			}
		}
		
	}
	//FINE TICKET ALM #2847-08.5
	
	
	//TICKET ALM - 3.04.3
	/**
	 * Verifica se l'id della funzione delegata sia valido
	 * @param idFunzioniDelegate id da validare
	 * @return
	 * @throws SQLException 
	 */
	/*private boolean tipologiaFunzioniDelegateValida(String idFunzioniDelegate, Object o)  {
	if(!super.isSQLConnectionEnabled){ return true; } 
		
		AccessiDB adb = new AccessiDB(connection, logger);

		try {
			return adb.getTipologicaWithData(FUNZIONI_DELEGATE.TABLE_NAME, 
					FUNZIONI_DELEGATE.ID_F_DELEGATE, 
					FUNZIONI_DELEGATE.DESCRIZIONE, 
					FUNZIONI_DELEGATE.DATA_INIZIO_VALIDITA,
					FUNZIONI_DELEGATE.DATA_FINE_VALIDITA,o).containsKey(String.valueOf(idFunzioniDelegate).trim());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
	}*/
}