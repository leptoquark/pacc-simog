package it.anticorruzione.ted.db.repository;

import it.anticorruzione.ted.db.entity.TEDSubmit;

public interface TEDSubmitRepository {

	public boolean insertSubmit(TEDSubmit submit);
	public TEDSubmit getLastSubmit(Long idTedNotice);
}
