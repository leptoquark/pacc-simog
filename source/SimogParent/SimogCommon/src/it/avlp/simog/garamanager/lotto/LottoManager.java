package it.avlp.simog.garamanager.lotto;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.avcp.simog.manager.cup.CupLottoAggManager;
import it.avcp.simog.managers.aggiudicazione.CondizioniManager;
import it.avcp.simog.managers.aggiudicazione.MisuraPremialeManager;
import it.avcp.simog.managers.aggiudicazione.MotivoDerogaManager;
import it.avcp.simog.managers.aggiudicazione.TipoAppaltoManager;
import it.avlp.simog.beans.CIGBean;
import it.avlp.simog.beans.CondizioneLottoBean;
import it.avlp.simog.beans.CpvLotto;
import it.avlp.simog.beans.DerogaQualificazioneSABean;
import it.avlp.simog.beans.Gara;
import it.avlp.simog.beans.InfoComuniBean;
import it.avlp.simog.beans.InfoGaraBean;
import it.avlp.simog.beans.IniziativaSoggAggr;
import it.avlp.simog.beans.Lotto;
import it.avlp.simog.beans.MisuraPremialeBean;
import it.avlp.simog.beans.MisuraPremialeLottoBean;
import it.avlp.simog.beans.MotivoDerogaBean;
import it.avlp.simog.beans.MotivoDerogaLottoBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.common.action.CupLottoAggAction;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.common.servlet.ParametriServlet;
import it.avlp.simog.db.AccessiDB;
import it.avlp.simog.db.Costanti;
import it.avlp.simog.db.SimogFlags;
import it.avlp.simog.db.advanced.TableBean;
import it.avlp.simog.db.generated.AFFIDAMENTI_RISERVATI;
import it.avlp.simog.db.generated.ART_ESCLUSIONE;
import it.avlp.simog.db.generated.ART_ESTREMA_URGENZA_SOMMA_URGENZA;
import it.avlp.simog.db.generated.CATEGORIA;
import it.avlp.simog.db.generated.CIG_INDEX;
import it.avlp.simog.db.generated.CIG_STORIA;
import it.avlp.simog.db.generated.CPVEU;
import it.avlp.simog.db.generated.CPV_LOTTO;
import it.avlp.simog.db.generated.DEROGA_QUALIFICAZIONE_SA;
import it.avlp.simog.db.generated.EAGG_CATEGORIE;
import it.avlp.simog.db.generated.EAGG_MOTIVI;
import it.avlp.simog.db.generated.FUNZIONI_DELEGATE;
import it.avlp.simog.db.generated.FUNZIONI_DELEGATE_GARA;
import it.avlp.simog.db.generated.GARA;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.MISURA_PREMIALE_LOTTO;
import it.avlp.simog.db.generated.MODALITA_INDIZIONE_ALLEGATO_IX;
import it.avlp.simog.db.generated.MODI_REALIZZAZIONE;
import it.avlp.simog.db.generated.MODO_INDIZIONE;
import it.avlp.simog.db.generated.MOTIVI_CANCELLAZIONE;
import it.avlp.simog.db.generated.MOTIVO_COLLEGAMENTO;
import it.avlp.simog.db.generated.MOTIVO_DEROGA;
import it.avlp.simog.db.generated.MOTIVO_REVISIONE_PREZZI;
import it.avlp.simog.db.generated.REL_LOTTO_CATEGORIA_SCORPORABILE;
import it.avlp.simog.db.generated.SCELTA_CONTRAENTE;
import it.avlp.simog.db.generated.STATI_SCHEDA;
import it.avlp.simog.db.generated.STRUMENTI_SVOLGIMENTO_PROCEDURE;
import it.avlp.simog.db.generated.TIPI_CATEGORIA;
import it.avlp.simog.db.generated.TIPOLOGIA;
import it.avlp.simog.errormessage.Messaggi;
import it.avlp.simog.garamanager.GaraManager;
import it.avlp.simog.garamanager.IniziativaManager;
import it.avlp.simog.util.CIGUtils;
import it.avlp.simog.util.PageHelper;
import it.avlp.simog.util.SimogProperties;

public class LottoManager extends AccessiDB {

	private final String GET_LOTTO_BY_ID_LOTTO_QUERY_AMM = "SELECT " + LOTTO.FLAG_PNRR_PNC + ", "
			//MEV 38205 3.04.8.1
			+ LOTTO.FLAG_USO_METODI_EDILIZIA + ", "
			//MEV 37010 3.04.8.1 aggiunto flag deroga adesione
			+ LOTTO.FLAG_DEROGA_ADESIONE + ", " + LOTTO.FLAG_PREVISIONE_QUOTA + ", " + LOTTO.FLAG_MISURE_PREMIALI + ", " + LOTTO.QUOTA_FEMMINILE + ", "
			+ LOTTO.QUOTA_GIOVANILE + ", " + GARA.T_ID_GARA + ", " + GARA.T_OGGETTO + " AS " + GARA.TABLE_NAME
			+ GARA.OGGETTO + ", " + GARA.DATA_CREAZIONE + ", " + GARA.CF_UTENTE + ", " + GARA.ID_STAZIONE_APPALTANTE
			+ ", " + GARA.DENOM_STAZIONE_APPALTANTE + ", " + GARA.CF_AMMINISTRAZIONE + ", " + GARA.DENOM_AMMINISTRAZIONE
			+ ", " + GARA.DATA_PERFEZIONAMENTO_BANDO
			// gm due nuovi campi simog 3.04 per sblocco lotto contratto escluso
			+ ", " + GARA.ID_PUBBLICAZIONE + ", " + GARA.DATA_INIZIO_PUBB
			// gm nuovo campo simog 3.04
			+ ", " + GARA.NUMERO_LOTTI
			// 659 nuovo campo simog
			+ ", " + GARA.DURATA_GIORNI + ", " + LOTTO.DATA_INIB_PAGAMENTO + ", " + LOTTO.DATA_CANCELLAZIONE_LOTTO
			+ ", " + LOTTO.DATA_SCADENZA_PAGAMENTI + ", " + LOTTO.DATA_PUBBLICAZIONE + ", " + LOTTO.T_ID_LOTTO + ", "
			+ LOTTO.DATA_COMUNICAZIONE + ", " + LOTTO.CIG_CICLE + ", " + LOTTO.CIG + ", " + LOTTO.CIG_KKK + ", "
			+ LOTTO.T_OGGETTO + " AS " + LOTTO.TABLE_NAME + LOTTO.OGGETTO + ", " + LOTTO.SOMMA_URGENZA + ", "
			+ LOTTO.IMPORTO_LOTTO + ", " + LOTTO.IMPORTO_SA + ", " + LOTTO.IMPORTO_IMPRESA + ", "
			+ LOTTO.ID_CATEGORIA_PREVALENTE + ", " + LOTTO.T_ID_SCELTA_CONTRAENTE + " AS " + LOTTO.ID_SCELTA_CONTRAENTE
			+ ", " + SCELTA_CONTRAENTE.T_DESCRIZIONE + " AS " + SCELTA_CONTRAENTE.TABLE_NAME
			// 2846
			+ ", " + LOTTO.T_ID_MOTIVO + " AS " + LOTTO.ID_MOTIVO + ", " + MOTIVO_COLLEGAMENTO.T_DESCRIZIONE + " AS "
			+ MOTIVO_COLLEGAMENTO.TABLE_NAME
			// 2846
			+ ", " + LOTTO.T_ID_TIPOLOGIA + ", " + TIPOLOGIA.T_DESCRIZIONE + " AS " + TIPOLOGIA.TABLE_NAME + ", "
			+ LOTTO.T_ID_CPV + ", " + CPVEU.T_DESCRIZIONE + " AS " + CPVEU.TABLE_NAME + ", "
			+ REL_LOTTO_CATEGORIA_SCORPORABILE.T_ID_CATEGORIA + " AS " + ParametriServlet.CATEGORIA_SCORPORABILE + ", "
			+ GARA.TABLE_NAME + "." + GARA.DATA_CANCELLAZIONE_GARA + ", " + GARA.TABLE_NAME + "." + GARA.DATA_INIB_PAGAM
			+ ", " + GARA.TABLE_NAME + "." + GARA.DATA_TERMINE_PAGAMENTO + ", " + GARA.TABLE_NAME + "."
			+ GARA.DATA_CONFERMA_GARA + ", " + GARA.TABLE_NAME + "." + GARA.DATA_COMUN + ", " + GARA.TABLE_NAME + "."
			+ GARA.IMPORTO_SA_GARA + ", " + GARA.TABLE_NAME + "." + GARA.IMPORTO_GARA + ", " + STATI_SCHEDA.TABLE_NAME
			+ "." + STATI_SCHEDA.DESCRIZIONE + ", " + LOTTO.T_ID_MOTIVAZIONE + ", " + LOTTO.T_NOTE_CANC + ", "
			+ LOTTO.T_DATA_CREAZIONE_LOTTO + ", " + GARA.ID_STATO + ", " + GARA.TIPO_SCHEDA_GARA + ", "
			+ GARA.ID_MOTIVAZIONE_CANC + ", " + TIPI_CATEGORIA.T_DESCRIZIONE + " AS " + TIPI_CATEGORIA.TABLE_NAME + ", "
			+ GARA.T_ID_MODO_GARA + ", " + MODO_INDIZIONE.T_DESCRIZIONE + " AS " + MODO_INDIZIONE.TABLE_NAME + ", "
			+ GARA.T_ID_MODO_REAL + ", " + GARA.T_CIG_ACC_QUADRO + ", " + MODI_REALIZZAZIONE.T_DESCRIZIONE + " AS "
			+ MODI_REALIZZAZIONE.TABLE_NAME + ", " + STRUMENTI_SVOLGIMENTO_PROCEDURE.T_DESCRIZIONE + " AS "
			+ STRUMENTI_SVOLGIMENTO_PROCEDURE.TABLE_NAME // TICKET ALM #664
			+ ", " + ART_ESTREMA_URGENZA_SOMMA_URGENZA.T_DESCRIZIONE + " AS "
			+ ART_ESTREMA_URGENZA_SOMMA_URGENZA.TABLE_NAME // TICKET ALM #3832
			+ ", " + MODALITA_INDIZIONE_ALLEGATO_IX.T_DESCRIZIONE + " AS " + MODALITA_INDIZIONE_ALLEGATO_IX.TABLE_NAME // TICKET
																														// ALM
																														// #3834
			+ ", " + GARA.NOTE_CANC_GARA
// colonna duplicata manda in errore il tablebean!		+ ", " + LOTTO.DATA_CREAZIONE_LOTTO
			+ ", " + LOTTO.TIPO_CONTRATTO_LOTTO + ", " + LOTTO.FLAG_ESCLUSO + ", " + LOTTO.T_ID_ESCLUSIONE
			/* gm nuovo codice 3.0 */
			+ ", " + LOTTO.TRIENNIO_ANNO_INIZIO + ", " + LOTTO.TRIENNIO_ANNO_FINE + ", " + LOTTO.TRIENNIO_PROGRESSIVO
			+ ", " + LOTTO.ANNUALE_CUI_MININF

			// gm nuovo codice pubblicazione bando 3.0
			+ ", " + LOTTO.LUOGO_ISTAT + ", " + LOTTO.LUOGO_NUTS + ", " + LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA
			// gm fine nuovo codice pubblicazione bando 3.0

