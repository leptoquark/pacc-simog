package it.avcp.simog.managers.comportamento.caricamento;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.accordi.AccordoBean;
import it.avlp.simog.db.generated.ACCORDI;
import it.avlp.simog.db.generated.STATI_SCHEDA;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ILoadAccordo extends ICaricamento {

	public final String CONDIZIONE_STATO =
		"AND " + ACCORDI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
		+" AND (" + ACCORDI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + ACCORDI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String WHERE_STANDARD = 
		" WHERE " +
		ACCORDI.T_ID_ACCORDO + " = ? " +
		" AND " + ACCORDI.T_DATA_INIZIO_ACC + " = ?  "
		+CONDIZIONE_STATO;
	
	public final String WHERE_IDLOCALE =
		" WHERE " +
		ACCORDI.T_ID_SCHEDA_LOCALE + " = ? " +
		" AND " + ACCORDI.T_ID_AGGIUDICAZIONE + " = ?  "
		+CONDIZIONE_STATO;
	
	public final String WHERE_IDSIMOG = 
		" WHERE " 
		+ ACCORDI.T_ID_ACCORDO + " = ? "
		+CONDIZIONE_STATO;
	
	/**
	 * @param idLocale
	 * @param idAggiudicazione
	 * @return
	 * @throws SQLException
	 */
	public AccordoBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException;
	/**
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 */
	public AccordoBean loadByIdSimog(long idSimog) throws SQLException;
	/**
	 * @param rs
	 * @param bean
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, AccordoBean bean) throws SQLException;
}
