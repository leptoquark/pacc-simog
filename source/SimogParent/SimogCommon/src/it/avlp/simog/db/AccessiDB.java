package it.avlp.simog.db;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.beans.EsitoEnum;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.MisuraPremialeBean;
import it.avlp.simog.beans.MotivoDerogaBean;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.RichiestaAnnullamento;
import it.avlp.simog.beans.SchedaState;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.common.servlet.ParametriServletAccordo;
import it.avlp.simog.common.servlet.ParametriServletAvanzamento;
import it.avlp.simog.common.servlet.ParametriServletCollaudo;
import it.avlp.simog.common.servlet.ParametriServletConclusioni;
import it.avlp.simog.common.servlet.ParametriServletR129;
import it.avlp.simog.common.servlet.ParametriServletSospensioni;
import it.avlp.simog.common.servlet.ParametriServletSubappalti;
import it.avlp.simog.common.servlet.ParametriServletVariante;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.advanced.TableBeanRow;
import it.avlp.simog.db.generated.ACCORDI;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.db.generated.ART_ESCLUSIONE;
import it.avlp.simog.db.generated.CATEGORIA;
import it.avlp.simog.db.generated.COLLAUDO;
import it.avlp.simog.db.generated.CONTRAENTE_REGIONE;
import it.avlp.simog.db.generated.CPVEU;
import it.avlp.simog.db.generated.FINE_LAVORI;
import it.avlp.simog.db.generated.INDICE_DISPERSIONE;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.INIZIO_LAVORI;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.MISURA_PREMIALE;
import it.avlp.simog.db.generated.MOTIVO_COLLEGAMENTO;
import it.avlp.simog.db.generated.MOTIVO_DEROGA;
import it.avlp.simog.db.generated.R129;
import it.avlp.simog.db.generated.RICHIESTA_ANNULLAMENTO;
import it.avlp.simog.db.generated.RUOLI_RESPONSABILE;
import it.avlp.simog.db.generated.RUOLI_RESP_SCHEDA;
import it.avlp.simog.db.generated.SCELTA_CONTRAENTE;
import it.avlp.simog.db.generated.SOSPENSIONI;
import it.avlp.simog.db.generated.STATI_AVANZ;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.db.generated.STIPULA;
import it.avlp.simog.db.generated.SUBAPPALTI;
import it.avlp.simog.db.generated.VARIANTI;
import it.avlp.simog.exception.SimogException;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.lotto.LottoManager;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

/**
 * Super classe di tutti i manager, centralizza alcune operazioni fondamentali
 * per l-interazione con il db
 *
 */
public class AccessiDB {

	protected Logger logger = null;

// PP non usato	protected SimogProperties configuration = null;

	protected Connection activeConnection = null;
//	protected static String FORNITURE_BENI = "FB";
//	protected static String FORNITURE_SERVIZI = "FS";

	public static String DATA_NULLA = "********";

	public AccessiDB() {
	}

	public AccessiDB(Connection currentActiveConnection, Logger logger) {
		this.activeConnection = currentActiveConnection;
		this.logger = logger;
	}

	protected String addInCondition(String fieldName, Enumeration condList) {

		logger.debug("Esecuzione addInCondition");
		return fieldName + addInCondition(condList);
	}

	/**
	 * VL - PATCH - MASSLAODER 20-01-2010 Retrocompatibilita' per l' id locale
	 * 
	 * Effettua un select per controllare lo stato del campo id_scheda_locale
	 * tramite i riferimenti passati: - se non esiste il record eccezione - se
	 * esiste il record ma l'id locale e' gia valorizzato non fa' nulla e ritorna
	 * false - se esiste il record e l'id locale e' NULL ritorna true - se sono
	 * entrambe nulli ritorna false
	 * 
	 * 
	 * @param tableName
	 * @param nomeCampoId
	 * @param nomeCampoDataInizio
	 * @param idSimog
	 * @param dataInizioSimog
	 * @param idLocale
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	protected boolean idLocaleNecessitaDiAggiornamento(String tableName, String nomeCampoId, String nomeCampoDataInizio,
			long idSimog, Timestamp dataInizioSimog, String idLocale) throws Exception, SQLException {

		String nomeCampoIdSchedaLocale = INFO_AGGIUDICAZIONI.ID_SCHEDA_LOCALE;
		String nomeCampoIdStatoScheda = INFO_AGGIUDICAZIONI.ID_STATO;

		String queryDiControllo = " Select " + nomeCampoIdSchedaLocale + " From " + tableName + " WHERE " + nomeCampoId
				+ " = ? AND " + nomeCampoDataInizio + " = ? AND " + " ( " + nomeCampoIdStatoScheda + " = 1 OR "
				+ nomeCampoIdStatoScheda + " = 2 ) ";

		logger.debug("Query di selezione record per id locale [" + tableName + "," + nomeCampoId + "=" + idSimog + ","
				+ nomeCampoDataInizio + "=" + dataInizioSimog + "] " + queryDiControllo);

		int index = 1;
		PreparedStatement prepStat1 = null;
		ResultSet rs = null;
		try {
			if (idLocale != null && !"".equals(idLocale)) {

				prepStat1 = activeConnection.prepareStatement(queryDiControllo);

				prepStat1.setLong(index++, idSimog);
				prepStat1.setTimestamp(index++, dataInizioSimog);

				rs = prepStat1.executeQuery();
				// resetto l'indice
				index = 1;
				// se e' stato trovato un record
				if (rs.next()) {
					String idLocaleSulDb = rs.getString(nomeCampoIdSchedaLocale);
					// controlla che sia nullo
					if (idLocaleSulDb == null) {
						logger.info("L'id locale risulta NULLO dovra' dunque essere aggiornato con il seguente valore["
								+ idLocale + "] rilevato nel file xml");
						return true;
					} else {
						logger.info("L'id locale risulta gia' valorizzato con questo valore [" + idLocaleSulDb
								+ "] non sara' dunque aggiornato con il seguente valore[" + idLocale
								+ "] rilevato nel file xml");
						return false;
					}
					// altrimenti errore nei riferimenti, o record non esistente, quindi devo
					// bloccare l'esecuzione
				} else {
					throw new Exception("Non e' stato trovato alcun record per la tabella [" + tableName
							+ "] con i seguenti riferimenti [" + idSimog + " , " + dataInizioSimog
							+ "] per aggiornarlo con il seguente id locale [" + idLocale + "]");
				}

			} else {
				logger.info("L'id locale risulta NULLO sia sul db che nel file xml, non occorre alcun aggiornamento");
				return false;
			}

		} finally {
			close(rs, prepStat1);
		}

	}

	/**
	 * VL - PATCH - MASSLAODER 20-01-2010 Retrocompatibilita' per l' id locale
	 * 
	 * - ritorna il numero dei record aggiornati o eccezione nel caso in cui i
	 * record aggiornati sono > 1 (sintomo anomalia)
	 * 
	 * vedi idLocaleNecessitaDiAggiornamento(..), eseguire questo metodo solamente
	 * se questo metodo ha ritornato true
	 * 
	 * @param tableName
	 * @param nomeCampoId
	 * @param nomeCampoDataInizio
	 * @param idSimog
	 * @param dataInizioSimog
	 * @param idLocale
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	protected boolean aggiornaIdLocale(String tableName, String nomeCampoId, String nomeCampoDataInizio, long idSimog,
			Timestamp dataInizioSimog, String idLocale) throws Exception, SQLException {

		String nomeCampoIdSchedaLocale = INFO_AGGIUDICAZIONI.ID_SCHEDA_LOCALE;
		String nomeCampoIdStatoScheda = INFO_AGGIUDICAZIONI.ID_STATO;

		String query = "update " + tableName + " set " + " " + nomeCampoIdSchedaLocale + "  = ? " + " WHERE "
				+ nomeCampoId + " = ? AND " + nomeCampoDataInizio + " = ? AND " + " ( " + nomeCampoIdStatoScheda
				+ " = 1 OR " + nomeCampoIdStatoScheda + " = 2 ) ";

		logger.debug("Query di aggiornamento id locale [" + tableName + "," + nomeCampoId + "=" + idSimog + ","
				+ nomeCampoDataInizio + "=" + dataInizioSimog + "] " + query);

		int index = 1;
		int affectedRows = -1;
		PreparedStatement prepStat = null;
		try {

			prepStat = activeConnection.prepareStatement(query);

			prepStat.setString(index++, idLocale);
			prepStat.setLong(index++, idSimog);
			prepStat.setTimestamp(index++, dataInizioSimog);

			affectedRows = prepStat.executeUpdate();

			// nel caso in cui, l'aggiornamento ha coinvolto piu di un record c'e' qualche
			// errore quindi interrompi operazione
			if (affectedRows > 1)
				throw new Exception(
						"Il numero di record aggiornati e' maggiore di uno per evitare la corruzione dei dati si effettuera' il rollback dell' operazione");

			// il valore puo' essere solamente 0 o 1
			return affectedRows > 0;

		} finally {
			close(null, prepStat);
		}

	}

	/**************************************************************************************************
	 * Crea una stringa concatenando tutte le condizioni inserite nella lista in
	 * ingresso
	 * 
	 * @param condList : un enumeration che contiene le condizioni da concatenare
	 * @return String
	 **************************************************************************************************/
	protected String addInCondition(Enumeration condList) {

		logger.debug("Esecuzione addInCondition");

		String finalQuery = " in (";
		int i = 0;
		while (condList.hasMoreElements()) {
			if (i++ > 0) {
				finalQuery += ", ";
			}
			finalQuery += "?";
			condList.nextElement();
		}
		finalQuery += ") ";

		return finalQuery;
	}

	/***************************************************************************************************
	 * Restituisce una TableBean della tabella che risulta avere a null il campo
	 * specificato, la tablebean e' ordinata in base al parametro indicato da
	 * nomeCampoOrdinamento.
	 * 
	 * @param nomeTabella          : Stringa per indicare il nome della tabella
	 * @param nomeCampoValidita    : Stringa per il nome del campo della tabella
	 * @param nomeCampoOrdinamento : Stringa per il campo in base al quale verra'
	 *                             effettuato l'ordinamento
	 * @param dataVal              data inizio validita', se passato il valore
	 *                             '********' significa senza controllo data, prende
	 *                             tutti i codici
	 * @param nomeCampoOrdinamento : Stringa per il campo in base al quale verra'
	 *                             effettuato l'ordinamento
	 * @return TableBean
	 * @throws SQLException
	 ***************************************************************************************************/
	public TableBean executeSelect(String nomeTabella, String nomeCampoValidita, String nomeCampoOrdinamento,
			String dataVal, boolean isOrgano) throws SQLException {

		String fullSelect = "SELECT * FROM " + nomeTabella + " WHERE 1=1 ";

		if (dataVal != null && !DATA_NULLA.equals(dataVal))
			fullSelect = fullSelect + " AND " + buildISNULL(nomeCampoValidita, "99999999") + " >= '" + dataVal + "' ";

		// PP organi costituzionali, devo evitare di scrivere anche la voce di categoria
		// fittizia se sono chiamato dal listtypes
		if (CATEGORIA.TABLE_NAME.equals(nomeTabella) && isOrgano == false) {
			fullSelect = fullSelect + " AND " + CATEGORIA.ID_CATEGORIA + "<> '" + Costanti.CATEGORIA_PREV_OOCC + "'";
		}

		// is30233_RFWEBGL06Active organi costituzionali, devo evitare di scrivere anche
		// la voce di articolo esclusione se sono chiamato dal listtypes
		if (ART_ESCLUSIONE.TABLE_NAME.equals(nomeTabella) && isOrgano == false) {
			fullSelect = fullSelect + " AND " + ART_ESCLUSIONE.ID_ESCLUSIONE + "<> '" + Costanti.ART_ESCLUSIONE_OOCC
					+ "'";
		}

		fullSelect = fullSelect + " ORDER BY " + nomeCampoOrdinamento;
		// PP ordinamenti+ " DESC";

		Statement stmt = activeConnection.createStatement();

		logger.debug("Esecuzione query [" + fullSelect + "]");

		TableBean tb = new TableBean(stmt.executeQuery(fullSelect));

		close(null, stmt);

		return tb;
	}
	
	//3.04.9.1 MAD DELLA MEV 40610
	public TableBean executeSelectDerogaQualificazioneSA(String nomeTabella, String nomeCampoValidita, String nomeCampoInizioValidita, String nomeCampoOrdinamento,
			String dataVal, boolean isOrgano) throws SQLException {

		String fullSelect = "SELECT * FROM " + nomeTabella + " WHERE 1=1 ";

		if (dataVal != null && !DATA_NULLA.equals(dataVal))
			fullSelect = fullSelect + " AND " + buildISNULL(nomeCampoValidita, "99999999") + " > '" + dataVal + "' ";
		
		if (dataVal != null && !DATA_NULLA.equals(dataVal))
			fullSelect = fullSelect + " AND " + buildISNULL(nomeCampoInizioValidita, "20230701") + " <= '" + dataVal + "' ";

		// PP organi costituzionali, devo evitare di scrivere anche la voce di categoria
		// fittizia se sono chiamato dal listtypes
		if (CATEGORIA.TABLE_NAME.equals(nomeTabella) && isOrgano == false) {
			fullSelect = fullSelect + " AND " + CATEGORIA.ID_CATEGORIA + "<> '" + Costanti.CATEGORIA_PREV_OOCC + "'";
		}

		// is30233_RFWEBGL06Active organi costituzionali, devo evitare di scrivere anche
		// la voce di articolo esclusione se sono chiamato dal listtypes
		if (ART_ESCLUSIONE.TABLE_NAME.equals(nomeTabella) && isOrgano == false) {
			fullSelect = fullSelect + " AND " + ART_ESCLUSIONE.ID_ESCLUSIONE + "<> '" + Costanti.ART_ESCLUSIONE_OOCC
					+ "'";
		}

		fullSelect = fullSelect + " ORDER BY " + nomeCampoOrdinamento;
		// PP ordinamenti+ " DESC";

		Statement stmt = activeConnection.createStatement();

		logger.debug("Esecuzione query [" + fullSelect + "]");

		TableBean tb = new TableBean(stmt.executeQuery(fullSelect));

		close(null, stmt);

		return tb;
	}

