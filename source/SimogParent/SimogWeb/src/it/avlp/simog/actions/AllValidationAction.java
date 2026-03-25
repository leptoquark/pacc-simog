package it.avlp.simog.actions;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import it.avlp.simog.actions.accordo.AccordoAction;
import it.avlp.simog.actions.aggiudicazione.Scheda_A_Action;
import it.avlp.simog.actions.avanzamento.AvanzamentoAction;
import it.avlp.simog.actions.collaudo.CollaudoAction;
import it.avlp.simog.actions.conclusione.ConclusioniAction;
import it.avlp.simog.actions.inizio.PosizioneAggiudicataroAction;
import it.avlp.simog.actions.inizio.ResponsabileInizioAction;
import it.avlp.simog.actions.r129.R129Action;
import it.avlp.simog.actions.sospensioni.SospensioniAction;
import it.avlp.simog.actions.subappalti.SubappaltiAction;
import it.avlp.simog.actions.variante.VarianteAction;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.ValidationBean;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.beans.accordi.SchedaAccordo;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.Scheda_A;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.beans.avanzamento.SchedaAvanzamento;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.beans.collaudo.SchedaCollaudo;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.beans.conclusione.SchedaConclusione;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.beans.inizio.SchedaInizioLavori;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.beans.r129.SchedaR129;
import it.avlp.simog.beans.sospensioni.SchedaSospensione;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.beans.subappalti.SchedaSubAppalti;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.beans.variante.SchedaVariante;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServletAccordo;
import it.avlp.simog.common.servlet.ParametriServletAvanzamento;
import it.avlp.simog.common.servlet.ParametriServletCollaudo;
import it.avlp.simog.common.servlet.ParametriServletConclusioni;
import it.avlp.simog.common.servlet.ParametriServletR129;
import it.avlp.simog.common.servlet.ParametriServletSospensioni;
import it.avlp.simog.common.servlet.ParametriServletSubappalti;
import it.avlp.simog.common.servlet.ParametriServletVariante;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.validatore.SimogValidator;
import it.avlp.simog.validatore.factory.ValidatorFactory;
import it.mef.serviziCUP.ElaborazioniCUPClient;

public class AllValidationAction extends BaseAction {

	public static String CLAZZ = "AllValidationAction";

	private InfoGaraBean infoGara = null;
	private InfoComuniBean infoComuniBean = null;
	private AggiudicazioneBean aggiudicazioneBean = null;
	private InizioLavoriBean inizioLavoriBean = null;
	private SimogProperties configuration;

	public AllValidationAction(InfoGaraBean infoGara, Connection activeConnection, Logger logger,
			SimogProperties config) {
		super(activeConnection, logger);
		this.infoGara = infoGara;
		this.configuration = config;
	}

	/**
	 * Validazione di tutte le schede relative ad una aggiudicazione
	 * 
	 * @param idAggiudicazione
	 * @param dataInzioAggiudicazione
	 * @return HashMap<String, Boolean> report con i risultati delle validazioni
	 *         scheda per scheda
	 */
	public HashMap<String, Boolean> validationReport(Long idAggiudicazione, Timestamp dataInzioAggiudicazione)
			throws ActionException {
		HashMap<String, Boolean> report = new HashMap<String, Boolean>();

		try {

			// carico in precedenza le schede maggiormente utilizzate in modo da caricarle
			// una volta per tutte
			this.infoComuniBean = _getInfoComuniBean(); // non varia ad ogni chiamata di validationReport
			this.aggiudicazioneBean = this.bsa.getAggiudicazione(idAggiudicazione, dataInzioAggiudicazione);
			// FIX-ME: VL: utilizza la sospensioni action per caricare inizio lavori ?
			this.inizioLavoriBean = (new SospensioniAction(connection, logger)).getInizioLavori(idAggiudicazione,
					dataInzioAggiudicazione);

			// XXX un eventuale eccezzione fa saltare le altre validazioni
			report.put(IdentificativoSchede.TAB_AGGIUDICAZIONE,
					validaSchedaA(idAggiudicazione, dataInzioAggiudicazione));
			report.put(IdentificativoSchede.TAB_INIZIO_LAVORI,
					validaInzioLavori(idAggiudicazione, dataInzioAggiudicazione));
			report.put(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI,
					validaConclusioni(idAggiudicazione, dataInzioAggiudicazione));
			report.put(ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO,
					validaCollaudo(idAggiudicazione, dataInzioAggiudicazione));
			report.put(ParametriServletAvanzamento.TAB_AVANZAMENTO,
					validaAvanzamenti(idAggiudicazione, dataInzioAggiudicazione));
			report.put(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI,
					validaSospensioni(idAggiudicazione, dataInzioAggiudicazione));
			report.put(ParametriServletR129.TAB_SCHEDA_R129, validaRitardi(idAggiudicazione, dataInzioAggiudicazione));
			report.put(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI,
					validaSubappalti(idAggiudicazione, dataInzioAggiudicazione));
			report.put(ParametriServletVariante.TAB_SCHEDA_VARIANTE,
					validaVarianti(idAggiudicazione, dataInzioAggiudicazione));
			report.put(ParametriServletAccordo.TAB_SCHEDA_ACCORDO,
					validaAccordi(idAggiudicazione, dataInzioAggiudicazione));

			// siamo sicuri che dopo un "validationReport" non ci sono piu' riferimenti a
			// schede "obsolete"
			this.infoComuniBean = null;
			this.aggiudicazioneBean = null;
			this.inizioLavoriBean = null;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ActionException(e);
		}

		return report;
	}

