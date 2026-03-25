package it.avcp.simog.managers.aggiudicazione;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.avcp.simog.managers.comportamento.annullamento.IAnnullamento;
import it.avcp.simog.managers.comportamento.caricamento.ILoadAggiudicazione;
import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.beans.DatiEconomiciBean;
import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.AggiudicazioneBean;
import it.avlp.simog.beans.aggiudicazione.TipoAggiudicazione;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.db.generated.APPALTI_PER_CATEGORIA;
import it.avlp.simog.db.generated.CONDIZIONI;
import it.avlp.simog.db.generated.FINE_LAVORI;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.MODALITA_GARA;
import it.avlp.simog.db.generated.MODI_RIAGGIUD;
import it.avlp.simog.db.generated.MODO_INDIZIONE;
import it.avlp.simog.db.generated.MOTIVI_VARIAZIONE_CO;
import it.avlp.simog.db.generated.PRESTAZIONI_PER_CATEGORIA;
import it.avlp.simog.db.generated.RICHIESTA_ANNULLAMENTO;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.db.generated.TIPI_APPALTI;
import it.avlp.simog.db.generated.TIPI_PRESTAZIONI;
import it.avlp.simog.db.generated.TIPO_FINANZIAMENTO;
import it.avlp.simog.db.generated.TIPO_STRUMENTO;
import it.avlp.simog.managers.log.LogBloccoDatiManager;
import it.avlp.simog.util.CIGUtils;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.validatore.SimogValidator;

/**
 * Classe che si occupa della lettura/scrittura per l'entit&agrave; aggiudicazione
 *
 */
public class AggiudicazioniManager extends AccessiDB implements IAnnullamento,ILoadAggiudicazione {
	
	public static String CLAZZ = "AggiudicazioniManager";
	CIGUtils cigUtils=new CIGUtils(); 

	
//	public final String QUERY_SELECT_LISTA_AGGIUDICAZIONI = 
//		" SELECT " +
//		AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE +","+
//		AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE +","+
//		AGGIUDICAZIONI.T_ID_STATO +", "+
//		STATI_SCHEDA.T_DESCRIZIONE+ " + " +  buildRichAnnQuery(PSBD.TAB_AGGIUDICAZIONE, AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE,null) 
//		+" AS "+STATI_SCHEDA.DESCRIZIONE+", "+
//		AGGIUDICAZIONI.T_CUI+", "+
//		AGGIUDICAZIONI.T_PROG_CUI 
//		
//		+ ", " +INIZIO_LAVORI.ID_INIZIO+", "+ 
//		INIZIO_LAVORI.DATA_INIZIO_INIZIO+", "+
//		"id_25,data_inizio_25,id_50,data_inizio_50,id_75,data_inizio_75,id_100,data_inizio_100,"+
//		FINE_LAVORI.ID_ULTIM+", "+
//		FINE_LAVORI.DATA_INIZIO_ULTIM+" "+
//		" FROM " + STATI_SCHEDA.TABLE_NAME +", "+
//		AGGIUDICAZIONI.TABLE_NAME +
//		" inner join stati_avanzamento_view"+
//		" on (stati_avanzamento_view.ID_AGGIUDICAZIONE = "+AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE+ 
//		" AND stati_avanzamento_view.DATA_INIZIO_AGGIUDICAZIONE = "+AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE+ " ) "+
//		" WHERE " +
//		AGGIUDICAZIONI.ID_INFO + "= ?"+
//		" AND " + AGGIUDICAZIONI.DATA_INIZIO_INFO + "= ?"+
//		" AND " + AGGIUDICAZIONI.T_ID_STATO + "=" + STATI_SCHEDA.T_ID_STATO
//// PP
////		" AND "+STATI_SCHEDA.T_DESCRIZIONE+" = ( SELECT "+ STATI_SCHEDA.T_DESCRIZIONE +" FROM "+STATI_SCHEDA.TABLE_NAME
////		+" WHERE "+STATI_SCHEDA.T_ID_STATO+" = "+AGGIUDICAZIONI.T_ID_STATO+" )"
//		+" AND (" + AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
//		+" OR " + AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")"
//		+" ORDER BY " +  AGGIUDICAZIONI.ID_INFO + ","
//		+ AGGIUDICAZIONI.CUI + ", "
//		+ AGGIUDICAZIONI.PROG_CUI
//		; // PP ordinamenti + " DESC";
	
	public final String QUERY_SELECT_LISTA_AGGIUDICAZIONI_NEW = 
		" SELECT " +
		AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE +","+
		AGGIUDICAZIONI.ID_INFO + ", " + 
		AGGIUDICAZIONI.DATA_INIZIO_INFO + ", " + 
		AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE +","+
		AGGIUDICAZIONI.T_SOTTOTIPO +","+
		
		AGGIUDICAZIONI.T_ID_STATO +", "+
		STATI_SCHEDA.T_DESCRIZIONE+ " + " +  buildRichAnnQueryMult(new String[]{IdentificativoSchede.TAB_AGGIUDICAZIONE, IdentificativoSchede.TAB_SOTTOSOGLIA, IdentificativoSchede.TAB_ESCLUSI, IdentificativoSchede.TAB_ADESIONE}, AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE,null) 
		+" AS "+STATI_SCHEDA.DESCRIZIONE+", "+
		AGGIUDICAZIONI.T_CUI+", "+
		AGGIUDICAZIONI.T_PROG_CUI+", "+
		AGGIUDICAZIONI.T_PROG_CUI_RIAGGIUDICATO +", "+
		//gm aggiunto per appalti multilotto
		AGGIUDICAZIONI.T_FLAG_AGGIUD_PRINCIPALE +", "+
		AGGIUDICAZIONI.T_CODICE_CONTRATTO +", "+
		//gm aggiunto per avvisi aggiudicazione
		AGGIUDICAZIONI.T_ID_PUBBLICAZIONE_AGG +", "+
		AGGIUDICAZIONI.T_DATA_INIZIO_PUBB_AGG +", "+
		
		AGGIUDICAZIONI.ID_SCHEDA_LOCALE
		+ " FROM " + AGGIUDICAZIONI.TABLE_NAME 
		+ ", " + STATI_SCHEDA.TABLE_NAME
		+ " WHERE " + AGGIUDICAZIONI.ID_INFO + " = ? "
		+ " AND " + AGGIUDICAZIONI.DATA_INIZIO_INFO + " = ? "
		+ " AND " + AGGIUDICAZIONI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
		+ " AND (" + AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE
		+ " OR " + AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.CONFERMATO + ")"
		// Ticket#2015050810000322
		+ " ORDER BY " + AGGIUDICAZIONI.PROG_CUI
		;
	
	private final String QUERY_SELECT_AGGIUDICAZIONI =  
		"SELECT " +
		AGGIUDICAZIONI.ID_AGGIUDICAZIONE + ", " +
		AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE + ", " +
		AGGIUDICAZIONI.ID_INFO + ", " + 
		AGGIUDICAZIONI.DATA_INIZIO_INFO + ", " + 
		AGGIUDICAZIONI.NUM_IMPRESE_INVITATE + ", " + 
		AGGIUDICAZIONI.NUM_IMPRESE_RICHIEDENTI + ", " + 
		AGGIUDICAZIONI.NUM_IMPRESE_OFFERENTI + ", " + 
		AGGIUDICAZIONI.NUM_OFFERTE_AMMESSE + ", " + 
		AGGIUDICAZIONI.DATA_VERBALE_AGGIUDICAZIONE + ", " +
		AGGIUDICAZIONI.DATA_STIPULA + ", " +
		AGGIUDICAZIONI.SOTTOTIPO + ", " +
		AGGIUDICAZIONI.PROG_CUI_RIAGGIUDICATO +","+
		AGGIUDICAZIONI.MODALITA_RIAGGIUDICAZIONE +","+
		AGGIUDICAZIONI.TERMINE_CONTRATTUALE + ", " +
		AGGIUDICAZIONI.DURATA_CONTRATTUALE + ", " +
		AGGIUDICAZIONI.DATA_SCADENZA_RICHIESTA_INVITO + ", " + 
		AGGIUDICAZIONI.DATA_SCADENZA_PRES_OFFERTA + ", " + 
		AGGIUDICAZIONI.ID_MODALITA_GARA + ", " + 
		//gm aggiunto per avvisi aggiudicazione
		AGGIUDICAZIONI.T_ID_PUBBLICAZIONE_AGG +", "+
		AGGIUDICAZIONI.T_DATA_INIZIO_PUBB_AGG +", "+
		
		AGGIUDICAZIONI.DATA_FINE_AGGIUDICAZIONE + ", " + 	
		AGGIUDICAZIONI.CUI + ", " + 
		AGGIUDICAZIONI.PROG_CUI + ", " + 
		AGGIUDICAZIONI.IMPORTO_AGGIUDICAZIONE + ", " + 
		AGGIUDICAZIONI.IMPORTO_COMPLESSIVO + ", " + 
		AGGIUDICAZIONI.ID_SCELTA_CONTRAENTE + ", " + 
		AGGIUDICAZIONI.IMPORTO_LAVORI + ", " +
		AGGIUDICAZIONI.IMPORTO_SERVIZI + ", " +
		AGGIUDICAZIONI.IMPORTO_FORNITURE + ", " +
		AGGIUDICAZIONI.IMPORTO_ATTUAZIONE_SICUREZZA + ", " + 
		AGGIUDICAZIONI.IMPORTO_DISPOSIZIONE + ", " +
		AGGIUDICAZIONI.IMPORTO_PROGETTAZIONE + ", " +
		AGGIUDICAZIONI.SISTEMA_QUALIFICAZIONE + ", " + 
		AGGIUDICAZIONI.CRITERI_SELEZIONE_STABILITI_SA + ", " + 
		AGGIUDICAZIONI.T_ID_STATO + ", " + 
		AGGIUDICAZIONI.ID_TIPO_PRESTAZIONE + ", " +
		AGGIUDICAZIONI.CUP + ", " + 
		AGGIUDICAZIONI.FLAG_ACCORDO_QUADRO+ ", " + 
		AGGIUDICAZIONI.LUOGO_ISTAT + ", " + 
		AGGIUDICAZIONI.LUOGO_NUTS + ", " + 
		AGGIUDICAZIONI.ASTA_ELETTRONICA + ", " +
		AGGIUDICAZIONI.PERC_RIBASSO_AGG + ", " +
		AGGIUDICAZIONI.PERC_OFF_AUMENTO + ", " +
		AGGIUDICAZIONI.DATA_INVITO + ", " + 
		AGGIUDICAZIONI.NUM_MANIF_INTERESSE + ", " +
		AGGIUDICAZIONI.DATA_MANIF_INTERESSE + ", " +
		AGGIUDICAZIONI.FLAG_RICH_SUBAPPALTO + ", " +
		AGGIUDICAZIONI.NUM_OFFERTE_ESCLUSE + ", " +
		AGGIUDICAZIONI.OFFERTA_MASSIMO + ", " +
		AGGIUDICAZIONI.OFFERTA_MINIMA + ", " +
		AGGIUDICAZIONI.VAL_SOGLIA_ANOMALIA + ", " +
		AGGIUDICAZIONI.NUM_OFFERTE_FUORI_SOGLIA + ", " +
		AGGIUDICAZIONI.NUM_IMP_ESCL_INSUF_GIUST + ", " +
		AGGIUDICAZIONI.PROCEDURA_ACC + ", " +
		AGGIUDICAZIONI.PREINFORMAZIONE + ", " +
		AGGIUDICAZIONI.TERMINE_RIDOTTO + ", " +
		AGGIUDICAZIONI.ID_MODO_GARA + ", " +
		// duplicato AGGIUDICAZIONI.ID_SCELTA_CONTRAENTE + ", " +
		AGGIUDICAZIONI.COD_STRUMENTO + ", " +
		AGGIUDICAZIONI.IMP_NON_ASSOG + ", " +
		//gm nuovo per appalti multilotto
		AGGIUDICAZIONI.CODICE_CONTRATTO + ", " +
		AGGIUDICAZIONI.FLAG_AGGIUD_PRINCIPALE + ", " +
		
		//AGGIUDICAZIONI.DURATA_CONVENZIONE+", "+
		
		//gm nuovo codice 3.0
		AGGIUDICAZIONI.OPERE_URBANIZZAZIONE+", " +
		//gm fine nuovo codice 3.0
		
		// PP B302.2.0
		(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive() ? AGGIUDICAZIONI.ID_MOTIVO_VAR_CO + ", "  : "") + 
		
		(SimogFlags.is3028_RFWEBSC00Active() ? AGGIUDICAZIONI.ORIGINE + ", " : "") + 
		
		STATI_SCHEDA.DESCRIZIONE + " + " +
		   buildRichAnnQueryMult(new String[]{IdentificativoSchede.TAB_AGGIUDICAZIONE, IdentificativoSchede.TAB_SOTTOSOGLIA, IdentificativoSchede.TAB_ESCLUSI, IdentificativoSchede.TAB_ADESIONE}, AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE,null) 
		+" AS "+STATI_SCHEDA.DESCRIZIONE
		+ ", " + AGGIUDICAZIONI.ID_SCHEDA_LOCALE  
		+ ", " + AGGIUDICAZIONI.RELAZIONE_UNICA + //TICKET ALM #14639 - 3.04.5
		" FROM " + AGGIUDICAZIONI.TABLE_NAME 
		+ " JOIN " + STATI_SCHEDA.TABLE_NAME
		+ " ON " + AGGIUDICAZIONI.T_ID_STATO + "=" + STATI_SCHEDA.T_ID_STATO
		+ " WHERE " + AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = ? " +
		"   AND " + AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE + " = ? ";

		private final String WHERE_STATO = " AND (" + AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE
		+" OR " + AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";

	private final String QUERY_SELECT_AGGIUDICAZIONI_MASSLOADER =  
		"SELECT " +
		AGGIUDICAZIONI.ID_AGGIUDICAZIONE + ", " +
		AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE + ", " +
		AGGIUDICAZIONI.ID_INFO + ", " + 
		AGGIUDICAZIONI.DATA_INIZIO_INFO + ", " + 
		AGGIUDICAZIONI.NUM_IMPRESE_INVITATE + ", " + 
		AGGIUDICAZIONI.NUM_IMPRESE_RICHIEDENTI + ", " + 
		AGGIUDICAZIONI.NUM_IMPRESE_OFFERENTI + ", " + 
		AGGIUDICAZIONI.NUM_OFFERTE_AMMESSE + ", " + 
		AGGIUDICAZIONI.DATA_VERBALE_AGGIUDICAZIONE + ", " +
		AGGIUDICAZIONI.DATA_STIPULA + ", " +
		AGGIUDICAZIONI.SOTTOTIPO + ", " +
		AGGIUDICAZIONI.PROG_CUI_RIAGGIUDICATO +","+
		AGGIUDICAZIONI.MODALITA_RIAGGIUDICAZIONE +","+
		AGGIUDICAZIONI.TERMINE_CONTRATTUALE + ", " +
		AGGIUDICAZIONI.DURATA_CONTRATTUALE + ", " +
		AGGIUDICAZIONI.DATA_SCADENZA_RICHIESTA_INVITO + ", " + 
		AGGIUDICAZIONI.DATA_SCADENZA_PRES_OFFERTA + ", " + 
		AGGIUDICAZIONI.ID_MODALITA_GARA + ", " + 
		//gm aggiunto per avvisi aggiudicazione
		AGGIUDICAZIONI.T_ID_PUBBLICAZIONE_AGG +", "+
		AGGIUDICAZIONI.T_DATA_INIZIO_PUBB_AGG +", "+
		
