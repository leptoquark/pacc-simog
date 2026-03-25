package it.avlp.simog.beans;

import java.sql.Timestamp;

public class PubblicazioneBean implements VO , java.io.Serializable{
	
		
	public enum TipoOperazione {
		BANDO("1", "Bando di Gara")
		, AVVISOAGG("2", "Avviso di Aggiudicazione")
		, LETTINV("3", "Lettera di Invito")
		, ELENCOINV("4", "Elenco Invitati")
		, RETTIFICA("5", "Rettifica")
		, RETTIFICA_DATE("6", "Rettifica date perfezionamento")
		;
		
		private String codice;
		private String descr;
		
		
		
		TipoOperazione(String codice, String descr){
			this.codice = codice;
			this.descr = descr;
		}
		public String getCodice() {
			return codice;
		}
		public String getDescr() {
			return descr;
		}
	    public static TipoOperazione getEnumByTipo(String tipo) 
	    {
	    	TipoOperazione lista [] = values();
	    	for(int i=0; i<lista.length;i++) {
	    		if (lista[i].getCodice().equals(tipo))
	    			return lista[i];
	    	}
	    	return null;
	    }
	};
	
	public enum TipoDocumento {
		BANDO("1", "Bando di Gara")
		, DISCIPLINARE("2", "Disciplinare")
		, AVVISO("3", "Avviso di aggiudicazione")
		, RETTIFICA("4", "Rettifica")
		, LETTERA_INVITO("5", "Lettera di Invito")
		, RETTIFICAAVVISO("6", "Rettifica Avviso")
		;
		
		private String codice;
		private String descr;
		
		TipoDocumento(String codice, String descr){
			this.codice = codice;
			this.descr = descr;
		}
		public String getCodice() {
			return codice;
		}
		public String getDescr() {
			return descr;
		}
	    public static TipoDocumento getEnumByTipo(String tipo) 
	    {
	    	TipoDocumento lista [] = values();
	    	for(int i=0; i<lista.length;i++) {
	    		if (lista[i].getCodice().equals(tipo))
	    			return lista[i];
	    	}
	    	return null;
	    }
	    
	};

	private String dataAlbo;
	private String dataGuce;
	private String dataGuri;
	
	private Integer quotidianiNaz;
	private Integer quotidianiReg;
	
	private String profiloCommitente;
	private String sitoMinisteroInfTrasp;
	private String sitoOsservatorioCP;
	private long idPubblicazione;
	private Timestamp dataInizioPubblicazione;
	
	private String dataBore;
	private Integer periodici;
	
	private String idLocale;
	
	//gm nuovo codice pubblicazione bando 3.0
	private String numeroGuri;
	private String numeroGuce;
	private String numeroBore;
	private String linkSitoCommittente;
	//gm fine nuovo codice pubblicazione bando 3.0
	
	//gm nuovo codice estensione pubblicazione bandi
	private String flag_benicult;
	private String flag_sospeso;
	
	private String tipoOperazione;
	
	private String link_affidamento_diretto;
	
	//3.04.10 MEV 43345
	private String derogaQualificazioneSA;
	private String flagIsQualificataKO;
	//fine 3.04.9 MEV 43345
	
	public String getIdLocale() {
		return idLocale;
	}

	public void setIdLocale(String idLocale) {
		this.idLocale = idLocale;
	}

	public String getDataAlbo() {
		return dataAlbo;
	}
	public void setDataAlbo(String dataAlbo) {
		this.dataAlbo = dataAlbo;
	}
	public String getDataGuce() {
		return dataGuce;
	}
	public void setDataGuce(String dataGuce) {
		this.dataGuce = dataGuce;
	}
	public String getDataGuri() {
		return dataGuri;
	}
	public void setDataGuri(String dataGuri) {
		this.dataGuri = dataGuri;
	}
	public Integer getQuotidianiNaz() {
		return quotidianiNaz;
	}
	public void setQuotidianiNaz(Integer quotidianiNaz) {
		this.quotidianiNaz = quotidianiNaz;
	}
	public Integer getQuotidianiReg() {
		return quotidianiReg;
	}
	public void setQuotidianiReg(Integer quotidianiReg) {
		this.quotidianiReg = quotidianiReg;
	}
	public String getProfiloCommitente() {
		return profiloCommitente;
	}
	public void setProfiloCommitente(String profiloCommitente) {
		this.profiloCommitente = profiloCommitente;
	}
	public String getSitoMinisteroInfTrasp() {
		return sitoMinisteroInfTrasp;
	}
	public void setSitoMinisteroInfTrasp(String sitoMinisteroInfTrasp) {
		this.sitoMinisteroInfTrasp = sitoMinisteroInfTrasp;
	}
	public String getSitoOsservatorioCP() {
		return sitoOsservatorioCP;
	}
	public void setSitoOsservatorioCP(String sitoOsservatorioCP) {
		this.sitoOsservatorioCP = sitoOsservatorioCP;
	}
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
	public String getDataBore() {
		return dataBore;
	}
	public void setDataBore(String dataBore) {
		this.dataBore = dataBore;
	}
	public Integer getPeriodici() {
		return periodici;
	}
	public void setPeriodici(Integer periodici) {
		this.periodici = periodici;
	}
	//gm nuovo codice pubblicazione bando 3.0
	public String getNumeroGuri() {
		return numeroGuri;
	}
	public void setNumeroGuri(String numeroGuri) {
		this.numeroGuri = numeroGuri;
	}
	public String getNumeroGuce() {
		return numeroGuce;
	}
	public void setNumeroGuce(String numeroGuce) {
		this.numeroGuce = numeroGuce;
	}
	public String getNumeroBore() {
		return numeroBore;
	}
	public void setNumeroBore(String numeroBore) {
		this.numeroBore = numeroBore;
	}
	public String getLinkSitoCommittente() {
		return linkSitoCommittente;
	}
	public void setLinkSitoCommittente(String linkSitoCommittente) {
		this.linkSitoCommittente = linkSitoCommittente;
	}
	//gm fine nuovo codice pubblicazione bando 3.0

	public String getTipoOperazione() {
		return tipoOperazione;
	}

	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}
	//gm nuovo codice estensione pubblicazione bandi
	public String getFlag_benicult() {
		return flag_benicult;
	}
	public void setFlag_benicult(String flag_benicult) {
		this.flag_benicult = flag_benicult;
	}
	public String getFlag_sospeso() {
		return flag_sospeso;
	}
	public void setFlag_sospeso(String flag_sospeso) {
		this.flag_sospeso = flag_sospeso;
	}

	//MARRA MEV 34470 3.04.8
	public void setLinkAffidamentoDiretto(String link_affidamento_diretto) {
		// TODO Auto-generated method stub
		this.link_affidamento_diretto = link_affidamento_diretto;
	}
	
	public String getLinkAffidamentoDiretto() {
		return link_affidamento_diretto;
	}
	//FINE MEV

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

