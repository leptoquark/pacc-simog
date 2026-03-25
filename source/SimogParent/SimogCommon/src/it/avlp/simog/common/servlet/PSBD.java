package it.avlp.simog.common.servlet;

import it.avlp.simog.beans.IdentificativoSchede;
import it.avlp.simog.db.generated.AGGIUDICAZIONI;
import it.avlp.simog.db.generated.CATEGORIA;
import it.avlp.simog.db.generated.CLASSI_IMPORTO;
import it.avlp.simog.db.generated.CONDIZIONI_AGG;
import it.avlp.simog.db.generated.CONDIZIONI_LOTTO;
import it.avlp.simog.db.generated.CONTENZIOSI;
import it.avlp.simog.db.generated.DEROGA_QUALIFICAZIONE_SA;
import it.avlp.simog.db.generated.FINANZIAMENTI_AGG;
import it.avlp.simog.db.generated.INFO_AGGIUDICAZIONI;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.db.generated.MISURA_PREMIALE;
import it.avlp.simog.db.generated.MOTIVO_DEROGA;
import it.avlp.simog.db.generated.REQUISITI;
import it.avlp.simog.db.generated.RUOLI_RESPONSABILE;
import it.avlp.simog.db.generated.SOGGETTI_PARTECIPANTI;
import it.avlp.simog.db.generated.SOGGETTI_RESPONSABILI;
import it.avlp.simog.db.generated.TIPO_APPALTO_AGG;
import it.avlp.simog.db.generated.TIPO_FINANZIAMENTO;

public interface PSBD {
	// FIXME: ******** PP pezza per mancato refactoring delle jsp, da fare
	public static final String TAB_INFO_COMUNI =IdentificativoSchede.TAB_INFO_COMUNI;
	public static final String TAB_AGGIUDICAZIONE = IdentificativoSchede.TAB_AGGIUDICAZIONE;
	public static final String TAB_ADESIONE = IdentificativoSchede.TAB_ADESIONE;
	public static final String TAB_ESCLUSI = IdentificativoSchede.TAB_ESCLUSI;
	public static final String TAB_SOTTOSOGLIA = IdentificativoSchede.TAB_SOTTOSOGLIA;
	public static final String TAB_INIZIO_LAVORI = IdentificativoSchede.TAB_INIZIO_LAVORI;
	public static final String TAB_AVANZAMENTO = IdentificativoSchede.TAB_AVANZAMENTO;
	public static final String TAB_FINELAVORI = IdentificativoSchede.TAB_FINELAVORI;
	public static final String TAB_COLLAUDO = IdentificativoSchede.TAB_COLLAUDO;
	public static final String TAB_SOSPENSIONE = IdentificativoSchede.TAB_SOSPENSIONE;
	public static final String TAB_VARIANTE = IdentificativoSchede.TAB_VARIANTE;
	public static final String TAB_ACCORDO = IdentificativoSchede.TAB_ACCORDO; 
	public static final String TAB_RITARDO = IdentificativoSchede.TAB_RITARDO;
	public static final String TAB_INVITATO = "FCinvitati";
	
	public final static String NAVIGATION_RULES_LIST = "navigationRulesList";
//	public final static String BLOCCO_DATI_TIPO_APPALTO_AGG = "BLOCCO_DATI_TIPO_APPALTO_AGG";
//	public final static String BLOCCO_DATI_CONDIZIONI_AGG = "BLOCCO_DATI_CONDIZIONI_AGG";
	public final static String BLOCCO_DATI_PARAM_NAME ="BLOCCODATI_GLOBALE";
	public final static String BLOCCO_DATI_AGGIUDICAZIONI ="BLOCCO_DATI_AGGIUDICAZIONI";
	public final static String BLOCCO_DATI_RESPONSABILI ="BLOCCO_DATI_RESPONSABILI";
	public final static String BLOCCO_DATI_REQUISITI_QUA ="BLOCCO_DATI_REQUISITI_QUA";
	public final static String BLOCCO_DATI_REQUISITI_DPR ="BLOCCO_DATI_REQUISITI_DPR";
	public final static String BLOCCO_DATI_REQUISITI_EST ="BLOCCO_DATI_REQUISITI_EST";
	public final static String BLOCCO_DATI_CATEGORIE ="BLOCCO_DATI_CATEGORIE";
	public final static String BLOCCO_DATI_CLASSI_IMPORTO ="BLOCCO_DATI_CLASSI_IMPORTO";
	
	public final static String FIELD_NAME_PRG_CUI_RIAGG = AGGIUDICAZIONI.PROG_CUI_RIAGGIUDICATO;
	public final static String FIELD_NAME_MOD_RIAGG = AGGIUDICAZIONI.MODALITA_RIAGGIUDICAZIONE;
	public final static String BLOCCO_DATI_MASSIMO_RIBASSO ="BLOCCO_DATI_MASSIMO_RIBASSO";
	public final static String BLOCCO_DATI_CONTENZIOSI ="BLOCCO_DATI_CONTENZIOSI";
	public final static String BLOCCO_DATI_SOGGETTI ="BLOCCO_DATI_SOGGETTI";
	public final static String BLOCCO_DATI_AGGIUDICATARI ="BLOCCO_DATI_AGGIUDICATARI";
	public final static String BLOCCO_DATI_INFO_COMUNI = "datiComuni";
	public final static String FIELD_NAME_CONDIZIONI_AGG = CONDIZIONI_AGG.ID_CONDIZIONE;
	public final static String FIELD_NAME_CONDIZIONI_LOTTO = CONDIZIONI_LOTTO.ID_CONDIZIONE;//TICKET ALM #3835
	public final static String FIELD_NAME_TIPO_APPALTO_AGG_L = TIPO_APPALTO_AGG.ID_APPALTO+"L";
	public final static String FIELD_NAME_TIPO_APPALTO_AGG_SF = TIPO_APPALTO_AGG.ID_APPALTO+"SF";
	public final static String ID_TABELLA_AFFIDATARI = "ID_TABELLA_AFFIDATARI";
	public final static String ID_TABELLA_CONTENZIOSI = "ID_TABELLA_CONTENZIOSI";
	public final static String ID_TABELLA_RESPONSABILI = "ID_TABELLA_RESPONSABILI";
	
	public final static String FIELD_NAME_MOTIVO_DEROGA = MOTIVO_DEROGA.ID_MOTIVO;
	public final static String FIELD_NAME_MISURA_PREMIALE = MISURA_PREMIALE.ID_MISURA;
	
	//3.04.9 MEV 40610
	public final static String FIELD_NAME_DEROGA_QUALIFICAZIONE_SA = DEROGA_QUALIFICAZIONE_SA.ID_DEROGA_QUALIFICAZIONE;
	public final static String FLAG_IS_QUALIFICATA_KO = LOTTO.FLAG_IS_QUALIFICATA_KO;
	//fine 3.04.9 MEV 40610
	
