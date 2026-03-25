/**
 * Web Service TED
 */
package it.anticorruzione.ted.db.repository;

import java.util.List;

import it.anticorruzione.ted.db.entity.TedDelta;


public interface TedDeltaRepository {
	public TedDelta getDeltaGaraValido(Long idGara);
	public TedDelta getDeltaLottoValidoByIdLotto(Long idGara, Long idLotto);
	public TedDelta getDeltaGaraNonCancellato(Long idGara);
	public TedDelta getDeltaLottoNonCancellato(Long idGara, Long idLotto);
	public List<TedDelta> getListaDetaLottoByIdGara(Long idGara);
	public Boolean merge(TedDelta tedDelta);
	public Boolean persist(TedDelta tedDelta);
	public TedDelta getDeltaLottoValidoByCIG(String cig);
}