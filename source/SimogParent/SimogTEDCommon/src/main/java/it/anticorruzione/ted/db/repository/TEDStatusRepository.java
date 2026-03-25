package it.anticorruzione.ted.db.repository;

import it.anticorruzione.ted.db.entity.TEDStatus;

public interface TEDStatusRepository {

	public TEDStatus getLastNoticeStatus(Long idTedNotice);
	public boolean saveTEDStatus(TEDStatus status);
	
	
}
