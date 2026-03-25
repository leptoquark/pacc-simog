package it.avlp.simog.beans.collaudo;
import it.avlp.simog.beans.ResponsabileBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.common.servlet.PSBD;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
public class CollaudoBean {
	
	private long idCollaudo;
	private Timestamp dataIniColl;
	private Timestamp dataFinColl;
	
	private long idStato;
	private String descrizioneStato;
	
	private long idAggiudicazione;
	private Timestamp dataIniAggiudicazione;
	private String dataRegolareEsec;
	private String dataCollaudoStat;
	private String modoCollaudo;
	private String dataNominaColl;
	private String dataIniOper;
	private String dataCertCollaudo;
	private String dataDelibera;
	private String esitoCollaudo;
	private BigDecimal impFinaleLavori;
	private BigDecimal impFinaleServizi;
	private BigDecimal impFinaleFornit;
	private BigDecimal impFinaleSicur;
	private BigDecimal impProgettazione;
	private BigDecimal impDisposizione;
	private int ammNumDefinite;
	private int ammNumDaDef;
	private BigDecimal ammImportoRich;
	private BigDecimal ammImportoDef;
	private String ammImportoRichStr;
	private String ammImportoDefStr;
	private int arbNumDefinite;
	private int arbNumDaDef;
	private BigDecimal arbImportoRich;
	private BigDecimal arbImportoDef;
	private String arbImportoRichStr;
	private String arbImportoDefStr;
	private int giuNumDefinite;
	private int giuNumDaDef;
	private BigDecimal giuImportORich;
	private BigDecimal giuImportoDef;
	private String giuImportORichStr;
	private String giuImportoDefStr;
	private int traNumDefinite;
	private int traNumDaDef;
	private BigDecimal traImportoRich;
	private BigDecimal traImportoDef;
	private String traImportoRichStr;
	private String traImportoDefStr;
	private List<ResponsabileBean> respBean;
	private String impFinaleLavoriStr;
	private String impFinaleServiziStr;
	private String impFinaleFornitStr;
	private String impFinaleSicurStr;
	private String impProgettazioneStr;
	private String impDisposizioneStr;
	private String subStr;
	private BigDecimal sub;
	private BigDecimal sub2;
	private String sub2Str;
	private BigDecimal finale;
	private String finaleStr;
	private int totaleRiserveAvanzate;
	private int totaleRiserveDefinite;
	private BigDecimal importoContenziosoRisolto;
	private String importoContenziosoRisoltoStr;
	private String flagLavoriEstesi;
	private boolean okCancellazione ;
	
	private String idLocale;
	
	// PP B302.2.0
	private String idMotivoVarCO;
	private boolean validaVariazione = false;
	
	public String getIdLocale() {
		return idLocale;
	}

	public void setIdLocale(String idLocale) {
		this.idLocale = idLocale;
	}

	public int getTotaleRiserveAvanzate() {
		return totaleRiserveAvanzate;
	}

	public void setTotaleRiserveAvanzate(int totaleRiserveAvanzate) {
		this.totaleRiserveAvanzate = totaleRiserveAvanzate;
	}

	public int getTotaleRiserveDefinite() {
		return totaleRiserveDefinite;
	}

	public void setTotaleRiserveDefinite(int totaleRiserveDefinite) {
		this.totaleRiserveDefinite = totaleRiserveDefinite;
	}

	public BigDecimal getImportoContenziosoRisolto() {
		return importoContenziosoRisolto;
	}

	public void setImportoContenziosoRisolto(BigDecimal importoContenziosoRisolto) {
		this.importoContenziosoRisolto = importoContenziosoRisolto;
	}

	public String getImportoContenziosoRisoltoStr() {
		return importoContenziosoRisoltoStr;
	}

	public void setImportoContenziosoRisoltoStr(String importoContenziosoRisoltoStr) {
		this.importoContenziosoRisoltoStr = importoContenziosoRisoltoStr;
	}

	public BigDecimal getSub2() {
		return sub2;
	}

	public void setSub2(BigDecimal sub2) {
		this.sub2 = sub2;
	}

	public String getSub2Str() {
		return sub2Str;
	}

	public void setSub2Str(String sub2Str) {
		this.sub2Str = sub2Str;
	}

	public BigDecimal getFinale() {
		return finale;
	}

