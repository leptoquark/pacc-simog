package it.avcp.simog.managers.cpv;

import it.avlp.simog.beans.CpvEu;
import it.avlp.simog.beans.CpvLotto;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.CPVEU;
import it.avlp.simog.db.generated.CPV_LOTTO;
import it.avlp.simog.util.PageHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * Classe per gestione dei cpv
 *
 */
public class CPVEUManager extends AccessiDB {
	
	public final static String CPV_CODICE = "CODICE_CPV";
		
	private final String GET_ALL_ACTIVE_CPV = 	
		"SELECT " + CPVEU.TABLE_NAME + ".* "
		+ " FROM "
		+ CPVEU.TABLE_NAME
		+ " WHERE " + localBuildISNULL(CPVEU.T_DATA_FINE_VALIDITA,"99999999") + " >= ? ";
	
	private final String GET_VOX_ACTIVE_CPV = 	
		" , " + CPVEU.TABLE_NAME + ".* "
		+ " FROM "
		+ CPVEU.TABLE_NAME
		+ " WHERE " + localBuildISNULL(CPVEU.T_DATA_FINE_VALIDITA,"99999999") + " >= ? ";

	private final String ORDERBY_CODICE =
		" ORDER BY " + CPVEU.T_ID_DIV + "," + CPVEU.T_ID_GRP + "," 
			+ CPVEU.T_ID_CLS + "," + CPVEU.T_ID_CTG + "," + CPVEU.T_ID_VOX ;
	
//	private final String WHERE_BASE = " AND " + CPVEU.T_ID_GRP + " = '0' "; 

   private final String WHERE_BASE = " AND ((" + CPVEU.T_ID_GRP + " ='0') OR "          
         + " ( " + CPVEU.T_ID_CLS + " <> '0' "                     
         + " AND not exists "
         + " (select b.id_ctg from cpveu b "
         + " where b.id_div=cpveu.id_div "
         + " and b.id_grp = '0' "
         + " and b.versione=cpveu.versione)"
         +"))"; 
   
//	private final String WHERE_DIV = " AND " + CPVEU.T_ID_DIV + " =?" 
//							+ " AND " + CPVEU.T_ID_GRP + " <> '0' "
//							+ " AND " + CPVEU.T_ID_CLS + " = '0' ";
	
   private final String WHERE_DIV = " AND " + CPVEU.T_ID_DIV + " =?" 
         + " AND ((" + CPVEU.T_ID_GRP + " <> '0' "                   
         + " AND " + CPVEU.T_ID_CLS + " ='0') OR "          
         + " ( " + CPVEU.T_ID_GRP + " <> '0' "                     
         + " AND " + CPVEU.T_ID_CLS + " <>'0' "
         + " AND " + CPVEU.T_ID_CTG + " ='0' "
         + " AND not exists "
         + " (select b.id_ctg from cpveu b "
         + " where b.id_div=cpveu.id_div "
         + " and b.id_grp = cpveu.id_grp "
         + " and b.id_cls = '0' "
         + " and b.versione=cpveu.versione)"
         +"))"; 
   
	private final String WHERE_GRP = " AND " + CPVEU.T_ID_DIV + " =?" 
							+ " AND " + CPVEU.T_ID_GRP + " = ? "
							+ " AND ((" + CPVEU.T_ID_CLS + " <> '0' "							
							+ " AND " + CPVEU.T_ID_CTG + " ='0') OR "				
							+ " ( " + CPVEU.T_ID_CLS + " <> '0' "                     
                     + " AND " + CPVEU.T_ID_CTG + " <>'0' "
                      + " AND " + CPVEU.T_ID_VOX + " ='000' "
                     + " AND not exists "
                     + " (select b.id_ctg from cpveu b "
                     + " where b.id_div=cpveu.id_div "
                     + " and b.id_grp = cpveu.id_grp "
                     + " and b.id_cls = cpveu.id_cls "
                     + " and b.id_ctg = '0' "
                     + " and b.versione=cpveu.versione)"
                     +"))"; 
		
//	private final String WHERE_CTG = " AND " + CPVEU.T_ID_DIV + " =?" 
//							+ " AND " + CPVEU.T_ID_GRP + " = ? "
//							+ " AND " + CPVEU.T_ID_CLS + " = ? "
//							+ " AND " + CPVEU.T_ID_CTG + " <>'0' "
//							+ " AND " + CPVEU.T_ID_VOX + " ='000' "; 

