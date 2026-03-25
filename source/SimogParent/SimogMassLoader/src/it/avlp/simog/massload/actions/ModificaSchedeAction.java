package it.avlp.simog.massload.actions;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.avcp.simog.managers.accordo.AccordoManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicatarioManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.CondizioniManager;
import it.avcp.simog.managers.aggiudicazione.DittaAusiliariaManager;
import it.avcp.simog.managers.aggiudicazione.FinanziamentoManager;
import it.avcp.simog.managers.aggiudicazione.InfoComuniManager;
import it.avcp.simog.managers.aggiudicazione.PubblicazioneManager;
import it.avcp.simog.managers.aggiudicazione.RequisitiManager;
import it.avcp.simog.managers.aggiudicazione.ResponsabileManager;
import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avcp.simog.managers.avanzamento.AvanzamentoManager;
import it.avcp.simog.managers.collaudo.CollaudoManager;
import it.avcp.simog.managers.collaudo.ResponsabileCollManager;
import it.avcp.simog.managers.conclusione.ConclusioniManager;
import it.avcp.simog.managers.inizio.InizioLavoriManager;
import it.avcp.simog.managers.inizio.PosizAggiudManager;
import it.avcp.simog.managers.inizio.ResponsabileInizioManager;
import it.avcp.simog.managers.r129.R129Manager;
import it.avcp.simog.managers.sospensioni.SospensioniManager;
import it.avcp.simog.managers.stipula.StipulaManager;
import it.avcp.simog.managers.subappalti.SubappaltiManager;
import it.avcp.simog.managers.variante.EventiMotiviVariantiManager;
import it.avcp.simog.managers.variante.VarianteManager;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.OrigineSchedaEnum;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.SchedaSpecificaValidationBean;
import it.avlp.simog.beans.SituazioneSchedeAttuale;
import it.avlp.simog.beans.StatoScheda;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.accordi.SchedaAccordo;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.CondizioneAggBean;
import it.avlp.simog.beans.aggiudicazione.DittaAusiliariaBean;
import it.avlp.simog.beans.aggiudicazione.RequisitiBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoResponsabileBean;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.aggiudicazione.TipoFinanziamentoBean;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.avanzamento.SchedaAvanzamento;
import it.avlp.simog.beans.collaudo.SchedaCollaudo;
import it.avlp.simog.beans.conclusione.SchedaConclusione;
import it.avlp.simog.beans.inizio.PosizioneAggiudicatarioBean;
import it.avlp.simog.beans.inizio.SchedaInizioLavori;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.beans.r129.SchedaR129;
import it.avlp.simog.beans.sospensioni.SchedaSospensione;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.beans.stipula.SchedaStipula;
import it.avlp.simog.beans.subappalti.SchedaSubAppalti;
import it.avlp.simog.beans.subappalti.SubappaltatoreBean;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.beans.variante.EventiMotiviVariantiBean;
import it.avlp.simog.beans.variante.SchedaVariante;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.common.action.CupLottoAggAction;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.db.generated.COLLAUDO;
import it.avlp.simog.db.generated.FINE_LAVORI;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.INIZIO_LAVORI;
import it.avlp.simog.db.generated.STIPULA;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.flusso.OperazioneScheda;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.massload.bean.IdsSchedaXML;
import it.avlp.simog.massload.bean.schede.TutteLeSchede;
import it.avlp.simog.massload.esito.EsitoOperazioneControlloLogico;
import it.avlp.simog.massload.esito.EsitoOperazioneInserimentoOModifica;
import it.avlp.simog.massload.manager.MassLoaderManager;
import it.avlp.simog.massload.util.conversion.SituazioneAttualeSchedeXml;
import it.avlp.simog.rubricamanager.RubricaManager;
import it.avlp.simog.util.SimogProperties;

public class ModificaSchedeAction extends GenericSchedeAction{

	private String userName;
	
	private String infoSeverity = ValidationBean.VALBEAN_SEV_INFO;
	// XXX: nota che siccome e' esito positivo questo messaggio non risulta nel feddback
	private String commonInfoMessage = "Scheda Modificata Correttamente";
	
	private MassLoaderManager aggiornatoreIdLocale;
	
	public ModificaSchedeAction(Connection con, Logger logger, String userName, OrigineSchedaEnum origine) {
		super(con, logger, origine);
		this.userName = userName;
		// mi serve per aggiornare gli id locale quando ci si trova nella condizione db.id_locale = null e xml.id_locale != null
		// per le sole schede singole
		this.aggiornatoreIdLocale = new MassLoaderManager(con, logger);
	}
	
//	public EsitoOperazioneControlloLogico controllaCorrettezzaFlusso(SituazioneSchedeAttuale situazioneAttuale, int progressivoSchedaCompleta){
//		return controllaCorrettezzaFlusso(situazioneAttuale, OperazioneScheda.getModifica(),progressivoSchedaCompleta);
//	}
	
