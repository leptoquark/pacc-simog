package it.avlp.simog.beans;

import it.avlp.simog.util.PageHelper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class InfoGaraBean implements  Serializable{
	
	private String idOsservatorio;
	public String getIdOsservatorio() {
		return idOsservatorio;
	}
	public void setIdOsservatorio(String idOsservatorio) {
		this.idOsservatorio = idOsservatorio;
	}

	private long idInfo;
	private Timestamp dataInizioInfo;
	private long idLotto;
	private BigDecimal importoLotto;
	private long idSceltaContraente;
	private long idAggiudicazione;
	private Timestamp dataInizioAggiudicazione;
	private String tipoContratto;
	private String tipoEnte;
	private String cig;
	private String cigKKK;
	private int cigCicle;
	private String idCPV;
	private String descrizioneCPV;
	private String dataCancelazioneLotto;
	private String dataInibPagamento;
	private String oggettoLotto;
	private String cfAmministrazione;
	private String denomAmministrazione;
	private String cfSA;
	private String denomSA;
	private String cfRup;
	private String sommaUrgenza;
	private String dataScadenzaPagamenti;
	private String dataScadenzaInvito; //MEV 34183 3.04.8
	private String dataInvito; //MEV 34183 3.04.8
	private String dataCreazione;
	private String TIPO_SCHEDA_GARA;
	private int ID_MODO_REAL;
	private String FLAG_ESCLUSO;
	private int ID_ESCLUSIONE;
	
	//is3025_RFWEBGL02Active
	private String oraScadenza;
	
	//gm aggiunto per adesione accordo quadro
	private String CIG_ACC_QUADRO;
	
	//gm nuovo codice pubblicazione bando 3.0
	private long idPubblicazione;
	private Timestamp dataInizioPubblicazione;
	private String luogoIstat;
	private String luogoNuts;
	private BigDecimal importo_attuazione_sicurezza;
	
	private long idGara;
	private String dataPubblicazione;

	private boolean isDelegata;
	
   private String dataCreazioneGara;

	public long getIdPubblicazione() {
		return idPubblicazione;
	}
	public void setIdPubblicazione(long idPubblicazione) {
		this.idPubblicazione = idPubblicazione;
	}
	public Timestamp getDataInizioPubblicazione() {
		return dataInizioPubblicazione;
	}
	public void setDataInizioPubblicazione(Timestamp dataInizioPubblicazione) {
		this.dataInizioPubblicazione = dataInizioPubblicazione;
	}
	public void setLUOGO_ISTAT(String luogoIstat) {
		this.luogoIstat = luogoIstat;
	}

	public void setLUOGO_NUTS(String luogoNuts) {
		this.luogoNuts = luogoNuts;
	}
	
	public void setIMPORTO_ATTUAZIONE_SICUREZZA (BigDecimal importo_attuazione_sicurezza){
		this.importo_attuazione_sicurezza = importo_attuazione_sicurezza;
	}

	public String getLUOGO_ISTAT() {
		return luogoIstat;
	}

	public String getLUOGO_NUTS() {
		return luogoNuts;
	}

	public BigDecimal getIMPORTO_ATTUAZIONE_SICUREZZA(){
		return importo_attuazione_sicurezza;
	}

	//gm fine nuovo codice pubblicazione bando 3.0

	
	public boolean isDeleted(){
		if(dataCancelazioneLotto == null && dataInibPagamento == null )
		  return false;
		else return true;
		  
		
	}
	
	public long getIdInfo() {
		return idInfo;
	}
	public void setIdInfo(long idInfo) {
		this.idInfo = idInfo;
	}
	public Timestamp getDataInizioInfo() {
		return dataInizioInfo;
	}
	public void setDataInizioInfo(Timestamp dataInizioInfo) {
		this.dataInizioInfo = dataInizioInfo;
	}
	public long getIdLotto() {
		return idLotto;
	}
	public void setIdLotto(long idLotto) {
		this.idLotto = idLotto;
	}
	public long getIdAggiudicazione() {
		return idAggiudicazione;
	}
	public void setIdAggiudicazione(long idAggiudicazione) {
		this.idAggiudicazione = idAggiudicazione;
	}
	public Timestamp getDataInizioAggiudicazione() {
		return dataInizioAggiudicazione;
	}
	public void setDataInizioAggiudicazione(Timestamp dataInizioAggiudicazione) {
		this.dataInizioAggiudicazione = dataInizioAggiudicazione;
	}
	public String getTipoContratto() {
		return tipoContratto;
	}
	public void setTipoContratto(String tipoContratto) {
		this.tipoContratto = tipoContratto;
	}
	public String getTipoEnte() {
		return tipoEnte;
	}
	public void setTipoEnte(String tipoEnte) {
		this.tipoEnte = tipoEnte;
	}
	public String getCig() {
		return cig;
	}
	public void setCig(String cig) {
		this.cig = cig;
	}
	public String getCigKKK() {
		return cigKKK;
	}
	public void setCigKKK(String cigKKK) {
		this.cigKKK = cigKKK;
	}
	public int getCigCicle() {
		return cigCicle;
	}
	public void setCigCicle(int cigCicle) {
		this.cigCicle = cigCicle;
	}
	public String getIdCPV() {
		return idCPV;
	}
	public void setIdCPV(String idCPV) {
		this.idCPV = idCPV;
	}
	public String getDataCancelazioneLotto() {
		return dataCancelazioneLotto;
	}
	public void setDataCancelazioneLotto(String dataCancelazioneLotto) {
		this.dataCancelazioneLotto = dataCancelazioneLotto;
	}
	public String getDataInibPagamento() {
		return dataInibPagamento;
	}
	public void setDataInibPagamento(String dataInibPagamento) {
		this.dataInibPagamento = dataInibPagamento;
	}
	public String getOggettoLotto() {
		return oggettoLotto;
	}
	public void setOggettoLotto(String oggettoLotto) {
		this.oggettoLotto = oggettoLotto;
	}

	public BigDecimal getImportoLotto() {
		return importoLotto;
	}
	public String getImportoLottoStr() {
		 //return PageHelper.formattaImporto(importoLotto);
		
		String impLotto = PageHelper.IMPORTO_ND;	
		String unformattedImporto = importoLotto.toString();
		if(importoLotto.compareTo(new BigDecimal(-1)) != 0)
			impLotto = PageHelper.getFormattedImporto(unformattedImporto);
		return impLotto;
	}
	
	public void setImportoLotto(BigDecimal importoLotto) {
		this.importoLotto = importoLotto;
	}

	public long getIdSceltaContraente() {
		return idSceltaContraente;
	}

	public void setIdSceltaContraente(long idSceltaContraente) {
		this.idSceltaContraente = idSceltaContraente;
	}

	public String getCfAmministrazione() {
		return cfAmministrazione;
	}

	public void setCfAmministrazione(String cfAmministrazione) {
		this.cfAmministrazione = cfAmministrazione;
	}

	public String getDenomAmministrazione() {
		return denomAmministrazione;
	}

	public void setDenomAmministrazione(String denomAmministrazione) {
		this.denomAmministrazione = denomAmministrazione;
	}

	public String getCfSA() {
		return cfSA;
	}

	public void setCfSA(String cfSA) {
		this.cfSA = cfSA;
	}

	public String getDenomSA() {
		return denomSA;
	}

	public void setDenomSA(String denomSA) {
		this.denomSA = denomSA;
	}

	public String getDescrizioneCPV() {
		return descrizioneCPV;
	}

	public void setDescrizioneCPV(String descrizioneCPV) {
		this.descrizioneCPV = descrizioneCPV;
	}

	public String getCfRup() {
		return cfRup;
	}

	public void setCfRup(String cfRup) {
		this.cfRup = cfRup;
	}

	public String getSommaUrgenza() {
		return sommaUrgenza;
	}

	public void setSommaUrgenza(String sommaUrgenza) {
		this.sommaUrgenza = sommaUrgenza;
	}

	public String getDataScadenzaPagamenti() {
		return dataScadenzaPagamenti;
	}

	public void setDataScadenzaPagamenti(String dataScadenzaPagamenti) {
		this.dataScadenzaPagamenti = dataScadenzaPagamenti;
	}

	public String getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getFullCIG() {
		return CIGBean.getFullCIG(getSommaUrgenza(), getCig(), getCigKKK(), getDataCreazione()); 
	}

	public int getID_MODO_REAL() {
		return ID_MODO_REAL;
	}

	public void setID_MODO_REAL(int id_modo_real) {
		ID_MODO_REAL = id_modo_real;
	}

	public String getTIPO_SCHEDA_GARA() {
		return TIPO_SCHEDA_GARA;
	}

	public void setTIPO_SCHEDA_GARA(String tipo_scheda_gara) {
		TIPO_SCHEDA_GARA = tipo_scheda_gara;
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
	
	//gm aggiunto per adesione accordo quadro
	public String getCIG_ACC_QUADRO() {
		return CIG_ACC_QUADRO;
	}
	public void setCIG_ACC_QUADRO(String CIG_ACC_QUADRO) {
		this.CIG_ACC_QUADRO = CIG_ACC_QUADRO;
	}
	public long getIdGara() {
		return idGara;
	}
	public void setIdGara(long idGara) {
		this.idGara = idGara;
	}
	public String getDataPubblicazione() {
		return dataPubblicazione;
	}
	public void setDataPubblicazione(String dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}
   public String getOraScadenza() {
      return oraScadenza;
   }
   public void setOraScadenza(String oraScadenza) {
      this.oraScadenza = oraScadenza;
   }
   public String getDataCreazioneGara() {
      return dataCreazioneGara;
   }
   public void setDataCreazioneGara(String dataCreazioneGara) {
      this.dataCreazioneGara = dataCreazioneGara;
   }
	public boolean isDelegata() {
		return isDelegata;
	}
	public void setDelegata(boolean isDelegata) {
		this.isDelegata = isDelegata;
	}
   
	//MEV 34183 3.04.8.1
	public String getDataInvito() {
		return dataInvito;
	}
	public void setDataInvito(String dataInvito) {
		this.dataInvito = dataInvito;
	}
	public String getDataScadenzaInvito() {
		return dataScadenzaInvito;
	}
	public void setDataScadenzaInvito(String dataScadenzaInvito) {
		this.dataScadenzaInvito = dataScadenzaInvito;
	}
	//MEV 34183 3.04.8.1
}
