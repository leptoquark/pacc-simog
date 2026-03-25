package it.avlp.simog.beans;

import java.sql.Timestamp;
//TICKET ALM #3835
/*********************************************************************************
 * 
 * La classe definisce e gestisce i seguenti attributi :
 * <ul>
 * <li>long : idCondizioneLotto
 * <li>Timestamp : dataInizioCond
 * <li>Timestamp : dataFineCond
 * <li>int : idStato
 * <li>long : idLotto
 * <li>long : idCondizione
 * </ul>
 * con i relativi metodi di get e set
 * @author Steponweb
 */
public class CondizioneLottoBean {

	private long idCondizioneLotto;
	private Timestamp dataInizioCond; 
	private Timestamp dataFineCond;
	private int idStato;
	private long idLotto; 
	private long idCondizione;
	
	public long getIdLotto() {
		return idLotto;
	}
	public void setIdLotto(long idLotto) {
		this.idLotto = idLotto;
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
	public long getIdCondizioneLotto() {
		return idCondizioneLotto;
	}
	public void setIdCondizioneLotto(long idCondizioneLotto) {
		this.idCondizioneLotto = idCondizioneLotto;
	}
	public long getIdCondizione() {
		return idCondizione;
	}
	public void setIdCondizione(long idCondizione) {
		this.idCondizione = idCondizione;
	}
}
