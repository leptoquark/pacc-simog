package it.avlp.simog.garamanager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoRettificaBean;
import it.avlp.simog.beans.ProfiloEnum;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.StazioneAppaltante;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.GaraTableBean;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.AFFIDAMENTI_RISERVATI;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.db.generated.ART_ESCLUSIONE;
import it.avlp.simog.db.generated.ART_ESTREMA_URGENZA_SOMMA_URGENZA;
import it.avlp.simog.db.generated.CIG_STORIA;
import it.avlp.simog.db.generated.CPVEU;
import it.avlp.simog.db.generated.EAGG_CATEGORIE;
import it.avlp.simog.db.generated.EAGG_GARA_CATEGORIE;
import it.avlp.simog.db.generated.EAGG_MOTIVI;
import it.avlp.simog.db.generated.FINE_LAVORI;
import it.avlp.simog.db.generated.FUNZIONI_DELEGATE;
import it.avlp.simog.db.generated.FUNZIONI_DELEGATE_GARA;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.MODALITA_INDIZIONE_ALLEGATO_IX;
import it.avlp.simog.db.generated.MODI_REALIZZAZIONE;
import it.avlp.simog.db.generated.MODO_INDIZIONE;
import it.avlp.simog.db.generated.MOTIVI_CANCELLAZIONE;
import it.avlp.simog.db.generated.MOTIVO_COLLEGAMENTO;
import it.avlp.simog.db.generated.ORGANI_COSTITUZIONALI;
import it.avlp.simog.db.generated.PUBBLICAZIONI;
import it.avlp.simog.db.generated.REL_LOTTO_CATEGORIA_SCORPORABILE;
import it.avlp.simog.db.generated.RICHIESTA_ANNULLAMENTO;
import it.avlp.simog.db.generated.SCELTA_CONTRAENTE;
import it.avlp.simog.db.generated.SOGGETTI_RESPONSABILI;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.db.generated.STRUMENTI_SVOLGIMENTO_PROCEDURE;
import it.avlp.simog.db.generated.TIPI_APPALTI;
import it.avlp.simog.db.generated.TIPI_CATEGORIA;
import it.avlp.simog.db.generated.TIPOLOGIA;
import it.avlp.simog.db.generated.TIPO_APPALTO_AGG;
import it.avlp.simog.util.ConcatenateString;
import it.avlp.simog.util.ObjectIntrospector;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;
import it.avlp.simog.db.generated.LISTA_SOGGETTI_AGGREGATORI;

public class GaraManager extends AccessiDB {

	
	// Store procedure per l'inserimento di una nuova gara
	// Restituisce l'ID della gara appena inserita
	// private final static String INSERISCI_NUOVA_GARA = "INSERISCI_NUOVA_GARA";

	private final String BASE_SELECT_INFO_GARA_LOTTO = GARA.T_ID_GARA + ", " + GARA.T_OGGETTO + ", "
			+ GARA.T_CF_AMMINISTRAZIONE + ", " + GARA.DENOM_AMMINISTRAZIONE + ", " + GARA.T_ID_STAZIONE_APPALTANTE
			+ ", " + GARA.DENOM_STAZIONE_APPALTANTE + ", " + GARA.T_ID_OSSERVATORIO + ", " + GARA.DATA_CREAZIONE + ", " + GARA.T_CF_UTENTE +", "
			+ GARA.DATA_PERFEZIONAMENTO_BANDO + ", " + LOTTO.DATA_SCADENZA_PAGAMENTI + ", " + LOTTO.DATA_PUBBLICAZIONE
			+ ", " + LOTTO.DATA_CANCELLAZIONE_LOTTO + ", " + LOTTO.DATA_INIB_PAGAMENTO + ", "
			+ buildISNULL(LOTTO.IMPORTO_LOTTO, new BigDecimal(0)) + " AS " + LOTTO.IMPORTO_LOTTO + ", "
			+ buildISNULL(LOTTO.T_CIG, "") + " AS " + LOTTO.CIG + ", " + buildISNULL(LOTTO.T_CIG_KKK, "") + " AS "
			+ LOTTO.CIG_KKK + ", " + buildISNULL(LOTTO.T_ID_LOTTO, new Long(0)) + " AS " + LOTTO.ID_LOTTO + ", "
			+ buildISNULL(LOTTO.SOMMA_URGENZA, "N") + " AS " + LOTTO.SOMMA_URGENZA + ", "
			+ buildISNULL(LOTTO.T_OGGETTO, "") + " AS " + LOTTO.TABLE_NAME + LOTTO.OGGETTO + ", "
			+ buildISNULL(LOTTO.IMPORTO_IMPRESA, new BigDecimal(0)) + " AS " + LOTTO.IMPORTO_IMPRESA + ", "
			+ buildISNULL(LOTTO.IMPORTO_SA, new BigDecimal(0)) + " AS " + LOTTO.IMPORTO_SA + ", " + GARA.IMPORTO_GARA
			+ ", " + GARA.IMPORTO_SA_GARA + ", " + GARA.ID_STATO + ", " + STATI_SCHEDA.T_DESCRIZIONE + " AS STATOSCHEDA"

			+ ", " + INFO_AGGIUDICAZIONI.ID_INFO + ", " + INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO + ", upper("
			+ INFO_AGGIUDICAZIONI.CF_RUP + ") AS " + INFO_AGGIUDICAZIONI.CF_RUP +" , "+INFO_AGGIUDICAZIONI.CF_AMM
			+ ", " + LOTTO.DATA_CREAZIONE_LOTTO

			+ ", " + GARA.TIPO_SCHEDA_GARA + ", " + GARA.ID_MODO_GARA + ", " + GARA.ID_MODO_REAL + ", "
			+ GARA.CIG_ACC_QUADRO + ", " + GARA.ID_SVOLGIMENTO // TICKET ALM #664
			+ ", " + GARA.ID_ESTREMA_URGENZA // TICKET ALM #3832
			+ ", " + GARA.ID_ALLEGATO_IX // TICKET ALM #3834
			+ ", " + LOTTO.TIPO_CONTRATTO_LOTTO + ", " + LOTTO.FLAG_ESCLUSO
			/* gm nuovo codice 3.0 */
			+ ", " + LOTTO.TRIENNIO_ANNO_INIZIO + ", " + LOTTO.TRIENNIO_ANNO_FINE + ", " + LOTTO.TRIENNIO_PROGRESSIVO
			+ ", " + LOTTO.ANNUALE_CUI_MININF

			// FC necessario per elenco invitati
			+ ", " + PUBBLICAZIONI.T_ID_PUBBLICAZIONE + ", " + PUBBLICAZIONI.T_DATA_INIZIO_PUBB + ", "
			+ PUBBLICAZIONI.T_TIPO_OPERAZIONE

			// gm nuovo codice pubblicazione bando 3.0
			+ ", " + LOTTO.LUOGO_ISTAT + ", " + LOTTO.LUOGO_NUTS + ", " + LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA + ", "
			+ LOTTO.T_ID_SCELTA_CONTRAENTE + " AS " + LOTTO.ID_SCELTA_CONTRAENTE
			// gm fine nuovo codice pubblicazione bando 3.0
			+ ", " + LOTTO.ID_ESCLUSIONE + ", " + CIG_STORIA.APPLICAZIONE
			+ (SimogFlags.is3031_ESCL_AVCPASS() ? "," + GARA.ESCLUSO_AVCPASS : "")
			+ (SimogFlags.isINT85_RFWEBGL01Active() ? "," + GARA.SCELTA_LEGGE89 : "")
			+ (SimogFlags.isINT85_RFWEBGL01Active() ? "," + GARA.TIPOSA_BDNCP : "")
			+ (SimogFlags.isINT87_RFSIMOGWEB01Active() ? "," + GARA.URGENZA_DL133 : "")
			+ (SimogFlags.is30350_RFWEBGL01Active() ? "," + GARA.COD_MOTIVO_EAGG : "") 
			+ ","+ FUNZIONI_DELEGATE_GARA.T_CF_AMM_AGENTE + " AS T_CF_AMM_AGENTE"
			+ ","+FUNZIONI_DELEGATE_GARA.T_ID_F_DELEGATE+ " AS T_ID_F_DELEGATE"
			+ ", " + FUNZIONI_DELEGATE_GARA.T_DEN_AMM_AGENTE+ " AS T_DEN_AMM_AGENTE" // TICKET ALM #659 - 3.04.4
			+ ", " + FUNZIONI_DELEGATE_GARA.T_CF_AMM_DELEGATA+ " AS T_CF_AMM_DELEGATA" // TICKET ALM #659 - 3.04.4
			+ ", " + FUNZIONI_DELEGATE_GARA.T_DEN_AMM_DELEGATA+ " AS T_DEN_AMM_DELEGATA" // TICKET ALM #659 - 3.04.4
			+ ", " + FUNZIONI_DELEGATE.T_DESCRIZIONE + " AS "+FUNZIONI_DELEGATE.TABLE_NAME // TICKET ALM #659 - 3.04.4
			+", "+GARA.CODICE_AUSA
			+", "+LOTTO.IMPORTO_OPZIONI
	;

	private final String BASE_SELECT_INFO_GARA_LOTTO_FROM = " FROM (" + GARA.TABLE_NAME + " JOIN "
			+ STATI_SCHEDA.TABLE_NAME + " ON " + GARA.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO + ")" + " LEFT JOIN "
			+ PUBBLICAZIONI.TABLE_NAME + " ON " + GARA.T_ID_PUBBLICAZIONE + "=" + PUBBLICAZIONI.T_ID_PUBBLICAZIONE
			+ " LEFT JOIN " + LOTTO.TABLE_NAME + " ON " + GARA.T_ID_GARA + "=" + LOTTO.T_ID_GARA + " left outer join "
			+ CIG_STORIA.TABLE_NAME + " on (" + CIG_STORIA.T_CIG + " = " + LOTTO.T_CIG + " and "
			+ CIG_STORIA.T_CIG_CICLE + " = " + LOTTO.T_CIG_CICLE + ")" + " LEFT OUTER JOIN "
			+ INFO_AGGIUDICAZIONI.TABLE_NAME + " ON " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO
			+ " AND " + INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO + " = (SELECT MAX(" + INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO
			+ ")" + " FROM " + INFO_AGGIUDICAZIONI.TABLE_NAME + " WHERE " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + " = "
			+ LOTTO.T_ID_LOTTO + " AND (" + INFO_AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE
			+ "   OR " + INFO_AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.CONFERMATO + "   OR "
			+ INFO_AGGIUDICAZIONI.T_ID_STATO + " is null )" + ")"
			+ " LEFT JOIN "+FUNZIONI_DELEGATE_GARA.TABLE_NAME+" ON "+FUNZIONI_DELEGATE_GARA.T_ID_GARA+" = "+GARA.T_ID_GARA
			+ " LEFT JOIN " + FUNZIONI_DELEGATE.TABLE_NAME + " ON " + FUNZIONI_DELEGATE_GARA.T_ID_F_DELEGATE + "="
			+ FUNZIONI_DELEGATE.T_ID_F_DELEGATE;
	// + " WHERE 1 = 1 ";

	// seleziona le informazioni per i lotti che sono scaduti (data_scadenza !=
	// null)
	// che appartengono al gruppo "fs" e "fb" e visualizza i campi id_info e
	// data_inizio_info
	// valorizzati se per tali lotti esiste un'aggiudicazione
	private final String QUERY_RUP = GARA.T_ID_GARA + ", " + GARA.T_OGGETTO + ", " + GARA.T_CF_AMMINISTRAZIONE + ", " + GARA.T_CF_UTENTE +", "
			+ GARA.DENOM_AMMINISTRAZIONE + ", " + GARA.T_ID_STAZIONE_APPALTANTE + ", " + GARA.DENOM_STAZIONE_APPALTANTE
			+ ", " + GARA.DATA_CREAZIONE + ", " + GARA.DATA_PERFEZIONAMENTO_BANDO + ", " + LOTTO.DATA_SCADENZA_PAGAMENTI
			+ ", " + LOTTO.DATA_PUBBLICAZIONE + ", " + LOTTO.DATA_CANCELLAZIONE_LOTTO + ", " + LOTTO.DATA_INIB_PAGAMENTO
			+ ", " + buildISNULL(LOTTO.T_IMPORTO_LOTTO, new BigDecimal(0)) + " AS " + LOTTO.IMPORTO_LOTTO + ", "
			+ buildISNULL(LOTTO.T_IMPORTO_SA, new BigDecimal(0)) + " AS " + LOTTO.IMPORTO_SA + ", "
			+ buildISNULL(LOTTO.T_IMPORTO_IMPRESA, new BigDecimal(0)) + " AS " + LOTTO.IMPORTO_IMPRESA + ", "
			+ buildISNULL(LOTTO.T_ID_LOTTO, new Long(0)) + " AS " + LOTTO.ID_LOTTO + ", "
			+ buildISNULL(LOTTO.T_CIG_CICLE, "") + " AS " + LOTTO.CIG_CICLE + ", " + buildISNULL(LOTTO.T_CIG, "")
			+ " AS " + LOTTO.CIG + ", " + buildISNULL(LOTTO.T_CIG_KKK, "") + " AS " + LOTTO.CIG_KKK + ", "
			+ buildISNULL(LOTTO.T_OGGETTO, "") + " AS " + LOTTO.TABLE_NAME + LOTTO.OGGETTO + ", "
			+ buildISNULL(LOTTO.T_SOMMA_URGENZA, "N") + " AS " + LOTTO.SOMMA_URGENZA + ", "
			+ buildISNULL(CIG_STORIA.APPLICAZIONE, "") + " AS " + CIG_STORIA.APPLICAZIONE + ", "
			+ INFO_AGGIUDICAZIONI.ID_INFO + ", " + INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO + ", " + GARA.IMPORTO_GARA
			+ ", " + GARA.IMPORTO_SA_GARA + ", " + STATI_SCHEDA.T_DESCRIZIONE + " AS STATOSCHEDA, upper(" + INFO_AGGIUDICAZIONI.CF_RUP
			+ ") as " + INFO_AGGIUDICAZIONI.CF_RUP +" , "+INFO_AGGIUDICAZIONI.CF_AMM + ", "  + LOTTO.DATA_CREAZIONE_LOTTO

			+ ", " + GARA.TIPO_SCHEDA_GARA + ", " + GARA.ID_MODO_GARA + ", " + GARA.ID_MODO_REAL + ", "
			+ GARA.ID_SVOLGIMENTO // TICKET ALM #664
			+ ", " + GARA.ID_ESTREMA_URGENZA // TICKET ALM #3832
			+ ", " + GARA.ID_ALLEGATO_IX // TICKET ALM #3834
			+ ", " + GARA.CIG_ACC_QUADRO + ", " + LOTTO.TIPO_CONTRATTO_LOTTO + ", " + LOTTO.FLAG_ESCLUSO
			/* gm nuovo codice 3.0 */
			+ ", " + LOTTO.TRIENNIO_ANNO_INIZIO + ", " + LOTTO.TRIENNIO_ANNO_FINE + ", " + LOTTO.TRIENNIO_PROGRESSIVO
			+ ", " + LOTTO.ANNUALE_CUI_MININF

			// FC necessario per elenco invitati
			+ ", " + PUBBLICAZIONI.T_ID_PUBBLICAZIONE + ", " + PUBBLICAZIONI.T_DATA_INIZIO_PUBB + ", "
			+ PUBBLICAZIONI.T_TIPO_OPERAZIONE

			// gm nuovo codice pubblicazione bando 3.0
			+ ", " + LOTTO.LUOGO_ISTAT + ", " + LOTTO.LUOGO_NUTS + ", " + LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA + ", "
			+ LOTTO.T_ID_SCELTA_CONTRAENTE + " AS " + LOTTO.ID_SCELTA_CONTRAENTE

			// gm fine nuovo codice pubblicazione bando 3.0
			+ ", " + LOTTO.ID_ESCLUSIONE + (SimogFlags.is3031_ESCL_AVCPASS() ? "," + GARA.ESCLUSO_AVCPASS : "")
			+ (SimogFlags.is3031_RFWEBGL02Active() ? "," + LOTTO.FLAG_CUP : "")
			+ (SimogFlags.isINT85_RFWEBGL01Active() ? "," + GARA.SCELTA_LEGGE89 : "")
			+ (SimogFlags.isINT85_RFWEBGL01Active() ? "," + GARA.TIPOSA_BDNCP : "")
			+ (SimogFlags.isINT87_RFSIMOGWEB01Active() ? "," + GARA.URGENZA_DL133 : "")
			+ (SimogFlags.is30350_RFWEBGL01Active() ? "," + GARA.COD_MOTIVO_EAGG : "") 
			+ ","+ FUNZIONI_DELEGATE_GARA.T_CF_AMM_AGENTE + " AS T_CF_AMM_AGENTE"
			+ ","+FUNZIONI_DELEGATE_GARA.T_ID_F_DELEGATE+ " AS T_ID_F_DELEGATE"
			+ ", " + FUNZIONI_DELEGATE_GARA.T_DEN_AMM_AGENTE+ " AS T_DEN_AMM_AGENTE" // TICKET ALM #659 - 3.04.4
			+ ", " + FUNZIONI_DELEGATE_GARA.T_CF_AMM_DELEGATA+ " AS T_CF_AMM_DELEGATA" // TICKET ALM #659 - 3.04.4
			+ ", " + FUNZIONI_DELEGATE_GARA.T_DEN_AMM_DELEGATA+ " AS T_DEN_AMM_DELEGATA" // TICKET ALM #659 - 3.04.4
			+ ", " + FUNZIONI_DELEGATE.T_DESCRIZIONE + " AS "+FUNZIONI_DELEGATE.TABLE_NAME // TICKET ALM #659 - 3.04.4
			+ ", "+GARA.CODICE_AUSA
			+", "+LOTTO.IMPORTO_OPZIONI
	;

	//3.04.3.2
	private final String QUERY_RUP_FROM = " FROM " + GARA.TABLE_NAME + " WITH (NOLOCK) JOIN " + STATI_SCHEDA.TABLE_NAME + " ON "
			+ GARA.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO + " left join " + LOTTO.TABLE_NAME + " WITH (NOLOCK) on "
			+ GARA.T_ID_GARA + " = " + LOTTO.T_ID_GARA + " LEFT JOIN " + PUBBLICAZIONI.TABLE_NAME + " WITH (NOLOCK) ON "
			+ GARA.T_ID_PUBBLICAZIONE + "=" + PUBBLICAZIONI.T_ID_PUBBLICAZIONE + " LEFT OUTER JOIN "
			+ INFO_AGGIUDICAZIONI.TABLE_NAME + " WITH (NOLOCK) ON " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO
			+ " AND " + INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO + " = (SELECT MAX(" + INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO
			+ ")" + " FROM " + INFO_AGGIUDICAZIONI.TABLE_NAME + " WHERE " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + " = "
			+ LOTTO.T_ID_LOTTO + " AND (" + INFO_AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE
			+ "   OR " + INFO_AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.CONFERMATO + "   OR "
			+ INFO_AGGIUDICAZIONI.T_ID_STATO + " is null ))" + " left outer join " + CIG_STORIA.TABLE_NAME +  " WITH (NOLOCK) on ("
			+ CIG_STORIA.T_CIG + " = " + LOTTO.T_CIG + " and " + CIG_STORIA.T_CIG_CICLE + " = " + LOTTO.T_CIG_CICLE
			+ ")"
			+ " LEFT JOIN "+FUNZIONI_DELEGATE_GARA.TABLE_NAME+" ON "+FUNZIONI_DELEGATE_GARA.T_ID_GARA+" = "+GARA.T_ID_GARA
			+ " LEFT JOIN " + FUNZIONI_DELEGATE.TABLE_NAME + " ON " + FUNZIONI_DELEGATE_GARA.T_ID_F_DELEGATE + "="
			+ FUNZIONI_DELEGATE.T_ID_F_DELEGATE;
//		+" where 1=1 ";
//		" FROM "+ CIG_STORIA.TABLE_NAME +", "+ 
//		GARA.TABLE_NAME + " JOIN " + STATI_SCHEDA.TABLE_NAME 
//		+ " ON " + GARA.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO 
//		+" inner join "+LOTTO.TABLE_NAME 
//		+" on "+ GARA.T_ID_GARA+" = "+ LOTTO.T_ID_GARA+" left outer join "
//		+INFO_AGGIUDICAZIONI.TABLE_NAME+" on "+" ( "
//		+LOTTO.T_ID_LOTTO+" = "+INFO_AGGIUDICAZIONI.T_ID_LOTTO+" and ( "+INFO_AGGIUDICAZIONI.ID_INFO
//		+" = ( SELECT MAX( "+INFO_AGGIUDICAZIONI.ID_INFO+" ) FROM "
//		+INFO_AGGIUDICAZIONI.TABLE_NAME +" where "+LOTTO.T_ID_LOTTO +" = "
//		+INFO_AGGIUDICAZIONI.T_ID_LOTTO+" )))"
//		+" WHERE "
//		+ CIG_STORIA.T_CIG + " = " + LOTTO.T_CIG
//		+" AND "+CIG_STORIA.T_CIG_CICLE + " = " + LOTTO.T_CIG_CICLE +;
//		+" AND ("+INFO_AGGIUDICAZIONI.ID_STATO+" = "+StatiScheda.IN_DEFINIZIONE
//			+" OR "+INFO_AGGIUDICAZIONI.ID_STATO+" = "+StatiScheda.CONFERMATO
//			+" OR "+INFO_AGGIUDICAZIONI.ID_STATO+" is null )";

	// X-XX: quando e' l'utente deve poter vedere i dati in definizione
	private final String _INDEFINIZIONE = " AND (" + INFO_AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE
			+ " OR " + INFO_AGGIUDICAZIONI.T_ID_STATO + " = " + StatiScheda.CONFERMATO + " OR "
			+ INFO_AGGIUDICAZIONI.T_ID_STATO + " is null )";

	// X-XX: quando e' osservatorio NON deve poter vedere i dati in definizione
//	private final String _CONFERMATI = " AND ("+INFO_AGGIUDICAZIONI.T_ID_STATO+" = "+StatiScheda.CONFERMATO
//		+" OR "+INFO_AGGIUDICAZIONI.T_ID_STATO+" is null )";

//    private final String OLD_DETTAGLI_GARA =
//        "SELECT " + GARA.T_ID_GARA
//        + ", " + GARA.T_OGGETTO + " AS " + GARA.TABLE_NAME + GARA.OGGETTO
//        + ", " + GARA.T_DATA_CREAZIONE
//        + ", " + GARA.T_CF_UTENTE
//        + ", " + GARA.T_ID_STAZIONE_APPALTANTE
//        + ", " + GARA.T_DENOM_STAZIONE_APPALTANTE           
//        + ", " + GARA.T_CF_AMMINISTRAZIONE
//        + ", " + GARA.T_DENOM_AMMINISTRAZIONE
//        + ", " + GARA.T_ID_OSSERVATORIO         
//        + ", " + GARA.T_DATA_CANCELLAZIONE_GARA
//        + ", " + GARA.T_DATA_INIB_PAGAM
//        + ", " + GARA.T_DATA_TERMINE_PAGAMENTO
//        + ", " + GARA.T_DATA_COMUN
//        + ", " + GARA.T_DATA_CONFERMA_GARA
//        + ", " + GARA.T_IMPORTO_SA_GARA
//        + ", " + GARA.T_IMPORTO_GARA
//        + ", " + GARA.T_DATA_PERFEZIONAMENTO_BANDO
//        //gm nuovo campo simog 3.04
//        + ", " + GARA.NUMERO_LOTTI
//        //gm nuovo codice rettifica bando
//        + ", " + GARA.T_ID_PUBBLICAZIONE
//        + ", " + GARA.T_DATA_INIZIO_PUBB
//		+ ", " + PUBBLICAZIONI.T_FLAG_SOSPESO
//		+ ", " + PUBBLICAZIONI.T_TIPO_OPERAZIONE
//        
//        + ", " + LOTTO.T_DATA_INIB_PAGAMENTO
//        + ", " + LOTTO.T_DATA_CANCELLAZIONE_LOTTO
//        + ", " + LOTTO.T_DATA_SCADENZA_PAGAMENTI
//        + (SimogFlags.is3025_RFWEBGL02Active() ? ", " + LOTTO.ORA_SCADENZA   : "") 
//        + ", " + LOTTO.T_DATA_PUBBLICAZIONE
//        + (SimogFlags.is3030_RFWEBGL00Active() ? ", " + LOTTO.T_DATA_SCADENZA_RICHIESTA_INVITO   : "")
//        + (SimogFlags.is3030_RFWEBGL00Active() ? ", " + LOTTO.T_DATA_LETTERA_INVITO  : "")
//        + ", " + LOTTO.T_DATA_COMUNICAZIONE     
//        + ", " + LOTTO.T_ID_TIPOLOGIA
//        + ", " + LOTTO.T_ID_CPV
//        + ", " + buildISNULL(LOTTO.T_ID_LOTTO, new Long(0)) + " AS " + LOTTO.ID_LOTTO
//        + ", " + buildISNULL(LOTTO.T_CIG_CICLE, "")
//        + ", " + buildISNULL(LOTTO.T_CIG, "") + " AS " + LOTTO.CIG
//        + ", " + buildISNULL(LOTTO.T_CIG_KKK, "") + " AS " + LOTTO.CIG_KKK
//        + ", " + buildISNULL(LOTTO.T_OGGETTO, "") + " AS " + LOTTO.TABLE_NAME + LOTTO.OGGETTO
//        + ", " + buildISNULL(LOTTO.T_SOMMA_URGENZA, "N") + " AS " + LOTTO.SOMMA_URGENZA
//        + ", " + buildISNULL(LOTTO.T_IMPORTO_LOTTO, new BigDecimal(0)) + " AS " + LOTTO.IMPORTO_LOTTO
//        + ", " + buildISNULL(LOTTO.T_IMPORTO_SA, new BigDecimal(0)) + " AS " + LOTTO.IMPORTO_SA
//        + ", " + buildISNULL(LOTTO.T_IMPORTO_IMPRESA, new BigDecimal(0)) + " AS " + LOTTO.IMPORTO_IMPRESA
//        + ", " + buildISNULL(LOTTO.T_ID_CATEGORIA_PREVALENTE, new Long(0)) + " AS " + LOTTO.ID_CATEGORIA_PREVALENTE
//        + ", " + buildISNULL(CIG_STORIA.APPLICAZIONE, "") + " AS " + CIG_STORIA.APPLICAZIONE
//        + ", " 
//        + SCELTA_CONTRAENTE.T_DESCRIZIONE
//           + " AS " + SCELTA_CONTRAENTE.TABLE_NAME
//        + ", " + TIPOLOGIA.T_DESCRIZIONE + " AS " + TIPOLOGIA.TABLE_NAME
//        // ottimizzazione CPV+ ", " + CPVEU.T_DESCRIZIONE + " AS " + CPVEU.TABLE_NAME
//        + ", " + REL_LOTTO_CATEGORIA_SCORPORABILE.T_ID_CATEGORIA + " AS " + ParametriServlet.CATEGORIA_SCORPORABILE       
//        + ", " + STATI_SCHEDA.T_DESCRIZIONE
//        + ", " + GARA.ID_STATO
//        + ", " + LOTTO.ID_MOTIVAZIONE
//        + ", " + LOTTO.NOTE_CANC
//        // gm nuovo codice pubblicazione bando 3.0
//        + ", " + LOTTO.T_ID_SCELTA_CONTRAENTE + " AS " +  LOTTO.ID_SCELTA_CONTRAENTE
//        // gm fine nuovo codice pubblicazione bando 3.0
//        + ", " + LOTTO.DATA_CREAZIONE_LOTTO
//		+ ", " + GARA.ID_MOTIVAZIONE_CANC        
//		+ ", " + GARA.TIPO_SCHEDA_GARA
//        + ", " + TIPI_CATEGORIA.T_DESCRIZIONE + " AS " + TIPI_CATEGORIA.TABLE_NAME
//		+ ", " + GARA.T_ID_MODO_GARA
//        + ", " + MODO_INDIZIONE.T_DESCRIZIONE + " AS " + MODO_INDIZIONE.TABLE_NAME
//		+ ", " + GARA.T_ID_MODO_REAL
//        + ", " + MODI_REALIZZAZIONE.T_DESCRIZIONE + " AS " + MODI_REALIZZAZIONE.TABLE_NAME
//		+ ", " + GARA.NOTE_CANC_GARA
//		+ ", " + GARA.CIG_ACC_QUADRO
//		+ ", " + LOTTO.TIPO_CONTRATTO_LOTTO
//		+ ", " + LOTTO.FLAG_ESCLUSO
//		+ ", " + LOTTO.T_ID_ESCLUSIONE
//		/* gm nuovo codice 3.0 */
//		+ ", " + LOTTO.TRIENNIO_ANNO_INIZIO
//		+ ", " + LOTTO.TRIENNIO_ANNO_FINE
//		+ ", " + LOTTO.TRIENNIO_PROGRESSIVO
//		+ ", " + LOTTO.ANNUALE_CUI_MININF
//		
//		// gm nuovo codice pubblicazione bando 3.0
//		+ ", " + LOTTO.LUOGO_ISTAT
//		+ ", " + LOTTO.LUOGO_NUTS
//		+ ", " + LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA
//		// gm fine nuovo codice pubblicazione bando 3.0
//		
//		// PP B302.3.3
//		+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.FLAG_PREVEDE_RIP  : "") 
//		+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.FLAG_RIPETIZIONE  : "")
//		+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.CIG_ORIGINE_RIP   : "") 
//
//        + ", " + ART_ESCLUSIONE.T_DESCRIZIONE + " AS " + ART_ESCLUSIONE.TABLE_NAME
//        + ", " + "G_" + MOTIVI_CANCELLAZIONE.T_DESCRIZIONE + " AS " + "G_" + MOTIVI_CANCELLAZIONE.DESCRIZIONE
//        + ", " + "L_" + MOTIVI_CANCELLAZIONE.T_DESCRIZIONE + " AS " + "L_" + MOTIVI_CANCELLAZIONE.DESCRIZIONE
//		+ ", (select count(1) from info_aggiudicazioni where info_aggiudicazioni.id_lotto = lotto.id_lotto and info_aggiudicazioni.id_stato in (1,2)) AS " + PSBD.HASSCHEDE 
//		+  (SimogFlags.is3031_ESCL_AVCPASS() ? "," + GARA.ESCLUSO_AVCPASS  : "") 
//		
//        + " FROM "
//         
//        + GARA.TABLE_NAME 
//        + " JOIN " + STATI_SCHEDA.TABLE_NAME
//        + " ON " + GARA.T_ID_STATO + "=" + STATI_SCHEDA.T_ID_STATO 
//        
//        + " LEFT JOIN  " + LOTTO.TABLE_NAME
//        + " ON " + GARA.T_ID_GARA + "=" + LOTTO.T_ID_GARA
//        
//        + " LEFT JOIN  "
//        + REL_LOTTO_CATEGORIA_SCORPORABILE.TABLE_NAME
//        + " ON " + REL_LOTTO_CATEGORIA_SCORPORABILE.T_ID_LOTTO + "=" + LOTTO.T_ID_LOTTO   
//
//        + " LEFT JOIN  "
//        + SCELTA_CONTRAENTE.TABLE_NAME
//        + " ON " + SCELTA_CONTRAENTE.T_ID_SCELTA_CONTRAENTE + "=" + LOTTO.T_ID_SCELTA_CONTRAENTE           
//        
//        + " LEFT JOIN  "
//        + TIPI_CATEGORIA.TABLE_NAME
//        + " ON " + TIPI_CATEGORIA.T_ID_TIPO_CATEGORIA + "=" + GARA.T_TIPO_SCHEDA_GARA           
//        
//        + " LEFT JOIN  "
//        + MODO_INDIZIONE.TABLE_NAME
//        + " ON " + MODO_INDIZIONE.T_ID_MODO_GARA + "=" + GARA.T_ID_MODO_GARA           
//        
//        + " LEFT JOIN  "
//        + MODI_REALIZZAZIONE.TABLE_NAME
//        + " ON " + MODI_REALIZZAZIONE.T_ID_MODO_REAL + "=" + GARA.T_ID_MODO_REAL           
//        
//        + " LEFT JOIN  "
//        + ART_ESCLUSIONE.TABLE_NAME
//        + " ON " + ART_ESCLUSIONE.T_ID_ESCLUSIONE + "=" + LOTTO.T_ID_ESCLUSIONE           
//
//        + " LEFT JOIN  "
//        + CIG_STORIA.TABLE_NAME
//        + " ON " + CIG_STORIA.T_CIG_CICLE + "=" + LOTTO.T_CIG_CICLE
//        + " AND " + CIG_STORIA.T_CIG + "=" + LOTTO.T_CIG  
// 
//        + " LEFT JOIN  "
//        + TIPOLOGIA.TABLE_NAME
//        + " ON " + LOTTO.T_ID_TIPOLOGIA + "=" + TIPOLOGIA.T_ID_TIPOLOGIA          
//        
//        + " LEFT JOIN  "
//        + MOTIVI_CANCELLAZIONE.TABLE_NAME + " AS " + "G_" + MOTIVI_CANCELLAZIONE.TABLE_NAME
//        + " ON " + "G_" + MOTIVI_CANCELLAZIONE.T_ID_MOTIVO_CANC + "=" + GARA.ID_MOTIVAZIONE_CANC 
//
//        + " LEFT JOIN  "
//        + MOTIVI_CANCELLAZIONE.TABLE_NAME + " AS " + "L_" + MOTIVI_CANCELLAZIONE.TABLE_NAME
//        + " ON " + "L_" + MOTIVI_CANCELLAZIONE.T_ID_MOTIVO_CANC + "=" + LOTTO.ID_MOTIVAZIONE 
//// ottimizzazione CPV
////        + " LEFT JOIN  "
////        + CPVEU.TABLE_NAME
////        + " ON " + LOTTO.T_ID_CPV + "=" + CPVEU.ID_DIV + " + " + CPVEU.ID_GRP + " + " + CPVEU.ID_CLS + " + " + CPVEU.ID_CTG + " + " + CPVEU.ID_VOX + " +'-'+ " + CPVEU.CHK       
////        + " AND " + CPVEU.VERSIONE + " = " + buildVersCPV( LOTTO.T_ID_CPV ,  GARA.T_DATA_CREAZIONE)  
//        
//         + " LEFT JOIN  "
//        + PUBBLICAZIONI.TABLE_NAME
//        + " ON (" + GARA.T_ID_PUBBLICAZIONE + "=" + PUBBLICAZIONI.T_ID_PUBBLICAZIONE          
//        + " AND " + GARA.T_DATA_INIZIO_PUBB + "=" + PUBBLICAZIONI.T_DATA_INIZIO_PUBB + ")"    
//        
//        + " WHERE " + GARA.T_ID_GARA + "=?" ;