	/***************************************************************************************************
	 * Restituisce una TableBean della tabella che risulta avere a null il campo
	 * specificato, la tablebean e' ordinata in base al parametro indicato da
	 * nomeCampoOrdinamento.
	 * 
	 * @param nomeTabella          : Stringa per indicare il nome della tabella
	 * @param nomeCampoValidita    : Stringa per il nome del campo della tabella
	 * @param nomeCampoOrdinamento : Stringa per il campo in base al quale verra'
	 *                             effettuato l'ordinamento
	 * @param dataVal              data inizio validita', se passato il valore
	 *                             '********' significa senza controllo data, prende
	 *                             tutti i codici
	 * @param nomeCampoOrdinamento : Stringa per il campo in base al quale verra'
	 *                             effettuato l'ordinamento
	 * @return TableBean
	 * @throws SQLException
	 ***************************************************************************************************/
	public TableBean executeSelectWithData(String nomeTabella, String nomeCampoInizioValidita,
			String nomeCampoFineValidita, String nomeCampoOrdinamento, String dataVal, boolean isOrgano)
			throws SQLException {

		String fullSelect = "SELECT * FROM " + nomeTabella + " WHERE 1=1 ";

		if (dataVal != null && !DATA_NULLA.equals(dataVal)) {
			fullSelect = fullSelect + " AND " + buildISNULL(nomeCampoFineValidita, "99999999") + " >= '" + dataVal
					+ "' ";
			fullSelect = fullSelect + " AND " + nomeCampoInizioValidita + " < '" + dataVal + "' ";
		}
		// PP organi costituzionali, devo evitare di scrivere anche la voce di categoria
		// fittizia se sono chiamato dal listtypes
		if (CATEGORIA.TABLE_NAME.equals(nomeTabella) && isOrgano == false) {
			fullSelect = fullSelect + " AND " + CATEGORIA.ID_CATEGORIA + "<> '" + Costanti.CATEGORIA_PREV_OOCC + "'";
		}

		// is30233_RFWEBGL06Active organi costituzionali, devo evitare di scrivere anche
		// la voce di articolo esclusione se sono chiamato dal listtypes
		if (ART_ESCLUSIONE.TABLE_NAME.equals(nomeTabella) && isOrgano == false) {
			fullSelect = fullSelect + " AND " + ART_ESCLUSIONE.ID_ESCLUSIONE + "<> '" + Costanti.ART_ESCLUSIONE_OOCC
					+ "'";
		}

		fullSelect = fullSelect + " ORDER BY " + nomeCampoOrdinamento;
		// PP ordinamenti+ " DESC";

		Statement stmt = activeConnection.createStatement();

		logger.debug("Esecuzione query [" + fullSelect + "]");

		TableBean tb = new TableBean(stmt.executeQuery(fullSelect));

		close(null, stmt);

		return tb;
	}

	// TICKET ALM - 3.04.2 2005
	/***************************************************************************************************
	 * Restituisce una TableBean della tabella che risulta avere a null il campo
	 * specificato, la tablebean e' ordinata in base al parametro indicato da
	 * nomeCampoOrdinamento.
	 * 
	 * @param nomeTabella          : Stringa per indicare il nome della tabella
	 * @param nomeCampoValidita    : Stringa per il nome del campo della tabella
	 * @param nomeCampoOrdinamento : Stringa per il campo in base al quale verra'
	 *                             effettuato l'ordinamento
	 * @param dataVal              data inizio validita', se passato il valore
	 *                             '********' significa senza controllo data, prende
	 *                             tutti i codici
	 * @param nomeCampoOrdinamento : Stringa per il campo in base al quale verra'
	 *                             effettuato l'ordinamento
	 * @return TableBean
	 * @throws SQLException
	 ***************************************************************************************************/
	public TableBean executeSelectWithDP(String nomeTabella, String nomeCampoInizioValidita,
			String nomeCampoFineValidita, String nomeCampoOrdinamento, String dataVal, boolean isOrgano,
			String nomeParam, String param) throws SQLException {

		String fullSelect = "SELECT * FROM " + nomeTabella + " WHERE 1=1 ";

		if (dataVal != null && !DATA_NULLA.equals(dataVal)) {
			fullSelect = fullSelect + " AND " + buildISNULL(nomeCampoFineValidita, "99999999") + " >= '" + dataVal
					+ "' ";
			fullSelect = fullSelect + " AND " + nomeCampoInizioValidita + " < '" + dataVal + "' ";
		}
		// PP organi costituzionali, devo evitare di scrivere anche la voce di categoria
		// fittizia se sono chiamato dal listtypes
		if (CATEGORIA.TABLE_NAME.equals(nomeTabella) && isOrgano == false) {
			fullSelect = fullSelect + " AND " + CATEGORIA.ID_CATEGORIA + "<> '" + Costanti.CATEGORIA_PREV_OOCC + "'";
		}

		// is30233_RFWEBGL06Active organi costituzionali, devo evitare di scrivere anche
		// la voce di articolo esclusione se sono chiamato dal listtypes
		if (ART_ESCLUSIONE.TABLE_NAME.equals(nomeTabella) && isOrgano == false) {
			fullSelect = fullSelect + " AND " + ART_ESCLUSIONE.ID_ESCLUSIONE + "<> '" + Costanti.ART_ESCLUSIONE_OOCC
					+ "'";
		}

		fullSelect = fullSelect + " AND " + nomeParam + " = '" + param + "'";

		fullSelect = fullSelect + " ORDER BY " + nomeCampoOrdinamento;
		// PP ordinamenti+ " DESC";

		Statement stmt = activeConnection.createStatement();

		logger.debug("Esecuzione query [" + fullSelect + "]");

		TableBean tb = new TableBean(stmt.executeQuery(fullSelect));

		close(null, stmt);

		return tb;
	}

	/***************************************************************************************************
	 * Restituisce una TableBean della tabella che risulta avere a null il campo
	 * specificato, la tablebean e' ordinata in base al parametro indicato da
	 * nomeCampoOrdinamento.
	 * 
	 * @param nomeTabella          : Stringa per indicare il nome della tabella
	 * @param nomeCampoValidita    : Stringa per il nome del campo della tabella
	 * @param nomeCampoOrdinamento : Stringa per il campo in base al quale verr�
	 *                             effettuato l'ordinamento
	 * @param dataVal              data inizio validita', se passato il valore
	 *                             '********' significa senza controllo data, prende
	 *                             tutti i codici
	 * @param nomeCampoOrdinamento : Stringa per il campo in base al quale verra'
	 *                             effettuato l'ordinamento
	 * @return TableBean
	 * @throws SQLException
	 ***************************************************************************************************/
	public TableBean executeSelect(String nomeTabella, String nomeCampoInizioValidita, String nomeCampoOrdinamento,
			Date dataVal, boolean isOrgano, String nomeCampoFineValidita) throws SQLException {

		String fullSelect = "SELECT * FROM " + nomeTabella; // + " WHERE 1=1 ";

		if (dataVal != null) {
			fullSelect = fullSelect + " WHERE " + nomeCampoInizioValidita + " <=?" + " AND " + "("
					+ nomeCampoFineValidita + " IS NULL OR " + nomeCampoFineValidita + " >= ?)";
		}

		fullSelect = fullSelect + " ORDER BY " + nomeCampoOrdinamento;

		PreparedStatement stmt = activeConnection.prepareStatement(fullSelect);

		if (dataVal != null) {
			stmt.setDate(1, dataVal);
			stmt.setDate(2, dataVal);
		}

		logger.debug("Esecuzione query [" + fullSelect + "]");

		TableBean tb = new TableBean(stmt.executeQuery());

		close(null, stmt);

		return tb;
	}

	/***************************************************************************************************
	 * Restituisce una TableBean della tabella che risulta avere a null il campo
	 * specificato, la tablebean e' ordinata in base al parametro indicato da
	 * nomeCampoOrdinamento. Sono estratti solo i record che soddisfano la where
	 * 
	 * @param nomeTabella          : Stringa per indicare il nome della tabella
	 * @param nomeCampoValidita    : Stringa per il nome del campo della tabella
	 * @param nomeCampoOrdinamento : Stringa per il campo in base al quale verra'
	 *                             effettuato l'ordinamento
	 * @param dataVal              data inizio validita', se passato il valore
	 *                             '********' significa senza controllo data, prende
	 *                             tutti i codici
	 * @param nomeCampoOrdinamento : Stringa per il campo in base al quale verra'
	 *                             effettuato l'ordinamento
	 * @return TableBean
	 * @throws SQLException
	 ***************************************************************************************************/
	public TableBean executeSelectWhere(String nomeTabella, String nomeCampoValidita, String nomeCampoOrdinamento,
			String dataVal, String whereCond, String listaCampi) throws SQLException {

		String fullSelect = "SELECT " + (listaCampi == null ? "*" : listaCampi) + " FROM " + nomeTabella
				+ " WHERE 1=1 ";

		if (dataVal != null && !DATA_NULLA.equals(dataVal))
			fullSelect = fullSelect + " AND " + buildISNULL(nomeCampoValidita, "99999999") + " >= '" + dataVal + "' ";

		if (whereCond != null)
			fullSelect = fullSelect + " AND " + whereCond;

		fullSelect = fullSelect + " ORDER BY " + nomeCampoOrdinamento;

		Statement stmt = activeConnection.createStatement();

		logger.debug("Esecuzione query [" + fullSelect + "]");

		TableBean tb = new TableBean(stmt.executeQuery(fullSelect));

		close(null, stmt);

		return tb;
	}

