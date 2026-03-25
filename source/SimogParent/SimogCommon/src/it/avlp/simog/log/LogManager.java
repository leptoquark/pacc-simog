package it.avlp.simog.log;

import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.LOG;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.util.SimogProperties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

public class LogManager extends AccessiDB {
	
	public static final String INS_LOTTO = "Creazione Lotto";
	public static final String MOD_LOTTO = "Modifica Lotto";
	public static final String DEL_LOTTO = "Cancellazione Lotto";
	public static final String PERF_LOTTO = "Perfezionamento Lotto";
	public static final String PERF_LOTTO_PR1 = "Perfezionamento Lotto PR Fase 1";
	// public static final String PERF_LOTTO_PR2 = "Perfezionamento Lotto PR Fase 2";
	public static final String MOD_ESCLUSO = "Modifica Esclusione";
    public static final String MOD_RIPETIZ = "Modifica Ripetizione";
    public static final String RIPR_LOTTO = "Ripristino Lotto";
    public static final String INT_CUP = "Integrazione CUP";
  //MEV 37010 3.04.8.1
    public static final String INT_PARI_OPPORTUNITA = "Integrazione Pari Opportunita";
    
  //MEV 53643 3.04.13
    public static final String MODFIFICA_CPV = "Modifica CPV";
    
    /* MAD 68089 3.04.16 */
    public static final String MODFIFICA_CAT_SOA = "Modifica Categoria SOA: ";
    
  //MEV 3.04.10 43227
    public static final String INT_MODIFICA_DATI_PERFEZIONAMENTO = "Modifica dati perfezionamento";
	
	
	public static final String INS_GARA = "Creazione Gara";
	public static final String SAV_GARA = "Salvataggio Gara";
	public static final String MOD_GARA = "Modifica Gara";
	public static final String DEL_GARA = "Cancellazione Gara";
    public static final String RIPR_GARA = "Ripristino Gara";
	public static final String PERF_GARA = "Conferma Gara";
	public static final String SBLOCCO_GARA = "Sblocco Gara";
   public static final String PRESA_CARICO = "Presa in carico Gara";
	public static final String VAR_SA = "Variazione S.A.";
	public static final String PUBB_GARA_LOTTI = "Pubblicazione Gara";
	public static final String PERF_GARA_LOTTI = "Perfezionamento Gara";
	public static final String RETTIFICA_BANDO_SENZA_SOSPENSIONE = "Rettifica Bando";
	public static final String RETTIFICA_BANDO_CON_SOSPENSIONE = "Rettifica Bando (in sospeso)";
	public static final String CONFERMA_RETTIFICA = "Conferma Rettifica (Admin)";

    public static final String MOD_REQUISITI = "Modifica Requisiti";

// INT87
    public static final String MOD_DL133 = "Modifica DL133";
    
    //TICKET ALM - 3.04.3
    public static final String UPD_PRESA_CARICO_DELEGATA = "Presa in carico gara delegata";

	private final String insertion =
		
		"INSERT INTO "
		+ LOG.TABLE_NAME
		+" ("
		+ LOG.ID_SA_RIFERIMENTO
		+ ", " + LOG.CF_UTENTE
		+ ", " + LOG.CIG_LOTTO
		+ ", " + LOG.DATA_MODIFICA
		+ ", " + LOG.DESCRIZIONE_AZIONE
		+ ", " + LOG.CF_AMMINISTRAZIONE
		+ ", " + LOG.ID_LOTTO
		+ ", " + LOG.ID_GARA
		+ ") " +

		"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
	
	
	public LogManager(Connection connection, Logger logger){
		super(connection, logger);
	}
	
	/********************************************************************
	 * Effettua un inserimento sul log amministrativo restituendo il 
	 * numero di elementi inseriti
	 * @param dataModifica String
	 * @param cfSARiferimento String
	 * @param cfUtente String
	 * @param cigLotto String
	 * @param descrizioneAzione String
	 * @param cf_amministrazione String
	 * @param IdLotto String
	 * @param IdGara String
	 * @return int
	 * @throws SQLException
	 */
	public int log(String dataModifica, String cfSARiferimento,String cfUtente, String cigLotto,
		String descrizioneAzione, String cf_amministrazione, String IdLotto, String idGara) throws SQLException{
		
		PreparedStatement ps = activeConnection.prepareStatement(insertion);
		
		//TICKET ALM #6754
		if(descrizioneAzione.equals(INS_GARA))
			dataModifica = new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
		//FINE TICKET ALM #6754
		
		logger.debug( "Tentativo di inserimento su log amministrativo");
		logger.debug( "QUERY: ["+ insertion +"]");
		

//		logger.debug( "Tentativo di inserimento su log amministrativo cfsariferimento[" + cfSARiferimento + "]");
//		logger.debug( "Tentativo di inserimento su log amministrativo cfutente [" + cfUtente + "]");
//		logger.debug( "Tentativo di inserimento su log amministrativo ciglotto [" + cigLotto + "]");
//		logger.debug( "Tentativo di inserimento su log amministrativo datamodifica [" + dataModifica + "]");
//		logger.debug( "Tentativo di inserimento su log amministrativo cfamministrazione [" + cf_amministrazione + "]");
//		logger.debug( "Tentativo di inserimento su log amministrativo idlotto [" + IdLotto + "]");
		
		ps.setObject(1, cfSARiferimento);
		ps.setObject(2, cfUtente);
		ps.setObject(3, cigLotto);
		ps.setObject(4, dataModifica);
		ps.setObject(5, descrizioneAzione);
		ps.setObject(6, cf_amministrazione);
		if ("".equals(IdLotto))						// Se IdLotto è null sto loggando una gara
			ps.setNull(7, java.sql.Types.VARCHAR);
		else
		ps.setObject(7, IdLotto);
		ps.setObject(8, idGara);
		
		int executionResult = ps.executeUpdate();
		
		
		try {
			ps.close();
		} catch ( Exception e ) {}
		ps = null;
		
		return executionResult;
	}
	
	
	
