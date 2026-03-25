/**
 * Web Service TED
 */
package it.anticorruzione.ted.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import it.anticorruzione.ted.beans.ResponseMessageTED;
import it.anticorruzione.ted.db.entity.Gara;
import it.anticorruzione.ted.db.entity.TedDelta;
import it.anticorruzione.ted.db.repository.GaraRepository;
import it.anticorruzione.ted.db.repository.TedDeltaRepository;
import it.anticorruzione.ted.db.repositoryImp.GaraRepositoryImpl;
import it.anticorruzione.ted.db.repositoryImp.TedDeltaRepositoryImpl;
import it.anticorruzione.ted.service.CancellaDeltaGaraTedService;


public class CancellaDeltaGaraTedServiceImpl implements CancellaDeltaGaraTedService {
	private static final Logger logger = Logger.getLogger(CancellaDeltaGaraTedService.class);

	private GaraRepository garaRepository = new GaraRepositoryImpl();

	private TedDeltaRepository tedDeltaRepository = new TedDeltaRepositoryImpl();

	@Override
	public ResponseMessageTED execute(Long idGara, String idStazioneAppaltante) {
		ResponseMessageTED response = new ResponseMessageTED();

		Gara gara = garaRepository.findByIdGaraAndIdStazioneAppaltante(idGara, idStazioneAppaltante);

		if(gara != null) {
			if(gara.getDataPerfezionamentoBando() == null) {
				TedDelta tedDelta = tedDeltaRepository.getDeltaGaraNonCancellato(idGara);

				if(tedDelta != null) {
					Calendar calendar = Calendar.getInstance();
					Date dateNow = calendar.getTime();

					tedDelta.setDataCancellazione(dateNow);
					tedDelta.setDataFineValidita(dateNow);

					Boolean success = tedDeltaRepository.merge(tedDelta);

					response.setSuccess(success);
				}
			} else {
				logger.info("idGara : " + idGara + " - idStazioneAppaltante : " + idStazioneAppaltante + " - dataPerfezionamentoBando : " + gara.getDataPerfezionamentoBando() + " - DATA PERFEZIONAMENTO GARA IS NOT NULL");

				response.setSuccess(true);
				response.setError("idGara : " + idGara + " - idStazioneAppaltante : " + idStazioneAppaltante + " - GARA PERFEZIONATA");
			}
		} else {
			logger.info("idGara : " + idGara + " - idStazioneAppaltante : " + idStazioneAppaltante + " - NOT FOUND");

			response.setSuccess(false);
			response.setError("idGara : " + idGara + " - idStazioneAppaltante : " + idStazioneAppaltante + " - NOT FOUND");
		}

		return response;
	}
}