	/**
	 * Carica Info comuni solo se non e' gia' stata caricata in precedenza
	 * 
	 * @return
	 */
	private InfoComuniBean _getInfoComuniBean() {
		if (infoComuniBean == null)
			return this.bsa.getInfoComuni(infoGara.getIdInfo(), infoGara.getDataInizioInfo());
		else
			return infoComuniBean;
	}

	/**
	 * Carica un Aggiudicazione solo se non e' gia' stata caricata in precedenza
	 * 
	 * @return
	 */
	private AggiudicazioneBean _getAggiudicazioneBean(Long idAggiudicazione, Timestamp dataInzioAggiudicazione) {
		if (aggiudicazioneBean == null)
			return this.bsa.getAggiudicazione(idAggiudicazione, dataInzioAggiudicazione);
		else
			return aggiudicazioneBean;
	}

	/**
	 * Carica Inzio lavori solo se non e' gia' stato caricato in precedenza
	 * 
	 * @return
	 */
	private InizioLavoriBean _getInizioLavoriBean(Long idAggiudicazione, Timestamp dataInzioAggiudicazione)
			throws ActionException {
		if (inizioLavoriBean == null)
			return (new SospensioniAction(connection, logger)).getInizioLavori(idAggiudicazione,
					dataInzioAggiudicazione);
		else
			return inizioLavoriBean;
	}

	/**
	 * Verifica la validita' della scheda Info comuni con caricamento della scheda
	 * dal db
	 */
//	public boolean validaInfoComuni() throws ActionException, SimogException {
//		infoComuniBean = _getInfoComuniBean();
//		if(infoComuniBean.getIdInfo() > 0) {
//			return validaInfoComuni(infoComuniBean);
//		}
//		return true;
//	}

	/**
	 * Verifica la validita' della scheda Info comuni
	 */
	public boolean validaInfoComuni(InfoComuniBean infoComuniBean) throws SimogException {
		Scheda_A scheda_a = new Scheda_A();
		scheda_a.setInfoComuni(infoComuniBean);
		scheda_a.setInfoGara(infoGara);
		SimogValidator scheda_a_val = ValidatorFactory.getValidator(IdentificativoSchede.TAB_AGGIUDICAZIONE, connection,
				logger);
		return scheda_a_val.valida(scheda_a, IdentificativoSchede.TAB_INFO_COMUNI);
	}

