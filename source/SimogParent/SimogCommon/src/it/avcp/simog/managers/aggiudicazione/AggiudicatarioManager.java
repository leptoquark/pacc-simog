package it.avcp.simog.managers.aggiudicazione;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicatarioBean;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.AGGIUDICATARIO;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.SOGGETTI_PARTECIPANTI;
import it.avlp.simog.db.generated.TIPO_AGGIUDICATARIO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Classe che si occupa della lettura/scrittura di dati relativi ad un aggiudicatario
 *
 */
public class AggiudicatarioManager extends AccessiDB implements IAnnullamentoMulti{

	public static String CLAZZ = "AggiudicatarioManager";
	
	/**
	 * Costruttore che passa connessione e logger alla sua super classe
	 * 
	 * @param currentActiveConnection
	 * @param logger
	 */
	public AggiudicatarioManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
				
	
	/**
	 * Stringa per la formulazione della PreparedStatement per la SELECT
	 * di una lista di aggiudicatari. 
	 * Select condition
	 * ID Aggiudicazione
	 * Data Inizio Aggiudicazione
	 * Id Soggetto Partecipante
	 * Percentuale
	 * Ruolo
	 * Flag Avvalimento
	 * Cf Ausiliaria
	 * Data Inizio Soggetto
	 * Data Inizio Aggiudicazione
	 * Data Inizio
	 * Id Stato
	 * Data Fine
	 * Id Tipo Aggiudocazione
	 * Nome Tabella Soggetti Partecipanti
	 * From condition
	 * Tabella Aggiudicatario
	 * Tabella Soggetti PArtecipanti
	 * Where condition
	 * Id Aggiudicazione = ?
	 * Data Inizio Aggiudicazione = ? 
	 * Id Soggetto Partecipante in Soggetti Partecipanti = Id Soggetto partecipante in Aggiudicatario
	 * 
	 */
	private final String QUERY_SELECT_LISTA_AGGIUDICATARI = 
		" SELECT " +
		//gm aggiunto per ditte ausiliarie
		AGGIUDICATARIO.T_ID_AGGIUDICATARIO +","+
		
		AGGIUDICATARIO.T_ID_AGGIUDICAZIONE +","+
		AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE +","+
		AGGIUDICATARIO.T_ID_SOGGETTO_PARTECIPANTE +", "+
		AGGIUDICATARIO.T_PERCENTUALE +", "+
		AGGIUDICATARIO.T_RUOLO +", "+
		AGGIUDICATARIO.T_FLAG_AVVALIMENTO +", "+
		AGGIUDICATARIO.T_CF_AUSILIARIA +", "+
		AGGIUDICATARIO.T_DATA_INIZIO_SOGG +", "+
		AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE +", "+
		AGGIUDICATARIO.T_DATA_INIZIO +", "+
		AGGIUDICATARIO.T_ID_STATO +", "+
		AGGIUDICATARIO.T_DATA_FINE +", "+
		AGGIUDICATARIO.T_ID_TIPOAGG +", "+
		//gm aggiunto per raggruppamenti di impresa
		AGGIUDICATARIO.T_ID_GRUPPO +", "+
		// Rinaldo ticket 654 ///////////////////
		AGGIUDICATARIO.T_IMPORTO_AGGIUDICATARIO+", "+
		AGGIUDICATARIO.T_PERC_RIBASSO_AGGIUDICATARIO+", "+
		AGGIUDICATARIO.T_PERC_AUMENTO_AGGIUDICATARIO+", "+
		/////////////////////////////////////////
		SOGGETTI_PARTECIPANTI.T_ID_STATO + " AS ID_PAESE ," + /*UN*/
		SOGGETTI_PARTECIPANTI.TABLE_NAME + ".*"
		+" FROM "+
		AGGIUDICATARIO.TABLE_NAME +" , "+
		SOGGETTI_PARTECIPANTI.TABLE_NAME+
		" WHERE " +
		AGGIUDICATARIO.T_ID_AGGIUDICAZIONE +" = ?"+
		" AND "+
		AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE +" = ?"+
		" AND "+ SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE + " = " + AGGIUDICATARIO.T_ID_SOGGETTO_PARTECIPANTE
		+" AND "+ 		SOGGETTI_PARTECIPANTI.T_DATA_INIZIO_SOGG + " = " + AGGIUDICATARIO.T_DATA_INIZIO_SOGG;
	
	private final String QUERY_SELECT_LISTA_AGGIUDICATARI_BY_ID_AGGIUDICAZIONE = 
			" SELECT " +
			//gm aggiunto per ditte ausiliarie
			AGGIUDICATARIO.T_ID_AGGIUDICATARIO +","+
			
			AGGIUDICATARIO.T_ID_AGGIUDICAZIONE +","+
			AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE +","+
			AGGIUDICATARIO.T_ID_SOGGETTO_PARTECIPANTE +", "+
			AGGIUDICATARIO.T_PERCENTUALE +", "+
			AGGIUDICATARIO.T_RUOLO +", "+
			AGGIUDICATARIO.T_FLAG_AVVALIMENTO +", "+
			AGGIUDICATARIO.T_CF_AUSILIARIA +", "+
			AGGIUDICATARIO.T_DATA_INIZIO_SOGG +", "+
			AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE +", "+
			AGGIUDICATARIO.T_DATA_INIZIO +", "+
			AGGIUDICATARIO.T_ID_STATO +", "+
			AGGIUDICATARIO.T_DATA_FINE +", "+
			AGGIUDICATARIO.T_ID_TIPOAGG +", "+
			//gm aggiunto per raggruppamenti di impresa
			AGGIUDICATARIO.T_ID_GRUPPO +", "+
			// Rinaldo ticket 654 ///////////////////
			AGGIUDICATARIO.T_IMPORTO_AGGIUDICATARIO+", "+
			AGGIUDICATARIO.T_PERC_RIBASSO_AGGIUDICATARIO+", "+
			AGGIUDICATARIO.T_PERC_AUMENTO_AGGIUDICATARIO+", "+
			/////////////////////////////////////////
			SOGGETTI_PARTECIPANTI.T_ID_STATO + " AS ID_PAESE ," + /*UN*/
			SOGGETTI_PARTECIPANTI.TABLE_NAME + ".*"
			+" FROM "+
			AGGIUDICATARIO.TABLE_NAME +" , "+
			SOGGETTI_PARTECIPANTI.TABLE_NAME+
			" WHERE " +
			AGGIUDICATARIO.T_ID_AGGIUDICAZIONE +" = ?"+
			" AND "+ SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE + " = " + AGGIUDICATARIO.T_ID_SOGGETTO_PARTECIPANTE
			+" AND "+ 		SOGGETTI_PARTECIPANTI.T_DATA_INIZIO_SOGG + " = " + AGGIUDICATARIO.T_DATA_INIZIO_SOGG;
		
	
	private final String QUERY_SELECT_LISTA_AGGIUDICATARI_ID_INFO = 
			" SELECT " +
			//gm aggiunto per ditte ausiliarie
			AGGIUDICATARIO.T_ID_AGGIUDICATARIO +","+
			
			AGGIUDICATARIO.T_ID_AGGIUDICAZIONE +","+
			AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE +","+
			AGGIUDICATARIO.T_ID_SOGGETTO_PARTECIPANTE +", "+
			AGGIUDICATARIO.T_PERCENTUALE +", "+
			AGGIUDICATARIO.T_RUOLO +", "+
			AGGIUDICATARIO.T_FLAG_AVVALIMENTO +", "+
			AGGIUDICATARIO.T_CF_AUSILIARIA +", "+
			AGGIUDICATARIO.T_DATA_INIZIO_SOGG +", "+
			AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE +", "+
			AGGIUDICATARIO.T_DATA_INIZIO +", "+
			AGGIUDICATARIO.T_ID_STATO +", "+
			AGGIUDICATARIO.T_DATA_FINE +", "+
			AGGIUDICATARIO.T_ID_TIPOAGG +", "+
			//gm aggiunto per raggruppamenti di impresa
			AGGIUDICATARIO.T_ID_GRUPPO +", "+
			// Rinaldo ticket 654 ///////////////////
			AGGIUDICATARIO.T_IMPORTO_AGGIUDICATARIO+", "+
			AGGIUDICATARIO.T_PERC_RIBASSO_AGGIUDICATARIO+", "+
			AGGIUDICATARIO.T_PERC_AUMENTO_AGGIUDICATARIO+", "+
			/////////////////////////////////////////
			SOGGETTI_PARTECIPANTI.T_ID_STATO + " AS ID_PAESE ," + /*UN*/
			SOGGETTI_PARTECIPANTI.TABLE_NAME + ".*"
			+" FROM "+
			AGGIUDICATARIO.TABLE_NAME +" , "+
			SOGGETTI_PARTECIPANTI.TABLE_NAME+" ,"+
			AGGIUDICAZIONI.TABLE_NAME+", "+
			INFO_AGGIUDICAZIONI.TABLE_NAME+
			" WHERE " +
			AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE+"="+AGGIUDICATARIO.T_ID_AGGIUDICAZIONE+" AND "+AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE+"="+AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE
			+" AND "+INFO_AGGIUDICAZIONI.T_ID_INFO+"="+AGGIUDICAZIONI.T_ID_INFO+" AND "+INFO_AGGIUDICAZIONI.T_DATA_INIZIO_INFO+"="+AGGIUDICAZIONI.T_DATA_INIZIO_INFO+" AND "
			+INFO_AGGIUDICAZIONI.T_ID_INFO +" = ?"+
			" AND "+
			INFO_AGGIUDICAZIONI.T_DATA_INIZIO_INFO +" = ?"+
			" AND "+ SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE + " = " + AGGIUDICATARIO.T_ID_SOGGETTO_PARTECIPANTE
			+" AND "+ 		SOGGETTI_PARTECIPANTI.T_DATA_INIZIO_SOGG + " = " + AGGIUDICATARIO.T_DATA_INIZIO_SOGG;
	
	
	private final String QUERY_SELECT_LISTA_AGGIUDICATARI_NO_DATA = 
			" SELECT " +
			//gm aggiunto per ditte ausiliarie
			AGGIUDICATARIO.T_ID_AGGIUDICATARIO +","+
			