	/*********************************************************************************************************
	 * Restituisce una Hashtable con l'id del campo e la descrizione in base alla
	 * tabella specificata ed al campo di validita specificato se questo rusulta
	 * maggiore della data attuale.
	 * 
	 * @param nomeTabella      Stringa con il noem della tabella
	 * @param campoId          String per l'id del campo
	 * @param campoDescrizione String per la Descrizione del campo
	 * @param campoValidita    String campo in base al quale si effettua la ricerca
	 * @param data             deve essere Timestamp or String, campo per gestire le
	 *                         tipologie non piu valide delle tipologiche ma valide
	 *                         al momento dell'inserimento dei dati (quindi view e
	 *                         validation afterwards)
	 * @return Map&lt;String, String&gt;
	 * @throws SQLException
	 *********************************************************************************************************/
	public Map<String, String> getTipologica(String nomeTabella, String campoId, String campoDescrizione,
			String campoValidita, Object data) throws SQLException {

		String fullSelect = "SELECT " + campoId + ", " + campoDescrizione + " FROM " + nomeTabella;

		if (!SimogFlags.isFromMassLoader()) {
			fullSelect = fullSelect + " WHERE " + buildISNULL(campoValidita, "99999999") + " >= ?" + " ORDER BY "
					+ buildISNULL(campoValidita, "99999999");
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;

		HashMap<String, String> tipologicaM = new HashMap<String, String>();

		try {
			stmt = activeConnection.prepareStatement(fullSelect);

			if (!SimogFlags.isFromMassLoader())
				stmt.setObject(1, PageHelper.getFormattedNowOrInputFormattedDate(data, getNow()));

			rs = stmt.executeQuery();

			while (rs.next()) {
				tipologicaM.put(rs.getString(campoId), rs.getString(campoDescrizione));
			}
			return tipologicaM;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return tipologicaM;
		} finally {
			close(rs, stmt);
		}
	}

	/**
	 * Carica le voci della tipologica indicata ad eccezione degli id specificati
	 * nel parametro idNotIncluded
	 * 
	 * @param nomeTabella
	 * @param campoId
	 * @param campoDescrizione
	 * @param campoValidita
	 * @param data
	 * @param idNotIncluded
	 * @return
	 * @throws SQLException
	 */
	public Map<String, String> getTipologicaExcept(String nomeTabella, String campoId, String campoDescrizione,
			String campoValidita, Object data, int[] idNotIncluded) throws SQLException {

		String fullSelect = "SELECT " + campoId + ", " + campoDescrizione + " FROM " + nomeTabella;

		if (!SimogFlags.isFromMassLoader()) {
			fullSelect = fullSelect + " WHERE " + buildISNULL(campoValidita, "99999999") + " >= ?" + " AND " + campoId
					+ " NOT IN (";

			for (int i = 0; i < idNotIncluded.length; i++) {
				fullSelect += idNotIncluded[i];
				if (i < idNotIncluded.length - 1)
					fullSelect += ",";
			}
			fullSelect += ") ORDER BY " + buildISNULL(campoValidita, "99999999");
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;

		HashMap<String, String> tipologicaM = new HashMap<String, String>();

		try {
			System.out.println("TECHNIS getTipologicaExcept " + fullSelect);
			stmt = activeConnection.prepareStatement(fullSelect);

			if (!SimogFlags.isFromMassLoader())
				stmt.setObject(1, PageHelper.getFormattedNowOrInputFormattedDate(data, getNow()));

			rs = stmt.executeQuery();

			while (rs.next()) {
				tipologicaM.put(rs.getString(campoId), rs.getString(campoDescrizione));
			}
			return tipologicaM;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return tipologicaM;
		} finally {
			close(rs, stmt);
		}
	}

	// TICKET ALM #7649
	/*********************************************************************************************************
	 * Restituisce una Hashtable con l'id del campo e la descrizione in base alla
	 * tabella specificata ed al campo di validita specificato se questo rusulta
	 * maggiore della data attuale.
	 * 
	 * 
	 * @param nomeTabella      Stringa con il noem della tabella
	 * @param campoId          String per l'id del campo
	 * @param campoDescrizione String per la Descrizione del campo
	 * @param campoValidita    String campo in base al quale si effettua la ricerca
	 * @param data             deve essere Timestamp or String, campo per gestire le
	 *                         tipologie non piu valide delle tipologiche ma valide
	 *                         al momento dell'inserimento dei dati (quindi view e
	 *                         validation afterwards)
	 * @return Map&lt;String, String&gt;
	 * @throws SQLException
	 *********************************************************************************************************/
	public Map<String, String> getTipologicaNoFlag(String nomeTabella, String campoId, String campoDescrizione,
			String campoValidita, Object data) throws SQLException {

		String fullSelect = "SELECT " + campoId + ", " + campoDescrizione + " FROM " + nomeTabella;

		if (!SimogFlags.isFromMassLoader()) {
			fullSelect = fullSelect + " WHERE " + buildISNULL(campoValidita, "99999999") + " >= ?" + " ORDER BY "
					+ buildISNULL(campoValidita, "99999999");
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;

		HashMap<String, String> tipologicaM = new HashMap<String, String>();

		try {
			stmt = activeConnection.prepareStatement(fullSelect);
			if (!SimogFlags.isFromMassLoader())
				stmt.setObject(1, PageHelper.getFormattedNowOrInputFormattedDate(data, getNow()));

			rs = stmt.executeQuery();

			while (rs.next()) {
				tipologicaM.put(rs.getString(campoId), rs.getString(campoDescrizione));
			}
			return tipologicaM;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return tipologicaM;
		} finally {
			close(rs, stmt);
		}
	}

	// TICKET ALM #2847
	/*********************************************************************************************************
	 * Restituisce una Hashtable con l'id del campo e la descrizione in base alla
	 * tabella specificata ed al campo di validita specificato se questo rusulta
	 * maggiore della data attuale.
	 * 
	 * @param nomeTabella      Stringa con il noem della tabella
	 * @param campoId          String per l'id del campo
	 * @param campoDescrizione String per la Descrizione del campo
	 * @param campoValidita    String campo in base al quale si effettua la ricerca
	 * @param data             deve essere Timestamp or String, campo per gestire le
	 *                         tipologie non piu valide delle tipologiche ma valide
	 *                         al momento dell'inserimento dei dati (quindi view e
	 *                         validation afterwards)
	 * @return Map&lt;String, String&gt;
	 * @throws SQLException
	 *********************************************************************************************************/
	public Map<String, String> getTipologicaWithData(String nomeTabella, String campoId, String campoDescrizione,
			String campoInizioValidita, String campoFineValidita, Object data) throws SQLException {

		String fullSelect = "SELECT " + campoId + ", " + campoDescrizione + " FROM " + nomeTabella;

		if (!SimogFlags.isFromMassLoader()) {
			fullSelect = fullSelect + " WHERE " + buildISNULL(campoFineValidita, "99999999") + " > ?" + " AND "
					+ campoInizioValidita + " <= ? ";
			if (MOTIVO_COLLEGAMENTO.TABLE_NAME.equals(nomeTabella)) {
				fullSelect += "ORDER BY " + MOTIVO_COLLEGAMENTO.ORDINAMENTO;
			} else {
				fullSelect += "ORDER BY " + buildISNULL(campoFineValidita, "99999999");
			}
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		HashMap<String, String> tipologicaM = new HashMap<String, String>();

		try {
			stmt = activeConnection.prepareStatement(fullSelect);

			if (!SimogFlags.isFromMassLoader()) {
				stmt.setObject(1, PageHelper.getFormattedNowOrInputFormattedDate(data, getNow()));
				stmt.setObject(2, PageHelper.getFormattedNowOrInputFormattedDate(data, getNow()));
			}
			rs = stmt.executeQuery();

			while (rs.next()) {
				tipologicaM.put(rs.getString(campoId), rs.getString(campoDescrizione));
			}
			return tipologicaM;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return tipologicaM;
		} finally {
			close(rs, stmt);
		}
	}
	// FINE TICKET ALM #2847

	public LinkedHashMap<String, String> getMotivoCollegamentoWithData(String nomeTabella, String campoId,
			String campoDescrizione, String campoInizioValidita, String campoFineValidita, Object data)
			throws SQLException {

		String fullSelect = "SELECT " + campoId + ", " + campoDescrizione + " FROM " + nomeTabella;

		if (!SimogFlags.isFromMassLoader()) {
			fullSelect = fullSelect + " WHERE " + buildISNULL(campoFineValidita, "99999999") + " > ?" + " AND "
					+ campoInizioValidita + " <= ? ";
			if (MOTIVO_COLLEGAMENTO.TABLE_NAME.equals(nomeTabella)) {
				fullSelect += "ORDER BY " + MOTIVO_COLLEGAMENTO.ORDINAMENTO;
			} else {
				fullSelect += "ORDER BY " + buildISNULL(campoFineValidita, "99999999");
			}
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedHashMap<String, String> tipologicaM = new LinkedHashMap<String, String>();

		try {
			stmt = activeConnection.prepareStatement(fullSelect);

			if (!SimogFlags.isFromMassLoader()) {
				stmt.setObject(1, PageHelper.getFormattedNowOrInputFormattedDate(data, getNow()));
				stmt.setObject(2, PageHelper.getFormattedNowOrInputFormattedDate(data, getNow()));
			}
			rs = stmt.executeQuery();

			while (rs.next()) {
				tipologicaM.put(rs.getString(campoId), rs.getString(campoDescrizione));
			}
			return tipologicaM;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return tipologicaM;
		} finally {
			close(rs, stmt);
		}
	}

	public Map<String, String> getMotivoDerogaWithData(String nomeTabella, String campoId, String campoDescrizione,
			String campoInizioValidita, String campoFineValidita, Object data) throws SQLException {

		String fullSelect = "SELECT * FROM " + nomeTabella;

		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedHashMap<String, String> tipologicaM = new LinkedHashMap<String, String>();

		try {
			stmt = activeConnection.prepareStatement(fullSelect);

			rs = stmt.executeQuery();

			while (rs.next()) {
				tipologicaM.put(rs.getString(campoId), rs.getString(campoDescrizione));
			}
			return tipologicaM;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return tipologicaM;
		} finally {
			close(rs, stmt);
		}
	}

	public Map<String, String> getMisuraPremialeWithData(String nomeTabella, String campoId, String campoDescrizione,
			String dataInizioValidita, String dataFineValidita, String dataUltimaModifica) throws SQLException {

		String fullSelect = "SELECT * FROM " + nomeTabella;

		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedHashMap<String, String> tipologicaM = new LinkedHashMap<String, String>();

		try {
			stmt = activeConnection.prepareStatement(fullSelect);

			rs = stmt.executeQuery();

			while (rs.next()) {
//				MisuraPremialeBean misuraPremialeBean = new MisuraPremialeBean(rs.getLong(1), rs.getString(2),
//						rs.getString(3), rs.getString(4), rs.getString(5));

				tipologicaM.put(rs.getString(campoId), rs.getString(campoDescrizione));
				// list.add(misuraPremialeBean);
			}

			return tipologicaM;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return tipologicaM;
		} finally {
			close(rs, stmt);
		}
	}

	public List<MisuraPremialeBean> getMisuraPremialeWithDataById(String nomeTabella, List<String> campoIds)
			throws SQLException {
		List<MisuraPremialeBean> list = new ArrayList<MisuraPremialeBean>();

		if (campoIds.size() > 0) {
			String whereCondition = " where Id_Misura_Premiale=" + campoIds.get(0);

			for (int index = 1; index < campoIds.size(); index++) {
				whereCondition = whereCondition.concat(" OR Id_Misura_Premiale=" + campoIds.get(index) + " ");
			}

			String fullSelect = "SELECT * FROM " + nomeTabella + whereCondition;

			PreparedStatement stmt = null;
			ResultSet rs = null;

			try {
				stmt = activeConnection.prepareStatement(fullSelect);

				rs = stmt.executeQuery();

				while (rs.next()) {
					MisuraPremialeBean misuraPremialeBean = new MisuraPremialeBean(rs.getLong(1), rs.getString(2),
							rs.getString(3), rs.getString(4), rs.getString(5));

					// tipologicaM.put(rs.getString(campoId), rs.getString(campoDescrizione));
					list.add(misuraPremialeBean);
				}

				return list;
			} catch (Exception e) {
				logger.error(e.getMessage());
				return list;
			} finally {
				close(rs, stmt);
			}
		}
		return list;
	}

	public List<MotivoDerogaBean> getMotivoDerogaWithDataById(String nomeTabella, List<String> campoIds)
			throws SQLException {
		List<MotivoDerogaBean> tipologicaM = new ArrayList<MotivoDerogaBean>();

		if (campoIds.size() > 0) {
			String whereCondition = " where Id_Motivo_Deroga=" + campoIds.get(0);

			for (int index = 1; index < campoIds.size(); index++) {
				whereCondition = whereCondition.concat(" OR Id_Motivo_Deroga=" + campoIds.get(index) + " ");
			}

			String fullSelect = "SELECT * FROM " + nomeTabella + whereCondition;

			PreparedStatement stmt = null;
			ResultSet rs = null;

			try {
				stmt = activeConnection.prepareStatement(fullSelect);

				rs = stmt.executeQuery();

				while (rs.next()) {
					MotivoDerogaBean motivoDerogaBean = new MotivoDerogaBean(rs.getLong(1), rs.getString(2),
							rs.getString(3), rs.getString(4), rs.getString(5));
					tipologicaM.add(motivoDerogaBean);
				}
				return tipologicaM;
			} catch (Exception e) {
				logger.error(e.getMessage());
				return tipologicaM;
			} finally {
				close(rs, stmt);
			}
		}

		return tipologicaM;
	}

	public Map<String, String> getTipologicaNoData(String nomeTabella, String campoId, String campoDescrizione,
			String campoParam, String param) throws SQLException {

		String fullSelect = "SELECT " + campoId + ", " + campoDescrizione + " FROM " + nomeTabella;

		fullSelect = fullSelect + " WHERE " + campoParam + " = ? ";

		PreparedStatement stmt = null;
		ResultSet rs = null;

		HashMap<String, String> tipologicaM = new HashMap<String, String>();

		try {
			stmt = activeConnection.prepareStatement(fullSelect);

			stmt.setString(1, param);
			rs = stmt.executeQuery();

			while (rs.next()) {
				tipologicaM.put(rs.getString(campoId), rs.getString(campoDescrizione));
			}

			return tipologicaM;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return tipologicaM;
		} finally {
			close(rs, stmt);
		}
	}

	// TICKET ALM - 3.04.2 2005
	/*********************************************************************************************************
	 * Restituisce una Hashtable con l'id del campo e la descrizione in base alla
	 * tabella specificata ed al campo di validita specificato se questo rusulta
	 * maggiore della data attuale.
	 * 
	 * @param nomeTabella      Stringa con il noem della tabella
	 * @param campoId          String per l'id del campo
	 * @param campoDescrizione String per la Descrizione del campo
	 * @param campoValidita    String campo in base al quale si effettua la ricerca
	 * @param data             deve essere Timestamp or String, campo per gestire le
	 *                         tipologie non piu valide delle tipologiche ma valide
	 *                         al momento dell'inserimento dei dati (quindi view e
	 *                         validation afterwards)
	 * @return Map&lt;String, String&gt;
	 * @throws SQLException
	 *********************************************************************************************************/
	public Map<String, String> getTipologicaWithDP(String nomeTabella, String campoId, String campoDescrizione,
			String campoInizioValidita, String campoFineValidita, Object data, String campoParam, String param)
			throws SQLException {

		String fullSelect = "SELECT " + campoId + ", " + campoDescrizione + " FROM " + nomeTabella;

		fullSelect = fullSelect + " WHERE " + campoParam + " = ? ";

		if (!SimogFlags.isFromMassLoader()) {
			fullSelect = fullSelect + " AND " + campoInizioValidita + " < ?" + " AND "
					+ buildISNULL(campoFineValidita, "99999999") + " >= ?" + " ORDER BY "
					+ buildISNULL(campoFineValidita, "99999999");
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;

		HashMap<String, String> tipologicaM = new HashMap<String, String>();

		try {
			stmt = activeConnection.prepareStatement(fullSelect);

			stmt.setString(1, param);
			if (!SimogFlags.isFromMassLoader()) {
				stmt.setObject(2, PageHelper.getFormattedNowOrInputFormattedDate(data, getNow()));
				stmt.setObject(3, PageHelper.getFormattedNowOrInputFormattedDate(data, getNow()));
			}
			rs = stmt.executeQuery();

			while (rs.next()) {
				tipologicaM.put(rs.getString(campoId), rs.getString(campoDescrizione));
			}
			// DSR
			logger.debug("SIZE============" + tipologicaM.size());
			return tipologicaM;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return tipologicaM;
		} finally {
			close(rs, stmt);
		}
	}
	// FINE TICKET ALM 3.04.2 2005

	/*********************************************************************************************************
	 * Restituisce una Hashtable con l'id del campo e la descrizione in base alla
	 * tabella specificata ed al campo di inizio validita' e fine validita'.
	 * 
	 * @param nomeTabella         Stringa con il nome della tabella
	 * @param campoId             String per l'id del campo
	 * @param campoDescrizione    String per la Descrizione del campo
	 * @param campoInizioValidita String campo che indica la data di inizio
	 *                            validita' del record
	 * @param campoFineValidita   String campo che indica la data di fine validita'
	 *                            del record
	 * @param data                data di riferimento
	 * @return Map&lt;String, String&gt;
	 * @throws SQLException
	 *********************************************************************************************************/
	public Map<String, String> getTipologica(String nomeTabella, String campoId, String campoDescrizione,
			String campoInizioValidita, String campoFineValidita, Timestamp data) throws SQLException {

		String fullSelect = "SELECT " + campoId + ", " + campoDescrizione + " FROM " + nomeTabella;

		if (!SimogFlags.isFromMassLoader()) {
			fullSelect = fullSelect + " WHERE " + campoInizioValidita + " < ? " + " AND " + "(" + campoFineValidita
					+ " IS NULL OR " + campoFineValidita + " >= ? )" + " ORDER BY " + campoDescrizione;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;

		HashMap<String, String> tipologicaM = new HashMap<String, String>();

		try {
			stmt = activeConnection.prepareStatement(fullSelect);

			if (!SimogFlags.isFromMassLoader()) {
				stmt.setObject(1, data);
				stmt.setObject(2, data);
			}

			rs = stmt.executeQuery();

			while (rs.next()) {
				tipologicaM.put(rs.getString(campoId), rs.getString(campoDescrizione));
			}
			return tipologicaM;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return tipologicaM;
		} finally {
			close(rs, stmt);
		}
	}

	/*********************************************************************************************************
	 * Restituisce una Hashtable con l'id del campo e la descrizione in base alla
	 * tabella specificata ed al campo di validita specificato se questo risulta
	 * maggiore della data attuale. Odinata in base alla descrizione
	 * 
	 * @param nomeTabella      Stringa con il noem della tabella
	 * @param campoId          String per l'id del campo
	 * @param campoDescrizione String per la Descrizione del campo
	 * @param campoValidita    String campo in base al quale si effettua la ricerca
	 * @param data             deve essere Timestamp or String, campo per gestire le
	 *                         tipologie non piu valide delle tipologiche ma valide
	 *                         al momento dell'inserimento dei dati (quindi view e
	 *                         validation afterwards)
	 * @return Map&lt;String, String&gt;
	 * @throws SQLException
	 *********************************************************************************************************/
	public Map<String, String> getTipologicaDescr(String nomeTabella, String campoId, String campoDescrizione,
			String campoValidita, Object data, boolean isOrgano) throws SQLException {

		String ldata = null;

		if (data != null) {
			if (data instanceof String)
				ldata = (String) data;
			else
				ldata = PageHelper.getDBDateFromTS((Timestamp) data);
		}

		TableBean tb = executeSelect(nomeTabella, campoValidita, campoDescrizione,
				SimogFlags.isFromMassLoader() ? null : ldata, isOrgano);

		// lista con chiavi invertite
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
		for (int i = 0; i < tb.getFullSize(); i++) {
			ret.put(tb.getField(campoDescrizione, i), tb.getField(campoId, i));
		}

		return ret;
	}

	// TICKET ALM #2847
	/*********************************************************************************************************
	 * Restituisce una Hashtable con l'id del campo e la descrizione in base alla
	 * tabella specificata ed al campo di validita specificato se questo risulta
	 * maggiore della data attuale. Odinata in base alla descrizione
	 * 
	 * @param nomeTabella      Stringa con il noem della tabella
	 * @param campoId          String per l'id del campo
	 * @param campoDescrizione String per la Descrizione del campo
	 * @param campoValidita    String campo in base al quale si effettua la ricerca
	 * @param data             deve essere Timestamp or String, campo per gestire le
	 *                         tipologie non piu valide delle tipologiche ma valide
	 *                         al momento dell'inserimento dei dati (quindi view e
	 *                         validation afterwards)
	 * @return Map&lt;String, String&gt;
	 * @throws SQLException
	 *********************************************************************************************************/
	public Map<String, String> getTipologicaDescrWithData(String nomeTabella, String campoId, String campoDescrizione,
			String campoFineValidita, String campoInizioValidita, Object data, boolean isOrgano) throws SQLException {

		String ldata = null;

		if (data != null) {
			if (data instanceof String)
				ldata = (String) data;
			else
				ldata = PageHelper.getDBDateFromTS((Timestamp) data);
		}

		TableBean tb = executeSelectWithData(nomeTabella, campoInizioValidita, campoFineValidita, campoDescrizione,
				ldata, isOrgano);

		// lista con chiavi invertite
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
		for (int i = 0; i < tb.getFullSize(); i++) {
			ret.put(tb.getField(campoDescrizione, i), tb.getField(campoId, i));
		}

		return ret;
	}
	// FINE TICKET ALM #2847

	// TICKET ALM - 3.04.2 2005
	/*********************************************************************************************************
	 * Restituisce una Hashtable con l'id del campo e la descrizione in base alla
	 * tabella specificata ed al campo di validita specificato se questo risulta
	 * maggiore della data attuale. Odinata in base alla descrizione
	 * 
	 * @param nomeTabella      Stringa con il noem della tabella
	 * @param campoId          String per l'id del campo
	 * @param campoDescrizione String per la Descrizione del campo
	 * @param campoValidita    String campo in base al quale si effettua la ricerca
	 * @param data             deve essere Timestamp or String, campo per gestire le
	 *                         tipologie non piu valide delle tipologiche ma valide
	 *                         al momento dell'inserimento dei dati (quindi view e
	 *                         validation afterwards)
	 * @return Map&lt;String, String&gt;
	 * @throws SQLException
	 *********************************************************************************************************/
	public Map<String, String> getTipologicaDescrWithDP(String nomeTabella, String campoId, String campoDescrizione,
			String campoFineValidita, String campoInizioValidita, Object data, boolean isOrgano, String campoParam,
			String param) throws SQLException {

		String ldata = null;

		if (data != null) {
			if (data instanceof String)
				ldata = (String) data;
			else
				ldata = PageHelper.getDBDateFromTS((Timestamp) data);
		}

		TableBean tb = executeSelectWithDP(nomeTabella, campoInizioValidita, campoFineValidita, campoDescrizione, ldata,
				isOrgano, campoParam, param);

		// lista con chiavi invertite
		LinkedHashMap<String, String> ret = new LinkedHashMap<String, String>();
		for (int i = 0; i < tb.getFullSize(); i++) {
			ret.put(tb.getField(campoDescrizione, i), tb.getField(campoId, i));
		}

		return ret;
	}
	// FINE TICKET ALM - 3.04.2 2005

	/*********************************************************************************************************
	 * Restituisce una Hashtable con l'id del campo e la descrizione in base alla
	 * tabella specificata ed al campo di validita specificato se questo rusulta
	 * maggiore della data attuale.
	 * 
	 * il campo descrizione puo' essere il risultato di una concatenazione, l'alias
	 * serve per ritornarlo nella mappa
	 * 
	 * @param nomeTabella      Stringa con il noem della tabella
	 * @param campoId          String per l'id del campo
	 * @param campoDescrizione String per la Descrizione del campo
	 * @param campoValidita    String campo in base al quale si effettua la ricerca
	 * @param data             deve essere Timestamp or String, campo per gestire le
	 *                         tipologie non piu valide delle tipologiche ma valide
	 *                         al momento dell'inserimento dei dati (quindi view e
	 *                         validation afterwards)
	 * @param campoAlias       String nome dell'alias per la colonna descrizione
	 * @return Map&lt;String, String&gt;
	 * @throws SQLException
	 *********************************************************************************************************/
	public Map<String, String> getTipologicaAlias(String nomeTabella, String campoId, String campoDescrizione,
			String campoValidita, Object data, String campoAlias) throws SQLException {

		String fullSelect = "SELECT " + campoId + ", " + campoDescrizione + " as " + campoAlias + " FROM " + nomeTabella
				+ " WHERE " + buildISNULL(campoValidita, "99999999") + " >= ?" + " ORDER BY "
				+ buildISNULL(campoValidita, "99999999");

		PreparedStatement stmt = null;
		ResultSet rs = null;

		HashMap<String, String> tipologicaM = new HashMap<String, String>();

		try {
			stmt = activeConnection.prepareStatement(fullSelect);
			// il metodo del secondo argomento ritorna getnow formattato se il primo
			// parametro e'
			// nullo altrimenti il primo parametro formattato
			stmt.setObject(1, PageHelper.getFormattedNowOrInputFormattedDate(data, getNow()));
			rs = stmt.executeQuery();
			while (rs.next()) {
				tipologicaM.put(rs.getString(campoId), rs.getString(campoAlias));
			}
			return tipologicaM;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return tipologicaM;
		} finally {
			close(rs, stmt);
		}
	}

	/*********************************************************************************************************
	 * Restituisce una Hashtable con l'id del campo e la descrizione in base alla
	 * tabella specificata ed al campo di validita specificato se questo rusulta
	 * maggiore della data attuale.
	 * 
	 * @param nomeTabella      Stringa con il noem della tabella
	 * @param campoId          String per l'id del campo
	 * @param campoDescrizione String per la Descrizione del campo
	 * @param campoValidita    String campo in base al quale si effettua la ricerca
	 * @param data             deve essere Timestamp or String, campo per gestire le
	 *                         tipologie non piu valide delle tipologiche ma valide
	 *                         al momento dell'inserimento dei dati (quindi view e
	 *                         validation afterwards)
	 * @param where            da applicare alla select
	 * @return Map&lt;String, String&gt;
	 * @throws SQLException
	 *********************************************************************************************************/
	public Map<String, String> getTipologicaWhere(String nomeTabella, String campoId, String campoDescrizione,
			String campoValidita, Object data, String whereCond) throws SQLException {

		String fullSelect = "SELECT " + campoId + ", " + campoDescrizione + " FROM " + nomeTabella + " WHERE 1=1 ";

		if (whereCond != null)
			fullSelect = fullSelect + " AND " + whereCond;

		if (!SimogFlags.isFromMassLoader()) {
			fullSelect = fullSelect + " AND " + buildISNULL(campoValidita, "99999999") + " >= ?";
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;

		HashMap<String, String> tipologicaM = new HashMap<String, String>();

		try {
			stmt = activeConnection.prepareStatement(fullSelect);

			if (!SimogFlags.isFromMassLoader())
				stmt.setObject(1, PageHelper.getFormattedNowOrInputFormattedDate(data, getNow()));

			rs = stmt.executeQuery();

			while (rs.next()) {
				tipologicaM.put(rs.getString(campoId), rs.getString(campoDescrizione));
			}
			return tipologicaM;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return tipologicaM;
		} finally {
			close(rs, stmt);
		}
	}

	/**
	 * legge la tabella scelta contraente tenendo conto della gestione organi
	 * costituzionali
	 * 
	 * @param data
	 * @param isOrganoCost
	 * @return
	 * @throws SQLException
	 */
	private Map<String, String> loadSceltaContranteOrgCost(Object data, boolean isOrganoCost // pp organi costituzionali
	) throws SQLException {

		Map<String, String> tipologicaM = null;
		try {
			// TICKET ALM #2847
			if (SimogFlags.is3042Active()) {
				tipologicaM = getTipologicaWithData(SCELTA_CONTRAENTE.TABLE_NAME,
						SCELTA_CONTRAENTE.ID_SCELTA_CONTRAENTE, SCELTA_CONTRAENTE.DESCRIZIONE,
						SCELTA_CONTRAENTE.DATA_INIZIO_VALIDITA, SCELTA_CONTRAENTE.DATA_FINE_VALIDITA, data);
			} else {
				tipologicaM = getTipologica(SCELTA_CONTRAENTE.TABLE_NAME, SCELTA_CONTRAENTE.ID_SCELTA_CONTRAENTE,
						SCELTA_CONTRAENTE.DESCRIZIONE, SCELTA_CONTRAENTE.DATA_FINE_VALIDITA, data);
			}
			// FINE TICKET ALM #2847
			// se non e' organo costituzionale elimino la voce riservata (20)
			if (!isOrganoCost)
				tipologicaM.remove(Costanti.SCELTA_CONTRAENTE_OOCC);

			return tipologicaM;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return tipologicaM;
		}
	}

	/**
	 * TICKET 2846 metodo per il recupero del motivo collegamento
	 * 
	 * @param o Object deve essere un Timestamp o una String yyyymmdd
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public LinkedHashMap<String, String> loadMotivoCollegamento(Object o) throws SQLException {

//		return getTipologicaWithData(MOTIVO_COLLEGAMENTO.TABLE_NAME, MOTIVO_COLLEGAMENTO.ID_MOTIVO,
//				MOTIVO_COLLEGAMENTO.DESCRIZIONE, MOTIVO_COLLEGAMENTO.DATA_INIZIO_VALIDITA,
//				MOTIVO_COLLEGAMENTO.DATA_FINE_VALIDITA, o);

		return getMotivoCollegamentoWithData(MOTIVO_COLLEGAMENTO.TABLE_NAME, MOTIVO_COLLEGAMENTO.ID_MOTIVO,
				MOTIVO_COLLEGAMENTO.DESCRIZIONE, MOTIVO_COLLEGAMENTO.DATA_INIZIO_VALIDITA,
				MOTIVO_COLLEGAMENTO.DATA_FINE_VALIDITA, o);

	}

	/**
	 * TICKET 31047 metodo per il recupero del motivo deroga
	 * 
	 * @param o Object deve essere un Timestamp o una String yyyymmdd
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public Map<String, String> loadMotivoDeroga(Object o) throws SQLException {

		return getMotivoDerogaWithData(MOTIVO_DEROGA.TABLE_NAME, MOTIVO_DEROGA.ID_MOTIVO, MOTIVO_DEROGA.DESCRIZIONE,
				MOTIVO_DEROGA.DATA_INIZIO_VALIDITA, MOTIVO_DEROGA.DATA_FINE_VALIDITA, o);

	}

	public Map<String, String> loadMisuraPremiale(Object o) throws SQLException {

		return getMisuraPremialeWithData(MISURA_PREMIALE.TABLE_NAME, MISURA_PREMIALE.ID_MISURA,
				MISURA_PREMIALE.DESCRIZIONE, MISURA_PREMIALE.DATA_INIZIO_VALIDITA, MISURA_PREMIALE.DATA_FINE_VALIDITA,
				MISURA_PREMIALE.DATA_ULTIMA_MODIFICA);

	}

	/**
	 * metodo per il recupero della scelta contraente
	 * 
	 * @param o        Object deve essere un Timestamp o una String yyyymmdd
	 * @param isOrgano flag se organo cost
	 * @param idOss    id Osservatorio per personalizzazione scelta contraente se
	 *                 null nessuna personalizazione
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public Map<String, String> loadSceltaContraente(Object o, boolean isOrgano, String idOss) throws SQLException {

		// is3028RFWEBGL00Active()
		final String PERS_OP_MODIFICA = "M";
		final String PERS_OP_INSERISCI = "I";
		final String PERS_OP_CANCELLA = "C";

		if (!SimogFlags.is3028_RFWEBGL00Active()) {
			if (SimogFlags.isOrganiCostActive())
				return loadSceltaContranteOrgCost(o, isOrgano);
			else {
				// TICKET ALM #2847
				if (SimogFlags.is3042Active()) {
					return getTipologicaWithData(SCELTA_CONTRAENTE.TABLE_NAME, SCELTA_CONTRAENTE.ID_SCELTA_CONTRAENTE,
							SCELTA_CONTRAENTE.DESCRIZIONE, SCELTA_CONTRAENTE.DATA_INIZIO_VALIDITA,
							SCELTA_CONTRAENTE.DATA_FINE_VALIDITA, o);
				} else {
					return getTipologica(SCELTA_CONTRAENTE.TABLE_NAME, SCELTA_CONTRAENTE.ID_SCELTA_CONTRAENTE,
							SCELTA_CONTRAENTE.DESCRIZIONE, SCELTA_CONTRAENTE.DATA_FINE_VALIDITA, o);
				}
				// FINE TICKET ALM #2847
			}
		} else {
			// costruzione della lista in base alle personalizzazioni previste per
			// l'osservatorio
			Map<String, String> lista = null;
			Map<String, String> listaNew = new LinkedHashMap<String, String>();

			try {
				lista = loadSceltaContranteOrgCost(o, isOrgano);

				// se idOss non valorizzato provengo dalla creazione lotto e le scelte sono solo
				// quelle nazionali, parto dalla lista completa
				if (idOss == null || ProfiloEnum.REGIONE_ZERO.equals(idOss)) {
					// tolgo le voci presenti nelle personalizzazioni, sono comunque presenti nella
					// scelta_coontraente
					TableBean pers = executeSelectWhere(CONTRAENTE_REGIONE.TABLE_NAME,
							CONTRAENTE_REGIONE.DATA_FINE_VALIDITA, CONTRAENTE_REGIONE.ID_RECORD,
							PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()),
							CONTRAENTE_REGIONE.ID_EQUIVALENTE + " <> " + CONTRAENTE_REGIONE.ID_SCELTA_AVCP, null);

					// tolgo le voci referenzate nella personalizzazione
					for (int i = 0; i < pers.getFullSize(); i++) {
						TableBeanRow row = pers.getRow(i);
						String keyAVCP = row.getNulledField(CONTRAENTE_REGIONE.ID_SCELTA_AVCP);
						lista.remove(keyAVCP);
					}

					// costruisco la lista da restituire
					for (int i = 0; i < lista.keySet().size(); i++) {
						if (!listaNew.keySet().contains(lista.keySet().toArray()[i])) {
							listaNew.put((String) lista.keySet().toArray()[i], lista.get(lista.keySet().toArray()[i]));
						}
					}
				} else {
					// sono in scheda aggiudicazione devo costruire la lista personalizzata o quella
					// standard se non ci sono
					// personalizzazioni
					// leggo le voci personalizzate per l'osservatorio
					TableBean pers = executeSelectWhere(CONTRAENTE_REGIONE.TABLE_NAME,
							CONTRAENTE_REGIONE.DATA_FINE_VALIDITA, "1",
							PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()),
							CONTRAENTE_REGIONE.ID_OSSERVATORIO + " = '" + idOss + "'",
							"(case when id_scelta_avcp = id_equivalente then 1 else 0 end) as ord, id_scelta_avcp, id_equivalente, descrizione, data_fine_validita");

					if (pers.getFullSize() > 0) {

						for (int i = 0; i < pers.getFullSize(); i++) {
							TableBeanRow row = pers.getRow(i);
							String keyAVCP = row.getNulledField(CONTRAENTE_REGIONE.ID_SCELTA_AVCP);
							String descPERS = row.getNulledField(CONTRAENTE_REGIONE.DESCRIZIONE);
							listaNew.put(keyAVCP, "".equals(descPERS) ? lista.get(keyAVCP) : descPERS);
						}

						// metto le voci standard non personalizzate
						for (int i = 0; i < lista.keySet().size(); i++) {
							if (!listaNew.keySet().contains(lista.keySet().toArray()[i])) {
								listaNew.put((String) lista.keySet().toArray()[i],
										lista.get(lista.keySet().toArray()[i]));
							}
						}
					} else {
						// lista solo nazionali
						// tolgo le voci presenti nelle personalizzazioni, sono comunque presenti nella
						// scelta_coontraente
						TableBean pers2 = executeSelectWhere(CONTRAENTE_REGIONE.TABLE_NAME,
								CONTRAENTE_REGIONE.DATA_FINE_VALIDITA, CONTRAENTE_REGIONE.ID_RECORD,
								PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()),
								CONTRAENTE_REGIONE.ID_EQUIVALENTE + " <> " + CONTRAENTE_REGIONE.ID_SCELTA_AVCP, null);

						// tolgo le voci referenzate nella personalizzazione
						for (int i = 0; i < pers2.getFullSize(); i++) {
							TableBeanRow row = pers2.getRow(i);
							String keyAVCP = row.getNulledField(CONTRAENTE_REGIONE.ID_SCELTA_AVCP);
							lista.remove(keyAVCP);
						}

						// costruisco la lista da restituire
						for (int i = 0; i < lista.keySet().size(); i++) {
							if (!listaNew.keySet().contains(lista.keySet().toArray()[i])) {
								listaNew.put((String) lista.keySet().toArray()[i],
										lista.get(lista.keySet().toArray()[i]));
							}
						}

					}

					// elaboro la personalizzazione
//   	               for (int i = 0; i < pers.getFullSize(); i++) {
//                        TableBeanRow row = pers.getRow(i);
					// String op = row.getNulledField(CONTRAENTE_REGIONE.ID_OPERAZIONE);
//                        String keyAVCP = row.getNulledField(CONTRAENTE_REGIONE.ID_SCELTA_AVCP);
//                        String descPERS = row.getNulledField(CONTRAENTE_REGIONE.DESCRIZIONE);
//                        String keyPERS = row.getNulledField(CONTRAENTE_REGIONE.ID_RECORD);

//                        if(PERS_OP_CANCELLA.equals(op))
//                           lista.remove(keyAVCP);
//                        if(PERS_OP_MODIFICA.equals(op))
//                           lista.put(keyAVCP, descPERS);  // sostituzione della descrizione
//                        if(PERS_OP_INSERISCI.equals(op))
//                           lista.put(new Integer(idOss).toString().trim() + String.format("%03d", new Integer(keyPERS)), descPERS);
//   	               }

					// AGGIUNTA delle voci AVCP non presenti nella lista personalizzata
//   	               for (Iterator iterator = lista.keySet().iterator(); iterator.hasNext();) {
//                        String key = (String) iterator.next();
//                        if(pers.getColumn(CONTRAENTE_REGIONE.ID_SCELTA_AVCP).contains(key))
//                           listaNew.put(key, lista.get(key));
//                     }          
				}
			} catch (Exception e) {
				throw new SQLException(e);
			}
			return listaNew;
		}
	}

	// is3028_RFWEBGL00Active

	/**
	 * @param o data validità
	 * @param l chiave scelta contraente personalizzata XXyyy dove XX è il codice
	 *          osservatorio (senza zero iniziale) e yyy l'id record della voce
	 *          personalizzata
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public String getSceltaContraenteAVCP(Object o, long l) throws SQLException, Exception {

		String retVal = "0";

		TableBean pers = executeSelectWhere(CONTRAENTE_REGIONE.TABLE_NAME, CONTRAENTE_REGIONE.DATA_FINE_VALIDITA,
				CONTRAENTE_REGIONE.ID_SCELTA_AVCP, PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()),
				CONTRAENTE_REGIONE.ID_SCELTA_AVCP + " = " + String.format("%d", l), null);

		if (pers.getFullSize() > 0)
			retVal = pers.getNulledField(CONTRAENTE_REGIONE.ID_EQUIVALENTE, 0);
		else
			retVal = String.valueOf(l);
		return retVal;
	}

	/*************************************************************************************************
	 * Costruisce il token ISNULL in base al db corrente Costruisce una Stringa da
	 * inserire nella query per far si che se il campo specificato sia null, questo
	 * valore venga sostituito con quello dell'oggetto specificato nei parametri del
	 * metodo.
	 * 
	 * @param field     colonna della tabella
	 * @param nullValue valore da ritornare
	 **************************************************************************************************/
	public static String buildISNULL(String field, Object nullValue) {
		String retVal;
		String locNullValue = "";

		// se il valore di default e' stringa devo mettere gli apicetti
		if (nullValue instanceof String)
			locNullValue = "'" + nullValue + "'";
		else
			locNullValue = nullValue.toString();

		retVal = SimogProperties.getInstance().getDBMS().equalsIgnoreCase(ParametriServlet.MYSQL) ? "IFNULL" : "ISNULL";
		retVal += "(" + field + "," + locNullValue + ")";
		return retVal;
	}

	/*************************************************************************************************
	 * Costruisce il token ISNULL in base al db corrente Costruisce una Stringa da
	 * inserire nella query per far si che se il campo specificato sia null, questo
	 * valore venga sostituito con quello dell'oggetto specificato nei parametri del
	 * metodo.
	 * 
	 * @param field     colonna della tabella
	 * @param nullValue valore da ritornare
	 **************************************************************************************************/
	public static String buildISNULLHsqlDb(String field, Object nullValue) {
		String retVal;
		String locNullValue = "";

		// se il valore di default e' stringa devo mettere gli apicetti
		if (nullValue instanceof String)
			locNullValue = "'" + nullValue + "'";
		else
			locNullValue = nullValue.toString();

		retVal = "IFNULL";
		retVal += "(" + field + "," + locNullValue + ")";
		return retVal;
	}

	/*************************************************************************************************
	 * Costruisce il token per reperire la versione valida del CPV da usare nelle
	 * join
	 * 
	 * @param fieldCPV  colonna della tabella origine che ha id_cpv
	 * @param fieldDATA colonna della tabella origine che ha data di riferimento
	 **************************************************************************************************/
	public static String buildVersCPV(String fieldCPV, String fieldDATA) {
		String retVal;

		retVal = " (select min(" + CPVEU.VERSIONE + ") from " + CPVEU.TABLE_NAME + " where " + CPVEU.ID_DIV + " + "
				+ CPVEU.ID_GRP + " + " + CPVEU.ID_CLS + " + " + CPVEU.ID_CTG + " + " + CPVEU.ID_VOX + " +'-'+ "
				+ CPVEU.CHK + " = " + fieldCPV + " and " + buildISNULL(CPVEU.T_DATA_FINE_VALIDITA, "99999999") + " >= "
				+ fieldDATA + ")";

		return retVal;
	}

	/**
	 * Trova l'indice di dispersione deviazione standard della tabella Indice
	 * Dispersione in corrispondenza di un anno inserito Le informazioni sono
	 * contenute nella tabella INDICE_DISPERSIONE: Anno, Tipo Settore, Tipo
	 * Contratto, Indice, Data fine validita, Data ultima modifica
	 *
	 * @param anno
	 * @param tipoSettore
	 * @param tipoContratto
	 * @param data          in formato aaaa/mm/gg
	 * @return
	 * @throws SQLException
	 */
	protected BigDecimal getIndiceDispersioneByAnno(String anno, String tipoSettore, String tipoContratto, Object data)
			throws SQLException {

		BigDecimal result = null;

		String selectIndiceDispersioneByAnno = "SELECT " + INDICE_DISPERSIONE.INDICE + " FROM "
				+ INDICE_DISPERSIONE.TABLE_NAME + " WHERE " + INDICE_DISPERSIONE.ANNO + " = ? ";

		String selectIndiceDispersioneByAnnoSettore = selectIndiceDispersioneByAnno + " AND "
				+ INDICE_DISPERSIONE.TIPO_SETTORE + " = ? ";

		String selectIndiceDispersioneByAnnoSettoreContratto = selectIndiceDispersioneByAnnoSettore + " AND "
				+ INDICE_DISPERSIONE.TIPO_CONTRATTO + " = ? ";

		String whereFinale = " AND " + buildISNULL(INDICE_DISPERSIONE.DATA_FINE_VALIDITA, "99999999") + " >= ?"
				+ " ORDER BY " + buildISNULL(INDICE_DISPERSIONE.DATA_FINE_VALIDITA, "99999999");

		logger.debug(
				"ESECUZIONE Query [" + selectIndiceDispersioneByAnnoSettoreContratto + "] per anno [" + anno + "]");

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = activeConnection.prepareStatement(selectIndiceDispersioneByAnnoSettoreContratto + whereFinale);
			pstmt.setObject(1, anno);
			pstmt.setObject(2, tipoSettore);
			pstmt.setObject(3, tipoContratto);

			try {
				pstmt.setObject(4, PageHelper.getFormattedNowOrInputFormattedDate(data, getNow()));
			} catch (Exception e) {
				throw new SQLException(e.getMessage());
			}
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = (BigDecimal) rs.getObject(1);
			} else {
				close(rs, pstmt);
				pstmt = activeConnection.prepareStatement(selectIndiceDispersioneByAnnoSettore + whereFinale);
				pstmt.setObject(1, anno);
				pstmt.setObject(2, tipoSettore);
				try {
					pstmt.setObject(3, PageHelper.getFormattedNowOrInputFormattedDate(data, getNow()));
				} catch (Exception e) {
					throw new SQLException(e.getMessage());
				}

				rs = pstmt.executeQuery();

				if (rs.next()) {
					result = (BigDecimal) rs.getObject(1);
				} else {
					close(rs, pstmt);
					pstmt = activeConnection.prepareStatement(selectIndiceDispersioneByAnno + whereFinale);
					pstmt.setObject(1, anno);

					try {
						pstmt.setObject(2, PageHelper.getFormattedNowOrInputFormattedDate(data, getNow()));
					} catch (Exception e) {
						throw new SQLException(e.getMessage());
					}

					rs = pstmt.executeQuery();

					if (rs.next()) {
						result = (BigDecimal) rs.getObject(1);
					}
				}
			}
			logger.debug("Indice dispersione [" + result + "] -> anno/settore/contratto [" + anno + "/" + tipoSettore
					+ "/" + tipoContratto + "] ");
			return result;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, pstmt);
		}
	}

	/******************************************************************************************************
	 * Inserisce nel PreparedStatement, dalla posizione indicata da posCounter in
	 * poi, gli oggetti nella Hashtable
	 * 
	 * @param pstmt            : PreparedStatement
	 * @param posCounter       : int che determina l'idice da cui partire per
	 *                         l'inserimento degli oggetto contenuti nella Hashtable
	 * @param listaSAAbilitato : Hashtable
	 * @return PreparedStatement
	 * @throws SQLException
	 ******************************************************************************************************/
	protected PreparedStatement fillPstmt(PreparedStatement pstmt, int posCounter, Hashtable listaSAAbilitato)
			throws SQLException {
		if (listaSAAbilitato != null) {
			for (Enumeration e = listaSAAbilitato.keys(); e.hasMoreElements();) {
				String currentElement = (String) e.nextElement();
				logger.debug("Setting value [" + currentElement + "] at position[" + posCounter + "]");
				pstmt.setObject(posCounter++, currentElement);
			}
		}
		return pstmt;
	}

	protected PreparedStatement fillPstmt(PreparedStatement pstmt, int posCounter, Hashtable listaSAAbilitato,
			Hashtable ammUser) throws SQLException {
		if (listaSAAbilitato != null) {
			for (Enumeration e = listaSAAbilitato.keys(); e.hasMoreElements();) {
				String currentElement = (String) e.nextElement();
				logger.debug("Setting value [" + currentElement + "] at position[" + posCounter + "]");
				pstmt.setObject(posCounter++, currentElement);
			}
		}
		if (ammUser != null) {
			for (Enumeration e = ammUser.keys(); e.hasMoreElements();) {
				String currentElement = (String) e.nextElement();
				logger.debug("Setting value [" + currentElement + "] at position[" + posCounter + "]");
				pstmt.setObject(posCounter++, currentElement);
			}
		}
		return pstmt;
	}

	/********************************************************************************************************
	 * <code>protected String <b>getQueryConditionByCIGSommaUrgenza</b> ( String )</code><br>
	 * <br>
	 * Genera una stringa per le condizioni relative al CIG. Se il CIG inizia per 9
	 * imposta la ricerca di somma urgenza a "S" altrimenti a "N".
	 * 
	 * @param cig : Stringa contenente il CIG
	 * @return : Stringa relativa alla condizione da aggiunge ad una query per le
	 *         condizioni del CIG.
	 */
	protected String getQueryConditionByCIGSommaUrgenza(String cig) {

		String lCig = CIGBean.getRealCIG(cig);
		String cig_part = CIGBean.getCIGPart(lCig);
		String cig_KKK = CIGBean.getCIGKKK(lCig);
		// NEWCIG inutile String sommaUrgenza = isSommaUrgenza ? Costanti.FLAG_VALORE_SI
		// : Costanti.FLAG_VALORE_NO;

		// FIXME: PP attenzione cig_cicle fisso a zero!
		// String condizioni = LOTTO.T_CIG + "='" + cig_part + "' AND " +
		// LOTTO.T_CIG_CICLE + " = 0 AND " + LOTTO.T_CIG_KKK --> cig_cicle non gestito
		String condizioni = LOTTO.T_CIG + "='" + cig_part + "' AND " + LOTTO.T_CIG_KKK + " = '" + cig_KKK + "' "; // NEWCIG
																													// inutile
																													// AND
																													// "
																													// +
																													// LOTTO.T_SOMMA_URGENZA
																													// +
																													// "='"
																													// +
																													// sommaUrgenza
																													// +
																													// "'
																													// "
																													// ;

		return condizioni;
	}

	// legge la data dal DB
	/*******************************************************************************************************
	 * Ottiene il Timestamp della data attuale
	 * 
	 * @return Timestamp
	 *******************************************************************************************************/
	public Timestamp getNow() {

		Timestamp ora = new Timestamp((System.currentTimeMillis() / 1000) * 1000);

// debug per problemi sui lock, non prendo la data dal db 
		if (!SimogFlags.isDEBUG_GETDATE()) {
			try {
				Statement stmt = activeConnection.createStatement();
				ResultSet rs = null;
				if (SimogProperties.getInstance().getDBMS().equalsIgnoreCase(ParametriServlet.MYSQL))
					ora = stmt.executeQuery("SELECT now()").getTimestamp(0);
				else {
					rs = stmt.executeQuery("SELECT getdate() as ora");
					rs.next();
					ora = rs.getTimestamp("ora");
				}
				close(rs, stmt);

			} catch (Exception e) {
				logger.fatal("***** Errore durante select getdate(): " + e.getMessage());
			}
		}
		return ora;
	}

	/*******************************************************************************************************
	 * Ritorna il formato per prendere la data nel db utilizzato
	 * 
	 * @return String
	 *******************************************************************************************************/
	public static String buildGetDate() {
		String ora = "";
		if (SimogProperties.getInstance().getDBMS().equalsIgnoreCase(ParametriServlet.MYSQL))
			ora = " now() ";
		else
			ora = " getdate() ";

		return ora;
	}

	/**
	 * Metodo nato dalla necessita' di prevenire, in caso di inseriementi /
	 * aggiornamenti molto veloci, la duplicazione della componente della chiave
	 * primaria "data inizio", avendo il vincolo del secondo, ovvero come unita' di
	 * tempo minima, si e' pensato di aggiungere un secondo alla precedente data
	 * inizio.
	 * 
	 * @param dataInizio String : pur troppo abbiamo la data in formato stringa
	 *                   informato yyyyMMdd @see PageHelper.DEFAULT_DATE_FORMAT
	 * @return *** non usato
	 */
	@Deprecated
	public Timestamp getDatePlusOneSecond(String dataInizio) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(PageHelper.DEFAULT_DATE_FORMAT);
			java.util.Date d = sdf.parse(dataInizio);
			long oneSecond = 1000;
			Timestamp t = new Timestamp(d.getTime() + oneSecond);
			return t;
		} catch (java.text.ParseException pe) {
			logger.error("Errore durante il tentativo di conversione di una data in formato stringa in Timestamp");
			return null;
		}
	}

	/**
	 * Ritorna un Timestamp con un secondo in piu rispetto a quello passato
	 * 
	 * @param dataInizio
	 * @return Timestamp
	 */
	public Timestamp getDatePlusOneSecond(Timestamp dataInizio) {
		long oneSecond = 1000;
		Timestamp t = new Timestamp(dataInizio.getTime() + oneSecond);
		return t;

	}

	/**
	 * Compara due timestamp e determina se now e' successivo a oldDataInizio + 1
	 * secondo, in caso positivo ritorna now, altrimenti ritorna oldDataInizio + 1
	 * secondo
	 * 
	 * @param now
	 * @param oldDataInizio
	 * @return
	 */
	public Timestamp getTimestampPlusOneSecondIfInTheSameSecond(Timestamp now, Timestamp oldDataInizio) {
		// dato che LA PRECISIONE DI "oldDataInizio" E' DEL SECONDO con questo confronto
		// posso
		// determinare se il now si trova nel secondo successivo alla vecchia data
		// inizio

		Timestamp candidateToDataInizio = this.getDatePlusOneSecond(oldDataInizio);
		logger.info("oldData: " + PageHelper.formatTimeStamp(oldDataInizio) + ", candidate: "
				+ PageHelper.formatTimeStamp(candidateToDataInizio) + ", now: " + now);
		if (now.after(candidateToDataInizio)) {
			return now;
		}
		return candidateToDataInizio;
	}

	/***************************************************************************************************
	 * Chiude il resultset e lo statement
	 * 
	 * @param rs   : ResultSet
	 * @param stmt : Statment
	 ***************************************************************************************************/
	protected void close(ResultSet rs, Statement stmt) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				logger.debug(e.getMessage());
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				logger.debug(e.getMessage());
			}
		}
	}

