package it.avlp.simog.beans;

import it.avlp.simog.common.servlet.PSBD;

import java.io.Serializable;
import java.sql.Timestamp;

public class InfoComuniBean implements Serializable{
	private long idStato;
	private String descrizioneStato;
	private String cig;
	private int cigCycle;
	private long idLotto;
	
	private long idInfo;
	private Timestamp dataInizioInfo;
	private String idCategSa;
	private String cfAmmAgente;
	private String denAmmAgente;
	private String flagEnteSpeciale;
	private String tipoContratto;
	private String cfAmministrazione;
	private String denAmministrazione;
	private String cfStazioneAppaltante;
	private String denStazioneAppaltante;
	private String cfRup;
	private String provvPresaCarico;
	private String codiceCC;
	private String denomCC;
	private String flagSAAgente;
	private long tipologiaSA;
	private PubblicazioneBean pubblicazione;
	private boolean hasAwards;
	private String esitoProcedura;
	private boolean aggiudicata;
	private boolean onlyAwards;
	private boolean okCancellazione ;
	
	
	private int ID_MODO_REAL;
	private String FLAG_ESCLUSO;
	private int ID_ESCLUSIONE;

	//gm nuovi campi dati comuni
	private long tipologiaProcedura;
	private int durataConvenzione;
	private String flagProcedeStipula;
	
	//UN 09/04/2013
	private int origine = OrigineSchedaEnum.WEB.code();
	
