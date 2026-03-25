package it.avcp.simog.managers.comportamento.annullamento;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.db.generated.ACCORDI;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.db.generated.COLLAUDO;
import it.avlp.simog.db.generated.FINE_LAVORI;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.INIZIO_LAVORI;
import it.avlp.simog.db.generated.PUBBLICAZIONI;
import it.avlp.simog.db.generated.R129;
import it.avlp.simog.db.generated.SOSPENSIONI;
import it.avlp.simog.db.generated.STATI_AVANZ;
import it.avlp.simog.db.generated.STIPULA;
import it.avlp.simog.db.generated.SUBAPPALTI;
import it.avlp.simog.db.generated.VARIANTI;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Questa intefaccia nasce per determinare un comportamento 
 * che deve essere implementato da ogni *Manager
 * In particolare definisce come comportamento l'operazione 
 * di "annullamento" di una scheda o sottoscheda tramite
 * tramite idlocale e/o idSimog
 * 
 * Permette anche di raggruppare tutte le query di annullamento
 * 
 * @author vletizia
 *
 */
public interface IAnnullamento {

	public final String QUERY_ANNULLA_INFO_COMUNI =
		"UPDATE "+INFO_AGGIUDICAZIONI.TABLE_NAME+" SET "
		+INFO_AGGIUDICAZIONI.T_ID_STATO+ " = ? "
		+", "+INFO_AGGIUDICAZIONI.DATA_FINE_INFO+ " = ? "
		+" WHERE "+INFO_AGGIUDICAZIONI.ID_INFO+" = ?"
		+" AND "+INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO+" = ?"
		+" AND (" + INFO_AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + INFO_AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_AGGIUDICAZIONI =
		"UPDATE "+AGGIUDICAZIONI.TABLE_NAME+" SET "
		+AGGIUDICAZIONI.T_ID_STATO+ " = ? "
		+", "+AGGIUDICAZIONI.DATA_FINE_AGGIUDICAZIONE+ " = ? "
		+" WHERE "+AGGIUDICAZIONI.ID_AGGIUDICAZIONE+" = ?"
		+" AND "+AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE+" = ?"
		+" AND (" + AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";

