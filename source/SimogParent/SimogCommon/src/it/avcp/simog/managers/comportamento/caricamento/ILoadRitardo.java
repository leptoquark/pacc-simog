package it.avcp.simog.managers.comportamento.caricamento;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.r129.R129Bean;
import it.avlp.simog.db.generated.R129;
import it.avlp.simog.db.generated.STATI_SCHEDA;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ILoadRitardo extends ICaricamento {

	public final String CONDIZIONE_STATO =
		"AND " + R129.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
		+" AND (" + R129.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + R129.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String WHERE_STANDARD = 
		" WHERE " +
		R129.T_ID_RECORD + " = ? " +
		" AND " + R129.T_DATA_INIZIO+ " = ?  "
		+CONDIZIONE_STATO;
	
	public final String WHERE_IDLOCALE =
		" WHERE " +
		R129.T_ID_SCHEDA_LOCALE + " = ? " +
		" AND " + R129.T_ID_AGGIUDICAZIONE + " = ?  "
		+CONDIZIONE_STATO;
	
	public final String WHERE_IDSIMOG = 
		" WHERE " 
		+ R129.T_ID_RECORD + " = ? "
		+CONDIZIONE_STATO;
	/**
	 * @param idLocale
	 * @param idAggiudicazione
	 * @return
	 * @throws SQLException
	 */
	public R129Bean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException;
	/**
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 */
	public R129Bean loadByIdSimog(long idSimog) throws SQLException;
	/**
	 * @param rs
	 * @param bean
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, R129Bean bean) throws SQLException;
}
