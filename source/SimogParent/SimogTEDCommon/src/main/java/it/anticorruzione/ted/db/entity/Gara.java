/**
 * Web Service TED
 */
package it.anticorruzione.ted.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GARA")
public class Gara implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_GARA")
	private Long idGara;

	@Column(name = "ID_STAZIONE_APPALTANTE")
	private String idStazioneAppaltante;

	@Column(name = "DATA_CANCELLAZIONE_GARA")
	private String dataCancellazioneGara;

	@Column(name = "DATA_PERFEZIONAMENTO_BANDO")
	private String dataPerfezionamentoBando;
	
	@Column(name = "ID_MODO_REAL")
	private Long idModoReal;
	
	@Column(name = "ID_SVOLGIMENTO")
	private Long idSvolgimento;
	
	@Column(name = "NUMERO_LOTTI")
	private Long numeroLotti;
	
	@Column(name = "ID_PUBBLICAZIONE")
	private Long idPubblicazione;
	
	@Column(name = "DATA_INIZIO_PUBB")
	private Date dataInizioPubb;

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
	 * @return the idStazioneAppaltante
	 */
	public String getIdStazioneAppaltante() {
		return idStazioneAppaltante;
	}

	/**
	 * @param idStazioneAppaltante the idStazioneAppaltante to set
	 */
	public void setIdStazioneAppaltante(String idStazioneAppaltante) {
		this.idStazioneAppaltante = idStazioneAppaltante;
	}

	/**
	 * @return the dataCancellazioneGara
	 */
	public String getDataCancellazioneGara() {
		return dataCancellazioneGara;
	}

	/**
	 * @param dataCancellazioneGara the dataCancellazioneGara to set
	 */
	public void setDataCancellazioneGara(String dataCancellazioneGara) {
		this.dataCancellazioneGara = dataCancellazioneGara;
	}

	/**
	 * @return the dataPerfezionamentoBando
	 */
	public String getDataPerfezionamentoBando() {
		return dataPerfezionamentoBando;
	}

	/**
	 * @param dataPerfezionamentoBando the dataPerfezionamentoBando to set
	 */
	public void setDataPerfezionamentoBando(String dataPerfezionamentoBando) {
		this.dataPerfezionamentoBando = dataPerfezionamentoBando;
	}

	public Long getIdModoReal() {
		return idModoReal;
	}

	public void setIdModoReal(Long idModoReal) {
		this.idModoReal = idModoReal;
	}

	public Long getIdSvolgimento() {
		return idSvolgimento;
	}

	public void setIdSvolgimento(Long idSvolgimento) {
		this.idSvolgimento = idSvolgimento;
	}

	public Long getNumeroLotti() {
		return numeroLotti;
	}

	public void setNumeroLotti(Long numeroLotti) {
		this.numeroLotti = numeroLotti;
	}

	public Long getIdPubblicazione() {
		return idPubblicazione;
	}

	public void setIdPubblicazione(Long idPubblicazione) {
		this.idPubblicazione = idPubblicazione;
	}

	public Date getDataInizioPubb() {
		return dataInizioPubb;
	}

	public void setDataInizioPubb(Date dataInizioPubb) {
		this.dataInizioPubb = dataInizioPubb;
	}
	
	
}