		AGGIUDICAZIONI.DATA_FINE_AGGIUDICAZIONE + ", " + 	
		AGGIUDICAZIONI.CUI + ", " + 
		AGGIUDICAZIONI.PROG_CUI + ", " + 
		AGGIUDICAZIONI.IMPORTO_AGGIUDICAZIONE + ", " + 
		AGGIUDICAZIONI.IMPORTO_COMPLESSIVO + ", " + 
		AGGIUDICAZIONI.ID_SCELTA_CONTRAENTE + ", " + 
		AGGIUDICAZIONI.IMPORTO_LAVORI + ", " +
		AGGIUDICAZIONI.IMPORTO_SERVIZI + ", " +
		AGGIUDICAZIONI.IMPORTO_FORNITURE + ", " +
		AGGIUDICAZIONI.IMPORTO_ATTUAZIONE_SICUREZZA + ", " + 
		AGGIUDICAZIONI.IMPORTO_DISPOSIZIONE + ", " +
		AGGIUDICAZIONI.IMPORTO_PROGETTAZIONE + ", " +
		AGGIUDICAZIONI.SISTEMA_QUALIFICAZIONE + ", " + 
		AGGIUDICAZIONI.CRITERI_SELEZIONE_STABILITI_SA + ", " + 
		AGGIUDICAZIONI.T_ID_STATO + ", " + 
		AGGIUDICAZIONI.ID_TIPO_PRESTAZIONE + ", " +
		AGGIUDICAZIONI.CUP + ", " + 
		AGGIUDICAZIONI.FLAG_ACCORDO_QUADRO+ ", " + 
		AGGIUDICAZIONI.LUOGO_ISTAT + ", " + 
		AGGIUDICAZIONI.LUOGO_NUTS + ", " + 
		AGGIUDICAZIONI.ASTA_ELETTRONICA + ", " +
		AGGIUDICAZIONI.PERC_RIBASSO_AGG + ", " +
		AGGIUDICAZIONI.PERC_OFF_AUMENTO + ", " +
		AGGIUDICAZIONI.DATA_INVITO + ", " + 
		AGGIUDICAZIONI.NUM_MANIF_INTERESSE + ", " +
		AGGIUDICAZIONI.DATA_MANIF_INTERESSE + ", " +
		AGGIUDICAZIONI.FLAG_RICH_SUBAPPALTO + ", " +
		AGGIUDICAZIONI.NUM_OFFERTE_ESCLUSE + ", " +
		AGGIUDICAZIONI.OFFERTA_MASSIMO + ", " +
		AGGIUDICAZIONI.OFFERTA_MINIMA + ", " +
		AGGIUDICAZIONI.VAL_SOGLIA_ANOMALIA + ", " +
		AGGIUDICAZIONI.NUM_OFFERTE_FUORI_SOGLIA + ", " +
		AGGIUDICAZIONI.NUM_IMP_ESCL_INSUF_GIUST + ", " +
		AGGIUDICAZIONI.PROCEDURA_ACC + ", " +
		AGGIUDICAZIONI.PREINFORMAZIONE + ", " +
		AGGIUDICAZIONI.TERMINE_RIDOTTO + ", " +
		AGGIUDICAZIONI.ID_MODO_GARA + ", " +
		// duplicato AGGIUDICAZIONI.ID_SCELTA_CONTRAENTE + ", " +
		AGGIUDICAZIONI.COD_STRUMENTO + ", " +
		AGGIUDICAZIONI.IMP_NON_ASSOG + ", " +
		//gm nuovo per appalti multilotto
		AGGIUDICAZIONI.CODICE_CONTRATTO + ", " +
		AGGIUDICAZIONI.FLAG_AGGIUD_PRINCIPALE + ", " +
		
		//AGGIUDICAZIONI.DURATA_CONVENZIONE+", "+
		
		//gm nuovo codice 3.0
		AGGIUDICAZIONI.OPERE_URBANIZZAZIONE+", "+
		//gm fine nuovo codice 3.0
		
		// PP B302.2.0
		(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive() ? AGGIUDICAZIONI.ID_MOTIVO_VAR_CO + ", "  : "") + 

      (SimogFlags.is3028_RFWEBSC00Active() ? AGGIUDICAZIONI.ORIGINE + ", " : "") + 

		STATI_SCHEDA.DESCRIZIONE + " + " +
		  buildRichAnnQueryMult(new String[]{IdentificativoSchede.TAB_AGGIUDICAZIONE, IdentificativoSchede.TAB_SOTTOSOGLIA, IdentificativoSchede.TAB_ESCLUSI, IdentificativoSchede.TAB_ADESIONE}, AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE,null)
		+" AS "+STATI_SCHEDA.DESCRIZIONE+
		", " +AGGIUDICAZIONI.ID_SCHEDA_LOCALE + 
	    ", " + AGGIUDICAZIONI.RELAZIONE_UNICA + //TICKET ALM #14639 - 3.04.5
		" FROM " + AGGIUDICAZIONI.TABLE_NAME + "," + STATI_SCHEDA.TABLE_NAME ;
//		+
//		" WHERE " + AGGIUDICAZIONI.PROG_CUI + " = ? " +
//		"   AND " + AGGIUDICAZIONI.CUI + " = ? " + 
//		"   AND " + AGGIUDICAZIONI.T_ID_STATO + "=" + STATI_SCHEDA.T_ID_STATO
//		+" AND " + AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+"";

	// XXX: ATTENZIONE QUESTA QUERY PRENDE SOLO LE AGGIUDICAZIONI CONFERMATE.
	private final String WHERE_CONFERMATA = 
		" WHERE " + 
		AGGIUDICAZIONI.PROG_CUI + " = ? " +
		"   AND substring(" + AGGIUDICAZIONI.CUI + ",4,10)  = substring(?, 2, 10) " + 
		" AND " + AGGIUDICAZIONI.T_ID_STATO + "=" + STATI_SCHEDA.T_ID_STATO +
		" AND " + AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+"";
	
	private final String WHERE_STANDARD = 
		" WHERE " + 
		AGGIUDICAZIONI.PROG_CUI + " = ? " +
		"   AND substring(" + AGGIUDICAZIONI.CUI + ",4,10)  = substring(?, 2, 10) " + 
		" AND " + AGGIUDICAZIONI.T_ID_STATO + "=" + STATI_SCHEDA.T_ID_STATO +
		" AND (" + AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE +
		" OR " + AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
	
	private final String QUERY_UPDATE_AGGIUDICAZIONI = 
			" UPDATE " + AGGIUDICAZIONI.TABLE_NAME + " SET " +
		
			AGGIUDICAZIONI.NUM_IMPRESE_INVITATE + " = ?, " +
			AGGIUDICAZIONI.NUM_IMPRESE_RICHIEDENTI + " = ?, " +
			AGGIUDICAZIONI.NUM_IMPRESE_OFFERENTI + " = ?, " +
			AGGIUDICAZIONI.NUM_OFFERTE_AMMESSE + " = ?, " +
			AGGIUDICAZIONI.DATA_VERBALE_AGGIUDICAZIONE + " = ?, " +
			AGGIUDICAZIONI.DATA_STIPULA + " = ?, " +
			AGGIUDICAZIONI.SOTTOTIPO + " = ?, " +
			AGGIUDICAZIONI.PROG_CUI_RIAGGIUDICATO + " = ?, " +
			AGGIUDICAZIONI.MODALITA_RIAGGIUDICAZIONE + " = ?, " +
			AGGIUDICAZIONI.TERMINE_CONTRATTUALE + " = ?, " +
			AGGIUDICAZIONI.DURATA_CONTRATTUALE + " = ?, " +
		
			AGGIUDICAZIONI.DATA_SCADENZA_RICHIESTA_INVITO + " = ?, " +
			AGGIUDICAZIONI.DATA_SCADENZA_PRES_OFFERTA + " = ?, " +
			AGGIUDICAZIONI.ID_MODALITA_GARA + " = ?, " +
		
			
			AGGIUDICAZIONI.IMPORTO_AGGIUDICAZIONE + " = ?, " +
			AGGIUDICAZIONI.IMPORTO_COMPLESSIVO + " = ?, " +
			AGGIUDICAZIONI.ID_SCELTA_CONTRAENTE + " = ?, " +
	
			AGGIUDICAZIONI.IMPORTO_LAVORI + " = ?, " +
			AGGIUDICAZIONI.IMPORTO_SERVIZI + " = ?, " +
			AGGIUDICAZIONI.IMPORTO_FORNITURE + " = ?, " +
			
			AGGIUDICAZIONI.IMPORTO_ATTUAZIONE_SICUREZZA + " = ?, " +
			AGGIUDICAZIONI.IMPORTO_PROGETTAZIONE + " = ?, " +
			AGGIUDICAZIONI.IMPORTO_DISPOSIZIONE + " = ?, " +
			
			AGGIUDICAZIONI.SISTEMA_QUALIFICAZIONE + " = ?, " +
	
			AGGIUDICAZIONI.CRITERI_SELEZIONE_STABILITI_SA + " = ?, " + 
			AGGIUDICAZIONI.DATA_FINE_AGGIUDICAZIONE + " = ?, " + 
			AGGIUDICAZIONI.ID_STATO + " = ?, " +
			AGGIUDICAZIONI.ID_TIPO_PRESTAZIONE + " = ?, " +
			AGGIUDICAZIONI.CUP + " = ?, " + 
			AGGIUDICAZIONI.FLAG_ACCORDO_QUADRO+ " = ?, " + 
			AGGIUDICAZIONI.LUOGO_ISTAT + " = ?, " + 
			AGGIUDICAZIONI.LUOGO_NUTS + " = ?, " + 
		
			AGGIUDICAZIONI.ASTA_ELETTRONICA + " = ?, " +
			
			AGGIUDICAZIONI.OFFERTA_MASSIMO + " = ?, " +
			AGGIUDICAZIONI.OFFERTA_MINIMA + " = ?, " +
			AGGIUDICAZIONI.VAL_SOGLIA_ANOMALIA + " = ?, " +
			AGGIUDICAZIONI.NUM_OFFERTE_FUORI_SOGLIA + " = ?, " +
			AGGIUDICAZIONI.NUM_OFFERTE_ESCLUSE + " = ?, " +
			AGGIUDICAZIONI.NUM_IMP_ESCL_INSUF_GIUST + " = ?, " +
			
			AGGIUDICAZIONI.PERC_RIBASSO_AGG + " = ?, " +
			AGGIUDICAZIONI.PERC_OFF_AUMENTO + " = ?, " +
			AGGIUDICAZIONI.DATA_INVITO + " = ?, " + 
			AGGIUDICAZIONI.NUM_MANIF_INTERESSE + " = ?, " +
			AGGIUDICAZIONI.DATA_MANIF_INTERESSE + " = ?, " +
			AGGIUDICAZIONI.PROCEDURA_ACC + " = ?, " +
			AGGIUDICAZIONI.PREINFORMAZIONE + " = ?, " +
			AGGIUDICAZIONI.TERMINE_RIDOTTO + " = ?, " +
			AGGIUDICAZIONI.ID_MODO_GARA + " = ?, " +
			AGGIUDICAZIONI.FLAG_RICH_SUBAPPALTO + " = ?, " +
			AGGIUDICAZIONI.COD_STRUMENTO + "=?, " +
			AGGIUDICAZIONI.IMP_NON_ASSOG + "=?, " +
			//gm nuovo per appalti multilotto
			AGGIUDICAZIONI.CODICE_CONTRATTO + "=?, " +
			AGGIUDICAZIONI.FLAG_AGGIUD_PRINCIPALE + "=?, " +
			
			//AGGIUDICAZIONI.DURATA_CONVENZIONE + "=? " +
			
			//gm nuovo codice 3.0
			AGGIUDICAZIONI.OPERE_URBANIZZAZIONE + "=? " +
			//gm fine nuovo codice 3.0

			// PP B302.2.0
			(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive() ? "," + AGGIUDICAZIONI.ID_MOTIVO_VAR_CO + "=? "  : "") + 
			", " + AGGIUDICAZIONI.RELAZIONE_UNICA + "= ?"+ //TICKET ALM #14639 - 3.04.5
			" WHERE "
			+ AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = ?"
			+" AND "
			+ AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	         
	// PP fix controllo stato scheda
	private final String WHERE_DEF = " AND " + AGGIUDICAZIONI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING;
	private final String WHERE_CONF = " AND (" + AGGIUDICAZIONI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING
	                                 + " OR " + AGGIUDICAZIONI.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING	+ " )" ;
			
	private final String QUERY_INSERT_AGGIUDICAZIONI = 
		  "INSERT INTO " + AGGIUDICAZIONI.TABLE_NAME + " ( " +
		AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE + ", " + 
		AGGIUDICAZIONI.ID_INFO + ", " + 
		AGGIUDICAZIONI.DATA_INIZIO_INFO + ", " + 
		AGGIUDICAZIONI.NUM_IMPRESE_INVITATE + ", " + 
		AGGIUDICAZIONI.NUM_IMPRESE_RICHIEDENTI + ", " + 
		AGGIUDICAZIONI.NUM_IMPRESE_OFFERENTI + ", " + 
		AGGIUDICAZIONI.NUM_OFFERTE_AMMESSE + ", " + 
		AGGIUDICAZIONI.DATA_VERBALE_AGGIUDICAZIONE + ", " + 
		AGGIUDICAZIONI.DATA_STIPULA + ", " +
		AGGIUDICAZIONI.SOTTOTIPO + ", " +
		AGGIUDICAZIONI.PROG_CUI_RIAGGIUDICATO + ", " +
		AGGIUDICAZIONI.MODALITA_RIAGGIUDICAZIONE + ", " +
		AGGIUDICAZIONI.TERMINE_CONTRATTUALE + ", " +
		AGGIUDICAZIONI.DURATA_CONTRATTUALE + ", " +
		AGGIUDICAZIONI.DATA_SCADENZA_RICHIESTA_INVITO + ", " + 
		AGGIUDICAZIONI.DATA_SCADENZA_PRES_OFFERTA + ", " + 
		AGGIUDICAZIONI.ID_MODALITA_GARA + ", " + 
		AGGIUDICAZIONI.CUI + ", " + 
		AGGIUDICAZIONI.PROG_CUI + ", " + 
		AGGIUDICAZIONI.IMPORTO_AGGIUDICAZIONE + ", " + 
		AGGIUDICAZIONI.IMPORTO_COMPLESSIVO + ", " + 
		AGGIUDICAZIONI.ID_SCELTA_CONTRAENTE + ", " + 
		AGGIUDICAZIONI.IMPORTO_LAVORI + ", " +
		AGGIUDICAZIONI.IMPORTO_SERVIZI + ", " +
		AGGIUDICAZIONI.IMPORTO_FORNITURE + ", " +
		AGGIUDICAZIONI.IMPORTO_ATTUAZIONE_SICUREZZA + ", " +
		AGGIUDICAZIONI.IMPORTO_PROGETTAZIONE + ", " +
		AGGIUDICAZIONI.IMPORTO_DISPOSIZIONE + ", " +
		AGGIUDICAZIONI.SISTEMA_QUALIFICAZIONE + ", " + 
		AGGIUDICAZIONI.CRITERI_SELEZIONE_STABILITI_SA + ", " + 
		AGGIUDICAZIONI.ID_TIPO_PRESTAZIONE + ", " +
		AGGIUDICAZIONI.CUP + ", " + 
		AGGIUDICAZIONI.FLAG_ACCORDO_QUADRO+ ", " + 
		AGGIUDICAZIONI.LUOGO_ISTAT + ", " + 
		AGGIUDICAZIONI.LUOGO_NUTS + ", " + 
		AGGIUDICAZIONI.ASTA_ELETTRONICA + ", " +
		AGGIUDICAZIONI.OFFERTA_MASSIMO + ", " +
		AGGIUDICAZIONI.OFFERTA_MINIMA + ", " +
		AGGIUDICAZIONI.VAL_SOGLIA_ANOMALIA + ", " +
		AGGIUDICAZIONI.NUM_OFFERTE_FUORI_SOGLIA + ", " +
		AGGIUDICAZIONI.NUM_OFFERTE_ESCLUSE + ", " +
		AGGIUDICAZIONI.NUM_IMP_ESCL_INSUF_GIUST + ", " +
		AGGIUDICAZIONI.PERC_RIBASSO_AGG + ", " +
		AGGIUDICAZIONI.PERC_OFF_AUMENTO + ", " +
		AGGIUDICAZIONI.DATA_INVITO + ", " + 
		AGGIUDICAZIONI.NUM_MANIF_INTERESSE + ", " +
		AGGIUDICAZIONI.DATA_MANIF_INTERESSE + ", " +
		AGGIUDICAZIONI.ID_STATO + ", " +
		AGGIUDICAZIONI.PROCEDURA_ACC + ", " +
		AGGIUDICAZIONI.PREINFORMAZIONE + ", " +
		AGGIUDICAZIONI.TERMINE_RIDOTTO + ", " +
		AGGIUDICAZIONI.ID_MODO_GARA + ", " +
		AGGIUDICAZIONI.FLAG_RICH_SUBAPPALTO  + ", " +
		AGGIUDICAZIONI.COD_STRUMENTO + ", " +
		AGGIUDICAZIONI.IMP_NON_ASSOG +  ", " +
		//gm nuovo per appalti multilotto
		AGGIUDICAZIONI.CODICE_CONTRATTO + ", " +
		AGGIUDICAZIONI.FLAG_AGGIUD_PRINCIPALE + ", " +
		
		AGGIUDICAZIONI.OPERE_URBANIZZAZIONE+", "+

		AGGIUDICAZIONI.ID_SCHEDA_LOCALE +
		(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive() ?  ", " + AGGIUDICAZIONI.ID_MOTIVO_VAR_CO  : "") + 
		(SimogFlags.is3028_RFWEBSC00Active() ? ", " + AGGIUDICAZIONI.ORIGINE : "") +
		", " + AGGIUDICAZIONI.RELAZIONE_UNICA + //TICKET ALM #14639 - 3.04.5
		" ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
					"?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
					"?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
					"?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
					"?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
					"?, ?, ?, ?, ?, ?, ?, ?, ?" +
					(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive() ?  ",?" : "") + 
					(SimogFlags.is3028_RFWEBSC00Active() ?  ",?" : "") +
					",?"+//TICKET ALM #14639 - 3.04.5
					")";
	
	/**
	 * Query per prendere informazioni sulla presa incarico, 
	 * la data di inizio, il codicefiscale del RUP provv della presa incarico 
	 */
	
	private final String QUERY_SELECT_PRESA_INCARICO = 
		