	private final String DETTAGLI_GARA_PAGINAZIONE = "SELECT " + GARA.T_ID_GARA + ", " + GARA.T_OGGETTO + " AS "
			+ GARA.TABLE_NAME + GARA.OGGETTO + ", " + GARA.T_DATA_CREAZIONE + ", " + GARA.T_CF_UTENTE + ", "
			+ GARA.T_ID_STAZIONE_APPALTANTE + ", " + GARA.T_DENOM_STAZIONE_APPALTANTE + ", " + GARA.T_CF_AMMINISTRAZIONE
			+ ", " + GARA.T_DENOM_AMMINISTRAZIONE + ", " + GARA.T_ID_OSSERVATORIO + ", "
			+ GARA.T_DATA_CANCELLAZIONE_GARA + ", " + GARA.T_DATA_INIB_PAGAM + ", " + GARA.T_DATA_TERMINE_PAGAMENTO
			+ ", " + GARA.T_DATA_COMUN + ", " + GARA.T_DATA_CONFERMA_GARA + ", " + GARA.T_IMPORTO_SA_GARA + ", "
			+ GARA.T_IMPORTO_GARA + ", " + GARA.T_DATA_PERFEZIONAMENTO_BANDO
			// gm nuovo campo simog 3.04
			+ ", " + GARA.NUMERO_LOTTI
			// 659 nuovo campo simog
			+ ", " + GARA.DURATA_GIORNI
			// gm nuovo codice rettifica bando
			+ ", " + GARA.T_ID_PUBBLICAZIONE + ", " + GARA.T_DATA_INIZIO_PUBB + ", " + PUBBLICAZIONI.T_FLAG_SOSPESO
			+ ", " + PUBBLICAZIONI.T_TIPO_OPERAZIONE
			//MEV 40610 3.04.9
			+ ", " + LOTTO.T_ID_DEROGA_QUALIFICAZIONE_SA
			//MEV 40610 3.04.9
			+ ", " + LOTTO.T_DATA_INIB_PAGAMENTO + ", " + LOTTO.T_DATA_CANCELLAZIONE_LOTTO + ", "
			+ LOTTO.T_DATA_SCADENZA_PAGAMENTI + (SimogFlags.is3025_RFWEBGL02Active() ? ", " + LOTTO.ORA_SCADENZA : "")
			+ (SimogFlags.is3030_RFWEBGL00Active() ? ", " + LOTTO.DATA_SCADENZA_RICHIESTA_INVITO : "")
			+ (SimogFlags.is3030_RFWEBGL00Active() ? ", " + LOTTO.DATA_LETTERA_INVITO : "") + ", "
			+ LOTTO.T_DATA_PUBBLICAZIONE + ", " + LOTTO.T_DATA_COMUNICAZIONE + ", " + LOTTO.T_ID_TIPOLOGIA + ", "
			+ LOTTO.T_ID_CPV + ", " + buildISNULL(LOTTO.T_ID_LOTTO, new Long(0)) + " AS " + LOTTO.ID_LOTTO + ", "
			+ buildISNULL(LOTTO.T_CIG_CICLE, "") + ", " + buildISNULL(LOTTO.T_CIG, "") + " AS " + LOTTO.CIG + ", "
			+ buildISNULL(LOTTO.T_CIG_KKK, "") + " AS " + LOTTO.CIG_KKK + ", " + buildISNULL(LOTTO.T_OGGETTO, "")
			+ " AS " + LOTTO.TABLE_NAME + LOTTO.OGGETTO + ", " + buildISNULL(LOTTO.T_SOMMA_URGENZA, "N") + " AS "
			+ LOTTO.SOMMA_URGENZA + ", " + buildISNULL(LOTTO.T_IMPORTO_LOTTO, new BigDecimal(0)) + " AS "
			+ LOTTO.IMPORTO_LOTTO + ", " + buildISNULL(LOTTO.T_IMPORTO_SA, new BigDecimal(0)) + " AS "
			+ LOTTO.IMPORTO_SA + ", " + buildISNULL(LOTTO.T_IMPORTO_IMPRESA, new BigDecimal(0)) + " AS "
			+ LOTTO.IMPORTO_IMPRESA + ", " + buildISNULL(LOTTO.T_ID_CATEGORIA_PREVALENTE, new Long(0)) + " AS "
			+ LOTTO.ID_CATEGORIA_PREVALENTE + ", " + buildISNULL(CIG_STORIA.APPLICAZIONE, "") + " AS "
			+ CIG_STORIA.APPLICAZIONE + ", " + SCELTA_CONTRAENTE.T_DESCRIZIONE + " AS " + SCELTA_CONTRAENTE.TABLE_NAME
			// TICKET ALM - 3.04.3 #2846
			+ ", " + MOTIVO_COLLEGAMENTO.T_DESCRIZIONE + " AS " + MOTIVO_COLLEGAMENTO.TABLE_NAME
			// FINE TICKET ALM - 3.04.3 #2846
			+ ", " + TIPOLOGIA.T_DESCRIZIONE + " AS " + TIPOLOGIA.TABLE_NAME + ", " + STATI_SCHEDA.T_DESCRIZIONE + ", "
			+ GARA.ID_STATO + ", " + LOTTO.ID_MOTIVAZIONE + ", " + LOTTO.NOTE_CANC
			// gm nuovo codice pubblicazione bando 3.0
			+ ", " + LOTTO.T_ID_SCELTA_CONTRAENTE + " AS " + LOTTO.ID_SCELTA_CONTRAENTE
			// gm fine nuovo codice pubblicazione bando 3.0
			+ ", " + LOTTO.DATA_CREAZIONE_LOTTO + ", " + GARA.ID_MOTIVAZIONE_CANC + ", " + GARA.TIPO_SCHEDA_GARA + ", "
			+ TIPI_CATEGORIA.T_DESCRIZIONE + " AS " + TIPI_CATEGORIA.TABLE_NAME + ", " + GARA.T_ID_MODO_GARA + ", "
			+ MODO_INDIZIONE.T_DESCRIZIONE + " AS " + MODO_INDIZIONE.TABLE_NAME + ", " + GARA.T_ID_MODO_REAL + ", "
			+ MODI_REALIZZAZIONE.T_DESCRIZIONE + " AS " + MODI_REALIZZAZIONE.TABLE_NAME + ", " + GARA.NOTE_CANC_GARA
			+ ", " + GARA.CIG_ACC_QUADRO + ", " + STRUMENTI_SVOLGIMENTO_PROCEDURE.T_DESCRIZIONE + " AS "
			+ STRUMENTI_SVOLGIMENTO_PROCEDURE.TABLE_NAME // TICKET ALM #664
			+ ", " + ART_ESTREMA_URGENZA_SOMMA_URGENZA.T_DESCRIZIONE + " AS "
			+ ART_ESTREMA_URGENZA_SOMMA_URGENZA.TABLE_NAME // TICKET ALM #3832
			+ ", " + MODALITA_INDIZIONE_ALLEGATO_IX.T_DESCRIZIONE + " AS " + MODALITA_INDIZIONE_ALLEGATO_IX.TABLE_NAME // TICKET
																														// ALM
														//MEV 38205 3.04.8.1																									// #3834
			+ ", " + LOTTO.TIPO_CONTRATTO_LOTTO + ", " + LOTTO.FLAG_USO_METODI_EDILIZIA + ", " + LOTTO.FLAG_ESCLUSO + ", " + LOTTO.T_ID_ESCLUSIONE
			/* gm nuovo codice 3.0 */
			+ ", " + LOTTO.TRIENNIO_ANNO_INIZIO + ", " + LOTTO.TRIENNIO_ANNO_FINE + ", " + LOTTO.TRIENNIO_PROGRESSIVO
			+ ", " + LOTTO.ANNUALE_CUI_MININF

			// gm nuovo codice pubblicazione bando 3.0
			+ ", " + LOTTO.LUOGO_ISTAT + ", " + LOTTO.LUOGO_NUTS + ", " + LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA
			// gm fine nuovo codice pubblicazione bando 3.0

			// TICKET ALM #2845
			+ ", " + LOTTO.FLAG_DL50 + ", " + LOTTO.PRIMA_ANNUALITA
			// FINE TICKET ALM #2845

			// TICKET ALM #3835
			+ ", " + AFFIDAMENTI_RISERVATI.T_DESCRIZIONE + " AS " + AFFIDAMENTI_RISERVATI.TABLE_NAME
			// FINE TICKET ALM #3835

			// TICKET ALM #3836
			+ ", " + LOTTO.FLAG_REGIME
			// + ", " + ART_REGIMI_PARTICOLARI_DI_APPALTO.T_DESCRIZIONE + " AS " +
			// ART_REGIMI_PARTICOLARI_DI_APPALTO.TABLE_NAME
			// FINE TICKET ALM #3836

			// PP B302.3.3
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.FLAG_PREVEDE_RIP : "")
			// Ticket #20058 - 09 - 02 - 21
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.DURATA_RINNOVI_RIPETIZIONI : "")
			//Ticket #20057
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.DURATA_AFFIDAMENTO_IN_GIORNI : "")
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.FLAG_RIPETIZIONE : "")
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.CIG_ORIGINE_RIP : "")

			+ ", " + ART_ESCLUSIONE.T_DESCRIZIONE + " AS " + ART_ESCLUSIONE.TABLE_NAME + ", " + "G_"
			+ MOTIVI_CANCELLAZIONE.T_DESCRIZIONE + " AS " + "G_" + MOTIVI_CANCELLAZIONE.DESCRIZIONE + ", " + "L_"
			+ MOTIVI_CANCELLAZIONE.T_DESCRIZIONE + " AS " + "L_" + MOTIVI_CANCELLAZIONE.DESCRIZIONE
			+ ", (select count(1) from info_aggiudicazioni where info_aggiudicazioni.id_lotto = lotto.id_lotto and info_aggiudicazioni.id_stato in (1,2)) AS "
			+ PSBD.HASSCHEDE

			+ (SimogFlags.is3031_ESCL_AVCPASS() ? ", " + GARA.ESCLUSO_AVCPASS : "")
			+ (SimogFlags.is3031_RFWEBGL02Active() ? ", " + LOTTO.FLAG_CUP : "")
			+ (SimogFlags.isINT85_RFWEBGL01Active() ? "," + GARA.SCELTA_LEGGE89 : "")
			+ (SimogFlags.isINT85_RFWEBGL01Active() ? "," + GARA.TIPOSA_BDNCP : "")
			+ (SimogFlags.isINT87_RFSIMOGWEB01Active() ? "," + GARA.URGENZA_DL133 : "")
			+ (SimogFlags.is30350_RFWEBGL01Active() ? "," + GARA.COD_MOTIVO_EAGG : "")
			+ (SimogFlags.is30350_RFWEBGL01Active() ? "," + EAGG_MOTIVI.T_DESCRIZIONE : "") + " AS "
			+ EAGG_MOTIVI.TABLE_NAME

			+ ", " + FUNZIONI_DELEGATE_GARA.T_FLAG_SA_AGENTE // TICKET ALM #659 - 3.04.4
			+ ", " + FUNZIONI_DELEGATE.T_DESCRIZIONE +" AS "+FUNZIONI_DELEGATE.TABLE_NAME // TICKET ALM #659 - 3.04.4
			+ ", " + FUNZIONI_DELEGATE_GARA.T_CF_AMM_AGENTE // TICKET ALM #659 - 3.04.4
			+ ", " + FUNZIONI_DELEGATE_GARA.T_DEN_AMM_AGENTE // TICKET ALM #659 - 3.04.4
			+ ", " + EAGG_CATEGORIE.T_DESCRIZIONE +  " AS " + EAGG_CATEGORIE.TABLE_NAME
			+", "+GARA.CODICE_AUSA
			+", "+LOTTO.IMPORTO_OPZIONI
			+ " FROM "
//3.04.3.2
			+ GARA.TABLE_NAME + " WITH (NOLOCK) JOIN " + STATI_SCHEDA.TABLE_NAME + " ON " + GARA.T_ID_STATO + "="
			+ STATI_SCHEDA.T_ID_STATO

			+ " LEFT JOIN  " + LOTTO.TABLE_NAME + " ON " + GARA.T_ID_GARA + "=" + LOTTO.T_ID_GARA

//          + " LEFT JOIN  "
//          + REL_LOTTO_CATEGORIA_SCORPORABILE.TABLE_NAME
//          + " ON " + REL_LOTTO_CATEGORIA_SCORPORABILE.T_ID_LOTTO + "=" + LOTTO.T_ID_LOTTO   

			+ " LEFT JOIN  " + SCELTA_CONTRAENTE.TABLE_NAME + " ON " + SCELTA_CONTRAENTE.T_ID_SCELTA_CONTRAENTE + "="
			+ LOTTO.T_ID_SCELTA_CONTRAENTE

			// TICKET ALM - 3.04.3 #2846
			+ " LEFT JOIN  " + MOTIVO_COLLEGAMENTO.TABLE_NAME + " ON " + MOTIVO_COLLEGAMENTO.T_ID_MOTIVO + "="
			+ LOTTO.T_ID_MOTIVO
			// TICKET ALM - 3.04.3 #2846

			+ " LEFT JOIN  " + TIPI_CATEGORIA.TABLE_NAME + " ON " + TIPI_CATEGORIA.T_ID_TIPO_CATEGORIA + "="
			+ GARA.T_TIPO_SCHEDA_GARA

			+ " LEFT JOIN  " + MODO_INDIZIONE.TABLE_NAME + " ON " + MODO_INDIZIONE.T_ID_MODO_GARA + "="
			+ GARA.T_ID_MODO_GARA

			+ " LEFT JOIN  " + MODI_REALIZZAZIONE.TABLE_NAME + " ON " + MODI_REALIZZAZIONE.T_ID_MODO_REAL + "="
			+ GARA.T_ID_MODO_REAL

			// TICKET ALM #664
			+ " LEFT JOIN  " + STRUMENTI_SVOLGIMENTO_PROCEDURE.TABLE_NAME + " ON "
			+ STRUMENTI_SVOLGIMENTO_PROCEDURE.T_ID_SVOLGIMENTO + "=" + GARA.T_ID_SVOLGIMENTO
			// FINE TICKET ALM #664

			// TICKET ALM #3832
			+ " LEFT JOIN  " + ART_ESTREMA_URGENZA_SOMMA_URGENZA.TABLE_NAME + " ON "
			+ ART_ESTREMA_URGENZA_SOMMA_URGENZA.T_ID_ESTREMA_URGENZA + "=" + GARA.T_ID_ESTREMA_URGENZA
			// FINE TICKET ALM #3832

			// TICKET ALM #3834
			+ " LEFT JOIN  " + MODALITA_INDIZIONE_ALLEGATO_IX.TABLE_NAME + " ON "
			+ MODALITA_INDIZIONE_ALLEGATO_IX.T_ID_ALLEGATO_IX + "=" + GARA.T_ID_ALLEGATO_IX
			// FINE TICKET ALM #3834

			// TICKET ALM #3835
			+ " LEFT JOIN  " + AFFIDAMENTI_RISERVATI.TABLE_NAME + " ON " + AFFIDAMENTI_RISERVATI.T_ID_AFF_RISERVATI
			+ "=" + LOTTO.T_ID_AFF_RISERVATI
			// FINE TICKET ALM #3835

			// TICKET ALM #3836
			// + " LEFT JOIN "
			// + ART_REGIMI_PARTICOLARI_DI_APPALTO.TABLE_NAME
			// + " ON " + ART_REGIMI_PARTICOLARI_DI_APPALTO.T_ID_ART_REGIME + "=" +
			// LOTTO.T_ID_ART_REGIME
			// FINE TICKET ALM #3836

			+ " LEFT JOIN  " + ART_ESCLUSIONE.TABLE_NAME + " ON " + ART_ESCLUSIONE.T_ID_ESCLUSIONE + "="
			+ LOTTO.T_ID_ESCLUSIONE

			+ " LEFT JOIN  " + CIG_STORIA.TABLE_NAME + " ON " + CIG_STORIA.T_CIG_CICLE + "=" + LOTTO.T_CIG_CICLE
			+ " AND " + CIG_STORIA.T_CIG + "=" + LOTTO.T_CIG

			+ " LEFT JOIN  " + TIPOLOGIA.TABLE_NAME + " ON " + LOTTO.T_ID_TIPOLOGIA + "=" + TIPOLOGIA.T_ID_TIPOLOGIA

			+ " LEFT JOIN  " + MOTIVI_CANCELLAZIONE.TABLE_NAME + " AS " + "G_" + MOTIVI_CANCELLAZIONE.TABLE_NAME
			+ " ON " + "G_" + MOTIVI_CANCELLAZIONE.T_ID_MOTIVO_CANC + "=" + GARA.ID_MOTIVAZIONE_CANC

			+ " LEFT JOIN  " + MOTIVI_CANCELLAZIONE.TABLE_NAME + " AS " + "L_" + MOTIVI_CANCELLAZIONE.TABLE_NAME
			+ " ON " + "L_" + MOTIVI_CANCELLAZIONE.T_ID_MOTIVO_CANC + "=" + LOTTO.ID_MOTIVAZIONE

			+ " LEFT JOIN  " + PUBBLICAZIONI.TABLE_NAME + " ON (" + GARA.T_ID_PUBBLICAZIONE + "="
			+ PUBBLICAZIONI.T_ID_PUBBLICAZIONE + " AND " + GARA.T_DATA_INIZIO_PUBB + "="
			+ PUBBLICAZIONI.T_DATA_INIZIO_PUBB + ")"

			// TICKET ALM #659 - 3.04.4
			+ " LEFT JOIN " + FUNZIONI_DELEGATE_GARA.TABLE_NAME + " ON " + FUNZIONI_DELEGATE_GARA.T_ID_GARA + "="
			+ GARA.T_ID_GARA
			+ " LEFT JOIN "+FUNZIONI_DELEGATE.TABLE_NAME+" ON "+FUNZIONI_DELEGATE.T_ID_F_DELEGATE+" = "+FUNZIONI_DELEGATE_GARA.T_ID_F_DELEGATE
			// FINE TICKET ALM #659 - 3.04.4

			+ " LEFT JOIN " + EAGG_CATEGORIE.TABLE_NAME+ " ON "+EAGG_CATEGORIE.T_COD_CATEGORIA+" = "+LOTTO.T_COD_CATEGORIA
			
			// is30350_RFWEBGL01Active
			+ (SimogFlags.is30350_RFWEBGL01Active() ? " LEFT JOIN  " : "")
			+ (SimogFlags.is30350_RFWEBGL01Active() ? EAGG_MOTIVI.TABLE_NAME : "")
			+ (SimogFlags.is30350_RFWEBGL01Active() ? " ON " + EAGG_MOTIVI.T_COD_MOTIVO + "=" + GARA.T_COD_MOTIVO_EAGG
					: "")
			+ " WHERE " + GARA.T_ID_GARA + "=?";

	private final String QUERY_CPV_DESC = " SELECT " + CPVEU.DESCRIZIONE + " FROM " + CPVEU.TABLE_NAME + " WHERE ? = "
			+ CPVEU.ID_DIV + " + " + CPVEU.ID_GRP + " + " + CPVEU.ID_CLS + " + " + CPVEU.ID_CTG + " + " + CPVEU.ID_VOX
			+ " +'-'+ " + CPVEU.CHK + " AND " + CPVEU.VERSIONE + " = " + buildVersCPV("?", "?");

	private final String QUERY_SCORP_DESC = " SELECT " + REL_LOTTO_CATEGORIA_SCORPORABILE.T_ID_CATEGORIA + " AS "
			+ ParametriServlet.CATEGORIA_SCORPORABILE + " FROM " + LOTTO.TABLE_NAME + " JOIN  "
			+ REL_LOTTO_CATEGORIA_SCORPORABILE.TABLE_NAME + " ON " + REL_LOTTO_CATEGORIA_SCORPORABILE.T_ID_LOTTO + "="
			+ LOTTO.T_ID_LOTTO + " AND " + LOTTO.T_ID_LOTTO + " = ?";

	private final String LOTTO_ORDER = " ORDER BY " + LOTTO.T_ID_LOTTO; // PP ordinamenti + " DESC";