   private final String WHERE_CTG = " AND " + CPVEU.T_ID_DIV + " =?" 
         + " AND " + CPVEU.T_ID_GRP + " = ? "
         + " AND " + CPVEU.T_ID_CLS + " = ? "
         + " AND ((" + CPVEU.T_ID_CTG + " <> '0' "                   
         + " AND " + CPVEU.T_ID_VOX + " ='000') OR "          
         + " ( " + CPVEU.T_ID_CTG + " <> '0' "                     
         + " AND " + CPVEU.T_ID_VOX + " <>'000' "
         + " AND not exists "
         + " (select b.id_ctg from cpveu b "
         + " where b.id_div=cpveu.id_div "
         + " and b.id_grp = cpveu.id_grp "
         + " and b.id_cls = cpveu.id_cls "
         + " and b.id_ctg = cpveu.id_ctg "
         + " and b.id_vox = '000' "
         + " and b.versione=cpveu.versione)"
         +"))"; 
	
	
//	private final String WHERE_VOX = " AND " + CPVEU.T_ID_DIV + " =?" 
//							+ " AND " + CPVEU.T_ID_GRP + " = ? "
//							+ " AND " + CPVEU.T_ID_CLS + " = ? "
//							+ " AND " + CPVEU.T_ID_CTG + " = ? "
//							+ " AND " + CPVEU.T_ID_VOX + " <>'000' "; 

   private final String WHERE_VOX = " AND " + CPVEU.T_ID_DIV + " =?" 
         + " AND " + CPVEU.T_ID_GRP + " = ? "
         + " AND " + CPVEU.T_ID_CLS + " = ? "
         + " AND " + CPVEU.T_ID_CTG + " = ? "
         + " AND (" + CPVEU.T_ID_VOX + " <> '000' OR "
         + " (" + CPVEU.T_ID_VOX + " <> '000' AND "
         + " not exists "
         + " (select b.id_ctg from cpveu b "
         + " where b.id_div=cpveu.id_div "
         + " and b.id_grp = cpveu.id_grp "
         + " and b.id_cls = cpveu.id_cls "
         + " and b.id_ctg = cpveu.id_ctg "
         + " and b.id_vox = '000' "
         + " and b.versione=cpveu.versione)))";
	
	 //modificare per integrare nuovo cpv
	private final String getCPVByIDCPV = 	
		"SELECT "
		+ CPVEU.TABLE_NAME + ".*"
		+ " FROM "
		+ CPVEU.TABLE_NAME
		+ " WHERE "
		+ CPVEU.ID_DIV + " = ? "
		+ " and " + CPVEU.ID_GRP + " = ? "
		+ " and " + CPVEU.ID_CLS + " = ? "
		+ " and " + CPVEU.ID_CTG + " = ? "
		+ " and " + CPVEU.ID_VOX + " = ? "
		+ " and " + CPVEU.CHK + " = ? "
		+ " and " + localBuildISNULL(CPVEU.DATA_FINE_VALIDITA,"99999999") + " >= ? ";	

	private final String getCPVByIDCPVNoData = 	
			"SELECT "
			+ CPVEU.TABLE_NAME + ".*"
			+ " FROM "
			+ CPVEU.TABLE_NAME
			+ " WHERE "
			+ CPVEU.ID_DIV + " = ? "
			+ " and " + CPVEU.ID_GRP + " = ? "
			+ " and " + CPVEU.ID_CLS + " = ? "
			+ " and " + CPVEU.ID_CTG + " = ? "
			+ " and " + CPVEU.ID_VOX + " = ? "
			+ " and " + CPVEU.CHK + " = ? ";
	
