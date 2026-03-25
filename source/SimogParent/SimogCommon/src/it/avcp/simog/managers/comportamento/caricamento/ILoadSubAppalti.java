package it.avcp.simog.managers.comportamento.caricamento;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.subappalti.SubappaltiBean;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.db.generated.SUBAPPALTI;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ILoadSubAppalti extends ICaricamento {

	public final String CONDIZIONE_STATO =
		"AND " + SUBAPPALTI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
		+" AND (" + SUBAPPALTI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + SUBAPPALTI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String WHERE_STANDARD = 
		" WHERE " +
		SUBAPPALTI.T_ID_RECORD + " = ? " +
		" AND " + SUBAPPALTI.T_DATA_INIZIO_RECORD+ " = ?  "
		+CONDIZIONE_STATO;
	
	public final String WHERE_IDLOCALE =
		" WHERE " +
		SUBAPPALTI.T_ID_SCHEDA_LOCALE + " = ? " +
		" AND " + SUBAPPALTI.T_ID_AGGIUDICAZIONE + " = ?  "
		+CONDIZIONE_STATO;
	
	public final String WHERE_IDSIMOG = 
		" WHERE " 
		+ SUBAPPALTI.T_ID_RECORD + " = ? "
		+CONDIZIONE_STATO;
	/**
	 * @param idLocale
	 * @param idAggiudicazione
	 * @return
	 * @throws SQLException
	 */
	public SubappaltiBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException;
	/**
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 */
	public SubappaltiBean loadByIdSimog(long idSimog) throws SQLException;
	/**
	 * @param rs
	 * @param bean
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, SubappaltiBean bean) throws SQLException;

}