	/**
	 * Verifica la validita' della scheda Aggiudicazione
	 * 
	 * @param idAggiudicazione        Long
	 * @param dataInzioAggiudicazione Timestamp
	 * @return boolean
	 * @throws ActionException
	 * @throws SimogException
	 */
	public boolean validaSchedaA(Long idAggiudicazione, Timestamp dataInzioAggiudicazione)
			throws ActionException, SimogException {
		Scheda_A_Action saAction = new Scheda_A_Action(connection, logger);
		Scheda_A saBean = saAction.load(idAggiudicazione, dataInzioAggiudicazione, infoGara.getTipoEnte(), false,
				infoGara.getIdLotto());

		if (saBean.getAggiudicazione().getIdInfo() > 0) {

			// se è una riaggiudicazione la data di validità è quella della scheda originale
			if (saBean.isRiaggiudicazione()) {
				String cuiOrig = saBean.getAggiudicazione().getCui().substring(2) + "-"
						+ String.valueOf(saBean.getAggiudicazione().getProgCuiRiaggiudicato()).trim();
				AggiudicazioneBean oldAgg = saAction.getAggiudicazioneByProgAndCui(cuiOrig, false);

				saBean.getAggiudicazione().setDataValidatore(oldAgg.getDataInizioAggiudicazione());
			}

			saBean.setInfoComuni(_getInfoComuniBean());
			saBean.setInfoGara(infoGara);

			// PP ricerco il giusto validatore in base alla tipologia di aggiudicazione
			// (sopra, sotto, esclusi, adesione, acc.quadro)
			String tab = IdentificativoSchede.TAB_AGGIUDICAZIONE;
			if (TipoAggiudicazione.E.toString().equals(saBean.getAggiudicazione().getSottotipo().toString()))
				tab = IdentificativoSchede.TAB_ESCLUSI;
			else if (TipoAggiudicazione.S.toString().equals(saBean.getAggiudicazione().getSottotipo().toString()))
				tab = IdentificativoSchede.TAB_SOTTOSOGLIA;
			else if (TipoAggiudicazione.Q.toString().equals(saBean.getAggiudicazione().getSottotipo().toString()))
				tab = IdentificativoSchede.TAB_ADESIONE;

			SimogValidator validator = ValidatorFactory.getValidator(tab, connection, logger);

			if (SimogFlags.is3031_RFWEBGL02Active()
					&& !SimogProperties.getInstance().isCUPLotto(saBean.getInfoGara().getDataCreazioneGara())
					&& SimogProperties.getInstance().isCUPAttivo()) {

				boolean esito = validator.valida(saBean, IdentificativoSchede.TAB_AGGIUDICAZIONE);
				// Per ogni CUP verifico la situazione
				ElaborazioniCUPClient cli = new ElaborazioniCUPClient(configuration, logger);
				Lotto lt = new Lotto();
				lt.setElencoCup(saBean.getElencoCup());
				//MAC CUP 3.04.8 cambiato da validaCupDIPE a validaCupDIPEAgg
				AllValidationBeans eccezDIPE = cli.validaCupDIPEAgg(lt, false);
				if (eccezDIPE != null)
					validator.getEccezioni().add(eccezDIPE);

				// se ci sono errori non salvo
				if (validator.getEccezioni().getAllBySeverity(ValidationBean.VALBEAN_SEV_ERR).getSize() > 0)
					esito = false;
			} else
				return validator.valida(saBean, IdentificativoSchede.TAB_AGGIUDICAZIONE);

		}
		return true;
	}

	/**
	 * Verifica la validita' della scheda Inizio lavori
	 * 
	 * @param idAggiudicazione        Long
	 * @param dataInzioAggiudicazione Timestamp
	 * @return boolean
	 * @throws ActionException
	 * @throws SimogException
	 */
	public boolean validaInzioLavori(Long idAggiudicazione, Timestamp dataInzioAggiudicazione)
			throws ActionException, SimogException {
		ResponsabileInizioAction riAction = new ResponsabileInizioAction(connection, logger);
		PosizioneAggiudicataroAction paAction = new PosizioneAggiudicataroAction(connection, logger);
		// InizioLavoriAction iAction = new InizioLavoriAction(connection, logger);

		SchedaInizioLavori schedaLavori = new SchedaInizioLavori();

		InizioLavoriBean datiLavori = _getInizioLavoriBean(idAggiudicazione, dataInzioAggiudicazione);
		if (datiLavori.getIdInizioLavori() > 0) {

			schedaLavori.setDatiInizio(datiLavori);
			schedaLavori.setInfoComuni(_getInfoComuniBean());
			schedaLavori.setAggiudicazione(_getAggiudicazioneBean(idAggiudicazione, dataInzioAggiudicazione));
			schedaLavori.setResponsabiliInizio(
					riAction.loadMany(datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori(), false));
			schedaLavori.setPosizioneAggiudicatari(
					paAction.loadMany(datiLavori.getIdInizioLavori(), datiLavori.getDataInizioLavori(), false));

			SimogValidator inizioValidator = ValidatorFactory.getValidator(IdentificativoSchede.TAB_INIZIO_LAVORI,
					connection, logger);
			return inizioValidator.valida(schedaLavori, PSBD.SEZIONE_IN);
		}
		return true;
	}