//	private final String DELETE_GARA_LOTTO_BY_ID =
//		"UPDATE "
//		+ LOTTO.TABLE_NAME
//		+ " SET " 
//		+ LOTTO.DATA_INIB_PAGAMENTO + "=?"
//		+ LOTTO.TABLE_NAME + "." + LOTTO.ID_LOTTO + "=?"
//		+ " AND " + LOTTO.DATA_PUBBLICAZIONE + " IS NULL";

	public final String QUERY_SELECT_PRE_INSERT = " SELECT " + GARA.TABLE_NAME + "." + GARA.ID_GARA + ", "
			+ GARA.T_OGGETTO + ", " + GARA.CF_AMMINISTRAZIONE + ", " + GARA.DENOM_AMMINISTRAZIONE + ", "
			+ GARA.ID_STAZIONE_APPALTANTE + ", " + GARA.DENOM_STAZIONE_APPALTANTE + ", " + GARA.ID_OSSERVATORIO + ", "
			+ LOTTO.T_ID_LOTTO + ", " + LOTTO.T_IMPORTO_LOTTO + ", " + LOTTO.CIG_CICLE + ", " + LOTTO.CIG + ", "
			+ LOTTO.CIG_KKK + ", " + LOTTO.T_ID_CPV + ", " + CPVEU.T_DESCRIZIONE + " AS " + CPVEU.TABLE_NAME + ", "
			+ LOTTO.DATA_PUBBLICAZIONE + ", " + LOTTO.T_ID_SCELTA_CONTRAENTE + " AS " + LOTTO.ID_SCELTA_CONTRAENTE
			+ ", " + SCELTA_CONTRAENTE.T_DESCRIZIONE + " AS " + SCELTA_CONTRAENTE.TABLE_NAME
			// 2846
			+ ", " + MOTIVO_COLLEGAMENTO.T_DESCRIZIONE + " AS " + MOTIVO_COLLEGAMENTO.TABLE_NAME
			// 2846
			+ ", " + LOTTO.T_DATA_CANCELLAZIONE_LOTTO + ", " + LOTTO.T_DATA_CREAZIONE_LOTTO

			+ ", " + GARA.TIPO_SCHEDA_GARA + ", " + GARA.ID_MODO_GARA + ", " + GARA.ID_MODO_REAL + ", "
			+ GARA.CIG_ACC_QUADRO + ", " + GARA.ID_SVOLGIMENTO // TICKET ALM #664
			+ ", " + GARA.ID_ESTREMA_URGENZA // TICKET ALM #3832
			+ ", " + GARA.ID_ALLEGATO_IX // TICKET ALM #3834
			+ ", " + LOTTO.TIPO_CONTRATTO_LOTTO + ", " + LOTTO.FLAG_ESCLUSO
			/* gm nuovo codice 3.0 */
			+ ", " + LOTTO.TRIENNIO_ANNO_INIZIO + ", " + LOTTO.TRIENNIO_ANNO_FINE + ", " + LOTTO.TRIENNIO_PROGRESSIVO
			+ ", " + LOTTO.ANNUALE_CUI_MININF

			// gm nuovo codice pubblicazione bando 3.0
			+ ", " + LOTTO.LUOGO_ISTAT + ", " + LOTTO.LUOGO_NUTS + ", " + LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA
			// gm fine nuovo codice pubblicazione bando 3.0
			+ ", " + LOTTO.ID_ESCLUSIONE

			// TICKET ALM #2845
			+ ", " + LOTTO.FLAG_DL50 + ", " + LOTTO.PRIMA_ANNUALITA
			// FINE TICKET ALM #2845

			// TICKET ALM #3835
			+ ", " + LOTTO.ID_AFF_RISERVATI
			// FINE TICKET ALM #3835

			// TICKET ALM #3836
			+ ", " + LOTTO.FLAG_REGIME
			// + ", "+LOTTO.ID_ART_REGIME
			// FINE TICKET ALM #3836

			// PP B302.3.3
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.FLAG_PREVEDE_RIP : "")
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.FLAG_RIPETIZIONE : "")
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.CIG_ORIGINE_RIP : "")
			+ (SimogFlags.is3031_ESCL_AVCPASS() ? "," + GARA.ESCLUSO_AVCPASS : "")
			+ (SimogFlags.isINT85_RFWEBGL01Active() ? "," + GARA.SCELTA_LEGGE89 : "")
			+ (SimogFlags.isINT85_RFWEBGL01Active() ? "," + GARA.TIPOSA_BDNCP : "")
			+ (SimogFlags.isINT87_RFSIMOGWEB01Active() ? "," + GARA.URGENZA_DL133 : "")
			+ (SimogFlags.is30350_RFWEBGL01Active() ? "," + GARA.COD_MOTIVO_EAGG : "") + ", "
			+ FUNZIONI_DELEGATE_GARA.FLAG_SA_AGENTE // TICKET ALM #659 - 3.04.4
			+ ", " + FUNZIONI_DELEGATE_GARA.ID_F_DELEGATE // TICKET ALM #659 - 3.04.4
			+ ", " + FUNZIONI_DELEGATE_GARA.CF_AMM_AGENTE // TICKET ALM #659 - 3.04.4
			+ ", " + FUNZIONI_DELEGATE_GARA.DEN_AMM_AGENTE // TICKET ALM #659 - 3.04.4
            +" , "+GARA.CODICE_AUSA
            +", "+LOTTO.IMPORTO_OPZIONI
			+ " FROM " + GARA.TABLE_NAME + " JOIN  " + LOTTO.TABLE_NAME + " ON " + GARA.T_ID_GARA + "="
			+ LOTTO.T_ID_GARA + " JOIN  " + SCELTA_CONTRAENTE.TABLE_NAME + " ON "
			+ SCELTA_CONTRAENTE.T_ID_SCELTA_CONTRAENTE + "=" + LOTTO.T_ID_SCELTA_CONTRAENTE
			// 2846
			+ " JOIN  " + MOTIVO_COLLEGAMENTO.TABLE_NAME + " ON " + MOTIVO_COLLEGAMENTO.T_ID_MOTIVO + "="
			+ LOTTO.T_ID_MOTIVO
			// 2846
			+ " LEFT OUTER JOIN  " + CPVEU.TABLE_NAME + " ON " + LOTTO.T_ID_CPV + "=" + CPVEU.ID_DIV + " + "
			+ CPVEU.ID_GRP + " + " + CPVEU.ID_CLS + " + " + CPVEU.ID_CTG + " + " + CPVEU.ID_VOX + " +'-'+ " + CPVEU.CHK
			+ " AND " + CPVEU.VERSIONE + " = " + buildVersCPV(LOTTO.T_ID_CPV, GARA.T_DATA_CREAZIONE)

			// TICKET ALM #659 - 3.04.4
			+ " LEFT JOIN " + FUNZIONI_DELEGATE_GARA.TABLE_NAME + " ON " + FUNZIONI_DELEGATE_GARA.T_ID_GARA + "="
			+ GARA.T_ID_GARA
			// FINE TICKET ALM #659 - 3.04.4

			+ " WHERE " + LOTTO.T_ID_LOTTO + "= ?";

	private final String QUERY_SELECT_ALL_FOR_BEAN = "SELECT * " + " FROM " + GARA.TABLE_NAME + " WHERE " + GARA.ID_GARA
			+ "= ?";

	private final String QUERY_SELECT_ALL_CIG_FOR_BEAN = "SELECT " + LOTTO.CIG + ", " + LOTTO.CIG_KKK + " FROM "
			+ LOTTO.TABLE_NAME + " WHERE " + GARA.ID_GARA + "= ?";

	//Ticket #20055
	private final String QUERY_SELECT_CHECK_RETTIFICA_BY_ID_GARA = "SELECT * " + "FROM " + PUBBLICAZIONI.TABLE_NAME +"," + LOTTO.TABLE_NAME
	+ " WHERE " + PUBBLICAZIONI.ID_PUBBLICAZIONE
	+ " IN " + "(SELECT " + GARA.ID_PUBBLICAZIONE + " FROM " + GARA.TABLE_NAME + " WHERE " + GARA.ID_GARA + "= ?" + " AND " + GARA.ID_PUBBLICAZIONE + " IS NOT NULL )"
	+ " AND " + PUBBLICAZIONI.T_ID_STATO + " != 5"
	+ " AND " + LOTTO.ID_GARA + " = "
	+ " (SELECT " + GARA.ID_GARA + " FROM " + GARA.TABLE_NAME + " WHERE " + GARA.ID_GARA + "= ? )"
	+ " AND " + LOTTO.DATA_CANCELLAZIONE_LOTTO + " IS NULL";
	
	//Ticket 20055
	private final String UPDATE_PUBBLICAZIONI_FLAG_SOSPESO = "UPDATE " + PUBBLICAZIONI.TABLE_NAME 
	+ " SET " + PUBBLICAZIONI.FLAG_SOSPESO + "= ?" 
	+ " WHERE " + PUBBLICAZIONI.ID_PUBBLICAZIONE + "= ?";
	
	// MEV 34188 - 3.04.8.1 fase 2 - controllo che se scelta la voce dalla LISTA MOTIVAZIONI CIG il codice amministrazione va verificato
	//che sia presente nella tabella "LISTA_SOGGETTI_AGGREGATORI"
	private final String QUERY_SELECT_CHECK_SOGG_AGGREG = "SELECT * " + " FROM " + LISTA_SOGGETTI_AGGREGATORI.TABLE_NAME + " WHERE " + LISTA_SOGGETTI_AGGREGATORI.CF_SOGG_AGGREGATORE + "= ?";
			
	
	/***********************************************************************
	 * <b>GaraManager</b><br>
	 * Costruttore
	 * 
	 * @param activeConnection Connection
	 * @param logger           Logger
	 */
	public GaraManager(Connection activeConnection, Logger logger) {
		super(activeConnection, logger);
	}

	/***********************************************************************
	 * Restituisce un vettore contenente gli ID dei Lotti che risultano aver
	 * superato la data di scadenza dei pagamenti
	 * 
	 * @return Vector con id dei lotti
	 * @throws SQLException
	 */
	public Vector hasAwards() throws SQLException {
		// return false;

		String queryhasAwards = "SELECT " + LOTTO.T_ID_LOTTO + " FROM " + INFO_AGGIUDICAZIONI.TABLE_NAME + ","
				+ GARA.TABLE_NAME + "," + LOTTO.TABLE_NAME + " WHERE " + GARA.T_ID_GARA + " = " + LOTTO.T_ID_GARA
				+ " AND " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + " = " + LOTTO.T_ID_LOTTO + " AND "
				+ LOTTO.T_DATA_SCADENZA_PAGAMENTI + " is not null " + " AND " + LOTTO.T_DATA_SCADENZA_PAGAMENTI + " < "
				+ PageHelper.getCurrentDate();
		// + " AND ( " + LOTTO.T_ID_CATEGORIA_PREVALENTE + " = 'FS' OR " +
		// LOTTO.T_ID_CATEGORIA_PREVALENTE + " = 'FB' )";

		logger.debug("Controllo Esistenza aggiudicazioni con la query [" + queryhasAwards + "]");

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = activeConnection.prepareStatement(queryhasAwards);

			rs = pstmt.executeQuery();

			Vector vector = new Vector();
			while (rs.next()) {
				String idlotto = rs.getString(LOTTO.ID_LOTTO);
				vector.add(idlotto);
			}
			return vector;
		} finally {
			close(rs, pstmt);
		}
	}

	/******************************************************************
	 * cerca i CPV dei lotti input tablebean
	 * 
	 * @throws SQLException
	 */

	private TableBean fillCPV(TableBean tb) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TableBean localtb = (TableBean) tb.clone();
		localtb.addColumn(CPVEU.TABLE_NAME);
		try {
			pstmt = activeConnection.prepareStatement(QUERY_CPV_DESC);
			// TableBeanRow row = null;
			for (int i = 0; i < localtb.getTableSize(); i++) {
				// row = tb.getRow(i);
				// solo se esiste il lotto
				if (!"".equals(localtb.getRow(i).getNulledField(LOTTO.CIG))) {
					pstmt.setString(1, localtb.getRow(i).getNulledField(LOTTO.ID_CPV));
					pstmt.setString(2, localtb.getRow(i).getNulledField(LOTTO.ID_CPV));
					pstmt.setString(3, localtb.getRow(i).getNulledField(GARA.DATA_CREAZIONE));
					rs = pstmt.executeQuery();
					if (rs.next())
						localtb.getRow(i).addFieldValue(CPVEU.TABLE_NAME, rs.getString(CPVEU.DESCRIZIONE));
					else // campo vuoto
						localtb.getRow(i).addFieldValue(CPVEU.TABLE_NAME, "");
				} else // campo vuoto
					localtb.getRow(i).addFieldValue(CPVEU.TABLE_NAME, "");
			}

			return localtb;
		} finally {
			close(rs, pstmt);
		}
	}

	/******************************************************************
	 * cerca le scorporabili input tablebean
	 * 
	 * @throws SQLException
	 */

	private TableBean fillScorp(TableBean tb) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TableBean localtb = (TableBean) tb.clone();
		localtb.addColumn(ParametriServlet.CATEGORIA_SCORPORABILE);
		try {
			pstmt = activeConnection.prepareStatement(QUERY_SCORP_DESC);
			// TableBeanRow row = null;
			for (int i = 0; i < localtb.getTableSize(); i++) {
				// solo se esiste il lotto
				if (!"".equals(localtb.getRow(i).getNulledField(LOTTO.CIG))) {
					pstmt.setLong(1, Long.valueOf(localtb.getRow(i).getNulledField(LOTTO.ID_LOTTO)));
					rs = pstmt.executeQuery();
					String result = "";
					while (rs.next()) {
						result = result + rs.getString(ParametriServlet.CATEGORIA_SCORPORABILE) + "~";
					}
					localtb.getRow(i).addFieldValue(ParametriServlet.CATEGORIA_SCORPORABILE, result);

				} else // campo vuoto
					localtb.getRow(i).addFieldValue(ParametriServlet.CATEGORIA_SCORPORABILE, "");
			}

			return localtb;
		} finally {
			close(rs, pstmt);
		}
	}

	/****************************************************************************
	 * Recupera i dettagli della Gara attraverso l'id della Gara.
	 * 
	 * @param idGara String per l'id della gara
	 * @return TableBean contenente i dettagli della gara
	 * @throws SQLException
	 */
	public TableBean getDettagliGaraByIdGara(String idGara) throws SQLException {

		logger.debug("Ricerca dettagli gara [" + idGara + "]");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = activeConnection.prepareStatement(DETTAGLI_GARA_PAGINAZIONE + LOTTO_ORDER);
			pstmt.setLong(1, Long.parseLong(idGara));
			logger.debug("Esecuzione query [" + DETTAGLI_GARA_PAGINAZIONE + LOTTO_ORDER + "]");
			rs = pstmt.executeQuery();
			TableBean result = new TableBean(rs);

			// valorizzo la descrizione del CPV, lasciata in bianco dalla query
			// PP 04.12.2012 solo se ho letto qualcosa!!!
			if (result.getFullSize() > 0)
				result = fillCPV(result);

			return result;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, pstmt);
		}

	}

	/****************************************************************************
	 * Ricerca i dettagli della dara tramite id gara RSSA
	 * 
	 * @param idGara           Stringa
	 * @param listaSAAbilitato Hashtable
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getDettagliGaraByIdGaraRSSA(String idGara, Hashtable listaSAAbilitato) throws SQLException {

		String queryRSSA = DETTAGLI_GARA_PAGINAZIONE + (listaSAAbilitato.size() > 0
				? " AND " + addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys())
				: "") + LOTTO_ORDER;
		logger.debug("Ricerca dettagli gara RSSA [" + idGara + "] eseguita query [" + queryRSSA + "]");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = activeConnection.prepareStatement(queryRSSA);
			int posCounter = 1;

			pstmt.setLong(posCounter++, Long.parseLong(idGara));
			pstmt = fillPstmt(pstmt, posCounter, listaSAAbilitato);
			rs = pstmt.executeQuery();
			TableBean result = new TableBean(rs);

			// valorizzo la descrizione del CPV, lasciata in bianco dalla query
			result = fillCPV(result);

			return result;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, pstmt);
		}
	}

	/****************************************************************************
	 * Ricerca i dettagli della dara con puntatori per paginazione
	 * 
	 * @param idGara           Stringa
	 * @param listaSAAbilitato Hashtable
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getDettagliGaraByIdGaraNew(String idGara, int start, int count, Hashtable listaSAAbilitato)
			throws SQLException {

		String queryRSSA = DETTAGLI_GARA_PAGINAZIONE;

		queryRSSA += (listaSAAbilitato == null || listaSAAbilitato.size() == 0 ? ""
				: " AND " + addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys()));
		queryRSSA += LOTTO_ORDER;

		logger.debug("Ricerca dettagli gara NEW [" + idGara + "] eseguita query [" + queryRSSA + "]");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			System.out.println("TECHNIS getDettagliGaraByIdGaraNew "+queryRSSA);
			pstmt = activeConnection.prepareStatement(queryRSSA, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int posCounter = 1;

			pstmt.setLong(posCounter++, Long.parseLong(idGara));
			if (listaSAAbilitato != null)
				pstmt = fillPstmt(pstmt, posCounter, listaSAAbilitato);

			rs = pstmt.executeQuery();
			TableBean result = new GaraTableBean(rs, start, count, true, logger, false);

			// valorizzo la descrizione del CPV, lasciata in bianco dalla query
			result = fillCPV(result);
			// valorizzo l'array delle scorporabili, lasciate in bianco dalla query
			result = fillScorp(result);

			return (TableBean) result;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, pstmt);
		}
	}

	/**
	 * restituisce l'ID_GARA DELLA GARA APPENA INSERITA
	 */
	/*
	 * DATA_CREAZIONE, OGGETTO, CF_UTENTE, ID_STAZIONE_APPALTANTE,
	 * DENOM_STAZIONE_APPALTANTE, CF_AMMINISTRAZIONE, DENOM_AMMINISTRAZIONE,
	 * ID_OSSERVATORIOIMPORTO_GARA, ID_STATO_GARA
	 * 
	 */
	public static String INSERT_GARA = "INSERT INTO " + GARA.TABLE_NAME + " ( " + GARA.DATA_CREAZIONE + ", "
			+ GARA.OGGETTO + ", " + GARA.CF_UTENTE + ", " + GARA.ID_STAZIONE_APPALTANTE + ", "
			+ GARA.DENOM_STAZIONE_APPALTANTE + ", " + GARA.CF_AMMINISTRAZIONE + ", " + GARA.DENOM_AMMINISTRAZIONE + ", "
			+ GARA.ID_OSSERVATORIO + ", " + GARA.IMPORTO_GARA + ", " + GARA.ID_STATO + ", " + GARA.TIPO_SCHEDA_GARA
			+ ", " + GARA.ID_MODO_GARA + ", " + GARA.ID_MODO_REAL + ", " + GARA.ID_SVOLGIMENTO + ", " + // TICKET ALM
																										// #664
			GARA.ID_ESTREMA_URGENZA + ", " + // TICKET ALM #3832
			GARA.ID_ALLEGATO_IX + ", " + // TICKET ALM #3834
			GARA.CIG_ACC_QUADRO +
			// gm nuovo campo simog 3.04
			", " + GARA.NUMERO_LOTTI +
			// 659 nuovo campo simog
			", " + GARA.DURATA_GIORNI + (SimogFlags.is3031_ESCL_AVCPASS() ? "," + GARA.ESCLUSO_AVCPASS : "")
			+ (SimogFlags.isINT85_RFWEBGL01Active() ? "," + GARA.SCELTA_LEGGE89 : "")
			+ (SimogFlags.isINT85_RFWEBGL01Active() ? "," + GARA.TIPOSA_BDNCP : "")
			+ (SimogFlags.isINT87_RFSIMOGWEB01Active() ? "," + GARA.URGENZA_DL133 : "")
			+ (SimogFlags.is30350_RFWEBGL01Active() ? "," + GARA.COD_MOTIVO_EAGG : "") 
			+ "," + GARA.CODICE_AUSA +
			// 659 upd " ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"+
			" ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
			+ (SimogFlags.is3031_ESCL_AVCPASS() ? ",?" : "") + (SimogFlags.isINT85_RFWEBGL01Active() ? ",?" : "")
			+ (SimogFlags.isINT85_RFWEBGL01Active() ? ",?" : "") + (SimogFlags.isINT87_RFSIMOGWEB01Active() ? ",?" : "")
			+ (SimogFlags.is30350_RFWEBGL01Active() ? ",?" : "") 
			+",?"
			+ ")";

	private static String INSERT_EAGG = "INSERT INTO " + EAGG_GARA_CATEGORIE.TABLE_NAME + " ( "
			+ EAGG_GARA_CATEGORIE.COD_CATEGORIA + ", " + EAGG_GARA_CATEGORIE.COD_GARA + " ) VALUES (?, ?)";

	private static String CANCELLA_EAGG = "DELETE FROM " + EAGG_GARA_CATEGORIE.TABLE_NAME + " WHERE "
			+ EAGG_GARA_CATEGORIE.COD_GARA + " = ?";

	private static String GET_EAGG = "SELECT " + EAGG_GARA_CATEGORIE.COD_CATEGORIA + " FROM "
			+ EAGG_GARA_CATEGORIE.TABLE_NAME + " WHERE " + EAGG_GARA_CATEGORIE.COD_GARA + " = ?";

	/************************************************************************
	 * Crea una nuova gara e restituisce l'ID assegnatole
	 * 
	 * @param nuovaGara Gara contenente i dati da inserire
	 * @return long - retituisce l'ID con cui la Gara &egrave; stata memorizzata
	 * @throws SQLException
	 */
	public long creaNuovaGara(Gara nuovaGara) throws SQLException {
		logger.debug(ObjectIntrospector.propertiesInfo(Gara.class, nuovaGara));
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(createInsertQuery(INSERT_GARA, GARA.ID_GARA));
			logger.debug("Tentativo di creazione nuova gara [" + nuovaGara.getOggetto() + "]");
			int index = 1;
			// CallableStatement insertGaraFunction = activeConnection.prepareCall("{call "
			// + INSERISCI_NUOVA_GARA + "(?, ?, ?, ?, ?, ?, ?, ?)}");
			// TICKET ALM #6754
			// stmt.setString(index++, nuovaGara.getData_creazione());
			stmt.setString(index++, new SimpleDateFormat("yyyyMMdd").format(new java.util.Date()));
			// FINE TICKET ALM #6754
			stmt.setString(index++, PageHelper.replaceWordChars(nuovaGara.getOggetto()));
			stmt.setString(index++, nuovaGara.getCF_UTENTE());
			stmt.setString(index++, nuovaGara.getID_STAZIONE_APPALTANTE());
			stmt.setString(index++, nuovaGara.getDENOM_STAZIONE_APPALTANTE());
			stmt.setString(index++, nuovaGara.getCF_AMMINISTRAZIONE());
			stmt.setString(index++, nuovaGara.getDENOM_AMMINISTRAZIONE());
			stmt.setString(index++, nuovaGara.getID_OSSERVATORIO());
			stmt.setBigDecimal(index++, nuovaGara.getIMPORTO_GARA());
			stmt.setLong(index++, StatiScheda.IN_DEFINIZIONE);

			stmt.setString(index++, nuovaGara.getTIPO_SCHEDA_GARA());

			if (nuovaGara.getID_MODO_GARA() == 0)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, nuovaGara.getID_MODO_GARA());

			if (nuovaGara.getID_MODO_REAL() == 0)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, nuovaGara.getID_MODO_REAL());

			// TICKET ALM #664
			if (nuovaGara.getID_SVOLGIMENTO() == 0)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, nuovaGara.getID_SVOLGIMENTO());
			// FINE TICKET ALM #664

			// TICKET ALM #3832
			if (nuovaGara.getID_ESTREMA_URGENZA() == 0)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, nuovaGara.getID_ESTREMA_URGENZA());
			// FINE TICKET ALM #3832

			// TICKET ALM #3834
			if (nuovaGara.getID_ALLEGATO_IX() == 0)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, nuovaGara.getID_ALLEGATO_IX());
			// FINE TICKET ALM #3834

			stmt.setString(index++, nuovaGara.getCIG_ACC_QUADRO());

			// gm nuovo campo simog 3.04
			if (nuovaGara.getNumeroLotti() == null)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, nuovaGara.getNumeroLotti());

			// TICKET ALM - 3.04.3 #659
			if (nuovaGara.getDurataGiorni() == null) {
				stmt.setNull(index++, java.sql.Types.INTEGER);
			} else {
				stmt.setInt(index++, nuovaGara.getDurataGiorni());
			}
			// FINE TICKET ALM - 3.04.3 #659

				if (nuovaGara.getESCLUSO_AVCPASS() == null || "".equals(nuovaGara.getESCLUSO_AVCPASS().trim()))
					stmt.setNull(index++, java.sql.Types.VARCHAR);
				else
					stmt.setString(index++, nuovaGara.getESCLUSO_AVCPASS());

			// se non è attivo non valorizzo i campi
			if (SimogProperties.getInstance().isINT85Attivo()) {
				stmt.setInt(index++, nuovaGara.getSCELTA_LEGGE89());
				stmt.setObject(index++, nuovaGara.getTIPOSA_BDNCP());
			} else {
				stmt.setNull(index++, java.sql.Types.INTEGER);
				stmt.setNull(index++, java.sql.Types.VARCHAR);
			}

			// INT87
			// se non è attivo non valorizzo il campo
			// if(SimogProperties.getInstance().isINT87Attivo(nuovaGara.getData_creazione())){
			stmt.setObject(index++, nuovaGara.getURGENZA_DL133());
			// }
			// else{
			// stmt.setNull(index++, java.sql.Types.VARCHAR);
			// }

			// is30350_RFWEBGL01Active
			// se non è valorizzato lascio null
			if (nuovaGara.getCOD_MOTIVO_EAGG() > 0) {
				stmt.setInt(index++, nuovaGara.getCOD_MOTIVO_EAGG());
			} else {
				stmt.setNull(index++, java.sql.Types.INTEGER);
			}

			if(nuovaGara.getCodiceAusa()!=null || !"".equals(nuovaGara.getCodiceAusa()))
				stmt.setString(index++, nuovaGara.getCodiceAusa());
			else
				stmt.setNull(index++, java.sql.Types.VARCHAR);
			
			// insertGaraFunction.registerOutParameter (1, Types.BIGINT);
			// logger.debug("Sta per essere Eseguita funzione [" + INSERISCI_NUOVA_GARA + "]
			// - sql - ["+createInsertQuery(INSERT_GARA,GARA.ID_GARA)+"]");

			stmt.execute();
			rs = stmt.getResultSet();
			rs.next();
			long idGara = rs.getLong(GARA.ID_GARA); // FIXME: *!*!*!*! PP non compatibile Mysql : PP per mysql leva
													// rs.next() e metti long idGara =
													// ((com.mysql.jdbc.PreparedStatement) stmt).getLastInsertID();

			if (idGara > 0 && nuovaGara.getCatMerc() != null) {
				for (int i = 0; i < nuovaGara.getCatMerc().size(); i++) {
					stmt.close();
					stmt = activeConnection
							.prepareStatement(createInsertQuery(INSERT_EAGG, EAGG_GARA_CATEGORIE.COD_GARA_CATEG));
					stmt.setLong(1, Long.valueOf((String) nuovaGara.getCatMerc().get(i)));
					stmt.setLong(2, idGara);
					stmt.execute();
				}
			}

			nuovaGara.setIdGara(idGara);

			// close(rs,stmt);
			// insertGaraFunction = null;

			// logger.debug("Eseguita funzione [" + INSERISCI_NUOVA_GARA + "] valore
			// restituito [" + idGara + "]");
			logger.debug("Gara inserita id_gara: [" + idGara + "]");

			return idGara;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, stmt);
		}
	}

	/************************************************************************
	 * Salva gara
	 * 
	 * @param nuovaGara Gara contenente i dati da aggiornare
	 * @return void
	 * @throws SQLException
	 */
	public void saveGara(Gara nuovaGara) throws SQLException {
		// PP lo stato non deve essere toccato !!!
		// nuovaGara.setID_STATO_GARA(StatiScheda.IN_DEFINIZIONE);
		// Update
		updateGara(nuovaGara);
	}

	/**
	 * restituisce l'ID_GARA DELLA GARA APPENA AGGIORNATA
	 */
	public static String UPDATE_GARA = "UPDATE " + GARA.TABLE_NAME + " SET " + GARA.OGGETTO + " = ? ,"
			+ GARA.ID_STAZIONE_APPALTANTE + " = ? ," + GARA.CF_AMMINISTRAZIONE + " = ? ,"
			+ GARA.DENOM_STAZIONE_APPALTANTE + " = ? ," + GARA.DENOM_AMMINISTRAZIONE + " = ? ," + GARA.IMPORTO_GARA
			+ " = ? ," +
			// gm nuovo campo simog 3.04
			GARA.NUMERO_LOTTI + " = ? ," +
			// 659 nuovo campo simog
			GARA.DURATA_GIORNI + " = ? ," + GARA.IMPORTO_SA_GARA + " = ? ," + GARA.DATA_CONFERMA_GARA + " = ? ,"
			+ GARA.DATA_TERMINE_PAGAMENTO + " = ? ," + GARA.DATA_CANCELLAZIONE_GARA + " = ? ," +
// PP No	GARA.DATA_INIB_PAGAM + " = ? ," + 
// PP No	GARA.DATA_COMUN + " = ? ," + 
			GARA.ID_STATO + " = ? ," +

			GARA.TIPO_SCHEDA_GARA + " = ? ," + GARA.ID_MODO_GARA + " = ? ," + GARA.ID_MODO_REAL + " = ?, "
			+ GARA.ID_SVOLGIMENTO + " = ?, " + // TICKET ALM #664
			GARA.ID_ESTREMA_URGENZA + " = ?, " + // TICKET ALM #3832
			GARA.ID_ALLEGATO_IX + " = ?, " + // TICKET ALM #3834
			GARA.CIG_ACC_QUADRO + " = ? "
			+ (SimogFlags.is3031_ESCL_AVCPASS() ? "," + GARA.ESCLUSO_AVCPASS + " = ? " : "")
			+ (SimogFlags.isINT85_RFWEBGL01Active() ? "," + GARA.SCELTA_LEGGE89 + " = ? " : "")
			+ (SimogFlags.isINT85_RFWEBGL01Active() ? "," + GARA.TIPOSA_BDNCP + " = ? " : "")
			+ (SimogFlags.isINT87_RFSIMOGWEB01Active() ? "," + GARA.URGENZA_DL133 + " = ? " : "")
			+ (SimogFlags.is30350_RFWEBGL01Active() ? "," + GARA.COD_MOTIVO_EAGG + " = ? " : "") + " WHERE "
			+ GARA.ID_GARA + " = ? ";

	/************************************************************************
	 * Update di una gara
	 * 
	 * @param nuovaGara Gara contenente i dati da aggiornare
	 * @return void
	 * @throws SQLException
	 */
	public void updateGara(Gara nuovaGara) throws SQLException {
		logger.debug(ObjectIntrospector.propertiesInfo(Gara.class, nuovaGara));
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(UPDATE_GARA);
			logger.debug("Tentativo di update gara [" + nuovaGara.getOggetto() + "]");
			int index = 1;

			stmt.setString(index++, PageHelper.replaceWordChars(nuovaGara.getOggetto()));
			stmt.setString(index++, nuovaGara.getID_STAZIONE_APPALTANTE());
			stmt.setString(index++, nuovaGara.getCF_AMMINISTRAZIONE());
			stmt.setString(index++, nuovaGara.getDENOM_STAZIONE_APPALTANTE());
			stmt.setString(index++, nuovaGara.getDENOM_AMMINISTRAZIONE());
			stmt.setBigDecimal(index++, nuovaGara.getIMPORTO_GARA());

			// gm nuovo campo simog 3.04
			if (nuovaGara.getNumeroLotti() == null)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, nuovaGara.getNumeroLotti());

			// 659 nuovo campo simog
			if (nuovaGara.getDurataGiorni() == null)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, nuovaGara.getDurataGiorni());

			// PP se lo stato è confermato ricalcolo l'importo del contributo
//		if (nuovaGara.getID_STATO_GARA() == StatiScheda.CONFERMATO){
//			BigDecimal[] importi = getImportiByImportoLotto(nuovaGara.getIMPORTO_GARA(), nuovaGara.getDATA_CONFERMA_GARA(), nuovaGara.getID_MODO_REAL());
//			nuovaGara.setIMPORTO_SA_GARA(importi[0]);			
//		}

			stmt.setBigDecimal(index++, nuovaGara.getIMPORTO_SA_GARA());

			stmt.setString(index++, nuovaGara.getDATA_CONFERMA_GARA());
			stmt.setString(index++, nuovaGara.getDATA_TERMINE_PAGAMENTO());
			stmt.setString(index++, nuovaGara.getDATA_CANCELLAZIONE_GARA());