	/**
	 * Metodo piu' preciso di quello invocato si occupa di valorizzare i progressivi delle schede multiple informazione che
	 * prima non avevo.
	 * Quindi le operazioni di controllo vegono fatte a valle, qui rimedio al progressivo.
	 * 
	 * @param ids
	 * @return
	 */
	public EsitoOperazioneControlloLogico controllaCorrettezzaFlussoConProgressivo(IdsSchedaXML ids){
		return super.controllaCorrettezzaFlussoConProgressivo(ids, OperazioneScheda.getModifica(),"");
	}
	/**
	 * Metodo che inserisce una scheda che comprende dati comuni, n schede complete (1,1 CUI);
	 * 
	 * @param idScheda
	 * @param situazioneAttuale ce l'ho in idsSchedaXMl..
	 * @return
	 */
	public EsitoOperazioneInserimentoOModifica modificaScheda(IdsSchedaXML idScheda, TutteLeSchede tutteLeSchede) throws ModificaException{
		
		esitiOperazioni = new ArrayList<SchedaSpecificaValidationBean>();
		EsitoOperazioneInserimentoOModifica esitoInserimento = new EsitoOperazioneInserimentoOModifica();
		esitoInserimento.setListOfSuccess(esitiOperazioni);

		SituazioneAttualeSchedeXml situazioneXML = idScheda.getSituazioneAttualeXml();
		SituazioneSchedeAttuale situazioneDb = idScheda.getSituazioneAttuale();
		String cig = idScheda.getCig();
		boolean esito = true;
		int elemento = idScheda.getCardinalitaSchedaCig();
		int progressivoSchedaCompleta = idScheda.getCardinalitaSchedaCompleta();	
		
		if(situazioneXML.isPresentDatiComuni() && situazioneDb.getStatoDatiComuni().isEsistenteDb()){
			esito = esito && modificaDatiComuni(situazioneDb.getStatoDatiComuni(), tutteLeSchede.getSchedaA(), tutteLeSchede.getListOfAnagraficheResponsabili(),cig, elemento, progressivoSchedaCompleta);
		}
		if(situazioneXML.isPresentSchedaCompleta()){
			
			Scheda_A schedaA = tutteLeSchede.getSchedaA();
			List<SoggettoPartecipanteBean> listOfAnapartecipante = tutteLeSchede.getListOfAnagrafichePartecipanti();
			List<SoggettoResponsabileBean> listOfAnaResponsabile = tutteLeSchede.getListOfAnagraficheResponsabili();
			
			if(situazioneXML.isPresentAggiudicazione() && situazioneDb.getStatoAggiudicazione().isEsistenteDb()){
				esito = esito && modificaAggiudicazione(situazioneDb.getStatoAggiudicazione(), schedaA, listOfAnapartecipante, listOfAnaResponsabile,cig, elemento, progressivoSchedaCompleta, TipoAggiudicazione.A);
			}
			if(situazioneXML.isPresentAdesione() && situazioneDb.getStatoAdesione().isEsistenteDb()){
				esito = esito && modificaAggiudicazione(situazioneDb.getStatoAdesione(), schedaA, listOfAnapartecipante, listOfAnaResponsabile,cig, elemento, progressivoSchedaCompleta, TipoAggiudicazione.Q);
			}
			
			if(situazioneXML.isPresentSottosoglia() && situazioneDb.getStatoSottosoglia().isEsistenteDb()){
				esito = esito && modificaAggiudicazione(situazioneDb.getStatoSottosoglia(), schedaA, listOfAnapartecipante, listOfAnaResponsabile,cig, elemento, progressivoSchedaCompleta, TipoAggiudicazione.S);
			}
			if(situazioneXML.isPresentEscluso() && situazioneDb.getStatoEscluso().isEsistenteDb()){
				esito = esito && modificaAggiudicazione(situazioneDb.getStatoEscluso(), schedaA, listOfAnapartecipante, listOfAnaResponsabile,cig, elemento, progressivoSchedaCompleta, TipoAggiudicazione.E);
			}
			
			if(situazioneXML.isPresentInizioLavori() && situazioneDb.getStatoInizioLavori().isEsistenteDb()){
				esito = esito && modificaDatiInizio(situazioneDb.getStatoInizioLavori(), tutteLeSchede.getSchedaInizio(), schedaA, listOfAnapartecipante, listOfAnaResponsabile,cig, elemento, progressivoSchedaCompleta);
			}
			
			if(situazioneXML.isPresentStipula() && situazioneDb.getStatoStipula().isEsistenteDb()){
				esito = esito && modificaDatiStipula(situazioneDb.getStatoStipula(), tutteLeSchede.getSchedaStipula(), schedaA, listOfAnapartecipante, listOfAnaResponsabile,cig, elemento, progressivoSchedaCompleta);
			}
			
			if(situazioneXML.isPresentAvanzamenti()){
				esito = esito && modificaDatiAvanzamenti(situazioneDb.getStatoAvanzamento(), tutteLeSchede.getSchedeAvanzamento(), schedaA,cig, elemento, progressivoSchedaCompleta);
			}
			if(situazioneXML.isPresentConclusione() && situazioneDb.getStatoConclusione().isEsistenteDb()){
				esito = esito && modificaDatiConclusione(situazioneDb.getStatoConclusione(), tutteLeSchede.getSchedaConclusione(), schedaA,cig, elemento, progressivoSchedaCompleta);
			}
			if(situazioneXML.isPresentCollaudo() && situazioneDb.getStatoCollaudo().isEsistenteDb()){
				esito = esito && modificaDatiCollaudo(situazioneDb.getStatoCollaudo(), tutteLeSchede.getSchedaCollaudo(), schedaA, listOfAnaResponsabile,cig, elemento, progressivoSchedaCompleta);
			}
			if(situazioneXML.isPresentAccordi()){
				esito = esito && modificaDatiAccordi(situazioneDb.getStatoAccordi(), tutteLeSchede.getSchedeAccordo(), schedaA,cig, elemento, progressivoSchedaCompleta);
			}
			if(situazioneXML.isPresentRitardo()){
				esito = esito && modificaDatiRitardi(situazioneDb.getStatoRitardo(), tutteLeSchede.getSchedeRitardi(), schedaA, cig, elemento, progressivoSchedaCompleta);
			}
			if(situazioneXML.isPresentSospensioni()){
				esito = esito && modificaDatiSospensioni(situazioneDb.getStatoSospensioni(), tutteLeSchede.getSchedeSospensione(), schedaA,cig, elemento, progressivoSchedaCompleta);
			}
			if(situazioneXML.isPresentSubAppalti()){
				esito = esito && modificaDatiSubbappalti(situazioneDb.getStatoSubAppalti(), tutteLeSchede.getSchedeSubAppalto(), listOfAnapartecipante, schedaA,cig, elemento, progressivoSchedaCompleta);
			}
			if(situazioneXML.isPresentVarianti()){
				esito = esito && modificaDatiVarianti(situazioneDb.getStatoVarianti(), tutteLeSchede.getSchedeVariante(), schedaA,cig, elemento, progressivoSchedaCompleta);
			}

		}
	      if(!esito)
	           throw new ModificaException(IdentificativoSchede.getDatiComuni(), cig, "", "", "", Messaggi.SIMOG_MASSLOADER_199 );

		esitoInserimento.setEsitoOperazione(esito);
		return esitoInserimento;
	}	
	
	/**
	 * Modifica di tutti i dati relativi ai dati comuni
	 * 
	 * @param statoDatiComuni
	 * @param schedaA
	 * @param listOfAnaResponsabile
	 * @param cig
	 * @return
	 * @throws ModificaException
	 */
	private boolean modificaDatiComuni(StatoScheda statoDatiComuni, Scheda_A schedaA, List<SoggettoResponsabileBean> listOfAnaResponsabile,String cig,
			int elemento,int progressivoSchedaCompleta) throws ModificaException{
		ModificaException modificaException = 
			new ModificaException(IdentificativoSchede.getDatiComuni(),cig, null,
					getStringValueOfLong(schedaA.getInfoComuni().getIdInfo()), schedaA.getInfoComuni().getIdLocale(),
					Messaggi.SIMOG_MASSLOADER_193.replace("$1", OperazioneScheda.MODIFICA).replace("$2", IdentificativoSchede.DATI_COMUNI));

		try{	
			schedaA.getInfoComuni().setIdInfo(statoDatiComuni.getIdRecord());
			schedaA.getInfoComuni().setDataInizioInfo(statoDatiComuni.getDataInizioRecord());
			//
			InfoComuniManager infoManager = new InfoComuniManager(con, logger);
			infoManager.save(schedaA.getInfoComuni(), userName);
			infoManager.confirm(schedaA.getInfoComuni(), userName);
			
			PubblicazioneManager pubblicazioneManager = new PubblicazioneManager(con, logger);
			pubblicazioneManager.save(schedaA.getInfoComuni().getPubblicazione());
			pubblicazioneManager.confirm(schedaA.getInfoComuni().getPubblicazione());
			
			modificaAnagraficaResponsabili(null, listOfAnaResponsabile, schedaA.getInfoComuni().getCfRup(), true);
	
            // aggiornamento dei campi gara e lotto
            infoManager.updateGaraLotto(con, logger, schedaA.getInfoComuni());

			esitiOperazioni.add(
					new SchedaSpecificaValidationBean(
							commonInfoMessage,infoSeverity,
							elemento,progressivoSchedaCompleta, 0,
							IdentificativoSchede.getDatiComuni().getNomeScheda(),
							cig,null,String.valueOf(schedaA.getInfoComuni().getIdInfo()),
							schedaA.getInfoComuni().getIdLocale()
					)
			);
			// XXX: VL - PATCH - RETROCOMPATIBILITA' ID LOCALE
			
			SchedaSpecificaValidationBean idLocaleFeedBackInfo	= new SchedaSpecificaValidationBean(
					Messaggi.SIMOG_MASSLOADER_204,infoSeverity,
					elemento,progressivoSchedaCompleta,0,
					IdentificativoSchede.getDatiComuni().getNomeScheda(),
					cig,null,String.valueOf(schedaA.getInfoComuni().getIdInfo()),
					schedaA.getInfoComuni().getIdLocale()
			);
			
			aggiornatoreIdLocale.aggiornaIdLocale(INFO_AGGIUDICAZIONI.TABLE_NAME, 
												INFO_AGGIUDICAZIONI.ID_INFO, 
												INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO, 
												schedaA.getInfoComuni().getIdInfo(), 
												schedaA.getInfoComuni().getDataInizioInfo(), 
												schedaA.getInfoComuni().getIdLocale(),
												idLocaleFeedBackInfo,esitiOperazioni);
			return true;
			
		}catch (Exception e) {
			e.printStackTrace();
			throw modificaException;
		}
	}

