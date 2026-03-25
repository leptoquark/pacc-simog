package it.avcp.simog.managers.accordo;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamento;
import it.avcp.simog.managers.comportamento.caricamento.ILoadAccordo;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.common.servlet.ParametriServletAccordo;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.ACCORDI;
import it.avlp.simog.db.generated.STATI_AVANZ;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Classe che si occupa della gestione di accesso/scrittura
 * sul db della scheda accordo
 *
 */
public class AccordoManager extends AccessiDB implements IAnnullamento,ILoadAccordo {
	
	
	
	public static String CLAZZ = "AccordoManager";

	/**
	 * Costruttore della classe AccordoManager
	 * 
	 * @param currentActiveConnection connessione attiva
	 * @param logger Logger
	 */
	public AccordoManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	
	/**
	 * Stringa per la generazione della query di insert nella tabella ACCORDI tramite prepared statement. 
	 * I parametri della query sono:
	 * <ul>
	 * <li>Data Accordo
	 * <li>Data inizio accordo
	 * <li>Data fine accordo
	 * <li>Data inizio aggiudicazione
	 * <li>Id aggiudicazine
	 * <li>Id stato
	 * <li>Numero riserve
	 * <li>Oneri derivati
	 * </ul>
	 */
	public final String INSERT_ACCORDO = "INSERT INTO " + ACCORDI.TABLE_NAME + "("
	+ACCORDI.DATA_ACCORDO+", "
	+ACCORDI.DATA_INIZIO_ACC+", "
	+ACCORDI.DATA_FINE_ACC+", "
	+ACCORDI.DATA_INIZIO_AGGIUDICAZIONE+", "
	+ACCORDI.ID_AGGIUDICAZIONE+", "
	+ACCORDI.ID_STATO+", "
	+ACCORDI.NUM_RISERVE+", "
	+ACCORDI.ONERI_DERIVANTI+", "
	+ACCORDI.ID_SCHEDA_LOCALE
	+") VALUES (?,?,?,?,?,?,?,?,?)";
		
	/***********************************************************************************************************
	 * Metodo per l&rsquo; INSERIMENTO nella tabella ACCORDI. Viene eseguita la query per l&rsquo;inserimento 
	 * dei dati nella tabella. I dati dell&rsquo;inserimento sono contenuti nel Bean AccordoBean. 
	 * La data relativa all&rsquo;inizio accordo &egrave; settata alla data attuale dell&rsquo;inserimento (now)
	 * ed inserita all&rsquo;interno dei dati del bean. Lo stato &egrave; impostato a &quot;in definizione&quot;.
	 *   
	 * @param bean associato agli accordi, valorizzato
	 * @param cfUtente che effettua l&rsquo;inserimento
	 * @throws SQLException eccezione sollevata durante l&rsquo;esecuzione della query SQL
	 */
	public void insert(AccordoBean bean, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try {
			stmt = activeConnection.prepareStatement(createInsertQuery(INSERT_ACCORDO, ACCORDI.ID_ACCORDO));
			
			stmt.setString(index++, PageHelper.formatDateOrNull( bean.getDataAccordo()));
			
			bean.setDataInizioAccordo(getNow());
			stmt.setTimestamp(index++,bean.getDataInizioAccordo());
			
			stmt.setNull(index++,Types.TIMESTAMP);
			stmt.setTimestamp(index++, bean.getDataInizioAggiudicazione());
			stmt.setLong(index++, bean.getIdAggiudicazione());
			
			bean.setIdStato(StatiScheda.IN_DEFINIZIONE);
			stmt.setLong(index++, bean.getIdStato());
			
			stmt.setInt(index++, bean.getNumeroRiserve());
			stmt.setBigDecimal(index++, bean.getOneriDerivanti());
			
			if(bean.getIdLocale() == null){
				stmt.setNull(index++, Types.VARCHAR);
			}else{
				stmt.setString(index++, bean.getIdLocale() );
			}
			
			
	
			if(stmt.execute()){
				rs = stmt.getResultSet();
				rs.next();
				bean.setIdAccordo(rs.getLong(ACCORDI.ID_ACCORDO));
				List<Object> attributiChiave = new ArrayList<Object>();
				//attributiChiave.add(bean.getIdSospensione());
				//attributiChiave.add(bean.getDataFineSosp());
				attributiChiave.add(bean.getIdAccordo());
				attributiChiave.add(bean.getDataInizioAccordo());
				LogBloccoDatiManager.loggingINSERT(activeConnection, logger, cfUtente, ParametriServletAccordo.TAB_SCHEDA_ACCORDO, attributiChiave);
			}
			
		} finally {
			close(rs, stmt);
		}
	}
	
