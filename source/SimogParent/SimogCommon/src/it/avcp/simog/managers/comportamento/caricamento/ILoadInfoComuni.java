package it.avcp.simog.managers.comportamento.caricamento;

import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ILoadInfoComuni extends ICaricamento {

	public final String WHERE_STANDARD = " AND " + INFO_AGGIUDICAZIONI.T_ID_INFO+ " = ? AND "+INFO_AGGIUDICAZIONI.T_DATA_INIZIO_INFO+" = ?";
	public final String WHERE_IDLOCALE = " AND " + INFO_AGGIUDICAZIONI.T_ID_SCHEDA_LOCALE+ " = ? AND " + INFO_AGGIUDICAZIONI.T_CIG + "= ?";
	public final String WHERE_IDSIMOG = " AND " + INFO_AGGIUDICAZIONI.T_ID_INFO+ " = ?";

	/**
	 * metodo per il caricamento degli info comuni tramite id del sistema remoto
	 * - il cig e' necessario, poiche l'id locale e' sicuramente univoco solamente
	 * 		al livello di cig (ovvero all'interno dello stesso sistema del cliente)
	 * 
	 * @param idLocale
	 * @param cig
	 * @return
	 * @throws SQLException
	 */
	public InfoComuniBean loadByIdLocale(String idLocale, String cig) throws SQLException;
	/**
	 * metodo per il caricamento degli info comuni tramite IdSimog (ID_INFO)
	 * 
	 * @param idLocale String
	 * @return InfoComuniBean
	 * @throws SQLException
	 */
	public InfoComuniBean loadByIdSimog(long idSimog) throws SQLException;
	/**
	 * metodo per la valorizzazione di un bean tramite il "suo" resultset
	 * @param rs
	 * @param infoComuni
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, InfoComuniBean infoComuni) throws SQLException;
}
