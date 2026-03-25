package it.avlp.simog.transazioni;

import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.ESATTORECANALEPAGAMENTO;
import it.avlp.simog.db.generated.ESATTORESTATOPAGAMENTO;
import it.avlp.simog.db.generated.ESATTORETIPOUTENZA;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.PAGAMENTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class TransazioniManager extends AccessiDB { 
	
	public static int MAX_TRANSAZIONI = 1000;
	
	private static boolean order = false;
	
	
//	private final String selectLottiPubblicati = "SELECT "
//		+ PAGAMENTO.ID_PAGAMENTO
//		+ ", "
//		+ LOTTO.T_IMPORTO_LOTTO
//		+ ", "
//		+ LOTTO.T_ID_LOTTO
//		+ ", "
//		+ GARA.T_ID_GARA
//		+ ", "
//		+ GARA.T_CF_AMMINISTRAZIONE
//		+ ", "
//		+ LOTTO.T_OGGETTO
//		+ ", "
//		+ LOTTO.T_CIG + "+" + LOTTO.T_CIG_KKK + " AS " + LOTTO.CIG
//		+ ", "
//		+ GARA.T_DENOM_AMMINISTRAZIONE
//		+ ", "
//		+ GARA.T_DENOM_STAZIONE_APPALTANTE
//		+ ", "
//		+ GARA.T_OGGETTO
//		+ ", "
//		+ LOTTO.T_OGGETTO + " AS " + LOTTO.TABLE_NAME+LOTTO.OGGETTO
//		+ " FROM "
//		+ GARA.TABLE_NAME
//		+ ", "
//		+ LOTTO.TABLE_NAME
//		+ " LEFT JOIN " + PAGAMENTO.TABLE_NAME + " ON " + LOTTO.T_ID_LOTTO +  "=" + PAGAMENTO.T_ID_LOTTO
//		+ " WHERE "
//		+ LOTTO.T_ID_GARA + "=" + GARA.T_ID_GARA
//		+ " and " + LOTTO.DATA_PUBBLICAZIONE + " IS NOT NULL";
	
	public TransazioniManager(Connection connection, Logger logger){
		super(connection, logger);
	}
	
	/**
	 * Utilizzato dall'RSSA
	 */
	
	
	/****************************************************************************************
	 * Ottiene la TableBean della Transazioni
	 * @param idLotto String
	 * @param cfAmministrazione  String
	 * @param orderBy String
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getTransazioni( String idLotto, String cfAmministrazione, String orderBy ) 
		throws SQLException {
		
		String orderMode = order ? " ASC " : " DESC ";
		order = ! order;
		
		orderBy = orderBy != null ? orderBy : PAGAMENTO.ID_PAGAMENTO;
		

		String INFO_PAGAMENTO_BY_ID_LOTTO =
			"SELECT 'CONTRIBUTO LOTTO' AS TIPO, "
			+ PAGAMENTO.T_CIG_LOTTO
			+ ", " + LOTTO.T_OGGETTO + " AS OGGETTO_LOTTO "
			+ ", " + PAGAMENTO.T_DATA_PAGAMENTO + " AS DATA_PAGAMENTO "
			+ ", " + ESATTORECANALEPAGAMENTO.DESCRIZIONE_CANALE_PAGAMENTO + " AS " + ParametriServlet.CANALE_PAGAMENTO
			+ ", " + PAGAMENTO.T_ID_SA_RIFERIMENTO + " AS CF_CONTRIBUENTE "
			+ ", " + PAGAMENTO.T_CODICE_TRANSAZIONE
			+ ", " + PAGAMENTO.T_DATA_CONTABILIZZAZIONE
			+ ", " + PAGAMENTO.T_IMPORTO_PAGATO
			+ ", " + ESATTORESTATOPAGAMENTO.T_DESCRIZIONE_STATO_PAGAMENTO + " AS " + ParametriServlet.STATO_PAGAMENTO
			+ ", " + ESATTORETIPOUTENZA.T_DESCRIZIONE_TIPO_UTENZA + " AS " + ParametriServlet.TIPO_UTENZA
			+ " FROM "
			+ PAGAMENTO.TABLE_NAME
			+ ", " + LOTTO.TABLE_NAME
			+ ", " + ESATTORECANALEPAGAMENTO.TABLE_NAME
			+ ", " + ESATTORESTATOPAGAMENTO.TABLE_NAME
			+ ", " + ESATTORETIPOUTENZA.TABLE_NAME
			+ " WHERE "
			+ LOTTO.T_ID_LOTTO + "=" + idLotto 
			+ " AND " + LOTTO.T_ID_LOTTO + "=" + PAGAMENTO.T_ID_LOTTO 
			+ " AND " + PAGAMENTO.T_CANALE_PAGAMENTO + "=" + ESATTORECANALEPAGAMENTO.T_ID_CANALE_PAGAMENTO
			+ " AND " + PAGAMENTO.T_STATO_PAGAMENTO + "=" + ESATTORESTATOPAGAMENTO.T_ID_STATO_PAGAMENTO
			+ " AND " + PAGAMENTO.T_TIPO_UTENZA + "=" + ESATTORETIPOUTENZA.T_ID_TIPO_UTENZA;
		
			if ( ! "".equalsIgnoreCase( orderBy ) ) {
				INFO_PAGAMENTO_BY_ID_LOTTO += " ORDER BY " + orderBy + orderMode;
			} else {
				INFO_PAGAMENTO_BY_ID_LOTTO += " ORDER BY " + PAGAMENTO.ID_PAGAMENTO;
			}
		
			logger.debug( "Ricerca transazioni per LOTTO ID[" + idLotto + "] query [" + INFO_PAGAMENTO_BY_ID_LOTTO + "]" );
		
			Statement st = activeConnection.createStatement();
			ResultSet rs = st.executeQuery(INFO_PAGAMENTO_BY_ID_LOTTO);
			TableBean tb = new TableBean(rs);		
			close(rs,st);
			return tb;
	}
	
	
	/****************************************************************************************
	 * Ottiene la TableBean della Transazioni per gara
	 * @param idGara String
	 * @param cfAmministrazione  String
	 * @param orderBy String
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getTransazioniGara( String idGara, String cfAmministrazione, String orderBy ) 
		throws SQLException {
		
		String orderMode = order ? " ASC " : " DESC ";
		order = ! order;
		
		orderBy = orderBy != null ? orderBy : PAGAMENTO.ID_PAGAMENTO;
		

		String INFO_PAGAMENTO_BY_ID_GARA =
			"SELECT 'CONTRIBUTO GARA' AS TIPO, "
			+ PAGAMENTO.T_ID_GARA
			+ ", " + GARA.T_OGGETTO + " AS OGGETTO_GARA "
			+ ", " + PAGAMENTO.T_DATA_PAGAMENTO + " AS DATA_PAGAMENTO "
			+ ", " + ESATTORECANALEPAGAMENTO.DESCRIZIONE_CANALE_PAGAMENTO + " AS " + ParametriServlet.CANALE_PAGAMENTO
			+ ", " + PAGAMENTO.T_ID_SA_RIFERIMENTO + " AS CF_CONTRIBUENTE "
			+ ", " + PAGAMENTO.T_CODICE_TRANSAZIONE
			+ ", " + PAGAMENTO.T_DATA_CONTABILIZZAZIONE
			+ ", " + PAGAMENTO.T_IMPORTO_PAGATO
			+ ", " + ESATTORESTATOPAGAMENTO.T_DESCRIZIONE_STATO_PAGAMENTO + " AS " + ParametriServlet.STATO_PAGAMENTO
			+ ", " + ESATTORETIPOUTENZA.T_DESCRIZIONE_TIPO_UTENZA + " AS " + ParametriServlet.TIPO_UTENZA
			+ " FROM "
			+ PAGAMENTO.TABLE_NAME
			+ ", " + GARA.TABLE_NAME
			+ ", " + ESATTORECANALEPAGAMENTO.TABLE_NAME
			+ ", " + ESATTORESTATOPAGAMENTO.TABLE_NAME
			+ ", " + ESATTORETIPOUTENZA.TABLE_NAME
			+ " WHERE "
			+ GARA.T_ID_GARA + "=" + idGara
			+ " AND " + GARA.T_ID_GARA + "=" + PAGAMENTO.T_ID_GARA 
			+ " AND " + PAGAMENTO.ID_LOTTO + " is NULL "
			+ " AND " + PAGAMENTO.T_CANALE_PAGAMENTO + "=" + ESATTORECANALEPAGAMENTO.T_ID_CANALE_PAGAMENTO
			+ " AND " + PAGAMENTO.T_STATO_PAGAMENTO + "=" + ESATTORESTATOPAGAMENTO.T_ID_STATO_PAGAMENTO
			+ " AND " + PAGAMENTO.T_TIPO_UTENZA + "=" + ESATTORETIPOUTENZA.T_ID_TIPO_UTENZA;
		
			if ( ! "".equalsIgnoreCase( orderBy ) ) {
				INFO_PAGAMENTO_BY_ID_GARA += " ORDER BY " + orderBy + orderMode;
			} else {
				INFO_PAGAMENTO_BY_ID_GARA += " ORDER BY " + PAGAMENTO.ID_PAGAMENTO;
			}
		
			logger.debug( "Ricerca transazioni per GARA ID[" + idGara + "] query [" + INFO_PAGAMENTO_BY_ID_GARA + "]" );
		
			Statement st = activeConnection.createStatement();
			ResultSet rs = st.executeQuery(INFO_PAGAMENTO_BY_ID_GARA);
			TableBean tb = new TableBean(rs);		
			close(rs,st);
			return tb;
	}
	
	
	
	/********************************************************************
	 * Ricerca i lotti in base ai parametri in ingreso
	 * @param cfSARiferimento String per il codice fiscale SA di riferimento
	 * @param cfRSSA String peril codice fiscale di RSSA
	 * @param cigLotto String per il CIG del lotto
	 * @param cfAmministrazione String per il codice fiscale Amministratore
	 * @param fromDataPub String per la data "da data pubblicazione"
	 * @param toDataPub String per la data "a data pubblicazione"
	 * @param fromDataScad Stringa per la data "da data scadenza"
	 * @param toDataScad Stringa per la data "a data scadenza"
	 * @return TableBean
	 * @throws SQLException
	 */
//	public TableBean getListaLotti (
//			String cfSARiferimento,
//			String cfRSSA,
//			String cigLotto,
//			String cfAmministrazione,
//			
//			// filtri temporali
//			String fromDataPub,
//			String toDataPub,
//			
//			String fromDataScad,
//			String toDataScad ) throws SQLException {
//		/*
//		 * SELECT LOTTO.CIG+LOTTO.CIG_KKK AS CIG_LOTTO, 
//		 * LOTTO.OGGETTO AS OGGETTO_LOTTO, 
//		 * '-' AS DATA_PAGAMENTO, 
//		 * '-' AS CANALE, 
//		 * '-' AS CF_CONTRIBUENTE, 
//		 * '-' AS CODICE_TRANSAZIONE, 
//		 * '-' AS DATA_CONTABILIZZAZIONE, 
//		 * '-' AS DATA_PAGAMENTO, 
//		 * '-' AS IMPORTO_PAGATO, 
//		 * 'NON PAGATO' AS STATO, 
//		 * '-' AS TIPO_UTENZA 
//		 * FROM LOTTO 
//		 * WHERE LOTTO.DATA_PUBBLICAZIONE BETWEEN '20060101' AND '20070109' AND LOTTO.DATA_SCADENZA_PAGAMENTI BETWEEN '20060101' AND '20300109'
//		 * */
//		
//		String queryLottiPubblicati = "SELECT "
//			+ GARA.DENOM_AMMINISTRAZIONE + " AS AMMINISTRAZIONE"
//			+ ", " + LOTTO.CIG+LOTTO.CIG_KKK + " AS CIG_LOTTO"
//			+ ", " + LOTTO.OGGETTO + " AS OGGETTO_LOTTO"
//			+ ", " + "'-' AS " + PAGAMENTO.DATA_PAGAMENTO
//			+ ", " + "'-' AS CANALE"
//			+ ", " + "'-' AS CF_CONTRIBUENTE"
//			+ ", " + "'-' AS CODICE_TRANSAZIONE"
//			+ ", " + "'-' AS " + PAGAMENTO.DATA_CONTABILIZZAZIONE
//// PP			+ ", " + "'-' AS " + PAGAMENTO.DATA_PAGAMENTO
//			+ ", " + "'-' AS " + PAGAMENTO.IMPORTO_PAGATO
//			+ ", " + "'NON PAGATO' AS STATO"
//			+ ", " + "'-' AS TIPO_UTENZA"
//			+ " FROM " + LOTTO.TABLE_NAME
//			+ ", " + GARA.TABLE_NAME			
//			+ " WHERE "
//			+ LOTTO.T_DATA_PUBBLICAZIONE + " BETWEEN '" + fromDataPub + "' AND '" + toDataPub + "'"
//			+ " AND "
//			+ LOTTO.T_DATA_SCADENZA_PAGAMENTI + " BETWEEN '" + fromDataScad + "' AND '" + toDataScad + "'"
//			+ " AND " + GARA.T_ID_GARA + " = " + LOTTO.T_ID_GARA;
//
//		if( ! "".equalsIgnoreCase( cfSARiferimento ) ) {
//			queryLottiPubblicati += " AND " + GARA.T_ID_STAZIONE_APPALTANTE + " = '" + cfSARiferimento + "'";
//		}
//		
//		if ( ! "".equalsIgnoreCase( cfRSSA ) ) {
//			queryLottiPubblicati += " AND " + GARA.T_CF_UTENTE + " = '" + cfRSSA + "'";
//		}
//		
//		if ( ! "".equalsIgnoreCase( cigLotto ) ) {
//			queryLottiPubblicati += " AND " + PAGAMENTO.T_CIG_LOTTO + "= '" + cigLotto + "'";
//		}
//		
//		if ( ! "".equalsIgnoreCase( cfAmministrazione ) ) {
//			queryLottiPubblicati += " AND " + GARA.T_CF_AMMINISTRAZIONE + " = '" + cfAmministrazione + "'";
//		}
//
//		logger.debug( "Ricerca Lista lotti pubblicati query [" + queryLottiPubblicati + "]" );
//
//		Statement st = null;
//		try {
//			 st = activeConnection.createStatement();
//			 return new TableBean(st.executeQuery(queryLottiPubblicati));			
//		} finally {
//			try {
//				st.close();
//			} catch ( Exception e ) {}
//			st = null;
//		}
//	}
//	

//	/*****************************************************
//	 * Ottiene la TableBean relativa alle transazioni 
//	 * ricercate attraverso i parametri in ingresso
//	 * 
//	 * @param cfSARiferimento String per il codice fiscale SA di riferimento
//	 * @param cfRSSA String per il codice fiscale RSSA
//	 * @param cigLotto String per il CIG del Lotto
//	 * @param cfAmministrazione String per codice fiscale Amministrazione
//	 * @param fromDataPub String per la data "da data pubblicazione"
//	 * @param toDataPub String per la data "a data pubblicazione"
//	 * @param fromDataScad String per la data "da data scadenza"
//	 * @param toDataScad String per la data "a data di scadenza"
//	 * @param orderBy String che determina la colonna utilizzata per l'ordinamento della lista
//	 * @return
//	 * @throws SQLException
//	 */
//	public TableBean getTransazioni(
//			// filtri nominali
//			String cfSARiferimento,
//			String cfRSSA,
//			String cigLotto,
//			String cfAmministrazione,
//			
//			// filtri temporali
//			String fromDataPub,
//			String toDataPub,
//			
//			String fromDataScad,
//			String toDataScad,
//			
//			String orderBy)
//			// Nome Colonna
//			
//			throws SQLException {		
//		
//		orderBy = orderBy != null ? orderBy : PAGAMENTO.ID_PAGAMENTO;
//		String query =
//			"SELECT "
//			+ GARA.DENOM_AMMINISTRAZIONE + " AS AMMINISTRAZIONE"
//			+ ", " + PAGAMENTO.T_CIG_LOTTO
//			+ ", " + LOTTO.T_OGGETTO + " AS OGGETTO_LOTTO "
//			+ ", " + PAGAMENTO.T_DATA_PAGAMENTO + " AS DATA_PAGAMENTO "
//			+ ", " + ESATTORECANALEPAGAMENTO.DESCRIZIONE_CANALE_PAGAMENTO + " AS " + ParametriServlet.CANALE_PAGAMENTO
//			+ ", " + PAGAMENTO.T_ID_SA_RIFERIMENTO + " AS CF_CONTRIBUENTE "
//			+ ", " + PAGAMENTO.T_CODICE_TRANSAZIONE + " AS " + PAGAMENTO.CODICE_TRANSAZIONE
//			+ ", " + PAGAMENTO.T_DATA_CONTABILIZZAZIONE + " AS " + PAGAMENTO.DATA_CONTABILIZZAZIONE
//// PP			+ ", " + PAGAMENTO.T_DATA_PAGAMENTO + " AS " + PAGAMENTO.DATA_PAGAMENTO
//			+ ", " + PAGAMENTO.T_IMPORTO_PAGATO + " AS " + PAGAMENTO.IMPORTO_PAGATO
//			+ ", " + ESATTORESTATOPAGAMENTO.T_DESCRIZIONE_STATO_PAGAMENTO + " AS " + ParametriServlet.STATO_PAGAMENTO
//			+ ", " + ESATTORETIPOUTENZA.T_DESCRIZIONE_TIPO_UTENZA + " AS " + ParametriServlet.TIPO_UTENZA
//			+ " FROM "
//			+ PAGAMENTO.TABLE_NAME
//			+ ", " + LOTTO.TABLE_NAME
//			+ ", " + GARA.TABLE_NAME
//			+ ", " + ESATTORECANALEPAGAMENTO.TABLE_NAME
//			+ ", " + ESATTORESTATOPAGAMENTO.TABLE_NAME
//			+ ", " + ESATTORETIPOUTENZA.TABLE_NAME
//			+ " WHERE "
//			+ LOTTO.T_DATA_PUBBLICAZIONE + " BETWEEN '" + fromDataPub + "' AND '" + toDataPub + "'"
//			+ " AND " + LOTTO.T_DATA_SCADENZA_PAGAMENTI + " BETWEEN '" + fromDataScad + "' AND '" + toDataScad + "'" 
//			+ " AND " + PAGAMENTO.T_CIG_LOTTO + " = " + LOTTO.T_CIG + "+" + LOTTO.T_CIG_KKK
//			+ " AND " + PAGAMENTO.T_ID_LOTTO + "=" + LOTTO.T_ID_LOTTO
//			+ " AND " + GARA.T_ID_GARA + " = " + LOTTO.T_ID_GARA
//			+ " AND " + PAGAMENTO.T_CANALE_PAGAMENTO + "=" + ESATTORECANALEPAGAMENTO.T_ID_CANALE_PAGAMENTO
//			+ " AND " + PAGAMENTO.T_STATO_PAGAMENTO + "=" + ESATTORESTATOPAGAMENTO.T_ID_STATO_PAGAMENTO
//			+ " AND " + PAGAMENTO.T_TIPO_UTENZA + "=" + ESATTORETIPOUTENZA.T_ID_TIPO_UTENZA;
//		
//		if( ! "".equalsIgnoreCase( cfSARiferimento ) ) {
//			query += " AND " + GARA.T_ID_STAZIONE_APPALTANTE + " = '" + cfSARiferimento + "'";
//		}
//		
//		if ( ! "".equalsIgnoreCase( cfRSSA ) ) {
//			query += " AND " + GARA.T_CF_UTENTE + " = '" + cfRSSA + "'";
//		}
//		
//		if ( ! "".equalsIgnoreCase( cigLotto ) ) {
//			query += " AND " + PAGAMENTO.T_CIG_LOTTO + "= '" + cigLotto + "'";
//		}
//		
//		if ( ! "".equalsIgnoreCase( cfAmministrazione ) ) {
//			query += " AND " + GARA.T_CF_AMMINISTRAZIONE + " = '" + cfAmministrazione + "'";
//		}
//		
//		/*
//		if ( ! "".equalsIgnoreCase( orderBy ) ) {
//			query += " ORDER BY " + orderBy + " ASC ";
//		} else {
//			query += " ORDER BY " + PAGAMENTO.ID_PAGAMENTO;
//		}
//		*/
//		logger.debug( "Ricerca transazioni [" + query + "]" );
//		
//		Statement st = null;
//		try {
//			 st = activeConnection.createStatement();
//
//			 return new TableBean(st.executeQuery(query));			
//		} finally {
//			try {
//				st.close();
//			} catch ( Exception e ) {}
//			st = null;
//		}
//	}
	
	
	/**
	 * Restituisce le transazioni in base ai filtri di ricerca
	 * forniti.
	 * Nel caso in cui sia richiesta la lista
	 * dei lotti pubblicati ma che non abbiano ricevuto pagamenti
	 * effettua una UNION
	 */
	
	public TableBean getFullTransazioni(
			// filtri nominali
			String cfSARiferimento,
			String cfRSSA,
			String cigLotto,
			String cfAmministrazione,
			String idGara,
			
			// filtri temporali
			String fromDataPub, 
			String toDataPub,	
			
			String fromDataScad, 
			String toDataScad, 
			
			String orderBy,
			boolean union)
			// Nome Colonna
			
			throws SQLException {		
		
		orderBy = orderBy != null ? orderBy : PAGAMENTO.ID_PAGAMENTO;

		String querygiusta =
			// sezione gare
			"SELECT 'CONTRIBUTO GARA' AS TIPO "
			+ ", null AS " + LOTTO.ID_LOTTO
			+ ", " + GARA.T_DENOM_AMMINISTRAZIONE + " AS AMMINISTRAZIONE"
			+ ", " + GARA.T_ID_GARA 
			+ ", null " + " AS CIG "
			+ ", " + GARA.T_OGGETTO + " AS OGGETTO "
			+ ", " + PAGAMENTO.T_DATA_PAGAMENTO + " AS DATA_PAGAMENTO "
			+ ", " + ESATTORECANALEPAGAMENTO.T_DESCRIZIONE_CANALE_PAGAMENTO + " AS " + ParametriServlet.CANALE_PAGAMENTO
			+ ", " + PAGAMENTO.T_ID_SA_RIFERIMENTO + " AS CF_CONTRIBUENTE "
			+ ", " + PAGAMENTO.T_CODICE_TRANSAZIONE + " AS " + PAGAMENTO.CODICE_TRANSAZIONE
			+ ", " + PAGAMENTO.T_DATA_CONTABILIZZAZIONE + " AS " + PAGAMENTO.DATA_CONTABILIZZAZIONE
			+ ", " + PAGAMENTO.T_IMPORTO_PAGATO + " AS " + PAGAMENTO.IMPORTO_PAGATO
			+ ", " + ESATTORESTATOPAGAMENTO.T_DESCRIZIONE_STATO_PAGAMENTO + " AS " + ParametriServlet.STATO_PAGAMENTO
			+ ", " + ESATTORETIPOUTENZA.T_DESCRIZIONE_TIPO_UTENZA + " AS " + ParametriServlet.TIPO_UTENZA
			+ " FROM "
			+ GARA.TABLE_NAME + " left join "+PAGAMENTO.TABLE_NAME+" on "+PAGAMENTO.T_ID_GARA + "=" + GARA.T_ID_GARA  
			+" left join "+ESATTORECANALEPAGAMENTO.TABLE_NAME+" on "+PAGAMENTO.T_CANALE_PAGAMENTO+" = "+ESATTORECANALEPAGAMENTO.T_ID_CANALE_PAGAMENTO
			+" left join "+ESATTORESTATOPAGAMENTO.TABLE_NAME+" on "+PAGAMENTO.T_STATO_PAGAMENTO+" = "+ESATTORESTATOPAGAMENTO.T_ID_STATO_PAGAMENTO
			+" left join "+ESATTORETIPOUTENZA.TABLE_NAME+" on "+PAGAMENTO.T_TIPO_UTENZA+" = "+ESATTORETIPOUTENZA.T_ID_TIPO_UTENZA
			+" where "
			+GARA.T_DATA_CONFERMA_GARA + " BETWEEN ? AND ?"
			+ " AND " + GARA.T_DATA_TERMINE_PAGAMENTO + " BETWEEN ? AND ? ";
		
		//		se si cerca una transazione relativa a una specifica stazione appaltante
		if( ! "".equalsIgnoreCase( cfSARiferimento ) ) {
			querygiusta += " AND " + GARA.T_ID_STAZIONE_APPALTANTE + " = ? ";
		}
		
		//se si cerca una transazione relativa a uno specifico RSSA
		if ( ! "".equalsIgnoreCase( cfRSSA ) ) {
			querygiusta += " AND " + GARA.T_CF_UTENTE + " = ? ";
		}
		
		
/** non ha senso per la gara
 * 	//se si cerca una transazione relativa a uno specifico lotto
 		if ( ! "".equalsIgnoreCase( cigLotto ) ) {
			querygiusta += " AND " + PAGAMENTO.T_CIG_LOTTO + "= ? ";
		}
*/
		
		//se si cerca una transazione relativa a una specifica amministrazione
		if ( ! "".equalsIgnoreCase( cfAmministrazione ) ) {
			querygiusta += " AND " + GARA.T_CF_AMMINISTRAZIONE + " = ? ";
		}
		
		//se si cerca una transazione relativa ad una specifica gara
		if ( ! "".equalsIgnoreCase( idGara ) ) {
			querygiusta += " AND " + GARA.T_ID_GARA + " = ? ";
		}
		
		//se non si vogliono le gare senza pagamento.
		if ( !union ) {
			querygiusta += " AND "+PAGAMENTO.T_DATA_PAGAMENTO+" is not null";
		}

		querygiusta += 
			" UNION "
			// sezione lotti
			+ "SELECT 'CONTRIBUTO LOTTO' as TIPO "
			+ ", "+ LOTTO.T_ID_LOTTO
			+ ", " + GARA.T_DENOM_AMMINISTRAZIONE + " AS AMMINISTRAZIONE"
			+ ", " + GARA.T_ID_GARA 
			+ ", " + PAGAMENTO.T_CIG_LOTTO + " AS CIG "
			+ ", " + LOTTO.T_OGGETTO + " AS OGGETTO "
			+ ", " + PAGAMENTO.T_DATA_PAGAMENTO + " AS DATA_PAGAMENTO "
			+ ", " + ESATTORECANALEPAGAMENTO.T_DESCRIZIONE_CANALE_PAGAMENTO + " AS " + ParametriServlet.CANALE_PAGAMENTO
			+ ", " + PAGAMENTO.T_ID_SA_RIFERIMENTO + " AS CF_CONTRIBUENTE "
			+ ", " + PAGAMENTO.T_CODICE_TRANSAZIONE + " AS " + PAGAMENTO.CODICE_TRANSAZIONE
			+ ", " + PAGAMENTO.T_DATA_CONTABILIZZAZIONE + " AS " + PAGAMENTO.DATA_CONTABILIZZAZIONE
			+ ", " + PAGAMENTO.T_IMPORTO_PAGATO + " AS " + PAGAMENTO.IMPORTO_PAGATO
			+ ", " + ESATTORESTATOPAGAMENTO.T_DESCRIZIONE_STATO_PAGAMENTO + " AS " + ParametriServlet.STATO_PAGAMENTO
			+ ", " + ESATTORETIPOUTENZA.T_DESCRIZIONE_TIPO_UTENZA + " AS " + ParametriServlet.TIPO_UTENZA
			+ " FROM "
			+ LOTTO.TABLE_NAME + " left join "+PAGAMENTO.TABLE_NAME+" on("+PAGAMENTO.T_ID_LOTTO + "=" + LOTTO.T_ID_LOTTO 
							+" AND ("+PAGAMENTO.T_CIG_LOTTO + " = " + LOTTO.T_CIG + "+" + LOTTO.T_CIG_KKK +" OR "+
							PAGAMENTO.T_CIG_LOTTO +" = '" + CIGBean.CIFRA_SOMMA_URGENZA + "'+substring("+LOTTO.T_CIG+",2,len("+LOTTO.T_CIG+"))+"+LOTTO.T_CIG_KKK+"))" 
			+" left join "+ESATTORECANALEPAGAMENTO.TABLE_NAME+" on "+PAGAMENTO.T_CANALE_PAGAMENTO+" = "+ESATTORECANALEPAGAMENTO.T_ID_CANALE_PAGAMENTO
			+" left join "+ESATTORESTATOPAGAMENTO.TABLE_NAME+" on "+PAGAMENTO.T_STATO_PAGAMENTO+" = "+ESATTORESTATOPAGAMENTO.T_ID_STATO_PAGAMENTO
			+" left join "+ESATTORETIPOUTENZA.TABLE_NAME+" on "+PAGAMENTO.T_TIPO_UTENZA+" = "+ESATTORETIPOUTENZA.T_ID_TIPO_UTENZA
			+", "+GARA.TABLE_NAME
			+" where "
			+LOTTO.T_DATA_PUBBLICAZIONE + " BETWEEN ? AND ?"
			+ " AND " + LOTTO.T_DATA_SCADENZA_PAGAMENTI + " BETWEEN ? AND ? "
			+ " AND " + GARA.T_ID_GARA + " = " + LOTTO.T_ID_GARA;
		
		//		se si cerca una transazione relativa a una specifica stazione appaltante
		if( ! "".equalsIgnoreCase( cfSARiferimento ) ) {
			querygiusta += " AND " + GARA.T_ID_STAZIONE_APPALTANTE + " = ? ";
		}
		
		//se si cerca una transazione relativa a uno specifico RSSA
		if ( ! "".equalsIgnoreCase( cfRSSA ) ) {
			querygiusta += " AND " + GARA.T_CF_UTENTE + " = ? ";
		}
		
		//se si cerca una transazione relativa a uno specifico lotto
		if ( ! "".equalsIgnoreCase( cigLotto ) ) {
			querygiusta += " AND " + PAGAMENTO.T_CIG_LOTTO + "= ? ";
		}
		
		//se si cerca una transazione relativa a una specifica amministrazione
		if ( ! "".equalsIgnoreCase( cfAmministrazione ) ) {
			querygiusta += " AND " + GARA.T_CF_AMMINISTRAZIONE + " = ? ";
		}
		
		//se si cerca una transazione relativa ad una specifica gara
		if ( ! "".equalsIgnoreCase( idGara ) ) {
			querygiusta += " AND " + GARA.T_ID_GARA + " = ? ";
		}
		
		//se non si vogliono i lotti senza pagamento.
		if ( !union ) {
			querygiusta += " AND "+PAGAMENTO.T_DATA_PAGAMENTO+" is not null";
		}
		
		querygiusta += " ORDER BY 1, " + GARA.T_ID_GARA ;
		
		logger.debug( "Ricerca transazioni [" + querygiusta + "]" );
		
		PreparedStatement ps = null;
		try{
			ps = activeConnection.prepareStatement(querygiusta);
			int i = 1;
			//dati che erano settati in query (non direttamente settati dal utente)
			ps.setString(i++,fromDataPub);//fromDataPub,1
			ps.setString(i++, toDataPub);//toDataPub,2
			ps.setString(i++, fromDataScad);//fromDataScad,3
			ps.setString(i++, toDataScad);//toDataScad,4
			
// gara
			//di seguito le stesse condizioni della creazione della query
			//per fare il setting del prepared statement (...)
			if( ! "".equalsIgnoreCase( cfSARiferimento ) ) {	ps.setString(i++,cfSARiferimento);	}
			
			//se si cerca una transazione relativa a uno specifico RSSA
			if ( ! "".equalsIgnoreCase( cfRSSA ) ) {	ps.setString(i++,cfRSSA);	}
			
			//se si cerca una transazione relativa a una specifica amministrazione
			if ( ! "".equalsIgnoreCase( cfAmministrazione ) ) {	ps.setString(i++,cfAmministrazione);	}

			//se si cerca una transazione relativa ad una specifica gara
			if ( ! "".equalsIgnoreCase( idGara ) ) {	ps.setString(i++,idGara);	}
			
			//dati che erano settati in query (non direttamente settati dal utente)
			ps.setString(i++,fromDataPub);//fromDataPub,1
			ps.setString(i++, toDataPub);//toDataPub,2
			ps.setString(i++, fromDataScad);//fromDataScad,3
			ps.setString(i++, toDataScad);//toDataScad,4

// lotto
			//di seguito le stesse condizioni della creazione della query
			//per fare il setting del prepared statement (...)
			if( ! "".equalsIgnoreCase( cfSARiferimento ) ) {	ps.setString(i++,cfSARiferimento);	}
			
			//se si cerca una transazione relativa a uno specifico RSSA
			if ( ! "".equalsIgnoreCase( cfRSSA ) ) {	ps.setString(i++,cfRSSA);	}
			
			//se si cerca una transazione relativa a uno specifico lotto
			if ( ! "".equalsIgnoreCase( cigLotto ) ) {	ps.setString(i++,cigLotto);	}
			
			//se si cerca una transazione relativa a una specifica amministrazione
			if ( ! "".equalsIgnoreCase( cfAmministrazione ) ) {	ps.setString(i++,cfAmministrazione);	}
			
			//se si cerca una transazione relativa ad una specifica gara
			if ( ! "".equalsIgnoreCase( idGara ) ) {	ps.setString(i++,idGara);	}
			
			TableBean ret =  new  TableBean(ps.executeQuery(), 0, 1000);
			return ret;
		}catch( Exception e ){			
			//logger.debug(e.getMessage());
			e.printStackTrace();
			return null;
		}finally{
			try{	ps.close();	}
			catch( Exception e){ 	return null; 	}
			ps = null;
		}
	}
}