			// PP B302.3.3
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.FLAG_PREVEDE_RIP : "")
			// Ticket #20058 - 09 - 02 - 21
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.DURATA_RINNOVI_RIPETIZIONI : "")
			// Ticket #20057 - 09 - 02 - 21
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.DURATA_AFFIDAMENTO_IN_GIORNI : "")
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.FLAG_RIPETIZIONE : "")
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.CIG_ORIGINE_RIP : "")

			+ (SimogFlags.is3025_RFWEBGL02Active() ? ", " + LOTTO.ORA_SCADENZA : "")

			+ (SimogFlags.is3030_RFWEBGL00Active() ? ", " + LOTTO.DATA_SCADENZA_RICHIESTA_INVITO : "")
			+ (SimogFlags.is3030_RFWEBGL00Active() ? ", " + LOTTO.DATA_LETTERA_INVITO : "")

			+ ", " + ART_ESCLUSIONE.T_DESCRIZIONE + " AS " + ART_ESCLUSIONE.TABLE_NAME + ", " + "G_"
			+ MOTIVI_CANCELLAZIONE.T_DESCRIZIONE + " AS " + "G_" + MOTIVI_CANCELLAZIONE.DESCRIZIONE + ", " + "L_"
			+ MOTIVI_CANCELLAZIONE.T_DESCRIZIONE + " AS " + "L_" + MOTIVI_CANCELLAZIONE.DESCRIZIONE
			+ ", (select count(1) from info_aggiudicazioni where info_aggiudicazioni.id_lotto = lotto.id_lotto and info_aggiudicazioni.id_stato in (1,2)) AS "
			+ PSBD.HASSCHEDE + (SimogFlags.is3031_ESCL_AVCPASS() ? "," + GARA.ESCLUSO_AVCPASS : "")
			+ (SimogFlags.isINT85_RFWEBGL01Active() ? "," + GARA.SCELTA_LEGGE89 : "")
			+ (SimogFlags.isINT85_RFWEBGL01Active() ? "," + GARA.TIPOSA_BDNCP : "")
			+ (SimogFlags.isINT87_RFSIMOGWEB01Active() ? "," + GARA.URGENZA_DL133 : "")
			+ (SimogFlags.is30350_RFWEBGL01Active() ? "," + GARA.COD_MOTIVO_EAGG : "")
			+ (SimogFlags.is30350_RFWEBGL01Active() ? "," + EAGG_MOTIVI.T_DESCRIZIONE : "") + " AS "
			+ EAGG_MOTIVI.TABLE_NAME + ", " + LOTTO.FLAG_DL50 // TICKET ALM #2845
			+ ", " + LOTTO.PRIMA_ANNUALITA // TICKET ALM #2845
			+ ", " + AFFIDAMENTI_RISERVATI.T_DESCRIZIONE + " AS " + AFFIDAMENTI_RISERVATI.TABLE_NAME // TICKET ALM #3835
			+ ", " + LOTTO.T_ID_AFF_RISERVATI // TICKET ALM #3835
			+ ", " + LOTTO.FLAG_REGIME // TICKET ALM #3836
			+ ", " + FUNZIONI_DELEGATE_GARA.T_FLAG_SA_AGENTE // TICKET ALM #659 - 3.04.4
			+ ", " + FUNZIONI_DELEGATE.T_DESCRIZIONE + " AS " + FUNZIONI_DELEGATE.TABLE_NAME // TICKET ALM #659 - 3.04.4
			+ ", " + FUNZIONI_DELEGATE_GARA.T_CF_AMM_AGENTE // TICKET ALM #659 - 3.04.4
			+ ", " + FUNZIONI_DELEGATE_GARA.T_DEN_AMM_AGENTE // TICKET ALM #659 - 3.04.4
			+ ", " + LOTTO.T_COD_CATEGORIA // TICKET ALM #4222 - 3.04.4
			// UN is3031_RFWEBGL02Active
			+ (SimogFlags.is3031_RFWEBGL02Active() ? ", " + LOTTO.FLAG_CUP : "") + ", " + EAGG_CATEGORIE.T_DESCRIZIONE
			+ " AS " + EAGG_CATEGORIE.TABLE_NAME + ", " + GARA.CODICE_AUSA + ", " + LOTTO.IMPORTO_OPZIONI + ", "
			+ LOTTO.T_FLAG_PNRR_PNC + " FROM " + LOTTO.TABLE_NAME

			+ " JOIN " + GARA.TABLE_NAME + " ON " + GARA.T_ID_GARA + "=" + LOTTO.T_ID_GARA

			+ " JOIN " + STATI_SCHEDA.TABLE_NAME + " ON " + GARA.T_ID_STATO + " = " + STATI_SCHEDA.T_ID_STATO

			+ " JOIN " + TIPOLOGIA.TABLE_NAME + " ON " + LOTTO.T_ID_TIPOLOGIA + "=" + TIPOLOGIA.T_ID_TIPOLOGIA

			+ " LEFT OUTER JOIN " + CPVEU.TABLE_NAME + " ON " + CPVEU.T_VERSIONE + " = "
			+ buildVersCPV(LOTTO.T_ID_CPV, GARA.T_DATA_CREAZIONE) + " AND " + LOTTO.T_ID_CPV + "=" + CPVEU.ID_DIV
			+ " + " + CPVEU.ID_GRP + " + " + CPVEU.ID_CLS + " + " + CPVEU.ID_CTG + " + " + CPVEU.ID_VOX + " +'-'+ "
			+ CPVEU.CHK

			+ " JOIN " + SCELTA_CONTRAENTE.TABLE_NAME + " ON " + SCELTA_CONTRAENTE.T_ID_SCELTA_CONTRAENTE + "="
			+ LOTTO.T_ID_SCELTA_CONTRAENTE

			// TICKET ALM - 3.04.3 #2846
			+ " LEFT JOIN " + MOTIVO_COLLEGAMENTO.TABLE_NAME + " ON " + MOTIVO_COLLEGAMENTO.T_ID_MOTIVO + "="
			+ LOTTO.T_ID_MOTIVO
			// FINE TICKET ALM - 3.04.3 #2846

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

			+ " LEFT JOIN  " + ART_ESCLUSIONE.TABLE_NAME + " ON " + ART_ESCLUSIONE.T_ID_ESCLUSIONE + "="
			+ LOTTO.T_ID_ESCLUSIONE

			+ " LEFT JOIN  " + MOTIVI_CANCELLAZIONE.TABLE_NAME + " AS " + "G_" + MOTIVI_CANCELLAZIONE.TABLE_NAME
			+ " ON " + "G_" + MOTIVI_CANCELLAZIONE.T_ID_MOTIVO_CANC + "=" + LOTTO.ID_MOTIVAZIONE

			+ " LEFT JOIN  " + MOTIVI_CANCELLAZIONE.TABLE_NAME + " AS " + "L_" + MOTIVI_CANCELLAZIONE.TABLE_NAME
			+ " ON " + "L_" + MOTIVI_CANCELLAZIONE.T_ID_MOTIVO_CANC + "=" + LOTTO.ID_MOTIVAZIONE

			+ " LEFT JOIN  " + REL_LOTTO_CATEGORIA_SCORPORABILE.TABLE_NAME + " ON "
			+ REL_LOTTO_CATEGORIA_SCORPORABILE.T_ID_LOTTO + "=" + LOTTO.T_ID_LOTTO

			// TICKET ALM #659 - 3.04.4
			+ " LEFT JOIN " + FUNZIONI_DELEGATE_GARA.TABLE_NAME + " ON " + FUNZIONI_DELEGATE_GARA.T_ID_GARA + "="
			+ GARA.T_ID_GARA + " LEFT JOIN " + FUNZIONI_DELEGATE.TABLE_NAME + " ON " + FUNZIONI_DELEGATE.T_ID_F_DELEGATE
			+ " = " + FUNZIONI_DELEGATE_GARA.T_ID_F_DELEGATE
			// FINE TICKET ALM #659 - 3.04.4

			+ " LEFT JOIN " + EAGG_CATEGORIE.TABLE_NAME + " ON " + EAGG_CATEGORIE.T_COD_CATEGORIA + " = "
			+ LOTTO.T_COD_CATEGORIA

			// is30350_RFWEBGL01Active
			+ (SimogFlags.is30350_RFWEBGL01Active() ? " LEFT JOIN  " : "")
			+ (SimogFlags.is30350_RFWEBGL01Active() ? EAGG_MOTIVI.TABLE_NAME : "")
			+ (SimogFlags.is30350_RFWEBGL01Active() ? " ON " + EAGG_MOTIVI.T_COD_MOTIVO + "=" + GARA.T_COD_MOTIVO_EAGG
					: "")

			+ " WHERE " + LOTTO.T_ID_LOTTO + "=?";

	private final String perfezionamentoLotto = "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.DATA_PUBBLICAZIONE
			+ "=?, " + LOTTO.DATA_SCADENZA_PAGAMENTI + "=?"
			+ (SimogFlags.is3025_RFWEBGL02Active() ? ", " + LOTTO.ORA_SCADENZA + "=?" : "") + ", " + LOTTO.IMPORTO_SA
			+ "=?" + ", " + LOTTO.IMPORTO_IMPRESA + "=?"
			+ (SimogFlags.is3030_RFWEBGL00Active() ? ", " + LOTTO.DATA_SCADENZA_RICHIESTA_INVITO + "=?" : "")
			+ (SimogFlags.is3030_RFWEBGL00Active() ? ", " + LOTTO.DATA_LETTERA_INVITO + "=?" : "") + " WHERE "
			+ LOTTO.ID_LOTTO + "=?";
	
	//MEV 43345 3.04.10
	private final String perfezionamentoLottoQualificazioneSA = "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.ID_DEROGA_QUALIFICAZIONE_SA
			+ "=?, " + LOTTO.FLAG_IS_QUALIFICATA_KO + "=?" + " WHERE "
			+ LOTTO.ID_LOTTO + "=?";
	//fine mev

	private final String aggiornamentoLotto = "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.OGGETTO + "= ?" + ", "
			+ LOTTO.FLAG_PNRR_PNC + " =?" + ", " + LOTTO.FLAG_PREVISIONE_QUOTA + " =?" + ", "
			//MEV 38205 3.04.8.1
			+ LOTTO.FLAG_USO_METODI_EDILIZIA + " =?" + ", "
			//3.04.9 MEV 40610
			+ LOTTO.ID_DEROGA_QUALIFICAZIONE_SA + " =?" + ", "
			//3.04.9 MEV 40610
			//+ LOTTO.FLAG_IS_QUALIFICATA_KO + " =?" + ", "
			//MEV 37010 3.04.8.1 aggiunto flag deroga adesione
			+ LOTTO.FLAG_DEROGA_ADESIONE + " =?" + ", " + LOTTO.FLAG_MISURE_PREMIALI + " =?" + ", " + LOTTO.QUOTA_FEMMINILE + " =?" + ", " + LOTTO.QUOTA_GIOVANILE
			+ " =?" + ", " + LOTTO.IMPORTO_LOTTO + " =?" + ", " + LOTTO.ID_TIPOLOGIA + " =?" + ", " + LOTTO.ID_CPV
			+ " =?" + ", " + LOTTO.ID_SCELTA_CONTRAENTE + " =?" + ", " + LOTTO.ID_MOTIVO + " =?" // TICKET ALM - 3.04.3
			+ ", " + LOTTO.ID_CATEGORIA_PREVALENTE + " =?" + ", " + LOTTO.IMPORTO_SA + "=?" + ", "
			+ LOTTO.IMPORTO_IMPRESA + "=?" + ", " + LOTTO.TIPO_CONTRATTO_LOTTO + "=?" + ", " + LOTTO.FLAG_ESCLUSO + "=?"
			+ ", " + LOTTO.ID_ESCLUSIONE + "=?"
			/* gm nuovo codice 3.0 */
			+ ", " + LOTTO.TRIENNIO_ANNO_INIZIO + "=?" + ", " + LOTTO.TRIENNIO_ANNO_FINE + "=?" + ", "
			+ LOTTO.TRIENNIO_PROGRESSIVO + "=?" + ", " + LOTTO.ANNUALE_CUI_MININF + "=?"

			// gm nuovo codice pubblicazione bando 3.0
			+ ", " + LOTTO.LUOGO_ISTAT + "=?" + ", " + LOTTO.LUOGO_NUTS + "=?" + ", "
			+ LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA + "=?"
			// gm fine nuovo codice pubblicazione bando 3.0

			+ ", " + LOTTO.SOMMA_URGENZA + "=?"

			// PP B302.3.3
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.FLAG_PREVEDE_RIP + " =?" : "")
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.FLAG_RIPETIZIONE + " =?" : "")
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.CIG_ORIGINE_RIP + " =?" : "")
//        + (SimogFlags.is3025_RFWEBGL02Active() ? ", " + LOTTO.ORA_SCADENZA + " =?" : "") 
			+ (SimogFlags.is3031_RFWEBGL02Active() ? ", " + LOTTO.FLAG_CUP + " =?" : "") + ", " + LOTTO.FLAG_DL50 + "=?" // TICKET
																															// ALM
																															// #2845
			+ ", " + LOTTO.PRIMA_ANNUALITA + "=? " // TICKET ALM #2845
			+ ", " + LOTTO.ID_AFF_RISERVATI + "=? " // TICKET ALM #3835
			+ ", " + LOTTO.FLAG_REGIME + "=?" // TICKET ALM #3836
			+ ", " + LOTTO.COD_CATEGORIA + "=? " // TICKET ALM #4222 - 3.04.4
			+ ", " + LOTTO.IMPORTO_OPZIONI + "=?" // TICKET ALM 13691 - 3.04.5
			// Ticket #20058 - 09 - 02 - 21
			+ ", " + LOTTO.DURATA_RINNOVI_RIPETIZIONI + "=?"
			// Ticket #20058 - 09 - 02 - 21
			+ ", " + LOTTO.DURATA_AFFIDAMENTO_IN_GIORNI + "=?" + " WHERE " + LOTTO.ID_LOTTO + " = ?";
	private final String SET_REL_CATEGORIA_SCORPORABILE = "INSERT INTO  " + REL_LOTTO_CATEGORIA_SCORPORABILE.TABLE_NAME
			+ " values ( ?, ? )";
	private final String INSERT_LOTTO_CATEGORIA_SCORPORABILE = "INSERT INTO "
			+ REL_LOTTO_CATEGORIA_SCORPORABILE.TABLE_NAME + " (" + REL_LOTTO_CATEGORIA_SCORPORABILE.ID_CATEGORIA + ", "
			+ REL_LOTTO_CATEGORIA_SCORPORABILE.ID_LOTTO + " ) VALUES (?, ?)";
	private final String cancellaCategorieScorporabili = "DELETE FROM " + REL_LOTTO_CATEGORIA_SCORPORABILE.TABLE_NAME
			+ " WHERE " + REL_LOTTO_CATEGORIA_SCORPORABILE.ID_LOTTO + " = ?";
	private final String categorieScorporabiliByIdLotto = "SELECT " + CATEGORIA.T_ID_CATEGORIA + ", "
			+ CATEGORIA.DESCRIZIONE + " FROM " + CATEGORIA.TABLE_NAME + ", "
			+ REL_LOTTO_CATEGORIA_SCORPORABILE.TABLE_NAME + " WHERE " + CATEGORIA.T_ID_CATEGORIA + "="
			+ REL_LOTTO_CATEGORIA_SCORPORABILE.T_ID_CATEGORIA + " AND " + REL_LOTTO_CATEGORIA_SCORPORABILE.ID_LOTTO
			+ " =?";
	// semplice query per il ritorno della categoria prevalente (per il preselect
	// nel combobox di daticomuni)
	private final String categoriaPrevalente = "SELECT " + LOTTO.ID_CATEGORIA_PREVALENTE + " FROM " + LOTTO.TABLE_NAME
			+ " WHERE " + LOTTO.ID_LOTTO + " = ?";

	/*********************************************************************************************
	 * Costruttore
	 * 
	 * @param currentActiveConnection
	 * @param logger
	 */
	public LottoManager(Connection currentActiveConnection, Logger logger) {
		super(currentActiveConnection, logger);
	}
	/*
	 * metodo aggiunto per il recupero della categoria prevalente relativa ad un cig
	 */

	/*********************************************************************************************
	 * Ricerca la categoria prevalente del lotto
	 * 
	 * @param idLotto long per l'id del lotto
	 * @return String
	 */
	public String getCategoriaPrevalenteId(long idLotto) throws SQLException {
		logger.debug("Ricerca categoria prevalente [" + idLotto + "] Query Eseguita[" + categoriaPrevalente + "]");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = activeConnection.prepareStatement(categoriaPrevalente);
			pstmt.setLong(1, idLotto);
			rs = pstmt.executeQuery();
			String categoriaPrevalente = "";
			if (rs.next()) {
				categoriaPrevalente = rs.getString(LOTTO.ID_CATEGORIA_PREVALENTE);
			}
			rs.close();
			return categoriaPrevalente;
		} finally {
			close(rs, pstmt);
// 			try {	pstmt.close();	} 
// 			catch( Exception e ) {	}
// 			pstmt = null;
		}
	}

	/**************************************************************************************************
	 * Ottiene le informazioni associate al lotto in base all'id del lotto. Tali
	 * info riguardano:
	 * <ul>
	 * <li>Id Gara
	 * <li>Oggetto della Gara
	 * <li>Data di creazione
	 * <li>Codice Fiscale Utente
	 * <li>Id Stazione Appaltante
	 * <li>Denominazione Stazione Appaltante
	 * <li>Codice Fiscale Amministratore
	 * <li>Denominazione Amministratore
	 * <li>Data Inibizione Pagamento
	 * <li>Data Cancellazione Lotto
	 * <li>Data Scadenza Pagamenti
	 * <li>Data Scadenza Richiesta Invito
	 * <li>Data Lettera Invito
	 * <li>Data Pubblicazione
	 * <li>Id Lotto
	 * <li>Data Comunicazione
	 * <li>CIG CICLE
	 * <li>CIG
	 * <li>CIG KKK
	 * <li>Oggetto del Lotto
	 * <li>Somma Urgenza
	 * <li>Importo del Lotto
	 * <li>Imprto SA
	 * <li>Importo Impresa
	 * <li>Id Categoria Prevalente
	 * <li>Id Scelta Contraente
	 * <li>Descrizione Scelta Contraente
	 * </ul>
	 * 
	 * @param idLotto long per l'id del lotto
	 * @return TableBean
	 * @throws SQLException
	 */
	public TableBean getLottoByIdLottoAmm(long idLotto) throws SQLException {

		logger.debug("Ricerca informazioni per lotto [" + idLotto + "] Query Eseguita["
				+ GET_LOTTO_BY_ID_LOTTO_QUERY_AMM + "]");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = activeConnection.prepareStatement(GET_LOTTO_BY_ID_LOTTO_QUERY_AMM);
			pstmt.setLong(1, idLotto);

			rs = pstmt.executeQuery();

			return new TableBean(rs);
		} finally {
			close(rs, pstmt);
// 			try {
// 				pstmt.close();
// 			} catch ( Exception e ) {}
// 			pstmt = null;
		}
	}

	private final String GET_LOTTO_BY_CIG = "SELECT * "
//		+ 		LOTTO.ID_LOTTO
//		+ ", " + LOTTO.CIG_CICLE
//		+ ", " + LOTTO.CIG
//		+ ", " + LOTTO.CIG_KKK
//		+ ", " + LOTTO.ID_SCELTA_CONTRAENTE
//		+ ", " + LOTTO.DATA_CREAZIONE_LOTTO
			+ " FROM " + LOTTO.TABLE_NAME + " WHERE " + LOTTO.CIG + "= ? and " + LOTTO.CIG_KKK + "= ?" + " order by "
			+ LOTTO.CIG_CICLE + " desc ";
	/* added feb. 2008 for bean loading skipping use of table bean */

	// Utilizzato insieme ad Updata_data_PUBBLICAZIONE ed update_data_Gara per
	// sbloccare una gara
	private final String UPDATE_DATA_LOTTI = "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.T_DATA_PUBBLICAZIONE
			+ " = ?, " + LOTTO.T_DATA_SCADENZA_PAGAMENTI + " = ?, "
			+ (SimogFlags.is3025_RFWEBGL02Active() ? LOTTO.T_ORA_SCADENZA + " =?," : "")
			+ (SimogFlags.is3030_RFWEBGL00Active() ? LOTTO.T_DATA_SCADENZA_RICHIESTA_INVITO + " =?," : "")
			+ (SimogFlags.is3030_RFWEBGL00Active() ? LOTTO.T_DATA_LETTERA_INVITO + " =?," : "")
// PP azzero anche il contributo
			+ LOTTO.IMPORTO_IMPRESA + " = 0 "