	public final static String RUOLI_RESPONSABILE_TABLEBEAN = "RUOLI_RESPONSABILE_TABLEBEAN";
	public final static String TIPI_AGGIUDICATARIO_TABLEBEAN = "TIPI_AGGIUDICATARIO_TABLEBEAN";
	public final static String RESPONSABILE = "Incaricato";
	public final static String PRESTAZIONE = "Prestazione";
	public final static String PRESTAZIONE_SOGG_PART = "Prestazione_sogg_part";
	public final static String FINANZIAMENTO = "Finanziamento";
	public final static String AGGIUDICATARIO = "Aggiudicatario";
	public final static String SUBAFFIDATARIO = "SUBAFFIDATARIO";
	public final static String CONTENZIOSO = "Contenzioso";
	public final static String REQUISITO = "Requisito";
	public static final String INVITATO = "Invitato";
	//gm aggiunto per ditte ausiliarie
	public final static String DITTA_AUSILIARIA = "ditta ausiliaria";
	
	public final static String NOME_SCHEDA = "nome_scheda" ;
	
	public final static String NR_RIGHE_CONTENZIOSI = "nrRighe"+CONTENZIOSO;
	public final static String NR_RIGHE_AFFIDATARI = "nrRighe"+AGGIUDICATARIO;
	public final static String NR_RIGHE_RESPONSABILI = "nrRighe"+RESPONSABILE;
	public final static String NR_RIGHE_REQUISITI = "nrRighe"+REQUISITO;
	public final static String NR_RIGHE_FINANZIAMENTI = "nrRighe"+FINANZIAMENTO;
	public final static String NR_RIGHE_PRESTAZIONI = "nrRighe"+PRESTAZIONE;
	public final static String NR_RIGHE_INVITATO = "nrRighe"+INVITATO;
	public final static String NR_RIGHE_SUBAFFIDATARI = "nrRighe"+SUBAFFIDATARIO;
	public final static String HASSCHEDE = "HASSCHEDE" ;
		
	//aggiudicazioni
	public final static String FIELD_NAME_ID_AGGIUDICAZIONE = AGGIUDICAZIONI.ID_AGGIUDICAZIONE;
	//public final static String FIELD_NAME_DATA_INIZIO_AGGIUDICAZIONE = "dataInizioAggiudicazione";
	//public final static String FIELD_NAME_ID_RESPONSABILE="idResponsabile";
	public final static String FIELD_NAME_ID_MODALITA_GARA	= AGGIUDICAZIONI.ID_MODALITA_GARA;	
	public final static String FIELD_NAME_ID_MODO_INDIZIONE	= AGGIUDICAZIONI.ID_MODO_GARA;	
	public final static String FIELD_NAME_COD_STRUMENTO	= AGGIUDICAZIONI.COD_STRUMENTO;
	public final static String FIELD_NAME_ID_PUBBLICAZIONE_ESITO	= "idPubblicazioneEsito";
	//public final static String FIELD_NAME_ID_MASSIMO_RIBASSO	= "maxRibasso";
	public final static String FIELD_NAME_ID_INFO	= INFO_AGGIUDICAZIONI.ID_INFO;	
	public final static String FIELD_NAME_ID_SCELTA_CONTRAENTE= AGGIUDICAZIONI.ID_SCELTA_CONTRAENTE;

	public static final String FIELD_NAME_ID_TIPO_PRESTAZIONE =  AGGIUDICAZIONI.ID_TIPO_PRESTAZIONE;
	public static final String FIELD_NAME_DATA_AGGIUDICAZIONE_DEFINITIVA = AGGIUDICAZIONI.DATA_VERBALE_AGGIUDICAZIONE;
	public static final String FIELD_NAME_DATA_SCADENZA_RICHIESTA_INVITO = AGGIUDICAZIONI.DATA_SCADENZA_RICHIESTA_INVITO;

	public static final String FIELD_NAME_DATA_SCADENZA_PRES_OFFERTA = AGGIUDICAZIONI.DATA_SCADENZA_PRES_OFFERTA;
	//requisiti
	public static final String FIELD_NAME_ID_REQUISITO = REQUISITI.ID_REQUISITO;
	public static final String FIELD_NAME_DATA_INIZIO_REQ = REQUISITI.DATA_INIZIO_REQ;

	public static final String FIELD_NAME_ID_CATEGORIA = REQUISITI.ID_CATEGORIA;
	public static final String FIELD_NAME_DESCRIZIONE_CATEGORIA = CATEGORIA.DESCRIZIONE;
	public static final String FIELD_NAME_CLASSE_IMPORTO = CLASSI_IMPORTO.TABLE_NAME+CLASSI_IMPORTO.IMPORTO_DA;
	public static final String FIELD_NAME_ID_CLASSE_IMPORTO = REQUISITI.CLASSE_IMPORTO;
	public static final String FIELD_NAME_PREVALENTE = REQUISITI.PREVALENTE;
	public static final String FIELD_NAME_SCORPORABILE = REQUISITI.SCORPORABILE;
	public static final String FIELD_NAME_SUBAPPALTABILE = REQUISITI.SUBAPPALTABILE;

	public static final String FIELD_NAME_DATA_FINE_REQ = REQUISITI.DATA_FINE_REQ;
	public static final String FIELD_NAME_ID_STATO_REQUISITI = REQUISITI.ID_STATO;
    //GM NUOVO PER AVVISI AGGIUDICAZIONE
	public static final String FIELD_NAME_ID_PUBBLICAZIONE_AGG = AGGIUDICAZIONI.ID_PUBBLICAZIONE_AGG;
	public static final String FIELD_NAME_DATA_INIZIO_PUBB_AGG = AGGIUDICAZIONI.DATA_INIZIO_PUBB_AGG;
 
	//TICKET ALM #14639 - 3.04.5
	public static final String FIELD_NAME_RELAZIONE_UNICA = AGGIUDICAZIONI.RELAZIONE_UNICA;
	
	// finanaziamenti
	public static final String FIELD_NAME_TIPO_FINANZIAMENTO 		= TIPO_FINANZIAMENTO.TABLE_NAME+FINANZIAMENTI_AGG.ID_FINANZIAMENTO;
	public static final String FIELD_NAME_DES_FINANZIAMENTO 		= TIPO_FINANZIAMENTO.TABLE_NAME+TIPO_FINANZIAMENTO.DESCRIZIONE;
	public static final String FIELD_NAME_IMPORTO_FINANZIAMENTO 	= TIPO_FINANZIAMENTO.TABLE_NAME+FINANZIAMENTI_AGG.IMPORTO_FINANZIAMENTO;
	
	
	public final static String argsReq =  "'"+PSBD.FIELD_NAME_ID_CATEGORIA+"','"+
		PSBD.FIELD_NAME_DESCRIZIONE_CATEGORIA+"','"+
		PSBD.FIELD_NAME_CLASSE_IMPORTO+"','"+
		PSBD.FIELD_NAME_PREVALENTE+"','"+
		PSBD.FIELD_NAME_SCORPORABILE+"','"+
		PSBD.FIELD_NAME_SUBAPPALTABILE+"'";
	
