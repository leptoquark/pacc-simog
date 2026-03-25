package it.avlp.simog.beans.aggiudicazione;

import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/*******************************************************************************
 * <code>public class <b>AggiudicatarioBean</b></code><br><br>
 * La classe AggiudicatarioBean gestisce i seguenti parametri:
 * <ul>
 * <li>long : idAggiudicazione
 * <li>Timestamp : dataInizioAggiudicazione
 * <li>String : ruolo
 * <li>long : idStato
 * <li>String : cfAusiliaria
 * <li>String : flagAvvalimento
 * <li>BigDecimal : percentuale
 * <li>long : idTipoAgg
 * <li>long : idGruppo
 * </ul>
 * attraverso i relativi metodi di get e set
 */
public class AggiudicatarioBean {
	
	private SoggettoPartecipanteBean soggettoPartecipante;
	//gm aggiunto per ditte ausiliarie
	private List<DittaAusiliariaBean> ditteAusiliarie;
	private String ditteAusiliarieString;
	private String ditteRaggruppamentoString;
	
	private long idAggiudicatario;
	private Timestamp dataInizioAggiudicatario;
	
	private long idAggiudicazione;
	private Timestamp dataInizioAggiudicazione;
	private String ruolo;
	private long idStato;
	private String cfAusiliaria;
	private String flagAvvalimento;
	private BigDecimal percentuale;
	
	private long idTipoAgg;
	
	//gm aggiunto per raggruppamenti di impresa
	private long idGruppo;
	
	// Rinaldo ticket 654 ////////
	private BigDecimal impAggiudicatario;
	private BigDecimal percRibassoAggiudicatario;
	private BigDecimal percAumentoAggiudicatario;
	
	//!!SM MEV #4113 15-01-2019 Inizio
	private long selezionato;
	//!!SM MEV #4113 Fine

	
	public BigDecimal getImpAggiudicatario() {
		return impAggiudicatario;
	}
	public void setImpAggiudicatario(BigDecimal impAggiudicatario) {
		this.impAggiudicatario = impAggiudicatario;
	}
	public String getImpAggiudicatarioStr() {
		return PageHelper.formattaImporto(impAggiudicatario);
	}
	
	public BigDecimal getPercRibassoAggiudicatario() {
		return percRibassoAggiudicatario;
	}
	public void setPercRibassoAggiudicatario(BigDecimal percRibassoAggiudicatario) {
		this.percRibassoAggiudicatario = percRibassoAggiudicatario;
	}
	public String getPercRibassoAggiudicatarioStr() {
		return PageHelper.formattaImporto(percRibassoAggiudicatario);
	}
	public BigDecimal getPercAumentoAggiudicatario() {
		return percAumentoAggiudicatario;
	}
	public void setPercAumentoAggiudicatario(BigDecimal percAumentoAggiudicatario) {
		this.percAumentoAggiudicatario = percAumentoAggiudicatario;
	}
	public String getPercAumentoAggiudicatarioStr() {
		return PageHelper.formattaImporto(percAumentoAggiudicatario);
	}
	///////////////////////////////
	
	public long getIdTipoAgg() {
		return idTipoAgg;
	}
	public void setIdTipoAgg(long idTipoAgg) {
		this.idTipoAgg = idTipoAgg;
	}
	public Timestamp getDataInizioAggiudicazione() {
		return dataInizioAggiudicazione;
	}
	public void setDataInizioAggiudicazione(Timestamp dataInizioAggiudicazione) {
		this.dataInizioAggiudicazione = dataInizioAggiudicazione;
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
	
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
	public String getCfAusiliaria() {
		return cfAusiliaria;
	}
	public void setCfAusiliaria(String cfAusiliaria) {
		this.cfAusiliaria = cfAusiliaria;
	}
	public String getFlagAvvalimento() {
		return flagAvvalimento;
	}
	
	//gm aggiunto per raggruppamenti di impresa
	public long getIdGruppo() {
		return idGruppo;
	}
	public void setIdGruppo(long idGruppo) {
		this.idGruppo = idGruppo;
	}
	
	
	/*********************************************************************************
	 * Il metodo restituisce una stringa associata allo stato del flag di avvalimento. 
	 * Se il flag risulta impostato a :
	 * <ul>
	 * <li> 1 : e' stato selezionato Requisiti
	 * <li> 2 : e' stato selezionato Attestazione
	 * <li> 3 : sono stati selezionati entrambi Requisiti e Attestazione
	 * <li> nulla : non e' stato selezionato alcun flag
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
	
	public void setFlagAvvalimento(String flagAvvalimento) {
		this.flagAvvalimento = flagAvvalimento;
	}
	public SoggettoPartecipanteBean getSoggettoPartecipante() {
		return soggettoPartecipante;
	}
	public void setSoggettoPartecipante(
			SoggettoPartecipanteBean soggettoPartecipante) {
		this.soggettoPartecipante = soggettoPartecipante;
	}
	public BigDecimal getPercentuale() {
		return percentuale;
	}
	public void setPercentuale(BigDecimal percentuale) {
		this.percentuale = percentuale;
	} 	
	public void setDitteAusiliarie (List<DittaAusiliariaBean> ditteAusiliarie){
		this.ditteAusiliarie = ditteAusiliarie;
	}
	public List<DittaAusiliariaBean> getDitteAusiliarie(){
		return ditteAusiliarie;
	}	
	public void setDitteAusiliarieString (String ditteAusiliarieString){
		this.ditteAusiliarieString = ditteAusiliarieString;
	}
	public String getDitteAusiliarieString(){
		return ditteAusiliarieString;
	}
	public void setDitteRaggruppamentoString (String ditteRaggruppamentoString){
		this.ditteRaggruppamentoString = ditteRaggruppamentoString;
	}
	public String getDitteRaggruppamentoString(){
		return ditteRaggruppamentoString;
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
   public String getPrimaAusiliaria(){
      return ditteAusiliarie != null  && ditteAusiliarie.size() > 0 ? ditteAusiliarie.get(0).getSoggettoPartecipante().getCodiceFiscale() : "";
   }
   public String getPrimoAvvalimento(){
      return ditteAusiliarie != null  && ditteAusiliarie.size() > 0 ? ditteAusiliarie.get(0).getFlagAvvalimentoDecod() : "";
   }

   //!!SM MEV #4113 15-01-2019 Inizio 
	public long getSelezionato() {
		return selezionato;
	}
	public void setSelezionato(long selezionato) {
		this.selezionato = selezionato;
	}
   //!!SM MEV #4113 Fine   
}
