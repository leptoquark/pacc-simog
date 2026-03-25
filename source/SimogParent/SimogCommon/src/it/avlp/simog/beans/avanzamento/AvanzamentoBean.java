package it.avlp.simog.beans.avanzamento;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class AvanzamentoBean {
	/* refs to load many*/
	private long idAggiudicazione;
	private Timestamp dataInizioAggiudicazione;
	/**/
	private long idAvanzamento;
	private Timestamp dataInizioAvanzamento;
	private Timestamp dataFineAvanzamento;
	/* ref status (in * managers)*/
	private long idStato;
	private String descrizioneStato;
	/**/
	private int numeroAvanzamento;
	private String flagPagamento;
	private String dataAnticipazione;
	private BigDecimal importoAnticipazione;

	private String dataRaggiungimento;
	private BigDecimal importoSal;

	private String dataCertificato;
	private BigDecimal importoCertificato;

	private String flagRitardo;
	private int numeroGiorniScost;
	private int numeroGiorniProroga;
	
	private String denomStatoAvanz;
	
	private boolean okCancellazione ;
	
	public static  String AVANZ_RITARDO = "R";
	public static  String AVANZ_ANTICIPO = "A";
	public static  String AVANZ_PUNTUALE = "P";
		
	private String idLocale;
	
	public String getIdLocale() {
		return idLocale;
	}

	public void setIdLocale(String idLocale) {
		this.idLocale = idLocale;
	}

	
	public Long getIdAggiudicazione() {
		return idAggiudicazione;
	}
	public void setIdAggiudicazione(Long idAggiudicazione) {
		this.idAggiudicazione = idAggiudicazione;
	}
	public Timestamp getDataInizioAggiudicazione() {
		return dataInizioAggiudicazione;
	}
	public void setDataInizioAggiudicazione(Timestamp dataInizioAggiudicazione) {
		this.dataInizioAggiudicazione = dataInizioAggiudicazione;
	}
	public long getIdAvanzamento() {
		return idAvanzamento;
	}
	public void setIdAvanzamento(long idAvanzamento) {
		this.idAvanzamento = idAvanzamento;
	}
	public Timestamp getDataInizioAvanzamento() {
		return dataInizioAvanzamento;
	}
	public void setDataInizioAvanzamento(Timestamp dataInizioAvanzamento) {
		this.dataInizioAvanzamento = dataInizioAvanzamento;
	}
	public Timestamp getDataFineAvanzamento() {
		return dataFineAvanzamento;
	}
	public void setDataFineAvanzamento(Timestamp dataFineAvanzamento) {
		this.dataFineAvanzamento = dataFineAvanzamento;
	}
	public long getIdStato() {
		return idStato;
	}
	public void setIdStato(long idStato) {
		this.idStato = idStato;
	}
	public int getNumeroAvanzamento() {
		return numeroAvanzamento;
	}
	public void setNumeroAvanzamento(int numeroAvanzamento) {
		this.numeroAvanzamento = numeroAvanzamento;
	}
	public String getFlagPagamento() {
		return flagPagamento;
	}
	public void setFlagPagamento(String flagPagamento) {
		this.flagPagamento = flagPagamento;
	}
	public String getDataAnticipazione() {
		return dataAnticipazione;
	}
	public void setDataAnticipazione(String dataAnticipazione) {
		this.dataAnticipazione = dataAnticipazione;
	}
	public BigDecimal getImportoAnticipazione() {
		return importoAnticipazione;
	}
	public void setImportoAnticipazione(BigDecimal importoAnticipazione) {
		this.importoAnticipazione = importoAnticipazione;
	}
	public String getDataRaggiungimento() {
		return dataRaggiungimento;
	}
	public void setDataRaggiungimento(String dataRaggiungimento) {
		this.dataRaggiungimento = dataRaggiungimento;
	}
	public BigDecimal getImportoSal() {
		return importoSal;
	}
	public void setImportoSal(BigDecimal importoSal) {
		this.importoSal = importoSal;
	}
	public String getDataCertificato() {
		return dataCertificato;
	}
	public void setDataCertificato(String dataCertificato) {
		this.dataCertificato = dataCertificato;
	}
	public BigDecimal getImportoCertificato() {
		return importoCertificato;
	}
	public void setImportoCertificato(BigDecimal importoCertificato) {
		this.importoCertificato = importoCertificato;
	}
	public String getFlagRitardo() {
		return flagRitardo;
	}
	public void setFlagRitardo(String flagRitardo) {
		this.flagRitardo = flagRitardo;
	}
	public int getNumeroGiorniScost() {
		return numeroGiorniScost;
	}
	public void setNumeroGiorniScost(int numeroGiorniScost) {
		this.numeroGiorniScost = numeroGiorniScost;
	}
	public int getNumeroGiorniProroga() {
		return numeroGiorniProroga;
	}
	public void setNumeroGiorniProroga(int numeroGiorniProroga) {
		this.numeroGiorniProroga = numeroGiorniProroga;
	}
	
	/***************************************************************************************************************
	 * Verifica se risulta essere stata fatta una richiesta di annullamento
	 * 
	 * @return boolean.
	 */
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
	
	
	/***************************************************************************************************************
	 * il metodo determina se l'id dello stato � a confermato o meno. 
	 * 
	 * @return boolean
	 */
	public boolean isConfirmed(){
		
		return idStato == StatiScheda.CONFERMATO;
		
	}
	public String getDescrizioneStato() {
		return descrizioneStato;
	}
	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	
	/***************************************************************
	 * Il metodo restituisce una stringa contenente importo 
	 * anticipazione formattato avendo il punto come separatore 
	 * delle migliaia e la virgola come separatore decimale. 
	 * 
	 * @return String
	 */
	public String getImportoAnticipazioneStr() {
		return PageHelper.formattaImporto(this.importoAnticipazione);
	}
	
	/***************************************************************
	 * Il metodo restituisce una stringa contenente importo 
	 * Sal formattato avendo il punto come separatore 
	 * delle migliaia e la virgola come separatore decimale. 
	 * 
	 * @return String
	 */
	public String getImportoSalStr() {
		return PageHelper.formattaImporto(this.importoSal);
	}
	
	/******************************************************************
	 * Il metodo restituisce una stringa contenente importo 
	 * Certificato formattato avendo il punto come separatore 
	 * delle migliaia e la virgola come separatore decimale. 
	 * 
	 * @return String
	 */
	public String getImportoCertificatoStr() {
		return PageHelper.formattaImporto(this.importoCertificato);
	}
	
	public String getDenomStatoAvanz() {
		return denomStatoAvanz;
	}
	
	public void setDenomStatoAvanz(String denomStatoAvanz) {
		this.denomStatoAvanz = denomStatoAvanz;
	}
	public boolean isOkCancellazione() {
		return okCancellazione;
	}
	public void setOkCancellazione(boolean okCancellazione) {
		this.okCancellazione = okCancellazione;
	}
	
}