	public void setFinale(BigDecimal finale) {
		this.finale = finale;
	}

	public String getFinaleStr() {
		return finaleStr;
	}

	public void setFinaleStr(String finaleStr) {
		this.finaleStr = finaleStr;
	}

	public BigDecimal getSub() {
		return sub;
	}

	public void setSub(BigDecimal sub) {
		this.sub = sub;
	}

	public String getSubStr() {
		return subStr;
	}

	public void setSubStr(String subStr) {
		this.subStr = subStr;
	}

	public String getAmmImportoRichStr() {
		return ammImportoRichStr;
	}

	public void setAmmImportoRichStr(String ammImportoRichStr) {
		this.ammImportoRichStr = ammImportoRichStr;
	}

	public String getAmmImportoDefStr() {
		return ammImportoDefStr;
	}

	public void setAmmImportoDefStr(String ammImportoDefStr) {
		this.ammImportoDefStr = ammImportoDefStr;
	}

	public String getArbImportoRichStr() {
		return arbImportoRichStr;
	}

	public void setArbImportoRichStr(String arbImportoRichStr) {
		this.arbImportoRichStr = arbImportoRichStr;
	}

	public String getArbImportoDefStr() {
		return arbImportoDefStr;
	}

	public void setArbImportoDefStr(String arbImportoDefStr) {
		this.arbImportoDefStr = arbImportoDefStr;
	}

	public String getGiuImportORichStr() {
		return giuImportORichStr;
	}

	public void setGiuImportORichStr(String giuImportORichStr) {
		this.giuImportORichStr = giuImportORichStr;
	}

	public String getGiuImportoDefStr() {
		return giuImportoDefStr;
	}

	public void setGiuImportoDefStr(String giuImportoDefStr) {
		this.giuImportoDefStr = giuImportoDefStr;
	}

	public String getTraImportoRichStr() {
		return traImportoRichStr;
	}

	public void setTraImportoRichStr(String traImportoRichStr) {
		this.traImportoRichStr = traImportoRichStr;
	}

	public String getTraImportoDefStr() {
		return traImportoDefStr;
	}

	public void setTraImportoDefStr(String traImportoDefStr) {
		this.traImportoDefStr = traImportoDefStr;
	}

	public String getImpFinaleLavoriStr() {
		return impFinaleLavoriStr;
	}

	public void setImpFinaleLavoriStr(String impFinaleLavoriStr) {
		this.impFinaleLavoriStr = impFinaleLavoriStr;
	}

	public String getImpFinaleServiziStr() {
		return impFinaleServiziStr;
	}

	public void setImpFinaleServiziStr(String impFinaleServiziStr) {
		this.impFinaleServiziStr = impFinaleServiziStr;
	}

	public String getImpFinaleFornitStr() {
		return impFinaleFornitStr;
	}

	public void setImpFinaleFornitStr(String impFinaleFornitStr) {
		this.impFinaleFornitStr = impFinaleFornitStr;
	}

	public String getImpFinaleSicurStr() {
		return impFinaleSicurStr;
	}

	public void setImpFinaleSicurStr(String impFinaleSicurStr) {
		this.impFinaleSicurStr = impFinaleSicurStr;
	}

	public String getImpProgettazioneStr() {
		return impProgettazioneStr;
	}

	public void setImpProgettazioneStr(String impProgettazioneStr) {
		this.impProgettazioneStr = impProgettazioneStr;
	}

	public String getImpDisposizioneStr() {
		return impDisposizioneStr;
	}

	public void setImpDisposizioneStr(String impDisposizioneStr) {
		this.impDisposizioneStr = impDisposizioneStr;
	}

	public void setIdCollaudo(long idCollaudo) {
		this.idCollaudo = idCollaudo;
	}

	public void setIdAggiudicazione(long idAggiudicazione) {
		this.idAggiudicazione = idAggiudicazione;
	}

	public boolean isConfirmed(){
		return idStato == StatiScheda.CONFERMATO;
	}
	
