package it.avlp.simog.beans.inizio;

import it.avlp.simog.beans.VO;
import it.avlp.simog.beans.aggiudicazione.SoggettoPartecipanteBean;

import java.sql.Timestamp;

public class PosizioneAggiudicatarioBean implements VO{
	

	private long idInizioLavori;
	private Timestamp dataInizioLavori;
	
	private String codiceINPS;
	private String codiceINAIL;
	private String codiceCassa;
	private String codiceStato;
	private Long idStato;
	private SoggettoPartecipanteBean soggettoPartecipante;
	
	public Long getIdStato() {
		return idStato;
	}
	public void setIdStato(Long idStato) {
		this.idStato = idStato;
	}
	
	public String getCodiceCassa() {
		return codiceCassa;
	}
	public void setCodiceCassa(String codiceCassa) {
		this.codiceCassa = codiceCassa;
	}
	public String getCodiceINAIL() {
		return codiceINAIL;
	}
	public void setCodiceINAIL(String codiceINAIL) {
		this.codiceINAIL = codiceINAIL;
	}
	public String getCodiceINPS() {
		return codiceINPS;
	}
	public void setCodiceINPS(String codiceINPS) {
		this.codiceINPS = codiceINPS;
	}
	public Timestamp getDataInizioLavori() {
		return dataInizioLavori;
	}
	public void setDataInizioLavori(Timestamp dataInizioLavori) {
		this.dataInizioLavori = dataInizioLavori;
	}

	public long getIdInizioLavori() {
		return idInizioLavori;
	}
	public void setIdInizioLavori(long idInizioLavori) {
		this.idInizioLavori = idInizioLavori;
	}
	
	public SoggettoPartecipanteBean getSoggettoPartecipante() {
		return soggettoPartecipante;
	}
	public void setSoggettoPartecipante(SoggettoPartecipanteBean soggettoPartecipante) {
		this.soggettoPartecipante = soggettoPartecipante;
	}
	public String getCodiceStato() {
		return codiceStato;
	}
	public void setCodiceStato(String codiceStato) {
		this.codiceStato = codiceStato;
	}
	

}