	public final static String argsReqNascosti = "'"+PSBD.FIELD_NAME_ID_CLASSE_IMPORTO+"'"; 
												
	
	public final static String FIELD_NAME_NUM_IMPRESE_INVITATE	= AGGIUDICAZIONI.NUM_IMPRESE_INVITATE;	
	public final static String FIELD_NAME_NUM_IMPRESE_RICHIEDENTI	= AGGIUDICAZIONI.NUM_IMPRESE_RICHIEDENTI;
	public final static String FIELD_NAME_NUM_IMPRESE_OFFERENTI	= AGGIUDICAZIONI.NUM_IMPRESE_OFFERENTI;
	public final static String FIELD_NAME_NUM_OFFERTE_AMMESSE	= AGGIUDICAZIONI.NUM_OFFERTE_AMMESSE;
	public final static String FIELD_NAME_NUM_IMP_ESCLUSE_AUTOMATICAMENTE	= AGGIUDICAZIONI.NUM_OFFERTE_ESCLUSE;
	public final static String FIELD_NAME_NUM_IMP_ESCLUSE_INSUF_GIUST	= AGGIUDICAZIONI.NUM_IMP_ESCL_INSUF_GIUST;
	public final static String FIELD_NAME_NUM_OFFERTE_ESCLUSE	= AGGIUDICAZIONI.NUM_OFFERTE_ESCLUSE;
	public static final String FIELD_NAME_PROCEDURA_ACC = AGGIUDICAZIONI.PROCEDURA_ACC;
	public static final String FIELD_NAME_PREINFORMAZIONE = AGGIUDICAZIONI.PREINFORMAZIONE;
	public static final String FIELD_NAME_TERMINE_RIDOTTO = AGGIUDICAZIONI.TERMINE_RIDOTTO;
	public final static String FIELD_NAME_CUI=AGGIUDICAZIONI.CUI;
	public final static String FIELD_NAME_PROG_CUI=AGGIUDICAZIONI.PROG_CUI;
	public final static String FIELD_NAME_IMPORTO_LAVORI=AGGIUDICAZIONI.IMPORTO_LAVORI;
	public final static String FIELD_NAME_IMPORTO_SERVIZI=AGGIUDICAZIONI.IMPORTO_SERVIZI;
	public final static String FIELD_NAME_IMPORTO_FORNITURE=AGGIUDICAZIONI.IMPORTO_FORNITURE;
	
	
	public final static String FIELD_NAME_SISTEMA_QUALIFICAZIONE	= AGGIUDICAZIONI.SISTEMA_QUALIFICAZIONE;
	public final static String S_FIELD_NAME_SISTEMA_QUALIFICAZIONE	= "Si"+AGGIUDICAZIONI.SISTEMA_QUALIFICAZIONE;
	public final static String N_FIELD_NAME_SISTEMA_QUALIFICAZIONE	= "No"+AGGIUDICAZIONI.SISTEMA_QUALIFICAZIONE;
	public final static String FIELD_NAME_CRITERI_SELEZIONE_STABILITI_SA	= AGGIUDICAZIONI.CRITERI_SELEZIONE_STABILITI_SA;
	public final static String S_FIELD_NAME_CRITERI_SELEZIONE_STABILITI_SA	= "Si"+AGGIUDICAZIONI.CRITERI_SELEZIONE_STABILITI_SA;
	public final static String N_FIELD_NAME_CRITERI_SELEZIONE_STABILITI_SA	= "No"+AGGIUDICAZIONI.CRITERI_SELEZIONE_STABILITI_SA;
	
	
	public final static String FIELD_NAME_IMPORTO_AGGIUDICAZIONE	= AGGIUDICAZIONI.IMPORTO_AGGIUDICAZIONE;
	public final static String FIELD_NAME_IMPORTO_COMPLESSIVO	= AGGIUDICAZIONI.IMPORTO_COMPLESSIVO;	
	public final static String FIELD_NAME_IMPORTO_ATTUAZIONE_SICUREZZA	= AGGIUDICAZIONI.IMPORTO_ATTUAZIONE_SICUREZZA;
	public final static String FIELD_NAME_IMPORTO_DISPOSIZIONE	= AGGIUDICAZIONI.IMPORTO_DISPOSIZIONE;
	public final static String FIELD_NAME_IMPORTO_PROGETTAZIONE	= AGGIUDICAZIONI.IMPORTO_PROGETTAZIONE;
	public final static String FIELD_NAME_IMPORTO_NON_ASSOG	= AGGIUDICAZIONI.IMP_NON_ASSOG;
	
	//nuovi campi aggiudicazioni
	public final static String FIELD_NAME_CUP = AGGIUDICAZIONI.CUP;
	public final static String FIELD_NAME_FLAG_ACCORDO_QUADRO = AGGIUDICAZIONI.FLAG_ACCORDO_QUADRO;
	public final static String S_FIELD_NAME_FLAG_ACCORDO_QUADRO = "Si"+AGGIUDICAZIONI.FLAG_ACCORDO_QUADRO;
	public final static String N_FIELD_NAME_FLAG_ACCORDO_QUADRO = "No"+AGGIUDICAZIONI.FLAG_ACCORDO_QUADRO;
	
	public final static String FIELD_NAME_LUOGO_ISTAT = AGGIUDICAZIONI.LUOGO_ISTAT;
	public final static String FIELD_NAME_LUOGO_NUTS = AGGIUDICAZIONI.LUOGO_NUTS;
	
	public final static String FIELD_NAME_CIG_PROG_ESTERNA = it.avlp.simog.db.generated.RESPONSABILE.CIG_PROG_ESTERNA;
	public final static String FIELD_NAME_DATA_AFF_PROG_ESTERNA = it.avlp.simog.db.generated.RESPONSABILE.DATA_AFF_PROG_ESTERNA;
	public final static String FIELD_NAME_DATA_CONS_PROG_ESTERNA = it.avlp.simog.db.generated.RESPONSABILE.DATA_CONS_PROG_ESTERNA;
	public final static String FIELD_NAME_ASTA_ELETTRONICA = AGGIUDICAZIONI.ASTA_ELETTRONICA;
	public final static String S_FIELD_NAME_ASTA_ELETTRONICA = "Si"+AGGIUDICAZIONI.ASTA_ELETTRONICA;
	public final static String N_FIELD_NAME_ASTA_ELETTRONICA = "No"+AGGIUDICAZIONI.ASTA_ELETTRONICA;
		
