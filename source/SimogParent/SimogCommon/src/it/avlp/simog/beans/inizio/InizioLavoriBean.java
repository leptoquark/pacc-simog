package it.avlp.simog.beans.inizio;


import it.avlp.simog.beans.PubblicazioneBean;
import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.VO;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class InizioLavoriBean implements VO{
	
	private long idInizioLavori;
	private Timestamp dataInizioLavori;
	private Timestamp dataFineLavori;
	private int idStato;
	private long idAggiudicazione;
	private Timestamp dataInizioAggiudicazione;
	private String dataStipula;
	private String dataEsecutivita;
	private BigDecimal importoCauzione;	
	private String dataIniProgEsec;
	private String dataAppProgEsec;	
	private String flagFrazionata;	
	private String dataVerbaleCons;
	private String dataVerbaleDef;	
	private String flagRiserva;
	private String dataVerbaleInizio;
	private String dataTermine;
	private PubblicazioneBean pubblicazione;
	private String descrizioneStato;
	private boolean okCancellazione ;
	
	// PP B302.2.0
	private String idMotivoVarCO;
	private boolean validaVariazione = false;

	public boolean isOkCancellazione() {
		return okCancellazione;
	}

	public void setOkCancellazione(boolean okCancellazione) {
		this.okCancellazione = okCancellazione;
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
	
	public boolean isRichDelete(){
		if(descrizioneStato != null)
			return (descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTA_CANCELLAZIONE)
					|| descrizioneStato.toLowerCase().contains(PSBD.MSG_RICHIESTA_CANC_TOTALE));
		else return false;
	}
	
	public boolean isConfirmed(){
		
		return idStato == StatiScheda.CONFERMATO;
		
	}
	public Timestamp getDataFineLavori() {
		return dataFineLavori;
	}
	public void setDataFineLavori(Timestamp dataFineLavori) {
		this.dataFineLavori = dataFineLavori;
	}
	public String getDataIniProgEsec() {
		return dataIniProgEsec;
	}
	public void setDataIniProgEsec(String dataIniProgEsec) {
		this.dataIniProgEsec = dataIniProgEsec;
	}
	public String getDataAppProgEsec() {
		return dataAppProgEsec;
	}
	public void setDataAppProgEsec(String dataAppProgEsec) {
		this.dataAppProgEsec = dataAppProgEsec;
	}
	public String getFlagFrazionata() {
		return flagFrazionata;
	}
	public void setFlagFrazionata(String flagFrazionata) {
		this.flagFrazionata = flagFrazionata;
	}
	public String getDataVerbaleCons() {
		return dataVerbaleCons;
	}
	public void setDataVerbaleCons(String dataVerbaleCons) {
		this.dataVerbaleCons = dataVerbaleCons;
	}
	public String getDataVerbaleDef() {
		return dataVerbaleDef;
	}
	public void setDataVerbaleDef(String dataVerbaleDef) {
		this.dataVerbaleDef = dataVerbaleDef;
	}
	public long getIdInizioLavori() {
		return idInizioLavori;
	}
	public void setIdInizioLavori(long idInizioLavori) {
		this.idInizioLavori = idInizioLavori;
	}
	public Timestamp getDataInizioLavori() {
		return dataInizioLavori;
	}
	public void setDataInizioLavori(Timestamp dataInizioLavori) {
		this.dataInizioLavori = dataInizioLavori;
	} 
	public int getIdStato() {
		return idStato;
	}
	public void setIdStato(int idStato) {
		this.idStato = idStato;
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
	public String getDataStipula() {
		return dataStipula;
	}
	public void setDataStipula(String dataStipula) {
		this.dataStipula = dataStipula;
	}
	public String getDataEsecutivita() {
		return dataEsecutivita;
	}
	public void setDataEsecutivita(String dataEsecutivita) {
		this.dataEsecutivita = dataEsecutivita;
	}
	public BigDecimal getImportoCauzione() {
		return importoCauzione;
	}
	public String getImportoCauzioneStr() {
		return PageHelper.formattaImporto(importoCauzione);
	}
	public void setImportoCauzione(BigDecimal importoCauzione) {
		this.importoCauzione = importoCauzione;
	}
	public String getFlagRiserva() {
		return flagRiserva;
	}
	public void setFlagRiserva(String flagRiserva) {
		this.flagRiserva = flagRiserva;
	}
	public String getDataVerbaleInizio() {
		return dataVerbaleInizio;
	}
	public void setDataVerbaleInizio(String dataVerbaleInizio) {
		this.dataVerbaleInizio = dataVerbaleInizio;
	}
	public String getDataTermine() {
		return dataTermine;
	}
	public void setDataTermine(String dataTermine) {
		this.dataTermine = dataTermine;
	}
	public PubblicazioneBean getPubblicazione() {
		return pubblicazione;
	}
	public void setPubblicazione(PubblicazioneBean pubblicazione) {
		this.pubblicazione = pubblicazione;
	}


	public String getDescrizioneStato() {
		return descrizioneStato;
	}


	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
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