			AGGIUDICATARIO.T_ID_AGGIUDICAZIONE +","+
			AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE +","+
			AGGIUDICATARIO.T_ID_SOGGETTO_PARTECIPANTE +", "+
			AGGIUDICATARIO.T_RUOLO +", "+
			AGGIUDICATARIO.T_FLAG_AVVALIMENTO +", "+
			AGGIUDICATARIO.T_CF_AUSILIARIA +", "+
			AGGIUDICATARIO.T_DATA_INIZIO_SOGG +", "+
			AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE +", "+
			AGGIUDICATARIO.T_DATA_INIZIO +", "+
			AGGIUDICATARIO.T_ID_STATO +", "+
			AGGIUDICATARIO.T_DATA_FINE +", "+
			AGGIUDICATARIO.T_ID_TIPOAGG +", "+
			//gm aggiunto per raggruppamenti di impresa
			AGGIUDICATARIO.T_ID_GRUPPO +", "+
			// Rinaldo ticket 654 ///////////////////
			AGGIUDICATARIO.T_IMPORTO_AGGIUDICATARIO+", "+
			AGGIUDICATARIO.T_PERC_RIBASSO_AGGIUDICATARIO+", "+
			AGGIUDICATARIO.T_PERC_AUMENTO_AGGIUDICATARIO+", "+
			/////////////////////////////////////////
			SOGGETTI_PARTECIPANTI.T_ID_STATO + " AS ID_PAESE ," + /*UN*/
			SOGGETTI_PARTECIPANTI.TABLE_NAME + ".*"
			+" FROM "+
			AGGIUDICATARIO.TABLE_NAME +" , "+
			SOGGETTI_PARTECIPANTI.TABLE_NAME+
			AGGIUDICAZIONI.TABLE_NAME+
			" WHERE (" +
			AGGIUDICATARIO.T_ID_AGGIUDICAZIONE +" = "+AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE+" AND "+AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE+" = "+AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE+")  AND "+
			AGGIUDICAZIONI.T_ID_INFO+" = ? AND "+AGGIUDICAZIONI.T_DATA_INIZIO_INFO+"=? " +" AND "+AGGIUDICAZIONI.T_ID_STATO + " = "+StatiScheda.CONFERMATO+" "+
			" AND "+ SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE + " = " + AGGIUDICATARIO.T_ID_SOGGETTO_PARTECIPANTE
			+" AND "+ 		SOGGETTI_PARTECIPANTI.T_DATA_INIZIO_SOGG + " = " + AGGIUDICATARIO.T_DATA_INIZIO_SOGG;
	
		private final String WHERE_STATO = " AND (" + AGGIUDICATARIO.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE 
		+" OR " + AGGIUDICATARIO.T_ID_STATO + " = " + StatiScheda.CONFERMATO+")";
		
		

