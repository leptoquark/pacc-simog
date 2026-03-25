package it.anticorruzione.ted.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.anticorruzione.ted.enums.StatusNoticeEnum;

@Entity
@Table(name = "TED_STATUS")
public class TEDStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_TED_STATUS")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idTedStatus;
	
	@Column(name = "ID_TED_NOTICE")
	private Long idTedNotice;
	
	@Column(name = "ID_TED_TYPE_STATUS")
	private Long idTedTypeStatus;
	
	@Column(name = "JSON_RESPONSE", columnDefinition= "Text")
	private String jsonResponse;
	
	@Column(name = "UPDATE_DATA")
	private Date updateData;
	
	@Column(name = "CANCEL_BY_USER")
	private Boolean cancelByUser;

	public Long getIdTedStatus() {
		return idTedStatus;
	}

	public void setIdTedStatus(Long idTedStatus) {
		this.idTedStatus = idTedStatus;
	}

	public Long getIdTedNotice() {
		return idTedNotice;
	}

	public void setIdTedNotice(Long idTedNotice) {
		this.idTedNotice = idTedNotice;
	}

	public Long getIdTedTypeStatus() {
		return idTedTypeStatus;
	}

	public void setIdTedTypeStatus(long idTedTypeStatus) {
		this.idTedTypeStatus = idTedTypeStatus;
	}

	public String getJsonResponse() {
		return jsonResponse;
	}

	public void setJsonResponse(String jsonResponse) {
		this.jsonResponse = jsonResponse;
	}

	public Date getUpdateData() {
		return updateData;
	}

	public void setUpdateData(Date updateData) {
		this.updateData = updateData;
	}
	
	
	
	public Boolean isCancelByUser() {
		return cancelByUser;
	}

	public void setCancelByUser(Boolean cancelByUser) {
		this.cancelByUser = cancelByUser;
	}

	public StatusNoticeEnum getStatusNoticeEnum() {
		return StatusNoticeEnum.findStatusById(idTedTypeStatus.intValue());
	}
	
}
