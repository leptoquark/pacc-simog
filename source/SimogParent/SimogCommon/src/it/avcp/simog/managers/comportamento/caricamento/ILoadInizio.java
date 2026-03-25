package it.avcp.simog.managers.comportamento.caricamento;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.inizio.InizioLavoriBean;
import it.avlp.simog.db.generated.INIZIO_LAVORI;
import it.avlp.simog.db.generated.STATI_SCHEDA;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ILoadInizio extends ICaricamento {

	public final String CONDIZIONE_STATO =
		" AND " + INIZIO_LAVORI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
		+ " AND (" + INIZIO_LAVORI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + INIZIO_LAVORI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String WHERE_STANDARD = 
		" WHERE " +
		INIZIO_LAVORI.T_ID_AGGIUDICAZIONE + " = ? " +
		" AND " + INIZIO_LAVORI.T_DATA_INIZIO_AGGIUDICAZIONE + " = ?  " +
		CONDIZIONE_STATO;
	
	public final String WHERE_ID = 
		" WHERE " +
		INIZIO_LAVORI.T_ID_INIZIO + " = ? " +
		" AND " + INIZIO_LAVORI.T_DATA_INIZIO_INIZIO + " = ?  " +
		" AND " + INIZIO_LAVORI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO;

	public final String WHERE_IDLOCALE =
		" WHERE " +
		INIZIO_LAVORI.T_ID_SCHEDA_LOCALE + " = ? " +
		" AND " + INIZIO_LAVORI.T_ID_AGGIUDICAZIONE + " = ?  "
		+CONDIZIONE_STATO;
	
	public final String WHERE_IDSIMOG = 
		" WHERE " 
		+ INIZIO_LAVORI.T_ID_INIZIO + " = ? "
		+CONDIZIONE_STATO;
	/**
	 * @param idLocale
	 * @param idAggiudicazione
	 * @return
	 * @throws SQLException
	 */
	public InizioLavoriBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException;
	/**
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 */
	public InizioLavoriBean loadByIdSimog(long idSimog) throws SQLException;
	/**
	 * @param rs
	 * @param bean
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, InizioLavoriBean bean) throws SQLException;
}