	/**
	 * metodo che serve a recuperare tutti gli aggiudicatari relativi ad una aggiudicazione
	 * 
	 * @param idAggiudicazione
	 * @param dataInizioAgg
	 * @param ignoraStato TODO
	 * @return List&lt;AggiudicatarioBean&gt; - una lista di aggiudicatari
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public List<AggiudicatarioBean> loadMany(long idAggiudicazione, Timestamp dataInizioAgg, boolean ignoraStato) throws SQLException{
		
		String mtd = "loadMany";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<AggiudicatarioBean> ris = new ArrayList<AggiudicatarioBean>();
		try{
			AggiudicatarioBean nuovoAggiudicatario = null;
			SoggettoPartecipanteBean nuovoSoggettoPartecipante = null;
			
			String qry = QUERY_SELECT_LISTA_AGGIUDICATARI;
			if(!ignoraStato)
				qry += WHERE_STATO;
			
			stmt = activeConnection.prepareStatement(qry);
			logger.debug(logPrefix+" query ["+qry+"]");
			int index = 1;
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataInizioAgg);
			rs = stmt.executeQuery();
			while(rs.next()){
				nuovoAggiudicatario = new AggiudicatarioBean();
				nuovoSoggettoPartecipante = new SoggettoPartecipanteBean();
				//gm aggiunto per ditte ausiliarie
				nuovoAggiudicatario.setIdAggiudicatario(rs.getLong(AGGIUDICATARIO.ID_AGGIUDICATARIO));
				nuovoAggiudicatario.setDataInizioAggiudicatario(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO));
				
				nuovoAggiudicatario.setCfAusiliaria(rs.getString(AGGIUDICATARIO.CF_AUSILIARIA));
			
				nuovoAggiudicatario.setDataInizioAggiudicazione(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE));
			
				nuovoAggiudicatario.setFlagAvvalimento(rs.getString(AGGIUDICATARIO.FLAG_AVVALIMENTO));
				nuovoAggiudicatario.setIdAggiudicazione(rs.getLong(AGGIUDICATARIO.ID_AGGIUDICAZIONE));
				nuovoAggiudicatario.setIdStato(rs.getLong(AGGIUDICATARIO.ID_STATO));
				nuovoAggiudicatario.setIdTipoAgg(rs.getLong(AGGIUDICATARIO.ID_TIPOAGG));
				// Rinaldo ticket 654 ///////////////////
//				System.out.println("Step 2 IMPORTO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.IMPORTO_AGGIUDICATARIO)
//						+" PERC_RIBASSO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.PERC_RIBASSO_AGGIUDICATARIO)
//						+" PERC_AUMENTO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.PERC_AUMENTO_AGGIUDICATARIO));
				nuovoAggiudicatario.setImpAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.IMPORTO_AGGIUDICATARIO));
				nuovoAggiudicatario.setPercRibassoAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.PERC_RIBASSO_AGGIUDICATARIO));
				nuovoAggiudicatario.setPercAumentoAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.PERC_AUMENTO_AGGIUDICATARIO));
				/////////////////////////////////////////
				//gm aggiunto per raggruppamenti di impresa
				nuovoAggiudicatario.setIdGruppo(rs.getLong(AGGIUDICATARIO.ID_GRUPPO));
				nuovoAggiudicatario.setRuolo(rs.getString(AGGIUDICATARIO.RUOLO));
				nuovoSoggettoPartecipante.setCodiceFiscale(rs.getString(SOGGETTI_PARTECIPANTI.CODICE_FISCALE));
				nuovoSoggettoPartecipante.setDenominazione(rs.getString(SOGGETTI_PARTECIPANTI.DENOMINAZIONE));
				//nuovoSoggettoPartecipante.setCameraCommercio(rs.getString(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO));
				//nuovoSoggettoPartecipante.setCfRappresentante(rs.getString(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE));
				nuovoSoggettoPartecipante.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
				nuovoSoggettoPartecipante.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
				nuovoSoggettoPartecipante.setIdSoggettoPartecipante(rs.getLong(AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE));
				nuovoSoggettoPartecipante.setDataInizioSogg(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_SOGG));
				nuovoSoggettoPartecipante.setCameraCommercio(rs.getString(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO));
				nuovoSoggettoPartecipante.setCfRappresentante(rs.getString(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE));
				nuovoSoggettoPartecipante.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
				nuovoSoggettoPartecipante.setCap(rs.getString(SOGGETTI_PARTECIPANTI.CAP));
				nuovoSoggettoPartecipante.setCitta(rs.getString(SOGGETTI_PARTECIPANTI.CITTA));
				nuovoSoggettoPartecipante.setCivico(rs.getString(SOGGETTI_PARTECIPANTI.CIVICO));
				nuovoSoggettoPartecipante.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
				nuovoSoggettoPartecipante.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
				nuovoSoggettoPartecipante.setPartitaIva(rs.getString(SOGGETTI_PARTECIPANTI.PARTITA_IVA));
				nuovoSoggettoPartecipante.setProvincia(rs.getString(SOGGETTI_PARTECIPANTI.PROVINCIA));
				nuovoSoggettoPartecipante.setId_stato(rs.getString("ID_PAESE")); 			/*UN*/
				nuovoSoggettoPartecipante.setIdSoggettoPartecipante(rs.getLong(AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE));
				nuovoSoggettoPartecipante.setDataInizioSogg(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_SOGG));
				nuovoAggiudicatario.setSoggettoPartecipante(nuovoSoggettoPartecipante);
				ris.add(nuovoAggiudicatario);
			}
		}
		finally{
			close(rs,stmt);
		}
		ris.trimToSize();
		return ris;
	}
	
	//MAC 34266 3.04.8
	/**
	 * metodo che serve a recuperare tutti gli aggiudicatari relativi ad una aggiudicazione
	 * 
	 * @param idAggiudicazione
	 * @param dataInizioAgg
	 * @param ignoraStato TODO
	 * @return List&lt;AggiudicatarioBean&gt; - una lista di aggiudicatari
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public List<AggiudicatarioBean> loadManyByIdAggiudicazione(long idAggiudicazione, boolean ignoraStato) throws SQLException{
		
		String mtd = "loadMany";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<AggiudicatarioBean> ris = new ArrayList<AggiudicatarioBean>();
		try{
			AggiudicatarioBean nuovoAggiudicatario = null;
			SoggettoPartecipanteBean nuovoSoggettoPartecipante = null;
			
			String qry = QUERY_SELECT_LISTA_AGGIUDICATARI_BY_ID_AGGIUDICAZIONE;
			if(!ignoraStato)
				qry += WHERE_STATO;
			
			stmt = activeConnection.prepareStatement(qry);
			logger.debug(logPrefix+" query ["+qry+"]");
			int index = 1;
			stmt.setLong(index++, idAggiudicazione);
			rs = stmt.executeQuery();
			while(rs.next()){
				nuovoAggiudicatario = new AggiudicatarioBean();
				nuovoSoggettoPartecipante = new SoggettoPartecipanteBean();
				//gm aggiunto per ditte ausiliarie
				nuovoAggiudicatario.setIdAggiudicatario(rs.getLong(AGGIUDICATARIO.ID_AGGIUDICATARIO));
				nuovoAggiudicatario.setDataInizioAggiudicatario(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO));
				
				nuovoAggiudicatario.setCfAusiliaria(rs.getString(AGGIUDICATARIO.CF_AUSILIARIA));
			
				nuovoAggiudicatario.setDataInizioAggiudicazione(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE));
			
				nuovoAggiudicatario.setFlagAvvalimento(rs.getString(AGGIUDICATARIO.FLAG_AVVALIMENTO));
				nuovoAggiudicatario.setIdAggiudicazione(rs.getLong(AGGIUDICATARIO.ID_AGGIUDICAZIONE));
				nuovoAggiudicatario.setIdStato(rs.getLong(AGGIUDICATARIO.ID_STATO));
				nuovoAggiudicatario.setIdTipoAgg(rs.getLong(AGGIUDICATARIO.ID_TIPOAGG));
				// Rinaldo ticket 654 ///////////////////
//				System.out.println("Step 2 IMPORTO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.IMPORTO_AGGIUDICATARIO)
//						+" PERC_RIBASSO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.PERC_RIBASSO_AGGIUDICATARIO)
//						+" PERC_AUMENTO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.PERC_AUMENTO_AGGIUDICATARIO));
				nuovoAggiudicatario.setImpAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.IMPORTO_AGGIUDICATARIO));
				nuovoAggiudicatario.setPercRibassoAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.PERC_RIBASSO_AGGIUDICATARIO));
				nuovoAggiudicatario.setPercAumentoAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.PERC_AUMENTO_AGGIUDICATARIO));
				/////////////////////////////////////////
				//gm aggiunto per raggruppamenti di impresa
				nuovoAggiudicatario.setIdGruppo(rs.getLong(AGGIUDICATARIO.ID_GRUPPO));
				nuovoAggiudicatario.setRuolo(rs.getString(AGGIUDICATARIO.RUOLO));
				nuovoSoggettoPartecipante.setCodiceFiscale(rs.getString(SOGGETTI_PARTECIPANTI.CODICE_FISCALE));
				nuovoSoggettoPartecipante.setDenominazione(rs.getString(SOGGETTI_PARTECIPANTI.DENOMINAZIONE));
				//nuovoSoggettoPartecipante.setCameraCommercio(rs.getString(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO));
				//nuovoSoggettoPartecipante.setCfRappresentante(rs.getString(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE));
				nuovoSoggettoPartecipante.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
				nuovoSoggettoPartecipante.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
				nuovoSoggettoPartecipante.setIdSoggettoPartecipante(rs.getLong(AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE));
				nuovoSoggettoPartecipante.setDataInizioSogg(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_SOGG));
				nuovoSoggettoPartecipante.setCameraCommercio(rs.getString(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO));
				nuovoSoggettoPartecipante.setCfRappresentante(rs.getString(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE));
				nuovoSoggettoPartecipante.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
				nuovoSoggettoPartecipante.setCap(rs.getString(SOGGETTI_PARTECIPANTI.CAP));
				nuovoSoggettoPartecipante.setCitta(rs.getString(SOGGETTI_PARTECIPANTI.CITTA));
				nuovoSoggettoPartecipante.setCivico(rs.getString(SOGGETTI_PARTECIPANTI.CIVICO));
				nuovoSoggettoPartecipante.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
				nuovoSoggettoPartecipante.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
				nuovoSoggettoPartecipante.setPartitaIva(rs.getString(SOGGETTI_PARTECIPANTI.PARTITA_IVA));
				nuovoSoggettoPartecipante.setProvincia(rs.getString(SOGGETTI_PARTECIPANTI.PROVINCIA));
				nuovoSoggettoPartecipante.setId_stato(rs.getString("ID_PAESE")); 			/*UN*/
				nuovoSoggettoPartecipante.setIdSoggettoPartecipante(rs.getLong(AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE));
				nuovoSoggettoPartecipante.setDataInizioSogg(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_SOGG));
				nuovoAggiudicatario.setSoggettoPartecipante(nuovoSoggettoPartecipante);
				ris.add(nuovoAggiudicatario);
			}
		}
		finally{
			close(rs,stmt);
		}
		ris.trimToSize();
		return ris;
	}
	
	
	/**
	 * metodo che serve a recuperare tutti gli aggiudicatari relativi ad una aggiudicazione
	 * 
	 * @param idAggiudicazione
	 * @param dataInizioAgg
	 * @param ignoraStato TODO
	 * @return List&lt;AggiudicatarioBean&gt; - una lista di aggiudicatari
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public List<AggiudicatarioBean> loadManyIdInfo(long idInfo, Timestamp dataInizioInfo, boolean ignoraStato) throws SQLException{
		
		String mtd = "loadMany";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<AggiudicatarioBean> ris = new ArrayList<AggiudicatarioBean>();
		Map<String,String> cfAgg = new HashMap<String,String>();
		try{
			AggiudicatarioBean nuovoAggiudicatario = null;
			SoggettoPartecipanteBean nuovoSoggettoPartecipante = null;
			
			String qry = QUERY_SELECT_LISTA_AGGIUDICATARI_ID_INFO;
			if(!ignoraStato)
				qry += WHERE_STATO;
			
			stmt = activeConnection.prepareStatement(qry);
			logger.debug(logPrefix+" query ["+qry+"]");
			int index = 1;
			stmt.setLong(index++, idInfo);
			stmt.setTimestamp(index++, dataInizioInfo);
			rs = stmt.executeQuery();
			while(rs.next()){
				nuovoAggiudicatario = new AggiudicatarioBean();
				nuovoSoggettoPartecipante = new SoggettoPartecipanteBean();
				//gm aggiunto per ditte ausiliarie
				nuovoAggiudicatario.setIdAggiudicatario(rs.getLong(AGGIUDICATARIO.ID_AGGIUDICATARIO));
				nuovoAggiudicatario.setDataInizioAggiudicatario(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO));
				
				nuovoAggiudicatario.setCfAusiliaria(rs.getString(AGGIUDICATARIO.CF_AUSILIARIA));
			
				nuovoAggiudicatario.setDataInizioAggiudicazione(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE));
			
				nuovoAggiudicatario.setFlagAvvalimento(rs.getString(AGGIUDICATARIO.FLAG_AVVALIMENTO));
				nuovoAggiudicatario.setIdAggiudicazione(rs.getLong(AGGIUDICATARIO.ID_AGGIUDICAZIONE));
				nuovoAggiudicatario.setIdStato(rs.getLong(AGGIUDICATARIO.ID_STATO));
				nuovoAggiudicatario.setIdTipoAgg(rs.getLong(AGGIUDICATARIO.ID_TIPOAGG));
				// Rinaldo ticket 654 ///////////////////
//				System.out.println("Step 2 IMPORTO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.IMPORTO_AGGIUDICATARIO)
//						+" PERC_RIBASSO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.PERC_RIBASSO_AGGIUDICATARIO)
//						+" PERC_AUMENTO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.PERC_AUMENTO_AGGIUDICATARIO));
				nuovoAggiudicatario.setImpAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.IMPORTO_AGGIUDICATARIO));
				nuovoAggiudicatario.setPercRibassoAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.PERC_RIBASSO_AGGIUDICATARIO));
				nuovoAggiudicatario.setPercAumentoAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.PERC_AUMENTO_AGGIUDICATARIO));
				/////////////////////////////////////////
				//gm aggiunto per raggruppamenti di impresa
				nuovoAggiudicatario.setIdGruppo(rs.getLong(AGGIUDICATARIO.ID_GRUPPO));
				nuovoAggiudicatario.setRuolo(rs.getString(AGGIUDICATARIO.RUOLO));
				nuovoSoggettoPartecipante.setCodiceFiscale(rs.getString(SOGGETTI_PARTECIPANTI.CODICE_FISCALE));
				nuovoSoggettoPartecipante.setDenominazione(rs.getString(SOGGETTI_PARTECIPANTI.DENOMINAZIONE));
				//nuovoSoggettoPartecipante.setCameraCommercio(rs.getString(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO));
				//nuovoSoggettoPartecipante.setCfRappresentante(rs.getString(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE));
				nuovoSoggettoPartecipante.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
				nuovoSoggettoPartecipante.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
				nuovoSoggettoPartecipante.setIdSoggettoPartecipante(rs.getLong(AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE));
				nuovoSoggettoPartecipante.setDataInizioSogg(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_SOGG));
				nuovoSoggettoPartecipante.setCameraCommercio(rs.getString(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO));
				nuovoSoggettoPartecipante.setCfRappresentante(rs.getString(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE));
				nuovoSoggettoPartecipante.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
				nuovoSoggettoPartecipante.setCap(rs.getString(SOGGETTI_PARTECIPANTI.CAP));
				nuovoSoggettoPartecipante.setCitta(rs.getString(SOGGETTI_PARTECIPANTI.CITTA));
				nuovoSoggettoPartecipante.setCivico(rs.getString(SOGGETTI_PARTECIPANTI.CIVICO));
				nuovoSoggettoPartecipante.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
				nuovoSoggettoPartecipante.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
				nuovoSoggettoPartecipante.setPartitaIva(rs.getString(SOGGETTI_PARTECIPANTI.PARTITA_IVA));
				nuovoSoggettoPartecipante.setProvincia(rs.getString(SOGGETTI_PARTECIPANTI.PROVINCIA));
				nuovoSoggettoPartecipante.setId_stato(rs.getString("ID_PAESE")); 			/*UN*/
				nuovoSoggettoPartecipante.setIdSoggettoPartecipante(rs.getLong(AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE));
				nuovoSoggettoPartecipante.setDataInizioSogg(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_SOGG));
				nuovoAggiudicatario.setSoggettoPartecipante(nuovoSoggettoPartecipante);
				
				if(cfAgg.get(nuovoSoggettoPartecipante.getCodiceFiscale())==null) {
					cfAgg.put(nuovoSoggettoPartecipante.getCodiceFiscale(), nuovoSoggettoPartecipante.getCodiceFiscale());
				ris.add(nuovoAggiudicatario);
				}
			}
		}
		finally{
			close(rs,stmt);
		}
		ris.trimToSize();
		return ris;
	}
	
	//TICKET ALM #11168 - 3.04.4
	public List<AggiudicatarioBean> loadManyById(long idAggiudicazione) throws SQLException{
		
		String mtd = "loadMany";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<AggiudicatarioBean> ris = new ArrayList<AggiudicatarioBean>();
		try{
			AggiudicatarioBean nuovoAggiudicatario = null;
			SoggettoPartecipanteBean nuovoSoggettoPartecipante = null;
			
			String qry = QUERY_SELECT_LISTA_AGGIUDICATARI_NO_DATA+ " order by "+AGGIUDICATARIO.T_ID_STATO;

			
			stmt = activeConnection.prepareStatement(qry);
			logger.debug(logPrefix+" query ["+qry+"]");
			int index = 1;
			stmt.setLong(index++, idAggiudicazione);

			rs = stmt.executeQuery();
			while(rs.next()){
				nuovoAggiudicatario = new AggiudicatarioBean();
				nuovoSoggettoPartecipante = new SoggettoPartecipanteBean();
				//gm aggiunto per ditte ausiliarie
				nuovoAggiudicatario.setIdAggiudicatario(rs.getLong(AGGIUDICATARIO.ID_AGGIUDICATARIO));
				nuovoAggiudicatario.setDataInizioAggiudicatario(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO));
				
				nuovoAggiudicatario.setCfAusiliaria(rs.getString(AGGIUDICATARIO.CF_AUSILIARIA));
			
				nuovoAggiudicatario.setDataInizioAggiudicazione(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE));
			
				nuovoAggiudicatario.setFlagAvvalimento(rs.getString(AGGIUDICATARIO.FLAG_AVVALIMENTO));
				nuovoAggiudicatario.setIdAggiudicazione(rs.getLong(AGGIUDICATARIO.ID_AGGIUDICAZIONE));
				nuovoAggiudicatario.setIdStato(rs.getLong(AGGIUDICATARIO.ID_STATO));
				nuovoAggiudicatario.setIdTipoAgg(rs.getLong(AGGIUDICATARIO.ID_TIPOAGG));
				// Rinaldo ticket 654 ///////////////////
//				System.out.println("Step 2 IMPORTO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.IMPORTO_AGGIUDICATARIO)
//						+" PERC_RIBASSO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.PERC_RIBASSO_AGGIUDICATARIO)
//						+" PERC_AUMENTO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.PERC_AUMENTO_AGGIUDICATARIO));
				nuovoAggiudicatario.setImpAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.IMPORTO_AGGIUDICATARIO));
				nuovoAggiudicatario.setPercRibassoAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.PERC_RIBASSO_AGGIUDICATARIO));
				nuovoAggiudicatario.setPercAumentoAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.PERC_AUMENTO_AGGIUDICATARIO));
				/////////////////////////////////////////
				//gm aggiunto per raggruppamenti di impresa
				nuovoAggiudicatario.setIdGruppo(rs.getLong(AGGIUDICATARIO.ID_GRUPPO));
				nuovoAggiudicatario.setRuolo(rs.getString(AGGIUDICATARIO.RUOLO));
				nuovoSoggettoPartecipante.setCodiceFiscale(rs.getString(SOGGETTI_PARTECIPANTI.CODICE_FISCALE));
				nuovoSoggettoPartecipante.setDenominazione(rs.getString(SOGGETTI_PARTECIPANTI.DENOMINAZIONE));
				//nuovoSoggettoPartecipante.setCameraCommercio(rs.getString(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO));
				//nuovoSoggettoPartecipante.setCfRappresentante(rs.getString(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE));
				nuovoSoggettoPartecipante.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
				nuovoSoggettoPartecipante.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
				nuovoSoggettoPartecipante.setIdSoggettoPartecipante(rs.getLong(AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE));
				nuovoSoggettoPartecipante.setDataInizioSogg(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_SOGG));
				nuovoSoggettoPartecipante.setCameraCommercio(rs.getString(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO));
				nuovoSoggettoPartecipante.setCfRappresentante(rs.getString(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE));
				nuovoSoggettoPartecipante.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
				nuovoSoggettoPartecipante.setCap(rs.getString(SOGGETTI_PARTECIPANTI.CAP));
				nuovoSoggettoPartecipante.setCitta(rs.getString(SOGGETTI_PARTECIPANTI.CITTA));
				nuovoSoggettoPartecipante.setCivico(rs.getString(SOGGETTI_PARTECIPANTI.CIVICO));
				nuovoSoggettoPartecipante.setPartitaIva(rs.getString(SOGGETTI_PARTECIPANTI.PARTITA_IVA));
				nuovoSoggettoPartecipante.setProvincia(rs.getString(SOGGETTI_PARTECIPANTI.PROVINCIA));
				nuovoSoggettoPartecipante.setId_stato(rs.getString("ID_PAESE")); 			/*UN*/
				nuovoAggiudicatario.setSoggettoPartecipante(nuovoSoggettoPartecipante);
				ris.add(nuovoAggiudicatario);
			}
		}
		finally{
			close(rs,stmt);
		}
		ris.trimToSize();
		ris = eliminaDuplicati(ris);
		return ris;
	}
	//TICKET ALM #14599
	public ArrayList<AggiudicatarioBean> eliminaDuplicati(ArrayList<AggiudicatarioBean> aggiudicatari){
		
		ArrayList<AggiudicatarioBean> ris = new ArrayList<AggiudicatarioBean>();
		 List<String> listaCfAgg = new ArrayList<String>();
		 
		 for(AggiudicatarioBean agg : aggiudicatari) {
			 String cfElem = agg.getSoggettoPartecipante().getCodiceFiscale();
			 boolean found=false;
			 for(String cf : listaCfAgg) {
				 if(cf.equals(cfElem)) {
					 found=true;
					 break;
				 }
			 }
			 if(!found) {
				 ris.add(agg);
				 listaCfAgg.add(cfElem);
			 }
		 }
		 
		 return ris;
		
	}
	
	private final String QUERY_SELECT_LISTA_AGGIUDICATARI_BY_ID_INFO_AGG = 
			" SELECT  DISTINCT " +
			//gm aggiunto per ditte ausiliarie
			AGGIUDICATARIO.T_ID_AGGIUDICATARIO +","+
			
			AGGIUDICATARIO.T_ID_AGGIUDICAZIONE +","+
			AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE +","+
			AGGIUDICATARIO.T_ID_SOGGETTO_PARTECIPANTE +", "+
			AGGIUDICATARIO.T_PERCENTUALE +", "+
			AGGIUDICATARIO.T_RUOLO +", "+
			AGGIUDICATARIO.T_FLAG_AVVALIMENTO +", "+
			AGGIUDICATARIO.T_CF_AUSILIARIA +", "+
			AGGIUDICATARIO.T_DATA_INIZIO_SOGG +", "+
			AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE +", "+
			AGGIUDICATARIO.T_DATA_INIZIO +", "+
			AGGIUDICATARIO.T_ID_STATO +", "+
			AGGIUDICATARIO.T_DATA_FINE +", "+
			AGGIUDICATARIO.T_ID_TIPOAGG +", "+
			//gm aggiunto per raggruppamenti di impresa
			AGGIUDICATARIO.T_ID_GRUPPO +", "+
			// Rinaldo ticket 654 ///////////////////
			AGGIUDICATARIO.T_IMPORTO_AGGIUDICATARIO+", "+
			AGGIUDICATARIO.T_PERC_RIBASSO_AGGIUDICATARIO+", "+
			AGGIUDICATARIO.T_PERC_AUMENTO_AGGIUDICATARIO+", "+
			/////////////////////////////////////////
			SOGGETTI_PARTECIPANTI.T_ID_STATO + " AS ID_PAESE ," + /*UN*/
			SOGGETTI_PARTECIPANTI.TABLE_NAME + ".*"
			+" FROM "+
			AGGIUDICATARIO.TABLE_NAME +" , "+
			SOGGETTI_PARTECIPANTI.TABLE_NAME+","+
			AGGIUDICAZIONI.TABLE_NAME+
			" WHERE " +
			AGGIUDICATARIO.T_ID_AGGIUDICAZIONE +" = "+AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE+
			" AND "+AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE+" = "+AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE+
			" AND "+AGGIUDICAZIONI.T_ID_INFO+" = ? "+
			" AND "+ SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE + " = " + AGGIUDICATARIO.T_ID_SOGGETTO_PARTECIPANTE
			+" AND "+ 		SOGGETTI_PARTECIPANTI.T_DATA_INIZIO_SOGG + " = " + AGGIUDICATARIO.T_DATA_INIZIO_SOGG;
	
	//TICKET ALM #11168 - 3.04.4
	public List<AggiudicatarioBean> loadManyByIdInfoAgg(long idInfoAgg) throws SQLException{
		
		String mtd = "loadMany";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<AggiudicatarioBean> ris = new ArrayList<AggiudicatarioBean>();
		try{
			AggiudicatarioBean nuovoAggiudicatario = null;
			SoggettoPartecipanteBean nuovoSoggettoPartecipante = null;
			
			String qry = QUERY_SELECT_LISTA_AGGIUDICATARI_BY_ID_INFO_AGG;

			
			stmt = activeConnection.prepareStatement(qry);
			logger.debug(logPrefix+" query ["+qry+"]");
			int index = 1;
			stmt.setLong(index++, idInfoAgg);

			rs = stmt.executeQuery();
			while(rs.next()){
				nuovoAggiudicatario = new AggiudicatarioBean();
				nuovoSoggettoPartecipante = new SoggettoPartecipanteBean();
				//gm aggiunto per ditte ausiliarie
				nuovoAggiudicatario.setIdAggiudicatario(rs.getLong(AGGIUDICATARIO.ID_AGGIUDICATARIO));
				nuovoAggiudicatario.setDataInizioAggiudicatario(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO));
				
				nuovoAggiudicatario.setCfAusiliaria(rs.getString(AGGIUDICATARIO.CF_AUSILIARIA));
			
				nuovoAggiudicatario.setDataInizioAggiudicazione(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE));
			
				nuovoAggiudicatario.setFlagAvvalimento(rs.getString(AGGIUDICATARIO.FLAG_AVVALIMENTO));
				nuovoAggiudicatario.setIdAggiudicazione(rs.getLong(AGGIUDICATARIO.ID_AGGIUDICAZIONE));
				nuovoAggiudicatario.setIdStato(rs.getLong(AGGIUDICATARIO.ID_STATO));
				nuovoAggiudicatario.setIdTipoAgg(rs.getLong(AGGIUDICATARIO.ID_TIPOAGG));
				// Rinaldo ticket 654 ///////////////////
//				System.out.println("Step 2 IMPORTO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.IMPORTO_AGGIUDICATARIO)
//						+" PERC_RIBASSO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.PERC_RIBASSO_AGGIUDICATARIO)
//						+" PERC_AUMENTO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.PERC_AUMENTO_AGGIUDICATARIO));
				nuovoAggiudicatario.setImpAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.IMPORTO_AGGIUDICATARIO));
				nuovoAggiudicatario.setPercRibassoAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.PERC_RIBASSO_AGGIUDICATARIO));
				nuovoAggiudicatario.setPercAumentoAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.PERC_AUMENTO_AGGIUDICATARIO));
				/////////////////////////////////////////
				//gm aggiunto per raggruppamenti di impresa
				nuovoAggiudicatario.setIdGruppo(rs.getLong(AGGIUDICATARIO.ID_GRUPPO));
				nuovoAggiudicatario.setRuolo(rs.getString(AGGIUDICATARIO.RUOLO));
				nuovoSoggettoPartecipante.setCodiceFiscale(rs.getString(SOGGETTI_PARTECIPANTI.CODICE_FISCALE));
				nuovoSoggettoPartecipante.setDenominazione(rs.getString(SOGGETTI_PARTECIPANTI.DENOMINAZIONE));
				//nuovoSoggettoPartecipante.setCameraCommercio(rs.getString(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO));
				//nuovoSoggettoPartecipante.setCfRappresentante(rs.getString(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE));
				nuovoSoggettoPartecipante.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
				nuovoSoggettoPartecipante.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
				nuovoSoggettoPartecipante.setIdSoggettoPartecipante(rs.getLong(AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE));
				nuovoSoggettoPartecipante.setDataInizioSogg(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_SOGG));
				nuovoSoggettoPartecipante.setCameraCommercio(rs.getString(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO));
				nuovoSoggettoPartecipante.setCfRappresentante(rs.getString(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE));
				nuovoSoggettoPartecipante.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
				nuovoSoggettoPartecipante.setCap(rs.getString(SOGGETTI_PARTECIPANTI.CAP));
				nuovoSoggettoPartecipante.setCitta(rs.getString(SOGGETTI_PARTECIPANTI.CITTA));
				nuovoSoggettoPartecipante.setCivico(rs.getString(SOGGETTI_PARTECIPANTI.CIVICO));
				nuovoSoggettoPartecipante.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
				nuovoSoggettoPartecipante.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
				nuovoSoggettoPartecipante.setPartitaIva(rs.getString(SOGGETTI_PARTECIPANTI.PARTITA_IVA));
				nuovoSoggettoPartecipante.setProvincia(rs.getString(SOGGETTI_PARTECIPANTI.PROVINCIA));
				nuovoSoggettoPartecipante.setId_stato(rs.getString("ID_PAESE")); 			/*UN*/
				nuovoSoggettoPartecipante.setIdSoggettoPartecipante(rs.getLong(AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE));
				nuovoSoggettoPartecipante.setDataInizioSogg(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_SOGG));
				nuovoAggiudicatario.setSoggettoPartecipante(nuovoSoggettoPartecipante);
				ris.add(nuovoAggiudicatario);
			}
		}
		finally{
			close(rs,stmt);
		}
		ris.trimToSize();
		return ris;
	}
	
	private final String QUERY_SELECT_LISTA_AGGIUDICATARI_MULTILOTTO = 
		"SELECT " + AGGIUDICATARIO.TABLE_NAME + ".*, " + 
		SOGGETTI_PARTECIPANTI.T_ID_STATO + " AS ID_PAESE, " +
		SOGGETTI_PARTECIPANTI.TABLE_NAME + ".* " +
		" FROM " + AGGIUDICATARIO.TABLE_NAME + ", " + SOGGETTI_PARTECIPANTI.TABLE_NAME +
		" WHERE " + AGGIUDICATARIO.T_ID_SOGGETTO_PARTECIPANTE + " = " + SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE +
		" AND " + AGGIUDICATARIO.T_DATA_INIZIO_SOGG + " = " + SOGGETTI_PARTECIPANTI.T_DATA_INIZIO_SOGG +
		" AND " +
		"(" + AGGIUDICATARIO.T_ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE +
		" OR " + AGGIUDICATARIO.T_ID_STATO + " = " + StatiScheda.CONFERMATO + ")" +
		" AND " + AGGIUDICATARIO.T_ID_AGGIUDICAZIONE + " IN " +
		"(SELECT DISTINCT " + AGGIUDICAZIONI.ID_AGGIUDICAZIONE +
		" FROM " + AGGIUDICAZIONI.TABLE_NAME + 
		" WHERE " +
		AGGIUDICAZIONI.CODICE_CONTRATTO + " = ? " +
		" AND " +
		"(" + AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE +
		" OR " + AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.CONFERMATO + ")" +
		" AND " +
		AGGIUDICAZIONI.T_ID_INFO + " IN " +
		"(SELECT DISTINCT " +
		INFO_AGGIUDICAZIONI.T_ID_INFO + " FROM " + INFO_AGGIUDICAZIONI.TABLE_NAME +
		" WHERE " +
		INFO_AGGIUDICAZIONI.T_ID_LOTTO + " IN " +
		"(SELECT DISTINCT " +
		LOTTO.T_ID_LOTTO + " FROM " + LOTTO.TABLE_NAME +
		" WHERE " +
		LOTTO.T_ID_GARA + " = " +
		"(SELECT " +
		LOTTO.T_ID_GARA + " FROM " + LOTTO.TABLE_NAME +
		" WHERE " +
		LOTTO.T_ID_LOTTO + " = ? " +
	    "))))";
	
	/**
	 * metodo per il recupero di tutte gli aggiudicatari inerenti ai lotti di una 
	 * stessa gara che hanno codice contratto in comune
	 * 
	 * @param codiceContratto
	 * @param idLotto
	 * @return List&lt;AggiudicatariBean&gt; - lista di aggiudicatari
	 */
	public List<AggiudicatarioBean> getAggiudicatariListMultilotto(String codiceContratto, long idLotto) {
		ArrayList<AggiudicatarioBean> listaAgg = new ArrayList<AggiudicatarioBean>();
		AggiudicatarioBean agg = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_LISTA_AGGIUDICATARI_MULTILOTTO);
			stmt.setString(1, codiceContratto);
			stmt.setLong(2, idLotto);
			rs = stmt.executeQuery();
			while(rs.next()){
				agg = new AggiudicatarioBean();
				this.fillBean(rs, agg);
				listaAgg.add(agg);
			}
	    }
		catch(Exception e){
		    logger.debug("eccezione: "+e);
		}
		finally {
			close(rs,stmt);
		}
		listaAgg.trimToSize();
		return listaAgg;	
	}
	
	
	
	private final String ELIMINA_AGGIUDICATARI = "DELETE FROM " + AGGIUDICATARIO.TABLE_NAME + " WHERE "
	+ AGGIUDICATARIO.ID_AGGIUDICAZIONE + " = ? AND " + AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE +" = ? ";