	public boolean isRichDelete(){
		if(descrizioneStato != null)
			return (descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTA_CANCELLAZIONE)
					|| descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTA_CANC_TOTALE));
		else return false;
	}

	private String idLocale;
	
	public String getIdLocale() {
		return idLocale;
	}

	public void setIdLocale(String idLocale) {
		this.idLocale = idLocale;
	}

	public boolean isRichAnn(){
		if(descrizioneStato != null)
			return (descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTO_ANNULLAMENTO));
		else return false;
	}
	public boolean isNuova(){
		return idInfo < 1;
	}
	public boolean isConfirmed(){
		
		return idStato == StatiScheda.CONFERMATO;
		
	}
	
	public boolean isOkCancellazione() {
		return okCancellazione;
	}
	public void setOkCancellazione(boolean okCancellazione) {
		this.okCancellazione = okCancellazione;
	}
	public PubblicazioneBean getPubblicazione() {
		return pubblicazione;
	}
	public void setPubblicazione(PubblicazioneBean pubblicazione) {
		this.pubblicazione = pubblicazione;
	}
	public String getFlagSAAgente() {
		return flagSAAgente;
	}
	public void setFlagSAAgente(String flagSAAgente) {
		this.flagSAAgente = flagSAAgente;
	}
	public long getTipologiaSA() {
		return tipologiaSA;
	}
	public void setTipologiaSA(long tipologiaSA) {
		this.tipologiaSA = tipologiaSA;
	}
	public String getCig() {
		return cig;
	}
	public void setCig(String cig) {
		this.cig = cig;
	}
	public int getCigCycle() {
		return cigCycle;
	}
	public void setCigCycle(int cigCycle) {
		this.cigCycle = cigCycle;
	}
	public long getIdLotto() {
		return idLotto;
	}
	public void setIdLotto(long idLotto) {
		this.idLotto = idLotto;
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
	public String getIdCategSa() {
		return idCategSa;
	}
	public void setIdCategSa(String idCategSa) {
		this.idCategSa = idCategSa;
	}
	public String getCfAmmAgente() {
		return cfAmmAgente;
	}
	public void setCfAmmAgente(String cfAmmAgente) {
		this.cfAmmAgente = cfAmmAgente;
	}
	public String getDenAmmAgente() {
		return denAmmAgente;
	}
	public void setDenAmmAgente(String denAmmAgente) {
		this.denAmmAgente = denAmmAgente;
	}
	public String getFlagEnteSpeciale() {
		return flagEnteSpeciale;
	}
	public void setFlagEnteSpeciale(String flagEnteSpeciale) {
		this.flagEnteSpeciale = flagEnteSpeciale;
	}
	public String getTipoContratto() {
		return tipoContratto;
	}
	public void setTipoContratto(String tipoContratto) {
		this.tipoContratto = tipoContratto;
	}
	public String getCfAmministrazione() {
		return cfAmministrazione;
	}
	public void setCfAmministrazione(String cfAmministrazione) {
		this.cfAmministrazione = cfAmministrazione;
	}
	public String getDenAmministrazione() {
		return denAmministrazione;
	}
	public void setDenAmministrazione(String denAmministrazione) {
		this.denAmministrazione = denAmministrazione;
	}
	public String getCfStazioneAppaltante() {
		return cfStazioneAppaltante;
	}
	public void setCfStazioneAppaltante(String cfStazioneAppaltante) {
		this.cfStazioneAppaltante = cfStazioneAppaltante;
	}
	public String getDenStazioneAppaltante() {
		return denStazioneAppaltante;
	}
	public void setDenStazioneAppaltante(String denStazioneAppaltante) {
		this.denStazioneAppaltante = denStazioneAppaltante;
	}
	public String getCfRup() {
		return cfRup;
	}
	public void setCfRup(String cfRup) {
		this.cfRup = cfRup == null ? cfRup : cfRup.toUpperCase();
	}
	public String getProvvPresaCarico() {
		return provvPresaCarico;
	}
	public void setProvvPresaCarico(String provvPresaCarico) {
		this.provvPresaCarico = provvPresaCarico;
	}
	public String getCodiceCC() {
		return codiceCC;
	}
	public void setCodiceCC(String codiceCC) {
		this.codiceCC = codiceCC;
	}
	public String getDenomCC() {
		return denomCC;
	}
	public void setDenomCC(String denomCC) {
		this.denomCC = denomCC;
	}
	public long getIdStato() {
		return idStato;
	}
	public void setIdStato(long idStato) {
		this.idStato = idStato;
	}
	public String getDescrizioneStato() {
		return descrizioneStato;
	}
	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	public boolean isHasAwards() {
		return hasAwards;
	}
	public void setHasAwards(boolean hasAwards) {
		this.hasAwards = hasAwards;
	}
	public String getEsitoProcedura() {
		return esitoProcedura;
	}
	public void setEsitoProcedura(String esitoProcedura) {
		this.esitoProcedura = esitoProcedura;
	}
	public boolean isAggiudicata() {
		return aggiudicata;
	}
	public void setAggiudicata(boolean aggiudicata) {
		this.aggiudicata = aggiudicata;
	}
	public boolean isOnlyAwards() {
		return onlyAwards;
	}
	public void setOnlyAwards(boolean onlyAwards) {
		this.onlyAwards = onlyAwards;
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

	public int getID_MODO_REAL() {
		return ID_MODO_REAL;
	}

	public void setID_MODO_REAL(int id_modo_real) {
		ID_MODO_REAL = id_modo_real;
	}
	//gm nuovi campi dati comuni
	public long getTipologiaProcedura() {
		return tipologiaProcedura;
	}
	public void setTipologiaProcedura(long tipologiaProcedura) {
		this.tipologiaProcedura = tipologiaProcedura;
	}
	public int getDurataConvenzione(){
		return durataConvenzione;
	}
	public void setDurataConvenzione(int durataConvenzione){
		this.durataConvenzione = durataConvenzione;
	}
	public String getFlagProcedeStipula(){
		return flagProcedeStipula;
	}
	public void setFlagProcedeStipula(String flagProcedeStipula){
		this.flagProcedeStipula = flagProcedeStipula;
	}

   public int getOrigine() {
      return origine;
   }
   public void setOrigine(int origine) {
      this.origine = origine;
   }
   public boolean isFromAVCPass(){
      return origine == OrigineSchedaEnum.AVCPASS.code();
   }
   
   
}