// PP 11.09.2014 mancava data_comunicazione settata dall'ETL di riscossione
			+ "," + LOTTO.DATA_COMUNICAZIONE + " = NULL " + " WHERE " + LOTTO.T_ID_GARA + " = ? AND "
			+ LOTTO.T_DATA_PUBBLICAZIONE + " IS NOT NULL ";

	private final String GET_LOTTO_BY_IDLOTTO = "SELECT * " + " FROM " + LOTTO.TABLE_NAME + " WHERE " + LOTTO.ID_LOTTO
			+ "= ?";

	private final String GET_LOTTI_BY_IDGARA = "SELECT * " + " FROM " + LOTTO.TABLE_NAME + " WHERE " + LOTTO.ID_GARA
			+ "= ? " + " AND " + LOTTO.DATA_CANCELLAZIONE_LOTTO + " IS NULL " + " AND " + LOTTO.DATA_INIB_PAGAMENTO
			+ " IS NULL ";

	/*********************************************************************************************
	 * restituisce una lista di lotto identificati per Cig
	 * 
	 * @param cig log per il cig
	 * @return List&lt;Lotto&gt;
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Lotto> getLottoByCigWS(String fullCig) throws SQLException, Exception {
		if (fullCig.trim().length() < 10) {
			throw new Exception(Messaggi.SIMOG_VALIDAZIONE_186.replace("$1", fullCig));
		}

		fullCig = CIGBean.getRealCIG(fullCig);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Lotto l = null;
		List<Lotto> lOut = new ArrayList<Lotto>();

		try {
			pstmt = activeConnection.prepareStatement(GET_LOTTO_BY_CIG);
			pstmt.setString(1, fullCig.substring(0, 7));
			pstmt.setString(2, fullCig.substring(7, 10));
			logger.debug("0,7 -> " + fullCig.substring(0, 7) + ", 7,10 -> " + fullCig.substring(7, 10));
			rs = pstmt.executeQuery();
			l = new Lotto();

			if (rs.next()) {
				fillBean(rs, l);
				lOut.add(l);
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
	 * restituisce una un lotto identificato da id lotto
	 * 
	 * @param idLotto
	 * @return Lotto
	 * @throws SQLException
	 * @throws Exception
	 */
	public Lotto getLotto(long idLotto) throws SQLException, Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Lotto l = null;

		try {
			pstmt = activeConnection.prepareStatement(GET_LOTTO_BY_IDLOTTO);
			pstmt.setLong(1, idLotto);
			rs = pstmt.executeQuery();
			l = new Lotto();

			if (rs.next()) {
				fillBean(rs, l);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		}

		return l;
	}

	/*********************************************************************************************
	 * restituisce tutti i lotti attivi associati alla gara
	 * 
	 * @param idGara
	 * @return Lista di Lotto
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<Lotto> getListaLotti(long idGara) throws SQLException, Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Lotto l = null;

		List<Lotto> lista = new ArrayList<Lotto>();
		try {
			pstmt = activeConnection.prepareStatement(GET_LOTTI_BY_IDGARA);
			pstmt.setLong(1, idGara);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				l = new Lotto();
				fillBean(rs, l);
				lista.add(l);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		}

		return lista;
	}

	/**
	 * //MAC #7833 Il metodo restituisce oggetti di elemento lotto attivi che
	 * contengono solo le informazioni relative a Id Lotto, CIG e CIG_KKK Questa
	 * funzione e' da utilizzare nei casi in cui si richiede solamente gli id lotto
	 * e il CIG come informazione evitando il caricamento di altre informazioni non
	 * necessarie
	 */
	public List<Lotto> getListaCIGByIdGara(long idGara) throws SQLException, Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Lotto l = null;

		List<Lotto> lista = new ArrayList<Lotto>();
		final String GET_CIG_BY_IDGARA = "SELECT " + LOTTO.ID_LOTTO + "," + LOTTO.CIG + "," + LOTTO.CIG_KKK + ","
				+ LOTTO.COD_CATEGORIA + "," + LOTTO.IMPORTO_LOTTO + " FROM " + LOTTO.TABLE_NAME + " WHERE "
				+ LOTTO.ID_GARA + " = ? AND " + LOTTO.DATA_CANCELLAZIONE_LOTTO + " IS NULL " + " AND "
				+ LOTTO.DATA_INIB_PAGAMENTO + " IS NULL ";
		try {
			pstmt = activeConnection.prepareStatement(GET_CIG_BY_IDGARA);
			pstmt.setLong(1, idGara);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				l = new Lotto();
				l.setId_Lotto(rs.getLong(LOTTO.ID_LOTTO));
				l.setCig(rs.getString(LOTTO.CIG));
				l.setCig_kkk(rs.getString(LOTTO.CIG_KKK));
				l.setCOD_CATEGORIA(rs.getString(LOTTO.COD_CATEGORIA));
				l.setImporto_Lotto(rs.getBigDecimal(LOTTO.IMPORTO_LOTTO));
				lista.add(l);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		}

		return lista;
	}

	/*********************************************************************************************
	 * restituisce tutti i lotti attivi associati alla gara
	 * 
	 * @param idGara
	 * @return Lista di Lotto
	 * @throws SQLException
	 * @throws Exception
	 */
	public Map<String, Lotto> getMappaLotti(long idGara) throws SQLException, Exception {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Lotto l = null;

		Map<String, Lotto> mappa = new HashMap<String, Lotto>();
		try {
			pstmt = activeConnection.prepareStatement(GET_LOTTI_BY_IDGARA);
			pstmt.setLong(1, idGara);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				l = new Lotto();
				fillBean(rs, l);
				// if(l.getData_Pubblicazione() != null)
				mappa.put(l.getCIG() + l.getCIG_kkk(), l);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		}

		return mappa;
	}

	/**************************************************************************************************
	 * Ricerca delle informazioni RSSA per lotto
	 * 
	 * @param idLotto          long
	 * @param listaSAAbilitato Hashtable
	 * @return TableBean
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public TableBean getLottoByIdLottoRSSA(long idLotto, Hashtable listaSAAbilitato) throws SQLException {

		String queryRSSA = GET_LOTTO_BY_ID_LOTTO_QUERY_AMM + " AND "
				+ addInCondition(GARA.T_ID_STAZIONE_APPALTANTE, listaSAAbilitato.keys());

		logger.debug("Ricerca informazioni RSSA per lotto [" + idLotto + "] Query Eseguita[" + queryRSSA + "]");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int posCounter = 1;

		try {
			pstmt = activeConnection.prepareStatement(queryRSSA);
			pstmt.setLong(posCounter++, idLotto);

			for (Enumeration e = listaSAAbilitato.keys(); e.hasMoreElements();) {
				String currentElement = (String) e.nextElement();
				pstmt.setObject(posCounter++, currentElement);
				logger.debug("Setting value [" + currentElement + "] at position[" + posCounter + "]");
			}

			rs = pstmt.executeQuery();

			return new TableBean(rs);
		} finally {
			close(rs, pstmt);
// 			try {
// 				pstmt.close();
// 			} catch ( Exception e ) {}
// 			pstmt = null;
		}
	}

	public final static String DELETE_LOTTO = "DELETE FROM " + LOTTO.TABLE_NAME + " WHERE " + LOTTO.ID_LOTTO + "=?";

	/*********************************************************************************************
	 * elimina un lotto in base all'id
	 * 
	 * @param pkLotto long per l'id del lotto
	 * @return int - Restituisce il numero di righe eliminate
	 * @throws SQLException
	 */
	public int eliminaLotto(long pkLotto) throws SQLException {

		PreparedStatement ps = null;

		try {
			ps = activeConnection.prepareStatement(DELETE_LOTTO);
			ps.setLong(1, pkLotto);
			return ps.executeUpdate();
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
			ps = null;
		}
	}

	/********************************************************************************************
	 * ricerca le categorie scorporabili tramite id lotto, le inserisce poi nella
	 * HashMap con id categoria e descrizione
	 * 
	 * @param idLotto long
	 * @return HashMap&lt;String, String&gt;
	 * @throws SQLException
	 */
	public HashMap<String, String> findCategorieScorporabili(long idLotto) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		HashMap<String, String> result = new HashMap<String, String>();

		try {
			pstmt = activeConnection.prepareStatement(categorieScorporabiliByIdLotto);
			pstmt.setLong(1, new Long(idLotto));
			// logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Long: "+idLotto);
			logger.debug("Esecuzione query [" + categorieScorporabiliByIdLotto + "]");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String idCategoria = rs.getString(1);
				String descrizione = rs.getString(2);
				result.put(idCategoria, descrizione);
			}

			return result;
		} finally {
			close(rs, pstmt);
//			try {
//				pstmt.close();
//			} catch ( Exception e ) {}
//			pstmt = null;
		}
	}

	/***********************************************************************************************
	 * Inserisce categoirie scorporabili
	 * 
	 * @param idLotto               String per l'id del lotto
	 * @param categoriaScorporabile String[]
	 * @return int - restituisce il numero di elementi inseriti
	 * @throws SQLException
	 */
	public int updateLottoCategorieScorporabili(String idLotto, String[] categoriaScorporabile) throws SQLException {

		int result = 0;
		PreparedStatement insertStatement = null;

		try {

			deleteCategorieScorporabili(idLotto);

			insertStatement = activeConnection.prepareStatement(SET_REL_CATEGORIA_SCORPORABILE);

			for (int i = 0; i < categoriaScorporabile.length; i++) {

				insertStatement.setLong(1, Long.parseLong(idLotto));
				insertStatement.setObject(2, categoriaScorporabile[i]);

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
	 * cancella le categorie scorporabili
	 * 
	 * @param idLotto String perl'id del lotto
	 * @return int - retsituisce il numero di elementi cancellati
	 * @throws SQLException
	 */
	private int deleteCategorieScorporabili(String idLotto) throws SQLException {

		PreparedStatement psDelete = null;

		try {
			psDelete = activeConnection.prepareStatement(cancellaCategorieScorporabili);

//			 PP patch per deadlock, conversione da string a intero
			Integer intIdLotto = new Integer(idLotto);
			psDelete.setInt(1, intIdLotto);

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

	/**************************************************************************************
	 * Perfeziona il lotto
	 * 
	 * @param idLotto               String per l'id del lotto
	 * @param dataPubblicazione     String per la data della pubblicazione
	 * @param dataScadenzaPagamenti String per la data di scadenza dei pagamenti
	 * @return int - restituisce il numero dei lotti perfezionati
	 * @throws SQLException
	 */
	public int perfezionaLotto(String idLotto, String dataPubblicazione, String dataScadenzaPagamenti,
			BigDecimal importoImpresa, String oraScadenza, String dataScadenzaRichiestaInvito, String dataLetteraInvito)
			throws SQLException {

		int aggiornamento = -1;
		PreparedStatement ps = null;

		// leggo l'importo lotto
//		TableBean rsGet = getLottoByIdLottoAmm(Long.parseLong(idLotto));
//		BigDecimal impLotto = new BigDecimal(rsGet.getRow(0).getNulledField(LOTTO.IMPORTO_LOTTO));

//		BigDecimal[] infoImporti = getImportiByImportoLotto(impLotto, dataPubblicazione, (new BigInteger(rsGet.getRow(0).getNulledField(GARA.ID_MODO_REAL)).intValue()));
//		BigDecimal importoSA = infoImporti[0];
//		BigDecimal importoImpresa = infoImporti[1];

		logger.debug("Perfezionamento Lotto [" + idLotto + "]");
		logger.debug("Query Perfezionamento lotto [" + perfezionamentoLotto + "]");

		try {
			int i = 1;
			ps = activeConnection.prepareStatement(perfezionamentoLotto);

			logger.debug("Perfezionamento lotto [" + idLotto + "] DataPubblicazione [" + dataPubblicazione
					+ "] DataScadenza [" + dataScadenzaPagamenti + "]"
					+ (SimogFlags.is3030_RFWEBGL00Active()
							? "Data Scadenza Richiesta Invito [" + dataScadenzaRichiestaInvito
									+ "] Data Lettera Invito [" + dataLetteraInvito + "]"
							: ""));

			ps.setString(i++, dataPubblicazione);

			if (dataScadenzaPagamenti == null || "".equals(dataScadenzaPagamenti)) // PP per adesioni a blank
				ps.setNull(i++, Types.VARCHAR);
			else
				ps.setString(i++, dataScadenzaPagamenti);

			if (SimogFlags.is3025_RFWEBGL02Active()) {
				if (oraScadenza == null)
					ps.setNull(i++, Types.VARCHAR);
				else
					ps.setString(i++, oraScadenza);
			}

			ps.setObject(i++, 0);

			if (importoImpresa == null)
				ps.setObject(i++, new BigDecimal(0));
			else
				ps.setObject(i++, importoImpresa);

			if (SimogFlags.is3030_RFWEBGL00Active()) {

				if (dataScadenzaRichiestaInvito == null)
					ps.setNull(i++, Types.VARCHAR);
				else
					ps.setString(i++, dataScadenzaRichiestaInvito);

				if (dataLetteraInvito == null)
					ps.setNull(i++, Types.VARCHAR);
				else
					ps.setString(i++, dataLetteraInvito);
			}

			ps.setLong(i++, Long.parseLong(idLotto));

			aggiornamento = ps.executeUpdate();

			if (aggiornamento > 0) {
				logger.debug("Perfezionato Correttamente lotto idLotto[" + idLotto + "]");
				return aggiornamento;
			} else {
				throw new SQLException(Messaggi.SIMOG_LOTTO_012e + " idLotto [" + idLotto + "]");
			}
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
			ps = null;
		}
	}

	public int perfezionaLotto(String idLotto, String dataPubblicazione, String dataScadenzaPagamenti,
			BigDecimal importoImpresa, String oraScadenza) throws SQLException {
		return perfezionaLotto(idLotto, dataPubblicazione, dataScadenzaPagamenti, importoImpresa, oraScadenza, null,
				null);
	}
	
	public int perfezionamentoLottoQualificazioneSA(String idLotto, String derogaQualificazioneSA, String isFlagDerogaQualificazioneSaKo)
			throws SQLException {

		int aggiornamento = -1;
		PreparedStatement ps = null;

		// leggo l'importo lotto
//		TableBean rsGet = getLottoByIdLottoAmm(Long.parseLong(idLotto));
//		BigDecimal impLotto = new BigDecimal(rsGet.getRow(0).getNulledField(LOTTO.IMPORTO_LOTTO));

//		BigDecimal[] infoImporti = getImportiByImportoLotto(impLotto, dataPubblicazione, (new BigInteger(rsGet.getRow(0).getNulledField(GARA.ID_MODO_REAL)).intValue()));
//		BigDecimal importoSA = infoImporti[0];
//		BigDecimal importoImpresa = infoImporti[1];

		logger.debug("Inserimento deroga qualificazione SA al perfezionamento Lotto [" + idLotto + "]");
		logger.debug("Query Inserimento deroga qualificazione SA al perfezionamento Lotto [" + perfezionamentoLottoQualificazioneSA + "]");

		try {
			int i = 1;
			ps = activeConnection.prepareStatement(perfezionamentoLottoQualificazioneSA);

			logger.debug("Inserimento deroga qualificazione SA al perfezionamento lotto [" + idLotto + "] derogaQualificazioneSA [" + derogaQualificazioneSA
					+ "] isKo [" + isFlagDerogaQualificazioneSaKo + "]");

			if (derogaQualificazioneSA == null || "".equals(derogaQualificazioneSA)) {
				ps.setNull(i++, Types.VARCHAR);
			}else {
				ps.setString(i++, derogaQualificazioneSA);
			}
			

			if (isFlagDerogaQualificazioneSaKo == null || "".equals(isFlagDerogaQualificazioneSaKo)) {
				ps.setNull(i++, Types.VARCHAR);
			}else {
				ps.setString(i++, isFlagDerogaQualificazioneSaKo);
			}
				

			

			ps.setLong(i++, Long.parseLong(idLotto));

			aggiornamento = ps.executeUpdate();

			if (aggiornamento > 0) {
				logger.debug("Inserimento deroga qualificazione SA al perfezionamento inserita Correttamente lotto idLotto[" + idLotto + "]");
				return aggiornamento;
			} else {
				throw new SQLException(Messaggi.SIMOG_LOTTO_012e + " idLotto [" + idLotto + "]");
			}
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
			ps = null;
		}
	}

	/*
	 * public int perfezionaLottoPubblicazione(String idLotto, String
	 * dataPubblicazione, String dataScadenzaPagamenti) throws SQLException {
	 * 
	 * int aggiornamento = -1; PreparedStatement ps = null; //TableBean rsGet =
	 * getLottoByIdLottoAmm(Long.parseLong(idLotto)); String updateLogico =
	 * "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.DATA_PUBBLICAZIONE + " = ?,  "
	 * + LOTTO.DATA_SCADENZA_PAGAMENTI + " = ?  " + " WHERE " + LOTTO.ID_LOTTO +
	 * " = ? "; logger.debug("Perfezionamento Lotto Pubblicazione[" + idLotto +
	 * "]"); //logger.debug ( "Query Perfezionamento lotto Pubblicazione[" +
	 * perfezionamentoLotto + "]" );
	 * 
	 * try {
	 * 
	 * ps = activeConnection.prepareStatement(updateLogico); logger.debug (
	 * "Aggiornamento lotto [" + idLotto + "] DataPubblicazione [" +
	 * dataPubblicazione + "] DataScadenza [" + dataScadenzaPagamenti + "]");
	 * ps.setString(1, dataPubblicazione); ps.setString(2, dataScadenzaPagamenti);
	 * ps.setObject(3, idLotto);
	 * 
	 * aggiornamento = ps.executeUpdate();
	 * 
	 * if ( aggiornamento > 0 ) { logger.debug(
	 * "Perfezionato Correttamente lotto idLotto[" + idLotto + "]"); return
	 * aggiornamento; } else { throw new SQLException ( Messaggi.SIMOG_LOTTO_012e +
	 * " idLotto [" + idLotto + "]"); } } finally { try { ps.close(); } catch
	 * (Exception e ) {} ps = null; } }
	 */
	/***************************************************************************************************
	 * cancella il lotto
	 * 
	 * @param dataCorrente String
	 * @param idLotto      String
	 * @param pubblicato   boolean
	 * @return int - ritorna il numero di elemnti cancellati
	 * @throws SQLException
	 */
	public int cancellaLotto(String dataCorrente, String idLotto, String id_motivazione, String note,
			boolean pubblicato) throws SQLException {

		PreparedStatement ps = null;
		String campoDaModificare = pubblicato ? LOTTO.DATA_INIB_PAGAMENTO : LOTTO.DATA_CANCELLAZIONE_LOTTO;

		String cancellazioneLogica = "UPDATE " + LOTTO.TABLE_NAME + " SET " + campoDaModificare + " = ?,  "
				+ LOTTO.ID_MOTIVAZIONE + " = ?,  " + LOTTO.NOTE_CANC + " = ?  " + "WHERE " + LOTTO.ID_LOTTO + " = ? ";

		logger.debug("Cancellazione Lotto - esecuzione query [" + cancellazioneLogica + "]");

		try {
			ps = activeConnection.prepareStatement(cancellazioneLogica);

			ps.setString(1, dataCorrente);
			ps.setString(2, id_motivazione);
			ps.setString(3, note);
			ps.setLong(4, Long.parseLong(idLotto));
			int result = ps.executeUpdate();

			return result;
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
			ps = null;
		}
	}

	/******************************************************************************************************
	 * inserisce le categorie scorporabili
	 * 
	 * @param listaCategorieScorporabili String[]
	 * @param idLotto                    long per l'id del lotto
	 * @throws SQLException
	 */
	public void inserisciLottoCategoriaScorporabile(String[] listaCategorieScorporabili, long idLotto)
			throws SQLException {

		logger.debug("Inserimento (" + listaCategorieScorporabili.length + ")categorie Scorporabili per lotto ["
				+ idLotto + "]");
		PreparedStatement pstmt = null;

		try {
			pstmt = activeConnection.prepareStatement(INSERT_LOTTO_CATEGORIA_SCORPORABILE);

			for (int i = 0; i < listaCategorieScorporabili.length; i++) {
				logger.debug("Inserita categoria scorporabile [" + listaCategorieScorporabili[i] + "]");
				pstmt.setObject(1, listaCategorieScorporabili[i]);
				pstmt.setLong(2, idLotto);
				pstmt.executeUpdate();
				pstmt.clearParameters();
			}
		} finally {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
			pstmt = null;
		}
	}

	private String INSERISCI_LOTTO = "INSERT INTO " + LOTTO.TABLE_NAME + " ("
	// ticket 31047
			+ LOTTO.FLAG_PNRR_PNC + ", " + LOTTO.FLAG_PREVISIONE_QUOTA + ", " + LOTTO.FLAG_MISURE_PREMIALI + ", "
			//MEV 38205 3.04.8.1
			+ LOTTO.FLAG_USO_METODI_EDILIZIA + ", "
			//3.04.9 MEV 40610
			+ LOTTO.ID_DEROGA_QUALIFICAZIONE_SA + ", "
			//3.04.9 MEV 40610
			+ LOTTO.FLAG_IS_QUALIFICATA_KO + ", "
			//mev 37010 3.04.8.1 
			+ LOTTO.FLAG_DEROGA_ADESIONE + ", " + LOTTO.QUOTA_FEMMINILE + ", " + LOTTO.QUOTA_GIOVANILE + ", " + LOTTO.OGGETTO + ", " + LOTTO.SOMMA_URGENZA
			+ ", " + LOTTO.IMPORTO_LOTTO + ", " + LOTTO.IMPORTO_SA + ", " + LOTTO.IMPORTO_IMPRESA + ", " + LOTTO.ID_GARA
			+ ", " + LOTTO.ID_TIPOLOGIA + ", " + LOTTO.ID_CPV + ", " + LOTTO.ID_SCELTA_CONTRAENTE
			// 2846
			+ ", " + LOTTO.ID_MOTIVO
			// 2846
			+ ", " + LOTTO.ID_CATEGORIA_PREVALENTE + ", " + LOTTO.CIG + ", " + LOTTO.CIG_CICLE + ", " + LOTTO.CIG_KKK
			+ ", " + LOTTO.DATA_CREAZIONE_LOTTO + ", " + LOTTO.TIPO_CONTRATTO_LOTTO + ", " + LOTTO.FLAG_ESCLUSO + ", "
			+ LOTTO.ID_ESCLUSIONE
			// gm nuovo codice pubblicazione bando 3.0
			+ ", " + LOTTO.LUOGO_ISTAT + ", " + LOTTO.LUOGO_NUTS + ", " + LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA
			// gm fine nuovo codice pubblicazione bando 3.0
			// + ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			/* gm nuovo codice 3.0 */
			+ ", " + LOTTO.TRIENNIO_ANNO_INIZIO + ", " + LOTTO.TRIENNIO_ANNO_FINE + ", " + LOTTO.TRIENNIO_PROGRESSIVO
			+ ", " + LOTTO.ANNUALE_CUI_MININF + ", " + LOTTO.FLAG_DL50 // TICKET ALM #2845
			+ ", " + LOTTO.PRIMA_ANNUALITA // TICKET ALM #2845
			+ ", " + LOTTO.DURATA_AFFIDAMENTO_IN_GIORNI // TICKET #20057
			+ ", " + LOTTO.ID_AFF_RISERVATI // TICKET ALM #3835
			+ ", " + LOTTO.FLAG_REGIME // TICKET ALM #3836
			+ ", " + LOTTO.COD_CATEGORIA // TICKET ALM #4222 - 3.04.4
			// PP B302.3.3
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.FLAG_PREVEDE_RIP : "")
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.FLAG_RIPETIZIONE : "")
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ", " + LOTTO.CIG_ORIGINE_RIP : "")
			+ (SimogFlags.is3025_RFWEBGL02Active() ? ", " + LOTTO.ORA_SCADENZA : "")
			+ (SimogFlags.is3031_RFWEBGL02Active() ? ", " + LOTTO.FLAG_CUP : "") + ", " + LOTTO.IMPORTO_OPZIONI
			// Ticket #20058 - 09 - 02 - 21
			+ (SimogFlags.is3031_RFWEBGL02Active() ? ", " + LOTTO.DURATA_RINNOVI_RIPETIZIONI : "")
			// 2846
			//mev 37010 3.04.8.1 aggiunto ? sotto
			//mev 38205 3.04.8.1 aggiunto ? sotto
			//mev 3.04.9 MEV 40610 aggiunto due ? sotto
			+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" // TICKET ALM #2845
																								// TICKET ALM #3835
																								// TICKET ALM #3836
			// 2846
			// PP B302.3.3
			+ (SimogFlags.is30233_RFWEBGL02Active() ? ",?,?,?" : "") + (SimogFlags.is3025_RFWEBGL02Active() ? ",?" : "")
			+ (SimogFlags.is3031_RFWEBGL02Active() ? ",?" : "") + ",?,?,? " // TICKET ALM #13691 - 3.04.5
			+ ")";

	private String INSERISCI_STORIA = "INSERT INTO " + CIG_STORIA.TABLE_NAME + " (" + CIG_STORIA.APPLICAZIONE + ", "
			+ CIG_STORIA.CF_UTENTE + ", " + CIG_STORIA.CF_AMMINISTRAZIONE + ", " + CIG_STORIA.ID_STAZIONE_APPALTANTE
			+ ", " + CIG_STORIA.DATA_ATTRIBUZIONE + ", " + CIG_STORIA.CIG_CICLE + ", " + CIG_STORIA.CIG + ", "
			+ CIG_STORIA.CIG_KKK + ", " + CIG_STORIA.ADMIN_OR + ") VALUES(?,?,?,?,?,?,?,?,?)";

	/***************************************************************************************************
	 * Genea un nuovo lotto
	 * 
	 * @param gara           Gara
	 * @param lotto          Lotto
	 * @param richiedenteCIG stringa contenente il cig del richiedente
	 * @param adminOr        codice regionale dell'osservatorio
	 * @return Lotto
	 * @throws SQLException
	 */
	public Lotto creaNuovoLotto(Gara gara, Lotto lotto, String richiedenteCIG, String adminOr) throws SQLException {
		if (activeConnection.getTransactionIsolation() != Connection.TRANSACTION_SERIALIZABLE)
			throw new SQLException(
					"SIMOG_SQL_9999 - L'inserimento di un nuovo lotto deve essere fatto con una transazione serializzabile");
		logger.debug("Inserimento Nuovo Lotto - Oggetto [" + lotto.getOggetto() + "]");

		// CallableStatement insertLottoFunction = null;
		PreparedStatement insertLotto = null;

		ResultSet rs = null;
		try {
			CIGBean cigBean;
			System.out.println("===== TB: START INIZIO RICHIESTA NUOVO CIG DA UTENTE " + gara.getCF_UTENTE()
					+ " PER L'AMMINISTRAZIONE " + gara.getCF_AMMINISTRAZIONE() + " tramite app " + richiedenteCIG);

			cigBean = generaCig(new CIGBean(richiedenteCIG, gara.getCF_UTENTE(), gara.getCF_AMMINISTRAZIONE(),
					gara.getID_STAZIONE_APPALTANTE()), adminOr);
			System.out.println("===== TB: FINE RICHIESTA NUOVO CIG DA UTENTE " + gara.getCF_UTENTE()
					+ " PER L'AMMINISTRAZIONE " + gara.getCF_AMMINISTRAZIONE() + " tramite app " + richiedenteCIG);

			// BigDecimal[] infoImporti = getImportiByImportoLotto(lotto.getImporto_Lotto(),
			// PageHelper.getDBDateFromTS(getNow()),gara.getID_MODO_REAL());
			// BigDecimal importoSA = infoImporti[0];
			// BigDecimal importoImpresa = infoImporti[1];
			// lotto.setImporto_SA(infoImporti[0]);
			// lotto.setImporto_Impresa(infoImporti[1]);

			lotto.setImporto_SA(new BigDecimal(0));
			lotto.setImporto_Impresa(new BigDecimal(0));

			// FIXME: PP ATTENZIONE verificare dappertutto l'accesso al cig anche tramite
			// cig_cicle
			lotto.setCig(cigBean.getCig());
			lotto.setCig_cicle(cigBean.getCigCicle());
			lotto.setCig_kkk(cigBean.getCigKKK());
			int index = 1;

			insertLotto = activeConnection.prepareStatement(createInsertQuery(INSERISCI_LOTTO, LOTTO.ID_LOTTO));
			/////////////////////
			insertLotto.setString(index++, lotto.getFLAG_PNRR_PNC());
			insertLotto.setString(index++, lotto.getFLAG_PREVISIONE_QUOTA());
			insertLotto.setString(index++, lotto.getFLAG_MISURE_PREMIALI());
			insertLotto.setString(index++, lotto.getFLAG_USO_METODI_EDILIZIA()); //mev 38205 3.04.8.1
			insertLotto.setString(index++, lotto.getDerogaQualificazioneSA()); //3.04.9 MEV 40610
			insertLotto.setString(index++, lotto.getFlagIsQualificataKO()); //3.04.9 MEV 40610
			insertLotto.setString(index++, lotto.getFLAG_DEROGA_ADESIONE()); //mev 37010 3.04.8.1
			insertLotto.setBigDecimal(index++, lotto.getQuotaFemminile());
			insertLotto.setBigDecimal(index++, lotto.getQuotaGiovanile());
			/////////////////////
			insertLotto.setString(index++, PageHelper.replaceWordChars(lotto.getOggetto()));
			insertLotto.setString(index++, Character.toString(lotto.getSomma_Urgenza()));
			insertLotto.setBigDecimal(index++, lotto.getImporto_Lotto());
			/* ******************* */
			insertLotto.setBigDecimal(index++, lotto.getImporto_SA());
			insertLotto.setBigDecimal(index++, lotto.getImporto_Impresa());
			/* ******************* */
			insertLotto.setLong(index++, lotto.getId_Gara());
			insertLotto.setString(index++, lotto.getId_Tipologia());
			//MAC 43240 3.04.10
			insertLotto.setString(index++, (lotto.getId_CPV() != null) ? lotto.getId_CPV().trim() : null);
			//FINE MAC 43240
			insertLotto.setString(index++, lotto.getId_Scelta_Contraente());
			// 2846
			if (lotto.getID_MOTIVO_COLL_CIG() != null)
				insertLotto.setObject(index++, lotto.getID_MOTIVO_COLL_CIG());
			else
				insertLotto.setNull(index++, Types.BIGINT);
			// 2846
			insertLotto.setString(index++, lotto.getId_Categoria_prevalente());
			insertLotto.setObject(index++, lotto.getCIG()); // cig
			insertLotto.setInt(index++, lotto.getCIG_cicle()); // cig_cicle
			insertLotto.setString(index++, lotto.getCIG_kkk()); // cigKKK
			insertLotto.setString(index++, PageHelper.getCurrentDate());
			insertLotto.setString(index++, lotto.getTIPO_CONTRATTO_LOTTO());
			insertLotto.setString(index++, lotto.getFLAG_ESCLUSO());
			// TICKET ALM - 3.04.2 2005
			if (lotto.getID_ESCLUSIONE() == 0 && lotto.getID_ART_REGIME() == 0)
				insertLotto.setNull(index++, Types.INTEGER);
			else
				insertLotto.setInt(index++,
						lotto.getID_ESCLUSIONE() == 0 ? lotto.getID_ART_REGIME() : lotto.getID_ESCLUSIONE());

			// gm nuovo codice pubblicazione bando 3.0
			insertLotto.setString(index++, lotto.getLUOGO_ISTAT());
			insertLotto.setString(index++, lotto.getLUOGO_NUTS());

			if (lotto.getIMPORTO_ATTUAZIONE_SICUREZZA() != null)
				insertLotto.setObject(index++, lotto.getIMPORTO_ATTUAZIONE_SICUREZZA());
			else
				insertLotto.setNull(index++, Types.BIGINT);

			// gm fine nuovo codice pubblicazione bando 3.0

			/* gm nuovo codice 3.0 */
			insertLotto.setString(index++, lotto.getTRIENNIO_ANNO_INIZIO());
			insertLotto.setString(index++, lotto.getTRIENNIO_ANNO_FINE());
			insertLotto.setString(index++, lotto.getTRIENNIO_PROGRESSIVO());
			insertLotto.setString(index++, lotto.getANNUALE_CUI_MININF());

			// TICKET ALM #2845
			if (lotto.getFLAG_DL50() == null)
				insertLotto.setNull(index++, Types.VARCHAR);
			else
				insertLotto.setString(index++, lotto.getFLAG_DL50());

			if (lotto.getPRIMA_ANNUALITA() == null)
				insertLotto.setNull(index++, Types.VARCHAR);
			else
				insertLotto.setString(index++, lotto.getPRIMA_ANNUALITA());
			// FINE TICKET ALM #2845

			// Ticket #20057 - 09 - 02 - 21
			if (lotto.getDurataAffidamentoGiorni() != -1)
				insertLotto.setInt(index++, lotto.getDurataAffidamentoGiorni());
			else
				insertLotto.setNull(index++, Types.BIGINT);

			// TICKET ALM #3835
			if (lotto.getID_AFF_RISERVATI() == 0)
				insertLotto.setNull(index++, Types.INTEGER);
			else
				insertLotto.setInt(index++, lotto.getID_AFF_RISERVATI());
			// FINE TICKET ALM #3835

			// TICKET ALM #3836
			if (lotto.getFLAG_REGIME() == null)
				insertLotto.setNull(index++, Types.VARCHAR);
			else
				insertLotto.setString(index++, lotto.getFLAG_REGIME());

			// TICKET ALM - 3.04.4
			if (lotto.getCOD_CATEGORIA() == null || "".equals(lotto.getCOD_CATEGORIA()))
				insertLotto.setNull(index++, Types.INTEGER);
			else
				insertLotto.setLong(index++, Integer.parseInt(lotto.getCOD_CATEGORIA()));

			// PP B302.3.3
			if (SimogFlags.is30233_RFWEBGL02Active()) {
				if (lotto.getFLAG_PREVEDE_RIP() == null || "".equals(lotto.getFLAG_PREVEDE_RIP()))
					insertLotto.setNull(index++, Types.VARCHAR);
				else
					insertLotto.setString(index++, lotto.getFLAG_PREVEDE_RIP());

				// TICKET ALM - 3.04.4
				// Se e' stato indicato un motivo collegamento, imposta il flag secondo la
				// selezione effettuata
				if (lotto.getID_MOTIVO_COLL_CIG() == null || "".equals(lotto.getID_MOTIVO_COLL_CIG())) {
					if (lotto.getFLAG_RIPETIZIONE() == null || "".equals(lotto.getFLAG_RIPETIZIONE()))
						insertLotto.setNull(index++, Types.VARCHAR);
					else
						insertLotto.setString(index++, lotto.getFLAG_RIPETIZIONE());
				} else {
					insertLotto.setString(index++,
							!lotto.getID_MOTIVO_COLL_CIG().equals(Costanti.COLL_CIG_RIP) ? "N" : "S"); // TICKET ALM
																										// #10944 (MAC)
				}
				// FINE TICKET ALM - 3.04.4

				insertLotto.setString(index++, lotto.getCIG_ORIGINE_RIP());
			}

			if (lotto.getORA_SCADENZA() == null || "".equals(lotto.getORA_SCADENZA()))
				insertLotto.setNull(index++, Types.VARCHAR);
			else
				insertLotto.setString(index++, lotto.getORA_SCADENZA());

			if (lotto.getFLAG_CUP() == null || "".equals(lotto.getFLAG_CUP()))
				insertLotto.setNull(index++, Types.VARCHAR);
			else
				insertLotto.setString(index++, lotto.getFLAG_CUP());

			// TICKET ALM 13691 - 3.04.5
			if (lotto.getImporto_opzioni() != null)
				insertLotto.setBigDecimal(index++, lotto.getImporto_opzioni());
			else
				insertLotto.setNull(index++, Types.BIGINT);

			// Ticket #20058 - 09 - 02 - 21
			if (lotto.getDurataRipetizioni() != -1)
				insertLotto.setInt(index++, lotto.getDurataRipetizioni());
			else
				insertLotto.setNull(index++, Types.BIGINT);

			insertLotto.execute();
			rs = insertLotto.getResultSet();
			rs.next();
			long idLotto = rs.getLong(LOTTO.ID_LOTTO);

			lotto.setId_Lotto(idLotto);

			return lotto;
		} finally {
			// try {
			close(rs, insertLotto);
//				insertLotto.close();
			// } catch ( Exception e ) {e.printStackTrace();}
			// insertLotto = null;
		}
	}

	private String UPDATE_CIG = "UPDATE " + CIG_INDEX.TABLE_NAME + " SET " + CIG_INDEX.CIG + " =(" + "case "
			+ CIG_INDEX.CIG
			// + " WHEN 9999999 THEN 0001001 " PRECEDENTE
			+ " WHEN 9999999 THEN 167772160 " // nel db continuo a salvarlo come numero intero corrispondente ad A000000
			+ " ELSE " + CIG_INDEX.CIG + "+ 1   END)" + ", " + CIG_INDEX.CIG_CICLE + " =(" + "case " + CIG_INDEX.CIG
			+ " WHEN  9999999 THEN " + CIG_INDEX.CIG_CICLE + "+1" + " ELSE " + CIG_INDEX.CIG_CICLE + " END)";
	private String GET_CIG = "SELECT " + CIG_INDEX.CIG + "," + CIG_INDEX.CIG_CICLE + " FROM " + CIG_INDEX.TABLE_NAME;

	/**
	 * Metodo per la generazione di un CIG
	 * 
	 * @param bean    CIGBean
	 * @param adminOr String
	 * @return CIGBean
	 * @throws SQLException
	 */
	public synchronized CIGBean generaCig(CIGBean bean, String adminOr) throws SQLException {
		CIGBean cigBean = new CIGBean(bean.getApplicazione(), bean.getCfUtente(), bean.getCfAmministrazione(),
				bean.getCfStazione());
		//log
		logger.error("genera cig log 3.04.10.2---");
		logger.error("bean getApplicazione() ---> " + bean.getApplicazione());
		logger.info("genera cig log 3.04.10.2---");
		logger.info("bean getApplicazione() ---> " + bean.getApplicazione());
		System.out.println("genera cig log 3.04.10.2---");
		System.out.println("bean getApplicazione() ---> " + bean.getApplicazione());
		//log

		System.out.println("===== TB: 0.INIZIO RICHIESTA NUOVO CIG DA UTENTE " + bean.getCfUtente()
				+ " PER L'AMMINISTRAZIONE " + bean.getCfAmministrazione() + " tramite app " + bean.getApplicazione());

		if (activeConnection.getTransactionIsolation() != Connection.TRANSACTION_SERIALIZABLE)
			throw new SQLException("SIMOG_SQL_9999 - Transazione non serializzata, cig non generato");
		PreparedStatement upCig = null;
		PreparedStatement getCig = null;
		PreparedStatement insertStoria = null;
		CallableStatement cs = null;
		CIGUtils cigUtils = new CIGUtils();
		ResultSet rs = null;
		long cig = 0; // ANNNNNN in integer form
		int cicle = 0;
		String letter = "";
		try {
			long startTime = System.currentTimeMillis();
//			if (!SimogProperties.getInstance().getCIGFromDB()) { 3.04.11 commentata sezione vecchia generazione cig
//				//log
//				logger.error("getCIGFromDB ---> " + SimogProperties.getInstance().getCIGFromDB());
//				logger.info("getCIGFromDB ---> " + SimogProperties.getInstance().getCIGFromDB());
//				System.out.println("getCIGFromDB ---> " + SimogProperties.getInstance().getCIGFromDB());
//				//log
//				upCig = activeConnection.prepareStatement(UPDATE_CIG);
//				getCig = activeConnection.prepareStatement(GET_CIG);
//				logger.debug("update cig index: " + UPDATE_CIG);
//				System.out.println("===== TB: 1.RICHIESTA NUOVO CIG DA UTENTE " + bean.getCfUtente()
//						+ " - UPDATE TABELLA CIG_INDEX IN CORSO");
//				upCig.execute();
//				System.out.println("===== TB: 2.RICHIESTA NUOVO CIG DA UTENTE " + bean.getCfUtente()
//						+ " - UPDATE TABELLA CIG_INDEX ESEGUITA");
//				logger.debug("getting the new cig: " + GET_CIG);
//
//				// eseguo la query e ricavo cig e cig_cicle
//				System.out.println("===== TB: 3.RICHIESTA NUOVO CIG DA UTENTE " + bean.getCfUtente()
//						+ " - SELECT TABELLA CIG_INDEX IN CORSO");
//				rs = getCig.executeQuery();
//				rs.next();
//				cig = rs.getLong(CIG_INDEX.CIG);
//				cicle = rs.getInt(CIG_INDEX.CIG_CICLE);
//				System.out.println("===== TB: 4.RICHIESTA NUOVO CIG DA UTENTE " + bean.getCfUtente()
//						+ " - SELECT TABELLA CIG_INDEX ESEGUITA. OTTENUTO CIG nr. " + cig);
//				//log
//				logger.error("CIG ---> " + cig);
//				logger.info("CIG ---> " + cig);
//				System.out.println("CIG ---> " + cig);
//				logger.error("cicle ---> " + cicle);
//				logger.info("cicle ---> " + cicle);
//				System.out.println("cicle ---> " + cicle);
//				//log
//				// calcolo cig_kkk
//			} else {
				cs = activeConnection.prepareCall("{call simog.dbo.sp_cig_index(?,?,?,?)}");
				cs.registerOutParameter(1, Types.BIGINT);
				cs.registerOutParameter(2, Types.INTEGER);
				cs.registerOutParameter(3, Types.VARCHAR);
				cs.registerOutParameter(4, Types.VARCHAR);
				cs.executeUpdate();
				cig = cs.getLong(1);
				cicle = cs.getInt(2);
				letter = cs.getString(3);
				String err = cs.getString(4);
				//log
				logger.error("calcolo cig_kkk ---");
				logger.info("calcolo cig_kkk ---");
				System.out.println("calcolo cig_kkk ---");
				logger.error("CIG ---> " + cig);
				logger.info("CIG ---> " + cig);
				System.out.println("CIG ---> " + cig);
				logger.error("cicle ---> " + cicle);
				logger.info("cicle ---> " + cicle);
				System.out.println("cicle ---> " + cicle);
				logger.error("letter ---> " + letter);
				logger.info("letter ---> " + letter);
				System.out.println("letter ---> " + letter);
				//log
				if (err != null && !err.isEmpty())
					throw new SQLException("SIMOG_SQL_500 - errore imprevisto dal db: " + err);
			//}

			// String cigKKK = Long.toHexString(kkk).toUpperCase();

			String kkk = "";
//			if (cig >= 9999999) {
////				String parsedCig = Long.toString(cig);
////				long aDec = Integer.parseInt(parsedCig.substring(0, 2));
////				long nnnnnnDec = Integer.parseInt(parsedCig.substring(2, parsedCig.length()));
////				kkk = ((nnnnnnDec + aDec) * 211) % 4091; // KKK = Hex((Dec(NNNNNN) + Ord (A)) * 211 mod 4091)
////				cigBean.setCig(Long.toHexString(cig).toUpperCase()); // converto in esadecimale per averlo nella forma
////																		// ANNNNNN
//
//				// Nuova versione
//				// Conversione del CIG INDEX in esadecimale
//				kkk=cigUtils.calcolaKKK(cig); 
//				cigBean.setCig(Long.toHexString(cig));
//				
//				System.out.println("TEST " + cig);
//			} else {
//				kkk = (cig * 211) % 4091;
//				// la lunghezza del cig deve essere di 7 quindi...
////				cigBean.setCig(Long.toString(cig));
//				
//				String hexCig = Long.toHexString(cig);
//				
//				int number = hexCig.length() - 6;
//				
//				if(number < 6) {
//					String.format("%06x", hexCig);
//				}
//				
//				cigBean.setCig(letter + Long.toHexString(cig)); //FFFFFF MAX VALUE
//				
////				cigBean.setCig("0000000" + cigBean.getCig());
////				cigBean.setCig(cigBean.getCig().substring(cigBean.getCig().length() - 7));
//			}

			if (letter.isEmpty()) { // VECCHIO ALGORITMO
//				String fill = String.format("%07x", cig);
				logger.error("lettera vuota ---");
				logger.info("lettera vuota ---");
				System.out.println("lettera vuota ---");
				

				String fill = StringUtils.leftPad(String.valueOf(cig), 7, "0");

				kkk = cigUtils.calcolaVecchioKKK(cig);
				logger.error("lettera vuota --- kkk " + kkk);
				logger.info("lettera vuota --- kkk " + kkk);
				System.out.println("lettera vuota --- kkk " + kkk);
				logger.error("lettera vuota --- fill " + fill);
				logger.info("lettera vuota --- fill " + fill);
				System.out.println("lettera vuota --- fill " + fill);

				cigBean.setCig(fill);
			} else { // NUOVO ALGORITMO
				logger.error("NUOVO ALGORITMO ---");
				logger.info("NUOVO ALGORITMO ---");
				System.out.println("NUOVO ALGORITMO ---");
				String hexCig = cigUtils.calcolaCig(letter, cig);

				cigBean.setCig(hexCig); // FFFFFF MAX VALUE

				kkk = cigUtils.calcolaKKK(letter, cig);
				logger.error("NUOVO ALGORITMO --- hexCig " + hexCig);
				logger.info("NUOVO ALGORITMO --- hexCig " + hexCig);
				System.out.println("NUOVO ALGORITMO --- hexCig " + hexCig);
				logger.error("NUOVO ALGORITMO --- fill " + kkk);
				logger.info("NUOVO ALGORITMO --- fill " + kkk);
				System.out.println("NUOVO ALGORITMO --- fill " + kkk);
			}

			cigBean.setCigKKK(kkk);
			cigBean.setCigCicle(cicle);
			System.out.println("===== TB: 5.RICHIESTA NUOVO CIG DA UTENTE " + bean.getCfUtente()
					+ " - ELABORATO CIG_KKK. IL CIG GENERATO E' " + cigBean.getCig() + cigBean.getCigKKK());
			// logger.debug(ObjectIntrospector.propertiesInfo(CIGBean.class, cigBean));
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			System.out.println("===== TB: 5.1 TEMPO DI ESECUZIONE RILASCIO CIG " + cigBean.getCig()
					+ cigBean.getCigKKK() + " : " + elapsedTime);
			insertStoria = activeConnection.prepareStatement(createInsertQuery(INSERISCI_STORIA, CIG_STORIA.ID_RECORD));
			int indexs = 1;
			insertStoria.setString(indexs++, cigBean.getApplicazione());
			insertStoria.setString(indexs++, cigBean.getCfUtente());
			insertStoria.setString(indexs++, cigBean.getCfAmministrazione());
			insertStoria.setString(indexs++, cigBean.getCfStazione());
			insertStoria.setObject(indexs++, getNow());
			insertStoria.setInt(indexs++, cigBean.getCigCicle());
			insertStoria.setString(indexs++, cigBean.getCig());
			insertStoria.setString(indexs++, cigBean.getCigKKK());
			insertStoria.setString(indexs++, adminOr);

			insertStoria.execute();
			// insertStoria.close();
			System.out.println("===== TB: 6.RICHIESTA NUOVO CIG DA UTENTE " + bean.getCfUtente()
					+ " - ELABORATO CIG_KKK. ESEGUITA INSERT IN CIG_STORIA PER IL CIG " + cigBean.getCig()
					+ cigBean.getCigKKK());
			return cigBean;
		} finally {
			close(rs, insertStoria);
			close(null, upCig);
			close(null, getCig);
			close(cs);
		}
	}

	/******************************************************************************************************
	 * modifica il lotto
	 * 
	 * @param lottoDaAggiornare : Lotto contenente i parametri da inserire
	 * @return int : indica il numero di elementi aggiornati
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int modificaLotto(Lotto lottoDaAggiornare) throws SQLException, ClassNotFoundException {

		logger.debug("Modifica lotto [" + lottoDaAggiornare + "]");
		PreparedStatement updateLottoFunction = null;

		String ldata = PageHelper.getDBDateFromTS(getNow());
		if (lottoDaAggiornare.getData_Pubblicazione() != null && !"".equals(lottoDaAggiornare.getData_Pubblicazione()))
			ldata = lottoDaAggiornare.getData_Pubblicazione();

		int idx = 0;
		try {
			// leggo la modalita' di realizzazione
			// TableBean rsGet = getLottoByIdLottoAmm(lottoDaAggiornare.getId_Lotto());

//			BigDecimal[] infoImporti = getImportiByImportoLotto(lottoDaAggiornare.getImporto_Lotto(), 
//																ldata,
//																(new BigInteger(rsGet.getRow(0).getNulledField(GARA.ID_MODO_REAL)).intValue()));
			// BigDecimal importoSA = infoImporti[0];
			// BigDecimal importoImpresa = infoImporti[1];
//			lottoDaAggiornare.setImporto_Impresa(infoImporti[1]);
//			lottoDaAggiornare.setImporto_SA(infoImporti[0]);

			updateLottoFunction = activeConnection.prepareStatement(aggiornamentoLotto);

			updateLottoFunction.setObject(++idx, PageHelper.replaceWordChars(lottoDaAggiornare.getOggetto()));
			updateLottoFunction.setObject(++idx, lottoDaAggiornare.getFLAG_PNRR_PNC());
			updateLottoFunction.setObject(++idx, lottoDaAggiornare.getFLAG_PREVISIONE_QUOTA());
			updateLottoFunction.setObject(++idx, lottoDaAggiornare.getFLAG_USO_METODI_EDILIZIA()); //mev 38205 3.04.8.1
			updateLottoFunction.setObject(++idx, lottoDaAggiornare.getDerogaQualificazioneSA()); //mad alla mev 40610 3.04.9.1
			//updateLottoFunction.setObject(++idx, lottoDaAggiornare.getFlagIsQualificataKO()); //mad alla mev 40610 3.04.9.1
			updateLottoFunction.setObject(++idx, lottoDaAggiornare.getFLAG_DEROGA_ADESIONE()); //mev 37010 3.04.8.1
			updateLottoFunction.setObject(++idx, lottoDaAggiornare.getFLAG_MISURE_PREMIALI());
			updateLottoFunction.setBigDecimal(++idx, lottoDaAggiornare.getQuotaFemminile());
			updateLottoFunction.setBigDecimal(++idx, lottoDaAggiornare.getQuotaGiovanile());
			updateLottoFunction.setObject(++idx, lottoDaAggiornare.getImporto_Lotto());
			updateLottoFunction.setObject(++idx, lottoDaAggiornare.getId_Tipologia());
			//MAC 43240 3.04.10
			updateLottoFunction.setObject(++idx, (lottoDaAggiornare.getId_CPV() != null) ? lottoDaAggiornare.getId_CPV().trim() : null);
			updateLottoFunction.setString(++idx, lottoDaAggiornare.getId_Scelta_Contraente());
			// TICKET ALM - 3.04.3 #2846
			updateLottoFunction.setString(++idx, lottoDaAggiornare.getID_MOTIVO_COLL_CIG());
			// FINE TICKET ALM - 3.04.3 #2846
			updateLottoFunction.setObject(++idx, lottoDaAggiornare.getId_Categoria_prevalente());
			updateLottoFunction.setObject(++idx, lottoDaAggiornare.getImporto_SA());
			if (lottoDaAggiornare.getImporto_Impresa() == null)
				updateLottoFunction.setObject(++idx, new BigDecimal(0));
			else
				updateLottoFunction.setObject(++idx, lottoDaAggiornare.getImporto_Impresa());

			updateLottoFunction.setObject(++idx, lottoDaAggiornare.getTIPO_CONTRATTO_LOTTO());
			updateLottoFunction.setObject(++idx, lottoDaAggiornare.getFLAG_ESCLUSO());

			// TICKET ALM - 3.04.2 2005
			if (lottoDaAggiornare.getID_ESCLUSIONE() == 0 && lottoDaAggiornare.getID_ART_REGIME() == 0)
				updateLottoFunction.setNull(++idx, Types.INTEGER);
			else
				updateLottoFunction.setInt(++idx,
						lottoDaAggiornare.getID_ESCLUSIONE() == 0 ? lottoDaAggiornare.getID_ART_REGIME()
								: lottoDaAggiornare.getID_ESCLUSIONE());

			/* gm nuovo codice 3.0 */
			if (lottoDaAggiornare.getTRIENNIO_ANNO_INIZIO() != null)
				updateLottoFunction.setObject(++idx, lottoDaAggiornare.getTRIENNIO_ANNO_INIZIO());
			else
				updateLottoFunction.setNull(++idx, Types.VARCHAR);

			if (lottoDaAggiornare.getTRIENNIO_ANNO_FINE() != null)
				updateLottoFunction.setObject(++idx, lottoDaAggiornare.getTRIENNIO_ANNO_FINE());
			else
				updateLottoFunction.setNull(++idx, Types.VARCHAR);

			if (lottoDaAggiornare.getTRIENNIO_PROGRESSIVO() != null)
				updateLottoFunction.setObject(++idx, lottoDaAggiornare.getTRIENNIO_PROGRESSIVO());
			else
				updateLottoFunction.setNull(++idx, Types.VARCHAR);

			if (lottoDaAggiornare.getANNUALE_CUI_MININF() != null)
				updateLottoFunction.setObject(++idx, lottoDaAggiornare.getANNUALE_CUI_MININF());
			else
				updateLottoFunction.setNull(++idx, Types.VARCHAR);

			// gm nuovo codice pubblicazione bando 3.0
			if (lottoDaAggiornare.getLUOGO_ISTAT() != null)
				updateLottoFunction.setObject(++idx, lottoDaAggiornare.getLUOGO_ISTAT());
			else
				updateLottoFunction.setNull(++idx, Types.VARCHAR);
			if (lottoDaAggiornare.getLUOGO_NUTS() != null)
				updateLottoFunction.setObject(++idx, lottoDaAggiornare.getLUOGO_NUTS());
			else
				updateLottoFunction.setNull(++idx, Types.VARCHAR);
			if (lottoDaAggiornare.getIMPORTO_ATTUAZIONE_SICUREZZA() != null)
				updateLottoFunction.setObject(++idx, lottoDaAggiornare.getIMPORTO_ATTUAZIONE_SICUREZZA());
			else
				updateLottoFunction.setNull(++idx, Types.BIGINT);
			// gm fine nuovo codice pubblicazione bando 3.0

			updateLottoFunction.setString(++idx, String.valueOf(lottoDaAggiornare.getSomma_Urgenza()));

			// PP B302.3.3

			if (lottoDaAggiornare.getFLAG_PREVEDE_RIP() == null || "".equals(lottoDaAggiornare.getFLAG_PREVEDE_RIP()))
				updateLottoFunction.setNull(++idx, Types.VARCHAR);
			else
				updateLottoFunction.setString(++idx, lottoDaAggiornare.getFLAG_PREVEDE_RIP());

			// TICKET ALM - 3.04.4
			// Se e' stato indicato un motivo collegamento, imposta il flag secondo la
			// selezione effettuata
			if (lottoDaAggiornare.getID_MOTIVO_COLL_CIG() == null
					|| "".equals(lottoDaAggiornare.getID_MOTIVO_COLL_CIG())) {
				if (lottoDaAggiornare.getFLAG_RIPETIZIONE() == null
						|| "".equals(lottoDaAggiornare.getFLAG_RIPETIZIONE()))
					updateLottoFunction.setNull(++idx, Types.VARCHAR);
				else
					updateLottoFunction.setString(++idx, lottoDaAggiornare.getFLAG_RIPETIZIONE());
			} else {
				updateLottoFunction.setString(++idx,
						!lottoDaAggiornare.getID_MOTIVO_COLL_CIG().equals(Costanti.COLL_CIG_RIP) ? "N" : "S"); // TICKET
																												// ALM
																												// #10944
																												// (MAC)
			}
			// FINE TICKET ALM - 3.04.4

			updateLottoFunction.setString(++idx, lottoDaAggiornare.getCIG_ORIGINE_RIP());

//               if(lottoDaAggiornare.getORA_SCADENZA() == null || "".equals(lottoDaAggiornare.getORA_SCADENZA()))
//                   updateLottoFunction.setNull(++idx, Types.VARCHAR);
//               else
//                   updateLottoFunction.setString(++idx, lottoDaAggiornare.getORA_SCADENZA());

			if (lottoDaAggiornare.getFLAG_CUP() == null || "".equals(lottoDaAggiornare.getFLAG_CUP()))
				updateLottoFunction.setNull(++idx, Types.VARCHAR);
			else
				updateLottoFunction.setString(++idx, lottoDaAggiornare.getFLAG_CUP());

			// TICKET ALM #2845
			updateLottoFunction.setString(++idx, lottoDaAggiornare.getFLAG_DL50());
			updateLottoFunction.setString(++idx,
					lottoDaAggiornare.getPRIMA_ANNUALITA() == null ? "" : lottoDaAggiornare.getPRIMA_ANNUALITA());
			// FINE TICKET ALM #2845

			// TICKET ALM #3835
			if (lottoDaAggiornare.getID_AFF_RISERVATI() == 0)
				updateLottoFunction.setNull(++idx, Types.INTEGER);
			else
				updateLottoFunction.setInt(++idx, lottoDaAggiornare.getID_AFF_RISERVATI());
			// FINE TICKET ALM #3835

			// TICKET ALM #3836
			updateLottoFunction.setString(++idx, lottoDaAggiornare.getFLAG_REGIME());

			// TICKET ALM - 3.04.4
			if (lottoDaAggiornare.getCOD_CATEGORIA() == null || "".equals(lottoDaAggiornare.getCOD_CATEGORIA()))
				updateLottoFunction.setNull(++idx, Types.INTEGER);
			else
				updateLottoFunction.setLong(++idx, Integer.parseInt(lottoDaAggiornare.getCOD_CATEGORIA()));

			// TICKET ALM 13691 - 3.04.5
			if (lottoDaAggiornare.getImporto_opzioni() != null)
				updateLottoFunction.setObject(++idx, lottoDaAggiornare.getImporto_opzioni());
			else
				updateLottoFunction.setNull(++idx, Types.BIGINT);

			// Ticket #20058 - 09 - 02 - 21

			updateLottoFunction.setObject(++idx, lottoDaAggiornare.getDurataRipetizioni());

			// Ticket #20057
			updateLottoFunction.setObject(++idx, lottoDaAggiornare.getDurataAffidamentoGiorni());

			updateLottoFunction.setLong(++idx, lottoDaAggiornare.getId_Lotto());

			int result = updateLottoFunction.executeUpdate();
			logger.debug("Eseguita query [" + aggiornamentoLotto + "] per idLotto [" + lottoDaAggiornare.getId_Lotto()
					+ "]");
			logger.debug("Completato aggiornamento del lotto [" + lottoDaAggiornare.getId_Lotto() + "] CIG ["
					+ lottoDaAggiornare.getCIG() + "] Esito [" + result + "]");

			return result;
		} finally {
			try {
				updateLottoFunction.close();
			} catch (Exception e) {
			}
			updateLottoFunction = null;
		}
	}

	private final String UPDATE_LOTTO_INFO = "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.TIPO_CONTRATTO_LOTTO + "=?"
			+ ", " + LOTTO.FLAG_ESCLUSO + "=?" + ", " + LOTTO.ID_ESCLUSIONE + "=?" + " WHERE " + LOTTO.ID_LOTTO
			+ " = ?";

	/******************************************************************************************************
	 * modifica i campi lotto modificati da scheda dati comuni
	 * 
	 * @param lottoDaAggiornare : Lotto contenente i parametri da inserire
	 * @throws SQLException
	 */
	public void updateCampiInfoComuni(InfoComuniBean bean) throws SQLException {

		PreparedStatement updateLottoFunction = null;

		int idx = 0;
		try {

			updateLottoFunction = activeConnection.prepareStatement(UPDATE_LOTTO_INFO);
			updateLottoFunction.setObject(++idx, bean.getTipoContratto());
			updateLottoFunction.setObject(++idx, bean.getFLAG_ESCLUSO());

			if (bean.getID_ESCLUSIONE() == 0)
				updateLottoFunction.setNull(++idx, Types.INTEGER);
			else
				updateLottoFunction.setInt(++idx, bean.getID_ESCLUSIONE());

			updateLottoFunction.setLong(++idx, bean.getIdLotto());

			int result = updateLottoFunction.executeUpdate();
		} finally {
			try {
				updateLottoFunction.close();
			} catch (Exception e) {
			}
			updateLottoFunction = null;
		}
	}

	private final String UPDATE_LOTTO_ESCLUSO = "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.FLAG_ESCLUSO + " = ? "
			+ ", " + LOTTO.ID_ESCLUSIONE + " = ? " + ", " + LOTTO.IMPORTO_IMPRESA + " = ? " + ", " + LOTTO.FLAG_REGIME
			+ " = ? "// TICKET ALM - 3.04.2 2805
			+ " WHERE " + LOTTO.ID_LOTTO + " = ?";

	private final String UPDATE_RIPETIZIONI = "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.FLAG_PREVEDE_RIP + " = ? "
			+ ", " + LOTTO.FLAG_RIPETIZIONE + " = ? " + ", " + LOTTO.ID_MOTIVO + " = ? " // TICKET ALM - 3.04.3
			+ ", " + LOTTO.CIG_ORIGINE_RIP + " = ? " + ", " + LOTTO.DURATA_RINNOVI_RIPETIZIONI + " = ? " // Ticket
																											// #20058 -
																											// 09 - 02 -
																											// 21
			+ " WHERE " + LOTTO.ID_LOTTO + " = ?";
	
	//MEV 37010 3.04.8.1
	private final String UPDATE_PARI_OPPORTUNITA = "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.FLAG_PNRR_PNC + " = ? "
			+ ", " + LOTTO.FLAG_DEROGA_ADESIONE + " = ? " + ", " + LOTTO.FLAG_PREVISIONE_QUOTA+ " = ? " 
			+ ", " + LOTTO.FLAG_MISURE_PREMIALI + " = ? " + ", " + LOTTO.QUOTA_GIOVANILE + " = ? " + ", " + LOTTO.QUOTA_FEMMINILE+ " = ? " 
																											
			+ " WHERE " + LOTTO.ID_LOTTO + " = ?";
	
	//MEV 37010 3.04.8.1
	private final String UPDATE_DATI_PERFEZIONAMENTO_FASE_1 = "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.DATA_SCADENZA_RICHIESTA_INVITO + " = ? " 
																											
			+ " WHERE " + LOTTO.ID_GARA + " = ?";
	
	private final String UPDATE_DATI_PERFEZIONAMENTO_FASE_2 = "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.DATA_SCADENZA_PAGAMENTI + " = ? "
			+ ", " + LOTTO.ORA_SCADENZA+ " = ? " 
																											
			+ " WHERE " + LOTTO.ID_GARA + " = ?";
	
	//MEV 53643 3.04.13
	private final String UPDATE_CPV= "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.ID_CPV + " = ? "
			
																											
			+ " WHERE " + LOTTO.ID_LOTTO + " = ?";
	
	/* MAD 68089 3.04.16 Inizio */
	private final String UPDATE_CAT_SOA_PREVAL = "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.ID_CATEGORIA_PREVALENTE + " = ?  WHERE " + LOTTO.ID_LOTTO + " = ? ";
	private final String INS_REL_LOTTO_CAT_SCORP_STO = "INSERT INTO REL_LOTTO_CATEGORIA_SCORPORABILE_STORICO ( Id_Lotto, Id_Categoria, data_modifica ) VALUES ( ?, ?, getdate() ) ";
	/* MAD 68089 3.04.16 Inizio */

	/******************************************************************************************************
	 * modifica i campi lotto modificati da modifica contratto escluso
	 * 
	 * @param lottoDaAggiornare : Lotto contenente i parametri da inserire
	 * @throws SQLException
	 */
	public void updateCampiEsclusoLotto(Lotto lotto) throws SQLException {

		PreparedStatement updateLottoFunction = null;

		int idx = 0;
		try {

			updateLottoFunction = activeConnection.prepareStatement(UPDATE_LOTTO_ESCLUSO);

			updateLottoFunction.setObject(++idx, lotto.getFLAG_ESCLUSO());

			// TICKET ALM - 3.04.2 2005
			if (lotto.getID_ESCLUSIONE() == 0 && lotto.getID_ART_REGIME() == 0)
				updateLottoFunction.setNull(++idx, Types.INTEGER);
			else
				updateLottoFunction.setInt(++idx,
						lotto.getID_ESCLUSIONE() == 0 ? lotto.getID_ART_REGIME() : lotto.getID_ESCLUSIONE());

			if (lotto.getImporto_Impresa() == null)
				updateLottoFunction.setObject(++idx, new BigDecimal(0));
			else
				updateLottoFunction.setObject(++idx, lotto.getImporto_Impresa());

			// TICKET ALM - 3.04.2 2805
			updateLottoFunction.setObject(++idx, lotto.getFLAG_REGIME());

			updateLottoFunction.setLong(++idx, lotto.getId_Lotto());

			int result = updateLottoFunction.executeUpdate();
		} finally {
			try {
				updateLottoFunction.close();
			} catch (Exception e) {
			}
			updateLottoFunction = null;
		}
	}

	/******************************************************************************************************
	 * modifica i campi lotto modificati da modifica ripetizioni
	 * 
	 * @param lottoDaAggiornare : Lotto contenente i parametri da inserire
	 * @throws SQLException
	 */
	public void updateRipetizioni(Lotto lotto) throws SQLException {

		PreparedStatement updateLottoFunction = null;

		int idx = 0;
		try {

			updateLottoFunction = activeConnection.prepareStatement(UPDATE_RIPETIZIONI);

			if (lotto.getFLAG_PREVEDE_RIP() == null || "".equals(lotto.getFLAG_PREVEDE_RIP()))
				updateLottoFunction.setNull(++idx, Types.VARCHAR);
			else
				updateLottoFunction.setString(++idx, lotto.getFLAG_PREVEDE_RIP());

			// TICKET ALM - 3.04.3
			// Se e' stato indicato un motivo collegamento, imposta il flag secondo la
			// selezione effettuata
			if (lotto.getID_MOTIVO_COLL_CIG() == null || "".equals(lotto.getID_MOTIVO_COLL_CIG())) {
				if (lotto.getFLAG_RIPETIZIONE() == null || "".equals(lotto.getFLAG_RIPETIZIONE()))
					updateLottoFunction.setNull(++idx, Types.VARCHAR);
				else
					updateLottoFunction.setString(++idx, lotto.getFLAG_RIPETIZIONE());

				updateLottoFunction.setNull(++idx, Types.VARCHAR);
			} else {
				updateLottoFunction.setString(++idx,
						!lotto.getID_MOTIVO_COLL_CIG().equals(Costanti.COLL_CIG_RIP) ? "N" : "S"); // TICKET ALM #10944
																									// (MAC)
				updateLottoFunction.setString(++idx, lotto.getID_MOTIVO_COLL_CIG());
			}
			// FINE TICKET ALM - 3.04.4

			updateLottoFunction.setString(++idx, lotto.getCIG_ORIGINE_RIP());

			// Ticket #20058 - 09 - 02 - 21
			updateLottoFunction.setInt(++idx, lotto.getDurataRipetizioni());

			updateLottoFunction.setLong(++idx, lotto.getId_Lotto());

			int result = updateLottoFunction.executeUpdate();
		} finally {
			try {
				updateLottoFunction.close();
			} catch (Exception e) {
			}
			updateLottoFunction = null;
		}
	}
	
	//MEV 37010 3.04.8.1
		public void updatePariOpportunita(Lotto lotto) throws SQLException {

			PreparedStatement updateLottoFunction = null;

			int idx = 0;
			try {

				updateLottoFunction = activeConnection.prepareStatement(UPDATE_PARI_OPPORTUNITA);

				updateLottoFunction.setString(++idx, lotto.getFLAG_PNRR_PNC());
				updateLottoFunction.setString(++idx, lotto.getFLAG_DEROGA_ADESIONE());
				updateLottoFunction.setString(++idx, lotto.getFLAG_PREVISIONE_QUOTA());
				updateLottoFunction.setString(++idx, lotto.getFLAG_MISURE_PREMIALI());
				updateLottoFunction.setBigDecimal(++idx, lotto.getQuotaGiovanile());
				updateLottoFunction.setBigDecimal(++idx, lotto.getQuotaFemminile());

				updateLottoFunction.setLong(++idx, lotto.getId_Lotto());

				int result = updateLottoFunction.executeUpdate();
			} finally {
				try {
					updateLottoFunction.close();
				} catch (Exception e) {
				}
				updateLottoFunction = null;
			}
		}
		//MEV 37010 3.04.8.1
		
		//MEV 3.04.10 43227
				public void updateModificaDatiPerfezionamento(Lotto lotto) throws SQLException {

					PreparedStatement updateLottoFunction = null;

					int idx = 0;
					try {
						
						//se proc ristretta fase 2
						if ((lotto.getDATA_SCADENZA_PAGAMENTI() != null && !"".equals(lotto.getDATA_SCADENZA_PAGAMENTI()))
								&&(lotto.getId_Scelta_Contraente().equals("2") || lotto.getId_Scelta_Contraente().equals("13") || lotto.getId_Scelta_Contraente().equals("25"))
								&& (lotto.getDataScadenzaRichiestaInvito() != null && !"".equals(lotto.getDataScadenzaRichiestaInvito()))
								&& (lotto.getDataLetteraInvito() != null && !"".equals(lotto.getDataLetteraInvito()))) {
							updateLottoFunction = activeConnection.prepareStatement(UPDATE_DATI_PERFEZIONAMENTO_FASE_2);
							updateLottoFunction.setString(++idx, lotto.getDATA_SCADENZA_PAGAMENTI());
							updateLottoFunction.setString(++idx, lotto.getORA_SCADENZA());
							//se proc ristretta fase 1
						}else if ((lotto.getDataScadenzaRichiestaInvito() != null && !"".equals(lotto.getDataScadenzaRichiestaInvito()))
								&&(lotto.getId_Scelta_Contraente().equals("2") || lotto.getId_Scelta_Contraente().equals("13") || lotto.getId_Scelta_Contraente().equals("25"))) {
							updateLottoFunction = activeConnection.prepareStatement(UPDATE_DATI_PERFEZIONAMENTO_FASE_1);
							updateLottoFunction.setString(++idx, lotto.getDataScadenzaRichiestaInvito());
						// se caso normale controllo la data scadenza pagamenti
						}else if (lotto.getDATA_SCADENZA_PAGAMENTI() != null && !"".equals(lotto.getDATA_SCADENZA_PAGAMENTI())) {
							updateLottoFunction = activeConnection.prepareStatement(UPDATE_DATI_PERFEZIONAMENTO_FASE_2);
							updateLottoFunction.setString(++idx, lotto.getDATA_SCADENZA_PAGAMENTI());
							updateLottoFunction.setString(++idx, lotto.getORA_SCADENZA());
						}
						
//						if (lotto.getDataScadenzaRichiestaInvito() != null && !"".equals(lotto.getDataScadenzaRichiestaInvito())) {
//							updateLottoFunction = activeConnection.prepareStatement(UPDATE_DATI_PERFEZIONAMENTO_FASE_1);
//							updateLottoFunction.setString(++idx, lotto.getDataScadenzaRichiestaInvito());
//							
//						}else {
//							updateLottoFunction = activeConnection.prepareStatement(UPDATE_DATI_PERFEZIONAMENTO_FASE_2);
//							updateLottoFunction.setString(++idx, lotto.getDATA_SCADENZA_PAGAMENTI());
//							updateLottoFunction.setString(++idx, lotto.getORA_SCADENZA());
//						}
						
						

						updateLottoFunction.setLong(++idx, lotto.getId_Gara());

						int result = updateLottoFunction.executeUpdate();
					} finally {
						try {
							updateLottoFunction.close();
						} catch (Exception e) {
						}
						updateLottoFunction = null;
					}
				}
				//MEV 3.04.10 43227
				
				//MEV 53643 3.04.13
				public void updateCPV(Lotto lotto) throws SQLException {

					PreparedStatement updateLottoFunction = null;

					int idx = 0;
					try {
						
						
							updateLottoFunction = activeConnection.prepareStatement(UPDATE_CPV);
							updateLottoFunction.setString(++idx, lotto.getId_CPV());
							updateLottoFunction.setLong(++idx, lotto.getId_Lotto());

						int result = updateLottoFunction.executeUpdate();
					} finally {
						try {
							updateLottoFunction.close();
						} catch (Exception e) {
						}
						updateLottoFunction = null;
					}
				}
				
				
				
				/* MAD 68089 3.04.16 Inizio */
				public void updateCategoriaSoa(Lotto lotto, String categoriaPrevalente) throws SQLException {

					PreparedStatement updateLottoFunction = null;
					int idx = 0;
					
					try {						
						
						updateLottoFunction = activeConnection.prepareStatement(UPDATE_CAT_SOA_PREVAL);
						updateLottoFunction.setString(++idx, categoriaPrevalente);
						updateLottoFunction.setLong(++idx, lotto.getId_Lotto());

						int result = updateLottoFunction.executeUpdate();
						
					} finally {
						try {
							updateLottoFunction.close();
						} catch (Exception e) {
						}
						updateLottoFunction = null;
					}
					
				}
				
				
				public void insertRelLottoCatScorpStorico(long idLotto, String idCatScorp) throws SQLException {
					
					PreparedStatement updateRelLottoCatScorpStoFunc = null;
					int idx = 0;
					
					try {						
						
						updateRelLottoCatScorpStoFunc = activeConnection.prepareStatement(INS_REL_LOTTO_CAT_SCORP_STO);
						updateRelLottoCatScorpStoFunc.setLong(++idx, idLotto);
						updateRelLottoCatScorpStoFunc.setString(++idx, idCatScorp);

						int result = updateRelLottoCatScorpStoFunc.executeUpdate();
						
					} finally {
						try {
							updateRelLottoCatScorpStoFunc.close();
						} catch (Exception e) {
						}
						updateRelLottoCatScorpStoFunc = null;
					}
					
				}
				/* MAD 68089 3.04.16 Fine */
				
				
				
	// @see cancellabilita e modificabilita gara con / senza cig dipendenti (accordo
	// quadro)
