package it.avcp.simog.managers.luogo;

import it.avlp.simog.beans.aggiudicazione.luogo.IstatBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.CODICI_ISTAT;
import it.avlp.simog.db.generated.COMUNI_VIEW;
import it.avlp.simog.db.generated.REGIONE_PROVINCIA;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * Classe per la gestione dei codici istat
 *
 */
public class IstatManager extends AccessiDB {

	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public IstatManager(Connection currentActiveConnection,Logger logger) {
		super(currentActiveConnection, logger);	
	}
	
	private final String GET_REGIONI = 	
		"SELECT DISTINCT " + COMUNI_VIEW.ID_REGIONE + "," 
		+ COMUNI_VIEW.DENOM_REGIONE 

		+ " FROM "
		+ COMUNI_VIEW.TABLE_NAME + " ORDER BY " + COMUNI_VIEW.DENOM_REGIONE;
	
	private final String GET_ALL_ISTAT = 
		", " + COMUNI_VIEW.TABLE_NAME + ".*" 
		+ " FROM "
		+ COMUNI_VIEW.TABLE_NAME;
		
	private final String GET_ALL_PROVINCE_ISTAT = 
		"SELECT DISTINCT " + COMUNI_VIEW.ID_REGIONE + "," 
		+ COMUNI_VIEW.DENOM_REGIONE + ","
		+ COMUNI_VIEW.ID_PROVINCIA + "," 
		+ COMUNI_VIEW.DENOM_PROVINCIA
		+ " FROM "
		+ COMUNI_VIEW.TABLE_NAME +
		" WHERE " + COMUNI_VIEW.ID_REGIONE + " =?";
	
	private final String GET_ALL_COMUNI = 
		"SELECT DISTINCT " + COMUNI_VIEW.TABLE_NAME + ".*"
		+ " FROM "
		+ COMUNI_VIEW.TABLE_NAME
		+ " WHERE " + COMUNI_VIEW.ID_REGIONE + " =?"
		+ " AND " + COMUNI_VIEW.ID_PROVINCIA + " =?";
	
