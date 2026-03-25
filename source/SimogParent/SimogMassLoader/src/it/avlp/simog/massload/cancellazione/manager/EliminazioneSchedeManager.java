package it.avlp.simog.massload.cancellazione.manager;

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
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.IdsScheda;
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
import it.avlp.simog.massload.cancellazione.business.EliminazioneAggiudicazione;
import it.avlp.simog.massload.cancellazione.business.EliminazioneCollaudo;
import it.avlp.simog.massload.cancellazione.business.EliminazioneInfoComuni;
import it.avlp.simog.massload.cancellazione.business.EliminazioneInizioLavori;
import it.avlp.simog.massload.cancellazione.business.EliminazioneStipula;
import it.avlp.simog.massload.cancellazione.business.EliminazioneVarianti;
import it.avlp.simog.massload.cancellazione.eccezione.EliminazioneFallitaException;
import it.avlp.simog.massload.cancellazione.report.ReportCancellazioneScheda;
import it.avlp.simog.massload.cancellazione.report.ReportCancellazioneSchede;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextPackage.NotFound;

public class EliminazioneSchedeManager {

	private Connection con;
	private Logger logger;
	private String cfUtente;

	/**
	 * @param con
	 * @param logger
	 * @param cfUtente
	 */
	public EliminazioneSchedeManager(Connection con, Logger logger, String cfUtente){
		this.con = con;
		this.logger = logger;
		this.cfUtente = cfUtente;
	}
	/**
	 * Cancella tutto a partire dai dati comuni, quindi (0,n) aggiudicazioni con relativi figli,
	 * 
	 * @param CIG
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ReportCancellazioneSchede cancellaTuttoByCIG(ArrayList<SituazioneSchedeAttuale> listOfSituazioni) throws SQLException,EliminazioneFallitaException, NotFound{//,Exception{
		ReportCancellazioneSchede report = new ReportCancellazioneSchede();
		String CIG = null;
	
		if(listOfSituazioni != null && listOfSituazioni.size() > 0){
			
			CIG = listOfSituazioni.get(0).getStatoDatiComuni().getCig();
			logger.info("Mi accingo a cancellare il CIG["+CIG+"]");
			String idLocale = listOfSituazioni.get(0).getStatoDatiComuni().getIdLocale(); 
			String idRecord =  String.valueOf(listOfSituazioni.get(0).getStatoDatiComuni().getIdRecord());

			for(SituazioneSchedeAttuale situazioneCorrente : listOfSituazioni){
				// cancella tutte le schede by id aggiudicazione	
				if(situazioneCorrente.getStatoAggiudicazioneSottotipo().isEsistenteDb()){
					ReportCancellazioneSchede reportCorrente = this.cancellaDaAggiudicazione(situazioneCorrente);
					report.addAllReports(reportCorrente.getReports());
				}
			}
			
			// cancellazione dati comuni per ultimo
			IdsScheda riferimentiScheda = this.valorizzaIdsScheda(CIG, null, idLocale, idRecord, IdentificativoSchede.getDatiComuni());
			// cancellazione dei dati comuni e aggiuta del report
			report.addAllReports(this.cancellaScheda(riferimentiScheda).getReports());
		}
//		else{
//			// l'unica situazione dove dovrebbe entrare qui dentro e' quando si e' gia cancellato i dati comuni
//			throw new Exception("Non sono stati trovati dati comuni per il CIG: "+CIG);
//		}
		
		return report;
	}
	/**
	 * Cancella tutto a partire da un'aggiudicazione identificata da cui
	 * 
	 * @param CUI
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ReportCancellazioneSchede cancellaDaAggiudicazioneByCUI(SituazioneSchedeAttuale situazioneCorrente) throws SQLException,EliminazioneFallitaException,NotFound{//,Exception{

		ReportCancellazioneSchede report = null;

		if(situazioneCorrente != null)
			report = this.cancellaDaAggiudicazione(situazioneCorrente);
//		else
//			throw new Exception("L'oggetto \"SituazioneSchedeAttuale\" risulta nullo NON risulta possibile per tanto eseguire l'operazione di cancellazione");
		
		return report;
	}

	/**
	 * Implementa la cancellazione.
	 * Nel caso delle schede con "dati correlati" viene usato lo strato di business che si occupa di
	 * cancellare anche tutte le dipendenze.
	 * Nel caso delle schede multiple memorizzo tutti i riferimenti per ogni elemento di scheda multipla
	 * 
	 * @param report
	 * @param aggiudicazioneCorrente
	 * @throws SQLException
	 * @throws Exception
	 */
	private ReportCancellazioneSchede cancellaDaAggiudicazione(SituazioneSchedeAttuale situazioneCorrente) throws SQLException,EliminazioneFallitaException, NotFound{//,Exception{
		
		ReportCancellazioneSchede report = new ReportCancellazioneSchede();
		
		String cig = situazioneCorrente.getStatoDatiComuni().getCig() ;
		String cui = situazioneCorrente.getStatoAggiudicazioneSottotipo().getCui() ;
		IdentificativoSchede schedaAggiudicazione = null;
		if(situazioneCorrente.findTipoAggiudicazione().equals(TipoAggiudicazione.S))
			 schedaAggiudicazione = IdentificativoSchede.getSottosoglia();
		else if(situazioneCorrente.findTipoAggiudicazione().equals(TipoAggiudicazione.E))
			 schedaAggiudicazione = IdentificativoSchede.getEscluso();
		else if(situazioneCorrente.findTipoAggiudicazione().equals(TipoAggiudicazione.Q))
			 schedaAggiudicazione = IdentificativoSchede.getAdesione();
		else 
			 schedaAggiudicazione = IdentificativoSchede.getAggiudicazione();
		// cancella tutte le schede by id aggiudicazione
		
		if(situazioneCorrente.getStatoAccordi()!= null && situazioneCorrente.getStatoAccordi().size() > 0){
			AccordoManager accordoManager = new AccordoManager(con, logger);
			ArrayList<StatoScheda> listOfAccordi = situazioneCorrente.getStatoAccordi();
			for(StatoScheda statoCorrente : listOfAccordi){
//				logger.debug("Sto cancellado l'accordo["+statoCorrente.getIdRecord()+","+statoCorrente.getDataInizioRecord()+"]");
				IdsScheda rifSchedaCorrente = this.valorizzaIdsScheda(cig, cui, statoCorrente.getIdLocale(), String.valueOf(statoCorrente.getIdRecord()), IdentificativoSchede.getAccordi());
				boolean esitoAccordi = accordoManager.annulla(statoCorrente.getIdRecord(), cfUtente);
				this.setReportScheda(report, rifSchedaCorrente, esitoAccordi);
				if(!esitoAccordi) throw new EliminazioneFallitaException("E' Fallita la cancellazione degli Accordi",rifSchedaCorrente);
			}
		}
		if(situazioneCorrente.getStatoAvanzamento()!= null && situazioneCorrente.getStatoAvanzamento().size() > 0){
			AvanzamentoManager avanzamentoManager = new AvanzamentoManager(con, logger);
			ArrayList<StatoScheda> listOfAvanzamenti = situazioneCorrente.getStatoAvanzamento();
			for(StatoScheda statoCorrente : listOfAvanzamenti){
//				logger.debug("Sto cancellado l'avanzamento["+statoCorrente.getIdRecord()+","+statoCorrente.getDataInizioRecord()+"]");
				IdsScheda rifSchedaCorrente = this.valorizzaIdsScheda(cig, cui, statoCorrente.getIdLocale(), String.valueOf(statoCorrente.getIdRecord()), IdentificativoSchede.getAvanzamenti());
				boolean esitoAvanzamento = avanzamentoManager.annulla(statoCorrente.getIdRecord(), cfUtente);
				setReportScheda(report, rifSchedaCorrente, esitoAvanzamento);			
				if(!esitoAvanzamento) throw new EliminazioneFallitaException("E' Fallita la cancellazione degli Avanzamenti",rifSchedaCorrente);

			}
		}
		if(situazioneCorrente.getStatoCollaudo().isEsistenteDb()){
//			logger.debug("Sto cancellado il collaudo["+situazioneCorrente.getStatoCollaudo().getIdRecord()+","+situazioneCorrente.getStatoCollaudo().getDataInizioRecord()+"]");
			// valorizzo i riferimenti per il feedback
			IdsScheda rifSchedaCorrente = this.valorizzaIdsScheda(cig, cui, situazioneCorrente.getStatoCollaudo().getIdLocale(), String.valueOf(situazioneCorrente.getStatoCollaudo().getIdRecord()), IdentificativoSchede.getCollaudo());
			EliminazioneCollaudo collaudoBusinessDelete = new EliminazioneCollaudo(con,logger,cfUtente);
			boolean esitoCollaudo = collaudoBusinessDelete.annulla(String.valueOf(situazioneCorrente.getStatoCollaudo().getIdRecord()));
			// memorizzo riferimenti per il feedback
			setReportScheda(report, rifSchedaCorrente, esitoCollaudo);			
			if(!esitoCollaudo) throw new EliminazioneFallitaException("E' Fallita la cancellazione del Collaudo",rifSchedaCorrente);
			
		}
		if(situazioneCorrente.getStatoConclusione().isEsistenteDb()){
//			logger.debug("Sto cancellado la conclusione["+situazioneCorrente.getStatoConclusione().getIdRecord()+","+situazioneCorrente.getStatoConclusione().getDataInizioRecord()+"]");
			ConclusioniManager conclusioniManager = new ConclusioniManager(con, logger);
			IdsScheda rifSchedaCorrente = this.valorizzaIdsScheda(cig, cui, situazioneCorrente.getStatoConclusione().getIdLocale(), String.valueOf(situazioneCorrente.getStatoConclusione().getIdRecord()), IdentificativoSchede.getConclusione());
			boolean esitoConclusione = conclusioniManager.annulla(situazioneCorrente.getStatoConclusione().getIdRecord(), cfUtente);
			setReportScheda(report, rifSchedaCorrente, esitoConclusione);			
			if(!esitoConclusione) throw new EliminazioneFallitaException("E' Fallita la cancellazione della Conclusione",rifSchedaCorrente);
			
		}
		if(situazioneCorrente.getStatoInizioLavori().isEsistenteDb()){
//			logger.debug("Sto cancellado l'inizio lavori["+situazioneCorrente.getStatoInizioLavori().getIdRecord()+","+situazioneCorrente.getStatoInizioLavori().getDataInizioRecord()+"]");
			IdsScheda rifSchedaCorrente = this.valorizzaIdsScheda(cig, cui, situazioneCorrente.getStatoInizioLavori().getIdLocale(), String.valueOf(situazioneCorrente.getStatoInizioLavori().getIdRecord()), IdentificativoSchede.getInizioLavori());
			EliminazioneInizioLavori inizioLavoriBusinessDelete = new EliminazioneInizioLavori(con,logger,cfUtente);
			boolean esitoInizio = inizioLavoriBusinessDelete.annulla(String.valueOf(situazioneCorrente.getStatoInizioLavori().getIdRecord()));
			setReportScheda(report, rifSchedaCorrente, esitoInizio);			
			if(!esitoInizio) throw new EliminazioneFallitaException("E' Fallita la cancellazione di Inizio Lavori",rifSchedaCorrente);
			
		}
		
		if(situazioneCorrente.getStatoStipula().isEsistenteDb()){
//			logger.debug("Sto cancellado l'inizio lavori["+situazioneCorrente.getStatoInizioLavori().getIdRecord()+","+situazioneCorrente.getStatoInizioLavori().getDataInizioRecord()+"]");
			IdsScheda rifSchedaCorrente = this.valorizzaIdsScheda(cig, cui, situazioneCorrente.getStatoStipula().getIdLocale(), String.valueOf(situazioneCorrente.getStatoStipula().getIdRecord()), IdentificativoSchede.getStipula());
			EliminazioneStipula stipulaBusinessDelete = new EliminazioneStipula(con,logger,cfUtente);
			boolean esitoInizio = stipulaBusinessDelete.annulla(String.valueOf(situazioneCorrente.getStatoStipula().getIdRecord()));
			setReportScheda(report, rifSchedaCorrente, esitoInizio);			
			if(!esitoInizio) throw new EliminazioneFallitaException("E' Fallita la cancellazione di Stipula",rifSchedaCorrente);
			
		}
		if(situazioneCorrente.getStatoRitardo()!= null && situazioneCorrente.getStatoRitardo().size() > 0){
			R129Manager r129Manager = new R129Manager(con, logger);
			ArrayList<StatoScheda> listOfR129 = situazioneCorrente.getStatoRitardo();
			for(StatoScheda statoCorrente : listOfR129){
//				logger.debug("Sto cancellado il ritardo["+statoCorrente.getIdRecord()+","+statoCorrente.getDataInizioRecord()+"]");
				IdsScheda rifSchedaCorrente = this.valorizzaIdsScheda(cig, cui, statoCorrente.getIdLocale(), String.valueOf(statoCorrente.getIdRecord()), IdentificativoSchede.getRitardo());
				boolean esitoRitardo = r129Manager.annulla(statoCorrente.getIdRecord(), cfUtente);
				setReportScheda(report, rifSchedaCorrente, esitoRitardo);			
				if(!esitoRitardo) throw new EliminazioneFallitaException("E' Fallita la cancellazione dei Ritardi",rifSchedaCorrente);

			}
			
		}
		if(situazioneCorrente.getStatoSospensioni()!= null && situazioneCorrente.getStatoSospensioni().size() > 0){
			SospensioniManager sospensioniManager = new SospensioniManager(con, logger);
			ArrayList<StatoScheda> listOfSospensioni = situazioneCorrente.getStatoSospensioni();
			for(StatoScheda statoCorrente : listOfSospensioni){
//				logger.debug("Sto cancellado la sospensione["+statoCorrente.getIdRecord()+","+statoCorrente.getDataInizioRecord()+"]");
				IdsScheda rifSchedaCorrente = this.valorizzaIdsScheda(cig, cui, statoCorrente.getIdLocale(), String.valueOf(statoCorrente.getIdRecord()), IdentificativoSchede.getSospensioni());
				boolean esitoSospensioni = sospensioniManager.annulla(statoCorrente.getIdRecord(), cfUtente);
				setReportScheda(report, rifSchedaCorrente, esitoSospensioni);			
				if(!esitoSospensioni) throw new EliminazioneFallitaException("E' Fallita la cancellazione delle Sospensioni",rifSchedaCorrente);

			}
			
		}
		if(situazioneCorrente.getStatoSubAppalti()!= null && situazioneCorrente.getStatoSubAppalti().size() > 0){
			SubappaltiManager subappaltiManager = new SubappaltiManager(con, logger);
			ArrayList<StatoScheda> listOfSubAppalti = situazioneCorrente.getStatoSubAppalti();
			for(StatoScheda statoCorrente  : listOfSubAppalti){
//				logger.debug("Sto cancellado il subappalto["+statoCorrente.getIdRecord()+","+statoCorrente.getDataInizioRecord()+"]");
				IdsScheda rifSchedaCorrente = this.valorizzaIdsScheda(cig, cui, statoCorrente.getIdLocale(), String.valueOf(statoCorrente.getIdRecord()), IdentificativoSchede.getSubAppalti());
				boolean esitoSubAppalti = subappaltiManager.annulla(statoCorrente.getIdRecord(), cfUtente);
				setReportScheda(report, rifSchedaCorrente, esitoSubAppalti);			
				if(!esitoSubAppalti) throw new EliminazioneFallitaException("E' Fallita la cancellazione dei SubAppalti",rifSchedaCorrente);

			}
			
		}
		if(situazioneCorrente.getStatoVarianti()!= null && situazioneCorrente.getStatoVarianti().size() > 0){
			ArrayList<StatoScheda> listOfVarianti = situazioneCorrente.getStatoVarianti();
			for(StatoScheda statoCorrente : listOfVarianti){
//				logger.debug("Sto cancellado la variante["+statoCorrente.getIdRecord()+","+statoCorrente.getDataInizioRecord()+"]");
				IdsScheda rifSchedaCorrente = this.valorizzaIdsScheda(cig, cui, statoCorrente.getIdLocale(), String.valueOf(statoCorrente.getIdRecord()), IdentificativoSchede.getVarianti());
				EliminazioneVarianti varianteBusinessDelete = new EliminazioneVarianti(con,logger,cfUtente);
				boolean esitoVarianti = varianteBusinessDelete.annulla(String.valueOf(statoCorrente.getIdRecord()));
				setReportScheda(report, rifSchedaCorrente, esitoVarianti);			
				if(!esitoVarianti) throw new EliminazioneFallitaException("E' Fallita la cancellazione delle Varianti",rifSchedaCorrente);

			}
			
		}
		IdsScheda riferimentiScheda = this.valorizzaIdsScheda(
				cig, 
				cui, 
				situazioneCorrente.getStatoAggiudicazioneSottotipo().getIdLocale(), 
				String.valueOf(situazioneCorrente.getStatoAggiudicazioneSottotipo().getIdRecord()), 
				schedaAggiudicazione);
		// cancellazione aggiudicazione e aggiuta del report
		report.addAllReports(this.cancellaScheda(riferimentiScheda).getReports());
		return report;
		
	} 
	/**
	 * Effettua la cancellazione della scheda di cui identificativo e aggiunge l'esito al report,
	 * ad uso interno
	 * 
	 * @param report
	 * @param identificativo
	 * @param CIG
	 * @param CUI
	 * @param idScheda
	 * @return
	 * @throws NotFound
	 * @throws SQLException
	 * @throws Exception
	 */
	private ReportCancellazioneSchede cancellaScheda(IdsScheda riferimentiScheda) throws NotFound,SQLException{ //,Exception{
		ReportCancellazioneSchede reportSchede = new ReportCancellazioneSchede();
		ReportCancellazioneScheda report = new ReportCancellazioneScheda();
		
		String idScheda = riferimentiScheda.getIdScheda();
		
		report.setSchede(riferimentiScheda);
		report.setEsitoOperazione(false);
		
		reportSchede.addReport(report);
		
		int indiceScheda = riferimentiScheda.getIdentificativo().getIndiceScheda();
		
		boolean esito;
		
		switch (indiceScheda) {
		
		case IdentificativoSchede.INDICE_DATI_COMUNI:		

			EliminazioneInfoComuni eliminazioneInfoBusiness = new EliminazioneInfoComuni(con, logger, cfUtente);
			esito = eliminazioneInfoBusiness.annulla(idScheda);
			break;
			
		case IdentificativoSchede.INDICE_AGGIUDICAZIONE:
			

			EliminazioneAggiudicazione eliminazioneAggBusiness = new EliminazioneAggiudicazione(con, logger, cfUtente);
			esito = eliminazioneAggBusiness.annulla(idScheda);
			break;
			
		case IdentificativoSchede.INDICE_SOTTOSOGLIA:

			EliminazioneAggiudicazione eliminazioneSSBusiness = new EliminazioneAggiudicazione(con, logger, cfUtente);
			esito = eliminazioneSSBusiness.annulla(idScheda);
			break;
		
		case IdentificativoSchede.INDICE_ESCLUSO:

			EliminazioneAggiudicazione eliminazioneEsBusiness = new EliminazioneAggiudicazione(con, logger, cfUtente);
			esito = eliminazioneEsBusiness.annulla(idScheda);
			break;
			
		case IdentificativoSchede.INDICE_ADESIONE:

			EliminazioneAggiudicazione eliminazioneAdBusiness = new EliminazioneAggiudicazione(con, logger, cfUtente);
			esito = eliminazioneAdBusiness.annulla(idScheda);
			break;
			
			
		case IdentificativoSchede.INDICE_ACCORDO_BONARIO:
			
			AccordoManager accordoManager = new AccordoManager(con, logger);
			esito = accordoManager.annulla(Long.parseLong(idScheda), cfUtente);
			break;
			
		case IdentificativoSchede.INDICE_STATO_AVANZAMENTO:
			
			AvanzamentoManager manager = new AvanzamentoManager(con, logger);
			esito = manager.annulla(Long.parseLong(idScheda), cfUtente);
			break;
			
		case IdentificativoSchede.INDICE_COLLAUDO:
			
			EliminazioneCollaudo eliminazioneCollBusiness = new EliminazioneCollaudo(con, logger, cfUtente);
			esito = eliminazioneCollBusiness.annulla(idScheda);
			break;
			
		case IdentificativoSchede.INDICE_FINE_LAVORI:
			
			ConclusioniManager conclusioniManager = new ConclusioniManager(con, logger);
			esito = conclusioniManager.annulla(Long.parseLong(idScheda), cfUtente);
			break;
			
		case IdentificativoSchede.INDICE_FASE_INIZIALE:

			EliminazioneInizioLavori eliminazioneIniBusiness = new EliminazioneInizioLavori(con, logger, cfUtente);
			esito = eliminazioneIniBusiness.annulla(idScheda);
			break;
		
		case IdentificativoSchede.INDICE_STIPULA:

			EliminazioneStipula eliminazioneStiBusiness = new EliminazioneStipula(con, logger, cfUtente);
			esito = eliminazioneStiBusiness.annulla(idScheda);
			break;
			
		case IdentificativoSchede.INDICE_IPOTESI_RECESSO:
			
			R129Manager r129Manager = new R129Manager(con, logger);
			esito = r129Manager.annulla(Long.parseLong(idScheda), cfUtente);
			break;
			
		case IdentificativoSchede.INDICE_SOSPENSIONE:
			
			SospensioniManager sospensioniManager = new SospensioniManager(con, logger);
			esito = sospensioniManager.annulla(Long.parseLong(idScheda), cfUtente);
			break;
			
		case IdentificativoSchede.INDICE_SUBAPPALTO:
			
			SubappaltiManager subappaltiManager = new SubappaltiManager(con, logger);
			esito = subappaltiManager.annulla(Long.parseLong(idScheda), cfUtente);
			break;
			
		case IdentificativoSchede.INDICE_VARIANTE:

			EliminazioneVarianti eliminazioneVarBusiness = new EliminazioneVarianti(con, logger, cfUtente);
			esito = eliminazioneVarBusiness.annulla(idScheda);
			break;
			
		default :
			throw new NotFound();
		}
		report.setEsitoOperazione(esito);
		return reportSchede;
	}
	/**
	 * Effettua la cancellazione della scheda di cui identificativo e ritorna il report dell'operazione
	 * su una singola scheda
	 * 
	 * @param identificativo
	 * @param CIG
	 * @param CUI
	 * @param idLocale
	 * @param idScheda
	 * @return
	 * @throws NotFound
	 * @throws SQLException
	 * @throws Exception
	 */
	public ReportCancellazioneScheda cancellaScheda(IdentificativoSchede identificativo, 
													String CIG, 
													String CUI,
													String idAggiudicazione, 
													String idLocale, 
													String idScheda, 
													boolean byIdSimog)throws NotFound,SQLException{
		
		ReportCancellazioneScheda report = new ReportCancellazioneScheda();
		
		IdsScheda identificativoScheda = this.valorizzaIdsScheda(CIG, CUI, idLocale, idScheda, identificativo);
		
		report.setSchede(identificativoScheda);
		
		int indiceScheda = identificativo.getIndiceScheda();
		
		boolean esito;
		
		switch (indiceScheda) {
		
		case IdentificativoSchede.INDICE_DATI_COMUNI:		

			EliminazioneInfoComuni eliminazioneInfoBusiness = new EliminazioneInfoComuni(con, logger, cfUtente);
			if(byIdSimog) esito = eliminazioneInfoBusiness.annulla(idScheda);
			else esito = eliminazioneInfoBusiness.annulla(idLocale, CIG);
			break;
			
		case IdentificativoSchede.INDICE_AGGIUDICAZIONE:

			EliminazioneAggiudicazione eliminazioneAggBusiness = new EliminazioneAggiudicazione(con, logger, cfUtente);
			if(byIdSimog) esito = eliminazioneAggBusiness.annulla(idScheda);
			else esito = eliminazioneAggBusiness.annulla(idLocale, CUI);
			break;
		
		case IdentificativoSchede.INDICE_SOTTOSOGLIA:

			EliminazioneAggiudicazione eliminazioneSSBusiness = new EliminazioneAggiudicazione(con, logger, cfUtente);
			if(byIdSimog) esito = eliminazioneSSBusiness.annulla(idScheda);
			else esito = eliminazioneSSBusiness.annulla(idLocale, CUI);
			break;
			
		case IdentificativoSchede.INDICE_ESCLUSO:

			EliminazioneAggiudicazione eliminazioneEsBusiness = new EliminazioneAggiudicazione(con, logger, cfUtente);
			if(byIdSimog) esito = eliminazioneEsBusiness.annulla(idScheda);
			else esito = eliminazioneEsBusiness.annulla(idLocale, CUI);
			break;
			
		//gm aggiunto per adesione
		case IdentificativoSchede.INDICE_ADESIONE:

			EliminazioneAggiudicazione eliminazioneAdBusiness = new EliminazioneAggiudicazione(con, logger, cfUtente);
			if(byIdSimog) esito = eliminazioneAdBusiness.annulla(idScheda);
			else esito = eliminazioneAdBusiness.annulla(idLocale, CUI);
			break;
			
		case IdentificativoSchede.INDICE_ACCORDO_BONARIO:
			
			AccordoManager accordoManager = new AccordoManager(con, logger);
			if(byIdSimog) esito = accordoManager.annulla(Long.parseLong(idScheda), cfUtente);
			else esito = accordoManager.annulla(idLocale, idAggiudicazione, cfUtente);
			break;
			
		case IdentificativoSchede.INDICE_STATO_AVANZAMENTO:
			
			AvanzamentoManager manager = new AvanzamentoManager(con, logger);
			if(byIdSimog) esito = manager.annulla(Long.parseLong(idScheda), cfUtente);
			else esito = manager.annulla(idLocale, idAggiudicazione, cfUtente);
			break;
			
		case IdentificativoSchede.INDICE_COLLAUDO:

			EliminazioneCollaudo eliminazioneCollBusiness = new EliminazioneCollaudo(con, logger, cfUtente);
			if(byIdSimog) esito = eliminazioneCollBusiness.annulla(idScheda);
			else esito = eliminazioneCollBusiness.annulla(idLocale, idAggiudicazione);
			break;
			
		case IdentificativoSchede.INDICE_FINE_LAVORI:
			
			ConclusioniManager conclusioniManager = new ConclusioniManager(con, logger);
			if(byIdSimog) esito = conclusioniManager.annulla(Long.parseLong(idScheda), cfUtente);
			else esito = conclusioniManager.annulla(idLocale, idAggiudicazione, cfUtente);
			break;
			
		case IdentificativoSchede.INDICE_FASE_INIZIALE:

			EliminazioneInizioLavori eliminazioneIniBusiness = new EliminazioneInizioLavori(con, logger, cfUtente);
			if(byIdSimog) esito = eliminazioneIniBusiness.annulla(idScheda);
			else esito = eliminazioneIniBusiness.annulla(idLocale, idAggiudicazione);
			break;
			
			
		case IdentificativoSchede.INDICE_STIPULA:

			EliminazioneStipula eliminazioneStiBusiness = new EliminazioneStipula(con, logger, cfUtente);
			if(byIdSimog) esito = eliminazioneStiBusiness.annulla(idScheda);
			else esito = eliminazioneStiBusiness.annulla(idLocale, idAggiudicazione);
			break;
			
		case IdentificativoSchede.INDICE_IPOTESI_RECESSO:
			
			R129Manager r129Manager = new R129Manager(con, logger);
			if(byIdSimog) esito = r129Manager.annulla(Long.parseLong(idScheda), cfUtente);
			else esito = r129Manager.annulla(idLocale, idAggiudicazione, cfUtente);
			break;
			
		case IdentificativoSchede.INDICE_SOSPENSIONE:
			
			SospensioniManager sospensioniManager = new SospensioniManager(con, logger);
			if(byIdSimog) esito = sospensioniManager.annulla(Long.parseLong(idScheda), cfUtente);
			else esito = sospensioniManager.annulla(idLocale, idAggiudicazione, cfUtente);
			break;
			
		case IdentificativoSchede.INDICE_SUBAPPALTO:
			
			SubappaltiManager subappaltiManager = new SubappaltiManager(con, logger);
			if(byIdSimog) esito = subappaltiManager.annulla(Long.parseLong(idScheda), cfUtente);
			else esito = subappaltiManager.annulla(idLocale, idAggiudicazione, cfUtente);
			break;
			
		case IdentificativoSchede.INDICE_VARIANTE:

			EliminazioneVarianti eliminazioneVarBusiness = new EliminazioneVarianti(con, logger, cfUtente);
			if(byIdSimog) esito = eliminazioneVarBusiness.annulla(idScheda);
			else esito = eliminazioneVarBusiness.annulla(idLocale, idAggiudicazione);
			break;
			
		default :
			throw new NotFound();
		}
		report.setEsitoOperazione(esito);
		return report;
	}
	
