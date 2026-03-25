package it.avcp.simog.managers.luogo;

import it.avlp.simog.beans.aggiudicazione.luogo.NutsBean;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.generated.CODICI_NUTS;
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
 * Classe per la gestione dei dati relativi ai codici nuts
 *
 */
public class NutsManager extends AccessiDB {

	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public NutsManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
		
	}
	
	private final String GET_NUTS_BASE =
		"SELECT " + CODICI_NUTS.TABLE_NAME + ".* "
		+ " FROM " + CODICI_NUTS.TABLE_NAME;
	
	private final String WHERE_BASE = 	" WHERE " + localBuildISNULL(CODICI_NUTS.DATA_FINE_VALIDITA,"99999999") + " >= ?";
	
	private final String WHERE_BASE_LIVELLO = 
		WHERE_BASE	+ " AND " + CODICI_NUTS.LIVELLO + " = ? ";
	
	private final String COND_NUTS_LIKE = 
		" AND " + CODICI_NUTS.ID_NUTS + " LIKE ? ";
	private final String ORDER_BY = " ORDER BY " + CODICI_NUTS.LIVELLO +  " , " + CODICI_NUTS.DESCRIZIONE;
	
	private final String RICERCA_BASE = 
		", " + CODICI_NUTS.TABLE_NAME + ".*"
		+ " FROM " + CODICI_NUTS.TABLE_NAME;
	
	private List<NutsBean> copyToArray(ResultSet rs) throws SQLException{
		LinkedList<NutsBean> listaIstat = new LinkedList<NutsBean>();
	//	try {
			NutsBean bean = null;
			while (rs.next()){
				bean = new NutsBean();
				bean.setLivello(rs.getInt(CODICI_NUTS.LIVELLO));
				bean.setIdNuts(rs.getString(CODICI_NUTS.ID_NUTS));
				bean.setDescrizione(rs.getString(CODICI_NUTS.DESCRIZIONE));
				listaIstat.add(bean);
 			}
		/*} catch ( Exception e ) {
			e.printStackTrace();
			return null;}*/
		
		return listaIstat;
	}
	
	/**
	 * metodo per il recupero di una lista di nuts di cui livello e id
	 * 
	 * @param livello int
	 * @param idNuts String
	 * @return List&lt;NutsBean&gt;
	 * @throws SQLException
	 */
	public List<NutsBean> getBranch(int livello, String idNuts) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int i = 1;
		String lQuery = GET_NUTS_BASE + WHERE_BASE_LIVELLO;
		
		try{
			if(livello != 1)
				lQuery += COND_NUTS_LIKE;
			
			lQuery += ORDER_BY;
			
			stmt = activeConnection.prepareStatement(lQuery);
			stmt.setString(i++, PageHelper.getDBDateFromTS(getNow()));
			stmt.setInt(i++, livello);
			if(livello != 1){
				stmt.setString(i++, idNuts + "%");
			}
			rs = stmt.executeQuery();
			
			return copyToArray(rs);
			
		} catch ( Exception ex ) {ex.printStackTrace(); return new LinkedList<NutsBean>();}
		finally {
			close(rs, stmt);
		}
	
	}
	
	/**
	 * metodo per il recupero di una lista di nuts di cui chiavi
	 * 
	 * @param chiavi String
	 * @return List&lt;NutsBean&gt;
	 * @throws SQLException
	 */
	public List<NutsBean> getVoci(String chiavi) throws SQLException{
		PreparedStatement stmt = null;
		StringTokenizer tokenChiavi = null;
		
		String condizioniChiavi = WHERE_BASE;
		String pesatura = "";
		String currToken = "";
		
		//logger.debug("Ricerca voci ISTAT");

		if (chiavi!= null && chiavi.trim().length() > 0 ) {
			tokenChiavi = new StringTokenizer( chiavi.toLowerCase() );
			currToken = getCleanToken( tokenChiavi.nextToken() );
			
			condizioniChiavi += " AND (lower(" + CODICI_NUTS.DESCRIZIONE + ") LIKE '%" + currToken + "%'";
			//pesatura = "SELECT sign(charindex('" + currToken+ "',lower("+ CODICI_NUTS.DESCRIZIONE + "))) ";
			//virtuale 
			pesatura = "SELECT sign(locate('" + currToken+ "',lower("+ CODICI_NUTS.DESCRIZIONE + "))) ";
			
			while ( tokenChiavi.hasMoreElements() ) {
				currToken = getCleanToken( tokenChiavi.nextToken() );
				condizioniChiavi += " OR lower(" + CODICI_NUTS.DESCRIZIONE + ") LIKE '%" + currToken + "%'";
				//pesatura += " + sign(charindex('" + currToken+ "',lower("+ CODICI_NUTS.DESCRIZIONE + "))) ";
				//virtuale
				pesatura += " + sign(locate('" + currToken+ "',lower("+ CODICI_NUTS.DESCRIZIONE + "))) ";
			}
			condizioniChiavi = condizioniChiavi + ")";
		}
		ResultSet rs = null;
 		try {
 			//logger.debug(pesatura + RICERCA_BASE + condizioniChiavi + " order by 1 desc");
 			stmt = activeConnection.prepareStatement(pesatura + RICERCA_BASE + condizioniChiavi + " order by 1 desc");
 			stmt.setString(1, PageHelper.getDBDateFromTS(getNow()));
 			
 			rs = stmt.executeQuery();		
			
			return copyToArray(rs);
 		} catch ( Exception ex ) {ex.printStackTrace(); return new LinkedList<NutsBean>();}
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
	 * metodo per il controllo della validata' di un codice nuts
	 * 
	 * @param codiceNuts String
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isValid(String codiceNuts,Object data) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean retVal = false;
		
		String query = "SELECT " + CODICI_NUTS.ID_NUTS + " FROM " + CODICI_NUTS.TABLE_NAME +" WHERE "
		+ CODICI_NUTS.ID_NUTS + "=? ";
		
		if(!SimogFlags.isFromMassLoader()){
			query = query 
			+ " AND " + localBuildISNULL(CODICI_NUTS.DATA_FINE_VALIDITA,"99999999") + " >= ?"
			+ " ORDER BY " + localBuildISNULL(CODICI_NUTS.DATA_FINE_VALIDITA,"99999999"); 
		}
		
		try{
			//logger.debug("[query] - check nuts["+codiceNuts+"]" + query);
			stmt = activeConnection.prepareStatement(query);
			stmt.setString(1, codiceNuts);

			if(!SimogFlags.isFromMassLoader()){
				stmt.setObject(2,PageHelper.getFormattedNowOrInputFormattedDate(data, getNow()));				
			}
			rs = stmt.executeQuery();
			if(rs.next()){
			   if(rs.getString(CODICI_NUTS.ID_NUTS).equals(codiceNuts))
			      retVal = true;
			}
			return retVal;
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		} finally{
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

}
