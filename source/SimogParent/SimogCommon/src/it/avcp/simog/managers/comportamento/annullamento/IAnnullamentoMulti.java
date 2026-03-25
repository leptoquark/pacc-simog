package it.avcp.simog.managers.comportamento.annullamento;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.db.generated.AGGIUDICATARIO;
import it.avlp.simog.db.generated.CONDIZIONI_AGG;
import it.avlp.simog.db.generated.DITTE_AUSILIARIE;
import it.avlp.simog.db.generated.EVENTI_MOTIVI_VARIANTI;
import it.avlp.simog.db.generated.FINANZIAMENTI_AGG;
import it.avlp.simog.db.generated.POSIZ_AGGIUD;
import it.avlp.simog.db.generated.REQUISITI;
import it.avlp.simog.db.generated.RESPONSABILE;
import it.avlp.simog.db.generated.RESP_COLL;
import it.avlp.simog.db.generated.RESP_INIZIO;
import it.avlp.simog.db.generated.TIPO_APPALTO_AGG;

import java.sql.SQLException;
import java.sql.Timestamp;

public interface IAnnullamentoMulti {
	
	public final String QUERY_ANNULLA_DITTE_AUSILIARIE = ""
		+"UPDATE "+DITTE_AUSILIARIE.TABLE_NAME+" SET "
		+DITTE_AUSILIARIE.T_ID_STATO_SCHEDA+ " = ? "
		+", "+DITTE_AUSILIARIE.DATA_FINE_RECORD+ " = ? "
		+" WHERE "+DITTE_AUSILIARIE.ID_AGGIUDICAZIONE+" = ?"
		+" AND "+DITTE_AUSILIARIE.DATA_INIZIO_AGGIUDICAZIONE+" = ?"
		+" AND (" + DITTE_AUSILIARIE.T_ID_STATO_SCHEDA + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + DITTE_AUSILIARIE.T_ID_STATO_SCHEDA + "=" + StatiScheda.CONFERMATO+")";

	public final String QUERY_ANNULLA_AGGIUDICATARI = ""
		+"UPDATE "+AGGIUDICATARIO.TABLE_NAME+" SET "
		+AGGIUDICATARIO.T_ID_STATO+ " = ? "
		+", "+AGGIUDICATARIO.DATA_FINE+ " = ? "
		+" WHERE "+AGGIUDICATARIO.ID_AGGIUDICAZIONE+" = ?"
		+" AND "+AGGIUDICATARIO.DATA_INIZIO_AGGIUDICAZIONE+" = ?"
		+" AND (" + AGGIUDICATARIO.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + AGGIUDICATARIO.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_CONDIZIONI = ""
		+"UPDATE "+CONDIZIONI_AGG.TABLE_NAME+" SET "
		+CONDIZIONI_AGG.T_ID_STATO+ " = ? "
		+", "+CONDIZIONI_AGG.DATA_FINE_COND+ " = ? "
		+" WHERE "+CONDIZIONI_AGG.ID_AGGIUDICAZIONE+" = ?"
		+" AND "+CONDIZIONI_AGG.DATA_INIZIO_AGGIUDICAZIONE+" = ?"
		+" AND (" + CONDIZIONI_AGG.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + CONDIZIONI_AGG.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_FINANZIAMENTI = ""
		+"UPDATE "+FINANZIAMENTI_AGG.TABLE_NAME+" SET "
		+FINANZIAMENTI_AGG.T_ID_STATO+ " = ? "
		+", "+FINANZIAMENTI_AGG.DATA_FINE_FINAGG+ " = ? "
		+" WHERE "+FINANZIAMENTI_AGG.ID_AGGIUDICAZIONE+" = ?"
		+" AND "+FINANZIAMENTI_AGG.DATA_INIZIO_AGGIUDICAZIONE+" = ?"
		+" AND (" + FINANZIAMENTI_AGG.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + FINANZIAMENTI_AGG.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_REQUISITI = ""
		+"UPDATE "+REQUISITI.TABLE_NAME+" SET "
		+REQUISITI.T_ID_STATO+ " = ? "
		+", "+REQUISITI.DATA_FINE_REQ+ " = ? "
		+" WHERE "+REQUISITI.ID_AGGIUDICAZIONE+" = ?"
		+" AND "+REQUISITI.DATA_INIZIO_AGGIUDICAZIONE+" = ?"
		+" AND (" + REQUISITI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + REQUISITI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_RESPONSABILI_AGG = ""
		+"UPDATE "+RESPONSABILE.TABLE_NAME+" SET "
		+RESPONSABILE.T_ID_STATO+ " = ? "
		+", "+RESPONSABILE.DATA_FINE+ " = ? "
		+" WHERE "+RESPONSABILE.ID_AGGIUDICAZIONE+" = ?"
		+" AND "+RESPONSABILE.DATA_INIZIO_AGGIUDICAZIONE+" = ?"
		+" AND (" + RESPONSABILE.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + RESPONSABILE.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_TIPOAPPALTO = ""
		+"UPDATE "+TIPO_APPALTO_AGG.TABLE_NAME+" SET "
		+TIPO_APPALTO_AGG.T_ID_STATO+ " = ? "
		+", "+TIPO_APPALTO_AGG.DATA_FINE_TIPOAPP+ " = ? "
		+" WHERE "+TIPO_APPALTO_AGG.ID_AGGIUDICAZIONE+" = ?"
		+" AND "+TIPO_APPALTO_AGG.DATA_INIZIO_AGGIUDICAZIONE+" = ?"
		+" AND (" + TIPO_APPALTO_AGG.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + TIPO_APPALTO_AGG.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
   public final String QUERY_ANNULLA_TIPOAPPALTO_LOTTO = ""
         +"UPDATE "+TIPO_APPALTO_AGG.TABLE_NAME+" SET "
         +TIPO_APPALTO_AGG.T_ID_STATO+ " = ? "
         +", "+TIPO_APPALTO_AGG.DATA_FINE_TIPOAPP+ " = ? "
         +" WHERE "+TIPO_APPALTO_AGG.ID_LOTTO+" = ?"
         +" AND (" + TIPO_APPALTO_AGG.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
         +" OR " + TIPO_APPALTO_AGG.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";

