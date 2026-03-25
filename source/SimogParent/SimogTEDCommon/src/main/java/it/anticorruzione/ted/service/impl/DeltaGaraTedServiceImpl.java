/**
 * Web Service TED
 */
package it.anticorruzione.ted.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import it.anticorruzione.ted.beans.ResponseMessageTED;
import it.anticorruzione.ted.db.entity.TedDelta;
import it.anticorruzione.ted.db.repository.TedDeltaRepository;
import it.anticorruzione.ted.db.repositoryImp.TedDeltaRepositoryImpl;
import it.anticorruzione.ted.service.DeltaGaraTedService;

public class DeltaGaraTedServiceImpl implements DeltaGaraTedService {
	private static final Logger logger = Logger.getLogger(DeltaGaraTedService.class);


	private TedDeltaRepository tedDeltaRepository = new TedDeltaRepositoryImpl();

	@Override
	public ResponseMessageTED execute(Long idGara, String idStazioneAppaltante, String deltaGaraTed) {
		ResponseMessageTED response = new ResponseMessageTED();


				Calendar calendar = Calendar.getInstance();
				Date dateNow = calendar.getTime();
				Date dataInserimento = dateNow;

				TedDelta tedDeltaOld = tedDeltaRepository.getDeltaGaraValido(idGara);

				if(tedDeltaOld != null) {
					tedDeltaOld.setDataFineValidita(dateNow);

					tedDeltaRepository.merge(tedDeltaOld);

					dataInserimento = tedDeltaOld.getDataInserimento();
				}

				TedDelta tedDelta = new TedDelta();
				tedDelta.setIdGara(idGara);
				tedDelta.setXmlDelta(deltaGaraTed);
				tedDelta.setDataInserimento(dataInserimento);
				tedDelta.setDataInizioValidita(dateNow);

				tedDeltaRepository.persist(tedDelta);

				response.setSuccess(true);

		

		return response;
	}
}