package it.avcp.simog.managers.comportamento.caricamento;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.sospensioni.SospensioniBean;
import it.avlp.simog.db.generated.SOSPENSIONI;
import it.avlp.simog.db.generated.STATI_SCHEDA;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ILoadSospensioni extends ICaricamento {

	public final String CONDIZIONE_STATO =
		"AND " + SOSPENSIONI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
		+" AND (" + SOSPENSIONI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + SOSPENSIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String WHERE_STANDARD = 
	    " AND " + SOSPENSIONI.T_ID_SOSPENSIONE + " = ? " +
		" AND " + SOSPENSIONI.T_DATA_INIZIO_SOSP+ " = ?  "
		+CONDIZIONE_STATO;
	
	public final String WHERE_IDLOCALE =
		" AND " + SOSPENSIONI.T_ID_SCHEDA_LOCALE + " = ? " +
		" AND " + SOSPENSIONI.T_ID_AGGIUDICAZIONE + " = ?  "
		+CONDIZIONE_STATO;
	
	public final String WHERE_IDSIMOG = 
		" AND " + SOSPENSIONI.T_ID_SOSPENSIONE + " = ? "+
		CONDIZIONE_STATO;
	/**
	 * @param idLocale
	 * @param idAggiudicazione
	 * @return
	 * @throws SQLException
	 */
	public SospensioniBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException;
	/**
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 */
	public SospensioniBean loadByIdSimog(long idSimog) throws SQLException;
	/**
	 * @param rs
	 * @param bean
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, SospensioniBean bean) throws SQLException;
}
