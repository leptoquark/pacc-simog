package it.avcp.simog.managers.comportamento.caricamento;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.conclusione.ConclusioneBean;
import it.avlp.simog.db.generated.FINE_LAVORI;
import it.avlp.simog.db.generated.STATI_SCHEDA;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ILoadConclusione extends ICaricamento {

	public final String CONDIZIONE_STATO = 
		" AND " + FINE_LAVORI.T_ID_STATO + "=" + STATI_SCHEDA.T_ID_STATO +
		" AND (" + FINE_LAVORI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE +
		" OR " + FINE_LAVORI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String WHERE_STANDARD =
		" WHERE " +
		FINE_LAVORI.ID_AGGIUDICAZIONE + "= ? " +
		" AND " + FINE_LAVORI.DATA_INIZIO_AGGIUDICAZIONE + "= ? "
		+CONDIZIONE_STATO;
	
	public final String WHERE_IDLOCALE = 
		" WHERE " +
		FINE_LAVORI.ID_AGGIUDICAZIONE + "= ? " +
		" AND " + FINE_LAVORI.ID_SCHEDA_LOCALE + "= ? " +
		CONDIZIONE_STATO;
	
	public final String WHERE_IDSIMOG = 
		" WHERE " +
		FINE_LAVORI.ID_ULTIM + "= ? " +
		CONDIZIONE_STATO;
	
	/**
	 * @param idLocale
	 * @param idAggiudicazione
	 * @return
	 * @throws SQLException
	 */
	public ConclusioneBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException;
	/**
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 */
	public ConclusioneBean loadByIdSimog(long idSimog) throws SQLException;
	/**
	 * @param rs
	 * @param bean
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, ConclusioneBean bean) throws SQLException;
}