// PP No		stmt.setString(index++, nuovaGara.getDATA_INIB_PAGAM());
// PP No 		stmt.setString(index++, nuovaGara.getDATA_COMUN());
			stmt.setLong(index++, nuovaGara.getID_STATO_GARA());

			stmt.setString(index++, nuovaGara.getTIPO_SCHEDA_GARA());

			if (nuovaGara.getID_MODO_GARA() == 0)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, nuovaGara.getID_MODO_GARA());

			if (nuovaGara.getID_MODO_REAL() == 0)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, nuovaGara.getID_MODO_REAL());

			// TICKET ALM #664
			if (nuovaGara.getID_SVOLGIMENTO() == 0)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, nuovaGara.getID_SVOLGIMENTO());
			// FINE TICKET ALM #664

			// TICKET ALM #3832
			if (nuovaGara.getID_ESTREMA_URGENZA() == 0)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, nuovaGara.getID_ESTREMA_URGENZA());
			// FINE TICKET ALM #3832

			// TICKET ALM #3834
			if (nuovaGara.getID_ALLEGATO_IX() == 0)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, nuovaGara.getID_ALLEGATO_IX());
			// FINE TICKET ALM #3834

			stmt.setString(index++, nuovaGara.getCIG_ACC_QUADRO());

			if (SimogFlags.is3031_ESCL_AVCPASS()) {
				if (nuovaGara.getESCLUSO_AVCPASS() == null || "".equals(nuovaGara.getESCLUSO_AVCPASS().trim()))
					stmt.setNull(index++, java.sql.Types.VARCHAR);
				else
					stmt.setString(index++, nuovaGara.getESCLUSO_AVCPASS());
			}

			if (SimogFlags.isINT85_RFWEBGL01Active()) {
				// se non è attivo e la data è precedente non valorizzo i campi
				if (SimogProperties.getInstance().isINT85Attivo()
						&& SimogProperties.getInstance().isSAINT85(nuovaGara.getData_creazione())) {
					stmt.setInt(index++, nuovaGara.getSCELTA_LEGGE89());
					stmt.setObject(index++, nuovaGara.getTIPOSA_BDNCP());
				} else {
					stmt.setNull(index++, java.sql.Types.INTEGER);
					stmt.setNull(index++, java.sql.Types.VARCHAR);
				}
			}

			// INT87
			if (SimogFlags.isINT87_RFSIMOGWEB01Active()) {
				// se non è valorizzato lascio null
				if (// SimogProperties.getInstance().isINT87Attivo(nuovaGara.getData_creazione())
				Costanti.FLAG_VALORE_SI.equals(nuovaGara.getURGENZA_DL133())
						|| Costanti.FLAG_VALORE_NO.equals(nuovaGara.getURGENZA_DL133())) {
					stmt.setObject(index++, nuovaGara.getURGENZA_DL133());
				} else {
					stmt.setNull(index++, java.sql.Types.VARCHAR);
				}
			}

			// is30350_RFWEBGL01Active
			if (SimogFlags.is30350_RFWEBGL01Active()) {
				// se non è valorizzato lascio null
				if (nuovaGara.getCOD_MOTIVO_EAGG() > 0) {
					stmt.setInt(index++, nuovaGara.getCOD_MOTIVO_EAGG());
				} else {
					stmt.setNull(index++, java.sql.Types.INTEGER);
				}
			}

			stmt.setLong(index++, nuovaGara.getId_Gara());

			int nrow = stmt.executeUpdate();

			if (SimogFlags.is30350_RFWEBGL01Active()) {
				if (nuovaGara.getCatMerc() != null) {
					updateGaraCategorie(nuovaGara.getId_Gara(), nuovaGara.getCatMerc());
				}
			}

			logger.debug("Numero di righe modificate : " + nrow);
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	// INT87
	/**
	 * aggiorna DL133
	 */
	public static String UPDATE_DL133 = "UPDATE " + GARA.TABLE_NAME + " SET " + GARA.URGENZA_DL133 + " = ?, "
			+ GARA.ID_ESTREMA_URGENZA + " = ? " + " WHERE " + GARA.ID_GARA + " = ? ";

	/************************************************************************
	 * Update di una gara
	 * 
	 * @param nuovaGara Gara contenente i dati da aggiornare
	 * @return void
	 * @throws SQLException
	 */
	public void saveDL133(Gara nuovaGara) throws SQLException {
		logger.debug(ObjectIntrospector.propertiesInfo(Gara.class, nuovaGara));
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(UPDATE_DL133);
			logger.debug("Tentativo di UPDATE_DL133 gara [" + nuovaGara.getOggetto() + "]");
			int index = 1;

			stmt.setObject(index++, nuovaGara.getURGENZA_DL133());

			// TICKET ALM - 3.04.2 2905
			if (nuovaGara.getID_ESTREMA_URGENZA() != 0)
				stmt.setInt(index++, nuovaGara.getID_ESTREMA_URGENZA());
			else
				stmt.setNull(index++, Types.BIGINT);
			// FINE TICKET ALM - 3.04.2 2905

			stmt.setLong(index++, nuovaGara.getId_Gara());

			int nrow = stmt.executeUpdate();
			logger.debug("Numero di righe modificate : " + nrow);
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	public void updateGaraPerfezionamento(Gara nuovaGara) throws SQLException {

		logger.debug(ObjectIntrospector.propertiesInfo(Gara.class, nuovaGara));
		int index = 1;
		String updateLogico = "UPDATE " + GARA.TABLE_NAME + " SET " + GARA.DATA_PERFEZIONAMENTO_BANDO + " = ?, "
				+ GARA.DATA_CONFERMA_GARA + " = ?, " + GARA.NUMERO_LOTTI + " = ?, " + GARA.IMPORTO_GARA + " = ?, "
				+ GARA.IMPORTO_SA_GARA + " = ?, " + GARA.ID_STATO + " = ? " + "WHERE " + GARA.ID_GARA + " = ? ";

		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(updateLogico);
			logger.debug("Tentativo di pubblicazione gara [" + nuovaGara.getId_Gara() + "]");
			stmt.setString(index++, nuovaGara.getDATA_PERFEZIONAMENTO_BANDO());
			stmt.setString(index++, nuovaGara.getDATA_CONFERMA_GARA());
// PP	    	stmt.setInt(index++, nuovaGara.getNumeroLotti());
			if (nuovaGara.getNumeroLotti() == null)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, nuovaGara.getNumeroLotti());

			stmt.setBigDecimal(index++, nuovaGara.getIMPORTO_GARA());
			stmt.setBigDecimal(index++, nuovaGara.getIMPORTO_SA_GARA());
			stmt.setLong(index++, StatiScheda.CONFERMATO);
			stmt.setLong(index++, nuovaGara.getId_Gara());
			int nrow = stmt.executeUpdate();
			logger.debug("Numero di righe modificate : " + nrow);
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}
	
	//MAC 42787 3.04.9.2
		public void updateGaraPerfezionamento(Gara nuovaGara, String linkAffidamento) throws SQLException {

			logger.debug(ObjectIntrospector.propertiesInfo(Gara.class, nuovaGara));
			int index = 1;
			String updateLogico = "UPDATE " + GARA.TABLE_NAME + " SET " + GARA.DATA_PERFEZIONAMENTO_BANDO + " = ?, "
					+ GARA.DATA_CONFERMA_GARA + " = ?, " + GARA.NUMERO_LOTTI + " = ?, " + GARA.IMPORTO_GARA + " = ?, "
					+ GARA.IMPORTO_SA_GARA + " = ?, " + GARA.ID_STATO + " = ?, " + GARA.LINK_AFFIDAMENTO_DIRETTO + " = ? " + "WHERE " + GARA.ID_GARA + " = ? ";

			PreparedStatement stmt = null;
			try {
				stmt = activeConnection.prepareStatement(updateLogico);
				logger.debug("Tentativo di pubblicazione gara [" + nuovaGara.getId_Gara() + "]");
				stmt.setString(index++, nuovaGara.getDATA_PERFEZIONAMENTO_BANDO());
				stmt.setString(index++, nuovaGara.getDATA_CONFERMA_GARA());
	// PP	    	stmt.setInt(index++, nuovaGara.getNumeroLotti());
				if (nuovaGara.getNumeroLotti() == null)
					stmt.setNull(index++, java.sql.Types.INTEGER);
				else
					stmt.setInt(index++, nuovaGara.getNumeroLotti());

				stmt.setBigDecimal(index++, nuovaGara.getIMPORTO_GARA());
				stmt.setBigDecimal(index++, nuovaGara.getIMPORTO_SA_GARA());
				stmt.setLong(index++, StatiScheda.CONFERMATO);
				stmt.setString(index++, linkAffidamento != null && !linkAffidamento.equals("") ? linkAffidamento : null); 	//MAC 42787 3.04.9.2
				stmt.setLong(index++, nuovaGara.getId_Gara());
				int nrow = stmt.executeUpdate();
				logger.debug("Numero di righe modificate : " + nrow);
			} finally {
				if (stmt != null)
					stmt.close();
			}
		}//FINE MAC

	public void updateGaraPubblicazione(Gara nuovaGara, long idPubblicazione, Timestamp dataPubblicazione)
			throws SQLException {

		logger.debug(ObjectIntrospector.propertiesInfo(Gara.class, nuovaGara));
		int index = 1;
		String updateLogico = "UPDATE " + GARA.TABLE_NAME + " SET " + GARA.DATA_PERFEZIONAMENTO_BANDO + " = ?, "
				+ GARA.DATA_CONFERMA_GARA + " = ?, " + GARA.ID_PUBBLICAZIONE + " = ?, " + GARA.DATA_INIZIO_PUBB
				+ " = ?, " + GARA.NUMERO_LOTTI + " = ?, " + GARA.IMPORTO_GARA + " = ?, " + GARA.IMPORTO_SA_GARA
				+ " = ?, " + GARA.ID_STATO + " = ? " + "WHERE " + GARA.ID_GARA + " = ? ";

		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(updateLogico);
			logger.debug("Tentativo di pubblicazione gara [" + nuovaGara.getOggetto() + "]");
			stmt.setString(index++, nuovaGara.getDATA_PERFEZIONAMENTO_BANDO());
			stmt.setString(index++, nuovaGara.getDATA_CONFERMA_GARA());
			stmt.setLong(index++, idPubblicazione);
			stmt.setTimestamp(index++, dataPubblicazione);
// PP	    	stmt.setInt(index++, nuovaGara.getNumeroLotti());
			if (nuovaGara.getNumeroLotti() == null)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, nuovaGara.getNumeroLotti());

			stmt.setBigDecimal(index++, nuovaGara.getIMPORTO_GARA());
			stmt.setBigDecimal(index++, nuovaGara.getIMPORTO_SA_GARA());
			stmt.setLong(index++, StatiScheda.CONFERMATO);
			stmt.setLong(index++, nuovaGara.getId_Gara());
			int nrow = stmt.executeUpdate();
			logger.debug("Numero di righe modificate : " + nrow);
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}
	
	//MAC 42787 3.04.9.2
	
		public void updateGaraPubblicazione(Gara nuovaGara, long idPubblicazione, Timestamp dataPubblicazione, String linkAffidamento) 
				throws SQLException {

			logger.debug(ObjectIntrospector.propertiesInfo(Gara.class, nuovaGara));
			int index = 1;
			String updateLogico = "UPDATE " + GARA.TABLE_NAME + " SET " + GARA.DATA_PERFEZIONAMENTO_BANDO + " = ?, "
					+ GARA.DATA_CONFERMA_GARA + " = ?, " + GARA.ID_PUBBLICAZIONE + " = ?, " + GARA.DATA_INIZIO_PUBB
					+ " = ?, " + GARA.NUMERO_LOTTI + " = ?, " + GARA.IMPORTO_GARA + " = ?, " + GARA.IMPORTO_SA_GARA
					+ " = ?, " + GARA.ID_STATO + " = ?, " + GARA.LINK_AFFIDAMENTO_DIRETTO + " = ? " + "WHERE " + GARA.ID_GARA + " = ? ";

			PreparedStatement stmt = null;
			try {
				stmt = activeConnection.prepareStatement(updateLogico);
				logger.debug("Tentativo di pubblicazione gara [" + nuovaGara.getOggetto() + "]");
				stmt.setString(index++, nuovaGara.getDATA_PERFEZIONAMENTO_BANDO());
				stmt.setString(index++, nuovaGara.getDATA_CONFERMA_GARA());
				stmt.setLong(index++, idPubblicazione);
				stmt.setTimestamp(index++, dataPubblicazione);
	// PP	    	stmt.setInt(index++, nuovaGara.getNumeroLotti());
				if (nuovaGara.getNumeroLotti() == null)
					stmt.setNull(index++, java.sql.Types.INTEGER);
				else
					stmt.setInt(index++, nuovaGara.getNumeroLotti());

				stmt.setBigDecimal(index++, nuovaGara.getIMPORTO_GARA());
				stmt.setBigDecimal(index++, nuovaGara.getIMPORTO_SA_GARA());
				stmt.setLong(index++, StatiScheda.CONFERMATO);
				stmt.setString(index++, linkAffidamento != null && !linkAffidamento.equals("") ? linkAffidamento : null); 	//MAC 42787 3.04.9.2
				stmt.setLong(index++, nuovaGara.getId_Gara());
				int nrow = stmt.executeUpdate();
				logger.debug("Numero di righe modificate : " + nrow);
			} finally {
				if (stmt != null)
					stmt.close();
			}
		}

	public void updateGaraPresaInCarico(Gara nuovaGara) throws SQLException {

		logger.debug(ObjectIntrospector.propertiesInfo(Gara.class, nuovaGara));
		int index = 1;
		String updateLogico = "UPDATE " + GARA.TABLE_NAME + " SET " + GARA.CF_UTENTE + " = ?, "
				+ GARA.PROVV_PRESA_CARICO + " = ? " + "WHERE " + GARA.ID_GARA + " = ? ";

		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(updateLogico);
			logger.debug("Tentativo di presa in carico gara [" + nuovaGara.getId_Gara() + "]");
			stmt.setString(index++, nuovaGara.getCF_UTENTE());
			stmt.setString(index++, nuovaGara.getPROVV_PRESA_CARICO());
			stmt.setLong(index++, nuovaGara.getId_Gara());
			int nrow = stmt.executeUpdate();
			logger.debug("Numero di righe modificate : " + nrow);
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	/************************************************************************
	 * Cancella gara
	 * 
	 * @param dataCorrente String La data corrente
	 * @param idGara       String id della gara da cancellare
	 * @param pubblicato   boolean
	 * @return int - numero di righe aggiornate
	 * @throws SQLException
	 */
	public int cancelGara(String dataCorrente, String idGara, boolean pubblicato, String id_motivazione, String note)
			throws SQLException {

		String campoDaModificare = pubblicato ? GARA.DATA_INIB_PAGAM : GARA.DATA_CANCELLAZIONE_GARA;

		String cancellazioneLogica = "UPDATE " + GARA.TABLE_NAME + " SET " + campoDaModificare + " = ? ,"
				+ GARA.ID_STATO + " = ?,  " + GARA.ID_MOTIVAZIONE_CANC + " = ?,  " + GARA.NOTE_CANC_GARA + " = ?  "
				+ "WHERE " + GARA.ID_GARA + " = ? ";

		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(cancellazioneLogica);
			logger.debug("Cancellazione gara - esecuzione query [" + cancellazioneLogica + "]");
			stmt.setString(1, dataCorrente);
			stmt.setObject(2, StatiScheda.ANNULLATO_STRING);
			stmt.setObject(3, id_motivazione);
			stmt.setObject(4, note);
			stmt.setLong(5, Long.parseLong(idGara));
			int nrow = stmt.executeUpdate();
			return nrow;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(null, stmt);
		}
	}

	/************************************************************************
	 * ripristina gara gara
	 * 
	 * @param idGara String id della gara da cancellare
	 * @return int - numero di righe aggiornate
	 * @throws SQLException
	 */
	public int ripristinaGara(String idGara, boolean confermata) throws SQLException {

		String ripristinaGara = "UPDATE " + GARA.TABLE_NAME + " SET " + GARA.DATA_INIB_PAGAM + " = null ,"
				+ GARA.DATA_CANCELLAZIONE_GARA + " = null ," + GARA.ID_STATO + "='"
				+ (confermata ? StatiScheda.CONFERMATO_STRING : StatiScheda.IN_DEFINIZIONE_STRING) + "',  "
				+ GARA.ID_MOTIVAZIONE_CANC + " = null,  " + GARA.NOTE_CANC_GARA + " = null  " + "WHERE " + GARA.ID_GARA
				+ " = ? ";

		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(ripristinaGara);
			stmt.setLong(1, Long.parseLong(idGara));
			int nrow = stmt.executeUpdate();
			return nrow;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(null, stmt);
		}
	}

	private void fillGara(Gara g, ResultSet rs) throws Exception {
		Pattern INVALID_XML_CHARS = Pattern.compile("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\uD800\uDC00-\uDBFF\uDFFF]");
		
		g.setData_creazione(rs.getString(GARA.DATA_CREAZIONE));
		g.setIdGara(rs.getLong(GARA.ID_GARA));
		g.setCF_UTENTE(rs.getString(GARA.CF_UTENTE));
		g.setOggetto(INVALID_XML_CHARS.matcher(rs.getString(GARA.OGGETTO)).replaceAll(" "));
		g.setID_STAZIONE_APPALTANTE(rs.getString(GARA.ID_STAZIONE_APPALTANTE));
		g.setCF_AMMINISTRAZIONE(rs.getString(GARA.CF_AMMINISTRAZIONE));
		g.setDENOM_AMMINISTRAZIONE(rs.getString(GARA.DENOM_AMMINISTRAZIONE));
		g.setDENOM_STAZIONE_APPALTANTE(rs.getString(GARA.DENOM_STAZIONE_APPALTANTE));
		g.setID_OSSERVATORIO(rs.getString(GARA.ID_OSSERVATORIO));
		g.setIMPORTO_GARA(rs.getBigDecimal(GARA.IMPORTO_GARA));
		// X-XX: VL - [18112008] - new entry [nuova normativa]
		g.setID_STATO_GARA(rs.getLong(GARA.ID_STATO));
		g.setDATA_COMUN(rs.getString(GARA.DATA_COMUN));
		g.setIMPORTO_SA_GARA(rs.getBigDecimal(GARA.IMPORTO_SA_GARA));
		g.setDATA_INIB_PAGAM(rs.getString(GARA.DATA_INIB_PAGAM));
		g.setDATA_TERMINE_PAGAMENTO(rs.getString(GARA.DATA_TERMINE_PAGAMENTO));
		g.setDATA_CANCELLAZIONE_GARA(rs.getString(GARA.DATA_CANCELLAZIONE_GARA));
		g.setDATA_CONFERMA_GARA(rs.getString(GARA.DATA_CONFERMA_GARA));

		g.setTIPO_SCHEDA_GARA(rs.getString(GARA.TIPO_SCHEDA_GARA));
		g.setID_MODO_GARA(rs.getInt(GARA.ID_MODO_GARA));
		g.setID_MODO_REAL(rs.getInt(GARA.ID_MODO_REAL));
		g.setID_MOTIVAZIONE_CANC(rs.getInt(GARA.ID_MOTIVAZIONE_CANC));
		g.setNOTE_CANC_GARA(rs.getString(GARA.NOTE_CANC_GARA));
		g.setCIG_ACC_QUADRO(rs.getString(GARA.CIG_ACC_QUADRO));
		g.setID_SVOLGIMENTO(rs.getInt(GARA.ID_SVOLGIMENTO)); // TICKET ALM #664
		g.setID_ESTREMA_URGENZA(rs.getInt(GARA.ID_ESTREMA_URGENZA)); // TICKET ALM #3832
		g.setID_ALLEGATO_IX(rs.getInt(GARA.ID_ALLEGATO_IX));// TICKET ALM #3834

		// PP - BANDI GARA
		g.setDATA_PERFEZIONAMENTO_BANDO(rs.getString(GARA.DATA_PERFEZIONAMENTO_BANDO));
		g.setIdPubblicazione(rs.getLong(GARA.ID_PUBBLICAZIONE));
		g.setDataInizioPubblicazione(rs.getTimestamp(GARA.DATA_INIZIO_PUBB));
		// gm nuovo codice simog 3.04
		// se faccio solo il rs.getInt restituisce 0 se non trovato e non va bene
		if (rs.getObject(GARA.NUMERO_LOTTI) != null)
			g.setNumeroLotti(rs.getInt(GARA.NUMERO_LOTTI));
		else
			g.setNumeroLotti(null);

		// TICKET ALM - 3.04.3 #659 nuovo codice simog
		// se faccio solo il rs.getInt restituisce 0 se non trovato e non va bene
		if (rs.getObject(GARA.DURATA_GIORNI) != null)
			g.setDurataGiorni(rs.getInt(GARA.DURATA_GIORNI));
		else
			g.setDurataGiorni(null);

		// pp organi costituzionali, verifico se l'amministrazione della gara lo era al
		// momento della creazione della gara
		g.setOrganoCost(isOrganoCost(g.getCF_AMMINISTRAZIONE(), g.getData_creazione()));

		// is3031_ESCL_AVCPASS
		if (SimogFlags.is3031_ESCL_AVCPASS())
			g.setESCLUSO_AVCPASS(rs.getString(GARA.ESCLUSO_AVCPASS));

		// INT85
		if (SimogFlags.isINT85_RFWEBGL01Active()) {
			g.setSCELTA_LEGGE89(rs.getInt(GARA.SCELTA_LEGGE89));
			g.setTIPOSA_BDNCP(rs.getString(GARA.TIPOSA_BDNCP));
		}

		// INT87
		if (SimogFlags.isINT87_RFSIMOGWEB01Active()) {
			g.setURGENZA_DL133(rs.getString(GARA.URGENZA_DL133));
		}

		// is30350_RFWEBGL01Active
		if (SimogFlags.is30350_RFWEBGL01Active()) {
			g.setCOD_MOTIVO_EAGG(rs.getInt(GARA.COD_MOTIVO_EAGG));
		}
		
		if(rs.getString(GARA.CODICE_AUSA)!=null) 
			g.setCodiceAusa(rs.getString(GARA.CODICE_AUSA));
		
		   //MAC 42787  3.04.9.2 togliere commento 
		if(rs.getString(GARA.LINK_AFFIDAMENTO_DIRETTO)!=null) {
			g.setLINK_AFFIDAMENTO_DIRETTO(rs.getString(GARA.LINK_AFFIDAMENTO_DIRETTO));
		}else {
			g.setLINK_AFFIDAMENTO_DIRETTO("");
		}
			

	}

	/********************************************************************************************
	 * Recupera una gara in base al suo id
	 * 
	 * @param idGara : Long
	 * @return Gara
	 * @throws SQLException
	 * @throws Exception
	 */

	public Gara getGara(Long idGara) throws SQLException, Exception {
		logger.debug("getGara(" + idGara +")");
		logger.info("getGara(" + idGara +")");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = activeConnection.prepareStatement(QUERY_SELECT_ALL_FOR_BEAN);
			pstmt.setLong(1, idGara);
			rs = pstmt.executeQuery();
			Gara g = null;
			int counter = 0;

			while (rs.next()) {
				counter++;

				g = new Gara();
				fillGara(g, rs);

				// TICKET ALM #659 - 3.04.4
				// Recupera dati delega gara
				g = this.selectFunzioniDelegateGara(g);


					g.setCatMerc(getCategorie(idGara));
				}

			logger.debug("getGara(" + idGara +") - > QUERY_SELECT_ALL_FOR_BEAN");
			logger.info("getGara(" + idGara +") - > QUERY_SELECT_ALL_FOR_BEAN");
			return g;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, pstmt);
		}
	}

	/********************************************************************************************
	 * Recupera una gara in base al suo id, controllamdo se l'utente è abilitato
	 * alla gara
	 * 
	 * @param idGara           : Long
	 * @param listaSAAbilitato : Hashtable
	 * @return Gara
	 * @throws SQLException
	 * @throws Exception
	 */
	public Gara getGara(Long idGara, Hashtable listaSAAbilitato) throws SQLException, Exception {
		logger.debug("getGara(" + idGara + ", " + listaSAAbilitato +")");
		logger.info("getGara(" + idGara + ", " + listaSAAbilitato +")");
		Gara g = getGara(idGara);
		boolean ok = true;

		if (g != null && listaSAAbilitato != null && listaSAAbilitato.size() > 0) {
			ok = false;
			for (Enumeration e = listaSAAbilitato.keys(); e.hasMoreElements();) {
				String currentElement = (String) e.nextElement();
				logger.debug("currentElement" + currentElement );
				logger.info("currentElement" + currentElement );
				logger.debug("g.getID_STAZIONE_APPALTANTE()" + g.getID_STAZIONE_APPALTANTE() );
				logger.info("g.getID_STAZIONE_APPALTANTE()" + g.getID_STAZIONE_APPALTANTE() );
				if (currentElement.equals(g.getID_STAZIONE_APPALTANTE())) {
					ok = true;
					break;
				}
			}
		}

		return ok ? g : null;
	}

	/********************************************************************************************
	 * Recupera tutti i cig dei lotti di una gara in base al suo id
	 * 
	 * @param idGara : Long
	 * @return List
	 * @throws SQLException
	 * @throws Exception
	 */

	public List<String> getAllCigGara(Long idGara) throws SQLException, Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = activeConnection.prepareStatement(QUERY_SELECT_ALL_CIG_FOR_BEAN);
			pstmt.setLong(1, idGara);
			rs = pstmt.executeQuery();
			List<String> l = new ArrayList<String>();
			String cig = null;
			int counter = 0;
			while (rs.next()) {
				counter++;
				cig = (rs.getString(LOTTO.CIG)).concat(rs.getString(LOTTO.CIG_KKK));
				l.add(cig);
			}
			return l;
		} catch (Exception e) {
			throw e;
		} finally {
			close(rs, pstmt);
		}
	}
	
	public TableBean getGaraDelegataList(String oggettoGara, String oggettoLotto, String cig, String numGara,
			Hashtable listaSAAbilitato, String dataScadenza_a, String dataScadenza_da, String dataPubblicazione_a,
			String dataPubblicazione_da, int startRow, int count, boolean isRup_Cs, String richiestaAnnullamento,
			String admin_or, String richiestaAggiudicate, String cfRUP, String minSoglia, String maxSoglia,
			String cfAmm, String cfCreata,Hashtable ammUtente) throws SQLException {
		
		logger.debug("getGaraDelegataList");
		logger.info("getGaraDelegataList");
		logger.debug("parametri-------------");
		logger.info("parametri-------------");
		logger.debug("oggettoGara " + oggettoGara );
		logger.info("oggettoGara " + oggettoGara );
		logger.debug("cig " + cig );
		logger.info("cig " + cig );
		logger.debug("numGara " + numGara );
		logger.info("numGara " + numGara );
		logger.debug("listaSAAbilitato " + listaSAAbilitato );
		logger.info("listaSAAbilitato " + listaSAAbilitato );
		logger.debug("dataScadenza_a " + dataScadenza_a );
		logger.info("dataScadenza_a " + dataScadenza_a );
		logger.debug("dataScadenza_da " + dataScadenza_da );
		logger.info("dataScadenza_da " + dataScadenza_da );
		logger.debug("dataPubblicazione_a " + dataPubblicazione_a );
		logger.info("dataPubblicazione_a " + dataPubblicazione_a );
		logger.debug("dataPubblicazione_da " + dataPubblicazione_da );
		logger.info("dataPubblicazione_da " + dataPubblicazione_da );
		logger.debug("startRow " + startRow );
		logger.info("startRow " + startRow );
		logger.debug("count " + count );
		logger.info("count " + count );
		logger.debug("isRup_Cs " + isRup_Cs );
		logger.info("isRup_Cs " + isRup_Cs );
		logger.debug("richiestaAnnullamento " + richiestaAnnullamento );
		logger.info("richiestaAnnullamento " + richiestaAnnullamento );
		logger.debug("admin_or " + admin_or );
		logger.info("admin_or " + admin_or );
		logger.debug("richiestaAggiudicate " + richiestaAggiudicate );
		logger.info("richiestaAggiudicate " + richiestaAggiudicate );
		logger.debug("cfRUP " + cfRUP );
		logger.info("cfRUP " + cfRUP );
		logger.debug("minSoglia " + minSoglia );
		logger.info("minSoglia " + minSoglia );
		logger.debug("maxSoglia " + maxSoglia );
		logger.info("maxSoglia " + maxSoglia );
		logger.debug("cfAmm " + cfAmm );
		logger.info("cfAmm " + cfAmm );
		logger.debug("cfCreata " + cfCreata );
		logger.info("cfCreata " + cfCreata );
		logger.debug("ammUtente " + ammUtente );
		logger.info("ammUtente " + ammUtente );
		

		String whereCond = " where 1=1 ";
		// String selectRicerca = "SELECT " + BASE_SELECT_INFO_GARA_LOTTO +
		// BASE_SELECT_INFO_GARA_LOTTO_FROM;
		String selectRicerca = BASE_SELECT_INFO_GARA_LOTTO + BASE_SELECT_INFO_GARA_LOTTO_FROM;
		if (isRup_Cs) {
			// selectRicerca = "SELECT distinct " + QUERY_RUP + QUERY_RUP_FROM;
			selectRicerca = QUERY_RUP + QUERY_RUP_FROM;

// PP tutti vedono tutto			
			selectRicerca += _INDEFINIZIONE;

//			// il profilo non è osservatorio regionale o centrale, ma solo rup
//			if(admin_or == null || ProfiloEnum.REGIONE_ZERO.equals(admin_or) ){
//			//AGGIUNTA CONDIZIONE IN DEFINIZIONE CHE E' STATA STACCATA DA QUERY_RUP_FROM
//				selectRicerca += _INDEFINIZIONE + " AND " + LOTTO.DATA_SCADENZA_PAGAMENTI + " is not null "  
//						  +  " AND "+LOTTO.DATA_SCADENZA_PAGAMENTI+" < '"+ PageHelper.getCurrentDate()+"'" ;
//			//ALTRIMENTI CONCATENO PER AVERE I DATI CONFERMATI/in definizione senza limitazioni
//			}else if(ProfiloEnum.REGIONE_099.equals(admin_or) || ProfiloEnum.REGIONE_999.equals(admin_or)){
//				selectRicerca += _INDEFINIZIONE;
//			//ALTRIMENTI CONCATENO PER AVERE I DATI SOLO CONFERMATI
//			}else{
//				selectRicerca += _INDEFINIZIONE;  // PP richiesta Obino 12.02.09 anche OSSR vede tutto selectRicerca += _CONFERMATI;
//			}
		}

		StringTokenizer tokenOggettoGara = null;
		String condizioniOggettoGara = null;

		if (oggettoGara != null && oggettoGara.trim().length() > 0) {
			tokenOggettoGara = new StringTokenizer(oggettoGara);
			String currToken = getCleanToken(tokenOggettoGara.nextToken());
			condizioniOggettoGara = GARA.T_OGGETTO + " LIKE '%" + currToken + "%'";
//			String pesatura = " sign(charindex('"+ currToken+ "',lower("+ GARA.T_OGGETTO + "))) ";

			while (tokenOggettoGara.hasMoreElements()) {
				currToken = getCleanToken(tokenOggettoGara.nextToken());
				condizioniOggettoGara += " AND " + GARA.T_OGGETTO + " LIKE '%" + currToken + "%'"; // UN sostituiti gli
																									// OR con AND
																									// (16-02-09)
//				pesatura += " + sign(charindex('"+ currToken+ "',lower("+ GARA.T_OGGETTO + "))) ";
			}
			whereCond += " AND ( " + condizioniOggettoGara + ")";
			logger.debug("condizioniOggettoGara " + condizioniOggettoGara );
			logger.info("condizioniOggettoGara " + condizioniOggettoGara );
		}

		StringTokenizer tokenOggettoLotto = null;

		String condizioniOggettoLotto = null;

		if (oggettoLotto != null && oggettoLotto.trim().length() > 0) {
			tokenOggettoLotto = new StringTokenizer(oggettoLotto);
			condizioniOggettoLotto = LOTTO.T_OGGETTO + " LIKE '%" + getCleanToken(tokenOggettoLotto.nextToken()) + "%'";
			while (tokenOggettoLotto.hasMoreElements()) {
				condizioniOggettoLotto += " AND " + LOTTO.T_OGGETTO + " LIKE '%"
						+ getCleanToken(tokenOggettoLotto.nextToken()) + "%'"; // UN sostituiti gli OR con AND
																				// (16-02-09)
			}
			whereCond += " AND ( " + condizioniOggettoLotto + ")";
			logger.debug("condizioniOggettoLotto " + condizioniOggettoLotto );
			logger.info("condizioniOggettoLotto " + condizioniOggettoLotto );
		}

		if (cig != null && cig.trim().length() > 0) {
			whereCond += " AND " + getQueryConditionByCIGSommaUrgenza(cig);
			logger.debug("getQueryConditionByCIGSommaUrgenza " + getQueryConditionByCIGSommaUrgenza(cig) );
			logger.info("getQueryConditionByCIGSommaUrgenza " + getQueryConditionByCIGSommaUrgenza(cig) );
		}

		if (numGara != null && numGara.trim().length() > 0) {
			whereCond += " AND " + GARA.T_ID_GARA + " = " + numGara;
		}

		if (cfRUP != null && cfRUP.trim().length() > 0) {
//	PP 20.11.2015 condizione errata per OSSN		whereCond += " AND "  + INFO_AGGIUDICAZIONI.CF_RUP + " = '" + cfRUP + "'";
			whereCond += " AND (" + GARA.T_CF_UTENTE + " = '" + cfRUP + "'" + " OR " + INFO_AGGIUDICAZIONI.T_CF_RUP
					+ "= '" + cfRUP + "')";
		}

		if (cfAmm != null && cfAmm.trim().length() > 0) {
			whereCond += " AND " + GARA.T_CF_AMMINISTRAZIONE + " = '" + cfAmm + "'";
		}

		if (cfCreata != null && cfCreata.trim().length() > 0) {
			whereCond += " AND (" + GARA.T_CF_UTENTE + " = '" + cfCreata + "'" + " OR " + INFO_AGGIUDICAZIONI.T_CF_RUP
					+ "= '" + cfCreata + "')";
		}
		if (dataScadenza_a != null) {
			whereCond += " AND " + LOTTO.T_DATA_SCADENZA_PAGAMENTI + " <= '" + dataScadenza_a + "'";
		}
		if (dataScadenza_da != null) {
			whereCond += " AND " + LOTTO.T_DATA_SCADENZA_PAGAMENTI + " >= '" + dataScadenza_da + "'";
		}

		if (dataPubblicazione_a != null) {
			whereCond += " AND " + LOTTO.T_DATA_PUBBLICAZIONE + " <= '" + dataPubblicazione_a + "'";
		}
		if (dataPubblicazione_da != null) {
			whereCond += " AND " + LOTTO.T_DATA_PUBBLICAZIONE + " >= '" + dataPubblicazione_da + "'";
		}
		if (listaSAAbilitato.size() > 0) {
			whereCond += " AND " + addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys());
			logger.debug("addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys()) " + addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys()) );
			logger.info("addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys()) " + addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys()));
		}

		if (richiestaAnnullamento != null) {
			whereCond += " AND " + LOTTO.T_ID_LOTTO + ("N".equals(richiestaAnnullamento) ? " NOT IN " : " IN ")
					+ "( SELECT " + RICHIESTA_ANNULLAMENTO.ID_LOTTO + " FROM " + RICHIESTA_ANNULLAMENTO.TABLE_NAME
					+ " WHERE " + RICHIESTA_ANNULLAMENTO.T_DATA_FINE + " IS NULL )";

		}
		// solo con aggiudicazioni o senza aggiudicazioni
		if (richiestaAggiudicate != null) {
			// whereCond += " AND " + INFO_AGGIUDICAZIONI.T_ID_INFO +
			// ("S".equals(richiestaAggiudicate) ? " IS NULL " : " IS NOT NULL " );
			whereCond += " AND " + (Costanti.FLAG_VALORE_SI.equals(richiestaAggiudicate) ? "NOT" : "")
					+ " EXISTS (SELECT 1 FROM " + AGGIUDICAZIONI.TABLE_NAME + " WHERE " + AGGIUDICAZIONI.ID_INFO + " = "
					+ INFO_AGGIUDICAZIONI.T_ID_INFO + " AND " + AGGIUDICAZIONI.DATA_INIZIO_INFO + " = "
					+ INFO_AGGIUDICAZIONI.T_DATA_INIZIO_INFO + ") ";
		}

		// filtro per osservatori regionali
		if (admin_or != null && !ProfiloEnum.REGIONE_ZERO.equals(admin_or) && !ProfiloEnum.REGIONE_999.equals(admin_or)
				&& !ProfiloEnum.REGIONE_099.equals(admin_or)) {

//			whereCond += " AND EXISTS(SELECT 1 FROM " + AGGIUDICAZIONI.TABLE_NAME
//                          + " WHERE " + AGGIUDICAZIONI.ID_INFO + " = " + INFO_AGGIUDICAZIONI.T_ID_INFO 
//                          + " AND " + AGGIUDICAZIONI.DATA_INIZIO_INFO + " = " + INFO_AGGIUDICAZIONI.T_DATA_INIZIO_INFO
//                          + " AND (" + AGGIUDICAZIONI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING 
//                          + " OR "   + AGGIUDICAZIONI.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING + ")"

			String padded_id_osservatorio = admin_or.trim();
			if (padded_id_osservatorio.length() == 2)
				padded_id_osservatorio = "0" + padded_id_osservatorio;

			whereCond += " AND " + GARA.ID_OSSERVATORIO + " = '" + padded_id_osservatorio + "' ";
			logger.debug("padded_id_osservatorio " + padded_id_osservatorio );
			logger.info("padded_id_osservatorio " + padded_id_osservatorio );

			/*
			 * + " AND (EXISTS(SELECT 1 FROM " + COMUNI_VIEW.TABLE_NAME + " WHERE " +
			 * COMUNI_VIEW.ID_COMUNE + " = " + AGGIUDICAZIONI.T_LUOGO_ISTAT + " AND " +
			 * COMUNI_VIEW.ID_REGIONE + " = '" + admin_or + "')" +
			 * " OR EXISTS(SELECT 1 FROM " + CODICI_NUTS.TABLE_NAME + " WHERE " +
			 * CODICI_NUTS.ID_NUTS + " = " + AGGIUDICAZIONI.T_LUOGO_NUTS + " AND " +
			 * CODICI_NUTS.ID_REGIONE + " = '" + admin_or + "')))";
			 */
		}

		// filtro soglie importo
		if (minSoglia != null && minSoglia.trim().length() > 0 && maxSoglia != null && maxSoglia.trim().length() > 0) {
			whereCond += " AND (" + GARA.IMPORTO_GARA + " BETWEEN " + minSoglia + " AND " + maxSoglia + " OR "
					+ LOTTO.IMPORTO_LOTTO + " BETWEEN " + minSoglia + " AND " + maxSoglia + ")";
		}
		
		if (ammUtente.size() > 0) {
			whereCond += " AND " + addInCondition(FUNZIONI_DELEGATE_GARA.T_CF_AMM_AGENTE, ammUtente.keys());
		}

		final String GARA_ORDERBY = " order by " + GARA.T_ID_GARA + " ASC"; // LOTTO.T_CIG ; // PP LOTTO.T_ID_GARA + ","
																			// +

		String query = selectRicerca + whereCond + GARA_ORDERBY;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		query = "SELECT " + query;
		logger.info("Visualizzazione Ricerca Gare Delegate [" + query + "]");
		logger.debug("Visualizzazione Ricerca Gare Delegate [" + query + "]");
		// query
		try {
			pstmt = activeConnection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int posCounter = 1;
			pstmt = fillPstmt(pstmt, posCounter, listaSAAbilitato,ammUtente);
			
			logger.info("esecuzione prima exec");
			logger.debug("esecuzione prima exec");
			rs = pstmt.executeQuery();
			logger.info("fine esecuzione prima query");
			logger.debug("fine esecuzione prima query");

			/**
			 * X-XX: il false nel costruttore permette una iterazione parziale del RecordSet
			 */
			// TableBean result = new TableBean (false, rs, startRow, count, false);
			// new
			logger.info("tablebean exec");
			logger.debug("tablebean exec");
			// mi serve come parametro anche
			TableBean result = new GaraTableBean(rs, startRow, count, true, logger, true);

			// Ordiniamo la tablebean perche' non ci piace l'ordine decrescente dei lotti
			// dettato dalla query
			// che purtroppo non e' modificabile
//    		result.sortByGaraAndLotto();

			logger.debug("tablebean exec end");
			// close(rs1,pstmt1);
			logger.debug("Risultato per la query [" + selectRicerca + "] Tuple [" + result.getFullSize() + "]");
			logger.info("Risultato per la query [" + selectRicerca + "] Tuple [" + result.getFullSize() + "]");
			return (TableBean) result;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, pstmt);
		}
		// restituisco la connessione cosi come mi � stata data

