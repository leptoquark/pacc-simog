package it.avlp.simog.beans;

//TICKET ALM #3835
/*********************************************************************************
 * 
 * La classe definisce e gestisce i seguenti attributi :
 * <ul>
 * <li>long : idMisuraPremialeLotto
 * <li>Timestamp : dataInizioCond
 * <li>Timestamp : dataFineCond
 * <li>int : idStato
 * <li>long : idLotto
 * <li>long : idMisuraPremiale
 * </ul>
 * con i relativi metodi di get e set
 * @author Steponweb
 * 
 * [Id_Lotto_Misura_Premiale]
      ,[Id_Lotto]
      ,[Id_Misura_Premiale]
      ,[Data_Inizio_Validita]
      ,[Data_Fine_Validita]
      ,[Data_Ultima_Modifica]
 */
public class MisuraPremialeLottoBean {

	private long idMisuraPremialeLotto;
	private long idLotto; 
	private long idMisuraPremiale;
	private String dataInizioValidita;
	private String dataFineValidita;
	private String dataUltimaModifica;
	public long getIdMisuraPremialeLotto() {
		return idMisuraPremialeLotto;
	}
	public void setIdMisuraPremialeLotto(long idMisuraPremialeLotto) {
		this.idMisuraPremialeLotto = idMisuraPremialeLotto;
	}
	public long getIdLotto() {
		return idLotto;
	}
	public void setIdLotto(long idLotto) {
		this.idLotto = idLotto;
	}
	public long getIdMisuraPremiale() {
		return idMisuraPremiale;
	}
	public void setIdMisuraPremiale(long idMisuraPremiale) {
		this.idMisuraPremiale = idMisuraPremiale;
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