	public Long getIdCollaudo() {
		return idCollaudo;
	}
	public void setIdCollaudo(Long idCollaudo) {
		this.idCollaudo = idCollaudo;
	}
	public Timestamp getDataIniColl() {
		return dataIniColl;
	}
	public void setDataIniColl(Timestamp dataIniColl) {
		this.dataIniColl = dataIniColl;
	}
	public Timestamp getDataFinColl() {
		return dataFinColl;
	}
	public void setDataFinColl(Timestamp dataFinColl) {
		this.dataFinColl = dataFinColl;
	}
	public long getIdStato() {
		return idStato;
	}
	public void setIdStato(long idStato) {
		this.idStato = idStato;
	}
	public Long getIdAggiudicazione() {
		return idAggiudicazione;
	}
	public void setIdAggiudicazione(Long idAggiudicazione) {
		this.idAggiudicazione = idAggiudicazione;
	}
	public Timestamp getDataIniAggiudicazione() {
		return dataIniAggiudicazione;
	}
	public void setDataIniAggiudicazione(Timestamp dataIniAggiudicazione) {
		this.dataIniAggiudicazione = dataIniAggiudicazione;
	}
	public String getDataRegolareEsec() {
		return dataRegolareEsec;
	}
	public void setDataRegolareEsec(String dataRegolareEsec) {
		this.dataRegolareEsec = dataRegolareEsec;
	}
	public String getDataCollaudoStat() {
		return dataCollaudoStat;
	}
	public void setDataCollaudoStat(String dataCollaudoStat) {
		this.dataCollaudoStat = dataCollaudoStat;
	}
	public String getModoCollaudo() {
		return modoCollaudo;
	}
	public void setModoCollaudo(String modoCollaudo) {
		this.modoCollaudo = modoCollaudo;
	}
	public String getDataNominaColl() {
		return dataNominaColl;
	}
	public void setDataNominaColl(String dataNominaColl) {
		this.dataNominaColl = dataNominaColl;
	}
	public String getDataIniOper() {
		return dataIniOper;
	}
	public void setDataIniOper(String dataIniOper) {
		this.dataIniOper = dataIniOper;
	}
	public String getDataCertCollaudo() {
		return dataCertCollaudo;
	}
	public void setDataCertCollaudo(String dataCertCollaudo) {
		this.dataCertCollaudo = dataCertCollaudo;
	}
	public String getEsitoCollaudo() {
		return esitoCollaudo;
	}
	public void setEsitoCollaudo(String esitoCollaudo) {
		this.esitoCollaudo = esitoCollaudo;
	}
	public BigDecimal getImpFinaleLavori() {
		return impFinaleLavori;
	}
	public void setImpFinaleLavori(BigDecimal impFinaleLavori) {
		this.impFinaleLavori = impFinaleLavori;
	}
	public BigDecimal getImpFinaleServizi() {
		return impFinaleServizi;
	}
	public void setImpFinaleServizi(BigDecimal impFinaleServizi) {
		this.impFinaleServizi = impFinaleServizi;
	}
	public BigDecimal getImpFinaleFornit() {
		return impFinaleFornit;
	}
	public void setImpFinaleFornit(BigDecimal impFinaleFornit) {
		this.impFinaleFornit = impFinaleFornit;
	}
	public BigDecimal getImpFinaleSicur() {
		return impFinaleSicur;
	}
	public void setImpFinaleSicur(BigDecimal impFinaleSicur) {
		this.impFinaleSicur = impFinaleSicur;
	}
	public BigDecimal getImpProgettazione() {
		return impProgettazione;
	}
	public void setImpProgettazione(BigDecimal impProgettazione) {
		this.impProgettazione = impProgettazione;
	}
	public BigDecimal getImpDisposizione() {
		return impDisposizione;
	}
	public void setImpDisposizione(BigDecimal impDisposizione) {
		this.impDisposizione = impDisposizione;
	}
	public int getAmmNumDefinite() {
		return ammNumDefinite;
	}
	public void setAmmNumDefinite(int ammNumDefinite) {
		this.ammNumDefinite = ammNumDefinite;
	}
	public int getAmmNumDaDef() {
		return ammNumDaDef;
	}
	public void setAmmNumDaDef(int ammNumDaDef) {
		this.ammNumDaDef = ammNumDaDef;
	}
	public BigDecimal getAmmImportoRich() {
		return ammImportoRich;
	}
	public void setAmmImportoRich(BigDecimal ammImportoRich) {
		this.ammImportoRich = ammImportoRich;
	}
	public BigDecimal getAmmImportoDef() {
		return ammImportoDef;
	}
	public void setAmmImportoDef(BigDecimal ammImportoDef) {
		this.ammImportoDef = ammImportoDef;
	}
	public int getArbNumDefinite() {
		return arbNumDefinite;
	}
	public void setArbNumDefinite(int arbNumDefinite) {
		this.arbNumDefinite = arbNumDefinite;
	}
	public int getArbNumDaDef() {
		return arbNumDaDef;
	}
	public void setArbNumDaDef(int arbNumDaDef) {
		this.arbNumDaDef = arbNumDaDef;
	}
	public BigDecimal getArbImportoRich() {
		return arbImportoRich;
	}
	public void setArbImportoRich(BigDecimal arbImportoRich) {
		this.arbImportoRich = arbImportoRich;
	}
	public BigDecimal getArbImportoDef() {
		return arbImportoDef;
	}
	public void setArbImportoDef(BigDecimal arbImportoDef) {
		this.arbImportoDef = arbImportoDef;
	}
	public int getGiuNumDefinite() {
		return giuNumDefinite;
	}
	public void setGiuNumDefinite(int giuNumDefinite) {
		this.giuNumDefinite = giuNumDefinite;
	}
	public int getGiuNumDaDef() {
		return giuNumDaDef;
	}
	public void setGiuNumDaDef(int giuNumDaDef) {
		this.giuNumDaDef = giuNumDaDef;
	}
	public BigDecimal getGiuImportORich() {
		return giuImportORich;
	}
	public void setGiuImportORich(BigDecimal giuImportORich) {
		this.giuImportORich = giuImportORich;
	}
	public BigDecimal getGiuImportoDef() {
		return giuImportoDef;
	}
	public void setGiuImportoDef(BigDecimal giuImportoDef) {
		this.giuImportoDef = giuImportoDef;
	}
	public int getTraNumDefinite() {
		return traNumDefinite;
	}
	public void setTraNumDefinite(int traNumDefinite) {
		this.traNumDefinite = traNumDefinite;
	}
	public int getTraNumDaDef() {
		return traNumDaDef;
	}
	public void setTraNumDaDef(int traNumDaDef) {
		this.traNumDaDef = traNumDaDef;
	}
	public BigDecimal getTraImportoRich() {
		return traImportoRich;
	}
	public void setTraImportoRich(BigDecimal traImportoRich) {
		this.traImportoRich = traImportoRich;
	}
	public BigDecimal getTraImportoDef() {
		return traImportoDef;
	}
	public void setTraImportoDef(BigDecimal traImportoDef) {
		this.traImportoDef = traImportoDef;
	}
	public List<ResponsabileBean> getRespBean() {
		return respBean;
	}
	public void setRespBean(List<ResponsabileBean> respBean) {
		this.respBean = respBean;
	}