//		logger.debug( "Risultato per la query [" + selectRicerca + "] Tuple [" + result.getTableSize() + "]");
// end
//		result.setFullSize(sizeOfReturnedRecords);	
	}
	

	/***************************************************************************************************
	 * Diego
	 * Recupera i dati delle gare RASA
	 * 
	 * @param oggettoGara           String
	 * @param oggettoLotto          String
	 * @param cig                   String
	 * @param numGara               String
	 * @param listaSAAbilitato      Hashtable
	 * @param dataScadenza_a        String
	 * @param dataScadenza_da       String
	 * @param dataPubblicazione_a   String
	 * @param dataPubblicazione_da  String
	 * @param startRow              int
	 * @param count                 int
	 * @param isRup_Cs              boolean
	 * @param richiestaAnnullamento String
	 * @param admin_or              String
	 * @param richiestaAggiudicate  String
	 * @param cfRUP                 String
	 * @param minSoglia             String
	 * @param maxSoglia             String
	 * @param cfAmm                 String
	 * 
	 * @return TableBean
	 * @throws SQLException
	 */

	public TableBean getGaraListRasa(String oggettoGara, String oggettoLotto, String cig, String numGara,
			Hashtable listaSAAbilitato, String dataScadenza_a, String dataScadenza_da, String dataPubblicazione_a,
			String dataPubblicazione_da, int startRow, int count, boolean isRup_Cs, String richiestaAnnullamento,
			String admin_or, String richiestaAggiudicate, String cfRUP, String minSoglia, String maxSoglia,
			ArrayList listCfAmm, String cfCreata) throws SQLException {
		
		logger.debug("getGaraListRasa");
		logger.info("getGaraListRasa");
		logger.debug("parametri-------------");
		logger.info("parametri-------------");
		logger.debug("oggettoGara " + oggettoGara );
		logger.info("oggettoGara " + oggettoGara );
		logger.debug("oggettoGara " + oggettoLotto );
		logger.info("oggettoGara " + oggettoLotto );
		logger.debug("cig " + cig );
		logger.info("cig " + cig );
		logger.debug("numGara " + numGara );
		logger.info("numGara " + numGara );
		logger.debug("listaSAAbilitato " + listaSAAbilitato );
		logger.info("listaSAAbilitato " + listaSAAbilitato );
		logger.debug("dataScadenza_a " + dataScadenza_a );
		logger.info("dataScadenza_a " + dataScadenza_a );
		logger.debug("dataScadenza_da " + dataScadenza_da );
		logger.info("dataScadenza_da " + dataScadenza_da );
		logger.debug("dataPubblicazione_a " + dataPubblicazione_a );
		logger.info("dataPubblicazione_a " + dataPubblicazione_a );
		logger.debug("dataPubblicazione_da " + dataPubblicazione_da );
		logger.info("dataPubblicazione_da " + dataPubblicazione_da );
		logger.debug("startRow " + startRow );
		logger.info("startRow " + startRow );
		logger.debug("count " + count );
		logger.info("count " + count );
		logger.debug("isRup_Cs " + isRup_Cs );
		logger.info("isRup_Cs " + isRup_Cs );
		logger.debug("richiestaAnnullamento " + richiestaAnnullamento );
		logger.info("richiestaAnnullamento " + richiestaAnnullamento );
		logger.debug("admin_or " + admin_or );
		logger.info("admin_or " + admin_or );
		logger.debug("richiestaAggiudicate " + richiestaAggiudicate );
		logger.info("richiestaAggiudicate " + richiestaAggiudicate );
		logger.debug("cfRUP " + cfRUP );
		logger.info("cfRUP " + cfRUP );
		logger.debug("minSoglia " + minSoglia );
		logger.info("minSoglia " + minSoglia );
		logger.debug("maxSoglia " + maxSoglia );
		logger.info("maxSoglia " + maxSoglia );
		logger.debug("cfAmm " + listCfAmm );
		logger.info("cfAmm " + listCfAmm );
		logger.debug("cfCreata " + cfCreata );
		logger.info("cfCreata " + cfCreata );

		String whereCond = " where 1=1 ";
		// String selectRicerca = "SELECT " + BASE_SELECT_INFO_GARA_LOTTO +
		// BASE_SELECT_INFO_GARA_LOTTO_FROM;
		String selectRicerca = BASE_SELECT_INFO_GARA_LOTTO + BASE_SELECT_INFO_GARA_LOTTO_FROM;
		if (isRup_Cs) {
			// selectRicerca = "SELECT distinct " + QUERY_RUP + QUERY_RUP_FROM;
			selectRicerca = QUERY_RUP + QUERY_RUP_FROM;

// PP tutti vedono tutto			
			selectRicerca += _INDEFINIZIONE;

//			// il profilo non è osservatorio regionale o centrale, ma solo rup
//			if(admin_or == null || ProfiloEnum.REGIONE_ZERO.equals(admin_or) ){
//			//AGGIUNTA CONDIZIONE IN DEFINIZIONE CHE E' STATA STACCATA DA QUERY_RUP_FROM
//				selectRicerca += _INDEFINIZIONE + " AND " + LOTTO.DATA_SCADENZA_PAGAMENTI + " is not null "  
//						  +  " AND "+LOTTO.DATA_SCADENZA_PAGAMENTI+" < '"+ PageHelper.getCurrentDate()+"'" ;
//			//ALTRIMENTI CONCATENO PER AVERE I DATI CONFERMATI/in definizione senza limitazioni
//			}else if(ProfiloEnum.REGIONE_099.equals(admin_or) || ProfiloEnum.REGIONE_999.equals(admin_or)){
//				selectRicerca += _INDEFINIZIONE;
//			//ALTRIMENTI CONCATENO PER AVERE I DATI SOLO CONFERMATI
//			}else{
//				selectRicerca += _INDEFINIZIONE;  // PP richiesta Obino 12.02.09 anche OSSR vede tutto selectRicerca += _CONFERMATI;
//			}
		}

		StringTokenizer tokenOggettoGara = null;
		String condizioniOggettoGara = null;

		if (oggettoGara != null && oggettoGara.trim().length() > 0) {
			tokenOggettoGara = new StringTokenizer(oggettoGara);
			String currToken = getCleanToken(tokenOggettoGara.nextToken());
			condizioniOggettoGara = GARA.T_OGGETTO + " LIKE '%" + currToken + "%'";
//			String pesatura = " sign(charindex('"+ currToken+ "',lower("+ GARA.T_OGGETTO + "))) ";

			while (tokenOggettoGara.hasMoreElements()) {
				currToken = getCleanToken(tokenOggettoGara.nextToken());
				condizioniOggettoGara += " AND " + GARA.T_OGGETTO + " LIKE '%" + currToken + "%'"; // UN sostituiti gli
																									// OR con AND
																									// (16-02-09)
//				pesatura += " + sign(charindex('"+ currToken+ "',lower("+ GARA.T_OGGETTO + "))) ";
			}
			whereCond += " AND ( " + condizioniOggettoGara + ")";
			logger.debug("currToken " + currToken );
			logger.info("currToken " + currToken );
			logger.debug("condizioniOggettoGara " + condizioniOggettoGara );
			logger.info("condizioniOggettoGara " + condizioniOggettoGara );
			
		}

		StringTokenizer tokenOggettoLotto = null;

		String condizioniOggettoLotto = null;

		if (oggettoLotto != null && oggettoLotto.trim().length() > 0) {
			tokenOggettoLotto = new StringTokenizer(oggettoLotto);
			condizioniOggettoLotto = LOTTO.T_OGGETTO + " LIKE '%" + getCleanToken(tokenOggettoLotto.nextToken()) + "%'";
			while (tokenOggettoLotto.hasMoreElements()) {
				condizioniOggettoLotto += " AND " + LOTTO.T_OGGETTO + " LIKE '%"
						+ getCleanToken(tokenOggettoLotto.nextToken()) + "%'"; // UN sostituiti gli OR con AND
																				// (16-02-09)
			}
			whereCond += " AND ( " + condizioniOggettoLotto + ")";
			logger.debug("tokenOggettoLotto " + tokenOggettoLotto );
			logger.info("tokenOggettoLotto " + tokenOggettoLotto );
			logger.debug("condizioniOggettoLotto " + condizioniOggettoLotto );
			logger.info("condizioniOggettoLotto " + condizioniOggettoLotto );
		}

		if (cig != null && cig.trim().length() > 0) {
			whereCond += " AND " + getQueryConditionByCIGSommaUrgenza(cig);
			logger.debug("getQueryConditionByCIGSommaUrgenza(cig) " + getQueryConditionByCIGSommaUrgenza(cig) );
			logger.info("getQueryConditionByCIGSommaUrgenza(cig) " + getQueryConditionByCIGSommaUrgenza(cig) );
		}

		if (numGara != null && numGara.trim().length() > 0) {
			whereCond += " AND " + GARA.T_ID_GARA + " = " + numGara;
		}

		if (cfRUP != null && cfRUP.trim().length() > 0) {
//	PP 20.11.2015 condizione errata per OSSN		whereCond += " AND "  + INFO_AGGIUDICAZIONI.CF_RUP + " = '" + cfRUP + "'";
			whereCond += " AND (" + GARA.T_CF_UTENTE + " = '" + cfRUP + "'" + " OR " + INFO_AGGIUDICAZIONI.T_CF_RUP
					+ "= '" + cfRUP + "')";
		}

		if (listCfAmm != null && listCfAmm.size()>0) {
			//Diego
			//Originale
			//whereCond += " AND " + GARA.T_CF_AMMINISTRAZIONE + " = '" + cfAmm + "'";
			//Modificato - 
			
			StringBuffer values = ConcatenateString.concatenate(listCfAmm);
					
			whereCond += " AND " + GARA.T_CF_AMMINISTRAZIONE + " IN" +" ("+ values +")";
			logger.debug("listCfAmm values " + values );
			logger.info("listCfAmm values " + values );
		}

		if (cfCreata != null && cfCreata.trim().length() > 0) {
			whereCond += " AND (" + GARA.T_CF_UTENTE + " = '" + cfCreata + "'" + " OR " + INFO_AGGIUDICAZIONI.T_CF_RUP
					+ "= '" + cfCreata + "')";
		}
		if (dataScadenza_a != null) {
			whereCond += " AND " + LOTTO.T_DATA_SCADENZA_PAGAMENTI + " <= '" + dataScadenza_a + "'";
		}
		if (dataScadenza_da != null) {
			whereCond += " AND " + LOTTO.T_DATA_SCADENZA_PAGAMENTI + " >= '" + dataScadenza_da + "'";
		}

		if (dataPubblicazione_a != null) {
			whereCond += " AND " + LOTTO.T_DATA_PUBBLICAZIONE + " <= '" + dataPubblicazione_a + "'";
		}
		if (dataPubblicazione_da != null) {
			whereCond += " AND " + LOTTO.T_DATA_PUBBLICAZIONE + " >= '" + dataPubblicazione_da + "'";
		}
		if (listaSAAbilitato.size() > 0) {
			whereCond += " AND " + addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys());
			logger.debug("addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys()) " + addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys()) );
			logger.info("addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys()) " + addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys()));
		}

		if (richiestaAnnullamento != null) {
			whereCond += " AND " + LOTTO.T_ID_LOTTO + ("N".equals(richiestaAnnullamento) ? " NOT IN " : " IN ")
					+ "( SELECT " + RICHIESTA_ANNULLAMENTO.ID_LOTTO + " FROM " + RICHIESTA_ANNULLAMENTO.TABLE_NAME
					+ " WHERE " + RICHIESTA_ANNULLAMENTO.T_DATA_FINE + " IS NULL )";

		}
		// solo con aggiudicazioni o senza aggiudicazioni
		if (richiestaAggiudicate != null) {
			// whereCond += " AND " + INFO_AGGIUDICAZIONI.T_ID_INFO +
			// ("S".equals(richiestaAggiudicate) ? " IS NULL " : " IS NOT NULL " );
			whereCond += " AND " + (Costanti.FLAG_VALORE_SI.equals(richiestaAggiudicate) ? "NOT" : "")
					+ " EXISTS (SELECT 1 FROM " + AGGIUDICAZIONI.TABLE_NAME + " WHERE " + AGGIUDICAZIONI.ID_INFO + " = "
					+ INFO_AGGIUDICAZIONI.T_ID_INFO + " AND " + AGGIUDICAZIONI.DATA_INIZIO_INFO + " = "
					+ INFO_AGGIUDICAZIONI.T_DATA_INIZIO_INFO + ") ";
		}

		// filtro per osservatori regionali
		if (admin_or != null && !ProfiloEnum.REGIONE_ZERO.equals(admin_or) && !ProfiloEnum.REGIONE_999.equals(admin_or)
				&& !ProfiloEnum.REGIONE_099.equals(admin_or)) {

//			whereCond += " AND EXISTS(SELECT 1 FROM " + AGGIUDICAZIONI.TABLE_NAME
//                          + " WHERE " + AGGIUDICAZIONI.ID_INFO + " = " + INFO_AGGIUDICAZIONI.T_ID_INFO 
//                          + " AND " + AGGIUDICAZIONI.DATA_INIZIO_INFO + " = " + INFO_AGGIUDICAZIONI.T_DATA_INIZIO_INFO
//                          + " AND (" + AGGIUDICAZIONI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING 
//                          + " OR "   + AGGIUDICAZIONI.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING + ")"

			String padded_id_osservatorio = admin_or.trim();
			if (padded_id_osservatorio.length() == 2)
				padded_id_osservatorio = "0" + padded_id_osservatorio;

			whereCond += " AND " + GARA.ID_OSSERVATORIO + " = '" + padded_id_osservatorio + "' ";
			logger.debug("padded_id_osservatorio " + padded_id_osservatorio );
			logger.info("padded_id_osservatorio " + padded_id_osservatorio );

			/*
			 * + " AND (EXISTS(SELECT 1 FROM " + COMUNI_VIEW.TABLE_NAME + " WHERE " +
			 * COMUNI_VIEW.ID_COMUNE + " = " + AGGIUDICAZIONI.T_LUOGO_ISTAT + " AND " +
			 * COMUNI_VIEW.ID_REGIONE + " = '" + admin_or + "')" +
			 * " OR EXISTS(SELECT 1 FROM " + CODICI_NUTS.TABLE_NAME + " WHERE " +
			 * CODICI_NUTS.ID_NUTS + " = " + AGGIUDICAZIONI.T_LUOGO_NUTS + " AND " +
			 * CODICI_NUTS.ID_REGIONE + " = '" + admin_or + "')))";
			 */
		}

		// filtro soglie importo
		if (minSoglia != null && minSoglia.trim().length() > 0 && maxSoglia != null && maxSoglia.trim().length() > 0) {
			whereCond += " AND (" + GARA.IMPORTO_GARA + " BETWEEN " + minSoglia + " AND " + maxSoglia + " OR "
					+ LOTTO.IMPORTO_LOTTO + " BETWEEN " + minSoglia + " AND " + maxSoglia + ")";
		}

		final String GARA_ORDERBY = " order by " + GARA.T_ID_GARA + " ASC"; // LOTTO.T_CIG ; // PP LOTTO.T_ID_GARA + ","
																			// +

		String query = selectRicerca + whereCond + GARA_ORDERBY;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		query = "SELECT " + query;
		logger.info("Visualizzazione Ricerca Gare [" + query + "]");
		logger.debug("Visualizzazione Ricerca Gare [" + query + "]");
		// query
		try {
			pstmt = activeConnection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int posCounter = 1;
			pstmt = fillPstmt(pstmt, posCounter, listaSAAbilitato);
			logger.debug("esecuzione prima exec");
			rs = pstmt.executeQuery();
			logger.debug("fine esecuzione prima query");

			/**
			 * X-XX: il false nel costruttore permette una iterazione parziale del RecordSet
			 */
			// TableBean result = new TableBean (false, rs, startRow, count, false);
			// new
			logger.debug("tablebean exec");
			// mi serve come parametro anche
			TableBean result = new GaraTableBean(rs, startRow, count, true, logger, true);

			// Ordiniamo la tablebean perche' non ci piace l'ordine decrescente dei lotti
			// dettato dalla query
			// che purtroppo non e' modificabile
//    		result.sortByGaraAndLotto();

			logger.debug("tablebean exec end");
			// close(rs1,pstmt1);
			logger.debug("Risultato per la query [" + selectRicerca + "] Tuple [" + result.getFullSize() + "]");
			logger.info("Risultato per la query [" + selectRicerca + "] Tuple [" + result.getFullSize() + "]");
			return (TableBean) result;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, pstmt);
		}
		// restituisco la connessione cosi come mi � stata data

//		logger.debug( "Risultato per la query [" + selectRicerca + "] Tuple [" + result.getTableSize() + "]");
// end
//		result.setFullSize(sizeOfReturnedRecords);	
	}
	

	/***************************************************************************************************
	 * Recupera i dati delle gare
	 * 
	 * @param oggettoGara           String
	 * @param oggettoLotto          String
	 * @param cig                   String
	 * @param numGara               String
	 * @param listaSAAbilitato      Hashtable
	 * @param dataScadenza_a        String
	 * @param dataScadenza_da       String
	 * @param dataPubblicazione_a   String
	 * @param dataPubblicazione_da  String
	 * @param startRow              int
	 * @param count                 int
	 * @param isRup_Cs              boolean
	 * @param richiestaAnnullamento String
	 * @param admin_or              String
	 * @param richiestaAggiudicate  String
	 * @param cfRUP                 String
	 * @param minSoglia             String
	 * @param maxSoglia             String
	 * @param cfAmm                 String
	 * 
	 * @return TableBean
	 * @throws SQLException
	 */

	public TableBean getGaraList(String oggettoGara, String oggettoLotto, String cig, String numGara,
			Hashtable listaSAAbilitato, String dataScadenza_a, String dataScadenza_da, String dataPubblicazione_a,
			String dataPubblicazione_da, int startRow, int count, boolean isRup_Cs, String richiestaAnnullamento,
			String admin_or, String richiestaAggiudicate, String cfRUP, String minSoglia, String maxSoglia,
			String cfAmm, String cfCreata) throws SQLException {
		
		logger.debug("getGaraList");
		logger.info("getGaraList");
		logger.debug("parametri-------------");
		logger.info("parametri-------------");
		logger.debug("oggettoGara " + oggettoGara );
		logger.info("oggettoGara " + oggettoGara );
		logger.debug("oggettoGara " + oggettoLotto );
		logger.info("oggettoGara " + oggettoLotto );
		logger.debug("cig " + cig );
		logger.info("cig " + cig );
		logger.debug("numGara " + numGara );
		logger.info("numGara " + numGara );
		logger.debug("listaSAAbilitato " + listaSAAbilitato );
		logger.info("listaSAAbilitato " + listaSAAbilitato );
		logger.debug("dataScadenza_a " + dataScadenza_a );
		logger.info("dataScadenza_a " + dataScadenza_a );
		logger.debug("dataScadenza_da " + dataScadenza_da );
		logger.info("dataScadenza_da " + dataScadenza_da );
		logger.debug("dataPubblicazione_a " + dataPubblicazione_a );
		logger.info("dataPubblicazione_a " + dataPubblicazione_a );
		logger.debug("dataPubblicazione_da " + dataPubblicazione_da );
		logger.info("dataPubblicazione_da " + dataPubblicazione_da );
		logger.debug("startRow " + startRow );
		logger.info("startRow " + startRow );
		logger.debug("count " + count );
		logger.info("count " + count );
		logger.debug("isRup_Cs " + isRup_Cs );
		logger.info("isRup_Cs " + isRup_Cs );
		logger.debug("richiestaAnnullamento " + richiestaAnnullamento );
		logger.info("richiestaAnnullamento " + richiestaAnnullamento );
		logger.debug("admin_or " + admin_or );
		logger.info("admin_or " + admin_or );
		logger.debug("richiestaAggiudicate " + richiestaAggiudicate );
		logger.info("richiestaAggiudicate " + richiestaAggiudicate );
		logger.debug("cfRUP " + cfRUP );
		logger.info("cfRUP " + cfRUP );
		logger.debug("minSoglia " + minSoglia );
		logger.info("minSoglia " + minSoglia );
		logger.debug("maxSoglia " + maxSoglia );
		logger.info("maxSoglia " + maxSoglia );
		logger.debug("cfAmm " + cfAmm );
		logger.info("cfAmm " + cfAmm );
		logger.debug("cfCreata " + cfCreata );
		logger.info("cfCreata " + cfCreata );
		

		String whereCond = " where 1=1 ";
		// String selectRicerca = "SELECT " + BASE_SELECT_INFO_GARA_LOTTO +
		// BASE_SELECT_INFO_GARA_LOTTO_FROM;
		String selectRicerca = BASE_SELECT_INFO_GARA_LOTTO + BASE_SELECT_INFO_GARA_LOTTO_FROM;
		if (isRup_Cs) {
			// selectRicerca = "SELECT distinct " + QUERY_RUP + QUERY_RUP_FROM;
			selectRicerca = QUERY_RUP + QUERY_RUP_FROM;

// PP tutti vedono tutto			
			selectRicerca += _INDEFINIZIONE;

//			// il profilo non è osservatorio regionale o centrale, ma solo rup
//			if(admin_or == null || ProfiloEnum.REGIONE_ZERO.equals(admin_or) ){
//			//AGGIUNTA CONDIZIONE IN DEFINIZIONE CHE E' STATA STACCATA DA QUERY_RUP_FROM
//				selectRicerca += _INDEFINIZIONE + " AND " + LOTTO.DATA_SCADENZA_PAGAMENTI + " is not null "  
//						  +  " AND "+LOTTO.DATA_SCADENZA_PAGAMENTI+" < '"+ PageHelper.getCurrentDate()+"'" ;
//			//ALTRIMENTI CONCATENO PER AVERE I DATI CONFERMATI/in definizione senza limitazioni
//			}else if(ProfiloEnum.REGIONE_099.equals(admin_or) || ProfiloEnum.REGIONE_999.equals(admin_or)){
//				selectRicerca += _INDEFINIZIONE;
//			//ALTRIMENTI CONCATENO PER AVERE I DATI SOLO CONFERMATI
//			}else{
//				selectRicerca += _INDEFINIZIONE;  // PP richiesta Obino 12.02.09 anche OSSR vede tutto selectRicerca += _CONFERMATI;
//			}
		}

		StringTokenizer tokenOggettoGara = null;
		String condizioniOggettoGara = null;

		if (oggettoGara != null && oggettoGara.trim().length() > 0) {
			tokenOggettoGara = new StringTokenizer(oggettoGara);
			String currToken = getCleanToken(tokenOggettoGara.nextToken());
			condizioniOggettoGara = GARA.T_OGGETTO + " LIKE '%" + currToken + "%'";
//			String pesatura = " sign(charindex('"+ currToken+ "',lower("+ GARA.T_OGGETTO + "))) ";

			while (tokenOggettoGara.hasMoreElements()) {
				currToken = getCleanToken(tokenOggettoGara.nextToken());
				condizioniOggettoGara += " AND " + GARA.T_OGGETTO + " LIKE '%" + currToken + "%'"; // UN sostituiti gli
																									// OR con AND
																									// (16-02-09)
//				pesatura += " + sign(charindex('"+ currToken+ "',lower("+ GARA.T_OGGETTO + "))) ";
			}
			whereCond += " AND ( " + condizioniOggettoGara + ")";
			logger.debug("condizioniOggettoGara " + condizioniOggettoGara );
			logger.info("condizioniOggettoGara " + condizioniOggettoGara );
			logger.debug("currToken " + currToken );
			logger.info("currToken " + currToken );
		}

		StringTokenizer tokenOggettoLotto = null;

		String condizioniOggettoLotto = null;

		if (oggettoLotto != null && oggettoLotto.trim().length() > 0) {
			tokenOggettoLotto = new StringTokenizer(oggettoLotto);
			condizioniOggettoLotto = LOTTO.T_OGGETTO + " LIKE '%" + getCleanToken(tokenOggettoLotto.nextToken()) + "%'";
			while (tokenOggettoLotto.hasMoreElements()) {
				condizioniOggettoLotto += " AND " + LOTTO.T_OGGETTO + " LIKE '%"
						+ getCleanToken(tokenOggettoLotto.nextToken()) + "%'"; // UN sostituiti gli OR con AND
																				// (16-02-09)
			}
			whereCond += " AND ( " +condizioniOggettoLotto + ")";
			logger.debug("condizioniOggettoLotto " + condizioniOggettoLotto );
			logger.info("condizioniOggettoLotto " + condizioniOggettoLotto );
			logger.debug("tokenOggettoLotto " + tokenOggettoLotto );
			logger.info("tokenOggettoLotto " + tokenOggettoLotto );
		}

		if (cig != null && cig.trim().length() > 0) {
			whereCond += " AND " + getQueryConditionByCIGSommaUrgenza(cig);
			logger.debug("getQueryConditionByCIGSommaUrgenza(cig) " + getQueryConditionByCIGSommaUrgenza(cig) );
			logger.info("getQueryConditionByCIGSommaUrgenza(cig) " + getQueryConditionByCIGSommaUrgenza(cig) );
		}

		if (numGara != null && numGara.trim().length() > 0) {
			whereCond += " AND " + GARA.T_ID_GARA + " = " + numGara;
		}

		if (cfRUP != null && cfRUP.trim().length() > 0) {
//	PP 20.11.2015 condizione errata per OSSN		whereCond += " AND "  + INFO_AGGIUDICAZIONI.CF_RUP + " = '" + cfRUP + "'";
			whereCond += " AND (" + GARA.T_CF_UTENTE + " = '" + cfRUP + "'" + " OR " + INFO_AGGIUDICAZIONI.T_CF_RUP
					+ "= '" + cfRUP + "')";
		}

		if (cfAmm != null && cfAmm.trim().length() > 0) {
			whereCond += " AND " + GARA.T_CF_AMMINISTRAZIONE + " = '" + cfAmm + "'";
		}

		if (cfCreata != null && cfCreata.trim().length() > 0) {
			whereCond += " AND (" + GARA.T_CF_UTENTE + " = '" + cfCreata + "'" + " OR " + INFO_AGGIUDICAZIONI.T_CF_RUP
					+ "= '" + cfCreata + "')";
		}
		if (dataScadenza_a != null) {
			whereCond += " AND " + LOTTO.T_DATA_SCADENZA_PAGAMENTI + " <= '" + dataScadenza_a + "'";
		}
		if (dataScadenza_da != null) {
			whereCond += " AND " + LOTTO.T_DATA_SCADENZA_PAGAMENTI + " >= '" + dataScadenza_da + "'";
		}

		if (dataPubblicazione_a != null) {
			whereCond += " AND " + LOTTO.T_DATA_PUBBLICAZIONE + " <= '" + dataPubblicazione_a + "'";
		}
		if (dataPubblicazione_da != null) {
			whereCond += " AND " + LOTTO.T_DATA_PUBBLICAZIONE + " >= '" + dataPubblicazione_da + "'";
		}
		if (listaSAAbilitato.size() > 0) {
			whereCond += " AND " + addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys());
			logger.debug("addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys()) " + addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys()) );
			logger.info("addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys()) " + addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys()) );
		}

		if (richiestaAnnullamento != null) {
			whereCond += " AND " + LOTTO.T_ID_LOTTO + ("N".equals(richiestaAnnullamento) ? " NOT IN " : " IN ")
					+ "( SELECT " + RICHIESTA_ANNULLAMENTO.ID_LOTTO + " FROM " + RICHIESTA_ANNULLAMENTO.TABLE_NAME
					+ " WHERE " + RICHIESTA_ANNULLAMENTO.T_DATA_FINE + " IS NULL )";

		}
		// solo con aggiudicazioni o senza aggiudicazioni
		if (richiestaAggiudicate != null) {
			// whereCond += " AND " + INFO_AGGIUDICAZIONI.T_ID_INFO +
			// ("S".equals(richiestaAggiudicate) ? " IS NULL " : " IS NOT NULL " );
			whereCond += " AND " + (Costanti.FLAG_VALORE_SI.equals(richiestaAggiudicate) ? "NOT" : "")
					+ " EXISTS (SELECT 1 FROM " + AGGIUDICAZIONI.TABLE_NAME + " WHERE " + AGGIUDICAZIONI.ID_INFO + " = "
					+ INFO_AGGIUDICAZIONI.T_ID_INFO + " AND " + AGGIUDICAZIONI.DATA_INIZIO_INFO + " = "
					+ INFO_AGGIUDICAZIONI.T_DATA_INIZIO_INFO + ") ";
		}

		// filtro per osservatori regionali
		if (admin_or != null && !ProfiloEnum.REGIONE_ZERO.equals(admin_or) && !ProfiloEnum.REGIONE_999.equals(admin_or)
				&& !ProfiloEnum.REGIONE_099.equals(admin_or)) {

//			whereCond += " AND EXISTS(SELECT 1 FROM " + AGGIUDICAZIONI.TABLE_NAME
//                          + " WHERE " + AGGIUDICAZIONI.ID_INFO + " = " + INFO_AGGIUDICAZIONI.T_ID_INFO 
//                          + " AND " + AGGIUDICAZIONI.DATA_INIZIO_INFO + " = " + INFO_AGGIUDICAZIONI.T_DATA_INIZIO_INFO
//                          + " AND (" + AGGIUDICAZIONI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE_STRING 
//                          + " OR "   + AGGIUDICAZIONI.ID_STATO + " = " + StatiScheda.CONFERMATO_STRING + ")"

			String padded_id_osservatorio = admin_or.trim();
			if (padded_id_osservatorio.length() == 2)
				padded_id_osservatorio = "0" + padded_id_osservatorio;

			whereCond += " AND " + GARA.ID_OSSERVATORIO + " = '" + padded_id_osservatorio + "' ";
			logger.debug("padded_id_osservatorio " + padded_id_osservatorio );
			logger.info("padded_id_osservatorio " + padded_id_osservatorio );

			/*
			 * + " AND (EXISTS(SELECT 1 FROM " + COMUNI_VIEW.TABLE_NAME + " WHERE " +
			 * COMUNI_VIEW.ID_COMUNE + " = " + AGGIUDICAZIONI.T_LUOGO_ISTAT + " AND " +
			 * COMUNI_VIEW.ID_REGIONE + " = '" + admin_or + "')" +
			 * " OR EXISTS(SELECT 1 FROM " + CODICI_NUTS.TABLE_NAME + " WHERE " +
			 * CODICI_NUTS.ID_NUTS + " = " + AGGIUDICAZIONI.T_LUOGO_NUTS + " AND " +
			 * CODICI_NUTS.ID_REGIONE + " = '" + admin_or + "')))";
			 */
		}

		// filtro soglie importo
		if (minSoglia != null && minSoglia.trim().length() > 0 && maxSoglia != null && maxSoglia.trim().length() > 0) {
			whereCond += " AND (" + GARA.IMPORTO_GARA + " BETWEEN " + minSoglia + " AND " + maxSoglia + " OR "
					+ LOTTO.IMPORTO_LOTTO + " BETWEEN " + minSoglia + " AND " + maxSoglia + ")";
		}

		final String GARA_ORDERBY = " order by " + GARA.T_ID_GARA + " ASC"; // LOTTO.T_CIG ; // PP LOTTO.T_ID_GARA + ","
																			// +

		String query = selectRicerca + whereCond + GARA_ORDERBY;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		query = "SELECT " + query;
		logger.info("Visualizzazione Ricerca Gare [" + query + "]");
		logger.debug("Visualizzazione Ricerca Gare [" + query + "]");
		// query
		try {
			pstmt = activeConnection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int posCounter = 1;
			pstmt = fillPstmt(pstmt, posCounter, listaSAAbilitato);
			logger.debug("esecuzione prima exec");
			rs = pstmt.executeQuery();
			logger.debug("fine esecuzione prima query");

			/**
			 * X-XX: il false nel costruttore permette una iterazione parziale del RecordSet
			 */
			// TableBean result = new TableBean (false, rs, startRow, count, false);
			// new
			logger.debug("tablebean exec");
			// mi serve come parametro anche
			TableBean result = new GaraTableBean(rs, startRow, count, true, logger, true);

			// Ordiniamo la tablebean perche' non ci piace l'ordine decrescente dei lotti
			// dettato dalla query
			// che purtroppo non e' modificabile
//    		result.sortByGaraAndLotto();

			logger.debug("tablebean exec end");
			// close(rs1,pstmt1);
			logger.debug("Risultato per la query [" + selectRicerca + "] Tuple [" + result.getFullSize() + "]");
			logger.info("Risultato per la query [" + selectRicerca + "] Tuple [" + result.getFullSize() + "]");
			return (TableBean) result;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, pstmt);
		}
		// restituisco la connessione cosi come mi � stata data