	/**
	 * Modifica di tutti i dati relativi alla aggiudicazione
	 * 
	 * @param statoAggiudicazione
	 * @param schedaA
	 * @param listOfAnapartecipante
	 * @param listOfAnaResponsabile
	 * @param cig
	 * @return
	 * @throws ModificaException
	 */
	private boolean modificaAggiudicazione(StatoScheda statoAggiudicazione, Scheda_A schedaA, List<SoggettoPartecipanteBean> listOfAnapartecipante, 
			List<SoggettoResponsabileBean> listOfAnaResponsabile,String cig,
			int elemento, int progressivoSchedaCompleta, TipoAggiudicazione tipoAgg) throws ModificaException{
		
		IdentificativoSchede idScheda = IdentificativoSchede.getAggiudicazione();
		if(TipoAggiudicazione.Q.equals(tipoAgg))
			idScheda = IdentificativoSchede.getAdesione();
		else if(TipoAggiudicazione.E.equals(tipoAgg))
			idScheda = IdentificativoSchede.getEscluso();
		else if(TipoAggiudicazione.S.equals(tipoAgg))
			idScheda = IdentificativoSchede.getSottosoglia();
		
		ModificaException modificaException = 
			new ModificaException(idScheda,cig, schedaA.getAggiudicazione().getCui(),
					getStringValueOfLong(schedaA.getAggiudicazione().getIdAggiudicazione()), schedaA.getAggiudicazione().getIdLocale(),
					Messaggi.SIMOG_MASSLOADER_193.replace("$1", OperazioneScheda.MODIFICA).replace("$2", idScheda.getNomeScheda()));
		boolean retVal = true;
		try{
			//
			schedaA.getAggiudicazione().setIdAggiudicazione(statoAggiudicazione.getIdRecord());
			schedaA.getAggiudicazione().setDataInizioAggiudicazione(statoAggiudicazione.getDataInizioRecord());
			schedaA.getAggiudicazione().setCui(statoAggiudicazione.getCui());
			
			
			//imposto anche il sottotipo, anche se in modifica dovrebbe gia essere OK
			schedaA.getAggiudicazione().setSottotipo(tipoAgg);
			//
			// nel caso in cui anche infoComuni sia stato inserito adesso ho bisogno degli estremi
			schedaA.getAggiudicazione().setIdInfo(schedaA.getInfoComuni().getIdInfo());
			schedaA.getAggiudicazione().setDataInizioInfo(schedaA.getInfoComuni().getDataInizioInfo());
			// --
			AggiudicazioniManager am = new AggiudicazioniManager(con, logger);
			
			//MAC 25701
			AggiudicazioneBean abean = am.getAggiudicazioni(statoAggiudicazione.getIdRecord(), statoAggiudicazione.getDataInizioRecord(), false);
			if(abean!=null) {
			  schedaA.getAggiudicazione().setProgCUI(abean.getProgCUI());
			  schedaA.getAggiudicazione().setProgCuiRiaggiudicato(abean.getProgCuiRiaggiudicato());
			}
			//FINE MAC 25701
			
			
			//TICKET ALM #3835
			//Ottieni la data di pubblicazione del lotto
				long dataCreazioneTime = 0;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Gara g = new GaraManager(con,logger).getGara(schedaA.getInfoGara().getIdGara());
				Lotto l = new LottoManager(con,logger).getLotto(schedaA.getInfoGara().getIdLotto());
				String dataCreazioneStr = g.getData_creazione();
				dataCreazioneTime = sdf.parse(dataCreazioneStr).getTime();
	         
				//Se la gara e' stata pubblicata successivamente, la scelta del contraente e modalita indizione vengono scartate e recuperata dal lotto e gara
				if(dataCreazioneTime >= SimogProperties.getInstance().getDataAttivazione3042Timestamp()) {
					
					//TICKET ALM #3835-08.3
					schedaA.getAggiudicazione().setIdSceltaContraente(Long.parseLong(l.getId_Scelta_Contraente()));
					//TICKET ALM #4214-08.3
					if(g.getID_MODO_GARA() != 0)
					    schedaA.getAggiudicazione().setIdModoIndizione(g.getID_MODO_GARA());
				}
			//FINE TICKET ALM #3835
			
			
			am.save(schedaA.getAggiudicazione(), userName);	
			am.confirm(schedaA.getAggiudicazione(), userName);
			
			
			long idAggiudicazione = schedaA.getAggiudicazione().getIdAggiudicazione();
			Timestamp dataInizioAggiudicazione = schedaA.getAggiudicazione().getDataInizioAggiudicazione();
		
         if(SimogProperties.getInstance().isCUPAttivo()){

            CupLottoAggAction claAction = new CupLottoAggAction(con, logger);
            
            if(!SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())){
               claAction.updateElencoCup(schedaA.getFlagCUP(),schedaA.getElencoCup(), schedaA.getInfoGara().getIdLotto(), idAggiudicazione, dataInizioAggiudicazione, true);
               claAction.confirmAllValidCup(schedaA.getElencoCup());           
            }
            else
               claAction.completaDatiAggCup(schedaA.getInfoGara().getIdLotto(), idAggiudicazione, dataInizioAggiudicazione);
         }

         AggiudicatarioManager aggm = new AggiudicatarioManager(con, logger);
         DittaAusiliariaManager dam = new DittaAusiliariaManager(con, logger);
         dam.deleteDitteAusiliarieByAggiudicazione(idAggiudicazione, dataInizioAggiudicazione);
         aggm.deleteAggiudicatari(idAggiudicazione, dataInizioAggiudicazione);

         if(schedaA.getAggiudicatari() != null && !schedaA.getAggiudicatari().isEmpty()){
				for(AggiudicatarioBean ab : schedaA.getAggiudicatari()){
					ab.setIdAggiudicazione(idAggiudicazione);
					ab.setDataInizioAggiudicazione(dataInizioAggiudicazione);
					if(!modificaAnagraficaAggiudicatari(null, ab, null,null, listOfAnapartecipante, 
					         ab.getSoggettoPartecipante().getCodiceFiscale(), ab.getSoggettoPartecipante().getId_stato(), 
					         true, false,false)){
                       esitiOperazioni.add(new SchedaSpecificaValidationBean(
                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_PARTECIPANTE)
                                .replace("$2", ab.getSoggettoPartecipante().getCodiceFiscale()), 
                                ValidationBean.VALBEAN_SEV_ERR, elemento,
                                progressivoSchedaCompleta,  0, idScheda.getNomeScheda(), 
                                cig, getCuiForDummies(schedaA), String.valueOf(schedaA.getAggiudicazione().getIdAggiudicazione()), 
                                schedaA.getAggiudicazione().getIdLocale()));
                       retVal = false;
					}//ow new Exception(".. no comment ..");
					else
					   aggm.confirm(ab);
					
					if(ab.getDitteAusiliarie() != null && !ab.getDitteAusiliarie().isEmpty()){
						for(DittaAusiliariaBean dab: ab.getDitteAusiliarie()){
							//gm: gli id e date dell'aggiudicazione ed aggiudicatario devono essere obbligatoriamente inseriti
							dab.setIdAggiudicazione(idAggiudicazione);
							dab.setDataInizioAggiudicazione(dataInizioAggiudicazione);
							dab.setIdAggiudicatario(ab.getIdAggiudicatario());
							dab.setDataInizioAggiudicatario(ab.getDataInizioAggiudicatario());
							
							if(!modificaAnagraficaAggiudicatari(null, null, dab,null, listOfAnapartecipante, dab.getSoggettoPartecipante().getCodiceFiscale(), 
									dab.getSoggettoPartecipante().getId_stato(),false,true,false)) {
		                       esitiOperazioni.add(new SchedaSpecificaValidationBean(
		                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_AUSILIARIA)
		                                .replace("$2", dab.getCfAusiliaria()), 
		                                ValidationBean.VALBEAN_SEV_ERR, elemento,
		                                progressivoSchedaCompleta,  0, idScheda.getNomeScheda(), 
		                                cig, getCuiForDummies(schedaA), String.valueOf(schedaA.getAggiudicazione().getIdAggiudicazione()), 
		                                schedaA.getAggiudicazione().getIdLocale()));
		                       retVal = false;
							} //	   thrsadasdow new Exception(".. no comment ..");
							else
							   dam.update(dab,true);
						}
					}
				}
			}

         ResponsabileManager rm = new ResponsabileManager(con, logger);
         rm.deleteResponsabili(idAggiudicazione, dataInizioAggiudicazione);
			
			if(schedaA.getPrestazioni() != null && ! schedaA.getPrestazioni().isEmpty()){
				for(ResponsabileBean rb : schedaA.getPrestazioni()){
					rb.setIdScheda(idAggiudicazione);
					rb.setDataInizioScheda(dataInizioAggiudicazione);
					if(rb.getSoggettoPartecipante() != null){
						
						if(rb.getSoggettoPartecipante().getIdSoggettoPartecipante()==0) {
							RubricaManager rubricaMan = new RubricaManager(con,logger);
							SoggettoPartecipanteBean soggDB = rubricaMan.getAllSoggettoPartecipanteByCF(rb.getSoggettoPartecipante().getCodiceFiscale(),
									String.valueOf(rb.getSoggettoPartecipante().getId_stato()));
							if(soggDB!=null)
								rb.setSoggettoPartecipante(soggDB);
						}
						
						if(!modificaAnagraficaAggiudicatari(null, null, null,rb, 
						      listOfAnapartecipante, rb.getSoggettoPartecipante().getCodiceFiscale(), 
						      String.valueOf(rb.getSoggettoPartecipante().getId_stato()),false,false,true)) {
	                       esitiOperazioni.add(new SchedaSpecificaValidationBean(
	                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_PROGETTISTA)
	                                .replace("$2", rb.getSoggettoPartecipante().getCodiceFiscale()), 
	                                ValidationBean.VALBEAN_SEV_ERR, elemento,
	                                progressivoSchedaCompleta,  0, idScheda.getNomeScheda(), 
	                                cig, getCuiForDummies(schedaA), String.valueOf(schedaA.getAggiudicazione().getIdAggiudicazione()), 
	                                schedaA.getAggiudicazione().getIdLocale()));
	                       retVal = false;
						}//						   dwdewdwthrow new Exception(".. no comment ..");
						else
						   rm.confirm(rb);
					}
					else{
						if(!modificaAnagraficaResponsabili(rb, listOfAnaResponsabile, 
						      rb.getSoggettoResponsabile().getCodiceFiscaleResponsabile(), false)) {
	                       esitiOperazioni.add(new SchedaSpecificaValidationBean(
	                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_PROGETTISTA)
	                                .replace("$2", rb.getSoggettoResponsabile().getCodiceFiscaleResponsabile()), 
	                                ValidationBean.VALBEAN_SEV_ERR, elemento,
	                                progressivoSchedaCompleta,  0, idScheda.getNomeScheda(), 
	                                cig, getCuiForDummies(schedaA), String.valueOf(schedaA.getAggiudicazione().getIdAggiudicazione()), 
	                                schedaA.getAggiudicazione().getIdLocale()));
	                       retVal = false;
						}//						   throdsadasdw new Exception(".. no comment ..");
						else
						   rm.confirm(rb);
					}
				}
			}
			
			if(schedaA.getResponsabili() != null && !schedaA.getResponsabili().isEmpty()){
				for(ResponsabileBean pr : schedaA.getResponsabili()){
					pr.setIdScheda(idAggiudicazione);
					pr.setDataInizioScheda(dataInizioAggiudicazione);
					if(!modificaAnagraficaResponsabili(pr, listOfAnaResponsabile, 
					      pr.getSoggettoResponsabile().getCodiceFiscaleResponsabile(), false)){
                       esitiOperazioni.add(new SchedaSpecificaValidationBean(
                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE)
                                .replace("$2", pr.getSoggettoResponsabile().getCodiceFiscaleResponsabile()), 
                                ValidationBean.VALBEAN_SEV_ERR, elemento,
                                progressivoSchedaCompleta,  0, idScheda.getNomeScheda(), 
                                cig, getCuiForDummies(schedaA), String.valueOf(schedaA.getAggiudicazione().getIdAggiudicazione()), 
                                schedaA.getAggiudicazione().getIdLocale()));
                       retVal = false;
					}//					   tsdadahrow new Exception(".. no comment ..");
					else
					   rm.confirm(pr);
				}				
			}
			
         CondizioniManager cm = new CondizioniManager(con, logger);
         cm.deleteCondizioniAgg(idAggiudicazione, dataInizioAggiudicazione);
			
         if(schedaA.getCondizioni() != null && !schedaA.getCondizioni().isEmpty()){
				for(CondizioneAggBean cab : schedaA.getCondizioni()){				
					cab.setIdAggiudicazione(idAggiudicazione);
					cab.setDataInizioAggiudicazione(dataInizioAggiudicazione);
					cm.confirm(cab);
				}
			}

			FinanziamentoManager fm = new FinanziamentoManager(con, logger);
         fm.deleteFinanziamenti(idAggiudicazione, dataInizioAggiudicazione);
			
         if(schedaA.getFinanziamenti() != null && !schedaA.getFinanziamenti().isEmpty()){
				for(TipoFinanziamentoBean tfb : schedaA.getFinanziamenti()){
					tfb.setIdAggiudicazione(idAggiudicazione);
					tfb.setDataInizioAggiudicazione(dataInizioAggiudicazione);
					fm.confirm(tfb);
				}
			}
			// siccome e' un manager condiviso.. l'operazione di cancellazione va' fatta una volta sola