	/**
	 * Metodo che si occupa di controllare la relazione cui -> (nomescheda,idsimog,idlocale)
	 * Se la relazione non e' rispettata allora ritorna false;
	 * (1) - ho come rif idSimog -> carico tramite cui aggiudicazioni, carico tramite id la scheda in oggetto se gli idAggiudicazione sono uguali true altrimenti false
	 * (2) - ho come rif idLocale -> carico tramite cui aggiudicazioni, carico tramite idLocale + idAggiudicazione la scheda in oggetto se ritorna null non e' valida
	 * 
	 * @param cui
	 * @param idScheda
	 * @param idLocale
	 * @param identificativo
	 * @param byIdSimog
	 * @return
	 * @throws NotFound
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean controllaAfferenzaCUIConScheda(String cui, String idScheda, String idLocale, IdentificativoSchede identificativo, boolean byIdSimog) throws NotFound,SQLException{ //,Exception{
		
		int indiceScheda = identificativo.getIndiceScheda();
		boolean esito = true;
		AggiudicazioniManager aggiudicazioneManager = new AggiudicazioniManager(con, logger);
		AggiudicazioneBean  aggiudicazioneBean = aggiudicazioneManager.getAggiudicazioneByProgAndCui(cui, false);
		
		if(aggiudicazioneBean == null || aggiudicazioneBean.getIdAggiudicazione() == 0) return false;
		
		String idAggiudicazioneString = String.valueOf(aggiudicazioneBean.getIdAggiudicazione());
		long idSchedaLong = 0;
		if(idScheda != null && !"".equals(idScheda)){
			idSchedaLong = Long.parseLong(idScheda);
		}
		long idAggiudicazione = aggiudicazioneBean.getIdAggiudicazione();
		switch (indiceScheda) {
		
		case IdentificativoSchede.INDICE_DATI_COMUNI:		

			if(byIdSimog) esito = idSchedaLong == aggiudicazioneBean.getIdInfo();
			else{ 
				InfoComuniManager infoComuniManager = new InfoComuniManager(con, logger);
				InfoComuniBean infobean = infoComuniManager.load(aggiudicazioneBean.getIdInfo(), aggiudicazioneBean.getDataInizioInfo());
				esito = idLocale == null ? false : idLocale.equals(infobean.getIdLocale());	
			}
			break;
			
		case IdentificativoSchede.INDICE_AGGIUDICAZIONE:
			if(byIdSimog) esito = Long.parseLong(idScheda) == idAggiudicazione;
			else esito = idLocale == null ? false : idLocale.equals(aggiudicazioneBean.getIdLocale());
			
			break;
			
		case IdentificativoSchede.INDICE_SOTTOSOGLIA:
			if(byIdSimog) esito = Long.parseLong(idScheda) == idAggiudicazione;
			else esito = idLocale == null ? false : idLocale.equals(aggiudicazioneBean.getIdLocale());
			
			break;
			
		case IdentificativoSchede.INDICE_ESCLUSO:
			if(byIdSimog) esito = Long.parseLong(idScheda) == idAggiudicazione;
			else esito = idLocale == null ? false : idLocale.equals(aggiudicazioneBean.getIdLocale());
			
			break;
		
		//gm aggiunto per adesione
		case IdentificativoSchede.INDICE_ADESIONE:
			if(byIdSimog) esito = Long.parseLong(idScheda) == idAggiudicazione;
			else esito = idLocale == null ? false : idLocale.equals(aggiudicazioneBean.getIdLocale());
			
			break;
			
		case IdentificativoSchede.INDICE_ACCORDO_BONARIO:
			AccordoManager accordoManager = new AccordoManager(con, logger);
			if(byIdSimog){
				AccordoBean accordoBean = accordoManager.loadByIdSimog(idSchedaLong);
				// se il bean e' nullo o vuoto false, altrimenti controlla che gli idAggiudicazione siano uguali
				esito = accordoBean != null && accordoBean.getIdAccordo() > 0 ? accordoBean.getIdAggiudicazione() == idAggiudicazione : false;
			}else{
				esito = idLocale == null ? false : idLocale.equals(accordoManager.loadByIdLocale(idLocale, idAggiudicazioneString).getIdLocale());
			}
			break;
			
		case IdentificativoSchede.INDICE_STATO_AVANZAMENTO:
			AvanzamentoManager manager = new AvanzamentoManager(con, logger);
			if(byIdSimog){
				AvanzamentoBean avanzamentoBean = manager.loadByIdSimog(idSchedaLong);
				esito = avanzamentoBean != null && avanzamentoBean.getIdAvanzamento() > 0 ? avanzamentoBean.getIdAggiudicazione() == idAggiudicazione : false;
			}else{
				esito = idLocale == null ? false : idLocale.equals(manager.loadByIdLocale(idLocale, idAggiudicazioneString).getIdLocale());
			}
			break;
			
		case IdentificativoSchede.INDICE_COLLAUDO:
			CollaudoManager collaudoManager = new CollaudoManager(con, logger);
			if(byIdSimog){
				CollaudoBean collaudoBean = collaudoManager.loadByIdSimog(idSchedaLong);
				esito = collaudoBean != null && collaudoBean.getIdCollaudo() > 0 ? collaudoBean.getIdAggiudicazione() == idAggiudicazione: false;
			}else{
				esito = idLocale == null ? false : idLocale.equals(collaudoManager.loadByIdLocale(idLocale, idAggiudicazioneString).getIdLocale());
			}
			break;
			
		case IdentificativoSchede.INDICE_FINE_LAVORI:
			ConclusioniManager conclusioniManager = new ConclusioniManager(con, logger);
			if(byIdSimog){
				ConclusioneBean conclusioneBean = conclusioniManager.loadByIdSimog(idSchedaLong);
				esito = conclusioneBean != null && conclusioneBean.getIdUltim() > 0 ? conclusioneBean.getIdAggiudicazione() == idAggiudicazione: false;
			}else{
				esito = idLocale == null ? false : idLocale.equals(conclusioniManager.loadByIdLocale(idLocale, idAggiudicazioneString).getIdLocale());
			}
			break;
			
		case IdentificativoSchede.INDICE_FASE_INIZIALE:
			InizioLavoriManager inizioLavoriManager = new InizioLavoriManager(con, logger);
			if(byIdSimog){
				InizioLavoriBean inizioBean = inizioLavoriManager.loadByIdSimog(idSchedaLong);
				esito = inizioBean != null && inizioBean.getIdInizioLavori() > 0 ? inizioBean.getIdAggiudicazione() == idAggiudicazione : false;
			}else{
				esito = idLocale == null ? false : idLocale.equals(inizioLavoriManager.loadByIdLocale(idLocale, idAggiudicazioneString).getIdLocale());
			}
			break;
			
			
		case IdentificativoSchede.INDICE_STIPULA:
			StipulaManager stipulaManager = new StipulaManager(con, logger);
			if(byIdSimog){
				StipulaBean inizioBean = stipulaManager.loadByIdSimog(idSchedaLong);
				esito = inizioBean != null && inizioBean.getIdStipula() > 0 ? inizioBean.getIdAggiudicazione() == idAggiudicazione : false;
			}else{
				esito = idLocale == null ? false : idLocale.equals(stipulaManager.loadByIdLocale(idLocale, idAggiudicazioneString).getIdLocale());
			}
			break;
			
		case IdentificativoSchede.INDICE_IPOTESI_RECESSO:
			R129Manager r129Manager = new R129Manager(con, logger);
			if(byIdSimog){
				R129Bean rBean = r129Manager.loadByIdSimog(idSchedaLong);
				esito = rBean != null && rBean.getIdRecord()>0 ? rBean.getIdAggiudicazione() == idAggiudicazione : false;
			}else{
				esito = idLocale == null ? false : idLocale.equals(r129Manager.loadByIdLocale(idLocale, idAggiudicazioneString).getIdLocale());
			}
			break;
			
		case IdentificativoSchede.INDICE_SOSPENSIONE:
			SospensioniManager sospensioniManager = new SospensioniManager(con, logger);
			if(byIdSimog){
				SospensioniBean sospensioniBean = sospensioniManager.loadByIdSimog(idSchedaLong);
				esito = sospensioniBean != null && sospensioniBean.getIdSospensione() > 0 ? sospensioniBean.getIdAggiudicazione() == idAggiudicazione : false;
			}else{
				esito = idLocale == null ? false : idLocale.equals(sospensioniManager.loadByIdLocale(idLocale, idAggiudicazioneString).getIdLocale());
			}
			break;
			
		case IdentificativoSchede.INDICE_SUBAPPALTO:
			SubappaltiManager subappaltiManager = new SubappaltiManager(con, logger);
			if(byIdSimog){
				SubappaltiBean subABean = subappaltiManager.loadByIdSimog(idSchedaLong);
				esito =  subABean != null && subABean.getIdRecord() > 0 ? subABean.getIdAggiudicazione() == idAggiudicazione : false;
			}else{
				esito = idLocale == null ? false : idLocale.equals(subappaltiManager.loadByIdLocale(idLocale, idAggiudicazioneString).getIdLocale());
			}
			break;
			
		case IdentificativoSchede.INDICE_VARIANTE:
			VarianteManager varianteManager = new VarianteManager(con, logger);
			if(byIdSimog){
				VarianteBean varianteBean = varianteManager.loadByIdSimog(idSchedaLong);
				esito = varianteBean != null && varianteBean.getIdVariante() > 0 ? varianteBean.getIdAggiudicazione() == idAggiudicazione : false;
			}else{
				esito = idLocale == null ? false : idLocale.equals(varianteManager.loadByIdLocale(idLocale, idAggiudicazioneString).getIdLocale());
			}
			break;
			
		default :
			throw new NotFound();
		}
		return esito;

	}

	/**
	 * @param report
	 * @param identificativo
	 * @param esito
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 */
	private void setReportScheda(ReportCancellazioneSchede report, IdsScheda riferimentiScheda, boolean esito){
		ReportCancellazioneScheda reportScheda = new ReportCancellazioneScheda();
		reportScheda.setSchede(riferimentiScheda);
		reportScheda.setEsitoOperazione(esito);
		report.addReport(reportScheda);
	}	
	/**
	 * Serve a caricare l'oggetto dal quale potro ricavare gli id che mi servono per il feedback
	 * 
	 * @param cig
	 * @param cui
	 * @param idLocale
	 * @param idScheda
	 * @param identificativo
	 * @return
	 */
	public IdsScheda valorizzaIdsScheda(String cig, String cui, String idLocale, String idScheda, IdentificativoSchede identificativo){
		IdsScheda identificativoScheda = new IdsScheda();
		identificativoScheda.setCig(cig);	
		identificativoScheda.setCui(cui);
		identificativoScheda.setIdentificativo(identificativo);
		identificativoScheda.setIdScheda(idScheda);
		identificativoScheda.setIdLocale(idLocale);
		return identificativoScheda;
	}
}
