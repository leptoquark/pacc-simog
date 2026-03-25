package it.avlp.simog.beans.aggiudicazione;

import java.sql.Timestamp;

/*********************************************************************************
 * 
 * La classe definisce e gestisce i seguenti attributi :
 * <ul>
 * <li>long : idCondizioneAgg
 * <li>Timestamp : dataInizioCond
 * <li>Timestamp : dataFineCond
 * <li>int : idStato
 * <li>long : idAggiudicazione
 * <li>Timestamp  :dataInizioAggiudicazione
 * <li>long : idCondizione
 * </ul>
 * con i relativi metodi di get e set
 * @author Steponweb
 */
public class CondizioneAggBean {

	private long idCondizioneAgg;
	private Timestamp dataInizioCond; 
	private Timestamp dataFineCond;
	private int idStato;
	private long idAggiudicazione; 
	private Timestamp dataInizioAggiudicazione;
	private long idCondizione;
	
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
	public int getIdStato() {
		return idStato;
	}
	public void setIdStato(int idStato) {
		this.idStato = idStato;
	}
	public Timestamp getDataFineCond() {
		return dataFineCond;
	}
	public void setDataFineCond(Timestamp dataFineCond) {
		this.dataFineCond = dataFineCond;
	}
	public Timestamp getDataInizioCond() {
		return dataInizioCond;
	}
	public void setDataInizioCond(Timestamp dataInizioCond) {
		this.dataInizioCond = dataInizioCond;
	}
	public long getIdCondizioneAgg() {
		return idCondizioneAgg;
	}
	public void setIdCondizioneAgg(long idCondizioneAgg) {
		this.idCondizioneAgg = idCondizioneAgg;
	}
	public long getIdCondizione() {
		return idCondizione;
	}
	public void setIdCondizione(long idCondizione) {
		this.idCondizione = idCondizione;
	}
}