//		logger.debug( "Risultato per la query [" + selectRicerca + "] Tuple [" + result.getTableSize() + "]");
// end
//		result.setFullSize(sizeOfReturnedRecords);	
	}

	
	public TableBean getGaraListPostDelega(String oggettoGara, String oggettoLotto, String cig, String numGara,
			Hashtable listaSAAbilitato, String dataScadenza_a, String dataScadenza_da, String dataPubblicazione_a,
			String dataPubblicazione_da, int startRow, int count, boolean isRup_Cs, String richiestaAnnullamento,
			String admin_or, String richiestaAggiudicate, String cfRUP, String minSoglia, String maxSoglia,
			String cfAmm, String cfCreata) throws SQLException {
		
		logger.debug("getGaraListPostDelega");
		logger.info("getGaraListPostDelega");
		logger.debug("parametri-------------");
		logger.info("parametri-------------");
		logger.debug("oggettoGara " + oggettoGara );
		logger.info("oggettoGara " + oggettoGara );
		logger.debug("oggettoGara " + oggettoLotto );
		logger.info("oggettoGara " + oggettoLotto );
		logger.debug("cig " + cig );
		logger.info("cig " + cig );
		logger.debug("numGara " + numGara );
		logger.info("numGara " + numGara );
		logger.debug("listaSAAbilitato " + listaSAAbilitato );
		logger.info("listaSAAbilitato " + listaSAAbilitato );
		logger.debug("dataScadenza_a " + dataScadenza_a );
		logger.info("dataScadenza_a " + dataScadenza_a );
		logger.debug("dataScadenza_da " + dataScadenza_da );
		logger.info("dataScadenza_da " + dataScadenza_da );
		logger.debug("dataPubblicazione_a " + dataPubblicazione_a );
		logger.info("dataPubblicazione_a " + dataPubblicazione_a );
		logger.debug("dataPubblicazione_da " + dataPubblicazione_da );
		logger.info("dataPubblicazione_da " + dataPubblicazione_da );
		logger.debug("startRow " + startRow );
		logger.info("startRow " + startRow );
		logger.debug("count " + count );
		logger.info("count " + count );
		logger.debug("isRup_Cs " + isRup_Cs );
		logger.info("isRup_Cs " + isRup_Cs );
		logger.debug("richiestaAnnullamento " + richiestaAnnullamento );
		logger.info("richiestaAnnullamento " + richiestaAnnullamento );
		logger.debug("admin_or " + admin_or );
		logger.info("admin_or " + admin_or );
		logger.debug("richiestaAggiudicate " + richiestaAggiudicate );
		logger.info("richiestaAggiudicate " + richiestaAggiudicate );
		logger.debug("cfRUP " + cfRUP );
		logger.info("cfRUP " + cfRUP );
		logger.debug("minSoglia " + minSoglia );
		logger.info("minSoglia " + minSoglia );
		logger.debug("maxSoglia " + maxSoglia );
		logger.info("maxSoglia " + maxSoglia );
		logger.debug("cfAmm " + cfAmm );
		logger.info("cfAmm " + cfAmm );
		logger.debug("cfCreata " + cfCreata );
		logger.info("cfCreata " + cfCreata );

		String whereCond = " where 1=1 ";
		// String selectRicerca = "SELECT " + BASE_SELECT_INFO_GARA_LOTTO +
		// BASE_SELECT_INFO_GARA_LOTTO_FROM;
		String selectRicerca = BASE_SELECT_INFO_GARA_LOTTO + BASE_SELECT_INFO_GARA_LOTTO_FROM;
		if (isRup_Cs) {
			selectRicerca = QUERY_RUP + QUERY_RUP_FROM;

			selectRicerca += _INDEFINIZIONE;

		}

		StringTokenizer tokenOggettoGara = null;
		String condizioniOggettoGara = null;

		if (oggettoGara != null && oggettoGara.trim().length() > 0) {
			tokenOggettoGara = new StringTokenizer(oggettoGara);
			String currToken = getCleanToken(tokenOggettoGara.nextToken());
			condizioniOggettoGara = GARA.T_OGGETTO + " LIKE '%" + currToken + "%'";

			while (tokenOggettoGara.hasMoreElements()) {
				currToken = getCleanToken(tokenOggettoGara.nextToken());
				condizioniOggettoGara += " AND " + GARA.T_OGGETTO + " LIKE '%" + currToken + "%'"; // UN sostituiti gli
																									// OR con AND
																									// (16-02-09)
			}
			whereCond += " AND ( " + condizioniOggettoGara + ")";
			logger.debug("condizioniOggettoGara " + condizioniOggettoGara );
			logger.info("condizioniOggettoGara " + condizioniOggettoGara );
			logger.debug("currToken " + currToken );
			logger.info("currToken " + currToken );
		}

		StringTokenizer tokenOggettoLotto = null;

		String condizioniOggettoLotto = null;

		if (oggettoLotto != null && oggettoLotto.trim().length() > 0) {
			tokenOggettoLotto = new StringTokenizer(oggettoLotto);
			condizioniOggettoLotto = LOTTO.T_OGGETTO + " LIKE '%" + getCleanToken(tokenOggettoLotto.nextToken()) + "%'";
			while (tokenOggettoLotto.hasMoreElements()) {
				condizioniOggettoLotto += " AND " + LOTTO.T_OGGETTO + " LIKE '%"
						+ getCleanToken(tokenOggettoLotto.nextToken()) + "%'"; // UN sostituiti gli OR con AND
																				// (16-02-09)
			}
			whereCond += " AND ( " + condizioniOggettoLotto + ")";
			logger.debug("condizioniOggettoLotto " + condizioniOggettoLotto );
			logger.info("condizioniOggettoLotto " + condizioniOggettoLotto );
			logger.debug("tokenOggettoLotto " + tokenOggettoLotto );
			logger.info("tokenOggettoLotto " + tokenOggettoLotto );
		}

		if (cig != null && cig.trim().length() > 0) {
			whereCond += " AND " + getQueryConditionByCIGSommaUrgenza(cig);
			logger.debug("getQueryConditionByCIGSommaUrgenza(cig) " + getQueryConditionByCIGSommaUrgenza(cig) );
			logger.info("getQueryConditionByCIGSommaUrgenza(cig) " + getQueryConditionByCIGSommaUrgenza(cig) );
		}

		if (numGara != null && numGara.trim().length() > 0) {
			whereCond += " AND " + GARA.T_ID_GARA + " = " + numGara;
		}

		if (cfRUP != null && cfRUP.trim().length() > 0) {
			whereCond += " AND (" + GARA.T_CF_UTENTE + " = '" + cfRUP + "'" + " OR " + INFO_AGGIUDICAZIONI.T_CF_RUP
					+ "= '" + cfRUP + "')";
		}

		if (cfAmm != null && cfAmm.trim().length() > 0) {
			whereCond += " AND " + GARA.T_CF_AMMINISTRAZIONE + " = '" + cfAmm + "'";
		}

		if (cfCreata != null && cfCreata.trim().length() > 0) {
			whereCond += " AND (" + GARA.T_CF_UTENTE + " = '" + cfCreata + "'" + " OR " + INFO_AGGIUDICAZIONI.T_CF_RUP
					+ "= '" + cfCreata + "')";
		}
		if (dataScadenza_a != null) {
			whereCond += " AND " + LOTTO.T_DATA_SCADENZA_PAGAMENTI + " <= '" + dataScadenza_a + "'";
		}
		if (dataScadenza_da != null) {
			whereCond += " AND " + LOTTO.T_DATA_SCADENZA_PAGAMENTI + " >= '" + dataScadenza_da + "'";
		}

		if (dataPubblicazione_a != null) {
			whereCond += " AND " + LOTTO.T_DATA_PUBBLICAZIONE + " <= '" + dataPubblicazione_a + "'";
		}
		if (dataPubblicazione_da != null) {
			whereCond += " AND " + LOTTO.T_DATA_PUBBLICAZIONE + " >= '" + dataPubblicazione_da + "'";
		}
		if (listaSAAbilitato.size() > 0) {
			whereCond += " AND " + addInCondition(FUNZIONI_DELEGATE_GARA.T_ID_SA_DELEGATA, listaSAAbilitato.keys());
			logger.debug("addInCondition(FUNZIONI_DELEGATE_GARA.T_ID_SA_DELEGATA, listaSAAbilitato.keys()) " + addInCondition(FUNZIONI_DELEGATE_GARA.T_ID_SA_DELEGATA, listaSAAbilitato.keys()) );
			logger.info("addInCondition(FUNZIONI_DELEGATE_GARA.T_ID_SA_DELEGATA, listaSAAbilitato.keys()) " + addInCondition(FUNZIONI_DELEGATE_GARA.T_ID_SA_DELEGATA, listaSAAbilitato.keys()) );
		}

		if (richiestaAnnullamento != null) {
			whereCond += " AND " + LOTTO.T_ID_LOTTO + ("N".equals(richiestaAnnullamento) ? " NOT IN " : " IN ")
					+ "( SELECT " + RICHIESTA_ANNULLAMENTO.ID_LOTTO + " FROM " + RICHIESTA_ANNULLAMENTO.TABLE_NAME
					+ " WHERE " + RICHIESTA_ANNULLAMENTO.T_DATA_FINE + " IS NULL )";

		}

		if (richiestaAggiudicate != null) {
			whereCond += " AND " + (Costanti.FLAG_VALORE_SI.equals(richiestaAggiudicate) ? "NOT" : "")
					+ " EXISTS (SELECT 1 FROM " + AGGIUDICAZIONI.TABLE_NAME + " WHERE " + AGGIUDICAZIONI.ID_INFO + " = "
					+ INFO_AGGIUDICAZIONI.T_ID_INFO + " AND " + AGGIUDICAZIONI.DATA_INIZIO_INFO + " = "
					+ INFO_AGGIUDICAZIONI.T_DATA_INIZIO_INFO + ") ";
		}

		// filtro per osservatori regionali
		if (admin_or != null && !ProfiloEnum.REGIONE_ZERO.equals(admin_or) && !ProfiloEnum.REGIONE_999.equals(admin_or)
				&& !ProfiloEnum.REGIONE_099.equals(admin_or)) {


			String padded_id_osservatorio = admin_or.trim();
			if (padded_id_osservatorio.length() == 2)
				padded_id_osservatorio = "0" + padded_id_osservatorio;

			whereCond += " AND " + GARA.ID_OSSERVATORIO + " = '" + padded_id_osservatorio + "' ";
			logger.debug("padded_id_osservatorio " + padded_id_osservatorio );
			logger.info("padded_id_osservatorio " + padded_id_osservatorio );


		}

		// filtro soglie importo
		if (minSoglia != null && minSoglia.trim().length() > 0 && maxSoglia != null && maxSoglia.trim().length() > 0) {
			whereCond += " AND (" + GARA.IMPORTO_GARA + " BETWEEN " + minSoglia + " AND " + maxSoglia + " OR "
					+ LOTTO.IMPORTO_LOTTO + " BETWEEN " + minSoglia + " AND " + maxSoglia + ")";
		}

		final String GARA_ORDERBY = " order by " + GARA.T_ID_GARA + " ASC"; // LOTTO.T_CIG ; // PP LOTTO.T_ID_GARA + ","
																			// +

		String query = selectRicerca + whereCond + GARA_ORDERBY;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		query = "SELECT " + query;
		logger.info("Visualizzazione Ricerca Gare [" + query + "]");
		logger.debug("Visualizzazione Ricerca Gare [" + query + "]");
		// query
		try {
			pstmt = activeConnection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int posCounter = 1;
			pstmt = fillPstmt(pstmt, posCounter, listaSAAbilitato);
			logger.debug("esecuzione prima exec");
			rs = pstmt.executeQuery();
			logger.debug("fine esecuzione prima query");

			logger.debug("tablebean exec");

			TableBean result = new GaraTableBean(rs, startRow, count, true, logger, true);


			logger.debug("tablebean exec end");
			// close(rs1,pstmt1);
			logger.debug("Risultato per la query [" + selectRicerca + "] Tuple [" + result.getFullSize() + "]");
			logger.info("Risultato per la query [" + selectRicerca + "] Tuple [" + result.getFullSize() + "]");
			return (TableBean) result;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, pstmt);
		}

	}
	
	/***************************************************************************************************
	 * Recupera i dati delle gare
	 * 
	 * @param oggettoGara           String
	 * @param oggettoLotto          String
	 * @param cig                   String
	 * @param numGara               String
	 * @param listaSAAbilitato      Hashtable
	 * @param dataScadenza_a        String
	 * @param dataScadenza_da       String
	 * @param dataPubblicazione_a   String
	 * @param dataPubblicazione_da  String
	 * @param startRow              int
	 * @param count                 int
	 * @param isRup_Cs              boolean
	 * @param richiestaAnnullamento String
	 * @param admin_or              String
	 * @param richiestaAggiudicate  String
	 * @param cfRUP                 String
	 * @param minSoglia             String
	 * @param maxSoglia             String
	 * @param cfAmm                 String
	 * 
	 * @return TableBean
	 * @throws SQLException
	 */

	public TableBean getGaraListAccQ(String oggettoGara, String oggettoLotto, String cig, String numGara,
			Hashtable listaSAAbilitato, String dataScadenza_a, String dataScadenza_da, String dataPubblicazione_a,
			String dataPubblicazione_da, int startRow, int count, boolean isRup_Cs, String richiestaAnnullamento,
			String admin_or, String richiestaAggiudicate, String cfRUP, String minSoglia, String maxSoglia)
			throws SQLException {
		
		logger.debug("getGaraListAccQ");
		logger.info("getGaraListAccQ");
		logger.debug("parametri-------------");
		logger.info("parametri-------------");
		logger.debug("oggettoGara " + oggettoGara );
		logger.info("oggettoGara " + oggettoGara );
		logger.debug("oggettoGara " + oggettoLotto );
		logger.info("oggettoGara " + oggettoLotto );
		logger.debug("cig " + cig );
		logger.info("cig " + cig );
		logger.debug("numGara " + numGara );
		logger.info("numGara " + numGara );
		logger.debug("listaSAAbilitato " + listaSAAbilitato );
		logger.info("listaSAAbilitato " + listaSAAbilitato );
		logger.debug("dataScadenza_a " + dataScadenza_a );
		logger.info("dataScadenza_a " + dataScadenza_a );
		logger.debug("dataScadenza_da " + dataScadenza_da );
		logger.info("dataScadenza_da " + dataScadenza_da );
		logger.debug("dataPubblicazione_a " + dataPubblicazione_a );
		logger.info("dataPubblicazione_a " + dataPubblicazione_a );
		logger.debug("dataPubblicazione_da " + dataPubblicazione_da );
		logger.info("dataPubblicazione_da " + dataPubblicazione_da );
		logger.debug("startRow " + startRow );
		logger.info("startRow " + startRow );
		logger.debug("count " + count );
		logger.info("count " + count );
		logger.debug("isRup_Cs " + isRup_Cs );
		logger.info("isRup_Cs " + isRup_Cs );
		logger.debug("richiestaAnnullamento " + richiestaAnnullamento );
		logger.info("richiestaAnnullamento " + richiestaAnnullamento );
		logger.debug("admin_or " + admin_or );
		logger.info("admin_or " + admin_or );
		logger.debug("richiestaAggiudicate " + richiestaAggiudicate );
		logger.info("richiestaAggiudicate " + richiestaAggiudicate );
		logger.debug("cfRUP " + cfRUP );
		logger.info("cfRUP " + cfRUP );
		logger.debug("minSoglia " + minSoglia );
		logger.info("minSoglia " + minSoglia );
		logger.debug("maxSoglia " + maxSoglia );
		logger.info("maxSoglia " + maxSoglia );

		String whereCond = " where 1=1 ";
		// String selectRicerca = "SELECT " + BASE_SELECT_INFO_GARA_LOTTO +
		// BASE_SELECT_INFO_GARA_LOTTO_FROM;
		String selectRicerca = BASE_SELECT_INFO_GARA_LOTTO + BASE_SELECT_INFO_GARA_LOTTO_FROM;
		if (isRup_Cs) {
			// selectRicerca = "SELECT distinct " + QUERY_RUP + QUERY_RUP_FROM;
			selectRicerca = QUERY_RUP + QUERY_RUP_FROM;

// PP tutti vedono tutto			
			selectRicerca += _INDEFINIZIONE;

		}

		StringTokenizer tokenOggettoGara = null;
		String condizioniOggettoGara = null;

		if (oggettoGara != null && oggettoGara.trim().length() > 0) {
			tokenOggettoGara = new StringTokenizer(oggettoGara);
			String currToken = getCleanToken(tokenOggettoGara.nextToken());
			condizioniOggettoGara = GARA.T_OGGETTO + " LIKE '%" + currToken + "%'";
//			String pesatura = " sign(charindex('"+ currToken+ "',lower("+ GARA.T_OGGETTO + "))) ";

			while (tokenOggettoGara.hasMoreElements()) {
				currToken = getCleanToken(tokenOggettoGara.nextToken());
				condizioniOggettoGara += " AND " + GARA.T_OGGETTO + " LIKE '%" + currToken + "%'"; // UN sostituiti gli
																									// OR con AND
																									// (16-02-09)
//				pesatura += " + sign(charindex('"+ currToken+ "',lower("+ GARA.T_OGGETTO + "))) ";
			}
			whereCond += " AND ( " + condizioniOggettoGara + ")";
			logger.debug("condizioniOggettoGara " + condizioniOggettoGara );
			logger.info("condizioniOggettoGara " + condizioniOggettoGara );
			logger.debug("currToken " + currToken );
			logger.info("currToken " + currToken );
		}

		StringTokenizer tokenOggettoLotto = null;

		String condizioniOggettoLotto = null;

		if (oggettoLotto != null && oggettoLotto.trim().length() > 0) {
			tokenOggettoLotto = new StringTokenizer(oggettoLotto);
			condizioniOggettoLotto = LOTTO.T_OGGETTO + " LIKE '%" + getCleanToken(tokenOggettoLotto.nextToken()) + "%'";
			while (tokenOggettoLotto.hasMoreElements()) {
				condizioniOggettoLotto += " AND " + LOTTO.T_OGGETTO + " LIKE '%"
						+ getCleanToken(tokenOggettoLotto.nextToken()) + "%'"; // UN sostituiti gli OR con AND
																				// (16-02-09)
			}
			whereCond += " AND ( " + condizioniOggettoLotto + ")";
			logger.debug("condizioniOggettoLotto " + condizioniOggettoLotto );
			logger.info("condizioniOggettoLotto " + condizioniOggettoLotto );
			logger.debug("tokenOggettoLotto " + tokenOggettoLotto );
			logger.info("tokenOggettoLotto " + tokenOggettoLotto );
		}

		if (cig != null && cig.trim().length() > 0) {
			whereCond += " AND " + getQueryConditionByCIGSommaUrgenza(cig);
			logger.debug("getQueryConditionByCIGSommaUrgenza(cig) " + getQueryConditionByCIGSommaUrgenza(cig) );
			logger.info("getQueryConditionByCIGSommaUrgenza(cig) " + getQueryConditionByCIGSommaUrgenza(cig) );
		}

		//ticket 31050 (rimosso per far vedere tutti gli AQ da OssReg)
		//whereCond += " AND " + GARA.T_ID_OSSERVATORIO + " = '099' ";
		whereCond += " AND " + GARA.T_ID_MODO_REAL + " IN (" + Costanti.MODOREAL_ACCORDO_QUADRO + ", "
				+ Costanti.MODOREAL_ACCORDO + "," + Costanti.MODOREAL_CONVENZIONE + ") ";

		if (numGara != null && numGara.trim().length() > 0) {
			whereCond += " AND " + GARA.T_ID_GARA + " = " + numGara;
		}

		if (cfRUP != null && cfRUP.trim().length() > 0) {
//	PP 20.11.2015 condizione errata per OSSN		whereCond += " AND "  + INFO_AGGIUDICAZIONI.CF_RUP + " = '" + cfRUP + "'";
			whereCond += " AND (" + GARA.T_CF_UTENTE + " = '" + cfRUP + "'" + " OR " + INFO_AGGIUDICAZIONI.T_CF_RUP
					+ "= '" + cfRUP + "')";
		}

		if (dataScadenza_a != null) {
			whereCond += " AND " + LOTTO.T_DATA_SCADENZA_PAGAMENTI + " <= '" + dataScadenza_a + "'";
		}
		if (dataScadenza_da != null) {
			whereCond += " AND " + LOTTO.T_DATA_SCADENZA_PAGAMENTI + " >= '" + dataScadenza_da + "'";
		}

		if (dataPubblicazione_a != null) {
			whereCond += " AND " + LOTTO.T_DATA_PUBBLICAZIONE + " <= '" + dataPubblicazione_a + "'";
		}
		if (dataPubblicazione_da != null) {
			whereCond += " AND " + LOTTO.T_DATA_PUBBLICAZIONE + " >= '" + dataPubblicazione_da + "'";
		}

		if (richiestaAnnullamento != null) {
			whereCond += " AND " + LOTTO.T_ID_LOTTO + ("N".equals(richiestaAnnullamento) ? " NOT IN " : " IN ")
					+ "( SELECT " + RICHIESTA_ANNULLAMENTO.ID_LOTTO + " FROM " + RICHIESTA_ANNULLAMENTO.TABLE_NAME
					+ " WHERE " + RICHIESTA_ANNULLAMENTO.T_DATA_FINE + " IS NULL )";

		}
		// solo con aggiudicazioni o senza aggiudicazioni
		if (richiestaAggiudicate != null) {
			// whereCond += " AND " + INFO_AGGIUDICAZIONI.T_ID_INFO +
			// ("S".equals(richiestaAggiudicate) ? " IS NULL " : " IS NOT NULL " );
			whereCond += " AND " + (Costanti.FLAG_VALORE_SI.equals(richiestaAggiudicate) ? "NOT" : "")
					+ " EXISTS (SELECT 1 FROM " + AGGIUDICAZIONI.TABLE_NAME + " WHERE " + AGGIUDICAZIONI.ID_INFO + " = "
					+ INFO_AGGIUDICAZIONI.T_ID_INFO + " AND " + AGGIUDICAZIONI.DATA_INIZIO_INFO + " = "
					+ INFO_AGGIUDICAZIONI.T_DATA_INIZIO_INFO + ") ";
		}

		// filtro soglie importo
		if (minSoglia != null && minSoglia.trim().length() > 0 && maxSoglia != null && maxSoglia.trim().length() > 0) {
			whereCond += " AND (" + GARA.IMPORTO_GARA + " BETWEEN " + minSoglia + " AND " + maxSoglia + " OR "
					+ LOTTO.IMPORTO_LOTTO + " BETWEEN " + minSoglia + " AND " + maxSoglia + ")";
		}

		final String GARA_ORDERBY = " order by " + GARA.T_ID_GARA + " ASC"; // LOTTO.T_CIG ; // PP LOTTO.T_ID_GARA + ","
																			// +

		String query = selectRicerca + whereCond + GARA_ORDERBY;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		System.out.println("TECHNIS getGaraListAccQ: " + query);

		query = "SELECT " + query;
		logger.debug("Visualizzazione Ricerca Gare [" + query + "]");
		logger.info("Visualizzazione Ricerca Gare [" + query + "]");
		// query
		try {
			pstmt = activeConnection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			logger.debug("esecuzione prima exec");
			rs = pstmt.executeQuery();
			logger.debug("fine esecuzione prima query");

			/**
			 * X-XX: il false nel costruttore permette una iterazione parziale del RecordSet
			 */
			// TableBean result = new TableBean (false, rs, startRow, count, false);
			// new
			logger.debug("tablebean exec");
			// mi serve come parametro anche
			TableBean result = new GaraTableBean(rs, startRow, count, true, logger, true);

			// Ordiniamo la tablebean perche' non ci piace l'ordine decrescente dei lotti
			// dettato dalla query
			// che purtroppo non e' modificabile
//    		result.sortByGaraAndLotto();

			logger.debug("tablebean exec end");
			// close(rs1,pstmt1);
			logger.debug("Risultato per la query [" + selectRicerca + "] Tuple [" + result.getFullSize() + "]");
			logger.info("Risultato per la query [" + selectRicerca + "] Tuple [" + result.getFullSize() + "]");
			return (TableBean) result;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, pstmt);
		}
		// restituisco la connessione cosi come mi � stata data