//	+ "AND "+ AGGIUDICATARIO.DATA_FINE +  " IS NULL";
	
	
	/**
	 * metodo per la cancellazione di tutti gli aggiudicatari, in stato di definizione, relativi ad una aggiudicazione 
	 *  
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public void deleteAggiudicatari(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(ELIMINA_AGGIUDICATARI);
			int index = 1;
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataInizioAggiudicazione);
			stmt.execute();
		}
		finally{
			close(null,stmt);
		}
	}	

	/***************************************************************************************
	 * metodo per l'aggiornamento dell'aggiudicatario allo stato "confermato",
	 * lo stato viene aggiornato anche all'interno del bean passato in ingresso 
	 * 
	 * @param aggiudicatario
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public void confirm(AggiudicatarioBean aggiudicatario) throws SQLException {
		update(aggiudicatario, true);
	}
	
	/**
	 * metodo per l'inserimento dell'aggiudicatario in stato "in definizione",
	 * nel passaggio nel bean viene settato lo stato anche nel bean
	 * 
	 * @param aggiudicatario
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */	
	public void save(AggiudicatarioBean aggiudicatario) throws SQLException {
		update(aggiudicatario, false);
	}		
	
	private final String QUERY_INSERT_AGGIUDICATARIO =
		"INSERT INTO " + AGGIUDICATARIO.TABLE_NAME + " ( " +
		AGGIUDICATARIO.ID_AGGIUDICAZIONE + ", " +
		AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE + ", " + 
		AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE + ", " + 
		AGGIUDICATARIO.DATA_INIZIO_SOGG + ", " + 
		AGGIUDICATARIO.ID_STATO + ", " + 
		AGGIUDICATARIO.DATA_FINE + ", " +
	//	AGGIUDICATARIO.PERCENTUALE + ", " + 
		AGGIUDICATARIO.RUOLO + ", " + 
		AGGIUDICATARIO.FLAG_AVVALIMENTO + ", " +
		AGGIUDICATARIO.CF_AUSILIARIA + ", " +
		AGGIUDICATARIO.ID_TIPOAGG + ", " +
		AGGIUDICATARIO.DATA_INIZIO +", "+
		// Rinaldo ticket 654 ///////////////////
		AGGIUDICATARIO.IMPORTO_AGGIUDICATARIO + ", " +
		AGGIUDICATARIO.PERC_RIBASSO_AGGIUDICATARIO + ", " +
		AGGIUDICATARIO.PERC_AUMENTO_AGGIUDICATARIO + ", " +
		//gm aggiunto per raggruppamenti di impresa
		/////////////////////////////////////////
		AGGIUDICATARIO.ID_GRUPPO +
		" ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private void update(AggiudicatarioBean aggiudicatario, boolean conferma) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {		
			//stmt = activeConnection.prepareStatement(QUERY_INSERT_AGGIUDICATARIO);				
			stmt = activeConnection.prepareStatement(createInsertQuery(QUERY_INSERT_AGGIUDICATARIO,AGGIUDICATARIO.ID_AGGIUDICATARIO));			

			int index = 1;
			
			// AGGIUDICATARIO.ID_AGGIUDICAZIONE 
			stmt.setLong(index++, aggiudicatario.getIdAggiudicazione());               
			
			// AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE + ", " +
			stmt.setTimestamp(index++, aggiudicatario.getDataInizioAggiudicazione());               
			
			//	AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE
			stmt.setLong(index++, aggiudicatario.getSoggettoPartecipante().getIdSoggettoPartecipante());               
			
			// AGGIUDICATARIO.DATA_INIZIO_SOGG			
			stmt.setTimestamp(index++, aggiudicatario.getSoggettoPartecipante().getDataInizioSogg());               
			
			// AGGIUDICATARIO.ID_STATO
			if (conferma){
				stmt.setLong(index++, StatiScheda.CONFERMATO);
				stmt.setTimestamp(index++, getNow());
				aggiudicatario.setIdStato(StatiScheda.CONFERMATO);
			}else{
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
				stmt.setNull(index++, Types.TIMESTAMP);
				aggiudicatario.setIdStato(StatiScheda.IN_DEFINIZIONE);
			}

			// AGGIUDICATARIO.PERCENTUALE 
		//	stmt.setBigDecimal(index++, aggiudicatario.getPercentuale());               
				
			// AGGIUDICATARIO.RUOLO		
			stmt.setString(index++, aggiudicatario.getRuolo());               
			
			// FLAG_AVVALIMENTO
			if(aggiudicatario.getFlagAvvalimento() != null && !"".equals(aggiudicatario.getFlagAvvalimento().trim()))
			   stmt.setString(index++, aggiudicatario.getFlagAvvalimento());      
			else
			   stmt.setNull(index++, Types.VARCHAR);
			
			// CF_AUSILIARIA
			//stmt.setString(index++, aggiudicatario.getCfAusiliaria()); 
			//gm non serve più, adesso è gestito nelle ditte ausiliarie
			stmt.setString(index++, null);
			
			// ID_TIPO_AGG
			stmt.setLong(index++, aggiudicatario.getIdTipoAgg());               
			
			//AGGIUDICATARIO.DATA_INIZIO
			aggiudicatario.setDataInizioAggiudicatario(getNow());
			stmt.setTimestamp(index++, aggiudicatario.getDataInizioAggiudicatario()); 
			
			// Rinaldo ticket 654 ///////////////////
