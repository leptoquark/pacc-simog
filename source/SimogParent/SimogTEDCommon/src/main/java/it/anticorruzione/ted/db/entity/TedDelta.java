/**
 * Web Service TED
 */
package it.anticorruzione.ted.db.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TED_DELTA")
public class TedDelta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_TED_DELTA")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idTedDelta;

	@Column(name = "ID_GARA")
	private Long idGara;

	@Column(name = "ID_LOTTO")
	private Long idLotto;

	@Column(name = "CIG")
	private String cig;

	@Column(name = "XML_DELTA", columnDefinition= "Text")
	private String xmlDelta;

	@Column(name = "DATA_INSERIMENTO")
	private Date dataInserimento;

	@Column(name = "DATA_CANCELLAZIONE")
	private Date dataCancellazione;

	@Column(name = "DATA_INIZIO_VALIDITA")
	private Date dataInizioValidita;

	@Column(name = "DATA_FINE_VALIDITA")
	private Date dataFineValidita;
	
	@Column(name = "NO_LOT")
	private Long noLot;

	/**
	 * @return the idTedDelta
	 */
	public Long getIdTedDelta() {
		return idTedDelta;
	}

	/**
	 * @param idTedDelta the idTedDelta to set
	 */
	public void setIdTedDelta(Long idTedDelta) {
		this.idTedDelta = idTedDelta;
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
	 * @return the xmlDelta
	 */
	public String getXmlDelta() {
		return xmlDelta;
	}

	/**
	 * @param xmlDelta the xmlDelta to set
	 */
	public void setXmlDelta(String xmlDelta) {
		this.xmlDelta = xmlDelta;
	}

	/**
	 * @return the dataInserimento
	 */
	public Date getDataInserimento() {
		return dataInserimento;
	}

	/**
	 * @param dataInserimento the dataInserimento to set
	 */
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	/**
	 * @return the dataCancellazione
	 */
	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	/**
	 * @param dataCancellazione the dataCancellazione to set
	 */
	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	/**
	 * @return the dataInizioValidita
	 */
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}

	/**
	 * @param dataInizioValidita the dataInizioValidita to set
	 */
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	/**
	 * @return the dataFineValidita
	 */
	public Date getDataFineValidita() {
		return dataFineValidita;
	}

	/**
	 * @param dataFineValidita the dataFineValidita to set
	 */
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public Long getNoLot() {
		return noLot;
	}

	public void setNoLot(Long noLot) {
		this.noLot = noLot;
	}
	
	
}