	/*****************************************************************************************************************
	 * Creazione della stringa per la query di update ACCORDI con i parametri:
	 * <ul>
	 * <li>Data fine accordo
	 * <li>Id stato
	 * <li>Data accordo
	 * <li>Oneri derivanti
	 * <li>Numero riserve
	 * </ul> 
	 * where condition
	 * <ul>
	 * <li>Id accordo
	 * <li>Data inizio acccordo
	 * </ul>
	 */
	public static String UPDATE_ACCORDI = "UPDATE " + ACCORDI.TABLE_NAME + " SET "
	
	+ACCORDI.DATA_FINE_ACC+"=? ,"
	+ACCORDI.ID_STATO+"=? ,"
	
	+ACCORDI.DATA_ACCORDO+"=? ,"
	+ACCORDI.ONERI_DERIVANTI+"=? ,"
	+ACCORDI.NUM_RISERVE+"=? "
    +"WHERE "+ ACCORDI.ID_ACCORDO + " = ? AND "
    +ACCORDI.DATA_INIZIO_ACC+"=? "; 
	//+ACCORDI.ID_STATO+" = " + StatiScheda.IN_DEFINIZIONE_STRING
	// PP fix controllo stato scheda + " OR " +ACCORDI.ID_STATO+" = " + StatiScheda.CONFERMATO_STRING
	//+ " )";
	
    private final static String WHERE_DEF = " AND " + ACCORDI.ID_STATO + " = " +  StatiScheda.IN_DEFINIZIONE_STRING;
    
    private final static String WHERE_CONF = " AND (" + ACCORDI.ID_STATO + " = " 
          +  StatiScheda.IN_DEFINIZIONE_STRING + " OR " + ACCORDI.ID_STATO + " = " +  StatiScheda.CONFERMATO_STRING + ")";
    
	/*************************************************************************************************************
	 * Metodo per l&rsquo;UPDATE, aggiornamento, dei record nella tabella ACCORDI. 
	 *
	 * param bean associato agli accordi
	 * param cfUtente che effettua l&rsquo;aggiornamento
	 * param confirm flag per il tipo di update. Se confirm &egrave; true la 
	 * 				   data di fine accordo &egrave; impostata alla data attuale
	 * 				   e lo stato a &quot;Confermato&quot;, se false la data di fine
	 * 				   accordo &egrave; impostata a null e lo stato della scheda a 
	 * 				   &quot;In Definizione&quot;.   
	 * throws SQLException eccezione sollevata durante l&rsquo;esecuzione della query SQL
	 */
	private int update(AccordoBean bean, String cfUtente, boolean confirm)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		try {
			stmt = activeConnection.prepareStatement(UPDATE_ACCORDI + (SimogFlags.isFlagNoDate() ? WHERE_CONF : WHERE_DEF));	
			/********************************************************************************
			 * Inserimento data fine accordo.
			 * Se lo stato e' confermato viene inserita come data quella attuale altrimenti 
			 * si mantiene a null.
			 ********************************************************************************/
			if(confirm){
				stmt.setTimestamp(index++, getNow()); // data fine 
				stmt.setLong(index++, StatiScheda.CONFERMATO);
			}else{
				//un aggiornamento normale
				stmt.setNull(index++, Types.TIMESTAMP);//data fine record
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);
			}
			//*******************************************************************************
			stmt.setString(index++, PageHelper.formatDateOrNull( bean.getDataAccordo()));
			stmt.setBigDecimal(index++, bean.getOneriDerivanti());
			stmt.setInt(index++, bean.getNumeroRiserve());
			
			// condizioni per la WHERE
			
			stmt.setLong(index++, bean.getIdAccordo());
			stmt.setTimestamp(index++, bean.getDataInizioAccordo());
			int num = stmt.executeUpdate();
			
