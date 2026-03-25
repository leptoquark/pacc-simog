package it.avcp.simog.manager.delega;

import it.avlp.simog.beans.DelegaDatiSimog;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.generated.DELEGA_DATI_SIMOG;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DelegaDatiSimogManager extends AccessiDB {

	public DelegaDatiSimogManager(Connection currentActiveConnection,
			Logger logger) {
		super(currentActiveConnection, logger);
	}

	private final String QUERY_DELEGA_DATI = "select "
			+ DELEGA_DATI_SIMOG.ID_OSSERVATORIO + ", "
			+ DELEGA_DATI_SIMOG.DESCRIZIONE + ", "
			+ DELEGA_DATI_SIMOG.DELEGA_CIG + ", "
			+ DELEGA_DATI_SIMOG.DELEGA_CIG_DAL + ", "
			+ DELEGA_DATI_SIMOG.DELEGA_SCHEDE + ", "
			+ DELEGA_DATI_SIMOG.DELEGA_SCHEDE_DAL + ", "
			+ DELEGA_DATI_SIMOG.URL_SISTEMA + ", "
			+ DELEGA_DATI_SIMOG.DATA_FINE_VALIDITA + ", "
			+ DELEGA_DATI_SIMOG.DATA_ULTIMA_MODIFICA + " from "
			+ DELEGA_DATI_SIMOG.TABLE_NAME + " where "
			+ DELEGA_DATI_SIMOG.ID_OSSERVATORIO + " = ?";

	protected void fillBean(ResultSet rs, DelegaDatiSimog bean)
			throws SQLException {
		int i = 1;
		if (rs.next()) {
			bean.setIdOsservatorio(rs.getString(i++));
			bean.setDescrizione(rs.getString(i++));
			bean.setDelegaCig(rs.getString(i++));
			bean.setDelegaCigDal(rs.getTimestamp(i++));
			bean.setDelegaSchede(rs.getString(i++));
			bean.setDelegaSchedeDal(rs.getTimestamp(i++));
			bean.setUrlSistema(rs.getString(i++));
			bean.setDataFineValidita(rs.getTimestamp(i++));
			bean.setDataUltimaModifica(rs.getTimestamp(i++));
		}

	}

	/** Controlla lo stato di abilitazione di un osservatorio alla compilazione delle schede e richiesta CIG
	 * @param idOsservatorio osservatorio da controllare
	 * @return {@link DelegaDatiSimog} lo stato di abilitazione per richiesta CIG e compilazione schede
	 * @throws SQLException
	 */
	public DelegaDatiSimog getDelegaDatiSimog(String idOsservatorio)
			throws SQLException {

		DelegaDatiSimog bean = new DelegaDatiSimog();
		
		if (idOsservatorio == null)
			return bean;
		
		idOsservatorio = "000".concat(idOsservatorio);
		idOsservatorio = idOsservatorio.substring(idOsservatorio.length() - 3);

		PreparedStatement stmt = null;
		ResultSet rs = null;
		logger.debug("Executing query [ " + QUERY_DELEGA_DATI
				+ " ] with parameters: " + idOsservatorio);
		try {
			stmt = activeConnection.prepareStatement(QUERY_DELEGA_DATI);
			stmt.setString(1, idOsservatorio);
			rs = stmt.executeQuery();
			fillBean(rs, bean);

		} finally {
			close(rs, stmt);
		}

		return bean;
	}

}
