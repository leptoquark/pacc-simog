/**
 * Web Service TED
 */
package it.anticorruzione.ted.db.repository;

import it.anticorruzione.ted.db.entity.Gara;

public interface GaraRepository {
	public Gara findByIdGaraAndIdStazioneAppaltante(Long idGara, String idStazioneAppaltante);

}