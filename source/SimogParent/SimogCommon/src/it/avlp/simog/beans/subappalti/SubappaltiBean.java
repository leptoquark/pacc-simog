package it.avlp.simog.beans.subappalti;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.db.generated.DITTE_SUBAPPALTATRICI;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class SubappaltiBean {
	
	private long idRecord = -1;
	private String dataAutorizzazione;
	private Timestamp dataFineRecord;
	private String cfDitta;
	private String flagDittaSubEstera; //MEV 36771 3.04.8.1
	private Timestamp dataInizioAggiudicazione; 
	private Timestamp dataInizioRecord;
	private long idAggiudicazione;
	private long idStato;
	private BigDecimal importoEffettivo;
	private BigDecimal importoPresunto;
	private String oggettoSubappalto;
	private String descrizioneStato;
	private String idCategoria;
	private String idCpv;
	private boolean okCancellazione ;
	private List<SubappaltatoreBean> subappaltatori;//TICKET ALM - 3.04.3
	private String subappaltatoriString;
	
	//gm nuovo codice 3.0
	private String cfAggiudicatario;
	//gm fine nuovo codice 3.0
	
	private String idLocale;

	
	public boolean isOkCancellazione() {
		return okCancellazione;
	}

	public void setOkCancellazione(boolean okCancellazione) {
		this.okCancellazione = okCancellazione;
	}

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
	/**
	 * Attenzione nel caso in cui non sia stato variato il valore tramite il metodo setter il valore dell'id_record � -1
	 * @return
	 */
	public long getIdRecord() {
		return idRecord;
	}

	public void setIdRecord(long idRecord) {
		this.idRecord = idRecord;
	}

 	public String getCfDitta() {
		return cfDitta;
	}

	public void setCfDitta(String cfDitta) {
		this.cfDitta = cfDitta;
	} 

	//gm nuovo codice 3.0
/**/
	public String getCfAggiudicatario() {
		return cfAggiudicatario;
	}

	public void setCfAggiudicatario(String cfAggiudicatario) {
		this.cfAggiudicatario = cfAggiudicatario;
	}

	//gm fine nuovo codice 3.0

	public String getDataAutorizzazione() {
		return dataAutorizzazione;
	}

	public void setDataAutorizzazione(String dataAutorizzazione) {
		this.dataAutorizzazione = dataAutorizzazione;
	}

	public Timestamp getDataFineRecord() {
		return dataFineRecord;
	}

	public void setDataFineRecord(Timestamp dataFineRecord) {
		this.dataFineRecord = dataFineRecord;
	}

	public Timestamp getDataInizioAggiudicazione() {
		return dataInizioAggiudicazione;
	}

	public void setDataInizioAggiudicazione(Timestamp dataInizioAggiudicazione) {
		this.dataInizioAggiudicazione = dataInizioAggiudicazione;
	}

	public Timestamp getDataInizioRecord() {
		return dataInizioRecord;
	}

	public void setDataInizioRecord(Timestamp dataInizioRecord) {
		this.dataInizioRecord = dataInizioRecord;
	}

	public long getIdAggiudicazione() {
		return idAggiudicazione;
	}

	public void setIdAggiudicazione(long idAggiudicazione) {
		this.idAggiudicazione = idAggiudicazione;
	}

	public long getIdStato() {
		return idStato;
	}

	public void setIdStato(long idStato) {
		this.idStato = idStato;
	}

	public BigDecimal getImportoEffettivo() {
		return importoEffettivo;
	}
	
	public String getImportoEffettivoStr(){
		return PageHelper.formattaImporto(importoEffettivo);
	}

	public void setImportoEffettivo(BigDecimal importoEffettivo) {
		this.importoEffettivo = importoEffettivo;
	}

	public BigDecimal getImportoPresunto() {
		return importoPresunto;
	}
	
	public String getImportoPresuntoStr(){
		return PageHelper.formattaImporto(importoPresunto);
	}

	public void setImportoPresunto(BigDecimal importoPresunto) {
		this.importoPresunto = importoPresunto;
	}

	public String getOggettoSubappalto() {
		return oggettoSubappalto;
	}

	public void setOggettoSubappalto(String oggettoSubappalto) {
		this.oggettoSubappalto = oggettoSubappalto;
	}

	public String getDescrizioneStato() {
		return descrizioneStato;
	}

	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}

	public String getIdCpv() {
		return idCpv;
	}

	public void setIdCpv(String idCpv) {
		this.idCpv = idCpv;
	}

	public String getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(String idCategoria) {
		this.idCategoria = idCategoria;
	}

	public List<SubappaltatoreBean> getSubappaltatori() {
		return subappaltatori;
	}

	public void setSubappaltatori(List<SubappaltatoreBean> subappaltatori) {
		this.subappaltatori = subappaltatori;
	}
	
	public void setSubappaltatoriString (String subappaltatoriString){
		this.subappaltatoriString = subappaltatoriString;
	}
	
	public String getSubappaltatoriString(){
		return subappaltatoriString;
	}

	//MEV 36771 3.04.8.1
	public String getFlagDittaSubEstera() {
		return flagDittaSubEstera;
	}

	public void setFlagDittaSubEstera(String flagDittaSubEstera) {
		this.flagDittaSubEstera = flagDittaSubEstera;
	}
	//fine MEV 36771 3.04.8.1
	
	
	
}
