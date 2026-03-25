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
@Table(name = "TED_NOTICE")
public class TEDNotice implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_TED_NOTICE")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idTedNotice;
	
	@Column(name = "ID_GARA")
	private long idGara;
	
	@Column(name = "ID_LOTTO")
	private long idLotto;
	
	@Column(name = "NO_DOC_EXT")
	private String noDocExt;
	
	@Column(name = "SUBMISSION_ID")
	private String submissionId;
	
	@Column(name = "RECEIVED_AT")
	private Date receivedAt;

	@Column(name = "NO_DOC_OJS")
	private String noDocOjs;
	
	@Column(name = "PUBLISHED_AT")
	private Date publishedAt;
	
	@Column(name = "TED_LINK")
	private String tedLink;
	
	@Column(name = "ID_TED_TYPE_NOTICE")
	private Long idTedTypeNotice;

	public Long getIdTedNotice() {
		return idTedNotice;
	}

	public void setIdTedNotice(Long idTedNotice) {
		this.idTedNotice = idTedNotice;
	}

	public long getIdGara() {
		return idGara;
	}

	public void setIdGara(long idGara) {
		this.idGara = idGara;
	}

	public long getIdLotto() {
		return idLotto;
	}

	public void setIdLotto(long idLotto) {
		this.idLotto = idLotto;
	}

	public String getNoDocExt() {
		return noDocExt;
	}

	public void setNoDocExt(String noDocExt) {
		this.noDocExt = noDocExt;
	}

	public String getNoDocOjs() {
		return noDocOjs;
	}

	public void setNoDocOjs(String noDocOjs) {
		this.noDocOjs = noDocOjs;
	}

	public Long getIdTedTypeNotice() {
		return idTedTypeNotice;
	}

	public void setIdTedTypeNotice(Long idTedTypeNotice) {
		this.idTedTypeNotice = idTedTypeNotice;
	}
	
	
	public String getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(String submissionId) {
		this.submissionId = submissionId;
	}

	public Date getReceivedAt() {
		return receivedAt;
	}

	public void setReceivedAt(Date receivedAt) {
		this.receivedAt = receivedAt;
	}

	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getTedLink() {
		return tedLink;
	}

	public void setTedLink(String tedLink) {
		this.tedLink = tedLink;
	}
	
}
