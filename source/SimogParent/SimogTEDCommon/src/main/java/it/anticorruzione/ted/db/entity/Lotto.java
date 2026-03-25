/**
 * Web Service TED
 */
package it.anticorruzione.ted.db.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LOTTO")
public class Lotto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_LOTTO")
	private Long idLotto;

	@Column(name = "CIG")
	private String cig;

	@Column(name = "CIG_KKK")
	private String cigKkk;

	@Column(name = "ID_GARA")
	private Long idGara;

	@Column(name = "DATA_CANCELLAZIONE_LOTTO")
	private String dataCancellazioneLotto;

	@Column(name = "ID_SCELTA_CONTRAENTE")
	private String idSceltaContraente;
	
	@Column(name = "FLAG_PREVEDE_RIP")
	private String flagPrevedeRip;
	
	/**
	 * @return the idLotto
	 */
	public Long getIdLotto() {
		return idLotto;
	}

	/**
	 * @param idLotto the idLotto to set
	 */
	public void setIdLotto(Long idLotto) {
		this.idLotto = idLotto;
	}

	/**
	 * @return the cig
	 */
	public String getCig() {
		return cig;
	}

	/**
	 * @param cig the cig to set
	 */
	public void setCig(String cig) {
		this.cig = cig;
	}

	/**
	 * @return the cigKkk
	 */
	public String getCigKkk() {
		return cigKkk;
	}

	/**
	 * @param cigKkk the cigKkk to set
	 */
	public void setCigKkk(String cigKkk) {
		this.cigKkk = cigKkk;
	}

	/**
	 * @return the idGara
	 */
	public Long getIdGara() {
		return idGara;
	}

	/**
	 * @param idGara the idGara to set
	 */
	public void setIdGara(Long idGara) {
		this.idGara = idGara;
	}

	/**
	 * @return the dataCancellazioneLotto
	 */
	public String getDataCancellazioneLotto() {
		return dataCancellazioneLotto;
	}

	/**
	 * @param dataCancellazioneLotto the dataCancellazioneLotto to set
	 */
	public void setDataCancellazioneLotto(String dataCancellazioneLotto) {
		this.dataCancellazioneLotto = dataCancellazioneLotto;
	}

	public String getIdSceltaContraente() {
		return idSceltaContraente;
	}

	public void setIdSceltaContraente(String idSceltaContraente) {
		this.idSceltaContraente = idSceltaContraente;
	}

	public String getFlagPrevedeRip() {
		return flagPrevedeRip;
	}

	public void setFlagPrevedeRip(String flagPrevedeRip) {
		this.flagPrevedeRip = flagPrevedeRip;
	}
	
	
}