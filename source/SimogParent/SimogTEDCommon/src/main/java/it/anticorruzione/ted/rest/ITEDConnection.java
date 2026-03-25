package it.anticorruzione.ted.rest;


import it.anticorruzione.ted.json.TEDNoticeInformation;
import it.anticorruzione.ted.exception.TEDErrorException;

public interface ITEDConnection {

	public TEDNoticeInformation getNotice(String submission_id);
	public TEDNoticeInformation submit(String encodedNotice) throws TEDErrorException;	
	public boolean stopPublication(String submission_id) throws TEDErrorException, Exception;
}
