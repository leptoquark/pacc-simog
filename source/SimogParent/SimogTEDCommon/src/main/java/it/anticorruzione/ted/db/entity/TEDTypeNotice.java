package it.anticorruzione.ted.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TED_TYPE_NOTICE")
public class TEDTypeNotice {

	@Id
	@Column(name = "ID_TED_TYPE_NOTICE")
	private Long idTedTypeNotice;
	
	@Column(name = "COD_TYPE_NOTICE")
	private String codTypeNotice;

	public Long getIdTedTypeNotice() {
		return idTedTypeNotice;
	}

	public void setIdTedTypeNotice(Long idTedTypeNotice) {
		this.idTedTypeNotice = idTedTypeNotice;
	}

	public String getCodTypeNotice() {
		return codTypeNotice;
	}

	public void setCodTypeNotice(String codTypeNotice) {
		this.codTypeNotice = codTypeNotice;
	}
	
	
}
