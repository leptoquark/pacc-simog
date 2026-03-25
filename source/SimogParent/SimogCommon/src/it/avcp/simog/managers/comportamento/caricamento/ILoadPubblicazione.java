package it.avcp.simog.managers.comportamento.caricamento;

import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.PUBBLICAZIONI;
import it.avlp.simog.db.generated.STATI_SCHEDA;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ILoadPubblicazione extends ICaricamento {
	
	public final String QUERY_SELECT_PUBBLICAZIONE_JOIN_INFOAGGIUDICAZIONI =
		" SELECT "
		+PUBBLICAZIONI.T_ID_PUBBLICAZIONE
		+", "+PUBBLICAZIONI.T_DATA_INIZIO_PUBB
		+", "+PUBBLICAZIONI.T_ID_STATO
		+", "+PUBBLICAZIONI.DATA_ALBO
		+", "+PUBBLICAZIONI.DATA_GUCE
		+", "+PUBBLICAZIONI.DATA_GURI
		+", "+PUBBLICAZIONI.QUOTIDIANI_NAZ
		+", "+PUBBLICAZIONI.QUOTIDIANI_REG
		+", "+PUBBLICAZIONI.PROFILO_COMMITTENTE
		+", "+PUBBLICAZIONI.SITO_MINISTERO_INF_TRASP
		+", "+PUBBLICAZIONI.SITO_OSSERVATORIO_CP
		+", "+PUBBLICAZIONI.DATA_BORE
		+", "+PUBBLICAZIONI.PERIODICI
		+", "+PUBBLICAZIONI.T_ID_SCHEDA_LOCALE
		//PP BANDI GARA
		+", "+PUBBLICAZIONI.NUMERO_GURI
		+", "+PUBBLICAZIONI.NUMERO_GUCE
		+", "+PUBBLICAZIONI.NUMERO_BORE
		+", "+PUBBLICAZIONI.LINK_SITO_COMMITTENTE
		+", "+PUBBLICAZIONI.TIPO_OPERAZIONE	
		//gm nuovo codice estensione pubblicazione bandi
		+", "+PUBBLICAZIONI.FLAG_BENICULT		
		+", "+PUBBLICAZIONI.FLAG_SOSPESO	
		+", "+PUBBLICAZIONI.LINK_AFFIDAMENTO_DIRETTO //MEV 34470 3.04.8
		+" FROM "
		+PUBBLICAZIONI.TABLE_NAME + ", "
		+INFO_AGGIUDICAZIONI.TABLE_NAME + ", "
		+STATI_SCHEDA.TABLE_NAME + ""
		+" WHERE "
		+PUBBLICAZIONI.T_ID_PUBBLICAZIONE + " = " + INFO_AGGIUDICAZIONI.T_ID_PUBBLICAZIONE
		+" AND " + PUBBLICAZIONI.T_DATA_INIZIO_PUBB + " = " + INFO_AGGIUDICAZIONI.T_DATA_INIZIO_PUBB
		+" AND " + PUBBLICAZIONI.T_ID_STATO + " = " + INFO_AGGIUDICAZIONI.T_ID_STATO
		
		+" AND " + PUBBLICAZIONI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
		+" AND (" + PUBBLICAZIONI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + PUBBLICAZIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String WHERE_IDLOCALE = 
		" AND "+INFO_AGGIUDICAZIONI.CIG+"= ?"
		+" AND "+PUBBLICAZIONI.T_ID_SCHEDA_LOCALE+"= ? ";
	
	public final String WHERE_IDSIMOG = " AND " + INFO_AGGIUDICAZIONI.T_ID_INFO+ " = ? ";
	
	public final String WHERE_STANDARD = 
		" WHERE "+PUBBLICAZIONI.T_ID_PUBBLICAZIONE+"= ?"+" AND "+PUBBLICAZIONI.T_DATA_INIZIO_PUBB+"= ?"
		+" AND " + PUBBLICAZIONI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
		+" AND (" + PUBBLICAZIONI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + PUBBLICAZIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";

	/**
	 * @param idLocale
	 * @param cig
	 * @return
	 * @throws SQLException
	 */
	public PubblicazioneBean loadByIdLocale(String idLocale, String cig) throws SQLException;
	/**
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 */
	public PubblicazioneBean loadByIdSimog(long idSimog) throws SQLException;
	/**
	 * @param rs
	 * @param pubBean
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, PubblicazioneBean pubBean) throws SQLException;
}