//			System.out.println("Step 3 IMPORTO_AGGIUDICATARIO:"+aggiudicatario.getImpAggiudicatario()
//				+" PERC_RIBASSO_AGGIUDICATARIO:"+ aggiudicatario.getPercRibassoAggiudicatario()	
//				+" PERC_AUMENTO_AGGIUDICATARIO:"+ aggiudicatario.getPercAumentoAggiudicatario());	
			logger.debug("TBTBTBTBTBTBTBTB: INSERIMENTO AGGIUDICATARIO");
			stmt.setBigDecimal(index++, aggiudicatario.getImpAggiudicatario());   
			stmt.setBigDecimal(index++, aggiudicatario.getPercRibassoAggiudicatario());   
			stmt.setBigDecimal(index++, aggiudicatario.getPercAumentoAggiudicatario());   
			/////////////////////////////////////////
			
			//gm aggiunto per raggruppamenti di impresa
    		stmt.setLong(index++, aggiudicatario.getIdGruppo());   
			
			//stmt.executeUpdate();
			
			//gm aggiunto per ditte ausiliarie
    		if(stmt.execute()){
			    rs = stmt.getResultSet();
			    rs.next();
			    aggiudicatario.setIdAggiudicatario(rs.getLong(AGGIUDICATARIO.ID_AGGIUDICATARIO));
    		}
		} 
		finally {
				close(rs, stmt); 
		}
	}		
	
	//modificare
	/**
	 * metodo per la storicizzazione di un record relati ad un aggiudicatario
	 * 
	 * @param idAggiudicazione String
	 * @param dataInizioRecord Timestamp
	 * @param vecchiaData Timestamp
	 * @return boolean
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public boolean copyRecord(String idAggiudicazione,Timestamp dataInizioRecord, Timestamp vecchiaData) throws SQLException{
		
		
		//  Con questa query viene aggiornato lo stato e la data di inizio aggiudicazione del record 
		//  identificato attraverso id aggiudicazione, data inizio aggiudicazione e con stato = confermato
		//  
		String QUERY_UPDATE_OLD_RECORD =
			"UPDATE "+AGGIUDICATARIO.TABLE_NAME+ " SET "
			+ AGGIUDICATARIO.ID_STATO+ " = ?, "
			+ AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE+ " = ? "
			
			+" WHERE "
			+AGGIUDICATARIO.T_ID_AGGIUDICAZIONE+" = ?"
			+" AND "+AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE+" = ?"
			+" AND "+AGGIUDICATARIO.T_ID_STATO + "=" + StatiScheda.CONFERMATO;
		
		//  La query esegue un inserimento nella tabella aggiudicatario degli 
		//  elementi della tabella aggiudicatario identificati tramite l'id 
		//  dell'aggiudicazione, la data di inizio dell'aggiudicazione 
		//  ed aventi lo stato a confermato di tali record viene reimpostata 
		//  Data Inizio, Data Fine e Id Stato. 
		String QUERY_COPY_RECORD =
			"INSERT INTO "+AGGIUDICATARIO.TABLE_NAME+" ("
			+ AGGIUDICATARIO.ID_AGGIUDICATARIO
			+","+AGGIUDICATARIO.RUOLO
			// Rinaldo ticket 654 ///////////////////
			+","+AGGIUDICATARIO.IMPORTO_AGGIUDICATARIO
			+","+AGGIUDICATARIO.PERC_RIBASSO_AGGIUDICATARIO
			+","+AGGIUDICATARIO.PERC_AUMENTO_AGGIUDICATARIO
			/////////////////////////////////////////
			+","+AGGIUDICATARIO.PERCENTUALE
			+","+AGGIUDICATARIO.FLAG_AVVALIMENTO
			+","+AGGIUDICATARIO.CF_AUSILIARIA
			+","+AGGIUDICATARIO.ID_TIPOAGG
			+","+AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE
			+","+AGGIUDICATARIO.DATA_INIZIO_SOGG
			+","+AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE
			+","+AGGIUDICATARIO.ID_AGGIUDICAZIONE
			+","+AGGIUDICATARIO.DATA_INIZIO
			+","+AGGIUDICATARIO.DATA_FINE
			+","+AGGIUDICATARIO.ID_STATO
			//gm aggiunto per raggruppamenti di impresa
			+","+AGGIUDICATARIO.ID_GRUPPO
			+" ) "
			+"SELECT "
			+ AGGIUDICATARIO.ID_AGGIUDICATARIO
			+","+AGGIUDICATARIO.RUOLO
			// Rinaldo ticket 654 ///////////////////
			+","+AGGIUDICATARIO.IMPORTO_AGGIUDICATARIO
			+","+AGGIUDICATARIO.PERC_RIBASSO_AGGIUDICATARIO
			+","+AGGIUDICATARIO.PERC_AUMENTO_AGGIUDICATARIO
			/////////////////////////////////////////
			+","+AGGIUDICATARIO.PERCENTUALE
			+","+AGGIUDICATARIO.FLAG_AVVALIMENTO
			+","+AGGIUDICATARIO.CF_AUSILIARIA
			+","+AGGIUDICATARIO.ID_TIPOAGG
			+","+AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE
			+","+AGGIUDICATARIO.DATA_INIZIO_SOGG
			+","+AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE
			+","+AGGIUDICATARIO.ID_AGGIUDICAZIONE
			+", ?"
			+", ?"
			+", ?"
			//gm aggiunto per raggruppamenti di impresa
			+","+AGGIUDICATARIO.ID_GRUPPO
			+" FROM "+AGGIUDICATARIO.TABLE_NAME
			+" WHERE "
			+AGGIUDICATARIO.ID_AGGIUDICAZIONE+" = ?"
			+" AND "+AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE+" = ?"
		    +" AND "+AGGIUDICATARIO.ID_STATO+" = "+StatiScheda.CONFERMATO;
			PreparedStatement stmt = null;
			PreparedStatement stmt2 = null;
			try{
				int index = 1;
				stmt = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD,AGGIUDICATARIO.TABLE_NAME));
				stmt.setTimestamp(index++, getNow()); //data_inizio_aggiudicatario
				stmt.setNull(index++, Types.TIMESTAMP); // data_fine_aggiudicatario
				stmt.setInt(index++, StatiScheda.IN_DEFINIZIONE); //stato scheda
				stmt.setLong(index++, Long.parseLong(idAggiudicazione));
				stmt.setTimestamp(index++, dataInizioRecord);
				int rowsCopied = stmt.executeUpdate();
				if(rowsCopied > 0){
					index = 1;
					stmt2 = activeConnection.prepareStatement(QUERY_UPDATE_OLD_RECORD);
					stmt2.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA); //stato scheda
					stmt2.setTimestamp(index++, vecchiaData); //data_inizio_aggiudicazione
					stmt2.setLong(index++, Long.parseLong(idAggiudicazione));
					stmt2.setTimestamp(index++, dataInizioRecord);
					rowsCopied = stmt2.executeUpdate();
					return (rowsCopied>0);
				}
				else {
					logger.debug("AGGIUDICATARIO_MANAGER.copyRecord: Nessun record da copiare");
					return true;
				}
			}
			finally{
				close(null, stmt2);
				close(null, stmt);
			}
	}
	
	//Metodo utilizzato dall'amministratore per cancellare il record attivo qualora venisse rifiutata
	//la richiesta di annullamento ad esso relativa
	
	private final String QUERY_DELETE_AGGIUDICATARIO = 
		"DELETE FROM "+AGGIUDICATARIO.TABLE_NAME+
		" WHERE " + AGGIUDICATARIO.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	/**
	 * metodo per l'eliminazione di un record
	 * 
	 * @param idAggiudicazione String
	 * @param dataInizioAgg Timestamp
	 * @return int - affected row count
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public int deleteRecord(String idAggiudicazione, Timestamp dataInizioAgg) throws SQLException{
		
		int numRow = -1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			int index = 1;
			stmt = activeConnection.prepareStatement(QUERY_DELETE_AGGIUDICATARIO);
			
			stmt.setLong(index++, Long.parseLong(idAggiudicazione));
			
			stmt.setTimestamp(index++,dataInizioAgg);
			
			numRow = stmt.executeUpdate();
			 
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}
	
	private final String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_AGGIUDICATARIO = 
		"UPDATE "+AGGIUDICATARIO.TABLE_NAME+
		" SET " + AGGIUDICATARIO.ID_STATO + " = ?,"+
		AGGIUDICATARIO.DATA_FINE + " = " + buildGetDate()+
		" WHERE " + AGGIUDICATARIO.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	/**
	 * metodo per l'aggiornamento di un record
	 * 
	 * @param idAggiudicazione String
	 * @param dataInizioAgg Timestamp
	 * @param stato_scheda ->(String) predefiniti @see StatiScheda
	 * @return int - affected row count
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public int updateRecord(String idAggiudicazione, Timestamp dataInizioAgg, String stato_scheda ) throws SQLException{
		
		int numRow = -1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			int index = 1;
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_AGGIUDICATARIO);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_AGGIUDICATARIO);
			
			stmt.setInt(index++, Integer.parseInt(stato_scheda));
			
			stmt.setLong(index++, Long.parseLong(idAggiudicazione));
			
			stmt.setTimestamp(index++,dataInizioAgg);
						
			numRow = stmt.executeUpdate();
			//PPactiveConnection.commit(currentActiveConnection);
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}	
	private final String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_AGGIUDICATARIO_NEWRECORD = 
		"UPDATE "+AGGIUDICATARIO.TABLE_NAME+
		" SET " + AGGIUDICATARIO.ID_STATO + " = ?,"+ //+STATI_SCHEDA.CONFERMATO+
		AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE + " = ?"+
		" WHERE " + AGGIUDICATARIO.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE + " = ?";	
	
	public int updateRecordToPointToNew(String idAggiudicazione, Timestamp dataInizioAggOld,Timestamp dataInizioAggNew, String stato_scheda ) throws SQLException{
		
		int numRow = -1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			int index = 1;
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_AGGIUDICATARIO_NEWRECORD);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_AGGIUDICATARIO_NEWRECORD);
			
			stmt.setInt(index++, Integer.parseInt(stato_scheda));
			stmt.setTimestamp(index++,dataInizioAggNew);
			stmt.setLong(index++, Long.parseLong(idAggiudicazione));
			
			stmt.setTimestamp(index++,dataInizioAggOld);
						
			numRow = stmt.executeUpdate();
			//PPactiveConnection.commit(currentActiveConnection);
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}	
	/**
	 * metodo per il recupero del tipo aggiudicatario
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String,String&gt; - id , descrizione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public Map<String,String> loadTipoAggiudicatario(Object o) throws SQLException{
		return getTipologica(TIPO_AGGIUDICATARIO.TABLE_NAME, TIPO_AGGIUDICATARIO.ID_TIPOAGG, TIPO_AGGIUDICATARIO.DESCRIZIONE, TIPO_AGGIUDICATARIO.DATA_FINE_VALIDITA,o);		
	}
	
	
	/**
	 * 
	 */
	private final String QUERY_SELECT_AGGIUDICATARI_BY_SOGG = 
		" SELECT "
		+ AGGIUDICATARIO.T_ID_AGGIUDICAZIONE +","
		+ AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE +","
		+ AGGIUDICATARIO.T_ID_SOGGETTO_PARTECIPANTE +", "
		// Rinaldo ticket 654 ///////////////////
		+ AGGIUDICATARIO.T_IMPORTO_AGGIUDICATARIO +", "
		+ AGGIUDICATARIO.T_PERC_RIBASSO_AGGIUDICATARIO + ", "
		+ AGGIUDICATARIO.T_PERC_AUMENTO_AGGIUDICATARIO + ", "
		/////////////////////////////////////////
		+ AGGIUDICATARIO.T_PERCENTUALE +", "
		+ AGGIUDICATARIO.T_RUOLO +", "
		+ AGGIUDICATARIO.T_FLAG_AVVALIMENTO +", "
		+ AGGIUDICATARIO.T_CF_AUSILIARIA +", "
		+ AGGIUDICATARIO.T_DATA_INIZIO_SOGG +", "
		+ AGGIUDICATARIO.T_DATA_INIZIO_AGGIUDICAZIONE +", "
		+ AGGIUDICATARIO.T_DATA_INIZIO +", "
		+ AGGIUDICATARIO.T_ID_STATO +", "
		+ AGGIUDICATARIO.T_DATA_FINE +", "
		+ AGGIUDICATARIO.T_ID_TIPOAGG +", "
		//gm aggiunto per raggruppamenti di impresa
		+ AGGIUDICATARIO.T_ID_GRUPPO + ", "
		+ SOGGETTI_PARTECIPANTI.T_ID_STATO + " AS ID_PAESE, "
		+ SOGGETTI_PARTECIPANTI.TABLE_NAME + ".*"
		+ " FROM "
		+ AGGIUDICATARIO.TABLE_NAME +" , "
		+ SOGGETTI_PARTECIPANTI.TABLE_NAME
		+ " WHERE " + AGGIUDICATARIO.T_ID_SOGGETTO_PARTECIPANTE + " = " + SOGGETTI_PARTECIPANTI.T_ID_SOGGETTO_PARTECIPANTE
		//+ " AND " + AGGIUDICATARIO.T_DATA_INIZIO_SOGG + " = " + SOGGETTI_PARTECIPANTI.T_DATA_INIZIO_SOGG
		+ " AND " + AGGIUDICATARIO.T_ID_SOGGETTO_PARTECIPANTE + " = ? ";
		//+ " AND " + AGGIUDICATARIO.T_DATA_INIZIO_SOGG + " = ? ";

	
	/**
	 * metodo che serve a recuperare tutti gli aggiudicatari congruenti ad un soggetto
	 * 
	 * @param idSogg
	 * @param dataInizioSogg
	 * @return List&lt;AggiudicatarioBean&gt; - una lista di aggiudicatari
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public List<AggiudicatarioBean> loadBySogg(long idSogg) throws SQLException{
		
		String mtd = "loadBySogg";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<AggiudicatarioBean> ris = new ArrayList<AggiudicatarioBean>();
		try{
			AggiudicatarioBean nuovoAggiudicatario = null;
			stmt = activeConnection.prepareStatement(QUERY_SELECT_AGGIUDICATARI_BY_SOGG);
			logger.debug(logPrefix+" query ["+QUERY_SELECT_AGGIUDICATARI_BY_SOGG+"]");
			int index = 1;
			stmt.setLong(index++, idSogg);
			rs = stmt.executeQuery();
			while(rs.next()){
				nuovoAggiudicatario = new AggiudicatarioBean();
				this.fillBean(rs, nuovoAggiudicatario);
				ris.add(nuovoAggiudicatario);
			}
		}
		finally{
			close(rs,stmt);
		}
		ris.trimToSize();
		return ris;
	}
	/*********************************************************************************************************/
	/**************************		NUOVE FUNZIONALITA' 	**************************************************/
	/*********************************************************************************************************/

	/**
	 * @param rs
	 * @param aggiudicatarioBean
	 * @throws SQLException
	 */
	private void fillBean(ResultSet rs, AggiudicatarioBean aggiudicatarioBean)throws SQLException {
		SoggettoPartecipanteBean anagraficaAggiudicatario = new SoggettoPartecipanteBean();
		aggiudicatarioBean.setCfAusiliaria(rs.getString(AGGIUDICATARIO.CF_AUSILIARIA));
		aggiudicatarioBean.setDataInizioAggiudicazione(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE));
		aggiudicatarioBean.setFlagAvvalimento(rs.getString(AGGIUDICATARIO.FLAG_AVVALIMENTO));
		aggiudicatarioBean.setIdAggiudicazione(rs.getLong(AGGIUDICATARIO.ID_AGGIUDICAZIONE));
		aggiudicatarioBean.setIdStato(rs.getLong(AGGIUDICATARIO.ID_STATO));
		aggiudicatarioBean.setIdTipoAgg(rs.getLong(AGGIUDICATARIO.ID_TIPOAGG));		
		// Rinaldo ticket 654 ///////////////////