	public final static String FIELD_NAME_PERC_RIBASSO_AGG = AGGIUDICAZIONI.PERC_RIBASSO_AGG;
	public final static String FIELD_NAME_PERC_OFF_AUMENTO = AGGIUDICAZIONI.PERC_OFF_AUMENTO;
	public final static String FIELD_NAME_DATA_INVITO = AGGIUDICAZIONI.DATA_INVITO;
	public final static String FIELD_NAME_NUM_MANIF_INTERESSE = AGGIUDICAZIONI.NUM_MANIF_INTERESSE;
	public final static String FIELD_NAME_DATA_MANIF_INTERESSE = AGGIUDICAZIONI.DATA_MANIF_INTERESSE;
	
	public final static String FIELD_NAME_FLAG_RICH_SUBAPPALTO = AGGIUDICAZIONI.FLAG_RICH_SUBAPPALTO;
	
	public final static String FIELD_NAME_DURATA_CONVENZIONE = AGGIUDICAZIONI.DURATA_CONVENZIONE;
	//gm nuovo codice 3.0
	public final static String FIELD_NAME_OPERE_URBANIZZAZIONE = AGGIUDICAZIONI.OPERE_URBANIZZAZIONE;
	//gm fine nuovo codice 3.0
	
	//gm nuovo per appalti multilotto
    public final static String FIELD_NAME_CODICE_CONTRATTO = AGGIUDICAZIONI.CODICE_CONTRATTO;	
	public final static String FIELD_NAME_FLAG_AGGIUD_PRINCIPALE = AGGIUDICAZIONI.FLAG_AGGIUD_PRINCIPALE;
	public final static String S_FIELD_NAME_FLAG_AGGIUD_PRINCIPALE = "Si"+AGGIUDICAZIONI.FLAG_AGGIUD_PRINCIPALE;
	public final static String N_FIELD_NAME_FLAG_AGGIUD_PRINCIPALE = "No"+AGGIUDICAZIONI.FLAG_AGGIUD_PRINCIPALE;
	
	
	public final static String DATA_INIZIO_AGGIUDICAZIONE = AGGIUDICAZIONI.DATA_INIZIO_AGGIUDICAZIONE;
	public final static String DATA_FINE_AGGIUDICAZIONE = AGGIUDICAZIONI.DATA_FINE_AGGIUDICAZIONE;
	public final static String DATA_INIZIO_INFO= INFO_AGGIUDICAZIONI.DATA_INIZIO_INFO;
	public final static String DATA_VERBALE_AGGIUDICAZIONE	= AGGIUDICAZIONI.DATA_VERBALE_AGGIUDICAZIONE;
	public final static String DATA_INIZIO_RESPONSABILE ="dataInizioResponsabile";
	
	
	public final static String ID_BLOCCO_DATI = "idBloccoDati";
	public final static String DATA_INIZIO_BLOCCO_DATI = "dataInizioBloccoDati";
		
	public static final String ACTION_TYPE = "tipoAzione";
	public static final String ACTION = "action";
	public static final String ACTION_SALVA = "SalvaGlobale";
	public static final String ACTION_SALVA_RESPONSABILE = "SalvaResponsabile";
	public static final String ACTION_SALVA_AFFIDATARIO = "SalvaAffidatario";
	public static final String ACTION_SALVA_CONTENZIOSO = "SalvaContenzioso";
	public static final String ACTION_CONFERMA = "Conferma";
	public static final String ACTION_CAMBIA_TAB = "CambiaTab";
	public static final String ACTION_AGGIUNGI = "Aggiungi";
	public static final String ACTION_ADD = "Aggiungi";
	public static final String ACTION_MODIFICA = "Modifica";
	public static final String ACTION_MODIFICA_CONTENZIOSO = "ModificaContenzioso";
	public static final String ACTION_MODIFICA_AFFIDATARIO = "ModificaAffidatario";
	public static final String ACTION_CANCELLA = "Cancella";
	public static final String ACTION_CANCELLA_CONTENZIOSO = "CancellaContenzioso";
	public static final String ACTION_CANCELLA_AFFIDATARIO = "CancellaAffidatario";
	public static final String ACTION_CANCELLA_RESPONSABILE = "CancellaResponsabile";
	public static final String ACTION_CERCA = "Cerca";
	public static final String ACTION_RICHIESTA_ANNULLAMENTO = "richiesta_annulla";
	public static final String ACTION_RICHIESTA_CANCELLAZIONE = "richiesta_cancella";
	public static final String ACTION_LOAD = "Carica";
	public static final String ACTION_RIAGGIUDICAZIONE = "riaggiudica";
	public static final String ACTION_VARIAZIONI_ANAGRAFICHE = "variazioni_anagrafiche";
	public static final String ACTION_VARIAZIONI_ANAGRAFICHE_SAVE = "variazioni_anagrafiche_save";
	public static final String ACTION_CARICA_JSP_ANNULLAMENTO = "loadJspAnnullamento";
	public static final String ACTION_CARICA_JSP_CANCELLAZIONE = "loadJspCancellazione";
	public static final String ACTION_REIMPOSTA = "Reimposta";
	public static final String ACTION_HST_SCHEDA = "hstScheda";

	public static final String MOTIVAZIONE_ANNULLAMENTO = "motivazione_annullamento";
	public static final String MOTIVAZIONE_CANCELLAZIONE = "motivazione_cancellazione";
	public static final String CANC_COMPLETA = "cancCompleta";

	public static final String TAB = "tab";

	//identificativi delle schede per richiesta annullamento
	
	public static final String TAB_RESPONSABILE_PROCEDIMENTO = "TabResponsabileProcedimento";
	public static final String TAB_ADD_RESPONSABILE = "TabAddResponsabile";
	public static final String TAB_REQUISITI = "TabRequisiti";
	public static final String TAB_MODIFICA_RESPONSABILE = "TabModificaResponsabile";
	public static final String TAB_PUBBLICAZIONE_ESITO = "TabPubblicazioneEsito";
	public static final String TAB_CONTENZIOSO = "TabContenzioso";
	public static final String TAB_ADD_CONTENZIOSO = "TabAddContenzioso";
	public static final String TAB_MODIFICA_CONTENZIOSO = "TabModificaContenzioso";
	public static final String TAB_AFFIDATARIO = "TabAffidatario";
	
