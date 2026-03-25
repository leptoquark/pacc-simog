package it.avlp.simog.beans.variante;

import it.avlp.simog.beans.StatiScheda;
import it.avlp.simog.common.servlet.PSBD;
import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Questo Bean contiene i dati relativi a una ROW della tabella VARIANTI
 * piu una lista di bean EventiMotiviVarianti rappresentante le tabelle correlate
 * 
 * **/
public class VarianteBean {
	/* ref per eventi motivi varianti */
	private long idVariante;
	private Timestamp dataInizioVar;
	/**/
	private String dataVerbaleApprovazione;
	private Timestamp dataFineVar;
	private long idStato;
	private String descrizioneStato;
	/* refs to load many*/
	private long idAggiudicazione;
	private Timestamp dataInizioAggiudicazione;
	/**/
	private BigDecimal impRidetLavori;
	private BigDecimal impRidetServizi;
	private BigDecimal impRidetFornit;
	private BigDecimal impSicurezza;
	private BigDecimal impProgettazione;
	private BigDecimal ulterioriSomme;
	private BigDecimal impDisposizione;
	private String dataAttoAggiuntivo;
	private int numGiorniProroga;
	private String altreMotivazioni;
	private boolean okCancellazione ;
	private String cigProcedura;//TICKET ALM - 3.04.3 PT
	//MEV 34191 3.04.8
	private String linkVarianti;
	
	/* devono essere caricati esplicitamente */
	private List<EventiMotiviVariantiBean> emvb = null;
	
	//MEV MARRA 34469 3.04.8
		private String idMotivoRevPrezzi;	
	//

	private String idLocale;
	
	public String getIdLocale() {
		return idLocale;
	}
	
	public void setIdLocale(String idLocale) {
		this.idLocale = idLocale;
	}
	
	/**************************************************
	 *                GETTER & SETTER
	 **************************************************/
	
	public Long getIdVariante() {
		return idVariante;
	}

	public void setIdVariante(Long idVariante) {
		this.idVariante = idVariante;
	}

	public Timestamp getDataInizioVar() {
		return dataInizioVar;
	}

	public void setDataInizioVar(Timestamp dataInizioVar) {
		this.dataInizioVar = dataInizioVar;
	}

	public Timestamp getDataFineVar() {
		return dataFineVar;
	}

	public void setDataFineVar(Timestamp dataFineVar) {
		this.dataFineVar = dataFineVar;
	}

	public Long getIdStato() {
		return idStato;
	}