	/**
	 * Costruttore
	 * 
	 * @param currentActiveConnection Connection
	 * @param logger Logger
	 */
	public CPVEUManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}	

	/*
	 * carica l'arraylist in base al resultset
	 */
	private List<CpvEu> loadCpvArray (ResultSet rs, boolean fromSearch) throws SQLException {
		ArrayList<CpvEu> alCpv = new ArrayList<CpvEu>();
		List<CpvEu> childList = new ArrayList<CpvEu>();

	//	try {
			while (rs.next()){
				if(fromSearch){
				// verifica se esistono figli
				childList = getBranch(rs.getString(CPVEU.ID_DIV), 
						rs.getString(CPVEU.ID_GRP),
						rs.getString(CPVEU.ID_CLS), 
						rs.getString(CPVEU.ID_CTG), 
						rs.getString(CPVEU.ID_VOX));
				}
				
				if (!fromSearch ||(!"0".equals(rs.getString(CPVEU.ID_GRP)) && !"0".equals(rs.getString(CPVEU.ID_CLS)))
					 || childList.size() == 0){
				
					alCpv.add( new CpvEu(rs.getString(CPVEU.ID_DIV),
										 rs.getString(CPVEU.ID_GRP),
										 rs.getString(CPVEU.ID_CLS),
										 rs.getString(CPVEU.ID_CTG),
										 rs.getString(CPVEU.ID_VOX),
										 rs.getString(CPVEU.CHK),
										 rs.getString(CPVEU.DESCRIZIONE),
										 rs.getString(CPVEU.DATA_FINE_VALIDITA),
										 rs.getString(CPVEU.DATA_ULTIMA_MODIFICA),
										 rs.getString(CPVEU.VERSIONE)
					));
				}
 			}
	//	} catch ( Exception e ) {e.printStackTrace();return null;}
		alCpv.trimToSize();
		return alCpv;
	}

	/*
	 * ritorna un arraylist di CpvEu beans con tutte le voci attive
	 * input: flag solo gruppi
	 */	
	/**
	 * metodo che:
	 * ritorna un arraylist di CpvEu beans con tutte le voci attive
	 * input: flag solo gruppi
	 * 
	 * @param onlyGroups boolean
	 * @return List&lt;CpvEu&gt;
	 * @throws SQLException
	 */
	public List<CpvEu> getAllActiveCPV(boolean onlyGroups) throws SQLException {

//		logger.debug("Ricerca Informazioni sulle CPV attive, query[" + GET_ALL_ACTIVE_CPV + ORDERBY_CODICE + "]");
		
 		PreparedStatement stmt = null;
 		ResultSet rs = null;
 		
 		try {
 			stmt = activeConnection.prepareStatement(GET_ALL_ACTIVE_CPV 
					+ (onlyGroups ? " and " + CPVEU.ID_VOX + " is null " : "")
					+ ORDERBY_CODICE);
 			stmt.setString(1, PageHelper.getCurrentDate());
			rs = stmt.executeQuery();
	
			return loadCpvArray(rs, false);
 		} finally {
 			try {
 				close(rs, stmt);
 			} catch ( Exception e ) {e.printStackTrace();}
 			stmt = null;
 		}
	}
	 
	/**
	 * metodo che:
	 * ritorna un arraylist di CpvEu beans con tutte le voci appartenenti al ramo indicato
	 * input: codici di ramo
	 * per accedere al primo livello occorre passare null al primo parametro e zeri (secondo
	 * la lunghezza dei campi) negli altri parametri
	 * esempio: getBranch(null, "0", "0", "0", "000")
	 * 
	 * @param idDiv String
	 * @param idGrp String
	 * @param idCls String
	 * @param idCtg String
	 * @param idVox String
	 * @return List&lt;CpvEu&gt;
	 * @throws SQLException
	 */
	public  List<CpvEu> getBranch(String idDiv, String idGrp, String idCls, String idCtg, String idVox) throws SQLException {
		
		String lQuery = GET_ALL_ACTIVE_CPV;
				
 		PreparedStatement stmt = null;
 		ResultSet rs = null;

 		try {
			// se null richiesto solo il primo livello
 			if (idDiv == null)
				lQuery = lQuery + WHERE_BASE;		
			else if (idGrp.equals("0"))
				lQuery = lQuery + WHERE_DIV;		
			else if (idCls.equals("0"))
				lQuery = lQuery + WHERE_GRP;		
			else if (idCtg.equals("0"))
				lQuery = lQuery + WHERE_CTG;		
			else //if (idVox.equals("000"))
				lQuery = lQuery + WHERE_VOX;		
 	
// 			lQuery += " and " + CPVEU.VERSIONE + " = " +  buildVersCPV( CPVEU.ID_DIV + " + " + CPVEU.ID_GRP + " + " + CPVEU.ID_CLS + " + " + CPVEU.ID_CTG + " + " + CPVEU.ID_VOX + " +'-'+ " + CPVEU.CHK
// 									,  "'" + PageHelper.getCurrentDate() + "'");
 			
			lQuery = lQuery + ORDERBY_CODICE;

 			stmt = activeConnection.prepareStatement(lQuery);
 			 			
			int i = 1;
			stmt.setString(i++, PageHelper.getCurrentDate());
			if (idDiv != null) 
				stmt.setString(i++, idDiv);
			
 			if (!idGrp.equals("0"))
 					stmt.setString(i++, idGrp);

 			if (!idCls.equals("0"))
					stmt.setString(i++, idCls);

 			if (!idCtg.equals("0"))
					stmt.setString(i++, idCtg);
 				
	 		//logger.debug("Ricerca branch CPV, query[" + lQuery + "]");
	 		
			rs = stmt.executeQuery();

         //logger.debug(String.format("Ricerca branch CPV - DIV[%s] GRP[%s] CLS[%s] CTG[%s], query[%s]", String.valueOf(idDiv), String.valueOf(idGrp), String.valueOf(idCls), String.valueOf(idCtg), lQuery));

			return loadCpvArray(rs, false);
			
 		} catch ( Exception ex ) {
 			ex.printStackTrace(); return new ArrayList<CpvEu>();}
 		finally {
 			close(rs, stmt);
 		}
	}

	/**
	 * metodo che:
	 * ritorna un arraylist di CpvEu beans con tutte che includono almeno una chiave di ricerca
	 * ordinato per codice
	 * input: chiavi di ricerca
	 * 
	 * @param chiavi String
	 * @return List&lt;CpvEu&gt;
	 * @throws SQLException
	 */
	public List<CpvEu> getVoci(String chiavi) throws SQLException {

 		PreparedStatement stmt = null;
 		ResultSet rs = null;
		StringTokenizer tokenChiavi = null;
		
		String condizioniChiavi = "";
		String pesatura = "";
		String currToken = "";
		
//		logger.debug("Ricerca voci CPV");

		if (chiavi!= null && chiavi.trim().length() > 0 ) {
			tokenChiavi = new StringTokenizer( chiavi.toLowerCase() );
			currToken = getCleanToken( tokenChiavi.nextToken() );
			
			condizioniChiavi = " AND (lower(" + CPVEU.DESCRIZIONE + ") LIKE '%" + currToken + "%'";
			//pesatura = "SELECT sign(charindex('"+ currToken+ "',lower("+ CPVEU.DESCRIZIONE + "))) ";
			//virtuale
			pesatura = "SELECT sign(locate('"+ currToken+ "',lower("+ CPVEU.DESCRIZIONE + "))) ";
			
			while ( tokenChiavi.hasMoreElements() ) {
				currToken = getCleanToken( tokenChiavi.nextToken() );
				condizioniChiavi += " OR lower(" + CPVEU.DESCRIZIONE + ") LIKE '%" + currToken + "%'";
				//pesatura += " + sign(charindex('"+ currToken+ "',lower("+ CPVEU.DESCRIZIONE + "))) ";
				//virtuale
				pesatura += " + sign(locate('"+ currToken+ "',lower("+ CPVEU.DESCRIZIONE + "))) ";
			}
			condizioniChiavi = condizioniChiavi + ")";
		}
//logger.debug("***RICERCA CPV ***:" + pesatura + GET_VOX_ACTIVE_CPV + condizioniChiavi + " order by 1 desc");
 		try {
 			stmt = activeConnection.prepareStatement(pesatura + GET_VOX_ACTIVE_CPV + condizioniChiavi + " order by 1 desc");
 			stmt.setString(1, PageHelper.getCurrentDate());
 			rs = stmt.executeQuery();		
			
			return loadCpvArray(rs, true);
 		} catch ( Exception ex ) {ex.printStackTrace(); return new ArrayList<CpvEu>();}
 		finally {
 				close(rs, stmt);
 		}
	}
	
	/**
	 * Creato per gestire gli apici nel campo di ricerca
	 * per gestire token contenenti apostrofo
	 */
	
	private String getCleanToken ( String currentToken ) {
		/*if ( currentToken.contains("\'") ) {
			int apostrophePosition = currentToken.indexOf("'");
			String firstPart = currentToken.substring(0, apostrophePosition);
			String second = currentToken.substring(apostrophePosition + 1);
			currentToken = firstPart + "''" + second;
		}
		*/
		currentToken = currentToken.replace("'", "''");
		return currentToken;
	}
	
	/*******************************************************************************************
	 * Verifica l'esistenza di un CPV
	 * 
	 * @param cpvDaVerificare String
	 * @param dataValidita' String
	 * @return boolean true se esiste false se non esiste
	 * @throws SQLException
	 */
	public boolean checkCPV ( String cpvDaVerificare, String dataVal) throws SQLException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int index = 1;
		try {
			if(cpvDaVerificare.length() != 10 || cpvDaVerificare.indexOf("-")!= 8)
				return false;
			String div= cpvDaVerificare.substring(0, 2);
			String grp = cpvDaVerificare.substring(2, 3);
			String cls = cpvDaVerificare.substring(3, 4);
			String ctg = cpvDaVerificare.substring(4, 5);
			String vox = cpvDaVerificare.substring(5, 8);
			String chk = cpvDaVerificare.substring(9,10);
			pstmt = activeConnection.prepareStatement(getCPVByIDCPV);
			
			pstmt.setObject( index++, div );
			pstmt.setObject( index++, grp );
			pstmt.setObject( index++, cls );
			pstmt.setObject( index++, ctg );
			pstmt.setObject( index++, vox );
			pstmt.setObject( index++, chk );
			pstmt.setObject( index++, dataVal );
			rs = pstmt.executeQuery();

			boolean CPVxists = rs.next();
			return CPVxists;
		} finally {
			close(rs,pstmt);
//			try {
//				pstmt.close();
//			} catch ( Exception e ) {}
//			pstmt = null;
		}
	}	
	
	public boolean checkCPVNoData ( String cpvDaVerificare) throws SQLException {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int index = 1;
		try {
			if(cpvDaVerificare.trim().length() != 10 || cpvDaVerificare.trim().indexOf("-")!= 8)
				return false;
			String div= cpvDaVerificare.substring(0, 2);
			String grp = cpvDaVerificare.substring(2, 3);
			String cls = cpvDaVerificare.substring(3, 4);
			String ctg = cpvDaVerificare.substring(4, 5);
			String vox = cpvDaVerificare.substring(5, 8);
			String chk = cpvDaVerificare.substring(9,10);
			pstmt = activeConnection.prepareStatement(getCPVByIDCPVNoData);
			
			pstmt.setObject( index++, div );
			pstmt.setObject( index++, grp );
			pstmt.setObject( index++, cls );
			pstmt.setObject( index++, ctg );
			pstmt.setObject( index++, vox );
			pstmt.setObject( index++, chk );
//			pstmt.setObject( index++, dataVal );
			rs = pstmt.executeQuery();

			boolean CPVxists = rs.next();
			return CPVxists;
		} finally {
			close(rs,pstmt);
//			try {
//				pstmt.close();
//			} catch ( Exception e ) {}
//			pstmt = null;
		}
	}	
	
	/*******************************************************************************************
	 * Recupera la descrizione di un CPV
	 * 
	 * @param cpvDaVerificare String
	 * @return String descrizione della cpv
	 * @throws SQLException
	 */
	public String getCPVDesc ( String cpvDaVerificare) throws SQLException {
		
		 String query = 	
				"SELECT "
				+ CPVEU.DESCRIZIONE
				+ " FROM "
				+ CPVEU.TABLE_NAME
				+ " WHERE "
				+ CPVEU.ID_DIV + " = ? "
				+ " and " + CPVEU.ID_GRP + " = ? "
				+ " and " + CPVEU.ID_CLS + " = ? "
				+ " and " + CPVEU.ID_CTG + " = ? "
				+ " and " + CPVEU.ID_VOX + " = ? "
				+ " and " + CPVEU.CHK + " = ? "
				+ " and " + localBuildISNULL(CPVEU.DATA_FINE_VALIDITA,"99999999") + " >= ? ";	
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int index = 1;
		String res = "KO";
		try {
			if(cpvDaVerificare.length() != 10 || cpvDaVerificare.indexOf("-")!= 8)
				return res;
			String div= cpvDaVerificare.substring(0, 2);
			String grp = cpvDaVerificare.substring(2, 3);
			String cls = cpvDaVerificare.substring(3, 4);
			String ctg = cpvDaVerificare.substring(4, 5);
			String vox = cpvDaVerificare.substring(5, 8);
			String chk = cpvDaVerificare.substring(9,10);
			pstmt = activeConnection.prepareStatement(query);
			pstmt.setObject( index++, div );
			pstmt.setObject( index++, grp );
			pstmt.setObject( index++, cls );
			pstmt.setObject( index++, ctg );
			pstmt.setObject( index++, vox );
			pstmt.setObject( index++, chk );
			pstmt.setObject( index++, PageHelper.getCurrentDate() );
			rs = pstmt.executeQuery();

			if(rs.next())
			   res= rs.getString(1);
		} finally {
			close(rs,pstmt);
		}
		return res;
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