	/**
	 * Ottiene la tablebean dei log 	(DEPRECATO)
	 *
	public TableBean getLog(
			String saRiferimento,
			String cfUtente,
			String cigLotto,
			String cfAmministrazione,
			String fromData,
			String toData,
			String orderField,
			boolean ascDesc)
			
			throws SQLException {
		
		
		String query =
			"SELECT "
			+ LOG.T_ID_LOTTO
			+ ", " + LOTTO.T_CIG + "+" + LOTTO.T_CIG_KKK + " AS " + LOTTO.CIG
			+ ", " + LOTTO.T_SOMMA_URGENZA
			+ ", " + LOG.T_DESCRIZIONE_AZIONE
			+ ", " + LOG.T_CF_UTENTE
			+ ", " + LOG.T_ID_SA_RIFERIMENTO
			+ ", " + LOG.T_CF_AMMINISTRAZIONE
			+ ", " + LOG.T_DATA_MODIFICA
			+ ", " + LOG.T_ID_GARA
			+ " FROM "
			+ LOTTO.TABLE_NAME
			+ ", " + LOG.TABLE_NAME
			+ " WHERE "
			+ LOG.T_ID_LOTTO + "=" + LOTTO.T_ID_LOTTO;
		
		if(fromData != null && toData!= null){
			query+= " AND "
			+ LOG.T_DATA_MODIFICA
			+ " BETWEEN ? AND ? ";
		}

		if ( saRiferimento != null && ! "".equalsIgnoreCase(saRiferimento) ) {
			logger.debug ( "Aggiunta condizione CF_SA_RIFERIMENTO [" + saRiferimento + "]" );
			query += " AND " + LOG.ID_SA_RIFERIMENTO + "= ? " ;
		}
		
		if( cfUtente != null && ! "".equalsIgnoreCase(cfUtente) ) {
			logger.debug ( "Aggiunta condizione CF_UTENTE [" + cfUtente + "]" );
			query += " AND " + LOG.CF_UTENTE + " = ?";
		}
		
		if ( cigLotto != null && ! "".equalsIgnoreCase(cigLotto) ) {
			logger.debug ( "Aggiunta condizione CIG_LOTTO [" + cigLotto + "]" );
			query += " AND " + getQueryConditionByCIGSommaUrgenza(cigLotto);
			
		}
		
		if ( cfAmministrazione != null && ! "".equalsIgnoreCase(cfAmministrazione) ) {
			logger.debug ( "Aggiunta condizione CF_AMMINISTRAZIONE [" + cfAmministrazione + "]" );
			query += " AND " + LOG.CF_AMMINISTRAZIONE + " = ? ";
		}

		
		if ( orderField != null ) {
			String ascDescStr = ascDesc ? " ASC " : " DESC ";
			orderField = orderField + ascDescStr;
		} else {
			orderField = LOG.DATA_MODIFICA + " DESC";
		}
		
		query += " ORDER BY " + orderField;
		
		logger.debug("Esecuzione query [" + query + "]");
		
		PreparedStatement st = activeConnection.prepareStatement(query);
		
		int i=1;
		
		
		
		
		if(fromData != null && toData!= null){
			st.setString(i++, fromData);
			st.setString(i++, toData);
		}
		
		if ( saRiferimento != null && ! "".equalsIgnoreCase(saRiferimento) ) {
			st.setString(i++, saRiferimento);
		}
		
		if( cfUtente != null && ! "".equalsIgnoreCase(cfUtente) ) {
			st.setString(i++, cfUtente);
		}
				
		if ( cfAmministrazione != null && ! "".equalsIgnoreCase(cfAmministrazione) ) {
			st.setString(i++, cfAmministrazione);
		}

		
		
		
		ResultSet rs = st.executeQuery();
		
		TableBean tb = new TableBean(rs);
		close(rs,st);
		return tb;
	}
*/
	
	
	/**
	 *
	 * Ottiene la tablebean dei log
	 */
	public TableBean getLog(
			String saRiferimento,
			String cfUtente,
			String cigLotto,
			String cfAmministrazione,
			String idGara,
			String fromData,
			String toData,
			String orderField,
			boolean ascDesc,
			int startRow,
			int maxRowsAllowed)
			