			" select "
			+ INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO + " AS 'Data Inizio presa in carico', "
			+ INFO_AGGIUDICAZIONI.CF_RUP + " AS 'Cod Fiscale RUP', "
			+ INFO_AGGIUDICAZIONI.PROVV_PRESA_CARICO + " AS 'Estremi del provvedimento di nomina' " +
			" from "
			+ INFO_AGGIUDICAZIONI.TABLE_NAME + " " +
			" where "
			+ INFO_AGGIUDICAZIONI.ID_INFO + " = ? AND "
			+ INFO_AGGIUDICAZIONI.ID_STATO + " IN (" + StatiScheda.PRESA_IN_CARICO + "," + StatiScheda.IN_DEFINIZIONE + "," + StatiScheda.CONFERMATO + ") "
			+ " order by " + INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO  ;
	
//	private final String QUERY_SELECT_DATA_INIZIO_INFO =
//		" SELECT "
//		+ INFO_AGGIUDICAZIONI.TABLE_NAME + "." + INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO 
//		+" FROM "
//		+INFO_AGGIUDICAZIONI.TABLE_NAME
//		+" WHERE "
//		+INFO_AGGIUDICAZIONI.T_ID_INFO+" = ?";
	
	
	/**
	 * metodo per il recupero della categoria prevalente per un lotto
	 * 
	 * @param id_lotto long
	 * @return String - categoria prevalente
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public String estraiTipoGara(long id_lotto) throws SQLException{
		
		String query = "select ID_CATEGORIA_PREVALENTE as categoriaPrevalente from "
						+LOTTO.TABLE_NAME
						+" where "
						+ LOTTO.ID_LOTTO +" = "+ id_lotto;
		
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			stmt = activeConnection.prepareStatement(query);
		
				
			rs = stmt.executeQuery();
			if(rs.next())
				return rs.getString("categoriaPrevalente");
			return "";
		}
		finally{
			close(rs, stmt);
		}
	}
	
	/**
	 * Metodo per caricare combo tipo appalto con nuova struttura db 
	 * 
	 * @param tipiCategoria String
	 * @param tipoScheda String
	 * @param o Object deve essere un Timestamp o una String yyyymmdd
	 * @return Map&lt;String, String&gt; idAppalto , descrizione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public Map<String, String> caricaComboAppalto(String tipiCategoria, 
													String tipoScheda,
													Object o)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		HashMap<String, String> listaApp = new HashMap<String, String>();
		try{
			String query="";
			
			query="select DISTINCT " +
			TIPI_APPALTI.T_ID_APPALTO + ","+
			TIPI_APPALTI.T_DESCRIZIONE +
			" from " + 
			TIPI_APPALTI.TABLE_NAME+
			","+
			APPALTI_PER_CATEGORIA.TABLE_NAME+
			" WHERE " 
         + TIPI_APPALTI.T_ID_APPALTO + " = " +  APPALTI_PER_CATEGORIA.T_ID_APPALTO
         + " AND " + APPALTI_PER_CATEGORIA.T_ID_TIPO_CATEGORIA + " = ?"
         + " AND " + APPALTI_PER_CATEGORIA.T_ID_CATEGORIA + " = ?";			
			
			if(!SimogFlags.isFlagNoDate()){
			query += " AND " + buildISNULL(TIPI_APPALTI.T_DATA_FINE_VALIDITA,"99999999") + " >= ? "
			      + " AND " + buildISNULL(APPALTI_PER_CATEGORIA.T_DATA_FINE_VALIDITA,"99999999") + " >= ? ";
			}
			
			stmt = activeConnection.prepareStatement(query);
			String dataFine =  PageHelper.getFormattedNowOrInputFormattedDate(o, getNow());
			
			stmt.setObject(1, tipiCategoria);
			stmt.setObject(2, tipoScheda);

         if(!SimogFlags.isFlagNoDate()){
            stmt.setObject(3, dataFine);
            stmt.setObject(4, dataFine);
         }
         
			logger.debug("Select COMBO APPALTO, query ["+query+"]");
			
			rs = stmt.executeQuery();
			
			// PP debug
//			if (rs.next())				
//				listaApp.put(rs.getString(TIPI_APPALTI.ID_APPALTO), rs.getString(TIPI_APPALTI.DESCRIZIONE));
//			else
//				logger.fatal("***FATALE*** comboAppalti vuota: tipoScheda="+ tipoScheda + " tipiCategoria="+ tipiCategoria);
		
			while(rs.next()){
				listaApp.put(rs.getString(TIPI_APPALTI.ID_APPALTO), rs.getString(TIPI_APPALTI.DESCRIZIONE));
				
			}
			
			
		}catch(Exception e){
			logger.debug("eccezione: "+e);
		}finally{
			close(rs,stmt);
		}
		return listaApp;

	}
	
	/**
	 * Metodo per caricare combo tipo prestazione con nuova struttura db
	 * 
	 * @param idLotto
	 * @param tipoCategoria
	 * @param tipoScheda
	 * @param o Object deve essere un Timestamp o una String yyyymmdd
	 * @return Map&lt;String, String&gt; - mappa prestazioni
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public Map<String, String> caricaComboPrestazione(long idLotto, 
													String tipoCategoria, 
													String tipoScheda,
													Object o)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		HashMap<String, String> listaCP = new HashMap<String, String>();
		try{
			//String categoriaPrevalente = estraiTipoGara(idLotto).substring(0,2);
		//	ArrayList<String> scorporabili = getScorporabili(idLotto);
			String query="";
			
			query="select DISTINCT " +
			TIPI_PRESTAZIONI.T_ID_PRESTAZIONE + ","+
			TIPI_PRESTAZIONI.T_DESCRIZIONE +
			" from " + 
			TIPI_PRESTAZIONI.TABLE_NAME+ ","+
			
			PRESTAZIONI_PER_CATEGORIA.TABLE_NAME+
			
			" WHERE " + buildISNULL(PRESTAZIONI_PER_CATEGORIA.T_DATA_FINE_VALIDITA,"99999999") + " >= ? "
			+ " AND " + buildISNULL(TIPI_PRESTAZIONI.T_DATA_FINE_VALIDITA,"99999999") + " >= ? AND "+
			TIPI_PRESTAZIONI.T_ID_PRESTAZIONE+
			" = "+
			PRESTAZIONI_PER_CATEGORIA.T_ID_PRESTAZIONE+
			" and "+
			PRESTAZIONI_PER_CATEGORIA.ID_TIPO_CATEGORIA + " = ?" +
			" and " + 
			PRESTAZIONI_PER_CATEGORIA.T_ID_CATEGORIA + " = ?";		
			
			stmt = activeConnection.prepareStatement(query);
			logger.debug("Select COMBO PRESTAZIONE, query ["+query+"]");
			String dataFine =  PageHelper.getFormattedNowOrInputFormattedDate(o, getNow());
			stmt.setObject(1, dataFine);
			stmt.setObject(2, dataFine);
			stmt.setObject(3, tipoCategoria);
			stmt.setObject(4, tipoScheda);

			rs = stmt.executeQuery();
			while(rs.next()){
				listaCP.put(rs.getString(TIPI_PRESTAZIONI.ID_PRESTAZIONE), rs.getString(TIPI_PRESTAZIONI.DESCRIZIONE));			
			}		
		}catch(Exception e){
			logger.debug("eccezione: "+e);
		}finally{
			close(rs, stmt);
		}
		//logger.debug(tb.toString());
		return listaCP;

	}
	
	/**
	 * costruttore
	 * 
	 * @param activeConnection
	 * @param logger
	 */
	public AggiudicazioniManager ( Connection activeConnection, Logger logger ) {
		super ( activeConnection, logger );
	}
	
	
	/**
	 * metodo per il recupero di tutte le aggiudicazioni inerenti ad un id dati comuni
	 * 
	 * @param idInfo
	 * @param dataInizioInfo
	 * @return List&lt;AggiudicazioneBean&gt; - lista di aggiudicazioni
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public List<AggiudicazioneBean> getAggiudicazioniList(long idInfo, Timestamp dataInizioInfo) throws SQLException{
		ArrayList<AggiudicazioneBean> listaAgg = new ArrayList<AggiudicazioneBean>();
		AggiudicazioneBean agg = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_LISTA_AGGIUDICAZIONI_NEW);
			stmt.setLong(1, idInfo);
			stmt.setTimestamp(2, dataInizioInfo);
			rs = stmt.executeQuery();
			while(rs.next()){
				agg = new AggiudicazioneBean();
				agg.setIdAggiudicazione(rs.getLong(AGGIUDICAZIONI.ID_AGGIUDICAZIONE));
				agg.setDataInizioAggiudicazione(rs.getTimestamp(AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
				
				agg.setIdInfo(rs.getLong(AGGIUDICAZIONI.ID_INFO));
				agg.setDataInizioInfo(rs.getTimestamp(AGGIUDICAZIONI.DATA_INIZIO_INFO));
				
				agg.setSottotipo(TipoAggiudicazione.fromString(rs.getString(AGGIUDICAZIONI.SOTTOTIPO)));
				agg.setIdStato(rs.getLong(AGGIUDICAZIONI.ID_STATO));
				agg.setDescrizioneStato(rs.getString(STATI_SCHEDA.DESCRIZIONE));
				agg.setCui(rs.getString(AGGIUDICAZIONI.CUI));
				agg.setProgCUI(rs.getInt(AGGIUDICAZIONI.PROG_CUI));
				agg.setProgCuiRiaggiudicato(rs.getInt(AGGIUDICAZIONI.PROG_CUI_RIAGGIUDICATO));
				//gm aggiunto per appalti multilotto
				agg.setFlagAggiudPrincipale(rs.getString(AGGIUDICAZIONI.FLAG_AGGIUD_PRINCIPALE));
				agg.setCodiceContratto(rs.getString(AGGIUDICAZIONI.CODICE_CONTRATTO));
				agg.setIdLocale(rs.getString(AGGIUDICAZIONI.ID_SCHEDA_LOCALE));
				listaAgg.add(agg);
			}
			

			listaAgg.trimToSize();
			return listaAgg;	
			
		}finally {
			close(rs,stmt);
		}
	}
	
//	private final String QUERY_SELECT_LISTA_AGGIUDICAZIONI_MULTILOTTO = 
//		"SELECT " + AGGIUDICAZIONI.TABLE_NAME + ".*," +
//		STATI_SCHEDA.T_DESCRIZIONE +
//		" FROM " + AGGIUDICAZIONI.TABLE_NAME + ", " + STATI_SCHEDA.TABLE_NAME +
//		" WHERE " +
//		AGGIUDICAZIONI.CODICE_CONTRATTO + " = ? " +
//		" AND " +
//		"(" + AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE +
//		" OR " + AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.CONFERMATO + ")" +
//		" AND " +
//		AGGIUDICAZIONI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO +
//		" AND " +
//		AGGIUDICAZIONI.T_ID_INFO + " IN " +
//		"(SELECT DISTINCT " +
//		INFO_AGGIUDICAZIONI.T_ID_INFO + " FROM " + INFO_AGGIUDICAZIONI.TABLE_NAME +
//		" WHERE " +
//		INFO_AGGIUDICAZIONI.T_ID_LOTTO + " IN " +
//		"(SELECT DISTINCT " +
//		LOTTO.T_ID_LOTTO + " FROM " + LOTTO.TABLE_NAME +
//		" WHERE " +
//		LOTTO.T_ID_GARA + " = " +
//		"(SELECT " +
//		LOTTO.T_ID_GARA + " FROM " + LOTTO.TABLE_NAME +
//		" WHERE " +
//		LOTTO.T_ID_LOTTO + " = ? " +
//	    ")))";
	
	/**
	 * metodo per il recupero di tutte le aggiudicazioni inerenti ai lotti di una 
	 * stessa gara che hanno codice contratto e aggiudicatario in comune
	 * 
	 * @param codiceContratto
	 * @param idLotto
	 * @return List&lt;AggiudicazioneBean&gt; - lista di aggiudicazioni
	 */
//	public List<AggiudicazioneBean> getAggiudicazioniListMultilotto(String codiceContratto, long idLotto) {
//		ArrayList<AggiudicazioneBean> listaAgg = new ArrayList<AggiudicazioneBean>();
//		AggiudicazioneBean agg = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		try{
//			stmt = activeConnection.prepareStatement(QUERY_SELECT_LISTA_AGGIUDICAZIONI_MULTILOTTO);
//			stmt.setString(1, codiceContratto);
//			stmt.setLong(2, idLotto);
//			rs = stmt.executeQuery();
//			while(rs.next()){
//				agg = new AggiudicazioneBean();
//				this.fillBean(rs, agg);
//				listaAgg.add(agg);
//			}
//	    }
//		catch(Exception e){
//		    logger.debug("eccezione: "+e);
//		}
//		finally {
//			close(rs,stmt);
//		}
//		listaAgg.trimToSize();
//		return listaAgg;	
//	}
	
	
	
	/* =============================================================================== */
	
	/**
	 * Il metodo restituisce le informazioni relative alla presa di un incarico e quindi
	 * dati inizio info, cf RUP, provv_presa_carico. le informazioni sono ottenute dalla tabella 
	 * info_aggiudicazioni.
	 * 
	 * @param idInfo long . 
	 * @return restituisce una TableBean con le informazioni necessarie
	 * 
	 */
	
	public TableBean getInfoPresaIncarico( long idInfo) {
		
		logger.debug( "Ricerca informazioni presa incarico: \n\t[" + QUERY_SELECT_PRESA_INCARICO + " ]" );
		PreparedStatement pstmt = null;
		//Statement stmt = null;
		ResultSet rs = null;
		TableBean tBean = null;
		try {		
			pstmt = activeConnection.prepareStatement( QUERY_SELECT_PRESA_INCARICO );
			pstmt.setLong(1, idInfo);
			rs = pstmt.executeQuery();
			
			if (rs != null ) {
				tBean = new TableBean(rs);
				if(tBean== null) {
					logger.debug("\n\tTablebean per lo storico presa incarico: nullo");
				} else logger.debug("\n\tTableBean per lo storico presa incarico: non nullo");	
			}
			else logger.debug("\n\tNessun record trovato per lo storico presa incarico");
		}
		
		catch(SQLException e) {
			logger.error("errore nella query per le informazioni sulla presa incarico: ", e);
		}
		finally {
			close(rs, pstmt);
		}
		
		return tBean;
	}
	
	/* =============================================================================== */
	

	/**
	 * metodo per l'inserimento di un'aggiudicazione nel passaggio vengono settati i campi idAggiudicazione 
	 * e dataInizioAggiudicazione
	 * 
	 * @param aggiudicazione Bean Aggiudicazione
	 * @param cfUtente String
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public void insert(AggiudicazioneBean aggiudicazione, String cfUtente)
			 throws SQLException {

//		if(aggiudicazione!=null && aggiudicazione.getDataVerbaleAggiudicazione()==null)
//			throw new SQLException("SIMOG_SQL_501 - data verbale aggiudicazione non valorizzata - CIG: "+aggiudicazione.getCig());
		
		logger.debug("Inserimento Aggiudicazioni on query: [" + QUERY_INSERT_AGGIUDICAZIONI + "]");
//		logger.debug("sto inserendo l'agg: " + ObjectIntrospector.propertiesInfo(AggiudicazioneBean.class, aggiudicazione));
		logger.debug("Estremi Aggiudicazione da inserire: id_info [" +aggiudicazione.getIdAggiudicazione() + "] , dataInizioInfo [" + aggiudicazione.getDataInizioAggiudicazione()+"]");	
	
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			stmt = activeConnection.prepareStatement(createInsertQuery(QUERY_INSERT_AGGIUDICAZIONI, AGGIUDICAZIONI.ID_AGGIUDICAZIONE));
			
			int index = 1;
		
			aggiudicazione.setDataInizioAggiudicazione(getNow());
			stmt.setTimestamp(index++, aggiudicazione.getDataInizioAggiudicazione());
			stmt.setLong(index++, aggiudicazione.getIdInfo());                                // AGGIUDICAZIONI.ID_INFO
			stmt.setTimestamp(index++, aggiudicazione.getDataInizioInfo());                      // AGGIUDICAZIONI.DATA_INIZIO_INFO
			stmt.setObject(index++, aggiudicazione.getNumImpreseInvitate());                    // AGGIUDICAZIONI.NUM_IMPRESE_INVITATE
			stmt.setObject(index++, aggiudicazione.getNumImpreseRichiedenti());                 // AGGIUDICAZIONI.NUM_IMPRESE_RICHIEDENTI
			stmt.setObject(index++, aggiudicazione.getNumImpreseOfferenti());                   // AGGIUDICAZIONI.NUM_IMPRESE_OFFERENTI
			stmt.setObject(index++, aggiudicazione.getNumOfferteAmmesse());                     // AGGIUDICAZIONI.NUM_OFFERTE_AMMESSE
			stmt.setString(index++, PageHelper.formatDateOrNull(aggiudicazione.getDataVerbaleAggiudicazione()));             // AGGIUDICAZIONI.DATA_VERBALE_AGGIUDICAZIONE
			stmt.setString(index++, PageHelper.formatDateOrNull(aggiudicazione.getDataStipula()));             // AGGIUDICAZIONI.DATA_STIPULA
			if(aggiudicazione.getSottotipo() != null)
				stmt.setString(index++, aggiudicazione.getSottotipo().name());
			else
				stmt.setString(index++, TipoAggiudicazione.A.name());
			
			stmt.setInt(index++, aggiudicazione.getProgCuiRiaggiudicato());
			stmt.setInt(index++, aggiudicazione.getModalitaRiaggiudicazione());
			stmt.setString(index++, PageHelper.formatDateOrNull(aggiudicazione.getTermineContrattuale()));             // AGGIUDICAZIONI.TERMINE_CONTRATTUALE
			if(aggiudicazione.getDurataContrattuale() == null)
				stmt.setNull(index++, Types.BIGINT);
			else
				stmt.setObject(index++, aggiudicazione.getDurataContrattuale());
			          
			stmt.setString(index++, PageHelper.formatDateOrNull(aggiudicazione.getDataScadenzaRichiestaInvito()));           // AGGIUDICAZIONI.DATA_SCADENZA_RICHIESTA_INVITO
			stmt.setString(index++, PageHelper.formatDateOrNull(aggiudicazione.getDataScadenzaPresOfferta()));               // AGGIUDICAZIONI.DATA_SCADENZA_PRES_OFFERTA
			if(aggiudicazione.getIdModalitaGara() < 1)
				stmt.setNull(index++, Types.BIGINT);
			else
				stmt.setObject(index++, aggiudicazione.getIdModalitaGara());                        // AGGIUDICAZIONI.ID_MODALITA_GARA
			stmt.setString(index++, aggiudicazione.getCui());                                   // AGGIUDICAZIONI.CUI
			stmt.setObject(index++, aggiudicazione.getProgCUI());                               // AGGIUDICAZIONI.PROG_CUI
			stmt.setBigDecimal(index++, aggiudicazione.getImportoAggiudicazione());                 // AGGIUDICAZIONI.IMPORTO_AGGIUDICAZIONE
			stmt.setBigDecimal(index++, aggiudicazione.getImportoComplessivo());                 // AGGIUDICAZIONI.IMPORTO_AGGIUDICAZIONE
			stmt.setObject(index++, aggiudicazione.getIdSceltaContraente());                    // AGGIUDICAZIONI.ID_SCELTA_CONTRAENTE
			stmt.setBigDecimal(index++, aggiudicazione.getImportoLavori());
			stmt.setBigDecimal(index++, aggiudicazione.getImportoServizi());
			stmt.setBigDecimal(index++, aggiudicazione.getImportoForniture());
			stmt.setBigDecimal(index++, aggiudicazione.getImportoAttuazioneSicurezza());            // AGGIUDICAZIONI.IMPORTO_ATTUAZIONE_SICUREZZA
			stmt.setBigDecimal(index++, aggiudicazione.getImportoProgettazione());
			stmt.setBigDecimal(index++, aggiudicazione.getImportoDisposizione());		
			stmt.setString(index++, aggiudicazione.getSistemaQualificazione());                 // AGGIUDICAZIONI.SISTEMA_QUALIFICAZIONE
			stmt.setString(index++, aggiudicazione.getCriteriSelezioneStabilitiSA());           // AGGIUDICAZIONI.CRITERI_SELEZIONE_STABILITI_SA */
			