	public String getDescrizioneStato() {
		return descrizioneStato;
	}

	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	
	public boolean isRichAnn(){
		if(descrizioneStato != null)
			return (descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTO_ANNULLAMENTO));
		else return false;
	}

	public boolean isRichDelete(){
		if(descrizioneStato != null)
			return (descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTA_CANCELLAZIONE)
					|| descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTA_CANC_TOTALE));
		else return false;
	}
	
	public String getDataDelibera() {
		return dataDelibera;
	}

	public void setDataDelibera(String dataDelibera) {
		this.dataDelibera = dataDelibera;
	}

	public String getFlagLavoriEstesi() {
		return flagLavoriEstesi;
	}

	public void setFlagLavoriEstesi(String flagLavoriEstesi) {
		this.flagLavoriEstesi = flagLavoriEstesi;
	}

	public boolean isOkCancellazione() {
		return okCancellazione;
	}

	public void setOkCancellazione(boolean okCancellazione) {
		this.okCancellazione = okCancellazione;
	}

	public String getIdMotivoVarCO() {
		return idMotivoVarCO;
	}

	public void setIdMotivoVarCO(String idMotivoVarCO) {
		this.idMotivoVarCO = idMotivoVarCO;
	}

	public boolean isValidaVariazione() {
		return validaVariazione;
	}

	public void setValidaVariazione(boolean validaVariazione) {
		this.validaVariazione = validaVariazione;
	}
	

}