	public static final String TAB_ADD_AFFIDATARIO = "TabAddAffidatario";
	public static final String TAB_AFFIDATARIO_SELECTED = "TabAffidatarioSelected";
	public static final String TAB_MODIFICA_AFFIDATARIO = "TabModificaAffidatario";
	public static final String TAB_RUBRICA_AFFIDATARIO = "TabRubAffidatario";
	public static final String TAB_RUBRICA_RESPONSABILI = "TabRubResponsabili";
	public static final String TAB_LISTA_AFFIDATARI = "TabListaAffidatari";
	public static final String TAB_RICHIESTA_ANNULLAMENTO = "tabRichiestaAnnullamento";
	public static final String TAB_PRESTAZIONI = "TabPrestazioni";
	public static final String TAB_FINANZIAMENTI = "TabFinanziamenti";
	//gm aggiunto per ditte ausiliarie
	public static final String TAB_DITTA_AUSILIARIA = "TabDittaAusiliaria";
	
	public static final String JSP_GESTIONE_TAB = "gestioneTabAggiudicazioni.jsp";
	public static final String JSP_GESTIONE_AGGIUDICAZIONI = "aggiudicazioni.jsp";
	public static final String JSP_DATI_COMUNI = "datiComuni.jsp";
	public static final String SRV_DATI_COMUNI ="datiComuni";
	//modalita' gara
	public final static String FIELD_NAME_DESCRIZIONE ="descrizione";
	public final static String DATA_ULTIMA_MODIFICA ="dataUltimaModifica";
	public final static String DATA_FINE_VALIDITA ="dataFineValidita";
	
	
	// Blocco dati massimo ribasso
	
	
	public final static String FIELD_NAME_NUM_OFFERTE_ESCLUS_AUTOMATICA =AGGIUDICAZIONI.NUM_OFFERTE_ESCLUSE;
	public final static String FIELD_NAME_NUM_IMP_ESCL_INSUF_GIUST =AGGIUDICAZIONI.NUM_IMP_ESCL_INSUF_GIUST;
	public final static String FIELD_NAME_OFFERTA_MASSIMO_RIBASSO =AGGIUDICAZIONI.OFFERTA_MASSIMO;
	public final static String FIELD_NAME_OFFERTA_MINIMO_RIBASSO =AGGIUDICAZIONI.OFFERTA_MINIMA;
	public final static String FIELD_NAME_VALORE_SOGLIA_ANOMALIA =AGGIUDICAZIONI.VAL_SOGLIA_ANOMALIA;
	public final static String FIELD_NAME_NUM_OFFERTE_MAG_SOGLIA = AGGIUDICAZIONI.NUM_OFFERTE_FUORI_SOGLIA;

	
	// Blocco dati responsabile
	
	public final static String FIELD_NAME_ID_RESPONSABILE = it.avlp.simog.db.generated.RESPONSABILE.ID_RESPONSABILE;
	public final static String FIELD_NAME_DATA_INIZIO_RES = it.avlp.simog.db.generated.RESPONSABILE.DATA_INIZIO_RES;
	public final static String FIELD_NAME_CODICE_FISCALE_RESPONSABILE = SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE;
	public final static String FIELD_NAME_COGNOME_RESPONSABILE = SOGGETTI_RESPONSABILI.COGNOME;
	public final static String FIELD_NAME_NOME_RESPONSABILE = SOGGETTI_RESPONSABILI.NOME;
	public final static String FIELD_NAME_TELEFONO_RESPONSABILE = SOGGETTI_RESPONSABILI.TELEFONO;
	public final static String FIELD_NAME_EMAIL_RESPONSABILE = SOGGETTI_RESPONSABILI.EMAIL;
	public final static String FIELD_NAME_FAX_RESPONSABILE = SOGGETTI_RESPONSABILI.FAX;
	public final static String FIELD_NAME_DATA_FINE_RES = SOGGETTI_RESPONSABILI.DATA_FINE_RES;
	public static final String FIELD_NAME_ID_STATO_RESPONSABILE = it.avlp.simog.db.generated.RESPONSABILE.ID_STATO;
	public static final String FIELD_NAME_ID_RUOLO_RESPONSABILE = it.avlp.simog.db.generated.RESPONSABILE.ID_RUOLO;
	public static final String FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE = RUOLI_RESPONSABILE.TABLE_NAME+RUOLI_RESPONSABILE.DESCRIZIONE;
	public static final String FIELD_NAME_INDIRIZZO_RESPONSABILE = SOGGETTI_RESPONSABILI.INDIRIZZO;
	public static final String FIELD_NAME_ANAG = "DATIANAG";
	public static final String FIELD_NAME_PRESTAZIONE_ANAG = "PRESTDATIANAG";
	public static final String FIELD_NAME_ANAGOE = "DATIANAGOE";

	// blocco dati contenziosi
	public final static String FIELD_NAME_ID_CONTENZIOSO 		= CONTENZIOSI.ID_CONTENZIOSO;
	public final static String FIELD_NAME_DATA_INIZIO_CONT 		= CONTENZIOSI.DATA_INIZIO_CONT;
	public final static String FIELD_NAME_CONTENZIOSO_GARA 		= CONTENZIOSI.CONTENZIOSO_GARA;
	public final static String S_FIELD_NAME_CONTENZIOSO_GARA 	= "Si"+CONTENZIOSI.CONTENZIOSO_GARA;
	public final static String N_FIELD_NAME_CONTENZIOSO_GARA 	= "No"+CONTENZIOSI.CONTENZIOSO_GARA;
	public final static String FIELD_NAME_MOTIVAZIONE 			= CONTENZIOSI.MOTIVAZIONE;
	public final static String FIELD_NAME_CODICE_FISCALE_DITTA 	= CONTENZIOSI.CODICE_FISCALE_DITTA;
	public final static String FIELD_NAME_DATA_FINE_CONT 		= CONTENZIOSI.DATA_FINE_CONT;
	public final static String FIELD_NAME_ID_STATO_CONTEZIOSO 	= CONTENZIOSI.ID_STATO;
	
