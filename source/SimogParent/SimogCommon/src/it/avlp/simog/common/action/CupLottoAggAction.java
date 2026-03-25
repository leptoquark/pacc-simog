package it.avlp.simog.common.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.avcp.simog.manager.cup.CupLottoAggManager;
import it.avcp.simog.managers.aggiudicazione.AggiudicazioniManager;
import it.avcp.simog.managers.aggiudicazione.MisuraPremialeManager;
import it.avcp.simog.managers.aggiudicazione.MotivoDerogaManager;
import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avlp.simog.beans.AllValidationBeans;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.RichiestaCUP;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.cup.CupLottoAgg;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.exception.ActionException;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.garamanager.lotto.RichiesteCUPManager;
import it.avlp.simog.util.SimogProperties;

public class CupLottoAggAction extends BaseSharedAction {

	public CupLottoAggAction(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}

	private CupLottoAggManager cupLottoAggManager = null;

	public CupLottoAggManager getCupLottoAggManager() {
		if (cupLottoAggManager == null) {
			cupLottoAggManager = new CupLottoAggManager(connection, logger);
		}
		return cupLottoAggManager;
	}

	public static List<CupLottoAggExt> convertCupLottoAggToExt(List<CupLottoAgg> items) {
		List<CupLottoAggExt> extended = new LinkedList<CupLottoAggExt>();
		for (CupLottoAgg item : items) {
			extended.add(new CupLottoAggExt(item));
		}
		return extended;
	}

	public static List<CupLottoAgg> convertExtToCupLottoAgg(List<CupLottoAggExt> items) {
		List<CupLottoAgg> lista = new LinkedList<CupLottoAgg>();
		for (CupLottoAggExt item : items)
			lista.add(new CupLottoAgg(item));
		return lista;
	}

	/**
	 * Restiuisce l'elenco dei CUP di un lotto o di una aggiudicazione
	 * 
	 * @param idLotto
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @return
	 * @throws ActionException
	 */
	public List<CupLottoAggExt> getElencoCup(Long idLotto, Long idAggiudicazione, Timestamp dataInizioAggiudicazione,
			boolean ignoraStato) throws ActionException {
		try {
			List<CupLottoAggExt> listaCupExt = getCupLottoAggManager().getElencoCup(idLotto, idAggiudicazione,
					dataInizioAggiudicazione, ignoraStato);
			settingDatiDIPE(listaCupExt);
			return listaCupExt;
		} catch (SQLException e) {
			logger.error(e);
			throw new ActionException(e);
		}
	}

	/**
	 * Restiuisce l'elenco dei CUP di una aggiudicazione
	 * 
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @return
	 * @throws ActionException
	 */
	public List<CupLottoAggExt> getElencoCup(Long idAggiudicazione, Timestamp dataInizioAggiudicazione,
			boolean ignoraStato) throws ActionException {
		return getElencoCup(-1L, idAggiudicazione, dataInizioAggiudicazione, ignoraStato);
	}

	/**
	 * Restiuisce l'elenco dei CUP di una gara
	 * 
	 * @param idGara
	 * @return
	 * @throws SQLException
	 */
	public List<CupLottoAggExt> getElencoCupGara(Long idGara) throws ActionException {
		try {
			List<CupLottoAggExt> listaCupExt = getCupLottoAggManager().getElencoCupGara(idGara);
			settingDatiDIPE(listaCupExt);
			return listaCupExt;
		} catch (SQLException e) {
			logger.error(e);
			throw new ActionException(e);
		}
	}
	/**
	 * MAC 34162 3.04.8.1
	 * Restituisce l'elenco dei CUP di una gara di lotti non cancellati
	 * 
	 * @param idGara
	 * @return
	 * @throws SQLException
	 */
	public List<CupLottoAggExt> getElencoCupGaraNoLottiCancellati(Long idGara) throws ActionException {
		try {
			List<CupLottoAggExt> listaCupExt = getCupLottoAggManager().getElencoCupGaraNoLottiCancellati(idGara);
			settingDatiDIPE(listaCupExt);
			return listaCupExt;
		} catch (SQLException e) {
			logger.error(e);
			throw new ActionException(e);
		}
	}
	/*FINE MAC 34162*/

