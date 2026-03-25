package it.avcp.simog.managers.comportamento.caricamento;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.avanzamento.AvanzamentoBean;
import it.avlp.simog.db.generated.STATI_AVANZ;
import it.avlp.simog.db.generated.STATI_SCHEDA;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ILoadAvanzamento extends ICaricamento {

	public final String CONDIZIONE_STATO =
		" AND " + STATI_AVANZ.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
		+" AND (" + STATI_AVANZ.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + STATI_AVANZ.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";	
	
	public final String WHERE_STANDARD = 
		" WHERE " + STATI_AVANZ.ID_AVANZAMENTO + " = ? " +
		" AND " + STATI_AVANZ.DATA_INIZIO_AVANZAMENTO + " = ? "
		+ CONDIZIONE_STATO;
	
	public final String WHERE_IDLOCALE = 
		" WHERE " + 
		STATI_AVANZ.T_ID_AGGIUDICAZIONE+" = ? " +
		" AND " + STATI_AVANZ.T_ID_SCHEDA_LOCALE+ " = ? "
		+CONDIZIONE_STATO;
	
	public final String WHERE_IDSIMOG = 
		" WHERE " +
		STATI_AVANZ.T_ID_AVANZAMENTO+ " = ? "
		+ CONDIZIONE_STATO;
	
	/**
	 * @param idLocale
	 * @param cig
	 * @return
	 * @throws SQLException
	 */
	public AvanzamentoBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException;
	/**
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 */
	public AvanzamentoBean loadByIdSimog(long idSimog) throws SQLException;
	/**
	 * @param rs
	 * @param pubBean
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, AvanzamentoBean avanBean) throws SQLException;

}