			if(aggiudicazione.getIdTipoPrestazione() < 1)
				stmt.setNull(index++, Types.BIGINT);
			else
				stmt.setObject(index++, aggiudicazione.getIdTipoPrestazione());        
		                        // AGGIUDICAZIONI.ID_STATO                   
			
			stmt.setString(index++, aggiudicazione.getCup());
			stmt.setString(index++, aggiudicazione.getFlagAccordoQuadro());
			stmt.setString(index++, aggiudicazione.getLuogoIstat());
			stmt.setString(index++, aggiudicazione.getLuogoNuts());
			stmt.setString(index++, aggiudicazione.getAstaElettronica());
			stmt.setBigDecimal(index++, aggiudicazione.getOffertaMassimo());
			stmt.setBigDecimal(index++, aggiudicazione.getOffertaMinima());
			stmt.setBigDecimal(index++, aggiudicazione.getValSogliaAnomalia());
			stmt.setObject(index++, aggiudicazione.getNumOfferteFuoriSoglia());
			stmt.setObject(index++, aggiudicazione.getNumOfferteEscluse());
			stmt.setObject(index++, aggiudicazione.getNumImpEscluseInsufGiust());
			stmt.setBigDecimal(index++, aggiudicazione.getPercRibassoAgg());
			stmt.setBigDecimal(index++, aggiudicazione.getPercOffAumento());
			stmt.setString(index++, PageHelper.formatDateOrNull(aggiudicazione.getDataInvito()));
			stmt.setObject(index++, aggiudicazione.getNumManifInteresse());
			stmt.setString(index++, PageHelper.formatDateOrNull(aggiudicazione.getDataManifInteresse()));
			//-----fine nuovi campi
			stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);    // AGGIUDICAZIONI.ID_STATO
			stmt.setString(index++, aggiudicazione.getProceduraAcc());
			stmt.setString(index++, aggiudicazione.getPreinformazione());
			stmt.setString(index++, aggiudicazione.getTermineRidotto());
			if (aggiudicazione.getIdModoIndizione()==0)
				stmt.setNull(index++, Types.BIGINT);
			else
				stmt.setObject(index++, aggiudicazione.getIdModoIndizione());
			stmt.setString(index++, aggiudicazione.getFlagRichSubappalto());
		    stmt.setString(index++, aggiudicazione.getCodStrumento());
		    stmt.setBigDecimal(index++, aggiudicazione.getImportoNonAssog());
		    
		    //gm nuovo per appalti multilotto
		    stmt.setString(index++, aggiudicazione.getCodiceContratto());
		    stmt.setString(index++, aggiudicazione.getFlagAggiudPrincipale());
		    
		    //gm nuovo codice 3.0
		    //stmt.setObject(index++, aggiudicazione.getDurataConvenzione());
		    stmt.setString(index++, aggiudicazione.getOpereUrbanizzazione());
		    //gm fine nuovo codice 3.0
		    
		    if(aggiudicazione.getIdLocale() == null){
		    	stmt.setNull(index++, Types.VARCHAR);
		    }else{
		    	stmt.setString(index++, aggiudicazione.getIdLocale());
		    }
		    
		    // PP B302.2.0
		    if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive()){
			    if(aggiudicazione.getIdMotivoVarCO() == null){
			    	stmt.setNull(index++, Types.VARCHAR);
			    }else{
			    	stmt.setString(index++, aggiudicazione.getIdMotivoVarCO());
			    }		    	
		    }
		    
		    if(SimogFlags.is3028_RFWEBSC00Active()){
		       stmt.setInt(index++, aggiudicazione.getOrigine());
		    }
		    
		    //TICKET ALM #14639 - 3.04.5
		    if(aggiudicazione.getRelazioneUnica()!=null)
		    	stmt.setString(index++, aggiudicazione.getRelazioneUnica());
		    else
		    	stmt.setNull(index++, Types.VARCHAR);
		    //FINE TICKET ALM #14639 - 3.04.5
		    
			logger.debug(QUERY_INSERT_AGGIUDICAZIONI);
			
			stmt.execute();
			
			rs = stmt.getResultSet();
			if(rs.next()){
				aggiudicazione.setIdAggiudicazione(rs.getLong(AGGIUDICAZIONI.ID_AGGIUDICAZIONE));
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(aggiudicazione.getIdAggiudicazione());
				attributiChiave.add(aggiudicazione.getDataInizioAggiudicazione());
				if(TipoAggiudicazione.Q.equals(aggiudicazione.getSottotipo()))
					LogBloccoDatiManager.loggingINSERT(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_ADESIONE, attributiChiave);
				else if(TipoAggiudicazione.E.equals(aggiudicazione.getSottotipo()))
					LogBloccoDatiManager.loggingINSERT(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_ESCLUSI, attributiChiave);
				else if(TipoAggiudicazione.S.equals(aggiudicazione.getSottotipo()))
					LogBloccoDatiManager.loggingINSERT(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_SOTTOSOGLIA, attributiChiave);
				else
					LogBloccoDatiManager.loggingINSERT(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_AGGIUDICAZIONE, attributiChiave);
			}
		
		}
		finally {
			close(rs,stmt);
		}		
}

	

	/**
	 * metodo per il salvataggio di un'aggiudicazione in stato di "definizione"
	 * 
	 * @param aggiudicazione Bean aggiudicazione
	 * @param cfUtente String
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public int save(AggiudicazioneBean aggiudicazione, String cfUtente) throws SQLException{
		
//		if(aggiudicazione!=null && aggiudicazione.getDataVerbaleAggiudicazione()==null)
//			throw new SQLException("SIMOG_SQL_501 - data verbale aggiudicazione non valorizzata - CIG: "+aggiudicazione.getCig());
		
		return updateAggiudicazione(aggiudicazione, cfUtente, false);
	}
	/**
	 * metodo per la conferma di un'aggiudicazione update allo stato di "confermato"
	 * nel passaggio nel bean viene settato il campo dataFineAggiudicazione
	 * 
	 * @param aggiudicazione Bean Aggiudicazione
	 * @param cfUtente String
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public int confirm(AggiudicazioneBean aggiudicazione, String cfUtente) throws SQLException{
//		if(aggiudicazione!=null && aggiudicazione.getDataVerbaleAggiudicazione()==null)
//			throw new SQLException("SIMOG_SQL_501 - data verbale aggiudicazione non valorizzata - CIG: "+aggiudicazione.getCig());
		return updateAggiudicazione(aggiudicazione, cfUtente, true);
	}
	
	
	private int updateAggiudicazione(AggiudicazioneBean aggiudicazione, String cfUtente, boolean conferma)
						 throws SQLException{
		PreparedStatement stmt = null;
		try{ 
//			logger.debug("sto inserendo l'agg: " + ObjectIntrospector.propertiesInfo(AggiudicazioneBean.class, aggiudicazione));	
			logger.debug("Estremi Aggiudicazione da inserire: id_info [" +aggiudicazione.getIdAggiudicazione() + "] , dataInizioInfo [" + aggiudicazione.getDataInizioAggiudicazione()+"]");	

			logger.debug("Update Aggiudicazioni on query: [" + QUERY_UPDATE_AGGIUDICAZIONI + "]");
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_AGGIUDICAZIONI 
			                                       + (SimogFlags.isFlagNoDate() ? WHERE_CONF : WHERE_DEF)
			                                         );
			
			int index = 1;
	                      
		                              
	                                
			stmt.setInt(index++, aggiudicazione.getNumImpreseInvitate());                    // AGGIUDICAZIONI.NUM_IMPRESE_INVITATE                            
			stmt.setInt(index++, aggiudicazione.getNumImpreseRichiedenti());                 // AGGIUDICAZIONI.NUM_IMPRESE_RICHIEDENTI                         
			stmt.setInt(index++, aggiudicazione.getNumImpreseOfferenti());                   // AGGIUDICAZIONI.NUM_IMPRESE_OFFERENTI                           
			stmt.setInt(index++, aggiudicazione.getNumOfferteAmmesse());                     // AGGIUDICAZIONI.NUM_OFFERTE_AMMESSE                             
			stmt.setString(index++, PageHelper.formatDateOrNull(aggiudicazione.getDataVerbaleAggiudicazione()));             // AGGIUDICAZIONI.DATA_VERBALE_AGGIUDICAZIONE                     
			stmt.setString(index++, PageHelper.formatDateOrNull(aggiudicazione.getDataStipula()));             // AGGIUDICAZIONI.DATA_STIPULA
			if(aggiudicazione.getSottotipo() != null)
				stmt.setString(index++, aggiudicazione.getSottotipo().name());
			else
				stmt.setString(index++, TipoAggiudicazione.A.name());         // AGGIUDICAZIONI.SOTTOTIPO
			
			stmt.setInt(index++, aggiudicazione.getProgCuiRiaggiudicato());
			stmt.setInt(index++, aggiudicazione.getModalitaRiaggiudicazione());
			stmt.setString(index++, PageHelper.formatDateOrNull(aggiudicazione.getTermineContrattuale()));             // AGGIUDICAZIONI.TERMINE_CONTRATTUALE
			if(aggiudicazione.getDurataContrattuale() == null)
				stmt.setNull(index++, Types.BIGINT);
			else
				stmt.setObject(index++, aggiudicazione.getDurataContrattuale());                          
			stmt.setString(index++, PageHelper.formatDateOrNull(aggiudicazione.getDataScadenzaRichiestaInvito()));           // AGGIUDICAZIONI.DATA_SCADENZA_RICHIESTA_INVITO                  
			stmt.setString(index++, PageHelper.formatDateOrNull(aggiudicazione.getDataScadenzaPresOfferta()));               // AGGIUDICAZIONI.DATA_SCADENZA_PRES_OFFERTA                      
			if(aggiudicazione.getIdModalitaGara() < 1)
				stmt.setNull(index++, Types.BIGINT);
			else
				stmt.setObject(index++, aggiudicazione.getIdModalitaGara());                            // AGGIUDICAZIONI.ID_MODALITA_GARA                                
		              
		                               
			stmt.setBigDecimal(index++, aggiudicazione.getImportoAggiudicazione());                 // AGGIUDICAZIONI.IMPORTO_AGGIUDICAZIONE     
			stmt.setBigDecimal(index++, aggiudicazione.getImportoComplessivo());                 // AGGIUDICAZIONI.IMPORTO_COMPLESSIVO         
			stmt.setObject(index++, aggiudicazione.getIdSceltaContraente());                    // AGGIUDICAZIONI.ID_SCELTA_CONTRAENTE                            
		    
			stmt.setBigDecimal(index++, aggiudicazione.getImportoLavori());
			stmt.setBigDecimal(index++, aggiudicazione.getImportoServizi());
			stmt.setBigDecimal(index++, aggiudicazione.getImportoForniture());
			
			stmt.setBigDecimal(index++, aggiudicazione.getImportoAttuazioneSicurezza());            // AGGIUDICAZIONI.IMPORTO_ATTUAZIONE_SICUREZZA                    
			stmt.setBigDecimal(index++, aggiudicazione.getImportoProgettazione());
			stmt.setBigDecimal(index++, aggiudicazione.getImportoDisposizione());
			
			stmt.setString(index++, aggiudicazione.getSistemaQualificazione());                 // AGGIUDICAZIONI.SISTEMA_QUALIFICAZIONE
			                   
	
			stmt.setString(index++, aggiudicazione.getCriteriSelezioneStabilitiSA());           // AGGIUDICAZIONI.CRITERI_SELEZIONE_STABILITI_SA                  
			
			if(conferma){
				aggiudicazione.setDataFineAggiudicazione(getNow());
				stmt.setTimestamp(index++, aggiudicazione.getDataFineAggiudicazione());
				//stmt.setObject(index++, PageHelper.formatTimeStamp(aggiudicazione.getDataFineAggiudicazione()));
				stmt.setLong(index++, StatiScheda.CONFERMATO);                               // AGGIUDICAZIONI.ID_STATO

			}else{
				stmt.setTimestamp(index++, null);
				stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);                               // AGGIUDICAZIONI.ID_STATO

			}		
			
			
			if(aggiudicazione.getIdTipoPrestazione() < 1)
				stmt.setNull(index++, Types.BIGINT);
			else
				stmt.setObject(index++, aggiudicazione.getIdTipoPrestazione());        
		                        // AGGIUDICAZIONI.ID_STATO                                               // AGGIUDICAZIONI.ID_STATO
		
			
			
			stmt.setString(index++, aggiudicazione.getCup());
			stmt.setString(index++, aggiudicazione.getFlagAccordoQuadro());
			
			stmt.setString(index++, aggiudicazione.getLuogoIstat());
			stmt.setString(index++, aggiudicazione.getLuogoNuts());
		
			stmt.setString(index++, aggiudicazione.getAstaElettronica());
			stmt.setBigDecimal(index++, aggiudicazione.getOffertaMassimo());
			stmt.setBigDecimal(index++, aggiudicazione.getOffertaMinima());
			stmt.setBigDecimal(index++, aggiudicazione.getValSogliaAnomalia());
			stmt.setObject(index++, aggiudicazione.getNumOfferteFuoriSoglia());
			stmt.setObject(index++, aggiudicazione.getNumOfferteEscluse());
			stmt.setObject(index++, aggiudicazione.getNumImpEscluseInsufGiust());
		
			stmt.setBigDecimal(index++, aggiudicazione.getPercRibassoAgg());
			stmt.setBigDecimal(index++, aggiudicazione.getPercOffAumento());
			stmt.setString(index++, PageHelper.formatDateOrNull(aggiudicazione.getDataInvito()));
			stmt.setObject(index++, aggiudicazione.getNumManifInteresse());
			stmt.setString(index++, PageHelper.formatDateOrNull(aggiudicazione.getDataManifInteresse()));
			stmt.setString(index++, aggiudicazione.getProceduraAcc());
			stmt.setString(index++, aggiudicazione.getPreinformazione());
			stmt.setString(index++, aggiudicazione.getTermineRidotto());
			if (aggiudicazione.getIdModoIndizione()==0)
				stmt.setNull(index++, Types.BIGINT);
			else
				stmt.setObject(index++, aggiudicazione.getIdModoIndizione());
			stmt.setString(index++, aggiudicazione.getFlagRichSubappalto());
			stmt.setString(index++, aggiudicazione.getCodStrumento());
		    stmt.setBigDecimal(index++, aggiudicazione.getImportoNonAssog());
		    
		    //gm nuovo per appalti multilotto
		    stmt.setString(index++, aggiudicazione.getCodiceContratto());
		    stmt.setString(index++, aggiudicazione.getFlagAggiudPrincipale());
		    
		    //stmt.setObject(index++, aggiudicazione.getDurataConvenzione());
		    
		    //gm nuovo codice 3.0
		    stmt.setString(index++, aggiudicazione.getOpereUrbanizzazione());
		    //gm fine nuovo codice 3.0
		    
			//-----fine nuovi campi
			
		    // PP B302.2.0
		    if(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive()){
			    if(aggiudicazione.getIdMotivoVarCO() == null){
			    	stmt.setNull(index++, Types.VARCHAR);
			    }else{
			    	stmt.setString(index++, aggiudicazione.getIdMotivoVarCO());
			    }		    	
		    }
			
		    //TICKET ALM #14639 - 3.04.5
		    if(aggiudicazione.getRelazioneUnica()!=null)
		    	stmt.setString(index++, aggiudicazione.getRelazioneUnica());
		    else
		    	stmt.setNull(index++, Types.VARCHAR);
		     //TICKET ALM #14639 - 3.04.5 
		    
			stmt.setLong(index++, aggiudicazione.getIdAggiudicazione());           // AGGIUDICAZIONI.CRITERI_SELEZIONE_STABILITI_SA
			stmt.setTimestamp(index++, aggiudicazione.getDataInizioAggiudicazione());  
		
			logger.debug("Estremi Aggiudicazione Inserita: id_aggiudicazione [" +aggiudicazione.getIdAggiudicazione() + "] , dataInizio_aggiudicazione [" + aggiudicazione.getDataInizioAggiudicazione()+"]");	
			int num = stmt.executeUpdate();
			if(num > 0){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(aggiudicazione.getIdAggiudicazione());
				attributiChiave.add(aggiudicazione.getDataInizioAggiudicazione());
				if(!conferma){
					if(TipoAggiudicazione.Q.equals(aggiudicazione.getSottotipo()))
						LogBloccoDatiManager.loggingUPDATE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_ADESIONE, attributiChiave);
					else if(TipoAggiudicazione.E.equals(aggiudicazione.getSottotipo()))
						LogBloccoDatiManager.loggingUPDATE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_ESCLUSI, attributiChiave);
					else if(TipoAggiudicazione.S.equals(aggiudicazione.getSottotipo()))
						LogBloccoDatiManager.loggingUPDATE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_SOTTOSOGLIA, attributiChiave);
					else
						LogBloccoDatiManager.loggingUPDATE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_AGGIUDICAZIONE, attributiChiave);
				
				}
				else{
					if(TipoAggiudicazione.Q.equals(aggiudicazione.getSottotipo()))
						LogBloccoDatiManager.loggingCONFIRM(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_ADESIONE, attributiChiave);
					else if(TipoAggiudicazione.E.equals(aggiudicazione.getSottotipo()))
						LogBloccoDatiManager.loggingCONFIRM(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_ESCLUSI, attributiChiave);
					else if(TipoAggiudicazione.S.equals(aggiudicazione.getSottotipo()))
						LogBloccoDatiManager.loggingCONFIRM(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_SOTTOSOGLIA, attributiChiave);
					else
						LogBloccoDatiManager.loggingCONFIRM(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_AGGIUDICAZIONE, attributiChiave);
					
				}
			}
			return num;
		}
		finally {
			close(null,stmt);
		}	

}
	private final String QUERY_UPDATE_AGGIUDICAZIONE_PUBBLICAZIONE = 
		"UPDATE " + AGGIUDICAZIONI.TABLE_NAME + " SET " +
        AGGIUDICAZIONI.ID_PUBBLICAZIONE_AGG + " = ?, " + 
        AGGIUDICAZIONI.DATA_INIZIO_PUBB_AGG + " = ? " +
        " WHERE " +
        AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = ? " + 
        " AND " + 
        AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE + " = ? ";
	
	
	/**
	 * metodo per il salvataggio di una rettifica di un avviso di aggiudicazione
	*/
	public int updateAggiudicazionePubblicazione(long idPubblicazione, Timestamp dataInizioPubb, long idAggiudicazione, Timestamp dataInizioAgg)
	    throws SQLException{
        PreparedStatement stmt = null;
        try{
        	logger.debug("Update AggiudicazionePubblicazione on query: [" + QUERY_UPDATE_AGGIUDICAZIONE_PUBBLICAZIONE + "]");
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_AGGIUDICAZIONE_PUBBLICAZIONE);
			
			int index = 1;
	                                                    
			stmt.setLong(index++, idPubblicazione);                                         
			stmt.setTimestamp(index++, dataInizioPubb);                                     
			stmt.setLong(index++, idAggiudicazione);                                        
			stmt.setTimestamp(index++, dataInizioAgg);       
			int num = stmt.executeUpdate();
            return num;
        }
    	finally {
	    	close(null,stmt);
	    }	
	}
	
	/**
	 * metodo per il recupero del massimo progCUI dato l'id dei dati comuni
	 * 
	 * @param id_info String
	 * @param CUI String
	 * @return int - numero rappresentante l'ultimo progCUI
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public int getMaxProgCUI(String id_info, String CUI) throws SQLException{
		
		String query = "select max("+AGGIUDICAZIONI.PROG_CUI+") as maxProgCUI from "
						+AGGIUDICAZIONI.TABLE_NAME
						+" where "
						+ AGGIUDICAZIONI.ID_INFO +" = "+id_info 
						+ "   AND substring(" + AGGIUDICAZIONI.CUI + ",4,10)  = substring(?, 4, 10) ";
	
		PreparedStatement stmt = null;
		ResultSet rs =  null;
		try{
			stmt = activeConnection.prepareStatement(query);
			stmt.setObject(1, CUI);
			rs = stmt.executeQuery();
			
			if(rs.next())
				return rs.getInt("maxProgCUI");
			return 0;
		}
		finally{
			close(rs, stmt);
		}
	}

	/**
	 * metodo per il recupero di un'aggiudicazione dato il suo id e la data inizio aggiudicazione
	 * 
	 * @param idAggiudicazione
	 * @param dataAggiudicazione
	 * @param ignoraStato TODO
	 * @return AggiudicazioneBean - bean dell'aggiudicazione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public AggiudicazioneBean getAggiudicazioni(long idAggiudicazione, Timestamp dataAggiudicazione, boolean ignoraStato) throws SQLException {
		
		String mtd = "getAggiudicazioni";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		AggiudicazioneBean bean = new AggiudicazioneBean();
		
		String qry = QUERY_SELECT_AGGIUDICAZIONI;
		if(!ignoraStato)
			qry += WHERE_STATO;

		try{
			stmt = activeConnection.prepareStatement(qry);
				
			logger.debug(logPrefix+" query ["+qry+"]");
			
			stmt.setLong(1, idAggiudicazione);
			stmt.setTimestamp(2, dataAggiudicazione);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				//gm usato il fillBean anzichè il caricamento manuale dei dati
				fillBean(rs,bean);
//				logger.debug("Estremi dell'Aggiudicazione: ID_agg ["+bean.getIdAggiudicazione()+"] , DataInizio_agg ["+ bean.getDataInizioAggiudicazione()+"]");
				
			}
			
	
		}
		finally{
			close(rs,stmt);
		}
		return bean;
	}
	
		//MAC 46894 
		private final String WHERE_STANDARD_UPDATED =
			" WHERE " + 
			AGGIUDICAZIONI.PROG_CUI + " = ? " +
			"   AND substring(" + AGGIUDICAZIONI.CUI + ",3,10)  = ? " + 
			" AND " + AGGIUDICAZIONI.T_ID_STATO + "=" + STATI_SCHEDA.T_ID_STATO +
			" AND (" + AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.IN_DEFINIZIONE +
			" OR " + AGGIUDICAZIONI.T_ID_STATO + "=" + StatiScheda.CONFERMATO+")";
		//FINE MAC 46894
	
	/**
	 * METODO CHE SI OCCUPA DI RECUPERARE UNA AGGIUDICAZIONE DAL CUI + PROGCUI
	 * SERVE PER IL MASSLOADER
	 * @param CUI
	 * @return
	 */
   public AggiudicazioneBean getAggiudicazioneByProgAndCui(String CUI,
         boolean confermata) throws SQLException {
      String mtd = "getAggiudicazioniByProgAndCui";
      String logPrefix = CLAZZ + "." + mtd + ": ";
      AggiudicazioneBean bean = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;

      // NEW_CIG
      String realCUI = CIGBean.getRealCIG(CUI.substring(0, 10).trim());

      // PP 03/12/2012 introdotto ciclo per tentare con il cui originale
      int cicle = 0;

      try {
         while (cicle < 2) {
            cicle++;

            int progCUI = Integer.parseInt(CUI.substring(11,12));
            logger.debug("cui: [" + realCUI + "] - progcui: [" + progCUI + "]");
            if (confermata) {
               stmt = activeConnection
                     .prepareStatement(QUERY_SELECT_AGGIUDICAZIONI_MASSLOADER
                           + WHERE_CONFERMATA);
               logger.debug(logPrefix + " query ["
                     + QUERY_SELECT_AGGIUDICAZIONI_MASSLOADER
                     + WHERE_CONFERMATA + "]");
            } else {
            	//MAC 46894
		//              stmt = activeConnection
		//                    .prepareStatement(QUERY_SELECT_AGGIUDICAZIONI_MASSLOADER
		//                          + WHERE_STANDARD);
		             stmt = activeConnection
		             .prepareStatement(QUERY_SELECT_AGGIUDICAZIONI_MASSLOADER
		                   + WHERE_STANDARD_UPDATED);
		//              logger.debug(logPrefix + " query ["
		//                    + QUERY_SELECT_AGGIUDICAZIONI_MASSLOADER + WHERE_STANDARD
		//                    + "]");
		           logger.debug(logPrefix + " query ["
		           + QUERY_SELECT_AGGIUDICAZIONI_MASSLOADER + WHERE_STANDARD_UPDATED
		           + "]");
		         //FINE MAC 46894
            }

            stmt.setInt(1, progCUI);
            // FIXME: PP attenzione al cig cicle al momento risolto con la
            // concatenazione semplice ma poi ?
            stmt.setString(2, realCUI);
            rs = stmt.executeQuery();

            if (rs.next()) {
               bean = new AggiudicazioneBean();
               this.fillBean(rs, bean);
               
               cicle = 2; // forzo uscita
            } else {
               // riprovo con il cui originario
               realCUI = CUI.substring(0, 10).trim();
            }
         }
      } finally {
         close(rs, stmt);
      }
      return bean;
   }
	
	/**
	 * metodo per il recupero di tutte le aggiudicazioni inerenti ad un CIG
	 * 
	 * @param CIG
	 * @return List&lt;AggiudicazioneBean&gt; - lista di aggiudicazioni
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public List<AggiudicazioneBean> getAggiudicazioniByCIG(String CIG) throws SQLException{
		ArrayList<AggiudicazioneBean> listaAgg = new ArrayList<AggiudicazioneBean>();
		AggiudicazioneBean agg = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT " + 
		    AGGIUDICAZIONI.ID_AGGIUDICAZIONE + ", " +
		    AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE + ", " +
		    AGGIUDICAZIONI.PROG_CUI + ", " +
		    AGGIUDICAZIONI.PROG_CUI_RIAGGIUDICATO + ", " +
		    AGGIUDICAZIONI.DATA_VERBALE_AGGIUDICAZIONE + ", " +
		    AGGIUDICAZIONI.CODICE_CONTRATTO +
		    " FROM " + AGGIUDICAZIONI.TABLE_NAME + 
		    " WHERE " +
		  //MAC 43853 da =? è diventato LIKE ?
		    AGGIUDICAZIONI.CUI + " LIKE ?" +
		    " AND " +
		    "(" + AGGIUDICAZIONI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE +
			" OR " 
		    + AGGIUDICAZIONI.ID_STATO + " = " + StatiScheda.CONFERMATO + ")";
		try{
			stmt = activeConnection.prepareStatement(query);
			// MAC 43853 String cui = "0-" + CIG; 
			stmt.setString(1, "%"+CIG);
			rs = stmt.executeQuery();
			while(rs.next()){
				agg = new AggiudicazioneBean();
				agg.setIdAggiudicazione(rs.getLong(AGGIUDICAZIONI.ID_AGGIUDICAZIONE));
				agg.setDataInizioAggiudicazione(rs.getTimestamp(AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
				agg.setProgCUI(rs.getInt(AGGIUDICAZIONI.PROG_CUI));
				agg.setProgCuiRiaggiudicato(rs.getInt(AGGIUDICAZIONI.PROG_CUI_RIAGGIUDICATO));
				agg.setDataVerbaleAggiudicazione(rs.getString(AGGIUDICAZIONI.DATA_VERBALE_AGGIUDICAZIONE));
				agg.setCodiceContratto(rs.getString(AGGIUDICAZIONI.CODICE_CONTRATTO));
				listaAgg.add(agg);
			}
		}
		finally{
			close(rs,stmt);
		}
		return listaAgg;
	}	


	/**
	 * metodo per la storicizzazione di un record
	 * 
	 * @param id_record String
	 * @param data_inizio_record Timestamp
	 * @param blocco String
	 * @return Timestamp - la nuova data inizio
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public Timestamp copyRecord(String id_record,Timestamp data_inizio_record,String blocco) throws SQLException{
		String QUERY_SELECT_DATA_FINE =
			"SELECT "
			+AGGIUDICAZIONI.DATA_FINE_AGGIUDICAZIONE
			+" FROM "+AGGIUDICAZIONI.TABLE_NAME
			+" WHERE "
			+AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE+"= ?"
			+" AND "+AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE+" = ?"
			+" AND "+AGGIUDICAZIONI.ID_STATO+" = "+StatiScheda.CONFERMATO;
		
		String QUERY_SET_STATO_RICHIESTA_ANNULLAMENTO =
			"UPDATE "+AGGIUDICAZIONI.TABLE_NAME+ " SET "
			+AGGIUDICAZIONI.ID_STATO+ " = ? , "
			+AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE+" = ? , "
			+AGGIUDICAZIONI.DATA_FINE_AGGIUDICAZIONE+" = ?"
			+" WHERE "
			+AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE+"= ?"
			+" AND "+AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE+"= ?"
			+" AND "+AGGIUDICAZIONI.ID_STATO+" = "+StatiScheda.CONFERMATO;
		
		String QUERY_COPY_RECORD =
			" INSERT INTO "+AGGIUDICAZIONI.TABLE_NAME+" ("
			
			+AGGIUDICAZIONI.CRITERI_SELEZIONE_STABILITI_SA
			+","+AGGIUDICAZIONI.CUI
		
			+","+AGGIUDICAZIONI.DATA_INIZIO_INFO
			
			+","+AGGIUDICAZIONI.DATA_SCADENZA_PRES_OFFERTA
			+","+AGGIUDICAZIONI.DATA_SCADENZA_RICHIESTA_INVITO
			+","+AGGIUDICAZIONI.DATA_VERBALE_AGGIUDICAZIONE
			+","+AGGIUDICAZIONI.DATA_STIPULA
			+","+AGGIUDICAZIONI.SOTTOTIPO
			+","+AGGIUDICAZIONI.PROG_CUI_RIAGGIUDICATO 
			+","+AGGIUDICAZIONI.MODALITA_RIAGGIUDICAZIONE 
			+","+AGGIUDICAZIONI.TERMINE_CONTRATTUALE
			+","+AGGIUDICAZIONI.DURATA_CONTRATTUALE
			+","+AGGIUDICAZIONI.ID_INFO
			+","+AGGIUDICAZIONI.ID_MODALITA_GARA
			//gm aggiunto per avvisi aggiudicazione
			+","+AGGIUDICAZIONI.T_ID_PUBBLICAZIONE_AGG 
			+","+AGGIUDICAZIONI.T_DATA_INIZIO_PUBB_AGG
			
			+","+AGGIUDICAZIONI.ID_SCELTA_CONTRAENTE
			
			+","+AGGIUDICAZIONI.ID_TIPO_PRESTAZIONE
			+","+AGGIUDICAZIONI.IMPORTO_AGGIUDICAZIONE
			+","+AGGIUDICAZIONI.IMPORTO_COMPLESSIVO
			+","+AGGIUDICAZIONI.IMPORTO_ATTUAZIONE_SICUREZZA
			
			+","+AGGIUDICAZIONI.NUM_IMPRESE_INVITATE
			+","+AGGIUDICAZIONI.NUM_IMPRESE_OFFERENTI
			+","+AGGIUDICAZIONI.NUM_IMPRESE_RICHIEDENTI
			+","+AGGIUDICAZIONI.NUM_OFFERTE_AMMESSE
			+","+AGGIUDICAZIONI.PROG_CUI
		
			+","+AGGIUDICAZIONI.SISTEMA_QUALIFICAZIONE +","
			
		
		+	AGGIUDICAZIONI.FLAG_ACCORDO_QUADRO+ ", " + 
			
			AGGIUDICAZIONI.LUOGO_ISTAT + ", " + 
			AGGIUDICAZIONI.LUOGO_NUTS + ", " + 
		
			AGGIUDICAZIONI.ASTA_ELETTRONICA + ", " +
			
			
			AGGIUDICAZIONI.PERC_RIBASSO_AGG + ", " +
			AGGIUDICAZIONI.PERC_OFF_AUMENTO + ", " + 
			AGGIUDICAZIONI.DATA_INVITO + ", " + 
			AGGIUDICAZIONI.NUM_MANIF_INTERESSE + ", " +
			AGGIUDICAZIONI.DATA_MANIF_INTERESSE + ", " +
			AGGIUDICAZIONI.PROCEDURA_ACC + ", " +
			AGGIUDICAZIONI.PREINFORMAZIONE + ", " +
			AGGIUDICAZIONI.TERMINE_RIDOTTO + ", " +
			AGGIUDICAZIONI.ID_MODO_GARA + ", " +
			AGGIUDICAZIONI.IMPORTO_LAVORI + ", " +
			AGGIUDICAZIONI.IMPORTO_SERVIZI + ", " +
			AGGIUDICAZIONI.IMPORTO_FORNITURE + ", " +
			AGGIUDICAZIONI.IMPORTO_DISPOSIZIONE + ", " +
			AGGIUDICAZIONI.IMPORTO_PROGETTAZIONE + ", " +
			AGGIUDICAZIONI.IMP_NON_ASSOG + ", " +
			//gm nuovo per appalti multilotto
			AGGIUDICAZIONI.CODICE_CONTRATTO + ", " +
			AGGIUDICAZIONI.FLAG_AGGIUD_PRINCIPALE + ", " +
			
			//AGGIUDICAZIONI.DURATA_CONVENZIONE + ", "+
			
			//gm nuovo codice 3.0
			AGGIUDICAZIONI.OPERE_URBANIZZAZIONE + ", "+
			//gm fine nuovo codice 3.0
			
			AGGIUDICAZIONI.CUP + ", " +
			AGGIUDICAZIONI.FLAG_RICH_SUBAPPALTO + ", " +
			AGGIUDICAZIONI.NUM_OFFERTE_ESCLUSE + ", " +
			AGGIUDICAZIONI.OFFERTA_MASSIMO + ", " +
			AGGIUDICAZIONI.OFFERTA_MINIMA + ", " +
			AGGIUDICAZIONI.VAL_SOGLIA_ANOMALIA + ", " +
			AGGIUDICAZIONI.NUM_OFFERTE_FUORI_SOGLIA + ", " +
			AGGIUDICAZIONI.COD_STRUMENTO + ", " +
			AGGIUDICAZIONI.NUM_IMP_ESCL_INSUF_GIUST + ", " +
			AGGIUDICAZIONI.ID_SCHEDA_LOCALE + ", " +
			
			(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive() ? AGGIUDICAZIONI.ID_MOTIVO_VAR_CO + ", "  : "") + 

			AGGIUDICAZIONI.ID_AGGIUDICAZIONE
			+","+AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE
			+","+AGGIUDICAZIONI.DATA_FINE_AGGIUDICAZIONE
			+","+AGGIUDICAZIONI.ID_STATO 
			+ (SimogFlags.is3028_RFWEBSC00Active() ? ", " + AGGIUDICAZIONI.ORIGINE : "")
			+ ") "
			+"SELECT "
			
			+AGGIUDICAZIONI.CRITERI_SELEZIONE_STABILITI_SA
			+","+AGGIUDICAZIONI.CUI
		
			+","+AGGIUDICAZIONI.DATA_INIZIO_INFO
			
			+","+AGGIUDICAZIONI.DATA_SCADENZA_PRES_OFFERTA
			+","+AGGIUDICAZIONI.DATA_SCADENZA_RICHIESTA_INVITO
			+","+AGGIUDICAZIONI.DATA_VERBALE_AGGIUDICAZIONE
			+","+AGGIUDICAZIONI.DATA_STIPULA
			+","+AGGIUDICAZIONI.SOTTOTIPO
			+","+AGGIUDICAZIONI.PROG_CUI_RIAGGIUDICATO 
			+","+AGGIUDICAZIONI.MODALITA_RIAGGIUDICAZIONE 
			+","+AGGIUDICAZIONI.TERMINE_CONTRATTUALE
			+","+AGGIUDICAZIONI.DURATA_CONTRATTUALE
			+","+AGGIUDICAZIONI.ID_INFO
			+","+AGGIUDICAZIONI.ID_MODALITA_GARA
			//gm aggiunto per avvisi aggiudicazione
			+","+AGGIUDICAZIONI.T_ID_PUBBLICAZIONE_AGG 
			+","+AGGIUDICAZIONI.T_DATA_INIZIO_PUBB_AGG
			
			+","+AGGIUDICAZIONI.ID_SCELTA_CONTRAENTE
			
			+","+AGGIUDICAZIONI.ID_TIPO_PRESTAZIONE
			+","+AGGIUDICAZIONI.IMPORTO_AGGIUDICAZIONE
			+","+AGGIUDICAZIONI.IMPORTO_COMPLESSIVO
			+","+AGGIUDICAZIONI.IMPORTO_ATTUAZIONE_SICUREZZA
			
			+","+AGGIUDICAZIONI.NUM_IMPRESE_INVITATE
			+","+AGGIUDICAZIONI.NUM_IMPRESE_OFFERENTI
			+","+AGGIUDICAZIONI.NUM_IMPRESE_RICHIEDENTI
			+","+AGGIUDICAZIONI.NUM_OFFERTE_AMMESSE
			+","+AGGIUDICAZIONI.PROG_CUI
		
			+","+AGGIUDICAZIONI.SISTEMA_QUALIFICAZIONE +","
			
		
		+	AGGIUDICAZIONI.FLAG_ACCORDO_QUADRO+ ", " + 
			
			AGGIUDICAZIONI.LUOGO_ISTAT + ", " + 
			AGGIUDICAZIONI.LUOGO_NUTS + ", " + 
		
			AGGIUDICAZIONI.ASTA_ELETTRONICA + ", " +
			
			
			AGGIUDICAZIONI.PERC_RIBASSO_AGG + ", " +
			AGGIUDICAZIONI.PERC_OFF_AUMENTO + ", " + 
			AGGIUDICAZIONI.DATA_INVITO + ", " + 
			AGGIUDICAZIONI.NUM_MANIF_INTERESSE + ", " +
			AGGIUDICAZIONI.DATA_MANIF_INTERESSE + ", " +
			AGGIUDICAZIONI.PROCEDURA_ACC + ", " +
			AGGIUDICAZIONI.PREINFORMAZIONE + ", " +
			AGGIUDICAZIONI.TERMINE_RIDOTTO + ", " +
			AGGIUDICAZIONI.ID_MODO_GARA + ", " +
			AGGIUDICAZIONI.IMPORTO_LAVORI + ", " +
			AGGIUDICAZIONI.IMPORTO_SERVIZI + ", " +
			AGGIUDICAZIONI.IMPORTO_FORNITURE + ", " +
			AGGIUDICAZIONI.IMPORTO_DISPOSIZIONE + ", " +
			AGGIUDICAZIONI.IMPORTO_PROGETTAZIONE + ", " +
			AGGIUDICAZIONI.IMP_NON_ASSOG + ", " +
			//gm nuovo per appalti multilotto
			AGGIUDICAZIONI.CODICE_CONTRATTO + ", " +
			AGGIUDICAZIONI.FLAG_AGGIUD_PRINCIPALE + ", " +
			
			//AGGIUDICAZIONI.DURATA_CONVENZIONE+", "+
			
			//gm nuovo codice 3.0
			AGGIUDICAZIONI.OPERE_URBANIZZAZIONE+", "+
			//gm fine nuovo codice 3.0
			
			AGGIUDICAZIONI.CUP + ", " +
			AGGIUDICAZIONI.FLAG_RICH_SUBAPPALTO + ", " +
			AGGIUDICAZIONI.NUM_OFFERTE_ESCLUSE + ", " +
			AGGIUDICAZIONI.OFFERTA_MASSIMO + ", " +
			AGGIUDICAZIONI.OFFERTA_MINIMA + ", " +
			AGGIUDICAZIONI.VAL_SOGLIA_ANOMALIA + ", " +
			AGGIUDICAZIONI.NUM_OFFERTE_FUORI_SOGLIA + ", " +
			AGGIUDICAZIONI.COD_STRUMENTO + ", " +
			AGGIUDICAZIONI.NUM_IMP_ESCL_INSUF_GIUST + ", " +
			AGGIUDICAZIONI.ID_SCHEDA_LOCALE + 
			(SimogFlags.is30220Active() && SimogFlags.isVarAnagActive() ? ", " + AGGIUDICAZIONI.ID_MOTIVO_VAR_CO  : "")  
			+", ?"
			+", ?"
			+", ?"
			+", ?"
			+ (SimogFlags.is3028_RFWEBSC00Active() ? ", " + AGGIUDICAZIONI.ORIGINE : "")
			+" FROM "+AGGIUDICAZIONI.TABLE_NAME
			+" WHERE "
			+AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE+" = ?"
			+" AND "+AGGIUDICAZIONI.T_DATA_INIZIO_AGGIUDICAZIONE+"= ?"
			+" AND "+AGGIUDICAZIONI.ID_STATO+" = "+StatiScheda.IN_DEFINIZIONE;
		
		String QUERY_AGGIORNA_TABELLA_RICHIESTA_ANNULLAMENTO = 
			"UPDATE "+RICHIESTA_ANNULLAMENTO.TABLE_NAME+" SET "
			+RICHIESTA_ANNULLAMENTO.DATA_INIZIO_RECORD+" = ?"
			+" WHERE "
			+RICHIESTA_ANNULLAMENTO.ID_RECORD+" = ? "
			+" AND "+RICHIESTA_ANNULLAMENTO.DATA_INIZIO_RECORD+" = ?"
			+" AND "+RICHIESTA_ANNULLAMENTO.BLOCCO+" = ?"
			+" AND "+RICHIESTA_ANNULLAMENTO.ESITO+" IS NULL";
		
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		PreparedStatement stmt4 = null;
		ResultSet rs = null;
		Timestamp nuovadatainizio = null;
		int num;
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_DATA_FINE);
			stmt.setLong(1, Long.parseLong(id_record));
			stmt.setTimestamp(2, data_inizio_record);
			
			rs = stmt.executeQuery();
			logger.debug("ESECUZIONE QUERY [ "+QUERY_SELECT_DATA_FINE+"]");
			if(rs.next()){
				Timestamp datafine = rs.getTimestamp(AGGIUDICAZIONI.DATA_FINE_AGGIUDICAZIONE);
				nuovadatainizio = getNow();
				stmt2 = activeConnection.prepareStatement(QUERY_SET_STATO_RICHIESTA_ANNULLAMENTO);
				stmt2.setLong(1, StatiScheda.IN_DEFINIZIONE);
				stmt2.setTimestamp(2, nuovadatainizio);
				stmt2.setNull(3, Types.TIMESTAMP);
				stmt2.setLong(4, Long.parseLong(id_record));
				stmt2.setTimestamp(5, data_inizio_record);
				
				num = stmt2.executeUpdate();
				logger.debug("ESECUZIONE QUERY [ "+QUERY_SET_STATO_RICHIESTA_ANNULLAMENTO+"]");
				if(num>0){
					stmt3 = activeConnection.prepareStatement(createCopyRecord(QUERY_COPY_RECORD,AGGIUDICAZIONI.TABLE_NAME));
					
					stmt3.setLong(1, Long.parseLong(id_record));
					stmt3.setTimestamp(2, data_inizio_record);
					stmt3.setTimestamp(3, datafine);
					stmt3.setLong(4, StatiScheda.ANNULLAMENTO_RICHIESTA);
					stmt3.setLong(5, Long.parseLong(id_record));
					stmt3.setTimestamp(6, nuovadatainizio);
					
					num = stmt3.executeUpdate();
					logger.debug("ESECUZIONE QUERY [ "+QUERY_COPY_RECORD+"]");
					if(num>0){
						stmt4 = activeConnection.prepareStatement(QUERY_AGGIORNA_TABELLA_RICHIESTA_ANNULLAMENTO);
						
						stmt4.setTimestamp(1, nuovadatainizio);
						stmt4.setLong(2, Long.parseLong(id_record));
						stmt4.setTimestamp(3, data_inizio_record);
						stmt4.setString(4, blocco);
						
						num = stmt4.executeUpdate();
						logger.debug("ESECUZIONE QUERY [ "+QUERY_AGGIORNA_TABELLA_RICHIESTA_ANNULLAMENTO+"]");
						
						return nuovadatainizio;
						
					}
					else
						return null;
				}
				else
					return null;
			}
			else
				return null;
		}/*catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}*/
		
		finally{
			close(null, stmt4);
			close(null, stmt3);
			close(null, stmt2);
			close(rs, stmt);
		}
	}
	
	public static String QUERY_DELETE_AGGIUDICAZIONI = 
		"DELETE FROM "+AGGIUDICAZIONI.TABLE_NAME+
		" WHERE " + AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	/**
	 * metodo per la cancellazione di un record
	 * 
	 * @param idRecord String
	 * @param dataInizioRecord Timestamp
	 * @return int - affected row count
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public int deleteRecord(String idRecord, Timestamp dataInizioRecord) throws SQLException{
	
		int numRow = -1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_DELETE_AGGIUDICAZIONI);
			logger.debug("query per la delete record attivo: "+QUERY_DELETE_AGGIUDICAZIONI);
		
			logger.debug(1 + ":::::: "+idRecord);
			stmt.setLong(1, Long.parseLong(idRecord));
			
			stmt.setTimestamp(2,dataInizioRecord);
			logger.debug(2 + "::: "+dataInizioRecord);
			
			numRow = stmt.executeUpdate();
			 
		} finally {
			close(rs, stmt);
		}
		return numRow;
}

	public static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_AGGIUDICAZIONI = 
		"UPDATE "+AGGIUDICAZIONI.TABLE_NAME+
		" SET " + AGGIUDICAZIONI.ID_STATO + " = "+ StatiScheda.CONFERMATO+
		" WHERE "+AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	/**
	 * metodo per l'aggionrnamento di un record  per la richiesta annullamento
	 * 
	 * @param idRecord String
	 * @param dataInizioRecord Timestamp
	 * @return int - affected row count
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public int updateRecordAggiudicazioni(String idRecord, Timestamp dataInizioRecord ) throws SQLException{
		
		int numRow = -1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_AGGIUDICAZIONI);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_AGGIUDICAZIONI);


			stmt.setLong(1, Long.parseLong(idRecord));
			logger.debug(1 + ": "+idRecord);
			
			stmt.setTimestamp(2,dataInizioRecord);
			logger.debug(2 + ": "+dataInizioRecord);
			numRow = stmt.executeUpdate();
			//PPactiveConnection.commit(currentActiveConnection);
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}

	/**
	 * metodo per la cancellazione di un record
	 * 
	 * @param idAggiudicazione String
	 * @param dataInizioAggiudicazione Timestamp
	 * @param dataRecordDaAnnullare Timestamp
	 * @return int - affected rows
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public int deleteAggiudicazioniRecord(String idAggiudicazione, Timestamp dataInizioAggiudicazione, Timestamp dataRecordDaAnnullare) throws SQLException{
			
	
			int numRow=-1;
			numRow=deleteRecord(idAggiudicazione, dataInizioAggiudicazione);		
	
			if(numRow>0){				
				//update dello stato di Aggiudicazione a confermato
				numRow = updateRecordAggiudicazioni(idAggiudicazione, dataRecordDaAnnullare);
			}	
			return numRow;
		}

	public static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_AGG_ACCETTATA = 
		"UPDATE "+AGGIUDICAZIONI.TABLE_NAME+
		" SET " + AGGIUDICAZIONI.ID_STATO + " = ?,"+
		AGGIUDICAZIONI.DATA_FINE_AGGIUDICAZIONE + " = " + buildGetDate() +
		" WHERE "+AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	/**
	 * metodo per l'aggiornamento di un record
	 * 
	 * @param idRecord
	 * @param dataInizioRecord
	 * @param stato_scheda stato al quale si vuole aggiornare il record
	 * @return int - affected row count
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public int updateRecord(String idRecord, Timestamp dataInizioRecord, String stato_scheda ) throws SQLException{
		int numRow=-1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_AGG_ACCETTATA);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_AGG_ACCETTATA);

	
			stmt.setObject(1, stato_scheda);
			logger.debug(1 + ": "+stato_scheda);
			
			stmt.setLong(2, Long.parseLong(idRecord));
			logger.debug(2 + ": "+idRecord);
			
			stmt.setTimestamp(3,dataInizioRecord);
			logger.debug(3 + ": "+dataInizioRecord);
			numRow = stmt.executeUpdate();
			//PPactiveConnection.commit(currentActiveConnection);
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}
	public static String QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_AGG_ACCETTATA_EDATAFINE = 
		"UPDATE "+AGGIUDICAZIONI.TABLE_NAME+
		" SET " + AGGIUDICAZIONI.ID_STATO + " = ?,"+ //+StatiScheda.CONFERMATO+
		AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE + " = ?,"+
		AGGIUDICAZIONI.DATA_FINE_AGGIUDICAZIONE + " = ?"+
		" WHERE "+AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = ?"+
		" AND "+AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
	
	public int updateRecordAndData(String idRecord, Timestamp dataInizioRecordNew,Timestamp dataInizioRecordOld,Timestamp dataFineOld, String stato_scheda ) throws SQLException{
		int numRow=-1;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_AGG_ACCETTATA_EDATAFINE);
			logger.debug("query per l'UPDATE del record con richiesta annullamento:::"+QUERY_UPDATE_RECORD_RICHIESTA_ANNULLAMENTO_AGG_ACCETTATA_EDATAFINE);

	
			stmt.setObject(1, stato_scheda);
			logger.debug(1 + ": "+stato_scheda);
			
			stmt.setTimestamp(2,dataInizioRecordOld);
			logger.debug(2 + ": "+dataInizioRecordOld);
			
			stmt.setTimestamp(3,dataFineOld);
			logger.debug(3 + ": "+dataFineOld);
			
			stmt.setLong(4, Long.parseLong(idRecord));
			logger.debug(4 + ": "+idRecord);
			
			stmt.setTimestamp(5,dataInizioRecordNew);
			logger.debug(5 + ": "+dataInizioRecordNew);
			numRow = stmt.executeUpdate();
			//PPactiveConnection.commit(currentActiveConnection);
		} finally {
			close(rs, stmt);
		}
		return numRow;
	}
	/**
	 * metodo per il check dell'esistenza di una aggiudicazione
	 * 
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @return boolean
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public boolean existAggiudicazione(long idAggiudicazione, Timestamp dataInizioAggiudicazione) throws SQLException{
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "SELECT * FROM " + AGGIUDICAZIONI.TABLE_NAME + " WHERE " + 
						AGGIUDICAZIONI.ID_AGGIUDICAZIONE + " = ? AND " + 
						AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE + " = ?";
		
		try{
			stmt = activeConnection.prepareStatement(query);
			stmt.setLong(1, idAggiudicazione);
			stmt.setTimestamp(2, dataInizioAggiudicazione);
			rs = stmt.executeQuery();
			return rs.next();
		}finally{
			close(rs, stmt);
		}				
	}
	
	/**
	 * metodo per il recupero della scelta contraente
	 * @param o Object deve essere un Timestamp o una String yyyymmdd
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
//	public Map<String,String> loadSceltaContraente(Object o, boolean isOrgano ) throws SQLException{
//		
//	   return super.loadSceltaContraente(o, isOrgano);	   
//	}
	
	/**
	 * metodo per il recuper delle condiazioni aggiuntive
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String,String&gt; -  id,descrizione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public Map<String,String> loadCondizioniAggiuntive(Object o) throws SQLException{
		return getTipologicaWithData(CONDIZIONI.TABLE_NAME, CONDIZIONI.ID_CONDIZIONE, CONDIZIONI.DESCRIZIONE, CONDIZIONI.DATA_INIZIO_VALIDITA, CONDIZIONI.DATA_FINE_VALIDITA,o);		
	}
	
	/**
	 * metodo per il recupero dei criteri di aggiudicazione
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public Map<String,String> loadCriteriAggiudicazione(Object o) throws SQLException{
		return getTipologicaWithData(MODALITA_GARA.TABLE_NAME, MODALITA_GARA.ID_MODALITA_GARA, MODALITA_GARA.DESCRIZIONE,MODALITA_GARA.DATA_INIZIO_VALIDITA ,MODALITA_GARA.DATA_FINE_VALIDITA,o);		
	}
	
	/**
	 * metodo per il recupero dei finanziamenti
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public Map<String,String> loadFinanziamenti(Object o) throws SQLException{
		return getTipologica(TIPO_FINANZIAMENTO.TABLE_NAME, TIPO_FINANZIAMENTO.ID_FINANZIAMENTO, TIPO_FINANZIAMENTO.DESCRIZIONE, TIPO_FINANZIAMENTO.DATA_FINE_VALIDITA,o);		
	}
	
	/**
	 * metodo per il recupero dei modi indizione
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public Map<String,String> loadModoIndizione(Object o) throws SQLException{
		return getTipologica(MODO_INDIZIONE.TABLE_NAME, MODO_INDIZIONE.ID_MODO_GARA, MODO_INDIZIONE.DESCRIZIONE, MODO_INDIZIONE.DATA_FINE_VALIDITA,o);		
	}
	
	/**
	 * metodo per il recupero degli strumenti
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public Map<String,String> loadStrumenti(Object o) throws SQLException{
		return getTipologica(TIPO_STRUMENTO.TABLE_NAME, TIPO_STRUMENTO.ID_STRUMENTO, TIPO_STRUMENTO.DESCRIZIONE, TIPO_STRUMENTO.DATA_FINE_VALIDITA,o);		
	}

	/**
	 * metodo per il recupero degli strumenti
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public Map<String,String> loadModiRiaggiud(Object o) throws SQLException{
		//TICKET ALM #2847 - Modalita Riagg
		//return getTipologica(MODI_RIAGGIUD.TABLE_NAME, MODI_RIAGGIUD.ID_MODO_RIAGGIUD, MODI_RIAGGIUD.DESCRIZIONE, MODI_RIAGGIUD.DATA_FINE_VALIDITA,o);		
		return getTipologicaWithData(MODI_RIAGGIUD.TABLE_NAME, MODI_RIAGGIUD.ID_MODO_RIAGGIUD, MODI_RIAGGIUD.DESCRIZIONE, MODI_RIAGGIUD.DATA_INIZIO_VALIDITA, MODI_RIAGGIUD.DATA_FINE_VALIDITA,o);		
		
	}

	/**
	 * metodo per il recupero dei motivi variazioni Co
	 * @param o deve essere Timestamp o String [yyyymmdd] per l'estensione della validit� di una tipologia a posteriori
	 * @return Map&lt;String,String&gt; - id,descrizione
	 * @throws SQLException eccezione sollevata durante l'esecuzione della query SQL
	 */
	public Map<String,String> loadMotiviVCO(Object o) throws SQLException{
		return getTipologica(MOTIVI_VARIAZIONE_CO.TABLE_NAME, MOTIVI_VARIAZIONE_CO.ID_MOTIVO_VAR_CO, MOTIVI_VARIAZIONE_CO.DESCRIZIONE, MOTIVI_VARIAZIONE_CO.DATA_FINE_VALIDITA,o);		
	}
	
	/**
	 * metodo per la generazione e il caricamento nel bean passato, del cui
	 * 
	 * @param icb
	 * @param ab
	 * @param isSimogValidationEnabled : attenzione flag che se settato a true offre la garanzia che il cig sia valido
	 * 	altrimenti il cig potrebbe non essere corretto formalmente
	 */