	// blocco prestazioni
	public final static String FIELD_NAME_PRESTAZIONE_NOME						= "PREST" + SOGGETTI_RESPONSABILI.NOME;
	public final static String FIELD_NAME_PRESTAZIONE_COGNOME					= "PREST" + SOGGETTI_RESPONSABILI.COGNOME;
	public final static String FIELD_NAME_PRESTAZIONE_CODICEFISCALE				= "PREST" + SOGGETTI_RESPONSABILI.CODICE_FISCALE_RESPONSABILE;		
	public final static String FIELD_NAME_PRESTAZIONE_ID_RUOLO					= "PREST" + it.avlp.simog.db.generated.RESPONSABILE.ID_RUOLO;
	public final static String FIELD_NAME_PRESTAZIONE_CIG_PROG_ESTERNA			= "PREST" + it.avlp.simog.db.generated.RESPONSABILE.CIG_PROG_ESTERNA;
	public final static String FIELD_NAME_PRESTAZIONE_DATA_AFF_PROG_ESTERNA		= "PREST" + it.avlp.simog.db.generated.RESPONSABILE.DATA_AFF_PROG_ESTERNA;
	public final static String FIELD_NAME_PRESTAZIONE_DATA_CONS_PROG_ESTERNA	= "PREST" + it.avlp.simog.db.generated.RESPONSABILE.DATA_CONS_PROG_ESTERNA;
	public final static String FIELD_NAME_PRESTAZIONE_DESCRIZIONE_RUOLO			= "PREST" + RUOLI_RESPONSABILE.DESCRIZIONE;
	public final static String FIELD_NAME_PRESTAZIONE_ID_RESPONSABILE			= "PREST" + it.avlp.simog.db.generated.RESPONSABILE.ID_RESPONSABILE;
	public final static String FIELD_NAME_PRESTAZIONE_DATA_INIZIO_RES			= "PREST" + it.avlp.simog.db.generated.RESPONSABILE.DATA_INIZIO_RES;
	
	// blocco affidatario
	public final static String FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE   = it.avlp.simog.db.generated.AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE;
	public final static String FIELD_NAME_AGG_ID_AGGIUDICAZIONE          = "idAggiudicazione";
	public final static String FIELD_NAME_AGG_DATA_INIZIO_SOGG           = it.avlp.simog.db.generated.AGGIUDICATARIO.DATA_INIZIO_SOGG;
	public final static String FIELD_NAME_AGG_RUOLO                      = "RuoloAgg";
	public final static String FIELD_NAME_AGG_TIPO                       = "TipoAgg";
	// Rinaldo ticket 654 ////////
	public final static String FIELD_NAME_IMP_AGGIUDICATARIO	 		 = it.avlp.simog.db.generated.AGGIUDICATARIO.IMPORTO_AGGIUDICATARIO;
	public final static String FIELD_NAME_PERC_RIBASSO_AGGIUDICATARIO	 = it.avlp.simog.db.generated.AGGIUDICATARIO.PERC_RIBASSO_AGGIUDICATARIO;
	public final static String FIELD_NAME_PERC_AUMENTO_AGGIUDICATARIO	 = it.avlp.simog.db.generated.AGGIUDICATARIO.PERC_AUMENTO_AGGIUDICATARIO;
	public final static String FIELD_NAME_FLAG_TIPOLOGIA_AFFIDATARIO      = "FLAG_TIPOLOGIA_AFFIDATARIO";
	public final static String FIELD_NAME_FLAG_TIPOLOGIA_AFFIDATARIO_SINGOLO  = "Singolo";
	public final static String FIELD_NAME_FLAG_TIPOLOGIA_AFFIDATARIO_MULTIPLO = "Multiplo";
	//////////////////////////////
	public final static String FIELD_NAME_AGG_ID_RUOLO                   = it.avlp.simog.db.generated.AGGIUDICATARIO.RUOLO;
	public final static String FIELD_NAME_AGG_ID_STATO                   = it.avlp.simog.db.generated.AGGIUDICATARIO.ID_STATO;
	public final static String FIELD_NAME_AGG_DATA_INIZIO_AGGIUDICAZIONE = "dataInizioAgg";
	public final static String FIELD_NAME_AGG_PERCENTUALE                = it.avlp.simog.db.generated.AGGIUDICATARIO.PERCENTUALE;
	public final static String FIELD_NAME_AGG_DATA_INIZIO                = it.avlp.simog.db.generated.AGGIUDICATARIO.DATA_INIZIO;
	public final static String FIELD_NAME_AGG_DATA_FINE                  = it.avlp.simog.db.generated.AGGIUDICATARIO.DATA_FINE;
	public final static String FIELD_NAME_AGG_FLAG_AVVALIMENTO			 = it.avlp.simog.db.generated.AGGIUDICATARIO.FLAG_AVVALIMENTO;
	public final static String S_FIELD_NAME_AGG_FLAG_AVVALIMENTO		 = "Si"+it.avlp.simog.db.generated.AGGIUDICATARIO.FLAG_AVVALIMENTO;
	public final static String N_FIELD_NAME_AGG_FLAG_AVVALIMENTO		 = "No"+it.avlp.simog.db.generated.AGGIUDICATARIO.FLAG_AVVALIMENTO;
	public final static String REQUISITI_FLAG_AVVALIMENTO 				 = "Per i Requisiti";
	public final static String ATTTESTAZIONE_FLAG_AVVALIMENTO 			 = "Per l'Attestazione";
	public final static String ENTRAMBI_FLAG_AVVALIMENTO 				 = "Per i Requisiti e l'Attestazione";
	public final static String NESSUNO_FLAG_AVVALIMENTO 				 = "No";
	public final static String FIELD_NAME_AGG_CF_AUSILIARIA				 = it.avlp.simog.db.generated.AGGIUDICATARIO.CF_AUSILIARIA;
	public final static String FIELD_NAME_AGG_ID_TIPO_AGG				 = it.avlp.simog.db.generated.AGGIUDICATARIO.ID_TIPOAGG;
	public final static String FIELD_NAME_AGG_DESCRIZIONE_TIPO_AGG		 = "DESCRIZIONE_TIPO_AGG";
	public final static String FIELD_NAME_AGG_COGNOME				 	 = SOGGETTI_PARTECIPANTI.TABLE_NAME+SOGGETTI_PARTECIPANTI.COGNOME;
	public final static String FIELD_NAME_AGG_DENOMINAZIONE				 = SOGGETTI_PARTECIPANTI.TABLE_NAME+SOGGETTI_PARTECIPANTI.DENOMINAZIONE;
	public final static String FIELD_NAME_AGG_NOME						 = SOGGETTI_PARTECIPANTI.TABLE_NAME+SOGGETTI_PARTECIPANTI.NOME;
	public final static String FIELD_NAME_AGG_ID_PAESE					 = SOGGETTI_PARTECIPANTI.TABLE_NAME+SOGGETTI_PARTECIPANTI.ID_STATO; 
	//gm aggiunto per ditte ausiliarie
	public final static String FIELD_NAME_AGG_ID_AGGIUDICATARIO          = it.avlp.simog.db.generated.AGGIUDICATARIO.ID_AGGIUDICATARIO;
	public final static String FIELD_NAME_AGG_PARAMETRI_AUSILIARIE       = "parametri_ditte_ausiliarie";
	