	protected void close(CallableStatement cs) {
		if (cs != null) {
			try {
				cs.close();
				cs = null;
			} catch (SQLException e) {
				logger.debug(e.getMessage());
			}
		}
	}

	/*******************************************************************************************************
	 * Inserisce una richiesta di annullamento, risponde True se l'inserimento
	 * avviene, False altrimenti
	 * 
	 * @param blocco             : String
	 * @param cfUtente           : Stringa per il codice fiscale dell'utente
	 * @param motivazione        : String
	 * @param id_record          : Stringa per l'ID del record
	 * @param data_inizio_record : data di inizio del record
	 * @param idLotto            : Stringa per l'id del lotto
	 * @return Boolean : true se e' stata inserita la richiesta False altrimenti
	 * @throws SQLException
	 *******************************************************************************************************/
	public boolean richiediAnnullamento(RichiestaAnnullamento bean) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String queryinsert = "INSERT INTO " + RICHIESTA_ANNULLAMENTO.TABLE_NAME + " ("
				+ RICHIESTA_ANNULLAMENTO.DATA_INIZIO + "," + RICHIESTA_ANNULLAMENTO.BLOCCO + ","
				+ RICHIESTA_ANNULLAMENTO.ID_RECORD + "," + RICHIESTA_ANNULLAMENTO.DATA_INIZIO_RECORD + ","
				+ RICHIESTA_ANNULLAMENTO.RICHIEDENTE + "," + RICHIESTA_ANNULLAMENTO.MOTIVO_RICHIESTA + ","
				+ RICHIESTA_ANNULLAMENTO.ID_LOTTO + "," + RICHIESTA_ANNULLAMENTO.CANCELLAZIONE
				+ (SimogFlags.is30230_RFWEBSC03Active() ? "," + RICHIESTA_ANNULLAMENTO.ID_MOTIVO_RICH : "") + " ) "
				+ " VALUES ( ?,?,?,?,?,?,?,?" + (SimogFlags.is30230_RFWEBSC03Active() ? ",?" : "") + " )";
		try {
			int index = 1;
			stmt = activeConnection
					.prepareStatement(createInsertQuery(queryinsert, RICHIESTA_ANNULLAMENTO.ID_RICHIESTA));
			stmt.setObject(index++, getNow());
			stmt.setString(index++, bean.getBlocco());
			stmt.setInt(index++, Integer.parseInt(bean.getId_record()));
			stmt.setObject(index++, bean.getData_inizio_record());
			stmt.setString(index++, bean.getRichiedente());
			stmt.setString(index++, bean.getMotivo_richiesta());
			stmt.setString(index++, bean.getId_lotto());

			if (bean.getCancellazione() == null)
				stmt.setNull(index++, Types.VARCHAR);
			else
				stmt.setString(index++, bean.getCancellazione());

			if (SimogFlags.is30230_RFWEBSC03Active()) {
				if (bean.getIdMotivo() == null)
					stmt.setNull(index++, Types.VARCHAR);
				else
					stmt.setString(index++, bean.getIdMotivo());
			}

			stmt.execute();
			rs = stmt.getResultSet();
			rs.next();
			long idRich = rs.getLong(RICHIESTA_ANNULLAMENTO.ID_RICHIESTA); // FIXME: *!*!*!*! PP non compatibile Mysql
																			// DA FARE SU TUTTE LE QUERY CHE USANO
																			// createInsertQUery : PP per mysql leva
																			// rs.next() e metti long idGara =
																			// ((com.mysql.jdbc.PreparedStatement)
																			// stmt).getLastInsertID();
			bean.setId_richiesta(idRich); // ritorno id

			// int num = stmt.executeUpdate();
			// return (num>0);
			return idRich > 0;
		} finally {
			close(rs, stmt);
		}
	}

	/***********************************************************************************************
	 * Ritorna il blocco query da inserire in una select per ottenere la descrizione
	 * della eventuale presenza di una richiesta di annullamento
	 *
	 * @param blocco    String dati
	 * @param campoId   id del blocco dati usato nella query di select
	 * @param campoData
	 * @return String contenente la query
	 */
	public String buildRichAnnQuery(String blocco, String campoId, String campoData) {
		String QUERY_ESISTE_ANNULLAMENTO = " isnull((" + " select case isnull(" + RICHIESTA_ANNULLAMENTO.CANCELLAZIONE
				+ ", 'X') + ltrim(str(count(1))) " + " when 'X1' then '" + PSBD.MSG_RICHIESTO_ANNULLAMENTO + "' "
				+ " when 'N1' then '" + PSBD.MSG_RICHIESTA_CANCELLAZIONE + "' " + " when 'S1' then '"
				+ PSBD.MSG_RICHIESTA_CANC_TOTALE + "' " + " else '' end " + " from " + RICHIESTA_ANNULLAMENTO.TABLE_NAME
				+ " where " + RICHIESTA_ANNULLAMENTO.T_ESITO + " is null and " + RICHIESTA_ANNULLAMENTO.T_BLOCCO
				+ " = '" + blocco + "' and " + RICHIESTA_ANNULLAMENTO.T_ID_RECORD + " = " + campoId;

		if (campoData != null) {
			QUERY_ESISTE_ANNULLAMENTO = QUERY_ESISTE_ANNULLAMENTO + " and "
					+ RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD + " = " + campoData;
		}
		QUERY_ESISTE_ANNULLAMENTO = QUERY_ESISTE_ANNULLAMENTO + " group by " + RICHIESTA_ANNULLAMENTO.CANCELLAZIONE;
		QUERY_ESISTE_ANNULLAMENTO = QUERY_ESISTE_ANNULLAMENTO + "),'') ";

		return QUERY_ESISTE_ANNULLAMENTO;
	}

	/***********************************************************************************************
	 * Ritorna il blocco query da inserire in una select per ottenere la descrizione
	 * della eventuale presenza di una richiesta di annullamento
	 *
	 * @param blocco    String dati
	 * @param campoId   id del blocco dati usato nella query di select
	 * @param campoData
	 * @return String contenente la query
	 */
	public String buildRichAnnQueryMult(String[] blocco, String campoId, String campoData) {
		String QUERY_ESISTE_ANNULLAMENTO = " isnull((" + " select case isnull(" + RICHIESTA_ANNULLAMENTO.CANCELLAZIONE
				+ ", 'X') + ltrim(str(count(1))) " + " when 'X1' then '" + PSBD.MSG_RICHIESTO_ANNULLAMENTO + "' "
				+ " when 'N1' then '" + PSBD.MSG_RICHIESTA_CANCELLAZIONE + "' " + " when 'S1' then '"
				+ PSBD.MSG_RICHIESTA_CANC_TOTALE + "' " + " else '' end " + " from " + RICHIESTA_ANNULLAMENTO.TABLE_NAME
				+ " where " + RICHIESTA_ANNULLAMENTO.T_ESITO + " is null and " + RICHIESTA_ANNULLAMENTO.T_BLOCCO
				+ " in :blk and " + RICHIESTA_ANNULLAMENTO.T_ID_RECORD + " = " + campoId;

		if (campoData != null) {
			QUERY_ESISTE_ANNULLAMENTO = QUERY_ESISTE_ANNULLAMENTO + " and "
					+ RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD + " = " + campoData;
		}
		QUERY_ESISTE_ANNULLAMENTO = QUERY_ESISTE_ANNULLAMENTO + " group by " + RICHIESTA_ANNULLAMENTO.CANCELLAZIONE;
		QUERY_ESISTE_ANNULLAMENTO = QUERY_ESISTE_ANNULLAMENTO + "),'') ";
		String setStr = "(";
		for (String s : blocco) {
			if (setStr.length() > 1)
				setStr += ("," + "'" + s + "'");
			else
				setStr += ("'" + s + "'");
		}
		setStr += ")";

		return QUERY_ESISTE_ANNULLAMENTO.replace(":blk", setStr);
	}

	/******************************************************************************************************
	 * imposta una nuova data per un nuovo record identificato tramite ID e Data
	 * Inizio
	 * 
	 * @param QUERY_REIMPOSTA_DATA : Stringa contenente la query per reimpostare la
	 *                             data
	 * @param idrecord             : String per l'ID del record
	 * @param datainiziorecord     : Timestamp contenente la data di inizio del
	 *                             record
	 * @param datavecchia          : Timestamp contenente la vecchia data
	 * @throws SQLException
	 */
