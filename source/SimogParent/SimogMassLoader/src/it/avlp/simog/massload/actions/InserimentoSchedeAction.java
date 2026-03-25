package it.avlp.simog.massload.actions;

import it.avlp.simog.beans.InfoComuniBean;
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
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.StatoScheda;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.accordi.SchedaAccordo;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
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
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.flusso.OperazioneScheda;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.massload.bean.IdsSchedaXML;
import it.avlp.simog.massload.bean.schede.TutteLeSchede;
import it.avlp.simog.massload.esito.EsitoOperazioneControlloLogico;
import it.avlp.simog.massload.esito.EsitoOperazioneInserimentoOModifica;
import it.avlp.simog.massload.util.conversion.SituazioneAttualeSchedeXml;
import it.avlp.simog.rubricamanager.RubricaManager;
import it.avlp.simog.util.SimogProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class InserimentoSchedeAction extends GenericSchedeAction {

	private String userName;

	// flag che mi serve per determinare se in fase di feedback (con eccezione
	// dirante l'inserimento) devo valorizzare il cui o meno.
	public boolean amIInsertingAggiudicazioni = false;

	private String infoSeverity = ValidationBean.VALBEAN_SEV_INFO;
	// XXX: nota che siccome e' esito positivo questo messaggio non risulta nel
	// feddback
	private String commonInfoMessage = "Scheda Inserita Correttamente";

	/**
	 * Classe che si occupa delle operazioni inerenti all'operazione di
	 * inserimento di dati xml.
	 * 
	 * @param con
	 * @param logger
	 */
	public InserimentoSchedeAction(Connection con, Logger logger, String user, OrigineSchedaEnum origine) {
		super(con, logger, origine);
		this.userName = user;
	}

	// /**
	// * Controllo la correttezza del flusso per l'operazione di inserimento
	// *
	// * @param situazioneAttuale
	// * @return
	// */
	// private EsitoOperazioneControlloLogico
	// controllaCorrettezzaFlusso(SituazioneSchedeAttuale situazioneAttuale, int
	// progressivoSchedaCompleta){
	// return controllaCorrettezzaFlusso(situazioneAttuale,
	// OperazioneScheda.getInserimento(), progressivoSchedaCompleta);
	// }

	/**
	 * Metodo piu' preciso di quello invocato si occupa di valorizzare i
	 * progressivi delle schede multiple informazione che prima non avevo.
	 * Quindi le operazioni di controllo vegono fatte a valle, qui rimedio al
	 * progressivo.
	 * 
	 * @param ids
	 * @return
	 */
	public EsitoOperazioneControlloLogico controllaCorrettezzaFlussoConProgressivo(
			IdsSchedaXML ids, String user) {
		return super.controllaCorrettezzaFlussoConProgressivo(ids,
				OperazioneScheda.getInserimento(),user);
	}

	/**
	 * Metodo che inserisce una scheda che comprende dati comuni, n schede
	 * complete (1,1 CUI);
	 * 
	 * @param idScheda
	 * @param situazioneAttuale
	 *            ce l'ho in idsSchedaXMl..
	 * @return
	 */
	public EsitoOperazioneInserimentoOModifica inserisciScheda(
			IdsSchedaXML idScheda, TutteLeSchede tutteLeSchede, boolean noConfirm, boolean noConfirmAgg)
			throws InserimentoException {

		esitiOperazioni = new ArrayList<SchedaSpecificaValidationBean>();
		EsitoOperazioneInserimentoOModifica esitoInserimento = new EsitoOperazioneInserimentoOModifica();
		esitoInserimento.setListOfSuccess(esitiOperazioni);
		SituazioneAttualeSchedeXml situazioneXML = idScheda
				.getSituazioneAttualeXml();
		SituazioneSchedeAttuale situazioneDb = idScheda.getSituazioneAttuale();
		String cig = idScheda.getCig();
		boolean esito = true;
		boolean esitoFineLavori = true; //TICKET ALM #3437
		int elemento = idScheda.getCardinalitaSchedaCig();
		int progressivoSchedaCompleta = idScheda.getCardinalitaSchedaCompleta();
		//MAC 33380 3.04.8.1
				InfoComuniManager infoManager = new InfoComuniManager(con, logger);
				try {
					List<InfoComuniBean> checkList = infoManager.getListInfoComuniByCig(cig.substring(0, 8));
					if (checkList.isEmpty()) {
						logger.info("Non esistono schede Dati Comuni sul DB per il CIG "+ cig + " , " + progressivoSchedaCompleta + " , " + elemento);
					}
					else {
						for(InfoComuniBean info : checkList) {
							logger.info(info.getCig()+info.getCigCycle());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		
		if (situazioneXML.isPresentDatiComuni()
				&& !(situazioneDb.getStatoDatiComuni().isEsistente() && !situazioneDb
						.getStatoDatiComuni().isFromXml())) {
			//MAC 33380 3.04.8.1 è stato aggiunto un logger di controllo
			logger.info("Non esistono schede Dati Comuni sul DB per il CIG "+ cig + " , " + progressivoSchedaCompleta + " , " + elemento);
			esito = esito
					&& insertDatiComuni(situazioneDb.getStatoDatiComuni(),
							tutteLeSchede.getSchedaA(),
							tutteLeSchede.getListOfAnagraficheResponsabili(),
							cig, elemento, progressivoSchedaCompleta,	noConfirm);
		}
		if (situazioneXML.isPresentSchedaCompleta()) {

			Scheda_A schedaA = tutteLeSchede.getSchedaA();
			List<SoggettoPartecipanteBean> listOfAnapartecipante = tutteLeSchede
					.getListOfAnagrafichePartecipanti();
			List<SoggettoResponsabileBean> listOfAnaResponsabile = tutteLeSchede
					.getListOfAnagraficheResponsabili();

			if (situazioneXML.isPresentAggiudicazione()
					&& !situazioneDb.getStatoAggiudicazione().isEsistenteDb()) {
				esito = esito
						&& insertAggiudicazione(
								situazioneDb.getStatoAggiudicazione(), schedaA,
								listOfAnapartecipante, listOfAnaResponsabile,
								cig, elemento, progressivoSchedaCompleta,
								TipoAggiudicazione.A, noConfirmAgg);
				esitoInserimento.setCui(getCuiForDummies(schedaA));
			}
			
			if (situazioneXML.isPresentAdesione()
					&& !situazioneDb.getStatoAdesione().isEsistenteDb()) {
				esito = esito
						&& insertAggiudicazione(
								situazioneDb.getStatoAdesione(), schedaA,
								listOfAnapartecipante, listOfAnaResponsabile,
								cig, elemento, progressivoSchedaCompleta,
								TipoAggiudicazione.Q, noConfirmAgg);
				esitoInserimento.setCui(getCuiForDummies(schedaA));
			}

			if (situazioneXML.isPresentSottosoglia()
					&& !situazioneDb.getStatoSottosoglia().isEsistenteDb()) {
				esito = esito
						&& insertAggiudicazione(
								situazioneDb.getStatoSottosoglia(), schedaA,
								listOfAnapartecipante, listOfAnaResponsabile,
								cig, elemento, progressivoSchedaCompleta,
								TipoAggiudicazione.S, noConfirmAgg);
				esitoInserimento.setCui(getCuiForDummies(schedaA));
			}

			if (situazioneXML.isPresentEscluso()
					&& !situazioneDb.getStatoEscluso().isEsistenteDb()) {
				esito = esito
						&& insertAggiudicazione(situazioneDb.getStatoEscluso(),
								schedaA, listOfAnapartecipante,
								listOfAnaResponsabile, cig, elemento,
								progressivoSchedaCompleta, TipoAggiudicazione.E, noConfirmAgg);
				esitoInserimento.setCui(getCuiForDummies(schedaA));
			}

			if (situazioneXML.isPresentInizioLavori()
					&& !situazioneDb.getStatoInizioLavori().isEsistenteDb()) {
				esito = esito
						&& insertDatiInizio(
								situazioneDb.getStatoInizioLavori(),
								tutteLeSchede.getSchedaInizio(), schedaA,
								listOfAnapartecipante, listOfAnaResponsabile,
								cig, elemento, progressivoSchedaCompleta);
			}

			if (situazioneXML.isPresentStipula()
					&& !situazioneDb.getStatoStipula().isEsistenteDb()) {
				esito = esito
						&& insertDatiStipula(situazioneDb.getStatoStipula(),
								tutteLeSchede.getSchedaStipula(), schedaA,
								listOfAnapartecipante, listOfAnaResponsabile,
								cig, elemento, progressivoSchedaCompleta);
			}

			if (situazioneXML.isPresentAvanzamenti()) {
				esito = esito
						&& insertDatiAvanzamenti(
								situazioneDb.getStatoAvanzamento(),
								tutteLeSchede.getSchedeAvanzamento(), schedaA,
								cig, elemento, progressivoSchedaCompleta);
			}
			if (situazioneXML.isPresentConclusione()
					&& !situazioneDb.getStatoConclusione().isEsistenteDb()) {
				
				//TICKET ALM #3437
				Long motiviInterr = tutteLeSchede.getSchedaConclusione().getConclusione().getMotiviInterruzione();
				GaraManager gm = new GaraManager(con,logger);
				Gara g = null;
				try {
					g = gm.getGara(schedaA.getInfoGara().getIdGara());
				} catch (SQLException err) {
					// TODO Auto-generated catch block
					err.printStackTrace();
				} catch (Exception erro) {
					// TODO Auto-generated catch block
					erro.printStackTrace();
				}
				if( !SimogFlags.isAccordoQuadroOrConvenzione(schedaA.getInfoGara().getID_MODO_REAL()) && !SimogFlags.isSvolgimentoAccordoQuadro(g.getID_SVOLGIMENTO()) && (motiviInterr == null || motiviInterr.longValue() == 0) && !situazioneXML.isPresentInizioLavori() && !situazioneDb.getStatoInizioLavori().isEsistenteDb() )
					esitoFineLavori = false;
				else //FINE TICKET ALM #3437
					esito = esito
							&& insertDatiConclusione(
									situazioneDb.getStatoConclusione(),
									tutteLeSchede.getSchedaConclusione(), schedaA,
									cig, elemento, progressivoSchedaCompleta);
			}
			if (situazioneXML.isPresentCollaudo()
					&& !situazioneDb.getStatoCollaudo().isEsistenteDb()) {
				esito = esito
						&& insertDatiCollaudo(situazioneDb.getStatoCollaudo(),
								tutteLeSchede.getSchedaCollaudo(), schedaA,
								listOfAnaResponsabile, cig, elemento,
								progressivoSchedaCompleta);
			}
			if (situazioneXML.isPresentAccordi()) {
				esito = esito
						&& insertDatiAccordi(situazioneDb.getStatoAccordi(),
								tutteLeSchede.getSchedeAccordo(), schedaA, cig,
								elemento, progressivoSchedaCompleta);
			}
			if (situazioneXML.isPresentRitardo()) {
				esito = esito
						&& insertDatiRitardi(situazioneDb.getStatoRitardo(),
								tutteLeSchede.getSchedeRitardi(), schedaA, cig,
								elemento, progressivoSchedaCompleta);
			}
			if (situazioneXML.isPresentSospensioni()) {
				esito = esito
						&& insertDatiSospensioni(
								situazioneDb.getStatoSospensioni(),
								tutteLeSchede.getSchedeSospensione(), schedaA,
								cig, elemento, progressivoSchedaCompleta);
			}
			if (situazioneXML.isPresentSubAppalti()) {
				esito = esito
						&& insertDatiSubbappalti(
								situazioneDb.getStatoSubAppalti(),
								tutteLeSchede.getSchedeSubAppalto(), schedaA,
								listOfAnapartecipante,
								cig, elemento, progressivoSchedaCompleta);
			}
			if (situazioneXML.isPresentVarianti()) {
				esito = esito
						&& insertDatiVarianti(situazioneDb.getStatoVarianti(),
								tutteLeSchede.getSchedeVariante(), schedaA,
								cig, elemento, progressivoSchedaCompleta);
			}

		}
		esitoInserimento.setEsitoOperazione(esito);
		
		if(!esito)
		   throw new InserimentoException(IdentificativoSchede.getDatiComuni(), cig, "", "", "", Messaggi.SIMOG_MASSLOADER_199 );
		
		//TICKET ALM #3437
		if(!esitoFineLavori) {
			esitoInserimento.setEsitoOperazione(esitoFineLavori);
			throw new InserimentoException(IdentificativoSchede.getConclusione(), cig, "", "", "", Messaggi.SIMOG_VALIDAZIONE_243.replace("$1", "Scheda Conclusione") );
		}//FINE TICKET ALM #3437
		
	
	return esitoInserimento;
	}

	/**
	 * Inserisce, modifica, o non fa nulla per l'anagrafica di un responsabile
	 * 
	 * @param listOfAnaResponsabile
	 * @param cf
	 * @return
	 * @throws SQLException
	 */
	public boolean inserisciAnagraficaResponsabili(ResponsabileBean rb,
			List<SoggettoResponsabileBean> listOfAnaResponsabile, String cf,
			boolean isOnlyCf) throws SQLException {
		return super.modificaAnagraficaResponsabili(rb, listOfAnaResponsabile,
				cf, isOnlyCf);

	}

	/**
	 * Inserisce o aggiorna o non fa nulla per le anagrafiche degli
	 * aggiudicatari
	 * 
	 * @param listOfAnapartecipante
	 * @param cf
	 * @param codiceNazione
	 * @return
	 * @throws SQLException
	 */
	public boolean inserisciAnagraficaAggiudicatari(
			PosizioneAggiudicatarioBean bean, AggiudicatarioBean ab,DittaAusiliariaBean da, ResponsabileBean rb,
			List<SoggettoPartecipanteBean> listOfAnapartecipante, String cf,
			String codiceNazione, boolean isAggiudicatario, boolean isAusiliaria, boolean isRespGiuridico) throws SQLException {
		return super.modificaAnagraficaAggiudicatari(bean, ab, da,rb,
				listOfAnapartecipante, cf, codiceNazione, isAggiudicatario, isAusiliaria, isRespGiuridico);

	}

	public boolean inserisciAnagraficaAggiudicatariSub(SubappaltatoreBean sb,
			List<SoggettoPartecipanteBean> listOfAnapartecipante, String cf) throws SQLException {
		return super.modificaAnagraficaAggiudicatariSub(sb,	listOfAnapartecipante, cf);

	}

	/**
	 * Inserimento di tutti i dati relativi ai dati comuni
	 * 
	 * @param schedaA
	 * @param listOfAnaResponsabile
	 * @return
	 * @throws InserimentoException
	 */
	public boolean insertDatiComuni(StatoScheda statoDatiComuni,
			Scheda_A schedaA,
			List<SoggettoResponsabileBean> listOfAnaResponsabile, String cig,
			int elemento, int progressivoSchedaCompleta, boolean noConfirm)
			throws InserimentoException {
		InserimentoException inserimentoException = new InserimentoException(
				IdentificativoSchede.getDatiComuni(), cig, null, schedaA
						.getInfoComuni().getIdLocale(),
				getStringValueOfLong(schedaA.getInfoComuni().getIdInfo()),
				Messaggi.SIMOG_MASSLOADER_193.replace("$1",
						OperazioneScheda.INSERIMENTO).replace("$2",
						IdentificativoSchede.DATI_COMUNI));

		try {

			PubblicazioneManager pubblicazioneManager = new PubblicazioneManager(
					con, logger);
			pubblicazioneManager.insertPubblicazione(schedaA.getInfoComuni().getPubblicazione());
			
			if(!noConfirm)
			   pubblicazioneManager.confirm(schedaA.getInfoComuni().getPubblicazione());

         if(SimogFlags.is3028_RFWEBSC00Active()){
            schedaA.getInfoComuni().setOrigine(this.origine.code());
            // preservo origine avcpass
            if(statoDatiComuni.getOrigine() == OrigineSchedaEnum.AVCPASS.code())
               schedaA.getInfoComuni().setOrigine(OrigineSchedaEnum.AVCPASS.code());
         }
         
			InfoComuniManager infoManager = new InfoComuniManager(con, logger);
			infoManager.insert(schedaA.getInfoComuni(), userName);
         
			if(!noConfirm)
            infoManager.confirm(schedaA.getInfoComuni(), userName);

			inserisciAnagraficaResponsabili(null, listOfAnaResponsabile,
					schedaA.getInfoComuni().getCfRup(), true);

            // aggiornamento dei campi gara e lotto
			infoManager.updateGaraLotto(con, logger, schedaA.getInfoComuni());

			// aggiornamento dello stato
			statoDatiComuni.setFromXml(false);
			statoDatiComuni.setIdRecord(schedaA.getInfoComuni().getIdInfo());
			statoDatiComuni.setDataInizioRecord(schedaA.getInfoComuni()
					.getDataInizioInfo());
			statoDatiComuni.setAsConfermato(false);
			statoDatiComuni.setStato(StatiScheda.CONFERMATO);
			// -- end --

			esitiOperazioni.add(new SchedaSpecificaValidationBean(
					commonInfoMessage, infoSeverity, elemento,
					progressivoSchedaCompleta, 0, IdentificativoSchede
							.getDatiComuni().getNomeScheda(), cig, null, String
							.valueOf(schedaA.getInfoComuni().getIdInfo()),
					schedaA.getInfoComuni().getIdLocale()));

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			throw inserimentoException;
		}
	}

	/**
	 * Inserimento di tutti i dati relativi alla aggiudicazione
	 * 
	 * @param schedaA
	 * @param listOfAnapartecipante
	 * @param listOfAnaResponsabile
	 * @return
	 * @throws InserimentoException
	 */
	private boolean insertAggiudicazione(StatoScheda statoAggiudicazione,
			Scheda_A schedaA,
			List<SoggettoPartecipanteBean> listOfAnapartecipante,
			List<SoggettoResponsabileBean> listOfAnaResponsabile, String cig,
			int elemento, int progressivoSchedaCompleta,
			TipoAggiudicazione tipoAgg, boolean noConfirm) throws InserimentoException {

	    boolean retVal = true;
	    
		IdentificativoSchede idScheda = IdentificativoSchede
				.getAggiudicazione();
		if (TipoAggiudicazione.Q.equals(tipoAgg))
			idScheda = IdentificativoSchede.getAdesione();
		else if (TipoAggiudicazione.E.equals(tipoAgg))
			idScheda = IdentificativoSchede.getEscluso();
		else if (TipoAggiudicazione.S.equals(tipoAgg))
			idScheda = IdentificativoSchede.getSottosoglia();

		amIInsertingAggiudicazioni = true;
		InserimentoException inserimentoException = new InserimentoException(
				idScheda, cig, schedaA.getAggiudicazione().getCui(), schedaA
						.getAggiudicazione().getIdLocale(),
				getStringValueOfLong(schedaA.getAggiudicazione()
						.getIdAggiudicazione()), Messaggi.SIMOG_MASSLOADER_193
						.replace("$1", OperazioneScheda.INSERIMENTO).replace(
								"$2", idScheda.getNomeScheda()));

		try {
			// nel caso in cui anche infoComuni sia stato inserito adesso ho
			// bisogno degli estremi
			schedaA.getAggiudicazione().setIdInfo(
					schedaA.getInfoComuni().getIdInfo());
			schedaA.getAggiudicazione().setDataInizioInfo(
					schedaA.getInfoComuni().getDataInizioInfo());
			schedaA.getAggiudicazione().setSottotipo(tipoAgg);
			// --
			AggiudicazioniManager am = new AggiudicazioniManager(con, logger);
			// valorizzazione del cui in aggiudicazione
			am.fillCuiAndProgCui(schedaA.getAggiudicazione(), schedaA
					.getInfoComuni().getCig(), String.valueOf(schedaA
					.getInfoComuni().getCigCycle()), schedaA.getInfoComuni()
					.getIdInfo());

			if(SimogFlags.is3028_RFWEBSC00Active()){
			   schedaA.getAggiudicazione().setOrigine(this.origine.code());
            // preservo origine avcpass
            if(statoAggiudicazione.getOrigine() == OrigineSchedaEnum.AVCPASS.code())
               schedaA.getAggiudicazione().setOrigine(OrigineSchedaEnum.AVCPASS.code());
			}
			
			//TICKET ALM #3835
			//Ottieni la data di creazione della gara
			if(SimogFlags.is3042Active()) {
				long dataCreazioneTime = 0;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Gara g = new GaraManager(con,logger).getGara(schedaA.getInfoGara().getIdGara());
				Lotto l = new LottoManager(con,logger).getLotto(schedaA.getInfoGara().getIdLotto());
				String dataCreazioneStr = g.getData_creazione();
				dataCreazioneTime = sdf.parse(dataCreazioneStr).getTime();
			
				//Se la gara e' stata pubblicata successivamente, la scelta del contraente e modalita indizione vengono scartate e recuperata dal lotto e gara
				if(dataCreazioneTime >= SimogProperties.getInstance().getDataAttivazione3042Timestamp()) {
					
					//TICKET ALM #3835-08.3 e #3835-10.3
					schedaA.getAggiudicazione().setIdSceltaContraente(Long.parseLong(l.getId_Scelta_Contraente()));
					//TICKET ALM #4214-08.3
					if(g.getID_MODO_GARA() != 0)
					    schedaA.getAggiudicazione().setIdModoIndizione(g.getID_MODO_GARA());
				}
			}//FINE TICKET ALM #3835
			
			am.insert(schedaA.getAggiudicazione(), userName);
			
         if(!noConfirm)
            am.confirm(schedaA.getAggiudicazione(), userName);


			long idAggiudicazione = schedaA.getAggiudicazione()
					.getIdAggiudicazione();
			Timestamp dataInizioAggiudicazione = schedaA.getAggiudicazione()
					.getDataInizioAggiudicazione();

         if( SimogFlags.is3031_RFWEBGL02Active() 
               && SimogProperties.getInstance().isCUPAttivo()){

            CupLottoAggAction claAction = new CupLottoAggAction(con, logger);
            
            if(!SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())){
               // a causa dell'introduzione della funzione di integrazione dati cup, sono costretto a tentare la cancellazione
               // di eventuali record presenti
               try {
                  claAction.getCupLottoAggManager().deleteCup(schedaA.getInfoGara().getIdLotto(), false);
               } catch (SQLException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }

               claAction.updateElencoCup(schedaA.getFlagCUP(),schedaA.getElencoCup(), schedaA.getInfoGara().getIdLotto(), idAggiudicazione, dataInizioAggiudicazione, true);
               claAction.confirmAllValidCup(schedaA.getElencoCup());           
            }
            else
               claAction.completaDatiAggCup(schedaA.getInfoGara().getIdLotto(), idAggiudicazione, dataInizioAggiudicazione);
         }
                  
			if (schedaA.getAggiudicatari() != null
					&& !schedaA.getAggiudicatari().isEmpty()) {
				AggiudicatarioManager aggm = new AggiudicatarioManager(con,
						this.logger);
				DittaAusiliariaManager dam = new DittaAusiliariaManager(con,
						this.logger);
				for (AggiudicatarioBean ab : schedaA.getAggiudicatari()) {
					ab.setIdAggiudicazione(idAggiudicazione);
					ab.setDataInizioAggiudicazione(dataInizioAggiudicazione);
					if (!inserisciAnagraficaAggiudicatari(null, ab,null,null,
							listOfAnapartecipante, ab.getSoggettoPartecipante()
									.getCodiceFiscale(), ab
									.getSoggettoPartecipante().getId_stato(),
							true,false,false)){
						//throw new Exception(".. no comment ..");
					   esitiOperazioni.add(new SchedaSpecificaValidationBean(
					         Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_PARTECIPANTE)
					            .replace("$2", ab.getSoggettoPartecipante().getCodiceFiscale()), 
					            ValidationBean.VALBEAN_SEV_ERR, elemento,
		                        progressivoSchedaCompleta,  0, idScheda.getNomeScheda(), 
		                        cig, "", "", ""));
					   retVal = false;
					}
					else{
					   if(noConfirm)
					      aggm.save(ab);
					   else
					      aggm.confirm(ab); 
					}
					// RETROCOMPATIBILITA
					if(ab.getDitteAusiliarie() != null && !ab.getDitteAusiliarie().isEmpty()){
						for(DittaAusiliariaBean dab: ab.getDitteAusiliarie()){
							if (!inserisciAnagraficaAggiudicatari(null, null,dab,null,
									listOfAnapartecipante, dab.getSoggettoPartecipante().getCodiceFiscale(), 
									dab.getSoggettoPartecipante().getId_stato(),false,true,false)){
								//throw new Exception(".. no comment 2..");
			                    esitiOperazioni.add(new SchedaSpecificaValidationBean(
			                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_AUSILIARIA)
			                                .replace("$2", dab.getCfAusiliaria()), 
			                                ValidationBean.VALBEAN_SEV_ERR, elemento,
			                                progressivoSchedaCompleta,  0, idScheda.getNomeScheda(), 
			                                cig, "", "", ""));
			                    retVal = false;

							}
							else{
      							dab.setIdAggiudicatario(ab.getIdAggiudicatario());
      							dab.setDataInizioAggiudicatario(ab.getDataInizioAggiudicatario());
      							dab.setIdAggiudicazione(ab.getIdAggiudicazione());
      							dab.setDataInizioAggiudicazione(ab.getDataInizioAggiudicazione());
      							
      		               if(noConfirm)
                              dam.update(dab, false);
      		               else
      		                  dam.update(dab, true);
	                  }
						}
					}
				}
			}
			
			
			if (schedaA.getPrestazioni() != null
					&& !schedaA.getPrestazioni().isEmpty()) {
				ResponsabileManager rm = new ResponsabileManager(con, logger);
				for (ResponsabileBean rb : schedaA.getPrestazioni()) {
					rb.setIdScheda(idAggiudicazione);
					rb.setDataInizioScheda(dataInizioAggiudicazione);
				
					if(rb.getSoggettoPartecipante() != null ){
						
						if(rb.getSoggettoPartecipante().getIdSoggettoPartecipante()==0) {
							RubricaManager rubricaMan = new RubricaManager(con,logger);
							SoggettoPartecipanteBean soggDB = rubricaMan.getAllSoggettoPartecipanteByCF(rb.getSoggettoPartecipante().getCodiceFiscale(),
									String.valueOf(rb.getSoggettoPartecipante().getId_stato()));
							if(soggDB!=null)
								rb.setSoggettoPartecipante(soggDB);
						}
						
						if (!inserisciAnagraficaAggiudicatari(null, null,null,rb,
								listOfAnapartecipante, rb.getSoggettoPartecipante()
										.getCodiceFiscale(), String.valueOf(rb.getSoggettoPartecipante().getId_stato()),
								false,false,true)){
                           esitiOperazioni.add(new SchedaSpecificaValidationBean(
                                 Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_PROGETTISTA)
                                    .replace("$2", rb.getSoggettoPartecipante().getCodiceFiscale()), 
                                    ValidationBean.VALBEAN_SEV_ERR, elemento,
                                    progressivoSchedaCompleta,  0, idScheda.getNomeScheda(), 
                                    cig, "", "", ""));
                           retVal = false;

						} else{
						   if(noConfirm)
						      rm.save(rb);
						   else
						      rm.confirm(rb);
							//throw new Exception(".. no comment ..");
						}
					}
					else {
						if (!inserisciAnagraficaResponsabili(rb,
								listOfAnaResponsabile, rb.getSoggettoResponsabile()
										.getCodiceFiscaleResponsabile(), false)){
                           esitiOperazioni.add(new SchedaSpecificaValidationBean(
                                 Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_PROGETTISTA)
                                    .replace("$2", rb.getSoggettoResponsabile().getCodiceFiscaleResponsabile()), 
                                    ValidationBean.VALBEAN_SEV_ERR, elemento,
                                    progressivoSchedaCompleta,  0, idScheda.getNomeScheda(), 
                                    cig, "", "", ""));
                           retVal = false;

						}
						else {
                     if(noConfirm)
                        rm.save(rb);
                     else   
                        rm.confirm(rb);
							//throw new Exception(".. no comment ..");
						}
					}			
				}
			}
			
			
			if (schedaA.getResponsabili() != null
					&& !schedaA.getResponsabili().isEmpty()) {
				ResponsabileManager rm = new ResponsabileManager(con, logger);
				for (ResponsabileBean pr : schedaA.getResponsabili()) {
					pr.setIdScheda(idAggiudicazione);
					pr.setDataInizioScheda(dataInizioAggiudicazione);
					if (!inserisciAnagraficaResponsabili(pr,
							listOfAnaResponsabile, pr.getSoggettoResponsabile()
									.getCodiceFiscaleResponsabile(), false)){
                       esitiOperazioni.add(new SchedaSpecificaValidationBean(
                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE)
                                .replace("$2", pr.getSoggettoResponsabile().getCodiceFiscaleResponsabile()), 
                                ValidationBean.VALBEAN_SEV_ERR, elemento,
                                progressivoSchedaCompleta,  0, idScheda.getNomeScheda(), 
                                cig, "", "", ""));
                       retVal = false;

					}
					else	{//throw new Exception(".. no comment ..");
                  if(noConfirm)
                     rm.save(pr);
                  else
                     rm.confirm(pr);
					}
				}
			}

			if (schedaA.getCondizioni() != null
					&& !schedaA.getCondizioni().isEmpty()) {
				CondizioniManager cm = new CondizioniManager(con, logger);
				for (CondizioneAggBean cab : schedaA.getCondizioni()) {
					cab.setIdAggiudicazione(idAggiudicazione);
					cab.setDataInizioAggiudicazione(dataInizioAggiudicazione);
               if(noConfirm)
                  cm.save(cab);
               else
                  cm.confirm(cab);
				}
			}
			if (schedaA.getFinanziamenti() != null
					&& !schedaA.getFinanziamenti().isEmpty()) {
				FinanziamentoManager fm = new FinanziamentoManager(con, logger);
				for (TipoFinanziamentoBean tfb : schedaA.getFinanziamenti()) {
					tfb.setIdAggiudicazione(idAggiudicazione);
					tfb.setDataInizioAggiudicazione(dataInizioAggiudicazione);
               if(noConfirm)
                  fm.save(tfb);
               else
                  fm.confirm(tfb);
				}
			}

         TipoAppaltoManager tam = new TipoAppaltoManager(con, logger);

			if(SimogFlags.is3031_RFWEBGL00Active()){
	         if (schedaA.getTipoAppalto() != null && !schedaA.getTipoAppalto().isEmpty()){
	            if(SimogProperties.getInstance().isCUPLotto(schedaA.getInfoGara().getDataCreazioneGara())){
	               tam.completaDatiTipoAppalto(schedaA.getInfoGara().getIdLotto(), idAggiudicazione, dataInizioAggiudicazione);
	            }
	            else{
                  // a causa dell'introduzione della funzione di integrazione dati cup, sono costretto a tentare la cancellazione
                  // di eventuali record presenti
                  try {
                        tam.deleteAppaltiAgg(schedaA.getInfoGara().getIdLotto());
                  } catch (SQLException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }

	               for (TipoAppaltoAggBean taab : schedaA.getTipoAppalto()) {
	                  taab.setIdAggiudicazione(idAggiudicazione);
	                  taab.setDataInizioAggiudicazione(dataInizioAggiudicazione);
	                  taab.setIdLotto(schedaA.getInfoGara().getIdLotto());
	                  
	                  if(noConfirm)
	                     tam.save(taab);
	                  else
	                     tam.confirm(taab);
	               }               
	            }
	         }
			}
			else{
			   // gestione precedente
            if (schedaA.getTipoFS() != null && !schedaA.getTipoFS().isEmpty()) {
               for (TipoAppaltoAggBean taab : schedaA.getTipoFS()) {
                  taab.setIdAggiudicazione(idAggiudicazione);
                  taab.setDataInizioAggiudicazione(dataInizioAggiudicazione);
                  
                  if(noConfirm)
                     tam.save(taab);
                  else
                     tam.confirm(taab);
               }               
   			}
   			if (schedaA.getTipoLavoro() != null && !schedaA.getTipoLavoro().isEmpty()) {
   				for (TipoAppaltoAggBean taab : schedaA.getTipoLavoro()) {
   					taab.setIdAggiudicazione(idAggiudicazione);
   					taab.setDataInizioAggiudicazione(dataInizioAggiudicazione);
   					
                  if(noConfirm)
                     tam.save(taab);
                  else
                     tam.confirm(taab);
   				}
   			}
         }         

			if (schedaA.getRequisiti() != null
					&& !schedaA.getRequisiti().isEmpty()) {
				RequisitiManager rm = new RequisitiManager(con, logger);
				for (RequisitiBean rb : schedaA.getRequisiti()) {
					rb.setIdAggiudicazione(idAggiudicazione);
					rb.setDataInizioAggiudicazione(dataInizioAggiudicazione);
               if(noConfirm)
                  rm.save(rb);
               else
                  rm.confirm(rb);
				}
			}

			// aggiornamento dello stato
			statoAggiudicazione.setFromXml(false);
			statoAggiudicazione.setIdRecord(schedaA.getAggiudicazione()
					.getIdAggiudicazione());
			statoAggiudicazione.setDataInizioRecord(schedaA.getAggiudicazione()
					.getDataInizioAggiudicazione());
			statoAggiudicazione.setAsConfermato(false);
			statoAggiudicazione.setStato(StatiScheda.CONFERMATO);
			// -- end --

			if(retVal)
      			esitiOperazioni.add(new SchedaSpecificaValidationBean(
      					commonInfoMessage, infoSeverity, elemento,
      					progressivoSchedaCompleta, 0, idScheda.getNomeScheda(),
      					cig, getCuiForDummies(schedaA), String.valueOf(schedaA
      							.getAggiudicazione().getIdAggiudicazione()),
      					schedaA.getAggiudicazione().getIdLocale()));
			return retVal;
		} catch (Exception e) {
			e.printStackTrace();
			throw inserimentoException;
		}
	}

	/**
	 * Inserisce tutti i dati relativi alla scheda inizio
	 * 
	 * @param schedaInizioLavori
	 * @param schedaA
	 * @param listOfAnapartecipante
	 * @param listOfAnaResponsabile
	 * @return
	 * @throws InserimentoException
	 */
	private boolean insertDatiInizio(StatoScheda statoInizioLavori,
			SchedaInizioLavori schedaInizioLavori, Scheda_A schedaA,
			List<SoggettoPartecipanteBean> listOfAnapartecipante,
			List<SoggettoResponsabileBean> listOfAnaResponsabile, String cig,
			int elemento, int progressivoSchedaCompleta)
			throws InserimentoException {
	   
	   boolean retVal = true;
	   
		InserimentoException inserimentoException = new InserimentoException(
				IdentificativoSchede.getInizioLavori(), cig,
				getCuiForDummies(schedaA), schedaInizioLavori.getDatiInizio()
						.getIdLocale(), getStringValueOfLong(schedaInizioLavori
						.getDatiInizio().getIdInizioLavori()),
				Messaggi.SIMOG_MASSLOADER_193.replace("$1",
						OperazioneScheda.INSERIMENTO).replace("$2",
						IdentificativoSchede.FASE_INIZIALE));

		try {
			schedaInizioLavori.getDatiInizio().setDataInizioAggiudicazione(
					schedaA.getAggiudicazione().getDataInizioAggiudicazione());
			schedaInizioLavori.getDatiInizio().setIdAggiudicazione(
					schedaA.getAggiudicazione().getIdAggiudicazione());

			PubblicazioneManager pm = new PubblicazioneManager(con, logger);
			pm.insertPubblicazione(schedaInizioLavori.getDatiInizio()
					.getPubblicazione());
			pm.confirm(schedaInizioLavori.getDatiInizio().getPubblicazione());

			InizioLavoriManager im = new InizioLavoriManager(con, logger);
			im.insert(schedaInizioLavori.getDatiInizio(), userName);
			im.confirm(schedaInizioLavori.getDatiInizio(), userName);

			List<ResponsabileBean> responsabili = schedaInizioLavori
					.getResponsabiliInizio();
			if (responsabili != null && responsabili.size() > 0) {
				ResponsabileInizioManager rm = new ResponsabileInizioManager(
						con, logger);
				for (ResponsabileBean bean : responsabili) {
					// gestione anagrafica
					if (!inserisciAnagraficaResponsabili(bean,
							listOfAnaResponsabile, bean
									.getSoggettoResponsabile()
									.getCodiceFiscaleResponsabile(), false)){
						//throw new Exception(".. no comment ..");
                       esitiOperazioni.add(new SchedaSpecificaValidationBean(
                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE)
                                .replace("$2", bean.getSoggettoResponsabile().getCodiceFiscaleResponsabile()), 
                                ValidationBean.VALBEAN_SEV_ERR, elemento,
                                progressivoSchedaCompleta,  0, IdentificativoSchede.getInizioLavori().getNomeScheda(), 
                                cig, getCuiForDummies(schedaA), schedaInizioLavori.getDatiInizio()
                                .getIdLocale(), getStringValueOfLong(schedaInizioLavori
                                      .getDatiInizio().getIdInizioLavori())));
                       retVal = false;

					}
					else{
      					bean.setIdScheda(schedaInizioLavori.getDatiInizio()
      							.getIdInizioLavori());
      					bean.setDataInizioScheda(schedaInizioLavori.getDatiInizio()
      							.getDataInizioLavori());
      					rm.confirm(bean);
					}
				}
			}
			List<PosizioneAggiudicatarioBean> posizioni = schedaInizioLavori
					.getPosizioneAggiudicatari();
			if (posizioni != null && posizioni.size() > 0) {
				PosizAggiudManager posMan = new PosizAggiudManager(con, logger);
				for (PosizioneAggiudicatarioBean bean : posizioni) {
					// gestione anagrafica
					if (!inserisciAnagraficaAggiudicatari(bean, null,null,null,
							listOfAnapartecipante, bean
									.getSoggettoPartecipante()
									.getCodiceFiscale(), bean
									.getSoggettoPartecipante().getId_stato(),
							false,false,false)){
                       esitiOperazioni.add(new SchedaSpecificaValidationBean(
                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_POSIZIONE)
                                .replace("$2", bean.getSoggettoPartecipante().getCodiceFiscale()), 
                                ValidationBean.VALBEAN_SEV_ERR, elemento,
                                progressivoSchedaCompleta,  0, IdentificativoSchede.getInizioLavori().getNomeScheda(), 
                                cig, getCuiForDummies(schedaA),  schedaInizioLavori.getDatiInizio()
                                .getIdLocale(), getStringValueOfLong(schedaInizioLavori
                                      .getDatiInizio().getIdInizioLavori())));
                       retVal = false;


					}//						throw new Exception(".. no comment ..");
					else{
      					bean.setIdInizioLavori(schedaInizioLavori.getDatiInizio()
      							.getIdInizioLavori());
      					bean.setDataInizioLavori(schedaInizioLavori.getDatiInizio()
      							.getDataInizioLavori());
      					posMan.confirm(bean);
					}
				}
			}
			// aggiornamento dello stato
			statoInizioLavori.setFromXml(false);
			statoInizioLavori.setIdRecord(schedaInizioLavori.getDatiInizio()
					.getIdInizioLavori());
			statoInizioLavori.setDataInizioRecord(schedaInizioLavori
					.getDatiInizio().getDataInizioLavori());
			statoInizioLavori.setAsConfermato(false);
			statoInizioLavori.setStato(StatiScheda.CONFERMATO);
			// -- end --
			if(retVal)
      			esitiOperazioni.add(new SchedaSpecificaValidationBean(
      					commonInfoMessage, infoSeverity, elemento,
      					progressivoSchedaCompleta, 0, IdentificativoSchede
      							.getInizioLavori().getNomeScheda(), cig,
      					getCuiForDummies(schedaA), String
      							.valueOf(schedaInizioLavori.getDatiInizio()
      									.getIdInizioLavori()), schedaInizioLavori
      							.getDatiInizio().getIdLocale()));
			return retVal;
		} catch (Exception e) {
			e.printStackTrace();
			throw inserimentoException;
		}

	}

	/**
	 * Inserisce tutti i dati relativi alla scheda inizio
	 * 
	 * @param schedaStipula
	 * @param schedaA
	 * @param listOfAnapartecipante
	 * @param listOfAnaResponsabile
	 * @return
	 * @throws InserimentoException
	 */
	private boolean insertDatiStipula(StatoScheda statoStipula,
			SchedaStipula schedaStipula, Scheda_A schedaA,
			List<SoggettoPartecipanteBean> listOfAnapartecipante,
			List<SoggettoResponsabileBean> listOfAnaResponsabile, String cig,
			int elemento, int progressivoSchedaCompleta)
			throws InserimentoException {
	   
	   boolean retVal = true;
	   
		InserimentoException inserimentoException = new InserimentoException(
				IdentificativoSchede.getStipula(), cig,
				getCuiForDummies(schedaA), schedaStipula.getStipula()
						.getIdLocale(), getStringValueOfLong(schedaStipula
						.getStipula().getIdStipula()),
				Messaggi.SIMOG_MASSLOADER_193.replace("$1",
						OperazioneScheda.INSERIMENTO).replace("$2",
						IdentificativoSchede.STIPULA));

		try {
			schedaStipula.getStipula().setDataInizioAggiudicazione(
					schedaA.getAggiudicazione().getDataInizioAggiudicazione());
			schedaStipula.getStipula().setIdAggiudicazione(
					schedaA.getAggiudicazione().getIdAggiudicazione());

			PubblicazioneManager pm = new PubblicazioneManager(con, logger);
			pm.insertPubblicazione(schedaStipula.getStipula()
					.getPubblicazione());
			pm.confirm(schedaStipula.getStipula().getPubblicazione());

			StipulaManager im = new StipulaManager(con, logger);
			im.insert(schedaStipula.getStipula(), userName);
			im.confirm(schedaStipula.getStipula(), userName);

			List<ResponsabileBean> responsabili = schedaStipula
					.getResponsabiliInizio();
			if (responsabili != null && responsabili.size() > 0) {
				ResponsabileInizioManager rm = new ResponsabileInizioManager(
						con, logger);
				for (ResponsabileBean bean : responsabili) {
					// gestione anagrafica
					if (!inserisciAnagraficaResponsabili(bean,
							listOfAnaResponsabile, bean
									.getSoggettoResponsabile()
									.getCodiceFiscaleResponsabile(), false)){
                       esitiOperazioni.add(new SchedaSpecificaValidationBean(
                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE)
                                .replace("$2", bean.getSoggettoResponsabile().getCodiceFiscaleResponsabile()), 
                                ValidationBean.VALBEAN_SEV_ERR, elemento,
                                progressivoSchedaCompleta,  0, IdentificativoSchede.getStipula().getNomeScheda(), 
                                cig, getCuiForDummies(schedaA), schedaStipula.getStipula()
                                .getIdLocale(), getStringValueOfLong(schedaStipula
                                      .getStipula().getIdStipula())));
                       retVal = false;

					}
					else{	//throw new Exception(".. no comment ..");

      					bean.setIdScheda(schedaStipula.getStipula().getIdStipula());
      					bean.setDataInizioScheda(schedaStipula.getStipula()
      							.getDataInizioStipula());
      					rm.confirm(bean);
	                }
				}
			}
			List<PosizioneAggiudicatarioBean> posizioni = schedaStipula
					.getPosizioneAggiudicatari();
			if (posizioni != null && posizioni.size() > 0) {
				PosizAggiudManager posMan = new PosizAggiudManager(con, logger);
				for (PosizioneAggiudicatarioBean bean : posizioni) {
					// gestione anagrafica
					if (!inserisciAnagraficaAggiudicatari(bean, null,null,null,
							listOfAnapartecipante, bean
									.getSoggettoPartecipante()
									.getCodiceFiscale(), bean
									.getSoggettoPartecipante().getId_stato(),
							false,false,false)){
                       esitiOperazioni.add(new SchedaSpecificaValidationBean(
                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_POSIZIONE)
                                .replace("$2", bean.getSoggettoPartecipante().getCodiceFiscale()), 
                                ValidationBean.VALBEAN_SEV_ERR, elemento,
                                progressivoSchedaCompleta,  0, IdentificativoSchede.getStipula().getNomeScheda(), 
                                cig, getCuiForDummies(schedaA),  schedaStipula.getStipula()
                                .getIdLocale(), getStringValueOfLong(schedaStipula
                                      .getStipula().getIdStipula())));
                       retVal = false;


					} //					throw new Exception(".. no comment ..");
					else{
      					bean.setIdInizioLavori(schedaStipula.getStipula()
      							.getIdStipula());
      					bean.setDataInizioLavori(schedaStipula.getStipula()
      							.getDataInizioStipula());
      					posMan.confirm(bean);
					}
				}
			}
			// aggiornamento dello stato
			statoStipula.setFromXml(false);
			statoStipula.setIdRecord(schedaStipula.getStipula().getIdStipula());
			statoStipula.setDataInizioRecord(schedaStipula.getStipula()
					.getDataInizioStipula());
			statoStipula.setAsConfermato(false);
			statoStipula.setStato(StatiScheda.CONFERMATO);
			// -- end --
			if(retVal)
      			esitiOperazioni.add(new SchedaSpecificaValidationBean(
      					commonInfoMessage, infoSeverity, elemento,
      					progressivoSchedaCompleta, 0, IdentificativoSchede
      							.getStipula().getNomeScheda(), cig,
      					getCuiForDummies(schedaA), String.valueOf(schedaStipula
      							.getStipula().getIdStipula()), schedaStipula
      							.getStipula().getIdLocale()));
			return retVal;
		} catch (Exception e) {
			e.printStackTrace();
			throw inserimentoException;
		}

	}

	/**
	 * @param schedaAvanzamentiBean
	 * @param schedaA
	 * @return
	 * @throws InserimentoException
	 */
	private boolean insertDatiAvanzamenti(
			ArrayList<StatoScheda> listOfStatiAvanzamenti,
			SchedaAvanzamento schedaAvanzamentiBean, Scheda_A schedaA,
			String cig, int elemento, int progressivoSchedaCompleta)
			throws InserimentoException {

		AvanzamentoManager am = new AvanzamentoManager(con, logger);
		List<AvanzamentoBean> la = schedaAvanzamentiBean.getAvanzamenti();

		if (listOfStatiAvanzamenti.size() == la.size()) {
			logger.debug("All OK");
		} else {
			logger.debug("All KO");
		}

		int i = 0;
		for (AvanzamentoBean avb : la) {
			InserimentoException inserimentoException = new InserimentoException(
					IdentificativoSchede.getAvanzamenti(), cig,
					getCuiForDummies(schedaA), avb.getIdLocale(),
					getStringValueOfLong(avb.getIdAvanzamento()),
					Messaggi.SIMOG_MASSLOADER_193.replace("$1",
							OperazioneScheda.INSERIMENTO).replace("$2",
							IdentificativoSchede.STATO_AVANZAMENTO));
			try {

				avb.setDataInizioAggiudicazione(schedaA.getAggiudicazione()
						.getDataInizioAggiudicazione());
				avb.setIdAggiudicazione(schedaA.getAggiudicazione()
						.getIdAggiudicazione());
				am.insert(avb, userName);
				am.confirm(avb, userName);

				// XX-X: CONTROLLA aggiornamento dello stato
				listOfStatiAvanzamenti.get(i).setFromXml(false);
				listOfStatiAvanzamenti.get(i).setIdRecord(
						avb.getIdAvanzamento());
				listOfStatiAvanzamenti.get(i).setDataInizioRecord(
						avb.getDataInizioAvanzamento());
				listOfStatiAvanzamenti.get(i).setAsConfermato(false);
				listOfStatiAvanzamenti.get(i).setStato(StatiScheda.CONFERMATO);
				// -- end --

				esitiOperazioni.add(new SchedaSpecificaValidationBean(
						commonInfoMessage, infoSeverity, elemento,
						progressivoSchedaCompleta, i, IdentificativoSchede
								.getAvanzamenti().getNomeScheda(), cig,
						getCuiForDummies(schedaA), String.valueOf(avb
								.getIdAvanzamento()), avb.getIdLocale()));
			} catch (Exception e) {
				e.printStackTrace();
				throw inserimentoException;
			}
			i++;
		}
		return true;

	}

	/**
	 * @param schedaConclusioneBean
	 * @param schedaA
	 * @return
	 * @throws InserimentoException
	 */
	private boolean insertDatiConclusione(StatoScheda statoConclusione,
			SchedaConclusione schedaConclusioneBean, Scheda_A schedaA,
			String cig, int elemento, int progressivoSchedaCompleta)
			throws InserimentoException {
		InserimentoException inserimentoException = new InserimentoException(
				IdentificativoSchede.getConclusione(), cig,
				getCuiForDummies(schedaA), schedaConclusioneBean
						.getConclusione().getIdLocale(),
				getStringValueOfLong(schedaConclusioneBean.getConclusione()
						.getIdUltim()), Messaggi.SIMOG_MASSLOADER_193.replace(
						"$1", OperazioneScheda.INSERIMENTO).replace("$2",
						IdentificativoSchede.FINE_LAVORI));
		try {

			ConclusioniManager cm = new ConclusioniManager(con, logger);
			schedaConclusioneBean.getConclusione().setDataInizioAggiudicazione(
					schedaA.getAggiudicazione().getDataInizioAggiudicazione());
			schedaConclusioneBean.getConclusione().setIdAggiudicazione(
					schedaA.getAggiudicazione().getIdAggiudicazione());
			cm.insert(schedaConclusioneBean.getConclusione(), userName);
			cm.confirm(schedaConclusioneBean.getConclusione(), userName);

			// XX-X: CONTROLLA aggiornamento dello stato
			statoConclusione.setFromXml(false);
			statoConclusione.setIdRecord(schedaConclusioneBean.getConclusione()
					.getIdUltim());
			statoConclusione.setDataInizioRecord(schedaConclusioneBean
					.getConclusione().getDataIniUltim());
			statoConclusione.setAsConfermato(false);
			statoConclusione.setStato(StatiScheda.CONFERMATO);
			// -- end --
			esitiOperazioni.add(new SchedaSpecificaValidationBean(
					commonInfoMessage, infoSeverity, elemento,
					progressivoSchedaCompleta, 0, IdentificativoSchede
							.getConclusione().getNomeScheda(), cig,
					getCuiForDummies(schedaA), String
							.valueOf(schedaConclusioneBean.getConclusione()
									.getIdUltim()), schedaConclusioneBean
							.getConclusione().getIdLocale()));

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw inserimentoException;
		}
	}

	/**
	 * @param schedaCollaudoBean
	 * @param schedaA
	 * @return
	 * @throws InserimentoException
	 */
	private boolean insertDatiCollaudo(StatoScheda statoCollaudo,
			SchedaCollaudo schedaCollaudoBean, Scheda_A schedaA,
			List<SoggettoResponsabileBean> listOfAnaResponsabile, String cig,
			int elemento, int progressivoSchedaCompleta)
			throws InserimentoException {
		InserimentoException inserimentoException = new InserimentoException(
				IdentificativoSchede.getCollaudo(), cig,
				getCuiForDummies(schedaA), schedaCollaudoBean.getCollaudo()
						.getIdLocale(), getStringValueOfLong(schedaCollaudoBean
						.getCollaudo().getIdCollaudo()),
				Messaggi.SIMOG_MASSLOADER_193.replace("$1",
						OperazioneScheda.INSERIMENTO).replace("$2",
						IdentificativoSchede.COLLAUDO));
		
		boolean retVal = true;
		try {

			CollaudoManager cm = new CollaudoManager(con, logger);
			schedaCollaudoBean.getCollaudo().setDataIniAggiudicazione(
					schedaA.getAggiudicazione().getDataInizioAggiudicazione());
			schedaCollaudoBean.getCollaudo().setIdAggiudicazione(
					schedaA.getAggiudicazione().getIdAggiudicazione());
			cm.insert(schedaCollaudoBean.getCollaudo(), userName);
			cm.confirm(schedaCollaudoBean.getCollaudo(), userName);
			if (schedaCollaudoBean.getIncaricati() != null
					&& !schedaCollaudoBean.getIncaricati().isEmpty()) {
				ResponsabileCollManager rcm = new ResponsabileCollManager(con,
						logger);
				for (ResponsabileBean rb : schedaCollaudoBean.getIncaricati()) {
					rb.setIdScheda(schedaCollaudoBean.getCollaudo()
							.getIdCollaudo());
					rb.setDataInizioScheda(schedaCollaudoBean.getCollaudo()
							.getDataIniColl());
					if (!inserisciAnagraficaResponsabili(rb,
							listOfAnaResponsabile, rb.getSoggettoResponsabile()
									.getCodiceFiscaleResponsabile(), false)){
                       esitiOperazioni.add(new SchedaSpecificaValidationBean(
                             Messaggi.SIMOG_MASSLOADER_172.replace("$1", Costanti.SOGGETTO_RESPONSABILE)
                                .replace("$2", rb.getSoggettoResponsabile().getCodiceFiscaleResponsabile()), 
                                ValidationBean.VALBEAN_SEV_ERR, elemento,
                                progressivoSchedaCompleta,  0, IdentificativoSchede.getCollaudo().getNomeScheda(), 
                                cig, getCuiForDummies(schedaA), schedaCollaudoBean.getCollaudo()
                                .getIdLocale(), getStringValueOfLong(schedaCollaudoBean
                                      .getCollaudo().getIdCollaudo())));
                       retVal = false;

					}//						throw new Exception(".. no comment ..");
					else
					   rcm.confirm(rb);

				}
			}
			statoCollaudo.setFromXml(false);
			statoCollaudo.setIdRecord(schedaCollaudoBean.getCollaudo()
					.getIdCollaudo());
			statoCollaudo.setDataInizioRecord(schedaCollaudoBean.getCollaudo()
					.getDataIniColl());
			statoCollaudo.setAsConfermato(false);
			statoCollaudo.setStato(StatiScheda.CONFERMATO);
			if(retVal)
      			esitiOperazioni.add(new SchedaSpecificaValidationBean(
      					commonInfoMessage, infoSeverity, elemento,
      					progressivoSchedaCompleta, 0, IdentificativoSchede
      							.getCollaudo().getNomeScheda(), cig,
      					getCuiForDummies(schedaA), String
      							.valueOf(schedaCollaudoBean.getCollaudo()
      									.getIdCollaudo()), schedaCollaudoBean
      							.getCollaudo().getIdLocale()));
			return retVal;
		} catch (Exception e) {
			e.printStackTrace();
			throw inserimentoException;
		}
	}

	/**
	 * @param schedaRitardiBean
	 * @param schedaA
	 * @param listOfAnaResponsabile
	 * @return
	 * @throws InserimentoException
	 */
	private boolean insertDatiRitardi(
			ArrayList<StatoScheda> listOfStatiRitardi,
			SchedaR129 schedaRitardiBean, Scheda_A schedaA, String cig,
			int elemento, int progressivoSchedaCompleta)
			throws InserimentoException {
		R129Manager rm = new R129Manager(con, logger);
		List<R129Bean> lr = schedaRitardiBean.getR129s();
		int i = 0;
		for (R129Bean rb : lr) {
			InserimentoException inserimentoException = new InserimentoException(
					IdentificativoSchede.getRitardo(), cig,
					getCuiForDummies(schedaA), rb.getIdLocale(),
					getStringValueOfLong(rb.getIdRecord()),
					Messaggi.SIMOG_MASSLOADER_193.replace("$1",
							OperazioneScheda.INSERIMENTO).replace("$2",
							IdentificativoSchede.IPOTESI_RECESSO));
			try {

				rb.setDataInizioAggiudicazione(schedaA.getAggiudicazione()
						.getDataInizioAggiudicazione());
				rb.setIdAggiudicazione(schedaA.getAggiudicazione()
						.getIdAggiudicazione());
				rm.insert(rb, userName);
				rm.confirm(rb, userName);

				// XX-X: CONTROLLA aggiornamento dello stato
				listOfStatiRitardi.get(i).setFromXml(false);
				listOfStatiRitardi.get(i).setIdRecord(rb.getIdRecord());
				listOfStatiRitardi.get(i).setDataInizioRecord(
						rb.getDataInizioRecord());
				listOfStatiRitardi.get(i).setAsConfermato(false);
				listOfStatiRitardi.get(i).setStato(StatiScheda.CONFERMATO);
				// -- end --

				esitiOperazioni.add(new SchedaSpecificaValidationBean(
						commonInfoMessage, infoSeverity, elemento,
						progressivoSchedaCompleta, i, IdentificativoSchede
								.getRitardo().getNomeScheda(), cig,
						getCuiForDummies(schedaA), String.valueOf(rb
								.getIdRecord()), rb.getIdLocale()));
			} catch (Exception e) {
				e.printStackTrace();
				throw inserimentoException;
			}
			i++;
		}
		return true;

	}

	/**
	 * @param schedaAccordiBean
	 * @param schedaA
	 * @return
	 * @throws InserimentoException
	 */
	private boolean insertDatiAccordi(
			ArrayList<StatoScheda> listOfStatiAccordi,
			SchedaAccordo schedaAccordiBean, Scheda_A schedaA, String cig,
			int elemento, int progressivoSchedaCompleta)
			throws InserimentoException {

		AccordoManager am = new AccordoManager(con, logger);
		List<AccordoBean> accordi = schedaAccordiBean.getAccordi();
		int i = 0;
		for (AccordoBean acc : accordi) {
			InserimentoException inserimentoException = new InserimentoException(
					IdentificativoSchede.getAccordi(), cig,
					getCuiForDummies(schedaA), acc.getIdLocale(),
					getStringValueOfLong(acc.getIdAccordo()),
					Messaggi.SIMOG_MASSLOADER_193.replace("$1",
							OperazioneScheda.INSERIMENTO).replace("$2",
							IdentificativoSchede.ACCORDO_BONARIO));
			try {

				acc.setDataInizioAggiudicazione(schedaA.getAggiudicazione()
						.getDataInizioAggiudicazione());
				acc.setIdAggiudicazione(schedaA.getAggiudicazione()
						.getIdAggiudicazione());
				am.insert(acc, userName);
				am.confirm(acc, userName);

				// XX-X: CONTROLLA aggiornamento dello stato
				listOfStatiAccordi.get(i).setFromXml(false);
				listOfStatiAccordi.get(i).setIdRecord(acc.getIdAccordo());
				listOfStatiAccordi.get(i).setDataInizioRecord(
						acc.getDataInizioAccordo());
				listOfStatiAccordi.get(i).setAsConfermato(false);
				listOfStatiAccordi.get(i).setStato(StatiScheda.CONFERMATO);
				// -- end --

				esitiOperazioni.add(new SchedaSpecificaValidationBean(
						commonInfoMessage, infoSeverity, elemento,
						progressivoSchedaCompleta, i, IdentificativoSchede
								.getAccordi().getNomeScheda(), cig,
						getCuiForDummies(schedaA), String.valueOf(acc
								.getIdAccordo()), acc.getIdLocale()));
			} catch (Exception e) {
				e.printStackTrace();
				throw inserimentoException;
			}
			i++;
		}
		return true;

	}

	/**
	 * @param schedaSospensioniBean
	 * @param schedaA
	 * @return
	 * @throws InserimentoException
	 */
	private boolean insertDatiSospensioni(
			ArrayList<StatoScheda> listOfStatiSospensioni,
			SchedaSospensione schedaSospensioniBean, Scheda_A schedaA,
			String cig, int elemento, int progressivoSchedaCompleta)
			throws InserimentoException {

		SospensioniManager sm = new SospensioniManager(con, logger);
		List<SospensioniBean> ls = schedaSospensioniBean.getSospensioni();
		int i = 0;
		for (SospensioniBean sb : ls) {
			InserimentoException inserimentoException = new InserimentoException(
					IdentificativoSchede.getSospensioni(), cig,
					getCuiForDummies(schedaA), sb.getIdLocale(),
					getStringValueOfLong(sb.getIdSospensione()),
					Messaggi.SIMOG_MASSLOADER_193.replace("$1",
							OperazioneScheda.INSERIMENTO).replace("$2",
							IdentificativoSchede.SOSPENSIONE));
			try {

				sb.setDataInizioAggiudicazione(schedaA.getAggiudicazione()
						.getDataInizioAggiudicazione());
				sb.setIdAggiudicazione(schedaA.getAggiudicazione()
						.getIdAggiudicazione());
				sm.insert(sb, userName);
				sm.confirm(sb, userName);

				// XX-X: CONTROLLA aggiornamento dello stato
				listOfStatiSospensioni.get(i).setFromXml(false);
				listOfStatiSospensioni.get(i)
						.setIdRecord(sb.getIdSospensione());
				listOfStatiSospensioni.get(i).setDataInizioRecord(
						sb.getDataInizioSosp());
				listOfStatiSospensioni.get(i).setAsConfermato(false);
				listOfStatiSospensioni.get(i).setStato(StatiScheda.CONFERMATO);
				// -- end --
				esitiOperazioni.add(new SchedaSpecificaValidationBean(
						commonInfoMessage, infoSeverity, elemento,
						progressivoSchedaCompleta, i, IdentificativoSchede
								.getSospensioni().getNomeScheda(), cig,
						getCuiForDummies(schedaA), String.valueOf(sb
								.getIdSospensione()), sb.getIdLocale()));
			} catch (Exception e) {
				e.printStackTrace();
				throw inserimentoException;
			}
			i++;
		}
		return true;

	}

	/**
	 * @param schedaVariantiBean
	 * @param schedaA
	 * @return
	 * @throws InserimentoException
	 */
	private boolean insertDatiVarianti(
			ArrayList<StatoScheda> listOfStatiVarianti,
			SchedaVariante schedaVariantiBean, Scheda_A schedaA, String cig,
			int elemento, int progressivoSchedaCompleta)
			throws InserimentoException {

		VarianteManager vm = new VarianteManager(con, logger);
		EventiMotiviVariantiManager emvm = new EventiMotiviVariantiManager(con,
				logger);
		List<VarianteBean> lv = schedaVariantiBean.getVarianti();
		int i = 0;
		for (VarianteBean vb : lv) {
			InserimentoException inserimentoException = new InserimentoException(
					IdentificativoSchede.getVarianti(), cig,
					getCuiForDummies(schedaA), vb.getIdLocale(),
					getStringValueOfLong(vb.getIdVariante()),
					Messaggi.SIMOG_MASSLOADER_193.replace("$1",
							OperazioneScheda.INSERIMENTO).replace("$2",
							IdentificativoSchede.VARIANTE));
			try {

				vb.setDataInizioAggiudicazione(schedaA.getAggiudicazione()
						.getDataInizioAggiudicazione());
				vb.setIdAggiudicazione(schedaA.getAggiudicazione()
						.getIdAggiudicazione());
				vm.insert(vb, userName);
				vm.confirm(vb, userName);
				List<EventiMotiviVariantiBean> le = vb.getEmvb();
				for (EventiMotiviVariantiBean eb : le) {
					eb.setIdVariante(vb.getIdVariante());
					eb.setDataIniVariante(vb.getDataInizioVar());
					// emvm.save(eb, username);
					emvm.confirm(eb, userName);
				}
				// XX-X: CONTROLLA aggiornamento dello stato
				listOfStatiVarianti.get(i).setFromXml(false);
				listOfStatiVarianti.get(i).setIdRecord(vb.getIdVariante());
				listOfStatiVarianti.get(i).setDataInizioRecord(
						vb.getDataInizioVar());
				listOfStatiVarianti.get(i).setAsConfermato(false);
				listOfStatiVarianti.get(i).setStato(StatiScheda.CONFERMATO);
				// -- end --

				esitiOperazioni.add(new SchedaSpecificaValidationBean(
						commonInfoMessage, infoSeverity, elemento,
						progressivoSchedaCompleta, i, IdentificativoSchede
								.getVarianti().getNomeScheda(), cig,
						getCuiForDummies(schedaA), String.valueOf(vb
								.getIdVariante()), vb.getIdLocale()));
			} catch (Exception e) {
				e.printStackTrace();
				throw inserimentoException;
			}
			i++;
		}
		return true;

	}

	/**
	 * @param schedaSubbappaltiBean
	 * @param schedaA
	 * @param cig
	 * @return
	 * @throws InserimentoException
	 */
	private boolean insertDatiSubbappalti(
			ArrayList<StatoScheda> listOfStatiSubAppalti,
			SchedaSubAppalti schedaSubbappaltiBean, Scheda_A schedaA,
			List<SoggettoPartecipanteBean> listOfAnapartecipante,
			String cig, int elemento, int progressivoSchedaCompleta)
			throws InserimentoException {

		boolean retVal=true;
		
		SubappaltiManager sm = new SubappaltiManager(con, logger);
		List<SubappaltiBean> ls = schedaSubbappaltiBean.getSubAppalti();
		int i = 0;
		for (SubappaltiBean sb : ls) {
			InserimentoException inserimentoException = new InserimentoException(
					IdentificativoSchede.getSubAppalti(), cig,
					getCuiForDummies(schedaA), sb.getIdLocale(),
					getStringValueOfLong(sb.getIdRecord()),
					Messaggi.SIMOG_MASSLOADER_193.replace("$1",
							OperazioneScheda.INSERIMENTO).replace("$2",
							IdentificativoSchede.SUBAPPALTO));
			try {

				sb.setDataInizioAggiudicazione(schedaA.getAggiudicazione()
						.getDataInizioAggiudicazione());
				sb.setIdAggiudicazione(schedaA.getAggiudicazione()
						.getIdAggiudicazione());
				sm.insert(sb, userName);
				sm.confirm(sb, userName);

				

				//TICKET ALM - 3.04.3
				SubappaltiManager subManager = new SubappaltiManager(con,
						this.logger);
				for (SubappaltatoreBean subBean : sb.getSubappaltatori()) {
					subBean.setIdSubappalto(sb.getIdRecord());
					subBean.setDataInizioSubappalto(sb.getDataInizioRecord());
					
					if (!inserisciAnagraficaAggiudicatariSub(subBean,
							listOfAnapartecipante, subBean.getSoggettoPartecipante().getCodiceFiscale())){
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
				//FINE TICKET ALM - 3.04.3
				
				// XX-X: CONTROLLA aggiornamento dello stato
				listOfStatiSubAppalti.get(i).setFromXml(false);
				listOfStatiSubAppalti.get(i).setIdRecord(sb.getIdRecord());
				listOfStatiSubAppalti.get(i).setDataInizioRecord(
						sb.getDataInizioRecord());
				listOfStatiSubAppalti.get(i).setAsConfermato(false);
				listOfStatiSubAppalti.get(i).setStato(StatiScheda.CONFERMATO);
				// -- end --

				esitiOperazioni.add(new SchedaSpecificaValidationBean(
						commonInfoMessage, infoSeverity, elemento,
						progressivoSchedaCompleta, i, IdentificativoSchede
								.getSubAppalti().getNomeScheda(), cig,
						getCuiForDummies(schedaA), String.valueOf(sb
								.getIdRecord()), sb.getIdLocale()));
			} catch (Exception e) {
				e.printStackTrace();
				throw inserimentoException;
			}
			i++;
		}
		return retVal;

	}
}
