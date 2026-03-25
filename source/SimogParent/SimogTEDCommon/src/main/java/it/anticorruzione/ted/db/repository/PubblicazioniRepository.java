package it.anticorruzione.ted.db.repository;

import java.util.Date;

import it.anticorruzione.ted.db.entity.Pubblicazioni;

public interface PubblicazioniRepository {

	public Pubblicazioni find(Long idPub, Date dataInizioPub); 
	public Boolean merge(Pubblicazioni pub);
	
}
