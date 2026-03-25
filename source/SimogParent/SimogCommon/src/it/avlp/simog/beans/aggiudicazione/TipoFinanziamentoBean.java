package it.avlp.simog.beans.aggiudicazione;

import it.avlp.simog.util.PageHelper;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**********************************************************************************
 * <code>public class <b>TipoFinanziamentoBean</b></code><br><br>
 * La classe si occupa di gestire e definire i parametri seguenti per la 
 * modellazione del tipo di finanziamento:
 * <ul>
 * <li> String idFinanziamento;
 *	<li>Timestamp dataInizioFin; 
 *	<li>Timestamp dataFineFin;
 *	<li>String descrizione;
 *	<li>BigDecimal importo;
 *	<li>int idStato;
 *	<li>long idAggiudicazione; 
 *	<li>Timestamp dataInizioAggiudicazione;
 * </ul>
 * Attraverso i relativi metodi di get e set
 * @author Steponweb
 *
 */
public class TipoFinanziamentoBean {
	private String idFinanziamento;
	private Timestamp dataInizioFin; 
	private Timestamp dataFineFin;
	private String descrizione;
	private BigDecimal importo;
	private int idStato;
	private long idAggiudicazione; 
	private Timestamp dataInizioAggiudicazione;
	
	public String getIdFinanziamento() {
		return idFinanziamento;
	}
	public void setIdFinanziamento(String idFinanziamento) {
		this.idFinanziamento = idFinanziamento;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public String getImportoStr() {
		return PageHelper.formattaImporto(importo);
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public Timestamp getDataInizioFin() {
		return dataInizioFin;
	}
	public void setDataInizioFin(Timestamp dataInizioFin) {
		this.dataInizioFin = dataInizioFin;
	}
	public Timestamp getDataFineFin() {
		return dataFineFin;
	}
	public void setDataFineFin(Timestamp dataFineFin) {
		this.dataFineFin = dataFineFin;
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
}