//	@Deprecated
//	public void setCUI(InfoComuniBean icb, AggiudicazioneBean ab, boolean isSimogValidationEnabled, boolean isSQLConnectionEnabledToRead){
//		
//		String CIG_CYCLE = Integer.toString(icb.getCigCycle());
//		String CIG = icb.getCig();
//		
//		//calcolo cig_kkk 
//		long kkk = 0;
//		String CIG_KKK = "";
//		// se ho la certezza della validita' del cig effettua operazioni
//		if(isSimogValidationEnabled || (SimogValidator.isNumero(CIG) && !"".equals(CIG))){
//			kkk = (Long.parseLong(CIG)*211)%4091;		
//			CIG_KKK = Long.toHexString(kkk).toUpperCase();
//		// altrimenti metto un valore di default
//		}else{
//			CIG = "0000000";
//			CIG_KKK = "KKK";
//		}
//
//		//la lunghezza di kkk deve essere 3, quindi ....
//		CIG_KKK = "000" + CIG_KKK ;
//		CIG_KKK = CIG_KKK.substring(CIG_KKK.length() - 3);
//
////
//		String CUI = CIG_CYCLE+"-"+CIG+CIG_KKK; 
//		ab.setCui(CUI);
////		
//		//settaggio di default
//		int maxProCUI = 0;
//		try {
//			//se ho un db pulito o esistente prendo il maxprogcui dal db
//			if(isSQLConnectionEnabledToRead){
//				maxProCUI = getMaxProgCUI(Long.toString(icb.getIdInfo()),CUI);
//			}
//			//altrimenti setto al default + 1 [=1] (vedi inizializzazioen variabile)
//			ab.setProgCUI(maxProCUI+1);
//		} catch (SQLException e) {
//			logger.error(e);
//		}
//		
//	}
	/**
	 * Metodo per il calcolo del cui e il progCui e valorizzazione all'interno del bean di aggiudicazione
	 * String CIG: attenzione non deve contenere il 9 !
	 * @param icb
	 * @param ab
	 * @param isSimogValidationEnabled : attenzione flag che se settato a true offre la garanzia che il cig sia valido
	 * 	altrimenti il cig potrebbe non essere corretto formalmente
	 */
	public void fillCuiAndProgCui(AggiudicazioneBean ab, String CIG, String CIG_CYCLE, long idInfo){		
		//calcolo cig_kkk 
		long kkk = 0;
		String CIG_KKK = "";

// 		nel caso in cui non ci sia la garanzia che arrivi il cig senza nove scommentare.
//		CIG = CIGBean.getRealCIG(CIG);
		// se ho la certezza della validita' del cig effettua operazioni, ri-calcola il KKK
		
		if((SimogValidator.isNumero(CIG) && !"".equals(CIG))){
			kkk = (Long.parseLong(CIG)*211)%4091;		
			CIG_KKK = Long.toHexString(kkk).toUpperCase();
		
			
		}else{
			//CIG = "0000000";
			//CIG_KKK = "KKK";
			
			String letter=CIG.substring(0,1); 
			Long index=new BigInteger(CIG.substring(1),16).longValue(); 
			
			
			CIG_KKK = ""+cigUtils.calcolaKKK(letter,index);
			
		}

		//la lunghezza di kkk deve essere 3, quindi ....
		CIG_KKK = "000" + CIG_KKK ;
		CIG_KKK = CIG_KKK.substring(CIG_KKK.length() - 3);

//
		String CUI = CIG_CYCLE+"-"+CIG+CIG_KKK; 
		ab.setCui(CUI);
//		
		//settaggio di default
		int maxProCUI = 0;
		try {

			maxProCUI = getMaxProgCUI(Long.toString(idInfo),CUI);
			//altrimenti setto al default + 1 [=1] (vedi inizializzazioen variabile)
			ab.setProgCUI(maxProCUI+1);
			
		} catch (SQLException e) {
			logger.error(e);
		}
		
	}
	
	/**
	 * Metodo per trovare l'indice di dispersione deviazione standard
	 * in base ad un anno 
	 * @param anno
	 */
	public BigDecimal getIndiceDispersione(String anno, String tipoSettore, String tipoContratto,Object data) throws SQLException{
		return getIndiceDispersioneByAnno(anno,  tipoSettore,  tipoContratto, data);		
	}
	
	/*********************************************************************************************************/
	/**************************		NUOVE FUNZIONALITA' 	**************************************************/
	/*********************************************************************************************************/
	