   public final String QUERY_ANNULLA_RESPONSABILI_COLL = ""
		+"UPDATE "+RESP_COLL.TABLE_NAME+" SET "
		+RESP_COLL.T_ID_STATO+ " = ? "
		+", "+RESP_COLL.DATA_FINE_RECORD+ " = ? "
		+" WHERE "+RESP_COLL.ID_COLLAUDO+" = ?"
		+" AND "+RESP_COLL.DATA_INIZIO_COLL+" = ?"
		+" AND (" + RESP_COLL.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + RESP_COLL.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_POSIZIONE_AGGIUDICATARI = ""
		+"UPDATE "+POSIZ_AGGIUD.TABLE_NAME+" SET "
		+POSIZ_AGGIUD.T_ID_STATO+ " = ? "
		+", "+POSIZ_AGGIUD.DATA_FINE_RECORD+ " = ? "
		+" WHERE "+POSIZ_AGGIUD.ID_INIZIO+" = ?"
		+" AND "+POSIZ_AGGIUD.DATA_INIZIO_INIZIO+" = ?"
		+" AND (" + POSIZ_AGGIUD.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + POSIZ_AGGIUD.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_RESPONSABILI_INIZIO = ""
		+"UPDATE "+RESP_INIZIO.TABLE_NAME+" SET "
		+RESP_INIZIO.T_ID_STATO+ " = ? "
		+", "+RESP_INIZIO.DATA_FINE_RECORD+ " = ? "
		+" WHERE "+RESP_INIZIO.ID_INIZIO+" = ?"
		+" AND "+RESP_INIZIO.DATA_INIZIO_INIZIO+" = ?"
		+" AND (" + RESP_INIZIO.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + RESP_INIZIO.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	public final String QUERY_ANNULLA_EVENTI_VARIANTI = ""
		+"UPDATE "+EVENTI_MOTIVI_VARIANTI.TABLE_NAME+" SET "
		+EVENTI_MOTIVI_VARIANTI.T_ID_STATO+ " = ? "
		+", "+EVENTI_MOTIVI_VARIANTI.DATA_FINE_RECORD+ " = ? "
		+" WHERE "+EVENTI_MOTIVI_VARIANTI.ID_VARIANTE+" = ?"
		+" AND "+EVENTI_MOTIVI_VARIANTI.DATA_INIZIO_VAR+" = ?"
		+" AND (" + EVENTI_MOTIVI_VARIANTI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + EVENTI_MOTIVI_VARIANTI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	/**
	 * Annulla, ovverosia pone allo stato annullato tutti i record che fanno riferimento
	 * ad una scheda padre, di cui riferimenti.
	 * 
	 * @param idSchedaPadre
	 * @param dataInizioSchedaPadre
	 * @return boolean : true se l'operazione ha effettuato qualche modifica (update) false altrimenti 
	 * @throws SQLException
	 */
	public boolean annulla(long idSchedaPadre, Timestamp dataInizioSchedaPadre) throws SQLException;
	
	/**
	 * Nasce dall'esigenza di non donver caricare i bean per poter effetuare la cancellazione
	 * 
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @return
	 * @throws SQLException
	 */
	//public boolean annullaByAggiudicazione(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException;
}