	/**
	 * Verifica la validita' della scheda Conclusione
	 * 
	 * @param idAggiudicazione        Long
	 * @param dataInzioAggiudicazione Timestamp
	 * @return boolean
	 * @throws ActionException
	 * @throws SimogException
	 */
	public boolean validaConclusioni(Long idAggiudicazione, Timestamp dataInzioAggiudicazione)
			throws ActionException, SimogException {
		ConclusioniAction cAction = new ConclusioniAction(connection, logger);
		ConclusioneBean cbean = cAction.load(idAggiudicazione, dataInzioAggiudicazione);
		if (cbean != null) {

			SchedaConclusione schedaConclusione = new SchedaConclusione();
			schedaConclusione.setConclusione(cbean);
			schedaConclusione.setInfoComuni(_getInfoComuniBean());
			schedaConclusione.setAggiudicazione(_getAggiudicazioneBean(idAggiudicazione, dataInzioAggiudicazione));

			SimogValidator validator = ValidatorFactory.getValidator(ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI,
					connection, logger);
			return validator.valida(schedaConclusione, null);
		}
		return true;
	}

	/**
	 * Verifica la validita' della scheda Collaudo
	 * 
	 * @param idAggiudicazione        Long
	 * @param dataInzioAggiudicazione Timestamp
	 * @return boolean
	 * @throws ActionException
	 * @throws SimogException
	 */
	public boolean validaCollaudo(Long idAggiudicazione, Timestamp dataInzioAggiudicazione)
			throws ActionException, SimogException {
		CollaudoAction cAction = new CollaudoAction(connection, logger);
		CollaudoBean bean = cAction.load(idAggiudicazione, dataInzioAggiudicazione);
		if (bean.getIdCollaudo() > 0) {

			SchedaCollaudo schedaCollaudo = new SchedaCollaudo();
			schedaCollaudo.setCollaudo(bean);
			schedaCollaudo.setInfoComuni(_getInfoComuniBean());
			schedaCollaudo.setAggiudicazione(_getAggiudicazioneBean(idAggiudicazione, dataInzioAggiudicazione));
			schedaCollaudo.setAccordiBonario(cAction.getAccordoBonario(idAggiudicazione, dataInzioAggiudicazione));
			schedaCollaudo.setConclusione(cAction.getConclusione(idAggiudicazione, dataInzioAggiudicazione));
			schedaCollaudo.setIncaricati(new ArrayList<ResponsabileBean>()); // XXX UN Come da srvColluado, ma siamo
																				// sicuri che non vanno validati gli
																				// Incaricati?
			// 2.10 aggiunto caricamento per i controlli 13.1.1.2
			schedaCollaudo.setInizioLavori(_getInizioLavoriBean(idAggiudicazione, dataInzioAggiudicazione));
			SimogValidator validator = ValidatorFactory.getValidator(ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO,
					connection, logger);
			return validator.valida(schedaCollaudo, PSBD.SEZIONE_CO);
		}
		return true;
	}

	/**************************************
	 * SCHEDE MULTIPLE
	 ************************************************/
	/*******************************************************************************************************/