//	 PP non usata	public void reimpostaData(String QUERY_REIMPOSTA_DATA, String idrecord, Timestamp datainiziorecord, Timestamp datavecchia) throws SQLException{
//		PreparedStatement stmt = null;
//		try {
//			stmt = activeConnection.prepareStatement(QUERY_REIMPOSTA_DATA);
//			logger.debug("Reimposta data, query ["+QUERY_REIMPOSTA_DATA+"]");
//			logger.debug("1: "+datavecchia);
//			logger.debug("2: "+idrecord);
//			logger.debug("3: "+datainiziorecord);
//			stmt.setObject(1, datavecchia);
//			stmt.setObject(2, idrecord);
//			stmt.setObject(3,datainiziorecord);
//			stmt.executeUpdate();
//		}finally {
//			close(null, stmt);
//		}
//	}

	/**********************************************************************************************
	 * controlla il campo di semaforo. Se contiene off risponde False, True
	 * altrimenti
	 * 
	 * @return boolean
	 */
	public boolean checkService() {

		return true;

//		 PP commentata per non pesare sulle prestazioni		
//		Statement stmt = null;
//		ResultSet rs = null;
//		boolean retVal = false;
//		
//		try{
//			String query = "SELECT campo FROM semaforo";
//			stmt = activeConnection.createStatement();
//			logger.debug("checking Service");
//			rs = stmt.executeQuery(query);
//			if(rs.next()){
//				String campo = rs.getString("campo");
//				campo = campo.trim().toLowerCase().substring(0,3);
//				
//				retVal = !"off".equals(campo);
//			}
//			return retVal;
//		}
//		catch(Exception e){
//		logger.fatal(e.getMessage());
////			e.printStackTrace();
//			return false;
//		}
//		finally{
//			close(rs, stmt);
//		}
	}

	/**********************************************************************************
	 * Imposta il campo del semaforo a 'unlock'
	 */
	public void enableService() {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String update = "UPDATE semaforo set campo = 'unlock'";
			stmt = activeConnection.createStatement();
			logger.debug("checking Service");
			stmt.executeUpdate(update);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
//			e.printStackTrace();			
		} finally {
			close(rs, stmt);
		}
	}

	/*************************************************************************************
	 * Imposta il campo di semaforo a 'off'
	 */
	public void disableService() {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String update = "UPDATE semaforo set campo = 'off'";
			stmt = activeConnection.createStatement();
			logger.debug("checking Service");
			stmt.executeUpdate(update);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
//			e.printStackTrace();			
		} finally {
			close(rs, stmt);
		}
	}

	/**********************************************************************************************
	 * Genera una stringa capace di eseguire la query e cambiare la nomenclatura
	 * dell'ultimo id inserito
	 *
	 * @param query         : String
	 * @param identityField : String
	 * @return String
	 */
	public String createInsertQuery(String query, String identityField) {
		if (SimogProperties.getInstance().getDBMS().equalsIgnoreCase(ParametriServlet.MYSQL))
			return query + "; SELECT LAST_INSERT_ID() AS " + identityField + ";";
		else
			return "SET NOCOUNT ON;" + query + ";SELECT SCOPE_IDENTITY() AS " + identityField;
	}

	public String createCopyRecord(String query, String table) {
		if (SimogProperties.getInstance().getDBMS().equalsIgnoreCase(ParametriServlet.MYSQL))
			return query;
		else
			return "SET IDENTITY_INSERT " + table + " ON;" + query;

	}

	/*******************************************************************************************
	 * Ritorna il campo di un ResultSet in Integer
	 * 
	 * @param rs        : ResulSet
	 * @param fieldName : String per il nome del campo
	 * @return Integer
	 * @throws SQLException
	 */
	public Integer getIntegerFromRS(ResultSet rs, String fieldName) throws SQLException {
		try {
			Integer returnValue = null;
			Object o = rs.getObject(fieldName);
			if (o != null)
				returnValue = new Integer(String.valueOf(o));
			return returnValue;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			return null;
		}
	}

	/*********************************************************************************************
	 * <code>public Long <b>getLongFromRS</b>(ResultSet, String)<pre>throws SQLException</pre></code>
	 * Restituisce il Long associato al campo del ResulSet indicato dal parametro in
	 * ingresso fieldName
	 * 
	 * @param rs
	 * @param fieldName
	 * @return Long associato al campo specificato
	 * @throws SQLException
	 */
	public Long getLongFromRS(ResultSet rs, String fieldName) throws SQLException {
		try {
			Long returnValue = null;
			Object o = rs.getObject(fieldName);
			if (o != null)
				returnValue = (Long) o;
			return returnValue;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			return null;
		}
	}

	/***********************************************************************************************
	 * <code>public SchedaState <b>isEmptyRs</b>(String,String, long, Timestamp) <pre>throws SQLException</pre></code>
	 * restituisce la SchedaState della tabella indicata
	 * 
	 * @param tableName                : Stringa per il nome della tabella
	 * @param idName                   : Stringa per il nome dell'id
	 * @param idAggiudicazione         : Stringa per l'id dell'aggiudicazione
	 * @param dataInizioAggiudicazione : Timestamp per la data di inizio
	 *                                 dell'aggiudicazione puo' essere nulla
	 *                                 (modifca fatta per il massloader)
	 * @param idField                  Stringa del nome campo per id
	 * @param dateField                Stringa del nome campo per data inizio
	 * @return SchedaState
	 * @throws SQLException
	 */
	public SchedaState isEmptyRs(String tableName, String idName, long id, Timestamp dataInizio, String idField,
			String dateField) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT min(" + STATI_SCHEDA.ID_STATO + "), max(" + STATI_SCHEDA.ID_STATO + ")," + idName
				+ " FROM " + tableName + " WHERE " + idField + " =? ";
		query += dateField == null ? "" : " AND " + dateField + " =? ";
		query += " AND " + STATI_SCHEDA.ID_STATO + " in (" + StatiScheda.IN_DEFINIZIONE + ","
				+ StatiScheda.ANNULLAMENTO_RICHIESTA + " , " + StatiScheda.CONFERMATO + ")" + " group by " + idName;

		this.logger.debug("isEmptyRs: query: " + query);
		try {
			stmt = activeConnection.prepareStatement(query);
			stmt.setLong(1, id);
			if (dateField != null)
				stmt.setTimestamp(2, dataInizio);

			rs = stmt.executeQuery();
			if (rs.next())
				return new SchedaState(rs.getInt(1), rs.getInt(2) == StatiScheda.ANNULLAMENTO_RICHIESTA, rs.getLong(3));
			else
				return new SchedaState(-1, false, new Long(-1));
		} finally {
			close(rs, stmt);
		}

	}

	/*****************************************************************************************************
	 * restituisce il nome del blocco per il nome tabella DB indicato
	 * 
	 * @param value String
	 * @return String
	 */
	private static String returnBlockName(String value) {

		HashMap map = new HashMap();

		map.put(AGGIUDICAZIONI.TABLE_NAME, IdentificativoSchede.TAB_AGGIUDICAZIONE);
		map.put(INFO_AGGIUDICAZIONI.TABLE_NAME, IdentificativoSchede.TAB_INFO_COMUNI);
		map.put(INIZIO_LAVORI.TABLE_NAME, IdentificativoSchede.TAB_INIZIO_LAVORI);
		map.put(STIPULA.TABLE_NAME, IdentificativoSchede.TAB_STIPULA);
		map.put(STATI_AVANZ.TABLE_NAME, ParametriServletAvanzamento.TAB_AVANZAMENTO);
		map.put(FINE_LAVORI.TABLE_NAME, ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI);
		map.put(COLLAUDO.TABLE_NAME, ParametriServletCollaudo.TAB_SCHEDA_COLLAUDO);
		map.put(ACCORDI.TABLE_NAME, ParametriServletAccordo.TAB_SCHEDA_ACCORDO);
		map.put(SOSPENSIONI.TABLE_NAME, ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI);
		map.put(SUBAPPALTI.TABLE_NAME, ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI);
		map.put(VARIANTI.TABLE_NAME, ParametriServletVariante.TAB_SCHEDA_VARIANTE);
		map.put(R129.TABLE_NAME, ParametriServletR129.TAB_SCHEDA_R129);

		String tableName = map.get(value).toString();

		return tableName;
	}

	/***********************************************************************************************
	 * <code>public Boolean <b>checkRichDelete</b>(String,String, long, Timestamp) <pre>throws SQLException</pre></code>
	 * restituisce true se la scheda attiva ha una richiesta di cancellazione in
	 * corso
	 * 
	 * @param tableName  : Stringa per il nome della tabella
	 * @param id         : long per id dell'aggiudicazione
	 * @param dataInizio : Timestamp per la data di inizio dell'aggiudicazione
	 * @param idField    Stringa del nome campo per id della scheda
	 * @param dateField  Stringa del nome campo per data inizio
	 * @return null, se non ci sono cancellazioni in corso, N per cancellazione
	 *         semplice, S per cancellazione totale
	 * @throws SQLException
	 */
	public String checkRichDelete(String tableName, long id, Timestamp dataInizio, String idField, String dateField)
			throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String blocco = returnBlockName(tableName);

		String query = "SELECT " + RICHIESTA_ANNULLAMENTO.CANCELLAZIONE + " FROM " + tableName + " join "
				+ AGGIUDICAZIONI.TABLE_NAME + "   on " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " = " + tableName + "."
				+ AGGIUDICAZIONI.ID_AGGIUDICAZIONE + "      and " + AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " = "
				+ tableName + "." + AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE + " join "
				+ RICHIESTA_ANNULLAMENTO.TABLE_NAME + "   on " + RICHIESTA_ANNULLAMENTO.BLOCCO + " = '" + blocco + "' "
				+ "      and " + RICHIESTA_ANNULLAMENTO.T_ID_RECORD + " = " + tableName + "." + idField + "      and "
				+ RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD + " = " + tableName + "." + dateField + "      and "
				+ RICHIESTA_ANNULLAMENTO.CANCELLAZIONE + " is not null " + "      and " + RICHIESTA_ANNULLAMENTO.T_ESITO
				+ " is null " + " where " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " =? AND "
				+ AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE + " =? " + " AND " + tableName + "."
				+ STATI_SCHEDA.ID_STATO + " in (" + StatiScheda.IN_DEFINIZIONE + "," + StatiScheda.CONFERMATO + ")";

		try {
			stmt = activeConnection.prepareStatement(query);
			stmt.setLong(1, id);
			stmt.setTimestamp(2, dataInizio);
			rs = stmt.executeQuery();
			if (rs.next())
				return rs.getString(RICHIESTA_ANNULLAMENTO.CANCELLAZIONE);
			else
				return null;
		} catch (SQLException e) {
			// introdotto il catching perche altrimenti l'eccezione viene "mascherata" da
			// errore di reflection
			// in caso di invocazione in una quadro di reflection
			e.printStackTrace();
			throw e;
		} finally {
			close(rs, stmt);
		}

	}

	/***********************************************************************************************
	 * restituisce true se la scheda ha una richiesta di cancellazione in corso
	 * questa versione controlla lo stato della singola scheda, viene usato solo per
	 * le multiple e per i dati comuni
	 * 
	 * @param tableName  : Stringa per il nome della tabella
	 * @param id         : long per l'id della scheda
	 * @param dataInizio : Timestamp per la data di inizio
	 * @param idField    Stringa del nome campo per id della scheda
	 * @param dateField  Stringa del nome campo per data inizio
	 * @return null, se non ci sono cancellazioni in corso, N per cancellazione
	 *         semplice, S per cancellazione totale
	 * @throws SQLException
	 */
	public String checkRichDeleteSingola(String tableName, long id, Timestamp dataInizio, String idField,
			String dateField) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String blocco = returnBlockName(tableName);

		String query = "SELECT " + RICHIESTA_ANNULLAMENTO.CANCELLAZIONE + " FROM " + tableName + " join "
				+ RICHIESTA_ANNULLAMENTO.TABLE_NAME + "   on " + RICHIESTA_ANNULLAMENTO.BLOCCO + " = '" + blocco + "' "
				+ "      and " + RICHIESTA_ANNULLAMENTO.T_ID_RECORD + " = " + tableName + "." + idField + "      and "
				+ RICHIESTA_ANNULLAMENTO.T_DATA_INIZIO_RECORD + " = " + tableName + "." + dateField + "      and "
				+ RICHIESTA_ANNULLAMENTO.CANCELLAZIONE + " is not null " + "      and " + RICHIESTA_ANNULLAMENTO.T_ESITO
				+ " is null " + " where " + tableName + "." + idField + " =? ";

		query += dataInizio == null ? "" : " AND " + tableName + "." + dateField + " =? ";

		query += " AND " + tableName + "." + STATI_SCHEDA.ID_STATO + " in (" + StatiScheda.IN_DEFINIZIONE + ","
				+ StatiScheda.CONFERMATO + ")";

		try {
			stmt = activeConnection.prepareStatement(query);
			stmt.setLong(1, id);
			if (dataInizio != null)
				stmt.setTimestamp(2, dataInizio);
			rs = stmt.executeQuery();
			if (rs.next())
				return rs.getString(RICHIESTA_ANNULLAMENTO.CANCELLAZIONE);
			else
				return null;
		} catch (SQLException e) {
			// introdotto il catching perche altrimenti l'eccezione viene "mascherata" da
			// errore di reflection
			// in caso di invocazione in una quadro di reflection
			e.printStackTrace();
			throw e;
		} finally {
			close(rs, stmt);
		}

	}

	private static String QUERY_LOAD_RUOLI_SEZIONE = "SELECT DISTINCT " + RUOLI_RESPONSABILE.T_ID_RUOLO + ", "
			+ RUOLI_RESPONSABILE.DESCRIZIONE + " FROM " + RUOLI_RESPONSABILE.TABLE_NAME + " " + "JOIN "
			+ RUOLI_RESP_SCHEDA.TABLE_NAME + " " + "ON " + RUOLI_RESPONSABILE.T_ID_RUOLO + " = "
			+ RUOLI_RESP_SCHEDA.T_ID_RUOLO + " WHERE " + RUOLI_RESP_SCHEDA.ID_SCHEDA + " = ?" + " AND "
			+ buildISNULL(RUOLI_RESPONSABILE.T_DATA_FINE_VALIDITA, "99999999") + " >= ?" + " AND "
			+ buildISNULL(RUOLI_RESP_SCHEDA.T_DATA_FINE_VALIDITA, "99999999") + " >= ?";

	/*************************************************************************************************
	 * <code>public Map< String,String > <b>loadRuoliSezione</b>(String) <pre>throws SQLException</pre></code>
	 * Genera una Map dei Ruoli in base alla sezione indicata secondo Id Ruolo e
	 * Descrizione.
	 * 
	 * @param sezione String
	 * @param o       deve essere Timestamp o String [yyyymmdd] per l'estensione
	 *                della validita' di una tipologia a posteriori
	 * @return Map
	 * @throws SQLException
	 */
	public Map<String, String> loadRuoliSezione(String sezione, Object o) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		HashMap<String, String> ris = new HashMap<String, String>();

		try {
			stmt = activeConnection.prepareStatement(QUERY_LOAD_RUOLI_SEZIONE);
			int index = 1;
			stmt.setString(index++, sezione);
			stmt.setObject(index++, PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()));
			stmt.setObject(index++, PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()));
			logger.debug(QUERY_LOAD_RUOLI_SEZIONE + "[PARAMETERS] - ("
					+ PageHelper.getFormattedNowOrInputFormattedDate(o, getNow()) + sezione + ")");
			rs = stmt.executeQuery();
			while (rs.next()) {
				ris.put(rs.getString(RUOLI_RESPONSABILE.ID_RUOLO), rs.getString(RUOLI_RESPONSABILE.DESCRIZIONE));
			}
			return ris;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ris;
		} finally {
			close(rs, stmt);
		}
	}

	/**
	 * Creato per gestire gli apici nel campo di ricerca per gestire token
	 * contenenti apostrofo
	 */

	private String getCleanToken(String currentToken) {
		/*
		 * if ( currentToken.contains("\'") ) { int apostrophePosition =
		 * currentToken.indexOf("'"); String firstPart = currentToken.substring(0,
		 * apostrophePosition); String second =
		 * currentToken.substring(apostrophePosition + 1); currentToken = firstPart +
		 * "''" + second; }
		 */
		currentToken = currentToken.replace("'", "''");
		return currentToken;
	}

	/**
	 * metodo per l'estrazione della parte cig dal cig+kkk e il replace del primo
	 * carattere che potrebbe essere impostato a 9 per la somma urgenza
	 * 
	 * @param fullCIG
	 * @return
	 */

