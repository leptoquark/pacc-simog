package it.avlp.simog.common.action;

import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.RequisitiManager;
import it.avcp.simog.managers.aggiudicazione.ResponsabileManager;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.Rubrica;
import it.avlp.simog.beans.RubricaResponsabili;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.beans.comparators.SoggettiPartecipantiComparator;
import it.avlp.simog.beans.comparators.SoggettiResponsabiliComparator;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.factory.AnnullamentoFactory;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.rubricamanager.RubricaManager;
import it.avlp.simog.rubricamanager.RubricaResponsabiliManager;
import it.avlp.simog.util.Base64Coder;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class Scheda_A_SharedAction extends BaseSharedAction {
	
public static String CLAZZ = "Scheda_A_SharedAction";

	public Scheda_A_SharedAction(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
	}
	
   /**
   * METODO CHE SI OCCUPA DI RECUPERARE UNA AGGIUDICAZIONE DAL CUI + PROGCUI
   * @param CUI
   * @return
   */
   public AggiudicazioneBean getAggiudicazioneByProgAndCui(String CUI, boolean confermata) throws ActionException {
      return (new AggiudicazioneAction(connection,logger)).getAggiudicazioneByProgAndCui(CUI, confermata);
   }	 
	
	/*******************************************************************************************************
	 * Carica le informazioni della scheda A in base ai parametri dell'aggiudicazione inserita
	 * @param idAggiudicazione long
	 * @param dataInizioAggiudicazione Timestamp
	 * @param tipoEnte String
	 * @param ignoraStato TODO
	 * @return Scheda_A
	 * @throws ActionException
	 */
	public Scheda_A load(long idAggiudicazione, Timestamp dataInizioAggiudicazione, String tipoEnte, boolean ignoraStato, long idLotto) throws ActionException{
		Scheda_A saBean = new Scheda_A();
		logger.debug(CLAZZ+".load: START");
		saBean.setAggiudicazione((new AggiudicazioneAction(connection,logger)).loadOne(idAggiudicazione, dataInizioAggiudicazione, ignoraStato));
		saBean.setAggiudicatari((new AggiudicatarioAction(connection,logger)).loadMany(idAggiudicazione, dataInizioAggiudicazione, ignoraStato));
		saBean.setRequisiti((new RequisitiAction(connection,logger)).loadMany(idAggiudicazione, dataInizioAggiudicazione, ignoraStato));		

		TipoAggiudicazione sottotipo = saBean.getAggiudicazione().getSottotipo() == null ? TipoAggiudicazione.A : saBean.getAggiudicazione().getSottotipo();
		String sezioneResp = PSBD.SEZIONE_RA;
		if(TipoAggiudicazione.E.compareTo(sottotipo) == 0)
			sezioneResp = PSBD.SEZIONE_RE;
		else if(TipoAggiudicazione.S.compareTo(sottotipo) == 0)
				sezioneResp = PSBD.SEZIONE_RS;
		else if(TipoAggiudicazione.Q.compareTo(sottotipo) == 0)
			sezioneResp = PSBD.SEZIONE_RQ;		
		
		saBean.setResponsabili((new ResponsabileAction(connection,logger)).loadMany(idAggiudicazione, dataInizioAggiudicazione, sezioneResp, ignoraStato));
		//gm modificato per caricare sia le prestazioni di soggetti responsabili che quelle di soggetti partecipanti
		ResponsabileAction respAction = new ResponsabileAction(connection,logger);
		List<ResponsabileBean> prestazioni = respAction.loadManyAll(idAggiudicazione, dataInizioAggiudicazione, PSBD.SEZIONE_PA, ignoraStato);
	
		List<ResponsabileBean> newPrest = respAction.implodiAggiudicatari(prestazioni);
		saBean.setPrestazioni(newPrest);
		
		saBean.setTipoLavoro((new TipoAppaltoAction(connection,logger)).loadManyL(idAggiudicazione, dataInizioAggiudicazione,tipoEnte, ignoraStato)); 
		saBean.setTipoFS((new TipoAppaltoAction(connection,logger)).loadManyFS(idAggiudicazione, dataInizioAggiudicazione,tipoEnte, ignoraStato));
		saBean.setCondizioni((new CondizioniAction(connection,logger)).loadMany(idAggiudicazione, dataInizioAggiudicazione, ignoraStato));
		saBean.setFinanziamenti(new FinanziamentoAction(connection,logger).loadMany(idAggiudicazione, dataInizioAggiudicazione, ignoraStato));
		
		// PP dati complementari
      InfoComuniManager iManager = new InfoComuniManager(connection, logger);
      InfoGaraBean igb = null;
      try {
         igb = iManager.loadInfoGara(idLotto);
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      saBean.setInfoGara(igb);
		saBean.setInfoComuni(getInfoComuni(igb.getIdInfo(), igb.getDataInizioInfo()));
		
		if(SimogFlags.is3031_RFWEBGL02Active()){
		   saBean.setElencoCup( new CupLottoAggAction(connection, logger).getElencoCup(idAggiudicazione <= 0 ? null : idAggiudicazione, dataInizioAggiudicazione, ignoraStato) );
		   LottoManager lm = new LottoManager(connection, logger);
		   try {
            Lotto lotto = lm.getLotto(idLotto);
            saBean.setFlagCUP(lotto.getFLAG_CUP());
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
		}
		
		return saBean;
	}
	
	
	public Timestamp gestisciVariazioniCO(Scheda_A saBean, RichiestaAnnullamento rab, String cfUtente, String tipoEnte)throws Exception{
		Timestamp vecchiaData = rab.getData_inizio_record();
		Timestamp ts = richiediAnnullamento(rab);
		boolean ret = false;
		List<AggiudicatarioBean> affidatari = saBean.getAggiudicatari();
		List<ResponsabileBean> responsabili = saBean.getResponsabili();
		List<ResponsabileBean> prestazioni	= saBean.getPrestazioni();
		
		
		BaseRichiestaAnnullamento aaa = AnnullamentoFactory.getAction(IdentificativoSchede.TAB_AGGIUDICAZIONE, connection, logger);	
		
		if(ts != null){
			rab.setDecisore(cfUtente);
			rab.setEsito(RichiestaAnnullamento.RICHIESTA_ACCETTATA);
		
			rab.setData_inizio_record(vecchiaData);
			
			// PP B302.2.0
			if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive()){
				Map<String, String> lista = this.loadMotiviVCO(saBean.getAggiudicazione().getDataInizioAggiudicazione());			
				rab.setMotivo_esito( (String) lista.get(saBean.getAggiudicazione().getIdMotivoVarCO()));
			}
			
			ret = aaa.gestisciRichiesta(rab, cfUtente);
			if(ret){
				
				// aggiornamento anagrafiche responsabili modificate
				RubricaResponsabiliManager rrm = new RubricaResponsabiliManager(connection, logger);
				RubricaManager rpm = new RubricaManager(connection, logger);
				
				if(responsabili != null){
					for(ResponsabileBean resp: responsabili){
						// da modificare
						if( resp.getSoggettoResponsabile().isModifica()){
	
							SoggettoResponsabileBean anagrafica_db = rrm.getAllSoggettoResponsabileByCF(resp.getSoggettoResponsabile().getCodiceFiscaleResponsabile());
							SoggettoResponsabileBean anagrafica_web = resp.getSoggettoResponsabile();
							anagrafica_web.setIdResponsabile(anagrafica_db.getIdResponsabile());
							anagrafica_web.setDataInizioRes(anagrafica_db.getDataInizioRes());
							boolean esitoConfronto = new SoggettiResponsabiliComparator().equals(resp.getSoggettoResponsabile(), anagrafica_db);
							//se le anagrafiche non sono uguali
							if(!esitoConfronto){
	
								rrm.cancellaPartecipante(RubricaResponsabili.converti(anagrafica_web));
								Object[] retVal = rrm.insertPartecipante(RubricaResponsabili.converti(resp.getSoggettoResponsabile()), true);
								
								// aggiorno la data validita
								resp.getSoggettoResponsabile().setDataInizioRes((Timestamp)retVal[1]);
							}
						}
					}
				}

				// aggiornamento anagrafiche prestazioni modificate
				if(prestazioni != null){
					for(ResponsabileBean resp: prestazioni){
						// responsabile da modificare
						if( resp.getSoggettoResponsabile() != null && resp.getSoggettoResponsabile().isModifica()){
	
							SoggettoResponsabileBean anagrafica_db = rrm.getAllSoggettoResponsabileByCF(resp.getSoggettoResponsabile().getCodiceFiscaleResponsabile());
							SoggettoResponsabileBean anagrafica_web = resp.getSoggettoResponsabile();
							anagrafica_web.setIdResponsabile(anagrafica_db.getIdResponsabile());
							anagrafica_web.setDataInizioRes(anagrafica_db.getDataInizioRes());
							boolean esitoConfronto = new SoggettiResponsabiliComparator().equals(resp.getSoggettoResponsabile(), anagrafica_db);
							//se le anagrafiche non sono uguali
							if(!esitoConfronto){
	
								rrm.cancellaPartecipante(RubricaResponsabili.converti(anagrafica_web));
								Object[] retVal = rrm.insertPartecipante(RubricaResponsabili.converti(resp.getSoggettoResponsabile()), true);
								
								// aggiorno la data validita
								resp.getSoggettoResponsabile().setDataInizioRes((Timestamp)retVal[1]);
							}
						}
						// partecipante da modificare
						if( resp.getSoggettoPartecipante()!= null && resp.getSoggettoPartecipante().isModifica()){
	
							SoggettoPartecipanteBean anagrafica_db = rpm.getAllSoggettoPartecipanteByCF(resp.getSoggettoPartecipante().getCodiceFiscale(), resp.getSoggettoPartecipante().getId_stato());
	
							SoggettoPartecipanteBean anagrafica_web = resp.getSoggettoPartecipante();
							// sovrascrivo le info fisse						
							anagrafica_web.setIdSoggettoPartecipante(anagrafica_db.getIdSoggettoPartecipante());
							anagrafica_web.setDataInizioSogg(anagrafica_db.getDataInizioSogg());
							anagrafica_web.setId_stato(anagrafica_db.getId_stato());
							anagrafica_web.setFlagEsteri(anagrafica_db.getFlagEsteri());
							
							boolean esitoConfronto = new SoggettiPartecipantiComparator().equals(anagrafica_web, anagrafica_db);
							//se le anagrafiche non sono uguali
							if(!esitoConfronto){
	
								rpm.cancellaPartecipante(Rubrica.converti(anagrafica_web));
								Object[] retVal = rpm.insertPartecipante(Rubrica.converti(anagrafica_web), true);
								
								// aggiorno la data validita
								resp.getSoggettoPartecipante().setDataInizioSogg((Timestamp)retVal[1]);
							}
						}
					}
				}

				// aggiornamento anagrafiche aggiudicatari modificate
				if(affidatari != null){			
					for(AggiudicatarioBean aff: affidatari){
						// partecipante da modificare
						if( aff.getSoggettoPartecipante()!= null && aff.getSoggettoPartecipante().isModifica()){
	
							SoggettoPartecipanteBean anagrafica_db = rpm.getAllSoggettoPartecipanteByCF(aff.getSoggettoPartecipante().getCodiceFiscale(), aff.getSoggettoPartecipante().getId_stato());
	
							SoggettoPartecipanteBean anagrafica_web = aff.getSoggettoPartecipante();
							// sovrascrivo le info fisse						
							anagrafica_web.setIdSoggettoPartecipante(anagrafica_db.getIdSoggettoPartecipante());
							anagrafica_web.setDataInizioSogg(anagrafica_db.getDataInizioSogg());
							anagrafica_web.setId_stato(anagrafica_db.getId_stato());
							anagrafica_web.setFlagEsteri(anagrafica_db.getFlagEsteri());
							
							boolean esitoConfronto = new SoggettiPartecipantiComparator().equals(anagrafica_web, anagrafica_db);
							//se le anagrafiche non sono uguali
							if(!esitoConfronto){
	
								rpm.cancellaPartecipante(Rubrica.converti(anagrafica_web));
								Object[] retVal = rpm.insertPartecipante(Rubrica.converti(anagrafica_web), true);
								
								// aggiorno la data validita
								aff.getSoggettoPartecipante().setDataInizioSogg((Timestamp)retVal[1]);
							}
						}
					
						// loop per eventuali ditte ausiliarie modificate
						if(aff.getDitteAusiliarie() != null && !aff.getDitteAusiliarie().isEmpty()){
							for(DittaAusiliariaBean aus: aff.getDitteAusiliarie()){
								// partecipante da modificare
								if( aus.getSoggettoPartecipante()!= null && aus.getSoggettoPartecipante().isModifica()){
		
									SoggettoPartecipanteBean anagrafica_db = rpm.getAllSoggettoPartecipanteByCF(aus.getSoggettoPartecipante().getCodiceFiscale(), aus.getSoggettoPartecipante().getId_stato());
		
									SoggettoPartecipanteBean anagrafica_web = aus.getSoggettoPartecipante();
									// sovrascrivo le info fisse						
									anagrafica_web.setIdSoggettoPartecipante(anagrafica_db.getIdSoggettoPartecipante());
									anagrafica_web.setDataInizioSogg(anagrafica_db.getDataInizioSogg());
									anagrafica_web.setId_stato(anagrafica_db.getId_stato());
									anagrafica_web.setFlagEsteri(anagrafica_db.getFlagEsteri());
									
									boolean esitoConfronto = new SoggettiPartecipantiComparator().equals(anagrafica_web, anagrafica_db);
									//se le anagrafiche non sono uguali
									if(!esitoConfronto){
		
										rpm.cancellaPartecipante(Rubrica.converti(anagrafica_web));
										Object[] retVal = rpm.insertPartecipante(Rubrica.converti(anagrafica_web), true);
										
										// aggiorno la data validita
										aus.getSoggettoPartecipante().setDataInizioSogg((Timestamp)retVal[1]);
									}
								}
							}
						}
					}
				}

				if(responsabili != null){
	                for(ResponsabileBean resp: responsabili)
	                    resp.setDataInizioScheda(ts);		   
				}

                if(prestazioni != null){
      				for(ResponsabileBean resp: prestazioni)
      					resp.setDataInizioScheda(ts);
                }
      				
                if(affidatari != null){
         			for(AggiudicatarioBean aff: affidatari)
   					aff.setDataInizioAggiudicazione(ts);
                }
                
				saBean = load(saBean.getAggiudicazione().getIdAggiudicazione(), ts, tipoEnte, false, saBean.getInfoGara().getIdLotto());
				saBean.setResponsabili(responsabili);
				saBean.setPrestazioni(prestazioni);
				saBean.setAggiudicatari(affidatari);
				
				confirm(saBean, new Boolean[]{true,true,true,true,true,true,true,true}, cfUtente);
			}
			
			
		}
		return ts;
	}
	
	/******************************************************************************************************
	 * Viene gestito il salvataggio dei dati della scheda A.
	 * @param saBean Scheda_A
	 * @param flags Boolean[]
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public void save(Scheda_A saBean, Boolean[] flags, String cfUtente) throws ActionException{
		
		logger.debug("SCHEDA_A_ACTION.save: START");
		long idAggiudicazione = saBean.getAggiudicazione().getIdAggiudicazione();
		Timestamp dataInizioAgg = saBean.getAggiudicazione().getDataInizioAggiudicazione();
		logger.debug("idAGG: "+idAggiudicazione+"; dataInizioAGG: "+dataInizioAgg);
		AggiudicazioneAction aggiudicazioneAction = new AggiudicazioneAction(connection,logger);
		
		if(aggiudicazioneAction.existAggiudicazione(idAggiudicazione, dataInizioAgg)){
		
			int numAgg = 0;
			
			// Sezione Aggiudicazione
			if(flags[0].booleanValue()){
				numAgg = aggiudicazioneAction.save(saBean.getAggiudicazione(),cfUtente);
			}
			
			if(numAgg > 0){
				
				// Sezione Responsabili - // Prestazioni
				if(flags[1].booleanValue()){
					ResponsabileAction responsabileAction = new ResponsabileAction(connection,logger);
					responsabileAction.save(saBean.getAllResponsabili(),idAggiudicazione,dataInizioAgg);	
					
				}
				
				// Affidatario
				if(flags[2].booleanValue()){
					AggiudicatarioAction aggiudicatarioAction = new AggiudicatarioAction(connection,logger);
					aggiudicatarioAction.save(saBean.getAggiudicatari(),idAggiudicazione,dataInizioAgg);
				}
				
				//Requisiti
				if(flags[3].booleanValue()){
					RequisitiAction requisitiAction = new RequisitiAction(connection,logger);
					requisitiAction.save(saBean.getRequisiti(),idAggiudicazione,dataInizioAgg);
				}
				//Condizioni
				if(flags[4].booleanValue()){
					CondizioniAction condizioniAction = new CondizioniAction(connection,logger);
					condizioniAction.save(saBean.getCondizioni(),idAggiudicazione,dataInizioAgg);
				}
				//TipoAppalto
				if(flags[5].booleanValue()){
				   TipoAppaltoAction tipoAppaltoAction = new TipoAppaltoAction(connection,logger);
               tipoAppaltoAction.setIdLotto(saBean.getTipoFS(), saBean.getInfoGara().getIdLotto());
               tipoAppaltoAction.setIdLotto(saBean.getTipoLavoro(), saBean.getInfoGara().getIdLotto());
               
               if(SimogFlags.is3031_RFWEBGL00Active()
                     && SimogProperties.getInstance().isCUPLotto(saBean.getInfoGara().getDataCreazioneGara()))
               {
                  tipoAppaltoAction.completaDatiTipoAppalto(saBean.getInfoGara().getIdLotto(),idAggiudicazione,dataInizioAgg);
               }
               else{
                  // PP aggiungo idlotto
                  tipoAppaltoAction.setIdLotto(saBean.getTipoAppalto(), saBean.getInfoGara().getIdLotto());
                  tipoAppaltoAction.save(saBean.getTipoAppalto(),idAggiudicazione,dataInizioAgg);
               }               
				}
				
				// Finanziamenti
				if(flags[7].booleanValue()){
					FinanziamentoAction finanziamentoAction = new FinanziamentoAction(connection,logger);
				
					finanziamentoAction.save(saBean.getFinanziamenti(),idAggiudicazione,dataInizioAgg);
				}
		
				if(SimogFlags.is3031_RFWEBGL02Active() 
				&& !SimogProperties.getInstance().isCUPLotto(saBean.getInfoGara().getDataCreazioneGara()))
				{
				   CupLottoAggAction claAction = new CupLottoAggAction(connection, logger);
				   claAction.updateElencoCup(saBean.getFlagCUP(), saBean.getElencoCup(), saBean.getInfoGara().getIdLotto(), idAggiudicazione, dataInizioAgg, false);
				   
				}
			
			}

			logger.debug("SCHEDA_A_ACTION.save: END --> aggiudicazione.id = " + idAggiudicazione + "aggiudicazione.inizio = " + dataInizioAgg);
			
		}
		else{
			throw new ActionException("Aggiudicazione inesistente");
		}
	}
	
	/*********************************************************************************************************
	 * Viene gestita la conferma delle informazioni inserite
	 * @param saBean Scheda_A
	 * @param flags Boolean
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public void confirm(Scheda_A saBean,Boolean[] flags,String cfUtente) throws ActionException{
		
		logger.debug("SCHEDA_A_ACTION.confirm: START");
		long idAggiudicazione = saBean.getAggiudicazione().getIdAggiudicazione();
		Timestamp dataInizioAgg = saBean.getAggiudicazione().getDataInizioAggiudicazione();
		AggiudicazioneAction aggiudicazioneAction = new AggiudicazioneAction(connection,logger);
		RequisitiAction requisitiAction = new RequisitiAction(connection, logger);
		ResponsabileAction responsabiliAction = new ResponsabileAction(connection, logger);
		AggiudicatarioAction aggiudicatarioAction = new AggiudicatarioAction(connection,logger);
		CondizioniAction condizioniAction = new CondizioniAction(connection,logger);
		TipoAppaltoAction tipoAppaltoAction = new TipoAppaltoAction(connection,logger);
		FinanziamentoAction finanziamentoAction = new FinanziamentoAction(connection,logger);
		CupLottoAggAction claAction = new CupLottoAggAction(connection, logger);
		
		if(aggiudicazioneAction.existAggiudicazione(idAggiudicazione, dataInizioAgg)){
			logger.debug("SCHEDA_A_ACTION.confirm: END --> aggiudicazione.id = " + idAggiudicazione + "aggiudicazione.inizio = " + dataInizioAgg);
			int numAgg = aggiudicazioneAction.confirm(saBean.getAggiudicazione(), cfUtente);
			if(numAgg > 0){
				aggiudicatarioAction.confirm(saBean.getAggiudicatari(),idAggiudicazione,dataInizioAgg);
				responsabiliAction.confirm(saBean.getAllResponsabili(),idAggiudicazione,dataInizioAgg);
				
				requisitiAction.confirm(saBean.getRequisiti(),idAggiudicazione,dataInizioAgg);
				condizioniAction.confirm(saBean.getCondizioni(),idAggiudicazione,dataInizioAgg);
				finanziamentoAction.confirm(saBean.getFinanziamenti(), idAggiudicazione, dataInizioAgg);

            tipoAppaltoAction.setIdLotto(saBean.getTipoFS(), saBean.getInfoGara().getIdLotto());
            tipoAppaltoAction.setIdLotto(saBean.getTipoLavoro(), saBean.getInfoGara().getIdLotto());
            
            if(SimogFlags.is3031_RFWEBGL00Active()
                  && SimogProperties.getInstance().isCUPLotto(saBean.getInfoGara().getDataCreazioneGara()))
            {
               tipoAppaltoAction.completaDatiTipoAppalto(saBean.getInfoGara().getIdLotto(),idAggiudicazione,dataInizioAgg);
            }
            else{
               tipoAppaltoAction.confirm(saBean.getTipoAppalto(),idAggiudicazione,dataInizioAgg);
            }               
				
            if(SimogFlags.is3031_RFWEBGL02Active() 
                  && !SimogProperties.getInstance().isCUPLotto(saBean.getInfoGara().getDataCreazioneGara()))
            {
               claAction.updateElencoCup(saBean.getFlagCUP(),saBean.getElencoCup(), saBean.getInfoGara().getIdLotto(), idAggiudicazione, dataInizioAgg, true);
            }
            
			}

		}
		else{
			throw new ActionException("Operazione non valida");
		}
	}
	
	
	/********************************************************************************************************
	 * Gestisce la richiesta di annullamento 
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public Timestamp richiediAnnullamento(RichiestaAnnullamento bean) throws ActionException{
		Timestamp nuovadata = null;
		if(bean.getBlocco() == null)
			bean.setBlocco(IdentificativoSchede.TAB_AGGIUDICAZIONE);
		nuovadata = new AggiudicazioneAction(connection,logger).richiediAnnullamento(bean);
		logger.debug("SCHEDA_A_ACTION.richiediAnnullamento: START");
		if(nuovadata != null){
			Timestamp datavecchia = bean.getData_inizio_record();
			bean.setData_inizio_record(nuovadata);
//			bean.setBlocco(PSBD.TAB_RESPONSABILE_PROCEDIMENTO);
			if(new ResponsabileAction(connection,logger).richiediAnnullamento(bean,datavecchia))
				if(new FinanziamentoAction(connection,logger).richiediAnnullamento(bean,datavecchia))
					if(new AggiudicatarioAction(connection,logger).richiediAnnullamento(bean,datavecchia))
						if(new CondizioniAction(connection,logger).richiediAnnullamento(bean,datavecchia))
						   if(new RequisitiAction(connection,logger).richiediAnnullamento(bean,datavecchia))
							   if(new TipoAppaltoAction(connection,logger).richiediAnnullamento(bean,datavecchia))
							      if(new CupLottoAggAction(connection, logger).richiediAnnullamento(bean, datavecchia)){
								   	return nuovadata;
							      }
		}
		logger.debug("SCHEDA_A_ACTION.richiediAnnullamento: END -->" + ObjectIntrospector.propertiesInfo(RichiestaAnnullamento.class, bean));
		return null;	
	}
	
	/********************************************************************************************************
	 * Gestisce la richiesta di cancellazione 
	 * @param bean RichiestaAnnullamento
	 * @return Timestamp
	 * @throws ActionException
	 */
	public void richiediCancellazione(RichiestaAnnullamento bean) throws ActionException{
		if(bean.getBlocco() == null) bean.setBlocco(IdentificativoSchede.TAB_AGGIUDICAZIONE);
		logger.debug("SCHEDA_A_ACTION.richiediCancellazione: START");
		
		new AggiudicazioneAction(connection,logger).richiediCancellazione(bean);
		
			
		logger.debug("SCHEDA_A_ACTION.richiediCancellazione: END -->" + ObjectIntrospector.propertiesInfo(RichiestaAnnullamento.class, bean));
	}
	
//	public Scheda_A_Validator validate(HttpServletRequest request,Scheda_A saBean, String sezione) throws ActionException{
//		
//		logger.debug("SCHEDA_A_ACTION.validate: START");
//		Scheda_A_Validator valid = new Scheda_A_Validator(connection, logger, saBean, sezione); 
//		valid.valida();
//		logger.debug("SCHEDA_A_ACTION.save: END");
//		return valid;
//		
//		
//		
//	}

	
	
	/**********************************************************************************************
	 * Si occupa della creazione della Scheda_A
	 * @param saBean Scheda_A
	 * @param flags Boolean[]
	 * @param cfUtente String
	 * @throws ActionException
	 */
	public void create(Scheda_A saBean,Boolean[] flags,String cfUtente) throws ActionException{
		
		logger.debug("SCHEDA_A_ACTION.create: START");
		AggiudicazioneAction aggiudicazioneAction = new AggiudicazioneAction(connection,logger);
		RequisitiAction requisitiAction = new RequisitiAction(connection,logger);
		ResponsabileAction responsabiliAction = new ResponsabileAction(connection,logger);
		AggiudicatarioAction aggiudicatariAction = new AggiudicatarioAction(connection,logger);
		CondizioniAction condizioniAction = new CondizioniAction(connection,logger);
		TipoAppaltoAction tipoAppaltoAction = new TipoAppaltoAction(connection,logger);
		FinanziamentoAction	 finanziamentoAction = new FinanziamentoAction(connection,logger);
		
		aggiudicazioneAction.save(saBean.getAggiudicazione(), cfUtente);
		long idAggiudicazione = saBean.getAggiudicazione().getIdAggiudicazione();
		Timestamp dataInizioAggiudicazione = saBean.getAggiudicazione().getDataInizioAggiudicazione();
		
      // aggancio i cup  a idlotto 
//      if(SimogFlags.is3031_RFWEBGL02Active())
//            aggiudicazioneAction.updateRecordCup(true,saBean);

		requisitiAction.save(saBean.getRequisiti(),idAggiudicazione,dataInizioAggiudicazione);

		responsabiliAction.save(saBean.getAllResponsabili(),idAggiudicazione,dataInizioAggiudicazione);

		aggiudicatariAction.save(saBean.getAggiudicatari(),idAggiudicazione,dataInizioAggiudicazione);
		
		condizioniAction.save(saBean.getCondizioni(),idAggiudicazione,dataInizioAggiudicazione);

	    finanziamentoAction.save(saBean.getFinanziamenti(), idAggiudicazione, dataInizioAggiudicazione);

      if(SimogFlags.is3031_RFWEBGL00Active()){
         tipoAppaltoAction.setIdLotto(saBean.getTipoFS(), saBean.getInfoGara().getIdLotto());
         tipoAppaltoAction.setIdLotto(saBean.getTipoLavoro(), saBean.getInfoGara().getIdLotto());

         // a causa dell'introduzione della funzione di integrazione dati cup, sono costretto a tentare la cancellazione
         // di eventuali record presenti
         try {
            if(!SimogProperties.getInstance().isCUPLotto(saBean.getInfoGara().getDataCreazioneGara()))
               tipoAppaltoAction.getTipoAppaltoManager().deleteAppaltiAgg(saBean.getInfoGara().getIdLotto());
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         
         if(SimogProperties.getInstance().isCUPLotto(saBean.getInfoGara().getDataCreazioneGara()))
         {
            tipoAppaltoAction.completaDatiTipoAppalto(saBean.getInfoGara().getIdLotto(),idAggiudicazione,dataInizioAggiudicazione);
         }
         else{
            // PP aggiungo idlotto
            tipoAppaltoAction.setIdLotto(saBean.getTipoAppalto(), saBean.getInfoGara().getIdLotto());
            tipoAppaltoAction.save(saBean.getTipoAppalto(),idAggiudicazione,dataInizioAggiudicazione);
         }
      }
      else
         tipoAppaltoAction.save(saBean.getTipoAppalto(),idAggiudicazione,dataInizioAggiudicazione);
      
		if( SimogFlags.is3031_RFWEBGL02Active() 
		      //&& !SimogProperties.getInstance().isCUPLotto(saBean.getInfoGara().getDataCreazioneGara())
		){
		   CupLottoAggAction claAction = new CupLottoAggAction(connection, logger);
		   
		   // a causa dell'introduzione della funzione di integrazione dati cup, sono costretto a tentare la cancellazione
		   // di eventuali record presenti
		   try {
            if(!SimogProperties.getInstance().isCUPLotto(saBean.getInfoGara().getDataCreazioneGara()))
               claAction.getCupLottoAggManager().deleteCup(saBean.getInfoGara().getIdLotto(), false);
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }

         if(!SimogProperties.getInstance().isCUPLotto(saBean.getInfoGara().getDataCreazioneGara()))
            claAction.updateElencoCup(saBean.getFlagCUP(),saBean.getElencoCup(), saBean.getInfoGara().getIdLotto(), idAggiudicazione, dataInizioAggiudicazione, false);
         else
            claAction.completaDatiAggCup(saBean.getInfoGara().getIdLotto(), idAggiudicazione, dataInizioAggiudicazione);
		}
		
		logger.debug("SCHEDA_A_ACTION.create: END");
		
	}	
		
	/*********************************************************************************
	 * Carica la scelta del contraente
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @param idOss 
	 * @return Map&lt;String, String&gt; - id, descrizione
	 * @throws ActionException
	 */
	public Map<String, String> loadSceltaContraente(Object o, String cfAmm, String idOss) throws ActionException{
		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);
		try {
			GaraManager gm = new GaraManager(connection, logger);
			boolean isOrgano = gm.isOrganoCost(cfAmm, o);
			return man.loadSceltaContraente(o, isOrgano, idOss);
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/***********************************************************************************
	 * Carica le condizioni aggiuntive della scheda
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadCondizioniAggiuntive(Object o) throws ActionException{
		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);
		try {
			return man.loadCondizioniAggiuntive(o);
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/*************************************************************************************
	 * Carica i criteri di aggiudicazione
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadCriteriAggiudicazione(Object o) throws ActionException{
		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);
		try {
			return man.loadCriteriAggiudicazione(o);
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/***********************************************************************************
	 * Carica le modalit� di indizione
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadModoIndizione(Object o) throws ActionException{
		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);
		try {
			return man.loadModoIndizione(o);
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/*******************************************************************************************
	 * Carica le categorie del lotto specificato in ingresso attraverso l'id
	 * @param idlotto long
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
//	public Map<String, String> loadCategoria(long idlotto,Object o) throws ActionException{
//		
//		RequisitiManager rman = new RequisitiManager(connection, logger);
//		try {
//			return rman.caricaCategorie(idlotto,o);
//		} catch (Exception e) {
////			log come fatal demandato al chiamante
//			logger.error(e);
//			throw new ActionException(e);
//		}
//	}
	/*******************************************************************************************
	 * Carica tutte le categorie "lavori"

	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadCategoria(Object o, String cfAmm) throws ActionException{
		
		
		RequisitiManager rman = new RequisitiManager(connection, logger);
		try {
			GaraManager gm = new GaraManager(connection, logger);
			boolean isOrgano = gm.isOrganoCost(cfAmm, o);

			Map<String, String> tipologicaM = rman.caricaCategorie(o);
			// se non è organo costituzionale elimino la voce riservata (20)
			if(!isOrgano)
				tipologicaM.remove(Costanti.CATEGORIA_PREV_OOCC);

			return tipologicaM;
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}	
	/**************************************************************************************
	 * Carica le classi di importo
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadClasseImporto(Object o) throws ActionException{
		RequisitiManager rman = new RequisitiManager(connection, logger);
		try {
			return rman.caricaClassiImporto(o);
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	
	/****************************************************************************************
	 * Si occupa del caricamento delle tipologie di aggiudicatario
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadTipoAggiudicatario(Object o) throws ActionException{
		AggiudicatarioManager man = new AggiudicatarioManager(connection, logger);
		try {
			return man.loadTipoAggiudicatario(o);
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	/****************************************************************************************
	 * Carica i Ruoli della sezione
	 * @param sezione
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadRuoliSezione(String sezione,Object o) throws ActionException{
		ResponsabileManager rman = new ResponsabileManager(connection, logger);
		try {
			return rman.loadRuoliSezione(sezione,o);
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/******************************************************************************************
	 * Carica i dati relativi ai finanziamenti
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadFinanziamenti(Object o) throws ActionException{
		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);
		try {
			return man.loadFinanziamenti(o);
		} catch (Exception e) {
//			log come fatal demandato al chiamante
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/********************************************************************************************
	 * Carica le informazioni relative agli strumenti della scheda
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadStrumenti(Object o) throws ActionException{
		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);
		try {
			return man.loadStrumenti(o);
		} catch (Exception e) {
			logger.error(e);
			throw new ActionException(e);
		}
	}
	
	/********************************************************************************************
	 * Carica le informazioni relative ai modi riaggiudicazione della scheda
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String, String&gt;
	 * @throws ActionException
	 */
	public Map<String, String> loadModiRiaggiud(Object o) throws ActionException{
		AggiudicazioniManager man = new AggiudicazioniManager(connection, logger);
		try {
			return man.loadModiRiaggiud(o);
		} catch (Exception e) {
			logger.error(e);
			throw new ActionException(e);
		}
	}
	/****************************************************************************************************
	 * Crea la lista degli <code>AggiudicatarioBean</code> inserendo gli Aggiudicatari più
	 * gli Aggiudicatari dei raggruppamenti nella Stringa ditteRaggruppamentoString
	 * @param Scheda_A saBean
	 * @return Scheda_A
	 */
	public Scheda_A esplodiAggiudicatari(Scheda_A saBean)throws ActionException  {
		List <AggiudicatarioBean> aggiudicatari = saBean.getAggiudicatari();
		List<AggiudicatarioBean> result = new ArrayList<AggiudicatarioBean>();
		int idGruppo = 0;
		String [] parametriAttesi ={PSBD.FIELD_NAME_AGG_DENOMINAZIONE,PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO,PSBD.FIELD_NAME_AGG_ID_PAESE,
	    		PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE,PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG, PSBD.FIELD_NAME_ANAGOE};
		//per ogni aggiudicatario
		for(AggiudicatarioBean aggCorrente : aggiudicatari){
			//idLista tiene memoria della posizione nella lista mostrata in jsp, vale per imprese singole e gruppi
			//se è un'ATI o Consorzio
			if(aggCorrente.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_ATI || aggCorrente.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_CONSORZIO){  
				if(aggCorrente.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_ATI)
					aggCorrente.setRuolo(Costanti.MANDATARIA);
				idGruppo = idGruppo+1;
	    		//gm il progressivo raggruppamento è gestito automaticamente per simog 3.05
	    		aggCorrente.setIdGruppo(idGruppo);    		

	        	List<AggiudicatarioBean> ditteRaggruppamento = new ArrayList<AggiudicatarioBean>();
	        	String ditteRaggruppamentoString = aggCorrente.getDitteRaggruppamentoString();
	        	if(ditteRaggruppamentoString!=null && !"".equals(ditteRaggruppamentoString)){
	        		//ogni elemento dell'array sarà un Aggiudicatario componente del raggruppamento d'impresa
	        		String [] ditteAux = ditteRaggruppamentoString.split("~");
	        		for(int i=0; i<ditteAux.length; i++){
		    		    //ogni elemento dell'array sarà un campo dell'Aggiudicatario del raggruppamento
		    	    	String [] dittaAux = ditteAux[i].split("\\|");
		    	    	if(dittaAux!=null){
			        		AggiudicatarioBean dittaBean = new AggiudicatarioBean();
			        		SoggettoPartecipanteBean sogg = new SoggettoPartecipanteBean();
			        		for(int j=0; j<dittaAux.length && j<parametriAttesi.length; j++){	    
			        		    if(parametriAttesi[j].equals(PSBD.FIELD_NAME_AGG_DENOMINAZIONE)){
				        	    	sogg.setDenominazione(dittaAux[j]);
				        	    }
				    	        else if(parametriAttesi[j].equals(PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO)){
				        	    	sogg.setCodiceFiscale(dittaAux[j]);
					            }
			    	    	    else if(parametriAttesi[j].equals(PSBD.FIELD_NAME_AGG_ID_PAESE)){
			    	    	    	if(dittaAux[j]==null || "".equals(dittaAux[j]))
					                	sogg.setId_stato(dittaAux[j]);
			    	    	    	else
			    	    	    		sogg.setId_stato(Costanti.CODICE_STATO_ITALIANO);
			    	    	    }
			    	    	    else if(parametriAttesi[j].equals(PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE)){ 
				        	    	sogg.setIdSoggettoPartecipante(Long.parseLong(dittaAux[j]));
			        		    }		
				        		else if(parametriAttesi[j].equals(PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG)){ 
				        			sogg.setDataInizioSogg(PageHelper.parseTime(dittaAux[j]));
				        		}else if(parametriAttesi[j].equals(PSBD.FIELD_NAME_ANAGOE)){ 
				    	    		// PP se esistono variazioni anagrafiche le memorizzo
				    	    		String datiAnag = dittaAux[j];
				    	    		if (datiAnag != null && !"".equals(datiAnag) && !"*".equals(datiAnag)){
				    	    			
						    			String [] val = Base64Coder.decodeString(datiAnag).split(PSBD.SEP_VARANAG_S,-1);

				    	    			sogg.setIdSoggettoPartecipante(Long.parseLong(val[0]));
				    	    			sogg.setCodiceFiscale(val[1]);
				    	    			sogg.setDenominazione(val[2]);
				    	    			sogg.setCameraCommercio(val[3]);
				    	    			sogg.setPartitaIva(val[4]);
				    	    			sogg.setIndirizzo(val[5]);
				    	    			sogg.setCivico(val[6]);
				    	    			sogg.setCitta(val[7]);
				    	    			sogg.setProvincia(val[8]);
				    	    			sogg.setCap(val[9]);
				    	    			sogg.setCfRappresentante(val[10]);
				    	    			sogg.setCognome(val[11]);
				    	    			sogg.setNome(val[12]);
				    	    			sogg.setId_stato(val[13]);
				    	    			
				    	    			// setto il flag per indicare la successiva modifica
				    	    			sogg.setModifica(true);
				    	    		}
				        		}
			        	    }
			    	    	dittaBean.setSoggettoPartecipante(sogg);
			        		dittaBean.setIdGruppo(aggCorrente.getIdGruppo());
					
        		    		if(aggCorrente.getIdAggiudicazione()!=0){
	            				dittaBean.setIdAggiudicazione(aggCorrente.getIdAggiudicazione());
		            		}
	    	    	    	if(aggCorrente.getDataInizioAggiudicazione()!=null){
	    	    		    	dittaBean.setDataInizioAggiudicazione(aggCorrente.getDataInizioAggiudicazione());
	    	    		    }
		        	    	if(aggCorrente.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_ATI)
	        	        		//gm per convenzione gli aggiudicatari della lista sono le mandanti
		        	    		dittaBean.setRuolo(Costanti.MANDANTE);
	        	        	if(aggCorrente.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_ATI)
	        	        		dittaBean.setIdTipoAgg(Costanti.TIPODITTA_LIKE_ATI);
	    	            	if(aggCorrente.getIdTipoAgg()==Costanti.TIPODITTA_LIKE_CONSORZIO)
	    	            		dittaBean.setIdTipoAgg(Costanti.TIPODITTA_LIKE_CONSORZIO);
	    	    	
			            	ditteRaggruppamento.add(dittaBean);
			        	}
		        	}
	        	}
	            result.addAll(ditteRaggruppamento);
	    	}
	    	result.add(aggCorrente);
    	}
		saBean.setAggiudicatari(result);
		
		
		/* ESPLODI INCARICATI */
		List <ResponsabileBean> responsabili = saBean.getPrestazioni();
		List<ResponsabileBean> resultResp = new ArrayList<ResponsabileBean>();
		idGruppo = 0;
		//per ogni aggiudicatario
		for(ResponsabileBean respCorrente : responsabili){

			if(PSBD.SEZIONE_PA.equals(respCorrente.getSezione()) && respCorrente.getIdRuolo()==Costanti.TIPO_INCARICATO_ATI) {
				respCorrente.setMandante(false);
				idGruppo = idGruppo+1;
				respCorrente.setIdGruppo(idGruppo);
				
	        	List<ResponsabileBean> ditteRaggruppamento = new ArrayList<ResponsabileBean>();
	        	String ditteRaggruppamentoString = respCorrente.getDitteRaggruppamentoString();
	        	if(ditteRaggruppamentoString!=null && !"".equals(ditteRaggruppamentoString)){
	        		//ogni elemento dell'array sarà un Aggiudicatario componente del raggruppamento d'impresa
	        		String [] ditteAux = ditteRaggruppamentoString.split("~");
	        		for(int i=0; i<ditteAux.length; i++){
	        			//ogni elemento dell'array sarà un campo dell'Aggiudicatario del raggruppamento
		    	    	String [] dittaAux = ditteAux[i].split("\\|");
		    	    	if(dittaAux!=null){
		    	    		ResponsabileBean dittaBean = new ResponsabileBean();
			        		SoggettoPartecipanteBean sogg = new SoggettoPartecipanteBean();
			        		
			        		for(int j=0; j<dittaAux.length && j<parametriAttesi.length; j++){	    
			        		    if(parametriAttesi[j].equals(PSBD.FIELD_NAME_AGG_DENOMINAZIONE)){
				        	    	sogg.setDenominazione(dittaAux[j]);
				        	    }
				    	        else if(parametriAttesi[j].equals(PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO)){
				        	    	sogg.setCodiceFiscale(dittaAux[j]);
					            }
			    	    	    else if(parametriAttesi[j].equals(PSBD.FIELD_NAME_AGG_ID_PAESE)){
			    	    	    	if(dittaAux[j]==null || "".equals(dittaAux[j]))
					                	sogg.setId_stato(dittaAux[j]);
			    	    	    	else
			    	    	    		sogg.setId_stato(Costanti.CODICE_STATO_ITALIANO);
			    	    	    }
			    	    	    else if(parametriAttesi[j].equals(PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE)){ 
				        	    	sogg.setIdSoggettoPartecipante(Long.parseLong(dittaAux[j]));
			        		    }		
				        		else if(parametriAttesi[j].equals(PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG)){ 
				        			sogg.setDataInizioSogg(PageHelper.parseTime(dittaAux[j]));
				        		}else if(parametriAttesi[j].equals(PSBD.FIELD_NAME_ANAGOE)){ 
				    	    		// PP se esistono variazioni anagrafiche le memorizzo
				    	    		String datiAnag = dittaAux[j];
				    	    		if (datiAnag != null && !"".equals(datiAnag) && !"*".equals(datiAnag)){
				    	    			
						    			String [] val = Base64Coder.decodeString(datiAnag).split(PSBD.SEP_VARANAG_S,-1);

				    	    			sogg.setIdSoggettoPartecipante(Long.parseLong(val[0]));
				    	    			sogg.setCodiceFiscale(val[1]);
				    	    			sogg.setDenominazione(val[2]);
				    	    			sogg.setCameraCommercio(val[3]);
				    	    			sogg.setPartitaIva(val[4]);
				    	    			sogg.setIndirizzo(val[5]);
				    	    			sogg.setCivico(val[6]);
				    	    			sogg.setCitta(val[7]);
				    	    			sogg.setProvincia(val[8]);
				    	    			sogg.setCap(val[9]);
				    	    			sogg.setCfRappresentante(val[10]);
				    	    			sogg.setCognome(val[11]);
				    	    			sogg.setNome(val[12]);
				    	    			sogg.setId_stato(val[13]);
				    	    			
				    	    			// setto il flag per indicare la successiva modifica
				    	    			sogg.setModifica(true);
				    	    		}
				        		}
			        	    }
			        		
			        		dittaBean.setSoggettoPartecipante(sogg);
			        		dittaBean.setIdGruppo(respCorrente.getIdGruppo());
	         	    		dittaBean.setMandante(true);
	         	    		dittaBean.setSezione(PSBD.SEZIONE_PA);
	         	    		dittaBean.setIdRuolo(Costanti.TIPO_INCARICATO_ATI);
	    	    	
			            	ditteRaggruppamento.add(dittaBean);
			        		
		    	    	}
	        		}
	        		
	        		respCorrente.setDitteRaggruppamentoString(fixStringRaggr(respCorrente.getDitteRaggruppamentoString()));
	        	} 
	        	resultResp.addAll(ditteRaggruppamento);
			}  
		  resultResp.add(respCorrente);
		}
		saBean.setPrestazioni(resultResp);
		
	    return saBean;
    }
	
	private String fixStringRaggr(String ditteRaggruppamentoString) {
		String res = "";
		
		String [] ditteAux = ditteRaggruppamentoString.split("~");
		for(int i=0; i<ditteAux.length; i++){
			String curr = ditteAux[i];
			String[] fields = curr.split("\\|");
			for(int j=0;j<fields.length-2;j++) 
				res+=fields[j]+"|";
			
			res+="~";
		}
		
		return res;
	}

	/****************************************************************************************************
	 * Riorganizza la lista degli<code>AggiudicatarioBean</code> raggruppando gli Aggiudicatari
	 * dello stesso tipo e idGruppo in una stringa e restituendo la nuova lista alla Scheda_A
	 * @param Scheda_A saBean
	 * @return Scheda_A
	 */
	public Scheda_A implodiAggiudicatari(Scheda_A saBean)throws ActionException  {
		List<AggiudicatarioBean> aggiudicatari = saBean.getAggiudicatari();
		List<AggiudicatarioBean> result = new ArrayList<AggiudicatarioBean>();
		Map <Long,List<AggiudicatarioBean>> mappaAggiudicatariPerGruppo = new HashMap<Long,List<AggiudicatarioBean>>();
		List<AggiudicatarioBean> listaAggiudicatariPerGruppo;
		List <Long> listIdGruppo = new ArrayList<Long>();
		for(AggiudicatarioBean agg : aggiudicatari){
			if(!listIdGruppo.contains(Long.valueOf(agg.getIdGruppo())))
				listIdGruppo.add(Long.valueOf(agg.getIdGruppo()));
			//creo la mappa degli aggiudicatari organizzati per idGruppo
			listaAggiudicatariPerGruppo = mappaAggiudicatariPerGruppo.get(Long.valueOf(agg.getIdGruppo()));
			if(listaAggiudicatariPerGruppo==null)
				listaAggiudicatariPerGruppo = new ArrayList<AggiudicatarioBean>();
		   	listaAggiudicatariPerGruppo.add(agg);	
			mappaAggiudicatariPerGruppo.put(Long.valueOf(agg.getIdGruppo()), listaAggiudicatariPerGruppo);		
		}
		
		for(Long idGruppo : listIdGruppo){
			List<AggiudicatarioBean> listaAggiudicatariDaRaggruppare = mappaAggiudicatariPerGruppo.get(idGruppo);
			//se non ho idGruppo gli Aggiudicatari sono singoli e devono esseree aggiunti tutti al risultato
			if(idGruppo == 0)
				result.addAll(listaAggiudicatariDaRaggruppare);
			else{
				//Collections.sort(listaAggiudicatariDaRaggruppare,new ComparaAggiudicatarioPerIdAggiudicatario());
				//per l'ordinamento scelto, l'ultimo della lista è il capogruppo
				AggiudicatarioBean aggCapoGruppo;
				//gm per gli ATI
				if(listaAggiudicatariDaRaggruppare.get(0).getIdTipoAgg()==Costanti.TIPODITTA_LIKE_ATI)
			    	aggCapoGruppo = findCapoGruppoATI(listaAggiudicatariDaRaggruppare);
				//gm per i Consorzi
				else
			        aggCapoGruppo = listaAggiudicatariDaRaggruppare.get(listaAggiudicatariDaRaggruppare.size()-1);
				
				listaAggiudicatariDaRaggruppare.remove(aggCapoGruppo);
				String ditteRaggruppamentoString = "";
				ditteRaggruppamentoString = creaDitteRaggruppamentoString(listaAggiudicatariDaRaggruppare);
				aggCapoGruppo.setDitteRaggruppamentoString(ditteRaggruppamentoString);
				result.add(aggCapoGruppo);
			}
		}
		saBean.setAggiudicatari(result);
		return saBean;
	}
	
	/****************************************************************************************************
	 * Crea la ditteRaggruppamentoString dell'<code>AggiudicatarioBean</code> inserendo i valori relativi
	 * alla lista List<AggiudicatarioBean> passata in input
	 * @param List<AggiudicatarioBean>
	 * @return String ditteRaggruppamentoString
	 */
	
	public String creaDitteRaggruppamentoString(List<AggiudicatarioBean> aggiudicatari)throws ActionException  {
		String mtd = "creaDitteRaggruppamentoString";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		String ditteRaggruppamentoString = "";
        
		if(aggiudicatari!=null && !aggiudicatari.isEmpty()){
			String record = "";
			for(AggiudicatarioBean dittaBean : aggiudicatari){
				record = record + 
				dittaBean.getSoggettoPartecipante().getDenominazione() + "|" +
				dittaBean.getSoggettoPartecipante().getCodiceFiscale() + "|" +
				dittaBean.getSoggettoPartecipante().getId_stato() + "|" +
				String.valueOf(dittaBean.getSoggettoPartecipante().getIdSoggettoPartecipante()) + "|" +
				PageHelper.formatTimeStamp(dittaBean.getSoggettoPartecipante().getDataInizioSogg()) + "|" + 
				dittaBean.getSoggettoPartecipante().getDatiModifica() + "|";
				ditteRaggruppamentoString = ditteRaggruppamentoString + record + "~";
				record = "";
			}
		}	
        return ditteRaggruppamentoString;
	}
	
	/****************************************************************************************************
	 * Trova il Capo Gruppo della lista di <code>AggiudicatarioBean</code> inserendo i valori relativi
	 * alla lista List<AggiudicatarioBean> passata in input
	 * @param List<AggiudicatarioBean>
	 * @return String ditteRaggruppamentoString
	 */
	public AggiudicatarioBean findCapoGruppoATI(List<AggiudicatarioBean> aggiudicatari)throws ActionException  {
		AggiudicatarioBean capoGruppo = null;
        
		if(aggiudicatari!=null && !aggiudicatari.isEmpty()){
			for(AggiudicatarioBean aggBean : aggiudicatari){
				if(Costanti.MANDATARIA.equals(aggBean.getRuolo())){
					capoGruppo = aggBean;
				}
			}
		}	
        return capoGruppo;
	}

	@Deprecated
   public void updateRecordCup(boolean aggancia, Scheda_A saBean) {
      new AggiudicazioneAction(connection,logger).updateRecordCup(aggancia, saBean);      
   }
}