	/**
	 * Settaggio dei dati DIPE
	 * 
	 * @param elencoCup
	 */
	public void settingDatiDIPE(List<CupLottoAggExt> elencoCup) {
		RichiesteCUPManager dm = new RichiesteCUPManager(connection, logger);

		for (CupLottoAggExt curr : elencoCup) {
			// verifico se ci sono dati DIPE
			RichiestaCUP datiDIPE = null;
			try {
				datiDIPE = dm.getByCup(curr.getCup());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (datiDIPE != null)
				curr.setDatiDIPE(datiDIPE);
		}
	}

	/**
	 * Conferma i CUP validi (flag ok_utente a SI)
	 * 
	 * @param listaCup
	 * @return
	 */
	public int confirmAllValidCup(List<CupLottoAggExt> listaCup) throws ActionException {
		try {
			return getCupLottoAggManager().confirmValidCup(listaCup);
		} catch (SQLException e) {
			logger.error("Impossibile confermare i CUP validi", e);
			throw new ActionException(e);
		}
	}

	/**
	 * Aggiorna i CUP di una aggiudicazione
	 * 
	 * @param elencoCup
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @return
	 * @throws ActionException
	 */
	public boolean updateElencoCup(String flagCUP, List<CupLottoAggExt> elencoCup, Long idLotto, Long idAggiudicazione,
			Timestamp dataInizioAggiudicazione, boolean confermata) throws ActionException {
		try {
			LottoManager lm = new LottoManager(connection, logger);

			Lotto lotto = new Lotto();
			lotto.setId_Lotto(idLotto);
			lotto.setFLAG_CUP(flagCUP);
			lm.updateFlagCup(lotto);

			if (elencoCup == null)
				return true;

			for (CupLottoAgg item : elencoCup) {
				item.setIdLotto(idLotto);
				item.setIdAggiudicazione(idAggiudicazione);
				item.setDataInizioAgg(dataInizioAggiudicazione);
			}
			return getCupLottoAggManager().updateElencoCup(elencoCup, idAggiudicazione, dataInizioAggiudicazione,
					confermata);
		} catch (SQLException e) {
			logger.error("Impossibile aggiornare i Cup di una aggiudicazione", e);
			throw new ActionException(e);
		}
	}

	/**
	 * Aggiunge ai cup del lotto le informazioni sull'aggiudicazione -
	 * idAggiudicazione - dataInizioAggiudicazione
	 * 
	 * @param idLotto
	 * @param idAggiudicazione
	 * @param dataInizioAgg
	 * @return
	 * @throws SQLException
	 */
	public int completaDatiAggCup(Long idLotto, Long idAggiudicazione, Timestamp dataInizioAgg) throws ActionException {
		try {
			return getCupLottoAggManager().completaDatiAggCup(idLotto, idAggiudicazione, dataInizioAgg);
		} catch (SQLException e) {
			logger.error("Impossibile completare i dati dei Cup", e);
			throw new ActionException(e);
		}
	}

	/**************************************************************************************************
	 * Gestisce la richiesta di annullamento
	 * 
	 * @param bean        RichiestaAnnullamento
	 * @param datavecchia Timesatmp
	 * @return booelan
	 * @throws ActionException
	 */
	public boolean richiediAnnullamento(RichiestaAnnullamento bean, Timestamp datavecchia) throws ActionException {

		if (!SimogFlags.is3031_RFWEBGL02Active())
			return true;

		try {
			InfoGaraBean infoGaraBean = getInfoGaraBeanByLotto(Long.parseLong(bean.getId_lotto()));
			boolean okDataAttivazioneCup = SimogProperties.getInstance()
					.isCUPLotto(infoGaraBean.getDataCreazioneGara());
			if (!okDataAttivazioneCup) {
				// Competenza Aggiudicazione
				CupLottoAggManager claMan = new CupLottoAggManager(connection, logger);
				return claMan.copyRecord(bean.getId_record(), bean.getData_inizio_record(), datavecchia);
			} else {
				// Competenza Lotto
				boolean ret = completaDatiAggCup(Long.parseLong(bean.getId_lotto()),
						Long.parseLong(bean.getId_record()), bean.getData_inizio_record()) > 0;

				// PP ritorno sempre true perchè non è detto che i record ci siano
				return true;
			}

		} catch (SQLException e) {
			logger.fatal(e);
			return false;
		} catch (Exception ex) {
			// log come fatal demandato al chiamante
			logger.error(ex);
			throw new ActionException(ex);
		}
	}

	public AllValidationBeans validaCodiciCUPConf(Lotto lt) {

		AllValidationBeans eccez = new AllValidationBeans();

		// Se tutti i cup non sono validi DIPE devo bloccare la pubblicazione
		for (CupLottoAggExt elem : lt.getElencoCup()) {
			if (elem.getDatiDIPE() != null && !Costanti.FLAG_VALORE_SI.equals(elem.getDatiDIPE().getVALIDO())) {
				eccez.addValidationErr(Messaggi.SIMOG_VALIDAZIONE_227.replace("$1", "conferma della scheda"));
				break;
			}
		}

		return eccez;
	}

	public void updateDatiCupLotto(Lotto lotto) throws ActionException {
		LottoManager lman = new LottoManager(connection, logger);
		CupLottoAggManager claMan = new CupLottoAggManager(connection, logger);
		TipoAppaltoManager appMan = new TipoAppaltoManager(connection, logger);
		AggiudicazioniManager aggMan = new AggiudicazioniManager(connection, logger);
		MotivoDerogaManager motivoDerogaManager = new MotivoDerogaManager(connection, logger);
		MisuraPremialeManager misuraPremialeManager = new MisuraPremialeManager(connection, logger);
		try 	{

			List<AggiudicazioneBean> aggiudicazioni = aggMan
					.getAggiudicazioniByCIG(lotto.getCIG() + lotto.getCIG_kkk());
			AggiudicazioneBean maxCuiBean = null;
			for (AggiudicazioneBean bean : aggiudicazioni) {
				if (maxCuiBean == null || bean.getProgCUI() > maxCuiBean.getProgCUI())
					maxCuiBean = bean;
			}
			Long idAggiudicazione = maxCuiBean == null ? null : maxCuiBean.getIdAggiudicazione();
			Timestamp dataInizioAggiudicazione = maxCuiBean == null ? null : maxCuiBean.getDataInizioAggiudicazione();

			if (lotto.getElencoCup() != null)
				for (CupLottoAgg item : lotto.getElencoCup()) {
					item.setIdLotto(lotto.getId_Lotto());
					item.setIdAggiudicazione(idAggiudicazione);
					item.setDataInizioAgg(dataInizioAggiudicazione);
				}

			if (lotto.getElencoTipoAppaltoLottoL() != null)
				for (TipoAppaltoAggBean item : lotto.getElencoTipoAppaltoLottoL()) {
					item.setIdLotto(lotto.getId_Lotto());
					if (idAggiudicazione != null) {
						item.setIdAggiudicazione(idAggiudicazione);
						item.setDataInizioAggiudicazione(dataInizioAggiudicazione);
					}
				}
			if (lotto.getElencoTipoAppaltoLottoF() != null)
				for (TipoAppaltoAggBean item : lotto.getElencoTipoAppaltoLottoF()) {
					item.setIdLotto(lotto.getId_Lotto());
					if (idAggiudicazione != null) {
						item.setIdAggiudicazione(idAggiudicazione);
						item.setDataInizioAggiudicazione(dataInizioAggiudicazione);
					}
				}
			// update flag
			lman.updateFlagCup(lotto);
			claMan.updateElencoCup(lotto, lotto.getData_Pubblicazione() != null);
			appMan.aggiornaTipoAppaltoLotto(lotto, lotto.getData_Pubblicazione() != null);
			lman.updateFlagParitaDiGenere(lotto);
			motivoDerogaManager.aggiornaMotivoDerogaLotto(lotto);
			misuraPremialeManager.aggiornaMisuraPremialeLotto(lotto);

		} catch (SQLException e) {
			logger.error("Impossibile aggiornare i dati CUP del lotto ID[" + lotto.getId_Lotto() + "]", e);
			throw new ActionException(e);
		}

	}

	public Map<String, String> getIntegrazioneCupDatiMap(TableBean garaList) throws ActionException {
		Map<String, String> mappa = new LinkedHashMap<String, String>();

		for (int idx = 0; idx < garaList.getTableSize(); idx++) {
			TableBeanRow row = garaList.getRow(idx);
			Long idLotto = Long.parseLong(row.getNulledField(LOTTO.ID_LOTTO));
			String key = row.getNulledField(LOTTO.CIG) + row.getNulledField(LOTTO.CIG_KKK);
			String value = concatCup(getElencoCup(idLotto, null, null, false));
			mappa.put(key, value);
		}
		return mappa;
	}

	private String concatCup(List<CupLottoAggExt> listaCup) {
		String result = "";
		for (CupLottoAggExt item : listaCup) {
			result += ", " + item.getCup();
		}
		return result.length() > 0 ? result.substring(2) : result;
	}

}