//	private final String selezionaLottoByIdGara = 
//		"SELECT " + LOTTO.CIG+LOTTO.CIG_KKK + "AS FULLCIG " +
//		" FROM LOTTO " +
//		" WHERE " +
//		LOTTO.DATA_CANCELLAZIONE_LOTTO + " IS NULL " +
//		" AND " + LOTTO.ID_GARA +" = ? ";
//	
//	/**
//	 * Metodo che si occupa del recupero di tutti i CIG appartenti ad una gara
//	 * 
//	 * @param idGara
//	 * @return
//	 */
//	public List<String> getCigByIdGara(long idGara) throws SQLException{
//		
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		List<String> listaDiCig = new ArrayList<String>();
//		try {
//						
//			stmt = activeConnection.prepareStatement(selezionaLottoByIdGara);
//			stmt.setLong(1, idGara);
//			
//			rs = stmt.executeQuery();
//			while(rs.next()){
//				String actualCig = rs.getString("FULLCIG");
//				listaDiCig.add(actualCig);
//			}
//			return listaDiCig;
//			
//		} finally {
//			close(rs, stmt);
//		}		
//	}

	public void sbloccaLotto(long sessionIdGara) throws SQLException {

		int idx = 0;

		PreparedStatement updateLottoFunction = null;

		try {

			updateLottoFunction = activeConnection.prepareStatement(UPDATE_DATA_LOTTI);
			updateLottoFunction.setNull(++idx, Types.VARCHAR);
			updateLottoFunction.setNull(++idx, Types.VARCHAR);

			if (SimogFlags.is3025_RFWEBGL02Active())
				updateLottoFunction.setNull(++idx, Types.VARCHAR);

			if (SimogFlags.is3030_RFWEBGL00Active()) {
				updateLottoFunction.setNull(++idx, Types.VARCHAR);
				updateLottoFunction.setNull(++idx, Types.VARCHAR);
			}

			updateLottoFunction.setLong(++idx, sessionIdGara);

			updateLottoFunction.executeUpdate();
			logger.debug("Eseguita query [" + UPDATE_DATA_LOTTI + "] per idGara [" + sessionIdGara + "]");

		} finally {
			try {
				updateLottoFunction.close();
			} catch (Exception e) {
			}
			updateLottoFunction = null;
		}
	}

	private final String UPDATE_RIPRISTINA = "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.DATA_CANCELLAZIONE_LOTTO
			+ " = null, " + LOTTO.DATA_INIB_PAGAMENTO + " = null, " + LOTTO.ID_MOTIVAZIONE + " = null, "
			+ LOTTO.NOTE_CANC + " = null " + " WHERE " + LOTTO.ID_LOTTO + " = ?";

	public void ripristinaLotto(long idLotto) throws SQLException {

		int idx = 0;

		PreparedStatement updateLottoFunction = null;

		try {

			updateLottoFunction = activeConnection.prepareStatement(UPDATE_RIPRISTINA);
			updateLottoFunction.setLong(++idx, idLotto);

			updateLottoFunction.executeUpdate();
		} finally {
			try {
				updateLottoFunction.close();
			} catch (Exception e) {
			}
			updateLottoFunction = null;
		}
	}

	/*********************************************************************************************
	 * restituisce una lista di lotto per i qauli deve essere ricalcolato il
	 * contributo
	 * 
	 * @return List&lt;Lotto&gt;
	 * @throws SQLException
	 * @throws Exception
	 */
	public List<InfoGaraBean> getLottiNoContributo() throws SQLException, Exception {

		String QUERY_SELECT_LOTTI_NOCONTRIB = " SELECT " + GARA.CF_AMMINISTRAZIONE + ", " + GARA.DENOM_AMMINISTRAZIONE
				+ ", " + GARA.ID_STAZIONE_APPALTANTE + ", " + GARA.DENOM_STAZIONE_APPALTANTE + ", "
				+ GARA.T_ID_PUBBLICAZIONE + ", " + GARA.T_DATA_INIZIO_PUBB + ", " + GARA.ID_OSSERVATORIO + ", "
				+ GARA.CIG_ACC_QUADRO + ", " + LOTTO.T_CIG + ", " + LOTTO.T_ID_SCELTA_CONTRAENTE + ", "
				+ LOTTO.T_CIG_CICLE + ", " + LOTTO.T_CIG_KKK + ", " + LOTTO.T_ID_CPV + ", "
				+ LOTTO.T_DATA_CANCELLAZIONE_LOTTO + ", " + LOTTO.T_DATA_INIB_PAGAMENTO + ", " + LOTTO.T_OGGETTO + ", "
				+ LOTTO.T_ID_LOTTO + ", " + LOTTO.T_SOMMA_URGENZA + ",  " + LOTTO.T_IMPORTO_LOTTO + ",  "
				+ LOTTO.T_DATA_SCADENZA_PAGAMENTI
				+ (SimogFlags.is3025_RFWEBGL02Active() ? ", " + LOTTO.ORA_SCADENZA : "") + ",  "
				+ LOTTO.T_DATA_CREAZIONE_LOTTO + ",  " + GARA.T_ID_MODO_REAL + ",  " + GARA.T_TIPO_SCHEDA_GARA + ",  "
				+ GARA.T_DATA_CREAZIONE + ",  " + LOTTO.T_FLAG_ESCLUSO + ",  " + LOTTO.T_ID_ESCLUSIONE + ",  "
				+ LOTTO.T_TIPO_CONTRATTO_LOTTO + ",  " + LOTTO.LUOGO_ISTAT + ",  " + LOTTO.LUOGO_NUTS + ",  "
				+ LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA + ",  " + LOTTO.T_ID_GARA + ",  " + LOTTO.T_DATA_PUBBLICAZIONE
				+ " FROM " + LOTTO.TABLE_NAME + " JOIN " + GARA.TABLE_NAME + " ON " + LOTTO.T_ID_GARA + " = "
				+ GARA.T_ID_GARA + " WHERE " + LOTTO.DATA_CANCELLAZIONE_LOTTO + " IS NULL " + " AND "
				+ LOTTO.DATA_INIB_PAGAMENTO + " IS NULL " + " AND " + LOTTO.IMPORTO_IMPRESA + " = "
				+ Costanti.IMPORTO_FUORI_SCALA;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		InfoGaraBean igb = null;
		List<InfoGaraBean> lOut = new ArrayList<InfoGaraBean>();

		try {
			pstmt = activeConnection.prepareStatement(QUERY_SELECT_LOTTI_NOCONTRIB);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				igb = new InfoGaraBean();
				fillInfoGaraBean(rs, igb);
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

	private void fillInfoGaraBean(ResultSet rs, InfoGaraBean igb) throws SQLException {
		igb.setCig(rs.getString(LOTTO.CIG));
		igb.setCigCicle(rs.getInt(LOTTO.CIG_CICLE));
		igb.setCigKKK(rs.getString(LOTTO.CIG_KKK));
		igb.setIdSceltaContraente(rs.getLong(LOTTO.ID_SCELTA_CONTRAENTE));
		igb.setDataCancelazioneLotto(rs.getString(LOTTO.DATA_CANCELLAZIONE_LOTTO));
		igb.setDataInibPagamento(rs.getString(LOTTO.DATA_INIB_PAGAMENTO));
		igb.setIdCPV(rs.getString(LOTTO.ID_CPV));
		igb.setIdLotto(rs.getLong(LOTTO.ID_LOTTO));
		igb.setOggettoLotto(rs.getString(LOTTO.OGGETTO));
		igb.setTipoContratto(rs.getString(LOTTO.TIPO_CONTRATTO_LOTTO));
		igb.setCfAmministrazione(rs.getString(GARA.CF_AMMINISTRAZIONE));
		igb.setDenomAmministrazione(rs.getString(GARA.DENOM_AMMINISTRAZIONE));
		igb.setCfSA(rs.getString(GARA.ID_STAZIONE_APPALTANTE));
		igb.setDenomSA(rs.getString(GARA.DENOM_STAZIONE_APPALTANTE));
		igb.setImportoLotto(rs.getBigDecimal(LOTTO.IMPORTO_LOTTO));
		igb.setSommaUrgenza(rs.getString(LOTTO.SOMMA_URGENZA));
		igb.setDataScadenzaPagamenti(rs.getString(LOTTO.DATA_SCADENZA_PAGAMENTI));
		igb.setDataCreazione(rs.getString(LOTTO.DATA_CREAZIONE_LOTTO));
		igb.setDataCreazioneGara(rs.getString(GARA.DATA_CREAZIONE));
		igb.setTIPO_SCHEDA_GARA(rs.getString(GARA.TIPO_SCHEDA_GARA));
		igb.setID_MODO_REAL(rs.getInt(GARA.ID_MODO_REAL));
		igb.setFLAG_ESCLUSO(rs.getString(LOTTO.FLAG_ESCLUSO));
		igb.setID_ESCLUSIONE(rs.getInt(LOTTO.ID_ESCLUSIONE));
		igb.setIdPubblicazione(rs.getInt(GARA.ID_PUBBLICAZIONE));
		igb.setDataInizioPubblicazione(rs.getTimestamp(GARA.DATA_INIZIO_PUBB));
		igb.setLUOGO_ISTAT(rs.getString(LOTTO.LUOGO_ISTAT));
		igb.setLUOGO_NUTS(rs.getString(LOTTO.LUOGO_NUTS));
		igb.setIMPORTO_ATTUAZIONE_SICUREZZA(rs.getBigDecimal(LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA));
		igb.setIdOsservatorio(rs.getString(GARA.ID_OSSERVATORIO));
		igb.setCIG_ACC_QUADRO(rs.getString(GARA.CIG_ACC_QUADRO));
		igb.setIdGara(rs.getLong(LOTTO.ID_GARA));
		igb.setDataPubblicazione(rs.getString(LOTTO.DATA_PUBBLICAZIONE));

		if (SimogFlags.is3025_RFWEBGL02Active())
			igb.setOraScadenza(rs.getString(LOTTO.ORA_SCADENZA));
	}

	private void fillBean(ResultSet rs, Lotto l) throws SQLException {
		Pattern INVALID_XML_CHARS = Pattern
				.compile("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\uD800\uDC00-\uDBFF\uDFFF]");

		l.setId_Lotto(rs.getLong(LOTTO.ID_LOTTO));
		l.setCig(rs.getString(LOTTO.CIG));
		l.setCig_cicle(rs.getInt(LOTTO.CIG_CICLE));
		l.setCig_kkk(rs.getString(LOTTO.CIG_KKK));
		l.setId_Scelta_Contraente(rs.getString(LOTTO.ID_SCELTA_CONTRAENTE));
		// TICKET ALM - 3.04.3
		l.setID_MOTIVO_COLL_CIG(rs.getString(LOTTO.ID_MOTIVO));
		// TICKET ALM - 3.04.3
		l.setDataCreazione(rs.getString(LOTTO.DATA_CREAZIONE_LOTTO));
		l.setFLAG_ESCLUSO(rs.getString(LOTTO.FLAG_ESCLUSO));
		l.setTIPO_CONTRATTO_LOTTO(rs.getString(LOTTO.TIPO_CONTRATTO_LOTTO));
		l.setID_ESCLUSIONE(rs.getInt(LOTTO.ID_ESCLUSIONE));
		l.setDATA_CANCELLAZIONE_LOTTO(rs.getString(LOTTO.DATA_CANCELLAZIONE_LOTTO));
		l.setData_Pubblicazione(rs.getString(LOTTO.DATA_PUBBLICAZIONE));
		l.setTriennio_anno_inizio(rs.getString(LOTTO.TRIENNIO_ANNO_INIZIO));
		l.setTriennio_anno_fine(rs.getString(LOTTO.TRIENNIO_ANNO_FINE));
		l.setTriennio_progressivo(rs.getString(LOTTO.TRIENNIO_PROGRESSIVO));
		l.setAnnuale_cui_mininf(rs.getString(LOTTO.ANNUALE_CUI_MININF));
		l.setLUOGO_ISTAT(rs.getString(LOTTO.LUOGO_ISTAT));
		l.setLUOGO_NUTS(rs.getString(LOTTO.LUOGO_NUTS));
		//MEV 40610
		if (rs.getString(LOTTO.ID_DEROGA_QUALIFICAZIONE_SA) != null && !rs.getString(LOTTO.ID_DEROGA_QUALIFICAZIONE_SA).equals("")) {
			l.setDerogaQualificazioneSA(rs.getString(LOTTO.ID_DEROGA_QUALIFICAZIONE_SA));
		}
		//FINE MEV 40610
		//MAC 34164 3.04.8 **
				l.setIMPORTO_ATTUAZIONE_SICUREZZA(LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA == null ? new BigDecimal(0) : rs.getBigDecimal(LOTTO.IMPORTO_ATTUAZIONE_SICUREZZA));
				
				l.setId_Gara(rs.getLong(GARA.ID_GARA));
				l.setData_Pubblicazione(rs.getString(LOTTO.DATA_PUBBLICAZIONE));
				l.setOggetto(INVALID_XML_CHARS.matcher(rs.getString(LOTTO.OGGETTO)).replaceAll(" "));
				l.setSomma_Urgenza(rs.getString(LOTTO.SOMMA_URGENZA).charAt(0));
				
//				l.setImporto_Lotto(rs.getBigDecimal(LOTTO.IMPORTO_LOTTO)); CODICE INIZIALE **
				//MAC 34164 3.04.8 **
				l.setImporto_Lotto(rs.getBigDecimal(LOTTO.IMPORTO_LOTTO) == null ? new BigDecimal(0) : rs.getBigDecimal(LOTTO.IMPORTO_LOTTO));
				
//				l.setImporto_SA(rs.getBigDecimal(LOTTO.IMPORTO_SA)); CODICE INIZIALE **
				//MAC 34164 3.04.8 **
				l.setImporto_SA(rs.getBigDecimal(LOTTO.IMPORTO_SA) == null ? new BigDecimal(0) : rs.getBigDecimal(LOTTO.IMPORTO_SA));
				
//				l.setImporto_Impresa(rs.getBigDecimal(LOTTO.IMPORTO_IMPRESA)); CODICE INIZIALE **
				//MAC 34164 3.04.8 **
				l.setImporto_Impresa(rs.getBigDecimal(LOTTO.IMPORTO_IMPRESA) == null ? new BigDecimal(0) : rs.getBigDecimal(LOTTO.IMPORTO_IMPRESA));
				
				l.setId_Categoria_prevalente(rs.getString(LOTTO.ID_CATEGORIA_PREVALENTE));
				l.setId_Tipologia(rs.getString(LOTTO.ID_TIPOLOGIA));
				l.setId_CPV(rs.getString(LOTTO.ID_CPV));
				l.setDataScadenzaPagamenti(rs.getString(LOTTO.DATA_SCADENZA_PAGAMENTI));
				l.setId_motivazione(rs.getString(LOTTO.ID_MOTIVAZIONE));
				l.setNoteCancellazione(rs.getString(LOTTO.NOTE_CANC));
				l.setDataCreazione(rs.getString(LOTTO.DATA_CREAZIONE_LOTTO));
				l.setDataComunicazione(rs.getString(LOTTO.DATA_COMUNICAZIONE));
				l.setDataInibizionePagamento(rs.getString(LOTTO.DATA_INIB_PAGAMENTO));
				// categorie scorporabili
				l.setCategorieScorporabili(findCategorieScorporabili(l.getId_Lotto()));

				// PP 3.02.3.3
				l.setFLAG_PREVEDE_RIP(rs.getString(LOTTO.FLAG_PREVEDE_RIP));
				l.setFLAG_RIPETIZIONE(rs.getString(LOTTO.FLAG_RIPETIZIONE));
				l.setCIG_ORIGINE_RIP(rs.getString(LOTTO.CIG_ORIGINE_RIP));

				l.setORA_SCADENZA(rs.getString(LOTTO.ORA_SCADENZA));

				// UN 3.03.0
				l.setDataScadenzaRichiestaInvito(rs.getString(LOTTO.DATA_SCADENZA_RICHIESTA_INVITO));
				l.setDataLetteraInvito(rs.getString(LOTTO.DATA_LETTERA_INVITO));

				l.setFLAG_CUP(rs.getString(LOTTO.FLAG_CUP));

				CupLottoAggManager cla = new CupLottoAggManager(activeConnection, logger);

				List<CupLottoAggExt> listaCup = cla.getElencoCup(l.getId_Lotto(), null, null, false);
				if (!listaCup.isEmpty()) {
					CupLottoAggAction claAction = new CupLottoAggAction(activeConnection, logger);

					claAction.settingDatiDIPE(listaCup);
					l.setElencoCup(listaCup);
				}

				// Caricamento voci
				TipoAppaltoManager tam = new TipoAppaltoManager(activeConnection, logger);
				GaraManager gam = new GaraManager(activeConnection, logger);
				Gara gara = null;
				try {
					gara = gam.getGara(l.getId_Gara());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				List<TipoAppaltoAggBean> ele = tam.loadMany(l.getId_Lotto(), Costanti.TIPO_SCHEDA_LAVORI,
						gara.getTIPO_SCHEDA_GARA(), false);
				if (ele != null && ele.size() > 0)
					l.setElencoTipoAppaltoLottoL(ele);

				List<TipoAppaltoAggBean> elef = tam.loadMany(l.getId_Lotto(), Costanti.TIPO_SCHEDA_FORNITURE,
						gara.getTIPO_SCHEDA_GARA(), false);
				if (elef != null && elef.size() > 0)
					l.setElencoTipoAppaltoLottoF(elef);
				// TICKET ALM #2845
				l.setFLAG_DL50(rs.getString(LOTTO.FLAG_DL50));
				l.setPRIMA_ANNUALITA(rs.getString(LOTTO.PRIMA_ANNUALITA));
				// FINE TICKET ALM #2845

				// TICKET #2846
				l.setID_MOTIVO_COLL_CIG(rs.getString(LOTTO.ID_MOTIVO));

				// FINE TICKET #2846

				// TICKET ALM #3835
				l.setID_AFF_RISERVATI(rs.getInt(LOTTO.ID_AFF_RISERVATI));
				CondizioniManager cm = new CondizioniManager(activeConnection, logger);
				List<CondizioneLottoBean> listaClb = cm.loadManyCondizioniLotto(l.getId_Lotto(), false);
				if (listaClb != null) {
					l.setCondizioni(listaClb);
				}
				// FINE TICKET ALM #3835

				// TICKET ALM #3836
				l.set_FLAG_REGIME(rs.getString(LOTTO.FLAG_REGIME));

				// TICKET ALM - 3.04.4
				if (rs.getLong(LOTTO.COD_CATEGORIA) != 0) {
					l.setCOD_CATEGORIA(String.valueOf(rs.getLong(LOTTO.COD_CATEGORIA)));
				}

				// TICKET ALM #4219 - 3.04.4
				l.setElencoCpvSecondarie(this.selectCpvLotto(l.getId_Lotto()));

				// TICKET ALM #4223-#4224 - 3.04.4
				l.setFlagNoAdesione(Costanti.FLAG_VALORE_NO);
				l.setFlagSANonClass(Costanti.FLAG_VALORE_NO);
				IniziativaManager im = new IniziativaManager(activeConnection, logger);
				List<Long> autoDichiarazioniLotto = im.getAutodichiarazioni(l.getId_Lotto(), l.getCOD_CATEGORIA());
				for (Long autoD : autoDichiarazioniLotto) {
					if (autoD == Costanti.INIZIATIVE_NON_IDONEE)
						l.setFlagNoAdesione(Costanti.FLAG_VALORE_SI);
					else if (autoD == Costanti.SA_NON_CLASSIFICATA)
						l.setFlagSANonClass(Costanti.FLAG_VALORE_SI);
				}
				if (!l.getFlagNoAdesione().equals(Costanti.FLAG_VALORE_SI)
						&& !l.getFlagSANonClass().equals(Costanti.FLAG_VALORE_SI)) {

					try {
						gara = gam.getGara(l.getId_Gara());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					List<IniziativaSoggAggr> iniziative = new ArrayList<IniziativaSoggAggr>();
					if (gara.getCIG_ACC_QUADRO() != null && !"".equals(gara.getCIG_ACC_QUADRO()))
						iniziative = im.getIniziative(gara.getCIG_ACC_QUADRO(), null, null, null, null, false);

					if (iniziative.size() > 0)
						l.setCigIniziativa(gara.getCIG_ACC_QUADRO());
				}

				// TICKET ALM 13691 - 3.04.5
				l.setImporto_opzioni(rs.getBigDecimal(LOTTO.IMPORTO_OPZIONI));
				l.setDurataRipetizioni(rs.getInt(LOTTO.DURATA_RINNOVI_RIPETIZIONI));
				l.setDurataAffidamentoGiorni(rs.getInt(LOTTO.DURATA_AFFIDAMENTO_IN_GIORNI));

				// PARI OPPORTUNITA
				l.setFLAG_PNRR_PNC(rs.getString(LOTTO.FLAG_PNRR_PNC));
				l.setFLAG_PREVISIONE_QUOTA(rs.getString(LOTTO.FLAG_PREVISIONE_QUOTA));
				if (rs.getString(LOTTO.QUOTA_FEMMINILE) != null && !rs.getString(LOTTO.QUOTA_FEMMINILE).equals("")) {
					l.setQuotaFemminile(new BigDecimal(rs.getString(LOTTO.QUOTA_FEMMINILE)));
				}
				if (rs.getString(LOTTO.QUOTA_GIOVANILE) != null && !rs.getString(LOTTO.QUOTA_GIOVANILE).equals("")) {
					l.setQuotaGiovanile(new BigDecimal(rs.getString(LOTTO.QUOTA_GIOVANILE)));
				}
				l.setFLAG_MISURE_PREMIALI(rs.getString(LOTTO.FLAG_MISURE_PREMIALI));
				l.setFLAG_USO_METODI_EDILIZIA(rs.getString(LOTTO.FLAG_USO_METODI_EDILIZIA)); // MEV 38295 3.04.8.1
				l.setFLAG_DEROGA_ADESIONE(rs.getString(LOTTO.FLAG_DEROGA_ADESIONE)); // MEV 37010 3.04.8.1
	
				
				
				MotivoDerogaManager mdm = new MotivoDerogaManager(activeConnection, logger);
				List<MotivoDerogaLottoBean> mdlb= mdm.loadMany(l.getId_Lotto());
				List<MotivoDerogaBean> listMotivoDerogaBean= new ArrayList<MotivoDerogaBean>();
				for(int i=0; i< mdlb.size(); i++ ) {
					MotivoDerogaBean m=new MotivoDerogaBean(); 
					m.setIdMotivoDeroga(mdlb.get(i).getIdMotivoDeroga()); 
					listMotivoDerogaBean.add(m); 
				}
				l.setElencoMotivoDeroga(listMotivoDerogaBean);

				MisuraPremialeManager mpm = new MisuraPremialeManager(activeConnection, logger);
				List<MisuraPremialeLottoBean> mplb= mpm.loadMany(l.getId_Lotto());
				List<MisuraPremialeBean> listMisuraPremialeBean= new ArrayList<MisuraPremialeBean>();
				for(int i=0; i< mplb.size(); i++ ) {
					MisuraPremialeBean m=new MisuraPremialeBean(); 
					m.setIdMisuraPremiale(mplb.get(i).getIdMisuraPremiale()); 
					listMisuraPremialeBean.add(m); 
				}
				l.setElencoMisurePremiali(listMisuraPremialeBean);

				

			}

			private final String SELECT_PROCEDURA_RISTRETTA = "SELECT * " + " FROM " + LOTTO.TABLE_NAME + " WHERE "
					+ LOTTO.ID_GARA + " = ? " + " AND " + LOTTO.ID_SCELTA_CONTRAENTE + " NOT IN ($0)";

			/**
			 * Verifica se una gara è di tipo "Procedura Ristretta"
			 * 
			 * @param idGara
			 * @param codiciProceduraRistretta (direttamente da parametro di configurazione
			 *                                 Simog.ini) codici separati da virgole
			 * @return
			 * @throws SQLException
			 */
			public boolean isProceduraRistretta(long idGara, String codiciProceduraRistretta) throws SQLException {

				PreparedStatement stmt = null;
				ResultSet rs = null;
				try {

					stmt = activeConnection
							.prepareStatement(SELECT_PROCEDURA_RISTRETTA.replace("$0", codiciProceduraRistretta));
					stmt.setLong(1, idGara);

					rs = stmt.executeQuery();
					if (rs.next()) {
						return false;
					}
					return true;

				} finally {
					close(rs, stmt);
				}
			}

			private final String UPDATE_FLAG_CUP = "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.FLAG_CUP + " = ?" + " WHERE "
					+ LOTTO.ID_LOTTO + " = ?";

			/**
			 * Aggiorna il solo campo FLAG_CUP di una lotto
			 * 
			 * @param lotto
			 * @return
			 * @throws SQLException
			 */
			public int updateFlagCup(Lotto lotto) throws SQLException {
				PreparedStatement stmt = null;
				int updated = 0;
				try {
					stmt = activeConnection.prepareStatement(UPDATE_FLAG_CUP);
					if (lotto.getFLAG_CUP() == null)
						stmt.setNull(1, Types.VARCHAR);
					else
						stmt.setString(1, lotto.getFLAG_CUP());

					stmt.setLong(2, lotto.getId_Lotto());

					updated = stmt.executeUpdate();

					return updated;

				} finally {
					close(null, stmt);
				}
			}
			
			private final String UPDATE_FLAG_PARITA_DI_GENERE = "UPDATE " + LOTTO.TABLE_NAME + 
					" SET " + LOTTO.FLAG_PNRR_PNC + " = ?, "
							+ LOTTO.FLAG_PREVISIONE_QUOTA + " = ?, "
							+ LOTTO.QUOTA_GIOVANILE + " = ?, "
							+ LOTTO.QUOTA_FEMMINILE + " = ?, "
							+ LOTTO.FLAG_MISURE_PREMIALI + " = ?, "
							+ LOTTO.FLAG_DEROGA_ADESIONE + " = ?" //MEV 37010 3.04.8.1
							+ " WHERE " + LOTTO.ID_LOTTO + " = ?";
			
			public int updateFlagParitaDiGenere(Lotto lotto) throws SQLException {
				PreparedStatement stmt = null;
				int updated = 0;
				try {
					stmt = activeConnection.prepareStatement(UPDATE_FLAG_PARITA_DI_GENERE);

					stmt.setString(1, lotto.getFLAG_PNRR_PNC());
					stmt.setString(2, lotto.getFLAG_PREVISIONE_QUOTA());
					stmt.setBigDecimal(3, lotto.getQuotaGiovanile());
					stmt.setBigDecimal(4, lotto.getQuotaFemminile());
					stmt.setString(5, lotto.getFLAG_MISURE_PREMIALI());
					stmt.setString(6, lotto.getFLAG_DEROGA_ADESIONE()); //MEV 37010 3.04.8.1
					stmt.setLong(7, lotto.getId_Lotto());

					updated = stmt.executeUpdate();

					return updated;

				} finally {
					close(null, stmt);
				}
			}

			public boolean checkCigAccQuadro(String cig) {
				boolean res = false;

				String query = "SELECT " + LOTTO.CIG + " FROM " + LOTTO.TABLE_NAME + " INNER JOIN " + GARA.TABLE_NAME + " ON "
						+ GARA.T_ID_GARA + " = " + LOTTO.T_ID_GARA + " WHERE " + LOTTO.CIG + "+" + LOTTO.CIG_KKK + " = ? AND "
						+ GARA.ID_OSSERVATORIO + " = '099' " + "AND " + GARA.ID_MODO_REAL + " IN ("
						+ Costanti.MODOREAL_ACCORDO_QUADRO + "," + Costanti.MODOREAL_ACCORDO + ","
						+ Costanti.MODOREAL_CONVENZIONE + ")";

				PreparedStatement stmt = null;
				ResultSet rs = null;

				try {
					try {

						stmt = activeConnection.prepareStatement(query);
						stmt.setString(1, cig);
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
			// FINE TICKET ALM - 3.04.3

			// TICKET ALM - 3.04.4
			/**
			 * Recupera le cpv secondarie di un lotto
			 * 
			 * @param id_lotto
			 * @return
			 */
			public List<CpvLotto> selectCpvLotto(long id_lotto) {
				List<CpvLotto> listaCpvSecondarie = new ArrayList<CpvLotto>();

				final String query = "SELECT * FROM " + CPV_LOTTO.TABLE_NAME + " WHERE " + CPV_LOTTO.ID_LOTTO + " = ?";

				PreparedStatement stmt = null;
				ResultSet rs = null;

				try {
					try {

						stmt = activeConnection.prepareStatement(query);
						stmt.setLong(1, id_lotto);
						rs = stmt.executeQuery();

						while (rs.next()) {
							CpvLotto cpvSecondaria = new CpvLotto();
							cpvSecondaria.setIdCPVLotto(rs.getLong(CPV_LOTTO.ID_CPV_LOTTO));
							cpvSecondaria.setIdCpv(rs.getString(CPV_LOTTO.ID_CPV));
							cpvSecondaria.setIdLotto(id_lotto);
							listaCpvSecondarie.add(cpvSecondaria);
						}

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

				return listaCpvSecondarie;
			}

			/**
			 * Inserisci cpv secondaria in un lotto
			 * 
			 * @param idLotto
			 * @param cpvlotto
			 * @throws SQLException
			 */
			public void insertCpvLotto(long idLotto, CpvLotto cpvlotto) throws SQLException {
				String INSERT_CPV_LOTTO = "INSERT INTO " + CPV_LOTTO.TABLE_NAME + " (" + CPV_LOTTO.ID_LOTTO + ","
						+ CPV_LOTTO.ID_CPV + ") VALUES(?,?)";

				PreparedStatement stmt = null;
				ResultSet rs = null;
				int index = 1;
				try {
					stmt = activeConnection.prepareStatement(INSERT_CPV_LOTTO);
					stmt.setLong(index++, idLotto);
					stmt.setString(index++, cpvlotto.getIdCpv());
					stmt.execute();
				} finally {
					close(rs, stmt);
				}
			}

			/**
			 * Elimina le CPV secondarie di un lotto
			 * 
			 * @param idLotto
			 * @throws SQLException
			 */
			public void deleteCpvLotto(long idLotto) throws SQLException {
				String EXPIRE_CPV_LOTTO = "DELETE FROM " + CPV_LOTTO.TABLE_NAME + " WHERE " + CPV_LOTTO.ID_LOTTO + " = ? ";

				PreparedStatement stmt = null;
				ResultSet rs = null;
				int index = 1;

				try {
					stmt = activeConnection.prepareStatement(EXPIRE_CPV_LOTTO);
					stmt.setLong(index++, idLotto);

					stmt.execute();
				} finally {
					close(rs, stmt);
				}
			}

			/**
			 * Recupera la lista delle categorie merceologiche selezionate nei lotti attivi
			 * 
			 * @param idGara
			 * @return
			 */
			public String selectCodCatMercGara(long idGara) {
				String listaCodCatStr = "";

				final String query = "SELECT DISTINCT " + LOTTO.COD_CATEGORIA + " FROM " + LOTTO.TABLE_NAME + " WHERE "
						+ LOTTO.ID_GARA + " = ? and " + LOTTO.DATA_CANCELLAZIONE_LOTTO + " IS NULL";

				PreparedStatement stmt = null;
				ResultSet rs = null;

				try {
					try {

						stmt = activeConnection.prepareStatement(query);
						stmt.setLong(1, idGara);
						rs = stmt.executeQuery();

						while (rs.next()) {
							listaCodCatStr += (String.valueOf(rs.getLong(1))) + "_";
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}

				} finally {
					close(rs, stmt);
				}

				return listaCodCatStr;
			}

			public String getCodProceduraBDNCP(String idSceltaContraente) {
				String res = null;

				final String query = "SELECT " + SCELTA_CONTRAENTE.BDNCP_COD + " FROM " + SCELTA_CONTRAENTE.TABLE_NAME
						+ " WHERE " + SCELTA_CONTRAENTE.ID_SCELTA_CONTRAENTE + " = ?";

				PreparedStatement stmt = null;
				ResultSet rs = null;

				try {
					try {

						stmt = activeConnection.prepareStatement(query);
						stmt.setString(1, idSceltaContraente);
						rs = stmt.executeQuery();

						if (rs.next()) {
							res = rs.getString(1);
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}

				} finally {
					close(rs, stmt);
				}

				return res;
			}

			public BigDecimal getSommaImportiAdesioni(String cigAccQuadro) {
				BigDecimal res = new BigDecimal(0);

				final String query = "SELECT SUM(" + LOTTO.IMPORTO_LOTTO + ") FROM " + LOTTO.TABLE_NAME + " INNER JOIN "
						+ GARA.TABLE_NAME + " ON " + LOTTO.T_ID_GARA + " = " + GARA.T_ID_GARA + " LEFT JOIN "
						+ INFO_AGGIUDICAZIONI.TABLE_NAME + " ON " + LOTTO.T_ID_LOTTO + " = " + INFO_AGGIUDICAZIONI.T_ID_LOTTO
						+ " WHERE " + GARA.CIG_ACC_QUADRO + " = ? AND " + LOTTO.DATA_CANCELLAZIONE_LOTTO + " IS NULL AND " + "("
						+ INFO_AGGIUDICAZIONI.ESITO_PROCEDURA + " IS NULL OR " + "(" + INFO_AGGIUDICAZIONI.ESITO_PROCEDURA
						+ " != ? AND " + INFO_AGGIUDICAZIONI.ID_STATO + " = ?))";

				PreparedStatement stmt = null;
				ResultSet rs = null;

				try {
					try {

						stmt = activeConnection.prepareStatement(query);
						stmt.setString(1, cigAccQuadro);
						stmt.setInt(2, Costanti.AGGIUDICATA);
						stmt.setInt(3, StatiScheda.CONFERMATO);
						rs = stmt.executeQuery();

						if (rs.next()) {
							res = rs.getBigDecimal(1);
						}

					} catch (SQLException e) {
						e.printStackTrace();
						return res;
					}

				} finally {
					close(rs, stmt);
				}

				return res;
			}

			public String getValueField(String field, long id_lotto) {
				String res = null;
				final String qry = "SELECT " + field + " FROM " + LOTTO.TABLE_NAME + " WHERE " + LOTTO.ID_LOTTO + " = ?";

				PreparedStatement stmt = null;
				ResultSet rs = null;

				try {
					try {

						stmt = activeConnection.prepareStatement(qry);
						stmt.setLong(1, id_lotto);

						rs = stmt.executeQuery();

						if (rs.next()) {
							res = rs.getString(1);
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}

				} finally {
					close(rs, stmt);
				}
				return res;
			}

			public int updatePubblicazioneToCurrentDate(long idgara) throws SQLException {
				PreparedStatement stmt = null;
				int updated = 0;
				String query = "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.DATA_PUBBLICAZIONE + " = ? WHERE " + LOTTO.ID_GARA
						+ "= ? AND " + LOTTO.DATA_CANCELLAZIONE_LOTTO + " IS NULL";
				try {
					stmt = activeConnection.prepareStatement(query);
					stmt.setString(1, PageHelper.getCurrentDate());
					stmt.setLong(2, idgara);

					updated = stmt.executeUpdate();

					return updated;

				} finally {
					close(null, stmt);
				}
			}

			public void updateDatePubblicazione(String dataPubblicazione, String dataScadenzaPagamenti,
					String dataScadenzaRichiestaInvito, String dataLetteraInvito, String oraScadenza, long idgara)
					throws SQLException {
				PreparedStatement stmt = null;
				String query = "UPDATE " + LOTTO.TABLE_NAME + " SET " + LOTTO.DATA_PUBBLICAZIONE + " = ?";
				if (dataScadenzaPagamenti != null)
					query += "," + LOTTO.DATA_SCADENZA_PAGAMENTI + "= ?";
				if (dataScadenzaRichiestaInvito != null)
					query += "," + LOTTO.DATA_SCADENZA_RICHIESTA_INVITO + " = ?";
				if (dataLetteraInvito != null)
					query += "," + LOTTO.DATA_LETTERA_INVITO + " = ?";
				if (oraScadenza != null)
					query += "," + LOTTO.ORA_SCADENZA + "= ?";

				String where = " WHERE " + LOTTO.ID_GARA + "= ? AND " + LOTTO.DATA_CANCELLAZIONE_LOTTO + " IS NULL";

				try {
					int index = 1;
					stmt = activeConnection.prepareStatement(query + where);
					stmt.setString(index++, dataPubblicazione);

					if (dataScadenzaPagamenti != null)
						stmt.setString(index++, dataScadenzaPagamenti);
					if (dataScadenzaRichiestaInvito != null)
						stmt.setString(index++, dataScadenzaRichiestaInvito);
					if (dataLetteraInvito != null)
						stmt.setString(index++, dataScadenzaRichiestaInvito);
					if (oraScadenza != null)
						stmt.setString(index++, oraScadenza);

					stmt.setLong(index++, idgara);

					stmt.executeUpdate();

				} finally {
					close(null, stmt);
				}
			}
		
//MEV 37010 3.04.8.1
public List<Lotto> getLottiAdesioniByCigAQ(String cigIniziativaAQ) throws SQLException {
	String QUERY_SELECT_GARE_ADESIONE = " SELECT * " + " FROM " + GARA.TABLE_NAME + " RIGHT JOIN " + LOTTO.TABLE_NAME 
			+ " ON " + GARA.T_ID_GARA + " = " + LOTTO.T_ID_GARA
			+ " WHERE "
			+ GARA.T_CIG_ACC_QUADRO+ "=?";

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Lotto l = null;
	List<Lotto> lOut = new ArrayList<Lotto>();

	try {
		pstmt = activeConnection.prepareStatement(QUERY_SELECT_GARE_ADESIONE);
		pstmt.setString(1,cigIniziativaAQ);
		rs = pstmt.executeQuery();
		l = new Lotto();

		while (rs.next()) {
			l = new Lotto();
			try {
				fillBean(rs,l);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lOut.add(l);
		}
	} finally {
		if (rs != null)
			rs.close();
		if (pstmt != null)
			pstmt.close();
	}

	return lOut;
	}
//MEV 37010 3.04.8.1

//3.04.9 MEV 40610
		/**************************************************************************************************
		 * <b>getValidDerogaQualificazioneSA</b><br>
		 * restituisce una mappa con id deroga qualificazione sa e la loro descrizione	
		 * @return SortedMap 
		 * @throws SQLException
		 **************************************************************************************************/
		public List<DerogaQualificazioneSABean> getValidDerogaQualificazioneSA() throws SQLException{
			PreparedStatement stmt = null;
		      ResultSet rs = null;
		      
		      List<DerogaQualificazioneSABean> listaderoghe = new ArrayList<DerogaQualificazioneSABean>();
		      
		      try {
		               
		         stmt = activeConnection.prepareStatement("SELECT "+DEROGA_QUALIFICAZIONE_SA.ID_DEROGA_QUALIFICAZIONE+", "
                         + DEROGA_QUALIFICAZIONE_SA.DESCRIZIONE
                         + " FROM "+DEROGA_QUALIFICAZIONE_SA.TABLE_NAME
                         + " WHERE " +DEROGA_QUALIFICAZIONE_SA.DATA_FINE_VALIDITA 
                         + " is null or " + DEROGA_QUALIFICAZIONE_SA.DATA_FINE_VALIDITA 
                         + " > convert(varchar, getdate(), 112)");
		         
//		         stmt.setObject(1, idLotto);

		         rs = stmt.executeQuery();

		         while (rs.next()) {

		        	 DerogaQualificazioneSABean DerogaQualificazioneSABean = new DerogaQualificazioneSABean();
		        	 DerogaQualificazioneSABean.setIdDerogaQualificazioneSA(rs.getLong(DEROGA_QUALIFICAZIONE_SA.ID_DEROGA_QUALIFICAZIONE));
		        	 DerogaQualificazioneSABean.setDescrizione(rs.getString(DEROGA_QUALIFICAZIONE_SA.DESCRIZIONE));
		        	 
		        	 	
		        	 listaderoghe.add(DerogaQualificazioneSABean);	                  
		         }
		      } catch (Exception e) {
		         logger.error("Impossibile caricare i motivi deroga", e);
		      } finally {
		         close(rs, stmt);
		      }
		      return listaderoghe;
		      
		      
		      
		   
		}
		
		public List<DerogaQualificazioneSABean> getAllDerogaQualificazioneSA() throws SQLException{
			PreparedStatement stmt = null;
		      ResultSet rs = null;
		      
		      List<DerogaQualificazioneSABean> listaderoghe = new ArrayList<DerogaQualificazioneSABean>();
		      
		      try {
		               
		         stmt = activeConnection.prepareStatement("SELECT * "
                         + " FROM "+DEROGA_QUALIFICAZIONE_SA.TABLE_NAME
                         + " WHERE " +DEROGA_QUALIFICAZIONE_SA.DATA_FINE_VALIDITA 
                         + " is null or " + DEROGA_QUALIFICAZIONE_SA.DATA_FINE_VALIDITA);
		         
//		         stmt.setObject(1, idLotto);

		         rs = stmt.executeQuery();

		         while (rs.next()) {

		        	 DerogaQualificazioneSABean DerogaQualificazioneSABean = new DerogaQualificazioneSABean();
		        	 DerogaQualificazioneSABean.setIdDerogaQualificazioneSA(rs.getLong(DEROGA_QUALIFICAZIONE_SA.ID_DEROGA_QUALIFICAZIONE));
		        	 DerogaQualificazioneSABean.setDescrizione(rs.getString(DEROGA_QUALIFICAZIONE_SA.DESCRIZIONE));
		        	 if (rs.getString(DEROGA_QUALIFICAZIONE_SA.DATA_FINE_VALIDITA) != null) {
		        		 DerogaQualificazioneSABean.setDataFineValidita(rs.getString(DEROGA_QUALIFICAZIONE_SA.DATA_FINE_VALIDITA));
					}
		        	 if (rs.getString(DEROGA_QUALIFICAZIONE_SA.DATA_INIZIO_VALIDITA) != null) {
		        		 DerogaQualificazioneSABean.setDataInizioValidita(rs.getString(DEROGA_QUALIFICAZIONE_SA.DATA_INIZIO_VALIDITA));
					}
		        	 
		        	 
		        	 	
		        	 listaderoghe.add(DerogaQualificazioneSABean);	                  
		         }
		      } catch (Exception e) {
		         logger.error("Impossibile caricare i motivi deroga", e);
		      } finally {
		         close(rs, stmt);
		      }
		      return listaderoghe;
		      
		      
		      
		   
		}
		
		public TableBean executeCaricaDerogaQualificazioneSA() throws SQLException {

			String fullSelect = "SELECT "+DEROGA_QUALIFICAZIONE_SA.ID_DEROGA_QUALIFICAZIONE+", "
                    + DEROGA_QUALIFICAZIONE_SA.DESCRIZIONE
                    + " FROM "+DEROGA_QUALIFICAZIONE_SA.TABLE_NAME
                    + " WHERE " +DEROGA_QUALIFICAZIONE_SA.DATA_FINE_VALIDITA 
                    + " is null or " + DEROGA_QUALIFICAZIONE_SA.DATA_FINE_VALIDITA 
                    + " > convert(varchar, getdate(), 112)";

			

			Statement stmt = activeConnection.createStatement();

			logger.debug("Esecuzione query [" + fullSelect + "]");

			TableBean tb = new TableBean(stmt.executeQuery(fullSelect));

			close(null, stmt);

			return tb;
		}
		
		public String getDerogaQualificazioneSAByID(long id_deroga_qualificazione_sa) throws SQLException {
		      PreparedStatement stmt = null;
		      ResultSet rs = null;
		      String derogaQualificazioneSADescr = null;
		      try {
		               
		         stmt = activeConnection.prepareStatement("SELECT "
                         + DEROGA_QUALIFICAZIONE_SA.DESCRIZIONE
                         + " FROM "+DEROGA_QUALIFICAZIONE_SA.TABLE_NAME
                         + " WHERE " +DEROGA_QUALIFICAZIONE_SA.ID_DEROGA_QUALIFICAZIONE 
                         + " = ? ");
//		         String dataFine = PageHelper.getFormattedNowOrInputFormattedDate(o, getNow());
//		         
//		         stmt.setObject(1, dataFine);
		         
		         
					stmt.setLong(1, id_deroga_qualificazione_sa);

					rs = stmt.executeQuery();

					if (rs.next()) {
						derogaQualificazioneSADescr = rs.getString(1);
					}

		       
		      } catch (Exception e) {
		         logger.error("Impossibile caricare i motivi deroga qualificazione SA", e);
		      } finally {
		         close(rs, stmt);
		      }
		      return derogaQualificazioneSADescr;
		   }
		
		
  
		//fine 3.04.9 MEV 40610
}