	public final String QUERY_ANNULLA_PUBBLICAZIONE =
		"UPDATE "+PUBBLICAZIONI.TABLE_NAME+" SET "
		+PUBBLICAZIONI.T_ID_STATO+ " = ? "
		+", "+PUBBLICAZIONI.DATA_FINE_PUBB+ " = ? "
		+" WHERE "+PUBBLICAZIONI.ID_PUBBLICAZIONE+" = ?"
		+" AND "+PUBBLICAZIONI.DATA_INIZIO_PUBB+" = ?"
		+" AND (" + PUBBLICAZIONI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + PUBBLICAZIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_ACCORDO =
		"UPDATE "+ACCORDI.TABLE_NAME+" SET "
		+ACCORDI.T_ID_STATO+ " = ? "
		+", "+ACCORDI.DATA_FINE_ACC+ " = ? "
		+" WHERE "+ACCORDI.ID_ACCORDO+" = ?"
		+" AND "+ACCORDI.DATA_INIZIO_ACC+" = ?"
		+" AND (" + ACCORDI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + ACCORDI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_AVANZAMENTO =
		"UPDATE "+STATI_AVANZ.TABLE_NAME+" SET "
		+STATI_AVANZ.T_ID_STATO+ " = ? "
		+", "+STATI_AVANZ.DATA_FINE_AVANZAMENTO+ " = ? "
		+" WHERE "+STATI_AVANZ.ID_AVANZAMENTO+" = ?"
		+" AND "+STATI_AVANZ.DATA_INIZIO_AVANZAMENTO+" = ?"
		+" AND (" + STATI_AVANZ.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + STATI_AVANZ.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_COLLAUDO =
		"UPDATE "+COLLAUDO.TABLE_NAME+" SET "
		+COLLAUDO.T_ID_STATO+ " = ? "
		+", "+COLLAUDO.DATA_FINE_COLL+ " = ? "
		+" WHERE "+COLLAUDO.ID_COLLAUDO+" = ?"
		+" AND "+COLLAUDO.DATA_INIZIO_COLL+" = ?"
		+" AND (" + COLLAUDO.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + COLLAUDO.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_CONCLUSIONE =
		"UPDATE "+FINE_LAVORI.TABLE_NAME+" SET "
		+FINE_LAVORI.T_ID_STATO+ " = ? "
		+", "+FINE_LAVORI.DATA_FINE_ULTIM+ " = ? "
		+" WHERE "+FINE_LAVORI.ID_ULTIM+" = ?"
		+" AND "+FINE_LAVORI.DATA_INIZIO_ULTIM+" = ?"
		+" AND (" + FINE_LAVORI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + FINE_LAVORI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_INIZIO_LAVORI =
		"UPDATE "+INIZIO_LAVORI.TABLE_NAME+" SET "
		+INIZIO_LAVORI.T_ID_STATO+ " = ? "
		+", "+INIZIO_LAVORI.DATA_FINE_INIZIO+ " = ? "
		+" WHERE "+INIZIO_LAVORI.ID_INIZIO+" = ?"
		+" AND "+INIZIO_LAVORI.DATA_INIZIO_INIZIO+" = ?"
		+" AND (" + INIZIO_LAVORI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + INIZIO_LAVORI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_R129 =
		"UPDATE "+R129.TABLE_NAME+" SET "
		+R129.T_ID_STATO+ " = ? "
		+", "+R129.DATA_FINE+ " = ? "
		+" WHERE "+R129.ID_RECORD+" = ?"
		+" AND "+R129.DATA_INIZIO+" = ?"
		+" AND (" + R129.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + R129.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_SOSPENSIONI =
		"UPDATE "+SOSPENSIONI.TABLE_NAME+" SET "
		+SOSPENSIONI.T_ID_STATO+ " = ? "
		+", "+SOSPENSIONI.DATA_FINE_SOSP+ " = ? "
		+" WHERE "+SOSPENSIONI.ID_SOSPENSIONE+" = ?"
		+" AND "+SOSPENSIONI.DATA_INIZIO_SOSP+" = ?"
		+" AND (" + SOSPENSIONI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + SOSPENSIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_SUBAPPALTI =
		"UPDATE "+SUBAPPALTI.TABLE_NAME+" SET "
		+SUBAPPALTI.T_ID_STATO+ " = ? "
		+", "+SUBAPPALTI.DATA_FINE_RECORD+ " = ? "
		+" WHERE "+SUBAPPALTI.ID_RECORD+" = ?"
		+" AND "+SUBAPPALTI.DATA_INIZIO_RECORD+" = ?"
		+" AND (" + SUBAPPALTI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + SUBAPPALTI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_VARIANTI =
		"UPDATE "+VARIANTI.TABLE_NAME+" SET "
		+VARIANTI.T_ID_STATO+ " = ? "
		+", "+VARIANTI.DATA_FINE_VAR+ " = ? "
		+" WHERE "+VARIANTI.ID_VARIANTE+" = ?"
		+" AND "+VARIANTI.DATA_INIZIO_VAR+" = ?"
		+" AND (" + VARIANTI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + VARIANTI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_STIPULA =
		"UPDATE "+STIPULA.TABLE_NAME+" SET "
		+STIPULA.T_ID_STATO+ " = ? "
		+", "+STIPULA.DATA_FINE_STIPULA+ " = ? "
		+" WHERE "+STIPULA.ID_STIPULA+" = ?"
		+" AND "+STIPULA.DATA_INIZIO_STIPULA+" = ?"
		+" AND (" + STIPULA.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + STIPULA.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	/**
	 * Mette la scheda nello stato di annullato cercando il record per le sue chiavi
	 * 
	 * @param idScheda
	 * @param dataInizioScheda
	 * @param cfUtente
	 * @return boolean : true se l'operazione ha effettuato qualche modifica (update) false altrimenti 
	 * @throws SQLException
	 */
	public boolean annulla(long idScheda, Timestamp dataInizioScheda, String cfUtente) throws SQLException;
	
	/**
	 * Annullamento per id della scheda, (condizione che la scheda sia in stato "def.." o "conf..")
	 * 
	 * @param idSimog
	 * @param cfUtente
	 * @return boolean : true se l'operazione ha effettuato qualche modifica (update) false altrimenti 
	 * @throws SQLException
	 */
	public boolean annulla(long idSimog, String cfUtente) throws SQLException;
	
	/**
	 * Annullamento tramite l'id locale e il rifermento simog che permette di trattare come univoco
	 * l'id locale
	 * 
	 * @param idLocale: id passato dal sistema esterno, puo essere considerato valido solo all'interno "del CIG"
	 * @param rifSimog -> a seconda del manager si puo' parlare di:
	 * - cig nel caso di infoComuni e pubblicazioni,
	 * - cui nel caso di aggiudicazioni, 
	 * - idAggiudicazione nel caso di tutte le schede (vere e proprie)
	 * - idPadre nel caso di "componenti" di schede
	 * @param cfUtente
	 * @return boolean : true se l'operazione ha effettuato qualche modifica (update) false altrimenti 
	 * @throws SQLException
	 */
	public boolean annulla(String idLocale, String rifSimog, String cfUtente) throws SQLException;
	
}
