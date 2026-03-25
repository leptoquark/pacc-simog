/**
 * Web Service TED
 */
package it.anticorruzione.ted.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.anticorruzione.ted.db.entity.Lotto;
import it.anticorruzione.ted.db.entity.TedDelta;
import it.anticorruzione.ted.db.repository.LottoRepository;
import it.anticorruzione.ted.db.repository.TedDeltaRepository;
import it.anticorruzione.ted.db.repositoryImp.LottoRepositoryImpl;
import it.anticorruzione.ted.db.repositoryImp.TedDeltaRepositoryImpl;
import it.anticorruzione.ted.service.ConsultaDeltaLottoTedService;
import it.anticorruzione.ted.service.DeltaLottoTedService;
import it.anticorruzione.ted.xml.DeltaLotto;
import it.anticorruzione.ted.xml.response.Response;

public class ConsultaDeltaLottoTedServiceImpl implements ConsultaDeltaLottoTedService {
	private static final Logger logger = Logger.getLogger(DeltaLottoTedService.class);

	private LottoRepository lottoRepository = new LottoRepositoryImpl();

	private TedDeltaRepository tedDeltaRepository = new TedDeltaRepositoryImpl();

	@Override
	public Response execute(String cig, String idStazioneAppaltante) {
		Response response = new Response();

		Lotto lotto = lottoRepository.findByCigAndIdStazioneAppaltante(cig, idStazioneAppaltante);

		if(lotto != null) {
			if(lotto.getDataCancellazioneLotto() == null) {
				TedDelta tedDelta = tedDeltaRepository.getDeltaLottoValidoByIdLotto(lotto.getIdGara(), lotto.getIdLotto());
				if(tedDelta != null) {
					List<DeltaLotto> listDeltaLotto = new ArrayList<DeltaLotto>();

					DeltaLotto deltaLotto = new DeltaLotto();
					deltaLotto.setCig(tedDelta.getCig());
					deltaLotto.setDataInserimento(tedDelta.getDataInserimento());
					deltaLotto.setDataInizioValidita(tedDelta.getDataInizioValidita());
					deltaLotto.setDeltaLotto(tedDelta.getXmlDelta());

					listDeltaLotto.add(deltaLotto);

					response.setDeltaLotto(listDeltaLotto);
					response.setSuccess(true);
				} else {
					logger.info("CIG : " + cig + " - idStazioneAppaltante : " + idStazioneAppaltante + " - NOT FOUND");

					response.setSuccess(false);
					response.setError("CIG : " + cig + " - idStazioneAppaltante : " + idStazioneAppaltante + " - DELTA NOT FOUND");
				}
			} else {
				logger.info("CIG : " + cig + " - idStazioneAppaltante : " + idStazioneAppaltante + " - dataCancellazioneLotto : " + lotto.getDataCancellazioneLotto() + " - DATA CANCELLAZIONE LOTTO IS NOT NULL");

				response.setSuccess(false);
				response.setError("CIG : " + cig + " - idStazioneAppaltante : " + idStazioneAppaltante + " - NOT FOUND");
			}
		} else {
			logger.info("CIG : " + cig + " - idStazioneAppaltante : " + idStazioneAppaltante + " - NOT FOUND");

			response.setSuccess(false);
			response.setError("CIG : " + cig + " - idStazioneAppaltante : " + idStazioneAppaltante + " - NOT FOUND");
		}

		return response;
	}
}