package it.avcp.simog.managers.comportamento.caricamento;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.variante.VarianteBean;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.db.generated.VARIANTI;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ILoadVariante extends ICaricamento {

	public final String CONDIZIONE_STATO =
		"AND " + VARIANTI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
		+" AND (" + VARIANTI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + VARIANTI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String WHERE_STANDARD = 
		" WHERE " +
		VARIANTI.T_ID_VARIANTE + " = ? " +
		" AND " + VARIANTI.T_DATA_INIZIO_VAR+ " = ?  "
		+CONDIZIONE_STATO;
	
	public final String WHERE_IDLOCALE =
		" WHERE " +
		VARIANTI.T_ID_SCHEDA_LOCALE + " = ? " +
		" AND " + VARIANTI.T_ID_AGGIUDICAZIONE + " = ?  "
		+CONDIZIONE_STATO;
	
	public final String WHERE_IDSIMOG = 
		" WHERE " 
		+ VARIANTI.T_ID_VARIANTE + " = ? "
		+CONDIZIONE_STATO;
	/**
	 * @param idLocale
	 * @param idAggiudicazione
	 * @return
	 * @throws SQLException
	 */
	public VarianteBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException;
	/**
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 */
	public VarianteBean loadByIdSimog(long idSimog) throws SQLException;
	/**
	 * @param rs
	 * @param bean
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, VarianteBean bean) throws SQLException;

}