	//gm aggiunto per raggruppamenti di impresa
	public final static String FIELD_NAME_AGG_ID_GRUPPO		             = it.avlp.simog.db.generated.AGGIUDICATARIO.ID_GRUPPO; 
	public final static String FIELD_NAME_AGG_PARAMETRI_GRUPPI           = "parametri_ATI_Consorzio";
	public final static String FIELD_NAME_AGG_LISTA_GRUPPI               = "lista_ATI_Consorzio";  
	public final static String FIELD_NAME_AGG_ATI                        = "ATI (Mandataria)";  
	public final static String FIELD_NAME_AGG_CONSORZIO                  = "Consorzio";  
	public final static String FIELD_NAME_AGG_IMPRESA_SINGOLA            = "Impresa Singola";  
	public final static String FIELD_NAME_AGG_GEIE                       = "GEIE";  
	public final static String DITTA_RAGGRUPPAMENTO                      = "ditta nel Raggruppamento";
	public static final String TAB_DITTA_RAGGRUPPAMENTO                  = "TabDittaRaggruppamento";
	public static final String FIELD_NAME_READONLY_AFFIDATARIO           = "readonlyAffidatario";
	public final static String FIELD_NAME_ASSOCIAZIONE_CATEGORIA         = "Associazione di categoria";  
	public final static String FIELD_NAME_ATI_ASS_CATEGORIA        		 = "list_ATI_cat";  
	public final static String FIELD_NAME_AGG_PARAMETRI_GRUPPI_CAT       = "p_ATI_cat";
	
	//gm aggiunto per ditte ausiliarie
	public final static String FIELD_NAME_AGG_LISTA_AUSILIARIE           = "lista_ditte_ausiliarie";  
	
	                    // SOGGETTI_PARTECIPANTI.ID_STATO in conflitto con AGGIUDICATARIO.ID_STATO, quindi lo chiamo ID_PAESE 
	
	public final static String FIELD_NAME_CODICE_FISCALE_AFFIDATARIO 	 = SOGGETTI_PARTECIPANTI.CODICE_FISCALE;
	public final static String FIELD_NAME_PARTITA_IVA_AFFIDATARIO 	     = SOGGETTI_PARTECIPANTI.PARTITA_IVA;
	
	public final static String PREF_POSIZ 					 			 = "POSIZ";
	public final static String FIELD_NAME_COD_FISC_POSIZIONI		 	 = PREF_POSIZ + SOGGETTI_PARTECIPANTI.CODICE_FISCALE;
	public final static String FIELD_NAME_AGG_ID_SOGG_POSIZIONI          = PREF_POSIZ + it.avlp.simog.db.generated.AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE;
	public final static String FIELD_NAME_AGG_DATA_INIZIO_SOGG_POSIZIONI = PREF_POSIZ + it.avlp.simog.db.generated.AGGIUDICATARIO.DATA_INIZIO_SOGG;
	
//	public static final String SRV_BLOCCO_DATI = "loadBloccoDati";
	
	public final static String FIELD_NAME_ORIGINE_SCHEDA                 = "ORIGINE_SCHEDA";
	
	
	// SUBAPPALTO 
	public static final String TAB_SUBAPPALTO = "TabSubappalto";
	public final static String FIELD_NAME_CODICE_FISCALE_SUBAFFIDATARIO  = SOGGETTI_PARTECIPANTI.CODICE_FISCALE;
	public final static String FIELD_NAME_PARTITA_IVA_SUBAFFIDATARIO  = SOGGETTI_PARTECIPANTI.PARTITA_IVA;
	public final static String FIELD_NAME_SUBAFFIDATARIO_ID_STATO = SOGGETTI_PARTECIPANTI.TABLE_NAME+SOGGETTI_PARTECIPANTI.ID_STATO;  
	
	
	/***********************************************************************************
	 * Richiesto Annullamento
	 */
	public final static String MSG_RICHIESTO_ANNULLAMENTO = " (richiesta modifica)";
	

	public final static String MSG_RICHIESTA_CANCELLAZIONE = " (richiesta cancellazione)";
	public final static String MSG_RICHIESTA_CANC_TOTALE = " (richiesta cancellazione totale)";

	// codici sezione per responsabili in aggiudicazione, inizio lavori e collaudo
	public final static String SEZIONE_RA = "RA"; 
	public final static String SEZIONE_PA = "PA";
	public final static String SEZIONE_IN = "IN";
	public final static String SEZIONE_CO = "CO";
	
	// sezioni per adesione, sottosoglia ed esclusi
	public final static String SEZIONE_RS = "RS"; // sottosoglia 
	public final static String SEZIONE_RE = "RE"; // esclusi
	public final static String SEZIONE_RQ = "RQ"; // adesione
	
	 public final String FIELD_NAME_DATA_STIPULA = AGGIUDICAZIONI.DATA_STIPULA;
	 public final String FIELD_NAME_DURATA_CONTRATTUALE = AGGIUDICAZIONI.DURATA_CONTRATTUALE;
	 public final String FIELD_NAME_TERMINE_CONTRATTUALE = AGGIUDICAZIONI.TERMINE_CONTRATTUALE;
	 
	 
	public static final String VAR_ANN = "variazioniAnagrafiche";
	
	//invitati
	public final static String FIELD_NAME_INV_ID_SOGGETTO_PARTECIPANTE   = it.avlp.simog.db.generated.AGGIUDICATARIO.ID_SOGGETTO_PARTECIPANTE;

	 public final String FIELD_NAME_MOTIVO_CO = AGGIUDICAZIONI.ID_MOTIVO_VAR_CO;
	 
	  public final static String FIELD_NAME_MOTIVO_RICH = "motivo_rich";

	 public final String TAB_RICHANN = "tabRichAnn";
	 public final String TAB_VARANAG = "tabVarAnag";
		
	public final static String argsResp = "'"+PSBD.FIELD_NAME_COGNOME_RESPONSABILE+"','"+
		PSBD.FIELD_NAME_NOME_RESPONSABILE+"','"+				 
		PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE+"','"+
		PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE+"'";
	
	public final static String argsRespNascosti = "'"+PSBD.FIELD_NAME_DESCRIZIONE_RUOLO_RESPONSABILE+"','"+
		PSBD.FIELD_NAME_DATA_INIZIO_RES+"','"+
	 	PSBD.FIELD_NAME_ID_RESPONSABILE+"','"+
	 	PSBD.FIELD_NAME_ANAG+"'";
	
	public final static String argsRespAdesione = "'"+PSBD.FIELD_NAME_COGNOME_RESPONSABILE+"','"+
	PSBD.FIELD_NAME_NOME_RESPONSABILE+"','"+				 
	PSBD.FIELD_NAME_CODICE_FISCALE_RESPONSABILE+"','"+
	PSBD.FIELD_NAME_ID_RUOLO_RESPONSABILE+"','"+
	PSBD.FIELD_NAME_INDIRIZZO_RESPONSABILE+"','"+
	PSBD.FIELD_NAME_TELEFONO_RESPONSABILE+"','"+
	PSBD.FIELD_NAME_FAX_RESPONSABILE +"'";

