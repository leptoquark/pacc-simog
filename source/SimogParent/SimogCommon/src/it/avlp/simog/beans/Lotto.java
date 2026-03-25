package it.avlp.simog.beans;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import it.avlp.simog.beans.aggiudicazione.TipoAppaltoAggBean;
import it.avlp.simog.beans.cup.CupLottoAggExt;
import it.avlp.simog.db.generated.LOTTO;
import it.avlp.simog.util.SimogProperties;

public class Lotto implements LOTTO {

	private boolean noCalcolo = false; // PP aggiunto per ricalcolo contributo
	private String cig = null;
	// old private String data_Presentazione_Offerte = null;
	private String data_Pubblicazione = null;
	private String id_Categoria_prevalente = null;
	private String id_Categoria_Scorporabile;
	private String id_CPV;
	private long id_Gara;
	private long id_Lotto;
	private String id_Scelta_Contraente;
	private String id_Tipologia;
	private BigDecimal importo_Impresa;
	private BigDecimal importo_Lotto;
	private BigDecimal importo_SA;
	private String oggetto;
	private String cig_kkk;
	private int cig_cicle;
	private char Somma_Urgenza;
	private String dataComunicazione;
	private String dataCancellazioneLotto;

	private String dataScadenzaPagamenti;
	private String dataInibizionePagamento;

	private String id_motivazione;
	private String noteCancellazione;

	private String dataCreazione;

	private String FLAG_ESCLUSO;
	private String TIPO_CONTRATTO_LOTTO;
	private int ID_ESCLUSIONE;

	private HashMap<String, String> categorieScorporabili;

	// gm nuovo codice 3.0
	private String triennio_anno_inizio;
	private String triennio_anno_fine;
	private String triennio_progressivo;
	private String annuale_cui_mininf;
	// gm fine nuovo codice 3.0

	// gm nuovo codice pubblicazione bando 3.0
	private String luogoIstat;
	private String luogoNuts;
	private BigDecimal importo_attuazione_sicurezza;
	// gm fine nuovo codice pubblicazione bando 3.0

	private String FLAG_PREVEDE_RIP;
	private String FLAG_RIPETIZIONE;
	private String CIG_ORIGINE_RIP;

	// PP is3025_RFWEBGL02Active
	private String ORA_SCADENZA;

	// UN is3030_RFWEBGL00Active
	private String dataScadenzaRichiestaInvito;
	private String dataLetteraInvito;

	// UN is3031_RFWEBGL02Active
	private String FLAG_CUP;
	private List<CupLottoAggExt> elencoCup;
	private List<TipoAppaltoAggBean> elencoTipoAppaltoLottoL;
	private List<TipoAppaltoAggBean> elencoTipoAppaltoLottoF;

	private List<MotivoDerogaBean> elencoMotivoDeroga;
	private List<MisuraPremialeBean> elencoMisurePremiali;
	
	//3.04.9 MEV 40610
	private String derogaQualificazioneSA;
	private String flagIsQualificataKO;
	//fine 3.04.9 MEV 40610
//	private String FLAG_PAR_GEN_MOD1;
//	private String FLAG_PAR_GEN_MOD2;
	
	//MEV 38205 3.04.8.1
	private String FLAG_USO_METODI_EDILIZIA;
	
	//MEV 37010 3.04.8.1
	private String FLAG_DEROGA_ADESIONE;
	
	private String FLAG_PNRR_PNC;
	private String FLAG_PREVISIONE_QUOTA;
	private String FLAG_MISURE_PREMIALI;
	public String getFLAG_MISURE_PREMIALI() {
		return FLAG_MISURE_PREMIALI;
	}

	public void setFLAG_MISURE_PREMIALI(String fLAG_MISURE_PREMIALI) {
		FLAG_MISURE_PREMIALI = fLAG_MISURE_PREMIALI;
	}

	private BigDecimal QUOTA_GIOVANILE;
	private BigDecimal QUOTA_FEMMINILE;
	

	// TICKET ALM #2845
	private String FLAG_DL50;
	private String PRIMA_ANNUALITA;
	// FINE TICKET ALM #2845