//		logger.debug( "Risultato per la query [" + selectRicerca + "] Tuple [" + result.getTableSize() + "]");
// end
//		result.setFullSize(sizeOfReturnedRecords);	
	}

	// SQL SERVER
	// private final String TOP = "SELECT * FROM ( SELECT TOP $1 * FROM (SELECT
	// DISTINCT TOP $2 ";
	/** @deprecated */
	private final String TOP = "SELECT * FROM ( SELECT TOP $1 * FROM ( $2 $3 ";
	/** @deprecated */
	private final String TOP_ORDERBY = " ) as lotto_set order by id_gara desc, id_lotto desc) as lotto_limited order by id_gara asc";
	// MYSQL
	// il primo parametro rappresenta il numero del record da cui iniziare il
	// secondo il numero di record voluti
	/** @deprecated */
	private final String LIMIT_ORDERBY = " ASC LIMIT $1,$2";

	/**
	 * metodo specializzato per il db mssql
	 * 
	 * @param query
	 * @param insieme
	 * @param nrRecords
	 * @param queryRup
	 * @return
	 * @deprecated
	 */
	private String mssql_onlyXrecords(String query, int insieme, int nrRecords, boolean queryRup, boolean isLast) {
		// 11 record
		String selectSyntax = "select top";
		if (queryRup) {
			selectSyntax = "select distinct top";
		}
		// se e' l'ultima pagina si deve comportare in modo diverso altrimenti va' a
		// pescare record della pagina precedente, e non ritorna i record corretti.
		if (!isLast) {
			query = TOP.replace("$1", String.valueOf(nrRecords)).replace("$2", selectSyntax).replace("$3",
					String.valueOf(insieme + nrRecords)) + query;
		} else {
			query = TOP.replace("$1", String.valueOf(nrRecords)).replace("$2", selectSyntax).replace("$3",
					String.valueOf(insieme + nrRecords)) + query;
		}
		query = query + TOP_ORDERBY;
		return query;
	}

	/**
	 * metodo specializzato per il db mysql
	 * 
	 * @param query
	 * @param insieme
	 * @param nrRecords
	 * @param queryRup
	 * @return
	 * @deprecated
	 */
	private String mysql_onlyXrecords(String query, int insieme, int nrRecords, boolean queryRup) {
		String selectSyntax = "select";
		if (queryRup) {
			selectSyntax = "select distinct";
		}
		query = selectSyntax + query;
		query = query
				+ LIMIT_ORDERBY.replace("$1", String.valueOf(insieme)).replace("$2", String.valueOf(nrRecords + 1));
		return query;
	}

	/**
	 * Creato per gestire gli apici nel campo di ricerca per gestire token
	 * contenenti apostrofo
	 */

	private String getCleanToken(String currentToken) {
		/*
		 * if ( currentToken.contains("\'") ) { int apostrophePosition =
		 * currentToken.indexOf("'"); String firstPart = currentToken.substring(0,
		 * apostrophePosition); String second =
		 * currentToken.substring(apostrophePosition + 1); currentToken = firstPart +
		 * "''" + second; }
		 */
		currentToken = currentToken.replace("'", "''");
		return currentToken;
	}

	/********************************************************************************************
	 * Recupera i dati relativi all'aggiudicazione prima della insert
	 * 
	 * @param id_lotto String
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getDatiPreInsertAgg(String id_lotto) throws SQLException {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(QUERY_SELECT_PRE_INSERT);
			stmt.setLong(1, Long.parseLong(id_lotto));

			rs = stmt.executeQuery();

			TableBean dati = new TableBean(rs);

			return dati;
		} finally {
			close(rs, stmt);
		}
	}

	public static String UPDATE_GARA_INF = "UPDATE " + GARA.TABLE_NAME + " SET " + GARA.TIPO_SCHEDA_GARA + " = ? ,"
			+ GARA.ID_MODO_REAL + " = ? " + " WHERE " + GARA.ID_GARA + " = ? ";

	/************************************************************************
	 * Update dei campi modificati da scheda dati comuni
	 * 
	 * @param bean   InfoComuniBean
	 * @param idGara long
	 * @return void
	 * @throws SQLException
	 */
	public void updateCampiInfoComuni(InfoComuniBean bean, long idGara) throws SQLException {
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(UPDATE_GARA_INF);
			int index = 1;

			stmt.setString(index++, bean.getFlagEnteSpeciale());

			if (bean.getID_MODO_REAL() == 0)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, bean.getID_MODO_REAL());

			stmt.setLong(index++, idGara);

			int nrow = stmt.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	public static String UPDATE_GARA_SA = "UPDATE " + GARA.TABLE_NAME + " SET " + GARA.ID_STAZIONE_APPALTANTE + " = ? ,"
			+ GARA.DENOM_STAZIONE_APPALTANTE + " = ?, " + GARA.CF_AMMINISTRAZIONE + " = ? ,"
			+ GARA.DENOM_AMMINISTRAZIONE + " = ?, " + GARA.ID_OSSERVATORIO + " = ?, " +
			// gm nuovo campo simog3.06
			GARA.ID_MOTIVO_VAR + " = ? " + " WHERE " +

			GARA.ID_GARA + " = ? ";

	public static String UPDATE_DATI_COMUNI_GARA_SA = "UPDATE " + INFO_AGGIUDICAZIONI.TABLE_NAME + " SET "
			+ INFO_AGGIUDICAZIONI.CF_AMM + " = ? ," + INFO_AGGIUDICAZIONI.DEN_AMM + " = ?, " + INFO_AGGIUDICAZIONI.CF_SA
			+ " = ? ," + INFO_AGGIUDICAZIONI.DEN_SA + " = ? ," + INFO_AGGIUDICAZIONI.CODICE_CC + " = ? ,"
			+ INFO_AGGIUDICAZIONI.DENOM_CC + " = ? " +

			" WHERE (" +

			INFO_AGGIUDICAZIONI.ID_STATO + " = " + StatiScheda.IN_DEFINIZIONE + " or " + INFO_AGGIUDICAZIONI.ID_STATO
			+ " = " + StatiScheda.CONFERMATO + ") and " + INFO_AGGIUDICAZIONI.ID_LOTTO + " in ( select "
			+ LOTTO.ID_LOTTO + " from " + LOTTO.TABLE_NAME + " where " + LOTTO.ID_GARA + " =? )";

	// Utilizzato insieme ad Updata_data_lotti ed update_data_perfezionamento per
	// sbloccare una gara
	private final String UPDATE_DATA_GARA = "UPDATE " + GARA.TABLE_NAME + " SET " + GARA.T_DATA_PERFEZIONAMENTO_BANDO
			+ " = ?, " + GARA.ID_PUBBLICAZIONE + " = ?, " + GARA.T_DATA_INIZIO_PUBB + " = ?, "
//		 PP azzero anche il contributo e ripristono lo stato
			+ GARA.IMPORTO_SA_GARA + " = null, "
			// Ticket#2015041310001957 PP mancava data_comun e data conferma gara
			+ GARA.DATA_COMUN + " = null, " + GARA.DATA_CONFERMA_GARA + " = null, " + GARA.ID_STATO + " = "
			+ StatiScheda.IN_DEFINIZIONE + " WHERE " + GARA.T_ID_GARA + " = ?";

	// Aggiornamento modalità di realizzazione gara uguale ad accordo quadro
	private final String UPDATE_REALIZZAZIONE_GARA = "UPDATE " + GARA.TABLE_NAME + " SET " + GARA.T_ID_MODO_REAL
			+ " = ? " + " WHERE " + GARA.T_ID_GARA + " = ?";

	public boolean updateStazioneAppaltante(Long idGara, Long idMotivo, StazioneAppaltante selezione)
			throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			int index = 1;
			stmt = activeConnection.prepareStatement(UPDATE_GARA_SA);

			stmt.setString(index++, selezione.getIdUfficio());
			stmt.setString(index++, selezione.getDenominazione());

			stmt.setString(index++, selezione.getCodiceFiscaleAmministrazione());
			stmt.setString(index++, selezione.getDenominazioneAmministrazione());

			stmt.setString(index++, selezione.getAmministrazione().getId_osservatorio());

			// gm nuovo campo simog3.06
			stmt.setObject(index++, idMotivo);

			stmt.setObject(index++, idGara);

			if (stmt.executeUpdate() > 0) {
				stmt.close();
				index = 1;
				stmt = activeConnection.prepareStatement(UPDATE_DATI_COMUNI_GARA_SA);
				stmt.setString(index++, selezione.getCodiceFiscaleAmministrazione());
				stmt.setString(index++, selezione.getDenominazioneAmministrazione());
				stmt.setString(index++, selezione.getCodiceFiscaleAmministrazione());
				stmt.setString(index++, selezione.getDenominazioneAmministrazione());

				stmt.setString(index++, selezione.getIdUfficio());
				stmt.setString(index++, selezione.getDenominazione());

				stmt.setLong(index++, idGara);
				stmt.executeUpdate();

			} else
				return false;

			return true;
		} finally {
			close(rs, stmt);
		}
	}