//		System.out.println("Step 4 IMPORTO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.IMPORTO_AGGIUDICATARIO)
//				+ " PERC_RIBASSO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.PERC_RIBASSO_AGGIUDICATARIO)
//				+ " PERC_AUMENTO_AGGIUDICATARIO:"+rs.getBigDecimal(AGGIUDICATARIO.PERC_AUMENTO_AGGIUDICATARIO));
		aggiudicatarioBean.setImpAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.IMPORTO_AGGIUDICATARIO));
		aggiudicatarioBean.setPercRibassoAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.PERC_RIBASSO_AGGIUDICATARIO));
		aggiudicatarioBean.setPercAumentoAggiudicatario(rs.getBigDecimal(AGGIUDICATARIO.PERC_AUMENTO_AGGIUDICATARIO));
		/////////////////////////////////////////
		//gm aggiunto per raggruppamenti di impresa
		aggiudicatarioBean.setIdGruppo(rs.getLong(AGGIUDICATARIO.ID_GRUPPO));
		
		aggiudicatarioBean.setRuolo(rs.getString(AGGIUDICATARIO.RUOLO));
		
		anagraficaAggiudicatario.setCodiceFiscale(rs.getString(SOGGETTI_PARTECIPANTI.CODICE_FISCALE));
		anagraficaAggiudicatario.setDenominazione(rs.getString(SOGGETTI_PARTECIPANTI.DENOMINAZIONE));
		anagraficaAggiudicatario.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
		anagraficaAggiudicatario.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
		anagraficaAggiudicatario.setIdSoggettoPartecipante(rs.getLong(AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE));
		anagraficaAggiudicatario.setDataInizioSogg(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_SOGG));
		anagraficaAggiudicatario.setCameraCommercio(rs.getString(SOGGETTI_PARTECIPANTI.CAMERA_COMMERCIO));
		anagraficaAggiudicatario.setCfRappresentante(rs.getString(SOGGETTI_PARTECIPANTI.CF_RAPPRESENTANTE));
		anagraficaAggiudicatario.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
		anagraficaAggiudicatario.setCap(rs.getString(SOGGETTI_PARTECIPANTI.CAP));
		anagraficaAggiudicatario.setCitta(rs.getString(SOGGETTI_PARTECIPANTI.CITTA));
		anagraficaAggiudicatario.setCivico(rs.getString(SOGGETTI_PARTECIPANTI.CIVICO));
		anagraficaAggiudicatario.setCognome(rs.getString(SOGGETTI_PARTECIPANTI.COGNOME));
		anagraficaAggiudicatario.setNome(rs.getString(SOGGETTI_PARTECIPANTI.NOME));
		anagraficaAggiudicatario.setPartitaIva(rs.getString(SOGGETTI_PARTECIPANTI.PARTITA_IVA));
		anagraficaAggiudicatario.setProvincia(rs.getString(SOGGETTI_PARTECIPANTI.PROVINCIA));
		anagraficaAggiudicatario.setId_stato(rs.getString("ID_PAESE")); 
		anagraficaAggiudicatario.setIdSoggettoPartecipante(rs.getLong(AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE));
		anagraficaAggiudicatario.setDataInizioSogg(rs.getTimestamp(AGGIUDICATARIO.DATA_INIZIO_SOGG));

		aggiudicatarioBean.setSoggettoPartecipante(anagraficaAggiudicatario);
		
	}

	/**
	 * @see it.avcp.simog.managers.comportamento.annullamento.IAnnullamentoMulti#annulla(long, java.sql.Timestamp)
	 */
	public boolean annulla(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException {
			return _annulla(idAggiudicazione, dataInizioAggiudicazione);	
	}
	/**
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @throws SQLException
	 */
	private boolean _annulla(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_AGGIUDICATARI);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataInizioAggiudicazione);
			someRowAffected = stmt.executeUpdate() > 0;
			return someRowAffected;
		}finally {
			close(null,stmt);
		}
	}


}