//			boolean alreadyDeleted = false;

         TipoAppaltoManager tam = new TipoAppaltoManager(con, logger);

         if(SimogFlags.is3031_RFWEBGL00Active()
                  && !SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())){
            tam.deleteAppaltiAgg(idAggiudicazione, dataInizioAggiudicazione);
         }
         
         if(SimogFlags.is3031_RFWEBGL00Active()){
            if (schedaA.getTipoAppalto() != null && !schedaA.getTipoAppalto().isEmpty()){
               if(SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())){
                  tam.completaDatiTipoAppalto(schedaA.getInfoGara().getIdLotto(), idAggiudicazione, dataInizioAggiudicazione);
               }
               else{
                  for (TipoAppaltoAggBean taab : schedaA.getTipoAppalto()) {
                     taab.setIdAggiudicazione(idAggiudicazione);
                     taab.setDataInizioAggiudicazione(dataInizioAggiudicazione);
                     taab.setIdLotto(schedaA.getInfoGara().getIdLotto());
                     
                     tam.confirm(taab);
                  }               
               }
            }
         }
         else{
            if(schedaA.getTipoFS() != null && ! schedaA.getTipoFS().isEmpty()){
               //alreadyDeleted = true;
               for(TipoAppaltoAggBean taab : schedaA.getTipoFS()){
                  taab.setIdAggiudicazione(idAggiudicazione);
                  taab.setDataInizioAggiudicazione(dataInizioAggiudicazione);
                  tam.confirm(taab);
               }
            }
            if(schedaA.getTipoLavoro() != null && !schedaA.getTipoLavoro().isEmpty()){
//             if(!alreadyDeleted) tam.deleteAppaltiAgg(idAggiudicazione, dataInizioAggiudicazione);
               for(TipoAppaltoAggBean taab : schedaA.getTipoLavoro()){
                  taab.setIdAggiudicazione(idAggiudicazione);
                  taab.setDataInizioAggiudicazione(dataInizioAggiudicazione);
                  taab.setIdLotto(schedaA.getInfoGara().getIdLotto());               
                  tam.confirm(taab);
               }
            }
         }
            
         RequisitiManager rqm = new RequisitiManager(con, logger);
         rqm.deleteRequisiti(idAggiudicazione, dataInizioAggiudicazione);

         if(schedaA.getRequisiti() != null && !schedaA.getRequisiti().isEmpty()){
				for(RequisitiBean rb : schedaA.getRequisiti()){
					rb.setIdAggiudicazione(idAggiudicazione);
					rb.setDataInizioAggiudicazione(dataInizioAggiudicazione);
					rqm.confirm(rb);
				}
			}
			
			esitiOperazioni.add(
					new SchedaSpecificaValidationBean(
							commonInfoMessage,infoSeverity,
							elemento,progressivoSchedaCompleta,0,
							idScheda.getNomeScheda(),
							cig, getCuiForDummies(schedaA),String.valueOf(schedaA.getAggiudicazione().getIdAggiudicazione()),
							schedaA.getAggiudicazione().getIdLocale()
					)
			);
			// XXX: VL - PATCH - RETROCOMPATIBILITA' ID LOCALE
			
			SchedaSpecificaValidationBean idLocaleFeedBackInfo	= new SchedaSpecificaValidationBean(
							Messaggi.SIMOG_MASSLOADER_204,infoSeverity,
							elemento,progressivoSchedaCompleta,0,
							idScheda.getNomeScheda(),
							cig, getCuiForDummies(schedaA),String.valueOf(schedaA.getAggiudicazione().getIdAggiudicazione()),
							schedaA.getAggiudicazione().getIdLocale()
					);
			aggiornatoreIdLocale.aggiornaIdLocale(AGGIUDICAZIONI.TABLE_NAME, 
												AGGIUDICAZIONI.ID_AGGIUDICAZIONE, 
												AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE, 
												schedaA.getAggiudicazione().getIdAggiudicazione(), 
												schedaA.getAggiudicazione().getDataInizioAggiudicazione(), 
												schedaA.getAggiudicazione().getIdLocale(),
												idLocaleFeedBackInfo,esitiOperazioni);

			return retVal;
		}catch (Exception e) {
			e.printStackTrace();
			throw modificaException;
		}
	}
	/**
	 * Modifica tutti i dati relativi alla scheda inizio
	 * 
	 * @param statoInizioLavori
	 * @param schedaInizioLavori
	 * @param schedaA
	 * @param listOfAnapartecipante
	 * @param listOfAnaResponsabile
	 * @param cig
	 * @return
	 * @throws ModificaException
	 */
	private boolean modificaDatiInizio(StatoScheda statoInizioLavori, SchedaInizioLavori schedaInizioLavori, Scheda_A schedaA, 
			List<SoggettoPartecipanteBean> listOfAnapartecipante, List<SoggettoResponsabileBean> listOfAnaResponsabile, String cig,
			int elemento,int progressivoSchedaCompleta)throws ModificaException{
		ModificaException modificaException = 
			new ModificaException(
					IdentificativoSchede.getInizioLavori(),cig, getCuiForDummies(schedaA),
					getStringValueOfLong(schedaInizioLavori.getDatiInizio().getIdInizioLavori()),
					schedaInizioLavori.getDatiInizio().getIdLocale(),					 
					Messaggi.SIMOG_MASSLOADER_193.replace("$1", OperazioneScheda.MODIFICA).replace("$2", IdentificativoSchede.FASE_INIZIALE)
		);
		boolean retVal = true;
		try{
			
			schedaInizioLavori.getDatiInizio().setDataInizioAggiudicazione(schedaA.getAggiudicazione().getDataInizioAggiudicazione());
			schedaInizioLavori.getDatiInizio().setIdAggiudicazione(schedaA.getAggiudicazione().getIdAggiudicazione());
			//
			schedaInizioLavori.getDatiInizio().setIdInizioLavori(statoInizioLavori.getIdRecord());
			schedaInizioLavori.getDatiInizio().setDataInizioLavori(statoInizioLavori.getDataInizioRecord());
			//
			InizioLavoriManager im = new InizioLavoriManager(con, logger);
			im.save(schedaInizioLavori.getDatiInizio(), userName);
			im.confirm(schedaInizioLavori.getDatiInizio(), userName);

			PubblicazioneManager pm = new PubblicazioneManager(con, logger);
			pm.save(schedaInizioLavori.getDatiInizio().getPubblicazione());
			pm.confirm(schedaInizioLavori.getDatiInizio().getPubblicazione());

			long idInizioLavori = statoInizioLavori.getIdRecord();
			Timestamp dataInizioLavori = statoInizioLavori.getDataInizioRecord();

         ResponsabileInizioManager rm = new ResponsabileInizioManager(con, logger);
         rm.deleteRecord(idInizioLavori, dataInizioLavori);

			List<ResponsabileBean> responsabili = schedaInizioLavori.getResponsabiliInizio();
			if(responsabili != null && responsabili.size() > 0){
				for(ResponsabileBean bean: responsabili ){
					// gestione anagrafica
					if(!modificaAnagraficaResponsabili(bean, listOfAnaResponsabile, 
					      bean.getSoggettoResponsabile().getCodiceFiscaleResponsabile(), false)){
                       esitiOperazioni.add(new SchedaSpecificaValidationBean(
                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE)
                                .replace("$2", bean.getSoggettoResponsabile().getCodiceFiscaleResponsabile()), 
                                ValidationBean.VALBEAN_SEV_ERR, elemento,
                                progressivoSchedaCompleta,  0, IdentificativoSchede.getInizioLavori().getNomeScheda(), 
                                cig, getCuiForDummies(schedaA), getStringValueOfLong(schedaInizioLavori.getDatiInizio().getIdInizioLavori()),
                                schedaInizioLavori.getDatiInizio().getIdLocale()));
                       retVal = false;
					} //				   thdasdarow new Exception(".. no comment ..");
					else{
      					bean.setIdScheda(schedaInizioLavori.getDatiInizio().getIdInizioLavori());
      					bean.setDataInizioScheda(schedaInizioLavori.getDatiInizio().getDataInizioLavori());
      					rm.confirm(bean);
					}
				}
			}
			
         PosizAggiudManager posMan = new PosizAggiudManager(con, logger);
         posMan.deleteRecord(idInizioLavori, dataInizioLavori);

			List<PosizioneAggiudicatarioBean> posizioni = schedaInizioLavori.getPosizioneAggiudicatari();
			if(posizioni != null && posizioni.size() > 0){
				for(PosizioneAggiudicatarioBean bean : posizioni){
					// gestione anagrafica
					if(!modificaAnagraficaAggiudicatari(bean, null,null,null, listOfAnapartecipante, 
					      bean.getSoggettoPartecipante().getCodiceFiscale(), 
					      bean.getSoggettoPartecipante().getId_stato(), false, false,false)){
                       esitiOperazioni.add(new SchedaSpecificaValidationBean(
                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_POSIZIONE)
                                .replace("$2", bean.getSoggettoPartecipante().getCodiceFiscale()), 
                                ValidationBean.VALBEAN_SEV_ERR, elemento,
                                progressivoSchedaCompleta,  0, IdentificativoSchede.getInizioLavori().getNomeScheda(), 
                                cig, getCuiForDummies(schedaA), getStringValueOfLong(schedaInizioLavori.getDatiInizio().getIdInizioLavori()),
                                schedaInizioLavori.getDatiInizio().getIdLocale()));
                       retVal = false;
					} //     threrwerwerow new Exception(".. no comment ..");
					else{
      					bean.setIdInizioLavori(schedaInizioLavori.getDatiInizio().getIdInizioLavori());
      					bean.setDataInizioLavori(schedaInizioLavori.getDatiInizio().getDataInizioLavori());
      					posMan.confirm(bean);
					}
				}
			}
			esitiOperazioni.add(
					new SchedaSpecificaValidationBean(
							commonInfoMessage,infoSeverity,
							elemento, progressivoSchedaCompleta,0,
							IdentificativoSchede.getInizioLavori().getNomeScheda(),
							cig, getCuiForDummies(schedaA),String.valueOf(schedaInizioLavori.getDatiInizio().getIdInizioLavori()),
							schedaInizioLavori.getDatiInizio().getIdLocale()
							));
			// XXX: VL - PATCH - RETROCOMPATIBILITA' ID LOCALE
			
			SchedaSpecificaValidationBean idLocaleFeedBackInfo	= new SchedaSpecificaValidationBean(
					Messaggi.SIMOG_MASSLOADER_204,infoSeverity,
					elemento, progressivoSchedaCompleta,0,
					IdentificativoSchede.getInizioLavori().getNomeScheda(),
					cig, getCuiForDummies(schedaA),String.valueOf(schedaInizioLavori.getDatiInizio().getIdInizioLavori()),
					schedaInizioLavori.getDatiInizio().getIdLocale()
					);
			
			aggiornatoreIdLocale.aggiornaIdLocale(INIZIO_LAVORI.TABLE_NAME, 
													INIZIO_LAVORI.ID_INIZIO, 
													INIZIO_LAVORI.DATA_INIZIO_INIZIO, 
												schedaInizioLavori.getDatiInizio().getIdInizioLavori(), 
												schedaInizioLavori.getDatiInizio().getDataInizioLavori(), 
												schedaInizioLavori.getDatiInizio().getIdLocale(),
												idLocaleFeedBackInfo,esitiOperazioni);
			return retVal;
		}catch(Exception e){
			e.printStackTrace();
			throw modificaException;
		}

	}
	
	/**
	 * Modifica tutti i dati relativi alla scheda inizio
	 * 
	 * @param statoStipula
	 * @param schedaStipula
	 * @param schedaA
	 * @param listOfAnapartecipante
	 * @param listOfAnaResponsabile
	 * @param cig
	 * @return
	 * @throws ModificaException
	 */
	private boolean modificaDatiStipula(StatoScheda statoStipula, SchedaStipula schedaStipula, Scheda_A schedaA, 
			List<SoggettoPartecipanteBean> listOfAnapartecipante, List<SoggettoResponsabileBean> listOfAnaResponsabile, String cig,
			int elemento,int progressivoSchedaCompleta)throws ModificaException{
		ModificaException modificaException = 
			new ModificaException(
					IdentificativoSchede.getInizioLavori(),cig, getCuiForDummies(schedaA),
					getStringValueOfLong(schedaStipula.getStipula().getIdStipula()),
					schedaStipula.getStipula().getIdLocale(),					 
					Messaggi.SIMOG_MASSLOADER_193.replace("$1", OperazioneScheda.MODIFICA).replace("$2", IdentificativoSchede.STIPULA)
		);
		boolean retVal = true;
		try{
			
			schedaStipula.getStipula().setDataInizioAggiudicazione(schedaA.getAggiudicazione().getDataInizioAggiudicazione());
			schedaStipula.getStipula().setIdAggiudicazione(schedaA.getAggiudicazione().getIdAggiudicazione());
			//
			schedaStipula.getStipula().setIdStipula(statoStipula.getIdRecord());
			schedaStipula.getStipula().setDataInizioStipula(statoStipula.getDataInizioRecord());
			//
			StipulaManager im = new StipulaManager(con, logger);
			im.save(schedaStipula.getStipula(), userName);
			im.confirm(schedaStipula.getStipula(), userName);

			PubblicazioneManager pm = new PubblicazioneManager(con, logger);
			pm.save(schedaStipula.getStipula().getPubblicazione());
			pm.confirm(schedaStipula.getStipula().getPubblicazione());

			long idInizioLavori = statoStipula.getIdRecord();
			Timestamp dataInizioLavori = statoStipula.getDataInizioRecord();

         ResponsabileInizioManager rm = new ResponsabileInizioManager(con, logger);
         rm.deleteRecord(idInizioLavori, dataInizioLavori);

			List<ResponsabileBean> responsabili = schedaStipula.getResponsabiliInizio();
			if(responsabili != null && responsabili.size() > 0){
				for(ResponsabileBean bean: responsabili ){
					// gestione anagrafica
					if(!modificaAnagraficaResponsabili(bean, listOfAnaResponsabile, 
					      bean.getSoggettoResponsabile().getCodiceFiscaleResponsabile(), false)){
                       esitiOperazioni.add(new SchedaSpecificaValidationBean(
                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE)
                                .replace("$2", bean.getSoggettoResponsabile().getCodiceFiscaleResponsabile()), 
                                ValidationBean.VALBEAN_SEV_ERR, elemento,
                                progressivoSchedaCompleta,  0, IdentificativoSchede.getStipula().getNomeScheda(), 
                                cig, getCuiForDummies(schedaA), getStringValueOfLong(schedaStipula.getStipula().getIdStipula()),
                                schedaStipula.getStipula().getIdLocale()));
                       retVal = false;
					}//					   sdadsadathrow new Exception(".. no comment ..");
					else{
      					bean.setIdScheda(schedaStipula.getStipula().getIdStipula());
      					bean.setDataInizioScheda(schedaStipula.getStipula().getDataInizioStipula());
      					rm.confirm(bean);
					}
				}
			}

         PosizAggiudManager posMan = new PosizAggiudManager(con, logger);
         posMan.deleteRecord(idInizioLavori, dataInizioLavori);

			List<PosizioneAggiudicatarioBean> posizioni = schedaStipula.getPosizioneAggiudicatari();
			if(posizioni != null && posizioni.size() > 0){
				for(PosizioneAggiudicatarioBean bean : posizioni){
					// gestione anagrafica
					if(!modificaAnagraficaAggiudicatari(bean, null,null,null, 
					      listOfAnapartecipante, bean.getSoggettoPartecipante().getCodiceFiscale(), 
					      bean.getSoggettoPartecipante().getId_stato(), false, false,false)){
                       esitiOperazioni.add(new SchedaSpecificaValidationBean(
                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_POSIZIONE)
                                .replace("$2", bean.getSoggettoPartecipante().getCodiceFiscale()), 
                                ValidationBean.VALBEAN_SEV_ERR, elemento,
                                progressivoSchedaCompleta,  0, IdentificativoSchede.getStipula().getNomeScheda(), 
                                cig, getCuiForDummies(schedaA), getStringValueOfLong(schedaStipula.getStipula().getIdStipula()),
                                schedaStipula.getStipula().getIdLocale()));
                       retVal = false;
					}//				   throdfsfsdfw new Exception(".. no comment ..");
					else{
      					bean.setIdInizioLavori(schedaStipula.getStipula().getIdStipula());
      					bean.setDataInizioLavori(schedaStipula.getStipula().getDataInizioStipula());
      					posMan.confirm(bean);
					}
				}
			}
			esitiOperazioni.add(
					new SchedaSpecificaValidationBean(
							commonInfoMessage,infoSeverity,
							elemento, progressivoSchedaCompleta,0,
							IdentificativoSchede.getStipula().getNomeScheda(),
							cig, getCuiForDummies(schedaA),String.valueOf(schedaStipula.getStipula().getIdStipula()),
							schedaStipula.getStipula().getIdLocale()
							));
			// XXX: VL - PATCH - RETROCOMPATIBILITA' ID LOCALE
			
			SchedaSpecificaValidationBean idLocaleFeedBackInfo	= new SchedaSpecificaValidationBean(
					Messaggi.SIMOG_MASSLOADER_204,infoSeverity,
					elemento, progressivoSchedaCompleta,0,
					IdentificativoSchede.getInizioLavori().getNomeScheda(),
					cig, getCuiForDummies(schedaA),String.valueOf(schedaStipula.getStipula().getIdStipula()),
					schedaStipula.getStipula().getIdLocale()
					);
			
			aggiornatoreIdLocale.aggiornaIdLocale(STIPULA.TABLE_NAME, 
					STIPULA.ID_STIPULA, 
					STIPULA.DATA_INIZIO_STIPULA, 
												schedaStipula.getStipula().getIdStipula(), 
												schedaStipula.getStipula().getDataInizioStipula(), 
												schedaStipula.getStipula().getIdLocale(),
												idLocaleFeedBackInfo,esitiOperazioni);
			return retVal;
		}catch(Exception e){
			e.printStackTrace();
			throw modificaException;
		}

	}
	
		
	/**
	 * 
	 * @param listOfStatiAvanzamenti
	 * @param schedaAvanzamentiBean
	 * @param schedaA
	 * @param cig
	 * @return
	 * @throws ModificaException
	 */
	private boolean modificaDatiAvanzamenti(ArrayList<StatoScheda> listOfStatiAvanzamenti, SchedaAvanzamento schedaAvanzamentiBean,Scheda_A schedaA, 
			String cig,int elemento,int progressivoSchedaCompleta)throws ModificaException{

		AvanzamentoManager am = new AvanzamentoManager(con, logger);
		List<AvanzamentoBean> la = schedaAvanzamentiBean.getAvanzamenti();
		
		int i = 0;
		for(AvanzamentoBean avb : la){
			ModificaException modificaException = 
				new ModificaException(IdentificativoSchede.getAvanzamenti(),cig, getCuiForDummies(schedaA),
						getStringValueOfLong(avb.getIdAvanzamento()), avb.getIdLocale(),
						Messaggi.SIMOG_MASSLOADER_193.replace("$1", OperazioneScheda.MODIFICA).replace("$2", IdentificativoSchede.STATO_AVANZAMENTO));
			try{
				
				am.save(avb, userName);
				am.confirm(avb, userName);
				
				esitiOperazioni.add(
						new SchedaSpecificaValidationBean(
								commonInfoMessage,infoSeverity,
								elemento,progressivoSchedaCompleta,i,
								IdentificativoSchede.getAvanzamenti().getNomeScheda(),
								cig, getCuiForDummies(schedaA),
								String.valueOf(avb.getIdAvanzamento()),avb.getIdLocale()));
			}catch(Exception e){
				e.printStackTrace();
				throw modificaException;
			}
			i++;
		}
		return true;

	}
	/**
	 * 
	 * @param statoConclusione
	 * @param schedaConclusioneBean
	 * @param schedaA
	 * @param cig
	 * @return
	 * @throws ModificaException
	 */
	private boolean modificaDatiConclusione(StatoScheda statoConclusione, SchedaConclusione schedaConclusioneBean,Scheda_A schedaA, String cig,
			int elemento,int progressivoSchedaCompleta)throws ModificaException{
		ModificaException modificaException = 
			new ModificaException(IdentificativoSchede.getConclusione(),cig, getCuiForDummies(schedaA),
					getStringValueOfLong(schedaConclusioneBean.getConclusione().getIdUltim()), 
					schedaConclusioneBean.getConclusione().getIdLocale(),
					Messaggi.SIMOG_MASSLOADER_193.replace("$1", OperazioneScheda.MODIFICA).replace("$2", IdentificativoSchede.FINE_LAVORI));
		try{

			ConclusioniManager cm = new ConclusioniManager(con, logger);
			schedaConclusioneBean.getConclusione().setDataInizioAggiudicazione(schedaA.getAggiudicazione().getDataInizioAggiudicazione());
			schedaConclusioneBean.getConclusione().setIdAggiudicazione(schedaA.getAggiudicazione().getIdAggiudicazione());
			//
			schedaConclusioneBean.getConclusione().setIdUltim(statoConclusione.getIdRecord());
			schedaConclusioneBean.getConclusione().setDataIniUltim(statoConclusione.getDataInizioRecord());
			//
			cm.save(schedaConclusioneBean.getConclusione(), userName);
			cm.confirm(schedaConclusioneBean.getConclusione(), userName);

			esitiOperazioni.add(
					new SchedaSpecificaValidationBean(
							commonInfoMessage,infoSeverity,
							elemento,progressivoSchedaCompleta,0,
							IdentificativoSchede.getConclusione().getNomeScheda(),
							cig, getCuiForDummies(schedaA),
							String.valueOf(schedaConclusioneBean.getConclusione().getIdUltim())
							,schedaConclusioneBean.getConclusione().getIdLocale()));
			
			// XXX: VL - PATCH - RETROCOMPATIBILITA' ID LOCALE
			
			SchedaSpecificaValidationBean idLocaleFeedBackInfo	= new SchedaSpecificaValidationBean(
					Messaggi.SIMOG_MASSLOADER_204,infoSeverity,
					elemento,progressivoSchedaCompleta,0,
					IdentificativoSchede.getConclusione().getNomeScheda(),
					cig, getCuiForDummies(schedaA),
					String.valueOf(schedaConclusioneBean.getConclusione().getIdUltim())
					,schedaConclusioneBean.getConclusione().getIdLocale());
			
			aggiornatoreIdLocale.aggiornaIdLocale(FINE_LAVORI.TABLE_NAME, 
												FINE_LAVORI.ID_ULTIM, 
												FINE_LAVORI.DATA_INIZIO_ULTIM, 
												schedaConclusioneBean.getConclusione().getIdUltim(), 
												schedaConclusioneBean.getConclusione().getDataIniUltim(), 
												schedaConclusioneBean.getConclusione().getIdLocale(),
												idLocaleFeedBackInfo,esitiOperazioni);
			
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			throw modificaException;
		}
	}
	/**
	 * 
	 * @param statoCollaudo
	 * @param schedaCollaudoBean
	 * @param schedaA
	 * @param listOfAnaResponsabile
	 * @param cig
	 * @return
	 * @throws ModificaException
	 */
	private boolean modificaDatiCollaudo(StatoScheda statoCollaudo, SchedaCollaudo schedaCollaudoBean, Scheda_A schedaA, 
			List<SoggettoResponsabileBean> listOfAnaResponsabile, String cig,
			int elemento,int progressivoSchedaCompleta)throws ModificaException{
		ModificaException modificaException = 
			new ModificaException(IdentificativoSchede.getCollaudo(),cig, getCuiForDummies(schedaA),
					getStringValueOfLong(schedaCollaudoBean.getCollaudo().getIdCollaudo()), 
					schedaCollaudoBean.getCollaudo().getIdLocale(),
					Messaggi.SIMOG_MASSLOADER_193.replace("$1", OperazioneScheda.MODIFICA).replace("$2", IdentificativoSchede.COLLAUDO));
		boolean retVal=true;
		try{

			CollaudoManager cm = new CollaudoManager(con, logger);				
			schedaCollaudoBean.getCollaudo().setDataIniAggiudicazione(schedaA.getAggiudicazione().getDataInizioAggiudicazione());
			schedaCollaudoBean.getCollaudo().setIdAggiudicazione(schedaA.getAggiudicazione().getIdAggiudicazione());
			//
			schedaCollaudoBean.getCollaudo().setIdCollaudo(statoCollaudo.getIdRecord());
			schedaCollaudoBean.getCollaudo().setDataIniColl(statoCollaudo.getDataInizioRecord());
			//
			
			long idCollaudo = statoCollaudo.getIdRecord();
			Timestamp dataInizioCollaudo = statoCollaudo.getDataInizioRecord();
			
			cm.save(schedaCollaudoBean.getCollaudo(), userName);
			cm.confirm(schedaCollaudoBean.getCollaudo(), userName);

         ResponsabileCollManager rcm = new ResponsabileCollManager(con, logger); 
         rcm.deleteRecord(idCollaudo, dataInizioCollaudo);

			if(schedaCollaudoBean.getIncaricati() != null && !schedaCollaudoBean.getIncaricati().isEmpty()){
				for(ResponsabileBean rb : schedaCollaudoBean.getIncaricati()){
					rb.setIdScheda(schedaCollaudoBean.getCollaudo().getIdCollaudo());
					rb.setDataInizioScheda(schedaCollaudoBean.getCollaudo().getDataIniColl());
					if(!modificaAnagraficaResponsabili(rb, listOfAnaResponsabile, 
					      rb.getSoggettoResponsabile().getCodiceFiscaleResponsabile(), false)){
                       esitiOperazioni.add(new SchedaSpecificaValidationBean(
                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE)
                                .replace("$2", rb.getSoggettoResponsabile().getCodiceFiscaleResponsabile()), 
                                ValidationBean.VALBEAN_SEV_ERR, elemento,
                                progressivoSchedaCompleta,  0, IdentificativoSchede.getCollaudo().getNomeScheda(), 
                                cig, getCuiForDummies(schedaA), getStringValueOfLong(schedaCollaudoBean.getCollaudo().getIdCollaudo()), 
                                schedaCollaudoBean.getCollaudo().getIdLocale()));
                       retVal = false;
					}//					   adadasdthrow new Exception(".. no comment ..");
					//rm.save(rb);
					else
					   rcm.confirm(rb);
				}
			}
			esitiOperazioni.add(
					new SchedaSpecificaValidationBean(commonInfoMessage,infoSeverity,
							elemento,progressivoSchedaCompleta,0,
							IdentificativoSchede.getCollaudo().getNomeScheda(),cig, getCuiForDummies(schedaA)
							,String.valueOf(schedaCollaudoBean.getCollaudo().getIdCollaudo()),
							schedaCollaudoBean.getCollaudo().getIdLocale()));
			
			// XXX: VL - PATCH - RETROCOMPATIBILITA' ID LOCALE
			
			SchedaSpecificaValidationBean idLocaleFeedBackInfo	= new SchedaSpecificaValidationBean(Messaggi.SIMOG_MASSLOADER_204,infoSeverity,
					elemento,progressivoSchedaCompleta,0,
					IdentificativoSchede.getCollaudo().getNomeScheda(),cig, getCuiForDummies(schedaA)
					,String.valueOf(schedaCollaudoBean.getCollaudo().getIdCollaudo()),
					schedaCollaudoBean.getCollaudo().getIdLocale());
			
			aggiornatoreIdLocale.aggiornaIdLocale(COLLAUDO.TABLE_NAME, 
												COLLAUDO.ID_COLLAUDO, 
												COLLAUDO.DATA_INIZIO_COLL, 
												schedaCollaudoBean.getCollaudo().getIdCollaudo(), 
												schedaCollaudoBean.getCollaudo().getDataIniColl(), 
												schedaCollaudoBean.getCollaudo().getIdLocale(),
												idLocaleFeedBackInfo,esitiOperazioni);

			
			return retVal;
		}catch(Exception e){
			e.printStackTrace();
			throw modificaException;
		}
	}
	/**
	 * 
	 * @param listOfStatiRitardi
	 * @param schedaRitardiBean
	 * @param schedaA
	 * @param cig
	 * @return
	 * @throws ModificaException
	 */
	private boolean modificaDatiRitardi(ArrayList<StatoScheda> listOfStatiRitardi, SchedaR129 schedaRitardiBean,Scheda_A schedaA, String cig,
			int elemento,int progressivoSchedaCompleta)throws ModificaException{
		R129Manager rm = new R129Manager(con, logger);
		List<R129Bean> lr = schedaRitardiBean.getR129s();
		int i = 0;
		for(R129Bean rb : lr){
			ModificaException modificaException = 
				new ModificaException(IdentificativoSchede.getRitardo(),cig, getCuiForDummies(schedaA),
						getStringValueOfLong(rb.getIdRecord()), rb.getIdLocale(),
						Messaggi.SIMOG_MASSLOADER_193.replace("$1", OperazioneScheda.MODIFICA).replace("$2", IdentificativoSchede.IPOTESI_RECESSO));
			try{
				
				rm.save(rb, userName);
				rm.confirm(rb, userName);			
				
				esitiOperazioni.add(
						new SchedaSpecificaValidationBean(
								commonInfoMessage,infoSeverity,
								elemento,progressivoSchedaCompleta,i,
								IdentificativoSchede.getRitardo().getNomeScheda(),
								cig, getCuiForDummies(schedaA),
								String.valueOf(rb.getIdRecord()),rb.getIdLocale()));
			}catch(Exception e){
				e.printStackTrace();
				throw modificaException;
			}
			i++;
		}
		return true;

	}
	/**
	 * 
	 * @param listOfStatiAccordi
	 * @param schedaAccordiBean
	 * @param schedaA
	 * @param cig
	 * @return
	 * @throws ModificaException
	 */
	private boolean modificaDatiAccordi(ArrayList<StatoScheda> listOfStatiAccordi, SchedaAccordo schedaAccordiBean,Scheda_A schedaA, 
			String cig, int elemento,int progressivoSchedaCompleta)throws ModificaException{


		AccordoManager am = new AccordoManager(con, logger);
		List<AccordoBean> accordi = schedaAccordiBean.getAccordi();
		int i = 0;
		for(AccordoBean acc : accordi){
			ModificaException modificaException = 
				new ModificaException(IdentificativoSchede.getAccordi(),cig, getCuiForDummies(schedaA),
						getStringValueOfLong(acc.getIdAccordo()), acc.getIdLocale(),
						Messaggi.SIMOG_MASSLOADER_193.replace("$1", OperazioneScheda.MODIFICA).replace("$2", IdentificativoSchede.ACCORDO_BONARIO));
			try{

				am.save(acc, userName);
				am.confirm(acc, userName);
				
				esitiOperazioni.add(
						new SchedaSpecificaValidationBean(
								commonInfoMessage,infoSeverity,
								elemento,progressivoSchedaCompleta,i,
								IdentificativoSchede.getAccordi().getNomeScheda(),
								cig, getCuiForDummies(schedaA),
								String.valueOf(acc.getIdAccordo()),acc.getIdLocale()));
			}catch(Exception e){
				e.printStackTrace();
				throw modificaException;
			}
			i++;
		}
		return true;

	}
	/**
	 * 
	 * @param listOfStatiSospensioni
	 * @param schedaSospensioniBean
	 * @param schedaA
	 * @param cig
	 * @return
	 * @throws ModificaException
	 */
	private boolean modificaDatiSospensioni(ArrayList<StatoScheda> listOfStatiSospensioni, SchedaSospensione schedaSospensioniBean,Scheda_A schedaA, 
			String cig, int elemento,int progressivoSchedaCompleta)throws ModificaException{
		
		SospensioniManager sm = new SospensioniManager(con, logger);
		List<SospensioniBean> ls = schedaSospensioniBean.getSospensioni();
		int i = 0;
		for(SospensioniBean sb : ls){
			ModificaException modificaException = 
				new ModificaException(IdentificativoSchede.getSospensioni(),cig, getCuiForDummies(schedaA),
						getStringValueOfLong(sb.getIdSospensione()), sb.getIdLocale(),
						Messaggi.SIMOG_MASSLOADER_193.replace("$1", OperazioneScheda.MODIFICA).replace("$2", IdentificativoSchede.SOSPENSIONE));
			try{

				sm.save(sb, userName);
				sm.confirm(sb, userName);

				esitiOperazioni.add(
						new SchedaSpecificaValidationBean(
								commonInfoMessage,infoSeverity,
								elemento,progressivoSchedaCompleta,i,
								IdentificativoSchede.getSospensioni().getNomeScheda(),
								cig, getCuiForDummies(schedaA),
								String.valueOf(sb.getIdSospensione()),sb.getIdLocale()));
			}catch(Exception e){
				e.printStackTrace();
				throw modificaException;
			}
			i++;
		}return true;

	}
	/**
	 * 
	 * @param listOfStatiVarianti
	 * @param schedaVariantiBean
	 * @param schedaA
	 * @param cig
	 * @return
	 * @throws ModificaException
	 */
	private boolean modificaDatiVarianti(ArrayList<StatoScheda> listOfStatiVarianti, SchedaVariante schedaVariantiBean,Scheda_A schedaA,
			String cig, int elemento,int progressivoSchedaCompleta)throws ModificaException{


		VarianteManager vm = new VarianteManager(con, logger);
		EventiMotiviVariantiManager emvm = new EventiMotiviVariantiManager(con, logger);
		List<VarianteBean> lv = schedaVariantiBean.getVarianti();
		int i = 0;
		for(VarianteBean vb : lv){
			ModificaException modificaException = 
				new ModificaException(IdentificativoSchede.getVarianti(),cig, getCuiForDummies(schedaA),
						getStringValueOfLong(vb.getIdVariante()), vb.getIdLocale(),
						Messaggi.SIMOG_MASSLOADER_193.replace("$1", OperazioneScheda.MODIFICA).replace("$2", IdentificativoSchede.VARIANTE));
			try{

				long idVariante = vb.getIdVariante();
				Timestamp dataInizioVar = vb.getDataInizioVar();
				
				vm.save(vb, userName);
				vm.confirm(vb, userName);
				List<EventiMotiviVariantiBean> le = vb.getEmvb();
				emvm.deleteRecord(idVariante, dataInizioVar);
				for(EventiMotiviVariantiBean eb : le){
					eb.setIdVariante(vb.getIdVariante());
					eb.setDataIniVariante(vb.getDataInizioVar());
					//emvm.save(eb, username);
					emvm.confirm(eb, userName);
				}
				
				esitiOperazioni.add(
						new SchedaSpecificaValidationBean(commonInfoMessage,infoSeverity,
								elemento,progressivoSchedaCompleta,i,
								IdentificativoSchede.getVarianti().getNomeScheda(),cig, 
								getCuiForDummies(schedaA),
								String.valueOf(vb.getIdVariante()),vb.getIdLocale()));			
			}catch(Exception e){
				e.printStackTrace();
				throw modificaException;
			}
			i++;
		}return true;
		

	}
	/**
	 * 
	 * @param listOfStatiSubAppalti
	 * @param schedaSubbappaltiBean
	 * @param schedaA
	 * @param cig
	 * @return
	 * @throws ModificaException
	 */
	private boolean modificaDatiSubbappalti(ArrayList<StatoScheda> listOfStatiSubAppalti, SchedaSubAppalti schedaSubbappaltiBean, List<SoggettoPartecipanteBean> listOfAnapartecipante,
			Scheda_A schedaA, String cig, int elemento,int progressivoSchedaCompleta)throws ModificaException{
        boolean retVal = true;
		SubappaltiManager sm = new SubappaltiManager(con, logger);
		List<SubappaltiBean> ls = schedaSubbappaltiBean.getSubAppalti();
		int i = 0;
		for(SubappaltiBean sb : ls){
			ModificaException modificaException = 
				new ModificaException(
						IdentificativoSchede.getSubAppalti(),cig, getCuiForDummies(schedaA),
						getStringValueOfLong(sb.getIdRecord()), sb.getIdLocale(),
						Messaggi.SIMOG_MASSLOADER_193.replace("$1", OperazioneScheda.MODIFICA).replace("$2", IdentificativoSchede.SUBAPPALTO));
			try{
				
				sm.save(sb, userName);
				sm.confirm(sb, userName);
				
				//TICKET ALM - 3.04.3 #4198
				SubappaltiManager subManager = new SubappaltiManager(con,
						this.logger);
				//Fai scadere le precedenti ditte e, se indicate, inserisci le nuove
				subManager.expireDitteSubappaltatrici(sb);
				for (SubappaltatoreBean subBean : sb.getSubappaltatori()) {
					subBean.setIdSubappalto(sb.getIdRecord());
					subBean.setDataInizioSubappalto(sb.getDataInizioRecord());
					
					if (!modificaAnagraficaAggiudicatariSub(subBean,
							listOfAnapartecipante, subBean.getSoggettoPartecipante()
									.getCodiceFiscale())){
						//throw new Exception(".. no comment ..");
					   esitiOperazioni.add(new SchedaSpecificaValidationBean(
					         Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_PARTECIPANTE)
					            .replace("$2", subBean.getSoggettoPartecipante().getCodiceFiscale()), 
					            ValidationBean.VALBEAN_SEV_ERR, elemento,
		                        progressivoSchedaCompleta,  0, IdentificativoSchede
								.getSubAppalti().getNomeScheda(), 
		                        cig, "", "", ""));
					   retVal = false;
					}
					else{
						
					    subManager.insertDitteSubappaltatrici(subBean);

					}
	
				}
				//FINE TICKET ALM - 3.04.3 #4198
				
				esitiOperazioni.add(
						new SchedaSpecificaValidationBean(
								commonInfoMessage,infoSeverity,
								elemento,progressivoSchedaCompleta,i,
								IdentificativoSchede.getSubAppalti().getNomeScheda(),cig, 
								getCuiForDummies(schedaA),String.valueOf(sb.getIdRecord()),sb.getIdLocale()));				
			}catch(Exception e){
				e.printStackTrace();
				throw modificaException;
			}
			i++;
		}return retVal;
		

	}
	
	
}