	// TICKET #2846
	private String ID_MOTIVO_COLL_CIG;

	private String ID_MOTIVO_DEROGA;

	// FINE TICKET #2846

	// TICKET ALM #3835
	private int ID_AFF_RISERVATI;
	private List<CondizioneLottoBean> condizioni;
	// FINE TICKET ALM #3835

	// TICKET ALM #3836
	private String FLAG_REGIME;
	private int ID_ART_REGIME;
	// FINE TICKET ALM #3836

	// TICKET ALM #4219 - 3.04.4
	private List<CpvLotto> elencoCpvSecondarie;
	private String codCatMerc;
	// FINE TICKET ALM #4219 - 3.04.4

	// TICKET ALM #4223-#4224 - 3.04.4
	private String flagNoAdesione;
	private String flagSANonClass;
	private String cigIniziativa;

	// TICKET ALM 13691 - 3.04.5
	private BigDecimal importo_opzioni;

	private int durataRipetizioni;
	private int durataAffidamentoGiorni;

	public Lotto() {
	}

// NEWCIG eliminato, mai richiamato
//	public Lotto(String cig, String data_Presentazione_Offerte, String data_Pubblicazione, String id_Categoria_Prevalente, 
//			String id_Categoria_Scorporabile, String id_CPV, long id_Gara, long id_Lotto, String id_Scelta_Contraente, 
//			String id_Tipologia, BigDecimal importo_Impresa, BigDecimal importo_Lotto, BigDecimal importo_SA, String oggetto, 
//			String cig_kkk, char somma_Urgenza, String dataComunicazione, String dataCancellazioneLotto, String dataScadenzaPagamenti, 
//			String dataInibizionePagamento) {		
//		super();
//		this.cig = cig;
//		this.data_Presentazione_Offerte = data_Presentazione_Offerte;
//		this.data_Pubblicazione = data_Pubblicazione;
//		this.id_Categoria_prevalente = id_Categoria_Prevalente;
//		this.id_Categoria_Scorporabile = id_Categoria_Scorporabile;
//		this.id_CPV = id_CPV;
//		this.id_Gara = id_Gara;
//		this.id_Lotto = id_Lotto;
//		this.id_Scelta_Contraente = id_Scelta_Contraente;
//		this.id_Tipologia = id_Tipologia;
//		this.importo_Impresa = importo_Impresa;
//		this.importo_Lotto = importo_Lotto;
//		this.importo_SA = importo_SA;
//		this.oggetto = oggetto;
//		this.cig_kkk = cig_kkk;
//		Somma_Urgenza = somma_Urgenza;
//		this.dataComunicazione = dataComunicazione;
//		this.dataCancellazioneLotto = dataCancellazioneLotto;
//		this.dataScadenzaPagamenti = dataScadenzaPagamenti;
//		this.dataInibizionePagamento = dataInibizionePagamento;
//	}

//	public String getData_Presentazione_Offerte() {
//		return data_Presentazione_Offerte;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.avlp.simog.db.generated.LOTTO#getData_Pubblicazione()
	 */
	public String getData_Pubblicazione() {
		return data_Pubblicazione;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.avlp.simog.db.generated.LOTTO#getId_Categoria_Scorporabile()
	 */
	public String getId_Categoria_Scorporabile() {
		return id_Categoria_Scorporabile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.avlp.simog.db.generated.LOTTO#getId_CPV()
	 */
	public String getId_CPV() {
		return id_CPV;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.avlp.simog.db.generated.LOTTO#getId_Gara()
	 */
	public long getId_Gara() {
		return id_Gara;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.avlp.simog.db.generated.LOTTO#getId_Lotto()
	 */
	public long getId_Lotto() {
		return id_Lotto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.avlp.simog.db.generated.LOTTO#getId_Scelta_Contraente()
	 */
	public String getId_Scelta_Contraente() {
		return id_Scelta_Contraente;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.avlp.simog.db.generated.LOTTO#getId_Tipologia()
	 */
	public String getId_Tipologia() {
		return id_Tipologia;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.avlp.simog.db.generated.LOTTO#getImporto_Impresa()
	 */
	public BigDecimal getImporto_Impresa() {
		return importo_Impresa;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.avlp.simog.db.generated.LOTTO#getImporto_Lotto()
	 */
	public BigDecimal getImporto_Lotto() {
		return importo_Lotto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.avlp.simog.db.generated.LOTTO#getImporto_SA()
	 */
	public BigDecimal getImporto_SA() {
		return importo_SA;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.avlp.simog.db.generated.LOTTO#getOggetto()
	 */
	public String getOggetto() {
		return oggetto;
	}

	/**
	 * @param id_Gara The id_Gara to set.
	 */
	public void setId_Gara(long id_Gara) {
		this.id_Gara = id_Gara;
	}

	/**
	 * @param cig The cig to set.
	 */
	public void setCig(String cig) {
		this.cig = cig;
	}

	/**
	 * @param data_Presentazione_Offerte The data_Presentazione_Offerte to set.
	 */
//	public void setData_Presentazione_Offerte(String data_Presentazione_Offerte) {
//		this.data_Presentazione_Offerte = data_Presentazione_Offerte;
//	}

	/**
	 * @param data_Pubblicazione The data_Pubblicazione to set.
	 */
	public void setData_Pubblicazione(String data_Pubblicazione) {
		this.data_Pubblicazione = data_Pubblicazione;
	}

	/**
	 * @param id_Categoria_Prevalente The id_Categoria_Prevalente to set.
	 */
	public void setId_Categoria_prevalente(String id_Categoria_Prevalente) {
		this.id_Categoria_prevalente = id_Categoria_Prevalente;
	}

	/**
	 * @param id_Categoria_Scorporabile The id_Categoria_Scorporabile to set.
	 */
	public void setId_Categoria_Scorporabile(String id_Categoria_Scorporabile) {
		this.id_Categoria_Scorporabile = id_Categoria_Scorporabile;
	}

	/**
	 * @param id_CPV The id_CPV to set.
	 */
	public void setId_CPV(String id_CPV) {
		this.id_CPV = id_CPV;
	}

	/**
	 * @param id_Lotto The id_Lotto to set.
	 */
	public void setId_Lotto(long id_Lotto) {
		this.id_Lotto = id_Lotto;
	}

	/**
	 * @param id_Scelta_Contraente The id_Scelta_Contraente to set.
	 */
	public void setId_Scelta_Contraente(String id_Scelta_Contraente) {
		this.id_Scelta_Contraente = id_Scelta_Contraente;
	}

	/**
	 * @param id_Tipologia The id_Tipologia to set.
	 */
	public void setId_Tipologia(String id_Tipologia) {
		this.id_Tipologia = id_Tipologia;
	}

	/**
	 * @param importo_Impresa The importo_Impresa to set.
	 */
	public void setImporto_Impresa(BigDecimal importo_Impresa) {
		this.importo_Impresa = importo_Impresa;
	}

	/**
	 * @param importo_Lotto The importo_Lotto to set.
	 */
	public void setImporto_Lotto(BigDecimal importo_Lotto) {
		this.importo_Lotto = importo_Lotto;
	}

	/**
	 * @param importo_SA The importo_SA to set.
	 */
	public void setImporto_SA(BigDecimal importo_SA) {
		this.importo_SA = importo_SA;
	}

	/**
	 * @param oggetto The oggetto to set.
	 */
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public int getCIG_cicle() {
		return cig_cicle;
	}

	/**
	 * @param cig_cicle The cig_cicle to set.
	 */
	public void setCig_cicle(int cig_cicle) {
		this.cig_cicle = cig_cicle;
	}

	/**
	 * @param cig_kkk The cig_kkk to set.
	 */
	public void setCig_kkk(String cig_kkk) {
		this.cig_kkk = cig_kkk;
	}

	public String getCIG_kkk() {
		return cig_kkk;
	}

	public String getFullCIG() {

		return CIGBean.getFullCIG(String.valueOf(getSomma_Urgenza()), getCIG(), getCIG_kkk(), getDataCreazione());
	}

	public char getSomma_Urgenza() {
		return Somma_Urgenza;
	}

	public String getCIG() {
		return cig;
	}

	public String getDATA_INIB_PAGAMENTO() {
		return dataInibizionePagamento;
	}

	public String getDATA_SCADENZA_PAGAMENTI() {
		return dataScadenzaPagamenti;
	}

	public void setSomma_Urgenza(char somma_Urgenza) {
		this.Somma_Urgenza = somma_Urgenza;
	}

	public String getData_Comunicazione() {
		return dataComunicazione;
	}

	public void setDATA_CANCELLAZIONE_LOTTO(String dataCancellazioneLotto) {
		this.dataCancellazioneLotto = dataCancellazioneLotto;
	}

	public String getDATA_CANCELLAZIONE_LOTTO() {
		return dataCancellazioneLotto;
	}

	public void setDataScadenzaPagamenti(String dataScadenzaPagamenti) {
		this.dataScadenzaPagamenti = dataScadenzaPagamenti;
	}

	public void setDataComunicazione(String dataComunicazione) {
		this.dataComunicazione = dataComunicazione;
	}

	public void setDataInibizionePagamento(String dataInibizionePagamento) {
		this.dataInibizionePagamento = dataInibizionePagamento;
	}

	public String getId_Categoria_prevalente() {
		return id_Categoria_prevalente;
	}

	public HashMap<String, String> getCategorieScorporabili() {
		return categorieScorporabili;
	}

	public void setCategorieScorporabili(HashMap<String, String> categorieScorporabili) {
		this.categorieScorporabili = categorieScorporabili;
	}

	public String getId_motivazione() {
		return id_motivazione;
	}

	public void setId_motivazione(String id_motivazione) {
		this.id_motivazione = id_motivazione;
	}

	public String getNoteCancellazione() {
		return noteCancellazione;
	}

	public void setNoteCancellazione(String noteCancellazione) {
		this.noteCancellazione = noteCancellazione;
	}

	public String getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getFLAG_ESCLUSO() {
		return FLAG_ESCLUSO;
	}

	public void setFLAG_ESCLUSO(String flag_escluso) {
		FLAG_ESCLUSO = flag_escluso;
	}

	public int getID_ESCLUSIONE() {
		return ID_ESCLUSIONE;
	}

	public void setID_ESCLUSIONE(int id_esclusione) {
		ID_ESCLUSIONE = id_esclusione;
	}

	public String getTIPO_CONTRATTO_LOTTO() {
		return TIPO_CONTRATTO_LOTTO;
	}

	public void setTIPO_CONTRATTO_LOTTO(String tipo_contratto_lotto) {
		TIPO_CONTRATTO_LOTTO = tipo_contratto_lotto;
	}

	// gm nuovo codice 3.0
	public String getANNUALE_CUI_MININF() {
		return annuale_cui_mininf;
	}

	public String getTRIENNIO_ANNO_FINE() {
		return triennio_anno_fine;
	}

	public String getTRIENNIO_ANNO_INIZIO() {
		return triennio_anno_inizio;
	}

	public String getTRIENNIO_PROGRESSIVO() {
		return triennio_progressivo;
	}

	public void setTriennio_anno_inizio(String triennioAnnoInizio) {
		triennio_anno_inizio = triennioAnnoInizio;
	}

	public void setTriennio_anno_fine(String triennioAnnoFine) {
		triennio_anno_fine = triennioAnnoFine;
	}

	public void setTriennio_progressivo(String triennioProgressivo) {
		triennio_progressivo = triennioProgressivo;
	}

	public void setAnnuale_cui_mininf(String annualeCuiMininf) {
		annuale_cui_mininf = annualeCuiMininf;
	}
	// gm fine nuovo codice 3.0

	// gm nuovo codice pubblicazione bando 3.0
	public void setLUOGO_ISTAT(String luogoIstat) {
		this.luogoIstat = luogoIstat;
	}

	public void setLUOGO_NUTS(String luogoNuts) {
		this.luogoNuts = luogoNuts;
	}

	public void setIMPORTO_ATTUAZIONE_SICUREZZA(BigDecimal importo_attuazione_sicurezza) {
		this.importo_attuazione_sicurezza = importo_attuazione_sicurezza;
	}

	public String getLUOGO_ISTAT() {
		return luogoIstat;
	}

	public String getLUOGO_NUTS() {
		return luogoNuts;
	}

	public BigDecimal getIMPORTO_ATTUAZIONE_SICUREZZA() {
		return importo_attuazione_sicurezza;
	}
	// gm fine nuovo codice pubblicazione bando 3.0

	public String getCIG_ORIGINE_RIP() {
		return CIG_ORIGINE_RIP;
	}

	public void setCIG_ORIGINE_RIP(String cig_origine_rip) {
		CIG_ORIGINE_RIP = cig_origine_rip;
	}

	public String getFLAG_PREVEDE_RIP() {
		return FLAG_PREVEDE_RIP;
	}

	public void setFLAG_PREVEDE_RIP(String flag_prevede_rip) {
		FLAG_PREVEDE_RIP = flag_prevede_rip;
	}

	public String getFLAG_RIPETIZIONE() {
		return FLAG_RIPETIZIONE;
	}

	public void setFLAG_RIPETIZIONE(String flag_ripetizione) {
		FLAG_RIPETIZIONE = flag_ripetizione;
	}

	public boolean isNoCalcolo() {
		return noCalcolo;
	}

	public void setNoCalcolo(boolean noCalcolo) {
		this.noCalcolo = noCalcolo;
	}

	public void setORA_SCADENZA(String oraScadenza) {
		this.ORA_SCADENZA = oraScadenza;
	}

	public String getORA_SCADENZA() {
		return this.ORA_SCADENZA;
	}

	// UN nuove date per procedura ristretta
	public String getDataScadenzaRichiestaInvito() {
		return dataScadenzaRichiestaInvito;
	}

	public void setDataScadenzaRichiestaInvito(String dataScadenzaRichiestaInvito) {
		this.dataScadenzaRichiestaInvito = dataScadenzaRichiestaInvito;
	}

	public String getDataLetteraInvito() {
		return dataLetteraInvito;
	}

	public void setDataLetteraInvito(String dataLetteraInvito) {
		this.dataLetteraInvito = dataLetteraInvito;
	}

	public String getFLAG_CUP() {
		return FLAG_CUP;
	}

	public void setFLAG_CUP(String fLAG_CUP) {
		FLAG_CUP = fLAG_CUP;
	}

	// TICKET ALM #31047
//	public String getFLAGPARGENMOD1() {
//		return FLAG_PAR_GEN_MOD1;
//	}
//
//	public void setFlagParGenMod1(String flag_ParGenMod1) {
//		FLAG_PAR_GEN_MOD1 = flag_ParGenMod1;
//	}
//
//	public String getFLAGPARGENMOD2() {
//		return FLAG_PAR_GEN_MOD2;
//	}
//
//	public void setFlagParGenMod2(String flag_ParGenMod2) {
//		FLAG_PAR_GEN_MOD2 = flag_ParGenMod2;
//	}
	
	

	public String getFLAG_PNRR_PNC() {
		return FLAG_PNRR_PNC;
	}

	public void setFLAG_PNRR_PNC(String fLAG_PNRR_PNC) {
		FLAG_PNRR_PNC = fLAG_PNRR_PNC;
	}

	public String getFLAG_PREVISIONE_QUOTA() {
		return FLAG_PREVISIONE_QUOTA;
	}

	public void setFLAG_PREVISIONE_QUOTA(String fLAG_PREVISIONE_QUOTA) {
		FLAG_PREVISIONE_QUOTA = fLAG_PREVISIONE_QUOTA;
	}
	

	public BigDecimal getQuotaGiovanile() {
		return QUOTA_GIOVANILE;
	}

	public BigDecimal getQuotaFemminile() {
		return QUOTA_FEMMINILE;
	}

	public void setQuotaFemminile(BigDecimal quotaFemminile) {
		QUOTA_FEMMINILE = quotaFemminile;
	}

	public void setQuotaGiovanile(BigDecimal quotaGiovanile) {
		QUOTA_GIOVANILE = quotaGiovanile;
	}

	public List<CupLottoAggExt> getElencoCup() {
		return elencoCup;
	}

	public void setElencoCup(List<CupLottoAggExt> elencoCup) {
		this.elencoCup = elencoCup;
	}

	public List<TipoAppaltoAggBean> getElencoTipoAppaltoLottoL() {
		return elencoTipoAppaltoLottoL;
	}

	public void setElencoTipoAppaltoLottoL(List<TipoAppaltoAggBean> elencoTipoAppaltoLottoL) {
		this.elencoTipoAppaltoLottoL = elencoTipoAppaltoLottoL;
	}

	public List<TipoAppaltoAggBean> getElencoTipoAppaltoLottoF() {
		return elencoTipoAppaltoLottoF;
	}

	public void setElencoTipoAppaltoLottoF(List<TipoAppaltoAggBean> elencoTipoAppaltoLottoF) {
		this.elencoTipoAppaltoLottoF = elencoTipoAppaltoLottoF;
	}

	public List<MotivoDerogaBean> getElencoMotivoDeroga() {
		return elencoMotivoDeroga;
	}

	public void setElencoMotivoDeroga(List<MotivoDerogaBean> elencoMotivoDeroga) {
		this.elencoMotivoDeroga = elencoMotivoDeroga;
	}
	
	

	// TICKET ALM #2845
	public String getFLAG_DL50() {
		return FLAG_DL50;
	}

	public void setFLAG_DL50(String fLAG_DL50) {
		FLAG_DL50 = fLAG_DL50;
	}

	public String getPRIMA_ANNUALITA() {
		return PRIMA_ANNUALITA;
	}

	public void setPRIMA_ANNUALITA(String primaAnnualita) {
		PRIMA_ANNUALITA = primaAnnualita;
	}
	// FINE TICKET ALM #2845

	public String getID_MOTIVO_DEROGA() {
		return ID_MOTIVO_DEROGA;
	}

	// TICKET #2846
	public String getID_MOTIVO_COLL_CIG() {
		return ID_MOTIVO_COLL_CIG;
	}

	public void setID_MOTIVO_COLL_CIG(String idMotivo) {
		ID_MOTIVO_COLL_CIG = idMotivo;
	}
	// FINE TICKET #2846

	// TICKET ALM #3835
	public int getID_AFF_RISERVATI() {
		return ID_AFF_RISERVATI;
	}

	public void setID_AFF_RISERVATI(int idAffRiservati) {
		ID_AFF_RISERVATI = idAffRiservati;
	}

	public List<CondizioneLottoBean> getCondizioni() {
		return condizioni;
	}

	public void setCondizioni(List<CondizioneLottoBean> condizioni) {
		this.condizioni = condizioni;
	}
	// FINE TICKET ALM #3835

	// TICKET ALM #3836
	public String getFLAG_REGIME() {
		return FLAG_REGIME;
	}

	public void set_FLAG_REGIME(String flagRegime) {
		FLAG_REGIME = flagRegime;
	}

	public int getID_ART_REGIME() {
		return ID_ART_REGIME;
	}

	public void setID_ART_REGIME(int idArtRegime) {
		ID_ART_REGIME = idArtRegime;
	}
	// FINE TICKET ALM #3836

	/*
	 * restituisce le due liste di tipi appalti unite, non usare per modificare il
	 * bean perchè restituisce un nuovo oggetto!!!
	 */
	public List<TipoAppaltoAggBean> getElencoTipoAppaltoLotto() {
		List<TipoAppaltoAggBean> merged = new LinkedList<TipoAppaltoAggBean>();
		if (elencoTipoAppaltoLottoL != null) {
			merged.addAll(elencoTipoAppaltoLottoL);
		}

		if (elencoTipoAppaltoLottoF != null) {
			merged.addAll(elencoTipoAppaltoLottoF);
		}

		return merged;
	}

	// TICKET ALM - 3.04.4
	public List<CpvLotto> getElencoCpvSecondarie() {
		return elencoCpvSecondarie;
	}

	public void setElencoCpvSecondarie(List<CpvLotto> elencoCpvSecondarie) {
		this.elencoCpvSecondarie = elencoCpvSecondarie;
	}

	public String getCOD_CATEGORIA() {
		return codCatMerc;
	}

	public void setCOD_CATEGORIA(String codCatMerc) {
		this.codCatMerc = codCatMerc;
	}

	// FINE TICKET ALM - 3.04.4

	// TICKET ALM #4223-#4224 - 3.04.4
	public String getFlagNoAdesione() {
		return flagNoAdesione;
	}

	public void setFlagNoAdesione(String flagNoAdesione) {
		this.flagNoAdesione = flagNoAdesione;
	}

	public String getFlagSANonClass() {
		return flagSANonClass;
	}

	public void setFlagSANonClass(String flagSANonClass) {
		this.flagSANonClass = flagSANonClass;
	}

	public String getCigIniziativa() {
		return cigIniziativa;
	}

	public void setCigIniziativa(String cigIniziativa) {
		this.cigIniziativa = cigIniziativa;
	}
	// FINE TICKET ALM #4223-#4224 - 3.04.4

	public BigDecimal getImporto_opzioni() {
		return importo_opzioni;
	}

	public void setImporto_opzioni(BigDecimal importo_opzioni) {
		this.importo_opzioni = importo_opzioni;
	}

	public int getDurataRipetizioni() {
		return durataRipetizioni;
	}

	public void setDurataRipetizioni(int durataRipetizioni) {
		this.durataRipetizioni = durataRipetizioni;
	}

	public int getDurataAffidamentoGiorni() {
		return durataAffidamentoGiorni;
	}

	public void setDurataAffidamentoGiorni(int durataAffidamentoGiorni) {
		this.durataAffidamentoGiorni = durataAffidamentoGiorni;
	}

	public boolean isContrattoLavoro() {
		return this.getTIPO_CONTRATTO_LOTTO().equals("L");
	}

	public boolean isAppaltoPubblico() {
		boolean isAppaltoPubblico = false;
		for (String idAppaltoPubblico : SimogProperties.getInstance().getIdAppaltiPubblici().split(";")) {
			isAppaltoPubblico = Integer.parseInt(idAppaltoPubblico) == this.getID_ART_REGIME();
			break;
		}

		return isAppaltoPubblico;
	}

	// TODO: DA VEDERE COME RECUPERA
	public boolean isValidDipe() {
		return true;
	}

	// TODO: DA VEDERE COME RECUPERA
	public boolean isTematicaPNRR() {
		return true;
	}

	public List<MisuraPremialeBean> getElencoMisurePremiali() {
		return elencoMisurePremiali;
	}

	public void setElencoMisurePremiali(List<MisuraPremialeBean> elencoMisurePremiali) {
		this.elencoMisurePremiali = elencoMisurePremiali;
	}
	
	//MEV 38205 3.04.8.1
	public String getFLAG_USO_METODI_EDILIZIA() {
		return FLAG_USO_METODI_EDILIZIA;
	}

	public void setFLAG_USO_METODI_EDILIZIA(String fLAG_USO_METODI_EDILIZIA) {
		FLAG_USO_METODI_EDILIZIA = fLAG_USO_METODI_EDILIZIA;
	}
	
	//MEV 37010 3.04.8.1
	public String getFLAG_DEROGA_ADESIONE() {
		return FLAG_DEROGA_ADESIONE;
	}

	public void setFLAG_DEROGA_ADESIONE(String fLAG_DEROGA_ADESIONE) {
		FLAG_DEROGA_ADESIONE = fLAG_DEROGA_ADESIONE;
	}

	public String getDerogaQualificazioneSA() {
		return derogaQualificazioneSA;
	}

	public void setDerogaQualificazioneSA(String derogaQualificazioneSA) {
		this.derogaQualificazioneSA = derogaQualificazioneSA;
	}

	public String getFlagIsQualificataKO() {
		return flagIsQualificataKO;
	}

	public void setFlagIsQualificataKO(String flagIsQualificataKO) {
		this.flagIsQualificataKO = flagIsQualificataKO;
	}


	
	
}
