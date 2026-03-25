package it.avcp.simog.managers.comportamento.caricamento;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.collaudo.CollaudoBean;
import it.avlp.simog.db.generated.COLLAUDO;
import it.avlp.simog.db.generated.STATI_SCHEDA;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ILoadCollaudo extends ICaricamento {

	public final String CONDIZIONE_STATO =
		" AND " + COLLAUDO.T_ID_STATO + "=" + STATI_SCHEDA.T_ID_STATO +
		" AND (" + COLLAUDO.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE +
		" OR " + COLLAUDO.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String WHERE_STANDARD =
		" WHERE " +
		COLLAUDO.ID_AGGIUDICAZIONE + "= ?"+
		" AND " + COLLAUDO.DATA_INIZIO_AGGIUDICAZIONE + "= ? "
		+CONDIZIONE_STATO;
	
	public final String WHERE_ID =
		" WHERE " +
		COLLAUDO.ID_COLLAUDO + "= ?"+
		" AND " + COLLAUDO.DATA_INIZIO_COLL + "= ? "
		+ " AND " + COLLAUDO.T_ID_STATO + "=" + STATI_SCHEDA.T_ID_STATO ;

	public final String WHERE_IDLOCALE = 
		" WHERE " +
		COLLAUDO.ID_AGGIUDICAZIONE + "= ?"+ 
		" AND " + COLLAUDO.ID_SCHEDA_LOCALE + "= ? "+
		CONDIZIONE_STATO;
	
	public final String WHERE_IDSIMOG = 
		" WHERE " +
		COLLAUDO.ID_COLLAUDO + "= ?"+ 
		CONDIZIONE_STATO;
	/**
	 * @param idLocale
	 * @param idAggiudicazione
	 * @return
	 * @throws SQLException
	 */
	public CollaudoBean loadByIdLocale(String idLocale, String idAggiudicazione) throws SQLException;
	/**
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 */
	public CollaudoBean loadByIdSimog(long idSimog) throws SQLException;
	/**
	 * @param rs
	 * @param bean
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, CollaudoBean bean, boolean ignoraStato) throws SQLException;
}