	/**
	 * Verifica la validita' della scheda Avanzamenti
	 * 
	 * @param idAggiudicazione        Long
	 * @param dataInzioAggiudicazione Timestamp
	 * @return boolean
	 * @throws ActionException
	 * @throws SimogException
	 */
	public boolean validaAvanzamenti(Long idAggiudicazione, Timestamp dataInzioAggiudicazione)
			throws ActionException, SimogException {
		boolean valido = true;
		AvanzamentoAction avanzamentoAction = new AvanzamentoAction(connection, logger);
		SimogValidator validator = ValidatorFactory.getValidator(ParametriServletAvanzamento.TAB_AVANZAMENTO,
				connection, logger);
		List<AvanzamentoBean> listaAvanzamenti = avanzamentoAction.loadMany(idAggiudicazione, dataInzioAggiudicazione);
		SchedaAvanzamento schedaAvanzamento = new SchedaAvanzamento();
		schedaAvanzamento.setInfoComuni(_getInfoComuniBean());
		schedaAvanzamento.setAggiudicazione(_getAggiudicazioneBean(idAggiudicazione, dataInzioAggiudicazione));
		// 2.10 aggiunto caricamento per i controlli 11.1.1.4,7,9
		VarianteAction varAction = new VarianteAction(connection, logger);
		List<VarianteBean> listaVarianti = varAction.loadAllByAgg(idAggiudicazione, dataInzioAggiudicazione);
		schedaAvanzamento.setVarianti(listaVarianti);
		// 2.10 fine caricamento per i controlli 11.1.1.4,7,9
		for (AvanzamentoBean bean : listaAvanzamenti) {
			schedaAvanzamento.setAvanzamentoFE(bean);
			valido = valido && validator.valida(schedaAvanzamento, null);
		}
		return valido;
	}

	/**
	 * Verifica la validita' della scheda Sospensioni
	 * 
	 * @param idAggiudicazione        Long
	 * @param dataInzioAggiudicazione Timestamp
	 * @return boolean
	 * @throws ActionException
	 * @throws SimogException
	 */
	public boolean validaSospensioni(Long idAggiudicazione, Timestamp dataInzioAggiudicazione)
			throws ActionException, SimogException {
		boolean valido = true;
		SospensioniAction rAction = new SospensioniAction(connection, logger);
		SimogValidator validator = ValidatorFactory.getValidator(ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI,
				connection, logger);
		List<SospensioniBean> listaSospensioni = rAction.loadAllByAgg(idAggiudicazione, dataInzioAggiudicazione);
		SchedaSospensione schedaSospensione = new SchedaSospensione();
		schedaSospensione.setInfoComuni(_getInfoComuniBean());
		schedaSospensione.setAggiudicazione(_getAggiudicazioneBean(idAggiudicazione, dataInzioAggiudicazione));
		schedaSospensione.setInizioLavori(_getInizioLavoriBean(idAggiudicazione, dataInzioAggiudicazione));
		for (SospensioniBean bean : listaSospensioni) {
			schedaSospensione.setSospensioneFE(bean);
			valido = valido && validator.valida(schedaSospensione, null);
		}
		return valido;
	}

	/**
	 * Verifica la validita' della scheda Ipotesi di recesso
	 * 
	 * @param idAggiudicazione        Long
	 * @param dataInzioAggiudicazione Timestamp
	 * @return boolean
	 * @throws ActionException
	 * @throws SimogException
	 */
	public boolean validaRitardi(Long idAggiudicazione, Timestamp dataInzioAggiudicazione)
			throws ActionException, SimogException {
		boolean valido = true;
		R129Action rAction = new R129Action(connection, logger);
		SimogValidator validator = ValidatorFactory.getValidator(ParametriServletR129.TAB_SCHEDA_R129, connection,
				logger);
		List<R129Bean> listaRitardi = rAction.loadAllByAgg(idAggiudicazione, dataInzioAggiudicazione);
		SchedaR129 schedaR129 = new SchedaR129();
		schedaR129.setInfoComuni(_getInfoComuniBean());
		schedaR129.setAggiudicazione(_getAggiudicazioneBean(idAggiudicazione, dataInzioAggiudicazione));
		schedaR129.setInizioLavori(_getInizioLavoriBean(idAggiudicazione, dataInzioAggiudicazione));
		for (R129Bean bean : listaRitardi) {
			schedaR129.setRitardoFE(bean);
			valido = valido && validator.valida(schedaR129, null);
		}
		return valido;
	}