	public void setIdStato(Long idStato) {
		this.idStato = idStato;
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

	public BigDecimal getImpRidetLavori() {
		return impRidetLavori;
	}
	public String getImpRidetLavoriStr() {
		return  PageHelper.formattaImporto(impRidetLavori);
	}

	public void setImpRidetLavori(BigDecimal impRidetLavori) {
		this.impRidetLavori = impRidetLavori;
	}

	public BigDecimal getImpRidetServizi() {
		return impRidetServizi;
	}

	public String getImpRidetServiziStr() {
		return  PageHelper.formattaImporto(impRidetServizi);
	}
	
	public void setImpRidetServizi(BigDecimal impRidetServizi) {
		this.impRidetServizi = impRidetServizi;
	}

	public BigDecimal getImpRidetFornit() {
		return impRidetFornit;
	}

	public String getImpRidetFornitStr() {
		return  PageHelper.formattaImporto(impRidetFornit);
	}
	
	public void setImpRidetFornit(BigDecimal impRidetFornit) {
		this.impRidetFornit = impRidetFornit;
	}

	public BigDecimal getImpSicurezza() {
		return impSicurezza;
	}

	public String getImpSicurezzaStr() {
		return  PageHelper.formattaImporto(impSicurezza);
	}
	
	public void setImpSicurezza(BigDecimal impSicurezza) {
		this.impSicurezza = impSicurezza;
	}

	public BigDecimal getImpProgettazione() {
		return impProgettazione;
	}

	public String getImpProgettazioneStr() {
		return  PageHelper.formattaImporto(impProgettazione);
	}
	
	public void setImpProgettazione(BigDecimal impProgettazione) {
		this.impProgettazione = impProgettazione;
	}

	public BigDecimal getUlterioriSomme() {
		return ulterioriSomme;
	}

	public String getUlterioriSommeStr() {
		return  PageHelper.formattaImporto(ulterioriSomme);
	}
	
	public void setUlterioriSomme(BigDecimal ulterioriSomme) {
		this.ulterioriSomme = ulterioriSomme;
	}

	public BigDecimal getImpDisposizione() {
		return impDisposizione;
	}

	public String getImpDisposizioneStr() {
		return  PageHelper.formattaImporto(impDisposizione);
	}
	
	public void setImpDisposizione(BigDecimal impDisposizione) {
		this.impDisposizione = impDisposizione;
	}

	public String getDataAttoAggiuntivo() {
		return dataAttoAggiuntivo;
	}

	public void setDataAttoAggiuntivo(String dataAttoAggiuntivo) {
		this.dataAttoAggiuntivo = dataAttoAggiuntivo;
	}

	public int getNumGiorniProroga() {
		return numGiorniProroga;
	}

	public void setNumGiorniProroga(int numGiorniProroga) {
		this.numGiorniProroga = numGiorniProroga;
	}

	public String getAltreMotivazioni() {
		return altreMotivazioni;
	}

	public void setAltreMotivazioni(String altreMotivazioni) {
		this.altreMotivazioni = altreMotivazioni;
	}

	public List<EventiMotiviVariantiBean> getEmvb() {
		return emvb;
	}

	public void setEmvb(List<EventiMotiviVariantiBean> emvb) {
		this.emvb = emvb;
	}

	public String getDataVerbaleApprovazione() {
		return dataVerbaleApprovazione;
	}

	public void setDataVerbaleApprovazione(String dataVerbaleApprovazione) {
		this.dataVerbaleApprovazione = dataVerbaleApprovazione;
	}
	
	public String getDescrizioneStato() {
		return descrizioneStato;
	}

	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}
	public String getImporto(){
		BigDecimal[] bd =new BigDecimal[]{this.impRidetLavori,this.impRidetServizi,this.impRidetFornit,this.impSicurezza,this.impProgettazione,this.impDisposizione, this.ulterioriSomme};
		return PageHelper.formattaImporto(this.SommaBigDecimal(new BigDecimal(0), bd));
	}
	private BigDecimal SommaBigDecimal(BigDecimal var,BigDecimal[] bd){
		for(int i = 0; i<bd.length; i++){
			var = var.add(bd[i]);
		}return var;
	}

	// ************************************************************************************************
	// *************************************************************************************************
	public boolean isConfirmed(){
		
		return idStato == StatiScheda.CONFERMATO;
		
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

	public boolean isOkCancellazione() {
		return okCancellazione;
	}

	public void setOkCancellazione(boolean okCancellazione) {
		this.okCancellazione = okCancellazione;
	}

	//TICKET ALM - 3.04.3 PT
	public String getCigProcedura() {
		return cigProcedura;
	}

	public void setCigProcedura(String cigProcedura) {
		this.cigProcedura = cigProcedura;
	}

	//MEV 34191 3.04.8
	public String getLinkVarianti() {
		return linkVarianti;
	}

	public void setLinkVarianti(String linkVarianti) {
		this.linkVarianti = linkVarianti;
	}

	//MEV MARRA 34469 3.04.8
	public String getIdMotivoRevPrezzi() {
		return idMotivoRevPrezzi;
	}

	public void setIdMotivoRevPrezzi(String idMotivoRevPrezzi) {
		this.idMotivoRevPrezzi = idMotivoRevPrezzi;
	}

	
	

	
	
}
