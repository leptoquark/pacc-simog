package it.avlp.simog.beans.aggiudicazione;

import java.sql.Timestamp;

/****************************************************************************
 * La classe definisce e gestisce i seguenti attributi:
 * <ul>
 * <li>long : idContenzioso
 * <li>Timestamp : dataInizioCont
 * <li>String : contenziosoGara
 * <li>String : motivazione
 * <li>String : codiceFiscaleDitta
 * <li>String : dataFineCont
 * <li>int : idStato
 * <li>long : idAggiudicazione
 * <li>Timestamp : dataInizioAggiudicazione
 * </ul>
 * con i relativi metodi di get e set. 
 * @author Steponweb
 *
 */
public class ContenziosoBean {

	private long idContenzioso;
	private Timestamp dataInizioCont; 
	private String contenziosoGara; 
	private String motivazione; 
	private String codiceFiscaleDitta; 
	private String dataFineCont;
	private int idStato;
	private long idAggiudicazione; 
	private Timestamp dataInizioAggiudicazione;
	
	public long getIdContenzioso() {
		return idContenzioso;
	}
	public void setIdContenzioso(long idContenzioso) {
		this.idContenzioso = idContenzioso;
	}
	public Timestamp getDataInizioCont() {
		return dataInizioCont;
	}
	public void setDataInizioCont(Timestamp dataInizioCont) {
		this.dataInizioCont = dataInizioCont;
	}
	public String getContenziosoGara() {
		return contenziosoGara;
	}
	public void setContenziosoGara(String contenziosoGara) {
		this.contenziosoGara = contenziosoGara;
	}
	public String getMotivazione() {
		return motivazione;
	}
	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}
	public String getCodiceFiscaleDitta() {
		return codiceFiscaleDitta;
	}
	public void setCodiceFiscaleDitta(String codiceFiscaleDitta) {
		this.codiceFiscaleDitta = codiceFiscaleDitta;
	}
	public String getDataFineCont() {
		return dataFineCont;
	}
	public void setDataFineCont(String dataFineCont) {
		this.dataFineCont = dataFineCont;
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
