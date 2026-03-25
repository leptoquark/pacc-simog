/**
 * Web Service TED
 */
package it.anticorruzione.ted.service.impl;

import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;

import it.anticorruzione.ted.beans.ResponseMessageTED;
import it.anticorruzione.ted.db.entity.Lotto;
import it.anticorruzione.ted.db.entity.TedDelta;
import it.anticorruzione.ted.db.repository.LottoRepository;
import it.anticorruzione.ted.db.repository.TedDeltaRepository;
import it.anticorruzione.ted.db.repositoryImp.LottoRepositoryImpl;
import it.anticorruzione.ted.db.repositoryImp.TedDeltaRepositoryImpl;
import it.anticorruzione.ted.service.DeltaLottoTedService;


public class DeltaLottoTedServiceImpl implements DeltaLottoTedService {
	private static final Logger logger = Logger.getLogger(DeltaLottoTedService.class);

	private LottoRepository lottoRepository = new LottoRepositoryImpl();

	private TedDeltaRepository tedDeltaRepository = new TedDeltaRepositoryImpl();

	@Override
	public ResponseMessageTED execute(String cig, String idStazioneAppaltante, String deltaLottoTed, Long noLot) {
		ResponseMessageTED response = new ResponseMessageTED();

		Lotto lotto = lottoRepository.findByCigAndIdStazioneAppaltante(cig, idStazioneAppaltante);

		
			if(lotto.getDataCancellazioneLotto() == null) {
				Calendar calendar = Calendar.getInstance();
				Date dateNow = calendar.getTime();
				Date dataInserimento = dateNow;

				TedDelta tedDeltaOld = tedDeltaRepository.getDeltaLottoValidoByIdLotto(lotto.getIdGara(), lotto.getIdLotto());

				if(tedDeltaOld != null) {
					tedDeltaOld.setDataFineValidita(dateNow);

					tedDeltaRepository.merge(tedDeltaOld);

					dataInserimento = tedDeltaOld.getDataInserimento();
				}

				TedDelta tedDelta = new TedDelta();
				tedDelta.setIdGara(lotto.getIdGara());
				tedDelta.setIdLotto(lotto.getIdLotto());
				tedDelta.setCig(cig);
				tedDelta.setXmlDelta(deltaLottoTed);
				tedDelta.setDataInserimento(dataInserimento);
				tedDelta.setDataInizioValidita(dateNow);
				tedDelta.setNoLot(noLot);

				tedDeltaRepository.persist(tedDelta);

				response.setSuccess(true);
			} else {
				logger.info("cig : " + cig + " - idStazioneAppaltante : " + idStazioneAppaltante + " - dataCancellazioneLotto : " + lotto.getDataCancellazioneLotto() + " - DATA CANCELLAZIONE LOTTO IS NOT NULL");

				response.setSuccess(false);
				response.setError("SERVICE_ERROR_C02c - il lotto e' cancellato");
			}


		return response;
	}
}