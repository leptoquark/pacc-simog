package it.avlp.simog.flusso.business.impl;

import it.avcp.simog.managers.accordo.AccordoManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.avanzamento.AvanzamentoManager;
import it.avcp.simog.managers.collaudo.CollaudoManager;
import it.avcp.simog.managers.conclusione.ConclusioniManager;
import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avcp.simog.managers.r129.R129Manager;
import it.avcp.simog.managers.sospensioni.SospensioniManager;
import it.avcp.simog.managers.stipula.StipulaManager;
import it.avcp.simog.managers.subappalti.SubappaltiManager;
import it.avcp.simog.managers.variante.VarianteManager;
import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.beans.StatoScheda;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.beans.stipula.StipulaBean;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.fllusso.action.RichiesteAction;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.SimogProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class LoadSituazioneByDatiAggiudicazione {

	protected Logger logger;
	protected Connection con;
	
	/**
	 * Costruttore usato dalla classe estesa load by id scheda
	 * (necessita della connessione)
	 * @param con
	 * @param logger
	 */
	public LoadSituazioneByDatiAggiudicazione(Connection con, Logger logger){
		this.con = con;
		this.logger = logger;
	}

	/**
	 * Metodo che serve a caricare tutte informazioni delle schede relative ad un CIG
	 * (Livello dati comuni ?) da usare solamente nel caso non ci siano aggiudicazioni ovvero
	 * il CUI in quanto obligatorio e' valorizzato con il valore stringa vuota.
	 * 
	 * @param CIG
	 * @return
	 */
	public ArrayList<SituazioneSchedeAttuale> loadSituazioneByCIG(String CIG) throws SQLException,Exception{
		ArrayList<SituazioneSchedeAttuale> situazioniAttuali = new ArrayList<SituazioneSchedeAttuale>();
		try{
			InfoComuniManager infoComuniManager = new InfoComuniManager(con, logger);
			// PATCH - VL - 09-02-2010 gestione della somma urgenza
			InfoComuniBean infoComuniBean = infoComuniManager.getInfoComuniByCig(CIGBean.getRealCIG(CIG));
			
			AggiudicazioniManager aggiudicazioneManager = new AggiudicazioniManager(con, logger);
			List<AggiudicazioneBean> listOfAggiudicazioni = aggiudicazioneManager.getAggiudicazioniList(infoComuniBean.getIdInfo(), infoComuniBean.getDataInizioInfo());
			int listSize = listOfAggiudicazioni.size();
			if(listSize == 0){
				SituazioneSchedeAttuale situazioneAttuale = new SituazioneSchedeAttuale();
				
				RichiesteAction richiesteAction = new RichiesteAction(con,logger);
				int inRichiesta = richiesteAction.isInRichiesta(IdentificativoSchede.getDatiComuni().getNomeScheda(), false, infoComuniBean.getIdInfo(), infoComuniBean.getDataInizioInfo());
				
				//TICKET ALM #11228 - 3.04.3.2
				boolean obblighiComunicativiSpeciali = false;
				if(infoComuniBean.getIdLotto()>0)
					obblighiComunicativiSpeciali = fillObblighiComunicativiSpeciali(infoComuniBean);
				
				situazioneAttuale.setStatoDatiComuni(infoComuniBean, CIG, inRichiesta,obblighiComunicativiSpeciali);
				situazioniAttuali.add(situazioneAttuale);
			}else{
				for(AggiudicazioneBean aggiudicazioneCorrente : listOfAggiudicazioni){
					SituazioneSchedeAttuale situazioneAttuale = new SituazioneSchedeAttuale();
					situazioniAttuali.add(situazioneAttuale);
					this.fillSituazione(situazioneAttuale, aggiudicazioneCorrente.getIdAggiudicazione(), aggiudicazioneCorrente.getDataInizioAggiudicazione());
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("Errore durate il caricamento a partire dal cig["+CIG+"], "+e.getMessage());
			throw new Exception("Errore durate il caricamento a partire dal cig["+CIG+"], ",e);
		}
		return situazioniAttuali;
	}
//	
	/**
	 * Metodo che serve a caricare  tutte le informazioni delle schede relative ad un CUI
	 * (Livello Aggiudicazione)
	 * 
	 * @param CUI
	 * @return
	 */
	public SituazioneSchedeAttuale loadSituazioneByCUI(String CUI) throws SQLException,Exception{
		SituazioneSchedeAttuale situazioneAttuale = new SituazioneSchedeAttuale();
		try{
			AggiudicazioniManager aggiudicazioneManager = new AggiudicazioniManager(con, logger);
			AggiudicazioneBean aggiudicazioneBean = aggiudicazioneManager.getAggiudicazioneByProgAndCui(CUI, false);
			
			//if (aggiudicazioneBean == null)
				//throw new Exception(Messaggi.SIMOG_MASSLOADER_174.replace("$1",CUI ));
			if(aggiudicazioneBean != null)
    			this.fillSituazione(situazioneAttuale, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
			
		}catch (Exception e) {
			//e.printStackTrace();
			logger.debug("Errore durate il caricamento a partire dal cui["+CUI+"], "+e.getMessage());
			throw new Exception("Errore durate il caricamento a partire dal cui["+CUI+"], ",e);
		}return situazioneAttuale;
	}
	/**
	 * Metodo che si occupa di valorizzare la "Situazione attuale" delle schede, in base ai riferimenti dell'aggiudicazione
	 * 
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @return
	 */
	protected void fillSituazione(SituazioneSchedeAttuale situazioneAttuale, long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException,Exception{	
		int numeroOperazione = -1;
		// XXX: VL - problema collocazione anomalia in feedback quando errore occorre nel controllo flusso
		String cig = "";
		String cui = "";
		
		try{
			RichiesteAction richiesteAction = new RichiesteAction(con,logger);
			
			/** Aggiudicazione **/
			numeroOperazione = 1;
			AggiudicazioniManager aggiudicazioneManager = new AggiudicazioniManager(con, logger);
			AggiudicazioneBean aggiudicazioneBean = aggiudicazioneManager.getAggiudicazioni(idAggiudicazione, dataInizioAggiudicazione, false);
			
			if(aggiudicazioneBean != null){
				if(aggiudicazioneBean.getSottotipo().equals(TipoAggiudicazione.A)){
					int inRichiesta = richiesteAction.isInRichiesta(IdentificativoSchede.getAggiudicazione().getNomeScheda(), false, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
					
					situazioneAttuale.setStatoAggiudicazione(aggiudicazioneBean, inRichiesta);
					cig = situazioneAttuale.getStatoAggiudicazioneSottotipo().getCig();
					cui = situazioneAttuale.getStatoAggiudicazioneSottotipo().getCui();
				}
				else if(aggiudicazioneBean.getSottotipo().equals(TipoAggiudicazione.E)){
					int inRichiesta = richiesteAction.isInRichiesta(IdentificativoSchede.getEscluso().getNomeScheda(), false, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());			
					situazioneAttuale.setStatoEscluso(aggiudicazioneBean, inRichiesta);
					cig = situazioneAttuale.getStatoEscluso().getCig();
					cui = situazioneAttuale.getStatoEscluso().getCui();
				}
				else if(aggiudicazioneBean.getSottotipo().equals(TipoAggiudicazione.S)){
					int inRichiesta = richiesteAction.isInRichiesta(IdentificativoSchede.getSottosoglia().getNomeScheda(), false, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());			
					situazioneAttuale.setStatoSottosoglia(aggiudicazioneBean, inRichiesta);
					cig = situazioneAttuale.getStatoSottosoglia().getCig();
					cui = situazioneAttuale.getStatoSottosoglia().getCui();
				}
				else if(aggiudicazioneBean.getSottotipo().equals(TipoAggiudicazione.Q)){
					int inRichiesta = richiesteAction.isInRichiesta(IdentificativoSchede.getAdesione().getNomeScheda(), false, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());			
					situazioneAttuale.setStatoAdesione(aggiudicazioneBean, inRichiesta);
					cig = situazioneAttuale.getStatoAdesione().getCig();
					cui = situazioneAttuale.getStatoAdesione().getCui();
				}
			}
//			logger.debug("Stato Aggiudicazione: " + situazioneAttuale.getStatoAggiudicazione().toString());
			
			/** dati comuni **/
			numeroOperazione = 0;
			InfoComuniManager infoComuniManager = new InfoComuniManager(con, logger);
			InfoComuniBean infoComuniBean = infoComuniManager.load(aggiudicazioneBean.getIdInfo(), aggiudicazioneBean.getDataInizioInfo());

			if(infoComuniBean != null){
				int inRichiesta = richiesteAction.isInRichiesta(IdentificativoSchede.getDatiComuni().getNomeScheda(), false, infoComuniBean.getIdInfo(), infoComuniBean.getDataInizioInfo());
				
				//TICKET ALM #11228 - 3.04.3.2
				boolean obblighiComunicativiSpeciali = false;
				if(infoComuniBean.getIdLotto()>0)
					obblighiComunicativiSpeciali = fillObblighiComunicativiSpeciali(infoComuniBean);
				
				situazioneAttuale.setStatoDatiComuni(infoComuniBean,aggiudicazioneBean.getCui().split("-")[1], inRichiesta,obblighiComunicativiSpeciali);
				// XXX: more info for feedback
				this.fillCigAndCuiOnStatoScheda(situazioneAttuale.getStatoDatiComuni(), cig, cui);
			}
//			logger.debug("Stato DatiComuni: " + situazioneAttuale.getStatoDatiComuni().toString());
			// se il bean dell'aggiudicazione non e null se il cui non e null se lo split ritorna esattamente 2 elementi
			
			/** inizio lavori **/
			numeroOperazione = 2;
			InizioLavoriManager inizioManager = new InizioLavoriManager(con, logger);
			InizioLavoriBean inizioBean = inizioManager.load(idAggiudicazione, dataInizioAggiudicazione);
			
			if(inizioBean != null){
				int inRichiesta = richiesteAction.isInRichiesta(IdentificativoSchede.getInizioLavori().getNomeScheda(), false, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
				
				situazioneAttuale.setStatoInizioLavori(inizioBean,inRichiesta);
				// XXX: more info for feedback
				this.fillCigAndCuiOnStatoScheda(situazioneAttuale.getStatoInizioLavori(), cig, cui);
			}
			
			
//			logger.debug("Stato InizioLavori: " + situazioneAttuale.getStatoInizioLavori().toString());
			
			/** avanzamento **/
			numeroOperazione = 3;
			AvanzamentoManager avanzamentoManager = new AvanzamentoManager(con, logger);
			List<AvanzamentoBean> listOfAvanzamenti = avanzamentoManager.loadMany(idAggiudicazione, dataInizioAggiudicazione);
			
			if(listOfAvanzamenti != null && listOfAvanzamenti.size() > 0){
				ArrayList<Integer> listOfInRichiesta = new ArrayList<Integer>(); 
				for(AvanzamentoBean avanzamentoBean : listOfAvanzamenti){
					
					int inRichiesta = richiesteAction.isInRichiesta(IdentificativoSchede.getAvanzamenti().getNomeScheda(), true, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
					listOfInRichiesta.add(Integer.valueOf(inRichiesta));
				}
				situazioneAttuale.setStatoAvanzamento(listOfAvanzamenti,listOfInRichiesta);
				// XXX: more info for feedback
				this.fillCigAndCuiOnStatoScheda(situazioneAttuale.getStatoAvanzamento(), cig, cui);
				
			}
//			logger.debug("Stato Avanzamento: " + situazioneAttuale.getStatoAvanzamento().toString());
			
			
			/** conclusioni **/
			numeroOperazione = 4;
			ConclusioniManager conclusioniManager = new ConclusioniManager(con, logger);
			ConclusioneBean conclusioneBean = conclusioniManager.load(idAggiudicazione, dataInizioAggiudicazione);

			if(conclusioneBean != null){
				int inRichiesta = richiesteAction.isInRichiesta(IdentificativoSchede.getConclusione().getNomeScheda(), false, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
				
				situazioneAttuale.setStatoConclusione(conclusioneBean,inRichiesta);
				// XXX: more info for feedback
				this.fillCigAndCuiOnStatoScheda(situazioneAttuale.getStatoConclusione(), cig, cui);
			}
//			logger.debug("Stato Conclusioni: " + situazioneAttuale.getStatoConclusione().toString());
			
			/** collaudo **/
			numeroOperazione = 5;
			CollaudoManager collaudoManager = new CollaudoManager(con, logger);
			CollaudoBean collaudoBean = collaudoManager.load(idAggiudicazione, dataInizioAggiudicazione);
			// ATTENZIONE IL MANAGER RESTITUISCE UN BEAN NON NULLO ANCHE SE NON HA TROVATO NULLA SUL DB..
			// QUINDI PERFEZIONO IL CONTROLLO			
			if(collaudoBean != null && collaudoBean.getIdCollaudo() > 0){
				int inRichiesta = richiesteAction.isInRichiesta(IdentificativoSchede.getCollaudo().getNomeScheda(), false, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
				
				situazioneAttuale.setStatoCollaudo(collaudoBean,inRichiesta);
				// XXX: more info for feedback
				this.fillCigAndCuiOnStatoScheda(situazioneAttuale.getStatoCollaudo(), cig, cui);
			}
//			logger.debug("Stato Collaudo: " + situazioneAttuale.getStatoCollaudo().toString());
			
			/** ritardo **/
			numeroOperazione = 6;
			R129Manager recessoManager = new R129Manager(con, logger);
			List<R129Bean> listOfR129 = recessoManager.loadMany(idAggiudicazione, dataInizioAggiudicazione);
			
			if(listOfR129 != null && listOfR129.size() > 0){
				ArrayList<Integer> listOfInRicerca = new ArrayList<Integer>();
				for(R129Bean r129Bean : listOfR129){
					
					int inRichiesta = richiesteAction.isInRichiesta(IdentificativoSchede.getRitardo().getNomeScheda(), true, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
					listOfInRicerca.add(Integer.valueOf(inRichiesta));
					
				}
				situazioneAttuale.setStatoRitardo(listOfR129,listOfInRicerca);
				// XXX: more info for feedback
				this.fillCigAndCuiOnStatoScheda(situazioneAttuale.getStatoRitardo(), cig, cui);
			}
//			logger.debug("Stato Ritardo: " + situazioneAttuale.getStatoRitardo().toString());
			
			/** sospensioni **/
			numeroOperazione = 7;
			SospensioniManager sospensioniManager = new SospensioniManager(con, logger);
			List<SospensioniBean> listOfSospensioni = sospensioniManager.loadMany(idAggiudicazione, dataInizioAggiudicazione);
			
			if(listOfSospensioni != null && listOfSospensioni.size() > 0){
				ArrayList<Integer> listOfInRicerca = new ArrayList<Integer>();
				for(SospensioniBean sospensioniBean : listOfSospensioni){
					
					int inRichiesta = richiesteAction.isInRichiesta(IdentificativoSchede.getSospensioni().getNomeScheda(), true, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
					listOfInRicerca.add(Integer.valueOf(inRichiesta));	
				}
				situazioneAttuale.setStatoSospensioni(listOfSospensioni,listOfInRicerca);
				// XXX: more info for feedback
				this.fillCigAndCuiOnStatoScheda(situazioneAttuale.getStatoSospensioni(), cig, cui);
			}
//			logger.debug("Stato Sospensioni: " + situazioneAttuale.getStatoSospensioni().toString());
			
			/** accordo **/
			numeroOperazione = 8;
			AccordoManager accordoManager = new AccordoManager(con, logger);
			List<AccordoBean> listOfAccordi = accordoManager.loadMany(idAggiudicazione, dataInizioAggiudicazione);
			
			if(listOfAccordi != null && listOfAccordi.size() > 0){
				ArrayList<Integer> listOfInRicerca = new ArrayList<Integer>();
				for(AccordoBean accordoBean : listOfAccordi){
					
					int inRichiesta = richiesteAction.isInRichiesta(IdentificativoSchede.getAccordi().getNomeScheda(), true, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
					listOfInRicerca.add(Integer.valueOf(inRichiesta));			
					
				}
				situazioneAttuale.setStatoAccordi(listOfAccordi,listOfInRicerca);
				// XXX: more info for feedback
				this.fillCigAndCuiOnStatoScheda(situazioneAttuale.getStatoAccordi(), cig, cui);
			}
//			logger.debug("Stato Accordo: " + situazioneAttuale.getStatoAccordi().toString());
			
			/** subappalti **/
			numeroOperazione = 9;
			SubappaltiManager subAppaltiManager = new SubappaltiManager(con, logger);
			List<SubappaltiBean> listOfSubAppalti = subAppaltiManager.loadMany(idAggiudicazione, dataInizioAggiudicazione);
			
			if(listOfSubAppalti != null && listOfSubAppalti.size() > 0){
				ArrayList<Integer> listOfInRicerca = new ArrayList<Integer>();
				for(SubappaltiBean subappaltiBean : listOfSubAppalti){
					
					int inRichiesta = richiesteAction.isInRichiesta(IdentificativoSchede.getVarianti().getNomeScheda(), true, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
					listOfInRicerca.add(Integer.valueOf(inRichiesta));
				}
				situazioneAttuale.setStatoSubAppalti(listOfSubAppalti,listOfInRicerca);
				// XXX: more info for feedback
				this.fillCigAndCuiOnStatoScheda(situazioneAttuale.getStatoSubAppalti(), cig, cui);
			}
//			logger.debug("Stato SubAppalti: " + situazioneAttuale.getStatoSubAppalti().toString());
			
			/** varianti **/
			numeroOperazione = 10;
			VarianteManager varianteManager = new VarianteManager(con, logger);
			List<VarianteBean> listOfVarianti = varianteManager.loadMany(idAggiudicazione, dataInizioAggiudicazione);
			
			if(listOfVarianti != null && listOfVarianti.size() > 0){
				ArrayList<Integer> listOfInRicerca = new ArrayList<Integer>();
				for(VarianteBean varianteBean : listOfVarianti){
					
					int inRichiesta = richiesteAction.isInRichiesta(IdentificativoSchede.getVarianti().getNomeScheda(), true, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
					listOfInRicerca.add(Integer.valueOf(inRichiesta));
				}
				situazioneAttuale.setStatoVarianti(listOfVarianti,listOfInRicerca);
				// XXX: more info for feedback
				this.fillCigAndCuiOnStatoScheda(situazioneAttuale.getStatoVarianti(), cig, cui);
			}
			
			/** stipula **/
			numeroOperazione = 11;
			StipulaManager stipulaManager = new StipulaManager(con, logger);
			StipulaBean stipulaBean = stipulaManager.load(idAggiudicazione, dataInizioAggiudicazione);
			
			if(stipulaBean != null && stipulaBean.getIdStipula() > 0){
				int inRichiesta = richiesteAction.isInRichiesta(IdentificativoSchede.getStipula().getNomeScheda(), false, aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione());
				
				situazioneAttuale.setStatoStipula(stipulaBean,inRichiesta);
				// XXX: more info for feedback
				this.fillCigAndCuiOnStatoScheda(situazioneAttuale.getStatoStipula(), cig, cui);
			}
//			logger.debug("Stato Varianti: " + situazioneAttuale.getStatoVarianti().toString());
			
		}catch(Exception e){
			logger.debug("Errore durante l'operazione nr.:" +numeroOperazione);
			throw e;
		}
	}
	
	/**
	 * Questi valori (cig e cui) devono essere valorizzati se possibile per collocare correttamente l'elemento di feedback anomalia
	 * qualora l'anomalia si verificasse nel controllo di flusso
	 * 
	 * @param statoSchedaCorrente
	 * @param cig
	 * @param cui
	 */
	private void fillCigAndCuiOnStatoScheda(StatoScheda statoSchedaCorrente,String cig, String cui){
		statoSchedaCorrente.setCig(cig);
		statoSchedaCorrente.setCui(cui);
	}
	
	/**
	 * Valorizza in una lista di statischeda il cig e il cui
	 * 
	 * @see fillCigAndCuiOnStatoScheda(StatoScheda ..)
	 * 
	 * @param listaDiStati
	 * @param cig
	 * @param cui
	 */
	private void fillCigAndCuiOnStatoScheda(List<StatoScheda> listaDiStati, String cig, String cui){
		for(StatoScheda statoSchedaCorrente: listaDiStati){
			this.fillCigAndCuiOnStatoScheda(statoSchedaCorrente, cig, cui);
		}
	}
	/**
	 * Verifica se per il CIG sono previsti gli obblighi comunicativi
	 * @param infoComuniBean
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	private boolean fillObblighiComunicativiSpeciali(InfoComuniBean infoComuniBean) throws SQLException, Exception {
		//TICKET ALM 11228 - 3.04.3.2
		//Se la gara riguarda il settore ordinario, lascia a true gli obblighi comunicativi
		boolean obblighiComunicativiSpeciali = Costanti.TIPO_ENTE_ORDINARIO.equals(infoComuniBean.getFlagEnteSpeciale());
		//Se la gara riguarda il settore speciale, verifica che la data sia uguale o successiva alla data degli obblighi comunicativi
		if(!obblighiComunicativiSpeciali) {
//			String dataCreazioneGara = new GaraManager(con,logger).getGara(new LottoManager(con,logger).getLotto(infoComuniBean.getIdLotto()).getId_Gara()).getData_creazione();
			String dataPubblicazione = new LottoManager(con,logger).getLotto(infoComuniBean.getIdLotto()).getData_Pubblicazione();
			obblighiComunicativiSpeciali = SimogProperties.getInstance().isDataAfterObblighiComunicativiSpeciali(dataPubblicazione);
		}
		return obblighiComunicativiSpeciali;
	}
}
