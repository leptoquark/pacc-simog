package it.avlp.simog.beans;

	/**
	 * Ogni oggetto di questa classe viene utilizzato
	 * per la memorizzazione temporanea e la successiva
	 * archiviazione delle informazioni relative ad una gara
	 * ed ai lotti relativi
	 */

public class CpvEu implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String idDiv = null;
	private String idGrp = null;
	private String idCls = null;
	private String idCtg = null;
	private String idVox = null;
	private String check = null;
	private String descr = null;
	private String versione = null;
	
	private String dataInizioValidita = null;
	private String dataUltimaModifica = null;
	
	public CpvEu(String idDiv, String idGrp, String idCls, String idCtg, String idVox, String check, String descr, String dataInizioValidita, String dataUltimaModifica, String versione) {
		this.idDiv = idDiv;
		this.idCtg = idCtg;
		this.idCls = idCls;
		this.idGrp = idGrp;
		this.idVox = idVox;
		this.check = check;
		this.descr = descr;
		this.dataInizioValidita = dataInizioValidita;
		this.dataUltimaModifica = dataUltimaModifica;
		this.versione = versione;
	}

	public CpvEu() {
	}

	public String getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(String dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public String getDataUltimaModifica() {
		return dataUltimaModifica;
	}

	public void setDataUltimaModifica(String dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getIdCls() {
		return idCls;
	}

	public void setIdCls(String idCls) {
		this.idCls = idCls;
	}

	public String getIdCtg() {
		return idCtg;
	}

	public void setIdCtg(String idCtg) {
		this.idCtg = idCtg;
	}

	public String getIdDiv() {
		return idDiv;
	}

	public void setIdDiv(String idDiv) {
		this.idDiv = idDiv;
	}

	public String getIdGrp() {
		return idGrp;
	}

	public void setIdGrp(String idGrp) {
		this.idGrp = idGrp;
	}

	public String getIdVox() {
		return idVox;
	}

	public void setIdVox(String idVox) {
		this.idVox = idVox;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getVersione() {
		return versione;
	}

	public void setVersione(String versione) {
		this.versione = versione;
	}


}