	/**
	 * Verifica la validita' della scheda Subappalti
	 * 
	 * @param idAggiudicazione        Long
	 * @param dataInzioAggiudicazione Timestamp
	 * @return boolean
	 * @throws ActionException
	 * @throws SimogException
	 */
	public boolean validaSubappalti(Long idAggiudicazione, Timestamp dataInzioAggiudicazione)
			throws ActionException, SimogException {
		boolean valido = true;
		SubappaltiAction sAction = new SubappaltiAction(connection, logger);
		SimogValidator validator = ValidatorFactory.getValidator(ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI,
				connection, logger);
		List<SubappaltiBean> listaSubappalti = sAction.loadAllByAgg(idAggiudicazione, dataInzioAggiudicazione);
		SchedaSubAppalti schedaSubappalti = new SchedaSubAppalti();
		schedaSubappalti.setInfoComuni(_getInfoComuniBean());
		schedaSubappalti.setAggiudicazione(_getAggiudicazioneBean(idAggiudicazione, dataInzioAggiudicazione));
		// gm servono anche gli aggiudicatari, per coerenza li carico con la
		// subappaltiAction
		schedaSubappalti.setAggiudicatari(sAction.getAggiudicatari(idAggiudicazione, dataInzioAggiudicazione));
		schedaSubappalti.setInizioLavori(_getInizioLavoriBean(idAggiudicazione, dataInzioAggiudicazione));
		for (SubappaltiBean bean : listaSubappalti) {
			schedaSubappalti.setSubAppaltiFE(bean);
			valido = valido && validator.valida(schedaSubappalti, null);
		}
		return valido;
	}

	/**
	 * Verifica la validita' della scheda Variante
	 * 
	 * @param idAggiudicazione        Long
	 * @param dataInzioAggiudicazione Timestamp
	 * @return boolean
	 * @throws ActionException
	 * @throws SimogException
	 */
	public boolean validaVarianti(Long idAggiudicazione, Timestamp dataInzioAggiudicazione)
			throws ActionException, SimogException {
		boolean valido = true;
		VarianteAction varAction = new VarianteAction(connection, logger);
		SimogValidator validator = ValidatorFactory.getValidator(ParametriServletVariante.TAB_SCHEDA_VARIANTE,
				connection, logger);
		List<VarianteBean> listaVarianti = varAction.loadAllByAgg(idAggiudicazione, dataInzioAggiudicazione);
		SchedaVariante schedaVariante = new SchedaVariante();
		schedaVariante.setInfoComuni(_getInfoComuniBean());
		schedaVariante.setAggiudicazione(_getAggiudicazioneBean(idAggiudicazione, dataInzioAggiudicazione));
		schedaVariante.setInizioLavori(_getInizioLavoriBean(idAggiudicazione, dataInzioAggiudicazione));
		for (VarianteBean bean : listaVarianti) {
			schedaVariante.setVarianteFE(bean);
			valido = valido && validator.valida(schedaVariante, null);
		}
		return valido;
	}

	/**
	 * Verifica la validita' della scheda Accordi bonari
	 * 
	 * @param idAggiudicazione        Long
	 * @param dataInzioAggiudicazione Timestamp
	 * @return boolean
	 * @throws ActionException
	 * @throws SimogException
	 */
	public boolean validaAccordi(Long idAggiudicazione, Timestamp dataInzioAggiudicazione)
			throws ActionException, SimogException {
		boolean valido = true;
		AccordoAction accAction = new AccordoAction(connection, logger);
		SimogValidator validator = ValidatorFactory.getValidator(ParametriServletAccordo.TAB_SCHEDA_ACCORDO, connection,
				logger);
		List<AccordoBean> listaAccordi = accAction.loadAllByAgg(idAggiudicazione, dataInzioAggiudicazione);
		SchedaAccordo schedaAccordo = new SchedaAccordo();
		schedaAccordo.setInfoComuni(_getInfoComuniBean());
		schedaAccordo.setAggiudicazione(_getAggiudicazioneBean(idAggiudicazione, dataInzioAggiudicazione));
		schedaAccordo.setInizioLavori(_getInizioLavoriBean(idAggiudicazione, dataInzioAggiudicazione));
		for (AccordoBean bean : listaAccordi) {
			schedaAccordo.setAccordoFE(bean);
			valido = valido && validator.valida(schedaAccordo, null);
		}
		return valido;
	}

}