			throws SQLException {
		
		
		String query =
			"SELECT "
         //+ LOG.T_ID_RECORD	+ ", " 
			+ LOG.T_ID_LOTTO
			+ ", " + LOTTO.T_CIG + "+" + LOTTO.T_CIG_KKK + " AS " + LOTTO.CIG
			+ ", " + LOTTO.T_SOMMA_URGENZA
			+ ", " + LOTTO.T_DATA_CREAZIONE_LOTTO
			+ ", " + LOG.T_DESCRIZIONE_AZIONE
			+ ", " + LOG.T_CF_UTENTE
			+ ", " + LOG.T_ID_SA_RIFERIMENTO
			+ ", " + LOG.T_CF_AMMINISTRAZIONE
			+ ", " + LOG.T_DATA_MODIFICA
			+ ", " + LOG.T_ID_GARA
			+ " FROM "
			+ LOG.TABLE_NAME
			+ " LEFT JOIN " + LOTTO.TABLE_NAME
			+ " ON "
			+ LOG.T_ID_LOTTO + "=" + LOTTO.T_ID_LOTTO
			+ " WHERE 1 = 1 ";
		
		if(fromData != null && toData!= null){
			query+= " AND "
			+ LOG.T_DATA_MODIFICA
			+ " BETWEEN ? AND ? ";
		}

		if ( saRiferimento != null && ! "".equalsIgnoreCase(saRiferimento) ) {
			logger.debug ( "Aggiunta condizione CF_SA_RIFERIMENTO [" + saRiferimento + "]" );
			query += " AND " + LOG.ID_SA_RIFERIMENTO + "= ? " ;
		}
		
		if( cfUtente != null && ! "".equalsIgnoreCase(cfUtente) ) {
			logger.debug ( "Aggiunta condizione CF_UTENTE [" + cfUtente + "]" );
			query += " AND " + LOG.CF_UTENTE + " = ?";
		}
		
		if ( cigLotto != null && ! "".equalsIgnoreCase(cigLotto) ) {
			logger.debug ( "Aggiunta condizione CIG_LOTTO [" + cigLotto + "]" );
			query += " AND " + getQueryConditionByCIGSommaUrgenza(cigLotto);
		}
		
		if ( cfAmministrazione != null && ! "".equalsIgnoreCase(cfAmministrazione) ) {
			logger.debug ( "Aggiunta condizione CF_AMMINISTRAZIONE [" + cfAmministrazione + "]" );
			query += " AND " + LOG.CF_AMMINISTRAZIONE + " = ? ";
		}

		if ( idGara != null && ! "".equalsIgnoreCase(idGara) ) {
			logger.debug ( "Aggiunta condizione ID_GARA [" + idGara + "]" );
			query += " AND " + LOG.T_ID_GARA + " = ? ";
		}
		
		if ( orderField != null ) {
			String ascDescStr = ascDesc ? " ASC " : " DESC ";
			orderField = orderField + ascDescStr;
		} else {
			orderField = LOG.DATA_MODIFICA + " DESC";
		}
		
		query += " ORDER BY " + orderField;
		
		logger.debug("Esecuzione query [" + query + "]");
		
		PreparedStatement st = null;
		ResultSet rs = null;
		TableBean tb = null;
		try{
	    	st = activeConnection.prepareStatement(query);	
	    	int i=1;
    		if(fromData != null && toData!= null){
	    		st.setString(i++, fromData);
	    		st.setString(i++, toData);
	    	}
		
	    	if ( saRiferimento != null && ! "".equalsIgnoreCase(saRiferimento) ) {
		    	st.setString(i++, saRiferimento);
	    	}
		
	    	if( cfUtente != null && ! "".equalsIgnoreCase(cfUtente) ) {
		    	st.setString(i++, cfUtente);
	    	}
				
	    	if ( cfAmministrazione != null && ! "".equalsIgnoreCase(cfAmministrazione) ) {
		    	st.setString(i++, cfAmministrazione);
	    	}

	    	if ( idGara != null && ! "".equalsIgnoreCase(idGara) ) {
		    	st.setString(i++, idGara);
	    	}
		
	    	logger.debug("inner begin");
	    	rs = st.executeQuery();
	    	logger.debug("inner end");
		    tb = new TableBean(rs, startRow, maxRowsAllowed);
		    logger.debug("end load tablebean");
		}
		finally{
		    close(rs,st);
		}
		return tb;
	}	
	
	public String getCFRUP(long idGara) throws SQLException {
		String res = "";
		String query = "SELECT "+LOG.CF_UTENTE+" FROM "+LOG.TABLE_NAME+" WHERE "+LOG.ID_GARA+" = ? AND "+LOG.DESCRIZIONE_AZIONE+" = ?";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = activeConnection.prepareStatement(query);	
			st.setLong(1, idGara);
			st.setString(2, INS_GARA);
			
			rs = st.executeQuery();
			if(rs.next())
				res = rs.getString(1);
			
		}finally{
		    close(rs,st);
		}
		return res;
	}
	
}