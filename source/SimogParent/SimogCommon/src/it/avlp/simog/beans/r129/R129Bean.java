package it.avlp.simog.beans.r129;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.db.Costanti;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class R129Bean {
	private long idRecord = -1;
	private Timestamp dataInizioRecord;
	private long idAggiudicazione;
	private Timestamp dataInizioAggiudicazione;
	private long idStato;
	private String tipoComunicazione;
	private int durataSospensione;
	private String motivoSospensione;
	private String dataIstRecesso;
	private String flagAccolta;
	private String flagTardiva;
	private String flagRipresa;
	private String flagRiserva;
	private BigDecimal importoSpese;
	private BigDecimal importoOneri;
	private String dataComunicazione;
	private String dataTermine;
	private String dataConsegna;
	private String descrizioneStato;
	private boolean okCancellazione ;
	
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
	
	public String getDataConsegna() {
		return dataConsegna;
	}
	public void setDataConsegna(String dataConsegna) {
		this.dataConsegna = dataConsegna;
	}
	public String getDataTermine() {
		return dataTermine;
	}
	public void setDataTermine(String dataTermine) {
		this.dataTermine = dataTermine;
	}
	public String getDataComunicazione() {
		return dataComunicazione;
	}
	public void setDataComunicazione(String dataComunicazione) {
		this.dataComunicazione = dataComunicazione;
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
	public Timestamp getDataInizioAggiudicazione() {
		return dataInizioAggiudicazione;
	}
	public void setDataInizioAggiudicazione(Timestamp dataInizioAggiudicazione) {
		this.dataInizioAggiudicazione = dataInizioAggiudicazione;
	}
	public long getIdStato() {
		return idStato;
	}
	public void setIdStato(long idStato) {
		this.idStato = idStato;
	}
	public String getTipoComunicazione() {
		return tipoComunicazione;
	}
	public void setTipoComunicazione(String tipoComunicazione) {
		this.tipoComunicazione = tipoComunicazione;
	}
	public int getDurataSospensione() {
		return durataSospensione;
	}
	public void setDurataSospensione(int durataSospensione) {
		this.durataSospensione = durataSospensione;
	}
	public String getMotivoSospensione() {
		return motivoSospensione;
	}
	public void setMotivoSospensione(String motivoSospensione) {
		this.motivoSospensione = motivoSospensione;
	}
	public String getDataIstRecesso() {
		return dataIstRecesso;
	}
	public void setDataIstRecesso(String dataIstRecesso) {
		this.dataIstRecesso = dataIstRecesso;
	}
	public String getFlagAccolta() {
		return flagAccolta;
	}
	public void setFlagAccolta(String flagAccolta) {
		this.flagAccolta = flagAccolta;
	}
	public String getFlagTardiva() {
		return flagTardiva;
	}
	public void setFlagTardiva(String flagTardiva) {
		this.flagTardiva = flagTardiva;
	}
	public String getFlagRipresa() {
		return flagRipresa;
	}
	public void setFlagRipresa(String flagRipresa) {
		this.flagRipresa = flagRipresa;
	}
	public String getFlagRiserva() {
		return flagRiserva;
	}
	public void setFlagRiserva(String flagRiserva) {
		this.flagRiserva = flagRiserva;
	}
	public BigDecimal getImportoSpese() {
		return importoSpese;
	}
	public void setImportoSpese(BigDecimal importoSpese) {
		this.importoSpese = importoSpese;
	}
	public BigDecimal getImportoOneri() {
		return importoOneri;
	}
	public void setImportoOneri(BigDecimal importoOneri) {
		this.importoOneri = importoOneri;
	}
	public String getDescrizioneStato() {
		return descrizioneStato;
	}
	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	
	public boolean isConfirmed(){
		
		return idStato == StatiScheda.CONFERMATO;
		
	}
	/************************************************************************
	 * Il metodo determina se la comunicazione sia di Ritardo o meno
	 * 
	 * @return boolean - True se la comunicazione � di ritardo, false altrimenti
	 */
	public boolean getRitardo() {
		return Costanti.TIPCOM_RITARDO.equals(this.tipoComunicazione);
	}
	/************************************************************************
	 * Il metodo determina se la comunicazione sia di Sospensione o meno
	 * 
	 * @return boolean - True se la comunicazione � di Sospensione, false altrimenti
	 */
	public boolean getSospensione() {
		return Costanti.TIPCOM_SOSPENSIONE.equals(this.tipoComunicazione);
	}
	

}
