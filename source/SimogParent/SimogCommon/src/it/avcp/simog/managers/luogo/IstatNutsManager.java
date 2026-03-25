package it.avcp.simog.managers.luogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import it.avlp.simog.db.AccessiDB;

public class IstatNutsManager extends AccessiDB {

	public IstatNutsManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}	
	
	
	private final String GET_NUTS_FROM_ISTAT = "SELECT ID_NUTS FROM ISTAT_NUTS "
											+ "WHERE ID_COMUNE=? AND (DATA_INIZIO_VALIDITA >= GETDATE() OR DATA_FINE_VALIDITA IS NULL)";
	
	
	public String getNutsFromIstat(String codiceIstat) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String res=null;
		
		try{
			
			stmt = activeConnection.prepareStatement(GET_NUTS_FROM_ISTAT);
			stmt.setString(1, codiceIstat);
			
			rs = stmt.executeQuery();
			if(rs.next()){
				res = rs.getString(1);
			}
			
			
		}catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}finally{
			close(rs, stmt);
		}
		
		return res;
	}
}
