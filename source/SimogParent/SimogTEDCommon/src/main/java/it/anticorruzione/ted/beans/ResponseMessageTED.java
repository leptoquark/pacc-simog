package it.anticorruzione.ted.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ResponseData")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseMessageTED {

	
	@XmlElement
	public boolean success;
	@XmlElement
	private String status;
	@XmlElement
	private String error;
	@XmlElement
	private String status_msg="";
	@XmlElement
	private String no_doc_ext;
	@XmlElement
	private String data_received;
	@XmlElement
	private String no_doc_ojs;
	@XmlElement
	private String publication_date;
	@XmlElement
	private String ted_link;
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus_msg() {
		return status_msg;
	}
	public void setStatus_msg(String status_msg) {
		this.status_msg = status_msg;
	}
	public String getNo_doc_ext() {
		return no_doc_ext;
	}
	public void setNo_doc_ext(String no_doc_ext) {
		this.no_doc_ext = no_doc_ext;
	}
	public String getData_received() {
		return data_received;
	}
	public void setData_received(String data_received) {
		this.data_received = data_received;
	}
	public String getNo_doc_ojs() {
		return no_doc_ojs;
	}
	public void setNo_doc_ojs(String no_doc_ojs) {
		this.no_doc_ojs = no_doc_ojs;
	}
	public String getPublication_date() {
		return publication_date;
	}
	public void setPublication_date(String publication_date) {
		this.publication_date = publication_date;
	}
	public String getTed_link() {
		return ted_link;
	}
	public void setTed_link(String ted_link) {
		this.ted_link = ted_link;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
	
	
}
