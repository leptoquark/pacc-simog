/**
 * Web Service TED
 */
package it.anticorruzione.ted.service.impl;

import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;

import it.anticorruzione.ted.beans.ResponseMessageTED;
import it.anticorruzione.ted.db.entity.Gara;
import it.anticorruzione.ted.db.entity.Lotto;
import it.anticorruzione.ted.db.entity.TedDelta;
import it.anticorruzione.ted.db.repository.GaraRepository;
import it.anticorruzione.ted.db.repository.LottoRepository;
import it.anticorruzione.ted.db.repository.TedDeltaRepository;
import it.anticorruzione.ted.db.repositoryImp.GaraRepositoryImpl;
import it.anticorruzione.ted.db.repositoryImp.LottoRepositoryImpl;
import it.anticorruzione.ted.db.repositoryImp.TedDeltaRepositoryImpl;
import it.anticorruzione.ted.service.CancellaDeltaLottoTedService;


public class CancellaDeltaLottoTedServiceImpl implements CancellaDeltaLottoTedService {
	private static final Logger logger = Logger.getLogger(CancellaDeltaLottoTedService.class);

	private LottoRepository lottoRepository = new LottoRepositoryImpl();

	private GaraRepository garaRepository = new GaraRepositoryImpl();

	private TedDeltaRepository tedDeltaRepository = new TedDeltaRepositoryImpl();

	@Override
	public ResponseMessageTED execute(String cig, String idStazioneAppaltante) {
		ResponseMessageTED response = new ResponseMessageTED();

		Lotto lotto = lottoRepository.findByCigAndIdStazioneAppaltante(cig, idStazioneAppaltante);

		if(lotto != null) {
			Gara gara = garaRepository.findByIdGaraAndIdStazioneAppaltante(lotto.getIdGara(), idStazioneAppaltante);

			if(gara != null) {
				if(gara.getDataPerfezionamentoBando() == null) {
					TedDelta tedDelta = tedDeltaRepository.getDeltaLottoNonCancellato(lotto.getIdGara(), lotto.getIdLotto());

					if(tedDelta != null) {
						Calendar calendar = Calendar.getInstance();
						Date dateNow = calendar.getTime();

						tedDelta.setDataCancellazione(dateNow);
						tedDelta.setDataFineValidita(dateNow);

						Boolean success = tedDeltaRepository.merge(tedDelta);

						response.setSuccess(success);
					}
				} else {
					logger.info("CIG : " + cig + " - idStazioneAppaltante : " + idStazioneAppaltante + " - dataPerfezionamentoBando : " + gara.getDataPerfezionamentoBando() + " - DATA PERFEZIONAMENTO GARA IS NOT NULL");

					response.setSuccess(false);
					response.setError("CIG : " + cig + " - idStazioneAppaltante : " + idStazioneAppaltante + " - GARA PERFEZIONATA");
				}
			} else {

			}
		} else {
			logger.info("CIG : " + cig + " - idStazioneAppaltante : " + idStazioneAppaltante + " - NOT FOUND");

			response.setSuccess(false);
			response.setError("CIG : " + cig + " - idStazioneAppaltante : " + idStazioneAppaltante + " - NOT FOUND");
		}

		return response;
	}
}