/**************************		LOAD 	**************************************************/	
	/**
	 * Load tramite identificativo esterno e cui, per garantire l'univocita dell'idLocale
	 * 
	 * @param idLocale
	 * @param cui
	 * @return
	 */
	public AggiudicazioneBean loadByIdLocale(String idLocale, String cui) throws SQLException{
		String mtd = "loadByIdLocale";
		String logPrefix = CLAZZ + "." + mtd + ": ";
		String realCUI = cui.substring(0, 10);
		//3.04.8 if successivo fa andare in errore l'elimina scheda
//		if(realCUI.trim().substring(0, 1).equals("9")){
//			realCUI = "0"+realCUI.substring(1);
//        }
		//gm se il realCUI è uguale al CIG lo modifico in forma normale "0-CIG"
		if(realCUI.trim().length()==10){
			realCUI = "0-"+realCUI;
		}
		int progCUI = Integer.parseInt(cui.substring(11));
		logger.debug("cui: ["+realCUI+"] - progcui: ["+progCUI+"]");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		AggiudicazioneBean bean = null;
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_AGGIUDICAZIONI_MASSLOADER + WHERE_IDLOCALE);
			logger.debug(logPrefix+" query ["+QUERY_SELECT_AGGIUDICAZIONI_MASSLOADER + WHERE_IDLOCALE+"]");
			
			stmt.setInt(1, progCUI);
			//FIXME: PP attenzione al cig cicle al momento risolto con la concatenazione semplice ma poi ?
			stmt.setString(2, realCUI);
			stmt.setString(3, idLocale);
			rs = stmt.executeQuery();
			bean =new AggiudicazioneBean();
			if(rs.next()){
				
				this.fillBean(rs, bean);		
			}	
		}
		finally{
			close(rs,stmt);
		}
		return bean;		
	}
	/**
	 * Load by id della scheda 
	 * 
	 * @param idSimog
	 * @return
	 * @throws SQLException
	 */
	public AggiudicazioneBean loadByIdSimog(long idSimog) throws SQLException{
		String mtd = "loadByIdSimog";
		String logPrefix = CLAZZ + "." + mtd + ": ";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		AggiudicazioneBean bean = null;
		try{
			stmt = activeConnection.prepareStatement(QUERY_SELECT_AGGIUDICAZIONI_MASSLOADER + WHERE_IDSIMOG);
		
			logger.debug(logPrefix+" query ["+QUERY_SELECT_AGGIUDICAZIONI_MASSLOADER + WHERE_IDSIMOG+"]");
			stmt.setLong(1, idSimog);
			rs = stmt.executeQuery();
			bean =new AggiudicazioneBean();
			if(rs.next()){
				
				this.fillBean(rs, bean);		
			}	
		}
		finally{
			close(rs,stmt);
		}
		return bean;		
	}