	private final String ORDERBY_CODICE =
		" ORDER BY " + COMUNI_VIEW.ID_REGIONE + "," + COMUNI_VIEW.DENOM_PROVINCIA + "," 
			+ COMUNI_VIEW.DENOMINAZIONE ;
	private final String ORDERBY_RP_CODICE =
		" ORDER BY " + COMUNI_VIEW.ID_REGIONE + "," + COMUNI_VIEW.DENOM_PROVINCIA;
			
	
	
	
	/**
	 * metodo che restituisce 
	 * param ResultSet: rs
	 * param boolean: allReg
	 * param boolean: comuni
	 * return List<IstatBean>
	 * throws SQLException
	 */
	private List<IstatBean> copyToArray(ResultSet rs, boolean allReg,boolean comuni) throws SQLException{
		LinkedList<IstatBean> listaIstat = new LinkedList<IstatBean>();
		//try {
			IstatBean bean = null;
			while (rs.next()){
				bean = new IstatBean();
				bean.setIdRegione(rs.getString(COMUNI_VIEW.ID_REGIONE));
				bean.setDenomRegione(rs.getString(COMUNI_VIEW.DENOM_REGIONE));
				if(!allReg){
					bean.setIdProvincia(rs.getString(COMUNI_VIEW.ID_PROVINCIA));
					bean.setDenomProvincia(rs.getString(COMUNI_VIEW.DENOM_PROVINCIA));
					if(comuni){
						bean.setIdComune(rs.getString(COMUNI_VIEW.ID_COMUNE));
						bean.setDenomComune(rs.getString(COMUNI_VIEW.DENOMINAZIONE));
					}
				}
				listaIstat.add(bean);
 			}
		/*} catch ( Exception e ) {
			e.printStackTrace();
			return null;}*/
		
		return listaIstat;
	}
		
	
	
	
	/**
	 * metodo per il recupero di una lista di codici istat in base alle stringhe in ingresso
	 * 
	 * @param idRegione String
	 * @param idProvincia String
	 * @param idComune String
	 * @return List&lt;IstatBean&gt;
	 * @throws SQLException
	 */
	public synchronized List<IstatBean> getBranch(String idRegione, String idProvincia, String idComune) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int i = 1;
		String lQuery = null;
		try {
			if(idRegione == null){
			stmt = activeConnection.prepareStatement(GET_REGIONI);
			}else{
				
				if(!"000".equals(idProvincia))
					lQuery =GET_ALL_COMUNI + ORDERBY_CODICE;
				else lQuery = GET_ALL_PROVINCE_ISTAT + ORDERBY_RP_CODICE;
				stmt = activeConnection.prepareStatement(lQuery);
				stmt.setString(i++, idRegione);
				if(!"000".equals(idProvincia))
					stmt.setString(i++, idProvincia);
			}	
			//logger.debug(lQuery);
			rs = stmt.executeQuery();
			return copyToArray(rs,idRegione == null,!idProvincia.equals("000"));
		} catch ( Exception ex ) {ex.printStackTrace(); return new LinkedList<IstatBean>();}
		finally {
			close(rs, stmt);
		}
	}
	
	/**
	 * metodo per il recupero dei codici istat di cui chiave
	 * 
	 * @param chiavi String
	 * @return List&lt;IstatBean&gt;
	 * @throws SQLException
	 */
	public List<IstatBean> getVoci(String chiavi) throws SQLException{
		PreparedStatement stmt = null;
		StringTokenizer tokenChiavi = null;
		
		String condizioniChiavi = " WHERE 1=1";
		String pesatura = "";
		String currToken = "";
		
//		logger.debug("Ricerca voci ISTAT");

		if (chiavi!= null && chiavi.trim().length() > 0 ) {
			tokenChiavi = new StringTokenizer( chiavi.toLowerCase() );
			currToken = getCleanToken( tokenChiavi.nextToken() );
			
			condizioniChiavi += " AND (lower(" + COMUNI_VIEW.DENOMINAZIONE + ") LIKE '%" + currToken + "%'";
			//pesatura = "SELECT sign(charindex('" + currToken+ "',lower("+ COMUNI_VIEW.DENOMINAZIONE + "))) ";
			//virtuale
			pesatura = "SELECT sign(locate('" + currToken+ "',lower("+ COMUNI_VIEW.DENOMINAZIONE + "))) ";
			
			while ( tokenChiavi.hasMoreElements() ) {
				currToken = getCleanToken( tokenChiavi.nextToken() );
				condizioniChiavi += " OR lower(" + COMUNI_VIEW.DENOMINAZIONE + ") LIKE '%" + currToken + "%'";
				//pesatura += " + sign(charindex('" + currToken+ "',lower("+ COMUNI_VIEW.DENOMINAZIONE + "))) ";
				//virtuale
				pesatura += " + sign(locate('" + currToken+ "',lower("+ COMUNI_VIEW.DENOMINAZIONE + "))) ";
			}
			condizioniChiavi = condizioniChiavi + ")";
		}
		ResultSet rs = null;
 		try {
 			logger.debug(pesatura + GET_ALL_ISTAT + condizioniChiavi + " order by 1 desc");
 			stmt = activeConnection.prepareStatement(pesatura + GET_ALL_ISTAT + condizioniChiavi + " order by 1 desc");
 			//stmt.setTimestamp(1, getNow());
 			rs = stmt.executeQuery();		
			
			return copyToArray(rs,false,true);
 		} catch ( Exception ex ) {ex.printStackTrace(); return new LinkedList<IstatBean>();}
 		finally {
 			close(rs,stmt);
// 			try {
// 				stmt.close();
// 			} catch ( Exception e ) {}
// 			stmt = null;
 		}
	}
	
	
	private String getCleanToken ( String currentToken ) {
		if ( currentToken.contains("\'") ) {
			int apostrophePosition = currentToken.indexOf("'");
			String firstPart = currentToken.substring(0, apostrophePosition);
			String second = currentToken.substring(apostrophePosition + 1);
			currentToken = firstPart + "''" + second;
		}
		return currentToken;
	}
	
	
	/**
	 * metodo per il controllo di validita' di un codice istat
	 * 
	 * @param codiceIstat String
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isValid(String codiceIstat,Object data) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		boolean retVal = false;
		
		String query = "SELECT " + CODICI_ISTAT.ID_COMUNE + " FROM " + CODICI_ISTAT.TABLE_NAME +" WHERE "
		+ CODICI_ISTAT.ID_COMUNE + "=?";
		
		if(!SimogFlags.isFromMassLoader()){
			query = query 
			+ " AND " + localBuildISNULL(CODICI_ISTAT.DATA_FINE_VALIDITA,"99999999") + " >= ?"
			+ " ORDER BY " + localBuildISNULL(CODICI_ISTAT.DATA_FINE_VALIDITA,"99999999"); 
		}
		
		try{
//			logger.debug("[query] - check istat["+codiceIstat+"]" + query);
			stmt = activeConnection.prepareStatement(query);
			stmt.setString(1, codiceIstat);
			
			if(!SimogFlags.isFromMassLoader()){
				stmt.setObject(2,PageHelper.getFormattedNowOrInputFormattedDate(data, getNow()));				
			}

	      rs = stmt.executeQuery();
         if(rs.next()){
            if(rs.getString(CODICI_ISTAT.ID_COMUNE).equals(codiceIstat))
               retVal = true;
         }
         return retVal;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}finally{
			close(rs, stmt);
		}
	}
	
	/***
	 * wrapper per diversità di HSQLDB
	 * @param field
	 * @param nullValue
	 * @return
	 */
	private String localBuildISNULL(String field, Object nullValue){
		
		String retVal = "";
		try {
			retVal = this.activeConnection.getMetaData().getDatabaseProductName().indexOf("HSQL") >= 0
					 ? buildISNULLHsqlDb(field, nullValue) : buildISNULL(field, nullValue);
		} catch (SQLException e) {}

		return retVal;
	}	

	//TICKET ALM - 3.04.4 verifica l'esistenza del codice regione
	/**
	 * Metodo che verifica la correttezza del codice regione ISTAT
	 */
	public boolean isRegioneValid(String codRegione) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		boolean retVal = false;
		
		String query = "SELECT " + REGIONE_PROVINCIA.ID_REGIONE + " FROM " + REGIONE_PROVINCIA.TABLE_NAME +" WHERE "
		+ REGIONE_PROVINCIA.ID_REGIONE + "=?";
		
		try{
//			logger.debug("[query] - check istat["+codiceIstat+"]" + query);
			stmt = activeConnection.prepareStatement(query);
			stmt.setString(1, codRegione);

	      rs = stmt.executeQuery();
         if(rs.next()){
            if(rs.getString(REGIONE_PROVINCIA.ID_REGIONE).equals(codRegione))
               retVal = true;
         }
         return retVal;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}finally{
			close(rs, stmt);
		}
	}
}
