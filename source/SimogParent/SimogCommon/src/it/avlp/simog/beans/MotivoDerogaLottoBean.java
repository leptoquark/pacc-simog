package it.avlp.simog.beans;

//TICKET ALM #3835
/*********************************************************************************
 * 
 * La classe definisce e gestisce i seguenti attributi :
 * <ul>
 * <li>long : idMotivoDerogaLotto
 * <li>Timestamp : dataInizioCond
 * <li>Timestamp : dataFineCond
 * <li>int : idStato
 * <li>long : idLotto
 * <li>long : idMotivoDeroga
 * </ul>
 * con i relativi metodi di get e set
 * @author Steponweb
 */
public class MotivoDerogaLottoBean {
	
	private long idMotivoDerogaLotto;
	private long idLotto; 
	private long idMotivoDeroga;
	private String dataInizioValidita; 
	private String dataFineValidita;
	private String dataUltimaModifica;
	public long getIdMotivoDerogaLotto() {
		return idMotivoDerogaLotto;
	}
	public void setIdMotivoDerogaLotto(long idMotivoDerogaLotto) {
		this.idMotivoDerogaLotto = idMotivoDerogaLotto;
	}
	public long getIdLotto() {
		return idLotto;
	}
	public void setIdLotto(long idLotto) {
		this.idLotto = idLotto;
	}
	public long getIdMotivoDeroga() {
		return idMotivoDeroga;
	}
	public void setIdMotivoDeroga(long idMotivoDeroga) {
		this.idMotivoDeroga = idMotivoDeroga;
	}
	public String getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(String dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	public String getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(String dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	public String getDataUltimaModifica() {
		return dataUltimaModifica;
	}
	public void setDataUltimaModifica(String dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}
}