			List<Object> attributiChiave = new ArrayList<Object>();
			attributiChiave.add(bean.getIdAccordo());
			attributiChiave.add(bean.getDataInizioAccordo());
			if(confirm)
				LogBloccoDatiManager.loggingCONFIRM(activeConnection, logger, cfUtente, ParametriServletAccordo.TAB_SCHEDA_ACCORDO, attributiChiave);
			else 
				LogBloccoDatiManager.loggingUPDATE(activeConnection, logger, cfUtente, ParametriServletAccordo.TAB_SCHEDA_ACCORDO, attributiChiave);
			return num;
		} finally {
			close(rs, stmt);
		}
	}
		
	/*********************************************************************************************************
	 * Metodo che effettual il salvataggio dei dati di un accordo nel database. 
	 * 
	 * @param bean associato agli accordi, deve essere valorizzato
	 * @param cfUtente che effettua il salvataggio
	 * @throws SQLException eccezione sollevata durante l&rsquo;esecuzione della query SQL
	 */
	public int save(AccordoBean bean, String cfUtente)throws SQLException{
		return update(bean,cfUtente, false);
	}
	
	
	/*********************************************************************************************************
	 * metodo per la conferma di un&rsquo;accordo
	 * 
	 * @param bean associato agli accordi, deve essere valorizzato
	 * @param cfUtente codice fiscale utente
	 * @throws SQLException eccezione sollevata durante l&rsquo;esecuzione della query SQL
	 */
	public int confirm(AccordoBean bean, String cfUtente)throws SQLException{
		return update(bean,cfUtente, true);
	}
	
	/***************************************************************************************************
	 * Stringa per la formulazione della prepared statement relativa alla SELECT di un accordo
	 * i campi interessati sono :
	 * <ul>
	 * <li>Id accordo
	 * <li>Data accordo
	 * <li>Data fine accordo
	 * <li>Id aggiudicazione
	 * <li>Data inizio aggiudicazine
	 * <li>Id stato
	 * <li>Numero riserve
	 * <li>Oneri derivanti
	 * <li>Descrizione stato
	 * <li>(Parametri per la query sulla richiesta di annullamento)
	 * </ul>
	 * From condition
	 * <ul>
	 * <li>Tabella Accordi
	 * <li>Tabella per gli stati delle schede
	 * </ul>
	 * where contition
	 * <ul>
	 * <li>Id Accordo
	 * <li>Data inizio Accordo
	 * <li>Id stato
	 * </ul> 
	 */
	
	public final String SELECT_ONE_ACCORDO = "SELECT "
			+ ACCORDI.ID_ACCORDO
			+ ", " + ACCORDI.DATA_ACCORDO
		    + ", " + ACCORDI.DATA_INIZIO_ACC
		    + ", " + ACCORDI.DATA_FINE_ACC
		    + ", " + ACCORDI.ID_AGGIUDICAZIONE
		    + ", " + ACCORDI.DATA_INIZIO_AGGIUDICAZIONE
		    + ", " + ACCORDI.T_ID_STATO
		    + ", " + ACCORDI.NUM_RISERVE
		    + ", " + ACCORDI.ONERI_DERIVANTI
		    + ", " + STATI_SCHEDA.T_DESCRIZIONE 
		    + " + " + buildRichAnnQuery(ParametriServletAccordo.TAB_SCHEDA_ACCORDO, ACCORDI.T_ID_ACCORDO,null) 
			+" AS "+STATI_SCHEDA.DESCRIZIONE
			+", " + ACCORDI.ID_SCHEDA_LOCALE
		+ " FROM " 
			+ ACCORDI.TABLE_NAME + ", " 
			+ STATI_SCHEDA.TABLE_NAME; 


	/****************************************************************************************************
	 * metodo per caricare i dati di un Accordo in base all&rsquo;id ed alla data di inizio
	 * 
	 * @param idAccordo id accordo da caricare
	 * @param dataInizioAccordo data inizio accordo da caricare
	 * @return AccordoBean un bean contente tutte le informazioni relative all&rsquo;accordo di cui id e data
	 * @throws SQLException eccezione sollevata durante l&rsquo;esecuzione della query SQL
	 */
	public AccordoBean loadOne(long idAccordo, Timestamp dataInizioAccordo)throws SQLException{
		List<AccordoBean> lista = load(idAccordo, dataInizioAccordo, false);
		if(lista!= null && lista.size() > 0)
			return lista.get(0);
		else return null;
		
	}

	
	
	/**
	 * Formulazione della prepared statement per ottenere piu accordi<br>
	 * select condition
	 * <ul>
	 * <li>Id Accordo
	 * <li>Data Accordo
	 * <li>Data Inizio Accordo
	 * <li>Data Fine Accordo
	 * <li>Id Aggiudicazione
	 * <li>Data Inizio Aggiudicazione
	 * <li>Id Stato
	 * <li>Numero Riserve
	 * <li>Oneri Derivanti
	 * <li>Id Stato 
	 * <li>Descrizione Stato
	 * <li>(Query per la richiesta di annullamento)
	 * </ul>
	 * From condition
	 * <ul>
	 * <li>Tabella Accordi
	 * <li>Tabella Stati Scheda
	 * </ul>
	 * Where condition
	 * <ul>
	 * <li>Id Aggiudicazione = ?
	 * <li>Data Inizio Aggiudicazione = ?
	 * <li>Id Stato in Accordo = Id Stato in stati scheda
	 * <li>AND (stato = in definizione OR Stato = confermato) 
	 * </ul>
	 */
	
	public  final String SELECT_MANY_ACCORDI = "SELECT "
		+ ACCORDI.ID_ACCORDO
		+ ", " + ACCORDI.DATA_ACCORDO
	    + ", " + ACCORDI.DATA_INIZIO_ACC   
	    + ", " + ACCORDI.DATA_FINE_ACC
	    + ", " + ACCORDI.ID_AGGIUDICAZIONE
	    + ", " + ACCORDI.DATA_INIZIO_AGGIUDICAZIONE
	    + ", " + ACCORDI.T_ID_STATO
	    + ", " + ACCORDI.NUM_RISERVE
	    + ", " + ACCORDI.ONERI_DERIVANTI
	    + "," +ACCORDI.T_ID_STATO + ", " + STATI_SCHEDA.T_DESCRIZIONE + " + " +
		   buildRichAnnQuery(ParametriServletAccordo.TAB_SCHEDA_ACCORDO, ACCORDI.T_ID_ACCORDO,null) 
		+" AS "+STATI_SCHEDA.DESCRIZIONE
		+ ", " + ACCORDI.ID_SCHEDA_LOCALE + " "
	    + " FROM " + ACCORDI.TABLE_NAME + ", " + STATI_SCHEDA.TABLE_NAME   
	    
	    + " WHERE " + ACCORDI.T_ID_AGGIUDICAZIONE + " = ? AND "
	    + ACCORDI.T_DATA_INIZIO_AGGIUDICAZIONE + " = ?  AND "
	    + ACCORDI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
	    +" AND (" + ACCORDI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + ACCORDI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+") order by " + ACCORDI.DATA_ACCORDO; 	
	
	/****************************************************************************************************************
	 * metodo per il caricamento di tutti gli accordi relativi ad un&rsquo;aggiudicazione
	 * 
	 * @param idAggiudicazioni id aggiudicazione per i quali si vuole gli accordi
	 * @param dataInizioAggiudicazione data inizio aggiudicazione associata all&rsquo;id
	 * @return List&lt;AccordoBean&gt;
	 * @throws SQLException eccezione sollevata durante l&rsquo;esecuzione della query SQL
	 */
	public List<AccordoBean> loadMany(long idAggiudicazioni, Timestamp dataInizioAggiudicazione) throws SQLException{
		return load(idAggiudicazioni, dataInizioAggiudicazione, true);
	}
	
	
	/****************************************************************************************************************
	 * <code>private List&lt;AccordoBean&gt; <b>load</b> ( long id, Timestamp date, boolean byAggiudicazione )  
	 * <pre>throws SQLException</pre></code>
	 * metodo per il caricamento di tutti gli accordi associati ad una aggiudicazione oppure un singolo accordo. 
	 * I dati caricati riguardano:
	 * <ul>
	 * <li>Data Accordo
	 * <li>Data Fine Accordo
	 * <li>Data Inizio Accordo
	 * <li>Descrizione stato della scheda
	 * <li>Id Accordo
	 * <li>Id Aggiudicazione
	 * <li>Id Stato
	 * <li>Numero Riserve
	 * <li>Oneri Derivanti
	 * </ul>
	 * param id puo essere di un'aggiudicazione o di un'accordo
	 * param date puo essere di un'aggiudicazione o di un'accordo
	 * param byAggiudicazione flag che serve a distinguere i casi in caso positivo l&rsquo;id e la data 
	 * 		saranno di una aggiudicazione, altrimenti di un&rsquo;accordo
	 * return List&lt;AccordoBean&gt; una lista di accordi, anche vuota
	 * throws SQLException
	 */
	private List<AccordoBean> load(long id, Timestamp date, boolean byAggiudicazione) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		//TableBean result = null;
		ArrayList<AccordoBean> listaSchede = new ArrayList<AccordoBean>();
		AccordoBean bean = null;
		try{
			if(byAggiudicazione) 
				stmt = activeConnection.prepareStatement(SELECT_MANY_ACCORDI);
			else 
				stmt = activeConnection.prepareStatement(SELECT_ONE_ACCORDO + WHERE_STANDARD);
			stmt.setLong(index++, id );
			stmt.setTimestamp(index++, date);
			rs = stmt.executeQuery();
			while(rs.next()){
				bean = new AccordoBean();
				fillBean(rs, bean);
				listaSchede.add(bean);
			}
			listaSchede.trimToSize();
			return listaSchede;
		}finally{
			close(rs,stmt);
		}
	}

	/*********************************************************************************************************
	 * metodo per la storicizzazione del record, usato in richiesta annullamento. Il record vecchio in accordo
	 * passa nello storico sostituito da quello attuale.
	 * 
	 * @param idAccordo Id dell&rsquo;accordo da storicizzare
	 * @param dataInizioAccordo Data Inizio dell&rsquo;accordo da storicizzare
	 * @return nuova data inizio
	 * @throws SQLException eccezione sollevata durante l&rsquo;esecuzione della query SQL
	 */
	public Timestamp copyRecord(long idAccordo, Timestamp dataInizioAccordo) throws SQLException{
		
		String QUERY_SELECT_DATA_FINE = "SELECT " + ACCORDI.DATA_FINE_ACC
		+ " FROM " + ACCORDI.TABLE_NAME
		+ " WHERE " + ACCORDI.ID_ACCORDO + " = ? "
		+ " AND " + ACCORDI.DATA_INIZIO_ACC + " = ?"
		+ " AND " + ACCORDI.ID_STATO + " = " + StatiScheda.CONFERMATO;
		
		String UPDATE_STATO_OLD_ACCORDI = 
			"UPDATE " + ACCORDI.TABLE_NAME + " SET "
			+ ACCORDI.ID_STATO + " = ? "
			+ ", " + ACCORDI.DATA_INIZIO_ACC + " = ?"
			+ ", " + ACCORDI.DATA_FINE_ACC + " = ?"
			+ " WHERE " + ACCORDI.ID_ACCORDO + " = ? "
			+ " AND " + ACCORDI.DATA_INIZIO_ACC + " = ?"
			+ " AND " + ACCORDI.ID_STATO + " = " + StatiScheda.CONFERMATO;
		
		String COPY_RECORD = " INSERT INTO " + ACCORDI.TABLE_NAME + "("
			+ ACCORDI.ID_ACCORDO	
		    + ", " + ACCORDI.DATA_ACCORDO
		    + ", " + ACCORDI.DATA_INIZIO_AGGIUDICAZIONE
		    + ", " + ACCORDI.ID_AGGIUDICAZIONE
		    + ", " + ACCORDI.NUM_RISERVE
		    + ", " + ACCORDI.ONERI_DERIVANTI
		    + ", " + ACCORDI.ID_SCHEDA_LOCALE
		    
		    + ", " + ACCORDI.DATA_INIZIO_ACC
		    + ", " + ACCORDI.DATA_FINE_ACC
		    + ", " + ACCORDI.ID_STATO
		    + ") SELECT "
		    + ACCORDI.ID_ACCORDO
		    + ", " + ACCORDI.DATA_ACCORDO
		    + ", " + ACCORDI.DATA_INIZIO_AGGIUDICAZIONE
		    + ", " + ACCORDI.ID_AGGIUDICAZIONE
		    + ", " + ACCORDI.NUM_RISERVE
		    + ", " + ACCORDI.ONERI_DERIVANTI
		    + ", " + ACCORDI.ID_SCHEDA_LOCALE
		    + ", ?"
			+ ", ?"
			+ ", ?"
			+ " FROM " + ACCORDI.TABLE_NAME
			+ " WHERE " + ACCORDI.ID_ACCORDO + " = ? "
			+ " AND " + ACCORDI.DATA_INIZIO_ACC + " = ?";

		Timestamp dataFine = null;
		Timestamp nuovaDataAccordo = null;
		int index = 1;
		ResultSet rs = null;
		PreparedStatement getDataFine = null;
		PreparedStatement updateRecord = null;
		PreparedStatement copyRecord = null;
		try{
			//prendo la data fine del record
			getDataFine = activeConnection.prepareStatement(QUERY_SELECT_DATA_FINE);
			getDataFine.setLong(index++, idAccordo);
			getDataFine.setTimestamp(index++, dataInizioAccordo);
			rs = getDataFine.executeQuery();
			if(rs.next()){
				dataFine = rs.getTimestamp(ACCORDI.DATA_FINE_ACC);
				
				//il record corrente diventa il nuovo record
				index = 1;
				nuovaDataAccordo = getNow();
				updateRecord = activeConnection.prepareStatement(UPDATE_STATO_OLD_ACCORDI);
				updateRecord.setInt(index++, StatiScheda.IN_DEFINIZIONE);
				updateRecord.setTimestamp(index++, nuovaDataAccordo);
				updateRecord.setNull(index++, Types.TIMESTAMP);
				updateRecord.setLong(index++, idAccordo);
				updateRecord.setTimestamp(index++, dataInizioAccordo);
				updateRecord.execute();
				
				//copy record
				index = 1;
				copyRecord = activeConnection.prepareStatement(createCopyRecord(COPY_RECORD,ACCORDI.TABLE_NAME));
				copyRecord.setTimestamp(index++, dataInizioAccordo);
				copyRecord.setTimestamp(index++, dataFine);
				copyRecord.setInt(index++, StatiScheda.ANNULLAMENTO_RICHIESTA);
				copyRecord.setLong(index++, idAccordo);
				copyRecord.setTimestamp(index++, nuovaDataAccordo);
				
				copyRecord.execute();
				
				return nuovaDataAccordo;
			}
			return null;
		}/*catch(Exception e){	
			
			e.printStackTrace();
			throw new SQLException(e.getMessage());
			
		}*/finally{
			close(rs, getDataFine);
			close(null, updateRecord);
			close(null, copyRecord);
		}
	}
	
	
	/*********************************************************************************************
	 * Il metodo interroga il database per determinare se ci siano contenuti dei record che 
	 * abbiano l&rsquo;id accordo e data inizio acccordo uguali a quelli inseriti come parametri in ingresso. 
	 * 
	 * @param idAccordo
	 * @param dataInizioAccordo
	 * @return boolean
	 * @throws SQLException eccezione sollevata durante l&rsquo;sesecuzione della query SQL
	 */
	public boolean existAccordo(long idAccordo, Timestamp dataInizioAccordo) throws SQLException{

		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM " + ACCORDI.TABLE_NAME + " WHERE " + 
		ACCORDI.ID_ACCORDO + " = ? AND " + 
		ACCORDI.DATA_INIZIO_ACC + " = ?";
		
		try{
			stmt = activeConnection.prepareStatement(query);
			stmt.setLong(1, idAccordo);
			stmt.setTimestamp(2, dataInizioAccordo);
			rs = stmt.executeQuery();
			return (rs.next());
		}finally{
			close(rs, stmt);
		}
	}
	
	/********************************************************************************
	 * Stringa per la formulazione di una PreparedStatement per la cancellazione di un record
	 * dalla tabella ACCORDI in base a Id Accordo e Data Inizio Accordo. 
	 */
	private String DELETE_RECORD_ACCORDI = 
		"DELETE FROM " + ACCORDI.TABLE_NAME
		+ " WHERE " + ACCORDI.ID_ACCORDO + " = ?"
		+ " AND " + ACCORDI.DATA_INIZIO_ACC + " = ?";
	
	/********************************************************************************
	 * metodo che si occupa della rimozione di un record dalla tabella in base ad Id Accordo e 
	 * Data Inizio Accordo. Il metodo restitisce il numero di righe eliminate nella Tabella.
	 * Ci si aspetta un risultato che sia 1, se la cancellazione va a buon fine, 
	 * 0 se non viene identificato il record da cancellare 
	 * 
	 * @param idAccordo
	 * @param dataInizioAccordo
	 * @return affected row count
	 * @throws SQLException eccezione sollevata durante l&rsquo;esecuzione della query SQL
	 */
	public int deleteRecord(long idAccordo, Timestamp dataInizioAccordo)throws SQLException{
		PreparedStatement stmt = null;
		int index = 1;
		try{
			
			stmt = activeConnection.prepareStatement(DELETE_RECORD_ACCORDI);
			stmt.setLong(index++, idAccordo);
			stmt.setTimestamp(index++, dataInizioAccordo);
			return stmt.executeUpdate();
		}finally{
			close(null,stmt);
		}
	}
	
	/**************************************************************************************************************
	 * Stringa per la formulazione di una PreparedStatement che aggiorna il campo Id Stato ad un valore
	 * assegnato del record identificato tramite Id Accordo e Data Inizio Accordo.  
	 * 
	 */
	private static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO = 
		"UPDATE "+ACCORDI.TABLE_NAME+
		" SET " + ACCORDI.ID_STATO + " = ?," 
		+ ACCORDI.DATA_FINE_ACC + " = " + buildGetDate()
		+ " WHERE " + ACCORDI.ID_ACCORDO + " = ?"
		+ " AND " + ACCORDI.DATA_INIZIO_ACC + " = ?";
	
	/***************************************************************************************************************
	 * Metodo che si occupa dell&rsquo;aggiornamento di un record, identificato tramite 
	 * id Accordo e Data Inizio Accordo, impostando l&rsquo;id stato al valore inserito nel campo String
	 * Il metodo restituisce il numero di righe che risultano essere state aggiornate sul database. 
	 * 
	 * @param idAccordo
	 * @param dataInizioAccordo
	 * @param statoScheda Stringa che indica il nuovo stato che verr&agrave; inserito nel record. 
	 * @return int affected row count. Ci si apetta il valore 1 nel caso la query abbia buon esito, 
	 * 0 nel caso in cui non venga identificato correttamente il record. 
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public int updateRecord(long idAccordo, Timestamp dataInizioAccordo, String statoScheda ) throws SQLException{
		int numRow=-1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO);

			stmt.setString(1, statoScheda);
			stmt.setLong(2, idAccordo);
			
			stmt.setTimestamp(3,dataInizioAccordo);
		
			numRow = stmt.executeUpdate();
		
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}
	
	/*********************************************************************************************************/
	/**************************		NUOVE FUNZIONALITA' 	**************************************************/
	/*********************************************************************************************************/	
	
	/**
	 * @param idLocale
	 * @param idAggiudicazione
	 * @return
	 * @throws SQLException
	 */
	public AccordoBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException{ 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		AccordoBean bean = null;
		try{
			stmt = activeConnection.prepareStatement(SELECT_ONE_ACCORDO + WHERE_IDLOCALE);
			int index = 1;
			stmt.setString(index++, idLocale );
			long idAggiudicazioneL = Long.parseLong(idAggiudicazione);
			stmt.setLong(index++, idAggiudicazioneL );
			rs = stmt.executeQuery();
			bean = new AccordoBean();
			if(rs.next()){
				this.fillBean(rs, bean); 
			}
			return  bean;
		}finally{
			close(rs,stmt);
		}

	}
	/**
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 */
	public AccordoBean loadByIdSimog(long idSimog) throws SQLException{ 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		AccordoBean bean = null;
		try{
			stmt = activeConnection.prepareStatement(SELECT_ONE_ACCORDO + WHERE_IDSIMOG);
			int index = 1;
			stmt.setLong(index++, idSimog );
			rs = stmt.executeQuery();
			bean = new AccordoBean();
			if(rs.next()){
				this.fillBean(rs, bean); 
			}
			return  bean; 
		}finally{
			close(rs,stmt);
		}

	}
	/**
	 * @param rs
	 * @param bean
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, AccordoBean bean) throws SQLException {
		bean.setDataAccordo(PageHelper.getViewDate(rs.getString(ACCORDI.DATA_ACCORDO)));
		bean.setDataFineAccordo(rs.getTimestamp(ACCORDI.DATA_FINE_ACC));
		bean.setDataInizioAccordo(rs.getTimestamp(ACCORDI.DATA_INIZIO_ACC));
		bean.setDescrizioneStato(rs.getString(STATI_SCHEDA.DESCRIZIONE));
		bean.setIdAccordo(rs.getLong(ACCORDI.ID_ACCORDO));
		bean.setIdAggiudicazione(rs.getLong(ACCORDI.ID_AGGIUDICAZIONE));
		bean.setDataInizioAggiudicazione(rs.getTimestamp(ACCORDI.DATA_INIZIO_AGGIUDICAZIONE));
		bean.setIdStato(rs.getLong(ACCORDI.ID_STATO));
		bean.setNumeroRiserve(rs.getInt(ACCORDI.NUM_RISERVE));
		bean.setOneriDerivanti(rs.getBigDecimal(ACCORDI.ONERI_DERIVANTI));
		bean.setOneriDerivantiStr(PageHelper.formattaImporto(rs.getBigDecimal(ACCORDI.ONERI_DERIVANTI)));
		bean.setDescrizioneStato(rs.getString(STATI_SCHEDA.DESCRIZIONE));
		/**...**/
		bean.setIdLocale(rs.getString(ACCORDI.ID_SCHEDA_LOCALE));
	}
	/**
	 * Eliminazione scheda tramite identificativo del sistema remoto e CIG
	 * 
	 * @param idLocale
	 * @param cig
	 * @param cfUtente
	 * @return boolean : true se l'operazione ha effettuato qualche modifica (update) false altrimenti 
	 * @throws SQLException
	 */
	public boolean annulla(String idLocale, String idAggiudicazione, String cfUtente) throws SQLException{
		AccordoBean accordoBean = loadByIdLocale(idLocale, idAggiudicazione);
		
		if (accordoBean.getIdAccordo() > 0){
			return _annulla(accordoBean.getIdAccordo(), accordoBean.getDataInizioAccordo(), cfUtente);
		}
		
		return false;
	}
	
	/** Eliminazione scheda tramite identificativo simog
	 * @param idLocale
	 * @param cfUtente
	 * @return boolean : true se l'operazione ha effettuato qualche modifica (update) false altrimenti 
	 * @throws SQLException
	 */
	public boolean annulla(long idSimog, String cfUtente) throws SQLException{
		AccordoBean accordoBean = loadByIdSimog(idSimog);
		
		if (accordoBean.getIdAccordo() > 0){
			return _annulla(accordoBean.getIdAccordo(), accordoBean.getDataInizioAccordo(), cfUtente);
		}
		
		return false;
	}
	/** Eliminazione scheda tramite id e dataInizio della scheda.
	 * 
	 * @param idAccordo
	 * @param dataInizioAccordo
	 * @param cfUtente
	 * @return boolean : true se l'operazione ha effettuato qualche modifica (update) false altrimenti 
	 * @throws SQLException
	 */
	public boolean annulla(long idAccordo, Timestamp dataInizioAccordo, String cfUtente) throws SQLException{
		return _annulla(idAccordo, dataInizioAccordo, cfUtente);
	}

	/**
	 * @param idAccordo
	 * @param dataInizioAccordo
	 * @param cfUtente
	 * @return boolean : true se l'operazione ha effettuato qualche modifica (update) false altrimenti 
	 * @throws SQLException
	 */
	private boolean _annulla(long idAccordo, Timestamp dataInizioAccordo, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_ACCORDO);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idAccordo);
			stmt.setTimestamp(index++, dataInizioAccordo);
			someRowAffected = stmt.executeUpdate() > 0;
			// se c'� almeno una riga modificata dall'update effettua il Log blocco dati
			if(someRowAffected){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idAccordo);
				attributiChiave.add(dataInizioAccordo);
				// update per settare tutto a NULL
					LogBloccoDatiManager.loggingDELETE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_ACCORDO, attributiChiave);
			}
			return someRowAffected;
		 }
		finally {
			close(null,stmt);
		}

	}

//	/**
//	 * see it.avcp.simog.managers.comportamento.annullamento.IAnnullamento#annullaByAggiudicazione(long, java.sql.Timestamp, java.lang.String)
//	 */
//	public boolean annullaByAggiudicazione(long idAggiudicazione,Timestamp dataInizioAggiudicazione, String cfUtente) throws SQLException {
//		List<AccordoBean> listOfAccordi = this.loadMany(idAggiudicazione, dataInizioAggiudicazione);
//		boolean esitOperazione = listOfAccordi.size() > 0 ? true : false;
//		for(AccordoBean accordoCorrente : listOfAccordi){
//			esitOperazione = esitOperazione && _annulla(accordoCorrente.getIdAccordo(), accordoCorrente.getDataInizioAccordo(), cfUtente);
//		}
//		return esitOperazione;
//	}	
}




