package it.avcp.simog.managers.comportamento.caricamento;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.stipula.StipulaBean;
import it.avlp.simog.db.generated.INIZIO_LAVORI;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.db.generated.STIPULA;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ILoadStipula extends ICaricamento {

	public final String CONDIZIONE_STATO =
		"AND " + STIPULA.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
		+" AND (" + STIPULA.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + STIPULA.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String WHERE_STANDARD = 
		" WHERE " +
		STIPULA.T_ID_AGGIUDICAZIONE + " = ? " +
		" AND " + STIPULA.T_DATA_INIZIO_AGGIUDICAZIONE + " = ?  "
		+CONDIZIONE_STATO;
	
	public final String WHERE_IDLOCALE =
		" WHERE " +
		STIPULA.T_ID_SCHEDA_LOCALE + " = ? " +
		" AND " + STIPULA.T_ID_AGGIUDICAZIONE + " = ?  "
		+CONDIZIONE_STATO;
	
	public final String WHERE_IDSIMOG = 
		" WHERE " 
		+ STIPULA.T_ID_STIPULA + " = ? "
		+CONDIZIONE_STATO;
	/**
	 * @param idLocale
	 * @param idAggiudicazione
	 * @return
	 * @throws SQLException
	 */
	public StipulaBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException;
	/**
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 */
	public StipulaBean loadByIdSimog(long idSimog) throws SQLException;
	/**
	 * @param rs
	 * @param bean
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, StipulaBean bean) throws SQLException;
}
