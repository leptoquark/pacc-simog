package it.avlp.simog.beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class DelegaDatiSimog implements Serializable {
	private String idOsservatorio;
	private String descrizione;
	private String delegaCig;
	private Timestamp delegaCigDal;
	private String delegaSchede;
	private Timestamp delegaSchedeDal;
	private String urlSistema;
	private Timestamp dataFineValidita;
	private Timestamp dataUltimaModifica;

	public Timestamp getDataUltimaModifica() {
		return dataUltimaModifica;
	}

	public void setDataUltimaModifica(Timestamp dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}

	public String getIdOsservatorio() {
		return idOsservatorio;
	}

	public void setIdOsservatorio(String idOsservatorio) {
		this.idOsservatorio = idOsservatorio;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDelegaCig() {
		return delegaCig;
	}

	public void setDelegaCig(String delegaCig) {
		this.delegaCig = delegaCig;
	}

	public Timestamp getDelegaCigDal() {
		return delegaCigDal;
	}

	public void setDelegaCigDal(Timestamp delegaCigDal) {
		this.delegaCigDal = delegaCigDal;
	}

	public String getDelegaSchede() {
		return delegaSchede;
	}

	public void setDelegaSchede(String delegaSchede) {
		this.delegaSchede = delegaSchede;
	}

	public Timestamp getDelegaSchedeDal() {
		return delegaSchedeDal;
	}

	public void setDelegaSchedeDal(Timestamp delegaSchedeDal) {
		this.delegaSchedeDal = delegaSchedeDal;
	}

	public String getUrlSistema() {
		return urlSistema;
	}

	public void setUrlSistema(String urlSistema) {
		this.urlSistema = urlSistema;
	}

	public Timestamp getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Timestamp dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

}
