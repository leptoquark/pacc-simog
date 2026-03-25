package it.avlp.simog.beans.aggiudicazione;

import it.avlp.simog.db.SimogFlags;

import java.sql.Timestamp;

/******************************************************************************
 * La classe definisce e gestisce i seguenti attributi per la modellazione di Tipo Appalto Aggiudicazione
 * <ul>
 * <li>Stringa final static : COMPONENTE_LAVORI
 * <li>Stringa final static : COMPONENTE_FS
 * <li>Stringa final static : ENTE_ORDINARI
 * <li>Stringa final static : ENTE_SPECIALE
 * </ul>
 * <ul>
 * <li>long : idTipoAppAgg
 * <li>Timestamp : dataInizioTipApp
 * <li>Timestamp : dataFineTipApp
 * <li>int : idStato
 * <li>long : idAggiudicazione
 * <li>Timestamp : dataInizioAggiudicazione
 * <li>long : idAppalto
 * </ul>
 * Con i relativi metodi di get e set
 * @author Steponweb
 *
 */
public class TipoAppaltoAggBean {

	private long idTipoAppAgg;
	private Timestamp dataInizioTipApp; 
	private Timestamp dataFineTipApp;
	private int idStato;
	private long idAggiudicazione; 
	private Timestamp dataInizioAggiudicazione;
	private long idAppalto;
   
	//is3031_RFWEBGL00Active
	private long idLotto; 

	
	public TipoAppaltoAggBean(String idappalto2) {
	   this.idAppalto = Long.valueOf(idappalto2);
   }
   public TipoAppaltoAggBean() {}
   
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
	public Timestamp getDataFineTipApp() {
		return dataFineTipApp;
	}
	public void setDataFineTipApp(Timestamp dataFineTipApp) {
		this.dataFineTipApp = dataFineTipApp;
	}
	public Timestamp getDataInizioTipApp() {
		return dataInizioTipApp;
	}
	public void setDataInizioTipApp(Timestamp dataInizioTipApp) {
		this.dataInizioTipApp = dataInizioTipApp;
	}
	public long getIdAppalto() {
		return idAppalto;
	}
	public void setIdAppalto(long idAppalto) {
		this.idAppalto = idAppalto;
	}
	public long getIdTipoAppAgg() {
		return idTipoAppAgg;
	}
	public void setIdTipoAppAgg(long idTipoAppAgg) {
		this.idTipoAppAgg = idTipoAppAgg;
	}
   public long getIdLotto() {
      return idLotto;
   }
   public void setIdLotto(long idLotto) {
      this.idLotto = idLotto;
   }
}