// NEWCIG
	private String replaceSommaUrgenza(String fullCIG) {
		try {
			if (fullCIG == null) {
				throw new Exception("Il fullCIG risulta essere nullo");
			} else if (fullCIG.trim().length() > 10 || fullCIG.trim().length() < 10) {
				throw new Exception("Il fullCIG non risulta avere la lughezza richiesta");
			} else {
				fullCIG = CIGBean.getCIGPart(fullCIG);
			}
			return fullCIG;
		} catch (Exception e) {
			logger.error(e.getMessage());
			fullCIG = "";
			return fullCIG;
		}
	}

	protected String getIdInfo(String fullCIG) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		long id_info = 0;
		try {
//		    logger.debug("[input] - fullcig: "+fullCIG);
			String CIG = this.replaceSommaUrgenza(fullCIG);
//	    	logger.debug("[input] - cig: "+CIG);
			if (CIG.equals("")) {
				return CIG;
			}
			String sql = "select " + INFO_AGGIUDICAZIONI.ID_INFO + " from " + INFO_AGGIUDICAZIONI.TABLE_NAME + " where "
					+ INFO_AGGIUDICAZIONI.CIG + " =? " + " AND ( " + INFO_AGGIUDICAZIONI.ID_STATO + " = "
					+ StatiScheda.IN_DEFINIZIONE_STRING + " OR " + INFO_AGGIUDICAZIONI.ID_STATO + " = "
					+ StatiScheda.CONFERMATO_STRING + " ) ";

			ps = activeConnection.prepareStatement(sql);
			ps.setString(1, CIG);
			rs = ps.executeQuery();

			if (rs.next()) {
				id_info = rs.getLong(INFO_AGGIUDICAZIONI.ID_INFO);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, ps);
		}
		return Long.toString(id_info);
	}

	/********************************************************************************************************
	 * Controlla se esistono schede dipendenti da quella per cui e' stata richiesta
	 * la cancellazione
	 * 
	 * @param blocco           String
	 * @param idAggiudicazione long
	 * @param dataInizioAgg    Timestamp
	 * @param tipoSettore      String
	 * @param tipoContratto    String
	 * @return boolean
	 * @throws SimogException
	 */
	public boolean thereAreDependencies(String blocco, long idAggiudicazione, Timestamp dataInizioAgg,
			String tipoSettore, String tipoContratto) throws SimogException {

		boolean retVal = false;

		// FIXME: *!*!*!*!*! PP se cambia il flusso occorre rivedere questo metodo !

		try {
			// in base al settore della scheda
			if (Costanti.TIPO_ENTE_SPECIALE.equals(tipoSettore)) {

				// settore speciale
				// dati comuni -> aggiudicazione
				if (IdentificativoSchede.TAB_INFO_COMUNI.equals(blocco)) {
					retVal = statoAggiudicazione(idAggiudicazione, dataInizioAgg).getState() > 0;
				} else if (IdentificativoSchede.TAB_AGGIUDICAZIONE.equals(blocco)) {
					retVal = statoConclusione(idAggiudicazione, dataInizioAgg).getState() > 0;
				}
			} else {

				// settore ordinario
				/** FLUSSO PRINCIPALE **/
				// dati comuni -> aggiudicazione
				if (IdentificativoSchede.TAB_INFO_COMUNI.equals(blocco)) {
					retVal = statoAggiudicazione(idAggiudicazione, dataInizioAgg).getState() > 0;
				}
				// aggiudicazione -> fase iniziale o conclusione
				// XXX: review -> Non esistono inizio,conclusione,r129,subappalti,variante
				// gm aggiunto anche il flusso per la scheda adesione
				else if (IdentificativoSchede.TAB_AGGIUDICAZIONE.equals(blocco)
						|| IdentificativoSchede.TAB_ADESIONE.equals(blocco)) {
					retVal = statoInizio(idAggiudicazione, dataInizioAgg).getState() > 0
							|| statoConclusione(idAggiudicazione, dataInizioAgg).getState() > 0
							|| statoR129(idAggiudicazione, dataInizioAgg).getState() > 0
							|| statoSubappalto(idAggiudicazione, dataInizioAgg).getState() > 0
							|| statoVariante(idAggiudicazione, dataInizioAgg).getState() > 0;
				}
				// inizio -> stati avanzamento o conclusione
				// XXX: review -> Non esistono avanzamento, accordi, sospensione, fine lavori
				else if (IdentificativoSchede.TAB_INIZIO_LAVORI.equals(blocco)) {
					retVal = statoAvanzamento(idAggiudicazione, dataInizioAgg).getState() > 0
							|| statoConclusione(idAggiudicazione, dataInizioAgg).getState() > 0
							|| statoAccordi(idAggiudicazione, dataInizioAgg).getState() > 0
							|| statoSospensioni(idAggiudicazione, dataInizioAgg).getState() > 0;
				}
				// stati avanzamento -> nessuna dipendenza reale
				// XXX: review -> Non esiste Conclusione
				else if (ParametriServletAvanzamento.TAB_AVANZAMENTO.equals(blocco)) {
					retVal = statoConclusione(idAggiudicazione, dataInizioAgg).getState() > 0;
				}
				// conclusione -> collaudo
				else if (ParametriServletConclusioni.TAB_SCHEDA_CONCLUSIONI.equals(blocco)) {
					retVal = statoCollaudo(idAggiudicazione, dataInizioAgg).getState() > 0;
				}
				// collaudo sempre cancellabile (false che e' il valore con il quale e'
				// istanziata la variabile quindi OK)

				/** FLUSSO LATERALE **/

				// Non esiste Collaudo
				else if (ParametriServletAccordo.TAB_SCHEDA_ACCORDO.equals(blocco)) {
					retVal = statoCollaudo(idAggiudicazione, dataInizioAgg).getState() > 0;
				}
				// Non esiste inizio lavori
				else if (ParametriServletR129.TAB_SCHEDA_R129.equals(blocco)) {
					retVal = statoInizio(idAggiudicazione, dataInizioAgg).getState() > 0;
				}
				// Non esiste Conclusione
				else if (ParametriServletSospensioni.TAB_SCHEDA_SOSPENSIONI.equals(blocco)) {
					retVal = statoConclusione(idAggiudicazione, dataInizioAgg).getState() > 0;
				}
				// Non esiste Collaudo
				else if (ParametriServletSubappalti.TAB_SCHEDA_SUBAPPALTI.equals(blocco)) {
					retVal = statoCollaudo(idAggiudicazione, dataInizioAgg).getState() > 0;
				}
				// Non esiste Collaudo
				else if (ParametriServletVariante.TAB_SCHEDA_VARIANTE.equals(blocco)) {
					retVal = statoCollaudo(idAggiudicazione, dataInizioAgg).getState() > 0;
				} else if (IdentificativoSchede.TAB_STIPULA.equals(blocco)) {
					retVal = statoConclusione(idAggiudicazione, dataInizioAgg).getState() > 0;

				}
			}

			return retVal;

		} catch (Exception e) {
			logger.fatal(e);
			throw new SimogException("WorkFlowController: Errore imprevisto -> ", e);
		}
	}

	public SchedaState statoInizio(Long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException {
		return (isEmptyRs(INIZIO_LAVORI.TABLE_NAME, INIZIO_LAVORI.ID_INIZIO, idAggiudicazione, dataInizioAggiudicazione,
				AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
	}

	public SchedaState statoStipula(Long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException {
		return (isEmptyRs(STIPULA.TABLE_NAME, STIPULA.ID_STIPULA, idAggiudicazione, dataInizioAggiudicazione,
				AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
	}

	public SchedaState statoAvanzamento(Long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException {
		return (isEmptyRs(STATI_AVANZ.TABLE_NAME, STATI_AVANZ.ID_AGGIUDICAZIONE, idAggiudicazione,
				dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
	}

	public SchedaState statoConclusione(Long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException {
		return (isEmptyRs(FINE_LAVORI.TABLE_NAME, FINE_LAVORI.ID_ULTIM, idAggiudicazione, dataInizioAggiudicazione,
				AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
	}

	public SchedaState statoCollaudo(Long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException {
		return (isEmptyRs(COLLAUDO.TABLE_NAME, COLLAUDO.ID_COLLAUDO, idAggiudicazione, dataInizioAggiudicazione,
				AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
	}

	public SchedaState statoR129(Long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException {
		return (isEmptyRs(R129.TABLE_NAME, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione, dataInizioAggiudicazione,
				AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
	}

	public SchedaState statoAccordi(Long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException {
		return (isEmptyRs(ACCORDI.TABLE_NAME, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione,
				dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
	}

	public SchedaState statoSospensioni(Long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException {
		return (isEmptyRs(SOSPENSIONI.TABLE_NAME, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione,
				dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
	}

	public SchedaState statoSubappalto(Long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException {
		return (isEmptyRs(SUBAPPALTI.TABLE_NAME, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione,
				dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
	}

	public SchedaState statoVariante(Long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException {
		return (isEmptyRs(VARIANTI.TABLE_NAME, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, idAggiudicazione,
				dataInizioAggiudicazione, AGGIUDICAZIONI.ID_AGGIUDICAZIONE, AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
	}

	public SchedaState statoAggiudicazione(Long idInfo, Timestamp dataInizioInfo) throws SQLException {
		return (isEmptyRs(AGGIUDICAZIONI.TABLE_NAME, AGGIUDICAZIONI.ID_INFO, idInfo, dataInizioInfo,
				INFO_AGGIUDICAZIONI.ID_INFO, INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO));
	}

	/** @deprecated */
	private int isolation_level;
	/** @deprecated */
	private boolean read_only;
	/** @deprecated */
	private boolean called;

	/**
	 * Metodi nati per essere sicuri delle caratteristiche dalla connessione. Per il
	 * ritorno dei parametri della connessione a come e' stata fornita @see
	 * rollbackConnectionConfig(Connection con)
	 * 
	 * @param con
	 * @throws SQLException
	 * @deprecated
	 */
	protected void prepareConnectionForSelect(Connection con) throws SQLException {
		this.isolation_level = con.getTransactionIsolation();
		this.read_only = con.isReadOnly();
		con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		con.setReadOnly(true);
		this.called = true;
	}

	/**
	 * Ritorna la connessione alla configurazione che e' stata passata al
	 * metodo @see prepareConnectionForSelect(Connection con)
	 * 
	 * @param con
	 * @throws SQLException
	 * @deprecated
	 */
	protected void rollbackConnectionConfig(Connection con) throws SQLException {
		if (this.called) {
			con.setTransactionIsolation(this.isolation_level);
			con.setReadOnly(this.read_only);
		}
	}

	/**
	 * aggiornamento campi gara e lotto se modificati su infoComuni
	 * 
	 * @param conn
	 * @param logger
	 * @param bean
	 * @throws Exception
	 */
	public void updateGaraLotto(Connection conn, Logger logger, InfoComuniBean bean) throws Exception {
		GaraManager gm = new GaraManager(conn, logger);
		LottoManager lm = new LottoManager(conn, logger);

		Lotto lotto = lm.getLotto(bean.getIdLotto());

		gm.updateCampiInfoComuni(bean, lotto.getId_Gara());

		// TICKET ALM - 3.04.2 2005
		// Non cambiare i campi del lotto
		if (!SimogFlags.is3042Active()) {
			lm.updateCampiInfoComuni(bean);			
		}
		
		//ticket #31057 parte 1
		if(StatiScheda.CONFERMATO == bean.getIdStato() && EsitoEnum.ANNULLATA_PRIMA.codice().equals(bean.getEsitoProcedura())) {
			lotto.setImporto_Impresa(new BigDecimal(0));
			lm.modificaLotto(lotto);
		}
	}

	/**
	 * verifica se esiste una colla in un un resultset
	 * 
	 * @param rs
	 * @param colName
	 * @return
	 */
	public boolean existCol(ResultSet rs, String colName) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();

			// Get the column names; column indices start from 1
			for (int i = 1; i < numColumns + 1; i++) {
				if (rsmd.getColumnName(i).equals(colName))
					return true;
			}
		} catch (SQLException e) {
		}
		return false;
	}

	public Logger getLogger() {
		return logger;
	}

	public boolean isPubblicabile(String sceltaContraente, BigDecimal lottoImporto, String tipoContratto) {
		boolean pubblicabile = false;

		try {
			String scelta = sceltaContraente;
			LottoManager lm = new LottoManager(activeConnection, logger);
			// ricavo la scelta contraente equivalente
			if (SimogFlags.is3028_RFWEBGL00Active()) {
				scelta = lm.getSceltaContraenteAVCP(null, Long.valueOf(sceltaContraente));
			}

			int idSceltaContraente = Integer.parseInt(scelta);
			if (idSceltaContraente == Costanti.PROC_APE || idSceltaContraente == Costanti.PROC_RIS
					|| idSceltaContraente == Costanti.DIA_COMP || idSceltaContraente == Costanti.PROC_NEG_PP
					|| idSceltaContraente == Costanti.PROC_RIS_AVVISI || idSceltaContraente == Costanti.PROC_RIS_SEMP
					//MAD 42789 3.04.9.2
					|| idSceltaContraente == Costanti.PROC_COMP_NEG || idSceltaContraente == Costanti.PAT_INN) // Ticket
				pubblicabile = true; // ALM
										// #648.
										// Modalita'
										// Contraente
										// pubblicabili

		} catch (Exception e) {
			pubblicabile = pubblicabile || false;
		}
		/*
		 * gm controllo non richiesto nelle specifiche 1.4 try{
		 * if(Costanti.TIPO_SCHEDA_LAVORI.equals(tipoContratto) && lottoImporto != null
		 * && lottoImporto.floatValue()>=Costanti.IMPORTO_LOTTO_500000) pubblicabile =
		 * true; } catch (Exception e){ pubblicabile = pubblicabile || false; }
		 */
		return pubblicabile;
	}
}