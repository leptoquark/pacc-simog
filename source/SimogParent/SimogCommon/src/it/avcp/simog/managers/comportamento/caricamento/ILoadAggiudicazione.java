package it.avcp.simog.managers.comportamento.caricamento;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.db.generated.STATI_SCHEDA;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ILoadAggiudicazione extends ICaricamento {

	

	
	public final String CONDIZIONE_STATO =
		" AND " + AGGIUDICAZIONI.T_ID_STATO + "=" + STATI_SCHEDA.T_ID_STATO +
		" AND (" + AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE+
		" OR " + AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String WHERE_IDLOCALE = 
		" WHERE " + 
		AGGIUDICAZIONI.PROG_CUI + " = ? " +
		" AND " + AGGIUDICAZIONI.CUI + " = ? " +
		" AND " + AGGIUDICAZIONI.T_ID_SCHEDA_LOCALE+ " = ? " +
		CONDIZIONE_STATO ;
	
	public final String WHERE_IDSIMOG = 
		" WHERE " +
		AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE+ " = ? " +
		CONDIZIONE_STATO;
	
	/**
	 * Load tramite identificativo esterno e cui, per garantire l'univocita dell'idLocale
	 * 
	 * @param idLocale
	 * @param cui
	 * @return
	 */
	public AggiudicazioneBean loadByIdLocale(String idLocale, String cui) throws SQLException;
	/**
	 * Load by id della scheda 
	 * 
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 */
	public AggiudicazioneBean loadByIdSimog(long idSimog) throws SQLException;
	/**
	 * Valorizzazione centralizza del bean di aggiudicazione 
	 * @param rs
	 * @param bean
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, AggiudicazioneBean bean) throws SQLException;
}