/**************************		VALORIZZAZIONE BEAN 	**************************************************/	
	/**
	 * Valorizzazione centralizza del bean di aggiudicazione 
	 * @param rs
	 * @param bean
	 * @throws SQLException
	 */
	public void fillBean(ResultSet rs, AggiudicazioneBean bean) throws SQLException{
		
		bean.setIdAggiudicazione(rs.getLong(AGGIUDICAZIONI.ID_AGGIUDICAZIONE));
		bean.setDataInizioAggiudicazione(rs.getTimestamp(AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE));
		bean.setIdInfo(rs.getLong(AGGIUDICAZIONI.ID_INFO));
		bean.setDataInizioInfo(rs.getTimestamp(AGGIUDICAZIONI.DATA_INIZIO_INFO));
		bean.setNumImpreseInvitate(rs.getInt(AGGIUDICAZIONI.NUM_IMPRESE_INVITATE));
		bean.setNumImpreseRichiedenti(rs.getInt(AGGIUDICAZIONI.NUM_IMPRESE_RICHIEDENTI));
		bean.setNumImpreseOfferenti(rs.getInt(AGGIUDICAZIONI.NUM_IMPRESE_OFFERENTI));
		bean.setNumOfferteAmmesse(rs.getInt(AGGIUDICAZIONI.NUM_OFFERTE_AMMESSE));
		bean.setDataVerbaleAggiudicazione(PageHelper.getViewDate(rs.getString(AGGIUDICAZIONI.DATA_VERBALE_AGGIUDICAZIONE)));
		bean.setDataStipula(PageHelper.getViewDate(rs.getString(AGGIUDICAZIONI.DATA_STIPULA)));
		bean.setSottotipo(TipoAggiudicazione.fromString(rs.getString(AGGIUDICAZIONI.SOTTOTIPO)));
		
		// retrocompatibilità sottotipo
		if (bean.getSottotipo() == null || "".equals(bean.getSottotipo().toString()))
		   bean.setSottotipo(TipoAggiudicazione.A);
		
		bean.setProgCuiRiaggiudicato(rs.getInt(AGGIUDICAZIONI.PROG_CUI_RIAGGIUDICATO));
		bean.setModalitaRiaggiudicazione(rs.getInt(AGGIUDICAZIONI.MODALITA_RIAGGIUDICAZIONE));
		
		bean.setTermineContrattuale(PageHelper.getViewDate(rs.getString(AGGIUDICAZIONI.TERMINE_CONTRATTUALE)));
		bean.setDurataContrattuale(PageHelper.getInteger(rs.getObject(AGGIUDICAZIONI.DURATA_CONTRATTUALE)));
		bean.setDataScadenzaRichiestaInvito(PageHelper.getViewDate(rs.getString(AGGIUDICAZIONI.DATA_SCADENZA_RICHIESTA_INVITO)));
		bean.setDataScadenzaPresOfferta(PageHelper.getViewDate(rs.getString(AGGIUDICAZIONI.DATA_SCADENZA_PRES_OFFERTA)));
		bean.setIdModalitaGara(rs.getLong(AGGIUDICAZIONI.ID_MODALITA_GARA));
	//	bean.setDataInizioPubbEsito(rs.getTimestamp(AGGIUDICAZIONI.DATA_INIZIO_PUBB_ESITO));
		bean.setDataFineAggiudicazione(rs.getTimestamp(AGGIUDICAZIONI.DATA_FINE_AGGIUDICAZIONE));
		bean.setCui(rs.getString(AGGIUDICAZIONI.CUI));
		bean.setProgCUI(rs.getInt(AGGIUDICAZIONI.PROG_CUI));
		bean.setImportoAggiudicazione(rs.getBigDecimal(AGGIUDICAZIONI.IMPORTO_AGGIUDICAZIONE));
		bean.setImportoComplessivo(rs.getBigDecimal(AGGIUDICAZIONI.IMPORTO_COMPLESSIVO));
	//	bean.setIdSceltaContraente(rs.getLong(AGGIUDICAZIONI.ID_SCELTA_CONTRAENTE));
		bean.setImportoLavori(rs.getBigDecimal(AGGIUDICAZIONI.IMPORTO_LAVORI));
		bean.setImportoServizi(rs.getBigDecimal(AGGIUDICAZIONI.IMPORTO_SERVIZI));
		bean.setImportoForniture(rs.getBigDecimal(AGGIUDICAZIONI.IMPORTO_FORNITURE));
		bean.setImportoAttuazioneSicurezza(rs.getBigDecimal(AGGIUDICAZIONI.IMPORTO_ATTUAZIONE_SICUREZZA));
		bean.setImportoDisposizione(rs.getBigDecimal(AGGIUDICAZIONI.IMPORTO_DISPOSIZIONE));
		bean.setImportoProgettazione(rs.getBigDecimal(AGGIUDICAZIONI.IMPORTO_PROGETTAZIONE));
		bean.setSistemaQualificazione(rs.getString(AGGIUDICAZIONI.SISTEMA_QUALIFICAZIONE));
		bean.setCriteriSelezioneStabilitiSA(rs.getString(AGGIUDICAZIONI.CRITERI_SELEZIONE_STABILITI_SA));
		bean.setIdStato(rs.getLong(AGGIUDICAZIONI.ID_STATO));
		bean.setIdTipoPrestazione(rs.getLong(AGGIUDICAZIONI.ID_TIPO_PRESTAZIONE));
		bean.setCup(rs.getString(AGGIUDICAZIONI.CUP));
		bean.setFlagAccordoQuadro(rs.getString(AGGIUDICAZIONI.FLAG_ACCORDO_QUADRO));
		bean.setLuogoIstat(rs.getString(AGGIUDICAZIONI.LUOGO_ISTAT));
		bean.setLuogoNuts(rs.getString(AGGIUDICAZIONI.LUOGO_NUTS));
		bean.setAstaElettronica(rs.getString(AGGIUDICAZIONI.ASTA_ELETTRONICA));
		bean.setPercOffAumento(rs.getBigDecimal(AGGIUDICAZIONI.PERC_OFF_AUMENTO));
		bean.setPercRibassoAgg(rs.getBigDecimal(AGGIUDICAZIONI.PERC_RIBASSO_AGG));
		bean.setDataInvito(PageHelper.getViewDate(rs.getString(AGGIUDICAZIONI.DATA_INVITO)));
		bean.setNumManifInteresse(rs.getInt(AGGIUDICAZIONI.NUM_MANIF_INTERESSE));
		bean.setDataManifInteresse(PageHelper.getViewDate(rs.getString(AGGIUDICAZIONI.DATA_MANIF_INTERESSE)));
		bean.setFlagRichSubappalto(rs.getString(AGGIUDICAZIONI.FLAG_RICH_SUBAPPALTO));
		bean.setNumOfferteEscluse(rs.getInt(AGGIUDICAZIONI.NUM_OFFERTE_ESCLUSE));
		bean.setOffertaMassimo(rs.getBigDecimal(AGGIUDICAZIONI.OFFERTA_MASSIMO));
		bean.setOffertaMinima(rs.getBigDecimal(AGGIUDICAZIONI.OFFERTA_MINIMA));
		bean.setValSogliaAnomalia(rs.getBigDecimal(AGGIUDICAZIONI.VAL_SOGLIA_ANOMALIA));
		bean.setNumOfferteFuoriSoglia(rs.getInt(AGGIUDICAZIONI.NUM_OFFERTE_FUORI_SOGLIA));
		bean.setNumImpEscluseInsufGiust(rs.getInt(AGGIUDICAZIONI.NUM_IMP_ESCL_INSUF_GIUST));
		bean.setProceduraAcc(rs.getString(AGGIUDICAZIONI.PROCEDURA_ACC));
		bean.setPreinformazione(rs.getString(AGGIUDICAZIONI.PREINFORMAZIONE));
		bean.setTermineRidotto(rs.getString(AGGIUDICAZIONI.TERMINE_RIDOTTO));
		bean.setIdSceltaContraente(rs.getBigDecimal(AGGIUDICAZIONI.ID_SCELTA_CONTRAENTE).longValue());
		bean.setDescrizioneStato(rs.getString(STATI_SCHEDA.DESCRIZIONE));
		bean.setIdModoIndizione(rs.getInt(AGGIUDICAZIONI.ID_MODO_GARA));
		bean.setCodStrumento(rs.getString(AGGIUDICAZIONI.COD_STRUMENTO));		
		bean.setImportoNonAssog(rs.getBigDecimal(AGGIUDICAZIONI.IMP_NON_ASSOG));
		//gm nuovo per appalti multilotto
		bean.setCodiceContratto(rs.getString(AGGIUDICAZIONI.CODICE_CONTRATTO));
		bean.setFlagAggiudPrincipale(rs.getString(AGGIUDICAZIONI.FLAG_AGGIUD_PRINCIPALE));
		//gm aggiunto per avvisi aggiudicazione
		bean.setIdPubblicazioneAgg(rs.getLong(AGGIUDICAZIONI.ID_PUBBLICAZIONE_AGG));
		bean.setDataPubblicazioneAgg(rs.getTimestamp(AGGIUDICAZIONI.DATA_INIZIO_PUBB_AGG));		
		
		//bean.setDurataConvenzione(rs.getLong(AGGIUDICAZIONI.DURATA_CONVENZIONE));
		
		//gm nuovo codice 3.0
		bean.setOpereUrbanizzazione(rs.getString(AGGIUDICAZIONI.OPERE_URBANIZZAZIONE));
		//gm fine nuovo codice 3.0
		
		bean.setIdLocale(rs.getString(AGGIUDICAZIONI.ID_SCHEDA_LOCALE));

		// PP B302.2.0
	    	bean.setIdMotivoVarCO(rs.getString(AGGIUDICAZIONI.ID_MOTIVO_VAR_CO));	    
	    
	    
	    //TICKET ALM #14639 - 3.04.5
	  	bean.setRelazioneUnica(rs.getString(AGGIUDICAZIONI.RELAZIONE_UNICA));
	    
        // PP 3.02.3.3 caricamento dati economici se appartiene ad aggiudicazione multilotto
        InfoComuniManager icMan = new InfoComuniManager(activeConnection, logger);

        InfoComuniBean dc = icMan.load(bean.getIdInfo(), bean.getDataInizioInfo());
        bean.setDatiEconomici(this.getDatiEconomici(bean.getCodiceContratto(), dc.getIdLotto()));

           bean.setOrigine( rs.getInt(AGGIUDICAZIONI.ORIGINE) );
        
//		logger.debug("bean caricato: " +ObjectIntrospector.propertiesInfo(AggiudicazioneBean.class, bean));
	}
	