//	@see cancellabilita e modificabilita gara con / senza cig dipendenti (accordo quadro)
//	
//	private final String selezionaTutteLeGarePerIlCigIndicato = 
//		"SELECT COUNT(1) as numero_figli "+
//		" FROM GARA " +
//		" WHERE " +
////		GARA.ID_MODO_REAL + " IN ("+Costanti.MODOREAL_ADESIONE+","+Costanti.MODOREAL_ADESIONE_NOCOMPET+") " +
////		" AND " + 
//		GARA.CIG_ACC_QUADRO + " = ?";
//	
//	
//	/**
//	 * Metodo che si occupa di controllare se esistono dei cig dipendenti (figli) al cig di cui argomento
//	 * NOTA: la query controlla solamente quelle gara che hanno come valori della colonna "id_modo_real" = 2 o 11
//	 * ovvero adesione ad accordo quadro
//	 * TO-DO: RITORNARE LA LISTA DELLE GARE / LOTTI REFERENZIATI
//	 * 
//	 * @param cig
//	 * @return
//	 * @throws SQLException
//	 */
//	public boolean controllaEsistenzaCigFigli(String cig) throws SQLException{
//		
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		try{
//			stmt = activeConnection.prepareStatement(selezionaTutteLeGarePerIlCigIndicato);
//			logger.debug("Query per il controllo di esistenza cig figli per il cig: " + selezionaTutteLeGarePerIlCigIndicato);
//			stmt.setString(1, cig);
//			
//			rs = stmt.executeQuery();
//			if(rs.next()){
//				int numero_figli = rs.getInt("numero_figli");
//				if(numero_figli > 0) return true;
//			}
//			return false;
//		}
//		finally{
//			close(rs, stmt);
//		}		
//	}

	public void sbloccaGara(long sessionIdGara) throws SQLException {

		int idx = 0;

		PreparedStatement updateGaraFunction = null;

		try {

			updateGaraFunction = activeConnection.prepareStatement(UPDATE_DATA_GARA);
			updateGaraFunction.setNull(++idx, Types.VARCHAR);
			updateGaraFunction.setNull(++idx, Types.INTEGER);
			updateGaraFunction.setNull(++idx, Types.VARCHAR);

			updateGaraFunction.setLong(++idx, sessionIdGara);

			updateGaraFunction.executeUpdate();
			logger.debug("Eseguita query [" + UPDATE_DATA_GARA + "] per idGara [" + sessionIdGara + "]");

		} finally {
			try {
				updateGaraFunction.close();
			} catch (Exception e) {
			}
			updateGaraFunction = null;
		}

	}

	public void modificaRealizzazioneGara(long sessionIdGara, int modoReal) throws SQLException {

		int idx = 0;

		PreparedStatement updateGaraFunction = null;

		try {
			updateGaraFunction = activeConnection.prepareStatement(UPDATE_REALIZZAZIONE_GARA);
			updateGaraFunction.setInt(++idx, modoReal);

			updateGaraFunction.setLong(++idx, sessionIdGara);

			updateGaraFunction.executeUpdate();
			logger.debug("Eseguita query [" + UPDATE_REALIZZAZIONE_GARA + "] per idGara [" + sessionIdGara + "]");

		} finally {
			try {
				updateGaraFunction.close();
			} catch (Exception e) {
			}
			updateGaraFunction = null;
		}

	}

	// Aggiornamento importo gara
	private final String UPDATE_IMPORTO_GARA = "UPDATE " + GARA.TABLE_NAME + " SET " + GARA.T_IMPORTO_GARA + " = ? "
			+ " WHERE " + GARA.T_ID_GARA + " = ?";

	public void updateImportoGara(BigDecimal importoGara, long sessionIdGara) throws SQLException {

		int idx = 0;
		PreparedStatement updateGaraFunction = null;

		try {
			updateGaraFunction = activeConnection.prepareStatement(UPDATE_IMPORTO_GARA);
			updateGaraFunction.setBigDecimal(++idx, importoGara);
			updateGaraFunction.setLong(++idx, sessionIdGara);

			updateGaraFunction.executeUpdate();
			logger.debug("Eseguita query [" + UPDATE_IMPORTO_GARA + "] per idGara [" + sessionIdGara + "]");
		} finally {
			try {
				updateGaraFunction.close();
			} catch (Exception e) {
			}
			updateGaraFunction = null;
		}
	}

	/**
	 * verifica se l'amministrazione è un organo costituzionale
	 * 
	 * @param cfAmm
	 * @param data  validita'
	 * @return true se è organo costituzionale
	 * @throws SQLException
	 * @throws Exception
	 */
	public boolean isOrganoCost(String cfAmm, Object data) throws SQLException, Exception {
		// se la funzione non è attiva ritorna false sempre
		if (!SimogFlags.isOrganiCostActive())
			return false;

		try {

			Map<String, String> tab = getTipologica(ORGANI_COSTITUZIONALI.TABLE_NAME, ORGANI_COSTITUZIONALI.CODICE,
					ORGANI_COSTITUZIONALI.DESCRIZIONE, ORGANI_COSTITUZIONALI.DATA_FINE_VALIDITA, data);

			return tab.containsKey(cfAmm);
		} catch (Exception e) {
			throw e;
		}
	}

	/*********************************************************************************************
	 * restituisce una lista di gara per le quali deve essere ricalcolato il
	 * contributo
	 * 
	 * @return List&lt;Lotto&gt;
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Gara> getGareNoContributo() throws SQLException, Exception {

		String QUERY_SELECT_GARE_NOCONTRIB = " SELECT * " + " FROM " + GARA.TABLE_NAME + " WHERE "
				+ GARA.DATA_CANCELLAZIONE_GARA + " IS NULL " + " AND " + GARA.IMPORTO_SA_GARA + " = "
				+ Costanti.IMPORTO_FUORI_SCALA;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Gara igb = null;
		List<Gara> lOut = new ArrayList<Gara>();

		try {
			pstmt = activeConnection.prepareStatement(QUERY_SELECT_GARE_NOCONTRIB);
			rs = pstmt.executeQuery();
			igb = new Gara();

			while (rs.next()) {
				igb = new Gara();
				fillGara(igb, rs);
				lOut.add(igb);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		}

		return lOut;
	}

	/*********************************************************************************************
	 * restituisce la lista delle SA desunte dalle gare per una data amministrazione
	 * 
	 */
//    public Map <String, String>  getSAList(String cfAmm)throws SQLException,Exception{
//
//       String QUERY_SELECT_SA =
//             " SELECT " + GARA.ID_STAZIONE_APPALTANTE + ","
//             + "max(" + GARA.DENOM_STAZIONE_APPALTANTE + ")"
//             + " FROM " + GARA.TABLE_NAME + " with(nolock) " 
//             + " WHERE " 
//             + GARA.CF_AMMINISTRAZIONE + " = ? " 
//             + " group by  " + GARA.ID_STAZIONE_APPALTANTE;
//       
//       PreparedStatement pstmt = null; 
//       ResultSet rs = null;
//       Map <String, String> lOut = new HashMap<String, String>();
//
//        try{
//            pstmt = activeConnection.prepareStatement(QUERY_SELECT_SA);
//            pstmt.setString(1, cfAmm);
//            rs = pstmt.executeQuery();
//            
//            while(rs.next()){
//                lOut.put(rs.getString(1) , rs.getString(2));
//            }
//        }
//        finally{
//           if(rs != null) rs.close();
//           if(pstmt != null) pstmt.close();
//        }
//
//        return lOut;    
//    }

	public Map<String, String> getSAList(Hashtable lista) throws SQLException, Exception {

		String whereCond = "";
		String QUERY_SELECT_SA = " SELECT " + GARA.ID_STAZIONE_APPALTANTE + "," + "max("
				+ GARA.DENOM_STAZIONE_APPALTANTE + ")" + " FROM " + GARA.TABLE_NAME + " with(nolock) ";

//             + " WHERE " 
//             + GARA.CF_AMMINISTRAZIONE + " = ? " 

		String GROUPBY = " group by  " + GARA.ID_STAZIONE_APPALTANTE;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String, String> lOut = new HashMap<String, String>();

		try {

			if (lista.size() > 0) {
				whereCond = " WHERE " + addInCondition(GARA.CF_AMMINISTRAZIONE, lista.keys());
			}

			pstmt = activeConnection.prepareStatement(QUERY_SELECT_SA + whereCond + GROUPBY);

			logger.debug("GetSAList [" + QUERY_SELECT_SA + whereCond + GROUPBY + "]");

			pstmt = fillPstmt(pstmt, 1, lista);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				lOut.put(rs.getString(1), rs.getString(2));
			}
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		}

		return lOut;
	}

	/***************************************************************************************************
	 * Recupera i dati delle gare
	 * 
	 * @return TableBean
	 * @throws SQLException
	 */

	public TableBean getElencoCig(Hashtable lista, String cfRUP, Hashtable listaSAAbilitato, String dataPubblicazione_a,
			String dataPubblicazione_da, int startRow, int count) throws SQLException {

		String whereCond = "";
		//TICKET ALM 14697
//		String selectRicerca = QUERY_RUP + ", " + GARA.T_CF_UTENTE + ", " + SOGGETTI_RESPONSABILI.COGNOME + ", "
		String selectRicerca = QUERY_RUP + ", " + SOGGETTI_RESPONSABILI.COGNOME + ", "
				+ SOGGETTI_RESPONSABILI.NOME + QUERY_RUP_FROM + " left outer join " + SOGGETTI_RESPONSABILI.TABLE_NAME
				+ "   on " + SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE + " = " + GARA.T_CF_UTENTE + "     and "
				+ SOGGETTI_RESPONSABILI.DATA_FINE_RES + " is null" + " where " + LOTTO.T_CIG + " is not null ";

		if (lista.size() > 0) {
			whereCond = " AND " + addInCondition(GARA.T_CF_AMMINISTRAZIONE, lista.keys());
		}

		if (cfRUP != null && cfRUP.trim().length() > 0) {
			whereCond += " AND " + GARA.T_CF_UTENTE + " = '" + cfRUP + "'";
		}

		if (dataPubblicazione_a != null) {
			whereCond += " AND " + LOTTO.T_DATA_CREAZIONE_LOTTO + " <= '" + dataPubblicazione_a + "'";
		}
		if (dataPubblicazione_da != null) {
			whereCond += " AND " + LOTTO.T_DATA_CREAZIONE_LOTTO + " >= '" + dataPubblicazione_da + "'";
		}
		if (listaSAAbilitato.size() > 0) {
			whereCond += " AND " + addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys());
		}

		whereCond+= " AND "+GARA.T_CF_AMMINISTRAZIONE+" <> 'N/A' ";
		
		final String GARA_ORDERBY = " order by " + GARA.T_CF_AMMINISTRAZIONE + "," + GARA.T_ID_STAZIONE_APPALTANTE + ","
				+ GARA.T_CF_UTENTE + "," + GARA.T_ID_GARA + "," + LOTTO.T_ID_LOTTO;

		String query = "SELECT " + selectRicerca + whereCond + GARA_ORDERBY;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		logger.debug("Visualizzazione Elenco Gare [" + query + "]");
        System.out.println("TECHNIS ELENCO GARE "+query);
		try {
			pstmt = activeConnection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int posCounter = 1;

			pstmt = fillPstmt(pstmt, posCounter, lista);

			pstmt = fillPstmt(pstmt, posCounter + lista.size(), listaSAAbilitato);

			rs = pstmt.executeQuery();
			TableBean result = new GaraTableBean(rs, startRow, count, true, logger, false);

			return (TableBean) result;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, pstmt);
		}
	}

	public TableBean getElencoCigIntegrazioneCup(String cfAmm, String cfRUP, Hashtable listaSAAbilitato,
			String dataPubblicazione_a, String dataPubblicazione_da, String dataAttivazioneMev, String orderField,
			boolean ascDesc, int startRow, int count, String cig, boolean ignoraFlagCup) throws SQLException {

		String whereCond = "";

		String TIPO_APPALTO_QUERY = "(SELECT MAX(" + TIPI_APPALTI.T_DESCRIZIONE + ")" + " FROM "
				+ TIPO_APPALTO_AGG.TABLE_NAME + ", " + TIPI_APPALTI.TABLE_NAME + " WHERE "
				+ TIPO_APPALTO_AGG.T_ID_APPALTO + " = " + TIPI_APPALTI.T_ID_APPALTO + " AND "
				+ TIPO_APPALTO_AGG.T_ID_LOTTO + " = " + LOTTO.T_ID_LOTTO + ") AS TIPO_APPALTO_QUALSIASI";

		String selectRicerca = QUERY_RUP + ", " + GARA.T_CF_UTENTE + ", "
				+ buildISNULL(SOGGETTI_RESPONSABILI.COGNOME, "") + ", " + buildISNULL(SOGGETTI_RESPONSABILI.NOME, "")
				+ ", " + TIPO_APPALTO_QUERY + QUERY_RUP_FROM + " left outer join " + SOGGETTI_RESPONSABILI.TABLE_NAME
				+ "   on " + SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE + " = " + GARA.T_CF_UTENTE + "     and "
				+ SOGGETTI_RESPONSABILI.DATA_FINE_RES + " is null" + " where " + LOTTO.T_CIG + " is not null " + " AND "
				+ LOTTO.TIPO_CONTRATTO_LOTTO + " = '" + Costanti.TIPO_SCHEDA_LAVORI + "'" + " AND "
				+ GARA.DATA_CREAZIONE + " < " + dataAttivazioneMev + " AND " + LOTTO.DATA_PUBBLICAZIONE + " IS NOT NULL"
				+ (ignoraFlagCup ? "" : " AND " + LOTTO.FLAG_CUP + " IS NULL") + " AND NOT EXISTS (" + " SELECT 1 FROM "
				+ FINE_LAVORI.TABLE_NAME + "," + AGGIUDICAZIONI.TABLE_NAME + " WHERE " + FINE_LAVORI.T_ID_AGGIUDICAZIONE
				+ " = " + AGGIUDICAZIONI.T_ID_AGGIUDICAZIONE + " AND " + AGGIUDICAZIONI.T_ID_INFO + " = "
				+ INFO_AGGIUDICAZIONI.T_ID_INFO + " AND " + AGGIUDICAZIONI.T_ID_STATO + " IN (1, 2)" + " AND "
				+ AGGIUDICAZIONI.T_MODALITA_RIAGGIUDICAZIONE + " IS NULL" + " AND " + FINE_LAVORI.T_ID_STATO + " = "
				+ StatiScheda.CONFERMATO_STRING + " AND " + FINE_LAVORI.T_DATA_ULTIMAZIONE + " > '2012-02-21 00:00:00'"
				+ ")";

		if (cfAmm != null && cfAmm.trim().length() > 0) {
			whereCond += " AND " + GARA.T_CF_AMMINISTRAZIONE + " = '" + cfAmm + "'";
		}

		if (cfRUP != null && cfRUP.trim().length() > 0) {
			whereCond += " AND " + GARA.T_CF_UTENTE + " = '" + cfRUP + "'";
		}

		if (dataPubblicazione_a != null) {
			whereCond += " AND " + LOTTO.T_DATA_CREAZIONE_LOTTO + " <= '" + dataPubblicazione_a + "'";
		}
		if (dataPubblicazione_da != null) {
			whereCond += " AND " + LOTTO.T_DATA_CREAZIONE_LOTTO + " >= '" + dataPubblicazione_da + "'";
		}
		if (listaSAAbilitato.size() > 0) {
			whereCond += " AND " + addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys());
		}

		if (cig != null) {
			String lCig = CIGBean.getCIGPart(cig);
			whereCond += " AND " + LOTTO.T_CIG + " = " + lCig;
		}

		final String GARA_ORDERBY = " ORDER BY "
				+ (orderField == null ? LOTTO.T_DATA_CREAZIONE_LOTTO : (orderField + (ascDesc ? " ASC" : " DESC")));

		String query = "SELECT " + selectRicerca + whereCond + GARA_ORDERBY;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		logger.debug("Visualizzazione Elenco Gare [" + query + "]");
 
		try {
			pstmt = activeConnection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int posCounter = 1;
			pstmt = fillPstmt(pstmt, posCounter, listaSAAbilitato);
			rs = pstmt.executeQuery();
			TableBean result = new GaraTableBean(rs, startRow, count, true, logger, false);

			return (TableBean) result;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, pstmt);
		}
	}

	/********************************************************************************************
	 * cancella le categorie
	 * 
	 * @param idLotto String per l'idGara
	 * @return int - retsituisce il numero di elementi cancellati
	 * @throws SQLException
	 */
	private int deleteCategorie(long idGara) throws SQLException {

		PreparedStatement psDelete = null;

		try {
			psDelete = activeConnection.prepareStatement(CANCELLA_EAGG);

			psDelete.setLong(1, idGara);

			int executionResult = psDelete.executeUpdate();
			return executionResult;
		} finally {
			try {
				psDelete.close();
			} catch (Exception e) {
			}
			psDelete = null;
		}
	}

	/***********************************************************************************************
	 * aggiorna categorie
	 * 
	 * @param idLotto               String per l'id del lotto
	 * @param categoriaScorporabile String[]
	 * @return int - restituisce il numero di elementi inseriti
	 * @throws SQLException
	 */
	public int updateGaraCategorie(long idGara, List<String> list) throws SQLException {

		int result = 0;
		PreparedStatement insertStatement = null;

		try {

			deleteCategorie(idGara);

			insertStatement = activeConnection.prepareStatement(INSERT_EAGG);

			for (int i = 0; i < list.size(); i++) {

				insertStatement.setLong(1, Long.parseLong((String) list.get(i)));
				insertStatement.setLong(2, idGara);

				result += insertStatement.executeUpdate();
			}

			return result;
		} finally {
			try {
				insertStatement.close();
			} catch (Exception e) {
			}
			insertStatement = null;
		}
	}

	/********************************************************************************************
	 * ricerca le categorie tramite id gara, le inserisce poi nella HashMap con id
	 * categoria e descrizione
	 * 
	 * @param idGara long
	 * @return HashMap&lt;String, String&gt;
	 * @throws SQLException
	 */
	public List<String> getCategorie(long idgara) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<String> cat = new ArrayList<String>();

		try {
			pstmt = activeConnection.prepareStatement(GET_EAGG);
			pstmt.setLong(1, idgara);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cat.add(String.valueOf(rs.getLong(1)));
			}

			return (cat);

		} finally {
			close(rs, pstmt);
		}
	}

	// TICKET ALM #659 - 3.04.4
	final String UPDATE_GARA_DELEGATA_PRESA_IN_CARICO = "UPDATE " + GARA.TABLE_NAME + " SET " + GARA.CF_UTENTE + " = ?,"
			+ GARA.ID_STAZIONE_APPALTANTE + " = ?," + GARA.DENOM_STAZIONE_APPALTANTE + " = ?," + GARA.CF_AMMINISTRAZIONE
			+ " = ?," + GARA.DENOM_AMMINISTRAZIONE + " = ?, " + GARA.ID_OSSERVATORIO + " = ? WHERE " + GARA.ID_GARA
			+ " = ?";

	/**
	 * Effettua la presa in carico di una gara delagata ad altra SA
	 */
	public void eseguiPresaInCaricoGaraDelegata(StazioneAppaltante sa, String cfUtente, String idGara)
			throws SQLException {

		PreparedStatement updateStatement = null;
		try {

			updateStatement = activeConnection.prepareStatement(UPDATE_GARA_DELEGATA_PRESA_IN_CARICO);
			updateStatement.setString(1, cfUtente);
			updateStatement.setString(2, sa.getIdUfficio());
			updateStatement.setString(3, sa.getDenominazione());
			updateStatement.setString(4, sa.getAmministrazione().getCodiceFiscale());
			updateStatement.setString(5, sa.getAmministrazione().getDenominazioneAmministrazione());
			updateStatement.setString(6, sa.getAmministrazione().getId_osservatorio());
			updateStatement.setLong(7, Long.parseLong(idGara));

			updateStatement.executeUpdate();

		} finally {
			try {
				updateStatement.close();
			} catch (Exception e) {
			}
			updateStatement = null;
		}
	}
	
	public void eseguiPresaInCaricoInfoAggiudicazioni(StazioneAppaltante sa, 
														String cfUtente, 
														String idGara) throws SQLException  {
	
		final String UPDATE_INFO_AGGIUDICAZIONI = "UPDATE "+INFO_AGGIUDICAZIONI.TABLE_NAME+ " SET "+INFO_AGGIUDICAZIONI.CODICE_CC + " = ?,"
			                                                                             	+ INFO_AGGIUDICAZIONI.DENOM_CC+ " = ?,"
				                                                                            + INFO_AGGIUDICAZIONI.CF_AMM+" = ?,"
			                                                                             	+ INFO_AGGIUDICAZIONI.DEN_AMM+" = ?,"
			                                                                             	+ INFO_AGGIUDICAZIONI.DEN_SA+" = ?,"
				                                                                            + INFO_AGGIUDICAZIONI.CF_SA+" = ?,"
				                                                                            + INFO_AGGIUDICAZIONI.CF_RUP+" = ?"
				                                    +" FROM "+INFO_AGGIUDICAZIONI.TABLE_NAME
				                                    +" INNER JOIN "+LOTTO.TABLE_NAME+" ON "+LOTTO.T_ID_LOTTO+" = "+INFO_AGGIUDICAZIONI.T_ID_LOTTO
				                                    +" INNER JOIN "+GARA.TABLE_NAME+" ON "+GARA.T_ID_GARA+" = "+LOTTO.T_ID_GARA
				                                    +" WHERE "+GARA.T_ID_GARA+" = ? AND "+INFO_AGGIUDICAZIONI.T_ID_STATO+" = ?";
		PreparedStatement updateStatement = null;
		try {
           int index=1;
			updateStatement = activeConnection.prepareStatement(UPDATE_INFO_AGGIUDICAZIONI);
			updateStatement.setString(index++, sa.getIdUfficio());
			updateStatement.setString(index++, sa.getDenominazione());
			updateStatement.setString(index++, sa.getAmministrazione().getCodiceFiscale());
			updateStatement.setString(index++, sa.getAmministrazione().getDenominazioneAmministrazione());
			updateStatement.setString(index++, sa.getAmministrazione().getDenominazioneAmministrazione());
			updateStatement.setString(index++, sa.getAmministrazione().getCodiceFiscale());
			updateStatement.setString(index++, cfUtente);
			
			updateStatement.setLong(index++, Long.parseLong(idGara));
			updateStatement.setInt(index++, StatiScheda.CONFERMATO);
			updateStatement.executeUpdate();

		} finally {
			try {
				updateStatement.close();
			} catch (Exception e) {
			}
			updateStatement = null;
		}
	}
	
	
	
	public void eseguiPresaInCaricoInfoAggiudicazioniSingoloCIG(StazioneAppaltante sa, 
			String cfUtente, 
			Long idLotto, 
			boolean propostaDiAgg, 
			boolean fromWeb) throws SQLException  {

final String UPDATE_INFO_AGGIUDICAZIONI = "UPDATE "+INFO_AGGIUDICAZIONI.TABLE_NAME+ " SET "+INFO_AGGIUDICAZIONI.CODICE_CC + " = ?,"
                                             	+ INFO_AGGIUDICAZIONI.DENOM_CC+ " = ?,"
                                                + INFO_AGGIUDICAZIONI.CF_AMM+" = ?,"
                                             	+ INFO_AGGIUDICAZIONI.DEN_AMM+" = ?,"
                                             	+ INFO_AGGIUDICAZIONI.DEN_SA+" = ?,"
                                                + INFO_AGGIUDICAZIONI.CF_SA+" = ?,"
                                                + INFO_AGGIUDICAZIONI.CF_RUP+" = ?"
	                                                +(propostaDiAgg && fromWeb ? ", "+INFO_AGGIUDICAZIONI.ID_STATO+"= ?, "+INFO_AGGIUDICAZIONI.DATA_FINE_INFO+"=NULL" : "")
	        +" FROM "+INFO_AGGIUDICAZIONI.TABLE_NAME
	        +" WHERE "+INFO_AGGIUDICAZIONI.T_ID_LOTTO+" = ? AND "+INFO_AGGIUDICAZIONI.T_ID_STATO+" IN (?,?)";
				PreparedStatement updateStatement = null;
				try {
					int index=1;
					updateStatement = activeConnection.prepareStatement(UPDATE_INFO_AGGIUDICAZIONI);
					updateStatement.setString(index++, sa.getIdUfficio());
					updateStatement.setString(index++, sa.getDenominazione());
					updateStatement.setString(index++, sa.getAmministrazione().getCodiceFiscale());
					updateStatement.setString(index++, sa.getAmministrazione().getDenominazioneAmministrazione());
					updateStatement.setString(index++, sa.getAmministrazione().getDenominazioneAmministrazione());
					updateStatement.setString(index++, sa.getAmministrazione().getCodiceFiscale());
					updateStatement.setString(index++, cfUtente);
					
					//SOLOWEB: Se c'e' la delega di tipo Proposta di Aggiudicazione, riporta la scheda in stato In Definizione
					if(propostaDiAgg && fromWeb) 
					updateStatement.setInt(index++, StatiScheda.IN_DEFINIZIONE);
					
					updateStatement.setLong(index++, idLotto);
					updateStatement.setInt(index++, StatiScheda.IN_DEFINIZIONE);
					updateStatement.setInt(index++, StatiScheda.CONFERMATO);
					updateStatement.executeUpdate();
				
				} finally {
				try {
				    updateStatement.close();
			  } catch (Exception e) {
			}
			updateStatement = null;
		}
}
	
	private String prepareAmmNotIn(Hashtable amministrazioni, String query) {
		if (amministrazioni.entrySet().size() > 0) {
			query += " AND " + FUNZIONI_DELEGATE_GARA.T_CF_AMM_AGENTE + " IN (";

			Iterator it2 = amministrazioni.entrySet().iterator();
			while (it2.hasNext()) {
				Map.Entry pair = (Map.Entry) it2.next();
				query += "'" + pair.getKey() + "'";
				if (it2.hasNext())
					query += ",";
			}
			query += ")";

			query += " AND " + GARA.T_CF_AMMINISTRAZIONE + " NOT IN (";

			Iterator it = amministrazioni.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				query += "'" + pair.getKey() + "'";
				if (it.hasNext())
					query += ",";
			}
			query += ") ";

		}
		return query;
	}

	private String prepareAmmIn(Hashtable amministrazioni, String query) {
		if (amministrazioni.entrySet().size() > 0) {

			query += " AND " + GARA.T_CF_AMMINISTRAZIONE + " IN (";

			Iterator it = amministrazioni.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				query += "'" + pair.getKey() + "'";
				if (it.hasNext())
					query += ",";
			}
			query += ") ";

		}
		return query;
	}

	public boolean isGaraAccordoQuadroNonDiCompetenza(String idGara) {
		boolean res = false;
		String QUERY_ID_GARA_COMP = "SELECT " + GARA.ID_GARA + " FROM " + GARA.TABLE_NAME + " WHERE " + GARA.ID_GARA
				+ " = ? AND " + GARA.ID_OSSERVATORIO + " = '099' " + "AND " + GARA.ID_MODO_REAL + " IN ("
				+ Costanti.MODOREAL_ACCORDO + "," + Costanti.MODOREAL_ACCORDO_QUADRO + ","
				+ Costanti.MODOREAL_CONVENZIONE + ")";

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			try {

				stmt = activeConnection.prepareStatement(QUERY_ID_GARA_COMP);
				stmt.setInt(1, Integer.parseInt(idGara));
				rs = stmt.executeQuery();

				if (rs.next())
					res = true;

				// while(rs.next())
				// res.put(rs.getString(1), rs.getString(1));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			close(rs, stmt);
		}

		return res;
	}
	
	public boolean isGaraPostDelega(String idGara,String cig,Hashtable uffici) {
		boolean res = false;
		String query = "SELECT " + FUNZIONI_DELEGATE_GARA.T_ID_GARA + " FROM " + FUNZIONI_DELEGATE_GARA.TABLE_NAME;
		
		if(cig!=null)
			query+=" INNER JOIN "+LOTTO.TABLE_NAME+" ON "+LOTTO.T_ID_GARA+" = "+GARA.T_ID_GARA;
		
		query+=" WHERE ";
		
		if(idGara!=null)
			query += FUNZIONI_DELEGATE_GARA.T_ID_GARA+ " = ? AND ";
		if(cig!=null)
			query += LOTTO.CIG+"+"+LOTTO.CIG_KKK+" = ? AND ";
					
		query += FUNZIONI_DELEGATE_GARA.ID_SA_DELEGATA+" IN (";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Iterator it = uffici.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			query += "'" + pair.getKey() + "'";
			if (it.hasNext())
				query += ",";
		}
		
		query += ") AND "+FUNZIONI_DELEGATE_GARA.DATA_PRESA_IN_CARICO+" IS NOT NULL";
		int index=1;
		try {
			try {

				stmt = activeConnection.prepareStatement(query);
				if(idGara!=null)
				    stmt.setInt(index++, Integer.parseInt(idGara));
				if(cig!=null)
					stmt.setString(index++, cig);
					
				rs = stmt.executeQuery();

				if (rs.next())
					res = true;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			close(rs, stmt);
		}

		return res;
	}

	// TICKET ALM #659 - 3.04.4
	public void insertFunzioniDelegateGara(Gara gara) throws SQLException {
		String INSERT_FUNZIONI_DELEGATE_QUERY = "INSERT INTO " + FUNZIONI_DELEGATE_GARA.TABLE_NAME + " " + "("
				+ FUNZIONI_DELEGATE_GARA.ID_GARA + "," 
				+ FUNZIONI_DELEGATE_GARA.FLAG_SA_AGENTE + ","
				+ FUNZIONI_DELEGATE_GARA.ID_F_DELEGATE + "," 
				+ FUNZIONI_DELEGATE_GARA.CF_AMM_AGENTE + ","
				+ FUNZIONI_DELEGATE_GARA.DEN_AMM_AGENTE + ","
				+ FUNZIONI_DELEGATE_GARA.CF_RUP_DELEGATA + ","
				+ FUNZIONI_DELEGATE_GARA.CF_AMM_DELEGATA + ","
				+ FUNZIONI_DELEGATE_GARA.DEN_AMM_DELEGATA + ","
				+ FUNZIONI_DELEGATE_GARA.ID_SA_DELEGATA + ","
				+ FUNZIONI_DELEGATE_GARA.DENOM_SA_DELEGATA + 
				")" + " VALUES(?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(INSERT_FUNZIONI_DELEGATE_QUERY);

			int index = 1;
			stmt.setLong(index++, gara.getId_Gara());
			if (gara.getFlagSAAgente() == null || "".equals(gara.getFlagSAAgente()))
				stmt.setString(index++, Costanti.FLAG_VALORE_NO);
			else
				stmt.setString(index++, gara.getFlagSAAgente());

			if (gara.getID_F_DELEGATE() == 0)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, gara.getID_F_DELEGATE());

			if (gara.getCF_AMM_AGENTE() == null || "".equals(gara.getCF_AMM_AGENTE()))
				stmt.setNull(index++, java.sql.Types.VARCHAR);
			else
				stmt.setString(index++, gara.getCF_AMM_AGENTE());

			if (gara.getDEN_AMM_AGENTE() == null || "".equals(gara.getDEN_AMM_AGENTE()))
				stmt.setNull(index++, java.sql.Types.VARCHAR);
			else
				stmt.setString(index++, gara.getDEN_AMM_AGENTE());

			stmt.setString(index++, gara.getCF_UTENTE());
			stmt.setString(index++, gara.getCF_AMMINISTRAZIONE());
			stmt.setString(index++, gara.getDENOM_AMMINISTRAZIONE());
			stmt.setString(index++, gara.getID_STAZIONE_APPALTANTE());
			stmt.setString(index++, gara.getDENOM_STAZIONE_APPALTANTE());
			
			stmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, stmt);
		}

	}
	
	//Ticket 20055
	public boolean updateFlagSospesoPubblicazioni(Long idPubblicazione) throws SQLException {
		
		boolean result = false;
		int index = 1;
		PreparedStatement stmt = null;
		try {
			stmt = activeConnection.prepareStatement(UPDATE_PUBBLICAZIONI_FLAG_SOSPESO);
			stmt.setString(index, "N");
			stmt.setLong(++index, idPubblicazione);
			
			int nrow = stmt.executeUpdate();
			result = true;
			logger.debug("Numero di righe modificate : " + nrow);
		} finally {
			if (stmt != null)
				stmt.close();
		}
		return result;
	}

	public void updateFunzioniDelegateGara(Gara gara) throws SQLException {
		String UPDATE_FUNZIONI_DELEGATE_QUERY = "UPDATE " + FUNZIONI_DELEGATE_GARA.TABLE_NAME + " " + "SET "
				                                 + FUNZIONI_DELEGATE_GARA.FLAG_SA_AGENTE + "= ?," 
				                                 + FUNZIONI_DELEGATE_GARA.ID_F_DELEGATE + "=?,"
			                                     + FUNZIONI_DELEGATE_GARA.CF_AMM_AGENTE + "=?," 
				                                 + FUNZIONI_DELEGATE_GARA.DEN_AMM_AGENTE + "=?, "
				                                 + FUNZIONI_DELEGATE_GARA.CF_RUP_DELEGATA + "=?,"
				                                 + FUNZIONI_DELEGATE_GARA.CF_AMM_DELEGATA + "=?,"
			                                     + FUNZIONI_DELEGATE_GARA.DEN_AMM_DELEGATA + "=?,"
			                                     + FUNZIONI_DELEGATE_GARA.ID_SA_DELEGATA + "=?,"
			                                     + FUNZIONI_DELEGATE_GARA.DENOM_SA_DELEGATA + "=?"
				                                 + " WHERE " + FUNZIONI_DELEGATE_GARA.ID_GARA + "=?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(UPDATE_FUNZIONI_DELEGATE_QUERY);

			int index = 1;
			if (gara.getFlagSAAgente() == null || "".equals(gara.getFlagSAAgente()))
				stmt.setString(index++, Costanti.FLAG_VALORE_NO);
			else
				stmt.setString(index++, gara.getFlagSAAgente());

			if (gara.getID_F_DELEGATE() == 0)
				stmt.setNull(index++, java.sql.Types.INTEGER);
			else
				stmt.setInt(index++, gara.getID_F_DELEGATE());

			if (gara.getCF_AMM_AGENTE() == null || "".equals(gara.getCF_AMM_AGENTE()))
				stmt.setNull(index++, java.sql.Types.VARCHAR);
			else
				stmt.setString(index++, gara.getCF_AMM_AGENTE());

			if (gara.getDEN_AMM_AGENTE() == null || "".equals(gara.getDEN_AMM_AGENTE()))
				stmt.setNull(index++, java.sql.Types.VARCHAR);
			else
				stmt.setString(index++, gara.getDEN_AMM_AGENTE());

			stmt.setString(index++, gara.getCF_UTENTE());
			stmt.setString(index++, gara.getCF_AMMINISTRAZIONE());
			stmt.setString(index++, gara.getDENOM_AMMINISTRAZIONE());
			stmt.setString(index++, gara.getID_STAZIONE_APPALTANTE());
			stmt.setString(index++, gara.getDENOM_STAZIONE_APPALTANTE());
			
			stmt.setLong(index++, gara.getId_Gara());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, stmt);
		}

	}
	
	/**
	 * Mostra i dati per lo storico della gara delegata
	 * @param idGara
	 * @return
	 * @throws SQLException
	 */
	public List<String> getDatiStoriciGaraDelegata(Long idGara) throws  SQLException {
		List<String> res = new ArrayList<String>();
		
		String GET_DATI_STORICO = "SELECT "+FUNZIONI_DELEGATE_GARA.CF_RUP_DELEGATA+","+
		                                    FUNZIONI_DELEGATE_GARA.CF_AMM_DELEGATA+","+
		                                    FUNZIONI_DELEGATE_GARA.DEN_AMM_DELEGATA+","+
		                                    FUNZIONI_DELEGATE_GARA.ID_SA_DELEGATA+","+
		                                    FUNZIONI_DELEGATE_GARA.DENOM_SA_DELEGATA+","+
		                                    FUNZIONI_DELEGATE_GARA.DATA_PRESA_IN_CARICO +
		                          " FROM "+FUNZIONI_DELEGATE_GARA.TABLE_NAME+ " WHERE "+FUNZIONI_DELEGATE_GARA.ID_GARA+"= ?";
				
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = activeConnection.prepareStatement(GET_DATI_STORICO);
			stmt.setLong(1, idGara);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				res.add(rs.getString(1));
				res.add(rs.getString(2));
				res.add(rs.getString(3));
				res.add(rs.getString(4));
				res.add(rs.getString(5));
              
				Timestamp ts = rs.getTimestamp(6);
				if(ts!=null) {
					res.add(PageHelper.getViewDate(new Date(ts.getTime())));
				} else
					res.add("");
			}
				
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, stmt);
		}
		return res;
	}
	
	/**
	 * Valorizza la data di presa in carico della gara da parte della SA Delegante
	 * @param idGara
	 * @throws SQLException
	 */
	public void setDataPresaInCaricoDelega(long idGara) throws SQLException {
		String UPDATE_DATA_PRESA_IN_CARICO_DELEGA = "UPDATE "+FUNZIONI_DELEGATE_GARA.TABLE_NAME
				                                   +" SET "+FUNZIONI_DELEGATE_GARA.DATA_PRESA_IN_CARICO+"=? "
				                                   +" WHERE "+FUNZIONI_DELEGATE_GARA.ID_GARA+"=?";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
			try {
				stmt = activeConnection.prepareStatement(UPDATE_DATA_PRESA_IN_CARICO_DELEGA);
				stmt.setTimestamp(1,getNow());
				stmt.setLong(2, idGara);
				stmt.executeUpdate();
			} catch (SQLException e) {
				throw e;
			} finally {
				close(rs, stmt);
			}
		
	}

	private Gara selectFunzioniDelegateGara(Gara gara) throws SQLException {

		final String SELECT_FUNZIONI_DELEGATE_GARA = "SELECT " + FUNZIONI_DELEGATE_GARA.FLAG_SA_AGENTE + ","
				+ FUNZIONI_DELEGATE_GARA.ID_F_DELEGATE + "," + FUNZIONI_DELEGATE_GARA.CF_AMM_AGENTE + ","
				+ FUNZIONI_DELEGATE_GARA.DEN_AMM_AGENTE + " FROM " + FUNZIONI_DELEGATE_GARA.TABLE_NAME + " INNER JOIN "
				+ GARA.TABLE_NAME + " ON " + GARA.T_ID_GARA + " = " + FUNZIONI_DELEGATE_GARA.T_ID_GARA + " WHERE "
				+ FUNZIONI_DELEGATE_GARA.T_ID_GARA + " = ? ";

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			try {
				stmt = activeConnection.prepareStatement(SELECT_FUNZIONI_DELEGATE_GARA);
				stmt.setLong(1, gara.getId_Gara());
				rs = stmt.executeQuery();

				if (rs.next()) {
					gara.setFlagSAAgente(rs.getString(1));
					gara.setID_F_DELEGATE(rs.getInt(2));
					gara.setCF_AMM_AGENTE(rs.getString(3));
					gara.setDEN_AMM_AGENTE(rs.getString(4));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			close(rs, stmt);
		}

		return gara;
	}

	public void deleteFunzioniDelegateGara(long idGara) throws SQLException {

		final String CANCELLA_FUNZIONI_DELEGATE_GARA = "DELETE FROM " + FUNZIONI_DELEGATE_GARA.TABLE_NAME + " WHERE "
				+ FUNZIONI_DELEGATE_GARA.ID_GARA + " = ?";

		PreparedStatement psDelete = null;

		try {
			psDelete = activeConnection.prepareStatement(CANCELLA_FUNZIONI_DELEGATE_GARA);

			psDelete.setLong(1, idGara);

			psDelete.executeUpdate();
		} finally {
			try {
				psDelete.close();
			} catch (Exception e) {
			}
			psDelete = null;
		}
	}

	public boolean checkFunzioniDelegateGara(long idGara) throws SQLException {

		final String CHECK_FUNZIONI_DELEGATE_GARA = "SELECT * " + " FROM " + FUNZIONI_DELEGATE_GARA.TABLE_NAME
				+ " INNER JOIN " + GARA.TABLE_NAME + " ON " + GARA.T_ID_GARA + " = " + FUNZIONI_DELEGATE_GARA.T_ID_GARA
				+ " WHERE " + FUNZIONI_DELEGATE_GARA.T_ID_GARA + " = ? ";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean res = false;
		try {
			try {
				stmt = activeConnection.prepareStatement(CHECK_FUNZIONI_DELEGATE_GARA);
				stmt.setLong(1, idGara);
				rs = stmt.executeQuery();

				if (rs.next()) {
					res = true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			close(rs, stmt);
		}

		return res;
	}
	
	public String getCFRupGaraDelegata(long idGara) throws SQLException {

		final String CHECK_FUNZIONI_DELEGATE_GARA = "SELECT "+ FUNZIONI_DELEGATE_GARA.CF_RUP_DELEGATA + " FROM " + FUNZIONI_DELEGATE_GARA.TABLE_NAME
				+ " INNER JOIN " + GARA.TABLE_NAME + " ON " + GARA.T_ID_GARA + " = " + FUNZIONI_DELEGATE_GARA.T_ID_GARA
				+ " WHERE " + FUNZIONI_DELEGATE_GARA.T_ID_GARA + " = ? ";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		String res = null;
		try {
			try {
				stmt = activeConnection.prepareStatement(CHECK_FUNZIONI_DELEGATE_GARA);
				stmt.setLong(1, idGara);
				rs = stmt.executeQuery();

				if (rs.next()) {
					res = rs.getString(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			close(rs, stmt);
		}

		return res;
	}

	/**
	 * Recupera la lista delle categorie merceologiche DPCM inserite nei lotti
	 * attivi a partire dal numero gara
	 * 
	 * @param idGara
	 * @param codCategoria
	 * @return
	 * @throws SQLException
	 */
	public List<Long> getListaCatMercInLotto(long idGara) throws SQLException {

		final String CHECK_CAT_MERC_IN_LOTTO = "SELECT DISTINCT " + LOTTO.COD_CATEGORIA + " FROM " + LOTTO.TABLE_NAME
				+ " WHERE " + LOTTO.ID_GARA + " = ? AND " + LOTTO.DATA_CANCELLAZIONE_LOTTO + " IS NULL";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Long> res = new ArrayList<Long>();
		try {
			try {
				stmt = activeConnection.prepareStatement(CHECK_CAT_MERC_IN_LOTTO);
				stmt.setLong(1, idGara);
				rs = stmt.executeQuery();

				while (rs.next()) {
					res.add(rs.getLong(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			close(rs, stmt);
		}

		return res;
	}

	/**
	 * Recupera descrizione categoria merceologica
	 * 
	 * @param idCat
	 * @return
	 * @throws SQLException
	 */
	public String getDescrCat(long idCat) throws SQLException {
		final String GET_DESC_CAT = "SELECT " + EAGG_CATEGORIE.DESCRIZIONE + " FROM " + EAGG_CATEGORIE.TABLE_NAME
				+ " WHERE " + EAGG_CATEGORIE.COD_CATEGORIA + " = ?";
		String res = "";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			try {
				stmt = activeConnection.prepareStatement(GET_DESC_CAT);
				stmt.setLong(1, idCat);
				rs = stmt.executeQuery();

				if (rs.next()) {
					res = rs.getString(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			close(rs, stmt);
		}

		return res;
	}

	public Hashtable getSADelegateFromAmministrazioni(Hashtable amministrazioni) throws SQLException {
		Hashtable res = new Hashtable();

		final String SELECT_SA_DELEGATE = "SELECT DISTINCT " + GARA.T_ID_STAZIONE_APPALTANTE + " FROM "
				+ GARA.TABLE_NAME + " INNER JOIN " + FUNZIONI_DELEGATE_GARA.TABLE_NAME + " ON "
				+ FUNZIONI_DELEGATE_GARA.T_ID_GARA + " = " + GARA.T_ID_GARA + " WHERE 1=1 ";

		String query = SELECT_SA_DELEGATE;

		PreparedStatement stmt = null;
		ResultSet rs = null;

		query = this.prepareAmmNotIn(amministrazioni, query);

		try {
			try {
				stmt = activeConnection.prepareStatement(query);
				rs = stmt.executeQuery();

				while (rs.next()) {
					res.put(rs.getString(1), rs.getString(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} finally {
			close(rs, stmt);
		}

		return res;
	}

	/**
	 * Recupera il codice fiscale della SA delegante a partire da un numero gara o
	 * id del lotto
	 * 
	 * @param idGara
	 * @param id_Lotto
	 * @param b
	 * @param amministrazioni
	 * @return
	 */
	public String getCfAmmDelegata(long idGara, long id_Lotto, Hashtable amministrazioni) throws SQLException {
		String cf = "";
		String query = "SELECT " + FUNZIONI_DELEGATE_GARA.CF_AMM_AGENTE + " FROM " + FUNZIONI_DELEGATE_GARA.TABLE_NAME
				+ " INNER JOIN " + GARA.TABLE_NAME + " ON " + GARA.T_ID_GARA + " = " + FUNZIONI_DELEGATE_GARA.T_ID_GARA;
				
				if (id_Lotto != 0)
					query+= " INNER JOIN " + LOTTO.TABLE_NAME + " ON " + LOTTO.T_ID_GARA + " = " + GARA.T_ID_GARA; 
				
				query+= " WHERE "+ FUNZIONI_DELEGATE_GARA.T_CF_AMM_AGENTE + " IN (";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		Iterator it = amministrazioni.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			query += "'" + pair.getKey() + "'";
			if (it.hasNext())
				query += ",";
		}

		query += ")";

		if (idGara != 0)
			query += " AND " + GARA.T_ID_GARA + " = ?";
		if (id_Lotto != 0)
			query += " AND " + LOTTO.T_ID_LOTTO + " = ?";

		try {

			stmt = activeConnection.prepareStatement(query);
			if (idGara != 0)
				stmt.setLong(index++, idGara);
			if (id_Lotto != 0)
				stmt.setLong(index++, id_Lotto);
			rs = stmt.executeQuery();

			if (rs.next())
				cf = rs.getString(1);

		} finally {
			close(rs, stmt);
		}

		return cf;
	}
	
	/**
	 * Recupera il codice funzione delegata a partire da un numero gara o
	 * id del lotto
	 * 
	 * @param idGara
	 * @param id_Lotto
	 * @param b
	 * @param amministrazioni
	 * @return
	 */
	public String getIdFunzDelega(long idGara) throws SQLException {
		String idFunzDelega = "";
		String query = "SELECT " + FUNZIONI_DELEGATE_GARA.ID_F_DELEGATE + " FROM " + FUNZIONI_DELEGATE_GARA.TABLE_NAME
				+ " WHERE "+ FUNZIONI_DELEGATE_GARA.ID_GARA + " = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int index = 1;
		

		try {

			stmt = activeConnection.prepareStatement(query);
			if (idGara != 0)
				stmt.setLong(index++, idGara);
			
			rs = stmt.executeQuery();

			if (rs.next())
				idFunzDelega = rs.getString(1);

		} finally {
			close(rs, stmt);
		}

		return idFunzDelega;
	}

	private final String QUERY_LOTTI_AGGIUDICATI = "SELECT 1 FROM " + LOTTO.TABLE_NAME + " INNER JOIN "
			+ GARA.TABLE_NAME + " ON " + GARA.T_ID_GARA + " = " + LOTTO.T_ID_GARA + " INNER JOIN "
			+ FUNZIONI_DELEGATE_GARA.TABLE_NAME + " ON " + FUNZIONI_DELEGATE_GARA.T_ID_GARA + " = " + GARA.T_ID_GARA
			+ " INNER JOIN " + INFO_AGGIUDICAZIONI.TABLE_NAME + " ON " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + "="
			+ LOTTO.T_ID_LOTTO + " INNER JOIN " + AGGIUDICAZIONI.TABLE_NAME + " ON " + AGGIUDICAZIONI.T_ID_INFO + " = "
			+ INFO_AGGIUDICAZIONI.T_ID_INFO + " AND " + AGGIUDICAZIONI.T_DATA_INIZIO_INFO + " = "
			+ INFO_AGGIUDICAZIONI.T_DATA_INIZIO_INFO + " WHERE " + GARA.T_ID_GARA + " = ? " + " AND "
			+ FUNZIONI_DELEGATE_GARA.T_FLAG_SA_AGENTE + " = ?" + " AND " + LOTTO.DATA_CANCELLAZIONE_LOTTO + " IS NULL"
			+ " AND " + GARA.DATA_CANCELLAZIONE_GARA + " IS NULL" + " AND " + INFO_AGGIUDICAZIONI.T_ID_STATO + " = ?"
			+ " AND " + AGGIUDICAZIONI.T_ID_STATO + " = ?" + " AND " + INFO_AGGIUDICAZIONI.T_ESITO_PROCEDURA + " = ? "
		    + " AND "+GARA.CF_AMMINISTRAZIONE+" <> "+FUNZIONI_DELEGATE_GARA.T_CF_AMM_AGENTE;
	private final String QUERY_LOTTI_DATI_COMUNI = "SELECT 1 FROM " + LOTTO.TABLE_NAME + " INNER JOIN " + GARA.TABLE_NAME
			+ " ON " + GARA.T_ID_GARA + " = " + LOTTO.T_ID_GARA + " INNER JOIN " + FUNZIONI_DELEGATE_GARA.TABLE_NAME
			+ " ON " + FUNZIONI_DELEGATE_GARA.T_ID_GARA + " = " + GARA.T_ID_GARA + " INNER JOIN "
			+ INFO_AGGIUDICAZIONI.TABLE_NAME + " ON " + INFO_AGGIUDICAZIONI.T_ID_LOTTO + "=" + LOTTO.T_ID_LOTTO
			+ " WHERE " + GARA.T_ID_GARA + " = ? " + " AND " + FUNZIONI_DELEGATE_GARA.T_FLAG_SA_AGENTE + " = ?"
			+ " AND " + LOTTO.DATA_CANCELLAZIONE_LOTTO + " IS NULL" + " AND " + GARA.DATA_CANCELLAZIONE_GARA
			+ " IS NULL" + " AND " + INFO_AGGIUDICAZIONI.T_ID_STATO + " = ? AND "+GARA.CF_AMMINISTRAZIONE+" <> "+FUNZIONI_DELEGATE_GARA.T_CF_AMM_AGENTE;

	public boolean checkPresaInCaricoDelega(Long idGara, int idfDelega, Hashtable amministrazioni, int numLotti)
			throws SQLException {
		boolean res = false;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = "";
		int counter = 0;
		if(idfDelega==0)
			return false;
		
		try {
//Controlla se la gara abbia schede aggiudicazioni confermate o se abbia dati comuni con valore diverso da 'aggiudicata'
			if (idfDelega == Costanti.DELEGA1 || idfDelega == Costanti.DELEGA2) {
				query = addControlloAmministrazioni(QUERY_LOTTI_AGGIUDICATI, amministrazioni);
				
				stmt = activeConnection.prepareStatement(query);
				stmt.setLong(1, idGara);
				stmt.setString(2, Costanti.FLAG_VALORE_SI);
				stmt.setInt(3, StatiScheda.CONFERMATO);
				stmt.setInt(4, StatiScheda.CONFERMATO);
				stmt.setInt(5, Costanti.AGGIUDICATA);

				rs = stmt.executeQuery();
				while (rs.next())
					counter++;

				if (counter == numLotti)
					res = true;
				else {
					
					query = addControlloAmministrazioni(QUERY_LOTTI_DATI_COMUNI, amministrazioni);
					query+= " AND " + INFO_AGGIUDICAZIONI.T_ESITO_PROCEDURA + " <> ?";
					stmt = activeConnection.prepareStatement(query);
					stmt.setLong(1, idGara);
					stmt.setString(2, Costanti.FLAG_VALORE_SI);
					stmt.setInt(3, StatiScheda.CONFERMATO);
					stmt.setInt(4, Costanti.AGGIUDICATA);

					rs = stmt.executeQuery();
					while (rs.next())
						counter++;

					if (counter == numLotti)
						res = true;
				}
			} // Controlla se tutti i cig hanno una scheda dati comuni inviata
			else if (idfDelega == Costanti.DELEGA4) {
				query = addControlloAmministrazioni(QUERY_LOTTI_DATI_COMUNI, amministrazioni);
				stmt = activeConnection.prepareStatement(query);
				stmt.setLong(1, idGara);
				stmt.setString(2, Costanti.FLAG_VALORE_SI);
				stmt.setInt(3, StatiScheda.CONFERMATO);
				rs = stmt.executeQuery();
				while (rs.next())
					counter++;

				if (counter == numLotti)
					res = true;
			}

		} finally {
			close(rs, stmt);
		}

		return res;
	}

	/**
	 * Aggiungi in coda alla query la verifica sulla valorizzazione del CF della SA
	 * delegante
	 * 
	 * @param query
	 * @param amministrazioni
	 * @return
	 */
	private String addControlloAmministrazioni(String query, Hashtable amministrazioni) {
		String res = "";
//Prima recupera la lista dei lotti dove risulta trasmessa la scheda aggiudicazione confermata
		query += " AND " + FUNZIONI_DELEGATE_GARA.T_CF_AMM_AGENTE + " IN (";

		Iterator it = amministrazioni.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			query += "'" + pair.getKey() + "'";
			if (it.hasNext())
				query += ",";
		}

//		query += ") AND " + GARA.T_CF_AMMINISTRAZIONE + " NOT IN (";
//
//		Iterator it2 = amministrazioni.entrySet().iterator();
//		while (it2.hasNext()) {
//			Map.Entry pair = (Map.Entry) it2.next();
//			query += "'" + pair.getKey() + "'";
//			if (it2.hasNext())
//				query += ",";
//		}
//
		query += ")";
		res = query;
		return res;

	}

	/**
	 * Metodo chiamato solo da web per l'aggiornamento automatico del cig accordo quadro della gara in caso di adesione a una iniziativa presso
	 * i soggetti aggregatori
	 * @param id_Gara
	 * @param cigIniziativa
	 * @param idModReal
	 * @throws SQLException
	 */
	public void updateCIGAccQ(long id_Gara, String cigIniziativa, int idModReal) throws SQLException {
		String query = "UPDATE "+GARA.TABLE_NAME+" SET "+GARA.CIG_ACC_QUADRO+"= ?,"+GARA.ID_MODO_REAL+"= ? WHERE "+GARA.ID_GARA+" = ? ";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
			try {
				stmt = activeConnection.prepareStatement(query);
				stmt.setString(1,cigIniziativa);
				stmt.setInt(2, idModReal);
				stmt.setLong(3, id_Gara);
				stmt.executeUpdate();
			} catch (SQLException e) {
				throw e;
			} finally {
				close(rs, stmt);
			}
	}
	
	//Ticket #20055
	public InfoRettificaBean getInfoRettifica(String idGara) throws SQLException, Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		InfoRettificaBean infoRettifica = null;
		
		try {
			pstmt = activeConnection.prepareStatement(QUERY_SELECT_CHECK_RETTIFICA_BY_ID_GARA);
			pstmt.setLong(1, Long.valueOf(idGara));
			pstmt.setLong(2, Long.valueOf(idGara));
			rs = pstmt.executeQuery();
			Gara g = null;
			int counter = 0;

			while (rs.next()) {
				counter++;
				
				infoRettifica = new InfoRettificaBean();
				
				infoRettifica.setIdGara(rs.getLong(GARA.ID_GARA));
				infoRettifica.setDataPubblicazione(rs.getString(LOTTO.DATA_PUBBLICAZIONE));
				infoRettifica.setDataScadenzaPagamenti(rs.getString(LOTTO.DATA_SCADENZA_PAGAMENTI));
				infoRettifica.setFlagSospeso(rs.getString(PUBBLICAZIONI.FLAG_SOSPESO));
				infoRettifica.setIdPubblicazione(rs.getLong(PUBBLICAZIONI.ID_PUBBLICAZIONE));
				infoRettifica.setTipoOperazione(rs.getString(PUBBLICAZIONI.TIPO_OPERAZIONE));
				infoRettifica.setDataScadenzaInvito(rs.getString(LOTTO.DATA_SCADENZA_RICHIESTA_INVITO));
			
			}

			return infoRettifica;
		} catch (SQLException e) {
			throw e;
		} finally {
			close(rs, pstmt);
		}
	}
	
	// MEV 34188 - 3.04.8.1 FASE 2 
		public boolean checkSoggettoAggregatorePresente(String cf_Amm) throws SQLException, Exception {

			final String CHECK_FUNZIONI_DELEGATE_GARA = QUERY_SELECT_CHECK_SOGG_AGGREG;
	        int coutRow = 0;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			boolean res = false;
			try {
				try {
					stmt = activeConnection.prepareStatement(CHECK_FUNZIONI_DELEGATE_GARA);
					stmt.setString(1, cf_Amm);
					rs = stmt.executeQuery();

					while (rs.next())
					{
						coutRow++;
						if(coutRow > 0)
						{
							res = true;
							break;
						}					
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} finally {
				close(rs, stmt);
			}

			return res;
		}

}