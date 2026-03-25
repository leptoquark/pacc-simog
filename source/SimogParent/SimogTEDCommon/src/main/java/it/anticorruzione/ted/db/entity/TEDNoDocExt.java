package it.anticorruzione.ted.db.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TED_NO_DOC_EXT")
public class TEDNoDocExt implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "YEAR_NO")
	private String year;
	
	@Column(name = "COUNT_NO")
	private Long countNo;

	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Long getCountNo() {
		return countNo;
	}

	public void setCountNo(Long countNo) {
		this.countNo = countNo;
	}
	
	public void incrementCount() {
		this.countNo++;
	}
	

	public String getStringCount() {
		return String.format("%06d", this.countNo);
	}
}
