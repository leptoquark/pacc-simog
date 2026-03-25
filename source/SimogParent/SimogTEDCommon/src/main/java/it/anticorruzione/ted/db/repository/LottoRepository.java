/**
 * Web Service TED
 */
package it.anticorruzione.ted.db.repository;

import java.util.List;

import it.anticorruzione.ted.db.entity.Lotto;

public interface LottoRepository {
	public Lotto findByCigAndIdStazioneAppaltante(String cig, String idStazioneAppaltante);
	
	public List<Lotto> getByIdGara(Long idGara);
	
}