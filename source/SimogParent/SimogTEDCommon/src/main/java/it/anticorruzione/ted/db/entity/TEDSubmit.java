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
@Table(name = "TED_SUBMIT")
public class TEDSubmit implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_TED_SUBMIT")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idTedSubmit;
	
	@Column(name = "ID_TED_NOTICE")
	private Long idTedNotice;
	
	@Column(name = "XML_REQUEST", columnDefinition= "Text")
	private String xmlRequest;
	
	@Column(name = "DATA_REQUEST")
	private Date dataRequest;


	public Long getIdTedSubmit() {
		return idTedSubmit;
	}

	public void setIdTedSubmit(Long idTedSubmit) {
		this.idTedSubmit = idTedSubmit;
	}

	public Long getIdTedNotice() {
		return idTedNotice;
	}

	public void setIdTedNotice(Long idTedNotice) {
		this.idTedNotice = idTedNotice;
	}

	public String getXmlRequest() {
		return xmlRequest;
	}

	public void setXmlRequest(String xmlRequest) {
		this.xmlRequest = xmlRequest;
	}

	public Date getDataRequest() {
		return dataRequest;
	}

	public void setDataRequest(Date dataRequest) {
		this.dataRequest = dataRequest;
	}	
	
}
