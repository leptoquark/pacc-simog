package it.avlp.simog.beans.aggiudicazione;

import it.avlp.simog.common.servlet.PSBD;

import java.sql.Timestamp;

/*******************************************************************************
 * <code>public class <b>DittaAusiliariaBean</b></code><br><br>
 * La classe DittaAusiliariaBean gestisce i seguenti parametri:
 * <ul>
 * <li>long : idAggiudicazione
 * <li>Timestamp : dataInizioAggiudicazione
 * <li>long : idAggiudicatario
 * <li>Timestamp : dataInizioAggiudicatario
 * <li>SoggettoPartecipanteBean : soggettoPartecipante
 * <li>long : idStato
 * <li>String : cfAusiliaria
 * <li>String : flagAvvalimento
 * <li>String : flag Esteri
 * </ul>
 * attraverso i relativi metodi di get e set
 */
public class DittaAusiliariaBean {
	private long idDittaAusiliaria;
	private long idAggiudicazione;
	private Timestamp dataInizioAggiudicazione;
	private long idAggiudicatario;
	private Timestamp dataInizioAggiudicatario;
	private SoggettoPartecipanteBean soggettoPartecipante;
	
	//gm aggiunto per i ws
	private String codiceFiscaleAggiudicatario;
	private String id_statoAggiudicatario;
	
	private long idStato;
	private String flagAvvalimento;
	private String cfAusiliaria;
	
	public long getIdDittaAusiliaria() {
		return idDittaAusiliaria;
	}
	public void setIdDittaAusiliaria(long idDittaAusiliaria) {
		this.idDittaAusiliaria = idDittaAusiliaria;
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
	
	public long getIdAggiudicatario() {
		return idAggiudicatario;
	}
	public void setIdAggiudicatario(long idAggiudicatario) {
		this.idAggiudicatario = idAggiudicatario;
	}
	
	public Timestamp getDataInizioAggiudicatario() {
		return dataInizioAggiudicatario;
	}
	public void setDataInizioAggiudicatario(Timestamp dataInizioAggiudicatario) {
		this.dataInizioAggiudicatario = dataInizioAggiudicatario;
	}
	
	public SoggettoPartecipanteBean getSoggettoPartecipante() {
		return soggettoPartecipante;
	}
	
	public void setSoggettoPartecipante(SoggettoPartecipanteBean soggettoPartecipante) {
		this.soggettoPartecipante = soggettoPartecipante;
	}
	
	public long getIdStato() {
		return idStato;
	}
	public void setIdStato(long idStato) {
		this.idStato = idStato;
	}
	
	public String getFlagAvvalimento() {
		return flagAvvalimento;
	}
	
	public void setFlagAvvalimento(String flagAvvalimento){
		this.flagAvvalimento = flagAvvalimento;
	}
	
	public String getCfAusiliaria() {
		return cfAusiliaria;
	}
	public void setCfAusiliaria(String cfAusiliaria) {
		this.cfAusiliaria = cfAusiliaria;
	}
	public String getId_statoAggiudicatario() {
		return id_statoAggiudicatario;
	}
	public void setId_statoAggiudicatario(String id_statoAggiudicatario) {
		this.id_statoAggiudicatario = id_statoAggiudicatario;
	}
	public String getCodiceFiscaleAggiudicatario() {
		return codiceFiscaleAggiudicatario;
	}
	public void setCodiceFiscaleAggiudicatario(String codiceFiscaleAggiudicatario) {
		this.codiceFiscaleAggiudicatario = codiceFiscaleAggiudicatario;
	}
	
	/*********************************************************************************
	 * Il metodo restituisce una stringa associata allo stato del flag di avvalimento. 
	 * Se il flag risulta impostato a :
	 * <ul>
	 * <li> 1 : � stato selezionato Requisiti
	 * <li> 2 : � stato selezionato Attestazione
	 * <li> 3 : sono stati selezionati entrambi Requisiti e Attestazione
	 * <li> nulla : non � stato selezionato alcun flag
	 * </ul>
	 * 
	 * @return Stringa associata al flag
	 */
	public String getFlagAvvalimentoDecod() {
		if("1".equalsIgnoreCase(flagAvvalimento))
			return PSBD.REQUISITI_FLAG_AVVALIMENTO;
		else if("2".equalsIgnoreCase(flagAvvalimento))
			return PSBD.ATTTESTAZIONE_FLAG_AVVALIMENTO;
		else if("3".equalsIgnoreCase(flagAvvalimento))
			return PSBD.ENTRAMBI_FLAG_AVVALIMENTO;
		else return PSBD.NESSUNO_FLAG_AVVALIMENTO;
	}
}
	