	public final static String argsPrest = "'"+ PSBD.FIELD_NAME_PRESTAZIONE_COGNOME +"','"+
	PSBD.FIELD_NAME_PRESTAZIONE_NOME +"','"+				 
	PSBD.FIELD_NAME_PRESTAZIONE_CODICEFISCALE +"','"+
	PSBD.FIELD_NAME_PRESTAZIONE_ID_RUOLO +"','"+
	PSBD.FIELD_NAME_PRESTAZIONE_CIG_PROG_ESTERNA +"','"+
	PSBD.FIELD_NAME_PRESTAZIONE_DATA_AFF_PROG_ESTERNA +"','"+
	PSBD.FIELD_NAME_PRESTAZIONE_DATA_CONS_PROG_ESTERNA+"'";

	public final static String argsPrestNascosti = "'"+PSBD.FIELD_NAME_PRESTAZIONE_DESCRIZIONE_RUOLO+"','"+
	PSBD.FIELD_NAME_PRESTAZIONE_DATA_INIZIO_RES+"','"+
 	PSBD.FIELD_NAME_PRESTAZIONE_ID_RESPONSABILE +"','"+
 	PSBD.FIELD_NAME_PRESTAZIONE_ANAG+"'";
	
	public final static String argsCont = "'"+PSBD.FIELD_NAME_MOTIVAZIONE+"','"+
		PSBD.FIELD_NAME_CODICE_FISCALE_DITTA+"','"+
		PSBD.S_FIELD_NAME_CONTENZIOSO_GARA+"','"+
		PSBD.N_FIELD_NAME_CONTENZIOSO_GARA+"'";

	public final static String argsFin = "'"+PSBD.FIELD_NAME_TIPO_FINANZIAMENTO +"','"+
	PSBD.FIELD_NAME_IMPORTO_FINANZIAMENTO+"'";
	
	public final static String argsFinNascosti = "'"+PSBD.FIELD_NAME_DES_FINANZIAMENTO+"'";

	public final static String argsContNascosti = "";
	
	public final static String argsAggiud = "'"+
		PSBD.FIELD_NAME_AGG_DENOMINAZIONE+"','"+		
		PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO+"','"+	
		PSBD.FIELD_NAME_AGG_ID_PAESE+"','"+
		//PSBD.FIELD_NAME_AGG_PERCENTUALE+"','"+
		PSBD.FIELD_NAME_AGG_TIPO+"','" +
		/// Rinaldo tiket 654 //////////////////////
		PSBD.FIELD_NAME_IMP_AGGIUDICATARIO+"','" +
		PSBD.FIELD_NAME_PERC_RIBASSO_AGGIUDICATARIO+"','" +
	 	PSBD.FIELD_NAME_PERC_AUMENTO_AGGIUDICATARIO +"'" ;
		/////////////////////////////////////////////
		//PSBD.FIELD_NAME_AGG_RUOLO+"','"+	s
		//PSBD.S_FIELD_NAME_AGG_FLAG_AVVALIMENTO+"','"+
		//PSBD.FIELD_NAME_AGG_CF_AUSILIARIA+"','"+
		//PSBD.FIELD_NAME_AGG_ID_GRUPPO+"'";
	
	public final static String argsAdesione = "'"+
			PSBD.FIELD_NAME_AGG_DENOMINAZIONE+"','"+		
			PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO+"','"+	
			PSBD.FIELD_NAME_AGG_ID_PAESE+"','"+
			PSBD.FIELD_NAME_IMP_AGGIUDICATARIO+"','" +
			PSBD.FIELD_NAME_PERC_RIBASSO_AGGIUDICATARIO+"','" +
		 	PSBD.FIELD_NAME_PERC_AUMENTO_AGGIUDICATARIO +"'" ;
	
	public final static String argsSubAggiud = "'"+
			PSBD.FIELD_NAME_AGG_DENOMINAZIONE+"','"+		
			PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO+"','"+	
			PSBD.FIELD_NAME_AGG_ID_PAESE+"','"+ 
			PSBD.FIELD_NAME_AGG_TIPO+"','" + 
			PSBD.FIELD_NAME_IMP_AGGIUDICATARIO+"'"; 
	
	public final static String argsAggiudNascosti = "'"+PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE+"','"+
	PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG+"','"+
	//PSBD.FIELD_NAME_AGG_ID_RUOLO+"','"+
	PSBD.FIELD_NAME_AGG_ID_TIPO_AGG+"','"+
	PSBD.FIELD_NAME_ANAGOE+"'";
	
	public final static String argsAdesioneNascosti = "'"+PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE+"','"+
			PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG+"','"+
			//PSBD.FIELD_NAME_AGG_ID_RUOLO+"','"+
			PSBD.FIELD_NAME_AGG_ID_TIPO_AGG+"','"+
			PSBD.FIELD_NAME_ANAGOE+"'";
	
	public final static String argsAusiliarie = "'"+
	PSBD.FIELD_NAME_AGG_DENOMINAZIONE+"','"+		
	PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO+"','"+	
	PSBD.FIELD_NAME_AGG_ID_PAESE+"','"+
	PSBD.S_FIELD_NAME_AGG_FLAG_AVVALIMENTO+"'";

	public final static String argsAusiliarieNascosti = "'"+
	PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE+"','"+
	PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG+"','"+
 	PSBD.FIELD_NAME_ANAGOE+"'";
	
	public final static String argsRaggruppamento = "'"+
	PSBD.FIELD_NAME_AGG_DENOMINAZIONE+"','"+		
	PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO+"','"+	
	PSBD.FIELD_NAME_AGG_ID_PAESE+"'";

	public final static String argsRaggruppamentoNascosti = "'"+
	PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE+"','"+
	PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG+"','"+
 	PSBD.FIELD_NAME_ANAGOE+"'";
	
	public final static String argsInvitati = "'"+
	PSBD.FIELD_NAME_AGG_DENOMINAZIONE+"','"+		
	PSBD.FIELD_NAME_CODICE_FISCALE_AFFIDATARIO+"','"+	
	PSBD.FIELD_NAME_AGG_ID_PAESE+"'";
	
	public final static String argsInvitatiNascosti = 
	"'"+PSBD.FIELD_NAME_AGG_ID_SOGGETTO_PARTECIPANTE+"','"+
	PSBD.FIELD_NAME_AGG_DATA_INIZIO_SOGG+"'";
	
	public final static String SEP_VARANAG = "|";
	public final static String SEP_VARANAG_S = "\\" + SEP_VARANAG;
	
	//TICKET ALM - 3.04.4
	public final static String LISTA_CPV_SECONDARIE1 = "listaCPVSecondarie1";
	public final static String LISTA_CPV_SECONDARIE2 = "listaCPVSecondarie2";
}