/**************************		ANNULLAMENTO 	**************************************************/
	/**
	 * Eliminazione scheda tramite identificativo del sistema remoto e CIG
	 * 
	 * @param idLocale
	 * @param cig
	 * @param cfUtente
	 * @throws SQLException
	 */
	public boolean annulla(String idLocale, String cui, String cfUtente) throws SQLException{
		AggiudicazioneBean aggiudicazioneBean = loadByIdLocale(idLocale, cui);
		
		if (aggiudicazioneBean.getIdAggiudicazione() > 0){
			return _annulla(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione(), cfUtente);
		}
		return false;
	}
	
	/** Eliminazione scheda tramite identificativo simog
	 * @param idLocale
	 * @param cfUtente
	 * @throws SQLException
	 */
	public boolean annulla(long idSimog, String cfUtente) throws SQLException{
		AggiudicazioneBean aggiudicazioneBean = loadByIdSimog(idSimog);
		
		if (aggiudicazioneBean.getIdAggiudicazione() > 0){
			return _annulla(aggiudicazioneBean.getIdAggiudicazione(), aggiudicazioneBean.getDataInizioAggiudicazione(), cfUtente);
		}
		return false;
	}
	/** Eliminazione scheda tramite id e dataInizio della scheda.
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @param cfUtente
	 * @throws SQLException
	 */
	public boolean annulla(long idAggiudicazione, Timestamp dataInizioAggiudicazione, String cfUtente) throws SQLException{
		return _annulla(idAggiudicazione, dataInizioAggiudicazione, cfUtente);
	}

	/**
	 * @param idAggiudicazione
	 * @param dataInizioAggiudicazione
	 * @param cfUtente
	 * @throws SQLException
	 */
	private boolean _annulla(long idAggiudicazione, Timestamp dataInizioAggiudicazione, String cfUtente) throws SQLException{
		PreparedStatement stmt = null;
		boolean someRowAffected = false;
		try{ 
			stmt = activeConnection.prepareStatement(QUERY_ANNULLA_AGGIUDICAZIONI);
			AggiudicazioneBean ab = getAggiudicazioni(idAggiudicazione, dataInizioAggiudicazione, false);
			int index = 1;
			stmt.setLong(index++, StatiScheda.ELIMINATO);
			stmt.setTimestamp(index++, getNow());
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataInizioAggiudicazione);
			someRowAffected = stmt.executeUpdate() > 0;
			if(someRowAffected){
				List<Object> attributiChiave = new ArrayList<Object>();
				attributiChiave.add(idAggiudicazione);
				attributiChiave.add(dataInizioAggiudicazione);
				LogBloccoDatiManager.loggingDELETE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_AGGIUDICAZIONE, attributiChiave);
				if(TipoAggiudicazione.Q.equals(ab.getSottotipo()))
					LogBloccoDatiManager.loggingDELETE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_ADESIONE, attributiChiave);
				else if(TipoAggiudicazione.E.equals(ab.getSottotipo()))
					LogBloccoDatiManager.loggingDELETE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_ESCLUSI, attributiChiave);
				else if(TipoAggiudicazione.S.equals(ab.getSottotipo()))
					LogBloccoDatiManager.loggingDELETE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_SOTTOSOGLIA, attributiChiave);
				else
					LogBloccoDatiManager.loggingDELETE(activeConnection, logger, cfUtente, IdentificativoSchede.TAB_AGGIUDICAZIONE, attributiChiave);
			 
			}
			return someRowAffected;
		}
		finally {
			close(null,stmt);
		}

	}
	
	final String QUERY_IS_REVOCATA = "select " + FINE_LAVORI.ID_MOTIVO_INTERR 
	+ " from " + FINE_LAVORI.TABLE_NAME + "," + STATI_SCHEDA.TABLE_NAME + " where "
	+ FINE_LAVORI.ID_AGGIUDICAZIONE + " = ? and "
	+ FINE_LAVORI.DATA_INIZIO_AGGIUDICAZIONE + " = ? and "
	+ FINE_LAVORI.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO
	+ " and " + FINE_LAVORI.T_ID_STATO + " = " + StatiScheda.CONFERMATO
	+ " and " + FINE_LAVORI.ID_MOTIVO_INTERR 
	+ " <> "+Costanti.RECESSO_SA;
	
	
	
	final String QUERY_IS_REVOCATA_WITH_NEW = "SELECT " + AGGIUDICAZIONI.ID_AGGIUDICAZIONE 
	+ " from " +  AGGIUDICAZIONI.TABLE_NAME  + "," + STATI_SCHEDA.TABLE_NAME + " where "
	+ AGGIUDICAZIONI.ID_INFO + " = ? and " + AGGIUDICAZIONI.DATA_INIZIO_INFO + " = ? and "
	+ AGGIUDICAZIONI.PROG_CUI_RIAGGIUDICATO + " = ? "
	+ " AND " + AGGIUDICAZIONI.T_ID_STATO + "<>" + StatiScheda.ANNULLATO +
	" AND " + AGGIUDICAZIONI.T_ID_STATO + "<>" + StatiScheda.ELIMINATO;
	;
	
	public boolean isAggiudicazioneRevocata(long idAggiudicazione, Timestamp dataInizioAggiudicazione)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			stmt = activeConnection.prepareStatement(QUERY_IS_REVOCATA);
			int index = 1;
			stmt.setLong(index++, idAggiudicazione);
			stmt.setTimestamp(index++, dataInizioAggiudicazione);
			rs = stmt.executeQuery();
			return rs.next();
		}catch (SQLException e) {
			logger.error(e);
			throw e;
		}finally{
			close(rs, stmt);
		}
		
		
		
	}
	
	public boolean isRevocataWithNewAgg( long idInfo, Timestamp dataInizioInfo, int progCui)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{ 
			int index = 1;
			stmt = activeConnection.prepareStatement(QUERY_IS_REVOCATA_WITH_NEW);
			stmt.setLong(index++, idInfo);
			stmt.setTimestamp(index++, dataInizioInfo);
			stmt.setInt(index++, progCui);
			rs = stmt.executeQuery();
			return rs.next();
			
		}catch (SQLException e) {
			logger.error(e);
			throw e;
		}
	    finally{
		    close(rs, stmt);
	    }	
	}
	
    /** lettura quadro economico per controlli multilotto
     * @param idAggiudicazione principale
     * @param dataInizioAgg principale
     * @return
     */
    public DatiEconomiciBean getDatiEconomici(String contratto, long idLotto) {
       
       AggiudicatarioManager aggman = new AggiudicatarioManager(activeConnection, logger);
       MultilottoManager mman = new MultilottoManager(activeConnection, logger);

       DatiEconomiciBean retVal = null;
       List<AggiudicazioneBean> lista = mman.getAggiudicazioniListMultilotto(contratto, idLotto);
       
       if(lista.size() > 0){
          retVal = new DatiEconomiciBean();
          
          for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
             AggiudicazioneBean bean = (AggiudicazioneBean) iterator.next();
             
             retVal.setDataVerbaleAggiudicazione(
                   retVal.getDataVerbaleAggiudicazione().compareTo(bean.getDataVerbaleAggiudicazione()) < 0
                   ? bean.getDataVerbaleAggiudicazione() : retVal.getDataVerbaleAggiudicazione());
             
             retVal.setDataVerbaleAggiudicazioneMin(
                   retVal.getDataVerbaleAggiudicazioneMin().compareTo(bean.getDataVerbaleAggiudicazione()) > 0
                   ? bean.getDataVerbaleAggiudicazione() : retVal.getDataVerbaleAggiudicazioneMin());

             if(bean.getImportoAggiudicazione()!=null)
                retVal.setImportoAggiudicazione(retVal.getImportoAggiudicazione().add(bean.getImportoAggiudicazione()));
             
             if(bean.getImportoComplessivo()!=null)
                retVal.setImportoComplessivo(retVal.getImportoComplessivo().add(bean.getImportoComplessivo()));
             
             if(bean.getImportoDisposizione()!=null)
                retVal.setImportoDisposizione(retVal.getImportoDisposizione().add(bean.getImportoDisposizione()));
             
             if(bean.getImportoForniture()!=null)
                retVal.setImportoForniture(retVal.getImportoForniture().add(bean.getImportoForniture()));
             
             if(bean.getImportoLavori()!=null)
                 retVal.setImportoLavori(retVal.getImportoLavori().add(bean.getImportoLavori()));
             
             if(bean.getImportoNonAssog()!=null)
                retVal.setImportoNonAssog(retVal.getImportoNonAssog().add(bean.getImportoNonAssog()));
             
             if(bean.getImportoProgettazione()!=null)
                 retVal.setImportoProgettazione(retVal.getImportoProgettazione().add(bean.getImportoProgettazione()));
             
             if(bean.getImportoServizi()!=null)
                retVal.setImportoServizi(retVal.getImportoServizi().add(bean.getImportoServizi()));
             
             if(bean.getImportoAttuazioneSicurezza()!=null)
                retVal.setImportoSicurezza(retVal.getImportoSicurezza().add(bean.getImportoAttuazioneSicurezza()));
          }
          
          if (retVal != null){
             try {
               retVal.setAggiudicatari(aggman.loadMany(lista.get(0).getIdAggiudicazione(), lista.get(0).getDataInizioAggiudicazione(), false));
            } catch (SQLException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
          }
       }
       
       return retVal;
    }
    
    
   /**
    * Metodo per caricare combo tipo appalto ignorando il tipo categoria (Ordinario/Speciale)
    * 
    * @param tipoScheda
    * @param o
    * @return
    * @throws SQLException
    */
   public Map<String, String> caricaLottoComboAppalto(String tipoScheda, Object o) throws SQLException {
      PreparedStatement stmt = null;
      ResultSet rs = null;
      HashMap<String, String> listaApp = new HashMap<String, String>();
      try {
         String query = "";

         query = "SELECT DISTINCT "
               + TIPI_APPALTI.T_ID_APPALTO
               + "," + TIPI_APPALTI.T_DESCRIZIONE
               + " FROM "
               + TIPI_APPALTI.TABLE_NAME
               + "," + APPALTI_PER_CATEGORIA.TABLE_NAME
               + " WHERE "
               + buildISNULL(TIPI_APPALTI.T_DATA_FINE_VALIDITA, "99999999") + " >= ? "
               + " AND "
               + buildISNULL(APPALTI_PER_CATEGORIA.T_DATA_FINE_VALIDITA, "99999999") + " >= ? "
               + " AND " 
               + TIPI_APPALTI.T_ID_APPALTO + " = " + APPALTI_PER_CATEGORIA.T_ID_APPALTO 
               + " AND " 
               + APPALTI_PER_CATEGORIA.T_ID_CATEGORIA + " = ?"
//               + " AND "
//               + TIPI_APPALTI.T_DATA_FINE_VALIDITA + " IS NULL"
//               + " AND "
//               + APPALTI_PER_CATEGORIA.T_DATA_FINE_VALIDITA + " IS NULL"
               ;
               

         stmt = activeConnection.prepareStatement(query);
         String dataFine = PageHelper.getFormattedNowOrInputFormattedDate(o, getNow());

         stmt.setObject(1, dataFine);
         stmt.setObject(2, dataFine);
         stmt.setObject(3, tipoScheda);

         rs = stmt.executeQuery();

         while (rs.next()) {
            listaApp.put(
                  rs.getString(TIPI_APPALTI.ID_APPALTO),
                  rs.getString(TIPI_APPALTI.DESCRIZIONE));

         }

      } catch (Exception e) {
         logger.error("Impossibile caricare le tipologie di appalto", e);
      } finally {
         close(rs, stmt);
      }
      return listaApp;

   }
    

}
